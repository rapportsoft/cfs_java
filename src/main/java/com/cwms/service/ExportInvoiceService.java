
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
import java.util.Arrays;
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
import com.cwms.entities.AssessmentSheet;
import com.cwms.entities.Branch;
import com.cwms.entities.CFSDay;
import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.Cfinvsrvanx;
import com.cwms.entities.Cfinvsrvanxback;
import com.cwms.entities.Cfinvsrvdtl;
import com.cwms.entities.ExportMovement;
import com.cwms.entities.FinTrans;
import com.cwms.entities.InvCreditTrack;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.entities.PaymentReceiptDTO;
import com.cwms.entities.PdaDtl;
import com.cwms.entities.Pdahdr;
import com.cwms.entities.SSRDtl;
import com.cwms.helper.ExportContainerAssessmentData;
import com.cwms.helper.ExportContainerAssessmentData.ssrDetail;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.AssessmentSheetRepo;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CFSDayRepo;
import com.cwms.repository.CFSRepositary;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.CfinvsrvRepo;
import com.cwms.repository.CfinvsrvanxRepo;
import com.cwms.repository.CfinvsrvanxbackRepo;
import com.cwms.repository.CfinvsrvdtlRepo;
import com.cwms.repository.ExportInvoiceRepo;
import com.cwms.repository.FintransRepo;
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
public class ExportInvoiceService {

	@Autowired
	private ExportInvoiceRepo exportInvoiceRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProcessNextIdService processService;

	@Autowired
	private HelperMethods helperMethods;

	@Autowired
	private PartyAddressRepository partyRepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfinvsrvanxRepo cfinvsrvanxrepo;

	@Autowired
	private AssessmentSheetRepo assessmentsheetrepo;

	@Autowired
	private CFSTarrifServiceRepository cfstariffservicerepo;

	@Autowired
	private CFSDayRepo cfsdayrepo;

	@Autowired
	private SSRDtlRepository ssrRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private CfinvsrvanxbackRepo invsrvbackrepo;

	@Autowired
	private CfinvsrvdtlRepo invsrvdtlRepo;

	@Autowired
	private CfinvsrvRepo invsrvRepo;

	@Autowired
	private FintransRepo finTransrepo;
	
	@Autowired
	private InvCreditTrackRepo invcredittrackrepo;

	@Autowired
	private PdaDtlRepo pdadtlrepo;

	@Autowired
	private PadHdrRepo pdahdrrepo;
	
	@Autowired
	private PartyRepository partyRepository;

	
	
	
	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveExportAssesmentBackToTown(String cid, String bid, String user, Map<String, Object> data, String invoiceType)
			throws JsonMappingException, JsonProcessingException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assesmentSheet")),
					AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<ExportContainerAssessmentData> containerData = mapper.readValue(
					mapper.writeValueAsString(data.get("containerData")),
					new TypeReference<List<ExportContainerAssessmentData>>() {
					});

			System.out.println(containerData);
			if (containerData.isEmpty()) {
				return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
			}

			if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {
				
				int sr = 1;

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P01143", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("EBTC%06d", nextNumericNextID1);
		
				String mappingType = "EXPBK";				
				
				
				int i = 1;
				AtomicReference<BigDecimal> anxSrNo = new AtomicReference<>(new BigDecimal(1));
//				long cargoFreeDays = 0l;
//				BigDecimal freeDays = BigDecimal.ZERO;
				
				for (ExportContainerAssessmentData con : containerData) {
					List<ExportContainerAssessmentData> finalConData = new ArrayList<>();
					
					System.out.println("con " + i + " " + con.getContainerNo());

					// Service Mapping
					List<String> serviceMappingData = new ArrayList<>();

					System.out.println(" -----1");				
					
					

					if ("SINGLE".equalsIgnoreCase(assessment.getInvoiceCategory())) {
						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceAll(cid, bid,
								con.getProfitcentreId(), mappingType);
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceByType(cid, bid,
								con.getProfitcentreId(), mappingType, group);
					}

					System.out.println(" -----2");

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

					System.out.println(" -----3");
					String tariffNo = exportInvoiceRepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList,
							fwIdList, assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
							assessment.getOnAccountOf());

					con.setUpTariffNo(tariffNo);
					System.out.println(" -----4");

					// Find default service
					List<String> defaultService = exportInvoiceRepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

					System.out.println(" -----5");
					if (!defaultService.isEmpty()) {
						combinedService.addAll(defaultService);
					}

					// SSR service
					List<SSRDtl> ssrServices = new ArrayList<>();

					if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

						ssrServices = exportInvoiceRepo.getServiceIdCargoBackToTown(cid, bid, con.getSsrTransId(), con.getSbNo(), con.getSbTransId(), con.getBackToTownTransId());

						if (!ssrServices.isEmpty()) {
							List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId)
									.collect(Collectors.toList());
							combinedService.addAll(ssrServiceId);
						}
					}

					List<SSRDtl> finalSsrServices = ssrServices;

					List<String> finalServices = new ArrayList<>(combinedService);

					List<Object[]> finalPricing = new ArrayList<>();
					List<String> notInPricing = new ArrayList<>();
					
					Date assData = assessment.getAssesmentDate();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String formattedDate = formatter.format(assData);
					Date parsedDate = formatter.parse(formattedDate);

					List<Object[]> pricingData = exportInvoiceRepo.getServiceRateForCarting(cid, bid, parsedDate, finalServices, con.getUpTariffNo());

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
						
						List<Object[]> pricingData1 = exportInvoiceRepo.getServiceRateForCarting(cid, bid, parsedDate, notInPricing, "CFS1000001");

						if (!pricingData1.isEmpty()) {
							List<Object[]> remainingPricing = pricingData1.stream()
									.filter(data1 -> tempPricing.contains(data1[0].toString()))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						}
					}

					

//						STARTING LOOP FOR ALL DISTINCT SERVICE IDS
					finalPricing.stream().forEach(f -> {
						ExportContainerAssessmentData tempAss = new ExportContainerAssessmentData();
						CFSDay day = cfsdayrepo.getData(cid, bid);

// STARTING IF SERVICE ID IS FROM SSR
						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

// IF SSR DATA NOT EMPTY START
							if (!finalSsrServices.isEmpty()) {

								SSRDtl rate1 = finalSsrServices.stream()
										.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
										.orElse(null);

//									IF SSRDTL RATE EXIST
								if (rate1 != null) {

									try {
										tempAss = (ExportContainerAssessmentData) con.clone();

										Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
												day.getEndTime());

										tempAss.setInvoiceDate(invDate);
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											
										tempAss.setmAmount(f[12] != null ? new BigDecimal(String.valueOf(f[12]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
										e.printStackTrace();
									}
								}

//									IF SSRDTL RATE DOES NOT EXIST									
								else {
									try {
//											IF SLAB
										String storageType = getFirstServiceGroup(
												f[19] != null ? String.valueOf(f[19]) : "");
										String serviceGrp = String.valueOf(f[14]);
//											IF STORAGE TYPE IN CRG STARTING....
										System.out.println("storageType " + storageType + " serviceGrp " + serviceGrp);

//											IF STORAGE TYPE NOT CRG STARTING

											System.out.println("----------7");
											tempAss = (ExportContainerAssessmentData) con.clone();
											
											if ("SA".equals(String.valueOf(f[8]))) {
												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//													
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setTaxId(String.valueOf(f[11]));
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

												

												List<Object[]> rangeValues = exportInvoiceRepo.getDataByServiceIdCarting(cid, bid,
														String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
														String.valueOf(f[8]));

												if (!rangeValues.isEmpty()) {

													String unit = String.valueOf(f[1]);

													AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
															BigDecimal.ZERO);

													LocalDateTime gateInDateTime = null;
													LocalDateTime destuffDateTime = null;
													
													BigDecimal areaReleased = con.getAreaUsed();
													BigDecimal grossWeight = con.getGrossWeight();
 													
													if ("MTY".equals(storageType)) {
														gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getGateInDate()),
																day.getStartTime());

														destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getStuffTallyDate()),
																day.getEndTime());

													} else if ("CON".equals(storageType)) {
														gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getStuffTallyDate()),
																day.getStartTime());

														destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());

													} else {
														gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getCartingDate()),
																day.getStartTime());

														destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());
													}

													System.out.println(" gateInDateTime " + gateInDateTime
															+ " destuffDateTime " + destuffDateTime);

													BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
															.filter(r -> ((BigDecimal) r[8])
																	.compareTo(BigDecimal.ZERO) == 0) // Filter where
																										// rate is 0
															.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																							// BigDecimal
															.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
															.orElse(BigDecimal.ZERO); // Default value if no match found

													if ("DAY".equals(unit)) {

														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														System.out.println(" ChronoUnit.HOURS "
																+ ChronoUnit.HOURS.between(gateInDateTime,
																		destuffDateTime)
																+ " + daysBetween1 + " + daysBetween + " "
																+ daysBetween);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														final long daysBetween1 = daysBetween;
														System.out.println(" daysBetween1 " + daysBetween1
																+ " daysBetween " + daysBetween);

														tempAss.setExecutionUnit(String.valueOf(daysBetween1));
														tempAss.setExecutionUnit1(String.valueOf(areaReleased));
														tempAss.setFreeDays(freeDaysForExportContainerStorage);

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
																serviceRate.set(rate);
															}
															// Calculate days in the current slab
															long daysInSlab = Math.min(toRange, daysBetween1)
																	- Math.max(fromRange, 1) + 1;

															daysInSlab = Math.max(daysInSlab, 0);
															return rate.multiply(new BigDecimal(daysInSlab));
														}).reduce(BigDecimal.ZERO, BigDecimal::add);

														
														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
														
														if ("SM".equals(String.valueOf(f[7]))) {
															totalRate = (totalRate.multiply(areaReleased))
																	.setScale(3, RoundingMode.HALF_UP);
														}

														if ("MTON".equals(String.valueOf(f[7]))) {
															BigDecimal newAreaReleased = grossWeight
										                              .divide(new BigDecimal(1000))
										                              .setScale(3, RoundingMode.HALF_UP);
															
															totalRate = (totalRate.multiply(newAreaReleased))
																	.setScale(3, RoundingMode.HALF_UP);
														}
														
														tempAss.setServiceRate(serviceRate.get());
														tempAss.setRates(totalRate);

													}

													if ("WEEK".equals(unit)) {

														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														System.out.println(" ChronoUnit.HOURS "
																+ ChronoUnit.HOURS.between(gateInDateTime,
																		destuffDateTime)
																+ " + daysBetween1 + " + daysBetween + " "
																+ daysBetween);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														long freeDaysOld = freeDaysForExportContainerStorage != null
																? freeDaysForExportContainerStorage.longValue()
																: 0L;
														;

														tempAss.setFreeDays(
																new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));

														final long daysBetween1 = daysBetween;
														long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

														tempAss.setExecutionUnit(String.valueOf(weeksBetween));
														tempAss.setExecutionUnit1(String.valueOf(areaReleased));

														BigDecimal totalRate = rangeValues.stream().filter(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();
															// Include slabs that overlap with weeksBetween
															return weeksBetween >= fromRange;
														}).map(r -> {
															int fromRange = ((BigDecimal) r[6]).intValue();
															int toRange = ((BigDecimal) r[7]).intValue();
															BigDecimal rate = (BigDecimal) r[8];
															if (weeksBetween >= fromRange && weeksBetween <= toRange) {
																serviceRate.set(rate); // Set the rate for the
																						// matching
																						// slab
															}

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
														System.out
																.println("totalRate " + totalRate + " " + weeksBetween);
														
														if ("SM".equals(String.valueOf(f[7]))) {
															totalRate = (totalRate.multiply(grossWeight))
																	.setScale(3, RoundingMode.HALF_UP);
														}

														if ("MTON".equals(String.valueOf(f[7]))) {
															BigDecimal newAreaReleased = areaReleased
										                              .divide(new BigDecimal(1000))
										                              .setScale(3, RoundingMode.HALF_UP);
															
															totalRate = (totalRate.multiply(newAreaReleased))
																	.setScale(3, RoundingMode.HALF_UP);
														}


														tempAss.setRates(totalRate);
														tempAss.setServiceRate(serviceRate.get());
													}

												}

											}

//											ELSE IF RANGE
										
											
											
											
											
											
											
											
											else if ("RA".equals(String.valueOf(f[8]))) {
												System.out.println("----------8");

												String unit = String.valueOf(f[1]);
												BigDecimal executionUnit = BigDecimal.ZERO;
												
												if ("MTON".equals(unit)) {
//														String serviceGrp = String.valueOf(f[18]);

													if ("H".equals(serviceGrp)) {
//																												
														String grossWtStr = String.valueOf(con.getGrossWeight()).trim(); // Remove spaces
														 BigDecimal cargoWt = BigDecimal.ZERO;
														
														// Check if the value is numeric before converting
														if (grossWtStr != null && grossWtStr.matches("-?\\d+(\\.\\d+)?")) { 
														     cargoWt = new BigDecimal(grossWtStr)
														                              .divide(new BigDecimal(1000))
														                              .setScale(3, RoundingMode.HALF_UP);
														} else {
														    cargoWt = BigDecimal.ZERO; // Default value or handle appropriately
														    System.out.println("Invalid gross weight: " + grossWtStr);
														}

														

														executionUnit = cargoWt;

														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else {

//																												
														BigDecimal cargoWeight = con.getGrossWeight();
														BigDecimal cargoWt = (cargoWeight != null) 
														    ? cargoWeight.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP) 
														    : BigDecimal.ZERO; // Default to zero if null


														executionUnit = cargoWt;
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");

													}
													Object rangeValue = exportInvoiceRepo.getRangeDataByServiceIdCarting(cid, bid,
															String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
															executionUnit);

													if (rangeValue != null) {
														Object[] finalRangeValue = (Object[]) rangeValue;
														tempAss.setServiceGroup(
																f[14] != null ? String.valueOf(f[14]) : "");
														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
														tempAss.setServiceName(
																f[18] != null ? String.valueOf(f[18]) : "");
														tempAss.setServiceUnit(
																f[1] != null ? String.valueOf(f[1]) : "");
														tempAss.setServiceUnit1(
																f[7] != null ? String.valueOf(f[7]) : "");
														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
														tempAss.setRangeFrom(
																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																		: BigDecimal.ZERO);
														tempAss.setRangeTo(
																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																		: BigDecimal.ZERO);
														tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
														tempAss.setRates(totalRate);
														tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
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
//															String serviceGrp = String.valueOf(f[18]);
														if ("H".equals(serviceGrp)) {

//																CARGO WEIGHT												

															BigDecimal cargoWt = new BigDecimal(
																	String.valueOf(con.getGrossWeight()));

															executionUnit = cargoWt;

															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														} else {
															BigDecimal cargoWt = con.getGrossWeight();

															executionUnit = cargoWt;
															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														}
													}

													Object rangeValue = exportInvoiceRepo.getRangeDataByServiceIdCarting(cid, bid,
															String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
															executionUnit);

													if (rangeValue != null) {
														Object[] finalRangeValue = (Object[]) rangeValue;
														tempAss.setServiceGroup(
																f[14] != null ? String.valueOf(f[14]) : "");
														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
														tempAss.setServiceName(
																f[18] != null ? String.valueOf(f[18]) : "");
														tempAss.setServiceUnit(
																f[1] != null ? String.valueOf(f[1]) : "");
														tempAss.setServiceUnit1(
																f[7] != null ? String.valueOf(f[7]) : "");
														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
														tempAss.setRangeFrom(
																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																		: BigDecimal.ZERO);
														tempAss.setRangeTo(
																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																		: BigDecimal.ZERO);
														tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
														tempAss.setRates(totalRate);
													}

												} else {
													tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
													tempAss.setRates(totalRate);
												}

											}
//												ELSE PLAIN
											
											
											
											
											
											else {
												System.out.println("----------9");
												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
												tempAss.setContainerStatus(con.getContainerStatus());
												tempAss.setGateOutId(con.getGateOutId());
												tempAss.setGatePassNo(con.getGatePassNo());
												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
														? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
												tempAss.setExecutionUnit(String.valueOf(1));
												tempAss.setExecutionUnit1("");
												tempAss.setTaxId(String.valueOf(f[11]));
												
												
												String unit = String.valueOf(f[1]);
												BigDecimal executionUnit = BigDecimal.ONE;
												
												if ("MTON".equals(unit)) {

													if ("H".equals(serviceGrp)) {
														BigDecimal cargoWt = new BigDecimal(
																String.valueOf(con.getGrossWeight()))
																.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

														executionUnit = cargoWt;

														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else {
														BigDecimal cargoWt = con.getGrossWeight().divide(new BigDecimal(1000))
																.setScale(3, RoundingMode.HALF_UP);
													
														executionUnit = cargoWt;
														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													}													
												}
												
												
												BigDecimal rate =  new BigDecimal(String.valueOf(f[2]));
												
												BigDecimal multiply = rate.multiply(executionUnit);
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												
												tempAss.setExRate(rate);
												BigDecimal totalRate = multiply;						
												
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

												tempAss.setServiceRate(totalRate);
												tempAss.setRates(totalRate);
											}
											
											
											
											
//											IF STORAGE TYPE NOT IN CONT, CRG AND EMPTY ENDING
										finalConData.add(tempAss);

									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							// IF SSR DATA NOT EMPTY END
						}
						// ENDING IF SERVICE ID IS FROM SSR

//	STARTING IF SERVICE ID IS NOT FROM SSR		
						else {
							
							try {
//									IF SLAB
								String storageType = getFirstServiceGroup(
										f[19] != null ? String.valueOf(f[19]) : "");
								String serviceGrp = String.valueOf(f[14]);
//									IF STORAGE TYPE IN CRG STARTING....
								System.out.println("storageType " + storageType + " serviceGrp " + serviceGrp);

//									IF STORAGE TYPE NOT CRG STARTING

									System.out.println("----------7");
									tempAss = (ExportContainerAssessmentData) con.clone();
									
									if ("SA".equals(String.valueOf(f[8]))) {
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//											
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(
												f[15] != null ? new BigDecimal(String.valueOf(f[15]))
														: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
												? BigDecimal.ZERO
												: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										

										List<Object[]> rangeValues = exportInvoiceRepo.getDataByServiceIdCarting(cid, bid,
												String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												String.valueOf(f[8]));

										if (!rangeValues.isEmpty()) {

											String unit = String.valueOf(f[1]);

											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
													BigDecimal.ZERO);

											LocalDateTime gateInDateTime = null;
											LocalDateTime destuffDateTime = null;
											
											BigDecimal areaReleased = con.getAreaUsed();
											BigDecimal grossWeight = con.getGrossWeight();
												
											if ("MTY".equals(storageType)) {
												gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(con.getGateInDate()),
														day.getStartTime());

												destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(con.getStuffTallyDate()),
														day.getEndTime());

											} else if ("CON".equals(storageType)) {
												gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(con.getStuffTallyDate()),
														day.getStartTime());

												destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(con.getInvoiceDate()),
														day.getEndTime());

											} else {
												gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(con.getCartingDate()),
														day.getStartTime());

												destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(con.getInvoiceDate()),
														day.getEndTime());
											}

											System.out.println(" gateInDateTime " + gateInDateTime
													+ " destuffDateTime " + destuffDateTime);

											BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
													.filter(r -> ((BigDecimal) r[8])
															.compareTo(BigDecimal.ZERO) == 0) // Filter where
																								// rate is 0
													.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																					// BigDecimal
													.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
													.orElse(BigDecimal.ZERO); // Default value if no match found

											if ("DAY".equals(unit)) {

												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												System.out.println(" ChronoUnit.HOURS "
														+ ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime)
														+ " + daysBetween1 + " + daysBetween + " "
														+ daysBetween);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

												final long daysBetween1 = daysBetween;
												System.out.println(" daysBetween1 " + daysBetween1
														+ " daysBetween " + daysBetween);

												tempAss.setExecutionUnit(String.valueOf(daysBetween1));
												tempAss.setExecutionUnit1(String.valueOf(areaReleased));
												tempAss.setFreeDays(freeDaysForExportContainerStorage);

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
														serviceRate.set(rate);
													}
													// Calculate days in the current slab
													long daysInSlab = Math.min(toRange, daysBetween1)
															- Math.max(fromRange, 1) + 1;

													daysInSlab = Math.max(daysInSlab, 0);
													return rate.multiply(new BigDecimal(daysInSlab));
												}).reduce(BigDecimal.ZERO, BigDecimal::add);

												
												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
												
												if ("SM".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(areaReleased))
															.setScale(3, RoundingMode.HALF_UP);
												}

												if ("MTON".equals(String.valueOf(f[7]))) {
													BigDecimal newAreaReleased = grossWeight
								                              .divide(new BigDecimal(1000))
								                              .setScale(3, RoundingMode.HALF_UP);
													
													totalRate = (totalRate.multiply(newAreaReleased))
															.setScale(3, RoundingMode.HALF_UP);
												}
												
												tempAss.setServiceRate(serviceRate.get());
												tempAss.setRates(totalRate);

											}

											if ("WEEK".equals(unit)) {

												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												System.out.println(" ChronoUnit.HOURS "
														+ ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime)
														+ " + daysBetween1 + " + daysBetween + " "
														+ daysBetween);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

												long freeDaysOld = freeDaysForExportContainerStorage != null
														? freeDaysForExportContainerStorage.longValue()
														: 0L;
												;

												tempAss.setFreeDays(
														new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));

												final long daysBetween1 = daysBetween;
												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

												tempAss.setExecutionUnit(String.valueOf(weeksBetween));
												tempAss.setExecutionUnit1(String.valueOf(areaReleased));

												BigDecimal totalRate = rangeValues.stream().filter(r -> {
													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();
													// Include slabs that overlap with weeksBetween
													return weeksBetween >= fromRange;
												}).map(r -> {
													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();
													BigDecimal rate = (BigDecimal) r[8];
													if (weeksBetween >= fromRange && weeksBetween <= toRange) {
														serviceRate.set(rate); // Set the rate for the
																				// matching
																				// slab
													}

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
												System.out
														.println("totalRate " + totalRate + " " + weeksBetween);
												
												if ("SM".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(areaReleased))
															.setScale(3, RoundingMode.HALF_UP);
												}

												if ("MTON".equals(String.valueOf(f[7]))) {
													BigDecimal newAreaReleased = grossWeight
								                              .divide(new BigDecimal(1000))
								                              .setScale(3, RoundingMode.HALF_UP);
													
													totalRate = (totalRate.multiply(newAreaReleased))
															.setScale(3, RoundingMode.HALF_UP);
												}


												tempAss.setRates(totalRate);
												tempAss.setServiceRate(serviceRate.get());
											}

										}

									}

//									ELSE IF RANGE
								
									
									else if ("RA".equals(String.valueOf(f[8]))) {
										System.out.println("----------8");

										String unit = String.valueOf(f[1]);
										BigDecimal executionUnit = BigDecimal.ZERO;
										
										if ("MTON".equals(unit)) {
//												String serviceGrp = String.valueOf(f[18]);

											if ("H".equals(serviceGrp)) {
//																										
												String grossWtStr = String.valueOf(con.getGrossWeight()).trim(); // Remove spaces
												 BigDecimal cargoWt = BigDecimal.ZERO;
												
												// Check if the value is numeric before converting
												if (grossWtStr != null && grossWtStr.matches("-?\\d+(\\.\\d+)?")) { 
												     cargoWt = new BigDecimal(grossWtStr)
												                              .divide(new BigDecimal(1000))
												                              .setScale(3, RoundingMode.HALF_UP);
												} else {
												    cargoWt = BigDecimal.ZERO; // Default value or handle appropriately
												    System.out.println("Invalid gross weight: " + grossWtStr);
												}

												

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {

//																										
												BigDecimal cargoWeight = con.getGrossWeight();
												BigDecimal cargoWt = (cargoWeight != null) 
												    ? cargoWeight.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP) 
												    : BigDecimal.ZERO; // Default to zero if null


												executionUnit = cargoWt;
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");

											}
											Object rangeValue = exportInvoiceRepo.getRangeDataByServiceIdCarting(cid, bid,
													String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
													executionUnit);

											if (rangeValue != null) {
												Object[] finalRangeValue = (Object[]) rangeValue;
												tempAss.setServiceGroup(
														f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(
														f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(
														f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(
														f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(
														f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
												tempAss.setRates(totalRate);
												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
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
//													String serviceGrp = String.valueOf(f[18]);
												if ("H".equals(serviceGrp)) {

//														CARGO WEIGHT												

													BigDecimal cargoWt = new BigDecimal(
															String.valueOf(con.getGrossWeight()));

													executionUnit = cargoWt;

													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else {
													BigDecimal cargoWt = con.getGrossWeight();

													executionUnit = cargoWt;
													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												}
											}

											Object rangeValue = exportInvoiceRepo.getRangeDataByServiceIdCarting(cid, bid,
													String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
													executionUnit);

											if (rangeValue != null) {
												Object[] finalRangeValue = (Object[]) rangeValue;
												tempAss.setServiceGroup(
														f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(
														f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(
														f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(
														f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(
														f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
												tempAss.setRates(totalRate);
											}

										} else {
											tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(
													f[15] != null ? new BigDecimal(String.valueOf(f[15]))
															: BigDecimal.ZERO);
											tempAss.setRangeTo(
													f[16] != null ? new BigDecimal(String.valueOf(f[16]))
															: BigDecimal.ZERO);
											tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
											tempAss.setRates(totalRate);
										}

									}
//										ELSE PLAIN			
									
									
									
									
									else {
										System.out.println("----------9");
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(
												f[15] != null ? new BigDecimal(String.valueOf(f[15]))
														: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
												? BigDecimal.ZERO
												: new BigDecimal(String.valueOf(f[12])));
										tempAss.setExecutionUnit(String.valueOf(1));
										tempAss.setExecutionUnit1("");
										tempAss.setTaxId(String.valueOf(f[11]));
										
										
										String unit = String.valueOf(f[1]);
										BigDecimal executionUnit = BigDecimal.ONE;
										
										if ("MTON".equals(unit)) {

											if ("H".equals(serviceGrp)) {
												BigDecimal cargoWt = new BigDecimal(
														String.valueOf(con.getGrossWeight()))
														.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getGrossWeight().divide(new BigDecimal(1000))
														.setScale(3, RoundingMode.HALF_UP);
											
												executionUnit = cargoWt;
												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											}													
										}
										
										
										BigDecimal rate =  new BigDecimal(String.valueOf(f[2]));
										
										BigDecimal multiply = rate.multiply(executionUnit);
										tempAss.setExecutionUnit(String.valueOf(executionUnit));
										
										tempAss.setExRate(rate);
										BigDecimal totalRate = multiply;						
										
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));

										tempAss.setServiceRate(totalRate);
										tempAss.setRates(totalRate);
									}
									
									
									
//									IF STORAGE TYPE NOT IN CONT, CRG AND EMPTY ENDING
								finalConData.add(tempAss);

							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
							
								
						}
						
//							ENDING IF SERVICE ID IS NOT FROM SSR

					});
					
//						ENDING LOOP FOR ALL DISTINCT SERVICE IDS
										
					
							for(ExportContainerAssessmentData fnewAnxData : finalConData)
							{
								
								System.out.println("fnewAnxData " + fnewAnxData.getContainerNo() + " " + fnewAnxData.getServiceId() + " " + fnewAnxData.getRates());
							}
									
					
					List<ExportContainerAssessmentData> newAnxData = finalConData.stream()
						    .map(item -> {
						        try {
						            return (ExportContainerAssessmentData) item.clone(); // Clone first
						        } catch (CloneNotSupportedException e) {
						            e.printStackTrace();
						            return null;
						        }
						    })
						    .filter(Objects::nonNull)
						    .filter(item -> Objects.equals(item.getContainerNo(), con.getContainerNo())) // Safe comparison
						    .collect(Collectors.toList());

					for(ExportContainerAssessmentData f : newAnxData)
					{
						
						System.out.println(" newAnxData " + f.getContainerNo() + " " + f.getServiceId() + " " + f.getRates());
					}
					

					System.out.println("Here Before ---> ENDING LOOP FOR ALL DISTINCT SERVICE IDS");

					AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();

					AssessmentSheet newAss = tempAssessment;

					newAss.setCompanyId(cid);
					newAss.setBranchId(bid);
					newAss.setAssesmentLineNo(String.valueOf(sr));
					newAss.setStatus('A');
					newAss.setCreatedBy(user);
					newAss.setCreatedDate(new Date());
					newAss.setApprovedBy(user);
					newAss.setApprovedDate(new Date());
					newAss.setAssesmentDate(new Date());
					newAss.setTransType("Export Cargo");
					newAss.setProfitcentreId(con.getProfitcentreId());

					newAss.setAssesmentId(HoldNextIdD1);

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

						Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

						String billingId = "EXP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
								: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
										: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
												: assessment.getOthPartyId();

						String billingAdd = "EXP".equals(assessment.getBillingParty())
								? String.valueOf(assessment.getImpSrNo())
								: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
										: "FWR".equals(assessment.getBillingParty())
												? String.valueOf(assessment.getAccSrNo())
												: assessment.getOthSrNo();

						PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

						newAss.setPartyId(billingId);
						newAss.setPartySrNo(new BigDecimal(billingAdd));

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

					System.out.println("Here Before ---> List<ExportContainerAssessmentData> filteredData");

					List<ExportContainerAssessmentData> filteredData = newAnxData.stream()
							.filter(c -> "H".equals(c.getServiceGroup())).collect(Collectors.toList());


					BigDecimal totalRates = filteredData.stream().map(ExportContainerAssessmentData::getRates)
							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

					System.out.println("totalRates : " + totalRates);

					List<ExportContainerAssessmentData> filteredData1 = newAnxData.stream()
							.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());
					
					

					BigDecimal totalRates1 = filteredData1.stream().map(ExportContainerAssessmentData::getRates)
							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

					BigDecimal zero = BigDecimal.ZERO;

					newAss.setContainerNo(con.getContainerNo());
					newAss.setContainerSize(con.getContainerSize());
					newAss.setContainerType(con.getContainerType());
					newAss.setGateInDate(con.getGateInDate());
					newAss.setGateOutDate(con.getGateoutDate() == null ? null : con.getGateoutDate());
					newAss.setGrossWeight(con.getGrossWeight());
					newAss.setGateOutType(con.getGateOutType());
					newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
					newAss.setTypeOfContainer(con.getTypeOfContainer());
					newAss.setScanerType(con.getScannerType());
					newAss.setExaminedPercentage(zero);
					newAss.setWeighmentFlag("N");
					newAss.setCargoWeight(con.getCargoWeight());
					newAss.setDestuffDate(con.getGateInDate() == null ? null : con.getGateInDate());
					newAss.setCalculateInvoice('N');
					newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
					newAss.setContainerHandlingAmt(totalRates);
					newAss.setContainerStorageAmt(totalRates1);
					newAss.setInvoiceCategory(assessment.getInvoiceCategory());
					newAss.setInvType("First");
					newAss.setSa(tempAssessment.getSa());
					newAss.setSl(tempAssessment.getSl());
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrServiceId(con.getSsrTransId());
					newAss.setStuffTallyDate(con.getStuffTallyDate());
					newAss.setGateOutDate(con.getGateOutDate());
					newAss.setMovReqDate(con.getMovementDate());
					newAss.setMinCartingTransDate(con.getCartingDate());
					newAss.setCartingTransId(con.getCartingTransId());
					newAss.setSbWeight(con.getCargoWeight());
					newAss.setAreaUsed(con.getAreaUsed());				
					newAss.setInvoiceNo(null);
					
					
					assessmentsheetrepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P01143", HoldNextIdD1, "2024");

					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);

					System.out.println("Here Before ---> finalConData.stream().forEach(c -> {");

					newAnxData.stream().forEach(c -> {						
						

							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
								Cfinvsrvanx anx = new Cfinvsrvanx();
								anx.setCompanyId(cid);
								anx.setBranchId(bid);
								anx.setErpDocRefNo(newAss.getSbTransId());
								anx.setProcessTransId(HoldNextIdD1);
								anx.setServiceId(c.getServiceId());
								anx.setSrlNo(anxSrNo.get());
								anx.setDocRefNo(newAss.getSbNo());
								anx.setProfitcentreId(newAss.getProfitcentreId());
								anx.setInvoiceType("EXP");
								anx.setInvoiceSubType("BTT");
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
								anx.setProcessId("P01103");
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
								anx.setFreeDays(c.getFreeDays());

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
								
								System.out.println("newAss.getAssesmentLineNo() " + newAss.getAssesmentLineNo());
								
								anx.setLineNo(newAss.getAssesmentLineNo());

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

								cfinvsrvanxrepo.save(anx);

								anxSrNo.set(anxSrNo.get().add(new BigDecimal(1)));
							}
						
					}					
							
							);

					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

					int updateExpBackToTownInvoiceSave = exportInvoiceRepo.updateExpBackToTownInvoiceSave(
																		cid, bid, HoldNextIdD1, con.getBackToTownTransId());

					System.out.println("updateExpBackToTownInvoiceSave : " + updateExpBackToTownInvoiceSave);

					assessment.setAssesmentId(HoldNextIdD1);

					sr++;

				}
			} else {

				int updateAssessmentContainerEdit = exportInvoiceRepo.updateAssessmentContainerEdit(cid, bid,
						assessment.getProfitcentreId(), assessment.getAssesmentId(), assessment.getComments(),
						assessment.getIntComments());

				System.out.println("updateAssessmentContainerEdit : " + updateAssessmentContainerEdit);

			}

			System.out.println("Before ------ selectedExportAssesmentSheet ");
			AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
					assessment.getProfitcentreId(), assessment.getAssesmentId());

			
			List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
					.getSelectedExportAssesmentSheetBackToTown(cid, bid, assessment.getProfitcentreId(),
							assessment.getAssesmentId());

			System.out.println("Before ------ cargoStorageServiceId");

			
			
			if (selectedExportAssesmentSheetContainerData.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				selectedExportAssesmentSheetContainerData.stream().forEach(r -> {
					
//					BigDecimal rate = new BigDecimal(String.valueOf(r.getRates()));
//					BigDecimal taxPerc = new BigDecimal(String.valueOf(r.getTaxPerc()));
					BigDecimal rate = Optional.ofNullable(r.getRates())
	                         .map(Object::toString)
	                         .filter(s -> s.matches("-?\\d+(\\.\\d+)?"))
	                         .map(BigDecimal::new)
	                         .orElse(BigDecimal.ZERO);

	BigDecimal taxPerc = Optional.ofNullable(r.getTaxPerc())
	                             .map(Object::toString)
	                             .filter(s -> s.matches("-?\\d+(\\.\\d+)?"))
	                             .map(BigDecimal::new)
	                             .orElse(BigDecimal.ZERO);
					

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

				} else if ("EXP".equals(assessment.getBillingParty())) {
					pId = assessment.getImporterId();
				} else if ("FWR".equals(assessment.getBillingParty())) {
					pId = assessment.getOnAccountOf();
				} else if ("OTH".equals(assessment.getBillingParty())) {
					pId = assessment.getOthPartyId();
				}

				Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);

				Map<String, Object> finalResult = new HashMap<>();
				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

				if (p != null) {
					
					
					if (assessment.getCreditType() == 'P') {
						BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, pId, "AD");

						finalResult.put("advanceData", advanceData);
						selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
					}

					BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
					BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

					BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
									
					
					selectedExportAssesmentSheet.setCreditAllowed(credit1);
					selectedExportAssesmentSheet.setUsedCredit(credit2);		
					selectedExportAssesmentSheet.setPendingCredit(allowedValue);
					
					
					
					finalResult.put("tanNo", p.getTanNoId());
					finalResult.put("tdsPerc", p.getTdsPercentage());
				}

			
			finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
			finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
			}
			// return null;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	
	
	
	


