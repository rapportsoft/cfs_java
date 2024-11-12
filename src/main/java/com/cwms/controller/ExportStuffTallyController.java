package com.cwms.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.ExportAudit;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.Impexpgrid;
import com.cwms.entities.Vessel;
import com.cwms.entities.Voyage;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.ExportAuditRepo;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffRequestRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VesselRepository;
import com.cwms.repository.VoyageRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.cwms.service.ProcessNextIdService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/stuffTally")
public class ExportStuffTallyController {

	@Autowired
	private VesselRepository vesselrepo;

	@Autowired
	private VoyageRepository voyagerepo;

	@Autowired
	private ProcessNextIdService processService;

	@Autowired
	private ExportStuffRequestRepo exportstuffrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private ExportEntryRepo exportsbrepo;

	@Autowired
	private ExportCartingRepo exportcartingrepo;

	@Autowired
	private ExportSbCargoEntryRepo exportsbcargorepo;

	@Autowired
	private ExportStuffTallyRepo exportstufftallyrepo;

	@Autowired
	private Impexpgridrepo impexpgridrepo;

	@Autowired
	private ExportInventoryRepository exportinvrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private ExportAuditRepo exportauditrepo;

	@Autowired
	private YardBlockCellRepository yardBlockCellRepository;

	@GetMapping("/searchVoyage")
	public ResponseEntity<?> searchVessel(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("search") String search) {
		List<Voyage> voyage = voyagerepo.searchData(cid, bid, search);

		if (voyage.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(voyage, HttpStatus.OK);
	}

	@GetMapping("/getDataByStuffId")
	public ResponseEntity<?> getDataByStuffId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("search") String search) {

		List<String> data = exportstuffrepo.getDataBystuffReqId(cid, bid, search);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getDataByStuffReqId")
	public ResponseEntity<?> getDataByStuffReqId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id) {

		List<ExportStuffRequest> data = exportstuffrepo.getDataByStuffReqId(cid, bid, id);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		Object[] cha = exportsbrepo.getChaName(cid, bid, data.get(0).getSbNo(), data.get(0).getSbTransId());

		Map<String, Object> result = new HashMap<>();
		result.put("data", data);
		result.put("cha", cha);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/saveStuffingTally")
	public ResponseEntity<?> saveStuffingTally(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@Param("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {

		ObjectMapper mapper = new ObjectMapper();

		ExportStuffTally singleTally = mapper.readValue(mapper.writeValueAsString(data.get("singleTally")),
				ExportStuffTally.class);

		List<ExportStuffTally> tally = mapper.readValue(mapper.writeValueAsString(data.get("tally")),
				new TypeReference<List<ExportStuffTally>>() {
				});

		if (tally.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		if (singleTally.getVesselId() == null || singleTally.getVesselId().isEmpty()) {
			String autoIncrementVesselId = processService.autoIncrementVesselId(cid, bid, "P03202");
			Date currentDate = new Date();
			Date zeroDate = new Date(0);

			Vessel newVessel = new Vessel(cid, bid, autoIncrementVesselId, singleTally.getVesselName(), "P00220",
					singleTally.getTerminal(), "Y", "Y", singleTally.getVesselName(), user, new Date(), user,
					new Date());

			Voyage newVoyage = new Voyage(cid, bid, singleTally.getPod(), "", autoIncrementVesselId,
					singleTally.getVoyageNo(), singleTally.getViaNo(), " ", " ", " ", 1, zeroDate, zeroDate,
					singleTally.getGateOpenDate(), zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, 0, 0, "",
					singleTally.getVesselName(), "", singleTally.getBerthingDate(), singleTally.getRotationNo(),
					singleTally.getRotationDate(), zeroDate, " ", zeroDate, " ", zeroDate, new BigDecimal("0"), user,
					currentDate, user, currentDate, user, currentDate, "A");

			vesselrepo.save(newVessel);
			voyagerepo.save(newVoyage);

			singleTally.setVesselId(autoIncrementVesselId);

		}

		if (singleTally.getStuffTallyId() == null || singleTally.getStuffTallyId().isEmpty()) {
			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05083", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("STW%07d", nextNumericNextID1);

			String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05084", "2024");

			// Extract the parts of the ID
			String[] parts = lastValue.split("/");
			String baseIdWithPrefix = parts[0]; // This should be "STWO0000"
			String numericPart = baseIdWithPrefix.substring(4); // Extract the numeric part after "STWO"
			String financialYear = parts[1];

			// Increment the numerical part
			int newVal = Integer.parseInt(numericPart) + 1;

			// Format newVal to maintain leading zeros (e.g., 0001)
			String formattedNewVal = String.format("%04d", newVal);

			// Get the current financial year
			String currentFinancialYear = getCurrentFinancialYear();

			// Construct the new ID in the desired format
			String newId = "STWO" + formattedNewVal + "/" + currentFinancialYear;

			int sr = 1;
			for (ExportStuffTally e : tally) {

				List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTransAndLineNo(cid, bid,
						e.getSbTransId(), e.getSbNo(), e.getSbLineId());

				if (cartingData.isEmpty()) {
					return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
				}

				System.out.println("cartingData " + cartingData.size());

				ExportStuffRequest stuffReq = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId2(cid, bid,
						e.getSbTransId(), e.getSbNo(), singleTally.getStuffId());

				if (stuffReq == null) {
					return new ResponseEntity<>("Stuffing request data not found", HttpStatus.CONFLICT);
				}

				ExportSbCargoEntry cargo = exportsbcargorepo.getExportSbEntryBySbNoAndSbTransIdAndSbLine(cid, bid,
						e.getSbNo(), e.getSbTransId(), e.getSbLineId());

				if (cargo == null) {
					return new ResponseEntity<>("Export cargo data not found", HttpStatus.CONFLICT);
				}

				ExportSbEntry sbENtry = exportsbrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, e.getSbNo(),
						e.getSbTransId());

				if (sbENtry == null) {
					return new ResponseEntity<>("Export sb data not found", HttpStatus.CONFLICT);
				}

				BigDecimal val = e.getStuffedQty();

				for (ExportCarting c : cartingData) {
					BigDecimal remainingQty = c.getActualNoOfPackages().subtract(c.getStuffedNoOfPackages());

					if (val.compareTo(BigDecimal.ZERO) <= 0) {
						break;
					}

					BigDecimal qty = BigDecimal.ZERO;

					if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
						qty = remainingQty;
					} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
						qty = val;
					} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
						qty = remainingQty;
					}

					BigDecimal area = (c.getAreaOccupied().multiply(qty)).divide(c.getYardPackages());

					ExportStuffTally newTally = new ExportStuffTally();

					newTally.setCompanyId(cid);
					newTally.setBranchId(bid);
					newTally.setStuffTallyId(HoldNextIdD1);
					newTally.setSbTransId(c.getSbTransId());
					newTally.setStuffTallyLineId(sr);
					newTally.setProfitcentreId(c.getProfitcentreId());
					newTally.setCartingTransId(c.getCartingTransId());
					newTally.setCartingLineId(c.getCartingLineId());
					newTally.setSbLineId(c.getSbLineNo());
					newTally.setSbNo(c.getSbNo());
					newTally.setStuffTallyWoTransId(newId);
					newTally.setStuffTallyCutWoTransDate(new Date());
					newTally.setStuffTallyDate(new Date());
					newTally.setStuffId(stuffReq.getStuffReqId());
					newTally.setStuffDate(stuffReq.getStuffReqDate());
					newTally.setSbDate(cargo.getSbDate());
					newTally.setShift(singleTally.getShift());
					newTally.setAgentSealNo(singleTally.getAgentSealNo());
					newTally.setVesselId(singleTally.getVesselId());
					newTally.setVoyageNo(singleTally.getVoyageNo());
					newTally.setRotationNo(singleTally.getRotationNo());
					newTally.setRotationDate(singleTally.getRotationDate());
					newTally.setPol(sbENtry.getPol());
					newTally.setTerminal(singleTally.getTerminal());
					newTally.setPod(singleTally.getPod());
					newTally.setFinalPod(singleTally.getFinalPod());
					newTally.setContainerNo(singleTally.getContainerNo());
					newTally.setContainerSize(singleTally.getContainerSize());
					newTally.setPeriodFrom(singleTally.getPeriodFrom());
					newTally.setGateInId(stuffReq.getGateInId());
					newTally.setContainerStatus(singleTally.getContainerStatus());
					newTally.setContainerType(singleTally.getContainerType());
					newTally.setContainerCondition(stuffReq.getContainerHealth());
					newTally.setYardPackages(c.getYardPackages());
					newTally.setCellAreaAllocated(e.getCellAreaAllocated());
					newTally.setOnAccountOf(stuffReq.getOnAccountOf());
					newTally.setCha(singleTally.getCha());
					newTally.setTotalGrossWeight(singleTally.getTotalGrossWeight());
					newTally.setCargoWeight(e.getCargoWeight());
					newTally.setStuffRequestQty(e.getStuffRequestQty());
					newTally.setStuffedQty(qty);
					stuffReq.setStuffedQty(
							stuffReq.getStuffedQty() == null ? BigDecimal.ZERO : stuffReq.getStuffedQty().add(qty));
					newTally.setBalanceQty(c.getYardPackages().subtract(qty));
					newTally.setTareWeight(singleTally.getTareWeight());
					newTally.setAreaReleased(area);
					newTally.setGenSetRequired(singleTally.getGenSetRequired());
					newTally.setHaz(singleTally.getHaz());
					newTally.setImoCode(sbENtry.getImoCode());
					newTally.setShippingAgent(stuffReq.getShippingAgent());
					newTally.setShippingLine(stuffReq.getShippingLine());
					newTally.setCommodity(e.getCommodity());
					newTally.setCustomsSealNo(singleTally.getCustomsSealNo());
					newTally.setViaNo(singleTally.getViaNo());
					newTally.setCartingDate(c.getCartingTransDate());
					newTally.setExporterName(sbENtry.getExporterName());
					newTally.setConsignee(e.getConsignee());
					newTally.setFob(e.getFob());
					newTally.setBerthingDate(singleTally.getBerthingDate());
					newTally.setGateOpenDate(singleTally.getGateOpenDate());
					newTally.setDocType(singleTally.getDocType());
					newTally.setDocNo(singleTally.getDocNo());
					newTally.setStatus("A");
					newTally.setCreatedBy(user);
					newTally.setCreatedDate(new Date());
					newTally.setApprovedBy(user);
					newTally.setApprovedDate(new Date());
					newTally.setDeliveryOrderNo(singleTally.getDeliveryOrderNo());
					newTally.setStuffMode(singleTally.getStuffMode());
					newTally.setStuffLineId(stuffReq.getStuffReqLineId());
					newTally.setTypeOfPackage(e.getTypeOfPackage());
					newTally.setSealType(singleTally.getSealType());
					newTally.setTotalCargoWeight(e.getTotalCargoWeight());

					exportstufftallyrepo.save(newTally);
					sr++;
					processnextidrepo.updateAuditTrail(cid, bid, "P05083", HoldNextIdD1, "2024");
					processnextidrepo.updateAuditTrail(cid, bid, "P05084", newId, "2024");

					c.setStuffedNoOfPackages(
							(c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO : c.getStuffedNoOfPackages())
									.add(qty));

					exportcartingrepo.save(c);

					val = val.subtract(qty);

					List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid, c.getCartingTransId(),
							c.getCartingLineId());

					if (!grid.isEmpty()) {
						AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
						grid.stream().forEach(g -> {
							BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages()); // Convert yardPackages
																								// to BigDecimal once
							BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut() : BigDecimal.ZERO;
							BigDecimal gridValue = gridVal.get();
							BigDecimal remainingYardPackages = yardPackages.subtract(qtyTakenOut); // Calculate
																									// remaining yard
																									// packages

							YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
									g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());

							if (gridValue.compareTo(remainingYardPackages) >= 0) {
								// Case where gridValue is greater than or equal to remaining yard packages

								// Update QtyTakenOut to include the remaining yard packages
								g.setQtyTakenOut(qtyTakenOut.add(remainingYardPackages));

								// Calculate area to release based on remaining yard packages
								BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingYardPackages)
										.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

								// Update AreaReleased, adding the calculated tenArea
								g.setAreaReleased((g.getAreaReleased() != null ? g.getAreaReleased() : BigDecimal.ZERO)
										.add(tenArea));

								// Subtract remaining yard packages from gridVal
								gridVal.set(gridValue.subtract(remainingYardPackages));

								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.subtract(tenArea));
									yardBlockCellRepository.save(yard);
								}
							} else {
								// Case where gridValue is less than remaining yard packages

								// Partially update QtyTakenOut by adding gridValue
								g.setQtyTakenOut(qtyTakenOut.add(gridValue));

								// Calculate the area to release based on gridValue amount
								BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue).divide(yardPackages,
										BigDecimal.ROUND_HALF_UP);

								// Update AreaReleased by adding the calculated tenArea
								g.setAreaReleased((g.getAreaReleased() != null ? g.getAreaReleased() : BigDecimal.ZERO)
										.add(tenArea));

