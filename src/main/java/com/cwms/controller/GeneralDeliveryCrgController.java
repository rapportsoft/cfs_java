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

import com.cwms.entities.GeneralDeliveryCrg;
import com.cwms.entities.GeneralDeliveryDetails;
import com.cwms.entities.GeneralDeliveryGrid;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingGateInDtl;
import com.cwms.entities.GeneralReceivingGrid;
import com.cwms.service.GeneralDeliveryCrgService;

@RestController
@RequestMapping("/api/generaldelivery")
@CrossOrigin("*")
public class GeneralDeliveryCrgController {

	@Autowired
	private GeneralDeliveryCrgService  generalDeliveryCrgService;
	
	@GetMapping("/generalDeliveryCrgService")
	public ResponseEntity<List<GeneralReceivingCrg>> dataAllDataOfCfBondNocForInbondScreen(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<GeneralReceivingCrg> data = generalDeliveryCrgService.getDataByBoeNoForDeliveryScreen(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/generalDeliveryCrgServiceFIFO")
	public ResponseEntity<List<GeneralReceivingCrg>> dataAllDataOfCfBondNocForInbondScreenFIFO(@RequestParam String companyId,
			@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
		List<GeneralReceivingCrg> data = generalDeliveryCrgService.generalDeliveryCrgServiceFIFO(companyId, branchId, partyName);
		return ResponseEntity.ok(data);
	}
	
	
	
	
	@GetMapping("/getAfterSaveGrid")
	public ResponseEntity<List<GeneralReceivingGrid>> getAfterSaved (@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId ,
		@RequestParam ("receivingId") String receivingId)
	{
		return generalDeliveryCrgService.getDataAfterSaved(companyId, branchId, receivingId);
	}
	
	@GetMapping("/getDataForDeliveryScreenFromReceivingDTL")
	public ResponseEntity<List<GeneralReceivingGateInDtl>> getDataForDeliveryScreenFromReceivingDTL (@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId ,
		@RequestParam ("receivingId") String receivingId)
	{
		return generalDeliveryCrgService.getDataForDeliveryScreenFromReceivingDTL(companyId, branchId, receivingId);
	}
	
	
	
	
	
	
	@PostMapping("/saveCfInbondCrg")
    public ResponseEntity<?> saveDataOfCfInbondCrg(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return generalDeliveryCrgService.saveDataOfCfInbondCrg(companyId, branchId, user, flag, requestBody);
    }
	
	
	
	
	
	@GetMapping("/getSavedDeliveryDetails")
	public ResponseEntity<List<GeneralDeliveryDetails>>  findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("deliveryid") String deliveryid,
			@RequestParam ("receivingId") String receivingId,@RequestParam ("depositeNo") String depositeNo) {
		
		return generalDeliveryCrgService.findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(companyId, branchId, deliveryid,receivingId,depositeNo);
	}
	
	@GetMapping("/getSavedDataOfDeliveryGrid")
	public ResponseEntity<List<GeneralDeliveryGrid>>  getSavedDataOfDeliveryGrid(
			@RequestParam ("companyId") String companyId,
			@RequestParam ("branchId") String branchId,
			@RequestParam ("deliveryId") String deliveryId,
			@RequestParam ("receivingId") String receivingId) {
		
		return generalDeliveryCrgService.getSavedDataOfDeliveryGrid(companyId, branchId, deliveryId,receivingId);
	}
	
	
	
	
	
	 @GetMapping("/search")
	    public List<GeneralDeliveryCrg> getParties(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(name = "partyName", required = false) String partyName) {
	        return generalDeliveryCrgService.findAllCfinbondCrgIn(companyId, branchId, partyName);
	    }
	 
	 @GetMapping("/getDataByTransIdANDNocIDAndInBondingId")
		public GeneralDeliveryCrg getDataByTransIdANDNocID(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
				@RequestParam("deliveryId") String deliveryId,@RequestParam("receivingId") String receivingId,@RequestParam("depositeNo") String depositeNo,@RequestParam("boeNo") String boeNo){
			return generalDeliveryCrgService.getCfInbondCrgDataByidOrSearch(companyId, branchId, deliveryId,receivingId,depositeNo,boeNo);
		}
	
}
