package com.cwms.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.Branch;
import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.IsoContainer;
import com.cwms.entities.Party;
import com.cwms.entities.Port;
import com.cwms.entities.Vessel;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.IsoContainerRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.PortRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VesselRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/excelUpload")
public class ExcelUploadController {

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private PortRepository portrepo;

	@Autowired
	private VesselRepository vesselrepo;

	@Autowired
	private IsoContainerRepository isoContainerRepository;

	@Autowired
	private BranchRepo branchrepo;

	@Value("${file.igmFormat}")
	private String filePath;

	@Value("${file.igmFiles}")
	private String igmFiles;

	@GetMapping("/excelFormatDownload")
	public ResponseEntity<?> downloadExcelFile() {
		try {
			// Path to your Excel file (you need to define 'filePath' correctly)
			File file = new File(filePath);

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

	@PostMapping("/upload-Igm")
	public ResponseEntity<?> handleFileUploadParty(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("finYear") String finYear,
			@RequestParam("file") MultipartFile file) throws ParseException {
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			// Get headers from the first row
			Row headerRow = sheet.getRow(0);
			Map<String, Integer> headerMap = new HashMap<>();
			for (Cell cell : headerRow) {
				headerMap.put(cell.getStringCellValue(), cell.getColumnIndex());
			}

			// Iterate through the remaining rows

			Map<String, Object> result = new HashMap<>();
			List<String> list = new ArrayList<>();

			Row row = sheet.getRow(1);
			if (row != null) {

				String igmNo = getCellValue(row, headerMap.get("IGM No"), evaluator);
				String igmDate = getCellValue(row, headerMap.get("IGM Date"), evaluator);
				Date igmDate1 = null;
				if (!igmDate.isEmpty()) {
					igmDate1 = parseExcelDate(igmDate);
				}
				String vesselBerthingDate = getCellValue(row, headerMap.get("Vessel Berthing Date"), evaluator);

				Date vesselBerthingDate1 = null;
				if (!vesselBerthingDate.isEmpty()) {
					vesselBerthingDate1 = parseExcelDate(vesselBerthingDate);
				}

				String vessel = getCellValue(row, headerMap.get("Vessel"), evaluator);
				String voyage = getCellValue(row, headerMap.get("Voyage"), evaluator);
				String viaNo = getCellValue(row, headerMap.get("Via No"), evaluator);
				String port = getCellValue(row, headerMap.get("Port"), evaluator);
				String sl = getCellValue(row, headerMap.get("Shipping Line"), evaluator);
				String sa = getCellValue(row, headerMap.get("Shipping Agent"), evaluator);

				if (igmNo.isEmpty()) {
					String mess = " - IGM No is required.";
					list.add(mess);
				} else {
					if (igmNo.length() > 10) {
						String mess = "For IGM no " + igmNo + " - IGM number length should not exceed 10 characters.";
						list.add(mess);
					}
				}

				if (igmDate.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - IGM Date is required.";
					list.add(mess);
				}

				if (vesselBerthingDate.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Vessel Berthing Date is required.";
					list.add(mess);
				}

				if (vessel.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Vessel is required.";
					list.add(mess);
				}

				if (voyage.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Voyage is required.";
					list.add(mess);
				} else {
					if (voyage.length() > 10) {
						String mess = "For IGM no " + igmNo + " - Voyage length should not exceed 10 characters.";
						list.add(mess);
					}
				}

				if (viaNo.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Via No is required.";
					list.add(mess);
				} else {
					if (viaNo.length() > 10) {
						String mess = "For IGM no " + igmNo + " - Via No length should not exceed 10 characters.";
						list.add(mess);

					}
				}

				if (port.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Port is required.";
					list.add(mess);
				}

				if (sl.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Shipping line is required.";
					list.add(mess);
				}

				if (sa.isEmpty()) {
					String mess = "For IGM no " + igmNo + " - Shipping Agent is required.";
					list.add(mess);
				}

				CFIgm igm = new CFIgm();

				Boolean isExist = cfigmrepo.isDataExist2(cid, bid, igmNo, sl);

				if (isExist) {
					igm = cfigmrepo.getDataByIgmNo3(cid, bid, igmNo, sl);
				} else {
					String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05060", "2024");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String HoldNextIdD1 = String.format("CIAR%06d", nextNumericNextID1);

					Port portName = portrepo.getPortByPortCode(cid, bid, port);

					if (portName == null) {
						String mess = "IGM no " + igmNo + " - Port not found in port master.";
						list.add(mess);
					}

					Party shippingLine = partyrepo.getDataByCustomerCode1(cid, bid, sl);

					if (shippingLine == null) {
						String mess = "IGM no " + igmNo + " - Shipping line not found in party master.";
						list.add(mess);
					}

					Party shippingAgent = partyrepo.getDataByCustomerCode2(cid, bid, sa);

					if (shippingAgent == null) {
						String mess = "IGM no " + igmNo + " - Shipping agent not found in party master.";
						list.add(mess);
					}

					Vessel vesselName = vesselrepo.getDataByVesselName(cid, bid, vessel);

					if (vesselName == null) {
						Vessel vessel1 = new Vessel();
						String holdId = processnextidrepo.findAuditTrail(cid, bid, "P03202", "2239");

						int lastNextNumericId = Integer.parseInt(holdId.substring(1));

						int nextNumericNextID = lastNextNumericId + 1;
						String HoldNextIdD = String.format("V%05d", nextNumericNextID);
						vessel1.setCompanyId(cid);
						vessel1.setVesselId(HoldNextIdD);
						vessel1.setBranchId(bid);
						vessel1.setVesselName(vessel);
						vessel1.setStatus("A");
						vessel1.setApprovedBy(user);
						vessel1.setApprovedDate(new Date());

						vesselrepo.save(vessel1);
						processnextidrepo.updateAuditTrail(cid, bid, "P03202", HoldNextIdD, "2239");

						igm.setVesselId(HoldNextIdD);
					} else {
						igm.setVesselId(vesselName.getVesselId());
					}

					igm.setFinYear(finYear);
					igm.setCompanyId(cid);
					igm.setIgmTransId(HoldNextIdD1);
					igm.setIgmNo(igmNo);
					igm.setVoyageNo(voyage);
					igm.setViaNo(viaNo);
					igm.setIgmDate(igmDate1);
					igm.setVesselEta(vesselBerthingDate1);
					igm.setBranchId(bid);
					igm.setPort(port);
					igm.setStatus('A');
					igm.setCreatedBy(user);
					igm.setCreatedDate(new Date());
					igm.setApprovedBy(user);
					igm.setApprovedDate(new Date());
					igm.setShippingLine(shippingLine != null ? shippingLine.getPartyId() : "");
					igm.setShippingAgent(shippingAgent != null ? shippingAgent.getPartyId() : "");
					igm.setProfitcentreId("N00002");
					igm.setDocDate(new Date());

					if (list.size() > 0) {
						result.put("message", "error");
						result.put("result", list);

						return new ResponseEntity<>(result, HttpStatus.OK);
					} else {
						cfigmrepo.save(igm);

						processnextidrepo.updateAuditTrail(cid, bid, "P05060", HoldNextIdD1, "2024");
					}

				}

				Row headerRow1 = sheet.getRow(2);
				Map<String, Integer> headerMap1 = new HashMap<>();
				for (Cell cell : headerRow1) {
					headerMap1.put(cell.getStringCellValue(), cell.getColumnIndex());
				}

				for (int i = 3; i <= sheet.getLastRowNum(); i++) {
					Row r = sheet.getRow(i);

					if (r != null) {
						String srNo = getCellValue(r, headerMap1.get("Sr No"), evaluator);
						String containerNo = getCellValue(r, headerMap1.get("Container No"), evaluator);
						String iso = getCellValue(r, headerMap1.get("ISO"), evaluator);
						String cargoWt = getCellValue(r, headerMap1.get("Cargo Weight(KGS)"), evaluator);
						String packages = getCellValue(r, headerMap1.get("Packages"), evaluator);
						String typeofPackages = getCellValue(r, headerMap1.get("Type Of Packages"), evaluator);
						String sealNo = getCellValue(r, headerMap1.get("Seal No"), evaluator);
						String itemNo = getCellValue(r, headerMap1.get("Item No"), evaluator);
						String blNo = getCellValue(r, headerMap1.get("BL Number"), evaluator);
						String blDate = getCellValue(r, headerMap1.get("BL Date"), evaluator);
						Date blDate1 = parseExcelDate(blDate);
						String status = getCellValue(r, headerMap1.get("Status"), evaluator);
						String cargoDesc = getCellValue(r, headerMap1.get("Cargo Description"), evaluator);
						String importerName = getCellValue(r, headerMap1.get("Importer Name"), evaluator);
						String address1 = getCellValue(r, headerMap1.get("Address1"), evaluator);
						String address2 = getCellValue(r, headerMap1.get("Address2"), evaluator);
						String address3 = getCellValue(r, headerMap1.get("Address3"), evaluator);
						String origin = getCellValue(r, headerMap1.get("Origin"), evaluator);
						Boolean check = false;

						if (srNo != null && !srNo.isEmpty()) {
							if (containerNo == null || containerNo.isEmpty()) {
								String mess = " - Container No is required.";
								check = true;
								list.add(mess);
							} else {
								if (containerNo.length() > 11) {
									String mess = "For Container no " + containerNo
											+ " - Container No length should not exceed 11 characters.";
									list.add(mess);
									check = true;

								}
							}

							if (iso == null || iso.isEmpty()) {
								String mess = "For Container no " + containerNo + " - ISO is required.";
								list.add(mess);
								check = true;
							} else {
								if (iso.length() > 4) {
									String mess = "For Container no " + containerNo
											+ " - ISO length should not exceed 4 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Cargo Weight (KGS) validation
							if (cargoWt == null || cargoWt.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Cargo Weight is required.";
								list.add(mess);
								check = true;
							} else {
								try {
									double weight = Double.parseDouble(cargoWt);
									if (weight <= 0) {
										String mess = "For Container no " + containerNo
												+ " - Cargo Weight should be a positive number.";
										list.add(mess);
										check = true;
									}
									if (cargoWt.length() > 8) {
										String mess = "For Container no " + containerNo
												+ " - Cargo Wt length should not exceed 8 characters.";
										list.add(mess);
										check = true;
									}
								} catch (NumberFormatException e) {
									String mess = "For Container no " + containerNo + " - Invalid number format.";
									list.add(mess);
									check = true;
								}
							}

							// Packages validation
							if (packages == null || packages.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Packages is required.";
								list.add(mess);
								check = true;
							} else {
								if (packages.length() > 10) {
									String mess = "For Container no " + containerNo
											+ " - Packages length should not exceed 10 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Type of Packages validation
							if (typeofPackages == null || typeofPackages.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Type Of Packages is required.";
								list.add(mess);
								check = true;
							} else {
								if (typeofPackages.length() > 6) {
									String mess = "For Container no " + containerNo
											+ " - Type Of package length should not exceed 6 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Seal No validation
							if (sealNo == null || sealNo.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Seal No is required.";
								list.add(mess);
								check = true;
							} else {
								if (sealNo.length() > 15) {
									String mess = "For Container no " + containerNo
											+ " - ISO length should not exceed 15 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Item No validation
							if (itemNo == null || itemNo.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Item No is required.";
								list.add(mess);
								check = true;
							} else {
								if (itemNo.length() > 7) {
									String mess = "For Container no " + containerNo
											+ " - Item no length should not exceed 7 characters.";
									list.add(mess);
									check = true;
								}
							}

							// BL Number validation
							if (blNo == null || blNo.isEmpty()) {
								String mess = "For Container no " + containerNo + " - BL Number is required.";
								list.add(mess);
								check = true;
							} else {
								if (blNo.length() > 20) { // Assuming 20 as max length for BL Number
									String mess = "For Container no " + containerNo
											+ " - BL Number length should not exceed 20 characters.";
									list.add(mess);
									check = true;
								}
							}

							// BL Date validation
							if (blDate == null || blDate.isEmpty()) {
								String mess = "For Container no " + containerNo + " - BL Date is required.";
								list.add(mess);
								check = true;
							}

							// Status validation
							if (status == null || status.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Status is required.";
								list.add(mess);
								check = true;
							} else {
								if (!"FCL".equals(status) && !"LCL".equals(status)) {
									String mess = "For Container no " + containerNo
											+ " - The status must be either FCL or LCL.";
									list.add(mess);
									check = true;
								}
							}

							// Cargo Description validation
							if (cargoDesc == null || cargoDesc.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Cargo Description is required.";
								list.add(mess);
								check = true;
							} else {
								if (cargoDesc.length() > 250) {
									String mess = "For Container no " + containerNo
											+ " - Cargo desc length should not exceed 250 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Importer Name validation
							if (importerName == null || importerName.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Importer Name is required.";
								list.add(mess);
								check = true;
							} else {
								if (importerName.length() > 100) {
									String mess = "For Container no " + containerNo
											+ " - Importer name length should not exceed 100 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Address1 validation
							if (address1 == null || address1.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Address1 is required.";
								list.add(mess);
								check = true;
							} else {
								if (address1.length() > 250) {
									String mess = "For Container no " + containerNo
											+ " - Address1 length should not exceed 250 characters.";
									list.add(mess);
									check = true;
								}
							}

							if (address2 != null && !address2.isEmpty()) {
								if (address2.length() > 100) {
									String mess = "For Container no " + containerNo
											+ " - Address2 length should not exceed 100 characters.";
									list.add(mess);
									check = true;
								}
							}

							if (address3 != null && !address3.isEmpty()) {
								if (address3.length() > 100) {
									String mess = "For Container no " + containerNo
											+ " - Address3 length should not exceed 100 characters.";
									list.add(mess);
									check = true;
								}
							}

							// Origin validation
							if (origin == null || origin.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Origin is required.";
								list.add(mess);
								check = true;
							} else {
								if (origin.length() > 50) {
									String mess = "For Container no " + containerNo
											+ " - Origin length should not exceed 50 characters.";
									list.add(mess);
									check = true;
								}
							}
						}

						if (itemNo != null && !itemNo.isEmpty()) {
							Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, itemNo, igmNo);

							if (exist1) {

								Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, igm.getIgmTransId(), igmNo, itemNo);

//    							if (crg == null) {
//    								return new ResponseEntity<>("Item data not found.", HttpStatus.BAD_REQUEST);
//    							}

								Boolean isExist2 = cfigmcnrepo.isExistContainer(cid, bid, igm.getIgmTransId(), igmNo,
										itemNo, containerNo);

								if (isExist2) {
									check = true;
									String mess = "Container No " + containerNo + " - Duplicate Container No";
									list.add(mess);

								}

								Cfigmcn cn = new Cfigmcn();

								String holdId3 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");

								int lastNextNumericId3 = Integer.parseInt(holdId3.substring(1));

								int nextNumericNextID3 = lastNextNumericId3 + 1;

								String HoldNextIdD3 = String.format("C%09d", nextNumericNextID3);

								IsoContainer isoData = isoContainerRepository.getDataByIsoCode(cid, iso);

								if (isoData == null) {
									check = true;
									String mess = "Container No " + containerNo + " - ISO data not found";
									list.add(mess);

								}

								cn.setContainerTransId(HoldNextIdD3);
								cn.setCompanyId(cid);
								cn.setBranchId(bid);
								cn.setStatus('A');
								cn.setCreatedBy(user);
								cn.setCreatedDate(new Date());
								cn.setApprovedBy(user);
								cn.setApprovedDate(new Date());
								cn.setContainerNo(containerNo);
								cn.setNoOfPackages(Integer.parseInt(packages));
								cn.setCustomsSealNo(sealNo);
								cn.setContainerStatus(status);
								cn.setIgmTransId(igm.getIgmTransId());
								cn.setIgmNo(igmNo);
								cn.setIgmLineNo(itemNo);
								cn.setProfitcentreId("N00002");
								cn.setIso(iso);
								cn.setFinYear(finYear);
								cn.setContainerSize(isoData.getContainerSize());
								cn.setContainerType(isoData.getContainerType());
								cn.setContainerWeight(isoData.getTareWeight());
								cn.setGrossWt(isoData.getTareWeight().add(crg.getGrossWeight()));
								if ("LCL".equals(status)) {
									cn.setGateOutType("CRG");
									cn.setUpTariffDelMode("CRG");
								}

								if (!check) {
									cfigmcnrepo.save(cn);
									processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD3, "2024");
									int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(),
											cn.getContainerNo());
									crg.setNoOfPackages(crg.getNoOfPackages()
											.add(new BigDecimal(packages).setScale(3, RoundingMode.HALF_UP)));
									cfigmcrgrepo.save(crg);
								}

							} else {

//    							Boolean exist = cfigmcrgrepo.isExistRecord(cid, bid, blNo);
//
//    							if (exist) {
//    								return new ResponseEntity<>("Duplicate BL No", HttpStatus.BAD_REQUEST);
//    							}

								Cfigmcrg crg = new Cfigmcrg();
								String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05061", "2024");

								int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

								int nextNumericNextID2 = lastNextNumericId2 + 1;

								String HoldNextIdD2 = String.format("ICR%07d", nextNumericNextID2);

								crg.setIgmCrgTransId(HoldNextIdD2);
								crg.setCompanyId(cid);
								crg.setProfitcentreId("N00002");
								crg.setBranchId(bid);
								crg.setIgmNo(igm.getIgmNo());
								crg.setIgmTransId(igm.getIgmTransId());
								crg.setCreatedBy(user);
								crg.setCreatedDate(new Date());
								crg.setViaNo(igm.getViaNo());
								crg.setApprovedBy(user);
								crg.setApprovedDate(new Date());
								crg.setStatus("A");
								if (cargoWt != null && !cargoWt.isEmpty()) {
									crg.setGrossWeight(new BigDecimal(cargoWt).setScale(3, RoundingMode.HALF_UP));
								} else {
									// Handle the case where cargoWt is null, e.g., set to zero or log an error
									crg.setGrossWeight(BigDecimal.ZERO); // or any appropriate default value
								}

								crg.setTypeOfPackage(typeofPackages);
								crg.setIgmLineNo(itemNo);
								crg.setBlNo(blNo);
								crg.setBlDate(blDate1);
								crg.setCommodityDescription(cargoDesc);
								crg.setImporterName(importerName);
								crg.setImporterAddress1(address1);
								crg.setImporterAddress2(address2);
								crg.setImporterAddress3(address3);
								crg.setUnitOfWeight("KGS");
								crg.setOrigin(origin);
								crg.setFinYear(finYear);
								crg.setNoOfPackages(BigDecimal.ZERO
										.add(new BigDecimal(packages).setScale(3, RoundingMode.HALF_UP)));

								cfigmcrgrepo.save(crg);
								processnextidrepo.updateAuditTrail(cid, bid, "P05061", HoldNextIdD2, "2024");

								Boolean isExist3 = cfigmcnrepo.isExistContainer(cid, bid, igm.getIgmTransId(), igmNo,
										itemNo, containerNo);

								if (isExist3) {
									check = true;
									String mess = "Container No " + containerNo + " - Duplicate container no";
									list.add(mess);
								}

								Cfigmcn cn = new Cfigmcn();

								String holdId3 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");

								int lastNextNumericId3 = Integer.parseInt(holdId3.substring(1));

								int nextNumericNextID3 = lastNextNumericId3 + 1;

								String HoldNextIdD3 = String.format("C%09d", nextNumericNextID3);

								IsoContainer isoData = isoContainerRepository.getDataByIsoCode(cid, iso);

								if (isoData == null) {
									check = true;
									String mess = "Container No " + containerNo + " - ISO data not found";
									list.add(mess);
								}

								cn.setContainerTransId(HoldNextIdD3);
								cn.setCompanyId(cid);
								cn.setBranchId(bid);
								cn.setStatus('A');
								cn.setCreatedBy(user);
								cn.setCreatedDate(new Date());
								cn.setApprovedBy(user);
								cn.setApprovedDate(new Date());
								cn.setContainerNo(containerNo);
								cn.setNoOfPackages(Integer.parseInt(packages));
								cn.setCustomsSealNo(sealNo);
								cn.setContainerStatus(status);
								cn.setIgmTransId(igm.getIgmTransId());
								cn.setIgmNo(igmNo);
								cn.setIgmLineNo(itemNo);
								cn.setProfitcentreId("N00002");
								cn.setIso(iso);
								cn.setFinYear(finYear);
								cn.setContainerSize(isoData.getContainerSize());
								cn.setContainerType(isoData.getContainerType());
								cn.setContainerWeight(isoData.getTareWeight());
								cn.setGrossWt(isoData.getTareWeight().add(crg.getGrossWeight()));
								if ("LCL".equals(status)) {
									cn.setGateOutType("CRG");
									cn.setUpTariffDelMode("CRG");
								}

								if (!check) {
									cfigmcnrepo.save(cn);
									int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(),
											cn.getContainerNo());
									processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD3, "2024");
								}

							}
						}
					}
				}

			}

			if (list.size() > 0) {
				result.put("message", "error");
				result.put("result", list);

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("message", "success");
				result.put("result", "File uploaded and processed successfully");

				return new ResponseEntity<>(result, HttpStatus.OK);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the file");
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

	private static Date parseExcelDate(String dateStr) {
		if (dateStr == null || dateStr.isEmpty()) {
			return null;
		}

		try {
			// Check if the date is in numeric format (Excel date as a number)
			double numericValue = Double.parseDouble(dateStr);
			return DateUtil.getJavaDate(numericValue);
		} catch (NumberFormatException e) {
			// If it's not a numeric value, try parsing it as a date string
			try {
				// Define the expected date format. Adjust the pattern if necessary.
				SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
				return sdf.parse(dateStr);
			} catch (ParseException pe) {
				pe.printStackTrace();
				return null;
			}
		}
	}

	public ResponseEntity<?> saveIgm(String cid, String bid, String user, String finYear,
			List<Map<String, String>> itemData, List<Map<String, String>> conData, CFIgm igm) {

		if (itemData.size() > 0) {
			CFIgm existingIgm = cfigmrepo.getDataByIgmNoAndSl(cid, bid, igm.getIgmNo(), igm.getShippingLine());

			Map<String, Object> result = new HashMap<>();
			List<String> list = new ArrayList<>();
			List<String> itemList = new ArrayList<>();

			if (existingIgm != null) {
				for (Map<String, String> m : itemData) {
					Boolean check = false;
					String itemNo = m.get("Item no");
					String blNo = m.get("Bl no");
					String blDate = m.get("Bl Date");
					String importer = m.get("Importer name");
					String impAddress1 = m.get("Importer address1");
					String impAddress2 = m.get("Importer address2");
					String impAddress3 = m.get("Importer address3");
					String notifyParty = m.get("Notify Party");
					String notifyPartyAddress1 = m.get("Notify Party address1");
					String notifyPartyAddress2 = m.get("Notify Party address2");
					String notifyPartyAddress3 = m.get("Notify Party address3");
					String cargotype = m.get("Cargo type");
					String cfsCode = m.get("Cfs code");
					String packages = m.get("Packages");
					String packageType = m.get("Package Type");
					String cargoWt = m.get("Cargo wt");
					String commodity = m.get("Commodity");
					String origin = m.get("Origin");

					if (itemNo.isEmpty()) {
						String mess = " - Item No is required.";
						list.add(mess);
						check = true;
					} else {
						if (itemNo.length() > 7) {
							String mess = "For Item no " + itemNo + " - Item No length should not exceed 7 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (blNo.isEmpty()) {
						String mess = "For Item no " + itemNo + " - BL No is required.";
						list.add(mess);
						check = true;
					} else {
						if (blNo.length() > 20) {
							String mess = "For Item no " + itemNo + " - BL No length should not exceed 20 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (blDate.isEmpty()) {
						String mess = "For Item no " + itemNo + " - BL date is required.";
						list.add(mess);
						check = true;
					}

					if (importer.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Importer is required.";
						list.add(mess);
						check = true;
					} else {
						if (importer.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Importer length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}
					if (impAddress1.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Importer address1 is required.";
						list.add(mess);
						check = true;
					} else {
						if (impAddress1.length() > 250) {
							String mess = "For Item no " + itemNo
									+ " - Importer address1 length should not exceed 250 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!impAddress2.isEmpty()) {
						if (impAddress2.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Importer address2 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!impAddress3.isEmpty()) {
						if (impAddress3.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Importer address3 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyParty.isEmpty()) {
						if (notifyParty.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Notify party length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyPartyAddress1.isEmpty()) {
						if (notifyPartyAddress1.length() > 250) {
							String mess = "For Item no " + itemNo
									+ " - Notify party address1 length should not exceed 250 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyPartyAddress2.isEmpty()) {
						if (notifyPartyAddress2.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Notify party address2 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyPartyAddress3.isEmpty()) {
						if (notifyPartyAddress3.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Notify party address3 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (packages.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Packages is required.";
						list.add(mess);
						check = true;
					}

					if (packageType.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Package type is required.";
						list.add(mess);
						check = true;
					} else {
						if (packageType.length() > 3) {
							String mess = "For Item no " + itemNo
									+ " - Pakage type length should not exceed 3 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (cargoWt.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Cargo wt is required.";
						list.add(mess);
						check = true;
					} else {
						if (cargoWt.length() > 12) {
							String mess = "For Item no " + itemNo
									+ " - Cargo wt length should not exceed 12 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (commodity.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Commodity is required.";
						list.add(mess);
						check = true;
					} else {
						if (commodity.length() > 250) {
							String mess = "For Item no " + itemNo
									+ " - Commodity length should not exceed 250 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (origin.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Origin is required.";
						list.add(mess);
						check = true;
					} else {
						if (origin.length() > 50) {
							String mess = "For Item no " + itemNo + " - Origin length should not exceed 50 characters.";
							list.add(mess);
							check = true;
						}
					}

					SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
					Date blDate1 = null;
					try {
						blDate1 = format.parse(blDate);

						if (blDate1 == null) {

							String mess = "For Item no " + itemNo + " - Invalid BL Date.";
							list.add(mess);
							check = true;

						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						String mess = "For Item no " + itemNo + " - Invalid BL date format.";
						list.add(mess);
						check = true;

					}

					Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, itemNo, existingIgm.getIgmNo());

					if (!exist1) {
						Cfigmcrg crg = new Cfigmcrg();
						String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05061", "2024");

						int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

						int nextNumericNextID2 = lastNextNumericId2 + 1;

						String HoldNextIdD2 = String.format("ICR%07d", nextNumericNextID2);

						crg.setIgmCrgTransId(HoldNextIdD2);
						crg.setCompanyId(cid);
						crg.setProfitcentreId("N00002");
						crg.setBranchId(bid);
						crg.setIgmNo(existingIgm.getIgmNo());
						crg.setIgmTransId(existingIgm.getIgmTransId());
						crg.setCreatedBy(user);
						crg.setCreatedDate(new Date());
						crg.setViaNo(existingIgm.getViaNo());
						crg.setApprovedBy(user);
						crg.setApprovedDate(new Date());
						crg.setStatus("A");
						if (cargoWt != null && !cargoWt.isEmpty()) {
							crg.setGrossWeight(new BigDecimal(cargoWt).setScale(3, RoundingMode.HALF_UP));
						} else {
							crg.setGrossWeight(BigDecimal.ZERO);
						}

						crg.setTypeOfPackage(packageType);
						crg.setCargoType(cargotype);
						crg.setIgmLineNo(itemNo);
						crg.setBlNo(blNo);
						crg.setBlDate(blDate1);
						crg.setCommodityDescription(commodity);
						crg.setImporterName(importer);
						crg.setImporterAddress1(impAddress1);
						crg.setImporterAddress2(impAddress2);
						crg.setImporterAddress3(impAddress3);
						crg.setUnitOfWeight("KGS");
						crg.setOrigin(origin);
						crg.setFinYear(finYear);
						crg.setNotifyPartyName(notifyParty);
						crg.setNotifiedAddress1(notifyPartyAddress1);
						crg.setNotifiedAddress2(notifyPartyAddress2);
						crg.setNotifiedAddress3(notifyPartyAddress3);
						crg.setNoOfPackages(
								BigDecimal.ZERO.add(new BigDecimal(packages).setScale(3, RoundingMode.HALF_UP)));

						if (!check) {
							cfigmcrgrepo.save(crg);
							processnextidrepo.updateAuditTrail(cid, bid, "P05061", HoldNextIdD2, "2024");
						}

						if (check) {
							if (!itemNo.isEmpty()) {
								itemList.add(itemNo);
							}

						}

					}

				}

				if (conData.size() > 0) {
					for (Map<String, String> c : conData) {

						String containerNo = c.get("Container no");
						String itemNo = c.get("Item no");
						String sealNo = c.get("Seal no");
						String conStatus = c.get("Container Status");
						String packages = c.get("Packages");
						String cargoWt = c.get("Cargo Wt.");
						String iso = c.get("ISO");

						Boolean check = itemList.stream().anyMatch(c1 -> c1.equals(itemNo));
						Boolean check1 = false;

						if (!check) {

							if (containerNo.isEmpty()) {
								String mess = "For Item no " + itemNo + " - Container no is required.";
								list.add(mess);
								check1 = true;
							} else {
								if (containerNo.length() > 11) {
									String mess = "For Container no " + containerNo
											+ " - Container No length should not exceed 11 characters.";
									list.add(mess);
									check1 = true;

								}
							}

							if (itemNo.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Item no is required.";
								list.add(mess);
								check1 = true;
							} else {
								if (itemNo.length() > 7) {
									String mess = "For Container no " + containerNo
											+ " - Item no length should not exceed 11 characters.";
									list.add(mess);
									check1 = true;

								}
							}

							if (conStatus.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Container status is required.";
								list.add(mess);
								check1 = true;
							} else {
								if (!"FCL".equals(conStatus) && !"LCL".equals(conStatus)) {
									String mess = "For Container no " + containerNo
											+ " - Container status should be 'FCL' OR 'LCL'.";
									list.add(mess);
									check1 = true;

								}
							}

							if (packages.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Packages is required.";
								list.add(mess);
								check1 = true;
							}

							if (iso.isEmpty()) {
								String mess = "For Container no " + containerNo + " - ISO is required.";
								list.add(mess);
								check1 = true;
							}

							Boolean isExist3 = cfigmcnrepo.isExistContainer(cid, bid, existingIgm.getIgmTransId(),
									existingIgm.getIgmNo(), itemNo, containerNo);

							if (isExist3) {
								// return new ResponseEntity<>("Duplicate container no", HttpStatus.CONFLICT);
								String mess = "For Container no " + containerNo + " - Duplicate container no.";
								list.add(mess);
								check1 = true;
							}

							Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, existingIgm.getIgmTransId(), igm.getIgmNo(),
									itemNo);

							if (crg == null) {
								// return new ResponseEntity<>("Item data not found", HttpStatus.CONFLICT);
								String mess = "For Container no " + containerNo + " - Item data not found.";
								list.add(mess);
								check1 = true;
							}

							Cfigmcn cn = new Cfigmcn();

							String holdId3 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");

							int lastNextNumericId3 = Integer.parseInt(holdId3.substring(1));

							int nextNumericNextID3 = lastNextNumericId3 + 1;

							String HoldNextIdD3 = String.format("C%09d", nextNumericNextID3);

							IsoContainer isoData = isoContainerRepository.getDataByIsoCode(cid, iso);

							if (isoData == null) {
								// return new ResponseEntity<>("ISO data not found", HttpStatus.CONFLICT);
								String mess = "For Container no " + containerNo + " - ISO data not found.";
								list.add(mess);
								check1 = true;
							}

							cn.setContainerTransId(HoldNextIdD3);
							cn.setCompanyId(cid);
							cn.setBranchId(bid);
							cn.setStatus('A');
							cn.setCreatedBy(user);
							cn.setCreatedDate(new Date());
							cn.setApprovedBy(user);
							cn.setApprovedDate(new Date());
							cn.setContainerNo(containerNo);
							cn.setNoOfPackages(Integer.parseInt(packages));
							cn.setCustomsSealNo(sealNo);
							cn.setContainerStatus(conStatus);
							cn.setIgmTransId(existingIgm.getIgmTransId());
							cn.setIgmNo(existingIgm.getIgmNo());
							cn.setIgmLineNo(itemNo);
							cn.setProfitcentreId("N00002");
							cn.setIso(iso);
							cn.setFinYear(finYear);
							if(isoData != null) {
								cn.setContainerSize(isoData.getContainerSize());
								cn.setContainerType(isoData.getContainerType());
								cn.setContainerWeight(isoData.getTareWeight());
								cn.setGrossWt(isoData.getTareWeight().add(crg.getGrossWeight()));
							}
							if ("LCL".equals(conStatus)) {
								cn.setGateOutType("CRG");
								cn.setUpTariffDelMode("CRG");
							}

							if (!check1) {
								cfigmcnrepo.save(cn);
								int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(),
										cn.getContainerNo());
								processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD3, "2024");
							}

						} else {
							String mess = "For Container no " + containerNo + " - Error for Item no " + itemNo;
							list.add(mess);
							check = true;
						}

					}
				}

			} else {

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05060", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("CIAR%06d", nextNumericNextID1);

				igm.setCompanyId(cid);
				igm.setIgmTransId(HoldNextIdD1);
				igm.setBranchId(bid);
				igm.setStatus('A');
				igm.setCreatedBy(user);
				igm.setCreatedDate(new Date());
				igm.setApprovedBy(user);
				igm.setApprovedDate(new Date());
				cfigmrepo.save(igm);
				processnextidrepo.updateAuditTrail(cid, bid, "P05060", HoldNextIdD1, "2024");

				for (Map<String, String> m : itemData) {
					String itemNo = m.get("Item no");
					String blNo = m.get("Bl no");
					String blDate = m.get("Bl Date");
					String importer = m.get("Importer name");
					String impAddress1 = m.get("Importer address1");
					String impAddress2 = m.get("Importer address2");
					String impAddress3 = m.get("Importer address3");
					String notifyParty = m.get("Notify Party");
					String notifyPartyAddress1 = m.get("Notify Party address1");
					String notifyPartyAddress2 = m.get("Notify Party address2");
					String notifyPartyAddress3 = m.get("Notify Party address3");
					String cargotype = m.get("Cargo type");
					String cfsCode = m.get("Cfs code");
					String packages = m.get("Packages");
					String packageType = m.get("Package Type");
					String cargoWt = m.get("Cargo wt");
					String commodity = m.get("Commodity");
					String origin = m.get("Origin");

					Boolean check = false;

					if (itemNo.isEmpty()) {
						String mess = " - Item No is required.";
						list.add(mess);
						check = true;
					} else {
						if (itemNo.length() > 7) {
							String mess = "For Item no " + itemNo + " - Item No length should not exceed 7 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (blNo.isEmpty()) {
						String mess = "For Item no " + itemNo + " - BL No is required.";
						list.add(mess);
						check = true;
					} else {
						if (blNo.length() > 20) {
							String mess = "For Item no " + itemNo + " - BL No length should not exceed 20 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (blDate.isEmpty()) {
						String mess = "For Item no " + itemNo + " - BL date is required.";
						list.add(mess);
						check = true;
					}

					if (importer.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Importer is required.";
						list.add(mess);
						check = true;
					} else {
						if (importer.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Importer length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}
					if (impAddress1.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Importer address1 is required.";
						list.add(mess);
						check = true;
					} else {
						if (impAddress1.length() > 250) {
							String mess = "For Item no " + itemNo
									+ " - Importer address1 length should not exceed 250 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!impAddress2.isEmpty()) {
						if (impAddress2.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Importer address2 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!impAddress3.isEmpty()) {
						if (impAddress3.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Importer address3 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyParty.isEmpty()) {
						if (notifyParty.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Notify party length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyPartyAddress1.isEmpty()) {
						if (notifyPartyAddress1.length() > 250) {
							String mess = "For Item no " + itemNo
									+ " - Notify party address1 length should not exceed 250 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyPartyAddress2.isEmpty()) {
						if (notifyPartyAddress2.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Notify party address2 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (!notifyPartyAddress3.isEmpty()) {
						if (notifyPartyAddress3.length() > 100) {
							String mess = "For Item no " + itemNo
									+ " - Notify party address3 length should not exceed 100 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (packages.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Packages is required.";
						list.add(mess);
						check = true;
					}

					if (packageType.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Package type is required.";
						list.add(mess);
						check = true;
					} else {
						if (packageType.length() > 3) {
							String mess = "For Item no " + itemNo
									+ " - Pakage type length should not exceed 3 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (cargoWt.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Cargo wt is required.";
						list.add(mess);
						check = true;
					} else {
						if (cargoWt.length() > 12) {
							String mess = "For Item no " + itemNo
									+ " - Cargo wt length should not exceed 12 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (commodity.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Commodity is required.";
						list.add(mess);
						check = true;
					} else {
						if (commodity.length() > 250) {
							String mess = "For Item no " + itemNo
									+ " - Commodity length should not exceed 250 characters.";
							list.add(mess);
							check = true;
						}
					}

					if (origin.isEmpty()) {
						String mess = "For Item no " + itemNo + " - Origin is required.";
						list.add(mess);
						check = true;
					} else {
						if (origin.length() > 50) {
							String mess = "For Item no " + itemNo + " - Origin length should not exceed 50 characters.";
							list.add(mess);
							check = true;
						}
					}

					SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
					Date blDate1 = null;
					try {
						blDate1 = format.parse(blDate);

						if (blDate1 == null) {

							String mess = "For Item no " + itemNo + " - Invalid BL Date.";
							list.add(mess);
							check = true;

						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						String mess = "For Item no " + itemNo + " - Invalid BL date format.";
						list.add(mess);
						check = true;

					}

					Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, itemNo, igm.getIgmNo());

					if (!exist1) {
						Cfigmcrg crg = new Cfigmcrg();
						String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05061", "2024");

						int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));

						int nextNumericNextID2 = lastNextNumericId2 + 1;

						String HoldNextIdD2 = String.format("ICR%07d", nextNumericNextID2);

						crg.setIgmCrgTransId(HoldNextIdD2);
						crg.setCompanyId(cid);
						crg.setProfitcentreId("N00002");
						crg.setBranchId(bid);
						crg.setIgmNo(igm.getIgmNo());
						crg.setIgmTransId(HoldNextIdD1);
						crg.setCreatedBy(user);
						crg.setCreatedDate(new Date());
						crg.setViaNo(igm.getViaNo());
						crg.setApprovedBy(user);
						crg.setApprovedDate(new Date());
						crg.setStatus("A");
						if (cargoWt != null && !cargoWt.isEmpty()) {
							crg.setGrossWeight(new BigDecimal(cargoWt).setScale(3, RoundingMode.HALF_UP));
						} else {
							crg.setGrossWeight(BigDecimal.ZERO);
						}

						crg.setTypeOfPackage(packageType);
						crg.setCargoType(cargotype);
						crg.setIgmLineNo(itemNo);
						crg.setBlNo(blNo);
						crg.setBlDate(blDate1);
						crg.setCommodityDescription(commodity);
						crg.setImporterName(importer);
						crg.setImporterAddress1(impAddress1);
						crg.setImporterAddress2(impAddress2);
						crg.setImporterAddress3(impAddress3);
						crg.setUnitOfWeight("KGS");
						crg.setOrigin(origin);
						crg.setFinYear(finYear);
						crg.setNotifyPartyName(notifyParty);
						crg.setNotifiedAddress1(notifyPartyAddress1);
						crg.setNotifiedAddress2(notifyPartyAddress2);
						crg.setNotifiedAddress3(notifyPartyAddress3);
						crg.setNoOfPackages(
								BigDecimal.ZERO.add(new BigDecimal(packages).setScale(3, RoundingMode.HALF_UP)));

						if (!check) {
							cfigmcrgrepo.save(crg);
							processnextidrepo.updateAuditTrail(cid, bid, "P05061", HoldNextIdD2, "2024");
						}

						if (check) {
							if (!itemNo.isEmpty()) {
								itemList.add(itemNo);
							}

						}

					}

				}

				if (conData.size() > 0) {
					for (Map<String, String> c : conData) {

						String containerNo = c.get("Container no");
						String itemNo = c.get("Item no");
						String sealNo = c.get("Seal no");
						String conStatus = c.get("Container Status");
						String packages = c.get("Packages");
						String cargoWt = c.get("Cargo Wt.");
						String iso = c.get("ISO");

						Boolean check = itemList.stream().anyMatch(c1 -> c1.equals(itemNo));
						Boolean check1 = false;

						if (!check) {

							if (containerNo.isEmpty()) {
								String mess = "For Item no " + itemNo + " - Container no is required.";
								list.add(mess);
								check1 = true;
							} else {
								if (containerNo.length() > 11) {
									String mess = "For Container no " + containerNo
											+ " - Container No length should not exceed 11 characters.";
									list.add(mess);
									check1 = true;

								}
							}

							if (itemNo.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Item no is required.";
								list.add(mess);
								check1 = true;
							} else {
								if (itemNo.length() > 7) {
									String mess = "For Container no " + containerNo
											+ " - Item no length should not exceed 11 characters.";
									list.add(mess);
									check1 = true;

								}
							}

							if (conStatus.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Container status is required.";
								list.add(mess);
								check1 = true;
							} else {
								if (!"FCL".equals(conStatus) && !"LCL".equals(conStatus)) {
									String mess = "For Container no " + containerNo
											+ " - Container status should be 'FCL' OR 'LCL'.";
									list.add(mess);
									check1 = true;

								}
							}

							if (packages.isEmpty()) {
								String mess = "For Container no " + containerNo + " - Packages is required.";
								list.add(mess);
								check1 = true;
							}

							if (iso.isEmpty()) {
								String mess = "For Container no " + containerNo + " - ISO is required.";
								list.add(mess);
								check1 = true;
							}

							Boolean isExist3 = cfigmcnrepo.isExistContainer(cid, bid, igm.getIgmTransId(),
									igm.getIgmNo(), itemNo, containerNo);

							if (isExist3) {
								// return new ResponseEntity<>("Duplicate container no", HttpStatus.CONFLICT);
								String mess = "For Container no " + containerNo + " - Duplicate container no.";
								list.add(mess);
								check1 = true;
							}

							Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, igm.getIgmTransId(), igm.getIgmNo(), itemNo);

							if (crg == null) {
								// return new ResponseEntity<>("Item data not found", HttpStatus.CONFLICT);
								String mess = "For Container no " + containerNo + " - Item data not found.";
								list.add(mess);
								check1 = true;
							}

							Cfigmcn cn = new Cfigmcn();

							String holdId3 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");

							int lastNextNumericId3 = Integer.parseInt(holdId3.substring(1));

							int nextNumericNextID3 = lastNextNumericId3 + 1;

							String HoldNextIdD3 = String.format("C%09d", nextNumericNextID3);

							IsoContainer isoData = isoContainerRepository.getDataByIsoCode(cid, iso);

							if (isoData == null) {
								// return new ResponseEntity<>("ISO data not found", HttpStatus.CONFLICT);
								String mess = "For Container no " + containerNo + " - ISO data not found.";
								list.add(mess);
								check1 = true;
							}

							cn.setContainerTransId(HoldNextIdD3);
							cn.setCompanyId(cid);
							cn.setBranchId(bid);
							cn.setStatus('A');
							cn.setCreatedBy(user);
							cn.setCreatedDate(new Date());
							cn.setApprovedBy(user);
							cn.setApprovedDate(new Date());
							cn.setContainerNo(containerNo);
							cn.setNoOfPackages(Integer.parseInt(packages));
							cn.setCustomsSealNo(sealNo);
							cn.setContainerStatus(conStatus);
							cn.setIgmTransId(igm.getIgmTransId());
							cn.setIgmNo(igm.getIgmNo());
							cn.setIgmLineNo(itemNo);
							cn.setProfitcentreId("N00002");
							cn.setIso(iso);
							cn.setFinYear(finYear);
							if(isoData != null) {
								cn.setContainerSize(isoData.getContainerSize());
								cn.setContainerType(isoData.getContainerType());
								cn.setContainerWeight(isoData.getTareWeight());
								cn.setGrossWt(isoData.getTareWeight().add(crg.getGrossWeight()));
							}
						

							if ("LCL".equals(conStatus)) {
								cn.setGateOutType("CRG");
								cn.setUpTariffDelMode("CRG");
							}

							if (!check1) {
								cfigmcnrepo.save(cn);
								int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(),
										cn.getContainerNo());
								processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD3, "2024");
							}

						} else {
							String mess = "For Container no " + containerNo + " - Error for Item no " + itemNo;
							list.add(mess);
							check = true;
						}
					}
				}
			}

			if (list.size() > 0) {
				result.put("message", "error");
				result.put("result", list);

				return new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("message", "success");
				result.put("result", "File uploaded and processed successfully");

				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("Data for cfs code not found", HttpStatus.CONFLICT);
		}

	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadIgmFile(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("finYear") String finYear,
			@RequestParam("file") MultipartFile file, @RequestParam("igm") String igmData) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			CFIgm igm = objectMapper.readValue(igmData, CFIgm.class); // Deseriali

			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("File is empty.");
			}

			// Create timestamp for the new file name
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String newFileName = "IGM_File_" + igm.getIgmNo() + "_" + igm.getShippingLine() + "_" + timestamp + ".txt";

			// Define the path where the new file will be saved
			Path newFilePath = Paths.get(igmFiles + newFileName);

			// Save the uploaded MultipartFile to a temporary file first
			File tempFile = File.createTempFile("upload", file.getOriginalFilename());
			file.transferTo(tempFile);

			// Read the content of the temporary file
			List<String> fileContent = Files.readAllLines(tempFile.toPath(), StandardCharsets.UTF_8);

			// Write the content to the new file
			Files.write(newFilePath, fileContent, StandardCharsets.UTF_8, StandardOpenOption.CREATE);

			// Optionally, delete the temporary file after processing
			tempFile.delete();

			// Now, create a new File object for the newly created file
			File newFile = newFilePath.toFile();

			// Call the service to process the new file
			List<List<String>> content = readAndParseCargoSection(newFile);
			List<List<String>> content1 = readAndParseContainerSection(newFile);


			List<Map<String, String>> itemData = new ArrayList<>();

			List<Map<String, String>> conData = new ArrayList<>();

			Branch b = branchrepo.findByBranchIdWithCompanyId(cid, bid);

			List<String> itemList = new ArrayList<>();

			content.stream().forEach(c -> {

				if (b.getCfsCode().equals(c.get(25).toString())) {
					Map<String, String> add = new HashMap<>();
					add.put("Item no", c.get(6));
					add.put("Bl no", c.get(8));
					add.put("Bl Date", c.get(9));
					add.put("Origin", c.get(10));
					add.put("Importer name", c.get(14));
					add.put("Importer address1", c.get(15));
					add.put("Importer address2", c.get(16));
					add.put("Importer address3", c.get(17));
					add.put("Notify Party", c.get(18));
					add.put("Notify Party address1", c.get(19));
					add.put("Notify Party address2", c.get(20));
					add.put("Notify Party address3", c.get(21));
					add.put("Cargo type", c.get(24));
					add.put("Cfs code", c.get(25));
					add.put("Packages", c.get(26));
					add.put("Package Type", c.get(27));
					add.put("Cargo wt", c.get(28));
					add.put("Commodity", c.get(29));

					itemData.add(add);

					itemList.add(c.get(6));
				}

			});

			if (content.size() > 0) {
				content1.stream().forEach(c -> {

					Boolean check = itemList.stream().anyMatch(c1 -> c1.equals(c.get(6).toString()));

					if (check) {
						Map<String, String> add = new HashMap<>();
						add.put("Container no", c.get(8));
						add.put("Item no", c.get(6));
						add.put("Seal No", c.get(9));
						add.put("Container Status", c.get(11));
						add.put("Packages", c.get(12));
						add.put("Cargo Wt.", c.get(13));
						add.put("ISO", c.get(14));

						conData.add(add);
					}

				});

			}

			return saveIgm(cid, bid, user, finYear, itemData, conData, igm);
			// return ResponseEntity.ok(data);
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Failed to read the file.");
		}
	}

	public List<List<String>> readAndParseIgmFile(MultipartFile file) throws IOException {
		List<List<String>> records = new ArrayList<>();
		StringBuilder content = new StringBuilder();

		// Read the file content with UTF-8 encoding
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				content.append(line); // Append each line (without adding extra spaces or newlines)
			}
		}

		// Process the content, replacing the ASCII 29 character ( ) with a recognizable
		// delimiter
		String processedContent = content.toString();

		// Split by the Group Separator ' ' (ASCII 29) directly, as it should be treated
		// as a field separator
		String[] lines = processedContent.split("\u001D");

		// Process each line/record
		for (String record : lines) {
			// Split the record fields by ' '
			String[] fields = record.split("\u001D");

			List<String> recordFields = new ArrayList<>();
			for (String field : fields) {
				// Remove characters with ASCII value 0
				StringBuilder filteredField = new StringBuilder();
				for (char c : field.toCharArray()) {
					if ((int) c != 0) { // Exclude characters with ASCII value 0
						filteredField.append(c);
					}
				}

				// If the filtered field is not empty, process it further
				String trimmedField = filteredField.toString().trim();
				if (!trimmedField.isEmpty()) {
					recordFields.add(trimmedField); // Add the trimmed field

					// Prepare ASCII values for the valid characters in the filtered field
					StringBuilder asciiValues = new StringBuilder();
					for (char c : trimmedField.toCharArray()) {
						int asciiValue = (int) c;
						if (asciiValue != 0) { // Only include non-zero ASCII values
							asciiValues.append(asciiValue).append(","); // Append ASCII value
						}
					}

					// Append ASCII values if there are any valid ones
					if (asciiValues.length() > 0) {
						// Remove the trailing comma
						asciiValues.setLength(asciiValues.length() - 1);
						recordFields.add("ASCII values: " + asciiValues.toString());
					}
				}
			}

			// Only add non-empty lists to the records
			if (!recordFields.isEmpty()) {
				records.add(recordFields);
			}
		}

		return records;
	}

	public List<List<String>> readAndParseCargoSection(File file) throws IOException {
		List<List<String>> records = new ArrayList<>();
		StringBuilder content = new StringBuilder();
		boolean isCargoSection = false;

		  try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Check if we are inside the <cargo> section
				if (line.contains("<cargo>")) {
					isCargoSection = true;
					continue; // Skip the <cargo> line itself
				}

				// If we hit <END-cargo>, stop processing
				if (line.contains("<END-cargo>")) {
					isCargoSection = false;
					break; // Stop reading further
				}

				// Only process lines within <cargo> and <END-cargo>
				if (isCargoSection) {
					content.append(line);
				}
			}
		}

		// Replace "F " with a new line to indicate record boundaries
		String processedContent = content.toString().replace("F", "\n");

		// Split by new line to get each record
		String[] lines = processedContent.split("\n");

		// Process each line
		for (String record : lines) {
			// Split by the delimiter " "
			String[] fields = record.split("");

			// Store each field as a list of strings
			List<String> recordFields = new ArrayList<>();
			for (String field : fields) {
				recordFields.add(field.trim()); // Optional: trim to remove excess spaces
			}

			if (!recordFields.isEmpty() && recordFields.stream().anyMatch(field -> !field.isEmpty())) {
				records.add(recordFields); // Only add if it has meaningful content
			}

		}

		return records;
	}

	public List<List<String>> readAndParseContainerSection(File file) throws IOException {
		List<List<String>> records = new ArrayList<>();
		StringBuilder content = new StringBuilder();
		boolean isCargoSection = false;

		  try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Check if we are inside the <cargo> section
				if (line.contains("<contain>")) {
					isCargoSection = true;
					continue; // Skip the <cargo> line itself
				}

				// If we hit <END-cargo>, stop processing
				if (line.contains("<END-contain>")) {
					isCargoSection = false;
					break; // Stop reading further
				}

				// Only process lines within <cargo> and <END-cargo>
				if (isCargoSection) {
					content.append(line);
				}
			}
		}

		// Replace "F " with a new line to indicate record boundaries
		String processedContent = content.toString().replace("F", "\n");

		// Split by new line to get each record
		String[] lines = processedContent.split("\n");

		// Process each line
		for (String record : lines) {
			// Split by the delimiter " "
			String[] fields = record.split("");

			// Store each field as a list of strings
			List<String> recordFields = new ArrayList<>();
			for (String field : fields) {
				recordFields.add(field.trim()); // Optional: trim to remove excess spaces
			}

			if (!recordFields.isEmpty() && recordFields.stream().anyMatch(field -> !field.isEmpty())) {
				records.add(recordFields); // Only add if it has meaningful content
			}

		}

		return records;
	}

}
