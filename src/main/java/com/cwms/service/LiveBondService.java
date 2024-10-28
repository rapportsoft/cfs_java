package com.cwms.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiveBondService {

	
	
	@Autowired
	private com.cwms.repository.InbondCFRepositary InbondCFRepositary;
	
	
	public Sheet fillInbondSheetData(Sheet inbondSheet, Date startDate, Date endDate, String company, String branch,
			String wareHouse) {

		List<Object[]> inbondData = InbondCFRepositary.findinbondDataLiveBondInbond(company, branch, startDate,
				endDate);

		Font boldFont = inbondSheet.getWorkbook().createFont();
		boldFont.setBold(true);

		// Create cell style for headers (bold with border)
		CellStyle headerStyle = inbondSheet.getWorkbook().createCellStyle();
		headerStyle.setFont(boldFont);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// Create cell style for data rows (bold)
		CellStyle dataStyle = inbondSheet.getWorkbook().createCellStyle();
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setAlignment(HorizontalAlignment.CENTER);
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dataStyle.setWrapText(true);

		Row headerRow1 = inbondSheet.createRow(0); // First row (0-based index)
		Cell headerCell = headerRow1.createCell(0);
		headerCell.setCellValue("INTO BOND TILL " + formatDate(endDate)); // Format end date

		// Merge cells for the first row
		inbondSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11)); // Merge from column 0 to column 11 (12 columns)
		applyBorderToMergedCells(inbondSheet, 0, 0, 11);

		// Create cell style for bold font
		CellStyle boldStyle = inbondSheet.getWorkbook().createCellStyle();
		Font boldFont1 = inbondSheet.getWorkbook().createFont();
		boldFont1.setBold(true);
		boldStyle.setFont(boldFont1);
		boldStyle.setAlignment(HorizontalAlignment.CENTER);
		boldStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Apply bold font to header row
//		headerCell.setCellStyle(boldStyle);
		// Merge cells for the header

		// Add second row with the current date
		// Add second row with the current date
		Row currentDateRow = inbondSheet.createRow(1); // Second row (0-based index)
		Cell currentDateCell = currentDateRow.createCell(0);
		currentDateCell.setCellValue("Date: " + formatDate(new Date())); // Current date
//		currentDateCell.setCellStyle(headerStyle);

		// Merge cells for the second row
		inbondSheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 11)); // Merge from column 0 to column 11 (12 columns)
		applyBorderToMergedCells(inbondSheet, 1, 0, 11);

		// Apply bold font to current date row
