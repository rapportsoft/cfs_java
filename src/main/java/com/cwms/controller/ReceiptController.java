package com.cwms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.service.ReceiptService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.transaction.Transactional;

@CrossOrigin("*")
@RequestMapping("/receipt")
@RestController
public class ReceiptController {

	@Autowired
	private ReceiptService receiptService;
	
	
	@GetMapping("/advanceReceiptBeforeSaveSearch")
	public ResponseEntity<?> advanceReceiptBeforeSaveSearch(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("type") String type){
		if("AD".equals(type)) {
			return receiptService.advanceReceiptBeforeSaveSearch(cid, bid, id, type);
		}
		else if("RE".equals(type)) {
			return receiptService.receiptReceiptBeforeSaveSearch(cid, bid, id, type);
		}
		
		else {
			return receiptService.adjustmentBeforeSaveSearch(cid, bid, id, type);
		}
		
		
	}
	
	
	@PostMapping("/saveAdvanceReceipt")
	public ResponseEntity<?> saveAdvanceReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("user") String user,@RequestBody Map<String,Object> data) throws JsonMappingException, JsonProcessingException{
		return receiptService.saveAdvanceReceipt(cid, bid, user, data);
	}
	
	@Transactional
	@PostMapping("/saveReceipt")
	public ResponseEntity<?> saveReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("user") String user,@RequestParam("type") String type,@RequestBody Map<String,Object> data) throws JsonMappingException, JsonProcessingException{
		if("RE".equals(type)) {
			return receiptService.saveReceiptData(cid, bid, user, data);
		}
		else {
			return receiptService.saveAdjustmentData(cid, bid, user, data);
		}
	}
	
	@GetMapping("/getAfterSaveData")
	public ResponseEntity<?> searchAfterSaveData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="val",required = false) String val){
		return receiptService.getAfterSaveSearchData(cid, bid, val);
	}
	
	
	@GetMapping("/getReceiptSelectedData")
	public ResponseEntity<?> getReceiptSelectedData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("val") String val,@RequestParam("id") String id,@RequestParam("type") String type){
		return receiptService.getSelectedData(cid, bid, val, id, type);
	}
}
