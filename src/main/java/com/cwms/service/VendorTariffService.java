package com.cwms.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.APServices;
import com.cwms.entities.CFSTariffService;
import com.cwms.entities.CfsTarrif;
import com.cwms.entities.Services;
import com.cwms.entities.VendorTariff;
import com.cwms.entities.VendorTariffSrv;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ApServicesRepo;
import com.cwms.repository.CFSRepositary;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.JarDetailRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.SerViceRepositary;
import com.cwms.repository.VendorTariffRepo;
import com.cwms.repository.VendorTariffSrvRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class VendorTariffService {
	@Autowired
	private VendorTariffRepo tarrifRepo;

	@Autowired
	private VendorTariffSrvRepo tarrifServiceRepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	public ApServicesRepo serviceRepo;

	@Autowired
	private ProcessNextIdService processService;

	@Autowired
	private HelperMethods helperMethods;

	@Autowired
	private JarDetailRepository jarDtlRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@PersistenceContext
	private EntityManager entityManager;
	
	
	

	public byte[] getTariffReport(String companyId, String branchId, String cfsTariffNo, String cfsAmendNo, String contractName, String userId) {
	    try {
	        // Fetch services
	        List<VendorTariffSrv> forTariffReport = tarrifServiceRepo.getForTariffReport(companyId, branchId, cfsTariffNo, cfsAmendNo);

	        // Group services by serviceName
	        Map<String, List<VendorTariffSrv>> groupedServices = forTariffReport.stream()
	            .collect(Collectors.groupingBy(VendorTariffSrv::getServiceName));

	        // Create workbook and sheet
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Tariff Report");
	        
	   
	        // Set column widths for clarity
	        sheet.setColumnWidth(0, 4500); // First column
	        sheet.setColumnWidth(1, 4500); // Second column
	        sheet.setColumnWidth(2, 5500); // Third column
	        sheet.setColumnWidth(3, 4500); // Fourth column
	        sheet.setColumnWidth(4, 5500); // Fifth column

	        // Set a default width for columns from the sixth onwards
	        for (int i = 5; i < 25; i++) {
	            sheet.setColumnWidth(i, 4500);
	        }
	        
	        
	        int rowIndex = 0;
	        
	        // Create a CellStyle for center alignment
	        CellStyle centerAlignedStyle = workbook.createCellStyle();
	        centerAlignedStyle.setAlignment(HorizontalAlignment.CENTER);
	        centerAlignedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        
	        centerAlignedStyle.setBorderTop(BorderStyle.THIN);
	        centerAlignedStyle.setBorderBottom(BorderStyle.THIN);
	        centerAlignedStyle.setBorderLeft(BorderStyle.THIN);
	        centerAlignedStyle.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        centerAlignedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

	        
	        
	     // Set background color to light grey
	        centerAlignedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        centerAlignedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        
	        // Create a bold font for headers
	        Font boldFont = workbook.createFont();
	        boldFont.setBold(true);

	     // Create a CellStyle with medium borders
	        CellStyle borderedStyle = workbook.createCellStyle();
	        borderedStyle.setBorderTop(BorderStyle.THIN);
	        borderedStyle.setBorderBottom(BorderStyle.THIN);
	        borderedStyle.setBorderLeft(BorderStyle.THIN);
	        borderedStyle.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        borderedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        borderedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        borderedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        borderedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

	        
	        
	        
	        
	        
	        // Add title row
	        Row titleRow = sheet.createRow(rowIndex++);
	        Cell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("Revenue Tariff Rates");
	        titleCell.setCellStyle(centerAlignedStyle);
//	        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6)); // Merge cells for the title

	        
	     // Merge the cells for the title (e.g., from column 0 to column 6)
	        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 6);
	        sheet.addMergedRegion(mergedRegion);

	        // Apply the centerAlignedStyle to all cells in the merged range
	        for (int col = 1; col <= 6; col++) {
	            Cell cell = titleRow.createCell(col);
	            cell.setCellStyle(centerAlignedStyle);
	        }
	        
	        // Add blank rows
	        sheet.createRow(rowIndex++);
	       
	     // Create the header row
	        Row contractHeaderRow = sheet.createRow(rowIndex++);
	        Cell contractHeaderCell = contractHeaderRow.createCell(0);
	        contractHeaderCell.setCellValue("Contract Name :");
	        contractHeaderCell.setCellStyle(centerAlignedStyle);
	        
	        Cell contractCell = contractHeaderRow.createCell(1);
	        contractCell.setCellValue(contractName);
	        contractCell.setCellStyle(borderedStyle);

	        // Merge the next six cells horizontally
	        sheet.addMergedRegion(new CellRangeAddress(
	            contractHeaderRow.getRowNum(), contractHeaderRow.getRowNum(), // Rows to merge
	            1, 6 // Column indexes for the merged cells
	        ));

	        for (int col = 2; col <= 6; col++) {
	            Cell cell = contractHeaderRow.createCell(col);
	            cell.setCellStyle(borderedStyle);
	        }
	        
	        
	        
	       
	        	                
	        CellStyle centerAlignedStyleValue = workbook.createCellStyle();
	        centerAlignedStyleValue.setAlignment(HorizontalAlignment.CENTER);
	        centerAlignedStyleValue.setVerticalAlignment(VerticalAlignment.CENTER);
	        centerAlignedStyleValue.setBorderTop(BorderStyle.THIN);
	        centerAlignedStyleValue.setBorderBottom(BorderStyle.THIN);
	        centerAlignedStyleValue.setBorderLeft(BorderStyle.THIN);
	        centerAlignedStyleValue.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        centerAlignedStyleValue.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyleValue.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyleValue.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyleValue.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        
	        
	        

	        // Create a CellStyle for right alignment
	        CellStyle rightAlignedStyle = workbook.createCellStyle();
	        rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
	        rightAlignedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        rightAlignedStyle.setBorderTop(BorderStyle.THIN);
	        rightAlignedStyle.setBorderBottom(BorderStyle.THIN);
	        rightAlignedStyle.setBorderLeft(BorderStyle.THIN);
	        rightAlignedStyle.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        rightAlignedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        rightAlignedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        rightAlignedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        rightAlignedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	   
	        	      
	        // Iterate through each service group
	        for (Map.Entry<String, List<VendorTariffSrv>> entry : groupedServices.entrySet()) {
	            String serviceName = entry.getKey();
	            
	            List<VendorTariffSrv> serviceList = entry.getValue();

	            // Sort the serviceList by srNo
	            serviceList.sort(Comparator.comparing(VendorTariffSrv::getSrNo));

	            // Add a blank row before each service
	            sheet.createRow(rowIndex++);

	            // Add Tariff Head row
	         // Add Tariff Head row
	            Row tariffHeadRow = sheet.createRow(rowIndex++);
	            // Set the "Tariff Head:" value in the first cell (cell 0)
//	            tariffHeadRow.createCell(0).setCellValue("Tariff Head:");
	            // Create the second cell (cell 1) and set the value and style
	            
	            Cell tariffCell = tariffHeadRow.createCell(0);
	            tariffCell.setCellValue("Tariff Head:");
	            tariffCell.setCellStyle(centerAlignedStyle);
	            
	            
	            Cell serviceCell = tariffHeadRow.createCell(1);
	            serviceCell.setCellValue(serviceName);
	            serviceCell.setCellStyle(borderedStyle);      
	            
	            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 6));
	            
	            for (int col = 2; col <= 6; col++) {
		            Cell cell = tariffHeadRow.createCell(col);
		            cell.setCellStyle(borderedStyle);
		        }
	            
	            
	            // Get rangeType from the first record (assuming all records in the group have the same rangeType)
	            String rangeType = serviceList.get(0).getRangeType();
	            
	            // Add headers based on rangeType
	            Row headerRow = sheet.createRow(rowIndex++);
	            if ("SA".equals(rangeType)) {
	                headerRow.createCell(0).setCellValue("Range From");
	                headerRow.createCell(1).setCellValue("Range To");
	                headerRow.createCell(2).setCellValue("Cont Size");
	                headerRow.createCell(3).setCellValue("Cargo Type");
	                headerRow.createCell(4).setCellValue("Commodity");
	                headerRow.createCell(5).setCellValue("Currency");
	                headerRow.createCell(6).setCellValue("Rate");
	                
	                
	                
	            } else if ("RA".equals(rangeType)) {
	                headerRow.createCell(0).setCellValue("Weight From");
	                headerRow.createCell(1).setCellValue("Weight To");
	                headerRow.createCell(2).setCellValue("Cont Size");
	                headerRow.createCell(3).setCellValue("Cargo Type");
	                headerRow.createCell(4).setCellValue("Commodity");
	                headerRow.createCell(5).setCellValue("Currency");
	                headerRow.createCell(6).setCellValue("Rate");
	            } else if ("NA".equals(rangeType)) {
	                headerRow.createCell(0).setCellValue("Cont Size");
	                headerRow.createCell(1).setCellValue("Cargo Type");
	                headerRow.createCell(2).setCellValue("Commodity");
	                headerRow.createCell(3).setCellValue("Currency");
	                headerRow.createCell(4).setCellValue("Rate");
	            }

	            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
	                headerRow.getCell(i).setCellStyle(centerAlignedStyle);
	            }
	            
	            // Add service details
	            for (VendorTariffSrv service : serviceList) {
	                // Create a data row for each service based on the rangeType
	                Row dataRow = sheet.createRow(rowIndex++);
	                
	                if ("SA".equals(service.getRangeType()) || "RA".equals(service.getRangeType())) {
	                    
	                    // Create cells and set the values first
	                    Cell cell0 = dataRow.createCell(0);
//	                    cell0.setCellValue(service.getFromRange() != null ? service.getFromRange().toString() : "");
	                    
	                    cell0.setCellValue(service.getFromRange() != null ? ((BigDecimal) service.getFromRange()).doubleValue() : 0);
	                    
	                    cell0.setCellStyle(rightAlignedStyle);
	                    
	                    Cell cell1 = dataRow.createCell(1);
//	                    cell1.setCellValue(service.getToRange() != null ? service.getToRange().toString() : "");
	                    
	                    cell1.setCellValue(service.getToRange() != null ? ((BigDecimal) service.getToRange()).doubleValue() : 0);
	                    cell1.setCellStyle(rightAlignedStyle);
	                    
	                    Cell cell2 = dataRow.createCell(2);
	                    cell2.setCellValue(service.getContainerSize());
	                    cell2.setCellStyle(rightAlignedStyle);

	                    Cell cell3 = dataRow.createCell(3);
	                    cell3.setCellValue(service.getCargoType());
	                    cell3.setCellStyle(centerAlignedStyleValue);

	                    Cell cell4 = dataRow.createCell(4);
	                    cell4.setCellValue(service.getCommodityCode());
	                    cell4.setCellStyle(centerAlignedStyleValue);

	                    Cell cell5 = dataRow.createCell(5);
	                    cell5.setCellValue(service.getCurrencyId());
	                    cell5.setCellStyle(centerAlignedStyleValue);

	                    Cell cell6 = dataRow.createCell(6);
	                    
	                    cell6.setCellValue(service.getRate() != null ? ((BigDecimal) service.getRate()).doubleValue() : 0);
//	                    cell6.setCellValue(service.getRate() != null ? service.getRate().toString() : "");
	                    cell6.setCellStyle(rightAlignedStyle);

	                } else if ("NA".equals(service.getRangeType())) { // Handle NA type (different value rows)

	                    // Create cells and set the values first
	                    Cell cell0 = dataRow.createCell(0);
	                    cell0.setCellValue(service.getContainerSize());
	                    cell0.setCellStyle(rightAlignedStyle);

	                    Cell cell1 = dataRow.createCell(1);
	                    cell1.setCellValue(service.getCargoType());
	                    cell1.setCellStyle(centerAlignedStyleValue);

	                    Cell cell2 = dataRow.createCell(2);
	                    cell2.setCellValue(service.getCommodityCode());
	                    cell2.setCellStyle(centerAlignedStyleValue);

	                    Cell cell3 = dataRow.createCell(3);
	                    cell3.setCellValue(service.getCurrencyId());
	                    cell3.setCellStyle(centerAlignedStyleValue);

	                    Cell cell4 = dataRow.createCell(4);
	                    
	                    cell4.setCellValue(service.getRate() != null ? ((BigDecimal) service.getRate()).doubleValue() : 0);
	                    cell4.setCellStyle(rightAlignedStyle);
	                }
	            }

	        }

	        // Write workbook to a byte array
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        workbook.close();

	        return outputStream.toByteArray();
	    } catch (Exception e) {
	        System.out.print("Tariff Report Error: " + e);
	        return null;
	    }
	}


	
	
	
	
	public byte[] getTariffReportTemplate(String companyId, String branchId, String cfsTariffNo, String cfsAmendNo, String contractName, String userId) {
	    try {
	           // Fetch services
	    	   // Create a list to hold CFSTariffService objects
	        List<CFSTariffService> forTariffReport = new ArrayList<>();

	        // Add 7 objects to the list using the constructor
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("1"), "20", "Agriculture Cargo", "General", "NA",
	            new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("500"), "Container Handling and Transportation B", "INR"
	        ));
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("2"), "40", "Agriculture Cargo", "Reefer", "NA",
	            new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("1000"), "Container Handling and Transportation B", "INR"
	        ));
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("1"), "20", "Chemicals", "Hazardous", "RA",
	            new BigDecimal("0"), new BigDecimal("2000"), new BigDecimal("150"), "Cargo Handling Charges Export", "INR"
	        ));
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("2"), "20", "Chemicals", "Hazardous", "RA",
	            new BigDecimal("2001"), new BigDecimal("5000"), new BigDecimal("250"), "Cargo Handling Charges Export", "INR"
	        ));
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("3"), "20", "Chemicals", "Hazardous", "RA",
	            new BigDecimal("5001"), new BigDecimal("10000"), new BigDecimal("350"), "Cargo Handling Charges Export", "INR"
	        ));
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("1"), "20", "HOUSEHOLD ARTICLES", "General", "SA",
	            new BigDecimal("0"), new BigDecimal("10"), new BigDecimal("0"), "Container Storage Charges - Factory", "INR"
	        ));
	        forTariffReport.add(new CFSTariffService(
	            new BigDecimal("2"), "20", "HOUSEHOLD ARTICLES", "General", "SA",
	            new BigDecimal("10"), new BigDecimal("99999"), new BigDecimal("100"), "Container Storage Charges - Factory", "INR"
	        ));
	        
	        
	        
	        // Group services by serviceName
	        Map<String, List<CFSTariffService>> groupedServices = forTariffReport.stream()
	            .collect(Collectors.groupingBy(CFSTariffService::getServiceName));

	        // Create workbook and sheet
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Tariff Upload Template");
	        
	        // Set column widths for clarity
	        sheet.setColumnWidth(0, 4500); // First column
	        sheet.setColumnWidth(1, 4500); // Second column
	        sheet.setColumnWidth(2, 5500); // Third column
	        sheet.setColumnWidth(3, 4500); // Fourth column
	        sheet.setColumnWidth(4, 5500); // Fifth column

	        // Set a default width for columns from the sixth onwards
	        for (int i = 5; i < 25; i++) {
	            sheet.setColumnWidth(i, 4500);
	        }
	        
	        
	        int rowIndex = 0;
	        
	        
	        // Create a CellStyle for center alignment
	        CellStyle centerAlignedStyle = workbook.createCellStyle();
	        centerAlignedStyle.setAlignment(HorizontalAlignment.CENTER);
	        centerAlignedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        
	        centerAlignedStyle.setBorderTop(BorderStyle.THIN);
	        centerAlignedStyle.setBorderBottom(BorderStyle.THIN);
	        centerAlignedStyle.setBorderLeft(BorderStyle.THIN);
	        centerAlignedStyle.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        centerAlignedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

	        
	        
	     // Set background color to light grey
	        centerAlignedStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        centerAlignedStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	        
	        
	        
	        // Create a CellStyle with medium borders
	        CellStyle borderedStyle = workbook.createCellStyle();
	        borderedStyle.setBorderTop(BorderStyle.THIN);
	        borderedStyle.setBorderBottom(BorderStyle.THIN);
	        borderedStyle.setBorderLeft(BorderStyle.THIN);
	        borderedStyle.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        borderedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        borderedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        borderedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        borderedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

	        
	        
	        
	        
	        
	        // Add title row
	        Row titleRow = sheet.createRow(rowIndex++);
	        Cell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("Revenue Tariff Rates");
	        titleCell.setCellStyle(centerAlignedStyle);
