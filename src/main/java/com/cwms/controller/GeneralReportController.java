package com.cwms.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.service.GeneralReportsService;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/generalReports")
public class GeneralReportController {

	@Autowired
	private GeneralReportsService generalReportsService;
	
	@GetMapping("/getImportGateInContainerDetailedReport")
	public ResponseEntity<byte[]> generateExcelDoNoHistory(
	        @RequestParam(name = "companyId") String companyId,
	        @RequestParam(name = "branchId") String branchId,
	        @RequestParam(name = "uname") String username,
	        @RequestParam(name = "type",required = false) String type,
	        @RequestParam(name = "cname") String companyname,
	        @RequestParam(name = "bname") String branchname,
	        @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        @RequestParam(name = "accountHolder", required = false) String accountHolder,
	        @RequestParam(name = "cha", required = false) String cha,
	        @RequestParam(name = "selectedReport",required = true) String selectedReport)
	        throws DocumentException, ParseException {

	    byte[] excelBytes;
	    String fileName;

	    // Determine which report to generate
	    switch (selectedReport) {
	    
	        case "General Receiving Register":
	            excelBytes = generalReportsService.createGeneralReceivingDetailsRegister(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate,accountHolder, cha, selectedReport);
	            fileName = "General Receiving Register.xlsx";
	            break;
	            
	        case "General Delivery Register":
	            excelBytes = generalReportsService.createGeneralDeliveryRegisterReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, accountHolder, cha, selectedReport);
	            fileName = "General Delivery Register.xlsx";
	            break;
	            
	        case "General Stock Report":
	            excelBytes = generalReportsService.createGeneralStockReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate,accountHolder, cha, selectedReport);
	            fileName = "General Stock Report.xlsx";
	            break;
	            
	            
	        default:
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(("Unsupported report type: " + selectedReport).getBytes());
	    }

	    // Set response headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", fileName);

	    // Return the response
	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(excelBytes);
	}

	
	 @GetMapping("/show")
	    public ResponseEntity<List<Object[]>> showInventoryData(
	    		 @RequestParam(name = "companyId") String companyId,
			       @RequestParam(name = "branchId") String branchId, 
			       @RequestParam(name = "uname") String username,
			       @RequestParam(name = "type") String type, 
			       @RequestParam(name = "cname") String companyname,
			       @RequestParam(name = "bname") String branchname, 
			       @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			       @RequestParam(name = "endDate" ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
			       @RequestParam(name="accountHolder",required = false) String accountHolder,
			       @RequestParam(name="cha",required = false) String cha,
			       @RequestParam (name = "selectedReport")  String selectedReport) 
			    		   throws DocumentException, ParseException {
	        return generalReportsService.getDataOfImportContainerDetailsReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate,accountHolder,cha,selectedReport);
	    }
}
