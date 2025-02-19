package com.cwms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.stereotype.Service;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.entities.Party;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.DMRRepository;
import com.cwms.repository.ExportOperationalReportRepository;
import com.cwms.repository.FinanceReportsRepository;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class DMRService {

	@Autowired
	private DMRRepository dmrRepository;
	
	@Autowired
	private FinanceReportsRepository financeReportsRepo;
	
	@Autowired
	private ExportOperationalReportRepository exportOperationalReportRepo;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	public List<Party> getAllAccountHolder(String companyId, String branchId,String lin,String agt,String cha,String partyName) {
		return dmrRepository.getAllAccountHolder(companyId, branchId,lin,agt,cha,partyName);
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
	
	public byte[] createExcelReportOfExportDmrReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String accountHolderName,
	        String acc,
	        String cha
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

		        
		        List<Object[]> importDetails = dmrRepository.exportEmptyInDetails(companyId, branchId,acc,startDate, endDate);
		        
		        
		       
		        
		        Sheet sheet = workbook.createSheet("Empty IN");
		        
//		        Sheet sheetIn = workbook.createSheet("Empty IN");
//		        createExcelOfTEUSOnlyFCL(sheetIn, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet1 = workbook.createSheet("Empty Balance");
		        createExcelOfEmptyBalance(sheet1, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet2 = workbook.createSheet("Export Empty Out");
		        createExcelOfExportEmptyOut(sheet2, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
//		        
		        Sheet sheet3 = workbook.createSheet("Stuffing Details");
		        createExcelOfStuffingDetails(sheet3, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
//		        
		        Sheet sheet4 = workbook.createSheet("Daily Carting Details");
		        createExcelOfDailyCartingDetails(sheet4, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet5 = workbook.createSheet("Loaded Out");
		        createExcelOfLoadedOut(sheet5, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
//		        ,accountHolderName
		        Sheet sheet6 = workbook.createSheet("Loaded Balance");
		        createExcelOfLoadedBalance(sheet6, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet7 = workbook.createSheet("Cargo Balance");
		        createExcelOfCargoBalance(sheet7, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet8 = workbook.createSheet("Back To Town");
		        createExcelOfBackToTown(sheet8, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
//		        
//		        Sheet sheet9 = workbook.createSheet("Port Shut Out In");
//		        createExcelOfPortShutOutIn(sheet9, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        

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
		        	    "Sr.NO.", "In Date", "In Time", "Container No", "Size", "Type", 
		        	    "Tare Weight", "Pay Load", "Seal No", "AGENT NAME", "Shipping Line", 
		        	    "Booking No", "vehicle", "Transport", "Empty Yard", "Remark", "CfsCode"
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
		        reportTitleCell.setCellValue("Empty In Report For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Empty In Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Empty In Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr.NO.":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "In Date":
		                    if (resultData1[0] != null) {
		                        cell.setCellValue(resultData1[0].toString());
		                        // Optionally apply a date cell style if you have one:
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "In Time":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Container No":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Size":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Type":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Tare Weight":
		                    double tareWeight = 0.0;
		                    if (resultData1[5] != null) {
		                        try {
		                            tareWeight = Double.parseDouble(resultData1[5].toString());
		                        } catch (NumberFormatException e) {
		                            tareWeight = 0.0;
		                        }
		                    }
		                    cell.setCellValue(tareWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Pay Load":
		                    double payLoad = 0.0;
		                    if (resultData1[6] != null) {
		                        try {
		                            payLoad = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            payLoad = 0.0;
		                        }
		                    }
		                    cell.setCellValue(payLoad);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Seal No":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "AGENT NAME":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(resultData1[8].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Shipping Line":
		                    if (resultData1[9] != null) {
		                        cell.setCellValue(resultData1[9].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Booking No":
		                    if (resultData1[10] != null) {
		                        cell.setCellValue(resultData1[10].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "vehicle":
		                    if (resultData1[11] != null) {
		                        cell.setCellValue(resultData1[11].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Transport":
		                    if (resultData1[12] != null) {
		                        cell.setCellValue(resultData1[12].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Empty Yard":
		                    if (resultData1[13] != null) {
		                        cell.setCellValue(resultData1[13].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Remark":
		                    if (resultData1[14] != null) {
		                        cell.setCellValue(resultData1[14].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "CfsCode":
		                    if (resultData1[15] != null) {
		                        cell.setCellValue(resultData1[15].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		       
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[3] != null ? i[3].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 14 * 306); 
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(13, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(9, 27 * 306); 
		        sheet.setColumnWidth(10, 27 * 306);
		        sheet.setColumnWidth(14, 27 * 306);
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
	
	
	public byte[] createExcelReportOfImportDmrReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String accountHolderName,
	        String acc,
	        String cha
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchVesselBerthingDetails(companyId, branchId, acc, endDate);
		        
		        
		       
		        
		        Sheet sheet = workbook.createSheet("Port Pendency");
		        
//		        Sheet sheetIn = workbook.createSheet("Port Pendency");
//		        createExcelOfPortPendency(sheetIn, companyId, branchId,username,companyname,branchname, startDate,endDate,acc);
		        
		        Sheet sheet1 = workbook.createSheet("In Movement");
		        createExcelOfInMovement(sheet1, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet2 = workbook.createSheet("Yard Balance");
		        createExcelOfYardBalance(sheet2, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet3 = workbook.createSheet("Loaded Out");
		        createExcelOfImportLoadedOut(sheet3, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet4 = workbook.createSheet("Destuff");
		        createExcelOfImportDestuff(sheet4, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet5 = workbook.createSheet("Empty Balance");
		        createExcelOfImportEmptyBalance(sheet5, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        
		        Sheet sheet6 = workbook.createSheet("Empty Out");
		        createExcelOfImportEmptyOut(sheet6, companyId, branchId,username,companyname,branchname, startDate,endDate,acc,accountHolderName);
		        

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
		        	    "SR NO", "JOB PLACE DATE", "PORT", "BERTHING", "VSL Name", 
		        	    "VIA NO.", "SHIPPING LINE", "JOB ORDER PLACED", "PENDENCY 24Hrs", 
		        	    "CONTAINER NO", "Container size", "berthing place", "berthing placing time updated"
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
		        reportTitleCell.setCellValue("Port Pendency Report :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Port Pendency Report As On Date : " + formattedEndDate);
//		        }
//		        else 
//		        {
//		        	 reportTitleCell1.setCellValue("Port Pendency Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "JOB PLACE DATE":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "PORT":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "BERTHING":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;
		                    
		                case "VSL Name":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "VIA NO.":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "SHIPPING LINE":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "JOB ORDER PLACED":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "PENDENCY 24Hrs":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "CONTAINER NO":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "Container size":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "berthing place":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "berthing placing time updated":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }

		            }
		        }
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[9] != null ? i[9].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 14 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        sheet.setColumnWidth(6, 36 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
		        sheet.setColumnWidth(14, 14 * 306); 
		        sheet.setColumnWidth(13, 14 * 306);
		        
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
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
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.exportEmptyInDetails(companyId, branchId,billParty,startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "Sr.NO.", "In Date", "In Time", "Container No", "Size", "Type", 
		        	    "Tare Weight", "Pay Load", "Seal No", "AGENT NAME", "Shipping Line", 
		        	    "Booking No", "vehicle", "Transport", "Empty Yard", "Remark", "CfsCode"
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
		        reportTitleCell.setCellValue("Export Empty In Report for :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        
		        int total20 = 0;
		        int total40 = 0;
		        int totalTEUs = 0;
		        int serialNo = 1; // Initialize serial number counter
		        for (Object[] resultData1 : importDetails) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		          
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);
		               
		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
		                case "Sr.NO.":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "In Date":
		                    if (resultData1[0] != null) {
		                        cell.setCellValue(resultData1[0].toString());
		                        // Optionally apply a date cell style if you have one:
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "In Time":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Container No":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Size":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Type":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Tare Weight":
		                    double tareWeight = 0.0;
		                    if (resultData1[5] != null) {
		                        try {
		                            tareWeight = Double.parseDouble(resultData1[5].toString());
		                        } catch (NumberFormatException e) {
		                            tareWeight = 0.0;
		                        }
		                    }
		                    cell.setCellValue(tareWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Pay Load":
		                    double payLoad = 0.0;
		                    if (resultData1[6] != null) {
		                        try {
		                            payLoad = Double.parseDouble(resultData1[6].toString());
		                        } catch (NumberFormatException e) {
		                            payLoad = 0.0;
		                        }
		                    }
		                    cell.setCellValue(payLoad);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Seal No":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "AGENT NAME":
		                    if (resultData1[8] != null) {
		                        cell.setCellValue(resultData1[8].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Shipping Line":
		                    if (resultData1[9] != null) {
		                        cell.setCellValue(resultData1[9].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Booking No":
		                    if (resultData1[10] != null) {
		                        cell.setCellValue(resultData1[10].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "vehicle":
		                    if (resultData1[11] != null) {
		                        cell.setCellValue(resultData1[11].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Transport":
		                    if (resultData1[12] != null) {
		                        cell.setCellValue(resultData1[12].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Empty Yard":
		                    if (resultData1[13] != null) {
		                        cell.setCellValue(resultData1[13].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Remark":
		                    if (resultData1[14] != null) {
		                        cell.setCellValue(resultData1[14].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "CfsCode":
		                    if (resultData1[15] != null) {
		                        cell.setCellValue(resultData1[15].toString());
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		       
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[3] != null ? i[3].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 14 * 306); 
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(13, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(9, 27 * 306); 
		        sheet.setColumnWidth(10, 27 * 306);
		        sheet.setColumnWidth(14, 27 * 306);
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
	
	
	public byte[] createExcelOfEmptyBalance(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchExportBalanceDetails(companyId, branchId, billParty, endDate);
		        
		        
		      
		        
		         

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
		        	    "SR.NO.", "IN DATE", "In Time", "CONTAINER NO", "CONTAINER SIZE", 
		        	    "CONTAINER TYPE", "Tare Weight", "Pay Load", "Seal No", "AGENT NAME", 
		        	    "Shipping Line", "Booking No", "vehicle", "Transport", "Empty Yard", 
		        	    "REMARKS", "CfsCode"
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
		        reportTitleCell.setCellValue("Empty Balance For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
//		        }
//		        else 
//		        {
//		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		 
		                switch (columnsHeader[i]) {
		                    case "SR.NO.":
		                        cell.setCellValue(serialNo++); // Increment serial number
		                        break;
		                        
		                    case "IN DATE":
		                        if (resultData1[0] != null) {
		                            cell.setCellValue(resultData1[0].toString());
		                            // Optionally, apply a date style if you have one
		                            cell.setCellStyle(dateCellStyle);
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "In Time":
		                        if (resultData1[1] != null) {
		                            cell.setCellValue(resultData1[1].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "CONTAINER NO":
		                        if (resultData1[2] != null) {
		                            cell.setCellValue(resultData1[2].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "CONTAINER SIZE":
		                        if (resultData1[3] != null) {
		                            cell.setCellValue(resultData1[3].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "CONTAINER TYPE":
		                        if (resultData1[4] != null) {
		                            cell.setCellValue(resultData1[4].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "Tare Weight":
		                        double tareWeight = 0.0;
		                        if (resultData1[5] != null) {
		                            try {
		                                tareWeight = Double.parseDouble(resultData1[5].toString());
		                            } catch (NumberFormatException e) {
		                                tareWeight = 0.0;
		                            }
		                        }
		                        cell.setCellValue(tareWeight);
		                        cell.setCellStyle(numberCellStyle);
		                        break;
		                        
		                    case "Pay Load":
		                        double payLoad = 0.0;
		                        if (resultData1[6] != null) {
		                            try {
		                                payLoad = Double.parseDouble(resultData1[6].toString());
		                            } catch (NumberFormatException e) {
		                                payLoad = 0.0;
		                            }
		                        }
		                        cell.setCellValue(payLoad);
		                        cell.setCellStyle(numberCellStyle);
		                        break;
		                        
		                    case "Seal No":
		                        if (resultData1[7] != null) {
		                            cell.setCellValue(resultData1[7].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "AGENT NAME":
		                        if (resultData1[8] != null) {
		                            cell.setCellValue(resultData1[8].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "Shipping Line":
		                        if (resultData1[9] != null) {
		                            cell.setCellValue(resultData1[9].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "Booking No":
		                        if (resultData1[10] != null) {
		                            cell.setCellValue(resultData1[10].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "vehicle":
		                        if (resultData1[11] != null) {
		                            cell.setCellValue(resultData1[11].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "Transport":
		                        if (resultData1[12] != null) {
		                            cell.setCellValue(resultData1[12].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "Empty Yard":
		                        if (resultData1[13] != null) {
		                            cell.setCellValue(resultData1[13].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "REMARKS":
		                        if (resultData1[14] != null) {
		                            cell.setCellValue(resultData1[14].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                    case "CfsCode":
		                        if (resultData1[15] != null) {
		                            cell.setCellValue(resultData1[15].toString());
		                        } else {
		                            cell.setBlank();
		                        }
		                        break;
		                        
		                        
		                    default:
		                        cell.setCellValue("");
		                        break;
		                }
		            
		            }
		        }
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[3] != null ? i[3].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306);
		        sheet.setColumnWidth(6, 14 * 306);
		        sheet.setColumnWidth(7, 14 * 306);
		        sheet.setColumnWidth(8, 14 * 306);
		        sheet.setColumnWidth(9, 27 * 306);
		        sheet.setColumnWidth(10, 27 * 306);
		        sheet.setColumnWidth(11, 14 * 306);
		        sheet.setColumnWidth(12, 14 * 306);
		        sheet.setColumnWidth(13, 27 * 306);
		        sheet.setColumnWidth(14, 14 * 306);
		        sheet.setColumnWidth(15, 14 * 306);
		        sheet.setColumnWidth(16, 14 * 306);
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
	
	
	public byte[] createExcelOfExportEmptyOut(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchGateOutDetails(companyId, branchId,billParty, startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "Sr.NO.", "GatePass No", "GateIn Date", "Out Date", "Time", 
		        	    "Container No", "Size", "Type", "A/c Holder", "Agent Name", 
		        	    "Shipping Line", "Booking No", "Vehicle No", "Transporter", 
		        	    "Location", "DO", "JO No", "Remark", "Rail"
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
		        reportTitleCell.setCellValue("Empty In Out Report For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr.NO.":
		                    cell.setCellValue(serialNo++); // Increment serial number
		                    break;

		                case "GatePass No":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;

		                case "GateIn Date":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        // Optionally apply a date style:
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Out Date":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        // Optionally apply a date style:
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Time":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Container No":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "Size":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Type":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "A/c Holder":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Agent Name":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;

		                case "Shipping Line":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "Booking No":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "Vehicle No":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "Transporter":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "Location":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "DO":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "JO No":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Remark":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "Rail":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		            
		            }
		        }
		        
		       
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[5] != null ? i[5].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 14 * 306); 
		        sheet.setColumnWidth(3, 9 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306);
		        sheet.setColumnWidth(6, 14 * 306);
		        sheet.setColumnWidth(7, 14 * 306);
		        sheet.setColumnWidth(8, 27 * 306);
		        sheet.setColumnWidth(9, 27 * 306);
		        sheet.setColumnWidth(10, 27 * 306);
		        sheet.setColumnWidth(11, 14 * 306);
		        sheet.setColumnWidth(12, 14 * 306);
		        sheet.setColumnWidth(13, 14 * 306);
		        sheet.setColumnWidth(14, 14 * 306);
		        sheet.setColumnWidth(15, 14 * 306);
		        sheet.setColumnWidth(16, 14 * 306);
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
	
	public byte[] createExcelOfStuffingDetails(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchStuffTallyDetails(companyId, branchId,billParty, startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "Sr.NO.", "CARTING DATE", "SB No", "SB Date", "Exporter Name", "CHA Name", 
		        	    "A/C HOLDER", "Consignee Name", "Description", "Total Pkgs", "STUFF PKGS", 
		        	    "Gross Weight", "FOB VALUE", "POD", "CARGO TYPE", "USED L/F/C", "LOCATION", 
		        	    "VEHICLE NO.", "EMPTY IN Date", "TRANSPORTER MTY CONT", "STFD DATE", "CONT NO", 
		        	    "CONT SIZE", "CONT TYPE", "LINE", "A/SEAL", "C/SEAL", "VSL / VOY", "REMARK", 
		        	    "CfsCode", "Movement Type"
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
		        reportTitleCell.setCellValue("Stuffing Details Report For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;

		                case "CARTING DATE":
		                    if (resultData1[0] != null) {
		                        cell.setCellValue(resultData1[0].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "SB No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;

		                case "SB Date":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Exporter Name":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "CHA Name":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;

		                case "A/C HOLDER":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;

		                case "Consignee Name":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;

		                case "Description":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "Total Pkgs":
		                    double totalPkgs = 0.0;
		                    if (resultData1[8] != null) {
		                        try {
		                            totalPkgs = Double.parseDouble(resultData1[8].toString());
		                        } catch (NumberFormatException e) {
		                            totalPkgs = 0.0;
		                        }
		                    }
		                    cell.setCellValue(totalPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "STUFF PKGS":
		                    double stuffPkgs = 0.0;
		                    if (resultData1[9] != null) {
		                        try {
		                            stuffPkgs = Double.parseDouble(resultData1[9].toString());
		                        } catch (NumberFormatException e) {
		                            stuffPkgs = 0.0;
		                        }
		                    }
		                    cell.setCellValue(stuffPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "Gross Weight":
		                    double grossWeight = 0.0;
		                    if (resultData1[10] != null) {
		                        try {
		                            grossWeight = Double.parseDouble(resultData1[10].toString());
		                        } catch (NumberFormatException e) {
		                            grossWeight = 0.0;
		                        }
		                    }
		                    cell.setCellValue(grossWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "FOB VALUE":
		                    double fobValue = 0.0;
		                    if (resultData1[11] != null) {
		                        try {
		                            fobValue = Double.parseDouble(resultData1[11].toString());
		                        } catch (NumberFormatException e) {
		                            fobValue = 0.0;
		                        }
		                    }
		                    cell.setCellValue(fobValue);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "POD":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "CARGO TYPE":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "USED L/F/C":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "LOCATION":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "VEHICLE NO.":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		                case "EMPTY IN Date":
		                    if (resultData1[17] != null) {
		                        cell.setCellValue(resultData1[17].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "TRANSPORTER MTY CONT":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;

		                case "STFD DATE":
		                    if (resultData1[19] != null) {
		                        cell.setCellValue(resultData1[19].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "CONT NO":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;

		                case "CONT SIZE":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;

		                case "CONT TYPE":
		                    cell.setCellValue(resultData1[22] != null ? resultData1[22].toString() : "");
		                    break;

		                case "LINE":
		                    cell.setCellValue(resultData1[23] != null ? resultData1[23].toString() : "");
		                    break;

		                case "A/SEAL":
		                    cell.setCellValue(resultData1[24] != null ? resultData1[24].toString() : "");
		                    break;

		                case "C/SEAL":
		                    cell.setCellValue(resultData1[25] != null ? resultData1[25].toString() : "");
		                    break;

		                case "VSL / VOY":
		                    cell.setCellValue(resultData1[26] != null ? resultData1[26].toString() : "");
		                    break;

		                case "REMARK":
		                    cell.setCellValue(resultData1[27] != null ? resultData1[27].toString() : "");
		                    break;

		                case "CfsCode":
		                    cell.setCellValue(resultData1[28] != null ? resultData1[28].toString() : "");
		                    break;

		                case "Movement Type":
		                    cell.setCellValue(resultData1[29] != null ? resultData1[29].toString() : "");
		                    break;

		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		            
		            }
		        }
		        
		       
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[21] != null ? i[21].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }
		        
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 14 * 306); 
		        sheet.setColumnWidth(3, 14 * 306); 
		        
		        sheet.setColumnWidth(4, 27 * 306); 
		        sheet.setColumnWidth(5, 27 * 306);
		        sheet.setColumnWidth(6, 27 * 306);
		        sheet.setColumnWidth(7, 27 * 306);
		        sheet.setColumnWidth(8, 27 * 306);
		        
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(13, 14 * 306); 
		        sheet.setColumnWidth(14, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        sheet.setColumnWidth(20, 14 * 306); 
		        sheet.setColumnWidth(21, 14 * 306);
		        sheet.setColumnWidth(22, 14 * 306); 
		        sheet.setColumnWidth(23, 14 * 306); 
		        sheet.setColumnWidth(24, 27 * 306); 
		        sheet.setColumnWidth(25, 14 * 306); 
		        sheet.setColumnWidth(26, 14 * 306); 
		        sheet.setColumnWidth(27, 14 * 306); 
		        sheet.setColumnWidth(28, 14 * 306); 
		        sheet.setColumnWidth(29, 14 * 306); 
		        sheet.setColumnWidth(30, 14 * 306); 
		        sheet.setColumnWidth(31, 14 * 306); 
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
	
	public byte[] createExcelOfDailyCartingDetails(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchCartingDetails(companyId, branchId,billParty, startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "Sr.NO.", "A/C HOLDER", "CARTING DATE", "CARTING TIME", "SB NO", 
		        	    "SB DATE", "EXPORTER", "CHA", "Consignee Name", "CARGO DESCRIPTION", 
		        	    "Gross Weight", "Total Pkgs", "REC.PKGS", "FOB VALUE", "POD", 
		        	    "CARGO TYPE", "USED L/F/C", "STATUS GEN/RES", "LOCATION", 
		        	    "AREA (sq.mt)", "VEHICLE NO.", "REMARKS", "CfsCode"
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
		        reportTitleCell.setCellValue("Carting Details Report For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "Sr.NO.":
		                    cell.setCellValue(serialNo++); // Automatically generate serial number
		                    break;
		                    
		                case "A/C HOLDER":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "CARTING DATE":
		                    if (resultData1[1] != null) {
		                        cell.setCellValue(resultData1[1].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "CARTING TIME":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;
		                    
		                case "SB NO":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "SB DATE":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "EXPORTER":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "CHA":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "Consignee Name":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "CARGO DESCRIPTION":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "Gross Weight":
		                    double grossWeight = 0.0;
		                    if (resultData1[9] != null) {
		                        try {
		                            grossWeight = Double.parseDouble(resultData1[9].toString());
		                        } catch (NumberFormatException e) {
		                            grossWeight = 0.0;
		                        }
		                    }
		                    cell.setCellValue(grossWeight);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "Total Pkgs":
		                    double totalPkgs = 0.0;
		                    if (resultData1[10] != null) {
		                        try {
		                            totalPkgs = Double.parseDouble(resultData1[10].toString());
		                        } catch (NumberFormatException e) {
		                            totalPkgs = 0.0;
		                        }
		                    }
		                    cell.setCellValue(totalPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "REC.PKGS":
		                    double recPkgs = 0.0;
		                    if (resultData1[11] != null) {
		                        try {
		                            recPkgs = Double.parseDouble(resultData1[11].toString());
		                        } catch (NumberFormatException e) {
		                            recPkgs = 0.0;
		                        }
		                    }
		                    cell.setCellValue(recPkgs);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "FOB VALUE":
		                    double fobValue = 0.0;
		                    if (resultData1[12] != null) {
		                        try {
		                            fobValue = Double.parseDouble(resultData1[12].toString());
		                        } catch (NumberFormatException e) {
		                            fobValue = 0.0;
		                        }
		                    }
		                    cell.setCellValue(fobValue);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "POD":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "CARGO TYPE":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                case "USED L/F/C":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;
		                    
		                case "STATUS GEN/RES":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;
		                    
		                case "LOCATION":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;
		                    
		                case "AREA (sq.mt)":
		                    double area = 0.0;
		                    if (resultData1[18] != null) {
		                        try {
		                            area = Double.parseDouble(resultData1[18].toString());
		                        } catch (NumberFormatException e) {
		                            area = 0.0;
		                        }
		                    }
		                    cell.setCellValue(area);
		                    cell.setCellStyle(numberCellStyle);
		                    break;
		                    
		                case "VEHICLE NO.":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;
		                    
		                case "REMARKS":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;
		                    
		                case "CfsCode":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		            
		            }
		        }
		        
		       
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 45 * 306); 
		        sheet.setColumnWidth(2, 14 * 306); 
		        sheet.setColumnWidth(3, 9 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306);
		        sheet.setColumnWidth(6, 27 * 306); 
		        sheet.setColumnWidth(7, 27 * 306); 
		        sheet.setColumnWidth(8, 27 * 306); 
		        sheet.setColumnWidth(9, 27 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(13, 14 * 306); 
		        sheet.setColumnWidth(14, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
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
	
	public byte[] createExcelOfLoadedOut(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchGateLaodedOutDetails(companyId, branchId,billParty, startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "SR.NO.", "CONT NO", "CONT SIZE", "CONT TYPE", "VESSLE", "Gate In Date",
		        	    "Stuffing Date", "OUT_DATE", "OUT TIME", "WT", "A/SEAL", "C/SEAL",
		        	    "PARTY", "LINE", "TRANSPORT", "Vehical no", "POL", "Cfscode"
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
		        reportTitleCell.setCellValue("Loaded Out Report For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
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

		                case "VESSLE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;

		                case "Gate In Date":
		                    if (resultData1[4] != null) {
		                        cell.setCellValue(resultData1[4].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "Stuffing Date":
		                    if (resultData1[5] != null) {
		                        cell.setCellValue(resultData1[5].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "OUT_DATE":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        // If OUT_DATE is a date, apply dateCellStyle
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "OUT TIME":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;

		                case "WT":
		                    double wt = 0.0;
		                    if (resultData1[8] != null) {
		                        try {
		                            wt = Double.parseDouble(resultData1[8].toString());
		                        } catch (NumberFormatException e) {
		                            wt = 0.0;
		                        }
		                    }
		                    cell.setCellValue(wt);
		                    cell.setCellStyle(numberCellStyle);
		                    break;

		                case "A/SEAL":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;

		                case "C/SEAL":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;

		                case "PARTY":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;

		                case "LINE":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;

		                case "TRANSPORT":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;

		                case "Vehical no":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;

		                case "POL":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;

		                case "Cfscode":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;

		              

		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		            
		            }
		        }
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[1] != null ? i[1].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }
		       
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2,14 * 306); 
		        sheet.setColumnWidth(3, 14 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306);
		        sheet.setColumnWidth(6, 18 * 306); 
		        
		        sheet.setColumnWidth(7, 18 * 306); 
		        sheet.setColumnWidth(8, 9 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 36 * 306); 
		        sheet.setColumnWidth(13, 36 * 306); 
		        sheet.setColumnWidth(14, 36 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
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
	
	public byte[] createExcelOfLoadedBalance(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String acc,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchLoadedBalance(companyId, branchId, acc, endDate);
		        
		        
		      
		        
		         

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
		        	    "S.NO.", "CONT NO", "CONT SIZE", "CONT TYPE", "Cargo Type", 
		        	    "AGENT NAME", "LINE", "Gate In Date", "Stuffing Date", 
		        	    "Agent Seal", "Custom Seal", "Vessel", "Port", "POD", 
		        	    "REMARK", "Movement Type"
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
		        reportTitleCell.setCellValue("Loade Balance Report For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "S.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
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
		                    
		                case "Cargo Type":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "AGENT NAME":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "LINE":
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
		                    
		                case "Stuffing Date":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "Agent Seal":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "Custom Seal":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "Vessel":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "Port":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                case "POD":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                    
		                case "REMARK":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "Movement Type":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		            
		            }
		        }
		        
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[1] != null ? i[1].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }
		       
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 14 * 306); 
		        sheet.setColumnWidth(3, 14 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setColumnWidth(6, 36 * 306);
		        sheet.setColumnWidth(7, 18 * 306); 
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
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
	
	public byte[] createExcelOfCargoBalance(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String acc,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.exportCargoBalanceReport(companyId, branchId, acc, endDate);
		        
		        
		      
		        
		         

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
		        	    "SR.NO.", "CARTING DATE", "SB No", "SB Date", "Exporter Name",
		        	    "CHA Name", "A/C HOLDER", "Consignee Name", "Description",
		        	    "Total Pkgs", "REC.PKGS", "Gross Weight", "FOB VALUE", "POD",
		        	    "CARGO TYPE", "USED L/F/C", "STATUS GEN/RES", "LOCATION",
		        	    "AREA (sq.mt)", "VEHICLE NO."
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
		        reportTitleCell.setCellValue("Cargo Balance Report For : "+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "CARTING DATE":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "SB No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "SB Date":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "Exporter Name":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "CHA Name":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "A/C HOLDER":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "Consignee Name":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "Description":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "Total Pkgs":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "REC.PKGS":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "Gross Weight":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "FOB VALUE":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                case "POD":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                    
		                case "CARGO TYPE":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "USED L/F/C":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                case "STATUS GEN/RES":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;
		                    
		                case "LOCATION":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;
		                    
		                case "AREA (sq.mt)":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;
		                    
		                case "VEHICLE NO.":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		        
		       
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 36 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setColumnWidth(6, 36 * 306); 
		        
		        sheet.setColumnWidth(7, 27 * 306); 
		        sheet.setColumnWidth(8, 27 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
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
	
	
	public byte[] createExcelOfBackToTown(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String acc,
	        String accountHolderName
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
		        

		        
		        List<Object[]> importDetails = dmrRepository.fetchBackToTownDetails(companyId, branchId,acc, startDate, endDate);

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
    "SR.NO.", "Back To Town/Escorting", "SB No", "SB Date", "BTT/Escorting Date", 
    "Agent", "CHA", "Exporter", "Consignee", "Cargo Description", 
    "BTT Qty", "Vehicle No", "Remarks"
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
		        reportTitleCell.setCellValue("Crago Back To Twon For : "+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "Back To Town/Escorting":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "SB No":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "SB Date":
		                    if (resultData1[2] != null) {
		                        cell.setCellValue(resultData1[2].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "BTT/Escorting Date":
		                    if (resultData1[3] != null) {
		                        cell.setCellValue(resultData1[3].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "Agent":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "CHA":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "Exporter":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "Consignee":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "Cargo Description":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "BTT Qty":
		                    double qty = 0.0;
		                    if (resultData1[9] != null) {
		                        try {
		                            qty = Double.parseDouble(resultData1[9].toString());
		                        } catch (NumberFormatException e) {
		                            qty = 0.0;
		                        }
		                    }
		                    cell.setCellValue(qty);
		                    break;
		                    
		                case "Vehicle No":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "Remarks":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		        
		       
		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 18 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 36 * 306);
		        sheet.setColumnWidth(6, 36 * 306); 
		        
		        sheet.setColumnWidth(7, 27 * 306); 
		        sheet.setColumnWidth(8, 27 * 306); 
		        sheet.setColumnWidth(9, 18 * 306); 
		        sheet.setColumnWidth(10, 18 * 306); 
		        sheet.setColumnWidth(11, 18 * 306); 
		        sheet.setColumnWidth(12, 18 * 306); 
		        sheet.setColumnWidth(13, 18 * 306); 
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
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
	
	public byte[] createExcelOfPortShutOutIn(Sheet sheet, String companyId,
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
		        	    "SR.NO.", "CONT NO", "CONT SIZE", "CONT TYPE", "Cargo Type", 
		        	    "Agent", "Line", "Loaded Out Date", "Shut Out IN Date & Time", 
		        	    "Custom Seal", "Agent Seal", "Vessel", "Via No", "Voyage", 
		        	    "Port", "POD", "Transporter", "Vehicle No", "Remarks"
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
		        reportTitleCell.setCellValue("Port In Out For :" );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
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
		                    
		                case "Cargo Type":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "Agent":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "Line":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "Loaded Out Date":
		                    if (resultData1[6] != null) {
		                        cell.setCellValue(resultData1[6].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "Shut Out IN Date & Time":
		                    if (resultData1[7] != null) {
		                        cell.setCellValue(resultData1[7].toString());
		                        cell.setCellStyle(dateCellStyle);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		                    
		                case "Custom Seal":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "Agent Seal":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "Vessel":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "Via No":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                case "Voyage":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                    
		                case "Port":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "POD":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                case "Transporter":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;
		                    
		                case "Vehicle No":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;
		                    
		                case "Remarks":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
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
	
	
	public byte[] createExcelOfPortPendency(Sheet sheet, String companyId,
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
    "SR NO", "JOB PLACE DATE", "PORT", "BERTHING", "VSL Name", 
    "VIA NO.", "SHIPPING LINE", "JOB ORDER PLACED", "PENDENCY 24Hrs", 
    "CONTAINER NO", "Container size", "berthing place", "berthing placing time updated"
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
		        reportTitleCell.setCellValue("Port Pendency For :" );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR NO":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "JOB PLACE DATE":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "PORT":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "BERTHING":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;
		                    
		                case "VSL Name":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "VIA NO.":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "SHIPPING LINE":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "JOB ORDER PLACED":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "PENDENCY 24Hrs":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "CONTAINER NO":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "Container size":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "berthing place":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "berthing placing time updated":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
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
	
	
	public byte[] createExcelOfInMovement(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.getImportInMovementReport(companyId, branchId,billParty,startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "SR.NO.", "GATE-IN DATE", "GATE-IN TIME", "CONTAINER NO", "SIZE", 
		        	    "TYPE", "BL NO", "IGM NO", "ITEM NO", "SUBITEM NO", "CONTAINER TYPE", 
		        	    "SCAN TYPE", "SCAN STATUS", "A/C HOLDER", "LINE AGENT", "LINE SEAL", 
		        	    "VESSEL", "VIA NO", "PORT", "REMARKS", "DPD / NON DPD"
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
		        reportTitleCell.setCellValue("In Movement For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "GATE-IN DATE":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "GATE-IN TIME":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "CONTAINER NO":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;
		                    
		                case "SIZE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "TYPE":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "BL NO":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "IGM NO":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "ITEM NO":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "SUBITEM NO":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "CONTAINER TYPE":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "SCAN TYPE":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "SCAN STATUS":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                case "A/C HOLDER":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                    
		                case "LINE AGENT":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "LINE SEAL":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                case "VESSEL":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;
		                    
		                case "VIA NO":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;
		                    
		                case "PORT":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;
		                    
		                case "REMARKS":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;
		                    
		                case "DPD / NON DPD":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		        
		       
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[3] != null ? i[3].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(15, 14 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
		        sheet.setColumnWidth(14, 36 * 306); 
		        sheet.setColumnWidth(13, 36 * 306);
		        
		        sheet.setColumnWidth(17, 14 * 306); 
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
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
	
	public byte[] createExcelOfYardBalance(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchYardBalanceDetails(companyId, branchId, billParty, endDate);
		        
		        
		      
		        
		         

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
		        	    "SR.NO.", "IN DATE", "TIME", "CONTAINER NO", "SIZE", 
		        	    "TYPE", "SEAL", "CONT STATUS", "SCAN", "VESSEL", 
		        	    "VIA", "LINE", "A/C HOLDER", "ORIGIN", "STATUS", "REMARK", 
		        	    "DPD / NON DPD", "Cargo Type"
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
		        reportTitleCell.setCellValue("Yard Balance For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "IN DATE":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "TIME":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "CONTAINER NO":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;
		                    
		                case "SIZE":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "TYPE":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "SEAL":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "CONT STATUS":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "SCAN":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "VESSEL":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "VIA":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "LINE":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "A/C HOLDER":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                case "ORIGIN":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                    
		                case "STATUS":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "REMARK":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                case "DPD / NON DPD":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;
		                    
		                case "Cargo Type":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		        
		       
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[3] != null ? i[3].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 9 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 14 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(7, 14 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 36 * 306); 
		        sheet.setColumnWidth(12, 36 * 306); 
		        sheet.setColumnWidth(13, 36 * 306); 
		        sheet.setColumnWidth(16, 14 * 306); 
		        sheet.setColumnWidth(14, 14 * 306); 
		        sheet.setColumnWidth(13, 14 * 306);
		        
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
	
		public byte[] createExcelOfImportLoadedOut(Sheet sheet, String companyId,
		    String branchId, String username,String companyname,
		    String branchname,
		    Date startDate,
	        Date endDate,
	        String billParty,
	        String accountHolderName
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

		        
		        List<Object[]> importDetails = dmrRepository.fetchImportGateOutDetails(companyId, branchId,billParty, startDate, endDate);
		        
		        
		      
		        
		         

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
		        	    "SR.NO.", "GATE-IN DATE", "GATE-OUT DATE", "GATE-OUT TIME", "CONTAINER NO", 
		        	    "SIZE", "TYPE", "A/C HOLDER", "LINE AGENT", "BOE NO", 
		        	    "BOE DATE", "BL NO.", "IGM NO", "ITEM NO", "CHA", 
		        	    "IMPORTER", "DESCRIPTION", "REMARKS", "DPD / NON DPD", "VESSEL", 
		        	    "VIA NO", "TRUCKNO", "TRANSPORTER"
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
		        reportTitleCell.setCellValue("Import Loaded Out For :"+accountHolderName );

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
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		                case "SR.NO.":
		                    cell.setCellValue(serialNo++); // Generate serial number
		                    break;
		                    
		                case "GATE-IN DATE":
		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
		                    break;
		                    
		                case "GATE-OUT DATE":
		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
		                    break;
		                    
		                case "GATE-OUT TIME":
		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
		                    break;
		                    
		                case "CONTAINER NO":
		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
		                    break;
		                    
		                case "SIZE":
		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
		                    break;
		                    
		                case "TYPE":
		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
		                    break;
		                    
		                case "A/C HOLDER":
		                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
		                    break;
		                    
		                case "LINE AGENT":
		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
		                    break;
		                    
		                case "BOE NO":
		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
		                    break;
		                    
		                case "BOE DATE":
		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
		                    break;
		                    
		                case "BL NO.":
		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
		                    break;
		                    
		                case "IGM NO":
		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
		                    break;
		                    
		                case "ITEM NO":
		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
		                    break;
		                    
		                case "CHA":
		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
		                    break;
		                    
		                case "IMPORTER":
		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
		                    break;
		                    
		                case "DESCRIPTION":
		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
		                    break;
		                    
		                case "REMARKS":
		                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
		                    break;
		                    
		                case "DPD / NON DPD":
		                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
		                    break;
		                    
		                case "VESSEL":
		                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
		                    break;
		                    
		                case "VIA NO":
		                    cell.setCellValue(resultData1[19] != null ? resultData1[19].toString() : "");
		                    break;
		                    
		                case "TRUCKNO":
		                    cell.setCellValue(resultData1[20] != null ? resultData1[20].toString() : "");
		                    break;
		                    
		                case "TRANSPORTER":
		                    cell.setCellValue(resultData1[21] != null ? resultData1[21].toString() : "");
		                    break;
		                    
		                default:
		                    cell.setCellValue("");
		                    break;
		            }
		                
		            }
		        }
		        
		       
		        
		        Row emptyRow = sheet.createRow(rowNum++);
		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//		        
//
		        Row empty = sheet.createRow(rowNum++);
		        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//		        		
		        Row emptyRow1 = sheet.createRow(rowNum++);
//		        
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		        String[] columnsHeaderSummery = {
		            "Sr No", "20", "40","TEUS"
		        };

		        
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


		        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
		        totalRowStyle.cloneStyleFrom(numberCellStyle);
		        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
		        Font boldFont1 = workbook.createFont();
		        boldFont1.setBold(true);
		        totalRowStyle.setFont(boldFont1);

		        
		     // Create a map to store total counts
		        Map<String, Integer> containerCounts = new HashMap<>();
		        containerCounts.put("20", 0);
		        containerCounts.put("40", 0);

		        importDetails.forEach(i -> {
		            String size = i[4] != null ? i[4].toString() : "Unknown"; // Extract size

		            if (size.equals("20") || size.equals("22")) {
		                containerCounts.put("20", containerCounts.get("20") + 1);
		            } else if (size.equals("40") || size.equals("45")) {
		                containerCounts.put("40", containerCounts.get("40") + 1);
		            }
		        });

		        // Calculate TEUS
		        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

		        // Create a new row for summary totals
		        Row summaryRow = sheet.createRow(rowNum++);
		        summaryRow.createCell(0).setCellValue("Total");
		        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
		        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
		        summaryRow.createCell(3).setCellValue(teus); // TEUS count

 
		        // Apply styles for the total row
		        for (int i = 0; i <= 3; i++) { 
		            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		            cell.setCellStyle(totalRowStyle);
		        }

		        
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 14 * 306); 
		        sheet.setColumnWidth(3, 18 * 306); 
		        
		        sheet.setColumnWidth(4, 9 * 306); 
		        sheet.setColumnWidth(5, 9 * 306); 
		        sheet.setColumnWidth(6, 27 * 306); 
		        sheet.setColumnWidth(7, 27 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        sheet.setColumnWidth(12, 14 * 306); 
		        sheet.setColumnWidth(15, 36 * 306); 
		        sheet.setColumnWidth(16, 36 * 306); 
		        sheet.setColumnWidth(14, 36 * 306); 
		        sheet.setColumnWidth(13, 14 * 306);
		        
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
		
				public byte[] createExcelOfImportDestuff(Sheet sheet, String companyId,
			    String branchId, String username,String companyname,
			    String branchname,
			    Date startDate,
		        Date endDate,
		        String billParty,
		        String accountHolderName
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

			        
			        List<Object[]> importDetails = dmrRepository.fetchDeStuffDetails(companyId, branchId,billParty, startDate, endDate);
			        
			        
			      
			        
			         

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
			        	    "SR.NO.", "CONTAINER NO", "SIZE", "TYPE", "GATEIN DATE", 
			        	    "DESTUFF DATE", "DESTUFF TIME", "VESSEL", "IGM NO", "ITEM", 
			        	    "CHA", "IMPORTER NAME", "AGENT", "GATE PASS DATE", "B/E", 
			        	    "BL NO.", "REMARK", "DPD / NON DPD"
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
//			        branchCell1.setCellValue(branchAdd);
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
			        reportTitleCell.setCellValue("Import Destuff For :"+accountHolderName );

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
			        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
			        }
			        else 
			        {
			        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
			                case "SR.NO.":
			                    cell.setCellValue(serialNo++); // Generate serial number
			                    break;
			                    
			                case "CONTAINER NO":
			                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
			                    break;
			                    
			                case "SIZE":
			                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
			                    break;
			                    
			                case "TYPE":
			                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
			                    break;
			                    
			                case "GATEIN DATE":
			                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
			                    break;
			                    
			                case "DESTUFF DATE":
			                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
			                    break;
			                    
			                case "DESTUFF TIME":
			                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
			                    break;
			                    
			                case "VESSEL":
			                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
			                    break;
			                    
			                case "IGM NO":
			                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
			                    break;
			                    
			                case "ITEM":
			                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
			                    break;
			                    
			                case "CHA":
			                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
			                    break;
			                    
			                case "IMPORTER NAME":
			                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
			                    break;
			                    
			                case "AGENT":
			                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
			                    break;
			                    
			                case "GATE PASS DATE":
			                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
			                    break;
			                    
			                case "B/E":
			                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
			                    break;
			                    
			                case "BL NO.":
			                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
			                    break;
			                    
			                case "REMARK":
			                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
			                    break;
			                    
			                case "DPD / NON DPD":
			                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
			                    break;
			                    
			                default:
			                    cell.setCellValue("");
			                    break;
			            }
			                
			            }
			        }
			        
			       
			        
			        Row emptyRow = sheet.createRow(rowNum++);
			        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//			        
	//
			        Row empty = sheet.createRow(rowNum++);
			        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//			        		
			        Row emptyRow1 = sheet.createRow(rowNum++);
//			        
			        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

			        String[] columnsHeaderSummery = {
			            "Sr No", "20", "40","TEUS"
			        };

			        
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


			        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
			        totalRowStyle.cloneStyleFrom(numberCellStyle);
			        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	//
			        Font boldFont1 = workbook.createFont();
			        boldFont1.setBold(true);
			        totalRowStyle.setFont(boldFont1);

			        
			     // Create a map to store total counts
			        Map<String, Integer> containerCounts = new HashMap<>();
			        containerCounts.put("20", 0);
			        containerCounts.put("40", 0);

			        importDetails.forEach(i -> {
			            String size = i[1] != null ? i[1].toString() : "Unknown"; // Extract size

			            if (size.equals("20") || size.equals("22")) {
			                containerCounts.put("20", containerCounts.get("20") + 1);
			            } else if (size.equals("40") || size.equals("45")) {
			                containerCounts.put("40", containerCounts.get("40") + 1);
			            }
			        });

			        // Calculate TEUS
			        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

			        // Create a new row for summary totals
			        Row summaryRow = sheet.createRow(rowNum++);
			        summaryRow.createCell(0).setCellValue("Total");
			        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
			        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
			        summaryRow.createCell(3).setCellValue(teus); // TEUS count

	 
			        // Apply styles for the total row
			        for (int i = 0; i <= 3; i++) { 
			            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			            cell.setCellStyle(totalRowStyle);
			        }

			        
			        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
			        sheet.setColumnWidth(0,  9 * 306); 
			        sheet.setColumnWidth(1, 18 * 306); 
			        sheet.setColumnWidth(2, 9 * 306); 
			        sheet.setColumnWidth(3, 9 * 306); 
			        
			        sheet.setColumnWidth(4, 18 * 306); 
			        sheet.setColumnWidth(5, 14 * 306); 
			        sheet.setColumnWidth(6, 14 * 306); 
			        sheet.setColumnWidth(7, 14 * 306); 
			        sheet.setColumnWidth(8, 14 * 306); 
			        sheet.setColumnWidth(9, 14 * 306); 
			        sheet.setColumnWidth(10, 27 * 306); 
			        sheet.setColumnWidth(11, 27 * 306); 
			        sheet.setColumnWidth(12, 27 * 306); 
			        sheet.setColumnWidth(15, 14 * 306); 
			        sheet.setColumnWidth(16, 14 * 306); 
			        sheet.setColumnWidth(14, 14 * 306); 
			        sheet.setColumnWidth(13, 14 * 306);
			        
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
				
				
				public byte[] createExcelOfImportEmptyBalance(Sheet sheet, String companyId,
					    String branchId, String username,String companyname,
					    String branchname,
					    Date startDate,
				        Date endDate,
				        String billParty,
				        String accountHolderName
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

					        
					        List<Object[]> importDetails = dmrRepository.importEmptyBalanceReport(companyId, branchId, billParty, endDate);
					        
					        
					      
					        
					         

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
					        	    "SR.NO.", "Container No", "Size", "Type", "Vessel", 
					        	    "DSTF.DATE", "Remark", "DPD / Non DPD"
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
//					        branchCell1.setCellValue(branchAdd);
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
					        reportTitleCell.setCellValue("Empty Balance For :"+accountHolderName );

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
					        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
					        }
					        else 
					        {
					        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
					                case "SR.NO.":
					                    cell.setCellValue(serialNo++); // Generate serial number
					                    break;
					                    
					                case "Container No":
					                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
					                    break;
					                    
					                case "Size":
					                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
					                    break;
					                    
					                case "Type":
					                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
					                    break;
					                    
					                case "Vessel":
					                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
					                    break;
					                    
					                case "DSTF.DATE":
					                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
					                    break;
					                    
					                case "Remark":
					                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
					                    break;
					                    
					                case "DPD / Non DPD":
					                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
					                    break;
					                    
					                default:
					                    cell.setCellValue("");
					                    break;
					            }
					                
					            }
					        }
					        
					       
					        
					        Row emptyRow = sheet.createRow(rowNum++);
					        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//					        
			//
					        Row empty = sheet.createRow(rowNum++);
					        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//					        		
					        Row emptyRow1 = sheet.createRow(rowNum++);
//					        
					        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

					        String[] columnsHeaderSummery = {
					            "Sr No", "20", "40","TEUS"
					        };

					        
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


					        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
					        totalRowStyle.cloneStyleFrom(numberCellStyle);
					        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
					        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			//
					        Font boldFont1 = workbook.createFont();
					        boldFont1.setBold(true);
					        totalRowStyle.setFont(boldFont1);

					        
					     // Create a map to store total counts
					        Map<String, Integer> containerCounts = new HashMap<>();
					        containerCounts.put("20", 0);
					        containerCounts.put("40", 0);

					        importDetails.forEach(i -> {
					            String size = i[1] != null ? i[1].toString() : "Unknown"; // Extract size

					            if (size.equals("20") || size.equals("22")) {
					                containerCounts.put("20", containerCounts.get("20") + 1);
					            } else if (size.equals("40") || size.equals("45")) {
					                containerCounts.put("40", containerCounts.get("40") + 1);
					            }
					        });

					        // Calculate TEUS
					        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

					        // Create a new row for summary totals
					        Row summaryRow = sheet.createRow(rowNum++);
					        summaryRow.createCell(0).setCellValue("Total");
					        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
					        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
					        summaryRow.createCell(3).setCellValue(teus); // TEUS count

			 
					        // Apply styles for the total row
					        for (int i = 0; i <= 3; i++) { 
					            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					            cell.setCellStyle(totalRowStyle);
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
					        sheet.setColumnWidth(8, 18 * 306); 
					        sheet.setColumnWidth(9, 18 * 306); 
					        sheet.setColumnWidth(10, 18 * 306); 
					        sheet.setColumnWidth(11, 18 * 306); 
					        sheet.setColumnWidth(12, 18 * 306); 
					        sheet.setColumnWidth(15, 14 * 306); 
					        sheet.setColumnWidth(16, 14 * 306); 
					        sheet.setColumnWidth(14, 36 * 306); 
					        sheet.setColumnWidth(13, 36 * 306);
					        
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
				
				public byte[] createExcelOfImportEmptyOut(Sheet sheet, String companyId,
					    String branchId, String username,String companyname,
					    String branchname,
					    Date startDate,
				        Date endDate,
				        String billParty,
				        String accountHolderName
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

					        
					        List<Object[]> importDetails = dmrRepository.fetchImportEmptyOutDetails(companyId, branchId,billParty,startDate, endDate);
					        
					        
					      
					        
					         

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
					        	    "SR.NO.", "GatePass No", "GateIn Date", "Out Date", "Time", 
					        	    "Container No", "Size", "Type", "A/c Holder", "Line Agent", 
					        	    "Status", "Booking No", "Vehicle No", "Transporter", "Location", 
					        	    "Vessel", "VIA", "DO/JO No", "Remark", "DPD / Non DPD"
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
//					        branchCell1.setCellValue(branchAdd);
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
					        reportTitleCell.setCellValue("Import Empty Out For :"+accountHolderName );

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
					        	 reportTitleCell1.setCellValue("Daily Actvity Report for As On Date : " + formattedEndDate);
					        }
					        else 
					        {
					        	 reportTitleCell1.setCellValue("Daily Actvity Report for Report From : " + formattedStartDate + " to " + formattedEndDate);
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
					                case "SR.NO.":
					                    cell.setCellValue(serialNo++); // Generate serial number
					                    break;
					                    
					                case "GatePass No":
					                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
					                    break;
					                    
					                case "GateIn Date":
					                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
					                    break;
					                    
					                case "Out Date":
					                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
					                    break;
					                    
					                case "Time":
					                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
					                    break;
					                    
					                case "Container No":
					                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
					                    break;
					                    
					                case "Size":
					                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
					                    break;
					                    
					                case "Type":
					                    cell.setCellValue(resultData1[6] != null ? resultData1[6].toString() : "");
					                    break;
					                    
					                case "A/c Holder":
					                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
					                    break;
					                    
					                case "Line Agent":
					                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
					                    break;
					                    
					                case "Status":
					                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
					                    break;
					                    
					                case "Booking No":
					                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
					                    break;
					                    
					                case "Vehicle No":
					                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
					                    break;
					                    
					                case "Transporter":
					                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
					                    break;
					                    
					                case "Location":
					                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
					                    break;
					                    
					                case "Vessel":
					                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
					                    break;
					                    
					                case "VIA":
					                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
					                    break;
					                    
					                case "DO/JO No":
					                    cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
					                    break;
					                    
					                case "Remark":
					                    cell.setCellValue(resultData1[17] != null ? resultData1[17].toString() : "");
					                    break;
					                    
					                case "DPD / Non DPD":
					                    cell.setCellValue(resultData1[18] != null ? resultData1[18].toString() : "");
					                    break;
					                    
					                default:
					                    cell.setCellValue("");
					                    break;
					            }
					                
					            }
					        }
					        
					       
					        
					        Row emptyRow = sheet.createRow(rowNum++);
					        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
//					        
			//
					        Row empty = sheet.createRow(rowNum++);
					        empty.createCell(0).setCellValue("Summary Total"); // You can set a value or keep it blank	
//					        		
					        Row emptyRow1 = sheet.createRow(rowNum++);
//					        
					        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

					        String[] columnsHeaderSummery = {
					            "Sr No", "20", "40","TEUS"
					        };

					        
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


					        CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
					        totalRowStyle.cloneStyleFrom(numberCellStyle);
					        totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
					        totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			//
					        Font boldFont1 = workbook.createFont();
					        boldFont1.setBold(true);
					        totalRowStyle.setFont(boldFont1);

					        
					     // Create a map to store total counts
					        Map<String, Integer> containerCounts = new HashMap<>();
					        containerCounts.put("20", 0);
					        containerCounts.put("40", 0);

					        importDetails.forEach(i -> {
					            String size = i[5] != null ? i[5].toString() : "Unknown"; // Extract size

					            if (size.equals("20") || size.equals("22")) {
					                containerCounts.put("20", containerCounts.get("20") + 1);
					            } else if (size.equals("40") || size.equals("45")) {
					                containerCounts.put("40", containerCounts.get("40") + 1);
					            }
					        });

					        // Calculate TEUS
					        int teus = containerCounts.get("20") + (containerCounts.get("40") * 2);

					        // Create a new row for summary totals
					        Row summaryRow = sheet.createRow(rowNum++);
					        summaryRow.createCell(0).setCellValue("Total");
					        summaryRow.createCell(1).setCellValue(containerCounts.get("20")); // 20 Feet count
					        summaryRow.createCell(2).setCellValue(containerCounts.get("40")); // 40 Feet count
					        summaryRow.createCell(3).setCellValue(teus); // TEUS count

			 
					        // Apply styles for the total row
					        for (int i = 0; i <= 3; i++) { 
					            Cell cell = summaryRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					            cell.setCellStyle(totalRowStyle);
					        }

					        
					        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
					        sheet.setColumnWidth(0,  9 * 306); 
					        sheet.setColumnWidth(1, 14 * 306); 
					        sheet.setColumnWidth(2, 18 * 306); 
					        sheet.setColumnWidth(3, 18 * 306); 
					        
					        sheet.setColumnWidth(4, 14 * 306); 
					        sheet.setColumnWidth(5, 14 * 306); 
					        sheet.setColumnWidth(6, 9 * 306); 
					        sheet.setColumnWidth(7, 9 * 306); 
					        sheet.setColumnWidth(8, 27 * 306); 
					        sheet.setColumnWidth(9, 27 * 306); 
					        sheet.setColumnWidth(10, 14 * 306); 
					        sheet.setColumnWidth(11, 14 * 306); 
					        sheet.setColumnWidth(12, 14 * 306); 
					        sheet.setColumnWidth(15, 14 * 306); 
					        sheet.setColumnWidth(16, 14 * 306); 
					        sheet.setColumnWidth(14, 14 * 306); 
					        sheet.setColumnWidth(13, 27 * 306);
					        
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
