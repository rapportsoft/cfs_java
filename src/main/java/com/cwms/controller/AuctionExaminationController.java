package com.cwms.controller;

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

import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.ImportInventoryRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/auctionExamination")
public class AuctionExaminationController {

	@Autowired
	private ImportInventoryRepository importinvrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@GetMapping("/getAuctionContainerWiseData")
	public ResponseEntity<?> getAuctionContainerWiseData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid) {

		try {
			List<Object[]> data = cfigmcnrepo.getAuctionContainerWiseData(cid, bid);

			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping("/saveContainerTypeData")
	public ResponseEntity<?> saveContainerTypeData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestBody List<Object[]> con) {

		try {
			
			if(!con.isEmpty()) {
				
				
				con.stream().forEach(c->{
					int update = cfigmcnrepo.updateAuctionExaminationStatus(cid, bid, String.valueOf(c[0]), String.valueOf(c[1]), 
							String.valueOf(c[2]), String.valueOf(c[3]), 'B');
				});
				
				List<Object[]> data = cfigmcnrepo.getAuctionContainerWiseData(cid, bid);

				return new ResponseEntity<>(data, HttpStatus.OK);
			}
			else {
				List<Object[]> data = cfigmcnrepo.getAuctionContainerWiseData(cid, bid);

				return new ResponseEntity<>(data, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAuctionCargoWiseData")
	public ResponseEntity<?> getAuctionCargoWiseData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid) {

		try {
			List<Object[]> data = cfigmcnrepo.getAuctionCargoWiseData(cid, bid);

			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}
	
	@PostMapping("/saveCargoTypeData")
	public ResponseEntity<?> saveCargoTypeData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestBody List<Object[]> con) {

		try {
			
			if(!con.isEmpty()) {
				
				
				con.stream().forEach(c->{
					int update = cfigmcnrepo.updateAuctionExaminationStatus1(cid, bid, String.valueOf(c[0]), String.valueOf(c[1]), 
							String.valueOf(c[2]), "B");
				});
				
				List<Object[]> data = cfigmcnrepo.getAuctionCargoWiseData(cid, bid);

				return new ResponseEntity<>(data, HttpStatus.OK);
			}
			else {
				List<Object[]> data = cfigmcnrepo.getAuctionCargoWiseData(cid, bid);

				return new ResponseEntity<>(data, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