//	SAVE ASSESMENT DATA EXPORT CONTAINER

	@Transactional
	public ResponseEntity<?> saveExportAssesment(String cid, String bid, String user, Map<String, Object> data, String invoiceType)
			throws JsonMappingException, JsonProcessingException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assesmentSheet")),
					AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<ExportContainerAssessmentData> containerData = mapper.readValue(
					mapper.writeValueAsString(data.get("containerData")),
					new TypeReference<List<ExportContainerAssessmentData>>() {
					});

			System.out.println(containerData);
			if (containerData.isEmpty()) {
				return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
			}

			if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {
				
				int sr = 1;

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P01103", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("EXCM%06d", nextNumericNextID1);
				
//				String movementType = assessment.getMovementType();
//				String mappingType = switch (movementType.toUpperCase()) {
//				    case "CLP" -> "EXP";
//				    case "BUFFER", "ONWH" -> "ONWH";
//				    case "PORTRN" -> "PORT";
//				    default -> "";
//				};
				
				String movementType = assessment.getMovementType();
				String mappingType = "";				
				
				if("Export Container".equalsIgnoreCase(invoiceType))
				{
				mappingType = switch (movementType.toUpperCase()) {
				case "CLP" -> "EXP";
				case "BUFFER", "ONWH" -> "ONWH";
				case "PORTRN" -> "PORT";
				default -> "";
				};
				}else if("Export Cargo".equalsIgnoreCase(invoiceType))
				{				
					mappingType = "EXPBK";
				}
				
				
				
				int i = 1;
				AtomicReference<BigDecimal> anxSrNo = new AtomicReference<>(new BigDecimal(1));
				
				for (ExportContainerAssessmentData con : containerData) {
					List<ExportContainerAssessmentData> finalConData = new ArrayList<>();
					
					System.out.println("con " + i + " " + con.getContainerNo());

					// Service Mapping
					List<String> serviceMappingData = new ArrayList<>();

					System.out.println(" -----1");				
					
					

					if ("SINGLE".equalsIgnoreCase(assessment.getInvoiceCategory())) {
						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceAll(cid, bid,
								con.getProfitcentreId(), mappingType);
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceByType(cid, bid,
								con.getProfitcentreId(), mappingType, group);
					}

					System.out.println(" -----2");

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

					System.out.println(" -----3");
					String tariffNo = exportInvoiceRepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList,
							fwIdList, assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
							assessment.getOnAccountOf());

					con.setUpTariffNo(tariffNo);
					System.out.println(" -----4");

					// Find default service
					List<String> defaultService = exportInvoiceRepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

					System.out.println(" -----5");
					if (!defaultService.isEmpty()) {
						combinedService.addAll(defaultService);
					}

					// SSR service
					List<SSRDtl> ssrServices = new ArrayList<>();

					if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

						ssrServices = exportInvoiceRepo.getServiceId(cid, bid, con.getSsrTransId(),
								con.getContainerNo());

						if (!ssrServices.isEmpty()) {
							List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId)
									.collect(Collectors.toList());
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

					Date assData = assessment.getAssesmentDate();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String formattedDate = formatter.format(assData);
					Date parsedDate = formatter.parse(formattedDate);

					List<Object[]> pricingData = exportInvoiceRepo.getServiceRate(cid, bid, parsedDate,
							con.getContainerNo(), finalServices, con.getUpTariffNo(), conSize1, conTypeOfCon1);

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
						List<String> conSize2 = new ArrayList<>();
						conSize2.add("ALL");
						conSize2.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());

						List<String> conTypeOfCon2 = new ArrayList<>();
						conTypeOfCon2.add("ALL");
						conTypeOfCon2.add(con.getTypeOfContainer());

						List<Object[]> pricingData1 = exportInvoiceRepo.getServiceRate(cid, bid, parsedDate,
								con.getContainerNo(), notInPricing, "CFS1000001", conSize2, conTypeOfCon2);

						if (!pricingData1.isEmpty()) {
							List<Object[]> remainingPricing = pricingData1.stream()
									.filter(data1 -> tempPricing.contains(data1[0].toString()))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						}
					}

					String freeDaysForExportCargoStorage = exportInvoiceRepo.getFreeDaysForExportCargoStorage(cid, bid,
							assessment.getBillingParty());

					BigDecimal freeDays = convertToBigDecimal(freeDaysForExportCargoStorage);
					final long cargoFreeDays = freeDays != null ? freeDays.longValue() : 0L;

//						STARTING LOOP FOR ALL DISTINCT SERVICE IDS
					finalPricing.stream().forEach(f -> {
						ExportContainerAssessmentData tempAss = new ExportContainerAssessmentData();
						CFSDay day = cfsdayrepo.getData(cid, bid);

// STARTING IF SERVICE ID IS FROM SSR
						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {

// IF SSR DATA NOT EMPTY START
							if (!finalSsrServices.isEmpty()) {

								SSRDtl rate1 = finalSsrServices.stream()
										.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
										.orElse(null);

//									IF SSRDTL RATE EXIST
								if (rate1 != null) {

									try {
										tempAss = (ExportContainerAssessmentData) con.clone();

										Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
												day.getEndTime());

										tempAss.setInvoiceDate(invDate);
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//											
										tempAss.setmAmount(f[12] != null ? new BigDecimal(String.valueOf(f[12]))
												: BigDecimal.ZERO);
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
										e.printStackTrace();
									}

								}

//									IF SSRDTL RATE DOES NOT EXIST									
								else {
									try {

//											IF SLAB
										String storageType = getFirstServiceGroup(
												f[19] != null ? String.valueOf(f[19]) : "");
										String serviceGrp = String.valueOf(f[14]);
//											IF STORAGE TYPE IN CRG STARTING....
										SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										System.out.println("storageType " + storageType + " serviceGrp " + serviceGrp);

										System.out.println("----------6");
										if ("C".equals(serviceGrp) && "CRG".equals(storageType)) {
											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
													BigDecimal.ZERO);

											List<Object[]> exportCartingStorageDays = exportInvoiceRepo
													.getExportCartingStorageDays(cid, bid, con.getStuffTallyId(),
															con.getContainerNo());

											for (Object[] value : exportCartingStorageDays) {
												tempAss = (ExportContainerAssessmentData) con.clone();
												tempAss.setFreeDays(freeDays);

												Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(),
														day.getStartTime(), day.getEndTime());

												tempAss.setInvoiceDate(invDate);

												String sbNo = value[0] != null ? String.valueOf(value[0]) : "";
												Date stuffTallyDate = value[1] != null
														? dateTimeFormat.parse(value[1].toString())
														: null;
												Date inGateInDate = value[2] != null
														? dateTimeFormat.parse(value[2].toString())
														: null;
												Date cartingTransDate = value[5] != null
														? dateTimeFormat.parse(value[5].toString())
														: null;

												// Retrieve weights and areas as BigDecimal
												BigDecimal areaReleased = value[3] != null
														? new BigDecimal(value[3].toString())
														: null;
												BigDecimal totalCargoWeight = value[6] != null
														? new BigDecimal(value[6].toString())
														: null;

												// Vehicle number remains as a String
												String vehicleNo = value[4] != null ? value[4].toString() : "";

												LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(cartingTransDate), day.getStartTime());

												LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(stuffTallyDate), day.getEndTime());

												if ("SA".equals(String.valueOf(f[8]))) {
													tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//												
													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
													tempAss.setContainerStatus(con.getContainerStatus());
													tempAss.setGateOutId(con.getGateOutId());
													tempAss.setGatePassNo(con.getGatePassNo());
													tempAss.setTaxPerc(
															(f[12] == null || String.valueOf(f[12]).isEmpty())
																	? BigDecimal.ZERO
																	: new BigDecimal(String.valueOf(f[12])));
													tempAss.setTaxId(String.valueOf(f[11]));
													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
													tempAss.setCargoSbNo(sbNo);

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

													List<Object[]> rangeValues = cfstariffservicerepo
															.getDataByServiceId(cid, bid, String.valueOf(f[3]),
																	String.valueOf(f[4]), String.valueOf(f[0]),
																	String.valueOf(f[8]), conSize2, conTypeOfCon2,
																	commodityType, con.getContainerSize(),
																	con.getTypeOfContainer(),
																	assessment.getCommodityCode());

													if (!rangeValues.isEmpty()) {

														String unit = String.valueOf(f[1]);

														if ("DAY".equals(unit)) {

															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween - cargoFreeDays;
															tempAss.setExecutionUnit(String.valueOf(daysBetween));
															tempAss.setExecutionUnit1(String.valueOf(areaReleased));
															tempAss.setFreeDays(freeDays);
															System.out.println("daysBetween " + daysBetween);

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

															if ("SM".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(areaReleased))
																		.setScale(3, RoundingMode.HALF_UP);
															}

															if ("MTON".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(totalCargoWeight))
																		.setScale(3, RoundingMode.HALF_UP);
															}

															tempAss.setRates(totalRate);
															tempAss.setServiceRate(serviceRate.get());

														}

														if ("WEEK".equals(unit)) {

															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																	destuffDateTime);

															if (daysBetween == 0 && ChronoUnit.HOURS
																	.between(gateInDateTime, destuffDateTime) > 0) {
																daysBetween = 1;
															}

															final long daysBetween1 = daysBetween - cargoFreeDays;

															long weeksBetween2 = (long) Math.ceil(daysBetween / 7.0);
															long weeksBetween1 = (long) Math.ceil(cargoFreeDays / 7.0);
															long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

															tempAss.setExecutionUnit(String.valueOf(weeksBetween2));
															tempAss.setExecutionUnit1(String.valueOf(areaReleased));
															tempAss.setFreeDays(new BigDecimal(weeksBetween1));

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

																if (weeksBetween >= fromRange
																		&& weeksBetween <= toRange) {
																	serviceRate.set(rate); // Set the rate for the
																							// matching
																							// slab
																}
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
																totalRate = (totalRate.multiply(areaReleased))
																		.setScale(3, RoundingMode.HALF_UP);
															}

															if ("MTON".equals(String.valueOf(f[7]))) {
																totalRate = (totalRate.multiply(totalCargoWeight))
																		.setScale(3, RoundingMode.HALF_UP);
															}

//															serviceRate.updateAndGet(existingValue -> existingValue.add(serviceRate.get()));														
															tempAss.setServiceRate(serviceRate.get());
															tempAss.setRates(totalRate);
														}

													}

												}

												finalConData.add(tempAss);

											}

										}
//											IF STORAGE TYPE CRG ENDING										

//											IF STORAGE TYPE NOT CRG STARTING

										else {
											System.out.println("----------7");
											tempAss = (ExportContainerAssessmentData) con.clone();
											
											if ("SA".equals(String.valueOf(f[8]))) {
												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//													
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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

													String unit = String.valueOf(f[1]);

													AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
															BigDecimal.ZERO);

													LocalDateTime gateInDateTime = null;
													LocalDateTime destuffDateTime = null;

													if ("MTY".equals(storageType)) {
														gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getGateInDate()),
																day.getStartTime());

														destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getStuffTallyDate()),
																day.getEndTime());

													} else if ("CON".equals(storageType)) {
														gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getStuffTallyDate()),
																day.getStartTime());

														destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());

													} else {
														gateInDateTime = adjustToCustomStartOfDay(
																convertToLocalDateTime(con.getGateInDate()),
																day.getStartTime());

														destuffDateTime = adjustToCustomEndOfDay(
																convertToLocalDateTime(con.getInvoiceDate()),
																day.getEndTime());
													}

													System.out.println(" gateInDateTime " + gateInDateTime
															+ " destuffDateTime " + destuffDateTime);

													BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
															.filter(r -> ((BigDecimal) r[8])
																	.compareTo(BigDecimal.ZERO) == 0) // Filter where
																										// rate is 0
															.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
																							// BigDecimal
															.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
															.orElse(BigDecimal.ZERO); // Default value if no match found

													if ("DAY".equals(unit)) {

														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														System.out.println(" ChronoUnit.HOURS "
																+ ChronoUnit.HOURS.between(gateInDateTime,
																		destuffDateTime)
																+ " + daysBetween1 + " + daysBetween + " "
																+ daysBetween);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														final long daysBetween1 = daysBetween;
														System.out.println(" daysBetween1 " + daysBetween1
																+ " daysBetween " + daysBetween);

														tempAss.setExecutionUnit(String.valueOf(daysBetween1));
														tempAss.setExecutionUnit1("");
														tempAss.setFreeDays(freeDaysForExportContainerStorage);

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
														tempAss.setRates(totalRate);

													}

													if ("WEEK".equals(unit)) {

														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
																destuffDateTime);

														System.out.println(" ChronoUnit.HOURS "
																+ ChronoUnit.HOURS.between(gateInDateTime,
																		destuffDateTime)
																+ " + daysBetween1 + " + daysBetween + " "
																+ daysBetween);

														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
																destuffDateTime) > 0) {
															daysBetween = 1;
														}

														long freeDaysOld = freeDaysForExportContainerStorage != null
																? freeDaysForExportContainerStorage.longValue()
																: 0L;
														;

														tempAss.setFreeDays(
																new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));

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
															if (weeksBetween >= fromRange && weeksBetween <= toRange) {
																serviceRate.set(rate); // Set the rate for the
																						// matching
																						// slab
															}

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
														System.out
																.println("totalRate " + totalRate + " " + weeksBetween);

//																if ("SM".equals(String.valueOf(f[7]))) {
//																	totalRate = (totalRate.multiply(con.getArea()))
//																			.setScale(3, RoundingMode.HALF_UP);
//																}

														tempAss.setRates(totalRate);
														tempAss.setServiceRate(serviceRate.get());
													}

												}

											}

//											ELSE IF RANGE
											else if ("RA".equals(String.valueOf(f[8]))) {
												System.out.println("----------8");

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
//														String serviceGrp = String.valueOf(f[18]);

													if ("H".equals(serviceGrp)) {

//														BigDecimal cargoWt = new BigDecimal(
//																String.valueOf(con.getGrossWt()))
//																.divide(new BigDecimal(1000))
//																.setScale(3, RoundingMode.HALF_UP);
														
														String grossWtStr = String.valueOf(con.getGrossWt()).trim(); // Remove spaces
														 BigDecimal cargoWt = BigDecimal.ZERO;
														
														// Check if the value is numeric before converting
														if (grossWtStr != null && grossWtStr.matches("-?\\d+(\\.\\d+)?")) { 
														     cargoWt = new BigDecimal(grossWtStr)
														                              .divide(new BigDecimal(1000))
														                              .setScale(3, RoundingMode.HALF_UP);
														} else {
														    cargoWt = BigDecimal.ZERO; // Default value or handle appropriately
														    System.out.println("Invalid gross weight: " + grossWtStr);
														}

														

														executionUnit = cargoWt;

														tempAss.setExecutionUnit(String.valueOf(executionUnit));
														tempAss.setExecutionUnit1("");
													} else {

//														BigDecimal cargoWt = con.getCargoWeight()
//																.divide(new BigDecimal(1000))
//																.setScale(3, RoundingMode.HALF_UP);
														
														BigDecimal cargoWeight = con.getCargoWeight();
														BigDecimal cargoWt = (cargoWeight != null) 
														    ? cargoWeight.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP) 
														    : BigDecimal.ZERO; // Default to zero if null


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
																f[14] != null ? String.valueOf(f[14]) : "");
														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
														tempAss.setServiceName(
																f[18] != null ? String.valueOf(f[18]) : "");
														tempAss.setServiceUnit(
																f[1] != null ? String.valueOf(f[1]) : "");
														tempAss.setServiceUnit1(
																f[7] != null ? String.valueOf(f[7]) : "");
														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
														tempAss.setRangeFrom(
																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																		: BigDecimal.ZERO);
														tempAss.setRangeTo(
																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																		: BigDecimal.ZERO);
														tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
														tempAss.setRates(totalRate);
														tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
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
//															String serviceGrp = String.valueOf(f[18]);
														if ("H".equals(serviceGrp)) {

//																CARGO WEIGHT												

															BigDecimal cargoWt = new BigDecimal(
																	String.valueOf(con.getGrossWt()));

															executionUnit = cargoWt;

															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														} else {
															BigDecimal cargoWt = con.getCargoWeight();

															executionUnit = cargoWt;
															tempAss.setExecutionUnit(String.valueOf(executionUnit));
															tempAss.setExecutionUnit1("");
														}
													}

													Object rangeValue = exportInvoiceRepo.getRangeDataByServiceId(cid,
															bid, String.valueOf(f[3]), String.valueOf(f[4]),
															String.valueOf(f[0]), executionUnit, conSize2,
															conTypeOfCon2, commodityType, con.getContainerSize(),
															con.getTypeOfContainer(), assessment.getCommodityCode());

													if (rangeValue != null) {
														Object[] finalRangeValue = (Object[]) rangeValue;
														tempAss.setServiceGroup(
																f[14] != null ? String.valueOf(f[14]) : "");
														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
														tempAss.setServiceName(
																f[18] != null ? String.valueOf(f[18]) : "");
														tempAss.setServiceUnit(
																f[1] != null ? String.valueOf(f[1]) : "");
														tempAss.setServiceUnit1(
																f[7] != null ? String.valueOf(f[7]) : "");
														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
														tempAss.setRangeFrom(
																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																		: BigDecimal.ZERO);
														tempAss.setRangeTo(
																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																		: BigDecimal.ZERO);
														tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
														tempAss.setRates(totalRate);
													}

												} else {
													tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
													tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
													tempAss.setRangeFrom(
															f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																	: BigDecimal.ZERO);
													tempAss.setRangeTo(
															f[16] != null ? new BigDecimal(String.valueOf(f[16]))
																	: BigDecimal.ZERO);
													tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
													tempAss.setRates(totalRate);
												}

											}
//												ELSE PLAIN
											else {
												System.out.println("----------9");
												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
												tempAss.setRates(totalRate);
											}

										}

//											IF STORAGE TYPE NOT IN CONT, CRG AND EMPTY ENDING

										finalConData.add(tempAss);

									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

							// IF SSR DATA NOT EMPTY END

						}

						// ENDING IF SERVICE ID IS FROM SSR

//	STARTING IF SERVICE ID IS NOT FROM SSR		
						else {

//								IF SLAB
							String storageType = getFirstServiceGroup(f[19] != null ? String.valueOf(f[19]) : "");
							String serviceGrp = String.valueOf(f[14]);
//								IF STORAGE TYPE IN CRG STARTING....
							SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							System.out.println(
									"storageType " + storageType + " serviceGrp " + serviceGrp + "-----------10");

							if ("C".equals(serviceGrp) && "CRG".equals(storageType)) {
								AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(BigDecimal.ZERO);

								List<Object[]> exportCartingStorageDays = exportInvoiceRepo.getExportCartingStorageDays(
										cid, bid, con.getStuffTallyId(), con.getContainerNo());

								for (Object[] value : exportCartingStorageDays) {

									try {
										tempAss = (ExportContainerAssessmentData) con.clone();
									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
											day.getEndTime());

									tempAss.setInvoiceDate(invDate);

									String sbNo = value[0] != null ? String.valueOf(value[0]) : "";
									Date stuffTallyDate = null;
									try {
										stuffTallyDate = value[1] != null ? dateTimeFormat.parse(value[1].toString())
												: null;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									try {
										Date inGateInDate = value[2] != null ? dateTimeFormat.parse(value[2].toString())
												: null;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Date cartingTransDate = null;
									try {
										cartingTransDate = value[5] != null ? dateTimeFormat.parse(value[5].toString())
												: null;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									// Retrieve weights and areas as BigDecimal
									BigDecimal areaReleased = value[3] != null ? new BigDecimal(value[3].toString())
											: null;
									BigDecimal totalCargoWeight = value[6] != null ? new BigDecimal(value[6].toString())
											: null;

									// Vehicle number remains as a String
									String vehicleNo = value[4] != null ? value[4].toString() : "";

									LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
											convertToLocalDateTime(cartingTransDate), day.getStartTime());

									LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
											convertToLocalDateTime(stuffTallyDate), day.getEndTime());

									if ("SA".equals(String.valueOf(f[8]))) {
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//									
										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
										tempAss.setContainerStatus(con.getContainerStatus());
										tempAss.setGateOutId(con.getGateOutId());
										tempAss.setGatePassNo(con.getGatePassNo());
										tempAss.setTaxPerc(
												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
														: new BigDecimal(String.valueOf(f[12])));
										tempAss.setTaxId(String.valueOf(f[11]));
										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
										tempAss.setCargoSbNo(sbNo);

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

											String unit = String.valueOf(f[1]);

											if ("DAY".equals(unit)) {

												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

												final long daysBetween1 = daysBetween - cargoFreeDays;

//												final long daysBetween1 = daysBetween;

												tempAss.setExecutionUnit(String.valueOf(daysBetween));
												tempAss.setExecutionUnit1(String.valueOf(areaReleased));

												tempAss.setFreeDays(freeDays);

												System.out.println("daysBetween " + daysBetween1 + " serviceRate.get() "
														+ serviceRate.get());

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

												if ("SM".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(areaReleased)).setScale(3,
															RoundingMode.HALF_UP);
												}

												if ("MTON".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(totalCargoWeight)).setScale(3,
															RoundingMode.HALF_UP);
												}

												System.out.println("tempAss.serviceRate.get() " + serviceRate.get());

												tempAss.setServiceRate(serviceRate.get());
												tempAss.setRates(totalRate);

											}

											if ("WEEK".equals(unit)) {

												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

//												final long daysBetween1 = daysBetween;
//												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

												final long daysBetween1 = daysBetween - cargoFreeDays;

												long weeksBetween2 = (long) Math.ceil(daysBetween / 7.0);
												long weeksBetween1 = (long) Math.ceil(cargoFreeDays / 7.0);
												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);

												tempAss.setExecutionUnit(String.valueOf(weeksBetween2));
												tempAss.setExecutionUnit1(String.valueOf(areaReleased));
												tempAss.setFreeDays(new BigDecimal(weeksBetween1));

//
//												tempAss.setExecutionUnit(String.valueOf(weeksBetween));
//												tempAss.setExecutionUnit1(String.valueOf(areaReleased));

												BigDecimal totalRate = rangeValues.stream().filter(r -> {
													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();
													// Include slabs that overlap with weeksBetween
													return weeksBetween >= fromRange;
												}).map(r -> {
													int fromRange = ((BigDecimal) r[6]).intValue();
													int toRange = ((BigDecimal) r[7]).intValue();
													BigDecimal rate = (BigDecimal) r[8];

													if (weeksBetween >= fromRange && weeksBetween <= toRange) {
														serviceRate.set(rate); // Set the rate for the
																				// matching
																				// slab
													}

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
												System.out.println("totalRate " + totalRate + " " + weeksBetween);

												if ("SM".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(areaReleased)).setScale(3,
															RoundingMode.HALF_UP);
												}

												if ("MTON".equals(String.valueOf(f[7]))) {
													totalRate = (totalRate.multiply(totalCargoWeight)).setScale(3,
															RoundingMode.HALF_UP);
												}
												System.out.println("tempAss.serviceRate.get() 2 " + serviceRate.get());
//												serviceRate.updateAndGet(existingValue -> existingValue.add(serviceRate.get()));														
												tempAss.setServiceRate(serviceRate.get());
												tempAss.setRates(totalRate);
											}

										}

									}
									finalConData.add(tempAss);
								}

							} else {

								System.out.println("----------11");

								try {
									tempAss = (ExportContainerAssessmentData) con.clone();
									Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
											day.getEndTime());

									tempAss.setInvoiceDate(invDate);

									System.out.println("----------12");
//									IF SLAB
									if ("SA".equals(String.valueOf(f[8]))) {
										System.out.println("----------13");
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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

											String unit = String.valueOf(f[1]);

											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
													BigDecimal.ZERO);

											LocalDateTime gateInDateTime = null;
											LocalDateTime destuffDateTime = null;

											if ("MTY".equals(storageType)) {
												gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(con.getGateInDate()),
														day.getStartTime());

												destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(con.getStuffTallyDate()),
														day.getEndTime());

											} else if ("CON".equals(storageType)) {
												gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(con.getStuffTallyDate()),
														day.getStartTime());

												destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(con.getInvoiceDate()), day.getEndTime());

											} else {
												gateInDateTime = adjustToCustomStartOfDay(
														convertToLocalDateTime(con.getGateInDate()),
														day.getStartTime());

												destuffDateTime = adjustToCustomEndOfDay(
														convertToLocalDateTime(con.getInvoiceDate()), day.getEndTime());
											}
											System.out.println("gateInDateTime " + gateInDateTime + " destuffDateTime "
													+ destuffDateTime + " con.getStuffTallyDate() "
													+ con.getStuffTallyDate() + " con.getInvoiceDate() "
													+ con.getInvoiceDate());

											BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
													.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																														// where
																														// rate
																														// is
																														// 0
													.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as BigDecimal
													.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
													.orElse(BigDecimal.ZERO); // Default value if no match found

											if ("DAY".equals(unit)) {

												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);

												System.out.println(" ChronoUnit.HOURS "
														+ ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime)
														+ " + daysBetween1 + " + daysBetween + " " + daysBetween);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

//													final long daysBetween1 = daysBetween;
//
//													tempAss.setExecutionUnit(String.valueOf(daysBetween1));
//													tempAss.setExecutionUnit1("");

												final long daysBetween1 = daysBetween;

												tempAss.setExecutionUnit(String.valueOf(daysBetween1));
												tempAss.setExecutionUnit1("");
												tempAss.setFreeDays(freeDaysForExportContainerStorage);

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
												tempAss.setRates(totalRate);

											}

											if ("WEEK".equals(unit)) {

												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime);
												System.out.println(" ChronoUnit.HOURS "
														+ ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime)
														+ " + daysBetween1 + " + daysBetween + " " + daysBetween);

												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
														destuffDateTime) > 0) {
													daysBetween = 1;
												}

//													final long daysBetween1 = daysBetween;
//													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//													tempAss.setExecutionUnit(String.valueOf(weeksBetween));
//													tempAss.setExecutionUnit1("");

												long freeDaysOld = freeDaysForExportContainerStorage != null
														? freeDaysForExportContainerStorage.longValue()
														: 0L;
												;

												tempAss.setFreeDays(
														new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));

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

													if (weeksBetween >= fromRange && weeksBetween <= toRange) {
														serviceRate.set(rate); // Set the rate for the
																				// matching
																				// slab
													}

													// Adjust the fromRange to exclude week 0
													int adjustedFromRange = Math.max(fromRange, 1);

													// Determine actual weeks in this slab that contribute to the
													// total
													long weeksInSlab = Math.max(0,
															Math.min(toRange, weeksBetween) - adjustedFromRange + 1);

													// Exclude slabs where weeksInSlab is 0
													return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
															: BigDecimal.ZERO;
												}).reduce(BigDecimal.ZERO, BigDecimal::add);

												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//													if ("SM".equals(String.valueOf(f[7]))) {
//														totalRate = (totalRate.multiply(con.getArea())).setScale(3,
//																RoundingMode.HALF_UP);
//													}
												System.out.println("totalRate " + totalRate + " " + weeksBetween);
												tempAss.setServiceRate(serviceRate.get());
												tempAss.setRates(totalRate);

											}

										}

									}
//									ELSE IF RANGE
									else if ("RA".equals(String.valueOf(f[8]))) {
										System.out.println("----------14 " + f[0] != null ? String.valueOf(f[0]) : "");

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
//											String serviceGrp = String.valueOf(f[18]);

											if ("H".equals(serviceGrp)) {
												BigDecimal cargoWt = new BigDecimal(
														String.valueOf(con.getGrossWeight()))
														.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getCargoWeight().divide(new BigDecimal(1000))
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
												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
												tempAss.setRates(totalRate);

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
//												String serviceGrp = String.valueOf(f[18]);
												if ("H".equals(serviceGrp)) {

													BigDecimal cargoWt = new BigDecimal(
															String.valueOf(con.getGrossWeight()));

													executionUnit = cargoWt;

													tempAss.setExecutionUnit(String.valueOf(executionUnit));
													tempAss.setExecutionUnit1("");
												} else {
													BigDecimal cargoWt = con.getCargoWeight();

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
												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
												tempAss.setRangeFrom(
														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
																: BigDecimal.ZERO);
												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
														: BigDecimal.ZERO);
												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
												tempAss.setRates(totalRate);
											}

										}

										else {
											tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
													: BigDecimal.ZERO);
											tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
											tempAss.setRates(totalRate);
										}

									}
//									ELSE PLAIN
									else {
										System.out.println("----------15");
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
										tempAss.setRates(totalRate);
									}

									finalConData.add(tempAss);

								} catch (CloneNotSupportedException e) {
									e.printStackTrace();
								}
							}
						}

//							ENDING IF SERVICE ID IS NOT FROM SSR

					});
//						ENDING LOOP FOR ALL DISTINCT SERVICE IDS
					
					
					
