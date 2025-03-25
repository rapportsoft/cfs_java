package com.cwms.controller;

import java.util.Date;
import java.util.List;

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

import com.cwms.entities.GateOut;
import com.cwms.repository.GateOutRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/portEir")
public class PortEIRController {

	@Autowired
	private GateOutRepo gateoutrepo;
	
	@GetMapping("/getDataBeforeSavePortEIR")
	public ResponseEntity<?> getDataBeforeSavePortEIR(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id){
		
		try {
			
			List<Object[]> data = gateoutrepo.getDataBeforeSavePortEIR(cid, bid, id);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			else {
				return new ResponseEntity<>(data,HttpStatus.OK);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getSelectDataBeforeSavePortEIR")
	public ResponseEntity<?> getSelectDataBeforeSavePortEIR(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("sr") String sr){
		
		try {
			
			Object data = gateoutrepo.gateOutDataFOrEirENtry(cid, bid, id, sr);
			
			if(data == null) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			else {
				return new ResponseEntity<>(data,HttpStatus.OK);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/saveEirEntry")
	public ResponseEntity<?> saveEirEntry(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("user") String user,@RequestBody GateOut out){
		
		try {
			
			GateOut data = gateoutrepo.gateOutDataFOrEirENtry1(cid, bid, out.getGateOutId(), out.getSrNo());
			
			if(data == null) {
				return new ResponseEntity<>("Gate out data not found",HttpStatus.CONFLICT);
			}
			else {
				
				data.setEirStatus('A');
				data.setEirNo(out.getEirNo());
				data.setEirDate(out.getEirDate());
				data.setEirApprovedBy(user);
				data.setEirApprovedDate(new Date());
				data.setEirCreatedBy(user);
				data.setEirCreatedDate(new Date());
				
				gateoutrepo.save(data);
				
				Object result = gateoutrepo.gateOutDataFOrEirENtry(cid, bid, out.getGateOutId(), out.getSrNo());
				
				if(result == null) {
					return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
				}
				else {
					return new ResponseEntity<>(result,HttpStatus.OK);

				}

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/searchData")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id){
		
		try {
			
			List<Object[]> data = gateoutrepo.searchData(cid, bid, id);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			else {
				return new ResponseEntity<>(data,HttpStatus.OK);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
}
