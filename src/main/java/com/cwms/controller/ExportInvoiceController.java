package com.cwms.controller;

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

import com.cwms.entities.AssessmentSheet;
import com.cwms.helper.ExportContainerAssessmentData;
import com.cwms.service.ExportInvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/exportInvoice")
@CrossOrigin("*")
public class ExportInvoiceController {
	
	@Autowired
	private ExportInvoiceService exportInvoiceService;	
	
	
	
	
	
	@GetMapping("/getSelectedAssesMentSearch")
	public ResponseEntity<?> getSelectedAssesMentSearch(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("assesMentId") String assesMentId,
	        @RequestParam("invoiceNo") String invoiceNo, 
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = exportInvoiceService.getSelectedAssesMentSearch(companyId, branchId,  assesMentId, invoiceNo, sbTransId, profitCenterId);
	        return cartingEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@GetMapping("/getAssesMentEntriesToSelect")
	public ResponseEntity<?> getAssesMentEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]> cartingEntries = exportInvoiceService.getAssesMentEntriesToSelect(companyId, branchId, searchValue);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	
	@PostMapping("/processExportAssesmentContainer")
	 public ResponseEntity<?> saveExportAssesmentApprove(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			 @RequestParam("userId") String user, @RequestBody Map<String,Object> requestData) throws JsonMappingException, JsonProcessingException{
		 return exportInvoiceService.processExportAssesmentContainer(companyId, branchId, user, requestData);
	 }
	
	@PostMapping("/saveExportAssesmentContainer")
	public ResponseEntity<?> saveExportAssesment(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = exportInvoiceService.saveExportAssesment(companyId, branchId, user, requestData);
			return addExportAssesmentSheet;
		}
	
	@PostMapping("/saveAddServiceServiceWise")
	public ResponseEntity<?> saveAddServiceServiceWise(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = exportInvoiceService.saveAddServiceServiceWise(companyId, branchId, user, requestData);
			return addExportAssesmentSheet;
		}
		
	
	
	@GetMapping("/getAllContainerListOfAssessMentSheet")
	public ResponseEntity<?> getAllContainerListOfAssessMentSheet(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getAllContainerListOfAssessMentSheet(companyId, branchId, assesmentId, profiCentreId);
		return partyByTypeValue;
	}
	
	
	@GetMapping("/getAllContainerDetailsOfAssesmentId")
	public ResponseEntity<?> getAllContainerDetailsOfAssesmentId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getAllContainerDetailsOfAssesmentId(companyId, branchId, assesmentId, profiCentreId);
		return partyByTypeValue;
	}
	
	@PostMapping("/saveAddServiceContainerWise")
	public ResponseEntity<?> saveAddServiceContainerWise(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = exportInvoiceService.saveAddServiceContainerWise(companyId, branchId, user, requestData);
			return addExportAssesmentSheet;
		}
		
	
	@PostMapping("/searchServicesForAddService")
	public ResponseEntity<?> searchServicesForAddService(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,@RequestParam("commodity") String commodity,
			@RequestParam("containerSize") String containerSize, @RequestParam("cargoType") String cargoType, @RequestBody AssessmentSheet assessmentSheet) {
		ResponseEntity<?> servicesList = exportInvoiceService.searchServicesForAddService(companyId, branchId, searchValue, commodity, containerSize, cargoType, assessmentSheet);
		return servicesList;
	}
	
	
	@GetMapping("/getAllCfInvSrvAnxListByAssesmentId")
	public ResponseEntity<?> getAllCfInvSrvAnxListByAssesmentId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId,
			@RequestParam("assesmentLineNo") String assesmentLineNo, @RequestParam("containerNo") String containerNo) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId, assesmentId, assesmentLineNo, containerNo, profiCentreId);
		return partyByTypeValue;
	}
	
	
	
	
	
	
	
	@GetMapping("/searchAssesmentBySelectedValue")
	public ResponseEntity<?> searchAssesmentBySelectedValue(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitcentreId") String profitcentreId,
			@RequestParam("operation") String operation, @RequestParam("searchValue") String searchValue) throws CloneNotSupportedException {
		List<ExportContainerAssessmentData> resultSet= exportInvoiceService.searchAssesmentBySelectedValue(companyId, branchId, profitcentreId, operation, searchValue);
		return ResponseEntity.ok(resultSet);
	}
	
	@GetMapping("/getPartyByTypeValue")
	public ResponseEntity<?> getPartyByTypeValue(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,
			@RequestParam(name = "type", required = false) String type) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getPartyByTypeValue(companyId, branchId, searchValue, type);
		return partyByTypeValue;
	}
	
	@GetMapping("/getSbListOrContainer")
	public ResponseEntity<?> getSbListOrContainer(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,
			@RequestParam("type") String type, @RequestParam("profitCentreId") String profitCentreId) {
		List<Map<String, Object>> resultList= exportInvoiceService.getSbListOrContainer(companyId, branchId, searchValue, type, profitCentreId);
		return ResponseEntity.ok(resultList);
	}
	

}
