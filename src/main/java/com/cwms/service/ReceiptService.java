package com.cwms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.FinTrans;
import com.cwms.entities.FinTransAdj;
import com.cwms.entities.FinTransInv;
import com.cwms.entities.Party;
import com.cwms.entities.PaymentReceiptDTO;
import com.cwms.entities.PdaDtl;
import com.cwms.entities.Pdahdr;
import com.cwms.entities.ReceiptInvoiceDto;
import com.cwms.repository.CfinvsrvRepo;
import com.cwms.repository.FinTransAdjRepo;
import com.cwms.repository.FinTransInvRepository;
import com.cwms.repository.FintransRepo;
import com.cwms.repository.InvCreditTrackRepo;
import com.cwms.repository.PadHdrRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.PdaDtlRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class ReceiptService {

	@Autowired
	private FintransRepo fintransrepo;

	@Autowired
	private PdaDtlRepo pdadtlrepo;

	@Autowired
	private PadHdrRepo pdahdrrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private InvCreditTrackRepo invcredittrackrepo;

	@Autowired
	private FinTransInvRepository finTransInvRepo;

	@Autowired
	private CfinvsrvRepo invsrvrepo;

	@Autowired
	private FinTransAdjRepo fintransadjrepo;

	@Autowired
	private PartyRepository partyrepo;


	public ResponseEntity<?> advanceReceiptBeforeSaveSearch(String cid, String bid, String id, String type) {

		Object data = fintransrepo.advanceReceiptBeforeSaveSearch(cid, bid, id, type);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}

	public ResponseEntity<?> saveAdvanceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		FinTrans fin = mapper.readValue(mapper.writeValueAsString(data.get("finTransData")), FinTrans.class);

		if (fin == null) {
			return new ResponseEntity<>("Fintrans data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05099", "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

		Object checkCurrentDayData = pdahdrrepo.checkCurrentDayData(cid, bid, fin.getPartyId(), new Date());

		if (checkCurrentDayData == null) {
			Object checkOldDayData = pdahdrrepo.checkOldDayData(cid, bid, fin.getPartyId());

			if (checkOldDayData != null) {
				Object[] data1 = (Object[]) checkOldDayData;

				BigDecimal oldVal = (BigDecimal) data1[1];
				BigDecimal total = oldVal.add(fin.getDocumentAmt()).setScale(2, RoundingMode.HALF_UP);

				Pdahdr hdr = new Pdahdr();
				hdr.setCompanyId(cid);
				hdr.setBranchId(bid);
				hdr.setStatus("A");
				hdr.setCreatedBy(user);
				hdr.setTransDate(new Date());
				hdr.setPartyId(fin.getPartyId());
				hdr.setCreditBal(BigDecimal.ZERO);
				hdr.setOpeningBal(new BigDecimal(String.valueOf(data1[1])));
				hdr.setClosingBal(total);

				pdahdrrepo.save(hdr);
			} else {
				Pdahdr hdr = new Pdahdr();
				hdr.setCompanyId(cid);
				hdr.setBranchId(bid);
				hdr.setStatus("A");
				hdr.setCreatedBy(user);
				hdr.setTransDate(new Date());
				hdr.setPartyId(fin.getPartyId());
				hdr.setCreditBal(BigDecimal.ZERO);
				hdr.setOpeningBal(BigDecimal.ZERO);
				hdr.setClosingBal(fin.getDocumentAmt());

				pdahdrrepo.save(hdr);
			}

		} else {

			Object[] data1 = (Object[]) checkCurrentDayData;
			BigDecimal oldVal = (BigDecimal) data1[1];
			BigDecimal total = oldVal.add(fin.getDocumentAmt()).setScale(2, RoundingMode.HALF_UP);

			int updateData = pdahdrrepo.updateData(cid, bid, fin.getPartyId(), new Date(), total);

		}

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTrans newData = new FinTrans();

			newData.setCompanyId(cid);
			newData.setBranchId(bid);
			newData.setDocType(fin.getDocType());
			newData.setLedgerType("AR");
			newData.setLineId(srNo.get());
			newData.setOprInvoiceNo("");
			newData.setTransId(HoldNextIdD1);
			newData.setTransDate(new Date());
			newData.setCreatedBy(user);
			newData.setCreatedDate(new Date());
			newData.setApprovedBy(user);
			newData.setApprovedDate(new Date());
			newData.setStatus("A");
			newData.setPaymentMode(p.getPayMode());
			newData.setProfitcentreId("N00001");
			newData.setPartyId(fin.getPartyId());
			newData.setAccSrNo(fin.getAccSrNo());
			newData.setChequeNo(p.getChequeNo());
			newData.setChequeDate(p.getChequeDate());
			newData.setBankName(p.getBankDetails());
			newData.setDocumentAmt(p.getAmount());
			newData.setRecordType("NEW");
			newData.setNarration(fin.getNarration());

			fintransrepo.save(newData);

			srNo.set(srNo.get().add(new BigDecimal(1)));
		});

		BigDecimal sr = pdadtlrepo.maxId(cid, bid, fin.getPartyId());

		PdaDtl dtl = new PdaDtl();
		dtl.setCompanyId(cid);
		dtl.setBranchId(bid);
		dtl.setStatus("A");
		dtl.setCreatedBy(user);
		dtl.setCreatedDate(new Date());
		dtl.setPartyId(fin.getPartyId());
		dtl.setTransDate(new Date());
		dtl.setSrNo(sr.add(new BigDecimal(1)));
		dtl.setAdvanceAmount(fin.getDocumentAmt());
		dtl.setReceiptNo(HoldNextIdD1);

		pdadtlrepo.save(dtl);

		processnextidrepo.updateAuditTrail(cid, bid, "P05099", HoldNextIdD1, "2024");

		Object data1 = fintransrepo.advanceReceiptBeforeSaveSearch(cid, bid, fin.getPartyId(), fin.getDocType());

		List<Object[]> finData = fintransrepo.getDataByTransId(cid, bid, HoldNextIdD1);

		if (finData == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("bal", data1);
		result.put("finTransData", finData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public ResponseEntity<?> receiptReceiptBeforeSaveSearch(String cid, String bid, String id, String type) {

		List<Object[]> data = invcredittrackrepo.getBeforeSearchDataForeceipt(cid, bid, id);

		if (data.isEmpty()) {

			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}

	@Transactional
	public ResponseEntity<?> saveReceiptData(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		FinTrans fin = mapper.readValue(mapper.writeValueAsString(data.get("finTransData")), FinTrans.class);

		if (fin == null) {
			return new ResponseEntity<>("Fintrans data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		List<ReceiptInvoiceDto> invoiceDto = mapper.readValue(mapper.writeValueAsString(data.get("invoiceDto")),
				new TypeReference<List<ReceiptInvoiceDto>>() {
				});

		if (invoiceDto.isEmpty()) {
			return new ResponseEntity<>("Invoice details not found", HttpStatus.CONFLICT);
		}
		Object checkCurrentDayData = pdahdrrepo.checkCurrentDayData(cid, bid, fin.getPartyId(), new Date());

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05094", "2024");

		String pre = holdId1.substring(0, 4);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%06d", nextNumericNextID1);

		if (checkCurrentDayData == null) {
			Object checkOldDayData = pdahdrrepo.checkOldDayData(cid, bid, fin.getPartyId());

			if (checkOldDayData != null) {
				Object[] data1 = (Object[]) checkOldDayData;

				BigDecimal oldVal = (BigDecimal) data1[1];
				BigDecimal total = oldVal.add(fin.getDocumentAmt()).setScale(2, RoundingMode.HALF_UP);

				Pdahdr hdr = new Pdahdr();
				hdr.setCompanyId(cid);
				hdr.setBranchId(bid);
				hdr.setStatus("A");
				hdr.setCreatedBy(user);
				hdr.setTransDate(new Date());
				hdr.setPartyId(fin.getPartyId());
				hdr.setCreditBal(BigDecimal.ZERO);
				hdr.setOpeningBal(new BigDecimal(String.valueOf(data1[1])));
				hdr.setClosingBal(total);

				pdahdrrepo.save(hdr);
			} else {
				Pdahdr hdr = new Pdahdr();
				hdr.setCompanyId(cid);
				hdr.setBranchId(bid);
				hdr.setStatus("A");
				hdr.setCreatedBy(user);
				hdr.setTransDate(new Date());
				hdr.setPartyId(fin.getPartyId());
				hdr.setCreditBal(BigDecimal.ZERO);
				hdr.setOpeningBal(BigDecimal.ZERO);
				hdr.setClosingBal(fin.getDocumentAmt());

				pdahdrrepo.save(hdr);
			}

		} else {

			Object[] data1 = (Object[]) checkCurrentDayData;
			BigDecimal oldVal = (BigDecimal) data1[1];
			BigDecimal total = oldVal.add(fin.getDocumentAmt()).setScale(2, RoundingMode.HALF_UP);

			int updateData = pdahdrrepo.updateData(cid, bid, fin.getPartyId(), new Date(), total);

		}

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTrans newData = new FinTrans();

			newData.setCompanyId(cid);
			newData.setBranchId(bid);
			newData.setDocType(fin.getDocType());
			newData.setLedgerType("AR");
			newData.setLineId(srNo.get());
			newData.setOprInvoiceNo("");
			newData.setTransId(HoldNextIdD1);
			newData.setTransDate(new Date());
			newData.setCreatedBy(user);
			newData.setCreatedDate(new Date());
			newData.setApprovedBy(user);
			newData.setApprovedDate(new Date());
			newData.setStatus("A");
			newData.setPaymentMode(p.getPayMode());
			newData.setProfitcentreId("N00001");
			newData.setPartyId(fin.getPartyId());
			newData.setAccSrNo(fin.getAccSrNo());
			newData.setChequeNo(p.getChequeNo());
			newData.setChequeDate(p.getChequeDate());
			newData.setBankName(p.getBankDetails());
			newData.setDocumentAmt(p.getAmount());
			newData.setRecordType("NEW");
			newData.setNarration(fin.getNarration());

			if ("TDS".equals(p.getPayMode())) {
				// newData.setTdsType(invoiceDto.get(0).getTdsDeductee());
//				fin.setTdsPercentage(new BigDecimal(tdsPerc));
				newData.setTdsBillAmt(p.getAmount());
			}

			fintransrepo.save(newData);

			srNo.set(srNo.get().add(new BigDecimal(1)));
		});



		invoiceDto.stream().forEach(i -> {

			FinTransInv finInv = new FinTransInv();
			finInv.setCompanyId(cid);
			finInv.setBranchId(bid);
			finInv.setStatus("A");
			finInv.setCreatedBy(user);
			finInv.setCreatedDate(new Date());
			finInv.setApprovedBy(user);
			finInv.setApprovedDate(new Date());
			finInv.setCreditType("N");
			finInv.setReceiptAmt(i.getReceiptAmt());
			finInv.setPartyId(fin.getPartyId());
			finInv.setAccSrNo(fin.getAccSrNo());
			finInv.setTransId(HoldNextIdD1);
			finInv.setOprInvoiceNo(i.getInvoiceNo());
			finInv.setDocType(fin.getDocType());
			finInv.setBillingParty(i.getBillingParty());
			finInv.setLineId(new BigDecimal(1));
			finInv.setNarration(i.getComments());
			finInv.setImporterId(i.getImporterId());
			finInv.setImpSrNo(i.getImporterSrNo());
			finInv.setLedgerType("AR");
			finInv.setDocumentAmt(i.getInvoiceAmt());
			finInv.setProfitcentreId("N00001");
			finInv.setInvoiceBalAmt(i.getInvoiceBalAmt());

			finTransInvRepo.save(finInv);

			List<PaymentReceiptDTO> tds = paymentDto.stream().filter(c -> "TDS".equals(c.getPayMode()))
					.collect(Collectors.toList());

			String tdsStaus = "N";

			if (tds != null && tds.size() > 0) {
				tdsStaus = "Y";
			}

			BigDecimal oldReceiptVal = invsrvrepo.getReceiptAmtByInvoiceNo(cid, bid, i.getInvoiceNo());

			BigDecimal finalReceiptAmt = oldReceiptVal.add(i.getReceiptAmt()).setScale(3, RoundingMode.HALF_UP);

			int updateInvSrv = invsrvrepo.updateReceiptAmount(cid, bid, i.getInvoiceNo(), HoldNextIdD1,
					finalReceiptAmt);

			BigDecimal oldCreAdjVal = invcredittrackrepo.getCreditAdjAmtByInvoiceNo(cid, bid, i.getInvoiceNo());

			BigDecimal finalCreAdjVal = oldCreAdjVal.add(i.getReceiptAmt()).setScale(3, RoundingMode.HALF_UP);

			int updateCreAdjVal = invcredittrackrepo.updateCreditAdjAmt(cid, bid, i.getInvoiceNo(), finalCreAdjVal,
					tdsStaus);

			
			BigDecimal sr = pdadtlrepo.maxId(cid, bid, fin.getPartyId());

			PdaDtl dtl = new PdaDtl();
			dtl.setCompanyId(cid);
			dtl.setBranchId(bid);
			dtl.setStatus("A");
			dtl.setCreatedBy(user);
			dtl.setCreatedDate(new Date());
			dtl.setPartyId(fin.getPartyId());
			dtl.setTransDate(new Date());
			dtl.setSrNo(sr.add(new BigDecimal(1)));
			dtl.setAdjustAmount(i.getReceiptAmt());
			dtl.setReceiptNo(HoldNextIdD1);
			dtl.setInvoiceNo(i.getInvoiceNo());
			

			pdadtlrepo.save(dtl);
		});

		Party p = partyrepo.getDataById(cid, bid, fin.getPartyId());

		if (p != null) {
			p.setCrAmtLmtUse(p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse().add(fin.getDocumentAmt()));

			partyrepo.save(p);
		}

		processnextidrepo.updateAuditTrail(cid, bid, "P05094", HoldNextIdD1, "2024");
		List<Object[]> finData = fintransrepo.getDataByTransId(cid, bid, HoldNextIdD1);

		if (finData == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		List<Object[]> finInvData = finTransInvRepo.getAfterSaveDataByTransId(cid, bid, HoldNextIdD1);

		if (finInvData == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("finTransData", finData);
		result.put("finTransInvData", finInvData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public ResponseEntity<?> adjustmentBeforeSaveSearch(String cid, String bid, String id, String type) {

		Object advanceData = fintransrepo.advanceReceiptBeforeSaveSearch(cid, bid, id, "AD");

		if (advanceData == null) {
			return new ResponseEntity<>("Advance data not found", HttpStatus.CONFLICT);
		}

		List<Object[]> data = invcredittrackrepo.getBeforeSearchDataForeceipt(cid, bid, id);

		Map<String, Object> result = new HashMap<>();
		result.put("advance", advanceData);

		if (data.isEmpty()) {

			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			result.put("receipt", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}

	@Transactional
	public ResponseEntity<?> saveAdjustmentData(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		FinTrans fin = mapper.readValue(mapper.writeValueAsString(data.get("finTransData")), FinTrans.class);

		if (fin == null) {
			return new ResponseEntity<>("Fintrans data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		List<ReceiptInvoiceDto> invoiceDto = mapper.readValue(mapper.writeValueAsString(data.get("invoiceDto")),
				new TypeReference<List<ReceiptInvoiceDto>>() {
				});

		if (invoiceDto.isEmpty()) {
			return new ResponseEntity<>("Invoice details not found", HttpStatus.CONFLICT);
		}
		Object checkCurrentDayData = pdahdrrepo.checkCurrentDayData(cid, bid, fin.getPartyId(), new Date());

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05100", "2024");

		String pre = holdId1.substring(0, 3);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%07d", nextNumericNextID1);

		Party p1 = partyrepo.getDataById(cid, bid, fin.getPartyId());

		if (checkCurrentDayData == null) {
			Object checkOldDayData = pdahdrrepo.checkOldDayData(cid, bid, fin.getPartyId());

			if (checkOldDayData != null) {
				Object[] data1 = (Object[]) checkOldDayData;

				BigDecimal oldVal = (BigDecimal) data1[1];
				BigDecimal total = oldVal.add(fin.getDocumentAmt()).setScale(2, RoundingMode.HALF_UP);

				Pdahdr hdr = new Pdahdr();
				hdr.setCompanyId(cid);
				hdr.setBranchId(bid);
				hdr.setStatus("A");
				hdr.setCreatedBy(user);
				hdr.setTransDate(new Date());
				hdr.setPartyId(fin.getPartyId());

				hdr.setOpeningBal(new BigDecimal(String.valueOf(data1[1])));
				hdr.setClosingBal(total);
				
				if (p1 != null) {
					p1.setCrAmtLmtUse(p1.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p1.getCrAmtLmtUse().add(fin.getDocumentAmt()));
					hdr.setCreditBal(p1.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p1.getCrAmtLmtUse().add(fin.getDocumentAmt()).negate());
					partyrepo.save(p1);
				}

				pdahdrrepo.save(hdr);

			} else {
				Pdahdr hdr = new Pdahdr();
				hdr.setCompanyId(cid);
				hdr.setBranchId(bid);
				hdr.setStatus("A");
				hdr.setCreatedBy(user);
				hdr.setTransDate(new Date());
				hdr.setPartyId(fin.getPartyId());
				
				if (p1 != null) {
					p1.setCrAmtLmtUse(p1.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p1.getCrAmtLmtUse().add(fin.getDocumentAmt()));
					hdr.setCreditBal(p1.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p1.getCrAmtLmtUse().add(fin.getDocumentAmt()).negate());
					partyrepo.save(p1);
				}
				hdr.setOpeningBal(BigDecimal.ZERO);
				hdr.setClosingBal(fin.getDocumentAmt());

				pdahdrrepo.save(hdr);
			}

		} else {

			Object[] data1 = (Object[]) checkCurrentDayData;
			BigDecimal oldVal = (BigDecimal) data1[1];
			BigDecimal total = oldVal.add(fin.getDocumentAmt()).setScale(2, RoundingMode.HALF_UP);

			int updateData = pdahdrrepo.updateData(cid, bid, fin.getPartyId(), new Date(), total);

		}

		BigDecimal totalAmtWtTds = paymentDto.stream().filter(item -> !"TDS".equals(item.getPayMode())) // Exclude items
																										// with payMode
																										// 'TDS'
				.map(item -> item.getAmount()) // Map to BigDecimal amounts
				.filter(amount -> amount != null) // Filter out null values for safety
				.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP); // Sum up the amounts

		AtomicReference<BigDecimal> totalAmtWtTds1 = new AtomicReference<>(totalAmtWtTds);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTransAdj newData = new FinTransAdj();

			newData.setCompanyId(cid);
			newData.setBranchId(bid);
			newData.setDocType(fin.getDocType());
			newData.setLedgerType("AR");
			newData.setLineId(srNo.get());
			newData.setOprInvoiceNo("");
			newData.setTransId(HoldNextIdD1);
			newData.setTransDate(new Date());
			newData.setCreatedBy(user);
			newData.setCreatedDate(new Date());
			newData.setApprovedBy(user);
			newData.setApprovedDate(new Date());
			newData.setStatus("A");
			newData.setPaymentMode(p.getPayMode());
			newData.setProfitcentreId("N00001");
			newData.setPartyId(fin.getPartyId());
			newData.setAccSrNo(fin.getAccSrNo());
			newData.setChequeNo(p.getChequeNo());
			newData.setChequeDate(p.getChequeDate());
			newData.setBankName(p.getBankDetails());
			newData.setDocumentAmt(p.getAmount());
			newData.setRecordType("NEW");
			newData.setNarration(fin.getNarration());

			if ("TDS".equals(p.getPayMode())) {
				// newData.setTdsType(invoiceDto.get(0).getTdsDeductee());
//				fin.setTdsPercentage(new BigDecimal(tdsPerc));
				newData.setTdsBillAmt(p.getAmount());
			}

			fintransadjrepo.save(newData);

			srNo.set(srNo.get().add(new BigDecimal(1)));
		});

		List<FinTrans> advanceRecords = fintransrepo.getAdvanceRecords(cid, bid, fin.getPartyId(), "AD");

		if (!advanceRecords.isEmpty()) {
			advanceRecords.stream().forEach(f -> {

				if (totalAmtWtTds1.get().compareTo(BigDecimal.ZERO) > 0) {
					if (totalAmtWtTds1.get().compareTo((f.getDocumentAmt()
							.subtract(f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt()))) <= 0) {
						f.setClearedAmt((f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt())
								.add(totalAmtWtTds1.get()));

						totalAmtWtTds1.set(totalAmtWtTds1.get().subtract(totalAmtWtTds1.get()));
					} else if (totalAmtWtTds1.get().compareTo((f.getDocumentAmt()
							.subtract(f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt()))) > 0) {
						f.setClearedAmt((f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt())
								.add((f.getDocumentAmt()
										.subtract(f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt()))));

						totalAmtWtTds1.set(totalAmtWtTds1.get().subtract((f.getDocumentAmt().subtract(
								f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt()))));
					}

					fintransrepo.save(f);
				}

			});
		}

		invoiceDto.stream().forEach(i -> {

			FinTransInv finInv = new FinTransInv();
			finInv.setCompanyId(cid);
			finInv.setBranchId(bid);
			finInv.setStatus("A");
			finInv.setCreatedBy(user);
			finInv.setCreatedDate(new Date());
			finInv.setApprovedBy(user);
			finInv.setApprovedDate(new Date());
			finInv.setCreditType("N");
			finInv.setReceiptAmt(i.getReceiptAmt());
			finInv.setPartyId(fin.getPartyId());
			finInv.setAccSrNo(fin.getAccSrNo());
			finInv.setTransId(HoldNextIdD1);
			finInv.setOprInvoiceNo(i.getInvoiceNo());
			finInv.setDocType(fin.getDocType());
			finInv.setBillingParty(i.getBillingParty());
			finInv.setLineId(new BigDecimal(1));
			finInv.setNarration(i.getComments());
			finInv.setImporterId(i.getImporterId());
			finInv.setImpSrNo(i.getImporterSrNo());
			finInv.setLedgerType("AR");
			finInv.setDocumentAmt(i.getInvoiceAmt());
			finInv.setProfitcentreId("N00001");
			finInv.setInvoiceBalAmt(i.getInvoiceBalAmt());

			finTransInvRepo.save(finInv);

			List<PaymentReceiptDTO> tds = paymentDto.stream().filter(c -> "TDS".equals(c.getPayMode()))
					.collect(Collectors.toList());

			String tdsStaus = "N";

			if (tds != null && tds.size() > 0) {
				tdsStaus = "Y";
			}

			BigDecimal oldReceiptVal = invsrvrepo.getReceiptAmtByInvoiceNo(cid, bid, i.getInvoiceNo());

			BigDecimal finalReceiptAmt = oldReceiptVal.add(i.getReceiptAmt()).setScale(3, RoundingMode.HALF_UP);

			int updateInvSrv = invsrvrepo.updateReceiptAmount(cid, bid, i.getInvoiceNo(), HoldNextIdD1,
					finalReceiptAmt);

			BigDecimal oldCreAdjVal = invcredittrackrepo.getCreditAdjAmtByInvoiceNo(cid, bid, i.getInvoiceNo());

			BigDecimal finalCreAdjVal = oldCreAdjVal.add(i.getReceiptAmt()).setScale(3, RoundingMode.HALF_UP);

			int updateCreAdjVal = invcredittrackrepo.updateCreditAdjAmt(cid, bid, i.getInvoiceNo(), finalCreAdjVal,
					tdsStaus);
			
			
			
			AtomicReference<BigDecimal> ivAmt = new AtomicReference<>(i.getReceiptAmt());

			AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

			advanceRecords.stream().forEach(f -> {
				
				if(ivAmt.get().compareTo(BigDecimal.ZERO)>0) {
					
					FinTrans newData = new FinTrans();

					newData.setCompanyId(cid);
					newData.setBranchId(bid);
					newData.setDocType(fin.getDocType());
					newData.setLedgerType("AR");
					newData.setLineId(srNo1.get());
					newData.setOprInvoiceNo(i.getInvoiceNo());
					newData.setTransId(i.getTransId());
					newData.setOprAdjTransId(f.getTransId());
					newData.setTransDate(new Date());
					newData.setCreatedBy(user);
					newData.setCreatedDate(new Date());
					newData.setApprovedBy(user);
					newData.setApprovedDate(new Date());
					newData.setStatus("A");
					newData.setPaymentMode("ADVANCE");
					newData.setProfitcentreId("N00001");
					newData.setPartyId(fin.getPartyId());
					newData.setAccSrNo(fin.getAccSrNo());
					newData.setRecordType("NEW");
					newData.setCreditFlag("N");
					newData.setCreditAdjustFlag("Y");
					newData.setNarration("Adjustment Receipt No - "+HoldNextIdD1);

					BigDecimal sr = pdadtlrepo.maxId(cid, bid, fin.getPartyId());

					PdaDtl dtl = new PdaDtl();
					dtl.setCompanyId(cid);
					dtl.setBranchId(bid);
					dtl.setStatus("A");
					dtl.setCreatedBy(user);
					dtl.setCreatedDate(new Date());
					dtl.setPartyId(fin.getPartyId());
					dtl.setTransDate(new Date());
					dtl.setSrNo(sr.add(new BigDecimal(1)));
					dtl.setAdjTransId(f.getTransId());
					dtl.setCreditAdjustFlag("Y");
					dtl.setInvoiceNo(i.getInvoiceNo());
					
					BigDecimal docAmt = f.getDocumentAmt();
					BigDecimal clearAmt = f.getClearedAmt() == null ? BigDecimal.ZERO : f.getClearedAmt();
					
					if(ivAmt.get().compareTo(docAmt.subtract(clearAmt)) > 0) {
						dtl.setAdjustAmount(docAmt.subtract(clearAmt).setScale(2, RoundingMode.HALF_UP));
						newData.setDocumentAmt(docAmt.subtract(clearAmt).setScale(2, RoundingMode.HALF_UP));
						ivAmt.set(ivAmt.get().subtract(docAmt.subtract(clearAmt).setScale(2, RoundingMode.HALF_UP)));
					}
					
					else if(ivAmt.get().compareTo(docAmt.subtract(clearAmt)) <= 0) {
						dtl.setAdjustAmount(ivAmt.get());
						
						newData.setDocumentAmt(ivAmt.get());
						ivAmt.set(ivAmt.get().subtract(ivAmt.get()));
					}
					
					

					pdadtlrepo.save(dtl);
					fintransrepo.save(newData);
					
					srNo1.set(srNo1.get().add(new BigDecimal(1)));
				}

				
			});

		});

		processnextidrepo.updateAuditTrail(cid, bid, "P05100", HoldNextIdD1, "2024");

		Object advanceData = fintransrepo.advanceReceiptBeforeSaveSearch(cid, bid, fin.getPartyId(), "AD");

		if (advanceData == null) {
			return new ResponseEntity<>("Advance data not found", HttpStatus.CONFLICT);
		}

		List<Object[]> finData = fintransadjrepo.getDataByTransId(cid, bid, HoldNextIdD1);

		if (finData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		List<Object[]> finInvData = finTransInvRepo.getAfterSaveDataByTransId(cid, bid, HoldNextIdD1);

		if (finInvData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("advance", advanceData);
		result.put("finTransData", finData);
		result.put("finTransInvData", finInvData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public ResponseEntity<?> getAfterSaveSearchData(String cid, String bid, String val) {

		List<Object[]> result = new ArrayList<>();

		List<Object[]> data = fintransrepo.getAfterSaveData(cid, bid, val);

		if (!data.isEmpty()) {
			result.addAll(data);
		}

		List<Object[]> data1 = fintransadjrepo.getAfterSaveData(cid, bid, val);

		if (!data1.isEmpty()) {
			result.addAll(data1);
		}

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			result = result.stream().sorted((obj1, obj2) -> {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm"); // Adjust the format as
																							// needed
					String dateStr1 = (String) obj1[2]; // Assuming the date is at index 5
					String dateStr2 = (String) obj2[2];

					// Parsing the dates
					Date date1 = dateFormat.parse(dateStr1);
					Date date2 = dateFormat.parse(dateStr2);

					return date2.compareTo(date1); // Descending order
				} catch (Exception e) {
					e.printStackTrace();
					return 0; // If date parsing fails, keep the original order
				}
			}).collect(Collectors.toList()); // Collect the sorted result

		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	public ResponseEntity<?> getSelectedData(String cid, String bid, String val, String id, String type) {

		Object advanceData = fintransrepo.advanceReceiptBeforeSaveSearch(cid, bid, id, "AD");
		Map<String, Object> result = new HashMap<>();

		if ("AJ".equals(type)) {
			List<Object[]> finData = fintransadjrepo.getDataByTransId(cid, bid, val);

			if (finData.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("finTransData", finData);
		} else {
			List<Object[]> finData = fintransrepo.getDataByTransId(cid, bid, val);

			if (finData.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("finTransData", finData);
		}

		List<Object[]> finInvData = finTransInvRepo.getAfterSaveDataByTransId(cid, bid, val);

		result.put("advance", advanceData);

		result.put("finTransInvData", finInvData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
