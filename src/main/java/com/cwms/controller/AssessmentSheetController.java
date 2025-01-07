package com.cwms.controller;

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

import com.cwms.repository.PartyRepository;
import com.cwms.service.AssessmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/assessment")
public class AssessmentSheetController {

	 @Autowired
	 private AssessmentService assessmentService;
	 
	 @Autowired
	 private PartyRepository partyRepo;
	 
	 @GetMapping("/searchImportBeforeSaveAssessmentData")
	 public ResponseEntity<?> searchImportBeforeSaveAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam(name="val",required = false) String val){
		 return assessmentService.searchImportBeforeSaveAssessmentData(cid, bid, val);
	 }
	 
	 
	 @GetMapping("/getImportBeforeSaveAssessmentData")
	 public ResponseEntity<?> getImportBeforeSaveAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("trans") String trans,@RequestParam("igm") String igm,@RequestParam("lineNo") String lineNo){
		 return assessmentService.getImportBeforeSaveAssessmentData(cid, bid, trans, igm, lineNo);
	 }
	 
	 
	 
	 @PostMapping("/saveAssessmentData")
	 public ResponseEntity<?> saveAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException{
		 return assessmentService.saveAssessmentData(cid, bid, user, assessmentData);
	 }
	 
	 
	 @PostMapping("/saveImportInvoiceReceipt")
	 public ResponseEntity<?> saveImportInvoiceReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException{
		 return assessmentService.saveImportInvoiceReceipt(cid, bid, user, assessmentData);
	 }
	 
	 @GetMapping("/getTdsPerc")
	 public ResponseEntity<?> getTdsPerc(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("val") String val){
		 String tdsPerc = partyRepo.getTdsById(cid, bid, val);
		 
		 if(tdsPerc == null || tdsPerc.isEmpty()) {
			 return new ResponseEntity<>("Not found",HttpStatus.CONFLICT);
		 }
		 else {
			 return new ResponseEntity<>(tdsPerc,HttpStatus.OK);
		 }
	 }
	 
	 
	 @GetMapping("/searchInvoiceData1")
	 public ResponseEntity<?> searchInvoiceData1(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam(name="val",required = false) String val){
		 return assessmentService.searchInvoiceData1(cid, bid, val);
	 }
	 
	 @GetMapping("/getSelectedInvoiceData1")
	 public ResponseEntity<?> getSelectedInvoiceData1(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("assId") String assId,@RequestParam("invId") String invId){
		 return assessmentService.getSelectedInvoiceData1(cid, bid, assId, invId);
	 }
}
