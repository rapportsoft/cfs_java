package com.cwms.controller;

import java.text.ParseException;
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

import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.TaxRepository;
import com.cwms.service.AssessmentService;
import com.cwms.service.GeneralAssessmentService;
import com.cwms.service.MiscellaneousService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/assessment")
public class AssessmentSheetController {

	@Autowired
	private AssessmentService assessmentService;

	@Autowired
	private CFSTarrifServiceRepository cfstarrifservicerepo;

	@Autowired
	private PartyRepository partyRepo;
	
    @Autowired
    private TaxRepository taxRepository;
    
    @Autowired
    private MiscellaneousService miscellaneousService;
    
	@Autowired
	private GeneralAssessmentService generalAssessmentService;
	 
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
			 @RequestParam("user") String user, @RequestBody Map<String,Object> assessmentData) throws JsonMappingException, JsonProcessingException, CloneNotSupportedException{
		 
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
		public ResponseEntity<?> saveBondNocInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
				@RequestBody Map<String, Object> assessmentData) throws JsonMappingException, JsonProcessingException {

			if ("Y".equals(creditStatus)) {
				return assessmentService.saveBondNocCreditInvoiceReceipt(cid, bid, user, assessmentData);
			} else if ("P".equals(creditStatus)) {
				return assessmentService.saveBondNocPdaInvoiceReceipt(cid, bid, user, assessmentData);
			} else {
				return assessmentService.saveBondNocInvoiceReceipt(cid, bid, user, assessmentData);
			}

			
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
		public ResponseEntity<?> saveExBondInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("creditStatus") String creditStatus,
				@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
				throws JsonMappingException, JsonProcessingException {

			if ("Y".equals(creditStatus)) {
				return assessmentService.saveExbondCreditInvoiceReceipt(cid, bid, user, assessmentData);
			} else if ("P".equals(creditStatus)) {
				return assessmentService.saveExbondPdaInvoiceReceipt(cid, bid, user, assessmentData);
			} else {
				return assessmentService.saveExbondInvoiceReceipt(cid, bid, user, assessmentData);
			}

			
		}
	 
	 
	 @GetMapping("/getDataByAfterAssessment")
	 public ResponseEntity<?> getDataByAfterAssessment(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("id") String id,
			 @RequestParam("partyId") String partyId,@RequestParam("creditType") String creditType){
		 
		 
		 System.out.println("id "+id);
		 return assessmentService.getDataByAssessmentId(cid, bid, id, partyId, creditType);
	 }

	 @PostMapping("/saveAddServiceContainerWise")
		public ResponseEntity<?> saveAddServiceContainerWise(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
				@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
			{				
				ResponseEntity<?> addExportAssesmentSheet = assessmentService.saveAddServiceContainerWise(companyId, branchId, user, requestData);
				return addExportAssesmentSheet;
			}
	 
	 
	 @GetMapping("/getAllContainerListOfAssessMentSheet")
		public ResponseEntity<?> getAllContainerListOfAssessMentSheet(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId) {
			ResponseEntity<?> partyByTypeValue = assessmentService.getAllContainerListOfAssessMentSheet(companyId, branchId, assesmentId, profiCentreId);
			return partyByTypeValue;
		}
	 
	 
	 
		
		@PostMapping("/saveAddServiceServiceWise")
		public ResponseEntity<?> saveAddServiceServiceWise(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
				@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
			{				
				ResponseEntity<?> addExportAssesmentSheet = assessmentService.saveAddServiceServiceWise(companyId, branchId, user, requestData);
				return addExportAssesmentSheet;
			}
			
		
		
		@PostMapping("/saveBondAddService")
		public ResponseEntity<?> saveBondAddService(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
				@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user,@RequestParam("type") String type) throws JsonMappingException, JsonProcessingException
			{				
				ResponseEntity<?> addExportAssesmentSheet = assessmentService.saveBondAddService(companyId, branchId, user, requestData,type);
				return addExportAssesmentSheet;
			}
		
		 @GetMapping("/getDataByAfterAssessmentForBond")
		 public ResponseEntity<?> getDataByAfterAssessmentForBond(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("id") String id,
				 @RequestParam("partyId") String partyId,@RequestParam("creditType") String creditType,@RequestParam("type") String type){

			 return assessmentService.getDataByAssessmentId1(cid, bid, id, partyId, creditType, type);
		 }
		 
		 @GetMapping("getServices")
			public ResponseEntity<?> getAllServices(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

				List<Object[]> serviceList = cfstarrifservicerepo.getGeneralTarrifData2(cid, bid);
				if (serviceList.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(serviceList, HttpStatus.OK);
			}
			
			@GetMapping("getTax")
			public ResponseEntity<?> getTax(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

				List<Object[]> serviceList = taxRepository.getTaxData(cid, bid);
				if (serviceList.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(serviceList, HttpStatus.OK);
			}
			
			
			@PostMapping("/saveMISCAssessmentData")
			public ResponseEntity<?> saveMISCAssessmentData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
					throws JsonMappingException, JsonProcessingException {

				return miscellaneousService.saveMiscellaneousData(cid, bid, user, assessmentData);

			}

			@PostMapping("/saveMISCInvoiceReceipt")
			public ResponseEntity<?> saveMISCInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
					@RequestBody Map<String, Object> assessmentData)
					throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {

				if ("Y".equals(creditStatus)) {
					return miscellaneousService.saveImportInvoiceCreditReceipt(cid, bid, user, assessmentData);
				} else if ("P".equals(creditStatus)) {
					return miscellaneousService.saveImportInvoicePdaReceipt(cid, bid, user, assessmentData);
				} else {

					return miscellaneousService.saveMISCInvoiceReceipt(cid, bid, user, assessmentData);

				}
			}
			
			@GetMapping("/searchMISCInvoiceData1")
			public ResponseEntity<?> searchMISCInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam(name = "val", required = false) String val) {
				return miscellaneousService.searchMISCInvoiceData1(cid, bid, val);
			}

			@GetMapping("/getSelectedMISCInvoiceData1")
			public ResponseEntity<?> getSelectedMISCInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam("assId") String assId, @RequestParam("invId") String invId) {
				return miscellaneousService.getSelectedMISCInvoiceData1(cid, bid, assId, invId);
			}
			
			
			// General Receiving Invoice

			@GetMapping("/searchGeneralJobBeforeSaveAssessmentData")
			public ResponseEntity<?> searchGeneralJobBeforeSaveAssessmentData(@RequestParam("cid") String cid,
					@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {
				return generalAssessmentService.searchGeneralJobBeforeSaveAssessmentData(cid, bid, val);
			}

			@GetMapping("/getGeneralJobBeforeSaveAssessmentData")
			public ResponseEntity<?> getGeneralJobBeforeSaveAssessmentData(@RequestParam("cid") String cid,
					@RequestParam("bid") String bid, @RequestParam("trans") String trans, @RequestParam("boe") String boe) {
				return generalAssessmentService.getGeneralJobBeforeSaveAssessmentData(cid, bid, trans, boe);
			}

			@PostMapping("/saveGeneralReceivingAssessmentData")
			public ResponseEntity<?> saveGeneralReceivingAssessmentData(@RequestParam("cid") String cid,
					@RequestParam("bid") String bid, @RequestParam("user") String user,
					@RequestBody Map<String, Object> assessmentData)
					throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {
				return generalAssessmentService.generalAssessmentService(cid, bid, user, assessmentData);
			}

			@PostMapping("/saveGeneralJobInvoiceReceipt")
			public ResponseEntity<?> saveGeneralJobInvoiceReceipt(@RequestParam("cid") String cid,
					@RequestParam("bid") String bid, @RequestParam("creditStatus") String creditStatus,
					@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
					throws JsonMappingException, JsonProcessingException {

				if ("Y".equals(creditStatus)) {
					return generalAssessmentService.saveGeneralRecCreditInvoiceReceipt(cid, bid, user, assessmentData);
				} else if ("P".equals(creditStatus)) {
					return generalAssessmentService.saveGeneralRecPdaInvoiceReceipt(cid, bid, user, assessmentData);
				} else {
					return generalAssessmentService.saveGeneralRecInvoiceReceipt(cid, bid, user, assessmentData);
				}

			}
			
			
			@PostMapping("/saveGeneralAddService")
			public ResponseEntity<?> saveGeneralAddService(@RequestParam("companyId") String companyId,
					@RequestParam("branchId") String branchId, @RequestBody Map<String, Object> requestData,
					@RequestParam("userId") String user, @RequestParam("type") String type)
					throws JsonMappingException, JsonProcessingException {
				ResponseEntity<?> addExportAssesmentSheet = generalAssessmentService.saveGeneralAddService(companyId, branchId, user,
						requestData, type);
				return addExportAssesmentSheet;
			}
			
			@GetMapping("/searchGeneralInvoiceData")
			public ResponseEntity<?> searchGeneralInvoiceData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam(name = "val", required = false) String val, @RequestParam("type") String type) {
				return generalAssessmentService.searchGeneralInvoiceData(cid, bid, val, type);
			}
			
			
			
			// General Delivery Invoice
			
			@GetMapping("/getBeforeAssessmentDeliveryData")
			public ResponseEntity<?> getBeforeAssessmentDeliveryData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
					@RequestParam("val") String val){
				
				try {
					
					return generalAssessmentService.getBeforeAssessmentDeliveryData(cid, bid, val);
					
				} catch (Exception e) {
					e.printStackTrace();
					
					return new ResponseEntity<>("Internal Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
			@GetMapping("/getGeneralDeliveryBeforeSaveAssessmentData")
			public ResponseEntity<?> getGeneralDeliveryBeforeSaveAssessmentData(@RequestParam("cid") String cid,
					@RequestParam("bid") String bid, @RequestParam("val") String val) {
				return generalAssessmentService.getGeneralDeliveryBeforeSaveAssessmentData(cid, bid, val);
			}
			
			@PostMapping("/saveGeneralDeliveryAssessmentData")
			public ResponseEntity<?> saveGeneralDeliveryAssessmentData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
					throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {
				return generalAssessmentService.saveGeneralDeliveryAssessmentData(cid, bid, user, assessmentData);
			}
			
			@PostMapping("/saveGeneralDeliveryInvoiceReceipt")
			public ResponseEntity<?> saveGeneralDeliveryInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
					@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
					@RequestBody Map<String, Object> assessmentData) throws JsonMappingException, JsonProcessingException {

				if ("Y".equals(creditStatus)) {
					return generalAssessmentService.saveExbondCreditInvoiceReceipt(cid, bid, user, assessmentData);
				} else if ("P".equals(creditStatus)) {
					return generalAssessmentService.saveExbondPdaInvoiceReceipt(cid, bid, user, assessmentData);
				} else {
					return generalAssessmentService.saveExbondInvoiceReceipt(cid, bid, user, assessmentData);
				}

			}

			
}
