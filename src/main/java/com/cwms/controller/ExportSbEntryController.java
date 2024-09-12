package com.cwms.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.cwms.entities.Cfigmcn;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.service.ExportEntryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/ExportSbEntry")
public class ExportSbEntryController 
{
	@Autowired
	private ExportEntryService exportEntryService;	
		
	@GetMapping("/searchExportMain")
	public ResponseEntity<?> searchExportMain(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="sbNo", required = false) String sbNo,
	        @RequestParam(value="vehicleNo", required = false) String vehicleNo,
	        @RequestParam(value="containerNo", required = false) String containerNo,
	        @RequestParam(value="bookingNo", required = false) String bookingNo,
	        @RequestParam(value="pod", required = false) String pod
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = exportEntryService.searchExportMain(companyId, branchId, sbNo, vehicleNo, containerNo, bookingNo, pod);
	        return sbCargoGateIn;
	    } catch (Exception e) {	  	
	    	System.out.println(e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching export");
	    }
	}
	
	
	
//	@GetMapping("/searchSbNosToGateIn")
//	public ResponseEntity<?> searchSbNosToGateIn(
//	        @RequestParam("companyId") String companyId, 
//	        @RequestParam("branchId") String branchId,	        
//	        @RequestParam(value="searchValue", required = false) String searchValue      
//	       ) {	    
//	    try {	    	
//	    	ResponseEntity<?> sbCargoGateIn = exportEntryService.searchSbNosToGateIn(companyId, branchId, searchValue);
//	        return sbCargoGateIn;
//	    } catch (Exception e) {	       
//	        // Return an appropriate error response
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
//	    }
//	}
	
	@GetMapping("/searchSbNosToGateIn")
	public ResponseEntity<?> searchSbNosToGateIn(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("searchValue") String searchValue      
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = exportEntryService.searchSbNosToGateIn(companyId, branchId, searchValue);
	        return sbCargoGateIn;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	@GetMapping("/getSbCargoGateIn")
	public ResponseEntity<?> getSbCargoGateIn(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="gateInId", required = false) String gateInId,
	        @RequestParam("sbNo") String sbNo
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = exportEntryService.getSbCargoGateIn(companyId, branchId, sbNo, gateInId);
	        return sbCargoGateIn;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	@GetMapping("/getSbEntriesToSelect")
	public ResponseEntity<?> getSbEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]>  sbEntries = exportEntryService.getSbEntriesToSelect(companyId, branchId, searchValue);
	        return ResponseEntity.ok(sbEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@GetMapping("/getSelectedSbEntry")
	public ResponseEntity<?> getSelectedSbEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("hsbTransId") String hsbTransId, 
	        @RequestParam("sbNo") String sbNo,
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  sbEntries = exportEntryService.getSelectedSbEntry(companyId, branchId,  sbTransId, hsbTransId,sbNo, profitCenterId);
	        return sbEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@PostMapping("/addExportSbEntry")
	public ResponseEntity<?> addExportSbEntry(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody Map<String, Object> requestData, @RequestParam("userId") String User)
		{
				
			ResponseEntity<?> addExportSbEntry = exportEntryService.addExportSbEntry(companyId, branchId, requestData, User);
		
			return addExportSbEntry;
		}
	
	
	@GetMapping("/checkDuplicateSbNo")
	public ResponseEntity<?> checkDuplicateSbNo(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,
	        @RequestParam("sbNo") String sbNo, 
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("profitCenterId") String profitCenterId, 
	        @RequestParam("finYear") String finYear) {
	    
	    try {
	        boolean checkDuplicateSbNo = exportEntryService.checkDuplicateSbNo(companyId, branchId, finYear, profitCenterId, sbTransId, sbNo);
	        return ResponseEntity.ok(checkDuplicateSbNo);
	    } catch (Exception e) {
	        // Log the error message
	        System.out.println("Error occurred while checking duplicate SB No: " + e.getMessage());
	        
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}

	
	
	

}
