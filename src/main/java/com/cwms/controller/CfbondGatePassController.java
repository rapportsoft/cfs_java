package com.cwms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.Cfbondnoc;
import com.cwms.service.CfbondGatePassService;


@RestController
@RequestMapping("/api/cfbondgatepass")
@CrossOrigin("*")
public class CfbondGatePassController {

	@Autowired
    public CfbondGatePassService cfbondGatePassService;

    
	   @PostMapping("/saveDataOfGatePassAndGatePassDtl")
	    public ResponseEntity<?> saveDataOfGatePassAndGatePassDtl(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("user") String user,
	            @RequestParam("flag") String flag,
	            @RequestBody Map<String, Object> requestBody) {
	  
			return cfbondGatePassService.saveDataOfGatePassAndGatePassDtl(companyId, branchId, user, flag, requestBody);
	    }
	   
	   
	   @GetMapping("/dataAllDataOfGatePass")
		public ResponseEntity<List<CFBondGatePass>> getCfbondnocData(@RequestParam String companyId,
				@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
			List<CFBondGatePass> data = cfbondGatePassService.findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(companyId, branchId, partyName);
			return ResponseEntity.ok(data);
		}
	   
	   @GetMapping("/list")
	    public ResponseEntity<List<CFBondGatePass>> getAllListOfGatePass(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("gatePassId") String gatePassId,
	            @RequestParam("exBondBeNo") String exBondBeNo) {
	        List<CFBondGatePass> gatePasses = cfbondGatePassService.getAllListOfGatePass(companyId, branchId, gatePassId, exBondBeNo);
	        return ResponseEntity.ok(gatePasses);
	    }
	   
	   
	   @GetMapping("/getDataOfExBondBeNo")
	    public List<Object[]> getDataOfExBondBeNo(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "value", required = false) String value) {
	        return cfbondGatePassService.getDataOfExbondBeNo(companyId, branchId, value);
	    }
	    
	    
	    
	    @GetMapping("/getVehicleNoOfExbondBeNoFromGatePass")
	    public List<Object[]> getVehicleNoOfExbondBeNoFromGatePass(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("ecBondBeNo") String ecBondBeNo,
	            @RequestParam(value = "value", required = false) String value) {
	        return cfbondGatePassService.getVehicleNoOfExbondBeNoFromGatePass(companyId, branchId,ecBondBeNo, value);
	    }
}

