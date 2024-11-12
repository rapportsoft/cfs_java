package com.cwms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cwms.entities.Cfigmcn;
import com.cwms.entities.EquipmentActivity;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.GateIn;
import com.cwms.entities.JarDetail;
import com.cwms.entities.Party;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.EquipmentActivityRepository;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.JarDetailRepository;
import com.cwms.repository.PartyRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/equipmentActivity")
public class EquipmentActivityController {

	@Autowired
	private EquipmentActivityRepository equipmentActivityRepository;
	
	@Autowired
	private PartyRepository partyrepo;
	
	@Autowired
	private JarDetailRepository jardtlrepo;
	
	@Autowired
	private CfIgmCnRepository cfigmcnrepo;
	
	@Autowired
	private ExportCartingRepo cartingRepo;
	
	@Autowired
	private GateInRepository gateInRepo;

	@Autowired
	private HelperMethods helperMethods;
	
	@Autowired
	private ExportStuffTallyRepo exportstufftallyrepo;
	
	@GetMapping("/getVendor")
	public Map<String, Object> getParty(@RequestParam("cid") String cid,@RequestParam("bid") String bid) {
		Party party = partyrepo.getDataById(cid, bid, "P00015");
		
		
		List<JarDetail> getJar = jardtlrepo.getDataByJarId(cid, "J00012");
		
		Map<String, Object> data = new HashMap<>();
		data.put("party", party);
		data.put("jar", getJar);
		
		return data;
	}
	
	
	@PostMapping("/saveEquipment")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("equipment") String equip,@RequestParam("vendor") String vendor,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId,@RequestParam("finYear") String finYear,@RequestBody List<String> container){
		if(container.isEmpty()) {
			return new ResponseEntity<>("Container data not found",HttpStatus.BAD_REQUEST);
		}
	

		for(String cont : container) {
			Cfigmcn con = cfigmcnrepo.getSingleData4(cid, bid, igmTransId, igm,igmLine, cont);
			
			if(con != null) {
				int srNo = equipmentActivityRepository.getCount(cid, bid, igmTransId, igm,igmLine);
				EquipmentActivity equipment = new EquipmentActivity();
				equipment.setCompanyId(cid);
				equipment.setBranchId(bid);
				equipment.setApprovedBy(user);
				equipment.setApprovedDate(new Date());
				equipment.setContainerNo(con.getContainerNo());
				equipment.setContainerSize(con.getContainerSize());
				equipment.setContainerType(con.getContainerType());
				equipment.setCreatedBy(user);
				equipment.setCreatedDate(new Date());
				equipment.setDeStuffId(con.getContainerExamWoTransId());
				equipment.setDocRefNo(con.getIgmNo());
				equipment.setEditedBy(user);
				equipment.setEditedDate(new Date());
				equipment.setEquipment(equip);
				equipment.setEquipmentNm(equip);
				equipment.setErpDocRefNo(con.getIgmTransId());
				equipment.setFinYear(finYear);
				equipment.setProcessId("P00205");
				equipment.setProfitCenterId(con.getProfitcentreId());
				equipment.setSrNo(srNo+1);
				equipment.setSubDocRefNo(con.getIgmLineNo());
				equipment.setVendorId(vendor);
				equipment.setVendorNm(vendor);
				equipment.setStatus("A");
				equipmentActivityRepository.save(equipment);
		
			}
		}
		return new ResponseEntity<>("Data save successfully!!!",HttpStatus.OK);
	}
	
	
	@PostMapping("/saveDestuffEquipment")
	public ResponseEntity<?> saveDestuffData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("equipment") String equip,@RequestParam("vendor") String vendor,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId,@RequestParam("finYear") String finYear,@RequestBody List<String> container){
		if(container.isEmpty()) {
			return new ResponseEntity<>("Container data not found",HttpStatus.BAD_REQUEST);
		}
	

		for(String cont : container) {
			Cfigmcn con = cfigmcnrepo.getSingleData4(cid, bid, igmTransId, igm,igmLine, cont);
			
			if(con != null) {
				int srNo = equipmentActivityRepository.getCount(cid, bid, igmTransId, igm,igmLine);
				EquipmentActivity equipment = new EquipmentActivity();
				equipment.setCompanyId(cid);
				equipment.setBranchId(bid);
				equipment.setApprovedBy(user);
				equipment.setApprovedDate(new Date());
				equipment.setContainerNo(con.getContainerNo());
				equipment.setContainerSize(con.getContainerSize());
				equipment.setContainerType(con.getContainerType());
				equipment.setCreatedBy(user);
				equipment.setCreatedDate(new Date());
				equipment.setDeStuffId(con.getDeStuffId());
				equipment.setDocRefNo(con.getIgmNo());
				equipment.setEditedBy(user);
				equipment.setEditedDate(new Date());
				equipment.setEquipment(equip);
				equipment.setEquipmentNm(equip);
				equipment.setErpDocRefNo(con.getIgmTransId());
				equipment.setFinYear(finYear);
				equipment.setProcessId("P00205");
				equipment.setProfitCenterId(con.getProfitcentreId());
				equipment.setSrNo(srNo+1);
				equipment.setSubDocRefNo(con.getIgmLineNo());
				equipment.setVendorId(vendor);
				equipment.setVendorNm(vendor);
				equipment.setStatus("A");
				equipmentActivityRepository.save(equipment);
		
			}
		}
		return new ResponseEntity<>("Data save successfully!!!",HttpStatus.OK);
	}
	
	
	@GetMapping("/getData")
	public List<Object[]> getData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId){
		
		return equipmentActivityRepository.getData(cid, bid, igmTransId, igm, igmLine);
	}
	
	@PostMapping("/deleteEquipments")
	public ResponseEntity<?> deleteData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("equipment") String equip,@RequestParam("vendor") String vendor,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId){
		
		int data = equipmentActivityRepository.deleteData(cid, bid, igmTransId, igm, igmLine, equip, vendor, user, new Date());
		
		if(data > 0) {
			return new ResponseEntity<>("Data deleted successfully!!!",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Data not found!!!",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/saveContainerEquipment")
	public ResponseEntity<?> saveContainerData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("equipment") String equip,@RequestParam("vendor") String vendor,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId,@RequestParam("finYear") String finYear,@RequestParam("container") String container){
		if(container.isEmpty() || container == null) {
			return new ResponseEntity<>("Container data not found",HttpStatus.BAD_REQUEST);
		}
	

		
			Cfigmcn con = cfigmcnrepo.getSingleData4(cid, bid, igmTransId, igm,igmLine, container);
			
			if(con != null) {
				int srNo = equipmentActivityRepository.getCount(cid, bid, igmTransId, igm,igmLine);
				EquipmentActivity equipment = new EquipmentActivity();
				equipment.setCompanyId(cid);
				equipment.setBranchId(bid);
				equipment.setApprovedBy(user);
				equipment.setApprovedDate(new Date());
				equipment.setContainerNo(con.getContainerNo());
				equipment.setContainerSize(con.getContainerSize());
				equipment.setContainerType(con.getContainerType());
				equipment.setCreatedBy(user);
				equipment.setCreatedDate(new Date());
				equipment.setDeStuffId(con.getContainerExamWoTransId());
				equipment.setDocRefNo(con.getIgmNo());
				equipment.setEditedBy(user);
				equipment.setEditedDate(new Date());
				equipment.setEquipment(equip);
				equipment.setEquipmentNm(equip);
				equipment.setErpDocRefNo(con.getIgmTransId());
				equipment.setFinYear(finYear);
				equipment.setProcessId("P00205");
				equipment.setProfitCenterId(con.getProfitcentreId());
				equipment.setSrNo(srNo+1);
				equipment.setSubDocRefNo(con.getIgmLineNo());
				equipment.setVendorId(vendor);
				equipment.setVendorNm(vendor);
				equipment.setStatus("A");
				equipmentActivityRepository.save(equipment);
		
			}
		
		return new ResponseEntity<>("Data save successfully!!!",HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/saveDestuffContainerEquipment")
	public ResponseEntity<?> saveDestuffContainerData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("equipment") String equip,@RequestParam("vendor") String vendor,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId,@RequestParam("finYear") String finYear,@RequestParam("container") String container){
		if(container.isEmpty() || container == null) {
			return new ResponseEntity<>("Container data not found",HttpStatus.BAD_REQUEST);
		}
	

		
			Cfigmcn con = cfigmcnrepo.getSingleData4(cid, bid, igmTransId, igm,igmLine, container);
			
			if(con != null) {
				int srNo = equipmentActivityRepository.getCount(cid, bid, igmTransId, igm,igmLine);
				EquipmentActivity equipment = new EquipmentActivity();
				equipment.setCompanyId(cid);
				equipment.setBranchId(bid);
				equipment.setApprovedBy(user);
				equipment.setApprovedDate(new Date());
				equipment.setContainerNo(con.getContainerNo());
				equipment.setContainerSize(con.getContainerSize());
				equipment.setContainerType(con.getContainerType());
				equipment.setCreatedBy(user);
				equipment.setCreatedDate(new Date());
				equipment.setDeStuffId(con.getDeStuffId());
				equipment.setDocRefNo(con.getIgmNo());
				equipment.setEditedBy(user);
				equipment.setEditedDate(new Date());
				equipment.setEquipment(equip);
				equipment.setEquipmentNm(equip);
				equipment.setErpDocRefNo(con.getIgmTransId());
				equipment.setFinYear(finYear);
				equipment.setProcessId("P00205");
				equipment.setProfitCenterId(con.getProfitcentreId());
				equipment.setSrNo(srNo+1);
				equipment.setSubDocRefNo(con.getIgmLineNo());
				equipment.setVendorId(vendor);
				equipment.setVendorNm(vendor);
				equipment.setStatus("A");
				equipmentActivityRepository.save(equipment);
		
			}
		
		return new ResponseEntity<>("Data save successfully!!!",HttpStatus.OK);
	}
	
	
	@GetMapping("/getContainerData")
	public List<Object[]> getContainerData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId,@RequestParam("container") String container){
		
		return equipmentActivityRepository.getData1(cid, bid, igmTransId, igm, igmLine,container);
	}
	
	
	@PostMapping("/deleteContainerEquipments")
	public ResponseEntity<?> deleteContainerData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("equipment") String equip,@RequestParam("vendor") String vendor,@RequestParam("igm") String igm,
			@RequestParam("igmLine") String igmLine,@RequestParam("igmTransId") String igmTransId,@RequestParam("container") String container){
		
		int data = equipmentActivityRepository.deleteData1(cid, bid, igmTransId, igm, igmLine, equip, vendor, user, new Date(),container);
		
		if(data > 0) {
			return new ResponseEntity<>("Data deleted successfully!!!",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Data not found!!!",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/saveEquipMent")
	public ResponseEntity<?> saveEquipMent(
	        @RequestBody EquipmentActivity equipmentActivity,	       
	        @RequestParam("userId") String userId
	       ) {	    
	    try {	    	
	    	
	    	System.out.println(equipmentActivity);
	    	
	    	boolean existsByEquipMent = equipmentActivityRepository.existsByEquipMent(equipmentActivity.getCompanyId(), equipmentActivity.getBranchId(), equipmentActivity.getProfitCenterId(), equipmentActivity.getProcessId(), equipmentActivity.getErpDocRefNo(), equipmentActivity.getDocRefNo(), equipmentActivity.getDeStuffId(), equipmentActivity.getEquipment(), equipmentActivity.getSrNo());
	    		    	
	    	System.out.println("existsByEquipMent : "+existsByEquipMent);
	    	 if (existsByEquipMent) {
		            String errorMessage = "Duplicate Equipment found for SB No: " + equipmentActivity.getDocRefNo() + " and Equipment: " + equipmentActivity.getEquipmentNm();
		            return ResponseEntity.badRequest().body(errorMessage);
		        }
//	    	
	    	EquipmentActivity sendEquipmentActivity = new EquipmentActivity();
	    	 
	    	EquipmentActivity allEquipmentsWithEquipment = equipmentActivityRepository.getAllEquipmentsWithEquipment(equipmentActivity.getCompanyId(), equipmentActivity.getBranchId(), equipmentActivity.getProfitCenterId(), equipmentActivity.getProcessId(), equipmentActivity.getErpDocRefNo(), equipmentActivity.getDocRefNo(), equipmentActivity.getDeStuffId(), equipmentActivity.getEquipment());
	    	
	    	
	    	if(allEquipmentsWithEquipment != null)
	    	{
	    		
	    		
	    		allEquipmentsWithEquipment.setEquipment(equipmentActivity.getEquipment());
	    		allEquipmentsWithEquipment.setEquipmentNm(equipmentActivity.getEquipmentNm());
	    		
	    		allEquipmentsWithEquipment.setEditedBy(userId);
	    		allEquipmentsWithEquipment.setEditedDate(new Date());
	    		sendEquipmentActivity = equipmentActivityRepository.save(allEquipmentsWithEquipment);
	    	}
	    	else
	    	{
	    		String financialYear = helperMethods.getFinancialYear();
	    		
	    		equipmentActivity.setFinYear(financialYear);
	    		equipmentActivity.setStatus("A");
		    	equipmentActivity.setCreatedBy(userId);
		    	equipmentActivity.setEditedBy(userId);
		    	equipmentActivity.setEditedDate(new Date());
		    	equipmentActivity.setCreatedDate(new Date());	    	
		    	equipmentActivity.setApprovedBy(userId);
		    	equipmentActivity.setApprovedDate(new Date());
		    	sendEquipmentActivity = equipmentActivityRepository.save(equipmentActivity);		    	
	    	}	   
	    	
	    	
	    	System.out.println(sendEquipmentActivity);
	    	
	        return ResponseEntity.ok(sendEquipmentActivity);
	    } catch (Exception e) {	 
	    	System.out.println(e);
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	
	@GetMapping("/getAllEquipments")
	public ResponseEntity<?> getSelectedGateInEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("processId") String processId, 
	        @RequestParam("profitCenterId") String profitCenterId,
	        @RequestParam("gateInId") String gateInId, 
	        @RequestParam("sbNo") String sbNo	        
	       ) {	    
	    try {	
	    	
	    	System.out.println("companyId : "+ companyId + " branchId "+branchId + " sbTransId "+sbTransId + " processId "+processId+ " profitCenterId " +profitCenterId +  " gateInId "+gateInId + " sbNo "+sbNo);
	    	
	    	List<EquipmentActivity>  equipMentEntries = equipmentActivityRepository.getAllEquipments(companyId, branchId,  profitCenterId, processId, sbTransId, sbNo, gateInId);
	        return ResponseEntity.ok(equipMentEntries);
	    } catch (Exception e) {	 
	    	System.out.println(e);
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	@GetMapping("/getEquipments")
	public ResponseEntity<?> getEquipments(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("sbTransId") String sbTransId,
	        @RequestParam("processId") String processId, 
	        @RequestParam("profitCenterId") String profitCenterId,
	        @RequestParam("gateInId") String gateInId, 
	        @RequestParam("srNo") int srNo,
	        @RequestParam("equipMent") String equipMent, 
	        @RequestParam("sbNo") String sbNo	        
	       ) {	    
	    try {	    	
	    	EquipmentActivity  equipMentEntries = equipmentActivityRepository.getAllEquipmentsWithEquipmentSrNo(companyId, branchId,  profitCenterId, processId, sbTransId, sbNo, gateInId,equipMent,srNo);
	        return ResponseEntity.ok(equipMentEntries);
	    } catch (Exception e) {	 
	    	System.out.println(e);
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	@GetMapping("/deleteEquipment")
	public ResponseEntity<?> deleteEquipments(
			 @RequestParam("companyId") String companyId, 
		        @RequestParam("branchId") String branchId,	        
		        @RequestParam("sbTransId") String sbTransId,
		        @RequestParam("processId") String processId, 
		        @RequestParam("profitCenterId") String profitCenterId,
		        @RequestParam("gateInId") String gateInId, 
		        @RequestParam("srNo") int srNo,
		        @RequestParam("equipMent") String equipMent, 
		        @RequestParam("sbNo") String sbNo	,
		        @RequestParam("userId") String userId	
	       ) {	    
	    try {	    	
	    	EquipmentActivity  equipMentEntries = equipmentActivityRepository.getAllEquipmentsWithEquipmentSrNo(companyId, branchId,  profitCenterId, processId, sbTransId, sbNo, gateInId,equipMent,srNo);
	        
	    	equipMentEntries.setEditedBy(userId);
	    	equipMentEntries.setEditedDate(new Date());
	    	equipMentEntries.setStatus("D");
	    	equipmentActivityRepository.save(equipMentEntries);
	    	return ResponseEntity.ok(equipMentEntries);
	    } catch (Exception e) {	 
	    	System.out.println(e);
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	@PostMapping("/saveEquipMentCommonCarting")
	public ResponseEntity<?> saveEquipMentCommonCarting(@RequestBody EquipmentActivity equipmentActivity,
			@RequestParam("userId") String userId) {
		try {
			
			System.out.println("equipmentActivity \n"+ equipmentActivity );

			List<ExportCarting> cartingsForMultipleEquipment = cartingRepo.cartingsForMultipleEquipment(
					equipmentActivity.getCompanyId(), equipmentActivity.getBranchId(),
					equipmentActivity.getProfitCenterId(),
					equipmentActivity.getDeStuffId());

			System.out.println("cartingsForMultipleEquipment \n"+ cartingsForMultipleEquipment );
			for (ExportCarting gateIn : cartingsForMultipleEquipment) {

				System.out.println(equipmentActivity);				
    	
				EquipmentActivity sendEquipmentActivity = new EquipmentActivity();

				EquipmentActivity allEquipmentsWithEquipment = equipmentActivityRepository
						.getAllEquipmentsWithEquipment(equipmentActivity.getCompanyId(),
								equipmentActivity.getBranchId(), equipmentActivity.getProfitCenterId(),
								equipmentActivity.getProcessId(), gateIn.getSbTransId(),
								gateIn.getSbNo(), equipmentActivity.getDeStuffId(),
								equipmentActivity.getEquipment());

				if (allEquipmentsWithEquipment != null) {

					allEquipmentsWithEquipment.setEquipment(equipmentActivity.getEquipment());
					allEquipmentsWithEquipment.setEquipmentNm(equipmentActivity.getEquipmentNm());

					allEquipmentsWithEquipment.setEditedBy(userId);
					allEquipmentsWithEquipment.setEditedDate(new Date());
					sendEquipmentActivity = equipmentActivityRepository.save(allEquipmentsWithEquipment);
				} else {
					String financialYear = helperMethods.getFinancialYear();

					
					int getsrNo = equipmentActivityRepository.getHighestSrNo(equipmentActivity.getCompanyId(), equipmentActivity.getBranchId(),gateIn.getSbTransId(),gateIn.getSbNo());
					
					System.out.println(getsrNo);
					equipmentActivity.setSrNo(getsrNo + 1);
					equipmentActivity.setErpDocRefNo(gateIn.getSbTransId());
					equipmentActivity.setDocRefNo(gateIn.getSbNo());
					equipmentActivity.setFinYear(financialYear);
					equipmentActivity.setStatus("A");
					equipmentActivity.setCreatedBy(userId);
					equipmentActivity.setEditedBy(userId);
					equipmentActivity.setEditedDate(new Date());
					equipmentActivity.setCreatedDate(new Date());
					equipmentActivity.setApprovedBy(userId);
					equipmentActivity.setApprovedDate(new Date());
					sendEquipmentActivity = equipmentActivityRepository.save(equipmentActivity);
				}

				System.out.println(sendEquipmentActivity);
			}
			return ResponseEntity.ok("Success");
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}

	}
	
	
	
	@GetMapping("/getAllEquipmentsCommonCarting")
	public ResponseEntity<?> getAllEquipmentsCommonCarting(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitCenterId") String profitCenterId,
			@RequestParam("gateInId") String gateInId, @RequestParam("cartingTransId") String cartingTransId) {
		try {

			List<EquipmentActivity> equipMentEntries = equipmentActivityRepository.getAllEquipmentsCommonCarting(companyId,
					branchId, profitCenterId, cartingTransId, gateInId);
			return ResponseEntity.ok(equipMentEntries);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	
	
	
	
	
	
	
	@GetMapping("/getAllEquipmentsCarting")
	public ResponseEntity<?> getAllEquipmentsCarting(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("sbTransId") String sbTransId,
			@RequestParam("processId") String processId, @RequestParam("profitCenterId") String profitCenterId,
			@RequestParam("gateInId") String gateInId, @RequestParam("sbNo") String sbNo, @RequestParam("cartingTransId") String cartingTransId) {
		try {

			System.out.println("companyId : " + companyId + " branchId " + branchId + " sbTransId " + sbTransId
					+ " processId " + processId + " profitCenterId " + profitCenterId + " gateInId " + gateInId
					+ " sbNo " + sbNo + " cartingTransId "+ cartingTransId);

			List<EquipmentActivity> equipMentEntries = equipmentActivityRepository.getAllEquipmentsCarting(companyId, branchId,
					profitCenterId, sbTransId, sbNo, gateInId, cartingTransId);
			return ResponseEntity.ok(equipMentEntries);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}
	

	@PostMapping("/saveEquipMentCommon")
	public ResponseEntity<?> saveEquipMentCommon(@RequestBody EquipmentActivity equipmentActivity,
			@RequestParam("userId") String userId) {
		try {
			
			System.out.println("equipmentActivity \n"+ equipmentActivity );

			List<GateIn> gateInsForMultipleEquipment = gateInRepo.gateInsForMultipleEquipment(
					equipmentActivity.getCompanyId(), equipmentActivity.getBranchId(),
					equipmentActivity.getProfitCenterId(), equipmentActivity.getProcessId(),
					equipmentActivity.getDeStuffId(), "EXP");

			System.out.println("gateInsForMultipleEquipment \n"+ gateInsForMultipleEquipment );
			for (GateIn gateIn : gateInsForMultipleEquipment) {

				System.out.println(equipmentActivity);				
    	
				EquipmentActivity sendEquipmentActivity = new EquipmentActivity();

				EquipmentActivity allEquipmentsWithEquipment = equipmentActivityRepository
						.getAllEquipmentsWithEquipment(equipmentActivity.getCompanyId(),
								equipmentActivity.getBranchId(), equipmentActivity.getProfitCenterId(),
								equipmentActivity.getProcessId(), gateIn.getErpDocRefNo(),
								gateIn.getDocRefNo(), equipmentActivity.getDeStuffId(),
								equipmentActivity.getEquipment());

				if (allEquipmentsWithEquipment != null) {

					allEquipmentsWithEquipment.setEquipment(equipmentActivity.getEquipment());
					allEquipmentsWithEquipment.setEquipmentNm(equipmentActivity.getEquipmentNm());

					allEquipmentsWithEquipment.setEditedBy(userId);
					allEquipmentsWithEquipment.setEditedDate(new Date());
					sendEquipmentActivity = equipmentActivityRepository.save(allEquipmentsWithEquipment);
				} else {
					String financialYear = helperMethods.getFinancialYear();

					
					int getsrNo = equipmentActivityRepository.getHighestSrNo(equipmentActivity.getCompanyId(), equipmentActivity.getBranchId(),gateIn.getErpDocRefNo(),gateIn.getDocRefNo());
					
					System.out.println(getsrNo);
					equipmentActivity.setSrNo(getsrNo + 1);
					equipmentActivity.setErpDocRefNo(gateIn.getErpDocRefNo());
					equipmentActivity.setDocRefNo(gateIn.getDocRefNo());
					equipmentActivity.setFinYear(financialYear);
					equipmentActivity.setStatus("A");
					equipmentActivity.setCreatedBy(userId);
					equipmentActivity.setEditedBy(userId);
					equipmentActivity.setEditedDate(new Date());
					equipmentActivity.setCreatedDate(new Date());
					equipmentActivity.setApprovedBy(userId);
					equipmentActivity.setApprovedDate(new Date());
					sendEquipmentActivity = equipmentActivityRepository.save(equipmentActivity);
				}

				System.out.println(sendEquipmentActivity);
			}
			return ResponseEntity.ok("Success");
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}

	}
	
//	getAllEquipments of Gate In Ids 
	@GetMapping("/getAllEquipmentsCommon")
	public ResponseEntity<?> getAllEquipmentsCommon(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("profitCenterId") String profitCenterId,
			@RequestParam("gateInId") String gateInId, @RequestParam("processId") String processId) {
		try {

			List<EquipmentActivity> equipMentEntries = equipmentActivityRepository.getAllEquipmentsCommon(companyId,
					branchId, profitCenterId, processId, gateInId);
			return ResponseEntity.ok(equipMentEntries);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getContainerDataForStuffing")
	public ResponseEntity<?> getDataByContainerNo(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, 
			@RequestParam("con") String con, @RequestParam(name = "val1", required = false) String val1,
			@RequestParam(name = "val2", required = false) String val2) {

		List<Object[]> data = equipmentActivityRepository.getDataByContainerNo1(companyId, branchId, con,
				 val1, val2);
		
		System.out.println(data.size());

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveStuffTallyContainerEquipment")
	public ResponseEntity<?> saveStuffTallyContainerEquipment(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestParam("equipment") String equip,
			@RequestParam("vendor") String vendor, @RequestParam("finYear") String finYear,
			@RequestParam("container") String container, @RequestParam("stuffId") String stuffId) {
		if (container.isEmpty() || container == null) {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
		}

		List<ExportStuffTally> result = exportstufftallyrepo.getDataByTallyId(cid, bid, stuffId);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}
		
		result.stream().forEach(c->{
			int srNo = equipmentActivityRepository.getCount(cid, bid, c.getSbTransId(), c.getSbNo(), c.getStuffId());
			EquipmentActivity equipment = new EquipmentActivity();
			equipment.setCompanyId(cid);
			equipment.setBranchId(bid);
			equipment.setApprovedBy(user);
			equipment.setApprovedDate(new Date());
			equipment.setContainerNo(c.getContainerNo());
			equipment.setContainerSize(c.getContainerSize());
			equipment.setContainerType(c.getContainerType());
			equipment.setCreatedBy(user);
			equipment.setCreatedDate(new Date());
			equipment.setDeStuffId(c.getStuffTallyId());
			equipment.setDocRefNo(c.getSbNo());
			equipment.setEditedBy(user);
			equipment.setEditedDate(new Date());
			equipment.setEquipment(equip);
			equipment.setEquipmentNm(equip);
			equipment.setErpDocRefNo(c.getSbTransId());
			equipment.setFinYear(finYear);
			equipment.setProcessId("P00221");
			equipment.setProfitCenterId("N00004");
			equipment.setSrNo(srNo + 1);
			equipment.setSubDocRefNo(c.getStuffId());
			equipment.setVendorId(vendor);
			equipment.setVendorNm(vendor);
			equipment.setStatus("A");
			equipmentActivityRepository.save(equipment);

		});

		
		return new ResponseEntity<>("Data save successfully!!!", HttpStatus.OK);
	}
	
	@PostMapping("/saveStuffTallySbWiseContainerEquipment")
	public ResponseEntity<?> saveStuffTallySbWiseContainerEquipment(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestParam("equipment") String equip,
			@RequestParam("vendor") String vendor, @RequestParam("finYear") String finYear,
			@RequestParam("container") String container, @RequestParam("stuffId") String stuffId) {
		if (container.isEmpty() || container == null) {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
		}

		List<ExportStuffTally> result = exportstufftallyrepo.getDataByTallyId1(cid, bid, stuffId, container);

		if (result.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		}
		
		result.stream().forEach(c->{
			int srNo = equipmentActivityRepository.getCount(cid, bid, c.getSbTransId(), c.getSbNo(), c.getStuffId());
			EquipmentActivity equipment = new EquipmentActivity();
			equipment.setCompanyId(cid);
			equipment.setBranchId(bid);
			equipment.setApprovedBy(user);
			equipment.setApprovedDate(new Date());
			equipment.setContainerNo(c.getContainerNo());
			equipment.setContainerSize(c.getContainerSize());
			equipment.setContainerType(c.getContainerType());
			equipment.setCreatedBy(user);
			equipment.setCreatedDate(new Date());
			equipment.setDeStuffId(c.getStuffTallyId());
			equipment.setDocRefNo(c.getSbNo());
			equipment.setEditedBy(user);
			equipment.setEditedDate(new Date());
			equipment.setEquipment(equip);
			equipment.setEquipmentNm(equip);
			equipment.setErpDocRefNo(c.getSbTransId());
			equipment.setFinYear(finYear);
			equipment.setProcessId("P00222");
			equipment.setProfitCenterId("N00004");
			equipment.setSrNo(srNo + 1);
			equipment.setSubDocRefNo(c.getStuffId());
			equipment.setVendorId(vendor);
			equipment.setVendorNm(vendor);
			equipment.setStatus("A");
			equipmentActivityRepository.save(equipment);

		});

		
		return new ResponseEntity<>("Data save successfully!!!", HttpStatus.OK);
	}

	
	@PostMapping("/deleteContainerEquipmentsForStuffingTally")
	public ResponseEntity<?> deleteContainerEquipmentsForStuffingTally(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("equipment") String equip,
			@RequestParam("vendor") String vendor, @RequestParam("destuff") String destuff,
			@RequestParam("container") String container) {

		int data = equipmentActivityRepository.deleteData2(cid, bid, destuff, equip, vendor, user,
				new Date(), container);

		if (data > 0) {
			return new ResponseEntity<>("Data deleted successfully!!!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Data not found!!!", HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