//					List<ExportContainerAssessmentData> newAnxData = finalConData.stream().map(item -> {
//						try {
//							return (ExportContainerAssessmentData) item.clone(); // Clone first
//						} catch (CloneNotSupportedException e) {
//							e.printStackTrace();
//							return null;
//						}
//					}).filter(Objects::nonNull) // Remove null values from failed clones
//							.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
//																								// cloning
//							.collect(Collectors.toList());
//					
					
							for(ExportContainerAssessmentData fnewAnxData : finalConData)
							{
								
								System.out.println("fnewAnxData " + fnewAnxData.getContainerNo() + " " + fnewAnxData.getServiceId() + " " + fnewAnxData.getRates());
							}
					
					
					
					List<ExportContainerAssessmentData> newAnxData = finalConData.stream()
						    .map(item -> {
						        try {
						            return (ExportContainerAssessmentData) item.clone(); // Clone first
						        } catch (CloneNotSupportedException e) {
						            e.printStackTrace();
						            return null;
						        }
						    })
						    .filter(Objects::nonNull) // Remove null values from failed clones
						    .filter(item -> Objects.equals(item.getContainerNo(), con.getContainerNo())) // Safe comparison
						    .collect(Collectors.toList());

					for(ExportContainerAssessmentData f : newAnxData)
					{
						
						System.out.println(" newAnxData " + f.getContainerNo() + " " + f.getServiceId() + " " + f.getRates());
					}
					

					System.out.println("Here Before ---> ENDING LOOP FOR ALL DISTINCT SERVICE IDS");

					AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();

					AssessmentSheet newAss = tempAssessment;

					newAss.setCompanyId(cid);
					newAss.setBranchId(bid);
					newAss.setAssesmentLineNo(String.valueOf(sr));
					newAss.setStatus('A');
					newAss.setCreatedBy(user);
					newAss.setCreatedDate(new Date());
					newAss.setApprovedBy(user);
					newAss.setApprovedDate(new Date());
					newAss.setAssesmentDate(new Date());
					newAss.setTransType("Export Container");
					newAss.setProfitcentreId(con.getProfitcentreId());

					newAss.setAssesmentId(HoldNextIdD1);

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

						Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

						String billingId = "EXP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
								: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
										: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
												: assessment.getOthPartyId();

						String billingAdd = "EXP".equals(assessment.getBillingParty())
								? String.valueOf(assessment.getImpSrNo())
								: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
										: "FWR".equals(assessment.getBillingParty())
												? String.valueOf(assessment.getAccSrNo())
												: assessment.getOthSrNo();

						PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

						newAss.setPartyId(billingId);
						newAss.setPartySrNo(new BigDecimal(billingAdd));

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

					System.out.println("Here Before ---> List<ExportContainerAssessmentData> filteredData");

					List<ExportContainerAssessmentData> filteredData = newAnxData.stream()
							.filter(c -> "H".equals(c.getServiceGroup())).collect(Collectors.toList());


					BigDecimal totalRates = filteredData.stream().map(ExportContainerAssessmentData::getRates)
							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

					System.out.println("totalRates : " + totalRates);

					List<ExportContainerAssessmentData> filteredData1 = newAnxData.stream()
							.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());
					
					
					
					
					

					BigDecimal totalRates1 = filteredData1.stream().map(ExportContainerAssessmentData::getRates)
							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);

					BigDecimal zero = BigDecimal.ZERO;

					newAss.setContainerNo(con.getContainerNo());
					newAss.setContainerSize(con.getContainerSize());
					newAss.setContainerType(con.getContainerType());
					newAss.setGateInDate(con.getGateInDate());
					newAss.setGateOutDate(con.getGateoutDate() == null ? null : con.getGateoutDate());
					newAss.setGrossWeight(con.getGrossWeight());
					newAss.setGateOutType(con.getGateOutType());
					newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
					newAss.setTypeOfContainer(con.getTypeOfContainer());
					newAss.setScanerType(con.getScannerType());
					newAss.setExaminedPercentage(zero);
					newAss.setWeighmentFlag("N");
					newAss.setCargoWeight(con.getCargoWeight());
					newAss.setDestuffDate(con.getGateInDate() == null ? null : con.getGateInDate());
					newAss.setCalculateInvoice('N');
					newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
					newAss.setContainerHandlingAmt(totalRates);
					newAss.setContainerStorageAmt(totalRates1);
					newAss.setInvoiceCategory(assessment.getInvoiceCategory());
					newAss.setInvType("First");
					newAss.setSa(tempAssessment.getSa());
					newAss.setSl(tempAssessment.getSl());
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrServiceId(con.getSsrTransId());
					newAss.setStuffTallyDate(con.getStuffTallyDate());
					newAss.setGateOutDate(con.getGateOutDate());
					newAss.setMovReqDate(con.getMovementDate());
					newAss.setMinCartingTransDate(con.getCartingDate());
					newAss.setCartingTransId(con.getCartingTransId());

					newAss.setInvoiceNo(null);
					assessmentsheetrepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P01103", HoldNextIdD1, "2024");

					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);

					System.out.println("Here Before ---> finalConData.stream().forEach(c -> {");

					newAnxData.stream().forEach(c -> {
						
						if(c.getContainerNo().equals(con.getContainerNo()))
								{

						if ("C".equals(c.serviceGroup)) {

							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
								Cfinvsrvanx anx = new Cfinvsrvanx();
								anx.setCompanyId(cid);
								anx.setBranchId(bid);
								anx.setErpDocRefNo(newAss.getSbTransId());
								anx.setProcessTransId(HoldNextIdD1);
								anx.setServiceId(c.getServiceId());
								anx.setSrlNo(anxSrNo.get());
								anx.setDocRefNo(newAss.getSbNo());
								anx.setIgmLineNo("");
								anx.setProfitcentreId(newAss.getProfitcentreId());
								anx.setInvoiceType("EXP");
								
								
								anx.setInvoiceSubType("CONT");
								
								
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
								anx.setDiscPercentage(zero);
								anx.setDiscValue(zero);
								anx.setmPercentage(zero);
								anx.setmAmount(c.getmAmount());
								anx.setAcCode(c.getAcCode());
								anx.setProcessTransDate(newAss.getAssesmentDate());
								anx.setProcessId("P01103");
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
								anx.setCargoSBNo(c.getCargoSbNo());
								anx.setFreeDays(c.getFreeDays());
								anx.setLineNo(newAss.getAssesmentLineNo());
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
								anx.setLineNo(newAss.getAssesmentLineNo());
								
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

								cfinvsrvanxrepo.save(anx);

								anxSrNo.set(anxSrNo.get().add(new BigDecimal(1)));
							}

						} else {

							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
								Cfinvsrvanx anx = new Cfinvsrvanx();
								anx.setCompanyId(cid);
								anx.setBranchId(bid);
								anx.setErpDocRefNo(newAss.getSbTransId());
								anx.setProcessTransId(HoldNextIdD1);
								anx.setServiceId(c.getServiceId());
								
								anx.setSrlNo(anxSrNo.get());
								anx.setDocRefNo(newAss.getSbNo());
								anx.setProfitcentreId(newAss.getProfitcentreId());
								anx.setInvoiceType("EXP");
								
								anx.setInvoiceSubType("CONT");
								
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
								anx.setProcessId("P01103");
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
								anx.setFreeDays(c.getFreeDays());

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
								anx.setLineNo(newAss.getAssesmentLineNo());
								
								
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

								cfinvsrvanxrepo.save(anx);

								anxSrNo.set(anxSrNo.get().add(new BigDecimal(1)));
							}
						}
						
						
						
								}
						
					}
					
							
							
							
							
							
							
							);

					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

					int updateExpInventoryContainerInvoice = exportInvoiceRepo.updateExpInventoryContainerInvoice(
							HoldNextIdD1, "Y", cid, bid, con.getStuffTallyId(), con.getContainerNo());

					int updateStuffTallyContainerInvoice = exportInvoiceRepo.updateStuffTallyContainerInvoice(
							HoldNextIdD1, cid, bid, con.getStuffTallyId(), con.getContainerNo());

					int updateMovementRequestContainerInvoice = exportInvoiceRepo.updateMovementRequestContainerInvoice(
							HoldNextIdD1, cid, bid, con.getStuffTallyId(), con.getMovementReqId(),
							con.getContainerNo());

					System.out.println("updateExpInventoryContainerInvoice : " + updateExpInventoryContainerInvoice
							+ " updateStuffTallyContainerInvoice : " + updateStuffTallyContainerInvoice
							+ " updateMovementRequestContainerInvoice: " + updateMovementRequestContainerInvoice);

					assessment.setAssesmentId(HoldNextIdD1);

					sr++;

				}
			} else {

				int updateAssessmentContainerEdit = exportInvoiceRepo.updateAssessmentContainerEdit(cid, bid,
						assessment.getProfitcentreId(), assessment.getAssesmentId(), assessment.getComments(),
						assessment.getIntComments());

				System.out.println("updateAssessmentContainerEdit : " + updateAssessmentContainerEdit);

			}

			System.out.println("Before ------ selectedExportAssesmentSheet ");
			AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
					assessment.getProfitcentreId(), assessment.getAssesmentId());

			
			List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
					.getSelectedExportAssesmentSheetContainerData(cid, bid, assessment.getProfitcentreId(),
							assessment.getAssesmentId());

			System.out.println("Before ------ cargoStorageServiceId");

			String cargoStorageServiceId = exportInvoiceRepo.getCargoStorageServiceId(cid, bid);
			List<Object[]> cargoStorageOfAssessmentSheetDetails = exportInvoiceRepo
					.getCargoStorageOfAssessmentSheetDetails(cid, bid, assessment.getSbTransId(),
							assessment.getAssesmentId(), cargoStorageServiceId);

			
			
			
			
			
			
			if (selectedExportAssesmentSheetContainerData.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				selectedExportAssesmentSheetContainerData.stream().forEach(r -> {
					
//					BigDecimal rate = new BigDecimal(String.valueOf(r.getRates()));
//					BigDecimal taxPerc = new BigDecimal(String.valueOf(r.getTaxPerc()));
					BigDecimal rate = Optional.ofNullable(r.getRates())
	                         .map(Object::toString)
	                         .filter(s -> s.matches("-?\\d+(\\.\\d+)?"))
	                         .map(BigDecimal::new)
	                         .orElse(BigDecimal.ZERO);

	BigDecimal taxPerc = Optional.ofNullable(r.getTaxPerc())
	                             .map(Object::toString)
	                             .filter(s -> s.matches("-?\\d+(\\.\\d+)?"))
	                             .map(BigDecimal::new)
	                             .orElse(BigDecimal.ZERO);
					

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

				} else if ("EXP".equals(assessment.getBillingParty())) {
					pId = assessment.getImporterId();
				} else if ("FWR".equals(assessment.getBillingParty())) {
					pId = assessment.getOnAccountOf();
				} else if ("OTH".equals(assessment.getBillingParty())) {
					pId = assessment.getOthPartyId();
				}

				Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);

				Map<String, Object> finalResult = new HashMap<>();
				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

				if (p != null) {
					
					
					
					if (assessment.getCreditType() == 'P') {
						BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, pId, "AD");

						finalResult.put("advanceData", advanceData);						
						selectedExportAssesmentSheet.setAdvanceAmount(advanceData);						
					}

					BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
					BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

					BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
									
					
					selectedExportAssesmentSheet.setCreditAllowed(credit1);
					selectedExportAssesmentSheet.setUsedCredit(credit2);		
					selectedExportAssesmentSheet.setPendingCredit(allowedValue);
					
					
					
					
					
					finalResult.put("tanNo", p.getTanNoId());
					finalResult.put("tdsPerc", p.getTdsPercentage());
				}

				List<Object[]> filteredData = cargoStorageOfAssessmentSheetDetails.stream()  // Convert List<Object[]> to Stream<Object[]>
					    .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))  // Keep rows that have at least one non-null value
					    .collect(Collectors.toList());  // Convert back to List<Object[]>

				
			finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
			finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);
			finalResult.put("sbData", filteredData);

			return new ResponseEntity<>(finalResult, HttpStatus.OK);
			}
			// return null;
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Transactional
	public ResponseEntity<?> saveExportAssesmentCartingAndBackToTown(String cid, String bid, String user, Map<String, Object> data, String invoiceType)
			throws JsonMappingException, JsonProcessingException {
		try {
			ObjectMapper mapper = new ObjectMapper();

			AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assesmentSheet")),
					AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<ExportContainerAssessmentData> containerData = mapper.readValue(
					mapper.writeValueAsString(data.get("containerData")),
					new TypeReference<List<ExportContainerAssessmentData>>() {
					});

			System.out.println(containerData);
			if (containerData.isEmpty()) {
				return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
			}

			if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {

				int sr = 1;

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P01133", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("PCID%06d", nextNumericNextID1);

				String mappingType = "";
				
				if("Export Cargo Carting".equalsIgnoreCase(invoiceType))
				{
					
					mappingType = "EXPCRT";
				}
				
				
				AtomicReference<BigDecimal> anxSrNo = new AtomicReference<>(new BigDecimal(1));
				

				for (ExportContainerAssessmentData con : containerData) {

					List<ExportContainerAssessmentData> finalConData = new ArrayList<>();
					// Service Mapping
					List<String> serviceMappingData = new ArrayList<>();

					System.out.println(" -----1");

					if ("SINGLE".equalsIgnoreCase(assessment.getInvoiceCategory())) {
						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceAll(cid, bid,
								con.getProfitcentreId(), mappingType);
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceByType(cid, bid,
								con.getProfitcentreId(), mappingType, group);
					}

					System.out.println(" -----2");

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

					System.out.println(" -----3");
					String tariffNo = exportInvoiceRepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList,
							fwIdList, assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
							assessment.getOnAccountOf());

					con.setUpTariffNo(tariffNo);
					System.out.println(" -----4");

					// Find default service
					List<String> defaultService = exportInvoiceRepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());

					System.out.println(" -----5");
					if (!defaultService.isEmpty()) {
						combinedService.addAll(defaultService);
					}


					List<String> finalServices = new ArrayList<>(combinedService);

					List<Object[]> finalPricing = new ArrayList<>();
					List<String> notInPricing = new ArrayList<>();

					
					Date assData = assessment.getAssesmentDate();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String formattedDate = formatter.format(assData);
					Date parsedDate = formatter.parse(formattedDate);

					List<Object[]> pricingData = exportInvoiceRepo.getServiceRateForCarting(cid, bid, parsedDate, finalServices, con.getUpTariffNo());

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
						

						List<Object[]> pricingData1 = exportInvoiceRepo.getServiceRateForCarting(cid, bid, parsedDate,
														notInPricing, "CFS1000001");

						if (!pricingData1.isEmpty()) {
							List<Object[]> remainingPricing = pricingData1.stream()
									.filter(data1 -> tempPricing.contains(data1[0].toString()))
									.collect(Collectors.toList());

							finalPricing.addAll(remainingPricing);
						}
					}


//						STARTING LOOP FOR ALL DISTINCT SERVICE IDS
					finalPricing.stream().forEach(f -> {
						ExportContainerAssessmentData tempAss = new ExportContainerAssessmentData();
						CFSDay day = cfsdayrepo.getData(cid, bid);
//	STARTING IF SERVICE ID IS NOT FROM SSR								

//								IF SLAB
							String serviceGrp = String.valueOf(f[14]);
//								IF STORAGE TYPE IN CRG STARTING....
							System.out.println(" serviceGrp " + serviceGrp + "-----------10");


							System.out.println("----------11");

							try {
								tempAss = (ExportContainerAssessmentData) con.clone();
								Date invDate = adjustInvoiceDate(new Date(), day.getStartTime(),
										day.getEndTime());

								tempAss.setInvoiceDate(invDate);

								System.out.println("----------12");
//								IF SLAB
								if ("SA".equals(String.valueOf(f[8]))) {
									System.out.println("----------13");
									tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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

									List<Object[]> rangeValues = exportInvoiceRepo.getDataByServiceIdCarting(cid, bid,
											String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
											String.valueOf(f[8]));

									if (!rangeValues.isEmpty()) {

										String unit = String.valueOf(f[1]);

										AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
												BigDecimal.ZERO);

										LocalDateTime gateInDateTime = null;
										LocalDateTime destuffDateTime = null;

										
											gateInDateTime = adjustToCustomStartOfDay(
													convertToLocalDateTime(con.getSbDate()),
													day.getStartTime());

											destuffDateTime = adjustToCustomEndOfDay(
													convertToLocalDateTime(con.getSbDate()), day.getEndTime());
										
										System.out.println("gateInDateTime " + gateInDateTime + " destuffDateTime "
												+ destuffDateTime + " con.getStuffTallyDate() "
												+ con.getStuffTallyDate() + " con.getInvoiceDate() "
												+ con.getInvoiceDate());

										BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
												.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
																													// is
																													// 0
												.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as BigDecimal
												.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
												.orElse(BigDecimal.ZERO); // Default value if no match found

										if ("DAY".equals(unit)) {

											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
													destuffDateTime);

											System.out.println(" ChronoUnit.HOURS "
													+ ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime)
													+ " + daysBetween1 + " + daysBetween + " " + daysBetween);

											if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
													destuffDateTime) > 0) {
												daysBetween = 1;
											}

//												final long daysBetween1 = daysBetween;
//
//												tempAss.setExecutionUnit(String.valueOf(daysBetween1));
//												tempAss.setExecutionUnit1("");

											final long daysBetween1 = daysBetween;

											tempAss.setExecutionUnit(String.valueOf(daysBetween1));
											tempAss.setExecutionUnit1("");
											tempAss.setFreeDays(freeDaysForExportContainerStorage);

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
											tempAss.setRates(totalRate);

										}

										if ("WEEK".equals(unit)) {

											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
													destuffDateTime);
											System.out.println(" ChronoUnit.HOURS "
													+ ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime)
													+ " + daysBetween1 + " + daysBetween + " " + daysBetween);

											if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
													destuffDateTime) > 0) {
												daysBetween = 1;
											}

//												final long daysBetween1 = daysBetween;
//												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//												tempAss.setExecutionUnit(String.valueOf(weeksBetween));
//												tempAss.setExecutionUnit1("");

											long freeDaysOld = freeDaysForExportContainerStorage != null
													? freeDaysForExportContainerStorage.longValue()
													: 0L;
											;

											tempAss.setFreeDays(
													new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));

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

												if (weeksBetween >= fromRange && weeksBetween <= toRange) {
													serviceRate.set(rate); // Set the rate for the
																			// matching
																			// slab
												}

												// Adjust the fromRange to exclude week 0
												int adjustedFromRange = Math.max(fromRange, 1);

												// Determine actual weeks in this slab that contribute to the
												// total
												long weeksInSlab = Math.max(0,
														Math.min(toRange, weeksBetween) - adjustedFromRange + 1);

												// Exclude slabs where weeksInSlab is 0
												return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
														: BigDecimal.ZERO;
											}).reduce(BigDecimal.ZERO, BigDecimal::add);

											totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//												if ("SM".equals(String.valueOf(f[7]))) {
//													totalRate = (totalRate.multiply(con.getArea())).setScale(3,
//															RoundingMode.HALF_UP);
//												}
											System.out.println("totalRate " + totalRate + " " + weeksBetween);
											tempAss.setServiceRate(serviceRate.get());
											tempAss.setRates(totalRate);

										}

									}

								}
//								ELSE IF RANGE
								else if ("RA".equals(String.valueOf(f[8]))) {
									System.out.println("----------14 " + f[0] != null ? String.valueOf(f[0]) : "");

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
//										String serviceGrp = String.valueOf(f[18]);

										if ("H".equals(serviceGrp)) {
											BigDecimal cargoWt = new BigDecimal(
													String.valueOf(con.getGrossWeight()))
													.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;

											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else {
											BigDecimal cargoWt = con.getCargoWeight().divide(new BigDecimal(1000))
													.setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}

										Object rangeValue = exportInvoiceRepo.getRangeDataByServiceIdCarting(cid, bid,
												String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
												executionUnit);

										if (rangeValue != null) {
											Object[] finalRangeValue = (Object[]) rangeValue;
											tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(
													f[15] != null ? new BigDecimal(String.valueOf(f[15]))
															: BigDecimal.ZERO);
											tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
											tempAss.setRates(totalRate);

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
//											String serviceGrp = String.valueOf(f[18]);
											if ("H".equals(serviceGrp)) {

												BigDecimal cargoWt = new BigDecimal(
														String.valueOf(con.getGrossWeight()));

												executionUnit = cargoWt;

												tempAss.setExecutionUnit(String.valueOf(executionUnit));
												tempAss.setExecutionUnit1("");
											} else {
												BigDecimal cargoWt = con.getCargoWeight();

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
											tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
											tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
											tempAss.setRangeFrom(
													f[15] != null ? new BigDecimal(String.valueOf(f[15]))
															: BigDecimal.ZERO);
											tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
													: BigDecimal.ZERO);
											tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
											tempAss.setRates(totalRate);
										}

									}

									else {
										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
												: BigDecimal.ZERO);
										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
												: BigDecimal.ZERO);
										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
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
										tempAss.setRates(totalRate);
									}

								}
//								ELSE PLAIN
								else {
									
									String unit = String.valueOf(f[1]);
									BigDecimal executionUnit = BigDecimal.ZERO;
									
									System.out.println("----------15");
									tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");

									tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
									tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
									tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
									tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
											: BigDecimal.ZERO);
									tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
											: BigDecimal.ZERO);
									tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
									tempAss.setContainerStatus(con.getContainerStatus());
									tempAss.setGateOutId(con.getGateOutId());
									tempAss.setGatePassNo(con.getGatePassNo());
									
									tempAss.setExecutionUnit1("");
									tempAss.setTaxPerc(
											(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
													: new BigDecimal(String.valueOf(f[12])));
									tempAss.setTaxId(String.valueOf(f[11]));
									
									System.out.println("***************** unit ************" + unit);
									if ("MTON".equals(unit)) {
//										String serviceGrp = String.valueOf(f[18]);

										if ("H".equals(serviceGrp)) {
											BigDecimal cargoWt = new BigDecimal(
													String.valueOf(con.getGrossWeight()))
													.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;

											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										} else {
											BigDecimal cargoWt = con.getGrossWeight().divide(new BigDecimal(1000))
													.setScale(3, RoundingMode.HALF_UP);

											executionUnit = cargoWt;
											tempAss.setExecutionUnit(String.valueOf(executionUnit));
											tempAss.setExecutionUnit1("");
										}
										
										
									
									}
									
									
									BigDecimal rate =  new BigDecimal(String.valueOf(f[2]));
									
									BigDecimal multiply = rate.multiply(executionUnit);
									tempAss.setExecutionUnit(String.valueOf(executionUnit));
									
									tempAss.setExRate(rate);
									BigDecimal totalRate = multiply;
									tempAss.setServiceRate(totalRate);
									tempAss.setRates(totalRate);
								}

								finalConData.add(tempAss);

							} catch (CloneNotSupportedException e) {
								e.printStackTrace();
							}
										
							
						
					});
//						ENDING LOOP FOR ALL DISTINCT SERVICE IDS

				

					System.out.println("Here Before ---> ENDING LOOP FOR ALL DISTINCT SERVICE IDS");

					AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();

					AssessmentSheet newAss = tempAssessment;

					newAss.setCompanyId(cid);
					newAss.setBranchId(bid);
					newAss.setAssesmentLineNo(String.valueOf(sr));
					newAss.setStatus('A');
					newAss.setCreatedBy(user);
					newAss.setCreatedDate(new Date());
					newAss.setApprovedBy(user);
					newAss.setApprovedDate(new Date());
					newAss.setAssesmentDate(new Date());
					newAss.setTransType(invoiceType);
					newAss.setProfitcentreId(con.getProfitcentreId());

					newAss.setAssesmentId(HoldNextIdD1);

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

						Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);

						String billingId = "EXP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
								: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
										: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
												: assessment.getOthPartyId();

						String billingAdd = "EXP".equals(assessment.getBillingParty())
								? String.valueOf(assessment.getImpSrNo())
								: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
										: "FWR".equals(assessment.getBillingParty())
												? String.valueOf(assessment.getAccSrNo())
												: assessment.getOthSrNo();

						PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);

						newAss.setPartyId(billingId);
						newAss.setPartySrNo(new BigDecimal(billingAdd));

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

					System.out.println("Here Before ---> List<ExportContainerAssessmentData> filteredData");

					
				
					BigDecimal zero = BigDecimal.ZERO;

					newAss.setContainerNo(con.getContainerNo());
					newAss.setContainerSize(con.getContainerSize());
					newAss.setContainerType(con.getContainerType());
					newAss.setGateInDate(con.getGateInDate());
					newAss.setGateOutDate(con.getGateoutDate() == null ? null : con.getGateoutDate());
					newAss.setGrossWeight(con.getGrossWeight());
					newAss.setSbWeight(con.getGrossWeight());
					newAss.setTypeOfCargo(con.getCargoType());
					newAss.setNoOfPackages(con.getNoOfPackages());
					
					
					newAss.setGateOutType(con.getGateOutType());
					newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
					newAss.setTypeOfContainer(con.getTypeOfContainer());
					newAss.setScanerType(con.getScannerType());
					newAss.setExaminedPercentage(zero);
					newAss.setWeighmentFlag("N");
					newAss.setCargoWeight(con.getCargoWeight());
					newAss.setDestuffDate(con.getGateInDate() == null ? null : con.getGateInDate());
					newAss.setCalculateInvoice('N');
					newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
					newAss.setContainerHandlingAmt(zero);
					newAss.setContainerStorageAmt(zero);
					newAss.setInvoiceCategory(assessment.getInvoiceCategory());
					newAss.setInvType("CRTG");
					newAss.setSa(tempAssessment.getSa());
					newAss.setSl(tempAssessment.getSl());
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrServiceId(con.getSsrTransId());
					newAss.setStuffTallyDate(con.getStuffTallyDate());
					newAss.setGateOutDate(con.getGateOutDate());
					newAss.setMovReqDate(con.getMovementDate());
					newAss.setMinCartingTransDate(con.getCartingDate());
					newAss.setCartingTransId(con.getCartingTransId());
					newAss.setSpecialDelivery("CRTG");
					newAss.setTransactionType(assessment.getTransactionType()); // merge to the same to container Invoice
					

					newAss.setInvoiceNo(null);
					assessmentsheetrepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P01133", HoldNextIdD1, "2024");

					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);

					System.out.println("Here Before ---> finalConData.stream().forEach(c -> {");

					finalConData.stream().forEach(c -> {

						
								if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
									Cfinvsrvanx anx = new Cfinvsrvanx();
									anx.setCompanyId(cid);
									anx.setBranchId(bid);
									anx.setErpDocRefNo(newAss.getSbTransId());
									anx.setProcessTransId(HoldNextIdD1);
									anx.setServiceId(c.getServiceId());
									anx.setLineNo(newAss.getAssesmentLineNo());
									anx.setSrlNo(anxSrNo.get());
									
									anx.setDocRefNo(newAss.getSbNo());
									anx.setProfitcentreId(newAss.getProfitcentreId());
									anx.setInvoiceType("EXP");
									anx.setInvoiceSubType("CRTG");
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
									anx.setProcessId("P01103");
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
									anx.setFreeDays(c.getFreeDays());

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

									cfinvsrvanxrepo.save(anx);

									anxSrNo.set(anxSrNo.get().add(new BigDecimal(1)));						
													}

					}

					);

					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));


					assessment.setAssesmentId(HoldNextIdD1);

					sr++;

				}
			} else {

				int updateAssessmentContainerEdit = exportInvoiceRepo.updateAssessmentContainerEdit(cid, bid,
						assessment.getProfitcentreId(), assessment.getAssesmentId(), assessment.getComments(),
						assessment.getIntComments());

				System.out.println("updateAssessmentContainerEdit : " + updateAssessmentContainerEdit);

			}

			System.out.println("Before ------ selectedExportAssesmentSheet ");
			AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
					assessment.getProfitcentreId(), assessment.getAssesmentId());

			System.out.println(
					"Before ------ selectedExportAssesmentSheetContainerData : \n" + selectedExportAssesmentSheet);
			List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
					.getSelectedExportAssesmentSheetContainerDataCarting(cid, bid, assessment.getProfitcentreId(),
							assessment.getAssesmentId());

			System.out.println("Before ------ cargoStorageServiceId");

			
			if (selectedExportAssesmentSheetContainerData.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

				selectedExportAssesmentSheetContainerData.stream().forEach(r -> {
					System.out.println("r.get" + r.getServiceId() + " " + r.getRates() + " taxPerc " + r.getTaxPerc());

//					BigDecimal rate = new BigDecimal(String.valueOf(r.getRates()));
//					BigDecimal taxPerc = new BigDecimal(String.valueOf(r.getTaxPerc()));

					BigDecimal rate = Optional.ofNullable(r.getRates()).map(Object::toString)
							.filter(s -> s.matches("-?\\d+(\\.\\d+)?")).map(BigDecimal::new).orElse(BigDecimal.ZERO);

					BigDecimal taxPerc = Optional.ofNullable(r.getTaxPerc()).map(Object::toString)
							.filter(s -> s.matches("-?\\d+(\\.\\d+)?")).map(BigDecimal::new).orElse(BigDecimal.ZERO);

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

				} else if ("EXP".equals(assessment.getBillingParty())) {
					pId = assessment.getImporterId();
				} else if ("FWR".equals(assessment.getBillingParty())) {
					pId = assessment.getOnAccountOf();
				} else if ("OTH".equals(assessment.getBillingParty())) {
					pId = assessment.getOthPartyId();
				}

				Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);

				Map<String, Object> finalResult = new HashMap<>();
				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);

				if (p != null) {					
					
					System.out.println(" assessment.getCreditType() " + assessment.getCreditType());
					if (assessment.getCreditType() == 'P') {

						BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, pId, "AD");

						finalResult.put("advanceData", advanceData);
						selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
					}

					BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
					BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

					BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
									
					
					selectedExportAssesmentSheet.setCreditAllowed(credit1);
					selectedExportAssesmentSheet.setUsedCredit(credit2);		
					selectedExportAssesmentSheet.setPendingCredit(allowedValue);
					
					
					
					
					
					
					
					
					
					finalResult.put("tanNo", p.getTanNoId());
					finalResult.put("tdsPerc", p.getTdsPercentage());
				}

				finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
				finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);

				return new ResponseEntity<>(finalResult, HttpStatus.OK);
			}
			// return null;
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public List<Object[]> getAssesMentEntriesToSelect(String companyId, String branchId, String searchValue)
//	{				
//		return exportInvoiceRepo.getAssesMentEntriesToSelect(companyId, branchId, searchValue);
//	}
	
	
	public List<Object[]> getAssesMentEntriesToSelect(String companyId, String branchId, String searchValue, String transType) {
		return exportInvoiceRepo.getAssesMentEntriesToSelect(companyId, branchId, searchValue, transType);
	}
	
	public ResponseEntity<?> getSelectedAssesMentSearch(String companyId, String branchId, String assesMentId,
			String invoiceNo, String sbTransId, String profitCenterId, String transType) {

		AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(companyId, branchId, profitCenterId,
				assesMentId);

		System.out
				.println("Before ------ selectedExportAssesmentSheetContainerData : \n" + selectedExportAssesmentSheet);
		
		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = new ArrayList<>();
		List<Object[]> filteredData = new ArrayList<>();
		
		if("Export Container".equalsIgnoreCase(transType))
		{
			selectedExportAssesmentSheetContainerData = exportInvoiceRepo
					.getSelectedExportAssesmentSheetContainerData(companyId, branchId, profitCenterId, assesMentId);
			
			
			String cargoStorageServiceId = exportInvoiceRepo.getCargoStorageServiceId(companyId, branchId);
			List<Object[]> cargoStorageOfAssessmentSheetDetails = exportInvoiceRepo.getCargoStorageOfAssessmentSheetDetails(
					companyId, branchId, sbTransId, assesMentId, cargoStorageServiceId);

			System.out.println(
					"Before ------ cargoStorageOfAssessmentSheetDetails \n" + cargoStorageOfAssessmentSheetDetails);

			filteredData = cargoStorageOfAssessmentSheetDetails.stream()					
					.filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))
					.collect(Collectors.toList());
			
			
		}else if("Export Cargo Carting".equalsIgnoreCase(transType))
		{
			selectedExportAssesmentSheetContainerData = exportInvoiceRepo.getSelectedExportAssesmentSheetContainerDataCarting(companyId, branchId, profitCenterId, assesMentId);	
			
		}else if("Export Cargo".equalsIgnoreCase(transType))
		{
			selectedExportAssesmentSheetContainerData = exportInvoiceRepo.getSelectedExportAssesmentSheetBackToTown(companyId, branchId, profitCenterId, assesMentId);	
			
		}
		
		


		
		if (selectedExportAssesmentSheetContainerData == null || selectedExportAssesmentSheetContainerData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = exportInvoiceRepo.getInvoiceGenerated(companyId, branchId, invoiceNo);

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = exportInvoiceRepo.getFinTransGenerated(companyId, branchId, invoiceNo);

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())).findFirst()
				.orElse(null);

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		

		finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);
		finalResult.put("sbData", filteredData);

		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
			
			Party p = exportInvoiceRepo.getPartyForTanNo(companyId, branchId, existingSrv.getPartyId());

			if (p != null) {
			
			if (selectedExportAssesmentSheet.getCreditType() == 'P') {
				BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(companyId, branchId, existingSrv.getPartyId(), "AD");

				finalResult.put("advanceData", advanceData);
				selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
			}

			BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
			BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

			BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
							
			
			selectedExportAssesmentSheet.setCreditAllowed(credit1);
			selectedExportAssesmentSheet.setUsedCredit(credit2);		
			selectedExportAssesmentSheet.setPendingCredit(allowedValue);
			}
			
			
			
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = selectedExportAssesmentSheet.getCha();

			} else if ("EXP".equals(singleTransData.getTdsType())) {
				pId = selectedExportAssesmentSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = selectedExportAssesmentSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = selectedExportAssesmentSheet.getOthPartyId();
			}

			Party p = exportInvoiceRepo.getPartyForTanNo(companyId, branchId, pId);

			if (p != null) {
				finalResult.put("tanNo", p.getTanNoId());
				
				
				if (selectedExportAssesmentSheet.getCreditType() == 'P') {
					BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(companyId, branchId, pId, "AD");

					finalResult.put("advanceData", advanceData);
					selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
								
				
				selectedExportAssesmentSheet.setCreditAllowed(credit1);
				selectedExportAssesmentSheet.setUsedCredit(credit2);		
				selectedExportAssesmentSheet.setPendingCredit(allowedValue);
				
				
				
			}

		}
		return ResponseEntity.ok(finalResult);

	}

	
	
	
