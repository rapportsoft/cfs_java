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
import com.cwms.entities.AssessmentSheetPro;
import com.cwms.helper.ExportContainerAssessmentData;
import com.cwms.service.ExportInvoiceService;
import com.cwms.service.ExportInvoiceServicePro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/exportInvoice")
@CrossOrigin("*")
public class ExportInvoiceController {
	
	@Autowired
	private ExportInvoiceService exportInvoiceService;	
	
	@Autowired
	private ExportInvoiceServicePro exportInvoiceServicePro;	
	
	
	
	
	@GetMapping("/getSelectedAssesMentSearch")
	public ResponseEntity<?> getSelectedAssesMentSearch(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("assesMentId") String assesMentId,
	        @RequestParam("invoiceNo") String invoiceNo, 
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("profitCenterId") String profitCenterId, @RequestParam("transType") String transType
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = exportInvoiceService.getSelectedAssesMentSearch(companyId, branchId,  assesMentId, invoiceNo, sbTransId, profitCenterId, transType);
	        return cartingEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@GetMapping("/getAssesMentEntriesToSelect")
	public ResponseEntity<?> getAssesMentEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId, @RequestParam("transType") String transType,
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]> cartingEntries = exportInvoiceService.getAssesMentEntriesToSelect(companyId, branchId, searchValue, transType);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	
	@PostMapping("/processExportAssesmentContainer")
	 public ResponseEntity<?> saveExportAssesmentApprove(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			 @RequestParam("userId") String user, @RequestParam("invoiceType") String invoiceType , @RequestBody Map<String,Object> requestData, @RequestParam("creditStatus") String creditStatus) throws JsonMappingException, JsonProcessingException{
		ResponseEntity<?> addExportAssesmentSheet = null;
		
		if("Export Container".equalsIgnoreCase(invoiceType))
		{		
		addExportAssesmentSheet = exportInvoiceService.processExportAssesmentContainer(companyId, branchId, user, requestData, creditStatus);		
		
		}else if("Export Cargo Carting".equalsIgnoreCase(invoiceType)){		
		addExportAssesmentSheet = exportInvoiceService.processExportAssesmentCartingAndBackToTown(companyId, branchId, user, requestData, invoiceType, creditStatus);
		}else if("Export Cargo".equalsIgnoreCase(invoiceType))
		{	
			addExportAssesmentSheet = exportInvoiceService.processExportAssesmentBackToTown(companyId, branchId, user, requestData, invoiceType, creditStatus);
		}
				
		return addExportAssesmentSheet;
	 }
	
	@PostMapping("/saveExportAssesmentContainer")
	public ResponseEntity<?> saveExportAssesment(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user, @RequestParam("invoiceType") String invoiceType) throws JsonMappingException, JsonProcessingException
		{			
		ResponseEntity<?> addExportAssesmentSheet = null;
		if("Export Container".equalsIgnoreCase(invoiceType))
		{		
			addExportAssesmentSheet = exportInvoiceService.saveExportAssesment(companyId, branchId, user, requestData, invoiceType);
		}else if("Export Cargo Carting".equalsIgnoreCase(invoiceType))
		{	
			addExportAssesmentSheet = exportInvoiceService.saveExportAssesmentCartingAndBackToTown(companyId, branchId, user, requestData, invoiceType);		
		}else if("Export Cargo".equalsIgnoreCase(invoiceType))
		{	
			addExportAssesmentSheet = exportInvoiceService.saveExportAssesmentBackToTown(companyId, branchId, user, requestData, invoiceType);		
		}
			
			
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
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId, @RequestParam("invoiceType") String invoiceType) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getAllContainerListOfAssessMentSheet(companyId, branchId, assesmentId, profiCentreId, invoiceType);
		return partyByTypeValue;
	}
	
	
	@GetMapping("/getAllContainerDetailsOfAssesmentId")
	public ResponseEntity<?> getAllContainerDetailsOfAssesmentId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId, @RequestParam(value = "invoiceType", required = false) String invoiceType) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getAllContainerDetailsOfAssesmentId(companyId, branchId, assesmentId, profiCentreId, invoiceType);
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
			@RequestParam("assesmentLineNo") String assesmentLineNo) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceService.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId, assesmentId, assesmentLineNo, "", profiCentreId);
		return partyByTypeValue;
	}
	
	
	
	
	
	
	
	@GetMapping("/searchAssesmentBySelectedValue")
	public ResponseEntity<?> searchAssesmentBySelectedValue(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitcentreId") String profitcentreId,
			@RequestParam("operation") String operation, @RequestParam("searchValue") String searchValue, @RequestParam("invoiceType") String invoiceType) throws CloneNotSupportedException {
		List<ExportContainerAssessmentData> resultSet= exportInvoiceService.searchAssesmentBySelectedValue(companyId, branchId, profitcentreId, operation, searchValue, invoiceType);
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
			@RequestParam("type") String type, @RequestParam("profitCentreId") String profitCentreId, @RequestParam("invoiceType") String invoiceType) {
		List<Map<String, Object>> resultList= exportInvoiceService.getSbListOrContainer(companyId, branchId, searchValue, type, profitCentreId, invoiceType);
		return ResponseEntity.ok(resultList);
	}
	
	
	
	
	
	
	
	
