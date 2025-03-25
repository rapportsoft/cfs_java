package com.cwms.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
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
import com.cwms.entities.ImportInventory;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/rescangateout")
public class ReScanGateOutController {

	@Autowired
	private GateInRepository gateinrepo;
	
	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;


	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;
	
	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@GetMapping("/searchBeforeSaveDataForRescangateout")
	public ResponseEntity<?> searchBeforeSaveDataForRescangateout(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("val") String val) {
		try {

			List<Object[]> data = gateinrepo.searchBeforeSaveDataForRescangateout(cid, bid, val);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);

			}

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getSelectSearchBeforeSaveDataForRescangateout")
	public ResponseEntity<?> getSelectSearchBeforeSaveDataForRescangateout(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("val") String val) {
		try {

			GateIn data = gateinrepo.getData2(cid, bid, val);

			if (data == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);

			}

			Object data1 = gateinrepo.getSearchBeforeSaveDataForRescangateout(cid, bid, val);

			if (data1 == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);

			}
			
			Map<String, Object> result = new HashedMap<>();
			result.put("gateIn", data);
			result.put("gateIn1", data1);
			
			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveGateIn")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("flag") String flag, @RequestBody GateIn gatein) {

		System.out.println("gatein "+gatein);
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
				
//				Boolean check = vehicleTrackRepo.checkVehicleNo(cid, bid, gatein.getVehicleNo());
//
//				if (check) {
//					return new ResponseEntity<>("The vehicle is already inside.", HttpStatus.CONFLICT);
//				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05063", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("GIGG%06d", nextNumericNextID1);

				gatein.setGateInId(HoldNextIdD1);
				gatein.setCompanyId(cid);
				gatein.setLineNo(cn.get(0).getIgmLineNo());
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
				gatein.setProcessId("P00217");
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
					c.setRscanOut('Y');
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
				
//				VehicleTrack v = new VehicleTrack();
//				v.setCompanyId(cid);
//				v.setBranchId(bid);
//				v.setFinYear(gatein.getFinYear());
//				v.setVehicleNo(gatein.getVehicleNo());
//				v.setProfitcentreId(gatein.getProfitcentreId());
//				v.setSrNo(1);
//				v.setTransporterStatus(gatein.getTransporterStatus().charAt(0));
//				v.setTransporterName(gatein.getTransporterName());
//				v.setTransporter(gatein.getTransporter());
//				v.setDriverName(gatein.getDriverName());
//				v.setVehicleStatus('E');
//				v.setGateInId(HoldNextIdD1);
//				v.setGateInDate(new Date());
//				v.setGateNoIn("Gate01");
//				v.setShiftIn(gatein.getShift());
//				v.setStatus('A');
//				v.setCreatedBy(user);
//				v.setCreatedDate(new Date());
//				v.setApprovedBy(user);
//				v.setApprovedDate(new Date());
//
//				vehicleTrackRepo.save(v);
				
				

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
				existingData.setOutTransporterName(gatein.getOutTransporterName());
				existingData.setOutVehicleNo(gatein.getOutVehicleNo());
			//	existingData.setTransporter(gatein.getTransporter());
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
				existingData.setOutTransporterStatus(gatein.getOutTransporterStatus());
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
					c.setContainerWeight(gatein.getTareWeight());
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
				
				

				gateinrepo.save(existingData);
//				int update = vehicleTrackRepo.updateData(cid, bid, gatein.getGateInId(), gatein.getVehicleNo(),
//						gatein.getProfitcentreId(), gatein.getTransporterStatus().charAt(0), gatein.getTransporterName(),
//						gatein.getTransporter(), gatein.getDriverName(), 'E', "Gate01", gatein.getShift(),
//						user);
				return new ResponseEntity<>(existingData, HttpStatus.OK);

			}
		}
	}
	
	@GetMapping("/searchBeforeSaveDataForRescangatein")
	public ResponseEntity<?> searchBeforeSaveDataForRescangatein(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("val") String val) {
		try {

			List<Object[]> data = gateinrepo.searchBeforeSaveDataForRescangatein(cid, bid, val);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);

			}

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping("/saveReScanGateInData")
	public ResponseEntity<?> saveReScanGateInData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("flag") String flag, @RequestBody GateIn gatein) {

		System.out.println("gatein "+gatein);
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
				
//				Boolean check = vehicleTrackRepo.checkVehicleNo(cid, bid, gatein.getVehicleNo());
//
//				if (check) {
//					return new ResponseEntity<>("The vehicle is already inside.", HttpStatus.CONFLICT);
//				}

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05063", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("GIGG%06d", nextNumericNextID1);

				gatein.setGateInId(HoldNextIdD1);
				gatein.setCompanyId(cid);
				gatein.setLineNo(cn.get(0).getIgmLineNo());
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
				gatein.setProcessId("P00218");
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
					c.setRscanIn('Y');
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
				
//				VehicleTrack v = new VehicleTrack();
//				v.setCompanyId(cid);
//				v.setBranchId(bid);
//				v.setFinYear(gatein.getFinYear());
//				v.setVehicleNo(gatein.getVehicleNo());
//				v.setProfitcentreId(gatein.getProfitcentreId());
//				v.setSrNo(1);
//				v.setTransporterStatus(gatein.getTransporterStatus().charAt(0));
//				v.setTransporterName(gatein.getTransporterName());
//				v.setTransporter(gatein.getTransporter());
//				v.setDriverName(gatein.getDriverName());
//				v.setVehicleStatus('E');
//				v.setGateInId(HoldNextIdD1);
//				v.setGateInDate(new Date());
//				v.setGateNoIn("Gate01");
//				v.setShiftIn(gatein.getShift());
//				v.setStatus('A');
//				v.setCreatedBy(user);
//				v.setCreatedDate(new Date());
//				v.setApprovedBy(user);
//				v.setApprovedDate(new Date());
//
//				vehicleTrackRepo.save(v);
				
				

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
				existingData.setInTransporterName(gatein.getInTransporterName());
				existingData.setInVehicleNo(gatein.getInVehicleNo());
			//	existingData.setTransporter(gatein.getTransporter());
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
				existingData.setInTransporterStatus(gatein.getInTransporterStatus());
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
					c.setContainerWeight(gatein.getTareWeight());
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
				
				

				gateinrepo.save(existingData);
//				int update = vehicleTrackRepo.updateData(cid, bid, gatein.getGateInId(), gatein.getVehicleNo(),
//						gatein.getProfitcentreId(), gatein.getTransporterStatus().charAt(0), gatein.getTransporterName(),
//						gatein.getTransporter(), gatein.getDriverName(), 'E', "Gate01", gatein.getShift(),
//						user);
				return new ResponseEntity<>(existingData, HttpStatus.OK);

			}
		}
	}
	
}
