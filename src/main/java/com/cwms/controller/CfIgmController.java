package com.cwms.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.ContainerExaminationDTO;
import com.cwms.entities.ContainerSealCuttingDTO;
import com.cwms.entities.ExportAudit;
import com.cwms.entities.ContainerSealCuttingDTO.Cargo;
import com.cwms.entities.ContainerSealCuttingDTO.Container;
import com.cwms.entities.GateIn;
import com.cwms.entities.ImportExaminationDTO;
import com.cwms.entities.ImportInventory;
import com.cwms.entities.ManualGateIn;
import com.cwms.entities.Party;
import com.cwms.entities.SealCuttingData;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.ChildMenuRepository;
import com.cwms.repository.ExportAuditRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.ManualContainerGateInRepo;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/cfigm")
public class CfIgmController {

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Autowired
	private ChildMenuRepository childmenurepo;

	@Autowired
	private GateInRepository gateinrepo;
	
	@Autowired
	private ImportInventoryRepository importinventoryrepo;
	
	@Autowired
	private ManualContainerGateInRepo manualcontainergateinrepo;
	
	@Autowired
	private VehicleTrackRepository vehicletrackrepo;
	
	@Autowired
	private ExportInventoryRepository exportInvRepo;
	
	@Autowired
	private ExportAuditRepo exportauditrepo;

	@GetMapping("/search")
	public List<Object[]> getSearchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String search) {
		return cfigmrepo.getSearchData(cid, bid, search);
	}

	@GetMapping("/getDataByTransAndIGM")
	public CFIgm getDataByTransId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("transId") String transId, @RequestParam("igm") String igm) {
		System.out.println("transId " + transId);
		return cfigmrepo.getDataByIgmNoAndtrans(cid, bid, transId, igm);
	}

	@GetMapping("/getDataByIGM")
	public Map<String, Object> getDataByTransId1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {

		System.out.println("igm " + igm);
		CFIgm igm1 = cfigmrepo.getDataByIgmNo2(cid, bid, igm);
		Object[] igm2 = cfigmrepo.getSearchData2(cid, bid, igm1.getIgmTransId());

		Map<String, Object> data = new HashMap<>();
		data.put("igm", igm1);
		data.put("igm1", igm2);

		return data;
	}

	@PostMapping("/saveIgm")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("flag") String flag, @RequestBody CFIgm igm) {

		if (igm != null) {
			if ("add".equals(flag)) {
//				Boolean isExist = cfigmrepo.isDataExist(cid, bid, igm.getIgmNo());
//
//				if (isExist) {
//					return new ResponseEntity<>("Duplicate IGM no", HttpStatus.BAD_REQUEST);
//				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05060", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("CIAR%06d", nextNumericNextID1);

				igm.setCompanyId(cid);
				igm.setIgmTransId(HoldNextIdD1);
				igm.setBranchId(bid);
				igm.setStatus('A');
				igm.setCreatedBy(user);
				igm.setCreatedDate(new Date());
				igm.setApprovedBy(user);
				igm.setApprovedDate(new Date());
				igm.setPortJo(igm.getIgmNo());
				igm.setPortJoDate(new Date());
				cfigmrepo.save(igm);

				processnextidrepo.updateAuditTrail(cid, bid, "P05060", HoldNextIdD1, "2024");

				return new ResponseEntity<>(igm, HttpStatus.OK);

			} else {

				Boolean isExist = cfigmrepo.isDataExist1(cid, bid, igm.getIgmNo(), igm.getIgmTransId());

				if (isExist) {
					return new ResponseEntity<>("Duplicate IGM no", HttpStatus.BAD_REQUEST);
				}

				CFIgm existingigm = cfigmrepo.getDataByIgmNo(cid, bid, igm.getIgmTransId());

				int updateIgm = cfigmcrgrepo.updateIgmNo(cid, bid, existingigm.getIgmTransId(), existingigm.getIgmNo(),
						igm.getIgmNo(), igm.getViaNo());

				int updateIgm1 = cfigmcnrepo.updateIgmNo(cid, bid, existingigm.getIgmTransId(), existingigm.getIgmNo(),
						igm.getIgmNo());

				igm.setEditedBy(user);
				igm.setEditedDate(new Date());
				igm.setPortJo(igm.getIgmNo());
				cfigmrepo.save(igm);

				return new ResponseEntity<>(igm, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("IGM data not found", HttpStatus.BAD_REQUEST);
		}

	}

//	@PostMapping("/saveCrgData")
//	public ResponseEntity<?> saveCrgData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam("user") String user, @RequestParam("igmTransId") String igmTransId,
//			@RequestBody List<Cfigmcrg> igmcrg) {
//
//		if (!igmcrg.isEmpty()) {
//			CFIgm igm = cfigmrepo.getDataByIgmNo(cid, bid, igmTransId);
//			if (igm == null) {
//				return new ResponseEntity<>("IGM data not found", HttpStatus.BAD_REQUEST);
//			}
//
//			List<Cfigmcrg> data = new ArrayList<Cfigmcrg>();
//
//			for (Cfigmcrg i : igmcrg) {
//				if (i.getIgmTransId().isEmpty()) {
//					Boolean exist = cfigmcrgrepo.isExistRecord(cid, bid, i.getBlNo());
//
//					if (exist) {
//						return new ResponseEntity<>("Duplicate BL No", HttpStatus.BAD_REQUEST);
//					}
//
////					Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, i.getIgmLineNo());
////
////					if (exist1) {
////						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
////					}
//
//					Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, i.getIgmLineNo(), igm.getIgmNo());
//
//
//					if (exist1) {
//						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
//					}
//
//					String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05061", "2024");
//
//					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));
//
//					int nextNumericNextID1 = lastNextNumericId1 + 1;
//
//					String HoldNextIdD1 = String.format("ICR%07d", nextNumericNextID1);
//
//					i.setIgmCrgTransId(HoldNextIdD1);
//					i.setCompanyId(cid);
//					i.setProfitcentreId(igm.getProfitcentreId());
//					i.setBranchId(bid);
//					i.setIgmNo(igm.getIgmNo());
//					i.setIgmTransId(igmTransId);
//					i.setCreatedBy(user);
//					i.setCreatedDate(new Date());
//					i.setViaNo(igm.getViaNo());
//					i.setApprovedBy(user);
//					i.setApprovedDate(new Date());
//					i.setStatus("A");
//
//					cfigmcrgrepo.save(i);
//					data.add(i);
//					processnextidrepo.updateAuditTrail(cid, bid, "P05061", HoldNextIdD1, "2024");
//				} else {
//					Boolean exist = cfigmcrgrepo.isExistRecord1(cid, bid, i.getBlNo(), i.getIgmCrgTransId());
//
//					if (exist) {
//						return new ResponseEntity<>("Duplicate BL No", HttpStatus.BAD_REQUEST);
//					}
//
////					Boolean exist1 = cfigmcrgrepo.isExistLineRecord1(cid, bid, i.getIgmLineNo(), i.getIgmCrgTransId());
////
////					if (exist1) {
////						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
////					}
//					Boolean exist1 = cfigmcrgrepo.isExistLineRecord1(cid, bid, i.getIgmLineNo(), i.getIgmCrgTransId(),
//							i.getIgmNo());
//
//					if (exist1) {
//						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
//					}
//
//					Cfigmcrg existingData1 = cfigmcrgrepo.getData(cid, bid, igmTransId, i.getIgmNo(),
//							i.getIgmCrgTransId());
//
//					int updateContainer = cfigmcnrepo.updateIGMLineData(cid, bid, igmTransId,
//							existingData1.getProfitcentreId(), existingData1.getIgmNo(), existingData1.getIgmLineNo(),
//							i.getIgmLineNo());
//
//					int updateData = cfigmcrgrepo.updateData(cid, bid, igmTransId, i.getIgmNo(), i.getIgmCrgTransId(),
//							i.getCycle(), i.getIgmLineNo(), i.getBlNo(), i.getBlDate(), i.getCommodityDescription(),
//							i.getCargoMovement(), i.getGrossWeight(), i.getUnitOfWeight(), i.getNoOfPackages(),
//							i.getTypeOfPackage(), i.getAccountHolderId(), i.getAccountHolderName(), i.getMarksOfNumbers(),
//							i.getImporterId(), i.getImporterName(), i.getImporterAddress1(), i.getImporterAddress2(),
//							i.getImporterAddress3(), i.getNotifyPartyId(), i.getNotifyPartyName(),
//							i.getNotifiedAddress1(), i.getNotifiedAddress2(), i.getNotifiedAddress3(),
//							i.getDestination(), i.getCargoType(), i.getImoCode(), i.getUnNo(), i.getHazReeferRemarks(),
//							user, new Date(),i.getHsnCode());
//
//					Cfigmcrg existingData = cfigmcrgrepo.getData(cid, bid, igmTransId, i.getIgmNo(),
//							i.getIgmCrgTransId());
//
//					if (existingData != null) {
//						data.add(existingData);
//					}
//
//				}
//
//			}
//			return new ResponseEntity<>(data, HttpStatus.OK);
//
//		} else {
//			return new ResponseEntity<>("Import Cargo details not found", HttpStatus.BAD_REQUEST);
//		}
//
//	}
	
	@PostMapping("/saveCrgData")
	public ResponseEntity<?> saveCrgData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("igmTransId") String igmTransId,
			@RequestBody List<Cfigmcrg> igmcrg) {

		if (!igmcrg.isEmpty()) {
			CFIgm igm = cfigmrepo.getDataByIgmNo(cid, bid, igmTransId);
			if (igm == null) {
				return new ResponseEntity<>("IGM data not found", HttpStatus.BAD_REQUEST);
			}

			List<Cfigmcrg> data = new ArrayList<Cfigmcrg>();

			for (Cfigmcrg i : igmcrg) {
				if (i.getIgmTransId().isEmpty()) {
					Boolean exist = cfigmcrgrepo.isExistRecord(cid, bid, i.getBlNo());

					if (exist) {
						return new ResponseEntity<>("Duplicate BL No", HttpStatus.BAD_REQUEST);
					}

//					Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, i.getIgmLineNo());
//
//					if (exist1) {
//						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
//					}

					Boolean exist1 = cfigmcrgrepo.isExistLineRecord(cid, bid, i.getIgmLineNo(), igm.getIgmNo());

					if (exist1) {
						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
					}

					String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05061", "2024");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String HoldNextIdD1 = String.format("ICR%07d", nextNumericNextID1);

					i.setIgmCrgTransId(HoldNextIdD1);
					i.setCompanyId(cid);
					i.setProfitcentreId(igm.getProfitcentreId());
					i.setBranchId(bid);
					i.setIgmNo(igm.getIgmNo());
					i.setIgmTransId(igmTransId);
					i.setCreatedBy(user);
					i.setCreatedDate(new Date());
					i.setViaNo(igm.getViaNo());
					i.setApprovedBy(user);
					i.setApprovedDate(new Date());
					i.setStatus("A");

					cfigmcrgrepo.save(i);
					processnextidrepo.updateAuditTrail(cid, bid, "P05061", HoldNextIdD1, "2024");
				} else {
					Boolean exist = cfigmcrgrepo.isExistRecord1(cid, bid, i.getBlNo(), i.getIgmCrgTransId());

					if (exist) {
						return new ResponseEntity<>("Duplicate BL No", HttpStatus.BAD_REQUEST);
					}

//					Boolean exist1 = cfigmcrgrepo.isExistLineRecord1(cid, bid, i.getIgmLineNo(), i.getIgmCrgTransId());
//
//					if (exist1) {
//						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
//					}
					Boolean exist1 = cfigmcrgrepo.isExistLineRecord1(cid, bid, i.getIgmLineNo(), i.getIgmCrgTransId(),
							i.getIgmNo());

					if (exist1) {
						return new ResponseEntity<>("Duplicate IGM Line No", HttpStatus.BAD_REQUEST);
					}

					Cfigmcrg existingData1 = cfigmcrgrepo.getData(cid, bid, igmTransId, i.getIgmNo(),
							i.getIgmCrgTransId());

					int updateContainer = cfigmcnrepo.updateIGMLineData(cid, bid, igmTransId,
							existingData1.getProfitcentreId(), existingData1.getIgmNo(), existingData1.getIgmLineNo(),
							i.getIgmLineNo());

					System.out.println("i.getBlNo() " + i.getBlNo());

					int updateData = cfigmcrgrepo.updateData(cid, bid, igmTransId, i.getIgmNo(), i.getIgmCrgTransId(),
							i.getCycle(), i.getIgmLineNo(), i.getBlNo(), i.getBlDate(), i.getCommodityDescription(),
							i.getCargoMovement(), i.getGrossWeight(), i.getUnitOfWeight(), i.getNoOfPackages(),
							i.getTypeOfPackage(), i.getAccountHolderId(), i.getAccountHolderName(),
							i.getMarksOfNumbers(), i.getImporterId(), i.getImporterName(), i.getImporterAddress1(),
							i.getImporterAddress2(), i.getImporterAddress3(), i.getNotifyPartyId(),
							i.getNotifyPartyName(), i.getNotifiedAddress1(), i.getNotifiedAddress2(),
							i.getNotifiedAddress3(), i.getDestination(), i.getCargoType(), i.getImoCode(), i.getUnNo(),
							i.getHazReeferRemarks(), user, new Date(), i.getHsnCode());

					System.out.println("updateData " + updateData);

					List<String> conData = importinventoryrepo.getContainers(cid, bid, igmTransId, i.getIgmNo(),
							i.getIgmLineNo());

					if (!conData.isEmpty()) {
						conData.stream().forEach(c -> {
							int updateIGMCrgImportInventoryData = importinventoryrepo.updateIGMCrgImportInventoryData(
									cid, bid, igmTransId, i.getIgmNo(), c, i.getImporterName(), i.getCycle());
						});
					}

					int updateIGMCrgDestuffCrgData = importinventoryrepo.updateIGMCrgDestuffCrgData(cid, bid,
							igmTransId, i.getIgmNo(), i.getIgmLineNo(), i.getCommodityDescription(),
							i.getMarksOfNumbers(), i.getGrossWeight(), i.getTypeOfPackage(),
							i.getNoOfPackages().intValue(), i.getImporterName(), i.getImporterAddress1(),
							i.getImporterAddress2(), i.getImporterAddress3());

					checkAndUpdateIGMCRGAudit(cid, bid, user, existingData1, i);
				}

			}

			List<Cfigmcrg> existingData5 = cfigmcrgrepo.getDataByIgm(cid, bid, igmTransId, igm.getIgmNo());

			return new ResponseEntity<>(existingData5, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("Import Cargo details not found", HttpStatus.BAD_REQUEST);
		}

	}

//	@GetMapping("/getDatabyIgmNoAndTrans")
//	public List<Cfigmcrg> getData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam("trans") String trans, @RequestParam("igm") String igm) {
//		return cfigmcrgrepo.getDataByIgm(cid, bid, trans, igm);
//	}
	
	@GetMapping("/getDatabyIgmNoAndTrans")
	public List<Cfigmcrg> getData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("trans") String trans, @RequestParam("igm") String igm) {
		List<Cfigmcrg> existingData = cfigmcrgrepo.getDataByIgm(cid, bid, trans, igm);

		existingData.stream().forEach(c -> {
			String invStatus = cfigmcrgrepo.getInvoiceDoneStatus(cid, bid, c.getIgmTransId(), c.getIgmNo(),
					c.getIgmLineNo());

			c.setInvoiceDone((invStatus == null || invStatus.isEmpty()) ? "N" : invStatus);
		});

		return existingData;

	}


	@PostMapping("/deleteCrg")
	public String deleteData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("trans") String trans, @RequestParam("igm") String igm,
			@RequestParam("igmCrgTrans") String igmCrgTrans) {
		Cfigmcrg igmCrg = cfigmcrgrepo.getData(cid, bid, trans, igm, igmCrgTrans);

		if (igmCrg != null) {
			igmCrg.setStatus("D");
			cfigmcrgrepo.save(igmCrg);

			return "successfully deleted";
		} else {
			return "data not found";
		}
	}

