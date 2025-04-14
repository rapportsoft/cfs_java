package com.cwms.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

import com.cwms.entities.*;
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
import com.cwms.repository.GeneralJobEntryRepository;
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
public class GeneralAssessmentService {

	@Autowired
	private GeneralJobEntryRepository generaljobentryrepo;

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

	public ResponseEntity<?> searchGeneralJobBeforeSaveAssessmentData(String cid, String bid, String val) {
		List<Object[]> data = generaljobentryrepo.getDataBeforeAssessment(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	public ResponseEntity<?> getGeneralJobBeforeSaveAssessmentData(String cid, String bid, String trans, String boe) {
		List<Object[]> data = generaljobentryrepo.getSelectedDataBeforeAssessment(cid, bid, trans, boe);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@Transactional
	public ResponseEntity<?> generalAssessmentService(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {

		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Job data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Job data not found", HttpStatus.CONFLICT);
		}

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {

			int sr = 1;

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05137", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("GREA%06d", nextNumericNextID1);

			for (AssessmentContainerDTO con : containerData) {

				List<AssessmentContainerDTO> finalConData = new ArrayList<>();

				if (con.getLastInvoiceUptoDate() == null) {

					List<String> serviceMappingData = new ArrayList<>();

					if ("SINGLE".equals(assessment.getInvoiceCategory())) {
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice1(cid, bid,
								con.getProfitcentreId(), "GENREC");
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice2(cid, bid,
								con.getProfitcentreId(), "GENREC", group);
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
							finalServices, con.getUpTariffNo(), assessment.getIgmTransId(), assessment.getIgmNo());

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
								parsedDate, notInPricing, "CFS1000001", assessment.getIgmTransId(),
								assessment.getIgmNo());

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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (rate1.getTotalRate().multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (rate1.getTotalRate().subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else {
											tempAss.setRates(rate1.getTotalRate());
										}

										tempAss.setInvoiceDate(invDate);
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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
															destuffDateTime) + 1;

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

													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

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
															destuffDateTime) + 1;

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
													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

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
															(f[23] != null && !String.valueOf(f[23]).isEmpty())
																	? new BigDecimal(String.valueOf(f[23]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[24] != null && !String.valueOf(f[24]).isEmpty())
																	? new BigDecimal(String.valueOf(f[24]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(BigDecimal.ZERO);
													tempAss.setmAmount(BigDecimal.ZERO);
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

													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else {
														tempAss.setRates(totalRate);
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
															(f[23] != null && !String.valueOf(f[23]).isEmpty())
																	? new BigDecimal(String.valueOf(f[23]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[24] != null && !String.valueOf(f[24]).isEmpty())
																	? new BigDecimal(String.valueOf(f[24]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(BigDecimal.ZERO);
													tempAss.setmAmount(BigDecimal.ZERO);
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

													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else {
														tempAss.setRates(totalRate);
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
														(f[23] != null && !String.valueOf(f[23]).isEmpty())
																? new BigDecimal(String.valueOf(f[23]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
														? new BigDecimal(String.valueOf(f[24]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(BigDecimal.ZERO);
												tempAss.setmAmount(BigDecimal.ZERO);
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

												String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
												String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(amt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else {
													tempAss.setRates(totalRate);
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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else {
												tempAss.setRates(totalRate);
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
									tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
											? new BigDecimal(String.valueOf(f[23]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
											? new BigDecimal(String.valueOf(f[24]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage(BigDecimal.ZERO);
									tempAss.setmAmount(BigDecimal.ZERO);
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
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime)+1;

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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

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
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime)+1;

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
											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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
											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else {
												tempAss.setRates(totalRate);
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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else {
												tempAss.setRates(totalRate);
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
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else {
											tempAss.setRates(totalRate);
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
									tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
											? new BigDecimal(String.valueOf(f[23]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
											? new BigDecimal(String.valueOf(f[24]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage(BigDecimal.ZERO);
									tempAss.setmAmount(BigDecimal.ZERO);
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

									String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
									String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(amt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else {
										tempAss.setRates(totalRate);
									}

								}

								finalConData.add(tempAss);

							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});

					AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();

					GenerelJobEntry job = generaljobentryrepo.getGeneralData(cid, bid, assessment.getIgmTransId(),
							assessment.getIgmNo());

					if (job == null) {
						return new ResponseEntity<>("Job entry data not found", HttpStatus.CONFLICT);
					}

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
					newAss.setTransType("General Cargo");
					newAss.setProfitcentreId(con.getProfitcentreId());
					newAss.setSbNo(job.getBoeNo());
					newAss.setSbDate(job.getBoeDate());
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
					newAss.setInvType("RECEIVING");
					newAss.setSa("");
					newAss.setSl("");
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrTransId(con.getSsrTransId());
//					newAss.setLastAssesmentDate(assessment.getLastAssesmentDate());
//					newAss.setLastAssesmentId(assessment.getLastAssesmentId());
//					newAss.setLastInvoiceDate(assessment.getLastInvoiceDate());
//					newAss.setLastInvoiceNo(assessment.getLastInvoiceNo());
//					newAss.setLastInvoiceUptoDate(con.getLastInvoiceUptoDate());
				//	newAss.setInsuranceValue(assessment.getInsuranceValue());
					newAss.setNoOf20ft(con.getContainerSize());
					newAss.setNoOf40ft(con.getContainerType());
					newAss.setNocWeeks(new BigDecimal(con.getGateOutType()));
					newAss.setNocFromDate(con.getGateInDate());
					newAss.setNocValidityDate(con.getGateoutDate());
					assessmentsheetrepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P05137", HoldNextIdD1, "2024");

//					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

					finalConData.stream().forEach(c -> {
						if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
							Cfinvsrvanx anx = new Cfinvsrvanx();
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
							anx.setInvoiceType("GEN");
							anx.setInvoiceSubType("REC");
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
							anx.setProcessId("P01112");
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

							cfinvsrvanxrepo.save(anx);

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
								con.getProfitcentreId(), "GENRECPER");
					} else {

						String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
						serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice2(cid, bid,
								con.getProfitcentreId(), "GENRECPER", group);
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
							finalServices, con.getUpTariffNo(), assessment.getIgmTransId(), assessment.getIgmNo());

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
								parsedDate, notInPricing, "CFS1000001", assessment.getIgmTransId(),
								assessment.getIgmNo());

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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (rate1.getRate().multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (rate1.getRate().subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else {
											tempAss.setRates(rate1.getRate());
										}

										tempAss.setInvoiceDate(invDate);
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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
															destuffDateTime)+1;

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

													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

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
															destuffDateTime)+1;

													if (daysBetween == 0 && ChronoUnit.HOURS.between(gateInDateTime,
															destuffDateTime) > 0) {
														daysBetween = 1;
													}

													final long daysBetween1 = daysBetween;
													System.out.println("daysBetween1 "+daysBetween1);
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
													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

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
															(f[23] != null && !String.valueOf(f[23]).isEmpty())
																	? new BigDecimal(String.valueOf(f[23]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[24] != null && !String.valueOf(f[24]).isEmpty())
																	? new BigDecimal(String.valueOf(f[24]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(BigDecimal.ZERO);
													tempAss.setmAmount(BigDecimal.ZERO);
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

													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else {
														tempAss.setRates(totalRate);
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
															(f[23] != null && !String.valueOf(f[23]).isEmpty())
																	? new BigDecimal(String.valueOf(f[23]))
																	: BigDecimal.ZERO);
													tempAss.setDiscValue(
															(f[24] != null && !String.valueOf(f[24]).isEmpty())
																	? new BigDecimal(String.valueOf(f[24]))
																	: BigDecimal.ZERO);
													tempAss.setmPercentage(BigDecimal.ZERO);
													tempAss.setmAmount(BigDecimal.ZERO);
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
													String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
													String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

													if (cPerc != null && !cPerc.isEmpty()) {
														BigDecimal disPerc = new BigDecimal(cPerc);
														BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

														BigDecimal amt = (totalRate.multiply(finalPerc))
																.divide(new BigDecimal(100));

														amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(amt);

													} else if (cAmt != null && !cAmt.isEmpty()) {

														BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
																.setScale(3, BigDecimal.ROUND_HALF_UP);

														tempAss.setRates(disAmt);

													} else {
														tempAss.setRates(totalRate);
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
														(f[23] != null && !String.valueOf(f[23]).isEmpty())
																? new BigDecimal(String.valueOf(f[23]))
																: BigDecimal.ZERO);
												tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
														? new BigDecimal(String.valueOf(f[24]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(BigDecimal.ZERO);
												tempAss.setmAmount(BigDecimal.ZERO);
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

												String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
												String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(amt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												} else {
													tempAss.setRates(totalRate);
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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else {
												tempAss.setRates(totalRate);
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
									tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
											? new BigDecimal(String.valueOf(f[23]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
											? new BigDecimal(String.valueOf(f[24]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage(BigDecimal.ZERO);
									tempAss.setmAmount(BigDecimal.ZERO);
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
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime)+1;

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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

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
											long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime)+1;

											if (daysBetween == 0) {
												daysBetween = 1;
											}

											final long daysBetween1 = daysBetween;
											
											System.out.println("daysBetween1 "+daysBetween1);
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
											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else {
												tempAss.setRates(totalRate);
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
													(f[23] != null && !String.valueOf(f[23]).isEmpty())
															? new BigDecimal(String.valueOf(f[23]))
															: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											} else {
												tempAss.setRates(totalRate);
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
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
													BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										} else {
											tempAss.setRates(totalRate);
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
									tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
											? new BigDecimal(String.valueOf(f[23]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
											? new BigDecimal(String.valueOf(f[24]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage(BigDecimal.ZERO);
									tempAss.setmAmount(BigDecimal.ZERO);
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

									String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
									String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc)).divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(amt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (totalRate.subtract(new BigDecimal(cAmt))).setScale(3,
												BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									} else {
										tempAss.setRates(totalRate);
									}

								}

								finalConData.add(tempAss);

							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					});

					AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();

					GenerelJobEntry job = generaljobentryrepo.getGeneralData(cid, bid, assessment.getIgmTransId(),
							assessment.getIgmNo());

					if (job == null) {
						return new ResponseEntity<>("Job entry data not found", HttpStatus.CONFLICT);
					}

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
					newAss.setTransType("General Cargo");
					newAss.setProfitcentreId(con.getProfitcentreId());
					newAss.setSbNo(job.getBoeNo());
					newAss.setSbDate(job.getBoeDate());
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
					newAss.setInvType("RECEIVING");
					newAss.setSa("");
					newAss.setSl("");
					newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
					newAss.setSsrTransId(con.getSsrTransId());
					newAss.setLastAssesmentDate(assessment.getLastAssesmentDate());
					newAss.setLastAssesmentId(assessment.getLastAssesmentId());
					newAss.setLastInvoiceDate(assessment.getLastInvoiceDate());
					newAss.setLastInvoiceNo(assessment.getLastInvoiceNo());
					newAss.setLastInvoiceUptoDate(con.getLastInvoiceUptoDate());
				//	newAss.setInsuranceValue(assessment.getInsuranceValue());
					newAss.setNoOf20ft(con.getContainerSize());
					newAss.setNoOf40ft(con.getContainerType());
					newAss.setNocWeeks(new BigDecimal(con.getGateOutType()));
					newAss.setNocFromDate(con.getGateInDate());
					newAss.setNocValidityDate(con.getGateoutDate());
					assessmentsheetrepo.save(newAss);
					processnextidrepo.updateAuditTrail(cid, bid, "P05137", HoldNextIdD1, "2024");

//					AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
					AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
					AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

					finalConData.stream().forEach(c -> {
						if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
							Cfinvsrvanx anx = new Cfinvsrvanx();
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
							anx.setInvoiceType("GEN");
							anx.setInvoiceSubType("REC");
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
							anx.setProcessId("P01112");
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

							cfinvsrvanxrepo.save(anx);

							srNo1.set(srNo1.get().add(new BigDecimal(1)));
						}
					});
					totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

					assessment.setAssesmentId(HoldNextIdD1);

					sr++;

				}

			}

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

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForNoc(cid, bid, assessment.getAssesmentId());

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
	public ResponseEntity<?> saveGeneralRecInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

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
			processNextId = "P05139";
		} else {
			processNextId = "P05138";
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
			back.setInvoiceType("BON");
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
		srv.setInvoiceType("GEN");
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
		srv.setInvoiceSubType("REC");

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
			fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
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

			if ("TDS".equals(p.getPayMode())) {
				fin.setTdsType(tdsDeductee);
				fin.setTdsPercentage(new BigDecimal(tdsPerc));
				fin.setTdsBillAmt(p.getAmount());
			}

			finTransrepo.save(fin);

			srNo1.set(srNo1.get().add(new BigDecimal(1)));
		});

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

			int updateAssessmentId = ssrRepo.updateAssessmentId1(cid, bid, o.getSsrTransId(), o.getAssesmentId());

		});

//		Cfexbonddts existingDtls = cfexbonddtlsrepo.getDataByNocTransIdAndAssessment(cid, bid, assSheet.getIgmTransId(),
//				assSheet.getAssesmentId());
//
//		if (existingDtls != null) {
//			existingDtls.setStorageValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setInsuranceValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setApprovedBy(user);
//			existingDtls.setApprovedDate(new Date());
//
//			cfexbonddtlsrepo.save(existingDtls);
//
//			if (existingDtls.getInBondingId() != null && !existingDtls.getInBondingId().isEmpty()) {
//				int updateInbond = cfinbondhdrrepo.updateInvoiceData(cid, bid, assSheet.getIgmTransId(),
//						existingDtls.getInBondingId(), assSheet.getIgmNo(), HoldNextIdD1,
//						assSheet.getInvoiceUptoDate());
//			}
//
//		}

		int updateJob = generaljobentryrepo.updateJobEntry(cid, bid, assSheet.getIgmTransId(), assSheet.getIgmNo(),
				assSheet.getAssesmentId(), HoldNextIdD1, assSheet.getInvoiceUptoDate());


		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForNoc(cid, bid, assessment.getAssesmentId());

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
				finalResult.put("tanNo", p.getTanNoId());
			}

		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}

	@Transactional
	public ResponseEntity<?> saveGeneralRecCreditInvoiceReceipt(String cid, String bid, String user,
			Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

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
			processNextId = "P05139";
		} else {
			processNextId = "P05138";
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
			back.setInvoiceType("GEN");
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
		srv.setInvoiceType("GEN");
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
		srv.setInvoiceSubType("REC");

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
				fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
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
				fin.setCreditType("Y");
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
				fin.setCreditFlag(String.valueOf(assSheet.getCreditType()));
				fin.setPartyId(assSheet.getPayablePartyId());
				fin.setAccSrNo(assSheet.getPartySrNo().intValue());
				fin.setAcCode(consolidatedData.get(0).getAcCode());
				fin.setChequeNo(p.getChequeNo());
				fin.setChequeDate(p.getChequeDate());
				fin.setBankName(p.getBankDetails());
				fin.setCreditType("Y");
				fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
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

			int updateAssessmentId = ssrRepo.updateAssessmentId1(cid, bid, o.getSsrTransId(), o.getAssesmentId());

		});

//		Cfexbonddts existingDtls = cfexbonddtlsrepo.getDataByNocTransIdAndAssessment(cid, bid, assSheet.getIgmTransId(),
//				assSheet.getAssesmentId());
//
//		if (existingDtls != null) {
//			existingDtls.setStorageValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setInsuranceValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setApprovedBy(user);
//			existingDtls.setApprovedDate(new Date());
//
//			cfexbonddtlsrepo.save(existingDtls);
//
//			if (existingDtls.getInBondingId() != null && !existingDtls.getInBondingId().isEmpty()) {
//				int updateInbond = cfinbondhdrrepo.updateInvoiceData(cid, bid, assSheet.getIgmTransId(),
//						existingDtls.getInBondingId(), assSheet.getIgmNo(), HoldNextIdD1,
//						assSheet.getInvoiceUptoDate());
//			}
//
//		}

		int updateJob = generaljobentryrepo.updateJobEntry(cid, bid, assSheet.getIgmTransId(), assSheet.getIgmNo(),
				assSheet.getAssesmentId(), HoldNextIdD1, assSheet.getInvoiceUptoDate());

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForNoc(cid, bid, assessment.getAssesmentId());

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
	public ResponseEntity<?> saveGeneralRecPdaInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

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
			processNextId = "P05139";
		} else {
			processNextId = "P05138";
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
			back.setInvoiceType("GEN");
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
		srv.setInvoiceType("GEN");
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
		srv.setInvoiceSubType("REC");

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
			fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
			fin.setCreditType("P");
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

			int updateAssessmentId = ssrRepo.updateAssessmentId1(cid, bid, o.getSsrTransId(), o.getAssesmentId());

		});

//		Cfexbonddts existingDtls = cfexbonddtlsrepo.getDataByNocTransIdAndAssessment(cid, bid, assSheet.getIgmTransId(),
//				assSheet.getAssesmentId());
//
//		if (existingDtls != null) {
//			existingDtls.setStorageValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setInsuranceValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setApprovedBy(user);
//			existingDtls.setApprovedDate(new Date());
//
//			cfexbonddtlsrepo.save(existingDtls);
//
//			if (existingDtls.getInBondingId() != null && !existingDtls.getInBondingId().isEmpty()) {
//				int updateInbond = cfinbondhdrrepo.updateInvoiceData(cid, bid, assSheet.getIgmTransId(),
//						existingDtls.getInBondingId(), assSheet.getIgmNo(), HoldNextIdD1,
//						assSheet.getInvoiceUptoDate());
//			}
//
//		}

		int updateJob = generaljobentryrepo.updateJobEntry(cid, bid, assSheet.getIgmTransId(), assSheet.getIgmNo(),
				assSheet.getAssesmentId(), HoldNextIdD1, assSheet.getInvoiceUptoDate());

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForNoc(cid, bid, assessment.getAssesmentId());

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

	
	public ResponseEntity<?> saveGeneralAddService(String companyId, String branchId, String user,
			Map<String, Object> resultList, String type) {

		try {

			AssessmentSheet assessment = objectMapper.readValue(
					objectMapper.writeValueAsString(resultList.get("assesmentSheet")), AssessmentSheet.class);

			if (assessment == null) {
				return new ResponseEntity<>("Assessment data not found", HttpStatus.CONFLICT);
			}

			Cfinvsrvanx anx = objectMapper.readValue(objectMapper.writeValueAsString(resultList.get("Cfinvsrvanx")),
					Cfinvsrvanx.class);

			if (anx == null) {
				return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
			}

			BigDecimal zero = BigDecimal.ZERO;

			List<AssessmentSheet> existingData = assessmentsheetrepo.getDataByAssessmentId(companyId, branchId,
					assessment.getAssesmentId());

			if (existingData.isEmpty()) {
				return new ResponseEntity<>("Assessment Data not found", HttpStatus.CONFLICT);
			}

			AssessmentSheet data = existingData.get(0);

			Optional<BigDecimal> highestSRLNoOptional = exportInvoiceRepo.getHighestSrlNoByContainerNo1(companyId,
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
			anx.setInvoiceType("GEN");
			anx.setStartDate(data.getNocFromDate());
			anx.setGateOutDate(data.getNocValidityDate());
			anx.setLineNo("1");;

			if ("noc".equals(type)) {
				anx.setInvoiceSubType("REC");
			} else {
				anx.setInvoiceSubType("DEL");
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
			anx.setProcessId("P01112");
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

			cfinvsrvanxrepo.save(anx);

			return ResponseEntity.ok(true);
		} catch (Exception e) {
			System.out.println("Error in Add Service ContainerWise " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Error in Add Service ContainerWise: " + e.getMessage());
		}
	}

	

	public ResponseEntity<?> searchGeneralInvoiceData(String cid, String bid, String val, String type) {

		List<Object[]> data = new ArrayList<>();

		if ("noc".equals(type)) {
			data = assessmentsheetrepo.searchGeneralJobInvoiceData(cid, bid, val);
		} else {
			data = assessmentsheetrepo.searchGeneralDeliveryInvoiceData(cid, bid, val);
		}

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<?> getBeforeAssessmentDeliveryData(String cid, String bid, String val){
		
		List<Object[]> data = generaljobentryrepo.getBeforeAssessmentDeliveryData(cid, bid, val);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);
		
	}
	
	
	public ResponseEntity<?> getGeneralDeliveryBeforeSaveAssessmentData(String cid, String bid, String val) {

		List<Object[]> data = generaljobentryrepo.getGeneralDeliveryBeforeSaveAssessmentData(cid, bid, val);

		if (data == null || data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}
	
	
	@Transactional
	public ResponseEntity<?> saveGeneralDeliveryAssessmentData(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {

		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

		if (assessment == null) {
			return new ResponseEntity<>("Job Delivery data not found", HttpStatus.CONFLICT);
		}

		List<AssessmentContainerDTO> containerData = mapper.readValue(
				mapper.writeValueAsString(data.get("containerData")),
				new TypeReference<List<AssessmentContainerDTO>>() {
				});

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Job Delivery data not found", HttpStatus.CONFLICT);
		}

		AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));

		if (assessment.getAssesmentId() == null || assessment.getAssesmentId().isEmpty()) {

			int sr = 1;

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05140", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("BEXA%06d", nextNumericNextID1);

			for (AssessmentContainerDTO con : containerData) {
				List<AssessmentContainerDTO> finalConData = new ArrayList<>();

				List<String> serviceMappingData = new ArrayList<>();

				if ("SINGLE".equals(assessment.getInvoiceCategory())) {
					serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice1(cid, bid,
							con.getProfitcentreId(), "GENDEL");
				} else {

					String group = "STORAGE".equals(assessment.getInvoiceCategory()) ? "G" : "H";
					serviceMappingData = serviceMappingRepo.getServicesForBondNocInvoice2(cid, bid,
							con.getProfitcentreId(), "GENDEL", group);
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
						finalServices, con.getUpTariffNo(),assessment.getIgmTransId(), assessment.getViaNo());

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
							notInPricing, "CFS1000001",assessment.getIgmTransId(), assessment.getViaNo());

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
									
									String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
									String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (rate1.getTotalRate().multiply(finalPerc))
												.divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(amt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (rate1.getTotalRate()
												.subtract(new BigDecimal(cAmt)))
												.setScale(3, BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									}  else {
										tempAss.setRates(rate1.getTotalRate());
									}

									tempAss.setInvoiceDate(invDate);
									tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
									tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
									tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
									tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
									tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
									tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
									tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
											? new BigDecimal(String.valueOf(f[23]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
											? new BigDecimal(String.valueOf(f[24]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage(BigDecimal.ZERO);
									tempAss.setmAmount(BigDecimal.ZERO);
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
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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
														destuffDateTime) + 1;

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
												
												String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
												String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(amt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate
															.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												}  else {
													tempAss.setRates(totalRate);
												}


											}

											if ("WEEK".equals(unit)) {

												LocalDateTime gateInDateTime = LocalDateTime.ofInstant(
														con.getGateInDate().toInstant(), ZoneId.systemDefault());
												LocalDateTime destuffDateTime = LocalDateTime.ofInstant(
														con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
												long daysBetween = ChronoUnit.DAYS.between(gateInDateTime,
														destuffDateTime) + 1;

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
												String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
												String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(amt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate
															.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												}  else {
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
												tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
														? new BigDecimal(String.valueOf(f[23]))
														: BigDecimal.ZERO);
												tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
														? new BigDecimal(String.valueOf(f[24]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(BigDecimal.ZERO);
												tempAss.setmAmount(BigDecimal.ZERO);
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

												String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
												String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(amt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate
															.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												}  else {
													tempAss.setRates(totalRate);
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
												tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
														? new BigDecimal(String.valueOf(f[23]))
														: BigDecimal.ZERO);
												tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
														? new BigDecimal(String.valueOf(f[24]))
														: BigDecimal.ZERO);
												tempAss.setmPercentage(BigDecimal.ZERO);
												tempAss.setmAmount(BigDecimal.ZERO);
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

												String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
												String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

												if (cPerc != null && !cPerc.isEmpty()) {
													BigDecimal disPerc = new BigDecimal(cPerc);
													BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

													BigDecimal amt = (totalRate.multiply(finalPerc))
															.divide(new BigDecimal(100));

													amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(amt);

												} else if (cAmt != null && !cAmt.isEmpty()) {

													BigDecimal disAmt = (totalRate
															.subtract(new BigDecimal(cAmt)))
															.setScale(3, BigDecimal.ROUND_HALF_UP);

													tempAss.setRates(disAmt);

												}  else {
													tempAss.setRates(totalRate);
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
											tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
													? new BigDecimal(String.valueOf(f[23]))
													: BigDecimal.ZERO);
											tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
													? new BigDecimal(String.valueOf(f[24]))
													: BigDecimal.ZERO);
											tempAss.setmPercentage(BigDecimal.ZERO);
											tempAss.setmAmount(BigDecimal.ZERO);
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

											String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
											String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

											if (cPerc != null && !cPerc.isEmpty()) {
												BigDecimal disPerc = new BigDecimal(cPerc);
												BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

												BigDecimal amt = (totalRate.multiply(finalPerc))
														.divide(new BigDecimal(100));

												amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(amt);

											} else if (cAmt != null && !cAmt.isEmpty()) {

												BigDecimal disAmt = (totalRate
														.subtract(new BigDecimal(cAmt)))
														.setScale(3, BigDecimal.ROUND_HALF_UP);

												tempAss.setRates(disAmt);

											}  else {
												tempAss.setRates(totalRate);
											}

										}

									} else {
										tempAss.setServiceGroup(f[18] != null ? String.valueOf(f[18]) : "");
										tempAss.setServiceId(f[0] != null ? String.valueOf(f[0]) : "");
										tempAss.setServiceName(f[22] != null ? String.valueOf(f[22]) : "");
										tempAss.setServiceUnit(f[1] != null ? String.valueOf(f[1]) : "");
										tempAss.setServiceUnit1(f[7] != null ? String.valueOf(f[7]) : "");
										tempAss.setCurrencyId(f[5] != null ? String.valueOf(f[5]) : "");
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate
													.subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										}  else {
											tempAss.setRates(totalRate);
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
								tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
										? new BigDecimal(String.valueOf(f[23]))
										: BigDecimal.ZERO);
								tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
										? new BigDecimal(String.valueOf(f[24]))
										: BigDecimal.ZERO);
								tempAss.setmPercentage(BigDecimal.ZERO);
								tempAss.setmAmount(BigDecimal.ZERO);
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
										long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime) + 1;

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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate
													.subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										}  else {
											tempAss.setRates(totalRate);
										}


									}

									if ("WEEK".equals(unit)) {

										LocalDateTime gateInDateTime = LocalDateTime
												.ofInstant(con.getGateInDate().toInstant(), ZoneId.systemDefault());
										LocalDateTime destuffDateTime = LocalDateTime
												.ofInstant(con.getInvoiceDate().toInstant(), ZoneId.systemDefault());
										long daysBetween = ChronoUnit.DAYS.between(gateInDateTime, destuffDateTime) + 1;

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
										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate
													.subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										}  else {
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
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate
													.subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										}  else {
											tempAss.setRates(totalRate);
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
										tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
												? new BigDecimal(String.valueOf(f[23]))
												: BigDecimal.ZERO);
										tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
												? new BigDecimal(String.valueOf(f[24]))
												: BigDecimal.ZERO);
										tempAss.setmPercentage(BigDecimal.ZERO);
										tempAss.setmAmount(BigDecimal.ZERO);
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

										String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
										String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

										if (cPerc != null && !cPerc.isEmpty()) {
											BigDecimal disPerc = new BigDecimal(cPerc);
											BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

											BigDecimal amt = (totalRate.multiply(finalPerc))
													.divide(new BigDecimal(100));

											amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(amt);

										} else if (cAmt != null && !cAmt.isEmpty()) {

											BigDecimal disAmt = (totalRate
													.subtract(new BigDecimal(cAmt)))
													.setScale(3, BigDecimal.ROUND_HALF_UP);

											tempAss.setRates(disAmt);

										}  else {
											tempAss.setRates(totalRate);
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
									tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
											? new BigDecimal(String.valueOf(f[23]))
											: BigDecimal.ZERO);
									tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
											? new BigDecimal(String.valueOf(f[24]))
											: BigDecimal.ZERO);
									tempAss.setmPercentage(BigDecimal.ZERO);
									tempAss.setmAmount(BigDecimal.ZERO);
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

									String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
									String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

									if (cPerc != null && !cPerc.isEmpty()) {
										BigDecimal disPerc = new BigDecimal(cPerc);
										BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

										BigDecimal amt = (totalRate.multiply(finalPerc))
												.divide(new BigDecimal(100));

										amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(amt);

									} else if (cAmt != null && !cAmt.isEmpty()) {

										BigDecimal disAmt = (totalRate
												.subtract(new BigDecimal(cAmt)))
												.setScale(3, BigDecimal.ROUND_HALF_UP);

										tempAss.setRates(disAmt);

									}  else {
										tempAss.setRates(totalRate);
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
								tempAss.setDiscPercentage((f[23] != null && !String.valueOf(f[23]).isEmpty())
										? new BigDecimal(String.valueOf(f[23]))
										: BigDecimal.ZERO);
								tempAss.setDiscValue((f[24] != null && !String.valueOf(f[24]).isEmpty())
										? new BigDecimal(String.valueOf(f[24]))
										: BigDecimal.ZERO);
								tempAss.setmPercentage(BigDecimal.ZERO);
								tempAss.setmAmount(BigDecimal.ZERO);
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

								String cPerc = String.valueOf(f[23] == null ? "" : f[23]);
								String cAmt = String.valueOf(f[24] == null ? "" : f[24]);

								if (cPerc != null && !cPerc.isEmpty()) {
									BigDecimal disPerc = new BigDecimal(cPerc);
									BigDecimal finalPerc = new BigDecimal(100).subtract(disPerc);

									BigDecimal amt = (totalRate.multiply(finalPerc))
											.divide(new BigDecimal(100));

									amt = amt.setScale(3, BigDecimal.ROUND_HALF_UP);

									tempAss.setRates(amt);

								} else if (cAmt != null && !cAmt.isEmpty()) {

									BigDecimal disAmt = (totalRate
											.subtract(new BigDecimal(cAmt)))
											.setScale(3, BigDecimal.ROUND_HALF_UP);

									tempAss.setRates(disAmt);

								}  else {
									tempAss.setRates(totalRate);
								}

							}

							finalConData.add(tempAss);

						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});

				AssessmentSheet tempAssessment = (AssessmentSheet) assessment.clone();

			

				AssessmentSheet newAss = tempAssessment;
				
				GenerelJobEntry job = generaljobentryrepo.getGeneralData(cid, bid, assessment.getIgmTransId(),
						assessment.getIgmNo());

				if (job == null) {
					return new ResponseEntity<>("Job entry data not found", HttpStatus.CONFLICT);
				}


				newAss.setCompanyId(cid);
				newAss.setBranchId(bid);
				newAss.setAssesmentLineNo(String.valueOf(sr));
				newAss.setStatus('A');
				newAss.setCreatedBy(user);
				newAss.setCreatedDate(new Date());
				newAss.setApprovedBy(user);
				newAss.setApprovedDate(new Date());
				newAss.setAssesmentDate(new Date());
				newAss.setTransType("General Cargo");
				newAss.setProfitcentreId(con.getProfitcentreId());
				newAss.setIgmLineNo("");
				newAss.setViaNo("");
				newAss.setSbNo(job.getBoeNo());
				newAss.setSbDate(job.getBoeDate());
				newAss.setAssesmentId(HoldNextIdD1);
				newAss.setExBondingId(assessment.getIgmLineNo());
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
				newAss.setInvType("DELIVERY");
				newAss.setSa("");
				newAss.setSl("");
				newAss.setIsBos("N".equals(assessment.getTaxApplicable()) ? 'Y' : 'N');
				newAss.setSsrTransId(con.getSsrTransId());
//					newAss.setLastAssesmentDate(assessment.getLastAssesmentDate());
//					newAss.setLastAssesmentId(assessment.getLastAssesmentId());
//					newAss.setLastInvoiceDate(assessment.getLastInvoiceDate());
//					newAss.setLastInvoiceNo(assessment.getLastInvoiceNo());
//					newAss.setLastInvoiceUptoDate(con.getLastInvoiceUptoDate());
//				newAss.setInsuranceValue(new BigDecimal(con.getContainerNo()));
				newAss.setNoOf20ft(con.getContainerSize());
				newAss.setNoOf40ft(con.getContainerType());
				newAss.setNocWeeks(new BigDecimal(con.getGateOutType()));
				newAss.setNocFromDate(con.getLastInvoiceUptoDate());
				newAss.setNocValidityDate(con.getGateInDate());
				newAss.setInvoiceCalDate(finalConData.get(0).getInvoiceDate());
				assessmentsheetrepo.save(newAss);
				processnextidrepo.updateAuditTrail(cid, bid, "P05140", HoldNextIdD1, "2024");

//				AtomicReference<BigDecimal> srNo1 = new AtomicReference<>(new BigDecimal(1));
				AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> crgStorageDay = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> crgStorageAmt = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> invDay = new AtomicReference<>(BigDecimal.ZERO);
				AtomicReference<BigDecimal> anxSr = new AtomicReference<>(new BigDecimal(sr));

				finalConData.stream().forEach(c -> {
					if (c.getServiceId() != null && !c.getServiceId().isEmpty()) {
						Cfinvsrvanx anx = new Cfinvsrvanx();
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
						anx.setInvoiceType("GEN");
						anx.setInvoiceSubType("DEL");
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
						anx.setProcessId("P01112");
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

						cfinvsrvanxrepo.save(anx);

						srNo1.set(srNo1.get().add(new BigDecimal(1)));
					}
				});
				totalAmount.updateAndGet(amount -> amount.setScale(3, RoundingMode.HALF_UP));

		

				assessment.setAssesmentId(HoldNextIdD1);

			

				sr++;

			}

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

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForExbond(cid, bid, assessment.getAssesmentId());

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
	public ResponseEntity<?> saveExbondInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

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
			processNextId = "P05142";
		} else {
			processNextId = "P05141";
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
			back.setInvoiceType("GEN");
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
		srv.setInvoiceType("GEN");
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
		srv.setInvoiceSubType("DEL");

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
			fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
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

			if ("TDS".equals(p.getPayMode())) {
				fin.setTdsType(tdsDeductee);
				fin.setTdsPercentage(new BigDecimal(tdsPerc));
				fin.setTdsBillAmt(p.getAmount());
			}

			finTransrepo.save(fin);

			srNo1.set(srNo1.get().add(new BigDecimal(1)));
		});

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

			int updateAssessmentId = ssrRepo.updateAssessmentId1(cid, bid, o.getSsrTransId(), o.getAssesmentId());

		});

//		Cfexbonddts existingDtls = cfexbonddtlsrepo.getDataByNocTransIdAndAssessment(cid, bid, assSheet.getIgmTransId(),
//				assSheet.getAssesmentId());
//
//		if (existingDtls != null) {
//			existingDtls.setStorageValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setInsuranceValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setApprovedBy(user);
//			existingDtls.setApprovedDate(new Date());
//
//			cfexbonddtlsrepo.save(existingDtls);
//
//			int updateExbond = exbondrepo.updateInvoiceData(cid, bid, assSheet.getIgmTransId(),
//					existingDtls.getExBondingId(), HoldNextIdD1, assSheet.getInvoiceUptoDate(), billAmt.get(),
//					invAmt.get(), String.valueOf(assSheet.getCreditType()));
//
//		}

	    int updateDeliveryData = generaljobentryrepo.updateDeliveryEntry(cid, bid, assSheet.getExBondingId(), assSheet.getAssesmentId(),
	    		HoldNextIdD1, assSheet.getInvoiceUptoDate());

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForExbond(cid, bid, assessment.getAssesmentId());

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
				finalResult.put("tanNo", p.getTanNoId());
			}

		}

		return new ResponseEntity<>(finalResult, HttpStatus.OK);

	}

	@Transactional
	public ResponseEntity<?> saveExbondCreditInvoiceReceipt(String cid, String bid, String user,
			Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

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
			processNextId = "P05142";
		} else {
			processNextId = "P05141";
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
			back.setInvoiceType("GEN");
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
		srv.setInvoiceType("GEN");
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
		srv.setInvoiceSubType("DEL");

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
				fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
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
				fin.setCreditType("Y");
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
				fin.setCreditType("Y");
				fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
				fin.setProfitcentreId(assSheet.getProfitcentreId());
				fin.setCreditFlag(String.valueOf(assSheet.getCreditType()));
				fin.setPartyId(assSheet.getPayablePartyId());
				fin.setAccSrNo(assSheet.getPartySrNo().intValue());
				fin.setAcCode(consolidatedData.get(0).getAcCode());
				fin.setChequeNo(p.getChequeNo());
				fin.setChequeDate(p.getChequeDate());
				fin.setBankName(p.getBankDetails());
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

			int updateAssessmentId = ssrRepo.updateAssessmentId1(cid, bid, o.getSsrTransId(), o.getAssesmentId());

		});

//		Cfexbonddts existingDtls = cfexbonddtlsrepo.getDataByNocTransIdAndAssessment(cid, bid, assSheet.getIgmTransId(),
//				assSheet.getAssesmentId());
//
//		if (existingDtls != null) {
//			existingDtls.setStorageValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setInsuranceValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setApprovedBy(user);
//			existingDtls.setApprovedDate(new Date());
//
//			cfexbonddtlsrepo.save(existingDtls);
//
//			int updateExbond = exbondrepo.updateInvoiceData(cid, bid, assSheet.getIgmTransId(),
//					existingDtls.getExBondingId(), HoldNextIdD1, assSheet.getInvoiceUptoDate(), billAmt.get(),
//					invAmt.get(), String.valueOf(assSheet.getCreditType()));
//
//		}

	    int updateDeliveryData = generaljobentryrepo.updateDeliveryEntry(cid, bid, assSheet.getExBondingId(), assSheet.getAssesmentId(),
	    		HoldNextIdD1, assSheet.getInvoiceUptoDate());

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForExbond(cid, bid, assessment.getAssesmentId());

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
	public ResponseEntity<?> saveExbondPdaInvoiceReceipt(String cid, String bid, String user, Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		AssessmentSheet assessment = mapper.readValue(mapper.writeValueAsString(data.get("assessmentData")),
				AssessmentSheet.class);

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
			processNextId = "P05142";
		} else {
			processNextId = "P05141";
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
			back.setInvoiceType("GEN");
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
		srv.setInvoiceType("GEN");
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
		srv.setInvoiceSubType("DEL");

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
			fin.setCreditFlag("CREDIT".equals(p.getPayMode()) ? "Y" : "N");
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

			int updateAssessmentId = ssrRepo.updateAssessmentId1(cid, bid, o.getSsrTransId(), o.getAssesmentId());

		});

//		Cfexbonddts existingDtls = cfexbonddtlsrepo.getDataByNocTransIdAndAssessment(cid, bid, assSheet.getIgmTransId(),
//				assSheet.getAssesmentId());
//
//		if (existingDtls != null) {
//			existingDtls.setStorageValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setInsuranceValidityDate(assSheet.getInvoiceUptoDate());
//			existingDtls.setApprovedBy(user);
//			existingDtls.setApprovedDate(new Date());
//
//			cfexbonddtlsrepo.save(existingDtls);
//
//			int updateExbond = exbondrepo.updateInvoiceData(cid, bid, assSheet.getIgmTransId(),
//					existingDtls.getExBondingId(), HoldNextIdD1, assSheet.getInvoiceUptoDate(), billAmt.get(),
//					invAmt.get(), String.valueOf(assSheet.getCreditType()));
//
//		}

	    int updateDeliveryData = generaljobentryrepo.updateDeliveryEntry(cid, bid, assSheet.getExBondingId(), assSheet.getAssesmentId(),
	    		HoldNextIdD1, assSheet.getInvoiceUptoDate());

		List<Object[]> result = assessmentsheetrepo.getAssessmentDataForExbond(cid, bid, assessment.getAssesmentId());

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

}
