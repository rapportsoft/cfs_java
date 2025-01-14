package com.cwms.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.service.CommonReportsService;
import com.lowagie.text.DocumentException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/commonReports")
public class CommonReportsController {

	@Autowired
	private CommonReportsService commonReportService;
	
	@GetMapping("/getJoGateIn")
	public ResponseEntity<Map<String, Map<String, Object>>> getJoGateInReport(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  commonReportService.getDataForMovementSummeryReport(companyId, branchId, startDate, endDate);
		
	}
	
	@GetMapping("/getLoadedInventoryReport")
	public ResponseEntity<Map<String, Map<String, Object>>> getInventoryLoadedReport(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  commonReportService.getDataForLoadedInventoryReport(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	
	
	
	@GetMapping("/getMTYData")
	public ResponseEntity<Map<String, Map<String, Object>>> getMTYData(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  commonReportService.getMTYData(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	@GetMapping("/getDataForDeliveryReport")
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForDeliveryReport(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  commonReportService.getDataForDeliveryReport(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	@GetMapping("/getDataForExportSection")
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForExportSection(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  commonReportService.getDataForExportSection(companyId, branchId, startDate, endDate);
		
	}
	
	
	

	
	@GetMapping("/getPortWisePendency")
	public ResponseEntity<Map<String, Map<String, Object>>> getPortWisePendency(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId,
			 @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
		        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
			
			) {
		
		
		return  commonReportService.getDataForPortWisePendency(companyId, branchId, startDate, endDate);
		
	}
	
	
	
	
	
	@GetMapping("/getPortWisePendencyReport")
	public ResponseEntity<byte[]> generateExcelDoNoHistory(
	        @RequestParam(name = "companyId") String companyId,
	        @RequestParam(name = "branchId") String branchId,
	        @RequestParam(name = "uname") String username,
	        @RequestParam(name = "type",required = false) String type,
	        @RequestParam(name = "cname") String companyname,
	        @RequestParam(name = "bname") String branchname,
	        @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        @RequestParam(name = "igmNo", required = false) String igmNo,
	        @RequestParam(name = "itemNo", required = false) String itemNo,
	        @RequestParam(name = "shippingLine", required = false) String shippingLine,
	        @RequestParam(name = "accountHolder", required = false) String accountHolder,
	        @RequestParam(name = "cha", required = false) String cha,
	        @RequestParam(name = "selectedReport",required = true) String selectedReport)
	        throws DocumentException, ParseException {




	    	    byte[] excelBytes = commonReportService.createExcelReportOfPortWisePendency(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	    	    String  fileName = "Port Wise Pendency Report.xlsx";

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", fileName);

	    // Return the response
	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(excelBytes);
	}
	
//	@GetMapping("/getJoGateIn")
//	public ResponseEntity<Map<String, ContainerCountDTO>> getJoGateInReport(
//	        @RequestParam("companyId") String companyId,
//	        @RequestParam("branchId") String branchId,
//	        @RequestParam(name = "startDate", required = false) 
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
//	        @RequestParam(name = "endDate") 
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {
//
//	    // Call the service method to fetch the report data
//	    return commonReportService.getDataForMovementSummeryReport(companyId, branchId, startDate, endDate);
//	}

	
}
