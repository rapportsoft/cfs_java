package com.cwms.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.BufferWorkOrder;
import com.cwms.repository.BufferWorkOrderRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/bufferWO")
public class BufferWorkOrderController {

	@Autowired
	private BufferWorkOrderRepo bufferworepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Transactional
	@PostMapping("/saveWO")
	public ResponseEntity<?> saveWo(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data) {
		try {
			ObjectMapper mapper = new ObjectMapper();

			BufferWorkOrder buffer = mapper.readValue(mapper.writeValueAsString(data.get("bufferWorkOrder")),
					BufferWorkOrder.class);

			if (buffer == null) {
				return new ResponseEntity<>("Work order data not found", HttpStatus.CONFLICT);
			}

			List<BufferWorkOrder> containerData = mapper.readValue(mapper.writeValueAsString(data.get("containerData")),
					new TypeReference<List<BufferWorkOrder>>() {
					});

			if (containerData.isEmpty()) {
				return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
			}

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05132", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("EBWO%06d", nextNumericNextID1);

			int sr = 1;

			for (BufferWorkOrder c : containerData) {
				if (c.getContainerNo() != null && !c.getContainerNo().isEmpty()) {

					boolean check = bufferworepo.checkContainerNoAlreadyInInventory(cid, bid, c.getContainerNo());

					if (check) {
						return new ResponseEntity<>(c.getContainerNo() + " already in inventory", HttpStatus.CONFLICT);
					}
                    c.setCompanyId(cid);
                    c.setBranchId(bid);
					c.setStatus("A");
					c.setCreatedBy(user);
					c.setCreatedDate(new Date());
					c.setApprovedBy(user);
					c.setApprovedDate(new Date());
					c.setCha(buffer.getCha());
					c.setOnAccountOf(buffer.getOnAccountOf());
					c.setShippingLine(buffer.getShippingLine());
					c.setShipper(buffer.getShipper());
					c.setBookingNo(buffer.getBookingNo());
					c.setMoveFrom(buffer.getMoveFrom());
					c.setContainerStatus(buffer.getContainerStatus());
					c.setProfitcentreId("N00004");
					c.setWoDate(new Date());
					c.setSrNo(sr);
					c.setWoNo(HoldNextIdD1);

					bufferworepo.save(c);

					processnextidrepo.updateAuditTrail(cid, bid, "P05132", HoldNextIdD1, "2024");

					sr++;
				}
			}

			List<Object[]> result = bufferworepo.getDataByWoNo(cid, bid, HoldNextIdD1);

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/searchData")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required =  false) String val){
		
		try {
			
			List<Object[]> result = bufferworepo.searchData(cid, bid, val);
			
			if(result.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getSelectedSearchData")
	public ResponseEntity<?> getSelectedSearchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val){
		
		try {
			
			List<Object[]> result = bufferworepo.getDataByWoNo(cid, bid, val);
			
			if(result.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/searchContainer")
	public ResponseEntity<?> searchContainer(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val){
		
		try {
			
			List<Object[]> result = bufferworepo.searchContainer(cid, bid, val);
			
			if(result.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getSelectedContainer")
	public ResponseEntity<?> getSelectedContainer(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("val") String val,@RequestParam("con") String con){
		
		try {
			
			Object result = bufferworepo.getDataByWoNoAndContainerNo(cid, bid, val, con);
			
			if(result == null) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
