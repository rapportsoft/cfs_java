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
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CompanyRepo;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.lowagie.text.DocumentException;

@Service
public class CFSBondingReportService {

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	@Autowired
	private CfExBondCrgRepository cfExBondCrgRepository;
	
	
	@Autowired
	private CfinbondcrgRepo cfinbondcrgRepo;
	
	
	
	public ResponseEntity<List<Cfinbondcrg>> getDataOfInventoryToShow(
            String companyId,
            String branchId,
            String username,
            String type,
            String companyname,
            String branchname,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
    ) {
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

        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForInBondInventoryReport(companyId, branchId, startDate, endDate);

        if (resultData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
        }

        return new ResponseEntity<>(resultData, HttpStatus.OK); // Records found
    }
	
	
	
	
	
	
	
	
	
	public ResponseEntity<List<Cfinbondcrg>> showBondDepositRegister(
            String companyId,
            String branchId,
            String username,
            String type,
            String companyname,
            String branchname,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
            String boeNo
    ) {
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

//        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForInBondDepositeReport(companyId, branchId, startDate,endDate,boeNo);

        
        List<Cfinbondcrg> resultData=null;
        
     // Case 1: If both startDate and endDate are null, use getDataForBondDeliveryReportWithoutDates
        if (startDate == null && endDate == null) {
            resultData = cfinbondcrgRepo.getDataForInBondDepositeReportWithoutDates(companyId, branchId, boeNo);
        }
        // Case 2: 
        else {
            resultData = cfinbondcrgRepo.getDataForInBondDepositeReport(companyId, branchId, startDate, endDate, boeNo);
        }
        if (resultData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
        }

        return new ResponseEntity<>(resultData, HttpStatus.OK); // Records found
    }
	
	public byte[] createExcelReportOfExBondInventoryReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
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
		    
		        
		        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForInBondInventoryReport(companyId, branchId, startDate,endDate);

		        Sheet sheet = workbook.createSheet("Bond cargo Inventory Report ");

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

		        

