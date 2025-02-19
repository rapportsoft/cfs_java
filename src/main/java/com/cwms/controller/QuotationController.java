package com.cwms.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.cwms.entities.CFSTariffServiceQuatation;
import com.cwms.entities.CfsTarrifQuatation;
import com.cwms.service.Quotationservice;

@RestController
@RequestMapping("/quotation")
@CrossOrigin("*")
public class QuotationController {

	
	@Autowired
	public Quotationservice tariffService;
	
	
	@GetMapping(value = "/downLoadTariffReportTemlate")
	public ResponseEntity<?> getExportTruckWiseGateInReportTemlate( 
	        @RequestParam("companyId") String companyid, 
	        @RequestParam("branchId") String branchId,	      
	        @RequestParam("userId") String userId) {	    		
	    byte[] tariffReport = null; 

	    try {
	      
	            tariffReport = tariffService.getTariffReportTemplate(companyid, branchId, "CFST00001", "000", "DEMO", userId);
	        

	        if (tariffReport == null || tariffReport.length == 0) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                    .body("No report available for the provided parameters.");
	        }

	        // Set headers for the response
	        HttpHeaders headersNew = new HttpHeaders();
	        headersNew.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	        headersNew.setContentDispositionFormData("attachment", "TariffTemplate.xlsx");
	        // Return the Excel file as a byte array in the response body
	        return ResponseEntity.ok().headers(headersNew).body(tariffReport);
	    } catch (Exception e) {	       
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the report: ");
	    }
	}

	
	
	
	@PostMapping("/uploadTariff")
	public ResponseEntity<?> uploadTariff(
	        @RequestParam("companyId") String companyId,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("userId") String userId,
	        @RequestPart("cfsTariff") String cfsTariff,
	        @RequestParam("file") MultipartFile file) {
		
		System.out.println("companyId " + companyId + " branchId " + branchId + " user " + userId + " \n cfsTariff " + cfsTariff + " \n file " + file);
		
		ResponseEntity<?> addExportSbEntry = tariffService.uploadTariff(companyId, branchId, cfsTariff, file, userId);
		return addExportSbEntry;
	}
	
	
	
	
	@GetMapping(value = "/downLoadTariffReport")
	public ResponseEntity<?> getExportTruckWiseGateInReport(
	        @RequestParam("companyId") String companyid,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("cfsTariffNo") String cfsTariffNo,
	        @RequestParam("cfsAmendNo") String cfsAmendNo,@RequestParam("contractName") String contractName,
	        @RequestParam("type") String type,
	        @RequestParam("userId") String userId) {
	    
		System.out.println(companyid + " "  + branchId + " " + cfsTariffNo + " "+ cfsAmendNo + " " + contractName + " " +type + " " + userId);
		
	    byte[] tariffReport = null; 

	    try {
	        // Check if the type is "GateIn" and fetch the report
	        if ("tariff".equals(type)) {
	            tariffReport = tariffService.getTariffReport(companyid, branchId, cfsTariffNo, cfsAmendNo, contractName, userId);
	        } else {
	        	tariffReport = tariffService.getAuditTrailReport(companyid, branchId, cfsTariffNo, cfsAmendNo, contractName, userId);
	        }

	        if (tariffReport == null || tariffReport.length == 0) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT)
	                    .body("No report available for the provided parameters.");
	        }

	        // Set headers for the response
	        HttpHeaders headersNew = new HttpHeaders();
	        headersNew.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	        headersNew.setContentDispositionFormData("attachment", "Tariff.xlsx");
	        // Return the Excel file as a byte array in the response body
	        return ResponseEntity.ok().headers(headersNew).body(tariffReport);
	    } catch (Exception e) {	       
	    	System.out.print("Triff Report" + e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while generating the report: ");
	    }
	}

	
	
	
	
	
	
	
	
	
	@PostMapping("/ammendTariff")
	public ResponseEntity<?> ammendTariff(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody CfsTarrifQuatation cfsTariff, @RequestParam("userId") String user) {
		ResponseEntity<?> addExportSbEntry = tariffService.ammendTariffController(companyId, branchId, cfsTariff, user);
		return addExportSbEntry;
	}

	

	@PostMapping("/addHeaderCfsTariff")
	public ResponseEntity<?> addHeaderCfsTariff(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody CfsTarrifQuatation cfsTariff,
			@RequestParam("userId") String user, @RequestParam("type") String type) {
		ResponseEntity<?> addExportSbEntry = tariffService.addHeaderCfsTariff(companyId, branchId, cfsTariff, user, type);
		return addExportSbEntry;
	}

	
	
	
	
	@PostMapping("/addDetailCfsTariffService")
	public ResponseEntity<?> addDetailCfsTariffService(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,@RequestBody Map<String, Object> requestData, @RequestParam("userId") String user) {
		ResponseEntity<?> addExportSbEntry = tariffService.addDetailCfsTariffService(companyId, branchId, requestData,	user);
		return addExportSbEntry;
	}

	@GetMapping("/getSavedTariff")
	public ResponseEntity<?> getSavedTariff(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("tariffNo") String tariffNo,
			@RequestParam("cfsAmndNo") String cfsAmndNo) {
		ResponseEntity<?> partyByTypeValue = tariffService.getSavedTariff(companyId, branchId, tariffNo, cfsAmndNo);
		return partyByTypeValue;
	}

	@GetMapping("/searchSavedTariff")
	public ResponseEntity<?> searchSavedTariff(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam(name = "type", required = false) String type) {
		ResponseEntity<?> partyByTypeValue = tariffService.searchSavedTariff(companyId, branchId, type);
		return partyByTypeValue;
	}

	@GetMapping("/getAllServices")
	public ResponseEntity<?> getAllServices(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId) {
		try {
			ResponseEntity<?> serviceList = tariffService.getAllServices(companyId, branchId);
			return serviceList;
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getSingleService")
	public ResponseEntity<?> getSingleService(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("serviceId") String serviceId,
			@RequestParam("tariffNo") String tariffNo, @RequestParam("cfsAmndNo") String cfsAmndNo) {
		try {
			List<CFSTariffServiceQuatation> selectedService = tariffService.getSingleService(companyId, branchId, serviceId, tariffNo, cfsAmndNo);
			
			return ResponseEntity.ok(selectedService);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getPartyByTypeValue")
	public ResponseEntity<?> getPartyByTypeValue(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("searchValue") String searchValue,
			@RequestParam(name = "type", required = false) String type) {
		ResponseEntity<?> partyByTypeValue = tariffService.getPartyByTypeValue(companyId, branchId, searchValue, type);
		return partyByTypeValue;
	}
	
	
}
