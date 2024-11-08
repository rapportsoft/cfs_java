package com.cwms.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
							if (gridVal.get().compareTo(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())) >= 0) {

								g.setQtyTakenOut(
										(g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
												.add(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())));
								
								BigDecimal tenArea = (g.getCellAreaAllocated().multiply(new BigDecimal(g.getYardPackages())
										.subtract(g.getQtyTakenOut())))
										.divide(new BigDecimal(g.getYardPackages()),
												BigDecimal.ROUND_HALF_UP);
								
								g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
										: g.getAreaReleased()).add(tenArea));

								gridVal.set(gridVal.get().subtract(
										(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut()))));
							} else {

								g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
										.add(gridVal.get()));
								BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
										.divide(new BigDecimal(g.getYardPackages()), BigDecimal.ROUND_HALF_UP);
								g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO : g.getAreaReleased())
										.add(tenArea));

								gridVal.set(gridVal.get()
										.subtract((g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
												.add(gridVal.get())));
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
			
			System.out.println("rotation "+singleTally.getRotationNo()+" "+e3.getRotationNo());

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
				BigDecimal existAreaRelease = data1.stream().map(ExportStuffTally::getAreaReleased).reduce(BigDecimal.ZERO,BigDecimal::add);
				String existtype = e3.getTypeOfPackage();

			

				ExportStuffRequest stuffReq = exportstuffrepo.getDataBySbNoSbTransAndStuffReqLineId3(cid, bid,
						t.getSbTransId(), t.getSbNo(), singleTally.getStuffId());

				if (stuffReq == null) {
					return new ResponseEntity<>("Stuffing request data not found", HttpStatus.CONFLICT);
				}

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
								
								System.out.println("remainingQty "+remainingQty+" "+val+" "+stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty()));

								if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {

									if (remainingQty.compareTo(
											exist.getStuffedQty()) > 0) {
										qty = exist.getStuffedQty();
									} else if (remainingQty.compareTo(
											exist.getStuffedQty()) < 0) {
										qty = remainingQty;
									} else {
										qty = remainingQty;
									}

								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

									if (val.compareTo(
											exist.getStuffedQty()) > 0) {
										qty = exist.getStuffedQty();
									} else if (val.compareTo(
											exist.getStuffedQty()) < 0) {
										qty = val;
									} else {
										qty = val;
									}

								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
									if (remainingQty.compareTo(
											exist.getStuffedQty()) > 0) {
										qty = exist.getStuffedQty();
									} else if (remainingQty.compareTo(
											exist.getStuffedQty()) < 0) {
										qty = remainingQty;
									} else {
										qty = remainingQty;
									}
								}
								
								System.out.println("less qty "+qty);

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
										if (gridVal.get().compareTo(g.getQtyTakenOut()) >= 0) {

											g.setQtyTakenOut(
													(g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
															.subtract(g.getQtyTakenOut()));
											
											BigDecimal tenArea = (g.getCellAreaAllocated().multiply(g.getQtyTakenOut()))
													.divide(new BigDecimal(g.getYardPackages()),
															BigDecimal.ROUND_HALF_UP);
											
											g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
													: g.getAreaReleased()).subtract(tenArea));

											gridVal.set(gridVal.get().subtract(g.getQtyTakenOut()));
										} else {

											g.setQtyTakenOut(
													(g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
															.subtract(gridVal.get()));
											BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
													.divide(new BigDecimal(g.getYardPackages()),
															BigDecimal.ROUND_HALF_UP);
											g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
													: g.getAreaReleased()).subtract(tenArea));

											gridVal.set(gridVal.get().subtract(gridVal.get()));
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

										if (remainingQty.compareTo(
												exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (remainingQty.compareTo(
												exist.getStuffedQty()) < 0) {
											qty = remainingQty;
										} else {
											qty = remainingQty;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {

										if (val.compareTo(
												exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (val.compareTo(
												exist.getStuffedQty()) < 0) {
											qty = val;
										} else {
											qty = val;
										}

									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if (remainingQty.compareTo(
												exist.getStuffedQty()) > 0) {
											qty = exist.getStuffedQty();
										} else if (remainingQty.compareTo(
												exist.getStuffedQty()) < 0) {
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
											if (gridVal.get().compareTo(g.getQtyTakenOut()) >= 0) {

												g.setQtyTakenOut(BigDecimal.ZERO);
												
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(g.getQtyTakenOut()))
														.divide(new BigDecimal(g.getYardPackages()),
																BigDecimal.ROUND_HALF_UP);
												
												g.setAreaReleased(BigDecimal.ZERO);

												gridVal.set(gridVal.get().subtract(g.getQtyTakenOut()));
											} else {

												g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
														: g.getQtyTakenOut()).subtract(gridVal.get()));
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
														.divide(new BigDecimal(g.getYardPackages()),
																BigDecimal.ROUND_HALF_UP);
												g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
														: g.getAreaReleased()).subtract(tenArea));

												gridVal.set(gridVal.get().subtract(gridVal.get()));
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
											if (gridVal.get().compareTo(new BigDecimal(g.getYardPackages())
													.subtract(g.getQtyTakenOut())) >= 0) {

												g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
														: g.getQtyTakenOut())
														.add(new BigDecimal(g.getYardPackages())
																.subtract(g.getQtyTakenOut())));
												
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(new BigDecimal(g.getYardPackages())
														.subtract(g.getQtyTakenOut())))
														.divide(new BigDecimal(g.getYardPackages()),
																BigDecimal.ROUND_HALF_UP);
												
												g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
														: g.getAreaReleased())
														.add(tenArea));

												gridVal.set(gridVal.get().subtract(new BigDecimal(g.getYardPackages())
														.subtract(g.getQtyTakenOut())));
											} else {

												g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
														: g.getQtyTakenOut()).add(gridVal.get()));
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
														.divide(new BigDecimal(g.getYardPackages()),
																BigDecimal.ROUND_HALF_UP);
												g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
														: g.getAreaReleased()).add(tenArea));

												gridVal.set(gridVal.get().subtract(gridVal.get()));
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
												if (gridVal.get().compareTo(new BigDecimal(g.getYardPackages())
														.subtract(g.getQtyTakenOut())) >= 0) {

													g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
															: g.getQtyTakenOut())
															.add(new BigDecimal(g.getYardPackages())
																	.subtract(g.getQtyTakenOut())));
													
													BigDecimal tenArea = (g.getCellAreaAllocated().multiply(new BigDecimal(g.getYardPackages())
															.subtract(g.getQtyTakenOut())))
															.divide(new BigDecimal(g.getYardPackages()),
																	BigDecimal.ROUND_HALF_UP);
													
													g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
															: g.getAreaReleased())
															.add(tenArea));

													gridVal.set(
															gridVal.get().subtract(new BigDecimal(g.getYardPackages())
																	.subtract(g.getQtyTakenOut())));
												} else {

													g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
															: g.getQtyTakenOut()).add(gridVal.get()));
													BigDecimal tenArea = (g.getCellAreaAllocated()
															.multiply(gridVal.get()))
															.divide(new BigDecimal(g.getYardPackages()),
																	BigDecimal.ROUND_HALF_UP);
													g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
															: g.getAreaReleased()).add(tenArea));

													gridVal.set(gridVal.get().subtract(gridVal.get()));
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
							if (stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())
									.compareTo(BigDecimal.ZERO) > 0) {
								BigDecimal remainingQty = c.getActualNoOfPackages()
										.subtract(c.getStuffedNoOfPackages());

								if (val.compareTo(BigDecimal.ZERO) <= 0) {
									break;
								}

								BigDecimal qty = BigDecimal.ZERO;

								if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) == 0) {
									
									if(remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())) > 0){
										qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
									}
									else if(remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())) < 0) {
										qty = remainingQty;
									}
									else {
										qty = remainingQty;
									}
									
									
								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
									if(val.compareTo(stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())) > 0){
										qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
									}
									else if(val.compareTo(stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())) < 0) {
										qty = val;
									}
									else {
										qty = val;
									}
									
								} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {

							
									
									
									if(remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())) > 0){
										qty = stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty());
									}
									else if(remainingQty.compareTo(stuffReq.getNoOfPackagesStuffed().subtract(stuffReq.getStuffedQty())) < 0) {
										qty = remainingQty;
									}
									else {
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

								List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid, c.getCartingTransId(),
										c.getCartingLineId());

								if (!grid.isEmpty()) {
									AtomicReference<BigDecimal> gridVal = new AtomicReference<>(qty);
									grid.stream().forEach(g -> {
										if (gridVal.get().compareTo(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())) >= 0) {

											g.setQtyTakenOut(
													(g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
															.add(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())));
											
											BigDecimal tenArea = (g.getCellAreaAllocated().multiply(new BigDecimal(g.getYardPackages())
													.subtract(g.getQtyTakenOut())))
													.divide(new BigDecimal(g.getYardPackages()),
															BigDecimal.ROUND_HALF_UP);
											
											g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
													: g.getAreaReleased()).add(tenArea));

											gridVal.set(gridVal.get().subtract(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())));
										} else {

											g.setQtyTakenOut(
													(g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
															.add(gridVal.get()));
											BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
													.divide(new BigDecimal(g.getYardPackages()),
															BigDecimal.ROUND_HALF_UP);
											g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
													: g.getAreaReleased()).add(tenArea));

											gridVal.set(gridVal.get().subtract(gridVal.get()));
										}

										impexpgridrepo.save(g);
									});
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
										if(remainingQty.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) > 0) {
											qty = e.getNoOfPackagesStuffed().subtract(e.getStuffedQty());
										}
										else if(remainingQty.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) < 0) {
											qty = remainingQty;
										}
										else {
											qty = remainingQty;
										}
										
									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) > 0) {
										if(val.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) > 0) {
											qty = e.getNoOfPackagesStuffed().subtract(e.getStuffedQty());
										}
										else if(val.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) < 0) {
											qty = val;
										}
										else {
											qty = val;
										}
									} else if (remainingQty.subtract(val).compareTo(BigDecimal.ZERO) < 0) {
										if(remainingQty.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) > 0) {
											qty = e.getNoOfPackagesStuffed().subtract(e.getStuffedQty());
										}
										else if(remainingQty.compareTo(e.getNoOfPackagesStuffed().subtract(e.getStuffedQty())) < 0) {
											qty = remainingQty;
										}
										else {
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
											if (gridVal.get().compareTo(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())) >= 0) {

												g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
														: g.getQtyTakenOut()).add(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())));
												
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(new BigDecimal(g.getYardPackages())
														.subtract(g.getQtyTakenOut())))
														.divide(new BigDecimal(g.getYardPackages()),
																BigDecimal.ROUND_HALF_UP);
												
												g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
														: g.getAreaReleased()).add(tenArea));

												gridVal.set(gridVal.get()
														.subtract(new BigDecimal(g.getYardPackages()).subtract(g.getQtyTakenOut())));
											} else {

												g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO
														: g.getQtyTakenOut()).add(gridVal.get()));
												BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
														.divide(new BigDecimal(g.getYardPackages()),
																BigDecimal.ROUND_HALF_UP);
												g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO
														: g.getAreaReleased()).add(tenArea));

												gridVal.set(gridVal.get()
														.subtract(gridVal.get()));
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
				System.out.println("rotation "+singleTally.getRotationNo()+" "+e3.getRotationNo());

				if (!singleTally.getRotationNo().equals(e3.getRotationNo())) {
					System.out.println("rotation "+singleTally.getRotationNo()+" "+e3.getRotationNo());
					checkAndUpdateAudit(cid, bid, user, "Rotation No", e3.getRotationNo(), singleTally.getRotationNo(),
							e3);

				}

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

				if (!singleTally.getRotationDate().equals(e3.getRotationDate())) {
					String oldRotationDate = e3.getRotationDate() != null ? dateFormat.format(e3.getRotationDate())
							: null;
					String newRotationDate = singleTally.getRotationDate() != null
							? dateFormat.format(singleTally.getRotationDate())
							: null;

					checkAndUpdateAudit(cid, bid, user, "Rotation Date", oldRotationDate, newRotationDate, e3);

				}

				if (!singleTally.getBerthingDate().equals(e3.getBerthingDate())) {
					String oldRotationDate = e3.getBerthingDate() != null ? dateFormat.format(e3.getBerthingDate())
							: null;
					String newRotationDate = singleTally.getBerthingDate() != null
							? dateFormat.format(singleTally.getBerthingDate())
							: null;

					checkAndUpdateAudit(cid, bid, user, "Berthing Date", oldRotationDate, newRotationDate, e3);

				}

				if (!singleTally.getGateOpenDate().equals(e3.getGateOpenDate())) {
					String oldRotationDate = e3.getGateOpenDate() != null ? dateFormat.format(e3.getGateOpenDate())
							: null;
					String newRotationDate = singleTally.getGateOpenDate() != null
							? dateFormat.format(singleTally.getGateOpenDate())
							: null;

					checkAndUpdateAudit(cid, bid, user, "Gate Open Date", oldRotationDate, newRotationDate, e3);

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
			throws JsonMappingException, JsonProcessingException {
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

			List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTrans(cid, bid,
					singleTally.getSbTransId(), singleTally.getSbNo());

			if (cartingData.isEmpty()) {
				return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
			}

			ExportStuffRequest stuffReq = exportstuffrepo.getDataBySbNoSbTransAndStuffReqId(cid, bid,
					singleTally.getSbTransId(), singleTally.getSbNo(), e.getStuffId());

			if (stuffReq == null) {
				return new ResponseEntity<>("Stuffing request data not found", HttpStatus.CONFLICT);
			}

			if (e.getStuffTallyId().isEmpty() || e.getStuffTallyId() == null) {

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
							if (gridVal.get().compareTo(new BigDecimal(g.getYardPackages())) >= 0) {

								g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
										.add(new BigDecimal(g.getYardPackages())));
								g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO : g.getAreaReleased())
										.add(g.getCellAreaAllocated()));

								gridVal.set(gridVal.get()
										.subtract((g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
												.add(new BigDecimal(g.getYardPackages()))));
							} else {

								g.setQtyTakenOut((g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
										.add(gridVal.get()));
								BigDecimal tenArea = (g.getCellAreaAllocated().multiply(gridVal.get()))
										.divide(new BigDecimal(g.getYardPackages()), BigDecimal.ROUND_HALF_UP);
								g.setAreaReleased((g.getAreaReleased() == null ? BigDecimal.ZERO : g.getAreaReleased())
										.add(tenArea));

								gridVal.set(gridVal.get()
										.subtract((g.getQtyTakenOut() == null ? BigDecimal.ZERO : g.getQtyTakenOut())
												.add(gridVal.get())));
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

				exportstuffrepo.save(stuffReq);

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
			}

		}
		List<ExportStuffTally> existData = exportstufftallyrepo.getDataBySbNo(cid, bid, singleTally.getSbTransId(),
				singleTally.getSbNo());

		if (existData.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(existData, HttpStatus.OK);

	}

}
