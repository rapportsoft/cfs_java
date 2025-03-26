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

import com.cwms.entities.CFIgm;
import com.cwms.entities.CFSBLDetails;
import com.cwms.entities.CFSTariffService;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.CfsTarrif;
import com.cwms.entities.SSRDtl;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.CFSBLDetailsrepo;
import com.cwms.repository.CFSRepositary;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.SSRDtlRepository;
import com.cwms.repository.SerViceRepositary;
import com.cwms.service.ProcessNextIdService;
import com.cwms.service.Tarrifservice;

@RestController
@RequestMapping("/ssrUpload")
@CrossOrigin("*")
public class SSRUploadController {

	@Value("${file.ssrUploadFormat}")
	private String blTemplateDownloadPath;

	@Value("${file.ssrUploadPath}")
	private String blTariffUploadPath;

	@Autowired
	private SerViceRepositary serviceRepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private CFSRepositary cfsstdtrfrepo;

	@Autowired
	private CFSTarrifServiceRepository cfsstdtrfsrvrepo;

	@Autowired
	private CFSBLDetailsrepo cfsBlDetailsrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private HelperMethods helperMethods;

	@Autowired
	private ProcessNextIdService processService;

	@Autowired
	private Tarrifservice tariffService;
	
	@Autowired
	private CfIgmRepository cfigmrepo;
	
	@Autowired
	private SSRDtlRepository ssrdtlrepo;

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

	@PostMapping("/ssrExcelUpload")
	public ResponseEntity<?> handleFileUploadParty(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("id") String id, @RequestParam("file") MultipartFile file)
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

			List<String> serviceList = new ArrayList<>();
			List<String> serviceNameList = new ArrayList<>();

			for (Cell cell : headerRow) {
				headerMap.put(cell.getColumnIndex(), cell.getStringCellValue());

				headerVal.add(cell.getStringCellValue());

				if (cell.getColumnIndex() >= 6) {
					String checkServiceIsExistOrNot = serviceRepo.isExistServiceId(cid, bid, cell.getStringCellValue());
					if (checkServiceIsExistOrNot == null || checkServiceIsExistOrNot.isEmpty()) {
						list1.add(cell.getStringCellValue() + " ~ " + cell.getStringCellValue()
								+ " are not matching with the Services short desc.");
					} else {
						serviceList.add(checkServiceIsExistOrNot);
						serviceNameList.add(checkServiceIsExistOrNot + "~" + cell.getStringCellValue());
					}
				}
			}

			if (!list1.isEmpty()) {
				result.put("message", "error");
				result.put("result", list1);
				return new ResponseEntity<>(result, HttpStatus.OK);
			}

			List<String> errorList = new ArrayList<>();

			List<List<String>> allData = new ArrayList<>();


			List<Map<String, String>> serviceDataList = new ArrayList<>();

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Map<String, String> rowData = new HashMap<>();
					List<String> data1 = new ArrayList<>();
					Map<String, String> serviceData = new HashMap<>(); // Store serviceId mapped data

					int serviceIndex = 0; // To track serviceList index
					int valIndex = 0;

					int totalColumns = headerMap.size(); // Total number of columns based on headers
					for (int colIndex = 0; colIndex < totalColumns; colIndex++) {
						Cell cell = row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // Get cell, even
																										// if empty

						String columnName = headerMap.get(colIndex); // Get header name
						String cellValue = (valIndex == colIndex) ? String.valueOf(getCellValue(cell, evaluator)) : ""; // Extract
																														// value

						rowData.put(columnName, cellValue);
						data1.add(cellValue);

						// If column index >= 7 (service columns), get serviceId from serviceList
						if (colIndex >= 6) {
							if (serviceIndex < serviceList.size()) {
								String serviceId = serviceList.get(serviceIndex); // Get serviceId from serviceList
								serviceData.put(serviceId, cellValue); // Map serviceId to cell value
								serviceIndex++; // Move to the next serviceId
							}
						}

						valIndex++;
					}

