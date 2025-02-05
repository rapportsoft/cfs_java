package com.cwms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.Auction;
import com.cwms.entities.AuctionDetail;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.ContainerDTO;
import com.cwms.entities.GateOut;
import com.cwms.service.AuctionService;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/api/auction")
@CrossOrigin
public class AuctionController {

	@Autowired
	private AuctionService auctionService;
	
	
	@GetMapping("/getIgmTransId")
	public ResponseEntity<List<Object[]>> getIgmTrannsId(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("value") String value
			) {
		
		return auctionService.getDistinctIgmTransId(companyId, branchId, value);
		 
	}
	
	@GetMapping("/getIgmTransIdSecondNotice")
	public ResponseEntity<List<Object[]>> getIgmTransIdSecondNotice(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("value") String value
			) {
		
		return auctionService.getDistinctIgmTransIdSecondNotice(companyId, branchId, value);
		 
	}
	
	@GetMapping("/getIgmTransIdFinalNotice")
	public ResponseEntity<List<Object[]>> getIgmTransIdFinalNotice(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("value") String value
			) {
		
		return auctionService.findDistinctIGMDetailsForFinalNotice(companyId, branchId, value);
		 
	}
	
	
	@GetMapping("/getIgmTransIdDetails")
	public ResponseEntity<List<Object[]>> getIgmTransIdDetails(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("igmLineNo") String igmLineNo ,
			@RequestParam ("igmNo") String igmNo ,
			@RequestParam ("igmTransId") String igmTransId
			) {
		
		return auctionService.getIgmTransIdDetails(companyId, branchId, igmLineNo,igmNo,igmTransId);
		 
	}
	
	
	
	
	
	
	@GetMapping("/getContainersDetails")
	public ResponseEntity<List<ContainerDTO>> getContainersDetails(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("igmNo") String igmNo ,
			@RequestParam ("igmTransId") String igmTransId,
			@RequestParam ("igmLineNo") String igmLineNo 
			) {
		
		return auctionService.getContainerDetails(companyId, branchId,igmNo,igmTransId,igmLineNo);
		 
	}
	
	
	
	
	
	
	
	
	@GetMapping("/getContainerDetailsAfterSave")
	public ResponseEntity<List<Auction>> getContainerDetailsAfterSave(
			@RequestParam ("companyId") String companyId ,
			@RequestParam ("branchId") String branchId ,
			@RequestParam ("noticeId") String noticeId ,
			@RequestParam ("ammendNo") String ammendNo
			) {
		
		return auctionService.getContainerDetailsAfterSave(companyId, branchId,noticeId,ammendNo);
		 
	}
	
	
	
	
	
	
	@PostMapping("/saveDataOfAuctionNotice")
    public ResponseEntity<?> saveDataOfAuctionNotice(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return auctionService.saveDataOfAuctionNotice(companyId, branchId, user, flag, requestBody);
    }
	
	
	
	
	@PostMapping("/saveDataOfAuctionSecondNotice")
    public ResponseEntity<?> saveDataOfAuctionSecondNotice(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return auctionService.saveDataOfAuctionSecondNotice(companyId, branchId, user, flag, requestBody);
    }
	
	
	
	
	
	
	
	
	@PostMapping("/saveDataOfAuctionFinalNotice")
    public ResponseEntity<?> saveDataOfAuctionFinalNotice(
            @RequestParam("companyId") String companyId,
            @RequestParam("branchId") String branchId,
            @RequestParam("user") String user,
            @RequestParam("flag") String flag,
            @RequestBody Map<String, Object> requestBody) {
  
		return auctionService.saveDataOfAuctionFinalNotice(companyId, branchId, user, flag, requestBody);
    }
	
	
	
	 @GetMapping("/findAuctionDetails")
		public ResponseEntity<List<AuctionDetail>> getCfbondnocData(@RequestParam String companyId,
				@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
			List<AuctionDetail> data = auctionService.findAuctionDetails(companyId, branchId, partyName);
			return ResponseEntity.ok(data);
		}
	 
	 @GetMapping("/findAuctionDetailsSecond")
		public ResponseEntity<List<AuctionDetail>> findAuctionDetailsSecond(@RequestParam String companyId,
				@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
			List<AuctionDetail> data = auctionService.findAuctionDetailsSecond(companyId, branchId, partyName);
			return ResponseEntity.ok(data);
		}
	 
	 @GetMapping("/findAuctionDetailsFinal")
		public ResponseEntity<List<AuctionDetail>> findAuctionDetailsFinal(@RequestParam String companyId,
				@RequestParam String branchId, @RequestParam(name = "partyName", required = false) String partyName) {
			List<AuctionDetail> data = auctionService.findAuctionDetailsFinal(companyId, branchId, partyName);
			return ResponseEntity.ok(data);
		}
	
