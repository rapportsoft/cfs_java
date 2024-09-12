package com.cwms.controller;

import java.util.Date;
import java.util.List;

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

import com.cwms.entities.Voyage;
import com.cwms.repository.PortRepository;
import com.cwms.repository.VoyageRepository;

@RestController
@RequestMapping("/voyage")
@CrossOrigin("*")
public class VoyageController {

	@Autowired
	private VoyageRepository voyagerepo;

	@Autowired
	private PortRepository portrepository;

	@GetMapping("/search")
	public List<Object[]> search(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("vesselId") String vesselId,
			@RequestParam(name = "voyageNo", required = false) String voyageId) {

		return voyagerepo.search(cid, bid, vesselId, voyageId);
	}

	@GetMapping("/getPortData")
	public List<Object[]> getPortData(@RequestParam("cid") String cid, @RequestParam("bid") String bid) {
		return portrepository.getData(cid, bid);
	}

	@PostMapping("/saveData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, 
	                                  @RequestParam("bid") String bid,
	                                  @RequestParam("user") String user, 
	                                  @RequestParam("flag") String flag, 
	                                  @RequestBody Voyage voyage) {
	    if (voyage == null) {
	        return new ResponseEntity<>("Data not exist", HttpStatus.BAD_REQUEST);
	    }
	    
	    if ("add".equals(flag)) {
	        Boolean exist = voyagerepo.checkDuplicate(cid, bid, voyage.getVesselCode(), voyage.getVoyageNo());
	        if (exist) {
	            return new ResponseEntity<>("Duplicate voyage no.", HttpStatus.BAD_REQUEST);
	        }
	        
	        voyage.setCompanyId(cid);
	        voyage.setBranchId(bid);
	        voyage.setStatus("A");
	        voyage.setCreatedBy(user);
	        voyage.setCreatedDate(new Date());
	        voyage.setApprovedDate(new Date());
	        voyage.setApprovedBy(user);
	        
	        voyagerepo.save(voyage);
	        
	        Voyage updated = voyagerepo.getVoyageData1(cid, bid, voyage.getVesselCode(), voyage.getVoyageNo());
	        
	        return new ResponseEntity<>(updated, HttpStatus.OK);
	    } else {
	        Boolean exist = voyagerepo.checkDuplicate1(cid, bid, voyage.getVesselCode(), voyage.getVoyageNo(), voyage.getSrNo());
	        if (exist) {
	            return new ResponseEntity<>("Duplicate voyage no.", HttpStatus.BAD_REQUEST);
	        }
	        
	        Voyage v = voyagerepo.getVoyageData(cid, bid, voyage.getVesselCode(), voyage.getSrNo());
	        if (v == null) {
	            return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
	        }
	        
	        int update = voyagerepo.updateRecord(cid, bid, voyage.getVesselCode(), voyage.getSrNo(),
	                voyage.getVoyageNo(), voyage.getPol(), voyage.getPod(), voyage.getViaNo(), voyage.getEta(),
	                voyage.getGateOpenDate(), voyage.getIgmNo(), voyage.getIgmDate(), voyage.getRotationNo(),
	                voyage.getRotationNoDate(), voyage.getAtb(), voyage.getViaNoDate(), voyage.getAtd(), 
	                voyage.getCutOffDateTime(), user, new Date());
	        
	        if (update > 0) {
	            
	            return new ResponseEntity<>(voyage, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Update failed", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	}
	
	@GetMapping("/getVoyageData")
	public Voyage getData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,@RequestParam("voyage") String voyage,
			@RequestParam("sr") int sr) {
		return voyagerepo.getVoyageData(cid, bid, voyage, sr);
	}
	
	@PostMapping("/deleteVoyage")
	public String deleteRecord(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("vesselId") String vesselId,@RequestParam("sr") int sr) {
		Voyage v = voyagerepo.getVoyageData(cid, bid, vesselId, sr);
		
		if(v != null) {
			v.setStatus("D");
			voyagerepo.save(v);
			return "success";
		}
		else {
			return "Data not found";
		}
	}

}
