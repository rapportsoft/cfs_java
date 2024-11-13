package com.cwms.controller;

import java.math.BigDecimal;
import java.util.Date;
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

import com.cwms.entities.ExportGatePass;
import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportMovement;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportGatePassRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportMovementRepo;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffTallyRepo;
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
	
	@GetMapping("/getEmptyVehicle")
	public ResponseEntity<?> getEmptyVehicleForGatePass(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="veh",required = false) String veh){
		
		List<Object[]> vehData = vehicleTrackRepo.getEmptyVehGateIn2(cid, bid, veh, "N00004");
		
		if(vehData.isEmpty()) {
			return new ResponseEntity<>("Vehicle not found...",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(vehData,HttpStatus.OK);
	}
	
	@GetMapping("/getMovementData")
	public ResponseEntity<?> getMovementData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="val",required = false) String val){
		
		List<Object[]> data = exportMovementRepo.getDataForGatePass(cid, bid, val);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@GetMapping("/getSelectedMovementData")
	public ResponseEntity<?> getSelectedMovementData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("reqId") String reqId,@RequestParam("con") String con){
		
		ExportMovement data = exportMovementRepo.getSingleDataForGatePass(cid, bid, reqId, con);
		
		if(data == null) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	
	@PostMapping("/saveGatePass")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("user") String user,@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		
		ExportGatePass gatePass = mapper.readValue(mapper.writeValueAsString(data.get("gatePassData")),
				ExportGatePass.class);
		
		List<ExportGatePass> multipleGatePass = mapper.readValue(mapper.writeValueAsString(data.get("multipleGatePassData")),
				new TypeReference<List<ExportGatePass>>() {
				});
		
		if(gatePass == null) {
			return new ResponseEntity<>("Gate pass data not found",HttpStatus.CONFLICT);
		}
		
		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05086", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("EXGP%06d", nextNumericNextID1);
		
		if(gatePass.getGatePassId().isEmpty() || gatePass.getGatePassId() == null) {
			int sr = 1;
			for(ExportGatePass g : multipleGatePass) {
				
				ExportSbEntry sb = sbentryrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, g.getSbNo(), g.getSbTransId());
				
				if(sb == null) {
					return new ResponseEntity<>("SB data not found.",HttpStatus.CONFLICT);
				}
				
				ExportSbCargoEntry cargo = exportsbcargoentryrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, g.getSbNo(), g.getSbTransId());
				
				
				if(cargo == null) {
					return new ResponseEntity<>("SB cargo data not found.",HttpStatus.CONFLICT);
				}
				
				ExportMovement mov = exportMovementRepo.getSingleDataForGatePass1(cid, bid, g.getMovementReqId(), g.getContainerNo());
				
				if(mov == null) {
					return new ResponseEntity<>("Movement data not found.",HttpStatus.CONFLICT);
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
				
				exportgatepassrepo.save(g);
				
				mov.setGatePassNo(HoldNextIdD1);
				
				exportMovementRepo.save(mov);
				
				processnextidrepo.updateAuditTrail(cid, bid, "P05086", HoldNextIdD1, "2024");
				sr++;
				
				
				
				
				ExportInventory inv = exportinvrepo.getDataByContainerNoAndSb(cid, bid, g.getContainerNo(), g.getSbNo());
				
				if(inv != null) {
					inv.setGatePassNo(HoldNextIdD1);
					inv.setGatePassDate(new Date());
					
					exportinvrepo.save(inv);
				}
				
				
				int update = exportStuffTallyRepo.updateGatePassNo(cid, bid, HoldNextIdD1, mov.getStuffTallyId(), mov.getContainerNo());
			}
			
			if(!gatePass.getVehicleId().isEmpty() && gatePass.getVehicleId() != null) {
				VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, gatePass.getVehicleId());
				
				if(track != null) {
					track.setGatePassNo(HoldNextIdD1);
					vehicleTrackRepo.save(track);
				}
			}
		}
		else {
			List<ExportGatePass> exist = exportgatepassrepo.getDataByGatePassId1(cid, bid, gatePass.getGatePassId());
			
			
			if(!exist.isEmpty()) {
				
				if(!gatePass.getVehicleNo().equals(exist.get(0).getVehicleNo())) {
					
					if(!exist.get(0).getVehicleId().isEmpty() && exist.get(0).getVehicleId() != null) {
						VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, exist.get(0).getVehicleId());
						
						if(track != null) {
							track.setGatePassNo("");
							vehicleTrackRepo.save(track);
						}
					}
					
					
					if(!gatePass.getVehicleId().isEmpty() && gatePass.getVehicleId() != null) {
						VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, gatePass.getVehicleId());
						
						if(track != null) {
							track.setGatePassNo(gatePass.getGatePassId());
							vehicleTrackRepo.save(track);
						}
					}
					
				}
				
				
				exist.stream().forEach(e->{
					e.setVehicleId(gatePass.getVehicleId());
					e.setVehicleNo(gatePass.getVehicleNo());
					e.setComments(gatePass.getComments());
					e.setEditedBy(user);
					e.setEditedDate(new Date());
					
					exportgatepassrepo.save(e);
				});
				
				
			}
		}
		
	
		if(gatePass.getGatePassId().isEmpty() || gatePass.getGatePassId() == null) {
			
		
			
			
			List<ExportGatePass> result = exportgatepassrepo.getDataByGatePassId(cid, bid, HoldNextIdD1);
			
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		else {
			List<ExportGatePass> result = exportgatepassrepo.getDataByGatePassId(cid, bid, gatePass.getGatePassId());
			
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
	
		
	}
	
	
	@GetMapping("/searchGatepass")
	public ResponseEntity<?> searchGatepass(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="val",required = false) String val){
		
		
		List<Object[]> data = exportgatepassrepo.search(cid, bid, val);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	
	@GetMapping("/getSearchSelectedData")
	public ResponseEntity<?> getSelectedData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("val") String val){
		
		
		List<ExportGatePass> result = exportgatepassrepo.getDataByGatePassId(cid, bid, val);
		
		
		
		if(result.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
}
