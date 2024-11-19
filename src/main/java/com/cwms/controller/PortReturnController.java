package com.cwms.controller;

import java.math.BigDecimal;
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

import com.cwms.entities.ExportInventory;
import com.cwms.entities.GateIn;
import com.cwms.entities.GateOut;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.GateOutRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/portReturn")
public class PortReturnController {

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private GateInRepository gateinrepo;

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;

	@Autowired
	private ExportInventoryRepository exportInventoryRepo;

	@Autowired
	private GateOutRepo gateOutRepo;

	@Autowired
	private ExportStuffTallyRepo exportStuffTallyRepo;

	@GetMapping("/getContainers")
	public ResponseEntity<?> getContainerNoForPortReturn(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {
		List<String> data = gateOutRepo.getContainerNosForPortReturn(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/getSingleContainer")
	public ResponseEntity<?> getSingleContainerNoForPortReturn(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {
		GateOut data = gateOutRepo.getSingleContainerNosForPortReturn(cid, bid, val);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.NO_CONTENT);
		}

		List<Object[]> getSbData = exportStuffTallyRepo.getStufftallyRecordsForPortContainerGateIn(cid, bid,
				data.getGateOutId(), val);

		if (getSbData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.NO_CONTENT);
		}

		Map<String, Object> result = new HashMap<>();

		result.put("containerData", data);
		result.put("sbData", getSbData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/savePortContainerGateIn")
	public ResponseEntity<?> savePortContainerGateIn(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		GateIn gateIn = mapper.readValue(mapper.writeValueAsString(data.get("gateInData")), GateIn.class);

		List<GateIn> multipleGatePass = mapper.readValue(mapper.writeValueAsString(data.get("multipleGateInData")),
				new TypeReference<List<GateIn>>() {
				});

		if (gateIn == null) {
			return new ResponseEntity<>("Gate in data not found", HttpStatus.CONFLICT);
		}

		if (gateIn.getGateInId() == null || gateIn.getGateInId().isEmpty()) {
			int sr = 1;

			GateOut data1 = gateOutRepo.getSingleContainerNosForPortReturn1(cid, bid, gateIn.getContainerNo());

			if (data1 == null) {
				return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);
			}

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05089", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("GIPT%06d", nextNumericNextID1);

			for (GateIn g : multipleGatePass) {
				g.setCompanyId(cid);
				g.setBranchId(bid);
				g.setStatus("A");
				g.setCreatedBy(user);
				g.setLineNo("");
				g.setCreatedDate(new Date());
				g.setApprovedBy(user);
				g.setApprovedDate(new Date());
				g.setGateInId(HoldNextIdD1);
				g.setInGateInDate(new Date());
				g.setGateNo(gateIn.getGateNo());
				g.setCartingTransId(gateIn.getCartingTransId());
				g.setGateInType(gateIn.getGateInType());
				g.setContainerNo(gateIn.getContainerNo());
				g.setIsoCode(gateIn.getIsoCode());
				g.setContainerSize(gateIn.getContainerSize());
				g.setContainerType(gateIn.getContainerType());
				g.setContainerStatus(gateIn.getContainerStatus());
				g.setProfitcentreId("N00004");
				g.setCustomsSealNo(gateIn.getCustomsSealNo());
				g.setContainerSealNo(gateIn.getContainerSealNo());
				g.setGrossWeight(gateIn.getGrossWeight());
				g.setHazardous(gateIn.getHazardous());
				g.setRefer(gateIn.getRefer());
				g.setSa(gateIn.getSa());
				g.setSl(gateIn.getSl());
				g.setDeliveryOrderNo(gateIn.getDeliveryOrderNo());
				g.setDeliveryOrderDate(gateIn.getDeliveryOrderDate());
				g.setDoValidityDate(gateIn.getDoValidityDate());
				g.setOnAccountOf(gateIn.getOnAccountOf());
				g.setContainerHealth(gateIn.getContainerHealth());
				g.setTransporterStatus(gateIn.getTransporterStatus());
				g.setTransporter(gateIn.getTransporter());
				g.setTransporterName(gateIn.getTransporterName());
				g.setVehicleNo(gateIn.getVehicleNo());
				g.setDriverName(gateIn.getDriverName());
				g.setComments(gateIn.getComments());
				g.setFinYear(gateIn.getFinYear());
				g.setSrNo(sr);
				g.setProcessId("P00226");
				g.setCha(data1.getCha());
				g.setPrGatePassNo(data1.getGatePassNo());
				g.setPrGateOutId(data1.getGateOutId());

				gateinrepo.save(g);

				sr++;
			}

			VehicleTrack v = new VehicleTrack();
			v.setCompanyId(cid);
			v.setBranchId(bid);
			v.setFinYear(gateIn.getFinYear());
			v.setVehicleNo(gateIn.getVehicleNo());
			v.setProfitcentreId("N00004");
			v.setSrNo(1);
			v.setTransporterStatus(gateIn.getTransporterStatus().charAt(0));
			v.setTransporterName(gateIn.getTransporterName());
			v.setTransporter(gateIn.getTransporter());
			v.setDriverName(gateIn.getDriverName());
			v.setVehicleStatus('E');
			v.setGateInId(HoldNextIdD1);
			v.setGateInDate(new Date());
			v.setGateNoIn(gateIn.getGateNo());
			v.setShiftIn(gateIn.getShift());
			v.setStatus('A');
			v.setCreatedBy(user);
			v.setCreatedDate(new Date());
			v.setApprovedBy(user);
			v.setApprovedDate(new Date());

			vehicleTrackRepo.save(v);

			ExportInventory inventory = new ExportInventory();
			inventory.setCompanyId(cid);
			inventory.setBranchId(bid);
			inventory.setSbTransId("");
			inventory.setSbNo("");
			inventory.setProfitcentreId("N00004");
			inventory.setGateInId(HoldNextIdD1);
			inventory.setGateInDate(new Date());
			inventory.setContainerNo(gateIn.getContainerNo());
			inventory.setContainerSealNo(gateIn.getContainerSealNo());
			inventory.setContainerSize(gateIn.getContainerSize());
			inventory.setContainerType(gateIn.getContainerType());
			inventory.setContainerStatus(gateIn.getContainerStatus());
			inventory.setContainerWeight(BigDecimal.ZERO);
			inventory.setIso(gateIn.getIsoCode());
			inventory.setSa(gateIn.getSa());
			inventory.setSl(gateIn.getSl());
			inventory.setCreatedBy(user);
			inventory.setCreatedDate(new Date());
			inventory.setApprovedBy(user);
			inventory.setApprovedDate(new Date());
			inventory.setStatus("A");

			exportInventoryRepo.save(inventory);

			data1.setPortReturnFlag("Y");
			data1.setPortReturnId(HoldNextIdD1);

			gateOutRepo.save(data1);

			processnextidrepo.updateAuditTrail(cid, bid, "P05089", HoldNextIdD1, "2024");

			List<GateIn> result = gateinrepo.getPrData(cid, bid, HoldNextIdD1);

			if (result.isEmpty()) {
				return new ResponseEntity<>("Gate in data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			for (GateIn g : multipleGatePass) {

				GateIn existingData = gateinrepo.getData(cid, bid, gateIn.getGateInId(), g.getErpDocRefNo(),
						g.getDocRefNo());

				if (existingData == null) {
					return new ResponseEntity<>("Gate in data not found", HttpStatus.CONFLICT);
				}

				existingData.setEditedBy(user);
				existingData.setEditedDate(new Date());
				existingData.setGateNo(gateIn.getGateNo());
				existingData.setContainerStatus(gateIn.getContainerStatus());
				existingData.setGrossWeight(gateIn.getGrossWeight());
				existingData.setHazardous(gateIn.getHazardous());
				existingData.setRefer(gateIn.getRefer());
				existingData.setSa(gateIn.getSa());
				existingData.setSl(gateIn.getSl());
				existingData.setDeliveryOrderNo(gateIn.getDeliveryOrderNo());
				existingData.setDeliveryOrderDate(gateIn.getDeliveryOrderDate());
				existingData.setDoValidityDate(gateIn.getDoValidityDate());
				existingData.setOnAccountOf(gateIn.getOnAccountOf());
				existingData.setContainerHealth(gateIn.getContainerHealth());
				existingData.setTransporterStatus(gateIn.getTransporterStatus());
				existingData.setTransporter(gateIn.getTransporter());
				existingData.setTransporterName(gateIn.getTransporterName());
				existingData.setVehicleNo(gateIn.getVehicleNo());
				existingData.setDriverName(gateIn.getDriverName());
				existingData.setComments(gateIn.getComments());

				gateinrepo.save(existingData);
			}

			VehicleTrack existingVehicle = vehicleTrackRepo.getByGateInId(cid, bid, gateIn.getGateInId());

			if (existingVehicle != null) {
				existingVehicle.setVehicleNo(gateIn.getVehicleNo());
				existingVehicle.setDriverName(gateIn.getDriverName());
				existingVehicle.setEditedBy(user);
				existingVehicle.setEditedDate(new Date());
				existingVehicle.setGateNoIn(gateIn.getGateNo());
				existingVehicle.setTransporterStatus(gateIn.getTransporterStatus().charAt(0));
				existingVehicle.setTransporter(gateIn.getTransporter());
				existingVehicle.setTransporterName(gateIn.getTransporterName());

				vehicleTrackRepo.save(existingVehicle);

			}

			ExportInventory existingInv = exportInventoryRepo.getSingleDataByGateInId(cid, bid, gateIn.getGateInId());

			if (existingInv != null) {
				existingInv.setContainerStatus(gateIn.getContainerStatus());
				existingInv.setSa(gateIn.getSa());
				existingInv.setSl(gateIn.getSl());
				existingInv.setEditedBy(user);
				existingInv.setEditedDate(new Date());

				exportInventoryRepo.save(existingInv);
			}

			List<GateIn> result = gateinrepo.getPrData(cid, bid, gateIn.getGateInId());

			if (result.isEmpty()) {
				return new ResponseEntity<>("Gate in data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="val",required = false) String val) {
		
		List<Object[]> result = gateinrepo.searchPortRnDataList(cid, bid, val);
		
		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	@GetMapping("/searchSelectedData")
	public ResponseEntity<?> searchSelectedData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("val") String val) {
		
		List<GateIn> result = gateinrepo.getPrData(cid, bid, val);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Gate in data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
}
