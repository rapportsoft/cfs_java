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

import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfInBondGrid;
import com.cwms.service.CfExBondGridService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/exbondgrid")
public class CfExBondGridController {

	@Autowired
	private CfExBondGridService cfExBondGridService; 
	
	
	@GetMapping("/getInbondGridForExbond")
	public ResponseEntity<List<CfInBondGrid>> getInBondGridData(@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("inBondingId") String inBondingId,
			@RequestParam ("nocTransId") String nocTransId,
			@RequestParam ("cfBondDtlId") String cfBondDtlId) {
		
		return cfExBondGridService.getDataOfInBondGrid(companyId, branchId, inBondingId, nocTransId, cfBondDtlId);
	}
	
	
	@PostMapping("/saveCfExBondGrid")
	public ResponseEntity<?> saveCfExbondGrid(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("flag") String flag ,
			@RequestParam ("user") String user,
			 @RequestBody Map<String, Object> dataToSave) {
		
		System.out.println("grid__________________"+dataToSave);
		
		
		return cfExBondGridService.saveDataInCFExbondGrid(companyId, branchId, flag, user, dataToSave);
	}
	
	
	 @GetMapping("/savedDataOfExbondGrid")
	    public ResponseEntity<List<CfExBondGrid>>  getDataAfterSave(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("exBondingId") String exBondingId,
	            @RequestParam("cfBondDtlId") String cfBondDtlId) {
	        return cfExBondGridService.getDataAfterSave(companyId, branchId, exBondingId, cfBondDtlId);
	    }
	 
	    @PostMapping("/updateDataInExbondGridAfterBondGatePass")
	    public ResponseEntity<?> approveDataOfCfExbondCrg(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> dataToSave) {
		   return cfExBondGridService.updateDataInExbondGridAfterBondGatePass(companyId, branchId, flag, user, dataToSave);
	       
	    }
}
