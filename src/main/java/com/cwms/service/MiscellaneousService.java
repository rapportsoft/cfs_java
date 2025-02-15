package com.cwms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.AssessmentContainerDTO;
import com.cwms.entities.AssessmentSheet;
import com.cwms.entities.Branch;
import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.Cfinvsrvanx;
import com.cwms.entities.Cfinvsrvanxback;
import com.cwms.entities.Cfinvsrvdtl;
import com.cwms.entities.FinTrans;
import com.cwms.entities.InvCreditTrack;
import com.cwms.entities.MiscDto;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.entities.PaymentReceiptDTO;
import com.cwms.entities.PdaDtl;
import com.cwms.entities.Pdahdr;
import com.cwms.repository.AssessmentSheetRepo;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CFSDayRepo;
import com.cwms.repository.CFSRepositary;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.CfexbonddtsRepo;
import com.cwms.repository.CfinbondCrgHdrRepo;
import com.cwms.repository.CfinvsrvRepo;
import com.cwms.repository.CfinvsrvanxRepo;
import com.cwms.repository.CfinvsrvanxbackRepo;
import com.cwms.repository.CfinvsrvdtlRepo;
import com.cwms.repository.ExportInvoiceRepo;
import com.cwms.repository.FintransRepo;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.InvCreditTrackRepo;
import com.cwms.repository.PadHdrRepo;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.PdaDtlRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.SSRDtlRepository;
import com.cwms.repository.ServiceMappingRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MiscellaneousService {

	@Autowired
	private AssessmentSheetRepo assessmentsheetrepo;

	@Autowired
	private ExportInvoiceRepo exportInvoiceRepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private ServiceMappingRepo serviceMappingRepo;

	@Autowired
	private CFSRepositary cfstarrifrepo;

	@Autowired
	private CFSTarrifServiceRepository cfstariffservicerepo;

	@Autowired
	private CFSDayRepo cfsdayrepo;

	@Autowired
	private SSRDtlRepository ssrRepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private PartyAddressRepository partyRepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfinvsrvanxRepo cfinvsrvanxrepo;

	@Autowired
	private ImportInventoryRepository impInventoryRepo;

	@Autowired
	private CfinvsrvanxbackRepo invsrvbackrepo;

	@Autowired
	private CfinvsrvdtlRepo invsrvdtlRepo;

	@Autowired
	private CfinvsrvRepo invsrvRepo;

	@Autowired
	private FintransRepo finTransrepo;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private CfbondnocRepository cfBondNocRepo;

	@Autowired
	private CfinbondCrgHdrRepo cfinbondhdrrepo;

	@Autowired
	private InvCreditTrackRepo invcredittrackrepo;

	@Autowired
	private PdaDtlRepo pdadtlrepo;

	@Autowired
	private PadHdrRepo pdahdrrepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	public ResponseEntity<?> saveMiscellaneousData(String cid, String bid, String user, Map<String, Object> data) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
					AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<MiscDto> serviceData = mapper.readValue(mapper.writeValueAsString(data.get("serviceData")),
					new TypeReference<List<MiscDto>>() {
					});

			if (serviceData.isEmpty()) {
				return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
			}

			if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {
				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05113", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("MISC%06d", nextNumericNextID1);

				assessment.setCompanyId(cid);
				assessment.setBranchId(bid);
				assessment.setCreatedBy(user);
				assessment.setCreatedDate(new Date());
				assessment.setStatus('A');
				assessment.setWeighmentFlag("N");
				assessment.setCalculateInvoice('N');
				assessment.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
				assessment.setAssesmentLineNo("1");
				assessment.setApprovedBy(user);
				assessment.setApprovedDate(new Date());
				assessment.setAssesmentDate(new Date());
				assessment.setTransType("MISC");
				assessment.setAssesmentId(HoldNextIdD1);
				assessment.setInvoiceCategory("Single");

				Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

				PartyAddress p = partyRepo.getData(cid, bid, assessment.getOthPartyId(), assessment.getOthSrNo());

				assessment.setPartyId(assessment.getOthPartyId());
				assessment.setPartySrNo(new BigDecimal(assessment.getOthSrNo()));
				assessment.setPayableParty(assessment.getOthPartyId());
				assessment.setPayablePartyId(assessment.getOthSrNo());

				if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
					assessment.setIgst("Y");
					assessment.setCgst("N");
					assessment.setSgst("N");
				} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
					assessment.setIgst("E");
					assessment.setCgst("N");
					assessment.setSgst("N");
				}

				else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {

					if (b.getState().equals(p.getState())) {
						assessment.setIgst("N");
						assessment.setCgst("Y");
						assessment.setSgst("Y");
					} else {
						assessment.setIgst("Y");
						assessment.setCgst("N");
						assessment.setSgst("N");
					}

				} else {
					assessment.setIgst("N");
					assessment.setCgst("N");
					assessment.setSgst("N");
				}

				assessment.setContainerHandlingAmt(BigDecimal.ZERO);
				assessment.setContainerStorageAmt(BigDecimal.ZERO);
				assessmentsheetrepo.save(assessment);

				processnextidrepo.updateAuditTrail(cid, bid, "P05113", HoldNextIdD1, "2024");

			} else {
				List<AssessmentSheet> existingData = assessmentsheetrepo.getDataByAssessmentId(cid, bid,
						assessment.getAssesmentId());

				if (existingData.isEmpty()) {
					return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
				} else {
					existingData.stream().forEach(c -> {
						c.setCreditType(assessment.getCreditType());
						c.setComments(assessment.getComments());
						c.setIntComments(assessment.getIntComments());
						assessmentsheetrepo.save(c);
					});
				}
			}

			AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
			serviceData.stream().forEach(c -> {
				if (c.getAssesmentId() == null || c.getAssesmentId().isEmpty()) {
					Cfinvsrvanx anx = new Cfinvsrvanx();
					anx.setCompanyId(cid);
					anx.setBranchId(bid);
					anx.setErpDocRefNo("");
					anx.setLineNo("1");
					anx.setProcessTransId(assessment.getAssesmentId());
					anx.setServiceId(c.getServiceId());
					anx.setSrlNo(srNo1.get());
					anx.setDocRefNo("");
					anx.setIgmLineNo("");
					anx.setProfitcentreId(assessment.getProfitcentreId());
					anx.setInvoiceType("PD");
					anx.setServiceUnit(c.getServiceUnit());
					anx.setExecutionUnit(c.getExecutionUnit());
					anx.setServiceUnit1(c.getServiceUnit1());
					anx.setExecutionUnit1(c.getExecutionUnit1());
					anx.setRate(c.getRate());
					anx.setActualNoOfPackages(c.getTotalRate());
					anx.setCurrencyId("INR");
					anx.setLocalAmt(c.getTotalRate());
					anx.setInvoiceAmt(c.getTotalRate());
					anx.setCommodityDescription("");
					anx.setDiscPercentage(BigDecimal.ZERO);
					anx.setDiscValue(BigDecimal.ZERO);
					anx.setmPercentage(BigDecimal.ZERO);
					anx.setmAmount(BigDecimal.ZERO);
					anx.setAcCode(c.getSacCode());
					anx.setProcessTransDate(assessment.getAssesmentDate());
					anx.setProcessId("P01109");
					anx.setPartyId(assessment.getPartyId());
					anx.setWoNo(c.getTariffNo());
					anx.setWoAmndNo(c.getAmdNo());
					anx.setContainerNo("");
					anx.setContainerStatus("");
					anx.setAddServices("N");
					anx.setStatus("A");
					anx.setCreatedBy(user);
					anx.setCreatedDate(new Date());
					anx.setTaxApp(String.valueOf(assessment.getTaxApplicable()));
					anx.setSrvManualFlag("N");
					anx.setCriteria("");
					anx.setRangeFrom(BigDecimal.ZERO);
					anx.setRangeTo(BigDecimal.ZERO);
					anx.setRangeType("NA");
					anx.setTaxId(c.getTaxId() == null ? "" : c.getTaxId());
					anx.setExRate(BigDecimal.ZERO);
					anx.setFreeDays(BigDecimal.ZERO);
					if (("Y".equals(assessment.getIgst()))
							|| ("Y".equals(assessment.getCgst()) && "Y".equals(assessment.getSgst()))) {
						anx.setTaxPerc(new BigDecimal(c.getTaxPerc()));
					} else {
						anx.setTaxPerc(BigDecimal.ZERO);
					}
					cfinvsrvanxrepo.save(anx);
				} else {
					List<Cfinvsrvanx> existingData = cfinvsrvanxrepo.getDataByProcessTransIdAndServiceId(cid, bid,
							c.getAssesmentId(), c.getServiceId());

					if (!existingData.isEmpty()) {
						existingData.stream().forEach(e -> {
							e.setAcCode(c.getSacCode());
							e.setExecutionUnit(c.getExecutionUnit());
							e.setExecutionUnit1(c.getExecutionUnit1());
							e.setRate(c.getRate());
							e.setActualNoOfPackages(c.getTotalRate());
							e.setLocalAmt(c.getTotalRate());
							e.setInvoiceAmt(c.getTotalRate());
							e.setTaxId(c.getTaxId() == null ? "" : c.getTaxId());
							if (("Y".equals(assessment.getIgst()))
									|| ("Y".equals(assessment.getCgst()) && "Y".equals(assessment.getSgst()))) {
								e.setTaxPerc(new BigDecimal(c.getTaxPerc()));
							} else {
								e.setTaxPerc(BigDecimal.ZERO);
							}
							e.setEditedBy(user);
							e.setEditedDate(new Date());

							cfinvsrvanxrepo.save(e);
						});
					}
				}

				srNo1.set(srNo1.get().add(new BigDecimal(1)));
			});

			List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assessment.getAssesmentId());

	
			if (result.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				result.stream().forEach(r -> {
					System.out.println("String.valueOf(r[51]) "+String.valueOf(r[51])+" "+String.valueOf(r[46]));
					BigDecimal rate = new BigDecimal(String.valueOf(r[52]));
					BigDecimal taxPerc = new BigDecimal(String.valueOf(r[46]));

					BigDecimal taxAmt = (rate.multiply(taxPerc)).divide(new BigDecimal(100), RoundingMode.HALF_UP);
					totalRateWithoutTax.set(totalRateWithoutTax.get().add(rate));
					totalRateWithTax.set(totalRateWithTax.get().add(rate.add(taxAmt)));
				});

				// Get the final totals
				BigDecimal finaltotalRateWithoutTax = BigDecimal.ZERO;
				BigDecimal finaltotalRateWithTax = BigDecimal.ZERO;

				String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

				if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
					finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(3, RoundingMode.HALF_UP);
					finaltotalRateWithTax = totalRateWithTax.get().setScale(3, RoundingMode.HALF_UP);
				} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
					finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(0, RoundingMode.HALF_UP);
					finaltotalRateWithTax = totalRateWithTax.get().setScale(0, RoundingMode.HALF_UP);
				} else {
					finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(3, RoundingMode.HALF_UP);
					finaltotalRateWithTax = totalRateWithTax.get().setScale(3, RoundingMode.HALF_UP);
				}

				String pId = assessment.getOthPartyId();

				Party p = partyRepository.getDataById(cid, bid, pId);

				Map<String, Object> finalResult = new HashMap<>();
				finalResult.put("result", result);
				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

				if (p != null) {

					if (assessment.getCreditType() == 'P') {
						Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, pId, "AD");

						finalResult.put("advanceData", advanceData);
					}

					BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
					BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

					BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

					System.out.println("allowedValue " + credit1 + " " + credit2 + " " + allowedValue);

					finalResult.put("tanNo", p.getTanNoId());
					finalResult.put("tdsPerc", p.getTdsPercentage());
					finalResult.put("creditAllowed", allowedValue);
				}
				return new ResponseEntity<>(finalResult, HttpStatus.OK);
			}

			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Transactional
	public ResponseEntity<?> saveMISCInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<MiscDto> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<MiscDto>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheet> oldAssessmentData = assessmentsheetrepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<Cfinvsrvanx> oldAnxData = cfinvsrvanxrepo.getDataByProcessTransId(cid, bid, assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05115";
		} else {
			processNextId = "P05114";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

		oldAssessmentData.stream().forEach(o -> {

			Cfinvsrvanxback back = new Cfinvsrvanxback();
			back.setCompanyId(cid);
			back.setBranchId(bid);
			back.setStatus("A");
			back.setProcessTransId(o.getAssesmentId());
			back.setErpDocRefNo(o.getIgmTransId());
			back.setDocRefNo(o.getIgmNo());
			back.setIgmLineNo(o.getIgmLineNo());
			back.setServiceId(oldAnxData.get(0).getServiceId());
			back.setTaxId(oldAnxData.get(0).getTaxId());
			back.setSrlNo(srNo.get());
			back.setProfitCentreId(o.getProfitcentreId());
			back.setInvoiceNo(HoldNextIdD1);
			back.setInvoiceDate(new Date());
			back.setInvoiceType("MISC");
			back.setServiceUnit(oldAnxData.get(0).getServiceUnit());
			back.setExecutionUnit(oldAnxData.get(0).getExecutionUnit());
			back.setServiceUnit1(oldAnxData.get(0).getServiceUnit1());
			back.setExecutionUnit1(oldAnxData.get(0).getExecutionUnit1());
			back.setRate(oldAnxData.get(0).getRate());
			back.setCurrencyId(oldAnxData.get(0).getCurrencyId());
			back.setExRate(oldAnxData.get(0).getExRate());
			back.setLocalAmt(oldAnxData.get(0).getActualNoOfPackages());
			back.setTaxPerc(oldAnxData.get(0).getTaxPerc());
			back.setDiscPercentage(oldAnxData.get(0).getDiscPercentage());
			back.setDiscValue(oldAnxData.get(0).getDiscValue());
			back.setInvoiceAmt(oldAnxData.get(0).getTaxAmt());
			back.setAcCode(oldAnxData.get(0).getAcCode());
			back.setProcessTransDate(o.getAssesmentDate());
			back.setProcessId(oldAnxData.get(0).getProcessId());
			back.setPartyId(oldAnxData.get(0).getPartyId());
			back.setWoNo(oldAnxData.get(0).getWoNo());
			back.setWoAmndNo(oldAnxData.get(0).getWoAmndNo());
			back.setContainerNo(o.getContainerNo());
			back.setContainerStatus(oldAnxData.get(0).getContainerStatus());
			back.setActualNoOfPackages(oldAnxData.get(0).getActualNoOfPackages());
			back.setAddServices("N");
			back.setGateOutDate(o.getGateOutDate());
			back.setInvoiceUptoDate(oldAnxData.get(0).getInvoiceUptoDate());
			back.setCreatedBy(user);
			back.setCreatedDate(new Date());
			back.setApprovedBy(user);
			back.setApprovedDate(new Date());
			back.setBillAmt(oldAnxData.get(0).getActualNoOfPackages());
			back.setTaxApp(oldAnxData.get(0).getTaxApp());
			back.setTaxPercN(oldAnxData.get(0).getTaxPerc());

			BigDecimal taxAmt = (oldAnxData.get(0).getActualNoOfPackages().multiply(oldAnxData.get(0).getTaxPerc()))
					.divide(new BigDecimal(100), RoundingMode.HALF_UP);

			back.setTaxAmt(taxAmt);

			invsrvbackrepo.save(back);

			srNo.set(srNo.get().add(new BigDecimal(1)));
		});

		oldAnxData.stream().forEach(o -> {
			o.setApprovedBy(user);
			o.setApprovedDate(new Date());

			BigDecimal discAmt = BigDecimal.ZERO;

			if (o.getDiscValue() != null) {
				discAmt = o.getDiscValue();
			} else if (o.getmAmount() != null) {
				discAmt = o.getmAmount();
			} else if (o.getDiscPercentage() != null) {
				discAmt = ((o.getActualNoOfPackages().multiply(o.getDiscPercentage())).divide(new BigDecimal(100)))
						.setScale(3, RoundingMode.HALF_UP);

			} else if (o.getmPercentage() != null) {
				discAmt = ((o.getActualNoOfPackages().multiply(o.getmPercentage())).divide(new BigDecimal(100)))
						.setScale(3, RoundingMode.HALF_UP);

			}

			if (!"INR".equals(o.getCurrencyId())) {

				BigDecimal local = (o.getActualNoOfPackages().subtract(discAmt)).setScale(3, RoundingMode.HALF_UP);
				o.setForeignAmt((local.divide(o.getExRate())).setScale(3, RoundingMode.HALF_UP));
			}
			o.setBillAmt(o.getActualNoOfPackages());
			o.setTaxIdN(o.getTaxId());
			o.setTaxPercN(o.getTaxPerc());

			BigDecimal taxAmt = (o.getActualNoOfPackages().multiply(o.getTaxPerc())).divide(new BigDecimal(100),
					RoundingMode.HALF_UP);

			o.setTaxAmt(taxAmt);
			o.setInvoiceNo(HoldNextIdD1);
			o.setInvoiceDate(new Date());
			o.setInvoiceAmt((o.getActualNoOfPackages().add(taxAmt)).setScale(3, RoundingMode.HALF_UP));
			o.setAcCodeN(o.getAcCode());

			cfinvsrvanxrepo.save(o);

		});

		List<Cfinvsrvanx> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, Cfinvsrvanx> groupedData = newAnxData.stream().collect(Collectors.toMap(Cfinvsrvanx::getServiceId, // Key:
																														// serviceId
				c -> c, // Value: current record (initial)
				(c1, c2) -> { // Merge function for duplicates
					// Summing the required fields
					c1.setLocalAmt((c1.getLocalAmt().add(c2.getLocalAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setBillAmt((c1.getBillAmt().add(c2.getBillAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setInvoiceAmt((c1.getInvoiceAmt().add(c2.getInvoiceAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setTaxAmt((c1.getTaxAmt().add(c2.getTaxAmt())).setScale(3, RoundingMode.HALF_UP));
					return c1; // Retain the first record, updated with summed fields
				}));

		// Converting the grouped data to a list
		List<Cfinvsrvanx> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			Cfinvsrvdtl dtl = new Cfinvsrvdtl();
			dtl.setCompanyId(cid);
			dtl.setBranchId(bid);
			dtl.setInvoiceNo(HoldNextIdD1);
			dtl.setPartyId(c.getPartyId());
			dtl.setServiceId(c.getServiceId());
			dtl.setTaxId(c.getTaxId());
			dtl.setInvoiceDate(new Date());
			dtl.setProfitCentreId(c.getProfitcentreId());
			dtl.setWoNo(c.getWoNo());
			dtl.setWoAmndNo(c.getWoAmndNo());
			dtl.setErpDocRefNo(c.getErpDocRefNo());
			dtl.setDocRefNo(c.getDocRefNo());
			dtl.setForeignAmt(c.getForeignAmt());
			dtl.setExRate(c.getExRate());
			dtl.setLocalAmt(c.getLocalAmt());
			dtl.setBillAmt(c.getBillAmt());
			dtl.setInvoiceAmt(c.getInvoiceAmt());
			dtl.setAcCode(c.getAcCode());
			dtl.setStatus("A");
			dtl.setCreatedBy(user);
			dtl.setCreatedDate(new Date());
			dtl.setApprovedBy(user);
			dtl.setApprovedDate(new Date());
			dtl.setTaxApp(c.getTaxApp());
			dtl.setTaxIdN(c.getTaxIdN());
			dtl.setTaxPercN(c.getTaxPercN());
			dtl.setTaxAmt(c.getTaxAmt());
			dtl.setAcCodeN(c.getAcCodeN());

			invsrvdtlRepo.save(dtl);
		});

		AssessmentSheet assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05094", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		Cfinvsrv srv = new Cfinvsrv();
		srv.setCompanyId(cid);
		srv.setBranchId(bid);
		srv.setInvoiceNo(HoldNextIdD1);
		srv.setPartyId(assSheet.getPartyId());
		srv.setAccSrNo(assSheet.getPartySrNo().intValue());
		srv.setProfitcentreId(assSheet.getProfitcentreId());
		srv.setInvoiceDate(new Date());
		srv.setContainerNo(assSheet.getAssesmentId());
		srv.setWoNo(consolidatedData.get(0).getWoNo());
		srv.setWoAmndNo(consolidatedData.get(0).getWoAmndNo());
		srv.setPartyType(assSheet.getBillingParty());
		srv.setInvoiceDueDate(consolidatedData.get(0).getInvoiceUptoDate());
		srv.setInvoiceType("MISC");
		srv.setTransType("FCL".equals(consolidatedData.get(0).getContainerStatus()) ? "CONT" : "CRG");
		srv.setErpDocRefNo(assSheet.getIgmTransId());
		srv.setDocRefNo(assSheet.getIgmNo());
		srv.setIgmLineNo(assSheet.getIgmLineNo());
		srv.setForeignAmt(totalForAmt);
		srv.setExRate(consolidatedData.get(0).getExRate());
		srv.setLocalAmt(totalLocalAmt);
		srv.setBillAmt(totalBillAmt);
		srv.setInvoiceAmt(totalInvAmt);
		srv.setAcCode(consolidatedData.get(0).getAcCode());
		srv.setReceiptTransId(HoldNextIdD2);
		srv.setReceiptTransDate(new Date());
		srv.setFinTransId(HoldNextIdD2);
		srv.setFinTransDate(new Date());
		srv.setReceiptAmt(totalInvAmt);
		srv.setCreditType(String.valueOf(assSheet.getCreditType()));
		srv.setBillingParty(assSheet.getBillingParty());
		srv.setImporterId(assSheet.getImporterId());
		srv.setImpSrNo(assSheet.getImpSrNo());
		srv.setIgst(assSheet.getIgst());
		srv.setCgst(assSheet.getCgst());
		srv.setSgst(assSheet.getSgst());
		srv.setStatus("A");
		srv.setCreatedBy(user);
		srv.setCreatedDate(new Date());
		srv.setApprovedBy(user);
		srv.setApprovedDate(new Date());
		srv.setPayableParty(assSheet.getBillingParty());

		invsrvRepo.save(srv);

		processnextidrepo.updateAuditTrail(cid, bid, processNextId, HoldNextIdD1, "2024");
		processnextidrepo.updateAuditTrail(cid, bid, "P05094", HoldNextIdD2, "2024");

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTrans fin = new FinTrans();

			fin.setCompanyId(cid);
			fin.setBranchId(bid);
			fin.setTransId(HoldNextIdD2);
			fin.setLineId(srNo1.get());
			fin.setOprInvoiceNo(HoldNextIdD1);
			fin.setPaymentMode(p.getPayMode());
			fin.setLedgerType("AR");
			fin.setDocType("RE");
			fin.setProfitcentreId(assSheet.getProfitcentreId());
			fin.setCreditFlag(String.valueOf(assSheet.getCreditType()));
			fin.setPartyId(assSheet.getPayablePartyId());
			fin.setAccSrNo(assSheet.getPartySrNo().intValue());
			fin.setAcCode(consolidatedData.get(0).getAcCode());
			fin.setChequeNo(p.getChequeNo());
			fin.setChequeDate(p.getChequeDate());
			fin.setBankName(p.getBankDetails());
			fin.setDocumentAmt(p.getAmount());
			fin.setCreatedBy(user);
			fin.setCreatedDate(new Date());
			fin.setApprovedBy(user);
			fin.setApprovedDate(new Date());
			fin.setBillingParty(assSheet.getBillingParty());
			fin.setImporterId(assSheet.getImporterId());
			fin.setImpSrNo(assSheet.getImpSrNo());
			fin.setStatus("A");
			fin.setCreatedBy(user);
			fin.setCreatedDate(new Date());
			fin.setApprovedBy(user);
			fin.setApprovedDate(new Date());
			fin.setAssesmentId(assSheet.getAssesmentId());
			fin.setRecordType("NEW");
			fin.setCreditType("N");
			fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");

			if ("TDS".equals(p.getPayMode())) {
				fin.setTdsType(tdsDeductee);
				fin.setTdsPercentage(new BigDecimal(tdsPerc));
				fin.setTdsBillAmt(p.getAmount());
			}

			finTransrepo.save(fin);

			srNo1.set(srNo1.get().add(new BigDecimal(1)));
		});


		AtomicReference<BigDecimal> invAmt = new AtomicReference<BigDecimal>();

		invAmt.set(totalInvAmt);

		AtomicReference<BigDecimal> billAmt = new AtomicReference<BigDecimal>();

		billAmt.set(totalBillAmt);

		oldAssessmentData.stream().forEach(o -> {
			o.setTds(tdsDeductee);
			if (tdsDeductee == null || tdsDeductee.isEmpty()) {

				o.setTdsDeductee("");

			} else {

				o.setTdsDeductee("CHA".equals(tdsDeductee) ? o.getCha()
						: "IMP".equals(tdsDeductee) ? o.getImporterId()
								: "FWR".equals(tdsDeductee) ? o.getOnAccountOf() : o.getOthPartyId());
			}
			o.setBillAmt(billAmt.get());
			o.setReceiptNo(HoldNextIdD2);
			o.setReceiptDate(new Date());
			o.setIntComments(assessment.getIntComments());
			o.setFinalInvoiceDate(new Date());
			o.setInvoiceNo(HoldNextIdD1);
			o.setInvoiceAmt(invAmt.get());
			o.setComments(assessment.getComments());
			o.setInvoiceDate(new Date());
			o.setTds(tdsDeductee);

			assessmentsheetrepo.save(o);

			

		});


		List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = invsrvRepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = finTransrepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1,
				existingSrv.getProfitcentreId());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())) // Filter by
																											// payment
																											// mode
				.findFirst() // Get the first matching element
				.orElse(null); // Return null if no match is found

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");

			Party p = partyRepository.getDataById(cid, bid, existingSrv.getPartyId());

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				finalResult.put("creditAllowed", allowedValue);
			}
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = assSheet.getCha();

			} else if ("IMP".equals(singleTransData.getTdsType())) {
				pId = assSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOthPartyId();
			}

			Party p = partyRepository.getDataById(cid, bid, pId);

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
				finalResult.put("tanNo", p.getTanNoId());
				finalResult.put("creditAllowed", allowedValue);
			}

		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}
	
	
	@Transactional
	public ResponseEntity<?> saveImportInvoiceCreditReceipt(String cid, String bid, String user,
			Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<MiscDto> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<MiscDto>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheet> oldAssessmentData = assessmentsheetrepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<Cfinvsrvanx> oldAnxData = cfinvsrvanxrepo.getDataByProcessTransId(cid, bid, assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05115";
		} else {
			processNextId = "P05114";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

		oldAssessmentData.stream().forEach(o -> {

			Cfinvsrvanxback back = new Cfinvsrvanxback();
			back.setCompanyId(cid);
			back.setBranchId(bid);
			back.setStatus("A");
			back.setProcessTransId(o.getAssesmentId());
			back.setErpDocRefNo(o.getIgmTransId());
			back.setDocRefNo(o.getIgmNo());
			back.setIgmLineNo(o.getIgmLineNo());
			back.setServiceId(oldAnxData.get(0).getServiceId());
			back.setTaxId(oldAnxData.get(0).getTaxId());
			back.setSrlNo(srNo.get());
			back.setProfitCentreId(o.getProfitcentreId());
			back.setInvoiceNo(HoldNextIdD1);
			back.setInvoiceDate(new Date());
			back.setInvoiceType("MISC");
			back.setServiceUnit(oldAnxData.get(0).getServiceUnit());
			back.setExecutionUnit(oldAnxData.get(0).getExecutionUnit());
			back.setServiceUnit1(oldAnxData.get(0).getServiceUnit1());
			back.setExecutionUnit1(oldAnxData.get(0).getExecutionUnit1());
			back.setRate(oldAnxData.get(0).getRate());
			back.setCurrencyId(oldAnxData.get(0).getCurrencyId());
			back.setExRate(oldAnxData.get(0).getExRate());
			back.setLocalAmt(oldAnxData.get(0).getActualNoOfPackages());
			back.setTaxPerc(oldAnxData.get(0).getTaxPerc());
			back.setDiscPercentage(oldAnxData.get(0).getDiscPercentage());
			back.setDiscValue(oldAnxData.get(0).getDiscValue());
			back.setInvoiceAmt(oldAnxData.get(0).getTaxAmt());
			back.setAcCode(oldAnxData.get(0).getAcCode());
			back.setProcessTransDate(o.getAssesmentDate());
			back.setProcessId(oldAnxData.get(0).getProcessId());
			back.setPartyId(oldAnxData.get(0).getPartyId());
			back.setWoNo(oldAnxData.get(0).getWoNo());
			back.setWoAmndNo(oldAnxData.get(0).getWoAmndNo());
			back.setContainerNo(o.getContainerNo());
			back.setContainerStatus(oldAnxData.get(0).getContainerStatus());
			back.setActualNoOfPackages(oldAnxData.get(0).getActualNoOfPackages());
			back.setAddServices("N");
			back.setGateOutDate(o.getGateOutDate());
			back.setInvoiceUptoDate(oldAnxData.get(0).getInvoiceUptoDate());
			back.setCreatedBy(user);
			back.setCreatedDate(new Date());
			back.setApprovedBy(user);
			back.setApprovedDate(new Date());
			back.setBillAmt(oldAnxData.get(0).getActualNoOfPackages());
			back.setTaxApp(oldAnxData.get(0).getTaxApp());
			back.setTaxPercN(oldAnxData.get(0).getTaxPerc());

			BigDecimal taxAmt = (oldAnxData.get(0).getActualNoOfPackages().multiply(oldAnxData.get(0).getTaxPerc()))
					.divide(new BigDecimal(100), RoundingMode.HALF_UP);

			back.setTaxAmt(taxAmt);

			invsrvbackrepo.save(back);

			srNo.set(srNo.get().add(new BigDecimal(1)));
		});

		oldAnxData.stream().forEach(o -> {
			o.setApprovedBy(user);
			o.setApprovedDate(new Date());

			BigDecimal discAmt = BigDecimal.ZERO;

			if (o.getDiscValue() != null) {
				discAmt = o.getDiscValue();
			} else if (o.getmAmount() != null) {
				discAmt = o.getmAmount();
			} else if (o.getDiscPercentage() != null) {
				discAmt = ((o.getActualNoOfPackages().multiply(o.getDiscPercentage())).divide(new BigDecimal(100)))
						.setScale(3, RoundingMode.HALF_UP);

			} else if (o.getmPercentage() != null) {
				discAmt = ((o.getActualNoOfPackages().multiply(o.getmPercentage())).divide(new BigDecimal(100)))
						.setScale(3, RoundingMode.HALF_UP);

			}

			if (!"INR".equals(o.getCurrencyId())) {

				BigDecimal local = (o.getActualNoOfPackages().subtract(discAmt)).setScale(3, RoundingMode.HALF_UP);
				o.setForeignAmt((local.divide(o.getExRate())).setScale(3, RoundingMode.HALF_UP));
			}
			o.setBillAmt(o.getActualNoOfPackages());
			o.setTaxIdN(o.getTaxId());
			o.setTaxPercN(o.getTaxPerc());

			BigDecimal taxAmt = (o.getActualNoOfPackages().multiply(o.getTaxPerc())).divide(new BigDecimal(100),
					RoundingMode.HALF_UP);

			o.setTaxAmt(taxAmt);
			o.setInvoiceNo(HoldNextIdD1);
			o.setInvoiceDate(new Date());
			o.setInvoiceAmt((o.getActualNoOfPackages().add(taxAmt)).setScale(3, RoundingMode.HALF_UP));
			o.setAcCodeN(o.getAcCode());

			cfinvsrvanxrepo.save(o);

		});

		List<Cfinvsrvanx> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, Cfinvsrvanx> groupedData = newAnxData.stream().collect(Collectors.toMap(Cfinvsrvanx::getServiceId, // Key:
																														// serviceId
				c -> c, // Value: current record (initial)
				(c1, c2) -> { // Merge function for duplicates
					// Summing the required fields
					c1.setLocalAmt((c1.getLocalAmt().add(c2.getLocalAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setBillAmt((c1.getBillAmt().add(c2.getBillAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setInvoiceAmt((c1.getInvoiceAmt().add(c2.getInvoiceAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setTaxAmt((c1.getTaxAmt().add(c2.getTaxAmt())).setScale(3, RoundingMode.HALF_UP));
					return c1; // Retain the first record, updated with summed fields
				}));

		// Converting the grouped data to a list
		List<Cfinvsrvanx> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			Cfinvsrvdtl dtl = new Cfinvsrvdtl();
			dtl.setCompanyId(cid);
			dtl.setBranchId(bid);
			dtl.setInvoiceNo(HoldNextIdD1);
			dtl.setPartyId(c.getPartyId());
			dtl.setServiceId(c.getServiceId());
			dtl.setTaxId(c.getTaxId());
			dtl.setInvoiceDate(new Date());
			dtl.setProfitCentreId(c.getProfitcentreId());
			dtl.setWoNo(c.getWoNo());
			dtl.setWoAmndNo(c.getWoAmndNo());
			dtl.setErpDocRefNo(c.getErpDocRefNo());
			dtl.setDocRefNo(c.getDocRefNo());
			dtl.setForeignAmt(c.getForeignAmt());
			dtl.setExRate(c.getExRate());
			dtl.setLocalAmt(c.getLocalAmt());
			dtl.setBillAmt(c.getBillAmt());
			dtl.setInvoiceAmt(c.getInvoiceAmt());
			dtl.setAcCode(c.getAcCode());
			dtl.setStatus("A");
			dtl.setCreatedBy(user);
			dtl.setCreatedDate(new Date());
			dtl.setApprovedBy(user);
			dtl.setApprovedDate(new Date());
			dtl.setTaxApp(c.getTaxApp());
			dtl.setTaxIdN(c.getTaxIdN());
			dtl.setTaxPercN(c.getTaxPercN());
			dtl.setTaxAmt(c.getTaxAmt());
			dtl.setAcCodeN(c.getAcCodeN());

			invsrvdtlRepo.save(dtl);
		});

		AssessmentSheet assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05101", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		Cfinvsrv srv = new Cfinvsrv();
		srv.setCompanyId(cid);
		srv.setBranchId(bid);
		srv.setInvoiceNo(HoldNextIdD1);
		srv.setPartyId(assSheet.getPartyId());
		srv.setAccSrNo(assSheet.getPartySrNo().intValue());
		srv.setProfitcentreId(assSheet.getProfitcentreId());
		srv.setInvoiceDate(new Date());
		srv.setContainerNo(assSheet.getAssesmentId());
		srv.setWoNo(consolidatedData.get(0).getWoNo());
		srv.setWoAmndNo(consolidatedData.get(0).getWoAmndNo());
		srv.setPartyType(assSheet.getBillingParty());
		srv.setInvoiceDueDate(consolidatedData.get(0).getInvoiceUptoDate());
		srv.setInvoiceType("MISC");
		srv.setTransType("FCL".equals(consolidatedData.get(0).getContainerStatus()) ? "CONT" : "CRG");
		srv.setErpDocRefNo(assSheet.getIgmTransId());
		srv.setDocRefNo(assSheet.getIgmNo());
		srv.setIgmLineNo(assSheet.getIgmLineNo());
		srv.setForeignAmt(totalForAmt);
		srv.setExRate(consolidatedData.get(0).getExRate());
		srv.setLocalAmt(totalLocalAmt);
		srv.setBillAmt(totalBillAmt);
		srv.setInvoiceAmt(totalInvAmt);
		srv.setAcCode(consolidatedData.get(0).getAcCode());
		srv.setReceiptTransId("");
		srv.setReceiptTransDate(null);
		srv.setFinTransId(HoldNextIdD2);
		srv.setFinTransDate(new Date());
		srv.setReceiptAmt(BigDecimal.ZERO);
		srv.setCreditType(String.valueOf(assSheet.getCreditType()));
		srv.setBillingParty(assSheet.getBillingParty());
		srv.setImporterId(assSheet.getImporterId());
		srv.setImpSrNo(assSheet.getImpSrNo());
		srv.setIgst(assSheet.getIgst());
		srv.setCgst(assSheet.getCgst());
		srv.setSgst(assSheet.getSgst());
		srv.setStatus("A");
		srv.setCreatedBy(user);
		srv.setCreatedDate(new Date());
		srv.setApprovedBy(user);
		srv.setApprovedDate(new Date());
		srv.setPayableParty(assSheet.getBillingParty());

		invsrvRepo.save(srv);

		System.out.println(" processNextId, HoldNextIdD1 " + processNextId + " " + HoldNextIdD1);

		processnextidrepo.updateAuditTrail(cid, bid, processNextId, HoldNextIdD1, "2024");
		processnextidrepo.updateAuditTrail(cid, bid, "P05101", HoldNextIdD2, "2024");

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTrans fin = new FinTrans();

			if (!"CREDIT".equals(p.getPayMode())) {
				fin.setCompanyId(cid);
				fin.setBranchId(bid);
				fin.setTransId(HoldNextIdD2);
				fin.setLineId(srNo1.get());
				fin.setOprInvoiceNo(HoldNextIdD1);
				fin.setPaymentMode(p.getPayMode());
				fin.setLedgerType("AR");
				fin.setDocType("RE");
				fin.setProfitcentreId(assSheet.getProfitcentreId());
				fin.setCreditFlag(String.valueOf(assSheet.getCreditType()));
				fin.setPartyId(assSheet.getPayablePartyId());
				fin.setAccSrNo(assSheet.getPartySrNo().intValue());
				fin.setAcCode(consolidatedData.get(0).getAcCode());
				fin.setChequeNo(p.getChequeNo());
				fin.setChequeDate(p.getChequeDate());
				fin.setBankName(p.getBankDetails());
				fin.setDocumentAmt(p.getAmount());
				fin.setCreatedBy(user);
				fin.setCreatedDate(new Date());
				fin.setApprovedBy(user);
				fin.setApprovedDate(new Date());
				fin.setBillingParty(assSheet.getBillingParty());
				fin.setImporterId(assSheet.getImporterId());
				fin.setImpSrNo(assSheet.getImpSrNo());
				fin.setStatus("A");
				fin.setCreatedBy(user);
				fin.setCreatedDate(new Date());
				fin.setApprovedBy(user);
				fin.setApprovedDate(new Date());
				fin.setAssesmentId(assSheet.getAssesmentId());
				fin.setRecordType("NEW");
				fin.setCreditType("N");
				fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");

				if ("TDS".equals(p.getPayMode())) {
					fin.setTdsType(tdsDeductee);
					fin.setTdsPercentage(new BigDecimal(tdsPerc));
					fin.setTdsBillAmt(p.getAmount());
				}
			} else {
				fin.setCompanyId(cid);
				fin.setBranchId(bid);
				fin.setTransId(HoldNextIdD2);
				fin.setLineId(srNo1.get());
				fin.setOprInvoiceNo(HoldNextIdD1);
				fin.setPaymentMode(p.getPayMode());
				fin.setLedgerType("AR");
				fin.setDocType("RE");
				fin.setProfitcentreId(assSheet.getProfitcentreId());
				fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
				fin.setPartyId(assSheet.getPayablePartyId());
				fin.setAccSrNo(assSheet.getPartySrNo().intValue());
				fin.setAcCode(consolidatedData.get(0).getAcCode());
				fin.setChequeNo(p.getChequeNo());
				fin.setChequeDate(p.getChequeDate());
				fin.setBankName(p.getBankDetails());
				fin.setCreditType("Y");
				fin.setDocumentAmt(BigDecimal.ZERO);
				fin.setCreditAmount(p.getAmount().negate());
				fin.setCreatedBy(user);
				fin.setCreatedDate(new Date());
				fin.setApprovedBy(user);
				fin.setApprovedDate(new Date());
				fin.setBillingParty(assSheet.getBillingParty());
				fin.setImporterId(assSheet.getImporterId());
				fin.setImpSrNo(assSheet.getImpSrNo());
				fin.setStatus("A");
				fin.setCreatedBy(user);
				fin.setCreatedDate(new Date());
				fin.setApprovedBy(user);
				fin.setApprovedDate(new Date());
				fin.setAssesmentId(assSheet.getAssesmentId());
				fin.setRecordType("NEW");

				if ("TDS".equals(p.getPayMode())) {
					fin.setTdsType(tdsDeductee);
					fin.setTdsPercentage(new BigDecimal(tdsPerc));
					fin.setTdsBillAmt(p.getAmount());
				}
			}

			finTransrepo.save(fin);

			srNo1.set(srNo1.get().add(new BigDecimal(1)));
		});

		if (assessment.getCreditType() == 'Y') {

			BigDecimal totalCreditAmt = paymentDto.stream().filter(c -> "CREDIT".equals(c.getPayMode()))
					.map(item -> item.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add)
					.setScale(3, RoundingMode.HALF_UP);

			InvCreditTrack track = new InvCreditTrack();
			track.setCompanyId(cid);
			track.setBranchId(bid);
			track.setStatus("A");
			track.setCreatedby(user);
			track.setCreatedDate(new Date());
			track.setTransId(HoldNextIdD2);
			track.setTransDate(new Date());
			track.setInvoiceAmount(totalInvAmt);
			track.setAssesmentId(assessment.getAssesmentId());
			track.setCreditAdjustAmount(BigDecimal.ZERO);
			track.setCreditAmount(totalCreditAmt);
			track.setPartyId(assSheet.getPartyId());
			track.setInvoiceNo(HoldNextIdD1);
			track.setTdsStatus("N");
			track.setLocalAmount(totalLocalAmt);

			track.setTdsDeductee("CHA".equals(tdsDeductee) ? assSheet.getCha()
					: "IMP".equals(tdsDeductee) ? assSheet.getImporterId()
							: "FWR".equals(tdsDeductee) ? assSheet.getOnAccountOf()
									: "OTH".equals(tdsDeductee) ? assSheet.getOthPartyId() : "");

			invcredittrackrepo.save(track);

			Object checkCurrentDayData = pdahdrrepo.checkCurrentDayData(cid, bid, assSheet.getPartyId(), new Date());

			if (checkCurrentDayData == null) {
				Object checkOldDayData = pdahdrrepo.checkOldDayData(cid, bid, assSheet.getPartyId());

				if (checkOldDayData != null) {
					Object[] data1 = (Object[]) checkOldDayData;

					BigDecimal oldVal = (BigDecimal) data1[2];
					BigDecimal total = oldVal.add(totalCreditAmt.negate()).setScale(2, RoundingMode.HALF_UP);

					Pdahdr hdr = new Pdahdr();
					hdr.setCompanyId(cid);
					hdr.setBranchId(bid);
					hdr.setStatus("A");
					hdr.setCreatedBy(user);
					hdr.setTransDate(new Date());
					hdr.setPartyId(assSheet.getPartyId());
					hdr.setCreditBal(total);
					hdr.setOpeningBal(new BigDecimal(String.valueOf(data1[1])));
					hdr.setClosingBal(new BigDecimal(String.valueOf(data1[1])));

					pdahdrrepo.save(hdr);
				} else {
					Pdahdr hdr = new Pdahdr();
					hdr.setCompanyId(cid);
					hdr.setBranchId(bid);
					hdr.setStatus("A");
					hdr.setCreatedBy(user);
					hdr.setTransDate(new Date());
					hdr.setPartyId(assSheet.getPartyId());
					hdr.setCreditBal(totalCreditAmt.negate());
					hdr.setOpeningBal(BigDecimal.ZERO);
					hdr.setClosingBal(BigDecimal.ZERO);

					pdahdrrepo.save(hdr);
				}

			} else {

				Object[] data1 = (Object[]) checkCurrentDayData;
				BigDecimal oldVal = (BigDecimal) data1[2];
				BigDecimal total = oldVal.add(totalCreditAmt.negate()).setScale(2, RoundingMode.HALF_UP);

				int updateData = pdahdrrepo.updateData(cid, bid, assSheet.getPartyId(), new Date(), total);

			}

			BigDecimal sr = pdadtlrepo.maxId(cid, bid, assSheet.getPartyId());
			PdaDtl dtl = new PdaDtl();
			dtl.setCompanyId(cid);
			dtl.setBranchId(bid);
			dtl.setStatus("A");
			dtl.setCreatedBy(user);
			dtl.setCreatedDate(new Date());
			dtl.setCreditFlag("Y");
			dtl.setCreditAmount(totalCreditAmt.negate());
			dtl.setCreditAdjustFlag("N");
			dtl.setPartyId(assSheet.getPartyId());
			dtl.setTransDate(new Date());
			dtl.setSrNo(sr.add(new BigDecimal(1)));
			dtl.setAdvanceAmount(BigDecimal.ZERO);
			dtl.setInvoiceNo(HoldNextIdD1);

			pdadtlrepo.save(dtl);

			Party p = partyRepository.getDataById(cid, bid, assSheet.getPartyId());

			if (p != null) {
				p.setCrAmtLmtUse(
						(p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse()).add(totalCreditAmt));

				partyRepository.save(p);
				;
			}

		}


		AtomicReference<BigDecimal> invAmt = new AtomicReference<BigDecimal>();

		invAmt.set(totalInvAmt);

		AtomicReference<BigDecimal> billAmt = new AtomicReference<BigDecimal>();

		billAmt.set(totalBillAmt);

		oldAssessmentData.stream().forEach(o -> {
			o.setTds(tdsDeductee);
			if (tdsDeductee == null || tdsDeductee.isEmpty()) {

				o.setTdsDeductee("");

			} else {

				o.setTdsDeductee("CHA".equals(tdsDeductee) ? o.getCha()
						: "IMP".equals(tdsDeductee) ? o.getImporterId()
								: "FWR".equals(tdsDeductee) ? o.getOnAccountOf() : o.getOthPartyId());
			}
			o.setBillAmt(billAmt.get());
			o.setReceiptNo(HoldNextIdD2);
			o.setReceiptDate(new Date());
			o.setIntComments(assessment.getIntComments());
			o.setFinalInvoiceDate(new Date());
			o.setInvoiceNo(HoldNextIdD1);
			o.setInvoiceAmt(invAmt.get());
			o.setComments(assessment.getComments());
			o.setInvoiceDate(new Date());
			o.setTds(tdsDeductee);

			assessmentsheetrepo.save(o);

			

		});


		List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = invsrvRepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = finTransrepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1,
				existingSrv.getProfitcentreId());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())) // Filter by
																											// payment
																											// mode
				.findFirst() // Get the first matching element
				.orElse(null); // Return null if no match is found

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");

			Party p = partyRepository.getDataById(cid, bid, existingSrv.getPartyId());

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				finalResult.put("creditAllowed", allowedValue);
			}
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = assSheet.getCha();

			} else if ("IMP".equals(singleTransData.getTdsType())) {
				pId = assSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOthPartyId();
			}

			Party p = partyRepository.getDataById(cid, bid, pId);

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				System.out.println("allowedValue " + credit1 + " " + credit2 + " " + allowedValue);

				finalResult.put("tanNo", p.getTanNoId());
				finalResult.put("creditAllowed", allowedValue);
			}

		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}

	@Transactional
	public ResponseEntity<?> saveImportInvoicePdaReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<MiscDto> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<MiscDto>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheet> oldAssessmentData = assessmentsheetrepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<Cfinvsrvanx> oldAnxData = cfinvsrvanxrepo.getDataByProcessTransId(cid, bid, assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05115";
		} else {
			processNextId = "P05114";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

		oldAssessmentData.stream().forEach(o -> {

			Cfinvsrvanxback back = new Cfinvsrvanxback();
			back.setCompanyId(cid);
			back.setBranchId(bid);
			back.setStatus("A");
			back.setProcessTransId(o.getAssesmentId());
			back.setErpDocRefNo(o.getIgmTransId());
			back.setDocRefNo(o.getIgmNo());
			back.setIgmLineNo(o.getIgmLineNo());
			back.setServiceId(oldAnxData.get(0).getServiceId());
			back.setTaxId(oldAnxData.get(0).getTaxId());
			back.setSrlNo(srNo.get());
			back.setProfitCentreId(o.getProfitcentreId());
			back.setInvoiceNo(HoldNextIdD1);
			back.setInvoiceDate(new Date());
			back.setInvoiceType("MISC");
			back.setServiceUnit(oldAnxData.get(0).getServiceUnit());
			back.setExecutionUnit(oldAnxData.get(0).getExecutionUnit());
			back.setServiceUnit1(oldAnxData.get(0).getServiceUnit1());
			back.setExecutionUnit1(oldAnxData.get(0).getExecutionUnit1());
			back.setRate(oldAnxData.get(0).getRate());
			back.setCurrencyId(oldAnxData.get(0).getCurrencyId());
			back.setExRate(oldAnxData.get(0).getExRate());
			back.setLocalAmt(oldAnxData.get(0).getActualNoOfPackages());
			back.setTaxPerc(oldAnxData.get(0).getTaxPerc());
			back.setDiscPercentage(oldAnxData.get(0).getDiscPercentage());
			back.setDiscValue(oldAnxData.get(0).getDiscValue());
			back.setInvoiceAmt(oldAnxData.get(0).getTaxAmt());
			back.setAcCode(oldAnxData.get(0).getAcCode());
			back.setProcessTransDate(o.getAssesmentDate());
			back.setProcessId(oldAnxData.get(0).getProcessId());
			back.setPartyId(oldAnxData.get(0).getPartyId());
			back.setWoNo(oldAnxData.get(0).getWoNo());
			back.setWoAmndNo(oldAnxData.get(0).getWoAmndNo());
			back.setContainerNo(o.getContainerNo());
			back.setContainerStatus(oldAnxData.get(0).getContainerStatus());
			back.setActualNoOfPackages(oldAnxData.get(0).getActualNoOfPackages());
			back.setAddServices("N");
			back.setGateOutDate(o.getGateOutDate());
			back.setInvoiceUptoDate(oldAnxData.get(0).getInvoiceUptoDate());
			back.setCreatedBy(user);
			back.setCreatedDate(new Date());
			back.setApprovedBy(user);
			back.setApprovedDate(new Date());
			back.setBillAmt(oldAnxData.get(0).getActualNoOfPackages());
			back.setTaxApp(oldAnxData.get(0).getTaxApp());
			back.setTaxPercN(oldAnxData.get(0).getTaxPerc());

			BigDecimal taxAmt = (oldAnxData.get(0).getActualNoOfPackages().multiply(oldAnxData.get(0).getTaxPerc()))
					.divide(new BigDecimal(100), RoundingMode.HALF_UP);

			back.setTaxAmt(taxAmt);

			invsrvbackrepo.save(back);

			srNo.set(srNo.get().add(new BigDecimal(1)));
		});

		oldAnxData.stream().forEach(o -> {
			o.setApprovedBy(user);
			o.setApprovedDate(new Date());

			BigDecimal discAmt = BigDecimal.ZERO;

			if (o.getDiscValue() != null) {
				discAmt = o.getDiscValue();
			} else if (o.getmAmount() != null) {
				discAmt = o.getmAmount();
			} else if (o.getDiscPercentage() != null) {
				discAmt = ((o.getActualNoOfPackages().multiply(o.getDiscPercentage())).divide(new BigDecimal(100)))
						.setScale(3, RoundingMode.HALF_UP);

			} else if (o.getmPercentage() != null) {
				discAmt = ((o.getActualNoOfPackages().multiply(o.getmPercentage())).divide(new BigDecimal(100)))
						.setScale(3, RoundingMode.HALF_UP);

			}

			if (!"INR".equals(o.getCurrencyId())) {

				BigDecimal local = (o.getActualNoOfPackages().subtract(discAmt)).setScale(3, RoundingMode.HALF_UP);
				o.setForeignAmt((local.divide(o.getExRate())).setScale(3, RoundingMode.HALF_UP));
			}
			o.setBillAmt(o.getActualNoOfPackages());
			o.setTaxIdN(o.getTaxId());
			o.setTaxPercN(o.getTaxPerc());

			BigDecimal taxAmt = (o.getActualNoOfPackages().multiply(o.getTaxPerc())).divide(new BigDecimal(100),
					RoundingMode.HALF_UP);

			o.setTaxAmt(taxAmt);
			o.setInvoiceNo(HoldNextIdD1);
			o.setInvoiceDate(new Date());
			o.setInvoiceAmt((o.getActualNoOfPackages().add(taxAmt)).setScale(3, RoundingMode.HALF_UP));
			o.setAcCodeN(o.getAcCode());

			cfinvsrvanxrepo.save(o);

		});

		List<Cfinvsrvanx> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, Cfinvsrvanx> groupedData = newAnxData.stream().collect(Collectors.toMap(Cfinvsrvanx::getServiceId, // Key:
																														// serviceId
				c -> c, // Value: current record (initial)
				(c1, c2) -> { // Merge function for duplicates
					// Summing the required fields
					c1.setLocalAmt((c1.getLocalAmt().add(c2.getLocalAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setBillAmt((c1.getBillAmt().add(c2.getBillAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setInvoiceAmt((c1.getInvoiceAmt().add(c2.getInvoiceAmt())).setScale(3, RoundingMode.HALF_UP));
					c1.setTaxAmt((c1.getTaxAmt().add(c2.getTaxAmt())).setScale(3, RoundingMode.HALF_UP));
					return c1; // Retain the first record, updated with summed fields
				}));

		// Converting the grouped data to a list
		List<Cfinvsrvanx> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			Cfinvsrvdtl dtl = new Cfinvsrvdtl();
			dtl.setCompanyId(cid);
			dtl.setBranchId(bid);
			dtl.setInvoiceNo(HoldNextIdD1);
			dtl.setPartyId(c.getPartyId());
			dtl.setServiceId(c.getServiceId());
			dtl.setTaxId(c.getTaxId());
			dtl.setInvoiceDate(new Date());
			dtl.setProfitCentreId(c.getProfitcentreId());
			dtl.setWoNo(c.getWoNo());
			dtl.setWoAmndNo(c.getWoAmndNo());
			dtl.setErpDocRefNo(c.getErpDocRefNo());
			dtl.setDocRefNo(c.getDocRefNo());
			dtl.setForeignAmt(c.getForeignAmt());
			dtl.setExRate(c.getExRate());
			dtl.setLocalAmt(c.getLocalAmt());
			dtl.setBillAmt(c.getBillAmt());
			dtl.setInvoiceAmt(c.getInvoiceAmt());
			dtl.setAcCode(c.getAcCode());
			dtl.setStatus("A");
			dtl.setCreatedBy(user);
			dtl.setCreatedDate(new Date());
			dtl.setApprovedBy(user);
			dtl.setApprovedDate(new Date());
			dtl.setTaxApp(c.getTaxApp());
			dtl.setTaxIdN(c.getTaxIdN());
			dtl.setTaxPercN(c.getTaxPercN());
			dtl.setTaxAmt(c.getTaxAmt());
			dtl.setAcCodeN(c.getAcCodeN());

			invsrvdtlRepo.save(dtl);
		});

		AssessmentSheet assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanx::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanx::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanx::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanx::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05094", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		Cfinvsrv srv = new Cfinvsrv();
		srv.setCompanyId(cid);
		srv.setBranchId(bid);
		srv.setInvoiceNo(HoldNextIdD1);
		srv.setPartyId(assSheet.getPartyId());
		srv.setAccSrNo(assSheet.getPartySrNo().intValue());
		srv.setProfitcentreId(assSheet.getProfitcentreId());
		srv.setInvoiceDate(new Date());
		srv.setContainerNo(assSheet.getAssesmentId());
		srv.setWoNo(consolidatedData.get(0).getWoNo());
		srv.setWoAmndNo(consolidatedData.get(0).getWoAmndNo());
		srv.setPartyType(assSheet.getBillingParty());
		srv.setInvoiceDueDate(consolidatedData.get(0).getInvoiceUptoDate());
		srv.setInvoiceType("MISC");
		srv.setTransType("FCL".equals(consolidatedData.get(0).getContainerStatus()) ? "CONT" : "CRG");
		srv.setErpDocRefNo(assSheet.getIgmTransId());
		srv.setDocRefNo(assSheet.getIgmNo());
		srv.setIgmLineNo(assSheet.getIgmLineNo());
		srv.setForeignAmt(totalForAmt);
		srv.setExRate(consolidatedData.get(0).getExRate());
		srv.setLocalAmt(totalLocalAmt);
		srv.setBillAmt(totalBillAmt);
		srv.setInvoiceAmt(totalInvAmt);
		srv.setAcCode(consolidatedData.get(0).getAcCode());
		srv.setReceiptTransId(HoldNextIdD2);
		srv.setReceiptTransDate(new Date());
		srv.setFinTransId(HoldNextIdD2);
		srv.setFinTransDate(new Date());
		srv.setReceiptAmt(BigDecimal.ZERO);
		srv.setCreditType(String.valueOf(assSheet.getCreditType()));
		srv.setBillingParty(assSheet.getBillingParty());
		srv.setImporterId(assSheet.getImporterId());
		srv.setImpSrNo(assSheet.getImpSrNo());
		srv.setIgst(assSheet.getIgst());
		srv.setCgst(assSheet.getCgst());
		srv.setSgst(assSheet.getSgst());
		srv.setStatus("A");
		srv.setCreatedBy(user);
		srv.setCreatedDate(new Date());
		srv.setApprovedBy(user);
		srv.setApprovedDate(new Date());
		srv.setPayableParty(assSheet.getBillingParty());

		invsrvRepo.save(srv);

		processnextidrepo.updateAuditTrail(cid, bid, processNextId, HoldNextIdD1, "2024");
		processnextidrepo.updateAuditTrail(cid, bid, "P05094", HoldNextIdD2, "2024");

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTrans fin = new FinTrans();

			fin.setCompanyId(cid);
			fin.setBranchId(bid);
			fin.setTransId(HoldNextIdD2);
			fin.setLineId(srNo1.get());
			fin.setOprInvoiceNo(HoldNextIdD1);
			fin.setPaymentMode(p.getPayMode());
			fin.setLedgerType("AR");
			fin.setDocType("RE");
			fin.setProfitcentreId(assSheet.getProfitcentreId());
			fin.setPartyId(assSheet.getPayablePartyId());
			fin.setAccSrNo(assSheet.getPartySrNo().intValue());
			fin.setAcCode(consolidatedData.get(0).getAcCode());
			fin.setChequeNo(p.getChequeNo());
			fin.setChequeDate(p.getChequeDate());
			fin.setBankName(p.getBankDetails());
			fin.setDocumentAmt(p.getAmount());
			fin.setCreatedBy(user);
			fin.setCreatedDate(new Date());
			fin.setApprovedBy(user);
			fin.setApprovedDate(new Date());
			fin.setBillingParty(assSheet.getBillingParty());
			fin.setImporterId(assSheet.getImporterId());
			fin.setImpSrNo(assSheet.getImpSrNo());
			fin.setStatus("A");
			fin.setCreatedBy(user);
			fin.setCreatedDate(new Date());
			fin.setApprovedBy(user);
			fin.setApprovedDate(new Date());
			fin.setAssesmentId(assSheet.getAssesmentId());
			fin.setRecordType("NEW");
			fin.setCreditType("P");
			fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
			if ("TDS".equals(p.getPayMode())) {
				fin.setTdsType(tdsDeductee);
				fin.setTdsPercentage(new BigDecimal(tdsPerc));
				fin.setTdsBillAmt(p.getAmount());
			}

			finTransrepo.save(fin);

			srNo1.set(srNo1.get().add(new BigDecimal(1)));
		});

		if (assessment.getCreditType() == 'P') {

			BigDecimal totalAdvanceAmt = paymentDto.stream().filter(c -> "ADVANCE".equals(c.getPayMode()))
					.map(item -> item.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add)
					.setScale(3, RoundingMode.HALF_UP);

			Object checkCurrentDayData = pdahdrrepo.checkCurrentDayData(cid, bid, assSheet.getPartyId(), new Date());

			if (checkCurrentDayData == null) {
				Object checkOldDayData = pdahdrrepo.checkOldDayData(cid, bid, assSheet.getPartyId());

				if (checkOldDayData != null) {
					Object[] data1 = (Object[]) checkOldDayData;

					BigDecimal oldVal = (BigDecimal) data1[2];
					BigDecimal total = oldVal.add(totalAdvanceAmt).setScale(2, RoundingMode.HALF_UP);

					Pdahdr hdr = new Pdahdr();
					hdr.setCompanyId(cid);
					hdr.setBranchId(bid);
					hdr.setStatus("A");
					hdr.setCreatedBy(user);
					hdr.setTransDate(new Date());
					hdr.setPartyId(assSheet.getPartyId());
					hdr.setCreditBal(total);
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
					hdr.setPartyId(assSheet.getPartyId());
					hdr.setCreditBal(BigDecimal.ZERO);
					hdr.setOpeningBal(BigDecimal.ZERO);
					hdr.setClosingBal(totalAdvanceAmt);

					pdahdrrepo.save(hdr);
				}

			} else {

				Object[] data1 = (Object[]) checkCurrentDayData;
				BigDecimal oldVal = (BigDecimal) data1[2];
				BigDecimal total = oldVal.add(totalAdvanceAmt).setScale(2, RoundingMode.HALF_UP);

				int updateData = pdahdrrepo.updateData1(cid, bid, assSheet.getPartyId(), new Date(), total);

			}

			BigDecimal sr = pdadtlrepo.maxId(cid, bid, assSheet.getPartyId());
			PdaDtl dtl = new PdaDtl();
			dtl.setCompanyId(cid);
			dtl.setBranchId(bid);
			dtl.setStatus("A");
			dtl.setCreatedBy(user);
			dtl.setCreatedDate(new Date());
			dtl.setCreditFlag("N");
			dtl.setCreditAmount(BigDecimal.ZERO);
			dtl.setCreditAdjustFlag("N");
			dtl.setPartyId(assSheet.getPartyId());
			dtl.setTransDate(new Date());
			dtl.setSrNo(sr.add(new BigDecimal(1)));
			dtl.setAdvanceAmount(totalAdvanceAmt);
			dtl.setInvoiceNo(HoldNextIdD1);

			pdadtlrepo.save(dtl);

			AtomicReference<BigDecimal> totalAmtWtTds1 = new AtomicReference<>(totalAdvanceAmt);
			List<FinTrans> advanceRecords = finTransrepo.getAdvanceRecords(cid, bid, assSheet.getPartyId(), "AD");

			if (!advanceRecords.isEmpty()) {
				for (FinTrans f : advanceRecords) {
					if (totalAmtWtTds1.get().compareTo(BigDecimal.ZERO) <= 0) {
						break; // Stop processing if totalAmtWtTds1 is zero
					}

					BigDecimal clearedAmt = (f.getClearedAmt() == null) ? BigDecimal.ZERO : f.getClearedAmt();
					BigDecimal remainingAmt = f.getDocumentAmt().subtract(clearedAmt);

					if (totalAmtWtTds1.get().compareTo(remainingAmt) <= 0) {
						f.setClearedAmt(clearedAmt.add(totalAmtWtTds1.get()));
						totalAmtWtTds1.set(BigDecimal.ZERO); // Fully utilized
					} else {
						f.setClearedAmt(clearedAmt.add(remainingAmt));
						totalAmtWtTds1.set(totalAmtWtTds1.get().subtract(remainingAmt));
					}

					finTransrepo.save(f);
				}
			}

		}

//		int updateIgmCrg = cfigmcrgrepo.updateAssessmentData(cid, bid, assessment.getIgmNo(), assessment.getIgmTransId()
//				, assessment.getIgmLineNo(), "Y");

		AtomicReference<BigDecimal> invAmt = new AtomicReference<BigDecimal>();

		invAmt.set(totalInvAmt);

		AtomicReference<BigDecimal> billAmt = new AtomicReference<BigDecimal>();

		billAmt.set(totalBillAmt);

		oldAssessmentData.stream().forEach(o -> {
			o.setTds(tdsDeductee);
			if (tdsDeductee == null || tdsDeductee.isEmpty()) {

				o.setTdsDeductee("");

			} else {

				o.setTdsDeductee("CHA".equals(tdsDeductee) ? o.getCha()
						: "IMP".equals(tdsDeductee) ? o.getImporterId()
								: "FWR".equals(tdsDeductee) ? o.getOnAccountOf() : o.getOthPartyId());
			}
			o.setBillAmt(billAmt.get());
			o.setReceiptNo(HoldNextIdD2);
			o.setReceiptDate(new Date());
			o.setIntComments(assessment.getIntComments());
			o.setFinalInvoiceDate(new Date());
			o.setInvoiceNo(HoldNextIdD1);
			o.setInvoiceAmt(invAmt.get());
			o.setComments(assessment.getComments());
			o.setInvoiceDate(new Date());
			o.setTds(tdsDeductee);

			assessmentsheetrepo.save(o);

			

		});


		List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = invsrvRepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = finTransrepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1,
				existingSrv.getProfitcentreId());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())) // Filter by
																											// payment
																											// mode
				.findFirst() // Get the first matching element
				.orElse(null); // Return null if no match is found

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		if (assessment.getCreditType() == 'P') {
			Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, existingSrv.getPartyId(), "AD");

			finalResult.put("advanceData", advanceData);
		}

		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");

			Party p = partyRepository.getDataById(cid, bid, existingSrv.getPartyId());

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				finalResult.put("creditAllowed", allowedValue);
			}
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = assSheet.getCha();

			} else if ("IMP".equals(singleTransData.getTdsType())) {
				pId = assSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOthPartyId();
			}

			Party p = partyRepository.getDataById(cid, bid, pId);

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				System.out.println("allowedValue " + credit1 + " " + credit2 + " " + allowedValue);

				finalResult.put("tanNo", p.getTanNoId());
				finalResult.put("creditAllowed", allowedValue);
			}

		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}
	
	
	
	public ResponseEntity<?> searchMISCInvoiceData1(String cid, String bid, String val) {

	
		List<Object[]> data = assessmentsheetrepo.searchMISCInvoiceData1(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<?> getSelectedMISCInvoiceData1(String cid, String bid, String assId, String invoiceId) {

		List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assId);

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = invsrvRepo.getDataByInvoiceNo(cid, bid, invoiceId);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = finTransrepo.getDataByInvoiceNo(cid, bid, invoiceId,
				existingSrv.getProfitcentreId());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())) // Filter by
																											// payment
																											// mode
				.findFirst() // Get the first matching element
				.orElse(null); // Return null if no match is found

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, existingSrv.getPartyId(), "AD");

		finalResult.put("advanceData", advanceData);

		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");

			Party p = partyRepository.getDataById(cid, bid, existingSrv.getPartyId());

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				finalResult.put("creditAllowed", allowedValue);
			}
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";
			Object[] row = result.get(0);

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = row[14].toString();

			} else if ("IMP".equals(singleTransData.getTdsType())) {
				pId = row[11].toString();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = row[23].toString();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = row[29].toString();
			}

			Party p = partyRepository.getDataById(cid, bid, pId);

			if (p != null) {

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				System.out.println("allowedValue " + credit1 + " " + credit2 + " " + allowedValue);

				finalResult.put("tanNo", p.getTanNoId());
				finalResult.put("creditAllowed", allowedValue);
			}

		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);
	}

}
