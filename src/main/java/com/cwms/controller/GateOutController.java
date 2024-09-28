package com.cwms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.GateOut;
import com.cwms.service.GateOutService;

@RestController
@RequestMapping("/api/gateOutController")
@CrossOrigin("*")
public class GateOutController {

	@Autowired
	public GateOutService gateOutService;
	
	@PostMapping("/saveDataOfExbondGateOut")
    public ResponseEntity<?> saveDataOfExbondGateOut(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return gateOutService.saveDataOfExbondGateOut(companyId, branchId, user, flag, requestBody);
    }
	
	
	   @GetMapping("/findDataOfGateOutDetails")
		public ResponseEntity<List<GateOut>> getCfbondnocData(@RequestParam String companyId,
				@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
			List<GateOut> data = gateOutService.findDataOfGateOutDetails(companyId, branchId, partyName);
			return ResponseEntity.ok(data);
		}
	   

		  @GetMapping("/list")
		    public ResponseEntity<List<GateOut>> getAllListOfGatePass(
		            @RequestParam("companyId") String companyId,
		            @RequestParam("branchId") String branchId,
		            @RequestParam("gateOutId") String gateOutId,
		            @RequestParam("exBondBeNo") String exBondBeNo) {
		        List<GateOut> gatePasses = gateOutService.getAllListOfGateOut(companyId, branchId, gateOutId, exBondBeNo);
		        return ResponseEntity.ok(gatePasses);
		    }

		  
		  @GetMapping("/getDataOfGateOutDetails")
		    public ResponseEntity<List<GateOut>> getDataOfGateOutDetails(@RequestParam ("companyId") String companyId ,@RequestParam ("branchId") String branchId,@RequestParam ("gateOutId") String gateOutId,@RequestParam (name = "vehicleNo",required = false) String vehicleNo) {
				List<GateOut> getData = gateOutService.getDataOfGateOutDetails(companyId,branchId,gateOutId,vehicleNo);
		    	return new ResponseEntity<>(getData,HttpStatus.OK);
			}
		  
}