								// Set gridVal to zero after deduction
								gridVal.set(BigDecimal.ZERO);

								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.subtract(tenArea));
									yardBlockCellRepository.save(yard);
								}
							}

							impexpgridrepo.save(g);
						});
					}
				}

				stuffReq.setStuffTally('Y');
				stuffReq.setStuffTallyId(HoldNextIdD1);
				stuffReq.setAgentSealNo(singleTally.getAgentSealNo());
				stuffReq.setTareWeight(singleTally.getTareWeight());
				stuffReq.setRotationNo(singleTally.getRotationNo());
				stuffReq.setRotationDate(singleTally.getRotationDate());
				stuffReq.setBerthingDate(singleTally.getBerthingDate());
				stuffReq.setGateOpenDate(singleTally.getGateOpenDate());
				stuffReq.setTypeOfPackage(e.getTypeOfPackage());
				stuffReq.setVesselId(singleTally.getVesselId());
				stuffReq.setVesselName(singleTally.getVesselName());
				stuffReq.setVoyageNo(singleTally.getVoyageNo());
				stuffReq.setViaNo(singleTally.getViaNo());
				stuffReq.setEditedBy(user);
				stuffReq.setEditedDate(new Date());

				exportstuffrepo.save(stuffReq);

				cargo.setStuffedQty((cargo.getStuffedQty() == null ? BigDecimal.ZERO : cargo.getStuffedQty())
						.add(e.getStuffedQty()));
				cargo.setStuffedWt((cargo.getStuffedWt() == null ? BigDecimal.ZERO : cargo.getStuffedWt())
						.add(e.getCargoWeight()));
				exportsbcargorepo.save(cargo);

			}

			ExportInventory inv = exportinvrepo.getDataByContainerNo(cid, bid, singleTally.getContainerNo());

			if (inv != null) {
				inv.setStuffTallyDate(new Date());
				inv.setStuffTallyId(HoldNextIdD1);

				exportinvrepo.save(inv);
			}

			List<ExportStuffTally> result = exportstufftallyrepo.getDataByTallyId(cid, bid, HoldNextIdD1);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {

			List<ExportStuffTally> existingData = exportstufftallyrepo.getDataByStuffTallyId(cid, bid,
					singleTally.getStuffTallyId());

			if (existingData.isEmpty()) {
				return new ResponseEntity<>("Movement already done!!", HttpStatus.CONFLICT);
			}

			ExportStuffTally e3 = (ExportStuffTally) existingData.get(0).clone();

			System.out.println("rotation " + singleTally.getRotationNo() + " " + e3.getRotationNo());

			for (ExportStuffTally t : tally) {

				Optional<ExportStuffTally> optionalExportStuffTally1 = existingData.stream()
						.filter(t1 -> t1.getSbNo().equals(t.getSbNo())).findFirst();

				List<ExportStuffTally> data1 = existingData.stream().filter(t1 -> t1.getSbNo().equals(t.getSbNo())) // Match
																													// on
																													// sbNo
						.collect(Collectors.toList());

				ExportStuffTally e2 = optionalExportStuffTally1.get();

				BigDecimal existCargoWt = e3.getCargoWeight();
				BigDecimal existStuffQty = data1.stream().map(ExportStuffTally::getStuffedQty).reduce(BigDecimal.ZERO,
						BigDecimal::add);

				final BigDecimal existCargoWt1 = t.getStuffedQty();
				final BigDecimal existStuffQty1 = t.getCargoWeight();

				System.out.println("existStuffQty " + existStuffQty + " " + existCargoWt);
				BigDecimal existAreaRelease = data1.stream().map(ExportStuffTally::getAreaReleased)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				String existtype = e3.getTypeOfPackage();

				ExportStuffRequest stuffReq = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId3(cid, bid,
						t.getSbTransId(), t.getSbNo(), singleTally.getStuffId());

				if (stuffReq == null) {
					return new ResponseEntity<>("Stuffing request data not found", HttpStatus.CONFLICT);
				}

				ExportStuffRequest stuffReq1 = (ExportStuffRequest) stuffReq.clone();

				ExportSbCargoEntry cargo = exportsbcargorepo.getExportSbEntryBySbNoAndSbTransIdAndSbLine(cid, bid,
						t.getSbNo(), t.getSbTransId(), t.getSbLineId());

				if (cargo == null) {
					return new ResponseEntity<>("Export cargo data not found", HttpStatus.CONFLICT);
				}

				List<ExportStuffTally> existingData1 = existingData.stream()
						.filter(e1 -> e1.getSbNo().equals(t.getSbNo()))
						.sorted(Comparator.comparing(ExportStuffTally::getStuffedQty)).collect(Collectors.toList());

				BigDecimal totalStuffedQty = existingData1.stream().map(ExportStuffTally::getStuffedQty)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

				final BigDecimal total = totalStuffedQty;

				BigDecimal val1 = t.getStuffedQty();
				BigDecimal val = BigDecimal.ZERO;
				BigDecimal val2 = BigDecimal.ZERO;

				if (totalStuffedQty.compareTo(val1) < 0) {

					if (stuffReq.getNoOfPackagesStuffed().compareTo(val1) < 0
							&& stuffReq.getNoOfPackagesStuffed().compareTo(total) > 0) {
						val = val1.subtract(totalStuffedQty);
						val2 = val1.subtract(totalStuffedQty);
					} else if (stuffReq.getNoOfPackagesStuffed().compareTo(val1) < 0
							&& stuffReq.getNoOfPackagesStuffed().compareTo(total) < 0) {
						val = val1.subtract(totalStuffedQty);
						val2 = val1.subtract(totalStuffedQty);
					} else {
						val = val1.subtract(totalStuffedQty);
						val2 = val1.subtract(totalStuffedQty);
					}

				} else if (totalStuffedQty.compareTo(val1) > 0) {

					val = totalStuffedQty.subtract(val1);
					val2 = totalStuffedQty.subtract(val1);

				}

				System.out.println("val------ " + val);

				for (ExportStuffTally exist : existingData1) {
					ExportCarting existCar = exportcartingrepo.getDataBySbNoSbTransAndLineNoAndCartingTransId(cid, bid,
							t.getSbTransId(), t.getSbNo(), exist.getCartingTransId(), exist.getCartingLineId());

					if (existCar == null) {
						return new ResponseEntity<>("Carting data not found.", HttpStatus.CONFLICT);
					}

					if (totalStuffedQty.compareTo(val1) > 0) {
						BigDecimal remainingQty = existCar.getStuffedNoOfPackages();

						if (val.compareTo(BigDecimal.ZERO) <= 0) {
							exist.setStuffMode(singleTally.getStuffMode());
							exist.setShift(singleTally.getShift());
							exist.setAgentSealNo(singleTally.getAgentSealNo());
							exist.setCustomsSealNo(singleTally.getCustomsSealNo());
							exist.setContainerStatus(singleTally.getContainerStatus());
							exist.setVesselId(singleTally.getVesselId());
							exist.setVoyageNo(singleTally.getVoyageNo());
							exist.setViaNo(singleTally.getViaNo());
							exist.setTerminal(singleTally.getTerminal());
							exist.setPod(singleTally.getPod());
							exist.setContainerCondition(singleTally.getContainerCondition());
							exist.setTareWeight(singleTally.getTareWeight());
							exist.setFinalPod(singleTally.getFinalPod());
							exist.setCha(singleTally.getCha());
							exist.setHaz(singleTally.getHaz());
							exist.setSealType(singleTally.getSealType());
							exist.setGenSetRequired(singleTally.getGenSetRequired());
							exist.setDocType(singleTally.getDocType());
							exist.setDocNo(singleTally.getDocNo());
							exist.setRotationNo(singleTally.getRotationNo());
							exist.setRotationDate(singleTally.getRotationDate());
							exist.setBerthingDate(singleTally.getBerthingDate());
							exist.setGateOpenDate(singleTally.getGateOpenDate());
							exist.setTypeOfPackage(t.getTypeOfPackage());
							exist.setEditedBy(user);
							exist.setEditedDate(new Date());
							exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
							exist.setCargoWeight(t.getCargoWeight());
							exportstufftallyrepo.save(exist);
						} else {
							if (exist.getStuffId().equals(singleTally.getStuffId())) {
								BigDecimal qty = BigDecimal.ZERO;

//								if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
//									qty = remainingQty;
//								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
//									qty = val;
//								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
//									qty = remainingQty;
//								}

								System.out.println("remainingQty " + remainingQty + " " + val + " "
										+ stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty()));

								if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

									if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
										qty = exist.getStuffedQty();
									} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
										qty = remainingQty;
									} else {
										qty = remainingQty;
									}

								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

									if (val.compareTo(exist.getStuffedQty()) > 0) {
										qty = exist.getStuffedQty();
									} else if (val.compareTo(exist.getStuffedQty()) < 0) {
										qty = val;
									} else {
										qty = val;
									}

								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
									if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
										qty = exist.getStuffedQty();
									} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
										qty = remainingQty;
									} else {
										qty = remainingQty;
									}
								}

								System.out.println("less qty " + qty);

								BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
										.divide(existCar.getYardPackages());

								Optional<ExportStuffTally> optionalExportStuffTally = tally.stream()
										.filter(t1 -> t1.getSbNo().equals(exist.getSbNo())).findFirst();

								ExportStuffTally e = optionalExportStuffTally.get();

								exist.setStuffedQty(exist.getStuffedQty().subtract(qty));
								exist.setBalanceQty(exist.getBalanceQty().add(qty));
								exist.setAreaReleased(exist.getAreaReleased().subtract(area));
								stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
										: stuffReq.getStuffedQty().subtract(qty));

								val = val.subtract(qty);

								existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().subtract(qty));

								exportcartingrepo.save(existCar);

								List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
										existCar.getCartingTransId(), existCar.getCartingLineId());

								if (!grid.isEmpty()) {
									AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);

									grid.stream().forEach(g -> {
										YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
												g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
										BigDecimal gridValue = gridVal.get();
										BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut()
												: BigDecimal.ZERO;
										BigDecimal areaReleased = g.getAreaReleased() != null ? g.getAreaReleased()
												: BigDecimal.ZERO;
										BigDecimal yardPackages = new BigDecimal(g.getYardPackages());

										// Case when gridVal is greater than or equal to qtyTakenOut
										if (gridValue.compareTo(qtyTakenOut) >= 0) {
											// Since you want to "remove" qtyTakenOut, it's more logical to set it to
											// zero
											g.setQtyTakenOut(BigDecimal.ZERO); // Reset qtyTakenOut to zero

											// Calculate the area released based on qtyTakenOut
											BigDecimal tenArea = g.getCellAreaAllocated().multiply(qtyTakenOut)
													.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

											// Update the area released
											g.setAreaReleased(areaReleased.subtract(tenArea));

											// Subtract qtyTakenOut from gridVal
											gridVal.set(gridValue.subtract(qtyTakenOut));

											if (yard != null) {
												yard.setCellAreaUsed((yard.getCellAreaUsed() == null ? BigDecimal.ZERO
														: yard.getCellAreaUsed()).add(tenArea));
												yardBlockCellRepository.save(yard);
											}
										} else {
											// Case when gridVal is less than qtyTakenOut, we need to subtract gridVal
											// from qtyTakenOut
											g.setQtyTakenOut(qtyTakenOut.subtract(gridValue));

											// Calculate the area released based on gridVal
											BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
													.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

											// Update the area released
											g.setAreaReleased(areaReleased.subtract(tenArea));

											// Reset gridVal to zero after using its value
											gridVal.set(BigDecimal.ZERO);
											if (yard != null) {
												yard.setCellAreaUsed((yard.getCellAreaUsed() == null ? BigDecimal.ZERO
														: yard.getCellAreaUsed()).add(tenArea));
												yardBlockCellRepository.save(yard);
											}
										}

										impexpgridrepo.save(g);
									});
								}

								exist.setStuffMode(singleTally.getStuffMode());
								exist.setShift(singleTally.getShift());
								exist.setAgentSealNo(singleTally.getAgentSealNo());
								exist.setCustomsSealNo(singleTally.getCustomsSealNo());
								exist.setContainerStatus(singleTally.getContainerStatus());
								exist.setVesselId(singleTally.getVesselId());
								exist.setVoyageNo(singleTally.getVoyageNo());
								exist.setViaNo(singleTally.getViaNo());
								exist.setTerminal(singleTally.getTerminal());
								exist.setPod(singleTally.getPod());
								exist.setContainerCondition(singleTally.getContainerCondition());
								exist.setTareWeight(singleTally.getTareWeight());
								exist.setFinalPod(singleTally.getFinalPod());
								exist.setCha(singleTally.getCha());
								exist.setHaz(singleTally.getHaz());
								exist.setSealType(singleTally.getSealType());
								exist.setGenSetRequired(singleTally.getGenSetRequired());
								exist.setDocType(singleTally.getDocType());
								exist.setDocNo(singleTally.getDocNo());
								exist.setRotationNo(singleTally.getRotationNo());
								exist.setRotationDate(singleTally.getRotationDate());
								exist.setBerthingDate(singleTally.getBerthingDate());
								exist.setGateOpenDate(singleTally.getGateOpenDate());
								exist.setTypeOfPackage(t.getTypeOfPackage());
								exist.setEditedBy(user);
								exist.setEditedDate(new Date());
								exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
								exist.setCargoWeight(t.getCargoWeight());
								exportstufftallyrepo.save(exist);
							} else {
								BigDecimal qty = BigDecimal.ZERO;

//								if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
//									qty = remainingQty;
//								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
//									qty = val;
//								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
//									qty = remainingQty;
//								}

								ExportStuffRequest req = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId3(cid,
										bid, t.getSbTransId(), t.getSbNo(), exist.getStuffId());

								if (req != null) {
									if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (val.compareTo(exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (val.compareTo(exist.getStuffedQty()) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}
									}

									BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
											.divide(existCar.getYardPackages());

									Optional<ExportStuffTally> optionalExportStuffTally = tally.stream()
											.filter(t1 -> t1.getSbNo().equals(exist.getSbNo())).findFirst();

									ExportStuffTally e = optionalExportStuffTally.get();

									exist.setStuffedQty(exist.getStuffedQty().subtract(qty));
									exist.setBalanceQty(exist.getBalanceQty().add(qty));
									exist.setAreaReleased(exist.getAreaReleased().subtract(area));
									req.setStuffedQty(req.getStuffedQty() == null ? BigDecimal.ZERO
											: req.getStuffedQty().subtract(qty));

									exportstuffrepo.save(req);

									val = val.subtract(qty);

									existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().subtract(qty));

									exportcartingrepo.save(existCar);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
											existCar.getCartingTransId(), existCar.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
											BigDecimal gridValue = gridVal.get();
											BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
													: g.getQtyTakenOut();
											BigDecimal areaReleased = (g.getAreaReleased() == null) ? BigDecimal.ZERO
													: g.getAreaReleased();
											BigDecimal yardPackages = new BigDecimal(g.getYardPackages());

											// Case when gridVal is greater than or equal to qtyTakenOut
											if (gridValue.compareTo(qtyTakenOut) >= 0) {
												// Reset qtyTakenOut and areaReleased to zero
												g.setQtyTakenOut(BigDecimal.ZERO);
												g.setAreaReleased(BigDecimal.ZERO);

												// Since qtyTakenOut is zero, tenArea will also be zero, no need for
												// calculation
												BigDecimal tenArea = BigDecimal.ZERO;

												// Subtract qtyTakenOut (which is now zero) from gridVal
												gridVal.set(gridValue.subtract(qtyTakenOut));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).add(tenArea));
													yardBlockCellRepository.save(yard);
												}
											} else {
												// Case when gridVal is less than qtyTakenOut, we subtract gridVal from
												// qtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.subtract(gridValue));

												// Calculate the area released based on the remaining qtyTakenOut after
												// gridVal subtraction
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridValue))
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update the area released
												g.setAreaReleased(areaReleased.subtract(tenArea));

												// Reset gridVal to zero after use
												gridVal.set(BigDecimal.ZERO);
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).add(tenArea));
													yardBlockCellRepository.save(yard);
												}
											}

											impexpgridrepo.save(g);
										});
									}

									exist.setStuffMode(singleTally.getStuffMode());
									exist.setShift(singleTally.getShift());
									exist.setAgentSealNo(singleTally.getAgentSealNo());
									exist.setCustomsSealNo(singleTally.getCustomsSealNo());
									exist.setContainerStatus(singleTally.getContainerStatus());
									exist.setVesselId(singleTally.getVesselId());
									exist.setVoyageNo(singleTally.getVoyageNo());
									exist.setViaNo(singleTally.getViaNo());
									exist.setTerminal(singleTally.getTerminal());
									exist.setPod(singleTally.getPod());
									exist.setContainerCondition(singleTally.getContainerCondition());
									exist.setTareWeight(singleTally.getTareWeight());
									exist.setFinalPod(singleTally.getFinalPod());
									exist.setCha(singleTally.getCha());
									exist.setHaz(singleTally.getHaz());
									exist.setSealType(singleTally.getSealType());
									exist.setGenSetRequired(singleTally.getGenSetRequired());
									exist.setDocType(singleTally.getDocType());
									exist.setDocNo(singleTally.getDocNo());
									exist.setRotationNo(singleTally.getRotationNo());
									exist.setRotationDate(singleTally.getRotationDate());
									exist.setBerthingDate(singleTally.getBerthingDate());
									exist.setGateOpenDate(singleTally.getGateOpenDate());
									exist.setTypeOfPackage(t.getTypeOfPackage());
									exist.setEditedBy(user);
									exist.setEditedDate(new Date());
									exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
									exist.setCargoWeight(t.getCargoWeight());
									exportstufftallyrepo.save(exist);
								}
							}
						}

					} else if (totalStuffedQty.compareTo(val1) < 0) {
						BigDecimal remainingQty = existCar.getActualNoOfPackages()
								.subtract(existCar.getStuffedNoOfPackages());

						System.out.println("remainingQty remainingQty " + remainingQty);

						if (remainingQty.compareTo(BigDecimal.ZERO) > 0) {
							if (val.compareTo(BigDecimal.ZERO) <= 0) {
								exist.setStuffMode(singleTally.getStuffMode());
								exist.setShift(singleTally.getShift());
								exist.setAgentSealNo(singleTally.getAgentSealNo());
								exist.setCustomsSealNo(singleTally.getCustomsSealNo());
								exist.setContainerStatus(singleTally.getContainerStatus());
								exist.setVesselId(singleTally.getVesselId());
								exist.setVoyageNo(singleTally.getVoyageNo());
								exist.setViaNo(singleTally.getViaNo());
								exist.setTerminal(singleTally.getTerminal());
								exist.setPod(singleTally.getPod());
								exist.setContainerCondition(singleTally.getContainerCondition());
								exist.setTareWeight(singleTally.getTareWeight());
								exist.setFinalPod(singleTally.getFinalPod());
								exist.setCha(singleTally.getCha());
								exist.setHaz(singleTally.getHaz());
								exist.setSealType(singleTally.getSealType());
								exist.setGenSetRequired(singleTally.getGenSetRequired());
								exist.setDocType(singleTally.getDocType());
								exist.setDocNo(singleTally.getDocNo());
								exist.setRotationNo(singleTally.getRotationNo());
								exist.setRotationDate(singleTally.getRotationDate());
								exist.setBerthingDate(singleTally.getBerthingDate());
								exist.setGateOpenDate(singleTally.getGateOpenDate());
								exist.setTypeOfPackage(t.getTypeOfPackage());
								exist.setEditedBy(user);
								exist.setEditedDate(new Date());
								exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
								exist.setCargoWeight(t.getCargoWeight());
								exportstufftallyrepo.save(exist);
							} else {

								if (exist.getStuffId().equals(singleTally.getStuffId())) {
									BigDecimal qty = BigDecimal.ZERO;
									System.out.println("val val " + val);
									if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}
									}

									BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
											.divide(existCar.getYardPackages());

									Optional<ExportStuffTally> optionalExportStuffTally = tally.stream()
											.filter(t1 -> t1.getSbNo().equals(exist.getSbNo())).findFirst();

									ExportStuffTally e = optionalExportStuffTally.get();

									exist.setStuffedQty(exist.getStuffedQty().add(qty));
									exist.setBalanceQty(exist.getBalanceQty().subtract(qty));
									exist.setAreaReleased(exist.getAreaReleased().add(area));
									stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
											: stuffReq.getStuffedQty().add(qty));

									val = val.subtract(qty);

									existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().add(qty));

									exportcartingrepo.save(existCar);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
											existCar.getCartingTransId(), existCar.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());

											BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
											BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
													: g.getQtyTakenOut();
											BigDecimal gridValue = gridVal.get();
											BigDecimal areaReleased = (g.getAreaReleased() == null) ? BigDecimal.ZERO
													: g.getAreaReleased();

											// If gridValue is greater than or equal to the remaining qty to be taken
											// out
											if (gridValue.compareTo(yardPackages.subtract(qtyTakenOut)) >= 0) {

												// Add the remaining qty to qtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(yardPackages.subtract(qtyTakenOut)));

												// Calculate the area released based on the remaining qty
												BigDecimal tenArea = (g.getCellAreaAllocated()
														.multiply(yardPackages.subtract(qtyTakenOut)))
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update the areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Subtract the quantity that was taken out from gridValue
												gridVal.set(gridValue.subtract(yardPackages.subtract(qtyTakenOut)));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}

											} else {
												// If gridValue is less than the remaining qty to be taken out
												g.setQtyTakenOut(qtyTakenOut.add(gridValue));

												// Calculate the area released based on gridValue
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridValue))
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update the areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Subtract gridValue from gridVal to update the remaining gridValue
												gridVal.set(gridValue.subtract(gridValue));

												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											}

											impexpgridrepo.save(g);
										});
									}

									exist.setStuffMode(singleTally.getStuffMode());
									exist.setShift(singleTally.getShift());
									exist.setAgentSealNo(singleTally.getAgentSealNo());
									exist.setCustomsSealNo(singleTally.getCustomsSealNo());
									exist.setContainerStatus(singleTally.getContainerStatus());
									exist.setVesselId(singleTally.getVesselId());
									exist.setVoyageNo(singleTally.getVoyageNo());
									exist.setViaNo(singleTally.getViaNo());
									exist.setTerminal(singleTally.getTerminal());
									exist.setPod(singleTally.getPod());
									exist.setContainerCondition(singleTally.getContainerCondition());
									exist.setTareWeight(singleTally.getTareWeight());
									exist.setFinalPod(singleTally.getFinalPod());
									exist.setCha(singleTally.getCha());
									exist.setHaz(singleTally.getHaz());
									exist.setSealType(singleTally.getSealType());
									exist.setGenSetRequired(singleTally.getGenSetRequired());
									exist.setDocType(singleTally.getDocType());
									exist.setDocNo(singleTally.getDocNo());
									exist.setRotationNo(singleTally.getRotationNo());
									exist.setRotationDate(singleTally.getRotationDate());
									exist.setBerthingDate(singleTally.getBerthingDate());
									exist.setGateOpenDate(singleTally.getGateOpenDate());
									exist.setTypeOfPackage(t.getTypeOfPackage());
									exist.setEditedBy(user);
									exist.setEditedDate(new Date());
									exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
									exist.setCargoWeight(t.getCargoWeight());
									exportstufftallyrepo.save(exist);
								} else {
									BigDecimal qty = BigDecimal.ZERO;
									System.out.println("val val " + val);

									ExportStuffRequest req = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId3(cid,
											bid, t.getSbTransId(), t.getSbNo(), exist.getStuffId());

									if (req != null) {
										if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

											if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = req.getNoOfPackagesStuffed().subtract(req.getStuffedQty());
											} else if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = remainingQty;
											} else {
												qty = remainingQty;
											}

										} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

											if (val.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = req.getNoOfPackagesStuffed().subtract(req.getStuffedQty());
											} else if (val.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = val;
											} else {
												qty = val;
											}

										} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
											if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = req.getNoOfPackagesStuffed().subtract(req.getStuffedQty());
											} else if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = remainingQty;
											} else {
												qty = remainingQty;
											}
										}

										BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
												.divide(existCar.getYardPackages());

										Optional<ExportStuffTally> optionalExportStuffTally = tally.stream()
												.filter(t1 -> t1.getSbNo().equals(exist.getSbNo())).findFirst();

										ExportStuffTally e = optionalExportStuffTally.get();

										exist.setStuffedQty(exist.getStuffedQty().add(qty));
										exist.setBalanceQty(exist.getBalanceQty().subtract(qty));
										exist.setAreaReleased(exist.getAreaReleased().add(area));
