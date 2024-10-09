package com.cwms.controller;

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

import com.cwms.entities.ManualGateIn;
import com.cwms.entities.Party;
import com.cwms.entities.Port;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.ManualContainerGateInRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.PortRepository;
import com.cwms.repository.ProcessNextIdRepository;

@RestController
@RequestMapping("/manualContainerGateIn")
@CrossOrigin("*")
public class ManualContainerGateInController {

	@Autowired
	private PortRepository portrpo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private ManualContainerGateInRepo manualcontainergateinrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private ImportInventoryRepository importinventoryrepo;

	@GetMapping("/getports")
	public ResponseEntity<?> getPorts(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Port> getPorts = portrpo.getPortsForGateIn(cid, bid);

		if (getPorts.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(getPorts, HttpStatus.OK);
	}

	@PostMapping("/saveManualCOntainerGateIn")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody ManualGateIn gateIn) {

		if (gateIn == null) {
			return new ResponseEntity<>("Manual container gate in data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05063", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("GIGG%06d", nextNumericNextID1);

		if (gateIn.getGateInId() == null || gateIn.getGateInId().isEmpty()) {

			Boolean checkExist = cfigmcnrepo.isExistContainer2(cid, bid, gateIn.getContainerNo());

			if (checkExist) {
				return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
			}

			Boolean exist = importinventoryrepo.isExistContainer(cid, bid, gateIn.getContainerNo());

			if (exist) {
				return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
			}
			
			Boolean exist1 = manualcontainergateinrepo.isExistContainerNo(cid, bid, gateIn.getContainerNo());

			if (exist1) {
				return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
			}

			gateIn.setCompanyId(cid);
			gateIn.setBranchId(bid);
			gateIn.setStatus("A");
			gateIn.setCreatedBy(user);
			gateIn.setCreatedDate(new Date());
			gateIn.setApprovedBy(user);
			gateIn.setApprovedDate(new Date());
			gateIn.setGateNo("");
			gateIn.setSrNo(1);
			gateIn.setErpDocRefNo("");
			gateIn.setDocRefNo("");
			gateIn.setLineNo("");
			gateIn.setGateInDate(new Date());
			gateIn.setGateInId(HoldNextIdD1);
			gateIn.setProcessId("P00212");
			gateIn.setProfitcentreId("N00002");

			manualcontainergateinrepo.save(gateIn);

			processnextidrepo.updateAuditTrail(cid, bid, "P05063", HoldNextIdD1, "2024");
		} else {

			ManualGateIn exist = manualcontainergateinrepo.getSinggleData(cid, bid, gateIn.getGateInId());

			if (exist == null) {
				return new ResponseEntity<>("Manual container gate in data not found", HttpStatus.CONFLICT);
			}

			exist.setEditedBy(user);
			exist.setEditedDate(new Date());
			exist.setContainerSize(gateIn.getContainerSize());
			exist.setContainerType(gateIn.getContainerType());
			exist.setVehicleNo(gateIn.getVehicleNo());
			exist.setDriverName(gateIn.getDriverName());
			exist.setIsoCode(gateIn.getIsoCode());
			exist.setTareWeight(gateIn.getTareWeight());
			exist.setTransporter(gateIn.getTransporter());
			exist.setTransporterName(gateIn.getTransporterName());
			exist.setVessel(gateIn.getVessel());
			exist.setViaNo(gateIn.getViaNo());
			exist.setContainerSealNo(gateIn.getContainerSealNo());
			exist.setActualSealNo(gateIn.getActualSealNo());
			exist.setVoyageNo(gateIn.getVoyageNo());
			exist.setHazardous(gateIn.getHazardous());
			exist.setEirGrossWeight(gateIn.getEirGrossWeight());
			exist.setRefer(gateIn.getRefer());
			exist.setHazClass(gateIn.getHazClass());
			exist.setLowBed(gateIn.getLowBed());
			exist.setSl(gateIn.getSl());
			exist.setImporterName(gateIn.getImporterName());
			exist.setYardBlock(gateIn.getYardBlock());
			exist.setYardCell(gateIn.getYardCell());
			exist.setYardLocation(gateIn.getYardLocation());
			exist.setPortExitNo(gateIn.getPortExitNo());
			exist.setPortExitDate(gateIn.getPortExitDate());
			exist.setVehicleType(gateIn.getVehicleType());
			exist.setTerminal(gateIn.getTerminal());
			exist.setContainerHealth(gateIn.getContainerHealth());
			exist.setScanningStatus(gateIn.getScanningStatus());
			exist.setScannerType(gateIn.getScannerType());
			exist.setOdcStatus(gateIn.getOdcStatus());
			exist.setPnStatus(gateIn.getPnStatus());
			exist.setNoCheckDigit(gateIn.getNoCheckDigit());
			exist.setHoldType(gateIn.getHoldType());
			exist.setComments(gateIn.getComments());
			exist.setHoldRemarks(gateIn.getHoldRemarks());

			manualcontainergateinrepo.save(exist);
		}
		if (gateIn.getGateInId() == null || gateIn.getGateInId().isEmpty()) {

			ManualGateIn manual = manualcontainergateinrepo.getSinggleData(cid, bid, HoldNextIdD1);

			if (manual == null) {
				return new ResponseEntity<>("Manual container gate in data not found", HttpStatus.CONFLICT);
			}

			
			Map<String, Object> data = new HashMap<>();
			
			if(manual.getSl() != null && !manual.getSl().isEmpty()) {
				Party party = partyrepo.getDataById(cid, bid, manual.getSl());
				data.put("slName", party.getPartyName());
			}

			data.put("manual", manual);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} else {
			ManualGateIn manual = manualcontainergateinrepo.getSinggleData(cid, bid, gateIn.getGateInId());

			if (manual == null) {
				return new ResponseEntity<>("Manual container gate in data not found", HttpStatus.CONFLICT);
			}
			
			Map<String, Object> data = new HashMap<>();
			
			if(manual.getSl() != null && !manual.getSl().isEmpty()) {
				Party party = partyrepo.getDataById(cid, bid, manual.getSl());
				data.put("slName", party.getPartyName());
			}

			data.put("manual", manual);

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam(name="val",required = false) String val){
		
		List<Object[]> search = manualcontainergateinrepo.search(cid, bid, val);
		
		if (search.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(search, HttpStatus.OK);
	}
	
	@GetMapping("/getSelectedData")
	public ResponseEntity<?> getSelectedData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam(name="val",required = false) String val){
		ManualGateIn manual = manualcontainergateinrepo.getSinggleData(cid, bid, val);

		if (manual == null) {
			return new ResponseEntity<>("Manual container gate in data not found", HttpStatus.CONFLICT);
		}
		
		Map<String, Object> data = new HashMap<>();
		
		if(manual.getSl() != null && !manual.getSl().isEmpty()) {
			Party party = partyrepo.getDataById(cid, bid, manual.getSl());
			data.put("slName", party.getPartyName());
		}

		data.put("manual", manual);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
