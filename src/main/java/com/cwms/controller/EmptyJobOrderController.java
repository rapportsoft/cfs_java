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

import com.cwms.entities.EmptyInventory;
import com.cwms.entities.EmptyJobOrder;
import com.cwms.repository.EmptyInventoryRepo;
import com.cwms.repository.EmptyJobOrderRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/emptyOrder")
public class EmptyJobOrderController {

	@Autowired
	private EmptyJobOrderRepo emptyJobOrderRepo;

	@Autowired
	private EmptyInventoryRepo emptyinventoryrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@GetMapping("/getShipperRecords")
	public List<Object[]> getShipperRecords(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getDataForExp(cid, bid, val);

		return getPort;
	}

	@GetMapping("/getEmptyInventoryData")
	public ResponseEntity<?> getEmptyInventoryData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = emptyinventoryrepo.getInventoryData(cid, bid, val);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		EmptyJobOrder job = mapper.readValue(mapper.writeValueAsString(data.get("jobData")), EmptyJobOrder.class);

		List<EmptyJobOrder> jobData = mapper.readValue(mapper.writeValueAsString(data.get("multipleJobData")),
				new TypeReference<List<EmptyJobOrder>>() {
				});

		if (jobData.isEmpty()) {
			return new ResponseEntity<>("Empty job order data not found", HttpStatus.CONFLICT);
		}

		int sr = 1;

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05075", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("EJID%06d", nextNumericNextID1);

		if (job.getJobTransId() == null || job.getJobTransId().isEmpty()) {
			Boolean exist = emptyJobOrderRepo.isExistBookingNo(cid, bid, job.getBookingNo());

			if (exist) {
				return new ResponseEntity<>("Booking no already exist.", HttpStatus.CONFLICT);
			}
		} else {
			Boolean exist = emptyJobOrderRepo.isExistBookingNo1(cid, bid, job.getBookingNo(), job.getJobTransId());
			
			if (exist) {
				return new ResponseEntity<>("Booking no already exist.", HttpStatus.CONFLICT);
			}
		}

		for (EmptyJobOrder j : jobData) {
			if (j.getJobTransId() == null || j.getJobTransId().isEmpty()) {
				EmptyInventory inv = emptyinventoryrepo.getById1(cid, bid, j.getErpDocRefNo(), j.getDocRefNo(),
						j.getGateInId(), j.getContainerNo());

				if (inv == null) {
					return new ResponseEntity<>("Empty inventory data not found", HttpStatus.CONFLICT);
				}
				j.setCompanyId(cid);
				j.setBranchId(bid);
				j.setSa(inv.getSa());
				j.setJobTransId(HoldNextIdD1);
				j.setSrNo(sr);
				j.setJobTransDate(new Date());
				j.setCreatedBy(user);
				j.setCreatedDate(new Date());
				j.setStatus("A");
				j.setSl(job.getSl());
				j.setOnAccountOf(job.getOnAccountOf());
				j.setBookingNo(job.getBookingNo());
				j.setDoNo(job.getDoNo());
				j.setDoDate(job.getDoDate());
				j.setDoValidityDate(job.getDoValidityDate());
				j.setShipper(job.getShipper());
				j.setCha(job.getCha());
				j.setProfitcentreId(job.getProfitcentreId());
				j.setMovementType(job.getMovementType());
				j.setContainerHealth(job.getContainerHealth());
				j.setToLocation(job.getToLocation());
				j.setContainerHealth(job.getContainerHealth());

				emptyJobOrderRepo.save(j);

				inv.setEmptyJobOrder(HoldNextIdD1);
				emptyinventoryrepo.save(inv);

				processnextidrepo.updateAuditTrail(cid, bid, "P05075", HoldNextIdD1, "2024");

				sr++;
			} else {
				EmptyJobOrder existing = emptyJobOrderRepo.getSingleData(cid, bid, j.getJobTransId(),
						j.getErpDocRefNo(), j.getDocRefNo(), j.getSrNo());

				if (existing == null) {
					return new ResponseEntity<>("Empty job order data not found", HttpStatus.CONFLICT);
				}

				existing.setSl(job.getSl());
				existing.setOnAccountOf(job.getOnAccountOf());
				existing.setBookingNo(job.getBookingNo());
				existing.setDoNo(job.getDoNo());
				existing.setDoDate(job.getDoDate());
				existing.setDoValidityDate(job.getDoValidityDate());
				existing.setShipper(job.getShipper());
				existing.setCha(job.getCha());
				existing.setProfitcentreId(job.getProfitcentreId());
				existing.setMovementType(job.getMovementType());
				existing.setContainerHealth(job.getContainerHealth());
				existing.setToLocation(job.getToLocation());
				existing.setContainerHealth(job.getContainerHealth());

				emptyJobOrderRepo.save(existing);
			}
		}

		if (job.getJobTransId() == null || job.getJobTransId().isEmpty()) {
			List<Object[]> getData = emptyJobOrderRepo.getData(cid, bid, HoldNextIdD1);
			return new ResponseEntity<>(getData, HttpStatus.OK);
		} else {
			List<Object[]> getData = emptyJobOrderRepo.getData(cid, bid, job.getJobTransId());
			return new ResponseEntity<>(getData, HttpStatus.OK);
		}

	}

	@GetMapping("/search")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = emptyJobOrderRepo.search(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getDataByJobTransId")
	public ResponseEntity<?> getDataByJobTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = emptyJobOrderRepo.getData(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