	  @GetMapping("/getSavedDataOfHeader")
	    public ResponseEntity<AuctionDetail> getAllListOfGatePass(
	            @RequestParam("companyId") String companyId,
	            @RequestParam("branchId") String branchId,
	            @RequestParam("noticeId") String noticeId,
	            @RequestParam("noticeAmndNo") String noticeAmndNo,
	            @RequestParam("igmNo") String igmNo,
	            @RequestParam("igmLineNo") String igmLineNo,
	            @RequestParam("igmTransId") String igmTransId) {
	        AuctionDetail gatePasses = auctionService.getAuctionOnScreenData(companyId, branchId, noticeId,noticeAmndNo,igmNo, igmLineNo,igmTransId);
	        return ResponseEntity.ok(gatePasses);
	    }
	  
	  
	  
	  
	  
	  
	  
	  
	  @GetMapping("/getPrintOfFirstNotice")
	   public ResponseEntity<String> getPrintOfFirstNotice(
	       @RequestParam(name = "companyId") String companyId,
	       @RequestParam(name = "branchId") String branchId, 
	       @RequestParam(name = "uname") String username,
	       @RequestParam(name = "type") String type, 
	       @RequestParam(name = "cname") String companyname,
	       @RequestParam(name = "bname") String branchname, 
	       @RequestParam(name = "igmNo") String igmNo,
	       @RequestParam(name = "igmLineNo") String igmLineNo,
	       @RequestParam(name = "noticeId") String noticeId) throws DocumentException {

	       try {
	           // Call the service to generate the PDF and return the response
	           return auctionService.getPrintOfFirstNotice(
	               companyId, branchId, username, type, companyname, branchname, igmNo,igmLineNo,noticeId
	           );
	       } catch (Exception e) {
	           // Log the error for debugging
	           System.err.println("Error generating PDF: " + e.getMessage());

	           // Return a 500 status with the error message
	           return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
	       }
	   }
	  
	  @GetMapping("/getPrintOfFinalNotice")
	   public ResponseEntity<String> getPrintOfFinalNotice(
	       @RequestParam(name = "companyId") String companyId,
	       @RequestParam(name = "branchId") String branchId, 
	       @RequestParam(name = "uname") String username,
	       @RequestParam(name = "type") String type, 
	       @RequestParam(name = "cname") String companyname,
	       @RequestParam(name = "bname") String branchname, 
	       @RequestParam(name = "igmNo") String igmNo,
	       @RequestParam(name = "igmLineNo") String igmLineNo,
	       @RequestParam(name = "noticeId") String noticeId) throws DocumentException {

	       try {
	           // Call the service to generate the PDF and return the response
	           return auctionService.getPrintOfFinalNotice(
	               companyId, branchId, username, type, companyname, branchname, igmNo,igmLineNo,noticeId
	           );
	       } catch (Exception e) {
	           // Log the error for debugging
	           System.err.println("Error generating PDF: " + e.getMessage());

	           // Return a 500 status with the error message
	           return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
	       }
	   }
	  
	  
	  @GetMapping("/getPrintOfSecondNotice")
	   public ResponseEntity<String> getPrintOfSecondNotice(
	       @RequestParam(name = "companyId") String companyId,
	       @RequestParam(name = "branchId") String branchId, 
	       @RequestParam(name = "uname") String username,
	       @RequestParam(name = "type") String type, 
	       @RequestParam(name = "cname") String companyname,
	       @RequestParam(name = "bname") String branchname, 
	       @RequestParam(name = "igmNo") String igmNo,
	       @RequestParam(name = "igmLineNo") String igmLineNo,
	       @RequestParam(name = "noticeId") String noticeId) throws DocumentException {

	       try {
	           // Call the service to generate the PDF and return the response
	           return auctionService.getPrintOfSecondNotice(
	               companyId, branchId, username, type, companyname, branchname, igmNo,igmLineNo,noticeId
	           );
	       } catch (Exception e) {
	           // Log the error for debugging
	           System.err.println("Error generating PDF: " + e.getMessage());

	           // Return a 500 status with the error message
	           return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
	       }
	   }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  @GetMapping("/mainAuctionSearch")
	    public ResponseEntity<?> getDataForMainAuctionSearch(
	    		@RequestParam ("companyId") String companyId,
	    		@RequestParam ("branchId") String branchId,
	    		@RequestParam (value ="igmTransId", required =false ) String igmTransId ,
	    		@RequestParam(value ="igmNo",required = false ) String igmNo,
	    		@RequestParam(value ="igmLineNo",required = false) String igmLineNo,
	    		@RequestParam(value ="blNo",required = false ) String blNo
	    		) {
			return auctionService.getDataForMainAuctionSearch(companyId, branchId, igmTransId, igmNo, igmLineNo,blNo);
		}
}
