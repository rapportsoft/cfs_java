package com.cwms.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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



import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.cwms.entities.Branch;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfexbondcrgEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.Company;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.InbondCFRepositary;
import com.cwms.service.CFSBondingReportService;
import com.cwms.service.LiveBondService;
import com.itextpdf.io.exceptions.IOException; 
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/bondingReport")
@CrossOrigin("*")
public class CFSBondingReportController {

	@Autowired
	public CFSBondingReportService cFSBondingReportService;
	
	
	@Autowired
	public InbondCFRepositary InbondCFRepositary;
	
	@Autowired
	public LiveBondService InBondService;
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	    
	    @GetMapping("/cBondcargoInventoryReport")
		public ResponseEntity<byte[]> generateExcelDoNoHistory( @RequestParam(name = "companyId") String companyId,
			       @RequestParam(name = "branchId") String branchId, 
			       @RequestParam(name = "uname") String username,
			       @RequestParam(name = "type") String type, 
			       @RequestParam(name = "cname") String companyname,
			       @RequestParam(name = "bname") String branchname, 
			       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			       @RequestParam(name = "endDate" ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate ) 
			    		   throws DocumentException, ParseException {
		
			
			byte[] excelBytes = cFSBondingReportService.createExcelReportOfExBondInventoryReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate);
			
			String fileName = "Bond cargo Inventory Report.xlsx";
			HttpHeaders headers = new HttpHeaders();
			
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", fileName);

			return ResponseEntity.ok().headers(headers).body(excelBytes);
		}
		
		
		 @GetMapping("/show")
		    public ResponseEntity<List<Cfinbondcrg>> showInventoryData(
		    		@RequestParam(name = "companyId") String companyId,
				       @RequestParam(name = "branchId") String branchId, 
				       @RequestParam(name = "uname") String username,
				       @RequestParam(name = "type") String type, 
				       @RequestParam(name = "cname") String companyname,
				       @RequestParam(name = "bname") String branchname, 
				       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
				       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate ) 
				    		   throws DocumentException, ParseException {
		        return cFSBondingReportService.getDataOfInventoryToShow(companyId, branchId, username, type, companyname, branchname, startDate, endDate);
		    }
		 
		 
		 
		 @GetMapping("/bondDepositRegister")
			public ResponseEntity<byte[]> bondDepositRegister( @RequestParam(name = "companyId") String companyId,
				       @RequestParam(name = "branchId") String branchId, 
				       @RequestParam(name = "uname") String username,
				       @RequestParam(name = "type") String type, 
				       @RequestParam(name = "cname") String companyname,
				       @RequestParam(name = "bname") String branchname, 
				       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
				       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
				       @RequestParam(name = "boeNo", required = false) String boeNo) 
				    		   throws DocumentException, ParseException {
			
				byte[] excelBytes = cFSBondingReportService.createExcelReportOfBondDepositRegister(companyId,branchId,username,type,companyname,branchname,startDate, endDate,boeNo);
				
				System.out.println("excelBytes_____________________"+excelBytes);
				String fileName = "Bond Deposit Register.xlsx";
				HttpHeaders headers = new HttpHeaders();
				
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.setContentDispositionFormData("attachment", fileName);

				return ResponseEntity.ok().headers(headers).body(excelBytes);
			}
			
			
			 @GetMapping("/nocRegister")
				public ResponseEntity<byte[]> nocRegister( @RequestParam(name = "companyId") String companyId,
					       @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
					       @RequestParam(name = "boeNo", required = false) String boeNo) 
					    		   throws DocumentException, ParseException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfnocDepositRegister(companyId,branchId,username,type,companyname,branchname,startDate, endDate,boeNo);
					
					System.out.println("excelBytes_____________________"+excelBytes);
					String fileName = "Bond Deposit Register.xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
			
			
				 @GetMapping("/showNocRegister")
				    public ResponseEntity<List<Object[]>> showNocRegister(
				    		@RequestParam(name = "companyId") String companyId,
						       @RequestParam(name = "branchId") String branchId, 
						       @RequestParam(name = "uname") String username,
						       @RequestParam(name = "type") String type, 
						       @RequestParam(name = "cname") String companyname,
						       @RequestParam(name = "bname") String branchname, 
						       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
						       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
						       @RequestParam(name = "boeNo", required = false) String boeNo) 
						    		   throws DocumentException, ParseException {
				        return cFSBondingReportService.showNocDepositeRegisterReport(companyId, branchId, username, type, companyname, branchname, startDate, endDate,boeNo);
				    }
			
			
			
			 @GetMapping("/showBondDepositRegister")
			    public ResponseEntity<List<Cfinbondcrg>> showBondDepositRegister(
			    		@RequestParam(name = "companyId") String companyId,
			    		   @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
					       @RequestParam(name = "boeNo", required = false) String boeNo) 
					    		   throws DocumentException, ParseException {
			        return cFSBondingReportService.showBondDepositRegister(companyId, branchId, username, type, companyname, branchname, startDate, endDate,boeNo);
			    }
			 
			 @GetMapping("/bondDeliveryRegister")
				public ResponseEntity<byte[]> bondDeliveryRegister(
						@RequestParam(name = "companyId") String companyId,
			    		   @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
					       @RequestParam(name = "boeNo", required = false) String boeNo,
			    @RequestParam(name = "exBondBeNo", required = false) String exBondBeNo) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfBondDeliveryRegister(companyId, branchId, username, type, companyname, branchname, startDate, endDate,boeNo,exBondBeNo);
					
