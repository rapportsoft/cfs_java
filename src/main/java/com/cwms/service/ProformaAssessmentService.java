package com.cwms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.AssessmentContainerDTO;
import com.cwms.entities.AssessmentSheet;
import com.cwms.entities.AssessmentSheetPro;
import com.cwms.entities.Branch;
import com.cwms.entities.CFSDay;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfexbonddts;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.CfinbondcrgHDR;
import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.CfinvsrvPro;
import com.cwms.entities.Cfinvsrvanx;
import com.cwms.entities.CfinvsrvanxPro;
import com.cwms.entities.Cfinvsrvanxback;
import com.cwms.entities.Cfinvsrvdtl;
import com.cwms.entities.CfinvsrvdtlPro;
import com.cwms.entities.FinTrans;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.entities.PaymentReceiptDTO;
import com.cwms.entities.SSRDtl;
import com.cwms.repository.AssessmentSheetProRepository;
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
import com.cwms.repository.CfinvsrvProRepository;
import com.cwms.repository.CfinvsrvRepo;
import com.cwms.repository.CfinvsrvanxRepo;
import com.cwms.repository.CfinvsrvanxbackRepo;
import com.cwms.repository.CfinvsrvanxproRepository;
import com.cwms.repository.CfinvsrvdtlProRepository;
import com.cwms.repository.CfinvsrvdtlRepo;
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
public class ProformaAssessmentService {

	@Autowired
	private AssessmentSheetProRepository assessmentsheetprorepo;

	@Autowired
	private CfinvsrvdtlProRepository cfinvsrvdtlprorepo;

	@Autowired
	private CfinvsrvanxproRepository cfinvsrvanxprorepo;

	@Autowired
	private CfinvsrvProRepository cfinvsrvprorepo;

	@Autowired
	private AssessmentSheetRepo assessmentsheetrepo;

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
	private CfexbonddtsRepo cfexbonddtlsrepo;

	@Autowired
	private CfinbondCrgHdrRepo cfinbondhdrrepo;

	@Autowired
	private CfExBondCrgRepository exbondrepo;

	@Autowired
	private InvCreditTrackRepo invcredittrackrepo;

	@Autowired
	private PdaDtlRepo pdadtlrepo;

	@Autowired
	private PadHdrRepo pdahdrrepo;

	@Autowired
	private ObjectMapper objectMapper;

	public ResponseEntity<?> searchImportBeforeSaveAssessmentData(String cid, String bid, String val) {
		List<Object[]> data = assessmentsheetprorepo.getBeforeAssessmentData(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getImportBeforeSaveAssessmentData(String cid, String bid, String trans, String igm,
			String lineNo) {

		List<Object[]> data = assessmentsheetprorepo.getBeforeSaveAssessmentData(cid, bid, trans, igm, lineNo);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@Transactional
	public ResponseEntity<?> saveAssessmentData(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			AssessmentSheetPro assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
					AssessmentSheetPro.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<AssessmentContainerDTO> containerData = mapper.readValue(
					mapper.writeValueAsString(data.get("containerData")),
					new TypeReference<List<AssessmentContainerDTO>>() {
					});

			if (containerData.isEmpty()) {
				return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
			}

			AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

			if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {

				int sr = 1;

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05105", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("INCO%06d", nextNumericNextID1);

				for (AssessmentContainerDTO con : containerData) {
					List<AssessmentContainerDTO> finalConData = new ArrayList<>();
					if (con.getLastInvoiceUptoDate() == null) {

						List<String> conSize = new ArrayList<>();
						conSize.add("");
						conSize.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

						List<String> conTypeOfCon = new ArrayList<>();
						conTypeOfCon.add("");
						conTypeOfCon.add(con.getTypeOfContainer());

						List<String> scanType = new ArrayList<>();
						scanType.add("");
						scanType.add(con.getScannerType());

						List<String> examType = new ArrayList<>();
						examType.add("");
						examType.add((con.getExamPercentage().isEmpty() || "0".equals(con.getExamPercentage())) ? ""
								: Integer.parseInt(con.getExamPercentage()) > 25 ? "100" : con.getExamPercentage());

						String conStatus = "FCL".equals(con.getContainerStatus()) ? "IMP" : "IMPCRG";

						// Service Mapping
						List<String> serviceMappingData = new ArrayList<>();

						if ("SINGLE".equals(assessment.getInvoiceCategory())) {
							serviceMappingData = serviceMappingRepo.getServicesForImportInvoice1(cid, bid,
									con.getProfitcentreId(), conStatus, con.getGateOutType(), conSize, conTypeOfCon,
									scanType, examType);
						} else {

							String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
							serviceMappingData = serviceMappingRepo.getServicesForImportInvoice2(cid, bid,
									con.getProfitcentreId(), conStatus, con.getGateOutType(), conSize, conTypeOfCon,
									scanType, examType, group);
						}

						if (serviceMappingData.isEmpty()) {
							return new ResponseEntity<>(
									"Service mapping not found for container no " + con.getContainerNo(),
									HttpStatus.CONFLICT);
						}

						Set<String> combinedService = new HashSet<>();
						combinedService.addAll(serviceMappingData);

						List<String> partyIdList = new ArrayList<>();
						partyIdList.add(assessment.getOthPartyId());
						partyIdList.add("");

						List<String> impIdList = new ArrayList<>();
						impIdList.add(assessment.getImporterId());
						impIdList.add("");

						List<String> chaIdList = new ArrayList<>();
						chaIdList.add(assessment.getCha());
						chaIdList.add("");

						List<String> fwIdList = new ArrayList<>();
						fwIdList.add(assessment.getOnAccountOf());
						fwIdList.add("");

						if (con.getUpTariffNo() == null || con.getUpTariffNo().isEmpty()) {
							String tariffNo = cfstarrifrepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList,
									fwIdList, assessment.getOthPartyId(), assessment.getImporterId(),
									assessment.getCha(), assessment.getOnAccountOf());

							con.setUpTariffNo(tariffNo);
						}

						if (con.getUpTariffNo() == null || con.getUpTariffNo().isEmpty()) {
							return new ResponseEntity<>("Tariff no not found.", HttpStatus.CONFLICT);
						}

						// Find default service
						List<String> defaultService = cfstarrifrepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

						if (!defaultService.isEmpty()) {
							combinedService.addAll(defaultService);
						}

						// SSR service

						List<SSRDtl> ssrServices = new ArrayList<>();

						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
							ssrServices = ssrRepo.getServiceId(cid, bid, con.getSsrTransId(), con.getContainerNo());

							if (!ssrServices.isEmpty()) {
								List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId) // Extract
																											// serviceId
										.collect(Collectors.toList()); // Collect into a list

								combinedService.addAll(ssrServiceId);
							}

						}

						List<SSRDtl> finalSsrServices = ssrServices;

						List<String> finalServices = new ArrayList<>(combinedService);

						List<Object[]> finalPricing = new ArrayList<>();
						List<String> notInPricing = new ArrayList<>();

						List<String> conSize1 = new ArrayList<>();
						conSize1.add("ALL");
						conSize1.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

						List<String> conTypeOfCon1 = new ArrayList<>();
						conTypeOfCon1.add("ALL");
						conTypeOfCon1.add(con.getTypeOfContainer());

						Date assData = assessment.getAssesmentDate(); // Original date
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						String formattedDate = formatter.format(assData);
						Date parsedDate = formatter.parse(formattedDate);

						List<Object[]> pricingData = cfstariffservicerepo.getServiceRate(cid, bid, parsedDate,
								con.getContainerNo(), assessment.getIgmTransId(), assessment.getIgmNo(),
								assessment.getIgmLineNo(), finalServices, con.getUpTariffNo(), conSize1, conTypeOfCon1);

						if (!pricingData.isEmpty()) {
							List<Object[]> remainingPricing = pricingData.stream()
									.filter(data1 -> finalServices.contains(data1[0].toString()))
									.collect(Collectors.toList());

							notInPricing = finalServices.stream()
									.filter(service -> pricingData.stream()
											.noneMatch(data1 -> service.equals(data1[0].toString())))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						} else {
							notInPricing.addAll(finalServices);
						}

						if (!notInPricing.isEmpty()) {
							List<String> tempPricing = notInPricing;
							List<String> conSize2 = new ArrayList<>();
							conSize2.add("ALL");
							conSize2.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

							List<String> conTypeOfCon2 = new ArrayList<>();
							conTypeOfCon2.add("ALL");
							conTypeOfCon2.add(con.getTypeOfContainer());

							List<Object[]> pricingData1 = cfstariffservicerepo.getServiceRate(cid, bid, parsedDate,
									con.getContainerNo(), assessment.getIgmTransId(), assessment.getIgmNo(),
									assessment.getIgmLineNo(), notInPricing, "CFS1000001", conSize2, conTypeOfCon2);

							if (!pricingData1.isEmpty()) {
								List<Object[]> remainingPricing = pricingData1.stream()
										.filter(data1 -> tempPricing.contains(data1[0].toString()))
										.collect(Collectors.toList());

								finalPricing.addAll(remainingPricing);
							}
						}

						finalPricing.stream().forEach(f -> {
							AssessmentContainerDTO tempAss = new AssessmentContainerDTO();
							CFSDay day = cfsdayrepo.getData(cid, bid);

							if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

								if (!finalSsrServices.isEmpty()) {

									SSRDtl rate1 = finalSsrServices.stream()
											.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
											.orElse(null); // Return null if no match is found

									if (rate1 != null) {

										try {
											tempAss = (AssessmentContainerDTO) con.clone();

											Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(),
													day.getStartTime(), day.getEndTime());

											tempAss.setInvoiceDate(invDate);
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													f[13] != null ? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setRates(rate1.getTotalRate());
											tempAss.setServiceRate(rate1.getRate());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											finalConData.add(tempAss);
										} catch (CloneNotSupportedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									} else {
										try {
											tempAss = (AssessmentContainerDTO) con.clone();
											Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(),
													day.getStartTime(), day.getEndTime());

											tempAss.setInvoiceDate(invDate);
											if ("SA".equals(String.valueOf(f[8]))) {
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue(
														f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																: BigDecimal.ZERO);
												tempAss.setmPercentage(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

												List<String> conSize2 = new ArrayList<>();
												conSize2.add("ALL");
												conSize2.add(("22".equals(con.getContainerSize())) ? "20"
														: con.getContainerSize());

												List<String> conTypeOfCon2 = new ArrayList<>();
												conTypeOfCon2.add("ALL");
												conTypeOfCon2.add(con.getTypeOfContainer());

												List<String> commodityType = new ArrayList<>();
												commodityType.add("ALL");
												commodityType.add(assessment.getCommodityCode());

												List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(
														cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
														String.valueOf(f[0]), String.valueOf(f[8]), conSize2,
														conTypeOfCon2, commodityType, con.getContainerSize(),
														con.getTypeOfContainer(), assessment.getCommodityCode());

												if (!rangeValues.isEmpty()) {

													BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
															.filter(r -> ((BigDecimal) r[8])
																	.compareTo(BigDecimal.ZERO) == 0) // Filter where
																										// rate is 0
															.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																							// BigDecimal
															.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
															.orElse(BigDecimal.ZERO); // Default value if no match found

													tempAss.setFreeDays(freeDaysForExportContainerStorage);

													String unit = String.valueOf(f[1]);

													AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
															BigDecimal.ZERO);
													if ("DAY".equals(unit)) {

														if (con.getDestuffDate() != null) {
															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getGateInDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getDestuffDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;

															tempAss.setExecutionUnit(String.valueOf(daysBetween1));
															tempAss.setExecutionUnit1("");

															System.out.println("daysBetween " + daysBetween1);

															BigDecimal totalRate = rangeValues.stream().map(r -> {

																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();

																BigDecimal rate = (BigDecimal) r[8];

																if (daysBetween1 >= fromRange
																		&& daysBetween1 <= toRange) {
																	serviceRate.set(rate); // Set the rate for the
																							// matching
																							// slab
																}

																long daysInSlab = Math.min(toRange, daysBetween1)
																		- Math.max(fromRange, 1) + 1;

																daysInSlab = Math.max(daysInSlab, 0);

																serviceRate.set(rate);
																return rate.multiply(new BigDecimal(daysInSlab));
															}).reduce(BigDecimal.ZERO, BigDecimal::add);
															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															tempAss.setServiceRate(serviceRate.get());

															String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
															String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
															String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
															String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

															if (cPerc != null && !cPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(cPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (cAmt != null && !cAmt.isEmpty()) {

																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (mPerc != null && !mPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(mPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else if (mAmt != null && !mAmt.isEmpty()) {
																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else {
																tempAss.setRates(totalRate);
															}

														} else {

															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getGateInDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;

															tempAss.setExecutionUnit(String.valueOf(daysBetween1));
															tempAss.setExecutionUnit1("");

															System.out.println("daysBetween " + daysBetween1);

															BigDecimal totalRate = rangeValues.stream().map(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue(); // Start
																												// of
																												// the
																												// slab
																int toRange = ((BigDecimal) r[7]).intValue(); // End of
																												// the
																												// slab
																BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
																										// the
																										// slab
																if (daysBetween1 >= fromRange
																		&& daysBetween1 <= toRange) {
																	serviceRate.set(rate); // Set the rate for the
																							// matching
																							// slab
																}
																// Calculate days in the current slab
																long daysInSlab = Math.min(toRange, daysBetween1)
																		- Math.max(fromRange, 1) + 1;

																daysInSlab = Math.max(daysInSlab, 0);
																return rate.multiply(new BigDecimal(daysInSlab));
															}).reduce(BigDecimal.ZERO, BigDecimal::add);
															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															tempAss.setServiceRate(serviceRate.get());
															String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
															String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
															String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
															String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

															if (cPerc != null && !cPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(cPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (cAmt != null && !cAmt.isEmpty()) {

																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (mPerc != null && !mPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(mPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else if (mAmt != null && !mAmt.isEmpty()) {
																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else {
																tempAss.setRates(totalRate);
															}

														}
													}

													if ("WEEK".equals(unit)) {
														if (con.getDestuffDate() != null) {
															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getDestuffDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;
															long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

															tempAss.setExecutionUnit(String.valueOf(weeksBetween));
															tempAss.setExecutionUnit1("");

															BigDecimal totalRate = rangeValues.stream().filter(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																// Include slabs that overlap with weeksBetween
																return weeksBetween >= fromRange;
															}).map(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																BigDecimal rate = (BigDecimal) r[8];

																// Adjust the fromRange to exclude week 0
																int adjustedFromRange = Math.max(fromRange, 1);

																// Determine actual weeks in this slab that contribute
																// to the total
																long weeksInSlab = Math.max(0,
																		Math.min(toRange, weeksBetween)
																				- adjustedFromRange + 1);

																// Exclude slabs where weeksInSlab is 0
																return weeksInSlab > 0
																		? rate.multiply(new BigDecimal(weeksInSlab))
																		: BigDecimal.ZERO;
															}).reduce(BigDecimal.ZERO, BigDecimal::add);

															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);

															if ("SM".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(
																		con.getArea() == null ? new BigDecimal(1)
																				: con.getArea()))
																		.setScale(3, RoundingMode.HALF_UP);
															}

															System.out.println(
																	"totalRate " + totalRate + " " + weeksBetween);
															tempAss.setServiceRate(serviceRate.get());
															String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
															String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
															String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
															String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

															if (cPerc != null && !cPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(cPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (cAmt != null && !cAmt.isEmpty()) {

																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (mPerc != null && !mPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(mPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else if (mAmt != null && !mAmt.isEmpty()) {
																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else {
																tempAss.setRates(totalRate);
															}

														} else {
															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getGateInDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;
															long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

															tempAss.setExecutionUnit(String.valueOf(weeksBetween));
															tempAss.setExecutionUnit1("");

															BigDecimal totalRate = rangeValues.stream().filter(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																// Include slabs that overlap with weeksBetween
																return weeksBetween >= fromRange;
															}).map(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																BigDecimal rate = (BigDecimal) r[8];

																// Adjust the fromRange to exclude week 0
																int adjustedFromRange = Math.max(fromRange, 1);

																// Determine actual weeks in this slab that contribute
																// to the total
																long weeksInSlab = Math.max(0,
																		Math.min(toRange, weeksBetween)
																				- adjustedFromRange + 1);

																// Exclude slabs where weeksInSlab is 0
																return weeksInSlab > 0
																		? rate.multiply(new BigDecimal(weeksInSlab))
																		: BigDecimal.ZERO;
															}).reduce(BigDecimal.ZERO, BigDecimal::add);

															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															System.out.println(
																	"totalRate " + totalRate + " " + weeksBetween);

															if ("SM".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(
																		con.getArea() == null ? new BigDecimal(1)
																				: con.getArea()))
																		.setScale(3, RoundingMode.HALF_UP);
															}
															tempAss.setServiceRate(serviceRate.get());
															String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
															String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
															String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
															String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

															if (cPerc != null && !cPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(cPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (cAmt != null && !cAmt.isEmpty()) {

																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);

															} else if (mPerc != null && !mPerc.isEmpty()) {
																BigDecimal disPerc = new BigDecimal(mPerc);
																BigDecimal finalPerc = new BigDecimal(100)
																		.subtract(disPerc);

																BigDecimal amt = (totalRate.multiply(finalPerc))
																		.divide(new BigDecimal(100));

																amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

																BigDecimal disAmt = (totalRate.subtract(amt))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else if (mAmt != null && !mAmt.isEmpty()) {
																BigDecimal disAmt = (totalRate
																		.subtract(new BigDecimal(cAmt)))
																		.setScale(3, BigDecimal.ROUND_HALF_UP);

																tempAss.setRates(disAmt);
															} else {
																tempAss.setRates(totalRate);
															}
														}

													}

												}

											} else if ("RA".equals(String.valueOf(f[8]))) {

												String unit = String.valueOf(f[1]);
												BigDecimal executionUnit = BigDecimal.ZERO;
												List<String> conSize2 = new ArrayList<>();
												conSize2.add("ALL");
												conSize2.add(("22".equals(con.getContainerSize())) ? "20"
														: con.getContainerSize());

												List<String> conTypeOfCon2 = new ArrayList<>();
												conTypeOfCon2.add("ALL");
												conTypeOfCon2.add(con.getTypeOfContainer());

												List<String> commodityType = new ArrayList<>();
												commodityType.add("ALL");
												commodityType.add(assessment.getCommodityCode());

												if ("MTON".equals(unit)) {
													String serviceGrp = String.valueOf(f[18]);

													if ("H".equals(serviceGrp)) {

														BigDecimal cargoWt = new BigDecimal(
																String.valueOf(con.getGrossWt()))
																.divide(new BigDecimal(1000))
																.setScale(3, RoundingMode.HALF_UP);

														executionUnit = cargoWt;

														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else {
														BigDecimal cargoWt = con.getCargoWt()
																.divide(new BigDecimal(1000))
																.setScale(3, RoundingMode.HALF_UP);

														executionUnit = cargoWt;
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}

													Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(
															cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
															String.valueOf(f[0]), executionUnit, conSize2,
															conTypeOfCon2, commodityType, con.getContainerSize(),
															con.getTypeOfContainer(), assessment.getCommodityCode());

													if (rangeValue != null) {
														Object[] finalRangeValue = (Object[]) rangeValue;
														tempAss.setServiceGroup(
																f[18] != null ? String.valueOf(f[18]) : "");
														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
														tempAss.setServiceName(
																f[22] != null ? String.valueOf(f[22]) : "");
														tempAss.setServiceUnit(
																f[1] != null ? String.valueOf(f[1]) : "");
														tempAss.setServiceUnit1(
																f[7] != null ? String.valueOf(f[7]) : "");
														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
														tempAss.setDiscPercentage(
																f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																		: BigDecimal.ZERO);
														tempAss.setDiscValue(
																f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																		: BigDecimal.ZERO);
														tempAss.setmPercentage(
																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																		: BigDecimal.ZERO);
														tempAss.setmAmount(
																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																		: BigDecimal.ZERO);
														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
														tempAss.setRangeFrom(
																f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																		: BigDecimal.ZERO);
														tempAss.setRangeTo(
																f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																		: BigDecimal.ZERO);
														tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
														tempAss.setContainerStatus(con.getContainerStatus());
														tempAss.setGateOutId(con.getGateOutId());
														tempAss.setGatePassNo(con.getGatePassNo());
														tempAss.setTaxPerc(
																(f[12] == null || String.valueOf(f[12]).isEmpty())
																		? BigDecimal.ZERO
																		: new BigDecimal(String.valueOf(f[12])));
														tempAss.setTaxId(String.valueOf(f[11]));
														BigDecimal totalRate = new BigDecimal(
																String.valueOf(finalRangeValue[8]));

														tempAss.setServiceRate(totalRate);
														tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

														String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
														String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
														String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
														String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

														if (cPerc != null && !cPerc.isEmpty()) {
															BigDecimal disPerc = new BigDecimal(cPerc);
															BigDecimal finalPerc = new BigDecimal(100)
																	.subtract(disPerc);

															BigDecimal amt = (totalRate.multiply(finalPerc))
																	.divide(new BigDecimal(100));

															amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

															BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																	BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);

														} else if (cAmt != null && !cAmt.isEmpty()) {

															BigDecimal disAmt = (totalRate
																	.subtract(new BigDecimal(cAmt)))
																	.setScale(3, BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);

														} else if (mPerc != null && !mPerc.isEmpty()) {
															BigDecimal disPerc = new BigDecimal(mPerc);
															BigDecimal finalPerc = new BigDecimal(100)
																	.subtract(disPerc);

															BigDecimal amt = (totalRate.multiply(finalPerc))
																	.divide(new BigDecimal(100));

															amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

															BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																	BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);
														} else if (mAmt != null && !mAmt.isEmpty()) {
															BigDecimal disAmt = (totalRate
																	.subtract(new BigDecimal(cAmt)))
																	.setScale(3, BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);
														} else {
															tempAss.setRates(
																	new BigDecimal(String.valueOf(finalRangeValue[8])));
														}

													}

												} else if ("TEU".equals(unit) || "SM".equals(unit)
														|| "CNTR".equals(unit) || "PBL".equals(unit)
														|| "ACT".equals(unit) || "PCK".equals(unit)
														|| "SHFT".equals(unit) || "KG".equals(unit)) {

													if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
															|| "ACT".equals(unit) || "PCK".equals(unit)
															|| "SHFT".equals(unit)) {
														executionUnit = new BigDecimal("1");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}

													if ("TEU".equals(unit)) {
														if ("20".equals(con.getContainerSize())
																|| "22".equals(con.getContainerSize())) {
															executionUnit = new BigDecimal("1");
															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														} else if ("40".equals(con.getContainerSize())) {
															executionUnit = new BigDecimal("2");
															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														} else if ("45".equals(con.getContainerSize())) {
															executionUnit = new BigDecimal("2.5");
															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														}
													}

													if ("KG".equals(unit)) {
														String serviceGrp = String.valueOf(f[18]);
														if ("H".equals(serviceGrp)) {

															BigDecimal cargoWt = new BigDecimal(
																	String.valueOf(con.getGrossWt()));

															executionUnit = cargoWt;

															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														} else {
															BigDecimal cargoWt = con.getCargoWt();

															executionUnit = cargoWt;
															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														}
													}

													Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(
															cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
															String.valueOf(f[0]), executionUnit, conSize2,
															conTypeOfCon2, commodityType, con.getContainerSize(),
															con.getTypeOfContainer(), assessment.getCommodityCode());

													if (rangeValue != null) {
														Object[] finalRangeValue = (Object[]) rangeValue;
														tempAss.setServiceGroup(
																f[18] != null ? String.valueOf(f[18]) : "");
														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
														tempAss.setServiceName(
																f[22] != null ? String.valueOf(f[22]) : "");
														tempAss.setServiceUnit(
																f[1] != null ? String.valueOf(f[1]) : "");
														tempAss.setServiceUnit1(
																f[7] != null ? String.valueOf(f[7]) : "");
														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
														tempAss.setDiscPercentage(
																f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																		: BigDecimal.ZERO);
														tempAss.setDiscValue(
																f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																		: BigDecimal.ZERO);
														tempAss.setmPercentage(
																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																		: BigDecimal.ZERO);
														tempAss.setmAmount(
																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																		: BigDecimal.ZERO);
														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
														tempAss.setRangeFrom(
																f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																		: BigDecimal.ZERO);
														tempAss.setRangeTo(
																f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																		: BigDecimal.ZERO);
														tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
														tempAss.setContainerStatus(con.getContainerStatus());
														tempAss.setGateOutId(con.getGateOutId());
														tempAss.setGatePassNo(con.getGatePassNo());
														tempAss.setTaxPerc(
																(f[12] == null || String.valueOf(f[12]).isEmpty())
																		? BigDecimal.ZERO
																		: new BigDecimal(String.valueOf(f[12])));
														tempAss.setTaxId(String.valueOf(f[11]));
														tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
														BigDecimal totalRate = new BigDecimal(
																String.valueOf(finalRangeValue[8]));

														tempAss.setServiceRate(totalRate);

														String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
														String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
														String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
														String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

														if (cPerc != null && !cPerc.isEmpty()) {
															BigDecimal disPerc = new BigDecimal(cPerc);
															BigDecimal finalPerc = new BigDecimal(100)
																	.subtract(disPerc);

															BigDecimal amt = (totalRate.multiply(finalPerc))
																	.divide(new BigDecimal(100));

															amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

															BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																	BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);

														} else if (cAmt != null && !cAmt.isEmpty()) {

															BigDecimal disAmt = (totalRate
																	.subtract(new BigDecimal(cAmt)))
																	.setScale(3, BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);

														} else if (mPerc != null && !mPerc.isEmpty()) {
															BigDecimal disPerc = new BigDecimal(mPerc);
															BigDecimal finalPerc = new BigDecimal(100)
																	.subtract(disPerc);

															BigDecimal amt = (totalRate.multiply(finalPerc))
																	.divide(new BigDecimal(100));

															amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

															BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																	BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);
														} else if (mAmt != null && !mAmt.isEmpty()) {
															BigDecimal disAmt = (totalRate
																	.subtract(new BigDecimal(cAmt)))
																	.setScale(3, BigDecimal.ROUND_HALF_UP);

															tempAss.setRates(disAmt);
														} else {
															tempAss.setRates(
																	new BigDecimal(String.valueOf(finalRangeValue[8])));
														}
													}

												}

												else {
													tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
													tempAss.setDiscPercentage(
															f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(
															f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setmAmount(
															f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
													tempAss.setContainerStatus(con.getContainerStatus());
													tempAss.setGateOutId(con.getGateOutId());
													tempAss.setGatePassNo(con.getGatePassNo());
													tempAss.setTaxPerc(
															(f[12] == null || String.valueOf(f[12]).isEmpty())
																	? BigDecimal.ZERO
																	: new BigDecimal(String.valueOf(f[12])));
													tempAss.setTaxId(String.valueOf(f[11]));
													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
													BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
													tempAss.setServiceRate(totalRate);

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
													}

												}

											} else {
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue(
														f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																: BigDecimal.ZERO);
												tempAss.setmPercentage(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setExecutionUnit(String.valueOf(1));
												tempAss.setExecutionUnit1("");
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
												BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

												tempAss.setServiceRate(totalRate);

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
												}
											}

											finalConData.add(tempAss);

										} catch (CloneNotSupportedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}

							} else {

								try {
									tempAss = (AssessmentContainerDTO) con.clone();
									Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
											day.getEndTime());

									tempAss.setInvoiceDate(invDate);
									if ("SA".equals(String.valueOf(f[8]))) {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage(f[13] != null ? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										List<String> conSize2 = new ArrayList<>();
										conSize2.add("ALL");
										conSize2.add(
												("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

										List<String> conTypeOfCon2 = new ArrayList<>();
										conTypeOfCon2.add("ALL");
										conTypeOfCon2.add(con.getTypeOfContainer());

										List<String> commodityType = new ArrayList<>();
										commodityType.add("ALL");
										commodityType.add(assessment.getCommodityCode());

										List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(cid, bid,
												String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												String.valueOf(f[8]), conSize2, conTypeOfCon2, commodityType,
												con.getContainerSize(), con.getTypeOfContainer(),
												assessment.getCommodityCode());

										if (!rangeValues.isEmpty()) {

											BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
													.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																														// where
																														// rate
																														// is
																														// 0
													.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																					// BigDecimal
													.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
													.orElse(BigDecimal.ZERO); // Default value if no match found

											tempAss.setFreeDays(freeDaysForExportContainerStorage);

											String unit = String.valueOf(f[1]);

											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
													BigDecimal.ZERO);

											if ("DAY".equals(unit)) {

												if (con.getDestuffDate() != null) {
													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getGateInDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getDestuffDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;

													tempAss.setExecutionUnit(String.valueOf(daysBetween1));
													tempAss.setExecutionUnit1("");

													System.out.println("daysBetween " + daysBetween1);

													BigDecimal totalRate = rangeValues.stream().map(r -> {

														int fromRange = ((BigDecimal) r[6]).intValue(); // Start of
																										// the slab
														int toRange = ((BigDecimal) r[7]).intValue(); // End of the
																										// slab

														BigDecimal rate = (BigDecimal) r[8]; // Rate per day in the
																								// slab
														if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
															serviceRate.set(rate); // Set the rate for the matching slab
														}
														long daysInSlab = Math.min(toRange, daysBetween1)
																- Math.max(fromRange, 1) + 1;

														daysInSlab = Math.max(daysInSlab, 0);
														return rate.multiply(new BigDecimal(daysInSlab));
													}).reduce(BigDecimal.ZERO, BigDecimal::add);
													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													tempAss.setServiceRate(serviceRate.get());
													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												} else {

													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getGateInDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;

													tempAss.setExecutionUnit(String.valueOf(daysBetween1));
													tempAss.setExecutionUnit1("");

													System.out.println("daysBetween " + daysBetween1);

													BigDecimal totalRate = rangeValues.stream().map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue(); // Start of
																										// the slab
														int toRange = ((BigDecimal) r[7]).intValue(); // End of the
																										// slab
														BigDecimal rate = (BigDecimal) r[8]; // Rate per day in the
																								// slab
														if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
															serviceRate.set(rate); // Set the rate for the matching slab
														}
														// Calculate days in the current slab
														long daysInSlab = Math.min(toRange, daysBetween1)
																- Math.max(fromRange, 1) + 1;

														daysInSlab = Math.max(daysInSlab, 0);
														return rate.multiply(new BigDecimal(daysInSlab));
													}).reduce(BigDecimal.ZERO, BigDecimal::add);
													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													tempAss.setServiceRate(serviceRate.get());
													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												}
											}

											if ("WEEK".equals(unit)) {
												if (con.getDestuffDate() != null) {
													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getDestuffDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

													tempAss.setExecutionUnit(String.valueOf(weeksBetween));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().filter(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														// Include slabs that overlap with weeksBetween
														return weeksBetween >= fromRange;
													}).map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														BigDecimal rate = (BigDecimal) r[8];

														// Adjust the fromRange to exclude week 0
														int adjustedFromRange = Math.max(fromRange, 1);

														// Determine actual weeks in this slab that contribute to the
														// total
														long weeksInSlab = Math.max(0, Math.min(toRange, weeksBetween)
																- adjustedFromRange + 1);

														// Exclude slabs where weeksInSlab is 0
														return weeksInSlab > 0
																? rate.multiply(new BigDecimal(weeksInSlab))
																: BigDecimal.ZERO;
													}).reduce(BigDecimal.ZERO, BigDecimal::add);

													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													if ("SM".equals(String.valueOf(f[7]))) {
														totalRate = (totalRate
																.multiply(con.getArea() == null ? new BigDecimal(1)
																		: con.getArea()))
																.setScale(3, RoundingMode.HALF_UP);
													}
													System.out.println("totalRate " + totalRate + " " + weeksBetween);
													tempAss.setServiceRate(serviceRate.get());
													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												} else {
													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getGateInDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

													tempAss.setExecutionUnit(String.valueOf(weeksBetween));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().filter(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														// Include slabs that overlap with weeksBetween
														return weeksBetween >= fromRange;
													}).map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														BigDecimal rate = (BigDecimal) r[8];

														// Adjust the fromRange to exclude week 0
														int adjustedFromRange = Math.max(fromRange, 1);

														// Determine actual weeks in this slab that contribute to the
														// total
														long weeksInSlab = Math.max(0, Math.min(toRange, weeksBetween)
																- adjustedFromRange + 1);

														// Exclude slabs where weeksInSlab is 0
														return weeksInSlab > 0
																? rate.multiply(new BigDecimal(weeksInSlab))
																: BigDecimal.ZERO;
													}).reduce(BigDecimal.ZERO, BigDecimal::add);

													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													if ("SM".equals(String.valueOf(f[7]))) {
														totalRate = (totalRate
																.multiply(con.getArea() == null ? new BigDecimal(1)
																		: con.getArea()))
																.setScale(3, RoundingMode.HALF_UP);
													}
													System.out.println("totalRate " + totalRate + " " + weeksBetween);
													tempAss.setServiceRate(serviceRate.get());
													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}
												}

											}

										}

									} else if ("RA".equals(String.valueOf(f[8]))) {

										String unit = String.valueOf(f[1]);
										BigDecimal executionUnit = BigDecimal.ZERO;
										List<String> conSize2 = new ArrayList<>();
										conSize2.add("ALL");
										conSize2.add(
												("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

										List<String> conTypeOfCon2 = new ArrayList<>();
										conTypeOfCon2.add("ALL");
										conTypeOfCon2.add(con.getTypeOfContainer());

										List<String> commodityType = new ArrayList<>();
										commodityType.add("ALL");
										commodityType.add(assessment.getCommodityCode());

										if ("MTON".equals(unit)) {
											String serviceGrp = String.valueOf(f[18]);

											if ("H".equals(serviceGrp)) {
												BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()))
														.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000))
														.setScale(3, RoundingMode.HALF_UP);

												executionUnit = cargoWt;
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}

											Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(cid, bid,
													String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
													executionUnit, conSize2, conTypeOfCon2, commodityType,
													con.getContainerSize(), con.getTypeOfContainer(),
													assessment.getCommodityCode());

											if (rangeValue != null) {
												Object[] finalRangeValue = (Object[]) rangeValue;
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue(
														f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																: BigDecimal.ZERO);
												tempAss.setmPercentage(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
												BigDecimal totalRate = new BigDecimal(
														String.valueOf(finalRangeValue[8]));
												tempAss.setServiceRate(totalRate);

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(
															new BigDecimal(String.valueOf(finalRangeValue[8])));
												}

											}

										} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
												|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
												|| "SHFT".equals(unit) || "KG".equals(unit)) {

											if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
													|| "ACT".equals(unit) || "PCK".equals(unit)
													|| "SHFT".equals(unit)) {
												executionUnit = new BigDecimal("1");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}

											if ("TEU".equals(unit)) {
												if ("20".equals(con.getContainerSize())
														|| "22".equals(con.getContainerSize())) {
													executionUnit = new BigDecimal("1");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else if ("40".equals(con.getContainerSize())) {
													executionUnit = new BigDecimal("2");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else if ("45".equals(con.getContainerSize())) {
													executionUnit = new BigDecimal("2.5");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}
											}

											if ("KG".equals(unit)) {
												String serviceGrp = String.valueOf(f[18]);
												if ("H".equals(serviceGrp)) {

													BigDecimal cargoWt = new BigDecimal(
															String.valueOf(con.getGrossWt()));

													executionUnit = cargoWt;

													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else {
													BigDecimal cargoWt = con.getCargoWt();

													executionUnit = cargoWt;
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}
											}

											Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(cid, bid,
													String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
													executionUnit, conSize2, conTypeOfCon2, commodityType,
													con.getContainerSize(), con.getTypeOfContainer(),
													assessment.getCommodityCode());

											if (rangeValue != null) {
												Object[] finalRangeValue = (Object[]) rangeValue;
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue(
														f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																: BigDecimal.ZERO);
												tempAss.setmPercentage(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
												BigDecimal totalRate = new BigDecimal(
														String.valueOf(finalRangeValue[8]));

												tempAss.setServiceRate(totalRate);

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(
															new BigDecimal(String.valueOf(finalRangeValue[8])));
												}
											}

										}

										else {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													f[13] != null ? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setExecutionUnit(String.valueOf(1));
											tempAss.setExecutionUnit1("");
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
											BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

											tempAss.setServiceRate(totalRate);

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
											}

										}

									} else {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage(f[13] != null ? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setExecutionUnit(String.valueOf(1));
										tempAss.setExecutionUnit1("");
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
										tempAss.setServiceRate(totalRate);

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
										}
									}

									finalConData.add(tempAss);

								} catch (CloneNotSupportedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						});

						AssessmentSheetPro tempAssessment = (AssessmentSheetPro) assessment.clone();

						AssessmentSheetPro newAss = tempAssessment;

						newAss.setCompanyId(cid);
						newAss.setBranchId(bid);
						newAss.setAssesmentLineNo(String.valueOf(sr));
						newAss.setStatus('A');
						newAss.setCreatedBy(user);
						newAss.setCreatedDate(new Date());
						newAss.setApprovedBy(user);
						newAss.setApprovedDate(new Date());
						newAss.setAssesmentDate(new Date());
						newAss.setTransType("Import Container");
						newAss.setProfitcentreId(con.getProfitcentreId());

						newAss.setAssesmentId(HoldNextIdD1);

						Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

						String billingId = "IMP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
								: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
										: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
												: assessment.getOthPartyId();

						String billingAdd = "IMP".equals(assessment.getBillingParty())
								? String.valueOf(assessment.getImpSrNo())
								: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
										: "FWR".equals(assessment.getBillingParty())
												? String.valueOf(assessment.getAccSrNo())
												: assessment.getOthSrNo();

						PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

						newAss.setPartyId(billingId);
						newAss.setPartySrNo(new BigDecimal(billingAdd));
						newAss.setPayableParty(assessment.getBillingParty());
						newAss.setPayablePartyId(billingId);

						if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
							newAss.setIgst("Y");
							newAss.setCgst("N");
							newAss.setSgst("N");
						} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
							newAss.setIgst("E");
							newAss.setCgst("N");
							newAss.setSgst("N");
						}

						else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {

							if (b.getState().equals(p.getState())) {
								newAss.setIgst("N");
								newAss.setCgst("Y");
								newAss.setSgst("Y");
							} else {
								newAss.setIgst("Y");
								newAss.setCgst("N");
								newAss.setSgst("N");
							}

						} else {
							newAss.setIgst("N");
							newAss.setCgst("N");
							newAss.setSgst("N");
						}

						Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, assessment.getIgmTransId(),
								assessment.getIgmNo(), assessment.getIgmLineNo());

						if (crg != null) {
							newAss.setNoOfPackages(crg.getNoOfPackages());

						}

						List<AssessmentContainerDTO> newAnxData = finalConData.stream().map(item -> {
							try {
								return (AssessmentContainerDTO) item.clone(); // Clone first
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
								return null;
							}
						}).filter(Objects::nonNull) // Remove null values from failed clones
								.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
																									// cloning
								.collect(Collectors.toList());

						List<AssessmentContainerDTO> filteredData = newAnxData.stream()
								.filter(c -> "H".equals(c.getServiceGroup())).collect(Collectors.toList());

						BigDecimal totalRates = filteredData.stream().map(AssessmentContainerDTO::getRates)
								.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

						List<AssessmentContainerDTO> filteredData1 = newAnxData.stream()
								.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());

