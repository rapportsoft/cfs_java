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

import com.cwms.service.ExportReportsServiceReports;
import com.cwms.service.FinanceReportServices;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/financeReports")
@CrossOrigin("*")
public class FinanceReportsController {

	@Autowired
	private FinanceReportServices financeReportServices;
	
	@Autowired
	private ExportReportsServiceReports exportReportService;
	
	@GetMapping("/getFinanceReports")
	public ResponseEntity<byte[]> generateExcelDoNoHistory(
	        @RequestParam(name = "companyId") String companyId,
	        @RequestParam(name = "branchId") String branchId,
	        @RequestParam(name = "uname") String username,
	        @RequestParam(name = "type",required = false) String type,
	        @RequestParam(name = "cname") String companyname,
	        @RequestParam(name = "bname") String branchname,
	        @RequestParam(name = "startDate",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        @RequestParam(name = "sbNo", required = false) String sbNo,
	        @RequestParam(name = "bookingNo", required = false) String bookingNo,
	        @RequestParam(name = "billParty", required = false) String billParty,
	        @RequestParam(name = "accountHolder", required = false) String accountHolder,
	        @RequestParam(name = "cha", required = false) String cha,
	        @RequestParam(name = "selectedReport",required = true) String selectedReport)
	        throws DocumentException, ParseException {

	    byte[] excelBytes;
	    String fileName;

	    System.out.println("exporterName____________________________"+companyname);
	    // Determine which report to generate
	    switch (selectedReport) {
	    
	        case "Invoice Sales Register Report":
	            excelBytes = financeReportServices.createExcelReportOfInvoiceSalesRegisterReportReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Invoice Sales Register Report.xlsx";
	            break;

	            
	        case "Invoice Register Container Wise Report":
	            excelBytes = financeReportServices.createExcelReportOfInvoiceSalesRegisterContainerWiseReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Invoice Register Container Wise Report.xlsx";
	            break;

	            
	        case "Credit Note Report":
	            excelBytes = financeReportServices.createExcelReportOFCreditNoteReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Credit Note Report.xlsx";
	            break;
	            
	        case "Import Monthly Revenue Report":
	            excelBytes = financeReportServices.createExcelReportOfImportMonthlyRevenueReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Import Monthly Revenue Report.xlsx";
	            break;

	        case "Export Monthly Revenue Report":
	            excelBytes = financeReportServices.createExcelReportOfExportMonthlyRevenueReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Monthly Revenue Report.xlsx";
	            break;
//
	        // Add remaining cases
	        case "TDS Report":
	        	 excelBytes = financeReportServices.createExcelReportOfTDSReportReport(
		                    companyId, branchId, username, type, companyname, branchname,
		                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
		            fileName = "TDS Report.xlsx";
		            break;

	        case "Daily Receipt Report":
	            excelBytes = financeReportServices.createExcelReportOfDailyReceiptReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Daily Receipt Report.xlsx";
	            break;


	        case "Export SB Wise Container Out Report":
	            excelBytes = exportReportService.createExcelReportOfExportContainerOutBasedReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export SB Wise Container Out Report.xlsx";
	            break;


//
	        case "Export Stuffing Equipments Report":
	            excelBytes = exportReportService.createExcelReportOfExportStuffingEquipmentsReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Stuffing Equipments Report.xlsx";
	            break;
//
	       
//
	        case "Export Carting Report":
	        	excelBytes = exportReportService.createExcelReportOfExportCartingReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Carting Report.xlsx";
	            break;

	        case "Empty Container Report":
	            excelBytes = exportReportService.createExcelReportOfEmptyContainerReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Empty Container Report.xlsx";
	            break;
	            
	     
	        case "Export Container Gate Out Report":
	            excelBytes = exportReportService.createExcelReportOfYardBalanceReportDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Container Gate Out Report.xlsx";
	            break;

	        case "Export Container Reworking":
	            excelBytes = exportReportService.createExcelReportOfExportContainerReworkingDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Container Reworking.xlsx";
	            break;
	            
	        case "ExportEmptyInOutReport":
	            excelBytes = exportReportService.createExcelReportOfExportInOutContainersDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "ExportEmptyInOutReport.xlsx";
	            break;
	            
	        case "ExportLoadedBalanceReport":
	            excelBytes = exportReportService.createExcelReportOfExportEmptyLoadedContainersDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "ExportLoadedBalanceReport.xlsx";
	            break;
	            
	        case "Export Container Stuffing":
	            excelBytes = exportReportService.createExcelReportOfExportContainerStuffingDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Container Stuffing.xlsx";
	            break;
	            
	        case "Export Container ONWH/Buffer Stuffing":
	            excelBytes = exportReportService.createExcelReportOfExportContainerONWHBufferStuffingDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Export Container ONWH/Buffer Stuffing.xlsx";
	            break;
	           
	            
	            
	            
	            
	            
	            
	            
	            
	        case "EXPORT EMPTY IN":
	            excelBytes = exportReportService.createExcelReportExportEmptyGateInInExportSection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "Empty Container Report.xlsx";
	            break;

	            
	            
	        case "BUFFER GATE IN":
	            excelBytes = exportReportService.createExcelReportExportBufferGateInInExportSection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "BUFFER GATE IN.xlsx";
	            break;
	            
	        case "EXPORT FCL":
	            excelBytes = exportReportService.createExcelReportExportFCLGateInInExportSection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "EXPORT FCL.xlsx";
	            break;
	            
	        case "EXP MOVEMENT OUT":
	            excelBytes = exportReportService.createExcelReportExportMovementOutInExportSection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "EXP MOVEMENT OUT.xlsx";
	            break;
	            
	        case "EXP LDD PENDENCY":
	            excelBytes = exportReportService.createExportLDDPendencyReportExcle(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "EXP LDD PENDENCY.xlsx";
	            break;
	            
	        case "STUFFING TALLY":
	            excelBytes = exportReportService.createExportStuffingTallyReportExcle(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, sbNo, bookingNo, billParty, accountHolder, cha, selectedReport);
	            fileName = "STUFFING TALLY.xlsx";
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
		       @RequestParam(name="sbNo",required = false) String sbNo,
		       @RequestParam(name="bookingNo",required = false) String bookingNo,
		       @RequestParam(name="billParty",required = false) String billParty,
		       @RequestParam(name="accountHolder",required = false) String accountHolder,
		       @RequestParam(name="cha",required = false) String cha,
		       @RequestParam (name = "selectedReport")  String selectedReport) 
		    		   throws DocumentException, ParseException {
        return financeReportServices.getDataOfImportContainerDetailsReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate,sbNo,bookingNo,billParty,accountHolder,cha,selectedReport);
    }
}
