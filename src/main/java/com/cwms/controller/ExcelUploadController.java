package com.cwms.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.IsoContainer;
import com.cwms.entities.Party;
import com.cwms.entities.Port;
import com.cwms.entities.Vessel;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.IsoContainerRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.PortRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VesselRepository;

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

	@Value("${file.igmFormat}")
	private String filePath;

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

//	@PostMapping("/upload-Igm")
//	public ResponseEntity<?> handleFileUploadParty(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam("user") String user,@RequestParam("finYear") String finYear, @RequestParam("file") MultipartFile file) throws ParseException {
//		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
//			Sheet sheet = workbook.getSheetAt(0);
//			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
//
//			// Get headers from the first row
//			Row headerRow = sheet.getRow(0);
//			Map<String, Integer> headerMap = new HashMap<>();
//			for (Cell cell : headerRow) {
//				headerMap.put(cell.getStringCellValue(), cell.getColumnIndex());
//			}
//
//			// Iterate through the remaining rows
//			
//			Map<String,Object> result = new HashMap<>();
//
//			Row row = sheet.getRow(1);
//			if (row != null) {
//
//				String igmNo = getCellValue(row, headerMap.get("IGM No"), evaluator);
//				String igmDate = getCellValue(row, headerMap.get("IGM Date"), evaluator);
//				Date igmDate1 = parseExcelDate(igmDate);
//				String vesselBerthingDate = getCellValue(row, headerMap.get("Vessel Berthing Date"), evaluator);
//				Date vesselBerthingDate1 = parseExcelDate(vesselBerthingDate);
//				String vessel = getCellValue(row, headerMap.get("Vessel"), evaluator);
//				String voyage = getCellValue(row, headerMap.get("Voyage"), evaluator);
//				String viaNo = getCellValue(row, headerMap.get("Via No"), evaluator);
//				String port = getCellValue(row, headerMap.get("Port"), evaluator);
//				String sl = getCellValue(row, headerMap.get("Shipping Line"), evaluator);
//				String sa = getCellValue(row, headerMap.get("Shipping Agent"), evaluator);
//
//			
//				CFIgm igm = new CFIgm();
//				
//				Boolean isExist = cfigmrepo.isDataExist(cid, bid, igmNo);
//				
//				if(isExist) {
//					igm = cfigmrepo.getDataByIgmNo2(cid, bid, igmNo);
//				}
//				else {
//					String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05060", "2024");
//
//					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));
//
//					int nextNumericNextID1 = lastNextNumericId1 + 1;
//
//					String HoldNextIdD1 = String.format("CIAR%06d", nextNumericNextID1);
//
//					Port portName = portrepo.getPortByPortCode(cid, bid, port);
//
//					if (portName == null) {
//						return new ResponseEntity<>("Port not found in port master.", HttpStatus.CONFLICT);
//					}
//					
//
//					Party shippingLine = partyrepo.getDataByCustomerCode(cid, bid, sl);
//
//					if (shippingLine == null) {
//						return new ResponseEntity<>("Shipping line not found in party master.", HttpStatus.CONFLICT);
//					}
//
//					Party shippingAgent = partyrepo.getDataByCustomerCode(cid, bid, sa);
//
//					if (shippingAgent == null) {
//						return new ResponseEntity<>("Shipping agent not found in party master.", HttpStatus.CONFLICT);
//					}
//					
//					Vessel vesselName = vesselrepo.getDataByVesselName(cid, bid, vessel);
//					
//					if(vesselName == null) {
//						Vessel vessel1 = new Vessel();
//						String holdId = processnextidrepo.findAuditTrail(cid, bid, "P03202", "2239");
//
//						int lastNextNumericId = Integer.parseInt(holdId.substring(1));
//
//						int nextNumericNextID = lastNextNumericId + 1;
//						String HoldNextIdD = String.format("V%05d", nextNumericNextID);
//						vessel1.setCompanyId(cid);
//						vessel1.setVesselId(HoldNextIdD);
//						vessel1.setBranchId(bid);
//						vessel1.setVesselName(vessel);
//						vessel1.setStatus("A");
//						vessel1.setApprovedBy(user);
//						vessel1.setApprovedDate(new Date());
//						
//						vesselrepo.save(vessel1);
//						processnextidrepo.updateAuditTrail(cid, bid, "P03202", HoldNextIdD, "2239");
//						
//						igm.setVesselId(HoldNextIdD);
//					}
//					else {
//						igm.setVesselId(vesselName.getVesselId());
//					}
//
//					igm.setFinYear(finYear);
//					igm.setCompanyId(cid);
//					igm.setIgmTransId(HoldNextIdD1);
//					igm.setIgmNo(igmNo);
//					igm.setVoyageNo(voyage);
//					igm.setViaNo(viaNo);
//					igm.setIgmDate(igmDate1);
//					igm.setVesselEta(vesselBerthingDate1);
//					igm.setBranchId(bid);
//					igm.setPort(port);
//					igm.setStatus('A');
//					igm.setCreatedBy(user);
//					igm.setCreatedDate(new Date());
//					igm.setApprovedBy(user);
//					igm.setApprovedDate(new Date());
//					igm.setShippingLine(shippingLine.getPartyId());
//					igm.setShippingAgent(shippingAgent.getPartyId());
//					igm.setProfitcentreId("N00002");
//					igm.setDocDate(new Date());
//					cfigmrepo.save(igm);
//
//					processnextidrepo.updateAuditTrail(cid, bid, "P05060", HoldNextIdD1, "2024");
//				}
//				
//				
//				
//				Row headerRow1 = sheet.getRow(2);
//				Map<String, Integer> headerMap1 = new HashMap<>();
//				for (Cell cell : headerRow1) {
//					headerMap1.put(cell.getStringCellValue(), cell.getColumnIndex());
//				}
//				
//				for(int i = 3;i<=sheet.getLastRowNum();i++) {
//					Row r = sheet.getRow(i);
//					
//					if(r != null) {
//						String srNo = getCellValue(r, headerMap1.get("Sr No"), evaluator);
//						String containerNo = getCellValue(r, headerMap1.get("Container No"), evaluator);
//						String iso = getCellValue(r, headerMap1.get("ISO"), evaluator);
//						String cargoWt = getCellValue(r, headerMap1.get("Cargo Weight(KGS)"), evaluator);
//						String packages = getCellValue(r, headerMap1.get("Packages"), evaluator);
//						String typeofPackages = getCellValue(r, headerMap1.get("Type Of Packages"), evaluator);
//						String sealNo = getCellValue(r, headerMap1.get("Seal No"), evaluator);
//						String itemNo = getCellValue(r, headerMap1.get("Item No"), evaluator);
//						String blNo = getCellValue(r, headerMap1.get("BL Number"), evaluator);
//						String blDate = getCellValue(r, headerMap1.get("BL Date"), evaluator);
//						Date blDate1 = parseExcelDate(blDate);
//						String status = getCellValue(r, headerMap1.get("Status"), evaluator);
//						String cargoDesc = getCellValue(r, headerMap1.get("Cargo Description"), evaluator);
//						String importerName = getCellValue(r, headerMap1.get("Importer Name"), evaluator);
//						String address1 = getCellValue(r, headerMap1.get("Address1"), evaluator);
//						String address2 = getCellValue(r, headerMap1.get("Address2"), evaluator);
//						String address3 = getCellValue(r, headerMap1.get("Address3"), evaluator);
//						String origin = getCellValue(r, headerMap1.get("Origin"), evaluator);
//						
//						
//                        if(itemNo != null && !itemNo.isEmpty()) {
//    						Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, itemNo, igmNo);
//
//    						if (exist1) {
//    							
//    							Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, igm.getIgmTransId(), igmNo, itemNo);
//    							
//    							if (crg == null) {
//    								return new ResponseEntity<>("Item data not found.", HttpStatus.BAD_REQUEST);
//    							}
//    							
//    							Boolean isExist2 = cfigmcnrepo.isExistContainer(cid, bid, igm.getIgmTransId(), igmNo,
//    									itemNo, containerNo);
//
//    							if (isExist2) {
//    								return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
//    							}
//    							
//    							Cfigmcn cn = new Cfigmcn();
//    							
//    							String holdId3 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");
//
//    							int lastNextNumericId3 = Integer.parseInt(holdId3.substring(1));
//
//    							int nextNumericNextID3 = lastNextNumericId3 + 1;
//
//    							String HoldNextIdD3 = String.format("C%09d", nextNumericNextID3);
//    							
//    							IsoContainer isoData = isoContainerRepository.getDataByIsoCode(cid, iso);
//    							
//    							if(isoData == null) {
//    								return new ResponseEntity<>("ISO data not found", HttpStatus.BAD_REQUEST);
//    							}
//
//    							cn.setContainerTransId(HoldNextIdD3);
//    							cn.setCompanyId(cid);
//    							cn.setBranchId(bid);
//    							cn.setStatus('A');
//    							cn.setCreatedBy(user);
//    							cn.setCreatedDate(new Date());
//    							cn.setApprovedBy(user);
//    							cn.setApprovedDate(new Date());
//    							cn.setContainerNo(containerNo);
//    							cn.setNoOfPackages(Integer.parseInt(packages));
//    							cn.setCustomsSealNo(sealNo);
//    							cn.setContainerStatus(status);
//    							cn.setIgmTransId(igm.getIgmTransId());
//    							cn.setIgmNo(igmNo);
//    							cn.setIgmLineNo(itemNo);
//    							cn.setProfitcentreId("N00002");
//    							cn.setIso(iso);
//    							cn.setFinYear(finYear);
//    							cn.setContainerSize(isoData.getContainerSize());
//    							cn.setContainerType(isoData.getContainerType());
//    							cn.setContainerWeight(isoData.getTareWeight());
//    							cn.setGrossWt(isoData.getTareWeight().add(crg.getGrossWeight()));
//    							cfigmcnrepo.save(cn);
//    							
//    							processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD3, "2024");
//    							
//    							crg.setNoOfPackages(crg.getNoOfPackages().add(new BigDecimal(packages).setScale(3, RoundingMode.HALF_UP)));
//    							cfigmcrgrepo.save(crg);
//    						}
//    						else {
//    							
//    							Boolean exist = cfigmcrgrepo.isExistRecord(cid, bid, blNo);
//
//    							if (exist) {
//    								return new ResponseEntity<>("Duplicate BL No", HttpStatus.BAD_REQUEST);
//    							}
//
//    							
//    							Cfigmcrg crg = new Cfigmcrg();
//    							String holdId2 = processnextidrepo.findAuditTrail(cid, bid, "P05061", "2024");
//
//    							int lastNextNumericId2 = Integer.parseInt(holdId2.substring(4));
//
//    							int nextNumericNextID2 = lastNextNumericId2 + 1;
//
//    							String HoldNextIdD2 = String.format("ICR%07d", nextNumericNextID2);
//
//    							crg.setIgmCrgTransId(HoldNextIdD2);
//    							crg.setCompanyId(cid);
//    							crg.setProfitcentreId("N00002");
//    							crg.setBranchId(bid);
//    							crg.setIgmNo(igm.getIgmNo());
//    							crg.setIgmTransId(igm.getIgmTransId());
//    							crg.setCreatedBy(user);
//    							crg.setCreatedDate(new Date());
//    							crg.setViaNo(igm.getViaNo());
//    							crg.setApprovedBy(user);
//    							crg.setApprovedDate(new Date());
//    							crg.setStatus("A");
//    							if (cargoWt != null && !cargoWt.isEmpty()) {
//    							    crg.setGrossWeight(new BigDecimal(cargoWt).setScale(3, RoundingMode.HALF_UP));
//    							} else {
//    							    // Handle the case where cargoWt is null, e.g., set to zero or log an error
//    							    crg.setGrossWeight(BigDecimal.ZERO); // or any appropriate default value
//    							}
//
//    							crg.setTypeOfPackage(typeofPackages);
//    							crg.setIgmLineNo(itemNo);
//    							crg.setBlNo(blNo);
//    							crg.setBlDate(blDate1);
//    							crg.setCommodityDescription(cargoDesc);
//    							crg.setImporterName(importerName);
//    							crg.setImporterAddress1(address1);
//    							crg.setImporterAddress2(address2);
//    							crg.setImporterAddress3(address3);
//    							crg.setOrigin(origin);
//    							crg.setFinYear(finYear);
//    							crg.setNoOfPackages(BigDecimal.ZERO.add(new BigDecimal(packages).setScale(3, RoundingMode.HALF_UP)));
//
//    							cfigmcrgrepo.save(crg);
//    							processnextidrepo.updateAuditTrail(cid, bid, "P05061", HoldNextIdD2, "2024");
//    							
//    							
//    							Boolean isExist3 = cfigmcnrepo.isExistContainer(cid, bid, igm.getIgmTransId(), igmNo,
//    									itemNo, containerNo);
//
//    							if (isExist3) {
//    								return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
//    							}
//    							
//    							Cfigmcn cn = new Cfigmcn();
//    							
//    							String holdId3 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");
//
//    							int lastNextNumericId3 = Integer.parseInt(holdId3.substring(1));
//
//    							int nextNumericNextID3 = lastNextNumericId3 + 1;
//
//    							String HoldNextIdD3 = String.format("C%09d", nextNumericNextID3);
//    							
//    							IsoContainer isoData = isoContainerRepository.getDataByIsoCode(cid, iso);
//    							
//    							if(isoData == null) {
//    								return new ResponseEntity<>("ISO data not found", HttpStatus.BAD_REQUEST);
//    							}
//
//    							cn.setContainerTransId(HoldNextIdD3);
//    							cn.setCompanyId(cid);
//    							cn.setBranchId(bid);
//    							cn.setStatus('A');
//    							cn.setCreatedBy(user);
//    							cn.setCreatedDate(new Date());
//    							cn.setApprovedBy(user);
//    							cn.setApprovedDate(new Date());
//    							cn.setContainerNo(containerNo);
//    							cn.setNoOfPackages(Integer.parseInt(packages));
//    							cn.setCustomsSealNo(sealNo);
//    							cn.setContainerStatus(status);
//    							cn.setIgmTransId(igm.getIgmTransId());
//    							cn.setIgmNo(igmNo);
//    							cn.setIgmLineNo(itemNo);
//    							cn.setProfitcentreId("N00002");
//    							cn.setIso(iso);
//    							cn.setFinYear(finYear);
//    							cn.setContainerSize(isoData.getContainerSize());
//    							cn.setContainerType(isoData.getContainerType());
//    							cn.setContainerWeight(isoData.getTareWeight());
//    							cn.setGrossWt(isoData.getTareWeight().add(crg.getGrossWeight()));
//    							cfigmcnrepo.save(cn);
//    							
//    							processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD3, "2024");
//    						}
//                        }
//					}
//				}
//				
//				
//				
//			}
//
//			return ResponseEntity.ok("File uploaded and processed successfully");
//		} catch (IOException e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the file");
//		}
//	}

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

				Boolean isExist = cfigmrepo.isDataExist(cid, bid, igmNo);

				if (isExist) {
					igm = cfigmrepo.getDataByIgmNo2(cid, bid, igmNo);
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
						
						if(srNo != null && !srNo.isEmpty()) {
							if (containerNo == null || containerNo.isEmpty()) {
								String mess = " - Container No is required.";
								check = true;
								list.add(mess);
							} else {
								if (containerNo.length() > 11) {
									String mess = "For Container no " + containerNo + " - Container No length should not exceed 11 characters.";
									list.add(mess);
									check = true;

								}
							}
							
							if (iso == null || iso.isEmpty()) {
							    String mess =  "For Container no " + containerNo + " - ISO is required.";
							    list.add(mess);
							    check = true;
							} else {
							    if (iso.length() > 4) {
							        String mess = "For Container no " + containerNo + " - ISO length should not exceed 4 characters.";
							        list.add(mess);
							        check = true;
							    }
							}

							// Cargo Weight (KGS) validation
							if (cargoWt == null || cargoWt.isEmpty()) {
							    String mess =  "For Container no " + containerNo + " - Cargo Weight is required.";
							    list.add(mess);
							    check = true;
							} else {
							    try {
							        double weight = Double.parseDouble(cargoWt);
							        if (weight <= 0) {
							            String mess =  "For Container no " + containerNo + " - Cargo Weight should be a positive number.";
							            list.add(mess);
							            check = true;
							        }
							        if(cargoWt.length() > 8) {
							        	 String mess = "For Container no " + containerNo + " - Cargo Wt length should not exceed 8 characters.";
									        list.add(mess);
									        check = true;
							        }
							    } catch (NumberFormatException e) {
							        String mess =  "For Container no " + containerNo +  " - Invalid number format.";
							        list.add(mess);
							        check = true;
							    }
							}

							// Packages validation
							if (packages == null || packages.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Packages is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (packages.length() > 10) {
								        String mess = "For Container no " + containerNo + " - Packages length should not exceed 10 characters.";
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
							        String mess = "For Container no " + containerNo + " - Type Of package length should not exceed 6 characters.";
							        list.add(mess);
							        check = true;
							    }
							}

							// Seal No validation
							if (sealNo == null || sealNo.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Seal No is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (sealNo.length() > 15) {
								        String mess = "For Container no " + containerNo + " - ISO length should not exceed 15 characters.";
								        list.add(mess);
								        check = true;
								    }
								}

							// Item No validation
							if (itemNo == null || itemNo.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Item No is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (itemNo.length() > 7) {
								        String mess = "For Container no " + containerNo + " - Item no length should not exceed 7 characters.";
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
							    if (blNo.length() > 20) {  // Assuming 20 as max length for BL Number
							        String mess = "For Container no " + containerNo + " - BL Number length should not exceed 20 characters.";
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
							}
							 else {
								    if (!"FCL".equals(status) && !"LCL".equals(status)) {
								        String mess = "For Container no " + containerNo + " - The status must be either FCL or LCL.";
								        list.add(mess);
								        check = true;
								    }
								}

							// Cargo Description validation
							if (cargoDesc == null || cargoDesc.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Cargo Description is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (cargoDesc.length() > 250) {
								        String mess = "For Container no " + containerNo + " - Cargo desc length should not exceed 250 characters.";
								        list.add(mess);
								        check = true;
								    }
								}

							// Importer Name validation
							if (importerName == null || importerName.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Importer Name is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (importerName.length() > 100) {
								        String mess = "For Container no " + containerNo + " - Importer name length should not exceed 100 characters.";
								        list.add(mess);
								        check = true;
								    }
								}

							// Address1 validation
							if (address1 == null || address1.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Address1 is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (address1.length() > 250) {
								        String mess = "For Container no " + containerNo + " - Address1 length should not exceed 250 characters.";
								        list.add(mess);
								        check = true;
								    }
								}

							
							

							// Origin validation
							if (origin == null || origin.isEmpty()) {
							    String mess = "For Container no " + containerNo + " - Origin is required.";
							    list.add(mess);
							    check = true;
							}
							 else {
								    if (origin.length() > 50) {
								        String mess = "For Container no " + containerNo + " - Origin length should not exceed 50 characters.";
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

}
