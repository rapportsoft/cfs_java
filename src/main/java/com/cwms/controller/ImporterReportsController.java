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

import com.cwms.entities.Cfinbondcrg;
import com.cwms.service.ImportReportsService;
import com.lowagie.text.DocumentException;

@RestController
@CrossOrigin
@RequestMapping("/api/importReports/")
public class ImporterReportsController {

	@Autowired 
	private ImportReportsService importReporsService;
	
//	@GetMapping("/getImportGateInContainerDetailedReport")
//	public ResponseEntity<byte[]> generateExcelDoNoHistory( @RequestParam(name = "companyId") String companyId,
//		       @RequestParam(name = "branchId") String branchId, 
//		       @RequestParam(name = "uname") String username,
//		       @RequestParam(name = "type") String type, 
//		       @RequestParam(name = "cname") String companyname,
//		       @RequestParam(name = "bname") String branchname, 
//		       @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
//		       @RequestParam(name = "endDate" ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
//		       @RequestParam(name="igmNo",required = false) String igmNo,
//		       @RequestParam(name="itemNo",required = false) String itemNo,
//		       @RequestParam(name="shippingLine",required = false) String shippingLine,
//		       @RequestParam(name="accountHolder",required = false) String accountHolder,
//		       @RequestParam(name="cha",required = false) String cha,
//		       @RequestParam (name = "selectedReport")  String selectedReport )  
//		    		   throws DocumentException, ParseException {
//	
//		if("Import GateIn Container detailed Report".equals(selectedReport))
//		{
//			
//		}
//
//		byte[] excelBytes = importReporsService.createExcelReportOfImportGateInContainerDetailedReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate,igmNo,itemNo,shippingLine,accountHolder,cha,selectedReport);
//		
//		String fileName = "Import GateIn Container detailed Report.xlsx";
//		HttpHeaders headers = new HttpHeaders();
//		
//		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		headers.setContentDispositionFormData("attachment", fileName);
//
//		return ResponseEntity.ok().headers(headers).body(excelBytes);
//	}
	
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
	        @RequestParam(name = "igmNo", required = false) String igmNo,
	        @RequestParam(name = "itemNo", required = false) String itemNo,
	        @RequestParam(name = "shippingLine", required = false) String shippingLine,
	        @RequestParam(name = "accountHolder", required = false) String accountHolder,
	        @RequestParam(name = "cha", required = false) String cha,
	        @RequestParam(name = "selectedReport",required = true) String selectedReport)
	        throws DocumentException, ParseException {

	    byte[] excelBytes;
	    String fileName;

	    // Determine which report to generate
	    switch (selectedReport) {
	    
	        case "Import GateIn Container detailed Report":
	            excelBytes = importReporsService.createExcelReportOfImportGateInContainerDetailedReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import GateIn Container detailed Report.xlsx";
	            break;
	            
	        case "IMPORT GATE IN":
	            excelBytes = importReporsService.createExcelReportOfImportGateInContainerDetailedReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import GateIn Container detailed Report.xlsx";
	            break;

	        case "Import GateOut Container detailed Report":
	            excelBytes = importReporsService.createExcelReportOfImportGateOutContainerDetailedReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import GateOut Container detailed Report.xlsx";
	            break;
	            
	        case "Hold Release Report":
	            excelBytes = importReporsService.createExcelReportOfHoldReleasedReportDetailedReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Hold Release Report.xlsx";
	            break;


	        case "Import FGP Report":
	            excelBytes = importReporsService.createExcelReportOfImportFGPReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import FGP Report.xlsx";
	            break;

	        case "Import Long Standing Report":
	            excelBytes = importReporsService.createExcelReportOfImportLongStandingReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import Long Standing Report.xlsx";
	            break;

	        // Add remaining cases
	        case "Import LCL Cargo Delivery Report":
	            excelBytes = importReporsService.createExcelReportOfImportLclCargoDeliveryReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import LCL Cargo Delivery Report.xlsx";
	            break;

	        case "Import FCL Destuff Balance Report":
	            excelBytes = importReporsService.createExcelReportOfImportFclDestuffBalanceReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import FCL Destuff Balance Report.xlsx";
	            break;

	        case "LCL Cargo Balance Inventory Report":
	            excelBytes = importReporsService.createExcelReportOfLclCargoBalanceInventoryReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "LCL Cargo Balance Inventory Report.xlsx";
	            break;

	        case "Import Destuff Equipment Report":
	            excelBytes = importReporsService.createExcelReportOfImportDestuffEquipmentReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import Destuff Equipment Report.xlsx";
	            break;

	        case "Import FCL Custom Tally Sheet Report":
	            excelBytes = importReporsService.createExcelReportOfImportFclCustomTallySheetReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import FCL Custom Tally Sheet Report.xlsx";
	            break;
	            
	       
	            	
	        case "Import Manual GateIn Report":
	            excelBytes = importReporsService.createExcelReportOfImportManualGateInReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import Manual GateIn Report.xlsx";
	            break;

	        case "Loaded To Distuff Empty Inventory":
	            excelBytes = importReporsService.createExcelReportOfLoadedToDistuffEmptyInventoryReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Loaded To Distuff Empty Inventory.xlsx";
	            break;

	        case "Scan Container Report":
	            excelBytes = importReporsService.createExcelReportOfScanContainerReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Scan Container Report.xlsx";
	            break;

	        case "SealCutting Report":
	            excelBytes = importReporsService.createExcelReportOfSealCuttingReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "SealCutting Report.xlsx";
	            break;
	            
	        case "SEAL CUTTING":
	            excelBytes = importReporsService.createExcelReportOfSealCuttingReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "SealCutting Report.xlsx";
	            break;

	        case "WEIGHMENT REPORT":
	            excelBytes = importReporsService.createExcelReportOfWeighmentReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "WEIGHMENT REPORT.xlsx";
	            break;

	        case "TRANSPORTER WISE TUES REPORT":
	            excelBytes = importReporsService.createExcelReportOfTransporterWiseTuesReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "TRANSPORTER WISE TUES REPORT.xlsx";
	            break;

	        case "LCL Zero Payment":
	            excelBytes = importReporsService.createExcelReportOfLclZeroPaymentReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "LCL Zero Payment.xlsx";
	            break;

	        case "Import LCL Cargo Destuff Report":
	            excelBytes = importReporsService.createExcelReportOfImportLclCargoDestuffReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import LCL Cargo Destuff Report.xlsx";
	            break;
	            
//	        case "LCL DESTUFF":
//	            excelBytes = importReporsService.createExcelReportOfImportLclCargoDestuffReport(
//	                    companyId, branchId, username, type, companyname, branchname,
//	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
//	            fileName = "Import LCL Cargo Destuff Report.xlsx";
//	            break;

	        case "Yard Balance Report Details":
	            excelBytes = importReporsService.createExcelReportOfYardBalanceReportDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Yard Balance Report Details.xlsx";
	            break;

	        case "Loaded To Destuff Empty Out Report Details":
	            excelBytes = importReporsService.createExcelReportOfLoadedToDestuffEmptyOutReportDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Loaded To Destuff Empty Out Report Details.xlsx";
	            break;
	            
	        case "Import BOE Wise Gate Out Report":
	            excelBytes = importReporsService.createExcelReportOfImportBOEWiseGateOutReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import BOE Wise Gate Out Report.xlsx";
	            break;

	            
	        case "IMPORT CUSTOM EXAM":
	            excelBytes = importReporsService.createExcelReportOfImportCustomExamReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Import FCL Custom Tally Sheet Report.xlsx";
	            break;
	            
	        case "JOB ORDER":
	            excelBytes = importReporsService.createExcelReportOfIJobOrderReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "JOB ORDER.xlsx";
	            break;
	            
	        case "Export Carting Report":
	        	excelBytes = importReporsService.createExcelReportOfExportCartingReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Export Carting Report.xlsx";
	            break;

	        case "ExportEmptyInOutReport":
	            excelBytes = importReporsService.createExcelReportOfExportInOutContainersDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "ExportEmptyInOutReport.xlsx";
	            break;
	            
	        case "Import Empty Inventory":
	            excelBytes = importReporsService.createExcelReportOfImportEmptyInventoryDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "ExportEmptyInOutReport.xlsx";
	            break;
	            
	        case "Export Empty Inventory":
	            excelBytes = importReporsService.createExcelReportOfExportEmptyInventoryDetails(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "ExportEmptyInOutReport.xlsx";
	            break;
	            
	           
	            
	            
	        case "Total":
	            excelBytes = importReporsService.createExcelReportOfLCLLoadedInventoryTotalReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Total.xlsx";
	            break;
	            
	            
	        case "Hazardous":
	            excelBytes = importReporsService.createExcelReportOfLCLLoadedInventoryHazardousReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Hazardous.xlsx";
	            break;
	            
	        case "Reefer":
	            excelBytes = importReporsService.createExcelReportOfLCLLoadedInventoryReeferConReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Hazardous.xlsx";
	            break;
	            
	        case "ODC":
	            excelBytes = importReporsService.createExcelReportOfLCLLoadedInventoryODCConReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "ODC.xlsx";
	            break;
	            
	        case "General":
	            excelBytes = importReporsService.createExcelReportOfLCLLoadedInventoryGeneralConReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "General.xlsx";
	            break;
	            
	        case "Manual":
	            excelBytes = importReporsService.createExcelReportOfManualReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "Manual.xlsx";
	            break;
	            
	        case "FCL LOADED BALANCE":
	            excelBytes = importReporsService.createExcelReportOfLongStandingNewReport(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "FCL LOADED BALANCE.xlsx";
	            break;
	            
	        case "LOADING JO":
	            excelBytes = importReporsService.createExcelReportOfLoadingJoReportInDeliverySection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "LOADING JO.xlsx";
	            break;
	            
	        case "FCL DESTUFF":
	            excelBytes = importReporsService.createExcelReportOfFCLDestuffInDeliverySection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "FCL DESTUFF.xlsx";
	            break;
	            
	        case "FCL LOADED":
	            excelBytes = importReporsService.createExcelReportOfFCLLoadedInDeliverySection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "FCL LOADED.xlsx";
	            break;
	            
	        case "LCL DESTUFF":
	            excelBytes = importReporsService.createExcelReportOfLCLDestuffInDeliverySection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "FCL LOADED.xlsx";
	            break;
	            
	        case "EMPTY OUT":
	            excelBytes = importReporsService.createExcelReportOfEMPTYOUTInDeliverySection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "EMPTY OUT.xlsx";
	            break;
	            
	        case "LCL DELIVERED":
	            excelBytes = importReporsService.createExcelReportOfLCLCargoDeliviredDeliverySection(
	                    companyId, branchId, username, type, companyname, branchname,
	                    startDate, endDate, igmNo, itemNo, shippingLine, accountHolder, cha, selectedReport);
	            fileName = "LCL DELIVERED.xlsx";
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
			       @RequestParam(name="igmNo",required = false) String igmNo,
			       @RequestParam(name="itemNo",required = false) String itemNo,
			       @RequestParam(name="shippingLine",required = false) String shippingLine,
			       @RequestParam(name="accountHolder",required = false) String accountHolder,
			       @RequestParam(name="cha",required = false) String cha,
			       @RequestParam (name = "selectedReport")  String selectedReport) 
			    		   throws DocumentException, ParseException {
	        return importReporsService.getDataOfImportContainerDetailsReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate,igmNo,itemNo,shippingLine,accountHolder,cha,selectedReport);
	    }
}
