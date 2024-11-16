package com.cwms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.ExportBackToTown;
import com.cwms.entities.ExportGatePass;
import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportMovement;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.GateOut;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.ExportBackToTownRepo;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportGatePassRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportMovementRepo;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateOutRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("/exportGatePass")
public class ExportGatePassController {

	@Autowired
	private ExportGatePassRepo exportgatepassrepo;

	@Autowired
	private GateOutRepo gateoutrepo;

	@Autowired
	private ExportMovementRepo exportMovementRepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private ExportInventoryRepository exportinvrepo;

	@Autowired
	private ExportEntryRepo sbentryrepo;

	@Autowired
	private ExportSbCargoEntryRepo exportsbcargoentryrepo;

	@Autowired
	private ExportStuffTallyRepo exportStuffTallyRepo;

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;

	@Autowired
	private ExportBackToTownRepo exportbacktotownrepo;

	@GetMapping("/getEmptyVehicle")
	public ResponseEntity<?> getEmptyVehicleForGatePass(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "veh", required = false) String veh) {

		List<Object[]> vehData = vehicleTrackRepo.getEmptyVehGateIn2(cid, bid, veh, "N00004");

		if (vehData.isEmpty()) {
			return new ResponseEntity<>("Vehicle not found...", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(vehData, HttpStatus.OK);
	}

	@GetMapping("/getMovementData")
	public ResponseEntity<?> getMovementData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = exportMovementRepo.getDataForGatePass(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSelectedMovementData")
	public ResponseEntity<?> getSelectedMovementData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("reqId") String reqId, @RequestParam("con") String con) {

		ExportMovement data = exportMovementRepo.getSingleDataForGatePass(cid, bid, reqId, con);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveGatePass")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		ExportGatePass gatePass = mapper.readValue(mapper.writeValueAsString(data.get("gatePassData")),
				ExportGatePass.class);

		List<ExportGatePass> multipleGatePass = mapper.readValue(
				mapper.writeValueAsString(data.get("multipleGatePassData")), new TypeReference<List<ExportGatePass>>() {
				});

		if (gatePass == null) {
			return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05086", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("EXGP%06d", nextNumericNextID1);

		if (gatePass.getGatePassId().isEmpty() || gatePass.getGatePassId() == null) {
			int sr = 1;

			for (ExportGatePass g : multipleGatePass) {

				ExportSbEntry sb = sbentryrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, g.getSbNo(),
						g.getSbTransId());

				if (sb == null) {
					return new ResponseEntity<>("SB data not found.", HttpStatus.CONFLICT);
				}

				ExportSbCargoEntry cargo = exportsbcargoentryrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
						g.getSbNo(), g.getSbTransId());

				if (cargo == null) {
					return new ResponseEntity<>("SB cargo data not found.", HttpStatus.CONFLICT);
				}

				if ("CONT".equals(gatePass.getTransType())) {
					ExportMovement mov = exportMovementRepo.getSingleDataForGatePass1(cid, bid, g.getMovementReqId(),
							g.getContainerNo());

					if (mov == null) {
						return new ResponseEntity<>("Movement data not found.", HttpStatus.CONFLICT);
					}

					g.setCompanyId(cid);
					g.setBranchId(bid);
					g.setGatePassId(HoldNextIdD1);
					g.setGatePassDate(new Date());
					g.setStatus("A");
					g.setTransType(gatePass.getTransType());
					g.setMovementReqId(g.getMovementReqId());
					g.setProfitcentreId("N00004");
					g.setVehicleId(gatePass.getVehicleId());
					g.setVehicleNo(gatePass.getVehicleNo());
					g.setTransporter(gatePass.getTransporter());
					g.setTransporterName(gatePass.getTransporterName());
					g.setComments(gatePass.getComments());
					g.setStatus("A");
					g.setCreatedBy(user);
					g.setCreatedDate(new Date());
					g.setApprovedBy(user);
					g.setApprovedDate(new Date());
					g.setSrNo(sr);
					g.setStuffTallyId(mov.getStuffTallyId());
					g.setStuffTallyLineId(new BigDecimal(mov.getStuffTallyLineId()));
					g.setMovReqType(mov.getMovReqType());
					g.setShift("Day");
					g.setInvoiceNo(mov.getInvoiceNo());
					g.setImporterName(sb.getExporterName());
					g.setImporterAddress1(sb.getExporterAddress1());
					g.setImporterAddress2(sb.getExporterAddress2());
					g.setImporterAddress3(sb.getExporterAddress3());
					g.setCommodity(cargo.getCommodity());
					g.setTransporterStatus("C");
					g.setSl(mov.getShippingLine());
					g.setVesselId(mov.getVesselId());
					g.setCellAreaAllocated(BigDecimal.ZERO);
					g.setQtyTakenOut(BigDecimal.ZERO);
					g.setAreaReleased(BigDecimal.ZERO);
					g.setGwTakenOut(BigDecimal.ZERO);
					g.setYardPackages(BigDecimal.ZERO);
					g.setVehicleWt(BigDecimal.ZERO);
					g.setContainerStatus("FCL");
					g.setVoyageNo(mov.getVoyageNo());
					g.setSa(mov.getShippingAgent());
					g.setDriverName(gatePass.getDriverName());

					exportgatepassrepo.save(g);

					mov.setGatePassNo(HoldNextIdD1);

					exportMovementRepo.save(mov);

					ExportInventory inv = exportinvrepo.getDataByContainerNo(cid, bid, g.getContainerNo());

					if (inv != null) {
						inv.setGatePassNo(HoldNextIdD1);
						inv.setGatePassDate(new Date());

						exportinvrepo.save(inv);
					}

					int update = exportStuffTallyRepo.updateGatePassNo(cid, bid, HoldNextIdD1, mov.getStuffTallyId(),
							mov.getContainerNo());
				}

				else if ("CRG".equals(gatePass.getTransType())) {
					ExportBackToTown existBtt = exportbacktotownrepo.getDataByIdAndSbNoAndSbTrans(cid, bid,
							g.getStuffTallyId(), g.getSbNo(), g.getSbTransId());

					if (existBtt == null) {
						return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
					}

					g.setCompanyId(cid);
					g.setBranchId(bid);
					g.setGatePassId(HoldNextIdD1);
					g.setGatePassDate(new Date());
					g.setStatus("A");
					g.setTransType(gatePass.getTransType());
					g.setMovementReqId("");
					g.setProfitcentreId("N00004");
					g.setVehicleId(gatePass.getVehicleId());
					g.setVehicleNo(gatePass.getVehicleNo());
					g.setTransporter(gatePass.getTransporter());
					g.setTransporterName(gatePass.getTransporterName());
					g.setVehicleWt(gatePass.getVehicleWt());
					g.setComments(gatePass.getComments());
					g.setCha(sb.getCha());
					g.setStatus("A");
					g.setCreatedBy(user);
					g.setCreatedDate(new Date());
					g.setApprovedBy(user);
					g.setApprovedDate(new Date());
					g.setSrNo(sr);
					g.setStuffTallyLineId(BigDecimal.ZERO);
					g.setMovReqType("");
					g.setShift("Day");
					g.setInvoiceNo("");
					g.setImporterName(sb.getExporterName());
					g.setImporterAddress1(sb.getExporterAddress1());
					g.setImporterAddress2(sb.getExporterAddress2());
					g.setImporterAddress3(sb.getExporterAddress3());
					g.setCommodity(cargo.getCommodity());
					g.setTransporterStatus("C");
					g.setSl("");
					g.setVesselId("");
					g.setCellAreaAllocated(BigDecimal.ZERO);
					g.setQtyTakenOut(BigDecimal.ZERO);
					g.setAreaReleased(BigDecimal.ZERO);
					g.setGwTakenOut(BigDecimal.ZERO);
					g.setYardPackages(BigDecimal.ZERO);
					g.setBackToTownPackages(existBtt.getBackToTownPackages());
					g.setContainerStatus("");
					g.setVoyageNo("");
					g.setSa("");
					g.setDriverName(gatePass.getDriverName());
                    g.setCustomsSealNo("");
                    g.setAgentSealNo("");
                    g.setCommodity(g.getPol());

                    g.setPod("");
                    g.setPol("");
					
					exportgatepassrepo.save(g);

					existBtt.setGatePassId(HoldNextIdD1);
					existBtt.setGateOutPackages(existBtt.getBackToTownPackages());

					exportbacktotownrepo.save(existBtt);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05086", HoldNextIdD1, "2024");
				sr++;

			}

			if (!gatePass.getVehicleId().isEmpty() && gatePass.getVehicleId() != null) {

				VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, gatePass.getVehicleId());
				if (track != null) {
					track.setGatePassNo(HoldNextIdD1);
					vehicleTrackRepo.save(track);
				}
			}

			List<ExportGatePass> result = new ArrayList<>();

			if ("CONT".equals(gatePass.getTransType())) {
				result = exportgatepassrepo.getDataByGatePassId(cid, bid, HoldNextIdD1);
			} else if ("CRG".equals(gatePass.getTransType())) {
				result = exportgatepassrepo.getDataByGatePassIdFORCRG(cid, bid, HoldNextIdD1);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			List<ExportGatePass> exist = exportgatepassrepo.getDataByGatePassId1(cid, bid, gatePass.getGatePassId());

			if (!exist.isEmpty()) {

				if (!gatePass.getVehicleNo().equals(exist.get(0).getVehicleNo())) {

					if (!exist.get(0).getVehicleId().isEmpty() && exist.get(0).getVehicleId() != null) {
						VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, exist.get(0).getVehicleId());

						if (track != null) {
							track.setGatePassNo("");
							vehicleTrackRepo.save(track);
						}
					}

					if (!gatePass.getVehicleId().isEmpty() && gatePass.getVehicleId() != null) {
						VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, gatePass.getVehicleId());

						if (track != null) {
							track.setGatePassNo(gatePass.getGatePassId());
							vehicleTrackRepo.save(track);

						}
					}

				}

				exist.stream().forEach(e -> {
					e.setVehicleId(gatePass.getVehicleId());
					e.setVehicleNo(gatePass.getVehicleNo());
					e.setComments(gatePass.getComments());
					if ("CRG".equals(gatePass.getTransType())) {
						e.setVehicleWt(gatePass.getVehicleWt());
					}
					e.setEditedBy(user);
					e.setEditedDate(new Date());
					e.setDriverName(gatePass.getDriverName());

					exportgatepassrepo.save(e);
				});

			}

			List<ExportGatePass> result = new ArrayList<>();

			if ("CONT".equals(gatePass.getTransType())) {
				result = exportgatepassrepo.getDataByGatePassId(cid, bid, gatePass.getGatePassId());
			} else if ("CRG".equals(gatePass.getTransType())) {
				result = exportgatepassrepo.getDataByGatePassIdFORCRG(cid, bid, gatePass.getGatePassId());
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}

	@GetMapping("/searchGatepass")
	public ResponseEntity<?> searchGatepass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = exportgatepassrepo.search(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSearchSelectedData")
	public ResponseEntity<?> getSelectedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val, @RequestParam("type") String type) {

		List<ExportGatePass> result = new ArrayList<>();

		if ("CONT".equals(type)) {
			result = exportgatepassrepo.getDataByGatePassId(cid, bid, val);
		}
		else if("CRG".equals(type)) {
			result = exportgatepassrepo.getDataByGatePassIdFORCRG(cid, bid, val);
		}

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/searchForGateOut")
	public ResponseEntity<?> searchForGateOut(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> result = exportgatepassrepo.searchForGateout(cid, bid, val);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/searchForGateOutSelectData")
	public ResponseEntity<?> searchForGateOutSelectData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("val") String val) {
		List<ExportGatePass> result = exportgatepassrepo.searchForGateoutSelectedData(cid, bid, val);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/saveGateOut")
	public ResponseEntity<?> saveGateOut(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		GateOut gateOut = mapper.readValue(mapper.writeValueAsString(data.get("gateOutData")), GateOut.class);

		List<ExportGatePass> multipleGatePass = mapper.readValue(
				mapper.writeValueAsString(data.get("multipleGateOutData")), new TypeReference<List<ExportGatePass>>() {
				});

		if (gateOut == null) {
			return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05087", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("EXG%07d", nextNumericNextID1);

		int srNo = 1;

		if (gateOut.getGateOutId() == null || gateOut.getGateOutId().isEmpty()) {
			for (ExportGatePass pass : multipleGatePass) {

				
				ExportGatePass exist = exportgatepassrepo.getGataPassData(cid, bid, gateOut.getGatePassNo(),
						pass.getSrNo());

				if (exist == null) {
					return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
				}

                if("CONT".equals(gateOut.getTransType())) {
    				ExportMovement mov = exportMovementRepo.getSingleDataForGatePass1(cid, bid, exist.getMovementReqId(),
    						exist.getContainerNo());

    				if (mov == null) {
    					return new ResponseEntity<>("Movement data not found.", HttpStatus.CONFLICT);
    				}

    				ExportInventory inv = exportinvrepo.getDataByContainerNo(cid, bid, exist.getContainerNo());

    				if (inv == null) {
    					return new ResponseEntity<>("Export inventory data not found.", HttpStatus.CONFLICT);
    				}

    				GateOut out = new GateOut();
    				out.setCompanyId(cid);
    				out.setBranchId(bid);
    				out.setApprovedBy(user);
    				out.setApprovedDate(new Date());
    				out.setCha(exist.getCha());
    				out.setComments(gateOut.getComments());
    				out.setCommodityDescription(exist.getCommodity());
    				out.setContainerNo(pass.getContainerNo());
    				out.setContainerSize(pass.getContainerSize());
    				out.setContainerType(pass.getContainerType());
    				out.setCreatedBy(user);
    				out.setCreatedDate(new Date());
    				out.setDocRefDate(exist.getSbDate());
    				out.setDocRefNo(exist.getSbNo());
    				out.setDriverName(gateOut.getDriverName());
    				out.setErpDocRefNo(exist.getSbTransId());
    				out.setExporterName(exist.getImporterName());
    				out.setFinYear("2024");
    				out.setGateInDate(inv.getGateInDate());
    				out.setGateNoOut(gateOut.getGateNoOut());
    				out.setShift(gateOut.getShift());
    				out.setGateOutDate(new Date());
    				out.setGateOutId(HoldNextIdD1);
    				out.setGatePassDate(exist.getGatePassDate());
    				out.setGatePassNo(gateOut.getGatePassNo());
    				out.setGrossWt(pass.getGrossWt());
    				out.setIsoCode(inv.getIso());
    				out.setMovementType(mov.getMovReqType());
    				out.setOnAccountOf(mov.getOnAccountOf());
    				out.setProcessId("P00223");
    				out.setProfitcentreId("N00004");
    				out.setSl(exist.getSl());
    				out.setSrNo(String.valueOf(srNo));
    				out.setStatus('A');
    				out.setTallyId(mov.getStuffTallyId());
    				out.setTareWt(inv.getContainerWeight());
    				out.setTransporter(exist.getTransporter());
    				out.setTransporterName(exist.getTransporterName());
    				out.setTransporterStatus(exist.getTransporterStatus().charAt(0));
    				out.setTransType(gateOut.getTransType());
    				out.setVehicleId(exist.getVehicleId());
    				out.setVehicleNo(gateOut.getVehicleNo());
    				out.setVesselId(exist.getVesselId());
    				out.setViaNo(pass.getViaNo());

    				gateoutrepo.save(out);

    				

    				if (inv != null) {
    					inv.setGateOutId(HoldNextIdD1);
    					inv.setGateOutDate(new Date());

    					exportinvrepo.save(inv);
    				}

    				mov.setGateOutId(HoldNextIdD1);
    				mov.setGateOutDate(new Date());

    				exportMovementRepo.save(mov);
    				
    				int update = exportStuffTallyRepo.updateGateOutNo(cid, bid, HoldNextIdD1, exist.getStuffTallyId(),
    						exist.getContainerNo());
                }
                
                else if("CRG".equals(gateOut.getTransType())) {
                	ExportBackToTown existBtt = exportbacktotownrepo.getDataByIdAndSbNoAndSbTrans(cid, bid,
							exist.getStuffTallyId(), exist.getSbNo(), exist.getSbTransId());

					if (existBtt == null) {
						return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
					}

    				GateOut out = new GateOut();
    				out.setCompanyId(cid);
    				out.setBranchId(bid);
    				out.setApprovedBy(user);
    				out.setApprovedDate(new Date());
    				out.setCha(exist.getCha());
    				out.setComments(gateOut.getComments());
    				out.setCommodityDescription(exist.getCommodity());
    				out.setContainerNo("");
    				out.setContainerSize("");
    				out.setContainerType("");
    				out.setCreatedBy(user);
    				out.setCreatedDate(new Date());
    				out.setDocRefDate(exist.getSbDate());
    				out.setDocRefNo(exist.getSbNo());
    				out.setDriverName(gateOut.getDriverName());
    				out.setErpDocRefNo(exist.getSbTransId());
    				out.setExporterName(exist.getImporterName());
    				out.setFinYear("2024");
    				out.setGateInDate(null);
    				out.setGateNoOut(gateOut.getGateNoOut());
    				out.setShift(gateOut.getShift());
    				out.setGateOutDate(new Date());
    				out.setGateOutId(HoldNextIdD1);
    				out.setGatePassDate(exist.getGatePassDate());
    				out.setGatePassNo(gateOut.getGatePassNo());
    				out.setGrossWt(pass.getGrossWt());
    				out.setIsoCode("");
    				out.setMovementType("");
    				out.setOnAccountOf(existBtt.getOnAccountOf());
    				out.setProcessId("P00223");
    				out.setProfitcentreId("N00004");
    				out.setSl(exist.getSl());
    				out.setSrNo(String.valueOf(srNo));
    				out.setStatus('A');
    				out.setTallyId(exist.getStuffTallyId());
    				out.setTareWt(BigDecimal.ZERO);
    				out.setTransporter(exist.getTransporter());
    				out.setTransporterName(exist.getTransporterName());
    				out.setTransporterStatus(exist.getTransporterStatus().charAt(0));
    				out.setTransType(gateOut.getTransType());
    				out.setVehicleId(exist.getVehicleId());
    				out.setVehicleNo(gateOut.getVehicleNo());
    				out.setVesselId(exist.getVesselId());
    				out.setViaNo(pass.getViaNo());
    				out.setQtyTakenOut(exist.getBackToTownPackages());
    				

    				gateoutrepo.save(out);

    				exist.setGateOutPackages(exist.getBackToTownPackages());

    				
    				existBtt.setGateOutId(HoldNextIdD1);
    				existBtt.setGateOutDate(new Date());
    				
    				exportbacktotownrepo.save(existBtt);
    			
                }

				
				processnextidrepo.updateAuditTrail(cid, bid, "P05087", HoldNextIdD1, "2024");
				srNo++;
				exist.setGateOutId(HoldNextIdD1);
		

				exportgatepassrepo.save(exist);

				if (!exist.getVehicleId().isEmpty() && exist.getVehicleId() != null) {

					VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, exist.getVehicleId());
					if (track != null) {
						track.setGateOutId(HoldNextIdD1);
						track.setGateOutDate(new Date());
						vehicleTrackRepo.save(track);
					}
				}

			
			}
		}

		Map<String, Object> result = new HashMap<>();

		if (gateOut.getGateOutId() == null || gateOut.getGateOutId().isEmpty()) {
			List<GateOut> out = gateoutrepo.getEportGateOutData(cid, bid, HoldNextIdD1);

			if (out.isEmpty()) {
				return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);
			}

			List<ExportGatePass> gatePass = exportgatepassrepo.searchForGateoutSelectedData1(cid, bid,
					gateOut.getGatePassNo());

			if (gatePass.isEmpty()) {
				return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
			}

			result.put("gateOut", out);
			result.put("multipleGateOutData", gatePass);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			List<GateOut> out = gateoutrepo.getEportGateOutData(cid, bid, gateOut.getGateOutId());

			if (out.isEmpty()) {
				return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);
			}

			List<ExportGatePass> gatePass = exportgatepassrepo.searchForGateoutSelectedData1(cid, bid,
					gateOut.getGatePassNo());

			if (gatePass.isEmpty()) {
				return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
			}

			result.put("gateOut", out);
			result.put("multipleGateOutData", gatePass);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}

	@GetMapping("/searchGateOutData")
	public ResponseEntity<?> searchGateOutData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = gateoutrepo.searchExportGateOut(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSelectedGateOutData")
	public ResponseEntity<?> getSelectedGateOutData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val) {
		Map<String, Object> result = new HashMap<>();
		List<GateOut> out = gateoutrepo.getEportGateOutData(cid, bid, val);

		if (out.isEmpty()) {
			return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);
		}

		List<ExportGatePass> gatePass = exportgatepassrepo.searchForGateoutSelectedData1(cid, bid,
				out.get(0).getGatePassNo());

		if (gatePass.isEmpty()) {
			return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
		}

		result.put("gateOut", out);
		result.put("multipleGateOutData", gatePass);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
