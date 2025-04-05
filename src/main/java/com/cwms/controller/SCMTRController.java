package com.cwms.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.MpcinDto;
import com.cwms.entities.ScmtrJobTrack;
import com.cwms.entities.SplitTallyPkg;
import com.cwms.entities.jsonentities.CargoContainer;
import com.cwms.entities.jsonentities.CargoContainer1;
import com.cwms.entities.jsonentities.CargoDetails;
import com.cwms.entities.jsonentities.CargoDetails1;
import com.cwms.entities.jsonentities.CargoItnry;
import com.cwms.entities.jsonentities.Declaration;
import com.cwms.entities.jsonentities.HeaderField;
import com.cwms.entities.jsonentities.Location;
import com.cwms.entities.jsonentities.Master;
import com.cwms.entities.jsonentities.Master1;
import com.cwms.entities.jsonentities.RootJson;
import com.cwms.entities.jsonentities.RootJson1;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.SCMTRRepo;
import com.cwms.repository.ScmtrJobTrackRepo;
import com.cwms.repository.SplitTallyPkgRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin("*")
@RequestMapping("/scmtr")
@RestController
public class SCMTRController {

	@Autowired
	private SCMTRRepo scmtrRepo;

	@Autowired
	private ExportEntryRepo entryRepo;

	@Autowired
	private SplitTallyPkgRepo splittallypkgrepo;

	@Value("${file.json.path}")
	public String json_FilePath;

	@Autowired
	private ScmtrJobTrackRepo scmtrjobtrackrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Value("${MESSAGEID}")
	public String messageId;

	@Value("${SENDERID}")
	public String senderId;

	@Value("${RECEIVERID}")
	public String receiverId;

	@Value("${INDICATOR}")
	public String indicator;

	@Value("${REPORTINGEVENT}")
	public String reportingEvent;

	@Value("${VERSIONNO}")
	public String versionNo;

	@Value("${CFSCODE}")
	public String cfsCode;

	@Value("${CFSPAN}")
	public String cfsPan;

	@Value("${CFSAPAN}")
	public String cfsaPan;

	@Value("${CFSType}")
	public String cfsType;

	@Value("${CFSTypeASR}")
	public String cfsTypeAsr;

	@Value("${TERMINAL}")
	public String terminal;

	@Value("${PKGTYPE}")
	public String pkgType;

	@Value("${JSONPKGQUC}")
	public String jsonPkgQuc;

	@Value("${JSONPKGTYPE}")
	public String jsonPkgType;

	@Value("${friendlyPendrive}")
	private String CERT_ALIAS;