					String fileName = "Bond Delivery Register.xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
				
				
				 @GetMapping("/showBondDeliveryRegister")
				    public ResponseEntity<List<CfExBondCrg>> getDataForBondDeliveryReport(
				    		@RequestParam(name = "companyId") String companyId,
				    		   @RequestParam(name = "branchId") String branchId, 
						       @RequestParam(name = "uname") String username,
						       @RequestParam(name = "type") String type, 
						       @RequestParam(name = "cname") String companyname,
						       @RequestParam(name = "bname") String branchname, 
						       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
						       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
						       @RequestParam(name = "boeNo", required = false) String boeNo,
				    @RequestParam(name = "exBondBeNo", required = false) String exBondBeNo) throws DocumentException {
					
				        return cFSBondingReportService.getDataForBondDeliveryReport(companyId, branchId, username, type, companyname, branchname, startDate, endDate,boeNo,exBondBeNo);
				    }
				
				
				
				
				
				
				@GetMapping("/liveBondReport")
				public ResponseEntity<byte[]> liveBondReport( @RequestParam(name = "companyId") String companyId,
					       @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
					       @RequestParam(name = "section49",required = false) String section49) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfLiveBondReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate,section49);
					
					String fileName = "Live Bond Report.xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}

				
				@GetMapping("/expiredBondReport")
				public ResponseEntity<byte[]> createExcelReportOfExpiredbondreport( @RequestParam(name = "companyId") String companyId,
					       @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfExpiredbondreport(companyId,branchId,username,type,companyname,branchname,startDate,endDate);
					
					String fileName = "Expired Bond Report.xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
				
				
				
				
				
				@GetMapping("/sectionLiveBondReport")
				public ResponseEntity<byte[]> createExcelReportOfSectionLiveBondReport( @RequestParam(name = "companyId") String companyId,
					       @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "exBondingId") String exBondingId) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfSection49LiveBondReport(companyId,branchId,username,type,companyname,branchname,exBondingId);
					
					String fileName = "Section 49 Live Bond Report" + exBondingId + ".xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
				

				 @GetMapping("/showBondSectionExpiredBondReport")
				    public ResponseEntity<List<Cfinbondcrg>> showBondSectionExpiredBondReport(
				    		@RequestParam(name = "companyId") String companyId,
				    		   @RequestParam(name = "branchId") String branchId, 
						       @RequestParam(name = "uname") String username,
						       @RequestParam(name = "type") String type, 
						       @RequestParam(name = "cname") String companyname,
						       @RequestParam(name = "bname") String branchname, 
						       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
						       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) throws DocumentException {
					
				        return cFSBondingReportService.getDataOfSection49ExpiredBondToShow(companyId, branchId, username, type, companyname, branchname, startDate, endDate);
				    }
				
				
				
				
				@GetMapping("/sectionExpiredBondReport")
				public ResponseEntity<byte[]> createExcelReportOfSection49ExpiredBondReport( @RequestParam(name = "companyId") String companyId,
					       @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate", required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfSection49ExpiredBondReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate);
					
					String fileName = "Section 49 Expired Bond Report.xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
				
				 @GetMapping("/showBondExpiredBondData")
				    public ResponseEntity<List<Cfinbondcrg>> getDataOfExpiredBondToShow(
				    		@RequestParam(name = "companyId") String companyId,
				    		   @RequestParam(name = "branchId") String branchId, 
						       @RequestParam(name = "uname") String username,
						       @RequestParam(name = "type") String type, 
						       @RequestParam(name = "cname") String companyname,
						       @RequestParam(name = "bname") String branchname, 
						       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
						       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) throws DocumentException {
					
				        return cFSBondingReportService.getDataOfExpiredBondToShow(companyId, branchId, username, type, companyname, branchname, startDate, endDate);
				    }
				
				
				
				
				
				
				@GetMapping("/auditTrailReport")
				public ResponseEntity<byte[]> createExcelReportOfAuditTrailReport( @RequestParam(name = "companyId") String companyId,
					       @RequestParam(name = "branchId") String branchId, 
					       @RequestParam(name = "uname") String username,
					       @RequestParam(name = "type") String type, 
					       @RequestParam(name = "cname") String companyname,
					       @RequestParam(name = "bname") String branchname, 
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
					       @RequestParam(name = "boeNo",required = false ) String boeNo) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfAuditTrailReport(companyId,branchId,username,type,companyname,branchname,startDate,endDate,boeNo);
					
					String fileName = "AuditTrail Report.xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
				
				
				 @GetMapping("/showAuditTrailReport")
				    public ResponseEntity<List<CfexbondcrgEdit>> showAuditTrailReport(
				    		@RequestParam(name = "companyId") String companyId,
				    		 @RequestParam(name = "branchId") String branchId, 
						       @RequestParam(name = "uname") String username,
						       @RequestParam(name = "type") String type, 
						       @RequestParam(name = "cname") String companyname,
						       @RequestParam(name = "bname") String branchname, 
						       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
						       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
						       @RequestParam(name = "boeNo",required = false ) String boeNo) throws DocumentException {
					
				        return cFSBondingReportService.showAuditTrailReport(companyId, branchId, username, type, companyname, branchname, startDate, endDate,boeNo);
				    }
				
				
				@GetMapping("/downLoadmontlyReport")
				public ResponseEntity<?> downLoadMonthlyreport(@RequestParam(name = "companyId", required = false) String companyId,
						@RequestParam(name = "branchId", required = false) String branchId,
						@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
						@RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
						@RequestParam(name = "section49", required = false) String section49) {
					try {
								
						  // Create a new workbook
				        Workbook workbook = new XSSFWorkbook();

				        // Create INBOND sheet and fill data
				        Sheet inbondSheet = workbook.createSheet("INBOND");
				        Sheet fillInbondSheetData = InBondService.fillInbondSheetData(inbondSheet, startDate, endDate,companyId,branchId );

				        // Create EXBOND sheet and fill data
				        Sheet exbondSheet = workbook.createSheet("EXBOND");
				        Sheet fillExbondSheetData = InBondService.fillExbondSheetData(exbondSheet, startDate, endDate,companyId,branchId);

				        // Create LIVEBOND sheet and fill data
				        Sheet livebondSheet = workbook.createSheet("LIVEBOND");
				        Sheet fillLivebondSheetData = InBondService.fillLivebondSheetData(livebondSheet, startDate, endDate,companyId,branchId,section49);

				        // Adjust column sizes (optional)
				        for (int i = 0; i < 13; i++) {
				        	fillInbondSheetData.autoSizeColumn(i);
				        	fillExbondSheetData.autoSizeColumn(i);
				        	fillLivebondSheetData.autoSizeColumn(i);
				        }

				        // Create a ByteArrayOutputStream to write the workbook to
				        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				        workbook.write(outputStream);

				     // Set headers for the response
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
						headers.setContentDispositionFormData("attachment", "export_data.xlsx");

						// Return the Excel file as a byte array in the response body
						return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
			} catch (Exception e) {
				        // Handle exceptions appropriately
				        return ResponseEntity.badRequest().body("Error generating Excel file");
				    }
				}
				
				
				
				
				
				
				
				
				
				private void createHeaderContent(HSSFSheet sheet, HSSFWorkbook workbook,String companyName,String branchAdd,Branch branch) {
					// Create a cell style for the header content
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER); // Align content to the center

					// Create a font and set it to bold
					HSSFFont font = workbook.createFont();
					font.setBold(true);
					style.setFont(font);

					// Define the header content
					String[] headerContent = { companyName, 
							"W/H Code: "+branch.getBondCode(),
							branchAdd,
							"Mobile Number: " + branch.getPhoneNo() + " | E-mail: " + branch.getEmailId(),
							"Form-A",
							"Form to be maintained by the warehouse licensee of the receipt, handling, storing and removal of the warehouse goods.",
							"(in terms of Circular No. 25 / 2016 - Customs dated 08.06.2016)" };

					// Set the starting row and column index
					int rowIndex = 0;
					int columnIndex = 0;
					// Loop through the header content
					for (String content : headerContent) {
						HSSFRow row = sheet.getRow(rowIndex);
						if (row == null) {
							row = sheet.createRow(rowIndex);
						}

						// Create cells for each piece of data and merge them across multiple columns
						HSSFCell cell = row.createCell(columnIndex);
						cell.setCellValue(content);
						cell.setCellStyle(style);

						// Merge cells to simulate multiple columns for the header content
						sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 19));// Merge
																														// across 6
																														// columns
																														// (0 to 5)

						rowIndex++; // Move to the next row for the next piece of data
					}
				}

				// Add date rows for a sheet
				private void addDateRows(HSSFSheet sheet, Date fromDate, Date toDate) {
					// Create a cell style for the date rows
					HSSFWorkbook workbook = sheet.getWorkbook();
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER); // Align content to the center

					// Format start date with time 00:00
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

					// Format start date with time 00:00 and end date with time 23:59
					String formattedFromDate = dateFormat.format(fromDate) + " : 00.00";
					String formattedToDate = dateFormat.format(toDate) + " : 23.59";

					HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

					// Create cells for the date values and merge them across columns
					HSSFCell fromCell = row.createCell(0);
					fromCell.setCellValue("Report From Date: " + formattedFromDate);
					fromCell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 4));

					HSSFCell toCell = row.createCell(12);
					toCell.setCellValue("Report End Date: " + formattedToDate);
					toCell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 12, 19));
				}

				private void addTable(HSSFSheet sheet) {
					// Set the starting row and column index for the table
					int startRowIndex = sheet.getLastRowNum() + 1; // Adjust the starting row as needed
					int startColumnIndex = 0;

					// Create the table header row
					HSSFRow headerRow = sheet.createRow(startRowIndex);

					// Set the specified column lengths for each header
					int receiptColumns = 17;
					int handlingStorageColumns = 4;
					int removalColumns = 4;
					int otherRemovalsColumns = 5;
					int balanceColumns = 4;

					HSSFWorkbook workbook = sheet.getWorkbook();
					HSSFCellStyle boldCenterStyle = workbook.createCellStyle();
					HSSFFont font = workbook.createFont();
					font.setBold(true);
					boldCenterStyle.setFont(font);
					boldCenterStyle.setAlignment(HorizontalAlignment.CENTER);

					// Create and set the subheaders for each section
					String[] receiptsSubheaders = { "Bill of Entry No. and date", "Customs Station of import", "Bond No. and date",
							"Description of goods", "Description and No. of packages", "Marks and numbers on packages",
							"Unit Weight and quantity", "Value", "Duty assessed", "Date of order under Section 60(1)",
							"Warehouse code and address (in case of bond to bond transfer)",
							"Registration No. of means of transport", "OTL No.", " Date & Time Of Deposit", " Quantity received",
							"Breakage / damage", "Shortage" };
					setHeaderWithSubheaders(headerRow, startColumnIndex, receiptColumns, boldCenterStyle, "Receipts",
							receiptsSubheaders);

					String[] handlingStorageSubheaders = { "Date Of Expiry and Initial Bonding Period",
							"Date Of Expiry Of Extended Bonding Period", "Activities Undertaken Under Section 64 ",
							"Sample Drawn By Government Agencies (With Date Time)" };
					setHeaderWithSubheaders(headerRow, startColumnIndex + receiptColumns, handlingStorageColumns, boldCenterStyle,
							"Handling and Storage", handlingStorageSubheaders);

					String[] removalSubheaders = { "DATE AND TIME OF REMOVAL", "QUANTITY CLEARED", "VALUE", "DUTY" };
					setHeaderWithSubheaders(headerRow, startColumnIndex + receiptColumns + handlingStorageColumns, removalColumns,
							boldCenterStyle, "Removal", removalSubheaders);

					String[] otherRemovalsSubheaders = { "Exbond Bill of Entry No and Date/Shipping Bill No. and date",
							"Following Codes To Be Used :      WH:   Transfer To Another Warehouse EX : Export  OS: Others",
							"Date And Time", "Quantity", "Value", "Duty" };
					setHeaderWithSubheaders(headerRow, startColumnIndex + receiptColumns + handlingStorageColumns + removalColumns,
							otherRemovalsColumns, boldCenterStyle, "Other Removals", otherRemovalsSubheaders);

					String[] balanceInWarehouseSubheaders = { "Duty", "Balance quantity", "Balance Value", "Balance Duty",
							"Remarks" };
					setHeaderWithSubheaders(headerRow,
							startColumnIndex + receiptColumns + handlingStorageColumns + removalColumns + otherRemovalsColumns,
							balanceColumns, boldCenterStyle, "Balance In Warehouse", balanceInWarehouseSubheaders);

					// Add the Remarks header
//				    HSSFCell remarksCell = headerRow.createCell(startColumnIndex + receiptColumns + handlingStorageColumns + removalColumns + otherRemovalsColumns + balanceColumns);
//				    remarksCell.setCellValue("Remarks");
//				    remarksCell.setCellStyle(boldCenterStyle);

					// Apply bold border to the table
					applyBoldBorder(sheet, startRowIndex, startColumnIndex,
							receiptColumns + handlingStorageColumns + removalColumns + otherRemovalsColumns + balanceColumns + 1,
							boldCenterStyle);
					
					sheet.createFreezePane(0, startRowIndex + 2);
				}

				private void setHeader(HSSFRow headerRow, int startColumnIndex, int headerLength, HSSFCellStyle style,
						String headerText) {
					HSSFCell headerCell = headerRow.createCell(startColumnIndex);
					headerCell.setCellValue(headerText);
					headerCell.setCellStyle(style);
					headerRow.getSheet().addMergedRegion(new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(),
							startColumnIndex, startColumnIndex + headerLength - 1));
				}

				private void applyBoldBorder(HSSFSheet sheet, int startRowIndex, int startColumnIndex, int totalColumns,
						HSSFCellStyle style) {
					HSSFWorkbook workbook = sheet.getWorkbook();
//				    HSSFCellStyle style = workbook.createCellStyle();
					style.setBorderTop(BorderStyle.THIN);
					style.setBorderBottom(BorderStyle.THIN);
					style.setBorderLeft(BorderStyle.THIN);
					style.setBorderRight(BorderStyle.THIN);

					HSSFRow row = sheet.getRow(startRowIndex);
					if (row == null) {
						row = sheet.createRow(startRowIndex);
					}

					// Apply bold border to cells and keep existing center alignment
					for (int j = startColumnIndex; j < startColumnIndex + totalColumns; j++) {
						HSSFCell cell = row.getCell(j);
						if (cell == null) {
							cell = row.createCell(j);
						}
						HSSFCellStyle cellStyle = workbook.createCellStyle();
						cellStyle.cloneStyleFrom(cell.getCellStyle()); // Preserve existing style
						cellStyle.cloneStyleFrom(style); // Add bold border
						cell.setCellStyle(cellStyle);
					}
				}

				private void setHeaderWithSubheaders(HSSFRow headerRow, int startColumnIndex, int headerLength, HSSFCellStyle style,
						String headerText, String[] subheaders) {
					// Set the main header
					setHeader(headerRow, startColumnIndex, headerLength, style, headerText);

					// Set the subheaders
					int subheaderRowIndex = headerRow.getRowNum() + 1; // Move to the next row for subheaders
					int subheaderColumnIndex = startColumnIndex;
					HSSFWorkbook workbook = headerRow.getSheet().getWorkbook();
					HSSFSheet sheet = headerRow.getSheet();
					HSSFRow subheaderRow = sheet.getRow(subheaderRowIndex);
					HSSFCellStyle subheaderStyle = workbook.createCellStyle();
					subheaderStyle.cloneStyleFrom(style); // Copy the header style
					subheaderStyle.setFont(workbook.createFont()); // Set a new non-bold font for subheaders

					subheaderStyle.setBorderTop(BorderStyle.THIN);
					subheaderStyle.setBorderBottom(BorderStyle.THIN);
					subheaderStyle.setBorderLeft(BorderStyle.THIN);
					subheaderStyle.setBorderRight(BorderStyle.THIN);

					if (subheaderRow == null) {
						subheaderRow = sheet.createRow(subheaderRowIndex);
					}

					for (String subheader : subheaders) {
						HSSFCell subheaderCell = subheaderRow.createCell(subheaderColumnIndex);
						subheaderCell.setCellValue(subheader);
						subheaderCell.setCellStyle(subheaderStyle);
						subheaderCell.getCellStyle().setFont(sheet.getWorkbook().createFont()); // Remove bold by setting a new
																								// non-bold font
						subheaderCell.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
						subheaderCell.getCellStyle().setVerticalAlignment(VerticalAlignment.CENTER); // Set vertical alignment

						subheaderCell.getCellStyle().setWrapText(true); // Enable text wrapping
						subheaderColumnIndex++;
					}

					// Set a static height for the entire subheader row (in points)
					float staticSubheaderRowHeight = 160.0f; // Set your desired height
					subheaderRow.setHeightInPoints(staticSubheaderRowHeight);
				}
	
				
				
				
				
				
				
				private void createHeaderContentFormB(HSSFSheet sheet, HSSFWorkbook workbook,String companyName,String branchAdd,Branch branch) {
					// Create a cell style for the header content
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER); // Align content to the center

					// Create a font and set it to bold
					HSSFFont font = workbook.createFont();
					font.setBold(true);
					style.setFont(font);
					style.setWrapText(true);
					// Define the header content
					
					String[] headerContent = { companyName, 
							"W/H Code: "+branch.getBondCode(),
							branchAdd,
							"Mobile Number: " + branch.getPhoneNo() + " | E-mail: " + branch.getEmailId(),
							"Form-B",
							"(See Para 3 of Circular No 25 /2016-Customs dated 08.06.2016)",
					"Details of goods stored in the warehouse where the period for which they may remain warehoused under section 61 is expiring in the following month." };

					
			
					// Set the starting row and column index
					int rowIndex = 0;
					int columnIndex = 0;

					// Loop through the header content
					for (String content : headerContent) {
						HSSFRow row = sheet.getRow(rowIndex);
						if (row == null) {
							row = sheet.createRow(rowIndex);
						}

						// Create cells for each piece of data and merge them across multiple columns
						HSSFCell cell = row.createCell(columnIndex);
						cell.setCellValue(content);
						cell.setCellStyle(style);

						// Merge cells to simulate multiple columns for the header content
						sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 12));

						rowIndex++; // Move to the next row for the next piece of data
					}

					// Set the height of the 7th row
					int seventhRowIndex = 6; // 0-based index
					HSSFRow seventhRow = sheet.getRow(seventhRowIndex);
					if (seventhRow != null) {
						seventhRow.setHeightInPoints(30); // Set the desired height in points
					}
				}

				// Add date rows for a sheet
				private void addDateRowsFormB(HSSFSheet sheet, Date fromDate, Date toDate) {
					// Create a cell style for the date rows
					HSSFWorkbook workbook = sheet.getWorkbook();
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER); // Align content to the center

					// Format start date with time 00:00
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

					// Format start date with time 00:00 and end date with time 23:59
					String formattedFromDate = dateFormat.format(fromDate) + " : 00.00";
					String formattedToDate = dateFormat.format(toDate) + " : 23.59";

					HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

					// Create cells for the date values and merge them across columns
					HSSFCell fromCell = row.createCell(0);
					fromCell.setCellValue("Report From Date: " + formattedFromDate);
					fromCell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 3));

					HSSFCell toCell = row.createCell(8);
					toCell.setCellValue("Report End Date: " + formattedToDate);
					toCell.setCellStyle(style);
					sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 8, 12));
				}

				private void createTableAfterLastRow(HSSFSheet formBSheet, HSSFWorkbook workbook) {
					String[] mainTableHeaders = { "Bill of Entry No. and date", "Bond No. and date", "Name Of Importer",
							"Name of Cha", "Date of order under Section 60(1)", "Balance goods in the warehouse",
							"Date of expiry of initial bonding period", "Details of extensions(Period extended up to)", "Remarks" };

					// Create the first two rows for headers
					HSSFRow firstHeaderRow = formBSheet.createRow(formBSheet.getLastRowNum() + 1);
					HSSFRow secondHeaderRow = formBSheet.createRow(formBSheet.getLastRowNum() + 1);

					int currentCellIndex = 0; // Current cell index

					// Print headers in the first row
					for (int i = 0; i < mainTableHeaders.length; i++) {
						HSSFCell firstRowCell = firstHeaderRow.createCell(currentCellIndex);
						firstRowCell.setCellValue(mainTableHeaders[i]);

						if (mainTableHeaders[i].equals("Balance goods in the warehouse")) {
							// Merge cells for "Balance goods in the warehouse" across five cells
							formBSheet.addMergedRegion(new CellRangeAddress(firstHeaderRow.getRowNum(), // From row index (first
																										// header row)
									firstHeaderRow.getRowNum(), // To row index (first header row)
									currentCellIndex, // From column index
									currentCellIndex + 4 // To column index (inclusive)
							));

							// Add subheaders in the second row
							String[] subHeaders = { "SL. NO. Of The Import INVOICE NO.", "DESCRIPTION OF GOODS", "QUANTITY",
									"Value", "Duty" };
							for (String subHeader : subHeaders) {
								HSSFCell subHeaderCell = secondHeaderRow.createCell(currentCellIndex);
								subHeaderCell.setCellValue(subHeader);
								currentCellIndex++;
							}
						} else {
							// Merge cells for all other headers across two rows
							formBSheet.addMergedRegion(new CellRangeAddress(firstHeaderRow.getRowNum(), // From row index (first
																										// header row)
									secondHeaderRow.getRowNum(), // To row index (second header row)
									currentCellIndex, // From column index
									currentCellIndex // To column index (same column)
							));

							// Add the header to the second row
							HSSFCell secondRowCell = secondHeaderRow.createCell(currentCellIndex);
							secondRowCell.setCellValue(mainTableHeaders[i]);
							currentCellIndex++;
						}
					}

					// Set the height of the first row manually
					firstHeaderRow.setHeightInPoints(20); // Set the desired height for the first row

					// Set the height of the second row manually
					secondHeaderRow.setHeightInPoints(65); // Set the desired height for the second row

					// Create cell style after merging cells
					HSSFCellStyle headerStyle = workbook.createCellStyle();
					headerStyle.setBorderBottom(BorderStyle.THIN);
					headerStyle.setBorderTop(BorderStyle.THIN);
					headerStyle.setBorderLeft(BorderStyle.THIN);
					headerStyle.setBorderRight(BorderStyle.THIN);
					headerStyle.setAlignment(HorizontalAlignment.CENTER);
					headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					headerStyle.setWrapText(true);

					// Apply cell style to each cell in the first and second header rows
					for (int i = 0; i < secondHeaderRow.getLastCellNum(); i++) {
						HSSFCell firstHeaderCell = firstHeaderRow.getCell(i);
						if (firstHeaderCell == null) {
							firstHeaderCell = firstHeaderRow.createCell(i);
						}
						firstHeaderCell.setCellStyle(headerStyle);

						HSSFCell secondHeaderCell = secondHeaderRow.getCell(i);
						if (secondHeaderCell == null) {
							secondHeaderCell = secondHeaderRow.createCell(i);
						}
						secondHeaderCell.setCellStyle(headerStyle);
					}
					
					
					
					
					
					formBSheet.createFreezePane(0, firstHeaderRow.getRowNum() + 2);
				}

			//Header for the  0-90 Days sheet

				// Create the header content for each sheet
				private void createHeaderContent0To90DaysSheet(HSSFSheet sheet, HSSFWorkbook workbook,String companyName,String branchAdd,Branch branch) {
					// Create a cell style for the header content
					HSSFCellStyle style = workbook.createCellStyle();
					style.setAlignment(HorizontalAlignment.CENTER); // Align content to the center

					// Create a font and set it to bold
					HSSFFont font = workbook.createFont();
					font.setBold(true);
					style.setFont(font);
					style.setWrapText(true);
					// Define the header content
				
					String[] headerContent = { companyName, 
							"W/H Code: "+branch.getBondCode(),
							branchAdd,
							"Mobile Number: " + branch.getPhoneNo() + " | E-mail: " + branch.getEmailId(),
							"0-90 Days",
							"(See Para 3 of Circular No 25 /2016-Customs dated 08.06.2016)",
							"Details of goods stored in the warehouse where the period for which they may remain warehoused under section 61 is expiring in the following month." };

					// Set the starting row and column index
					int rowIndex = 0;
					int columnIndex = 0;

					// Loop through the header content
					for (String content : headerContent) {
						HSSFRow row = sheet.getRow(rowIndex);
						if (row == null) {
							row = sheet.createRow(rowIndex);
						}

						// Create cells for each piece of data and merge them across multiple columns
						HSSFCell cell = row.createCell(columnIndex);
						cell.setCellValue(content);
						cell.setCellStyle(style);

						// Merge cells to simulate multiple columns for the header content
						sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 12));

						rowIndex++; // Move to the next row for the next piece of data
					}

					// Set the height of the 7th row
					int seventhRowIndex = 6; // 0-based index
					HSSFRow seventhRow = sheet.getRow(seventhRowIndex);
					if (seventhRow != null) {
						seventhRow.setHeightInPoints(30); // Set the desired height in points
					}
				}
				
				



