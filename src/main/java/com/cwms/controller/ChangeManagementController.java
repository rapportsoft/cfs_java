package com.cwms.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.TicketInfo;
import com.cwms.entities.TicketInfoDtl;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.TicketInfoDtlRepository;
import com.cwms.repository.TicketInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/changeMangement")
@CrossOrigin("*")
public class ChangeManagementController {

	@Autowired
	private TicketInfoRepository ticketInforepo;

	@Autowired
	private TicketInfoDtlRepository ticketInfoDtlRepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Value("${file.upload.ticket}")
	private String filePath;

	@GetMapping("/getAssigneeData")
	public ResponseEntity<?> getAssigneeData(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> data = ticketInfoDtlRepo.getAssigneeData(cid, bid);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@GetMapping("/getApproverData")
	public ResponseEntity<?> getApproverData(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> data = ticketInfoDtlRepo.getApproverData(cid, bid);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@GetMapping("/getFollowersData")
	public ResponseEntity<?> getFollowersData(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> data = ticketInfoDtlRepo.getFollowers(cid, bid);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@PostMapping(value = "/saveTicket", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("status") String status,
			@RequestPart("ticketHdr") String ticketHdrJson, // JSON as a string
			@RequestPart("ticketDtl") String ticketDtlJson, // JSON as a string
			@RequestParam(value = "selectedFiles", required = false) List<MultipartFile> selectedFiles // Allow null
	) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		// Parse JSON data from strings
		TicketInfo ticketHdr = mapper.readValue(ticketHdrJson, TicketInfo.class);
		TicketInfoDtl ticketDtl = mapper.readValue(ticketDtlJson, TicketInfoDtl.class);

		// Validate parsed data
		if (ticketHdr == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket header data is invalid");
		}
		if (ticketDtl == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket detail data is invalid");
		}

		if (ticketHdr.getTicketNo() == null || ticketHdr.getTicketNo().isEmpty()) {
			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05116", "2024");

			String pre = holdId1.substring(0, 3);

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format(pre + "%07d", nextNumericNextID1);

			ticketHdr.setCompanyId(cid);
			ticketHdr.setBranchId(bid);
			ticketHdr.setStatus("N");
			ticketHdr.setCreatedBy(user);
			ticketHdr.setCreatedDate(new Date());
			ticketHdr.setTicketStatus(status);
			ticketHdr.setTicketNo(HoldNextIdD1);

			ticketInforepo.save(ticketHdr);

			processnextidrepo.updateAuditTrail(cid, bid, "P05116", HoldNextIdD1, "2024");
		}

		int sr = ticketInfoDtlRepo.totalSrNo(cid, bid, ticketHdr.getTicketNo());

		ticketDtl.setCompanyId(cid);
		ticketDtl.setBranchId(bid);
		ticketDtl.setStatus("A");
		ticketDtl.setCreatedBy(user);
		ticketDtl.setCreatedDate(new Date());
		ticketDtl.setTicketStatus(status);
		ticketDtl.setTicketNo(ticketHdr.getTicketNo());
		ticketDtl.setSrNo(sr + 1);
		ticketDtl.setMessageFrom(user);
		ticketDtl.setApprovedBy(user);
		ticketDtl.setApprovedDate(new Date());

		List<String> savedFilePaths = new ArrayList<>();

		if (selectedFiles != null && !selectedFiles.isEmpty()) {
			File uploadDir = new File(filePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs(); // Create the directory if it doesn't exist
			}

			for (MultipartFile file : selectedFiles) {
				String originalFileName = file.getOriginalFilename();
				if (originalFileName == null || originalFileName.isEmpty()) {
					continue;
				}

				String fileExtension = "";
				int lastDotIndex = originalFileName.lastIndexOf(".");
				if (lastDotIndex != -1) {
					fileExtension = originalFileName.substring(lastDotIndex);
				}

				String baseFileName = originalFileName.substring(0, lastDotIndex);
				String newFileName = originalFileName;
				File destinationFile = new File(filePath, newFileName);

				// Ensure the filename is unique
				int count = 1;
				while (destinationFile.exists()) {
					newFileName = baseFileName + "_" + count + fileExtension;
					destinationFile = new File(filePath, newFileName);
					count++;
				}

				try {
					// Save file to the specified directory
					Path targetPath = destinationFile.toPath();
					Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

					// Store the saved file path
					savedFilePaths.add(destinationFile.getAbsolutePath());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// Store saved file paths in ticketDtl, separated by "~"
			ticketDtl.setAttachedFiles(String.join("~", savedFilePaths));
		} else {
			ticketDtl.setAttachedFiles("");
		}

		ticketInfoDtlRepo.save(ticketDtl);

		Map<String, Object> result = new HashMap<>();

		result.put("ticketHdr", ticketHdr);

		List<TicketInfoDtl> dtlData = ticketInfoDtlRepo.getSavedData(cid, bid, ticketHdr.getTicketNo());

		if (dtlData.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket data not found.");

		}

		dtlData.stream().forEach(dtl -> {
			String attachedFiles = dtl.getAttachedFiles(); // e.g., "C:/uploads/file1.pdf~C:/uploads/file2.jpg"

			if (attachedFiles != null && !attachedFiles.isEmpty()) {
				String[] filePaths = attachedFiles.split("~");
				Map<String, byte[]> fileContents = new HashMap<>();

				for (String path : filePaths) {
					File file = new File(path);
					if (file.exists()) {
						try {
							byte[] fileBytes = Files.readAllBytes(file.toPath());
							fileContents.put(file.getName(), fileBytes);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				dtl.setFileByteData(fileContents); // Assuming you have a field `fileByteData` in TicketInfoDtl
			}
		});

		result.put("ticketDtl", dtlData);

		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/getTicketData")
	public ResponseEntity<?> getTicketData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("id") String id){
		List<TicketInfo> data = ticketInforepo.getData(cid, bid, id);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);

	}
	
	@GetMapping("/getTicketDataById")
	public ResponseEntity<?> getTicketDataById(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("id") String id){
		TicketInfo data = ticketInforepo.getDataByTicketId(cid, bid, id);
		
		if(data == null) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		Map<String, Object> result = new HashMap<>();

		result.put("ticketHdr", data);

		List<TicketInfoDtl> dtlData = ticketInfoDtlRepo.getSavedData(cid, bid, id);

		if (dtlData.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket data not found.");

		}

		dtlData.stream().forEach(dtl -> {
			String attachedFiles = dtl.getAttachedFiles(); // e.g., "C:/uploads/file1.pdf~C:/uploads/file2.jpg"

			if (attachedFiles != null && !attachedFiles.isEmpty()) {
				String[] filePaths = attachedFiles.split("~");
				Map<String, byte[]> fileContents = new HashMap<>();

				for (String path : filePaths) {
					File file = new File(path);
					if (file.exists()) {
						try {
							byte[] fileBytes = Files.readAllBytes(file.toPath());
							fileContents.put(file.getName(), fileBytes);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				dtl.setFileByteData(fileContents); // Assuming you have a field `fileByteData` in TicketInfoDtl
			}
		});

		result.put("ticketDtl", dtlData);

		return ResponseEntity.ok(result);

	}
	
	
	@GetMapping("/getPendingTicketData")
	public ResponseEntity<?> getPendingTicketData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("id") String id){
		List<TicketInfo> data = ticketInforepo.getPendingData(cid, bid, id);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);

	}
	
	
	@PostMapping(value = "/approveTicket", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> approveTicket(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("status") String status,
			@RequestPart("ticketHdr") String ticketHdrJson, // JSON as a string
			@RequestPart("ticketDtl") String ticketDtlJson, // JSON as a string
			@RequestParam(value = "selectedFiles", required = false) List<MultipartFile> selectedFiles // Allow null
	) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		// Parse JSON data from strings
		TicketInfo ticketHdr = mapper.readValue(ticketHdrJson, TicketInfo.class);
		TicketInfoDtl ticketDtl = mapper.readValue(ticketDtlJson, TicketInfoDtl.class);

		// Validate parsed data
		if (ticketHdr == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket header data is invalid");
		}
		if (ticketDtl == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket detail data is invalid");
		}

		TicketInfo existingData = ticketInforepo.getDataByTicketId(cid, bid, ticketHdr.getTicketNo());
		
		if(existingData != null) {
			
			if("N".equals(existingData.getStatus())) {
				existingData.setStatus("A");
				existingData.setApprovedBy(user);
				existingData.setTicketStatus(status);
				existingData.setApprovedDate(new Date());
				
				ticketInforepo.save(existingData);
			}
			
		}
		

		int sr = ticketInfoDtlRepo.totalSrNo(cid, bid, ticketHdr.getTicketNo());

		ticketDtl.setCompanyId(cid);
		ticketDtl.setBranchId(bid);
		ticketDtl.setStatus("A");
		ticketDtl.setCreatedBy(user);
		ticketDtl.setCreatedDate(new Date());
		ticketDtl.setApprovedBy(user);
		ticketDtl.setApprovedDate(new Date());
		ticketDtl.setTicketStatus(status);
		ticketDtl.setTicketNo(ticketHdr.getTicketNo());
		ticketDtl.setSrNo(sr + 1);
		ticketDtl.setMessageFrom(user);

		List<String> savedFilePaths = new ArrayList<>();

		if (selectedFiles != null && !selectedFiles.isEmpty()) {
			File uploadDir = new File(filePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs(); // Create the directory if it doesn't exist
			}

			for (MultipartFile file : selectedFiles) {
				String originalFileName = file.getOriginalFilename();
				if (originalFileName == null || originalFileName.isEmpty()) {
					continue;
				}

				String fileExtension = "";
				int lastDotIndex = originalFileName.lastIndexOf(".");
				if (lastDotIndex != -1) {
					fileExtension = originalFileName.substring(lastDotIndex);
				}

				String baseFileName = originalFileName.substring(0, lastDotIndex);
				String newFileName = originalFileName;
				File destinationFile = new File(filePath, newFileName);

				// Ensure the filename is unique
				int count = 1;
				while (destinationFile.exists()) {
					newFileName = baseFileName + "_" + count + fileExtension;
					destinationFile = new File(filePath, newFileName);
					count++;
				}

				try {
					// Save file to the specified directory
					Path targetPath = destinationFile.toPath();
					Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

					// Store the saved file path
					savedFilePaths.add(destinationFile.getAbsolutePath());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// Store saved file paths in ticketDtl, separated by "~"
			ticketDtl.setAttachedFiles(String.join("~", savedFilePaths));
		} else {
			ticketDtl.setAttachedFiles("");
		}

		ticketInfoDtlRepo.save(ticketDtl);

		Map<String, Object> result = new HashMap<>();

		result.put("ticketHdr", existingData);

		List<TicketInfoDtl> dtlData = ticketInfoDtlRepo.getSavedData(cid, bid, ticketHdr.getTicketNo());

		if (dtlData.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket data not found.");

		}

		dtlData.stream().forEach(dtl -> {
			String attachedFiles = dtl.getAttachedFiles(); // e.g., "C:/uploads/file1.pdf~C:/uploads/file2.jpg"

			if (attachedFiles != null && !attachedFiles.isEmpty()) {
				String[] filePaths = attachedFiles.split("~");
				Map<String, byte[]> fileContents = new HashMap<>();

				for (String path : filePaths) {
					File file = new File(path);
					if (file.exists()) {
						try {
							byte[] fileBytes = Files.readAllBytes(file.toPath());
							fileContents.put(file.getName(), fileBytes);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				dtl.setFileByteData(fileContents); // Assuming you have a field `fileByteData` in TicketInfoDtl
			}
		});

		result.put("ticketDtl", dtlData);

		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/getAllTickets")
	public ResponseEntity<?> getAllTickets(@RequestParam("cid") String cid,@RequestParam("bid") String bid){
		List<TicketInfo> data = ticketInforepo.getAllTickets(cid, bid);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);

	}
	
	
	@PostMapping(value = "/resolveTicket", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> resolveTicket(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("status") String status,
			@RequestPart("ticketHdr") String ticketHdrJson, // JSON as a string
			@RequestPart("ticketDtl") String ticketDtlJson, // JSON as a string
			@RequestParam(value = "selectedFiles", required = false) List<MultipartFile> selectedFiles // Allow null
	) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();

		// Parse JSON data from strings
		TicketInfo ticketHdr = mapper.readValue(ticketHdrJson, TicketInfo.class);
		TicketInfoDtl ticketDtl = mapper.readValue(ticketDtlJson, TicketInfoDtl.class);

		// Validate parsed data
		if (ticketHdr == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket header data is invalid");
		}
		if (ticketDtl == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket detail data is invalid");
		}

		TicketInfo existingData = ticketInforepo.getDataByTicketId(cid, bid, ticketHdr.getTicketNo());
		
		if(existingData != null) {
			
			if("Solved".equals(existingData.getTicketStatus())) {
			
				existingData.setEditedBy(user);
				existingData.setEditedDate(new Date());
				
				ticketInforepo.save(existingData);
			}
			else {
				existingData.setEditedBy(user);
				existingData.setTicketStatus(status);
				existingData.setEditedDate(new Date());
				
				ticketInforepo.save(existingData);
			}
			
		}
		

		int sr = ticketInfoDtlRepo.totalSrNo(cid, bid, ticketHdr.getTicketNo());

		ticketDtl.setCompanyId(cid);
		ticketDtl.setBranchId(bid);
		ticketDtl.setStatus("A");
		ticketDtl.setCreatedBy(user);
		ticketDtl.setCreatedDate(new Date());
		ticketDtl.setApprovedBy(user);
		ticketDtl.setApprovedDate(new Date());
		ticketDtl.setTicketStatus(status);
		ticketDtl.setTicketNo(ticketHdr.getTicketNo());
		ticketDtl.setSrNo(sr + 1);
		ticketDtl.setMessageFrom(user);

		List<String> savedFilePaths = new ArrayList<>();

		if (selectedFiles != null && !selectedFiles.isEmpty()) {
			File uploadDir = new File(filePath);
			if (!uploadDir.exists()) {
				uploadDir.mkdirs(); // Create the directory if it doesn't exist
			}

			for (MultipartFile file : selectedFiles) {
				String originalFileName = file.getOriginalFilename();
				if (originalFileName == null || originalFileName.isEmpty()) {
					continue;
				}

				String fileExtension = "";
				int lastDotIndex = originalFileName.lastIndexOf(".");
				if (lastDotIndex != -1) {
					fileExtension = originalFileName.substring(lastDotIndex);
				}

				String baseFileName = originalFileName.substring(0, lastDotIndex);
				String newFileName = originalFileName;
				File destinationFile = new File(filePath, newFileName);

				// Ensure the filename is unique
				int count = 1;
				while (destinationFile.exists()) {
					newFileName = baseFileName + "_" + count + fileExtension;
					destinationFile = new File(filePath, newFileName);
					count++;
				}

				try {
					// Save file to the specified directory
					Path targetPath = destinationFile.toPath();
					Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

					// Store the saved file path
					savedFilePaths.add(destinationFile.getAbsolutePath());

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			// Store saved file paths in ticketDtl, separated by "~"
			ticketDtl.setAttachedFiles(String.join("~", savedFilePaths));
		} else {
			ticketDtl.setAttachedFiles("");
		}

		ticketInfoDtlRepo.save(ticketDtl);

		Map<String, Object> result = new HashMap<>();

		result.put("ticketHdr", existingData);

		List<TicketInfoDtl> dtlData = ticketInfoDtlRepo.getSavedData(cid, bid, ticketHdr.getTicketNo());

		if (dtlData.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket data not found.");

		}

		dtlData.stream().forEach(dtl -> {
			String attachedFiles = dtl.getAttachedFiles(); // e.g., "C:/uploads/file1.pdf~C:/uploads/file2.jpg"

			if (attachedFiles != null && !attachedFiles.isEmpty()) {
				String[] filePaths = attachedFiles.split("~");
				Map<String, byte[]> fileContents = new HashMap<>();

				for (String path : filePaths) {
					File file = new File(path);
					if (file.exists()) {
						try {
							byte[] fileBytes = Files.readAllBytes(file.toPath());
							fileContents.put(file.getName(), fileBytes);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				dtl.setFileByteData(fileContents); // Assuming you have a field `fileByteData` in TicketInfoDtl
			}
		});

		result.put("ticketDtl", dtlData);

		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/getResolvedTickets")
	public ResponseEntity<?> getResolvedTickets(@RequestParam("cid") String cid,@RequestParam("bid") String bid){
		List<TicketInfo> data = ticketInforepo.getResolvedTickets(cid, bid);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(data,HttpStatus.OK);

	}
}
