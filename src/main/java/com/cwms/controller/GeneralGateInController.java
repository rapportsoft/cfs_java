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

import com.cwms.entities.GateIn;
import com.cwms.entities.GeneralGateIn;
import com.cwms.service.GeneralGateInService;

@RestController
@RequestMapping("/api/generalgatein")
@CrossOrigin("*")
public class GeneralGateInController {

	@Autowired
	public GeneralGateInService generalGateInService;
	
	 @GetMapping("/getAllBoeNoFromJobEntry")
	    public List<Object[]> getAllBoeNoFromJobEntry(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(value = "value", required = false) String value) {
	        return generalGateInService.getAllBoeNoFromJobEntry(companyId, branchId, value);
	    }
	    
	    
	    
	    
	    
	    
	    
	    @GetMapping("/getAllNocDtl")
	    public List<Object[]> getAllNocDtl(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("boeNo") String boeNo,
	            @RequestParam("nocTransId") String nocTransId,
	            @RequestParam(value = "value", required = false) String value) {
	        return generalGateInService.getAllNocDtl(companyId, branchId, boeNo,nocTransId,value);
	    }
	    @PostMapping("/saveGateInAndUpdateCfBondNocAndDtl")
		public ResponseEntity<?> saveGateInAndUpdateCfBondNocAndDtl(@RequestParam String cid, @RequestParam String bid,
				@RequestParam String user, @RequestParam String flag, @RequestBody Map<String, Object> requestBody) {

			// Call the saveData method from the service
			return generalGateInService.saveDataOfGateIn(cid, bid, user, flag, requestBody);
		}
	    
	    
	    
	    
	    
	    @GetMapping("/searchCragoGateIn")
		public List<GeneralGateIn> searchCragoGateIn(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam(name = "search", required = false) String search) {
			return generalGateInService.findAllCargoGateIn(companyId, branchId, search);
		}
	    
	    
	    
	    
	    
	    @GetMapping("/getDataBYGateInId")
		public List<GeneralGateIn> getDataBYGateInId(@RequestParam("companyId") String companyId,
				@RequestParam("branchId") String branchId, @RequestParam("gateInId") String gateInId) {
			return generalGateInService.getDataByGateInId(companyId, branchId, gateInId);
		}

}
