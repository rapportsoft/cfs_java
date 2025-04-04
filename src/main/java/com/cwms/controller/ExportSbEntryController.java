package com.cwms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
	
	
	
	@GetMapping("/getDataForDocumentupload")
	public ResponseEntity<?> getDataForDocumentupload(
	        @RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,	        
	        @RequestParam("profitcentreId") String profitcentreId, @RequestParam("sbTransId") String sbTransId, 
	        @RequestParam("hSbTransId") String hSbTransId, @RequestParam("sbNo") String sbNo,  @RequestParam("sbLineNo") String sbLineNo
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> getDataForDocumentupload = exportEntryService.getDataForDocumentupload(companyId, branchId, profitcentreId, sbTransId, hSbTransId, sbNo, sbLineNo);
	        return getDataForDocumentupload;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	    	System.out.println(e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while getting Saved Files.");
	    }
	}
	
	
	@PostMapping(value = "/saveSBDocumentUpload", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSBDocumentUpload(
    		@RequestParam("sbNo") String sbNo,
            @RequestParam("sbTransId") String sbTransId,
            @RequestParam("hsbTransId") String hSbTransId,
            @RequestParam("sbLineNo") String sbLineNo,
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("userId") String userId,
//            @RequestBody List<FileResponseDTO> files,  // Receiving list of FileResponseDTO
//            @RequestParam(value = "removedFiles", required = false) List<String> removedFiles // Receiving list of filenames   ,
            @RequestBody Map<String, Object> data
    ) {
        try {
            ResponseEntity<?> saveSBDocumentUpload = exportEntryService.saveSBDocumentUpload(
                     companyId, branchId, sbNo, sbTransId, sbLineNo, hSbTransId, data, userId);
            return saveSBDocumentUpload;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while searching export");
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/searchExportMain")
	public ResponseEntity<?> searchExportMain(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="sbNo", required = false) String sbNo,
	        @RequestParam(value="containerNo", required = false) String containerNo	       
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = exportEntryService.searchExportMain(companyId, branchId, sbNo, containerNo);
	        return sbCargoGateIn;
	    } catch (Exception e) {	  	
	    	System.out.println(e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching export");
	    }
	}
	

	@GetMapping("/getDataForOutOfCharge")
	public ResponseEntity<?> getDataForOutOfCharge(
	        @RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,	        
	        @RequestParam("profitcentreId") String profitcentreId, @RequestParam("sbTransId") String sbTransId, 
	        @RequestParam("hSbTransId") String hSbTransId, @RequestParam("sbNo") String sbNo
	       ) {	    
	    try {	    	
	    	ExportSbEntry exportSbEntry = exportEntryService.getDataForOutOfCharge(companyId, branchId, profitcentreId, sbTransId, hSbTransId, sbNo);
	        return ResponseEntity.ok(exportSbEntry);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	    	System.out.println(e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	

	@PostMapping("/updateOutOfCharge")
	public ResponseEntity<?> updateOutOfCharge(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,
	        @RequestBody ExportSbEntry exportSbEntry, 
	        @RequestParam("userId") String user) {
	    
	    System.out.println(exportSbEntry);
	    
	    int updateOutOfCharge = exportEntryService.updateOutOfCharge(companyId, branchId, exportSbEntry, user);
	    
	    if (updateOutOfCharge == 1) {
	        return ResponseEntity.ok("Record Updated Successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Record could not be updated");
	    }
	}

	
	
	
	
	
	
		
//	@GetMapping("/searchExportMain")
//	public ResponseEntity<?> searchExportMain(
//	        @RequestParam("companyId") String companyId, 
//	        @RequestParam("branchId") String branchId,	        
//	        @RequestParam(value="sbNo", required = false) String sbNo,
//	        @RequestParam(value="vehicleNo", required = false) String vehicleNo,
//	        @RequestParam(value="containerNo", required = false) String containerNo,
//	        @RequestParam(value="bookingNo", required = false) String bookingNo,
//	        @RequestParam(value="pod", required = false) String pod
//	       ) {	    
//	    try {	    	
//	    	ResponseEntity<?> sbCargoGateIn = exportEntryService.searchExportMain(companyId, branchId, sbNo, vehicleNo, containerNo, bookingNo, pod);
//	        return sbCargoGateIn;
//	    } catch (Exception e) {	  	
//	    	System.out.println(e);
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching export");
//	    }
//	}
//	
	
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
