package com.cwms.controller;

import java.util.List;

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

import com.cwms.repository.ServiceMappingRepo;
import com.cwms.entities.CFIgm;
import com.cwms.entities.Party;
import com.cwms.entities.ServiceMapping;

@RestController
@CrossOrigin("*")
@RequestMapping("/servicemapping")
public class ServiceMappingController {
	
	@Autowired
	ServiceMappingRepo servicemappingrepo;
	
	@GetMapping("/mappingdata")
	public List<ServiceMapping> getMappingData(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("profitcenterId")String profitcentreId,@RequestParam("invoiceType")String invoiceType,
			@RequestParam("containerSize") String containerSize ,@RequestParam("gateOutType") String gateOutType,
			@RequestParam("typeOfConatiner") String typeOfContainer) {
		
		return servicemappingrepo.searchMapping(companyId,branchId,profitcentreId,invoiceType,
				containerSize,gateOutType,typeOfContainer);
		
		
	}
	
	@PostMapping("/saveServiceData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestBody  ServiceMapping servicemapping) {
		
		if(servicemapping != null) {
			
			
			
			Boolean isExist = servicemappingrepo.isDataExist(cid,bid,servicemapping.getServiceId(),servicemapping.getProfitcentreId(),
					servicemapping.getContainerSize(),servicemapping.getContainerType(),servicemapping.getInvoiceType(),servicemapping.getGateOutType(),
					servicemapping.getScannerType(),servicemapping.getTypeOfContainer());

			if (isExist) {
				return new ResponseEntity<>("Duplicate Service Mapping Data", HttpStatus.BAD_REQUEST);
			}
			
			servicemapping.setCompanyId(cid);
			servicemapping.setBranchId(bid);
			servicemapping.setStatus("A");
			ServiceMapping serviceMapping = servicemappingrepo.save(servicemapping);
			
			return new ResponseEntity<>(serviceMapping, HttpStatus.OK);
			
		}
		
		else{
			return new ResponseEntity<>("Service Mapping Data Not Found", HttpStatus.BAD_REQUEST);
		}
		
	
	}
	
	@PostMapping("/deleteServiceData")
	public String deleteServiceData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestBody  ServiceMapping servicemapping) {
		
		
		int cnt = servicemappingrepo.deleteServiceData(cid,bid,servicemapping.getServiceId(),servicemapping.getProfitcentreId(),
				 servicemapping.getContainerSize(),servicemapping.getContainerType(),servicemapping.getInvoiceType(),
				 servicemapping.getGateOutType(),servicemapping.getScannerType(),servicemapping.getTypeOfContainer());
		
		if(cnt>0)
			return "success";
		else
			return "failure";
				
				
		
		
	}
	
	
	
	

}
