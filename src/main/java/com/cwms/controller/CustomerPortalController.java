package com.cwms.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Branch;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;
import com.cwms.entities.PartyUserCreation;
import com.cwms.entities.UserInfoCRM;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.UserInfoCRMRepo;
import com.cwms.service.EmailService;

@RestController
@CrossOrigin("*")
@RequestMapping("/customerPortal")
public class CustomerPortalController {

	@Autowired
	public PartyRepository partyRepo;

	@Autowired
	private PartyAddressRepository partyAddressRepo;

	@Autowired
	private CompanyRepo companyrepo;

	@Autowired
	private BranchRepo branchRepo;

	@Autowired
	private UserInfoCRMRepo userinfocrmrepo;

	@Autowired
	private BCryptPasswordEncoder passwordencode;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private EmailService emailService;

	@GetMapping("/getCustomers")
	public ResponseEntity<?> getCustomers(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {

		List<Object[]> pendingData = partyRepo.getPendingData(cid, bid);

		List<Object[]> approvedData = partyRepo.getApprovedData(cid, bid);

		Map<String, Object> data = new HashMap<>();
		data.put("pendingData", pendingData);
		data.put("approvedData", approvedData);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/getCustomerById")
	public ResponseEntity<?> getCustomerById(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id, @RequestParam("partyid") String partyid) {

		Party party = partyRepo.getDataById(cid, bid, partyid);

		if (party == null) {
			return new ResponseEntity<>("Party data not found", HttpStatus.CONFLICT);
		}

		PartyAddress address = partyAddressRepo.getDataBySr(cid, bid, partyid, "1");

		if (address == null) {
			return new ResponseEntity<>("Party address data not found", HttpStatus.CONFLICT);
		}

		List<Object[]> userData = partyRepo.getDataByUserId(cid, bid, partyid);

		Map<String, Object> data = new HashMap<>();

		data.put("party", party);
		data.put("address", address);
		data.put("userData", (userData == null || userData.isEmpty()) ? null : userData.get(0));

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@GetMapping("/downloadExistingFile")
	public ResponseEntity<?> downloadFile(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("partyId") String partyId, @RequestParam("report") String report)
			throws FileNotFoundException {

		Party party = partyRepo.getDataById(cid, bid, partyId);

		if (party == null) {
			return new ResponseEntity<>("Party data not found", HttpStatus.CONFLICT);
		}
		System.out.println("4");
		String docPath = "";

		if ("address".equals(report)) {
			docPath = party.getAddressUploadPath();
		} else if ("pan".equals(report)) {
			docPath = party.getPanUploadPath();
		} else if ("gst".equals(report)) {
			docPath = party.getGstUploadPath();
		}

		// Create a File object from the complete file path
		File file = new File(docPath);

		if (!file.exists() || !file.canRead()) {
			return new ResponseEntity<>("File not found", HttpStatus.CONFLICT);
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

	@Transactional
	@PostMapping("/saveUserCRMData")
	public ResponseEntity<?> saveUserCRMData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Party party) {

		if (party == null) {
			return new ResponseEntity<>("Party data not found", HttpStatus.INTERNAL_SERVER_ERROR);

		}

		Party data = partyRepo.getDataById(cid, bid, party.getPartyId());

		if (data == null) {
			return new ResponseEntity<>("Party data not found", HttpStatus.CONFLICT);
		}

		boolean checkUserId = userinfocrmrepo.checkDataByUserId(cid, bid, party.getLoginId());

		if (checkUserId) {
			return new ResponseEntity<>("User id already exist.", HttpStatus.CONFLICT);

		}

		data.setApprovalRemarks(party.getApprovalRemarks());
		data.setStatus("A");
		data.setApprovedBy(user);
		data.setApprovedDate(new Date());
		data.setPendingApproval("A");

		partyRepo.save(data);

		PartyAddress address = partyAddressRepo.getDataBySr1(cid, bid, party.getPartyId(), "1");

		if (address == null) {
			return new ResponseEntity<>("Party address data not found", HttpStatus.CONFLICT);
		}

		address.setStatus("A");
		address.setApprovedBy(user);
		address.setApprovedDate(new Date());

		partyAddressRepo.save(address);

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05122", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("CRM%07d", nextNumericNextID1);

		UserInfoCRM crm = new UserInfoCRM();
		crm.setCompany_Id(cid);
		crm.setBranch_Id(bid);
		crm.setUser_Id(party.getLoginId());
		crm.setUser_Name(party.getPartyName());
		crm.setAutoId(HoldNextIdD1);
		crm.setUser_Type("CRM");
		crm.setUser_Password(passwordencode.encode(party.getLoginPassword()));
		crm.setMapped_User("CRMUSER");
		crm.setUser_Email(party.getContactEmail());
		crm.setStop_Trans('N');
		crm.setCreated_By(user);
		crm.setCreated_Date(new Date());
		crm.setApproved_By(user);
		crm.setApproved_Date(new Date());
		crm.setStatus("A");
		crm.setRole("ROLE_USER");
		crm.setMobile(party.getContactPhone());
		crm.setOTP("1000");
		crm.setDefaultotp("1000");
		crm.setLogintypeid(party.getPartyId());
		crm.setLogintype("");

		userinfocrmrepo.save(crm);

		processnextidrepo.updateAuditTrail(cid, bid, "P05122", HoldNextIdD1, "2024");

		if (data.getContactEmail() != null && !data.getContactEmail().isEmpty()) {

			String com = companyrepo.findByCompany_Id1(cid);

			Branch branch = branchRepo.getDataByCompanyAndBranch(cid, bid);

			CompletableFuture.runAsync(() -> emailService.sendRegistrationMail(data.getContactEmail(), com, data.getPartyName(), party.getLoginId(),
					party.getLoginPassword(), branch.getCrmLink(), crm.getDefaultotp()));
		}

		List<Object[]> userData = partyRepo.getDataByUserId(cid, bid, party.getPartyId());

		Map<String, Object> data1 = new HashMap<>();

		data1.put("party", data);
		data1.put("address", address);
		data1.put("userData", (userData == null || userData.isEmpty()) ? null : userData.get(0));

		return new ResponseEntity<>(data1, HttpStatus.OK);

	}

	@PostMapping("/saveAlreadyApprovedPartyUser")
	public ResponseEntity<?> saveAlreadyApprovedPartyUser(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestBody PartyUserCreation party) {

		if (party == null) {
			return new ResponseEntity<>("User data not found", HttpStatus.CONFLICT);
		}

		boolean checkUserId = userinfocrmrepo.checkDataByUserId(cid, bid, party.getUserId());

		if (checkUserId) {
			return new ResponseEntity<>("User id already exist.", HttpStatus.CONFLICT);

		}

		Party data = partyRepo.getDataById(cid, bid, party.getPartyId());

		if (data == null) {
			return new ResponseEntity<>("Party data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05122", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("CRM%07d", nextNumericNextID1);

		UserInfoCRM crm = new UserInfoCRM();
		crm.setCompany_Id(cid);
		crm.setBranch_Id(bid);
		crm.setUser_Id(party.getUserId());
		crm.setUser_Name(party.getPartyName());
		crm.setAutoId(HoldNextIdD1);
		crm.setUser_Type("CRM");
		crm.setUser_Password(passwordencode.encode(party.getPassword()));
		crm.setMapped_User("CRMUSER");
		crm.setUser_Email(data.getContactEmail());
		crm.setStop_Trans('N');
		crm.setCreated_By(user);
		crm.setCreated_Date(new Date());
		crm.setApproved_By(user);
		crm.setApproved_Date(new Date());
		crm.setStatus("A");
		crm.setRole("ROLE_USER");
		crm.setMobile(data.getContactPhone());
		crm.setOTP("1000");
		crm.setDefaultotp("1000");
		crm.setLogintypeid(party.getPartyId());
		crm.setLogintype("");

		userinfocrmrepo.save(crm);

		processnextidrepo.updateAuditTrail(cid, bid, "P05122", HoldNextIdD1, "2024");

		if (data.getContactEmail() != null && !data.getContactEmail().isEmpty()) {

			String com = companyrepo.findByCompany_Id1(cid);

			Branch branch = branchRepo.getDataByCompanyAndBranch(cid, bid);

			CompletableFuture.runAsync(() -> emailService.sendRegistrationMail(data.getContactEmail(), com, data.getPartyName(), party.getUserId(),
					party.getPassword(), branch.getCrmLink(), crm.getDefaultotp()));
		}
		return new ResponseEntity<>("User save successfully!!",HttpStatus.OK);

	}

}
