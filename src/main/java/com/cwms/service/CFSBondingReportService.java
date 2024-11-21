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
import com.cwms.entities.CfexbondcrgEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfexbondcrgEditRepository;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CompanyRepo;
import com.itextpdf.io.source.ByteArrayOutputStream;
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

	@Autowired
	private com.cwms.repository.InbondCFRepositary InbondCFRepositary;
	
	@Autowired
	private CfexbondcrgEditRepository cfexbondcrgEditRepository;
	
	
	
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
		        	    "Breakage Pkg"
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
	                    case   "Breakage Pkg" :
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
		                case   "Breakage Pkg" :
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
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
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
	
	
	
	
	
	

	
	
	public byte[] createExcelReportOfLiveBondReport(
			String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
            String section49) throws DocumentException {

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
		        
		        List<Cfinbondcrg>  resultData = cfinbondcrgRepo.getDataForInBondInLiveInBondReport(companyId, branchId, startDate, endDate,section49);
		        
		     
		      		              
		      		        
		        
		      
		        
		        
		        Sheet sheet = workbook.createSheet("IN BOND ");
		        
		        Sheet sheet2 = workbook.createSheet("EX BOND ");
		        
//		        Sheet sheet3 = workbook.createSheet("Live Bond Report");
		        
		        Sheet livebondSheet = workbook.createSheet("Live Bond Report");
		        fillLivebondSheetData(livebondSheet, startDate, endDate,companyId,branchId,section49);
		       

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
		        	    "In Bonding Date", 
		        	    "Bond No", 
		        	    "Bond Date", 
		        	    "Bond Exp Date", 
		        	    "BOE No", 
		        	    "BOE Date", 
		        	    "IGM No", 
		        	    "CHA Name", 
		        	    "Cargo Description",
		        	    "BE Weight", 
		        	    "Weight Recvd MT", 
		        	    "Value", 
		        	    "Duty"
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
		        reportTitleCell.setCellValue("IN BOND" );

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
		        	  reportTitleCell1.setCellValue("IN BOND");
		         }
		         else
		         {
		        	  reportTitleCell1.setCellValue("IN BOND Report From : " + formattedStartDate + " to " + formattedEndDate);
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
    
		                case  "In Bonding Date":
	                        if (resultData1.getInBondingDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getInBondingDate());
	                            cell.setCellStyle(dateCellStyle); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	                        
		                case "BOE No":
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

	                        
	                        
		                case  "BOE Date":
	                        if (resultData1.getBoeDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBoeDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
		                case  "Bond Exp Date":
	                        if (resultData1.getBondValidityDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getBondValidityDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
	             
		                case "IGM No":
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
		                case "CHA Name":
		                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
		                    break;
		            
		                    
//		                case "Inbond Pkg":
//	                        BigDecimal inbondPkgs = resultData1.getInBondedPackagesDtl();
//	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
//	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
//	                        cell.setCellStyle(numberCellStyle);
//	                        break;
//	                        
		                case "BE Weight":
	                        BigDecimal inbondWeight = resultData1.getInbondGrossWtDtl();
	                        totalInbondWeight = totalInbondWeight.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Value":
	                        BigDecimal inbondAssetValue = resultData1.getInbondCifValue();
	                        totalInbondAssetValue = totalInbondAssetValue.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Duty":
	                        BigDecimal inbondDutyValue = resultData1.getInbondCargoDuty();
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
		            
		            case "Cargo Description":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle); // Apply centered style
		            
                    break;
		                case "Inbond Pkg":
		                    totalCell.setCellValue(totalInbondPkgs.doubleValue());
		                    break;
		                case "BE Weight":
		                    totalCell.setCellValue(totalInbondWeight.doubleValue());
		                    break;
		                case "Value":
		                    totalCell.setCellValue(totalInbondAssetValue.doubleValue());
		                    break;
		                case "Duty":
		                    totalCell.setCellValue(totalInbondDutyValue.doubleValue());
		                    break;
		                case "Short Pkgs":
		                    totalCell.setCellValue(totalShortPkgs.doubleValue());
		                    break;
		                case "Damage Pkg":
		                    totalCell.setCellValue(totalDamagedWeight.doubleValue());
		                    break;
		                case   "Breakage Pkg" :
		                    totalCell.setCellValue(totalBrakageValue.doubleValue());
		                    break;
		             
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		    
		        sheet.setColumnWidth(0,  9 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(1, 27 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(2, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(3, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(4, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(5, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(6, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(7, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(8, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(9, 27 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(10, 14 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(13, 18 * 306); // Set width for "Importer" (40 characters wide)
		       
		     // Apply autofilter to the header row
		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));

		        // Freeze the header row
		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

		        String[] columnsHeader2 = {
		        		"Sr No", 
		        	    "Bond No", 
		        	    "Bond Date", 
		        	    "Ex BOE No", 
		        	    "Ex BOE Date", 
		        	    "IGM", 
		        	    "IGM Date", 
		        	    "Weight MT", 
		        	    "Assessed Value", 
		        	    "Duty"
		        	};

		        // Add Company Name (Centered)
		        Row companyRow1 = sheet2.createRow(0);
		        Cell companyCell1 = companyRow1.createCell(0);
		        companyCell1.setCellValue(companyName);

		        // Create and style for centering
		        CellStyle companyStyle1 = workbook.createCellStyle();
		        companyStyle1.setAlignment(HorizontalAlignment.CENTER);
		        companyStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font companyFont1 = workbook.createFont();
		        companyFont1.setBold(true);
		        companyFont1.setFontHeightInPoints((short)18);
		        companyStyle1.setFont(companyFont1);
		        companyCell1.setCellStyle(companyStyle1);
		        sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsHeader2.length - 1));

		        // Add Branch Address (Centered)
		        Row branchRow11 = sheet2.createRow(1);
		        Cell branchCell11 = branchRow11.createCell(0);
		        branchCell11.setCellValue(branchAdd);
		        CellStyle branchStyle11 = workbook.createCellStyle();
		        branchStyle11.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont11 = workbook.createFont();
		        branchFont11.setFontHeightInPoints((short) 12);
		        branchStyle11.setFont(branchFont11);
		        branchCell11.setCellStyle(branchStyle11);
		        sheet2.addMergedRegion(new CellRangeAddress(1, 1, 0, columnsHeader2.length - 1));

		        
		        Row branchRow111 = sheet2.createRow(2);
		        Cell branchCell111 = branchRow111.createCell(0);
//		        branchCell1.setCellValue(branchAdd);
		        CellStyle branchStyle111 = workbook.createCellStyle();
		        branchStyle111.setAlignment(HorizontalAlignment.CENTER);
		        branchStyle111.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font branchFont111 = workbook.createFont();
		        branchFont111.setFontHeightInPoints((short) 12);
		        branchStyle111.setFont(branchFont111);
		        branchCell111.setCellStyle(branchStyle111);
		        sheet2.addMergedRegion(new CellRangeAddress(2, 2, 0, columnsHeader2.length - 1));
	
		        // Add Report Title "Bond cargo Inventory Report"
		        Row reportTitleRow11 = sheet2.createRow(3);
		        Cell reportTitleCell11 = reportTitleRow11.createCell(0);
		        reportTitleCell11.setCellValue("EX BOND" );

		        // Set alignment and merge cells for the heading
		        CellStyle reportTitleStyle11 = workbook.createCellStyle();
		        reportTitleStyle11.setAlignment(HorizontalAlignment.CENTER);
		        reportTitleStyle11.setVerticalAlignment(VerticalAlignment.CENTER);
		        Font reportTitleFont11 = workbook.createFont();
		        reportTitleFont11.setBold(true);
		        reportTitleFont11.setFontHeightInPoints((short) 16);
		        reportTitleFont11.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
		        reportTitleStyle11.setFont(reportTitleFont11);
		        reportTitleCell11.setCellStyle(reportTitleStyle11);
		        sheet2.addMergedRegion(new CellRangeAddress(3, 3, 0, columnsHeader2.length - 1));
		        
		        
		        Row reportTitleRow111 = sheet2.createRow(4);
		        Cell reportTitleCell111 = reportTitleRow111.createCell(0);
		         if(startDate ==null && endDate ==null)
		         {
		        	  reportTitleCell111.setCellValue("EX BOND");
		         }
		         else
		         {
		        	  reportTitleCell111.setCellValue("EX BOND Report From : " + formattedStartDate + " to " + formattedEndDate);
		         }
		      

		        // Create and set up the CellStyle for the report title
		        CellStyle reportTitleStyle111 = workbook.createCellStyle();
		        reportTitleStyle111.setAlignment(HorizontalAlignment.LEFT); // Set alignment

		        // Create the font and set its properties
		        Font reportTitleFont111 = workbook.createFont();
		        reportTitleFont111.setBold(true); // Make font bold
		        reportTitleFont111.setFontHeightInPoints((short) 12); // Set font size

		        // Apply the font to the CellStyle
		        reportTitleStyle111.setFont(reportTitleFont111);

		        // Set the style to the cell
		        reportTitleCell111.setCellStyle(reportTitleStyle111);
		     // Create a font and set its properties
		    

		        
		        // Set headers after the title
		        int headerRowIndex1 = 6; // Adjusted for the title rows above
		        Row headerRow1 = sheet2.createRow(headerRowIndex1);
		        
		        CellStyle borderStyle1 = workbook.createCellStyle();
		        borderStyle1.setAlignment(HorizontalAlignment.CENTER);
		        borderStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
		        borderStyle1.setBorderBottom(BorderStyle.THIN);
		        borderStyle1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle1.setBorderTop(BorderStyle.THIN);
		        borderStyle1.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle1.setBorderLeft(BorderStyle.THIN);
		        borderStyle1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderStyle1.setBorderRight(BorderStyle.THIN);
		        borderStyle1.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        Font boldFont11 = workbook.createFont();
		        boldFont11.setBold(true);
		        boldFont11.setFontHeightInPoints((short) 16);

		        for (int i = 0; i < columnsHeader2.length; i++) {
		            Cell cell = headerRow1.createCell(i);
		            cell.setCellValue(columnsHeader2[i]);

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
		            int headerWidth = (int) (columnsHeader2[i].length() * 306 * widthFactor);
		            sheet2.setColumnWidth(i, headerWidth);
		        }

		        // Populate data rows
		        int rowNum1 = headerRowIndex1 + 1;
		        BigDecimal totalInbondPkgs1 = BigDecimal.ZERO;
		        BigDecimal totalInbondWeight1 = BigDecimal.ZERO;
		        BigDecimal totalInbondAssetValue1 = BigDecimal.ZERO;
		        BigDecimal totalInbondDutyValue1 = BigDecimal.ZERO;
		        BigDecimal totalShortPkgs1 = BigDecimal.ZERO;
		        BigDecimal totalDamagedWeight1 = BigDecimal.ZERO;
		        BigDecimal totalBrakageValue1 = BigDecimal.ZERO;
		       
		        List<CfExBondCrg>  resultData2 = cfExBondCrgRepository.getDataForExbondReportInLiveBondReport(companyId, branchId, startDate, endDate,section49);
 
		           
		        int serialNo1 = 1; // Initialize serial number counter
		        for (CfExBondCrg resultData1 : resultData2) {
		            Row dataRow = sheet2.createRow(rowNum1++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader2.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle1);

		                // Switch case to handle each column header
		                switch (columnsHeader2[i]) {
		                
//		                
//		                "Sr No", 
//		        	    "Bond No", 
//		        	    "Bond Date", 
//		        	    "Ex BOE No", 
//		        	    "Ex BOE Date", 
//		        	    "IGM", 
//		        	    "IGM Date", 
//		        	    "Weight MT", 
//		        	    "Assessed Value", 
//		        	    "Duty"
		        	    
		                case "Sr No":
		                    cell.setCellValue(serialNo1++); // Serial Number increment
		                    break;


	                        
	                        
		                case "Bond No":
		                    cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
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
	                        
		                case "Ex BOE Date":
	                        if (resultData1.getExBondBeDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getExBondBeDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
	                        
		                case "IGM":
		                    cell.setCellValue(resultData1.getIgmNo() != null ? resultData1.getIgmNo() : "");
		                    break;
		                    
		                case "IGM Date":
	                        if (resultData1.getIgmDate() != null) 
	                        {
	                            cell.setCellValue(resultData1.getIgmDate());
	                            cell.setCellStyle(dateCellStyle1); // Apply date format
	                        } else {
	                            cell.setBlank();
	                        }
	                        break;
		                case "Ex BOE No":
		                    cell.setCellValue(resultData1.getExBondBeNo() != null ? resultData1.getExBondBeNo().toString() : "");
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
		              
		                    
//		                case "Inbond Pkg":
//	                        BigDecimal inbondPkgs = resultData1.getInBondedPackagesDtl();
//	                        totalInbondPkgs = totalInbondPkgs.add(inbondPkgs != null ? inbondPkgs : BigDecimal.ZERO);
//	                        cell.setCellValue(inbondPkgs != null ? inbondPkgs.doubleValue() : 0);
//	                        cell.setCellStyle(numberCellStyle);
//	                        break;
//	                        
		                case "Weight MT":
	                        BigDecimal inbondWeight = resultData1.getExBondedGWdtl();
	                        totalInbondWeight1 = totalInbondWeight1.add(inbondWeight != null ? inbondWeight : BigDecimal.ZERO);
	                        cell.setCellValue(inbondWeight != null ? inbondWeight.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                        
	                    case "Assessed Value":
	                        BigDecimal inbondAssetValue = resultData1.getExBondedCIFdtl();
	                        totalInbondAssetValue1 = totalInbondAssetValue1.add(inbondAssetValue != null ? inbondAssetValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondAssetValue != null ? inbondAssetValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
	                    case "Duty":
	                        BigDecimal inbondDutyValue = resultData1.getExBondedCargoDutydtl();
	                        totalInbondDutyValue1 = totalInbondDutyValue1.add(inbondDutyValue != null ? inbondDutyValue : BigDecimal.ZERO);
	                        cell.setCellValue(inbondDutyValue != null ? inbondDutyValue.doubleValue() : 0);
	                        cell.setCellStyle(numberCellStyle);
	                        break;
//	                       
//	                        
//	                    case "Short Pkgs":
//	                        BigDecimal shortage = resultData1.getShortagePackages();
//	                        totalShortPkgs = totalShortPkgs.add(shortage != null ? shortage : BigDecimal.ZERO);
//	                        cell.setCellValue(shortage != null ? shortage.doubleValue() : 0);
//	                        cell.setCellStyle(numberCellStyle);
//	                        break;
//	                    case "Damage Pkg":
//	                        BigDecimal dmg = resultData1.getDamagedQty();
//	                        totalDamagedWeight = totalDamagedWeight.add(dmg != null ? dmg : BigDecimal.ZERO);
//	                        cell.setCellValue(dmg != null ? dmg.doubleValue() : 0);
//	                        cell.setCellStyle(numberCellStyle);
//	                        break;
//	                    case   "Breakage Pkg" :
//	                        BigDecimal brakage = resultData1.getBreakage();
//	                        totalBrakageValue = totalBrakageValue.add(brakage != null ? brakage : BigDecimal.ZERO);
//	                        cell.setCellValue(brakage != null ? brakage.doubleValue() : 0);
//	                        cell.setCellStyle(numberCellStyle);
//	                        break;
	      
			        	  
	                        
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
		        Row emptyRow1 = sheet2.createRow(rowNum1++);
		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

		     // Create a row for totals
		        Row totalRow1 = sheet2.createRow(rowNum1++);

		     // Create a CellStyle for the background color
		        CellStyle totalRowStyle1 = sheet2.getWorkbook().createCellStyle();
		        totalRowStyle1.cloneStyleFrom(numberCellStyle); // Copy base style
		        totalRowStyle1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
		        totalRowStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Create a font for bold text
		        Font boldFont111 = workbook.createFont();
		        boldFont111.setBold(true);
		        totalRowStyle1.setFont(boldFont111);

		        // Add "Total" label to the first cell and apply the style
		        Cell totalLabelCell1 = totalRow1.createCell(0);
		        totalLabelCell1.setCellValue("Total");
		        totalLabelCell1.setCellStyle(totalRowStyle1); // Apply the background color and bold style

		        CellStyle centerStyle1 = workbook.createCellStyle();
		        centerStyle1.cloneStyleFrom(totalRowStyle1); // Use totalRowStyle as base (light green background)
		        centerStyle1.setAlignment(HorizontalAlignment.CENTER); // Center the text
		        centerStyle1.setVerticalAlignment(VerticalAlignment.CENTER); // Center the text vertically if necessary

		     
		        for (int i = 0; i < columnsHeader2.length; i++) {
		            Cell totalCell = totalRow1.createCell(i);
		            totalCell.setCellStyle(numberCellStyle);
		            totalCell.setCellStyle(totalRowStyle1); // Set the total row style
		            
		            switch (columnsHeader2[i]) {
		            
		            case "IGM Date":
		            	 totalCell.setCellValue("Total");
		                 totalCell.setCellStyle(centerStyle1); // Apply centered style
		            
                    break;
		                case "Inbond Pkg":
		                    totalCell.setCellValue(totalInbondPkgs1.doubleValue());
		                    break;
		                case "Weight MT":
		                    totalCell.setCellValue(totalInbondWeight1.doubleValue());
		                    break;
		                case "Assessed Value":
		                    totalCell.setCellValue(totalInbondAssetValue1.doubleValue());
		                    break;
		                case "Duty":
		                    totalCell.setCellValue(totalInbondDutyValue1.doubleValue());
		                    break;
		                case "Short Pkgs":
		                    totalCell.setCellValue(totalShortPkgs1.doubleValue());
		                    break;
		                case "Damage Pkg":
		                    totalCell.setCellValue(totalDamagedWeight1.doubleValue());
		                    break;
		                case   "Breakage Pkg" :
		                    totalCell.setCellValue(totalBrakageValue1.doubleValue());
		                    break;
		             
		                default:
		                    totalCell.setCellValue(""); // Leave cells blank for non-numeric columns
		                    break;
		            }
		        }

		     // Set specific column widths after populating the data
		        sheet2.setColumnWidth(0,  9 * 306); 
		        sheet2.setColumnWidth(1, 22 * 306);
		        sheet2.setColumnWidth(2, 18 * 306); 
		        sheet2.setColumnWidth(3, 14 * 306); 
		        sheet2.setColumnWidth(4, 14 * 306); 
		        sheet2.setColumnWidth(5, 14 * 306); 
		        sheet2.setColumnWidth(6, 14 * 306); 
		        sheet2.setColumnWidth(7, 14 * 306); 
		        sheet2.setColumnWidth(8, 36 * 306);
		        sheet2.setColumnWidth(9, 27 * 306); 
		      
		        
		     // Apply autofilter to the header row
		        sheet2.setAutoFilter(new CellRangeAddress(headerRowIndex1, headerRowIndex1, 0, columnsHeader2.length - 1));

		        // Freeze the header row
		        sheet2.createFreezePane(0, headerRowIndex1 + 1); // Freeze rows above the 6th row

		        
		        workbook.write(outputStream);
		        return outputStream.toByteArray();

		    } catch (IOException e) 
		    {
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
	
	public ResponseEntity<List<CfexbondcrgEdit>> showAuditTrailReport(
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

        List<CfexbondcrgEdit> resultData = cfexbondcrgEditRepository.getdataForAuditTrailReport(companyId, branchId, startDate,endDate,boeNo);

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
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String boeNo
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
		    
		        
		       

		        Sheet sheet = workbook.createSheet("AuditTrail Report");

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

		        

		        List<CfexbondcrgEdit> resultData = cfexbondcrgEditRepository.getdataForAuditTrailReport(companyId, branchId, startDate,endDate,boeNo);

		     


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
		        		        
		        Row reportTitleRow1 = sheet.createRow(4);
		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
		        if(formattedStartDate.equals("N/A"))
		        {
		        	 reportTitleCell1.setCellValue("AuditTrail Report : " + formattedEndDate);
		        }
		        else 
		        {
		        	 reportTitleCell1.setCellValue("AuditTrail Report : " + formattedStartDate + " to " + formattedEndDate);
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
		        for (CfexbondcrgEdit resultData1 : resultData) 
		        {
		            Row dataRow = sheet.createRow(rowNum++);
		    
		            int cellNum = 0;

		            for (int i = 0; i < columnsHeader.length; i++) {
		                Cell cell = dataRow.createCell(i);
		                cell.setCellStyle(borderStyle);

		                switch (columnsHeader[i]) {
		
		                case "BOE No New":
		                    cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
		                    break;
		                    
		                    
		                case "BOE No Old":
		                    cell.setCellValue(resultData1.getBoeNoOld() != null ? resultData1.getBoeNoOld() : "");
		                    break;
		                    
		        
		                  

		                case "BOE Date Old":
		                    if (resultData1.getBoeDateOld() != null) {
		                        cell.setCellValue(resultData1.getBoeDateOld());
		                        cell.setCellStyle(dateCellStyle1); // Apply date format
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;

		                case "InBonding Date Old":
		                    if (resultData1.getBoeDateOld() != null) {
		                        cell.setCellValue(resultData1.getBoeDateOld());
		                        cell.setCellStyle(dateCellStyle1);
		                    } else {
		                        cell.setBlank();
		                    }
		                    break;
		               
		         
		                case "CHA Old":
		                    cell.setCellValue(resultData1.getChaOld() != null ? resultData1.getChaOld() : "");
		                    break;
		                case "Commodity Description New":
		                    cell.setCellValue(resultData1.getNewCommodityDescription() != null ? resultData1.getNewCommodityDescription() : "");
		                    break;
		                case "Commodity Description Old":
		                    cell.setCellValue(resultData1.getCommodityDescriptionOld() != null ? resultData1.getCommodityDescriptionOld() : "");
		                    break;
		           
		                case "Importer Name Old":
		                    cell.setCellValue(resultData1.getImporterNameOld() != null ? resultData1.getImporterNameOld() : "");
		                    break;
		                case "Section49 New":
		                    cell.setCellValue(resultData1.getSection49().equals("Y") ? "YES" : "NO");
		                    break;
		                case "Section49 Old":
		                    cell.setCellValue(resultData1.getSection49Old().equals("Y") ? "YES" : "NO");
		                    break;
		                case "Section60 New":
		                    cell.setCellValue(resultData1.getSection60().equals("Y") ? "YES" : "NO");
		                    break;
		                case "Section60 Old":
		                    cell.setCellValue(resultData1.getSection60Old().equals("Y") ? "YES" : "NO");
		                    break;
		   
		                case "Trans Type":
		                    cell.setCellValue(resultData1.getType() != null ? resultData1.getType() : "");
		                    break;
		              
		                  
		                default:
		                    if ("EXBOND".equals(resultData1.getType())) 
		                    {
		                        switch (columnsHeader[i]) {
		                            case "Exbonding pkg New":
		                                cell.setCellValue(resultData1.getNewBondPackages() != null ? resultData1.getNewBondPackages().doubleValue() : 0);
		                                cell.setCellStyle(numberCellStyle);
		                                break;
		                            case "Exbonding pkg Old":
		                                cell.setCellValue(resultData1.getOldBondPackages() != null ? resultData1.getOldBondPackages().doubleValue() : 0);
		                                cell.setCellStyle(numberCellStyle);
		                                break;
		                            case "Exbond Cargo Duty":
		                                cell.setCellValue(resultData1.getNewBondCargoDuty() != null ? resultData1.getNewBondCargoDuty().doubleValue() : 0);
		                                cell.setCellStyle(numberCellStyle);
		                                break;
		                            case "Exbond Cargo Duty Old":
		                                cell.setCellValue(resultData1.getOldBondCargoDuty() != null ? resultData1.getOldBondCargoDuty().doubleValue() : 0);
		                                cell.setCellStyle(numberCellStyle);
		                                break;
		                            case "Exbond CIF Value":
		                                cell.setCellValue(resultData1.getNewBondCifValue() != null ? resultData1.getNewBondCifValue().doubleValue() : 0);
		                                cell.setCellStyle(numberCellStyle);
		                                break;
		                                
		                            case "Exbond CIF Value Old":
		                                cell.setCellValue(resultData1.getOldBondCifValue() != null ? resultData1.getNewBondCifValue().doubleValue() : 0);
		                                cell.setCellStyle(numberCellStyle);
		                                break;
		                                
		                                
		                            case "ExBond BOE No New":
		    		                    cell.setCellValue(resultData1.getExBondBeNo() != null ? resultData1.getExBondBeNo() : "");
		    		                    break;
		    		                case "ExBond BOE No Old":
		    		                    cell.setCellValue(resultData1.getExBondBeNoOld() != null ? resultData1.getExBondBeNoOld() : "");
		    		                    break;
		    		                case "ExBond BOE Date New":
		    		                    if (resultData1.getExBondBeDate() != null) {
		    		                        cell.setCellValue(resultData1.getExBondBeDate());
		    		                        cell.setCellStyle(dateCellStyle1);
		    		                    } else {
		    		                        cell.setBlank();
		    		                    }
		    		                    break;
		    		                case "ExBond BOE Date Old":
		    		                    if (resultData1.getExBondBeDateOld() != null) {
		    		                        cell.setCellValue(resultData1.getExBondBeDateOld());
		    		                        cell.setCellStyle(dateCellStyle1);
		    		                    } else {
		    		                        cell.setBlank();
		    		                    }
		    		                    break;
		    		                case "Exbonding Date New":
		    		                    if (resultData1.getExBondBeDate() != null) {
		    		                        cell.setCellValue(resultData1.getExBondBeDate());
		    		                        cell.setCellStyle(dateCellStyle1);
		    		                    } else {
		    		                        cell.setBlank();
		    		                    }
		    		                    break;
		    		                case "Exbonding Date Old":
		    		                    if (resultData1.getExBondBeDateOld() != null) {
		    		                        cell.setCellValue(resultData1.getExBondBeDateOld());
		    		                        cell.setCellStyle(dateCellStyle1);
		    		                    } else {
		    		                        cell.setBlank();
		    		                    } 
		    		                    break;
		                        }
		                    }
		                    
		                    if ("INBOND".equals(resultData1.getType())) {
		                        switch (columnsHeader[i]) {
		          
		                        case "CHA New":
				                    cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
				                    break;
				                    
		                        case "Importer Name New":
				                    cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
				                    break;
				                    
		                        case "Inbond pkg New":
				                    cell.setCellValue(resultData1.getNewBondPackages() != null ? resultData1.getNewBondPackages().doubleValue() : 0);
				                    cell.setCellStyle(numberCellStyle);
				                    break;
				                case "Inbond pkg Old":
				                    cell.setCellValue(resultData1.getOldBondPackages() != null ? resultData1.getOldBondPackages().doubleValue() : 0);
				                    cell.setCellStyle(numberCellStyle);
				                    break;
				                case "Bond No New":
				                    cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
				                    break;
				                case "Bond No Old":
				                    cell.setCellValue(resultData1.getBondingNoOld() != null ? resultData1.getBondingNoOld() : "");
				                    break;
				                case "Bond Date New":
				                    if (resultData1.getBondingDate() != null) {
				                        cell.setCellValue(resultData1.getBondingDate());
				                        cell.setCellStyle(dateCellStyle1);
				                    } else {
				                        cell.setBlank();
				                    }
				                    break;
				                case "Bond Date Old":
				                    if (resultData1.getBondingDateOld() != null) {
				                        cell.setCellValue(resultData1.getBondingDateOld());
				                        cell.setCellStyle(dateCellStyle1);
				                    } else {
				                        cell.setBlank();
				                    }
				                    break;
				                case "Inbond Cargo Duty New":
				                    cell.setCellValue(resultData1.getNewBondCargoDuty() != null ? resultData1.getNewBondCargoDuty().doubleValue() : 0);
				                    cell.setCellStyle(numberCellStyle);
				                    break;
				                case "Inbond Cargo Duty Old":
				                    cell.setCellValue(resultData1.getOldBondCargoDuty() != null ? resultData1.getOldBondCargoDuty().doubleValue() : 0);
				                    cell.setCellStyle(numberCellStyle);
				                    break;
				                case "Inbond CIF Value New":
				                    cell.setCellValue(resultData1.getNewBondCifValue() != null ? resultData1.getNewBondCifValue().doubleValue() : 0);
				                    cell.setCellStyle(numberCellStyle);
				                    break;
				                case "Inbond CIF Value Old":
				                    cell.setCellValue(resultData1.getOldBondCifValue() != null ? resultData1.getOldBondCifValue().doubleValue() : 0);
				                    cell.setCellStyle(numberCellStyle);
				                    break;
				                case "Bond Validity Date New":
				                    if (resultData1.getBondValidityDate() != null) {
				                        cell.setCellValue(resultData1.getBondValidityDate());
				                        cell.setCellStyle(dateCellStyle1);
				                    } else {
				                        cell.setBlank();
				                    }
				                    break;
				                case "Bond Validity Date Old":
				                    if (resultData1.getBondValidityDateOld() != null) 
				                    {
				                        cell.setCellValue(resultData1.getBondValidityDateOld());
				                        cell.setCellStyle(dateCellStyle1);
				                    } else {
				                        cell.setBlank();
				                    }
				                    break;
				                    
				                case "BOE Date New":
				                    if (resultData1.getBoeDate() != null) {
				                        cell.setCellValue(resultData1.getBoeDate());
				                        cell.setCellStyle(dateCellStyle1); // Apply date format
				                    } else {
				                        cell.setBlank();
				                    }
				                    break;
				                    
				                case "InBonding Date New":
				                    if (resultData1.getBoeDate() != null) {
				                        cell.setCellValue(resultData1.getBoeDate());
				                        cell.setCellStyle(dateCellStyle1);
				                    } else {
				                        cell.setBlank();
				                    }
				                    break;
		                        }
		                    }
		            }

		                
//		                switch (columnsHeader[i]) 
//		                {
//		                    case "BOE No New":
//		                        cell.setCellValue(resultData1.getBoeNo() != null ? resultData1.getBoeNo() : "");
//		                        break;
//		                    case "BOE No Old":
//		                        cell.setCellValue(resultData1.getBoeNoOld() != null ? resultData1.getBoeNoOld() : "");
//		                        break;
//		                    case "BOE Date New":
//		                        if (resultData1.getBoeDate() != null) {
//		                            cell.setCellValue(resultData1.getBoeDate());
//		                            cell.setCellStyle(dateCellStyle1); // Apply date format
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "BOE Date Old":
//		                        if (resultData1.getBoeDateOld() != null) {
//		                            cell.setCellValue(resultData1.getBoeDateOld());
//		                            cell.setCellStyle(dateCellStyle1); // Apply date format
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "InBonding Date New":
//		                        if (resultData1.getBoeDate() != null) {
//		                            cell.setCellValue(resultData1.getBoeDate());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "InBonding Date Old":
//		                        if (resultData1.getBoeDateOld() != null) {
//		                            cell.setCellValue(resultData1.getBoeDateOld());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Inbond pkg New":
//		                        cell.setCellValue(resultData1.getNewBondPackages() != null ? resultData1.getNewBondPackages().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Inbond pkg Old":
//		                        cell.setCellValue(resultData1.getOldBondPackages() != null ? resultData1.getOldBondPackages().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Bond No New":
//		                        cell.setCellValue(resultData1.getBondingNo() != null ? resultData1.getBondingNo() : "");
//		                        break;
//		                    case "Bond No Old":
//		                        cell.setCellValue(resultData1.getBondingNoOld() != null ? resultData1.getBondingNoOld() : "");
//		                        break;
//		                    case "Bond Date New":
//		                        if (resultData1.getBondingDate() != null) {
//		                            cell.setCellValue(resultData1.getBondingDate());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Bond Date Old":
//		                        if (resultData1.getBondingDateOld() != null) {
//		                            cell.setCellValue(resultData1.getBondingDateOld());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Inbond Cargo Duty New":
//		                        cell.setCellValue(resultData1.getNewBondCargoDuty() != null ? resultData1.getNewBondCargoDuty().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Inbond Cargo Duty Old":
//		                        cell.setCellValue(resultData1.getOldBondCargoDuty() != null ? resultData1.getOldBondCargoDuty().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Inbond CIF Value New":
//		                        cell.setCellValue(resultData1.getNewBondCifValue() != null ? resultData1.getNewBondCifValue().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Inbond CIF Value Old":
//		                        cell.setCellValue(resultData1.getOldBondCifValue() != null ? resultData1.getOldBondCifValue().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "CHA New":
//		                        cell.setCellValue(resultData1.getCha() != null ? resultData1.getCha() : "");
//		                        break;
//		                    case "CHA Old":
//		                        cell.setCellValue(resultData1.getChaOld() != null ? resultData1.getChaOld() : "");
//		                        break;
//		                    case "Commodity Description New":
//		                        cell.setCellValue(resultData1.getNewCommodityDescription() != null ? resultData1.getNewCommodityDescription() : "");
//		                        break;
//		                    case "Commodity Description Old":
//		                        cell.setCellValue(resultData1.getCommodityDescriptionOld() != null ? resultData1.getCommodityDescriptionOld() : "");
//		                        break;
//		                    case "Importer Name New":
//		                        cell.setCellValue(resultData1.getImporterName() != null ? resultData1.getImporterName() : "");
//		                        break;
//		                    case "Importer Name Old":
//		                        cell.setCellValue(resultData1.getImporterNameOld() != null ? resultData1.getImporterNameOld() : "");
//		                        break;
//		                    case "Section49 New":
//		                        cell.setCellValue(resultData1.getSection49().equals("Y") ? "YES" : "NO");
//		                        break;
//		                    case "Section49 Old":
//		                        cell.setCellValue(resultData1.getSection49Old().equals("Y") ? "YES" : "NO");
//		                        break;
//		                    case "Section60 New":
//		                        cell.setCellValue(resultData1.getSection60().equals("Y") ? "YES" : "NO");
//		                        break;
//		                    case "Section60 Old":
//		                        cell.setCellValue(resultData1.getSection60Old().equals("Y") ? "YES" : "NO");
//		                        break;
//		                    case "Bond Validity Date New":
//		                        if (resultData1.getBondValidityDate() != null) {
//		                            cell.setCellValue(resultData1.getBondValidityDate());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Bond Validity Date Old":
//		                        if (resultData1.getBondValidityDateOld() != null) {
//		                            cell.setCellValue(resultData1.getBondValidityDateOld());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Trans Type":
//		                        cell.setCellValue(resultData1.getType() != null ? resultData1.getType() : "");
//		                        break;
//		                    case "ExBond BOE No New":
//		                        cell.setCellValue(resultData1.getExBondBeNo() != null ? resultData1.getExBondBeNo() : "");
//		                        break;
//		                    case "ExBond BOE No Old":
//		                        cell.setCellValue(resultData1.getExBondBeNoOld() != null ? resultData1.getExBondBeNoOld() : "");
//		                        break;
//		                    case "ExBond BOE Date New":
//		                        if (resultData1.getExBondBeDate() != null) {
//		                            cell.setCellValue(resultData1.getExBondBeDate());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "ExBond BOE Date Old":
//		                        if (resultData1.getExBondBeDateOld() != null) {
//		                            cell.setCellValue(resultData1.getExBondBeDateOld());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Exbonding Date New":
//		                        if (resultData1.getExBondBeDate() != null) {
//		                            cell.setCellValue(resultData1.getExBondBeDate());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Exbonding Date Old":
//		                        if (resultData1.getExBondBeDateOld() != null) {
//		                            cell.setCellValue(resultData1.getExBondBeDateOld());
//		                            cell.setCellStyle(dateCellStyle1);
//		                        } else {
//		                            cell.setBlank();
//		                        }
//		                        break;
//		                    case "Exbonding pkg New":
//		                        cell.setCellValue(resultData1.getNewBondPackages() != null ? resultData1.getNewBondPackages().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Exbonding pkg Old":
//		                        cell.setCellValue(resultData1.getOldBondPackages() != null ? resultData1.getOldBondPackages().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Exbond Cargo Duty":
//		                        cell.setCellValue(resultData1.getNewBondCargoDuty() != null ? resultData1.getNewBondCargoDuty().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Exbond Cargo Duty Old":
//		                        cell.setCellValue(resultData1.getOldBondCargoDuty() != null ? resultData1.getOldBondCargoDuty().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Exbond CIF Value":
//		                        cell.setCellValue(resultData1.getNewBondCifValue() != null ? resultData1.getNewBondCifValue().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                    case "Exbond CIF Value Old":
//		                        cell.setCellValue(resultData1.getOldBondCifValue() != null ? resultData1.getOldBondCifValue().doubleValue() : 0);
//		                        cell.setCellStyle(numberCellStyle);
//		                        break;
//		                  
//		                    default:
//		                        cell.setCellValue(""); // Handle undefined columns if necessary
//		                        break;
//		                }

		                
		          
		            }
		        }
		        
		        
		      
		       
		
		        
		        
		        

		        sheet.setColumnWidth(0,  18 * 306); 
		        sheet.setColumnWidth(1, 14 * 306); 
		        sheet.setColumnWidth(2, 18 * 306); 
		        sheet.setColumnWidth(5, 18 * 306); 
		        sheet.setColumnWidth(6, 18 * 306); 
		        
		        sheet.setColumnWidth(7, 18 * 306); 
		        sheet.setColumnWidth(8, 18 * 306); 
		        sheet.setColumnWidth(9, 14 * 306); 
		        
		        sheet.setColumnWidth(11, 27 * 306); 
		        
		        sheet.setColumnWidth(12, 27 * 306); 
		        sheet.setColumnWidth(13, 36 * 306); 
		        sheet.setColumnWidth(14, 40 * 306); 
		        
		        sheet.setColumnWidth(15, 18 * 306); 
		        sheet.setColumnWidth(16, 36 * 306); 
		        sheet.setColumnWidth(17, 36 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(18, 36 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(19, 36 * 306); // Set width for "CHA" (30 characters wide)
		        sheet.setColumnWidth(20, 36 * 306); // Set width for "Importer" (40 characters wide)
		        sheet.setColumnWidth(21, 36 * 306); // Set width for "Importer" (40 characters wide)

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	public  Date getStartDateOfPreviousMonth(Date date, int numberOfMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -numberOfMonths);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public  Date getLastDateOfMonth(Date date, int numberOfMonths) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -numberOfMonths);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    
    private Date findPreviousAprilDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        // If the current month is April, return the first day of the month
        if (cal.get(Calendar.MONTH) == Calendar.APRIL) {
            cal.set(Calendar.DAY_OF_MONTH, 1);
            return cal.getTime();
        }

        // Move to the previous month until April is found
        while (cal.get(Calendar.MONTH) != Calendar.APRIL) {
            cal.add(Calendar.MONTH, -1);
        }

        // Set the day of the month to 1
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }
    
    
    private Date findLastDateOfPreviousMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        // Move to the previous month
        cal.add(Calendar.MONTH, -1);
        
        // Set the day of the month to the last day of the month
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        return cal.getTime();
    }
    
    
	public Sheet fillLivebondSheetData(Sheet livebondSheet, Date startDate, Date endDate, String company, String branch,String section49) {

        try {
            Date aprilDate = new Date();
            if (startDate != null) {
                aprilDate = findPreviousAprilDate(startDate);
            }
            Date start13 = getStartDateOfPreviousMonth(startDate, 3);
            Date lastDateOfPreviousMonth = findLastDateOfPreviousMonth(startDate);

            Date start36 = getStartDateOfPreviousMonth(startDate, 6);
            Date end36 = getLastDateOfMonth(startDate, 4);

            Date start612 = getStartDateOfPreviousMonth(startDate, 12);
            Date end612 = getLastDateOfMonth(startDate, 7);

            Date start12y = getStartDateOfPreviousMonth(startDate, 24);
            Date end12y = getLastDateOfMonth(startDate, 13);

            Date start23y = getStartDateOfPreviousMonth(startDate, 36);
            Date end23y = getLastDateOfMonth(startDate, 15);

            List<Object[]> getLiveOpeningBalance1 = InbondCFRepositary.getLiveOpeningBalance(company, branch, startDate,
                    endDate,section49);
            System.out.println("hiii1");
            
            System.out.println("getLiveOpeningBalance1______________getLiveOpeningBalance1"+getLiveOpeningBalance1.size()); 

            List<Object[]> getLiveReceiptsAndDisposal1 = InbondCFRepositary.getLiveReceiptsAndDisposal(company,
                    branch, startDate, endDate, aprilDate, lastDateOfPreviousMonth,section49);

            List<Object[]> getLiveBreckUp1 = InbondCFRepositary.getLiveBreckUp(company, branch, endDate, startDate,
                    endDate, start13, lastDateOfPreviousMonth, start36, end36, start612, end612, start12y, end12y,
                    start23y, end23y, start23y);

            Object[] d1 = getLiveOpeningBalance1.get(0);
            Object[] d2 = getLiveReceiptsAndDisposal1.get(0);
            Object[] d3 = getLiveReceiptsAndDisposal1.get(1);
            Object[] d4 = getLiveReceiptsAndDisposal1.get(2);
            Object[] d5 = getLiveReceiptsAndDisposal1.get(3);
            
            
            Object[] b1 = getLiveBreckUp1.get(0);
            Object[] b2 = getLiveBreckUp1.get(1);
            Object[] b3 = getLiveBreckUp1.get(2);
            Object[] b4 = getLiveBreckUp1.get(3);
            Object[] b5 = getLiveBreckUp1.get(4);
            Object[] b6 = getLiveBreckUp1.get(5);
            Object[] b7 = getLiveBreckUp1.get(6);

            System.out.println(d2 + " " + d3 + " " + d4 + " " + d5+" Hiii");

            int d11 = Integer.parseInt(String.valueOf(d1[0]));
            BigDecimal d12 = new BigDecimal(String.valueOf(d1[1]));
            BigDecimal d13 = new BigDecimal(String.valueOf(d1[2]));

            int d21 = Integer.parseInt(d2[0].toString());
            BigDecimal d22 = new BigDecimal(String.valueOf(d2[1]));
            BigDecimal d23 = new BigDecimal(String.valueOf(d2[2]));

            int d31 = Integer.parseInt(d3[0].toString());
            BigDecimal d32 = new BigDecimal(String.valueOf(d3[1]));
            BigDecimal d33 = new BigDecimal(String.valueOf(d3[2]));

            int totalIn = d21 + d31;
            BigDecimal totalCif = d22.add(d32);
            BigDecimal totalCargo = d23.add(d33);

            int d41 = Integer.parseInt(d4[0].toString());
            BigDecimal d42 = new BigDecimal(String.valueOf(d4[1]));
            BigDecimal d43 = new BigDecimal(String.valueOf(d4[2]));

            int d51 = Integer.parseInt(d5[0].toString());
            BigDecimal d52 = new BigDecimal(String.valueOf(d5[1]));
            BigDecimal d53 = new BigDecimal(String.valueOf(d5[2]));

            int totalEx = d41 + d51;
            BigDecimal totalExCif = d42.add(d52);
            BigDecimal totalExCargo = d43.add(d53);
            
            int totalClosePack = totalIn - totalEx;
            BigDecimal totalCloseCif = totalCif.subtract(totalExCif);
            BigDecimal totalCloseCargo = totalCargo.subtract(totalExCargo);
// ------------------------------------------------------------------------------
            int totalBreakPack = 0;
            BigDecimal totalBreakCif = new BigDecimal("0.0");
            BigDecimal totalBreakCargo = new BigDecimal("0.0");

            int b11 = Integer.parseInt(String.valueOf(b1[0]));
            BigDecimal b12 = new BigDecimal(String.valueOf(b1[1]));
            BigDecimal b13 = new BigDecimal(String.valueOf(b1[2]));
            
            totalBreakPack = totalBreakPack + b11;
            totalBreakCif = totalBreakCif.add(b12);
            totalBreakCargo = totalBreakCargo.add(b13);
            
            
            int b21 = Integer.parseInt(String.valueOf(b2[0]));
            BigDecimal b22 = new BigDecimal(String.valueOf(b2[1]));
            BigDecimal b23 = new BigDecimal(String.valueOf(b2[2]));
            
            totalBreakPack = totalBreakPack + b21;
            totalBreakCif = totalBreakCif.add(b22);
            totalBreakCargo = totalBreakCargo.add(b23);

            int b31 = Integer.parseInt(String.valueOf(b3[0]));
            BigDecimal b32 = new BigDecimal(String.valueOf(b3[1]));
            BigDecimal b33 = new BigDecimal(String.valueOf(b3[2]));
            
            totalBreakPack = totalBreakPack + b31;
            totalBreakCif = totalBreakCif.add(b32);
            totalBreakCargo = totalBreakCargo.add(b33);
            
            int b41 = Integer.parseInt(String.valueOf(b4[0]));
            BigDecimal b42 = new BigDecimal(String.valueOf(b4[1]));
            BigDecimal b43 = new BigDecimal(String.valueOf(b4[2]));
            
            totalBreakPack = totalBreakPack + b41;
            totalBreakCif = totalBreakCif.add(b42);
            totalBreakCargo = totalBreakCargo.add(b43);
            
            int b51 = Integer.parseInt(String.valueOf(b5[0]));
            BigDecimal b52 = new BigDecimal(String.valueOf(b5[1]));
            BigDecimal b53 = new BigDecimal(String.valueOf(b5[2]));
            
            totalBreakPack = totalBreakPack + b51;
            totalBreakCif = totalBreakCif.add(b52);
            totalBreakCargo = totalBreakCargo.add(b53);
            
            int b61 = Integer.parseInt(String.valueOf(b6[0]));
            BigDecimal b62 = new BigDecimal(String.valueOf(b6[1]));
            BigDecimal b63 = new BigDecimal(String.valueOf(b6[2]));
            
            totalBreakPack = totalBreakPack + b61;
            totalBreakCif = totalBreakCif.add(b62);
            totalBreakCargo = totalBreakCargo.add(b63);
            
            int b71 = Integer.parseInt(String.valueOf(b7[0]));
            BigDecimal b72 = new BigDecimal(String.valueOf(b7[1]));
            BigDecimal b73 = new BigDecimal(String.valueOf(b7[2]));
            
            totalBreakPack = totalBreakPack + b71;
            totalBreakCif = totalBreakCif.add(b72);
            totalBreakCargo = totalBreakCargo.add(b73);
           

            CellStyle dataStyle = livebondSheet.getWorkbook().createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
            dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            dataStyle.setWrapText(true);

            int rowIndex = 0;

            Row headerRow1 = livebondSheet.createRow(rowIndex++);
            Cell headerCell = headerRow1.createCell(0);
            headerCell.setCellValue("STATEMENT FOR THE MONTH OF  " + formatDate(endDate) + " (LIVE BOND) ");
            livebondSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 18));
            applyBorderToMergedCells(livebondSheet, 0, 0, 18);

            Row headerRow2 = livebondSheet.createRow(rowIndex++);
            Cell headerCell1 = headerRow2.createCell(0);
            headerCell1.setCellValue(" ALL ASSESSED VALUE AND DUTY AMOUNT ");
            livebondSheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 18));
            applyBorderToMergedCells(livebondSheet, 1, 0, 18);

            Row headerRow3 = livebondSheet.createRow(rowIndex++);
            Cell headerCell2 = headerRow3.createCell(0);
            headerCell2.setCellValue(" WAREHOUSE CODE : " + company);
            livebondSheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 18));
            applyBorderToMergedCells(livebondSheet, 2, 0, 17);

            Row headerRow4 = livebondSheet.createRow(rowIndex++);
            Cell headerCell3 = headerRow4.createCell(0);
            headerCell3.setCellValue(" PERIOD " + formatDate(startDate) + " TO " + formatDate(endDate));
            livebondSheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 18));
            applyBorderToMergedCells(livebondSheet, 3, 0, 18);

            Row headerRow5 = livebondSheet.createRow(rowIndex++);
            Cell headerCell4 = headerRow5.createCell(0);
            headerCell4.setCellValue(" Details  of LIVE Bond as on  " + formatDate(endDate));
            livebondSheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 18));
            applyBorderToMergedCells(livebondSheet, 4, 0, 18);

            livebondSheet.createRow(rowIndex++);
            Row headersRow = livebondSheet.createRow(rowIndex);

            for (int i = 0; i < 4; i++) {
                headersRow.createCell(i);
            }

//            Cell receiptsHeaderCell = headersRow.createCell(4);
//            receiptsHeaderCell.setCellValue("RECEIPTS");
//            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 4, 6));
//
//            Cell disposalHeaderCell = headersRow.createCell(7);
//            disposalHeaderCell.setCellValue("DISPOSAL");
//            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 9));
//            headersRow.createCell(10);
//
//            Cell breakupHeaderCell = headersRow.createCell(11);
//            breakupHeaderCell.setCellValue("BREAK UP OF THE PENDENCY AT THE END OF THE MONTH");
//            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 11, 18));

            
            
         // Create the header style with light orange background and white font color
            CellStyle headerStyle = livebondSheet.getWorkbook().createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            
          Font boldFont = livebondSheet.getWorkbook().createFont();
          boldFont.setBold(true);
          headerStyle.setFont(boldFont);
          boldFont.setColor(IndexedColors.WHITE.getIndex());
           
            // Apply the style to each header cell and its merged region

            // Set "RECEIPTS" header with background color and white font
            Cell receiptsHeaderCell = headersRow.createCell(4);
            receiptsHeaderCell.setCellValue("RECEIPTS");
//            receiptsHeaderCell.setCellStyle(headerStyle);
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 4, 6));
            for (int i = 5; i <= 6; i++) {
                Cell cell = headersRow.createCell(i);
//                cell.setCellStyle(headerStyle); // Apply style to merged cells without setting value
            }

            // Set "DISPOSAL" header with background color and white font
            Cell disposalHeaderCell = headersRow.createCell(7);
            disposalHeaderCell.setCellValue("DISPOSAL");
//            disposalHeaderCell.setCellStyle(headerStyle);
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 9));
            for (int i = 8; i <= 9; i++) {
                Cell cell = headersRow.createCell(i);
//                cell.setCellStyle(headerStyle); // Apply style to merged cells without setting value
            }

            // Set "BREAK UP OF THE PENDENCY AT THE END OF THE MONTH" with background color and white font
            Cell breakupHeaderCell = headersRow.createCell(11);
            breakupHeaderCell.setCellValue("BREAK UP OF THE PENDENCY AT THE END OF THE MONTH");
//            breakupHeaderCell.setCellStyle(headerStyle);
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 11, 18));
            for (int i = 12; i <= 18; i++) {
                Cell cell = headersRow.createCell(i);
//                cell.setCellStyle(headerStyle); // Apply style to merged cells without setting value
            }


            
            applyBorderToMergedCells(livebondSheet, rowIndex, 0, 18);

            rowIndex++;

            Row headersRow2 = livebondSheet.createRow(rowIndex++);

            String[] headers = { "SR.NO", "NAME OF THE BOND", "NUMBERS, DUTY & ASSESSED", "OPENING BALANCE",
                    "Upto Last Month From April", "FOR THE MONTH", "UPTO current MONTH",
                    "Upto Last Month From April", "FOR THE MONTH", "UPTO current MONTH", "CLOSING BALANCE",
                    "< 1 MONTHS", "1---3 MONTHS", "3---6 MONTHS", "6---12 MONTHS", "1-2 YEARS", "2-3 YEARS",
                    "ABOVE 3 YEARS", "Total" };

//            CellStyle headerStyle = livebondSheet.getWorkbook().createCellStyle();
//            headerStyle.setBorderBottom(BorderStyle.THIN);
//            headerStyle.setBorderTop(BorderStyle.THIN);
//            headerStyle.setBorderLeft(BorderStyle.THIN);
//            headerStyle.setBorderRight(BorderStyle.THIN);
//            headerStyle.setAlignment(HorizontalAlignment.CENTER);
//            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//          
//            
//            Font boldFont = livebondSheet.getWorkbook().createFont();
//            boldFont.setBold(true);
//            headerStyle.setFont(boldFont);
//            
//            headerFont.setColor(IndexedColors.WHITE.getIndex());
//			   
//	        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
//	        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < headers.length; i++) {
                Cell headerCell9 = headersRow2.createCell(i);
                headerCell9.setCellValue(headers[i]);
                headerCell9.setCellStyle(headerStyle);
                livebondSheet.autoSizeColumn(i);
            }

            String[] valuesForRow1 = { "1", "SKNC", "NO", String.valueOf(d11), String.valueOf(d21), String.valueOf(d31),
                    String.valueOf(totalIn), String.valueOf(d41), String.valueOf(d51), String.valueOf(totalEx), String.valueOf(totalClosePack),
                    String.valueOf(b11),String.valueOf(b21),String.valueOf(b31),String.valueOf(b41),String.valueOf(b51),String.valueOf(b61),String.valueOf(b71), String.valueOf(totalBreakPack) };
            setRowValues(livebondSheet, rowIndex++, valuesForRow1);

            String[] valuesForRow2 = { "", "", "ASSESSED VALUE", String.valueOf(d12), d22.toString(), d32.toString(),
                    String.valueOf(totalCif), String.valueOf(d42), String.valueOf(d52), String.valueOf(totalExCif),
                    String.valueOf(totalCloseCif), String.valueOf(b12),String.valueOf(b22),String.valueOf(b32),String.valueOf(b42),String.valueOf(b52),String.valueOf(b62),String.valueOf(b72), String.valueOf(totalBreakCif) };
            setRowValues(livebondSheet, rowIndex++, valuesForRow2);

            String[] valuesForRow3 = { "", "", "DUTY INVOLVED", String.valueOf(d13), d23.toString(), d33.toString(),
                    String.valueOf(totalCargo), String.valueOf(d43), String.valueOf(d53),
                    String.valueOf(totalExCargo), String.valueOf(totalCloseCargo), String.valueOf(b13),String.valueOf(b23),String.valueOf(b33),String.valueOf(b43),String.valueOf(b53),String.valueOf(b63),String.valueOf(b73), String.valueOf(totalBreakCargo) };
            setRowValues(livebondSheet, rowIndex++, valuesForRow3);

            rowIndex = rowIndex + 3;
            
            List<Object[]> openingBalanceList = InbondCFRepositary.getLiveOpeningBalance1(company, branch, startDate, endDate,section49);
            System.out.println("hiii1");

            List<Object[]> receiptsAndDisposalList = InbondCFRepositary.getLiveReceiptsAndDisposal1(company, branch, startDate, endDate, aprilDate, lastDateOfPreviousMonth,section49);

            List<Object[]> breckUpList = InbondCFRepositary.getLiveBreckUp1(company, branch, endDate, startDate, endDate, start13, lastDateOfPreviousMonth, start36, end36, start612, end612, start12y, end12y, start23y, end23y, start23y);

            Object[] openingBalance = openingBalanceList.get(0);
            Object[] receiptsData1 = receiptsAndDisposalList.get(0);
            Object[] receiptsData2 = receiptsAndDisposalList.get(1);
            Object[] receiptsData3 = receiptsAndDisposalList.get(2);
            Object[] receiptsData4 = receiptsAndDisposalList.get(3);

            Object[] breckUpData1 = breckUpList.get(0);
            Object[] breckUpData2 = breckUpList.get(1);
            Object[] breckUpData3 = breckUpList.get(2);
            Object[] breckUpData4 = breckUpList.get(3);
            Object[] breckUpData5 = breckUpList.get(4);
            Object[] breckUpData6 = breckUpList.get(5);
            Object[] breckUpData7 = breckUpList.get(6);

            System.out.println(receiptsData1 + " " + receiptsData2 + " " + receiptsData3 + " " + receiptsData4 + " Hiii");

            int openingBalanceInt = Integer.parseInt(String.valueOf(openingBalance[0]));
            BigDecimal openingBalanceDecimal1 = new BigDecimal(String.valueOf(openingBalance[1]));
            BigDecimal openingBalanceDecimal2 = new BigDecimal(String.valueOf(openingBalance[2]));

            int receiptsDataInt1 = Integer.parseInt(receiptsData1[0].toString());
            BigDecimal receiptsDataDecimal1 = new BigDecimal(String.valueOf(receiptsData1[1]));
            BigDecimal receiptsDataDecimal2 = new BigDecimal(String.valueOf(receiptsData1[2]));

            int receiptsDataInt2 = Integer.parseInt(receiptsData2[0].toString());
            BigDecimal receiptsDataDecimal3 = new BigDecimal(String.valueOf(receiptsData2[1]));
            BigDecimal receiptsDataDecimal4 = new BigDecimal(String.valueOf(receiptsData2[2]));

            int totalIn1 = receiptsDataInt1 + receiptsDataInt2;
            BigDecimal totalCif1 = receiptsDataDecimal1.add(receiptsDataDecimal3);
            BigDecimal totalCargo1 = receiptsDataDecimal2.add(receiptsDataDecimal4);

            int receiptsDataInt3 = Integer.parseInt(receiptsData3[0].toString());
            BigDecimal receiptsDataDecimal5 = new BigDecimal(String.valueOf(receiptsData3[1]));
            BigDecimal receiptsDataDecimal6 = new BigDecimal(String.valueOf(receiptsData3[2]));

            int receiptsDataInt4 = Integer.parseInt(receiptsData4[0].toString());
            BigDecimal receiptsDataDecimal7 = new BigDecimal(String.valueOf(receiptsData4[1]));
            BigDecimal receiptsDataDecimal8 = new BigDecimal(String.valueOf(receiptsData4[2]));

            int totalEx1 = receiptsDataInt3 + receiptsDataInt4;
            BigDecimal totalExCif1 = receiptsDataDecimal5.add(receiptsDataDecimal7);
            BigDecimal totalExCargo1 = receiptsDataDecimal6.add(receiptsDataDecimal8);

            int totalClosePack1 = totalIn1 - totalEx1;
            BigDecimal totalCloseCif1 = totalCif1.subtract(totalExCif1);
            BigDecimal totalCloseCargo1 = totalCargo1.subtract(totalExCargo1);
            // ------------------------------------------------------------------------------
            int totalBreakPack1 = 0;
            BigDecimal totalBreakCif1 = new BigDecimal("0.0");
            BigDecimal totalBreakCargo1 = new BigDecimal("0.0");

            int breckUpDataInt1 = Integer.parseInt(String.valueOf(breckUpData1[0]));
            BigDecimal breckUpDataDecimal1 = new BigDecimal(String.valueOf(breckUpData1[1]));
            BigDecimal breckUpDataDecimal2 = new BigDecimal(String.valueOf(breckUpData1[2]));

            totalBreakPack1 = totalBreakPack1 + breckUpDataInt1;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal1);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal2);

            int breckUpDataInt2 = Integer.parseInt(String.valueOf(breckUpData2[0]));
            BigDecimal breckUpDataDecimal3 = new BigDecimal(String.valueOf(breckUpData2[1]));
            BigDecimal breckUpDataDecimal4 = new BigDecimal(String.valueOf(breckUpData2[2]));

            totalBreakPack1 = totalBreakPack1 + breckUpDataInt2;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal3);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal4);

            int breckUpDataInt3 = Integer.parseInt(String.valueOf(breckUpData3[0]));
            BigDecimal breckUpDataDecimal5 = new BigDecimal(String.valueOf(breckUpData3[1]));
            BigDecimal breckUpDataDecimal6 = new BigDecimal(String.valueOf(breckUpData3[2]));

            totalBreakPack1 = totalBreakPack1 + breckUpDataInt3;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal5);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal6);

            int breckUpDataInt4 = Integer.parseInt(String.valueOf(breckUpData4[0]));
            BigDecimal breckUpDataDecimal7 = new BigDecimal(String.valueOf(breckUpData4[1]));
            BigDecimal breckUpDataDecimal8 = new BigDecimal(String.valueOf(breckUpData4[2]));

            totalBreakPack1 = totalBreakPack1 + breckUpDataInt4;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal7);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal8);

            int breckUpDataInt5 = Integer.parseInt(String.valueOf(breckUpData5[0]));
            BigDecimal breckUpDataDecimal9 = new BigDecimal(String.valueOf(breckUpData5[1]));
            BigDecimal breckUpDataDecimal10 = new BigDecimal(String.valueOf(breckUpData5[2]));

            totalBreakPack1 = totalBreakPack1 + breckUpDataInt5;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal9);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal10);

            int breckUpDataInt6 = Integer.parseInt(String.valueOf(breckUpData6[0]));
            BigDecimal breckUpDataDecimal11 = new BigDecimal(String.valueOf(breckUpData6[1]));
            BigDecimal breckUpDataDecimal12 = new BigDecimal(String.valueOf(breckUpData6[2]));

            totalBreakPack1 = totalBreakPack1 + breckUpDataInt6;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal11);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal12);

            int breckUpDataInt7 = Integer.parseInt(String.valueOf(breckUpData7[0]));
            BigDecimal breckUpDataDecimal13 = new BigDecimal(String.valueOf(breckUpData7[1]));
            BigDecimal breckUpDataDecimal14 = new BigDecimal(String.valueOf(breckUpData7[2]));
            
            totalBreakPack1 = totalBreakPack1 + breckUpDataInt7;
            totalBreakCif1 = totalBreakCif1.add(breckUpDataDecimal13);
            totalBreakCargo1 = totalBreakCargo1.add(breckUpDataDecimal14);


            Row headersRow10 = livebondSheet.createRow(rowIndex);
            Cell receiptsHeaderCell10 = headersRow10.createCell(0);
            receiptsHeaderCell10.setCellValue("Details of EXPIRED Bond as on " + formatDate(endDate));
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 18));
            applyBorderToMergedCells(livebondSheet, rowIndex, 0, 18);

            rowIndex++;

            Row headersRow11 = livebondSheet.createRow(rowIndex);

            for (int i = 0; i < 4; i++) {
                headersRow11.createCell(i);
            }

            Cell receiptsHeaderCell11 = headersRow11.createCell(4);
            receiptsHeaderCell11.setCellValue("RECEIPTS");
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 4, 6));

            Cell disposalHeaderCell11 = headersRow11.createCell(7);
            disposalHeaderCell11.setCellValue("DISPOSAL");
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 9));

            headersRow.createCell(10);

            Cell breakupHeaderCell11 = headersRow11.createCell(11);
            breakupHeaderCell11.setCellValue("BREAK UP OF THE PENDENCY AT THE END OF THE MONTH");
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 11, 18));

            applyBorderToMergedCells(livebondSheet, rowIndex, 0, 18);

            rowIndex++;

            Row headersRow3 = livebondSheet.createRow(rowIndex++);

            for (int i = 0; i < headers.length; i++) {
                Cell headerCell9 = headersRow3.createCell(i);
                headerCell9.setCellValue(headers[i]);
                headerCell9.setCellStyle(headerStyle);
                livebondSheet.autoSizeColumn(i);
            }

                livebondSheet.createFreezePane(0, 8);

            int headerRowIndex = 16; // This is the next header row after the 6 free rows (8 + 6)

            livebondSheet.createFreezePane(0, headerRowIndex + 1);
         // Enable auto-filter for the header row (adjust column range as needed)
