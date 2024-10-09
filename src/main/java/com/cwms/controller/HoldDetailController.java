package com.cwms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.server.ResponseStatusException;

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.HoldDetails;
import com.cwms.entities.HoldDto;
import com.cwms.entities.ImportInventory;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.HoldDetailsRepository;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.ProfitcentreRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/holdDetail")
public class HoldDetailController {

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private HoldDetailsRepository holdRepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private ProfitcentreRepository profitcenterrepo;

	@Autowired
	private ImportInventoryRepository importinventoryrepo;

//	@GetMapping("/search")
//	public ResponseEntity<?> search(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam(name = "igm", required = false) String igm,
//			@RequestParam(name = "item", required = false) String item,
//			@RequestParam(name = "container", required = false) String container) {
//
//		if (container == null || container.isEmpty()) {
//			
//			Cfigmcrg crg = cfigmcrgrepo.getData4(cid, bid, igm, item);
//			
//			if (crg == null) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//			
//			CFIgm igmData = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());
//
//			if (igmData == null) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//			
//			List<Cfigmcn> cn = cfigmcnrepo.getHoldSearch1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(),
//					crg.getIgmLineNo());
//
//			if (cn.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			Map<String, Object> result = new HashMap<>();
//			result.put("igm", igmData);
//			result.put("igmCrg", crg);
//			result.put("con", cn);
//
//			return new ResponseEntity<>(result, HttpStatus.OK);
//
//		} else {
//			List<Cfigmcn> data = cfigmcnrepo.getHoldSearch(cid, bid, container);
//
//			if (data.isEmpty()) {
//				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//			}
//
//			if (data.get(0).getNoOfItem() > 1) {
//				Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, data.get(0).getIgmTransId(), data.get(0).getIgmNo(),
//						data.get(0).getIgmLineNo());
//
//				if (crg == null) {
//					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//				}
//
//				List<Cfigmcn> cn = new ArrayList<>();
//				cn.add(data.get(0));
//
//				CFIgm igmData = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());
//
//				if (igmData == null) {
//					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//				}
//
//				Map<String, Object> result = new HashMap<>();
//				result.put("igm", igmData);
//				result.put("igmCrg", crg);
//				result.put("con", cn);
//
//				return new ResponseEntity<>(result, HttpStatus.OK);
//			} else {
//				Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, data.get(0).getIgmTransId(), data.get(0).getIgmNo(),
//						data.get(0).getIgmLineNo());
//
//				if (crg == null) {
//					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//				}
//
//				List<Cfigmcn> cn = cfigmcnrepo.getHoldSearch1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(),
//						crg.getIgmLineNo());
//
//				if (cn.isEmpty()) {
//					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//				}
//
//				CFIgm igmData = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());
//
//				if (igmData == null) {
//					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
//				}
//
//				Map<String, Object> result = new HashMap<>();
//				result.put("igm", igmData);
//				result.put("igmCrg", crg);
//				result.put("con", cn);
//
//				return new ResponseEntity<>(result, HttpStatus.OK);
//
//			}
//		}
//
//	
//	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "igm", required = false) String igm,
			@RequestParam(name = "item", required = false) String item,
			@RequestParam(name = "container", required = false) String container) {

		Map<String, Object> result = new HashMap<>();

		// If no container is provided
		if (container == null || container.isEmpty()) {

			// Fetch cargo data
			Cfigmcrg crg = cfigmcrgrepo.getData4(cid, bid, igm, item);
			if (crg == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// Fetch IGM data
			CFIgm igmData = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());
			if (igmData == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// Fetch container data
			List<Cfigmcn> cn = cfigmcnrepo.getHoldSearch1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(),
					crg.getIgmLineNo());
			if (cn.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			String profitcentrename = profitcenterrepo.getAllDataByID1(cid, bid, igmData.getProfitcentreId());

			result.put("igm", igmData);
			result.put("igmCrg", crg);
			result.put("con", cn);
			result.put("profit", profitcentrename);

			return new ResponseEntity<>(result, HttpStatus.OK);

		} else {
			// Handle case when container is provided
			List<Cfigmcn> data = cfigmcnrepo.getHoldSearch(cid, bid, container);
			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// Fetch cargo data based on the container
			Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, data.get(0).getIgmTransId(), data.get(0).getIgmNo(),
					data.get(0).getIgmLineNo());
			if (crg == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// Fetch IGM data based on the container
			CFIgm igmData = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());
			if (igmData == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			// Handle multiple items in the container
			List<Cfigmcn> cn;
			if (data.get(0).getNoOfItem() > 1) {
				cn = new ArrayList<>();
				cn.add(data.get(0)); // Only add the first item
			} else {
				cn = cfigmcnrepo.getHoldSearch1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(), crg.getIgmLineNo());
				if (cn.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}
			}

			String profitcentrename = profitcenterrepo.getAllDataByID1(cid, bid, igmData.getProfitcentreId());

			result.put("igm", igmData);
			result.put("igmCrg", crg);
			result.put("con", cn);
			result.put("profit", profitcentrename);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}

	@PostMapping("/saveData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody HoldDto holdData) {

		if (holdData == null) {
			return new ResponseEntity<>("Hold data not found", HttpStatus.CONFLICT);
		}

		if (holdData.getHoldId() == null || holdData.getHoldId().isEmpty()) {
			int sr = holdRepo.getIndex(cid, bid, holdData.getGateInId(), holdData.getContainerNo(),
					holdData.getIgmTransId(), holdData.getIgmNo(), holdData.getIgmLineNo());

			Boolean exist = holdRepo.isExist(cid, bid, holdData.getGateInId(), holdData.getContainerNo(),
					holdData.getIgmTransId(), holdData.getIgmNo(), holdData.getIgmLineNo(),
					holdData.getHoldingAgency());

			if (exist) {
				return new ResponseEntity<>("Holding agency already exist.", HttpStatus.CONFLICT);
			}

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05078", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("HOLD%06d", nextNumericNextID1);

			HoldDetails hold = new HoldDetails();
			hold.setCompanyId(cid);
			hold.setBranchId(bid);
			hold.setDocRefNo(HoldNextIdD1);
			hold.setContainerNo(holdData.getContainerNo());
			hold.setDocRefDate(new Date());
			hold.setIgmLineNo(holdData.getIgmLineNo());
			hold.setHldSrNo(sr + 1);
			hold.setIgmNo(holdData.getIgmNo());
			hold.setIgmTransId(holdData.getIgmTransId());
			hold.setGateInId(holdData.getGateInId());
			hold.setCreatedBy(user);
			hold.setCreatedDate(new Date());
			hold.setStatus("A");
			hold.setApprovedBy(user);
			hold.setApprovedDate(new Date());
			hold.setHoldStatus("H");
			hold.setHoldingAgency(holdData.getHoldingAgency());
			hold.setHoldDate(holdData.getHoldDate());
			hold.setHoldRemarks(holdData.getHoldRemarks());
			hold.setHoldUser(user);

			holdRepo.save(hold);

			processnextidrepo.updateAuditTrail(cid, bid, "P05078", HoldNextIdD1, "2024");

			List<Cfigmcn> cn = cfigmcnrepo.getHoldSearch2(cid, bid, holdData.getIgmTransId(), holdData.getIgmNo(),
					holdData.getContainerNo());

			if (cn.isEmpty()) {
				return new ResponseEntity<>("Container data not found.", HttpStatus.CONFLICT);
			}

			cn.stream().forEach(c -> {
				if (!"H".equals(c.getHoldStatus())) {
					c.setHoldDate(holdData.getHoldDate());
					c.setHoldDocRefNo(HoldNextIdD1);
					c.setHoldingAgentName(user);
					c.setHoldRemarks(holdData.getHoldRemarks());
					c.setHoldStatus("H");

					cfigmcnrepo.save(c);
				}

				Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, holdData.getIgmTransId(), holdData.getIgmNo(),
						c.getIgmLineNo());

				if (!"H".equals(crg.getHoldStatus())) {
					crg.setHoldDate(holdData.getHoldDate());
					crg.setHoldingAgentName(user);
					crg.setHoldRemarks(holdData.getHoldRemarks());
					crg.setHoldStatus("H");

					cfigmcrgrepo.save(crg);

				}

			});

			ImportInventory inv = importinventoryrepo.getById(cid, bid, holdData.getIgmTransId(), holdData.getIgmNo(),
					holdData.getContainerNo(), holdData.getGateInId());

			if (inv != null) {

				if (!"H".equals(inv.getHoldStatus())) {

					inv.setHoldDate(holdData.getHoldDate());
					inv.setHoldingAgentName(user);
					inv.setHoldRemarks(holdData.getHoldRemarks());
					inv.setHoldStatus("H");

					importinventoryrepo.save(inv);
				}

			}
		}

		List<HoldDetails> data = holdRepo.getData(cid, bid, holdData.getGateInId(), holdData.getContainerNo(),
				holdData.getIgmTransId(), holdData.getIgmNo(), holdData.getIgmLineNo());

		if (data.isEmpty()) {
			return new ResponseEntity<>("Hold data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/getData")
	public ResponseEntity<?> getData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestBody HoldDto holdData) {

		List<HoldDetails> data = holdRepo.getData(cid, bid, holdData.getGateInId(), holdData.getContainerNo(),
				holdData.getIgmTransId(), holdData.getIgmNo(), holdData.getIgmLineNo());

		if (data.isEmpty()) {
			return new ResponseEntity<>("Hold data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveReleaseData")
	public ResponseEntity<?> saveReleaseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String userId, @RequestBody List<HoldDetails> holdData) {

		if (holdData.isEmpty()) {
			return new ResponseEntity<>("Hold details data not found.", HttpStatus.CONFLICT);
		}

		for (HoldDetails hold : holdData) {
			HoldDetails h = holdRepo.getSingleData(cid, bid, hold.getGateInId(), hold.getContainerNo(),
					hold.getIgmTransId(), hold.getIgmNo(), hold.getIgmLineNo(), hold.getDocRefNo(), hold.getHldSrNo());

			if (h == null) {
				return new ResponseEntity<>("Hold data not found.", HttpStatus.CONFLICT);
			}

			h.setHoldStatus("R");
			h.setReleaseDate(hold.getReleaseDate());
			h.setReleaseRemarks(hold.getReleaseRemarks());
			h.setReleaseUser(userId);

			holdRepo.save(h);

			List<Cfigmcn> cn = cfigmcnrepo.getHoldSearch2(cid, bid, hold.getIgmTransId(), hold.getIgmNo(),
					hold.getContainerNo());

			if (cn.isEmpty()) {
				return new ResponseEntity<>("Container data not found.", HttpStatus.CONFLICT);
			}

			Boolean checkContainerHoldStatus = holdRepo.checkContainerHoldStatus(cid, bid, hold.getGateInId(),
					hold.getContainerNo(), hold.getIgmTransId(), hold.getIgmNo());

			if (checkContainerHoldStatus) {
				cn.stream().forEach(c -> {
					if (!"R".equals(c.getHoldStatus())) {
						c.setHoldStatus("R");
						c.setReleaseAgent(userId);
						c.setReleaseDate(hold.getReleaseDate());
						c.setReleaseRemarks(hold.getReleaseRemarks());

						cfigmcnrepo.save(c);
					}

					Boolean checkCon = cfigmcnrepo.checkContainerHoldStatus(cid, bid, hold.getIgmTransId(),
							hold.getIgmNo(), c.getIgmLineNo());

					if (checkCon) {
						Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, hold.getIgmTransId(), hold.getIgmNo(),
								c.getIgmLineNo());

						if (!"R".equals(crg.getHoldStatus())) {
							crg.setHoldStatus("R");
							crg.setReleaseAgent(userId);
							crg.setReleaseDate(hold.getReleaseDate());
							crg.setReleaseRemarks(hold.getReleaseRemarks());

							cfigmcrgrepo.save(crg);

						}
					}

				});

			}

			if (checkContainerHoldStatus) {
				ImportInventory inv = importinventoryrepo.getById(cid, bid, hold.getIgmTransId(),
						hold.getIgmNo(), hold.getContainerNo(), hold.getGateInId());

				if (inv != null) {

					if (!"R".equals(inv.getHoldStatus())) {

						inv.setReleaseUserName(userId);
						inv.setReleaseDate(hold.getReleaseDate());
						inv.setReleaseRemarks(hold.getReleaseRemarks());
						inv.setHoldStatus("R");

						importinventoryrepo.save(inv);
					}

				}
			}

		}

		List<HoldDetails> data = holdRepo.getData(cid, bid, holdData.get(0).getGateInId(),
				holdData.get(0).getContainerNo(), holdData.get(0).getIgmTransId(), holdData.get(0).getIgmNo(),
				holdData.get(0).getIgmLineNo());

		if (data.isEmpty()) {
			return new ResponseEntity<>("Hold data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

}
