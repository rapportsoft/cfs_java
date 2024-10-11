package com.cwms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CfInBondGrid;
import com.cwms.service.CfInBondGridService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cfinbondgrid")
public class CfInBondGridController {

	@Autowired
	public CfInBondGridService cfInBondGridService;
	
	@PostMapping("/saveCfInBondGrid")
	public ResponseEntity<?> saveCfIbindGrid(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("flag") String flag ,
			@RequestParam ("user") String user,
			 @RequestBody Map<String, Object> requestBody) {
		
		System.out.println("grid__________________"+requestBody);
		
		
		return cfInBondGridService.saveDataInCFbondGrid(companyId, branchId, flag, user, requestBody);
	}
	
	@GetMapping("/getAfterSaveGrid")
	public ResponseEntity<List<CfInBondGrid>> getAfterSaved (@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId ,
		@RequestParam ("inBondingId") String inBondingId,
		@RequestParam ("cfBondDtlId") String cfBondDtlId)
	{
		return cfInBondGridService.getDataAfterSaved(companyId, branchId, inBondingId,cfBondDtlId);
	}
	
	
	@DeleteMapping("/deleteRecord")
	public ResponseEntity<?> deleteRecord (@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId ,
		@RequestParam ("inBondingId") String inBondingId,
		@RequestParam ("cfBondDtlId") String cfBondDtlId,
		@RequestParam ("srNo") Integer srNo)
	{
		return cfInBondGridService.getDeleteRecord(companyId, branchId, inBondingId,cfBondDtlId,srNo);
	}
	
}