//	
//	public ResponseEntity<?> getSelectedAssesMentSearch(String companyId, String branchId, String assesMentId, String invoiceNo, String sbTransId, String profitCenterId) {
//					
//		AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(companyId, branchId, profitCenterId, assesMentId);
//
//		System.out.println(
//				"Before ------ selectedExportAssesmentSheetContainerData : \n" + selectedExportAssesmentSheet);
//		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
//												.getSelectedExportAssesmentSheetContainerData(companyId, branchId, profitCenterId, assesMentId);
//
//		System.out.println("Before ------ cargoStorageServiceId");
//
//		String cargoStorageServiceId = exportInvoiceRepo.getCargoStorageServiceId(companyId, branchId);
//		List<Object[]> cargoStorageOfAssessmentSheetDetails = exportInvoiceRepo
//				.getCargoStorageOfAssessmentSheetDetails(companyId, branchId, sbTransId, assesMentId, cargoStorageServiceId);
//
//		System.out.println(
//				"Before ------ cargoStorageOfAssessmentSheetDetails \n" + cargoStorageOfAssessmentSheetDetails);
//
//		
//		if (selectedExportAssesmentSheetContainerData == null || selectedExportAssesmentSheetContainerData.isEmpty()) {
//			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
//		}
//
//		Cfinvsrv existingSrv = exportInvoiceRepo.getInvoiceGenerated(companyId, branchId, invoiceNo);
//
//		if (existingSrv == null) {
//			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
//		}
//
//		List<FinTrans> existingSrvFin = exportInvoiceRepo.getFinTransGenerated(companyId, branchId, invoiceNo);
//
//		if (existingSrvFin.isEmpty()) {
//			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
//		}
//
//		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())) 
//				.findFirst()
//				.orElse(null);
//
//		Map<String, Object> finalResult = new HashMap<>();
//		finalResult.put("existingSrv", existingSrv);
//		finalResult.put("existingSrvFin", existingSrvFin);
//		
//		
//		List<Object[]> filteredData = cargoStorageOfAssessmentSheetDetails.stream()  // Convert List<Object[]> to Stream<Object[]>
//			    .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))  // Keep rows that have at least one non-null value
//			    .collect(Collectors.toList());  // Convert back to List<Object[]>
//
//		
//		finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
//		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);
//		finalResult.put("sbData", filteredData);
//
//		if (singleTransData == null) {
//			finalResult.put("tdsDeductee", "");
//			finalResult.put("tdsperc", "");
//			finalResult.put("tanNo", "");
//		} else {
//			finalResult.put("tdsDeductee", singleTransData.getTdsType());
//			finalResult.put("tdsperc", singleTransData.getTdsPercentage());
//
//			String pId = "";
//
//			if ("CHA".equals(singleTransData.getTdsType())) {
//
//				pId = selectedExportAssesmentSheet.getCha();
//
//			} else if ("IMP".equals(singleTransData.getTdsType())) {
//				pId = selectedExportAssesmentSheet.getImporterId();
//			} else if ("FWR".equals(singleTransData.getTdsType())) {
//				pId = selectedExportAssesmentSheet.getOnAccountOf();
//			} else if ("OTH".equals(singleTransData.getTdsType())) {
//				pId = selectedExportAssesmentSheet.getOthPartyId();
//			}
//
//			Party p = exportInvoiceRepo.getPartyForTanNo(companyId, branchId, pId);
//
//			if (p != null) {
//				finalResult.put("tanNo", p.getTanNoId());
//			}
//
//			
//		}
//		return ResponseEntity.ok(finalResult);
//		
//	}
//	
//	
	
	
	
	
	
	
	
	
	
//	
//	
//public ResponseEntity<?> saveAddServiceServiceWise(String companyId, String branchId,	String user,Map<String, Object> resultList) {
//		
//		try {			
//			
//			AssessmentSheet assessment = objectMapper.readValue(objectMapper.writeValueAsString(resultList.get("assesmentSheet")),
//					AssessmentSheet.class);
//
//			if (assessment == null) {
//				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
//			}
//
//			List<Cfinvsrvanx> cfInvsrvanxData = objectMapper.readValue(
//					objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
//					new TypeReference<List<Cfinvsrvanx>>() {
//					});
//
//			
//			BigDecimal zero = BigDecimal.ZERO;		
//			
//			
//			for(Cfinvsrvanx anx : cfInvsrvanxData)
//			{				
//				
//				Optional<BigDecimal> highestSRLNoOptional = exportInvoiceRepo.getHighestSrlNoByContainerNo(companyId, branchId, assessment.getProfitcentreId(), assessment.getAssesmentId(), anx.getContainerNo());
//				BigDecimal highestSRLNo = highestSRLNoOptional.orElse(BigDecimal.ZERO);
//				BigDecimal highestSRLNoNew = highestSRLNo.add(BigDecimal.ONE);
//					
//				anx.setCompanyId(companyId);
//				anx.setBranchId(branchId);
//				anx.setErpDocRefNo(assessment.getSbTransId());
//				anx.setProcessTransId(assessment.getAssesmentId());
//				anx.setSrlNo(highestSRLNoNew);
//				anx.setDocRefNo(assessment.getSbNo());
//				anx.setIgmLineNo("");
//				anx.setProfitcentreId(assessment.getProfitcentreId());
//				anx.setInvoiceType("EXP");
//				anx.setAddServices("Y");
//				
//				anx.setLocalAmt(anx.getActualNoOfPackages());
//				anx.setInvoiceAmt(anx.getActualNoOfPackages());
//				anx.setCommodityDescription(assessment.getCommodityDescription());
//				anx.setDiscPercentage(zero);
//				anx.setDiscValue(zero);
//				anx.setmPercentage(zero);
//				anx.setmAmount(zero);
//				
//				anx.setPartyId(assessment.getPartyId());
//				
//				
//				
//				anx.setProcessTransDate(assessment.getAssesmentDate());
//				anx.setProcessId("P01103");
//			
//
//				anx.setStatus("A");
//				anx.setCreatedBy(user);
//				anx.setCreatedDate(new Date());
//				
//
//				anx.setTaxApp(String.valueOf(assessment.getTaxApplicable()));
//				anx.setSrvManualFlag("N");
//				anx.setCriteria("CNTR");
//				anx.setRangeFrom(zero);
//				anx.setRangeTo(zero);
//
//				anx.setRangeType("CNTR");
//				anx.setExRate(zero);
//
//				if (("Y".equals(assessment.getIgst()))
//						|| ("Y".equals(assessment.getCgst()) && "Y".equals(assessment.getSgst()))) {
//					anx.setTaxPerc(anx.getTaxPerc());
//				} else {
//					anx.setTaxPerc(zero);
//				}				
//				
//			}	
//			
//			cfinvsrvanxrepo.saveAll(cfInvsrvanxData);
//			
//			
//			return ResponseEntity.ok(true);	
//		}catch(Exception e)
//		{
//		System.out.println("Error in Add Service ContainerWise " + e);
//		 return ResponseEntity.status(HttpStatus.CONFLICT).body("Error in Add Service ContainerWise: " + e.getMessage());		}		
//	}
//
	
	
	
	

	public ResponseEntity<?> saveAddServiceServiceWise(String companyId, String branchId, String user,
			Map<String, Object> resultList) {

		try {

			AssessmentSheet assessment = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("assesmentSheet")), AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<Cfinvsrvanx> cfInvsrvanxData = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
					new TypeReference<List<Cfinvsrvanx>>() {
					});

			BigDecimal zero = BigDecimal.ZERO;

			for (Cfinvsrvanx anx : cfInvsrvanxData) {

				Optional<BigDecimal> highestSRLNoOptional = exportInvoiceRepo.getHighestSrlNoByContainerNo(companyId,
						branchId, assessment.getProfitcentreId(), assessment.getAssesmentId());
				BigDecimal highestSRLNo = highestSRLNoOptional.orElse(BigDecimal.ZERO);
				BigDecimal highestSRLNoNew = highestSRLNo.add(BigDecimal.ONE);

				anx.setCompanyId(companyId);
				anx.setBranchId(branchId);
				anx.setErpDocRefNo(assessment.getSbTransId());
				anx.setProcessTransId(assessment.getAssesmentId());
				anx.setSrlNo(highestSRLNoNew);
				anx.setDocRefNo(assessment.getSbNo());
				anx.setIgmLineNo("");
				anx.setProfitcentreId(assessment.getProfitcentreId());
				anx.setInvoiceType("EXP");
				anx.setAddServices("Y");
				anx.setInvoiceType("EXP");
				

				anx.setLocalAmt(anx.getActualNoOfPackages());
				anx.setInvoiceAmt(anx.getActualNoOfPackages());
				anx.setCommodityDescription(assessment.getCommodityDescription());
				anx.setDiscPercentage(zero);
				anx.setDiscValue(zero);
				anx.setmPercentage(zero);
				anx.setmAmount(zero);

				anx.setPartyId(assessment.getPartyId());

				anx.setProcessTransDate(assessment.getAssesmentDate());
				anx.setProcessId("P01103");

				anx.setStatus("A");
				anx.setCreatedBy(user);
				anx.setCreatedDate(new Date());

				anx.setTaxApp(String.valueOf(assessment.getTaxApplicable()));
				anx.setSrvManualFlag("N");
				anx.setCriteria("CNTR");
				anx.setRangeFrom(zero);
				anx.setRangeTo(zero);

				anx.setRangeType("CNTR");
				anx.setExRate(zero);

				if (("Y".equals(assessment.getIgst()))
						|| ("Y".equals(assessment.getCgst()) && "Y".equals(assessment.getSgst()))) {
					anx.setTaxPerc(anx.getTaxPerc());
				} else {
					anx.setTaxPerc(zero);
				}

			}

			cfinvsrvanxrepo.saveAll(cfInvsrvanxData);

			return ResponseEntity.ok(true);
		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error in Add Service ContainerWise: " + e.getMessage());
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	
//	public ResponseEntity<?> getAllContainerListOfAssessMentSheet(String companyId, String branchId, String assesmentId, String profiCentreId) {
//		List<Cfinvsrvanx> assessmentSheetList = exportInvoiceRepo.getAllContainerListOfAssessMentSheet(companyId, branchId, profiCentreId, assesmentId);
//		
//		
//		
//		
//		return ResponseEntity.ok(assessmentSheetList);
//	}
//	
	
	

	public ResponseEntity<?> getAllContainerListOfAssessMentSheet(String companyId, String branchId, String assesmentId,
			String profiCentreId, String invoiceType) {
		
		if("Export Cargo".equalsIgnoreCase(invoiceType))
		{
			List<Cfinvsrvanx> allContainerListOfAssessMentSheetBackToTown = exportInvoiceRepo.getAllContainerListOfAssessMentSheetBackToTown(companyId,
											branchId, profiCentreId, assesmentId);
			
			return ResponseEntity.ok(allContainerListOfAssessMentSheetBackToTown);
		}
		else {
		List<Cfinvsrvanx> assessmentSheetList = exportInvoiceRepo.getAllContainerListOfAssessMentSheet(companyId,
				branchId, profiCentreId, assesmentId);
		return ResponseEntity.ok(assessmentSheetList);
		
		}
	}

	

	
	public ResponseEntity<?> getAllContainerDetailsOfAssesmentId(String companyId, String branchId, String assesmentId,
			String profitCentreId, String invoiceType) {
		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = new ArrayList<>();

		if("Export Cargo".equalsIgnoreCase(invoiceType))
		{
			selectedExportAssesmentSheetContainerData = exportInvoiceRepo
					.getSelectedExportAssesmentSheetBackToTown(companyId, branchId, profitCentreId, assesmentId);
			
		}else
		{
			selectedExportAssesmentSheetContainerData = exportInvoiceRepo
					.getSelectedExportAssesmentSheetContainerData(companyId, branchId, profitCentreId, assesmentId);
		}	
		

		AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
		AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);

		selectedExportAssesmentSheetContainerData.stream().forEach(r -> {
			BigDecimal rate = new BigDecimal(String.valueOf(r.getRates()));
			BigDecimal taxPerc = new BigDecimal(String.valueOf(r.getTaxPerc()));

			BigDecimal taxAmt = (rate.multiply(taxPerc)).divide(new BigDecimal(100), RoundingMode.HALF_UP);
			totalRateWithoutTax.set(totalRateWithoutTax.get().add(rate));
			totalRateWithTax.set(totalRateWithTax.get().add(rate.add(taxAmt)));
		});

		// Get the final totals
		BigDecimal finaltotalRateWithoutTax = BigDecimal.ZERO;
		BigDecimal finaltotalRateWithTax = BigDecimal.ZERO;

		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(companyId, branchId);

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

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
		finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);
		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);

		return ResponseEntity.ok(finalResult);
	}

	
	
	
	
	
//	
//	public ResponseEntity<?> getAllContainerDetailsOfAssesmentId(String companyId, String branchId, String assesmentId,	String profitCentreId) {
//		
//		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
//																						.getSelectedExportAssesmentSheetContainerData(companyId, branchId, profitCentreId, assesmentId);		
//		
//		
//		
//		AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
//		AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);
//
//		selectedExportAssesmentSheetContainerData.stream().forEach(r -> {
//			BigDecimal rate = new BigDecimal(String.valueOf(r.getRates()));
//			BigDecimal taxPerc = new BigDecimal(String.valueOf(r.getTaxPerc()));
//
//			BigDecimal taxAmt = (rate.multiply(taxPerc)).divide(new BigDecimal(100), RoundingMode.HALF_UP);
//			totalRateWithoutTax.set(totalRateWithoutTax.get().add(rate));
//			totalRateWithTax.set(totalRateWithTax.get().add(rate.add(taxAmt)));
//		});
//
//		// Get the final totals
//		BigDecimal finaltotalRateWithoutTax = BigDecimal.ZERO;
//		BigDecimal finaltotalRateWithTax = BigDecimal.ZERO;
//
//		String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(companyId, branchId);
//
//		if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
//			finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(3, RoundingMode.HALF_UP);
//			finaltotalRateWithTax = totalRateWithTax.get().setScale(3, RoundingMode.HALF_UP);
//		} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
//			finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(0, RoundingMode.HALF_UP);
//			finaltotalRateWithTax = totalRateWithTax.get().setScale(0, RoundingMode.HALF_UP);
//		} else {
//			finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(3, RoundingMode.HALF_UP);
//			finaltotalRateWithTax = totalRateWithTax.get().setScale(3, RoundingMode.HALF_UP);
//		}
//
//		
//		Map<String, Object> finalResult = new HashMap<>();
//		finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
//		finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);
//		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);
//		
//		
//
//		
//		
//		return ResponseEntity.ok(finalResult);
//	}
//
//	
	
	
	
	
	
	
	
	
	
	
	
	

	public ResponseEntity<?> saveAddServiceContainerWise(String companyId, String branchId, String user,
			Map<String, Object> resultList) {

		try {

			AssessmentSheet assessment = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("assesmentSheet")), AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			List<Cfinvsrvanx> cfInvsrvanxData = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
					new TypeReference<List<Cfinvsrvanx>>() {
					});

			List<Cfinvsrvanx> filteredData = cfInvsrvanxData.stream()
					.filter(detail -> "Y".equalsIgnoreCase(detail.getAddServices())).collect(Collectors.toList());
			BigDecimal zero = BigDecimal.ZERO;

			Cfinvsrvanx object1 = filteredData.get(0);
			
//			System.out.println("object1 \n" + object1);

			List<Cfinvsrvanx> saveToData = new ArrayList<>();
			for (Cfinvsrvanx anx : filteredData) {

				boolean existsByAssesmentIdAndSrlNo = exportInvoiceRepo.existsByAssesmentIdAndSrlNo(companyId, branchId,
						assessment.getProfitcentreId(), assessment.getAssesmentId(), anx.getSrlNo(), assessment.getAssesmentLineNo(), anx.getServiceId());

				if (existsByAssesmentIdAndSrlNo) {
					
					
					
					
					

					exportInvoiceRepo.updateCfinvsrvanx(companyId, branchId, assessment.getProfitcentreId(),
							assessment.getAssesmentId(), anx.getSrlNo(), anx.getExecutionUnit(),
							anx.getExecutionUnit1(), anx.getRate(), anx.getActualNoOfPackages());

				} else {

					Optional<BigDecimal> highestSRLNoOptional = exportInvoiceRepo.getHighestSrlNoByContainerNo(companyId,
							branchId, assessment.getProfitcentreId(), assessment.getAssesmentId());
					BigDecimal highestSRLNo = highestSRLNoOptional.orElse(BigDecimal.ZERO);
					BigDecimal highestSRLNoNew = highestSRLNo.add(BigDecimal.ONE);
					
					
					System.out.println("anx \n" + anx);
					
					anx.setSrlNo(highestSRLNoNew);
					anx.setCompanyId(companyId);
					anx.setBranchId(branchId);
					anx.setErpDocRefNo(assessment.getSbTransId());
					anx.setProcessTransId(assessment.getAssesmentId());

					anx.setDocRefNo(assessment.getSbNo());
					anx.setIgmLineNo("");
					anx.setProfitcentreId(assessment.getProfitcentreId());
					anx.setInvoiceType("EXP");

//				anx.setActualNoOfPackages(c.getRates());
					anx.setLocalAmt(anx.getActualNoOfPackages());
					anx.setInvoiceAmt(anx.getActualNoOfPackages());
					anx.setCommodityDescription(assessment.getCommodityDescription());
					anx.setDiscPercentage(zero);
					anx.setDiscValue(zero);
					anx.setmPercentage(zero);
					anx.setmAmount(zero);

//				anx.setAcCode(c.getAcCode());

					anx.setProcessTransDate(assessment.getAssesmentDate());
					anx.setProcessId("P01103");
					anx.setPartyId(assessment.getPartyId());

//				anx.setGateOutDate(c.getGateoutDate());
//				anx.setStartDate(c.getGateInDate());
//				anx.setInvoiceUptoDate(c.getInvoiceDate());
//				anx.setInvoiceUptoWeek(c.getInvoiceDate());
					anx.setStatus("A");
					anx.setCreatedBy(user);
					anx.setCreatedDate(new Date());

					anx.setTaxApp(String.valueOf(assessment.getTaxApplicable()));
					anx.setSrvManualFlag("N");
					anx.setCriteria("CNTR");
					anx.setRangeFrom(zero);
					anx.setRangeTo(zero);
//				anx.setGateOutId(c.getGateOutId());
//				anx.setGatePassNo(c.getGatePassNo());
					anx.setRangeType("CNTR");
//				anx.setTaxId(c.getTaxId());
					anx.setExRate(zero);

					
					if (("Y".equals(assessment.getIgst()))
							|| ("Y".equals(assessment.getCgst()) && "Y".equals(assessment.getSgst()))) {
						anx.setTaxPerc(anx.getTaxPerc());
					} else {
						anx.setTaxPerc(zero);
					}
					saveToData.add(anx);
				}
			}

			cfinvsrvanxrepo.saveAll(saveToData);

			List<Cfinvsrvanx> cfinvSrvList = exportInvoiceRepo.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId,
					assessment.getProfitcentreId(), assessment.getAssesmentId(), object1.getLineNo());

			return ResponseEntity.ok(cfinvSrvList);
		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return null;
		}
	}

	
	
	
	
	
	
	
	
