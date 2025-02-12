package com.cwms.service;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Couchbase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.entities.Party;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfexbondcrgEditRepository;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CommonReportsRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ExportOperationalReportRepository;
import com.cwms.repository.ImportReportsRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class ExportReportsServiceReports {

	
	@Autowired
	private ExportOperationalReportRepository exportOperationalReportRepo;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private CfExBondCrgRepository cfExBondCrgRepository;
	
	
	@Autowired
	private CfinbondcrgRepo cfinbondcrgRepo;

	@Autowired
	private com.cwms.repository.InbondCFRepositary InbondCFRepositary;
	
	@Autowired
	private CfexbondcrgEditRepository cfexbondcrgEditRepository;
	
	@Autowired
	private ImportReportsRepository importReportsRepository;
	
	@Autowired
	private CommonReportsRepo commonReportsRepo;
	
	
	 public List<Object[]> getAllExporter(String cid, String bid,String val) {
	        return exportOperationalReportRepo.getAllExporter(cid, bid,val);
	    }
	 
		public List<Object[]>getAllAccountHolder(String companyId, String branchId, String partyName) {
			return exportOperationalReportRepo.getAllAccountHolder(companyId, branchId, partyName);
		}
	 
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

	    // Normalize startDate to the beginning of the day (00:00:00.000)
//	    if (startDate != null) {
//	        cal.setTime(startDate);
//	        cal.set(Calendar.HOUR_OF_DAY, 0);
//	        cal.set(Calendar.MINUTE, 0);
//	        cal.set(Calendar.SECOND, 0);
//	        cal.set(Calendar.MILLISECOND, 0);
//	        startDate = cal.getTime();
//	    }
//
//	    // Normalize endDate to the end of the day (23:59:59.999)
//	    if (endDate != null) {
//	        cal.setTime(endDate);
//	        cal.set(Calendar.HOUR_OF_DAY, 23);
//	        cal.set(Calendar.MINUTE, 59);
//	        cal.set(Calendar.SECOND, 59);
//	        cal.set(Calendar.MILLISECOND, 999);
//	        endDate = cal.getTime();
//	    }
	    
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
	        case "Export Cargo Balance WareHouse Report":
	            importDetails = exportOperationalReportRepo.getCustomReport(
	                    companyId, branchId, endDate, sbNo, exporterName, acc, cha);
	            break;

	        case "Truck Wise Carting Report":
	            importDetails = exportOperationalReportRepo.findTruckWiseCartingReport(
	                    companyId, branchId, startDate, endDate, sbNo, exporterName, acc, cha);
	            break;
	              
	        case "Export SB Wise Container Out Report":
	            importDetails =  exportOperationalReportRepo.findExportGateOutBasedData(companyId, branchId,startDate, endDate,
		        		sbNo,bookingNo,exporterName,acc,cha);
	            break;
	            
	        case "Export Factory Stuff GateIn Report":
	            importDetails =  exportOperationalReportRepo.exportFactoryStuffGateInReport(companyId, branchId,startDate,endDate,sbNo,bookingNo,exporterName, acc,cha);
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
	            
	        case "TRANSPORTER WISE TUES REPORT":
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

	
	public byte[] createExcelReportOfExortCargoBalanceDetailedReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.getCustomReport(companyId, branchId, endDate,
		        		sbNo, exporterName,acc,cha);
		        
		  
		        
		        
//		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
//		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Cargo Balance WareHouse Report");

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
		        	    "Shipping Bill No",
		        	    "Shipping Bill Date",
		        	    "Cargo Description",
		        	    "CHA",
		        	    "Account Holder",
		        	    "Exporter",
		        	    "Dec Pkgs",
		        	    "Dec Wt",
		        	    "Carting Date",
		        	    "Bal Pkgs",
		        	    "Bal Wt",
		        	    "Bal Area",
		        	    "WH Location",
		        	    "Storage Space",
		        	    "No Of Days",
		        	    "POD",
		        	    "FOB"
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
		        reportTitleCell.setCellValue("Export Cargo Balance WareHouse Report" );

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
		        	 reportTitleCell1.setCellValue("Export Cargo Balance WareHouse Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Cargo Balance WareHouse Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "Shipping Bill No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Shipping Bill Date":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Account Holder":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Exporter":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

//		                case "Dec Pkgs":
//		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "0.00");
//		                    cell.setCellStyle(numberCellStyle);
//		                    break;
		                    
		                case "Dec Pkgs":
		                    // Parsing logic for Dec Pkgs as Double
		                    Double decPkgs = 0.00;
		                    if (resultData1[6] != null) {
		                        try {
		                            decPkgs = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            decPkgs = 0.00;
		                        }
		                    }
		                    cell.setCellValue(decPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

//		                case "Dec Wt":
//		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "0.00");
//		                    break;
		                    
		                    
		                case "Dec Wt":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double decWt = 0.00;
		                    if (resultData1[7] != null) {
		                        try {
		                            decWt = Double.parseDouble(resultData1[7].toString());
		                        } catch (NumberFormatException e) {
		                            decWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(decWt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;


		                case "Carting Date":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(resultData1[8].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

//		                case "Bal Pkgs":
//		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
//		                    break;
//
//		                case "Bal Wt":
//		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
//		                    break;
//
//		                case "Bal Area":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
//		                    break; 
		                    
		                case "Bal Pkgs":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double balPkgs = 0.00;
		                    if (resultData1[9] != null) {
		                        try {
		                            balPkgs = Double.parseDouble(resultData1[9].toString());
		                        } catch (NumberFormatException e) {
		                            balPkgs = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(balPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Bal Wt":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double balWt = 0.00;
		                    if (resultData1[10] != null) {
		                        try {
		                            balWt = Double.parseDouble(resultData1[10].toString());
		                        } catch (NumberFormatException e) {
		                            balWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(balWt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Bal Area":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double balArea = 0.00;
		                    if (resultData1[11] != null) {
		                        try {
		                            balArea = Double.parseDouble(resultData1[11].toString());
		                        } catch (NumberFormatException e) {
		                            balArea = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(balArea);
		                    cell.setCellStyle(numberCellStyle);
		                    break;


		                case "WH Location":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Storage Space":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "No Of Days":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "POD":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

//		                case "FOB":
//		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "0.00");
//		                    break;
		                    
		                case "FOB":
		                    // Parsing logic for FOB as Double
		                    Double fob = 0.00;
		                    if (resultData1[16] != null) {
		                        try {
		                            fob = Double.parseDouble(resultData1[16].toString());
		                        } catch (NumberFormatException e) {
		                            fob = 0.00;
		                        }
		                    }
		                    cell.setCellValue(fob);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
//		        Row emptyRow = sheet.createRow(rowNum++);
//		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//
//		     // Create a row for totals
//		        Row totalRow = sheet.createRow(rowNum++);
//
//		     // Create a CellStyle for the background color
//		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
//		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
//		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
//		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		        // Create a font for bold text
//		        Font boldFont1 = workbook.createFont();
//		        boldFont1.setBold(true);
//		        totalRowStyle.setFont(boldFont1);
//
//		        // Add "Total" label to the first cell and apply the style
//		        Cell totalLabelCell = totalRow.createCell(0);
//		        totalLabelCell.setCellValue("Total");
//		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style
//
//		        CellStyle centerStyle = workbook.createCellStyle();
//		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
//		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
//		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary
//
//		     
//		        for (int i = 0; i < columnsHeader.length; i++) {
//		            Cell totalCell = totalRow.createCell(i);
//		            totalCell.setCellStyle(numberCellStyle);
//		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
//		            
//		            switch (columnsHeader[i]) {
//		            
//		            case "Section 60(1)":
//		            	 totalCell.setCellValue("Total");
//		                 totalCell.setCellStyle(centerStyle); // Apply centered style
//		            
//                    break;
//		                case "Inbond Pkgs":
//		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
//		                    break;
//		                case "Inbond Weight":
//		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
//		                    break;
//		                case "Inbond Asset Value":
//		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
//		                    break;
//		                case "Inbond Duty Value":
//		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
//		                    break;
//		                case "Bal Pkgs":
//		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
//		                    break;
//		                case "Bal Wt":
//		                    totalCell.setCellValue(totalBalWeight.doubleValue());
//		                    break;
//		                case "Bal Asset Value":
//		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
//		                    break;
//		                case "Bal Duty Value":
//		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
//		                    break;
//		                case "Area Balance Area":
//		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
//		                    break;
//		                default:
//		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
//		                    break;
//		            }
//		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 36 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  27 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        sheet.setColumnWidth(20,14 * 306); 
		        sheet.setColumnWidth(21, 36 * 306);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfExportContainerOutBasedReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findExportGateOutBasedData(companyId, branchId,startDate, endDate,
		        		sbNo,bookingNo,exporterName,acc,cha);
		        
		  
		        
		        
//		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
//		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export SB Wise Container Out Report");

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
		        	    "SB No",
		        	    "SB Date",
		        	    "Pack",
		        	    "Stuff Pack",
		        	    "Pack Type",
		        	    "MCIPCIN",
		        	    "Cont No",
		        	    "Cont Size",
		        	    "Stuff Order Date",
		        	    "Seal No 1",
		        	    "Seal No 2",
		        	    "SF Status",
		        	    "ASR Error",
		        	    "Officer Approve Status",
		        	    "Out Date"
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
		        reportTitleCell.setCellValue("Export SB Wise Container Out Report" );

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
		        	 reportTitleCell1.setCellValue("Export SB Wise Container Out Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export SB Wise Container Out Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "SB No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "SB Date":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Pack":
		                    Double pack = 0.00;
		                    if (resultData1[2] != null) {
		                        try {
		                            pack = Double.parseDouble(resultData1[2].toString());
		                        } catch (NumberFormatException e) {
		                            pack = 0.00;
		                        }
		                    }
		                    cell.setCellValue(pack);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Stuff Pack":
		                    Double stuffPack = 0.00;
		                    if (resultData1[3] != null) {
		                        try {
		                            stuffPack = Double.parseDouble(resultData1[3].toString());
		                        } catch (NumberFormatException e) {
		                            stuffPack = 0.00;
		                        }
		                    }
		                    cell.setCellValue(stuffPack);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Pack Type":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "MCIPCIN":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Stuff Order Date":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(resultData1[8].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Seal No 1":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Seal No 2":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "SF Status":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "ASR Error":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Officer Approve Status":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Out Date":
		                    if (resultData1[14] != null) {
		                        cell.setCellValue(resultData1[14].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }


		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
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
	
	
	
	
	public byte[] createExcelReportOCartingPendancydReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findCartingPendancyData(companyId, branchId, endDate,
		        		sbNo,bookingNo, exporterName,acc,cha);
		        
		  
		        
		        
//		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
//		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Vehical Carting Pendency Report");

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
		        	    "Sr.No.",
		        	    "Shipping Bill No",
		        	    "Shipping Bill Date",
		        	    "EXPORTER",
		        	    "CHA",
		        	    "Account Holder",
		        	    "Truck No",
		        	    "Gate In Date",
		        	    "Cargo Description",
		        	    "SB Pkgs",
		        	    "SB WT",
		        	    "PKG WT",
		        	    "JO PKGS",
		        	    "JO WT",
		        	    "Pkg Type",
		        	    "JO NO"
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
		        reportTitleCell.setCellValue("Export Vehical Carting Pendency Report" );

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
//		        if(formattedStartDate.equals("N/A"))
//		        {
		        	 reportTitleCell1.setCellValue("Export Vehical Carting Pendency Report As On Date : " + formattedEndDate);
//		        }
//		        else 
//		        {
//		        	 reportTitleCell1.setCellValue("Export Vehical Carting Pendency Report From : " + formattedStartDate + " to " + formattedEndDate);
//		        }
//		       

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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {
		                case "Sr.No.":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "Shipping Bill No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Shipping Bill Date":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "EXPORTER":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Account Holder":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Truck No":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Gate In Date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

//		                case "SB Pkgs":
//		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "0");
//		                    break;
//
//		                case "SB WT":
//		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "0.00");
//		                    break;
//
//		                case "PKG WT":
//		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "0.00");
//		                    break;
//
//		                case "JO PKGS":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "0");
//		                    break;
//
//		                case "JO WT":
//		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "0.00");
//		                    break;

		                case "SB Pkgs":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0
		                    Double sbPkgs = 0.00;
		                    if (resultData1[8] != null) {
		                        try {
		                            sbPkgs = Double.parseDouble(resultData1[8].toString());
		                        } catch (NumberFormatException e) {
		                            sbPkgs = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(sbPkgs);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "SB WT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double sbWt = 0.00;
		                    if (resultData1[9] != null) {
		                        try {
		                            sbWt = Double.parseDouble(resultData1[9].toString());
		                        } catch (NumberFormatException e) {
		                            sbWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(sbWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "PKG WT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double pkgWt = 0.00;
		                    if (resultData1[10] != null) {
		                        try {
		                            pkgWt = Double.parseDouble(resultData1[10].toString());
		                        } catch (NumberFormatException e) {
		                            pkgWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(pkgWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "JO PKGS":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0
		                    Double joPkgs = 0.00;
		                    if (resultData1[11] != null) {
		                        try {
		                            joPkgs = Double.parseDouble(resultData1[11].toString());
		                        } catch (NumberFormatException e) {
		                            joPkgs = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(joPkgs);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "JO WT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double joWt = 0.00;
		                    if (resultData1[12] != null) {
		                        try {
		                            joWt = Double.parseDouble(resultData1[12].toString());
		                        } catch (NumberFormatException e) {
		                            joWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(joWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                    
		                case "Pkg Type":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "JO NO":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       


		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 36 * 306); 
		        
		        sheet.setColumnWidth(4, 36 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 36 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        sheet.setColumnWidth(20,14 * 306); 
		        sheet.setColumnWidth(21, 36 * 306);
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
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOFTruckWiseDetailedReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findTruckWiseCartingReport(companyId, branchId, startDate, endDate,
		        		sbNo, exporterName,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Truck Wise Carting Report");

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
		        	    "ON A/C", 
		        	    "Carting Date", 
		        	    "Truck No", 
		        	    "Truck Qty", 
		        	    "Truck Wt", 
		        	    "S.BILL NO", 
		        	    "S.B DATE", 
		        	    "SB QTY", 
		        	    "SB WEIGHT", 
		        	    "AREA", 
		        	    "AREA TYPE", 
		        	    "SHIPPER", 
		        	    "CHA", 
		        	    "Cargo Description", 
		        	    "Pod", 
		        	    "Carting Remark"
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
		        reportTitleCell.setCellValue("Truck Wise Carting Report" );

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
		        	 reportTitleCell1.setCellValue("Truck Wise Carting Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Truck Wise Carting Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "ON A/C":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Carting Date":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Truck No":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

//		                case "Truck Qty":
//		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
//		                    break;
//
//		                case "Truck Wt":
//		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
//		                    break;
		                    
		                case "Truck Qty":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double truckQty = 0.00;
		                    if (resultData1[3] != null) {
		                        try {
		                            truckQty = Double.parseDouble(resultData1[3].toString());
		                        } catch (NumberFormatException e) {
		                            truckQty = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(truckQty);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Truck Wt":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double truckWt = 0.00;
		                    if (resultData1[4] != null) {
		                        try {
		                            truckWt = Double.parseDouble(resultData1[4].toString());
		                        } catch (NumberFormatException e) {
		                            truckWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(truckWt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;


		                case "S.BILL NO":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "S.B DATE":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

//		                case "SB QTY":
//		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
//		                    break;
//
//		                case "SB WEIGHT":
//		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
//		                    break;
//
//		                case "AREA":
//		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
//		                    break;
		                    
		                case "SB QTY":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double sbQty = 0.00;
		                    if (resultData1[7] != null) {
		                        try {
		                            sbQty = Double.parseDouble(resultData1[7].toString());
		                        } catch (NumberFormatException e) {
		                            sbQty = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(sbQty);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "SB WEIGHT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double sbWeight = 0.00;
		                    if (resultData1[8] != null) {
		                        try {
		                            sbWeight = Double.parseDouble(resultData1[8].toString());
		                        } catch (NumberFormatException e) {
		                            sbWeight = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(sbWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "AREA":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double area = 0.00;
		                    if (resultData1[9] != null) {
		                        try {
		                            area = Double.parseDouble(resultData1[9].toString());
		                        } catch (NumberFormatException e) {
		                            area = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(area);
		                    cell.setCellStyle(numberCellStyle);
		                    break;


		                case "AREA TYPE":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "SHIPPER":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Pod":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Carting Remark":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }


		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 45 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 27 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfFactoryStuffGateInReport(String companyId,
		    String branchId, String username, String type, String companyname,
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
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.exportFactoryStuffGateInReport(companyId, branchId,startDate,endDate,sbNo,bookingNo,exporterName, acc,cha);
		        
		        Sheet sheet = workbook.createSheet("Export Factory Stuff GateIn Report");

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
		        	    "SR NO", "Mode", "Work Order No.", "Work Order Date", "Gate In Date", "Vehicle", 
		        	    "Container No", "Size", "Type", "Cargo Wt.", "Tare Wt.", "Account Holder", 
		        	    "CHA", "Line", "Sr. No Buffer", "Sr. No Factory", "Gate In Condition", 
		        	    "Gate Out Date", "Shipping Bill No", "Shipping Bill Date", "No Of Packages", 
		        	    "Weight", "Vessel", "POD", "POL", "Agent Seal", "Custom Seal", "Gate Out Condition"
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 9));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Factory Stuff GateIn Report" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 9));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Factory Stuff GateIn Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Factory Stuff GateIn Report From : " + formattedStartDate + " to " + formattedEndDate);
		        }
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header

		                switch (columnsHeader[i]) {
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Incrementing serial number
		                    break;

		                case "Mode":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Work Order No.":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Work Order Date":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Gate In Date":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Vehicle":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Container No":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Type":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Cargo Wt.":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(Double.parseDouble(resultData1[8].toString()));
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "Tare Wt.":
		                    if (resultData1[9] != null) {
		                        cell.setCellValue(Double.parseDouble(resultData1[9].toString()));
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "Account Holder":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Line":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Sr. No Buffer":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Sr. No Factory":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Gate In Condition":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Gate Out Date":
		                    if (resultData1[16] != null) {
		                        cell.setCellValue(resultData1[16].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Shipping Bill No":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Shipping Bill Date":
		                    if (resultData1[18] != null) {
		                        cell.setCellValue(resultData1[18].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "No Of Packages":
		                    if (resultData1[19] != null) {
		                        cell.setCellValue(Integer.parseInt(resultData1[19].toString()));
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "Weight":
		                    if (resultData1[20] != null) {
		                        cell.setCellValue(Double.parseDouble(resultData1[20].toString()));
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "Vessel":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "POD":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "POL":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                case "Agent Seal":
		                    cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                    break;

		                case "Custom Seal":
		                    cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                    break;

		                case "Gate Out Condition":
		                    cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined or extra columns
		                    break;
		            }


		            }
		        }
		      
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 45 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 45 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20, 18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306); 
		        sheet.setColumnWidth(22, 18 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306); 
		        sheet.setColumnWidth(26, 18 * 306); 
		        sheet.setColumnWidth(27, 18 * 306); 
		        
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfMovementPendencyReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.getContainerMovements(companyId, branchId, endDate,
		        		sbNo,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Container Movement Pendency Report");

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
		        	    "SR. NO.",
		        	    "CONT NO",
		        	    "CONT SIZE",
		        	    "LINE",
		        	    "SHIPPING BILL",
		        	    "CHA",
		        	    "EXPORTER",
		        	    "CARGO DETAILS",
		        	    "STUFF ORDER DATE",
		        	    "MOVEMENT ORDER DATE",
		        	    "CUTOFF DATE",
		        	    "VCN_NO",
		        	    "VESSEL NAME",
		        	    "NATURE",
		        	    "Terminal"
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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,7));

		        
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
		        reportTitleCell.setCellValue("Export Container Movement Pendency Report" );

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
//		        if(formattedStartDate.equals("N/A"))
//		        {
		        	 reportTitleCell1.setCellValue("Export Container Movement Pendency Report As On Date : " + formattedEndDate);
//		        }
//		        else 
//		        {
//		        	 reportTitleCell1.setCellValue("Export Container Movement Pendency Report From : " + formattedStartDate + " to " + formattedEndDate);
//		        }
		       

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
		                case "SR. NO.":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "CONT NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT SIZE":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "LINE":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "SHIPPING BILL":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "EXPORTER":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "CARGO DETAILS":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "STUFF ORDER DATE":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "MOVEMENT ORDER DATE":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(resultData1[8].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "CUTOFF DATE":
		                    if (resultData1[9] != null) {
		                        cell.setCellValue(resultData1[9].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "VCN_NO":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "VESSEL NAME":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "NATURE":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Terminal":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
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
		        sheet.setColumnWidth(3, 45 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 45 * 306); 
		        
		        sheet.setColumnWidth(6, 45 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
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
		        sheet.setColumnWidth(19, 36 * 306); 
		        sheet.setColumnWidth(20,36 * 306); 
		        sheet.setColumnWidth(21, 36 * 306);
		        sheet.setColumnWidth(22, 18 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 36 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		        sheet.setColumnWidth(28, 18 * 306);
		        sheet.setColumnWidth(29, 36 * 306); 
		        sheet.setColumnWidth(30, 18 * 306); 
		        
		        sheet.setColumnWidth(31, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	public byte[] createExcelReportOfCargoBackToTown(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.getCargoBackToTownDetails(companyId, branchId,startDate, endDate,
		        		sbNo,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Back To Town Cargo Report");

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
		                               "TRUCK NO",
		                               "SHIPPING BILL NO",
		                               "AGENT NAME",
		                               "CHA NAME",
		                               "OUT DATE",
		                               "PACKAGES",
		                               "WEIGHT"
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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0,7));

		        
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
		        reportTitleCell.setCellValue("Back To Town Cargo Report" );

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
		        	 reportTitleCell1.setCellValue("Back To Town Cargo Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Back To Town Cargo Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "TRUCK NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "SHIPPING BILL NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "AGENT NAME":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "CHA NAME":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "OUT DATE":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle); // Assuming dateCellStyle is defined for formatting dates
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

//		                case "PACKAGES":
//		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
//		                    break;
//
//		                case "WEIGHT":
//		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
//		                    break;
		                    
		                case "PACKAGES":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double packages = 0.0;
		                    if (resultData1[5] != null) {
		                        try {
		                            packages = Double.parseDouble(resultData1[5].toString());
		                        } catch (NumberFormatException e) {
		                            packages = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(packages);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "WEIGHT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double weight = 0.0;
		                    if (resultData1[6] != null) {
		                        try {
		                            weight = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            weight = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(weight);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
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
		        sheet.setColumnWidth(3, 45 * 306); 
		        
		        sheet.setColumnWidth(4, 45 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportFclDestuffBalanceReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = importReportsRepository.getDataForFCLDestuffBalanceReport(companyId, branchId, endDate,
		        		sbNo, bookingNo, exporterName,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import FCL Destuff Balance Report");

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
		        	    "Sr No", "Container No", "Container Size","Container Type", "IGM No", "ITEM No", "BL NO", 
		        	    "No Of Packages", "Importer Name", "Crago Description", 
		        	     "Vessel Name", "VOY No", "Package Type", 
		        	    "IGM Date", "Gate In Date", "Shipping Agent Name", "Console Agent", 
		        	    "No Of Packages 1", "Cargo Delv Pack", "Balance Packages", 
		        	    "New Wt", "Cargo Delv Wt", "Balance Wt"
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
		        reportTitleCell.setCellValue("Import FCL Destuff Balance Report" );

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
		        	 reportTitleCell1.setCellValue("Import FCL Destuff Balance Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import FCL Destuff Balance Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "Container No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Container Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Container Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "IGM No":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "ITEM No":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "BL NO":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "No Of Packages":
		                    cell.setCellValue(resultData1[6] != null ? Integer.parseInt(resultData1[6].toString()) : 0);
		                    break;

		                case "Importer Name":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "VOY No":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Package Type":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "IGM Date":
		                    if (resultData1[12] != null) {
		                        cell.setCellValue(resultData1[12].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Gate In Date":
		                    if (resultData1[13] != null) {
		                        cell.setCellValue(resultData1[13].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Shipping Agent Name":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Console Agent":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "No Of Packages 1":
		                    cell.setCellValue(resultData1[16] != null ? Integer.parseInt(resultData1[16].toString()) : 0);
		                    break;

		                case "Cargo Delv Pack":
		                    cell.setCellValue(resultData1[17] != null ? Integer.parseInt(resultData1[17].toString()) : 0);
		                    break;

		                case "Balance Packages":
		                    cell.setCellValue(resultData1[18] != null ? Integer.parseInt(resultData1[18].toString()) : 0);
		                    break;

		                case "Net Wt":
		                    cell.setCellValue(resultData1[19] != null ? Double.parseDouble(resultData1[19].toString()) : 0.0);
		                    break;

		                case "Cargo Delv Wt":
		                    cell.setCellValue(resultData1[20] != null ? Double.parseDouble(resultData1[20].toString()) : 0.0);
		                    break;

		                case "Balance Wt":
		                    cell.setCellValue(resultData1[21] != null ? Double.parseDouble(resultData1[21].toString()) : 0.0);
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		              


		            }
		        }


		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 45 * 306); 
		        sheet.setColumnWidth(9, 45 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 45 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306); 
		        
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
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfScanContainerReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = importReportsRepository.scanContainerReport(companyId, branchId,startDate, endDate,
		        		sbNo, bookingNo, exporterName,acc,cha);
		        
		        
		        List<Object[]> importDetails1 = importReportsRepository.scanContainerReport1(companyId, branchId, endDate,
		        		sbNo, bookingNo, exporterName,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails1);
		        
		        Sheet sheet = workbook.createSheet("Scan Container Report");

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
		        	    "SR No", "IGM_NO", "ITEM_NO", "BL_NO", "CONT_NO", "CONT_SIZE", 
		        	    "CONT_TYPE", "GATE_IN DATE", "SCAN_OUT_DATE", "SCAN_IN_DATE", "IMPORTER_NAME"
		        	};
		        
		        
		        String[] columnsHeader1 = {
		        	    "SR No", "IGM_NO", "ITEM_NO", "BOE_NO", "CON_NO", "CON_SIZE", 
		        	    "CON_TYPE", "GATE_IN DATE", "IMPORTER_NAME"
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
		        reportTitleCell.setCellValue("Scan Container Report" );

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
//		        if(formattedStartDate.equals("N/A"))
//		        {
		        	 reportTitleCell1.setCellValue("Scan Container Report As On Date : " + formattedEndDate);
//		        }
//		        else 
//		        {
//		        	 reportTitleCell1.setCellValue("Scan Container Report From : " + formattedStartDate + " to " + formattedEndDate);
//		        }
//		       

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
		        
		        
		        
		        int rowNum = headerRowIndex + 1;

		     // First Table - Populating importDetails
		     int serialNo = 1; // Initialize serial number counter
		     for (Object[] resultData1 : importDetails) {
		         Row dataRow = sheet.createRow(rowNum++);
		         int cellNum = 0;

		         // Populate data for the first table
		         for (int i = 0; i < columnsHeader.length; i++) {
		             Cell cell = dataRow.createCell(i);
		             cell.setCellStyle(borderStyle);

		             // Switch case to handle each column header
		             switch (columnsHeader[i]) {
		                 case "SR No":
		                     cell.setCellValue(serialNo++); // Increment serial number
		                     break;

		                 case "IGM_NO":
		                     cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                     break;

		                 case "ITEM_NO":
		                     cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                     break;

		                 case "BOE_NO":
		                     cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                     break;

		                 case "CONT_NO":
		                     cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                     break;

		                 case "CONT_SIZE":
		                     cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                     break;

		                 case "CONT_TYPE":
		                     cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                     break;

		                 case "GATE_IN DATE":
		                     if (resultData1[6] != null) {
		                         cell.setCellValue(resultData1[6].toString());
		                         cell.setCellStyle(dateCellStyle); // Apply date formatting
		                     } else {
		                         cell.setBlank();
		                         cell.setCellStyle(dateCellStyle);
		                     }
		                     break;

		                 case "SCAN_OUT_DATE":
		                     if (resultData1[7] != null) {
		                         cell.setCellValue(resultData1[7].toString());
		                         cell.setCellStyle(dateCellStyle); // Apply date formatting
		                     } else {
		                         cell.setBlank();
		                         cell.setCellStyle(dateCellStyle);
		                     }
		                     break;

		                 case "SCAN_IN_DATE":
		                     if (resultData1[8] != null) {
		                         cell.setCellValue(resultData1[8].toString());
		                         cell.setCellStyle(dateCellStyle); // Apply date formatting
		                     } else {
		                         cell.setBlank();
		                         cell.setCellStyle(dateCellStyle);
		                     }
		                     break;

		                 case "IMPORTER_NAME":
		                     cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                     break;

		                 default:
		                     cell.setCellValue(""); // Handle undefined columns
		                     break;
		             }
		         }
		     }

		     // Insert an empty row between the two tables
		    
int newRow = rowNum++;

Row emptyRow = sheet.createRow(newRow+2);
//emptyRow.createCell(0).setCellValue("Containers are in Scan but balance to Scan Gate Out"); // Leave the row empty for gap
  
Cell cell = emptyRow.createCell(0);
cell.setCellValue("Containers are in Scan but balance to Scan Gate Out");

// Create a new font and set the size and bold style
XSSFFont font = (XSSFFont) workbook.createFont();
font.setFontHeightInPoints((short) 12); // Set font size to 14
font.setBold(true); // Make the font bold

// Apply the font to the cell style
CellStyle cellStyle = workbook.createCellStyle();
cellStyle.setFont(font);

// Set the style to the cell
cell.setCellStyle(cellStyle);

		     Row headerRow1 = sheet.createRow(newRow+4);
		          
		     
		     for (int i = 0; i < columnsHeader1.length; i++) {
		            Cell cell1 = headerRow1.createCell(i);
		            cell1.setCellValue(columnsHeader1[i]);

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

		            cell1.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader1[i].length() * 360 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }
		     
		     // Second Table - Populating importDetails1
		     int serialNo1 = 1; // Initialize serial number counter
		     for (Object[] resultData1 : importDetails1) {
		         Row dataRow = sheet.createRow(rowNum++);
		         int cellNum = 0;
		         // Populate data for the second table
		         for (int i = 0; i < columnsHeader1.length; i++) {
		             
//		             Cell cell1 = headerRow1.createCell(i);
//		             cell1.setCellValue(columnsHeader1[i]);
		        	 Cell cell1 = dataRow.createCell(i);
		       
		             cell1.setCellStyle(borderStyle);

		             // Switch case to handle each column header for importDetails1
		             switch (columnsHeader1[i]) {
		                 case "SR No":
		                     cell1.setCellValue(serialNo1++); // Increment serial number
		                     break;

		                 case "IGM_NO":
		                     cell1.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "Null");
		                     break;

		                 case "ITEM_NO":
		                     cell1.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                     break;

		                 case "BOE_NO":
		                     cell1.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                     break;

		                 case "CON_NO":
		                     cell1.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                     break;

		                 case "CON_SIZE":
		                     cell1.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                     break;

		                 case "CON_TYPE":
		                     cell1.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                     break;

		                 case "GATE_IN DATE":
		                     if (resultData1[6] != null) {
		                         cell1.setCellValue(resultData1[6].toString());
		                         cell1.setCellStyle(dateCellStyle); // Apply date formatting
		                     } else {
		                         cell1.setBlank();
		                         cell1.setCellStyle(dateCellStyle);
		                     }
		                     break;

		                 case "IMPORTER_NAME":
		                     cell1.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                     break;

		                 default:
		                     cell1.setCellValue(""); // Handle undefined columns
		                     break;
		             }
		         }
		     }

		       
		   
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfExportStuffingEquipmentsReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.getEquipmentActivityDetails(companyId, branchId);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
//		        List<Object[]> newlist = new ArrayList<>();
//		        importDetails.forEach(i -> {
//		            System.out.println(Arrays.toString(i)); // Print the element
//		            newlist.add(i);                         // Add to the new list
//		        });
		        
		        List<String> concatenatedValues = new ArrayList<>();
		        
		       
               

//               
//		     importDetails.forEach(i -> {
//		         // Check if index 7 exists in the element to avoid IndexOutOfBoundsException
//		         if (i.length > 7) {
//		             concatenatedValues.add(i[7].toString());  // Add the value at index 7 as a string
//		         }
//		     });
//               
//		     String result = String.join(",", concatenatedValues);
               
		    
		        
		        Sheet sheet = workbook.createSheet("Export Stuffing Equipments Report");

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

		        
		        Vector<String> dynamic = new Vector<>();
		        
		        Map<String, Integer> containerValueCount = new HashMap<>(); // Map to store container count

		        String[] columnsHeader = {
		        	    "Sr No", "CONT NO", "CONT SIZE", "Stuffing JOB ORDER DATE", "CARGO COMMODITY", 
		        	    "AGENT NAME", "STUFFED WT", "TARE WT" 
		        	};

		        
		        importDetails.forEach(i -> {
		    	    // Check if index 7 exists in the element to avoid IndexOutOfBoundsException
		    	    if (i.length > 0) {
		    	        String element = i[7].toString();
		    	        
		    	        String container = i[0].toString(); // Get the container value from i[0]
		    	        
		    	        String sb = i[8].toString();
		    	        // Split the element by comma
		    	        String[] values = element.split(",");
		    	        
		    	      
		    	        // Check each value for duplicates before adding to the Vector
		    	        for (String value : values) {
		    	            value = value.trim(); // Remove extra spaces around the value
		    	            
		    	            String mainValue = container+","+sb+","+value ;
		    	            
		    	            containerValueCount.put(mainValue, containerValueCount.getOrDefault(mainValue, 0) + 1);
		    	            
		    	            if (!dynamic.contains(value)) {
		    	                dynamic.add(value);
		    	            }
		    	        }
		    	    }
		    	});
		        
		        containerValueCount.forEach((container, count) -> {
		            System.out.println("Container: " + container + " | Count of values: " + count);
		        });


		     // Join the concatenated values with a comma separator
		     String result = String.join(",", dynamic);

		     System.out.println(result);

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
//		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		     // Merge cells for the company name (if needed)
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		        
		    
		        
		        
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
//		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6)); // Merge row 1
		   
		        
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
//		         Merge the cells across the first 7 columns to center the title
		        
		        // Merge cells for additional branch details (row 2)
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
		   
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Stuffing Equipments Report" );

//		         Merge the cells across the first 7 columns to center the title
		        
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
		        
//		         Merge the cells across the first 7 columns to center the title
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6)); // Merge rows (3, 3) and columns (0 to 6)
//		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Stuffing Equipments Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Stuffing Equipments Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		     
		        
		      

		       
		  System.out.println("lenght 4566 :"+ combinedHeaders.length);
		        
		
			  
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		         
		            int cellNum = 0; 
		            
		            String container = resultData1[0].toString(); // Container value
		            String sb = resultData1[8].toString(); // sb value
		            
		            String mainkey= null;
		            
		          	for (String a : dynamic)
                	{
                		 mainkey = container+","+sb+","+a ;
                		
 
                	}

		            // Variable to hold the dynamic value (based on your data)
		            String value = ""; // You will populate this dynamically as needed
		            
		            for (int i = 0; i < combinedHeaders.length; i++) {
		            
		                Cell cell = dataRow.createCell(i);
		                
		                cell.setCellStyle(borderStyle);
		                
		                switch (combinedHeaders[i]) {
		                
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "CONT NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT SIZE":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Stuffing JOB ORDER DATE":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "CARGO COMMODITY":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "AGENT NAME":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

//		                case "STUFFED WT":
//		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
//		                    break;
//
//		                case "TARE WT":
//		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
//		                    break;

		                case "STUFFED WT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double stuffedWt = 0.00;
		                    if (resultData1[5] != null) {
		                        try {
		                            stuffedWt = Double.parseDouble(resultData1[5].toString());
		                        } catch (NumberFormatException e) {
		                            stuffedWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(stuffedWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "TARE WT":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double tareWt = 0.00;
		                    if (resultData1[6] != null) {
		                        try {
		                            tareWt = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            tareWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(tareWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                default:
		                			                    // Generate the key from container, sb, and header (assuming header relates to value)
		                    String mainValue = container + "," + sb + "," + combinedHeaders[i];
//		                    
//		                    // Get the count from containerValueCount map (default to 0 if not found)
//		                    
//		                    
//		                   
//		                    
		                    Integer count = containerValueCount.getOrDefault(mainValue, 0);
		                	cell.setCellValue(count);
		                   
		                    break;
		            }

		        }
		        }

		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 45 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(13, 45 * 306); // Set width for "Importer" (40 characters wide)
		        
		        
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
	
	
	
	
	
	
	
	public byte[] createExcelReportOfTransporterWiseTuesReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.getTransporterWiseTuesReport(companyId, branchId, startDate, endDate);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("TRANSPORTER WISE TEUS REPORT");

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
		        	    "Sr_no", "TRANSPORTER_NAME", "SIZE_20", "SIZE_40", "TEUS"
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
		        reportTitleCell.setCellValue("TRANSPORTER WISE TEUS REPORT" );

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
		        	 reportTitleCell1.setCellValue("TRANSPORTER WISE TEUS REPORT As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("TRANSPORTER WISE TEUS REPORT From : " + formattedStartDate + " to " + formattedEndDate);
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
		    
		            double value1 = (resultData1[2] != null && !resultData1[2].toString().isEmpty()) 
                            ? Double.parseDouble(resultData1[2].toString()) 
                            : 0;
                        double value2 = (resultData1[3] != null && !resultData1[3].toString().isEmpty()) 
                            ? Double.parseDouble(resultData1[3].toString()) 
                            : 0;
                        
                        
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr_no":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "TRANSPORTER_NAME":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "SIZE_20":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    totalSize20 += value1; // Add to total size 20
		                    break;

		                case "SIZE_40":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    totalSize40 += value2; // Add to total size 40
		                    break;

		                case "TEUS":
//		                    cell.setCellValue(value1 + (value2 * 2));
		                	double teus = value1 + (value2 * 2);
		                    cell.setCellValue(teus);
		                    totalTEUs += teus; // Add to total TEUs
		                    
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
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize40); // Total Size 40
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
	
	public byte[] createExcelReportOfLclZeroPaymentReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = importReportsRepository.toGetLCLZeroPaymetReport(companyId, branchId, startDate, endDate,
		        		sbNo, bookingNo, exporterName,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("LCL Zero Payment");

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
		        	   "Sr No", "igm_no", "item_no", "Cont_No", "Size", "Destuff_date", "BL_No", 
		        	    "Importer_Name", "LCL_No_Bill/LCL_zero_Bill", "Agent_name", "Gate_out_date"
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
		        reportTitleCell.setCellValue("LCL Zero Payment" );

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
		        	 reportTitleCell1.setCellValue("LCL Zero Payment REPORT As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("LCL Zero Payment REPORT From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;
		                    
		                case "igm_no":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "item_no":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Cont_No":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Destuff_date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "BL_No":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Importer_Name":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "LCL_No_Bill/LCL_zero_Bill":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Agent_name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Gate_out_date":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
//		        Row emptyRow = sheet.createRow(rowNum++);
//		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//
//		     // Create a row for totals
//		        Row totalRow = sheet.createRow(rowNum++);
//
//		     // Create a CellStyle for the background color
//		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
//		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
//		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
//		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		        // Create a font for bold text
//		        Font boldFont1 = workbook.createFont();
//		        boldFont1.setBold(true);
//		        totalRowStyle.setFont(boldFont1);
//
//		        // Add "Total" label to the first cell and apply the style
//		        Cell totalLabelCell = totalRow.createCell(0);
//		        totalLabelCell.setCellValue("Total");
//		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style
//
//		        CellStyle centerStyle = workbook.createCellStyle();
//		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
//		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
//		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary
//
//		     
//		        for (int i = 0; i < columnsHeader.length; i++) {
//		            Cell totalCell = totalRow.createCell(i);
//		            totalCell.setCellStyle(numberCellStyle);
//		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
//		            
//		            switch (columnsHeader[i]) {
//		            
//		            case "Section 60(1)":
//		            	 totalCell.setCellValue("Total");
//		                 totalCell.setCellStyle(centerStyle); // Apply centered style
//		            
//                    break;
//		                case "Inbond Pkgs":
//		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
//		                    break;
//		                case "Inbond Weight":
//		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
//		                    break;
//		                case "Inbond Asset Value":
//		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
//		                    break;
//		                case "Inbond Duty Value":
//		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
//		                    break;
//		                case "Bal Pkgs":
//		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
//		                    break;
//		                case "Bal Wt":
//		                    totalCell.setCellValue(totalBalWeight.doubleValue());
//		                    break;
//		                case "Bal Asset Value":
//		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
//		                    break;
//		                case "Bal Duty Value":
//		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
//		                    break;
//		                case "Area Balance Area":
//		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
//		                    break;
//		                default:
//		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
//		                    break;
//		            }
//		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 28 * 306); 
		        sheet.setColumnWidth(9, 36 * 306); 
		        sheet.setColumnWidth(10, 17 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 16 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfEmptyContainerReport(String companyId,
		    String branchId, String username, String type, String companyname,
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
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findContainerDetails(companyId, branchId, startDate, endDate, sbNo, bookingNo, exporterName, acc, cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Empty Container Report");

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
		        	    "Container No", 
		        	    "Cont Size", 
		        	    "Cont Type", 
		        	    "Shipping Line", 
		        	    "Gate In Date", 
		        	    "Gate Out Date", 
		        	    "Mode Of Transport", 
		        	    "Booking No", 
		        	    "Seal No", 
		        	    "A/C Holder", 
		        	    "Transporter", 
		        	    "Pickup Yard", 
		        	    "Gate In Remarks", 
		        	    "Mode Of Gate In"
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
//		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		     // Merge cells for the company name (if needed)
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		        
		    
		        
		        
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
//		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6)); // Merge row 1
		   
		        
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
//		         Merge the cells across the first 7 columns to center the title
		        
		        // Merge cells for additional branch details (row 2)
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
		   
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Empty Container Report" );

//		         Merge the cells across the first 7 columns to center the title
		        
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
		        
//		         Merge the cells across the first 7 columns to center the title
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6)); // Merge rows (3, 3) and columns (0 to 6)
//		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Empty Container Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Empty Container Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        
		        String lastContainerNo = ""; // Variable to track the last processed container number
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            boolean isFirstRowForContainer = !resultData1[0].toString().equals(lastContainerNo);
		            if (isFirstRowForContainer) {
		                lastContainerNo = resultData1[0].toString(); // Update last processed container number
		            }
		            
		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "Container No":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[0].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[1].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[2].toString() : "");
		                    break;

		                case "Shipping Line":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[3].toString() : "");
		                    break;

		                case "Gate In Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Gate Out Date":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Mode Of Transport":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Booking No":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Seal No":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "A/C Holder":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Transporter":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Pickup Yard":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Gate In Remarks":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Mode Of Gate In":
//		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                	cell.setCellValue("EMPTY");
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
			        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
			        		
			        Row emptyRow1 = sheet.createRow(rowNum++);
			        
			        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
			        // Header row for summary
			        String[] columnsHeaderSummery = {
			            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
			        };

			        // Create a map to store container count for each agent
			        Map<String, int[]> agentContainerCount = new HashMap<>();
			        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

			        importDetails.forEach(i -> {
			            String agent = i[3] != null ? i[3].toString() : "Unknown"; // Shipping Agent
			            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size
			            String containerNo = i[0] != null ? i[0].toString() : ""; // Container Number

			            // Initialize container tracker and count array for this agent if not already present
			            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
			            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

			            // Check if this container number has already been processed for this agent
			            if (!agentContainerTracker.get(agent).contains(containerNo)) {
			                // Add this container number to the set for this agent
			                agentContainerTracker.get(agent).add(containerNo);

			                // Increment the count based on container size
			                switch (containerSize) {
			                    case "20":
			                        agentContainerCount.get(agent)[0]++;
			                        break;
			                    case "22":
			                        agentContainerCount.get(agent)[1]++;
			                        break;
			                    case "40":
			                        agentContainerCount.get(agent)[2]++;
			                        break;
			                    case "45":
			                        agentContainerCount.get(agent)[3]++;
			                        break;
			                    default:
			                        break;
			                }
			            }
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

			        // Iterate over the map to create rows for each shipping agent and their container count
			        int srNo = 1;
			        
			        String shippingAgent =null;
			        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
			             shippingAgent = entry.getKey();
			            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

			            // Create a new row for this shipping agent
			            Row row = sheet.createRow(rowNum++);
			            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
			            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
			            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
			            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
			            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
			            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
			            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//			            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
			            
			            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
			            row.createCell(7).setCellValue(tuesValue); // TUE'S value
			        }


			        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
			        for (int[] sizes : agentContainerCount.values()) {
			            totalSize20 += sizes[0];
			            totalSize22 += sizes[1];
			            totalSize40 += sizes[2];
			            totalSize45 += sizes[3];
			        }

			        // Create a CellStyle for the background color
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
			        
			        
			     // Create the Total Row
			        Row totalRow = sheet.createRow(rowNum++);
			        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

			        // Calculate totals
			        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
			        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

			        // Set total values in respective columns
			        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
			        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
			        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
			        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
			        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
			        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

			        // Apply styles for the Total Row
			        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
			            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
			        }

			        // Adjust column widths to fit the data (optional)
			        for (int i = 0; i < columnsHeaderSummery.length; i++) {
			            sheet.autoSizeColumn(i); // Automatically adjust column width
			        }

			        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 9 * 306); 
		        
		        sheet.setColumnWidth(4, 45 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 45 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 45 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
 
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
	
	
	
	public byte[] createExcelReportOfExportCartingReport(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findExportCartingData(companyId, branchId, startDate, endDate, sbNo,exporterName, acc, cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Carting Report");

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
		        		 "Sr.No", 
		        	    "Shipping Bill No", 
		        	    "Shipping Bill Date", 
		        	    "CHA", 
		        	    "Account Holder", 
		        	    "Exporter Name", 
		        	    "Consignee", 
		        	    "Declared Weight", 
		        	    "Remarks", 
		        	    "Carting Date", 
		        	    "Cargo Description", 
		        	    "Ware House Occupancy", 
		        	    "Ware House Location", 
		        	    "Declared Pkgs", 
		        	    "Unload Package", 
		        	    "Unload Weight", 
		        	    "Unload Area", 
		        	    "Pod", 
		        	    "Equipment", 
		        	    "FOB"
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
//		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));

		     // Merge cells for the company name (if needed)
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		        
		    
		        
		        
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
//		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));

		        // Add Branch Address (Centered)
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6)); // Merge row 1
		   
		        
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
//		         Merge the cells across the first 7 columns to center the title
		        
		        // Merge cells for additional branch details (row 2)
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
		   
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Carting Report" );

//		         Merge the cells across the first 7 columns to center the title
		        
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
		        
//		         Merge the cells across the first 7 columns to center the title
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6)); // Merge rows (3, 3) and columns (0 to 6)
//		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Carting Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Carting Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        
		        String lastContainerNo = ""; // Variable to track the last processed container number
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            boolean isFirstRowForContainer = !resultData1[0].toString().equals(lastContainerNo);
		            if (isFirstRowForContainer) {
		                lastContainerNo = resultData1[0].toString(); // Update last processed container number
		            }
		            
		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                
		                case "Sr.No":
		                	  cell.setCellValue(serialNo++); // Increment serial number
		                    break;
		                    
		                case "Shipping Bill No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Shipping Bill Date":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Account Holder":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Exporter Name":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Consignee":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Declared Weight":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double declaredWeight = 0.0;
		                    if (resultData1[6] != null) {
		                        try {
		                            declaredWeight = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            declaredWeight = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(declaredWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Remarks":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Carting Date":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Ware House Occupancy":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double warehouseOccupancy = 0.0;
		                    if (resultData1[10] != null) {
		                        try {
		                            warehouseOccupancy = Double.parseDouble(resultData1[10].toString());
		                        } catch (NumberFormatException e) {
		                            warehouseOccupancy = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(warehouseOccupancy);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Ware House Location":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Declared Pkgs":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double declaredPkgs = 0.0;
		                    if (resultData1[12] != null) {
		                        try {
		                            declaredPkgs = Double.parseDouble(resultData1[12].toString());
		                        } catch (NumberFormatException e) {
		                            declaredPkgs = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(declaredPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Unload Package":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double unloadPackage = 0.0;
		                    if (resultData1[13] != null) {
		                        try {
		                            unloadPackage = Double.parseDouble(resultData1[13].toString());
		                        } catch (NumberFormatException e) {
		                            unloadPackage = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(unloadPackage);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Unload Weight":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double unloadWeight = 0.0;
		                    if (resultData1[14] != null) {
		                        try {
		                            unloadWeight = Double.parseDouble(resultData1[14].toString());
		                        } catch (NumberFormatException e) {
		                            unloadWeight = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(unloadWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Unload Area":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double unloadArea = 0.0;
		                    if (resultData1[15] != null) {
		                        try {
		                            unloadArea = Double.parseDouble(resultData1[15].toString());
		                        } catch (NumberFormatException e) {
		                            unloadArea = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(unloadArea);
		                    cell.setCellStyle(numberCellStyle);
		                    break;



		                case "Pod":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Equipment":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "FOB":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.0
		                    Double fob = 0.0;
		                    if (resultData1[18] != null) {
		                        try {
		                            fob = Double.parseDouble(resultData1[18].toString());
		                        } catch (NumberFormatException e) {
		                            fob = 0.0; // Default to 0.0 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(fob);
		                    cell.setCellStyle(numberCellStyle);
		                    break;


		                default:
		                    cell.setCellValue("");  // Handle undefined columns
		                    break;
		            }

		            }
		        }

		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Agent", "Total SB Count", "Weight", "Area"
		        };
			        Map<String, Map<String, Set<String>>> agentSbCartingMap = new HashMap<>(); // Map to store agents -> SB numbers -> Carting IDs
			        Map<String, Set<String>> cartingSbMap = new HashMap<>(); // Map to track Carting ID -> SB numbers
			        Map<String, double[]> agentTotals = new HashMap<>(); // Map to store agent totals: [0] - weight, [1] - area, [2] - SB count

			        // Populate maps
			        importDetails.forEach(i -> {
			            String agent = i[3] != null ? i[3].toString() : "Unknown"; // Shipping Agent
			            String sbno = i[0] != null ? i[0].toString() : ""; // SB Number
			            String cartingid = i[19] != null ? i[19].toString() : ""; // Carting ID
			            double weight = i[14] != null ? Double.parseDouble(i[14].toString()) : 0.0; // Weight
			            double area = i[15] != null ? Double.parseDouble(i[15].toString()) : 0.0; // Area
			            // Initialize the map for each agent if not already present
			            agentSbCartingMap.putIfAbsent(agent, new HashMap<>());

			            // Initialize the set for SB numbers for this agent if not already present
			            agentSbCartingMap.get(agent).putIfAbsent(sbno, new HashSet<>());

			            // Add the carting ID to the set of carting IDs for this SB number
			            agentSbCartingMap.get(agent).get(sbno).add(cartingid);

			            // Track Carting ID -> SB Numbers
			            cartingSbMap.putIfAbsent(cartingid, new HashSet<>());
			            cartingSbMap.get(cartingid).add(sbno);

			         
			            // Ensure agentTotals is initialized
			            agentTotals.putIfAbsent(agent, new double[3]); // [0] - weight, [1] - area, [2] - SB count
			            
			            agentTotals.get(agent)[0] += weight; // Add weight to total
			            agentTotals.get(agent)[1] += area;  // Add area to total
			        });

			        // Calculate the total SB count
			        agentSbCartingMap.forEach((agent, sbCartingMap) -> {
			            sbCartingMap.forEach((sbno, cartingIds) -> {
			                // Scenario 1: If one SB number is associated with multiple carting IDs, count it as 1
			                if (cartingIds.size() > 0) {
			                    agentTotals.get(agent)[2]++; // Increment count for this SB number
			                }
			            });
			        });

			        // Scenario 2: Count SB numbers for each Carting ID
			        cartingSbMap.forEach((cartingid, sbNumbers) -> {
			            sbNumbers.forEach(sbno -> {
			                // You can process each unique SB number here if required
			            });
			        });

			        // Print the total SB count per agent
			        agentTotals.forEach((agent, totals) -> {
			            System.out.println("Agent: " + agent + ", Total SB Count: " + totals[2]);
			        });




			        

			        // After processing, you can access agentTotals to get the total count of SBs, weight, and area for each agent


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

			        // Iterate over the map to create rows for each shipping agent and their totals (SB Count, Weight, Area)
			        int srNo = 1;

			        for (Map.Entry<String, double[]> entry : agentTotals.entrySet()) {
			            String shippingAgent = entry.getKey();
			            double[] totals = entry.getValue(); // totals[0] = totalWeight, totals[1] = totalArea, totals[2] = totalSBCount

			            // Create a new row for this shipping agent
			            Row row = sheet.createRow(rowNum++);
			            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
			            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
			            row.createCell(2).setCellValue(totals[2]); // Total SB Count
			            row.createCell(3).setCellValue(totals[0]); // Total Weight
			            row.createCell(4).setCellValue(totals[1]); // Total Area
			        }

			        // Create a CellStyle for the background color
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

			        
			        
//			     // Create the Total Row
//			        Row totalRow = sheet.createRow(rowNum++);
//			        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row
//
//			        // Calculate totals
//			        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
//			        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
//
//			        // Set total values in respective columns
//			        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
//			        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
//			        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
//			        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
//			        
//			        // Apply styles for the Total Row
//			        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
//			            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//			            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
//			        }
//
//			        // Adjust column widths to fit the data (optional)
//			        for (int i = 0; i < columnsHeaderSummery.length; i++) {
//			            sheet.autoSizeColumn(i); // Automatically adjust column width
//			        }

			        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 45 * 306); 
		        
		        sheet.setColumnWidth(4, 45 * 306); 
		        sheet.setColumnWidth(5, 45 * 306); 
		        
		        sheet.setColumnWidth(6, 45 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 45 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 27 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 27 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 36 * 306); 
 
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
	
	
	
	public byte[] createExcelReportOfYardBalanceReportDetails(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findEmptyGateOutContainerDetails(companyId, branchId,startDate, endDate,sbNo,bookingNo,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Container Gate Out Report");

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
		        	    "Sr.No", "Cont No", "Cont Size", "Cont Type", "Commodity", 
		        	    "Stuff Date", "Gate out Date", "Account Holder", "Shipping Line", 
		        	    "Vessel Name", "Port", "Custom Seal", "Agent Seal", "VCN No", 
		        	    "Stuff Package", "Stuff Weight", "Tare Weight", "Transporter", 
		        	    "Low Bed", "Vehicle No", "Empty In Date", "Empty Pickup Yard", 
		        	    "Process Type", "Remarks", "Invoice No"
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Container Gate Out Report");

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Container Gate Out Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Container Gate Out Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr.No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Commodity":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Stuff Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Gate out Date":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Account Holder":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Shipping Line":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Port":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Custom Seal":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Agent Seal":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "VCN No":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

//		                case "Stuff Package":
//		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
//		                    break;
//
//		                case "Stuff Weight":
//		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
//		                    break;
//
//		                case "Tare Weight":
//		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
//		                    break;

		                case "Stuff Package":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double stuffPackage = 0.00;
		                    if (resultData1[13] != null) {
		                        try {
		                            stuffPackage = Double.parseDouble(resultData1[13].toString());
		                        } catch (NumberFormatException e) {
		                            stuffPackage = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(stuffPackage);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Stuff Weight":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double stuffWeight = 0.00;
		                    if (resultData1[14] != null) {
		                        try {
		                            stuffWeight = Double.parseDouble(resultData1[14].toString());
		                        } catch (NumberFormatException e) {
		                            stuffWeight = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(stuffWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Tare Weight":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double tareWeight = 0.00;
		                    if (resultData1[15] != null) {
		                        try {
		                            tareWeight = Double.parseDouble(resultData1[15].toString());
		                        } catch (NumberFormatException e) {
		                            tareWeight = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(tareWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                    
		                case "Transporter":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Low Bed":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Vehicle No":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Empty In Date":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                case "Empty Pickup Yard":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "Process Type":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "Remarks":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "Invoice No":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
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
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Line", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();
		        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

		        importDetails.forEach(i -> {
		            String agent = i[7] != null ? i[7].toString() : "Unknown"; // Shipping Agent
		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size
		            String containerNo = i[0] != null ? i[0].toString() : ""; // Container Number

		            // Initialize container tracker and count array for this agent if not already present
		            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
		            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

		            // Check if this container number has already been processed for this agent
		            if (!agentContainerTracker.get(agent).contains(containerNo)) {
		                // Add this container number to the set for this agent
		                agentContainerTracker.get(agent).add(containerNo);

		                // Increment the count based on container size
		                switch (containerSize) {
		                    case "20":
		                        agentContainerCount.get(agent)[0]++;
		                        break;
		                    case "22":
		                        agentContainerCount.get(agent)[1]++;
		                        break;
		                    case "40":
		                        agentContainerCount.get(agent)[2]++;
		                        break;
		                    case "45":
		                        agentContainerCount.get(agent)[3]++;
		                        break;
		                    default:
		                        break;
		                }
		            }
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

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		        // Create a CellStyle for the background color
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
		        
		        
		     // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        // Calculate totals
		        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
		        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

		        // Set total values in respective columns
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
		        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 9 * 306); 
		        
		        sheet.setColumnWidth(4, 27 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 36 * 306); 
		        sheet.setColumnWidth(9, 36 * 306); 
		        sheet.setColumnWidth(10, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 36 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        
		        sheet.setColumnWidth(19, 18 * 306);
		        sheet.setColumnWidth(20, 18 * 306);
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306);
		        sheet.setColumnWidth(23, 18 * 306);
		        sheet.setColumnWidth(24, 18 * 306);
		        sheet.setColumnWidth(25, 18 * 306); 
		        sheet.setColumnWidth(26, 36 * 306); 
		        sheet.setColumnWidth(27, 18 * 306);

		  
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
	
	
	
	
	
	
	
	public byte[] createExcelReportOfExportContainerReworkingDetails(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findReworkingContainerDetails(companyId, branchId,startDate, endDate,sbNo,acc);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Container Reworking");

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
		        	    "Sr.No.", "Cont No", "Cont Size", "Cont Type", "Stuffing Date", 
		        	    "Reworking Date", "Packages", "Weight", "Agent Name", "Container Search Type"
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
		        reportTitleCell.setCellValue("Export Container Reworking");

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
		        	 reportTitleCell1.setCellValue("Export Container Reworking As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Container Reworking From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr.No.":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Stuffing Date":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Reworking Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

//		                case "Packages":
//		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
//		                    break;
//
//		                case "Weight":
//		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
//		                    break;
		                    
		                case "Packages":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double packages = 0.00;
		                    if (resultData1[5] != null) {
		                        try {
		                            packages = Double.parseDouble(resultData1[5].toString());
		                        } catch (NumberFormatException e) {
		                            packages = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(packages);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                case "Weight":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double weight = 0.00;
		                    if (resultData1[6] != null) {
		                        try {
		                            weight = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            weight = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(weight);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;


		                case "Agent Name":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Container Search Type":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
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
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Line", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();
		        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

		        importDetails.forEach(i -> {
		            String agent = i[7] != null ? i[7].toString() : "Unknown"; // Shipping Agent
		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size
		            String containerNo = i[0] != null ? i[0].toString() : ""; // Container Number

		            // Initialize container tracker and count array for this agent if not already present
		            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
		            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

		            // Check if this container number has already been processed for this agent
		            if (!agentContainerTracker.get(agent).contains(containerNo)) {
		                // Add this container number to the set for this agent
		                agentContainerTracker.get(agent).add(containerNo);

		                // Increment the count based on container size
		                switch (containerSize) {
		                    case "20":
		                        agentContainerCount.get(agent)[0]++;
		                        break;
		                    case "22":
		                        agentContainerCount.get(agent)[1]++;
		                        break;
		                    case "40":
		                        agentContainerCount.get(agent)[2]++;
		                        break;
		                    case "45":
		                        agentContainerCount.get(agent)[3]++;
		                        break;
		                    default:
		                        break;
		                }
		            }
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

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		        // Create a CellStyle for the background color
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
		        
		        
		     // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        // Calculate totals
		        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
		        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

		        // Set total values in respective columns
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
		        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 9 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 45 * 306); 
		        sheet.setColumnWidth(9, 27 * 306); 


		  
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
	
	public byte[] createExcelReportOfExportInOutContainersDetails(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.fetchExporGateInAndGateOutDetails(companyId, branchId,startDate, endDate,bookingNo,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Empty In Out Report");

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
		        	    "Sr No", "Cont No", "Cont Size", "Cont Type", "ISO CODE", "Gate In Date",
		        	    "Gate Out Date", "Out Truck No", "Transporter", "AC Holder", "Line",
		        	    "Yard Location", "Gate In Mode", "Damage Details", "Remarks",
		        	    "Booking No", "Shipper", "Port Code", "Gate In Remarks"
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
		        reportTitleCell.setCellValue("Export Empty In Out Report");

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
		        	 reportTitleCell1.setCellValue("Export Empty In Out Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Empty In Out Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "ISO CODE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Gate In Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Gate Out Date":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Out Truck No":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Transporter":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "AC Holder":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Line":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Yard Location":
		                    cell.setCellValue(branchname);
		                    break;

		                case "Gate In Mode":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Damage Details":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Remarks":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Booking No":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Shipper":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Port Code":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Gate In Remarks":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
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
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Line", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();
		        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

		        importDetails.forEach(i -> {
		            String agent = i[9] != null ? i[9].toString() : "Unknown"; // Shipping Agent
		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size
		            String containerNo = i[0] != null ? i[0].toString() : ""; // Container Number

		            // Initialize container tracker and count array for this agent if not already present
		            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
		            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

		            // Check if this container number has already been processed for this agent
		            if (!agentContainerTracker.get(agent).contains(containerNo)) {
		                // Add this container number to the set for this agent
		                agentContainerTracker.get(agent).add(containerNo);

		                // Increment the count based on container size
		                switch (containerSize) {
		                    case "20":
		                        agentContainerCount.get(agent)[0]++;
		                        break;
		                    case "22":
		                        agentContainerCount.get(agent)[1]++;
		                        break;
		                    case "40":
		                        agentContainerCount.get(agent)[2]++;
		                        break;
		                    case "45":
		                        agentContainerCount.get(agent)[3]++;
		                        break;
		                    default:
		                        break;
		                }
		            }
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

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		        // Create a CellStyle for the background color
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
		        
		        
		     // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        // Calculate totals
		        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
		        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

		        // Set total values in respective columns
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
		        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 9 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 45 * 306); 
		        sheet.setColumnWidth(9, 45 * 306); 
		        sheet.setColumnWidth(10,  45 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
		        
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 18 * 306); 
		        
		        sheet.setColumnWidth(18, 45 * 306); 

		  
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
	
	public byte[] createExcelReportOfExportEmptyLoadedContainersDetails(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findExportLoadedBalanceReport(companyId, branchId, endDate,sbNo,bookingNo,exporterName,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Loaded Balance Report");

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
		        	    "SR.NO", "CONT NO", "CONT SIZE", "CONT TYPE", "STUFF ORDER DATE", "MOVEMENT ORDER DATE", 
		        	    "ACCOUNT HOLDER", "LINE NAME", "AGENT SEAL", "CUSTOM SEAL", "VESSEL NAME", 
		        	    "VCN NO", "VOY NO", "PORT NAME", "CONT WT", "CONT CYCLE"
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
		        reportTitleCell.setCellValue("Export Loaded Balance Report");

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
		        	 reportTitleCell1.setCellValue("Export Loaded Balance Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Loaded Balance Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                
		                case "SR.NO" :
		                cell.setCellValue(serialNo++);
		                break;
		                
		                case "CONT NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT SIZE":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "CONT TYPE":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "STUFF ORDER DATE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "MOVEMENT ORDER DATE":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "ACCOUNT HOLDER":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "LINE NAME":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "AGENT SEAL":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "CUSTOM SEAL":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "VESSEL NAME":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "VCN NO":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "VOY NO":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "PORT NAME":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "CONT WT":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "CONT CYCLE":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
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
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Line", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();
		        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

		        importDetails.forEach(i -> {
		            String agent = i[6] != null ? i[6].toString() : "Unknown"; // Shipping Agent
		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size
		            String containerNo = i[0] != null ? i[0].toString() : ""; // Container Number

		            // Initialize container tracker and count array for this agent if not already present
		            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
		            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

		            // Check if this container number has already been processed for this agent
		            if (!agentContainerTracker.get(agent).contains(containerNo)) {
		                // Add this container number to the set for this agent
		                agentContainerTracker.get(agent).add(containerNo);

		                // Increment the count based on container size
		                switch (containerSize) {
		                    case "20":
		                        agentContainerCount.get(agent)[0]++;
		                        break;
		                    case "22":
		                        agentContainerCount.get(agent)[1]++;
		                        break;
		                    case "40":
		                        agentContainerCount.get(agent)[2]++;
		                        break;
		                    case "45":
		                        agentContainerCount.get(agent)[3]++;
		                        break;
		                    default:
		                        break;
		                }
		            }
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

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		        // Create a CellStyle for the background color
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
		        
		        
		     // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        // Calculate totals
		        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
		        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

		        // Set total values in respective columns
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
		        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10,  18 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
		        
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 18 * 306); 
		        
		        sheet.setColumnWidth(18, 45 * 306); 

		  
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
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfExportContainerStuffingDetails(String companyId,
		    String branchId, String username, String type, String companyname,
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
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.exportStuffGateInReport(companyId, branchId,startDate, endDate,sbNo,bookingNo,exporterName, acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Container Stuffing");

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
		        	    "Sr No", "A/C Name", "CONT No", "Size", "Cont Type", "Mty In Date", "Stuffing Date",
		        	    "Out Date", "POD", "Vessel Name", "Equipment", "POL", "Stuffed Pkgs", "Remarks",
		        	    "Gross Wt", "VCN No", "Rotation No", "VOY No", "Shipping Line", "Cust Seal", "Agent Seal"
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
		        reportTitleCell.setCellValue("Export Container Stuffing");

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
		        	 reportTitleCell1.setCellValue("Export Container Stuffing Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Container Stuffing Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "A/C Name":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Mty In Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Stuffing Date":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Out Date":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "POD":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Equipment":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "POL":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

//		                case "Stuffed Pkgs":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
//		                    break;
		                    
		                case "Stuffed Pkgs":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double stuffedPkgs = 0.00;
		                    if (resultData1[11] != null) {
		                        try {
		                            stuffedPkgs = Double.parseDouble(resultData1[11].toString());
		                        } catch (NumberFormatException e) {
		                            stuffedPkgs = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(stuffedPkgs);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;


		                case "Remarks":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

//		                case "Gross Wt":
//		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
//		                    break;
		                    
		                case "Gross Wt":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double grossWt = 0.00;
		                    if (resultData1[13] != null) {
		                        try {
		                            grossWt = Double.parseDouble(resultData1[13].toString());
		                        } catch (NumberFormatException e) {
		                            grossWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(grossWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;


		                case "VCN No":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Rotation No":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "VOY No":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Shipping Line":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Cust Seal":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Agent Seal":
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
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Line", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();
		        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

		        importDetails.forEach(i -> {
		            String agent = i[0] != null ? i[0].toString() : "Unknown"; // Shipping Agent
		            String containerSize = i[2] != null ? i[2].toString() : ""; // Container Size
		            String containerNo = i[1] != null ? i[1].toString() : ""; // Container Number

		            // Initialize container tracker and count array for this agent if not already present
		            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
		            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

		            // Check if this container number has already been processed for this agent
		            if (!agentContainerTracker.get(agent).contains(containerNo)) {
		                // Add this container number to the set for this agent
		                agentContainerTracker.get(agent).add(containerNo);

		                // Increment the count based on container size
		                switch (containerSize) {
		                    case "20":
		                        agentContainerCount.get(agent)[0]++;
		                        break;
		                    case "22":
		                        agentContainerCount.get(agent)[1]++;
		                        break;
		                    case "40":
		                        agentContainerCount.get(agent)[2]++;
		                        break;
		                    case "45":
		                        agentContainerCount.get(agent)[3]++;
		                        break;
		                    default:
		                        break;
		                }
		            }
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

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		        // Create a CellStyle for the background color
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
		        
		        
		     // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        // Calculate totals
		        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
		        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

		        // Set total values in respective columns
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
		        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10,  18 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
		        
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 18 * 306); 
		        
		        sheet.setColumnWidth(18, 45 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 


		  
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
	
	public byte[] createExcelReportOfExportContainerONWHBufferStuffingDetails(String companyId,
		    String branchId, String username, String type, String companyname,
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

		        
		        List<Object[]> importDetails = exportOperationalReportRepo.findExportContainerONWHBuffer(companyId, branchId,startDate, endDate,sbNo ,bookingNo,exporterName, acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Export Container ONWH Buffer Stuffing");

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
		        	    "Sr No", "A/C Name", "CONT No", "Size", "Cont Type", "Mty In Date", "Stuffing Date",
		        	    "Out Date", "POD", "Vessel Name", "Equipment", "POL", "Stuffed Pkgs", "Remarks",
		        	    "Gross Wt", "VCN No", "Rotation No", "VOY No", "Shipping Line", "Cust Seal", "Agent Seal"
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
		        reportTitleCell.setCellValue("Export Container ONWH Buffer Stuffing");

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
		        	 reportTitleCell1.setCellValue("Export Container ONWH Buffer Stuffing Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Container ONWH Buffer Stuffing Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "A/C Name":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Mty In Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Stuffing Date":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Out Date":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "POD":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Equipment":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "POL":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

//		                case "Stuffed Pkgs":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
//		                    break;

		                case "Stuffed Pkgs":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0
		                    Double stuffedPkgs = 0.00;
		                    if (resultData1[11] != null) {
		                        try {
		                            stuffedPkgs = Double.parseDouble(resultData1[11].toString());
		                        } catch (NumberFormatException e) {
		                            stuffedPkgs = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(stuffedPkgs);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;

		                    
		                case "Remarks":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

//		                case "Gross Wt":
//		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
//		                    break;
		                    
		                case "Gross Wt":
		                    // Try to parse the value as Double, if it's not a valid number, set to 0.00
		                    Double grossWt = 0.00;
		                    if (resultData1[13] != null) {
		                        try {
		                            grossWt = Double.parseDouble(resultData1[13].toString());
		                        } catch (NumberFormatException e) {
		                            grossWt = 0.00; // Default to 0.00 if it's not a valid number
		                        }
		                    }
		                    cell.setCellValue(grossWt);
		                    cell.setCellStyle(numberCellStyle); // Apply number formatting style
		                    break;


		                case "VCN No":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Rotation No":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "VOY No":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Shipping Line":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Cust Seal":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Agent Seal":
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
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank	
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Line", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();
		        Map<String, Set<String>> agentContainerTracker = new HashMap<>();

		        importDetails.forEach(i -> {
		            String agent = i[20] != null ? i[20].toString() : "Unknown"; // Shipping Agent
		            String containerSize = i[2] != null ? i[2].toString() : ""; // Container Size
		            String containerNo = i[1] != null ? i[1].toString() : ""; // Container Number

		            // Initialize container tracker and count array for this agent if not already present
		            agentContainerTracker.putIfAbsent(agent, new HashSet<>());
		            agentContainerCount.putIfAbsent(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45

		            // Check if this container number has already been processed for this agent
		            if (!agentContainerTracker.get(agent).contains(containerNo)) {
		                // Add this container number to the set for this agent
		                agentContainerTracker.get(agent).add(containerNo);

		                // Increment the count based on container size
		                switch (containerSize) {
		                    case "20":
		                        agentContainerCount.get(agent)[0]++;
		                        break;
		                    case "22":
		                        agentContainerCount.get(agent)[1]++;
		                        break;
		                    case "40":
		                        agentContainerCount.get(agent)[2]++;
		                        break;
		                    case "45":
		                        agentContainerCount.get(agent)[3]++;
		                        break;
		                    default:
		                        break;
		                }
		            }
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

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		        // Create a CellStyle for the background color
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
		        
		        
		     // Create the Total Row
		        Row totalRow = sheet.createRow(rowNum++);
		        totalRow.createCell(0).setCellValue("Total"); // Set the label for the total row

		        // Calculate totals
		        int totalContainers = totalSize20 + totalSize22 + totalSize40 + totalSize45;
		        int totalTUEs = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);

		        // Set total values in respective columns
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalContainers); // Total count of all containers
		        totalRow.createCell(7).setCellValue(totalTUEs); // Total TUEs

		        // Apply styles for the Total Row
		        for (int i = 0; i <= 7; i++) { // Iterate through all columns in the total row
		            Cell cell = totalRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle); // Apply light green style with bold text
		        }

		        // Adjust column widths to fit the data (optional)
		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
		            sheet.autoSizeColumn(i); // Automatically adjust column width
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 36 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10,  18 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
		        
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 18 * 306); 
		        
		        sheet.setColumnWidth(18, 45 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20, 18 * 306); 
		        sheet.setColumnWidth(21, 18 * 306); 
		        sheet.setColumnWidth(22, 18 * 306); 

		  
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
	public byte[] createExcelReportExportBufferGateInInExportSection(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

		        
		        List<Object[]> importDetails = commonReportsRepo.getExportBufferGateInReport(companyId, branchId,startDate, endDate);
		        
		        
		        Sheet sheet = workbook.createSheet("Export Buffer Gate In Report");

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
		        	    "Mode",                // This corresponds to "Mode"
		        	    "Work Order No.",      // This corresponds to "Work Order No."
		        	    "Work Order Date",     // This corresponds to "Work Order Date"
		        	    "Gate In Date",        // This corresponds to "Gate In Date"
		        	    "Vehicle",             // This corresponds to "Vehicle"
		        	    "Container No",       // This corresponds to "Container No."
		        	    "Size",                // This corresponds to "Size"
		        	    "Type",                // This corresponds to "Type"
		        	    "Cargo Wt",           // This corresponds to "Cargo Wt."
		        	    "TareWt",              // This corresponds to "TareWt"
		        	    "Account Holder",      // This corresponds to "Account Holder"
		        	    "CHA",                 // This corresponds to "CHA"
		        	    "Line",                // This corresponds to "Line"
		        	    "Sr.No Buffer",        // This corresponds to "Sr.No Buffer"
		        	    "Sr.No Factory",       // This corresponds to "Sr.No Factory"
		        	    "Gate In Condition",   // This corresponds to "Gate In Condition"
		        	    "Gate Out Date",       // This corresponds to "Gate Out Date"
		        	    "Shipping Bill No.",   // This corresponds to "Shipping Bill No."
		        	    "Shipping Bill Date",  // This corresponds to "Shipping Bill Date"
		        	    "No Of Packages",      // This corresponds to "No Of Packages"
		        	    "Weight",              // This corresponds to "Weight"
		        	    "Vessel",              // This corresponds to "Vessel"
		        	    "POD",                 // This corresponds to "POD"
		        	    "POL",                 // This corresponds to "POL"
		        	    "Agent Seal",          // This corresponds to "Agent Seal"
		        	    "Custom Seal",         // This corresponds to "Custom Seal"
		        	    "Gate Out Condition"   // This corresponds to "Gate Out Condition"
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Buffer Gate In Report" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Buffer Gate In Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Buffer Gate In Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		        for (int i = 0; i < columnsHeader.length; i++) 
		        {
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

		                    case "Mode":
		                        cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                        break;

		                    case "Work Order No.":
		                        cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                        break;

		                    case "Work Order Date":
		                        if (resultData1[2] != null) {
		                            cell.setCellValue(resultData1[2].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Gate In Date":
		                        if (resultData1[3] != null) {
		                            cell.setCellValue(resultData1[3].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Vehicle":
		                        cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                        break;

		                    case "Container No":
		                        cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                        break;

		                    case "Size":
		                        cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                        break;

		                    case "Type":
		                        cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                        break;

		                    case "Cargo Wt":
		                        if (resultData1[8] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[8].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[8].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;

		                    case "TareWt":
		                        if (resultData1[9] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[9].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[9].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;


		                    case "Account Holder":
		                        cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                        break;

		                    case "CHA":
		                        cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                        break;

		                    case "Line":
		                        cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                        break;

		                    case "Sr.No Buffer":
		                        cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                        break;

		                    case "Sr.No Factory":
		                        cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                        break;

		                    case "Gate In Condition":
		                        cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                        break;

		                    case "Gate Out Date":
		                        if (resultData1[16] != null) {
		                            cell.setCellValue(resultData1[16].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Shipping Bill No.":
		                        cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                        break;

		                    case "Shipping Bill Date":
		                        if (resultData1[18] != null) {
		                            cell.setCellValue(resultData1[18].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "No Of Packages":
		                        if (resultData1[19] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[19].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[19].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;

		                    case "Weight":
		                        if (resultData1[20] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[20].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[20].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;


		                    case "Vessel":
		                        cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                        break;

		                    case "POD":
		                        cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                        break;

		                    case "POL":
		                        cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                        break;

		                    case "Agent Seal":
		                        cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                        break;

		                    case "Custom Seal":
		                        cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                        break;

		                    case "Gate Out Condition":
		                        cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                        break;

		                    default:
		                        cell.setCellValue(""); // Handle undefined columns
		                        break;
		                }




		            }
		        }
		       
//		        // Create a font for bold text
//		       
//		        
//		      
//
//		        
//		     /// Create a row for spacing before totals
//		     // Create a row for spacing before totals
//		        Row emptyRow = sheet.createRow(rowNum++);
//		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
//		        Row empty = sheet.createRow(rowNum++);
//		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank
//		        
//		        		
//		        		
//		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
//		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
//
//		        // Create a row for totals
////		        Row totalRow = sheet.createRow(rowNum+ 2);
//
//		        // Header row for summary
//		        String[] columnsHeaderSummery = {
//		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
//		        };
//
//		        // Create a map to store container count for each agent
//		        Map<String, int[]> agentContainerCount = new HashMap<>();
//
//		        // Iterate over importDetails and count the container sizes for each shipping agent
//		        importDetails.forEach(i -> {
//		            String agent = i[12] != null ? i[12].toString() : "Unknown"; // Shipping Agent at index 10
//		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3
//
//		            // Initialize counts for this agent if not already present
//		            if (!agentContainerCount.containsKey(agent)) {
//		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
//		            }
//
//		            // Increment the count based on container size
//		            switch (containerSize) {
//		                case "20":
//		                    agentContainerCount.get(agent)[0]++;
//		                    break;
//		                case "22":
//		                    agentContainerCount.get(agent)[1]++;
//		                    break;
//		                case "40":
//		                    agentContainerCount.get(agent)[2]++;
//		                    break;
//		                case "45":
//		                    agentContainerCount.get(agent)[3]++;
//		                    break;
//		                default:
//		                    break;
//		            }
//		        });
//
//		        // Apply header row styling and set values
//		        Row headerRow1 = sheet.createRow(rowNum++);
//		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		            Cell cell = headerRow1.createCell(i);
//		            cell.setCellValue(columnsHeaderSummery[i]);
//
//		            // Set cell style for header
//		            CellStyle headerStyle = workbook.createCellStyle();
//		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		            Font headerFont = workbook.createFont();
//		            headerFont.setBold(true);
//		            headerFont.setFontHeightInPoints((short) 11);
//		            headerStyle.setFont(headerFont);
//		            headerStyle.setBorderBottom(BorderStyle.THIN);
//		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//		            headerStyle.setBorderTop(BorderStyle.THIN);
//		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		            headerStyle.setBorderLeft(BorderStyle.THIN);
//		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//		            headerStyle.setBorderRight(BorderStyle.THIN);
//		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//
//		            headerFont.setColor(IndexedColors.WHITE.getIndex());
//		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
//		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		            cell.setCellStyle(headerStyle);
//		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
//		            sheet.setColumnWidth(i, headerWidth);
//		        }
//
//		        // Iterate over the map to create rows for each shipping agent and their container count
//		        int srNo = 1;
//		        
//		        String shippingAgent =null;
//		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
//		             shippingAgent = entry.getKey();
//		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45
//
//		            // Create a new row for this shipping agent
//		            Row row = sheet.createRow(rowNum++);
//		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
//		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
//		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
//		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
//		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
//		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
//		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
////		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
//		            
//		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
//		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
//		        }
//
//
//		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
//		        for (int[] sizes : agentContainerCount.values()) {
//		            totalSize20 += sizes[0];
//		            totalSize22 += sizes[1];
//		            totalSize40 += sizes[2];
//		            totalSize45 += sizes[3];
//		        }
//
//		     // Create a row for the totals
//		        Row totalRow = sheet.createRow(rowNum++);
//
//		        // Set the values for the total row
//		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
//		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
//		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
//		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
//		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
//		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers
//
//		        // Calculate and set the total TUE'S value
//		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
//		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value
//
//		     // Apply the total row style (light green background and bold font)
//
//		     // Create a CellStyle for the background color
//		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
//		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
//		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
//		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		     // Apply the style to the cells of the total row
//		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		         // Ensure the cell exists before applying style
//		         Cell cell = totalRow.getCell(i);
//		         if (cell == null) {
//		             cell = totalRow.createCell(i);
//		         }
//		         cell.setCellStyle(totalRowStyle);
//		     }
//
//		     // Create a font for bold text
//		     Font boldFont1 = workbook.createFont();
//		     boldFont1.setBold(true);
//		     totalRowStyle.setFont(boldFont1);
//
//		     // Apply bold font and other styling
//		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		         Cell cell = totalRow.getCell(i);
//		         cell.setCellStyle(totalRowStyle);
//		     }






		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  36 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        
		        sheet.setColumnWidth(21,18 * 306); 
		        sheet.setColumnWidth(22,18 * 306); 
		        sheet.setColumnWidth(23,18 * 306); 
		        sheet.setColumnWidth(24,18 * 306); 
		        sheet.setColumnWidth(25,18 * 306); 
		        sheet.setColumnWidth(26,18 * 306); 
		        sheet.setColumnWidth(27,18 * 306); 
		       
		        
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
	
	
	
	
	
	
	public byte[] createExcelReportExportFCLGateInInExportSection(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

		        
		        List<Object[]> importDetails = commonReportsRepo.getExportFCLGateInReport(companyId, branchId,startDate, endDate);
		        
		        
		        Sheet sheet = workbook.createSheet("Export FCL Gate In Report");

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
		        	    "Mode",                // This corresponds to "Mode"
		        	    "Work Order No.",      // This corresponds to "Work Order No."
		        	    "Work Order Date",     // This corresponds to "Work Order Date"
		        	    "Gate In Date",        // This corresponds to "Gate In Date"
		        	    "Vehicle",             // This corresponds to "Vehicle"
		        	    "Container No",       // This corresponds to "Container No."
		        	    "Size",                // This corresponds to "Size"
		        	    "Type",                // This corresponds to "Type"
		        	    "Cargo Wt",           // This corresponds to "Cargo Wt."
		        	    "TareWt",              // This corresponds to "TareWt"
		        	    "Account Holder",      // This corresponds to "Account Holder"
		        	    "CHA",                 // This corresponds to "CHA"
		        	    "Line",                // This corresponds to "Line"
		        	    "Sr.No Buffer",        // This corresponds to "Sr.No Buffer"
		        	    "Sr.No Factory",       // This corresponds to "Sr.No Factory"
		        	    "Gate In Condition",   // This corresponds to "Gate In Condition"
		        	    "Gate Out Date",       // This corresponds to "Gate Out Date"
		        	    "Shipping Bill No.",   // This corresponds to "Shipping Bill No."
		        	    "Shipping Bill Date",  // This corresponds to "Shipping Bill Date"
		        	    "No Of Packages",      // This corresponds to "No Of Packages"
		        	    "Weight",              // This corresponds to "Weight"
		        	    "Vessel",              // This corresponds to "Vessel"
		        	    "POD",                 // This corresponds to "POD"
		        	    "POL",                 // This corresponds to "POL"
		        	    "Agent Seal",          // This corresponds to "Agent Seal"
		        	    "Custom Seal",         // This corresponds to "Custom Seal"
		        	    "Gate Out Condition"   // This corresponds to "Gate Out Condition"
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export FCL Gate In Report" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export FCL Gate In Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export FCL Gate In Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		        for (int i = 0; i < columnsHeader.length; i++) 
		        {
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

		                    case "Mode":
		                        cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                        break;

		                    case "Work Order No.":
		                        cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                        break;

		                    case "Work Order Date":
		                        if (resultData1[2] != null) {
		                            cell.setCellValue(resultData1[2].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Gate In Date":
		                        if (resultData1[3] != null) {
		                            cell.setCellValue(resultData1[3].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Vehicle":
		                        cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                        break;

		                    case "Container No":
		                        cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                        break;

		                    case "Size":
		                        cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                        break;

		                    case "Type":
		                        cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                        break;

		                    case "Cargo Wt":
		                        if (resultData1[8] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[8].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[8].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;

		                    case "TareWt":
		                        if (resultData1[9] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[9].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[9].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;


		                    case "Account Holder":
		                        cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                        break;

		                    case "CHA":
		                        cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                        break;

		                    case "Line":
		                        cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                        break;

		                    case "Sr.No Buffer":
		                        cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                        break;

		                    case "Sr.No Factory":
		                        cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                        break;

		                    case "Gate In Condition":
		                        cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                        break;

		                    case "Gate Out Date":
		                        if (resultData1[16] != null) {
		                            cell.setCellValue(resultData1[16].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Shipping Bill No.":
		                        cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                        break;

		                    case "Shipping Bill Date":
		                        if (resultData1[18] != null) {
		                            cell.setCellValue(resultData1[18].toString());
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "No Of Packages":
		                        if (resultData1[19] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[19].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[19].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;

		                    case "Weight":
		                        if (resultData1[20] != null) {
		                            try {
		                                double numericValue = Double.parseDouble(resultData1[20].toString());
		                                cell.setCellValue(numericValue); // Set numeric value
		                                cell.setCellStyle(numberCellStyle); // Apply numeric style
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(resultData1[20].toString()); // Fallback to string if parsing fails
		                            }
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;


		                    case "Vessel":
		                        cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                        break;

		                    case "POD":
		                        cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                        break;

		                    case "POL":
		                        cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                        break;

		                    case "Agent Seal":
		                        cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                        break;

		                    case "Custom Seal":
		                        cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                        break;

		                    case "Gate Out Condition":
		                        cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                        break;

		                    default:
		                        cell.setCellValue(""); // Handle undefined columns
		                        break;
		                }




		            }
		        }
		       
//		        // Create a font for bold text
//		       
//		        
//		      
//
//		        
//		     /// Create a row for spacing before totals
//		     // Create a row for spacing before totals
//		        Row emptyRow = sheet.createRow(rowNum++);
//		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
//		        Row empty = sheet.createRow(rowNum++);
//		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank
//		        
//		        		
//		        		
//		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
//		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
//
//		        // Create a row for totals
////		        Row totalRow = sheet.createRow(rowNum+ 2);
//
//		        // Header row for summary
//		        String[] columnsHeaderSummery = {
//		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
//		        };
//
//		        // Create a map to store container count for each agent
//		        Map<String, int[]> agentContainerCount = new HashMap<>();
//
//		        // Iterate over importDetails and count the container sizes for each shipping agent
//		        importDetails.forEach(i -> {
//		            String agent = i[12] != null ? i[12].toString() : "Unknown"; // Shipping Agent at index 10
//		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3
//
//		            // Initialize counts for this agent if not already present
//		            if (!agentContainerCount.containsKey(agent)) {
//		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
//		            }
//
//		            // Increment the count based on container size
//		            switch (containerSize) {
//		                case "20":
//		                    agentContainerCount.get(agent)[0]++;
//		                    break;
//		                case "22":
//		                    agentContainerCount.get(agent)[1]++;
//		                    break;
//		                case "40":
//		                    agentContainerCount.get(agent)[2]++;
//		                    break;
//		                case "45":
//		                    agentContainerCount.get(agent)[3]++;
//		                    break;
//		                default:
//		                    break;
//		            }
//		        });
//
//		        // Apply header row styling and set values
//		        Row headerRow1 = sheet.createRow(rowNum++);
//		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		            Cell cell = headerRow1.createCell(i);
//		            cell.setCellValue(columnsHeaderSummery[i]);
//
//		            // Set cell style for header
//		            CellStyle headerStyle = workbook.createCellStyle();
//		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		            Font headerFont = workbook.createFont();
//		            headerFont.setBold(true);
//		            headerFont.setFontHeightInPoints((short) 11);
//		            headerStyle.setFont(headerFont);
//		            headerStyle.setBorderBottom(BorderStyle.THIN);
//		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//		            headerStyle.setBorderTop(BorderStyle.THIN);
//		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		            headerStyle.setBorderLeft(BorderStyle.THIN);
//		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//		            headerStyle.setBorderRight(BorderStyle.THIN);
//		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//
//		            headerFont.setColor(IndexedColors.WHITE.getIndex());
//		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
//		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		            cell.setCellStyle(headerStyle);
//		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
//		            sheet.setColumnWidth(i, headerWidth);
//		        }
//
//		        // Iterate over the map to create rows for each shipping agent and their container count
//		        int srNo = 1;
//		        
//		        String shippingAgent =null;
//		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
//		             shippingAgent = entry.getKey();
//		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45
//
//		            // Create a new row for this shipping agent
//		            Row row = sheet.createRow(rowNum++);
//		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
//		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
//		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
//		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
//		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
//		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
//		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
////		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
//		            
//		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
//		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
//		        }
//
//
//		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
//		        for (int[] sizes : agentContainerCount.values()) {
//		            totalSize20 += sizes[0];
//		            totalSize22 += sizes[1];
//		            totalSize40 += sizes[2];
//		            totalSize45 += sizes[3];
//		        }
//
//		     // Create a row for the totals
//		        Row totalRow = sheet.createRow(rowNum++);
//
//		        // Set the values for the total row
//		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
//		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
//		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
//		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
//		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
//		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers
//
//		        // Calculate and set the total TUE'S value
//		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
//		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value
//
//		     // Apply the total row style (light green background and bold font)
//
//		     // Create a CellStyle for the background color
//		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
//		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
//		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
//		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//		     // Apply the style to the cells of the total row
//		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		         // Ensure the cell exists before applying style
//		         Cell cell = totalRow.getCell(i);
//		         if (cell == null) {
//		             cell = totalRow.createCell(i);
//		         }
//		         cell.setCellStyle(totalRowStyle);
//		     }
//
//		     // Create a font for bold text
//		     Font boldFont1 = workbook.createFont();
//		     boldFont1.setBold(true);
//		     totalRowStyle.setFont(boldFont1);
//
//		     // Apply bold font and other styling
//		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		         Cell cell = totalRow.getCell(i);
//		         cell.setCellStyle(totalRowStyle);
//		     }






		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  36 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        
		        sheet.setColumnWidth(21,18 * 306); 
		        sheet.setColumnWidth(22,18 * 306); 
		        sheet.setColumnWidth(23,18 * 306); 
		        sheet.setColumnWidth(24,18 * 306); 
		        sheet.setColumnWidth(25,18 * 306); 
		        sheet.setColumnWidth(26,18 * 306); 
		        sheet.setColumnWidth(27,18 * 306); 
		       
		        
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
	
	
	
	
	
	
	public byte[] createExcelReportExportMovementOutInExportSection(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

		        
		        List<Object[]> importDetails = commonReportsRepo.findExportMovementGateOutData(companyId, branchId,startDate, endDate);
		        
		        
		        Sheet sheet = workbook.createSheet("Export Movement Gate Out Report");

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
		                "Sr No",               // Serial Number
		                "Cont No",             // Container Number
		                "Cont Size",           // Container Size
		                "Cont Type",           // Container Type
		                "Commodity",           // Commodity
		                "Stuff Date",          // Stuffing Date
		                "Gate Out Date",       // Gate Out Date
		                "Account Holder",      // Account Holder
		                "Shipping Line",       // Shipping Line
		                "Vessel Name",         // Vessel Name
		                "Port",                // Port
		                "Custom Seal",         // Custom Seal
		                "Agent Seal",          // Agent Seal
		                "VCN No",              // VCN Number
		                "Stuff Package",       // Stuff Package
		                "Stuff Weight",        // Stuff Weight
		                "Tare Weight",         // Tare Weight
		                "Transporter",         // Transporter
		                "Low Bed",             // Low Bed
		                "Vehicle No",          // Vehicle Number
		                "Empty In Date",       // Empty In Date
		                "Empty Pickup Yard",   // Empty Pickup Yard
		                "Process Type",        // Process Type
		                "Remarks",             // Remarks
		                "Invoice No"           // Invoice Number
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Movement Gate Out Report" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Movement Gate Out Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Movement Gate Out Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		        for (int i = 0; i < columnsHeader.length; i++) 
		        {
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

		                case "Cont No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Commodity":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Stuff Date":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Gate Out Date":
		                    if (resultData1[5] != null) {
		                        cell.setCellValue(resultData1[5].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Account Holder":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Shipping Line":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Port":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Custom Seal":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Agent Seal":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "VCN No":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Stuff Package":
		                	if (resultData1[13] != null) {
		                        try {
		                            double numericValue = Double.parseDouble(resultData1[13].toString());
		                            cell.setCellValue(numericValue); // Set numeric value
		                            cell.setCellStyle(numberCellStyle); // Apply numeric style
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue(resultData1[13].toString()); // Fallback to string if parsing fails
		                        }
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Stuff Weight":
		                    if (resultData1[14] != null) {
		                        try {
		                            double numericValue = Double.parseDouble(resultData1[14].toString());
		                            cell.setCellValue(numericValue); // Set numeric value
		                            cell.setCellStyle(numberCellStyle); // Apply numeric style
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue(resultData1[14].toString()); // Fallback to string if parsing fails
		                        }
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Tare Weight":
		                    if (resultData1[15] != null) {
		                        try {
		                            double numericValue = Double.parseDouble(resultData1[15].toString());
		                            cell.setCellValue(numericValue); // Set numeric value
		                            cell.setCellStyle(numberCellStyle); // Apply numeric style
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue(resultData1[15].toString()); // Fallback to string if parsing fails
		                        }
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Transporter":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Low Bed":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Vehicle No":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Empty In Date":
		                    if (resultData1[19] != null) {
		                        cell.setCellValue(resultData1[19].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Empty Pickup Yard":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "Process Type":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "Remarks":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "Invoice No":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }





		            }
		        }
		       
//		        // Create a font for bold text
//		       
//		        
//		      
//
//		        
		     /// Create a row for spacing before totals
		     // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank
		        
		        		
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        // Create a row for totals
//		        Row totalRow = sheet.createRow(rowNum+ 2);

		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();

		        // Iterate over importDetails and count the container sizes for each shipping agent
		        importDetails.forEach(i -> {
		            String agent = i[29] != null ? i[29].toString() : "Unknown"; // Shipping Agent at index 10
		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

		            // Initialize counts for this agent if not already present
		            if (!agentContainerCount.containsKey(agent)) {
		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
		            }

		            // Increment the count based on container size
		            switch (containerSize) {
		                case "20":
		                    agentContainerCount.get(agent)[0]++;
		                    break;
		                case "22":
		                    agentContainerCount.get(agent)[1]++;
		                    break;
		                case "40":
		                    agentContainerCount.get(agent)[2]++;
		                    break;
		                case "45":
		                    agentContainerCount.get(agent)[3]++;
		                    break;
		                default:
		                    break;
		            }
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
		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		     // Create a row for the totals
		        Row totalRow = sheet.createRow(rowNum++);

		        // Set the values for the total row
		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers

		        // Calculate and set the total TUE'S value
		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value

		     // Apply the total row style (light green background and bold font)

		     // Create a CellStyle for the background color
		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		     // Apply the style to the cells of the total row
		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
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
		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
		         Cell cell = totalRow.getCell(i);
		         cell.setCellStyle(totalRowStyle);
		     }






		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 36 * 306); 
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
		        
		        sheet.setColumnWidth(21,18 * 306); 
		        sheet.setColumnWidth(22,18 * 306); 
		        sheet.setColumnWidth(23,18 * 306); 
		        sheet.setColumnWidth(24,18 * 306); 
		        sheet.setColumnWidth(25,18 * 306); 
		        sheet.setColumnWidth(26,18 * 306); 
		        sheet.setColumnWidth(27,18 * 306); 
		       
		        
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

	
	
	
	public byte[] createExportLDDPendencyReportExcle(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException 
	{

        	 try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
 		    {
 		    	System.out.println("startDate_______________________"+startDate);
 		    	
 		    	
 		    	System.out.println("endDate_______________________"+endDate);
 		    	
 		    	
 		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

 		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
 		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

 		        Calendar cal = Calendar.getInstance();

 		     // Set startDate to 00:00 if the time component is not set
 		     if (startDate != null) {
 		         cal.setTime(startDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 0);
 		         cal.set(Calendar.MINUTE, 0);
 		         cal.set(Calendar.SECOND, 0);
 		         cal.set(Calendar.MILLISECOND, 0);
 		         startDate = cal.getTime();
 		     }

 		     // Set endDate to 23:59 if the time component is not set
 		     if (endDate != null) {
 		         cal.setTime(endDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 23);
 		         cal.set(Calendar.MINUTE, 59);
 		         cal.set(Calendar.SECOND, 59);
 		         cal.set(Calendar.MILLISECOND, 999);
 		         endDate = cal.getTime();
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
 		        


 		        
 		        List<Object[]> importDetails = commonReportsRepo.findExportLDDPendencyContainerDetails(companyId, branchId, endDate);
 		        
 		        
 		        Sheet sheet = workbook.createSheet("Export LDD Pendency Report");

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
 		    		    "Sr No",               // Replacing "SR NO"
 		    		    "Container No",        // Replacing "Container No"
 		    		    "Size",                // Replacing "Size"
 		    		    "CtrType",             // Replacing "Type" with "CtrType"
 		    		    "In Date",             // Replacing "CtrMode" with "In Date"
 		    		    "AgentName",           // Replacing "Port" with "AgentName"
 		    		    "Linecd",              // Replacing "Sysdate" with "Linecd"
 		    		    "Agentcd",             // Replacing "Vsl Berthing Date" with "Agentcd"
 		    		    "Stf Date",            // Replacing "Days" with "Stf Date"
 		    		    "Movement Ord Date",   // Replacing "IgmSealNo" with "Movement Ord Date"
 		    		    "Rcpt Date",           // Replacing "Agent" with "Rcpt Date"
 		    		    "Remark",              // Replacing "Vessel" with "Remark"
 		    		    "PORT_NAME",           // Replacing "VCN" with "PORT_NAME"
 		    		    "CYCLE Type"           // Replacing "Igm" with "CYCLE Type"
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
 		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
 		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

 		        
 		        Row branchRow1 = sheet.createRow(2);
 		        Cell branchCell1 = branchRow1.createCell(0);
// 		        branchCell1.setCellValue(branchAdd);
 		        CellStyle branchStyle1 = workbook.createCellStyle();
 		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
 		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font branchFont1 = workbook.createFont();
 		        branchFont1.setFontHeightInPoints((short) 12);
 		        branchStyle1.setFont(branchFont1);
 		        branchCell1.setCellStyle(branchStyle1);
 		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
 	
 		        // Add Report Title "Bond cargo Inventory Report"
 		        Row reportTitleRow = sheet.createRow(3);
 		        Cell reportTitleCell = reportTitleRow.createCell(0);
 		        reportTitleCell.setCellValue("Export LDD Pendency Report");

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
 		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
 		        		        
 		        Row reportTitleRow1 = sheet.createRow(4);
 		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
// 		        if(formattedStartDate.equals("N/A"))
// 		        {
 		        	 reportTitleCell1.setCellValue("Export LDD Pendency Report As On Date : " + formattedEndDate);
// 		        }
// 		        else 
// 		        {
// 		        	 reportTitleCell1.setCellValue("Export LDD Pendency Report From : " + formattedStartDate + " to " + formattedEndDate);
// 		        }
 		       

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

 		        for (int i = 0; i < columnsHeader.length; i++) 
 		        {
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

 		              case "Container No":
 		                  cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
 		                  break;

 		              case "Size":
 		                  cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
 		                  break;

 		              case "CtrType":
 		                  cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
 		                  break;

 		              case "In Date":
 		                  if (resultData1[3] != null) {
 		                      cell.setCellValue(resultData1[3].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "AgentName":
 		                  cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
 		                  break;

 		              case "Linecd":
 		                  cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
 		                  break;

 		              case "Agentcd":
 		                  cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
 		                  break;

 		              case "Stf Date":
 		                  if (resultData1[7] != null) {
 		                      cell.setCellValue(resultData1[7].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "Movement Ord Date":
 		                  if (resultData1[8] != null) {
 		                      cell.setCellValue(resultData1[8].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "Rcpt Date":
 		                  if (resultData1[9] != null) {
 		                      cell.setCellValue(resultData1[9].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "Remark":
 		                  cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
 		                  break;

 		              case "PORT_NAME":
 		                  cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
 		                  break;

 		              case "CYCLE Type":
 		                  cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
 		                  break;

 		              default:
 		                  cell.setCellValue(""); // Handle undefined columns
 		                  break;
 		          }


 		            }
 		        }
 		       
 		        // Create a font for bold text
 		     /// Create a row for spacing before totals
 		     // Create a row for spacing before totals
 		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summary Shipping Agent Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Create a row for totals
// 		        Row totalRow = sheet.createRow(rowNum+ 2);

 		        // Header row for summary
 		        String[] columnsHeaderSummery = {
 		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		        };

 		        // Create a map to store container count for each agent
 		        Map<String, int[]> agentContainerCount = new HashMap<>();

 		        // Iterate over importDetails and count the container sizes for each shipping agent
 		        importDetails.forEach(i -> {
 		            String agent = i[4] != null ? i[4].toString() : "Unknown"; // Shipping Agent at index 10
 		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		            // Initialize counts for this agent if not already present
 		            if (!agentContainerCount.containsKey(agent)) {
 		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		            }

 		            // Increment the count based on container size
 		            switch (containerSize) {
 		                case "20":
 		                    agentContainerCount.get(agent)[0]++;
 		                    break;
 		                case "22":
 		                    agentContainerCount.get(agent)[1]++;
 		                    break;
 		                case "40":
 		                    agentContainerCount.get(agent)[2]++;
 		                    break;
 		                case "45":
 		                    agentContainerCount.get(agent)[3]++;
 		                    break;
 		                default:
 		                    break;
 		            }
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
 		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		            cell.setCellStyle(headerStyle);
 		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
 		            sheet.setColumnWidth(i, headerWidth);
 		        }

 		        // Iterate over the map to create rows for each shipping agent and their container count
 		        int srNo = 1;
 		        
 		        String shippingAgent =null;
 		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
 		             shippingAgent = entry.getKey();
 		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		            // Create a new row for this shipping agent
 		            Row row = sheet.createRow(rowNum++);
 		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
 		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
 		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
// 		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
 		            
 		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		        }


 		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
 		        for (int[] sizes : agentContainerCount.values()) {
 		            totalSize20 += sizes[0];
 		            totalSize22 += sizes[1];
 		            totalSize40 += sizes[2];
 		            totalSize45 += sizes[3];
 		        }

 		     // Create a row for the totals
 		        Row totalRow = sheet.createRow(rowNum++);

 		        // Set the values for the total row
 		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
 		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
 		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
 		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
 		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
 		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers

 		        // Calculate and set the total TUE'S value
 		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
 		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value

 		     // Apply the total row style (light green background and bold font)

 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
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
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary Port Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		     String[] columnsHeaderVesselSummery = {
 		         "Sr No", "Port", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		     };

 		     // Create a map to store container count for each vessel
 		     Map<String, int[]> vesselContainerCount = new HashMap<>();

 		     // Iterate over importDetails and count the container sizes for each vessel
 		     importDetails.forEach(i -> {
 		         String vessel = i[11] != null ? i[11].toString() : "Unknown"; // Vessel at index 10
 		         String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		         // Initialize counts for this vessel if not already present
 		         if (!vesselContainerCount.containsKey(vessel)) {
 		             vesselContainerCount.put(vessel, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		         }

 		         // Increment the count based on container size
 		         switch (containerSize) {
 		             case "20":
 		                 vesselContainerCount.get(vessel)[0]++;
 		                 break;
 		             case "22":
 		                 vesselContainerCount.get(vessel)[1]++;
 		                 break;
 		             case "40":
 		                 vesselContainerCount.get(vessel)[2]++;
 		                 break;
 		             case "45":
 		                 vesselContainerCount.get(vessel)[3]++;
 		                 break;
 		             default:
 		                 break;
 		         }
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

 		     // Iterate over the map to create rows for each vessel and their container count
 		     int srNoVessel = 1;
 		     for (Map.Entry<String, int[]> entry : vesselContainerCount.entrySet()) {
 		         String vessel = entry.getKey();
 		         int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		         // Create a new row for this vessel
 		         Row row = sheet.createRow(rowNum++);
 		         row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		         row.createCell(1).setCellValue(vessel); // Vessel Name
 		         row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		         row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		         row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		         row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		         row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers

 		         // Calculate and set the TUE'S value
 		         int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		         row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		     }

 		     // Calculate totals for all vessel data
 		     int totalVesselSize20 = 0, totalVesselSize22 = 0, totalVesselSize40 = 0, totalVesselSize45 = 0;
 		     for (int[] sizes : vesselContainerCount.values()) {
 		         totalVesselSize20 += sizes[0];
 		         totalVesselSize22 += sizes[1];
 		         totalVesselSize40 += sizes[2];
 		         totalVesselSize45 += sizes[3];
 		     }

 		     // Create a row for the totals of Vessel Wise Summary
 		     Row totalVesselRow = sheet.createRow(rowNum++);

 		     // Set the values for the total row
 		     totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
 		     totalVesselRow.createCell(2).setCellValue(totalVesselSize20); // Total Size 20
 		     totalVesselRow.createCell(3).setCellValue(totalVesselSize22); // Total Size 22
 		     totalVesselRow.createCell(4).setCellValue(totalVesselSize40); // Total Size 40
 		     totalVesselRow.createCell(5).setCellValue(totalVesselSize45); // Total Size 45
 		     totalVesselRow.createCell(6).setCellValue(totalVesselSize20 + totalVesselSize22 + totalVesselSize40 + totalVesselSize45); // Total of all containers

 		     // Calculate and set the total TUE'S value for Vessel Wise
 		     int totalVesselTuesValue = (totalVesselSize20 * 1) + (totalVesselSize22 * 1) + (totalVesselSize40 * 2) + (totalVesselSize45 * 2);
 		     totalVesselRow.createCell(7).setCellValue(totalVesselTuesValue); // Total TUE'S value

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




 		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
 		        sheet.setColumnWidth(0,  9 * 306); 
 		        sheet.setColumnWidth(1, 18 * 306); 
 		        sheet.setColumnWidth(2, 9 * 306); 
 		        sheet.setColumnWidth(3, 18 * 306); 
 		        
 		        sheet.setColumnWidth(4, 18 * 306); 
 		        sheet.setColumnWidth(5, 36 * 306); 
 		        
 		        sheet.setColumnWidth(6, 18 * 306); 
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
	
	
	
	
	public byte[] createExportStuffingTallyReportExcle(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException 
	{

        	 try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
 		    {
 		    	System.out.println("startDate_______________________"+startDate);
 		    	
 		    	
 		    	System.out.println("endDate_______________________"+endDate);
 		    	
 		    	
 		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

 		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
 		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

 		        Calendar cal = Calendar.getInstance();

 		     // Set startDate to 00:00 if the time component is not set
 		     if (startDate != null) {
 		         cal.setTime(startDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 0);
 		         cal.set(Calendar.MINUTE, 0);
 		         cal.set(Calendar.SECOND, 0);
 		         cal.set(Calendar.MILLISECOND, 0);
 		         startDate = cal.getTime();
 		     }

 		     // Set endDate to 23:59 if the time component is not set
 		     if (endDate != null) {
 		         cal.setTime(endDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 23);
 		         cal.set(Calendar.MINUTE, 59);
 		         cal.set(Calendar.SECOND, 59);
 		         cal.set(Calendar.MILLISECOND, 999);
 		         endDate = cal.getTime();
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
 		        


 		        
 		        List<Object[]> importDetails = commonReportsRepo.getStuffTallyData(companyId, branchId, startDate,endDate);
 		        
 		        
 		        Sheet sheet = workbook.createSheet("Export Stuffing Tally Report");

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
 		    		    "SR NO",             // Replacing "Sr No"
 		    		    "Container No",      // Replacing "Container No"
 		    		    "Size",              // Replacing "Size"
 		    		    "CtrType",           // Replacing "CtrType"
 		    		    "In Date",           // Replacing "In Date"
 		    		    "AgentName",         // Replacing "AgentName"
 		    		    "Line",              // Replacing "Linecd" with "Line"
 		    		    "Agentcd",           // Replacing "Agentcd"
 		    		    "CustomSeal",        // Replacing "Stf Date" with "CustomSeal"
 		    		    "VCN",               // Replacing "Movement Ord Date" with "VCN"
 		    		    "POD",               // Replacing "Rcpt Date" with "POD"
 		    		    "POL",               // Replacing "Remark" with "POL"
 		    		    "ActualWt",          // Replacing "PORT_NAME" with "ActualWt"
 		    		    "TareWt",            // Replacing "CYCLE Type" with "TareWt"
 		    		    "Tly Sht No",        // Replacing "Tly Sht No"
 		    		    "Tly Sht Date",      // Replacing "Tly Sht Date"
 		    		    "Vessel",            // Replacing "Vessel"
 		    		    "LabCode",           // Replacing "LabCode"
 		    		    "Stuff Date",        // Replacing "stuff date"
 		    		    "Remark"             // Replacing "remark"
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
 		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
 		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

 		        
 		        Row branchRow1 = sheet.createRow(2);
 		        Cell branchCell1 = branchRow1.createCell(0);
// 		        branchCell1.setCellValue(branchAdd);
 		        CellStyle branchStyle1 = workbook.createCellStyle();
 		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
 		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font branchFont1 = workbook.createFont();
 		        branchFont1.setFontHeightInPoints((short) 12);
 		        branchStyle1.setFont(branchFont1);
 		        branchCell1.setCellStyle(branchStyle1);
 		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
 	
 		        // Add Report Title "Bond cargo Inventory Report"
 		        Row reportTitleRow = sheet.createRow(3);
 		        Cell reportTitleCell = reportTitleRow.createCell(0);
 		        reportTitleCell.setCellValue("Export Stuffing Tally Report");

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
 		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
 		        		        
 		        Row reportTitleRow1 = sheet.createRow(4);
 		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
// 		        if(formattedStartDate.equals("N/A"))
// 		        {
 		        	 reportTitleCell1.setCellValue("Export Stuffing Tally Report As On Date : " + formattedEndDate);
// 		        }
// 		        else 
// 		        {
// 		        	 reportTitleCell1.setCellValue("Export Stuffing Tally Report From : " + formattedStartDate + " to " + formattedEndDate);
// 		        }
 		       

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

 		        for (int i = 0; i < columnsHeader.length; i++) 
 		        {
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

 		              case "Container No":
 		                  cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
 		                  break;

 		              case "Size":
 		                  cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
 		                  break;

 		              case "CtrType":
 		                  cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
 		                  break;

 		              case "In Date":
 		                  if (resultData1[3] != null) {
 		                      cell.setCellValue(resultData1[3].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "AgentName":
 		                  cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
 		                  break;

 		              case "Line":
 		                  cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
 		                  break;

 		              case "Agentcd":
 		                  cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
 		                  break;

 		              case "CustomSeal":
 		                  cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
 		                  break;

 		              case "VCN":
 		                  cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
 		                  break;

 		              case "POD":
 		                  cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
 		                  break;

 		              case "POL":
 		                  cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
 		                  break;

 		             case "ActualWt":
 		                if (resultData1[11] != null) {
 		                    cell.setCellValue(Double.parseDouble(resultData1[11].toString()));
 		                    cell.setCellStyle(numberCellStyle); // Apply numeric style
 		                } else {
 		                    cell.setCellValue(0.0); // Default value if null
 		                    cell.setCellStyle(numberCellStyle); // Apply numeric style
 		                }
 		                break;

 		            case "TareWt":
 		                if (resultData1[12] != null) {
 		                    cell.setCellValue(Double.parseDouble(resultData1[12].toString()));
 		                    cell.setCellStyle(numberCellStyle); // Apply numeric style
 		                } else {
 		                    cell.setCellValue(0.0); // Default value if null
 		                    cell.setCellStyle(numberCellStyle); // Apply numeric style
 		                }
 		                break;

 		              case "Tly Sht No":
 		                  cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
 		                  break;

 		              case "Tly Sht Date":
 		                  if (resultData1[14] != null) {
 		                      cell.setCellValue(resultData1[14].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "Vessel":
 		                  cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
 		                  break;

 		              case "LabCode":
 		                  cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
 		                  break;

 		              case "Stuff Date":
 		                  if (resultData1[17] != null) {
 		                      cell.setCellValue(resultData1[17].toString());
 		                      cell.setCellStyle(dateCellStyle);
 		                  } else {
 		                      cell.setBlank();
 		                      cell.setCellStyle(dateCellStyle);
 		                  }
 		                  break;

 		              case "Remark":
 		                  cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
 		                  break;

 		              default:
 		                  cell.setCellValue(""); // Handle undefined columns
 		                  break;
 		          }


 		            }
 		        }
 		       
 		        // Create a font for bold text
 		     /// Create a row for spacing before totals
 		     // Create a row for spacing before totals
 		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summary Shipping Agent Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Create a row for totals
// 		        Row totalRow = sheet.createRow(rowNum+ 2);

 		        // Header row for summary
 		        String[] columnsHeaderSummery = {
 		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		        };

 		        // Create a map to store container count for each agent
 		        Map<String, int[]> agentContainerCount = new HashMap<>();

 		        // Iterate over importDetails and count the container sizes for each shipping agent
 		        importDetails.forEach(i -> {
 		            String agent = i[4] != null ? i[4].toString() : "Unknown"; // Shipping Agent at index 10
 		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		            // Initialize counts for this agent if not already present
 		            if (!agentContainerCount.containsKey(agent)) {
 		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		            }

 		            // Increment the count based on container size
 		            switch (containerSize) {
 		                case "20":
 		                    agentContainerCount.get(agent)[0]++;
 		                    break;
 		                case "22":
 		                    agentContainerCount.get(agent)[1]++;
 		                    break;
 		                case "40":
 		                    agentContainerCount.get(agent)[2]++;
 		                    break;
 		                case "45":
 		                    agentContainerCount.get(agent)[3]++;
 		                    break;
 		                default:
 		                    break;
 		            }
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
 		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		            cell.setCellStyle(headerStyle);
 		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
 		            sheet.setColumnWidth(i, headerWidth);
 		        }

 		        // Iterate over the map to create rows for each shipping agent and their container count
 		        int srNo = 1;
 		        
 		        String shippingAgent =null;
 		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
 		             shippingAgent = entry.getKey();
 		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		            // Create a new row for this shipping agent
 		            Row row = sheet.createRow(rowNum++);
 		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
 		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
 		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
// 		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
 		            
 		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		        }


 		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
 		        for (int[] sizes : agentContainerCount.values()) {
 		            totalSize20 += sizes[0];
 		            totalSize22 += sizes[1];
 		            totalSize40 += sizes[2];
 		            totalSize45 += sizes[3];
 		        }

 		     // Create a row for the totals
 		        Row totalRow = sheet.createRow(rowNum++);

 		        // Set the values for the total row
 		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
 		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
 		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
 		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
 		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
 		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers

 		        // Calculate and set the total TUE'S value
 		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
 		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value

 		     // Apply the total row style (light green background and bold font)

 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
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
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary Port Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		     String[] columnsHeaderVesselSummery = {
 		         "Sr No", "Port", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		     };

 		     // Create a map to store container count for each vessel
 		     Map<String, int[]> vesselContainerCount = new HashMap<>();

 		     // Iterate over importDetails and count the container sizes for each vessel
 		     importDetails.forEach(i -> {
 		         String vessel = i[10] != null ? i[10].toString() : "Unknown"; // Vessel at index 10
 		         String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		         // Initialize counts for this vessel if not already present
 		         if (!vesselContainerCount.containsKey(vessel)) {
 		             vesselContainerCount.put(vessel, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		         }

 		         // Increment the count based on container size
 		         switch (containerSize) {
 		             case "20":
 		                 vesselContainerCount.get(vessel)[0]++;
 		                 break;
 		             case "22":
 		                 vesselContainerCount.get(vessel)[1]++;
 		                 break;
 		             case "40":
 		                 vesselContainerCount.get(vessel)[2]++;
 		                 break;
 		             case "45":
 		                 vesselContainerCount.get(vessel)[3]++;
 		                 break;
 		             default:
 		                 break;
 		         }
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

 		     // Iterate over the map to create rows for each vessel and their container count
 		     int srNoVessel = 1;
 		     for (Map.Entry<String, int[]> entry : vesselContainerCount.entrySet()) {
 		         String vessel = entry.getKey();
 		         int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		         // Create a new row for this vessel
 		         Row row = sheet.createRow(rowNum++);
 		         row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		         row.createCell(1).setCellValue(vessel); // Vessel Name
 		         row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		         row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		         row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		         row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		         row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers

 		         // Calculate and set the TUE'S value
 		         int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		         row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		     }

 		     // Calculate totals for all vessel data
 		     int totalVesselSize20 = 0, totalVesselSize22 = 0, totalVesselSize40 = 0, totalVesselSize45 = 0;
 		     for (int[] sizes : vesselContainerCount.values()) {
 		         totalVesselSize20 += sizes[0];
 		         totalVesselSize22 += sizes[1];
 		         totalVesselSize40 += sizes[2];
 		         totalVesselSize45 += sizes[3];
 		     }

 		     // Create a row for the totals of Vessel Wise Summary
 		     Row totalVesselRow = sheet.createRow(rowNum++);

 		     // Set the values for the total row
 		     totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
 		     totalVesselRow.createCell(2).setCellValue(totalVesselSize20); // Total Size 20
 		     totalVesselRow.createCell(3).setCellValue(totalVesselSize22); // Total Size 22
 		     totalVesselRow.createCell(4).setCellValue(totalVesselSize40); // Total Size 40
 		     totalVesselRow.createCell(5).setCellValue(totalVesselSize45); // Total Size 45
 		     totalVesselRow.createCell(6).setCellValue(totalVesselSize20 + totalVesselSize22 + totalVesselSize40 + totalVesselSize45); // Total of all containers

 		     // Calculate and set the total TUE'S value for Vessel Wise
 		     int totalVesselTuesValue = (totalVesselSize20 * 1) + (totalVesselSize22 * 1) + (totalVesselSize40 * 2) + (totalVesselSize45 * 2);
 		     totalVesselRow.createCell(7).setCellValue(totalVesselTuesValue); // Total TUE'S value

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




 		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
 		        sheet.setColumnWidth(0,  9 * 306); 
 		        sheet.setColumnWidth(1, 18 * 306); 
 		        sheet.setColumnWidth(2, 9 * 306); 
 		        sheet.setColumnWidth(3, 18 * 306); 
 		        
 		        sheet.setColumnWidth(4, 18 * 306); 
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
	
	
	
	
	public byte[] createExcelReportExportEmptyGateInInExportSection(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException {
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
		    {
		    	System.out.println("startDate_______________________"+startDate);
		    	
		    	
		    	System.out.println("endDate_______________________"+endDate);
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

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

		        
		        List<Object[]> importDetails = commonReportsRepo.getExportEmptyContainerGateInDetails(companyId, branchId,startDate, endDate);
		        
		        
		        Sheet sheet = workbook.createSheet("Export Empty Gate In Report");

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
		        	    "Sr No",            // Serial Number
		        	    "Container No",      // Container Number
		        	    "Size",              // Size of Container
		        	    "Container Type",           // Container Type
		        	    "In Date",           // Date of Entry
		        	    "Agent Name",         // Name of Agent
		        	    "Line Code",              // Line Information
		        	    "Agent Code",           // Agent Code
		        	    "Origin",            // Origin Information
		        	    "Remark"             // Remarks
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("Export Empty Gate In Report" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("Export Empty Gate In Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Export Empty Gate In Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		        for (int i = 0; i < columnsHeader.length; i++) 
		        {
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

		                case "Container No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : ""); // Container Number
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : ""); // Size
		                    break;

		                case "Container Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : ""); // Container Type
		                    break;

		                case "In Date":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString()); // In Date
		                        cell.setCellStyle(dateCellStyle); // Apply date style
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Agent Name":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : ""); // Agent Name
		                    break;

		                case "Line Code":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : ""); // Line Information
		                    break;

		                case "Agent Code":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : ""); // Agent Code
		                    break;

		                case "Origin":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : ""); // Origin Information
		                    break;

		                case "Remark":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : ""); // Remarks
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }




		            }
		        }
		       
//		        // Create a font for bold text
//		       
//		        
//		      
//
//		        
		     /// Create a row for spacing before totals
		     // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
		        

		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary"); // You can set a value or keep it blank
		        
		        		
		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        // Create a row for totals
//		        Row totalRow = sheet.createRow(rowNum+ 2);

		        // Header row for summary
		        String[] columnsHeaderSummery = {
		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
		        };

		        // Create a map to store container count for each agent
		        Map<String, int[]> agentContainerCount = new HashMap<>();

		        // Iterate over importDetails and count the container sizes for each shipping agent
		        importDetails.forEach(i -> {
		            String agent = i[4] != null ? i[4].toString() : "Unknown"; // Shipping Agent at index 10
		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

		            // Initialize counts for this agent if not already present
		            if (!agentContainerCount.containsKey(agent)) {
		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
		            }

		            // Increment the count based on container size
		            switch (containerSize) {
		                case "20":
		                    agentContainerCount.get(agent)[0]++;
		                    break;
		                case "22":
		                    agentContainerCount.get(agent)[1]++;
		                    break;
		                case "40":
		                    agentContainerCount.get(agent)[2]++;
		                    break;
		                case "45":
		                    agentContainerCount.get(agent)[3]++;
		                    break;
		                default:
		                    break;
		            }
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
		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Iterate over the map to create rows for each shipping agent and their container count
		        int srNo = 1;
		        
		        String shippingAgent =null;
		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
		             shippingAgent = entry.getKey();
		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

		            // Create a new row for this shipping agent
		            Row row = sheet.createRow(rowNum++);
		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
//		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
		            
		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
		        }


		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
		        for (int[] sizes : agentContainerCount.values()) {
		            totalSize20 += sizes[0];
		            totalSize22 += sizes[1];
		            totalSize40 += sizes[2];
		            totalSize45 += sizes[3];
		        }

		     // Create a row for the totals
		        Row totalRow = sheet.createRow(rowNum++);

		        // Set the values for the total row
		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers

		        // Calculate and set the total TUE'S value
		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value

		     // Apply the total row style (light green background and bold font)

		     // Create a CellStyle for the background color
		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		     // Apply the style to the cells of the total row
		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
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
		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
		         Cell cell = totalRow.getCell(i);
		         cell.setCellStyle(totalRowStyle);
		     }






		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  36 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,18 * 306); 
		        
		        sheet.setColumnWidth(21,18 * 306); 
		        sheet.setColumnWidth(22,18 * 306); 
		        sheet.setColumnWidth(23,18 * 306); 
		        sheet.setColumnWidth(24,18 * 306); 
		        sheet.setColumnWidth(25,18 * 306); 
		        sheet.setColumnWidth(26,18 * 306); 
		        sheet.setColumnWidth(27,18 * 306); 
		       
		        
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
}