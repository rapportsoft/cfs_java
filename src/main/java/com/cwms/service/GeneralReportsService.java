package com.cwms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GeneralReportsRepo;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class GeneralReportsService {
	

	@Autowired
	private GeneralReportsRepo generalReportsRepo;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private CfinbondcrgRepo cfinbondcrgRepo;
	
	public ResponseEntity<List<Object[]>> getDataOfImportContainerDetailsReport(
	        String companyId,
	        String branchId,
	        String username,
	        String type,
	        String companyname,
	        String branchname,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String acc,
	        String cha,
	        String selectedReport
	) throws DocumentException {

	    Calendar cal = Calendar.getInstance();

	    // Normalize startDate to the beginning of the day (00:00:00.000)
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
	        case "General Receiving Register":
	            importDetails = generalReportsRepo.findReceivingDetails(
	                    companyId, branchId, startDate, endDate, acc, cha);
	            break;

	        case "General Delivery Register":
	            importDetails = generalReportsRepo.getGeneralDeliveryReport(companyId, branchId, startDate,endDate,acc,cha);
	            break;

	        case "General Stock Report":
	            importDetails = generalReportsRepo.getReceivingDetails(companyId, branchId,endDate,acc,cha);
	            break;
	            
	        default:
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    // Check if the list is empty and return the appropriate response
	    if (importDetails.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
	    }

	    return new ResponseEntity<>(importDetails, HttpStatus.OK); // Records found
	}
	
	public byte[] createGeneralReceivingDetailsRegister(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
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
		    
		        
		        List<Object[]> resultData = generalReportsRepo.findReceivingDetails(companyId, branchId, startDate,endDate,acc,cha);

		        
		        int totalNoOf20FT = resultData.stream()
		        	    .map(row -> (row[7] != null && !row[7].toString().isEmpty()) ? Integer.parseInt(row[7].toString()) : 0)
		        	    .reduce(0, Integer::sum);

		        	int totalNoOf40FT = resultData.stream()
		        	    .map(row -> (row[8] != null && !row[8].toString().isEmpty()) ? Integer.parseInt(row[8].toString()) : 0)
		        	    .reduce(0, Integer::sum);

		        BigDecimal totalGateInPackages = resultData.stream()
		        	    .map(i -> i[9] != null ? new BigDecimal(i[9].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);
		        
		        BigDecimal totalGateInWeight = resultData.stream()
		        	    .map(i -> i[10] != null ? new BigDecimal(i[10].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);
		        
		        BigDecimal totalReceivingPackages= resultData.stream()
		        	    .map(i -> i[11] != null ? new BigDecimal(i[11].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);
		        
		        BigDecimal totalReceivingweight= resultData.stream()
		        	    .map(i -> i[12] != null ? new BigDecimal(i[12].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);

		        
		        
		        Sheet sheet = workbook.createSheet("General Receiving Register");

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
		        	    "Sr No", "Receiving Date", "Importer Name", "CHA Name", "BE NO", "BE Date", "Commodity",
		        	    "Container", "No Of 20", "No Of 40","Gate In Packages","Gate In Weight", "Received Quantity","Receiving Weight", "Package Type", 
		        	    "Vehicle No", "Transporter", "Handling","Handling1","Handling2",
		        	    "Owner1","Owner2", "Owner3", "Job No", "Remarks"
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("General Receiving Register" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0,10));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("General Receiving Register Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("General Receiving Register Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                    switch (columnsHeader[i]) {
		                        case "Sr No":
		                            cell.setCellValue(serialNo++);
		                            break;

		                        case "Receiving Date":
		                            if (resultData1[0] != null) {
		                                cell.setCellValue(resultData1[0].toString());
		                                cell.setCellStyle(dateCellStyle1); // Apply date format
		                            } else {
		                                cell.setBlank();
		                            }
		                            break;

		                        case "Importer Name":
		                            cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                            break;

		                        case "CHA Name":
		                            cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                            break;

		                        case "BE NO":
		                            cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                            break;

		                        case "BE Date":
		                            if (resultData1[4] != null) {
		                                cell.setCellValue(resultData1[4].toString());
		                                cell.setCellStyle(dateCellStyle1); // Apply date format
		                            } else {
		                                cell.setBlank();
		                            }
		                            break;

		                        case "Commodity":
		                            cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                            break;

		                        case "Container":
		                            cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                            break;

		                        case "No Of 20":
		                            cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                            break;

		                        case "No Of 40":
		                            cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                            break;

		                        case "Gate In Packages":
		                        	if (resultData1[9] != null) {
		                                try {
		                                    double gateInPackages = new BigDecimal(resultData1[9].toString()).doubleValue(); // Convert to double
		                                    cell.setCellValue(gateInPackages);
		                                    cell.setCellStyle(numberCellStyle); // Apply numeric format
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if not a valid number
		                                }
		                            } else {
		                                cell.setCellValue(""); // Set blank if null
		                            }
		                            
		                            
		                            break;

		                        case "Gate In Weight":
		                            if (resultData1[10] != null) {
		                                try {
		                                    double gateInWeight = new BigDecimal(resultData1[10].toString()).doubleValue();
		                                    cell.setCellValue(gateInWeight);
		                                    cell.setCellStyle(numberCellStyle);
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if parsing fails
		                                }
		                            } else {
		                                cell.setCellValue("");
		                            }
		                            break;

		                        case "Received Quantity":
		                            if (resultData1[11] != null) {
		                                try {
		                                    double receivedQuantity = new BigDecimal(resultData1[11].toString()).doubleValue();
		                                    cell.setCellValue(receivedQuantity);
		                                    cell.setCellStyle(numberCellStyle);
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if parsing fails
		                                }
		                            } else {
		                                cell.setCellValue("");
		                            }
		                            break;

		                        case "Receiving Weight":
		                            if (resultData1[12] != null) {
		                                try {
		                                    double receivingWeight = new BigDecimal(resultData1[12].toString()).doubleValue();
		                                    cell.setCellValue(receivingWeight);
		                                    cell.setCellStyle(numberCellStyle);
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if parsing fails
		                                }
		                            } else {
		                                cell.setCellValue("");
		                            }
		                            break;

		                        case "Package Type":
		                            cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                            break;

		                        case "Vehicle No":
		                            cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                            break;

		                        case "Transporter":
		                            cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                            break;

		                        case "Handling":
		                            cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                            break;

		                        case "Handling1":
		                            cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                            break;

		                        case "Handling2":
		                            cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                            break;

		                        case "Owner1":
		                            cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                            break;

		                        case "Owner2":
		                            cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                            break;

		                        case "Owner3":
		                            cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                            break;

		                        case "Job No":
		                            cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                            break;

		                        case "Remarks":
		                            cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                            break;

		                        default:
		                            cell.setCellValue(""); // Handle undefined columns if necessary
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
		            
		            
		            case "Sr No":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                   break;
                   
                   
		            case "Container":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "No Of 20":
		                    totalCell.setCellValue(totalNoOf20FT);
		                    break;
		                case "No Of 40":
		                    totalCell.setCellValue(totalNoOf40FT);
		                    break;
		                case "Gate In Packages":
		                    totalCell.setCellValue(totalGateInPackages.doubleValue());
		                    break;
		                case "Gate In Weight":
		                    totalCell.setCellValue(totalGateInWeight.doubleValue());
		                    break;
		                case "Received Quantity":
		                    totalCell.setCellValue(totalReceivingPackages.doubleValue());
		                    break;
		                case "Receiving Weight":
		                    totalCell.setCellValue(totalReceivingweight.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 27 * 306); 
		        sheet.setColumnWidth(3, 27 * 306); 
		        
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(10, 18 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 

		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 27 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(17, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(18, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(19, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(20, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(21, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(22, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(23, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(24, 18 * 306); // Set width for "Importer" (40 characters wide)

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
	
	
	
	
	
	
	
	
	
	public byte[] createGeneralDeliveryRegisterReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
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
		    
		        
		        List<Object[]> resultData = generalReportsRepo.getGeneralDeliveryReport(companyId, branchId, startDate,endDate,acc,cha);

		        int totalNoOf20FT = resultData.stream()
		        	    .map(row -> (row[7] != null && !row[7].toString().isEmpty()) ? Integer.parseInt(row[7].toString()) : 0)
		        	    .reduce(0, Integer::sum);

		        	int totalNoOf40FT = resultData.stream()
		        	    .map(row -> (row[8] != null && !row[8].toString().isEmpty()) ? Integer.parseInt(row[8].toString()) : 0)
		        	    .reduce(0, Integer::sum);

		        BigDecimal totalGateInPackages = resultData.stream()
		        	    .map(i -> i[9] != null ? new BigDecimal(i[9].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);
		        
		        BigDecimal totalGateInWeight = resultData.stream()
		        	    .map(i -> i[10] != null ? new BigDecimal(i[10].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);
		        
		        BigDecimal totalReceivingPackages= resultData.stream()
		        	    .map(i -> i[11] != null ? new BigDecimal(i[11].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);
		        
		        BigDecimal totalReceivingweight= resultData.stream()
		        	    .map(i -> i[12] != null ? new BigDecimal(i[12].toString()) : BigDecimal.ZERO)
		        	    .reduce(BigDecimal.ZERO, BigDecimal::add);

		        
		        Sheet sheet = workbook.createSheet("General Delivery Register");

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
		        	    "Sr No", "Delivery Date", "Importer Name", "CHA Name", "BE NO", "BE Date", "Commodity",
		        	    "Container", "No Of 20", "No Of 40","Delivery Packages","Delivery Weight", "Out Quantity","Out Weight", "Package Type", "Yard Location", "Yard Block", "Block Cell No", 
		        	    "Vehicle No", "Transporter", "Handling","Handling1","Handling2",
		        	    "Owner1","Owner2", "Owner3", "Job No", "Remarks"
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
		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

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
		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

		        
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
		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 10));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow = sheet.createRow(3);
		        Cell reportTitleCell = reportTitleRow.createCell(0);
		        reportTitleCell.setCellValue("General Delivery Register" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0,10));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("General Delivery Register Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("General Delivery Register Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                    switch (columnsHeader[i]) {
		                        case "Sr No":
		                            cell.setCellValue(serialNo++);
		                            break;

		                        case "Delivery Date":
		                            if (resultData1[0] != null) {
		                                cell.setCellValue(resultData1[0].toString());
		                                cell.setCellStyle(dateCellStyle1); // Apply date format
		                            } else {
		                                cell.setBlank();
		                            }
		                            break;

		                        case "Importer Name":
		                            cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                            break;

		                        case "CHA Name":
		                            cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                            break;

		                        case "BE NO":
		                            cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                            break;

		                        case "BE Date":
		                            if (resultData1[4] != null) {
		                                cell.setCellValue(resultData1[4].toString());
		                                cell.setCellStyle(dateCellStyle1); // Apply date format
		                            } else {
		                                cell.setBlank();
		                            }
		                            break;

		                        case "Commodity":
		                            cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                            break;

		                        case "Container":
		                            cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                            break;

		                        case "No Of 20":
		                            cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                            break;

		                        case "No Of 40":
		                            cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                            break;

		                        case "Delivery Packages":
		                        	if (resultData1[9] != null) {
		                                try {
		                                    double gateInPackages = new BigDecimal(resultData1[9].toString()).doubleValue(); // Convert to double
		                                    cell.setCellValue(gateInPackages);
		                                    cell.setCellStyle(numberCellStyle); // Apply numeric format
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if not a valid number
		                                }
		                            } else {
		                                cell.setCellValue(""); // Set blank if null
		                            }
		                            
		                            
		                            break;

		                        case "Delivery Weight":
		                            if (resultData1[10] != null) {
		                                try {
		                                    double gateInWeight = new BigDecimal(resultData1[10].toString()).doubleValue();
		                                    cell.setCellValue(gateInWeight);
		                                    cell.setCellStyle(numberCellStyle);
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if parsing fails
		                                }
		                            } else {
		                                cell.setCellValue("");
		                            }
		                            break;

		                        case "Out Quantity":
		                            if (resultData1[11] != null) {
		                                try {
		                                    double receivedQuantity = new BigDecimal(resultData1[11].toString()).doubleValue();
		                                    cell.setCellValue(receivedQuantity);
		                                    cell.setCellStyle(numberCellStyle);
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if parsing fails
		                                }
		                            } else {
		                                cell.setCellValue("");
		                            }
		                            break;

		                        case "Out Weight":
		                            if (resultData1[12] != null) {
		                                try {
		                                    double receivingWeight = new BigDecimal(resultData1[12].toString()).doubleValue();
		                                    cell.setCellValue(receivingWeight);
		                                    cell.setCellStyle(numberCellStyle);
		                                } catch (NumberFormatException e) {
		                                    cell.setCellValue(""); // Set blank if parsing fails
		                                }
		                            } else {
		                                cell.setCellValue("");
		                            }
		                            break;

		                        case "Package Type":
		                            cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                            break;
		                            
		                        case "Yard Location":
		                            cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                            break;
		                            
		                        case "Yard Block":
		                            cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                            break;

		                        case "Block Cell No":
		                            cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                            break;

		                        case "Vehicle No":
		                            cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                            break;

		                        case "Transporter":
		                            cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                            break;

		                        case "Handling":
		                            cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                            break;

		                        case "Handling1":
		                            cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                            break;

		                        case "Handling2":
		                            cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                            break;

		                        case "Owner1":
		                            cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                            break;

		                        case "Owner2":
		                            cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                            break;

		                        case "Owner3":
		                            cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                            break;

		                        case "Job No":
		                            cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                            break;

		                        case "Remarks":
		                            cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                            break;

		                        default:
		                            cell.setCellValue(""); // Handle undefined columns if necessary
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
		            
		            
		            case "Sr No":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                   break;
                   
                   
		            case "Container":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "No Of 20":
		                    totalCell.setCellValue(totalNoOf20FT);
		                    break;
		                case "No Of 40":
		                    totalCell.setCellValue(totalNoOf40FT);
		                    break;
		                case "Delivery Packages":
		                    totalCell.setCellValue(totalGateInPackages.doubleValue());
		                    break;
		                case "Delivery Weight":
		                    totalCell.setCellValue(totalGateInWeight.doubleValue());
		                    break;
		                case "Out Quantity":
		                    totalCell.setCellValue(totalReceivingPackages.doubleValue());
		                    break;
		                case "Out Weight":
		                    totalCell.setCellValue(totalReceivingweight.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 27 * 306); 
		        sheet.setColumnWidth(3, 27 * 306); 
		        
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        sheet.setColumnWidth(6, 18 * 306); 
		        sheet.setColumnWidth(7, 18 * 306); 
		        
		        sheet.setColumnWidth(10, 18 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        
		        sheet.setColumnWidth(18, 18 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 

//		        sheet.setColumnWidth(15, 18 * 306); 
//		        sheet.setColumnWidth(16, 27 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(17, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(18, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(19, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(20, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(21, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(22, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(23, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(24, 18 * 306); // Set width for "Importer" (40 characters wide)

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createGeneralStockReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
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
		    
		        
		        List<Object[]> resultData = generalReportsRepo.getReceivingDetails(companyId, branchId,endDate,acc,cha);
		        
		        BigDecimal totalGateInPackages = resultData.stream().map(i -> i[7] !=null ? new BigDecimal(i[7].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalGateInWeight = resultData.stream().map(i -> i[8] !=null ? new BigDecimal(i[8].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalReceivingPkg = resultData.stream().map(i -> i[9] !=null ? new BigDecimal(i[9].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalReceivingWeight = resultData.stream().map(i -> i[10] !=null ? new BigDecimal(i[10].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalDeliveryPkg = resultData.stream().map(i -> i[11] !=null ? new BigDecimal(i[11].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalDeliveryWeight = resultData.stream().map(i -> i[12] !=null ? new BigDecimal(i[12].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalGatePassPkg = resultData.stream().map(i -> i[13] !=null ? new BigDecimal(i[13].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalGatePassWeight = resultData.stream().map(i -> i[14] !=null ? new BigDecimal(i[14].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalBalanceReceiving = resultData.stream().map(i -> i[15] !=null ? new BigDecimal(i[15].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        BigDecimal totalBalanceDelivery = resultData.stream().map(i -> i[16] !=null ? new BigDecimal(i[16].toString()) : BigDecimal.ZERO).reduce(BigDecimal.ZERO,BigDecimal::add);
		        
		        Sheet sheet = workbook.createSheet("General Stock Report");

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
		        	    "Sr No", "Deposit No", "Boe No","Boe Date",  "Importer Name","CHA",
		        	    "Commodity","Commodity Desc",  "Gate In Packages","Gross Weight","Receiving Pkg","Receiving Weight", 
		        	    "Delivery Pkg","Delivery Weight","Gate Pass Pkg","Gate Pass Weight", "Remaining Packages", "Remaining Delivery", "Warehouse","Warehouse Block","Warehouse Cell No",
		        	    "Job No", "Job Trans Id"
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
		        reportTitleCell.setCellValue("General Stock Report" );

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
		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0,7));
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
//		        if(formattedStartDate.equals("N/A"))
//		        {
		        	 reportTitleCell1.setCellValue("General Stock Report As On Date : " + formattedEndDate);
//		        }
//		        else 
//		        {
//		        	 reportTitleCell1.setCellValue("General Stock Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Object[] resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) 
		                {
		                case "Sr No":
		                    cell.setCellValue(serialNo++);
		                    break;

		                case "Deposit No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "Boe No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "Boe Date":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle1); // Apply date format
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Importer Name":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "CHA":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Commodity":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Commodity Desc":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Gate In Packages":
		                    if (resultData1[7] != null) {
		                        try {
		                            double gateInPackages = new BigDecimal(resultData1[7].toString()).doubleValue();
		                            cell.setCellValue(gateInPackages);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                        cell.setCellValue("0");
		                    }
		                    break;

		                case "Gross Weight":
		                    if (resultData1[8] != null) {
		                        try {
		                            double grossWeight = new BigDecimal(resultData1[8].toString()).doubleValue();
		                            cell.setCellValue(grossWeight);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Receiving Pkg":
		                    if (resultData1[9] != null) {
		                        try {
		                            double receivingPkg = new BigDecimal(resultData1[9].toString()).doubleValue();
		                            cell.setCellValue(receivingPkg);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Receiving Weight":
		                    if (resultData1[10] != null) {
		                        try {
		                            double receivingWeight = new BigDecimal(resultData1[10].toString()).doubleValue();
		                            cell.setCellValue(receivingWeight);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Delivery Pkg":
		                    if (resultData1[11] != null) {
		                        try {
		                            double deliveryPkg = new BigDecimal(resultData1[11].toString()).doubleValue();
		                            cell.setCellValue(deliveryPkg);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Delivery Weight":
		                    if (resultData1[12] != null) {
		                        try {
		                            double deliveryWeight = new BigDecimal(resultData1[12].toString()).doubleValue();
		                            cell.setCellValue(deliveryWeight);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Gate Pass Pkg":
		                    if (resultData1[13] != null) {
		                        try {
		                            double gatePassPkg = new BigDecimal(resultData1[13].toString()).doubleValue();
		                            cell.setCellValue(gatePassPkg);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Gate Pass Weight":
		                    if (resultData1[14] != null) {
		                        try {
		                            double gatePassWeight = new BigDecimal(resultData1[14].toString()).doubleValue();
		                            cell.setCellValue(gatePassWeight);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Remaining Packages":
		                    if (resultData1[15] != null) {
		                        try {
		                            double remainingPkg = new BigDecimal(resultData1[15].toString()).doubleValue();
		                            cell.setCellValue(remainingPkg);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Remaining Delivery":
		                    if (resultData1[16] != null) {
		                        try {
		                            double remainingDelivery = new BigDecimal(resultData1[16].toString()).doubleValue();
		                            cell.setCellValue(remainingDelivery);
		                            cell.setCellStyle(numberCellStyle);
		                        } catch (NumberFormatException e) {
		                            cell.setCellValue("");
		                        }
		                    } else {
		                    	cell.setCellValue("0");
		                    }
		                    break;

		                case "Warehouse":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                case "Warehouse Block":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "Warehouse Cell No":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;

		                case "Job No":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "Job Trans Id":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns if necessary
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
		            
		            case "Sr No":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
                    
		            case "Commodity Desc":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
                   break;
		            case "Gate In Packages":
		                totalCell.setCellValue(totalGateInPackages.doubleValue());
		                break;
		            case "Gross Weight":
		                totalCell.setCellValue(totalGateInWeight.doubleValue());
		                break;
		            case "Receiving Pkg":
		                totalCell.setCellValue(totalReceivingPkg.doubleValue());
		                break;
		            case "Receiving Weight":
		                totalCell.setCellValue(totalReceivingWeight.doubleValue());
		                break;
		            case "Delivery Pkg":
		                totalCell.setCellValue(totalDeliveryPkg.doubleValue());
		                break;
		            case "Delivery Weight":
		                totalCell.setCellValue(totalDeliveryWeight.doubleValue());
		                break;
		            case "Gate Pass Pkg":
		                totalCell.setCellValue(totalGatePassPkg.doubleValue());
		                break;
		            case "Gate Pass Weight":
		                totalCell.setCellValue(totalGatePassWeight.doubleValue());
		                break;
		            case "Remaining Packages":
		                totalCell.setCellValue(totalBalanceReceiving.doubleValue());
		                break;
		            case "Remaining Delivery":
		                totalCell.setCellValue(totalBalanceDelivery.doubleValue());
		                break;
		            default:
		                totalCell.setCellValue(""); // Set empty if column doesn't match
		                break;
		            }
		        }

		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(3, 27 * 306); 
		        sheet.setColumnWidth(4, 27 * 306); 
		        sheet.setColumnWidth(5, 27 * 306); 
		        sheet.setColumnWidth(7, 27 * 306); 
		        
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        
		        sheet.setColumnWidth(2, 18 * 306); 
//		        sheet.setColumnWidth(15, 30 * 306); 
//		        sheet.setColumnWidth(16, 40 * 306); // Set width for "Importer" (40 characters wide)
//		        
//		        sheet.setColumnWidth(23, 30 * 306); // Set width for "CHA" (30 characters wide)
//		        sheet.setColumnWidth(24, 18 * 306); // Set width for "Importer" (40 characters wide)

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
