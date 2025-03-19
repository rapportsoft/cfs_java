package com.cwms.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.GeneralDeliveryGrid;
import com.cwms.entities.GeneralGatePassCargo;
import com.cwms.entities.JarDetail;
import com.cwms.repository.JarDetailRepository;
import com.cwms.service.GeneralGatePassService;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/gatepasscontroller")
public class GeneralGatePassController {

	@Autowired
	private GeneralGatePassService generalGatePassService;
	
	@Autowired
	JarDetailRepository jarDetailRepository;
	
	
	 @GetMapping("/getAllDataFormDeliveryDetails")
	    public List<Object[]> getAllDataFormDeliveryDetails(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "value", required = false) String value) {
	        return generalGatePassService.getAllDataFormDeliveryDetails(companyId, branchId, value);
	    }
	    
	    @GetMapping("/getDataDeliveryCargo")
	    public List<Object[]> getDataOfExBondCargo(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("exBondBeNo") String exBondBeNo,
	            @RequestParam(value = "value", required = false) String value) {
	        return generalGatePassService.getDataDeliveryCargo(companyId, branchId,exBondBeNo, value);
	    }
	    
	    @GetMapping("/getDataOfDeliveryGrid")
	    public ResponseEntity<List<GeneralDeliveryGrid>>  getDataAfterSave(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("deliveryId") String deliveryId,
	            @RequestParam("receivingId") String receivingId) {
	        return generalGatePassService.getDataAfterSave(companyId, branchId, deliveryId, receivingId);
	    }
	    
	    
	    @PostMapping("/saveDataOfGatePassAndGatePassDtl")
	    public ResponseEntity<?> saveDataOfGatePassAndGatePassDtl(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("user") String user,
	            @RequestParam("flag") String flag,
	            @RequestBody Map<String, Object> requestBody) {
	  
			return generalGatePassService.saveDataOfGatePassAndGatePassDtl(companyId, branchId, user, flag, requestBody);
	    }
	    
	    
	    @PostMapping("/updateDataInExbondGridAfterBondGatePass")
	    public ResponseEntity<?> approveDataOfCfExbondCrg(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> dataToSave) {
		   return generalGatePassService.updateDataInExbondGridAfterBondGatePass(companyId, branchId, flag, user, dataToSave);
	       
	    }
	    
	    @GetMapping("/sum")
	    public BigDecimal getSumOfQtyTakenOutForCommodity(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("exBondingId") String exBondingId,
	            @RequestParam("gatePassId") String gatePassId,
	            @RequestParam("cfBondDtlId") String cfBondDtlId) {
	        
	        return generalGatePassService.getSumOfQtyTakenOutForCommodity(companyId, branchId, exBondingId, gatePassId, cfBondDtlId);
	    }

	    @PostMapping("/approve")
	    public ResponseEntity<?> approveDataOfGaneralGatePass(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> requestBody) {
		   return generalGatePassService.approveDataOfInCFbondGrid(companyId, branchId, flag, user, requestBody);
	       
	    }
	    
	    
	    
	    @GetMapping("/dataAllDataOfGatePass")
	 		public ResponseEntity<List<GeneralGatePassCargo>> getCfbondnocData(@RequestParam String companyId,
	 				@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
	 			List<GeneralGatePassCargo> data = generalGatePassService.findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(companyId, branchId, partyName);
	 			return ResponseEntity.ok(data);
	 		}
	    @GetMapping("/list")
	    public ResponseEntity<List<GeneralGatePassCargo>> getAllListOfGatePass(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("gatePassId") String gatePassId
//	            @RequestParam("exBondBeNo") String exBondBeNo
	            ) {
	        List<GeneralGatePassCargo> gatePasses = generalGatePassService.getAllListOfGatePass(companyId, branchId, gatePassId);
	        return ResponseEntity.ok(gatePasses);
	    }
	    
	    @GetMapping("/generateBondGatePassPrint")
	    public ResponseEntity<String> generateSurveyPdf(
	    		@RequestParam(name = "companyId") String companyId,
				@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
				@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
				@RequestParam(name = "bname") String branchname, @RequestParam(name = "gatePassId") String gatePassId) throws DocumentException {
	        try {
	            // Call the service method and return the response
	            return generalGatePassService.getGeneralGatePassPrint(companyId, branchId, username, type, companyname, branchname, gatePassId);
	        } catch (Exception e) {
	            // In case of an error, you can handle the exception and return an appropriate response
	            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
	        }
	    }
	    
	    @GetMapping("/getDataForMainBondingSearch")
	    public ResponseEntity<?> getDataForMainSearch(
	    		@RequestParam ("companyId") String companyId,
	    		@RequestParam ("branchId") String branchId,
	    		@RequestParam (value ="nocNo", required =false ) String nocNo ,
	    		@RequestParam(value ="boeNo",required = false ) String boeNo,
	    		@RequestParam(value ="bondingNo",required = false) String bondingNo
	    		) {
			return generalGatePassService.getForMainBondingSearch(companyId, branchId, nocNo, boeNo, bondingNo);
		}
	    
	    @GetMapping("/jarIdList/{jarId}/{cid}")
		public List<JarDetail> getjarDtl(@PathVariable("jarId") String jarId,@PathVariable("cid") String cid) {
			System.out.println(jarId);
			return generalGatePassService.listByJarId(jarId,cid);
		}
}
