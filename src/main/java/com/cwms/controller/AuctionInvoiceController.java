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

import com.cwms.repository.AuctionRecordingRepo;
import com.cwms.service.AuctionInvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin("*")
@RequestMapping("/auctionInvoice")
public class AuctionInvoiceController {

	@Autowired
	private AuctionRecordingRepo auctionRecordingRepo;

	@Autowired
	private AuctionInvoiceService auctionInvoiceService;

//	
//	@GetMapping("/searchBeforeSaveInvoice")
//	public ResponseEntity<?> searchBeforeSaveInvoice(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
//			@RequestParam(name="id",required = false) String id){
//		
//		return auctionInvoiceService.searchBeforeSaveInvoice(cid, bid, id);
//	}

	@GetMapping("/searchAuctionBeforeSaveAssessmentData")
	public ResponseEntity<?> searchAuctionBeforeSaveAssessmentData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {
		return auctionInvoiceService.searchImportBeforeSaveAssessmentData(cid, bid, val);
	}

	@GetMapping("/getAuctionBeforeSaveAssessmentData")
	public ResponseEntity<?> getAuctionBeforeSaveAssessmentData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("trans") String trans, @RequestParam("igm") String igm,
			@RequestParam("lineNo") String lineNo, @RequestParam("id") String id) {
		return auctionInvoiceService.getImportBeforeSaveAssessmentData(cid, bid, trans, igm, lineNo, id);
	}

	@PostMapping("/saveAssessmentData")
	public ResponseEntity<?> saveAssessmentData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
			throws JsonMappingException, JsonProcessingException {

		return auctionInvoiceService.saveAssessmentData(cid, bid, user, assessmentData);

	}

	@PostMapping("/saveAuctionInvoiceReceipt")
	public ResponseEntity<?> saveImportInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
			@RequestBody Map<String, Object> assessmentData)
			throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {

		if ("Y".equals(creditStatus)) {
			return auctionInvoiceService.saveImportInvoiceCreditReceipt(cid, bid, user, assessmentData);
		} else if ("P".equals(creditStatus)) {
			return auctionInvoiceService.saveImportInvoicePdaReceipt(cid, bid, user, assessmentData);
		} else {

			return auctionInvoiceService.saveImportInvoiceReceipt(cid, bid, user, assessmentData);

		}
	}

	@GetMapping("/searchInvoiceData1")
	public ResponseEntity<?> searchInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		return auctionInvoiceService.searchInvoiceData1(cid, bid, val);
	}

	@GetMapping("/getSelectedInvoiceData1")
	public ResponseEntity<?> getSelectedInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("assId") String assId, @RequestParam("invId") String invId) {
		return auctionInvoiceService.getSelectedInvoiceData1(cid, bid, assId, invId);
	}

	@PostMapping("/saveAddServiceContainerWise")
	public ResponseEntity<?> saveAddServiceContainerWise(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody Map<String, Object> requestData,
			@RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException {
		ResponseEntity<?> addExportAssesmentSheet = auctionInvoiceService.saveAddServiceContainerWise(companyId, branchId,
				user, requestData);
		return addExportAssesmentSheet;
	}
	
	@PostMapping("/saveAddServiceServiceWise")
	public ResponseEntity<?> saveAddServiceServiceWise(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = auctionInvoiceService.saveAddServiceServiceWise(companyId, branchId, user, requestData);
			return addExportAssesmentSheet;
		}
	
	@GetMapping("/getDataByAfterAssessment")
	 public ResponseEntity<?> getDataByAfterAssessment(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("id") String id,
			 @RequestParam("partyId") String partyId,@RequestParam("creditType") String creditType){
		 
		 return auctionInvoiceService.getDataByAssessmentId(cid, bid, id, partyId, creditType);
	 }
	
	
	 @GetMapping("/getAllContainerListOfAssessMentSheet")
		public ResponseEntity<?> getAllContainerListOfAssessMentSheet(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId) {
			ResponseEntity<?> partyByTypeValue = auctionInvoiceService.getAllContainerListOfAssessMentSheet(companyId, branchId, assesmentId, profiCentreId);
			return partyByTypeValue;
		}
}
