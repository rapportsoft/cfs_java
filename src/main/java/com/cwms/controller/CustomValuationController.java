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

import com.cwms.entities.AuctionDetail;
import com.cwms.entities.AuctionDuty;
import com.cwms.repository.AuctionCrgRepo;
import com.cwms.repository.AuctionDutyRepo;

@RequestMapping("/customValuation")
@RestController
@CrossOrigin("*")
public class CustomValuationController {

	@Autowired
	private AuctionCrgRepo auctioncrgrepo;

	@Autowired
	private AuctionDutyRepo auctiondutyrepo;

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
}
