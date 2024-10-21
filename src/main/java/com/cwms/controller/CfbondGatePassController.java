package com.cwms.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.Cfbondnoc;
import com.cwms.service.CfbondGatePassService;
import com.lowagie.text.DocumentException;


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
	            @RequestParam("gatePassId") String gatePassId
//	            @RequestParam("exBondBeNo") String exBondBeNo
	            ) {
	        List<CFBondGatePass> gatePasses = cfbondGatePassService.getAllListOfGatePass(companyId, branchId, gatePassId);
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
	    @GetMapping("/getDataOfVehicleNo")
	    public List<Object[]> getDataOfVehicleNo(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "value", required = false) String value) {
	        return cfbondGatePassService.getDataOfVehicleNo(companyId, branchId, value);
	    }
	    
	    @GetMapping("/getCommodityDetailsByVehicleNo")
	    public ResponseEntity<List<CFBondGatePass>> getCommodityByVehicleNo(@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId,@RequestParam (name = "vehicleNo",required = false) String vehicleNo) {
			List<CFBondGatePass> getData = cfbondGatePassService.getDataOfCommodityDetailsByVehicleNo(companyId,branchId,vehicleNo);
	    	return new ResponseEntity<>(getData,HttpStatus.OK);
		}
	    
	    
	    
	    
	    @GetMapping("/emptyVehicles")
	    public List<Object[]> getEmptyVehiclesForGatePass(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "vehicleNo", required = false) String vehicleNo) {
	        return cfbondGatePassService.getEmptyVehiclesForGatePass(companyId, branchId, vehicleNo);
	    }
	    
	    
	    @GetMapping("/sum")
	    public BigDecimal getSumOfQtyTakenOutForCommodity(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("exBondingId") String exBondingId,
	            @RequestParam("gatePassId") String gatePassId,
	            @RequestParam("cfBondDtlId") String cfBondDtlId) {
	        
	        return cfbondGatePassService.getSumOfQtyTakenOutForCommodity(companyId, branchId, exBondingId, gatePassId, cfBondDtlId);
	    }
	    
	    
	    
	    
	    
	    
	    
	    @PostMapping("/approve")
	    public ResponseEntity<?> approveDataOfCfExbondCrg(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> requestBody) {
		   return cfbondGatePassService.approveDataOfInCFbondGrid(companyId, branchId, flag, user, requestBody);
	       
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/generateBondGatePassPrint")
	    public ResponseEntity<String> generateSurveyPdf(
	    		@RequestParam(name = "companyId") String companyId,
				@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
				@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
				@RequestParam(name = "bname") String branchname, @RequestParam(name = "gatePassId") String gatePassId) throws DocumentException {
	        try {
	            // Call the service method and return the response
	            return cfbondGatePassService.printOfSurveyDetails(companyId, branchId, username, type, companyname, branchname, gatePassId);
	        } catch (Exception e) {
	            // In case of an error, you can handle the exception and return an appropriate response
	            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
	        }
	    }
}

