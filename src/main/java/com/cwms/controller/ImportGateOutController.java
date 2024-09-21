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

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.DestuffCrg;
import com.cwms.entities.GateOut;
import com.cwms.entities.ImportGatePass;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.DestuffCrgRepository;
import com.cwms.repository.DestuffRepository;
import com.cwms.repository.ImportGateOutRepository;
import com.cwms.repository.ImportGatePassRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.ProfitcentreRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/importGateOut")
@RestController
@CrossOrigin("*")
public class ImportGateOutController {

	@Autowired
	private ImportGatePassRepo importgatepassrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private ProfitcentreRepository profitcentrerepo;

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;

	@Autowired
	private DestuffRepository destuffRepo;

	@Autowired
	private DestuffCrgRepository destuffcrgrepo;

	@Autowired
	private ImportGateOutRepository gateOutRepo;

	@GetMapping("/searchGatePassData")
	public ResponseEntity<?> searchGatePassData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmline") String igmline) {

		List<ImportGatePass> data = importgatepassrepo.searchDataForGateOut(cid, bid, igm, igmline);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		String partyName = partyrepo.getPartyNameById(cid, bid, data.get(0).getSl());
		String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, data.get(0).getProfitcentreId());

		Map<String, Object> result = new HashMap<>();
		result.put("gatePassData", data);
		result.put("sl", partyName);
		result.put("profit", profitCentreName);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/getGatePassDataByGatePassIdAndVehicleNo")
	public ResponseEntity<?> getGatePassDataByGatePassIdAndVehicleNo(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("val") String val) {

		List<Object[]> data = importgatepassrepo.getGatePassDataByGatePassIdAndVehicleNo(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getGatePassDataById")
	public ResponseEntity<?> getGatePassDataById(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val) {

		List<ImportGatePass> data = importgatepassrepo.getDataByGatePassId(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		String partyName = partyrepo.getPartyNameById(cid, bid, data.get(0).getSl());
		String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, data.get(0).getProfitcentreId());

		Map<String, Object> result = new HashMap<>();
		result.put("gatePassData", data);
		result.put("sl", partyName);
		result.put("profit", profitCentreName);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping("/saveImportGateOut")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String userId, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		GateOut out = mapper.readValue(mapper.writeValueAsString(data.get("singleGateOut")), GateOut.class);

		if (out == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		List<GateOut> gateOut = mapper.readValue(mapper.writeValueAsString(data.get("gateOutData")),
				new TypeReference<List<GateOut>>() {
				});

		if (gateOut.isEmpty()) {
			return new ResponseEntity<>("Gate out data not found", HttpStatus.CONFLICT);
		}

		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, out.getErpDocRefNo(), out.getDocRefNo());

		if (igm == null) {
			return new ResponseEntity<>("IGM data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05074", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("CNG%07d", nextNumericNextID1);

		for (GateOut g : gateOut) {

			ImportGatePass gatePass = importgatepassrepo.getDataByGatePassIdAndSrNo(cid, bid, g.getGatePassNo(),
					Integer.parseInt(g.getSrNo()));

			if (gatePass == null) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, g.getErpDocRefNo(), g.getDocRefNo(), g.getIgmLineNo());

			if (crg == null) {
				return new ResponseEntity<>("Item data not found " + g.getIgmLineNo(), HttpStatus.CONFLICT);
			}

			if (g.getGateOutId() == null || g.getGateOutId().isEmpty()) {
				g.setCompanyId(cid);
				g.setBranchId(bid);
				g.setStatus('A');
				g.setCreatedBy(userId);
				g.setCreatedDate(new Date());
				g.setApprovedBy(userId);
				g.setApprovedDate(new Date());
				g.setGateOutId(HoldNextIdD1);
				g.setGateOutDate(new Date());
				g.setDocRefDate(null);
				g.setProfitcentreId(gatePass.getProfitcentreId());
				g.setDrt('N');
				g.setProcessId("P00209");
				g.setShift(out.getShift());
				g.setGateNoOut(out.getGateNoOut());
				g.setViaNo(igm.getViaNo());
				g.setSa(igm.getShippingAgent());
				g.setSl(igm.getShippingLine());
				g.setCha(crg.getChaCode());
				g.setChaName(crg.getChaName());
				g.setImporterName(crg.getImporterName());
				g.setNatureOfCargo(crg.getTypeOfPackage());
				g.setVesselId(igm.getVesselId());
				g.setComments(out.getComments());
				g.setGateNoOut(out.getGateNoOut());
				g.setDeliveryOrderNo(out.getDeliveryOrderNo());
				g.setDeliveryOrderDate(out.getDeliveryOrderDate());
				g.setDoValidityDate(out.getDoValidityDate());

				gateOutRepo.save(g);

				gatePass.setGateOutId(HoldNextIdD1);
				gatePass.setGateOutDate(new Date());
				gatePass.setDoNo(g.getDeliveryOrderNo());
				gatePass.setDoDate(g.getDeliveryOrderDate());
				gatePass.setDoValidityDate(g.getDoValidityDate());

				importgatepassrepo.save(gatePass);

				crg.setGateOutNo(HoldNextIdD1);
				crg.setGateOutDate(new Date());

				cfigmcrgrepo.save(crg);

				if (!"LCL".equals(out.getTransType())) {
					if (g.getVehicleNo() != null && !g.getVehicleNo().isEmpty()) {
						System.out.println("gatePass.getVehicleGatePassId() " + gatePass.getVehicleGatePassId());
						VehicleTrack vehTrack = vehicleTrackRepo.getByGateInId(cid, bid,
								gatePass.getVehicleGatePassId());

						if (vehTrack != null) {
							vehTrack.setGateOutId(HoldNextIdD1);
							vehTrack.setGateOutDate(new Date());

							vehicleTrackRepo.save(vehTrack);
						}
						int updategateOutId = cfigmcnrepo.updategateOutId1(cid, bid, g.getErpDocRefNo(),
								g.getDocRefNo(), g.getIgmLineNo(), HoldNextIdD1, out.getDeliveryOrderNo(),
								out.getDeliveryOrderDate(), out.getDoValidityDate());
					}
				} else {
					int updateVehicle = vehicleTrackRepo.updateVehicleData(cid, bid, out.getGatePassNo(), HoldNextIdD1);

					int updategateOutId = cfigmcnrepo.updategateOutId1(cid, bid, g.getErpDocRefNo(), g.getDocRefNo(),
							"", HoldNextIdD1, out.getDeliveryOrderNo(), out.getDeliveryOrderDate(),
							out.getDoValidityDate());
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05074", HoldNextIdD1, "2024");
			} else {

				GateOut existing = gateOutRepo.getSingleData(cid, bid, g.getErpDocRefNo(), g.getDocRefNo(),
						g.getGateOutId(), g.getSrNo());

				if (existing != null) {
					existing.setShift(out.getShift());
					existing.setGateNoOut(out.getGateNoOut());
					existing.setDeliveryOrderNo(out.getDeliveryOrderNo());
					existing.setDeliveryOrderDate(out.getDeliveryOrderDate());
					existing.setDoValidityDate(out.getDoValidityDate());
					existing.setComments(out.getComments());
					existing.setEditedBy(userId);
					existing.setEditedDate(new Date());

					gateOutRepo.save(existing);
				}

				if (!"LCL".equals(out.getTransType())) {
					if (g.getVehicleNo() != null && !g.getVehicleNo().isEmpty()) {
						int updategateOutId = cfigmcnrepo.updategateOutId2(cid, bid, g.getErpDocRefNo(), g.getDocRefNo(),
								g.getIgmLineNo(), out.getDeliveryOrderNo(), out.getDeliveryOrderDate(),
								out.getDoValidityDate());
					}
				} else {
					int updategateOutId = cfigmcnrepo.updategateOutId2(cid, bid, g.getErpDocRefNo(), g.getDocRefNo(),
							"", out.getDeliveryOrderNo(), out.getDeliveryOrderDate(),
							out.getDoValidityDate());
				}

			}

		}

		if (out.getGateOutId() == null || out.getGateOutId().isEmpty()) {
			List<GateOut> outData = gateOutRepo.getData(cid, bid, HoldNextIdD1);

			String partyName = partyrepo.getPartyNameById(cid, bid, outData.get(0).getSl());
			String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, outData.get(0).getProfitcentreId());

			Map<String, Object> result = new HashMap<>();
			result.put("gatePassData", outData);
			result.put("sl", partyName);
			result.put("profit", profitCentreName);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} else {
			List<GateOut> outData = gateOutRepo.getData(cid, bid, out.getGateOutId());

			String partyName = partyrepo.getPartyNameById(cid, bid, outData.get(0).getSl());
			String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, outData.get(0).getProfitcentreId());

			Map<String, Object> result = new HashMap<>();
			result.put("gatePassData", outData);
			result.put("sl", partyName);
			result.put("profit", profitCentreName);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

	}
	
	@GetMapping("/searchGateOutData")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name = "val",required = false) String val){
		List<Object[]> data = gateOutRepo.search(cid, bid, val);

		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);
	}
	
	@GetMapping("/getSelectedGateOutData")
	public ResponseEntity<?> getSelectedGateOutData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("gateout") String gateout,@RequestParam("gatepass") String gatepass){
		List<GateOut> outData = gateOutRepo.getDataByGateOutIdAndGatePassNo(cid, bid, gateout,gatepass);

		if(outData.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		String partyName = partyrepo.getPartyNameById(cid, bid, outData.get(0).getSl());
		String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, outData.get(0).getProfitcentreId());

		Map<String, Object> result = new HashMap<>();
		result.put("gatePassData", outData);
		result.put("sl", partyName);
		result.put("profit", profitCentreName);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	
	@GetMapping("/getItems")
	public ResponseEntity<?> getItems(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("igm") String igm,@RequestParam("igmtrans") String igmtrans,@RequestParam("line") String line){
		System.out.println(cid+" "+ bid+" "+igm+" "+igmtrans+" "+line);
		List<String> data = importgatepassrepo.getItems(cid, bid, igm, igmtrans, line);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	@GetMapping("/getLatestRecord")
	public ResponseEntity<?> getLatestRecord(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("igm") String igm,@RequestParam("igmtrans") String igmtrans,@RequestParam("line") String line){
		
		GateOut data = gateOutRepo.getLatestRecordsGateOutId(cid, bid, igm, igmtrans, line);

		if(data == null) {  // Also check if data is empty
		    return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}


		List<GateOut> outData = gateOutRepo.getLatestRecordsFromIgm(cid, bid, igm,igmtrans,line,data.getGateOutId());

		if(outData.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		String partyName = partyrepo.getPartyNameById(cid, bid, outData.get(0).getSl());
		String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, outData.get(0).getProfitcentreId());

		Map<String, Object> result = new HashMap<>();
		result.put("gatePassData", outData);
		result.put("sl", partyName);
		result.put("profit", profitCentreName);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getGatePassIdFromContainerNo")
	public ResponseEntity<?> getGatePassIdFromContainerNo(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("igm") String igm,@RequestParam("igmtrans") String igmtrans,@RequestParam("con") String con){
		
		String gatePassId = importgatepassrepo.getGatePassIdFromContainerNo(cid, bid, igm, igmtrans, con);
		
		if(gatePassId == null || gatePassId.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(gatePassId,HttpStatus.OK);
	}
	
	@GetMapping("/getLatestRecordFromContainer")
	public ResponseEntity<?> getLatestRecordFromContainer(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("igm") String igm,@RequestParam("igmtrans") String igmtrans,@RequestParam("con") String con){
		
		GateOut data = gateOutRepo.getLatestRecordsGateOutIdByContainer(cid, bid, igm, igmtrans, con);

		if(data == null) {  // Also check if data is empty
		    return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}


		List<GateOut> outData = gateOutRepo.getLatestRecordsFromIgmByContainer(cid, bid, igm,igmtrans,con,data.getGateOutId());

		if(outData.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		String partyName = partyrepo.getPartyNameById(cid, bid, outData.get(0).getSl());
		String profitCentreName = profitcentrerepo.getAllDataByID1(cid, bid, outData.get(0).getProfitcentreId());

		Map<String, Object> result = new HashMap<>();
		result.put("gatePassData", outData);
		result.put("sl", partyName);
		result.put("profit", profitCentreName);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