//	
//	public ResponseEntity<?> saveAddServiceContainerWise(String companyId, String branchId,	String user,Map<String, Object> resultList) {
//		
//		try {			
//			
//			AssessmentSheet assessment = objectMapper.readValue(objectMapper.writeValueAsString(resultList.get("assesmentSheet")),
//					AssessmentSheet.class);
//
//			if (assessment == null) {
//				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
//			}
//
//			List<Cfinvsrvanx> cfInvsrvanxData = objectMapper.readValue(
//					objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
//					new TypeReference<List<Cfinvsrvanx>>() {
//					});
//
//			List<Cfinvsrvanx> filteredData = cfInvsrvanxData.stream()
//			        .filter(detail -> "Y".equalsIgnoreCase(detail.getAddServices()))
//			        .collect(Collectors.toList());
//			BigDecimal zero = BigDecimal.ZERO;
//			
//			Cfinvsrvanx object1 = filteredData.get(0);
//			
//			List<Cfinvsrvanx> saveToData = new ArrayList<>();
//			for(Cfinvsrvanx anx : filteredData)
//			{
//				
//				
//				boolean existsByAssesmentIdAndSrlNo = exportInvoiceRepo.existsByAssesmentIdAndSrlNo(companyId, branchId, assessment.getProfitcentreId(), assessment.getAssesmentId(), anx.getSrlNo(), anx.getContainerNo());
//				
//				if(existsByAssesmentIdAndSrlNo) {		
//					
//					exportInvoiceRepo.updateCfinvsrvanx(companyId, branchId, assessment.getProfitcentreId(), assessment.getAssesmentId(), anx.getSrlNo(), anx.getExecutionUnit(), anx.getExecutionUnit1(), anx.getRate(), anx.getActualNoOfPackages(), anx.getContainerNo());
//					
//				}else {
//					
//						
//				
//				anx.setCompanyId(companyId);
//				anx.setBranchId(branchId);
//				anx.setErpDocRefNo(assessment.getSbTransId());
//				anx.setProcessTransId(assessment.getAssesmentId());
//				
//				anx.setDocRefNo(assessment.getSbNo());
//				anx.setIgmLineNo("");
//				anx.setProfitcentreId(assessment.getProfitcentreId());
//				anx.setInvoiceType("EXP");
//				
//				
////				anx.setActualNoOfPackages(c.getRates());
//				anx.setLocalAmt(anx.getActualNoOfPackages());
//				anx.setInvoiceAmt(anx.getActualNoOfPackages());
//				anx.setCommodityDescription(assessment.getCommodityDescription());
//				anx.setDiscPercentage(zero);
//				anx.setDiscValue(zero);
//				anx.setmPercentage(zero);
//				anx.setmAmount(zero);
//				
////				anx.setAcCode(c.getAcCode());
//				
//				anx.setProcessTransDate(assessment.getAssesmentDate());
//				anx.setProcessId("P01103");
//				anx.setPartyId(assessment.getPartyId());
//			
////				anx.setGateOutDate(c.getGateoutDate());
////				anx.setStartDate(c.getGateInDate());
////				anx.setInvoiceUptoDate(c.getInvoiceDate());
////				anx.setInvoiceUptoWeek(c.getInvoiceDate());
//				anx.setStatus("A");
//				anx.setCreatedBy(user);
//				anx.setCreatedDate(new Date());
//				
//
//				anx.setTaxApp(String.valueOf(assessment.getTaxApplicable()));
//				anx.setSrvManualFlag("N");
//				anx.setCriteria("CNTR");
//				anx.setRangeFrom(zero);
//				anx.setRangeTo(zero);
////				anx.setGateOutId(c.getGateOutId());
////				anx.setGatePassNo(c.getGatePassNo());
//				anx.setRangeType("CNTR");
////				anx.setTaxId(c.getTaxId());
//				anx.setExRate(zero);
//
//				if (("Y".equals(assessment.getIgst()))
//						|| ("Y".equals(assessment.getCgst()) && "Y".equals(assessment.getSgst()))) {
//					anx.setTaxPerc(anx.getTaxPerc());
//				} else {
//					anx.setTaxPerc(zero);
//				}				
//				saveToData.add(anx);				
//				}
//			}	
//			
//			cfinvsrvanxrepo.saveAll(saveToData);
//			
//			List<Cfinvsrvanx> cfinvSrvList = exportInvoiceRepo.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId, assessment.getProfitcentreId(), assessment.getAssesmentId(), object1.getContainerNo());
//			
//			return ResponseEntity.ok(cfinvSrvList);	
//		}catch(Exception e)
//		{
//		System.out.println("Error in Add Service ContainerWise " + e);
//			return null;
//		}		
//	}
//	
	
	
	
	public ResponseEntity<?> searchServicesForAddService(String companyId, String branchId, String searchValue, String commodity, String containerSize, String cargoType, AssessmentSheet assessment) {
		
		List<String> conSize1 = new ArrayList<>();
		conSize1.add("ALL");
		conSize1.add(("22".equals(containerSize)) ? "20" : containerSize);

		List<String> cargoTypeList = new ArrayList<>();
		cargoTypeList.add("ALL");
		cargoTypeList.add(cargoType);
		
		List<String> commodityList = new ArrayList<>();
		commodityList.add("ALL");
		commodityList.add(commodity);
		
		
		
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

		
		String tariffNo = exportInvoiceRepo.getTarrifNo(companyId, branchId, partyIdList, impIdList, chaIdList,
				fwIdList, assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
				assessment.getOnAccountOf());
		
		List<String> tariffList = new ArrayList<>();
		tariffList.add(tariffNo);
		tariffList.add("CFS1000001");
				
		List<Cfinvsrvanx> cfinvSrvList = exportInvoiceRepo.searchServicesForAddService(companyId, branchId, searchValue, tariffList, conSize1, commodityList, cargoTypeList);
		
		List<Map<String, Object>> toSendGetParties = cfinvSrvList.stream().map(row -> {

			Map<String, Object> map = new HashMap<>();
			map.put("value", row.getServiceId());
			map.put("label", row.getServiceName() + " " + row.getRate());
			map.put("serviceUnit", row.getServiceUnit());
			map.put("serviceUnit1", row.getServiceUnit1());
			map.put("rate", row.getRate());
			map.put("currencyId", row.getCurrencyId());
			map.put("serviceId", row.getServiceId());
			map.put("woNo", row.getWoNo());
			map.put("woAmndNo", row.getWoAmndNo());
			map.put("rangeType", row.getRangeType());
			map.put("srNo", row.getSrNo());			
			map.put("serviceName", row.getServiceName());
			map.put("taxId", row.getTaxId());
			map.put("taxPerc", row.getTaxPerc());
			map.put("acCode", row.getAcCode());
			
			return map;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(toSendGetParties);
	}

//	public ResponseEntity<?> getAllCfInvSrvAnxListByAssesmentId(String companyId, String branchId, String assesmentId,
//			String assesmentLineNo, String containerNo, String profiCentreId) {
//		List<Cfinvsrvanx> cfinvSrvList = exportInvoiceRepo.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId, profiCentreId, assesmentId, containerNo);
//
//		return ResponseEntity.ok(cfinvSrvList);
//	}

	
	public ResponseEntity<?> getAllCfInvSrvAnxListByAssesmentId(String companyId, String branchId, String assesmentId,
			String assesmentLineNo, String containerNo, String profiCentreId) {
		List<Cfinvsrvanx> cfinvSrvList = exportInvoiceRepo.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId,
				profiCentreId, assesmentId, assesmentLineNo);

		return ResponseEntity.ok(cfinvSrvList);
	}
	
	
	public List<ExportContainerAssessmentData> searchAssesmentBySelectedValue(String companyId, String branchId,
			String profitcentreId, String operation, String searchValue, String invoiceType)
			throws CloneNotSupportedException {

		ExportContainerAssessmentData result = null;
		List<ExportContainerAssessmentData> assessmentDataList = new ArrayList<>();

		String sbNo = null;
		Date cartingDate = null;
		String cartingId = "";
		String movReqType = "";
		String containerNo = "";
		String sbTransId = "";

		if ("container".equalsIgnoreCase(operation)) {
			containerNo = searchValue;

			List<String> sbNos = exportInvoiceRepo.getDistinctSbNoForContainerNew(companyId, branchId, profitcentreId, containerNo);
			if (sbNos != null && !sbNos.isEmpty()) {
				sbNo = sbNos.get(0);
			}
			

		} else if ("sb".equalsIgnoreCase(operation)) {

			sbNo = searchValue;

		}
		
		invoiceType = invoiceType.trim();
		System.out.println("invoiceType + " + invoiceType);
	
		
		
		List<ExportContainerAssessmentData> resultList = new ArrayList<>();
		System.out.println("1");
		if ("Export Container".equalsIgnoreCase(invoiceType)) {
			
			System.out.println("2");
			
			List<String> movMentTypes = exportInvoiceRepo.getDistinctMovementType(companyId, branchId, profitcentreId,
					sbNo);

			if (movMentTypes != null && !movMentTypes.isEmpty()) {
				movReqType = movMentTypes.get(0);
			}


			if ("PORTRN".equalsIgnoreCase(movReqType)) {
				System.out.println("3");

				resultList = exportInvoiceRepo.getContainerAssessmentDataPortReturn(companyId, branchId, profitcentreId,
						containerNo, sbNo, movReqType);

			} else {
				System.out.println("4");
				resultList = exportInvoiceRepo.getContainerAssessmentData(companyId, branchId, profitcentreId, containerNo, sbNo, movReqType);
			}

		} else if ("Export Cargo Carting".equalsIgnoreCase(invoiceType)) {
			System.out.println("5");
			resultList = exportInvoiceRepo.getExportDetailsCartingDetails(companyId, branchId, sbNo, profitcentreId);

		} else if ("Export Cargo".equalsIgnoreCase(invoiceType)) {
			System.out.println("7");
			
			resultList = exportInvoiceRepo.getContainerAssessmentDataBackToTown(companyId, branchId, profitcentreId, sbNo);
		}

		System.out.println("8");
		if (resultList != null && !resultList.isEmpty()) {
			result = resultList.get(0);
		}
		sbTransId = result.getSbTransId();

		result.setMovementType(movReqType);

		List<Object[]> containerDetailsForContainerInvoice = new ArrayList<>();

		if ("Export Container".equalsIgnoreCase(invoiceType)) {

			if ("CLP".equalsIgnoreCase(movReqType)) {
				containerDetailsForContainerInvoice = exportInvoiceRepo.findContainerDetailsForContainerInvoice(
						companyId, branchId, result.getSbTransId(), movReqType);
			} else if ("BUFFER".equalsIgnoreCase(movReqType) || "ONWH".equalsIgnoreCase(movReqType)) {
				containerDetailsForContainerInvoice = exportInvoiceRepo
						.findContainerDetailsForContainerInvoiceBufferAndONWH(companyId, branchId,
								result.getSbTransId(), movReqType);
			} else if ("PORTRN".equalsIgnoreCase(movReqType)) {
				containerDetailsForContainerInvoice = exportInvoiceRepo
						.findContainerDetailsForContainerInvoicePortReturn(companyId, branchId, result.getSbTransId(),
								movReqType);
			}
		} else if ("Export Cargo Carting".equalsIgnoreCase(invoiceType)) {
			containerDetailsForContainerInvoice = exportInvoiceRepo.getExportDetailsCartingDetailsList(companyId, branchId, sbTransId);

		} else if ("Export Cargo".equalsIgnoreCase(invoiceType)) {
			
			

		}

		if(!"Export Cargo".equalsIgnoreCase(invoiceType))
		{
		Object[] cartingDetailsOld = exportInvoiceRepo.getCartingDateAndId(companyId, branchId, result.getSbTransId(),
				sbNo);

		Object[] cartingDetails = (Object[]) cartingDetailsOld[0];

		if (cartingDetails != null && cartingDetails.length >= 2) {
			cartingDate = cartingDetails[0] != null ? (Date) cartingDetails[0] : null;
			cartingId = cartingDetails[1] != null ? cartingDetails[1].toString() : null;
		}

		result.setCartingDate(cartingDate);
		result.setCartingTransId(cartingId);
		}
		
if("Export Container".equalsIgnoreCase(invoiceType)) {
		for (Object[] data : containerDetailsForContainerInvoice) {
			ExportContainerAssessmentData newData = (ExportContainerAssessmentData) result.clone();

			newData.setContainerNo(data[0] != null ? data[0].toString() : null);
			newData.setContainerSize(data[1] != null ? data[1].toString() : null);
			newData.setContainerType(data[2] != null ? data[2].toString() : null);
			newData.setStuffTallyDate(data[3] != null ? (Date) data[3] : null);
			newData.setContainerGrossWeight(data[4] != null ? (BigDecimal) data[4] : null);

			newData.setTypeOfContainer(data[5] != null ? data[5].toString() : null);
			newData.setMovementDate(data[6] != null ? (Date) data[6] : null);
			newData.setGateInDate(data[7] != null ? (Date) data[7] : null);
			newData.setStuffTallyId(data[8] != null ? data[8].toString() : null);

			newData.setCargoWeight(data[9] != null ? new BigDecimal(data[9].toString()) : BigDecimal.ZERO);

//			newData.setCargoWeight(data[9] != null ? (BigDecimal) data[9] : null);
			newData.setGateOutDate(data[10] != null ? (Date) data[10] : null);
			newData.setSsrTransId(data[11] != null ? data[11].toString() : null);

			if (!"PORTRN".equalsIgnoreCase(movReqType)) {

				// Map the concatenated ServiceDetails string into a list of ssrDetail objects
				if (data[12] != null) {
					String serviceDetailsStr = data[12].toString();
					List<ssrDetail> ssrDetails = new ArrayList<>();

					for (String detail : serviceDetailsStr.split(",")) {
						String[] parts = detail.split("~");
						if (parts.length == 4) {
							ssrDetail ssrDetail = new ssrDetail(parts[0], // serviceId
									new BigDecimal(parts[1]), // rate
									parts[2], // srlNo
									parts[3] // srNo
							);
							ssrDetails.add(ssrDetail);
						}
					}
					newData.setSsrDetail(ssrDetails);
				}

			}
			newData.setMovementReqId(data[13] != null ? data[13].toString() : null);
			newData.setHoldStatus(data[14] != null ? data[14].toString() : null);
			newData.setForceEntryFlag(data[15] != null ? data[15].toString() : null);
			newData.setContainerInvoiceType(data[16] != null ? data[16].toString() : null);

			// Add the mapped data to the list
			assessmentDataList.add(newData);
		}
}else if ("Export Cargo Carting".equalsIgnoreCase(invoiceType)) 
{

	for (Object[] data : containerDetailsForContainerInvoice) {
	
	ExportContainerAssessmentData newData = (ExportContainerAssessmentData) result.clone();

//	newData.setSbLineNo(data[0] != null ? data[0].toString() : null);
	
	newData.setSbLineNo(data[0] != null ? String.valueOf(data[0]) : null);
	newData.setAssesmentLineNo(data[0] != null ? String.valueOf(data[0]) : null);
	newData.setCommodity(data[1] != null ? data[1].toString() : null);
	newData.setCommodityDescription(data[1] != null ? data[1].toString() : null);
	
	newData.setNewCommodity(data[2] != null ? data[2].toString() : null);
	newData.setNoOfPackages(data[3] != null ? (BigDecimal) data[3] : BigDecimal.ZERO);
	newData.setGrossWeight(data[4] != null ? (BigDecimal) data[4] : BigDecimal.ZERO);
	
	newData.setTypeOfPackage(data[5] != null ? data[5].toString() : null);
	newData.setCargoType(data[6] != null ? data[6].toString() : null);
	
	
	assessmentDataList.add(newData);
	
}
	
}
else if("Export Cargo".equalsIgnoreCase(invoiceType)) 
{	
	assessmentDataList = resultList;

}
		
		
		
		
		
		return assessmentDataList;
	}

	

//	public List<ExportContainerAssessmentData> searchAssesmentBySelectedValue(String companyId, String branchId,
//			String profitcentreId, String operation, String searchValue) throws CloneNotSupportedException {
//
//		ExportContainerAssessmentData result = null;
//		List<ExportContainerAssessmentData> assessmentDataList = new ArrayList<>();
//
//		String sbNo = null;
//		Date cartingDate = null;
//		String cartingId = "";
//		String movReqType = "";
//		String containerNo = "";
//
//		if ("container".equals(operation)) {
//			containerNo = searchValue;
//
//			List<String> sbNos = exportInvoiceRepo.getDistinctSbNoForContainerNew(companyId, branchId, profitcentreId, containerNo);
//			if (sbNos != null && !sbNos.isEmpty()) {
//				sbNo = sbNos.get(0);
//			}
//			
////		 sbNo = exportInvoiceRepo.getDistinctSbNoForContainerNew(companyId, branchId, profitcentreId, containerNo);
//			
//			
//		} else if ("sb".equals(operation)) {
//
//			sbNo = searchValue;
//
//		}
//
//		List<String> movMentTypes = exportInvoiceRepo.getDistinctMovementType(companyId, branchId, profitcentreId, sbNo);
//
//		if (movMentTypes != null && !movMentTypes.isEmpty()) {
//			movReqType = movMentTypes.get(0);
//		}
//
//		List<ExportContainerAssessmentData> resultList = new ArrayList<>();
//		
//		
//		if("PORTRN".equalsIgnoreCase(movReqType)) {
//			
//			resultList = exportInvoiceRepo.getContainerAssessmentDataPortReturn(companyId,
//													branchId, profitcentreId, containerNo, sbNo, movReqType);
//			
//		}else
//		{
//			resultList = exportInvoiceRepo.getContainerAssessmentData(companyId,
//					branchId, profitcentreId, containerNo, sbNo, movReqType);			
//		}
//		
//		
//		
//
//		if (resultList != null && !resultList.isEmpty()) {
//			result = resultList.get(0);
//		}
//
//		result.setMovementType(movReqType);
//		
//		
//		
//		
//		
//		List<Object[]> containerDetailsForContainerInvoice = new ArrayList<>();
//		
//		if("CLP".equalsIgnoreCase(movReqType))
//		{
//			containerDetailsForContainerInvoice = exportInvoiceRepo.findContainerDetailsForContainerInvoice(companyId, branchId, result.getSbTransId(), movReqType);	
//		}else if("BUFFER".equalsIgnoreCase(movReqType) || "ONWH".equalsIgnoreCase(movReqType)){
//			containerDetailsForContainerInvoice = exportInvoiceRepo.findContainerDetailsForContainerInvoiceBufferAndONWH(companyId, branchId, result.getSbTransId(), movReqType);
//		}else if("PORTRN".equalsIgnoreCase(movReqType))
//		{
//			containerDetailsForContainerInvoice = exportInvoiceRepo.findContainerDetailsForContainerInvoicePortReturn(companyId, branchId, result.getSbTransId(), movReqType);		
//		}
//		
//		Object[] cartingDetailsOld = exportInvoiceRepo.getCartingDateAndId(companyId, branchId, result.getSbTransId(),
//				sbNo);
//		
//		Object[] cartingDetails = (Object[]) cartingDetailsOld[0];
//
//		if (cartingDetails != null && cartingDetails.length >= 2) {
//			cartingDate = cartingDetails[0] != null ? (Date) cartingDetails[0] : null;
//			cartingId = cartingDetails[1] != null ? cartingDetails[1].toString() : null;
//		}
//		
//		
//		result.setCartingDate(cartingDate);
//		result.setCartingTransId(cartingId);
//
//		for (Object[] data : containerDetailsForContainerInvoice) {
//			ExportContainerAssessmentData newData = (ExportContainerAssessmentData) result.clone();
//
//			
//			
//			newData.setContainerNo(data[0] != null ? data[0].toString() : null);
//			newData.setContainerSize(data[1] != null ? data[1].toString() : null);
//			newData.setContainerType(data[2] != null ? data[2].toString() : null);
//			newData.setStuffTallyDate(data[3] != null ? (Date) data[3] : null);
//			newData.setContainerGrossWeight(data[4] != null ? (BigDecimal) data[4] : null);
//
//			newData.setTypeOfContainer(data[5] != null ? data[5].toString() : null);
//			newData.setMovementDate(data[6] != null ? (Date) data[6] : null);
//			newData.setGateInDate(data[7] != null ? (Date) data[7] : null);
//			newData.setStuffTallyId(data[8] != null ? data[8].toString() : null);
//
//			newData.setCargoWeight(data[9] != null ?  new BigDecimal(data[9].toString()) : BigDecimal.ZERO);
//
////			newData.setCargoWeight(data[9] != null ? (BigDecimal) data[9] : null);
//			newData.setGateOutDate(data[10] != null ? (Date) data[10] : null);
//			newData.setSsrTransId(data[11] != null ? data[11].toString() : null);
//
//			if(!"PORTRN".equalsIgnoreCase(movReqType))
//			{
//			
//			// Map the concatenated ServiceDetails string into a list of ssrDetail objects
//			if (data[12] != null) {
//				String serviceDetailsStr = data[12].toString();
//				List<ssrDetail> ssrDetails = new ArrayList<>();
//
//				for (String detail : serviceDetailsStr.split(",")) {
//					String[] parts = detail.split("~");
//					if (parts.length == 4) {
//						ssrDetail ssrDetail = new ssrDetail(parts[0], // serviceId
//								new BigDecimal(parts[1]), // rate
//								parts[2], // srlNo
//								parts[3] // srNo
//						);
//						ssrDetails.add(ssrDetail);
//					}
//				}
//				newData.setSsrDetail(ssrDetails);
//			}
//
//			}
//			newData.setMovementReqId(data[13] != null ? data[13].toString() : null);
//			newData.setHoldStatus(data[14] != null ? data[14].toString() : null);
//			newData.setForceEntryFlag(data[15] != null ? data[15].toString() : null);
//			newData.setContainerInvoiceType(data[16] != null ? data[16].toString() : null);
//
//			
//			
//			System.out.println("newData : " + newData);
//			// Add the mapped data to the list
//			assessmentDataList.add(newData);
//		}
//
//		return assessmentDataList;
//	}

//	Export Container
	
	
	
	
	
	
	
	
	

	public List<Map<String, Object>> getSbListOrContainer(String companyId, String branchId, String searchValue,
			String type, String profitCentreId, String invoiceType) {
		List<String> listToSend = new ArrayList<>();
		List<Map<String, Object>> toSendGetList = new ArrayList<>();

		if ("container".equals(type)) {
			listToSend = exportInvoiceRepo.getDistinctContainerNoNew(companyId, branchId, profitCentreId, searchValue);
		} else {
			if ("Export Container".equalsIgnoreCase(invoiceType)) {
				listToSend = exportInvoiceRepo.getDistinctSbNoSearchList(companyId, branchId, profitCentreId,
						searchValue);
			} else if ("Export Cargo Carting".equalsIgnoreCase(invoiceType)) {
				listToSend = exportInvoiceRepo.getDistinctSbNoSearchCartingList(companyId, branchId, profitCentreId,
						searchValue);

			} else if ("Export Cargo".equalsIgnoreCase(invoiceType)) {
				listToSend = exportInvoiceRepo.getDistinctSbNoSearchBackToTownList(companyId, branchId, profitCentreId, searchValue);
			}

		}

		toSendGetList = listToSend.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put("value", row);
			map.put("label", row);
			return map;
		}).collect(Collectors.toList());

		return toSendGetList;
	}

	
	
	
	
	
	
	
	
	
	
	