						String crgDays = "";

						if (!filteredData1.isEmpty()) {
							crgDays = filteredData1.get(0).getExecutionUnit();
						}

						BigDecimal totalRates1 = filteredData1.stream().map(AssessmentContainerDTO::getRates)
								.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

						System.out.println("totalRates " + totalRates + " " + totalRates1);

						newAss.setContainerNo(con.getContainerNo());
						newAss.setContainerSize(con.getContainerSize());
						newAss.setContainerType(con.getContainerType());
						newAss.setGateInDate(con.getGateInDate());
						newAss.setGateOutDate(con.getGateoutDate() == null ? null : con.getGateoutDate());
						newAss.setGrossWeight(con.getGrossWt());
						newAss.setGateOutType(con.getGateOutType());
						newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
						newAss.setTypeOfContainer(con.getTypeOfContainer());
						newAss.setScanerType(con.getScannerType());
						newAss.setExaminedPercentage(new BigDecimal(con.getExamPercentage()));
						newAss.setWeighmentFlag("N");
						newAss.setCargoWeight(con.getCargoWt());
						newAss.setDestuffDate(con.getDestuffDate() == null ? null : con.getDestuffDate());
						newAss.setCalculateInvoice('N');
						newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
						newAss.setContainerHandlingAmt(totalRates);
						newAss.setContainerStorageAmt(totalRates1);
						newAss.setInvoiceCategory(assessment.getInvoiceCategory());
						newAss.setInvType("First");
						newAss.setSa(tempAssessment.getSaId());
						newAss.setSl(tempAssessment.getSlId());
						newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
						newAss.setSsrTransId(con.getSsrTransId());

						assessmentsheetprorepo.save(newAss);
						processnextidrepo.updateAuditTrail(cid, bid, "P05105", HoldNextIdD1, "2024");

						AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);

						AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

						System.out.println("finalConData " + finalConData);

						finalConData.stream().forEach(c -> {
							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
								CfinvsrvanxPro anx = new CfinvsrvanxPro();
								anx.setCompanyId(cid);
								anx.setBranchId(bid);
								anx.setLineNo(String.valueOf(anxSr.get()));
								anx.setErpDocRefNo(newAss.getIgmTransId());
								anx.setProcessTransId(HoldNextIdD1);
								anx.setServiceId(c.getServiceId());
								anx.setSrlNo(srNo1.get());
								anx.setDocRefNo(newAss.getIgmNo());
								anx.setIgmLineNo(newAss.getIgmLineNo());
								anx.setProfitcentreId(newAss.getProfitcentreId());
								anx.setInvoiceType("IMP");
								anx.setServiceUnit(c.getServiceUnit());
								anx.setExecutionUnit(c.getExecutionUnit());
								anx.setServiceUnit1(c.getServiceUnit1());
								anx.setExecutionUnit1(c.getExecutionUnit1());
								anx.setRate(c.getServiceRate());
								anx.setActualNoOfPackages(c.getRates());
								anx.setCurrencyId("INR");
								anx.setLocalAmt(c.getRates());
								anx.setInvoiceAmt(c.getRates());
								anx.setCommodityDescription(newAss.getCommodityDescription());
								anx.setDiscPercentage(c.getDiscPercentage());
								anx.setDiscValue(c.getDiscValue());
								anx.setmPercentage(c.getmPercentage());
								anx.setmAmount(c.getmAmount());
								anx.setAcCode(c.getAcCode());
								anx.setProcessTransDate(newAss.getAssesmentDate());
								anx.setProcessId("P01106");
								anx.setPartyId(newAss.getPartyId());
								anx.setWoNo(c.getWoNo());
								anx.setWoAmndNo(c.getWoAmndNo());
								anx.setContainerNo(c.getContainerNo());
								anx.setContainerStatus(con.getContainerStatus());
								anx.setGateOutDate(c.getGateoutDate());
								anx.setAddServices("N");
								anx.setStartDate(c.getGateInDate());
								anx.setInvoiceUptoDate(c.getInvoiceDate());
								anx.setInvoiceUptoWeek(c.getInvoiceDate());
								anx.setStatus("A");
								anx.setCreatedBy(user);
								anx.setCreatedDate(new Date());
								anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
								anx.setSrvManualFlag("N");
								anx.setCriteria("SA".equals(c.getCriteria()) ? "DW"
										: "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
								anx.setRangeFrom(c.getRangeFrom());
								anx.setRangeTo(c.getRangeTo());
								anx.setRangeType(c.getContainerStatus());
								anx.setGateOutId(c.getGateOutId());
								anx.setGatePassNo(c.getGatePassNo());
								anx.setRangeType(c.getCriteria());
								anx.setTaxId(c.getTaxId());
								anx.setExRate(c.getExRate());
								anx.setFreeDays(c.getFreeDays());

								if (("Y".equals(newAss.getIgst()))
										|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
									anx.setTaxPerc(c.getTaxPerc());
								} else {
									anx.setTaxPerc(BigDecimal.ZERO);
								}

								BigDecimal currentRate = c.getRates() != null ? c.getRates() : BigDecimal.ZERO;
								BigDecimal currentTotal = totalAmount.get() != null ? totalAmount.get()
										: BigDecimal.ZERO;

								// Perform addition
								totalAmount.set(currentTotal.add(currentRate));
								cfinvsrvanxprorepo.save(anx);

								srNo1.set(srNo1.get().add(new BigDecimal(1)));
							}
						});
						totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

						assessment.setAssesmentId(HoldNextIdD1);

						sr++;
					} else {

						List<String> conSize = new ArrayList<>();
						conSize.add("");
						conSize.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

						List<String> conTypeOfCon = new ArrayList<>();
						conTypeOfCon.add("");
						conTypeOfCon.add(con.getTypeOfContainer());

						String conStatus = "FCL".equals(con.getContainerStatus()) ? "IMPSEN" : "IMPCRGSEN";

						List<String> serviceMappingData = serviceMappingRepo.getServicesForImportInvoice3(cid, bid,
								con.getProfitcentreId(), conStatus, con.getGateOutType(), conSize, conTypeOfCon);

						if (serviceMappingData.isEmpty()) {
							return new ResponseEntity<>(
									"Service mapping not found for container no " + con.getContainerNo(),
									HttpStatus.CONFLICT);
						}

						Set<String> combinedService = new HashSet<>();
						combinedService.addAll(serviceMappingData);

						List<String> partyIdList = new ArrayList<>();
						partyIdList.add(assessment.getOthPartyId());
						partyIdList.add("");

						List<String> impIdList = new ArrayList<>();
						impIdList.add(assessment.getImporterId());
						impIdList.add("");

						List<String> chaIdList = new ArrayList<>();
						chaIdList.add(assessment.getCha());
						chaIdList.add("");

						List<String> fwIdList = new ArrayList<>();
						fwIdList.add(assessment.getOnAccountOf());
						fwIdList.add("");

						String tariffNo = cfstarrifrepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList,
								fwIdList, assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
								assessment.getOnAccountOf());

						if (tariffNo == null || tariffNo.isEmpty()) {
							return new ResponseEntity<>("Tariff no not found.", HttpStatus.CONFLICT);
						}

						List<SSRDtl> ssrServices = new ArrayList<>();

						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
							ssrServices = ssrRepo.getServiceId(cid, bid, con.getSsrTransId(), con.getContainerNo());

							if (!ssrServices.isEmpty()) {
								List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId) // Extract
																											// serviceId
										.collect(Collectors.toList()); // Collect into a list

								combinedService.addAll(ssrServiceId);
							}

						}

						List<SSRDtl> finalSsrServices = ssrServices;

						List<String> finalServices = new ArrayList<>(combinedService);

						List<Object[]> finalPricing = new ArrayList<>();
						List<String> notInPricing = new ArrayList<>();

						List<String> conSize1 = new ArrayList<>();
						conSize1.add("ALL");
						conSize1.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

						List<String> conTypeOfCon1 = new ArrayList<>();
						conTypeOfCon1.add("ALL");
						conTypeOfCon1.add(con.getTypeOfContainer());

						Date assData = assessment.getAssesmentDate(); // Original date
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
						String formattedDate = formatter.format(assData);
						Date parsedDate = formatter.parse(formattedDate);

						List<Object[]> pricingData = cfstariffservicerepo.getServiceRate(cid, bid, parsedDate,
								con.getContainerNo(), assessment.getIgmTransId(), assessment.getIgmNo(),
								assessment.getIgmLineNo(), finalServices, tariffNo, conSize1, conTypeOfCon1);

						if (!pricingData.isEmpty()) {
							List<Object[]> remainingPricing = pricingData.stream()
									.filter(data1 -> finalServices.contains(data1[0].toString()))
									.collect(Collectors.toList());

							notInPricing = finalServices.stream()
									.filter(service -> pricingData.stream()
											.noneMatch(data1 -> service.equals(data1[0].toString())))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						} else {
							notInPricing.addAll(finalServices);
						}

						if (!notInPricing.isEmpty()) {
							List<String> tempPricing = notInPricing;
							List<String> conSize2 = new ArrayList<>();
							conSize2.add("ALL");
							conSize2.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

							List<String> conTypeOfCon2 = new ArrayList<>();
							conTypeOfCon2.add("ALL");
							conTypeOfCon2.add(con.getTypeOfContainer());

							List<Object[]> pricingData1 = cfstariffservicerepo.getServiceRate(cid, bid, parsedDate,
									con.getContainerNo(), assessment.getIgmTransId(), assessment.getIgmNo(),
									assessment.getIgmLineNo(), notInPricing, "CFS1000001", conSize2, conTypeOfCon2);

							if (!pricingData1.isEmpty()) {
								List<Object[]> remainingPricing = pricingData1.stream()
										.filter(data1 -> tempPricing.contains(data1[0].toString()))
										.collect(Collectors.toList());

								finalPricing.addAll(remainingPricing);
							}
						}

						finalPricing.stream().forEach(f -> {
							AssessmentContainerDTO tempAss = new AssessmentContainerDTO();
							CFSDay day = cfsdayrepo.getData(cid, bid);

							System.out.println("con.getSsrTransId() " + con.getSsrTransId());

							if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

								if (!finalSsrServices.isEmpty()) {

									SSRDtl rate1 = finalSsrServices.stream()
											.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
											.orElse(null); // Return null if no match is found

									if (rate1 != null) {

										try {
											tempAss = (AssessmentContainerDTO) con.clone();

											Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(),
													day.getStartTime(), day.getEndTime());

											tempAss.setInvoiceDate(invDate);
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													f[13] != null ? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setRates(rate1.getTotalRate());
											tempAss.setServiceRate(rate1.getRate());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											finalConData.add(tempAss);
										} catch (CloneNotSupportedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									} else {
										try {
											tempAss = (AssessmentContainerDTO) con.clone();
											Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(),
													day.getStartTime(), day.getEndTime());

											tempAss.setInvoiceDate(invDate);
											if ("SA".equals(String.valueOf(f[8]))) {
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														f[13] != null ? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue(
														f[14] != null ? new BigDecimal(String.valueOf(f[14]))
																: BigDecimal.ZERO);
												tempAss.setmPercentage(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

												List<String> conSize2 = new ArrayList<>();
												conSize2.add("ALL");
												conSize2.add(("22".equals(con.getContainerSize())) ? "20"
														: con.getContainerSize());

												List<String> conTypeOfCon2 = new ArrayList<>();
												conTypeOfCon2.add("ALL");
												conTypeOfCon2.add(con.getTypeOfContainer());

												List<String> commodityType = new ArrayList<>();
												commodityType.add("ALL");
												commodityType.add(assessment.getCommodityCode());

												List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(
														cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
														String.valueOf(f[0]), String.valueOf(f[8]), conSize2,
														conTypeOfCon2, commodityType, con.getContainerSize(),
														con.getTypeOfContainer(), assessment.getCommodityCode());

												if (!rangeValues.isEmpty()) {

													BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
															.filter(r -> ((BigDecimal) r[8])
																	.compareTo(BigDecimal.ZERO) == 0) // Filter where
																										// rate is 0
															.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																							// BigDecimal
															.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
															.orElse(BigDecimal.ZERO); // Default value if no match found

													tempAss.setFreeDays(freeDaysForExportContainerStorage);

													String unit = String.valueOf(f[1]);

													AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
															BigDecimal.ZERO);
													if ("DAY".equals(unit)) {

														if (con.getDestuffDate() != null) {
															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getGateInDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;

															LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getDestuffDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());

															long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																	destuffDateTime1);

															if (daysBetween2 == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime1, destuffDateTime1) > 0) {
																daysBetween2 = 1;
															}

															tempAss.setExecutionUnit(String.valueOf(daysBetween2));
															tempAss.setExecutionUnit1("");

															BigDecimal totalRate = rangeValues.stream().map(r -> {

																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();

																BigDecimal rate = (BigDecimal) r[8];

																if (daysBetween1 >= fromRange
																		&& daysBetween1 <= toRange) {
																	serviceRate.set(rate); // Set the rate for the
																							// matching
																							// slab
																}

																long daysInSlab = Math.min(toRange, daysBetween1)
																		- Math.max(fromRange, 1) + 1;

																daysInSlab = Math.max(daysInSlab, 0);

																serviceRate.set(rate);
																return rate.multiply(new BigDecimal(daysInSlab));
															}).reduce(BigDecimal.ZERO, BigDecimal::add);
															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															tempAss.setServiceRate(serviceRate.get());

															BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																	assessment.getIgmTransId(), assessment.getIgmNo(),
																	assessment.getIgmLineNo(), con.getContainerNo());

															totalRate = totalRate.subtract(oldVal).setScale(3,
																	RoundingMode.HALF_UP);

															tempAss.setRates(totalRate);

														} else {

															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getGateInDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;

															LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																	convertToLocalDateTime(
																			con.getLastInvoiceUptoDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());

															long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																	destuffDateTime1);

															if (daysBetween2 == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime1, destuffDateTime1) > 0) {
																daysBetween2 = 1;
															}

															tempAss.setExecutionUnit(String.valueOf(daysBetween2));
															tempAss.setExecutionUnit1("");

															System.out.println("daysBetween " + daysBetween1);

															BigDecimal totalRate = rangeValues.stream().map(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue(); // Start
																												// of
																												// the
																												// slab
																int toRange = ((BigDecimal) r[7]).intValue(); // End of
																												// the
																												// slab
																BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
																										// the
																										// slab
																if (daysBetween1 >= fromRange
																		&& daysBetween1 <= toRange) {
																	serviceRate.set(rate); // Set the rate for the
																							// matching
																							// slab
																}
																// Calculate days in the current slab
																long daysInSlab = Math.min(toRange, daysBetween1)
																		- Math.max(fromRange, 1) + 1;

																daysInSlab = Math.max(daysInSlab, 0);
																return rate.multiply(new BigDecimal(daysInSlab));
															}).reduce(BigDecimal.ZERO, BigDecimal::add);
															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															tempAss.setServiceRate(serviceRate.get());

															BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																	assessment.getIgmTransId(), assessment.getIgmNo(),
																	assessment.getIgmLineNo(), con.getContainerNo());

															totalRate = totalRate.subtract(oldVal).setScale(3,
																	RoundingMode.HALF_UP);

															tempAss.setRates(totalRate);

														}
													}

													if ("WEEK".equals(unit)) {
														if (con.getDestuffDate() != null) {
															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getDestuffDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;
															long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

															LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																	convertToLocalDateTime(
																			con.getLastInvoiceUptoDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());

															long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																	destuffDateTime1);

															if (daysBetween2 == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime1, destuffDateTime1) > 0) {
																daysBetween2 = 1;
															}

															long weeksBetween1 = (long) Math.ceil(daysBetween2 / 7.0);

															tempAss.setExecutionUnit(String.valueOf(weeksBetween1));
															tempAss.setExecutionUnit1("");

															BigDecimal totalRate = rangeValues.stream().filter(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																// Include slabs that overlap with weeksBetween
																return weeksBetween >= fromRange;
															}).map(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																BigDecimal rate = (BigDecimal) r[8];

																// Adjust the fromRange to exclude week 0
																int adjustedFromRange = Math.max(fromRange, 1);

																// Determine actual weeks in this slab that contribute
																// to the total
																long weeksInSlab = Math.max(0,
																		Math.min(toRange, weeksBetween)
																				- adjustedFromRange + 1);

																// Exclude slabs where weeksInSlab is 0
																return weeksInSlab > 0
																		? rate.multiply(new BigDecimal(weeksInSlab))
																		: BigDecimal.ZERO;
															}).reduce(BigDecimal.ZERO, BigDecimal::add);

															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															if ("SM".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(
																		con.getArea() == null ? new BigDecimal(1)
																				: con.getArea()))
																		.setScale(3, RoundingMode.HALF_UP);
															}
															System.out.println(
																	"totalRate " + totalRate + " " + weeksBetween);
															tempAss.setServiceRate(serviceRate.get());

															BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																	assessment.getIgmTransId(), assessment.getIgmNo(),
																	assessment.getIgmLineNo(), con.getContainerNo());

															totalRate = totalRate.subtract(oldVal).setScale(3,
																	RoundingMode.HALF_UP);

															tempAss.setRates(totalRate);
														} else {
															LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																	convertToLocalDateTime(con.getGateInDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());
															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween;
															long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

															LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																	convertToLocalDateTime(
																			con.getLastInvoiceUptoDate()),
																	day.getStartTime());
															LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																	convertToLocalDateTime(con.getInvoiceDate()),
																	day.getEndTime());

															long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																	destuffDateTime1);

															if (daysBetween2 == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime1, destuffDateTime1) > 0) {
																daysBetween2 = 1;
															}

															long weeksBetween1 = (long) Math.ceil(daysBetween2 / 7.0);

															tempAss.setExecutionUnit(String.valueOf(weeksBetween1));
															tempAss.setExecutionUnit1("");

															BigDecimal totalRate = rangeValues.stream().filter(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																// Include slabs that overlap with weeksBetween
																return weeksBetween >= fromRange;
															}).map(r -> {
																int fromRange = ((BigDecimal) r[6]).intValue();
																int toRange = ((BigDecimal) r[7]).intValue();
																BigDecimal rate = (BigDecimal) r[8];

																// Adjust the fromRange to exclude week 0
																int adjustedFromRange = Math.max(fromRange, 1);

																// Determine actual weeks in this slab that contribute
																// to the total
																long weeksInSlab = Math.max(0,
																		Math.min(toRange, weeksBetween)
																				- adjustedFromRange + 1);

																// Exclude slabs where weeksInSlab is 0
																return weeksInSlab > 0
																		? rate.multiply(new BigDecimal(weeksInSlab))
																		: BigDecimal.ZERO;
															}).reduce(BigDecimal.ZERO, BigDecimal::add);

															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
															if ("SM".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(
																		con.getArea() == null ? new BigDecimal(1)
																				: con.getArea()))
																		.setScale(3, RoundingMode.HALF_UP);
															}
															System.out.println(
																	"totalRate " + totalRate + " " + weeksBetween);
															tempAss.setServiceRate(serviceRate.get());
															BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																	assessment.getIgmTransId(), assessment.getIgmNo(),
																	assessment.getIgmLineNo(), con.getContainerNo());

															totalRate = totalRate.subtract(oldVal).setScale(3,
																	RoundingMode.HALF_UP);

															tempAss.setRates(totalRate);
														}

													}

												}

											}

											finalConData.add(tempAss);

										} catch (CloneNotSupportedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								} else {
									try {
										tempAss = (AssessmentContainerDTO) con.clone();
										Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
												day.getEndTime());

										tempAss.setInvoiceDate(invDate);
										if ("SA".equals(String.valueOf(f[8]))) {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													f[13] != null ? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											List<String> conSize2 = new ArrayList<>();
											conSize2.add("ALL");
											conSize2.add(("22".equals(con.getContainerSize())) ? "20"
													: con.getContainerSize());

											List<String> conTypeOfCon2 = new ArrayList<>();
											conTypeOfCon2.add("ALL");
											conTypeOfCon2.add(con.getTypeOfContainer());

											List<String> commodityType = new ArrayList<>();
											commodityType.add("ALL");
											commodityType.add(assessment.getCommodityCode());

											List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(cid,
													bid, String.valueOf(f[3]), String.valueOf(f[4]),
													String.valueOf(f[0]), String.valueOf(f[8]), conSize2, conTypeOfCon2,
													commodityType, con.getContainerSize(), con.getTypeOfContainer(),
													assessment.getCommodityCode());

											if (!rangeValues.isEmpty()) {
												BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
														.filter(r -> ((BigDecimal) r[8])
																.compareTo(BigDecimal.ZERO) == 0) // Filter where
																									// rate is 0
														.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																						// BigDecimal
														.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
														.orElse(BigDecimal.ZERO); // Default value if no match found

												tempAss.setFreeDays(freeDaysForExportContainerStorage);
												String unit = String.valueOf(f[1]);

												AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
														BigDecimal.ZERO);
												if ("DAY".equals(unit)) {

													if (con.getDestuffDate() != null) {
														LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getGateInDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());
														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														final long daysBetween1 = daysBetween;

														LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getDestuffDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());

														long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																destuffDateTime1);

														if (daysBetween2 == 0 && ChronoUnit.HOURS
																.between(gateInDateTime1, destuffDateTime1) > 0) {
															daysBetween2 = 1;
														}

														tempAss.setExecutionUnit(String.valueOf(daysBetween2));
														tempAss.setExecutionUnit1("");

														BigDecimal totalRate = rangeValues.stream().map(r -> {

															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();

															BigDecimal rate = (BigDecimal) r[8];

															if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
																serviceRate.set(rate); // Set the rate for the
																						// matching
																						// slab
															}

															long daysInSlab = Math.min(toRange, daysBetween1)
																	- Math.max(fromRange, 1) + 1;

															daysInSlab = Math.max(daysInSlab, 0);

															serviceRate.set(rate);
															return rate.multiply(new BigDecimal(daysInSlab));
														}).reduce(BigDecimal.ZERO, BigDecimal::add);
														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
														tempAss.setServiceRate(serviceRate.get());

														BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																assessment.getIgmTransId(), assessment.getIgmNo(),
																assessment.getIgmLineNo(), con.getContainerNo());

														totalRate = totalRate.subtract(oldVal).setScale(3,
																RoundingMode.HALF_UP);

														tempAss.setRates(totalRate);

													} else {

														LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getGateInDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());
														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														final long daysBetween1 = daysBetween;

														LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getLastInvoiceUptoDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());

														long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																destuffDateTime1);

														if (daysBetween2 == 0 && ChronoUnit.HOURS
																.between(gateInDateTime1, destuffDateTime1) > 0) {
															daysBetween2 = 1;
														}

														tempAss.setExecutionUnit(String.valueOf(daysBetween2));
														tempAss.setExecutionUnit1("");

														System.out.println("daysBetween " + daysBetween1);

														BigDecimal totalRate = rangeValues.stream().map(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue(); // Start
																											// of
																											// the
																											// slab
															int toRange = ((BigDecimal) r[7]).intValue(); // End of
																											// the
																											// slab
															BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
																									// the
																									// slab
															if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
																serviceRate.set(rate); // Set the rate for the
																						// matching
																						// slab
															}
															// Calculate days in the current slab
															long daysInSlab = Math.min(toRange, daysBetween1)
																	- Math.max(fromRange, 1) + 1;

															daysInSlab = Math.max(daysInSlab, 0);
															return rate.multiply(new BigDecimal(daysInSlab));
														}).reduce(BigDecimal.ZERO, BigDecimal::add);
														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
														tempAss.setServiceRate(serviceRate.get());

														BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																assessment.getIgmTransId(), assessment.getIgmNo(),
																assessment.getIgmLineNo(), con.getContainerNo());

														totalRate = totalRate.subtract(oldVal).setScale(3,
																RoundingMode.HALF_UP);

														tempAss.setRates(totalRate);

													}
												}

												if ("WEEK".equals(unit)) {
													if (con.getDestuffDate() != null) {
														LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getDestuffDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());
														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														final long daysBetween1 = daysBetween;
														long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

														LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getLastInvoiceUptoDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());

														long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																destuffDateTime1);

														if (daysBetween2 == 0 && ChronoUnit.HOURS
																.between(gateInDateTime1, destuffDateTime1) > 0) {
															daysBetween2 = 1;
														}

														long weeksBetween1 = (long) Math.ceil(daysBetween2 / 7.0);

														tempAss.setExecutionUnit(String.valueOf(weeksBetween1));
														tempAss.setExecutionUnit1("");

														BigDecimal totalRate = rangeValues.stream().filter(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();
															// Include slabs that overlap with weeksBetween
															return weeksBetween >= fromRange;
														}).map(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();
															BigDecimal rate = (BigDecimal) r[8];

															// Adjust the fromRange to exclude week 0
															int adjustedFromRange = Math.max(fromRange, 1);

															// Determine actual weeks in this slab that contribute
															// to the total
															long weeksInSlab = Math.max(0,
																	Math.min(toRange, weeksBetween) - adjustedFromRange
																			+ 1);

															// Exclude slabs where weeksInSlab is 0
															return weeksInSlab > 0
																	? rate.multiply(new BigDecimal(weeksInSlab))
																	: BigDecimal.ZERO;
														}).reduce(BigDecimal.ZERO, BigDecimal::add);

														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
														if ("SM".equals(String.valueOf(f[7]))) {
															totalRate = (totalRate
																	.multiply(con.getArea() == null ? new BigDecimal(1)
																			: con.getArea()))
																	.setScale(3, RoundingMode.HALF_UP);
														}
														System.out
																.println("totalRate " + totalRate + " " + weeksBetween);
														tempAss.setServiceRate(serviceRate.get());

														BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																assessment.getIgmTransId(), assessment.getIgmNo(),
																assessment.getIgmLineNo(), con.getContainerNo());

														totalRate = totalRate.subtract(oldVal).setScale(3,
																RoundingMode.HALF_UP);

														tempAss.setRates(totalRate);
													} else {
														LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getGateInDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());
														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														final long daysBetween1 = daysBetween;
														long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

														LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getLastInvoiceUptoDate()),
																day.getStartTime());
														LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());

														long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
																destuffDateTime1);

														if (daysBetween2 == 0 && ChronoUnit.HOURS
																.between(gateInDateTime1, destuffDateTime1) > 0) {
															daysBetween2 = 1;
														}

														long weeksBetween1 = (long) Math.ceil(daysBetween2 / 7.0);

														tempAss.setExecutionUnit(String.valueOf(weeksBetween1));
														tempAss.setExecutionUnit1("");

														BigDecimal totalRate = rangeValues.stream().filter(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();
															// Include slabs that overlap with weeksBetween
															return weeksBetween >= fromRange;
														}).map(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();
															BigDecimal rate = (BigDecimal) r[8];

															// Adjust the fromRange to exclude week 0
															int adjustedFromRange = Math.max(fromRange, 1);

															// Determine actual weeks in this slab that contribute
															// to the total
															long weeksInSlab = Math.max(0,
																	Math.min(toRange, weeksBetween) - adjustedFromRange
																			+ 1);

															// Exclude slabs where weeksInSlab is 0
															return weeksInSlab > 0
																	? rate.multiply(new BigDecimal(weeksInSlab))
																	: BigDecimal.ZERO;
														}).reduce(BigDecimal.ZERO, BigDecimal::add);

														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
														if ("SM".equals(String.valueOf(f[7]))) {
															totalRate = (totalRate
																	.multiply(con.getArea() == null ? new BigDecimal(1)
																			: con.getArea()))
																	.setScale(3, RoundingMode.HALF_UP);
														}
														System.out
																.println("totalRate " + totalRate + " " + weeksBetween);
														tempAss.setServiceRate(serviceRate.get());
														BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
																assessment.getIgmTransId(), assessment.getIgmNo(),
																assessment.getIgmLineNo(), con.getContainerNo());

														totalRate = totalRate.subtract(oldVal).setScale(3,
																RoundingMode.HALF_UP);

														tempAss.setRates(totalRate);
													}

												}

											}

										}

										finalConData.add(tempAss);

									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							} else {
								try {
									tempAss = (AssessmentContainerDTO) con.clone();
									Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
											day.getEndTime());

									tempAss.setInvoiceDate(invDate);
									if ("SA".equals(String.valueOf(f[8]))) {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage(f[13] != null ? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue(f[14] != null ? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										List<String> conSize2 = new ArrayList<>();
										conSize2.add("ALL");
										conSize2.add(
												("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

										List<String> conTypeOfCon2 = new ArrayList<>();
										conTypeOfCon2.add("ALL");
										conTypeOfCon2.add(con.getTypeOfContainer());

										List<String> commodityType = new ArrayList<>();
										commodityType.add("ALL");
										commodityType.add(assessment.getCommodityCode());

										List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(cid, bid,
												String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												String.valueOf(f[8]), conSize2, conTypeOfCon2, commodityType,
												con.getContainerSize(), con.getTypeOfContainer(),
												assessment.getCommodityCode());

										System.out.println("rangeValues " + rangeValues);

										if (!rangeValues.isEmpty()) {

											BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
													.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																														// where
																														// rate
																														// is
																														// 0
													.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																					// BigDecimal
													.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
													.orElse(BigDecimal.ZERO); // Default value if no match found

											tempAss.setFreeDays(freeDaysForExportContainerStorage);

											String unit = String.valueOf(f[1]);

											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
													BigDecimal.ZERO);
											if ("DAY".equals(unit)) {

												if (con.getDestuffDate() != null) {
													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getGateInDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;

													LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getDestuffDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());

													long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
															destuffDateTime1);

													if (daysBetween2 == 0 && ChronoUnit.HOURS.between(gateInDateTime1,
															destuffDateTime1) > 0) {
														daysBetween2 = 1;
													}

													tempAss.setExecutionUnit(String.valueOf(daysBetween2));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().map(r -> {

														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();

														BigDecimal rate = (BigDecimal) r[8];

														if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
															serviceRate.set(rate); // Set the rate for the
																					// matching
																					// slab
														}

														long daysInSlab = Math.min(toRange, daysBetween1)
																- Math.max(fromRange, 1) + 1;

														daysInSlab = Math.max(daysInSlab, 0);

														serviceRate.set(rate);
														return rate.multiply(new BigDecimal(daysInSlab));
													}).reduce(BigDecimal.ZERO, BigDecimal::add);
													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													tempAss.setServiceRate(serviceRate.get());

													BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
															assessment.getIgmTransId(), assessment.getIgmNo(),
															assessment.getIgmLineNo(), con.getContainerNo());

													totalRate = totalRate.subtract(oldVal).setScale(3,
															RoundingMode.HALF_UP);

													tempAss.setRates(totalRate);

												} else {

													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getGateInDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;

													LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getLastInvoiceUptoDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());

													long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
															destuffDateTime1);

													if (daysBetween2 == 0 && ChronoUnit.HOURS.between(gateInDateTime1,
															destuffDateTime1) > 0) {
														daysBetween2 = 1;
													}

													tempAss.setExecutionUnit(String.valueOf(daysBetween2));
													tempAss.setExecutionUnit1("");

													System.out.println("daysBetween " + daysBetween1);

													BigDecimal totalRate = rangeValues.stream().map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue(); // Start
																										// of
																										// the
																										// slab
														int toRange = ((BigDecimal) r[7]).intValue(); // End of
																										// the
																										// slab
														BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
																								// the
																								// slab
														if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
															serviceRate.set(rate); // Set the rate for the
																					// matching
																					// slab
														}
														// Calculate days in the current slab
														long daysInSlab = Math.min(toRange, daysBetween1)
																- Math.max(fromRange, 1) + 1;

														daysInSlab = Math.max(daysInSlab, 0);
														return rate.multiply(new BigDecimal(daysInSlab));
													}).reduce(BigDecimal.ZERO, BigDecimal::add);
													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													tempAss.setServiceRate(serviceRate.get());

													BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
															assessment.getIgmTransId(), assessment.getIgmNo(),
															assessment.getIgmLineNo(), con.getContainerNo());

													totalRate = totalRate.subtract(oldVal).setScale(3,
															RoundingMode.HALF_UP);

													tempAss.setRates(totalRate);

												}
											}

											if ("WEEK".equals(unit)) {
												if (con.getDestuffDate() != null) {
													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getDestuffDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

													LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getLastInvoiceUptoDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());

													long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
															destuffDateTime1);

													if (daysBetween2 == 0 && ChronoUnit.HOURS.between(gateInDateTime1,
															destuffDateTime1) > 0) {
														daysBetween2 = 1;
													}

													long weeksBetween1 = (long) Math.ceil(daysBetween2 / 7.0);

													tempAss.setExecutionUnit(String.valueOf(weeksBetween1));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().filter(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														// Include slabs that overlap with weeksBetween
														return weeksBetween >= fromRange;
													}).map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														BigDecimal rate = (BigDecimal) r[8];

														// Adjust the fromRange to exclude week 0
														int adjustedFromRange = Math.max(fromRange, 1);

														// Determine actual weeks in this slab that contribute to the
														// total
														long weeksInSlab = Math.max(0, Math.min(toRange, weeksBetween)
																- adjustedFromRange + 1);

														// Exclude slabs where weeksInSlab is 0
														return weeksInSlab > 0
																? rate.multiply(new BigDecimal(weeksInSlab))
																: BigDecimal.ZERO;
													}).reduce(BigDecimal.ZERO, BigDecimal::add);

													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													if ("SM".equals(String.valueOf(f[7]))) {
														totalRate = (totalRate
																.multiply(con.getArea() == null ? new BigDecimal(1)
																		: con.getArea()))
																.setScale(3, RoundingMode.HALF_UP);
													}
													System.out.println("totalRate " + totalRate + " " + weeksBetween);
													tempAss.setServiceRate(serviceRate.get());

													BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
															assessment.getIgmTransId(), assessment.getIgmNo(),
															assessment.getIgmLineNo(), con.getContainerNo());

													totalRate = totalRate.subtract(oldVal).setScale(3,
															RoundingMode.HALF_UP);

													tempAss.setRates(totalRate);
												} else {
													LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getGateInDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

													LocalDateTime gateInDateTime1 = adjustToCustomStartOfDay(
															convertToLocalDateTime(con.getLastInvoiceUptoDate()),
															day.getStartTime());
													LocalDateTime destuffDateTime1 = adjustToCustomEndOfDay(
															convertToLocalDateTime(con.getInvoiceDate()),
															day.getEndTime());

													long daysBetween2 = ChronoUnit.DAYS.between(gateInDateTime1,
															destuffDateTime1);

													if (daysBetween2 == 0 && ChronoUnit.HOURS.between(gateInDateTime1,
															destuffDateTime1) > 0) {
														daysBetween2 = 1;
													}

													long weeksBetween1 = (long) Math.ceil(daysBetween2 / 7.0);

													tempAss.setExecutionUnit(String.valueOf(weeksBetween1));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().filter(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														// Include slabs that overlap with weeksBetween
														return weeksBetween >= fromRange;
													}).map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														BigDecimal rate = (BigDecimal) r[8];

														// Adjust the fromRange to exclude week 0
														int adjustedFromRange = Math.max(fromRange, 1);

														// Determine actual weeks in this slab that contribute to the
														// total
														long weeksInSlab = Math.max(0, Math.min(toRange, weeksBetween)
																- adjustedFromRange + 1);

														// Exclude slabs where weeksInSlab is 0
														return weeksInSlab > 0
																? rate.multiply(new BigDecimal(weeksInSlab))
																: BigDecimal.ZERO;
													}).reduce(BigDecimal.ZERO, BigDecimal::add);

													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													if ("SM".equals(String.valueOf(f[7]))) {
														totalRate = (totalRate
																.multiply(con.getArea() == null ? new BigDecimal(1)
																		: con.getArea()))
																.setScale(3, RoundingMode.HALF_UP);
													}
													System.out.println("totalRate " + totalRate + " " + weeksBetween);
													tempAss.setServiceRate(serviceRate.get());
													BigDecimal oldVal = cfigmcnrepo.getRateByServiceId(cid, bid,
															assessment.getIgmTransId(), assessment.getIgmNo(),
															assessment.getIgmLineNo(), con.getContainerNo());

													totalRate = totalRate.subtract(oldVal).setScale(3,
															RoundingMode.HALF_UP);

													tempAss.setRates(totalRate);
												}

											}

										}

									}

									finalConData.add(tempAss);

								} catch (CloneNotSupportedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						});

						AssessmentSheetPro tempAssessment = (AssessmentSheetPro) assessment.clone();

						AssessmentSheetPro newAss = tempAssessment;

						newAss.setCompanyId(cid);
						newAss.setBranchId(bid);
						newAss.setAssesmentLineNo(String.valueOf(sr));
						newAss.setStatus('A');
						newAss.setCreatedBy(user);
						newAss.setCreatedDate(new Date());
						newAss.setApprovedBy(user);
						newAss.setApprovedDate(new Date());
						newAss.setAssesmentDate(new Date());
						newAss.setTransType("Import Container");
						newAss.setProfitcentreId(con.getProfitcentreId());

						newAss.setAssesmentId(HoldNextIdD1);

						Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

						String billingId = "IMP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
								: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
										: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
												: assessment.getOthPartyId();

						String billingAdd = "IMP".equals(assessment.getBillingParty())
								? String.valueOf(assessment.getImpSrNo())
								: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
										: "FWR".equals(assessment.getBillingParty())
												? String.valueOf(assessment.getAccSrNo())
												: assessment.getOthSrNo();

						PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

						newAss.setPartyId(billingId);
						newAss.setPartySrNo(new BigDecimal(billingAdd));
						newAss.setPayableParty(assessment.getBillingParty());
						newAss.setPayablePartyId(billingId);

						if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
							newAss.setIgst("Y");
							newAss.setCgst("N");
							newAss.setSgst("N");
						} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
							newAss.setIgst("E");
							newAss.setCgst("N");
							newAss.setSgst("N");
						}

						else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {

							if (b.getState().equals(p.getState())) {
								newAss.setIgst("N");
								newAss.setCgst("Y");
								newAss.setSgst("Y");
							} else {
								newAss.setIgst("Y");
								newAss.setCgst("N");
								newAss.setSgst("N");
							}

						} else {
							newAss.setIgst("N");
							newAss.setCgst("N");
							newAss.setSgst("N");
						}

						Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, assessment.getIgmTransId(),
								assessment.getIgmNo(), assessment.getIgmLineNo());

						if (crg != null) {
							newAss.setNoOfPackages(crg.getNoOfPackages());

						}

						List<AssessmentContainerDTO> newAnxData = finalConData.stream().map(item -> {
							try {
								return (AssessmentContainerDTO) item.clone(); // Clone first
							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
								return null;
							}
						}).filter(Objects::nonNull) // Remove null values from failed clones
								.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
																									// cloning
								.collect(Collectors.toList());

						List<AssessmentContainerDTO> filteredData1 = newAnxData.stream()
								.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());

						String crgDays = "";

						if (!filteredData1.isEmpty()) {
							crgDays = filteredData1.get(0).getExecutionUnit();
						}

						BigDecimal totalRates1 = filteredData1.stream().map(AssessmentContainerDTO::getRates)
								.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

						newAss.setContainerNo(con.getContainerNo());
						newAss.setContainerSize(con.getContainerSize());
						newAss.setContainerType(con.getContainerType());
						newAss.setGateInDate(con.getGateInDate());
						newAss.setGateOutDate(con.getGateoutDate() == null ? null : con.getGateoutDate());
						newAss.setGrossWeight(con.getGrossWt());
						newAss.setGateOutType(con.getGateOutType());
						newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
						newAss.setTypeOfContainer(con.getTypeOfContainer());
						newAss.setScanerType(con.getScannerType());
						newAss.setExaminedPercentage(new BigDecimal(con.getExamPercentage()));
						newAss.setWeighmentFlag("N");
						newAss.setCargoWeight(con.getCargoWt());
						newAss.setDestuffDate(con.getDestuffDate() == null ? null : con.getDestuffDate());
						newAss.setCalculateInvoice('N');
						newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
						newAss.setContainerHandlingAmt(BigDecimal.ZERO);
						newAss.setContainerStorageAmt(totalRates1);
						newAss.setInvoiceCategory(assessment.getInvoiceCategory());
						newAss.setInvType("Second");
						newAss.setSa(tempAssessment.getSaId());
						newAss.setSl(tempAssessment.getSlId());
						newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
						newAss.setSsrTransId(con.getSsrTransId());
						newAss.setLastAssesmentDate(assessment.getLastAssesmentDate());
						newAss.setLastAssesmentId(assessment.getLastAssesmentId());
						newAss.setLastInvoiceDate(assessment.getLastInvoiceDate());
						newAss.setLastInvoiceNo(assessment.getLastInvoiceNo());
						newAss.setLastInvoiceUptoDate(con.getLastInvoiceUptoDate());

						assessmentsheetprorepo.save(newAss);
						processnextidrepo.updateAuditTrail(cid, bid, "P05105", HoldNextIdD1, "2024");

						AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
						AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

						finalConData.stream().forEach(c -> {
							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
								CfinvsrvanxPro anx = new CfinvsrvanxPro();
								anx.setCompanyId(cid);
								anx.setBranchId(bid);
								anx.setErpDocRefNo(newAss.getIgmTransId());
								anx.setLineNo(String.valueOf(anxSr.get()));
								anx.setProcessTransId(HoldNextIdD1);
								anx.setServiceId(c.getServiceId());
								anx.setSrlNo(srNo1.get());
								anx.setDocRefNo(newAss.getIgmNo());
								anx.setIgmLineNo(newAss.getIgmLineNo());
								anx.setProfitcentreId(newAss.getProfitcentreId());
								anx.setInvoiceType("IMP");
								anx.setServiceUnit(c.getServiceUnit());
								anx.setExecutionUnit(c.getExecutionUnit());
								anx.setServiceUnit1(c.getServiceUnit1());
								anx.setExecutionUnit1(c.getExecutionUnit1());
								anx.setRate(c.getServiceRate());
								anx.setActualNoOfPackages(c.getRates());
								anx.setCurrencyId("INR");
								anx.setLocalAmt(c.getRates());
								anx.setInvoiceAmt(c.getRates());
								anx.setCommodityDescription(newAss.getCommodityDescription());
								anx.setDiscPercentage(c.getDiscPercentage());
								anx.setDiscValue(c.getDiscValue());
								anx.setmPercentage(c.getmPercentage());
								anx.setmAmount(c.getmAmount());
								anx.setAcCode(c.getAcCode());
								anx.setProcessTransDate(newAss.getAssesmentDate());
								anx.setProcessId("P01106");
								anx.setPartyId(newAss.getPartyId());
								anx.setWoNo(c.getWoNo());
								anx.setWoAmndNo(c.getWoAmndNo());
								anx.setContainerNo(c.getContainerNo());
								anx.setContainerStatus(con.getContainerStatus());
								anx.setGateOutDate(c.getGateoutDate());
								anx.setAddServices("N");
								anx.setStartDate(c.getGateInDate());
								anx.setInvoiceUptoDate(c.getInvoiceDate());
								anx.setInvoiceUptoWeek(c.getInvoiceDate());
								anx.setStatus("A");
								anx.setCreatedBy(user);
								anx.setCreatedDate(new Date());
								anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
								anx.setSrvManualFlag("N");
								anx.setCriteria("SA".equals(c.getCriteria()) ? "DW"
										: "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
								anx.setRangeFrom(c.getRangeFrom());
								anx.setRangeTo(c.getRangeTo());
								anx.setRangeType(c.getContainerStatus());
								anx.setGateOutId(c.getGateOutId());
								anx.setGatePassNo(c.getGatePassNo());
								anx.setRangeType(c.getCriteria());
								anx.setTaxId(c.getTaxId());
								anx.setExRate(c.getExRate());
								anx.setFreeDays(c.getFreeDays());
								if (("Y".equals(newAss.getIgst()))
										|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
									anx.setTaxPerc(c.getTaxPerc());
								} else {
									anx.setTaxPerc(BigDecimal.ZERO);
								}

								BigDecimal currentRate = c.getRates() != null ? c.getRates() : BigDecimal.ZERO;
								BigDecimal currentTotal = totalAmount.get() != null ? totalAmount.get()
										: BigDecimal.ZERO;

								// Perform addition
								totalAmount.set(currentTotal.add(currentRate));

								cfinvsrvanxprorepo.save(anx);

								srNo1.set(srNo1.get().add(new BigDecimal(1)));
							}
						});
						totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

						assessment.setAssesmentId(HoldNextIdD1);

						sr++;

					}
				}
			} else {
				List<AssessmentSheetPro> existingData = assessmentsheetprorepo.getDataByAssessmentId(cid, bid,
						assessment.getAssesmentId());

				if (existingData.isEmpty()) {
					return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
				} else {
					existingData.stream().forEach(c -> {
						c.setCreditType(assessment.getCreditType());
						c.setComments(assessment.getComments());
						c.setIntComments(assessment.getIntComments());
						assessmentsheetprorepo.save(c);
					});
				}
			}

			List<Object[]> result = assessmentsheetprorepo.getAssessmentData(cid, bid, assessment.getAssesmentId());

			if (result.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				result.stream().forEach(r -> {
					BigDecimal rate = new BigDecimal(String.valueOf(r[71]));
					BigDecimal taxPerc = new BigDecimal(String.valueOf(r[74]));

					System.out.println("TaxPerc " + String.valueOf(r[69]) + " " + taxPerc);
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

				String pId = "";

				if ("CHA".equals(assessment.getBillingParty())) {

					pId = assessment.getCha();

				} else if ("IMP".equals(assessment.getBillingParty())) {
					pId = assessment.getImporterId();
				} else if ("FWR".equals(assessment.getBillingParty())) {
					pId = assessment.getOnAccountOf();
				} else if ("OTH".equals(assessment.getBillingParty())) {
					pId = assessment.getOthPartyId();
				}

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

			// return null;
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private LocalDateTime convertToLocalDateTime(Date date) {
		if (date == null) {
			return null; // Handle null date if necessary
		}

		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	private LocalDateTime convertToLocalDateTime1(Date date) {
		if (date == null) {
			return null; // Handle null date if necessary
		}

		// Handle the case for java.sql.Time separately
		if (date instanceof Time) {
			// Convert Time to LocalDateTime by getting today's date and setting the time
			// from the java.sql.Time
			LocalDateTime now = LocalDateTime.now();
			return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), date.getHours(),
					date.getMinutes(), date.getSeconds(), 0);
		}

		// For other Date types (java.util.Date), convert normally
		LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return localDateTime.withYear(LocalDateTime.now().getYear()) // Set to current year
				.withMonth(LocalDateTime.now().getMonthValue()) // Set to current month
				.withDayOfMonth(LocalDateTime.now().getDayOfMonth()); // Set to current day
	}

	private LocalDateTime adjustToCustomStartOfDay(LocalDateTime dateTime, Date startTime) {
		LocalDateTime startOfDayTime = convertToLocalDateTime1(startTime);

		// Extract dynamic start time values
		int startHour = startOfDayTime.getHour();
		int startMinute = startOfDayTime.getMinute();
		int startSecond = startOfDayTime.getSecond();
		int startNano = startOfDayTime.getNano();

		// If time is before the dynamic start time, adjust to the previous day
		if (dateTime.getHour() < startHour || (dateTime.getHour() == startHour && dateTime.getMinute() < startMinute)) {
			return dateTime.minusDays(1).withHour(startHour).withMinute(startMinute).withSecond(startSecond)
					.withNano(startNano);
		}
		// Otherwise, set to the dynamic start time of the current day
		return dateTime.withHour(startHour).withMinute(startMinute).withSecond(startSecond).withNano(startNano);
	}

	// Helper method to adjust DestuffDate to custom end of day logic
	private LocalDateTime adjustToCustomEndOfDay(LocalDateTime dateTime, Date endTime) {
		LocalDateTime endOfDayTime = convertToLocalDateTime1(endTime);

		// Extract dynamic end time values
		int endHour = endOfDayTime.getHour();
		int endMinute = endOfDayTime.getMinute();
		int endSecond = endOfDayTime.getSecond();
		int endNano = endOfDayTime.getNano();

		// If time is before the dynamic end time, keep within the same day
		if (dateTime.getHour() < endHour || (dateTime.getHour() == endHour && dateTime.getMinute() <= endMinute)) {
			return dateTime.withHour(endHour).withMinute(endMinute).withSecond(endSecond).withNano(endNano);
		}
		// Otherwise, set to the dynamic end time of the next day
		return dateTime.plusDays(1).withHour(endHour).withMinute(endMinute).withSecond(endSecond).withNano(endNano);
	}

	public static Date adjustInvoiceDate(Date invoiceDate, Date startTime, Date endTime) {
		// Extract the time components from startTime and endTime
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startTime);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);

		// Extract the date component from invoiceDate and apply start and end times
		Calendar invoiceCal = Calendar.getInstance();
		invoiceCal.setTime(invoiceDate);

		Calendar adjustedStartCal = (Calendar) invoiceCal.clone();
		adjustedStartCal.set(Calendar.HOUR_OF_DAY, startCal.get(Calendar.HOUR_OF_DAY));
		adjustedStartCal.set(Calendar.MINUTE, startCal.get(Calendar.MINUTE));
		adjustedStartCal.set(Calendar.SECOND, startCal.get(Calendar.SECOND));

		Calendar adjustedEndCal = (Calendar) invoiceCal.clone();
		adjustedEndCal.set(Calendar.HOUR_OF_DAY, endCal.get(Calendar.HOUR_OF_DAY));
		adjustedEndCal.set(Calendar.MINUTE, endCal.get(Calendar.MINUTE));
		adjustedEndCal.set(Calendar.SECOND, endCal.get(Calendar.SECOND));

		// Logic for adjusting the invoice date
		if (invoiceCal.before(adjustedStartCal)) {
			// Before start time: Adjust to same day's start time
			return adjustedStartCal.getTime();
		} else {
			// After start time: Adjust to the next day's start time
			adjustedStartCal.add(Calendar.DATE, 1);
			return adjustedStartCal.getTime();
		}
	}

	@Transactional
	public ResponseEntity<?> saveImportInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheetPro assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheetPro.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheetPro> oldAssessmentData = assessmentsheetprorepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<CfinvsrvanxPro> oldAnxData = cfinvsrvanxprorepo.getDataByProcessTransId(cid, bid,
				assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05107";
		} else {
			processNextId = "P05106";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

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

			cfinvsrvanxprorepo.save(o);

		});

		List<CfinvsrvanxPro> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, CfinvsrvanxPro> groupedData = newAnxData.stream()
				.collect(Collectors.toMap(CfinvsrvanxPro::getServiceId, // Key:
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
		List<CfinvsrvanxPro> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			CfinvsrvdtlPro dtl = new CfinvsrvdtlPro();
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

			cfinvsrvdtlprorepo.save(dtl);
		});

		AssessmentSheetPro assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05108", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		CfinvsrvPro srv = new CfinvsrvPro();
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
		srv.setInvoiceType("IMP");
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

		cfinvsrvprorepo.save(srv);

		System.out.println(" processNextId, HoldNextIdD1 " + processNextId + " " + HoldNextIdD1);

		processnextidrepo.updateAuditTrail(cid, bid, processNextId, HoldNextIdD1, "2024");
		processnextidrepo.updateAuditTrail(cid, bid, "P05108", HoldNextIdD2, "2024");

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

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

			assessmentsheetprorepo.save(o);

		});

		List<Object[]> result = assessmentsheetprorepo.getAssessmentData(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		CfinvsrvPro existingSrv = cfinvsrvprorepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		AssessmentSheetPro tdsData = assessmentsheetprorepo.getTdsData(cid, bid, assessment.getAssesmentId());

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);

		if (tdsData != null) {
			finalResult.put("tdsDeductee", tdsData.getTds());
			Party p = partyRepository.getDataById(cid, bid, tdsData.getTdsDeductee());

			if (p != null) {

				finalResult.put("tdsperc", p.getTdsPercentage());
				finalResult.put("tanNo", p.getTanNoId());
			} else {
				finalResult.put("tdsperc", "");
				finalResult.put("tanNo", "");
			}

		} else {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}

	public ResponseEntity<?> searchInvoiceData1(String cid, String bid, String val) {

		List<Object[]> data = assessmentsheetprorepo.searchInvoiceData1(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<?> getSelectedInvoiceData1(String cid, String bid, String assId, String invoiceId) {

		List<Object[]> result = assessmentsheetprorepo.getAssessmentData(cid, bid, assId);

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		CfinvsrvPro existingSrv = cfinvsrvprorepo.getDataByInvoiceNo(cid, bid, invoiceId);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		AssessmentSheetPro tdsData = assessmentsheetprorepo.getTdsData(cid, bid, assId);

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);

		if (tdsData != null) {
			finalResult.put("tdsDeductee", tdsData.getTds());
			Party p = partyRepository.getDataById(cid, bid, tdsData.getTdsDeductee());

			if (p != null) {

				finalResult.put("tdsperc", p.getTdsPercentage());
				finalResult.put("tanNo", p.getTanNoId());
			} else {
				finalResult.put("tdsperc", "");
				finalResult.put("tanNo", "");
			}

		} else {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);
	}

	public ResponseEntity<?> getAllCfInvSrvAnxListByAssesmentId(String companyId, String branchId, String assesmentId,
			String assesmentLineNo, String containerNo, String profiCentreId) {
		List<CfinvsrvanxPro> cfinvSrvList = cfinvsrvanxprorepo.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId,
				profiCentreId, assesmentId, assesmentLineNo);

		return ResponseEntity.ok(cfinvSrvList);
	}

	public ResponseEntity<?> saveAddServiceContainerWise(String companyId, String branchId, String user,
			Map<String, Object> resultList) {

		try {

			AssessmentSheetPro assessment = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("assesmentSheet")), AssessmentSheetPro.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<CfinvsrvanxPro> cfInvsrvanxData = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
					new TypeReference<List<CfinvsrvanxPro>>() {
					});

			List<CfinvsrvanxPro> filteredData = cfInvsrvanxData.stream()
					.filter(detail -> "Y".equalsIgnoreCase(detail.getAddServices())).collect(Collectors.toList());
			BigDecimal zero = BigDecimal.ZERO;

			CfinvsrvanxPro object1 = filteredData.get(0);

			AssessmentSheetPro existingData = assessmentsheetprorepo.getDataByAssessmentIdAndContNo(companyId, branchId,
					assessment.getAssesmentId(),object1.getContainerNo());

			if (existingData == null) {
				return new ResponseEntity<>("Assessment Data not found", HttpStatus.CONFLICT);
			}


			List<CfinvsrvanxPro> saveToData = new ArrayList<>();
			for (CfinvsrvanxPro anx : filteredData) {

				boolean existsByAssesmentIdAndSrlNo = cfinvsrvanxprorepo.existsByAssesmentIdAndSrlNo1(companyId,
						branchId, existingData.getProfitcentreId(), existingData.getAssesmentId(), anx.getSrlNo(),
						anx.getContainerNo());

				if (existsByAssesmentIdAndSrlNo) {

					cfinvsrvanxprorepo.updateCfinvsrvanx1(companyId, branchId, existingData.getProfitcentreId(),
							existingData.getAssesmentId(), anx.getSrlNo(), anx.getExecutionUnit(), anx.getExecutionUnit1(),
							anx.getRate(), anx.getActualNoOfPackages(), anx.getContainerNo());

				} else {

					anx.setCompanyId(companyId);
					anx.setBranchId(branchId);
					anx.setErpDocRefNo(existingData.getIgmTransId());
					anx.setProcessTransId(existingData.getAssesmentId());
					anx.setAddServices("Y");
					anx.setDocRefNo(existingData.getIgmNo());
					anx.setIgmLineNo(existingData.getIgmLineNo());
					anx.setProfitcentreId(existingData.getProfitcentreId());
					anx.setInvoiceType("IMP");
					anx.setLineNo(existingData.getAssesmentLineNo());

//				anx.setActualNoOfPackages(c.getRates());
					anx.setLocalAmt(anx.getActualNoOfPackages());
					anx.setInvoiceAmt(anx.getActualNoOfPackages());
					anx.setCommodityDescription(existingData.getCommodityDescription());
					anx.setDiscPercentage(zero);
					anx.setDiscValue(zero);
					anx.setmPercentage(zero);
					anx.setmAmount(zero);

//				anx.setAcCode(c.getAcCode());

					anx.setProcessTransDate(existingData.getAssesmentDate());
					anx.setProcessId("P01106");
					anx.setPartyId(existingData.getPartyId());

//				anx.setGateOutDate(c.getGateoutDate());
//				anx.setStartDate(c.getGateInDate());
//				anx.setInvoiceUptoDate(c.getInvoiceDate());
//				anx.setInvoiceUptoWeek(c.getInvoiceDate());
					anx.setStatus("A");
					anx.setCreatedBy(user);
					anx.setCreatedDate(new Date());

					anx.setTaxApp(String.valueOf(existingData.getTaxApplicable()));
					anx.setSrvManualFlag("N");
					anx.setCriteria("CNTR");
					anx.setRangeFrom(zero);
					anx.setRangeTo(zero);
//				anx.setGateOutId(c.getGateOutId());
//				anx.setGatePassNo(c.getGatePassNo());
					anx.setRangeType("CNTR");
//				anx.setTaxId(c.getTaxId());
					anx.setExRate(zero);

					if (("Y".equals(existingData.getIgst())) || ("Y".equals(existingData.getCgst()) && "Y".equals(existingData.getSgst()))) {
						anx.setTaxPerc(anx.getTaxPerc());
					} else {
						anx.setTaxPerc(zero);
					}
					saveToData.add(anx);
				}
			}

			cfinvsrvanxprorepo.saveAll(saveToData);

			List<CfinvsrvanxPro> cfinvSrvList = cfinvsrvanxprorepo.getAllCfInvSrvAnxListByAssesmentId1(companyId,
					branchId, existingData.getProfitcentreId(), existingData.getAssesmentId(), object1.getContainerNo());

			return ResponseEntity.ok(cfinvSrvList);
		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return null;
		}
	}

	public ResponseEntity<?> getDataByAssessmentId(String cid, String bid, String id, String billingPartyId,
			String creditType) {

		List<Object[]> result = assessmentsheetprorepo.getAssessmentData(cid, bid, id);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
			AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

			result.stream().forEach(r -> {
				BigDecimal rate = new BigDecimal(String.valueOf(r[71]));
				BigDecimal taxPerc = new BigDecimal(String.valueOf(r[74]));

				System.out.println("TaxPerc " + String.valueOf(r[69]) + " " + taxPerc);
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

			Party p = partyRepository.getDataById(cid, bid, billingPartyId);

			Map<String, Object> finalResult = new HashMap<>();
			finalResult.put("result", result);
			finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
			finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

			if (p != null) {

				if ("P".equals(creditType)) {
					Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, billingPartyId, "AD");

					finalResult.put("advanceData", advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);

				finalResult.put("tanNo", p.getTanNoId());
				finalResult.put("tdsPerc", p.getTdsPercentage());
				finalResult.put("creditAllowed", allowedValue);
			}

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}

	}

	public ResponseEntity<?> getAllContainerListOfAssessMentSheet(String companyId, String branchId, String assesmentId,
			String profiCentreId) {
		List<CfinvsrvanxPro> assessmentSheetList = cfinvsrvanxprorepo.getAllContainerListOfAssessMentSheet1(companyId,
				branchId, profiCentreId, assesmentId);

		return ResponseEntity.ok(assessmentSheetList);
	}

	public ResponseEntity<?> saveAddServiceServiceWise(String companyId, String branchId, String user,
			Map<String, Object> resultList) {

		try {

			AssessmentSheetPro assessment = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("assesmentSheet")), AssessmentSheetPro.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<CfinvsrvanxPro> cfInvsrvanxData = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
					new TypeReference<List<CfinvsrvanxPro>>() {
					});

			BigDecimal zero = BigDecimal.ZERO;

			List<AssessmentSheetPro> existingData = assessmentsheetprorepo.getDataByAssessmentId(companyId, branchId,
					assessment.getAssesmentId());

			if (existingData.isEmpty()) {
				return new ResponseEntity<>("Assessment Data not found", HttpStatus.CONFLICT);
			}

			

			for (CfinvsrvanxPro anx : cfInvsrvanxData) {
				 AssessmentSheetPro data = existingData.stream()
					        .filter(c -> c.getContainerNo().equals(anx.getContainerNo()))
					        .findFirst()
					        .orElse(null);

				Optional<BigDecimal> highestSRLNoOptional = cfinvsrvanxprorepo.getHighestSrlNoByContainerNo2(companyId,
						branchId, data.getProfitcentreId(), data.getAssesmentId(), anx.getContainerNo());
				BigDecimal highestSRLNo = highestSRLNoOptional.orElse(BigDecimal.ZERO);
				BigDecimal highestSRLNoNew = highestSRLNo.add(BigDecimal.ONE);

				anx.setCompanyId(companyId);
				anx.setBranchId(branchId);
				anx.setErpDocRefNo(data.getIgmTransId());
				anx.setProcessTransId(data.getAssesmentId());
				anx.setAddServices("Y");
				anx.setDocRefNo(data.getIgmNo());
				anx.setIgmLineNo(data.getIgmLineNo());
				anx.setProfitcentreId(data.getProfitcentreId());
				anx.setInvoiceType("IMP");
				anx.setSrlNo(highestSRLNoNew);
				anx.setLineNo(data.getAssesmentLineNo());

//			anx.setActualNoOfPackages(c.getRates());
				anx.setLocalAmt(anx.getActualNoOfPackages());
				anx.setInvoiceAmt(anx.getActualNoOfPackages());
				anx.setCommodityDescription(data.getCommodityDescription());
				anx.setDiscPercentage(zero);
				anx.setDiscValue(zero);
				anx.setmPercentage(zero);
				anx.setmAmount(zero);

//			anx.setAcCode(c.getAcCode());

				anx.setProcessTransDate(data.getAssesmentDate());
				anx.setProcessId("P01106");
				anx.setPartyId(data.getPartyId());

//			anx.setGateOutDate(c.getGateoutDate());
//			anx.setStartDate(c.getGateInDate());
//			anx.setInvoiceUptoDate(c.getInvoiceDate());
//			anx.setInvoiceUptoWeek(c.getInvoiceDate());
				anx.setStatus("A");
				anx.setCreatedBy(user);
				anx.setCreatedDate(new Date());

				anx.setTaxApp(String.valueOf(data.getTaxApplicable()));
				anx.setSrvManualFlag("N");
				anx.setCriteria("CNTR");
				anx.setRangeFrom(zero);
				anx.setRangeTo(zero);
//			anx.setGateOutId(c.getGateOutId());
//			anx.setGatePassNo(c.getGatePassNo());
				anx.setRangeType("CNTR");
//			anx.setTaxId(c.getTaxId());
				anx.setExRate(zero);

				if (("Y".equals(data.getIgst())) || ("Y".equals(data.getCgst()) && "Y".equals(data.getSgst()))) {
					anx.setTaxPerc(anx.getTaxPerc());
				} else {
					anx.setTaxPerc(zero);
				}

			}

			cfinvsrvanxprorepo.saveAll(cfInvsrvanxData);

			return ResponseEntity.ok(true);
		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error in Add Service ContainerWise: " + e.getMessage());
		}
	}
	@Transactional
	public ResponseEntity<?> saveBondNOCAssessmentData(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {

		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheetPro assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheetPro.class);

		if (assessment == null) {
			return new ResponseEntity<>("NOC data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("NOC data not found", HttpStatus.CONFLICT);
		}

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {

			int sr = 1;

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05109", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("BINA%06d", nextNumericNextID1);

			for (AssessmentContainerDTO con : containerData) {

				List<AssessmentContainerDTO> finalConData = new ArrayList<>();

				if (con.getLastInvoiceUptoDate() == null) {

					List<String> serviceMappingData = new ArrayList<>();

					if ("SINGLE".equals(assessment.getInvoiceCategory())) {
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice1(cid, bid,
								con.getProfitcentreId(), "INBOND");
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice2(cid, bid,
								con.getProfitcentreId(), "INBOND", group);
					}

					if (serviceMappingData.isEmpty()) {
						return new ResponseEntity<>("Service mapping not found", HttpStatus.CONFLICT);
					}

					Set<String> combinedService = new HashSet<>();
					combinedService.addAll(serviceMappingData);

					List<String> partyIdList = new ArrayList<>();
					partyIdList.add(assessment.getOthPartyId());
					partyIdList.add("");

					List<String> impIdList = new ArrayList<>();
					impIdList.add(assessment.getImporterId());
					impIdList.add("");

					List<String> chaIdList = new ArrayList<>();
					chaIdList.add(assessment.getCha());
					chaIdList.add("");

					List<String> fwIdList = new ArrayList<>();
					fwIdList.add(assessment.getOnAccountOf());
					fwIdList.add("");

					String tariffNo = cfstarrifrepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList, fwIdList,
							assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
							assessment.getOnAccountOf());

					con.setUpTariffNo(tariffNo);

					if (con.getUpTariffNo() == null || con.getUpTariffNo().isEmpty()) {
						return new ResponseEntity<>("Tariff no not found.", HttpStatus.CONFLICT);
					}

					// Find default service
					List<String> defaultService = cfstarrifrepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

					if (!defaultService.isEmpty()) {
						combinedService.addAll(defaultService);
					}

					List<SSRDtl> ssrServices = new ArrayList<>();

					if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
						ssrServices = ssrRepo.getServiceId1(cid, bid, con.getSsrTransId());

						if (!ssrServices.isEmpty()) {
							List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId) // Extract
																										// serviceId
									.collect(Collectors.toList()); // Collect into a list

							combinedService.addAll(ssrServiceId);
						}

					}

					List<SSRDtl> finalSsrServices = ssrServices;

					List<String> finalServices = new ArrayList<>(combinedService);

					List<Object[]> finalPricing = new ArrayList<>();
					List<String> notInPricing = new ArrayList<>();

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String formattedDate = formatter.format(new Date());
					Date parsedDate = formatter.parse(formattedDate);

					List<Object[]> pricingData = cfstariffservicerepo.getServiceRateForBondNoc(cid, bid, parsedDate,
							finalServices, con.getUpTariffNo());

					if (!pricingData.isEmpty()) {
						List<Object[]> remainingPricing = pricingData.stream()
								.filter(data1 -> finalServices.contains(data1[0].toString()))
								.collect(Collectors.toList());

						notInPricing = finalServices.stream().filter(
								service -> pricingData.stream().noneMatch(data1 -> service.equals(data1[0].toString())))
								.collect(Collectors.toList());

						finalPricing.addAll(remainingPricing);
					} else {
						notInPricing.addAll(finalServices);
					}

					if (!notInPricing.isEmpty()) {
						List<String> tempPricing = notInPricing;

						List<Object[]> pricingData1 = cfstariffservicerepo.getServiceRateForBondNoc(cid, bid,
								parsedDate, notInPricing, "CFS1000001");

						if (!pricingData1.isEmpty()) {
							List<Object[]> remainingPricing = pricingData1.stream()
									.filter(data1 -> tempPricing.contains(data1[0].toString()))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						}
					}

					finalPricing.stream().forEach(f -> {
						AssessmentContainerDTO tempAss = new AssessmentContainerDTO();
						CFSDay day = cfsdayrepo.getData(cid, bid);

						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

							if (!finalSsrServices.isEmpty()) {

								SSRDtl rate1 = finalSsrServices.stream()
										.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
										.orElse(null); // Return null if no match is found

								if (rate1 != null) {

									try {
										tempAss = (AssessmentContainerDTO) con.clone();

										tempAss = (AssessmentContainerDTO) con.clone();

										LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
												.atZone(ZoneId.systemDefault()).toLocalDateTime();
										LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59)
												.withSecond(59).withNano(0);
										Date updatedInvDate = Date
												.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

										Date invDate = updatedInvDate;

										tempAss.setInvoiceDate(invDate);
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setRates(rate1.getTotalRate());
										tempAss.setServiceRate(rate1.getRate());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										finalConData.add(tempAss);
									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									try {
										tempAss = (AssessmentContainerDTO) con.clone();

										LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
												.atZone(ZoneId.systemDefault()).toLocalDateTime();
										LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59)
												.withSecond(59).withNano(0);
										Date updatedInvDate = Date
												.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

										Date invDate = updatedInvDate;

										tempAss.setInvoiceDate(invDate);
										if ("SA".equals(String.valueOf(f[8]))) {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											List<String> commodityType = new ArrayList<>();
											commodityType.add("ALL");
											commodityType.add(assessment.getCommodityCode());

											List<Object[]> rangeValues = cfstariffservicerepo
													.getDataByServiceIdForBondNOC(cid, bid, String.valueOf(f[3]),
															String.valueOf(f[4]), String.valueOf(f[0]),
															String.valueOf(f[8]), commodityType,
															assessment.getCommodityCode());

											if (!rangeValues.isEmpty()) {

												BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
														.filter(r -> ((BigDecimal) r[8])
																.compareTo(BigDecimal.ZERO) == 0) // Filter where
																									// rate is 0
														.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																						// BigDecimal
														.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
														.orElse(BigDecimal.ZERO); // Default value if no match found

												tempAss.setFreeDays(freeDaysForExportContainerStorage);

												String unit = String.valueOf(f[1]);

												AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
														BigDecimal.ZERO);
												if ("DAY".equals(unit)) {

//														LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//																convertToLocalDateTime(con.getGateInDate()),
//																day.getStartTime());
//														LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//																convertToLocalDateTime(con.getDestuffDate()),
//																day.getEndTime());

													LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
															con.getGateInDate().toInstant(), ZoneId.systemDefault());
													LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
															con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;

													tempAss.setExecutionUnit(String.valueOf(daysBetween1));
													tempAss.setExecutionUnit1("");

													System.out.println("daysBetween " + daysBetween1);

													BigDecimal totalRate = rangeValues.stream().map(r -> {

														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();

														BigDecimal rate = (BigDecimal) r[8];

														if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
															serviceRate.set(rate); // Set the rate for the
																					// matching
																					// slab
														}

														long daysInSlab = Math.min(toRange, daysBetween1)
																- Math.max(fromRange, 1) + 1;

														daysInSlab = Math.max(daysInSlab, 0);

														serviceRate.set(rate);
														return rate.multiply(new BigDecimal(daysInSlab));
													}).reduce(BigDecimal.ZERO, BigDecimal::add);
													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													tempAss.setServiceRate(serviceRate.get());

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												}

												if ("WEEK".equals(unit)) {

													LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
															con.getGateInDate().toInstant(), ZoneId.systemDefault());
													LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
															con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

													tempAss.setExecutionUnit(String.valueOf(weeksBetween));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().filter(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														// Include slabs that overlap with weeksBetween
														return weeksBetween >= fromRange;
													}).map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														BigDecimal rate = (BigDecimal) r[8];

														// Adjust the fromRange to exclude week 0
														int adjustedFromRange = Math.max(fromRange, 1);

														// Determine actual weeks in this slab that contribute
														// to the total
														long weeksInSlab = Math.max(0, Math.min(toRange, weeksBetween)
																- adjustedFromRange + 1);

														// Exclude slabs where weeksInSlab is 0
														return weeksInSlab > 0
																? rate.multiply(new BigDecimal(weeksInSlab))
																: BigDecimal.ZERO;
													}).reduce(BigDecimal.ZERO, BigDecimal::add);

													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													if ("SM".equals(String.valueOf(f[7]))) {
														totalRate = (totalRate
																.multiply(con.getArea() == null ? new BigDecimal(1)
																		: con.getArea()))
																.setScale(3, RoundingMode.HALF_UP);
													}
													System.out.println("totalRate " + totalRate + " " + weeksBetween);
													tempAss.setServiceRate(serviceRate.get());
													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												}

											}

										} else if ("RA".equals(String.valueOf(f[8]))) {

											String unit = String.valueOf(f[1]);
											BigDecimal executionUnit = BigDecimal.ZERO;

											List<String> commodityType = new ArrayList<>();
											commodityType.add("ALL");
											commodityType.add(assessment.getCommodityCode());

											if ("MTON".equals(unit)) {
												String serviceGrp = String.valueOf(f[18]);

												if ("H".equals(serviceGrp)) {
													BigDecimal cargoWt = new BigDecimal(
															String.valueOf(con.getGrossWt()))
															.divide(new BigDecimal(1000))
															.setScale(3, RoundingMode.HALF_UP);

													executionUnit = cargoWt;

													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else {
													BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000))
															.setScale(3, RoundingMode.HALF_UP);

													executionUnit = cargoWt;
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}

												Object rangeValue = cfstariffservicerepo
														.getRangeDataByForbondNocServiceId(cid, bid,
																String.valueOf(f[3]), String.valueOf(f[4]),
																String.valueOf(f[0]), executionUnit, commodityType,
																assessment.getCommodityCode());

												if (rangeValue != null) {
													Object[] finalRangeValue = (Object[]) rangeValue;
													tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
													tempAss.setDiscPercentage(
															(f[13] != null && !String.valueOf(f[13]).isEmpty())
																	? new BigDecimal(String.valueOf(f[13]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[14] != null && !String.valueOf(f[14]).isEmpty())
																	? new BigDecimal(String.valueOf(f[14]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(
															(f[15] != null && !String.valueOf(f[15]).isEmpty())
																	? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setmAmount(
															(f[16] != null && !String.valueOf(f[16]).isEmpty())
																	? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
													tempAss.setContainerStatus(con.getContainerStatus());
													tempAss.setGateOutId(con.getGateOutId());
													tempAss.setGatePassNo(con.getGatePassNo());
													tempAss.setTaxPerc(
															(f[12] == null || String.valueOf(f[12]).isEmpty())
																	? BigDecimal.ZERO
																	: new BigDecimal(String.valueOf(f[12])));
													tempAss.setTaxId(String.valueOf(f[11]));
													BigDecimal totalRate = new BigDecimal(
															String.valueOf(finalRangeValue[8]));

													tempAss.setServiceRate(totalRate);
													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(
																new BigDecimal(String.valueOf(finalRangeValue[8])));
													}

												}

											} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
													|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
													|| "SHFT".equals(unit) || "KG".equals(unit)) {

												if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
														|| "ACT".equals(unit) || "PCK".equals(unit)
														|| "SHFT".equals(unit)) {
													executionUnit = new BigDecimal("1");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}

												if ("TEU".equals(unit)) {
													if ("20".equals(con.getContainerSize())
															|| "22".equals(con.getContainerSize())) {
														executionUnit = new BigDecimal("1");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else if ("40".equals(con.getContainerSize())) {
														executionUnit = new BigDecimal("2");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else if ("45".equals(con.getContainerSize())) {
														executionUnit = new BigDecimal("2.5");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}
												}

												if ("KG".equals(unit)) {
													String serviceGrp = String.valueOf(f[18]);
													if ("H".equals(serviceGrp)) {

														BigDecimal cargoWt = new BigDecimal(
																String.valueOf(con.getGrossWt()));

														executionUnit = cargoWt;

														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else {
														BigDecimal cargoWt = con.getCargoWt();

														executionUnit = cargoWt;
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}
												}
												Object rangeValue = cfstariffservicerepo
														.getRangeDataByForbondNocServiceId(cid, bid,
																String.valueOf(f[3]), String.valueOf(f[4]),
																String.valueOf(f[0]), executionUnit, commodityType,
																assessment.getCommodityCode());

												if (rangeValue != null) {
													Object[] finalRangeValue = (Object[]) rangeValue;
													tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
													tempAss.setDiscPercentage(
															(f[13] != null && !String.valueOf(f[13]).isEmpty())
																	? new BigDecimal(String.valueOf(f[13]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[14] != null && !String.valueOf(f[14]).isEmpty())
																	? new BigDecimal(String.valueOf(f[14]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(
															(f[15] != null && !String.valueOf(f[15]).isEmpty())
																	? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setmAmount(
															(f[16] != null && !String.valueOf(f[16]).isEmpty())
																	? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
													tempAss.setContainerStatus(con.getContainerStatus());
													tempAss.setGateOutId(con.getGateOutId());
													tempAss.setGatePassNo(con.getGatePassNo());
													tempAss.setTaxPerc(
															(f[12] == null || String.valueOf(f[12]).isEmpty())
																	? BigDecimal.ZERO
																	: new BigDecimal(String.valueOf(f[12])));
													tempAss.setTaxId(String.valueOf(f[11]));
													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
													BigDecimal totalRate = new BigDecimal(
															String.valueOf(finalRangeValue[8]));

													tempAss.setServiceRate(totalRate);

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(
																new BigDecimal(String.valueOf(finalRangeValue[8])));
													}
												}

											}

											else {
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														(f[13] != null && !String.valueOf(f[13]).isEmpty())
																? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
														? new BigDecimal(String.valueOf(f[14]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(
														(f[15] != null && !String.valueOf(f[15]).isEmpty())
																? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
														? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
												BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
												tempAss.setServiceRate(totalRate);

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
												}

											}

										} else {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setExecutionUnit(String.valueOf(1));
											tempAss.setExecutionUnit1("");
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
											BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

											tempAss.setServiceRate(totalRate);

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
											}
										}

										finalConData.add(tempAss);

									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

						} else {

							try {
								tempAss = (AssessmentContainerDTO) con.clone();

								LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDateTime();
								LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59).withSecond(59)
										.withNano(0);
								Date updatedInvDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

								Date invDate = updatedInvDate;

								tempAss.setInvoiceDate(invDate);
								if ("SA".equals(String.valueOf(f[8]))) {
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
											? new BigDecimal(String.valueOf(f[13]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
											? new BigDecimal(String.valueOf(f[14]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
											? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
											? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(
											f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
									tempAss.setRangeTo(
											f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
									tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setTaxId(String.valueOf(f[11]));
									tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

									List<String> commodityType = new ArrayList<>();
									commodityType.add("ALL");
									commodityType.add(assessment.getCommodityCode());

									List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceIdForBondNOC(cid,
											bid, String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
											String.valueOf(f[8]), commodityType, assessment.getCommodityCode());

									if (!rangeValues.isEmpty()) {

										BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
												.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																													// where
																													// rate
																													// is
																													// 0
												.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																				// BigDecimal
												.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
												.orElse(BigDecimal.ZERO); // Default value if no match found

										tempAss.setFreeDays(freeDaysForExportContainerStorage);

										String unit = String.valueOf(f[1]);

										AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
												BigDecimal.ZERO);
										if ("DAY".equals(unit)) {

//												LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//														convertToLocalDateTime(con.getGateInDate()),
//														day.getStartTime());
//												LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//														convertToLocalDateTime(con.getDestuffDate()),
//														day.getEndTime());

											LocalDateTime gateInDateTime = LocalDateTime
													.ofInstant(con.getGateInDate().toInstant(), ZoneId.systemDefault());
											LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
													con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime);

											if (daysBetween == 0
													&& ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime) > 0) {
												daysBetween = 1;
											}

											final long daysBetween1 = daysBetween;

											tempAss.setExecutionUnit(String.valueOf(daysBetween1));
											tempAss.setExecutionUnit1("");

											System.out.println("daysBetween " + daysBetween1);

											BigDecimal totalRate = rangeValues.stream().map(r -> {

												int fromRange = ((BigDecimal) r[6]).intValue();
												int toRange = ((BigDecimal) r[7]).intValue();

												BigDecimal rate = (BigDecimal) r[8];

												if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
													serviceRate.set(rate); // Set the rate for the
																			// matching
																			// slab
												}

												long daysInSlab = Math.min(toRange, daysBetween1)
														- Math.max(fromRange, 1) + 1;

												daysInSlab = Math.max(daysInSlab, 0);

												serviceRate.set(rate);
												return rate.multiply(new BigDecimal(daysInSlab));
											}).reduce(BigDecimal.ZERO, BigDecimal::add);
											totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
											tempAss.setServiceRate(serviceRate.get());

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(totalRate);
											}

										}

										if ("WEEK".equals(unit)) {

											LocalDateTime gateInDateTime = LocalDateTime
													.ofInstant(con.getGateInDate().toInstant(), ZoneId.systemDefault());
											LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
													con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime);

											if (daysBetween == 0
													&& ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime) > 0) {
												daysBetween = 1;
											}

											final long daysBetween1 = daysBetween;
											long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

											tempAss.setExecutionUnit(String.valueOf(weeksBetween));
											tempAss.setExecutionUnit1("");

											BigDecimal totalRate = rangeValues.stream().filter(r -> {
												int fromRange = ((BigDecimal) r[6]).intValue();
												int toRange = ((BigDecimal) r[7]).intValue();
												// Include slabs that overlap with weeksBetween
												return weeksBetween >= fromRange;
											}).map(r -> {
												int fromRange = ((BigDecimal) r[6]).intValue();
												int toRange = ((BigDecimal) r[7]).intValue();
												BigDecimal rate = (BigDecimal) r[8];

												// Adjust the fromRange to exclude week 0
												int adjustedFromRange = Math.max(fromRange, 1);

												// Determine actual weeks in this slab that contribute
												// to the total
												long weeksInSlab = Math.max(0,
														Math.min(toRange, weeksBetween) - adjustedFromRange + 1);

												// Exclude slabs where weeksInSlab is 0
												return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
														: BigDecimal.ZERO;
											}).reduce(BigDecimal.ZERO, BigDecimal::add);

											totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
											if ("SM".equals(String.valueOf(f[7]))) {
												totalRate = (totalRate.multiply(
														con.getArea() == null ? new BigDecimal(1) : con.getArea()))
														.setScale(3, RoundingMode.HALF_UP);
											}
											System.out.println("totalRate " + totalRate + " " + weeksBetween);
											tempAss.setServiceRate(serviceRate.get());
											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(totalRate);
											}

										}

									}

								} else if ("RA".equals(String.valueOf(f[8]))) {

									String unit = String.valueOf(f[1]);
									BigDecimal executionUnit = BigDecimal.ZERO;

									List<String> commodityType = new ArrayList<>();
									commodityType.add("ALL");
									commodityType.add(assessment.getCommodityCode());

									if ("MTON".equals(unit)) {
										String serviceGrp = String.valueOf(f[18]);

										if ("H".equals(serviceGrp)) {
											BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()))
													.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;

											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else {
											BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000))
													.setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}

										Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(cid,
												bid, String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												executionUnit, commodityType, assessment.getCommodityCode());

										if (rangeValue != null) {
											Object[] finalRangeValue = (Object[]) rangeValue;
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											BigDecimal totalRate = new BigDecimal(String.valueOf(finalRangeValue[8]));

											tempAss.setServiceRate(totalRate);
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(finalRangeValue[8])));
											}

										}

									} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
											|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
											|| "SHFT".equals(unit) || "KG".equals(unit)) {

										if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
												|| "ACT".equals(unit) || "PCK".equals(unit) || "SHFT".equals(unit)) {
											executionUnit = new BigDecimal("1");
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}

										if ("TEU".equals(unit)) {
											if ("20".equals(con.getContainerSize())
													|| "22".equals(con.getContainerSize())) {
												executionUnit = new BigDecimal("1");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else if ("40".equals(con.getContainerSize())) {
												executionUnit = new BigDecimal("2");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else if ("45".equals(con.getContainerSize())) {
												executionUnit = new BigDecimal("2.5");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}
										}

										if ("KG".equals(unit)) {
											String serviceGrp = String.valueOf(f[18]);
											if ("H".equals(serviceGrp)) {

												BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()));

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getCargoWt();

												executionUnit = cargoWt;
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}
										}

										Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(cid,
												bid, String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												executionUnit, commodityType, assessment.getCommodityCode());

										if (rangeValue != null) {
											Object[] finalRangeValue = (Object[]) rangeValue;
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
											BigDecimal totalRate = new BigDecimal(String.valueOf(finalRangeValue[8]));

											tempAss.setServiceRate(totalRate);

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(finalRangeValue[8])));
											}
										}

									}

									else {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
										tempAss.setServiceRate(totalRate);

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
										}

									}

								} else {
									System.out.println("f[13] " + String.valueOf(f[2]) + " " + String.valueOf(f[0]));
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
											? new BigDecimal(String.valueOf(f[13]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
											? new BigDecimal(String.valueOf(f[14]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
											? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
											? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(
											f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
									tempAss.setRangeTo(
											f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
									tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setExecutionUnit(String.valueOf(1));
									tempAss.setExecutionUnit1("");
									tempAss.setTaxId(String.valueOf(f[11]));
									tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
									BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

									tempAss.setServiceRate(totalRate);

									System.out.println("String.valueOf(f[2]) " + String.valueOf(f[2]));

									String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
									String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
									String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
									String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else if (mPerc != null && !mPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(mPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);
									} else if (mAmt != null && !mAmt.isEmpty()) {
										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);
									} else {
										tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
									}
								}

								finalConData.add(tempAss);

							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});

					AssessmentSheetPro tempAssessment = (AssessmentSheetPro) assessment.clone();

					Cfbondnoc noc = cfBondNocRepo.getDataByNocAndTrans(cid, bid, assessment.getIgmTransId(),
							assessment.getIgmNo());

					if (noc == null) {
						return new ResponseEntity<>("Noc data not found", HttpStatus.CONFLICT);
					}

					AssessmentSheetPro newAss = tempAssessment;

					newAss.setCompanyId(cid);
					newAss.setBranchId(bid);
					newAss.setAssesmentLineNo(String.valueOf(sr));
					newAss.setStatus('A');
					newAss.setCreatedBy(user);
					newAss.setCreatedDate(new Date());
					newAss.setApprovedBy(user);
					newAss.setApprovedDate(new Date());
					newAss.setAssesmentDate(new Date());
					newAss.setTransType("Bond Cargo");
					newAss.setProfitcentreId(con.getProfitcentreId());
					newAss.setSbNo(noc.getBoeNo());
					newAss.setSbDate(noc.getBoeDate());
					newAss.setAssesmentId(HoldNextIdD1);

					Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

					String billingId = "IMP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
							: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
									: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
											: assessment.getOthPartyId();

					String billingAdd = "IMP".equals(assessment.getBillingParty())
							? String.valueOf(assessment.getImpSrNo())
							: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
									: "FWR".equals(assessment.getBillingParty())
											? String.valueOf(assessment.getAccSrNo())
											: assessment.getOthSrNo();

					PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

					newAss.setPartyId(billingId);
					newAss.setPartySrNo(new BigDecimal(billingAdd));
					newAss.setPayableParty(assessment.getBillingParty());
					newAss.setPayablePartyId(billingId);

					if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
						newAss.setIgst("Y");
						newAss.setCgst("N");
						newAss.setSgst("N");
					} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
						newAss.setIgst("E");
						newAss.setCgst("N");
						newAss.setSgst("N");
					}

					else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {

						if (b.getState().equals(p.getState())) {
							newAss.setIgst("N");
							newAss.setCgst("Y");
							newAss.setSgst("Y");
						} else {
							newAss.setIgst("Y");
							newAss.setCgst("N");
							newAss.setSgst("N");
						}

					} else {
						newAss.setIgst("N");
						newAss.setCgst("N");
						newAss.setSgst("N");
					}

					newAss.setNoOfPackages(new BigDecimal(con.getExamPercentage()));

					List<AssessmentContainerDTO> newAnxData = finalConData.stream().map(item -> {
						try {
							return (AssessmentContainerDTO) item.clone(); // Clone first
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							return null;
						}
					}).filter(Objects::nonNull) // Remove null values from failed clones
							.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
																								// cloning
							.collect(Collectors.toList());

					List<AssessmentContainerDTO> filteredData1 = newAnxData.stream()
							.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());

					String crgDays = "";

					if (!filteredData1.isEmpty()) {
						crgDays = filteredData1.get(0).getExecutionUnit();
					}

					BigDecimal totalRates1 = filteredData1.stream().map(AssessmentContainerDTO::getRates)
							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

					newAss.setContainerNo("");
					newAss.setContainerSize("");
					newAss.setContainerType("");
					newAss.setGateInDate(assessment.getIgmDate());
					newAss.setGateOutDate(null);
					newAss.setGrossWeight(con.getGrossWt());
					newAss.setGateOutType("");
					newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
					newAss.setTypeOfContainer("");
					newAss.setScanerType("");
					newAss.setAreaUsed(con.getCargoWt());
					newAss.setExaminedPercentage(BigDecimal.ZERO);
					newAss.setWeighmentFlag("N");
					newAss.setCargoWeight(con.getGrossWt());
					newAss.setDestuffDate(null);
					newAss.setCalculateInvoice('N');
					newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
					newAss.setContainerHandlingAmt(BigDecimal.ZERO);
					newAss.setContainerStorageAmt(totalRates1);
					newAss.setInvoiceCategory(assessment.getInvoiceCategory());
					newAss.setInvType("NOCBondInv");
					newAss.setSa("");
					newAss.setSl("");
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrTransId(con.getSsrTransId());
//					newAss.setLastAssesmentDate(assessment.getLastAssesmentDate());
//					newAss.setLastAssesmentId(assessment.getLastAssesmentId());
//					newAss.setLastInvoiceDate(assessment.getLastInvoiceDate());
//					newAss.setLastInvoiceNo(assessment.getLastInvoiceNo());
//					newAss.setLastInvoiceUptoDate(con.getLastInvoiceUptoDate());
					newAss.setInsuranceValue(new BigDecimal(con.getContainerNo()));
					newAss.setNoOf20ft(con.getContainerSize());
					newAss.setNoOf40ft(con.getContainerType());
					newAss.setNocWeeks(new BigDecimal(con.getGateOutType()));
					newAss.setNocFromDate(con.getGateInDate());
					newAss.setNocValidityDate(con.getGateoutDate());
					assessmentsheetprorepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P05109", HoldNextIdD1, "2024");

