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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Party;
import com.cwms.service.DMRService;
import com.cwms.service.ExportReportsServiceReports;
import com.cwms.service.FinanceReportServices;
import com.lowagie.text.DocumentException;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dmrReport")
public class DMRController {

	@Autowired
	private DMRService dmrService;
	
	@GetMapping("/searchAccountHolder")
	public List<Party> getAllAccountHolder(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam(name = "lin", required = false) String lin,
			@RequestParam(name = "agt", required = false) String agt,
			@RequestParam(name = "cha", required = false) String cha,
			
			@RequestParam(name = "partyName", required = false) String partyName)
			{
		return dmrService.getAllAccountHolder(companyId, branchId,lin,agt,cha,partyName);
	}
	
	@GetMapping("/dmrReportProfitCenterWise")
	public ResponseEntity<byte[]> generateExcelDoNoHistory(
	        @RequestParam(name = "companyId") String companyId,
	        @RequestParam(name = "branchId") String branchId,
	        @RequestParam(name = "uname") String username,
	        @RequestParam(name = "type",required = false) String type,
	        @RequestParam(name = "cname") String companyname,
	        @RequestParam(name = "bname") String branchname,
	        @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        @RequestParam(name = "accountHolderName", required = false) String accountHolderName,
	        @RequestParam(name = "accountHolder", required = false) String accountHolder,
	        @RequestParam(name = "profitcenter", required = false) String profitcenter)
	        throws DocumentException, ParseException {

	    byte[] excelBytes;
	    String fileName;

	    System.out.println("exporterName____________________________"+companyname);
	    // Determine which report to generate
	    switch (profitcenter) {
	     
	    // Export
	        case "N00004":
	            excelBytes = dmrService.createExcelReportOfExportDmrReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate,accountHolderName,accountHolder, profitcenter);
	            fileName = "Invoice Sales Register Report.xlsx";
	            break;

	            
	        case "N00002":
	            excelBytes = dmrService.createExcelReportOfImportDmrReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate,accountHolderName,accountHolder, profitcenter);
	            fileName = "Invoice Register Container Wise Report.xlsx";
	            break;
 default:
	 
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body(("Unsupported report type: ").getBytes());
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
}