//	@PostMapping("/saveContainer")
//	public ResponseEntity<?> saveCnData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam("user") String user, @RequestParam("flag") String flag,
//			@RequestParam(name = "oldContainer", required = false) String oldContainer, @RequestBody Cfigmcn cn) {
//
//		if (cn != null) {
//			if ("add".equals(flag)) {
//				
//				Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, cn.getIgmTransId(), cn.getIgmNo(), cn.getIgmLineNo());
//
//				if (crg == null) {
//					return new ResponseEntity<>("Import cargo data not found", HttpStatus.BAD_REQUEST);
//				}
//				
//				CFIgm igm = cfigmrepo.getDataByIgmNo(cid, bid, cn.getIgmTransId());
//				if (igm == null) {
//					return new ResponseEntity<>("IGM data not found", HttpStatus.BAD_REQUEST);
//				}
//
//				Boolean isExist = cfigmcnrepo.isExistContainer(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
//						cn.getIgmLineNo(), cn.getContainerNo());
//
//				if (isExist) {
//					return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
//				}
//
//				if ("H".equals(cn.getHoldStatus())) {
//					cn.setHoldDate(new Date());
//					cn.setHoldRemarks(cn.getHoldRemarks());
//					cn.setHoldingAgentName(user);
//				}
//
//				else {
//					cn.setHoldStatus("N");
//					cn.setHoldRemarks("");
//				}
//
//				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");
//
//				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(1));
//
//				int nextNumericNextID1 = lastNextNumericId1 + 1;
//
//				String HoldNextIdD1 = String.format("C%09d", nextNumericNextID1);
//
//				cn.setContainerTransId(HoldNextIdD1);
//				cn.setCompanyId(cid);
//				cn.setBranchId(bid);
//				cn.setStatus('A');
//				cn.setCreatedBy(user);
//				cn.setCreatedDate(new Date());
//				cn.setApprovedBy(user);
//				cn.setApprovedDate(new Date());
//				cn.setCycle(crg.getCycle());
//				cn.setGateOutType(cn.getUpTariffDelMode());
//				
//				
//				
//				ManualGateIn manual = manualcontainergateinrepo.getDataByContainerNo(cid, bid, cn.getContainerNo());
//				
//				if(manual != null) {
//					cn.setGateInDate(manual.getGateInDate());
//					cn.setGateInId(manual.getGateInId());
//					cn.setHaz(manual.getHazardous());
//					cn.setHazClass(manual.getHazClass());
//					cn.setEirGrossWeight(manual.getEirGrossWeight());
//					cn.setVehicleType(manual.getVehicleType());
//					//cn.setContainerWeight(manual.getTareWeight());
//					cn.setContainerSealNo(manual.getContainerSealNo());
//					cn.setYardLocation(manual.getYardLocation());
//					cn.setYardBlock(manual.getYardBlock());
//					cn.setBlockCellNo(manual.getYardCell());
//				
//					//cn.setMovementType(manual.getDrt());
//					
//					cn.setCargoWt(cn.getGrossWt()
//							.subtract(manual.getTareWeight() != null ? manual.getTareWeight() : BigDecimal.ZERO));
////					gatein.setCargoWeight(c.getGrossWt()
////							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));
//
//					cn.setRefer("RF".equals(cn.getContainerType()) ? 'Y' : 'N');
//					
////					if ("Y".equals(manual.getLowBed())) {
////						cn.setLowBed('Y');
////					} else {
////						cn.setLowBed('N');
////					}
//
//					
//					cn.setMovementType("Y".equals(manual.getDrt()) ? "DRT" : "CFS");
//
//					if ("Y".equals(manual.getPnStatus())) {
//						cn.setPnStatus('Y');
//					} else {
//						cn.setPnStatus('N');
//					}
//
//					if ("Y".equals(manual.getOdcStatus())) {
//						cn.setOdcStatus('Y');
//					} else {
//						cn.setOdcStatus('N');
//					}
//					
//					
//					crg.setActualCargoWeight(crg.getActualCargoWeight() == null ? BigDecimal.ZERO
//							: crg.getActualCargoWeight().add(cn.getGrossWt().subtract(
//									manual.getTareWeight() != null ? manual.getTareWeight() : BigDecimal.ZERO)));
//					cfigmcrgrepo.save(crg);
//					
//					
////					manual.setErpDocRefNo(cn.getIgmTransId());
////					manual.setDocRefNo(cn.getIgmNo());
////					manual.setDocRefDate(igm.getIgmDate());
////					manual.setLineNo(cn.getIgmLineNo());
////					manual.setContainerStatus(cn.getContainerStatus());
////					manual.setIsoCode(cn.getIso());
////					if (cn.getOdcStatus() == 'Y') {
////						manual.setOdcStatus("Y");
////					} else {
////						manual.setOdcStatus("N");
////					}
////					
////					if (cn.getLowBed() == 'Y') {
////						manual.setLowBed("Y");
////					} else {
////						manual.setLowBed("N");
////					}
////					manual.setGrossWeight(cn.getGrossWt());
////					manual.setScannerType(cn.getScannerType());
////					manual.setCustomsSealNo(cn.getCustomsSealNo());
////					manual.setScanningDoneStatus(cn.getScanningDoneStatus());
////					manual.setVessel(igm.getVesselId());
////					manual.setViaNo(igm.getViaNo());
////					manual.setVoyageNo(igm.getVoyageNo());
////					manual.setRefer("RF".equals(cn.getContainerType()) ? "Y" : "N");
////					
////					manualcontainergateinrepo.save(manual);
//					
//					int updateData = manualcontainergateinrepo.updateManualGateIn(cid, bid, manual.getGateInId(), cn.getIgmTransId(), cn.getIgmNo(), 
//							cn.getIgmLineNo(), cn.getContainerStatus(), cn.getIso(), String.valueOf(cn.getOdcStatus()), String.valueOf(cn.getLowBed()), cn.getGrossWt(), 
//							cn.getScannerType(), cn.getCustomsSealNo(), cn.getScanningDoneStatus(), igm.getVesselId(), igm.getViaNo(), 
//							igm.getVoyageNo(), "RF".equals(cn.getContainerType()) ? "Y" : "N", igm.getIgmDate());
//					
//					GateIn gatein = new GateIn();
//					
//					gatein.setGateInId(manual.getGateInId());
//					gatein.setCompanyId(cid);
//					gatein.setLineNo("");
//					gatein.setSrNo(1);
//					gatein.setInGateInDate(manual.getGateInDate());
//					gatein.setBranchId(bid);
//					gatein.setFinYear(cn.getFinYear());
//					gatein.setCreatedBy(user);
//					gatein.setCreatedDate(new Date());
//					gatein.setApprovedBy(user);
//					gatein.setApprovedDate(new Date());
//					gatein.setStatus("A");
//					gatein.setGateInType("IMP");
//					gatein.setCargoWeight(cn.getGrossWt()
//					.subtract(manual.getTareWeight() != null ? manual.getTareWeight() : BigDecimal.ZERO));
//					gatein.setContainerNo(manual.getContainerNo());
//					gatein.setContainerStatus(cn.getContainerStatus());
//					gatein.setContainerSize(cn.getContainerSize());
//					gatein.setContainerType(cn.getContainerType());
//					gatein.setIsoCode(cn.getIso());
//					gatein.setSl(igm.getShippingLine());
//					gatein.setContainerSealNo(manual.getContainerSealNo());
//					gatein.setActualSealNo(manual.getActualSealNo());
//					gatein.setDocRefDate(igm.getIgmDate());
//					gatein.setErpDocRefNo(cn.getIgmTransId());
//					gatein.setDocRefNo(cn.getIgmNo());
//					gatein.setJobDate(igm.getDocDate());
//					gatein.setTerminal(manual.getTerminal());
//					gatein.setJobOrderId(cn.getIgmNo());
//					gatein.setTareWeight(cn.getContainerWeight());
//					gatein.setTerminal(igm.getPort());
//					gatein.setVessel(igm.getVesselId());
//					gatein.setViaNo(igm.getViaNo());
//					gatein.setEirGrossWeight(manual.getEirGrossWeight());
//					gatein.setRefer(manual.getRefer());
//					gatein.setLowBed(manual.getLowBed());
//					gatein.setScannerType(cn.getScannerType());
//					gatein.setOdcStatus(manual.getOdcStatus());
//					gatein.setTemperature(cn.getTemperature());
//					gatein.setProfitcentreId(igm.getProfitcentreId());
//					gatein.setLineNo(cn.getIgmLineNo());
//					gatein.setHazardous(manual.getHazardous());
//					gatein.setOrigin(crg.getOrigin());
//					gatein.setVehicleNo(manual.getVehicleNo());
//					gatein.setDriverName(manual.getDriverName());
//					gatein.setVehicleType(manual.getVehicleType());
//					gatein.setTransporter(manual.getTransporter());
//					gatein.setTransporterName(manual.getTransporterName());
//					gatein.setTransporterStatus("O");
//					gatein.setPortExitNo(manual.getPortExitNo());
//					gatein.setPortExitDate(manual.getPortExitDate());
//					gatein.setScanningDoneStatus(cn.getScanningDoneStatus());
//					gatein.setYardLocation(manual.getYardLocation());
//					gatein.setYardBlock(manual.getYardBlock());
//					gatein.setYardCell(manual.getYardCell());
//					gatein.setContainerHealth(manual.getContainerHealth());
//					gatein.setLowBed(String.valueOf(cn.getLowBed()));
//					gatein.setOdcStatus(String.valueOf(cn.getOdcStatus()));
//					gatein.setPnStatus(manual.getPnStatus());
//					gatein.setCustomsSealNo(cn.getCustomsSealNo());
//					gatein.setDrt("N");
//					gatein.setHazardous(manual.getHazardous());
//					gatein.setRefer("RF".equals(cn.getContainerType()) ? "Y" : "N");
//					gatein.setProcessId("P00212");
//					gateinrepo.save(gatein);
//					
//					VehicleTrack v = new VehicleTrack();
//					v.setCompanyId(cid);
//					v.setBranchId(bid);
//					v.setFinYear(igm.getFinYear());
//					v.setVehicleNo(manual.getVehicleNo());
//					v.setProfitcentreId(igm.getProfitcentreId());
//					v.setSrNo(1);
//					v.setTransporterStatus(gatein.getTransporterStatus().charAt(0));
//					v.setTransporterName(manual.getTransporterName());
//					v.setTransporter(manual.getTransporter());
//					v.setDriverName(manual.getDriverName());
//					v.setVehicleStatus('E');
//					v.setGateInId(manual.getGateInId());
//					v.setGateInDate(new Date());
//					v.setGateNoIn("Gate01");
//					v.setShiftIn("1");
//					v.setStatus('A');
//					v.setCreatedBy(user);
//					v.setCreatedDate(new Date());
//					v.setApprovedBy(user);
//					v.setApprovedDate(new Date());
//
//					vehicletrackrepo.save(v);
//					
//					
//					ImportInventory inventory = new ImportInventory();
//					inventory.setCompanyId(cid);
//					inventory.setBranchId(bid);
//					inventory.setFinYear(igm.getFinYear());
//					inventory.setIgmTransId(cn.getIgmTransId());
//					inventory.setProfitcentreId(cn.getProfitcentreId());
//					inventory.setIgmNo(cn.getIgmNo());
//					inventory.setVesselId(igm.getVesselId());
//					inventory.setViaNo(igm.getViaNo());
//					inventory.setContainerNo(cn.getContainerNo());
//					inventory.setContainerSize(cn.getContainerSize());
//					inventory.setContainerType(cn.getContainerType());
//					inventory.setIso(cn.getIso());
//					inventory.setSa(igm.getShippingAgent());
//					inventory.setSl(igm.getShippingLine());
//					inventory.setImporterName(crg.getImporterName());
//					inventory.setContainerStatus(cn.getContainerStatus());
//					inventory.setContainerSealNo(manual.getContainerSealNo());
//					inventory.setScannerType(cn.getScannerType());
//					inventory.setYardLocation(manual.getYardLocation());
//					inventory.setYardLocation1(manual.getYardLocation1());
//					inventory.setYardBlock(manual.getYardBlock());
//					inventory.setBlockCellNo(manual.getYardCell());
//					inventory.setGateInId(manual.getGateInId());
//					inventory.setGateInDate(manual.getGateInDate());
//					inventory.setStatus("A");
//					inventory.setCreatedBy(user);
//					inventory.setCreatedDate(new Date());
//					inventory.setApprovedBy(user);
//					inventory.setApprovedDate(new Date());
//					inventory.setNoOfItem(1);
//					inventory.setContainerWeight(cn.getGrossWt()
//							.subtract(cn.getContainerWeight() != null ? cn.getContainerWeight() : BigDecimal.ZERO));
//					inventory.setCycle(cn.getCycle());
//					
//					
//					importinventoryrepo.save(inventory);
//
//				}
//				
//				
//				
//				cfigmcnrepo.save(cn);
//				processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD1, "2024");
//				int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(), cn.getContainerNo());
//				return new ResponseEntity<>(cn, HttpStatus.OK);
//			} else {
//				System.out.println("cn.getContainerTransId() " + cn.getContainerTransId());
//				Boolean isExist = cfigmcnrepo.isExistContainer1(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
//						cn.getIgmLineNo(), cn.getContainerNo(), cn.getContainerTransId());
//
//				if (isExist) {
//					return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
//				}
//
//				Cfigmcn existingRecord1 = cfigmcnrepo.getSingleData4(cid, bid, cn.getIgmTransId(),
//						cn.getProfitcentreId(), cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId());
//
//				if ("H".equals(cn.getHoldStatus()) && !"H".equals(existingRecord1.getHoldStatus())) {
//					int update = cfigmcnrepo.updateContainerData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
//							cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId(), cn.getContainerNo(),
//							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getTypeOfContainer(),
//							cn.getContainerStatus(), cn.getTemperature(), cn.getContainerWeight(), cn.getGrossWt(),
//							cn.getNoOfPackages(), cn.getContainerSealNo(), cn.getUpTariffDelMode(), cn.getScannerType(),
//							cn.getScanningDoneStatus(), cn.getOdcStatus(), cn.getLowBed(), user, new Date(),
//							cn.getHoldRemarks(), cn.getHoldStatus(), new Date(), user, existingRecord1.getReleaseDate(),
//							existingRecord1.getReleaseAgent());
//				} else if ("R".equals(cn.getHoldStatus()) && "H".equals(existingRecord1.getHoldStatus())) {
//					int update = cfigmcnrepo.updateContainerData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
//							cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId(), cn.getContainerNo(),
//							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getTypeOfContainer(),
//							cn.getContainerStatus(), cn.getTemperature(), cn.getContainerWeight(), cn.getGrossWt(),
//							cn.getNoOfPackages(), cn.getContainerSealNo(), cn.getUpTariffDelMode(), cn.getScannerType(),
//							cn.getScanningDoneStatus(), cn.getOdcStatus(), cn.getLowBed(), user, new Date(),
//							cn.getHoldRemarks(), cn.getHoldStatus(), existingRecord1.getHoldDate(),
//							existingRecord1.getHoldingAgentName(), new Date(), user);
//				} else {
//					int update = cfigmcnrepo.updateContainerData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
//							cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId(), cn.getContainerNo(),
//							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getTypeOfContainer(),
//							cn.getContainerStatus(), cn.getTemperature(), cn.getContainerWeight(), cn.getGrossWt(),
//							cn.getNoOfPackages(), cn.getContainerSealNo(), cn.getUpTariffDelMode(), cn.getScannerType(),
//							cn.getScanningDoneStatus(), cn.getOdcStatus(), cn.getLowBed(), user, new Date(),
//							existingRecord1.getHoldRemarks(), existingRecord1.getHoldStatus(),
//							existingRecord1.getHoldDate(), existingRecord1.getHoldingAgentName(),
//							existingRecord1.getReleaseDate(), existingRecord1.getReleaseAgent());
//				}
//				int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(),
//						existingRecord1.getContainerNo());
//				int b = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(), cn.getContainerNo());
//
//				Cfigmcn existingRecord = cfigmcnrepo.getSingleData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
//						cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerNo());
//
//				return new ResponseEntity<>(existingRecord, HttpStatus.OK);
//			}
//		} else {
//			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
//		}
//
//	}

	@PostMapping("/saveContainer")
	public ResponseEntity<?> saveCnData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("flag") String flag,
			@RequestParam(name = "oldContainer", required = false) String oldContainer, @RequestBody Cfigmcn cn) {

		if (cn != null) {
			if ("add".equals(flag)) {

				Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, cn.getIgmTransId(), cn.getIgmNo(), cn.getIgmLineNo());

				if (crg == null) {
					return new ResponseEntity<>("Import cargo data not found", HttpStatus.BAD_REQUEST);
				}

				CFIgm igm = cfigmrepo.getDataByIgmNo(cid, bid, cn.getIgmTransId());
				if (igm == null) {
					return new ResponseEntity<>("IGM data not found", HttpStatus.BAD_REQUEST);
				}

				Boolean isExist = cfigmcnrepo.isExistContainer(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
						cn.getIgmLineNo(), cn.getContainerNo());

				if (isExist) {
					return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
				}

				if ("H".equals(cn.getHoldStatus())) {
					cn.setHoldDate(new Date());
					cn.setHoldRemarks(cn.getHoldRemarks());
					cn.setHoldingAgentName(user);
				}

				else {
					cn.setHoldStatus("N");
					cn.setHoldRemarks("");
				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05062", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(1));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("C%09d", nextNumericNextID1);

				cn.setContainerTransId(HoldNextIdD1);
				cn.setCompanyId(cid);
				cn.setBranchId(bid);
				cn.setStatus('A');
				cn.setCreatedBy(user);
				cn.setCreatedDate(new Date());
				cn.setApprovedBy(user);
				cn.setApprovedDate(new Date());
				cn.setCycle(crg.getCycle());
				cn.setGateOutType(cn.getUpTariffDelMode());

				ManualGateIn manual = manualcontainergateinrepo.getDataByContainerNo(cid, bid, cn.getContainerNo());

				if (manual != null) {
					cn.setGateInDate(manual.getGateInDate());
					cn.setGateInId(manual.getGateInId());
					cn.setHaz(manual.getHazardous());
					cn.setHazClass(manual.getHazClass());
					cn.setEirGrossWeight(manual.getEirGrossWeight());
					cn.setVehicleType(manual.getVehicleType());
					// cn.setContainerWeight(manual.getTareWeight());
					cn.setContainerSealNo(manual.getContainerSealNo());
					cn.setYardLocation(manual.getYardLocation());
					cn.setYardBlock(manual.getYardBlock());
					cn.setBlockCellNo(manual.getYardCell());

					// cn.setMovementType(manual.getDrt());

					cn.setCargoWt(cn.getGrossWt()
							.subtract(manual.getTareWeight() != null ? manual.getTareWeight() : BigDecimal.ZERO));
//					gatein.setCargoWeight(c.getGrossWt()
//							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));

					cn.setRefer("RF".equals(cn.getContainerType()) ? 'Y' : 'N');

//					if ("Y".equals(manual.getLowBed())) {
//						cn.setLowBed('Y');
//					} else {
//						cn.setLowBed('N');
//					}

					cn.setMovementType("Y".equals(manual.getDrt()) ? "DRT" : "CFS");

					if ("Y".equals(manual.getPnStatus())) {
						cn.setPnStatus('Y');
					} else {
						cn.setPnStatus('N');
					}

					if ("Y".equals(manual.getOdcStatus())) {
						cn.setOdcStatus('Y');
					} else {
						cn.setOdcStatus('N');
					}

					crg.setActualCargoWeight(crg.getActualCargoWeight() == null ? BigDecimal.ZERO
							: crg.getActualCargoWeight().add(cn.getGrossWt().subtract(
									manual.getTareWeight() != null ? manual.getTareWeight() : BigDecimal.ZERO)));
					cfigmcrgrepo.save(crg);

//					manual.setErpDocRefNo(cn.getIgmTransId());
//					manual.setDocRefNo(cn.getIgmNo());
//					manual.setDocRefDate(igm.getIgmDate());
//					manual.setLineNo(cn.getIgmLineNo());
//					manual.setContainerStatus(cn.getContainerStatus());
//					manual.setIsoCode(cn.getIso());
//					if (cn.getOdcStatus() == 'Y') {
//						manual.setOdcStatus("Y");
//					} else {
//						manual.setOdcStatus("N");
//					}
//					
//					if (cn.getLowBed() == 'Y') {
//						manual.setLowBed("Y");
//					} else {
//						manual.setLowBed("N");
//					}
//					manual.setGrossWeight(cn.getGrossWt());
//					manual.setScannerType(cn.getScannerType());
//					manual.setCustomsSealNo(cn.getCustomsSealNo());
//					manual.setScanningDoneStatus(cn.getScanningDoneStatus());
//					manual.setVessel(igm.getVesselId());
//					manual.setViaNo(igm.getViaNo());
//					manual.setVoyageNo(igm.getVoyageNo());
//					manual.setRefer("RF".equals(cn.getContainerType()) ? "Y" : "N");
//					
//					manualcontainergateinrepo.save(manual);

					int updateData = manualcontainergateinrepo.updateManualGateIn(cid, bid, manual.getGateInId(),
							cn.getIgmTransId(), cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerStatus(), cn.getIso(),
							String.valueOf(cn.getOdcStatus()), String.valueOf(cn.getLowBed()), cn.getGrossWt(),
							cn.getScannerType(), cn.getCustomsSealNo(), cn.getScanningDoneStatus(), igm.getVesselId(),
							igm.getViaNo(), igm.getVoyageNo(), "RF".equals(cn.getContainerType()) ? "Y" : "N",
							igm.getIgmDate());

					GateIn gatein = new GateIn();

					gatein.setGateInId(manual.getGateInId());
					gatein.setCompanyId(cid);
					gatein.setLineNo("");
					gatein.setSrNo(1);
					gatein.setInGateInDate(manual.getGateInDate());
					gatein.setBranchId(bid);
					gatein.setFinYear(cn.getFinYear());
					gatein.setCreatedBy(user);
					gatein.setCreatedDate(new Date());
					gatein.setApprovedBy(user);
					gatein.setApprovedDate(new Date());
					gatein.setStatus("A");
					gatein.setGateInType("IMP");
					gatein.setCargoWeight(cn.getGrossWt()
							.subtract(manual.getTareWeight() != null ? manual.getTareWeight() : BigDecimal.ZERO));
					gatein.setContainerNo(manual.getContainerNo());
					gatein.setContainerStatus(cn.getContainerStatus());
					gatein.setContainerSize(cn.getContainerSize());
					gatein.setContainerType(cn.getContainerType());
					gatein.setIsoCode(cn.getIso());
					gatein.setSl(igm.getShippingLine());
					gatein.setContainerSealNo(manual.getContainerSealNo());
					gatein.setActualSealNo(manual.getActualSealNo());
					gatein.setDocRefDate(igm.getIgmDate());
					gatein.setErpDocRefNo(cn.getIgmTransId());
					gatein.setDocRefNo(cn.getIgmNo());
					gatein.setJobDate(igm.getDocDate());
					gatein.setTerminal(manual.getTerminal());
					gatein.setJobOrderId(cn.getIgmNo());
					gatein.setTareWeight(cn.getContainerWeight());
					gatein.setTerminal(igm.getPort());
					gatein.setVessel(igm.getVesselId());
					gatein.setViaNo(igm.getViaNo());
					gatein.setEirGrossWeight(manual.getEirGrossWeight());
					gatein.setRefer(manual.getRefer());
					gatein.setLowBed(manual.getLowBed());
					gatein.setScannerType(cn.getScannerType());
					gatein.setOdcStatus(manual.getOdcStatus());
					gatein.setTemperature(cn.getTemperature());
					gatein.setProfitcentreId(igm.getProfitcentreId());
					gatein.setLineNo(cn.getIgmLineNo());
					gatein.setHazardous(manual.getHazardous());
					gatein.setOrigin(crg.getOrigin());
					gatein.setVehicleNo(manual.getVehicleNo());
					gatein.setDriverName(manual.getDriverName());
					gatein.setVehicleType(manual.getVehicleType());
					gatein.setTransporter(manual.getTransporter());
					gatein.setTransporterName(manual.getTransporterName());
					gatein.setTransporterStatus("O");
					gatein.setPortExitNo(manual.getPortExitNo());
					gatein.setPortExitDate(manual.getPortExitDate());
					gatein.setScanningDoneStatus(cn.getScanningDoneStatus());
					gatein.setYardLocation(manual.getYardLocation());
					gatein.setYardBlock(manual.getYardBlock());
					gatein.setYardCell(manual.getYardCell());
					gatein.setContainerHealth(manual.getContainerHealth());
					gatein.setLowBed(String.valueOf(cn.getLowBed()));
					gatein.setOdcStatus(String.valueOf(cn.getOdcStatus()));
					gatein.setPnStatus(manual.getPnStatus());
					gatein.setCustomsSealNo(cn.getCustomsSealNo());
					gatein.setDrt("N");
					gatein.setHazardous(manual.getHazardous());
					gatein.setRefer("RF".equals(cn.getContainerType()) ? "Y" : "N");
					gatein.setProcessId("P00212");
					gateinrepo.save(gatein);

					VehicleTrack v = new VehicleTrack();
					v.setCompanyId(cid);
					v.setBranchId(bid);
					v.setFinYear(igm.getFinYear());
					v.setVehicleNo(manual.getVehicleNo());
					v.setProfitcentreId(igm.getProfitcentreId());
					v.setSrNo(1);
					v.setTransporterStatus(gatein.getTransporterStatus().charAt(0));
					v.setTransporterName(manual.getTransporterName());
					v.setTransporter(manual.getTransporter());
					v.setDriverName(manual.getDriverName());
					v.setVehicleStatus('E');
					v.setGateInId(manual.getGateInId());
					v.setGateInDate(new Date());
					v.setGateNoIn("Gate01");
					v.setShiftIn("1");
					v.setStatus('A');
					v.setCreatedBy(user);
					v.setCreatedDate(new Date());
					v.setApprovedBy(user);
					v.setApprovedDate(new Date());

					vehicletrackrepo.save(v);

					ImportInventory inventory = new ImportInventory();
					inventory.setCompanyId(cid);
					inventory.setBranchId(bid);
					inventory.setFinYear(igm.getFinYear());
					inventory.setIgmTransId(cn.getIgmTransId());
					inventory.setProfitcentreId(cn.getProfitcentreId());
					inventory.setIgmNo(cn.getIgmNo());
					inventory.setVesselId(igm.getVesselId());
					inventory.setViaNo(igm.getViaNo());
					inventory.setContainerNo(cn.getContainerNo());
					inventory.setContainerSize(cn.getContainerSize());
					inventory.setContainerType(cn.getContainerType());
					inventory.setIso(cn.getIso());
					inventory.setSa(igm.getShippingAgent());
					inventory.setSl(igm.getShippingLine());
					inventory.setImporterName(crg.getImporterName());
					inventory.setContainerStatus(cn.getContainerStatus());
					inventory.setContainerSealNo(manual.getContainerSealNo());
					inventory.setScannerType(cn.getScannerType());
					inventory.setYardLocation(manual.getYardLocation());
					inventory.setYardLocation1(manual.getYardLocation1());
					inventory.setYardBlock(manual.getYardBlock());
					inventory.setBlockCellNo(manual.getYardCell());
					inventory.setGateInId(manual.getGateInId());
					inventory.setGateInDate(manual.getGateInDate());
					inventory.setStatus("A");
					inventory.setCreatedBy(user);
					inventory.setCreatedDate(new Date());
					inventory.setApprovedBy(user);
					inventory.setApprovedDate(new Date());
					inventory.setNoOfItem(1);
					inventory.setContainerWeight(cn.getGrossWt()
							.subtract(cn.getContainerWeight() != null ? cn.getContainerWeight() : BigDecimal.ZERO));
					inventory.setCycle(cn.getCycle());

					importinventoryrepo.save(inventory);

				}

				cfigmcnrepo.save(cn);
				processnextidrepo.updateAuditTrail(cid, bid, "P05062", HoldNextIdD1, "2024");
				int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(), cn.getContainerNo());
				return new ResponseEntity<>(cn, HttpStatus.OK);
			} else {
				System.out.println("cn.getContainerTransId() " + cn.getContainerTransId());
				Boolean isExist = cfigmcnrepo.isExistContainer1(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
						cn.getIgmLineNo(), cn.getContainerNo(), cn.getContainerTransId());

				if (isExist) {
					return new ResponseEntity<>("Duplicate Container No", HttpStatus.BAD_REQUEST);
				}

				Cfigmcn existingRecord1 = cfigmcnrepo.getSingleData4(cid, bid, cn.getIgmTransId(),
						cn.getProfitcentreId(), cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId());

				if ("H".equals(cn.getHoldStatus()) && !"H".equals(existingRecord1.getHoldStatus())) {
					int update = cfigmcnrepo.updateContainerData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
							cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId(), cn.getContainerNo(),
							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getTypeOfContainer(),
							cn.getContainerStatus(), cn.getTemperature(), cn.getContainerWeight(), cn.getGrossWt(),
							cn.getNoOfPackages(), cn.getContainerSealNo(), cn.getUpTariffDelMode(), cn.getScannerType(),
							cn.getScanningDoneStatus(), cn.getOdcStatus(), cn.getLowBed(), user, new Date(),
							cn.getHoldRemarks(), cn.getHoldStatus(), new Date(), user, existingRecord1.getReleaseDate(),
							existingRecord1.getReleaseAgent());
				} else if ("R".equals(cn.getHoldStatus()) && "H".equals(existingRecord1.getHoldStatus())) {
					int update = cfigmcnrepo.updateContainerData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
							cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId(), cn.getContainerNo(),
							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getTypeOfContainer(),
							cn.getContainerStatus(), cn.getTemperature(), cn.getContainerWeight(), cn.getGrossWt(),
							cn.getNoOfPackages(), cn.getContainerSealNo(), cn.getUpTariffDelMode(), cn.getScannerType(),
							cn.getScanningDoneStatus(), cn.getOdcStatus(), cn.getLowBed(), user, new Date(),
							cn.getHoldRemarks(), cn.getHoldStatus(), existingRecord1.getHoldDate(),
							existingRecord1.getHoldingAgentName(), new Date(), user);
				} else {
					int update = cfigmcnrepo.updateContainerData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
							cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerTransId(), cn.getContainerNo(),
							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getTypeOfContainer(),
							cn.getContainerStatus(), cn.getTemperature(), cn.getContainerWeight(), cn.getGrossWt(),
							cn.getNoOfPackages(), cn.getContainerSealNo(), cn.getUpTariffDelMode(), cn.getScannerType(),
							cn.getScanningDoneStatus(), cn.getOdcStatus(), cn.getLowBed(), user, new Date(),
							existingRecord1.getHoldRemarks(), existingRecord1.getHoldStatus(),
							existingRecord1.getHoldDate(), existingRecord1.getHoldingAgentName(),
							existingRecord1.getReleaseDate(), existingRecord1.getReleaseAgent());
				}
				int a = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(),
						existingRecord1.getContainerNo());
				int b = cfigmcnrepo.updateNoOfItem(cid, bid, cn.getIgmNo(), cn.getIgmTransId(), cn.getContainerNo());

				int updateIGMCnInventoryData = importinventoryrepo.updateIGMCnInventoryData(cid, bid,
						existingRecord1.getIgmTransId(), existingRecord1.getIgmNo(), existingRecord1.getContainerNo(),
						cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getContainerStatus(),
						cn.getContainerWeight(), cn.getScannerType(), cn.getContainerSealNo());

				if (existingRecord1.getGateInId() != null && !existingRecord1.getGateInId().isEmpty()) {
					int updateIGMCnGateInData = importinventoryrepo.updateIGMCnGateInData(cid, bid,
							existingRecord1.getIgmTransId(), existingRecord1.getIgmNo(), existingRecord1.getGateInId(),
							cn.getIso(), cn.getContainerSize(), cn.getContainerType(), cn.getContainerStatus(),
							String.valueOf(cn.getOdcStatus()), String.valueOf(cn.getLowBed()), cn.getTemperature(),
							cn.getContainerWeight(), cn.getGrossWt(), cn.getScannerType(), cn.getContainerSealNo());
				}

				if (existingRecord1.getDeStuffId() != null && !existingRecord1.getDeStuffId().isEmpty()) {
					int updateIGMCnDestuffData = importinventoryrepo.updateIGMCnDestuffData(cid, bid,
							existingRecord1.getIgmTransId(), existingRecord1.getIgmNo(), existingRecord1.getDeStuffId(),
							existingRecord1.getContainerNo(), cn.getContainerSize(), cn.getContainerType(),
							cn.getContainerStatus(), cn.getGrossWt(), cn.getContainerSealNo());
				}

				checkAndUpdateIGMCNAudit(cid, bid, user, existingRecord1, cn);
				
				Cfigmcn existingRecord = cfigmcnrepo.getSingleData(cid, bid, cn.getIgmTransId(), cn.getProfitcentreId(),
						cn.getIgmNo(), cn.getIgmLineNo(), cn.getContainerNo());

				return new ResponseEntity<>(existingRecord, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/getContainerData")
	public List<Cfigmcn> getContainerData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igmTrans") String igmTrans, @RequestParam("profit") String profit,
			@RequestParam("igm") String igm, @RequestParam("igmLineNo") String igmLineNo) {

		return cfigmcnrepo.getAllData(cid, bid, igmTrans, profit, igm, igmLineNo);
	}

	@GetMapping("/getSingleContainer")
	public Cfigmcn getSingledata(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igmTrans") String igmTrans, @RequestParam("profit") String profit,
			@RequestParam("igm") String igm, @RequestParam("igmLineNo") String igmLineNo,
			@RequestParam("containerNo") String containerNo) {
		return cfigmcnrepo.getSingleData(cid, bid, igmTrans, profit, igm, igmLineNo, containerNo);
	}

	@GetMapping("/deleteContainer")
	public ResponseEntity<?> deleteContainer(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("igmTrans") String igmTrans,
			@RequestParam("profit") String profit, @RequestParam("igm") String igm,
			@RequestParam("igmLineNo") String igmLineNo, @RequestParam("containerNo") String containerNo) {

		Cfigmcn cn = cfigmcnrepo.getSingleData(cid, bid, igmTrans, profit, igm, igmLineNo, containerNo);

		if (cn != null) {
			cn.setStatus('D');
			cn.setEditedBy(user);
			cn.setEditedDate(new Date());
			cfigmcnrepo.save(cn);

			return new ResponseEntity<>("Data deleted successfully!!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Data not found!!", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getDataByContainer")
	public Object[] containerSearch(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("container") String container) {
		return cfigmrepo.getDataByContainer(cid, bid, container);
	}

	@GetMapping("/searchContainer")
	public List<Object[]> searchContainer(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String search) {
		return cfigmcnrepo.getContainers(cid, bid, search);
	}

	@GetMapping("/getDataForSealCutting")
	public ResponseEntity<?> getDataForSealCutting(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("line") String line) {

		Cfigmcrg crgData = cfigmcrgrepo.getData2(cid, bid, igm, line);

		if (crgData == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
		}

//		List<Cfigmcn> cnData = cfigmcnrepo.getAllData1(cid, bid, igm, line);

		List<Cfigmcn> cnData = cfigmcnrepo.getSealCutting(cid, bid, igm, line);

		if (cnData.isEmpty()) {
			return new ResponseEntity<>("Container data not found or gate-in process not completed",
					HttpStatus.BAD_REQUEST);
		}
		SealCuttingData data = new SealCuttingData();
		data.setCn(cnData);
		data.setCrg(crgData);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveSealCutting")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody SealCuttingData data) {
		Cfigmcrg crg = data.getCrg();
		List<Cfigmcn> cn = data.getCn();

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
		}

		if (cn.isEmpty()) {
			return new ResponseEntity<>("Container not found", HttpStatus.BAD_REQUEST);
		}

		Cfigmcrg existingCrg = cfigmcrgrepo.getData1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(), crg.getIgmLineNo());

		if (existingCrg == null) {
			return new ResponseEntity<>("Cargo detail data not found", HttpStatus.BAD_REQUEST);
		}

		Boolean isExistBe = cfigmcrgrepo.isExistBENo(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(), crg.getBeNo());

		if (isExistBe) {
			return new ResponseEntity<>("BE No already exist.", HttpStatus.BAD_REQUEST);
		}

		existingCrg.setBeNo(crg.getBeNo());
		existingCrg.setBeDate(crg.getBeDate());
		existingCrg.setChaCode(crg.getChaCode());
		existingCrg.setChaName(crg.getChaName());
		existingCrg.setCargoValue(crg.getCargoValue());
		existingCrg.setCargoDuty(crg.getCargoDuty());
		existingCrg.setBlType(crg.getBlType());
		existingCrg.setMobileNo(crg.getMobileNo());
		existingCrg.setSealCuttingType(crg.getSealCuttingType());
		existingCrg.setBeWt(crg.getBeWt());
		existingCrg.setSealCuttingRemarks(crg.getSealCuttingRemarks());
		cfigmcrgrepo.save(existingCrg);

		String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05064", "2024");

		String[] parts = lastValue.split("/");
		String baseId = parts[0];
		String financialYear = parts[1];

		// Increment the base ID
		String newBaseId = incrementBaseId(baseId);

		// Get the current financial year
		String currentFinancialYear = getCurrentFinancialYear();

		// Construct the new ID
		String newId = newBaseId + "/" + currentFinancialYear;

		Party party = partyrepo.getDataByCustomerCode(cid, bid, crg.getChaCode());

		cn.stream().forEach(cnData -> {
			
			if ("Y".equals(cnData.getSealCuttingStatus())) {

				Cfigmcn existingCon = cfigmcnrepo.getSingleData4(cid, bid, cnData.getIgmTransId(), cnData.getIgmNo(),
						cnData.getIgmLineNo(), cnData.getContainerNo());

				if (cnData.getSealCutWoTransId() == null || cnData.getSealCutWoTransId().isEmpty()) {
					
					existingCon.setSealCutReqDate(cnData.getSealCutReqDate());
					existingCon.setGateOutType(cnData.getGateOutType());
					existingCon.setSealCutRemarks(crg.getSealCuttingRemarks());
					existingCon.setSealCutApprovedBy(user);
					existingCon.setSealCutApprovedDate(new Date());
					existingCon.setSealCutCreatedBy(user);
					existingCon.setSealCutCreatedDate(new Date());
					existingCon.setSealCuttingType(crg.getSealCuttingType());
					if ("RMS".equals(crg.getSealCuttingType())) {
						existingCon.setContainerExamStatus("Y");
					}
					existingCon.setBeNo(crg.getBeNo());
					existingCon.setBeDate(crg.getBeDate());
					existingCon.setSealCutTransId("A");
					existingCon.setSealCutWoTransDate(new Date());
					existingCon.setSealCutWoTransId(newId);
					existingCon.setSealCuttingStatus("Y");
					existingCon.setCha(party != null ? party.getPartyId() : "");

					cfigmcnrepo.save(existingCon);
					
					ImportInventory existingInv = importinventoryrepo.getById(cid, bid, existingCon.getIgmTransId(), existingCon.getIgmNo(), 
							existingCon.getContainerNo(), existingCon.getGateInId());
					
					if(existingInv != null) {
						existingInv.setSealCutReqDate(new Date());
						existingInv.setSealCutTransId(newId);
						existingInv.setCha(party != null ? party.getPartyId() : "");
						
						importinventoryrepo.save(existingInv);
					}

					processnextidrepo.updateAuditTrail(cid, bid, "P05064", newId, "2024");
				} else {
					existingCon.setSealCutReqDate(cnData.getSealCutReqDate());
					existingCon.setGateOutType(cnData.getGateOutType());
					existingCon.setSealCutRemarks(crg.getSealCuttingRemarks());
					existingCon.setSealCutApprovedBy(user);
					existingCon.setBeNo(crg.getBeNo());
					existingCon.setBeDate(crg.getBeDate());
					existingCon.setSealCutApprovedDate(new Date());
					existingCon.setSealCuttingType(crg.getSealCuttingType());
					if ("RMS".equals(crg.getSealCuttingType())) {
						existingCon.setContainerExamStatus("Y");
					}
					existingCon.setCha(party != null ? party.getPartyId() : "");
					
					ImportInventory existingInv = importinventoryrepo.getById(cid, bid, existingCon.getIgmTransId(), existingCon.getIgmNo(), 
							existingCon.getContainerNo(), existingCon.getGateInId());
					
					if(existingInv != null) {
	
						existingInv.setCha(party != null ? party.getPartyId() : "");
						
						importinventoryrepo.save(existingInv);
					}
					cfigmcnrepo.save(existingCon);
				}
			}

		});

		Cfigmcrg crgData = cfigmcrgrepo.getData2(cid, bid, crg.getIgmNo(), crg.getIgmLineNo());

		List<Cfigmcn> cnData = cfigmcnrepo.getAllData1(cid, bid, crg.getIgmNo(), crg.getIgmLineNo());

		SealCuttingData data1 = new SealCuttingData();
		data1.setCn(cnData);
		data1.setCrg(crgData);

		return new ResponseEntity<>(data1, HttpStatus.OK);

	}

	private static String incrementBaseId(String baseId) {
		// Extract the numeric part
		String numericPart = baseId.replaceAll("\\D", ""); // Remove non-digit characters
		int number = Integer.parseInt(numericPart);

		// Increment the number
		number++;

		// Format the number back to the original format
		return baseId.replace(numericPart, String.format("%04d", number));
	}

	private static String getCurrentFinancialYear() {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		// Check if the current date is before or after April 1st
		if (month < 4 || (month == 4 && day < 1)) {
			year--; // If before April 1st, consider the previous year
		}

		// Calculate financial year start and end
		int financialYearStart = year % 100; // Current year for start
		int financialYearEnd = (year + 1) % 100; // Next year for end

		// Format as YY-YY
		return String.format("%02d-%02d", financialYearStart, financialYearEnd);
	}

	@GetMapping("/getSingleContainerForSealCutting")
	public ResponseEntity<?> getSingleContainerForSealCutting(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm,
			@RequestParam("container") String container) {

		List<Object[]> cn = cfigmcnrepo.getSingleContainerForSealCutting2(cid, bid, igm, container);
		System.out.println("cn " + cn);

		if (cn.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
		} else {

			boolean holdStatusFlag = cn.stream().anyMatch(record -> "H".equals(record[40].toString()));

			if (holdStatusFlag) {
				return new ResponseEntity<>("Container is hold", HttpStatus.BAD_REQUEST);
			}

			Object[] singleResult = cn.get(0);
			if (singleResult[4] == null || singleResult[4].toString().isEmpty()) {
				return new ResponseEntity<>("Gate-in process not completed", HttpStatus.BAD_REQUEST);
			}
			List<Object[]> crg = cfigmcrgrepo.getCrgData(cid, bid, igm, container);

			Map<String, Object> response = new HashMap<>();
			response.put("singleResult", singleResult);
			response.put("crg", crg);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/saveContainerWiseSealCutting")
	public ResponseEntity<?> saveContainerWiseSealCutting(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user,
			@RequestBody ContainerSealCuttingDTO data) {
		Container con = data.getCon();
		List<Cargo> cargo = data.getCargo();
		

		List<Cfigmcn> containerData = cfigmcnrepo.getSingleContainerForSealCutting(cid, bid, con.getIgmNo(),
				con.getContainerNo());

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found.", HttpStatus.BAD_REQUEST);
		}
		
		

		for (Cargo c : cargo) {
			Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());
			if (crg == null) {
				return new ResponseEntity<>("Cargo data with item no " + c.getIgmLineNo() + " not found.",
						HttpStatus.BAD_REQUEST);
			}

			Boolean isExistBe = cfigmcrgrepo.isExistBENo(cid, bid, c.getIgmNo(), c.getIgmLineNo(), c.getBeNo());

			if (isExistBe) {
				return new ResponseEntity<>(
						"The BE Number already exists for the specified item number " + c.getIgmLineNo(),
						HttpStatus.BAD_REQUEST);
			}

			crg.setBeNo(c.getBeNo());
			crg.setBeDate(c.getBeDate());
			crg.setCargoValue(c.getCargoValue());
			crg.setCargoDuty(c.getCargoDuty());
			crg.setChaCode(c.getChaCode());
			crg.setChaName(c.getChaName());
			crg.setBlType(c.getBlType());
			crg.setMobileNo(con.getMobileNo());
			crg.setSealCuttingType(con.getSealCuttingType());
			crg.setSealCuttingRemarks(con.getSealCutRemarks());
//			crg.setBeWt(containerData.stream()
//		            .map(Cfigmcn::getCargoWt) // Replace with the actual method to get the value you want to sum
//		            .reduce(BigDecimal.ZERO, BigDecimal::add));

			cfigmcrgrepo.save(crg);

		}
		String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05064", "2024");

		String[] parts = lastValue.split("/");
		String baseId = parts[0];
		String financialYear = parts[1];

		// Increment the base ID
		String newBaseId = incrementBaseId(baseId);

		// Get the current financial year
		String currentFinancialYear = getCurrentFinancialYear();

		// Construct the new ID
		String newId = newBaseId + "/" + currentFinancialYear;

		containerData.stream().forEach(con1 -> {
			if (con1.getSealCutWoTransId() == null || con1.getSealCutWoTransId().isEmpty()) {

				Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, con.getIgmTransId(), con.getIgmNo(), con.getIgmLineNo());
				
				Party p = new Party();
				
				if(crg != null) {
					p = partyrepo.getDataByCustomerCode(cid, bid, crg.getChaCode());
				}
				
				con1.setSealCutReqDate(con.getSealCutWoTransDate());
				con1.setGateOutType(con.getGateOutType());
				con1.setSealCutRemarks(con.getSealCutRemarks());
				con1.setSealCutApprovedBy(user);
				con1.setSealCutApprovedDate(new Date());
				con1.setSealCutCreatedBy(user);
				con1.setSealCutCreatedDate(new Date());
				con1.setSealCuttingType(con.getSealCuttingType());
				if ("RMS".equals(con.getSealCuttingType())) {
					con1.setContainerExamStatus("Y");
				}
				con1.setSealCutTransId("A");
				con1.setSealCutWoTransDate(new Date());
				con1.setSealCutWoTransId(newId);
				con1.setSealCuttingStatus("Y");
				con1.setVehicleType(con.getVehicleType());
				
				if(p != null) {
					con1.setCha(p.getPartyId());
				}
				if ("Y".equals(con.getOdcStatus())) {
					con1.setOdcStatus('Y');
				} else {
					con1.setOdcStatus('N');
				}

				if ("Y".equals(con.getLowBed())) {
					con1.setLowBed('Y');
				} else {
					con1.setLowBed('N');
				}

				cfigmcnrepo.save(con1);
				
				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, con1.getIgmTransId(), con1.getIgmNo(), 
						con1.getContainerNo(), con1.getGateInId());
				
				if(existingInv != null) {
					existingInv.setSealCutReqDate(new Date());
					existingInv.setSealCutTransId(newId);
					
					
					importinventoryrepo.save(existingInv);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05064", newId, "2024");
			} else {
				con1.setSealCutReqDate(con.getSealCutWoTransDate());
				con1.setGateOutType(con.getGateOutType());
				con1.setSealCutRemarks(con.getSealCutRemarks());
				con1.setSealCutApprovedBy(user);
				con1.setSealCutApprovedDate(new Date());
				con1.setSealCuttingType(con.getSealCuttingType());
				con1.setVehicleType(con.getVehicleType());
				if ("RMS".equals(con.getSealCuttingType())) {
					con1.setContainerExamStatus("Y");
				}
				if ("Y".equals(con.getOdcStatus())) {
					con1.setOdcStatus('Y');
				} else {
					con1.setOdcStatus('N');
				}

				if ("Y".equals(con.getLowBed())) {
					con1.setLowBed('Y');
				} else {
					con1.setLowBed('N');
				}
				cfigmcnrepo.save(con1);
				
				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, con1.getIgmTransId(), con1.getIgmNo(), 
						con1.getContainerNo(), con1.getGateInId());
				
				if(existingInv != null) {
		
					existingInv.setCha(con1.getChaCode());
					
					importinventoryrepo.save(existingInv);
				}
			}

		});

		List<Object[]> cn = cfigmcnrepo.getSingleContainerForSealCutting1(cid, bid, con.getIgmNo(),
				con.getContainerNo());

		Object[] singleResult = cn.get(0);

		List<Object[]> crg = cfigmcrgrepo.getCrgData(cid, bid, con.getIgmNo(), con.getContainerNo());

		Map<String, Object> response = new HashMap<>();
		response.put("singleResult", singleResult);
		response.put("crg", crg);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	@GetMapping("/getDataForExamination")
	public ResponseEntity<?> getDataForExamination(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("line") String line) {

		Cfigmcrg crgData = cfigmcrgrepo.getData3(cid, bid, igm, line);

		System.out.println("crgData " + crgData);

		if (crgData == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
		}

		List<Cfigmcn> cnData = cfigmcnrepo.getAllData3(cid, bid, igm, line);

		if (cnData.isEmpty()) {
			return new ResponseEntity<>("Container data not found or seal cutting process not completed",
					HttpStatus.BAD_REQUEST);
		}
		ImportExaminationDTO data = new ImportExaminationDTO();
		data.setCn(cnData);
		data.setCrg(crgData);
		data.setDoDate(cnData.get(0).getDoDate());
		data.setDoNo(cnData.get(0).getDoNo());
		data.setDoValidityDate(cnData.get(0).getDoValidityDate());
		data.setOocDate(cnData.get(0).getOocDate());
		data.setOocNo(cnData.get(0).getOocNo());

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveExamination")
	public ResponseEntity<?> saveExamination(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody ImportExaminationDTO data) {
		Cfigmcrg crg = data.getCrg();
		List<Cfigmcn> cn = data.getCn();

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
		}

		if (cn.isEmpty()) {
			return new ResponseEntity<>("Container not found", HttpStatus.BAD_REQUEST);
		}

		Cfigmcrg existingCrg = cfigmcrgrepo.getData1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(), crg.getIgmLineNo());

		if (existingCrg == null) {
			return new ResponseEntity<>("Cargo detail data not found", HttpStatus.BAD_REQUEST);
		}

		existingCrg.setExaminationRemarks(crg.getExaminationRemarks());

		cfigmcrgrepo.save(existingCrg);

		String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05065", "2024");

		String[] parts = lastValue.split("/");
		String baseId = parts[0];
		String financialYear = parts[1];

		// Increment the base ID
		String newBaseId = incrementBaseId(baseId);

		// Get the current financial year
		String currentFinancialYear = getCurrentFinancialYear();

		// Construct the new ID
		String newId = newBaseId + "/" + currentFinancialYear;

		cn.stream().forEach(cnData -> {
			// if ("Y".equals(cnData.getSealCuttingStatus())) {

			Cfigmcn existingCon = cfigmcnrepo.getSingleData4(cid, bid, cnData.getIgmTransId(), cnData.getIgmNo(),
					cnData.getIgmLineNo(), cnData.getContainerNo());

			if (cnData.getContainerExamWoTransId() == null || cnData.getContainerExamWoTransId().isEmpty()) {

				existingCon.setContainerExamWoTransDate(new Date());
				existingCon.setContainerExamWoTransId(newId);
				existingCon.setContainerExamDate(new Date());
				existingCon.setExaminedPackages(cnData.getExaminedPackages());
				existingCon.setPackagesDeStuffed(cnData.getPackagesDeStuffed());
				existingCon.setPackagesStuffed(cnData.getPackagesStuffed());
				existingCon.setTypeOfContainer(cnData.getTypeOfContainer());
				existingCon.setGateOutType(cnData.getGateOutType());
				existingCon.setSpecialDelivery(cnData.getSpecialDelivery());
				existingCon.setContainerExamRemarks(cnData.getContainerExamRemarks());
				existingCon.setContainerExamCreatedBy(user);
				existingCon.setContainerExamCreatedDate(new Date());
				existingCon.setContainerExamApprovedBy(user);
				existingCon.setContainerExamApprovedDate(new Date());
				existingCon.setContainerExamStatus("Y");
				existingCon.setDoNo(data.getDoNo());
				existingCon.setDoDate(data.getDoDate());
				existingCon.setDoValidityDate(data.getDoValidityDate());
				existingCon.setOocNo(data.getOocNo());
				existingCon.setOocDate(data.getOocDate());

				cfigmcnrepo.save(existingCon);
				
				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, existingCon.getIgmTransId(), existingCon.getIgmNo(), 
						existingCon.getContainerNo(), existingCon.getGateInId());
				
				if(existingInv != null) {
					existingInv.setContainerExamStatus("Y");
					existingInv.setContainerExamDate(new Date());
					
					
					importinventoryrepo.save(existingInv);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05065", newId, "2024");
			} else {
				existingCon.setExaminedPackages(cnData.getExaminedPackages());
				existingCon.setPackagesDeStuffed(cnData.getPackagesDeStuffed());
				existingCon.setPackagesStuffed(cnData.getPackagesStuffed());
				existingCon.setTypeOfContainer(cnData.getTypeOfContainer());
				existingCon.setGateOutType(cnData.getGateOutType());
				existingCon.setSpecialDelivery(cnData.getSpecialDelivery());
				existingCon.setContainerExamRemarks(cnData.getContainerExamRemarks());
				existingCon.setContainerExamApprovedBy(user);
				existingCon.setContainerExamApprovedDate(new Date());
				existingCon.setDoNo(data.getDoNo());
				existingCon.setDoDate(data.getDoDate());
				existingCon.setDoValidityDate(data.getDoValidityDate());
				existingCon.setOocNo(data.getOocNo());
				existingCon.setOocDate(data.getOocDate());
				cfigmcnrepo.save(existingCon);
			}
			// }

		});

		Cfigmcrg crgData = cfigmcrgrepo.getData3(cid, bid, crg.getIgmNo(), crg.getIgmLineNo());

		List<Cfigmcn> cnData = cfigmcnrepo.getAllData4(cid, bid, crg.getIgmNo(), crg.getIgmLineNo());

		ImportExaminationDTO data1 = new ImportExaminationDTO();
		data1.setCn(cnData);
		data1.setCrg(crgData);
		data1.setDoDate(data.getDoDate());
		data1.setDoNo(data.getDoNo());
		data1.setDoValidityDate(data.getDoValidityDate());
		data1.setOocDate(data.getOocDate());
		data1.setOocNo(data.getOocNo());

		return new ResponseEntity<>(data1, HttpStatus.OK);

	}

	@GetMapping("/getSingleContainerForExamination")
	public ResponseEntity<?> getSingleContainerForExamination(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm,
			@RequestParam("container") String container) {

		List<Object[]> cn = cfigmcnrepo.getSingleContainerForSealCutting1(cid, bid, igm, container);

		if (cn.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
		} else {
			boolean holdStatusFlag = cn.stream().anyMatch(record -> "H".equals(record[40].toString()));

			if (holdStatusFlag) {
				return new ResponseEntity<>("Container is hold", HttpStatus.BAD_REQUEST);
			}

			
			Object[] singleResult = cn.get(0);
			if (singleResult[19] == null || singleResult[19].toString().isEmpty()) {
				return new ResponseEntity<>("Seal cutting process not completed", HttpStatus.BAD_REQUEST);
			}
			List<Object[]> crg = cfigmcrgrepo.getCrgData(cid, bid, igm, container);

			Map<String, Object> response = new HashMap<>();
			response.put("singleResult", singleResult);
			response.put("crg", crg);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	@PostMapping("/saveContainerWiseExamintion")
	public ResponseEntity<?> saveContainerWiseExamintion(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("user") String user,
			@RequestBody ContainerExaminationDTO data) {
		com.cwms.entities.ContainerExaminationDTO.Container con = data.getCon();
		List<com.cwms.entities.ContainerExaminationDTO.Cargo> cargo = data.getCargo();
		System.out.println("cargo " + cargo.size());

		List<Cfigmcn> containerData = cfigmcnrepo.getSingleContainerForSealCutting(cid, bid, con.getIgmNo(),
				con.getContainerNo());

		if (containerData.isEmpty()) {
			return new ResponseEntity<>("Container data not found.", HttpStatus.BAD_REQUEST);
		}

		for (com.cwms.entities.ContainerExaminationDTO.Cargo c : cargo) {
			Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());
			if (crg == null) {
				return new ResponseEntity<>("Cargo data with item no " + c.getIgmLineNo() + " not found.",
						HttpStatus.BAD_REQUEST);
			}

			crg.setExaminationRemarks(con.getContainerExamRemarks());

			cfigmcrgrepo.save(crg);

		}
		String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05065", "2024");

		String[] parts = lastValue.split("/");
		String baseId = parts[0];
		String financialYear = parts[1];

		// Increment the base ID
		String newBaseId = incrementBaseId(baseId);

		// Get the current financial year
		String currentFinancialYear = getCurrentFinancialYear();

		// Construct the new ID
		String newId = newBaseId + "/" + currentFinancialYear;

		containerData.stream().forEach(con1 -> {
			if (con1.getContainerExamWoTransId() == null || con1.getContainerExamWoTransId().isEmpty()) {

				con1.setContainerExamWoTransDate(new Date());
				con1.setContainerExamWoTransId(newId);
				con1.setContainerExamDate(new Date());
				con1.setExaminedPackages(con.getExaminedPackages());
				con1.setPackagesDeStuffed(con.getPackagesDeStuffed());
				con1.setPackagesStuffed(con.getPackagesStuffed());
				con1.setTypeOfContainer(con.getTypeOfContainer());
				con1.setGateOutType(con.getGateOutType());
				con1.setSpecialDelivery(con.getSpecialDelivery());
				con1.setContainerExamRemarks(con.getContainerExamRemarks());
				con1.setContainerExamCreatedBy(user);
				con1.setContainerExamCreatedDate(new Date());
				con1.setContainerExamApprovedBy(user);
				con1.setContainerExamApprovedDate(new Date());
				con1.setContainerExamStatus("Y");
				con1.setDoNo(data.getDoNo());
				con1.setDoDate(data.getDoDate());
				con1.setDoValidityDate(data.getDoValidityDate());
				con1.setOocNo(data.getOocNo());
				con1.setOocDate(data.getOocDate());

				cfigmcnrepo.save(con1);
				
				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, con1.getIgmTransId(), con1.getIgmNo(), 
						con1.getContainerNo(), con1.getGateInId());
				
				if(existingInv != null) {
					existingInv.setContainerExamStatus("Y");
					existingInv.setContainerExamDate(new Date());
					
					
					importinventoryrepo.save(existingInv);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05065", newId, "2024");
			} else {
				con1.setExaminedPackages(con.getExaminedPackages());
				con1.setPackagesDeStuffed(con.getPackagesDeStuffed());
				con1.setPackagesStuffed(con.getPackagesStuffed());
				con1.setTypeOfContainer(con.getTypeOfContainer());
				con1.setGateOutType(con.getGateOutType());
				con1.setSpecialDelivery(con.getSpecialDelivery());
				con1.setContainerExamRemarks(con.getContainerExamRemarks());
				con1.setContainerExamApprovedBy(user);
				con1.setContainerExamApprovedDate(new Date());
				con1.setDoNo(data.getDoNo());
				con1.setDoDate(data.getDoDate());
				con1.setDoValidityDate(data.getDoValidityDate());
				con1.setOocNo(data.getOocNo());
				con1.setOocDate(data.getOocDate());
				cfigmcnrepo.save(con1);
			}

		});

		List<Object[]> cn = cfigmcnrepo.getSingleContainerForSealCutting1(cid, bid, con.getIgmNo(),
				con.getContainerNo());

		Object[] singleResult = cn.get(0);

		List<Object[]> crg = cfigmcrgrepo.getCrgData(cid, bid, con.getIgmNo(), con.getContainerNo());

		Map<String, Object> response = new HashMap<>();
		response.put("singleResult", singleResult);
		response.put("crg", crg);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/searchContainerForDestuff")
	public ResponseEntity<?> searchContainerForDestuff(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("container") String container) {
		List<Cfigmcn> getContainer = cfigmcnrepo.searchContainer(cid, bid, container);
		if (getContainer.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
		}
		
		Boolean exist = getContainer.stream().anyMatch(c -> "H".equals(c.getHoldStatus()));

		if(exist) {
			return new ResponseEntity<>("Container is hold..", HttpStatus.CONFLICT);
		}
		
		List<Cfigmcrg> data = new ArrayList<>();

		getContainer.stream().forEach(con -> {
			Cfigmcrg crg = cfigmcrgrepo.getDataByIgmAndLine(cid, bid, con.getIgmTransId(), con.getIgmNo(),
					con.getIgmLineNo());

			if (crg != null) {
				crg.setNoOfPackages(new BigDecimal(con.getNoOfPackages()));
				crg.setGrossWeight(con.getGrossWt());
				data.add(crg);
			}
		});

		Map<String, Object> cn = new HashMap<>();
		cn.put("container", getContainer.get(0));
		cn.put("cargo", data);

		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans1(cid, bid, getContainer.get(0).getIgmTransId(),
				getContainer.get(0).getIgmNo());

		if (igm != null) {
			cn.put("igm", igm);
		}

		return new ResponseEntity<>(cn, HttpStatus.OK);

	}

//	@GetMapping("/searchMainHeader")
//	public ResponseEntity<?> searchMainHeader(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
//			@RequestParam(name = "igm", required = false) String igm,
//			@RequestParam(name = "item", required = false) String item,
//			@RequestParam(name = "blNo", required = false) String blNo,
//			@RequestParam(name = "beNo", required = false) String beNo,
//			@RequestParam(name = "container", required = false) String container) {
//
//		List<String> list = new ArrayList<>();
//
//		List<Cfigmcn> cn = cfigmcnrepo.searchMainHeader(cid, bid, igm, item, blNo, beNo, container);
//
//		List<String> cnlist = new ArrayList<>();
//
//		Object[] sealData = null;
//
//		if (cn.isEmpty()) {
//			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
//		}
//
//		list.add("P00201");
//		list.add("P00202");
//
//		boolean containsEmptyGateInId = cn.stream().anyMatch(c -> c.getGateInId() == null || c.getGateInId().isEmpty());
//
//		if (containsEmptyGateInId) {
//			list.add("P00203");
//			cnlist = cfigmcnrepo.getSearcgCon1(cid, bid, igm, item, container, blNo);
//			sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
//			System.out.println("cnlist " + cnlist);
//			Map<String, Object> c = new HashMap<>();
//			c.put("list", list);
//			c.put("igm", cn.get(0).getIgmNo());
//			c.put("sealData", sealData);
//			c.put("igmtrans", cn.get(0).getIgmTransId());
//			c.put("con", container);
//			c.put("cnlist", cnlist);
//			c.put("gateInId", "");
//			if (container.isEmpty()) {
//				c.put("sealCutStatus", "");
//			} else {
//				c.put("sealCutStatus", "");
//			}
//			c.put("sealData", sealData);
//
//			return new ResponseEntity<>(c, HttpStatus.OK);
//		}
//
//		boolean containsEmptySealCuttingId = cn.stream()
//				.anyMatch(c -> (c.getSealCutWoTransId() == null || c.getSealCutWoTransId().isEmpty())
//						&& "FCL".equals(c.getContainerStatus()));
//
//		if (containsEmptySealCuttingId) {
//			list.add("P00203");
//			list.add("P00204");
//
//			System.out.println("sealData " + sealData);
//			Map<String, Object> c = new HashMap<>();
//			c.put("list", list);
//			c.put("igm", cn.get(0).getIgmNo());
//			c.put("igmtrans", cn.get(0).getIgmTransId());
//			c.put("cnlist", cnlist);
//			c.put("con", container);
//			if (container.isEmpty()) {
//				c.put("sealCutStatus", "itemwise");
//				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
//
//				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());
//
//				if (in != null) {
//					c.put("gateInId", in);
//				} else {
//					c.put("gateInId", "");
//				}
//			} else {
//				c.put("sealCutStatus", "containerwise");
//				c.put("gateInId", "");
//				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
//			}
//			c.put("sealData", sealData);
//			return new ResponseEntity<>(c, HttpStatus.OK);
//		}
//
//		boolean containsExamination = cn.stream()
//				.anyMatch(c -> (c.getContainerExamWoTransId() == null || c.getContainerExamWoTransId().isEmpty())
//						&& "FCL".equals(c.getContainerStatus()));
//
//		if (containsExamination) {
//			list.add("P00203");
//			list.add("P00204");
//			list.add("P00205");
//
//			Map<String, Object> c = new HashMap<>();
//			c.put("list", list);
//			c.put("igm", cn.get(0).getIgmNo());
//			c.put("igmtrans", cn.get(0).getIgmTransId());
//			c.put("cnlist", cnlist);
//			c.put("con", container);
//			if (container.isEmpty()) {
//				c.put("sealCutStatus", "itemwise");
//				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
//				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());
//
//				if (in != null) {
//					c.put("gateInId", in);
//				} else {
//					c.put("gateInId", "");
//				}
//			} else {
//				c.put("sealCutStatus", "containerwise");
//				c.put("gateInId", "");
//				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
//			}
//			c.put("sealData", sealData);
//
//			return new ResponseEntity<>(c, HttpStatus.OK);
//		}
//
//		boolean containsDestuff = cn.stream()
//				.anyMatch(c -> (c.getDestuffWoTransId() == null || c.getDestuffWoTransId().isEmpty())
//						&& "CRG".equals(c.getGateOutType()));
//
//		if (containsDestuff) {
//			list.add("P00203");
//			list.add("P00204");
//			list.add("P00205");
//			list.add("P00206");
//
//			Map<String, Object> c = new HashMap<>();
//			c.put("list", list);
//			c.put("igm", cn.get(0).getIgmNo());
//			c.put("igmtrans", cn.get(0).getIgmTransId());
//			c.put("cnlist", cnlist);
//			c.put("con", container);
//			if (container.isEmpty()) {
//				c.put("sealCutStatus", "itemwise");
//				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
//				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());
//
//				if (in != null) {
//					c.put("gateInId", in);
//				} else {
//					c.put("gateInId", "");
//				}
//			} else {
//				c.put("sealCutStatus", "containerwise");
//				c.put("gateInId", "");
//				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
//			}
//			c.put("sealData", sealData);
//
//			return new ResponseEntity<>(c, HttpStatus.OK);
//		}
//
//		boolean containsGatePass = cn.stream().anyMatch(c -> c.getGatePassNo() == null || c.getGatePassNo().isEmpty());
//
//		if (containsGatePass) {
//			list.add("P00203");
//			list.add("P00204");
//			list.add("P00205");
//			list.add("P00206");
//			list.add("P00208");
//
//			boolean cargoExamination = cn.stream().anyMatch(c -> "LCL".equals(c.getContainerStatus()));
//			if (cargoExamination) {
//				list.add("P00210");
//			}
//
//			Map<String, Object> c = new HashMap<>();
//			c.put("list", list);
//			c.put("igm", cn.get(0).getIgmNo());
//			c.put("igmtrans", cn.get(0).getIgmTransId());
//			c.put("cnlist", cnlist);
//			c.put("con", container);
//			if (container.isEmpty()) {
//				c.put("sealCutStatus", "itemwise");
//				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
//				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());
//
//				if (in != null) {
//					c.put("gateInId", in);
//				} else {
//					c.put("gateInId", "");
//				}
//			} else {
//				c.put("sealCutStatus", "containerwise");
//				c.put("gateInId", "");
//				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
//			}
//			c.put("sealData", sealData);
//
//			return new ResponseEntity<>(c, HttpStatus.OK);
//		}
//
//		boolean containsGateOut = cn.stream().anyMatch(c -> c.getGateOutId() == null || c.getGateOutId().isEmpty());
//
//		if (containsGateOut) {
//			list.add("P00203");
//			list.add("P00204");
//			list.add("P00205");
//			list.add("P00206");
//			list.add("P00208");
//			list.add("P00209");
//			boolean cargoExamination = cn.stream().anyMatch(c -> "LCL".equals(c.getContainerStatus()));
//			if (cargoExamination) {
//				list.add("P00210");
//			}
//
//			
//			Map<String, Object> c = new HashMap<>();
//			c.put("list", list);
//			c.put("igm", cn.get(0).getIgmNo());
//			c.put("igmtrans", cn.get(0).getIgmTransId());
//			c.put("cnlist", cnlist);
//			c.put("con", container);
//			if (container.isEmpty()) {
//				c.put("sealCutStatus", "itemwise");
//				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
//				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());
//
//				if (in != null) {
//					c.put("gateInId", in);
//				} else {
//					c.put("gateInId", "");
//				}
//			} else {
//				c.put("sealCutStatus", "containerwise");
//				c.put("gateInId", "");
//				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
//			}
//			c.put("sealData", sealData);
//
//			return new ResponseEntity<>(c, HttpStatus.OK);
//		}
//
//		Map<String, Object> c = new HashMap<>();
//		c.put("list", list);
//		c.put("igm", cn.get(0).getIgmNo());
//		c.put("igmtrans", cn.get(0).getIgmTransId());
//		c.put("cnlist", cnlist);
//		c.put("con", container);
//		if (container.isEmpty()) {
//			c.put("sealCutStatus", "");
//		} else {
//			c.put("sealCutStatus", "");
//
//		}
//		c.put("gateInId", "");
//		c.put("sealData", sealData);
//
//		return new ResponseEntity<>(c, HttpStatus.OK);
//	}
	
	
	@GetMapping("/searchMainHeader")
	public ResponseEntity<?> searchMainHeader(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "igm", required = false) String igm,
			@RequestParam(name = "item", required = false) String item,
			@RequestParam(name = "blNo", required = false) String blNo,
			@RequestParam(name = "beNo", required = false) String beNo,
			@RequestParam(name = "container", required = false) String container) {

		List<String> list = new ArrayList<>();

		List<Cfigmcn> cn = cfigmcnrepo.searchMainHeader(cid, bid, igm, item, blNo, beNo, container);

		List<String> cnlist = new ArrayList<>();

		Object[] sealData = null;

		if (cn.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		list.add("P00201");
		list.add("P00202");

		boolean containsEmptyGateInId = cn.stream().anyMatch(c -> c.getGateInId() == null || c.getGateInId().isEmpty());

		if (containsEmptyGateInId) {
			list.add("P00203");
			cnlist = cfigmcnrepo.getSearcgCon1(cid, bid, igm, item, container, blNo);
			sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
			System.out.println("cnlist " + cnlist);
			Map<String, Object> c = new HashMap<>();
			c.put("list", list);
			c.put("igm", cn.get(0).getIgmNo());
			c.put("sealData", sealData);
			c.put("igmtrans", cn.get(0).getIgmTransId());
			c.put("con", container);
			c.put("cnlist", cnlist);
			c.put("gateInId", "");
			if (container.isEmpty()) {
				c.put("sealCutStatus", "");
			} else {
				c.put("sealCutStatus", "");
			}
			c.put("sealData", sealData);

			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		boolean containsEmptySealCuttingId = cn.stream()
				.anyMatch(c -> (c.getSealCutWoTransId() == null || c.getSealCutWoTransId().isEmpty())
						&& "FCL".equals(c.getContainerStatus()));

		if (containsEmptySealCuttingId) {
			list.add("P00203");
			list.add("P00204");

			System.out.println("sealData " + sealData);
			Map<String, Object> c = new HashMap<>();
			c.put("list", list);
			c.put("igm", cn.get(0).getIgmNo());
			c.put("igmtrans", cn.get(0).getIgmTransId());
			c.put("cnlist", cnlist);
			c.put("con", container);
			if (container.isEmpty()) {
				c.put("sealCutStatus", "itemwise");
				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);

				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());

				if (in != null) {
					c.put("gateInId", in);
				} else {
					c.put("gateInId", "");
				}
			} else {
				c.put("sealCutStatus", "containerwise");
				c.put("gateInId", "");
				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
			}
			c.put("sealData", sealData);
			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		boolean containsExamination = cn.stream()
				.anyMatch(c -> (c.getContainerExamWoTransId() == null || c.getContainerExamWoTransId().isEmpty())
						&& "FCL".equals(c.getContainerStatus()));

		if (containsExamination) {
			list.add("P00203");
			list.add("P00204");
			list.add("P00205");

			Map<String, Object> c = new HashMap<>();
			c.put("list", list);
			c.put("igm", cn.get(0).getIgmNo());
			c.put("igmtrans", cn.get(0).getIgmTransId());
			c.put("cnlist", cnlist);
			c.put("con", container);
			if (container.isEmpty()) {
				c.put("sealCutStatus", "itemwise");
				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());

				if (in != null) {
					c.put("gateInId", in);
				} else {
					c.put("gateInId", "");
				}
			} else {
				c.put("sealCutStatus", "containerwise");
				c.put("gateInId", "");
				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
			}
			c.put("sealData", sealData);

			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		boolean containsDestuff = cn.stream()
				.anyMatch(c -> (c.getDestuffWoTransId() == null || c.getDestuffWoTransId().isEmpty())
						&& "CRG".equals(c.getGateOutType()));

		if (containsDestuff) {
			list.add("P00203");
			list.add("P00204");
			list.add("P00205");
			list.add("P00206");

			Map<String, Object> c = new HashMap<>();
			c.put("list", list);
			c.put("igm", cn.get(0).getIgmNo());
			c.put("igmtrans", cn.get(0).getIgmTransId());
			c.put("cnlist", cnlist);
			c.put("con", container);
			if (container.isEmpty()) {
				c.put("sealCutStatus", "itemwise");
				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());

				if (in != null) {
					c.put("gateInId", in);
				} else {
					c.put("gateInId", "");
				}
			} else {
				c.put("sealCutStatus", "containerwise");
				c.put("gateInId", "");
				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
			}
			c.put("sealData", sealData);

			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		boolean containsGatePass = cn.stream().anyMatch(c -> c.getGatePassNo() == null || c.getGatePassNo().isEmpty());

		if (containsGatePass) {
			list.add("P00203");
			list.add("P00204");
			list.add("P00205");
			list.add("P00206");
			list.add("P00208");

			boolean cargoExamination = cn.stream().anyMatch(c -> "LCL".equals(c.getContainerStatus()));
			if (cargoExamination) {
				list.add("P00210");
			}

			Map<String, Object> c = new HashMap<>();
			c.put("list", list);
			c.put("igm", cn.get(0).getIgmNo());
			c.put("igmtrans", cn.get(0).getIgmTransId());
			c.put("cnlist", cnlist);
			c.put("con", container);
			if (container.isEmpty()) {
				c.put("sealCutStatus", "itemwise");
				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());

				if (in != null) {
					c.put("gateInId", in);
				} else {
					c.put("gateInId", "");
				}
			} else {
				c.put("sealCutStatus", "containerwise");
				c.put("gateInId", "");
				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
			}
			c.put("sealData", sealData);

			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		boolean containsGateOut = cn.stream().anyMatch(c -> c.getGateOutId() == null || c.getGateOutId().isEmpty());

		if (containsGateOut) {
			list.add("P00203");
			list.add("P00204");
			list.add("P00205");
			list.add("P00206");
			list.add("P00208");
			list.add("P00209");
			boolean cargoExamination = cn.stream().anyMatch(c -> "LCL".equals(c.getContainerStatus()));
			if (cargoExamination) {
				list.add("P00210");
			}

			
			Map<String, Object> c = new HashMap<>();
			c.put("list", list);
			c.put("igm", cn.get(0).getIgmNo());
			c.put("igmtrans", cn.get(0).getIgmTransId());
			c.put("cnlist", cnlist);
			c.put("con", container);
			if (container.isEmpty()) {
				c.put("sealCutStatus", "itemwise");
				sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
				Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());

				if (in != null) {
					c.put("gateInId", in);
				} else {
					c.put("gateInId", "");
				}
			} else {
				c.put("sealCutStatus", "containerwise");
				c.put("gateInId", "");
				sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
			}
			c.put("sealData", sealData);

			return new ResponseEntity<>(c, HttpStatus.OK);
		}

		list.add("P00203");
		list.add("P00204");
		list.add("P00205");
		list.add("P00206");
		list.add("P00208");
		list.add("P00209");
		boolean cargoExamination = cn.stream().anyMatch(c -> "LCL".equals(c.getContainerStatus()));
		if (cargoExamination) {
			list.add("P00210");
		}

		
		Map<String, Object> c = new HashMap<>();
		c.put("list", list);
		c.put("igm", cn.get(0).getIgmNo());
		c.put("igmtrans", cn.get(0).getIgmTransId());
		c.put("cnlist", cnlist);
		c.put("con", container);
		if (container.isEmpty()) {
			c.put("sealCutStatus", "itemwise");
			sealData = cfigmcnrepo.getSealCutData(cid, bid, igm, item, container, blNo, beNo);
			Object[] in = gateinrepo.getAll1(cid, bid, cn.get(0).getIgmTransId(), cn.get(0).getIgmNo());

			if (in != null) {
				c.put("gateInId", in);
			} else {
				c.put("gateInId", "");
			}
		} else {
			c.put("sealCutStatus", "containerwise");
			c.put("gateInId", "");
			sealData = cfigmcnrepo.getSealCutData1(cid, bid, igm, item, container, blNo, beNo);
		}
		c.put("sealData", sealData);

		return new ResponseEntity<>(c, HttpStatus.OK);
	}

	@GetMapping("/getSearchCon")
	public ResponseEntity<?> getContainer(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "con", required = false) String con) {
		List<String> list = cfigmcnrepo.getSearcgCon(cid, bid, con);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getSearchSingleCon")
	public ResponseEntity<?> getSearchSingleCon(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "con", required = false) String con) {
		List<Object[]> list = cfigmcnrepo.getContainers1(cid, bid, con);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getItemsList")
	public ResponseEntity<?> getItems(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> list = cfigmcnrepo.getItem(cid, bid, igm);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getContainerList")
	public ResponseEntity<?> getContainerList(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> list = cfigmcnrepo.getCon(cid, bid, igm);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getItemsList1")
	public ResponseEntity<?> getItems1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> list = cfigmcnrepo.getItem1(cid, bid, igm);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getContainerList1")
	public ResponseEntity<?> getContainerList1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> list = cfigmcnrepo.getCon1(cid, bid, igm);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getItemsList2")
	public ResponseEntity<?> getItems2(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> list = cfigmcnrepo.getItem2(cid, bid, igm);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getContainerList2")
	public ResponseEntity<?> getContainerList2(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> list = cfigmcnrepo.getCon2(cid, bid, igm);

		if (list.isEmpty()) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}

	@GetMapping("/getSingleCon")
	public ResponseEntity<?> getSingleCon(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("con") String con) {
		Cfigmcn cn = cfigmcnrepo.getConByConNo(cid, bid, con);

		if (cn == null) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(cn, HttpStatus.OK);
		}
	}

	@GetMapping("/getSingleCon1")
	public ResponseEntity<?> getSingleCon1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("con") String con) {
		Object[] cn = cfigmcnrepo.getConByConNo1(cid, bid, con);

		if (cn == null) {
			return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(cn, HttpStatus.OK);
		}
	}

	@GetMapping("/getDataOfLCLDestuff")
	public ResponseEntity<?> getDataOfLCLDestuff(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("con") String con) {

		List<Object[]> data = cfigmcnrepo.getSearchForExamination(cid, bid, con);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	@GetMapping("/importContainerHistory")
	public ResponseEntity<?> importConHistory(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("item") String itemNo, @RequestParam("blNo") String blNo,
			@RequestParam("con") String containerNo, @RequestParam("beNo") String beNo) {

		try {

			List<Object[]> findData = cfigmcnrepo.importContainerHistory1(cid, bid, igm, itemNo, blNo, containerNo,
					beNo);

			if (findData.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {

				Object[] igmData = findData.get(0);

				String igmTransId = String.valueOf(igmData[1]);
				String igmNo = String.valueOf(igmData[0]);
				String igmLineNo = String.valueOf(igmData[2]);
				String conNo = String.valueOf(igmData[5]);

				Map<String, Object> result = new HashMap<>();

				Object record1 = cfigmcnrepo.importContainerHistory2(cid, bid, igmNo, igmTransId, igmLineNo, conNo);

				result.put("record1", record1);

				List<Object[]> record2 = cfigmcnrepo.importContainerHistory3(cid, bid, igmNo, igmTransId, igmLineNo);

				result.put("record2", record2);

				List<Object[]> record3 = cfigmcnrepo.importContainerHistory4(cid, bid, igmNo, igmTransId, igmLineNo);

				result.put("record3", record3);

				List<Object[]> record4 = cfigmcnrepo.importContainerHistory5(cid, bid, igmNo, igmTransId, igmLineNo);

				result.put("record4", record4);

				return new ResponseEntity<>(result, HttpStatus.OK);

			}
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/commonContainerHistory")
	public ResponseEntity<?> commonContainerHistory(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("con") String containerNo) {

		try {

			List<Object[]> findData = cfigmcnrepo.getDataByContainerNo(cid, bid, containerNo);

			List<Object[]> findData1 = exportInvRepo.getDataByContNo(cid, bid, containerNo);

			String status = "";

			if (findData.isEmpty() && !findData1.isEmpty()) {
				status = "Export";
			} else if (!findData.isEmpty() && findData1.isEmpty()) {
				status = "Import";
			} else if (findData.isEmpty() && findData1.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			} else {
				Object[] o1 = findData.get(0);
				Object[] o2 = findData1.get(0);

				String createdDate1 = (String.valueOf(o1[7]));
				String createdDate2 = (String.valueOf(o2[4]));

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				// format
				try {
					Date date1 = sdf.parse(createdDate1);
					Date date2 = sdf.parse(createdDate2);

					if (date1.after(date2)) {
						status = "Import";
					} else {
						status = "Export";
					}

					System.out.println("Status: " + status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if ("Import".equals(status)) {

				Object[] igmData = findData.get(0);

				String igmTransId = String.valueOf(igmData[1]);
				String igmNo = String.valueOf(igmData[0]);
				String igmLineNo = String.valueOf(igmData[2]);
				String conNo = String.valueOf(igmData[5]);

				List<Map<String, String>> result = new ArrayList<>();

				List<Object[]> record1 = cfigmcnrepo.getContatainerData(cid, bid, igmNo, igmTransId, igmLineNo, conNo);

				if (!record1.isEmpty()) {

					Object[] a = record1.get(0);

					Map<String, String> r1 = new HashMap<>();

					r1.put("containerNo",
							(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
					r1.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
					r1.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
					r1.put("fcl", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
					r1.put("deliveryMode",
							(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
					r1.put("agent", (a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
					r1.put("nominator", (a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
					r1.put("transId", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
					r1.put("desc", "Job Order Updated");
					r1.put("vcnNo", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
					r1.put("vessel", (a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8]) : "");
					r1.put("processDate",
							(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
					r1.put("processBy",
							(a[10] != null && !String.valueOf(a[10]).isEmpty()) ? String.valueOf(a[10]) : "");
					r1.put("currentLocation",
							(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11]) : "");
					r1.put("pod", (a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12]) : "");
					r1.put("forceEntry",
							(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13]) : "");
					r1.put("holdStatus",
							(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14]) : "");

					result.add(r1);

					if (a[15] != null && !String.valueOf(a[15]).isEmpty()) {

						Map<String, String> r2 = new HashMap<>();

						r2.put("containerNo",
								(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
						r2.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
						r2.put("category",
								(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
						r2.put("fcl", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
						r2.put("deliveryMode",
								(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
						r2.put("agent", (a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
						r2.put("nominator",
								(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
						r2.put("transId",
								(a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
						r2.put("desc", "Received Import Full");
						r2.put("vcnNo", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
						r2.put("vessel", (a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8]) : "");
						r2.put("processDate",
								(a[16] != null && !String.valueOf(a[16]).isEmpty()) ? String.valueOf(a[16]) : "");
						r2.put("processBy",
								(a[17] != null && !String.valueOf(a[17]).isEmpty()) ? String.valueOf(a[17]) : "");
						r2.put("currentLocation",
								(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11]) : "");
						r2.put("pod", (a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12]) : "");
						r2.put("forceEntry",
								(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13]) : "");
						r2.put("holdStatus",
								(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14]) : "");

						result.add(r2);

						if (a[18] != null && !String.valueOf(a[18]).isEmpty()) {

							Map<String, String> r3 = new HashMap<>();

							r3.put("containerNo",
									(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
							r3.put("size",
									(a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
							r3.put("category",
									(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
							r3.put("fcl",
									(a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
							r3.put("deliveryMode",
									(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
							r3.put("agent",
									(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
							r3.put("nominator",
									(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
							r3.put("transId",
									(a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
							r3.put("desc", "Import Seal Cutting Done");
							r3.put("vcnNo",
									(a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
							r3.put("vessel",
									(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8]) : "");
							r3.put("processDate",
									(a[20] != null && !String.valueOf(a[20]).isEmpty()) ? String.valueOf(a[20]) : "");
							r3.put("processBy",
									(a[19] != null && !String.valueOf(a[19]).isEmpty()) ? String.valueOf(a[19]) : "");
							r3.put("currentLocation",
									(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11]) : "");
							r3.put("pod",
									(a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12]) : "");
							r3.put("forceEntry",
									(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13]) : "");
							r3.put("holdStatus",
									(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14]) : "");

							result.add(r3);

							if (a[21] != null && !String.valueOf(a[21]).isEmpty()) {

								Map<String, String> r4 = new HashMap<>();

								r4.put("containerNo",
										(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
								r4.put("size",
										(a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
								r4.put("category",
										(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
								r4.put("fcl",
										(a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
								r4.put("deliveryMode",
										(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
								r4.put("agent",
										(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
								r4.put("nominator",
										(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
								r4.put("transId",
										(a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
								r4.put("desc", "Import Examination Done");
								r4.put("vcnNo",
										(a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
								r4.put("vessel",
										(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8]) : "");
								r4.put("processDate",
										(a[22] != null && !String.valueOf(a[22]).isEmpty()) ? String.valueOf(a[22])
												: "");
								r4.put("processBy",
										(a[23] != null && !String.valueOf(a[23]).isEmpty()) ? String.valueOf(a[23])
												: "");
								r4.put("currentLocation",
										(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11])
												: "");
								r4.put("pod",
										(a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12])
												: "");
								r4.put("forceEntry",
										(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13])
												: "");
								r4.put("holdStatus",
										(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14])
												: "");

								result.add(r4);

								if (a[24] != null && !String.valueOf(a[24]).isEmpty()) {

									Map<String, String> r5 = new HashMap<>();

									r5.put("containerNo",
											(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0])
													: "");
									r5.put("size",
											(a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1])
													: "");
									r5.put("category",
											(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2])
													: "");
									r5.put("fcl",
											(a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3])
													: "");
									r5.put("deliveryMode",
											(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4])
													: "");
									r5.put("agent",
											(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
													: "");
									r5.put("nominator",
											(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
													: "");
									r5.put("transId",
											(a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6])
													: "");
									r5.put("desc", "Import Destuff Done");
									r5.put("vcnNo",
											(a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7])
													: "");
									r5.put("vessel",
											(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8])
													: "");
									r5.put("processDate",
											(a[25] != null && !String.valueOf(a[25]).isEmpty()) ? String.valueOf(a[25])
													: "");
									r5.put("processBy",
											(a[26] != null && !String.valueOf(a[26]).isEmpty()) ? String.valueOf(a[26])
													: "");
									r5.put("currentLocation",
											(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11])
													: "");
									r5.put("pod",
											(a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12])
													: "");
									r5.put("forceEntry",
											(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13])
													: "");
									r5.put("holdStatus",
											(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14])
													: "");

									result.add(r5);

								}

								if (a[27] != null && !String.valueOf(a[27]).isEmpty()) {

									Map<String, String> r5 = new HashMap<>();

									r5.put("containerNo",
											(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0])
													: "");
									r5.put("size",
											(a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1])
													: "");
									r5.put("category",
											(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2])
													: "");
									r5.put("fcl",
											(a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3])
													: "");
									r5.put("deliveryMode",
											(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4])
													: "");
									r5.put("agent",
											(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
													: "");
									r5.put("nominator",
											(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
													: "");
									r5.put("transId",
											(a[27] != null && !String.valueOf(a[27]).isEmpty()) ? String.valueOf(a[27])
													: "");
									r5.put("desc", "Import Destuff Done");
									r5.put("vcnNo",
											(a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7])
													: "");
									r5.put("vessel",
											(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8])
													: "");
									r5.put("processDate",
											(a[28] != null && !String.valueOf(a[28]).isEmpty()) ? String.valueOf(a[28])
													: "");
									r5.put("processBy",
											(a[29] != null && !String.valueOf(a[29]).isEmpty()) ? String.valueOf(a[29])
													: "");
									r5.put("currentLocation",
											(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11])
													: "");
									r5.put("pod",
											(a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12])
													: "");
									r5.put("forceEntry",
											(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13])
													: "");
									r5.put("holdStatus",
											(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14])
													: "");

									result.add(r5);

								}

								if (a[30] != null && !String.valueOf(a[30]).isEmpty()) {

									Map<String, String> r5 = new HashMap<>();

									r5.put("containerNo",
											(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0])
													: "");
									r5.put("size",
											(a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1])
													: "");
									r5.put("category",
											(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2])
													: "");
									r5.put("fcl",
											(a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3])
													: "");
									r5.put("deliveryMode",
											(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4])
													: "");
									r5.put("agent",
											(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
													: "");
									r5.put("nominator",
											(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
													: "");
									r5.put("transId",
											(a[30] != null && !String.valueOf(a[30]).isEmpty()) ? String.valueOf(a[30])
													: "");
									r5.put("desc", "Import Gate Pass Done");
									r5.put("vcnNo",
											(a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7])
													: "");
									r5.put("vessel",
											(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8])
													: "");
									r5.put("processDate",
											(a[32] != null && !String.valueOf(a[32]).isEmpty()) ? String.valueOf(a[32])
													: "");
									r5.put("processBy",
											(a[31] != null && !String.valueOf(a[31]).isEmpty()) ? String.valueOf(a[31])
													: "");
									r5.put("currentLocation",
											(a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11])
													: "");
									r5.put("pod",
											(a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12])
													: "");
									r5.put("forceEntry",
											(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13])
													: "");
									r5.put("holdStatus",
											(a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14])
													: "");

									result.add(r5);

									if (a[33] != null && !String.valueOf(a[33]).isEmpty()) {

										Map<String, String> r6 = new HashMap<>();

										r6.put("containerNo",
												(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0])
														: "");
										r6.put("size",
												(a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1])
														: "");
										r6.put("category",
												(a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2])
														: "");
										r6.put("fcl",
												(a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3])
														: "");
										r6.put("deliveryMode",
												(a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4])
														: "");
										r6.put("agent",
												(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
														: "");
										r6.put("nominator",
												(a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5])
														: "");
										r6.put("transId",
												(a[33] != null && !String.valueOf(a[33]).isEmpty())
														? String.valueOf(a[33])
														: "");
										r6.put("desc", "Import Gate Out Done");
										r6.put("vcnNo",
												(a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7])
														: "");
										r6.put("vessel",
												(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8])
														: "");
										r6.put("processDate",
												(a[34] != null && !String.valueOf(a[34]).isEmpty())
														? String.valueOf(a[34])
														: "");
										r6.put("processBy",
												(a[35] != null && !String.valueOf(a[35]).isEmpty())
														? String.valueOf(a[35])
														: "");
										r6.put("currentLocation",
												(a[11] != null && !String.valueOf(a[11]).isEmpty())
														? String.valueOf(a[11])
														: "");
										r6.put("pod",
												(a[12] != null && !String.valueOf(a[12]).isEmpty())
														? String.valueOf(a[12])
														: "");
										r6.put("forceEntry",
												(a[13] != null && !String.valueOf(a[13]).isEmpty())
														? String.valueOf(a[13])
														: "");
										r6.put("holdStatus",
												(a[14] != null && !String.valueOf(a[14]).isEmpty())
														? String.valueOf(a[14])
														: "");

										result.add(r6);

										return new ResponseEntity<>(result, HttpStatus.OK);

									}

								} else {
									return new ResponseEntity<>(result, HttpStatus.OK);
								}

							} else {
								return new ResponseEntity<>(result, HttpStatus.OK);
							}

						} else {
							return new ResponseEntity<>(result, HttpStatus.OK);
						}

					} else {
						return new ResponseEntity<>(result, HttpStatus.OK);
					}

					return new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

			} else {
				Object[] igmData = findData1.get(0);

				String gateInId = String.valueOf(igmData[3]);
				String conNo = String.valueOf(igmData[2]);

				List<Map<String, String>> result = new ArrayList<>();

				List<Object[]> record1 = exportInvRepo.getDataByGateInIdFOrHistory(cid, bid, gateInId, conNo);

				if (!record1.isEmpty()) {

					Object[] a = record1.get(0);

					Map<String, String> r1 = new HashMap<>();

					r1.put("containerNo",
							(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
					r1.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
					r1.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
					r1.put("fcl", "FCL");
					r1.put("deliveryMode","Loaded");
					r1.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
					r1.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
					r1.put("transId", (a[5] != null && !String.valueOf(a[5]).isEmpty()) ? String.valueOf(a[5]) : "");
					r1.put("desc", "Export Empty In Done");
					r1.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
					r1.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
					r1.put("processDate",
							(a[8] != null && !String.valueOf(a[8]).isEmpty()) ? String.valueOf(a[8]) : "");
					r1.put("processBy",
							(a[10] != null && !String.valueOf(a[10]).isEmpty()) ? String.valueOf(a[10]) : "");
					r1.put("currentLocation",
							(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
					r1.put("pod", "");
					r1.put("forceEntry","");
					r1.put("holdStatus","");

					result.add(r1);

					if (a[11] != null && !String.valueOf(a[11]).isEmpty()) {

						Map<String, String> r2 = new HashMap<>();

						r2.put("containerNo",
								(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
						r2.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
						r2.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
						r2.put("fcl", "FCL");
						r2.put("deliveryMode","Loaded");
						r2.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
						r2.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
						r2.put("transId", (a[11] != null && !String.valueOf(a[11]).isEmpty()) ? String.valueOf(a[11]) : "");
						r2.put("desc", "Export Stuffing Request Done");
						r2.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
						r2.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
						r2.put("processDate",
								(a[12] != null && !String.valueOf(a[12]).isEmpty()) ? String.valueOf(a[12]) : "");
						r2.put("processBy",
								(a[13] != null && !String.valueOf(a[13]).isEmpty()) ? String.valueOf(a[13]) : "");
						r2.put("currentLocation",
								(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
						r2.put("pod", (a[14] != null && !String.valueOf(a[14]).isEmpty()) ? String.valueOf(a[14]) : "");
						r2.put("forceEntry","");
						r2.put("holdStatus","");

						result.add(r2);

						if (a[15] != null && !String.valueOf(a[15]).isEmpty()) {

							Map<String, String> r3 = new HashMap<>();

							r3.put("containerNo",
									(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
							r3.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
							r3.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
							r3.put("fcl", "FCL");
							r3.put("deliveryMode","Loaded");
							r3.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
							r3.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
							r3.put("transId", (a[15] != null && !String.valueOf(a[15]).isEmpty()) ? String.valueOf(a[15]) : "");
							r3.put("desc", "Export Stuffing Tally Done");
							r3.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
							r3.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
							r3.put("processDate",
									(a[16] != null && !String.valueOf(a[16]).isEmpty()) ? String.valueOf(a[16]) : "");
							r3.put("processBy",
									(a[17] != null && !String.valueOf(a[17]).isEmpty()) ? String.valueOf(a[17]) : "");
							r3.put("currentLocation",
									(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
							r3.put("pod", (a[18] != null && !String.valueOf(a[18]).isEmpty()) ? String.valueOf(a[18]) : "");
							r3.put("forceEntry","");
							r3.put("holdStatus","");

							result.add(r3);

							if (a[19] != null && !String.valueOf(a[19]).isEmpty()) {

								Map<String, String> r4 = new HashMap<>();

								r4.put("containerNo",
										(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
								r4.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
								r4.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
								r4.put("fcl", "FCL");
								r4.put("deliveryMode","Loaded");
								r4.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
								r4.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
								r4.put("transId", (a[19] != null && !String.valueOf(a[19]).isEmpty()) ? String.valueOf(a[19]) : "");
								r4.put("desc", "Export Movement Request Done");
								r4.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
								r4.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
								r4.put("processDate",
										(a[20] != null && !String.valueOf(a[20]).isEmpty()) ? String.valueOf(a[20]) : "");
								r4.put("processBy",
										(a[21] != null && !String.valueOf(a[21]).isEmpty()) ? String.valueOf(a[21]) : "");
								r4.put("currentLocation",
										(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
								r4.put("pod", (a[22] != null && !String.valueOf(a[22]).isEmpty()) ? String.valueOf(a[22]) : "");
								r4.put("forceEntry",(a[23] != null && !String.valueOf(a[23]).isEmpty()) ? String.valueOf(a[23]) : "");
								r4.put("holdStatus","");

								result.add(r4);

								if (a[24] != null && !String.valueOf(a[24]).isEmpty()) {

									Map<String, String> r5 = new HashMap<>();

									r5.put("containerNo",
											(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
									r5.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
									r5.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
									r5.put("fcl", "FCL");
									r5.put("deliveryMode","Loaded");
									r5.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
									r5.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
									r5.put("transId", (a[24] != null && !String.valueOf(a[24]).isEmpty()) ? String.valueOf(a[24]) : "");
									r5.put("desc", "Invoice Completed");
									r5.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
									r5.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
									r5.put("processDate",
											(a[25] != null && !String.valueOf(a[25]).isEmpty()) ? String.valueOf(a[25]) : "");
									r5.put("processBy",
											(a[26] != null && !String.valueOf(a[26]).isEmpty()) ? String.valueOf(a[26]) : "");
									r5.put("currentLocation",
											(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
									r5.put("pod", (a[22] != null && !String.valueOf(a[22]).isEmpty()) ? String.valueOf(a[22]) : "");
									r5.put("forceEntry","");
									r5.put("holdStatus","");


									result.add(r5);

								}

								

								if (a[27] != null && !String.valueOf(a[27]).isEmpty()) {

									Map<String, String> r5 = new HashMap<>();

									r5.put("containerNo",
											(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
									r5.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
									r5.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
									r5.put("fcl", "FCL");
									r5.put("deliveryMode","Loaded");
									r5.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
									r5.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
									r5.put("transId", (a[27] != null && !String.valueOf(a[27]).isEmpty()) ? String.valueOf(a[27]) : "");
									r5.put("desc", "Export Gate Pass Done");
									r5.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
									r5.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
									r5.put("processDate",
											(a[28] != null && !String.valueOf(a[28]).isEmpty()) ? String.valueOf(a[28]) : "");
									r5.put("processBy",
											(a[29] != null && !String.valueOf(a[29]).isEmpty()) ? String.valueOf(a[29]) : "");
									r5.put("currentLocation",
											(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
									r5.put("pod", (a[22] != null && !String.valueOf(a[22]).isEmpty()) ? String.valueOf(a[22]) : "");
									r5.put("forceEntry","");
									r5.put("holdStatus","");

									result.add(r5);

									if (a[30] != null && !String.valueOf(a[30]).isEmpty()) {

										Map<String, String> r6 = new HashMap<>();

										r6.put("containerNo",
												(a[0] != null && !String.valueOf(a[0]).isEmpty()) ? String.valueOf(a[0]) : "");
										r6.put("size", (a[1] != null && !String.valueOf(a[1]).isEmpty()) ? String.valueOf(a[1]) : "");
										r6.put("category", (a[2] != null && !String.valueOf(a[2]).isEmpty()) ? String.valueOf(a[2]) : "");
										r6.put("fcl", "FCL");
										r6.put("deliveryMode","Loaded");
										r6.put("agent", (a[3] != null && !String.valueOf(a[3]).isEmpty()) ? String.valueOf(a[3]) : "");
										r6.put("nominator", (a[4] != null && !String.valueOf(a[4]).isEmpty()) ? String.valueOf(a[4]) : "");
										r6.put("transId", (a[30] != null && !String.valueOf(a[30]).isEmpty()) ? String.valueOf(a[30]) : "");
										r6.put("desc", "Export Gate Out Done");
										r6.put("vcnNo", (a[6] != null && !String.valueOf(a[6]).isEmpty()) ? String.valueOf(a[6]) : "");
										r6.put("vessel", (a[7] != null && !String.valueOf(a[7]).isEmpty()) ? String.valueOf(a[7]) : "");
										r6.put("processDate",
												(a[31] != null && !String.valueOf(a[31]).isEmpty()) ? String.valueOf(a[31]) : "");
										r6.put("processBy",
												(a[32] != null && !String.valueOf(a[32]).isEmpty()) ? String.valueOf(a[32]) : "");
										r6.put("currentLocation",
												(a[9] != null && !String.valueOf(a[9]).isEmpty()) ? String.valueOf(a[9]) : "");
										r6.put("pod", (a[22] != null && !String.valueOf(a[22]).isEmpty()) ? String.valueOf(a[22]) : "");
										r6.put("forceEntry","");
										r6.put("holdStatus","");
										result.add(r6);

										return new ResponseEntity<>(result, HttpStatus.OK);

									}

								} else {
									return new ResponseEntity<>(result, HttpStatus.OK);
								}

							} else {
								return new ResponseEntity<>(result, HttpStatus.OK);
							}

						} else {
							return new ResponseEntity<>(result, HttpStatus.OK);
						}

					} else {
						return new ResponseEntity<>(result, HttpStatus.OK);
					}

					return new ResponseEntity<>(result, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	
	@Transactional
	@PostMapping("/updateIgmHead")
	public ResponseEntity<?> updateIgmHead(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody CFIgm igm) {

		try {

			CFIgm existingData1 = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, igm.getIgmTransId(), igm.getIgmNo());

			if (existingData1 == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			if (!igm.getShippingLine().equals(existingData1.getShippingLine())) {
				checkAndUpdateIGMAudit(cid, bid, user, "Shipping Line", existingData1.getShippingLine(),
						existingData1.getShippingLine(), igm);
			}

			if (!igm.getShippingAgent().equals(existingData1.getShippingAgent())) {
				checkAndUpdateIGMAudit(cid, bid, user, "Shipping Agent", existingData1.getShippingAgent(),
						existingData1.getShippingAgent(), igm);
			}

			if (!igm.getVesselId().equals(existingData1.getVesselId())) {
				checkAndUpdateIGMAudit(cid, bid, user, "Vessel", existingData1.getVesselId(),
						existingData1.getVesselId(), igm);
			}

			int updateIGMHeaderData = importinventoryrepo.updateIGMHeaderData(cid, bid, igm.getIgmTransId(),
					igm.getShippingAgent(), igm.getShippingLine(), igm.getVesselId(), user);

			int updateIGMHeaderGateInData = importinventoryrepo.updateIGMHeaderGateInData(cid, bid, igm.getIgmTransId(),
					igm.getShippingAgent(), igm.getShippingLine(), igm.getVesselId());

			int updateIGMHeaderImportInventoryData = importinventoryrepo.updateIGMHeaderImportInventoryData(cid, bid,
					igm.getIgmTransId(), igm.getShippingAgent(), igm.getShippingLine(), igm.getVesselId());

			int updateIGMHeaderDestuffData = importinventoryrepo.updateIGMHeaderDestuffData(cid, bid,
					igm.getIgmTransId(), igm.getShippingAgent(), igm.getShippingLine());

			int updateIGMHeaderImportGatePassData = importinventoryrepo.updateIGMHeaderImportGatePassData(cid, bid,
					igm.getIgmTransId(), igm.getShippingLine());

			int updateIGMHeaderGateOutData = importinventoryrepo.updateIGMHeaderGateOutData(cid, bid,
					igm.getIgmTransId(), igm.getShippingAgent(), igm.getShippingLine(), igm.getVesselId());

			CFIgm existingData = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, igm.getIgmTransId(), igm.getIgmNo());

			if (existingData == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(existingData, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public void checkAndUpdateIGMAudit(String cid, String bid, String user, String field, String oldValue,
			String newValue, CFIgm existingData) {

		// Generate next ID for the audit record
		String holdId = processnextidrepo.findAuditTrail(cid, bid, "P05085", "2024");
		int lastNumericId = Integer.parseInt(holdId.substring(4));
		int nextNumericId = lastNumericId + 1;
		String nextAuditId = String.format("EXPA%06d", nextNumericId);

		// Create new audit record
		ExportAudit audit = new ExportAudit();
		audit.setCompanyId(cid);
		audit.setBranchId(bid);
		audit.setApprovedBy(user);
		audit.setApprovedDate(new Date());
		audit.setAuditDate(new Date());
		audit.setAuditId(nextAuditId);
		audit.setContainerNo("");
		audit.setCreatedBy(user);
		audit.setCreatedDate(new Date());
		audit.setField(field);
		audit.setProfitcentreId(existingData.getProfitcentreId());
		audit.setSbNo("");
		audit.setStatus("A");
		audit.setTableName("IGM Entry");
		audit.setOldValue(oldValue);
		audit.setNewValue(newValue);
		audit.setIgmLineNo("");
		audit.setIgmNo(existingData.getIgmNo());
		exportauditrepo.save(audit);

		// Update process ID
		processnextidrepo.updateAuditTrail(cid, bid, "P05085", nextAuditId, "2024");

	}

	public void checkAndUpdateIGMCRGAudit1(String cid, String bid, String user, String field, String oldValue,
			String newValue, Cfigmcrg existingData) {

		// Generate next ID for the audit record
		String holdId = processnextidrepo.findAuditTrail(cid, bid, "P05085", "2024");
		int lastNumericId = Integer.parseInt(holdId.substring(4));
		int nextNumericId = lastNumericId + 1;
		String nextAuditId = String.format("EXPA%06d", nextNumericId);

		// Create new audit record
		ExportAudit audit = new ExportAudit();
		audit.setCompanyId(cid);
		audit.setBranchId(bid);
		audit.setApprovedBy(user);
		audit.setApprovedDate(new Date());
		audit.setAuditDate(new Date());
		audit.setAuditId(nextAuditId);
		audit.setContainerNo("");
		audit.setCreatedBy(user);
		audit.setCreatedDate(new Date());
		audit.setField(field);
		audit.setProfitcentreId(existingData.getProfitcentreId());
		audit.setSbNo("");
		audit.setStatus("A");
		audit.setTableName("IGM CRG Entry");
		audit.setOldValue(oldValue);
		audit.setNewValue(newValue);
		audit.setIgmLineNo(existingData.getIgmLineNo());
		audit.setIgmNo(existingData.getIgmNo());
		exportauditrepo.save(audit);

		// Update process ID
		processnextidrepo.updateAuditTrail(cid, bid, "P05085", nextAuditId, "2024");

	}

	public void checkAndUpdateIGMCNAudit1(String cid, String bid, String user, String field, String oldValue,
			String newValue, Cfigmcn existingData) {

		// Generate next ID for the audit record
		String holdId = processnextidrepo.findAuditTrail(cid, bid, "P05085", "2024");
		int lastNumericId = Integer.parseInt(holdId.substring(4));
		int nextNumericId = lastNumericId + 1;
		String nextAuditId = String.format("EXPA%06d", nextNumericId);

		// Create new audit record
		ExportAudit audit = new ExportAudit();
		audit.setCompanyId(cid);
		audit.setBranchId(bid);
		audit.setApprovedBy(user);
		audit.setApprovedDate(new Date());
		audit.setAuditDate(new Date());
		audit.setAuditId(nextAuditId);
		audit.setContainerNo(existingData.getContainerNo());
		audit.setCreatedBy(user);
		audit.setCreatedDate(new Date());
		audit.setField(field);
		audit.setProfitcentreId(existingData.getProfitcentreId());
		audit.setSbNo("");
		audit.setStatus("A");
		audit.setTableName("IGM Container Entry");
		audit.setOldValue(oldValue);
		audit.setNewValue(newValue);
		audit.setIgmLineNo(existingData.getIgmLineNo());
		audit.setIgmNo(existingData.getIgmNo());
		exportauditrepo.save(audit);

		// Update process ID
		processnextidrepo.updateAuditTrail(cid, bid, "P05085", nextAuditId, "2024");

	}

	public void checkAndUpdateIGMCRGAudit(String cid, String bid, String user, Cfigmcrg existingData,
			Cfigmcrg newData) {

		if (!existingData.getCycle().equals(newData.getCycle())) {
			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Cycle", existingData.getCycle(), newData.getCycle(),
					existingData);
		}

		if (!existingData.getBlNo().equals(newData.getBlNo())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "BL NO", existingData.getBlNo(), newData.getBlNo(),
					existingData);
		}

		if (existingData.getBlDate().compareTo(newData.getBlDate()) != 0) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "BL Date", existingData.getBlDate().toString(),
					newData.getBlDate().toString(), existingData);
		}

		if (!existingData.getHsnCode().equals(newData.getHsnCode())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "HSN Code", existingData.getHsnCode(), newData.getHsnCode(),
					existingData);

		}

		if (!existingData.getCommodityDescription().equals(newData.getCommodityDescription())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Commodity Description", existingData.getCommodityDescription(),
					newData.getCommodityDescription(), existingData);
		}

		if (!existingData.getMarksOfNumbers().equals(newData.getMarksOfNumbers())) {
			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Marks & Nos", existingData.getMarksOfNumbers(),
					newData.getMarksOfNumbers(), existingData);

		}
		if (!existingData.getCargoMovement().equals(newData.getCargoMovement())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Cargo Movement", existingData.getCargoMovement(),
					newData.getCargoMovement(), existingData);

		}
		if (existingData.getGrossWeight().compareTo(newData.getGrossWeight()) != 0) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Cargo Weight", String.valueOf(existingData.getGrossWeight()),
					String.valueOf(newData.getGrossWeight()), existingData);

		}

		if (!existingData.getUnitOfWeight().equals(newData.getUnitOfWeight())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Unit", existingData.getUnitOfWeight(),
					newData.getUnitOfWeight(), existingData);

		}

		if (existingData.getNoOfPackages().compareTo(newData.getNoOfPackages()) != 0) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "No Of Packages", String.valueOf(existingData.getNoOfPackages()),
					String.valueOf(newData.getNoOfPackages()), existingData);

		}

		if (!existingData.getTypeOfPackage().equals(newData.getTypeOfPackage())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Type Of Package", existingData.getTypeOfPackage(),
					newData.getTypeOfPackage(), existingData);

		}

		if (!existingData.getAccountHolderName().equals(newData.getAccountHolderName())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Account Holder", existingData.getAccountHolderName(),
					newData.getAccountHolderName(), existingData);

		}

		if (!existingData.getImporterName().equals(newData.getImporterName())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Importer Name", existingData.getImporterName(),
					newData.getImporterName(), existingData);

		}
		if (!existingData.getImporterAddress1().equals(newData.getImporterAddress1())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Importer Address1", existingData.getImporterAddress1(),
					newData.getImporterAddress1(), existingData);

		}
		if (!existingData.getImporterAddress2().equals(newData.getImporterAddress2())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Importer Address2", existingData.getImporterAddress2(),
					newData.getImporterAddress2(), existingData);

		}
		if (!existingData.getImporterAddress3().equals(newData.getImporterAddress3())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Importer Address3", existingData.getImporterAddress3(),
					newData.getImporterAddress3(), existingData);

		}
		if (!existingData.getNotifyPartyName().equals(newData.getNotifyPartyName())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Notified Party", existingData.getNotifyPartyName(),
					newData.getNotifyPartyName(), existingData);

		}
		if (!existingData.getNotifiedAddress1().equals(newData.getNotifiedAddress1())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Notified Party Address1", existingData.getNotifiedAddress1(),
					newData.getNotifiedAddress1(), existingData);

		}
		if (!existingData.getNotifiedAddress2().equals(newData.getNotifiedAddress2())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Notified Party Address2", existingData.getNotifiedAddress2(),
					newData.getNotifiedAddress2(), existingData);

		}
		if (!existingData.getNotifiedAddress3().equals(newData.getNotifiedAddress3())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Notified Party Address3", existingData.getNotifiedAddress3(),
					newData.getNotifiedAddress3(), existingData);

		}
		if (!existingData.getOrigin().equals(newData.getOrigin())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Origin", existingData.getOrigin(), newData.getOrigin(),
					existingData);

		}
		if (!existingData.getDestination().equals(newData.getDestination())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Destination", existingData.getDestination(),
					newData.getDestination(), existingData);

		}
		if (!existingData.getCargoType().equals(newData.getCargoType())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Cargo Type", existingData.getCargoType(),
					newData.getCargoType(), existingData);

		}
		if (!existingData.getImoCode().equals(newData.getImoCode())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Imo Code", existingData.getImoCode(), newData.getImoCode(),
					existingData);

		}
		if (!existingData.getUnNo().equals(newData.getUnNo())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Un No", existingData.getUnNo(), newData.getUnNo(),
					existingData);

		}
		if (!existingData.getHazReeferRemarks().equals(newData.getHazReeferRemarks())) {

			checkAndUpdateIGMCRGAudit1(cid, bid, user, "Haz/Reefer Remarks", existingData.getHazReeferRemarks(),
					newData.getHazReeferRemarks(), existingData);

		}

	}

	public void checkAndUpdateIGMCNAudit(String cid, String bid, String user, Cfigmcn existingData, Cfigmcn newData) {

		if(!existingData.getIso().equals(newData.getIso())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Iso", existingData.getIso(), newData.getIso(), existingData);
		}
		
		if(!existingData.getContainerStatus().equals(newData.getContainerStatus())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Container Status", existingData.getContainerStatus(), newData.getContainerStatus(), existingData);
		}
		
		if(existingData.getNoOfPackages() != newData.getNoOfPackages()) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "No Of Packages", String.valueOf(existingData.getNoOfPackages()), String.valueOf(newData.getNoOfPackages()), existingData);
		}
		if(!String.valueOf(existingData.getOdcStatus()).equals(String.valueOf(newData.getOdcStatus()))) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Odc Status", String.valueOf(existingData.getOdcStatus()), String.valueOf(newData.getOdcStatus()), existingData);
		}
		if(!String.valueOf(existingData.getLowBed()).equals(String.valueOf(newData.getLowBed()))) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Low Bed", String.valueOf(existingData.getLowBed()), String.valueOf(newData.getLowBed()), existingData);
		}
		if(!existingData.getGateOutType().equals(newData.getUpTariffDelMode())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Delivery Mode", existingData.getGateOutType(), newData.getUpTariffDelMode(), existingData);
		}
		if(!existingData.getTypeOfContainer().equals(newData.getTypeOfContainer())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Type Of Container", existingData.getTypeOfContainer(), newData.getTypeOfContainer(), existingData);
		}
		if(!existingData.getTemperature().equals(newData.getTemperature())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Temperature", existingData.getTemperature(), newData.getTemperature(), existingData);
		}
		if(!existingData.getContainerSize().equals(newData.getContainerSize())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Container Size", existingData.getContainerSize(), newData.getContainerSize(), existingData);
		}
		if(!existingData.getContainerType().equals(newData.getContainerType())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Container Type", existingData.getContainerType(), newData.getContainerType(), existingData);
		}
		if(existingData.getContainerWeight().compareTo(newData.getContainerWeight()) != 0) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Tare Wt", String.valueOf(existingData.getContainerWeight()), String.valueOf(newData.getContainerWeight()), existingData);
		}
		if(existingData.getGrossWt().compareTo(newData.getGrossWt()) != 0) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Gross Wt", String.valueOf(existingData.getGrossWt()), String.valueOf(newData.getGrossWt()), existingData);
		}
		if(!existingData.getScannerType().equals(newData.getScannerType())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Scan Type", existingData.getScannerType(), newData.getScannerType(), existingData);
		}
		if(!existingData.getContainerSealNo().equals(newData.getContainerSealNo())) {
			checkAndUpdateIGMCNAudit1(cid, bid, user, "Container Seal No", existingData.getContainerSealNo(), newData.getContainerSealNo(), existingData);
		}
	}

}
