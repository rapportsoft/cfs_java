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

import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.GeneralGateIn;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingGateInDtl;
import com.cwms.entities.GeneralReceivingGrid;
import com.cwms.service.GeneralReceivingCrgService;

@RestController
@RequestMapping("/api/receiving")
@CrossOrigin("*")
public class GeneralReceivingCrgController {

	@Autowired
	private GeneralReceivingCrgService generalReceivingCrgService;
	
	@GetMapping("/dataAllDataOfCfBondNocForInbondScreen")
	public ResponseEntity<List<GeneralGateIn>> dataAllDataOfCfBondNocForInbondScreen(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<GeneralGateIn> data = generalReceivingCrgService.getCfbondnocDataForInBondScreen(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/getALLCfbondNocDtl")
	public List<GeneralGateIn> getALLCfbondNocDtl(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("nocTransId") String nocTransId,
			@RequestParam("nocNo") String nocNo) {
		return generalReceivingCrgService.findAllCfBondNocDtl(companyId, branchId, nocTransId, nocNo);
	}
	
	@PostMapping("/saveCfInbondCrg")
    public ResponseEntity<?> saveDataOfCfInbondCrg(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return generalReceivingCrgService.saveDataOfCfInbondCrg(companyId, branchId, user, flag, requestBody);
    }
	
	
	
	
	@GetMapping("/findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId")
	public ResponseEntity<List<GeneralReceivingGateInDtl>>  findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("inBondingId") String inBondingId) {
		
		return generalReceivingCrgService.findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(companyId, branchId, inBondingId);
	}
	
	
	
	
	
	
	
	 @GetMapping("/search")
	    public List<GeneralReceivingCrg> getParties(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(name = "partyName", required = false) String partyName) {
	        return generalReceivingCrgService.findAllCfinbondCrgIn(companyId, branchId, partyName);
	    }
	 
	 
	 
	 @GetMapping("/getDataByTransIdANDNocIDAndInBondingId")
		public GeneralReceivingCrg getDataByTransIdANDNocID(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
				@RequestParam("nocTransID") String nocTransID,@RequestParam("inBondingId") String inBondingId,@RequestParam("nocNo") String nocNo){
			return generalReceivingCrgService.getCfInbondCrgDataByidOrSearch(companyId, branchId, nocTransID,inBondingId,nocNo);
		}
	 
	 @GetMapping("/getAfterSaveGrid")
		public ResponseEntity<List<GeneralReceivingGrid>> getAfterSaved (@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId ,
			@RequestParam ("inBondingId") String inBondingId)
		{
			return generalReceivingCrgService.getDataAfterSaved(companyId, branchId, inBondingId);
		}
}