//         livebondSheet.setAutoFilter(new CellRangeAddress(5, 5, 0, headers.length - 1));
	        
            String[] valuesForRow4 = {"1", "SKNC", "NO", String.valueOf(openingBalanceInt), String.valueOf(receiptsDataInt1), String.valueOf(receiptsDataInt2),
                    String.valueOf(totalIn1), String.valueOf(receiptsDataInt3), String.valueOf(receiptsDataInt4), String.valueOf(totalEx1), String.valueOf(totalClosePack1),
                    String.valueOf(breckUpDataInt1), String.valueOf(breckUpDataInt2), String.valueOf(breckUpDataInt3), String.valueOf(breckUpDataInt4), String.valueOf(breckUpDataInt5),
                    String.valueOf(breckUpDataInt6), String.valueOf(breckUpDataInt7), String.valueOf(totalBreakPack1)};
            setRowValues(livebondSheet, rowIndex++, valuesForRow4);

            String[] valuesForRow5 = { "", "", "ASSESSED VALUE", String.valueOf(openingBalanceDecimal1), receiptsDataDecimal1.toString(), receiptsDataDecimal3.toString(),
                    String.valueOf(totalCif1), receiptsDataDecimal7.toString(), receiptsDataDecimal8.toString(), String.valueOf(totalExCif1),
                    String.valueOf(totalCloseCif1), breckUpDataDecimal1.toString(), breckUpDataDecimal3.toString(), breckUpDataDecimal5.toString(), breckUpDataDecimal7.toString(), breckUpDataDecimal9.toString(), breckUpDataDecimal11.toString(), breckUpDataDecimal13.toString(), String.valueOf(totalBreakCif1)  };
            setRowValues(livebondSheet, rowIndex++, valuesForRow5);

            String[] valuesForRow6 = { "", "", "DUTY INVOLVED", String.valueOf(openingBalanceDecimal2), receiptsDataDecimal2.toString(), receiptsDataDecimal4.toString(),
                    String.valueOf(totalCargo1), receiptsDataDecimal5.toString(), receiptsDataDecimal6.toString(),
                    String.valueOf(totalExCargo1), String.valueOf(totalCloseCargo1), breckUpDataDecimal2.toString(), breckUpDataDecimal4.toString(), breckUpDataDecimal6.toString(), breckUpDataDecimal8.toString(), breckUpDataDecimal10.toString(), breckUpDataDecimal12.toString(), breckUpDataDecimal14.toString(), String.valueOf(totalBreakCargo1) };
            setRowValues(livebondSheet, rowIndex++, valuesForRow6);

            return livebondSheet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	
	
	
	
	private String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		return sdf.format(date);
	}

	private void applyBorderToMergedCells(Sheet sheet, int rowIndex, int colIndexStart, int colIndexEnd) {
		for (int i = colIndexStart; i <= colIndexEnd; i++) {
			Cell cell = sheet.getRow(rowIndex).getCell(i);
			if (cell == null) {
				cell = sheet.getRow(rowIndex).createCell(i);
			}
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.cloneStyleFrom(cell.getCellStyle()); // Copy existing cell style
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setWrapText(true);
			Font boldFont = sheet.getWorkbook().createFont();
			boldFont.setBold(true);
			cellStyle.setFont(boldFont);
			cell.setCellStyle(cellStyle);
		}
	}



	private void setRowValues(Sheet sheet, int rowIndex, String[] values) {
		Row row = sheet.createRow(rowIndex);
		for (int i = 0; i < values.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(values[i]);
			CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
			cellStyle.cloneStyleFrom(cell.getCellStyle()); // Copy existing cell style
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setWrapText(true);
			Font boldFont = sheet.getWorkbook().createFont();
			boldFont.setBold(true);
			cellStyle.setFont(boldFont);
			cell.setCellStyle(cellStyle);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
	
	
	
}
