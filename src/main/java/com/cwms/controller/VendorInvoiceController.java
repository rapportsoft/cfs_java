package com.cwms.controller;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.AssessmentSheetAP;
import com.cwms.entities.Branch;
import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.Cfinvsrvanx;
import com.cwms.entities.Cfinvsrvanxap;
import com.cwms.entities.Cfinvsrvap;
import com.cwms.entities.Cfinvsrvdtlap;
import com.cwms.entities.FinTrans;
import com.cwms.entities.FinTransAP;
import com.cwms.entities.FinTransInv;
import com.cwms.entities.FinTransInvAP;
import com.cwms.entities.InvCreditTrackAP;
import com.cwms.entities.MiscDto;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.entities.PaymentReceiptDTO;
import com.cwms.entities.PdaDtl;
import com.cwms.entities.Pdahdr;
import com.cwms.entities.ReceiptInvoiceDto;
import com.cwms.repository.AssessmentSheetAPRepo;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.Cfinvsrvanxaprepo;
import com.cwms.repository.Cfinvsrvaprepo;
import com.cwms.repository.Cfinvsrvdtlaprepo;
import com.cwms.repository.FinTransAPRepo;
import com.cwms.repository.FinTransInvAPRepo;
import com.cwms.repository.FinTransInvRepository;
import com.cwms.repository.InvCreditTrackAPRepo;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VendorTariffSrvRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/vendorInvoice")
@CrossOrigin("*")
@RestController
public class VendorInvoiceController {

	@Autowired
	private AssessmentSheetAPRepo assessmentsheetrepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private PartyAddressRepository partyRepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private Cfinvsrvanxaprepo cfinvsrvanxrepo;

	@Autowired
	private Cfinvsrvdtlaprepo invsrvdtlRepo;

	@Autowired
	private Cfinvsrvaprepo invsrvRepo;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private InvCreditTrackAPRepo invcredittrackrepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VendorTariffSrvRepo cfstarrifservicerepo;
	
	@Autowired
	private FinTransAPRepo fintransrepo;
	
	@Autowired
	private FinTransInvAPRepo finTransInvRepo;

	@GetMapping("getServices")
	public ResponseEntity<?> getAllServices(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> serviceList = cfstarrifservicerepo.getGeneralTarrifData2(cid, bid);
		if (serviceList.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(serviceList, HttpStatus.OK);
	}

	@Transactional
	@PostMapping("/saveData")
	public ResponseEntity<?> saveMiscellaneousData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			AssessmentSheetAP assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
					AssessmentSheetAP.class);

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
				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05117", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("VEND%06d", nextNumericNextID1);

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
				assessment.setTransType("VEN");
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

