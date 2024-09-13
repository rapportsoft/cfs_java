package com.cwms.controller;

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

import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgHDR;
import com.cwms.entities.Party;
import com.cwms.service.CfinbondCrgService;


@RestController
@RequestMapping("/api/cfinbondcrg")
@CrossOrigin("*")
public class CfinbondCrgController {

	@Autowired
	public CfinbondCrgService  cfinbondCrgService;
	
	@PostMapping("/saveCfInbondCrg")
    public ResponseEntity<?> saveDataOfCfInbondCrg(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return cfinbondCrgService.saveDataOfCfInbondCrg(companyId, branchId, user, flag, requestBody);
    }
	
	 @GetMapping("/search")
	    public List<Cfinbondcrg> getParties(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(name = "partyName", required = false) String partyName) {
	        return cfinbondCrgService.findAllCfinbondCrgIn(companyId, branchId, partyName);
	    }
	 
	 
	 
	 
	 
	 @GetMapping("/searchHdr")
	    public List<Cfinbondcrg> getCfinbondcrgHDR(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam(name = "partyName", required = false) String partyName) {
	        return cfinbondCrgService.findAllCfinbondCrgHDR(companyId, branchId, partyName);
	    }
	 
	 
	 @GetMapping("/getDataByTransIdANDNocIDAndInBondingId")
		public Cfinbondcrg getDataByTransIdANDNocID(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
				@RequestParam("nocTransID") String nocTransID,@RequestParam("inBondingId") String inBondingId,@RequestParam("nocNo") String nocNo){
			return cfinbondCrgService.getCfInbondCrgDataByidOrSearch(companyId, branchId, nocTransID,inBondingId,nocNo);
		}
	 
	 
//	 @GetMapping("/getDataByTransIdANDNocIDAndInBondingHdrId")
//		public Cfinbondcrg getDataByTransIdANDNocIDAndInBondingHdrId(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
//				@RequestParam("nocTransID") String nocTransID,@RequestParam("inBondingId") String inBondingId,@RequestParam("nocNo") String nocNo){
//			return cfinbondCrgService.getFindAllCfinbondCrgHDR(companyId, branchId, nocTransID,inBondingId,nocNo);
//		}
	 
	 
	 
	 
	 
	 @GetMapping("/getDataByTransIdANDNocIDAndInBondingHdrId")
		public List<Cfinbondcrg> getDataByTransIdANDNocIDAndInBondingHdrId(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,
				   @RequestParam(name = "partyName", required = false) String partyName) {
			return cfinbondCrgService.getFindAllCfinbondCrgHDR(companyId, branchId,partyName);
		}
	 
	 
	 
	 
	 
	 
	 @GetMapping("/getDataOfBoeNoForEntryInExbond")
		public Cfinbondcrg getDataOfBoeNoForEntryInExbond(@RequestParam("companyId") String companyId,@RequestParam("branchId") String branchId,@RequestParam("nocTransId") String nocTransId,@RequestParam("nocNo") String nocNo,
				   @RequestParam("boeNo") String boeNo) {
			return cfinbondCrgService.getDataOfBoeNoForEntryInExbond(companyId, branchId,nocTransId,nocNo,boeNo);
		}
}
