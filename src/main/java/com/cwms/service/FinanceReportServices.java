package com.cwms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ExportOperationalReportRepository;
import com.cwms.repository.FinanceReportsRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class FinanceReportServices {

	@Autowired
	private FinanceReportsRepository financeReportsRepo;
	
	@Autowired
	private ExportOperationalReportRepository exportOperationalReportRepo;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	
	public ResponseEntity<List<Object[]>> getDataOfImportContainerDetailsReport(
	        String companyId,
	        String branchId,
	        String username,
	        String type,
	        String companyname,
	        String branchname,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String exporterName,
	        String acc,
	        String cha,
	        String selectedReport
	) throws DocumentException {

	    Calendar cal = Calendar.getInstance();
	    
	    if (startDate != null) {
            cal.setTime(startDate);
            // If the time is at the default 00:00:00, reset it to 00:00:00
            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                startDate = cal.getTime();
            }
        }

        // Check if time component is not set (i.e., time is null or empty)
        if (endDate != null) {
            cal.setTime(endDate);
            // If the time is at the default 23:59:59, reset it to 23:59:59
            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                endDate = cal.getTime();
            }
        }

	    List<Object[]> importDetails;

	    // Determine the repository method to call based on the selected report
	    switch (selectedReport) {
	        case "Invoice Sales Register Report":
	            importDetails = financeReportsRepo.getInvoiceSalesRegisterData(
	                    companyId, branchId,startDate, endDate, exporterName);
	            break;

	        case "Invoice Register Container Wise Report":
	            importDetails = financeReportsRepo.getInvoiceDetailsContainerWise(
	            		 companyId, branchId,startDate, endDate, exporterName);
	            break;
	              
	        case "Credit Note Report":
	            importDetails =  exportOperationalReportRepo.findExportGateOutBasedData(companyId, branchId,startDate, endDate,
		        		sbNo,bookingNo,exporterName,acc,cha);
	            break;
	            
	        case "TDS Report":
	        	 importDetails = financeReportsRepo.findFinanceTransactions(
		                    companyId, branchId,startDate, endDate, exporterName);
		            break;
		        
	        case "Carting Pendency":
	        	importDetails = exportOperationalReportRepo.findCartingPendancyData(companyId, branchId, endDate,
		        		sbNo,bookingNo, exporterName,acc,cha);
	            break;

	        // Add more cases for other reports as needed
	        case "Movement Pendency":
	        	importDetails = exportOperationalReportRepo.getContainerMovements(companyId, branchId, endDate,
		        		sbNo,acc,cha);
	            break;
	            
	        case "Cargo Back To Town":
	        	importDetails = exportOperationalReportRepo.getCargoBackToTownDetails(companyId, branchId,startDate, endDate,
		        		sbNo,acc,cha);
	            break;
	            
	        case "Export Stuffing Equipments Report":
	            importDetails = exportOperationalReportRepo.getEquipmentActivityDetails(companyId, branchId);
	            break;
	            
	        case "TRANSPORTER WISE TEUS REPORT":
	            importDetails = exportOperationalReportRepo.getTransporterWiseTuesReport(
	                    companyId, branchId, startDate, endDate);
	            break;
	            
	        case "Export Container Stuffing":
	        	 importDetails =exportOperationalReportRepo.exportStuffGateInReport(companyId, branchId,startDate, endDate,sbNo,bookingNo,exporterName, acc,cha);
	            break;
	            
	        case "Export Container ONWH/Buffer Stuffing":
	        	 importDetails = exportOperationalReportRepo.findExportContainerONWHBuffer(companyId, branchId,startDate, endDate,sbNo ,bookingNo,exporterName, acc,cha);
		            break;
	            
	        case "ExportEmptyInOutReport":
	        	importDetails = exportOperationalReportRepo.fetchExporGateInAndGateOutDetails(companyId, branchId,startDate, endDate,bookingNo,acc,cha);
	            break;
	            
	        case "Export Carting Report":
	        	 importDetails = exportOperationalReportRepo.findExportCartingData(companyId, branchId, startDate, endDate, sbNo,exporterName, acc, cha);
		            break;
	            
	        case "Empty Container Report":
	            importDetails = exportOperationalReportRepo.findContainerDetails(companyId, branchId, startDate, endDate, sbNo, bookingNo, exporterName, acc, cha);
	            break;
	            
	        case "Export Container Gate Out Report":
	            importDetails = exportOperationalReportRepo.findEmptyGateOutContainerDetails(companyId, branchId,startDate, endDate,sbNo,bookingNo,acc,cha);
	            break;
	            
	        case "Export Container Reworking":
	        	 importDetails = exportOperationalReportRepo.findReworkingContainerDetails(companyId, branchId,startDate, endDate,sbNo,acc);
	            break;
	            
	        case "ExportLoadedBalanceReport":
	        	importDetails = exportOperationalReportRepo.findExportLoadedBalanceReport(companyId, branchId, endDate,sbNo,bookingNo,exporterName,acc,cha);
	    break;
 
	  // Handle unsupported report types
	        default:
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    // Check if the list is empty and return the appropriate response
	    if (importDetails.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
	    }

	    return new ResponseEntity<>(importDetails, HttpStatus.OK); // Records found
	}

	
	
	
	private Double parseDoubleOrDefault(Object value, Double defaultValue) {
	    if (value != null) {
	        try {
	            return Double.parseDouble(value.toString());
	        } catch (NumberFormatException e) {
	            // Log error if necessary
	        }
	    }
	    return defaultValue;
	}

	
	
	
	
	
	
	
	private double safeParseDouble(Object value) {
	    try {
	        return value != null ? Double.parseDouble(value.toString()) : 0;
	    } catch (NumberFormatException e) {
	        // Log the exception if necessary or handle it accordingly
	        return 0; // Return 0 if the value can't be parsed
	    }
	}
	
	public byte[] createExcelReportOfInvoiceSalesRegisterReportReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.getInvoiceSalesRegisterData(companyId, branchId,startDate,endDate,
		        		billParty);
		        Sheet sheet = workbook.createSheet("Invoice Sales Register Report");

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "SR NO",
		        	    "INVOICE DATE",
		        	    "INVOICE DEBIT NOTE NO",
		        	    "INVOICE DEBIT NOTE FLAG",
		        	    "REFERENCE NO",
		        	    "Importer Name",
		        	    "DOCUMENT NO",
		        	    "BILLING PARTY",
		        	    "ADDRESS1",
		        	    "ADDRESS2",
		        	    "ADDRESS3",
		        	    "ADDRESS4",
		        	    "VAT TIN NO",
		        	    "CST NO",
		        	    "GST NO",
		        	    "CHARGE NAME",
		        	    "BILL WISE DETAIL",
		        	    "AMOUNT",
		        	    "CGST",
		        	    "SGST",
		        	    "IGST",
		        	    "CREDIT PERIOD",
		        	    "NARRATION",
		        	    "SAC CODE",
		        	    "REMARK",
		        	    "IRN",
		        	    "FCL/LCL",
		        	    "FOOTER REMARKS"
		        	};



	
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Invoice Sales Register Report" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Invoice Sales Register Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Invoice Sales Register Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "INVOICE DATE":
		                    if (resultData1[0] != null) {
		                        cell.setCellValue(resultData1[0].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "INVOICE DEBIT NOTE NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "INVOICE DEBIT NOTE FLAG":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "REFERENCE NO":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Importer Name":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "DOCUMENT NO":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "BILLING PARTY":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "ADDRESS1":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "ADDRESS2":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "ADDRESS3":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "ADDRESS4":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "VAT TIN NO":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "CST NO":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "GST NO":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "CHARGE NAME":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "BILL WISE DETAIL":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "AMOUNT":
		                    Double amount = parseDoubleOrDefault(resultData1[16], 0.00);
		                    cell.setCellValue(amount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "CGST":
		                    if ("Y".equals(resultData1[17].toString() != null ? resultData1[17].toString() : "")) {
		                        // If the condition is met, divide the amount by 2 and set it as the cell value
		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00) / 2;
		                        cell.setCellValue(amount1);
		                    } else {
		                        // Otherwise, set the CGST value
		                        Double cgst = parseDoubleOrDefault(resultData1[17], 0.00);
		                        cell.setCellValue(cgst);
		                    }
		                    cell.setCellStyle(numberCellStyle);
		                    break;


		                case "SGST":
		                    if ("Y".equals(resultData1[18].toString() != null ? resultData1[18].toString() : "")) {
		                        // If the condition is met, divide the amount by 2 and set it as the cell value
		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00) / 2;
		                        cell.setCellValue(amount1);
		                        cell.setCellStyle(numberCellStyle);
		                    } else {
		                        // Otherwise, set the CGST value
		                        Double SGST = parseDoubleOrDefault(resultData1[18], 0.00);
		                        cell.setCellValue(SGST);
		                        cell.setCellStyle(numberCellStyle);
		                    }
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		               

		                case "IGST":
		                	if ("Y".equals(resultData1[19].toString() != null ? resultData1[19].toString() : "")) {
		                        // If the condition is met, divide the amount by 2 and set it as the cell value
		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00) ;
		                        cell.setCellValue(amount1);
		                        cell.setCellStyle(numberCellStyle);
		                    } else {
		                        // Otherwise, set the CGST value
		                        Double IGST = parseDoubleOrDefault(resultData1[19], 0.00);
		                        cell.setCellValue(IGST);
		                        cell.setCellStyle(numberCellStyle);
		                    }
		                	 cell.setCellStyle(numberCellStyle);
		                    break;

		                case "CREDIT PERIOD":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "NARRATION":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "SAC CODE":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "REMARK":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                case "IRN":
		                    cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                    break;

		                case "FCL/LCL":
		                    cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                    break;

		                case "FOOTER REMARKS":
		                    cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       

		        
		        
		        
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Importer Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Header row for summary
 		    // Define header for Importer-wise summary
 		       String[] columnsHeaderSummary = {
 		           "Sr No", "Importer Name", "Taxable Amount", "CGST", "SGST", "IGST"
 		       };

 		       // Map to store summary data for each importer
 		       Map<String, Double[]> summaryData = new HashMap<>();
 		       double totalAmount = 0;
 		       double totalCgst = 0;
 		       double totalSgst = 0;
 		       double totalIgst = 0;

 		       // Apply header row styling and set values
 		       Row headerRow1 = sheet.createRow(rowNum++);
 		       for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		           Cell cell = headerRow1.createCell(i);
 		           cell.setCellValue(columnsHeaderSummary[i]);

 		           // Set cell style for header
 		           CellStyle headerStyle = workbook.createCellStyle();
 		           headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		           headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		           Font headerFont = workbook.createFont();
 		           headerFont.setBold(true);
 		           headerFont.setFontHeightInPoints((short) 11);
 		           headerFont.setColor(IndexedColors.WHITE.getIndex());
 		           headerStyle.setFont(headerFont);

 		           headerStyle.setBorderBottom(BorderStyle.THIN);
 		           headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderTop(BorderStyle.THIN);
 		           headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderLeft(BorderStyle.THIN);
 		           headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderRight(BorderStyle.THIN);
 		           headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		           headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		           headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		           cell.setCellStyle(headerStyle);
 		           int headerWidth = (int) (columnsHeaderSummary[i].length() * 360);
 		           sheet.setColumnWidth(i, headerWidth);
 		       }

 		       // Iterate over importDetails and sum up the required fields
 		       
// 		      double amount = 0.0; 
		         double cgst = 0.0; 
		         double sgst =0.0; 
		         double igst = 0.0; 
		         
 		       int srNo = 1;
 		       for (Object[] i : importDetails) {
 		           String importerName = i[4] != null ? i[4].toString() : "Unknown"; // Importer Name at index 4
 		           double amount = i[16] != null ? Double.parseDouble(i[16].toString()) : 0; // Taxable Amount at index 16
 		          if("Y".equals(i[17].toString())) {
 		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 cgst = amount1;
 		          }
 		          
 		         if("Y".equals(i[18].toString())) {
		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 sgst = amount1;
 		          }
 		         
 		        if("Y".equals(i[19].toString())) {
		        	  
		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) ;
		        	 igst = amount1;
		          }
 		           
 		          
 	
 		           // Initialize summary data if not already present
 		           if (!summaryData.containsKey(importerName)) {
 		               summaryData.put(importerName, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		           }

 		           // Accumulate values for each importer
 		           Double[] data = summaryData.get(importerName);
 		           data[0] += amount;
 		           data[1] += cgst;
 		           data[2] += sgst;
 		           data[3] += igst;

 		           // Add to totals
 		           totalAmount += amount;
 		           totalCgst += cgst;
 		           totalSgst += sgst;
 		           totalIgst += igst;
 		       }

 		       // Iterate over the map and create rows for each importer's summary data
 		       int srNoSummary = 1;
 		       for (Map.Entry<String, Double[]> entry : summaryData.entrySet()) {
 		           String importerName = entry.getKey();
 		           Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		           // Create a new row for this importer
 		           Row row = sheet.createRow(rowNum++);
 		           row.createCell(0).setCellValue(srNoSummary++); // Sr No
 		           row.createCell(1).setCellValue(importerName); // Importer Name
 		           row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		           row.createCell(3).setCellValue(values[1]); // CGST
 		           row.createCell(4).setCellValue(values[2]); // SGST
 		           row.createCell(5).setCellValue(values[3]); // IGST
 		       }

 		       // Create a row for the totals
 		       Row totalRow = sheet.createRow(rowNum++);

 		       // Set the values for the total row
 		       totalRow.createCell(0).setCellValue("Total"); // Label for total row
 		       totalRow.createCell(2).setCellValue(totalAmount); // Total Taxable Amount
 		       totalRow.createCell(3).setCellValue(totalCgst);   // Total CGST
 		       totalRow.createCell(4).setCellValue(totalSgst);   // Total SGST
 		       totalRow.createCell(5).setCellValue(totalIgst);   // Total IGST
 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     
 		     
 		     
 		   
	         
 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary SAC Code Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		 // Header row for vessel summary
 		    String[] columnsHeaderVesselSummery = {
 		        "Sr No", "SAC CODE", "CGST", "SGST", "IGST"
 		    };

 		
 		    // Create a map to store vessel details (no need for container count)
 		    Map<String, Double[]> vesselSummaryData = new HashMap<>();

 		    // Iterate over importDetails and gather summary data for each vessel
 		    importDetails.forEach(i -> {
 		        String vessel = i[27] != null ? i[27].toString() : "Unknown"; // Vessel Name at index 4
 		       double cgst1 = 0.0, sgst1 = 0.0, igst1 = 0.0, amount1 = parseDoubleOrDefault(i[16], 0.0);

 		      // Check if the condition is "Y" for corresponding index and apply the formula
 		      if ("Y".equals(i[17].toString())) {
 		          cgst1 = amount1 / 2;  // CGST logic
 		      }

 		      if ("Y".equals(i[18].toString())) {
 		          sgst1 = amount1 / 2;  // SGST logic
 		      }

 		      if ("Y".equals(i[19].toString())) {
 		          igst1 = amount1;  // IGST logic
 		      }
 		        // Initialize vessel summary data if not already present
 		        if (!vesselSummaryData.containsKey(vessel)) {
 		            vesselSummaryData.put(vessel, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		        }

 		        // Accumulate values for each vessel
 		        Double[] data = vesselSummaryData.get(vessel);
// 		        data[0] += amount1;
 		        data[0] += cgst1;
 		        data[1] += sgst1;
 		        data[2] += igst1;
 		    });

 		    // Apply header row styling and set values for Vessel Wise Summary
 		    Row headerRowVessel = sheet.createRow(rowNum++);
 		    for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		        Cell cell = headerRowVessel.createCell(i);
 		        cell.setCellValue(columnsHeaderVesselSummery[i]);

 		        // Set cell style for header
 		        CellStyle headerStyle = workbook.createCellStyle();
 		        headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		        Font headerFont = workbook.createFont();
 		        headerFont.setBold(true);
 		        headerFont.setFontHeightInPoints((short) 11);
 		        headerStyle.setFont(headerFont);
 		        headerStyle.setBorderBottom(BorderStyle.THIN);
 		        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderTop(BorderStyle.THIN);
 		        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderLeft(BorderStyle.THIN);
 		        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderRight(BorderStyle.THIN);
 		        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        headerFont.setColor(IndexedColors.WHITE.getIndex());
 		        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		        cell.setCellStyle(headerStyle);
 		        int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		        sheet.setColumnWidth(i, headerWidth);
 		    }

 		    // Iterate over the map to create rows for each vessel's summary data
 		    int srNoVessel = 1;
 		    for (Map.Entry<String, Double[]> entry : vesselSummaryData.entrySet()) {
 		        String vessel = entry.getKey();
 		        Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		        // Create a new row for this vessel
 		        Row row = sheet.createRow(rowNum++);
 		        row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		        row.createCell(1).setCellValue(vessel); // Vessel Name
// 		        row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		        row.createCell(2).setCellValue(values[0]); // CGST
 		        row.createCell(3).setCellValue(values[1]); // SGST
 		        row.createCell(4).setCellValue(values[2]); // IGST
 		    }

 		    // Calculate totals for all vessel data
 		    double totalCGST = 0, totalSGST = 0, totalIGST = 0;
 		    for (Double[] values : vesselSummaryData.values()) {
// 		        totalTaxableAmount += values[0];
 		        totalCGST += values[0];
 		        totalSGST += values[1];
 		        totalIGST += values[2];
 		    }

 		    // Create a row for the totals of Vessel Wise Summary
 		    Row totalVesselRow = sheet.createRow(rowNum++);

 		    // Set the values for the total row
 		    totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
