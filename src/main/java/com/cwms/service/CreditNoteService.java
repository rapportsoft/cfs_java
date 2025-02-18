package com.cwms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.AssessmentSheet;
import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.CreditNoteDtl;
import com.cwms.entities.CreditNoteHdr;
import com.cwms.entities.FinTrans;
import com.cwms.entities.Party;
import com.cwms.helper.ExportContainerAssessmentData;
import com.cwms.repository.CreditNoteDtlRepo;
import com.cwms.repository.CreditNoteHdrRepo;
import com.cwms.repository.FintransRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CreditNoteService {

	@Autowired
	private CreditNoteDtlRepo crdDtlRepo;
	
	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CreditNoteHdrRepo crHdrRepo;
	
	@Autowired
	private FintransRepo finTransRepo;
	
	@Autowired
	private ProcessNextIdService processnextIdService;
	

	public List<Object[]> getCreditNoteToSelect(String companyId, String branchId, String searchValue) {
		return crHdrRepo.getCreditNoteToSelect(companyId, branchId, searchValue);
	}
	
	public ResponseEntity<?> getSelectedAssesMentSearch(String companyId, String branchId, String creditNoteNo,
			String invoiceNo, String profitCenterId) {

		List<CreditNoteHdr> dataForSeletdInvoiceNo = crHdrRepo.getDataForSeletdInvoiceNoAfterSave(companyId,
				branchId, invoiceNo, creditNoteNo);
		CreditNoteHdr creditNoteHdr2 = dataForSeletdInvoiceNo.get(0);


		// Fetch service details
		List<CreditNoteDtl> listSend = crdDtlRepo.getServiceDetailsOfInvoiceAfterSave(companyId,
				branchId, invoiceNo, creditNoteNo);

		List<Object[]> creditNoteToSelectHistory = crHdrRepo.getCreditNoteToSelectHistory(companyId, branchId, invoiceNo, creditNoteNo);
		
		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("crHeader", creditNoteHdr2);
		finalResult.put("crDtl", listSend);
		finalResult.put("cHistory", creditNoteToSelectHistory);
		
		return ResponseEntity.ok(finalResult);

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public ResponseEntity<?> getInvoiceListForCreditNote(String companyId, String branchId, String searchValue,
			String profitCentreId) {
		List<String> resultList = crHdrRepo.getInvoiceNoForCreditNote(companyId, branchId, searchValue);

		List<Map<String, Object>> toSendGetList = resultList.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put("value", row);
			map.put("label", row);
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(toSendGetList);
	}

	public ResponseEntity<?> getDataForSeletdInvoiceNo(String companyId, String branchId, String profitCentreId,
			String invoiceNo) {

		List<CreditNoteHdr> dataForSeletdInvoiceNo = crHdrRepo.getDataForSeletdInvoiceNo(companyId, branchId,
				invoiceNo);
		CreditNoteHdr sendAssessmentSheet = dataForSeletdInvoiceNo.get(0);
		List<CreditNoteDtl> getServiceDetailsOfInvoice = crdDtlRepo.getServiceDetailsOfInvoice(companyId, branchId,
				invoiceNo);

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("crHeader", sendAssessmentSheet);
		finalResult.put("crDtl", getServiceDetailsOfInvoice);

		System.out.println("sendAssessmentSheet \n" + sendAssessmentSheet);

		return ResponseEntity.ok(finalResult);
	}

	@Transactional
	public ResponseEntity<?> saveCreditNote(String companyId, String branchId, String user,
			Map<String, Object> resultList, String creditType) {
		try {

			CreditNoteHdr creditNoteHdr = objectMapper
					.readValue(objectMapper.writeValueAsString(resultList.get("creditNoteHdr")), CreditNoteHdr.class);

			if (creditNoteHdr == null) {
				return new ResponseEntity<>("Header data not found", HttpStatus.CONFLICT);
			}

			List<CreditNoteDtl> creditNoteDtlData = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("CreditNoteDtl")),
					new TypeReference<List<CreditNoteDtl>>() {
					});

			BigDecimal zero = BigDecimal.ZERO;
			Date newDate = new Date();
			String autoCreditNoteNo = "";

			BigDecimal localAmt = zero;
			BigDecimal taxAmt = zero;
			
			System.out.println(creditNoteHdr + " \n" + creditNoteDtlData);
			

			if (creditNoteHdr.getInvoiceNo() != null && !creditNoteHdr.getInvoiceNo().trim().isEmpty()) {
				
				System.out.println("1");

				autoCreditNoteNo = creditNoteHdr.getInvoiceNo();

				for (CreditNoteDtl dtl : creditNoteDtlData) {
					
					System.out.println("2");
					BigDecimal localAmtDb = crdDtlRepo.findLocalAmtByInvoiceNoAndServiceId(companyId, branchId,
							dtl.getInvoiceNo(), dtl.getOldInvoiceNo() , dtl.getServiceId());
					
					System.out.println("3" + localAmtDb);

					if (localAmtDb != null && localAmtDb.compareTo(BigDecimal.ZERO) > 0) {
						
						System.out.println("4" + localAmtDb);
						
						crdDtlRepo.updateCreditNoteDtl(companyId, branchId,
						dtl.getInvoiceNo(), dtl.getOldInvoiceNo() , dtl.getServiceId(), dtl.getLocalAmt(), dtl.getCreditNoteGstAmt(), creditNoteHdr.getComments(), user);

//						int updateInvoiceDtl = crdDtlRepo.updateInvoiceDtl(companyId, branchId, dtl.getOldInvoiceNo(), dtl.getServiceId(), difference);

						taxAmt = taxAmt.add(dtl.getCreditNoteGstAmt());
						localAmt = localAmt.add(dtl.getLocalAmt());
					
					} else {
					
					
					dtl.setInvoiceNo(autoCreditNoteNo);
					dtl.setInvoiceDate(newDate);
					dtl.setCreatedBy(user);
					dtl.setCreatedDate(newDate);
					dtl.setStatus("N");
					dtl.setBillAmt(dtl.getLocalAmt());
					dtl.setInvoiceAmt(dtl.getLocalAmt());
					dtl.setTaxAmt(dtl.getCreditNoteGstAmt());
					dtl.setCompanyId(companyId);
					dtl.setBranchId(branchId);
					dtl.setFinYear("2025");
					dtl.setFinPeriod("25");
					dtl.setPartyId(creditNoteHdr.getPartyId());
					dtl.setOldInvoiceNo(creditNoteHdr.getOldInvoiceNo());
					dtl.setProfitcentreId(creditNoteHdr.getProfitcentreId());
					dtl.setComments(creditNoteHdr.getComments());
					
					taxAmt = taxAmt.add(dtl.getTaxAmt());
					localAmt = localAmt.add(dtl.getLocalAmt());
					
//					int updateInvoiceDtl = crdDtlRepo.updateInvoiceDtl(companyId, branchId, dtl.getOldInvoiceNo(), dtl.getServiceId(), dtl.getLocalAmt());
//					
//					System.out.println(" updateInvoiceDtl " + updateInvoiceDtl);
					crdDtlRepo.save(dtl);
					}		
				}			

				
				int updateCreditNoteHdr = crHdrRepo.updateCreditNoteHdr(companyId, branchId, creditNoteHdr.getInvoiceNo(), creditNoteHdr.getOldInvoiceNo(), creditNoteHdr.getComments(), localAmt, taxAmt.add(localAmt), user);
				System.out.println( " updateCreditNoteHdr " + updateCreditNoteHdr + creditNoteHdr + " \n Update \n " + creditNoteDtlData);
				
			} else {

				System.out.println("2");
				autoCreditNoteNo = processnextIdService.autoCreditNoteNo(companyId, branchId, "P01119");

				for (CreditNoteDtl dtl : creditNoteDtlData) {
					dtl.setInvoiceNo(autoCreditNoteNo);
					dtl.setInvoiceDate(newDate);
					dtl.setCreatedBy(user);
					dtl.setCreatedDate(newDate);
					dtl.setStatus("N");
					dtl.setBillAmt(dtl.getLocalAmt());
					dtl.setInvoiceAmt(dtl.getLocalAmt());
					dtl.setTaxAmt(dtl.getCreditNoteGstAmt());
					dtl.setCompanyId(companyId);
					dtl.setBranchId(branchId);
					dtl.setFinYear("2025");
					dtl.setFinPeriod("25");
					dtl.setPartyId(creditNoteHdr.getPartyId());
					dtl.setOldInvoiceNo(creditNoteHdr.getOldInvoiceNo());
					dtl.setProfitcentreId(creditNoteHdr.getProfitcentreId());
					dtl.setComments(creditNoteHdr.getComments());
					
					taxAmt = taxAmt.add(dtl.getTaxAmt());
					localAmt = localAmt.add(dtl.getLocalAmt());
					
//					int updateInvoiceDtl = crdDtlRepo.updateInvoiceDtl(companyId, branchId, dtl.getOldInvoiceNo(), dtl.getServiceId(), dtl.getLocalAmt());
					
//					System.out.println(" updateInvoiceDtl " + updateInvoiceDtl);

				}

				creditNoteHdr.setAccSrNo(1);
				creditNoteHdr.setInvoiceNo(autoCreditNoteNo);
				creditNoteHdr.setInvoiceDate(newDate);
				creditNoteHdr.setLocalAmt(localAmt);
				creditNoteHdr.setBillAmt(localAmt);
				creditNoteHdr.setInvoiceAmt(taxAmt.add(localAmt));
				creditNoteHdr.setAcCode("");
				creditNoteHdr.setFinYear("2025");
				creditNoteHdr.setFinPeriod("25");
				creditNoteHdr.setCompanyId(companyId);
				creditNoteHdr.setBranchId(branchId);
				creditNoteHdr.setStatus("N");
				creditNoteHdr.setCreatedBy(user);
				creditNoteHdr.setCreatedDate(newDate);

				System.out.println(creditNoteHdr + " \n\n " + creditNoteDtlData);
				crHdrRepo.save(creditNoteHdr);
				crdDtlRepo.saveAll(creditNoteDtlData);

			}

			List<CreditNoteHdr> dataForSeletdInvoiceNo = crHdrRepo.getDataForSeletdInvoiceNoAfterSave(companyId,
					branchId, creditNoteHdr.getOldInvoiceNo(), autoCreditNoteNo);
			CreditNoteHdr creditNoteHdr2 = dataForSeletdInvoiceNo.get(0);

			List<CreditNoteDtl> listSend = new ArrayList<>();

			// Fetch service details
			List<CreditNoteDtl> getServiceDetailsOfInvoice = crdDtlRepo.getServiceDetailsOfInvoiceAfterSave(companyId,
					branchId, creditNoteHdr.getOldInvoiceNo(), autoCreditNoteNo);

			// Extract service IDs
			List<String> serviceIds = getServiceDetailsOfInvoice.stream().map(CreditNoteDtl::getServiceId)
					.collect(Collectors.toList());

			// 🛠️ Fix: Ensure serviceIds is never empty
			if (serviceIds.isEmpty()) {
				serviceIds = Collections.singletonList("DUMMY"); // Prevent SQL error
			}

			// Fetch details NOT IN the serviceIds
			List<CreditNoteDtl> getServiceDetailsOfInvoiceNotIn = crdDtlRepo.getServiceDetailsOfInvoice(companyId,
					branchId, creditNoteHdr.getOldInvoiceNo(), serviceIds);

			// Combine both lists
			listSend.addAll(getServiceDetailsOfInvoice);
			listSend.addAll(getServiceDetailsOfInvoiceNotIn);


			List<Object[]> creditNoteToSelectHistory = crHdrRepo.getCreditNoteToSelectHistory(companyId, branchId, creditNoteHdr.getOldInvoiceNo(), autoCreditNoteNo);
			
			Map<String, Object> finalResult = new HashMap<>();
			finalResult.put("crHeader", creditNoteHdr2);
			finalResult.put("crDtl", listSend);
			finalResult.put("cHistory", creditNoteToSelectHistory);
			
			return ResponseEntity.ok(finalResult);

		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error in Add Service ContainerWise: " + e.getMessage());
		}
		
	}

	@Transactional
	public ResponseEntity<?> processCreditNote(String companyId, String branchId, String user,
			Map<String, Object> resultList, String creditType) {
		try {

			CreditNoteHdr creditNoteHdr = objectMapper
					.readValue(objectMapper.writeValueAsString(resultList.get("creditNoteHdr")), CreditNoteHdr.class);

			if (creditNoteHdr == null) {
				return new ResponseEntity<>("Header data not found", HttpStatus.CONFLICT);
			}

			List<CreditNoteDtl> creditNoteDtlData = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("CreditNoteDtl")),
					new TypeReference<List<CreditNoteDtl>>() {
					});

			BigDecimal zero = BigDecimal.ZERO;
			Date newDate = new Date();
			String autoCreditNoteNo = processnextIdService.autoCreditNoteNo(companyId, branchId, "P01109");

			BigDecimal localAmt = zero;
			BigDecimal taxAmt = zero;
			FinTrans finTransDataOfInvoice = crHdrRepo.getFinTransDataOfInvoice(companyId, branchId, creditNoteHdr.getOldInvoiceNo());
			
			
			for (CreditNoteDtl dtl : creditNoteDtlData) {
				
				System.out.println("2");
				BigDecimal localAmtDb = crdDtlRepo.findLocalAmtByInvoiceNoAndServiceId(companyId, branchId,
						dtl.getInvoiceNo(), dtl.getOldInvoiceNo() , dtl.getServiceId());
				
				System.out.println("3" + localAmtDb);

				if (localAmtDb != null && localAmtDb.compareTo(BigDecimal.ZERO) > 0) {
					
					System.out.println("4" + localAmtDb);
					BigDecimal difference = dtl.getLocalAmt().subtract(localAmtDb);
					System.out.println("5" + difference + " dtl.getLocalAmt() " + dtl.getLocalAmt() + " dtl.getTaxAmt() " + dtl.getTaxAmt());
					
					crdDtlRepo.updateCreditNoteDtlProcess(companyId, branchId,
					dtl.getInvoiceNo(), dtl.getOldInvoiceNo() , dtl.getServiceId(), dtl.getLocalAmt(), dtl.getCreditNoteGstAmt(), autoCreditNoteNo, newDate, creditNoteHdr.getComments(), user);

					int updateInvoiceDtl = crdDtlRepo.updateInvoiceDtl(companyId, branchId, dtl.getOldInvoiceNo(), dtl.getServiceId(), dtl.getLocalAmt());

					System.out.println(" difference " + difference + " \n updateInvoiceDtl " + updateInvoiceDtl);
					taxAmt = taxAmt.add(dtl.getCreditNoteGstAmt());
					localAmt = localAmt.add(dtl.getLocalAmt());
				
				} else {
				
				
				dtl.setInvoiceNo(autoCreditNoteNo);
				dtl.setInvoiceDate(newDate);
				dtl.setCreatedBy(user);
				dtl.setCreatedDate(newDate);
				dtl.setApprovedBy(user);
				dtl.setApprovedDate(newDate);
				dtl.setStatus("A");
				dtl.setBillAmt(dtl.getLocalAmt());
				dtl.setInvoiceAmt(dtl.getLocalAmt());
				dtl.setTaxAmt(dtl.getCreditNoteGstAmt());
				dtl.setCompanyId(companyId);
				dtl.setBranchId(branchId);
				dtl.setFinYear("2025");
				dtl.setFinPeriod("25");
				dtl.setPartyId(creditNoteHdr.getPartyId());
				dtl.setOldInvoiceNo(creditNoteHdr.getOldInvoiceNo());
				dtl.setProfitcentreId(creditNoteHdr.getProfitcentreId());
				dtl.setComments(creditNoteHdr.getComments());
				
				taxAmt = taxAmt.add(dtl.getTaxAmt());
				localAmt = localAmt.add(dtl.getLocalAmt());
				
				int updateInvoiceDtl = crdDtlRepo.updateInvoiceDtl(companyId, branchId, dtl.getOldInvoiceNo(), dtl.getServiceId(), dtl.getLocalAmt());
				
				System.out.println(" updateInvoiceDtl " + updateInvoiceDtl);
				crdDtlRepo.save(dtl);
				}		
			}			
			String holdId2 = processnextidrepo.findAuditTrail(companyId, branchId, "P05094", "2024");

			String pre1 = holdId2.substring(0, 4);

			int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

			int nextNumericNextID2 = lastNextNumericId2 + 1;

			String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

			
			FinTrans fintarns = new FinTrans();
			
			fintarns.setTransId(HoldNextIdD2);
			fintarns.setTransDate(newDate);
			fintarns.setCreatedBy(user);
			fintarns.setCreatedDate(newDate);
			fintarns.setApprovedBy(user);
			fintarns.setApprovedDate(newDate);
			fintarns.setStatus("A");
			fintarns.setCompanyId(companyId);
			fintarns.setBranchId(branchId);
			fintarns.setLineId(BigDecimal.ONE);
			fintarns.setPaymentMode(finTransDataOfInvoice.getPaymentMode());
			fintarns.setProfitcentreId(creditNoteHdr.getProfitcentreId());
			fintarns.setLedgerType("CN");
			fintarns.setDocType("CR");
			fintarns.setAccSrNo(finTransDataOfInvoice.getAccSrNo());
			fintarns.setAcCode(finTransDataOfInvoice.getAcCode());
			fintarns.setBillingParty(finTransDataOfInvoice.getPartyId());
			fintarns.setPaymentMode(finTransDataOfInvoice.getPaymentMode());
			fintarns.setNarration("Amount Adjust With CREDITNOTE");
			fintarns.setCnAdjustFlag("Y");
			fintarns.setChequeNo("");
			fintarns.setOprInvoiceNo(finTransDataOfInvoice.getTransId());
			fintarns.setOldInvoiceNo(creditNoteHdr.getOldInvoiceNo());
			fintarns.setDocumentAmt(taxAmt.add(localAmt));
			fintarns.setRecordType("NEW");
			fintarns.setImporterId(creditNoteHdr.getImporterId());
			fintarns.setImpSrNo(creditNoteHdr.getImpSrNo().intValue());
			
			fintarns.setPartyId(creditNoteHdr.getPartyId());
			fintarns.setTransDate(newDate);
			fintarns.setTransId(autoCreditNoteNo);
			fintarns.setCreditFlag("N");
			fintarns.setCreditType("N");
			
			finTransRepo.save(fintarns);
			
			
			processnextidrepo.updateAuditTrail(companyId, branchId, "P05094", HoldNextIdD2, "2024");
			
			int updateCreditNoteHdr = crHdrRepo.updateCreditNoteHdrProcess(companyId, branchId, creditNoteHdr.getInvoiceNo(), creditNoteHdr.getOldInvoiceNo(), creditNoteHdr.getComments(), localAmt, taxAmt.add(localAmt), autoCreditNoteNo, newDate, user);
			System.out.println( " updateCreditNoteHdr " + updateCreditNoteHdr + creditNoteHdr + " \n Update \n " + creditNoteDtlData);
			List<CreditNoteHdr> dataForSeletdInvoiceNo = crHdrRepo.getDataForSeletdInvoiceNoAfterSave(companyId,
					branchId, creditNoteHdr.getOldInvoiceNo(), autoCreditNoteNo);
			CreditNoteHdr creditNoteHdr2 = dataForSeletdInvoiceNo.get(0);


			// Fetch service details
			List<CreditNoteDtl> listSend = crdDtlRepo.getServiceDetailsOfInvoiceAfterSave(companyId,
					branchId, creditNoteHdr.getOldInvoiceNo(), autoCreditNoteNo);

		
			List<Object[]> creditNoteToSelectHistory = crHdrRepo.getCreditNoteToSelectHistory(companyId, branchId, creditNoteHdr.getOldInvoiceNo(), autoCreditNoteNo);
			
			Map<String, Object> finalResult = new HashMap<>();
			finalResult.put("crHeader", creditNoteHdr2);
			finalResult.put("crDtl", listSend);
			finalResult.put("cHistory", creditNoteToSelectHistory);
			
			return ResponseEntity.ok(finalResult);

		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error in Add Service ContainerWise: " + e.getMessage());
		}
	}

}