				String[] columnsHeader = {"Sr No", "Noc No", "Noc Date","In Bonding Date", "Igm No", "Item No", "Inbonded Boe No",
						"Inbonded Boe Date", "Section 49", "Section 60(1)", "Inbond Pkgs", "Inbond Weight",
						"Inbond Asset Value","Inbond Duty Value","Cargo Description","Cha","Importer","Account Holder"
						,"Bal Pkgs","Bal Wt","Bal Asset Value","Bal Duty Value","Area Balance Area","Bond No","Bond Date"};
	
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
		        reportTitleCell.setCellValue("Bond cargo Inventory Report" );

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
		        	 reportTitleCell1.setCellValue("Bond Cargo Inventory Report As On Date : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Bond Cargo Inventory Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (Cfinbondcrg resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) 
		                {
		                case "Sr No":
	                        cell.setCellValue(serialNo++); // Serial Number increment
	                        break;
	                    case "Noc No":
	                        cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo() : "");
	                        break;
	                    case "Noc Date":
	                        if (resultData1.getNocDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getNocDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Inbonded Boe Date":
	                        if (resultData1.getBoeDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBoeDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "In Bonding Date":
	                        if (resultData1.getInBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getInBondingDate());
	                            cell.setCellStyle(dateCellStyle); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Bond Date":
	                        if (resultData1.getBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondingDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
	                    case "Inbonded Boe No":
	                        cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
	                        break;

	                    case "Section 49":
	                        cell.setCellValue(resultData1.getSection49() != null ? resultData1.getSection49().toString() : "");
	                        break;
	                    case "Section 60(1)":
	                        cell.setCellValue(resultData1.getSection49() != null ? resultData1.getSection49().toString() : "");
	                        break;
	                    case "Igm No":
	                        cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
	                        break;
	                    case "Item No":
	                        cell.setCellValue(resultData1.getIgmLineNo() != null ? resultData1.getIgmLineNo().toString() : "");
	                        break;	                        
	                    case "Inbond Pkgs":
	                        BigDecimal inbondPkgs = resultData1.getInBondedPackagesDtl();
	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Weight":
	                        BigDecimal inbondWeight = resultData1.getInbondGrossWtDtl();
	                        totalInbondWeight = totalInbondWeight.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Asset Value":
	                        BigDecimal inbondAssetValue = resultData1.getInbondCifValue();
	                        totalInbondAssetValue = totalInbondAssetValue.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Duty Value":
	                        BigDecimal inbondDutyValue = resultData1.getInbondCargoDuty();
	                        totalInbondDutyValue = totalInbondDutyValue.add(inbondDutyValue != null ? inbondDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondDutyValue != null ? inbondDutyValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bal Pkgs":
	                        BigDecimal balPkgs = resultData1.getInBondedPackagesDtl().subtract(resultData1.getExBondedPackagesDtl() != null ? resultData1.getExBondedPackagesDtl() : BigDecimal.ZERO);
	                        totalBalPkgs = totalBalPkgs.add(balPkgs != null ? balPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(balPkgs.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bal Wt":
	                        BigDecimal balWt = resultData1.getInbondGrossWtDtl().subtract(resultData1.getExbondGrossWtDtl() != null ? resultData1.getExbondGrossWtDtl() : BigDecimal.ZERO);
	                        totalBalWeight = totalBalWeight.add(balWt != null ? balWt : BigDecimal.ZERO);
	                        cell.setCellValue(balWt.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bal Asset Value":
	                        BigDecimal balAssetValue = resultData1.getInbondCifValue().subtract(resultData1.getExbondCifValue() != null ? resultData1.getExbondCifValue() : BigDecimal.ZERO);
	                        totalBalAssetValue = totalBalAssetValue.add(balAssetValue != null ? balAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(balAssetValue.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bal Duty Value":
	                        BigDecimal balDutyValue = resultData1.getInbondCargoDuty().subtract(resultData1.getExbondCargoDuty() != null ? resultData1.getExbondCargoDuty() : BigDecimal.ZERO);
	                        totalBalDutyValue = totalBalDutyValue.add(balDutyValue != null ? balDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(balDutyValue.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Area Balance Area":
	                        BigDecimal areaBalanceArea = resultData1.getAreaOccupied().subtract(resultData1.getAreaRelesed() != null ? resultData1.getAreaRelesed() : BigDecimal.ZERO);
	                        totalAreaBalance = totalAreaBalance.add(areaBalanceArea != null ? areaBalanceArea : BigDecimal.ZERO);
	                        cell.setCellValue(areaBalanceArea.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Cargo Description":
	                        cell.setCellValue(resultData1.getCommodityDescription() != null ? resultData1.getCommodityDescription() : "");
	                        break;
	                    case "Cha":
	                        cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
	                        break;
	                    case "Importer":
	                        cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
	                        break;
	                    case "Account Holder":
	                        cell.setCellValue(resultData1.getOnAccountOf() != null ? resultData1.getOnAccountOf() : "");
	                        break;
	                    case "Bond No":
	                        cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
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
		        sheet.setColumnWidth(4, 9 * 306); 
		        sheet.setColumnWidth(5, 9 * 306); 
		        
		        sheet.setColumnWidth(10, 14 * 306); 
		        sheet.setColumnWidth(11, 14 * 306); 
		        
		        sheet.setColumnWidth(18, 14 * 306); 
		        sheet.setColumnWidth(19, 14 * 306); 
		        
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(15, 30 * 306); 
		        sheet.setColumnWidth(16, 40 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(23, 30 * 306); // Set width for "CHA" (30 characters wide)
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
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfBondDepositRegister(
			String companyId,
			 String branchId,
	            String username,
	            String type,
	            String companyname,
	            String branchname,
	            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	            String boeNo
			) throws DocumentException {

		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

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
		        
//		        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForInBondDepositeReport(companyId, branchId, startDate,endDate,boeNo);

		      

		        

		        
		        Sheet sheet = workbook.createSheet("Bond Deposit Register ");

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
		        	    "Section 60(1)", 
		        	    "Section 49()", 
		        	    "In Bonding Date",
		        	    "Bond No", 
		        	    "Bond Date", 
		        	    "Inbond Boe", 
		        	    "Inbond Boe Date", 
		        	    "Igm No", 
		        	    "Item No", 
		        	   
		        	    "Cargo Description", 
		        	    "Importer Name", 
		        	    "Cha Name", 
		        	    "On Account Holder", 
		        	    "Marks and Nos on Pkg", 
		        	    "Inbond Pkg", 
		        	    "Inbond Weight", 
		        	    "Inbond Asset Value", 
		        	    "Inbond Duty Value", 
		        	    "Damage Remark", 
		        	    "Short Pkgs", 
		        	    "Damage Pkg",
		        	    "Brakage Pkg"
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
		        reportTitleCell.setCellValue("Bond Deposit Register" );

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
		         if(startDate ==null && endDate ==null)
		         {
		        	  reportTitleCell1.setCellValue("Bond Deposit Register Report for BOE No : " + boeNo);
		         }
		         else
		         {
		        	  reportTitleCell1.setCellValue("Bond Deposit Register Report From : " + formattedStartDate + " to " + formattedEndDate);
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
		            int headerWidth = (int) (columnsHeader[i].length() * 306 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		        BigDecimal totalInbondPkgs = BigDecimal.ZERO;
		        BigDecimal totalInbondWeight = BigDecimal.ZERO;
		        BigDecimal totalInbondAssetValue = BigDecimal.ZERO;
		        BigDecimal totalInbondDutyValue = BigDecimal.ZERO;
		        BigDecimal totalShortPkgs = BigDecimal.ZERO;
		        BigDecimal totalDamagedWeight = BigDecimal.ZERO;
		        BigDecimal totalBrakageValue = BigDecimal.ZERO;
		       
		        
  List<Cfinbondcrg> resultData=null;
		        
  System.out.println("resultData_________________"+companyId +"" +branchId +"" +boeNo);
  
  System.out.println("resultData_________________"+companyId +"" +branchId +"" +boeNo +""+startDate +""+ endDate);
  
		        // Case 1: If both startDate and endDate are null, use getDataForBondDeliveryReportWithoutDates
		           if (startDate == null && endDate == null) {
		               resultData = cfinbondcrgRepo.getDataForInBondDepositeReportWithoutDates(companyId, branchId, boeNo);
		           }
		           // Case 2: 
		           else {
		               resultData = cfinbondcrgRepo.getDataForInBondDepositeReport(companyId, branchId, startDate, endDate, boeNo);
		           }
		           
		        int serialNo = 1; // Initialize serial number counter
		        for (Cfinbondcrg resultData1 : resultData) {
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
		                case "Section 60(1)":
		                    cell.setCellValue(resultData1.getSection49() != null ? resultData1.getSection49().toString() : "");
		                    break;
		                case "Section 49()":
		                    cell.setCellValue(resultData1.getSection49() != null ? resultData1.getSection49().toString() : "");
		                    break;
		              
		                    
		                case  "In Bonding Date":
	                        if (resultData1.getInBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getInBondingDate());
	                            cell.setCellStyle(dateCellStyle); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
		                case "Inbond Boe":
		                    cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
		                    break;
		                    
		                    
		                case "Bond Date":
	                        if (resultData1.getBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondingDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;

		                case "Inbond Boe Date":
	                        if (resultData1.getBoeDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBoeDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
	             
		                case "Igm No":
		                    cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
		                    break;
		                case "Item No":
		                    cell.setCellValue(resultData1.getIgmLineNo() != null ? resultData1.getIgmLineNo().toString() : "");
		                    break;
		                case "Bond No":
		                    cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
		                    break;

		                case "Cargo Description":
		                    cell.setCellValue(resultData1.getCommodityDescription() != null ? resultData1.getCommodityDescription() : "");
		                    break;
		                case "Importer Name":
		                    cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
		                    break;
		                case "Cha Name":
		                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
		                    break;
		                case "On Account Holder":
		                    cell.setCellValue(resultData1.getOnAccountOf() != null ? resultData1.getOnAccountOf() : "");
		                    break;
		                case "Marks and Nos on Pkg":
		                    cell.setCellValue(resultData1.getNumberOfMarks() != null ? resultData1.getNumberOfMarks() : ""); // Assuming getMarksNos()
		                    break;
		                    
		                case "Inbond Pkg":
	                        BigDecimal inbondPkgs = resultData1.getInBondedPackagesDtl();
	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
		                case "Inbond Weight":
	                        BigDecimal inbondWeight = resultData1.getInbondGrossWtDtl();
	                        totalInbondWeight = totalInbondWeight.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Asset Value":
	                        BigDecimal inbondAssetValue = resultData1.getInbondCifValue();
	                        totalInbondAssetValue = totalInbondAssetValue.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Duty Value":
	                        BigDecimal inbondDutyValue = resultData1.getInbondCargoDuty();
	                        totalInbondDutyValue = totalInbondDutyValue.add(inbondDutyValue != null ? inbondDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondDutyValue != null ? inbondDutyValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                       
	                        
	                    case "Short Pkgs":
	                        BigDecimal shortage = resultData1.getShortagePackages();
	                        totalShortPkgs = totalShortPkgs.add(shortage != null ? shortage : BigDecimal.ZERO);
	                        cell.setCellValue(shortage != null ? shortage.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Damage Pkg":
	                        BigDecimal dmg = resultData1.getDamagedQty();
	                        totalDamagedWeight = totalDamagedWeight.add(dmg != null ? dmg : BigDecimal.ZERO);
	                        cell.setCellValue(dmg != null ? dmg.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case   "Brakage Pkg" :
	                        BigDecimal brakage = resultData1.getBreakage();
	                        totalBrakageValue = totalBrakageValue.add(brakage != null ? brakage : BigDecimal.ZERO);
	                        cell.setCellValue(brakage != null ? brakage.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	      
			        	  
	                        
		                case "Damage Remark":
		                    cell.setCellValue(resultData1.getComments() != null ? resultData1.getComments() : "");
		                    break;
		              
		                   
		                default:
		                    cell.setCellValue(""); // Handle undefined columns if necessary
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
		            
		            case "Marks and Nos on Pkg":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkg":
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
		                case "Short Pkgs":
		                    totalCell.setCellValue(totalShortPkgs.doubleValue());
		                    break;
		                case "Damage Pkg":
		                    totalCell.setCellValue(totalDamagedWeight.doubleValue());
		                    break;
		                case   "Brakage Pkg" :
		                    totalCell.setCellValue(totalBrakageValue.doubleValue());
		                    break;
		             
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		     // Set specific column widths after populating the data
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(0,  9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(3, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(4, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(5, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(6, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(7, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(8, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(9, 9 * 306); // Set width for "CHA" (30 characters wide)
		        
		        sheet.setColumnWidth(11, 30 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 40 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(15, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(16, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(19, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(20, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(21, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(22, 18 * 306); // Set width for "Importer" (40 characters wide)

		        
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        
		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    } catch (IOException e) 
		    {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	
	
	
	
	public ResponseEntity<List<CfExBondCrg>> getDataForBondDeliveryReport(
            String companyId,
            String branchId,
            String username,
            String type,
            String companyname,
            String branchname,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
            String boeNo,
            String exBondBeNo
    ) {
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

        List<CfExBondCrg> resultData=null;
           
     // Case 1: If both startDate and endDate are null, use getDataForBondDeliveryReportWithoutDates
        if (startDate == null && endDate == null) {
            resultData = cfExBondCrgRepository.getDataForBondDeliveryReportWithoutDates(companyId, branchId, boeNo, exBondBeNo);
        }
        // Case 2: If boeNo and exBondBeNo are both null or empty, use getDataForBondDeliveryReport with dates
        else if ((boeNo == null || boeNo.isEmpty()) && (exBondBeNo == null || exBondBeNo.isEmpty())) {
            resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
        }
        // Case 3: If only exBondBeNo is null or empty, use getDataForBondDeliveryReport with dates
        else if (exBondBeNo == null || exBondBeNo.isEmpty()) {
            resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
        }
        // Case 4: If only boeNo is null or empty, use getDataForBondDeliveryReport with dates
        else if (boeNo == null || boeNo.isEmpty()) {
            resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
        }
        // Case 5: If both boeNo and exBondBeNo are provided, use getDataForBondDeliveryReport with dates
        else {
            resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
        }
        if (resultData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
        }

        return new ResponseEntity<>(resultData, HttpStatus.OK); // Records found
    }
	
	
	public byte[] createExcelReportOfBondDeliveryRegister(
			 String companyId,
	            String branchId,
	            String username,
	            String type,
	            String companyname,
	            String branchname,
	            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	            String boeNo,
	            String exBondBeNo
			) throws DocumentException {


		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

		    	
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
		     
		     
		     System.out.println("companyId, branchId, startDate,endDate,boeNo,exBondBeNo________________" +companyId +" "+ branchId +" "+ startDate+" "+endDate +" "+boeNo +" "+exBondBeNo);
		     
		        String user = username;
		        String companyName = companyname;
		        String branchName = branchname;

		  
		        Branch branchAddress = branchRepo.findByBranchId(branchId);

		       
		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

		        double widthFactor = 1;
		
		        Sheet sheet = workbook.createSheet("Bond Delivery Register ");

		        
		        
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
		        	    "SR No", 
		        	    "Exbond Date", 
		        	    "Exbond BOE No", 
		        	    "NOC No", 
		        	    "NOC Date", 
		        	    "Inbond BOE No", 
		        	    "Bond No", 
		        	    "Bond Date", 
		        	    "Bond Expiry Date", 
		        	    "Importer", 
		        	    "CHA", 
		        	    "Commodity", 
		        	    "Pkgs", 
		        	    "Type Of Unit", 
		        	    "Weight", 
		        	    "Exbond CIF", 
		        	    "Exbond Duty"
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
		        reportTitleCell.setCellValue("Bond Delivery Register" );

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
		        
		        
		        Row reportTitleRow11q = sheet.createRow(4);
		        Cell reportTitleCell11q = reportTitleRow11q.createCell(0);
		      	        
		        
		        Row reportTitleRow1 = sheet.createRow(5);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        reportTitleCell1.setCellValue("BOND DELIVERY REGISTER (Form No. RB-7/Ver 1.0)" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
		        reportTitleStyle1.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont1 = workbook.createFont();
		        reportTitleFont1.setBold(true);
		        reportTitleFont1.setFontHeightInPoints((short) 12);
		        reportTitleFont1.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle1.setFont(reportTitleFont1);
		        reportTitleCell1.setCellStyle(reportTitleStyle1);
		        sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, columnsHeader.length - 1));
	

		        Row dateRow = sheet.createRow(6); // Adjust the row number as needed
		        Cell reportTitleCell11 = dateRow.createCell(0);
		        
		        CellStyle reportTitleStyle11 = workbook.createCellStyle();
		        reportTitleStyle11.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		      
		        Font reportTitleFont11 = workbook.createFont();
		        reportTitleFont11.setBold(true); // Make font bold
		        reportTitleFont11.setFontHeightInPoints((short) 12); // Set font size

		        reportTitleStyle11.setFont(reportTitleFont11);


		        reportTitleCell11.setCellStyle(reportTitleStyle11);

		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		      
		        String formattedReportDate = dateFormat.format(new Date()); // Current date
		        
		         Cell startDateCell = dateRow.createCell(0);
			     startDateCell.setCellValue("Start Date: "+formattedStartDate);
			     startDateCell.setCellStyle(reportTitleStyle11); // Use the same style if needed

			     Cell endDateCell = dateRow.createCell(3); // Leave 2 cells blank between
			     endDateCell.setCellValue("End Date: "+formattedEndDate );
			     endDateCell.setCellStyle(reportTitleStyle11); // Use the same style if needed

			     Cell reportDateCell = dateRow.createCell(6); // Leave 2 cells blank between
			     reportDateCell.setCellValue("Report Date: " + formattedReportDate);
			     reportDateCell.setCellStyle(reportTitleStyle11); // Use the same style if needed
			        

		  
		        // Set headers after the title
		        int headerRowIndex = 8; // Adjusted for the title rows above
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
		            int headerWidth = (int) (columnsHeader[i].length() * 306 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		       
		        BigDecimal totalInbondPkgs = BigDecimal.ZERO;
		        BigDecimal totalInbondWeight = BigDecimal.ZERO;
		        BigDecimal totalInbondAssetValue = BigDecimal.ZERO;
		        BigDecimal totalInbondDutyValue = BigDecimal.ZERO;
		       
		      
		        
		        int serialNo = 1; // Initialize serial number counter
		        
//		        List<CfExBondCrg> resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate,endDate,boeNo,exBondBeNo);
		        
		        List<CfExBondCrg> resultData=null;
		           
		        // Case 1: If both startDate and endDate are null, use getDataForBondDeliveryReportWithoutDates
		           if (startDate == null && endDate == null) {
		               resultData = cfExBondCrgRepository.getDataForBondDeliveryReportWithoutDates(companyId, branchId, boeNo, exBondBeNo);
		           }
		           // Case 2: If boeNo and exBondBeNo are both null or empty, use getDataForBondDeliveryReport with dates
		           else if ((boeNo == null || boeNo.isEmpty()) && (exBondBeNo == null || exBondBeNo.isEmpty())) {
		               resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
		           }
		           // Case 3: If only exBondBeNo is null or empty, use getDataForBondDeliveryReport with dates
		           else if (exBondBeNo == null || exBondBeNo.isEmpty()) {
		               resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
		           }
		           // Case 4: If only boeNo is null or empty, use getDataForBondDeliveryReport with dates
		           else if (boeNo == null || boeNo.isEmpty()) {
		               resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
		           }
		           // Case 5: If both boeNo and exBondBeNo are provided, use getDataForBondDeliveryReport with dates
		           else {
		               resultData = cfExBondCrgRepository.getDataForBondDeliveryReport(companyId, branchId, startDate, endDate, boeNo, exBondBeNo);
		           }
		          
	
		        for (CfExBondCrg resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) 
		            {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);
		                // Switch case to handle each column header
		                switch (columnsHeader[i]) 
		                {
		                case "SR No":
		                    cell.setCellValue(serialNo++); // Serial Number increment
		                    break;
	
		                case  "Exbond Date":
	                        if (resultData1.getExBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getExBondingDate());
	                            cell.setCellStyle(dateCellStyle); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
		                case  "Exbond BOE No":
		                    cell.setCellValue(resultData1.getExBondBeNo() != null ? resultData1.getExBondBeNo() : "");
		                    break;
		                    
		                    
		                case  "NOC Date":
	                        if (resultData1.getNocDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getNocDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;

		                case "Bond Date":
	                        if (resultData1.getBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondingDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
		                case  "Bond Expiry Date":
	                        if (resultData1.getBondValidityDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondValidityDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	      
	             
		                case "Inbond BOE No":
		                    cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
		                    break;
		                case  "NOC No":
		                    cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo().toString() : "");
		                    break;
		                case "Bond No":
		                    cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
		                    break;

		                case "Commodity":
		                    cell.setCellValue(resultData1.getCommodityDescription() != null ? resultData1.getCommodityDescription() : "");
		                    break;
		                case "Importer":
		                    cell.setCellValue(resultData1.getGiTransporterName() != null ? resultData1.getGiTransporterName() : "");
		                    break;
		                case "CHA":
		                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
		                    break;
		                case "Type Of Unit":
		                    cell.setCellValue(resultData1.getTypeOfPackage() != null ? resultData1.getTypeOfPackage() : "");
		                    break;
		                
		                    
		                case "Pkgs":
	                        BigDecimal inbondPkgs = resultData1.getExBondedPackagesdtl();
	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
		                case "Weight":
	                        BigDecimal inbondWeight = resultData1.getExBondedGWdtl();
	                        totalInbondWeight = totalInbondWeight.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Exbond CIF":
	                        BigDecimal inbondAssetValue = resultData1.getExBondedCIFdtl();
	                        totalInbondAssetValue = totalInbondAssetValue.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Exbond Duty":
	                        BigDecimal inbondDutyValue = resultData1.getExBondedCargoDutydtl();
	                        totalInbondDutyValue = totalInbondDutyValue.add(inbondDutyValue != null ? inbondDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondDutyValue != null ? inbondDutyValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
		                   
		                default:
		                    cell.setCellValue(""); // Handle undefined columns if necessary
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
		            
		            case "Commodity":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		            case "Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		            case "Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		            case "Exbond CIF":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		            case "Exbond Duty":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		             
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		    
        	    sheet.setColumnWidth(0,  9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(1, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(2, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(3, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(4, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(5, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(6, 22 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(7, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(9, 30 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(10, 40 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(11, 40 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(13, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(14, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(15, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(16, 18 * 306); // Set width for "CHA" (30 characters wide)
		        
		     
		       
		        // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row
		        
		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	
	
	public byte[] createExcelReportOfLiveBondReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname, String exBondingId,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) throws DocumentException {

		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

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
		        List<CfExBondCrg> resultData = cfExBondCrgRepository.getDataForCustomsBondExbondReport(companyId, branchId, exBondingId);

		        Sheet sheet = workbook.createSheet("Live Bond Report");
		        
		        
		        Sheet sheet2 = workbook.createSheet("IN BOND ");
		        
		        Sheet sheet3 = workbook.createSheet("EX BOND ");
		        

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
		        	    "Noc no", 
		        	    "Noc date", 
		        	    "Inbond boe no", 
		        	    "Inbond boe date", 
		        	    "IgM no", 
		        	    "Item no", 
		        	    "Inbond weight", 
		        	    "Area", 
		        	    "Inbond asset value", 
		        	    "Inbond custom duty value", 
		        	    "Asset value + duty value", 
		        	    "Cha name", 
		        	    "Importer name", 
		        	    "Cargo description", 
		        	    "Bond bal asset value", 
		        	    "Bond bal duty value", 
		        	    "Bond expiry date", 
		        	    "Balance pkg", 
		        	    "Exbond delivery pkgs", 
		        	    "Remark"
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
		        reportTitleCell.setCellValue("Live Bond Report" );

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
		        reportTitleCell1.setCellValue("Live Bond Report");

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
		            int headerWidth = (int) (columnsHeader[i].length() * 306 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		        int serialNo = 1; // Initialize serial number counter
		        for (CfExBondCrg resultData1 : resultData) {
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
		                case "Noc No":
		                    cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo() : "");
		                    break;

		                case "Igm No":
		                    cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
		                    break;

		                case "Cha Name":
		                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
		                    break;
		                case "Importer Name":
		                    cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
		                    break;

		                default:
		                    cell.setCellValue(""); // Handle undefined columns if necessary
		                    break;
		            }



		            }
		        }

		     // Set specific column widths after populating the data
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(12, 30 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(13, 40 * 306); // Set width for "Importer" (40 characters wide)

		        // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row
		        
		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	public ResponseEntity<List<Cfinbondcrg>> getDataOfExpiredBondToShow(
            String companyId,
            String branchId,
            String username,
            String type,
            String companyname,
            String branchname,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
    ) {
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

        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForExpiredBondReport(companyId, branchId, startDate,endDate);

        if (resultData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
        }

        return new ResponseEntity<>(resultData, HttpStatus.OK); // Records found
    }
	

	
	public byte[] createExcelReportOfExpiredbondreport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
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
		    
		        
		       

		        Sheet sheet = workbook.createSheet("Expired Bond Report");

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

		        

		        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForExpiredBondReport(companyId, branchId, startDate,endDate);

		     

		        String[] columnsHeader = {
		        		"Sr. No.", 
		        	    "Noc No", 
		        	    "Noc Date", 
		        	    "Inbond Boe No", 
		        	    "Inbond Boe Date", 
		        	    "IgM No", 
		        	    "Item No", 
		        	    "Inbond Weight", 
		        	    "Area", 
		        	    "Inbond Asset Value", 
		        	    "Inbond Custom Duty", 
		        	    "Asset Value + Duty Value", 
		        	    "Cha Name", 
		        	    "Importer Name", 
		        	    "Cargo Description", 
		        	    "Bond Bal Asset Value", 
		        	    "Bond Bal Duty Value", 
		        	    "Bond Expiry Date", 
		        	    "Balance Pkgs", 
		        	    "Exbond Delivery Pkgs", 
		        	    "Remark"
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
		        reportTitleCell.setCellValue("Expired Bond Report" );

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
		        	 reportTitleCell1.setCellValue("Expired Bond Report : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Expired Bond Report : " + formattedStartDate + " to " + formattedEndDate);
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
		        BigDecimal totalInsurance = BigDecimal.ZERO;
		        BigDecimal totalBalAssetValue = BigDecimal.ZERO;
		        BigDecimal totalBalDutyValue = BigDecimal.ZERO;
		        BigDecimal totalArea = BigDecimal.ZERO;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Cfinbondcrg resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);


		                // Switch case to handle each column header
		                switch (columnsHeader[i]) 
		                {
		                case   "Sr. No.":
	                        cell.setCellValue(serialNo++); // Serial Number increment
	                        break;
	                    case "Noc No":
	                        cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo() : "");
	                        break;
	                    case "Noc Date":
	                        if (resultData1.getNocDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getNocDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Inbond Boe Date":
	                        if (resultData1.getBoeDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBoeDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Bond Expiry Date":
	                        if (resultData1.getBondValidityDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondValidityDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
	                    case "In Bonding Date":
	                        if (resultData1.getInBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getInBondingDate());
	                            cell.setCellStyle(dateCellStyle); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Bond Date":
	                        if (resultData1.getBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondingDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
	                    case "Inbond Boe No":
	                        cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
	                        break;


	                    case "IgM No":
	                        cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
	                        break;
	                    case "Item No":
	                        cell.setCellValue(resultData1.getIgmLineNo() != null ? resultData1.getIgmLineNo().toString() : "");
	                        break;
	                        
	                    case "Exbond Delivery Pkgs":
	                        BigDecimal inbondPkgs = resultData1.getExBondedPackagesDtl();
	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
	                    case "Inbond Weight":
	                        BigDecimal inbondWeight = resultData1.getInbondGrossWtDtl();
	                        totalInbondWeight = totalInbondWeight.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
	                    case "Inbond Asset Value":
	                        BigDecimal inbondAssetValue = resultData1.getInbondCifValue();
	                        totalInbondAssetValue = totalInbondAssetValue.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Custom Duty":
	                        BigDecimal inbondDutyValue = resultData1.getInbondCargoDuty();
	                        totalInbondDutyValue = totalInbondDutyValue.add(inbondDutyValue != null ? inbondDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondDutyValue != null ? inbondDutyValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Balance Pkgs":
	                        BigDecimal balPkgs = resultData1.getInBondedPackagesDtl().subtract(resultData1.getExBondedPackagesDtl() != null ? resultData1.getExBondedPackagesDtl() : BigDecimal.ZERO);
	                        totalBalPkgs = totalBalPkgs.add(balPkgs != null ? balPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(balPkgs.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Asset Value + Duty Value":
	                        BigDecimal balWt = resultData1.getInbondInsuranceValueDtl();
	                        totalInsurance = totalInsurance.add(balWt != null ? balWt : BigDecimal.ZERO);
	                        cell.setCellValue(balWt.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bond Bal Asset Value":
	                        BigDecimal balAssetValue = resultData1.getInbondCifValue().subtract(resultData1.getExbondCifValue() != null ? resultData1.getExbondCifValue() : BigDecimal.ZERO);
	                        totalBalAssetValue = totalBalAssetValue.add(balAssetValue != null ? balAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(balAssetValue.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bond Bal Duty Value":
	                        BigDecimal balDutyValue = resultData1.getInbondCargoDuty().subtract(resultData1.getExbondCargoDuty() != null ? resultData1.getExbondCargoDuty() : BigDecimal.ZERO);
	                        totalBalDutyValue = totalBalDutyValue.add(balDutyValue != null ? balDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(balDutyValue.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Area":
	                        BigDecimal areaBalanceArea = resultData1.getAreaOccupied();
	                        totalArea = totalArea.add(areaBalanceArea != null ? areaBalanceArea : BigDecimal.ZERO);
	                        cell.setCellValue(areaBalanceArea.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Cargo Description":
	                        cell.setCellValue(resultData1.getCommodityDescription() != null ? resultData1.getCommodityDescription() : "");
	                        break;
	                    case "Cha Name":
	                        cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
	                        break;
	                    case "Importer Name":
	                        cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
	                        break;
	                   
	                              case "Bond No":
	                        cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
	                        break;
	                        
	                        
	                        case "Remark" :
		                        cell.setCellValue(resultData1.getComments() != null ? resultData1.getComments() : "");
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
		            
		            case "Item No":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Exbond Delivery Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Custom Duty":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Balance Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Asset Value + Duty Value":
		                    totalCell.setCellValue(totalInsurance.doubleValue());
		                    break;
		                case "Bond Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bond Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area":
		                    totalCell.setCellValue(totalArea.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(4, 18 * 306); 
		        sheet.setColumnWidth(5, 14 * 306); 
		        
		        sheet.setColumnWidth(6, 14 * 306); 
		        sheet.setColumnWidth(8, 14 * 306); 
		        
		        sheet.setColumnWidth(10, 27 * 306); 
		        
		        sheet.setColumnWidth(11, 27 * 306); 
		        sheet.setColumnWidth(12, 36 * 306); 
		        sheet.setColumnWidth(13, 40 * 306); 
		        
		        sheet.setColumnWidth(14, 18 * 306); 
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(18, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(19, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(20, 27 * 306); // Set width for "Importer" (40 characters wide)

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
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfSection49LiveBondReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname, String exBondingId) throws DocumentException {

		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

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
		        List<CfExBondCrg> resultData = cfExBondCrgRepository.getDataForCustomsBondExbondReport(companyId, branchId, exBondingId);

		        Sheet sheet = workbook.createSheet("Section 49 Live Bond Report" + exBondingId);

		        String[] columnsHeader = {
		        	    "Sr. No.", 
		        	    "Noc no", 
		        	    "Noc date", 
		        	    "Section 49(yes/no)",  // Updated column
		        	    "Inbond boe no", 
		        	    "Inbond boe date", 
		        	    "IgM no", 
		        	    "Item no", 
		        	    "Inbond weight", 
		        	    "Area", 
		        	    "Inbond asset value", 
		        	    "Inbond custom duty value", 
		        	    "Asset value + duty value", 
		        	    "Cha name", 
		        	    "Importer name", 
		        	    "Cargo description", 
		        	    "Bond bal asset value", 
		        	    "Bond bal duty value", 
		        	    "Bond expiry date", 
		        	    "Balance pkg", 
		        	    "Exbond delivery pkgs", 
		        	    "Remark"
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
		        reportTitleCell.setCellValue("Section 49 Live Bond Report" );

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
		        
		        
		        
//		        Row reportTitleRow1 = sheet.createRow(4);
//		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
//		        reportTitleCell1.setCellValue("Bond cargo Inventory Report" );
//
//		        // Set alignment and merge cells for the heading
//		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
//		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT);
//		        Font reportTitleFont1 = workbook.createFont();
//		        reportTitleFont1.setBold(true);
//		        reportTitleFont1.setFontHeightInPoints((short) 12);
		        
		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        reportTitleCell1.setCellValue("Section 49 Live Bond Report");

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

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 306 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		        int serialNo = 1; // Initialize serial number counter
		        for (CfExBondCrg resultData1 : resultData) {
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
		                case "Noc no":
		                    cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo() : "");
		                    break;
//		                case "Noc date":
//		                    cell.setCellValue(resultData1.getNocDate() != null ? dateFormat.format(resultData1.getNocDate()) : "");
//		                    break;
//		                case "Section 49(yes/no)": // New column added
//		                    cell.setCellValue(resultData1.isSection49() ? "Yes" : "No"); // Assuming a boolean method for Section 49
//		                    break;
//		                case "Inbond boe no":
//		                    cell.setCellValue(resultData1.getInbondBoeNo() != null ? resultData1.getInbondBoeNo() : "");
//		                    break;
//		                case "Inbond boe date":
//		                    cell.setCellValue(resultData1.getInbondBoeDate() != null ? dateFormat.format(resultData1.getInbondBoeDate()) : "");
//		                    break;
		                case "IgM no":
		                    cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
		                    break;
//		                case "Item no":
//		                    cell.setCellValue(resultData1.getItemNo() != null ? resultData1.getItemNo().toString() : "");
//		                    break;
//		                case "Inbond weight":
//		                    cell.setCellValue(resultData1.getInbondWeight() != null ? resultData1.getInbondWeight().toString() : "");
//		                    break;
//		                case "Area":
//		                    cell.setCellValue(resultData1.getArea() != null ? resultData1.getArea() : "");
//		                    break;
//		                case "Inbond asset value":
//		                    cell.setCellValue(resultData1.getInbondAssetValue() != null ? resultData1.getInbondAssetValue().toString() : "");
//		                    break;
//		                case "Inbond custom duty value":
//		                    cell.setCellValue(resultData1.getInbondCustomDutyValue() != null ? resultData1.getInbondCustomDutyValue().toString() : "");
//		                    break;
//		                case "Asset value + duty value":
//		                    double totalValue = (resultData1.getInbondAssetValue() != null ? resultData1.getInbondAssetValue() : 0) 
//		                                        + (resultData1.getInbondCustomDutyValue() != null ? resultData1.getInbondCustomDutyValue() : 0);
//		                    cell.setCellValue(totalValue);
//		                    break;
		                case "Cha name":
		                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
		                    break;
		                case "Importer name":
		                    cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
		                    break;
//		                case "Cargo description":
//		                    cell.setCellValue(resultData1.getCargoDescription() != null ? resultData1.getCargoDescription() : "");
//		                    break;
//		                case "Bond bal asset value":
//		                    cell.setCellValue(resultData1.getBondBalAssetValue() != null ? resultData1.getBondBalAssetValue().toString() : "");
//		                    break;
//		                case "Bond bal duty value":
//		                    cell.setCellValue(resultData1.getBondBalDutyValue() != null ? resultData1.getBondBalDutyValue().toString() : "");
//		                    break;
//		                case "Bond expiry date":
//		                    cell.setCellValue(resultData1.getBondExpiryDate() != null ? dateFormat.format(resultData1.getBondExpiryDate()) : "");
//		                    break;
//		                case "Balance pkg":
//		                    cell.setCellValue(resultData1.getBalancePkg() != null ? resultData1.getBalancePkg().toString() : "");
//		                    break;
//		                case "Exbond delivery pkgs":
//		                    cell.setCellValue(resultData1.getExbondDeliveryPkgs() != null ? resultData1.getExbondDeliveryPkgs().toString() : "");
//		                    break;
//		                case "Remark":
//		                    cell.setCellValue(resultData1.getRemark() != null ? resultData1.getRemark() : "");
//		                    break;
		                default:
		                    cell.setCellValue(""); // Handle undefined columns if necessary
		                    break;
		            }




		            }
		        }

		     // Set specific column widths after populating the data
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(13, 30 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(14, 40 * 306); // Set width for "Importer" (40 characters wide)

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
	
	
	
	
	
	
	
	
	
	
	
//	public byte[] createExcelReportOfSection49ExpiredBondReport(String companyId,
//		    String branchId, String username, String type, String companyname,
//		    String branchname, String exBondingId) throws DocumentException {
//
//		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//
//		        String user = username;
//		        String companyName = companyname;
//		        String branchName = branchname;
//
//		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
//		        Branch branchAddress = branchRepo.findByBranchId(branchId);
//
//		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
//		                + companyAddress.getAddress_3() + companyAddress.getCity();
//
//		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
//		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();
//
//		        double widthFactor = 1;
//		        List<CfExBondCrg> resultData = cfExBondCrgRepository.getDataForCustomsBondExbondReport(companyId, branchId, exBondingId);
//
//		        Sheet sheet = workbook.createSheet("Section 49 Expired Bond Report" + exBondingId);
//
//		        String[] columnsHeader = {
//		        	    "Sr. No.", 
//		        	    "Noc no", 
//		        	    "Noc date", 
//		        	    "Section 49(yes/no)", 
//		        	    "Inbond boe no", 
//		        	    "Inbond boe date", 
//		        	    "IgM no", 
//		        	    "Item no", 
//		        	    "Inbond weight", 
//		        	    "Area", 
//		        	    "Inbond asset value", 
//		        	    "Inbond custom duty value", 
//		        	    "Asset value + duty value", 
//		        	    "Cha name", 
//		        	    "Importer name", 
//		        	    "Cargo description", 
//		        	    "Bond bal asset value", 
//		        	    "Bond bal duty value", 
//		        	    "Bond expiry date", 
//		        	    "Balance pkg", 
//		        	    "Exbond delivery pkgs", 
//		        	    "Remark"
//		        	};
//
//
//
//
//	
//		        // Add Company Name (Centered)
//		        Row companyRow = sheet.createRow(0);
//		        Cell companyCell = companyRow.createCell(0);
//		        companyCell.setCellValue(companyName);
//
//		        // Create and style for centering
//		        CellStyle companyStyle = workbook.createCellStyle();
//		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
//		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		        Font companyFont = workbook.createFont();
//		        companyFont.setBold(true);
//		        companyFont.setFontHeightInPoints((short)18);
//		        companyStyle.setFont(companyFont);
//		        companyCell.setCellStyle(companyStyle);
//		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader.length - 1));
//
//		        // Add Branch Address (Centered)
//		        Row branchRow = sheet.createRow(1);
//		        Cell branchCell = branchRow.createCell(0);
//		        branchCell.setCellValue(branchAdd);
//		        CellStyle branchStyle = workbook.createCellStyle();
//		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
//		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		        Font branchFont = workbook.createFont();
//		        branchFont.setFontHeightInPoints((short) 12);
//		        branchStyle.setFont(branchFont);
//		        branchCell.setCellStyle(branchStyle);
//		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader.length - 1));
//
//		        
//		        Row branchRow1 = sheet.createRow(2);
//		        Cell branchCell1 = branchRow1.createCell(0);
////		        branchCell1.setCellValue(branchAdd);
//		        CellStyle branchStyle1 = workbook.createCellStyle();
//		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
//		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
//		        Font branchFont1 = workbook.createFont();
//		        branchFont1.setFontHeightInPoints((short) 12);
//		        branchStyle1.setFont(branchFont1);
//		        branchCell1.setCellStyle(branchStyle1);
//		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, columnsHeader.length - 1));
//	
//		        // Add Report Title "Bond cargo Inventory Report"
//		        Row reportTitleRow = sheet.createRow(3);
//		        Cell reportTitleCell = reportTitleRow.createCell(0);
//		        reportTitleCell.setCellValue("Section 49 Expired Bond Report" );
//
//		        // Set alignment and merge cells for the heading
//		        CellStyle reportTitleStyle = workbook.createCellStyle();
//		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
//		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		        Font reportTitleFont = workbook.createFont();
//		        reportTitleFont.setBold(true);
//		        reportTitleFont.setFontHeightInPoints((short) 16);
//		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
//		        reportTitleStyle.setFont(reportTitleFont);
//		        reportTitleCell.setCellStyle(reportTitleStyle);
//		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader.length - 1));
//		        
//		        
//		        
////		        Row reportTitleRow1 = sheet.createRow(4);
////		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
////		        reportTitleCell1.setCellValue("Bond cargo Inventory Report" );
////
////		        // Set alignment and merge cells for the heading
////		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
////		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT);
////		        Font reportTitleFont1 = workbook.createFont();
////		        reportTitleFont1.setBold(true);
////		        reportTitleFont1.setFontHeightInPoints((short) 12);
//		        
//		        
//		        Row reportTitleRow1 = sheet.createRow(4);
//		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
//		        reportTitleCell1.setCellValue("Section 49 Expired Bond Report");
//
//		        // Create and set up the CellStyle for the report title
//		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
//		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment
//
//		        // Create the font and set its properties
//		        Font reportTitleFont1 = workbook.createFont();
//		        reportTitleFont1.setBold(true); // Make font bold
//		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size
//
//		        // Apply the font to the CellStyle
//		        reportTitleStyle1.setFont(reportTitleFont1);
//
//		        // Set the style to the cell
//		        reportTitleCell1.setCellStyle(reportTitleStyle1);
//		     // Create a font and set its properties
//		    
//
//		        
//		        // Set headers after the title
//		        int headerRowIndex = 6; // Adjusted for the title rows above
//		        Row headerRow = sheet.createRow(headerRowIndex);
//		        
//		        CellStyle borderStyle = workbook.createCellStyle();
//		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
//		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//		        borderStyle.setBorderBottom(BorderStyle.THIN);
//		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
//		        borderStyle.setBorderTop(BorderStyle.THIN);
//		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
//		        borderStyle.setBorderLeft(BorderStyle.THIN);
//		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
//		        borderStyle.setBorderRight(BorderStyle.THIN);
//		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
//
//		        Font boldFont = workbook.createFont();
//		        boldFont.setBold(true);
//		        boldFont.setFontHeightInPoints((short) 16);
//
//		        for (int i = 0; i < columnsHeader.length; i++) {
//		            Cell cell = headerRow.createCell(i);
//		            cell.setCellValue(columnsHeader[i]);
//
//		            CellStyle headerStyle = workbook.createCellStyle();
//		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//
//		            Font headerFont = workbook.createFont();
//		            headerFont.setBold(true);
//		            headerFont.setFontHeightInPoints((short) 11);
//
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
//		            cell.setCellStyle(headerStyle);
//		            int headerWidth = (int) (columnsHeader[i].length() * 306 * widthFactor);
//		            sheet.setColumnWidth(i, headerWidth);
//		        }
//
//		        // Populate data rows
//		        int rowNum = headerRowIndex + 1;
//		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//		        int serialNo = 1; // Initialize serial number counter
//		        for (CfExBondCrg resultData1 : resultData) {
//		            Row dataRow = sheet.createRow(rowNum++);
//		    
//		            int cellNum = 0;
//
//		            for (int i = 0; i < columnsHeader.length; i++) {
//		                Cell cell = dataRow.createCell(i);
//		                cell.setCellStyle(borderStyle);
//
//		                // Switch case to handle each column header
//		                switch (columnsHeader[i]) {
//		                case "Sr. No.":
//		                    cell.setCellValue(serialNo++); // Serial Number increment
//		                    break;
//		                case "Noc no":
//		                    cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo() : "");
//		                    break;
////		                case "Noc date":
////		                    cell.setCellValue(resultData1.getNocDate() != null ? dateFormat.format(resultData1.getNocDate()) : "");
////		                    break;
////		                case "Section 49(yes/no)":
////		                    cell.setCellValue(resultData1.isSection49() ? "Yes" : "No"); // Assuming a boolean or similar method for Section 49
////		                    break;
////		                case "Inbond boe no":
////		                    cell.setCellValue(resultData1.getInbondBoeNo() != null ? resultData1.getInbondBoeNo() : "");
////		                    break;
////		                case "Inbond boe date":
////		                    cell.setCellValue(resultData1.getInbondBoeDate() != null ? dateFormat.format(resultData1.getInbondBoeDate()) : "");
////		                    break;
//		                case "IgM no":
//		                    cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
//		                    break;
////		                case "Item no":
////		                    cell.setCellValue(resultData1.getItemNo() != null ? resultData1.getItemNo().toString() : "");
////		                    break;
////		                case "Inbond weight":
////		                    cell.setCellValue(resultData1.getInbondWeight() != null ? resultData1.getInbondWeight().toString() : "");
////		                    break;
////		                case "Area":
////		                    cell.setCellValue(resultData1.getArea() != null ? resultData1.getArea() : "");
////		                    break;
////		                case "Inbond asset value":
////		                    cell.setCellValue(resultData1.getInbondAssetValue() != null ? resultData1.getInbondAssetValue().toString() : "");
////		                    break;
////		                case "Inbond custom duty value":
////		                    cell.setCellValue(resultData1.getInbondCustomDutyValue() != null ? resultData1.getInbondCustomDutyValue().toString() : "");
////		                    break;
////		                case "Asset value + duty value":
////		                    double totalValue = (resultData1.getInbondAssetValue() != null ? resultData1.getInbondAssetValue() : 0) 
////		                                        + (resultData1.getInbondCustomDutyValue() != null ? resultData1.getInbondCustomDutyValue() : 0);
////		                    cell.setCellValue(totalValue);
////		                    break;
//		                case "Cha name":
//		                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
//		                    break;
//		                case "Importer name":
//		                    cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
//		                    break;
////		                case "Cargo description":
////		                    cell.setCellValue(resultData1.getCargoDescription() != null ? resultData1.getCargoDescription() : "");
////		                    break;
////		                case "Bond bal asset value":
////		                    cell.setCellValue(resultData1.getBondBalAssetValue() != null ? resultData1.getBondBalAssetValue().toString() : "");
////		                    break;
////		                case "Bond bal duty value":
////		                    cell.setCellValue(resultData1.getBondBalDutyValue() != null ? resultData1.getBondBalDutyValue().toString() : "");
////		                    break;
////		                case "Bond expiry date":
////		                    cell.setCellValue(resultData1.getBondExpiryDate() != null ? dateFormat.format(resultData1.getBondExpiryDate()) : "");
////		                    break;
////		                case "Balance pkg":
////		                    cell.setCellValue(resultData1.getBalancePkg() != null ? resultData1.getBalancePkg().toString() : "");
////		                    break;
////		                case "Exbond delivery pkgs":
////		                    cell.setCellValue(resultData1.getExbondDeliveryPkgs() != null ? resultData1.getExbondDeliveryPkgs().toString() : "");
////		                    break;
////		                case "Remark":
////		                    cell.setCellValue(resultData1.getRemark() != null ? resultData1.getRemark() : "");
////		                    break;
//		                default:
//		                    cell.setCellValue(""); // Handle undefined columns if necessary
//		                    break;
//		            }
//
//
//
//
//		            }
//		        }
//
//		     // Set specific column widths after populating the data
//		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
//		        sheet.setColumnWidth(13, 30 * 306); // Set width for "CHA" (30 characters wide)
//		        sheet.setColumnWidth(14, 40 * 306); // Set width for "Importer" (40 characters wide)
//
//		        workbook.write(outputStream);
//		        return outputStream.toByteArray();
//
//		    } catch (IOException e) {
//		        e.printStackTrace();
//		    }
//
//		    return null;
//	}
	
	
	
	public ResponseEntity<List<Cfinbondcrg>> getDataOfSection49ExpiredBondToShow(
            String companyId,
            String branchId,
            String username,
            String type,
            String companyname,
            String branchname,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
    ) {
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

        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForSection49ExpiredBondReport(companyId, branchId, startDate,endDate);

        if (resultData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No records found
        }

        return new ResponseEntity<>(resultData, HttpStatus.OK); // Records found
    }
	
	public byte[] createExcelReportOfSection49ExpiredBondReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate
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
		    
		        
		       

		        Sheet sheet = workbook.createSheet("Section 49 Expired Bond Report");

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

		        

		        List<Cfinbondcrg> resultData = cfinbondcrgRepo.getDataForSection49ExpiredBondReport(companyId, branchId, startDate,endDate);

		     

		        String[] columnsHeader = {
		        		"Sr. No.", 
		        	    "Noc No", 
		        	    "Noc Date", 
		        	    "Section 49(Yes/No)", 
		        	    "Inbond Boe No", 
		        	    "Inbond Boe Date", 
		        	    "IgM No", 
		        	    "Item No", 
		        	    "Inbond Weight", 
		        	    "Area", 
		        	    "Inbond Asset Value", 
		        	    "Inbond Custom Duty", 
		        	    "Asset Value + Duty Value", 
		        	    "Cha Name", 
		        	    "Importer Name", 
		        	    "Cargo Description", 
		        	    "Bond Bal Asset Value", 
		        	    "Bond Bal Duty Value", 
		        	    "Bond Expiry Date", 
		        	    "Balance Pkgs", 
		        	    "Exbond Delivery Pkgs", 
		        	    "Remark"
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
		        reportTitleCell.setCellValue("Section 49 Expired Bond Report" );

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
		        	 reportTitleCell1.setCellValue("Section 49 Expired Bond Report : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("Section 49 Expired Bond Report : " + formattedStartDate + " to " + formattedEndDate);
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
		        BigDecimal totalInsurance = BigDecimal.ZERO;
		        BigDecimal totalBalAssetValue = BigDecimal.ZERO;
		        BigDecimal totalBalDutyValue = BigDecimal.ZERO;
		        BigDecimal totalArea = BigDecimal.ZERO;
		        
		        
		        int serialNo = 1; // Initialize serial number counter
		        for (Cfinbondcrg resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);


		                // Switch case to handle each column header
		                switch (columnsHeader[i]) 
		                {
		                case   "Sr. No.":
	                        cell.setCellValue(serialNo++); // Serial Number increment
	                        break;
	                    case "Noc No":
	                        cell.setCellValue(resultData1.getNocNo() != null ? resultData1.getNocNo() : "");
	                        break;
	                    case "Noc Date":
	                        if (resultData1.getNocDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getNocDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Inbond Boe Date":
	                        if (resultData1.getBoeDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBoeDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Bond Expiry Date":
	                        if (resultData1.getBondValidityDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondValidityDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
	                    case "In Bonding Date":
	                        if (resultData1.getInBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getInBondingDate());
	                            cell.setCellStyle(dateCellStyle); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                    case "Bond Date":
	                        if (resultData1.getBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondingDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
	                       
	                    case "Inbond Boe No":
	                        cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
	                        break;
	                        
	                    case "Section 49(Yes/No)":
	                        cell.setCellValue(resultData1.getSection49().equals("Y") ? "YES" :
	                                         resultData1.getSection49().equals("N") ? "NO" :
	                                         resultData1.getSection49());
	                        break;


	                    case "IgM No":
	                        cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
	                        break;
	                    case "Item No":
	                        cell.setCellValue(resultData1.getIgmLineNo() != null ? resultData1.getIgmLineNo().toString() : "");
	                        break;
	                        
	                    case "Exbond Delivery Pkgs":
	                        BigDecimal inbondPkgs = resultData1.getExBondedPackagesDtl();
	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
	                    case "Inbond Weight":
	                        BigDecimal inbondWeight = resultData1.getInbondGrossWtDtl();
	                        totalInbondWeight = totalInbondWeight.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
	                    case "Inbond Asset Value":
	                        BigDecimal inbondAssetValue = resultData1.getInbondCifValue();
	                        totalInbondAssetValue = totalInbondAssetValue.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Inbond Custom Duty":
	                        BigDecimal inbondDutyValue = resultData1.getInbondCargoDuty();
	                        totalInbondDutyValue = totalInbondDutyValue.add(inbondDutyValue != null ? inbondDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondDutyValue != null ? inbondDutyValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Balance Pkgs":
	                        BigDecimal balPkgs = resultData1.getInBondedPackagesDtl().subtract(resultData1.getExBondedPackagesDtl() != null ? resultData1.getExBondedPackagesDtl() : BigDecimal.ZERO);
	                        totalBalPkgs = totalBalPkgs.add(balPkgs != null ? balPkgs : BigDecimal.ZERO);
	                        cell.setCellValue(balPkgs.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Asset Value + Duty Value":
	                        BigDecimal balWt = resultData1.getInbondInsuranceValueDtl();
	                        totalInsurance = totalInsurance.add(balWt != null ? balWt : BigDecimal.ZERO);
	                        cell.setCellValue(balWt.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bond Bal Asset Value":
	                        BigDecimal balAssetValue = resultData1.getInbondCifValue().subtract(resultData1.getExbondCifValue() != null ? resultData1.getExbondCifValue() : BigDecimal.ZERO);
	                        totalBalAssetValue = totalBalAssetValue.add(balAssetValue != null ? balAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(balAssetValue.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Bond Bal Duty Value":
	                        BigDecimal balDutyValue = resultData1.getInbondCargoDuty().subtract(resultData1.getExbondCargoDuty() != null ? resultData1.getExbondCargoDuty() : BigDecimal.ZERO);
	                        totalBalDutyValue = totalBalDutyValue.add(balDutyValue != null ? balDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(balDutyValue.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Area":
	                        BigDecimal areaBalanceArea = resultData1.getAreaOccupied();
	                        totalArea = totalArea.add(areaBalanceArea != null ? areaBalanceArea : BigDecimal.ZERO);
	                        cell.setCellValue(areaBalanceArea.doubleValue());
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Cargo Description":
	                        cell.setCellValue(resultData1.getCommodityDescription() != null ? resultData1.getCommodityDescription() : "");
	                        break;
	                    case "Cha Name":
	                        cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
	                        break;
	                    case "Importer Name":
	                        cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
	                        break;
	                   
	                              case "Bond No":
	                        cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
	                        break;
	                        
	                        
	                        case "Remark" :
		                        cell.setCellValue(resultData1.getComments() != null ? resultData1.getComments() : "");
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
		            
		            case "Item No":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Exbond Delivery Pkgs":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "Inbond Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Inbond Asset Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Inbond Custom Duty":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Balance Pkgs":
		                    totalCell.setCellValue(totalBalPkgs.doubleValue());
		                    break;
		                case "Asset Value + Duty Value":
		                    totalCell.setCellValue(totalInsurance.doubleValue());
		                    break;
		                case "Bond Bal Asset Value":
		                    totalCell.setCellValue(totalBalAssetValue.doubleValue());
		                    break;
		                case "Bond Bal Duty Value":
		                    totalCell.setCellValue(totalBalDutyValue.doubleValue());
		                    break;
		                case "Area":
		                    totalCell.setCellValue(totalArea.doubleValue());
		                    break;
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		        
		        sheet.setColumnWidth(0,  9 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        sheet.setColumnWidth(6, 14 * 306); 
		        
		        sheet.setColumnWidth(7, 14 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        
		        sheet.setColumnWidth(11, 27 * 306); 
		        
		        sheet.setColumnWidth(12, 27 * 306); 
		        sheet.setColumnWidth(13, 36 * 306); 
		        sheet.setColumnWidth(14, 40 * 306); 
		        
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 18 * 306); 
		        sheet.setColumnWidth(17, 18 * 306); // Set width for "Importer" (40 characters wide)
		        
		        sheet.setColumnWidth(19, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(20, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(21, 27 * 306); // Set width for "Importer" (40 characters wide)

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public byte[] createExcelReportOfAuditTrailReport(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname, String exBondingId) throws DocumentException {

		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

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
		        List<CfExBondCrg> resultData = cfExBondCrgRepository.getDataForCustomsBondExbondReport(companyId, branchId, exBondingId);

		        Sheet sheet = workbook.createSheet("AuditTrail Report" + exBondingId);

		        String[] columnsHeader = {
		        	    "BOE No New", 
		        	    "BOE No Old", 
		        	    "BOE Date New", 
		        	    "BOE Date Old", 
		        	    "InBonding Date New", 
		        	    "InBonding Date Old", 
		        	    "Inbond pkg New", 
		        	    "Inbond pkg Old", 
		        	    "Bond No New", 
		        	    "Bond No Old", 
		        	    "Bond Date New", 
		        	    "Bond Date Old", 
		        	    "Inbond Cargo Duty New", 
		        	    "Inbond Cargo Duty Old", 
		        	    "Inbond CIF Value New", 
		        	    "Inbond CIF Value Old", 
		        	    "CHA New", 
		        	    "CHA Old", 
		        	    "Commodity Description New", 
		        	    "Commodity Description Old", 
		        	    "Importer Name New", 
		        	    "Importer Name Old", 
		        	    "Section49 New", 
		        	    "Section49 Old", 
		        	    "Section60 New", 
		        	    "Section60 Old", 
		        	    "Bond Validity Date New", 
		        	    "Bond Validity Date Old", 
		        	    "Trans Type", 
		        	    "ExBond BOE No New", 
		        	    "ExBond BOE No Old", 
		        	    "ExBond BOE Date New", 
		        	    "ExBond BOE Date Old", 
		        	    "Exbonding Date New", 
		        	    "Exbonding Date Old", 
		        	    "Exbonding pkg New", 
		        	    "Exbonding pkg Old", 
		        	    "Exbond Cargo Duty", 
		        	    "Exbond Cargo Duty Old", 
		        	    "Exbond CIF Value", 
		        	    "Exbond CIF Value Old"
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
		        reportTitleCell.setCellValue("AuditTrail Report" );

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
		        
		        
		        
//		        Row reportTitleRow1 = sheet.createRow(4);
//		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
//		        reportTitleCell1.setCellValue("Bond cargo Inventory Report" );
//
//		        // Set alignment and merge cells for the heading
//		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
//		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT);
//		        Font reportTitleFont1 = workbook.createFont();
//		        reportTitleFont1.setBold(true);
//		        reportTitleFont1.setFontHeightInPoints((short) 12);
		        
		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        reportTitleCell1.setCellValue("AuditTrail Report");

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

		            cell.setCellStyle(headerStyle);
		            int headerWidth = (int) (columnsHeader[i].length() * 306 * widthFactor);
		            sheet.setColumnWidth(i, headerWidth);
		        }

		        // Populate data rows
		        int rowNum = headerRowIndex + 1;
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		        int serialNo = 1; // Initialize serial number counter
		        for (CfExBondCrg resultData1 : resultData) {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                // Switch case to handle each column header
		                switch (columnsHeader[i]) {
//		                case "BOE No New":
//		                    cell.setCellValue(resultData1.getBoeNoNew() != null ? resultData1.getBoeNoNew() : "");
//		                    break;
//		                case "BOE No Old":
//		                    cell.setCellValue(resultData1.getBoeNoOld() != null ? resultData1.getBoeNoOld() : "");
//		                    break;
//		                case "BOE Date New":
//		                    cell.setCellValue(resultData1.getBoeDateNew() != null ? dateFormat.format(resultData1.getBoeDateNew()) : "");
//		                    break;
//		                case "BOE Date Old":
//		                    cell.setCellValue(resultData1.getBoeDateOld() != null ? dateFormat.format(resultData1.getBoeDateOld()) : "");
//		                    break;
//		                case "InBonding Date New":
//		                    cell.setCellValue(resultData1.getInBondingDateNew() != null ? dateFormat.format(resultData1.getInBondingDateNew()) : "");
//		                    break;
//		                case "InBonding Date Old":
//		                    cell.setCellValue(resultData1.getInBondingDateOld() != null ? dateFormat.format(resultData1.getInBondingDateOld()) : "");
//		                    break;
//		                case "Inbond pkg New":
//		                    cell.setCellValue(resultData1.getInbondPkgNew() != null ? resultData1.getInbondPkgNew().toString() : "");
//		                    break;
//		                case "Inbond pkg Old":
//		                    cell.setCellValue(resultData1.getInbondPkgOld() != null ? resultData1.getInbondPkgOld().toString() : "");
//		                    break;
//		                case "Bond No New":
//		                    cell.setCellValue(resultData1.getBondNoNew() != null ? resultData1.getBondNoNew() : "");
//		                    break;
//		                case "Bond No Old":
//		                    cell.setCellValue(resultData1.getBondNoOld() != null ? resultData1.getBondNoOld() : "");
//		                    break;
//		                case "Bond Date New":
//		                    cell.setCellValue(resultData1.getBondDateNew() != null ? dateFormat.format(resultData1.getBondDateNew()) : "");
//		                    break;
//		                case "Bond Date Old":
//		                    cell.setCellValue(resultData1.getBondDateOld() != null ? dateFormat.format(resultData1.getBondDateOld()) : "");
//		                    break;
//		                case "Inbond Cargo Duty New":
//		                    cell.setCellValue(resultData1.getInbondCargoDutyNew() != null ? resultData1.getInbondCargoDutyNew().toString() : "");
//		                    break;
//		                case "Inbond Cargo Duty Old":
//		                    cell.setCellValue(resultData1.getInbondCargoDutyOld() != null ? resultData1.getInbondCargoDutyOld().toString() : "");
//		                    break;
//		                case "Inbond CIF Value New":
//		                    cell.setCellValue(resultData1.getInbondCifValueNew() != null ? resultData1.getInbondCifValueNew().toString() : "");
//		                    break;
//		                case "Inbond CIF Value Old":
//		                    cell.setCellValue(resultData1.getInbondCifValueOld() != null ? resultData1.getInbondCifValueOld().toString() : "");
//		                    break;
//		                case "CHA New":
//		                    cell.setCellValue(resultData1.getChaNew() != null ? resultData1.getChaNew() : "");
//		                    break;
//		                case "CHA Old":
//		                    cell.setCellValue(resultData1.getChaOld() != null ? resultData1.getChaOld() : "");
//		                    break;
//		                case "Commodity Description New":
//		                    cell.setCellValue(resultData1.getCommodityDescriptionNew() != null ? resultData1.getCommodityDescriptionNew() : "");
//		                    break;
//		                case "Commodity Description Old":
//		                    cell.setCellValue(resultData1.getCommodityDescriptionOld() != null ? resultData1.getCommodityDescriptionOld() : "");
//		                    break;
//		                case "Importer Name New":
//		                    cell.setCellValue(resultData1.getImporterNameNew() != null ? resultData1.getImporterNameNew() : "");
//		                    break;
//		                case "Importer Name Old":
//		                    cell.setCellValue(resultData1.getImporterNameOld() != null ? resultData1.getImporterNameOld() : "");
//		                    break;
//		                case "Section49 New":
//		                    cell.setCellValue(resultData1.isSection49New() ? "Yes" : "No");
//		                    break;
//		                case "Section49 Old":
//		                    cell.setCellValue(resultData1.isSection49Old() ? "Yes" : "No");
//		                    break;
//		                case "Section60 New":
//		                    cell.setCellValue(resultData1.isSection60New() ? "Yes" : "No");
//		                    break;
//		                case "Section60 Old":
//		                    cell.setCellValue(resultData1.isSection60Old() ? "Yes" : "No");
//		                    break;
//		                case "Bond Validity Date New":
//		                    cell.setCellValue(resultData1.getBondValidityDateNew() != null ? dateFormat.format(resultData1.getBondValidityDateNew()) : "");
//		                    break;
//		                case "Bond Validity Date Old":
//		                    cell.setCellValue(resultData1.getBondValidityDateOld() != null ? dateFormat.format(resultData1.getBondValidityDateOld()) : "");
//		                    break;
//		                case "Trans Type":
//		                    cell.setCellValue(resultData1.getTransType() != null ? resultData1.getTransType() : "");
//		                    break;
//		                case "ExBond BOE No New":
//		                    cell.setCellValue(resultData1.getExBondBoeNoNew() != null ? resultData1.getExBondBoeNoNew() : "");
//		                    break;
//		                case "ExBond BOE No Old":
//		                    cell.setCellValue(resultData1.getExBondBoeNoOld() != null ? resultData1.getExBondBoeNoOld() : "");
//		                    break;
//		                case "ExBond BOE Date New":
//		                    cell.setCellValue(resultData1.getExBondBoeDateNew() != null ? dateFormat.format(resultData1.getExBondBoeDateNew()) : "");
//		                    break;
//		                case "ExBond BOE Date Old":
//		                    cell.setCellValue(resultData1.getExBondBoeDateOld() != null ? dateFormat.format(resultData1.getExBondBoeDateOld()) : "");
//		                    break;
//		                case "Exbonding Date New":
//		                    cell.setCellValue(resultData1.getExbondingDateNew() != null ? dateFormat.format(resultData1.getExbondingDateNew()) : "");
//		                    break;
//		                case "Exbonding Date Old":
//		                    cell.setCellValue(resultData1.getExbondingDateOld() != null ? dateFormat.format(resultData1.getExbondingDateOld()) : "");
//		                    break;
//		                case "Exbonding pkg New":
//		                    cell.setCellValue(resultData1.getExbondingPkgNew() != null ? resultData1.getExbondingPkgNew().toString() : "");
//		                    break;
//		                case "Exbonding pkg Old":
//		                    cell.setCellValue(resultData1.getExbondingPkgOld() != null ? resultData1.getExbondingPkgOld().toString() : "");
//		                    break;
//		                case "Exbond Cargo Duty":
//		                    cell.setCellValue(resultData1.getExbondCargoDuty() != null ? resultData1.getExbondCargoDuty().toString() : "");
//		                    break;
//		                case "Exbond Cargo Duty Old":
//		                    cell.setCellValue(resultData1.getExbondCargoDutyOld() != null ? resultData1.getExbondCargoDutyOld().toString() : "");
//		                    break;
//		                case "Exbond CIF Value":
//		                    cell.setCellValue(resultData1.getExbondCifValue() != null ? resultData1.getExbondCifValue().toString() : "");
//		                    break;
//		                case "Exbond CIF Value Old":
//		                    cell.setCellValue(resultData1.getExbondCifValueOld() != null ? resultData1.getExbondCifValueOld().toString() : "");
//		                    break;
		                default:
		                    cell.setCellValue("");
		                    break;
		            }




		            }
		        }

		     // Set specific column widths after populating the data
		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
		        sheet.setColumnWidth(13, 30 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(14, 40 * 306); // Set width for "Importer" (40 characters wide)

		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return null;
	}
}
