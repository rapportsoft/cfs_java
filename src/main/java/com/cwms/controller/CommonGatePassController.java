package com.cwms.controller;

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

import com.cwms.entities.CommonGatePass;
import com.cwms.entities.EmptyJobOrder;
import com.cwms.entities.GateOut;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CommonGatePassRepo;
import com.cwms.repository.EmptyInventoryRepo;
import com.cwms.repository.EmptyJobOrderRepo;
import com.cwms.repository.ImportGateOutRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("/commonGatePass")
public class CommonGatePassController {

	@Autowired
	private EmptyJobOrderRepo emptyJobOrderRepo;

	@Autowired
	private EmptyInventoryRepo emptyinventoryrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CommonGatePassRepo commongatepassrepo;

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;

	@Autowired
	private ImportGateOutRepository gateOutRepo;

	@GetMapping("/getDataForGatePass")
	public ResponseEntity<?> getDataForGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = emptyJobOrderRepo.getDataForGatePass(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getSelectedDataForGatePass")
	public ResponseEntity<?> getSelectedDataForGatePass(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = emptyJobOrderRepo.getSelectedDataForGatePass(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveCommonGatePass")
	public ResponseEntity<?> saveCommonGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		CommonGatePass common = mapper.readValue(mapper.writeValueAsString(data.get("commonData")),
				CommonGatePass.class);

		List<CommonGatePass> commonData = mapper.readValue(mapper.writeValueAsString(data.get("multipleCommonData")),
				new TypeReference<List<CommonGatePass>>() {
				});

		if (commonData.isEmpty()) {
			return new ResponseEntity<>("Empty gate pass data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05076", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("EGPA%06d", nextNumericNextID1);

		for (CommonGatePass c : commonData) {
			EmptyJobOrder existingJobOrder = emptyJobOrderRepo.getSingleData1(cid, bid, c.getJobTransId(), c.getSrNo());
			if (existingJobOrder == null) {
				return new ResponseEntity<>("Empty job order data not found", HttpStatus.CONFLICT);
			}

			if (c.getGatePassId() == null || c.getGatePassId().isEmpty()) {
				c.setCha(common.getCha());
				c.setBookingNo(common.getBookingNo());
				c.setJobTransDate(existingJobOrder.getJobTransDate());
				c.setJobTransId(existingJobOrder.getJobTransId());
				c.setCompanyId(cid);
				c.setBranchId(bid);
				c.setContainerHealth(existingJobOrder.getContainerHealth());
				c.setContainerStatus(existingJobOrder.getContainerStatus());
				c.setCreatedBy(user);
				c.setCreatedDate(new Date());
				c.setDeStuffId(existingJobOrder.getDeStuffId());
				c.setDoDate(common.getDoDate());
				c.setDoNo(common.getDoNo());
				c.setDoValidityDate(common.getDoValidityDate());
				c.setDriverName(common.getDriverName());
				c.setVehicleNo(common.getVehicleNo());
				c.setVehicleId(common.getVehicleId());
				c.setShipper(common.getShipper());
				c.setGateInDate(new Date());
				c.setSl(existingJobOrder.getSl());
				c.setOnAccountOf(existingJobOrder.getOnAccountOf());
				c.setMovementCode(existingJobOrder.getMovementCode());
				c.setMovementType(existingJobOrder.getMovementType());
				c.setGatePassId(HoldNextIdD1);
				c.setSa(existingJobOrder.getSa());
				c.setStatus("A");

				commongatepassrepo.save(c);

				existingJobOrder.setShipper(common.getShipper());
				existingJobOrder.setCha(common.getCha());
				existingJobOrder.setDoNo(common.getDoNo());
				existingJobOrder.setDoDate(common.getDoDate());
				existingJobOrder.setDoValidityDate(common.getDoValidityDate());
				existingJobOrder.setGatePassId(HoldNextIdD1);

				emptyJobOrderRepo.save(existingJobOrder);

				processnextidrepo.updateAuditTrail(cid, bid, "P05076", HoldNextIdD1, "2024");
				
				VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, common.getVehicleId());

				if (track != null) {
					track.setGatePassNo(HoldNextIdD1);

					vehicleTrackRepo.save(track);
				}
			} else {

				CommonGatePass exist = commongatepassrepo.getSingleData(cid, bid, c.getJobTransId(), c.getGatePassId(),
						c.getSrNo());

				if (exist == null) {
					return new ResponseEntity<>("Empty gate pass data not found", HttpStatus.CONFLICT);
				}
				
				String existVeh = exist.getVehicleId();
				
				exist.setShipper(common.getShipper());
				exist.setCha(common.getCha());
				exist.setDoNo(common.getDoNo());
				exist.setDoDate(common.getDoDate());
				exist.setDoValidityDate(common.getDoValidityDate());
				exist.setVehicleId(common.getVehicleId());
				exist.setVehicleNo(common.getVehicleNo());

				commongatepassrepo.save(exist);

				existingJobOrder.setShipper(common.getShipper());
				existingJobOrder.setCha(common.getCha());
				existingJobOrder.setDoNo(common.getDoNo());
				existingJobOrder.setDoDate(common.getDoDate());
				existingJobOrder.setDoValidityDate(common.getDoValidityDate());

				emptyJobOrderRepo.save(existingJobOrder);
				
				if(!common.getVehicleId().equals(existVeh)) {
					VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, common.getVehicleId());

					if (track != null) {
						track.setGatePassNo(exist.getGatePassId());

						vehicleTrackRepo.save(track);
					}
					
					
					VehicleTrack track1 = vehicleTrackRepo.getByGateInId(cid, bid, existVeh);

					if (track1 != null) {
						track1.setGatePassNo("");

						vehicleTrackRepo.save(track1);
					}
				}
			}

		}

		
		if (common.getGatePassId() == null || common.getGatePassId().isEmpty()) {
			List<Object[]> gatePassData = commongatepassrepo.getGatePassData(cid, bid, common.getJobTransId(),
					HoldNextIdD1);

			return new ResponseEntity<>(gatePassData, HttpStatus.OK);
		} else {
			List<Object[]> gatePassData = commongatepassrepo.getGatePassData(cid, bid, common.getJobTransId(),
					common.getGatePassId());

			return new ResponseEntity<>(gatePassData, HttpStatus.OK);
		}

	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = commongatepassrepo.search(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getGatePassData")
	public ResponseEntity<?> getGatePassData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("job") String job, @RequestParam("gate") String gate) {
		List<Object[]> data = commongatepassrepo.getGatePassData(cid, bid, job, gate);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getPendingVehicleData")
	public ResponseEntity<?> getPendingVehicleData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = commongatepassrepo.getPendingVehicleData(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getPendingVehicleDataByGatepassId")
	public ResponseEntity<?> getPendingVehicleDataByGatepassId(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = commongatepassrepo.getGatePassDataForGateOut(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveGateOutData")
	public ResponseEntity<?> saveGateOutData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		GateOut gateout = mapper.readValue(mapper.writeValueAsString(data.get("gateOutData")), GateOut.class);

		List<GateOut> out = mapper.readValue(mapper.writeValueAsString(data.get("multipleGateOutData")),
				new TypeReference<List<GateOut>>() {
				});

		if (out.isEmpty()) {
			return new ResponseEntity<>("Empty gate out data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05077", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("EGOT%06d", nextNumericNextID1);

		for (GateOut g : out) {
			CommonGatePass exist = commongatepassrepo.getSingleData(cid, bid, g.getErpDocRefNo(), g.getGatePassNo(),
					Integer.parseInt(g.getSrNo()));

			if (exist == null) {
				return new ResponseEntity<>("Empty gate pass data not found", HttpStatus.CONFLICT);
			}
			
			EmptyJobOrder existingJobOrder = emptyJobOrderRepo.getSingleData1(cid, bid, g.getErpDocRefNo(), Integer.parseInt(g.getSrNo()));
			if (existingJobOrder == null) {
				return new ResponseEntity<>("Empty job order data not found", HttpStatus.CONFLICT);
			}

			if (g.getGateOutId() == null || g.getGateOutId().isEmpty()) {
				g.setCompanyId(cid);
				g.setBranchId(bid);
				g.setStatus('A');
				g.setCreatedBy(user);
				g.setCreatedDate(new Date());
				g.setApprovedBy(user);
				g.setApprovedDate(new Date());
				g.setSl(exist.getSl());
				g.setOnAccountOf(exist.getOnAccountOf());
				g.setShipper(exist.getShipper());
				g.setSa(exist.getSa());
				g.setProcessId("P00605");
				g.setVehicleId(gateout.getVehicleId());
				g.setVehicleNo(gateout.getVehicleNo());
				g.setGateOutId(HoldNextIdD1);
				g.setGateOutDate(new Date());

				gateOutRepo.save(g);

				processnextidrepo.updateAuditTrail(cid, bid, "P05077", HoldNextIdD1, "2024");
				
				existingJobOrder.setGateOutId(HoldNextIdD1);
				emptyJobOrderRepo.save(existingJobOrder);
				
				exist.setGateOutId(HoldNextIdD1);
				commongatepassrepo.save(exist);

				VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, gateout.getVehicleId());

				if (track != null) {
					track.setGateOutId(HoldNextIdD1);
					track.setGateOutDate(new Date());

					vehicleTrackRepo.save(track);
				}
				if (!gateout.getVehicleId().equals(exist.getVehicleId())) {
					VehicleTrack track1 = vehicleTrackRepo.getByGateInId(cid, bid, exist.getVehicleId());
					
					exist.setVehicleId(gateout.getVehicleId());
					exist.setVehicleNo(gateout.getVehicleNo());
					exist.setGateOutId(HoldNextIdD1);
					commongatepassrepo.save(exist);

					

					if (track1 != null) {
						track1.setGateOutId("");
						track1.setGateOutDate(null);
						track1.setGatePassNo("");

						vehicleTrackRepo.save(track1);
					}
				}
				
				

			} else {
				GateOut g1 = gateOutRepo.getSingleData(cid, bid, g.getErpDocRefNo(), g.getDocRefNo(), g.getGateOutId(),
						g.getSrNo());

				if (g1 == null) {
					return new ResponseEntity<>("Empty gate out data not found", HttpStatus.CONFLICT);
				}

				g1.setVehicleId(gateout.getVehicleId());
				g1.setVehicleNo(gateout.getVehicleNo());
				g1.setEditedBy(user);
				g1.setEditedDate(new Date());

				gateOutRepo.save(g1);

				
				
				if (!gateout.getVehicleId().equals(exist.getVehicleId())) {
					VehicleTrack track1 = vehicleTrackRepo.getByGateInId(cid, bid, exist.getVehicleId());
					
					exist.setVehicleId(gateout.getVehicleId());
					exist.setVehicleNo(gateout.getVehicleNo());

					commongatepassrepo.save(exist);

					

					if (track1 != null) {
						track1.setGateOutId("");
						track1.setGatePassNo("");
						track1.setGateOutDate(null);

						vehicleTrackRepo.save(track1);
					}
					
					VehicleTrack track = vehicleTrackRepo.getByGateInId(cid, bid, gateout.getVehicleId());

					if (track != null) {
						track.setGateOutId(g.getGateOutId());
						track.setGateOutDate(new Date());

						vehicleTrackRepo.save(track);
					}
				}
				
			}

			

		}

		if (gateout.getGateOutId() == null || gateout.getGateOutId().isEmpty()) {
			List<Object[]> outData = gateOutRepo.getGateOutData(cid, bid, gateout.getGatePassNo(), HoldNextIdD1);

			return new ResponseEntity<>(outData, HttpStatus.OK);
		} else {
			List<Object[]> outData = gateOutRepo.getGateOutData(cid, bid, gateout.getGatePassNo(), gateout.getGateOutId());

			return new ResponseEntity<>(outData, HttpStatus.OK);
		}

	}
	
	@GetMapping("/searchGateOut")
	public ResponseEntity<?> searchGateOut(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = gateOutRepo.searchGateOut(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getGateOutData")
	public ResponseEntity<?> getGateOutData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("gatepass") String gatepass, @RequestParam("gateout") String gateout) {
		List<Object[]> outData = gateOutRepo.getGateOutData(cid, bid, gatepass, gateout);

		if (outData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(outData, HttpStatus.OK);
	}

}
