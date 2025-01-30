package com.cwms.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.service.CommonReportsService;
import com.cwms.service.DashBoardService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dashboard")
public class DashBoardController {

	@Autowired
	private DashBoardService dashBoardService;
	
	@GetMapping("/getDashBoardImportData")
	public ResponseEntity<Map<String, Map<String, Object>>> getJoGateInReport(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  dashBoardService.getDataForMovementSummeryReport(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	
	@GetMapping("/getDashBoardImport")
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForImportDashBoard(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  dashBoardService.getDataForImportDashBoard(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	@GetMapping("/getDashBoardExport")
	public ResponseEntity<Map<String, Map<String, Object>>> getDashBoardExport(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  dashBoardService.getDataForExportDashBoard(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	@GetMapping("/getExportBarChart")
	public ResponseEntity<Map<String, Map<String, Object>>> getInventoryLoadedReport(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  dashBoardService.getDataForExportInventoryBarChart(companyId, branchId, startDate, endDate);
		
	}
}