//
//	public List<Map<String, Object>> getSbListOrContainer(String companyId, String branchId, String searchValue,
//			String type, String profitCentreId) {
//		List<String> listToSend = new ArrayList<>();
//		List<Map<String, Object>> toSendGetList = new ArrayList<>();
//
//		if ("container".equals(type)) {
//			listToSend = exportInvoiceRepo.getDistinctContainerNoNew(companyId, branchId, profitCentreId, searchValue);
//		} else {
//			listToSend = exportInvoiceRepo.getDistinctSbNoSearchList(companyId, branchId, profitCentreId, searchValue);
//		}
//
//		toSendGetList = listToSend.stream().map(row -> {
//			Map<String, Object> map = new HashMap<>();
//			map.put("value", row);
//			map.put("label", row);
//			return map;
//		}).collect(Collectors.toList());
//
//		return toSendGetList;
//	}

	public ResponseEntity<?> getPartyByTypeValue(String companyId, String branchId, String searchValue, String type) {

		List<Map<String, Object>> getParties = exportInvoiceRepo.getPartyByTypeWithAddress(companyId, branchId,
				searchValue, type);

		List<Map<String, Object>> toSendGetParties = getParties.stream().map(row -> {

			Object partyName = row.get("partyName");
			Object srNo = row.get("srNo");
			Object partyId = row.get("partyId");

			Map<String, Object> map = new HashMap<>();
			map.put("value", partyId + " " + srNo);
			map.put("label", partyName);
			map.put("address1", row.get("address1"));
			map.put("address2", row.get("address2"));
			map.put("address3", row.get("address3"));
			map.put("srNo", row.get("srNo"));
			map.put("gstNo", row.get("gstNo"));
			map.put("state", row.get("state"));
			map.put("iecCode", row.get("iecCode"));
			map.put("partyId", row.get("partyId"));
			map.put("partyName", row.get("partyName"));
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(toSendGetParties);
	}

	public String getFirstServiceGroup(String serviceGroup) {
		System.out.println("Original serviceGroup: " + serviceGroup);

		// Check if the serviceGroup is not null or empty
		if (serviceGroup != null && !serviceGroup.trim().isEmpty()) {
			// Split the string by comma
			String[] groups = serviceGroup.split(",");

			// Loop through the array to find the first non-empty group
			for (String group : groups) {
				if (!group.trim().isEmpty()) {
					System.out.println("First valid group: " + group.trim());
					return group.trim(); // Return the first non-empty value
				}
			}
		}

		// Return empty string if no valid group is found
		System.out.println("No valid group found. Returning empty string.");
		return "";
	}

	public BigDecimal convertToBigDecimal(String value) {
		// If the value is null or empty, return BigDecimal.ZERO
		if (value == null || value.trim().isEmpty()) {
			return BigDecimal.ZERO;
		}

		// Otherwise, convert the string to BigDecimal
		try {
			return new BigDecimal(value.trim());
		} catch (NumberFormatException e) {
			// If the string cannot be converted to BigDecimal, return BigDecimal.ZERO
			return BigDecimal.ZERO;
		}
	}

//	SAVE ASSESMENT DATA EXPORT CONTAINER

//	@Transactional
//	public ResponseEntity<?> saveExportAssesment(String cid, String bid, String user, Map<String, Object> data)
//			throws JsonMappingException, JsonProcessingException {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//
//			AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assesmentSheet")),
//					AssessmentSheet.class);
//
//			if (assessment == null) {
//				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
//			}
//
//			List<ExportContainerAssessmentData> containerData = mapper.readValue(
//					mapper.writeValueAsString(data.get("containerData")),
//					new TypeReference<List<ExportContainerAssessmentData>>() {
//					});
//
//			System.out.println(containerData);
//			if (containerData.isEmpty()) {
//				return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
//			}
//
//			if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {
//				
//				int sr = 1;
//
//				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P01103", "2024");
//
//				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));
//
//				int nextNumericNextID1 = lastNextNumericId1 + 1;
//
//				String HoldNextIdD1 = String.format("EXCM%06d", nextNumericNextID1);
//				
//				String movementType = assessment.getMovementType();
//				String mappingType = switch (movementType.toUpperCase()) {
//				    case "CLP" -> "EXP";
//				    case "BUFFER", "ONWH" -> "ONWH";
//				    case "PORTRN" -> "PORT";
//				    default -> "";
//				};
//				
//				int i = 1;
//				
//				for (ExportContainerAssessmentData con : containerData) {
//					List<ExportContainerAssessmentData> finalConData = new ArrayList<>();
//					
//					System.out.println("con " + i + " " + con.getContainerNo());
//
//					// Service Mapping
//					List<String> serviceMappingData = new ArrayList<>();
//
//					System.out.println(" -----1");
//					
//					
//					
//
//					if ("SINGLE".equalsIgnoreCase(assessment.getInvoiceCategory())) {
//						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceAll(cid, bid,
//								con.getProfitcentreId(), mappingType);
//					} else {
//
//						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
//						serviceMappingData = exportInvoiceRepo.getServicesForExportInvoiceByType(cid, bid,
//								con.getProfitcentreId(), mappingType, group);
//					}
//
//					System.out.println(" -----2");
//
//					if (serviceMappingData.isEmpty()) {
//						return new ResponseEntity<>(
//								"Service mapping not found for container no " + con.getContainerNo(),
//								HttpStatus.CONFLICT);
//					}
//
//					Set<String> combinedService = new HashSet<>();
//					combinedService.addAll(serviceMappingData);
//
//					List<String> partyIdList = new ArrayList<>();
//					partyIdList.add(assessment.getOthPartyId());
//					partyIdList.add("");
//
//					List<String> impIdList = new ArrayList<>();
//					impIdList.add(assessment.getImporterId());
//					impIdList.add("");
//
//					List<String> chaIdList = new ArrayList<>();
//					chaIdList.add(assessment.getCha());
//					chaIdList.add("");
//
//					List<String> fwIdList = new ArrayList<>();
//					fwIdList.add(assessment.getOnAccountOf());
//					fwIdList.add("");
//
//					System.out.println(" -----3");
//					String tariffNo = exportInvoiceRepo.getTarrifNo(cid, bid, partyIdList, impIdList, chaIdList,
//							fwIdList, assessment.getOthPartyId(), assessment.getImporterId(), assessment.getCha(),
//							assessment.getOnAccountOf());
//
//					con.setUpTariffNo(tariffNo);
//					System.out.println(" -----4");
//
//					// Find default service
//					List<String> defaultService = exportInvoiceRepo.getDefaultServiceId(cid, bid, con.getUpTariffNo());
//
//					System.out.println(" -----5");
//					if (!defaultService.isEmpty()) {
//						combinedService.addAll(defaultService);
//					}
//
//					// SSR service
//					List<SSRDtl> ssrServices = new ArrayList<>();
//
//					if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
//
//						ssrServices = exportInvoiceRepo.getServiceId(cid, bid, con.getSsrTransId(),
//								con.getContainerNo());
//
//						if (!ssrServices.isEmpty()) {
//							List<String> ssrServiceId = ssrServices.stream().map(SSRDtl::getServiceId)
//									.collect(Collectors.toList());
//							combinedService.addAll(ssrServiceId);
//						}
//					}
//
//					List<SSRDtl> finalSsrServices = ssrServices;
//
//					List<String> finalServices = new ArrayList<>(combinedService);
//
//					List<Object[]> finalPricing = new ArrayList<>();
//					List<String> notInPricing = new ArrayList<>();
//
//					List<String> conSize1 = new ArrayList<>();
//					conSize1.add("ALL");
//					conSize1.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());
//
//					List<String> conTypeOfCon1 = new ArrayList<>();
//					conTypeOfCon1.add("ALL");
//					conTypeOfCon1.add(con.getTypeOfContainer());
//
//					Date assData = assessment.getAssesmentDate();
//					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//					String formattedDate = formatter.format(assData);
//					Date parsedDate = formatter.parse(formattedDate);
//
//					List<Object[]> pricingData = exportInvoiceRepo.getServiceRate(cid, bid, parsedDate,
//							con.getContainerNo(), finalServices, con.getUpTariffNo(), conSize1, conTypeOfCon1);
//
//					if (!pricingData.isEmpty()) {
//						List<Object[]> remainingPricing = pricingData.stream()
//								.filter(data1 -> finalServices.contains(data1[0].toString()))
//								.collect(Collectors.toList());
//
//						notInPricing = finalServices.stream().filter(
//								service -> pricingData.stream().noneMatch(data1 -> service.equals(data1[0].toString())))
//								.collect(Collectors.toList());
//
//						finalPricing.addAll(remainingPricing);
//					} else {
//						notInPricing.addAll(finalServices);
//					}
//
//					if (!notInPricing.isEmpty()) {
//						List<String> tempPricing = notInPricing;
//						List<String> conSize2 = new ArrayList<>();
//						conSize2.add("ALL");
//						conSize2.add(("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());
//
//						List<String> conTypeOfCon2 = new ArrayList<>();
//						conTypeOfCon2.add("ALL");
//						conTypeOfCon2.add(con.getTypeOfContainer());
//
//						List<Object[]> pricingData1 = exportInvoiceRepo.getServiceRate(cid, bid, parsedDate,
//								con.getContainerNo(), notInPricing, "CFS1000001", conSize2, conTypeOfCon2);
//
//						if (!pricingData1.isEmpty()) {
//							List<Object[]> remainingPricing = pricingData1.stream()
//									.filter(data1 -> tempPricing.contains(data1[0].toString()))
//									.collect(Collectors.toList());
//
//							finalPricing.addAll(remainingPricing);
//						}
//					}
//
//					String freeDaysForExportCargoStorage = exportInvoiceRepo.getFreeDaysForExportCargoStorage(cid, bid,
//							assessment.getBillingParty());
//
//					BigDecimal freeDays = convertToBigDecimal(freeDaysForExportCargoStorage);
//					final long cargoFreeDays = freeDays != null ? freeDays.longValue() : 0L;
//
////						STARTING LOOP FOR ALL DISTINCT SERVICE IDS
//					finalPricing.stream().forEach(f -> {
//						ExportContainerAssessmentData tempAss = new ExportContainerAssessmentData();
//						CFSDay day = cfsdayrepo.getData(cid, bid);
//
//// STARTING IF SERVICE ID IS FROM SSR
//						if (con.getSsrTransId() != null && !con.getSsrTransId().isEmpty()) {
//
//// IF SSR DATA NOT EMPTY START
//							if (!finalSsrServices.isEmpty()) {
//
//								SSRDtl rate1 = finalSsrServices.stream()
//										.filter(s -> s.getServiceId().equals(String.valueOf(f[0]))).findFirst()
//										.orElse(null);
//
////									IF SSRDTL RATE EXIST
//								if (rate1 != null) {
//
//									try {
//										tempAss = (ExportContainerAssessmentData) con.clone();
//
//										Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
//												day.getEndTime());
//
//										tempAss.setInvoiceDate(invDate);
//										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
////											
//										tempAss.setmAmount(f[12] != null ? new BigDecimal(String.valueOf(f[12]))
//												: BigDecimal.ZERO);
//										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//												: BigDecimal.ZERO);
//										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//												: BigDecimal.ZERO);
//										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//										tempAss.setContainerStatus(con.getContainerStatus());
//										tempAss.setGateOutId(con.getGateOutId());
//										tempAss.setGatePassNo(con.getGatePassNo());
//										tempAss.setRates(rate1.getTotalRate());
//										tempAss.setServiceRate(rate1.getRate());
//										tempAss.setTaxPerc(
//												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//										tempAss.setTaxId(String.valueOf(f[11]));
//										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//
//										finalConData.add(tempAss);
//									} catch (CloneNotSupportedException e) {
//										e.printStackTrace();
//									}
//
//								}
//
////									IF SSRDTL RATE DOES NOT EXIST									
//								else {
//									try {
//
////											IF SLAB
//										String storageType = getFirstServiceGroup(
//												f[19] != null ? String.valueOf(f[19]) : "");
//										String serviceGrp = String.valueOf(f[14]);
////											IF STORAGE TYPE IN CRG STARTING....
//										SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//										System.out.println("storageType " + storageType + " serviceGrp " + serviceGrp);
//
//										System.out.println("----------6");
//										if ("C".equals(serviceGrp) && "CRG".equals(storageType)) {
//											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
//													BigDecimal.ZERO);
//
//											List<Object[]> exportCartingStorageDays = exportInvoiceRepo
//													.getExportCartingStorageDays(cid, bid, con.getStuffTallyId(),
//															con.getContainerNo());
//
//											for (Object[] value : exportCartingStorageDays) {
//												tempAss = (ExportContainerAssessmentData) con.clone();
//												tempAss.setFreeDays(freeDays);
//
//												Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(),
//														day.getStartTime(), day.getEndTime());
//
//												tempAss.setInvoiceDate(invDate);
//
//												String sbNo = value[0] != null ? String.valueOf(value[0]) : "";
//												Date stuffTallyDate = value[1] != null
//														? dateTimeFormat.parse(value[1].toString())
//														: null;
//												Date inGateInDate = value[2] != null
//														? dateTimeFormat.parse(value[2].toString())
//														: null;
//												Date cartingTransDate = value[5] != null
//														? dateTimeFormat.parse(value[5].toString())
//														: null;
//
//												// Retrieve weights and areas as BigDecimal
//												BigDecimal areaReleased = value[3] != null
//														? new BigDecimal(value[3].toString())
//														: null;
//												BigDecimal totalCargoWeight = value[6] != null
//														? new BigDecimal(value[6].toString())
//														: null;
//
//												// Vehicle number remains as a String
//												String vehicleNo = value[4] != null ? value[4].toString() : "";
//
//												LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//														convertToLocalDateTime(cartingTransDate), day.getStartTime());
//
//												LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//														convertToLocalDateTime(stuffTallyDate), day.getEndTime());
//
//												if ("SA".equals(String.valueOf(f[8]))) {
//													tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//													tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
////												
//													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//													tempAss.setRangeFrom(
//															f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																	: BigDecimal.ZERO);
//													tempAss.setRangeTo(
//															f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//																	: BigDecimal.ZERO);
//													tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//													tempAss.setContainerStatus(con.getContainerStatus());
//													tempAss.setGateOutId(con.getGateOutId());
//													tempAss.setGatePassNo(con.getGatePassNo());
//													tempAss.setTaxPerc(
//															(f[12] == null || String.valueOf(f[12]).isEmpty())
//																	? BigDecimal.ZERO
//																	: new BigDecimal(String.valueOf(f[12])));
//													tempAss.setTaxId(String.valueOf(f[11]));
//													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//													tempAss.setCargoSbNo(sbNo);
//
//													List<String> conSize2 = new ArrayList<>();
//													conSize2.add("ALL");
//													conSize2.add(("22".equals(con.getContainerSize())) ? "20"
//															: con.getContainerSize());
//
//													List<String> conTypeOfCon2 = new ArrayList<>();
//													conTypeOfCon2.add("ALL");
//													conTypeOfCon2.add(con.getTypeOfContainer());
//
//													List<String> commodityType = new ArrayList<>();
//													commodityType.add("ALL");
//													commodityType.add(assessment.getCommodityCode());
//
//													List<Object[]> rangeValues = cfstariffservicerepo
//															.getDataByServiceId(cid, bid, String.valueOf(f[3]),
//																	String.valueOf(f[4]), String.valueOf(f[0]),
//																	String.valueOf(f[8]), conSize2, conTypeOfCon2,
//																	commodityType, con.getContainerSize(),
//																	con.getTypeOfContainer(),
//																	assessment.getCommodityCode());
//
//													if (!rangeValues.isEmpty()) {
//
//														String unit = String.valueOf(f[1]);
//
//														if ("DAY".equals(unit)) {
//
//															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//																	destuffDateTime);
//
//															if (daysBetween == 0 && ChronoUnit.HOURS
//																	.between(gateInDateTime, destuffDateTime) > 0) {
//																daysBetween = 1;
//															}
//
//															final long daysBetween1 = daysBetween - cargoFreeDays;
//															tempAss.setExecutionUnit(String.valueOf(daysBetween));
//															tempAss.setExecutionUnit1(String.valueOf(areaReleased));
//															tempAss.setFreeDays(freeDays);
//															System.out.println("daysBetween " + daysBetween);
//
//															BigDecimal totalRate = rangeValues.stream().map(r -> {
//																int fromRange = ((BigDecimal) r[6]).intValue(); // Start
//																												// of
//																												// the
//																												// slab
//																int toRange = ((BigDecimal) r[7]).intValue(); // End of
//																												// the
//																												// slab
//																BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
//																										// the
//																										// slab
//																if (daysBetween1 >= fromRange
//																		&& daysBetween1 <= toRange) {
//																	serviceRate.set(rate); // Set the rate for the
//																							// matching
//																							// slab
//																}
//																// Calculate days in the current slab
//																long daysInSlab = Math.min(toRange, daysBetween1)
//																		- Math.max(fromRange, 1) + 1;
//
//																daysInSlab = Math.max(daysInSlab, 0);
//																return rate.multiply(new BigDecimal(daysInSlab));
//															}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//
//															if ("SM".equals(String.valueOf(f[7]))) {
//																totalRate = (totalRate.multiply(areaReleased))
//																		.setScale(3, RoundingMode.HALF_UP);
//															}
//
//															if ("MTON".equals(String.valueOf(f[7]))) {
//																totalRate = (totalRate.multiply(totalCargoWeight))
//																		.setScale(3, RoundingMode.HALF_UP);
//															}
//
//															tempAss.setRates(totalRate);
//															tempAss.setServiceRate(serviceRate.get());
//
//														}
//
//														if ("WEEK".equals(unit)) {
//
//															long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//																	destuffDateTime);
//
//															if (daysBetween == 0 && ChronoUnit.HOURS
//																	.between(gateInDateTime, destuffDateTime) > 0) {
//																daysBetween = 1;
//															}
//
//															final long daysBetween1 = daysBetween - cargoFreeDays;
//
//															long weeksBetween2 = (long) Math.ceil(daysBetween / 7.0);
//															long weeksBetween1 = (long) Math.ceil(cargoFreeDays / 7.0);
//															long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//															tempAss.setExecutionUnit(String.valueOf(weeksBetween2));
//															tempAss.setExecutionUnit1(String.valueOf(areaReleased));
//															tempAss.setFreeDays(new BigDecimal(weeksBetween1));
//
//															BigDecimal totalRate = rangeValues.stream().filter(r -> {
//																int fromRange = ((BigDecimal) r[6]).intValue();
//																int toRange = ((BigDecimal) r[7]).intValue();
//																// Include slabs that overlap with weeksBetween
//																return weeksBetween >= fromRange;
//															}).map(r -> {
//																int fromRange = ((BigDecimal) r[6]).intValue();
//																int toRange = ((BigDecimal) r[7]).intValue();
//																BigDecimal rate = (BigDecimal) r[8];
//
//																// Adjust the fromRange to exclude week 0
//																int adjustedFromRange = Math.max(fromRange, 1);
//
//																if (weeksBetween >= fromRange
//																		&& weeksBetween <= toRange) {
//																	serviceRate.set(rate); // Set the rate for the
//																							// matching
//																							// slab
//																}
//																// Determine actual weeks in this slab that contribute
//																// to the total
//																long weeksInSlab = Math.max(0,
//																		Math.min(toRange, weeksBetween)
//																				- adjustedFromRange + 1);
//
//																// Exclude slabs where weeksInSlab is 0
//																return weeksInSlab > 0
//																		? rate.multiply(new BigDecimal(weeksInSlab))
//																		: BigDecimal.ZERO;
//															}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//															totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//															System.out.println(
//																	"totalRate " + totalRate + " " + weeksBetween);
//
//															if ("SM".equals(String.valueOf(f[7]))) {
//																totalRate = (totalRate.multiply(areaReleased))
//																		.setScale(3, RoundingMode.HALF_UP);
//															}
//
//															if ("MTON".equals(String.valueOf(f[7]))) {
//																totalRate = (totalRate.multiply(totalCargoWeight))
//																		.setScale(3, RoundingMode.HALF_UP);
//															}
//
////															serviceRate.updateAndGet(existingValue -> existingValue.add(serviceRate.get()));														
//															tempAss.setServiceRate(serviceRate.get());
//															tempAss.setRates(totalRate);
//														}
//
//													}
//
//												}
//
//												finalConData.add(tempAss);
//
//											}
//
//										}
////											IF STORAGE TYPE CRG ENDING										
//
////											IF STORAGE TYPE NOT CRG STARTING
//
//										else {
//											System.out.println("----------7");
//											tempAss = (ExportContainerAssessmentData) con.clone();
//											
//											if ("SA".equals(String.valueOf(f[8]))) {
//												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
////													
//												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//												tempAss.setRangeFrom(
//														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																: BigDecimal.ZERO);
//												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//														: BigDecimal.ZERO);
//												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//												tempAss.setContainerStatus(con.getContainerStatus());
//												tempAss.setGateOutId(con.getGateOutId());
//												tempAss.setGatePassNo(con.getGatePassNo());
//												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
//														? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//												tempAss.setTaxId(String.valueOf(f[11]));
//												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//
//												List<String> conSize2 = new ArrayList<>();
//												conSize2.add("ALL");
//												conSize2.add(("22".equals(con.getContainerSize())) ? "20"
//														: con.getContainerSize());
//
//												List<String> conTypeOfCon2 = new ArrayList<>();
//												conTypeOfCon2.add("ALL");
//												conTypeOfCon2.add(con.getTypeOfContainer());
//
//												List<String> commodityType = new ArrayList<>();
//												commodityType.add("ALL");
//												commodityType.add(assessment.getCommodityCode());
//
//												List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(
//														cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
//														String.valueOf(f[0]), String.valueOf(f[8]), conSize2,
//														conTypeOfCon2, commodityType, con.getContainerSize(),
//														con.getTypeOfContainer(), assessment.getCommodityCode());
//
//												if (!rangeValues.isEmpty()) {
//
//													String unit = String.valueOf(f[1]);
//
//													AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
//															BigDecimal.ZERO);
//
//													LocalDateTime gateInDateTime = null;
//													LocalDateTime destuffDateTime = null;
//
//													if ("MTY".equals(storageType)) {
//														gateInDateTime = adjustToCustomStartOfDay(
//																convertToLocalDateTime(con.getGateInDate()),
//																day.getStartTime());
//
//														destuffDateTime = adjustToCustomEndOfDay(
//																convertToLocalDateTime(con.getStuffTallyDate()),
//																day.getEndTime());
//
//													} else if ("CON".equals(storageType)) {
//														gateInDateTime = adjustToCustomStartOfDay(
//																convertToLocalDateTime(con.getStuffTallyDate()),
//																day.getStartTime());
//
//														destuffDateTime = adjustToCustomEndOfDay(
//																convertToLocalDateTime(con.getInvoiceDate()),
//																day.getEndTime());
//
//													} else {
//														gateInDateTime = adjustToCustomStartOfDay(
//																convertToLocalDateTime(con.getGateInDate()),
//																day.getStartTime());
//
//														destuffDateTime = adjustToCustomEndOfDay(
//																convertToLocalDateTime(con.getInvoiceDate()),
//																day.getEndTime());
//													}
//
//													System.out.println(" gateInDateTime " + gateInDateTime
//															+ " destuffDateTime " + destuffDateTime);
//
//													BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
//															.filter(r -> ((BigDecimal) r[8])
//																	.compareTo(BigDecimal.ZERO) == 0) // Filter where
//																										// rate is 0
//															.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as
//																							// BigDecimal
//															.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
//															.orElse(BigDecimal.ZERO); // Default value if no match found
//
//													if ("DAY".equals(unit)) {
//
//														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//																destuffDateTime);
//
//														System.out.println(" ChronoUnit.HOURS "
//																+ ChronoUnit.HOURS.between(gateInDateTime,
//																		destuffDateTime)
//																+ " + daysBetween1 + " + daysBetween + " "
//																+ daysBetween);
//
//														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
//																destuffDateTime) > 0) {
//															daysBetween = 1;
//														}
//
//														final long daysBetween1 = daysBetween;
//														System.out.println(" daysBetween1 " + daysBetween1
//																+ " daysBetween " + daysBetween);
//
//														tempAss.setExecutionUnit(String.valueOf(daysBetween1));
//														tempAss.setExecutionUnit1("");
//														tempAss.setFreeDays(freeDaysForExportContainerStorage);
//
//														System.out.println("daysBetween " + daysBetween1);
//
//														BigDecimal totalRate = rangeValues.stream().map(r -> {
//															int fromRange = ((BigDecimal) r[6]).intValue(); // Start
//																											// of
//																											// the
//																											// slab
//															int toRange = ((BigDecimal) r[7]).intValue(); // End of
//																											// the
//																											// slab
//															BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
//																									// the
//																									// slab
//															if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
//																serviceRate.set(rate); // Set the rate for the
//																						// matching
//																						// slab
//															}
//															// Calculate days in the current slab
//															long daysInSlab = Math.min(toRange, daysBetween1)
//																	- Math.max(fromRange, 1) + 1;
//
//															daysInSlab = Math.max(daysInSlab, 0);
//															return rate.multiply(new BigDecimal(daysInSlab));
//														}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//
//														tempAss.setServiceRate(serviceRate.get());
//														tempAss.setRates(totalRate);
//
//													}
//
//													if ("WEEK".equals(unit)) {
//
//														long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//																destuffDateTime);
//
//														System.out.println(" ChronoUnit.HOURS "
//																+ ChronoUnit.HOURS.between(gateInDateTime,
//																		destuffDateTime)
//																+ " + daysBetween1 + " + daysBetween + " "
//																+ daysBetween);
//
//														if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
//																destuffDateTime) > 0) {
//															daysBetween = 1;
//														}
//
//														long freeDaysOld = freeDaysForExportContainerStorage != null
//																? freeDaysForExportContainerStorage.longValue()
//																: 0L;
//														;
//
//														tempAss.setFreeDays(
//																new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));
//
//														final long daysBetween1 = daysBetween;
//														long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//														tempAss.setExecutionUnit(String.valueOf(weeksBetween));
//														tempAss.setExecutionUnit1("");
//
//														BigDecimal totalRate = rangeValues.stream().filter(r -> {
//															int fromRange = ((BigDecimal) r[6]).intValue();
//															int toRange = ((BigDecimal) r[7]).intValue();
//															// Include slabs that overlap with weeksBetween
//															return weeksBetween >= fromRange;
//														}).map(r -> {
//															int fromRange = ((BigDecimal) r[6]).intValue();
//															int toRange = ((BigDecimal) r[7]).intValue();
//															BigDecimal rate = (BigDecimal) r[8];
//															if (weeksBetween >= fromRange && weeksBetween <= toRange) {
//																serviceRate.set(rate); // Set the rate for the
//																						// matching
//																						// slab
//															}
//
//															// Adjust the fromRange to exclude week 0
//															int adjustedFromRange = Math.max(fromRange, 1);
//
//															// Determine actual weeks in this slab that contribute
//															// to the total
//															long weeksInSlab = Math.max(0,
//																	Math.min(toRange, weeksBetween) - adjustedFromRange
//																			+ 1);
//
//															// Exclude slabs where weeksInSlab is 0
//															return weeksInSlab > 0
//																	? rate.multiply(new BigDecimal(weeksInSlab))
//																	: BigDecimal.ZERO;
//														}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//														totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//														System.out
//																.println("totalRate " + totalRate + " " + weeksBetween);
//
////																if ("SM".equals(String.valueOf(f[7]))) {
////																	totalRate = (totalRate.multiply(con.getArea()))
////																			.setScale(3, RoundingMode.HALF_UP);
////																}
//
//														tempAss.setRates(totalRate);
//														tempAss.setServiceRate(serviceRate.get());
//													}
//
//												}
//
//											}
//
////											ELSE IF RANGE
//											else if ("RA".equals(String.valueOf(f[8]))) {
//												System.out.println("----------8");
//
//												String unit = String.valueOf(f[1]);
//												BigDecimal executionUnit = BigDecimal.ZERO;
//												List<String> conSize2 = new ArrayList<>();
//												conSize2.add("ALL");
//												conSize2.add(("22".equals(con.getContainerSize())) ? "20"
//														: con.getContainerSize());
//
//												List<String> conTypeOfCon2 = new ArrayList<>();
//												conTypeOfCon2.add("ALL");
//												conTypeOfCon2.add(con.getTypeOfContainer());
//
//												List<String> commodityType = new ArrayList<>();
//												commodityType.add("ALL");
//												commodityType.add(assessment.getCommodityCode());
//
//												if ("MTON".equals(unit)) {
////														String serviceGrp = String.valueOf(f[18]);
//
//													if ("H".equals(serviceGrp)) {
//
////														BigDecimal cargoWt = new BigDecimal(
////																String.valueOf(con.getGrossWt()))
////																.divide(new BigDecimal(1000))
////																.setScale(3, RoundingMode.HALF_UP);
//														
//														String grossWtStr = String.valueOf(con.getGrossWt()).trim(); // Remove spaces
//														 BigDecimal cargoWt = BigDecimal.ZERO;
//														
//														// Check if the value is numeric before converting
//														if (grossWtStr != null && grossWtStr.matches("-?\\d+(\\.\\d+)?")) { 
//														     cargoWt = new BigDecimal(grossWtStr)
//														                              .divide(new BigDecimal(1000))
//														                              .setScale(3, RoundingMode.HALF_UP);
//														} else {
//														    cargoWt = BigDecimal.ZERO; // Default value or handle appropriately
//														    System.out.println("Invalid gross weight: " + grossWtStr);
//														}
//
//														
//
//														executionUnit = cargoWt;
//
//														tempAss.setExecutionUnit(String.valueOf(executionUnit));
//														tempAss.setExecutionUnit1("");
//													} else {
//
////														BigDecimal cargoWt = con.getCargoWeight()
////																.divide(new BigDecimal(1000))
////																.setScale(3, RoundingMode.HALF_UP);
//														
//														BigDecimal cargoWeight = con.getCargoWeight();
//														BigDecimal cargoWt = (cargoWeight != null) 
//														    ? cargoWeight.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP) 
//														    : BigDecimal.ZERO; // Default to zero if null
//
//
//														executionUnit = cargoWt;
//														tempAss.setExecutionUnit(String.valueOf(executionUnit));
//														tempAss.setExecutionUnit1("");
//
//													}
//													Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(
//															cid, bid, String.valueOf(f[3]), String.valueOf(f[4]),
//															String.valueOf(f[0]), executionUnit, conSize2,
//															conTypeOfCon2, commodityType, con.getContainerSize(),
//															con.getTypeOfContainer(), assessment.getCommodityCode());
//
//													if (rangeValue != null) {
//														Object[] finalRangeValue = (Object[]) rangeValue;
//														tempAss.setServiceGroup(
//																f[14] != null ? String.valueOf(f[14]) : "");
//														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//														tempAss.setServiceName(
//																f[18] != null ? String.valueOf(f[18]) : "");
//														tempAss.setServiceUnit(
//																f[1] != null ? String.valueOf(f[1]) : "");
//														tempAss.setServiceUnit1(
//																f[7] != null ? String.valueOf(f[7]) : "");
//														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//														tempAss.setRangeFrom(
//																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																		: BigDecimal.ZERO);
//														tempAss.setRangeTo(
//																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//																		: BigDecimal.ZERO);
//														tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//														tempAss.setContainerStatus(con.getContainerStatus());
//														tempAss.setGateOutId(con.getGateOutId());
//														tempAss.setGatePassNo(con.getGatePassNo());
//														tempAss.setTaxPerc(
//																(f[12] == null || String.valueOf(f[12]).isEmpty())
//																		? BigDecimal.ZERO
//																		: new BigDecimal(String.valueOf(f[12])));
//														tempAss.setTaxId(String.valueOf(f[11]));
//														BigDecimal totalRate = new BigDecimal(
//																String.valueOf(finalRangeValue[8]));
//
//														tempAss.setServiceRate(totalRate);
//														tempAss.setRates(totalRate);
//														tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//													}
//
//												} else if ("TEU".equals(unit) || "SM".equals(unit)
//														|| "CNTR".equals(unit) || "PBL".equals(unit)
//														|| "ACT".equals(unit) || "PCK".equals(unit)
//														|| "SHFT".equals(unit) || "KG".equals(unit)) {
//
//													if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
//															|| "ACT".equals(unit) || "PCK".equals(unit)
//															|| "SHFT".equals(unit)) {
//														executionUnit = new BigDecimal("1");
//														tempAss.setExecutionUnit(String.valueOf(executionUnit));
//														tempAss.setExecutionUnit1("");
//													}
//
//													if ("TEU".equals(unit)) {
//														if ("20".equals(con.getContainerSize())
//																|| "22".equals(con.getContainerSize())) {
//															executionUnit = new BigDecimal("1");
//															tempAss.setExecutionUnit(String.valueOf(executionUnit));
//															tempAss.setExecutionUnit1("");
//														} else if ("40".equals(con.getContainerSize())) {
//															executionUnit = new BigDecimal("2");
//															tempAss.setExecutionUnit(String.valueOf(executionUnit));
//															tempAss.setExecutionUnit1("");
//														} else if ("45".equals(con.getContainerSize())) {
//															executionUnit = new BigDecimal("2.5");
//															tempAss.setExecutionUnit(String.valueOf(executionUnit));
//															tempAss.setExecutionUnit1("");
//														}
//													}
//
//													if ("KG".equals(unit)) {
////															String serviceGrp = String.valueOf(f[18]);
//														if ("H".equals(serviceGrp)) {
//
////																CARGO WEIGHT												
//
//															BigDecimal cargoWt = new BigDecimal(
//																	String.valueOf(con.getGrossWt()));
//
//															executionUnit = cargoWt;
//
//															tempAss.setExecutionUnit(String.valueOf(executionUnit));
//															tempAss.setExecutionUnit1("");
//														} else {
//															BigDecimal cargoWt = con.getCargoWeight();
//
//															executionUnit = cargoWt;
//															tempAss.setExecutionUnit(String.valueOf(executionUnit));
//															tempAss.setExecutionUnit1("");
//														}
//													}
//
//													Object rangeValue = exportInvoiceRepo.getRangeDataByServiceId(cid,
//															bid, String.valueOf(f[3]), String.valueOf(f[4]),
//															String.valueOf(f[0]), executionUnit, conSize2,
//															conTypeOfCon2, commodityType, con.getContainerSize(),
//															con.getTypeOfContainer(), assessment.getCommodityCode());
//
//													if (rangeValue != null) {
//														Object[] finalRangeValue = (Object[]) rangeValue;
//														tempAss.setServiceGroup(
//																f[14] != null ? String.valueOf(f[14]) : "");
//														tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//														tempAss.setServiceName(
//																f[18] != null ? String.valueOf(f[18]) : "");
//														tempAss.setServiceUnit(
//																f[1] != null ? String.valueOf(f[1]) : "");
//														tempAss.setServiceUnit1(
//																f[7] != null ? String.valueOf(f[7]) : "");
//														tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//														tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//														tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//														tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//														tempAss.setRangeFrom(
//																f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																		: BigDecimal.ZERO);
//														tempAss.setRangeTo(
//																f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//																		: BigDecimal.ZERO);
//														tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//														tempAss.setContainerStatus(con.getContainerStatus());
//														tempAss.setGateOutId(con.getGateOutId());
//														tempAss.setGatePassNo(con.getGatePassNo());
//														tempAss.setTaxPerc(
//																(f[12] == null || String.valueOf(f[12]).isEmpty())
//																		? BigDecimal.ZERO
//																		: new BigDecimal(String.valueOf(f[12])));
//														tempAss.setTaxId(String.valueOf(f[11]));
//														tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//														BigDecimal totalRate = new BigDecimal(
//																String.valueOf(finalRangeValue[8]));
//
//														tempAss.setServiceRate(totalRate);
//														tempAss.setRates(totalRate);
//													}
//
//												} else {
//													tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//													tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//													tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//													tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//													tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//													tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//
//													tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//													tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//													tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//													tempAss.setRangeFrom(
//															f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																	: BigDecimal.ZERO);
//													tempAss.setRangeTo(
//															f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//																	: BigDecimal.ZERO);
//													tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//													tempAss.setContainerStatus(con.getContainerStatus());
//													tempAss.setGateOutId(con.getGateOutId());
//													tempAss.setGatePassNo(con.getGatePassNo());
//													tempAss.setTaxPerc(
//															(f[12] == null || String.valueOf(f[12]).isEmpty())
//																	? BigDecimal.ZERO
//																	: new BigDecimal(String.valueOf(f[12])));
//													tempAss.setTaxId(String.valueOf(f[11]));
//													tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//													BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
//													tempAss.setServiceRate(totalRate);
//													tempAss.setRates(totalRate);
//												}
//
//											}
////												ELSE PLAIN
//											else {
//												System.out.println("----------9");
//												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//
//												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//												tempAss.setRangeFrom(
//														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																: BigDecimal.ZERO);
//												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//														: BigDecimal.ZERO);
//												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//												tempAss.setContainerStatus(con.getContainerStatus());
//												tempAss.setGateOutId(con.getGateOutId());
//												tempAss.setGatePassNo(con.getGatePassNo());
//												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
//														? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//												tempAss.setExecutionUnit(String.valueOf(1));
//												tempAss.setExecutionUnit1("");
//												tempAss.setTaxId(String.valueOf(f[11]));
//												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//												BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
//
//												tempAss.setServiceRate(totalRate);
//												tempAss.setRates(totalRate);
//											}
//
//										}
//
////											IF STORAGE TYPE NOT IN CONT, CRG AND EMPTY ENDING
//
//										finalConData.add(tempAss);
//
//									} catch (CloneNotSupportedException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									} catch (ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//								}
//							}
//
//							// IF SSR DATA NOT EMPTY END
//
//						}
//
//						// ENDING IF SERVICE ID IS FROM SSR
//
////	STARTING IF SERVICE ID IS NOT FROM SSR		
//						else {
//
////								IF SLAB
//							String storageType = getFirstServiceGroup(f[19] != null ? String.valueOf(f[19]) : "");
//							String serviceGrp = String.valueOf(f[14]);
////								IF STORAGE TYPE IN CRG STARTING....
//							SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//							System.out.println(
//									"storageType " + storageType + " serviceGrp " + serviceGrp + "-----------10");
//
//							if ("C".equals(serviceGrp) && "CRG".equals(storageType)) {
//								AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(BigDecimal.ZERO);
//
//								List<Object[]> exportCartingStorageDays = exportInvoiceRepo.getExportCartingStorageDays(
//										cid, bid, con.getStuffTallyId(), con.getContainerNo());
//
//								for (Object[] value : exportCartingStorageDays) {
//
//									try {
//										tempAss = (ExportContainerAssessmentData) con.clone();
//									} catch (CloneNotSupportedException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//									Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
//											day.getEndTime());
//
//									tempAss.setInvoiceDate(invDate);
//
//									String sbNo = value[0] != null ? String.valueOf(value[0]) : "";
//									Date stuffTallyDate = null;
//									try {
//										stuffTallyDate = value[1] != null ? dateTimeFormat.parse(value[1].toString())
//												: null;
//									} catch (ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//									try {
//										Date inGateInDate = value[2] != null ? dateTimeFormat.parse(value[2].toString())
//												: null;
//									} catch (ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//									Date cartingTransDate = null;
//									try {
//										cartingTransDate = value[5] != null ? dateTimeFormat.parse(value[5].toString())
//												: null;
//									} catch (ParseException e) {
//										// TODO Auto-generated catch block
//										e.printStackTrace();
//									}
//
//									// Retrieve weights and areas as BigDecimal
//									BigDecimal areaReleased = value[3] != null ? new BigDecimal(value[3].toString())
//											: null;
//									BigDecimal totalCargoWeight = value[6] != null ? new BigDecimal(value[6].toString())
//											: null;
//
//									// Vehicle number remains as a String
//									String vehicleNo = value[4] != null ? value[4].toString() : "";
//
//									LocalDateTime gateInDateTime = adjustToCustomStartOfDay(
//											convertToLocalDateTime(cartingTransDate), day.getStartTime());
//
//									LocalDateTime destuffDateTime = adjustToCustomEndOfDay(
//											convertToLocalDateTime(stuffTallyDate), day.getEndTime());
//
//									if ("SA".equals(String.valueOf(f[8]))) {
//										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
////									
//										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//												: BigDecimal.ZERO);
//										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//												: BigDecimal.ZERO);
//										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//										tempAss.setContainerStatus(con.getContainerStatus());
//										tempAss.setGateOutId(con.getGateOutId());
//										tempAss.setGatePassNo(con.getGatePassNo());
//										tempAss.setTaxPerc(
//												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//										tempAss.setTaxId(String.valueOf(f[11]));
//										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//										tempAss.setCargoSbNo(sbNo);
//
//										List<String> conSize2 = new ArrayList<>();
//										conSize2.add("ALL");
//										conSize2.add(
//												("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());
//
//										List<String> conTypeOfCon2 = new ArrayList<>();
//										conTypeOfCon2.add("ALL");
//										conTypeOfCon2.add(con.getTypeOfContainer());
//
//										List<String> commodityType = new ArrayList<>();
//										commodityType.add("ALL");
//										commodityType.add(assessment.getCommodityCode());
//
//										List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(cid, bid,
//												String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
//												String.valueOf(f[8]), conSize2, conTypeOfCon2, commodityType,
//												con.getContainerSize(), con.getTypeOfContainer(),
//												assessment.getCommodityCode());
//
//										if (!rangeValues.isEmpty()) {
//
//											String unit = String.valueOf(f[1]);
//
//											if ("DAY".equals(unit)) {
//
//												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//														destuffDateTime);
//
//												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
//														destuffDateTime) > 0) {
//													daysBetween = 1;
//												}
//
//												final long daysBetween1 = daysBetween - cargoFreeDays;
//
////												final long daysBetween1 = daysBetween;
//
//												tempAss.setExecutionUnit(String.valueOf(daysBetween));
//												tempAss.setExecutionUnit1(String.valueOf(areaReleased));
//
//												tempAss.setFreeDays(freeDays);
//
//												System.out.println("daysBetween " + daysBetween1 + " serviceRate.get() "
//														+ serviceRate.get());
//
//												BigDecimal totalRate = rangeValues.stream().map(r -> {
//													int fromRange = ((BigDecimal) r[6]).intValue(); // Start
//																									// of
//																									// the
//																									// slab
//													int toRange = ((BigDecimal) r[7]).intValue(); // End of
//																									// the
//																									// slab
//													BigDecimal rate = (BigDecimal) r[8]; // Rate per day in
//																							// the
//																							// slab
//													if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
//														serviceRate.set(rate); // Set the rate for the
//																				// matching
//																				// slab
//													}
//													// Calculate days in the current slab
//													long daysInSlab = Math.min(toRange, daysBetween1)
//															- Math.max(fromRange, 1) + 1;
//
//													daysInSlab = Math.max(daysInSlab, 0);
//													return rate.multiply(new BigDecimal(daysInSlab));
//												}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//
//												if ("SM".equals(String.valueOf(f[7]))) {
//													totalRate = (totalRate.multiply(areaReleased)).setScale(3,
//															RoundingMode.HALF_UP);
//												}
//
//												if ("MTON".equals(String.valueOf(f[7]))) {
//													totalRate = (totalRate.multiply(totalCargoWeight)).setScale(3,
//															RoundingMode.HALF_UP);
//												}
//
//												System.out.println("tempAss.serviceRate.get() " + serviceRate.get());
//
//												tempAss.setServiceRate(serviceRate.get());
//												tempAss.setRates(totalRate);
//
//											}
//
//											if ("WEEK".equals(unit)) {
//
//												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//														destuffDateTime);
//
//												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
//														destuffDateTime) > 0) {
//													daysBetween = 1;
//												}
//
////												final long daysBetween1 = daysBetween;
////												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//												final long daysBetween1 = daysBetween - cargoFreeDays;
//
//												long weeksBetween2 = (long) Math.ceil(daysBetween / 7.0);
//												long weeksBetween1 = (long) Math.ceil(cargoFreeDays / 7.0);
//												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//												tempAss.setExecutionUnit(String.valueOf(weeksBetween2));
//												tempAss.setExecutionUnit1(String.valueOf(areaReleased));
//												tempAss.setFreeDays(new BigDecimal(weeksBetween1));
//
////
////												tempAss.setExecutionUnit(String.valueOf(weeksBetween));
////												tempAss.setExecutionUnit1(String.valueOf(areaReleased));
//
//												BigDecimal totalRate = rangeValues.stream().filter(r -> {
//													int fromRange = ((BigDecimal) r[6]).intValue();
//													int toRange = ((BigDecimal) r[7]).intValue();
//													// Include slabs that overlap with weeksBetween
//													return weeksBetween >= fromRange;
//												}).map(r -> {
//													int fromRange = ((BigDecimal) r[6]).intValue();
//													int toRange = ((BigDecimal) r[7]).intValue();
//													BigDecimal rate = (BigDecimal) r[8];
//
//													if (weeksBetween >= fromRange && weeksBetween <= toRange) {
//														serviceRate.set(rate); // Set the rate for the
//																				// matching
//																				// slab
//													}
//
//													// Adjust the fromRange to exclude week 0
//													int adjustedFromRange = Math.max(fromRange, 1);
//
//													// Determine actual weeks in this slab that contribute
//													// to the total
//													long weeksInSlab = Math.max(0,
//															Math.min(toRange, weeksBetween) - adjustedFromRange + 1);
//
//													// Exclude slabs where weeksInSlab is 0
//													return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
//															: BigDecimal.ZERO;
//												}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//												System.out.println("totalRate " + totalRate + " " + weeksBetween);
//
//												if ("SM".equals(String.valueOf(f[7]))) {
//													totalRate = (totalRate.multiply(areaReleased)).setScale(3,
//															RoundingMode.HALF_UP);
//												}
//
//												if ("MTON".equals(String.valueOf(f[7]))) {
//													totalRate = (totalRate.multiply(totalCargoWeight)).setScale(3,
//															RoundingMode.HALF_UP);
//												}
//												System.out.println("tempAss.serviceRate.get() 2 " + serviceRate.get());
////												serviceRate.updateAndGet(existingValue -> existingValue.add(serviceRate.get()));														
//												tempAss.setServiceRate(serviceRate.get());
//												tempAss.setRates(totalRate);
//											}
//
//										}
//
//									}
//									finalConData.add(tempAss);
//								}
//
//							} else {
//
//								System.out.println("----------11");
//
//								try {
//									tempAss = (ExportContainerAssessmentData) con.clone();
//									Date invDate = adjustInvoiceDate(tempAss.getInvoiceDate(), day.getStartTime(),
//											day.getEndTime());
//
//									tempAss.setInvoiceDate(invDate);
//
//									System.out.println("----------12");
////									IF SLAB
//									if ("SA".equals(String.valueOf(f[8]))) {
//										System.out.println("----------13");
//										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//
//										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//												: BigDecimal.ZERO);
//										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//												: BigDecimal.ZERO);
//										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//										tempAss.setContainerStatus(con.getContainerStatus());
//										tempAss.setGateOutId(con.getGateOutId());
//										tempAss.setGatePassNo(con.getGatePassNo());
//										tempAss.setTaxPerc(
//												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//										tempAss.setTaxId(String.valueOf(f[11]));
//										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//										List<String> conSize2 = new ArrayList<>();
//										conSize2.add("ALL");
//										conSize2.add(
//												("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());
//
//										List<String> conTypeOfCon2 = new ArrayList<>();
//										conTypeOfCon2.add("ALL");
//										conTypeOfCon2.add(con.getTypeOfContainer());
//
//										List<String> commodityType = new ArrayList<>();
//										commodityType.add("ALL");
//										commodityType.add(assessment.getCommodityCode());
//
//										List<Object[]> rangeValues = cfstariffservicerepo.getDataByServiceId(cid, bid,
//												String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
//												String.valueOf(f[8]), conSize2, conTypeOfCon2, commodityType,
//												con.getContainerSize(), con.getTypeOfContainer(),
//												assessment.getCommodityCode());
//
//										if (!rangeValues.isEmpty()) {
//
//											String unit = String.valueOf(f[1]);
//
//											AtomicReference<BigDecimal> serviceRate = new AtomicReference<>(
//													BigDecimal.ZERO);
//
//											LocalDateTime gateInDateTime = null;
//											LocalDateTime destuffDateTime = null;
//
//											if ("MTY".equals(storageType)) {
//												gateInDateTime = adjustToCustomStartOfDay(
//														convertToLocalDateTime(con.getGateInDate()),
//														day.getStartTime());
//
//												destuffDateTime = adjustToCustomEndOfDay(
//														convertToLocalDateTime(con.getStuffTallyDate()),
//														day.getEndTime());
//
//											} else if ("CON".equals(storageType)) {
//												gateInDateTime = adjustToCustomStartOfDay(
//														convertToLocalDateTime(con.getStuffTallyDate()),
//														day.getStartTime());
//
//												destuffDateTime = adjustToCustomEndOfDay(
//														convertToLocalDateTime(con.getInvoiceDate()), day.getEndTime());
//
//											} else {
//												gateInDateTime = adjustToCustomStartOfDay(
//														convertToLocalDateTime(con.getGateInDate()),
//														day.getStartTime());
//
//												destuffDateTime = adjustToCustomEndOfDay(
//														convertToLocalDateTime(con.getInvoiceDate()), day.getEndTime());
//											}
//											System.out.println("gateInDateTime " + gateInDateTime + " destuffDateTime "
//													+ destuffDateTime + " con.getStuffTallyDate() "
//													+ con.getStuffTallyDate() + " con.getInvoiceDate() "
//													+ con.getInvoiceDate());
//
//											BigDecimal freeDaysForExportContainerStorage = rangeValues.stream()
//													.filter(r -> ((BigDecimal) r[8]).compareTo(BigDecimal.ZERO) == 0) // Filter
//																														// where
//																														// rate
//																														// is
//																														// 0
//													.map(r -> (BigDecimal) r[7]) // Extract 'toRange' as BigDecimal
//													.max(Comparator.naturalOrder()) // Get the maximum 'toRange'
//													.orElse(BigDecimal.ZERO); // Default value if no match found
//
//											if ("DAY".equals(unit)) {
//
//												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//														destuffDateTime);
//
//												System.out.println(" ChronoUnit.HOURS "
//														+ ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime)
//														+ " + daysBetween1 + " + daysBetween + " " + daysBetween);
//
//												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
//														destuffDateTime) > 0) {
//													daysBetween = 1;
//												}
//
////													final long daysBetween1 = daysBetween;
////
////													tempAss.setExecutionUnit(String.valueOf(daysBetween1));
////													tempAss.setExecutionUnit1("");
//
//												final long daysBetween1 = daysBetween;
//
//												tempAss.setExecutionUnit(String.valueOf(daysBetween1));
//												tempAss.setExecutionUnit1("");
//												tempAss.setFreeDays(freeDaysForExportContainerStorage);
//
//												System.out.println("daysBetween " + daysBetween1);
//
//												BigDecimal totalRate = rangeValues.stream().map(r -> {
//													int fromRange = ((BigDecimal) r[6]).intValue(); // Start of
//																									// the slab
//													int toRange = ((BigDecimal) r[7]).intValue(); // End of the
//																									// slab
//													BigDecimal rate = (BigDecimal) r[8]; // Rate per day in the
//																							// slab
//													if (daysBetween1 >= fromRange && daysBetween1 <= toRange) {
//														serviceRate.set(rate); // Set the rate for the matching slab
//													}
//													// Calculate days in the current slab
//													long daysInSlab = Math.min(toRange, daysBetween1)
//															- Math.max(fromRange, 1) + 1;
//
//													daysInSlab = Math.max(daysInSlab, 0);
//													return rate.multiply(new BigDecimal(daysInSlab));
//												}).reduce(BigDecimal.ZERO, BigDecimal::add);
//												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
//												tempAss.setServiceRate(serviceRate.get());
//												tempAss.setRates(totalRate);
//
//											}
//
//											if ("WEEK".equals(unit)) {
//
//												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
//														destuffDateTime);
//												System.out.println(" ChronoUnit.HOURS "
//														+ ChronoUnit.HOURS.between(gateInDateTime, destuffDateTime)
//														+ " + daysBetween1 + " + daysBetween + " " + daysBetween);
//
//												if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
//														destuffDateTime) > 0) {
//													daysBetween = 1;
//												}
//
////													final long daysBetween1 = daysBetween;
////													long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
////
////													tempAss.setExecutionUnit(String.valueOf(weeksBetween));
////													tempAss.setExecutionUnit1("");
//
//												long freeDaysOld = freeDaysForExportContainerStorage != null
//														? freeDaysForExportContainerStorage.longValue()
//														: 0L;
//												;
//
//												tempAss.setFreeDays(
//														new BigDecimal((long) Math.ceil(freeDaysOld / 7.0)));
//
//												final long daysBetween1 = daysBetween;
//												long weeksBetween = (long) Math.ceil(daysBetween1 / 7.0);
//
//												tempAss.setExecutionUnit(String.valueOf(weeksBetween));
//												tempAss.setExecutionUnit1("");
//
//												BigDecimal totalRate = rangeValues.stream().filter(r -> {
//													int fromRange = ((BigDecimal) r[6]).intValue();
//													int toRange = ((BigDecimal) r[7]).intValue();
//													// Include slabs that overlap with weeksBetween
//													return weeksBetween >= fromRange;
//												}).map(r -> {
//													int fromRange = ((BigDecimal) r[6]).intValue();
//													int toRange = ((BigDecimal) r[7]).intValue();
//													BigDecimal rate = (BigDecimal) r[8];
//
//													if (weeksBetween >= fromRange && weeksBetween <= toRange) {
//														serviceRate.set(rate); // Set the rate for the
//																				// matching
//																				// slab
//													}
//
//													// Adjust the fromRange to exclude week 0
//													int adjustedFromRange = Math.max(fromRange, 1);
//
//													// Determine actual weeks in this slab that contribute to the
//													// total
//													long weeksInSlab = Math.max(0,
//															Math.min(toRange, weeksBetween) - adjustedFromRange + 1);
//
//													// Exclude slabs where weeksInSlab is 0
//													return weeksInSlab > 0 ? rate.multiply(new BigDecimal(weeksInSlab))
//															: BigDecimal.ZERO;
//												}).reduce(BigDecimal.ZERO, BigDecimal::add);
//
//												totalRate = totalRate.setScale(3, BigDecimal.ROUND_HALF_UP);
////													if ("SM".equals(String.valueOf(f[7]))) {
////														totalRate = (totalRate.multiply(con.getArea())).setScale(3,
////																RoundingMode.HALF_UP);
////													}
//												System.out.println("totalRate " + totalRate + " " + weeksBetween);
//												tempAss.setServiceRate(serviceRate.get());
//												tempAss.setRates(totalRate);
//
//											}
//
//										}
//
//									}
////									ELSE IF RANGE
//									else if ("RA".equals(String.valueOf(f[8]))) {
//										System.out.println("----------14 " + f[0] != null ? String.valueOf(f[0]) : "");
//
//										String unit = String.valueOf(f[1]);
//										BigDecimal executionUnit = BigDecimal.ZERO;
//										List<String> conSize2 = new ArrayList<>();
//										conSize2.add("ALL");
//										conSize2.add(
//												("22".equals(con.getContainerSize())) ? "20" : con.getContainerSize());
//
//										List<String> conTypeOfCon2 = new ArrayList<>();
//										conTypeOfCon2.add("ALL");
//										conTypeOfCon2.add(con.getTypeOfContainer());
//
//										List<String> commodityType = new ArrayList<>();
//										commodityType.add("ALL");
//										commodityType.add(assessment.getCommodityCode());
//
//										if ("MTON".equals(unit)) {
////											String serviceGrp = String.valueOf(f[18]);
//
//											if ("H".equals(serviceGrp)) {
//												BigDecimal cargoWt = new BigDecimal(
//														String.valueOf(con.getGrossWeight()))
//														.divide(new BigDecimal(1000)).setScale(3, RoundingMode.HALF_UP);
//
//												executionUnit = cargoWt;
//
//												tempAss.setExecutionUnit(String.valueOf(executionUnit));
//												tempAss.setExecutionUnit1("");
//											} else {
//												BigDecimal cargoWt = con.getCargoWeight().divide(new BigDecimal(1000))
//														.setScale(3, RoundingMode.HALF_UP);
//
//												executionUnit = cargoWt;
//												tempAss.setExecutionUnit(String.valueOf(executionUnit));
//												tempAss.setExecutionUnit1("");
//											}
//
//											Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(cid, bid,
//													String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
//													executionUnit, conSize2, conTypeOfCon2, commodityType,
//													con.getContainerSize(), con.getTypeOfContainer(),
//													assessment.getCommodityCode());
//
//											if (rangeValue != null) {
//												Object[] finalRangeValue = (Object[]) rangeValue;
//												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//												tempAss.setRangeFrom(
//														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																: BigDecimal.ZERO);
//												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//														: BigDecimal.ZERO);
//												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//												tempAss.setContainerStatus(con.getContainerStatus());
//												tempAss.setGateOutId(con.getGateOutId());
//												tempAss.setGatePassNo(con.getGatePassNo());
//												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
//														? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//												tempAss.setTaxId(String.valueOf(f[11]));
//												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//												BigDecimal totalRate = new BigDecimal(
//														String.valueOf(finalRangeValue[8]));
//												tempAss.setServiceRate(totalRate);
//												tempAss.setRates(totalRate);
//
//											}
//
//										} else if ("TEU".equals(unit) || "SM".equals(unit) || "CNTR".equals(unit)
//												|| "PBL".equals(unit) || "ACT".equals(unit) || "PCK".equals(unit)
//												|| "SHFT".equals(unit) || "KG".equals(unit)) {
//
//											if ("SM".equals(unit) || "CNTR".equals(unit) || "PBL".equals(unit)
//													|| "ACT".equals(unit) || "PCK".equals(unit)
//													|| "SHFT".equals(unit)) {
//												executionUnit = new BigDecimal("1");
//												tempAss.setExecutionUnit(String.valueOf(executionUnit));
//												tempAss.setExecutionUnit1("");
//											}
//
//											if ("TEU".equals(unit)) {
//												if ("20".equals(con.getContainerSize())
//														|| "22".equals(con.getContainerSize())) {
//													executionUnit = new BigDecimal("1");
//													tempAss.setExecutionUnit(String.valueOf(executionUnit));
//													tempAss.setExecutionUnit1("");
//												} else if ("40".equals(con.getContainerSize())) {
//													executionUnit = new BigDecimal("2");
//													tempAss.setExecutionUnit(String.valueOf(executionUnit));
//													tempAss.setExecutionUnit1("");
//												} else if ("45".equals(con.getContainerSize())) {
//													executionUnit = new BigDecimal("2.5");
//													tempAss.setExecutionUnit(String.valueOf(executionUnit));
//													tempAss.setExecutionUnit1("");
//												}
//											}
//
//											if ("KG".equals(unit)) {
////												String serviceGrp = String.valueOf(f[18]);
//												if ("H".equals(serviceGrp)) {
//
//													BigDecimal cargoWt = new BigDecimal(
//															String.valueOf(con.getGrossWeight()));
//
//													executionUnit = cargoWt;
//
//													tempAss.setExecutionUnit(String.valueOf(executionUnit));
//													tempAss.setExecutionUnit1("");
//												} else {
//													BigDecimal cargoWt = con.getCargoWeight();
//
//													executionUnit = cargoWt;
//													tempAss.setExecutionUnit(String.valueOf(executionUnit));
//													tempAss.setExecutionUnit1("");
//												}
//											}
//
//											Object rangeValue = cfstariffservicerepo.getRangeDataByServiceId(cid, bid,
//													String.valueOf(f[3]), String.valueOf(f[4]), String.valueOf(f[0]),
//													executionUnit, conSize2, conTypeOfCon2, commodityType,
//													con.getContainerSize(), con.getTypeOfContainer(),
//													assessment.getCommodityCode());
//
//											if (rangeValue != null) {
//												Object[] finalRangeValue = (Object[]) rangeValue;
//												tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//												tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//												tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//												tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//												tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//												tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//
//												tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//												tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//												tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//												tempAss.setRangeFrom(
//														f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//																: BigDecimal.ZERO);
//												tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//														: BigDecimal.ZERO);
//												tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//												tempAss.setContainerStatus(con.getContainerStatus());
//												tempAss.setGateOutId(con.getGateOutId());
//												tempAss.setGatePassNo(con.getGatePassNo());
//												tempAss.setTaxPerc((f[12] == null || String.valueOf(f[12]).isEmpty())
//														? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//												tempAss.setTaxId(String.valueOf(f[11]));
//												tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//												BigDecimal totalRate = new BigDecimal(
//														String.valueOf(finalRangeValue[8]));
//
//												tempAss.setServiceRate(totalRate);
//												tempAss.setRates(totalRate);
//											}
//
//										}
//
//										else {
//											tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//											tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//											tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//											tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//											tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//											tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//
//											tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//											tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//											tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//											tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//													: BigDecimal.ZERO);
//											tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//													: BigDecimal.ZERO);
//											tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//											tempAss.setContainerStatus(con.getContainerStatus());
//											tempAss.setGateOutId(con.getGateOutId());
//											tempAss.setGatePassNo(con.getGatePassNo());
//											tempAss.setExecutionUnit(String.valueOf(1));
//											tempAss.setExecutionUnit1("");
//											tempAss.setTaxPerc(
//													(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
//															: new BigDecimal(String.valueOf(f[12])));
//											tempAss.setTaxId(String.valueOf(f[11]));
//											tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//											BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
//
//											tempAss.setServiceRate(totalRate);
//											tempAss.setRates(totalRate);
//										}
//
//									}
////									ELSE PLAIN
//									else {
//										System.out.println("----------15");
//										tempAss.setServiceGroup(f[14] != null ? String.valueOf(f[14]) : "");
//										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
//										tempAss.setServiceName(f[18] != null ? String.valueOf(f[18]) : "");
//										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
//										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
//										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
//
//										tempAss.setWoNo(f[3] != null ? String.valueOf(f[3]) : "");
//										tempAss.setWoAmndNo(f[4] != null ? String.valueOf(f[4]) : "");
//										tempAss.setCriteria(f[8] != null ? String.valueOf(f[8]) : "");
//										tempAss.setRangeFrom(f[15] != null ? new BigDecimal(String.valueOf(f[15]))
//												: BigDecimal.ZERO);
//										tempAss.setRangeTo(f[16] != null ? new BigDecimal(String.valueOf(f[16]))
//												: BigDecimal.ZERO);
//										tempAss.setAcCode(f[13] != null ? String.valueOf(f[13]) : "");
//										tempAss.setContainerStatus(con.getContainerStatus());
//										tempAss.setGateOutId(con.getGateOutId());
//										tempAss.setGatePassNo(con.getGatePassNo());
//										tempAss.setExecutionUnit(String.valueOf(1));
//										tempAss.setExecutionUnit1("");
//										tempAss.setTaxPerc(
//												(f[12] == null || String.valueOf(f[12]).isEmpty()) ? BigDecimal.ZERO
//														: new BigDecimal(String.valueOf(f[12])));
//										tempAss.setTaxId(String.valueOf(f[11]));
//										tempAss.setExRate(new BigDecimal(String.valueOf(f[9])));
//										BigDecimal totalRate = new BigDecimal(String.valueOf(f[2]));
//										tempAss.setServiceRate(totalRate);
//										tempAss.setRates(totalRate);
//									}
//
//									finalConData.add(tempAss);
//
//								} catch (CloneNotSupportedException e) {
//									e.printStackTrace();
//								}
//							}
//						}
//
////							ENDING IF SERVICE ID IS NOT FROM SSR
//
//					});
////						ENDING LOOP FOR ALL DISTINCT SERVICE IDS
//					
//					
//					
////					List<ExportContainerAssessmentData> newAnxData = finalConData.stream().map(item -> {
////						try {
////							return (ExportContainerAssessmentData) item.clone(); // Clone first
////						} catch (CloneNotSupportedException e) {
////							e.printStackTrace();
////							return null;
////						}
////					}).filter(Objects::nonNull) // Remove null values from failed clones
////							.filter(item -> item.getContainerNo().equals(con.getContainerNo())) // Filter after
////																								// cloning
////							.collect(Collectors.toList());
////					
//					
//							for(ExportContainerAssessmentData fnewAnxData : finalConData)
//							{
//								
//								System.out.println("fnewAnxData " + fnewAnxData.getContainerNo() + " " + fnewAnxData.getServiceId() + " " + fnewAnxData.getRates());
//							}
//					
//					
//					
//					List<ExportContainerAssessmentData> newAnxData = finalConData.stream()
//						    .map(item -> {
//						        try {
//						            return (ExportContainerAssessmentData) item.clone(); // Clone first
//						        } catch (CloneNotSupportedException e) {
//						            e.printStackTrace();
//						            return null;
//						        }
//						    })
//						    .filter(Objects::nonNull) // Remove null values from failed clones
//						    .filter(item -> Objects.equals(item.getContainerNo(), con.getContainerNo())) // Safe comparison
//						    .collect(Collectors.toList());
//
//					for(ExportContainerAssessmentData f : newAnxData)
//					{
//						
//						System.out.println(" newAnxData " + f.getContainerNo() + " " + f.getServiceId() + " " + f.getRates());
//					}
//					
//
//					System.out.println("Here Before ---> ENDING LOOP FOR ALL DISTINCT SERVICE IDS");
//
//					AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();
//
//					AssessmentSheet newAss = tempAssessment;
//
//					newAss.setCompanyId(cid);
//					newAss.setBranchId(bid);
//					newAss.setAssesmentLineNo(String.valueOf(sr));
//					newAss.setStatus('A');
//					newAss.setCreatedBy(user);
//					newAss.setCreatedDate(new Date());
//					newAss.setApprovedBy(user);
//					newAss.setApprovedDate(new Date());
//					newAss.setAssesmentDate(new Date());
//					newAss.setTransType("Export Container");
//					newAss.setProfitcentreId(con.getProfitcentreId());
//
//					newAss.setAssesmentId(HoldNextIdD1);
//
//					if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'Y') {
//						newAss.setIgst("Y");
//						newAss.setCgst("N");
//						newAss.setSgst("N");
//					} else if (assessment.getSez() == 'Y' && assessment.getTaxApplicable() == 'N') {
//						newAss.setIgst("E");
//						newAss.setCgst("N");
//						newAss.setSgst("N");
//					}
//
//					else if (assessment.getSez() == 'N' && assessment.getTaxApplicable() == 'Y') {
//
//						Branch b = branchRepo.getDataByCompanyAndBranch(cid, bid);
//
//						String billingId = "IMP".equals(assessment.getBillingParty()) ? assessment.getImporterId()
//								: "CHA".equals(assessment.getBillingParty()) ? assessment.getCha()
//										: "FWR".equals(assessment.getBillingParty()) ? assessment.getOnAccountOf()
//												: assessment.getOthPartyId();
//
//						String billingAdd = "IMP".equals(assessment.getBillingParty())
//								? String.valueOf(assessment.getImpSrNo())
//								: "CHA".equals(assessment.getBillingParty()) ? String.valueOf(assessment.getChaSrNo())
//										: "FWR".equals(assessment.getBillingParty())
//												? String.valueOf(assessment.getAccSrNo())
//												: assessment.getOthSrNo();
//
//						PartyAddress p = partyRepo.getData(cid, bid, billingId, billingAdd);
//
//						newAss.setPartyId(billingId);
//						newAss.setPartySrNo(new BigDecimal(billingAdd));
//
//						if (b.getState().equals(p.getState())) {
//							newAss.setIgst("N");
//							newAss.setCgst("Y");
//							newAss.setSgst("Y");
//						} else {
//							newAss.setIgst("Y");
//							newAss.setCgst("N");
//							newAss.setSgst("N");
//						}
//					} else {
//						newAss.setIgst("N");
//						newAss.setCgst("N");
//						newAss.setSgst("N");
//					}
//
//					System.out.println("Here Before ---> List<ExportContainerAssessmentData> filteredData");
//
//					List<ExportContainerAssessmentData> filteredData = newAnxData.stream()
//							.filter(c -> "H".equals(c.getServiceGroup())).collect(Collectors.toList());
//
//
//					BigDecimal totalRates = filteredData.stream().map(ExportContainerAssessmentData::getRates)
//							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);
//
//					System.out.println("totalRates : " + totalRates);
//
//					List<ExportContainerAssessmentData> filteredData1 = newAnxData.stream()
//							.filter(c -> "G".equals(c.getServiceGroup())).collect(Collectors.toList());
//					
//					
//					
//					
//					
//
//					BigDecimal totalRates1 = filteredData1.stream().map(ExportContainerAssessmentData::getRates)
//							.reduce(BigDecimal.ZERO, BigDecimal::add).setScale(3, RoundingMode.HALF_UP);
//
//					BigDecimal zero = BigDecimal.ZERO;
//
//					newAss.setContainerNo(con.getContainerNo());
//					newAss.setContainerSize(con.getContainerSize());
//					newAss.setContainerType(con.getContainerType());
//					newAss.setGateInDate(con.getGateInDate());
//					newAss.setGateOutDate(con.getGateoutDate() == null ? null : con.getGateoutDate());
//					newAss.setGrossWeight(con.getGrossWeight());
//					newAss.setGateOutType(con.getGateOutType());
//					newAss.setAgroProductStatus("AGRO".equals(assessment.getCommodityCode()) ? "Y" : "N");
//					newAss.setTypeOfContainer(con.getTypeOfContainer());
//					newAss.setScanerType(con.getScannerType());
//					newAss.setExaminedPercentage(zero);
//					newAss.setWeighmentFlag("N");
//					newAss.setCargoWeight(con.getCargoWeight());
//					newAss.setDestuffDate(con.getGateInDate() == null ? null : con.getGateInDate());
//					newAss.setCalculateInvoice('N');
//					newAss.setInvoiceUptoDate(finalConData.get(0).getInvoiceDate());
//					newAss.setContainerHandlingAmt(totalRates);
//					newAss.setContainerStorageAmt(totalRates1);
//					newAss.setInvoiceCategory(assessment.getInvoiceCategory());
//					newAss.setInvType("First");
//					newAss.setSa(tempAssessment.getSaId());
//					newAss.setSl(tempAssessment.getSlId());
//					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
//					newAss.setSsrServiceId(con.getSsrTransId());
//					newAss.setStuffTallyDate(con.getStuffTallyDate());
//					newAss.setGateOutDate(con.getGateOutDate());
//					newAss.setMovReqDate(con.getMovementDate());
//					newAss.setMinCartingTransDate(con.getCartingDate());
//					newAss.setCartingTransId(con.getCartingTransId());
//
//					newAss.setInvoiceNo(null);
//					assessmentsheetrepo.save(newAss);
//					processnextidrepo.updateAuditTrail(cid, bid, "P01103", HoldNextIdD1, "2024");
//
//					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
//					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
//
//					System.out.println("Here Before ---> finalConData.stream().forEach(c -> {");
//
//					newAnxData.stream().forEach(c -> {
//						
//						if(c.getContainerNo().equals(con.getContainerNo()))
//								{
//
//						if ("C".equals(c.serviceGroup)) {
//
//							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
//								Cfinvsrvanx anx = new Cfinvsrvanx();
//								anx.setCompanyId(cid);
//								anx.setBranchId(bid);
//								anx.setErpDocRefNo(newAss.getSbTransId());
//								anx.setProcessTransId(HoldNextIdD1);
//								anx.setServiceId(c.getServiceId());
//								anx.setSrlNo(srNo1.get());
//								anx.setDocRefNo(newAss.getSbNo());
//								anx.setIgmLineNo("");
//								anx.setProfitcentreId(newAss.getProfitcentreId());
//								anx.setInvoiceType("EXP");
//								anx.setServiceUnit(c.getServiceUnit());
//								anx.setExecutionUnit(c.getExecutionUnit());
//								anx.setServiceUnit1(c.getServiceUnit1());
//								anx.setExecutionUnit1(c.getExecutionUnit1());
//								anx.setRate(c.getServiceRate());
//								anx.setActualNoOfPackages(c.getRates());
//								anx.setCurrencyId("INR");
//								anx.setLocalAmt(c.getRates());
//								anx.setInvoiceAmt(c.getRates());
//								anx.setCommodityDescription(newAss.getCommodityDescription());
//								anx.setDiscPercentage(zero);
//								anx.setDiscValue(zero);
//								anx.setmPercentage(zero);
//								anx.setmAmount(c.getmAmount());
//								anx.setAcCode(c.getAcCode());
//								anx.setProcessTransDate(newAss.getAssesmentDate());
//								anx.setProcessId("P01103");
//								anx.setPartyId(newAss.getPartyId());
//								anx.setWoNo(c.getWoNo());
//								anx.setWoAmndNo(c.getWoAmndNo());
//								anx.setContainerNo(c.getContainerNo());
//								anx.setContainerStatus(con.getContainerStatus());
//								anx.setGateOutDate(c.getGateoutDate());
//								anx.setAddServices("N");
//								anx.setStartDate(c.getGateInDate());
//								anx.setInvoiceUptoDate(c.getInvoiceDate());
//								anx.setInvoiceUptoWeek(c.getInvoiceDate());
//								anx.setStatus("A");
//								anx.setCreatedBy(user);
//								anx.setCreatedDate(new Date());
//								anx.setCargoSBNo(c.getCargoSbNo());
//								anx.setFreeDays(c.getFreeDays());
//
//								anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
//								anx.setSrvManualFlag("N");
//								anx.setCriteria("SA".equals(c.getCriteria()) ? "DW"
//										: "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
//								anx.setRangeFrom(c.getRangeFrom());
//								anx.setRangeTo(c.getRangeTo());
//								anx.setRangeType(c.getContainerStatus());
//								anx.setGateOutId(c.getGateOutId());
//								anx.setGatePassNo(c.getGatePassNo());
//								anx.setRangeType(c.getCriteria());
//								anx.setTaxId(c.getTaxId());
//								anx.setExRate(c.getExRate());
//								anx.setProcessTransLineId(newAss.getAssesmentLineNo());
//
//								if (("Y".equals(newAss.getIgst()))
//										|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
//									anx.setTaxPerc(c.getTaxPerc());
//								} else {
//									anx.setTaxPerc(BigDecimal.ZERO);
//								}
//
//								BigDecimal currentRate = c.getRates() != null ? c.getRates() : BigDecimal.ZERO;
//								BigDecimal currentTotal = totalAmount.get() != null ? totalAmount.get()
//										: BigDecimal.ZERO;
//
//								// Perform addition
//
//								totalAmount.set(currentTotal.add(currentRate));
//
//								cfinvsrvanxrepo.save(anx);
//
//								srNo1.set(srNo1.get().add(new BigDecimal(1)));
//							}
//
//						} else {
//
//							if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
//								Cfinvsrvanx anx = new Cfinvsrvanx();
//								anx.setCompanyId(cid);
//								anx.setBranchId(bid);
//								anx.setErpDocRefNo(newAss.getSbTransId());
//								anx.setProcessTransId(HoldNextIdD1);
//								anx.setServiceId(c.getServiceId());
//								anx.setSrlNo(srNo1.get());
//								anx.setDocRefNo(newAss.getSbNo());
//								anx.setProfitcentreId(newAss.getProfitcentreId());
//								anx.setInvoiceType("EXP");
//								anx.setServiceUnit(c.getServiceUnit());
//								anx.setExecutionUnit(c.getExecutionUnit());
//								anx.setServiceUnit1(c.getServiceUnit1());
//								anx.setExecutionUnit1(c.getExecutionUnit1());
//								anx.setRate(c.getServiceRate());
//								anx.setActualNoOfPackages(c.getRates());
//								anx.setCurrencyId("INR");
//								anx.setLocalAmt(c.getRates());
//								anx.setInvoiceAmt(c.getRates());
//								anx.setCommodityDescription(newAss.getCommodityDescription());
//								anx.setDiscPercentage(c.getDiscPercentage());
//								anx.setDiscValue(c.getDiscValue());
//								anx.setmPercentage(c.getmPercentage());
//								anx.setmAmount(c.getmAmount());
//								anx.setAcCode(c.getAcCode());
//								anx.setProcessTransDate(newAss.getAssesmentDate());
//								anx.setProcessId("P01103");
//								anx.setPartyId(newAss.getPartyId());
//								anx.setWoNo(c.getWoNo());
//								anx.setWoAmndNo(c.getWoAmndNo());
//								anx.setContainerNo(c.getContainerNo());
//								anx.setContainerStatus(con.getContainerStatus());
//								anx.setGateOutDate(c.getGateoutDate());
//								anx.setAddServices("N");
//								anx.setStartDate(c.getGateInDate());
//								anx.setInvoiceUptoDate(c.getInvoiceDate());
//								anx.setInvoiceUptoWeek(c.getInvoiceDate());
//								anx.setStatus("A");
//								anx.setCreatedBy(user);
//								anx.setCreatedDate(new Date());
//								anx.setFreeDays(c.getFreeDays());
//
//								anx.setTaxApp(String.valueOf(newAss.getTaxApplicable()));
//								anx.setSrvManualFlag("N");
//								anx.setCriteria("SA".equals(c.getCriteria()) ? "DW"
//										: "RA".equals(c.getCriteria()) ? "WT" : "CNTR");
//								anx.setRangeFrom(c.getRangeFrom());
//								anx.setRangeTo(c.getRangeTo());
//								anx.setRangeType(c.getContainerStatus());
//								anx.setGateOutId(c.getGateOutId());
//								anx.setGatePassNo(c.getGatePassNo());
//								anx.setRangeType(c.getCriteria());
//								anx.setTaxId(c.getTaxId());
//								anx.setExRate(c.getExRate());
//								anx.setProcessTransLineId(newAss.getAssesmentLineNo());
//
//								if (("Y".equals(newAss.getIgst()))
//										|| ("Y".equals(newAss.getCgst()) && "Y".equals(newAss.getSgst()))) {
//									anx.setTaxPerc(c.getTaxPerc());
//								} else {
//									anx.setTaxPerc(BigDecimal.ZERO);
//								}
//
//								BigDecimal currentRate = c.getRates() != null ? c.getRates() : BigDecimal.ZERO;
//								BigDecimal currentTotal = totalAmount.get() != null ? totalAmount.get()
//										: BigDecimal.ZERO;
//
//								// Perform addition
//								totalAmount.set(currentTotal.add(currentRate));
//
//								cfinvsrvanxrepo.save(anx);
//
//								srNo1.set(srNo1.get().add(new BigDecimal(1)));
//							}
//						}
//						
//						
//						
//								}
//						
//					}
//					
//							
//							
//							
//							
//							
//							
//							);
//
//					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));
//
//					int updateExpInventoryContainerInvoice = exportInvoiceRepo.updateExpInventoryContainerInvoice(
//							HoldNextIdD1, "Y", cid, bid, con.getStuffTallyId(), con.getContainerNo());
//
//					int updateStuffTallyContainerInvoice = exportInvoiceRepo.updateStuffTallyContainerInvoice(
//							HoldNextIdD1, cid, bid, con.getStuffTallyId(), con.getContainerNo());
//
//					int updateMovementRequestContainerInvoice = exportInvoiceRepo.updateMovementRequestContainerInvoice(
//							HoldNextIdD1, cid, bid, con.getStuffTallyId(), con.getMovementReqId(),
//							con.getContainerNo());
//
//					System.out.println("updateExpInventoryContainerInvoice : " + updateExpInventoryContainerInvoice
//							+ " updateStuffTallyContainerInvoice : " + updateStuffTallyContainerInvoice
//							+ " updateMovementRequestContainerInvoice: " + updateMovementRequestContainerInvoice);
//
//					assessment.setAssesmentId(HoldNextIdD1);
//
//					sr++;
//
//				}
//			} else {
//
//				int updateAssessmentContainerEdit = exportInvoiceRepo.updateAssessmentContainerEdit(cid, bid,
//						assessment.getProfitcentreId(), assessment.getAssesmentId(), assessment.getComments(),
//						assessment.getIntComments());
//
//				System.out.println("updateAssessmentContainerEdit : " + updateAssessmentContainerEdit);
//
//			}
//
//			System.out.println("Before ------ selectedExportAssesmentSheet ");
//			AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
//					assessment.getProfitcentreId(), assessment.getAssesmentId());
//
//			
//			List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
//					.getSelectedExportAssesmentSheetContainerData(cid, bid, assessment.getProfitcentreId(),
//							assessment.getAssesmentId());
//
//			System.out.println("Before ------ cargoStorageServiceId");
//
//			String cargoStorageServiceId = exportInvoiceRepo.getCargoStorageServiceId(cid, bid);
//			List<Object[]> cargoStorageOfAssessmentSheetDetails = exportInvoiceRepo
//					.getCargoStorageOfAssessmentSheetDetails(cid, bid, assessment.getSbTransId(),
//							assessment.getAssesmentId(), cargoStorageServiceId);
//
//			
//			
//			
//			
//			
//			
//			if (selectedExportAssesmentSheetContainerData.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			} else {
//				AtomicReference<BigDecimal> totalRateWithoutTax = new AtomicReference<>(BigDecimal.ZERO);
//				AtomicReference<BigDecimal> totalRateWithTax = new AtomicReference<>(BigDecimal.ZERO);
//
//				selectedExportAssesmentSheetContainerData.stream().forEach(r -> {
//					
////					BigDecimal rate = new BigDecimal(String.valueOf(r.getRates()));
////					BigDecimal taxPerc = new BigDecimal(String.valueOf(r.getTaxPerc()));
//					BigDecimal rate = Optional.ofNullable(r.getRates())
//	                         .map(Object::toString)
//	                         .filter(s -> s.matches("-?\\d+(\\.\\d+)?"))
//	                         .map(BigDecimal::new)
//	                         .orElse(BigDecimal.ZERO);
//
//	BigDecimal taxPerc = Optional.ofNullable(r.getTaxPerc())
//	                             .map(Object::toString)
//	                             .filter(s -> s.matches("-?\\d+(\\.\\d+)?"))
//	                             .map(BigDecimal::new)
//	                             .orElse(BigDecimal.ZERO);
//					
//
//					BigDecimal taxAmt = (rate.multiply(taxPerc)).divide(new BigDecimal(100), RoundingMode.HALF_UP);
//					totalRateWithoutTax.set(totalRateWithoutTax.get().add(rate));
//					totalRateWithTax.set(totalRateWithTax.get().add(rate.add(taxAmt)));
//				});
//
//				// Get the final totals
//				BigDecimal finaltotalRateWithoutTax = BigDecimal.ZERO;
//				BigDecimal finaltotalRateWithTax = BigDecimal.ZERO;
//
//				String invRoundStatus = branchRepo.getInvoiceRoundOffStatus(cid, bid);
//
//				if (invRoundStatus != null && !invRoundStatus.isEmpty() && "N".equals(invRoundStatus)) {
//					finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(3, RoundingMode.HALF_UP);
//					finaltotalRateWithTax = totalRateWithTax.get().setScale(3, RoundingMode.HALF_UP);
//				} else if (invRoundStatus != null && !invRoundStatus.isEmpty() && "Y".equals(invRoundStatus)) {
//					finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(0, RoundingMode.HALF_UP);
//					finaltotalRateWithTax = totalRateWithTax.get().setScale(0, RoundingMode.HALF_UP);
//				} else {
//					finaltotalRateWithoutTax = totalRateWithoutTax.get().setScale(3, RoundingMode.HALF_UP);
//					finaltotalRateWithTax = totalRateWithTax.get().setScale(3, RoundingMode.HALF_UP);
//				}
//
//				String pId = "";
//
//				if ("CHA".equals(assessment.getBillingParty())) {
//
//					pId = assessment.getCha();
//
//				} else if ("IMP".equals(assessment.getBillingParty())) {
//					pId = assessment.getImporterId();
//				} else if ("FWR".equals(assessment.getBillingParty())) {
//					pId = assessment.getOnAccountOf();
//				} else if ("OTH".equals(assessment.getBillingParty())) {
//					pId = assessment.getOthPartyId();
//				}
//
//				Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);
//
//				Map<String, Object> finalResult = new HashMap<>();
//				finalResult.put("finaltotalRateWithoutTax", finaltotalRateWithoutTax);
//				finalResult.put("finaltotalRateWithTax", finaltotalRateWithTax);
//
//				if (p != null) {
//					finalResult.put("tanNo", p.getTanNoId());
//					finalResult.put("tdsPerc", p.getTdsPercentage());
//				}
//
//				List<Object[]> filteredData = cargoStorageOfAssessmentSheetDetails.stream()  // Convert List<Object[]> to Stream<Object[]>
//					    .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))  // Keep rows that have at least one non-null value
//					    .collect(Collectors.toList());  // Convert back to List<Object[]>
//
//				
//			finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
//			finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);
//			finalResult.put("sbData", filteredData);
//
//			return new ResponseEntity<>(finalResult, HttpStatus.OK);
//			}
//			// return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	int safeInteger(Object obj) {
		try {
			return (obj == null || obj.toString().isEmpty()) ? 0 : Integer.parseInt(obj.toString());
		} catch (NumberFormatException e) {
			return 0; // Default to 0 in case of a parsing error
		}
	}

	Date safeDate(Object obj) {
		if (obj == null) {
			return new Date(0); // Default to "epoch" time if date is null
		}

		if (obj instanceof Date) {
			return (Date) obj;
		}

		// Define a list of possible date formats (date and time formats)
		String[] dateFormats = { "yyyy-MM-dd", // Date only
				"yyyy-MM-dd HH:mm:ss", // Date with time
				"yyyy-MM-dd HH:mm:ss.SSS" // Date with time and milliseconds
		};

		// Try parsing with different formats
		for (String format : dateFormats) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				return sdf.parse(obj.toString()); // Try to parse the string as date and time
			} catch (ParseException e) {
				// If parsing fails, continue with next format
			}
		}

		return new Date(0); // Return epoch time on error if no format works
	}

	private String safeString(Object obj) {
		return (obj == null || obj.toString().isEmpty()) ? "" : obj.toString();
	}

	
	private BigDecimal safeBigDecimal(Object obj) {
	    try {
	        return (obj == null || obj.toString().trim().isEmpty()) 
	            ? BigDecimal.ZERO 
	            : new BigDecimal(obj.toString().trim());
	    } catch (NumberFormatException e) {
	        return BigDecimal.ZERO;
	    }
	}

	
	
	
	
	private AssessmentSheet getSelectedAssesmentData(String companyId, String branchId, String profitcentreId,
			String assesmentId) {

		System.out.println("companyId " + companyId + " branchId " + branchId + " profitcentreId " + profitcentreId
				+ " assesmentId " + assesmentId + " ");
		Object[] selectedExportAssesmentSheetRec = exportInvoiceRepo.getSelectedExportAssesmentSheet(companyId,
				branchId, profitcentreId, assesmentId);

		if (selectedExportAssesmentSheetRec == null || selectedExportAssesmentSheetRec.length == 0) {
			return null; // Return null if no data is found
		}

		Object[] selectedExportAssesmentSheet = (Object[]) selectedExportAssesmentSheetRec[0];

		// Map the Object[] result to an AssessmentSheet object with default value
		// handling
		AssessmentSheet assessmentSheet = new AssessmentSheet(safeString(selectedExportAssesmentSheet[0]), // companyId
				safeString(selectedExportAssesmentSheet[1]), // branchId
				safeString(selectedExportAssesmentSheet[2]), // assesmentId
				safeString(selectedExportAssesmentSheet[3]), // assesmentLineNo
				safeString(selectedExportAssesmentSheet[4]), // transType
				safeDate(selectedExportAssesmentSheet[5]), // assesmentDate (Date handling)
				safeString(selectedExportAssesmentSheet[6]), // sbNo
				safeString(selectedExportAssesmentSheet[7]), // sbTransId
				safeDate(selectedExportAssesmentSheet[8]), // sbDate (Date handling)
				safeString(selectedExportAssesmentSheet[9]).charAt(0), // status (converting String to char)
				safeString(selectedExportAssesmentSheet[10]), // createdBy
				safeString(selectedExportAssesmentSheet[11]), // profitcentreId
				safeString(selectedExportAssesmentSheet[12]), // profitcentreName
				safeString(selectedExportAssesmentSheet[13]), // billingParty
				safeString(selectedExportAssesmentSheet[14]), // sl
				safeString(selectedExportAssesmentSheet[15]), // sa
				safeString(selectedExportAssesmentSheet[16]), // commodityDescription
				safeDate(selectedExportAssesmentSheet[17]), // minCartingTransDate (Date handling)
				safeString(selectedExportAssesmentSheet[18]), // importerId
				safeString(selectedExportAssesmentSheet[19]), // exporterName
				safeString(selectedExportAssesmentSheet[20]), // expAddress
				safeString(selectedExportAssesmentSheet[21]), // expGst
				safeString(selectedExportAssesmentSheet[22]), // expStateCode
				safeInteger(selectedExportAssesmentSheet[23]), // impSrNo
				safeString(selectedExportAssesmentSheet[24]), // cha
				safeString(selectedExportAssesmentSheet[25]), // chaName
				safeString(selectedExportAssesmentSheet[26]), // chaAddress
				safeString(selectedExportAssesmentSheet[27]), // chaGst
				safeString(selectedExportAssesmentSheet[28]), // chaStateCode
				safeInteger(selectedExportAssesmentSheet[29]), // chaSrNo
				safeString(selectedExportAssesmentSheet[30]), // othPartyId
				safeString(selectedExportAssesmentSheet[31]), // accHolderName
				safeString(selectedExportAssesmentSheet[32]), // accAddress
				safeString(selectedExportAssesmentSheet[33]), // accHolderGst
				safeString(selectedExportAssesmentSheet[34]), // accStateCode
				safeString(selectedExportAssesmentSheet[35]), // othSrNo
				safeString(selectedExportAssesmentSheet[36]), // onAccountOf
				safeString(selectedExportAssesmentSheet[37]), // fwdName
				safeString(selectedExportAssesmentSheet[38]), // fwdAddress
				safeString(selectedExportAssesmentSheet[39]), // fwdGst
				safeString(selectedExportAssesmentSheet[40]), // fwdState
				safeInteger(selectedExportAssesmentSheet[41]), // accSrNo
				safeString(selectedExportAssesmentSheet[42]), // invoiceNo
				safeDate(selectedExportAssesmentSheet[43]), // invoiceDate (Date handling)
				safeString(selectedExportAssesmentSheet[44]).charAt(0), // taxApplicable
				safeString(selectedExportAssesmentSheet[45]).charAt(0), // sez
				safeString(selectedExportAssesmentSheet[46]), // commodityCode
				safeString(selectedExportAssesmentSheet[47]).charAt(0), // creditType
				safeString(selectedExportAssesmentSheet[48]), // invoiceCategory
				safeString(selectedExportAssesmentSheet[49]), // irn
				safeString(selectedExportAssesmentSheet[50]), // receiptNo
				safeBigDecimal(selectedExportAssesmentSheet[51]), // creditAllowed
				safeBigDecimal(selectedExportAssesmentSheet[52]), // pendingCredit
				safeString(selectedExportAssesmentSheet[53]), // comments
				safeString(selectedExportAssesmentSheet[54]), // intComments
				safeString(selectedExportAssesmentSheet[55]), // partyId,
				
				safeString(selectedExportAssesmentSheet[56]), // cgst
				safeString(selectedExportAssesmentSheet[57]), // sgst
				safeString(selectedExportAssesmentSheet[58]) // igst,
				
				
				
		);

		return assessmentSheet;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	ECPORT CONTAINER INVOICE PROCESS
	
	

	
	
	@Transactional
	public ResponseEntity<?> processExportAssesmentContainer(String cid, String bid, String user, Map<String, Object> data, String creditStatus)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<ExportContainerAssessmentData> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<ExportContainerAssessmentData>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}

		List<PaymentReceiptDTO> paymentDto = mapper.readValue(mapper.writeValueAsString(data.get("paymentDto")),
				new TypeReference<List<PaymentReceiptDTO>>() {
				});

		if (paymentDto.isEmpty()) {
			return new ResponseEntity<>("Payment details not found", HttpStatus.CONFLICT);
		}

		List<AssessmentSheet> oldAssessmentData = assessmentsheetrepo.getDataByAssessmentId1(cid, bid, assessment.getAssesmentId());

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
			processNextId = "P01123";
		} else {
			processNextId = "P01113";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);
		
		
		AssessmentSheet assSheet = oldAssessmentData.get(0);
		Cfinvsrv srv = new Cfinvsrv();
		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		
		
		
		System.out.println(" creditStatus "+ creditStatus);
		if("Y".equalsIgnoreCase(creditStatus))
		{		
			System.out.println(" creditStatus 1"+ creditStatus);
			oldAssessmentData.stream().forEach(o -> {

				Cfinvsrvanxback back = new Cfinvsrvanxback();
				back.setCompanyId(cid);
				back.setBranchId(bid);
				back.setStatus("A");
				back.setProcessTransId(o.getAssesmentId());
				back.setErpDocRefNo(o.getSbTransId());
				back.setDocRefNo(o.getSbNo());
				back.setIgmLineNo(o.getIgmLineNo());
				back.setServiceId(oldAnxData.get(0).getServiceId());
				back.setTaxId(oldAnxData.get(0).getTaxId());
				back.setSrlNo(srNo.get());
				back.setProfitCentreId(o.getProfitcentreId());
				back.setInvoiceNo(HoldNextIdD1);
				back.setInvoiceDate(new Date());
				back.setInvoiceType("EXP");
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
			srv.setInvoiceType("EXP");
			srv.setTransType("CONT");
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
						: "EXP".equals(tdsDeductee) ? assSheet.getImporterId()
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
							: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

				int updateAssessmentId = ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(),
						o.getAssesmentId());


			});

			
			
			
			
			
			
			
		}
