package com.cwms.controller;

import java.text.ParseException;
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

import com.cwms.service.ProformaAssessmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/proforma")
public class ProformaController {

	@Autowired
	private ProformaAssessmentService proformaService;

	@GetMapping("/searchImportBeforeSaveAssessmentData")
	public ResponseEntity<?> searchImportBeforeSaveAssessmentData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {
		return proformaService.searchImportBeforeSaveAssessmentData(cid, bid, val);
	}

	@GetMapping("/getImportBeforeSaveAssessmentData")
	public ResponseEntity<?> getImportBeforeSaveAssessmentData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("trans") String trans, @RequestParam("igm") String igm,
			@RequestParam("lineNo") String lineNo) {
		return proformaService.getImportBeforeSaveAssessmentData(cid, bid, trans, igm, lineNo);
	}

	@PostMapping("/saveAssessmentData")
	public ResponseEntity<?> saveAssessmentData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
			throws JsonMappingException, JsonProcessingException {

		return proformaService.saveAssessmentData(cid, bid, user, assessmentData);
	}

	@PostMapping("/saveImportInvoiceReceipt")
	public ResponseEntity<?> saveImportInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
			@RequestBody Map<String, Object> assessmentData)
			throws JsonMappingException, JsonProcessingException, CloneNotSupportedException {

		return proformaService.saveImportInvoiceReceipt(cid, bid, user, assessmentData);

	}

	@GetMapping("/searchInvoiceData1")
	public ResponseEntity<?> searchInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		return proformaService.searchInvoiceData1(cid, bid, val);
	}

	@GetMapping("/getSelectedInvoiceData1")
	public ResponseEntity<?> getSelectedInvoiceData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("assId") String assId, @RequestParam("invId") String invId) {
		return proformaService.getSelectedInvoiceData1(cid, bid, assId, invId);
	}

	@GetMapping("/getAllCfInvSrvAnxListByAssesmentId")
	public ResponseEntity<?> getAllCfInvSrvAnxListByAssesmentId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId,
			@RequestParam("profiCentreId") String profiCentreId,
			@RequestParam("assesmentLineNo") String assesmentLineNo) {
		ResponseEntity<?> partyByTypeValue = proformaService.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId,
				assesmentId, assesmentLineNo, "", profiCentreId);
		return partyByTypeValue;
	}

	@PostMapping("/saveAddServiceContainerWise")
	public ResponseEntity<?> saveAddServiceContainerWise(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody Map<String, Object> requestData,
			@RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException {
		ResponseEntity<?> addExportAssesmentSheet = proformaService.saveAddServiceContainerWise(companyId, branchId,
				user, requestData);
		return addExportAssesmentSheet;
	}

	@GetMapping("/getDataByAfterAssessment")
	public ResponseEntity<?> getDataByAfterAssessment(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id, @RequestParam("partyId") String partyId,
			@RequestParam("creditType") String creditType) {

		return proformaService.getDataByAssessmentId(cid, bid, id, partyId, creditType);
	}

	@GetMapping("/getAllContainerListOfAssessMentSheet")
	public ResponseEntity<?> getAllContainerListOfAssessMentSheet(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId,
			@RequestParam("profiCentreId") String profiCentreId) {
		ResponseEntity<?> partyByTypeValue = proformaService.getAllContainerListOfAssessMentSheet(companyId, branchId,
				assesmentId, profiCentreId);
		return partyByTypeValue;
	}

	@PostMapping("/saveAddServiceServiceWise")
	public ResponseEntity<?> saveAddServiceServiceWise(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody Map<String, Object> requestData,
			@RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException {
		ResponseEntity<?> addExportAssesmentSheet = proformaService.saveAddServiceServiceWise(companyId, branchId, user,
				requestData);
		return addExportAssesmentSheet;
	}

	@PostMapping("/saveBondNocAssessmentData")
	public ResponseEntity<?> saveBondNocAssessmentData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
			throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {
		return proformaService.saveBondNOCAssessmentData(cid, bid, user, assessmentData);
	}

	@PostMapping("/saveBondNocInvoiceReceipt")
	public ResponseEntity<?> saveBondNocInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
			@RequestBody Map<String, Object> assessmentData) throws JsonMappingException, JsonProcessingException {

		return proformaService.saveBondNocInvoiceReceipt(cid, bid, user, assessmentData);

	}

	@GetMapping("/searchBondNOCInvoiceData")
	public ResponseEntity<?> searchBondNOCInvoiceData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val, @RequestParam("type") String type) {
		return proformaService.searchBondNOCInvoiceData(cid, bid, val, type);
	}

	@GetMapping("/getSelectedBondNOCInvoiceData")
	public ResponseEntity<?> getSelectedBondNOCInvoiceData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("assId") String assId, @RequestParam("invId") String invId,
			@RequestParam("type") String type) {
		return proformaService.getSelectedBondNOCInvoiceData(cid, bid, assId, invId, type);
	}

	@PostMapping("/saveExbondAssessmentData")
	public ResponseEntity<?> saveExbondAssessmentData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> assessmentData)
			throws JsonMappingException, JsonProcessingException, ParseException, CloneNotSupportedException {
		return proformaService.saveExbondAssessmentData(cid, bid, user, assessmentData);
	}

	@PostMapping("/saveExBondInvoiceReceipt")
	public ResponseEntity<?> saveExBondInvoiceReceipt(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("creditStatus") String creditStatus, @RequestParam("user") String user,
			@RequestBody Map<String, Object> assessmentData) throws JsonMappingException, JsonProcessingException {

		return proformaService.saveExbondInvoiceReceipt(cid, bid, user, assessmentData);

	}
	
	
	@PostMapping("/saveBondAddService")
	public ResponseEntity<?> saveBondAddService(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user,@RequestParam("type") String type) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = proformaService.saveBondAddService(companyId, branchId, user, requestData,type);
			return addExportAssesmentSheet;
		}
	
	 @GetMapping("/getDataByAfterAssessmentForBond")
	 public ResponseEntity<?> getDataByAfterAssessmentForBond(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("id") String id,
			 @RequestParam("partyId") String partyId,@RequestParam("creditType") String creditType,@RequestParam("type") String type){

		 return proformaService.getDataByAssessmentId1(cid, bid, id, partyId, creditType, type);
	 }

}
