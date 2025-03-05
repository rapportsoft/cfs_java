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
import org.apache.poi.ss.usermodel.CellType;
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

import com.cwms.entities.CFSBLDetails;
import com.cwms.entities.CFSTariffService;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.CfsTarrif;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.CFSBLDetailsrepo;
import com.cwms.repository.CFSRepositary;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.SerViceRepositary;
import com.cwms.service.ProcessNextIdService;
import com.cwms.service.Tarrifservice;

@RestController
@RequestMapping("/blTariffUpload")
@CrossOrigin("*")

public class BLTariffUploadController {

	@Value("${file.blTariffFormat}")
	private String blTemplateDownloadPath;

	@Value("${file.blTariffUpload}")
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

	@PostMapping("/blTariffUpload")
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

				if (cell.getColumnIndex() >= 7) {
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

//			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//				Row row = sheet.getRow(i);
//				if (row != null) {
//					Map<String, String> rowData = new HashMap<>();
//
//					List<String> data1 = new ArrayList<>();
//
//					for (Cell cell : row) {
//
//						String columnName = headerMap.get(cell.getColumnIndex()); // Get the corresponding header
//						String cellValue = String.valueOf(getCellValue(cell, evaluator)); // Extract the correct value
//						rowData.put(columnName, cellValue);
//						data1.add(cellValue);
//					}
//
//					allData.add(data1);
//				}
//			}

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
						if (colIndex >= 7) {
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

				if ((i.get(0) != null && !i.get(0).isEmpty()) && (i.get(1) != null && !i.get(1).isEmpty())) {
					boolean checkItem = cfigmcrgrepo.isExistLineRecord(cid, bid, i.get(1), i.get(0));

					if (!checkItem) {
						errorList.add("IGM No & Item no ~ Item no & Item No are not found for index " + index.get());
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

				List<String> distinctContainerSizes = conData.stream().map(Cfigmcn::getContainerSize) // Extract
																										// containerSize
						.distinct() // Get unique values
						.collect(Collectors.toList()); // Collect as List

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

					if (conData.get(0).getUpTariffFwd() == null || conData.get(0).getUpTariffFwd().isEmpty()) {

						CfsTarrif tariff = new CfsTarrif();

						String autoCFSTariffNo = processService.autoCFSTariffNo(cid, bid, "P00409");

						cfsTariffNo.set(autoCFSTariffNo);
						cfsAmndNo.set("000");

						tariff.setCompanyId(cid);
						tariff.setBranchId(bid);
						tariff.setFinYear(helperMethods.getFinancialYear());
						tariff.setProfitCentreId("N00002");
						tariff.setCfsTariffNo(autoCFSTariffNo);
						tariff.setCfsAmndNo("000");
						tariff.setPartyId(id);
						tariff.setCfsTariffDate(new Date());
						LocalDate futureDate = LocalDate.now().plusYears(2);
						Date newDate = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
						tariff.setCfsValidateDate(newDate);
						tariff.setCreatedBy(user);
						tariff.setCreatedDate(new Date());
						tariff.setApprovedBy(user);
						tariff.setApprovedDate(new Date());
						tariff.setStatus("A");
						tariff.setAmmendStatus("");
						tariff.setNvoccTariff("N");
						tariff.setOffdocTariff("N");
						tariff.setTariffType("blwise");

						cfsstdtrfrepo.save(tariff);

						serviceDtls.stream().forEach(c -> {

							String type = String.valueOf(c[6]);

							BigDecimal rate = (existingData.get(c[0]) == null || existingData.get(c[0]).isEmpty())
									? BigDecimal.ZERO
									: new BigDecimal(existingData.get(c[0]));

							if ("NA".equals(type) && rate.compareTo(BigDecimal.ZERO) > 0) {
								CFSTariffService service = new CFSTariffService();

								service.setCompanyId(cid);
								service.setBranchId(bid);
								service.setFinYear(helperMethods.getFinancialYear());
								service.setCfsTariffNo(autoCFSTariffNo);
								service.setRangeType("NA");
								service.setProfitCentreId("N00002");
								service.setCfsAmendNo("000");
								service.setServiceId(String.valueOf(c[0]));
								service.setServiceUnit(String.valueOf(c[1]));
								service.setServiceUnitI(String.valueOf(c[19]));
								service.setContainerSize(String.valueOf(c[13]));
								service.setCargoType(String.valueOf(c[12]));
								service.setCommodityCode(String.valueOf(c[11]));
								service.setSrNo(new BigDecimal(1));
								service.setFromRange(BigDecimal.ZERO);
								service.setToRange(BigDecimal.ZERO);
								service.setRate(rate);
								service.setMinimumRate((c[20] == null || c[20] == "") ? BigDecimal.ZERO
										: new BigDecimal(String.valueOf(c[20])));
								service.setCreatedBy(user);
								service.setCreatedDate(new Date());
								service.setApprovedBy(user);
								service.setApprovedDate(new Date());
								service.setStatus("A");
								service.setCurrencyId(String.valueOf(c[4]));
								service.setDefaultChk("N");

								cfsstdtrfsrvrepo.save(service);
							}

							if ("RA".equals(type) && rate.compareTo(BigDecimal.ZERO) > 0) {
								List<Object[]> rangeServices = cfsstdtrfsrvrepo.getRangeServiceDtlForBLWiseUpload(cid,
										bid, String.valueOf(c[0]), "CFS1000001", conSize, conType);

								if (!rangeServices.isEmpty()) {

									AtomicInteger rangeIndex = new AtomicInteger(1);

									rangeServices.stream().forEach(r -> {
										CFSTariffService service = new CFSTariffService();

										service.setCompanyId(cid);
										service.setBranchId(bid);
										service.setFinYear(helperMethods.getFinancialYear());
										service.setCfsTariffNo(autoCFSTariffNo);
										service.setRangeType("NA");
										service.setProfitCentreId("N00002");
										service.setCfsAmendNo("000");
										service.setServiceId(String.valueOf(c[0]));
										service.setServiceUnit(String.valueOf(c[1]));
										service.setServiceUnitI(String.valueOf(c[19]));
										service.setContainerSize(String.valueOf(c[13]));
										service.setCargoType(String.valueOf(c[12]));
										service.setCommodityCode(String.valueOf(c[11]));
										service.setSrNo(new BigDecimal(rangeIndex.get()));
										service.setFromRange(new BigDecimal(String.valueOf(r[17])));
										service.setToRange(new BigDecimal(String.valueOf(r[21])));
										service.setRate(rate);
										service.setMinimumRate((c[20] == null || c[20] == "") ? BigDecimal.ZERO
												: new BigDecimal(String.valueOf(c[20])));
										service.setCreatedBy(user);
										service.setCreatedDate(new Date());
										service.setApprovedBy(user);
										service.setApprovedDate(new Date());
										service.setStatus("A");
										service.setCurrencyId(String.valueOf(c[4]));
										service.setDefaultChk("N");

										cfsstdtrfsrvrepo.save(service);

										rangeIndex.incrementAndGet();
									});
								}
							}

							if ("SA".equals(type) && rate.compareTo(BigDecimal.ZERO) > 0) {
								List<Object[]> rangeServices = cfsstdtrfsrvrepo.getRangeServiceDtlForBLWiseUpload(cid,
										bid, String.valueOf(c[0]), "CFS1000001", conSize, conType);

								if (!rangeServices.isEmpty()) {
									AtomicInteger rangeIndex = new AtomicInteger(1);

									rangeServices.stream().forEach(r -> {
										BigDecimal fromRange = new BigDecimal(String.valueOf(r[17]));
										BigDecimal toRange = new BigDecimal(String.valueOf(r[21]));
										BigDecimal currentRate = new BigDecimal(String.valueOf(r[3]));

										if (rate.compareTo(toRange) == 0 || rate.compareTo(toRange) > 0) {
											// If rate is exactly equal to 'toRange', set rate to zero
											CFSTariffService service = createCFSTariffService(cid, bid, autoCFSTariffNo,
													c, user, fromRange, toRange, BigDecimal.ZERO, rangeIndex.get(),
													"000");

											cfsstdtrfsrvrepo.save(service);
											rangeIndex.incrementAndGet();
										} else if (rate.compareTo(fromRange) >= 0 && rate.compareTo(toRange) < 0) {
											// If rate falls within the range, split into two slabs
											CFSTariffService service1 = createCFSTariffService(cid, bid,
													autoCFSTariffNo, c, user, fromRange, rate, BigDecimal.ZERO,
													rangeIndex.get(), "000");

											CFSTariffService service2 = createCFSTariffService(cid, bid,
													autoCFSTariffNo, c, user, rate.add(BigDecimal.ONE), toRange,
													currentRate, rangeIndex.incrementAndGet(), "000");

											cfsstdtrfsrvrepo.save(service1);
											cfsstdtrfsrvrepo.save(service2);
											rangeIndex.incrementAndGet();
										} else {
											// Default case, save as is
											CFSTariffService service = createCFSTariffService(cid, bid, autoCFSTariffNo,
													c, user, fromRange, toRange, currentRate, rangeIndex.get(), "000");

											cfsstdtrfsrvrepo.save(service);
											rangeIndex.incrementAndGet();
										}
									});
								}
							}

						});

					} else {
						CfsTarrif tariff = new CfsTarrif();

						String autoCFSTariffNo = conData.get(0).getUpTariffNo();

						int amd = Integer.parseInt(conData.get(0).getUpTariffAmndNo());

						amd = amd + 1;

						String amdNo = String.format("%03d", amd);

						cfsTariffNo.set(autoCFSTariffNo);
						cfsAmndNo.set(amdNo);

						tariff.setCompanyId(cid);
						tariff.setBranchId(bid);
						tariff.setFinYear(helperMethods.getFinancialYear());
						tariff.setProfitCentreId("N00002");
						tariff.setCfsTariffNo(autoCFSTariffNo);
						tariff.setCfsAmndNo(amdNo);
						tariff.setPartyId(id);
						tariff.setCfsTariffDate(new Date());
						LocalDate futureDate = LocalDate.now().plusYears(2);
						Date newDate = Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
						tariff.setCfsValidateDate(newDate);
						tariff.setCreatedBy(user);
						tariff.setCreatedDate(new Date());
						tariff.setApprovedBy(user);
						tariff.setApprovedDate(new Date());
						tariff.setStatus("A");
						tariff.setAmmendStatus("");
						tariff.setNvoccTariff("N");
						tariff.setOffdocTariff("N");
						tariff.setTariffType("blwise");

						cfsstdtrfrepo.save(tariff);

						serviceDtls.stream().forEach(c -> {

							String type = String.valueOf(c[6]);

							BigDecimal rate = (existingData.get(c[0]) == null || existingData.get(c[0]).isEmpty())
									? BigDecimal.ZERO
									: new BigDecimal(existingData.get(c[0]));

							if ("NA".equals(type) && rate.compareTo(BigDecimal.ZERO) > 0) {
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
								service.setSrNo(new BigDecimal(1));
								service.setFromRange(BigDecimal.ZERO);
								service.setToRange(BigDecimal.ZERO);
								service.setRate(rate);
								service.setMinimumRate((c[20] == null || c[20] == "") ? BigDecimal.ZERO
										: new BigDecimal(String.valueOf(c[20])));
								service.setCreatedBy(user);
								service.setCreatedDate(new Date());
								service.setApprovedBy(user);
								service.setApprovedDate(new Date());
								service.setStatus("A");
								service.setCurrencyId(String.valueOf(c[4]));
								service.setDefaultChk("N");

								cfsstdtrfsrvrepo.save(service);
							}

							if ("RA".equals(type) && rate.compareTo(BigDecimal.ZERO) > 0) {
								List<Object[]> rangeServices = cfsstdtrfsrvrepo.getRangeServiceDtlForBLWiseUpload(cid,
										bid, String.valueOf(c[0]), "CFS1000001", conSize, conType);

								if (!rangeServices.isEmpty()) {

									AtomicInteger rangeIndex = new AtomicInteger(1);

									rangeServices.stream().forEach(r -> {
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
										service.setSrNo(new BigDecimal(rangeIndex.get()));
										service.setFromRange(new BigDecimal(String.valueOf(r[17])));
										service.setToRange(new BigDecimal(String.valueOf(r[21])));
										service.setRate(rate);
										service.setMinimumRate((c[20] == null || c[20] == "") ? BigDecimal.ZERO
												: new BigDecimal(String.valueOf(c[20])));
										service.setCreatedBy(user);
										service.setCreatedDate(new Date());
										service.setApprovedBy(user);
										service.setApprovedDate(new Date());
										service.setStatus("A");
										service.setCurrencyId(String.valueOf(c[4]));
										service.setDefaultChk("N");

										cfsstdtrfsrvrepo.save(service);

										rangeIndex.incrementAndGet();
									});
								}
							}

							if ("SA".equals(type) && rate.compareTo(BigDecimal.ZERO) > 0) {
								List<Object[]> rangeServices = cfsstdtrfsrvrepo.getRangeServiceDtlForBLWiseUpload(cid,
										bid, String.valueOf(c[0]), "CFS1000001", conSize, conType);

								if (!rangeServices.isEmpty()) {
									AtomicInteger rangeIndex = new AtomicInteger(1);

									rangeServices.stream().forEach(r -> {
										BigDecimal fromRange = new BigDecimal(String.valueOf(r[17]));
										BigDecimal toRange = new BigDecimal(String.valueOf(r[21]));
										BigDecimal currentRate = new BigDecimal(String.valueOf(r[3]));

										if (rate.compareTo(toRange) == 0 || rate.compareTo(toRange) > 0) {
											// If rate is exactly equal to 'toRange', set rate to zero
											CFSTariffService service = createCFSTariffService(cid, bid, autoCFSTariffNo,
													c, user, fromRange, toRange, BigDecimal.ZERO, rangeIndex.get(),
													amdNo);

											cfsstdtrfsrvrepo.save(service);
											rangeIndex.incrementAndGet();
										} else if (rate.compareTo(fromRange) >= 0 && rate.compareTo(toRange) < 0) {
											// If rate falls within the range, split into two slabs
											CFSTariffService service1 = createCFSTariffService(cid, bid,
													autoCFSTariffNo, c, user, fromRange, rate, BigDecimal.ZERO,
													rangeIndex.get(), amdNo);

											CFSTariffService service2 = createCFSTariffService(cid, bid,
													autoCFSTariffNo, c, user, rate.add(BigDecimal.ONE), toRange,
													currentRate, rangeIndex.incrementAndGet(), amdNo);

											cfsstdtrfsrvrepo.save(service1);
											cfsstdtrfsrvrepo.save(service2);
											rangeIndex.incrementAndGet();
										} else {
											// Default case, save as is
											CFSTariffService service = createCFSTariffService(cid, bid, autoCFSTariffNo,
													c, user, fromRange, toRange, currentRate, rangeIndex.get(), amdNo);

											cfsstdtrfsrvrepo.save(service);
											rangeIndex.incrementAndGet();
										}
									});
								}
							}

						});

					}

				}

				AtomicInteger index1 = new AtomicInteger(1);
				AtomicInteger valIndex = new AtomicInteger(7);

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05123", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("BILU%06d", nextNumericNextID1);

				serviceNameList.stream().forEach(s -> {

					String[] val = s.split("~");

					CFSBLDetails blDtls = new CFSBLDetails();
					blDtls.setCompanyId(cid);
					blDtls.setBranchId(bid);
					blDtls.setStatus("A");
					blDtls.setCreatedBy(user);
					blDtls.setCreatedDate(new Date());
					blDtls.setBlTransId(HoldNextIdD1);
					blDtls.setUpTariffNo(cfsTariffNo.get());
					blDtls.setUpTariffAmndNo(cfsAmndNo.get());
					blDtls.setSrNo(index1.get());
					blDtls.setIgmNo(i.get(0));
					blDtls.setIgmLineNo(i.get(1));
					blDtls.setContainerNo(i.get(2));
					blDtls.setContainerSize(i.get(3));
					blDtls.setDeliveryMode(i.get(4));
					blDtls.setSpecialDelivery(i.get(5));
					blDtls.setProfitcentreId(i.get(6));
					blDtls.setServiceId(val[0]);
					blDtls.setServiceDesc(val[1]);
					blDtls.setServiceAmt(
							(i.get(valIndex.get()) == null || i.get(valIndex.get()).isEmpty()) ? BigDecimal.ZERO
									: new BigDecimal(i.get(valIndex.get())));
					blDtls.setPartyId(id);
					blDtls.setFileUploadPath(blTariffUploadPath+newFileName);

					cfsBlDetailsrepo.save(blDtls);
					processnextidrepo.updateAuditTrail(cid, bid, "P05123", HoldNextIdD1, "2024");

					valIndex.incrementAndGet();
					index1.incrementAndGet();
				});

				conData.stream().forEach(con -> {
					int updateConList = cfigmcnrepo.updateConList(cid, bid, con.getIgmNo(), con.getIgmTransId(),
							con.getContainerNo(), HoldNextIdD1, cfsTariffNo.get(), cfsAmndNo.get());

				});

				if (conData.get(0).getUpTariffFwd() != null && !conData.get(0).getUpTariffFwd().isEmpty()) {
					tariffService.ammendBLWiseTariff(cid, bid, cfsTariffNo.get(), conData.get(0).getUpTariffAmndNo(),
							cfsAmndNo.get(), user);
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
}