//		currentDateCell.setCellStyle(boldStyle);

		// Add headers starting from the 4th row
		Row headerRow = inbondSheet.createRow(3); // 4th row (0-based index)
		String[] headers = { "SR NO", "IN.B.DATE", "BOND NO & DATE", "CHA NAME", "BOND EXP DATE", "BOE NO", "BOE DATE",
				"IGM", "BE WEIGHT", "WEIGHT RECVD MT", "VALUE", "DUTY" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		int serialNumber = 1; // Counter for serial number

		int lastRowIndex3 = inbondSheet.getLastRowNum();

		// Calculate the start row index for inserting new data after the existing data
		int startRowIndex3 = lastRowIndex3 + 1;

		for (Object[] rowData : inbondData) {
			Row dataRow = inbondSheet.createRow(startRowIndex3);

			// First Cell (Serial Number)
			Cell firstCell = dataRow.createCell(0);
			firstCell.setCellValue(serialNumber++);
			firstCell.setCellStyle(dataStyle);
			firstCell.getCellStyle().setWrapText(true);

			// Second Cell (IN.B.DATE)
			Cell secondCell = dataRow.createCell(1);
			secondCell.setCellValue(rowData[0] != null ? formatDateDDMMYYYY((Date) rowData[0]) : "");
			secondCell.setCellStyle(dataStyle);
			secondCell.getCellStyle().setWrapText(true);

			// Third Cell (BOND NO & DATE)
			String bondNoAndBonddate = (rowData[1] != null ? rowData[1].toString() : "") + " Date: "
					+ (rowData[2] != null ? formatDateDDMMYYYY((Date) rowData[2]) : "");
			Cell thirdCell = dataRow.createCell(2);
			thirdCell.setCellValue(bondNoAndBonddate);
			thirdCell.setCellStyle(dataStyle);
			thirdCell.getCellStyle().setWrapText(true);

			// Fourth Cell (CHA NAME)
			Cell fourthCell = dataRow.createCell(3);
			fourthCell.setCellValue(getStringValue(rowData[3]));
			fourthCell.setCellStyle(dataStyle);
			fourthCell.getCellStyle().setWrapText(true);

			// Fifth Cell (BOND EXP DATE)
			Cell fifthCell = dataRow.createCell(4);
			fifthCell.setCellValue(getStringValue(rowData[4]));
			fifthCell.setCellStyle(dataStyle);
			fifthCell.getCellStyle().setWrapText(true);

			// Sixth Cell (BOE NO)
			Cell sixthCell = dataRow.createCell(5);
			sixthCell.setCellValue(getStringValue(rowData[5]));
			sixthCell.setCellStyle(dataStyle);
			sixthCell.getCellStyle().setWrapText(true);

			// Seventh Cell (BOE DATE)
			Cell seventhCell = dataRow.createCell(6);
			seventhCell.setCellValue(rowData[6] != null ? formatDateDDMMYYYY((Date) rowData[6]) : "");
			seventhCell.setCellStyle(dataStyle);
			seventhCell.getCellStyle().setWrapText(true);

			// Eighth Cell (IGM)
			Cell eighthCell = dataRow.createCell(7);
			eighthCell.setCellValue(getStringValue(rowData[7]));
			eighthCell.setCellStyle(dataStyle);
			eighthCell.getCellStyle().setWrapText(true);

			// Ninth Cell (BE WEIGHT)
			Cell ninthCell = dataRow.createCell(8);
			ninthCell.setCellValue(getStringValue(rowData[8]));
			ninthCell.setCellStyle(dataStyle);
			ninthCell.getCellStyle().setWrapText(true);

			// Tenth Cell (WEIGHT RECVD MT)
			Cell tenthCell = dataRow.createCell(9);
			tenthCell.setCellValue(getStringValue(rowData[9]));
			tenthCell.setCellStyle(dataStyle);
			tenthCell.getCellStyle().setWrapText(true);

			// Eleventh Cell (VALUE)
			Cell eleventhCell = dataRow.createCell(10);
			eleventhCell.setCellValue(getStringValue(rowData[10]));
			eleventhCell.setCellStyle(dataStyle);
			eleventhCell.getCellStyle().setWrapText(true);

			// Twelfth Cell (DUTY)
			Cell twelfthCell = dataRow.createCell(11);
			twelfthCell.setCellValue(getStringValue(rowData[11]));
			twelfthCell.setCellStyle(dataStyle);
			twelfthCell.getCellStyle().setWrapText(true);

			startRowIndex3++;
		}
		return inbondSheet;

	}
	
	
	
	public Sheet fillExbondSheetData(Sheet exbondSheet, Date startDate, Date endDate, String company, String branch,
			String wareHouse) {

		List<Object[]> inbondData = InbondCFRepositary.findinbondDataLiveBondExbond(company, branch, startDate,
				endDate);

		Font boldFont = exbondSheet.getWorkbook().createFont();
		boldFont.setBold(true);

//		 Create cell style for headers (bold with border)
		CellStyle headerStyle = exbondSheet.getWorkbook().createCellStyle();
		headerStyle.setFont(boldFont);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// Create cell style for data rows (bold)
		CellStyle dataStyle = exbondSheet.getWorkbook().createCellStyle();
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setAlignment(HorizontalAlignment.CENTER);
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dataStyle.setWrapText(true);

		Row headerRow1 = exbondSheet.createRow(0); // First row (0-based index)
		Cell headerCell = headerRow1.createCell(0);
		headerCell.setCellValue("EX-BOND FROM  " + formatDate(startDate) + " TO " + formatDate(endDate)); // Format end
																											// date

		// Merge cells for the first row
		exbondSheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11)); // Merge from column 0 to column 11 (12 columns)
		applyBorderToMergedCells(exbondSheet, 0, 0, 17);

		// Add headers starting from the 4th row
		Row headerRow = exbondSheet.createRow(2); // 4th row (0-based index)
		String[] headers = { "SR NO", "BOND NO & DATE", "EX BOE NO", "EX BOE DATE", "IGM", "DATE", "WEIGHT MT",
				"ASS VALUE", "DUTY" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		int serialNumber = 1; // Counter for serial number

		int lastRowIndex3 = exbondSheet.getLastRowNum();

		// Calculate the start row index for inserting new data after the existing data
		int startRowIndex3 = lastRowIndex3 + 1;

		for (Object[] rowData : inbondData) {
			Row dataRow = exbondSheet.createRow(startRowIndex3);

			// First Cell (Serial Number)
			Cell firstCell = dataRow.createCell(0);
			firstCell.setCellValue(serialNumber++);
			firstCell.setCellStyle(dataStyle);
			firstCell.getCellStyle().setWrapText(true);

			String bondNoAndBonddate = (rowData[0] != null ? rowData[0].toString() : "") + " Date: "
					+ (rowData[1] != null ? formatDateDDMMYYYY((Date) rowData[1]) : "");
			// Second Cell (IN.B.DATE)
			Cell secondCell = dataRow.createCell(1);
			secondCell.setCellValue(bondNoAndBonddate);
			secondCell.setCellStyle(dataStyle);
			secondCell.getCellStyle().setWrapText(true);

			// Third Cell (BOND NO & DATE)

			Cell thirdCell = dataRow.createCell(2);
			thirdCell.setCellValue(getStringValue(rowData[2]));
			thirdCell.setCellStyle(dataStyle);
			thirdCell.getCellStyle().setWrapText(true);

			// Fourth Cell (CHA NAME)
			Cell fourthCell = dataRow.createCell(3);
			fourthCell.setCellValue(rowData[3] != null ? formatDateDDMMYYYY((Date) rowData[3]) : "");
			fourthCell.setCellStyle(dataStyle);
			fourthCell.getCellStyle().setWrapText(true);

			// Fifth Cell (BOND EXP DATE)
			Cell fifthCell = dataRow.createCell(4);
			fifthCell.setCellValue(getStringValue(rowData[4]));
			fifthCell.setCellStyle(dataStyle);
			fifthCell.getCellStyle().setWrapText(true);

			// Sixth Cell (BOE NO)
			Cell sixthCell = dataRow.createCell(5);
			sixthCell.setCellValue(rowData[5] != null ? formatDateDDMMYYYY((Date) rowData[5]) : "");
			sixthCell.setCellStyle(dataStyle);
			sixthCell.getCellStyle().setWrapText(true);

			// Seventh Cell (BOE DATE)
			Cell seventhCell = dataRow.createCell(6);
			seventhCell.setCellValue(getStringValue(rowData[6]));
			seventhCell.setCellStyle(dataStyle);
			seventhCell.getCellStyle().setWrapText(true);

			// Eighth Cell (IGM)
			Cell eighthCell = dataRow.createCell(7);
			eighthCell.setCellValue(getStringValue(rowData[7]));
			eighthCell.setCellStyle(dataStyle);
			eighthCell.getCellStyle().setWrapText(true);

			// Ninth Cell (BE WEIGHT)
			Cell ninthCell = dataRow.createCell(8);
			ninthCell.setCellValue(getStringValue(rowData[8]));
			ninthCell.setCellStyle(dataStyle);
			ninthCell.getCellStyle().setWrapText(true);

			startRowIndex3++;
		}
		return exbondSheet;

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
	public Sheet fillLivebondSheetData(Sheet livebondSheet, Date startDate, Date endDate, String company, String branch,
            String wareHouse) {

        try {
            System.out.println("Hiii");

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
                    endDate);
            System.out.println("hiii1");

            List<Object[]> getLiveReceiptsAndDisposal1 = InbondCFRepositary.getLiveReceiptsAndDisposal(company,
                    branch, startDate, endDate, aprilDate, lastDateOfPreviousMonth);

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
            headerCell2.setCellValue(" WAREHOUSE CODE : " + wareHouse);
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

            Cell receiptsHeaderCell = headersRow.createCell(4);
            receiptsHeaderCell.setCellValue("RECEIPTS");
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 4, 6));

            Cell disposalHeaderCell = headersRow.createCell(7);
            disposalHeaderCell.setCellValue("DISPOSAL");
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 9));
            		
            		  

            headersRow.createCell(10);

            Cell breakupHeaderCell = headersRow.createCell(11);
            breakupHeaderCell.setCellValue("BREAK UP OF THE PENDENCY AT THE END OF THE MONTH");
            livebondSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 11, 18));

            applyBorderToMergedCells(livebondSheet, rowIndex, 0, 18);

            rowIndex++;

            Row headersRow2 = livebondSheet.createRow(rowIndex++);

            String[] headers = { "SR.NO", "NAME OF THE BOND", "NUMBERS, DUTY & ASSESSED", "OPENING BALANCE",
                    "Upto Last Month From April", "FOR THE MONTH", "UPTO current MONTH",
                    "Upto Last Month From April", "FOR THE MONTH", "UPTO current MONTH", "CLOSING BALANCE",
                    "< 1 MONTHS", "1---3 MONTHS", "3---6 MONTHS", "6---12 MONTHS", "1-2 YEARS", "2-3 YEARS",
                    "ABOVE 3 YEARS", "Total" };

            CellStyle headerStyle = livebondSheet.getWorkbook().createCellStyle();
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            Font boldFont = livebondSheet.getWorkbook().createFont();
            boldFont.setBold(true);
            headerStyle.setFont(boldFont);

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
            
            
            
            
            
            
            List<Object[]> openingBalanceList = InbondCFRepositary.getLiveOpeningBalance1(company, branch, startDate, endDate);
            System.out.println("hiii1");

            List<Object[]> receiptsAndDisposalList = InbondCFRepositary.getLiveReceiptsAndDisposal1(company, branch, startDate, endDate, aprilDate, lastDateOfPreviousMonth);

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

	private String formatDateDDMMYYYY(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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

	private String getStringValue(Object value) {
		return value != null && !"".equals(value.toString()) ? value.toString() : "";
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