//										stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
//												: stuffReq.getStuffedQty().add(qty));

										if (req != null) {
											req.setStuffedQty(req.getStuffedQty() == null ? BigDecimal.ZERO
													: req.getStuffedQty().add(qty));

											exportstuffrepo.save(req);
										}

										val = val.subtract(qty);

										existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().add(qty));

										exportcartingrepo.save(existCar);

										List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
												existCar.getCartingTransId(), existCar.getCartingLineId());

										if (!grid.isEmpty()) {
											AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
											grid.stream().forEach(g -> {
												YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid,
														bid, g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
												BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
												BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
														: g.getQtyTakenOut();
												BigDecimal gridValue = gridVal.get();
												BigDecimal areaReleased = (g.getAreaReleased() == null)
														? BigDecimal.ZERO
														: g.getAreaReleased();

												// If gridValue is greater than or equal to the remaining qty to be
												// taken out
												if (gridValue.compareTo(yardPackages.subtract(qtyTakenOut)) >= 0) {
													// Add the remaining qty to qtyTakenOut
													g.setQtyTakenOut(
															qtyTakenOut.add(yardPackages.subtract(qtyTakenOut)));

													// Calculate the area released based on the remaining qty
													BigDecimal tenArea = g.getCellAreaAllocated()
															.multiply(yardPackages.subtract(qtyTakenOut))
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update the areaReleased
													g.setAreaReleased(areaReleased.add(tenArea));

													// Subtract the quantity that was taken out from gridValue
													gridVal.set(gridValue.subtract(yardPackages.subtract(qtyTakenOut)));
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed()).subtract(tenArea));
														yardBlockCellRepository.save(yard);
													}
												} else {
													// If gridValue is less than the remaining qty to be taken out
													g.setQtyTakenOut(qtyTakenOut.add(gridValue));

													// Calculate the area released based on gridValue
													BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update the areaReleased
													g.setAreaReleased(areaReleased.add(tenArea));

													// Subtract gridValue from gridVal to update the remaining gridValue
													gridVal.set(gridValue.subtract(gridValue));
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed()).subtract(tenArea));
														yardBlockCellRepository.save(yard);
													}
												}

												impexpgridrepo.save(g);
											});
										}

										exist.setStuffMode(singleTally.getStuffMode());
										exist.setShift(singleTally.getShift());
										exist.setAgentSealNo(singleTally.getAgentSealNo());
										exist.setCustomsSealNo(singleTally.getCustomsSealNo());
										exist.setContainerStatus(singleTally.getContainerStatus());
										exist.setVesselId(singleTally.getVesselId());
										exist.setVoyageNo(singleTally.getVoyageNo());
										exist.setViaNo(singleTally.getViaNo());
										exist.setTerminal(singleTally.getTerminal());
										exist.setPod(singleTally.getPod());
										exist.setContainerCondition(singleTally.getContainerCondition());
										exist.setTareWeight(singleTally.getTareWeight());
										exist.setFinalPod(singleTally.getFinalPod());
										exist.setCha(singleTally.getCha());
										exist.setHaz(singleTally.getHaz());
										exist.setSealType(singleTally.getSealType());
										exist.setGenSetRequired(singleTally.getGenSetRequired());
										exist.setDocType(singleTally.getDocType());
										exist.setDocNo(singleTally.getDocNo());
										exist.setRotationNo(singleTally.getRotationNo());
										exist.setRotationDate(singleTally.getRotationDate());
										exist.setBerthingDate(singleTally.getBerthingDate());
										exist.setGateOpenDate(singleTally.getGateOpenDate());
										exist.setTypeOfPackage(t.getTypeOfPackage());
										exist.setEditedBy(user);
										exist.setEditedDate(new Date());
										exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
										exist.setCargoWeight(t.getCargoWeight());
										exportstufftallyrepo.save(exist);
									}
								}

							}
						}

					} else {
						exist.setStuffMode(singleTally.getStuffMode());
						exist.setShift(singleTally.getShift());
						exist.setAgentSealNo(singleTally.getAgentSealNo());
						exist.setCustomsSealNo(singleTally.getCustomsSealNo());
						exist.setContainerStatus(singleTally.getContainerStatus());
						exist.setVesselId(singleTally.getVesselId());
						exist.setVoyageNo(singleTally.getVoyageNo());
						exist.setViaNo(singleTally.getViaNo());
						exist.setTerminal(singleTally.getTerminal());
						exist.setPod(singleTally.getPod());
						exist.setContainerCondition(singleTally.getContainerCondition());
						exist.setTareWeight(singleTally.getTareWeight());
						exist.setFinalPod(singleTally.getFinalPod());
						exist.setCha(singleTally.getCha());
						exist.setHaz(singleTally.getHaz());
						exist.setSealType(singleTally.getSealType());
						exist.setGenSetRequired(singleTally.getGenSetRequired());
						exist.setDocType(singleTally.getDocType());
						exist.setDocNo(singleTally.getDocNo());
						exist.setRotationNo(singleTally.getRotationNo());
						exist.setRotationDate(singleTally.getRotationDate());
						exist.setBerthingDate(singleTally.getBerthingDate());
						exist.setGateOpenDate(singleTally.getGateOpenDate());
						exist.setTypeOfPackage(t.getTypeOfPackage());
						exist.setEditedBy(user);
						exist.setEditedDate(new Date());
						exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
						exist.setCargoWeight(t.getCargoWeight());
						exportstufftallyrepo.save(exist);
					}
					exist.setStuffMode(singleTally.getStuffMode());
					exist.setShift(singleTally.getShift());
					exist.setAgentSealNo(singleTally.getAgentSealNo());
					exist.setCustomsSealNo(singleTally.getCustomsSealNo());
					exist.setContainerStatus(singleTally.getContainerStatus());
					exist.setVesselId(singleTally.getVesselId());
					exist.setVoyageNo(singleTally.getVoyageNo());
					exist.setViaNo(singleTally.getViaNo());
					exist.setTerminal(singleTally.getTerminal());
					exist.setPod(singleTally.getPod());
					exist.setContainerCondition(singleTally.getContainerCondition());
					exist.setTareWeight(singleTally.getTareWeight());
					exist.setFinalPod(singleTally.getFinalPod());
					exist.setCha(singleTally.getCha());
					exist.setHaz(singleTally.getHaz());
					exist.setSealType(singleTally.getSealType());
					exist.setGenSetRequired(singleTally.getGenSetRequired());
					exist.setDocType(singleTally.getDocType());
					exist.setDocNo(singleTally.getDocNo());
					exist.setRotationNo(singleTally.getRotationNo());
					exist.setRotationDate(singleTally.getRotationDate());
					exist.setBerthingDate(singleTally.getBerthingDate());
					exist.setGateOpenDate(singleTally.getGateOpenDate());
					exist.setTypeOfPackage(t.getTypeOfPackage());
					exist.setEditedBy(user);
					exist.setEditedDate(new Date());
					exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
					exist.setCargoWeight(t.getCargoWeight());
					exportstufftallyrepo.save(exist);
				}

				System.out.println("totalStuffedQty.compareTo(val1) < 0 " + (totalStuffedQty.compareTo(val1) < 0));
				System.out.println("totalStuffedQty.compareTo(val1) < 0 " + val);

				if (totalStuffedQty.compareTo(val1) < 0) {
					if (val.compareTo(BigDecimal.ZERO) > 0) {
						List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTransAndLineNo(cid, bid,
								t.getSbTransId(), t.getSbNo(), t.getSbLineId());

						if (cartingData.isEmpty()) {
							return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
						}

						System.out.println("stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty() "
								+ stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty()));

						for (ExportCarting c : cartingData) {

							ExportStuffTally exist1 = exportstufftallyrepo
									.getDataByStuffTallyIdANDStuffIdAndCartingTransId2(cid, bid,
											singleTally.getStuffTallyId(), singleTally.getStuffId(),
											c.getCartingTransId(), t.getSbNo(), t.getSbTransId());

							BigDecimal remainingQty1 = c.getActualNoOfPackages().subtract(c.getStuffedNoOfPackages());

							if (stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())
									.compareTo(BigDecimal.ZERO) > 0 && val.compareTo(BigDecimal.ZERO) > 0
									&& remainingQty1.compareTo(BigDecimal.ZERO) > 0) {
								BigDecimal remainingQty = c.getActualNoOfPackages()
										.subtract(c.getStuffedNoOfPackages());

								if (val.compareTo(BigDecimal.ZERO) <= 0) {
									break;
								}

								if (exist1 != null) {

									BigDecimal qty = BigDecimal.ZERO;

									if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty1;
										} else {
											qty = remainingQty1;
										}

									} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty1;
										} else {
											qty = remainingQty1;
										}
									}

									BigDecimal area = (c.getAreaOccupied().multiply(qty)).divide(c.getYardPackages());

									exist1.setStuffedQty(exist1.getStuffedQty().add(qty));
									exist1.setBalanceQty(exist1.getBalanceQty().subtract(qty));
									exist1.setAreaReleased(exist1.getAreaReleased().add(area));

									exist1.setStuffMode(singleTally.getStuffMode());
									exist1.setShift(singleTally.getShift());
									exist1.setAgentSealNo(singleTally.getAgentSealNo());
									exist1.setCustomsSealNo(singleTally.getCustomsSealNo());
									exist1.setContainerStatus(singleTally.getContainerStatus());
									exist1.setVesselId(singleTally.getVesselId());
									exist1.setVoyageNo(singleTally.getVoyageNo());
									exist1.setViaNo(singleTally.getViaNo());
									exist1.setTerminal(singleTally.getTerminal());
									exist1.setPod(singleTally.getPod());
									exist1.setContainerCondition(singleTally.getContainerCondition());
									exist1.setTareWeight(singleTally.getTareWeight());
									exist1.setFinalPod(singleTally.getFinalPod());
									exist1.setCha(singleTally.getCha());
									exist1.setHaz(singleTally.getHaz());
									exist1.setSealType(singleTally.getSealType());
									exist1.setGenSetRequired(singleTally.getGenSetRequired());
									exist1.setDocType(singleTally.getDocType());
									exist1.setDocNo(singleTally.getDocNo());
									exist1.setRotationNo(singleTally.getRotationNo());
									exist1.setRotationDate(singleTally.getRotationDate());
									exist1.setBerthingDate(singleTally.getBerthingDate());
									exist1.setGateOpenDate(singleTally.getGateOpenDate());
									exist1.setTypeOfPackage(t.getTypeOfPackage());
									exist1.setEditedBy(user);
									exist1.setEditedDate(new Date());
									exist1.setTotalGrossWeight(singleTally.getTotalGrossWeight());
									exist1.setCargoWeight(t.getCargoWeight());

									exportstufftallyrepo.save(exist1);
									stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
											: stuffReq.getStuffedQty().add(qty));

									val = val.subtract(qty);

									c.setStuffedNoOfPackages((c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO
											: c.getStuffedNoOfPackages()).add(qty));

									exportcartingrepo.save(c);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
											c.getCartingTransId(), c.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
											BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
											BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
													: g.getQtyTakenOut();
											BigDecimal remainingQty2 = yardPackages.subtract(qtyTakenOut);
											BigDecimal gridValue = gridVal.get();
											BigDecimal areaReleased = (g.getAreaReleased() == null) ? BigDecimal.ZERO
													: g.getAreaReleased();

											// If gridValue is greater than or equal to the remaining quantity
											if (gridValue.compareTo(remainingQty2) >= 0) {
												// Update QtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(remainingQty2));

												// Calculate area released
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingQty2)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Subtract the remaining quantity from gridValue
												gridVal.set(gridValue.subtract(remainingQty2));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											} else {
												// Update QtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(gridValue));

												// Calculate area released based on gridValue
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Reset gridValue to 0 by subtracting gridValue from itself
												gridVal.set(gridValue.subtract(gridValue)); // This sets gridVal to zero
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											}

											impexpgridrepo.save(g);
										});
									}
								} else {
									BigDecimal qty = BigDecimal.ZERO;

									if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
										if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {

										if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									}

									System.out.println("qty qty " + qty);

									BigDecimal area = (c.getAreaOccupied().multiply(qty)).divide(c.getYardPackages());

									ExportStuffTally newTally = new ExportStuffTally();

									int sr = exportstufftallyrepo.countOfStuffRecords(cid, bid,
											singleTally.getStuffTallyId());

									newTally.setCompanyId(cid);
									newTally.setBranchId(bid);
									newTally.setStuffTallyId(singleTally.getStuffTallyId());
									newTally.setSbTransId(c.getSbTransId());
									newTally.setStuffTallyLineId(sr + 1);
									newTally.setProfitcentreId(c.getProfitcentreId());
									newTally.setCartingTransId(c.getCartingTransId());
									newTally.setCartingLineId(c.getCartingLineId());
									newTally.setSbLineId(c.getSbLineNo());
									newTally.setSbNo(c.getSbNo());
									newTally.setStuffTallyWoTransId(singleTally.getStuffTallyWoTransId());
									newTally.setStuffTallyCutWoTransDate(singleTally.getStuffTallyCutWoTransDate());
									newTally.setStuffTallyDate(singleTally.getStuffTallyDate());
									newTally.setStuffId(stuffReq.getStuffReqId());
									newTally.setStuffDate(stuffReq.getStuffReqDate());
									newTally.setSbDate(cargo.getSbDate());
									newTally.setShift(singleTally.getShift());
									newTally.setAgentSealNo(singleTally.getAgentSealNo());
									newTally.setVesselId(singleTally.getVesselId());
									newTally.setVoyageNo(singleTally.getVoyageNo());
									newTally.setRotationNo(singleTally.getRotationNo());
									newTally.setRotationDate(singleTally.getRotationDate());
									newTally.setPol(singleTally.getPol());
									newTally.setTerminal(singleTally.getTerminal());
									newTally.setPod(singleTally.getPod());
									newTally.setFinalPod(singleTally.getFinalPod());
									newTally.setContainerNo(singleTally.getContainerNo());
									newTally.setContainerSize(singleTally.getContainerSize());
									newTally.setPeriodFrom(singleTally.getPeriodFrom());
									newTally.setGateInId(stuffReq.getGateInId());
									newTally.setContainerStatus(singleTally.getContainerStatus());
									newTally.setContainerType(singleTally.getContainerType());
									newTally.setContainerCondition(stuffReq.getContainerHealth());
									newTally.setYardPackages(c.getYardPackages());
									newTally.setCellAreaAllocated(t.getCellAreaAllocated());
									newTally.setOnAccountOf(stuffReq.getOnAccountOf());
									newTally.setCha(singleTally.getCha());
									newTally.setTotalGrossWeight(singleTally.getTotalGrossWeight());
									newTally.setCargoWeight(t.getCargoWeight());
									newTally.setStuffRequestQty(t.getStuffRequestQty());
									newTally.setStuffedQty(qty);
									stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
											: stuffReq.getStuffedQty().add(qty));
									newTally.setBalanceQty(c.getYardPackages().subtract(qty));
									newTally.setTareWeight(singleTally.getTareWeight());
									newTally.setAreaReleased(area);
									newTally.setGenSetRequired(singleTally.getGenSetRequired());
									newTally.setHaz(singleTally.getHaz());
									newTally.setImoCode(e2.getImoCode());
									newTally.setShippingAgent(stuffReq.getShippingAgent());
									newTally.setShippingLine(stuffReq.getShippingLine());
									newTally.setCommodity(t.getCommodity());
									newTally.setCustomsSealNo(singleTally.getCustomsSealNo());
									newTally.setViaNo(singleTally.getViaNo());
									newTally.setCartingDate(c.getCartingTransDate());
									newTally.setExporterName(e2.getExporterName());
									newTally.setConsignee(t.getConsignee());
									newTally.setFob(t.getFob());
									newTally.setBerthingDate(singleTally.getBerthingDate());
									newTally.setGateOpenDate(singleTally.getGateOpenDate());
									newTally.setDocType(singleTally.getDocType());
									newTally.setDocNo(singleTally.getDocNo());
									newTally.setStatus("A");
									newTally.setCreatedBy(user);
									newTally.setCreatedDate(new Date());
									newTally.setApprovedBy(user);
									newTally.setApprovedDate(new Date());
									newTally.setDeliveryOrderNo(singleTally.getDeliveryOrderNo());
									newTally.setStuffMode(singleTally.getStuffMode());
									newTally.setStuffLineId(stuffReq.getStuffReqLineId());
									newTally.setTypeOfPackage(t.getTypeOfPackage());
									newTally.setSealType(singleTally.getSealType());
									newTally.setTotalCargoWeight(t.getTotalCargoWeight());

									exportstufftallyrepo.save(newTally);

									c.setStuffedNoOfPackages((c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO
											: c.getStuffedNoOfPackages()).add(qty));

									exportcartingrepo.save(c);

									val = val.subtract(qty);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid,
											c.getCartingTransId(), c.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
											BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
											BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
													: g.getQtyTakenOut();
											BigDecimal remainingQty2 = yardPackages.subtract(qtyTakenOut);
											BigDecimal gridValue = gridVal.get();
											BigDecimal areaReleased = (g.getAreaReleased() == null) ? BigDecimal.ZERO
													: g.getAreaReleased();

											// If gridValue is greater than or equal to the remaining quantity
											if (gridValue.compareTo(remainingQty2) >= 0) {
												// Update QtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(remainingQty2));

												// Calculate area released
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingQty2)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Subtract the remaining quantity from gridValue
												gridVal.set(gridValue.subtract(remainingQty2));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											} else {
												// Update QtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(gridValue));

												// Calculate area released based on gridValue
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Reset gridValue to 0 by subtracting gridValue from itself
												gridVal.set(gridValue.subtract(gridValue)); // This sets gridVal to zero
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											}
											impexpgridrepo.save(g);
										});
									}
								}

							}

						}

					}
				}

				stuffReq.setAgentSealNo(singleTally.getAgentSealNo());
				stuffReq.setTareWeight(singleTally.getTareWeight());
				stuffReq.setRotationNo(singleTally.getRotationNo());
				stuffReq.setRotationDate(singleTally.getRotationDate());
				stuffReq.setBerthingDate(singleTally.getBerthingDate());
				stuffReq.setGateOpenDate(singleTally.getGateOpenDate());
				stuffReq.setTypeOfPackage(t.getTypeOfPackage());
				stuffReq.setVesselId(singleTally.getVesselId());
				stuffReq.setVesselName(singleTally.getVesselName());
				stuffReq.setVoyageNo(singleTally.getVoyageNo());
				stuffReq.setViaNo(singleTally.getViaNo());
				stuffReq.setEditedBy(user);
				stuffReq.setEditedDate(new Date());

				exportstuffrepo.save(stuffReq);

				if (val.compareTo(BigDecimal.ZERO) > 0) {

					if ((cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty())))
							.compareTo(BigDecimal.ZERO) > 0) {
						List<ExportCarting> cartingData1 = exportcartingrepo.getDataBySbNoSbTrans1(cid, bid,
								t.getSbTransId(), t.getSbNo());

						if (cartingData1.isEmpty()) {
							return new ResponseEntity<>("Carting data not found1", HttpStatus.CONFLICT);
						}

						List<ExportCarting> cartingData2 = new ArrayList<>();

						for (ExportCarting c : cartingData1) {

							if (val.compareTo(BigDecimal.ZERO) > 0
									&& (cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty())))
											.compareTo(BigDecimal.ZERO) > 0) {
								ExportStuffTally exist1 = exportstufftallyrepo
										.getDataByStuffTallyIdANDStuffIdAndCartingTransId2(cid, bid,
												singleTally.getStuffTallyId(), singleTally.getStuffId(),
												c.getCartingTransId(), t.getSbNo(), t.getSbTransId());

								BigDecimal remainingQty1 = c.getActualNoOfPackages()
										.subtract(c.getStuffedNoOfPackages());

								if (exist1 != null && remainingQty1.compareTo(BigDecimal.ZERO) > 0
										&& val.compareTo(BigDecimal.ZERO) > 0) {
									BigDecimal qty = BigDecimal.ZERO;

									if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty()))
												.compareTo(remainingQty1) > 0) {
											qty = remainingQty1;
										} else if (cargo.getCartedPackages()
												.subtract(new BigDecimal(cargo.getStuffReqQty()))
												.compareTo(remainingQty1) < 0) {
											qty = cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()));
										} else {
											qty = remainingQty1;
										}

									} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty()))
												.compareTo(val) > 0) {
											qty = val;
										} else if (cargo.getCartedPackages()
												.subtract(new BigDecimal(cargo.getStuffReqQty())).compareTo(val) < 0) {
											qty = cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()));
										} else {
											qty = val;
										}

									} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) < 0) {

										if (cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty()))
												.compareTo(remainingQty1) > 0) {
											qty = remainingQty1;
										} else if (cargo.getCartedPackages()
												.subtract(new BigDecimal(cargo.getStuffReqQty()))
												.compareTo(remainingQty1) < 0) {
											qty = cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()));
										} else {
											qty = remainingQty1;
										}

									}

									BigDecimal area = (c.getAreaOccupied().multiply(qty)).divide(c.getYardPackages());

									exist1.setStuffedQty(exist1.getStuffedQty().add(qty));
									exist1.setBalanceQty(exist1.getBalanceQty().subtract(qty));
									exist1.setAreaReleased(exist1.getAreaReleased().add(area));

									exist1.setStuffMode(singleTally.getStuffMode());
									exist1.setShift(singleTally.getShift());
									exist1.setAgentSealNo(singleTally.getAgentSealNo());
									exist1.setCustomsSealNo(singleTally.getCustomsSealNo());
									exist1.setContainerStatus(singleTally.getContainerStatus());
									exist1.setVesselId(singleTally.getVesselId());
									exist1.setVoyageNo(singleTally.getVoyageNo());
									exist1.setViaNo(singleTally.getViaNo());
									exist1.setTerminal(singleTally.getTerminal());
									exist1.setPod(singleTally.getPod());
									exist1.setContainerCondition(singleTally.getContainerCondition());
									exist1.setTareWeight(singleTally.getTareWeight());
									exist1.setFinalPod(singleTally.getFinalPod());
									exist1.setCha(singleTally.getCha());
									exist1.setHaz(singleTally.getHaz());
									exist1.setSealType(singleTally.getSealType());
									exist1.setGenSetRequired(singleTally.getGenSetRequired());
									exist1.setDocType(singleTally.getDocType());
									exist1.setDocNo(singleTally.getDocNo());
									exist1.setRotationNo(singleTally.getRotationNo());
									exist1.setRotationDate(singleTally.getRotationDate());
									exist1.setBerthingDate(singleTally.getBerthingDate());
									exist1.setGateOpenDate(singleTally.getGateOpenDate());
									exist1.setTypeOfPackage(t.getTypeOfPackage());
									exist1.setEditedBy(user);
									exist1.setEditedDate(new Date());
									exist1.setTotalGrossWeight(singleTally.getTotalGrossWeight());
									exist1.setCargoWeight(t.getCargoWeight());

									exportstufftallyrepo.save(exist1);

									stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
											: stuffReq.getStuffedQty().add(qty));
									stuffReq.setNoOfPackagesStuffed(stuffReq.getNoOfPackagesStuffed().add(qty));

									cargo.setStuffReqQty(cargo.getStuffReqQty() + Integer.parseInt(qty.toString()));

									val = val.subtract(qty);

									c.setStuffedNoOfPackages(c.getStuffedNoOfPackages().add(qty));

									exportcartingrepo.save(c);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
											c.getCartingTransId(), c.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
											BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
											BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
													: g.getQtyTakenOut();
											BigDecimal remainingQty = yardPackages.subtract(qtyTakenOut);
											BigDecimal gridValue = gridVal.get();
											BigDecimal areaReleased = (g.getAreaReleased() == null) ? BigDecimal.ZERO
													: g.getAreaReleased();

											// If gridValue is greater than or equal to the remaining quantity
											if (gridValue.compareTo(remainingQty) >= 0) {
												// Update QtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(remainingQty));

												// Calculate area released
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingQty)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Subtract the remaining quantity from gridValue
												gridVal.set(gridValue.subtract(remainingQty));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											} else {
												// Update QtyTakenOut with gridValue
												g.setQtyTakenOut(qtyTakenOut.add(gridValue));

												// Calculate area released based on gridValue
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Reset gridValue to 0 by subtracting it from itself
												gridVal.set(gridValue.subtract(gridValue)); // This sets gridVal to zero
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											}

											impexpgridrepo.save(g);
										});
									}
								} else {
									cartingData2.add(c);
								}
							}

						}

						if (val.compareTo(BigDecimal.ZERO) > 0 && cartingData2.size() > 0) {

							for (ExportCarting c : cartingData2) {
								if (val.compareTo(BigDecimal.ZERO) > 0
										&& (cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty())))
												.compareTo(BigDecimal.ZERO) > 0) {
									BigDecimal remainingQty1 = c.getActualNoOfPackages()
											.subtract(c.getStuffedNoOfPackages());

									if (remainingQty1.compareTo(BigDecimal.ZERO) > 0
											&& val.compareTo(BigDecimal.ZERO) > 0) {
										BigDecimal qty = BigDecimal.ZERO;

										if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

											if (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()))
													.compareTo(remainingQty1) > 0) {
												qty = remainingQty1;
											} else if (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()))
													.compareTo(remainingQty1) < 0) {
												qty = cargo.getCartedPackages()
														.subtract(new BigDecimal(cargo.getStuffReqQty()));
											} else {
												qty = remainingQty1;
											}

										} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

											if (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()))
													.compareTo(val) > 0) {
												qty = val;
											} else if (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()))
													.compareTo(val) < 0) {
												qty = cargo.getCartedPackages()
														.subtract(new BigDecimal(cargo.getStuffReqQty()));
											} else {
												qty = val;
											}

										} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) < 0) {

											if (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()))
													.compareTo(remainingQty1) > 0) {
												qty = remainingQty1;
											} else if (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty()))
													.compareTo(remainingQty1) < 0) {
												qty = cargo.getCartedPackages()
														.subtract(new BigDecimal(cargo.getStuffReqQty()));
											} else {
												qty = remainingQty1;
											}

										}

										BigDecimal area = (c.getAreaOccupied().multiply(qty))
												.divide(c.getYardPackages());

										ExportStuffTally newTally = new ExportStuffTally();

										int sr = exportstufftallyrepo.countOfStuffRecords(cid, bid,
												singleTally.getStuffTallyId());

										newTally.setCompanyId(cid);
										newTally.setBranchId(bid);
										newTally.setStuffTallyId(singleTally.getStuffTallyId());
										newTally.setSbTransId(c.getSbTransId());
										newTally.setStuffTallyLineId(sr + 1);
										newTally.setProfitcentreId(c.getProfitcentreId());
										newTally.setCartingTransId(c.getCartingTransId());
										newTally.setCartingLineId(c.getCartingLineId());
										newTally.setSbLineId(c.getSbLineNo());
										newTally.setSbNo(c.getSbNo());
										newTally.setStuffTallyWoTransId(singleTally.getStuffTallyWoTransId());
										newTally.setStuffTallyCutWoTransDate(singleTally.getStuffTallyCutWoTransDate());
										newTally.setStuffTallyDate(singleTally.getStuffTallyDate());
										newTally.setStuffId(stuffReq.getStuffReqId());
										newTally.setStuffDate(stuffReq.getStuffReqDate());
										newTally.setSbDate(cargo.getSbDate());
										newTally.setShift(singleTally.getShift());
										newTally.setAgentSealNo(singleTally.getAgentSealNo());
										newTally.setVesselId(singleTally.getVesselId());
										newTally.setVoyageNo(singleTally.getVoyageNo());
										newTally.setRotationNo(singleTally.getRotationNo());
										newTally.setRotationDate(singleTally.getRotationDate());
										newTally.setPol(singleTally.getPol());
										newTally.setTerminal(singleTally.getTerminal());
										newTally.setPod(singleTally.getPod());
										newTally.setFinalPod(singleTally.getFinalPod());
										newTally.setContainerNo(singleTally.getContainerNo());
										newTally.setContainerSize(singleTally.getContainerSize());
										newTally.setPeriodFrom(singleTally.getPeriodFrom());
										newTally.setGateInId(stuffReq.getGateInId());
										newTally.setContainerStatus(singleTally.getContainerStatus());
										newTally.setContainerType(singleTally.getContainerType());
										newTally.setContainerCondition(stuffReq.getContainerHealth());
										newTally.setYardPackages(c.getYardPackages());
										newTally.setCellAreaAllocated(t.getCellAreaAllocated());
										newTally.setOnAccountOf(stuffReq.getOnAccountOf());
										newTally.setCha(singleTally.getCha());
										newTally.setTotalGrossWeight(singleTally.getTotalGrossWeight());
										newTally.setCargoWeight(t.getCargoWeight());
										newTally.setStuffRequestQty(t.getStuffRequestQty());
										newTally.setStuffedQty(qty);

										newTally.setBalanceQty(c.getYardPackages().subtract(qty));
										newTally.setTareWeight(singleTally.getTareWeight());
										newTally.setAreaReleased(area);
										newTally.setGenSetRequired(singleTally.getGenSetRequired());
										newTally.setHaz(singleTally.getHaz());
										newTally.setImoCode(e2.getImoCode());
										newTally.setShippingAgent(stuffReq.getShippingAgent());
										newTally.setShippingLine(stuffReq.getShippingLine());
										newTally.setCommodity(t.getCommodity());
										newTally.setCustomsSealNo(singleTally.getCustomsSealNo());
										newTally.setViaNo(singleTally.getViaNo());
										newTally.setCartingDate(c.getCartingTransDate());
										newTally.setExporterName(e2.getExporterName());
										newTally.setConsignee(t.getConsignee());
										newTally.setFob(t.getFob());
										newTally.setBerthingDate(singleTally.getBerthingDate());
										newTally.setGateOpenDate(singleTally.getGateOpenDate());
										newTally.setDocType(singleTally.getDocType());
										newTally.setDocNo(singleTally.getDocNo());
										newTally.setStatus("A");
										newTally.setCreatedBy(user);
										newTally.setCreatedDate(new Date());
										newTally.setApprovedBy(user);
										newTally.setApprovedDate(new Date());
										newTally.setDeliveryOrderNo(singleTally.getDeliveryOrderNo());
										newTally.setStuffMode(singleTally.getStuffMode());
										newTally.setStuffLineId(stuffReq.getStuffReqLineId());
										newTally.setTypeOfPackage(t.getTypeOfPackage());
										newTally.setSealType(singleTally.getSealType());
										newTally.setTotalCargoWeight(t.getTotalCargoWeight());
										exportstufftallyrepo.save(newTally);

										stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
												: stuffReq.getStuffedQty().add(qty));
										stuffReq.setNoOfPackagesStuffed(stuffReq.getNoOfPackagesStuffed().add(qty));

										cargo.setStuffReqQty(cargo.getStuffReqQty() + Integer.parseInt(qty.toString()));

										c.setStuffedNoOfPackages(c.getStuffedNoOfPackages().add(qty));

										exportcartingrepo.save(c);

										val = val.subtract(qty);

										List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid,
												c.getCartingTransId(), c.getCartingLineId());

										if (!grid.isEmpty()) {
											AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
											grid.stream().forEach(g -> {
												YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid,
														bid, g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
												// Convert values to BigDecimal once to avoid recalculating
												BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
												BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null) ? BigDecimal.ZERO
														: g.getQtyTakenOut();
												BigDecimal remainingQty = yardPackages.subtract(qtyTakenOut);
												BigDecimal gridValue = gridVal.get();
												BigDecimal areaReleased = (g.getAreaReleased() == null)
														? BigDecimal.ZERO
														: g.getAreaReleased();

												// If gridValue is greater than or equal to remainingQty
												if (gridValue.compareTo(remainingQty) >= 0) {
													// Update QtyTakenOut
													g.setQtyTakenOut(qtyTakenOut.add(remainingQty));

													// Calculate area released based on remaining quantity
													BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingQty)
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update areaReleased
													g.setAreaReleased(areaReleased.add(tenArea));

													// Subtract remainingQty from gridValue
													gridVal.set(gridValue.subtract(remainingQty));
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed()).subtract(tenArea));
														yardBlockCellRepository.save(yard);
													}
												} else {
													// Update QtyTakenOut with gridValue
													g.setQtyTakenOut(qtyTakenOut.add(gridValue));

													// Calculate area released based on gridValue
													BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update areaReleased
													g.setAreaReleased(areaReleased.add(tenArea));

													// Reset gridValue to zero
													gridVal.set(BigDecimal.ZERO);
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed()).subtract(tenArea));
														yardBlockCellRepository.save(yard);
													}
												}

												impexpgridrepo.save(g);
											});
										}

									}
								}

							}

						}

					}

				}

				System.out.println("val " + val);
				if (stuffReq.getNoOfPackagesStuffed().compareTo(t.getStuffedQty()) < 0
						&& val.compareTo(BigDecimal.ZERO) > 0) {

					BigDecimal incrementVal = t.getStuffedQty().subtract(stuffReq.getNoOfPackagesStuffed());

					List<ExportStuffRequest> remainingStuffReq = exportstuffrepo.getDataBySbNoSbTrans(cid, bid,
							t.getSbTransId(), t.getSbNo(), singleTally.getStuffId());

					if (!remainingStuffReq.isEmpty()) {
						for (ExportStuffRequest e : remainingStuffReq) {
							if (incrementVal.compareTo(BigDecimal.ZERO) != 0) {

//								if ((e.getNoOfPackagesStuffed().subtract(e.getStuffedQty()))
//										.compareTo(incrementVal) >= 0) {
//									reqVal = incrementVal;
//
//									incrementVal = BigDecimal.ZERO;
//								} else {
//									reqVal = (e.getNoOfPackagesStuffed().subtract(e.getStuffedQty()));
//
//									incrementVal = incrementVal
//											.subtract((e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())));
//								}

								List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTransAndLineNo(cid,
										bid, t.getSbTransId(), t.getSbNo(), t.getSbLineId());

								if (cartingData.isEmpty()) {
									return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
								}

								for (ExportCarting c : cartingData) {

//									ExportStuffTally exist = exportstufftallyrepo.getDataByStuffTallyIdANDStuffIdAndCartingTransId(cid, bid,
//											singleTally.getStuffTallyId(), singleTally.getStuffId(), c.getCartingTransId());
//									
//									if(exist != null) {
//										
//									}
//									else {
//										
//									}

									BigDecimal remainingQty = c.getActualNoOfPackages()
											.subtract(c.getStuffedNoOfPackages());

									System.out.println("reqVal " + val + " " + remainingQty);
									if (val.compareTo(BigDecimal.ZERO) <= 0) {
										break;
									}

									BigDecimal qty = BigDecimal.ZERO;

									if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
										if (remainingQty.compareTo(
												e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) > 0) {
											qty = e.getNoOfPackagesStuffed().subtract(e.getStuffedQty());
										} else if (remainingQty.compareTo(
												e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
										if (val.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) > 0) {
											qty = e.getNoOfPackagesStuffed().subtract(e.getStuffedQty());
										} else if (val.compareTo(
												e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) < 0) {
											qty = val;
										} else {
											qty = val;
										}
									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty.compareTo(
												e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) > 0) {
											qty = e.getNoOfPackagesStuffed().subtract(e.getStuffedQty());
										} else if (remainingQty.compareTo(
												e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}
									}

									System.out.println("qty qty " + qty);
									BigDecimal area = (c.getAreaOccupied().multiply(qty)).divide(c.getYardPackages());

									ExportStuffTally newTally = new ExportStuffTally();

									int sr = exportstufftallyrepo.countOfStuffRecords(cid, bid,
											singleTally.getStuffTallyId());

									newTally.setCompanyId(cid);
									newTally.setBranchId(bid);
									newTally.setStuffTallyId(singleTally.getStuffTallyId());
									newTally.setSbTransId(c.getSbTransId());
									newTally.setStuffTallyLineId(sr + 1);
									newTally.setProfitcentreId(c.getProfitcentreId());
									newTally.setCartingTransId(c.getCartingTransId());
									newTally.setCartingLineId(c.getCartingLineId());
									newTally.setSbLineId(c.getSbLineNo());
									newTally.setSbNo(c.getSbNo());
									newTally.setStuffTallyWoTransId(singleTally.getStuffTallyWoTransId());
									newTally.setStuffTallyCutWoTransDate(singleTally.getStuffTallyCutWoTransDate());
									newTally.setStuffTallyDate(singleTally.getStuffTallyDate());
									newTally.setStuffId(e.getStuffReqId());
									newTally.setStuffDate(e.getStuffReqDate());
									newTally.setSbDate(cargo.getSbDate());
									newTally.setShift(singleTally.getShift());
									newTally.setAgentSealNo(singleTally.getAgentSealNo());
									newTally.setVesselId(singleTally.getVesselId());
									newTally.setVoyageNo(singleTally.getVoyageNo());
									newTally.setRotationNo(singleTally.getRotationNo());
									newTally.setRotationDate(singleTally.getRotationDate());
									newTally.setPol(singleTally.getPol());
									newTally.setTerminal(singleTally.getTerminal());
									newTally.setPod(singleTally.getPod());
									newTally.setFinalPod(singleTally.getFinalPod());
									newTally.setContainerNo(singleTally.getContainerNo());
									newTally.setContainerSize(singleTally.getContainerSize());
									newTally.setPeriodFrom(singleTally.getPeriodFrom());
									newTally.setGateInId(stuffReq.getGateInId());
									newTally.setContainerStatus(singleTally.getContainerStatus());
									newTally.setContainerType(singleTally.getContainerType());
									newTally.setContainerCondition(stuffReq.getContainerHealth());
									newTally.setYardPackages(c.getYardPackages());
									newTally.setCellAreaAllocated(t.getCellAreaAllocated());
									newTally.setOnAccountOf(stuffReq.getOnAccountOf());
									newTally.setCha(singleTally.getCha());
									newTally.setTotalGrossWeight(singleTally.getTotalGrossWeight());
									newTally.setCargoWeight(t.getCargoWeight());
									newTally.setStuffRequestQty(e.getNoOfPackagesStuffed());
									newTally.setStuffedQty(qty);
									e.setStuffedQty(
											e.getStuffedQty() == null ? BigDecimal.ZERO : e.getStuffedQty().add(qty));
									newTally.setBalanceQty(c.getYardPackages().subtract(qty));
									newTally.setTareWeight(singleTally.getTareWeight());
									newTally.setAreaReleased(area);
									newTally.setGenSetRequired(singleTally.getGenSetRequired());
									newTally.setHaz(singleTally.getHaz());
									newTally.setImoCode(e2.getImoCode());
									newTally.setShippingAgent(stuffReq.getShippingAgent());
									newTally.setShippingLine(stuffReq.getShippingLine());
									newTally.setCommodity(t.getCommodity());
									newTally.setCustomsSealNo(singleTally.getCustomsSealNo());
									newTally.setViaNo(singleTally.getViaNo());
									newTally.setCartingDate(c.getCartingTransDate());
									newTally.setExporterName(e2.getExporterName());
									newTally.setConsignee(t.getConsignee());
									newTally.setFob(t.getFob());
									newTally.setBerthingDate(singleTally.getBerthingDate());
									newTally.setGateOpenDate(singleTally.getGateOpenDate());
									newTally.setDocType(singleTally.getDocType());
									newTally.setDocNo(singleTally.getDocNo());
									newTally.setStatus("A");
									newTally.setCreatedBy(user);
									newTally.setCreatedDate(new Date());
									newTally.setApprovedBy(user);
									newTally.setApprovedDate(new Date());
									newTally.setDeliveryOrderNo(singleTally.getDeliveryOrderNo());
									newTally.setStuffMode(singleTally.getStuffMode());
									newTally.setStuffLineId(stuffReq.getStuffReqLineId());
									newTally.setTypeOfPackage(t.getTypeOfPackage());
									newTally.setSealType(singleTally.getSealType());
									newTally.setTotalCargoWeight(t.getTotalCargoWeight());

									exportstufftallyrepo.save(newTally);

									exportstuffrepo.save(e);

									c.setStuffedNoOfPackages((c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO
											: c.getStuffedNoOfPackages()).add(qty));

									exportcartingrepo.save(c);

									val = val.subtract(qty);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid,
											c.getCartingTransId(), c.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
											// Calculate yardPackages and qtyTakenOut just once
											BigDecimal yardPackages = new BigDecimal(g.getYardPackages());
											BigDecimal qtyTakenOut = (g.getQtyTakenOut() == null ? BigDecimal.ZERO
													: g.getQtyTakenOut());
											BigDecimal remainingQty2 = yardPackages.subtract(qtyTakenOut);
											BigDecimal gridValue = gridVal.get();
											BigDecimal areaReleased = (g.getAreaReleased() == null ? BigDecimal.ZERO
													: g.getAreaReleased());

											// Check if gridValue is greater than or equal to remainingQty
											if (gridValue.compareTo(remainingQty2) >= 0) {
												// Set QtyTakenOut to qtyTakenOut + remainingQty
												g.setQtyTakenOut(qtyTakenOut.add(remainingQty2));

												// Calculate the area to be released
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingQty2)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Subtract remainingQty from gridValue
												gridVal.set(gridValue.subtract(remainingQty2));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											} else {
												// Set QtyTakenOut to qtyTakenOut + gridValue
												g.setQtyTakenOut(qtyTakenOut.add(gridValue));

												// Calculate the area to be released
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update areaReleased
												g.setAreaReleased(areaReleased.add(tenArea));

												// Reset gridVal to zero
												gridVal.set(BigDecimal.ZERO);
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed()).subtract(tenArea));
													yardBlockCellRepository.save(yard);
												}
											}

											impexpgridrepo.save(g);
										});
									}
								}

							}

						}
					}

				} else {
					for (ExportStuffTally exist : existingData1) {

						exist.setStuffMode(singleTally.getStuffMode());
						exist.setShift(singleTally.getShift());
						exist.setAgentSealNo(singleTally.getAgentSealNo());
						exist.setCustomsSealNo(singleTally.getCustomsSealNo());
						exist.setContainerStatus(singleTally.getContainerStatus());
						exist.setVesselId(singleTally.getVesselId());
						exist.setVoyageNo(singleTally.getVoyageNo());
						exist.setViaNo(singleTally.getViaNo());
						exist.setTerminal(singleTally.getTerminal());
						exist.setPod(singleTally.getPod());
						exist.setContainerCondition(singleTally.getContainerCondition());
						exist.setTareWeight(singleTally.getTareWeight());
						exist.setFinalPod(singleTally.getFinalPod());
						exist.setCha(singleTally.getCha());
						exist.setHaz(singleTally.getHaz());
						exist.setSealType(singleTally.getSealType());
						exist.setGenSetRequired(singleTally.getGenSetRequired());
						exist.setDocType(singleTally.getDocType());
						exist.setDocNo(singleTally.getDocNo());
						exist.setRotationNo(singleTally.getRotationNo());
						exist.setRotationDate(singleTally.getRotationDate());
						exist.setBerthingDate(singleTally.getBerthingDate());
						exist.setGateOpenDate(singleTally.getGateOpenDate());
						exist.setTypeOfPackage(t.getTypeOfPackage());
						exist.setEditedBy(user);
						exist.setEditedDate(new Date());
						exist.setTotalGrossWeight(singleTally.getTotalGrossWeight());
						exist.setCargoWeight(t.getCargoWeight());
						exportstufftallyrepo.save(exist);
					}
				}

				cargo.setStuffedQty(((cargo.getStuffedQty() == null ? BigDecimal.ZERO : cargo.getStuffedQty())
						.add(t.getStuffedQty())).subtract(existStuffQty));
				cargo.setStuffedWt(((cargo.getStuffedWt() == null ? BigDecimal.ZERO : cargo.getStuffedWt())
						.add(t.getCargoWeight())).subtract(existCargoWt));
				exportsbcargorepo.save(cargo);

				System.out.println("stuffReq1.getNoOfPackagesStuffed() " + stuffReq1.getNoOfPackagesStuffed() + " "
						+ t.getStuffedQty());

				if (stuffReq1.getNoOfPackagesStuffed().compareTo(t.getStuffedQty()) != 0) {
					int updateQty = exportstufftallyrepo.updateStuffReqQuantity(cid, bid, singleTally.getStuffTallyId(),
							singleTally.getStuffId(), stuffReq.getNoOfPackagesStuffed(), t.getSbTransId(), t.getSbNo());

				}

				if (!singleTally.getStuffMode().equals(e3.getStuffMode())) {
					checkAndUpdateAudit(cid, bid, user, "Stuff Mode", e3.getStuffMode(), singleTally.getStuffMode(),
							e3);

				}

				if (!singleTally.getShift().equals(e3.getShift())) {
					checkAndUpdateAudit(cid, bid, user, "Shift", e3.getShift(), singleTally.getShift(), e3);

				}

				if (!singleTally.getAgentSealNo().equals(e3.getAgentSealNo())) {
					checkAndUpdateAudit(cid, bid, user, "Agent Seal No", e3.getAgentSealNo(),
							singleTally.getAgentSealNo(), e3);

				}

				if (!singleTally.getCustomsSealNo().equals(e3.getCustomsSealNo())) {
					checkAndUpdateAudit(cid, bid, user, "Customs Seal No", e3.getCustomsSealNo(),
							singleTally.getCustomsSealNo(), e3);

				}

				if (!singleTally.getContainerStatus().equals(e3.getContainerStatus())) {
					checkAndUpdateAudit(cid, bid, user, "Container Status", e3.getContainerStatus(),
							singleTally.getContainerStatus(), e3);

				}

				if (!singleTally.getVesselId().equals(e3.getVesselId())) {
					checkAndUpdateAudit(cid, bid, user, "Vessel Name", e3.getVesselId(), singleTally.getVesselId(), e3);

				}

				if (!singleTally.getVoyageNo().equals(e3.getVoyageNo())) {
					checkAndUpdateAudit(cid, bid, user, "Voyage No", e3.getVoyageNo(), singleTally.getVoyageNo(), e3);

				}

				if (!singleTally.getViaNo().equals(e3.getViaNo())) {
					checkAndUpdateAudit(cid, bid, user, "Via No", e3.getViaNo(), singleTally.getViaNo(), e3);

				}

				if (!singleTally.getTerminal().equals(e3.getTerminal())) {
					checkAndUpdateAudit(cid, bid, user, "Via No", e3.getTerminal(), singleTally.getTerminal(), e3);

				}

				if (!singleTally.getTerminal().equals(e3.getTerminal())) {
					checkAndUpdateAudit(cid, bid, user, "Terminal Name", e3.getTerminal(), singleTally.getTerminal(),
							e3);

				}

				if (!singleTally.getPod().equals(e3.getPod())) {
					checkAndUpdateAudit(cid, bid, user, "Port Of Discharge", e3.getPod(), singleTally.getPod(), e3);

				}

				if (!singleTally.getContainerCondition().equals(e3.getContainerCondition())) {
					checkAndUpdateAudit(cid, bid, user, "Container Health", e3.getContainerCondition(),
							singleTally.getContainerCondition(), e3);

				}

				if (singleTally.getTareWeight().compareTo(e3.getTareWeight()) != 0) {
					checkAndUpdateAudit(cid, bid, user, "Container Tare Wt", e3.getTareWeight().toString(),
							singleTally.getTareWeight().toString(), e3);

				}

				if (!singleTally.getFinalPod().equals(e3.getFinalPod())) {
					checkAndUpdateAudit(cid, bid, user, "Final POD", e3.getFinalPod(), singleTally.getFinalPod(), e3);

				}

				if (!singleTally.getCha().equals(e3.getCha())) {
					checkAndUpdateAudit(cid, bid, user, "CHA", e3.getCha(), singleTally.getCha(), e3);

				}

				if (!singleTally.getHaz().equals(e3.getHaz())) {
					checkAndUpdateAudit(cid, bid, user, "Hazardous", e3.getHaz(), singleTally.getHaz(), e3);

				}

				if (!singleTally.getSealType().equals(e3.getSealType())) {
					checkAndUpdateAudit(cid, bid, user, "Seal Type", e3.getSealType(), singleTally.getSealType(), e3);

				}

				if (!singleTally.getGenSetRequired().equals(e3.getGenSetRequired())) {
					checkAndUpdateAudit(cid, bid, user, "Gen Set Required", e3.getGenSetRequired(),
							singleTally.getGenSetRequired(), e3);

				}

				if (!singleTally.getDocType().equals(e3.getDocType())) {
					checkAndUpdateAudit(cid, bid, user, "Doc Type", e3.getDocType(), singleTally.getDocType(), e3);

				}

				if (!singleTally.getDocNo().equals(e3.getDocNo())) {
					checkAndUpdateAudit(cid, bid, user, "Doc Number", e3.getDocNo(), singleTally.getDocNo(), e3);

				}
				System.out.println("rotation " + singleTally.getRotationNo() + " " + e3.getRotationNo());

				if (!singleTally.getRotationNo().equals(e3.getRotationNo())) {
					System.out.println("rotation " + singleTally.getRotationNo() + " " + e3.getRotationNo());
					checkAndUpdateAudit(cid, bid, user, "Rotation No", e3.getRotationNo(), singleTally.getRotationNo(),
							e3);

				}

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

				if ((singleTally.getRotationDate() != null
						&& !singleTally.getRotationDate().equals(e3.getRotationDate()))
						|| (singleTally.getRotationDate() == null && e3.getRotationDate() != null)) {
					String oldRotationDate = e3.getRotationDate() != null ? dateFormat.format(e3.getRotationDate())
							: null;
					String newRotationDate = singleTally.getRotationDate() != null
							? dateFormat.format(singleTally.getRotationDate())
							: null;

					checkAndUpdateAudit(cid, bid, user, "Rotation Date", oldRotationDate, newRotationDate, e3);
				}

				if ((singleTally.getBerthingDate() != null
						&& !singleTally.getBerthingDate().equals(e3.getBerthingDate()))
						|| (singleTally.getBerthingDate() == null && e3.getBerthingDate() != null)) {
					String oldBerthingDate = e3.getBerthingDate() != null ? dateFormat.format(e3.getBerthingDate())
							: null;
					String newBerthingDate = singleTally.getBerthingDate() != null
							? dateFormat.format(singleTally.getBerthingDate())
							: null;

					checkAndUpdateAudit(cid, bid, user, "Berthing Date", oldBerthingDate, newBerthingDate, e3);
				}

				if ((singleTally.getGateOpenDate() != null
						&& !singleTally.getGateOpenDate().equals(e3.getGateOpenDate()))
						|| (singleTally.getGateOpenDate() == null && e3.getGateOpenDate() != null)) {
					String oldGateOpenDate = e3.getGateOpenDate() != null ? dateFormat.format(e3.getGateOpenDate())
							: null;
					String newGateOpenDate = singleTally.getGateOpenDate() != null
							? dateFormat.format(singleTally.getGateOpenDate())
							: null;

					checkAndUpdateAudit(cid, bid, user, "Gate Open Date", oldGateOpenDate, newGateOpenDate, e3);
				}

				if (!t.getTypeOfPackage().equals(existtype)) {

					checkAndUpdateAudit(cid, bid, user, "Type Of Package", existtype, t.getTypeOfPackage(), e3);

				}

				if (t.getCargoWeight().compareTo(existCargoWt) != 0) {
					checkAndUpdateAudit(cid, bid, user, "Gross Wt", existCargoWt.toString(),
							t.getCargoWeight().toString(), e3);

				}

				if (t.getAreaReleased().compareTo(existAreaRelease) != 0) {
					checkAndUpdateAudit(cid, bid, user, "Area Released", existAreaRelease.toString(),
							t.getAreaReleased().toString(), e3);

				}
				if (t.getStuffedQty().compareTo(existStuffQty) != 0) {
					checkAndUpdateAudit(cid, bid, user, "Stuffed Qty", existStuffQty.toString(),
							t.getStuffedQty().toString(), e3);

				}

			}

			List<ExportStuffTally> result = exportstufftallyrepo.getDataByTallyId(cid, bid,
					singleTally.getStuffTallyId());

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}

	public void checkAndUpdateAudit(String cid, String bid, String user, String field, String oldValue, String newValue,
			ExportStuffTally existingData) {

		// Generate next ID for the audit record
		String holdId = processnextidrepo.findAuditTrail(cid, bid, "P05085", "2024");
		int lastNumericId = Integer.parseInt(holdId.substring(4));
		int nextNumericId = lastNumericId + 1;
		String nextAuditId = String.format("EXPA%06d", nextNumericId);

		// Create new audit record
		ExportAudit audit = new ExportAudit();
		audit.setCompanyId(cid);
		audit.setBranchId(bid);
		audit.setApprovedBy(user);
		audit.setApprovedDate(new Date());
		audit.setAuditDate(new Date());
		audit.setAuditId(nextAuditId);
		audit.setContainerNo(existingData.getContainerNo());
		audit.setCreatedBy(user);
		audit.setCreatedDate(new Date());
		audit.setField(field);
		audit.setProfitcentreId(existingData.getProfitcentreId());
		audit.setSbNo(existingData.getSbNo());
		audit.setStatus("A");
		audit.setTableName("Stuffing Tally/CLP");
		audit.setOldValue(oldValue);
		audit.setNewValue(newValue);
		exportauditrepo.save(audit);

		// Update process ID
		processnextidrepo.updateAuditTrail(cid, bid, "P05085", nextAuditId, "2024");

	}

	@GetMapping("/searchTallyData")
	public ResponseEntity<?> searchTallyData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String search) {

		List<Object[]> data = exportstufftallyrepo.search(cid, bid, search);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSelectedData")
	public ResponseEntity<?> getSelectedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String search) {

		List<ExportStuffTally> result = exportstufftallyrepo.getDataByTallyId(cid, bid, search);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	private static String getCurrentFinancialYear() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		// Check if the current date is before or after April 1st
		if (month < 4 || (month == 4 && day < 1)) {
			year--; // If before April 1st, consider the previous year
		}

		// Calculate financial year start and end
		int financialYearStart = year % 100; // Current year for start
		int financialYearEnd = (year + 1) % 100; // Next year for end

		// Format as YY-YY
		return String.format("%02d-%02d", financialYearStart, financialYearEnd);
	}

	@GetMapping("/getSbNoForTally")
	public ResponseEntity<?> getSbNoForTally(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("sb") String sb) {

		List<Object[]> data = exportstuffrepo.getSbNoForTally(cid, bid, sb);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSbNoForTally1")
	public ResponseEntity<?> getSbNoForTally1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("con") String con) {

		List<Object[]> data = exportstuffrepo.getSbNoForTally1(cid, bid, con);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/searchDataForTally")
	public ResponseEntity<?> searchDataForTally(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("sb") String sb, @RequestParam("sbTransId") String sbTransId,
			@RequestParam(name = "saveCheck", required = false) String saveCheck,
			@RequestParam(name = "con", required = false) String con,
			@RequestParam(name = "newCheck", required = false) String newCheck) {

		if ("Y".equals(newCheck)) {
			List<ExportStuffRequest> data = exportstuffrepo.getDataForSbWiseStuff(cid, bid, sb, sbTransId);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);
		} else {
			List<ExportStuffTally> existData = exportstufftallyrepo.getDataBySbNo(cid, bid, sbTransId, sb);

			if (existData.isEmpty()) {
				return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(existData, HttpStatus.OK);
		}

	}

	@PostMapping("/saveSbWiseTallyRecord")
	private ResponseEntity<?> saveSbWiseTallyRecord(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {
		ObjectMapper mapper = new ObjectMapper();

		ExportStuffTally singleTally = mapper.readValue(mapper.writeValueAsString(data.get("singleTally")),
				ExportStuffTally.class);

		List<ExportStuffTally> tally = mapper.readValue(mapper.writeValueAsString(data.get("tally")),
				new TypeReference<List<ExportStuffTally>>() {
				});

		if (tally.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		ExportSbEntry sbENtry = exportsbrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, singleTally.getSbNo(),
				singleTally.getSbTransId());

		if (sbENtry == null) {
			return new ResponseEntity<>("Export sb data not found", HttpStatus.CONFLICT);
		}

		String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05084", "2024");

		// Extract the parts of the ID
		String[] parts = lastValue.split("/");
		String baseIdWithPrefix = parts[0]; // This should be "STWO0000"
		String numericPart = baseIdWithPrefix.substring(4); // Extract the numeric part after "STWO"
		String financialYear = parts[1];

		// Increment the numerical part
		int newVal = Integer.parseInt(numericPart) + 1;

		// Format newVal to maintain leading zeros (e.g., 0001)
		String formattedNewVal = String.format("%04d", newVal);

		// Get the current financial year
		String currentFinancialYear = getCurrentFinancialYear();

		// Construct the new ID in the desired format
		String newId = "STWO" + formattedNewVal + "/" + currentFinancialYear;

		if (singleTally.getVesselId() == null || singleTally.getVesselId().isEmpty()) {
			String autoIncrementVesselId = processService.autoIncrementVesselId(cid, bid, "P03202");
			Date currentDate = new Date();
			Date zeroDate = new Date(0);

			Vessel newVessel = new Vessel(cid, bid, autoIncrementVesselId, singleTally.getVesselName(), "P00220",
					singleTally.getTerminal(), "Y", "Y", singleTally.getVesselName(), user, new Date(), user,
					new Date());

			Voyage newVoyage = new Voyage(cid, bid, singleTally.getPod(), "", autoIncrementVesselId,
					singleTally.getVoyageNo(), singleTally.getViaNo(), " ", " ", " ", 1, zeroDate, zeroDate,
					singleTally.getGateOpenDate(), zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, 0, 0, "",
					singleTally.getVesselName(), "", singleTally.getBerthingDate(), singleTally.getRotationNo(),
					singleTally.getRotationDate(), zeroDate, " ", zeroDate, " ", zeroDate, new BigDecimal("0"), user,
					currentDate, user, currentDate, user, currentDate, "A");

			vesselrepo.save(newVessel);
			voyagerepo.save(newVoyage);

			singleTally.setVesselId(autoIncrementVesselId);

		}

		for (ExportStuffTally e : tally) {

			ExportSbCargoEntry cargo = exportsbcargorepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
					singleTally.getSbNo(), singleTally.getSbTransId());

			if (cargo == null) {
				return new ResponseEntity<>("Export cargo data not found", HttpStatus.CONFLICT);
			}

			if (e.getStuffTallyId().isEmpty() || e.getStuffTallyId() == null) {
				List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTrans1(cid, bid,
						singleTally.getSbTransId(), singleTally.getSbNo());

				if (cartingData.isEmpty()) {
					return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
				}
				ExportStuffRequest stuffReq = exportstuffrepo.getDataBySbNoSbTransAndStuffReqId(cid, bid,
						singleTally.getSbTransId(), singleTally.getSbNo(), e.getStuffId());

				if (stuffReq == null) {
					return new ResponseEntity<>("Stuffing request data not found", HttpStatus.CONFLICT);
				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05083", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("STW%07d", nextNumericNextID1);

				int srNo = 1;

				BigDecimal val = e.getStuffedQty();

				for (ExportCarting c : cartingData) {
					BigDecimal remainingQty = c.getActualNoOfPackages().subtract(c.getStuffedNoOfPackages());

					if (val.compareTo(BigDecimal.ZERO) <= 0) {
						break;
					}

					BigDecimal qty = BigDecimal.ZERO;

					if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
						qty = remainingQty;
					} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
						qty = val;
					} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
						qty = remainingQty;
					}

					BigDecimal area = (c.getAreaOccupied().multiply(qty)).divide(c.getYardPackages());

					ExportStuffTally newTally = new ExportStuffTally();

					newTally.setCompanyId(cid);
					newTally.setBranchId(bid);
					newTally.setStuffTallyId(HoldNextIdD1);
					newTally.setSbTransId(c.getSbTransId());
					newTally.setStuffTallyLineId(srNo);
					newTally.setProfitcentreId(c.getProfitcentreId());
					newTally.setCartingTransId(c.getCartingTransId());
					newTally.setCartingLineId(c.getCartingLineId());
					newTally.setSbLineId(c.getSbLineNo());
					newTally.setSbNo(c.getSbNo());
					newTally.setStuffTallyWoTransId(newId);
					newTally.setStuffTallyCutWoTransDate(new Date());
					newTally.setStuffTallyDate(new Date());
					newTally.setStuffId(stuffReq.getStuffReqId());
					newTally.setStuffDate(stuffReq.getStuffReqDate());
					newTally.setSbDate(cargo.getSbDate());
					newTally.setShift(singleTally.getShift());
					newTally.setAgentSealNo(e.getAgentSealNo());
					newTally.setVesselId(singleTally.getVesselId());
					newTally.setVoyageNo(singleTally.getVoyageNo());
					newTally.setRotationNo(singleTally.getRotationNo());
					newTally.setMovementType(singleTally.getMovementType());
					newTally.setCargoType(e.getCargoType());
					newTally.setPol(sbENtry.getPol());
					newTally.setTerminal(singleTally.getTerminal());
					newTally.setPod(singleTally.getPod());
					newTally.setFinalPod(singleTally.getFinalPod());
					newTally.setContainerNo(e.getContainerNo());
					newTally.setContainerSize(e.getContainerSize());
					newTally.setPeriodFrom(singleTally.getPeriodFrom());
					newTally.setGateInId(stuffReq.getGateInId());
					newTally.setContainerStatus(e.getContainerStatus());
					newTally.setContainerType(e.getContainerType());
					newTally.setContainerCondition(stuffReq.getContainerHealth());
					newTally.setYardPackages(c.getYardPackages());
					newTally.setCellAreaAllocated(e.getCellAreaAllocated());
					newTally.setOnAccountOf(stuffReq.getOnAccountOf());
					newTally.setCha(sbENtry.getCha());
					newTally.setTotalGrossWeight(e.getCargoWeight());
					newTally.setCargoWeight(e.getCargoWeight());
					newTally.setStuffRequestQty(e.getStuffRequestQty());
					newTally.setStuffedQty(qty);
					newTally.setBalanceQty(c.getYardPackages().subtract(qty));
					newTally.setTareWeight(e.getTareWeight());
					newTally.setAreaReleased(area);
					newTally.setGenSetRequired(singleTally.getGenSetRequired());
					newTally.setHaz(singleTally.getHaz());
					newTally.setImoCode(sbENtry.getImoCode());
					newTally.setShippingAgent(stuffReq.getShippingAgent());
					newTally.setShippingLine(stuffReq.getShippingLine());
					newTally.setCommodity(singleTally.getCommodity());
					newTally.setCustomsSealNo(e.getCustomsSealNo());
					newTally.setViaNo(singleTally.getViaNo());
					newTally.setCartingDate(c.getCartingTransDate());
					newTally.setExporterName(sbENtry.getExporterName());
					newTally.setConsignee(singleTally.getConsignee());
					newTally.setFob(singleTally.getFob());
					newTally.setBerthingDate(singleTally.getBerthingDate());
					newTally.setGateOpenDate(singleTally.getGateOpenDate());
					newTally.setDocType(singleTally.getDocType());
					newTally.setDocNo(singleTally.getDocNo());
					newTally.setStatus("A");
					newTally.setCreatedBy(user);
					newTally.setCreatedDate(new Date());
					newTally.setApprovedBy(user);
					newTally.setApprovedDate(new Date());
					newTally.setDeliveryOrderNo(e.getDeliveryOrderNo());
					newTally.setStuffMode(singleTally.getStuffMode());
					newTally.setStuffLineId(stuffReq.getStuffReqLineId());
					newTally.setTypeOfPackage(cargo.getTypeOfPackage());
					newTally.setSealType(singleTally.getSealType());
					newTally.setRotationDate(singleTally.getRotationDate());

					exportstufftallyrepo.save(newTally);
					srNo++;
					processnextidrepo.updateAuditTrail(cid, bid, "P05083", HoldNextIdD1, "2024");
					processnextidrepo.updateAuditTrail(cid, bid, "P05084", newId, "2024");

					c.setStuffedNoOfPackages(
							(c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO : c.getStuffedNoOfPackages())
									.add(qty));

					exportcartingrepo.save(c);

					val = val.subtract(qty);

					List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid, c.getCartingTransId(),
							c.getCartingLineId());

					if (!grid.isEmpty()) {
						AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
						grid.stream().forEach(g -> {
							BigDecimal yardPackages = new BigDecimal(g.getYardPackages()); // Convert int to BigDecimal
							BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut() : BigDecimal.ZERO;
							YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
									g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());
							if (gridVal.get().compareTo(yardPackages.subtract(qtyTakenOut)) >= 0) {
								// Calculate and set QtyTakenOut
								BigDecimal updatedQtyTakenOut = (g.getQtyTakenOut() != null ? g.getQtyTakenOut()
										: BigDecimal.ZERO).add(yardPackages.subtract(qtyTakenOut));
								g.setQtyTakenOut(updatedQtyTakenOut);

								// Calculate and set AreaReleased
								BigDecimal updatedAreaReleased = (g.getAreaReleased() != null ? g.getAreaReleased()
										: BigDecimal.ZERO).add(g.getCellAreaAllocated());
								g.setAreaReleased(updatedAreaReleased);

								// Update gridVal by subtracting the difference
								gridVal.set(gridVal.get().subtract(yardPackages.subtract(qtyTakenOut)));
								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.subtract(updatedAreaReleased));
									yardBlockCellRepository.save(yard);
								}
							} else {
								// Partial deduction as gridVal is less than available quantity
								BigDecimal updatedQtyTakenOut = (g.getQtyTakenOut() != null ? g.getQtyTakenOut()
										: BigDecimal.ZERO).add(gridVal.get());
								g.setQtyTakenOut(updatedQtyTakenOut);

								// Calculate the area proportional to the gridVal
								BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridVal.get())
										.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

								BigDecimal updatedAreaReleased = (g.getAreaReleased() != null ? g.getAreaReleased()
										: BigDecimal.ZERO).add(tenArea);
								g.setAreaReleased(updatedAreaReleased);

								// Set gridVal to zero after partial deduction
								gridVal.set(BigDecimal.ZERO);
								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.subtract(tenArea));
									yardBlockCellRepository.save(yard);
								}
							}

							impexpgridrepo.save(g);
						});
					}
				}

				stuffReq.setStuffTally('Y');
				stuffReq.setStuffTallyId(HoldNextIdD1);
				stuffReq.setAgentSealNo(e.getAgentSealNo());
				stuffReq.setTareWeight(e.getTareWeight());
				stuffReq.setRotationNo(singleTally.getRotationNo());
				stuffReq.setRotationDate(singleTally.getRotationDate());
				stuffReq.setBerthingDate(singleTally.getBerthingDate());
				stuffReq.setGateOpenDate(singleTally.getGateOpenDate());
				stuffReq.setTypeOfPackage(cargo.getTypeOfPackage());
				stuffReq.setVesselId(singleTally.getVesselId());
				stuffReq.setVesselName(singleTally.getVesselName());
				stuffReq.setVoyageNo(singleTally.getVoyageNo());
				stuffReq.setViaNo(singleTally.getViaNo());
				stuffReq.setEditedBy(user);
				stuffReq.setEditedDate(new Date());
				stuffReq.setStuffedQty((stuffReq.getStuffedQty() == null ? BigDecimal.ZERO : stuffReq.getStuffedQty())
						.add(e.getStuffedQty()));

				exportstuffrepo.save(stuffReq);

				System.out.println("e.getStuffedQty() " + e.getStuffedQty());

				cargo.setStuffedQty((cargo.getStuffedQty() == null ? BigDecimal.ZERO : cargo.getStuffedQty())
						.add(e.getStuffedQty()));
				cargo.setStuffedWt((cargo.getStuffedWt() == null ? BigDecimal.ZERO : cargo.getStuffedWt())
						.add(e.getCargoWeight()));
				cargo.setCargoType(e.getCargoType());
				exportsbcargorepo.save(cargo);

				ExportInventory inv = exportinvrepo.getDataByContainerNo(cid, bid, e.getContainerNo());

				if (inv != null) {
					inv.setStuffTallyDate(new Date());
					inv.setStuffTallyId(HoldNextIdD1);

					exportinvrepo.save(inv);
				}
			} else {

				ExportStuffRequest stuffReq = exportstuffrepo.getDataBySbNoSbTransAndStuffReqId1(cid, bid,
						singleTally.getSbTransId(), singleTally.getSbNo(), e.getStuffId());

				System.out.println("e.getStuffId() " + e.getStuffId());
				if (stuffReq == null) {
					return new ResponseEntity<>("Stuffing request data not found", HttpStatus.CONFLICT);
				}

				List<ExportStuffTally> existingData = exportstufftallyrepo.getDataByStuffTallyId(cid, bid,
						e.getStuffTallyId());

				if (existingData.isEmpty()) {
					return new ResponseEntity<>("Data not found stuff tally Id " + e.getStuffTallyId(),
							HttpStatus.CONFLICT);
				}

				BigDecimal existStuffQty = existingData.stream().map(ExportStuffTally::getStuffedQty)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

				ExportStuffTally existData = (ExportStuffTally) existingData.get(0).clone();

				final String cargoType = cargo.getCargoType();

				BigDecimal totalStuffedQty = existingData.stream().map(ExportStuffTally::getStuffedQty)
						.reduce(BigDecimal.ZERO, BigDecimal::add);

				BigDecimal val = BigDecimal.ZERO;

				if (totalStuffedQty.compareTo(e.getStuffedQty()) < 0) {
					val = e.getStuffedQty().subtract(totalStuffedQty);
				} else if (totalStuffedQty.compareTo(e.getStuffedQty()) > 0) {
					val = totalStuffedQty.subtract(e.getStuffedQty());
				}

				System.out.println("val " + val);
				System.out.println("t.getCustomsSealNo() " + e.getCustomsSealNo());

				for (ExportStuffTally exist : existingData) {

					ExportCarting existCar = exportcartingrepo.getDataBySbNoSbTransAndLineNoAndCartingTransId(cid, bid,
							singleTally.getSbTransId(), singleTally.getSbNo(), exist.getCartingTransId(),
							exist.getCartingLineId());

					if (existCar != null) {

						exist.setVesselId(singleTally.getVesselId());
						exist.setVoyageNo(singleTally.getVoyageNo());
						exist.setViaNo(singleTally.getViaNo());
						exist.setPod(singleTally.getPod());
						exist.setFinalPod(singleTally.getFinalPod());
						exist.setBerthingDate(singleTally.getBerthingDate());
						exist.setTerminal(singleTally.getTerminal());
						exist.setRotationNo(singleTally.getRotationNo());
						exist.setRotationDate(singleTally.getBerthingDate());
						exist.setStuffMode(singleTally.getStuffMode());
						exist.setAgentSealNo(e.getAgentSealNo());
						exist.setCustomsSealNo(e.getCustomsSealNo());
						exist.setTareWeight(e.getTareWeight());
						exist.setCargoType(e.getCargoType());
						exist.setTotalGrossWeight(e.getCargoWeight());
						exist.setCargoWeight(e.getCargoWeight());

						if (totalStuffedQty.compareTo(e.getStuffedQty()) < 0) {
							BigDecimal remainingQty = existCar.getActualNoOfPackages()
									.subtract(existCar.getStuffedNoOfPackages());

							if (remainingQty.compareTo(BigDecimal.ZERO) > 0 && val.compareTo(BigDecimal.ZERO) > 0) {

								if (exist.getStuffId().equals(e.getStuffId())) {
									BigDecimal qty = BigDecimal.ZERO;

									if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) > 0) {
											qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
										} else if (remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed()
												.subtract(stuffReq.getStuffedQty())) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}
									}

									BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
											.divide(existCar.getYardPackages());

									exist.setStuffedQty(exist.getStuffedQty().add(qty));
									exist.setBalanceQty(exist.getBalanceQty().subtract(qty));
									exist.setAreaReleased(exist.getAreaReleased().add(area));
									stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
											: stuffReq.getStuffedQty().add(qty));

									val = val.subtract(qty);

									existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().add(qty));

									exportcartingrepo.save(existCar);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
											existCar.getCartingTransId(), existCar.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											BigDecimal yardPackages = new BigDecimal(g.getYardPackages()); // Convert
																											// int to
																											// BigDecimal
											BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut()
													: BigDecimal.ZERO;

											if (gridVal.get().compareTo(yardPackages.subtract(qtyTakenOut)) >= 0) {
												// Update QtyTakenOut
												g.setQtyTakenOut(qtyTakenOut.add(yardPackages.subtract(qtyTakenOut)));

												// Calculate and update AreaReleased proportionally
												BigDecimal tenArea = g.getCellAreaAllocated()
														.multiply(yardPackages.subtract(qtyTakenOut))
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);
												g.setAreaReleased((g.getAreaReleased() != null ? g.getAreaReleased()
														: BigDecimal.ZERO).add(tenArea));

												// Update gridVal by subtracting the difference
												gridVal.set(gridVal.get().subtract(yardPackages.subtract(qtyTakenOut)));
											} else {
												// Partial deduction
												g.setQtyTakenOut(qtyTakenOut.add(gridVal.get()));

												// Calculate the proportional area based on gridVal
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridVal.get())
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);
												g.setAreaReleased((g.getAreaReleased() != null ? g.getAreaReleased()
														: BigDecimal.ZERO).add(tenArea));

												// Set gridVal to zero after deduction
												gridVal.set(BigDecimal.ZERO);
											}

											impexpgridrepo.save(g);
										});
									}

								}

								else {
									BigDecimal qty = BigDecimal.ZERO;

									ExportStuffRequest req = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId3(cid,
											bid, singleTally.getSbTransId(), singleTally.getSbNo(), exist.getStuffId());

									if (req != null) {
										if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

											if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = req.getNoOfPackagesStuffed().subtract(req.getStuffedQty());
											} else if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = remainingQty;
											} else {
												qty = remainingQty;
											}

										} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

											if (val.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = req.getNoOfPackagesStuffed().subtract(req.getStuffedQty());
											} else if (val.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = val;
											} else {
												qty = val;
											}

										} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
											if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = req.getNoOfPackagesStuffed().subtract(req.getStuffedQty());
											} else if (remainingQty.compareTo(
													req.getNoOfPackagesStuffed().subtract(req.getStuffedQty())) > 0) {
												qty = remainingQty;
											} else {
												qty = remainingQty;
											}
										}

										BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
												.divide(existCar.getYardPackages());

										exist.setStuffedQty(exist.getStuffedQty().add(qty));
										exist.setBalanceQty(exist.getBalanceQty().subtract(qty));
										exist.setAreaReleased(exist.getAreaReleased().add(area));

										req.setStuffedQty(req.getStuffedQty() == null ? BigDecimal.ZERO
												: req.getStuffedQty().add(qty));

										exportstuffrepo.save(req);

										val = val.subtract(qty);

										existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().add(qty));

										exportcartingrepo.save(existCar);

										List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
												existCar.getCartingTransId(), existCar.getCartingLineId());

										if (!grid.isEmpty()) {
											AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
											grid.stream().forEach(g -> {
												BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages());
												BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut()
														: BigDecimal.ZERO;
												BigDecimal gridValue = gridVal.get();
												YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid,
														bid, g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());

												if (gridValue.compareTo(yardPackages.subtract(qtyTakenOut)) >= 0) {
													// Update QtyTakenOut with the remaining amount
													BigDecimal newQtyTakenOut = qtyTakenOut
															.add(yardPackages.subtract(qtyTakenOut));
													g.setQtyTakenOut(newQtyTakenOut);

													// Calculate the proportionate area to release
													BigDecimal tenArea = g.getCellAreaAllocated()
															.multiply(yardPackages.subtract(qtyTakenOut))
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update AreaReleased
													BigDecimal newAreaReleased = (g.getAreaReleased() != null
															? g.getAreaReleased()
															: BigDecimal.ZERO).add(tenArea);
													g.setAreaReleased(newAreaReleased);

													// Subtract the difference from gridVal
													gridVal.set(gridValue.subtract(yardPackages.subtract(qtyTakenOut)));
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
														yardBlockCellRepository.save(yard);
													}
												} else {
													// Partial deduction since gridVal is less than the required amount
													g.setQtyTakenOut(qtyTakenOut.add(gridValue));

													// Calculate the proportionate area to release based on gridVal
													BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update AreaReleased
													BigDecimal newAreaReleased = (g.getAreaReleased() != null
															? g.getAreaReleased()
															: BigDecimal.ZERO).add(tenArea);
													g.setAreaReleased(newAreaReleased);

													// Set gridVal to zero after the deduction
													gridVal.set(BigDecimal.ZERO);
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
														yardBlockCellRepository.save(yard);
													}
												}

												impexpgridrepo.save(g);
											});

										}
									}

								}

							}

							if (val.compareTo(BigDecimal.ZERO) > 0) {
								List<ExportCarting> cartingData1 = exportcartingrepo.getDataBySbNoSbTrans1(cid, bid,
										singleTally.getSbTransId(), singleTally.getSbNo());

								if (!cartingData1.isEmpty()) {

									for (ExportCarting c : cartingData1) {
										ExportStuffTally exist1 = exportstufftallyrepo
												.getDataByStuffTallyIdANDStuffIdAndCartingTransId2(cid, bid,
														e.getStuffTallyId(), e.getStuffId(), c.getCartingTransId(),
														singleTally.getSbNo(), singleTally.getSbTransId());

										BigDecimal remainingQty1 = c.getActualNoOfPackages()
												.subtract(c.getStuffedNoOfPackages());

										if (stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())
												.compareTo(BigDecimal.ZERO) > 0 && val.compareTo(BigDecimal.ZERO) > 0
												&& remainingQty1.compareTo(BigDecimal.ZERO) > 0) {

											if (val.compareTo(BigDecimal.ZERO) <= 0) {
												break;
											}

											if (exist1 != null) {
												BigDecimal qty = BigDecimal.ZERO;

												if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

													if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) > 0) {
														qty = stuffReq.getNoOfPackagesStuffed()
																.subtract(stuffReq.getStuffedQty());
													} else if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) < 0) {
														qty = remainingQty1;
													} else {
														qty = remainingQty1;
													}

												} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

													if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) > 0) {
														qty = stuffReq.getNoOfPackagesStuffed()
																.subtract(stuffReq.getStuffedQty());
													} else if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) < 0) {
														qty = val;
													} else {
														qty = val;
													}

												} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
													if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) > 0) {
														qty = stuffReq.getNoOfPackagesStuffed()
																.subtract(stuffReq.getStuffedQty());
													} else if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) < 0) {
														qty = remainingQty1;
													} else {
														qty = remainingQty1;
													}
												}

												System.out.println("qty11 " + qty);

												BigDecimal area = (c.getAreaOccupied().multiply(qty))
														.divide(c.getYardPackages());

												exist1.setStuffedQty(exist1.getStuffedQty().add(qty));
												exist1.setBalanceQty(exist1.getBalanceQty().subtract(qty));
												exist1.setAreaReleased(exist1.getAreaReleased().add(area));

												exist1.setVesselId(singleTally.getVesselId());
												exist1.setVoyageNo(singleTally.getVoyageNo());
												exist1.setViaNo(singleTally.getViaNo());
												exist1.setPod(singleTally.getPod());
												exist1.setFinalPod(singleTally.getFinalPod());
												exist1.setBerthingDate(singleTally.getBerthingDate());
												exist1.setTerminal(singleTally.getTerminal());
												exist1.setRotationNo(singleTally.getRotationNo());
												exist1.setRotationDate(singleTally.getBerthingDate());
												exist1.setStuffMode(singleTally.getStuffMode());
												exist1.setAgentSealNo(e.getAgentSealNo());
												exist1.setCustomsSealNo(e.getCustomsSealNo());
												exist1.setTareWeight(e.getTareWeight());
												exist1.setCargoType(e.getCargoType());
												exist1.setTotalGrossWeight(e.getCargoWeight());
												exist1.setCargoWeight(e.getCargoWeight());

												exportstufftallyrepo.save(exist1);
												stuffReq.setStuffedQty(
														stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
																: stuffReq.getStuffedQty().add(qty));

												val = val.subtract(qty);

												c.setStuffedNoOfPackages(
														(c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO
																: c.getStuffedNoOfPackages()).add(qty));

												exportcartingrepo.save(c);

												List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
														c.getCartingTransId(), c.getCartingLineId());

												if (!grid.isEmpty()) {
													AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
													grid.stream().forEach(g -> {
														BigDecimal yardPackages = BigDecimal
																.valueOf(g.getYardPackages()); // Convert int to
																								// BigDecimal
														BigDecimal qtyTakenOut = g.getQtyTakenOut() != null
																? g.getQtyTakenOut()
																: BigDecimal.ZERO;
														BigDecimal gridValue = gridVal.get();
														YardBlockCell yard = yardBlockCellRepository
																.getYardCellByCellNo(cid, bid, g.getYardLocation(),
																		g.getYardBlock(), g.getBlockCellNo());

														if (gridValue
																.compareTo(yardPackages.subtract(qtyTakenOut)) >= 0) {
															// Update QtyTakenOut with the remaining amount
															BigDecimal newQtyTakenOut = qtyTakenOut
																	.add(yardPackages.subtract(qtyTakenOut));
															g.setQtyTakenOut(newQtyTakenOut);

															// Calculate the proportionate area to release
															BigDecimal tenArea = g.getCellAreaAllocated()
																	.multiply(yardPackages.subtract(qtyTakenOut))
																	.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

															// Update AreaReleased
															BigDecimal newAreaReleased = (g.getAreaReleased() != null
																	? g.getAreaReleased()
																	: BigDecimal.ZERO).add(tenArea);
															g.setAreaReleased(newAreaReleased);

															// Subtract the difference from gridVal
															gridVal.set(gridValue
																	.subtract(yardPackages.subtract(qtyTakenOut)));
															if (yard != null) {
																yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																		? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
																yardBlockCellRepository.save(yard);
															}
														} else {
															// Partial deduction since gridVal is less than the required
															// amount
															g.setQtyTakenOut(qtyTakenOut.add(gridValue));

															// Calculate the proportionate area to release based on
															// gridVal
															BigDecimal tenArea = g.getCellAreaAllocated()
																	.multiply(gridValue)
																	.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

															// Update AreaReleased
															BigDecimal newAreaReleased = (g.getAreaReleased() != null
																	? g.getAreaReleased()
																	: BigDecimal.ZERO).add(tenArea);
															g.setAreaReleased(newAreaReleased);

															// Set gridVal to zero after the deduction
															gridVal.set(BigDecimal.ZERO);
															if (yard != null) {
																yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																		? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
																yardBlockCellRepository.save(yard);
															}
														}

														impexpgridrepo.save(g);
													});
												}
											} else {
												BigDecimal qty = BigDecimal.ZERO;

												if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

													if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) > 0) {
														qty = stuffReq.getNoOfPackagesStuffed()
																.subtract(stuffReq.getStuffedQty());
													} else if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) < 0) {
														qty = remainingQty1;
													} else {
														qty = remainingQty1;
													}

												} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
													if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) > 0) {
														qty = stuffReq.getNoOfPackagesStuffed()
																.subtract(stuffReq.getStuffedQty());
													} else if (val.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) < 0) {
														qty = val;
													} else {
														qty = val;
													}

												} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) < 0) {

													if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) > 0) {
														qty = stuffReq.getNoOfPackagesStuffed()
																.subtract(stuffReq.getStuffedQty());
													} else if (remainingQty1.compareTo(stuffReq.getNoOfPackagesStuffed()
															.subtract(stuffReq.getStuffedQty())) < 0) {
														qty = remainingQty1;
													} else {
														qty = remainingQty1;
													}

												}

												BigDecimal area = (c.getAreaOccupied().multiply(qty))
														.divide(c.getYardPackages());

												ExportStuffTally newTally = new ExportStuffTally();

												int sr = exportstufftallyrepo.countOfStuffRecords(cid, bid,
														e.getStuffTallyId());

												newTally.setCompanyId(cid);
												newTally.setBranchId(bid);
												newTally.setStuffTallyId(e.getStuffTallyId());
												newTally.setSbTransId(c.getSbTransId());
												newTally.setStuffTallyLineId(sr + 1);
												newTally.setProfitcentreId(c.getProfitcentreId());
												newTally.setCartingTransId(c.getCartingTransId());
												newTally.setCartingLineId(c.getCartingLineId());
												newTally.setSbLineId(c.getSbLineNo());
												newTally.setSbNo(c.getSbNo());
												newTally.setStuffTallyWoTransId(singleTally.getStuffTallyWoTransId());
												newTally.setStuffTallyCutWoTransDate(
														singleTally.getStuffTallyCutWoTransDate());
												newTally.setStuffTallyDate(e.getStuffTallyDate());
												newTally.setStuffId(stuffReq.getStuffReqId());
												newTally.setStuffDate(stuffReq.getStuffReqDate());
												newTally.setSbDate(cargo.getSbDate());
												newTally.setShift(singleTally.getShift());
												newTally.setAgentSealNo(e.getAgentSealNo());
												newTally.setVesselId(singleTally.getVesselId());
												newTally.setVoyageNo(singleTally.getVoyageNo());
												newTally.setRotationNo(singleTally.getRotationNo());
												newTally.setPol(sbENtry.getPol());
												newTally.setTerminal(singleTally.getTerminal());
												newTally.setPod(singleTally.getPod());
												newTally.setFinalPod(singleTally.getFinalPod());
												newTally.setContainerNo(e.getContainerNo());
												newTally.setContainerSize(e.getContainerSize());
												newTally.setPeriodFrom(singleTally.getPeriodFrom());
												newTally.setGateInId(stuffReq.getGateInId());
												newTally.setContainerStatus(e.getContainerStatus());
												newTally.setContainerType(e.getContainerType());
												newTally.setContainerCondition(stuffReq.getContainerHealth());
												newTally.setYardPackages(c.getYardPackages());
												newTally.setCellAreaAllocated(e.getCellAreaAllocated());
												newTally.setOnAccountOf(stuffReq.getOnAccountOf());
												newTally.setCha(sbENtry.getCha());
												newTally.setTotalGrossWeight(e.getCargoWeight());
												newTally.setCargoWeight(e.getCargoWeight());
												newTally.setStuffRequestQty(e.getStuffRequestQty());
												newTally.setStuffedQty(qty);
												newTally.setBalanceQty(c.getYardPackages().subtract(qty));
												newTally.setTareWeight(e.getTareWeight());
												newTally.setAreaReleased(area);
												newTally.setGenSetRequired(singleTally.getGenSetRequired());
												newTally.setHaz(singleTally.getHaz());
												newTally.setImoCode(sbENtry.getImoCode());
												newTally.setShippingAgent(stuffReq.getShippingAgent());
												newTally.setShippingLine(stuffReq.getShippingLine());
												newTally.setCommodity(singleTally.getCommodity());
												newTally.setCustomsSealNo(e.getCustomsSealNo());
												newTally.setViaNo(singleTally.getViaNo());
												newTally.setCartingDate(c.getCartingTransDate());
												newTally.setExporterName(sbENtry.getExporterName());
												newTally.setConsignee(singleTally.getConsignee());
												newTally.setFob(singleTally.getFob());
												newTally.setBerthingDate(singleTally.getBerthingDate());
												newTally.setGateOpenDate(singleTally.getGateOpenDate());
												newTally.setDocType(singleTally.getDocType());
												newTally.setDocNo(singleTally.getDocNo());
												newTally.setStatus("A");
												newTally.setCreatedBy(user);
												newTally.setCreatedDate(new Date());
												newTally.setApprovedBy(user);
												newTally.setApprovedDate(new Date());
												newTally.setDeliveryOrderNo(e.getDeliveryOrderNo());
												newTally.setStuffMode(singleTally.getStuffMode());
												newTally.setStuffLineId(stuffReq.getStuffReqLineId());
												newTally.setTypeOfPackage(cargo.getTypeOfPackage());
												newTally.setSealType(singleTally.getSealType());
												newTally.setRotationDate(singleTally.getRotationDate());

												exportstufftallyrepo.save(newTally);

												stuffReq.setStuffedQty(
														stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
																: stuffReq.getStuffedQty().add(qty));

												c.setStuffedNoOfPackages(
														(c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO
																: c.getStuffedNoOfPackages()).add(qty));

												exportcartingrepo.save(c);

												val = val.subtract(qty);

												List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid,
														c.getCartingTransId(), c.getCartingLineId());

												if (!grid.isEmpty()) {
													AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
													grid.stream().forEach(g -> {
														YardBlockCell yard = yardBlockCellRepository
																.getYardCellByCellNo(cid, bid, g.getYardLocation(),
																		g.getYardBlock(), g.getBlockCellNo());
														BigDecimal yardPackages = BigDecimal
																.valueOf(g.getYardPackages()); // Convert int to
																								// BigDecimal once
														BigDecimal qtyTakenOut = g.getQtyTakenOut() != null
																? g.getQtyTakenOut()
																: BigDecimal.ZERO;
														BigDecimal gridValue = gridVal.get();

														BigDecimal availablePackages = yardPackages
																.subtract(qtyTakenOut);

														if (gridValue.compareTo(availablePackages) >= 0) {
															// Update QtyTakenOut to include all available packages
															g.setQtyTakenOut(qtyTakenOut.add(availablePackages));

															// Calculate the proportional area to release based on the
															// entire available amount
															BigDecimal tenArea = g.getCellAreaAllocated()
																	.multiply(availablePackages)
																	.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

															// Update AreaReleased, accounting for potential null values
															BigDecimal newAreaReleased = (g.getAreaReleased() != null
																	? g.getAreaReleased()
																	: BigDecimal.ZERO).add(tenArea);
															g.setAreaReleased(newAreaReleased);

															// Deduct the entire available packages from gridVal
															gridVal.set(gridValue.subtract(availablePackages));
															if (yard != null) {
																yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																		? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
																yardBlockCellRepository.save(yard);
															}
														} else {
															// Partial deduction based on gridVal's amount
															g.setQtyTakenOut(qtyTakenOut.add(gridValue));

															// Calculate the proportional area based on gridVal
															BigDecimal tenArea = g.getCellAreaAllocated()
																	.multiply(gridValue)
																	.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

															// Update AreaReleased
															BigDecimal newAreaReleased = (g.getAreaReleased() != null
																	? g.getAreaReleased()
																	: BigDecimal.ZERO).add(tenArea);
															g.setAreaReleased(newAreaReleased);

															// Set gridVal to zero after deduction
															gridVal.set(BigDecimal.ZERO);
															if (yard != null) {
																yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																		? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
																yardBlockCellRepository.save(yard);
															}
														}

														impexpgridrepo.save(g);
													});
												}
											}
										}

									}
								}

							}

							if (val.compareTo(BigDecimal.ZERO) > 0) {

								if ((cargo.getCartedPackages().subtract(new BigDecimal(cargo.getStuffReqQty())))
										.compareTo(BigDecimal.ZERO) > 0) {
									List<ExportCarting> cartingData1 = exportcartingrepo.getDataBySbNoSbTrans1(cid, bid,
											singleTally.getSbTransId(), singleTally.getSbNo());

									if (cartingData1.isEmpty()) {
										return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
									}

									List<ExportCarting> cartingData2 = new ArrayList<>();

									for (ExportCarting c : cartingData1) {

										if (val.compareTo(BigDecimal.ZERO) > 0 && (cargo.getCartedPackages()
												.subtract(new BigDecimal(cargo.getStuffReqQty())))
												.compareTo(BigDecimal.ZERO) > 0) {
											ExportStuffTally exist1 = exportstufftallyrepo
													.getDataByStuffTallyIdANDStuffIdAndCartingTransId2(cid, bid,
															e.getStuffTallyId(), e.getStuffId(), c.getCartingTransId(),
															singleTally.getSbNo(), singleTally.getSbTransId());

											BigDecimal remainingQty1 = c.getActualNoOfPackages()
													.subtract(c.getStuffedNoOfPackages());

											if (exist1 != null && remainingQty1.compareTo(BigDecimal.ZERO) > 0) {
												BigDecimal qty = BigDecimal.ZERO;

												if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

													if (cargo.getCartedPackages()
															.subtract(new BigDecimal(cargo.getStuffReqQty()))
															.compareTo(remainingQty1) > 0) {
														qty = remainingQty1;
													} else if (cargo.getCartedPackages()
															.subtract(new BigDecimal(cargo.getStuffReqQty()))
															.compareTo(remainingQty1) < 0) {
														qty = cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()));
													} else {
														qty = remainingQty1;
													}

												} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

													if (cargo.getCartedPackages()
															.subtract(new BigDecimal(cargo.getStuffReqQty()))
															.compareTo(val) > 0) {
														qty = val;
													} else if (cargo.getCartedPackages()
															.subtract(new BigDecimal(cargo.getStuffReqQty()))
															.compareTo(val) < 0) {
														qty = cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()));
													} else {
														qty = val;
													}

												} else if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) < 0) {

													if (cargo.getCartedPackages()
															.subtract(new BigDecimal(cargo.getStuffReqQty()))
															.compareTo(remainingQty1) > 0) {
														qty = remainingQty1;
													} else if (cargo.getCartedPackages()
															.subtract(new BigDecimal(cargo.getStuffReqQty()))
															.compareTo(remainingQty1) < 0) {
														qty = cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()));
													} else {
														qty = remainingQty1;
													}

												}

												BigDecimal area = (c.getAreaOccupied().multiply(qty))
														.divide(c.getYardPackages());

												exist1.setStuffedQty(exist1.getStuffedQty().add(qty));
												exist1.setBalanceQty(exist1.getBalanceQty().subtract(qty));
												exist1.setAreaReleased(exist1.getAreaReleased().add(area));

												exist1.setVesselId(singleTally.getVesselId());
												exist1.setVoyageNo(singleTally.getVoyageNo());
												exist1.setViaNo(singleTally.getViaNo());
												exist1.setPod(singleTally.getPod());
												exist1.setFinalPod(singleTally.getFinalPod());
												exist1.setBerthingDate(singleTally.getBerthingDate());
												exist1.setTerminal(singleTally.getTerminal());
												exist1.setRotationNo(singleTally.getRotationNo());
												exist1.setRotationDate(singleTally.getBerthingDate());
												exist1.setStuffMode(singleTally.getStuffMode());
												exist1.setAgentSealNo(e.getAgentSealNo());
												exist1.setCustomsSealNo(e.getCustomsSealNo());
												exist1.setTareWeight(e.getTareWeight());
												exist1.setCargoType(e.getCargoType());
												exist1.setTotalGrossWeight(e.getCargoWeight());
												exist1.setCargoWeight(e.getCargoWeight());

												exportstufftallyrepo.save(exist1);

												stuffReq.setStuffedQty(
														stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
																: stuffReq.getStuffedQty().add(qty));
												stuffReq.setNoOfPackagesStuffed(
														stuffReq.getNoOfPackagesStuffed().add(qty));

												cargo.setStuffReqQty(
														cargo.getStuffReqQty() + Integer.parseInt(qty.toString()));

												val = val.subtract(qty);

												c.setStuffedNoOfPackages(c.getStuffedNoOfPackages().add(qty));

												exportcartingrepo.save(c);

												List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
														c.getCartingTransId(), c.getCartingLineId());

												if (!grid.isEmpty()) {
													AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
													grid.stream().forEach(g -> {
														YardBlockCell yard = yardBlockCellRepository
																.getYardCellByCellNo(cid, bid, g.getYardLocation(),
																		g.getYardBlock(), g.getBlockCellNo());
														BigDecimal yardPackages = BigDecimal
																.valueOf(g.getYardPackages()); // Convert int to
																								// BigDecimal once
														BigDecimal qtyTakenOut = g.getQtyTakenOut() != null
																? g.getQtyTakenOut()
																: BigDecimal.ZERO;
														BigDecimal gridValue = gridVal.get();

														BigDecimal availablePackages = yardPackages
																.subtract(qtyTakenOut);

														if (gridValue.compareTo(availablePackages) >= 0) {
															// Update QtyTakenOut to include all available packages
															g.setQtyTakenOut(qtyTakenOut.add(availablePackages));

															// Calculate the proportional area to release based on the
															// entire available amount
															BigDecimal tenArea = g.getCellAreaAllocated()
																	.multiply(availablePackages)
																	.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

															// Update AreaReleased, accounting for potential null values
															BigDecimal newAreaReleased = (g.getAreaReleased() != null
																	? g.getAreaReleased()
																	: BigDecimal.ZERO).add(tenArea);
															g.setAreaReleased(newAreaReleased);

															// Deduct the entire available packages from gridVal
															gridVal.set(gridValue.subtract(availablePackages));
															if (yard != null) {
																yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																		? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
																yardBlockCellRepository.save(yard);
															}
														} else {
															// Partial deduction based on gridVal's amount
															g.setQtyTakenOut(qtyTakenOut.add(gridValue));

															// Calculate the proportional area based on gridVal
															BigDecimal tenArea = g.getCellAreaAllocated()
																	.multiply(gridValue)
																	.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

															// Update AreaReleased
															BigDecimal newAreaReleased = (g.getAreaReleased() != null
																	? g.getAreaReleased()
																	: BigDecimal.ZERO).add(tenArea);
															g.setAreaReleased(newAreaReleased);

															// Set gridVal to zero after deduction
															gridVal.set(BigDecimal.ZERO);
															if (yard != null) {
																yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																		? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.subtract(newAreaReleased));
																yardBlockCellRepository.save(yard);
															}
														}

														impexpgridrepo.save(g);
													});
												}
											} else {
												cartingData2.add(c);
											}
										}

									}

									if (val.compareTo(BigDecimal.ZERO) > 0 && cartingData2.size() > 0) {

										for (ExportCarting c : cartingData2) {
											if (val.compareTo(BigDecimal.ZERO) > 0 && (cargo.getCartedPackages()
													.subtract(new BigDecimal(cargo.getStuffReqQty())))
													.compareTo(BigDecimal.ZERO) > 0) {
												BigDecimal remainingQty1 = c.getActualNoOfPackages()
														.subtract(c.getStuffedNoOfPackages());

												if (remainingQty1.compareTo(BigDecimal.ZERO) > 0) {
													BigDecimal qty = BigDecimal.ZERO;

													if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

														if (cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()))
																.compareTo(remainingQty1) > 0) {
															qty = remainingQty1;
														} else if (cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()))
																.compareTo(remainingQty1) < 0) {
															qty = cargo.getCartedPackages()
																	.subtract(new BigDecimal(cargo.getStuffReqQty()));
														} else {
															qty = remainingQty1;
														}

													} else if (remainingQty1.subtract(val)
															.compareTo(BigDecimal.ZERO) > 0) {

														if (cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()))
																.compareTo(val) > 0) {
															qty = val;
														} else if (cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()))
																.compareTo(val) < 0) {
															qty = cargo.getCartedPackages()
																	.subtract(new BigDecimal(cargo.getStuffReqQty()));
														} else {
															qty = val;
														}

													} else if (remainingQty1.subtract(val)
															.compareTo(BigDecimal.ZERO) < 0) {

														if (cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()))
																.compareTo(remainingQty1) > 0) {
															qty = remainingQty1;
														} else if (cargo.getCartedPackages()
																.subtract(new BigDecimal(cargo.getStuffReqQty()))
																.compareTo(remainingQty1) < 0) {
															qty = cargo.getCartedPackages()
																	.subtract(new BigDecimal(cargo.getStuffReqQty()));
														} else {
															qty = remainingQty1;
														}

													}

													BigDecimal area = (c.getAreaOccupied().multiply(qty))
															.divide(c.getYardPackages());

													ExportStuffTally newTally = new ExportStuffTally();

													int sr = exportstufftallyrepo.countOfStuffRecords(cid, bid,
															e.getStuffTallyId());

													newTally.setCompanyId(cid);
													newTally.setBranchId(bid);
													newTally.setStuffTallyId(e.getStuffTallyId());
													newTally.setSbTransId(c.getSbTransId());
													newTally.setStuffTallyLineId(sr + 1);
													newTally.setProfitcentreId(c.getProfitcentreId());
													newTally.setCartingTransId(c.getCartingTransId());
													newTally.setCartingLineId(c.getCartingLineId());
													newTally.setSbLineId(c.getSbLineNo());
													newTally.setSbNo(c.getSbNo());
													newTally.setStuffTallyWoTransId(
															singleTally.getStuffTallyWoTransId());
													newTally.setStuffTallyCutWoTransDate(
															singleTally.getStuffTallyCutWoTransDate());
													newTally.setStuffTallyDate(e.getStuffTallyDate());
													newTally.setStuffId(stuffReq.getStuffReqId());
													newTally.setStuffDate(stuffReq.getStuffReqDate());
													newTally.setSbDate(cargo.getSbDate());
													newTally.setShift(singleTally.getShift());
													newTally.setAgentSealNo(e.getAgentSealNo());
													newTally.setVesselId(singleTally.getVesselId());
													newTally.setVoyageNo(singleTally.getVoyageNo());
													newTally.setRotationNo(singleTally.getRotationNo());
													newTally.setPol(sbENtry.getPol());
													newTally.setTerminal(singleTally.getTerminal());
													newTally.setPod(singleTally.getPod());
													newTally.setFinalPod(singleTally.getFinalPod());
													newTally.setContainerNo(e.getContainerNo());
													newTally.setContainerSize(e.getContainerSize());
													newTally.setPeriodFrom(singleTally.getPeriodFrom());
													newTally.setGateInId(stuffReq.getGateInId());
													newTally.setContainerStatus(e.getContainerStatus());
													newTally.setContainerType(e.getContainerType());
													newTally.setContainerCondition(stuffReq.getContainerHealth());
													newTally.setYardPackages(c.getYardPackages());
													newTally.setCellAreaAllocated(e.getCellAreaAllocated());
													newTally.setOnAccountOf(stuffReq.getOnAccountOf());
													newTally.setCha(sbENtry.getCha());
													newTally.setTotalGrossWeight(e.getCargoWeight());
													newTally.setCargoWeight(e.getCargoWeight());
													newTally.setStuffRequestQty(e.getStuffRequestQty());
													newTally.setStuffedQty(qty);
													newTally.setBalanceQty(c.getYardPackages().subtract(qty));
													newTally.setTareWeight(e.getTareWeight());
													newTally.setAreaReleased(area);
													newTally.setGenSetRequired(singleTally.getGenSetRequired());
													newTally.setHaz(singleTally.getHaz());
													newTally.setImoCode(sbENtry.getImoCode());
													newTally.setShippingAgent(stuffReq.getShippingAgent());
													newTally.setShippingLine(stuffReq.getShippingLine());
													newTally.setCommodity(singleTally.getCommodity());
													newTally.setCustomsSealNo(e.getCustomsSealNo());
													newTally.setViaNo(singleTally.getViaNo());
													newTally.setCartingDate(c.getCartingTransDate());
													newTally.setExporterName(sbENtry.getExporterName());
													newTally.setConsignee(singleTally.getConsignee());
													newTally.setFob(singleTally.getFob());
													newTally.setBerthingDate(singleTally.getBerthingDate());
													newTally.setGateOpenDate(singleTally.getGateOpenDate());
													newTally.setDocType(singleTally.getDocType());
													newTally.setDocNo(singleTally.getDocNo());
													newTally.setStatus("A");
													newTally.setCreatedBy(user);
													newTally.setCreatedDate(new Date());
													newTally.setApprovedBy(user);
													newTally.setApprovedDate(new Date());
													newTally.setDeliveryOrderNo(e.getDeliveryOrderNo());
													newTally.setStuffMode(singleTally.getStuffMode());
													newTally.setStuffLineId(stuffReq.getStuffReqLineId());
													newTally.setTypeOfPackage(cargo.getTypeOfPackage());
													newTally.setSealType(singleTally.getSealType());
													newTally.setRotationDate(singleTally.getRotationDate());

													exportstufftallyrepo.save(newTally);

													stuffReq.setStuffedQty(
															stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
																	: stuffReq.getStuffedQty().add(qty));
													stuffReq.setNoOfPackagesStuffed(
															stuffReq.getNoOfPackagesStuffed().add(qty));

													System.out.println(
															"cargo.getStuffReqQty()2 " + cargo.getStuffReqQty());
													cargo.setStuffReqQty(
															cargo.getStuffReqQty() + Integer.parseInt(qty.toString()));

													c.setStuffedNoOfPackages(c.getStuffedNoOfPackages().add(qty));

													exportcartingrepo.save(c);

													val = val.subtract(qty);

													List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid,
															c.getCartingTransId(), c.getCartingLineId());

													if (!grid.isEmpty()) {
														AtomicReference<BigDecimal> gridVal = new AtomicReference<>(
																qty);
														grid.stream().forEach(g -> {
															YardBlockCell yard = yardBlockCellRepository
																	.getYardCellByCellNo(cid, bid, g.getYardLocation(),
																			g.getYardBlock(), g.getBlockCellNo());
															BigDecimal yardPackages = BigDecimal
																	.valueOf(g.getYardPackages()); // Convert int to
																									// BigDecimal once
															BigDecimal qtyTakenOut = g.getQtyTakenOut() != null
																	? g.getQtyTakenOut()
																	: BigDecimal.ZERO;
															BigDecimal gridValue = gridVal.get();

															BigDecimal availablePackages = yardPackages
																	.subtract(qtyTakenOut);

															if (gridValue.compareTo(availablePackages) >= 0) {
																// Update QtyTakenOut to include all available packages
																g.setQtyTakenOut(qtyTakenOut.add(availablePackages));

																// Calculate the proportional area to release based on
																// the entire available amount
																BigDecimal tenArea = g.getCellAreaAllocated()
																		.multiply(availablePackages)
																		.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

																// Update AreaReleased, accounting for potential null
																// values
																BigDecimal newAreaReleased = (g
																		.getAreaReleased() != null ? g.getAreaReleased()
																				: BigDecimal.ZERO)
																		.add(tenArea);
																g.setAreaReleased(newAreaReleased);

																// Deduct the entire available packages from gridVal
																gridVal.set(gridValue.subtract(availablePackages));
																if (yard != null) {
																	yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																			? BigDecimal.ZERO
																			: yard.getCellAreaUsed())
																			.subtract(newAreaReleased));
																	yardBlockCellRepository.save(yard);
																}
															} else {
																// Partial deduction based on gridVal's amount
																g.setQtyTakenOut(qtyTakenOut.add(gridValue));

																// Calculate the proportional area based on gridVal
																BigDecimal tenArea = g.getCellAreaAllocated()
																		.multiply(gridValue)
																		.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

																// Update AreaReleased
																BigDecimal newAreaReleased = (g
																		.getAreaReleased() != null ? g.getAreaReleased()
																				: BigDecimal.ZERO)
																		.add(tenArea);
																g.setAreaReleased(newAreaReleased);

																// Set gridVal to zero after deduction
																gridVal.set(BigDecimal.ZERO);
																if (yard != null) {
																	yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																			? BigDecimal.ZERO
																			: yard.getCellAreaUsed())
																			.subtract(newAreaReleased));
																	yardBlockCellRepository.save(yard);
																}
															}

															impexpgridrepo.save(g);
														});
													}

												}
											}

										}

									}

								}

							}

							if (val.compareTo(BigDecimal.ZERO) > 0) {

								BigDecimal incrementVal = e.getStuffedQty().subtract(stuffReq.getNoOfPackagesStuffed());

								List<ExportStuffRequest> remainingStuffReq = exportstuffrepo.getDataBySbNoSbTrans(cid,
										bid, singleTally.getSbTransId(), singleTally.getSbNo(), e.getStuffId());

								if (!remainingStuffReq.isEmpty()) {
									for (ExportStuffRequest s : remainingStuffReq) {
										if (incrementVal.compareTo(BigDecimal.ZERO) != 0
												&& val.compareTo(BigDecimal.ZERO) > 0) {

											List<ExportCarting> cartingData1 = exportcartingrepo.getDataBySbNoSbTrans1(
													cid, bid, singleTally.getSbTransId(), singleTally.getSbNo());

											if (!cartingData1.isEmpty()) {
												for (ExportCarting c : cartingData1) {

													BigDecimal remainingQty1 = c.getActualNoOfPackages()
															.subtract(c.getStuffedNoOfPackages());

													if (val.compareTo(BigDecimal.ZERO) <= 0) {
														break;
													}

													BigDecimal qty = BigDecimal.ZERO;

													if (remainingQty1.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
														if (remainingQty1.compareTo(s.getNoOfPackagesStuffed()
																.subtract(s.getStuffedQty())) > 0) {
															qty = s.getNoOfPackagesStuffed()
																	.subtract(s.getStuffedQty());
														} else if (remainingQty1.compareTo(s.getNoOfPackagesStuffed()
																.subtract(s.getStuffedQty())) < 0) {
															qty = remainingQty1;
														} else {
															qty = remainingQty1;
														}

													} else if (remainingQty1.subtract(val)
															.compareTo(BigDecimal.ZERO) > 0) {
														if (val.compareTo(s.getNoOfPackagesStuffed()
																.subtract(s.getStuffedQty())) > 0) {
															qty = s.getNoOfPackagesStuffed()
																	.subtract(s.getStuffedQty());
														} else if (val.compareTo(s.getNoOfPackagesStuffed()
																.subtract(s.getStuffedQty())) < 0) {
															qty = val;
														} else {
															qty = val;
														}
													} else if (remainingQty1.subtract(val)
															.compareTo(BigDecimal.ZERO) < 0) {
														if (remainingQty1.compareTo(s.getNoOfPackagesStuffed()
																.subtract(s.getStuffedQty())) > 0) {
															qty = s.getNoOfPackagesStuffed()
																	.subtract(s.getStuffedQty());
														} else if (remainingQty1.compareTo(s.getNoOfPackagesStuffed()
																.subtract(s.getStuffedQty())) < 0) {
															qty = remainingQty1;
														} else {
															qty = remainingQty1;
														}
													}

													BigDecimal area = (c.getAreaOccupied().multiply(qty))
															.divide(c.getYardPackages());

													ExportStuffTally newTally = new ExportStuffTally();

													int sr = exportstufftallyrepo.countOfStuffRecords(cid, bid,
															e.getStuffTallyId());

													newTally.setCompanyId(cid);
													newTally.setBranchId(bid);
													newTally.setStuffTallyId(e.getStuffTallyId());
													newTally.setSbTransId(c.getSbTransId());
													newTally.setStuffTallyLineId(sr + 1);
													newTally.setProfitcentreId(c.getProfitcentreId());
													newTally.setCartingTransId(c.getCartingTransId());
													newTally.setCartingLineId(c.getCartingLineId());
													newTally.setSbLineId(c.getSbLineNo());
													newTally.setSbNo(c.getSbNo());
													newTally.setStuffTallyWoTransId(
															singleTally.getStuffTallyWoTransId());
													newTally.setStuffTallyCutWoTransDate(
															singleTally.getStuffTallyCutWoTransDate());
													newTally.setStuffTallyDate(e.getStuffTallyDate());
													newTally.setStuffId(s.getStuffReqId());
													newTally.setStuffDate(s.getStuffReqDate());
													newTally.setSbDate(cargo.getSbDate());
													newTally.setShift(singleTally.getShift());
													newTally.setAgentSealNo(e.getAgentSealNo());
													newTally.setVesselId(singleTally.getVesselId());
													newTally.setVoyageNo(singleTally.getVoyageNo());
													newTally.setRotationNo(singleTally.getRotationNo());
													newTally.setPol(sbENtry.getPol());
													newTally.setTerminal(singleTally.getTerminal());
													newTally.setPod(singleTally.getPod());
													newTally.setFinalPod(singleTally.getFinalPod());
													newTally.setContainerNo(e.getContainerNo());
													newTally.setContainerSize(e.getContainerSize());
													newTally.setPeriodFrom(singleTally.getPeriodFrom());
													newTally.setGateInId(stuffReq.getGateInId());
													newTally.setContainerStatus(e.getContainerStatus());
													newTally.setContainerType(e.getContainerType());
													newTally.setContainerCondition(stuffReq.getContainerHealth());
													newTally.setYardPackages(c.getYardPackages());
													newTally.setCellAreaAllocated(e.getCellAreaAllocated());
													newTally.setOnAccountOf(stuffReq.getOnAccountOf());
													newTally.setCha(sbENtry.getCha());
													newTally.setTotalGrossWeight(e.getCargoWeight());
													newTally.setCargoWeight(e.getCargoWeight());
													newTally.setStuffRequestQty(e.getStuffRequestQty());
													newTally.setStuffedQty(qty);
													s.setStuffedQty(s.getStuffedQty() == null ? BigDecimal.ZERO
															: s.getStuffedQty().add(qty));
													newTally.setBalanceQty(c.getYardPackages().subtract(qty));
													newTally.setTareWeight(e.getTareWeight());
													newTally.setAreaReleased(area);
													newTally.setGenSetRequired(singleTally.getGenSetRequired());
													newTally.setHaz(singleTally.getHaz());
													newTally.setImoCode(sbENtry.getImoCode());
													newTally.setShippingAgent(stuffReq.getShippingAgent());
													newTally.setShippingLine(stuffReq.getShippingLine());
													newTally.setCommodity(singleTally.getCommodity());
													newTally.setCustomsSealNo(e.getCustomsSealNo());
													newTally.setViaNo(singleTally.getViaNo());
													newTally.setCartingDate(c.getCartingTransDate());
													newTally.setExporterName(sbENtry.getExporterName());
													newTally.setConsignee(singleTally.getConsignee());
													newTally.setFob(singleTally.getFob());
													newTally.setBerthingDate(singleTally.getBerthingDate());
													newTally.setGateOpenDate(singleTally.getGateOpenDate());
													newTally.setDocType(singleTally.getDocType());
													newTally.setDocNo(singleTally.getDocNo());
													newTally.setStatus("A");
													newTally.setCreatedBy(user);
													newTally.setCreatedDate(new Date());
													newTally.setApprovedBy(user);
													newTally.setApprovedDate(new Date());
													newTally.setDeliveryOrderNo(e.getDeliveryOrderNo());
													newTally.setStuffMode(singleTally.getStuffMode());
													newTally.setStuffLineId(stuffReq.getStuffReqLineId());
													newTally.setTypeOfPackage(cargo.getTypeOfPackage());
													newTally.setSealType(singleTally.getSealType());
													newTally.setRotationDate(singleTally.getRotationDate());

													exportstufftallyrepo.save(newTally);

													exportstuffrepo.save(s);

													c.setStuffedNoOfPackages(
															(c.getStuffedNoOfPackages() == null ? BigDecimal.ZERO
																	: c.getStuffedNoOfPackages()).add(qty));

													exportcartingrepo.save(c);

													val = val.subtract(qty);

													List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid,
															c.getCartingTransId(), c.getCartingLineId());

													if (!grid.isEmpty()) {
														AtomicReference<BigDecimal> gridVal = new AtomicReference<>(
																qty);
														grid.stream().forEach(g -> {
															YardBlockCell yard = yardBlockCellRepository
																	.getYardCellByCellNo(cid, bid, g.getYardLocation(),
																			g.getYardBlock(), g.getBlockCellNo());
															BigDecimal yardPackages = BigDecimal
																	.valueOf(g.getYardPackages()); // Convert int to
																									// BigDecimal once
															BigDecimal qtyTakenOut = g.getQtyTakenOut() != null
																	? g.getQtyTakenOut()
																	: BigDecimal.ZERO;
															BigDecimal gridValue = gridVal.get();

															BigDecimal availablePackages = yardPackages
																	.subtract(qtyTakenOut);

															if (gridValue.compareTo(availablePackages) >= 0) {
																// Update QtyTakenOut to include all available packages
																g.setQtyTakenOut(qtyTakenOut.add(availablePackages));

																// Calculate the proportional area to release based on
																// the entire available amount
																BigDecimal tenArea = g.getCellAreaAllocated()
																		.multiply(availablePackages)
																		.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

																// Update AreaReleased, accounting for potential null
																// values
																BigDecimal newAreaReleased = (g
																		.getAreaReleased() != null ? g.getAreaReleased()
																				: BigDecimal.ZERO)
																		.add(tenArea);
																g.setAreaReleased(newAreaReleased);

																// Deduct the entire available packages from gridVal
																gridVal.set(gridValue.subtract(availablePackages));
																if (yard != null) {
																	yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																			? BigDecimal.ZERO
																			: yard.getCellAreaUsed())
																			.subtract(newAreaReleased));
																	yardBlockCellRepository.save(yard);
																}
															} else {
																// Partial deduction based on gridVal's amount
																g.setQtyTakenOut(qtyTakenOut.add(gridValue));

																// Calculate the proportional area based on gridVal
																BigDecimal tenArea = g.getCellAreaAllocated()
																		.multiply(gridValue)
																		.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

																// Update AreaReleased
																BigDecimal newAreaReleased = (g
																		.getAreaReleased() != null ? g.getAreaReleased()
																				: BigDecimal.ZERO)
																		.add(tenArea);
																g.setAreaReleased(newAreaReleased);

																// Set gridVal to zero after deduction
																gridVal.set(BigDecimal.ZERO);
																if (yard != null) {
																	yard.setCellAreaUsed((yard.getCellAreaUsed() == null
																			? BigDecimal.ZERO
																			: yard.getCellAreaUsed())
																			.subtract(newAreaReleased));
																	yardBlockCellRepository.save(yard);
																}
															}

															impexpgridrepo.save(g);
														});
													}
												}
											}

										}

									}
								}
							}

						} else if (totalStuffedQty.compareTo(e.getStuffedQty()) > 0) {
							BigDecimal remainingQty = existCar.getStuffedNoOfPackages();

							if (val.compareTo(BigDecimal.ZERO) > 0) {

								if (exist.getStuffId().equals(e.getStuffId())) {
									BigDecimal qty = BigDecimal.ZERO;

									if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

										if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (val.compareTo(exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (val.compareTo(exist.getStuffedQty()) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}
									}

									BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
											.divide(existCar.getYardPackages());

									exist.setStuffedQty(exist.getStuffedQty().subtract(qty));
									exist.setBalanceQty(exist.getBalanceQty().add(qty));
									exist.setAreaReleased(exist.getAreaReleased().subtract(area));
									stuffReq.setStuffedQty(stuffReq.getStuffedQty() == null ? BigDecimal.ZERO
											: stuffReq.getStuffedQty().subtract(qty));

									val = val.subtract(qty);

									existCar.setStuffedNoOfPackages(existCar.getStuffedNoOfPackages().subtract(qty));

									exportcartingrepo.save(existCar);

									List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
											existCar.getCartingTransId(), existCar.getCartingLineId());

									if (!grid.isEmpty()) {
										AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
										grid.stream().forEach(g -> {
											BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages()); // Convert
																												// int
											YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
													g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo()); // to
											// BigDecimal
											// once
											BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut()
													: BigDecimal.ZERO;
											BigDecimal gridValue = gridVal.get();

											if (gridValue.compareTo(qtyTakenOut) >= 0) {
												// Full deduction of qtyTakenOut from gridVal
												g.setQtyTakenOut(BigDecimal.ZERO); // Set qtyTakenOut to zero after
																					// fully deducting it

												// Calculate area to be released based on the amount in qtyTakenOut
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(qtyTakenOut)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Safely subtract the calculated tenArea from AreaReleased, handling
												// null
												BigDecimal newAreaReleased = (g.getAreaReleased() != null
														? g.getAreaReleased()
														: BigDecimal.ZERO).subtract(tenArea);
												g.setAreaReleased(newAreaReleased);

												// Deduct qtyTakenOut amount from gridVal
												gridVal.set(gridValue.subtract(qtyTakenOut));
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed())
																	.add(newAreaReleased));
													yardBlockCellRepository.save(yard);
												}
											} else {
												// Partial deduction from gridVal based on gridVal’s amount
												g.setQtyTakenOut(qtyTakenOut.subtract(gridValue));

												// Calculate area to release based on gridVal
												BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
														.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

												// Update AreaReleased by subtracting tenArea
												BigDecimal newAreaReleased = (g.getAreaReleased() != null
														? g.getAreaReleased()
														: BigDecimal.ZERO).subtract(tenArea);
												g.setAreaReleased(newAreaReleased);

												// Set gridVal to zero after full deduction
												gridVal.set(BigDecimal.ZERO);
												if (yard != null) {
													yard.setCellAreaUsed(
															(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																	: yard.getCellAreaUsed())
																	.add(newAreaReleased));
													yardBlockCellRepository.save(yard);
												}
											}

											impexpgridrepo.save(g);
										});
									}
								}

								else {

									BigDecimal qty = BigDecimal.ZERO;

									ExportStuffRequest req = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId3(cid,
											bid, singleTally.getSbTransId(), singleTally.getSbNo(), exist.getStuffId());

									if (req != null) {
										if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

											if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
												qty = exist.getStuffedQty();
											} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
												qty = remainingQty;
											} else {
												qty = remainingQty;
											}

										} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

											if (val.compareTo(exist.getStuffedQty()) > 0) {
												qty = exist.getStuffedQty();
											} else if (val.compareTo(exist.getStuffedQty()) < 0) {
												qty = val;
											} else {
												qty = val;
											}

										} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
											if (remainingQty.compareTo(exist.getStuffedQty()) > 0) {
												qty = exist.getStuffedQty();
											} else if (remainingQty.compareTo(exist.getStuffedQty()) < 0) {
												qty = remainingQty;
											} else {
												qty = remainingQty;
											}
										}

										BigDecimal area = (existCar.getAreaOccupied().multiply(qty))
												.divide(existCar.getYardPackages());

										exist.setStuffedQty(exist.getStuffedQty().subtract(qty));
										exist.setBalanceQty(exist.getBalanceQty().add(qty));
										exist.setAreaReleased(exist.getAreaReleased().subtract(area));
										req.setStuffedQty(req.getStuffedQty() == null ? BigDecimal.ZERO
												: req.getStuffedQty().subtract(qty));

										exportstuffrepo.save(req);

										val = val.subtract(qty);

										existCar.setStuffedNoOfPackages(
												existCar.getStuffedNoOfPackages().subtract(qty));

										exportcartingrepo.save(existCar);

										List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid,
												existCar.getCartingTransId(), existCar.getCartingLineId());

										if (!grid.isEmpty()) {
											AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
											grid.stream().forEach(g -> {
												BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages()); // Convert
																													// int
												YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid,
														bid, g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo()); // to
												// BigDecimal
												// once
												BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut()
														: BigDecimal.ZERO;
												BigDecimal gridValue = gridVal.get();

												if (gridValue.compareTo(qtyTakenOut) >= 0) {
													// Full deduction of qtyTakenOut from gridVal
													g.setQtyTakenOut(BigDecimal.ZERO); // Set qtyTakenOut to zero after
																						// fully deducting it

													// Calculate area to be released based on the amount in qtyTakenOut
													BigDecimal tenArea = g.getCellAreaAllocated().multiply(qtyTakenOut)
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Safely subtract the calculated tenArea from AreaReleased,
													// handling null
													BigDecimal newAreaReleased = (g.getAreaReleased() != null
															? g.getAreaReleased()
															: BigDecimal.ZERO).subtract(tenArea);
													g.setAreaReleased(newAreaReleased);

													// Deduct qtyTakenOut amount from gridVal
													gridVal.set(gridValue.subtract(qtyTakenOut));
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.add(newAreaReleased));
														yardBlockCellRepository.save(yard);
													}
												} else {
													// Partial deduction from gridVal based on gridVal’s amount
													g.setQtyTakenOut(qtyTakenOut.subtract(gridValue));

													// Calculate area to release based on gridVal
													BigDecimal tenArea = g.getCellAreaAllocated().multiply(gridValue)
															.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

													// Update AreaReleased by subtracting tenArea
													BigDecimal newAreaReleased = (g.getAreaReleased() != null
															? g.getAreaReleased()
															: BigDecimal.ZERO).subtract(tenArea);
													g.setAreaReleased(newAreaReleased);

													// Set gridVal to zero after full deduction
													gridVal.set(BigDecimal.ZERO);
													if (yard != null) {
														yard.setCellAreaUsed(
																(yard.getCellAreaUsed() == null ? BigDecimal.ZERO
																		: yard.getCellAreaUsed())
																		.add(newAreaReleased));
														yardBlockCellRepository.save(yard);
													}
												}

												impexpgridrepo.save(g);
											});
										}
									}
								}
							}

						}

						exportstufftallyrepo.save(exist);
					}

				}

				stuffReq.setAgentSealNo(e.getAgentSealNo());
				stuffReq.setTareWeight(e.getTareWeight());
				stuffReq.setRotationNo(singleTally.getRotationNo());
				stuffReq.setRotationDate(singleTally.getRotationDate());
				stuffReq.setBerthingDate(singleTally.getBerthingDate());
				stuffReq.setGateOpenDate(singleTally.getGateOpenDate());
				stuffReq.setTypeOfPackage(cargo.getTypeOfPackage());
				stuffReq.setVesselId(singleTally.getVesselId());
				stuffReq.setVesselName(singleTally.getVesselName());
				stuffReq.setVoyageNo(singleTally.getVoyageNo());
				stuffReq.setViaNo(singleTally.getViaNo());
				stuffReq.setEditedBy(user);
				stuffReq.setEditedDate(new Date());

				exportstuffrepo.save(stuffReq);

				if (stuffReq.getNoOfPackagesStuffed().compareTo(existData.getStuffRequestQty()) > 0) {
					int updateQty = exportstufftallyrepo.updateStuffReqQuantity(cid, bid, existData.getStuffTallyId(),
							stuffReq.getStuffReqId(), stuffReq.getNoOfPackagesStuffed(), singleTally.getSbTransId(),
							singleTally.getSbNo());

				}

				cargo.setStuffedQty(((cargo.getStuffedQty() == null ? BigDecimal.ZERO : cargo.getStuffedQty())
						.add(e.getStuffedQty().compareTo(cargo.getCartedPackages()) > 0 ? cargo.getCartedPackages()
								: e.getStuffedQty()))
						.subtract(existStuffQty));
				cargo.setStuffedWt(((cargo.getStuffedWt() == null ? BigDecimal.ZERO : cargo.getStuffedWt())
						.add(e.getCargoWeight())).subtract(existData.getCargoWeight()));
				cargo.setCargoType(e.getCargoType());

				exportsbcargorepo.save(cargo);

				if (!e.getAgentSealNo().equals(existData.getAgentSealNo())) {
					checkAndUpdateAudit1(cid, bid, user, "Agent Seal No", existData.getAgentSealNo(),
							e.getAgentSealNo(), existData);

				}

				if (!e.getCustomsSealNo().equals(existData.getCustomsSealNo())) {
					checkAndUpdateAudit1(cid, bid, user, "Customs Seal No", existData.getCustomsSealNo(),
							e.getCustomsSealNo(), existData);

				}

				if (e.getStuffedQty().compareTo(existStuffQty) != 0) {
					checkAndUpdateAudit1(cid, bid, user, "Stuffed Quantity", existStuffQty.toString(),
							e.getStuffedQty().toString(), existData);

				}

				if (e.getCargoWeight().compareTo(existData.getCargoWeight()) != 0) {
					checkAndUpdateAudit1(cid, bid, user, "Stuff Tally Wt", existData.getCargoWeight().toString(),
							e.getCargoWeight().toString(), existData);

				}

				if (e.getTareWeight().compareTo(existData.getTareWeight()) != 0) {
					checkAndUpdateAudit1(cid, bid, user, "Tare Wt.", existData.getTareWeight().toString(),
							e.getTareWeight().toString(), existData);

				}

				if (!e.getCargoType().equals(cargoType)) {
					checkAndUpdateAudit1(cid, bid, user, "Cargo Type", cargoType, e.getCargoType(), existData);

				}
				if (!singleTally.getMovementType().equals(existData.getMovementType())) {
					checkAndUpdateAudit1(cid, bid, user, "Movement Type", existData.getMovementType(),
							singleTally.getMovementType(), existData);

				}

				if (!singleTally.getVesselId().equals(existData.getVesselId())) {
					checkAndUpdateAudit1(cid, bid, user, "Vessel", existData.getVesselId(), singleTally.getVesselId(),
							existData);

				}

				if (!singleTally.getVoyageNo().equals(existData.getVoyageNo())) {
					checkAndUpdateAudit1(cid, bid, user, "Voyage No", existData.getVoyageNo(),
							singleTally.getVoyageNo(), existData);

				}

				if (!singleTally.getViaNo().equals(existData.getViaNo())) {
					checkAndUpdateAudit1(cid, bid, user, "Via No", existData.getViaNo(), singleTally.getViaNo(),
							existData);

				}

				if (!singleTally.getTerminal().equals(existData.getTerminal())) {
					checkAndUpdateAudit1(cid, bid, user, "Terminal Name", existData.getTerminal(),
							singleTally.getTerminal(), existData);

				}

				if (!singleTally.getRotationNo().equals(existData.getRotationNo())) {
					checkAndUpdateAudit1(cid, bid, user, "Rotation No", existData.getRotationNo(),
							singleTally.getRotationNo(), existData);

				}

				if (!singleTally.getPod().equals(existData.getPod())) {
					checkAndUpdateAudit1(cid, bid, user, "Port Of Discharge", existData.getPod(), singleTally.getPod(),
							existData);

				}

				if (!singleTally.getFinalPod().equals(existData.getFinalPod())) {
					checkAndUpdateAudit1(cid, bid, user, "Final POD", existData.getFinalPod(),
							singleTally.getFinalPod(), existData);

				}

				if (!singleTally.getStuffMode().equals(existData.getStuffMode())) {
					checkAndUpdateAudit1(cid, bid, user, "Stuff Mode", existData.getStuffMode(),
							singleTally.getStuffMode(), existData);

				}

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				if ((singleTally.getRotationDate() != null
						&& !singleTally.getRotationDate().equals(existData.getRotationDate()))
						|| (singleTally.getRotationDate() == null && existData.getRotationDate() != null)) {
					String oldRotationDate = existData.getRotationDate() != null
							? dateFormat.format(existData.getRotationDate())
							: null;
					String newRotationDate = singleTally.getRotationDate() != null
							? dateFormat.format(singleTally.getRotationDate())
							: null;

					checkAndUpdateAudit1(cid, bid, user, "Rotation Date", oldRotationDate, newRotationDate, existData);
				}

				if ((singleTally.getBerthingDate() != null
						&& !singleTally.getBerthingDate().equals(existData.getBerthingDate()))
						|| (singleTally.getBerthingDate() == null && existData.getBerthingDate() != null)) {
					String oldBerthingDate = existData.getBerthingDate() != null
							? dateFormat.format(existData.getBerthingDate())
							: null;
					String newBerthingDate = singleTally.getBerthingDate() != null
							? dateFormat.format(singleTally.getBerthingDate())
							: null;

					checkAndUpdateAudit1(cid, bid, user, "Berthing Date", oldBerthingDate, newBerthingDate, existData);
				}

			}

		}

		List<ExportStuffTally> existData = exportstufftallyrepo.getDataBySbNo(cid, bid, singleTally.getSbTransId(),
				singleTally.getSbNo());

		if (existData.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(existData, HttpStatus.OK);

	}

	public void checkAndUpdateAudit1(String cid, String bid, String user, String field, String oldValue,
			String newValue, ExportStuffTally existingData) {

		// Generate next ID for the audit record
		String holdId = processnextidrepo.findAuditTrail(cid, bid, "P05085", "2024");
		int lastNumericId = Integer.parseInt(holdId.substring(4));
		int nextNumericId = lastNumericId + 1;
		String nextAuditId = String.format("EXPA%06d", nextNumericId);

		// Create new audit record
		ExportAudit audit = new ExportAudit();
		audit.setCompanyId(cid);
		audit.setBranchId(bid);
		audit.setApprovedBy(user);
		audit.setApprovedDate(new Date());
		audit.setAuditDate(new Date());
		audit.setAuditId(nextAuditId);
		audit.setContainerNo(existingData.getContainerNo());
		audit.setCreatedBy(user);
		audit.setCreatedDate(new Date());
		audit.setField(field);
		audit.setProfitcentreId(existingData.getProfitcentreId());
		audit.setSbNo(existingData.getSbNo());
		audit.setStatus("A");
		audit.setTableName("SB Wise Tally/CLP");
		audit.setOldValue(oldValue);
		audit.setNewValue(newValue);
		exportauditrepo.save(audit);

		// Update process ID
		processnextidrepo.updateAuditTrail(cid, bid, "P05085", nextAuditId, "2024");

	}

}
