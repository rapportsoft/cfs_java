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

import com.cwms.service.CreditNoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@CrossOrigin("*")
@RestController
@RequestMapping("/creditNote")
public class CreditNoteController {
	
	 @Autowired
	 private CreditNoteService creditNoteService;
	 
	 
	 

		@GetMapping("/getSelectedCreditNoteSearch")
		public ResponseEntity<?> getSelectedCreditNoteSearch(
		        @RequestParam("companyId") String companyId, 
		        @RequestParam("branchId") String branchId,	        
		        @RequestParam("creditNoteNo") String creditNoteNo,
		        @RequestParam("invoiceNo") String invoiceNo, 
		        @RequestParam("profitCenterId") String profitCenterId
		       ) {	    
		    try {	    	
		    	ResponseEntity<?>  cartingEntries = creditNoteService.getSelectedAssesMentSearch(companyId, branchId, creditNoteNo, invoiceNo, profitCenterId);
		        return cartingEntries;
		    } catch (Exception e) {	       
		        // Return an appropriate error response
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
		    }
		}
		
		
		
		
		@GetMapping("/getCreditNoteToSelect")
		public ResponseEntity<?> getCreditNoteToSelect(
		        @RequestParam("companyId") String companyId, 
		        @RequestParam("branchId") String branchId,
		        @RequestParam(value="searchValue", required = false) String searchValue
		       ) {	    
		    try {	    	
		    	List<Object[]> cartingEntries = creditNoteService.getCreditNoteToSelect(companyId, branchId, searchValue);
		        return ResponseEntity.ok(cartingEntries);
		    } catch (Exception e) {	       
		        // Return an appropriate error response
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
		    }
		}
		
		
	 
	 
	 
	 
	 @GetMapping("/getInvoiceListForCreditNote")
		public ResponseEntity<?> getInvoiceListForCreditNote(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue, @RequestParam("profitCentreId") String profitCentreId) {
		 	ResponseEntity<?> resultList= creditNoteService.getInvoiceListForCreditNote(companyId, branchId, searchValue, profitCentreId);
			return resultList;
		}
	 
	 @GetMapping("/getDataForSeletdInvoiceNo")
		public ResponseEntity<?> getDataForSeletdInvoiceNo(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam("profitCentreId") String profitCentreId, @RequestParam("invoiceNo") String invoiceNo) {
		 ResponseEntity<?> resultList= creditNoteService.getDataForSeletdInvoiceNo(companyId, branchId, profitCentreId, invoiceNo);
			
			return resultList;
		}
	 
	 
	 @PostMapping("/processCreditNote")
	 public ResponseEntity<?> processCreditNote(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			 @RequestParam("userId") String user, @RequestBody Map<String,Object> requestData, @RequestParam("creditType") String creditType) throws JsonMappingException, JsonProcessingException{
			
		ResponseEntity<?> processCreditNote = creditNoteService.processCreditNote(companyId, branchId, user, requestData, creditType);	
				
		return processCreditNote;
	 }
	
	@PostMapping("/saveCreditNote")
	public ResponseEntity<?> saveCreditNote(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user, @RequestParam("creditType") String creditType) throws JsonMappingException, JsonProcessingException
		{			
		try {
		ResponseEntity<?> addCreditNote = creditNoteService.saveCreditNote(companyId, branchId, user, requestData, creditType);					
			
			return addCreditNote;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
		}

}