private BigDecimal getBigDecimalValue(Object[] rowData, int index) {
    try {
        return (rowData != null && rowData.length > index && rowData[index] != null) 
            ? new BigDecimal(rowData[index].toString()) 
            : BigDecimal.ZERO; // Default to BigDecimal.ZERO if null
    } catch (NumberFormatException e) {
        System.err.println("Invalid number format at index " + index + ": " + rowData[index]);
        return BigDecimal.ZERO;
    }
}

				@GetMapping("/downLoadformAB")
				public ResponseEntity<String> downLoadFormAFormBData(
						@RequestParam(name = "companyId", required = false) String companyId,
						@RequestParam(name = "branchId", required = false) String branchId,
						@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
						@RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
						@RequestParam(name = "cname", required = false) String cname,
						@RequestParam(name = "bname", required = false) String bname) throws java.io.IOException {
					try (HSSFWorkbook workbook = new HSSFWorkbook()) {

						List<Object[]> bondingData = InbondCFRepositary.getCustomQueryResults(companyId, branchId, startDate, endDate);

						
				        String companyName = cname;
				      

//				        Company companyAddress = companyRepo.findByCompany_Id(companyId);
				        Branch branchAddress = branchRepo.findByBranchId(branchId);

//				        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
//				                + companyAddress.getAddress_3() + companyAddress.getCity();

				        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
				                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

				        
						// Create "Form A" sheet
						HSSFSheet formASheet = workbook.createSheet("Form A");
						createHeaderContent(formASheet, workbook,companyName,branchAdd,branchAddress);

						addDateRows(formASheet, startDate, endDate);

						addTable(formASheet);

						int lastRowIndex = formASheet.getLastRowNum();

						// Calculate the start row index for inserting new data after the existing data
						int startRowIndex = lastRowIndex + 1; // Start inserting data after the last existing row

						// Get or create a cell style for your data
						HSSFCellStyle dataCellStyle = workbook.createCellStyle(); // Create a cell style for data
						dataCellStyle.setBorderTop(BorderStyle.THIN);
						dataCellStyle.setBorderBottom(BorderStyle.THIN);
						dataCellStyle.setBorderLeft(BorderStyle.THIN);
						dataCellStyle.setBorderRight(BorderStyle.THIN);

						dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
						dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
						// Normal font
						HSSFFont normalFont = workbook.createFont();
						normalFont.setFontHeightInPoints((short) 10); // Normal font size
						dataCellStyle.setFont(normalFont);

						  CellStyle numberCellStyle = workbook.createCellStyle();
						  CreationHelper createHelper = workbook.getCreationHelper();
					        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
					        numberCellStyle.setBorderBottom(BorderStyle.THIN);
					        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
					        numberCellStyle.setBorderTop(BorderStyle.THIN);
					        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
					        numberCellStyle.setBorderLeft(BorderStyle.THIN);
					        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
					        numberCellStyle.setBorderRight(BorderStyle.THIN);
					        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

					        
//						Set<String> mergedRegionSet = new HashSet();
						Map<String, List<Object[]>> boeNoDataMap = new HashMap<>();

						List<CellRangeAddress> regionsToMerge = new ArrayList<>();
						// Populate the map
						for (Object[] rowData : bondingData) {
							String boeNo = rowData[0] != null ? rowData[0].toString() : "";
							boeNoDataMap.computeIfAbsent(boeNo+"", k -> new ArrayList<>()).add(rowData);
						}

						for (List<Object[]> boeNoData : boeNoDataMap.values()) {
							String previousBoeNo = null;
							int mergedRegionStartIndex = 0;
							int boeNoCount = 0;

							for (Object[] rowData : boeNoData) {
								HSSFRow dataRow = formASheet.createRow(startRowIndex);

								String currentBoeNo = rowData[0] != null ? rowData[0].toString() : "";

								if (!currentBoeNo.equals(previousBoeNo)) {
									// If yes, update the mergedRegionStartIndex
									if (boeNoCount > 1) {
										int mergeEndIndex = startRowIndex - 1;
										for (int i = 0; i < 3; i++) {
											regionsToMerge.add(new CellRangeAddress(mergedRegionStartIndex, mergeEndIndex, i, i));
										}
									}

									// Reset the count for the number of rows with the same BoeNo
									boeNoCount = 1;
									mergedRegionStartIndex = startRowIndex;
								}
								// Rest of the code remains unchanged...

//						    	 HSSFRow dataRow = formASheet.createRow(startRowIndex);
//						            formASheet.addMergedRegion(new CellRangeAddress(startRowIndex, startRowIndex, 0, 20));

								// Concatenate elements of cellData[0] and cellData[2]
								String concatenatedValue = (rowData[0] != null ? rowData[0].toString() : "") + " Date: "
										+ (rowData[1] != null ? rowData[1].toString() : "");
								String srcOfImp = (rowData[2] != null ? rowData[2].toString() : "NA");

								String concatenatedBondingNo = (rowData[3] != null ? rowData[3].toString() : "") + " Date: "
										+ (rowData[4] != null ? rowData[4].toString() : "");

								String commodityDescription = (rowData[5] != null ? rowData[5].toString() : "");

								String DescAndNop = (rowData[9] != null ? rowData[9].toString() : "") + " "
										+ (rowData[7] != null ? rowData[7].toString() : "");

								 BigDecimal inbondingWeight = getBigDecimalValue(rowData, 10);
								    BigDecimal inbondingCIFValue = getBigDecimalValue(rowData, 11);
								    BigDecimal inbondingCargoDuty = getBigDecimalValue(rowData, 12);
								    BigDecimal exbondingcifValue = getBigDecimalValue(rowData, 26);
								    BigDecimal exbondingCargoDuty = getBigDecimalValue(rowData, 27);
								    BigDecimal balancecifValue = getBigDecimalValue(rowData, 29);
								    BigDecimal balanceCargoDuty = getBigDecimalValue(rowData, 27); // Reuse index 27
								    BigDecimal balancePackages = getBigDecimalValue(rowData, 28); // Reuse index 27
								    BigDecimal exbondingPackages = getBigDecimalValue(rowData, 25); // Reuse index 27
								    
								    BigDecimal inbodedPackages = getBigDecimalValue(rowData, 16);
								    BigDecimal damage = getBigDecimalValue(rowData, 17); // Reuse index 27
								    BigDecimal shortage = getBigDecimalValue(rowData, 18); // Reuse index 27
								    
//									String inbodedPackages = (rowData[16] != null ? rowData[16].toString() : "");
//									String damage = (rowData[17] != null ? rowData[17].toString() : "");
//									String shortage = (rowData[18] != null ? rowData[18].toString() : "");
									
//									String balancePackages = (rowData[28] != null ? rowData[28].toString() : "");
//									String balancecifValue = (rowData[29] != null ? rowData[29].toString() : "");
//									String balanceCargoDuty = (rowData[27] != null ? rowData[27].toString() : "");
//								String inbondingWeight = (rowData[10] != null ? rowData[10].toString() : "");
//								String inbondingCIFValue = (rowData[11] != null ? rowData[11].toString() : "");
//								String inbondingCargoDuty = (rowData[12] != null ? rowData[12].toString() : "");
								String inbondingDate = (rowData[8] != null ? rowData[8].toString() : "");
								String inbondTransportNo =  "";  //(rowData[12] != null ? rowData[12].toString() : "");
								String otlNo = (rowData[15] != null ? rowData[15].toString() : "");
								String bondingDate = (rowData[4] != null ? rowData[4].toString() : "");
							
								String bondingValidty = (rowData[19] != null ? rowData[19].toString() : "");
								String ExtendedbondingValidty = (rowData[20] != null ? rowData[20].toString() : "");
								String section64 = (rowData[21] != null ? rowData[21].toString() : "");

								String exbondingDate = (rowData[24] != null ? rowData[24].toString() : "");
//								String exbondingPackages = (rowData[25] != null ? rowData[25].toString() : "");

//								String exbondingcifValue = (rowData[26] != null ? rowData[26].toString() : "");
//								String exbondingCargoDuty = (rowData[27] != null ? rowData[27].toString() : "");
								String sbNoAndSbdate = (rowData[23] != null ? rowData[23].toString() : "") + " Date: "
										+ (rowData[24] != null ? rowData[24].toString() : "");

							
								System.out.println("rowData[31] 0"+rowData[31]);
								String exBondType = (rowData[32] != null ? rowData[32].toString() : "");

								boeNoCount++;

								for (int i = 0; i < 35; i++) {
									HSSFCell cell = dataRow.createCell(i);
									switch (i) {
									case 0:
										cell.setCellValue(concatenatedValue);
										break;
									case 1:
										cell.setCellValue(srcOfImp);
										break;
									case 2:
										cell.setCellValue(concatenatedBondingNo);
										break;
									case 3:
										cell.setCellValue(commodityDescription);
										break;
									case 4:
										cell.setCellValue(DescAndNop);
										break;
									case 5:
										cell.setCellValue("");
										break;
									case 6:
										cell.setCellValue(inbondingWeight.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 7:
										cell.setCellValue(inbondingCIFValue.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 8:
										cell.setCellValue(inbondingCargoDuty.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 9:
										cell.setCellValue(inbondingDate);
										break;
									case 10:
										cell.setCellValue("");
										break;
									case 11:
										cell.setCellValue(inbondTransportNo);
										break;
									case 12:
										cell.setCellValue(otlNo);
										break;
									case 13:
										cell.setCellValue(bondingDate);
										break;
									case 14:
										cell.setCellValue(inbodedPackages.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 15:
										cell.setCellValue(damage.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 16:
										cell.setCellValue(shortage.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 17:
										cell.setCellValue(bondingValidty);
										break;
									case 18:
										cell.setCellValue(ExtendedbondingValidty);
										break;
									case 19:
										cell.setCellValue(section64);
										break;
									case 20:
										cell.setCellValue("");
										break;
									case 21:
										cell.setCellValue("EXB".equals(exBondType) ? exbondingDate:"NA");
										break;

									case 22:
									    if ("EXB".equals(exBondType)) {
									        cell.setCellValue(exbondingPackages.doubleValue());
									    } else {
									        cell.setCellValue("NA");
									    }
									    cell.setCellStyle(numberCellStyle);
									    break;

									case 23:
									    if ("EXB".equals(exBondType)) {
									        cell.setCellValue(exbondingcifValue.doubleValue());
									    } else {
									        cell.setCellValue("NA");
									    }
									    cell.setCellStyle(numberCellStyle);
									    break;

									case 24:
									    if ("EXB".equals(exBondType)) {
									        cell.setCellValue(exbondingCargoDuty.doubleValue());
									    } else {
									        cell.setCellValue("NA");
									    }
									    cell.setCellStyle(numberCellStyle);
									    break;

									case 25:
									    cell.setCellValue(sbNoAndSbdate);  // sbNoAndSbdate is a String, so no need for conversion
									    break;

									case 26:
									    if ("BTB".equals(exBondType)) {
									        cell.setCellValue("WH");
									    } else if ("EXP".equals(exBondType)) {
									        cell.setCellValue("EX");
									    } else {
									        cell.setCellValue("NA");
									    }
									    break;

									case 27:
									    if ("BTB".equals(exBondType) || "EXP".equals(exBondType)) {
									        cell.setCellValue(exbondingDate);
									    } else {
									        cell.setCellValue("NA");
									    }
									    break;

									case 28:
									    if ("BTB".equals(exBondType) || "EXP".equals(exBondType)) {
									        cell.setCellValue(exbondingPackages.doubleValue());
									    } else {
									        cell.setCellValue("NA");
									    }
									    break;

									case 29:
									    if ("BTB".equals(exBondType) || "EXP".equals(exBondType)) {
									        cell.setCellValue(exbondingcifValue.doubleValue());
									    } else {
									        cell.setCellValue("NA");
									    }
									    break;

									case 30:
									    if ("BTB".equals(exBondType) || "EXP".equals(exBondType)) {
									        cell.setCellValue(exbondingCargoDuty.doubleValue());
									    } else {
									        cell.setCellValue("NA");
									    }
									    break;


									case 31:
										cell.setCellValue(balancePackages.doubleValue() );
										cell.setCellStyle(numberCellStyle);
										break;
									case 32:
										cell.setCellValue(balancecifValue.doubleValue() );
										cell.setCellStyle(numberCellStyle);
										break;
									case 33:
										cell.setCellValue(balanceCargoDuty.doubleValue() );
										cell.setCellStyle(numberCellStyle);
										break;
									case 34:
										cell.setCellValue("  ");
										break;
									default:
										// Handle other columns if needed
										break;
									}
									cell.setCellStyle(dataCellStyle);
									cell.getCellStyle().setWrapText(true);
								}

								previousBoeNo = currentBoeNo;
								startRowIndex++;

							}
							// Check if the last set of rows needs to be merged
							if (boeNoCount > 1) {
								int mergeEndIndex = startRowIndex - 1;
								for (int i = 0; i < 3; i++) {
									regionsToMerge.add(new CellRangeAddress(mergedRegionStartIndex, mergeEndIndex, i, i));
								}
							}

						}
						// After processing all rows, perform the merging
						for (CellRangeAddress region : regionsToMerge) {
							if (region.getFirstRow() != region.getLastRow()) {
								// Ensure that the region contains 2 or more cells before merging
								formASheet.addMergedRegion(region);
							}
						}
//						Form B		

						List<Object[]> bondingDataFormB = InbondCFRepositary.getCustomQueryResults1(companyId, branchId, endDate);

						Map<String, List<Object[]>> boeNoDataMap1 = new HashMap<>();

						List<CellRangeAddress> regionsToMerge1 = new ArrayList<>();
						
						
					
						// Populate the map
						for (Object[] rowData : bondingDataFormB) {
							String boeNo = rowData[0] != null ? rowData[0].toString() : "";
							boeNoDataMap1.computeIfAbsent(boeNo+"", k -> new ArrayList<>()).add(rowData);
						}
						HSSFSheet formBSheet = workbook.createSheet("Form B");
			
						createHeaderContentFormB(formBSheet, workbook,companyName,branchAdd,branchAddress);
						addDateRowsFormB(formBSheet, startDate, endDate);
						createTableAfterLastRow(formBSheet, workbook);
						


						int lastRowIndex2 = formBSheet.getLastRowNum();

						// Calculate the start row index for inserting new data after the existing data
						int startRowIndex2 = lastRowIndex2 + 1;
						
						for (List<Object[]> boeNoData : boeNoDataMap1.values()) {
							String previousBoeNo = null;
							int mergedRegionStartIndex = 0;
							int boeNoCount = 0;

							for (Object[] rowData : boeNoData) {
								HSSFRow dataRow = formBSheet.createRow(startRowIndex2);

								String currentBoeNo = rowData[0] != null ? rowData[0].toString() : "";

								if (!currentBoeNo.equals(previousBoeNo)) {
									// If yes, update the mergedRegionStartIndex
									if (boeNoCount > 1) {
										int mergeEndIndex = startRowIndex2 - 1;
										for (int i = 0; i < 4; i++) {
											regionsToMerge1.add(new CellRangeAddress(mergedRegionStartIndex, mergeEndIndex, i, i));
										}
									}

									// Reset the count for the number of rows with the same BoeNo
									boeNoCount = 1;
									mergedRegionStartIndex = startRowIndex2;
								}
									
								String concatenatedValue = (rowData[0] != null ? rowData[0].toString() : "") + " Date: "
										+ (rowData[1] != null ? rowData[1].toString() : "");
			
								String concatenatedBondingNo = (rowData[2] != null ? rowData[2].toString() : "") + " Date: "
										+ (rowData[3] != null ? rowData[3].toString() : "");
			
								String importerName = (rowData[4] != null ? rowData[4].toString() : "");
								String chaNmae = (rowData[5] != null ? rowData[5].toString() : "");
			
								String commodityDescription = (rowData[6] != null ? rowData[6].toString() : "");
			
//								String inbodedPackages = (rowData[7] != null ? rowData[7].toString() : "");
//			
//								String inbondingCIFValue = (rowData[8] != null ? rowData[8].toString() : "");
//								String inbondingCargoDuty = (rowData[9] != null ? rowData[9].toString() : "");
//			
								String bondingValidty = (rowData[10] != null ? rowData[10].toString() : "");
								String ExtendedbondingValidty = (rowData[11] != null ? rowData[11].toString() : "");	
									
									
								
								  BigDecimal inbondingCIFValue = getBigDecimalValue(rowData, 7);
								    BigDecimal inbondingCargoDuty = getBigDecimalValue(rowData, 8);
								    BigDecimal inbodedPackages = getBigDecimalValue(rowData, 9);
								   
								    
								boeNoCount++;

								for (int i = 0; i < 13; i++) {
									HSSFCell cell = dataRow.createCell(i);
									switch (i) {
									case 0:
										cell.setCellValue(concatenatedValue);
										break;
									case 1:
										cell.setCellValue(concatenatedBondingNo);
										break;
									case 2:
										cell.setCellValue(importerName);
										break;
									case 3:
										cell.setCellValue(chaNmae);
										break;
									case 4:
										cell.setCellValue(commodityDescription);
										break;
									case 5:
										cell.setCellValue(inbodedPackages.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 6:
										cell.setCellValue(inbondingCIFValue.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 7:
										cell.setCellValue(inbondingCIFValue.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
										
									case 8:
										cell.setCellValue(inbondingCargoDuty.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 9:
										cell.setCellValue(inbondingCargoDuty.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 10:
										cell.setCellValue(bondingValidty);
										
										break;
									case 11:
										cell.setCellValue(ExtendedbondingValidty);
										break;
									case 12:
										cell.setCellValue("");
										break;
									
									
									default:
										// Handle other columns if needed
										break;
									}
									cell.setCellStyle(dataCellStyle);
									cell.getCellStyle().setWrapText(true);
								}

								previousBoeNo = currentBoeNo;
								startRowIndex2++;
							}
							
							if (boeNoCount > 1) {
								int mergeEndIndex = startRowIndex2 - 1;
								for (int i = 0; i < 4; i++) {
									regionsToMerge1.add(new CellRangeAddress(mergedRegionStartIndex, mergeEndIndex, i, i));
								}
							}
						}
						
						for (CellRangeAddress region : regionsToMerge1) {
							if (region.getFirstRow() != region.getLastRow()) {
								// Ensure that the region contains 2 or more cells before merging
								formBSheet.addMergedRegion(region);
							}
						}


//						Form 0 - 90 Days

						 Calendar calendar = Calendar.getInstance();
					        calendar.setTime(endDate);

					        // Add 90 days to the date
					        calendar.add(Calendar.DAY_OF_YEAR, 90);

					        // Get the resulting date
					        Date dateAfter90Days = calendar.getTime();
						
						List<Object[]> bondingDataForm0To90DaysSheet = InbondCFRepositary.getCustomQueryResults2(companyId,
								branchId, endDate, dateAfter90Days);
						
						Map<String, List<Object[]>> boeNoDataMap2 = new HashMap<>();
						
						List<CellRangeAddress> regionsToMerge2 = new ArrayList<>();

						HSSFSheet form0To90DaysSheet = workbook.createSheet("0-90 Days");
						createHeaderContent0To90DaysSheet(form0To90DaysSheet, workbook,companyName,branchAdd,branchAddress);
						addDateRowsFormB(form0To90DaysSheet, startDate, endDate);
						createTableAfterLastRow(form0To90DaysSheet, workbook);
						
						for (Object[] rowData : bondingDataForm0To90DaysSheet) {
							String boeNo = rowData[0] != null ? rowData[0].toString() : "";
							boeNoDataMap2.computeIfAbsent(boeNo+"", k -> new ArrayList<>()).add(rowData);
						}
					

						int lastRowIndex3 = form0To90DaysSheet.getLastRowNum();

						// Calculate the start row index for inserting new data after the existing data
						int startRowIndex3 = lastRowIndex3 + 1;
						
						
						for (List<Object[]> boeNoData : boeNoDataMap2.values()) {
							String previousBoeNo = null;
							int mergedRegionStartIndex = 0;
							int boeNoCount = 0;

							for (Object[] rowData : boeNoData) {
								HSSFRow dataRow = form0To90DaysSheet.createRow(startRowIndex3);

								String currentBoeNo = rowData[0] != null ? rowData[0].toString() : "";

								if (!currentBoeNo.equals(previousBoeNo)) {
									// If yes, update the mergedRegionStartIndex
									if (boeNoCount > 1) {
										int mergeEndIndex = startRowIndex3 - 1;
										for (int i = 0; i < 4; i++) {
											regionsToMerge2.add(new CellRangeAddress(mergedRegionStartIndex, mergeEndIndex, i, i));
										}
									}

									// Reset the count for the number of rows with the same BoeNo
									boeNoCount = 1;
									mergedRegionStartIndex = startRowIndex3;
								}
									
								String concatenatedValue = (rowData[0] != null ? rowData[0].toString() : "") + " Date: "
										+ (rowData[1] != null ? rowData[1].toString() : "");
			
								String concatenatedBondingNo = (rowData[2] != null ? rowData[2].toString() : "") + " Date: "
										+ (rowData[3] != null ? rowData[3].toString() : "");
			
								String importerName = (rowData[4] != null ? rowData[4].toString() : "");
								String chaNmae = (rowData[5] != null ? rowData[5].toString() : "");
			
								String commodityDescription = (rowData[6] != null ? rowData[6].toString() : "");
			
//								String inbodedPackages = (rowData[7] != null ? rowData[7].toString() : "");
//			
//								String inbondingCIFValue = (rowData[8] != null ? rowData[8].toString() : "");
//								String inbondingCargoDuty = (rowData[9] != null ? rowData[9].toString() : "");
//			
								String bondingValidty = (rowData[10] != null ? rowData[10].toString() : "");
								String ExtendedbondingValidty = (rowData[11] != null ? rowData[11].toString() : "");
								
								
								
								
								  BigDecimal inbondingCIFValue = getBigDecimalValue(rowData, 7);
								    BigDecimal inbondingCargoDuty = getBigDecimalValue(rowData, 8);
								    BigDecimal inbodedPackages = getBigDecimalValue(rowData, 9);
									
									
								
								
								boeNoCount++;

								for (int i = 0; i < 13; i++) {
									HSSFCell cell = dataRow.createCell(i);
									switch (i) {
									case 0:
										cell.setCellValue(concatenatedValue);
										break;
									case 1:
										cell.setCellValue(concatenatedBondingNo);
										break;
									case 2:
										cell.setCellValue(importerName);
										break;
									case 3:
										cell.setCellValue(chaNmae);
										break;
									case 4:
										cell.setCellValue(commodityDescription);
										break;
									case 5:
										cell.setCellValue(inbodedPackages.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 6:
										cell.setCellValue(inbondingCIFValue.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 7:
										cell.setCellValue(inbondingCIFValue.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 8:
										cell.setCellValue(inbondingCargoDuty.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 9:
										cell.setCellValue(inbondingCargoDuty.doubleValue());
										cell.setCellStyle(numberCellStyle);
										break;
									case 10:
										cell.setCellValue(bondingValidty);
										break;
									case 11:
										cell.setCellValue(ExtendedbondingValidty);
										break;
									case 12:
										cell.setCellValue("");
										break;
									
									
									default:
										// Handle other columns if needed
										break;
									}
									cell.setCellStyle(dataCellStyle);
									cell.getCellStyle().setWrapText(true);
								}

								previousBoeNo = currentBoeNo;
								startRowIndex3++;
							}
							
							if (boeNoCount > 1) {
								int mergeEndIndex = startRowIndex3 - 1;
								for (int i = 0; i < 4; i++) {
									regionsToMerge2.add(new CellRangeAddress(mergedRegionStartIndex, mergeEndIndex, i, i));
								}
							}
						}
						
						for (CellRangeAddress region : regionsToMerge2) {
							if (region.getFirstRow() != region.getLastRow()) {
								// Ensure that the region contains 2 or more cells before merging
								form0To90DaysSheet.addMergedRegion(region);
							}
						}


						
						

						// Convert the workbook to bytes
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						workbook.write(bos);
						byte[] fileContent = bos.toByteArray();

						// Convert byte array to Base64 string
						String base64File = Base64.getEncoder().encodeToString(fileContent);

						// Construct a "base URL" using a data URL scheme
						String baseUrl = "data:application/vnd.ms-excel;base64,"; // Adjust the content type as needed
						String fileUrl = baseUrl + base64File;

						return ResponseEntity.ok().body(fileUrl);
					} catch (IOException e) {
						e.printStackTrace();
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate Excel file URL");
					}
				}
				
}