//		Credit END 
		
//		PDA START
		else if("P".equalsIgnoreCase(creditStatus))
		{
			System.out.println(" creditStatus 2"+ creditStatus);
			
			
			
			
			
			oldAssessmentData.stream().forEach(o -> {

				Cfinvsrvanxback back = new Cfinvsrvanxback();
				back.setCompanyId(cid);
				back.setBranchId(bid);
				back.setStatus("A");
				back.setProcessTransId(o.getAssesmentId());
				back.setErpDocRefNo(o.getSbTransId());
				back.setDocRefNo(o.getSbNo());
				back.setIgmLineNo(o.getIgmLineNo());
				back.setServiceId(oldAnxData.get(0).getServiceId());
				back.setTaxId(oldAnxData.get(0).getTaxId());
				back.setSrlNo(srNo.get());
				back.setProfitCentreId(o.getProfitcentreId());
				back.setInvoiceNo(HoldNextIdD1);
				back.setInvoiceDate(new Date());
				back.setInvoiceType("EXP");
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
			srv.setInvoiceType("EXP");
			srv.setTransType("CONT");
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
							: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

				int updateAssessmentId = ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(),
						o.getAssesmentId());

				System.out.println("o.getInvType() " + o.getInvType());


			});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
//		PDA END
//		NORMAL START
		else {
			
			System.out.println(" creditStatus 3"+ creditStatus);
		oldAssessmentData.stream().forEach(o -> {

			Cfinvsrvanxback back = new Cfinvsrvanxback();
			back.setCompanyId(cid);
			back.setBranchId(bid);
			back.setStatus("A");
			back.setProcessTransId(o.getAssesmentId());
			back.setErpDocRefNo(o.getSbTransId());
			back.setDocRefNo(o.getSbNo());
			back.setIgmLineNo("");
			back.setServiceId(oldAnxData.get(0).getServiceId());
			back.setTaxId(oldAnxData.get(0).getTaxId());
			back.setSrlNo(srNo.get());
			back.setProfitCentreId(o.getProfitcentreId());
			back.setInvoiceNo(HoldNextIdD1);
			back.setInvoiceDate(new Date());
			back.setInvoiceType("EXP");
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
			back.setAddServices(oldAnxData.get(0).getAddServices());
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

		
		
		List<Cfinvsrvanx> clonedList = new ArrayList<>();
		oldAnxData.stream().forEach(item -> {
			try {
				clonedList.add(item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Assuming `clone()` is properly overridden
		});
		
//		List<Cfinvsrvanx> clonedList = oldAnxData.stream()
//		        .map(Cfinvsrvanx::clone) // Calls clone() method
//		        .collect(Collectors.toList());
		
		Map<String, Cfinvsrvanx> groupedData = clonedList.stream().collect(Collectors.toMap(Cfinvsrvanx::getServiceId, // Key:
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
		srv.setInvoiceType("EXP");
		srv.setTransType("CONT");
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

		System.out.println(" processNextId, HoldNextIdD1 " + processNextId + " " + HoldNextIdD1);

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
			fin.setPartyId(assSheet.getPartyId());
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
						: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

			ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(), o.getAssesmentId());

		});
		}
//		Normal End
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		int updateExpInventoryContainerInvoiceProcess = exportInvoiceRepo.updateExpInventoryContainerInvoiceProcess(assSheet.getAssesmentId(), "Y", cid, bid, srv.getInvoiceNo(), srv.getInvoiceDate());
		int updateMovementRequestContainerInvoiceProcess = exportInvoiceRepo.updateMovementRequestContainerInvoiceProcess(assSheet.getAssesmentId(), cid, bid, srv.getInvoiceNo(), srv.getInvoiceDate(), srv.getCreditType(), assSheet.getInvoiceCategory(), totalBillAmt, totalInvAmt);
		int updateExportStuffTallyContainerInvoiceProcess = exportInvoiceRepo.updateExportStuffTallyContainerInvoiceProcess(assSheet.getAssesmentId(), cid, bid, srv.getInvoiceNo(), srv.getInvoiceDate(), srv.getCreditType(), assSheet.getInvoiceCategory(), totalBillAmt, totalInvAmt);

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		System.out.println("updateExpInventoryContainerInvoiceProcess -- : -- " + updateExpInventoryContainerInvoiceProcess + " updateMovementRequestContainerInvoiceProcess -- : -- " + updateMovementRequestContainerInvoiceProcess + "  updateExportStuffTallyContainerInvoiceProcess -- : -- " + updateExportStuffTallyContainerInvoiceProcess);

		
		AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
														assessment.getProfitcentreId(), assessment.getAssesmentId());

		System.out.println(
				"Before ------ selectedExportAssesmentSheetContainerData : \n" + selectedExportAssesmentSheet);
		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
												.getSelectedExportAssesmentSheetContainerData(cid, bid, assessment.getProfitcentreId(),
												assessment.getAssesmentId());

		System.out.println("Before ------ cargoStorageServiceId");

		String cargoStorageServiceId = exportInvoiceRepo.getCargoStorageServiceId(cid, bid);
		List<Object[]> cargoStorageOfAssessmentSheetDetails = exportInvoiceRepo
				.getCargoStorageOfAssessmentSheetDetails(cid, bid, assessment.getSbTransId(),
						assessment.getAssesmentId(), cargoStorageServiceId);

		System.out.println(
				"Before ------ cargoStorageOfAssessmentSheetDetails \n" + cargoStorageOfAssessmentSheetDetails);

		
		if (selectedExportAssesmentSheetContainerData == null || selectedExportAssesmentSheetContainerData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = exportInvoiceRepo.getInvoiceGenerated(cid, bid, srv.getInvoiceNo());

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = exportInvoiceRepo.getFinTransGenerated(cid, bid, srv.getInvoiceNo());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())) 
				.findFirst()
				.orElse(null);

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);
		
		
		List<Object[]> filteredData = cargoStorageOfAssessmentSheetDetails.stream()  // Convert List<Object[]> to Stream<Object[]>
			    .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))  // Keep rows that have at least one non-null value
			    .collect(Collectors.toList());  // Convert back to List<Object[]>

		
		finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);
		finalResult.put("sbData", filteredData);

		
		System.out.println("singleTransData \n" + singleTransData);
		
		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
			
			
			Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, srv.getPartyId());

			if (p != null) {	
				
				
				if (assessment.getCreditType() == 'P') {
					BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, srv.getPartyId(), "AD");

					finalResult.put("advanceData", advanceData);
					selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
								
				
				selectedExportAssesmentSheet.setCreditAllowed(credit1);
				selectedExportAssesmentSheet.setUsedCredit(credit2);		
				selectedExportAssesmentSheet.setPendingCredit(allowedValue);
				
				
			}	
			
			
			
			
			
			
			
			
			
			
			
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = assSheet.getCha();

			} else if ("EXP".equals(singleTransData.getTdsType())) {
				pId = assSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOthPartyId();
			}

			Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);

			if (p != null) {	
				
				
				if (assessment.getCreditType() == 'P') {
					BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, pId, "AD");

					finalResult.put("advanceData", advanceData);
					selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
								
				
				selectedExportAssesmentSheet.setCreditAllowed(credit1);
				selectedExportAssesmentSheet.setUsedCredit(credit2);		
				selectedExportAssesmentSheet.setPendingCredit(allowedValue);
				
				
				finalResult.put("tanNo", p.getTanNoId());
			}			
		}
		return ResponseEntity.ok(finalResult);
	}
	
	
	
	
	
	
	
	
	
