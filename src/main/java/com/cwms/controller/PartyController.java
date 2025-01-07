package com.cwms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.cwms.entities.PartyModal;
import com.cwms.entities.PartyModal.AddressDetails;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.JarDetailRepository;
import com.cwms.repository.PartyAddressRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.PortRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VesselRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/party")
public class PartyController {

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private PartyAddressRepository partyaddressrepo;

	@Autowired
	private BranchRepo branchrepo;

	@Autowired
	private ProcessNextIdRepository processrepo;

	@Autowired
	private JarDetailRepository jarrepo;

	@Autowired
	private PortRepository portrepository;

	@Autowired
	private VesselRepository vesselrepo;
	
	
	Logger logger = LoggerFactory.getLogger(Party.class);

	@GetMapping("/getDefaultBranch")
	public List<Branch> getDefaultBranches(@RequestParam("cid") String cid) {
		return branchrepo.getBranchByCompany(cid);
	}

	@GetMapping("/getAddresses")
	public List<PartyAddress> getAddress(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id) {

		return partyaddressrepo.getDataByPartyId(cid, bid, id);
	}

	@PostMapping("/savePartyWithAddress")
	public ResponseEntity<?> saveParty(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("flag") String flag, @RequestParam("user") String user,
			@RequestBody PartyModal partyDetails) {
		Party party = partyDetails.getParty();
		AddressDetails dtls = partyDetails.getAddress();

		if ("add".equals(flag)) {

			if (party != null) {

				Boolean existPan = partyrepo.isExistPanNo(cid, bid, party.getPanNo());

				if (existPan) {
					return new ResponseEntity<>("Duplicate pan No.", HttpStatus.BAD_REQUEST);
				}

				if (!party.getPhoneNo().isEmpty()) {
					Boolean existMobile = partyrepo.isExistPhoneNo(cid, bid, party.getPhoneNo());

					if (existMobile) {
						return new ResponseEntity<>("Duplicate phone No.", HttpStatus.BAD_REQUEST);
					}
				}

				Boolean existGST = partyaddressrepo.isGSTExist(cid, bid, dtls.getGstNo());

				if (existGST) {
					return new ResponseEntity<>("Duplicate GST No.", HttpStatus.BAD_REQUEST);
				}

				String holdId1 = processrepo.findAuditTrail(cid, bid, "P05057", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(1));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("P%05d", nextNumericNextID1);

				party.setCompanyId(cid);
				party.setBranchId(bid);
				party.setPartyId(HoldNextIdD1);
				party.setDefaultBranch(bid);
				party.setStatus("A");
				party.setCreatedBy(user);
				party.setCreatedDate(new Date());

				party.setApprovedBy(user);
				party.setApprovedDate(new Date());

				partyrepo.save(party);
				processrepo.updateAuditTrail(cid, bid, "P05057", HoldNextIdD1, "2024");

				PartyAddress address = new PartyAddress();
				address.setCompanyId(cid);
				address.setBranchId(bid);
				address.setPartyId(party.getPartyId());
				address.setPartyType(dtls.getCustomerType());
				address.setAddress1(dtls.getAddress1());
				address.setAddress2(dtls.getAddress2());
				address.setAddress3(dtls.getAddress3());
				address.setApprovedBy(user);
				address.setApprovedDate(new Date());
				address.setCity(dtls.getCity());
				address.setCreatedDate(new Date());
				address.setDefaultChk(dtls.getDefaultChk());
				address.setGstNo(dtls.getGstNo());
				address.setPin(dtls.getPin());
				address.setSrNo(String.valueOf(dtls.getSrNo()));
				address.setState(dtls.getState());
				address.setStatus("A");

				partyaddressrepo.save(address);

				return new ResponseEntity<>(party, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		} else {

			if (party != null) {
				Boolean existPan = partyrepo.isExistPanNo1(cid, bid, party.getPanNo(), party.getPartyId());

				if (existPan) {
					return new ResponseEntity<>("Duplicate pan No.", HttpStatus.BAD_REQUEST);
				}

				if (!party.getPhoneNo().isEmpty()) {
					Boolean existMobile = partyrepo.isExistPhoneNo1(cid, bid, party.getPhoneNo(), party.getPartyId());

					if (existMobile) {
						return new ResponseEntity<>("Duplicate phone No.", HttpStatus.BAD_REQUEST);
					}
				}

				party.setEditedBy(user);
				party.setEditedDate(new Date());

				partyrepo.save(party);

				PartyAddress address = partyaddressrepo.getData(cid, bid, party.getPartyId(),
						String.valueOf(dtls.getSrNo()));

				if (address != null) {

					Boolean existGST = partyaddressrepo.isGSTExist1(cid, bid, dtls.getGstNo(), party.getPartyId(),
							String.valueOf(dtls.getSrNo()));

					if (existGST) {
						return new ResponseEntity<>("Duplicate GST No.", HttpStatus.BAD_REQUEST);
					}

					address.setPartyType(dtls.getCustomerType());
					address.setAddress1(dtls.getAddress1());
					address.setAddress2(dtls.getAddress2());
					address.setAddress3(dtls.getAddress3());
					address.setCity(dtls.getCity());
					address.setCreatedDate(new Date());
					address.setDefaultChk(dtls.getDefaultChk());
					address.setGstNo(dtls.getGstNo());
					address.setPin(dtls.getPin());
					address.setState(dtls.getState());
					address.setEditedBy(user);
					address.setEditedDate(new Date());

					partyaddressrepo.save(address);
				} else {
					Boolean existGST = partyaddressrepo.isGSTExist(cid, bid, dtls.getGstNo());

					if (existGST) {
						return new ResponseEntity<>("Duplicate GST No.", HttpStatus.BAD_REQUEST);
					}

					PartyAddress address1 = new PartyAddress();
					address1.setCompanyId(cid);
					address1.setBranchId(bid);
					address1.setPartyId(party.getPartyId());
					address1.setPartyType(dtls.getCustomerType());
					address1.setAddress1(dtls.getAddress1());
					address1.setAddress2(dtls.getAddress2());
					address1.setAddress3(dtls.getAddress3());
					address1.setApprovedBy(user);
					address1.setApprovedDate(new Date());
					address1.setCity(dtls.getCity());
					address1.setCreatedDate(new Date());
					address1.setDefaultChk(dtls.getDefaultChk());
					address1.setGstNo(dtls.getGstNo());
					address1.setPin(dtls.getPin());
					address1.setSrNo(String.valueOf(dtls.getSrNo()));
					address1.setState(dtls.getState());
					address1.setStatus("A");

					partyaddressrepo.save(address1);
				}

				int update = partyaddressrepo.updateDefault(cid, bid, party.getPartyId(),
						String.valueOf(dtls.getSrNo()));

				return new ResponseEntity<>(party, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		}

	}

	@PostMapping("/saveParty")
	public ResponseEntity<?> savePartyWTAdd(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("flag") String flag, @RequestParam("user") String user, @RequestBody Party party) {
		System.out.println("Party " + party);

		if ("add".equals(flag)) {

			if (party != null) {

				Boolean existPan = partyrepo.isExistPanNo(cid, bid, party.getPanNo());

				if (existPan) {
					return new ResponseEntity<>("Duplicate pan No.", HttpStatus.BAD_REQUEST);
				}
				if (!party.getPhoneNo().isEmpty()) {
					Boolean existMobile = partyrepo.isExistPhoneNo(cid, bid, party.getPhoneNo());

					if (existMobile) {
						return new ResponseEntity<>("Duplicate phone No.", HttpStatus.BAD_REQUEST);
					}
				}

				String holdId1 = processrepo.findAuditTrail(cid, bid, "P05057", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(1));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("P%05d", nextNumericNextID1);

				party.setCompanyId(cid);
				party.setBranchId(bid);
				party.setPartyId(HoldNextIdD1);
				party.setDefaultBranch(bid);
				party.setStatus("A");
				party.setCreatedBy(user);
				party.setCreatedDate(new Date());

				party.setApprovedBy(user);
				party.setApprovedDate(new Date());

				partyrepo.save(party);
				processrepo.updateAuditTrail(cid, bid, "P05057", HoldNextIdD1, "2024");

				return new ResponseEntity<>(party, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		} else {

			if (party != null) {
				Boolean existPan = partyrepo.isExistPanNo1(cid, bid, party.getPanNo(), party.getPartyId());

				if (existPan) {
					return new ResponseEntity<>("Duplicate pan No.", HttpStatus.BAD_REQUEST);
				}

				if (!party.getPhoneNo().isEmpty()) {
					Boolean existMobile = partyrepo.isExistPhoneNo1(cid, bid, party.getPhoneNo(), party.getPartyId());

					if (existMobile) {
						return new ResponseEntity<>("Duplicate phone No.", HttpStatus.BAD_REQUEST);
					}
				}

				party.setEditedBy(user);
				party.setEditedDate(new Date());

				partyrepo.save(party);

				return new ResponseEntity<>(party, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		}

	}

	@GetMapping("/search")
	public List<Object[]> search(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "name", required = false) String name) {

		
		return partyrepo.search(cid, bid, name);
	}

	@GetMapping("/getPartyData")
	public Party getDataByPartyId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("partyId") String partyid) {
		return partyrepo.getDataById(cid, bid, partyid);
	}

	@PostMapping("/deleteAddress")
	public String deleteAddress(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("partyId") String partyid, @RequestParam("sr") String sr) {
		PartyAddress party = partyaddressrepo.getData(cid, bid, partyid, sr);
		party.setStatus("D");
		partyaddressrepo.save(party);

		return "success";
	}

	@PostMapping("/deleteParty")
	public String deleteParty(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("partyId") String partyid) {
		Party p = partyrepo.getDataById(cid, bid, partyid);
		p.setStatus("D");
		partyrepo.save(p);

		int data = partyaddressrepo.updateStatus(cid, bid, partyid);

		return "success";
	}

	@GetMapping("/getDataForVessel")
	public Map<String, List<Object[]>> getData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {
		Map<String, List<Object[]>> data = new HashMap<String, List<Object[]>>();

		List<Object[]> country = jarrepo.findByJarId(cid, "J00002");
		if (!country.isEmpty()) {
			data.put("country", country);
		}

		List<Object[]> party = partyrepo.getAll(cid, bid);
		if (!party.isEmpty()) {
			data.put("party", party);
		}

		List<Object[]> port = portrepository.getData(cid, bid);
		if (!port.isEmpty()) {
			data.put("port", port);
		}

		return data;

	}

	@GetMapping("/getPortData")
	public Map<String, List<Object[]>> getData(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {
		List<Object[]> getPort = portrepository.getData(cid, bid);

		Map<String, List<Object[]>> data = new HashMap<String, List<Object[]>>();
		if (!getPort.isEmpty()) {
			data.put("port", getPort);
		}

		List<Object[]> vessel = vesselrepo.getData(cid, bid);
		if (!vessel.isEmpty()) {
			data.put("vessel", vessel);
		}

		List<Object[]> liner = partyrepo.getLiner(cid, bid);
		if (!liner.isEmpty()) {
			data.put("liner", liner);
		}

		List<Object[]> agent = partyrepo.getAgent(cid, bid);
		if (!agent.isEmpty()) {
			data.put("agent", agent);
		}

		return data;
	}

	@GetMapping("/getPortData1")
	public List<Object[]> getData12(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = portrepository.getData1(cid, bid, val);

		return getPort;
	}

	@GetMapping("/getVessel")
	public List<Object[]> getVessel(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = vesselrepo.getData1(cid, bid, val);

		return getPort;
	}

	@GetMapping("/getLiner")
	public List<Object[]> getLiner(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getLiner1(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getSingleLiner")
	public ResponseEntity<?> getSingleLiner(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		Object[] getPort = partyrepo.getLiner2(cid, bid, val);
		
		if(getPort == null) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(getPort,HttpStatus.OK);
		}

		
	}

	@GetMapping("/getAgent")
	public List<Object[]> getAgent(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getAgent1(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getSingleAgent")
	public ResponseEntity<?> getSingleAgent(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		Object[] getPort = partyrepo.getAgent2(cid, bid, val);

		
		if(getPort == null) {
			return new ResponseEntity<>("Data not found",HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(getPort,HttpStatus.OK);
		}
	}

	@GetMapping("/getImp")
	public List<Object[]> getImp(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getDataForImp(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getImp1")
	public List<Object[]> getImp1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getDataForImp1(cid, bid, val);

		return getPort;
	}
	
	
	@GetMapping("/getForwarder")
	public List<Object[]> getForwarder(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getFwdWithAdd(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getImpAddress")
	public List<Object[]> getImpAddress(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getImpAddress(cid, bid, val);

		return getPort;
	}

	@GetMapping("/getCha")
	public List<Object[]> getCha(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getCHA(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getChaWithAdd")
	public List<Object[]> getChaWithAdd(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getCHAWithAdd(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getCha1")
	public ResponseEntity<?> getCha1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getCHA1(cid, bid, val);
		
		if(getPort.isEmpty()) {
			return new ResponseEntity<>("not found",HttpStatus.CONFLICT);
		}

		else {
			return new ResponseEntity<>(getPort,HttpStatus.OK);
		}
		
	}

	@GetMapping("/getAll")
	public List<Object[]> getAll(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getAll(cid, bid, val);

		return getPort;
	}
	
	@GetMapping("/getAllWithAdd")
	public List<Object[]> getAllWithAdd(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getAllWithAdd(cid, bid, val);

		return getPort;
	}

	@GetMapping("/getAcc")
	public List<Object[]> getAcc(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> getPort = partyrepo.getImp(cid, bid, val);

		return getPort;
	}
	


	@GetMapping("/getTrans")
	public List<Object[]> getTransp(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {
		List<Object[]> getPort = partyrepo.getTransporter(cid, bid);

		return getPort;
	}

	
	@GetMapping("/getCustomerCodes")
	public ResponseEntity<?> getCustomerCodes(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam(name="code1",required = false) String code1, @RequestParam(name="code2",required = false) String code2){
	
		  
		List<Party> customerCodes = partyrepo.getCustomerCodes(companyId, branchId, code1, code2); 
		return ResponseEntity.ok(customerCodes);		  
	}
	
	
	
//	get PartyBy Type
	
//	get PartyBy Type
	
	@GetMapping("/getPartyByType")
	public ResponseEntity<?> getPartyByType(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("searchValue") String searchValue, @RequestParam(name="type",required = false) String type){
		List<Map<String, Object>>  getParties = new ArrayList<>();
		List<Map<String, Object>>  toSendGetParties = new ArrayList<>();
		
		  if("exp".equals(type))
		  {			  
				 getParties = partyrepo.getPartyByType(companyId, branchId,searchValue);
				 
				 toSendGetParties = getParties.stream().map(row -> {
					 
					 Object partyName = row.get("party_Name");					 
					 Object srNo = row.get("sr_No");
					 Object partyId = row.get("party_Id");
					 
					 
					 
				        Map<String, Object> map = new HashMap<>();
				        map.put("value", partyId + " " +srNo);
				        map.put("label", partyName );
				        map.put("address1", row.get("address_1"));
				        map.put("address2", row.get("address_2"));
				        map.put("address3", row.get("address_3"));
				        map.put("srNo", row.get("sr_No"));
				        map.put("gstNo", row.get("gst_No"));
				        map.put("state", row.get("State"));
				        map.put("iecCode", row.get("iec_Code"));
				        map.put("partyId", row.get("party_Id"));
				        map.put("partyName", row.get("party_Name"));
				        return map;
				    }).collect(Collectors.toList());
				 
		  }
		  else if("cha".equals(type))
		  {
			  List<Object[]> getParties2 = partyrepo.getDataForCha(companyId, branchId,searchValue);
				 
					 toSendGetParties = getParties2.stream().map(row -> {
				        Map<String, Object> map = new HashMap<>();
				        map.put("value", row[0]);
				        map.put("label", row[1]);
				        map.put("partyType", row[2]);
				        map.put("customerCode", row[3]);
				        return map;
				    }).collect(Collectors.toList());		  }
		  else if("agent".equals(type))
		  {
			  List<Object[]> getParties2 = partyrepo.getDataForOnAccount(companyId, branchId,searchValue);
			  toSendGetParties = getParties2.stream().map(row -> {
			        Map<String, Object> map = new HashMap<>();
			        map.put("value", row[0]);
			        map.put("label", row[1]);
			        map.put("partyType", row[2]);
			        map.put("customerCode", row[3]);
			        return map;
			    }).collect(Collectors.toList());	
			  
		  }
		  
		return ResponseEntity.ok(toSendGetParties);
	}
	
	@GetMapping("/getPartyByTypeValue")
	public ResponseEntity<?> getPartyByTypeValue(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
			@RequestParam("searchValue") String searchValue, @RequestParam(name="type",required = false) String type){
		List<Map<String, Object>>  toSendGetParties = new ArrayList<>();
		
		List<Object[]> partyByTypeValue = partyrepo.getPartyByTypeValue(companyId, branchId, searchValue, type);
	
		  toSendGetParties = partyByTypeValue.stream().map(row -> {
		        Map<String, Object> map = new HashMap<>();
		        map.put("value", row[0]);
		        map.put("label", row[1]);
		        return map;
		    }).collect(Collectors.toList());	
	
	return ResponseEntity.ok(toSendGetParties);
	}
}
