package com.cwms.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.NotGateInHoldDtl;
import com.cwms.repository.NotGateInHoldDtlRepo;

@RestController
@CrossOrigin("*")
@RequestMapping("/notGateInHoldContainer")
public class NotGateInHoldDtlController {

	@Autowired
	private NotGateInHoldDtlRepo notgateinholdrepo;
	
	@Value("${file.holdContainerTempPath}")
	private String blTemplateDownloadPath;

	@Value("${file.holdContainerUploadPath}")
	private String blTariffUploadPath;
	
	@GetMapping("/excelFormatDownload")
	public ResponseEntity<?> downloadExcelFile() {
		try {
			// Path to your Excel file (you need to define 'filePath' correctly)
			File file = new File(blTemplateDownloadPath);

			// Check if the file exists
			if (!file.exists()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			// Serve the file
			Path path = Paths.get(file.getAbsolutePath());
			Resource resource = new UrlResource(path.toUri());

			// Return the file with the correct content type for Excel
			return ResponseEntity.ok()
					.contentType(MediaType
							.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
					.body(resource);

		} catch (MalformedURLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/advanceHoldContainerUpload")
	public ResponseEntity<?> handleFileUploadParty(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("file") MultipartFile file)
			throws ParseException, IOException {
		
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("File is empty.");
		}
		String fileName = file.getOriginalFilename();
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String newFileName = fileName + "_" + timestamp + ".xlsx";

		// Define the path where the new file will be saved
		Path newFilePath = Paths.get(blTariffUploadPath, newFileName);

		// Use try-with-resources to handle streams and ensure proper encoding
		try (InputStream inputStream = file.getInputStream();
				OutputStream outputStream = Files.newOutputStream(newFilePath, StandardOpenOption.CREATE)) {

			// Transfer file content while preserving special characters
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
		}

		// Now, create a new File object for the newly created file
		File newFile = newFilePath.toFile();

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			// Extract headers dynamically
			Row headerRow = sheet.getRow(0);
			Map<Integer, String> headerMap = new HashMap<>();
			List<String> list1 = new ArrayList<>();
			Map<String, Object> result = new HashMap<>();

			List<String> headerVal = new ArrayList<>();
			
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                String containerNo = getCellValue(row.getCell(0), evaluator);
                String agency = getCellValue(row.getCell(1), evaluator);
                String remarks = getCellValue(row.getCell(2), evaluator);
                String holdingAgent = getCellValue(row.getCell(3), evaluator);
                String igmNo = getCellValue(row.getCell(4), evaluator);
                
                if (notgateinholdrepo.checkContainerNoAlreadyExistOrNot(cid, bid, containerNo)) {
                	list1.add(containerNo + " ~ already exists.");
                }
                else {
                	
                	int srNo = notgateinholdrepo.getSrNoByContainerWise(cid, bid, containerNo);
                	
                	NotGateInHoldDtl dtl = new NotGateInHoldDtl();
                	dtl.setCompanyId(cid);
                	dtl.setBranchId(bid);
                	dtl.setSrNo(srNo + 1);
                	dtl.setDocRefNo((igmNo == null || igmNo.isEmpty()) ? "" : igmNo);
                	dtl.setContainerNo(containerNo);
                	dtl.setHoldStatus("H");
                	dtl.setCsd(agency);
                	dtl.setCsdHoldUserName(holdingAgent);
                	dtl.setCsdHoldDate(new Date());
                	dtl.setCsdHoldRemarks(remarks);
                	dtl.setStatus("A");
                	dtl.setCreatedBy(user);
                	dtl.setCreatedDate(new Date());
                	dtl.setApprovedBy(user);
                	dtl.setApprovedDate(new Date());
                	dtl.setFileUploadPath(blTariffUploadPath+newFileName);
                	dtl.setMergeFlag("N");
                	
                	notgateinholdrepo.save(dtl);
                	
                }

            }

			if (!list1.isEmpty()) {
				result.put("message", "error");
				result.put("result", list1);
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
			else {
				result.put("message", "success");
				result.put("result", "File uploaded and processed successfully");

				return new ResponseEntity<>(result, HttpStatus.OK);
			}
			
			
			
			

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the file");
		}
	}
	
	// Method to handle different cell types
	public static String getCellValue(Cell cell, FormulaEvaluator evaluator) {
		DataFormatter dataFormatter = new DataFormatter();

		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue().toString(); // Handle date formatting if needed
			} else {
				return dataFormatter.formatCellValue(cell); // Ensures numeric values don't have ".0"
			}
		case BOOLEAN:
			return Boolean.toString(cell.getBooleanCellValue());
		case FORMULA:
			return dataFormatter.formatCellValue(cell, evaluator); // Evaluate formulas correctly
		case BLANK:
			return "";
		default:
			return "";
		}
	}

	private String getCellValue(Row row, Integer cellIndex, FormulaEvaluator evaluator) {
		if (cellIndex != null) {
			Cell cell = row.getCell(cellIndex);
			if (cell != null) {
				try {
					switch (cell.getCellType()) {
					case STRING:
						return cell.getStringCellValue();
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							return cell.getDateCellValue().toString(); // Handle date cells separately
						} else {
							// Check if it's a whole number
							double numericValue = cell.getNumericCellValue();
							if (numericValue == Math.floor(numericValue)) {
								return String.valueOf((long) numericValue); // Cast to long to remove the decimal point
							} else {
								BigDecimal bigDecimal = BigDecimal.valueOf(numericValue);
								return bigDecimal.toPlainString(); // Convert decimal number to plain string
							}
						}
					case BOOLEAN:
						return String.valueOf(cell.getBooleanCellValue());
					case FORMULA:
						CellValue cellValue = evaluator.evaluate(cell);
						switch (cellValue.getCellType()) {
						case STRING:
							return cellValue.getStringValue();
						case NUMERIC:
							double formulaValue = cellValue.getNumberValue();
							if (formulaValue == Math.floor(formulaValue)) {
								return String.valueOf((long) formulaValue); // Remove the decimal for whole numbers
							} else {
								BigDecimal bigDecimal = BigDecimal.valueOf(formulaValue);
								return bigDecimal.toPlainString(); // Convert decimal number to plain string
							}
						case BOOLEAN:
							return String.valueOf(cellValue.getBooleanValue());
						default:
							return null;
						}
					default:
						return null;
					}
				} catch (Exception e) {
					// Handle unresolved external workbook reference
					System.out.println("External workbook not found for cell: " + cell.getAddress());
					return null;
				}
			}
		}
		return null;
	}
	
	@GetMapping("/getContainerData")
	public ResponseEntity<?> getContainerData(@RequestParam("cid") String cid,@RequestParam("bid") String bid){
		try {
			
			List<Object[]> data = notgateinholdrepo.getAdvanceHoldData(cid, bid);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			else {
				return new ResponseEntity<>(data,HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>("Internal Server Error",HttpStatus.CONFLICT);
		}
	}

}