//	        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6)); // Merge cells for the title

	     // Merge the cells for the title (e.g., from column 0 to column 6)
	        CellRangeAddress mergedRegion = new CellRangeAddress(0, 0, 0, 6);
	        sheet.addMergedRegion(mergedRegion);

	        // Apply the centerAlignedStyle to all cells in the merged range
	        for (int col = 1; col <= 6; col++) {
	            Cell cell = titleRow.createCell(col);
	            cell.setCellStyle(centerAlignedStyle);
	        }
	        
	        // Add blank rows
	        sheet.createRow(rowIndex++);
	       
	     // Create the header row
	        Row contractHeaderRow = sheet.createRow(rowIndex++);
	        Cell contractHeaderCell = contractHeaderRow.createCell(0);
	        contractHeaderCell.setCellValue("Contract Name :");
	        contractHeaderCell.setCellStyle(centerAlignedStyle);
	        
	        // Create a cell in the merged region and set the contract name
	        Cell contractCell = contractHeaderRow.createCell(1);
	        contractCell.setCellValue(contractName);
	        contractCell.setCellStyle(borderedStyle);

	        // Merge the next six cells horizontally
	        sheet.addMergedRegion(new CellRangeAddress(contractHeaderRow.getRowNum(), contractHeaderRow.getRowNum(), 1, 6));
	     // Apply the centerAlignedStyle to all cells in the merged range
	        for (int col = 2; col <= 6; col++) {
	            Cell cell = contractHeaderRow.createCell(col);
	            cell.setCellStyle(borderedStyle);
	        }
	        
	        
	        
	      