//	Carting Process
	
	


	@Transactional
	public ResponseEntity<?> processExportAssesmentCartingAndBackToTown(String cid, String bid, String user,
			Map<String, Object> data, String invoiceType, String creditStatus) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<ExportContainerAssessmentData> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<ExportContainerAssessmentData>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
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
			processNextId = "P01123";
		} else {
			processNextId = "P01113";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		AssessmentSheet assSheet = oldAssessmentData.get(0);
		Cfinvsrv srv = new Cfinvsrv();
		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		
		
		
		System.out.println(" creditStatus "+ creditStatus);
		if("Y".equalsIgnoreCase(creditStatus))
		{		
			System.out.println(" creditStatus 1"+ creditStatus);
			oldAssessmentData.stream().forEach(o -> {

				Cfinvsrvanxback back = new Cfinvsrvanxback();
				back.setCompanyId(cid);
				back.setBranchId(bid);
				back.setStatus("A");
				back.setProcessTransId(o.getAssesmentId());
				back.setErpDocRefNo(o.getSbTransId());
				back.setDocRefNo(o.getSbNo());
				back.setIgmLineNo(o.getIgmLineNo());
				back.setServiceId(oldAnxData.get(0).getServiceId());
				back.setTaxId(oldAnxData.get(0).getTaxId());
				back.setSrlNo(srNo.get());
				back.setProfitCentreId(o.getProfitcentreId());
				back.setInvoiceNo(HoldNextIdD1);
				back.setInvoiceDate(new Date());
				back.setInvoiceType("EXP");
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
			srv.setInvoiceType("EXP");
			srv.setTransType("CONT");
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
						: "EXP".equals(tdsDeductee) ? assSheet.getImporterId()
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
							: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

				int updateAssessmentId = ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(),
						o.getAssesmentId());


			});

			
			
			
			
			
			
			
		}
//		Credit END 
		
//		PDA START
		else if("P".equalsIgnoreCase(creditStatus))
		{
			System.out.println(" creditStatus 2"+ creditStatus);
			
			
			
			
			
			oldAssessmentData.stream().forEach(o -> {

				Cfinvsrvanxback back = new Cfinvsrvanxback();
				back.setCompanyId(cid);
				back.setBranchId(bid);
				back.setStatus("A");
				back.setProcessTransId(o.getAssesmentId());
				back.setErpDocRefNo(o.getSbTransId());
				back.setDocRefNo(o.getSbNo());
				back.setIgmLineNo(o.getIgmLineNo());
				back.setServiceId(oldAnxData.get(0).getServiceId());
				back.setTaxId(oldAnxData.get(0).getTaxId());
				back.setSrlNo(srNo.get());
				back.setProfitCentreId(o.getProfitcentreId());
				back.setInvoiceNo(HoldNextIdD1);
				back.setInvoiceDate(new Date());
				back.setInvoiceType("EXP");
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
			srv.setInvoiceType("EXP");
			srv.setTransType("CONT");
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
							: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

				int updateAssessmentId = ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(),
						o.getAssesmentId());

				System.out.println("o.getInvType() " + o.getInvType());


			});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		else {
		oldAssessmentData.stream().forEach(o -> {

			Cfinvsrvanxback back = new Cfinvsrvanxback();
			back.setCompanyId(cid);
			back.setBranchId(bid);
			back.setStatus("A");
			back.setProcessTransId(o.getAssesmentId());
			back.setErpDocRefNo(o.getSbTransId());
			back.setDocRefNo(o.getSbNo());
			back.setIgmLineNo("");
			back.setServiceId(oldAnxData.get(0).getServiceId());
			back.setTaxId(oldAnxData.get(0).getTaxId());
			back.setSrlNo(srNo.get());
			back.setProfitCentreId(o.getProfitcentreId());
			back.setInvoiceNo(HoldNextIdD1);
			back.setInvoiceDate(new Date());
			back.setInvoiceType("EXP");
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
			back.setAddServices(oldAnxData.get(0).getAddServices());
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

		List<Cfinvsrvanx> clonedList = oldAnxData.stream().map(t -> {
			try {
				return t.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return t;
		}) // Calls clone() method
				.collect(Collectors.toList());

		Map<String, Cfinvsrvanx> groupedData = clonedList.stream().collect(Collectors.toMap(Cfinvsrvanx::getServiceId, // Key:
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
		srv.setInvoiceType("EXP");
		srv.setTransType("CRTG");
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
		srv.setContainerNo(assSheet.getAssesmentId());		
		
		
		invsrvRepo.save(srv);

		System.out.println(" processNextId, HoldNextIdD1 " + processNextId + " " + HoldNextIdD1);

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
			fin.setPartyId(assSheet.getPartyId());
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
						: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

			ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(), o.getAssesmentId());

		});

	}
	
	
	
	
	
	
	
	
	
	
	
		
		
		int updateExpSbCargoEntryCartingInvoiceProcess = exportInvoiceRepo.updateExpSbCargoEntryCartingInvoiceProcess(cid, bid, srv.getInvoiceNo(), assessment.getSbTransId());
		

		System.out.println("updateExpSbCargoEntryCartingInvoiceProcess -- : -- " + updateExpSbCargoEntryCartingInvoiceProcess);

		AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
														assessment.getProfitcentreId(), assessment.getAssesmentId());

		System.out
				.println("Before ------ selectedExportAssesmentSheetContainerData : \n" + selectedExportAssesmentSheet);
		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
				.getSelectedExportAssesmentSheetContainerDataCarting(cid, bid, assessment.getProfitcentreId(),
						assessment.getAssesmentId());

	
		if (selectedExportAssesmentSheetContainerData == null || selectedExportAssesmentSheetContainerData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = exportInvoiceRepo.getInvoiceGenerated(cid, bid, srv.getInvoiceNo());

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = exportInvoiceRepo.getFinTransGenerated(cid, bid, srv.getInvoiceNo());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())).findFirst()
				.orElse(null);

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		
		finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);

		
		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
			
			
			
			Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, srv.getPartyId());

			if (p != null) {	
				
				
				if (assessment.getCreditType() == 'P') {
					BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, srv.getPartyId(), "AD");

					finalResult.put("advanceData", advanceData);
					selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
								
				
				selectedExportAssesmentSheet.setCreditAllowed(credit1);
				selectedExportAssesmentSheet.setUsedCredit(credit2);		
				selectedExportAssesmentSheet.setPendingCredit(allowedValue);
				
				
			}	
			
			
			
			
			
			
			
			
			
			
			
			
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = assSheet.getCha();

			} else if ("EXP".equals(singleTransData.getTdsType())) {
				pId = assSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOthPartyId();
			}

			Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);

			if (p != null) {
				finalResult.put("tanNo", p.getTanNoId());
				
				
				
				

				if (assessment.getCreditType() == 'P') {
					BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, srv.getPartyId(), "AD");

					finalResult.put("advanceData", advanceData);
					selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
								
				
				selectedExportAssesmentSheet.setCreditAllowed(credit1);
				selectedExportAssesmentSheet.setUsedCredit(credit2);		
				selectedExportAssesmentSheet.setPendingCredit(allowedValue);		
				
				
				
			}

		}
		return ResponseEntity.ok(finalResult);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	@Transactional
	public ResponseEntity<?> processExportAssesmentBackToTown(String cid, String bid, String user,
			Map<String, Object> data, String invoiceType, String creditStatus) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
		}

		List<ExportContainerAssessmentData> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<ExportContainerAssessmentData>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
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
			processNextId = "P01123";
		} else {
			processNextId = "P01113";
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, processNextId, "2024");

		String pre = holdId1.substring(0, 5);

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(5));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format(pre + "%05d", nextNumericNextID1);
		
		
		
		
		
		AssessmentSheet assSheet = oldAssessmentData.get(0);
		Cfinvsrv srv = new Cfinvsrv();
		BigDecimal totalLocalAmt = BigDecimal.ZERO;
		BigDecimal totalBillAmt = BigDecimal.ZERO;
		BigDecimal totalInvAmt = BigDecimal.ZERO;
		BigDecimal totalForAmt = BigDecimal.ZERO;

		
		
		
		System.out.println(" creditStatus "+ creditStatus);
		if("Y".equalsIgnoreCase(creditStatus))
		{		
			System.out.println(" creditStatus 1"+ creditStatus);
			oldAssessmentData.stream().forEach(o -> {

				Cfinvsrvanxback back = new Cfinvsrvanxback();
				back.setCompanyId(cid);
				back.setBranchId(bid);
				back.setStatus("A");
				back.setProcessTransId(o.getAssesmentId());
				back.setErpDocRefNo(o.getSbTransId());
				back.setDocRefNo(o.getSbNo());
				back.setIgmLineNo(o.getIgmLineNo());
				back.setServiceId(oldAnxData.get(0).getServiceId());
				back.setTaxId(oldAnxData.get(0).getTaxId());
				back.setSrlNo(srNo.get());
				back.setProfitCentreId(o.getProfitcentreId());
				back.setInvoiceNo(HoldNextIdD1);
				back.setInvoiceDate(new Date());
				back.setInvoiceType("EXP");
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
			srv.setInvoiceType("EXP");
			srv.setTransType("CONT");
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
						: "EXP".equals(tdsDeductee) ? assSheet.getImporterId()
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
							: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

				int updateAssessmentId = ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(),
						o.getAssesmentId());


			});

			
			
			
			
			
			
			
		}
//		Credit END 
		
//		PDA START
		else if("P".equalsIgnoreCase(creditStatus))
		{
			System.out.println(" creditStatus 2"+ creditStatus);
			
			
			
			
			
			oldAssessmentData.stream().forEach(o -> {

				Cfinvsrvanxback back = new Cfinvsrvanxback();
				back.setCompanyId(cid);
				back.setBranchId(bid);
				back.setStatus("A");
				back.setProcessTransId(o.getAssesmentId());
				back.setErpDocRefNo(o.getSbTransId());
				back.setDocRefNo(o.getSbNo());
				back.setIgmLineNo(o.getIgmLineNo());
				back.setServiceId(oldAnxData.get(0).getServiceId());
				back.setTaxId(oldAnxData.get(0).getTaxId());
				back.setSrlNo(srNo.get());
				back.setProfitCentreId(o.getProfitcentreId());
				back.setInvoiceNo(HoldNextIdD1);
				back.setInvoiceDate(new Date());
				back.setInvoiceType("EXP");
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
			srv.setInvoiceType("EXP");
			srv.setTransType("CONT");
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
							: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

				int updateAssessmentId = ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(),
						o.getAssesmentId());

				System.out.println("o.getInvType() " + o.getInvType());


			});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		else {
		

		oldAssessmentData.stream().forEach(o -> {

			Cfinvsrvanxback back = new Cfinvsrvanxback();
			back.setCompanyId(cid);
			back.setBranchId(bid);
			back.setLineNo(o.getAssesmentLineNo());
			back.setStatus("A");
			back.setProcessTransId(o.getAssesmentId());
			back.setErpDocRefNo(o.getSbTransId());
			back.setDocRefNo(o.getSbNo());
			back.setIgmLineNo("");
			back.setServiceId(oldAnxData.get(0).getServiceId());
			back.setTaxId(oldAnxData.get(0).getTaxId());
			back.setSrlNo(srNo.get());
			back.setProfitCentreId(o.getProfitcentreId());
			back.setInvoiceNo(HoldNextIdD1);
			back.setInvoiceDate(new Date());
			back.setInvoiceType("EXP");
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
			back.setAddServices(oldAnxData.get(0).getAddServices());
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

		List<Cfinvsrvanx> clonedList = oldAnxData.stream().map(t -> {
			try {
				return t.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return t;
		}) // Calls clone() method
				.collect(Collectors.toList());

		Map<String, Cfinvsrvanx> groupedData = clonedList.stream().collect(Collectors.toMap(Cfinvsrvanx::getServiceId, // Key:
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
		srv.setInvoiceType("EXP");
		srv.setTransType("BTT");
		srv.setErpDocRefNo(assSheet.getSbTransId());
		srv.setDocRefNo(assSheet.getSbNo());
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

		System.out.println(" processNextId, HoldNextIdD1 " + processNextId + " " + HoldNextIdD1);

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
			fin.setPartyId(assSheet.getPartyId());
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
						: "EXP".equals(tdsDeductee) ? o.getImporterId()
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

			ssrRepo.updateAssessmentId(cid, bid, o.getSsrTransId(), o.getContainerNo(), o.getAssesmentId());

		});

		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		int updateExpBackToTownInvoiceProcess = exportInvoiceRepo.updateExpBackToTownInvoiceProcess( assessment.getAssesmentId(),cid, bid, srv.getInvoiceNo(), srv.getInvoiceDate(), srv.getCreditType(), srv.getBillAmt(), srv.getInvoiceAmt());
		

		System.out.println("updateExpBackToTownInvoiceProcess -- : -- " + updateExpBackToTownInvoiceProcess);

		AssessmentSheet selectedExportAssesmentSheet = getSelectedAssesmentData(cid, bid,
														assessment.getProfitcentreId(), assessment.getAssesmentId());

		System.out
				.println("Before ------ selectedExportAssesmentSheetContainerData : \n" + selectedExportAssesmentSheet);
		List<ExportContainerAssessmentData> selectedExportAssesmentSheetContainerData = exportInvoiceRepo
				.getSelectedExportAssesmentSheetBackToTown(cid, bid, assessment.getProfitcentreId(),
						assessment.getAssesmentId());

	
		if (selectedExportAssesmentSheetContainerData == null || selectedExportAssesmentSheetContainerData.isEmpty()) {
			return new ResponseEntity<>("Assessment data not found!!", HttpStatus.CONFLICT);
		}

		Cfinvsrv existingSrv = exportInvoiceRepo.getInvoiceGenerated(cid, bid, srv.getInvoiceNo());

		if (existingSrv == null) {
			return new ResponseEntity<>("Invsrv data not found!!", HttpStatus.CONFLICT);
		}

		List<FinTrans> existingSrvFin = exportInvoiceRepo.getFinTransGenerated(cid, bid, srv.getInvoiceNo());

		if (existingSrvFin.isEmpty()) {
			return new ResponseEntity<>("Payment data not found!!", HttpStatus.CONFLICT);
		}

		FinTrans singleTransData = existingSrvFin.stream().filter(c -> "TDS".equals(c.getPaymentMode())).findFirst()
				.orElse(null);

		Map<String, Object> finalResult = new HashMap<>();
		finalResult.put("existingSrv", existingSrv);
		finalResult.put("existingSrvFin", existingSrvFin);

		
		finalResult.put("assesmentSheetRec", selectedExportAssesmentSheet);
		finalResult.put("containerDataRec", selectedExportAssesmentSheetContainerData);

		
		if (singleTransData == null) {
			finalResult.put("tdsDeductee", "");
			finalResult.put("tdsperc", "");
			finalResult.put("tanNo", "");
			
			Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, srv.getPartyId());

			if (p != null) {
			
			if (assessment.getCreditType() == 'P') {
				BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, srv.getPartyId(), "AD");

				finalResult.put("advanceData", advanceData);
				selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
			}

			BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
			BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

			BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
							
			
			selectedExportAssesmentSheet.setCreditAllowed(credit1);
			selectedExportAssesmentSheet.setUsedCredit(credit2);		
			selectedExportAssesmentSheet.setPendingCredit(allowedValue);
			
			}
			
			
			
			
			
		} else {
			finalResult.put("tdsDeductee", singleTransData.getTdsType());
			finalResult.put("tdsperc", singleTransData.getTdsPercentage());

			String pId = "";

			if ("CHA".equals(singleTransData.getTdsType())) {

				pId = assSheet.getCha();

			} else if ("EXP".equals(singleTransData.getTdsType())) {
				pId = assSheet.getImporterId();
			} else if ("FWR".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOnAccountOf();
			} else if ("OTH".equals(singleTransData.getTdsType())) {
				pId = assSheet.getOthPartyId();
			}

			Party p = exportInvoiceRepo.getPartyForTanNo(cid, bid, pId);

			if (p != null) {
				finalResult.put("tanNo", p.getTanNoId());
				
				if (assessment.getCreditType() == 'P') {
					BigDecimal advanceData = finTransrepo.advanceReceiptAmountNew(cid, bid, srv.getPartyId(), "AD");

					finalResult.put("advanceData", advanceData);
					selectedExportAssesmentSheet.setAdvanceAmount(advanceData);
				}

				BigDecimal credit1 = p.getCrAmtLmt() == null ? BigDecimal.ZERO : p.getCrAmtLmt();
				BigDecimal credit2 = p.getCrAmtLmtUse() == null ? BigDecimal.ZERO : p.getCrAmtLmtUse();

				BigDecimal allowedValue = credit1.subtract(credit2).setScale(3, RoundingMode.HALF_UP);
								
				
				selectedExportAssesmentSheet.setCreditAllowed(credit1);
				selectedExportAssesmentSheet.setUsedCredit(credit2);		
				selectedExportAssesmentSheet.setPendingCredit(allowedValue);
				
				
				
			}

		}
		return ResponseEntity.ok(finalResult);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}