//					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

					finalConData.stream().forEach(c -> {
						if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
							CfinvsrvanxPro anx = new CfinvsrvanxPro();
							anx.setCompanyId(cid);
							anx.setBranchId(bid);
							anx.setErpDocRefNo(newAss.getIgmTransId());
							anx.setLineNo(String.valueOf(anxSr.get()));
							anx.setProcessTransId(HoldNextIdD1);
							anx.setServiceId(c.getServiceId());
							anx.setSrlNo(srNo1.get());
							anx.setDocRefNo(newAss.getIgmNo());
							anx.setIgmLineNo("");
							anx.setProfitcentreId(newAss.getProfitcentreId());
							anx.setInvoiceType("BON");
							anx.setInvoiceSubType("NOC");
							anx.setServiceUnit(c.getServiceUnit());
							anx.setExecutionUnit(c.getExecutionUnit());
							anx.setServiceUnit1(c.getServiceUnit1());
							anx.setExecutionUnit1(c.getExecutionUnit1());
							anx.setRate(c.getServiceRate());
							anx.setActualNoOfPackages(c.getRates());
							anx.setCurrencyId("INR");
							anx.setLocalAmt(c.getRates());
							anx.setInvoiceAmt(c.getRates());
							anx.setCommodityDescription(newAss.getCommodityDescription());
							anx.setDiscPercentage(c.getDiscPercentage());
							anx.setDiscValue(c.getDiscValue());
							anx.setmPercentage(c.getmPercentage());
							anx.setmAmount(c.getmAmount());
							anx.setAcCode(c.getAcCode());
							anx.setProcessTransDate(newAss.getAssesmentDate());
							anx.setProcessId("P01102");
							anx.setPartyId(newAss.getPartyId());
							anx.setWoNo(c.getWoNo());
							anx.setWoAmndNo(c.getWoAmndNo());
							anx.setContainerNo("");
							anx.setContainerStatus("");
							anx.setGateOutDate(con.getGateoutDate());
							anx.setAddServices("N");
							anx.setStartDate(con.getGateInDate());
							anx.setInvoiceUptoDate(c.getInvoiceDate());
							anx.setInvoiceUptoWeek(c.getInvoiceDate());
							anx.setStatus("A");
							anx.setCreatedBy(user);
							anx.setCreatedDate(new Date());
//	  						anx.setApprovedBy(user);
//	  						anx.setApprovedDate(new Date());
							anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
							anx.setSrvManualFlag("N");
							anx.setCriteria(
									"SA".equals(c.getCriteria()) ? "DW" : "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
							anx.setRangeFrom(c.getRangeFrom());
							anx.setRangeTo(c.getRangeTo());
							anx.setRangeType(c.getContainerStatus());
							anx.setGateOutId(c.getGateOutId());
							anx.setGatePassNo(c.getGatePassNo());
							anx.setRangeType(c.getCriteria());
							anx.setTaxId(c.getTaxId());
							anx.setExRate(c.getExRate());
							anx.setFreeDays(c.getFreeDays());
							if (("Y".equals(newAss.getIgst()))
									|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
								anx.setTaxPerc(c.getTaxPerc());
							} else {
								anx.setTaxPerc(BigDecimal.ZERO);
							}

							totalAmount.set(totalAmount.get().add(c.getRates()));

							cfinvsrvanxprorepo.save(anx);

							srNo1.set(srNo1.get().add(new BigDecimal(1)));
						}
					});
					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

					assessment.setAssesmentId(HoldNextIdD1);

					sr++;

				} else {

					//

					List<String> serviceMappingData = new ArrayList<>();

					if ("SINGLE".equals(assessment.getInvoiceCategory())) {
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice1(cid, bid,
								con.getProfitcentreId(), "INBONDPER");
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice2(cid, bid,
								con.getProfitcentreId(), "INBONDPER", group);
					}

					if (serviceMappingData.isEmpty()) {
						return new ResponseEntity<>("Service mapping not found", HttpStatus.CONFLICT);
					}

					Set<String> combinedService = new HashSet<>();
					combinedService.addAll(serviceMappingData);

					List<String> partyIdList = new ArrayList<>();
					partyIdList.add(assessment.getOthPartyId());
					partyIdList.add("");

					List<String> impIdList = new ArrayList<>();
					impIdList.add(assessment.getImporterId());
					impIdList.add("");

					List<String> chaIdList = new ArrayList<>();
					chaIdList.add(assessment.getCha());
					chaIdList.add("");

					List<String> fwIdList = new ArrayList<>();
					fwIdList.add(assessment.getOnAccountOf());
					fwIdList.add("");

					String tariffNo = cfstarrifrepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList, fwIdList,
							assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
							assessment.getOnAccountOf());

					con.setUpTariffNo(tariffNo);

					if (con.getUpTariffNo() == null || con.getUpTariffNo().isEmpty()) {
						return new ResponseEntity<>("Tariff no not found.", HttpStatus.CONFLICT);
					}

