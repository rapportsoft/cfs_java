package com.cwms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import com.cwms.entities.GateIn;
import com.cwms.entities.HubDocument;
import com.cwms.entities.HubGatePass;
import com.cwms.entities.StuffRequestHub;
import com.cwms.service.HubService;

@RestController
@CrossOrigin("*")
@RequestMapping("Hub")
public class HubDocumentController {	
	
	@Autowired
	private HubService hubService;
	
	
	
	@GetMapping(value = "/downLoadHubReport")
	public ResponseEntity<?> downLoadHubReport(
	        @RequestParam("companyId") String companyid,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("gateInId") String gateInId,
	        @RequestParam("profitCenterId") String profitCenterId,
	        @RequestParam("type") String type,
	        @RequestParam("userId") String userId) {
	    
		System.out.println(companyid + " "  + branchId + " " + gateInId + " " + " profitCenterId " + " "+ type + " " + userId);
		
	    byte[] gateInReport = null; 

	    try {
	        if ("GateIn".equals(type)) {
	            gateInReport = hubService.downLoadHubReportGateIn(companyid, branchId, profitCenterId, gateInId, userId);
	        } 
	        else {
	            gateInReport = hubService.downLoadHubReportGatePass(companyid, branchId, profitCenterId, gateInId, userId);
	        }

	        // If no report is generated or returned, return a bad request with a message
	        if (gateInReport == null || gateInReport.length == 0) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                    .body("No report available for the provided parameters.");
	        }

	        // Prepare the headers for the response
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=getExportTruckWiseGateInReport.pdf");

	        // Return the report as a response
	        return ResponseEntity.ok().headers(headers).body(gateInReport);

	    } catch (Exception e) {
	        // Log the exception (ensure to use a logger in real code)
	        e.printStackTrace();  // Replace with proper logging (e.g., log.error(e.getMessage(), e))

	        // Return an internal server error response with an error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while generating the report: ");
	    }
	}
	
	
	
	
	
	@GetMapping("/searchHubMain")
	public ResponseEntity<?> searchHubMain(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="sbNo", required = false) String sbNo,
	        @RequestParam(value="sbLineNo", required = false) String sbLineNo	       
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = hubService.searchHubMain(companyId, branchId, sbNo, sbLineNo);
	        return sbCargoGateIn;
	    } catch (Exception e) {	  	
	    	System.out.println(e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while searching export");
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/searchContainerNoForHubGatePass")
	public ResponseEntity<?> searchContainerNoForHubGatePass(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("containerNo") String containerNo, @RequestParam("profitcentreId") String profitcentreId) {
		return hubService.searchContainerNoForHubGatePass(companyId, branchId, containerNo, profitcentreId);
	}
	
	@GetMapping("/searchContainerNoForHubGatePassSelect")
	public ResponseEntity<?> searchContainerNoForHubGatePassSelect(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("containerNo") String containerNo, @RequestParam("profitcentreId") String profitcentreId) {
		return hubService.searchContainerNoForHubGatePassSelect(companyId, branchId, containerNo, profitcentreId);
	}
	
	
	

	@PostMapping("/saveHubGatePass")
	public ResponseEntity<?> saveHubGatePass(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<HubGatePass> stuffRequestHub,
			@RequestParam("userId") String User) {
		ResponseEntity<?> saveHubStuffRequest = hubService.saveHubGatePass(companyId, branchId, stuffRequestHub, User);
		return saveHubStuffRequest;
	}
	
	
	

	
	
	@GetMapping("/getSelectedGatePassEntry")
	public ResponseEntity<?> getSelectedGatePassEntry(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("gatePassId") String gatePassId) {
		try {
			ResponseEntity<?> sbEntries = hubService.getSelectedGatePassEntry(companyId, branchId, gatePassId);
			return sbEntries;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getGatePassEntriesToSelect")
	public ResponseEntity<?> getGatePassEntriesToSelect(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitCenterId") String profitCenterId,			
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		try {
			List<Object[]> gateInEntries = hubService.getGatePassEntriesToSelect(companyId, branchId, searchValue, profitCenterId);
			return ResponseEntity.ok(gateInEntries);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/getSelectedStuffingEntry")
	public ResponseEntity<?> getSelectedStuffingEntry(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("stuffingReqId") String stuffingReqId) {
		try {
			ResponseEntity<?> sbEntries = hubService.getSelectedStuffingEntry(companyId, branchId, stuffingReqId);
			return sbEntries;
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getStuffingEntriesToSelect")
	public ResponseEntity<?> getStuffingEntriesToSelect(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitCenterId") String profitCenterId,			
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		try {
			List<Object[]> gateInEntries = hubService.getStuffingEntriesToSelect(companyId, branchId, searchValue, profitCenterId);
			return ResponseEntity.ok(gateInEntries);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	
	
	
	
	@PostMapping("/saveHubStuffRequestContainer")
	public ResponseEntity<?> saveHubStuffRequestContainer(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<StuffRequestHub> stuffRequestHub,
			@RequestParam("userId") String User) {
		ResponseEntity<?> saveHubStuffRequest = hubService.saveHubStuffRequestContainer(companyId, branchId, stuffRequestHub, User);
		return saveHubStuffRequest;
	}
	
	@GetMapping("/searchIgmNoForStuffing")
	public ResponseEntity<?> searchIgmNoForStuffing(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("searchValue") String searchValue, @RequestParam("profitcentreId") String profitcentreId, @RequestParam(value = "stuffReqId", required = false) String stuffReqId){
		return hubService.searchIgmNoForStuffing(companyId, branchId, searchValue, profitcentreId, stuffReqId);
	}
	
	@GetMapping("/searchContainerNoForHubCLP")
	public ResponseEntity<?> searchContainerNoForHubCLP(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("containerNo") String containerNo, @RequestParam("profitcentreId") String profitcentreId) {
		return hubService.searchContainerNoForHubCLP(companyId, branchId, containerNo, profitcentreId);
	}
	
	
	
	@GetMapping("/getSelectedGateInEntry")
	public ResponseEntity<?> getSelectedGateInEntry(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("igmNo") String igmNo,
			@RequestParam("gateInId") String gateInId, @RequestParam("hubTransId") String hubTransId,
			@RequestParam("profitCenterId") String profitCenterId) {
		try {
			ResponseEntity<?> sbEntries = hubService.getSelectedGateInEntry(companyId, branchId, igmNo, gateInId,
					hubTransId, profitCenterId);
			return sbEntries;
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getGateInEntriesToSelect")
	public ResponseEntity<?> getGateInEntriesToSelect(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,@RequestParam("processId") String processId, @RequestParam("profitCenterId") String profitCenterId,			
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		try {
			List<Object[]> gateInEntries = hubService.getGateInEntriesToSelect(companyId, branchId, searchValue, processId, profitCenterId);
			return ResponseEntity.ok(gateInEntries);
		} catch (Exception e) {
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	
	
	@PostMapping("/addHubGateIn")
	public ResponseEntity<?> addHubGateIn(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<GateIn> gateIn,
			@RequestParam("userId") String user) {

		ResponseEntity<?> addExportSbEntry = hubService.addHubGateIn(companyId, branchId, gateIn, user);

		return addExportSbEntry;
	}

	
	
	@GetMapping("/getIgmCargoGateIn")
	public ResponseEntity<?> getIgmCargoGateIn(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="gateInId", required = false) String gateInId,
	        @RequestParam("hubTransId") String hubTransId, @RequestParam("igmNo") String igmNo,  @RequestParam("profitCentreId") String profitCentreId	
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = hubService.getIgmCargoGateIn(companyId, branchId, hubTransId, gateInId, igmNo, profitCentreId);
	        return sbCargoGateIn;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	@GetMapping("/searchIgmNosToGateIn")
	public ResponseEntity<?> searchIgmNosToGateIn(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	
	        @RequestParam("profitCentreId") String profitCentreId,	
	        @RequestParam("searchValue") String searchValue,
	        @RequestParam(value = "gateInId", required = false) String gateInId 
	       ) {	    
	    try {	    	
	    	ResponseEntity<?> sbCargoGateIn = hubService.searchIgmNosToGateIn(companyId, branchId, profitCentreId, searchValue, gateInId);
	        return sbCargoGateIn;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate IgmNo No.");
	    }
	}
	
	
	
	
	@GetMapping("/getSelectedHubEntry")
	public ResponseEntity<?> getSelectedHubEntry(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("transId") String transId,
			@RequestParam("igmNo") String igmNo, @RequestParam("profitCenterId") String profitCenterId) {
		try {
			ResponseEntity<?> getSelectedHubEntry = hubService.getSelectedHubEntry(companyId, branchId, transId, igmNo, profitCenterId);
			return getSelectedHubEntry;
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getHubEntriesToSelect")
	public ResponseEntity<?> getHubEntriesToSelect(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,@RequestParam("profitCenterId") String profitCenterId,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		try {
			List<HubDocument> getHubEntriesToSelect = hubService.getHubEntriesToSelect(companyId, branchId, searchValue, profitCenterId);
			return ResponseEntity.ok(getHubEntriesToSelect);
		} catch (Exception e) {
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@PostMapping("/addHubDocumentEntry")
	public ResponseEntity<?> addHubDocumentEntry(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody HubDocument hubEntry,
			@RequestParam("userId") String user) {

		ResponseEntity<?> addHubDocumentEntry = hubService.addHubDocumentEntry(companyId, branchId, hubEntry, user);

		return addHubDocumentEntry;
	}

	
	
	
	
	

}
