package com.cwms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Port;
import com.cwms.service.PortService;

@RequestMapping("/port")
@CrossOrigin("*")
@RestController
public class PortController {
	@Autowired
	private PortService portService;

	@GetMapping("/searchPorts")
	public ResponseEntity<?> searchPorts(@RequestParam(name = "companyId", required = false) String companyId,
			@RequestParam(name = "branchId", required = false) String branchId,
			@RequestParam(name = "portName", required = false) String portName,
			@RequestParam(name = "portType", required = false) String portType,
			@RequestParam(name = "portCode", required = false) String portCode) {
		
		List<Port> searchPorts = portService.searchPorts(companyId, branchId, portCode, portType, portName);
		return ResponseEntity.ok(searchPorts);
	}
	
	
	@GetMapping("/getSinglePort")
	public ResponseEntity<?> getSinglePort(@RequestParam(name = "companyId", required = false) String companyId,
			@RequestParam(name = "branchId", required = false) String branchId,
			@RequestParam(name = "portCode", required = false) String portCode,
			@RequestParam(name = "portTransId", required = false) String portTransId) {
		
		Port searchPorts = portService.getSinglePort(companyId, branchId, portCode, portTransId);
		return ResponseEntity.ok(searchPorts);
	}
	
	
	@GetMapping("/getSelectTags")
	public ResponseEntity<?> getSelectTags(@RequestParam(name = "companyId", required = false) String companyId,
			@RequestParam(name = "branchId", required = false) String branchId) {

		List<Port> searchPorts = portService.getSelectTags(companyId, branchId);
		return ResponseEntity.ok(searchPorts);
	}
	
	

	@PostMapping("/addPort")
	public ResponseEntity<?> addPorts(@RequestBody Port port,
			@RequestParam("user") String user) {
		
		Map<String, Object> errorResponse = new HashMap<>();		    	
		 
		 boolean existportCode = portService.checkduplicatePortNo(port.getCompanyId(), port.getBranchId(), port.getPortCode());

			if (existportCode) {
				errorResponse.put("error", "Duplicate PortCode");
			    errorResponse.put("field", "portCode"); 
				return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
			}		
			Port addPorts = portService.addPort(port, user);
		
		return ResponseEntity.ok(addPorts);
	}
	
	
	
	@PatchMapping("/updatePort")
	public ResponseEntity<?> updatePort(@RequestBody Port port,
			@RequestParam("user") String user) {			 
		 
			Port updatePort = portService.updatePort(port, user);		
		return ResponseEntity.ok(updatePort);
	}
	
	
	
	@DeleteMapping("/deletePort")
	public ResponseEntity<?> deletePort(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId
			,@RequestParam("portCode") String portCode,@RequestParam("portTransId") String portTransId,
			@RequestParam("user") String user) {			 
		 
			Port updatePort = portService.deletePort(companyId, branchId, portCode, portTransId, user);		
		return ResponseEntity.ok(updatePort);
	}
	@GetMapping("/getPortListToSelect")
	public ResponseEntity<?> getPortListToSelect(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,@RequestParam("jobPrefix") String jobPrefix) {
		 List<Map<String, String>> portList = portService.getPortToSelect(companyId, branchId, jobPrefix); 
	   
	    return ResponseEntity.ok(portList);
	}

}