//	        contractCell.setCellStyle(centerAlignedStyle);
	        
	        
	        // Create a bold font for headers
	        Font boldFont = workbook.createFont();
	        boldFont.setBold(true);

	    
	        	                
	        CellStyle centerAlignedStyleValue = workbook.createCellStyle();
	        centerAlignedStyleValue.setAlignment(HorizontalAlignment.CENTER);
	        centerAlignedStyleValue.setVerticalAlignment(VerticalAlignment.CENTER);
	        centerAlignedStyleValue.setBorderTop(BorderStyle.THIN);
	        centerAlignedStyleValue.setBorderBottom(BorderStyle.THIN);
	        centerAlignedStyleValue.setBorderLeft(BorderStyle.THIN);
	        centerAlignedStyleValue.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        centerAlignedStyleValue.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyleValue.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyleValue.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        centerAlignedStyleValue.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        
	        
	        

	        // Create a CellStyle for right alignment
	        CellStyle rightAlignedStyle = workbook.createCellStyle();
	        rightAlignedStyle.setAlignment(HorizontalAlignment.RIGHT);
	        rightAlignedStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	        rightAlignedStyle.setBorderTop(BorderStyle.THIN);
	        rightAlignedStyle.setBorderBottom(BorderStyle.THIN);
	        rightAlignedStyle.setBorderLeft(BorderStyle.THIN);
	        rightAlignedStyle.setBorderRight(BorderStyle.THIN);

	        // Set border color to black
	        rightAlignedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        rightAlignedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        rightAlignedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        rightAlignedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        
	        	      
	        // Iterate through each service group
	        for (Map.Entry<String, List<CFSTariffService>> entry : groupedServices.entrySet()) {
	            String serviceName = entry.getKey();
	            
	            List<CFSTariffService> serviceList = entry.getValue();

	            // Sort the serviceList by srNo
	            serviceList.sort(Comparator.comparing(CFSTariffService::getSrNo));

	            // Add a blank row before each service
	            sheet.createRow(rowIndex++);

	            // Add Tariff Head row
	         // Add Tariff Head row
	            Row tariffHeadRow = sheet.createRow(rowIndex++);
	            // Set the "Tariff Head:" value in the first cell (cell 0)
	         // Create the first cell (cell 0) and set the value and style
	            Cell tariffCell = tariffHeadRow.createCell(0);
	            tariffCell.setCellValue("Tariff Head:");
	            tariffCell.setCellStyle(centerAlignedStyle);

	            // Create the second cell (cell 1) and set the value and style
	            Cell serviceCell = tariffHeadRow.createCell(1);
	            serviceCell.setCellValue(serviceName);
	            serviceCell.setCellStyle(borderedStyle);      
	            
	            sheet.addMergedRegion(new CellRangeAddress(rowIndex - 1, rowIndex - 1, 1, 6));

	            
	            for (int col = 2; col <= 6; col++) {
		            Cell cell = tariffHeadRow.createCell(col);
		            cell.setCellStyle(borderedStyle);
		        }
		        

	            // Get rangeType from the first record (assuming all records in the group have the same rangeType)
	            String rangeType = serviceList.get(0).getRangeType();
	            
	            // Add headers based on rangeType
	            Row headerRow = sheet.createRow(rowIndex++);
	            if ("SA".equals(rangeType)) {
	                headerRow.createCell(0).setCellValue("Range From");
	                headerRow.createCell(1).setCellValue("Range To");
	                headerRow.createCell(2).setCellValue("Cont Size");
	                headerRow.createCell(3).setCellValue("Cargo Type");
	                headerRow.createCell(4).setCellValue("Commodity");
	                headerRow.createCell(5).setCellValue("Currency");
	                headerRow.createCell(6).setCellValue("Rate");
	                
	                
	                
	            } else if ("RA".equals(rangeType)) {
	                headerRow.createCell(0).setCellValue("Weight From");
	                headerRow.createCell(1).setCellValue("Weight To");
	                headerRow.createCell(2).setCellValue("Cont Size");
	                headerRow.createCell(3).setCellValue("Cargo Type");
	                headerRow.createCell(4).setCellValue("Commodity");
	                headerRow.createCell(5).setCellValue("Currency");
	                headerRow.createCell(6).setCellValue("Rate");
	            } else if ("NA".equals(rangeType)) {
	                headerRow.createCell(0).setCellValue("Cont Size");
	                headerRow.createCell(1).setCellValue("Cargo Type");
	                headerRow.createCell(2).setCellValue("Commodity");
	                headerRow.createCell(3).setCellValue("Currency");
	                headerRow.createCell(4).setCellValue("Rate");
	            }

	            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
	                headerRow.getCell(i).setCellStyle(centerAlignedStyle);
	            }
	            
	            // Add service details
	            for (CFSTariffService service : serviceList) {
	                // Create a data row for each service based on the rangeType
	                Row dataRow = sheet.createRow(rowIndex++);
	                
	                if ("SA".equals(service.getRangeType()) || "RA".equals(service.getRangeType())) {
	                    
	                    // Create cells and set the values first
	                    Cell cell0 = dataRow.createCell(0);
//	                    cell0.setCellValue(service.getFromRange() != null ? service.getFromRange().toString() : "");
	                    
	                    cell0.setCellValue(service.getFromRange() != null ? ((BigDecimal) service.getFromRange()).doubleValue() : 0);
	                    
	                    cell0.setCellStyle(rightAlignedStyle);
	                    
	                    Cell cell1 = dataRow.createCell(1);
//	                    cell1.setCellValue(service.getToRange() != null ? service.getToRange().toString() : "");
	                    
	                    cell1.setCellValue(service.getToRange() != null ? ((BigDecimal) service.getToRange()).doubleValue() : 0);
	                    cell1.setCellStyle(rightAlignedStyle);
	                    
	                    Cell cell2 = dataRow.createCell(2);
	                    cell2.setCellValue(service.getContainerSize());
	                    cell2.setCellStyle(rightAlignedStyle);

	                    Cell cell3 = dataRow.createCell(3);
	                    cell3.setCellValue(service.getCargoType());
	                    cell3.setCellStyle(centerAlignedStyleValue);

	                    Cell cell4 = dataRow.createCell(4);
	                    cell4.setCellValue(service.getCommodityCode());
	                    cell4.setCellStyle(centerAlignedStyleValue);

	                    Cell cell5 = dataRow.createCell(5);
	                    cell5.setCellValue(service.getCurrencyId());
	                    cell5.setCellStyle(centerAlignedStyleValue);

	                    Cell cell6 = dataRow.createCell(6);
	                    
	                    cell6.setCellValue(service.getRate() != null ? ((BigDecimal) service.getRate()).doubleValue() : 0);
//	                    cell6.setCellValue(service.getRate() != null ? service.getRate().toString() : "");
	                    cell6.setCellStyle(rightAlignedStyle);

	                } else if ("NA".equals(service.getRangeType())) { // Handle NA type (different value rows)

	                    // Create cells and set the values first
	                    Cell cell0 = dataRow.createCell(0);
	                    cell0.setCellValue(service.getContainerSize());
	                    cell0.setCellStyle(rightAlignedStyle);

	                    Cell cell1 = dataRow.createCell(1);
	                    cell1.setCellValue(service.getCargoType());
	                    cell1.setCellStyle(centerAlignedStyleValue);

	                    Cell cell2 = dataRow.createCell(2);
	                    cell2.setCellValue(service.getCommodityCode());
	                    cell2.setCellStyle(centerAlignedStyleValue);

	                    Cell cell3 = dataRow.createCell(3);
	                    cell3.setCellValue(service.getCurrencyId());
	                    cell3.setCellStyle(centerAlignedStyleValue);

	                    Cell cell4 = dataRow.createCell(4);
	                    
	                    cell4.setCellValue(service.getRate() != null ? ((BigDecimal) service.getRate()).doubleValue() : 0);
	                    cell4.setCellStyle(rightAlignedStyle);
	                }
	                
	                
	            }

	        }

	        // Write workbook to a byte array
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        workbook.close();

	        return outputStream.toByteArray();
	    } catch (Exception e) {
	        System.out.print("Tariff Report Error: " + e);
	        return null;
	    }
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> uploadTariff(String companyId, String branchId, String cfsTariffJson, MultipartFile file, String user) {
		try {

			
			VendorTariff cfsTariff = null;
			try {
		        cfsTariff = objectMapper.readValue(cfsTariffJson, VendorTariff.class);
		    } catch (Exception e) {
		        return ResponseEntity.badRequest().body("Invalid CfsTariff JSON");
		    }
			
			
			VendorTariff savedTariff = tarrifRepo.getSavedTariffNotJoin(companyId, branchId, cfsTariff.getCfsTariffNo(), cfsTariff.getCfsAmndNo());

			if (savedTariff == null) {

				return ResponseEntity.badRequest().body("Cannot Upload this tariff");
			}

			int cfsAmmnendNo = Integer.parseInt(savedTariff.getCfsAmndNo());
			int newCfsAmmnendNo = cfsAmmnendNo + 1;
			String formattedCfsAmmnendNo = String.format("%03d", newCfsAmmnendNo);


			List<Object[]> comodityJar = jarDtlRepo.getJarDtlById(companyId, "J00006");
			List<Object[]> cargoTypesJar = jarDtlRepo.getJarDtlById(companyId, "J00068");
			List<Map<String, String>> comodityList = convertToValueLabelList(comodityJar);
			List<Map<String, String>> cargoTypeList = convertToValueLabelList(cargoTypesJar);
			
			List<VendorTariffSrv> excelFile = parseExcelFile(file, companyId, branchId, user, savedTariff.getCfsTariffNo(), formattedCfsAmmnendNo, comodityList, cargoTypeList);
			
			for(VendorTariffSrv dtl : excelFile)			
			
			System.out.println("dtl : " + dtl);
			
			
			
			if (excelFile.isEmpty()) {
				return ResponseEntity.badRequest().body("No Service Found");
			}

			
			
			
			
			tarrifServiceRepo.saveAll(excelFile);
			
			
			
			
			uploadTariff(companyId, branchId, savedTariff.getCfsTariffNo(), savedTariff.getCfsAmndNo(),	formattedCfsAmmnendNo, user);

			
			VendorTariff ammendedTariffBefore = (VendorTariff) savedTariff.clone();
			ammendedTariffBefore.setStatus("A");
			ammendedTariffBefore.setAmmendStatus("A");
			ammendedTariffBefore.setCfsAmndNo(formattedCfsAmmnendNo);
			tarrifRepo.save(ammendedTariffBefore);

			VendorTariff ammendedTariff = tarrifRepo.getSavedTariff(companyId, branchId, savedTariff.getCfsTariffNo(),	formattedCfsAmmnendNo);

			return ResponseEntity.ok(ammendedTariff);
//			return ResponseEntity.ok("");

		} catch (Exception e) {
			System.out.println("Error : " + e);
			return ResponseEntity.badRequest().body("Error saving in Tariff");
		}

	}
	
	
