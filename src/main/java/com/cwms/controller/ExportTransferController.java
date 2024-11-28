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

import com.cwms.entities.ExportTransfer;
import com.cwms.service.ExportTransferService;

@RestController
@CrossOrigin("*")
@RequestMapping("/sbTransfer")
public class ExportTransferController {	
	
	@Autowired
	private ExportTransferService transService;
	
	@GetMapping("/searchSbNoForTransfer")
	public ResponseEntity<?> searchSbNoForTransfer(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("searchValue") String searchValue, @RequestParam("profitcentreId") String profitcentreId, @RequestParam("type") String type){
		return transService.searchSbNoForTransfer(companyId, branchId, searchValue, profitcentreId, type);
	}
	
	
	@GetMapping("/gateTateInEntriesFromSbNo")
	public ResponseEntity<?> gateTateInEntriesFromSbNo(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("sbNo") String sbNo, @RequestParam("profitcentreId") String profitcentreId, @RequestParam("sbLineNo") String sbLineNo, @RequestParam("sbTransId") String sbTransId){
		return transService.gateTateInEntriesFromSbNo(companyId, branchId, profitcentreId,sbNo, sbTransId, sbLineNo);
	}
	
	@PostMapping("/addExportTransfer")
	public ResponseEntity<?> addExportTransferEntry(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String User)
		{				
			ResponseEntity<?> addExportSbEntry = transService.addExportTransferEntry(companyId, branchId, requestData, User);
			return addExportSbEntry;
		}
	
	
	@GetMapping("/getSelectedTransferEntry")
	public ResponseEntity<?> getSelectedTransferEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("sbChangeTransId") String sbChangeTransId,
	        @RequestParam("srNo") String srNo, 
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = transService.getSelectedTransferEntry(companyId, branchId,  sbChangeTransId, srNo, profitCenterId);
	        return cartingEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	@GetMapping("/getTransferEntriesToSelect")
	public ResponseEntity<?> getTransferEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,
	        @RequestParam("profitCenterId") String profitcenterId,
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<ExportTransfer> cartingEntries = transService.getTransferEntriesToSelect(companyId, branchId, searchValue, profitcenterId);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	

}
