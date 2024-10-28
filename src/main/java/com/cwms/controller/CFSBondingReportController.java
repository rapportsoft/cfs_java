package com.cwms.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.service.CFSBondingReportService;
import com.cwms.service.LiveBondService;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/bondingReport")
@CrossOrigin("*")
public class CFSBondingReportController {

	@Autowired
	public CFSBondingReportService cFSBondingReportService;
	
	@Autowired
	public LiveBondService InBondService;
	
	
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
					       @RequestParam(name = "exBondingId") String exBondingId,
					       @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
					       @RequestParam(name = "endDate",required = false ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfLiveBondReport(companyId,branchId,username,type,companyname,branchname,exBondingId,startDate,endDate);
					
					String fileName = "Live Bond Report" + exBondingId + ".xlsx";
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
					       @RequestParam(name = "exBondingId") String exBondingId) throws DocumentException {
				
					byte[] excelBytes = cFSBondingReportService.createExcelReportOfAuditTrailReport(companyId,branchId,username,type,companyname,branchname,exBondingId);
					
					String fileName = "AuditTrail Report" + exBondingId + ".xlsx";
					HttpHeaders headers = new HttpHeaders();
					
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.setContentDispositionFormData("attachment", fileName);

					return ResponseEntity.ok().headers(headers).body(excelBytes);
				}
				
				
				
				@GetMapping("/downLoadmontlyReport")
				public ResponseEntity<?> downLoadMonthlyreport(@RequestParam(name = "companyid", required = false) String companyid,
						@RequestParam(name = "branchId", required = false) String branchId,
						@RequestParam(name = "warehouse", required = false) String warehouse,
						@RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
						@RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
					try {
								
						  // Create a new workbook
				        Workbook workbook = new XSSFWorkbook();

				        // Create INBOND sheet and fill data
				        Sheet inbondSheet = workbook.createSheet("INBOND");
				        Sheet fillInbondSheetData = InBondService.fillInbondSheetData(inbondSheet, startDate, endDate,companyid,branchId,warehouse );

				        // Create EXBOND sheet and fill data
				        Sheet exbondSheet = workbook.createSheet("EXBOND");
				        Sheet fillExbondSheetData = InBondService.fillExbondSheetData(exbondSheet, startDate, endDate,companyid,branchId,warehouse);

				        // Create LIVEBOND sheet and fill data
				        Sheet livebondSheet = workbook.createSheet("LIVEBOND");
				        Sheet fillLivebondSheetData = InBondService.fillLivebondSheetData(livebondSheet, startDate, endDate,companyid,branchId,warehouse);

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
}