// 		    totalVesselRow.createCell(2).setCellValue(totalTaxableAmount); // Total Taxable Amount
 		    totalVesselRow.createCell(2).setCellValue(totalCGST); // Total CGST
 		    totalVesselRow.createCell(3).setCellValue(totalSGST); // Total SGST
 		    totalVesselRow.createCell(4).setCellValue(totalIGST); // Total IGST

 		    // Apply the total row style (light green background and bold font)


 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }


		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 45 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
//	private double parseDoubleSafely(String value) {
//	    try {
//	        return value.isEmpty() ? 0.0 : Double.parseDouble(value.trim());
//	    } catch (NumberFormatException e) {
//	        return 0.0; // Return a default value if parsing fails
//	    }
//	}	
	
	private double parseDoubleSafely(String value) {
	    try {
	        // Check for null or empty strings
	        if (value == null || value.trim().isEmpty()) {
	            return 0.0;
	        }
	        // Parse the value and ensure no rounding happens
	        return Double.parseDouble(value.trim());
	    } catch (NumberFormatException e) {
	        // Return a default value if parsing fails
	        return 0.0;
	    }
	}


	
	public byte[] createExcelReportOfInvoiceSalesRegisterContainerWiseReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		      
		        
		        Sheet sheet = workbook.createSheet("Invoice Register Container Wise Report");

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        
		        String[] columnsHeader = {
		        	    "SR NO",
		        	    "BILL TO",
		        	    "BILLING PARTY",
		        	    "IMPORTER/EXPORTER/CONSIGNEE NAME",
		        	    "LINE NAME",
		        	    "CHA NAME",
		        	    "ACCOUNT HOLDER",
		        	    "IGM/SB NO",
		        	    "ITEM NO",
		        	    "CONT NO",
		        	    "CONT SIZE",
		        	    "CONT TYPE",
		        	    "ENBLCK STATUS",
		        	    "FCL LCL MODE",
		        	    "CARGO WT",
		        	    "TARE WT",
		        	    "GROSS WT",
		        	    "AREA",
		        	    "LCL DESTUFF DATE",
		        	    "CARGO TYPE",
		        	    "GATE IN DATE",
		        	    "GATE OUT DATE",
		        	    "NO OF ITEMS",
		        	    "CARGO COMMODITY",
		        	    "INVOICE REF NO",
		        	    "INVOICE DATE",
		        	    "PAYMENT MODE",
		        	    "LOADED / DESTUFF",
		        	    "DPD/NON DPD (IMPORT)",
		        	    "BUFFER/DOCK STUFF (EXPORT)",
		        	    "EMPTY GATE IN",
		        	    "EMPTY GATE OUT",
		        	    "TOTAL NO OF DAYS",
		        	    "FREE DAYS",
		        	    "CHARGEABLE DAYS",
		        	    "BILL RATE",
		        	    "TOTAL INV AMOUNT",
		        	    "CGST",
		        	    "SGST",
		        	    "IGST",
		        	};

		        
		        Timestamp sqlStartDate = new Timestamp(startDate.getTime());
		        Timestamp sqlEndDate = new Timestamp(endDate.getTime());
		        
		        System.out.println("sqlStartDate "+sqlStartDate);
		        System.out.println("sqlEndDate "+sqlEndDate);
		        
		        List<Object[]> importDetails = financeReportsRepo.getInvoiceDetailsContainerWise(companyId, branchId,startDate,endDate,
		        		billParty);
		        
		        
		        
		        List<Object[]> services  = financeReportsRepo.findInvoiceServices(companyId, branchId,startDate,endDate);
		        
		        if (services!=null)
		        {
		        	services.forEach(i ->{
		        		System.out.println("i :" +i[3]);
		        	});
		        }
		        
//		      String invoiceNo = null;
//    	        
//		      String container =null ; // Get the container value from i[0]
		        
//  Vector<String> dynamic = new Vector<>();
//		        
//		        Map<String, Integer> containerValueCount = new HashMap<>(); // Map to store container count
//		        
//		        
//		        services.forEach(i -> {
//		    	    // Check if index 7 exists in the element to avoid IndexOutOfBoundsException
//		    	    if (i.length > 0) {
//		    	        String tot_services = i[3].toString();
//
//		    	        String   invoiceNo = i[0].toString();
//		    	        
//		    	        String  container = i[1].toString(); // Get the container value from i[0]
//		    	        // Split the element by comma
//		    	        String[] values = tot_services.split(",");
//		    	        
//		    	      
//		    	        // Check each value for duplicates before adding to the Vector
//		    	        for (String value : values) {
//		    	            value = value.trim(); // Remove extra spaces around the value
//		    	            
//		    	            String mainValue = invoiceNo+","+container ;
//		    	            
//		    	            containerValueCount.put(mainValue, containerValueCount.getOrDefault(mainValue, 0) + 1);
//		    	            
//		    	            if (!dynamic.contains(value)) {
//		    	                dynamic.add(value);
//		    	            }
//		    	        }
//		    	    }
//		    	});
//		        
//
//
//
//		     // Join the concatenated values with a comma separator
//		     String result = String.join(",", dynamic);
//
//		     System.out.println(result);
//
//		     String[] dynamicHeaders = result.split(",");
		        
		        
		        Vector<String> serviceVct = new Vector<>();
		        Map<String, Integer> containerValueCount = new HashMap<>(); // Map to store container count

		        // Initialize the map to store service data
		        Map<String, Map<String, Vector<Object>>> invServiceMap = new TreeMap<String, Map<String, Vector<Object>>>();

		        // Iterate through each service entry in the 'services' array
		        Map<String, BigDecimal[]> totalsByHeadKey = new TreeMap<>();

		        
//		        for (Object[] resultData1 : services) {
//		            if (resultData1.length > 0) {
//		                String tot_services = resultData1[3].toString();
//		                String invoiceNo = resultData1[0].toString();
//		                String  container = resultData1[1].toString();
//
//
//		                StringTokenizer stas = new StringTokenizer(tot_services, ",");
//		                
//		                while (stas.hasMoreTokens()) {
//	                        String serv = stas.nextToken();
//	                        
//			                String[] fields = serv.split("~");
//
//			                // Check if the split array has at least 5 elements
//			                if (fields.length > 4) {
//			                    // Extract the fifth element (index 4)
//			                    String serviceShortDesc = fields[4].trim();
//			                    String id = fields[0].trim();
//
////			                    String mainValue =extractedField+"~"+id ;
//			                    
//			                    String mainValue =serviceShortDesc+"~"+id ;
//
//			                    // Count occurrences of each mainValue
//			                   // containerValueCount.put(mainValue, containerValueCount.getOrDefault(mainValue, 0) + 1);
//
//			                    // Add the extracted field to the dynamic list if it's not already present
//			                    if (!serviceVct.contains(serviceShortDesc)) {
//			                    	serviceVct.add(serviceShortDesc);
//			                    }
//			                    
//			                } else {
//			                    // Handle the case where tot_services doesn't have enough elements
//			                    System.out.println("Error: tot_services does not contain enough elements.");
//			                }
//		                
//		                }
//		            }
//		        }
		        
		        for (Object[] resultData1 : services) {
		            if (resultData1.length > 0) {
		                String tot_services = resultData1[3] != null ? resultData1[3].toString().trim() : "";
//		                String invoiceNo = resultData1[0].toString();
//		                String container = resultData1[1].toString();

		                // Process only if tot_services is NOT null or empty
		                if (!tot_services.isEmpty()) {
		                    StringTokenizer stas = new StringTokenizer(tot_services, ",");

		                    while (stas.hasMoreTokens()) {
		                        String serv = stas.nextToken();
		                        String[] fields = serv.split("~");

		                        // Ensure the split array has at least 5 elements
		                        if (fields.length > 4) {
		                            String serviceShortDesc = fields[4].trim();
		                            String id = fields[0].trim();
		                            String mainValue = serviceShortDesc + "~" + id;

		                            // Add the extracted field to the dynamic list if it's not already present
		                            if (!serviceVct.contains(serviceShortDesc)) {
		                                serviceVct.add(serviceShortDesc);
		                            }
		                        } else {
		                            System.out.println("Error: tot_services does not contain enough elements.");
		                        }
		                    }
		                }
		            }
		        }

//		        for (Object[] resultData1 : services) {
		        	
		        
		        Map<String, Double> totalByHeadkey = new TreeMap<>();
		        Map<String, Double> totalByHeadkeyTax = new TreeMap<>();
		        double totalLocalAmt = 0.0; // To store the total of Local_Amt_str
		        double totalInvoiceAmt = 0.0; // To store the total of Invoice_Amt_str
		        
		        for (int i = 0; i < services.size(); i++) {
		        	
		            Object[] resultData1 = (Object[]) services.get(i); // Access each element in the list
		            if (resultData1.length > 0) 
		            {
//		                String tot_services = resultData1[3].toString();
//		                String invoiceNo = resultData1[0].toString();
//		                String  container = resultData1[1].toString();
		                
		                
		                String tot_services = resultData1[3] != null ? resultData1[3].toString().trim() : "";
		                String invoiceNo = resultData1[0] != null ? resultData1[0].toString().trim() : "";
		                String container = resultData1[1] != null ? resultData1[1].toString().trim() : "";
		                String headkey = invoiceNo + "~" + container;
		                
		                
		               
		                if (!tot_services.isEmpty() && !invoiceNo.isEmpty() && !container.isEmpty()) {
		                if (invServiceMap.containsKey(headkey)) 
		                {
		                	
		                    Map<String, Vector<Object>> serviceMap = invServiceMap.get(headkey);

		                    StringTokenizer sta = new StringTokenizer(tot_services, ",");
		                    while (sta.hasMoreTokens()) {
		                        String serv = sta.nextToken();
		                        
		                        String[] servicearr = serv.split("~");
		                        
		                        String key="";
		                        String Local_Amt_str = "";
	                            String invoice_Amt_str ="";
	                            String execution_Days = "";
	                            String free_Days = "";
	                            String chargeable_Days = "";
	                            
	                            String shortName = "";
	                            
		                        if (servicearr.length >= 8) {
		                             key = servicearr[0];
		                             Local_Amt_str = servicearr[1];
		                             invoice_Amt_str = servicearr[3];
		                             execution_Days = servicearr[5];
		                             free_Days = servicearr[6];
		                             chargeable_Days = servicearr[7];
		                             shortName= servicearr[4];
		                         
		                             
		                             totalLocalAmt += parseDoubleSafely(invoice_Amt_str);
				                        totalInvoiceAmt += parseDoubleSafely(invoice_Amt_str);
		                        } else {
		                            // Handle the case where the service data is incomplete or malformed
		                  
		                        }
		                        
		                        double localAmtT = parseDoubleSafely(Local_Amt_str);
		                        double invoiceAmt = parseDoubleSafely(invoice_Amt_str);

		                        // Update totalByHeadkey
		                        totalByHeadkeyTax.put(headkey, totalByHeadkeyTax.getOrDefault(headkey, 0.0) + invoiceAmt);
		                        totalByHeadkey.put(headkey, totalByHeadkey.getOrDefault(headkey, 0.0) + localAmtT);

		                        
		                        if (serviceMap.containsKey(key)) {
		                            Vector<Object> amtvct = serviceMap.get(key);
		                             
//		                            String shortDesc = (String) amtvct.get(4);
		                            String shortDesc = String.valueOf(amtvct.get(4)); // ✅ Safe conversion
		                            double localAmt = (double) amtvct.get(0), invAmt = (double) amtvct.get(1);
		                            double exe_days = (double) amtvct.get(2), free_days = (double) amtvct.get(3), chargable_days = (double) amtvct.get(4);

		                            localAmt += parseDoubleSafely(Local_Amt_str);
		                            invAmt += parseDoubleSafely(invoice_Amt_str);
		                            exe_days += parseDoubleSafely(execution_Days);
		                            free_days += parseDoubleSafely(free_Days);
		                            chargable_days += parseDoubleSafely(chargeable_Days);
		                            
		                            shortName= shortDesc;

		                            
		                            amtvct.set(0, localAmt);
		                            amtvct.set(1, invAmt);
		                            amtvct.set(2, exe_days);
		                            amtvct.set(3, free_days);
		                            amtvct.set(4, chargable_days);
		                            amtvct.set(5, shortName);

		                            serviceMap.put(key, amtvct);
		                        } else {
		                            // Create new service data if it doesn't exist
		                            Vector<Object> amtvct = new Vector<>();
		                            amtvct.add(0, parseDoubleSafely(Local_Amt_str));
		                            amtvct.add(1, parseDoubleSafely(invoice_Amt_str));
		                            amtvct.add(2, parseDoubleSafely(execution_Days));
		                            amtvct.add(3, parseDoubleSafely(free_Days));
		                            amtvct.add(4, parseDoubleSafely(chargeable_Days));
		                            amtvct.add(5, shortName);
		                            

		                            serviceMap.put(key, amtvct);
		                        }
		                    }
		                    invServiceMap.put(headkey, serviceMap);
		                } else {
		                	
		                    Map<String, Vector<Object>> serviceMap = new TreeMap<>();
		                    StringTokenizer sta = new StringTokenizer(tot_services, ",");
		                    while (sta.hasMoreTokens()) {
		                    	
		                    	
		                        String serv = sta.nextToken();
		                        

		                        String[] servicearr = serv.split("~");

		                        
		                        String key="";
		                        String Local_Amt_str = "";
	                            String invoice_Amt_str ="";
	                            String execution_Days = "";
	                            String free_Days = "";
	                            String chargeable_Days = "";
	                            String shortName = "";
	                            
		                        if (servicearr.length >= 8) {
		                             key = servicearr[0];
		                             Local_Amt_str = servicearr[1];
		                             invoice_Amt_str = servicearr[3];
		                             execution_Days = servicearr[5];
		                             free_Days = servicearr[6];
		                             chargeable_Days = servicearr[7];
		                             shortName= servicearr[4];
		                             
		                             
		                             totalLocalAmt += parseDoubleSafely(invoice_Amt_str);
				                        totalInvoiceAmt += parseDoubleSafely(invoice_Amt_str);

		                        } else {
		                            // Handle the case where the service data is incomplete or malformed
		                            System.out.println("Error: Invalid service data: " + serv);
		                        }

		                        
		                        double localAmtT = parseDoubleSafely(Local_Amt_str);
		                        double invoiceAmt = parseDoubleSafely(invoice_Amt_str);

		                        // Update totalByHeadkey
		                        totalByHeadkeyTax.put(headkey, totalByHeadkeyTax.getOrDefault(headkey, 0.0) + invoiceAmt);
		                        totalByHeadkey.put(headkey, totalByHeadkey.getOrDefault(headkey, 0.0) + localAmtT);
		                     
		                        // Check if service key already exists
		                        if (serviceMap.containsKey(key)) {
		                            Vector<Object> amtvct = serviceMap.get(key);
//		                            String shortDesc = (String) amtvct.get(4);
		                            String shortDesc = String.valueOf(amtvct.get(4)); // ✅ Safe conversion

		                            double localAmt = (double) amtvct.get(0), invAmt = (double) amtvct.get(1);
		                            double exe_days = (double) amtvct.get(2), free_days = (double) amtvct.get(3), chargable_days = (double) amtvct.get(4);

		                            shortName= shortDesc;
		                            localAmt += parseDoubleSafely(Local_Amt_str);
		                            invAmt += parseDoubleSafely(invoice_Amt_str);
		                            exe_days +=parseDoubleSafely(execution_Days);
		                            free_days += parseDoubleSafely(free_Days);
		                            chargable_days +=parseDoubleSafely(chargeable_Days);


		                            amtvct.set(0, localAmt);
		                            amtvct.set(1, invAmt);
		                            amtvct.set(2, exe_days);
		                            amtvct.set(3, free_days);
		                            amtvct.set(4, chargable_days);
		                            amtvct.set(5, shortName);

		                            serviceMap.put(key, amtvct);
		                        } else {
		                            // Create new service data
		                            Vector<Object> amtvct = new Vector<>();
		                            amtvct.add(0, parseDoubleSafely(Local_Amt_str));
		                            amtvct.add(1, parseDoubleSafely(invoice_Amt_str));
		                            amtvct.add(2, parseDoubleSafely(execution_Days));
		                            amtvct.add(3, parseDoubleSafely(free_Days));
		                            amtvct.add(4, parseDoubleSafely(chargeable_Days));
		                            amtvct.add(5, shortName);

		                            serviceMap.put(key, amtvct);
		                        }
		                    }

		                    invServiceMap.put(headkey, serviceMap);
		                }
		                }
		            }
		        }
		        
		        
		        System.out.println("Total by Headkey: " + totalByHeadkey);
		        System.out.println("Total by Headkey: " + totalByHeadkeyTax);

		        String result = String.join(",", serviceVct);
		        
