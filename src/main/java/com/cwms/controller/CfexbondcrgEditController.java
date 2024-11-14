package com.cwms.controller;

import java.math.BigDecimal;
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

import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.CfexbondcrgEdit;
import com.cwms.entities.CfinbondCommdtlEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.service.CfexbondcrgEditService;

@RestController
@RequestMapping("/api/cfexbondcrgEditController")
@CrossOrigin("*")
public class CfexbondcrgEditController {

	@Autowired
	public CfexbondcrgEditService cfexbondcrgEditService;
	
	
	@GetMapping("/getBoeNoData")
	public ResponseEntity<List<Cfinbondcrg>>  getDataOfBoeNoForInBondAuditTrailScreen(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("boeNo") String boeNo) {
		
		return cfexbondcrgEditService.getDataForInBondAuditTrailScreen(companyId, branchId, boeNo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/getExBondBoeNoData")
	public ResponseEntity<List<CfExBondCrg>>  getDataForExBondAuditTrailScreen(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("boeNo") String boeNo) {
		
		return cfexbondcrgEditService.getDataForExBondAuditTrailScreen(companyId, branchId, boeNo);
	}
	@GetMapping("/getFormCfInBondCrgDtl")
	public ResponseEntity<List<CfinbondcrgDtl>>  getDataFromCfInBondCrgDtl(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("inBondingId") String inBondingId,
			@RequestParam ("nocTransId") String nocTransId) {
		
		return cfexbondcrgEditService.getDataFromCfInBondCrgDtl(companyId, branchId, inBondingId,nocTransId);
	}
	
	
	
	
	
	 @GetMapping("/search")
	    public List<CfexbondcrgEdit> getParties(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(name = "partyName", required = false) String partyName) {
	        return cfexbondcrgEditService.findAllCfinbondCrgIn(companyId, branchId, partyName);
	    }
	 
	 
	 
	 
	 
	 
	 @GetMapping("/searchExBondAuditTrail")
	    public List<CfexbondcrgEdit> getDataOfExbondAuditTrail(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(name = "partyName", required = false) String partyName) {
	        return cfexbondcrgEditService.getForExBondAuditTrailScreen(companyId, branchId, partyName);
	    }
	 
	 
	 @GetMapping("/getBySelectingRadioButton")
		public CfexbondcrgEdit getBySelectingRadioButtonExBondAuditTrail(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
				@RequestParam(value = "SrNo", required = false) Long SrNo,
				@RequestParam("nocTransID") String nocTransID,@RequestParam("exBondingId") String exBondingId,@RequestParam("nocNo") String nocNo){
			return cfexbondcrgEditService.getBySelectingRadioButton(companyId, branchId, SrNo,nocTransID,exBondingId,nocNo);
		}
	 
	 
	 
	 
	 @GetMapping("/getDataByTransIdANDNocIDAndInBondingId")
		public CfexbondcrgEdit getDataByTransIdANDNocID(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
				@RequestParam(value = "SrNo", required = false) Long SrNo,
				@RequestParam("nocTransID") String nocTransID,@RequestParam("inBondingId") String inBondingId,@RequestParam("nocNo") String nocNo){
			return cfexbondcrgEditService.getCfInbondCrgDataByidOrSearch(companyId, branchId, SrNo,nocTransID,inBondingId,nocNo);
		}
	 
	@GetMapping("/findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId")
	public ResponseEntity<List<CfinbondCommdtlEdit>>  findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("inBondingId") String inBondingId,
			@RequestParam ("nocTransId") String nocTransId) {
		
		return cfexbondcrgEditService.findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(companyId, branchId, inBondingId,nocTransId);
	}
	
	
	
	
	
	
	
	@GetMapping("/getForExBondDtl")
	public ResponseEntity<List<CfinbondCommdtlEdit>>  getForExBondDtl(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("exBondingId") String exBondingId,
			@RequestParam ("nocTransId") String nocTransId) {
		
		return cfexbondcrgEditService.getForExBondDtl(companyId, branchId, exBondingId,nocTransId);
	}
	
	
	
	
	
	
	@GetMapping("/getFromCfExBondCrgDtlToChange")
	public ResponseEntity<List<CfexBondCrgDtl>>  getFromCfExBondCrgDtlToChange(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("nocTransId") String nocTransId,
			@RequestParam ("inBondingId") String inBondingId,
			@RequestParam ("exBondBeNo") String exBondBeNo,
			@RequestParam ("exBondingId") String exBondingId
			) {
		
		return cfexbondcrgEditService.getFromCfExBondCrgDtlToChange(companyId, branchId,nocTransId,inBondingId,exBondBeNo,exBondingId);
	}
	
	
	
	
	@PostMapping("/saveDataOfCfInbondCrgEditAuditTrail")
    public ResponseEntity<?> saveDataOfCfInbondCrgEditAuditTrail(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return cfexbondcrgEditService.saveDataOfCfInbondCrgEditAuditTrail(companyId, branchId, user, flag, requestBody);
		
    }
	
	
	
	
	
	
	
	
	@PostMapping("/saveCfExBondDataFromExBondAuditTrailScreen")
    public ResponseEntity<?> saveCfExBondGridDataFromExBondAuditTrailScreen(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return cfexbondcrgEditService.saveCfExBondDataFromExBondAuditTrailScreen(companyId, branchId, user, flag, requestBody);
		
    }
	
	
	
	
	
	 @GetMapping("/sum")
	    public ResponseEntity<BigDecimal> getSumOfInBondPackagesForCommodity(
	            @RequestParam String companyId,
	            @RequestParam String branchId,
	            @RequestParam String inBondingId,
	            @RequestParam String cfBondDtlId,
	            @RequestParam String nocTransId) {
	        
	        BigDecimal sumOfInBondPackages = cfexbondcrgEditService.getSumOfInBondPackagesForCommodity(companyId, branchId, inBondingId, cfBondDtlId, nocTransId);
	        return ResponseEntity.ok(sumOfInBondPackages);
	 }
	 
	 
	 
	 
	 
	 
	  @PostMapping("/approve")
	    public ResponseEntity<?> approveDataOfInCFbondGrid(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> requestBody) {
		   return cfexbondcrgEditService.approveDataOfInCFbondGrid(companyId, branchId, flag, user, requestBody);
	       
	    }
	  
	  @PostMapping("/approveDataFromExBondAuditTrailScreen")
	    public ResponseEntity<?> approveDataFromExBondAuditTrailScreen(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> requestBody) {
		   return cfexbondcrgEditService.approveDataFromExBondAuditTrailScreen(companyId, branchId, flag, user, requestBody);
	       
	    }
}
