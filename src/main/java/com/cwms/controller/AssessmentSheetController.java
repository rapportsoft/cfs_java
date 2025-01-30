package com.cwms.controller;

import java.text.ParseException;
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
	 public ResponseEntity<?> saveImportInvoiceReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("creditStatus") String creditStatus,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException{
		 
		 if("Y".equals(creditStatus)) {
			 return assessmentService.saveImportInvoiceCreditReceipt(cid, bid, user, assessmentData);
		 }
		 else if("P".equals(creditStatus)) {
			 return assessmentService.saveImportInvoicePdaReceipt(cid, bid, user, assessmentData);
		 }
		 else {
			 
			 
			 return assessmentService.saveImportInvoiceReceipt(cid, bid, user, assessmentData);

		 }
	 }
	 
	 @GetMapping("/getTdsPerc")
	 public ResponseEntity<?> getTdsPerc(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("val") String val){
		 Object tdsPerc = partyRepo.getTdsById(cid, bid, val);
		 
		 if(tdsPerc == null) {
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
	 
	 
	 
	 @GetMapping("/searchBONDNOCBeforeSaveAssessmentData")
	 public ResponseEntity<?> searchBONDNOCBeforeSaveAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam(name="val",required = false) String val){
		 return assessmentService.searchBONDNOCBeforeSaveAssessmentData(cid, bid, val);
	 }
	 
	 
	 @GetMapping("/getBONDNOCBeforeSaveAssessmentData")
	 public ResponseEntity<?> getBONDNOCBeforeSaveAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("trans") String trans,@RequestParam("boe") String boe){
		 return assessmentService.getBONDNOCBeforeSaveAssessmentData(cid, bid, trans, boe);
	 }
	 
	 @PostMapping("/saveBondNocAssessmentData")
	 public ResponseEntity<?> saveBondNocAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException{
		 return assessmentService.saveBondNOCAssessmentData(cid, bid, user, assessmentData);
	 }
	 
	 
	 @PostMapping("/saveBondNocInvoiceReceipt")
	 public ResponseEntity<?> saveBondNocInvoiceReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException{
		 return assessmentService.saveBondNocInvoiceReceipt(cid, bid, user, assessmentData);
	 }
	 
	 
	 @GetMapping("/searchBondNOCInvoiceData")
	 public ResponseEntity<?> searchBondNOCInvoiceData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam(name="val",required = false) String val,@RequestParam("type") String type){
		 return assessmentService.searchBondNOCInvoiceData(cid, bid, val, type);
	 }
	 
	 @GetMapping("/getSelectedBondNOCInvoiceData")
	 public ResponseEntity<?> getSelectedBondNOCInvoiceData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("assId") String assId,@RequestParam("invId") String invId,@RequestParam("type") String type){
		 return assessmentService.getSelectedBondNOCInvoiceData(cid, bid, assId, invId, type);
	 }
	 
	 @GetMapping("/searchExbondBeforeData")
	 public ResponseEntity<?> searchExbondBeforeData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam(name="val",required = false) String val){
		 return assessmentService.searchExbondbeNo(cid, bid, val);
	 }
	 
	 
	 @GetMapping("/getExbondBeforeSaveAssessmentData")
	 public ResponseEntity<?> getExbondBeforeSaveAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("val") String val){
		 return assessmentService.getExbondBeforeSaveAssessmentData(cid, bid, val);
	 }
	 
	 
	 @PostMapping("/saveExbondAssessmentData")
	 public ResponseEntity<?> saveExbondAssessmentData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException{
		 return assessmentService.saveExbondAssessmentData(cid, bid, user, assessmentData);
	 }
	 
	 @PostMapping("/saveExBondInvoiceReceipt")
	 public ResponseEntity<?> saveExBondInvoiceReceipt(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException{
		 return assessmentService.saveExbondInvoiceReceipt(cid, bid, user, assessmentData);
	 }
	 
}