// Find default service
					List<String> defaultService = cfstarrifrepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

					if (!defaultService.isEmpty()) {
						combinedService.addAll(defaultService);
					}

					List<SSRDtl> ssrServices = new ArrayList<>();

					if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
						ssrServices = ssrRepo.getServiceId1(cid, bid, con.getSsrTransId());

						if (!ssrServices.isEmpty()) {
							List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId) // Extract
																										// serviceId
									.collect(Collectors.toList()); // Collect into a list

							combinedService.addAll(ssrServiceId);
						}

					}

					List<SSRDtl> finalSsrServices = ssrServices;

					List<String> finalServices = new ArrayList<>(combinedService);

					List<Object[]> finalPricing = new ArrayList<>();
					List<String> notInPricing = new ArrayList<>();

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String formattedDate = formatter.format(new Date());
					Date parsedDate = formatter.parse(formattedDate);

					List<Object[]> pricingData = cfstariffservicerepo.getServiceRateForBondNoc(cid, bid, parsedDate,
							finalServices, con.getUpTariffNo());

					if (!pricingData.isEmpty()) {
						List<Object[]> remainingPricing = pricingData.stream()
								.filter(data1 -> finalServices.contains(data1[0].toString()))
								.collect(Collectors.toList());

						notInPricing = finalServices.stream().filter(
								service -> pricingData.stream().noneMatch(data1 -> service.equals(data1[0].toString())))
								.collect(Collectors.toList());

						finalPricing.addAll(remainingPricing);
					} else {
						notInPricing.addAll(finalServices);
					}

					if (!notInPricing.isEmpty()) {
						List<String> tempPricing = notInPricing;

						List<Object[]> pricingData1 = cfstariffservicerepo.getServiceRateForBondNoc(cid, bid,
								parsedDate, notInPricing, "CFS1000001");

						if (!pricingData1.isEmpty()) {
							List<Object[]> remainingPricing = pricingData1.stream()
									.filter(data1 -> tempPricing.contains(data1[0].toString()))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						}
					}

					finalPricing.stream().forEach(f -> {
						AssessmentContainerDTO tempAss = new AssessmentContainerDTO();
						CFSDay day = cfsdayrepo.getData(cid, bid);

						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

							if (!finalSsrServices.isEmpty()) {

								SSRDtl rate1 = finalSsrServices.stream()
										.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
										.orElse(null); // Return null if no match is found

								if (rate1 != null) {

									try {
										tempAss = (AssessmentContainerDTO) con.clone();

										tempAss = (AssessmentContainerDTO) con.clone();

										LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
												.atZone(ZoneId.systemDefault()).toLocalDateTime();
										LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59)
												.withSecond(59).withNano(0);
										Date updatedInvDate = Date
												.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

										Date invDate = updatedInvDate;

										tempAss.setInvoiceDate(invDate);
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setRates(rate1.getTotalRate());
										tempAss.setServiceRate(rate1.getRate());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										finalConData.add(tempAss);
									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								} else {
									try {
										tempAss = (AssessmentContainerDTO) con.clone();

										LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
												.atZone(ZoneId.systemDefault()).toLocalDateTime();
										LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59)
												.withSecond(59).withNano(0);
										Date updatedInvDate = Date
												.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

										Date invDate = updatedInvDate;

										tempAss.setInvoiceDate(invDate);
										if ("SA".equals(String.valueOf(f[8]))) {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											List<String> commodityType = new ArrayList<>();
											commodityType.add("ALL");
											commodityType.add(assessment.getCommodityCode());

											List<Object[]> rangeValues = cfstariffservicerepo
													.getDataByServiceIdForBondNOC(cid, bid, String.valueOf(f[3]),
															String.valueOf(f[4]), String.valueOf(f[0]),
															String.valueOf(f[8]), commodityType,
															assessment.getCommodityCode());

											if (!rangeValues.isEmpty()) {

												BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
														.filter(r -> ((BigDecimal) r[8])
																.compareTo(BigDecimal.ZERO) == 0) // Filter where
																									// rate is 0
														.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																						// BigDecimal
														.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
														.orElse(BigDecimal.ZERO); // Default value if no match found

												tempAss.setFreeDays(freeDaysForExportContainerStorage);

												String unit = String.valueOf(f[1]);

												AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
														BigDecimal.ZERO);
												if ("DAY".equals(unit)) {

//									LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//											convertToLocalDateTime(con.getGateInDate()),
//											day.getStartTime());
//									LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//											convertToLocalDateTime(con.getDestuffDate()),
//											day.getEndTime());

													LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
															con.getLastInvoiceUptoDate().toInstant(),
															ZoneId.systemDefault());
													LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
															con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;

													tempAss.setExecutionUnit(String.valueOf(daysBetween1));
													tempAss.setExecutionUnit1("");

													System.out.println("daysBetween " + daysBetween1);

													BigDecimal totalRate = rangeValues.stream().map(r -> {

														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();

														BigDecimal rate = (BigDecimal) r[8];

														if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
															serviceRate.set(rate); // Set the rate for the
																					// matching
																					// slab
														}

														long daysInSlab = Math.min(toRange, daysBetween1)
																- Math.max(fromRange, 1) + 1;

														daysInSlab = Math.max(daysInSlab, 0);

														serviceRate.set(rate);
														return rate.multiply(new BigDecimal(daysInSlab));
													}).reduce(BigDecimal.ZERO, BigDecimal::add);
													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													tempAss.setServiceRate(serviceRate.get());

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												}

												if ("WEEK".equals(unit)) {

													LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
															con.getLastInvoiceUptoDate().toInstant(),
															ZoneId.systemDefault());
													LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
															con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
													long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
															destuffDateTime);

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

													tempAss.setExecutionUnit(String.valueOf(weeksBetween));
													tempAss.setExecutionUnit1("");

													BigDecimal totalRate = rangeValues.stream().filter(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														// Include slabs that overlap with weeksBetween
														return weeksBetween >= fromRange;
													}).map(r -> {
														int fromRange = ((BigDecimal) r[6]).intValue();
														int toRange = ((BigDecimal) r[7]).intValue();
														BigDecimal rate = (BigDecimal) r[8];

														// Adjust the fromRange to exclude week 0
														int adjustedFromRange = Math.max(fromRange, 1);

														// Determine actual weeks in this slab that contribute
														// to the total
														long weeksInSlab = Math.max(0, Math.min(toRange, weeksBetween)
																- adjustedFromRange + 1);

														// Exclude slabs where weeksInSlab is 0
														return weeksInSlab > 0
																? rate.multiply(new BigDecimal(weeksInSlab))
																: BigDecimal.ZERO;
													}).reduce(BigDecimal.ZERO, BigDecimal::add);

													totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
													if ("SM".equals(String.valueOf(f[7]))) {
														totalRate = (totalRate
																.multiply(con.getArea() == null ? new BigDecimal(1)
																		: con.getArea()))
																.setScale(3, RoundingMode.HALF_UP);
													}
													System.out.println("totalRate " + totalRate + " " + weeksBetween);
													tempAss.setServiceRate(serviceRate.get());
													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(totalRate);
													}

												}

											}

										} else if ("RA".equals(String.valueOf(f[8]))) {

											String unit = String.valueOf(f[1]);
											BigDecimal executionUnit = BigDecimal.ZERO;

											List<String> commodityType = new ArrayList<>();
											commodityType.add("ALL");
											commodityType.add(assessment.getCommodityCode());

											if ("MTON".equals(unit)) {
												String serviceGrp = String.valueOf(f[18]);

												if ("H".equals(serviceGrp)) {
													BigDecimal cargoWt = new BigDecimal(
															String.valueOf(con.getGrossWt()))
															.divide(new BigDecimal(1000))
															.setScale(3, RoundingMode.HALF_UP);

													executionUnit = cargoWt;

													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else {
													BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000))
															.setScale(3, RoundingMode.HALF_UP);

													executionUnit = cargoWt;
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}

												Object rangeValue = cfstariffservicerepo
														.getRangeDataByForbondNocServiceId(cid, bid,
																String.valueOf(f[3]), String.valueOf(f[4]),
																String.valueOf(f[0]), executionUnit, commodityType,
																assessment.getCommodityCode());

												if (rangeValue != null) {
													Object[] finalRangeValue = (Object[]) rangeValue;
													tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
													tempAss.setDiscPercentage(
															(f[13] != null && !String.valueOf(f[13]).isEmpty())
																	? new BigDecimal(String.valueOf(f[13]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[14] != null && !String.valueOf(f[14]).isEmpty())
																	? new BigDecimal(String.valueOf(f[14]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(
															(f[15] != null && !String.valueOf(f[15]).isEmpty())
																	? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setmAmount(
															(f[16] != null && !String.valueOf(f[16]).isEmpty())
																	? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
													tempAss.setContainerStatus(con.getContainerStatus());
													tempAss.setGateOutId(con.getGateOutId());
													tempAss.setGatePassNo(con.getGatePassNo());
													tempAss.setTaxPerc(
															(f[12] == null || String.valueOf(f[12]).isEmpty())
																	? BigDecimal.ZERO
																	: new BigDecimal(String.valueOf(f[12])));
													tempAss.setTaxId(String.valueOf(f[11]));
													BigDecimal totalRate = new BigDecimal(
															String.valueOf(finalRangeValue[8]));

													tempAss.setServiceRate(totalRate);
													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(
																new BigDecimal(String.valueOf(finalRangeValue[8])));
													}

												}

											} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
													|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
													|| "SHFT".equals(unit) || "KG".equals(unit)) {

												if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
														|| "ACT".equals(unit) || "PCK".equals(unit)
														|| "SHFT".equals(unit)) {
													executionUnit = new BigDecimal("1");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}

												if ("TEU".equals(unit)) {
													if ("20".equals(con.getContainerSize())
															|| "22".equals(con.getContainerSize())) {
														executionUnit = new BigDecimal("1");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else if ("40".equals(con.getContainerSize())) {
														executionUnit = new BigDecimal("2");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else if ("45".equals(con.getContainerSize())) {
														executionUnit = new BigDecimal("2.5");
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}
												}

												if ("KG".equals(unit)) {
													String serviceGrp = String.valueOf(f[18]);
													if ("H".equals(serviceGrp)) {

														BigDecimal cargoWt = new BigDecimal(
																String.valueOf(con.getGrossWt()));

														executionUnit = cargoWt;

														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else {
														BigDecimal cargoWt = con.getCargoWt();

														executionUnit = cargoWt;
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}
												}
												Object rangeValue = cfstariffservicerepo
														.getRangeDataByForbondNocServiceId(cid, bid,
																String.valueOf(f[3]), String.valueOf(f[4]),
																String.valueOf(f[0]), executionUnit, commodityType,
																assessment.getCommodityCode());

												if (rangeValue != null) {
													Object[] finalRangeValue = (Object[]) rangeValue;
													tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
													tempAss.setDiscPercentage(
															(f[13] != null && !String.valueOf(f[13]).isEmpty())
																	? new BigDecimal(String.valueOf(f[13]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[14] != null && !String.valueOf(f[14]).isEmpty())
																	? new BigDecimal(String.valueOf(f[14]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(
															(f[15] != null && !String.valueOf(f[15]).isEmpty())
																	? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setmAmount(
															(f[16] != null && !String.valueOf(f[16]).isEmpty())
																	? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[20] != null ? new BigDecimal(String.valueOf(f[20]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
													tempAss.setContainerStatus(con.getContainerStatus());
													tempAss.setGateOutId(con.getGateOutId());
													tempAss.setGatePassNo(con.getGatePassNo());
													tempAss.setTaxPerc(
															(f[12] == null || String.valueOf(f[12]).isEmpty())
																	? BigDecimal.ZERO
																	: new BigDecimal(String.valueOf(f[12])));
													tempAss.setTaxId(String.valueOf(f[11]));
													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
													BigDecimal totalRate = new BigDecimal(
															String.valueOf(finalRangeValue[8]));

													tempAss.setServiceRate(totalRate);

													String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
													String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
													String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
													String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else if (mPerc != null && !mPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(mPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
																BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else if (mAmt != null && !mAmt.isEmpty()) {
														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);
													} else {
														tempAss.setRates(
																new BigDecimal(String.valueOf(finalRangeValue[8])));
													}
												}

											}

											else {
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														(f[13] != null && !String.valueOf(f[13]).isEmpty())
																? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
														? new BigDecimal(String.valueOf(f[14]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(
														(f[15] != null && !String.valueOf(f[15]).isEmpty())
																? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
														? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
												BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
												tempAss.setServiceRate(totalRate);

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
												}

											}

										} else {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setExecutionUnit(String.valueOf(1));
											tempAss.setExecutionUnit1("");
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
											BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

											tempAss.setServiceRate(totalRate);

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
											}
										}

										finalConData.add(tempAss);

									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

						} else {

							try {
								tempAss = (AssessmentContainerDTO) con.clone();

								LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
										.atZone(ZoneId.systemDefault()).toLocalDateTime();
								LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59).withSecond(59)
										.withNano(0);
								Date updatedInvDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

								Date invDate = updatedInvDate;

								tempAss.setInvoiceDate(invDate);
								if ("SA".equals(String.valueOf(f[8]))) {
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
											? new BigDecimal(String.valueOf(f[13]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
											? new BigDecimal(String.valueOf(f[14]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
											? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
											? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(
											f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
									tempAss.setRangeTo(
											f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
									tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setTaxId(String.valueOf(f[11]));
									tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

									List<String> commodityType = new ArrayList<>();
									commodityType.add("ALL");
									commodityType.add(assessment.getCommodityCode());

									List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceIdForBondNOC(cid,
											bid, String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
											String.valueOf(f[8]), commodityType, assessment.getCommodityCode());

									if (!rangeValues.isEmpty()) {

										BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
												.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																													// where
																													// rate
																													// is
																													// 0
												.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																				// BigDecimal
												.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
												.orElse(BigDecimal.ZERO); // Default value if no match found

										tempAss.setFreeDays(freeDaysForExportContainerStorage);

										String unit = String.valueOf(f[1]);

										AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
												BigDecimal.ZERO);
										if ("DAY".equals(unit)) {

//							LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//									convertToLocalDateTime(con.getGateInDate()),
//									day.getStartTime());
//							LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//									convertToLocalDateTime(con.getDestuffDate()),
//									day.getEndTime());

											LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
													con.getLastInvoiceUptoDate().toInstant(), ZoneId.systemDefault());
											LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
													con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime);

											if (daysBetween == 0
													&& ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime) > 0) {
												daysBetween = 1;
											}

											final long daysBetween1 = daysBetween;

											tempAss.setExecutionUnit(String.valueOf(daysBetween1));
											tempAss.setExecutionUnit1("");

											System.out.println("daysBetween " + daysBetween1);

											BigDecimal totalRate = rangeValues.stream().map(r -> {

												int fromRange = ((BigDecimal) r[6]).intValue();
												int toRange = ((BigDecimal) r[7]).intValue();

												BigDecimal rate = (BigDecimal) r[8];

												if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
													serviceRate.set(rate); // Set the rate for the
																			// matching
																			// slab
												}

												long daysInSlab = Math.min(toRange, daysBetween1)
														- Math.max(fromRange, 1) + 1;

												daysInSlab = Math.max(daysInSlab, 0);

												serviceRate.set(rate);
												return rate.multiply(new BigDecimal(daysInSlab));
											}).reduce(BigDecimal.ZERO, BigDecimal::add);
											totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
											tempAss.setServiceRate(serviceRate.get());

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(totalRate);
											}

										}

										if ("WEEK".equals(unit)) {

											LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
													con.getLastInvoiceUptoDate().toInstant(), ZoneId.systemDefault());
											LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
													con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime);

											if (daysBetween == 0
													&& ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime) > 0) {
												daysBetween = 1;
											}

											final long daysBetween1 = daysBetween;
											long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

											tempAss.setExecutionUnit(String.valueOf(weeksBetween));
											tempAss.setExecutionUnit1("");

											BigDecimal totalRate = rangeValues.stream().filter(r -> {
												int fromRange = ((BigDecimal) r[6]).intValue();
												int toRange = ((BigDecimal) r[7]).intValue();
												// Include slabs that overlap with weeksBetween
												return weeksBetween >= fromRange;
											}).map(r -> {
												int fromRange = ((BigDecimal) r[6]).intValue();
												int toRange = ((BigDecimal) r[7]).intValue();
												BigDecimal rate = (BigDecimal) r[8];

												// Adjust the fromRange to exclude week 0
												int adjustedFromRange = Math.max(fromRange, 1);

												// Determine actual weeks in this slab that contribute
												// to the total
												long weeksInSlab = Math.max(0,
														Math.min(toRange, weeksBetween) - adjustedFromRange + 1);

												// Exclude slabs where weeksInSlab is 0
												return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
														: BigDecimal.ZERO;
											}).reduce(BigDecimal.ZERO, BigDecimal::add);

											totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
											if ("SM".equals(String.valueOf(f[7]))) {
												totalRate = (totalRate.multiply(
														con.getArea() == null ? new BigDecimal(1) : con.getArea()))
														.setScale(3, RoundingMode.HALF_UP);
											}
											System.out.println("totalRate " + totalRate + " " + weeksBetween);
											tempAss.setServiceRate(serviceRate.get());
											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(totalRate);
											}

										}

									}

								} else if ("RA".equals(String.valueOf(f[8]))) {

									String unit = String.valueOf(f[1]);
									BigDecimal executionUnit = BigDecimal.ZERO;

									List<String> commodityType = new ArrayList<>();
									commodityType.add("ALL");
									commodityType.add(assessment.getCommodityCode());

									if ("MTON".equals(unit)) {
										String serviceGrp = String.valueOf(f[18]);

										if ("H".equals(serviceGrp)) {
											BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()))
													.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;

											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else {
											BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000))
													.setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}

										Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(cid,
												bid, String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												executionUnit, commodityType, assessment.getCommodityCode());

										if (rangeValue != null) {
											Object[] finalRangeValue = (Object[]) rangeValue;
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											BigDecimal totalRate = new BigDecimal(String.valueOf(finalRangeValue[8]));

											tempAss.setServiceRate(totalRate);
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(finalRangeValue[8])));
											}

										}

									} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
											|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
											|| "SHFT".equals(unit) || "KG".equals(unit)) {

										if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
												|| "ACT".equals(unit) || "PCK".equals(unit) || "SHFT".equals(unit)) {
											executionUnit = new BigDecimal("1");
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}

										if ("TEU".equals(unit)) {
											if ("20".equals(con.getContainerSize())
													|| "22".equals(con.getContainerSize())) {
												executionUnit = new BigDecimal("1");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else if ("40".equals(con.getContainerSize())) {
												executionUnit = new BigDecimal("2");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else if ("45".equals(con.getContainerSize())) {
												executionUnit = new BigDecimal("2.5");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}
										}

										if ("KG".equals(unit)) {
											String serviceGrp = String.valueOf(f[18]);
											if ("H".equals(serviceGrp)) {

												BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()));

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getCargoWt();

												executionUnit = cargoWt;
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}
										}

										Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(cid,
												bid, String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												executionUnit, commodityType, assessment.getCommodityCode());

										if (rangeValue != null) {
											Object[] finalRangeValue = (Object[]) rangeValue;
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
											BigDecimal totalRate = new BigDecimal(String.valueOf(finalRangeValue[8]));

											tempAss.setServiceRate(totalRate);

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(finalRangeValue[8])));
											}
										}

									}

									else {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
										tempAss.setServiceRate(totalRate);

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
										}

									}

								} else {
									System.out.println("f[13] " + String.valueOf(f[2]) + " " + String.valueOf(f[0]));
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
											? new BigDecimal(String.valueOf(f[13]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
											? new BigDecimal(String.valueOf(f[14]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
											? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
											? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(
											f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
									tempAss.setRangeTo(
											f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
									tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setExecutionUnit(String.valueOf(1));
									tempAss.setExecutionUnit1("");
									tempAss.setTaxId(String.valueOf(f[11]));
									tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
									BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

									tempAss.setServiceRate(totalRate);

									System.out.println("String.valueOf(f[2]) " + String.valueOf(f[2]));

									String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
									String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
									String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
									String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else if (mPerc != null && !mPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(mPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);
									} else if (mAmt != null && !mAmt.isEmpty()) {
										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);
									} else {
										tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
									}
								}

								finalConData.add(tempAss);

							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});

					AssessmentSheetPro tempAssessment = (AssessmentSheetPro) assessment.clone();

					Cfbondnoc noc = cfBondNocRepo.getDataByNocAndTrans(cid, bid, assessment.getIgmTransId(),
							assessment.getIgmNo());

					if (noc == null) {
						return new ResponseEntity<>("Noc data not found", HttpStatus.CONFLICT);
					}

					AssessmentSheetPro newAss = tempAssessment;

					newAss.setCompanyId(cid);
					newAss.setBranchId(bid);
					newAss.setAssesmentLineNo(String.valueOf(sr));
					newAss.setStatus('A');
					newAss.setCreatedBy(user);
					newAss.setCreatedDate(new Date());
					newAss.setApprovedBy(user);
					newAss.setApprovedDate(new Date());
					newAss.setAssesmentDate(new Date());
					newAss.setTransType("Bond Cargo");
					newAss.setProfitcentreId(con.getProfitcentreId());
					newAss.setSbNo(noc.getBoeNo());
					newAss.setSbDate(noc.getBoeDate());
					newAss.setAssesmentId(HoldNextIdD1);

					Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

					String billingId = "IMP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
							: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
									: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
											: assessment.getOthPartyId();

					String billingAdd = "IMP".equals(assessment.getBillingParty())
							? String.valueOf(assessment.getImpSrNo())
							: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
									: "FWR".equals(assessment.getBillingParty())
											? String.valueOf(assessment.getAccSrNo())
											: assessment.getOthSrNo();

					PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

					newAss.setPartyId(billingId);
					newAss.setPartySrNo(new BigDecimal(billingAdd));
					newAss.setPayableParty(assessment.getBillingParty());
					newAss.setPayablePartyId(billingId);

					if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
						newAss.setIgst("Y");
						newAss.setCgst("N");
						newAss.setSgst("N");
					} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
						newAss.setIgst("E");
						newAss.setCgst("N");
						newAss.setSgst("N");
					}

					else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {

						if (b.getState().equals(p.getState())) {
							newAss.setIgst("N");
							newAss.setCgst("Y");
							newAss.setSgst("Y");
						} else {
							newAss.setIgst("Y");
							newAss.setCgst("N");
							newAss.setSgst("N");
						}

					} else {
						newAss.setIgst("N");
						newAss.setCgst("N");
						newAss.setSgst("N");
					}

					newAss.setNoOfPackages(new BigDecimal(con.getExamPercentage()));

					List<AssessmentContainerDTO> newAnxData = finalConData.stream().map(item -> {
						try {
							return (AssessmentContainerDTO) item.clone(); // Clone first
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
							return null;
						}
					}).filter(Objects::nonNull) // Remove null values from failed clones
							.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
																								// cloning
							.collect(Collectors.toList());

					List<AssessmentContainerDTO> filteredData1 = newAnxData.stream()
							.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());

					String crgDays = "";

					if (!filteredData1.isEmpty()) {
						crgDays = filteredData1.get(0).getExecutionUnit();
					}

					BigDecimal totalRates1 = filteredData1.stream().map(AssessmentContainerDTO::getRates)
							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

					newAss.setContainerNo("");
					newAss.setContainerSize("");
					newAss.setContainerType("");
					newAss.setGateInDate(assessment.getIgmDate());
					newAss.setGateOutDate(null);
					newAss.setGrossWeight(con.getGrossWt());
					newAss.setGateOutType("");
					newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
					newAss.setTypeOfContainer("");
					newAss.setScanerType("");
					newAss.setAreaUsed(con.getCargoWt());
					newAss.setExaminedPercentage(BigDecimal.ZERO);
					newAss.setWeighmentFlag("N");
					newAss.setCargoWeight(con.getGrossWt());
					newAss.setDestuffDate(null);
					newAss.setCalculateInvoice('N');
					newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
					newAss.setContainerHandlingAmt(BigDecimal.ZERO);
					newAss.setContainerStorageAmt(totalRates1);
					newAss.setInvoiceCategory(assessment.getInvoiceCategory());
					newAss.setInvType("NOCBondInv");
					newAss.setSa("");
					newAss.setSl("");
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrTransId(con.getSsrTransId());
					newAss.setLastAssesmentDate(assessment.getLastAssesmentDate());
					newAss.setLastAssesmentId(assessment.getLastAssesmentId());
					newAss.setLastInvoiceDate(assessment.getLastInvoiceDate());
					newAss.setLastInvoiceNo(assessment.getLastInvoiceNo());
					newAss.setLastInvoiceUptoDate(con.getLastInvoiceUptoDate());
					newAss.setInsuranceValue(new BigDecimal(con.getContainerNo()));
					newAss.setNoOf20ft(con.getContainerSize());
					newAss.setNoOf40ft(con.getContainerType());
					newAss.setNocWeeks(new BigDecimal(con.getGateOutType()));
					newAss.setNocFromDate(con.getGateInDate());
					newAss.setNocValidityDate(con.getGateoutDate());
					assessmentsheetprorepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P05109", HoldNextIdD1, "2024");

