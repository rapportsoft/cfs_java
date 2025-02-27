package com.cwms.controller;

import java.math.BigDecimal;
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
import com.cwms.entities.CfinbondCommdtlEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.CfinbondcrgHDR;
import com.cwms.entities.Party;
import com.cwms.service.CfinbondCrgService;
import com.lowagie.text.DocumentException;


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
	 
	 
		@GetMapping("/findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId")
		public ResponseEntity<List<CfinbondcrgDtl>>  findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
				@RequestParam ("companyId") String companyId,
				@RequestParam ("branchId") String branchId,
				@RequestParam ("inBondingId") String inBondingId,
				@RequestParam ("nocTransId") String nocTransId) {
			
			return cfinbondCrgService.findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(companyId, branchId, inBondingId,nocTransId);
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
	 
	 
	   @PostMapping("/approve")
	    public ResponseEntity<?> approveDataOfInCFbondGrid(
	            @RequestParam("companyId") String companyId, 
	            @RequestParam("branchId") String branchId, 
	            @RequestParam("flag") String flag, 
	            @RequestParam("user") String user, 
	            @RequestBody Map<String, Object> requestBody) {
		   return cfinbondCrgService.approveDataOfInCFbondGrid(companyId, branchId, flag, user, requestBody);
	       
	    }
	   
	   @GetMapping("/sum")
	    public ResponseEntity<BigDecimal> getSumOfInBondPackagesForCommodity(
	            @RequestParam String companyId,
	            @RequestParam String branchId,
	            @RequestParam String inBondingId,
	            @RequestParam String cfBondDtlId,
	            @RequestParam String nocTransId) {
	        
	        BigDecimal sumOfInBondPackages = cfinbondCrgService.getSumOfInBondPackagesForCommodity(companyId, branchId, inBondingId, cfBondDtlId, nocTransId);
	        return ResponseEntity.ok(sumOfInBondPackages);
	    }
	   
	   
//	   
//	   
//	   @GetMapping("/generateCustomeInBondPrint")
//	    public ResponseEntity<String> generateSurveyPdf(
//	    		@RequestParam(name = "companyId") String companyId,
//				@RequestParam(name = "branchId") String branchId, @RequestParam(name = "uname") String username,
//				@RequestParam(name = "type") String type, @RequestParam(name = "cname") String companyname,
//				@RequestParam(name = "bname") String branchname, @RequestParam(name = "inBondingId") String inBondingId) throws DocumentException {
//	        try {
//	            // Call the service method and return the response
//	            return cfinbondCrgService.getPrintOfCutomesBondInBondCargo(companyId, branchId, username, type, companyname, branchname, inBondingId);
//	        } catch (Exception e) {
//	            // In case of an error, you can handle the exception and return an appropriate response
//	            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
//	        }
//	    }
	   
	   
	   @GetMapping("/generateCustomeInBondPrint")
	   public ResponseEntity<String> generateSurveyPdf(
	       @RequestParam(name = "companyId") String companyId,
	       @RequestParam(name = "branchId") String branchId, 
	       @RequestParam(name = "uname") String username,
	       @RequestParam(name = "type") String type, 
	       @RequestParam(name = "cname") String companyname,
	       @RequestParam(name = "bname") String branchname, 
	       @RequestParam(name = "inBondingId") String inBondingId) throws DocumentException {

	       try {
	           // Call the service to generate the PDF and return the response
	           return cfinbondCrgService.getPrintOfCutomesBondInBondCargo(
	               companyId, branchId, username, type, companyname, branchname, inBondingId
	           );
	       } catch (Exception e) {
	           // Log the error for debugging
	           System.err.println("Error generating PDF: " + e.getMessage());

	           // Return a 500 status with the error message
	           return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
	       }
	   }

}
