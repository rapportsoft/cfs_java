package com.cwms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.Voyage;
import com.cwms.service.ExportStuffRequestService;

@RestController
@CrossOrigin("*")
@RequestMapping("/ExportStuffing")
public class ExportStuffRequestController {
	
	@Autowired
	private ExportStuffRequestService stuffService;
	
	
	@GetMapping("/searchContainerNoForStuffingContainerWise")
	public ResponseEntity<?> searchContainerNoForStuffingContainerWise(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("containerNo") String containerNo, @RequestParam("type") String type, @RequestParam("profitcentreId") String profitcentreId) {
		return stuffService.searchContainerNoForStuffingConteinerWise(companyId, branchId, containerNo, type, profitcentreId);
	}
	
	
	@GetMapping("/searchSbNoForStuffing")
	public ResponseEntity<?> searchSbNoForStuffing(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("searchValue") String searchValue, @RequestParam("profitcentreId") String profitcentreId, @RequestParam(value = "stuffReqId", required = false) String stuffReqId){
		return stuffService.searchSbNoForStuffing(companyId, branchId, searchValue, profitcentreId, stuffReqId);
	}
	
//	@GetMapping("/searchContainerNoForStuffingContainerWise")
//	public ResponseEntity<?> searchContainerNoForStuffingContainerWise(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
//			@RequestParam("searchValue") String searchValue, @RequestParam("profitcentreId") String profitcentreId){
//		return stuffService.searchContainerNoForStuffingContainerWise(companyId, branchId, searchValue, profitcentreId);
//	}
	
	
	
	
	@GetMapping("/searchContainerNoForStuffing")
	public ResponseEntity<?> searchContainerNoForStuffing(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestParam("sbNo") String sbNo, @RequestParam("type") String type) {
		return stuffService.searchContainerNoForStuffing(companyId, branchId, sbNo, type);
	}
	
	
	
	
	
	@PostMapping("/saveExportStuffRequest")
	public ResponseEntity<?> saveExportStuffRequest(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<ExportStuffRequest> exportStuffRequest,
			@RequestParam("userId") String User) {
		ResponseEntity<?> saveExportStuffRequest = stuffService.saveExportStuffRequest(companyId, branchId, exportStuffRequest, User);
		return saveExportStuffRequest;
	}
	
	@PostMapping("/saveExportStuffRequestContainer")
	public ResponseEntity<?> saveExportStuffRequestContainer(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<ExportStuffRequest> exportStuffRequest,
			@RequestParam("userId") String User) {
		ResponseEntity<?> saveExportStuffRequest = stuffService.saveExportStuffRequestContainer(companyId, branchId, exportStuffRequest, User);
		return saveExportStuffRequest;
	}

	
	

}