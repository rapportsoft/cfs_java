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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.Branch;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfexbondcrgEditRepository;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.ImportReportsRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class ImportReportsService {

	
	
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
	
	
	public ResponseEntity<List<Object[]>> getDataOfImportContainerDetailsReport(
	        String companyId,
	        String branchId,
	        String username,
	        String type,
	        String companyname,
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

	    Calendar cal = Calendar.getInstance();

	    // Normalize startDate to the beginning of the day (00:00:00.000)
	    if (startDate != null) {
	        cal.setTime(startDate);
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        startDate = cal.getTime();
	    }

	    // Normalize endDate to the end of the day (23:59:59.999)
	    if (endDate != null) {
	        cal.setTime(endDate);
	        cal.set(Calendar.HOUR_OF_DAY, 23);
	        cal.set(Calendar.MINUTE, 59);
	        cal.set(Calendar.SECOND, 59);
	        cal.set(Calendar.MILLISECOND, 999);
	        endDate = cal.getTime();
	    }

	    List<Object[]> importDetails;

	    // Determine the repository method to call based on the selected report
	    switch (selectedReport) {
	        case "Import GateIn Container detailed Report":
	            importDetails = importReportsRepository.getDataForImportGateInContainerDetailedReport(
	                    companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;

	        case "Import GateOut Container detailed Report":
	            importDetails = importReportsRepository.importGateOutContainerDetailedReport(
	                    companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;

	        case "SealCutting Report":
	            importDetails = importReportsRepository.getDataForSealCuttingReport(
	                    companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;

	        case "Import Long Standing Report":
	            importDetails = importReportsRepository.findDataForLongStandingReport(companyId, branchId, endDate,igmNo,itemNo,cha,acc,sl);
	            break;
		        
	        // Add more cases for other reports as needed
	        case "Hold Release Report":
	            importDetails = importReportsRepository.findHoldDetails(
	                    companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;
	            
	        case "Import Manual GateIn Report":
	            importDetails = importReportsRepository.importManualGateInReportQuery(
	                    companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;
	            
	        case "WEIGHMENT REPORT":
	            importDetails = importReportsRepository.getWeighmentReport(
	                    companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;
	            
	        case "TRANSPORTER WISE TUES REPORT":
	            importDetails = importReportsRepository.getTransporterWiseTuesReport(
	                    companyId, branchId, startDate, endDate);
	            break;
	            
	        case "LCL Zero Payment":
	            importDetails = importReportsRepository.toGetLCLZeroPaymetReport(
	            		  companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;
	            
	        case "Loaded To Distuff Empty Inventory":
	            importDetails = importReportsRepository.getLoadedToDistuffEmptyContainerDetails(
	                    companyId, branchId, startDate, endDate,igmNo, itemNo, sl,  cha);
	            break;

	        case "LCL Cargo Balance Inventory Report":
	            importDetails = importReportsRepository.lclCargoBalanceInventory(companyId, branchId, endDate,igmNo,itemNo,sl,acc,cha);
	            break;
	            
	        case "Import FCL Destuff Balance Report":
	            importDetails = importReportsRepository.getDataForFCLDestuffBalanceReport(companyId, branchId, endDate,igmNo,itemNo,sl,acc,cha);
	            break;
	            
	        case "Import LCL Cargo Delivery Report":
	            importDetails = importReportsRepository.importLCLCargoDeliveryReport(companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;
	            
	        case "Import FCL Custom Tally Sheet Report":
	            importDetails = importReportsRepository.importFCLCustomTallySheetReport(
	                    companyId, branchId, startDate, endDate,igmNo, itemNo, sl, cha);
	            break;
	            
	        case "Import LCL Cargo Destuff Report":
	            importDetails = importReportsRepository.toGetImportLCLCargoDestuffReport(companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc);
	            break;
	            
	        case "Yard Balance Report Details":
	            importDetails = importReportsRepository.yardBalanceCOntainerReport(companyId, branchId, endDate);
	            break;
	            
	        case "Scan Container Report":
	            importDetails = importReportsRepository.scanContainerReport(companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc, cha);
	            break;
	            
	        case "Import BOE Wise Gate Out Report":
	            importDetails = importReportsRepository.findBOEWiseContainerOutDetails(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,cha);
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

	
	public byte[] createExcelReportOfImportGateInContainerDetailedReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getDataForImportGateInContainerDetailedReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import GateIn Container detailed Report ");

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
		        	    "Sr No", "Port", "Vessel Name", "Voy No", "Via/Vcn No", "Line Name", 
		        	    "Agent Name", "IGM No", 
		        	    "Item No", "Cont No", "Size", "Type", "ISO Code", "Cont Desc", "Weight", "Scan Location", 
		        	    "Genset", "Seal No", "Remarks", "Hold Remarks", "Cargo Type", "Commodity", "Importer Name", 
		        	    "V B Date", "Job Order No", "J O Date", "Gate In Date","Gate In time", "Truck No", "FCL/LCL", 
		        	    "Transporter Name", "Low Bed","Enblock", "BL No", "Damage Details", "JO Dwell Time (Dy:Hr:Min)", 
		        	    "Account Holder", "Nominated Customer", "IMO", "UN No", "Cargo Description", "Yard"
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
		        reportTitleCell.setCellValue("Import GateIn Container detailed Report" );

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
		        	 reportTitleCell1.setCellValue("Import GateIn Container detailed Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import GateIn Container detailed Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                        case "Port":
		                            cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                            break;

		                        case "Vessel Name":
		                            cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                            break;

		                        case "Voy No":
		                            cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                            break;

		                        case "Via/Vcn No":
		                            cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                            break;

		                        case "Line Name":
		                            cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                            break;

		                        case "Agent Name":
		                            cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                            break;

		                        case "IGM No":
		                            cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                            break;

		                        case "Item No":
		                            cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                            break;

		                        case "Cont No":
		                            cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                            break;

		                        case "Size":
		                            cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                            break;

		                        case "Type":
		                            cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                            break;

		                        case "ISO Code":
		                            cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                            break;

		                        case "Cont Desc":
		                            cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                            break;

		                        case "Weight":
		                            cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                            break;

		                        case "Scan Location":
		                            cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                            break;

		                        case "Genset":
		                            cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                            break;

		                        case "Seal No":
		                            cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                            break;

		                        case "Remarks":
		                            cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                            break;

		                        case "Hold Remarks":
		                            cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                            break;

		                        case "Cargo Type":
		                            cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                            break;

		                        case "Commodity":
		                            cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                            break;

		                        case "Importer Name":
		                            cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                            break;

		                        case "V B Date":
		                            if (resultData1[22] != null) {
		                                cell.setCellValue(resultData1[22].toString());
		                                cell.setCellStyle(dateCellStyle1);
		                            } else {
		                                cell.setBlank();
		                            }
		                            break;

		                        case "Job Order No":
		                            cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                            break;

		                        case "J O Date":
		                            if (resultData1[24] != null) {
		                                cell.setCellValue(resultData1[24].toString());
		                                cell.setCellStyle(dateCellStyle1);
		                            } else {
		                                cell.setBlank();
		                            }
		                            break;

		                        case "Gate In Date":
		                            if (resultData1[25] != null) {
		                                cell.setCellValue(resultData1[25].toString());
		                                cell.setCellStyle(dateCellStyle);
		                            } else {
		                                cell.setBlank();
		                                cell.setCellStyle(dateCellStyle);
		                            }
		                            break;

		                        case "Gate In Time":
		                            cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                            break;

		                        case "Truck No":
		                            cell.setCellValue(resultData1[27] != null ? resultData1[27].toString() : "");
		                            break;

		                        case "FCL/LCL":
		                            cell.setCellValue(resultData1[28] != null ? resultData1[28].toString() : "");
		                            break;

		                        case "Transporter Name":
		                            cell.setCellValue(resultData1[29] != null ? resultData1[29].toString() : "");
		                            break;

		                        case "Low Bed":
		                            cell.setCellValue(resultData1[30] != null ? resultData1[30].toString() : "");
		                            break;

		                        case "Enblock":
		                            cell.setCellValue(resultData1[31] != null ? resultData1[31].toString() : "");
		                            break;

		                        case "BL No":
		                            cell.setCellValue(resultData1[32] != null ? resultData1[32].toString() : "");
		                            break;

		                        case "Damage Details":
		                            cell.setCellValue(resultData1[33] != null ? resultData1[33].toString() : "");
		                            break;

		                        case "JO Dwell Time (Dy:Hr:Min)":
		                            cell.setCellValue(resultData1[34] != null ? resultData1[34].toString() : "");
		                            break;

		                        case "Account Holder":
		                            cell.setCellValue(resultData1[35] != null ? resultData1[35].toString() : "");
		                            break;

		                        case "Nominated Customer":
		                            cell.setCellValue(resultData1[36] != null ? resultData1[36].toString() : "");
		                            break;

		                        case "IMO":
		                            cell.setCellValue(resultData1[37] != null ? resultData1[37].toString() : "");
		                            break;

		                        case "UN No":
		                            cell.setCellValue(resultData1[38] != null ? resultData1[38].toString() : "");
		                            break;

		                        case "Cargo Description":
		                            cell.setCellValue(resultData1[39] != null ? resultData1[39].toString() : "");
		                            break;

		                        case "Yard":
		                            cell.setCellValue(resultData1[40] != null ? resultData1[40].toString() : "");
		                            break;

		                        default:
		                            cell.setCellValue(""); // Handle undefined columns
		                            break;
		                    }

		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
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
		        sheet.setColumnWidth(28, 18 * 306);
		        sheet.setColumnWidth(29, 18 * 306); 
		        sheet.setColumnWidth(30, 27 * 306); 
		        
		        sheet.setColumnWidth(31, 18 * 306); 
		        sheet.setColumnWidth(32, 18 * 306); 
		        sheet.setColumnWidth(33, 18 * 306); 
		        sheet.setColumnWidth(34, 27 * 306); 
//		        sheet.setColumnWidth(35, 27 * 306); 
		        sheet.setColumnWidth(36, 27 * 306); 
		        sheet.setColumnWidth(37, 27 * 306); 
		        sheet.setColumnWidth(38, 18 * 306); 
		        sheet.setColumnWidth(39, 18 * 306); 
		        sheet.setColumnWidth(40, 27 * 306); 
		        sheet.setColumnWidth(41, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportGateOutContainerDetailedReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.importGateOutContainerDetailedReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import GateOut Container detailed Report");

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
		        	    "Sr No", "Cont No", "Size", "Type", "ISO Code", "Cargo Type", "Scan Type", 
		        	    "FCL/LCL", "Movement Type", "IGM No", "Item No", "Gross Wt", "Gate In Date", 
		        	    "Gate Out Date", "S.Line", "CHA", "Importer", 
		        	    "VCN No.", "Voy No", "Vessel Name", "Port", "BOE No.", "BOE Date", "Truck No.", 
		        	    "Remarks", "Dwell Time in Teus", "BL No.", "Surveyor", "Out Dwell Time (Dy:Hr:Min)", 
		        	    "Cargo Description", "Nominated Customer", "Yard"
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
		        reportTitleCell.setCellValue("Import GateOut Container detailed Report" );

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
		        	 reportTitleCell1.setCellValue("Import GateOut Container detailed Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import GateOut Container detailed Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "Cont No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "ISO Code":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Cargo Type":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Scan Type":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "FCL/LCL":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Movement Type":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "IGM No":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Gross Wt":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Gate In Date":
		                    if (resultData1[11] != null) {
		                        cell.setCellValue(resultData1[11].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Gate Out Date":
		                    if (resultData1[12] != null) {
		                        cell.setCellValue(resultData1[12].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "S.Line":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Importer":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "VCN No.":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Voy No":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Port":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                case "BOE No.":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "BOE Date":
		                    if (resultData1[21] != null) {
		                        cell.setCellValue(resultData1[21].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Truck No.":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "Remarks":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                case "Dwell Time in Teus":
		                    cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                    break;

		                case "BL No.":
		                    cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                    break;

		                case "Surveyor":
		                    cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                    break;

		                case "Out Dwell Time (Dy:Hr:Min)":
		                    cell.setCellValue(resultData1[27] != null ? resultData1[27].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[28] != null ? resultData1[28].toString() : "");
		                    break;

		                case "Nominated Customer":
		                    cell.setCellValue(resultData1[29] != null ? resultData1[29].toString() : "");
		                    break;

		                case "Yard":
		                    cell.setCellValue(resultData1[30] != null ? resultData1[30].toString() : "");
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
//		        // Create a row for spacing before totals
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
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 45 * 306); 
		        sheet.setColumnWidth(15, 45 * 306); 
		        sheet.setColumnWidth(16, 45 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        sheet.setColumnWidth(20,14 * 306); 
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		        sheet.setColumnWidth(28, 27 * 306);
		        sheet.setColumnWidth(29, 45 * 306); 
		        sheet.setColumnWidth(30, 27 * 306); 
		        
		        sheet.setColumnWidth(31, 18 * 306); 
		        sheet.setColumnWidth(32, 18 * 306); 
		        sheet.setColumnWidth(33, 18 * 306); 
		        sheet.setColumnWidth(34, 18 * 306); 
//		        sheet.setColumnWidth(35, 27 * 306); 
		        sheet.setColumnWidth(36, 18 * 306); 
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfHoldReleasedReportDetailedReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.findHoldDetails(companyId, branchId, startDate, endDate,igmNo,itemNo,sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Hold Release Report");

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
		        	    "Sr No", "IGM No", "Item No", "Cont No", "Cont Size", "Cont Type", "BOE no", 
		        	    "BOE Date", "Gate In Date", "Hold Remarks", "Importer", "Agent", "FCL/LCL", 
		        	    "Hold BY", "Hold Type", "Hold Date", "Relese Date", "Relese Remark", 
		        	    "Cargo Description", "Gate Out","Destuff Date"
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
		        reportTitleCell.setCellValue("Hold Release Report" );

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
		        	 reportTitleCell1.setCellValue("Hold Release Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Hold Release Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "IGM No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "BOE no":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "BOE Date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Gate In Date":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Hold Remarks":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Importer":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Agent":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "FCL/LCL":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Hold BY":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Hold Type":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Hold Date":
		                    if (resultData1[14] != null) {
		                        cell.setCellValue(resultData1[14].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Relese Date":
		                    if (resultData1[15] != null) {
		                        cell.setCellValue(resultData1[15].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Relese Remark":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Gate Out":
		                    if (resultData1[18] != null) {
		                        cell.setCellValue(resultData1[18].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Destuff Date":
		                    if (resultData1[19] != null) {
		                        cell.setCellValue(resultData1[19].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
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
		        empty.createCell(0).setCellValue("Summery"); // You can set a value or keep it blank
		        
		        		
		        		
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
		            String agent = i[10] != null ? i[10].toString() : "Unknown"; // Shipping Agent at index 10
		            String containerSize = i[3] != null ? i[3].toString() : ""; // Container Size at index 3

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

		        // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Summary" label to the first cell and apply the style
//		        Cell totalLabelCell = totalRow.createCell(0);
//		        totalLabelCell.setCellValue("Summary");
//		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		        // Create total row cells
//		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
//		            Cell totalCell = totalRow.createCell(i);
//		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
//		            switch (columnsHeaderSummery[i]) {
//		                case "Sr No":
//		                    totalCell.setCellValue(""); // Empty for Sr No in total row
//		                    break;
//		                case "Shipping Agent":
//		                    totalCell.setCellValue(shippingAgent); // Empty for Shipping Agent in total row
//		                    totalCell.setCellStyle(centerStyle); // Apply centered style
//		                    break;
//		                case "Size 20":
//		                    totalCell.setCellValue(totalSize20);
//		                    break;
//		                case "Size 22":
//		                    totalCell.setCellValue(totalSize22);
//		                    break;
//		                case "Size 40":
//		                    totalCell.setCellValue(totalSize40);
//		                    break;
//		                case "Size 45":
//		                    totalCell.setCellValue(totalSize45);
//		                    break;
//		                case "Total":
//		                    totalCell.setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45);
//		                    break;
//		                case "TUE'S":
//		                    totalCell.setCellValue(""); // TUE'S (empty or can be filled if necessary)
//		                    break;
//		                default:
//		                    totalCell.setCellValue(""); // Default blank
//		                    break;
//		            }
//		        }



		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 14 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 27 * 306); 
		        sheet.setColumnWidth(18, 45 * 306); 
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
	
	
	
	
	
	
	public byte[] createExcelReportOfImportBOEWiseGateOutReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.findBOEWiseContainerOutDetails(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import BOE Wise Gate Out Report");

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
		        	    "Sr. No.", 
		        	    "boe_no", 
		        	    "boe_date", 
		        	    "importer_name", 
		        	    "cha", 
		        	    "gateoutdate"
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
		        reportTitleCell.setCellValue("Import BOE Wise Gate Out Report" );

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
		        	 reportTitleCell1.setCellValue("Import BOE Wise Gate Out Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import BOE Wise Gate Out Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr. No.":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "boe_no":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "boe_date":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "importer_name":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "cha":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "gateoutdate":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
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
		        
		        sheet.setColumnWidth(4, 45 * 306); 
		        sheet.setColumnWidth(5, 45 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 36 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	public byte[] createExcelReportOfImportFGPReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getDataForImportGateInContainerDetailedReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import FGP Report");

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
		        	    "Sr No", "IGM No", "Item No", "Cont No", "Cont Size", "Cont Type", "BOE no", 
		        	    "BOE Date", "Gate In Date", "Hold Remarks", "Importer", "Agent", "FCL/LCL", 
		        	    "Hold BY", "Hold Type", "Hold Date", "Relese Date", "Relese Remark", 
		        	    "Cargo Description", "Gate Out/Destuff Date"
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
		        reportTitleCell.setCellValue("Import FGP Report" );

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
		        	 reportTitleCell1.setCellValue("Import FGP Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import FGP Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "IGM No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Cont Type":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "BOE no":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "BOE Date":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Gate In Date":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(resultData1[8].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Hold Remarks":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Importer":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Agent":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "FCL/LCL":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Hold BY":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Hold Type":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Hold Date":
		                    if (resultData1[15] != null) {
		                        cell.setCellValue(resultData1[15].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Relese Date":
		                    if (resultData1[16] != null) {
		                        cell.setCellValue(resultData1[16].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Relese Remark":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Gate Out/Destuff Date":
		                    if (resultData1[19] != null) {
		                        cell.setCellValue(resultData1[19].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
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
		        sheet.setColumnWidth(28, 18 * 306);
		        sheet.setColumnWidth(29, 18 * 306); 
		        sheet.setColumnWidth(30, 27 * 306); 
		        
		        sheet.setColumnWidth(31, 18 * 306); 
		        sheet.setColumnWidth(32, 18 * 306); 
		        sheet.setColumnWidth(33, 18 * 306); 
		        sheet.setColumnWidth(34, 27 * 306); 
//		        sheet.setColumnWidth(35, 27 * 306); 
		        sheet.setColumnWidth(36, 27 * 306); 
		        sheet.setColumnWidth(37, 27 * 306); 
		        sheet.setColumnWidth(38, 18 * 306); 
		        sheet.setColumnWidth(39, 18 * 306); 
		        sheet.setColumnWidth(40, 27 * 306); 
		        sheet.setColumnWidth(41, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportLongStandingReport(String companyId,
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
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.findDataForLongStandingReport(companyId, branchId, endDate,igmNo,itemNo,cha,acc,sl);
		        
		        Sheet sheet = workbook.createSheet("Import Long Standing Report");

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
		        	    "Sr No.", "Container No", "Size", "Description Of Goods", "Weight(Kgs)", 
		        	    "In Date", "IGM No", "Item No", "Line", 
		        	    "Consignee's Name", "Days","CHA",  "Agent Code","Current Stage"
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
		        reportTitleCell.setCellValue("Import Long Standing Report" );

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
		        	 reportTitleCell1.setCellValue("Import Long Standing Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import Long Standing Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr No.":
		                    cell.setCellValue(serialNo++); // Incrementing serial number
		                    break;

		                case "Container No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Description Of Goods":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Weight(Kgs)":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(Double.parseDouble(resultData1[3].toString()));
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "In Date":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
//		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "IGM No":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		         

		                case "Line":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Consignee's Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Days":
		                    if (resultData1[9] != null) {
		                        cell.setCellValue(Integer.parseInt(resultData1[9].toString()));
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;
		                    
		                case "CHA":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Agent Code":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                case "Current Stage":
//		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		       
		                default:
		                    cell.setCellValue(""); // Handle undefined or extra columns
		                    break;
		            }

		            }
		        }
		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 36 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 9 * 306); 
		        
		        sheet.setColumnWidth(8, 36 * 306); 
		        sheet.setColumnWidth(9, 36 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportLclCargoDeliveryReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.importLCLCargoDeliveryReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import LCL Cargo Delivery Report");

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
		        	    "Sr. No.", "Cont No", "Cont Size", "Type", "IGM No", "Item No", 
		        	    "Igm Date", "Gate In Date", "Delivery Date", "Vessel Name", 
		        	    "VOY No", "BL No", "Boe no", "Boe date", "Destuff Date", 
		        	    "Package as per IGM", "Package Destuffed", "Package Type", 
		        	    "Movement Type", "Importer Name", "Agent Name", "Consoler", 
		        	    "Location", "Area", "Cargo Description", "Remarks", 
		        	    "Assess Value", "Duty Value", "Gate Pass Id", "CHA"
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
		        reportTitleCell.setCellValue("Import LCL Cargo Delivery Report" );

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
		        	 reportTitleCell1.setCellValue("Import LCL Cargo Delivery Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import LCL Cargo Delivery Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr. No.":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;

		                case "Cont No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Cont Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "IGM No":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Igm Date":
		                    if (resultData1[5] != null) {
		                        cell.setCellValue(resultData1[5].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting even if blank
		                    }
		                    break;

		                case "Gate In Date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting even if blank
		                    }
		                    break;

		                case "Delivery Date":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting even if blank
		                    }
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "VOY No":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "BL No":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Boe no":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Boe date":
		                    if (resultData1[12] != null) {
		                        cell.setCellValue(resultData1[12].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting even if blank
		                    }
		                    break;

		                case "Destuff Date":
		                    if (resultData1[13] != null) {
		                        cell.setCellValue(resultData1[13].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting even if blank
		                    }
		                    break;

		                case "Package as per IGM":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Package Destuffed":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Package Type":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Movement Type":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Importer Name":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Agent Name":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                case "Consoler":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "Location":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "Area":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                case "Remarks":
		                    cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                    break;

		                case "Assess Value":
		                    if (resultData1[25] != null) {
		                        cell.setCellValue(Double.parseDouble(resultData1[25].toString()));
		                        cell.setCellStyle(numberCellStyle);
		                    } else {
		                        cell.setCellValue("");
		                        cell.setCellStyle(numberCellStyle);
		                    }
		                    break;

		                case "Duty Value":
		                    if (resultData1[26] != null) {
		                        cell.setCellValue(Double.parseDouble(resultData1[26].toString()));
		                        cell.setCellStyle(numberCellStyle);
		                    } else {
		                        cell.setCellValue("");
		                        cell.setCellStyle(numberCellStyle);
		                    }
		                    break;

		                case "Gate Pass Id":
		                    cell.setCellValue(resultData1[27] != null ? resultData1[27].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[28] != null ? resultData1[28].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       
		        // Create a font for bold text
		       

//		        // Create a row for spacing before totals
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
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 27 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportFclDestuffBalanceReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getDataForFCLDestuffBalanceReport(companyId, branchId, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfLclCargoBalanceInventoryReport(String companyId,
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
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.lclCargoBalanceInventory(companyId, branchId, endDate,igmNo,itemNo,sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("LCL Cargo Balance Inventory Report");

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
		        	    "Sr_No", "Cont No", "Cont Size","Cont Type", "IGM", "Sub/Item", "IGM Date", "BL No", 
		        	    "BL Date", "Voy No", "Gate In Date", "Destuff Date", "Declared Pkg", 
		        	    "Destuffed Pkg", "Importer Name", "No of Days", "Ware House Location", 
		        	    "Area", "Movement Type", "Consoler Name", "Agent Name", "Remarks", 
		        	    "Location", "Hold Remarks", "Cargo Type", "Assessable Value", "Duty Value", 
		        	    "IMO", "UN No"
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
		        reportTitleCell.setCellValue("LCL Cargo Balance Inventory Report" );

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
		        	 reportTitleCell1.setCellValue("LCL Cargo Balance Inventory Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("LCL Cargo Balance Inventory Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr_No":
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


		                    case "IGM": // Index 3
		                        cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                        break;

		                    case "Sub/Item": // Index 4
		                        cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                        break;

		                    case "IGM Date": // Index 5
		                        if (resultData1[5] != null) {
		                            cell.setCellValue(resultData1[5].toString());
		                            cell.setCellStyle(dateCellStyle); // Apply date formatting
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "BL No": // Index 6
		                        cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                        break;

		                    case "BL Date": // Index 7
		                        if (resultData1[7] != null) {
		                            cell.setCellValue(resultData1[7].toString());
		                            cell.setCellStyle(dateCellStyle); // Apply date formatting
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Voy No": // Index 8
		                        cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                        break;

		                    case "Gate In Date": // Index 9
		                        if (resultData1[9] != null) {
		                            cell.setCellValue(resultData1[9].toString());
		                            cell.setCellStyle(dateCellStyle); // Apply date formatting
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Destuff Date": // Index 10
		                        if (resultData1[10] != null) {
		                            cell.setCellValue(resultData1[10].toString());
		                            cell.setCellStyle(dateCellStyle); // Apply date formatting
		                        } else {
		                            cell.setBlank();
		                            cell.setCellStyle(dateCellStyle);
		                        }
		                        break;

		                    case "Declared Pkg": // Index 11
		                        cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                        break;

		                    case "Destuffed Pkg": // Index 12
		                        cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                        break;

		                    case "Importer Name": // Index 13
		                        cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                        break;

		                    case "No of Days": // Index 14
		                        if (resultData1[14] != null) {
		                            cell.setCellValue(Integer.parseInt(resultData1[14].toString()));
		                        } else {
		                            cell.setCellValue("");
		                        }
		                        break;

		                    case "Ware House Location": // Index 15
		                        cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                        break;

		                    case "Area": // Index 16
		                        cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                        break;

		                    case "Movement Type": // Index 17
		                        cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                        break;

		                    case "Consoler Name": // Index 18
		                        cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                        break;

		                    case "Agent Name": // Index 19
		                        cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                        break;

		                    case "Remarks": // Index 20
		                        cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                        break;

		                    case "Location": // Index 21
		                        cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                        break;

		                    case "Hold Remarks": // Index 22
		                        cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                        break;

		                    case "Cargo Type": // Index 23
		                        cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                        break;

		                    case "Assessable Value": // Index 24
		                        if (resultData1[24] != null) {
		                            try {
		                                cell.setCellValue(Double.parseDouble(resultData1[24].toString()));
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(""); // Handle non-numeric values gracefully
		                            }
		                        } else {
		                            cell.setCellValue("");
		                        }
		                        break;

		                    case "Duty Value": // Index 25
		                        if (resultData1[25] != null) {
		                            try {
		                                cell.setCellValue(Double.parseDouble(resultData1[25].toString()));
		                            } catch (NumberFormatException e) {
		                                cell.setCellValue(""); // Handle non-numeric values gracefully
		                            }
		                        } else {
		                            cell.setCellValue("");
		                        }
		                        break;

		                    case "IMO": // Index 26
		                        cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                        break;

		                    case "UN No": // Index 27
		                        cell.setCellValue(resultData1[27] != null ? resultData1[27].toString() : "");
		                        break;

		                    default:
		                        cell.setCellValue(""); // Handle undefined columns
		                        break;
		                }



		            }
		        }
		       

//		        // Create a row for spacing before totals
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

		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 9 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 27 * 306); 
		        
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        
		        sheet.setColumnWidth(13,  9 * 306); 
		        sheet.setColumnWidth(14, 45 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 18 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 18 * 306); 
		        sheet.setColumnWidth(20,36 * 306); 
		        sheet.setColumnWidth(21, 14 * 306);
		        sheet.setColumnWidth(22, 14 * 306); 
		        sheet.setColumnWidth(23, 18 * 306); 
		        sheet.setColumnWidth(24, 18 * 306); 
		        sheet.setColumnWidth(25, 18 * 306);
		        sheet.setColumnWidth(26, 18 * 306);
		        sheet.setColumnWidth(27, 18 * 306); 
		        sheet.setColumnWidth(28, 18 * 306); 
	
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportDestuffEquipmentReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getDataForImportGateInContainerDetailedReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import Destuff Equipment Report");

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
		        	    "SR NO", "CONT NO", "CONT SIZE", "WORK ORDER DATE", "COMMODITY", "AGENT NAME", 
		        	    "10 TON FORK LIFT", "CHOKING - WITHOUT PLY - 20 FT CONTAINER", 
		        	    "CHOKING - WITHOUT PLY - 40 FT CONTAINER", "CHOKING - WITHOUT PLY - 45 FT CONTAINER", 
		        	    "CHOKING - WITH PLY - 4MM PLY", "CHOKING - WITH PLY - 6MM PLY", 
		        	    "CHOKING - WITH PLY - 8MM PLY", "AUCTION DUES IN CASE OF NORMAL DELIVERY", 
		        	    "AUCTION NOTICE CHARGES", "Additional Container ODC Hdlg Charges", 
		        	    "CRANE+FK10+FK3+LABOUR", "CRANE30+LABOUR+FK3", "CRANE15+FK3+FK5", 
		        	    "CRANE15+LABOUR", "CRANE15MT+FORKLIFT10MT", "CRANE25+LABOUR", 
		        	    "CRANE 25 MT + FK10+ FK3", "CRANE20+LABOUR", "CRANE30+LABOUR", "CRANE", 
		        	    "Crane Charges", "CRANE25MT+ LAB + FK3", "CRANE25MT+ LAB + FK10", 
		        	    "DIRECT LDD CONTAINER", "R/stacker DRF-01", "R/stacker DRF-02", 
		        	    "R/stacker DRF-05", "R/stacker DRF-06", "FORKLIFT + CRANE15", 
		        	    "FORKLIFT 10MT + LAB", "FORKLIFT5MT+FORKLIFT10MTFK10MT", 
		        	    "FORK LIFT 10 TON", "FORK LIFT 12 TON 4064", "FK3+FK5+LABOUR", 
		        	    "FK3-FK10-LABOUR", "FORKLIFT 3 + FORKLIFT 10 MT", "LABOUR+FORK LIFT 3 TON", 
		        	    "FORKLIFT 5 MT - CRANE 15 MT", "FORKLIFT3MT+CRANE15+LABOUR", 
		        	    "FORKLIFT3MT+FORKLIFT10MT", "FORKLIFT3MT+PVT LABOUR", "FORKLIFT 3 MT", 
		        	    "FORKLIFT 5 MT", "FORKLIFT 10MT", "HYDRA", 
		        	    "LASHING - IRON ROPE - 20 FT CONTAINER", "LASHING - IRON ROPE - 40 FT CONTAINER", 
		        	    "LASHING - IRON ROPE - 45 FT CONTAINER", "LABOUR + FORKLIFT 10 MT", 
		        	    "LABOUR + FORKLIFT 3 MT", "LABOUR + FORKLIFT 5 MT", "LABOUR + CRANE", 
		        	    "LABOUR", "REEFER MONITORING CHARGES", "PALLETIZATION", 
		        	    "REEFER PLUGGING CHARGES", "PPM+FK10", "PPM+FK3", "PPP+LAB", "PPM", 
		        	    "PRIVATE LABOUR", "R/stacker GJ-14-D-8311", "R/stacker GJ-14-D-8313", 
		        	    "LASHING - COIR ROPE - 20 FT CONTAINER", "LASHING - COIR ROPE - 40 FT CONTAINER", 
		        	    "REWORKING-DESTUFFING AND STUFFING", "REWORKING-DESTUFFING OR STUFFING", 
		        	    "LASHING - COIR ROPE - 45 FT CONTAINER", "W-PALLETIZATION", "WEIGHTMENT"
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
		        reportTitleCell.setCellValue("Import Destuff Equipment Report" );

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
		        	 reportTitleCell1.setCellValue("Import Destuff Equipment Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import Destuff Equipment Report From : " + formattedStartDate + " to " + formattedEndDate);
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

//		                switch (columnsHeader[i]) {
//		                case "Sr_No":
//		                    cell.setCellValue(serialNo++); // Increment serial number
//		                    break;
//
//		                case "Cont No":
//		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
//		                    break;
//
//		                case "Cont Size":
//		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
//		                    break;
//
//		                case "IGM":
//		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
//		                    break;
//
//		                case "Sub/Item":
//		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
//		                    break;
//
//		                case "IGM Date":
//		                    if (resultData1[5] != null) {
//		                        cell.setCellValue(resultData1[5].toString());
//		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
//		                    } else {
//		                        cell.setBlank();
//		                        cell.setCellStyle(dateCellStyle);
//		                    }
//		                    break;
//
//		                case "BL No":
//		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
//		                    break;
//
//		                case "BL Date":
//		                    if (resultData1[7] != null) {
//		                        cell.setCellValue(resultData1[7].toString());
//		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
//		                    } else {
//		                        cell.setBlank();
//		                        cell.setCellStyle(dateCellStyle);
//		                    }
//		                    break;
//
//		                case "Voy No":
//		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
//		                    break;
//
//		                case "Gate In Date":
//		                case "Destuff Date":
//		                    if (resultData1[9] != null) {
//		                        cell.setCellValue(resultData1[9].toString());
//		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
//		                    } else {
//		                        cell.setBlank();
//		                        cell.setCellStyle(dateCellStyle);
//		                    }
//		                    break;
//
//		                case "Declared Pkg":
//		                case "Destuffed Pkg":
//		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
//		                    break;
//
//		                case "Importer Name":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
//		                    break;
//
//		                case "No of Days":
//		                    if (resultData1[12] != null) {
//		                        cell.setCellValue(Integer.parseInt(resultData1[12].toString()));
//		                    } else {
//		                        cell.setCellValue("");
//		                    }
//		                    break;
//
//		                case "Ware House Location":
//		                case "Area":
//		                case "Location":
//		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
//		                    break;
//
//		                case "Movement Type":
//		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
//		                    break;
//
//		                case "Consoler Name":
//		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
//		                    break;
//
//		                case "Agent Name":
//		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
//		                    break;
//
//		                case "Remarks":
//		                case "Hold Remarks":
//		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
//		                    break;
//
//		                case "Cargo Type":
//		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
//		                    break;
//
//		                case "Assessable Value":
//		                case "Duty Value":
//		                    if (resultData1[19] != null) {
//		                        cell.setCellValue(Double.parseDouble(resultData1[19].toString()));
//		                    } else {
//		                        cell.setCellValue("");
//		                    }
//		                    break;
//
//		                case "IMO":
//		                case "UN No":
//		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
//		                    break;
//
//		                default:
//		                    cell.setCellValue(""); // Handle undefined columns
//		                    break;
//		            }




		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
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
		        sheet.setColumnWidth(28, 18 * 306);
		        sheet.setColumnWidth(29, 18 * 306); 
		        sheet.setColumnWidth(30, 27 * 306); 
		        
		        sheet.setColumnWidth(31, 18 * 306); 
		        sheet.setColumnWidth(32, 18 * 306); 
		        sheet.setColumnWidth(33, 18 * 306); 
		        sheet.setColumnWidth(34, 27 * 306); 
//		        sheet.setColumnWidth(35, 27 * 306); 
		        sheet.setColumnWidth(36, 27 * 306); 
		        sheet.setColumnWidth(37, 27 * 306); 
		        sheet.setColumnWidth(38, 18 * 306); 
		        sheet.setColumnWidth(39, 18 * 306); 
		        sheet.setColumnWidth(40, 27 * 306); 
		        sheet.setColumnWidth(41, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportFclCustomTallySheetReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.importFCLCustomTallySheetReport(companyId, branchId, startDate, endDate,igmNo,itemNo,sl,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import FCL Custom Tally Sheet Report");

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
		        		 "Sr No", "Container No.", "Size", "Type", "IGM No.", "Item", 
		        		    "boe no", "boe date", "CHA", "Importer", "Declared Pkgs.", 
		        		    "Examed No. of Packets", "Percent", "Item Description", 
		        		    "Invoice No.", "Invoice Date", "Custom Exam WO No.", 
		        		    "WO Date", "Scrap Lab", "Equipment", "Remark"
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
		        reportTitleCell.setCellValue("Import FCL Custom Tally Sheet Report" );

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
		        	 reportTitleCell1.setCellValue("Import FCL Custom Tally Sheet Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import FCL Custom Tally Sheet Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "Container No.":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "IGM No.":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Item":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "boe no":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "boe date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Importer":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Declared Pkgs.":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Examed No. of Packets":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Percent":
		                    if (resultData1[11] != null) {
		                    	   cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    } else {
		                        cell.setCellValue("");
		                    }
		                    break;

		                case "Item Description":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Invoice No.":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Invoice Date":
		                    if (resultData1[14] != null) {
		                        cell.setCellValue(resultData1[14].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Custom Exam WO No.":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "WO Date":
		                    if (resultData1[16] != null) {
		                        cell.setCellValue(resultData1[16].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Scrap Lab":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Equipment":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Remark":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }
		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
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
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 36 * 306); 
		        sheet.setColumnWidth(9, 36 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  36 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 

		        
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        sheet.setColumnWidth(20,14 * 306); 
		        sheet.setColumnWidth(21, 36 * 306);
		        sheet.setColumnWidth(22, 45 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportManualGateInReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.importManualGateInReportQuery(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import Manual GateIn Report");

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
		        	    "Sr No", "CONT NO", "CONT SIZE", "CONTAINER TYPE", "ISO CODE", "GATE PASS NO",
		        	    "GATE IN DATE", "SYSTEM GATE IN DATE", "AGENT SEAL NO", "CUSTOM SEAL NO", "TRUCK NO",
		        	    "PORT EIR NO", "VESSEL NAME", "LINE", "AGENT", "PORT", "SCAN LOCATION", "SCAN STATUS"
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
		        reportTitleCell.setCellValue("Import Manual GateIn Report" );

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
		        	 reportTitleCell1.setCellValue("Import Manual GateIn Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import Manual GateIn Report From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "CONT NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "CONT SIZE":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "CONTAINER TYPE":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "ISO CODE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "GATE PASS NO":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "GATE IN DATE":
		                    if (resultData1[5] != null) {
		                        cell.setCellValue(resultData1[5].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "SYSTEM GATE IN DATE":
		                    if (resultData1[5] != null) {
		                        cell.setCellValue(resultData1[5].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "AGENT SEAL NO":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "CUSTOM SEAL NO":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "TRUCK NO":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "PORT EIR NO":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "VESSEL NAME":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "LINE":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "AGENT":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "PORT":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "SCAN LOCATION":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "SCAN STATUS":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 9 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  45 * 306); 
		        sheet.setColumnWidth(14, 36 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfLoadedToDistuffEmptyInventoryReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getLoadedToDistuffEmptyContainerDetails(companyId, branchId, startDate, endDate,igmNo, itemNo, sl,  cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Loaded To Distuff Empty Inventory");

//		        CellStyle dateCellStyle = workbook.createCellStyle();
//		        CreationHelper createHelper = workbook.getCreationHelper();
//		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
//		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
//		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
//		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
//		        dateCellStyle.setBorderTop(BorderStyle.THIN);
//		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
//		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
//		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
//		        dateCellStyle.setBorderRight(BorderStyle.THIN);
//		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
		        
		        
		        CellStyle dateCellStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();

		        // Set the date format style
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
		        	    "Sr No", "Container No", "Size", "Type", "IsoCode", "Cha", 
		        	    "Agent Name", "Gate Pass Date", "Days"
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
		        reportTitleCell.setCellValue("Loaded To Distuff Empty Inventory" );

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
		        	 reportTitleCell1.setCellValue("Loaded To Distuff Empty Inventory As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Loaded To Distuff Empty Inventory From : " + formattedStartDate + " to " + formattedEndDate);
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
		                    // Increment serial number for each row
		                    cell.setCellValue(serialNo++);
		                    break;

		                case "Container No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : ""); // resultData1[0]
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : ""); // resultData1[1]
		                    break;

		                case "Type":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : ""); // resultData1[2]
		                    break;

		                case "IsoCode":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : ""); // resultData1[3]
		                    break;

		                case "Cha":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : ""); // resultData1[4]
		                    break;

		                case "Agent Name":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : ""); // resultData1[5]
		                    break;

		                case "Gate Pass Date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString()); // resultData1[6]
//		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
//		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;
		                    
//		                case "Gate Pass Date":
//		                    if (resultData1[6] != null) {
//		                        String dateString = resultData1[6].toString();
//		                        try {
//		                            // Parse the date string into a Date object using SimpleDateFormat or DateTimeFormatter
//		                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // For ISO 8601 format
//		                            Date parsedDate = dateFormat.parse(dateString);
//		                            cell.setCellValue(parsedDate);  // Set the date value in the cell
//		                        } catch (Exception e) {
//		                            // Handle parsing errors (invalid date format)
//		                            cell.setCellValue(""); // or set a default value or error message
//		                        }
//		                        cell.setCellStyle(dateCellStyle);  // Apply the date style
//		                    } else {
//		                        cell.setBlank();  // Handle null value
//		                        cell.setCellStyle(dateCellStyle);
//		                    }
//		                    break;

		                case "Days":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : ""); // resultData1[7]
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		       
		        // Create a font for bold text
		       

//		        // Create a row for spacing before totals
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
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306); 
		        
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(8, 18 * 306); 

		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfSealCuttingReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getDataForSealCuttingReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("SealCutting Report");

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
		        	    "SR No", "IGM_NO", "ITEM_NO", "BL_NO", "BL_DATE", "CONTAINER_NO", 
		        	    "CONTAINER_SIZE", "CONTAINER_TYPE", "IN DATE", "CHA", "BOE_NO", 
		        	    "BOE_DATE", "SEALCUTTING_DATE"
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
		        reportTitleCell.setCellValue("SealCutting Report" );

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
		        	 reportTitleCell1.setCellValue("SealCutting Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("SealCutting Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR No":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "IGM_NO":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "ITEM_NO":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "BL_NO":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "BL_DATE":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "CONTAINER_NO":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "CONTAINER_SIZE":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "CONTAINER_TYPE":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "IN DATE":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "BOE_NO":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "BOE_DATE":
		                    if (resultData1[10] != null) {
		                        cell.setCellValue(resultData1[10].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "SEALCUTTING_DATE":
		                    if (resultData1[11] != null) {
		                        cell.setCellValue(resultData1[11].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }


		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
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
		        sheet.setColumnWidth(9, 36 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		       
		        
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.scanContainerReport(companyId, branchId,startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        List<Object[]> importDetails1 = importReportsRepository.scanContainerReport1(companyId, branchId, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
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

		                 case "BL_NO":
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfWeighmentReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getWeighmentReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("WEIGHMENT REPORT");

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
		        	    "Sr No", "FCL_LCL_mode", "Container No", "Container Size", "Gate In Date", 
		        	    "Gate Out Date", "IGM Date", "IGM No", "Item No", "IGM WT", "VGM WT", 
		        	    "DIFF", "Importer", "Cargo Description"
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
		        reportTitleCell.setCellValue("WEIGHMENT REPORT" );

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
		        	 reportTitleCell1.setCellValue("WEIGHMENT REPORT As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("WEIGHMENT REPORT From : " + formattedStartDate + " to " + formattedEndDate);
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

		                case "FCL_LCL_mode":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Container No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Container Size":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "Gate In Date":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "Gate Out Date":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "IGM Date":
		                    if (resultData1[5] != null) {
		                        cell.setCellValue(resultData1[5].toString());
		                        cell.setCellStyle(dateCellStyle); // Apply date formatting
		                    } else {
		                        cell.setBlank();
		                        cell.setCellStyle(dateCellStyle);
		                    }
		                    break;

		                case "IGM No":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "IGM WT":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "VGM WT":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "DIFF":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Importer":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.getTransporterWiseTuesReport(companyId, branchId, startDate, endDate);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("TRANSPORTER WISE TUES REPORT");

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
		        reportTitleCell.setCellValue("TRANSPORTER WISE TUES REPORT" );

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
		        	 reportTitleCell1.setCellValue("TRANSPORTER WISE TUES REPORT As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("TRANSPORTER WISE TUES REPORT From : " + formattedStartDate + " to " + formattedEndDate);
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
		                    break;

		                case "SIZE_40":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "TEUS":
		                    cell.setCellValue(value1 + (value2 * 2));
		                    break;

		                
		                default:
		                    cell.setCellValue(""); // Handle undefined columns
		                    break;
		            }

		            }
		        }
		       
		        // Create a font for bold text
		       

		        // Create a row for spacing before totals
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow = sheet.createRow(rowNum++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell = totalRow.createCell(0);
		        totalLabelCell.setCellValue("Total");
		        totalLabelCell.setCellStyle(totalRowStyle); // Apply the background color and bold style

		        CellStyle centerStyle = workbook.createCellStyle();
		        centerStyle.cloneStyleFrom(totalRowStyle); // Use totalRowStyle as base (light green background)
		        centerStyle.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader.length; i++) {
		            Cell totalCell = totalRow.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle); // Set the total row style
		            
		            switch (columnsHeader[i]) {
		            
		            case "Section 60(1)":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Duty Value":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Bal Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Bal Wt":
		                    totalCell.setCellValue(totalBalWeight.doubleValue());
		                    break;
		                case "Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area Balance Area":
		                    totalCell.setCellValue(totalAreaBalance.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.toGetLCLZeroPaymetReport(companyId, branchId, startDate, endDate,
		        		igmNo, itemNo, sl,acc,cha);
		        
		        
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfImportLclCargoDestuffReport(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.toGetImportLCLCargoDestuffReport(companyId, branchId, startDate, endDate, igmNo, itemNo, sl, acc);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Import LCL Cargo Destuff Report");

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

		        String[] columnsHeader = {"Sr No", "Container No", "Size", "Type", "Tare WT", "Gate In Date", "Destuff Date", "Agent Name", "Line",
		        		"Vessel Name", "VOY No", "IGM No", "Item No", "Manifested Weight", "Manifested Qty", "Destuff Qty", "Excess", "Short", 
		        		"Weight", "Pkg Type", "Remarks"};
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
		        reportTitleCell.setCellValue("Import LCL Cargo Destuff Report" );

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
		        	 reportTitleCell1.setCellValue("Import LCL Cargo Destuff Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Import LCL Cargo Destuff Report From : " + formattedStartDate + " to " + formattedEndDate);
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

//		                case "Container No":
//		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
//		                    break;
//
//		                case "Size":
//		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
//		                    break;
//
//		                case "Type":
//		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
//		                    break;
//
//		                case "Tare WT":
//		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
//		                    break;
		                    
		                case "Container No":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[0].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[1].toString() : "");
		                    break;

		                case "Type":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[2].toString() : "");
		                    break;

		                case "Tare WT":
		                    cell.setCellValue(isFirstRowForContainer ? resultData1[3].toString() : "");
		                    break;

		                case "Gate In Date":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Destuff Date":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Agent Name":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Line":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "VOY No":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "IGM No":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Item No":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Manifested Weight":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Manifested Qty":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Destuff Qty":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "Excess":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Short":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Weight":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Pkg Type":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Remarks":
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
			        empty.createCell(0).setCellValue("Summery"); // You can set a value or keep it blank	
			        		
			        Row emptyRow1 = sheet.createRow(rowNum++);
			        
			        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank
			        // Header row for summary
			        String[] columnsHeaderSummery = {
			            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
			        };

			        // Create a map to store container count for each agent
			        Map<String, int[]> agentContainerCount = new HashMap<>();

			        // Iterate over importDetails and count the container sizes for each shipping agent
//			        importDetails.forEach(i -> {
//			            String agent = i[7] != null ? i[7].toString() : "Unknown"; // Shipping Agent at index 10
//			            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3
//
//			            // Initialize counts for this agent if not already present
//			            if (!agentContainerCount.containsKey(agent)) {
//			                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
//			            }
//
//			            // Increment the count based on container size
//			            switch (containerSize) {
//			                case "20":
//			                    agentContainerCount.get(agent)[0]++;
//			                    break;
//			                case "22":
//			                    agentContainerCount.get(agent)[1]++;
//			                    break;
//			                case "40":
//			                    agentContainerCount.get(agent)[2]++;
//			                    break;
//			                case "45":
//			                    agentContainerCount.get(agent)[3]++;
//			                    break;
//			                default:
//			                    break;
//			            }
//			        });
			        
//			        Map<String, int[]> agentContainerCount = new HashMap<>();
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
			        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 36 * 306); 
		        
		        sheet.setColumnWidth(8, 36 * 306); 
		        sheet.setColumnWidth(9, 27 * 306); 
		        sheet.setColumnWidth(10, 17 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 16 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 14 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(20, 27 * 306); 
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.yardBalanceCOntainerReport(companyId, branchId, endDate);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Yard Balance Report Details");

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
		                               "Sr. No.", "Container No.", "Container Gate In Date and Time", "IGM NO.", "IGM DATE", 
		                               "IGM LINE NO.", "IGM SUBLINE NO.", "B.E. NO.", "B.E. DATE", "Importer Name", 
		                               "Goods Cargo Description", "CHA Name","CHA Code", "Container Size", 
		                               "FCL/LCL", "B/L No.", "B/L Date", "CFS Name", "Container Type", 
		                               "Delivery Mode Loaded/Destuff", "Vessel Name", "Vessel Berthing Date", 
		                               "Port Out Date and Time", "VCN No.", "VOY No.", "Line", "Agent", 
		                               "Port", "Agent Seal", "Custom Seal", "Pkgs", "Weight", "Gate In Remark"
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
		        reportTitleCell.setCellValue("Yard Balance Report Details");

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
		        	 reportTitleCell1.setCellValue("Yard Balance Report Details Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Yard Balance Report Details Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr. No.":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "Container No.":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Container Gate In Date and Time":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "IGM NO.":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;

		                case "IGM DATE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "IGM LINE NO.":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "IGM SUBLINE NO.":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "B.E. NO.":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "B.E. DATE":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Importer Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Goods Cargo Description":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "CHA Name":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "CHA Code":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Container Size":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "FCL/LCL":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "B/L No.":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "B/L Date":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "CFS Name":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Container Type":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Delivery Mode Loaded/Destuff":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Vessel Name":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                case "Vessel Berthing Date":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "Port Out Date and Time":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "VCN No.":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "VOY No.":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                case "Line":
		                    cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                    break;

		                case "Agent":
		                    cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                    break;

		                case "Port":
		                    cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                    break;

		                case "Agent Seal":
		                    cell.setCellValue(resultData1[27] != null ? resultData1[27].toString() : "");
		                    break;

		                case "Custom Seal":
		                    cell.setCellValue(resultData1[28] != null ? resultData1[28].toString() : "");
		                    break;

		                case "Pkgs":
		                    cell.setCellValue(resultData1[29] != null ? resultData1[29].toString() : "");
		                    break;

		                case "Weight":
		                    cell.setCellValue(resultData1[30] != null ? resultData1[30].toString() : "");
		                    break;

		                case "Gate In Remark":
		                    cell.setCellValue(resultData1[31] != null ? resultData1[31].toString() : "");
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
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(8, 28 * 306); 
		        sheet.setColumnWidth(9, 36 * 306); 
		        sheet.setColumnWidth(10, 36 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(11, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(13,  18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        
		        sheet.setColumnWidth(19, 27 * 306);
		        sheet.setColumnWidth(20, 18 * 306);
		        sheet.setColumnWidth(21, 18 * 306);
		        sheet.setColumnWidth(22, 18 * 306);
		        sheet.setColumnWidth(23, 18 * 306);
		        sheet.setColumnWidth(24, 18 * 306);
		        sheet.setColumnWidth(25, 36 * 306); 
		        sheet.setColumnWidth(26, 36 * 306); 
		        sheet.setColumnWidth(27, 18 * 306);
		        sheet.setColumnWidth(28, 18 * 306);
		        sheet.setColumnWidth(29, 18 * 306);
		        sheet.setColumnWidth(30, 18 * 306);
		        sheet.setColumnWidth(31, 18 * 306);
		        sheet.setColumnWidth(32, 18 * 306);

		  
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
	
	
	
	
	
	
	
	public byte[] createExcelReportOfLoadedToDestuffEmptyOutReportDetails(String companyId,
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
		    	
		    	
		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

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

		        
		        List<Object[]> importDetails = importReportsRepository.loadedToDestuffEmptyOutReportDetails(companyId, branchId, startDate, endDate);
		        
		        
		        System.out.println("importDetails___________________________________________"+importDetails);
		        
		        List<Object[]> newlist = new ArrayList<>();
		        importDetails.forEach(i -> {
		            System.out.println(Arrays.toString(i)); // Print the element
		            newlist.add(i);                         // Add to the new list
		        });
		        
		        
		        System.out.println("newlist__________________________________________"+newlist);
		        
		        Sheet sheet = workbook.createSheet("Loaded To Destuff Empty Out Report Details");

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
		        	    "Container Size", 
		        	    "Container Type", 
		        	    "ISO Code", 
		        	    "Gate In Date", 
		        	    "Empty Out Date", 
		        	    "Line", 
		        	    "Agent Name", 
		        	    "CHA", 
		        	    "Importer Name", 
		        	    "Movement by (CFS/Party)", 
		        	    "Destination", 
		        	    "Remark", 
		        	    "Vehicle No", 
		        	    "Transporter Name"
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
		        reportTitleCell.setCellValue("Loaded To Destuff Empty Out Report Details" );

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
		        	 reportTitleCell1.setCellValue("Loaded To Destuff Empty Out Details Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Loaded To Destuff Empty Out Details Report From : " + formattedStartDate + " to " + formattedEndDate);
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
//		                switch (columnsHeader[i]) {
//		                case "Sr No":
//		                    cell.setCellValue(serialNo++); // Increment serial number
//		                    break;
//
//		                case "Container No":
//		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
//		                    break;
//
//		                case "Container Size":
//		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
//		                    break;
//
//		                case "Container Type":
//		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
//		                    break;
//
//		                case "ISO Code":
//		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
//		                    break;
//
//		                case "Gate In Date":
//		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
//		                    break;
//
//		                case "Empty Out Date":
//		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
//		                    break;
//
//		                case "Line":
//		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
//		                    break;
//
//		                case "Agent Name":
//		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
//		                    break;
//
//		                case "CHA":
//		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
//		                    break;
//
//		                case "Importer Name":
//		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
//		                    break;
//
//		                case "Movement by (CFS/Party)":
//		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
//		                    break;
//
//		                case "Destination":
//		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
//		                    break;
//
//		                case "Remark":
//		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
//		                    break;
//
//		                case "Vehicle No":
//		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
//		                    break;
//
//		                case "Transporter Name":
//		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
//		                    break;
//
//		                default:
//		                    cell.setCellValue(""); // Handle undefined columns
//		                    break;
//		            }
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
}