//	public List<CFSTariffService> parseExcelFile(MultipartFile file, String companyId, String branchId, String userId, String cfsTariffNo, String cfsAmmendNo, List<Map<String, String>> commodityList, List<Map<String, String>> cargoTypeList) throws Exception {
//	    List<CFSTariffService> tariffServices = new ArrayList<>();
//
//	    try (InputStream inputStream = file.getInputStream();
//	         Workbook workbook = new XSSFWorkbook(inputStream)) {
//
//	        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
//	        Iterator<Row> rowIterator = sheet.iterator();
//
//	        String currentServiceName = null;
//	        Services serviceForTariffUpload = null;
//	        int srNo = 1;
//	        while (rowIterator.hasNext()) {
//	            Row row = rowIterator.next();
//	            Cell firstCell = row.getCell(0);
//	            Cell secondCell = row.getCell(1);
//
//	            if (firstCell == null || firstCell.getCellType() == CellType.BLANK) {
//	                // Skip empty rows
//	                continue;
//	            }
//
//	            String firstCellValue = firstCell.toString().trim();
//	           
//	            
//	            // Check for "Tariff Head:"
//	            if (firstCellValue.startsWith("Tariff Head:")) {
//	            	 String secondCellValue = secondCell.toString().trim();
//	                currentServiceName = secondCellValue;	          
//	                System.out.println(" secondCellValue : " + secondCellValue);
//	                serviceForTariffUpload = serviceRepo.getServiceForTariffUpload(companyId, branchId, currentServiceName);
//	                srNo = 1;
//	                continue; // Move to next row
//	            }
//
//	           
//	            // Check if the row is a header
//	            List<String> header = getRowValues(row);
//	            if (isHeader(header)) {
//	                // Process the rows below the header
//	                while (rowIterator.hasNext()) {
//	                    row = rowIterator.next();
//	                    if (isRowEmpty(row)) {
//	                        break; // Stop on empty row (new service starts)
//	                    }
//	                    List<String> rowValues = getRowValues(row);
//	                    System.out.println("serviceForTariffUpload \n " + serviceForTariffUpload);
//	                    if(serviceForTariffUpload != null)
//	    	            {
//	                    CFSTariffService tariffService = mapToTariffService(currentServiceName, header, rowValues, serviceForTariffUpload, userId, cfsTariffNo, cfsAmmendNo, srNo, commodityList, cargoTypeList);
//	                    tariffServices.add(tariffService);
//	                    srNo++;
//	    	            }
//	                }
//	            }
//	            
//	            
//	            
//	        }
//	    }
//
//	    return tariffServices;
//	}
	
	
	
	public List<VendorTariffSrv> parseExcelFile(
	        MultipartFile file,
	        String companyId,
	        String branchId,
	        String userId,
	        String cfsTariffNo,
	        String cfsAmmendNo,
	        List<Map<String, String>> commodityList,
	        List<Map<String, String>> cargoTypeList) throws Exception {

	    List<VendorTariffSrv> tariffServices = new ArrayList<>();

	    try (InputStream inputStream = file.getInputStream();
	         Workbook workbook = new XSSFWorkbook(inputStream)) {

	        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet
	        Iterator<Row> rowIterator = sheet.iterator();

	        String currentServiceName = null;
	        APServices serviceForTariffUpload = null;
	        int srNo = 1;

	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            System.out.println("Processing Row: " + (row.getRowNum() + 1));

	            // Get values for the row
	            List<String> header = getRowValues(row);

	            // Check if the row is empty
	            if (isRowEmpty(row)) {
	                System.out.println("Empty row found at position: " + (row.getRowNum() + 1));
	                continue; // Skip the empty row and move to the next row in the outer loop
	            }

	            // Check for "Tariff Head:" in the first column (first cell value)
	            String firstCellValue = row.getCell(0) != null ? row.getCell(0).toString().trim() : "";

	            if (firstCellValue.startsWith("Tariff Head:")) {
	                String secondCellValue = row.getCell(1) != null ? row.getCell(1).toString().trim() : "";
	                currentServiceName = secondCellValue;
	                System.out.println("Tariff Head: " + secondCellValue);

	                // Process the service for tariff upload
	                serviceForTariffUpload = serviceRepo.getServiceForTariffUpload(companyId, branchId, currentServiceName);
	                srNo = 1;

	                // Skip to the next row in the outer loop but keep this row in the outer loop processing
	                continue; // This will cause the outer loop to continue processing from the same row
	            }

	            // If we are under a valid header row, process the data
	            if (isHeader(header)) {
	                // Start an inner loop to process rows below the header
	                while (rowIterator.hasNext()) {
	                    row = rowIterator.next();
	                    System.out.println("Processing Inner Row: " + (row.getRowNum() + 1));

	                    // Skip empty rows within the inner loop
	                    if (isRowEmpty(row)) {
	                        System.out.println("Empty row found at position: " + (row.getRowNum() + 1));
	                        continue; // Skip empty row and continue with the next row in the inner loop
	                    }

	                    // Get values for this row
	                    List<String> rowValues = getRowValues(row);

	                    // If the row starts with "Tariff Head:", break the inner loop and process with the outer loop
	                    firstCellValue = row.getCell(0) != null ? row.getCell(0).toString().trim() : "";
	                    if (firstCellValue.startsWith("Tariff Head:")) {
	                        String secondCellValue = row.getCell(1) != null ? row.getCell(1).toString().trim() : "";
	                        currentServiceName = secondCellValue;
	                        System.out.println("Tariff Head: " + secondCellValue);

	                        // Process the service for tariff upload
	                        serviceForTariffUpload = serviceRepo.getServiceForTariffUpload(companyId, branchId, currentServiceName);
	                        srNo = 1;

	                        // Break the inner loop but continue with the same row in the outer loop
	                        break; // This will break the inner loop and continue with the same row in the outer loop
	                    }

	                    // If serviceForTariffUpload is not null, process the row
	                    if (serviceForTariffUpload != null) {
	                    	VendorTariffSrv tariffService = mapToTariffService(
	                                currentServiceName, header, rowValues, serviceForTariffUpload,
	                                userId, cfsTariffNo, cfsAmmendNo, srNo, commodityList, cargoTypeList
	                        );
	                        tariffServices.add(tariffService);
	                        srNo++;
	                    }
	                }
	            }
	        }
	    }

	    return tariffServices;
	}

	
	
	
	
	
	

	private boolean isHeader(List<String> rowValues) {
	    // Identify headers based on known patterns
	    return rowValues.equals(List.of("Cont Size", "Cargo Type", "Commodity", "Currency", "Rate"))
	            || rowValues.equals(List.of("Weight From", "Weight To", "Cont Size", "Cargo Type", "Commodity", "Currency", "Rate"))
	            || rowValues.equals(List.of("Range From", "Range To", "Cont Size", "Cargo Type", "Commodity", "Currency", "Rate"));
	}

	private boolean isRowEmpty(Row row) {
		
		
		System.out.println(" IsRow Blank : " + row);
	    if (row == null) {
	        return true; // Row itself is null
	    }

	    for (Cell cell : row) {
	        if (cell != null) {
	            // Check if the cell has any value
	            if (cell.getCellType() != CellType.BLANK && cell.getCellType() != CellType._NONE) {
	                if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().trim().isEmpty()) {
	                    return false; // Non-empty string
	                } else if (cell.getCellType() == CellType.NUMERIC) {
	                    return false; // Numeric value
	                } else if (cell.getCellType() == CellType.BOOLEAN) {
	                    return false; // Boolean value
	                } else if (cell.getCellType() == CellType.FORMULA) {
	                    return false; // Formula with a value
	                }
	            }
	        }
	    }
	    return true; // No non-blank cells found
	}


	private List<String> getRowValues(Row row) {
	    List<String> values = new ArrayList<>();
	    for (Cell cell : row) {
	        values.add(cell.toString().trim());
	    }
	    return values;
	}

	private VendorTariffSrv mapToTariffService(String serviceName, List<String> header, List<String> rowValues, APServices service, String userId, String cfsTariffNo, String cfsAmmendNo, int srNo, List<Map<String, String>> commodityList, List<Map<String, String>> cargoTypeList) {
		VendorTariffSrv tariffService = new VendorTariffSrv();
	    tariffService.setServiceName(serviceName);
	    tariffService.setCompanyId(service.getCompanyId());
	    tariffService.setBranchId(service.getBranchId());
	    tariffService.setCfsTariffNo(cfsTariffNo);
	    tariffService.setCfsAmendNo(cfsAmmendNo);
	    tariffService.setStatus("A");
	    tariffService.setAmmendStatus("A");
	    tariffService.setCreatedBy(userId);
	    tariffService.setCreatedDate(new Date());
	    tariffService.setApprovedBy(userId);
	    tariffService.setApprovedDate(new Date());
	    tariffService.setFinYear(helperMethods.getFinancialYear());
	    tariffService.setServiceId(service.getServiceId());
	    tariffService.setSrNo(BigDecimal.valueOf(srNo));
	    tariffService.setServiceUnit(service.getServiceUnit());
	    tariffService.setServiceUnitI(service.getServiceUnit1());
	    tariffService.setProfitCentreId("N10000");
	    
	    String rangeType = "WT".equals(service.getCriteriaType()) ? "RA"
				: "DW".equals(service.getCriteriaType()) ? "SA"
						: "CNTR".equals(service.getCriteriaType()) ? "NA" : "NA";
	    
	    System.out.println("service.getCriteriaType() " + service.getCriteriaType() + " \n rangeType " + rangeType + " header \n" + header + " \n rowValues \n" + rowValues);
	    
	    tariffService.setRangeType(rangeType);
	    
	    for (int i = 0; i < header.size(); i++) {
	        String headerValue = header.get(i);
	        String rowValue = i < rowValues.size() ? rowValues.get(i) : null;

	        System.out.println("rowValue " + rowValue + " headerValue " + headerValue);
	        
	        switch (headerValue) {
	            case "Cont Size":
	                tariffService.setContainerSize(rowValue);
	                break;
	            case "Cargo Type":
	                tariffService.setCargoType(rowValue);
	                break;
	            case "Commodity":
	                tariffService.setCommodityCode(getValueForLabel(rowValue, commodityList));
	                break;
	            case "Currency":
	                tariffService.setCurrencyId(rowValue);
	                break;
	            case "Rate":
	                tariffService.setRate(convertToBigDecimal(rowValue));
	                break;
	            case "Weight From":
	                tariffService.setFromRange(convertToBigDecimal(rowValue));
	                break;
	            case "Weight To":
	                tariffService.setToRange(convertToBigDecimal(rowValue));
	                break;
	            case "Range From":
	                tariffService.setFromRange(convertToBigDecimal(rowValue));
	                break;
	            case "Range To":
	                tariffService.setToRange(convertToBigDecimal(rowValue));
	                break;
	        }
	    }

	    return tariffService;
	}
	
	
	
	public String getValueForLabel(String label, List<Map<String, String>> listOfMaps) {
		System.out.println("label " + label);
		
        for (Map<String, String> map : listOfMaps) {
        	System.out.println(map);
        	 if (label.equals(map.get("label"))) {
                 return map.get("value");
             }
        }
        // If the label is not found, return the input string
        return label;
    }
	
	
	private BigDecimal convertToBigDecimal(String value) {
	    if (value == null || value.trim().isEmpty()) {
	        return BigDecimal.ZERO; // Default to 0 if the value is null or empty
	    }
	    try {
	        return new BigDecimal(value.trim());
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Invalid number format for value: " + value, e);
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	public byte[] getAuditTrailReport(String companyId, String branchId, String cfsTariffNo, String cfsAmendNo, String contractName, String userId)
	{
		try
		{
			
			VendorTariff savedTariffAuditTrailReport = tarrifRepo.getSavedTariffAuditTrailReport(companyId, branchId, cfsTariffNo, cfsAmendNo);			
			List<VendorTariffSrv> forTariffAuditTrailReport = tarrifServiceRepo.getForTariffAuditTrailReport(companyId, branchId, cfsTariffNo);
			
			
			
			 Workbook workbook = new XSSFWorkbook();
		        
		        // Create a sheet
		        Sheet sheet = workbook.createSheet("AuditTrail Report");

		      
		        // Create a bold font for headers
		        Font boldFont = workbook.createFont();
		        boldFont.setBold(true);

		     // Create a CellStyle with medium borders
		        CellStyle borderedStyle = workbook.createCellStyle();
		        borderedStyle.setBorderTop(BorderStyle.THIN);
		        borderedStyle.setBorderBottom(BorderStyle.THIN);
		        borderedStyle.setBorderLeft(BorderStyle.THIN);
		        borderedStyle.setBorderRight(BorderStyle.THIN);

		        // Set border color to black
		        borderedStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        borderedStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        borderedStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        borderedStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        
		        // Create a CellStyle with medium borders
		        CellStyle headerStyle = workbook.createCellStyle();
		        headerStyle.setBorderTop(BorderStyle.THIN);
		        headerStyle.setBorderBottom(BorderStyle.THIN);
		        headerStyle.setBorderLeft(BorderStyle.THIN);
		        headerStyle.setBorderRight(BorderStyle.THIN);

		        // Set border color to black
		        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		        
		        
		     // Set background color to light grey
		        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		        // Set up header row
		        Row headerRow1 = sheet.createRow(0);
		        Cell headerCell = headerRow1.createCell(1);
		        headerCell.setCellValue("Tariff Audit Trail Report");
		        headerCell.setCellStyle(headerStyle);

		        // Populate data with bordered cells
		        createBorderedRow(sheet, 2, 1, "Contract Name :", savedTariffAuditTrailReport.getContractName(), 4, "Importer Name :", savedTariffAuditTrailReport.getImporterName(), borderedStyle, headerStyle);
		        createBorderedRow(sheet, 3, 1, "Customer Name :", savedTariffAuditTrailReport.getPartyName(), 4, "CHA Name :", savedTariffAuditTrailReport.getChaName(), borderedStyle, headerStyle);
		        createBorderedRow(sheet, 4, 1, "Line Name :", savedTariffAuditTrailReport.getShippingLineName(), 4, "Agent :", savedTariffAuditTrailReport.getShippingAgentName(), borderedStyle, headerStyle);
		        createBorderedRow(sheet, 5, 1, "System Generated Tariff Id :", savedTariffAuditTrailReport.getCfsTariffNo(), borderedStyle, headerStyle);
		        
		        Row seventhRow = sheet.createRow(7);
		        Cell billingHeadCell = seventhRow.createCell(1);
		        billingHeadCell.setCellValue("Billing Head Details :");
		        billingHeadCell.setCellStyle(headerStyle);

		        Cell billingHeadValueCell = seventhRow.createCell(2);
		        billingHeadValueCell.setCellValue("Amendment No (" + savedTariffAuditTrailReport.getCfsAmndNo() + ")");
		        billingHeadValueCell.setCellStyle(borderedStyle);

		        Map<String, String> serviceIdToNameMap = forTariffAuditTrailReport.stream()
		        	    .collect(Collectors.toMap(
		        	    		VendorTariffSrv::getServiceId, 
		        	    		VendorTariffSrv::getServiceName,
		        	        (existing, replacement) -> existing) // In case of duplicates, keep the existing value
		        	);

		        // Get distinct cfsAmendNo in descending order
		        List<String> distinctCfsAmendNos = forTariffAuditTrailReport.stream()
		            .map(VendorTariffSrv::getCfsAmendNo)
		            .distinct()
		            .sorted(Comparator.reverseOrder())
		            .collect(Collectors.toList());
		        
		        List<String> distinctServiceIdsWithNames = forTariffAuditTrailReport.stream()
		        	    .map(service -> service.getServiceId() + " - " + service.getServiceName())
		        	    .distinct()
		        	    .collect(Collectors.toList());


		           
		        
		        
		        
		        
		     // Set column widths for clarity
		        for (int i = 0; i < 400; i++) {
		            sheet.setColumnWidth(i, 6500);
		        }
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
		     

		        int rowIndex = 9; // Row tracking index
		        int colIndex;     // Column tracking index
		      

		        for (String serviceIdName : distinctServiceIdsWithNames) {
		            String[] parts = serviceIdName.split(" - ");
		            String serviceId = parts[0];
		            String serviceName = parts[1];

		            // Reserve a dedicated row for the Service ID
		            Row valuesRow = sheet.createRow(rowIndex++);
		            valuesRow.createCell(1).setCellValue(serviceName);
		            valuesRow.getCell(1).setCellStyle(headerStyle);

		            // Reset column index for this Service ID
		            colIndex = 2; // Start from column 1 for AmendNos
		            int maxRowForService = rowIndex; 
		            for (String amendNo : distinctCfsAmendNos) {
		                // Filter records for current Service ID and AmendNo
		                List<VendorTariffSrv> filteredRecords = forTariffAuditTrailReport.stream()
		                    .filter(record -> serviceId.equals(record.getServiceId()) && amendNo.equals(record.getCfsAmendNo()))
		                    .collect(Collectors.toList());

		                if (filteredRecords.isEmpty()) {
		                    continue; // Skip if no records for this AmendNo
		                }


		                if(!(colIndex == 2))
		                {
		                colIndex++;
		                }
		                // Add AmendNo as a header in the dedicated Service ID row
		                valuesRow.createCell(colIndex).setCellValue("Amendment No (" + amendNo + ")");
		                valuesRow.getCell(colIndex).setCellStyle(headerStyle);

		                // Determine headers based on RangeType for the current AmendNo
		                VendorTariffSrv firstRecord = filteredRecords.get(0);
		                String rangeType = firstRecord.getRangeType();

		                List<String> headers = switch (rangeType) {
		                    case "NA" -> Arrays.asList("Modified By", "Modified Date", "Cont Size", "Cargo Type", "Commodity", "Currency", "Rate", "", "");
		                    case "SA" -> Arrays.asList("Modified By", "Modified Date", "Range From", "Range To", "Cont Size", "Cargo Type", "Commodity", "Currency", "Rate");
		                    case "RA" -> Arrays.asList("Modified By", "Modified Date", "Weight From", "Weight To", "Cont Size", "Cargo Type", "Commodity", "Currency", "Rate");
		                    default -> Collections.emptyList();
		                };

		                // Add Headers in a new row below the Values Row
		                Row headerRow = sheet.getRow(rowIndex);
		                if (headerRow == null) {
		                    headerRow = sheet.createRow(rowIndex);
		                }

		                int headerColIndex = colIndex; // Start headers under the AmendNo column
		                for (String header : headers) {
		                    headerRow.createCell(headerColIndex).setCellValue(header);
		                    if (!header.isBlank()) { // Check if the header is not blank
		                        headerRow.getCell(headerColIndex).setCellStyle(headerStyle);
		                    }
		                    headerColIndex++;
		                }

		                // Increment the rowIndex to start data rows below headers
		                int dataRowIndex = rowIndex + 1; // Set data row index just below the header row

		                // Add Data Rows
		                for (VendorTariffSrv record : filteredRecords) {
		                    Row dataRow = sheet.getRow(dataRowIndex);
		                    if (dataRow == null) {
		                        dataRow = sheet.createRow(dataRowIndex);
		                    }

		                    int dataColIndex = colIndex; // Align data under AmendNo
		                    dataRow.createCell(dataColIndex).setCellValue(record.getEditedBy());
		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    if (Objects.nonNull(record.getEditedDate())) {
		                        // Convert java.util.Date to LocalDateTime
		                        LocalDateTime date = record.getEditedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		                        String formattedDate = date.format(formatter);
		                        dataRow.createCell(dataColIndex).setCellValue(formattedDate);
		                    } else {
		                        dataRow.createCell(dataColIndex).setCellValue(""); // Empty if date is null
		                    }

		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    if ("SA".equals(rangeType) || "RA".equals(rangeType)) {
		                        dataRow.createCell(dataColIndex).setCellValue(record.getFromRange() != null ? record.getFromRange().doubleValue() : 0);
		                        dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                        dataColIndex++;

		                        dataRow.createCell(dataColIndex).setCellValue(record.getToRange() != null ? record.getToRange().doubleValue() : 0);
		                        dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                        dataColIndex++;
		                    }

		                    dataRow.createCell(dataColIndex).setCellValue(record.getContainerSize());
		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    dataRow.createCell(dataColIndex).setCellValue(record.getCargoType());
		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    dataRow.createCell(dataColIndex).setCellValue(record.getCommodityCode());
		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    dataRow.createCell(dataColIndex).setCellValue(record.getCurrencyId());
		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    dataRow.createCell(dataColIndex).setCellValue(record.getRate() != null ? record.getRate().doubleValue() : 0);
		                    dataRow.getCell(dataColIndex).setCellStyle(borderedStyle);
		                    dataColIndex++;

		                    // Move to the next row for the next data entry
		                    dataRowIndex++;
		                }
		                maxRowForService = Math.max(maxRowForService, dataRowIndex);
		                // Move column index to the next AmendNo position
		                colIndex = headerColIndex;
		            }

		            // Increment rowIndex to create spacing before the next Service ID
		            rowIndex = maxRowForService + 1;
		        }

		      

		        // Create a ByteArrayOutputStream to write the workbook to
		        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		        workbook.write(outputStream);
		        workbook.close();
			
			return outputStream.toByteArray();
		}
		catch(Exception e)
		{
			System.out.print("Triff Report" + e);
			return null;
		}
	}
	
	
	
	
	private void createBorderedRow(Sheet sheet, int rowIndex, int col1, String label1, String value1, int col2, String label2, String value2, CellStyle style, CellStyle headerStyle) {
        Row row = sheet.createRow(rowIndex);
        Cell label1Cell = row.createCell(col1);
        label1Cell.setCellValue(label1);
        label1Cell.setCellStyle(headerStyle);

        Cell value1Cell = row.createCell(col1 + 1);
        value1Cell.setCellValue(value1);
        value1Cell.setCellStyle(style);

        Cell label2Cell = row.createCell(col2);
        label2Cell.setCellValue(label2);
        label2Cell.setCellStyle(headerStyle);

        Cell value2Cell = row.createCell(col2 + 1);
        value2Cell.setCellValue(value2);
        value2Cell.setCellStyle(style);
    }

    private void createBorderedRow(Sheet sheet, int rowIndex, int col1, String label1, String value1, CellStyle style, CellStyle headerStyle) {
        Row row = sheet.createRow(rowIndex);
        Cell labelCell = row.createCell(col1);
        labelCell.setCellValue(label1);
        labelCell.setCellStyle(headerStyle);

        Cell valueCell = row.createCell(col1 + 1);
        valueCell.setCellValue(value1);
        valueCell.setCellStyle(style);
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

//	AMMEND TARIFF
	@Transactional
	public ResponseEntity<?> ammendTariffController(String companyId, String branchId, CfsTarrif cfsTariff,
			String user) {
		try {

			VendorTariff savedTariff = tarrifRepo.getSavedTariffNotJoin(companyId, branchId, cfsTariff.getCfsTariffNo(),
					cfsTariff.getCfsAmndNo());

			if (savedTariff == null) {

				return ResponseEntity.badRequest().body("Cannot Ammend this tariff");
			}

			int cfsAmmnendNo = Integer.parseInt(savedTariff.getCfsAmndNo());
			int newCfsAmmnendNo = cfsAmmnendNo + 1;
			String formattedCfsAmmnendNo = String.format("%03d", newCfsAmmnendNo);

			ammendTariff(companyId, branchId, savedTariff.getCfsTariffNo(), savedTariff.getCfsAmndNo(),
					formattedCfsAmmnendNo, user);

			VendorTariff ammendedTariffBefore = (VendorTariff) savedTariff.clone();
			ammendedTariffBefore.setStatus("A");
			ammendedTariffBefore.setAmmendStatus("N");
			ammendedTariffBefore.setCfsAmndNo(formattedCfsAmmnendNo);
			tarrifRepo.save(ammendedTariffBefore);

			VendorTariff ammendedTariff = tarrifRepo.getSavedTariff(companyId, branchId, savedTariff.getCfsTariffNo(),
					formattedCfsAmmnendNo);

			return ResponseEntity.ok(ammendedTariff);

		} catch (Exception e) {
			System.out.println("Error : " + e);
			return ResponseEntity.badRequest().body("Error saving in Tariff");
		}

	}

// ADD HEADER
	@Transactional
	public ResponseEntity<?> addHeaderCfsTariff(String companyId, String branchId, VendorTariff cfsTariff, String user,
			String type) {
		Date currentDate = new Date();

		try {

			boolean existByCombosOfContractName = tarrifRepo.existByCombosOfContractName(companyId, branchId,
					cfsTariff.getContractName(), cfsTariff.getCfsTariffNo(), cfsTariff.getCfsAmndNo());

			if (existByCombosOfContractName) {

				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("type", "VALIDATION_ERROR");
				errorResponse.put("field", "contractName");
				errorResponse.put("details", Map.of("contractName", cfsTariff.getContractName()));
				errorResponse.put("message", "Duplicate ContractName: " + cfsTariff.getContractName());
				return ResponseEntity.badRequest().body(errorResponse);
			}

			boolean existByCombosOfParties = tarrifRepo.existByCombosOfParties(companyId, branchId,
					cfsTariff.getConsolerId(), cfsTariff.getPartyId(), cfsTariff.getCha(), cfsTariff.getShippingAgent(),
					cfsTariff.getShippingLine(), cfsTariff.getForwarderId(), cfsTariff.getImporterId(),
					cfsTariff.getCfsTariffNo(), cfsTariff.getCfsAmndNo());

			if (existByCombosOfParties) {

				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("type", "VALIDATION_ERROR");
				errorResponse.put("field", "combination");
				errorResponse.put("details", Map.of("Duplicate Combination of parties", cfsTariff.getPartyId()));
				errorResponse.put("message", "Duplicate Combination of parties");
				return ResponseEntity.badRequest().body(errorResponse);

			}

			VendorTariff savedTariff = new VendorTariff();

			cfsTariff.setAmmendStatus(type.equals("A") ? "A" : "N");

			if (cfsTariff.getCfsTariffNo() == null || cfsTariff.getCfsTariffNo().isEmpty()) {

				String autoCFSTariffNo = processService.autoCFSTariffNo(companyId, branchId, "P00410");

				cfsTariff.setCfsTariffNo(autoCFSTariffNo);
				cfsTariff.setStatus("A");

				cfsTariff.setCreatedBy(user);
				cfsTariff.setCreatedDate(currentDate);
				cfsTariff.setApprovedBy(user);
				cfsTariff.setApprovedDate(currentDate);
				cfsTariff.setCfsAmndNo("000");
				cfsTariff.setProfitCentreId("N10000");
				cfsTariff.setFinYear(helperMethods.getFinancialYear());
				cfsTariff.setCfsTariffDate(currentDate);		
				

				savedTariff = tarrifRepo.save(cfsTariff);
				
				
				if (cfsTariff.getApplyTariffNo() != null && !cfsTariff.getApplyTariffNo().isEmpty()) {
				  
					applyRatesOf(companyId, branchId, cfsTariff.getApplyTariffNo(), cfsTariff.getApplyAmendNo(), autoCFSTariffNo, "000", user);
					
				}
				
			}

			else {

				VendorTariff savedTariffNotJoin = tarrifRepo.getSavedTariffNotJoin(companyId, branchId, cfsTariff.getCfsTariffNo(), cfsTariff.getCfsAmndNo());				
				
				
				boolean areTariffFieldsEqual = areTariffFieldsEqual(cfsTariff, savedTariffNotJoin);
				
				System.out.println("areTariffFieldsEqual : " + areTariffFieldsEqual);

				if (!areTariffFieldsEqual) {
					VendorTariff cloneTariff = (VendorTariff) cfsTariff.clone();

					int cfsAmmnendNo = Integer.parseInt(savedTariffNotJoin.getCfsAmndNo());
					int newCfsAmmnendNo = cfsAmmnendNo + 1;
					String formattedCfsAmmnendNo = String.format("%03d", newCfsAmmnendNo);

					ammendTariff(companyId, branchId, savedTariffNotJoin.getCfsTariffNo(), savedTariffNotJoin.getCfsAmndNo(), formattedCfsAmmnendNo, user);

					cloneTariff.setCfsAmndNo(formattedCfsAmmnendNo);
					
					cloneTariff.setAmmendStatus(type.equals("A") ? "A" : "N");
					
					savedTariff = tarrifRepo.save(cloneTariff);

				}
				else
				{
					
					savedTariffNotJoin.setAmmendStatus(type.equals("A") ? "A" : "N");
					savedTariff = tarrifRepo.save(savedTariffNotJoin);				
					
				}				

			}
			
			if(type.equals("A"))
			{
				int updateSubmitCfsService = tarrifServiceRepo.updateSubmitCfsService(companyId, branchId, savedTariff.getCfsTariffNo(), savedTariff.getCfsAmndNo(), user);
			
				System.out.println("updateSubmitCfsService : " + updateSubmitCfsService);
				
			}

			VendorTariff savedTariff2 = tarrifRepo.getSavedTariff(companyId, branchId, savedTariff.getCfsTariffNo(), savedTariff.getCfsAmndNo());

			System.out.println(savedTariff2);
			return ResponseEntity.ok(savedTariff2);
		} catch (Exception e) {
			System.out.println("Error : " + e);
			return ResponseEntity.badRequest().body("Error saving in Tariff");
		}

	}

	public boolean areTariffFieldsEqual(VendorTariff screenTariff, VendorTariff dbTariff) {
		if (screenTariff == null || dbTariff == null) {
			return false;
		}

		boolean areFieldsEqual = isEqual(screenTariff.getPartyId(), dbTariff.getPartyId())
				&& isEqual(screenTariff.getStatus(), dbTariff.getStatus())
				&& isEqual(screenTariff.getContractName(), dbTariff.getContractName())
				&& isEqual(screenTariff.getShippingLine(), dbTariff.getShippingLine())
				&& isEqual(screenTariff.getCha(), dbTariff.getCha())
				&& isEqual(screenTariff.getImporterId(), dbTariff.getImporterId())
				&& isEqual(screenTariff.getExporterId(), dbTariff.getExporterId())
				&& isEqual(screenTariff.getShippingAgent(), dbTariff.getShippingAgent())
				&& isEqual(screenTariff.getForwarderId(), dbTariff.getForwarderId())
				&& isEqual(screenTariff.getConsolerId(), dbTariff.getConsolerId());

		boolean areDatesEqual = Objects.equals(screenTariff.getCfsTariffDate(), dbTariff.getCfsTariffDate())
				&& Objects.equals(screenTariff.getCfsFromDate(), dbTariff.getCfsFromDate())
				&& Objects.equals(screenTariff.getCfsValidateDate(), dbTariff.getCfsValidateDate());

		return areFieldsEqual && areDatesEqual;
	}

	private boolean isEqual(String screenValue, String dbValue) {
		return (screenValue == null || screenValue.isEmpty()) && (dbValue == null || dbValue.isEmpty())
				|| Objects.equals(screenValue, dbValue);
	}

	@Transactional
	public ResponseEntity<?> addDetailCfsTariffService(String companyId, String branchId,
			Map<String, Object> requestData, String user) {
		Date currentDate = new Date();

		VendorTariff tariffToSend = new VendorTariff();

		Map<String, Object> data = new HashMap<>();
		try {

			List<VendorTariffSrv> cfsTariffService = objectMapper.convertValue(requestData.get("cfsTariffService"),
					new TypeReference<List<VendorTariffSrv>>() {
					});

			VendorTariff cfsTariff = objectMapper.convertValue(requestData.get("cfsTariff"), VendorTariff.class);
			VendorTariff getSavedTariff = tarrifRepo.getSavedTariff(companyId, branchId, cfsTariff.getCfsTariffNo(),
					cfsTariff.getCfsAmndNo());

			if (getSavedTariff == null) {
				String errorMessage = "Tariff not found...";
				return ResponseEntity.badRequest().body(errorMessage);
			}

			if (!getSavedTariff.getStatus().equals("A") && !getSavedTariff.getAmmendStatus().equals("N")) {
				String errorMessage = "Cannot Add the Service in this Tariff";
				return ResponseEntity.badRequest().body(errorMessage);
			}

			VendorTariffSrv firstTariffService = cfsTariffService.get(0);

			boolean isUpdatable = tarrifServiceRepo.existByTariffAndAmndNoAndServiceId(companyId, branchId,
					firstTariffService.getCfsTariffNo(), firstTariffService.getCfsAmendNo(),
					firstTariffService.getServiceId());

			if (isUpdatable) {
//				Ammend the Tariff

				VendorTariff newAmmendedTariff = (VendorTariff) getSavedTariff.clone();

				int cfsAmmnendNo = Integer.parseInt(getSavedTariff.getCfsAmndNo());
				int newCfsAmmnendNo = cfsAmmnendNo + 1;
				String formattedCfsAmmnendNo = String.format("%03d", newCfsAmmnendNo);

				newAmmendedTariff.setRefTariffId(getSavedTariff.getCfsTariffNo());
				newAmmendedTariff.setRefTariffAmndId(getSavedTariff.getCfsAmndNo());

				newAmmendedTariff.setStatus("A");
				newAmmendedTariff.setAmmendStatus("N");
				newAmmendedTariff.setCfsAmndNo(formattedCfsAmmnendNo);

				VendorTariff newSavedTariff = tarrifRepo.save(newAmmendedTariff);
				tariffToSend = newSavedTariff;

				ammendTariff(companyId, branchId, getSavedTariff.getCfsTariffNo(), getSavedTariff.getCfsAmndNo(),
						formattedCfsAmmnendNo, user);

				for (VendorTariffSrv update : cfsTariffService) {

					int updateAmmendCfsService = tarrifServiceRepo.updateAmmendCfsService(companyId, branchId,
							newSavedTariff.getCfsTariffNo(), newSavedTariff.getCfsAmndNo(), update.getServiceId(),
							update.getSrNo(), update.getContainerSize(), update.getCommodityCode(),
							update.getCargoType(), update.getFromRange(), update.getToRange(), update.getRate(),
							update.getMinimumRate(), update.getDefaultChk(), user);
					if (updateAmmendCfsService == 0) {
						VendorTariffSrv clonedDetail = (VendorTariffSrv) update.clone();
						clonedDetail.setCfsAmendNo(formattedCfsAmmnendNo);

						tarrifServiceRepo.save(clonedDetail);
					}

					System.out.println(" Update :  ServiceId: " + update.getServiceId() + " srNo: " + update.getSrNo()
							+ " ------ " + updateAmmendCfsService);

				}

			} else {
				tariffToSend = getSavedTariff;

				for (VendorTariffSrv addService : cfsTariffService) {

					addService.setProfitCentreId(getSavedTariff.getProfitCentreId());
					addService.setStatus("A");
					addService.setAmmendStatus("N");
					addService.setCreatedBy(user);
					addService.setCreatedDate(currentDate);
					addService.setApprovedBy(user);
					addService.setApprovedDate(currentDate);
					addService.setFinYear(helperMethods.getFinancialYear());

					tarrifServiceRepo.save(addService);
				}

			}

			data.put("cfsTariffServer", tariffToSend);

			List<VendorTariffSrv> listOfSavedService = getSingleService(companyId, branchId,
					firstTariffService.getServiceId(), tariffToSend.getCfsTariffNo(), tariffToSend.getCfsAmndNo());

			int currentSize = listOfSavedService.size();
			if (currentSize < 7) {
				for (int i = currentSize; i < 7; i++) {
					VendorTariffSrv dummyService = cloneAndModifyService(firstTariffService, i + 1);
					listOfSavedService.add(dummyService);
				}
			}

			data.put("list", listOfSavedService);

			return ResponseEntity.ok(data);
		} catch (Exception e) {
			System.out.println("Error : " + e);
			return ResponseEntity.badRequest().body("Error saving in Tariff");
		}

	}

	private VendorTariffSrv cloneAndModifyService(VendorTariffSrv firstTariffService, int srNo)
			throws CloneNotSupportedException {
		VendorTariffSrv clonedService = (VendorTariffSrv) firstTariffService.clone();
		BigDecimal zero = BigDecimal.ZERO;
		// Modify the cloned object with your specified logic
		clonedService.setSrNo(BigDecimal.valueOf(srNo));
		clonedService.setContainerSize("");
		clonedService.setCommodityCode("");
		clonedService.setCargoType("");
		clonedService.setFromRange(BigDecimal.ZERO);
		clonedService.setToRange(BigDecimal.ZERO);
		clonedService.setRate(BigDecimal.ZERO);
		clonedService.setMinimumRate(BigDecimal.ZERO);
		clonedService.setDefaultChk("N");
		clonedService.setCurrencyId("INR");
		clonedService.setContainerSize("");
		clonedService.setCargoType("");
		clonedService.setCommodityCode("");
		clonedService.setRate(zero);
		clonedService.setFromRange(zero);
		clonedService.setToRange(zero);
		clonedService.setMinimumRate(zero);

		return clonedService;
	}

	public ResponseEntity<?> getSavedTariff(String companyId, String branchId, String tarrifNo, String cfsAmndNo) {
		VendorTariff getSavedTariffs = tarrifRepo.getSavedTariff(companyId, branchId, tarrifNo, cfsAmndNo);
		return ResponseEntity.ok(getSavedTariffs);
	}

	public ResponseEntity<?> searchSavedTariff(String companyId, String branchId, String type) {

		List<VendorTariff> savedTariffs = tarrifRepo.getSavedTariffSearch(companyId, branchId, type);

		List<Map<String, Object>> toSendGetTariffs = new ArrayList<>();
		toSendGetTariffs = savedTariffs.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put("value", row.getCfsTariffNo());
			map.put("label", row.getContractName());
			map.put("cfsAmndNo", row.getCfsAmndNo());
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(toSendGetTariffs);
	}

	public ResponseEntity<?> getPartyByTypeValue(String companyId, String branchId, String searchValue, String type) {

		List<Object[]> partyByTypeValue = partyrepo.getPartyByTypeValueTariff(companyId, branchId, searchValue, type);

		List<Map<String, Object>> toSendGetParties = new ArrayList<>();
		toSendGetParties = partyByTypeValue.stream().map(row -> {
			Map<String, Object> map = new HashMap<>();
			map.put("value", row[0]);
			map.put("label", row[1]);
			return map;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(toSendGetParties);
	}

	public ResponseEntity<?> getAllServices(String companyId, String branchId) {

		Map<String, Object> data = new HashMap<>();
		List<APServices> allServices = serviceRepo.getActiveServicesTariff(companyId, branchId);

		List<Object[]> comodityJar = jarDtlRepo.getJarDtlById(companyId, "J00006");
		List<Object[]> containerSizeJar = jarDtlRepo.getJarDtlById(companyId, "J00069");
		List<Object[]> cargoTypesJar = jarDtlRepo.getJarDtlById(companyId, "J00068");
		List<Map<String, String>> comodityList = convertToValueLabelList(comodityJar);
		List<Map<String, String>> containerList = convertToValueLabelList(containerSizeJar);
		List<Map<String, String>> cargoTypeList = convertToValueLabelList(cargoTypesJar);
		data.put("commodity", comodityList);
		data.put("container", containerList);
		data.put("cargo", cargoTypeList);
		data.put("services", allServices);

		return ResponseEntity.ok(data);
	}

	private List<Map<String, String>> convertToValueLabelList(List<Object[]> data) {
		return data.stream().map(obj -> {
			Map<String, String> map = new HashMap<>();
			map.put("value", obj[0] != null ? obj[0].toString() : "");
			map.put("label", obj[1] != null ? obj[1].toString() : "");
			return map;
		}).collect(Collectors.toList());
	}

	public List<VendorTariffSrv> getSingleService(String companyId, String branchId, String serviceId, String tariffNo,
			String cfsAmndNo) throws CloneNotSupportedException {
		List<VendorTariffSrv> updatedCfsTarrifService = new ArrayList<>();
		if (tariffNo != null) {
			List<VendorTariffSrv> savedCfsTarrifService = tarrifServiceRepo.getCFSTariffServiceByServiceId(companyId,
					branchId, tariffNo, cfsAmndNo, serviceId);
			if (savedCfsTarrifService.isEmpty()) {
				APServices singleService = serviceRepo.getActiveService(companyId, branchId, serviceId);
				if (singleService != null) {
					updatedCfsTarrifService = convertServiceToCFSTariffServiceBySaved(companyId, branchId,
							singleService, tariffNo, cfsAmndNo);
				}
			} else {
				updatedCfsTarrifService.addAll(savedCfsTarrifService);
				VendorTariffSrv firstTariffService = savedCfsTarrifService.get(0);

				int currentSize = savedCfsTarrifService.size();
				if (currentSize < 7) {
					for (int i = currentSize; i < 7; i++) {
						VendorTariffSrv dummyService = cloneAndModifyService(firstTariffService, i + 1);
						updatedCfsTarrifService.add(dummyService);
					}
				}

			}

		}

		return updatedCfsTarrifService;
	}

	public List<VendorTariffSrv> convertServiceToCFSTariffServiceBySaved(String companyId, String branchId,
			APServices service, String tariffNo, String cfsAmndNo) {
		List<VendorTariffSrv> list = new ArrayList<>();
		BigDecimal zero = BigDecimal.ZERO;

		for (int i = 0; i < 7; i++) {
			VendorTariffSrv cfService = new VendorTariffSrv();
			cfService.setServiceId(service.getServiceId());
			cfService.setServiceUnit(service.getServiceUnit());
			cfService.setServiceUnitI(service.getServiceUnit1());
			cfService.setCompanyId(companyId);
			cfService.setBranchId(branchId);
			cfService.setCurrencyId("INR");

			String rangeType = "WT".equals(service.getCriteriaType()) ? "RA"
					: "DW".equals(service.getCriteriaType()) ? "SA"
							: "CNTR".equals(service.getCriteriaType()) ? "NA" : "NA";

			cfService.setRangeType(rangeType);

			cfService.setCfsTariffNo(tariffNo);
			cfService.setCfsAmendNo(cfsAmndNo);
			cfService.setSacCode(service.getSacCode());
			cfService.setSrNo(new BigDecimal(i + 1));
			cfService.setContainerSize("");
			cfService.setCargoType("");
			cfService.setCommodityCode("");
			cfService.setRate(zero);
			cfService.setFromRange(zero);
			cfService.setToRange(zero);
			cfService.setMinimumRate(zero);

			cfService.setDefaultChk("N");

			list.add(cfService);
		}

		return list;
	}

	@Transactional
	public void ammendTariff(String companyId, String branchId, String tariffNo, String oldAmendNo, String newAmendNo,
			String user) {

		String updateQueryTariff = "UPDATE CfsTarrif t "
				+ "SET t.status = 'C', t.ammendStatus = 'C', t.editedBy = :user, t.editedDate = CURRENT_TIMESTAMP "
				+ "WHERE t.companyId = :companyId AND t.branchId = :branchId AND t.cfsTariffNo = :tariffNo AND t.cfsAmndNo = :oldAmendNo";

		entityManager.createQuery(updateQueryTariff).setParameter("tariffNo", tariffNo)
				.setParameter("oldAmendNo", oldAmendNo).setParameter("companyId", companyId).setParameter("user", user)
				.setParameter("branchId", branchId).executeUpdate();

		String updateQuery = "UPDATE CFSTariffService t "
				+ "SET t.status = 'C', t.ammendStatus = 'C', t.editedBy = :user, t.editedDate = CURRENT_TIMESTAMP  "
				+ "WHERE t.companyId = :companyId AND t.branchId = :branchId AND t.cfsTariffNo = :tariffNo AND t.cfsAmendNo = :oldAmendNo";

		entityManager.createQuery(updateQuery).setParameter("tariffNo", tariffNo).setParameter("oldAmendNo", oldAmendNo)
				.setParameter("user", user).setParameter("companyId", companyId).setParameter("branchId", branchId)
				.executeUpdate();

		String insertQuery = "INSERT INTO cfstdtrfsrv ("
				+ "Company_Id, Branch_Id, Fin_Year, CFS_Tariff_No, CFS_Amend_No, Service_Id, "
				+ "Sr_No, Container_Size, Commodity_Code, Cargo_Type, Status, Range_Type, "
				+ "profit_centre_id, Service_Unit, Service_UnitI, From_Range, To_Range, Rate, "
				+ "Minimum_Rate, Created_By, Created_Date, Edited_By, Edited_Date, Approved_By, "
				+ "Approved_Date, Currency_Id, ammend_status, Default_chk) "
				+ "SELECT t.Company_Id, t.Branch_Id, t.Fin_Year, t.CFS_Tariff_No, :newAmendNo, t.Service_Id, "
				+ "t.Sr_No, t.Container_Size, t.Commodity_Code, t.Cargo_Type, 'A', t.Range_Type, "
				+ "t.profit_centre_id, t.Service_Unit, t.Service_UnitI, t.From_Range, t.To_Range, t.Rate, "
				+ "t.Minimum_Rate, t.Created_By, t.Created_Date, t.Edited_By, t.Edited_Date, t.Approved_By, "
				+ "t.Approved_Date, t.Currency_Id, 'N', t.Default_chk " + "FROM cfstdtrfsrv t "
				+ "WHERE t.company_Id = :companyId AND t.branch_Id = :branchId AND t.CFS_Tariff_No = :tariffNo AND t.CFS_Amend_No = :oldAmendNo";

		entityManager.createNativeQuery(insertQuery).setParameter("newAmendNo", newAmendNo)
				.setParameter("tariffNo", tariffNo).setParameter("oldAmendNo", oldAmendNo)
				.setParameter("companyId", companyId).setParameter("branchId", branchId).executeUpdate();

	}

	
	
	
	@Transactional
	public void applyRatesOf(String companyId, String branchId, String oldTariffNo, String oldAmendNo, String newTariffNo, String newAmendNo, String user) {
		
		String insertQuery = "INSERT INTO cfstdtrfsrv ("
				+ "Company_Id, Branch_Id, Fin_Year, CFS_Tariff_No, CFS_Amend_No, Service_Id, "
				+ "Sr_No, Container_Size, Commodity_Code, Cargo_Type, Status, Range_Type, "
				+ "profit_centre_id, Service_Unit, Service_UnitI, From_Range, To_Range, Rate, "
				+ "Minimum_Rate, Created_By, Created_Date, Edited_By, Edited_Date, Approved_By, "
				+ "Approved_Date, Currency_Id, ammend_status, Default_chk) "
				+ "SELECT t.Company_Id, t.Branch_Id, t.Fin_Year, :newTariffNo, :newAmendNo, t.Service_Id, "
				+ "t.Sr_No, t.Container_Size, t.Commodity_Code, t.Cargo_Type, 'A', t.Range_Type, "
				+ "t.profit_centre_id, t.Service_Unit, t.Service_UnitI, t.From_Range, t.To_Range, t.Rate, "
				+ "t.Minimum_Rate, :user, CURRENT_TIMESTAMP, t.Edited_By, t.Edited_Date, t.Approved_By, "
				+ "t.Approved_Date, t.Currency_Id, 'N', t.Default_chk " + "FROM cfstdtrfsrv t "
				+ "WHERE t.company_Id = :companyId AND t.branch_Id = :branchId AND t.CFS_Tariff_No = :oldTariffNo AND t.CFS_Amend_No = :oldAmendNo";

		entityManager.createNativeQuery(insertQuery).setParameter("newAmendNo", newAmendNo).setParameter("newTariffNo", newTariffNo)
				.setParameter("oldTariffNo", oldTariffNo).setParameter("oldAmendNo", oldAmendNo).setParameter("user", user)
				.setParameter("companyId", companyId).setParameter("branchId", branchId).executeUpdate();

		
		
	}
	
	
	
	@Transactional
	public void uploadTariff(String companyId, String branchId, String tariffNo, String oldAmendNo, String newAmendNo, String user) {

		String updateQueryTariff = "UPDATE CfsTarrif t "
				+ "SET t.status = 'C', t.ammendStatus = 'C', t.editedBy = :user, t.editedDate = CURRENT_TIMESTAMP "
				+ "WHERE t.companyId = :companyId AND t.branchId = :branchId AND t.cfsTariffNo = :tariffNo AND t.cfsAmndNo = :oldAmendNo";

		entityManager.createQuery(updateQueryTariff).setParameter("tariffNo", tariffNo)
				.setParameter("oldAmendNo", oldAmendNo).setParameter("companyId", companyId).setParameter("user", user)
				.setParameter("branchId", branchId).executeUpdate();

		String updateQuery = "UPDATE CFSTariffService t "
				+ "SET t.status = 'C', t.ammendStatus = 'C', t.editedBy = :user, t.editedDate = CURRENT_TIMESTAMP  "
				+ "WHERE t.companyId = :companyId AND t.branchId = :branchId AND t.cfsTariffNo = :tariffNo AND t.cfsAmendNo = :oldAmendNo";

		entityManager.createQuery(updateQuery).setParameter("tariffNo", tariffNo).setParameter("oldAmendNo", oldAmendNo)
				.setParameter("user", user).setParameter("companyId", companyId).setParameter("branchId", branchId)
				.executeUpdate();		
	}

	
	
}
