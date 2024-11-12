package com.cwms.controller;

import java.util.List;

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

import com.cwms.entities.ExportMovement;
import com.cwms.service.ExportMovementService;

@RestController
@CrossOrigin("*")
@RequestMapping("ExportMovement")
public class ExportMovementController {

	@Autowired
	private ExportMovementService movementService;
	
	
	@GetMapping("/getSelectedMovementEntry")
	public ResponseEntity<?> getSelectedMovementEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("movementReqId") String movementReqId,
	        @RequestParam("lineId") String lineId, 
	        @RequestParam("ContainerNo") String ContainerNo,
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = movementService.getSelectedMovementEntry(companyId, branchId,  movementReqId, lineId,ContainerNo, profitCenterId);
	        return cartingEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@GetMapping("/getMovementEntriesToSelect")
	public ResponseEntity<?> getMovementEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]> cartingEntries = movementService.getMovementEntriesToSelect(companyId, branchId, searchValue);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	@PostMapping("/saveExportMovement")
	public ResponseEntity<?> saveExportMovement(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<ExportMovement> exportMovement,
			@RequestParam("userId") String User) {
		ResponseEntity<?> saveExportStuffRequest = movementService.saveExportMovement(companyId, branchId, exportMovement, User);
		return saveExportStuffRequest;
	}
	
	
	@GetMapping("/searchContainerNoForMovementWork")
	public ResponseEntity<?> searchContainerNoForMovementWork(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("containerNo") String containerNo, @RequestParam("profitcentreId") String profitcentreId) {
		return movementService.searchContainerNoForStuffingConteinerWise(companyId, branchId, containerNo, profitcentreId);
	}
	
	@GetMapping("/getSelectedCartingEntry")
	public ResponseEntity<?> getSelectedCartingEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,
	        @RequestParam("containerNo") String containerNo,
	        @RequestParam("movementReqId") String movementReqId,	       
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  moveMentEntries = movementService.getSelectedMovementEntry(companyId, branchId, movementReqId,"", containerNo, profitCenterId);
	        return moveMentEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
}