					allData.add(data1);
					// Add mapped service data to list
					if (!serviceData.isEmpty()) {
						serviceDataList.add(serviceData);
					}
				}
			}

			AtomicInteger index = new AtomicInteger(1);

			allData.stream().forEach(i -> {
				if (i.get(0) == null || i.get(0).isEmpty()) {
					errorList.add("IGM No ~ IGM no is required for index " + index.get());
				}

				if (i.get(1) == null || i.get(1).isEmpty()) {
					errorList.add("Item no ~ Item no is required for index " + index.get());
				}
				
				if (i.get(2) == null || i.get(2).isEmpty()) {
					errorList.add("Container no ~ Container no is required for index " + index.get());
				}

				if ((i.get(0) != null && !i.get(0).isEmpty()) && (i.get(1) != null && !i.get(1).isEmpty()) && (i.get(2) != null && !i.get(2).isEmpty())) {
					boolean checkItem = cfigmcrgrepo.isExistLineRecord(cid, bid, i.get(1), i.get(0));

					if (!checkItem) {
						errorList.add("IGM No ,Item no & Cotainer No ~ Item no ,Item No & Container No are not found for index " + index.get());
					}
				}

//				if (i.get(2) != null && !i.get(2).isEmpty()) {
//					boolean checkCon = cfigmcnrepo.checkContainer(cid, bid, i.get(0), i.get(1), i.get(2));
//
//					if (!checkCon) {
//						errorList.add("Container No ~ Container no is not found for index " + index.get());
//					}
//				}

				index.incrementAndGet();
			});

			if (!errorList.isEmpty()) {
				result.put("message", "error");
				result.put("result", errorList);

				return new ResponseEntity<>(result, HttpStatus.OK);
			}

			AtomicInteger in = new AtomicInteger(0);
			AtomicReference<String> cfsTariffNo = new AtomicReference<>("");
			AtomicReference<String> cfsAmndNo = new AtomicReference<>("");

			allData.stream().forEach(i -> {

				Map<String, String> existingData = serviceDataList.get(in.get());

				List<Cfigmcn> conData = cfigmcnrepo.conList(cid, bid, i.get(0), i.get(1), i.get(2));

				List<String> distinctContainerSizes = conData.stream()
					    .map(c -> "22".equals(c.getContainerSize()) ? "20" : c.getContainerSize()) // Map "22" to "20"
					    .distinct() // Get unique values
					    .collect(Collectors.toList());

				List<String> distinctToc = conData.stream().map(Cfigmcn::getTypeOfContainer) // Extract containerSize
						.distinct() // Get unique values
						.collect(Collectors.toList()); // Collect as List

				List<String> conSize = new ArrayList<>();
				conSize.add("ALL");
				conSize.addAll(distinctContainerSizes);

				List<String> conType = new ArrayList<>();
				conType.add("ALL");
				conType.addAll(distinctToc);

				List<Object[]> serviceDtls = cfsstdtrfsrvrepo.getServiceDtlForBLWiseUpload(cid, bid, serviceList,
						"CFS1000001", conSize, conType);

				if (!serviceDtls.isEmpty()) {

		            if(conData != null && !conData.isEmpty()) {
		            	
		            	Cfigmcn con = conData.get(0);
		            	
		            	String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05080", "2024");

		    			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		    			int nextNumericNextID1 = lastNextNumericId1 + 1;

		    			String HoldNextIdD1 = String.format("ISSR%06d", nextNumericNextID1);
		            	
		            	CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, conData.get(0).getIgmTransId(), conData.get(0).getIgmNo());

						Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, conData.get(0).getIgmTransId(), conData.get(0).getIgmNo(),
								conData.get(0).getIgmLineNo());
						
						
						serviceDtls.stream().forEach(s->{
							
							BigDecimal rate = (existingData.get(s[0]) == null || existingData.get(s[0]).isEmpty())
									? BigDecimal.ZERO
									: new BigDecimal(existingData.get(s[0]));
							
							String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05081", "2024");

							String[] parts = lastValue.split("/");
							String baseId = parts[0];
							String baseId1 = parts[1];
							String financialYear = parts[2];

							// Increment the numerical part
							int newVal = Integer.parseInt(baseId1) + 1;

							// Format newVal to maintain leading zeros (e.g., 0001)
							String formattedNewVal = String.format("%04d", newVal);

							// Get the current financial year
							String currentFinancialYear = getCurrentFinancialYear();

							// Construct the new ID
							String newId = baseId + "/" + formattedNewVal + "/" + currentFinancialYear;
							
							SSRDtl newSsr = new SSRDtl();

							newSsr.setApprovedBy(user);
							newSsr.setApprovedDate(new Date());
							newSsr.setBeDate(crg.getBeDate());
							newSsr.setBeNo(crg.getBeNo());
							newSsr.setBlDate(crg.getBlDate());
							newSsr.setBlNo(crg.getBlNo());
							newSsr.setBranchId(bid);
							newSsr.setCargoWt(con.getCargoWt());
							newSsr.setCha(con.getCha());
							newSsr.setCommodityDescription(crg.getCommodityDescription());
							newSsr.setCompanyId(cid);
							newSsr.setContainerNo(con.getContainerNo());
							newSsr.setContainerSize(con.getContainerSize());
							newSsr.setContainerType(con.getContainerType());
							newSsr.setCreatedBy(user);
							newSsr.setCreatedDate(new Date());
							newSsr.setDocRefDate(igm.getIgmDate());
							newSsr.setDocRefNo(con.getIgmNo());
							newSsr.setErpDocRefNo(con.getIgmTransId());
							newSsr.setExecutionUnit("1");
							newSsr.setGateOutType(con.getGateOutType());
							newSsr.setIgmLineNo(con.getIgmLineNo());
							newSsr.setNoOfPackages(con.getNoOfPackages());
							newSsr.setProfitcentreId(con.getProfitcentreId());
							newSsr.setRate(new BigDecimal(String.valueOf(s[3])));
							newSsr.setSa(igm.getShippingAgent());
							newSsr.setServiceId(String.valueOf(s[0]));
							newSsr.setServiceUnit(String.valueOf(s[1]));
							newSsr.setServiceUnit1(String.valueOf(s[19]));
							newSsr.setSl(igm.getShippingLine());
							newSsr.setSrNo(new BigDecimal(0));
							newSsr.setSsrModeFor("CONT");
							newSsr.setSsrRefNo(newId);
							newSsr.setStatus('A');
							newSsr.setTotalRate(rate);
							newSsr.setTransDate(new Date());
							newSsr.setTransId(HoldNextIdD1);
							newSsr.setTransLineNo(new BigDecimal(1));
							newSsr.setAccId(crg.getAccountHolderId());
							newSsr.setImpId(crg.getImporterId());
							newSsr.setSsrPartyId(id);

							ssrdtlrepo.save(newSsr);
							
							
							processnextidrepo.updateAuditTrail(cid, bid, "P05080", HoldNextIdD1, "2024");
							processnextidrepo.updateAuditTrail(cid, bid, "P05081", newId, "2024");
						});
						
						
						int update = cfigmcnrepo.updateSSRID(cid, bid, conData.get(0).getIgmTransId(), conData.get(0).getIgmNo(), 
								conData.get(0).getContainerNo(), HoldNextIdD1);
						
		            }

				}

				

				in.incrementAndGet();
			});

			// Create timestamp for the new file name

			

			result.put("message", "success");
			result.put("result", "File uploaded and processed successfully");

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the file");
		}
	}

	private CFSTariffService createCFSTariffService(String cid, String bid, String autoCFSTariffNo, Object[] c,
			String user, BigDecimal fromRange, BigDecimal toRange, BigDecimal rate, int index, String amdNo) {

		CFSTariffService service = new CFSTariffService();
		service.setCompanyId(cid);
		service.setBranchId(bid);
		service.setFinYear(helperMethods.getFinancialYear());
		service.setCfsTariffNo(autoCFSTariffNo);
		service.setRangeType("NA");
		service.setProfitCentreId("N00002");
		service.setCfsAmendNo(amdNo);
		service.setServiceId(String.valueOf(c[0]));
		service.setServiceUnit(String.valueOf(c[1]));
		service.setServiceUnitI(String.valueOf(c[19]));
		service.setContainerSize(String.valueOf(c[13]));
		service.setCargoType(String.valueOf(c[12]));
		service.setCommodityCode(String.valueOf(c[11]));
		service.setSrNo(new BigDecimal(index));
		service.setFromRange(fromRange);
		service.setToRange(toRange);
		service.setRate(rate);
		service.setMinimumRate(
				(c[20] == null || c[20].equals("")) ? BigDecimal.ZERO : new BigDecimal(String.valueOf(c[20])));
		service.setCreatedBy(user);
		service.setCreatedDate(new Date());
		service.setApprovedBy(user);
		service.setApprovedDate(new Date());
		service.setStatus("A");
		service.setCurrencyId(String.valueOf(c[4]));
		service.setDefaultChk("N");

		return service;
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

	@GetMapping("/search")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "transId", required = false) String transId) {

		List<Object[]> data = cfsBlDetailsrepo.getData(cid, bid, transId);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/getDataByTransId")
	public ResponseEntity<?> getDataByTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("transId") String transId) {

		List<Object[]> data = cfsBlDetailsrepo.getDataByTransId(cid, bid, transId);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}
	
	private static String getCurrentFinancialYear() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		// Check if the current date is before or after April 1st
		if (month < 4 || (month == 4 && day < 1)) {
			year--; // If before April 1st, consider the previous year
		}

		// Calculate financial year start and end
		int financialYearStart = year % 100; // Current year for start
		int financialYearEnd = (year + 1) % 100; // Next year for end

		// Format as YY-YY
		return String.format("%02d-%02d", financialYearStart, financialYearEnd);
	}
	
	
	@GetMapping("/getSSRData")
	public ResponseEntity<?> getSSRData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("id") String id){
		
		try {
			
			List<Object[]> data = ssrdtlrepo.getDataBySSRTransId(cid, bid, id);
			
			if(data.isEmpty()) {
				return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
			}
			
			return new ResponseEntity<>(data,HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
		}
	}
}