//					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

					finalConData.stream().forEach(c -> {
						if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
							CfinvsrvanxPro anx = new CfinvsrvanxPro();
							anx.setCompanyId(cid);
							anx.setBranchId(bid);
							anx.setErpDocRefNo(newAss.getIgmTransId());
							anx.setLineNo(String.valueOf(anxSr.get()));
							anx.setProcessTransId(HoldNextIdD1);
							anx.setServiceId(c.getServiceId());
							anx.setSrlNo(srNo1.get());
							anx.setDocRefNo(newAss.getIgmNo());
							anx.setIgmLineNo("");
							anx.setProfitcentreId(newAss.getProfitcentreId());
							anx.setInvoiceType("BON");
							anx.setInvoiceSubType("NOC");
							anx.setServiceUnit(c.getServiceUnit());
							anx.setExecutionUnit(c.getExecutionUnit());
							anx.setServiceUnit1(c.getServiceUnit1());
							anx.setExecutionUnit1(c.getExecutionUnit1());
							anx.setRate(c.getServiceRate());
							anx.setActualNoOfPackages(c.getRates());
							anx.setCurrencyId("INR");
							anx.setLocalAmt(c.getRates());
							anx.setInvoiceAmt(c.getRates());
							anx.setCommodityDescription(newAss.getCommodityDescription());
							anx.setDiscPercentage(c.getDiscPercentage());
							anx.setDiscValue(c.getDiscValue());
							anx.setmPercentage(c.getmPercentage());
							anx.setmAmount(c.getmAmount());
							anx.setAcCode(c.getAcCode());
							anx.setProcessTransDate(newAss.getAssesmentDate());
							anx.setProcessId("P01102");
							anx.setPartyId(newAss.getPartyId());
							anx.setWoNo(c.getWoNo());
							anx.setWoAmndNo(c.getWoAmndNo());
							anx.setContainerNo("");
							anx.setContainerStatus("");
							anx.setGateOutDate(con.getGateoutDate());
							anx.setAddServices("N");
							anx.setStartDate(con.getGateInDate());
							anx.setInvoiceUptoDate(c.getInvoiceDate());
							anx.setInvoiceUptoWeek(c.getInvoiceDate());
							anx.setStatus("A");
							anx.setCreatedBy(user);
							anx.setCreatedDate(new Date());
//			anx.setApprovedBy(user);
//			anx.setApprovedDate(new Date());
							anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
							anx.setSrvManualFlag("N");
							anx.setCriteria(
									"SA".equals(c.getCriteria()) ? "DW" : "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
							anx.setRangeFrom(c.getRangeFrom());
							anx.setRangeTo(c.getRangeTo());
							anx.setRangeType(c.getContainerStatus());
							anx.setGateOutId(c.getGateOutId());
							anx.setGatePassNo(c.getGatePassNo());
							anx.setRangeType(c.getCriteria());
							anx.setTaxId(c.getTaxId());
							anx.setExRate(c.getExRate());
							anx.setFreeDays(c.getFreeDays());
							if (("Y".equals(newAss.getIgst()))
									|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
								anx.setTaxPerc(c.getTaxPerc());
							} else {
								anx.setTaxPerc(BigDecimal.ZERO);
							}

							totalAmount.set(totalAmount.get().add(c.getRates()));

							cfinvsrvanxprorepo.save(anx);

							srNo1.set(srNo1.get().add(new BigDecimal(1)));
						}
					});
					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

					assessment.setAssesmentId(HoldNextIdD1);

					sr++;

				}

			}

		} else {
			List<AssessmentSheetPro> existingData = assessmentsheetprorepo.getDataByAssessmentId(cid, bid,
					assessment.getAssesmentId());

			if (existingData.isEmpty()) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			} else {
				existingData.stream().forEach(c -> {
					c.setCreditType(assessment.getCreditType());
					c.setComments(assessment.getComments());
					c.setIntComments(assessment.getIntComments());
					assessmentsheetprorepo.save(c);
				});
			}

		}

		List<Object[]> result = assessmentsheetprorepo.getAssessmentDataForNoc(cid, bid, assessment.getAssesmentId());

		System.out.println("result " + result);
		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
			AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

			result.stream().forEach(r -> {
				BigDecimal rate = new BigDecimal(String.valueOf(r[70]));
				BigDecimal taxPerc = new BigDecimal(String.valueOf(r[73]));

				System.out.println("TaxPerc " + String.valueOf(r[69]) + " " + taxPerc);
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

			String pId = "";

			if ("CHA".equals(assessment.getBillingParty())) {

				pId = assessment.getCha();

			} else if ("IMP".equals(assessment.getBillingParty())) {
				pId = assessment.getImporterId();
			} else if ("FWR".equals(assessment.getBillingParty())) {
				pId = assessment.getOnAccountOf();
			} else if ("OTH".equals(assessment.getBillingParty())) {
				pId = assessment.getOthPartyId();
			}

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

	}

	@Transactional
	public ResponseEntity<?> saveBondNocInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheetPro assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheetPro.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheetPro> oldAssessmentData = assessmentsheetprorepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<CfinvsrvanxPro> oldAnxData = cfinvsrvanxprorepo.getDataByProcessTransId(cid, bid,
				assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05111";
		} else {
			processNextId = "P05110";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

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

			cfinvsrvanxprorepo.save(o);

		});

		List<CfinvsrvanxPro> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, CfinvsrvanxPro> groupedData = newAnxData.stream()
				.collect(Collectors.toMap(CfinvsrvanxPro::getServiceId, // Key:
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
		List<CfinvsrvanxPro> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			CfinvsrvdtlPro dtl = new CfinvsrvdtlPro();
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

			cfinvsrvdtlprorepo.save(dtl);
		});

		AssessmentSheetPro assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05108", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		CfinvsrvPro srv = new CfinvsrvPro();
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
		srv.setInvoiceType("BON");
		srv.setTransType("CRG");
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
		srv.setInvoiceSubType("INB");

		cfinvsrvprorepo.save(srv);

		processnextidrepo.updateAuditTrail(cid, bid, processNextId, HoldNextIdD1, "2024");
		processnextidrepo.updateAuditTrail(cid, bid, "P05108", HoldNextIdD2, "2024");

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

			assessmentsheetprorepo.save(o);

		});

		List<Object[]> result = assessmentsheetprorepo.getAssessmentDataForNoc(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		CfinvsrvPro existingSrv = cfinvsrvprorepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		AssessmentSheetPro tdsData = assessmentsheetprorepo.getTdsData(cid, bid, assessment.getAssesmentId());

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);

		if (tdsData != null) {
			finalResult.put("tdsDeductee", tdsData.getTds());
			Party p = partyRepository.getDataById(cid, bid, tdsData.getTdsDeductee());

			if (p != null) {

				finalResult.put("tdsperc", p.getTdsPercentage());
				finalResult.put("tanNo", p.getTanNoId());
			} else {
				finalResult.put("tdsperc", "");
				finalResult.put("tanNo", "");
			}

		} else {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);
	}
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveExbondAssessmentData(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {

		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheetPro assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheetPro.class);

		if (assessment == null) {
			return new ResponseEntity<>("NOC data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("NOC data not found", HttpStatus.CONFLICT);
		}

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {

			int sr = 1;

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05112", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("BEXA%06d", nextNumericNextID1);

			for (AssessmentContainerDTO con : containerData) {
				List<AssessmentContainerDTO> finalConData = new ArrayList<>();

				List<String> serviceMappingData = new ArrayList<>();

				if ("SINGLE".equals(assessment.getInvoiceCategory())) {
					serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice1(cid, bid,
							con.getProfitcentreId(), "EXBOND");
				} else {

					String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
					serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice2(cid, bid,
							con.getProfitcentreId(), "EXBOND", group);
				}

				if (serviceMappingData.isEmpty()) {
					return new ResponseEntity<>("Service mapping not found", HttpStatus.CONFLICT);
				}

				Set<String> combinedService = new HashSet<>();
				combinedService.addAll(serviceMappingData);

				List<String> partyIdList = new ArrayList<>();
				partyIdList.add(assessment.getOthPartyId());
				partyIdList.add("");

				List<String> impIdList = new ArrayList<>();
				impIdList.add(assessment.getImporterId());
				impIdList.add("");

				List<String> chaIdList = new ArrayList<>();
				chaIdList.add(assessment.getCha());
				chaIdList.add("");

				List<String> fwIdList = new ArrayList<>();
				fwIdList.add(assessment.getOnAccountOf());
				fwIdList.add("");

				String tariffNo = cfstarrifrepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList, fwIdList,
						assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
						assessment.getOnAccountOf());

				con.setUpTariffNo(tariffNo);

				if (con.getUpTariffNo() == null || con.getUpTariffNo().isEmpty()) {
					return new ResponseEntity<>("Tariff no not found.", HttpStatus.CONFLICT);
				}

				// Find default service
				List<String> defaultService = cfstarrifrepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

				if (!defaultService.isEmpty()) {
					combinedService.addAll(defaultService);
				}

				List<SSRDtl> ssrServices = new ArrayList<>();

				if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
					ssrServices = ssrRepo.getServiceId1(cid, bid, con.getSsrTransId());

					if (!ssrServices.isEmpty()) {
						List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId) // Extract
																									// serviceId
								.collect(Collectors.toList()); // Collect into a list

						combinedService.addAll(ssrServiceId);
					}

				}

				List<SSRDtl> finalSsrServices = ssrServices;

				List<String> finalServices = new ArrayList<>(combinedService);

				List<Object[]> finalPricing = new ArrayList<>();
				List<String> notInPricing = new ArrayList<>();

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String formattedDate = formatter.format(new Date());
				Date parsedDate = formatter.parse(formattedDate);

				List<Object[]> pricingData = cfstariffservicerepo.getServiceRateForBondNoc(cid, bid, parsedDate,
						finalServices, con.getUpTariffNo());

				if (!pricingData.isEmpty()) {
					List<Object[]> remainingPricing = pricingData.stream()
							.filter(data1 -> finalServices.contains(data1[0].toString())).collect(Collectors.toList());

					notInPricing = finalServices.stream().filter(
							service -> pricingData.stream().noneMatch(data1 -> service.equals(data1[0].toString())))
							.collect(Collectors.toList());

					finalPricing.addAll(remainingPricing);
				} else {
					notInPricing.addAll(finalServices);
				}

				if (!notInPricing.isEmpty()) {
					List<String> tempPricing = notInPricing;

					List<Object[]> pricingData1 = cfstariffservicerepo.getServiceRateForBondNoc(cid, bid, parsedDate,
							notInPricing, "CFS1000001");

					if (!pricingData1.isEmpty()) {
						List<Object[]> remainingPricing = pricingData1.stream()
								.filter(data1 -> tempPricing.contains(data1[0].toString()))
								.collect(Collectors.toList());

						finalPricing.addAll(remainingPricing);
					}
				}

				finalPricing.stream().forEach(f -> {
					AssessmentContainerDTO tempAss = new AssessmentContainerDTO();
					CFSDay day = cfsdayrepo.getData(cid, bid);

					if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

						if (!finalSsrServices.isEmpty()) {

							SSRDtl rate1 = finalSsrServices.stream()
									.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
									.orElse(null); // Return null if no match is found

							if (rate1 != null) {

								try {
									tempAss = (AssessmentContainerDTO) con.clone();

									tempAss = (AssessmentContainerDTO) con.clone();

									LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
											.atZone(ZoneId.systemDefault()).toLocalDateTime();
									LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59).withSecond(59)
											.withNano(0);
									Date updatedInvDate = Date
											.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

									Date invDate = updatedInvDate;

									tempAss.setInvoiceDate(invDate);
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
											? new BigDecimal(String.valueOf(f[13]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
											? new BigDecimal(String.valueOf(f[14]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
											? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
											? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(
											f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
									tempAss.setRangeTo(
											f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
									tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									tempAss.setRates(rate1.getTotalRate());
									tempAss.setServiceRate(rate1.getRate());
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setTaxId(String.valueOf(f[11]));
									tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

									finalConData.add(tempAss);
								} catch (CloneNotSupportedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								try {
									tempAss = (AssessmentContainerDTO) con.clone();

									LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
											.atZone(ZoneId.systemDefault()).toLocalDateTime();
									LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59).withSecond(59)
											.withNano(0);
									Date updatedInvDate = Date
											.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

									Date invDate = updatedInvDate;

									tempAss.setInvoiceDate(invDate);
									if ("SA".equals(String.valueOf(f[8]))) {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										List<String> commodityType = new ArrayList<>();
										commodityType.add("ALL");
										commodityType.add(assessment.getCommodityCode());

										List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceIdForBondNOC(
												cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
												String.valueOf(f[0]), String.valueOf(f[8]), commodityType,
												assessment.getCommodityCode());

										if (!rangeValues.isEmpty()) {

											BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
													.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																														// where
																														// rate
																														// is
																														// 0
													.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																					// BigDecimal
													.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
													.orElse(BigDecimal.ZERO); // Default value if no match found

											tempAss.setFreeDays(freeDaysForExportContainerStorage);

											String unit = String.valueOf(f[1]);

											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
													BigDecimal.ZERO);
											if ("DAY".equals(unit)) {

//														LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//																convertToLocalDateTime(con.getGateInDate()),
//																day.getStartTime());
//														LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//																convertToLocalDateTime(con.getDestuffDate()),
//																day.getEndTime());

												LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
														con.getGateInDate().toInstant(), ZoneId.systemDefault());
												LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
														con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

												final long daysBetween1 = daysBetween;

												tempAss.setExecutionUnit(String.valueOf(daysBetween1));
												tempAss.setExecutionUnit1("");

												System.out.println("daysBetween " + daysBetween1);

												BigDecimal totalRate = rangeValues.stream().map(r -> {

													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();

													BigDecimal rate = (BigDecimal) r[8];

													if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
														serviceRate.set(rate); // Set the rate for the
																				// matching
																				// slab
													}

													long daysInSlab = Math.min(toRange, daysBetween1)
															- Math.max(fromRange, 1) + 1;

													daysInSlab = Math.max(daysInSlab, 0);

													serviceRate.set(rate);
													return rate.multiply(new BigDecimal(daysInSlab));
												}).reduce(BigDecimal.ZERO, BigDecimal::add);
												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
												tempAss.setServiceRate(serviceRate.get());

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(totalRate);
												}

											}

											if ("WEEK".equals(unit)) {

												LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
														con.getGateInDate().toInstant(), ZoneId.systemDefault());
												LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
														con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

												final long daysBetween1 = daysBetween;
												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

												tempAss.setExecutionUnit(String.valueOf(weeksBetween));
												tempAss.setExecutionUnit1("");

												BigDecimal totalRate = rangeValues.stream().filter(r -> {
													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();
													// Include slabs that overlap with weeksBetween
													return weeksBetween >= fromRange;
												}).map(r -> {
													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();
													BigDecimal rate = (BigDecimal) r[8];

													// Adjust the fromRange to exclude week 0
													int adjustedFromRange = Math.max(fromRange, 1);

													// Determine actual weeks in this slab that contribute
													// to the total
													long weeksInSlab = Math.max(0,
															Math.min(toRange, weeksBetween) - adjustedFromRange + 1);

													// Exclude slabs where weeksInSlab is 0
													return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
															: BigDecimal.ZERO;
												}).reduce(BigDecimal.ZERO, BigDecimal::add);

												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
												if ("SM".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(
															con.getArea() == null ? new BigDecimal(1) : con.getArea()))
															.setScale(3, RoundingMode.HALF_UP);
												}
												System.out.println("totalRate " + totalRate + " " + weeksBetween);
												tempAss.setServiceRate(serviceRate.get());
												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(totalRate);
												}

											}

										}

									} else if ("RA".equals(String.valueOf(f[8]))) {

										String unit = String.valueOf(f[1]);
										BigDecimal executionUnit = BigDecimal.ZERO;

										List<String> commodityType = new ArrayList<>();
										commodityType.add("ALL");
										commodityType.add(assessment.getCommodityCode());

										if ("MTON".equals(unit)) {
											String serviceGrp = String.valueOf(f[18]);

											if ("H".equals(serviceGrp)) {
												BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()))
														.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000))
														.setScale(3, RoundingMode.HALF_UP);

												executionUnit = cargoWt;
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}

											Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(
													cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
													String.valueOf(f[0]), executionUnit, commodityType,
													assessment.getCommodityCode());

											if (rangeValue != null) {
												Object[] finalRangeValue = (Object[]) rangeValue;
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														(f[13] != null && !String.valueOf(f[13]).isEmpty())
																? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
														? new BigDecimal(String.valueOf(f[14]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(
														(f[15] != null && !String.valueOf(f[15]).isEmpty())
																? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
														? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												BigDecimal totalRate = new BigDecimal(
														String.valueOf(finalRangeValue[8]));

												tempAss.setServiceRate(totalRate);
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(
															new BigDecimal(String.valueOf(finalRangeValue[8])));
												}

											}

										} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
												|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
												|| "SHFT".equals(unit) || "KG".equals(unit)) {

											if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
													|| "ACT".equals(unit) || "PCK".equals(unit)
													|| "SHFT".equals(unit)) {
												executionUnit = new BigDecimal("1");
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}

											if ("TEU".equals(unit)) {
												if ("20".equals(con.getContainerSize())
														|| "22".equals(con.getContainerSize())) {
													executionUnit = new BigDecimal("1");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else if ("40".equals(con.getContainerSize())) {
													executionUnit = new BigDecimal("2");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else if ("45".equals(con.getContainerSize())) {
													executionUnit = new BigDecimal("2.5");
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}
											}

											if ("KG".equals(unit)) {
												String serviceGrp = String.valueOf(f[18]);
												if ("H".equals(serviceGrp)) {

													BigDecimal cargoWt = new BigDecimal(
															String.valueOf(con.getGrossWt()));

													executionUnit = cargoWt;

													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else {
													BigDecimal cargoWt = con.getCargoWt();

													executionUnit = cargoWt;
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}
											}
											Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(
													cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
													String.valueOf(f[0]), executionUnit, commodityType,
													assessment.getCommodityCode());

											if (rangeValue != null) {
												Object[] finalRangeValue = (Object[]) rangeValue;
												tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setDiscPercentage(
														(f[13] != null && !String.valueOf(f[13]).isEmpty())
																? new BigDecimal(String.valueOf(f[13]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
														? new BigDecimal(String.valueOf(f[14]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(
														(f[15] != null && !String.valueOf(f[15]).isEmpty())
																? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
														? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[19] != null ? new BigDecimal(String.valueOf(f[19]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
												BigDecimal totalRate = new BigDecimal(
														String.valueOf(finalRangeValue[8]));

												tempAss.setServiceRate(totalRate);

												String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
												String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
												String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
												String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else if (mPerc != null && !mPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(mPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
															BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else if (mAmt != null && !mAmt.isEmpty()) {
													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);
												} else {
													tempAss.setRates(
															new BigDecimal(String.valueOf(finalRangeValue[8])));
												}
											}

										}

										else {
											tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setDiscPercentage(
													(f[13] != null && !String.valueOf(f[13]).isEmpty())
															? new BigDecimal(String.valueOf(f[13]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
													? new BigDecimal(String.valueOf(f[14]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
													? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
													? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
											tempAss.setContainerStatus(con.getContainerStatus());
											tempAss.setGateOutId(con.getGateOutId());
											tempAss.setGatePassNo(con.getGatePassNo());
											tempAss.setTaxPerc(
													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
															: new BigDecimal(String.valueOf(f[12])));
											tempAss.setTaxId(String.valueOf(f[11]));
											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
											BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
											tempAss.setServiceRate(totalRate);

											String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
											String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
											String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
											String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else if (mPerc != null && !mPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(mPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
														BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else if (mAmt != null && !mAmt.isEmpty()) {
												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);
											} else {
												tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
											}

										}

									} else {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setExecutionUnit(String.valueOf(1));
										tempAss.setExecutionUnit1("");
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

										tempAss.setServiceRate(totalRate);

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
										}
									}

									finalConData.add(tempAss);

								} catch (CloneNotSupportedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					} else {

						try {
							tempAss = (AssessmentContainerDTO) con.clone();

							LocalDateTime localDateTime = tempAss.getInvoiceDate().toInstant()
									.atZone(ZoneId.systemDefault()).toLocalDateTime();
							LocalDateTime endOfDay = localDateTime.withHour(23).withMinute(59).withSecond(59)
									.withNano(0);
							Date updatedInvDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

							Date invDate = updatedInvDate;

							tempAss.setInvoiceDate(invDate);
							if ("SA".equals(String.valueOf(f[8]))) {
								tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
								tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
								tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
								tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
								tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
								tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
								tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
										? new BigDecimal(String.valueOf(f[13]))
										: BigDecimal.ZERO);
								tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
										? new BigDecimal(String.valueOf(f[14]))
										: BigDecimal.ZERO);
								tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
										? new BigDecimal(String.valueOf(f[15]))
										: BigDecimal.ZERO);
								tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
										? new BigDecimal(String.valueOf(f[16]))
										: BigDecimal.ZERO);
								tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
								tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
								tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
								tempAss.setRangeFrom(
										f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
								tempAss.setRangeTo(
										f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
								tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
								tempAss.setContainerStatus(con.getContainerStatus());
								tempAss.setGateOutId(con.getGateOutId());
								tempAss.setGatePassNo(con.getGatePassNo());
								tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
										: new BigDecimal(String.valueOf(f[12])));
								tempAss.setTaxId(String.valueOf(f[11]));
								tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

								List<String> commodityType = new ArrayList<>();
								commodityType.add("ALL");
								commodityType.add(assessment.getCommodityCode());

								List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceIdForBondNOC(cid, bid,
										String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
										String.valueOf(f[8]), commodityType, assessment.getCommodityCode());

								if (!rangeValues.isEmpty()) {

									BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
											.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																												// where
																												// rate
																												// is 0
											.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																			// BigDecimal
											.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
											.orElse(BigDecimal.ZERO); // Default value if no match found

									tempAss.setFreeDays(freeDaysForExportContainerStorage);

									String unit = String.valueOf(f[1]);

									AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(BigDecimal.ZERO);
									if ("DAY".equals(unit)) {

//												LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//														convertToLocalDateTime(con.getGateInDate()),
//														day.getStartTime());
//												LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//														convertToLocalDateTime(con.getDestuffDate()),
//														day.getEndTime());

										LocalDateTime gateInDateTime = LocalDateTime
												.ofInstant(con.getGateInDate().toInstant(), ZoneId.systemDefault());
										LocalDateTime destuffDateTime = LocalDateTime
												.ofInstant(con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
										long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime);

										if (daysBetween == 0
												&& ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime) > 0) {
											daysBetween = 1;
										}

										final long daysBetween1 = daysBetween;

										tempAss.setExecutionUnit(String.valueOf(daysBetween1));
										tempAss.setExecutionUnit1("");

										System.out.println("daysBetween " + daysBetween1);

										BigDecimal totalRate = rangeValues.stream().map(r -> {

											int fromRange = ((BigDecimal) r[6]).intValue();
											int toRange = ((BigDecimal) r[7]).intValue();

											BigDecimal rate = (BigDecimal) r[8];

											if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
												serviceRate.set(rate); // Set the rate for the
																		// matching
																		// slab
											}

											long daysInSlab = Math.min(toRange, daysBetween1) - Math.max(fromRange, 1)
													+ 1;

											daysInSlab = Math.max(daysInSlab, 0);

											serviceRate.set(rate);
											return rate.multiply(new BigDecimal(daysInSlab));
										}).reduce(BigDecimal.ZERO, BigDecimal::add);
										totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
										tempAss.setServiceRate(serviceRate.get());

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(totalRate);
										}

									}

									if ("WEEK".equals(unit)) {

										LocalDateTime gateInDateTime = LocalDateTime
												.ofInstant(con.getGateInDate().toInstant(), ZoneId.systemDefault());
										LocalDateTime destuffDateTime = LocalDateTime
												.ofInstant(con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
										long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime);

										if (daysBetween == 0
												&& ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime) > 0) {
											daysBetween = 1;
										}

										final long daysBetween1 = daysBetween;
										long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

										tempAss.setExecutionUnit(String.valueOf(weeksBetween));
										tempAss.setExecutionUnit1("");

										BigDecimal totalRate = rangeValues.stream().filter(r -> {
											int fromRange = ((BigDecimal) r[6]).intValue();
											int toRange = ((BigDecimal) r[7]).intValue();
											// Include slabs that overlap with weeksBetween
											return weeksBetween >= fromRange;
										}).map(r -> {
											int fromRange = ((BigDecimal) r[6]).intValue();
											int toRange = ((BigDecimal) r[7]).intValue();
											BigDecimal rate = (BigDecimal) r[8];

											// Adjust the fromRange to exclude week 0
											int adjustedFromRange = Math.max(fromRange, 1);

											// Determine actual weeks in this slab that contribute
											// to the total
											long weeksInSlab = Math.max(0,
													Math.min(toRange, weeksBetween) - adjustedFromRange + 1);

											// Exclude slabs where weeksInSlab is 0
											return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
													: BigDecimal.ZERO;
										}).reduce(BigDecimal.ZERO, BigDecimal::add);

										totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
										if ("SM".equals(String.valueOf(f[7]))) {
											totalRate = (totalRate.multiply(
													con.getArea() == null ? new BigDecimal(1) : con.getArea()))
													.setScale(3, RoundingMode.HALF_UP);
										}
										System.out.println("totalRate " + totalRate + " " + weeksBetween);
										tempAss.setServiceRate(serviceRate.get());
										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(totalRate);
										}

									}

								}

							} else if ("RA".equals(String.valueOf(f[8]))) {

								String unit = String.valueOf(f[1]);
								BigDecimal executionUnit = BigDecimal.ZERO;

								List<String> commodityType = new ArrayList<>();
								commodityType.add("ALL");
								commodityType.add(assessment.getCommodityCode());

								if ("MTON".equals(unit)) {
									String serviceGrp = String.valueOf(f[18]);

									if ("H".equals(serviceGrp)) {
										BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()))
												.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

										executionUnit = cargoWt;

										tempAss.setExecutionUnit(String.valueOf(executionUnit));
										tempAss.setExecutionUnit1("");
									} else {
										BigDecimal cargoWt = con.getCargoWt().divide(new BigDecimal(1000)).setScale(3,
												RoundingMode.HALF_UP);

										executionUnit = cargoWt;
										tempAss.setExecutionUnit(String.valueOf(executionUnit));
										tempAss.setExecutionUnit1("");
									}

									Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(cid, bid,
											String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
											executionUnit, commodityType, assessment.getCommodityCode());

									if (rangeValue != null) {
										Object[] finalRangeValue = (Object[]) rangeValue;
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										BigDecimal totalRate = new BigDecimal(String.valueOf(finalRangeValue[8]));

										tempAss.setServiceRate(totalRate);
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(new BigDecimal(String.valueOf(finalRangeValue[8])));
										}

									}

								} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
										|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
										|| "SHFT".equals(unit) || "KG".equals(unit)) {

									if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
											|| "ACT".equals(unit) || "PCK".equals(unit) || "SHFT".equals(unit)) {
										executionUnit = new BigDecimal("1");
										tempAss.setExecutionUnit(String.valueOf(executionUnit));
										tempAss.setExecutionUnit1("");
									}

									if ("TEU".equals(unit)) {
										if ("20".equals(con.getContainerSize())
												|| "22".equals(con.getContainerSize())) {
											executionUnit = new BigDecimal("1");
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else if ("40".equals(con.getContainerSize())) {
											executionUnit = new BigDecimal("2");
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else if ("45".equals(con.getContainerSize())) {
											executionUnit = new BigDecimal("2.5");
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}
									}

									if ("KG".equals(unit)) {
										String serviceGrp = String.valueOf(f[18]);
										if ("H".equals(serviceGrp)) {

											BigDecimal cargoWt = new BigDecimal(String.valueOf(con.getGrossWt()));

											executionUnit = cargoWt;

											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else {
											BigDecimal cargoWt = con.getCargoWt();

											executionUnit = cargoWt;
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}
									}

									Object rangeValue = cfstariffservicerepo.getRangeDataByForbondNocServiceId(cid, bid,
											String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
											executionUnit, commodityType, assessment.getCommodityCode());

									if (rangeValue != null) {
										Object[] finalRangeValue = (Object[]) rangeValue;
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
												? new BigDecimal(String.valueOf(f[13]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
												? new BigDecimal(String.valueOf(f[14]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
												? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
												? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[19] != null ? new BigDecimal(String.valueOf(f[19]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[20] != null ? new BigDecimal(String.valueOf(f[20]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										BigDecimal totalRate = new BigDecimal(String.valueOf(finalRangeValue[8]));

										tempAss.setServiceRate(totalRate);

										String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
										String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
										String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
										String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else if (mPerc != null && !mPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(mPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else if (mAmt != null && !mAmt.isEmpty()) {
											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);
										} else {
											tempAss.setRates(new BigDecimal(String.valueOf(finalRangeValue[8])));
										}
									}

								}

								else {
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
											? new BigDecimal(String.valueOf(f[13]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
											? new BigDecimal(String.valueOf(f[14]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
											? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
											? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(
											f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
									tempAss.setRangeTo(
											f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
									tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setTaxId(String.valueOf(f[11]));
									tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
									BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
									tempAss.setServiceRate(totalRate);

									String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
									String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
									String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
									String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else if (mPerc != null && !mPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(mPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);
									} else if (mAmt != null && !mAmt.isEmpty()) {
										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);
									} else {
										tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
									}

								}

							} else {
								System.out.println("f[13] " + String.valueOf(f[2]) + " " + String.valueOf(f[0]));
								tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
								tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
								tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
								tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
								tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
								tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
								tempAss.setDiscPercentage((f[13] != null && !String.valueOf(f[13]).isEmpty())
										? new BigDecimal(String.valueOf(f[13]))
										: BigDecimal.ZERO);
								tempAss.setDiscValue((f[14] != null && !String.valueOf(f[14]).isEmpty())
										? new BigDecimal(String.valueOf(f[14]))
										: BigDecimal.ZERO);
								tempAss.setmPercentage((f[15] != null && !String.valueOf(f[15]).isEmpty())
										? new BigDecimal(String.valueOf(f[15]))
										: BigDecimal.ZERO);
								tempAss.setmAmount((f[16] != null && !String.valueOf(f[16]).isEmpty())
										? new BigDecimal(String.valueOf(f[16]))
										: BigDecimal.ZERO);
								tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
								tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
								tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
								tempAss.setRangeFrom(
										f[19] != null ? new BigDecimal(String.valueOf(f[19])) : BigDecimal.ZERO);
								tempAss.setRangeTo(
										f[20] != null ? new BigDecimal(String.valueOf(f[20])) : BigDecimal.ZERO);
								tempAss.setAcCode(f[17] != null ? String.valueOf(f[17]) : "");
								tempAss.setContainerStatus(con.getContainerStatus());
								tempAss.setGateOutId(con.getGateOutId());
								tempAss.setGatePassNo(con.getGatePassNo());
								tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
										: new BigDecimal(String.valueOf(f[12])));
								tempAss.setExecutionUnit(String.valueOf(1));
								tempAss.setExecutionUnit1("");
								tempAss.setTaxId(String.valueOf(f[11]));
								tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
								BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));

								tempAss.setServiceRate(totalRate);

								System.out.println("String.valueOf(f[2]) " + String.valueOf(f[2]));

								String cPerc = String.valueOf(f[13] == null ? "" : f[13]);
								String cAmt = String.valueOf(f[14] == null ? "" : f[14]);
								String mPerc = String.valueOf(f[15] == null ? "" : f[15]);
								String mAmt = String.valueOf(f[16] == null ? "" : f[16]);

								if (cPerc != null && !cPerc.isEmpty()) {
									BigDecimal disPerc = new BigDecimal(cPerc);
									BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

									BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

									amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

									BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3, BigDecimal.ROUND_HALF_UP);

									tempAss.setRates(disAmt);

								} else if (cAmt != null && !cAmt.isEmpty()) {

									BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
											BigDecimal.ROUND_HALF_UP);

									tempAss.setRates(disAmt);

								} else if (mPerc != null && !mPerc.isEmpty()) {
									BigDecimal disPerc = new BigDecimal(mPerc);
									BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

									BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

									amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

									BigDecimal disAmt = (totalRate.subtract(amt)).setScale(3, BigDecimal.ROUND_HALF_UP);

									tempAss.setRates(disAmt);
								} else if (mAmt != null && !mAmt.isEmpty()) {
									BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
											BigDecimal.ROUND_HALF_UP);

									tempAss.setRates(disAmt);
								} else {
									tempAss.setRates(new BigDecimal(String.valueOf(f[2])));
								}
							}

							finalConData.add(tempAss);

						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});

				AssessmentSheetPro tempAssessment = (AssessmentSheetPro) assessment.clone();

				Cfbondnoc noc = cfBondNocRepo.getDataByNocAndTrans(cid, bid, assessment.getIgmTransId(),
						assessment.getIgmNo());

				if (noc == null) {
					return new ResponseEntity<>("Noc data not found", HttpStatus.CONFLICT);
				}

				CfExBondCrg crg = exbondrepo.findExistingCfexbondCrg1(cid, bid, assessment.getIgmTransId(),
						assessment.getIgmNo(), assessment.getViaNo(), assessment.getIgmLineNo());

				if (crg == null) {
					return new ResponseEntity<>("Exbond data not found", HttpStatus.CONFLICT);
				}

				AssessmentSheetPro newAss = tempAssessment;

				newAss.setCompanyId(cid);
				newAss.setBranchId(bid);
				newAss.setAssesmentLineNo(String.valueOf(sr));
				newAss.setStatus('A');
				newAss.setCreatedBy(user);
				newAss.setCreatedDate(new Date());
				newAss.setApprovedBy(user);
				newAss.setApprovedDate(new Date());
				newAss.setAssesmentDate(new Date());
				newAss.setTransType("Bond Cargo");
				newAss.setProfitcentreId(con.getProfitcentreId());
				newAss.setIgmLineNo("");
				newAss.setViaNo("");
				newAss.setSbNo(assessment.getIgmLineNo());
				newAss.setSbDate(crg.getExBondBeDate());
				newAss.setAssesmentId(HoldNextIdD1);
				Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

				String billingId = "IMP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
						: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
								: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
										: assessment.getOthPartyId();

				String billingAdd = "IMP".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getImpSrNo())
						: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
								: "FWR".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getAccSrNo())
										: assessment.getOthSrNo();

				PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

				newAss.setPartyId(billingId);
				newAss.setPartySrNo(new BigDecimal(billingAdd));
				newAss.setPayableParty(assessment.getBillingParty());
				newAss.setPayablePartyId(billingId);

				if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
					newAss.setIgst("Y");
					newAss.setCgst("N");
					newAss.setSgst("N");
				} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
					newAss.setIgst("E");
					newAss.setCgst("N");
					newAss.setSgst("N");
				}

				else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {

					if (b.getState().equals(p.getState())) {
						newAss.setIgst("N");
						newAss.setCgst("Y");
						newAss.setSgst("Y");
					} else {
						newAss.setIgst("Y");
						newAss.setCgst("N");
						newAss.setSgst("N");
					}

				} else {
					newAss.setIgst("N");
					newAss.setCgst("N");
					newAss.setSgst("N");
				}

				newAss.setNoOfPackages(new BigDecimal(con.getExamPercentage()));

				List<AssessmentContainerDTO> newAnxData = finalConData.stream().map(item -> {
					try {
						return (AssessmentContainerDTO) item.clone(); // Clone first
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
						return null;
					}
				}).filter(Objects::nonNull) // Remove null values from failed clones
						.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
																							// cloning
						.collect(Collectors.toList());

				List<AssessmentContainerDTO> filteredData1 = newAnxData.stream()
						.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());

				String crgDays = "";

				if (!filteredData1.isEmpty()) {
					crgDays = filteredData1.get(0).getExecutionUnit();
				}

				BigDecimal totalRates1 = filteredData1.stream().map(AssessmentContainerDTO::getRates)
						.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

				newAss.setContainerNo("");
				newAss.setContainerSize("");
				newAss.setContainerType("");
				newAss.setGateInDate(assessment.getIgmDate());
				newAss.setGateOutDate(null);
				newAss.setGrossWeight(con.getGrossWt());
				newAss.setGateOutType("");
				newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
				newAss.setTypeOfContainer("");
				newAss.setScanerType("");
				newAss.setAreaUsed(con.getCargoWt());
				newAss.setExaminedPercentage(BigDecimal.ZERO);
				newAss.setWeighmentFlag("N");
				newAss.setCargoWeight(con.getGrossWt());
				newAss.setDestuffDate(null);
				newAss.setCalculateInvoice('N');
				newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
				newAss.setContainerHandlingAmt(BigDecimal.ZERO);
				newAss.setContainerStorageAmt(totalRates1);
				newAss.setInvoiceCategory(assessment.getInvoiceCategory());
				newAss.setInvType("ExBondInv");
				newAss.setSa("");
				newAss.setSl("");
				newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
				newAss.setSsrTransId(con.getSsrTransId());
				newAss.setInsuranceValue(new BigDecimal(con.getContainerNo()));
				newAss.setNoOf20ft(con.getContainerSize());
				newAss.setNoOf40ft(con.getContainerType());
				newAss.setNocWeeks(new BigDecimal(con.getGateOutType()));
				newAss.setNocFromDate(con.getLastInvoiceUptoDate());
				newAss.setNocValidityDate(con.getGateInDate());
				newAss.setInvoiceCalDate(finalConData.get(0).getInvoiceDate());
				assessmentsheetprorepo.save(newAss);
				processnextidrepo.updateAuditTrail(cid, bid, "P05112", HoldNextIdD1, "2024");

//				AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
				AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

				finalConData.stream().forEach(c -> {
					if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
						CfinvsrvanxPro anx = new CfinvsrvanxPro();
						anx.setCompanyId(cid);
						anx.setBranchId(bid);
						anx.setErpDocRefNo(newAss.getIgmTransId());
						anx.setLineNo(String.valueOf(anxSr.get()));
						anx.setProcessTransId(HoldNextIdD1);
						anx.setServiceId(c.getServiceId());
						anx.setSrlNo(srNo1.get());
						anx.setDocRefNo(newAss.getIgmNo());
						anx.setIgmLineNo("");
						anx.setProfitcentreId(newAss.getProfitcentreId());
						anx.setInvoiceType("BON");
						anx.setInvoiceSubType("EXB");
						anx.setServiceUnit(c.getServiceUnit());
						anx.setExecutionUnit(c.getExecutionUnit());
						anx.setServiceUnit1(c.getServiceUnit1());
						anx.setExecutionUnit1(c.getExecutionUnit1());
						anx.setRate(c.getServiceRate());
						anx.setActualNoOfPackages(c.getRates());
						anx.setCurrencyId("INR");
						anx.setLocalAmt(c.getRates());
						anx.setInvoiceAmt(c.getRates());
						anx.setCommodityDescription(newAss.getCommodityDescription());
						anx.setDiscPercentage(c.getDiscPercentage());
						anx.setDiscValue(c.getDiscValue());
						anx.setmPercentage(c.getmPercentage());
						anx.setmAmount(c.getmAmount());
						anx.setAcCode(c.getAcCode());
						anx.setProcessTransDate(newAss.getAssesmentDate());
						anx.setProcessId("P01107");
						anx.setPartyId(newAss.getPartyId());
						anx.setWoNo(c.getWoNo());
						anx.setWoAmndNo(c.getWoAmndNo());
						anx.setContainerNo("");
						anx.setContainerStatus("");
						anx.setGateOutDate(con.getGateoutDate());
						anx.setAddServices("N");
						// anx.setStartDate(con.getGateInDate());
						anx.setInvoiceUptoDate(c.getInvoiceDate());
						anx.setInvoiceUptoWeek(c.getInvoiceDate());
						anx.setStatus("A");
						anx.setCreatedBy(user);
						anx.setCreatedDate(new Date());
//	  						anx.setApprovedBy(user);
//	  						anx.setApprovedDate(new Date());
						anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
						anx.setSrvManualFlag("N");
						anx.setCriteria(
								"SA".equals(c.getCriteria()) ? "DW" : "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
						anx.setRangeFrom(c.getRangeFrom());
						anx.setRangeTo(c.getRangeTo());
						anx.setRangeType(c.getContainerStatus());
						anx.setGateOutId(c.getGateOutId());
						anx.setGatePassNo(c.getGatePassNo());
						anx.setRangeType(c.getCriteria());
						anx.setTaxId(c.getTaxId());
						anx.setExRate(c.getExRate());
						anx.setStartDate(c.getLastInvoiceUptoDate());
						anx.setFreeDays(c.getFreeDays());
						if (("Y".equals(newAss.getIgst()))
								|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
							anx.setTaxPerc(c.getTaxPerc());
						} else {
							anx.setTaxPerc(BigDecimal.ZERO);
						}

						totalAmount.set(totalAmount.get().add(c.getRates()));

						cfinvsrvanxprorepo.save(anx);

						srNo1.set(srNo1.get().add(new BigDecimal(1)));
					}
				});
				totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

				assessment.setAssesmentId(HoldNextIdD1);

				
				sr++;

			}

		} else {
			List<AssessmentSheetPro> existingData = assessmentsheetprorepo.getDataByAssessmentId(cid, bid,
					assessment.getAssesmentId());

			if (existingData.isEmpty()) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			} else {
				existingData.stream().forEach(c -> {
					c.setCreditType(assessment.getCreditType());
					c.setComments(assessment.getComments());
					c.setIntComments(assessment.getIntComments());
					assessmentsheetprorepo.save(c);
				});
			}

		}

		List<Object[]> result = assessmentsheetprorepo.getAssessmentDataForExbond(cid, bid, assessment.getAssesmentId());

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
			AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

			result.stream().forEach(r -> {
				BigDecimal rate = new BigDecimal(String.valueOf(r[70]));
				BigDecimal taxPerc = new BigDecimal(String.valueOf(r[73]));

				System.out.println("TaxPerc " + String.valueOf(r[69]) + " " + taxPerc);
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

			String pId = "";

			if ("CHA".equals(assessment.getBillingParty())) {

				pId = assessment.getCha();

			} else if ("IMP".equals(assessment.getBillingParty())) {
				pId = assessment.getImporterId();
			} else if ("FWR".equals(assessment.getBillingParty())) {
				pId = assessment.getOnAccountOf();
			} else if ("OTH".equals(assessment.getBillingParty())) {
				pId = assessment.getOthPartyId();
			}

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

	}
	
	
	@Transactional
	public ResponseEntity<?> saveExbondInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheetPro assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheetPro.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}


		List<AssessmentSheetPro> oldAssessmentData = assessmentsheetprorepo.getDataByAssessmentId1(cid, bid,
				assessment.getAssesmentId());

		if (oldAssessmentData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<CfinvsrvanxPro> oldAnxData = cfinvsrvanxprorepo.getDataByProcessTransId(cid, bid, assessment.getAssesmentId());

		if (oldAnxData.isEmpty()) {
			return new ResponseEntity<>("Annexure data not found", HttpStatus.CONFLICT);
		}

		String tdsDeductee = String.valueOf(data.get("tdsDeductee"));

		String tdsPerc = String.valueOf(data.get("tdsPerc"));

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);

		AtomicReference<BigDecimal> srNo = new AtomicReference<>(new BigDecimal(1));

		String processNextId = "";

		if (assessment.getTaxApplicable() == 'Y') {
			processNextId = "P05111";
		} else {
			processNextId = "P05110";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);

		
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

			cfinvsrvanxprorepo.save(o);

		});

		List<CfinvsrvanxPro> newAnxData = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				newAnxData.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});

		Map<String, CfinvsrvanxPro> groupedData = newAnxData.stream().collect(Collectors.toMap(CfinvsrvanxPro::getServiceId, // Key:
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
		List<CfinvsrvanxPro> consolidatedData = new ArrayList<>(groupedData.values());

		consolidatedData.stream().forEach(c -> {
			CfinvsrvdtlPro dtl = new CfinvsrvdtlPro();
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

			cfinvsrvdtlprorepo.save(dtl);
		});

		AssessmentSheetPro assSheet = oldAssessmentData.get(0);

		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values
		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP); // Sums up all the
		} else {
			totalLocalAmt = consolidatedData.stream().map(CfinvsrvanxPro::getLocalAmt) // Extracts the localAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalBillAmt = consolidatedData.stream().map(CfinvsrvanxPro::getBillAmt) // Extracts the billAmt field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalInvAmt = consolidatedData.stream().map(CfinvsrvanxPro::getInvoiceAmt) // Extracts the invoiceAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
																									// values

			totalForAmt = consolidatedData.stream().map(CfinvsrvanxPro::getForeignAmt) // Extracts the foreignAmt
					// field
					.map(amt -> amt != null ? amt : BigDecimal.ZERO) // Handles null values
					.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP); // Sums up all the
		}

		String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05108", "2024");

		String pre1 = holdId2.substring(0, 4);

		int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

		int nextNumericNextID2 = lastNextNumericId2 + 1;

		String HoldNextIdD2 = String.format(pre1 + "%06d", nextNumericNextID2);

		CfinvsrvPro srv = new CfinvsrvPro();
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
		srv.setInvoiceType("BON");
		srv.setTransType("CRG");
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
		srv.setInvoiceSubType("EXB");

		cfinvsrvprorepo.save(srv);


		processnextidrepo.updateAuditTrail(cid, bid, processNextId, HoldNextIdD1, "2024");
		processnextidrepo.updateAuditTrail(cid, bid, "P05108", HoldNextIdD2, "2024");

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));


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

			assessmentsheetprorepo.save(o);


		});

	

		List<Object[]> result = assessmentsheetprorepo.getAssessmentDataForExbond(cid, bid, assessment.getAssesmentId());

		if (result == null || result.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		CfinvsrvPro existingSrv = cfinvsrvprorepo.getDataByInvoiceNo(cid, bid, HoldNextIdD1);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		AssessmentSheetPro tdsData = assessmentsheetprorepo.getTdsData(cid, bid, assessment.getAssesmentId());

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("result", result);
		finalResult.put("existingSrv", existingSrv);

		if (tdsData != null) {
			finalResult.put("tdsDeductee", tdsData.getTds());
			Party p = partyRepository.getDataById(cid, bid, tdsData.getTdsDeductee());

			if (p != null) {

				finalResult.put("tdsperc", p.getTdsPercentage());
				finalResult.put("tanNo", p.getTanNoId());
			} else {
				finalResult.put("tdsperc", "");
				finalResult.put("tanNo", "");
			}

		} else {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}
	
	
	public ResponseEntity<?> searchBondNOCInvoiceData(String cid, String bid, String val, String type) {

		List<Object[]> data = new ArrayList<>();

		if ("noc".equals(type)) {
			data = assessmentsheetprorepo.searchBondNocInvoiceData(cid, bid, val);
		} else {
			data = assessmentsheetprorepo.searchExbondInvoiceData(cid, bid, val);
		}

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	public ResponseEntity<?> getSelectedBondNOCInvoiceData(String cid, String bid, String assId, String invoiceId,
			String type) {
		if ("noc".equals(type)) {
			List<Object[]> result = assessmentsheetprorepo.getAssessmentDataForNoc(cid, bid, assId);

			if (result == null || result.isEmpty()) {
				return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
			}

			CfinvsrvPro existingSrv = cfinvsrvprorepo.getDataByInvoiceNo(cid, bid, invoiceId);

			if (existingSrv == null) {
				return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
			}

			AssessmentSheetPro tdsData = assessmentsheetprorepo.getTdsData(cid, bid, assId);

			Map<String, Object> finalResult = new HashMap<>();
			finalResult.put("result", result);
			finalResult.put("existingSrv", existingSrv);

			if (tdsData != null) {
				finalResult.put("tdsDeductee", tdsData.getTds());
				Party p = partyRepository.getDataById(cid, bid, tdsData.getTdsDeductee());

				if (p != null) {

					finalResult.put("tdsperc", p.getTdsPercentage());
					finalResult.put("tanNo", p.getTanNoId());
				} else {
					finalResult.put("tdsperc", "");
					finalResult.put("tanNo", "");
				}

			} else {
				finalResult.put("tdsDeductee", "");
				finalResult.put("tdsperc", "");
				finalResult.put("tanNo", "");
			}

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		} else {
			List<Object[]> result = assessmentsheetprorepo.getAssessmentDataForExbond(cid, bid, assId);

			if (result == null || result.isEmpty()) {
				return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
			}

			CfinvsrvPro existingSrv = cfinvsrvprorepo.getDataByInvoiceNo(cid, bid, invoiceId);

			if (existingSrv == null) {
				return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
			}

			AssessmentSheetPro tdsData = assessmentsheetprorepo.getTdsData(cid, bid, assId);

			Map<String, Object> finalResult = new HashMap<>();
			finalResult.put("result", result);
			finalResult.put("existingSrv", existingSrv);

			if (tdsData != null) {
				finalResult.put("tdsDeductee", tdsData.getTds());
				Party p = partyRepository.getDataById(cid, bid, tdsData.getTdsDeductee());

				if (p != null) {

					finalResult.put("tdsperc", p.getTdsPercentage());
					finalResult.put("tanNo", p.getTanNoId());
				} else {
					finalResult.put("tdsperc", "");
					finalResult.put("tanNo", "");
				}

			} else {
				finalResult.put("tdsDeductee", "");
				finalResult.put("tdsperc", "");
				finalResult.put("tanNo", "");
			}

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
		}
	}
	
	
	

	public ResponseEntity<?> saveBondAddService(String companyId, String branchId, String user,
			Map<String, Object> resultList, String type) {

		try {

			AssessmentSheetPro assessment = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("assesmentSheet")), AssessmentSheetPro.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			CfinvsrvanxPro anx = objectMapper.readValue(objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
					CfinvsrvanxPro.class);

			if (anx == null) {
				return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
			}

			BigDecimal zero = BigDecimal.ZERO;

			List<AssessmentSheetPro> existingData = assessmentsheetprorepo.getDataByAssessmentId(companyId, branchId,
					assessment.getAssesmentId());

			if (existingData.isEmpty()) {
				return new ResponseEntity<>("Assessment Data not found", HttpStatus.CONFLICT);
			}

			AssessmentSheetPro data = existingData.get(0);

			Optional<BigDecimal> highestSRLNoOptional = assessmentsheetprorepo.getHighestSrlNoByContainerNo1(companyId,
					branchId, data.getProfitcentreId(), data.getAssesmentId());
			BigDecimal highestSRLNo = highestSRLNoOptional.orElse(BigDecimal.ZERO);
			BigDecimal highestSRLNoNew = highestSRLNo.add(BigDecimal.ONE);

			anx.setCompanyId(companyId);
			anx.setBranchId(branchId);
			anx.setErpDocRefNo(data.getIgmTransId());
			anx.setProcessTransId(data.getAssesmentId());
			anx.setAddServices("Y");
			anx.setDocRefNo(data.getIgmNo());
			anx.setIgmLineNo("");
			anx.setProfitcentreId(data.getProfitcentreId());
			anx.setInvoiceType("BON");
			anx.setStartDate(data.getNocFromDate());
			anx.setGateOutDate(data.getNocValidityDate());
			anx.setLineNo("1");

			if ("noc".equals(type)) {
				anx.setInvoiceSubType("NOC");
			} else {
				anx.setInvoiceSubType("EXB");
			}

			anx.setSrlNo(highestSRLNoNew);

//			anx.setActualNoOfPackages(c.getRates());
			anx.setLocalAmt(anx.getActualNoOfPackages());
			anx.setInvoiceAmt(anx.getActualNoOfPackages());
			anx.setCommodityDescription(data.getCommodityDescription());
			anx.setDiscPercentage(zero);
			anx.setDiscValue(zero);
			anx.setmPercentage(zero);
			anx.setmAmount(zero);
			anx.setInvoiceUptoDate(data.getInvoiceUptoDate());
			anx.setInvoiceUptoWeek(data.getInvoiceUptoDate());
//			anx.setAcCode(c.getAcCode());

			anx.setProcessTransDate(data.getAssesmentDate());
			anx.setProcessId("P01107");
			anx.setPartyId(data.getPartyId());

//			anx.setGateOutDate(c.getGateoutDate());
//			anx.setStartDate(c.getGateInDate());
//			anx.setInvoiceUptoDate(c.getInvoiceDate());
//			anx.setInvoiceUptoWeek(c.getInvoiceDate());
			anx.setStatus("A");
			anx.setCreatedBy(user);
			anx.setCreatedDate(new Date());

			anx.setTaxApp(String.valueOf(data.getTaxApplicable()));
			anx.setSrvManualFlag("N");
			anx.setCriteria("CNTR");
			anx.setRangeFrom(zero);
			anx.setRangeTo(zero);
//			anx.setGateOutId(c.getGateOutId());
//			anx.setGatePassNo(c.getGatePassNo());
			anx.setRangeType("CNTR");
//			anx.setTaxId(c.getTaxId());
			anx.setExRate(zero);

			if (("Y".equals(data.getIgst())) || ("Y".equals(data.getCgst()) && "Y".equals(data.getSgst()))) {
				anx.setTaxPerc(anx.getTaxPerc());
			} else {
				anx.setTaxPerc(zero);
			}

			cfinvsrvanxprorepo.save(anx);

			return ResponseEntity.ok(true);
		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error in Add Service ContainerWise: " + e.getMessage());
		}
	}
	
	public ResponseEntity<?> getDataByAssessmentId1(String cid, String bid, String id, String billingPartyId,
			String creditType, String type) {

		List<Object[]> result = new ArrayList<>();

		if ("noc".equals(type)) {
			result = assessmentsheetprorepo.getAssessmentDataForNoc(cid, bid, id);

			if (result.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				result.stream().forEach(r -> {
					BigDecimal rate = new BigDecimal(String.valueOf(r[70]));
					BigDecimal taxPerc = new BigDecimal(String.valueOf(r[73]));

					System.out.println("TaxPerc " + String.valueOf(r[69]) + " " + taxPerc);
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

				

				Party p = partyRepository.getDataById(cid, bid, billingPartyId);

				Map<String, Object> finalResult = new HashMap<>();
				finalResult.put("result", result);
				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

				if (p != null) {
					if ("P".equals(creditType)) {
						Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, billingPartyId, "AD");

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
		}
		else {
			result = assessmentsheetprorepo.getAssessmentDataForExbond(cid, bid, id);
			
			if (result.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				result.stream().forEach(r -> {
					BigDecimal rate = new BigDecimal(String.valueOf(r[70]));
					BigDecimal taxPerc = new BigDecimal(String.valueOf(r[73]));

					System.out.println("TaxPerc " + String.valueOf(r[69]) + " " + taxPerc);
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

				

				Party p = partyRepository.getDataById(cid, bid, billingPartyId);

				Map<String, Object> finalResult = new HashMap<>();
				finalResult.put("result", result);
				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

				if (p != null) {
					if ("P".equals(creditType)) {
						Object advanceData = finTransrepo.advanceReceiptBeforeSaveSearch(cid, bid, billingPartyId, "AD");

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
		}

	}
}