				processnextidrepo.updateAuditTrail(cid, bid, "P05117", HoldNextIdD1, "2024");

			} else {
				List<AssessmentSheetAP> existingData = assessmentsheetrepo.getDataByAssessmentId(cid, bid,
						assessment.getAssesmentId());

				if (existingData.isEmpty()) {
					return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
				} else {
					existingData.stream().forEach(c -> {
						c.setLastInvoiceNo(assessment.getLastInvoiceNo());
						c.setLastInvoiceDate(assessment.getLastInvoiceDate());
						c.setCreditType(assessment.getCreditType());
						c.setComments(assessment.getComments());
						c.setIntComments(assessment.getIntComments());
						assessmentsheetrepo.save(c);
					});
				}
			}

			serviceData.stream().forEach(c -> {
				if (c.getAssesmentId() == null || c.getAssesmentId().isEmpty()) {

					int srNo1 = cfinvsrvanxrepo.getCountByAssessmentId(cid, bid, assessment.getAssesmentId()) + 1;

					Cfinvsrvanxap anx = new Cfinvsrvanxap();
					anx.setCompanyId(cid);
					anx.setBranchId(bid);
					anx.setErpDocRefNo("");
					anx.setLineNo("1");
					anx.setProcessTransId(assessment.getAssesmentId());
					anx.setServiceId(c.getServiceId());
					anx.setSrlNo(new BigDecimal(srNo1));
					anx.setDocRefNo("");
					anx.setIgmLineNo("");
					anx.setProfitcentreId(assessment.getProfitcentreId());
					anx.setInvoiceType("AP");
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
					anx.setProcessId("P01703");
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
					if (assessment.getTaxApplicable() == 'Y') {

						anx.setTaxPerc(new BigDecimal(c.getTaxPerc()));
					} else {
						anx.setTaxPerc(BigDecimal.ZERO);
					}
					cfinvsrvanxrepo.save(anx);
				} else {
					List<Cfinvsrvanxap> existingData = cfinvsrvanxrepo.getDataByProcessTransIdAndServiceId(cid, bid,
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
							if (assessment.getTaxApplicable() == 'Y') {
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

			});

			List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assessment.getAssesmentId());

			if (result.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				result.stream().forEach(r -> {
					System.out.println("String.valueOf(r[51]) " + String.valueOf(r[51]) + " " + String.valueOf(r[46]));
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

//					if (assessment.getCreditType() == 'P') {
//						Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, pId, "AD");
//
//						finalResult.put("advanceData", advanceData);
//					}

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
	@PostMapping("/processData")
	public ResponseEntity<?> saveImportInvoiceCreditReceipt(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheetAP assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheetAP.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<MiscDto> containerData = mapper.readValue(mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<MiscDto>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheetAP> oldAssessmentData = assessmentsheetrepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05119";
		} else {
			processNextId = "P05118";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

		containerData.stream().forEach(c -> {
			if (c.getAssesmentId() == null || c.getAssesmentId().isEmpty()) {

				int srNo1 = cfinvsrvanxrepo.getCountByAssessmentId(cid, bid, assessment.getAssesmentId()) + 1;

				Cfinvsrvanxap anx = new Cfinvsrvanxap();
				anx.setCompanyId(cid);
				anx.setBranchId(bid);
				anx.setErpDocRefNo("");
				anx.setLineNo("1");
				anx.setProcessTransId(assessment.getAssesmentId());
				anx.setServiceId(c.getServiceId());
				anx.setSrlNo(new BigDecimal(srNo1));
				anx.setDocRefNo("");
				anx.setIgmLineNo("");
				anx.setProfitcentreId(assessment.getProfitcentreId());
				anx.setInvoiceType("AP");
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
				anx.setProcessId("P01703");
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
				if (assessment.getTaxApplicable() == 'Y') {

					anx.setTaxPerc(new BigDecimal(c.getTaxPerc()));
					anx.setTaxPercN(new BigDecimal(c.getTaxPerc()));
				} else {
					anx.setTaxPerc(BigDecimal.ZERO);
					anx.setTaxPercN(BigDecimal.ZERO);

				}

				anx.setApprovedBy(user);
				anx.setApprovedDate(new Date());

				anx.setBillAmt(c.getTotalRate());
				anx.setTaxIdN(c.getTaxId() == null ? "" : c.getTaxId());

				BigDecimal taxAmt = (c.getTotalRate().multiply(anx.getTaxPerc())).divide(new BigDecimal(100),
						RoundingMode.HALF_UP);

				anx.setTaxAmt(taxAmt);
				anx.setInvoiceNo(HoldNextIdD1);
				anx.setInvoiceDate(new Date());
				anx.setInvoiceAmt((c.getTotalRate().add(taxAmt)).setScale(3, RoundingMode.HALF_UP));

				cfinvsrvanxrepo.save(anx);
			} else {
				List<Cfinvsrvanxap> existingData = cfinvsrvanxrepo.getDataByProcessTransIdAndServiceId(cid, bid,
						c.getAssesmentId(), c.getServiceId());

				if (!existingData.isEmpty()) {
					existingData.stream().forEach(o -> {
						o.setApprovedBy(user);
						o.setApprovedDate(new Date());

						if (!"INR".equals(o.getCurrencyId())) {

							BigDecimal local = (o.getActualNoOfPackages()).setScale(3, RoundingMode.HALF_UP);
							o.setForeignAmt((local.divide(o.getExRate())).setScale(3, RoundingMode.HALF_UP));
						}
						o.setExecutionUnit(c.getExecutionUnit());
						o.setExecutionUnit1(c.getExecutionUnit1());
						o.setRate(c.getRate());
						o.setActualNoOfPackages(c.getTotalRate());
						o.setLocalAmt(c.getTotalRate());
						o.setTaxIdN(c.getTaxId() == null ? "" : c.getTaxId());
						o.setTaxId(c.getTaxId() == null ? "" : c.getTaxId());

						o.setTaxId(c.getTaxId() == null ? "" : c.getTaxId());
						if (assessment.getTaxApplicable() == 'Y') {
							o.setTaxPerc(new BigDecimal(c.getTaxPerc()));
							o.setTaxPercN(new BigDecimal(c.getTaxPerc()));
						} else {
							o.setTaxPerc(BigDecimal.ZERO);
							o.setTaxPercN(BigDecimal.ZERO);
						}

						BigDecimal taxAmt = (c.getTotalRate().multiply(o.getTaxPerc())).divide(new BigDecimal(100),
								RoundingMode.HALF_UP);

						o.setTaxAmt(taxAmt);
						o.setInvoiceNo(HoldNextIdD1);
						o.setInvoiceDate(new Date());
						o.setInvoiceAmt((c.getTotalRate().add(taxAmt)).setScale(3, RoundingMode.HALF_UP));
						o.setAcCodeN(c.getSacCode());

						cfinvsrvanxrepo.save(o);

					});
				}
			}

		});

		List<Cfinvsrvanxap> oldAnxData = cfinvsrvanxrepo.getDataByProcessTransId(cid, bid, assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

//
//		oldAnxData.stream().forEach(o -> {
//			o.setApprovedBy(user);
//			o.setApprovedDate(new Date());
//
//			BigDecimal discAmt = BigDecimal.ZERO;
//
//			if (o.getDiscValue() != null) {
//				discAmt = o.getDiscValue();
//			} else if (o.getmAmount() != null) {
//				discAmt = o.getmAmount();
//			} else if (o.getDiscPercentage() != null) {
//				discAmt = ((o.getActualNoOfPackages().multiply(o.getDiscPercentage())).divide(new BigDecimal(100)))
//						.setScale(3, RoundingMode.HALF_UP);
//
//			} else if (o.getmPercentage() != null) {
//				discAmt = ((o.getActualNoOfPackages().multiply(o.getmPercentage())).divide(new BigDecimal(100)))
//						.setScale(3, RoundingMode.HALF_UP);
//
//			}
//
//			if (!"INR".equals(o.getCurrencyId())) {
//
//				BigDecimal local = (o.getActualNoOfPackages().subtract(discAmt)).setScale(3, RoundingMode.HALF_UP);
//				o.setForeignAmt((local.divide(o.getExRate())).setScale(3, RoundingMode.HALF_UP));
//			}
//			o.setBillAmt(o.getActualNoOfPackages());
//			o.setTaxIdN(o.getTaxId());
//			o.setTaxPercN(o.getTaxPerc());
//
//			BigDecimal taxAmt = (o.getActualNoOfPackages().multiply(o.getTaxPerc())).divide(new BigDecimal(100),
//					RoundingMode.HALF_UP);
//
//			o.setTaxAmt(taxAmt);
//			o.setInvoiceNo(HoldNextIdD1);
//			o.setInvoiceDate(new Date());
//			o.setInvoiceAmt((o.getActualNoOfPackages().add(taxAmt)).setScale(3, RoundingMode.HALF_UP));
//			o.setAcCodeN(o.getAcCode());
//
//			cfinvsrvanxrepo.save(o);
//
//		});

		List<Cfinvsrvanxap> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, Cfinvsrvanxap> groupedData = newAnxData.stream()
				.collect(Collectors.toMap(Cfinvsrvanxap::getServiceId, // Key:
																		// serviceId
						c -> c, // Value: current record (initial)
						(c1, c2) -> { // Merge function for duplicates
							// Summing the required fields
							c1.setLocalAmt((c1.getLocalAmt().add(c2.getLocalAmt())).setScale(3, RoundingMode.HALF_UP));
							c1.setBillAmt((c1.getBillAmt().add(c2.getBillAmt())).setScale(3, RoundingMode.HALF_UP));
							c1.setInvoiceAmt(
									(c1.getInvoiceAmt().add(c2.getInvoiceAmt())).setScale(3, RoundingMode.HALF_UP));
							c1.setTaxAmt((c1.getTaxAmt().add(c2.getTaxAmt())).setScale(3, RoundingMode.HALF_UP));
							return c1; // Retain the first record, updated with summed fields
						}));

		// Converting the grouped data to a list
		List<Cfinvsrvanxap> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			Cfinvsrvdtlap dtl = new Cfinvsrvdtlap();
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

		AssessmentSheetAP assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanxap::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanxap::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanxap::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanxap::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanxap::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanxap::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanxap::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanxap::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(Cfinvsrvanxap::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(Cfinvsrvanxap::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(Cfinvsrvanxap::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(Cfinvsrvanxap::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05121", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		Cfinvsrvap srv = new Cfinvsrvap();
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
		srv.setInvoiceType("VEN");
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
		processnextidrepo.updateAuditTrail(cid, bid, "P05121", HoldNextIdD2, "2024");

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		InvCreditTrackAP track = new InvCreditTrackAP();
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
		track.setCreditAmount(totalInvAmt);
		track.setPartyId(assSheet.getPartyId());
		track.setInvoiceNo(HoldNextIdD1);
		track.setTdsStatus("N");
		track.setLocalAmount(totalLocalAmt);

		track.setTdsDeductee("CHA".equals(tdsDeductee) ? assSheet.getCha()
				: "IMP".equals(tdsDeductee) ? assSheet.getImporterId()
						: "FWR".equals(tdsDeductee) ? assSheet.getOnAccountOf()
								: "OTH".equals(tdsDeductee) ? assSheet.getOthPartyId() : "");

		invcredittrackrepo.save(track);

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
			o.setLastInvoiceNo(assessment.getLastInvoiceNo());
			o.setLastInvoiceDate(assessment.getLastInvoiceDate());

			assessmentsheetrepo.save(o);

		});

		List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrvap existingSrv = invsrvRepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}

	@GetMapping("/searchVendorInvoiceData")
	public ResponseEntity<?> searchMISCInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = assessmentsheetrepo.searchMISCInvoiceData1(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSelectedVendorInvoiceData")
	public ResponseEntity<?> getSelectedMISCInvoiceData1(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("assId") String assId, @RequestParam("invId") String invId) {
		List<Object[]> result = assessmentsheetrepo.getMISCAssessmentData(cid, bid, assId);

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrvap existingSrv = invsrvRepo.getDataByInvoiceNo(cid, bid, invId);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);

		return new ResponseEntity<>(finalResult, HttpStatus.OK);
	}

	@GetMapping("/getAllWithAdd")
	public List<Object[]> getAllWithAdd(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyRepository.getAllWithAddAndAccDtls(cid, bid, val);

		return getPort;
	}

	@GetMapping("/advanceReceiptBeforeSaveSearch")
	public ResponseEntity<?> advanceReceiptBeforeSaveSearch(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("id") String id, @RequestParam("type") String type) {

		List<Object[]> data = invcredittrackrepo.getBeforeSearchDataForeceipt(cid, bid, id);

		if (data.isEmpty()) {

			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}
	
	
	@Transactional
	@PostMapping("/saveReceipt")
	public ResponseEntity<?> saveReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("user") String user,@RequestParam("type") String type,@RequestBody Map<String,Object> data) throws JsonMappingException, JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		FinTransAP fin = mapper.readValue(mapper.writeValueAsString(data.get("finTransData")), FinTransAP.class);

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

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05120", "2024");

		String pre = holdId1.substring(0, 4);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%06d", nextNumericNextID1);



		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		paymentDto.stream().forEach(p -> {
			FinTransAP newData = new FinTransAP();

			newData.setCompanyId(cid);
			newData.setBranchId(bid);
			newData.setDocType(fin.getDocType());
			newData.setLedgerType("AP");
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
			
			FinTransInvAP finInv = new FinTransInvAP();
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
			finInv.setLedgerType("AP");
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

			BigDecimal oldReceiptVal = invsrvRepo.getReceiptAmtByInvoiceNo(cid, bid, i.getInvoiceNo());

			BigDecimal finalReceiptAmt = oldReceiptVal.add(i.getReceiptAmt()).setScale(3, RoundingMode.HALF_UP);

			int updateInvSrv = invsrvRepo.updateReceiptAmount(cid, bid, i.getInvoiceNo(), HoldNextIdD1,
					finalReceiptAmt);

			BigDecimal oldCreAdjVal = invcredittrackrepo.getCreditAdjAmtByInvoiceNo(cid, bid, i.getInvoiceNo());

			BigDecimal finalCreAdjVal = oldCreAdjVal.add(i.getReceiptAmt()).setScale(3, RoundingMode.HALF_UP);

			int updateCreAdjVal = invcredittrackrepo.updateCreditAdjAmt(cid, bid, i.getInvoiceNo(), finalCreAdjVal,
					tdsStaus);

			
	
		});

		

		processnextidrepo.updateAuditTrail(cid, bid, "P05120", HoldNextIdD1, "2024");
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
	
	@GetMapping("/getAfterSaveData")
	public ResponseEntity<?> searchAfterSaveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> result = new ArrayList<>();

		List<Object[]> data = fintransrepo.getAfterSaveData(cid, bid, val);

		if (!data.isEmpty()) {
			result.addAll(data);
		}

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(result, HttpStatus.OK);

		}

		
	}

	@GetMapping("/getReceiptSelectedData")
	public ResponseEntity<?> getReceiptSelectedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val, @RequestParam("id") String id, @RequestParam("type") String type) {
		Map<String, Object> result = new HashMap<>();

		List<Object[]> finData = fintransrepo.getDataByTransId(cid, bid, val);

		if (finData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		result.put("finTransData", finData);

		List<Object[]> finInvData = finTransInvRepo.getAfterSaveDataByTransId(cid, bid, val);

		result.put("finTransInvData", finInvData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}


}
