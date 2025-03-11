package com.cwms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.GeneralJobOrderEntryDetails;
import com.cwms.entities.GenerelJobEntry;
import com.cwms.entities.Party;
import com.cwms.service.GeneralJobEntryService;

@RestController
@RequestMapping("/api/generalJobEntry")
@CrossOrigin("*")
public class GeneralJobEntryController {

	
	@Autowired
	private GeneralJobEntryService jobEntrySrvice; 
	
	
	@GetMapping("/search")
	public List<Party> getParties(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam(name = "partyName", required = false) String partyName) {
		return jobEntrySrvice.findParties(companyId, branchId, partyName);
	}
	
	@PostMapping("/saveNoc")
	public ResponseEntity<?> saveData(@RequestParam String cid, @RequestParam String bid, @RequestParam String user,
			@RequestParam String flag, @RequestBody Map<String, Object> requestBody) {

		// Call the saveData method from the service
		return jobEntrySrvice.saveData(cid, bid, user, flag, requestBody);
	}
	
	@GetMapping("/dataAllDataOfCfBondNoc")
	public ResponseEntity<List<GenerelJobEntry>> getCfbondnocData(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<GenerelJobEntry> data = jobEntrySrvice.getCfbondnocData(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}
	
	
	@GetMapping("/getCfBondNocDtlForNocScreen")
	public List<GeneralJobOrderEntryDetails> getCfBondNocDtlForNocScreen(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("nocTransId") String nocTransId,
			@RequestParam("nocNo") String nocNo) {
		return jobEntrySrvice.getCfBondNocDtlForNocScreen(companyId, branchId, nocTransId, nocNo);
	}
	
//	@GetMapping("/getDataByTransIdANDNocID")
//	public GenerelJobEntry getDataByTransIdANDNocID(@RequestParam("companyId") String companyId,
//			@RequestParam("branchId") String branchId, @RequestParam("nocTransID") String nocTransID,
//			@RequestParam("nocNo") String nocNo) {
//		return jobEntrySrvice.getCfbondnocDataByidOrSearch(companyId, branchId, nocTransID, nocNo);
//	}
	
	
	
	@GetMapping("/getDataByTransIdANDNocID")
	public ResponseEntity<GenerelJobEntry> getDataByTransIdANDNocID(
	        @RequestParam("companyId") String companyId,
	        @RequestParam("branchId") String branchId,
	        @RequestParam("nocTransID") String nocTransID,
	        @RequestParam("nocNo") String nocNo) {
	    
	    GenerelJobEntry jobEntry = jobEntrySrvice.getCfbondnocDataByidOrSearch(companyId, branchId, nocTransID, nocNo);
	    
	    if (jobEntry == null) {
	        return ResponseEntity.notFound().build();  // Return 404 if no data found
	    }
	    
	    return ResponseEntity.ok(jobEntry);  // Return 200 with data
	}

	
	
	
	@PostMapping("/saveCfBondNoc")
	public ResponseEntity<?> saveCfBondNoc(@RequestParam String cid, @RequestParam String bid,
			@RequestParam String user, @RequestParam String flag, @RequestBody GenerelJobEntry requestBody) {

		// Call the saveData method from the service
		return jobEntrySrvice.saveDataOfCfBondNoc(cid, bid, user, flag, requestBody);
	}
	
	
	@GetMapping("/getDataCfBondNocDtl")
	public GeneralJobOrderEntryDetails getDataCfBondNocDtl(@RequestParam String companyId, @RequestParam String branchId,
			@RequestParam String dtlid, @RequestParam String trasnId, @RequestParam String nocId) {
		return jobEntrySrvice.getDataCfBondDtlById(companyId, branchId, dtlid, trasnId, nocId);
	}

}
