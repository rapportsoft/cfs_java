package com.cwms.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.IgmDocumentUpload;
import com.cwms.entities.IgmServiceDtl;
import com.cwms.entities.IgmServiceDtlDoc;
import com.cwms.repository.CFSTarrifServiceRepository;
import com.cwms.repository.CfExBondCrgRepository;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.IGMServiceDtlDocRepo;
import com.cwms.repository.IgmServiceDtlRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/waiver")
public class WaiverController {

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CFSTarrifServiceRepository cfstarrifservicerepo;

	@Autowired
	private IGMServiceDtlDocRepo igmServiceDtlDocrepo;

	@Autowired
	private IgmServiceDtlRepo igmservicedtlrepo;

	@Autowired
	private CfbondnocRepository cfbondnocrepo;

	@Autowired
	private CfExBondCrgRepository cfexbondcrgrepo;

	@Value("${file.igmServiceDocPath}")
	public String fileUploadPath;

	@GetMapping("getServices")
	public ResponseEntity<?> getAllServices(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> serviceList = cfstarrifservicerepo.getGeneralTarrifData3(cid, bid);
		if (serviceList.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(serviceList, HttpStatus.OK);
	}

	@GetMapping("/checkIGMAndLineNo")
	public ResponseEntity<?> getDataByIgmNoAndLineNo(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("lineNo") String lineNo) {
		Object data = cfigmcnrepo.getDataByIGMAndLineNo(cid, bid, igm, lineNo);

		if (data == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {

			Object[] data1 = (Object[]) data;

			List<Object[]> finalDocData = igmServiceDtlDocrepo.getDataByIgmDtls(cid, bid, igm, String.valueOf(data1[1]),
					lineNo);

			List<Object[]> servicData = igmservicedtlrepo.getDataByIgmDtls(cid, bid, String.valueOf(data1[1]), igm,
					lineNo);

			Map<String, Object> result = new HashMap<>();

			result.put("docData", finalDocData);
			result.put("igmData", data);
			result.put("servicData", servicData);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@Transactional
	@PostMapping("/saveData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmTransId") String igmTransId,
			@RequestParam("lineNo") String lineNo, @RequestParam("user") String user,
			@RequestParam(name = "files", required = false) MultipartFile[] files, @RequestPart("data") String dataJson)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = mapper.readValue(dataJson, new TypeReference<Map<String, Object>>() {
		});

		if (igmTransId == null || igmTransId.isEmpty()) {
			return new ResponseEntity<>("IGM data not found", HttpStatus.CONFLICT);

		}

		List<IgmServiceDtlDoc> docData = mapper.readValue(mapper.writeValueAsString(data.get("docData")),
				new TypeReference<List<IgmServiceDtlDoc>>() {
				});

		List<IgmServiceDtl> serviceData = mapper.readValue(mapper.writeValueAsString(data.get("serviceData")),
				new TypeReference<List<IgmServiceDtl>>() {
				});

		if (serviceData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		int countWithoutIgmTransId = (int) docData.stream()
				.filter(doc -> doc.getIgmTransId() == null || doc.getIgmTransId().isEmpty()).count();

		if (!docData.isEmpty() && files != null) {
			for (int i = 0; i < countWithoutIgmTransId; i++) {
				IgmServiceDtlDoc d = docData.get(i);
				MultipartFile file = files[i];
				if (file != null && !file.isEmpty()) {
					try {
						String fileName = file.getOriginalFilename();
						Path filePath = Paths.get(fileUploadPath, fileName);

						// Handle duplicate file names
						if (Files.exists(filePath)) {
							String extension = "";
							String baseName = fileName;
							int dotIndex = fileName.lastIndexOf(".");
							if (dotIndex > 0) {
								extension = fileName.substring(dotIndex);
								baseName = fileName.substring(0, dotIndex);
							}
							String newFileName = baseName + "_" + System.currentTimeMillis() + extension;
							filePath = Paths.get(fileUploadPath, newFileName);
						}

						Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

						// Set values in the entity
						int srNo = igmServiceDtlDocrepo.getLatestSrNo(cid, bid, igm, igmTransId, lineNo);
						d.setCompanyId(cid);
						d.setBranchId(bid);
						d.setStatus("A");
						d.setCreatedBy(user);
						d.setCreatedDate(new Date());
						d.setIgmNo(igm);
						d.setIgmTransId(igmTransId);
						d.setIgmLineNo(lineNo);
						d.setDocPath(String.valueOf(filePath));
						d.setSrNo(srNo + 1);

						igmServiceDtlDocrepo.save(d);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		List<Cfigmcn> conData = cfigmcnrepo.getHoldSearch1(cid, bid, igmTransId, igm, lineNo);

		if (conData.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.CONFLICT);
		}

		Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, igmTransId, igm, lineNo);

		if (crg == null) {
			return new ResponseEntity<>("Cargo data not found", HttpStatus.CONFLICT);
		}

		serviceData.stream().forEach(s -> {

			if (s.getServiceId() != null && !s.getServiceId().isEmpty()) {
				boolean isExistServiceId = igmservicedtlrepo.isExistTheServiceId(cid, bid, igmTransId, igm, lineNo,
						s.getServiceId());

				conData.stream().forEach(c -> {

					if (isExistServiceId) {

						IgmServiceDtl existing = igmservicedtlrepo.getDataByServiceId(cid, bid, igmTransId, igm, lineNo,
								c.getContainerNo(), s.getServiceId());

						if (existing == null) {
							IgmServiceDtl dtl = new IgmServiceDtl();

							dtl.setCompanyId(cid);
							dtl.setBranchId(bid);
							dtl.setStatus("A");
							dtl.setCreatedBy(user);
							dtl.setCreatedDate(new Date());
							dtl.setApprovedBy(user);
							dtl.setApprovedDate(new Date());
							dtl.setIgmTransId(igmTransId);
							dtl.setIgmNo(igm);
							dtl.setIgmLineNo(lineNo);
							dtl.setContainerNo(c.getContainerNo());
							dtl.setServiceId(s.getServiceId());
							dtl.setBeNo(crg.getBeNo());
							dtl.setBlNo(crg.getBlNo());
							dtl.setContainerSize(c.getContainerSize());
							dtl.setContainerType(c.getContainerType());
							dtl.setCfsTariffNo(s.getCfsTariffNo());
							dtl.setCfsAmndNo(s.getCfsAmndNo());
							dtl.setPercentage(s.getPercentage());
							dtl.setAmount(s.getAmount());
							dtl.setRemark(s.getRemark());
							dtl.setStatus("A");

							igmservicedtlrepo.save(dtl);
						} else {
							existing.setPercentage(s.getPercentage());
							existing.setAmount(s.getAmount());
							existing.setRemark(s.getRemark());

							igmservicedtlrepo.save(existing);
						}

					} else {

						IgmServiceDtl dtl = new IgmServiceDtl();

						dtl.setCompanyId(cid);
						dtl.setBranchId(bid);
						dtl.setStatus("A");
						dtl.setCreatedBy(user);
						dtl.setCreatedDate(new Date());
						dtl.setApprovedBy(user);
						dtl.setApprovedDate(new Date());
						dtl.setIgmTransId(igmTransId);
						dtl.setIgmNo(igm);
						dtl.setIgmLineNo(lineNo);
						dtl.setContainerNo(c.getContainerNo());
						dtl.setServiceId(s.getServiceId());
						dtl.setBeNo(crg.getBeNo());
						dtl.setBlNo(crg.getBlNo());
						dtl.setContainerSize(c.getContainerSize());
						dtl.setContainerType(c.getContainerType());
						dtl.setCfsTariffNo(s.getCfsTariffNo());
						dtl.setCfsAmndNo(s.getCfsAmndNo());
						dtl.setPercentage(s.getPercentage());
						dtl.setAmount(s.getAmount());
						dtl.setRemark(s.getRemark());
						dtl.setStatus("A");

						igmservicedtlrepo.save(dtl);
					}

				});
			}

		});

		List<Object[]> finalDocData = igmServiceDtlDocrepo.getDataByIgmDtls(cid, bid, igm, igmTransId, lineNo);

		List<Object[]> servicData = igmservicedtlrepo.getDataByIgmDtls(cid, bid, igmTransId, igm, lineNo);

		Map<String, Object> result = new HashMap<>();

		result.put("docData", finalDocData);
		result.put("servicData", servicData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/downloadFile")
	public ResponseEntity<?> downloadFile(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igmtrans") String igmtrans, @RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine, @RequestParam("sr") int sr) throws FileNotFoundException {

		String data = igmServiceDtlDocrepo.getDataBySrNo(cid, bid, igm, igmtrans, igmLine, sr);

		System.out.println("data " + data);

		if (data == null || data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.NOT_FOUND); // Change to NOT_FOUND for clarity
		}

		// Create a File object from the complete file path
		File file = new File(data);

		if (!file.exists() || !file.canRead()) {
			throw new FileNotFoundException("File not found: " + data);
		}

		// Get the filename from the File object
		String fileName = file.getName();
		if (fileName == null) {
			// Handle case where filename could not be extracted
			return new ResponseEntity<>("Filename could not be extracted", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Create a Resource from the File object
		Resource resource = new FileSystemResource(file);

		// Return the file as a response
		return ResponseEntity.ok()
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(resource);
	}

	// Bond Waiver

	@GetMapping("/searchBeforeWaiverData")
	public ResponseEntity<?> searchBeforeWaiverData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = cfbondnocrepo.getDataBeforeWaiver(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/searchExbondBeforeData")
	public ResponseEntity<?> searchExbondBeforeData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = cfexbondcrgrepo.getExBondBeNoForWaiver(cid, bid, val);

		if (data == null || data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/checkNocNo")
	public ResponseEntity<?> getDataByBoeNo(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("noc") String noc, @RequestParam("nocTransId") String nocTransId) {

		List<Object[]> finalDocData = igmServiceDtlDocrepo.getDataByNocDtls(cid, bid, noc, nocTransId);

		List<Object[]> servicData = igmservicedtlrepo.getDataByNOCDtls(cid, bid, nocTransId, noc);

		Map<String, Object> result = new HashMap<>();

		result.put("docData", finalDocData);
		result.put("servicData", servicData);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@Transactional
	@PostMapping("/saveNOCData")
	public ResponseEntity<?> saveNOCData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmTransId") String igmTransId,
			@RequestParam("lineNo") String lineNo, @RequestParam("user") String user,
			@RequestParam(name = "files", required = false) MultipartFile[] files, @RequestPart("data") String dataJson)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = mapper.readValue(dataJson, new TypeReference<Map<String, Object>>() {
		});

		if (igmTransId == null || igmTransId.isEmpty()) {
			return new ResponseEntity<>("IGM data not found", HttpStatus.CONFLICT);

		}

		List<IgmServiceDtlDoc> docData = mapper.readValue(mapper.writeValueAsString(data.get("docData")),
				new TypeReference<List<IgmServiceDtlDoc>>() {
				});

		List<IgmServiceDtl> serviceData = mapper.readValue(mapper.writeValueAsString(data.get("serviceData")),
				new TypeReference<List<IgmServiceDtl>>() {
				});

		if (serviceData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		int countWithoutIgmTransId = (int) docData.stream()
				.filter(doc -> doc.getIgmTransId() == null || doc.getIgmTransId().isEmpty()).count();

		if (!docData.isEmpty() && files != null) {
			for (int i = 0; i < countWithoutIgmTransId; i++) {
				IgmServiceDtlDoc d = docData.get(i);
				MultipartFile file = files[i];
				if (file != null && !file.isEmpty()) {
					try {
						String fileName = file.getOriginalFilename();
						Path filePath = Paths.get(fileUploadPath, fileName);

						// Handle duplicate file names
						if (Files.exists(filePath)) {
							String extension = "";
							String baseName = fileName;
							int dotIndex = fileName.lastIndexOf(".");
							if (dotIndex > 0) {
								extension = fileName.substring(dotIndex);
								baseName = fileName.substring(0, dotIndex);
							}
							String newFileName = baseName + "_" + System.currentTimeMillis() + extension;
							filePath = Paths.get(fileUploadPath, newFileName);
						}

						Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

						// Set values in the entity
						int srNo = igmServiceDtlDocrepo.getLatestSrNo(cid, bid, igm, igmTransId, lineNo);
						d.setCompanyId(cid);
						d.setBranchId(bid);
						d.setStatus("A");
						d.setCreatedBy(user);
						d.setCreatedDate(new Date());
						d.setIgmNo(igm);
						d.setIgmTransId(igmTransId);
						d.setIgmLineNo(lineNo);
						d.setDocPath(String.valueOf(filePath));
						d.setSrNo(srNo + 1);

						igmServiceDtlDocrepo.save(d);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		Cfbondnoc noc = cfbondnocrepo.getDataByNocAndTrans(cid, bid, igmTransId, igm);

		if (noc == null) {
			return new ResponseEntity<>("Noc data not found", HttpStatus.CONFLICT);
		}

		serviceData.stream().forEach(s -> {

			if (s.getServiceId() != null && !s.getServiceId().isEmpty()) {
				boolean isExistServiceId = igmservicedtlrepo.isExistTheServiceId(cid, bid, igmTransId, igm, lineNo,
						s.getServiceId());

				if (isExistServiceId) {

					IgmServiceDtl existing = igmservicedtlrepo.getDataByServiceId1(cid, bid, igmTransId, igm, lineNo, s.getServiceId());

					if (existing == null) {
						IgmServiceDtl dtl = new IgmServiceDtl();

						dtl.setCompanyId(cid);
						dtl.setBranchId(bid);
						dtl.setStatus("A");
						dtl.setCreatedBy(user);
						dtl.setCreatedDate(new Date());
						dtl.setApprovedBy(user);
						dtl.setApprovedDate(new Date());
						dtl.setIgmTransId(igmTransId);
						dtl.setIgmNo(igm);
						dtl.setIgmLineNo(lineNo);
						dtl.setContainerNo("");
						dtl.setServiceId(s.getServiceId());
						dtl.setBeNo(noc.getBoeNo());
						dtl.setBlNo("");
						dtl.setContainerSize("");
						dtl.setContainerType("");
						dtl.setCfsTariffNo(s.getCfsTariffNo());
						dtl.setCfsAmndNo(s.getCfsAmndNo());
						dtl.setPercentage(s.getPercentage());
						dtl.setAmount(s.getAmount());
						dtl.setRemark(s.getRemark());
						dtl.setStatus("A");

						igmservicedtlrepo.save(dtl);
					} else {
						existing.setPercentage(s.getPercentage());
						existing.setAmount(s.getAmount());
						existing.setRemark(s.getRemark());

						igmservicedtlrepo.save(existing);
					}

				} else {

					IgmServiceDtl dtl = new IgmServiceDtl();

					dtl.setCompanyId(cid);
					dtl.setBranchId(bid);
					dtl.setStatus("A");
					dtl.setCreatedBy(user);
					dtl.setCreatedDate(new Date());
					dtl.setApprovedBy(user);
					dtl.setApprovedDate(new Date());
					dtl.setIgmTransId(igmTransId);
					dtl.setIgmNo(igm);
					dtl.setIgmLineNo(lineNo);
					dtl.setContainerNo("");
					dtl.setServiceId(s.getServiceId());
					dtl.setBeNo(noc.getBoeNo());
					dtl.setBlNo("");
					dtl.setContainerSize("");
					dtl.setContainerType("");
					dtl.setCfsTariffNo(s.getCfsTariffNo());
					dtl.setCfsAmndNo(s.getCfsAmndNo());
					dtl.setPercentage(s.getPercentage());
					dtl.setAmount(s.getAmount());
					dtl.setRemark(s.getRemark());
					dtl.setStatus("A");

					igmservicedtlrepo.save(dtl);
				}

			}

		});

		List<Object[]> finalDocData = igmServiceDtlDocrepo.getDataByIgmDtls(cid, bid, igm, igmTransId, lineNo);

		List<Object[]> servicData = igmservicedtlrepo.getDataByIgmDtls(cid, bid, igmTransId, igm, lineNo);

		Map<String, Object> result = new HashMap<>();

		result.put("docData", finalDocData);
		result.put("servicData", servicData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@Transactional
	@PostMapping("/saveExbondData")
	public ResponseEntity<?> saveExbondData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmTransId") String igmTransId,
			@RequestParam("lineNo") String lineNo, @RequestParam("user") String user,
			@RequestParam(name = "files", required = false) MultipartFile[] files, @RequestPart("data") String dataJson)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> data = mapper.readValue(dataJson, new TypeReference<Map<String, Object>>() {
		});

		if (igmTransId == null || igmTransId.isEmpty()) {
			return new ResponseEntity<>("IGM data not found", HttpStatus.CONFLICT);

		}

		List<IgmServiceDtlDoc> docData = mapper.readValue(mapper.writeValueAsString(data.get("docData")),
				new TypeReference<List<IgmServiceDtlDoc>>() {
				});

		List<IgmServiceDtl> serviceData = mapper.readValue(mapper.writeValueAsString(data.get("serviceData")),
				new TypeReference<List<IgmServiceDtl>>() {
				});

		if (serviceData.isEmpty()) {
			return new ResponseEntity<>("Service data not found", HttpStatus.CONFLICT);
		}

		int countWithoutIgmTransId = (int) docData.stream()
				.filter(doc -> doc.getIgmTransId() == null || doc.getIgmTransId().isEmpty()).count();

		if (!docData.isEmpty() && files != null) {
			for (int i = 0; i < countWithoutIgmTransId; i++) {
				IgmServiceDtlDoc d = docData.get(i);
				MultipartFile file = files[i];
				if (file != null && !file.isEmpty()) {
					try {
						String fileName = file.getOriginalFilename();
						Path filePath = Paths.get(fileUploadPath, fileName);

						// Handle duplicate file names
						if (Files.exists(filePath)) {
							String extension = "";
							String baseName = fileName;
							int dotIndex = fileName.lastIndexOf(".");
							if (dotIndex > 0) {
								extension = fileName.substring(dotIndex);
								baseName = fileName.substring(0, dotIndex);
							}
							String newFileName = baseName + "_" + System.currentTimeMillis() + extension;
							filePath = Paths.get(fileUploadPath, newFileName);
						}

						Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

						// Set values in the entity
						int srNo = igmServiceDtlDocrepo.getLatestSrNo(cid, bid, igm, igmTransId, lineNo);
						d.setCompanyId(cid);
						d.setBranchId(bid);
						d.setStatus("A");
						d.setCreatedBy(user);
						d.setCreatedDate(new Date());
						d.setIgmNo(igm);
						d.setIgmTransId(igmTransId);
						d.setIgmLineNo(lineNo);
						d.setDocPath(String.valueOf(filePath));
						d.setSrNo(srNo + 1);

						igmServiceDtlDocrepo.save(d);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		CfExBondCrg noc = cfexbondcrgrepo.findExistingCfexbondCrg2(cid, bid, igmTransId, igm);

		if (noc == null) {
			return new ResponseEntity<>("Noc data not found", HttpStatus.CONFLICT);
		}

		serviceData.stream().forEach(s -> {

			if (s.getServiceId() != null && !s.getServiceId().isEmpty()) {
				boolean isExistServiceId = igmservicedtlrepo.isExistTheServiceId(cid, bid, igmTransId, igm, lineNo,
						s.getServiceId());

				if (isExistServiceId) {

					IgmServiceDtl existing = igmservicedtlrepo.getDataByServiceId1(cid, bid, igmTransId, igm, lineNo, s.getServiceId());

					if (existing == null) {
						IgmServiceDtl dtl = new IgmServiceDtl();

						dtl.setCompanyId(cid);
						dtl.setBranchId(bid);
						dtl.setStatus("A");
						dtl.setCreatedBy(user);
						dtl.setCreatedDate(new Date());
						dtl.setApprovedBy(user);
						dtl.setApprovedDate(new Date());
						dtl.setIgmTransId(igmTransId);
						dtl.setIgmNo(igm);
						dtl.setIgmLineNo(lineNo);
						dtl.setContainerNo("");
						dtl.setServiceId(s.getServiceId());
						dtl.setBeNo(noc.getExBondBeNo());
						dtl.setBlNo("");
						dtl.setContainerSize("");
						dtl.setContainerType("");
						dtl.setCfsTariffNo(s.getCfsTariffNo());
						dtl.setCfsAmndNo(s.getCfsAmndNo());
						dtl.setPercentage(s.getPercentage());
						dtl.setAmount(s.getAmount());
						dtl.setRemark(s.getRemark());
						dtl.setStatus("A");

						igmservicedtlrepo.save(dtl);
					} else {
						existing.setPercentage(s.getPercentage());
						existing.setAmount(s.getAmount());
						existing.setRemark(s.getRemark());

						igmservicedtlrepo.save(existing);
					}

				} else {

					IgmServiceDtl dtl = new IgmServiceDtl();

					dtl.setCompanyId(cid);
					dtl.setBranchId(bid);
					dtl.setStatus("A");
					dtl.setCreatedBy(user);
					dtl.setCreatedDate(new Date());
					dtl.setApprovedBy(user);
					dtl.setApprovedDate(new Date());
					dtl.setIgmTransId(igmTransId);
					dtl.setIgmNo(igm);
					dtl.setIgmLineNo(lineNo);
					dtl.setContainerNo("");
					dtl.setServiceId(s.getServiceId());
					dtl.setBeNo(noc.getExBondBeNo());
					dtl.setBlNo("");
					dtl.setContainerSize("");
					dtl.setContainerType("");
					dtl.setCfsTariffNo(s.getCfsTariffNo());
					dtl.setCfsAmndNo(s.getCfsAmndNo());
					dtl.setPercentage(s.getPercentage());
					dtl.setAmount(s.getAmount());
					dtl.setRemark(s.getRemark());
					dtl.setStatus("A");

					igmservicedtlrepo.save(dtl);
				}

			}

		});

		List<Object[]> finalDocData = igmServiceDtlDocrepo.getDataByIgmDtls(cid, bid, igm, igmTransId, lineNo);

		List<Object[]> servicData = igmservicedtlrepo.getDataByIgmDtls(cid, bid, igmTransId, igm, lineNo);

		Map<String, Object> result = new HashMap<>();

		result.put("docData", finalDocData);
		result.put("servicData", servicData);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
