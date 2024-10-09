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

import com.cwms.entities.ExportCarting;
import com.cwms.service.ExportCartingService;

@RestController
@RequestMapping("/carting")
@CrossOrigin("*")
public class ExportCartingController {
	
	@Autowired
	public ExportCartingService cartingService;
	

	@PostMapping("/addExportCarting")
	public ResponseEntity<?> addExportCarting(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody List<ExportCarting> cartingList, @RequestParam("userId") String User, @RequestParam("status") String status)
		{				
			ResponseEntity<?> addExportCartingEntry = cartingService.addExportCarting(companyId, branchId, cartingList, User, status);
			return addExportCartingEntry;
		}

	@GetMapping("/getSelectedCartingEntry")
	public ResponseEntity<?> getSelectedCartingEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("cartingTransId") String cartingTransId,
	        @RequestParam("cartingLineId") String cartingLineId, 
	        @RequestParam("sbNo") String sbNo,
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = cartingService.getSelectedCartingEntry(companyId, branchId,  cartingTransId, cartingLineId,sbNo, profitCenterId);
	        return cartingEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	@GetMapping("/getCartingEntriesToSelect")
	public ResponseEntity<?> getCartingEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]> cartingEntries = cartingService.getCartingEntriesToSelect(companyId, branchId, searchValue);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	

}
