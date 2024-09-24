package com.cwms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.GateIn;
import com.cwms.entities.GateOut;
import com.cwms.entities.ImportInventory;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.GateOutRepository;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.cwms.service.GateInService;

@RestController
@RequestMapping("/gateIn")
@CrossOrigin("*")
public class GateInController {

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private GateInRepository gateinrepo;

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;
	
	@Autowired
	private GateOutRepository gateoutrepository;
	
	@Autowired
	private ImportInventoryRepository importinventoryrepo;

	@PostMapping("/saveGateIn")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("flag") String flag, @RequestBody GateIn gatein) {

		if (gatein == null) {
			return new ResponseEntity<>("Gate in data not found.", HttpStatus.BAD_REQUEST);
		} else {

			if ("add".equals(flag)) {

//				List<Cfigmcn >cn = cfigmcnrepo.getSingleData5(cid, bid, gatein.getErpDocRefNo(), gatein.getProfitcentreId(),
//						gatein.getDocRefNo(), gatein.getContainerNo());

				List<Cfigmcn> cn = cfigmcnrepo.getSingleData5(cid, bid, gatein.getProfitcentreId(),
						gatein.getDocRefNo(), gatein.getContainerNo());

				if (cn.isEmpty()) {
					return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
				}
				
				Boolean check = vehicleTrackRepo.checkVehicleNo(cid, bid, gatein.getVehicleNo());

				if (check) {
					return new ResponseEntity<>("The vehicle is already inside.", HttpStatus.CONFLICT);
				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05063", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("GIGG%06d", nextNumericNextID1);

				gatein.setGateInId(HoldNextIdD1);
				gatein.setCompanyId(cid);
				gatein.setLineNo("");
				gatein.setSrNo(1);
				gatein.setInGateInDate(new Date());
				gatein.setBranchId(bid);
				gatein.setRefer("RF".equals(gatein.getContainerType()) ? "Y" : "N");
				gatein.setCreatedBy(user);
				gatein.setCreatedDate(new Date());
				gatein.setApprovedBy(user);
				gatein.setApprovedDate(new Date());
				gatein.setStatus("A");
				gatein.setGateInType("IMP");

				cn.stream().forEach(c -> {
					c.setGateInDate(new Date());
					c.setGateInId(HoldNextIdD1);
					c.setHaz(gatein.getHazardous());
					c.setHazClass(gatein.getHazClass());
					c.setEirGrossWeight(gatein.getEirGrossWeight());
					c.setVehicleType(gatein.getVehicleType());
					c.setContainerWeight(gatein.getTareWeight());
					c.setContainerSealNo(gatein.getContainerSealNo());
					c.setYardLocation(gatein.getYardLocation());
					c.setYardBlock(gatein.getYardBlock());
					c.setBlockCellNo(gatein.getYardCell());
					c.setYardLocation1(gatein.getYardLocation1());
					c.setYardBlock1(gatein.getYardBlock1());
					c.setBlockCellNo1(gatein.getYardCell1());
					c.setMovementType(gatein.getDrt());
					System.out.println("gatein.getTareWeight() " + gatein.getTareWeight());
					c.setCargoWt(c.getGrossWt()
							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));
					gatein.setCargoWeight(c.getGrossWt()
							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));

					if ("Y".equals(gatein.getRefer())) {
						c.setRefer('Y');
					} else {
						c.setRefer('N');
					}

					c.setLowBed(c.getLowBed());
					c.setMovementType("Y".equals(gatein.getDrt()) ? "DRT" : "CFS");
					c.setCustomsSealNo(gatein.getCustomsSealNo());

					if ("Y".equals(gatein.getPnStatus())) {
						c.setPnStatus('Y');
					} else {
						c.setPnStatus('N');
					}

					if ("Y".equals(gatein.getOdcStatus())) {
						c.setOdcStatus('Y');
					} else {
						c.setOdcStatus('N');
					}

					cfigmcnrepo.save(c);

					Cfigmcrg cr = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());

					if (cr != null) {
						cr.setActualCargoWeight(cr.getActualCargoWeight() == null ? BigDecimal.ZERO
								: cr.getActualCargoWeight().add(c.getGrossWt().subtract(
										gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO)));
						cfigmcrgrepo.save(cr);
					}
				});

				gateinrepo.save(gatein);
				
				VehicleTrack v = new VehicleTrack();
				v.setCompanyId(cid);
				v.setBranchId(bid);
				v.setFinYear(gatein.getFinYear());
				v.setVehicleNo(gatein.getVehicleNo());
				v.setProfitcentreId(gatein.getProfitcentreId());
				v.setSrNo(1);
				v.setTransporterStatus(gatein.getTransporterStatus().charAt(0));
				v.setTransporterName(gatein.getTransporterName());
				v.setTransporter(gatein.getTransporter());
				v.setDriverName(gatein.getDriverName());
				v.setVehicleStatus('E');
				v.setGateInId(HoldNextIdD1);
				v.setGateInDate(new Date());
				v.setGateNoIn("Gate01");
				v.setShiftIn(gatein.getShift());
				v.setStatus('A');
				v.setCreatedBy(user);
				v.setCreatedDate(new Date());
				v.setApprovedBy(user);
				v.setApprovedDate(new Date());

				vehicleTrackRepo.save(v);
				
				ImportInventory inventory = new ImportInventory();
				inventory.setCompanyId(cid);
				inventory.setBranchId(bid);
				inventory.setFinYear(gatein.getFinYear());
				inventory.setIgmTransId(gatein.getErpDocRefNo());
				inventory.setProfitcentreId(gatein.getProfitcentreId());
				inventory.setIgmNo(gatein.getDocRefNo());
				inventory.setVesselId(gatein.getVessel());
				inventory.setViaNo(gatein.getViaNo());
				inventory.setContainerNo(gatein.getContainerNo());
				inventory.setContainerSize(gatein.getContainerSize());
				inventory.setContainerType(gatein.getContainerType());
				inventory.setIso(gatein.getIsoCode());
				inventory.setSa(gatein.getSa());
				inventory.setSl(gatein.getSl());
				inventory.setImporterName(gatein.getImporterName());
				inventory.setContainerStatus(gatein.getContainerStatus());
				inventory.setContainerSealNo(gatein.getContainerSealNo());
				inventory.setScannerType(gatein.getScannerType());
				inventory.setYardLocation(gatein.getYardLocation());
				inventory.setYardLocation1(gatein.getYardLocation1());
				inventory.setYardBlock(gatein.getYardBlock());
				inventory.setBlockCellNo(gatein.getYardCell());
				inventory.setGateInId(HoldNextIdD1);
				inventory.setGateInDate(new Date());
				inventory.setStatus("A");
				inventory.setCreatedBy(user);
				inventory.setCreatedDate(new Date());
				inventory.setApprovedBy(user);
				inventory.setApprovedDate(new Date());
				inventory.setNoOfItem(cn.get(0).getNoOfItem());
				inventory.setContainerWeight(cn.get(0).getGrossWt()
						.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));
				inventory.setCycle(cn.get(0).getCycle());
				
				
				importinventoryrepo.save(inventory);

				processnextidrepo.updateAuditTrail(cid, bid, "P05063", HoldNextIdD1, "2024");

				return new ResponseEntity<>(gatein, HttpStatus.OK);
			} else {
				GateIn existingData = gateinrepo.getData(cid, bid, gatein.getGateInId(), gatein.getErpDocRefNo(),
						gatein.getDocRefNo());

				if (existingData == null) {
					return new ResponseEntity<>("Gate in data not found", HttpStatus.BAD_REQUEST);
				}

//				List<Cfigmcn> cn = cfigmcnrepo.getSingleData5(cid, bid, gatein.getErpDocRefNo(), gatein.getProfitcentreId(),
//						gatein.getDocRefNo(), gatein.getContainerNo());

				List<Cfigmcn> cn = cfigmcnrepo.getSingleData5(cid, bid, gatein.getProfitcentreId(),
						gatein.getDocRefNo(), gatein.getContainerNo());

				if (cn.isEmpty()) {
					return new ResponseEntity<>("Container data not found", HttpStatus.BAD_REQUEST);
				}
				existingData.setTransporterName(gatein.getTransporterName());
				existingData.setVehicleNo(gatein.getVehicleNo());
				existingData.setTransporter(gatein.getTransporter());
				existingData.setDriverName(gatein.getDriverName());
				existingData.setContainerSealNo(gatein.getContainerSealNo());
				existingData.setVehicleType(gatein.getVehicleType());
				existingData.setActualSealNo(gatein.getActualSealNo());
				existingData.setTareWeight(gatein.getTareWeight());
				existingData.setHazardous(gatein.getHazardous());
				existingData.setEirGrossWeight(gatein.getEirGrossWeight());
				existingData.setRefer(gatein.getRefer());
				existingData.setHazClass(gatein.getHazClass());
				existingData.setLowBed(gatein.getLowBed());
				existingData.setYardBlock(gatein.getYardBlock());
				existingData.setYardBlock1(gatein.getYardBlock1());
				existingData.setYardCell(gatein.getYardCell());
				existingData.setYardCell1(gatein.getYardCell1());
				existingData.setYardLocation(gatein.getYardLocation());
				existingData.setYardLocation1(gatein.getYardLocation1());
				existingData.setPortExitNo(gatein.getPortExitNo());
				existingData.setPortExitDate(gatein.getPortExitDate());
				existingData.setScanningStatus(gatein.getScanningStatus());
				existingData.setComments(gatein.getComments());
				existingData.setDrt(gatein.getDrt());
				existingData.setContainerHealth(gatein.getContainerHealth());
				existingData.setTransporterStatus(gatein.getTransporterStatus());
				existingData.setCustomsSealNo(gatein.getCustomsSealNo());
				existingData.setPnStatus(gatein.getPnStatus());
				existingData.setOdcStatus(gatein.getOdcStatus());
				existingData.setEditedBy(user);
				existingData.setEditedDate(new Date());

				cn.stream().forEach(c -> {
					BigDecimal cal = BigDecimal.ZERO;
					cal = c.getCargoWt();
					c.setHaz(gatein.getHazardous());
					c.setHazClass(gatein.getHazClass());
					c.setEirGrossWeight(gatein.getEirGrossWeight());
					c.setVehicleType(gatein.getVehicleType());
					c.setContainerWeight(c.getGrossWt()
							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));
					c.setContainerSealNo(gatein.getContainerSealNo());
					c.setYardLocation(gatein.getYardLocation());
					c.setYardBlock(gatein.getYardBlock());
					c.setBlockCellNo(gatein.getYardCell());
					c.setYardLocation1(gatein.getYardLocation1());
					c.setYardBlock1(gatein.getYardBlock1());
					c.setBlockCellNo1(gatein.getYardCell1());
					c.setCargoWt(c.getGrossWt().subtract(gatein.getTareWeight()));
					c.setMovementType(gatein.getDrt());
					existingData.setCargoWeight(c.getGrossWt()
							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));

					if ("Y".equals(gatein.getRefer())) {
						c.setRefer('Y');
					} else {
						c.setRefer('N');
					}

					c.setLowBed(c.getLowBed());
					c.setMovementType("Y".equals(gatein.getDrt()) ? "DRT" : "CFS");
					c.setCustomsSealNo(gatein.getCustomsSealNo());

					if ("Y".equals(gatein.getPnStatus())) {
						c.setPnStatus('Y');
					} else {
						c.setPnStatus('N');
					}

					if ("Y".equals(gatein.getOdcStatus())) {
						c.setOdcStatus('Y');
					} else {
						c.setOdcStatus('N');
					}

					cfigmcnrepo.save(c);

					Cfigmcrg cr = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());

					if (cr != null) {
						cr.setActualCargoWeight((cr.getActualCargoWeight()
								.add(c.getGrossWt().subtract(
										gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO)))
								.subtract(cal));
						cfigmcrgrepo.save(cr);
					}

				});
				
				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, gatein.getErpDocRefNo(), gatein.getDocRefNo(), 
						gatein.getContainerNo(), gatein.getGateInId());
				
				if(existingInv != null) {
					existingInv.setContainerWeight(cn.get(0).getGrossWt()
							.subtract(gatein.getTareWeight() != null ? gatein.getTareWeight() : BigDecimal.ZERO));
					existingInv.setContainerSealNo(gatein.getContainerSealNo());
	
					existingInv.setYardLocation(gatein.getYardLocation());
					existingInv.setYardLocation1(gatein.getYardLocation1());
					existingInv.setYardBlock(gatein.getYardBlock());
					existingInv.setBlockCellNo(gatein.getYardCell());
					
					importinventoryrepo.save(existingInv);
				}

				gateinrepo.save(existingData);
				int update = vehicleTrackRepo.updateData(cid, bid, gatein.getGateInId(), gatein.getVehicleNo(),
						gatein.getProfitcentreId(), gatein.getTransporterStatus().charAt(0), gatein.getTransporterName(),
						gatein.getTransporter(), gatein.getDriverName(), 'E', "Gate01", gatein.getShift(),
						user);
				return new ResponseEntity<>(existingData, HttpStatus.OK);

			}
		}
	}

	@GetMapping("/getImpData")
	public List<Object[]> getImpData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String search) {

		return gateinrepo.getAll(cid, bid, search);
	}

	@GetMapping("/getSingleData")
	public GateIn getSingleData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("gateinid") String gateinid, @RequestParam("igmtrans") String igmtrans,
			@RequestParam("igm") String igm) {
		return gateinrepo.getData(cid, bid, gateinid, igmtrans, igm);
	}

	@GetMapping("/getSingleData2")
	public GateIn getSingleData2(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("gateinid") String gateinid) {
		return gateinrepo.getData2(cid, bid, gateinid);
	}

	@GetMapping("/getSingleData1")
	public GateIn getSingleData1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("con") String con) {
		return gateinrepo.getData1(cid, bid, igm, con);
	}

	@GetMapping("/getContainerData")
	public ResponseEntity<?> getContainerData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("container") String container, @RequestParam("igmtrans") String igmtrans,
			@RequestParam("igm") String igm) {
		List<Cfigmcn> con = cfigmcnrepo.getContainers(cid, bid, igm, igmtrans, container);

		if (con.isEmpty()) {
			return new ResponseEntity<>("Container data not found.", HttpStatus.BAD_REQUEST);
		}

		Cfigmcn containerData = con.get(0);

		return new ResponseEntity<>(containerData, HttpStatus.OK);
	}

	@Autowired
	private GateInService gateInService;

	@GetMapping("/getSelectedGateInEntry")
	public ResponseEntity<?> getSelectedGateInEntry(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("sbTransId") String sbTransId,
			@RequestParam("gateInId") String gateInId, @RequestParam("sbNo") String sbNo,
			@RequestParam("profitCenterId") String profitCenterId) {
		try {
			ResponseEntity<?> sbEntries = gateInService.getSelectedGateInEntry(companyId, branchId, sbTransId, gateInId,
					sbNo, profitCenterId);
			return sbEntries;
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@GetMapping("/getGateInEntriesToSelect")
	public ResponseEntity<?> getGateInEntriesToSelect(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		try {
			List<Object[]> gateInEntries = gateInService.getGateInEntriesToSelect(companyId, branchId, searchValue);
			return ResponseEntity.ok(gateInEntries);
		} catch (Exception e) {
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	@PostMapping("/addExportGateIn")
	public ResponseEntity<?> addExportGateIn(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestBody List<GateIn> gateIn,
			@RequestParam("userId") String User) {

		ResponseEntity<?> addExportSbEntry = gateInService.addExportGateIn(companyId, branchId, gateIn, User);

		return addExportSbEntry;
	}

	@PostMapping("/saveEmptyVehiclegateIn")
	public ResponseEntity<?> saveEmptyVehiclegateIn(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("userId") String userId, @RequestBody GateIn gatein) {

		if (gatein == null) {
			return new ResponseEntity<>("Invalid gate in data", HttpStatus.CONFLICT);
		}

		if (gatein.getGateInId().isEmpty()) {

			Boolean check = vehicleTrackRepo.checkVehicleNo(cid, bid, gatein.getVehicleNo());

			if (check) {
				return new ResponseEntity<>("The vehicle is already inside.", HttpStatus.CONFLICT);
			}

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05071", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("CFAM%06d", nextNumericNextID1);

			gatein.setErpDocRefNo("1");
			gatein.setDocRefNo("1");
			gatein.setLineNo("1");
			gatein.setSrNo(1);
			gatein.setCompanyId(cid);
			gatein.setBranchId(bid);
			gatein.setStatus("A");
			gatein.setCreatedBy(userId);
			gatein.setCreatedDate(new Date());
			gatein.setApprovedBy(userId);
			gatein.setApprovedDate(new Date());
			gatein.setGateInId(HoldNextIdD1);
			gatein.setInGateInDate(new Date());

			gateinrepo.save(gatein);

			VehicleTrack v = new VehicleTrack();
			v.setCompanyId(cid);
			v.setBranchId(bid);
			v.setFinYear(gatein.getFinYear());
			v.setVehicleNo(gatein.getVehicleNo());
			v.setProfitcentreId(gatein.getProfitcentreId());
			v.setSrNo(1);
			v.setTransporterStatus(gatein.getTransporterStatus().charAt(0));
			v.setTransporterName(gatein.getTransporterName());
			v.setTransporter(gatein.getTransporter());
			v.setDriverName(gatein.getDriverName());
			v.setVehicleStatus('E');
			v.setGateInId(HoldNextIdD1);
			v.setGateInDate(new Date());
			v.setGateNoIn(gatein.getGateNo());
			v.setShiftIn(gatein.getShift());
			v.setStatus('A');
			v.setCreatedBy(userId);
			v.setCreatedDate(new Date());
			v.setApprovedBy(userId);
			v.setApprovedDate(new Date());

			vehicleTrackRepo.save(v);

			processnextidrepo.updateAuditTrail(cid, bid, "P05071", HoldNextIdD1, "2024");

			// GateIn data = gateinrepo.getData2(cid, bid, HoldNextIdD1);

			return new ResponseEntity<>(gatein, HttpStatus.OK);
		} else {
			GateIn data = gateinrepo.getData2(cid, bid, gatein.getGateInId());

			if (data == null) {
				return new ResponseEntity<>("Empty Vehicle data not found.", HttpStatus.CONFLICT);
			}

			Boolean check = vehicleTrackRepo.checkVehicleNo1(cid, bid, gatein.getVehicleNo(),gatein.getGateInId());

			if (check) {
				return new ResponseEntity<>("The vehicle is already inside.", HttpStatus.CONFLICT);
			}

			data.setShift(gatein.getShift());
			data.setGateNo(gatein.getGateNo());
			data.setTripType(gatein.getTripType());
			data.setVehicleNo(gatein.getVehicleNo());
			data.setDriverName(gatein.getDriverName());
			data.setProfitcentreId(gatein.getProfitcentreId());
			data.setTransporterStatus(gatein.getTransporterStatus());
			data.setTransporter(gatein.getTransporter());
			data.setTransporterName(gatein.getTransporterName());
			data.setEditedBy(userId);
			data.setEditedDate(new Date());

			gateinrepo.save(data);

			int update = vehicleTrackRepo.updateData(cid, bid, gatein.getGateInId(), gatein.getVehicleNo(),
					gatein.getProfitcentreId(), gatein.getTransporterStatus().charAt(0), gatein.getTransporterName(),
					gatein.getTransporter(), gatein.getDriverName(), 'E', gatein.getGateNo(), gatein.getShift(),
					userId);

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}

	@GetMapping("/searchEmptyVehicleGateInRecords")
	public ResponseEntity<?> searchEmptyVehicleGateInRecords(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = gateinrepo.searchEmptyVehicleGateInRecords(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}
	
	@GetMapping("/getVehTrkData")
	public ResponseEntity<?> getEmptyGateOutIdDataFromVehTrk(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid,@RequestParam(name="val",required = false) String val){
		
		List<Object[]> data = vehicleTrackRepo.getEmptyGateOutIdDataFromVehTrk(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}

		
	}
	
	@GetMapping("/getVehTrkSelectedData")
	public ResponseEntity<?> getSelectedData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid,@RequestParam(name="val",required = false) String val){
		
		VehicleTrack data = vehicleTrackRepo.getByGateInId1(cid, bid, val);
		
		if (data == null) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
		
	}
	
	
	@PostMapping("/saveEmptyGateOut")
	public ResponseEntity<?> saveEmptyGateOut(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("userId") String userId,@RequestBody GateOut gateout){
		
		
		if(gateout == null) {
			return new ResponseEntity<>("GateOut data not found!!", HttpStatus.CONFLICT);
		}
		
		if(gateout.getGateOutId().isEmpty()) {
			
			VehicleTrack veh = vehicleTrackRepo.getByGateInId2(cid, bid, gateout.getErpDocRefNo(), gateout.getVehicleNo());
			
			if(veh == null) {
				return new ResponseEntity<>("Vehicle track data not found!!", HttpStatus.CONFLICT);
			}
			
			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05072", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("EVGY%06d", nextNumericNextID1);
			
			gateout.setCompanyId(cid);
			gateout.setBranchId(bid);
			gateout.setGateOutId(HoldNextIdD1);
			gateout.setDocRefNo("1");
			gateout.setProfitcentreId(veh.getProfitcentreId());
			gateout.setSrNo("1");
			gateout.setGateOutDate(new Date());
			gateout.setProcessId("P00602");
			gateout.setStatus('A');
			gateout.setErpDocRefNo(veh.getGateInId());
			gateout.setCreatedBy(userId);
			gateout.setCreatedDate(new Date());
			gateout.setApprovedBy(userId);
			gateout.setApprovedDate(new Date());
			gateoutrepository.save(gateout);
			
			veh.setGateOutDate(new Date());
			veh.setGateOutId(HoldNextIdD1);
			veh.setShiftOut(gateout.getShift());
			veh.setGateNoOut(gateout.getGateNoOut());
			
			vehicleTrackRepo.save(veh);
			
			processnextidrepo.updateAuditTrail(cid, bid, "P05072", HoldNextIdD1, "2024");
			
			GateOut data = gateoutrepository.getDataByGateOutIdAndGateInId1(cid, bid, gateout.getErpDocRefNo(), HoldNextIdD1);
			
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
		else {
			GateOut out = gateoutrepository.getDataByGateOutIdAndGateInId(cid, bid, gateout.getErpDocRefNo(), gateout.getGateOutId());
			
			if(out == null) {
				return new ResponseEntity<>("Existing data not found!!", HttpStatus.CONFLICT);
			}
			

			VehicleTrack veh = vehicleTrackRepo.getByGateInId2(cid, bid, gateout.getErpDocRefNo(), gateout.getVehicleNo());
			
			if(veh == null) {
				return new ResponseEntity<>("Vehicle track data not found!!", HttpStatus.CONFLICT);
			}
			
			out.setEditedBy(userId);
			out.setEditedDate(new Date());
			out.setDriverName(gateout.getDriverName());
			out.setShift(gateout.getShift());
			out.setGateNoOut(gateout.getGateNoOut());
			out.setComments(gateout.getComments());
			
			gateoutrepository.save(out);
			
			veh.setShiftOut(gateout.getShift());
			veh.setGateNoOut(gateout.getGateNoOut());
			
			vehicleTrackRepo.save(veh);
			
			GateOut data = gateoutrepository.getDataByGateOutIdAndGateInId1(cid, bid, gateout.getErpDocRefNo(), gateout.getGateOutId());
			
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	
	@GetMapping("/searchgateOutData")
	public ResponseEntity<?> searchData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam(name="val",required = false) String val){
		
		List<Object[]> data = gateoutrepository.searchGateOutData(cid, bid, val);
		
		if(data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!",HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
	
	@GetMapping("/getSelectedGateOutData")
	public ResponseEntity<?> getSelectedGateOutData(@RequestParam("cid") String cid,@RequestParam("bid") String bid,
			@RequestParam("gateInId") String gateInId,@RequestParam("gateOutId") String gateOutId){
		
		GateOut data = gateoutrepository.getDataByGateOutIdAndGateInId1(cid, bid, gateInId,gateOutId);
		
		if(data == null) {
			return new ResponseEntity<>("Data not found!!",HttpStatus.CONFLICT);
		}
		else {
			return new ResponseEntity<>(data,HttpStatus.OK);
		}
	}
 
	
	@GetMapping("/searchByVehicleNo")
	public ResponseEntity<?> searchByVehicleNo(@RequestParam("cid") String cid,@RequestParam("bid") String bid,@RequestParam("veh") String veh){
		
		Boolean  v = vehicleTrackRepo.checkVehicleNoSearch(cid, bid, veh);
		List<String> pageList = new ArrayList<>();
		
		if(!v) {
			return new ResponseEntity<>("Vehicle no data not found!!",HttpStatus.CONFLICT);
		}
		
		VehicleTrack data = vehicleTrackRepo.vehNoSearch(cid, bid, veh);
		
		if(data == null) {
			return new ResponseEntity<>("Vehicle no data not found!!",HttpStatus.CONFLICT);
		}
		
		if (data.getGateOutId() == null || data.getGateOutId().isEmpty()) {
			pageList.add("P00601");
			pageList.add("P00602");
			
			Map<String,Object> allData = new HashMap<>();
			allData.put("pageList",pageList);
			allData.put("vehicleNo",data.getVehicleNo());
			allData.put("gateInId",data.getGateInId());
			allData.put("gateOutId","");
			
			return new ResponseEntity<>(allData,HttpStatus.OK);
		}
		else {
			pageList.add("P00601");
			pageList.add("P00602");
			
			Map<String,Object> allData = new HashMap<>();
			allData.put("pageList",pageList);
			allData.put("vehicleNo",data.getVehicleNo());
			allData.put("gateInId",data.getGateInId());
			allData.put("gateOutId",data.getGateOutId());
			return new ResponseEntity<>(allData,HttpStatus.OK);
		}
		
		
	}
}
