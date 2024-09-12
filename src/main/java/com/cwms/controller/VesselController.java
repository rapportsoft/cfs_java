package com.cwms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Vessel;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VesselRepository;

@RestController
@RequestMapping("/vessel")
@CrossOrigin("*")
public class VesselController {

	@Autowired
	private VesselRepository vesselrepo;
	
	@Autowired
	private ProcessNextIdRepository processNextIdRepo;
	
	@GetMapping("/search")
	public List<Vessel> search(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam(name="vname",required = false) String vname){
		
		return vesselrepo.search(cid, bid, vname);
	}
	
	@GetMapping("/getData")
	public Vessel getData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("vid") String vid) {
		
		return vesselrepo.getData(cid, bid, vid);
	}
	
	@PostMapping("/saveData")
	public Vessel saveData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("user") String user,
			@RequestParam("flag") String flag,@RequestBody Vessel vessel) {
		
		if("add".equals(flag)) {
			String holdId = processNextIdRepo.findAuditTrail(cid, bid, "P03202", "2239");

			int lastNextNumericId = Integer.parseInt(holdId.substring(1));

			int nextNumericNextID = lastNextNumericId + 1;
			String HoldNextIdD = String.format("V%05d", nextNumericNextID);
            System.out.println("vessel "+vessel.getOperator());
			vessel.setCompanyId(cid);
			vessel.setVesselId(HoldNextIdD);
			vessel.setBranchId(bid);
			vessel.setStatus("A");
			vessel.setApprovedBy(user);
			vessel.setApprovedDate(new Date());
			
			vesselrepo.save(vessel);
			processNextIdRepo.updateAuditTrail(cid, bid, "P03202", HoldNextIdD, "2239");
			
			return vessel;
		}
		else {
			vessel.setEditedBy(user);
			vessel.setEditedDate(new Date());
			
			vesselrepo.save(vessel);
			return vessel;
		}
		
		
	}
	
	

	@PostMapping("/deleteData")
	public String deleteData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("vid") String vid,@RequestParam("user") String user) {
		
		Vessel vessel = vesselrepo.getData(cid, bid, vid);
		
		if(vessel != null) {
			vessel.setStatus("D");
			vessel.setEditedBy(user);
			vessel.setEditedDate(new Date());
			vesselrepo.save(vessel);
			
			return "success";
		}
		else {
			return "not found";
		}
	}
	
	@GetMapping("/getVesselData")
	public List<Vessel> getVesselData(@RequestParam("cid") String cid,@RequestParam("bid") String bid) {
		
		return vesselrepo.getVesselData(cid, bid);
	}
}