//	**************************** Proforma *************************
	
	
	
	

	@GetMapping("/getSelectedAssesMentSearchProForma")
	public ResponseEntity<?> getSelectedAssesMentSearchProForma(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("assesMentId") String assesMentId,
	        @RequestParam("invoiceNo") String invoiceNo, 
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("profitCenterId") String profitCenterId, @RequestParam("transType") String transType
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = exportInvoiceServicePro.getSelectedAssesMentSearch(companyId, branchId,  assesMentId, invoiceNo, sbTransId, profitCenterId, transType);
	        return cartingEntries;			
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@GetMapping("/getAssesMentEntriesToSelectProForma")
	public ResponseEntity<?> getAssesMentEntriesToSelectProForma(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId, @RequestParam("transType") String transType,
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]> cartingEntries = exportInvoiceServicePro.getAssesMentEntriesToSelect(companyId, branchId, searchValue, transType);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	
	@PostMapping("/processExportAssesmentContainerProForma")
	 public ResponseEntity<?> saveExportAssesmentApproveProForma(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			 @RequestParam("userId") String user, @RequestParam("invoiceType") String invoiceType , @RequestBody Map<String,Object> requestData, @RequestParam("creditStatus") String creditStatus) throws JsonMappingException, JsonProcessingException{
		ResponseEntity<?> addExportAssesmentSheet = null;
		
		if("Export Container".equalsIgnoreCase(invoiceType))
		{		
		addExportAssesmentSheet = exportInvoiceServicePro.processExportAssesmentContainer(companyId, branchId, user, requestData, creditStatus);		
		
		}else if("Export Cargo Carting".equalsIgnoreCase(invoiceType)){		
		addExportAssesmentSheet = exportInvoiceServicePro.processExportAssesmentCartingAndBackToTown(companyId, branchId, user, requestData, invoiceType, creditStatus);
		}else if("Export Cargo".equalsIgnoreCase(invoiceType))
		{	
			addExportAssesmentSheet = exportInvoiceServicePro.processExportAssesmentBackToTown(companyId, branchId, user, requestData, invoiceType, creditStatus);
		}
				
		return addExportAssesmentSheet;
	 }
	
	@PostMapping("/saveExportAssesmentContainerProForma")
	public ResponseEntity<?> saveExportAssesmentProForma(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user, @RequestParam("invoiceType") String invoiceType) throws JsonMappingException, JsonProcessingException
		{			
		ResponseEntity<?> addExportAssesmentSheet = null;
		if("Export Container".equalsIgnoreCase(invoiceType))
		{		
			addExportAssesmentSheet = exportInvoiceServicePro.saveExportAssesment(companyId, branchId, user, requestData, invoiceType);
		}else if("Export Cargo Carting".equalsIgnoreCase(invoiceType))
		{	
			addExportAssesmentSheet = exportInvoiceServicePro.saveExportAssesmentCartingAndBackToTown(companyId, branchId, user, requestData, invoiceType);		
		}else if("Export Cargo".equalsIgnoreCase(invoiceType))
		{	
			addExportAssesmentSheet = exportInvoiceServicePro.saveExportAssesmentBackToTown(companyId, branchId, user, requestData, invoiceType);		
		}
			
			
			return addExportAssesmentSheet;
		}
	
	@PostMapping("/saveAddServiceServiceWisePro")
	public ResponseEntity<?> saveAddServiceServiceWiseProForma(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = exportInvoiceServicePro.saveAddServiceServiceWise(companyId, branchId, user, requestData);
			return addExportAssesmentSheet;
		}
		
	
	
	@GetMapping("/getAllContainerListOfAssessMentSheetProForma")
	public ResponseEntity<?> getAllContainerListOfAssessMentSheetProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId, @RequestParam("invoiceType") String invoiceType) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceServicePro.getAllContainerListOfAssessMentSheet(companyId, branchId, assesmentId, profiCentreId, invoiceType);
		return partyByTypeValue;
	}
	
	
	@GetMapping("/getAllContainerDetailsOfAssesmentIdProForma")
	public ResponseEntity<?> getAllContainerDetailsOfAssesmentIdProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId, @RequestParam(value = "invoiceType", required = false) String invoiceType) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceServicePro.getAllContainerDetailsOfAssesmentId(companyId, branchId, assesmentId, profiCentreId, invoiceType);
		return partyByTypeValue;
	}
	
	@PostMapping("/saveAddServiceContainerWiseProForma")
	public ResponseEntity<?> saveAddServiceContainerWiseProForma(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) throws JsonMappingException, JsonProcessingException
		{				
			ResponseEntity<?> addExportAssesmentSheet = exportInvoiceServicePro.saveAddServiceContainerWise(companyId, branchId, user, requestData);
			return addExportAssesmentSheet;
		}
		
	
	@PostMapping("/searchServicesForAddServiceProForma")
	public ResponseEntity<?> searchServicesForAddServiceProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,@RequestParam("commodity") String commodity,
			@RequestParam("containerSize") String containerSize, @RequestParam("cargoType") String cargoType, @RequestBody AssessmentSheetPro assessmentSheet) {
		ResponseEntity<?> servicesList = exportInvoiceServicePro.searchServicesForAddService(companyId, branchId, searchValue, commodity, containerSize, cargoType, assessmentSheet);
		return servicesList;
	}
	
	
	@GetMapping("/getAllCfInvSrvAnxListByAssesmentIdProForma")
	public ResponseEntity<?> getAllCfInvSrvAnxListByAssesmentIdProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("assesmentId") String assesmentId, @RequestParam("profiCentreId") String profiCentreId,
			@RequestParam("assesmentLineNo") String assesmentLineNo) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceServicePro.getAllCfInvSrvAnxListByAssesmentId(companyId, branchId, assesmentId, assesmentLineNo, "", profiCentreId);
		return partyByTypeValue;
	}
	
	
	
	@GetMapping("/searchAssesmentBySelectedValueProForma")
	public ResponseEntity<?> searchAssesmentBySelectedValueProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitcentreId") String profitcentreId,
			@RequestParam("operation") String operation, @RequestParam("searchValue") String searchValue, @RequestParam("invoiceType") String invoiceType) throws CloneNotSupportedException {
		List<ExportContainerAssessmentData> resultSet= exportInvoiceServicePro.searchAssesmentBySelectedValue(companyId, branchId, profitcentreId, operation, searchValue, invoiceType);
		return ResponseEntity.ok(resultSet);
	}
	
	@GetMapping("/getPartyByTypeValueProForma")
	public ResponseEntity<?> getPartyByTypeValueProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,
			@RequestParam(name = "type", required = false) String type) {
		ResponseEntity<?> partyByTypeValue = exportInvoiceServicePro.getPartyByTypeValue(companyId, branchId, searchValue, type);
		return partyByTypeValue;
	}
	
	@GetMapping("/getSbListOrContainerProForma")
	public ResponseEntity<?> getSbListOrContainerProForma(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,
			@RequestParam("type") String type, @RequestParam("profitCentreId") String profitCentreId, @RequestParam("invoiceType") String invoiceType) {
		List<Map<String, Object>> resultList= exportInvoiceServicePro.getSbListOrContainer(companyId, branchId, searchValue, type, profitCentreId, invoiceType);
		return ResponseEntity.ok(resultList);
	}
	
	
	
	
	
	
	
	
	

}


