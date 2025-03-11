package com.cwms.controller;

import java.util.Date;
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
import com.cwms.entities.AuctionRecording;
import com.cwms.repository.AuctionBidRepo;
import com.cwms.repository.AuctionCrgRepo;
import com.cwms.repository.AuctionDutyRepo;
import com.cwms.repository.AuctionRecordingRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auctionRecording")
@CrossOrigin("*")
public class AuctionRecordingController {

	@Autowired
	private AuctionCrgRepo auctioncrgrepo;

	@Autowired
	private AuctionDutyRepo auctiondutyrepo;

	@Autowired
	private AuctionBidRepo auctionbidrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private AuctionRecordingRepo auctionrecordingrepo;

	@GetMapping("/getBeforeSaveData")
	public ResponseEntity<?> getBeforeSaveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "id", required = false) String id) {

		try {

			List<String> data = auctionbidrepo.getDataForBeforeSaverecordingData(cid, bid, id);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getBeforeSaveSelectedData")
	public ResponseEntity<?> getBeforeSaveSelectedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id) {

		try {

			AuctionBid data = auctionbidrepo.getDataById1(cid, bid, id);

			if (data == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/saveRecordingData")
	public ResponseEntity<?> saveRecordingData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data) {

		try {

			ObjectMapper mapper = new ObjectMapper();

			AuctionRecording recording = mapper.readValue(mapper.writeValueAsString(data.get("recording")),
					AuctionRecording.class);

			if (recording == null) {
				return new ResponseEntity<>("Auction recording data not found", HttpStatus.CONFLICT);
			}

			List<AuctionRecording> bidderData = mapper.readValue(mapper.writeValueAsString(data.get("bidderData")),
					new TypeReference<List<AuctionRecording>>() {
					});

			if (bidderData.isEmpty()) {
				return new ResponseEntity<>("Bidder data not found", HttpStatus.CONFLICT);
			}

			AuctionBid bidData = auctionbidrepo.getDataById(cid, bid, recording.getBidId());

			if (bidData == null) {
				return new ResponseEntity<>("Bid data not found", HttpStatus.CONFLICT);
			}

			if (recording.getAuctionNo() == null || recording.getAuctionNo().isEmpty()) {
				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05125", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("ACCR%06d", nextNumericNextID1);

				bidderData.stream().forEach(b -> {

					int sr = auctionrecordingrepo.getSrNo(cid, bid, HoldNextIdD1) + 1;

					b.setCompanyId(cid);
					b.setBranchId(bid);
					b.setStatus("A");
					b.setBidId(bidData.getBidId());
					b.setSrNo(sr);
					b.setAuctionNo(HoldNextIdD1);
					b.setAuctionItemNo(bidData.getAuctionItemNo());
					b.setAuctionDate(new Date());
					b.setBidDate(bidData.getBidDate());
					b.setProfitcentreId(bidData.getProfitcentreId());
					b.setIgmTransId(bidData.getIgmTransId());
					b.setIgmTransDate(bidData.getIgmTransDate());
					b.setIgmNo(bidData.getIgmNo());
					b.setIgmLineNo(bidData.getIgmLineNo());
					b.setIgmDate(bidData.getIgmDate());
					b.setShift(recording.getShift());
					b.setCommodityDescription(bidData.getCommodityDescription());
					b.setActualNoOfPackages(bidData.getActualNoOfPackages());
					b.setGrossWt(bidData.getGrossWt());
					b.setUom(bidData.getUom());
					b.setCfsDues(bidData.getCfsDues());
					b.setDuty(bidData.getDuty());
					b.setReservePrice(bidData.getReservePrice());
					b.setCreatedBy(user);
					b.setCreatedDate(new Date());
					b.setStatus("A");
					b.setApprovedBy(user);
					b.setApprovedDate(new Date());

					auctionrecordingrepo.save(b);

					processnextidrepo.updateAuditTrail(cid, bid, "P05125", HoldNextIdD1, "2024");

				});

				List<AuctionRecording> result = auctionrecordingrepo.getDataByAuctionId(cid, bid, HoldNextIdD1);

				return new ResponseEntity<>(result, HttpStatus.OK);

			} else {

				bidderData.stream().forEach(b -> {

					if (b.getAuctionNo() == null || b.getAuctionNo().isEmpty()) {
						int sr = auctionrecordingrepo.getSrNo(cid, bid, recording.getAuctionNo()) + 1;

						b.setCompanyId(cid);
						b.setBranchId(bid);
						b.setStatus("A");
						b.setBidId(bidData.getBidId());
						b.setSrNo(sr);
						b.setAuctionNo(recording.getAuctionNo());
						b.setAuctionItemNo(bidData.getAuctionItemNo());
						b.setAuctionDate(new Date());
						b.setBidDate(bidData.getBidDate());
						b.setProfitcentreId(bidData.getProfitcentreId());
						b.setIgmTransId(bidData.getIgmTransId());
						b.setIgmTransDate(bidData.getIgmTransDate());
						b.setIgmNo(bidData.getIgmNo());
						b.setIgmLineNo(bidData.getIgmLineNo());
						b.setIgmDate(bidData.getIgmDate());
						b.setShift(recording.getShift());
						b.setCommodityDescription(bidData.getCommodityDescription());
						b.setActualNoOfPackages(bidData.getActualNoOfPackages());
						b.setGrossWt(bidData.getGrossWt());
						b.setUom(bidData.getUom());
						b.setCfsDues(bidData.getCfsDues());
						b.setDuty(bidData.getDuty());
						b.setReservePrice(bidData.getReservePrice());
						b.setCreatedBy(user);
						b.setCreatedDate(new Date());
						b.setStatus("A");
						b.setApprovedBy(user);
						b.setApprovedDate(new Date());

						auctionrecordingrepo.save(b);
					} else {
						AuctionRecording existing = auctionrecordingrepo.getDataByAuctionIdAndSrNo(cid, bid,
								recording.getAuctionNo(), b.getSrNo());

						if (existing != null) {
							existing.setEditedBy(user);
							existing.setEditedDate(new Date());
							existing.setShift(recording.getShift());
							existing.setBidderName(b.getBidderName());
							existing.setBidderAddress1(b.getBidderAddress1());
							existing.setBidderAddress2(b.getBidderAddress2());
							existing.setBidderAddress3(b.getBidderAddress3());
							existing.setStc(b.getStc());
							existing.setBidderAmount(b.getBidderAmount());
							existing.setAmountPaid(b.getAmountPaid());
							existing.setConfirmDate(b.getConfirmDate());

							auctionrecordingrepo.save(existing);
						}
					}

				});

				List<AuctionRecording> result = auctionrecordingrepo.getDataByAuctionId(cid, bid, recording.getAuctionNo());

				return new ResponseEntity<>(result, HttpStatus.OK);

			}


		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@GetMapping("/searchAuctionRecordingData")
	public ResponseEntity<?> searchAuctionRecordingData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="id",required = false) String id){
		try {
			
			List<Object[]> data = auctionrecordingrepo.searchAuctionRecordingData(cid, bid, id);
			
			return new ResponseEntity<>(data, HttpStatus.OK);

			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAuctionRecordingData")
	public ResponseEntity<?> getAuctionRecordingData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("id") String id){
		try {
			
			List<AuctionRecording> result = auctionrecordingrepo.getDataByAuctionId(cid, bid, id);
			
			if(result == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);

			}

			return new ResponseEntity<>(result, HttpStatus.OK);

			
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