//		        System.out.println("result :"+result);
		        // Split dynamic headers from the result
		        String[] dynamicHeaders = result.split(",");




		  // Create a combined array of static and dynamic headers
		  String[] combinedHeaders = new String[columnsHeader.length + dynamicHeaders.length];
		  System.arraycopy(columnsHeader, 0, combinedHeaders, 0, columnsHeader.length);
		  System.arraycopy(dynamicHeaders, 0, combinedHeaders, columnsHeader.length, dynamicHeaders.length);
		  
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);


		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Invoice Register Container Wise Report" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Invoice Register Container Wise Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Invoice Register Container Wise Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < combinedHeaders.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(combinedHeaders[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (combinedHeaders[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		     
		        
		        int serialNo = 1; // Initialize serial number counter
        	
		        Vector<String> servvct = new Vector<String>();
				  int ggroup = 0;
				  int cgroup = 0;
				  
		        for (Object[] resultData1 : importDetails) {
		        	 Row dataRow = sheet.createRow(rowNum++);
		        	 
		        	String   invoiceNo1 = resultData1[17].toString();
		        	
		        	String container1 = resultData1[6] != null ? resultData1[6].toString().trim() : "";
		        	
//		        	 String  container1 = resultData1[6].toString(); // Get the container value from i[0]
//		        	String tot_services =resultData1[23].toString(); // Get the container value from i[0]
		        	  String tot_services = resultData1[3] != null ? resultData1[3].toString().trim() : "";

//		        	  String key=null;
//		        	  
//		        	  if(!container1.isEmpty())
//		        	  {
		        			 String key =invoiceNo1+"~"+container1;
//		        		   key =invoiceNo1+"~"+container1;
//		        	  }
//		        

		            String  serviceid = resultData1[24].toString(); // Get the container value from i[0]

		            int cellNum = 0;

		            for (int i = 0; i < combinedHeaders.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (combinedHeaders[i]) {
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "BILL TO":
		                    cell.setCellValue(resultData1[28] != null ? resultData1[28].toString() : "");
		                    break;

		                case "BILLING PARTY":
		                    cell.setCellValue(resultData1[29] != null ? resultData1[29].toString() : "");
		                    break;

		                case "IMPORTER/EXPORTER/CONSIGNEE NAME":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "LINE NAME":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "CHA NAME":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "ACCOUNT HOLDER":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "IGM/SB NO":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "ITEM NO":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "CONT NO":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "CONT SIZE":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "CONT TYPE":
		                	
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "ENBLCK STATUS":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                	  cell.setCellValue("");
		                    break;

		                case "FCL LCL MODE":
		                    cell.setCellValue(resultData1[33] != null ? resultData1[33].toString() : "");
		                    break;

		                case "CARGO WT":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "TARE WT":
		                    cell.setCellValue(resultData1[32] != null ? resultData1[32].toString() : "");
		                    break;

		                case "GROSS WT":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "AREA":
		                    cell.setCellValue(resultData1[31] != null ? resultData1[31].toString() : "");
		                    break;

		                case "LCL DESTUFF DATE":
		                    if (resultData1[11] != null) {
		                        cell.setCellValue(resultData1[11].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "CARGO TYPE":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "GATE IN DATE":
		                    if (resultData1[13] != null) {
		                        cell.setCellValue(resultData1[13].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "GATE OUT DATE":
		                    if (resultData1[14] != null) {
		                        cell.setCellValue(resultData1[14].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "NO OF ITEMS":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "CARGO COMMODITY":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "INVOICE REF NO":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "INVOICE DATE":
		                    if (resultData1[18] != null) {
		                        cell.setCellValue(resultData1[18].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "PAYMENT MODE":
		                    if (resultData1[30] != null) {
		                        String paymentMode = resultData1[30].toString();
		                        cell.setCellValue(paymentMode.equals("N") ? "Cash" : paymentMode.equals("Y") ? "Credit" : "");
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "LOADED / DESTUFF":
		                    cell.setCellValue(resultData1[34] != null ? resultData1[34].toString() : "");
		                    break;

		                case "DPD/NON DPD (IMPORT)":
		                    cell.setCellValue(resultData1[35] != null ? 
		                        (resultData1[35].toString().equals("N00002") ? "NON DPD" : 
		                         resultData1[35].toString().equals("N00004") ? "DPD" : "") 
		                        : "");
		                    break;


		                case "BUFFER/DOCK STUFF (EXPORT)":
		                    cell.setCellValue(resultData1[36] != null ? resultData1[36].toString() : "");
		                    break;

		                case "EMPTY GATE IN":
		                    cell.setCellValue(resultData1[37] != null ? resultData1[37].toString() : "");
		                    break;

		                case "EMPTY GATE OUT":
		                    cell.setCellValue(resultData1[38] != null ? resultData1[38].toString() : "");
		                    break;

		                case "TOTAL NO OF DAYS":
		                    StringTokenizer st = new StringTokenizer(tot_services, ",");
		                    System.out.println("tot_services: " + tot_services);

		                    double totalExecutionDays = 0.0; // Initialize the total for execution days
		                  if(!tot_services.isEmpty()) {
		                    while (st.hasMoreTokens()) { // Iterate through all tokens
		                        String serv = st.nextToken();
		                        String[] servicearr = serv.split("~");

		                        // Variables to hold service data
		                        String execution_Days = "";
		                        String free_Days = "";
		                        String servgroup = "";

		                        // Parse service details
		                        if (servicearr.length >= 8) {
		                            execution_Days = servicearr[5];
		                            free_Days = servicearr[6];
		                            servgroup = servicearr[8];
		                        } else {
		                            
		                            continue; // Skip this iteration if data is malformed
		                        }

		                        double freedays = 0;
		                        double execdays = 0;

		                        // Parse execution and free days
		                        if (!free_Days.equals("")) {
		                            freedays = Double.parseDouble(free_Days);
		                        }
		                        if (servgroup.equals("G")  && !execution_Days.equals("")) {
		                            execdays = Double.parseDouble(execution_Days);
		                            totalExecutionDays += execdays; // Add to the total
		                        }

		                      
		                    }
		                }
		                    // Set the total execution days value in the cell
		                    cell.setCellValue(totalExecutionDays);
		                    cell.setCellStyle(numberCellStyle);

//		                    System.out.println("Total Execution Days: " + totalExecutionDays);
//		                    System.out.println("G Group Count: " + ggroup + ", C Group Count: " + cgroup);

		                    break;


		                case "FREE DAYS":
		                	 StringTokenizer st1 = new StringTokenizer(tot_services, ",");
			                    System.out.println("tot_services: " + tot_services);

			                    double totalFreeDays = 0.0; // Initialize the total for execution days
			                    if(!tot_services.isEmpty()) {
			                    while (st1.hasMoreTokens()) { // Iterate through all tokens
			                        String serv = st1.nextToken();
			                        String[] servicearr = serv.split("~");

			                        // Variables to hold service data
			                        String execution_Days = "";
			                        String free_Days = "";
			                        String servgroup = "";

			                        // Parse service details
			                        if (servicearr.length >= 8) {
			                            execution_Days = servicearr[5];
			                            free_Days = servicearr[6];
			                            servgroup = servicearr[8];
			                        } else {
			                          
			                            continue; // Skip this iteration if data is malformed
			                        }

			                        double freedays = 0;
			                        double execdays = 0;

			      
			                        if (servgroup.equals("G")  && !free_Days.equals("")) {
			                        	freedays = Double.parseDouble(free_Days);
			                            totalFreeDays += freedays; // Add to the total
			                        }

			                    }
			                    }

			                    // Set the total execution days value in the cell
			                    cell.setCellValue(totalFreeDays);
			                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "CHARGEABLE DAYS":
		                  	 StringTokenizer st11 = new StringTokenizer(tot_services, ",");
			                    System.out.println("tot_services: " + tot_services);

			                    double totalFreeDays1 = 0.0; // Initialize the total for execution days
			                    double totalExecutionDays1 = 0.0; // Initialize the total for execution days
			                    if(!tot_services.isEmpty()) {
			                    while (st11.hasMoreTokens()) { // Iterate through all tokens
			                        String serv = st11.nextToken();
			                        String[] servicearr = serv.split("~");

			                        // Variables to hold service data
			                        String execution_Days = "";
			                        String free_Days = "";
			                        String servgroup = "";

			                        // Parse service details
			                        if (servicearr.length >= 8) {
			                            execution_Days = servicearr[5];
			                            free_Days = servicearr[6];
			                            servgroup = servicearr[8];
			                        } else {
			                           
			                            continue; // Skip this iteration if data is malformed
			                        }

			                        double freedays = 0;
			                        double execdays = 0;

			      
			                        if (servgroup.equals("G")  && !free_Days.equals("")) {
			                        	freedays = Double.parseDouble(free_Days);
			                            totalFreeDays1 += freedays; // Add to the total
			                        }

			                        if (servgroup.equals("G")  && !execution_Days.equals("")) {
			                            execdays = Double.parseDouble(execution_Days);
			                            totalExecutionDays1 += execdays; // Add to the total
			                        }
			                      
			                    }
			                    
			                    }

			                    cell.setCellValue((totalExecutionDays1)-(totalFreeDays1));
			                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "CGST":
		                	
		                	 double totalInvoiceAmountT = 0.0; // Variable to store total invoice amount for the specific key

			                    // Iterate over each entry in the totalByHeadkeyTax map
			                    for (Map.Entry<String, Double> entry : totalByHeadkeyTax.entrySet()) {
			                        String key1 = entry.getKey();
			                        
			                        
			                        System.out.println( "key1 :  "+ key1   +" VALUED  : "+totalInvoiceAmountT );
			                        // Check if the current entry's key matches the specified key
			                        if (key1.equals(key)) {
			                            Double invoiceAmount = entry.getValue();
			                            
			                            // Accumulate the total invoice amount for the matching key
			                            totalInvoiceAmountT += invoiceAmount;
			                        }
			                        
			                        
			                    }

			                   if ("Y".equals(resultData1[21].toString() != null ? resultData1[21].toString() : "")) {
			                        // If the condition is met, divide the amount by 2 and set it as the cell value
			                        Double amount1 = parseDoubleOrDefault(totalInvoiceAmountT, 0.00) / 2;
			                        cell.setCellValue(amount1);
			                        cell.setCellStyle(numberCellStyle); // Apply the appropriate style
			                    } else {
			                        // Otherwise, set the CGST value
			                        Double cgst = parseDoubleOrDefault(resultData1[21], 0.00);
			                        cell.setCellValue(cgst);
			                        cell.setCellStyle(numberCellStyle); // Apply the appropriate style
			                    }

		                    break;


		                case "SGST":

		                	
		                	double totalInvoiceAmountT1 = 0.0; // Variable to store total invoice amount for the specific key

		                    // Iterate over each entry in the totalByHeadkeyTax map
		                    for (Map.Entry<String, Double> entry : totalByHeadkeyTax.entrySet()) {
		                        String key1 = entry.getKey();
		                        
		                        
		                        System.out.println( "key1 :  "+ key1   +" VALUED  : "+totalInvoiceAmountT1 );
		                        // Check if the current entry's key matches the specified key
		                        if (key1.equals(key)) {
		                            Double invoiceAmount = entry.getValue();
		                            
		                            // Accumulate the total invoice amount for the matching key
		                            totalInvoiceAmountT1 += invoiceAmount;
		                        }
		                        
		                        
		                    }

		                   if ("Y".equals(resultData1[22].toString() != null ? resultData1[22].toString() : "")) {
		                        // If the condition is met, divide the amount by 2 and set it as the cell value
		                        Double amount1 = parseDoubleOrDefault(totalInvoiceAmountT1, 0.00) / 2;
		                        cell.setCellValue(amount1);
		                        cell.setCellStyle(numberCellStyle); // Apply the appropriate style
		                    } else {
		                        // Otherwise, set the CGST value
		                        Double cgst = parseDoubleOrDefault(resultData1[22], 0.00);
		                        cell.setCellValue(cgst);
		                        cell.setCellStyle(numberCellStyle); // Apply the appropriate style
		                    }
		                    break;

		               

		                case "IGST":

		                	double totalInvoiceAmountT11 = 0.0; // Variable to store total invoice amount for the specific key

		                    // Iterate over each entry in the totalByHeadkeyTax map
		                    for (Map.Entry<String, Double> entry : totalByHeadkeyTax.entrySet()) {
		                        String key1 = entry.getKey();
		                        
		                        
		                        System.out.println( "key1 :  "+ key1   +" VALUED  : "+totalInvoiceAmountT11 );
		                        // Check if the current entry's key matches the specified key
		                        if (key1.equals(key)) {
		                            Double invoiceAmount = entry.getValue();
		                            
		                            // Accumulate the total invoice amount for the matching key
		                            totalInvoiceAmountT11 += invoiceAmount;
		                        }
		                        
		                        
		                    }

		                   if ("Y".equals(resultData1[20].toString() != null ? resultData1[20].toString() : "")) {
		                        // If the condition is met, divide the amount by 2 and set it as the cell value
		                        Double amount1 = parseDoubleOrDefault(totalInvoiceAmountT11, 0.00);
		                        cell.setCellValue(amount1);
		                        cell.setCellStyle(numberCellStyle); // Apply the appropriate style
		                    } else {
		                        // Otherwise, set the CGST value
		                        Double cgst = parseDoubleOrDefault(resultData1[20], 0.00);
		                        cell.setCellValue(cgst);
		                        cell.setCellStyle(numberCellStyle); // Apply the appropriate style
		                    }
		                    break;
		                    
		                case "BILL RATE":
		                    double totalLocatAmount = 0.0; // Variable to store total local amount for the specific key

		                    // Iterate over each entry in the totalByHeadkey map
		                    for (Map.Entry<String, Double> entry : totalByHeadkey.entrySet()) {
		                        String key1 = entry.getKey();
		                        
		                     System.out.println( "key1 :  "+ key1   +" VALUED  : "+totalLocatAmount );
		                        if (key1.equals(key)) {
		                            Double locatAmount1 = entry.getValue();
		                            
		                            // Accumulate the total local amount for the matching key
		                            totalLocatAmount += locatAmount1;
		                        }
		                    }

		                    // Set the total local amount (Bill Rate) for the specific key in the cell
		                    cell.setCellValue(totalLocatAmount);
		                    cell.setCellStyle(numberCellStyle); // Apply the appropriate style
		                    break;

		                case "TOTAL INV AMOUNT":
		                    double totalInvoiceAmountT111 = 0.0; // Variable to store total invoice amount for the specific key

		                    // Iterate over each entry in the totalByHeadkeyTax map
		                    for (Map.Entry<String, Double> entry : totalByHeadkeyTax.entrySet()) {
		                        String key1 = entry.getKey();
		                        
		                        
		                        System.out.println( "key1 :  "+ key1   +" VALUED  : "+totalInvoiceAmountT111 );
		                        // Check if the current entry's key matches the specified key
		                        if (key1.equals(key)) {
		                            Double invoiceAmount = entry.getValue();
		                            
		                            // Accumulate the total invoice amount for the matching key
		                            totalInvoiceAmountT111 += invoiceAmount;
		                        }
		                    }

		                    // Set the total invoice amount for the specific key in the cell
		                    cell.setCellValue(totalInvoiceAmountT111);
		                    cell.setCellStyle(numberCellStyle); // Apply the appropriate style
		                    break;

		                    
		                    
		                default:
		                	
		                	
		                    String mainValue = combinedHeaders[i];
		                    int count = 0;
		                    int countInvoice=0;
		                    
		                   
		                    String headkey = invoiceNo1 + "~" + container1;
		                    

		                    if (invServiceMap.containsKey(headkey)) {
		                        Map<String, Vector<Object>> serviceMap = invServiceMap.get(headkey);

		                       
		                        for (Map.Entry<String, Vector<Object>> entry : serviceMap.entrySet()) {
		                        	
		                            Vector<Object> serviceData = entry.getValue();

		                            if (serviceData != null && serviceData.size() >= 6) {
		                                String serviceName = (String) serviceData.get(5);

		                                if (serviceName.equals(mainValue)) 
		                                {
		                                    count = ((Double) serviceData.get(0)).intValue(); // Adjust index based on the structure of the data
		                                    cell.setCellValue(count);
		                                    cell.setCellStyle(numberCellStyle);
		                                    break; // Exit loop once the matching service is found
		                                }
		                                
		                            }
		                        }

		                        if (count == 0) {
		                            cell.setCellValue(0);
		                            cell.setCellStyle(numberCellStyle);
		                        }
		                    } else {
		                        cell.setCellValue(0);
		                        cell.setCellStyle(numberCellStyle);
		                    }
		                    
		                    
		                    
		                    break;

		            }



		            }
		            
		        }

		        System.out.println(" 894::  ggroup ::"+ggroup+" :: cgroup ::"+cgroup);
		        
		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Importer Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Header row for summary
 		    // Define header for Importer-wise summary
 		       String[] columnsHeaderSummary = {
 		           "Sr No", "Importer Name", "Taxable Amount", "CGST", "SGST", "IGST"
 		       };

 		       // Map to store summary data for each importer
 		       Map<String, Double[]> summaryData = new HashMap<>();
 		       double totalAmount = 0;
 		       double totalCgst = 0;
 		       double totalSgst = 0;
 		       double totalIgst = 0;

 		       // Apply header row styling and set values
 		       Row headerRow1 = sheet.createRow(rowNum++);
 		       for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		           Cell cell = headerRow1.createCell(i);
 		           cell.setCellValue(columnsHeaderSummary[i]);

 		           // Set cell style for header
 		           CellStyle headerStyle = workbook.createCellStyle();
 		           headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		           headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		           Font headerFont = workbook.createFont();
 		           headerFont.setBold(true);
 		           headerFont.setFontHeightInPoints((short) 11);
 		           headerFont.setColor(IndexedColors.WHITE.getIndex());
 		           headerStyle.setFont(headerFont);

 		           headerStyle.setBorderBottom(BorderStyle.THIN);
 		           headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderTop(BorderStyle.THIN);
 		           headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderLeft(BorderStyle.THIN);
 		           headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderRight(BorderStyle.THIN);
 		           headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		           headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		           headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		           cell.setCellStyle(headerStyle);
 		           int headerWidth = (int) (columnsHeaderSummary[i].length() * 360);
 		           sheet.setColumnWidth(i, headerWidth);
 		       }

 		       // Iterate over importDetails and sum up the required fields
 		       
// 		      double amount = 0.0; 
		         double cgst = 0.0; 
		         double sgst =0.0; 
		         double igst = 0.0; 
		         
 		       int srNo = 1;
 		       for (Object[] i : importDetails) {
 		           String importerName = i[0] != null ? i[0].toString() : "Unknown"; // Importer Name at index 4
// 		           double amount = i[43] != null ? Double.parseDouble(i[43].toString()) : 0; // Taxable Amount at index 16
 		           
 		          double amount = (i[43] != null && !i[43].toString().trim().isEmpty()) 
 		                 ? Double.parseDouble(i[43].toString()) 
 		                 : 0.0;

 		          if("Y".equals(i[21].toString())) {
 		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[43], 0.00) / 2;
 		        	 cgst = amount1;
 		          }
 		          
 		         if("Y".equals(i[22].toString())) {
		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[43], 0.00) / 2;
 		        	 sgst = amount1;
 		          }
 		         
 		        if("Y".equals(i[20].toString())) {
		        	  
		        	 Double amount1 = parseDoubleOrDefault(i[43], 0.00) ;
		        	 igst = amount1;
		          }
 		           
 		          
 	
 		           // Initialize summary data if not already present
 		           if (!summaryData.containsKey(importerName)) {
 		               summaryData.put(importerName, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		           }

 		           // Accumulate values for each importer
 		           Double[] data = summaryData.get(importerName);
 		           data[0] += amount;
 		           data[1] += cgst;
 		           data[2] += sgst;
 		           data[3] += igst;

 		           // Add to totals
 		           totalAmount += amount;
 		           totalCgst += cgst;
 		           totalSgst += sgst;
 		           totalIgst += igst;
 		       }

 		       // Iterate over the map and create rows for each importer's summary data
 		       int srNoSummary = 1;
 		       for (Map.Entry<String, Double[]> entry : summaryData.entrySet()) {
 		           String importerName = entry.getKey();
 		           Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		           // Create a new row for this importer
 		           Row row = sheet.createRow(rowNum++);
 		           row.createCell(0).setCellValue(srNoSummary++); // Sr No
 		           row.createCell(1).setCellValue(importerName); // Importer Name
 		           row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		           row.createCell(3).setCellValue(values[1]); // CGST
 		           row.createCell(4).setCellValue(values[2]); // SGST
 		           row.createCell(5).setCellValue(values[3]); // IGST
 		       }

 		       // Create a row for the totals
 		       Row totalRow = sheet.createRow(rowNum++);

 		       // Set the values for the total row
 		       totalRow.createCell(0).setCellValue("Total"); // Label for total row
 		       totalRow.createCell(2).setCellValue(totalAmount); // Total Taxable Amount
 		       totalRow.createCell(3).setCellValue(totalCgst);   // Total CGST
 		       totalRow.createCell(4).setCellValue(totalSgst);   // Total SGST
 		       totalRow.createCell(5).setCellValue(totalIgst);   // Total IGST
 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }
//
// 		     
// 		     
// 		     
// 		   
//	         
 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary SAC Code Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		 // Header row for vessel summary
 		    String[] columnsHeaderVesselSummery = {
 		        "Sr No", "SAC CODE", "CGST", "SGST", "IGST"
 		    };

 		
 		    // Create a map to store vessel details (no need for container count)
 		    Map<String, Double[]> vesselSummaryData = new HashMap<>();

 		    // Iterate over importDetails and gather summary data for each vessel
 		    importDetails.forEach(i -> {
 		        String vessel = i[44] != null ? i[44].toString() : "Unknown"; // Vessel Name at index 4
 		       double cgst1 = 0.0, sgst1 = 0.0, igst1 = 0.0, amount1 = parseDoubleOrDefault(i[43], 0.0);

 		      // Check if the condition is "Y" for corresponding index and apply the formula
 		      if ("Y".equals(i[21].toString())) {
 		          cgst1 = amount1 / 2;  // CGST logic
 		      }

 		      if ("Y".equals(i[22].toString())) {
 		          sgst1 = amount1 / 2;  // SGST logic
 		      }

 		      if ("Y".equals(i[20].toString())) {
 		          igst1 = amount1;  // IGST logic
 		      }
 		        // Initialize vessel summary data if not already present
 		        if (!vesselSummaryData.containsKey(vessel)) {
 		            vesselSummaryData.put(vessel, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		        }

 		        // Accumulate values for each vessel
 		        Double[] data = vesselSummaryData.get(vessel);
// 		        data[0] += amount1;
 		        data[0] += cgst1;
 		        data[1] += sgst1;
 		        data[2] += igst1;
 		    });

 		    // Apply header row styling and set values for Vessel Wise Summary
 		    Row headerRowVessel = sheet.createRow(rowNum++);
 		    for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		        Cell cell = headerRowVessel.createCell(i);
 		        cell.setCellValue(columnsHeaderVesselSummery[i]);

 		        // Set cell style for header
 		        CellStyle headerStyle = workbook.createCellStyle();
 		        headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		        Font headerFont = workbook.createFont();
 		        headerFont.setBold(true);
 		        headerFont.setFontHeightInPoints((short) 11);
 		        headerStyle.setFont(headerFont);
 		        headerStyle.setBorderBottom(BorderStyle.THIN);
 		        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderTop(BorderStyle.THIN);
 		        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderLeft(BorderStyle.THIN);
 		        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderRight(BorderStyle.THIN);
 		        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        headerFont.setColor(IndexedColors.WHITE.getIndex());
 		        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		        cell.setCellStyle(headerStyle);
 		        int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		        sheet.setColumnWidth(i, headerWidth);
 		    }

 		    // Iterate over the map to create rows for each vessel's summary data
 		    int srNoVessel = 1;
 		    for (Map.Entry<String, Double[]> entry : vesselSummaryData.entrySet()) {
 		        String vessel = entry.getKey();
 		        Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		        // Create a new row for this vessel
 		        Row row = sheet.createRow(rowNum++);
 		        row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		        row.createCell(1).setCellValue(vessel); // Vessel Name
// 		        row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		        row.createCell(2).setCellValue(values[0]); // CGST
 		        row.createCell(3).setCellValue(values[1]); // SGST
 		        row.createCell(4).setCellValue(values[2]); // IGST
 		    }

 		    // Calculate totals for all vessel data
 		    double totalCGST = 0, totalSGST = 0, totalIGST = 0;
 		    for (Double[] values : vesselSummaryData.values()) {
// 		        totalTaxableAmount += values[0];
 		        totalCGST += values[0];
 		        totalSGST += values[1];
 		        totalIGST += values[2];
 		    }

 		    // Create a row for the totals of Vessel Wise Summary
 		    Row totalVesselRow = sheet.createRow(rowNum++);

 		    // Set the values for the total row
 		    totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
// 		    totalVesselRow.createCell(2).setCellValue(totalTaxableAmount); // Total Taxable Amount
 		    totalVesselRow.createCell(2).setCellValue(totalCGST); // Total CGST
 		    totalVesselRow.createCell(3).setCellValue(totalSGST); // Total SGST
 		    totalVesselRow.createCell(4).setCellValue(totalIGST); // Total IGST

 		    // Apply the total row style (light green background and bold font)


 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }


		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 36 * 306); 
		        sheet.setColumnWidth(3, 36 * 306); 
		        
		        sheet.setColumnWidth(4, 36 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 14 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  14 * 306); 
		        sheet.setColumnWidth(14, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		        sheet.setColumnWidth(28, 18 * 306);
		        sheet.setColumnWidth(29, 18 * 306);
		        sheet.setColumnWidth(30, 18 * 306); 
		        sheet.setColumnWidth(31, 18 * 306); 
		        sheet.setColumnWidth(32, 18 * 306); 
		        sheet.setColumnWidth(33, 18 * 306); 
		        sheet.setColumnWidth(34, 18 * 306); 
		        sheet.setColumnWidth(35, 18 * 306); 
		        sheet.setColumnWidth(36, 18 * 306); 
		        sheet.setColumnWidth(37, 18 * 306); 
		        sheet.setColumnWidth(38, 18 * 306); 
		        sheet.setColumnWidth(39, 18 * 306); 
	
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOFCreditNoteReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		
		
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.getInvoiceSalesRegisterData(companyId, branchId,startDate,endDate,
		        		billParty);
		        Sheet sheet = workbook.createSheet("IMPORT");
		        
		        
		        Sheet exportSheet = workbook.createSheet("EXPORT");
		        createExcelReportOFCreditNoteExportReport(exportSheet, companyId, branchId,username,companyname,branchname, startDate,endDate,billParty);
		        		

		        Sheet miscSheet = workbook.createSheet("MISC");
		        createExcelReportOFCreditNoteMSICReport(miscSheet, companyId, branchId,username,companyname,branchname, startDate,endDate,billParty);
		        		


		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "Sr No",
		        	    "GST No",
		        	    "State ID",
		        	    "Credit Note Ref No",
		        	    "Credit Note Date",
		        	    "Invoice No",
		        	    "Invoice Ref No",
		        	    "Invoice Date",
		        	    "Customer Name",
		        	    "CGST",
		        	    "SGST",
		        	    "IGST",
		        	    "Basic Amt",
		        	    "Invoice Amt",
		        	    "GST Tax Amt",
		        	    "HSN_SAC Code",
		        	    "Address ID",
		        	    "Agent ID",
		        	    "IRN",
		        	    "Comments"
		        	};




	
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Credit Note Report For Import" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Credit Note Report For Import As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Credit Note Report For Import From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;

		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                switch (columnsHeader[i]) {
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "GST No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "State ID":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Credit Note Ref No":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Credit Note Date":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Invoice No":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Invoice Ref No":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Invoice Date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Customer Name":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

//		                case "CGST":
//		                    Double cgst = parseDoubleOrDefault(resultData1[8], 0.00);
//		                    cell.setCellValue(cgst);
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;

//		                case "SGST":
//		                	
////		                	if ("Y".equals(resultData1[18].toString() != null ? resultData1[18].toString() : "")) {
////		                        // If the condition is met, divide the amount by 2 and set it as the cell value
////		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00) / 2;
////		                        cell.setCellValue(amount1);
////		                        cell.setCellStyle(numberCellStyle);
////		                    } else {
////		                        // Otherwise, set the CGST value
////		                        Double SGST = parseDoubleOrDefault(resultData1[18], 0.00);
////		                        cell.setCellValue(SGST);
////		                        cell.setCellStyle(numberCellStyle);
////		                    }
////		                    cell.setCellStyle(numberCellStyle);
////		                    break;
//		                    Double sgst = parseDoubleOrDefault(resultData1[9], 0.00);
//		                    cell.setCellValue(sgst);
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;
//
//		                case "IGST":
//		                    Double igst = parseDoubleOrDefault(resultData1[10], 0.00);
//		                    cell.setCellValue(igst);
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;

		                case "Basic Amt":
		                    Double basicAmt = parseDoubleOrDefault(resultData1[11], 0.00);
		                    cell.setCellValue(basicAmt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Invoice Amt":
		                    Double invoiceAmt = parseDoubleOrDefault(resultData1[12], 0.00);
		                    cell.setCellValue(invoiceAmt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "GST Tax Amt":
		                    Double gstTaxAmt = parseDoubleOrDefault(resultData1[13], 0.00);
		                    cell.setCellValue(gstTaxAmt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "HSN_SAC Code":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Address ID":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Agent ID":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "IRN":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Comments":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       

		        
		        
		        
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Importer Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Header row for summary
 		    // Define header for Importer-wise summary
 		       String[] columnsHeaderSummary = {
 		           "Sr No", "Importer Name", "Taxable Amount", "CGST", "SGST", "IGST"
 		       };

 		       // Map to store summary data for each importer
 		       Map<String, Double[]> summaryData = new HashMap<>();
 		       double totalAmount = 0;
 		       double totalCgst = 0;
 		       double totalSgst = 0;
 		       double totalIgst = 0;

 		       // Apply header row styling and set values
 		       Row headerRow1 = sheet.createRow(rowNum++);
 		       for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		           Cell cell = headerRow1.createCell(i);
 		           cell.setCellValue(columnsHeaderSummary[i]);

 		           // Set cell style for header
 		           CellStyle headerStyle = workbook.createCellStyle();
 		           headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		           headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		           Font headerFont = workbook.createFont();
 		           headerFont.setBold(true);
 		           headerFont.setFontHeightInPoints((short) 11);
 		           headerFont.setColor(IndexedColors.WHITE.getIndex());
 		           headerStyle.setFont(headerFont);

 		           headerStyle.setBorderBottom(BorderStyle.THIN);
 		           headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderTop(BorderStyle.THIN);
 		           headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderLeft(BorderStyle.THIN);
 		           headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderRight(BorderStyle.THIN);
 		           headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		           headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		           headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		           cell.setCellStyle(headerStyle);
 		           int headerWidth = (int) (columnsHeaderSummary[i].length() * 360);
 		           sheet.setColumnWidth(i, headerWidth);
 		       }

 		       // Iterate over importDetails and sum up the required fields
 		       
// 		      double amount = 0.0; 
		         double cgst = 0.0; 
		         double sgst =0.0; 
		         double igst = 0.0; 
		         
 		       int srNo = 1;
 		       for (Object[] i : importDetails) {
 		           String importerName = i[4] != null ? i[4].toString() : "Unknown"; // Importer Name at index 4
 		           double amount = i[16] != null ? Double.parseDouble(i[16].toString()) : 0; // Taxable Amount at index 16
 		          if("Y".equals(i[17].toString())) {
 		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 cgst = amount1;
 		          }
 		          
 		         if("Y".equals(i[18].toString())) {
		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 sgst = amount1;
 		          }
 		         
 		        if("Y".equals(i[19].toString())) {
		        	  
		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) ;
		        	 igst = amount1;
		          }
 		           
 		          
 	
 		           // Initialize summary data if not already present
 		           if (!summaryData.containsKey(importerName)) {
 		               summaryData.put(importerName, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		           }

 		           // Accumulate values for each importer
 		           Double[] data = summaryData.get(importerName);
 		           data[0] += amount;
 		           data[1] += cgst;
 		           data[2] += sgst;
 		           data[3] += igst;

 		           // Add to totals
 		           totalAmount += amount;
 		           totalCgst += cgst;
 		           totalSgst += sgst;
 		           totalIgst += igst;
 		       }

 		       // Iterate over the map and create rows for each importer's summary data
 		       int srNoSummary = 1;
 		       for (Map.Entry<String, Double[]> entry : summaryData.entrySet()) {
 		           String importerName = entry.getKey();
 		           Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		           // Create a new row for this importer
 		           Row row = sheet.createRow(rowNum++);
 		           row.createCell(0).setCellValue(srNoSummary++); // Sr No
 		           row.createCell(1).setCellValue(importerName); // Importer Name
 		           row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		           row.createCell(3).setCellValue(values[1]); // CGST
 		           row.createCell(4).setCellValue(values[2]); // SGST
 		           row.createCell(5).setCellValue(values[3]); // IGST
 		       }

 		       // Create a row for the totals
 		       Row totalRow = sheet.createRow(rowNum++);

 		       // Set the values for the total row
 		       totalRow.createCell(0).setCellValue("Total"); // Label for total row
 		       totalRow.createCell(2).setCellValue(totalAmount); // Total Taxable Amount
 		       totalRow.createCell(3).setCellValue(totalCgst);   // Total CGST
 		       totalRow.createCell(4).setCellValue(totalSgst);   // Total SGST
 		       totalRow.createCell(5).setCellValue(totalIgst);   // Total IGST
 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     
 		     
 		     
 		   
	         
 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary SAC Code Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		 // Header row for vessel summary
 		    String[] columnsHeaderVesselSummery = {
 		        "Sr No", "SAC CODE", "CGST", "SGST", "IGST"
 		    };

 		
 		    // Create a map to store vessel details (no need for container count)
 		    Map<String, Double[]> vesselSummaryData = new HashMap<>();

 		    // Iterate over importDetails and gather summary data for each vessel
 		    importDetails.forEach(i -> {
 		        String vessel = i[27] != null ? i[27].toString() : "Unknown"; // Vessel Name at index 4
 		       double cgst1 = 0.0, sgst1 = 0.0, igst1 = 0.0, amount1 = parseDoubleOrDefault(i[16], 0.0);

 		      // Check if the condition is "Y" for corresponding index and apply the formula
 		      if ("Y".equals(i[17].toString())) {
 		          cgst1 = amount1 / 2;  // CGST logic
 		      }

 		      if ("Y".equals(i[18].toString())) {
 		          sgst1 = amount1 / 2;  // SGST logic
 		      }

 		      if ("Y".equals(i[19].toString())) {
 		          igst1 = amount1;  // IGST logic
 		      }
 		        // Initialize vessel summary data if not already present
 		        if (!vesselSummaryData.containsKey(vessel)) {
 		            vesselSummaryData.put(vessel, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		        }

 		        // Accumulate values for each vessel
 		        Double[] data = vesselSummaryData.get(vessel);
// 		        data[0] += amount1;
 		        data[0] += cgst1;
 		        data[1] += sgst1;
 		        data[2] += igst1;
 		    });

 		    // Apply header row styling and set values for Vessel Wise Summary
 		    Row headerRowVessel = sheet.createRow(rowNum++);
 		    for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		        Cell cell = headerRowVessel.createCell(i);
 		        cell.setCellValue(columnsHeaderVesselSummery[i]);

 		        // Set cell style for header
 		        CellStyle headerStyle = workbook.createCellStyle();
 		        headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		        Font headerFont = workbook.createFont();
 		        headerFont.setBold(true);
 		        headerFont.setFontHeightInPoints((short) 11);
 		        headerStyle.setFont(headerFont);
 		        headerStyle.setBorderBottom(BorderStyle.THIN);
 		        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderTop(BorderStyle.THIN);
 		        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderLeft(BorderStyle.THIN);
 		        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderRight(BorderStyle.THIN);
 		        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        headerFont.setColor(IndexedColors.WHITE.getIndex());
 		        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		        cell.setCellStyle(headerStyle);
 		        int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		        sheet.setColumnWidth(i, headerWidth);
 		    }

 		    // Iterate over the map to create rows for each vessel's summary data
 		    int srNoVessel = 1;
 		    for (Map.Entry<String, Double[]> entry : vesselSummaryData.entrySet()) {
 		        String vessel = entry.getKey();
 		        Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		        // Create a new row for this vessel
 		        Row row = sheet.createRow(rowNum++);
 		        row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		        row.createCell(1).setCellValue(vessel); // Vessel Name
// 		        row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		        row.createCell(2).setCellValue(values[0]); // CGST
 		        row.createCell(3).setCellValue(values[1]); // SGST
 		        row.createCell(4).setCellValue(values[2]); // IGST
 		    }

 		    // Calculate totals for all vessel data
 		    double totalCGST = 0, totalSGST = 0, totalIGST = 0;
 		    for (Double[] values : vesselSummaryData.values()) {
// 		        totalTaxableAmount += values[0];
 		        totalCGST += values[0];
 		        totalSGST += values[1];
 		        totalIGST += values[2];
 		    }

 		    // Create a row for the totals of Vessel Wise Summary
 		    Row totalVesselRow = sheet.createRow(rowNum++);

 		    // Set the values for the total row
 		    totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
// 		    totalVesselRow.createCell(2).setCellValue(totalTaxableAmount); // Total Taxable Amount
 		    totalVesselRow.createCell(2).setCellValue(totalCGST); // Total CGST
 		    totalVesselRow.createCell(3).setCellValue(totalSGST); // Total SGST
 		    totalVesselRow.createCell(4).setCellValue(totalIGST); // Total IGST

 		    // Apply the total row style (light green background and bold font)


 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }


		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 45 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	public byte[] createExcelReportOFCreditNoteExportReport(Sheet sheet,String companyId,
			String branchId, String username, String companyname,
			String branchname,
		Date startDate,
		Date   endDate,String  billParty
			) throws DocumentException {
		
		Workbook workbook = sheet.getWorkbook();
		
		    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.getInvoiceSalesRegisterData(companyId, branchId,startDate,endDate,
		        		billParty);
		        

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "Sr No",
		        	    "GST No",
		        	    "State ID",
		        	    "Credit Note Ref No",
		        	    "Credit Note Date",
		        	    "Invoice No",
		        	    "Invoice Ref No",
		        	    "Invoice Date",
		        	    "Customer Name",
		        	    "CGST",
		        	    "SGST",
		        	    "IGST",
		        	    "Basic Amt",
		        	    "Invoice Amt",
		        	    "GST Tax Amt",
		        	    "HSN_SAC Code",
		        	    "Address ID",
		        	    "Agent ID",
		        	    "IRN",
		        	    "Comments"
		        	};



	
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Credit Note Report For Export" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Credit Note Report For Export As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Credit Note Report For Export From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "GST No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "State ID":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Credit Note Ref No":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Credit Note Date":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Invoice No":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Invoice Ref No":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Invoice Date":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Customer Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

//		                case "CGST":
//		                    if ("Y".equals(resultData1[9].toString() != null ? resultData1[9].toString() : "")) {
//		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00) / 2;
//		                        cell.setCellValue(amount1);
//		                    } else {
//		                        Double cgst = parseDoubleOrDefault(resultData1[9], 0.00);
//		                        cell.setCellValue(cgst);
//		                    }
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;
//
//		                case "SGST":
//		                    if ("Y".equals(resultData1[10].toString() != null ? resultData1[10].toString() : "")) {
//		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00) / 2;
//		                        cell.setCellValue(amount1);
//		                    } else {
//		                        Double sgst = parseDoubleOrDefault(resultData1[10], 0.00);
//		                        cell.setCellValue(sgst);
//		                    }
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;
//
//		                case "IGST":
//		                    if ("Y".equals(resultData1[11].toString() != null ? resultData1[11].toString() : "")) {
//		                        Double amount1 = parseDoubleOrDefault(resultData1[16], 0.00);
//		                        cell.setCellValue(amount1);
//		                    } else {
//		                        Double igst = parseDoubleOrDefault(resultData1[11], 0.00);
//		                        cell.setCellValue(igst);
//		                    }
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;

		                case "Basic Amt":
		                    Double basicAmount = parseDoubleOrDefault(resultData1[12], 0.00);
		                    cell.setCellValue(basicAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Invoice Amt":
		                    Double invoiceAmount = parseDoubleOrDefault(resultData1[13], 0.00);
		                    cell.setCellValue(invoiceAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "GST Tax Amt":
		                    Double gstTaxAmount = parseDoubleOrDefault(resultData1[14], 0.00);
		                    cell.setCellValue(gstTaxAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "HSN_SAC Code":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Address ID":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Agent ID":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "IRN":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Comments":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }



		            }
		        }
		       

		        
		        
		        
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Importer Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Header row for summary
 		    // Define header for Importer-wise summary
 		       String[] columnsHeaderSummary = {
 		           "Sr No", "Importer Name", "Taxable Amount", "CGST", "SGST", "IGST"
 		       };

 		       // Map to store summary data for each importer
 		       Map<String, Double[]> summaryData = new HashMap<>();
 		       double totalAmount = 0;
 		       double totalCgst = 0;
 		       double totalSgst = 0;
 		       double totalIgst = 0;

 		       // Apply header row styling and set values
 		       Row headerRow1 = sheet.createRow(rowNum++);
 		       for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		           Cell cell = headerRow1.createCell(i);
 		           cell.setCellValue(columnsHeaderSummary[i]);

 		           // Set cell style for header
 		           CellStyle headerStyle = workbook.createCellStyle();
 		           headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		           headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		           Font headerFont = workbook.createFont();
 		           headerFont.setBold(true);
 		           headerFont.setFontHeightInPoints((short) 11);
 		           headerFont.setColor(IndexedColors.WHITE.getIndex());
 		           headerStyle.setFont(headerFont);

 		           headerStyle.setBorderBottom(BorderStyle.THIN);
 		           headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderTop(BorderStyle.THIN);
 		           headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderLeft(BorderStyle.THIN);
 		           headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderRight(BorderStyle.THIN);
 		           headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		           headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		           headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		           cell.setCellStyle(headerStyle);
 		           int headerWidth = (int) (columnsHeaderSummary[i].length() * 360);
 		           sheet.setColumnWidth(i, headerWidth);
 		       }

 		       // Iterate over importDetails and sum up the required fields
 		       
// 		      double amount = 0.0; 
		         double cgst = 0.0; 
		         double sgst =0.0; 
		         double igst = 0.0; 
		         
 		       int srNo = 1;
 		       for (Object[] i : importDetails) {
 		           String importerName = i[4] != null ? i[4].toString() : "Unknown"; // Importer Name at index 4
 		           double amount = i[16] != null ? Double.parseDouble(i[16].toString()) : 0; // Taxable Amount at index 16
 		          if("Y".equals(i[17].toString())) {
 		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 cgst = amount1;
 		          }
 		          
 		         if("Y".equals(i[18].toString())) {
		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 sgst = amount1;
 		          }
 		         
 		        if("Y".equals(i[19].toString())) {
		        	  
		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) ;
		        	 igst = amount1;
		          }
 		           
 		          
 	
 		           // Initialize summary data if not already present
 		           if (!summaryData.containsKey(importerName)) {
 		               summaryData.put(importerName, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		           }

 		           // Accumulate values for each importer
 		           Double[] data = summaryData.get(importerName);
 		           data[0] += amount;
 		           data[1] += cgst;
 		           data[2] += sgst;
 		           data[3] += igst;

 		           // Add to totals
 		           totalAmount += amount;
 		           totalCgst += cgst;
 		           totalSgst += sgst;
 		           totalIgst += igst;
 		       }

 		       // Iterate over the map and create rows for each importer's summary data
 		       int srNoSummary = 1;
 		       for (Map.Entry<String, Double[]> entry : summaryData.entrySet()) {
 		           String importerName = entry.getKey();
 		           Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		           // Create a new row for this importer
 		           Row row = sheet.createRow(rowNum++);
 		           row.createCell(0).setCellValue(srNoSummary++); // Sr No
 		           row.createCell(1).setCellValue(importerName); // Importer Name
 		           row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		           row.createCell(3).setCellValue(values[1]); // CGST
 		           row.createCell(4).setCellValue(values[2]); // SGST
 		           row.createCell(5).setCellValue(values[3]); // IGST
 		       }

 		       // Create a row for the totals
 		       Row totalRow = sheet.createRow(rowNum++);

 		       // Set the values for the total row
 		       totalRow.createCell(0).setCellValue("Total"); // Label for total row
 		       totalRow.createCell(2).setCellValue(totalAmount); // Total Taxable Amount
 		       totalRow.createCell(3).setCellValue(totalCgst);   // Total CGST
 		       totalRow.createCell(4).setCellValue(totalSgst);   // Total SGST
 		       totalRow.createCell(5).setCellValue(totalIgst);   // Total IGST
 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     
 		     
 		     
 		   
	         
 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary SAC Code Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		 // Header row for vessel summary
 		    String[] columnsHeaderVesselSummery = {
 		        "Sr No", "SAC CODE", "CGST", "SGST", "IGST"
 		    };

 		
 		    // Create a map to store vessel details (no need for container count)
 		    Map<String, Double[]> vesselSummaryData = new HashMap<>();

 		    // Iterate over importDetails and gather summary data for each vessel
 		    importDetails.forEach(i -> {
 		        String vessel = i[27] != null ? i[27].toString() : "Unknown"; // Vessel Name at index 4
 		       double cgst1 = 0.0, sgst1 = 0.0, igst1 = 0.0, amount1 = parseDoubleOrDefault(i[16], 0.0);

 		      // Check if the condition is "Y" for corresponding index and apply the formula
 		      if ("Y".equals(i[17].toString())) {
 		          cgst1 = amount1 / 2;  // CGST logic
 		      }

 		      if ("Y".equals(i[18].toString())) {
 		          sgst1 = amount1 / 2;  // SGST logic
 		      }

 		      if ("Y".equals(i[19].toString())) {
 		          igst1 = amount1;  // IGST logic
 		      }
 		        // Initialize vessel summary data if not already present
 		        if (!vesselSummaryData.containsKey(vessel)) {
 		            vesselSummaryData.put(vessel, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		        }

 		        // Accumulate values for each vessel
 		        Double[] data = vesselSummaryData.get(vessel);
// 		        data[0] += amount1;
 		        data[0] += cgst1;
 		        data[1] += sgst1;
 		        data[2] += igst1;
 		    });

 		    // Apply header row styling and set values for Vessel Wise Summary
 		    Row headerRowVessel = sheet.createRow(rowNum++);
 		    for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		        Cell cell = headerRowVessel.createCell(i);
 		        cell.setCellValue(columnsHeaderVesselSummery[i]);

 		        // Set cell style for header
 		        CellStyle headerStyle = workbook.createCellStyle();
 		        headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		        Font headerFont = workbook.createFont();
 		        headerFont.setBold(true);
 		        headerFont.setFontHeightInPoints((short) 11);
 		        headerStyle.setFont(headerFont);
 		        headerStyle.setBorderBottom(BorderStyle.THIN);
 		        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderTop(BorderStyle.THIN);
 		        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderLeft(BorderStyle.THIN);
 		        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderRight(BorderStyle.THIN);
 		        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        headerFont.setColor(IndexedColors.WHITE.getIndex());
 		        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		        cell.setCellStyle(headerStyle);
 		        int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		        sheet.setColumnWidth(i, headerWidth);
 		    }

 		    // Iterate over the map to create rows for each vessel's summary data
 		    int srNoVessel = 1;
 		    for (Map.Entry<String, Double[]> entry : vesselSummaryData.entrySet()) {
 		        String vessel = entry.getKey();
 		        Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		        // Create a new row for this vessel
 		        Row row = sheet.createRow(rowNum++);
 		        row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		        row.createCell(1).setCellValue(vessel); // Vessel Name
// 		        row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		        row.createCell(2).setCellValue(values[0]); // CGST
 		        row.createCell(3).setCellValue(values[1]); // SGST
 		        row.createCell(4).setCellValue(values[2]); // IGST
 		    }

 		    // Calculate totals for all vessel data
 		    double totalCGST = 0, totalSGST = 0, totalIGST = 0;
 		    for (Double[] values : vesselSummaryData.values()) {
// 		        totalTaxableAmount += values[0];
 		        totalCGST += values[0];
 		        totalSGST += values[1];
 		        totalIGST += values[2];
 		    }

 		    // Create a row for the totals of Vessel Wise Summary
 		    Row totalVesselRow = sheet.createRow(rowNum++);

 		    // Set the values for the total row
 		    totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
// 		    totalVesselRow.createCell(2).setCellValue(totalTaxableAmount); // Total Taxable Amount
 		    totalVesselRow.createCell(2).setCellValue(totalCGST); // Total CGST
 		    totalVesselRow.createCell(3).setCellValue(totalSGST); // Total SGST
 		    totalVesselRow.createCell(4).setCellValue(totalIGST); // Total IGST

 		    // Apply the total row style (light green background and bold font)


 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }


		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 45 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	

	
	public byte[] createExcelReportOFCreditNoteMSICReport(Sheet sheet,String companyId,
			String branchId, String username, String companyname,
			String branchname,
		Date startDate,
		Date   endDate,String  billParty
			) throws DocumentException {
		
		Workbook workbook = sheet.getWorkbook();
		    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.getInvoiceSalesRegisterData(companyId, branchId,startDate,endDate,
		        		billParty);
		       
		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        
		        String[] columnsHeader = {
		        	    "Sr No",
		        	    "GST No",
		        	    "State ID",
		        	    "Credit Note Ref No",
		        	    "Credit Note Date",
		        	    "Invoice No",
		        	    "Invoice Ref No",
		        	    "Invoice Date",
		        	    "Customer Name",
		        	    "CGST",
		        	    "SGST",
		        	    "IGST",
		        	    "Basic Amt",
		        	    "Invoice Amt",
		        	    "GST Tax Amt",
		        	    "HSN_SAC Code",
		        	    "Address ID",
		        	    "Agent ID",
		        	    "IRN",
		        	    "Comments"
		        	};




	
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Credit Note Report For MISC" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Credit Note Report For MISC As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Credit Note Report For MISC From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "Credit Note Date":
		                    if (resultData1[0] != null) {
		                        cell.setCellValue(resultData1[0].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Credit Note Ref No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Invoice No":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Invoice Ref No":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Invoice Date":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Customer Name":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Basic Amt":
		                    Double basicAmt = parseDoubleOrDefault(resultData1[6], 0.00);
		                    cell.setCellValue(basicAmt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

//		                case "CGST":
//		                    if ("Y".equals(resultData1[7] != null ? resultData1[7].toString() : "")) {
//		                        Double amount1 = parseDoubleOrDefault(resultData1[6], 0.00) / 2;
//		                        cell.setCellValue(amount1);
//		                    } else {
//		                        Double cgst = parseDoubleOrDefault(resultData1[7], 0.00);
//		                        cell.setCellValue(cgst);
//		                    }
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;
//
//		                case "SGST":
//		                    if ("Y".equals(resultData1[8] != null ? resultData1[8].toString() : "")) {
//		                        Double amount1 = parseDoubleOrDefault(resultData1[6], 0.00) / 2;
//		                        cell.setCellValue(amount1);
//		                    } else {
//		                        Double sgst = parseDoubleOrDefault(resultData1[8], 0.00);
//		                        cell.setCellValue(sgst);
//		                    }
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;
//
//		                case "IGST":
//		                    if ("Y".equals(resultData1[9] != null ? resultData1[9].toString() : "")) {
//		                        Double amount1 = parseDoubleOrDefault(resultData1[6], 0.00);
//		                        cell.setCellValue(amount1);
//		                    } else {
//		                        Double igst = parseDoubleOrDefault(resultData1[9], 0.00);
//		                        cell.setCellValue(igst);
//		                    }
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;

		                case "GST No":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "State ID":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Invoice Amt":
		                    Double invoiceAmt = parseDoubleOrDefault(resultData1[12], 0.00);
		                    cell.setCellValue(invoiceAmt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "GST Tax Amt":
		                    Double gstTaxAmt = parseDoubleOrDefault(resultData1[13], 0.00);
		                    cell.setCellValue(gstTaxAmt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "HSN_SAC Code":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Address ID":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Agent ID":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "IRN":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Comments":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       

		        
		        
		        
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Importer Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Header row for summary
 		    // Define header for Importer-wise summary
 		       String[] columnsHeaderSummary = {
 		           "Sr No", "Importer Name", "Taxable Amount", "CGST", "SGST", "IGST"
 		       };

 		       // Map to store summary data for each importer
 		       Map<String, Double[]> summaryData = new HashMap<>();
 		       double totalAmount = 0;
 		       double totalCgst = 0;
 		       double totalSgst = 0;
 		       double totalIgst = 0;

 		       // Apply header row styling and set values
 		       Row headerRow1 = sheet.createRow(rowNum++);
 		       for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		           Cell cell = headerRow1.createCell(i);
 		           cell.setCellValue(columnsHeaderSummary[i]);

 		           // Set cell style for header
 		           CellStyle headerStyle = workbook.createCellStyle();
 		           headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		           headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		           Font headerFont = workbook.createFont();
 		           headerFont.setBold(true);
 		           headerFont.setFontHeightInPoints((short) 11);
 		           headerFont.setColor(IndexedColors.WHITE.getIndex());
 		           headerStyle.setFont(headerFont);

 		           headerStyle.setBorderBottom(BorderStyle.THIN);
 		           headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderTop(BorderStyle.THIN);
 		           headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderLeft(BorderStyle.THIN);
 		           headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		           headerStyle.setBorderRight(BorderStyle.THIN);
 		           headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		           headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		           headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		           cell.setCellStyle(headerStyle);
 		           int headerWidth = (int) (columnsHeaderSummary[i].length() * 360);
 		           sheet.setColumnWidth(i, headerWidth);
 		       }

 		       // Iterate over importDetails and sum up the required fields
 		       
// 		      double amount = 0.0; 
		         double cgst = 0.0; 
		         double sgst =0.0; 
		         double igst = 0.0; 
		         
 		       int srNo = 1;
 		       for (Object[] i : importDetails) {
 		           String importerName = i[4] != null ? i[4].toString() : "Unknown"; // Importer Name at index 4
 		           double amount = i[16] != null ? Double.parseDouble(i[16].toString()) : 0; // Taxable Amount at index 16
 		          if("Y".equals(i[17].toString())) {
 		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 cgst = amount1;
 		          }
 		          
 		         if("Y".equals(i[18].toString())) {
		        	  
 		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) / 2;
 		        	 sgst = amount1;
 		          }
 		         
 		        if("Y".equals(i[19].toString())) {
		        	  
		        	 Double amount1 = parseDoubleOrDefault(i[16], 0.00) ;
		        	 igst = amount1;
		          }
 		           
 		          
 	
 		           // Initialize summary data if not already present
 		           if (!summaryData.containsKey(importerName)) {
 		               summaryData.put(importerName, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		           }

 		           // Accumulate values for each importer
 		           Double[] data = summaryData.get(importerName);
 		           data[0] += amount;
 		           data[1] += cgst;
 		           data[2] += sgst;
 		           data[3] += igst;

 		           // Add to totals
 		           totalAmount += amount;
 		           totalCgst += cgst;
 		           totalSgst += sgst;
 		           totalIgst += igst;
 		       }

 		       // Iterate over the map and create rows for each importer's summary data
 		       int srNoSummary = 1;
 		       for (Map.Entry<String, Double[]> entry : summaryData.entrySet()) {
 		           String importerName = entry.getKey();
 		           Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		           // Create a new row for this importer
 		           Row row = sheet.createRow(rowNum++);
 		           row.createCell(0).setCellValue(srNoSummary++); // Sr No
 		           row.createCell(1).setCellValue(importerName); // Importer Name
 		           row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		           row.createCell(3).setCellValue(values[1]); // CGST
 		           row.createCell(4).setCellValue(values[2]); // SGST
 		           row.createCell(5).setCellValue(values[3]); // IGST
 		       }

 		       // Create a row for the totals
 		       Row totalRow = sheet.createRow(rowNum++);

 		       // Set the values for the total row
 		       totalRow.createCell(0).setCellValue("Total"); // Label for total row
 		       totalRow.createCell(2).setCellValue(totalAmount); // Total Taxable Amount
 		       totalRow.createCell(3).setCellValue(totalCgst);   // Total CGST
 		       totalRow.createCell(4).setCellValue(totalSgst);   // Total SGST
 		       totalRow.createCell(5).setCellValue(totalIgst);   // Total IGST
 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummary.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     
 		     
 		     
 		   
	         
 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary SAC Code Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		 // Header row for vessel summary
 		    String[] columnsHeaderVesselSummery = {
 		        "Sr No", "SAC CODE", "CGST", "SGST", "IGST"
 		    };

 		
 		    // Create a map to store vessel details (no need for container count)
 		    Map<String, Double[]> vesselSummaryData = new HashMap<>();

 		    // Iterate over importDetails and gather summary data for each vessel
 		    importDetails.forEach(i -> {
 		        String vessel = i[27] != null ? i[27].toString() : "Unknown"; // Vessel Name at index 4
 		       double cgst1 = 0.0, sgst1 = 0.0, igst1 = 0.0, amount1 = parseDoubleOrDefault(i[16], 0.0);

 		      // Check if the condition is "Y" for corresponding index and apply the formula
 		      if ("Y".equals(i[17].toString())) {
 		          cgst1 = amount1 / 2;  // CGST logic
 		      }

 		      if ("Y".equals(i[18].toString())) {
 		          sgst1 = amount1 / 2;  // SGST logic
 		      }

 		      if ("Y".equals(i[19].toString())) {
 		          igst1 = amount1;  // IGST logic
 		      }
 		        // Initialize vessel summary data if not already present
 		        if (!vesselSummaryData.containsKey(vessel)) {
 		            vesselSummaryData.put(vessel, new Double[]{0.0, 0.0, 0.0, 0.0}); // Taxable Amount, CGST, SGST, IGST
 		        }

 		        // Accumulate values for each vessel
 		        Double[] data = vesselSummaryData.get(vessel);
// 		        data[0] += amount1;
 		        data[0] += cgst1;
 		        data[1] += sgst1;
 		        data[2] += igst1;
 		    });

 		    // Apply header row styling and set values for Vessel Wise Summary
 		    Row headerRowVessel = sheet.createRow(rowNum++);
 		    for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		        Cell cell = headerRowVessel.createCell(i);
 		        cell.setCellValue(columnsHeaderVesselSummery[i]);

 		        // Set cell style for header
 		        CellStyle headerStyle = workbook.createCellStyle();
 		        headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		        Font headerFont = workbook.createFont();
 		        headerFont.setBold(true);
 		        headerFont.setFontHeightInPoints((short) 11);
 		        headerStyle.setFont(headerFont);
 		        headerStyle.setBorderBottom(BorderStyle.THIN);
 		        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderTop(BorderStyle.THIN);
 		        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderLeft(BorderStyle.THIN);
 		        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        headerStyle.setBorderRight(BorderStyle.THIN);
 		        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        headerFont.setColor(IndexedColors.WHITE.getIndex());
 		        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		        cell.setCellStyle(headerStyle);
 		        int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		        sheet.setColumnWidth(i, headerWidth);
 		    }

 		    // Iterate over the map to create rows for each vessel's summary data
 		    int srNoVessel = 1;
 		    for (Map.Entry<String, Double[]> entry : vesselSummaryData.entrySet()) {
 		        String vessel = entry.getKey();
 		        Double[] values = entry.getValue(); // Taxable Amount, CGST, SGST, IGST

 		        // Create a new row for this vessel
 		        Row row = sheet.createRow(rowNum++);
 		        row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		        row.createCell(1).setCellValue(vessel); // Vessel Name
// 		        row.createCell(2).setCellValue(values[0]); // Taxable Amount
 		        row.createCell(2).setCellValue(values[0]); // CGST
 		        row.createCell(3).setCellValue(values[1]); // SGST
 		        row.createCell(4).setCellValue(values[2]); // IGST
 		    }

 		    // Calculate totals for all vessel data
 		    double totalCGST = 0, totalSGST = 0, totalIGST = 0;
 		    for (Double[] values : vesselSummaryData.values()) {
// 		        totalTaxableAmount += values[0];
 		        totalCGST += values[0];
 		        totalSGST += values[1];
 		        totalIGST += values[2];
 		    }

 		    // Create a row for the totals of Vessel Wise Summary
 		    Row totalVesselRow = sheet.createRow(rowNum++);

 		    // Set the values for the total row
 		    totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
// 		    totalVesselRow.createCell(2).setCellValue(totalTaxableAmount); // Total Taxable Amount
 		    totalVesselRow.createCell(2).setCellValue(totalCGST); // Total CGST
 		    totalVesselRow.createCell(3).setCellValue(totalSGST); // Total SGST
 		    totalVesselRow.createCell(4).setCellValue(totalIGST); // Total IGST

 		    // Apply the total row style (light green background and bold font)


 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }


		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 45 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	public byte[] createExcelReportOfExportMonthlyRevenueReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.findExportCLPInvoiceReceiptOnly(companyId, branchId, startDate, endDate,billParty);
		        
		        
		        Sheet sheet = workbook.createSheet("RECEIPT Only CLP Contain");
		        
		        
		        Sheet sheet1 = workbook.createSheet("TEUS Only CLP(Loaded Destuff)");
		        createExcelOfTEUSOnlyCLP(sheet1, companyId, branchId,username,companyname,branchname, startDate,endDate,billParty);

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "Sr_no", "LINE NAME", "RECEIPT NO", "INVOICE_DATE","WITH GST"
		        	};
		        
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columnsHeader.length - 1));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Monthly Revenue Report" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Monthly Revenue Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Monthly Revenue Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;


		        
		        
		        int serialNo = 1; // Initialize serial number counter
		       
		        double totalInvoiceAmount = 0;
		        double totalTEUs = 0;
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr_no":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "LINE NAME":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "RECEIPT NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                  
		                    break;

		                case "INVOICE_DATE":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date format
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;


		                case "WITH GST":
		                    Double amt = 0.0;
		                    if (resultData1[3] != null) {
		                        try {
		                            amt = Double.parseDouble(resultData1[3].toString());
		                            totalInvoiceAmount += amt; // Correctly accumulate the amount
		                        } catch (NumberFormatException e) {
		                            amt = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(amt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "OTHER_DEDUCTIONS":
		                	 cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                	break;
		               

		                
		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		        
		        Row totalRow1 = sheet.createRow(rowNum++);
		        totalRow1.createCell(0).setCellValue("Total"); // Set the label for the total row

		        CellStyle totalRowStyle1 = sheet.getWorkbook().createCellStyle();
		        totalRowStyle1.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont11 = workbook.createFont();
		        boldFont11.setBold(true);
		        totalRowStyle1.setFont(boldFont11);

		        CellStyle centerStyle1 = workbook.createCellStyle();
		        centerStyle1.cloneStyleFrom(totalRowStyle1); // Use totalRowStyle as base (light green background)
		        centerStyle1.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle1.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary
		        
		        // Set total values in respective columns
//		        totalRow1.createCell(2).setCellValue(totalSize201); // Total Size 20
		        totalRow1.createCell(4).setCellValue(totalInvoiceAmount); // Total Size 40
//		        totalRow1.createCell(4).setCellValue(totalTEUs);   // Total TEUs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 4; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow1.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle1); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeader.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }
		        
		        
		        
		        
		        
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Line wise invoice Total"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		     // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "LINE NAME", "TOTAL INVOICE AMOUNT"
		        };

		        // Create a map to store total invoice amount for each line
		        Map<String, Double> lineInvoiceTotals = new HashMap<>();

		        // Calculate the total invoice amount for each line
		        importDetails.forEach(i -> {
		            String lineName = i[0] != null ? i[0].toString() : "Unknown"; // Line name
		            Double invoiceAmount = i[3] != null ? parseDoubleOrDefault(i[3].toString(), 0.0) : 0.0; // Invoice amount

		            // Add the invoice amount to the total for this line
		            lineInvoiceTotals.put(lineName, lineInvoiceTotals.getOrDefault(lineName, 0.0) + invoiceAmount);
		        });

		        // Apply header row styling and set values
		        Row headerRow1 = sheet.createRow(rowNum++);
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            Cell cell = headerRow1.createCell(i);
		            cell.setCellValue(columnsHeaderSummery[i]);

		            // Set cell style for header
		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);
		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		            headerFont.setColor(IndexedColors.WHITE.getIndex());
		            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Iterate over the map to create rows for each line and their total invoice amount
		        int srNo = 1;
		        for (Map.Entry<String, Double> entry : lineInvoiceTotals.entrySet()) {
		            String lineName = entry.getKey();
		            Double totalInvoiceAmount1 = entry.getValue();

		            // Create a new row for this line
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(lineName); // Line name
		            row.createCell(2).setCellValue(totalInvoiceAmount1); // Total invoice amount

		            // Apply number style for the invoice amount
		            CellStyle numberStyle = workbook.createCellStyle();
		            numberStyle.cloneStyleFrom(numberCellStyle);
		            row.getCell(2).setCellStyle(numberStyle);
		        }

		        // Calculate the grand total for all invoice amounts
		        Double grandTotalInvoiceAmount = lineInvoiceTotals.values().stream().mapToDouble(Double::doubleValue).sum();

		        // Create a CellStyle for the total row
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
		        totalRow.createCell(2).setCellValue(grandTotalInvoiceAmount); // Grand total invoice amount

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 2; i++) { // Iterate through relevant columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i);
		        }
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	public byte[] createExcelOfTEUSOnlyFCL(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty
			) throws DocumentException {
		
		    Workbook workbook = sheet.getWorkbook();
		    try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.findInvoiceDetailsFCLTeusOnly(companyId, branchId, startDate, endDate,billParty);
		        
		        
		      
		        
		         

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "Sr_no", "LINE NAME", "CONT_NO", "CONT_SIZE","Delivery Mode","TEUS"
		        	};
		        
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columnsHeader.length - 1));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("TEUS Only FCL(Loaded Destuff)" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("TEUS Only FCL(Loaded Destuff) Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("TEUS Only FCL(Loaded Destuff) Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;


		        BigDecimal totalInbondPkgs = BigDecimal.ZERO;
		        BigDecimal totalInbondWeight = BigDecimal.ZERO;
		        BigDecimal totalInbondAssetValue = BigDecimal.ZERO;
		        BigDecimal totalInbondDutyValue = BigDecimal.ZERO;
		        BigDecimal totalBalPkgs = BigDecimal.ZERO;
		        BigDecimal totalBalWeight = BigDecimal.ZERO;
		        BigDecimal totalBalAssetValue = BigDecimal.ZERO;
		        BigDecimal totalBalDutyValue = BigDecimal.ZERO;
		        BigDecimal totalAreaBalance = BigDecimal.ZERO;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        int totalSize20 = 0;
		        int totalSize40 = 0;
		        double totalTEUs = 0;
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		          
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);
		               
		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr_no":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "LINE NAME":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT_NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                 
		                    break;

		                case "CONT_SIZE":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                  
		                    break;

		                case "Delivery Mode":
		                    cell.setCellValue("");
		                    break;
		                    
		                case "TEUS":
		                    double teusForSize = 0;
		                    if (resultData1[2] != null) {
		                        // Assuming resultData1[2] holds the container size
		                        String containerSize = resultData1[2].toString();

		                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                            teusForSize = 1; // If container size is 20 or 22, set TEUS to 1
		                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                            teusForSize = 2; // If container size is 40 or 45, set TEUS to 2
		                        }
		                    }
		                    cell.setCellValue(teusForSize);
		                    totalTEUs += teusForSize; // Add to total TEUs
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                
		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		        
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary
		        
		        // Set total values in respective columns
		      
		        totalRow.createCell(5).setCellValue(totalTEUs);   // Total TEUs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 5; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeader.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }
		        
		        
		        
		        
		        
		        
		        
		        
		        
		       
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Line wise Teus Total"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        
		        String[] columnsHeaderSummery = {
		        	    "Sr No", "LINE NAME", "TEUS"
		        	};

		        	// Create a map to store total TEUs for each line
		        	Map<String, Double> lineTEUTotals = new HashMap<>();

		        	// Calculate the total TEUs for each line
		        	importDetails.forEach(i -> {
		        	    String lineName = i[0] != null ? i[0].toString() : "Unknown"; // Line name
		        	    String containerSize = i[2] != null ? i[2].toString() : "Unknown"; // Container size

		        	    // Calculate TEUs based on container size
		        	    double teus = 0;
		        	    if ("20".equals(containerSize) || "22".equals(containerSize)) {
		        	        teus = 1; // 1 TEU for size 20 or 22
		        	    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		        	        teus = 2; // 2 TEUs for size 40 or 45
		        	    }

		        	    // Add the TEUs to the totals for this line
		        	    lineTEUTotals.put(lineName, lineTEUTotals.getOrDefault(lineName, 0.0) + teus);
		        	});

		        	// Apply header row styling and set values
		        	Row headerRow1 = sheet.createRow(rowNum++);
		        	for (int i = 0; i < columnsHeaderSummery.length; i++) {
		        	    Cell cell = headerRow1.createCell(i);
		        	    cell.setCellValue(columnsHeaderSummery[i]);

		        	    // Set cell style for header
		        	    CellStyle headerStyle = workbook.createCellStyle();
		        	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
		        	    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		        	    Font headerFont = workbook.createFont();
		        	    headerFont.setBold(true);
		        	    headerFont.setFontHeightInPoints((short) 11);
		        	    headerStyle.setFont(headerFont);
		        	    headerStyle.setBorderBottom(BorderStyle.THIN);
		        	    headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        	    headerStyle.setBorderTop(BorderStyle.THIN);
		        	    headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        	    headerStyle.setBorderLeft(BorderStyle.THIN);
		        	    headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        	    headerStyle.setBorderRight(BorderStyle.THIN);
		        	    headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        	    headerFont.setColor(IndexedColors.WHITE.getIndex());
		        	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		        	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        	    cell.setCellStyle(headerStyle);
		        	    int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
		        	    sheet.setColumnWidth(i, headerWidth);
		        	}

		        	// Iterate over the map to create rows for each line and their total TEUs
		        	int srNo = 1;
		        	for (Map.Entry<String, Double> entry : lineTEUTotals.entrySet()) {
		        	    String lineName = entry.getKey();
		        	    Double totalTEUs1 = entry.getValue();

		        	    // Create a new row for this line
		        	    Row row = sheet.createRow(rowNum++);
		        	    row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		        	    row.createCell(1).setCellValue(lineName); // Line name
		        	    row.createCell(2).setCellValue(totalTEUs1); // Total TEUs for the line

		        	    // Apply number style for the TEUs
		        	    CellStyle numberStyle = workbook.createCellStyle();
		        	    numberStyle.cloneStyleFrom(numberCellStyle);
		        	    row.getCell(2).setCellStyle(numberStyle);
		        	}

		        	// Calculate the grand total for all TEUs
		        	Double grandTotalTEUs = lineTEUTotals.values().stream().mapToDouble(Double::doubleValue).sum();

		        	// Create a CellStyle for the total row
		        	CellStyle totalRowStyle1 = sheet.getWorkbook().createCellStyle();
		        	totalRowStyle1.cloneStyleFrom(numberCellStyle);
		        	totalRowStyle1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        	totalRowStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        	Font boldFont11 = workbook.createFont();
		        	boldFont11.setBold(true);
		        	totalRowStyle1.setFont(boldFont11);

		        	// Create the Total Row
		        	Row totalRow1 = sheet.createRow(rowNum++);
		        	totalRow1.createCell(0).setCellValue("Total"); // Label for the total row
		        	totalRow1.createCell(2).setCellValue(grandTotalTEUs); // Grand total TEUs

		        	// Apply styles for the Total Row
		        	for (int i = 0; i <= 2; i++) { // Iterate through relevant columns in the total row
		        	    Cell cell = totalRow1.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		        	    cell.setCellStyle(totalRowStyle1);
		        	}

		        	// Adjust column widths to fit the data (optional)
		        	for (int i = 0; i < columnsHeaderSummery.length; i++) {
		        	    sheet.autoSizeColumn(i);
		        	}


		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportMonthlyRevenueReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.findReceiptFCLReportDataOnly(companyId, branchId, startDate, endDate,billParty);
		        
		        
		       
		        
		        Sheet sheet = workbook.createSheet("RECEIPT Only FCL(Loaded_Destuff");
		        
		        
		        Sheet sheet1 = workbook.createSheet("TEUS Only FCL(Loaded Destuff)");
		        createExcelOfTEUSOnlyFCL(sheet1, companyId, branchId,username,companyname,branchname, startDate,endDate,billParty);

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "Sr_no", "LINE NAME", "RECEIPT NO", "INVOICE_DATE","WITH GST"
		        	};
		        
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columnsHeader.length - 1));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Import Monthly Revenue Report" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Import Monthly Revenue Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import Monthly Revenue Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;


		        
		        
		        int serialNo = 1; // Initialize serial number counter
		       
		        double totalInvoiceAmount = 0;
		        double totalTEUs = 0;
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr_no":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "LINE NAME":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "RECEIPT NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                  
		                    break;

		                case "INVOICE_DATE":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date format
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;


		                case "WITH GST":
		                    Double amt = 0.0;
		                    if (resultData1[3] != null) {
		                        try {
		                            amt = Double.parseDouble(resultData1[3].toString());
		                            totalInvoiceAmount += amt; // Correctly accumulate the amount
		                        } catch (NumberFormatException e) {
		                            amt = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(amt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "OTHER_DEDUCTIONS":
		                	 cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                	break;
		               

		                
		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		        
		        Row totalRow1 = sheet.createRow(rowNum++);
		        totalRow1.createCell(0).setCellValue("Total"); // Set the label for the total row

		        CellStyle totalRowStyle1 = sheet.getWorkbook().createCellStyle();
		        totalRowStyle1.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont11 = workbook.createFont();
		        boldFont11.setBold(true);
		        totalRowStyle1.setFont(boldFont11);

		        CellStyle centerStyle1 = workbook.createCellStyle();
		        centerStyle1.cloneStyleFrom(totalRowStyle1); // Use totalRowStyle as base (light green background)
		        centerStyle1.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle1.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary
		        
		        // Set total values in respective columns
//		        totalRow1.createCell(2).setCellValue(totalSize201); // Total Size 20
		        totalRow1.createCell(4).setCellValue(totalInvoiceAmount); // Total Size 40
//		        totalRow1.createCell(4).setCellValue(totalTEUs);   // Total TEUs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 4; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow1.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle1); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeader.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }
		        
		        
		        
		        
		        
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Line wise invoice Total"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		     // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "LINE NAME", "TOTAL INVOICE AMOUNT"
		        };

		        // Create a map to store total invoice amount for each line
		        Map<String, Double> lineInvoiceTotals = new HashMap<>();

		        // Calculate the total invoice amount for each line
		        importDetails.forEach(i -> {
		            String lineName = i[0] != null ? i[0].toString() : "Unknown"; // Line name
		            Double invoiceAmount = i[3] != null ? parseDoubleOrDefault(i[3].toString(), 0.0) : 0.0; // Invoice amount

		            // Add the invoice amount to the total for this line
		            lineInvoiceTotals.put(lineName, lineInvoiceTotals.getOrDefault(lineName, 0.0) + invoiceAmount);
		        });

		        // Apply header row styling and set values
		        Row headerRow1 = sheet.createRow(rowNum++);
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            Cell cell = headerRow1.createCell(i);
		            cell.setCellValue(columnsHeaderSummery[i]);

		            // Set cell style for header
		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);
		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		            headerFont.setColor(IndexedColors.WHITE.getIndex());
		            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Iterate over the map to create rows for each line and their total invoice amount
		        int srNo = 1;
		        for (Map.Entry<String, Double> entry : lineInvoiceTotals.entrySet()) {
		            String lineName = entry.getKey();
		            Double totalInvoiceAmount1 = entry.getValue();

		            // Create a new row for this line
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(lineName); // Line name
		            row.createCell(2).setCellValue(totalInvoiceAmount1); // Total invoice amount

		            // Apply number style for the invoice amount
		            CellStyle numberStyle = workbook.createCellStyle();
		            numberStyle.cloneStyleFrom(numberCellStyle);
		            row.getCell(2).setCellStyle(numberStyle);
		        }

		        // Calculate the grand total for all invoice amounts
		        Double grandTotalInvoiceAmount = lineInvoiceTotals.values().stream().mapToDouble(Double::doubleValue).sum();

		        // Create a CellStyle for the total row
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
		        totalRow.createCell(2).setCellValue(grandTotalInvoiceAmount); // Grand total invoice amount

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 2; i++) { // Iterate through relevant columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i);
		        }
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	public byte[] createExcelOfTEUSOnlyCLP(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty
			) throws DocumentException {
		
		    Workbook workbook = sheet.getWorkbook();
		    try ( ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.findExportCLPTeusReportOnly(companyId, branchId, startDate, endDate,billParty);
		        
		        
		       
		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		        	    "Sr_no", "LINE NAME", "CONT_NO", "CONT_SIZE","TEUS"
		        	};
		        
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columnsHeader.length - 1));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("TEUS Only CLP(Loaded Destuff)" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("TEUS Only CLP(Loaded Destuff) Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("TEUS Only CLP(Loaded Destuff) Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;

		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        double totalTEUs = 0;
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		          
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);
		               
		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr_no":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "LINE NAME":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT_NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                 
		                    break;

		                case "CONT_SIZE":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                  
		                    break;

		               
		                    
		                case "TEUS":
		                    double teusForSize = 0;
		                    if (resultData1[2] != null) {
		                        // Assuming resultData1[2] holds the container size
		                        String containerSize = resultData1[2].toString();

		                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                            teusForSize = 1; // If container size is 20 or 22, set TEUS to 1
		                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                            teusForSize = 2; // If container size is 40 or 45, set TEUS to 2
		                        }
		                    }
		                    cell.setCellValue(teusForSize);
		                    totalTEUs += teusForSize; // Add to total TEUs
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                
		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		        
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary
		        
		        // Set total values in respective columns
		      
		        totalRow.createCell(4).setCellValue(totalTEUs);   // Total TEUs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 4; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeader.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }
		        
		        
		        
		        
		        
		        
		        
		        
		        
		       
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Line wise Teus Total"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        
		        String[] columnsHeaderSummery = {
		        	    "Sr No", "LINE NAME", "TEUS"
		        	};

		        	// Create a map to store total TEUs for each line
		        	Map<String, Double> lineTEUTotals = new HashMap<>();

		        	// Calculate the total TEUs for each line
		        	importDetails.forEach(i -> {
		        	    String lineName = i[0] != null ? i[0].toString() : "Unknown"; // Line name
		        	    String containerSize = i[2] != null ? i[2].toString() : "Unknown"; // Container size

		        	    // Calculate TEUs based on container size
		        	    double teus = 0;
		        	    if ("20".equals(containerSize) || "22".equals(containerSize)) {
		        	        teus = 1; // 1 TEU for size 20 or 22
		        	    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		        	        teus = 2; // 2 TEUs for size 40 or 45
		        	    }

		        	    // Add the TEUs to the totals for this line
		        	    lineTEUTotals.put(lineName, lineTEUTotals.getOrDefault(lineName, 0.0) + teus);
		        	});

		        	// Apply header row styling and set values
		        	Row headerRow1 = sheet.createRow(rowNum++);
		        	for (int i = 0; i < columnsHeaderSummery.length; i++) {
		        	    Cell cell = headerRow1.createCell(i);
		        	    cell.setCellValue(columnsHeaderSummery[i]);

		        	    // Set cell style for header
		        	    CellStyle headerStyle = workbook.createCellStyle();
		        	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
		        	    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		        	    Font headerFont = workbook.createFont();
		        	    headerFont.setBold(true);
		        	    headerFont.setFontHeightInPoints((short) 11);
		        	    headerStyle.setFont(headerFont);
		        	    headerStyle.setBorderBottom(BorderStyle.THIN);
		        	    headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        	    headerStyle.setBorderTop(BorderStyle.THIN);
		        	    headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        	    headerStyle.setBorderLeft(BorderStyle.THIN);
		        	    headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        	    headerStyle.setBorderRight(BorderStyle.THIN);
		        	    headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        	    headerFont.setColor(IndexedColors.WHITE.getIndex());
		        	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		        	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        	    cell.setCellStyle(headerStyle);
		        	    int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
		        	    sheet.setColumnWidth(i, headerWidth);
		        	}

		        	// Iterate over the map to create rows for each line and their total TEUs
		        	int srNo = 1;
		        	for (Map.Entry<String, Double> entry : lineTEUTotals.entrySet()) {
		        	    String lineName = entry.getKey();
		        	    Double totalTEUs1 = entry.getValue();

		        	    // Create a new row for this line
		        	    Row row = sheet.createRow(rowNum++);
		        	    row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		        	    row.createCell(1).setCellValue(lineName); // Line name
		        	    row.createCell(2).setCellValue(totalTEUs1); // Total TEUs for the line

		        	    // Apply number style for the TEUs
		        	    CellStyle numberStyle = workbook.createCellStyle();
		        	    numberStyle.cloneStyleFrom(numberCellStyle);
		        	    row.getCell(2).setCellStyle(numberStyle);
		        	}

		        	// Calculate the grand total for all TEUs
		        	Double grandTotalTEUs = lineTEUTotals.values().stream().mapToDouble(Double::doubleValue).sum();

		        	// Create a CellStyle for the total row
		        	CellStyle totalRowStyle1 = sheet.getWorkbook().createCellStyle();
		        	totalRowStyle1.cloneStyleFrom(numberCellStyle);
		        	totalRowStyle1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        	totalRowStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        	Font boldFont11 = workbook.createFont();
		        	boldFont11.setBold(true);
		        	totalRowStyle1.setFont(boldFont11);

		        	// Create the Total Row
		        	Row totalRow1 = sheet.createRow(rowNum++);
		        	totalRow1.createCell(0).setCellValue("Total"); // Label for the total row
		        	totalRow1.createCell(2).setCellValue(grandTotalTEUs); // Grand total TEUs

		        	// Apply styles for the Total Row
		        	for (int i = 0; i <= 2; i++) { // Iterate through relevant columns in the total row
		        	    Cell cell = totalRow1.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		        	    cell.setCellStyle(totalRowStyle1);
		        	}

		        	// Adjust column widths to fit the data (optional)
		        	for (int i = 0; i < columnsHeaderSummery.length; i++) {
		        	    sheet.autoSizeColumn(i);
		        	}


		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	public byte[] createExcelReportOfTDSReportReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.findFinanceTransactions(companyId, branchId,startDate,endDate,
		        		billParty);
		        Sheet sheet = workbook.createSheet("TDS Report");

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		                "SR NO",
		                "INVOICE REF NO",
		                "TDS NO",
		                "INVOICE DATE",
		                "BILLING PARTY",
		                "CHA NAME",
		                "IMPORTER NAME",
		                "BASIC AMOUNT",
		                "GST",
		                "TOTAL INVOICE AMOUNT",
		                "OTHER DEDUCTION AMOUNT",
		                "EFT",
		                "CHEQUE",
		                "TDS",
		                "CASH",
		                "RTGS"
		        };




	
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("TDS Report" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("TDS Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("TDS Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        
		      
		        
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            String services =(String) resultData1[9];
		            double totalEFT = 0.00; // Initialize a variable to store the total amount
			        double totalCheque = 0.00; // Initialize a variable to store the total amount
			        double totalRTGS = 0.00; // Initialize a variable to store the total amount
			        double cashTotal = 0.00; // Initialize a variable to store the total amount
			        double totalRTGS1 = 0.00; // Initialize a variable to store the total amount
		          
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "INVOICE REF NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "TDS NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "INVOICE DATE":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "BILLING PARTY":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "CHA NAME":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "IMPORTER NAME":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "BASIC AMOUNT":
		                    Double basicAmount = parseDoubleOrDefault(resultData1[6], 0.00);
		                    cell.setCellValue(basicAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "GST":
		                    Double gst = parseDoubleOrDefault(resultData1[7], 0.00);
		                    cell.setCellValue(gst);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "TOTAL INVOICE AMOUNT":
		                    Double totalInvoiceAmount = parseDoubleOrDefault(resultData1[8], 0.00);
		                    cell.setCellValue(totalInvoiceAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "OTHER DEDUCTION AMOUNT":
		                    cell.setCellValue("0.0");
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "EFT":
		                	StringTokenizer st = new StringTokenizer(services, ",");

		                    while (st.hasMoreTokens()) {
		                        String serv = st.nextToken();
		                        String[] fields = serv.split("~");

		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();

		                            // Check if the payment mode is "CASH"
		                            if ("EFT".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                            	totalEFT += parseDoubleOrDefault(documentAmt, 0.00);
		                            }
		                        } 
		                        else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(totalEFT);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "CHEQUE":
		                	StringTokenizer stas1 = new StringTokenizer(services, ",");

		                    while (stas1.hasMoreTokens()) {
		                        String serv = stas1.nextToken();
		                        String[] fields = serv.split("~");

		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();

		                            // Check if the payment mode is "CASH"
		                            if ("CHEQUE".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                            	totalCheque += parseDoubleOrDefault(documentAmt, 0.00);
		                            }
		                        } else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(totalCheque);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
//
		                case "TDS":
		                    StringTokenizer stas11 = new StringTokenizer(services, ",");
		                    while (stas11.hasMoreTokens()) {
		                        String serv = stas11.nextToken();
		                        System.out.println("Current token                :" + serv);
		                        
		                        String[] fields = serv.split("~");
		                      
		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();
		                            
		                    
		                            if ("TDS".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                                totalRTGS += parseDoubleOrDefault(documentAmt, 0.00);
		                             
		                            }
		                        } else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(totalRTGS);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "CASH":
		                    StringTokenizer stas = new StringTokenizer(services, ",");
		                    

		                    while (stas.hasMoreTokens()) {
		                        String serv = stas.nextToken();
		                        String[] fields = serv.split("~");

		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();

		                            // Check if the payment mode is "CASH"
		                            if ("CASH".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                                cashTotal += parseDoubleOrDefault(documentAmt, 0.00);
		                            }
		                        } else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(cashTotal);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "RTGS":
		                	
		                	 StringTokenizer stas111 = new StringTokenizer(services, ",");
			                    while (stas111.hasMoreTokens()) {
			                        String serv = stas111.nextToken();
			                        String[] fields = serv.split("~");

			                        // Check if the split array has at least 5 elements
			                        if (fields.length > 2) {
			                            String paymentMode = fields[0].trim();
			                            String documentAmt = fields[1].trim();

			                            // Check if the payment mode is "CASH"
			                            if ("RTGS".equals(paymentMode)) {
			                                // Parse the document amount and add it to the total
			                            	totalRTGS1 += parseDoubleOrDefault(documentAmt, 0.00);
			                            }
			                        } else {
			                            // Handle the case where the fields array doesn't have enough elements
			                            System.out.println("Error: fields array does not contain enough elements.");
			                        }
			                    }

			                    // Set the total cash amount to the cell
			                    cell.setCellValue(totalRTGS1);
			                    cell.setCellStyle(numberCellStyle);
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       

		        
		        
		        
		        
		        
		       


		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 36 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	
	
	public byte[] createExcelReportOfDailyReceiptReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String sbNo,
	        String bookingNo,
	        String billParty,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

		        Calendar cal = Calendar.getInstance();

		     // Set startDate to 00:00 if the time component is not set
		     if (startDate != null) {
		            cal.setTime(startDate);
		            // If the time is at the default 00:00:00, reset it to 00:00:00
		            if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.SECOND) == 0 && cal.get(Calendar.MILLISECOND) == 0) {
		                cal.set(Calendar.HOUR_OF_DAY, 0);
		                cal.set(Calendar.MINUTE, 0);
		                cal.set(Calendar.SECOND, 0);
		                cal.set(Calendar.MILLISECOND, 0);
		                startDate = cal.getTime();
		            }
		        }

		        // Check if time component is not set (i.e., time is null or empty)
		        if (endDate != null) {
		            cal.setTime(endDate);
		            // If the time is at the default 23:59:59, reset it to 23:59:59
		            if (cal.get(Calendar.HOUR_OF_DAY) == 23 && cal.get(Calendar.MINUTE) == 59 && cal.get(Calendar.SECOND) == 59 && cal.get(Calendar.MILLISECOND) == 999) {
		                cal.set(Calendar.HOUR_OF_DAY, 23);
		                cal.set(Calendar.MINUTE, 59);
		                cal.set(Calendar.SECOND, 59);
		                cal.set(Calendar.MILLISECOND, 999);
		                endDate = cal.getTime();
		            }
		        }
		     
		        
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
		                + companyAddress.getAddress_3() + companyAddress.getCity();

		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;

		        
		        List<Object[]> importDetails = financeReportsRepo.findFinanceTransactions(companyId, branchId,startDate,endDate,
		        		billParty);
		        Sheet sheet = workbook.createSheet("Daily Receipt Report");

		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderTop(BorderStyle.THIN);
		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle.setBorderRight(BorderStyle.THIN);
		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        CellStyle dateCellStyle1 = workbook.createCellStyle();
		        CreationHelper createHelper1 = workbook.getCreationHelper();
		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        // Create numeric format for Excel
		        CellStyle numberCellStyle = workbook.createCellStyle();
		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderTop(BorderStyle.THIN);
		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        numberCellStyle.setBorderRight(BorderStyle.THIN);
		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

		        

		        String[] columnsHeader = {
		                "SR NO",
		                "REMARKS",
		                "INVOICE DEBIT NOTE NO",
		                "RECEIPT TYPE",
		                "VOUCHER DATE",
		                "LOGIN ID",
		                "VOUCHER_AMOUNT",
		                "CUSTOMER NAME",
		                "PAYMENT MODE",
		                "INSTRUMENT NO",
		                "INSTRUMENT DATE",
		                "TRANSACTION TYPE",
		                "TRASANCTION AMOUNT",
		                "REFERENCE NUMBER",
		                "AMOUNT",
		                "STATUS",
		                "DRAWEE BANK NAME",
		                "DRAWEE BANK BRANCH",
		                "ACCOUNT HOLDER",
		                "TAN NO",
		                "PAYING PARTY"
		        };




	
		        // Add Company Name (Centered)
		        Row companyRow = sheet.createRow(0);
		        Cell companyCell = companyRow.createCell(0);
		        companyCell.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle = workbook.createCellStyle();
		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont = workbook.createFont();
		        companyFont.setBold(true);
		        companyFont.setFontHeightInPoints((short)18);
		        companyStyle.setFont(companyFont);
		        companyCell.setCellStyle(companyStyle);
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		        // Add Branch Address (Centered)
		        Row branchRow = sheet.createRow(1);
		        Cell branchCell = branchRow.createCell(0);
		        branchCell.setCellValue(branchAdd);
		        CellStyle branchStyle = workbook.createCellStyle();
		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont = workbook.createFont();
		        branchFont.setFontHeightInPoints((short) 12);
		        branchStyle.setFont(branchFont);
		        branchCell.setCellStyle(branchStyle);
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

		        
		        Row branchRow1 = sheet.createRow(2);
		        Cell branchCell1 = branchRow1.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle1 = workbook.createCellStyle();
		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont1 = workbook.createFont();
		        branchFont1.setFontHeightInPoints((short) 12);
		        branchStyle1.setFont(branchFont1);
		        branchCell1.setCellStyle(branchStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Daily Receipt Report" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle = workbook.createCellStyle();
		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont = workbook.createFont();
		        reportTitleFont.setBold(true);
		        reportTitleFont.setFontHeightInPoints((short) 16);
		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle.setFont(reportTitleFont);
		        reportTitleCell.setCellStyle(reportTitleStyle);
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Daily Receipt Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Receipt Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
		       

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true); // Make font bold
		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle1.setFont(reportTitleFont1);

		        // Set the style to the cell
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex = 6; // Adjusted for the title rows above
		        Row headerRow = sheet.createRow(headerRowIndex);
		        
		        CellStyle borderStyle = workbook.createCellStyle();
		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle.setBorderBottom(BorderStyle.THIN);
		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderTop(BorderStyle.THIN);
		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderLeft(BorderStyle.THIN);
		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle.setBorderRight(BorderStyle.THIN);
		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);
		        boldFont.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(columnsHeader[i]);

		            CellStyle headerStyle = workbook.createCellStyle();
		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		            
		
			        
		            Font headerFont = workbook.createFont();
		            headerFont.setBold(true);
		            headerFont.setFontHeightInPoints((short) 11);

		            headerStyle.setFont(headerFont);
		            headerStyle.setBorderBottom(BorderStyle.THIN);
		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderTop(BorderStyle.THIN);
		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderLeft(BorderStyle.THIN);
		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		            headerStyle.setBorderRight(BorderStyle.THIN);
		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		            
		            
		            headerFont.setColor(IndexedColors.WHITE.getIndex());
					   
			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		        
		        

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        
		        
		        int serialNo = 1; // Initialize serial number counter

		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            String services =(String) resultData1[9];
		            double totalEFT = 0.00; // Initialize a variable to store the total amount
			        double totalCheque = 0.00; // Initialize a variable to store the total amount
			        double totalRTGS = 0.00; // Initialize a variable to store the total amount
			        double cashTotal = 0.00; // Initialize a variable to store the total amount
			        double totalRTGS1 = 0.00; // Initialize a variable to store the total amount
		          
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {

		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "REMARKS":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "INVOICE DEBIT NOTE NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "RECEIPT TYPE":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "VOUCHER DATE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "CUSTOMER NAME":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case  "PAYMENT MODE":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "INSTRUMENT NO":
		                    Double basicAmount = parseDoubleOrDefault(resultData1[6], 0.00);
		                    cell.setCellValue(basicAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "INSTRUMENT DATE":
		                    Double gst = parseDoubleOrDefault(resultData1[7], 0.00);
		                    cell.setCellValue(gst);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case  "TRANSACTION TYPE":
		                    Double totalInvoiceAmount = parseDoubleOrDefault(resultData1[8], 0.00);
		                    cell.setCellValue(totalInvoiceAmount);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "TRASANCTION AMOUNT":
		                    cell.setCellValue("0.0");
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "REFERENCE NUMBER":
		                	StringTokenizer st = new StringTokenizer(services, ",");
		                    while (st.hasMoreTokens()) {
		                        String serv = st.nextToken();
		                        String[] fields = serv.split("~");

		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();

		                            if ("EFT".equals(paymentMode)) {
		                            	totalEFT += parseDoubleOrDefault(documentAmt, 0.00);
		                            }
		                        } 
		                        else {
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(totalEFT);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "AMOUNT":
		                	StringTokenizer stas1 = new StringTokenizer(services, ",");

		                    while (stas1.hasMoreTokens()) {
		                        String serv = stas1.nextToken();
		                        String[] fields = serv.split("~");

		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();

		                            // Check if the payment mode is "CASH"
		                            if ("CHEQUE".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                            	totalCheque += parseDoubleOrDefault(documentAmt, 0.00);
		                            }
		                        } else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(totalCheque);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
//
		                case "STATUS":
		                    StringTokenizer stas11 = new StringTokenizer(services, ",");
		                    while (stas11.hasMoreTokens()) {
		                        String serv = stas11.nextToken();
		                        System.out.println("Current token                :" + serv);
		                        
		                        String[] fields = serv.split("~");
		                      
		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();
		                            
		                    
		                            if ("TDS".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                                totalRTGS += parseDoubleOrDefault(documentAmt, 0.00);
		                             
		                            }
		                        } else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(totalRTGS);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "DRAWEE BANK NAME":
		                    StringTokenizer stas = new StringTokenizer(services, ",");
		                    

		                    while (stas.hasMoreTokens()) {
		                        String serv = stas.nextToken();
		                        String[] fields = serv.split("~");

		                        // Check if the split array has at least 5 elements
		                        if (fields.length > 2) {
		                            String paymentMode = fields[0].trim();
		                            String documentAmt = fields[1].trim();

		                            // Check if the payment mode is "CASH"
		                            if ("CASH".equals(paymentMode)) {
		                                // Parse the document amount and add it to the total
		                                cashTotal += parseDoubleOrDefault(documentAmt, 0.00);
		                            }
		                        } else {
		                            // Handle the case where the fields array doesn't have enough elements
		                            System.out.println("Error: fields array does not contain enough elements.");
		                        }
		                    }

		                    // Set the total cash amount to the cell
		                    cell.setCellValue(cashTotal);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "DRAWEE BANK BRANCH":
		                	
		                	 StringTokenizer stas111 = new StringTokenizer(services, ",");
			                    while (stas111.hasMoreTokens()) {
			                        String serv = stas111.nextToken();
			                        String[] fields = serv.split("~");

			                        // Check if the split array has at least 5 elements
			                        if (fields.length > 2) {
			                            String paymentMode = fields[0].trim();
			                            String documentAmt = fields[1].trim();

			                            // Check if the payment mode is "CASH"
			                            if ("RTGS".equals(paymentMode)) {
			                                // Parse the document amount and add it to the total
			                            	totalRTGS1 += parseDoubleOrDefault(documentAmt, 0.00);
			                            }
			                        } else {
			                            // Handle the case where the fields array doesn't have enough elements
			                            System.out.println("Error: fields array does not contain enough elements.");
			                        }
			                    }

			                    // Set the total cash amount to the cell
			                    cell.setCellValue(totalRTGS1);
			                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
			                
		                    
		                case "ACCOUNT HOLDER" :
		                	cell.setCellValue("");
		                	break;
		                	
		                case "TAN NO" :
		                	cell.setCellValue("");
		                	break;
		                	
		                	
		                case "PAYING PARTY"  :
		                	cell.setCellValue("");
		                	break;
		                	
		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       

		        
		        
		        
		        
		        
		       


		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 36 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		
		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    }
		        
		        catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
}
