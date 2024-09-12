package com.cwms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.Profitcentre;
import com.cwms.repository.ProfitcentreRepository;

@Service
public class ProfitcentreService {

	@Autowired
    private ProfitcentreRepository profitcentreRepository;
	
	
	@Autowired
    private ProcessNextIdService processNextIdService;


    public List<Profitcentre> findAll(String companyId,String branchId, String vessel ,String jo,String contManot,String desc) {
        return profitcentreRepository.search1(companyId,branchId,vessel,jo,contManot,desc);
    }


    public Profitcentre getDataById(String companyId,String branchId,String profitcentreId) {
    	

        return profitcentreRepository.getAllDataByID(companyId,branchId,profitcentreId);
    }
    
    
public int deleteProfitCenterById(String companyId,String branchId,String profitcentreId) {
        return profitcentreRepository.updateStatusToD(companyId,branchId,profitcentreId);
    }

    
    public ResponseEntity<?> saveProfitCenter( String cid,  String bid,
			 String flag, String user,  Profitcentre party) {
		System.out.println("Party " + party);

		if ("add".equals(flag)) {

			if (party != null) {

				
				String holdId1 = processNextIdService.autoIncrementNextProfitCenterId(cid, bid);

		

				party.setCompanyId(cid);
				party.setBranchId(bid);
				party.setProfitcentreId(holdId1);
				
				party.setStatus("A");
				party.setCreatedBy(user);
				party.setCreatedDate(new Date());

				party.setApprovedBy(user);
				party.setApprovedDate(new Date());

				profitcentreRepository.save(party);
				

				return new ResponseEntity<>(party, HttpStatus.OK);
			} else {

				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		} else {

			if (party != null) {
			

				party.setEditedBy(user);
				party.setEditedDate(new Date());

				profitcentreRepository.save(party);

				return new ResponseEntity<>(party, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Party data not found.", HttpStatus.BAD_REQUEST);
			}

		}

	}
    
    
    public List<Map<String, String>> getProfitcenters(String companyId, String branchId) { 
		 
		 return convertToValueLabelList(profitcentreRepository.getProfitcenters(companyId,branchId));	         
	    }
    
    private List<Map<String, String>> convertToValueLabelList(List<Profitcentre> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", obj.getProfitcentreId());
	        map.put("label", obj.getProfitcentreDesc());
	        return map;
	    }).collect(Collectors.toList());
	}
}