	@GetMapping("/getStuffingBulkSearchData")
	public ResponseEntity<?> getStuffingBulkSearchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate) {

		try {

			List<Object[]> data = scmtrRepo.getDataByStuffTallyDate(cid, bid, startDate, endDate);

			List<Object[]> data1 = scmtrRepo.getDataByStuffTallyDate1(cid, bid, startDate, endDate);

			if (data.isEmpty() && data1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> finalData = new HashMap<>();
			finalData.put("mpcinData", data);
			finalData.put("tallyData", data1);

			return new ResponseEntity<>(finalData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveSingleMpcinNo")
	public ResponseEntity<?> saveSingleMpcinNo(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate,
			@RequestBody MpcinDto data) {

		try {

			System.out.println("data " + data);

			ExportSbEntry entry = entryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, data.getSbNo(),
					data.getSbTrasnId());

			if (entry != null) {
				entry.setMpcin(data.getMpcinNo());
				entry.setMpcinReadDate(new Date());
				entryRepo.save(entry);
			} else {
				return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);

			}

			List<Object[]> result = scmtrRepo.getDataByStuffTallyDate(cid, bid, startDate, endDate);

			List<Object[]> result1 = scmtrRepo.getDataByStuffTallyDate1(cid, bid, startDate, endDate);

			if (result.isEmpty() && result1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> finalData = new HashMap<>();
			finalData.put("mpcinData", result);
			finalData.put("tallyData", result1);

			return new ResponseEntity<>(finalData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveMpcinNo")
	public ResponseEntity<?> saveMpcinNo(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate,
			@RequestBody List<MpcinDto> data) {

		try {

			data.stream().forEach(c -> {
				ExportSbEntry entry = entryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, c.getSbNo(),
						c.getSbTrasnId());

				if (entry != null) {
					entry.setMpcin(c.getMpcinNo());
					entry.setMpcinReadDate(new Date());
					entryRepo.save(entry);
				}
			});

			List<Object[]> result = scmtrRepo.getDataByStuffTallyDate(cid, bid, startDate, endDate);

			List<Object[]> result1 = scmtrRepo.getDataByStuffTallyDate1(cid, bid, startDate, endDate);

			if (result.isEmpty() && result1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> finalData = new HashMap<>();
			finalData.put("mpcinData", result);
			finalData.put("tallyData", result1);

			return new ResponseEntity<>(finalData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	@PostMapping("/downloadJson")
	public ResponseEntity<?> generateAndDownloadJson(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Object[] sbData) {
		FileWriter fileWriter = null;
		try {

			ScmtrJobTrack job = scmtrjobtrackrepo.getDataById(cid, bid, String.valueOf(sbData[1]),
					String.valueOf(sbData[0]), String.valueOf(sbData[16]), String.valueOf(sbData[3]), "SF");

			if (job == null) {

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05134", "2024");

				int nextNumericNextID1 = Integer.parseInt(holdId1) + 1;

				String HoldNextIdD1 = String.valueOf(nextNumericNextID1);

				ScmtrJobTrack newJob = new ScmtrJobTrack();
				newJob.setCompanyId(cid);
				newJob.setBranchId(bid);
				newJob.setStatus("A");
				newJob.setCreatedBy(user);
				newJob.setCreatedDate(new Date());
				newJob.setSbTransId(String.valueOf(sbData[1]));
				newJob.setSbNo(String.valueOf(sbData[0]));
				newJob.setStuffTallyId(String.valueOf(sbData[16]));
				newJob.setContainerNo(String.valueOf(sbData[3]));
				newJob.setJobNo(HoldNextIdD1);
				newJob.setJobDate(new Date());
				newJob.setMessageId("SF");

				File directory = new File(json_FilePath);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				RootJson rootJson = createJsonData(cid, bid, sbData, newJob);
				// 2. Create file
				File file = new File(json_FilePath + "SF_" + HoldNextIdD1 + "_" + String.valueOf(sbData[0]) + ".json");

				// 3. Convert object to JSON string
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(rootJson);

				String updatedJson = processFile(jsonString);
				// 4. Write using FileWriter (with proper exception handling)
				fileWriter = new FileWriter(file);
				fileWriter.write(updatedJson);
				fileWriter.flush(); // Ensure all data is written

				System.out.println("jsonString " + updatedJson);

				newJob.setMessageString(updatedJson);
				newJob.setJsonFilePath(file.getAbsolutePath());
				scmtrjobtrackrepo.save(newJob);
				processnextidrepo.updateAuditTrail(cid, bid, "P05134", HoldNextIdD1, "2024");

				int updateData = scmtrRepo.updateStuffTallyData(cid, bid, newJob.getStuffTallyId(),
						newJob.getContainerNo(), HoldNextIdD1, newJob.getJobDate(),
						Integer.parseInt(String.valueOf(sbData[12])), Integer.parseInt(String.valueOf(sbData[13])));

				int updateSbCrgData = scmtrRepo.updateSbCrgData(cid, bid, newJob.getSbNo(), newJob.getSbTransId(),
						String.valueOf(sbData[11]));
				// 5. Return file for download
				byte[] fileBytes = Files.readAllBytes(file.toPath());

				Map<String, Object> fileData = new HashMap<>();
				fileData.put("fileName", file.getName());
				fileData.put("fileContent", Base64.getEncoder().encodeToString(fileBytes)); // Convert file to
																							// Base64

				return ResponseEntity.ok(fileData);
			} else {

				File file = new File(job.getJsonFilePath());
				if (file.exists()) {
					try {

						byte[] fileBytes = Files.readAllBytes(file.toPath());

						Map<String, Object> fileData = new HashMap<>();
						fileData.put("fileName", file.getName());
						fileData.put("fileContent", Base64.getEncoder().encodeToString(fileBytes)); // Convert file to
																									// Base64

						System.out.println("fileData " + fileData.get("fileName"));

						return ResponseEntity.ok(fileData);
					} catch (IOException e) {
						e.printStackTrace();

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
					}
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File doesn't exist!!");
				}

			}

		} catch (Exception e) {
			// Handle specific exceptions
			if (e instanceof JsonProcessingException) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(("JSON processing error: " + e.getMessage()).getBytes());
			} else if (e instanceof IOException) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(("File writing error: " + e.getMessage()).getBytes());
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(("Unexpected error: " + e.getMessage()).getBytes());
			}
		} finally {
			// 6. Always close FileWriter
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					// Log closing error if needed
					System.err.println("Error closing FileWriter: " + e.getMessage());
				}
			}
		}

	}

	public RootJson createJsonData(String cid, String bid, Object[] data, ScmtrJobTrack newJob) {
		// Create HeaderField

		Date currentDate = new Date();
		Date sbDate = new Date(String.valueOf(data[2]));
		Date tallyDate = new Date(String.valueOf(data[5]));

		String pkg = String.valueOf(data[11]);
		String pkgquc = "";

		String[] arr = pkgType.split(",");
		String[] arr2 = jsonPkgQuc.split(",");
		String[] arr3 = jsonPkgType.split(",");

		for (int i = 0; i < arr.length; i++) {
			String s = arr[i];
			if (pkg.equalsIgnoreCase(s)) {
				pkg = arr3[i];
				pkgquc = arr2[i];
				break;
			}
		}

		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simple1 = new SimpleDateFormat("HH:mm");

		String formatCurrentDate = simple.format(currentDate);

		String formatCurrentTime = simple1.format(currentDate);

		String documentDate = simple.format(sbDate);
		String terminalOperator = getTerminalDesc(terminal, String.valueOf(data[8]));
		String clpDate = simple.format(tallyDate) + "T" + simple1.format(tallyDate);

		HeaderField headerField = new HeaderField();
		headerField.setDate(formatCurrentDate);
		headerField.setIndicator("T");
		headerField.setMessageID(messageId);
		headerField.setReceiverID(receiverId);
		headerField.setReportingEvent("SF");
		headerField.setSenderID(senderId);
		headerField.setSequenceOrControlNumber(Integer.parseInt(newJob.getJobNo()));
		headerField.setTime("T" + formatCurrentTime);
		headerField.setVersionNo(versionNo);

		// Create CargoDetails
		CargoDetails cargoDetails = new CargoDetails();
		cargoDetails.setCargoSequenceNo(1);
		cargoDetails.setDocumentDate(documentDate);
		cargoDetails.setDocumentNumber(newJob.getSbNo());
		cargoDetails.setDocumentSite(receiverId);
		cargoDetails.setDocumentType("ESB");
		cargoDetails.setMcinPcin(String.valueOf(data[6]));
		cargoDetails.setMessageType("F");
		cargoDetails.setPackUQC(pkgquc);
		cargoDetails.setPackageType(pkg);
		cargoDetails.setPacketsFrom(Integer.parseInt(String.valueOf(data[12])));
		cargoDetails.setPacketsTo(Integer.parseInt(String.valueOf(data[13])));
		cargoDetails.setQuantity(Integer.parseInt(String.valueOf(data[10])));
		cargoDetails.setShipmentLoadStatus("F");
		// Create CargoContainer
		CargoContainer cargoContainer = new CargoContainer();
		cargoContainer.setCargoDetails(Collections.singletonList(cargoDetails));
		cargoContainer.setEquipmentID(String.valueOf(data[3]));
		cargoContainer.setEquipmentLoadStatus("FCL");
		cargoContainer.setEquipmentQUC(pkgquc);
		cargoContainer.setEquipmentQuantity(Integer.parseInt(String.valueOf(data[10])));
		cargoContainer.setEquipmentSealNumber(String.valueOf(data[17]));
		cargoContainer.setEquipmentSealType("OTH");
		cargoContainer.setEquipmentSequenceNo(1);
		cargoContainer.setEquipmentSize(String.valueOf(data[4]));
		cargoContainer.setEquipmentStatus("94");
		cargoContainer.setEquipmentType("CN");
		cargoContainer.setEventDate(clpDate);
		cargoContainer.setFinalDestinationLocation(terminalOperator);
		cargoContainer.setMessageType("F");
		// Create Declaration
		Declaration declaration = new Declaration();
		declaration.setJobDate(simple.format(newJob.getJobDate()));
		declaration.setJobNo(Integer.parseInt(newJob.getJobNo()));
		declaration.setMessageType("F");
		declaration.setPortOfReporting(receiverId);
		declaration.setReportingEvent(reportingEvent);
		// Create Location
		Location location = new Location();
		location.setAuthorisedPersonPAN(cfsaPan);
		location.setReportingLocationCode(cfsCode);
		location.setReportingLocationName(cfsCode);
		location.setReportingPartyCode(cfsPan);
		location.setReportingPartyType(cfsType);
		// Create Master
		Master master = new Master();
		master.setCargoContainer(Collections.singletonList(cargoContainer));
		master.setDeclaration(declaration);
		master.setLocation(location);
		// Create RootJson
		RootJson rootJson = new RootJson();
		rootJson.setHeaderField(headerField);
		rootJson.setMaster(master);

		System.out.println("rootJson " + rootJson);

		return rootJson;
	}

	public static String getTerminalDesc(String terminal, String terminalCode) {
		// Split by '^' to get individual terminal pairs
		String[] terminalPairs = terminal.split("\\^");

		for (String pair : terminalPairs) {
			String[] parts = pair.split(",");
			if (parts.length == 2 && parts[0].equalsIgnoreCase(terminalCode)) {
				return parts[1]; // Return the terminal description
			}
		}

		return ""; // Default if not matched
	}

	@Transactional
	@PostMapping("/updateSfStatus")
	public ResponseEntity<?> updateSfStatus(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("job") String job, @RequestParam("stuffTally") String stufftally,
			@RequestParam("con") String con, @RequestParam("status") String selectedStatus,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate) {

		try {

			String statusString = "S".equals(selectedStatus) ? "SUCCESS" : "FAIL";

			int updateScmtr = scmtrRepo.updateStuffTallyData1(cid, bid, stufftally, con, selectedStatus, statusString);

			int updateSfStatus = scmtrjobtrackrepo.updateSFStatus(cid, bid, job, stufftally, selectedStatus,
					statusString,"SF");

			List<Object[]> result = scmtrRepo.getDataByStuffTallyDate(cid, bid, startDate, endDate);

			List<Object[]> result1 = scmtrRepo.getDataByStuffTallyDate1(cid, bid, startDate, endDate);

			if (result.isEmpty() && result1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> finalData = new HashMap<>();
			finalData.put("mpcinData", result);
			finalData.put("tallyData", result1);

			return new ResponseEntity<>(finalData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@Transactional
//	@PostMapping("/updateMultipleSfStatus")
//	public ResponseEntity<?> updateMultipleSfStatus(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
//			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
//			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate, @RequestBody List<Object[]> data){
//		
//		try {
//			
//			if(data.isEmpty()) {
//				return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
//
//			}
//			
//			data.stream().forEach(c->{
//				String statusString =  "S".equals(String.valueOf(c[14])) ? "SUCCESS" : "FAIL";
//				
//				int updateScmtr = scmtrRepo.updateStuffTallyData1(cid, bid, String.valueOf(c[16]), String.valueOf(c[3]),
//						String.valueOf(c[14]), statusString);
//				
//				int updateSfStatus = scmtrjobtrackrepo.updateSFStatus(cid, bid, String.valueOf(c[19]), String.valueOf(c[16]),
//						String.valueOf(c[14]), statusString);
//			});
//			
//
//			
//			List<Object[]> result = scmtrRepo.getDataByStuffTallyDate(cid, bid, startDate, endDate);
//
//			List<Object[]> result1 = scmtrRepo.getDataByStuffTallyDate1(cid, bid, startDate, endDate);
//
//			if (result.isEmpty() && result1.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			Map<String, Object> finalData = new HashMap<>();
//			finalData.put("mpcinData", result);
//			finalData.put("tallyData", result1);
//
//			return new ResponseEntity<>(finalData, HttpStatus.OK);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@Transactional
	@PostMapping("/updateMultipleSfStatus")
	public ResponseEntity<?> updateMultipleSfStatus(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate,
			@RequestBody List<Map<String, Object>> data) {

		try {
			// 1️⃣ Validate Data
			if (data == null || data.isEmpty()) {
				return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
			}

			// 2️⃣ Process Each Record
			for (Map<String, Object> c : data) {
				String statusString = "S".equals(String.valueOf(c.get("14"))) ? "SUCCESS" : "FAIL";

				// Update Scmtr
				int updateScmtr = scmtrRepo.updateStuffTallyData1(cid, bid, String.valueOf(c.get("16")), // STW ID
						String.valueOf(c.get("3")), // Container No
						String.valueOf(c.get("14")), // Status
						statusString);

				// Update SF Status
				int updateSfStatus = scmtrjobtrackrepo.updateSFStatus(cid, bid, String.valueOf(c.get("19")), // Some ID
						String.valueOf(c.get("16")), // STW ID
						String.valueOf(c.get("14")), // Status
						statusString,"SF");
			}

			// 3️⃣ Fetch Updated Data
			List<Object[]> result = scmtrRepo.getDataByStuffTallyDate(cid, bid, startDate, endDate);
			List<Object[]> result1 = scmtrRepo.getDataByStuffTallyDate1(cid, bid, startDate, endDate);

			// 4️⃣ Validate If Data Exists
			if (result.isEmpty() && result1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// 5️⃣ Prepare Response
			Map<String, Object> finalData = new HashMap<>();
			finalData.put("mpcinData", result);
			finalData.put("tallyData", result1);

			return new ResponseEntity<>(finalData, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getSplitPkg")
	public ResponseEntity<?> getSplitPkg(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("tallyId") String tallyId, @RequestParam("sbNo") String sbNo,
			@RequestParam("sbTransId") String sbTransId, @RequestParam("containerNo") String container) {

		try {

			List<Object[]> data = splittallypkgrepo.getData(cid, bid, tallyId, sbTransId, sbNo, container);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveSplitPkg")
	public ResponseEntity<?> saveSplitPkg(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody List<SplitTallyPkg> data) {

		try {

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);

			}

			AtomicInteger sr = new AtomicInteger(1);

			data.stream().forEach(c -> {
				c.setCompanyId(cid);
				c.setBranchId(bid);
				c.setStatus("A");
				c.setCreatedBy(user);
				c.setCreatedDate(new Date());
				c.setSrNo(sr.get());

				splittallypkgrepo.save(c);

				sr.set(sr.get() + 1);
			});

			List<Object[]> result = splittallypkgrepo.getData(cid, bid, data.get(0).getStuffTallyId(),
					data.get(0).getSbTransId(), data.get(0).getSbNo(), data.get(0).getContainerNo());

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String processFile(String jsonData) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

			// Convert the JSON data to a formatted (pretty-printed) string
			JsonNode jsonNode = objectMapper.readTree(jsonData);

			String prettyJsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);

			// Add the digital signature to the JSON data
			String updatedJson = addSignatureToJson(prettyJsonData);

			return updatedJson;
		} catch (Exception e) {
			e.printStackTrace();

			return jsonData;
		}
	}

	public String addSignatureToJson(String json) {
		try {
			KeyStore keystore = KeyStore.getInstance("Windows-MY");
			keystore.load(null, null);

			List<String> aliasList = Collections.list(keystore.aliases());

			System.out.println("Listing available certificates in the keystore: \n" + aliasList);

			X509Certificate certificate = null;
			PrivateKey privateKey = null;
//            CERT_ALIAS = CN=VIPIN PITALIYA

			// Iterate through the aliases using a for loop
			for (String alias : aliasList) {

				certificate = (X509Certificate) keystore.getCertificate(alias);
				if (certificate != null) {

					String subjectDN = certificate.getSubjectDN().getName();

					if (subjectDN.contains(CERT_ALIAS)) {

						privateKey = (PrivateKey) keystore.getKey(alias, null);
						break;
					}
				} else {

				}
			}

//	        PrivateKey privateKey = (PrivateKey) keystore.getKey(CERT_ALIAS, digiSignPassword.toCharArray());

			// Create the signature
			Signature signatureInstance = Signature.getInstance("SHA256withRSA");
			signatureInstance.initSign(privateKey);

			// Hash the JSON and sign
			signatureInstance.update(json.getBytes(StandardCharsets.UTF_8)); // Ensure UTF-8 encoding
			byte[] signatureBytes = signatureInstance.sign();

			// Base64 encode the signature and certificate
			String signature = Base64.getEncoder().encodeToString(signatureBytes);
			String encodedCertificate = Base64.getEncoder().encodeToString(certificate.getEncoded());

			// Parse the original JSON string into a JsonNode
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(json);

			// Ensure the original JSON structure is preserved
			if (jsonNode instanceof ObjectNode) {
				ObjectNode updatedJsonNode = (ObjectNode) jsonNode; // Use the original root node (if it's ObjectNode)

				// Create the digSign node with signature information
				ObjectNode digSignNode = objectMapper.createObjectNode();
				digSignNode.put("startSignature", signature); // Add the signature field
				digSignNode.put("startCertificate", encodedCertificate); // Add the certificate field
				digSignNode.put("signerVersion", "1.0"); // Add the signer version

				// Add the digSign node to the original JSON structure
				updatedJsonNode.set("digSign", digSignNode);

				// Use pretty-printing for the output JSON string
				String formattedJson = objectMapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(updatedJsonNode);

				// Return the updated, pretty-printed JSON string with the signature
				return formattedJson;
			} else {

				// throw new IllegalArgumentException("JSON is not an ObjectNode, unable to add
				// signature.");

				return json;
			}
		} catch (Exception e) {
			// If an error occurs, save the failure entry to DigiSignTrack with the error
			// message

			e.printStackTrace();

			return json;
		}
	}

	// ASR Bulk

	@GetMapping("/getASRBulkSearchData")
	public ResponseEntity<?> getASRBulkSearchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate) {

		try {

			List<Object[]> data1 = scmtrRepo.getDataByStuffTallyDateForASR(cid, bid, startDate, endDate);

			if (data1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data1, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	@PostMapping("/downloadASRJson")
	public ResponseEntity<?> generateAndDownloadASRJson(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestBody Object[] sbData) {
		FileWriter fileWriter = null;
		try {

			ScmtrJobTrack job = scmtrjobtrackrepo.getDataById(cid, bid, String.valueOf(sbData[1]),
					String.valueOf(sbData[0]), String.valueOf(sbData[16]), String.valueOf(sbData[3]), "ASR");

			if (job == null) {

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05135", "2024");

				int nextNumericNextID1 = Integer.parseInt(holdId1) + 1;

				String HoldNextIdD1 = String.valueOf(nextNumericNextID1);

				ScmtrJobTrack newJob = new ScmtrJobTrack();
				newJob.setCompanyId(cid);
				newJob.setBranchId(bid);
				newJob.setStatus("A");
				newJob.setCreatedBy(user);
				newJob.setCreatedDate(new Date());
				newJob.setSbTransId(String.valueOf(sbData[1]));
				newJob.setSbNo(String.valueOf(sbData[0]));
				newJob.setStuffTallyId(String.valueOf(sbData[16]));
				newJob.setContainerNo(String.valueOf(sbData[3]));
				newJob.setJobNo(HoldNextIdD1);
				newJob.setJobDate(new Date());
				newJob.setMessageId("ASR");

				File directory = new File(json_FilePath);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				RootJson1 rootJson = createASRJsonData(cid, bid, sbData, newJob);
				// 2. Create file
				File file = new File(json_FilePath + "ASR_" + HoldNextIdD1 + "_" + String.valueOf(sbData[0]) + ".json");

				// 3. Convert object to JSON string
				ObjectMapper objectMapper = new ObjectMapper();
				String jsonString = objectMapper.writeValueAsString(rootJson);

				String updatedJson = processFile(jsonString);
				// 4. Write using FileWriter (with proper exception handling)
				fileWriter = new FileWriter(file);
				fileWriter.write(updatedJson);
				fileWriter.flush(); // Ensure all data is written

				System.out.println("jsonString " + updatedJson);

				newJob.setMessageString(updatedJson);
				newJob.setJsonFilePath(file.getAbsolutePath());
				scmtrjobtrackrepo.save(newJob);
				processnextidrepo.updateAuditTrail(cid, bid, "P05135", HoldNextIdD1, "2024");

				int updateData = scmtrRepo.updateStuffTallyData2(cid, bid, newJob.getStuffTallyId(),
						newJob.getContainerNo(), HoldNextIdD1, newJob.getJobDate());

//				int updateSbCrgData = scmtrRepo.updateSbCrgData(cid, bid, newJob.getSbNo(), newJob.getSbTransId(),
//						String.valueOf(sbData[11]));
				// 5. Return file for download
				byte[] fileBytes = Files.readAllBytes(file.toPath());

				Map<String, Object> fileData = new HashMap<>();
				fileData.put("fileName", file.getName());
				fileData.put("fileContent", Base64.getEncoder().encodeToString(fileBytes)); // Convert file to
																							// Base64

				return ResponseEntity.ok(fileData);
			} else {

				File file = new File(job.getJsonFilePath());
				if (file.exists()) {
					try {

						byte[] fileBytes = Files.readAllBytes(file.toPath());

						Map<String, Object> fileData = new HashMap<>();
						fileData.put("fileName", file.getName());
						fileData.put("fileContent", Base64.getEncoder().encodeToString(fileBytes)); // Convert file to
																									// Base64

						System.out.println("fileData " + fileData.get("fileName"));

						return ResponseEntity.ok(fileData);
					} catch (IOException e) {
						e.printStackTrace();

						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
					}
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File doesn't exist!!");
				}

			}

		} catch (Exception e) {
			// Handle specific exceptions
			if (e instanceof JsonProcessingException) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(("JSON processing error: " + e.getMessage()).getBytes());
			} else if (e instanceof IOException) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(("File writing error: " + e.getMessage()).getBytes());
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(("Unexpected error: " + e.getMessage()).getBytes());
			}
		} finally {
			// 6. Always close FileWriter
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					// Log closing error if needed
					System.err.println("Error closing FileWriter: " + e.getMessage());
				}
			}
		}

	}

	public RootJson1 createASRJsonData(String cid, String bid, Object[] data, ScmtrJobTrack newJob) {
		// Create HeaderField

		Date currentDate = new Date();
		Date sbDate = new Date(String.valueOf(data[2]));
		Date tallyDate = new Date(String.valueOf(data[5]));

		String pkg = String.valueOf(data[11]);
		String pkgquc = "";

		String[] arr = pkgType.split(",");
		String[] arr2 = jsonPkgQuc.split(",");
		String[] arr3 = jsonPkgType.split(",");

		for (int i = 0; i < arr.length; i++) {
			String s = arr[i];
			if (pkg.equalsIgnoreCase(s)) {
				pkg = arr3[i];
				pkgquc = arr2[i];
				break;
			}
		}

		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat simple1 = new SimpleDateFormat("HH:mm");

		String formatCurrentDate = simple.format(currentDate);

		String formatCurrentTime = simple1.format(currentDate);

		String documentDate = simple.format(sbDate);
		String terminalOperator = getTerminalDesc(terminal, String.valueOf(data[8]));
		String clpDate = simple.format(tallyDate) + "T" + simple1.format(tallyDate);

		HeaderField headerField = new HeaderField();
		headerField.setDate(formatCurrentDate);
		headerField.setIndicator("T");
		headerField.setMessageID(messageId);
		headerField.setReceiverID(receiverId);
		headerField.setReportingEvent("ASR");
		headerField.setSenderID(senderId);
		headerField.setSequenceOrControlNumber(Integer.parseInt(newJob.getJobNo()));
		headerField.setTime("T" + formatCurrentTime);
		headerField.setVersionNo(versionNo);

		// Create CargoContainer
		CargoContainer1 cargoContainer = new CargoContainer1();
		cargoContainer.setEquipmentID(String.valueOf(data[3]));
		cargoContainer.setEquipmentLoadStatus("FCL");
		cargoContainer.setEquipmentPkg("20");
		cargoContainer.setEquipmentQUC(pkgquc);
		cargoContainer.setEquipmentQuantity(Integer.parseInt(String.valueOf(data[10])));
		cargoContainer.setEquipmentSealNumber(String.valueOf(data[17]));
		cargoContainer.setEquipmentSealType("OTH");
		cargoContainer.setEquipmentSequenceNo(1);
		cargoContainer.setEquipmentSize(String.valueOf(data[4]));
		cargoContainer.setEquipmentStatus("127");
		cargoContainer.setEquipmentType("CN");
		cargoContainer.setEventDate(clpDate);
		cargoContainer.setFinalDestinationLocation(terminalOperator);
		cargoContainer.setMessageType("F");
		
		CargoItnry cargoItnry = new CargoItnry();
		cargoItnry.setDocumentType("ESB");
		cargoItnry.setModeOfTrnsprt("1");
		cargoItnry.setNxtPrtOfCallCdd(receiverId);
		cargoItnry.setNxtPrtOfCallName(terminal);
		cargoItnry.setPrtOfCallCdd(receiverId);
		cargoItnry.setPrtOfCallName(terminal);
		cargoItnry.setPrtOfCallSeqNmbr(1);
		
		// Create CargoDetails
		CargoDetails1 cargoDetails = new CargoDetails1();
		cargoDetails.setCargoContainer(Collections.singletonList(cargoContainer));
		cargoDetails.setCargoItnry(Collections.singletonList(cargoItnry));
		cargoDetails.setCargoSequenceNo(1);
		cargoDetails.setDocumentDate(documentDate);
		cargoDetails.setDocumentNo(newJob.getSbNo());
		cargoDetails.setDocumentSite(receiverId);
		cargoDetails.setDocumentType("SB");
		cargoDetails.setMcinPcin(String.valueOf(data[6]));
		cargoDetails.setMessageType("F");
		cargoDetails.setPackageType("P");
		cargoDetails.setQuantity(Integer.parseInt(String.valueOf(data[10])));
		cargoDetails.setShipmentLoadStatus("F");

		// Create Declaration
		Declaration declaration = new Declaration();
		declaration.setJobDate(simple.format(newJob.getJobDate()));
		declaration.setJobNo(Integer.parseInt(newJob.getJobNo()));
		declaration.setMessageType("F");
		declaration.setPortOfReporting(receiverId);
		declaration.setReportingEvent("ASR");
		// Create Location
		Location location = new Location();
		location.setAuthorisedPersonPAN(cfsaPan);
		location.setReportingLocationCode(cfsCode);
		location.setReportingLocationName(cfsCode);
		location.setReportingPartyCode(cfsPan);
		location.setReportingPartyType(cfsTypeAsr);
		// Create Master
		Master1 master = new Master1();
		master.setCargoDetails(Collections.singletonList(cargoDetails));
		master.setDeclaration(declaration);
		master.setLocation(location);
		// Create RootJson
		RootJson1 rootJson = new RootJson1();
		rootJson.setHeaderField(headerField);
		rootJson.setMaster(master);

		System.out.println("rootJson " + rootJson);

		return rootJson;
	}
	
	
	@Transactional
	@PostMapping("/updateASRStatus")
	public ResponseEntity<?> updateASRStatus(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("job") String job, @RequestParam("stuffTally") String stufftally,
			@RequestParam("con") String con, @RequestParam("status") String selectedStatus,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate) {

		try {

			String statusString = "S".equals(selectedStatus) ? "SUCCESS" : "FAIL";

			int updateScmtr = scmtrRepo.updateStuffTallyData3(cid, bid, stufftally, con, selectedStatus, statusString);

			int updateSfStatus = scmtrjobtrackrepo.updateSFStatus(cid, bid, job, stufftally, selectedStatus,
					statusString,"ASR");

			List<Object[]> data1 = scmtrRepo.getDataByStuffTallyDateForASR(cid, bid, startDate, endDate);

			if (data1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data1, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Transactional
	@PostMapping("/updateMultipleASRStatus")
	public ResponseEntity<?> updateMultipleASRStatus(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date endDate,
			@RequestBody List<Object[]> data) {

		try {
			// 1️⃣ Validate Data
			if (data == null || data.isEmpty()) {
				return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
			}

			// 2️⃣ Process Each Record
			for (Object[] c : data) {
				String statusString = "S".equals(String.valueOf(c[14])) ? "SUCCESS" : "FAIL";

				// Update Scmtr
				int updateScmtr = scmtrRepo.updateStuffTallyData3(cid, bid, String.valueOf(c[16]), // STW ID
						String.valueOf(c[3]), // Container No
						String.valueOf(c[14]), // Status
						statusString);

				// Update SF Status
				int updateSfStatus = scmtrjobtrackrepo.updateSFStatus(cid, bid, String.valueOf(c[19]), // Some ID
						String.valueOf(c[16]), // STW ID
						String.valueOf(c[14]), // Status
						statusString,"ASR");
			}

			List<Object[]> data1 = scmtrRepo.getDataByStuffTallyDateForASR(cid, bid, startDate, endDate);

			if (data1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data1, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
