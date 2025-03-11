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

import com.cwms.entities.AuctionBid;
import com.cwms.entities.AuctionDetail;
import com.cwms.entities.AuctionDuty;
import com.cwms.repository.AuctionBidRepo;
import com.cwms.repository.AuctionCrgRepo;
import com.cwms.repository.AuctionDutyRepo;
import com.cwms.repository.ProcessNextIdRepository;

@RequestMapping("/customValuation")
@RestController
@CrossOrigin("*")
public class CustomValuationController {

	@Autowired
	private AuctionCrgRepo auctioncrgrepo;

	@Autowired
	private AuctionDutyRepo auctiondutyrepo;
	
	@Autowired
	private AuctionBidRepo auctionbidrepo;
	
	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@GetMapping("/getData")
	public ResponseEntity<?> getAuctionData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {

			List<Object[]> data = auctioncrgrepo.getFinalNoticeData(cid, bid, id);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getDataByNoticeId")
	public ResponseEntity<?> getDataByNoticeId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id) {

		try {

			List<Object[]> data = auctioncrgrepo.getDataByFinalNoticeId(cid, bid, id);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Object[]> dutyData = auctiondutyrepo.getDataByNoticeId(cid, bid, id);

			Map<String, Object> result = new HashMap<>();
			result.put("auctionData", data);
			result.put("auctionDuty", dutyData);

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveCustomValuation")
	public ResponseEntity<?> saveCustomValuation(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody AuctionDetail detail) {

		try {

			if (detail == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			AuctionDetail existing = auctioncrgrepo.getDataById(cid, bid, detail.getNoticeId());

			if (existing == null) {
				return new ResponseEntity<>("Auction data not found", HttpStatus.CONFLICT);
			}

			existing.setCvCreatedBy(user);
			existing.setCvCreatedDate(new Date());
			existing.setCvStatus("A");
			existing.setCvApprovedBy(user);
			existing.setCvApprovedDate(new Date());
			existing.setAccessableValueAsValuation(detail.getAccessableValueAsValuation());
			existing.setMop(detail.getMop());
			existing.setRateOfDuty(detail.getRateOfDuty());
			existing.setAmtOfDuty(detail.getAmtOfDuty());
			existing.setAssessiableAvailable(detail.getAssessiableAvailable());
			existing.setDuty(detail.getDuty());
			existing.setFairValueOfGoods(detail.getFairValueOfGoods());
			existing.setPmv(detail.getPmv());
			existing.setShift(detail.getShift());
			existing.setFileNo(detail.getFileNo());
			existing.setLotNo(detail.getLotNo());
			existing.setAuctionStatus(detail.getAuctionStatus());
			existing.setHsnNo(detail.getHsnNo());
			existing.setFileStatus(detail.getFileStatus());

			auctioncrgrepo.save(existing);

			List<Object[]> data = auctioncrgrepo.getDataByFinalNoticeId(cid, bid, detail.getNoticeId());

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Object[]> dutyData = auctiondutyrepo.getDataByNoticeId(cid, bid, detail.getNoticeId());

			Map<String, Object> result = new HashMap<>();
			result.put("auctionData", data);
			result.put("auctionDuty", dutyData);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveCustomValuationAsPerStatus")
	public ResponseEntity<?> saveCustomValuationAsPerStatus(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestParam("status") String status,
			@RequestBody AuctionDetail detail) {

		try {

			if (detail == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			AuctionDetail existing = auctioncrgrepo.getDataById(cid, bid, detail.getNoticeId());

			if (existing == null) {
				return new ResponseEntity<>("Auction data not found", HttpStatus.CONFLICT);
			}

			if ("GST_PERCENT".equals(status)) {
				existing.setTcs(detail.getTcs());
				existing.setCgst(detail.getCgst());
				existing.setSgst(detail.getSgst());
				existing.setIgst(detail.getIgst());
				existing.setGstApprovedDate(new Date());
			}
			if ("AUCTION_CMD".equals(status)) {
				existing.setAuctionType(detail.getAuctionType());
				existing.setCmdApprovedDate(new Date());
			}
			if ("HIGH_BID".equals(status)) {
				existing.setBidAmt(detail.getBidAmt());
				existing.setBidamtApprovedDate(new Date());
			}
			if ("STC_CONFIRMATION".equals(status)) {
				existing.setStcStatus(detail.getStcStatus());
				existing.setStcApprovedDate(new Date());
			}
			if ("CUSTOM_APPROVE".equals(status)) {
				existing.setAcceptRejectStatus(detail.getAcceptRejectStatus());
				existing.setCustomeAcceptRejectDate(new Date());
			}
			if ("CUSTOM_OOC".equals(status)) {
				existing.setCustomeOutOfChargeDate(detail.getCustomeOutOfChargeDate());
			}

			auctioncrgrepo.save(existing);

			List<Object[]> data = auctioncrgrepo.getDataByFinalNoticeId(cid, bid, detail.getNoticeId());

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Object[]> dutyData = auctiondutyrepo.getDataByNoticeId(cid, bid, detail.getNoticeId());

			Map<String, Object> result = new HashMap<>();
			result.put("auctionData", data);
			result.put("auctionDuty", dutyData);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveDutyUpdation")
	public ResponseEntity<?> saveDutyUpdation(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("noticeId") String noticeId,
			@RequestBody List<AuctionDuty> detail) {

		try {

			if (detail == null || detail.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			AuctionDetail existing = auctioncrgrepo.getDataById(cid, bid, noticeId);

			if (existing == null) {
				return new ResponseEntity<>("Auction data not found", HttpStatus.CONFLICT);
			}

			detail.stream().forEach(d -> {
				AuctionDuty existingDuty = auctiondutyrepo.getSingleData(cid, bid, noticeId, d.getDutyType());

				if(existingDuty == null) {
					d.setCompanyId(cid);
					d.setBranchId(bid);
					d.setNoticeId(noticeId);
					d.setNoticeAmndNo(existing.getNoticeAmndNo());
					d.setDutyType(d.getDutyType());
					d.setFinalNoticeId(noticeId);
					d.setIgmTransId(existing.getIgmTransId());
					d.setIgmNo(existing.getIgmNo());
					d.setIgmLineNo(existing.getIgmLineNo());
					d.setDutyRate(d.getDutyRate());
					d.setDutyValue(d.getDutyValue());
					d.setDutyCreatedDate(new Date());
					d.setDutyApprovedBy(user);
					d.setStatus("A");
					
					auctiondutyrepo.save(d);
				}
				else {
					existingDuty.setDutyRate(d.getDutyRate());
					existingDuty.setDutyValue(d.getDutyValue());
					
					auctiondutyrepo.save(existingDuty);
				}
			});

			List<Object[]> data = auctioncrgrepo.getDataByFinalNoticeId(cid, bid, noticeId);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			List<Object[]> dutyData = auctiondutyrepo.getDataByNoticeId(cid, bid, noticeId);

			Map<String, Object> result = new HashMap<>();
			result.put("auctionData", data);
			result.put("auctionDuty", dutyData);

			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	// Auction Bid --------------------------------------------
	
		@GetMapping("/getBeforeSaveDataForAuctionBid")
		public ResponseEntity<?> getBeforeSaveDataForAuctionBid(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
				@RequestParam(name="val",required = false) String val) {
			
			try {
				
				List<String> data = auctioncrgrepo.getBeforeSaveDataForAuctionBid(cid, bid, val);
				
				return new ResponseEntity<>(data,HttpStatus.OK);
				
			} catch (Exception e) {
				e.printStackTrace();

				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/getSelectBeforeSaveDataForAuctionBid")
		public ResponseEntity<?> getSelectBeforeSaveDataForAuctionBid(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
				@RequestParam(name="val",required = false) String val) {
			
			try {
				
				Object data = auctioncrgrepo.getSelectedBeforeSaveDataForAuctionBid(cid, bid, val);
				
				if(data == null) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}
				
				return new ResponseEntity<>(data,HttpStatus.OK);
				
			} catch (Exception e) {
				e.printStackTrace();

				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@PostMapping("/saveAuctionBidData")
		public ResponseEntity<?> saveAuctionBidData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
				@RequestParam("user") String user,@RequestBody AuctionBid data){
			
			try {
				
				if(data == null) {
					return new ResponseEntity<>("Auction data not found",HttpStatus.CONFLICT);
				}
				
				if(data.getBidId() == null || data.getBidId().isEmpty()) {
					AuctionDetail detail = auctioncrgrepo.getDataById(cid, bid, data.getNoticeId());
					
					if(detail == null) {
						return new ResponseEntity<>("Auction detail data not found",HttpStatus.CONFLICT);
					}
					
					String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05124", "2024");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String HoldNextIdD1 = String.format("AUBD%06d", nextNumericNextID1);
					
					data.setCompanyId(cid);
					data.setBranchId(bid);
					data.setAuctionItemNo("1");
					data.setBidId(HoldNextIdD1);
					data.setBidDate(new Date());
					data.setStatus("A");
					data.setCreatedBy(user);
					data.setCreatedDate(new Date());
					data.setApprovedBy(user);
					data.setApprovedDate(new Date());
					data.setProfitcentreId(detail.getProfitcentreId());
					data.setIgmTransId(detail.getIgmTransId());
					data.setIgmTransDate(detail.getIgmTransDate());
					data.setPackageType(detail.getTypeOfPackage());
					
					auctionbidrepo.save(data);
					
					processnextidrepo.updateAuditTrail(cid, bid, "P05124", HoldNextIdD1, "2024");
					
					detail.setBidId(HoldNextIdD1);
					detail.setBidDate(new Date());
					detail.setBidamtApprovedDate(new Date());
					
					auctioncrgrepo.save(detail);
					
					AuctionBid auc = auctionbidrepo.getDataById1(cid, bid, HoldNextIdD1);
					
					return new ResponseEntity<>(auc, HttpStatus.OK);


				}
				else {
					
					AuctionBid existingData = auctionbidrepo.getDataById(cid, bid, data.getBidId());
					
					if(existingData != null) {
						existingData.setShift(data.getShift());
						existingData.setCfsDues(data.getCfsDues());
						existingData.setReservePrice(data.getReservePrice());
						existingData.setEditedBy(user);
						existingData.setEditedDate(new Date());
						
						auctionbidrepo.save(existingData);
					}
					
					AuctionBid auc = auctionbidrepo.getDataById1(cid, bid, data.getBidId());

					
					return new ResponseEntity<>(auc, HttpStatus.OK);

					
				}
				
			} catch (Exception e) {
				e.printStackTrace();

				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		@GetMapping("/getBidAfterSearchData")
		public ResponseEntity<?> getBidAfterSearchData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
				@RequestParam(name="id", required = false) String id){
			try {
				
				List<Object[]> data = auctionbidrepo.getBidAfterSearchData(cid, bid, id);
				
				if(data.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}
				else {
					return new ResponseEntity<>(data, HttpStatus.OK);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();

				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		@GetMapping("/getBidAfterSelectSearchData")
		public ResponseEntity<?> getBidAfterSelectSearchData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
				@RequestParam("id") String id){
			try {
				
				AuctionBid data = auctionbidrepo.getDataById1(cid, bid, id);
				
				if(data == null) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}
				else {
					return new ResponseEntity<>(data, HttpStatus.OK);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();

				return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
}
