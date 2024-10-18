package com.cwms.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.Party;
import com.cwms.service.CfExBondCrgService;

import jakarta.mail.Part;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cfexbondcrg")
@CrossOrigin("*")
public class CfExBondCrgController {

    @Autowired
    private CfExBondCrgService cfExBondCrgService;

    @GetMapping("/getALLCfInBondCrgDtl")
    public List<CfinbondcrgDtl> getALLCfbondNocDtl(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("nocTransId") String nocTransId,
            @RequestParam("nocNo") String nocNo,
            @RequestParam("inBondigId") String inBondigId,
            @RequestParam("boeNo") String boeNo) {
        return cfExBondCrgService.findAllCfBondNocDtl(companyId, branchId, nocTransId, nocNo, inBondigId, boeNo);
    }
    
    
    @GetMapping("/getALLForworder")
    public List<Party> getALLCfbondNocDtl(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam( required = false, name="partyName" ) String partyName) {
        return cfExBondCrgService.findAllParty(companyId, branchId, partyName);
    }
    
    @GetMapping("/getDataOfForworder")
    public Object[] getDataOfForworder(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam( "partyId") String partyId) {
        return cfExBondCrgService.findForworderData(companyId, branchId, partyId);
    }
    
    
    @PostMapping("/saveCfExbondcrgAndExbondDetails")
    public ResponseEntity<?> saveCfExbondcrgAndExbondDetails(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return cfExBondCrgService.saveDataOfCfexbondCrgAndExbondCrgDtl(companyId, branchId, user, flag, requestBody);
    }
    
    
    @GetMapping("/exbondSearch")
    public ResponseEntity<List<CfExBondCrg>> findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam(value = "partyName", required = false) String partyName) {
        
        List<CfExBondCrg> result = cfExBondCrgService.getAllSavedDataOfExbondcrg(companyId, branchId, partyName);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/getDataOfExbond")
    public ResponseEntity<CfExBondCrg> getDataOfExbond(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("nocTransId") String nocTransId,
            @RequestParam("nocNo") String nocNo,
            @RequestParam("exBondingId") String exBondingId,
            @RequestParam("inBondingId") String inBondingId) {
        
        CfExBondCrg result = cfExBondCrgService.getDataOfExbond(companyId, branchId, nocTransId, nocNo, exBondingId, inBondingId);
        
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/exBondDetails")
    public List<CfexBondCrgDtl> getCfBondInBondDTLData(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("nocTransId") String nocTransId,
            @RequestParam("nocNo") String nocNo,
            @RequestParam("inBondingId") String inBondingId,
            @RequestParam("boeNo") String boeNo,
            @RequestParam("exBondingId") String exBondingId) {
        
        return cfExBondCrgService.getCfBondInBondDTLData(companyId, branchId, nocTransId, nocNo, inBondingId, boeNo, exBondingId);
    }
    
    
    
    
    
    
    
    
    
    @GetMapping("/getDataOfCha")
    public List<Object[]> getData1(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam(value = "value", required = false) String value) {
        return cfExBondCrgService.getData1(companyId, branchId, value);
    }
    
//    @GetMapping("/getDataOfImporter")
//    public List<Object[]> getDataOfImporter(
//            @RequestParam("companyId") String companyId,
//            @RequestParam("branchId") String branchId,
//            @RequestParam("cha") String cha,
//            @RequestParam(name = "value", required = false) String value) {
//    	
//    	System.out.println("value"+value);
//        return cfExBondCrgService.getDataOfImporter(companyId, branchId,cha,value);
//    }
    
    @GetMapping("/getDataOfImporter")
    public List<Object[]> getDataOfImporter(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("cha") String cha) {
        return cfExBondCrgService.getDataOfImporter(companyId, branchId,cha);
    }
    
    
    
    @GetMapping("/getAddressOfImporter")
    public List<Object[]> getAddressOfImporter(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("partyId") String partyId) {
        return cfExBondCrgService.getAddressOfImporter(companyId, branchId,partyId);
    }
    
    
    
    
    
    @GetMapping("/getDataOfExBondBeNo")
    public List<Object[]> getDataOfExBondBeNo(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam(value = "value", required = false) String value) {
        return cfExBondCrgService.getDataOfExbondBeNo(companyId, branchId, value);
    }
    
    
    @GetMapping("/getDataOfExBondCargo")
    public List<Object[]> getDataOfExBondCargo(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("exBondBeNo") String exBondBeNo,
            @RequestParam(value = "value", required = false) String value) {
        return cfExBondCrgService.getDataOfExbondBeNoCargo(companyId, branchId,exBondBeNo, value);
    }
    

    @PostMapping("/approve")
    public ResponseEntity<?> approveDataOfCfExbondCrg(
            @RequestParam("companyId") String companyId, 
            @RequestParam("branchId") String branchId, 
            @RequestParam("flag") String flag, 
            @RequestParam("user") String user, 
            @RequestBody Map<String, Object> requestBody) {
	   return cfExBondCrgService.approveDataOfInCFbondGrid(companyId, branchId, flag, user, requestBody);
       
    }
    
    
    @GetMapping("/sum")
    public ResponseEntity<BigDecimal> getSumOfInBondPackagesForCommodity(
            @RequestParam String companyId,
            @RequestParam String branchId,
            @RequestParam String exBondingId,
            @RequestParam String cfBondDtlId,
            @RequestParam String nocTransId) {
        
        BigDecimal sumOfInBondPackages = cfExBondCrgService.getSumOfInBondPackagesForCommodity(companyId, branchId, exBondingId, cfBondDtlId, nocTransId);
        return ResponseEntity.ok(sumOfInBondPackages);
    }
}
