package com.cwms.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cwms.entities.CFIgm;
import com.cwms.entities.CfImportGatePassVehDtl;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.DestuffCrg;
import com.cwms.entities.ImportGatePass;
import com.cwms.entities.ImportInventory;
import com.cwms.entities.Party;
import com.cwms.entities.VehicleDetailDTO;
import com.cwms.entities.VehicleTrack;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.DestuffCrgRepository;
import com.cwms.repository.DestuffRepository;
import com.cwms.repository.ImportGatePassRepo;
import com.cwms.repository.ImportGatePassVehRepository;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.PartyRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.VehicleTrackRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.coobird.thumbnailator.Thumbnails;

@RestController
@CrossOrigin("*")
@RequestMapping("/importGatePass")
public class ImportGatePassController {

	@Autowired
	private ImportGatePassRepo importgatepassrepo;

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private VehicleTrackRepository vehicleTrackRepo;

	@Autowired
	private DestuffRepository destuffRepo;

	@Autowired
	private DestuffCrgRepository destuffcrgrepo;

	@Autowired
	private PartyRepository partyrepo;

	@Value("${file.upload.import}")
	private String filePath;

	@Autowired
	private ImportGatePassVehRepository importgatepassvehrepo;

	@Autowired
	private ImportInventoryRepository importinventoryrepo;

	@GetMapping("/getItemWiseData")
	public ResponseEntity<?> getItemData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("item") String item,@RequestParam("type") String type) {

		List<Cfigmcn> data = cfigmcnrepo.getDataForGatePass(cid, bid, igm, item, type);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		if ("LCL".equals(data.get(0).getContainerStatus())) {
			Cfigmcrg crg = cfigmcrgrepo.getData4(cid, bid, igm, item);

			if (crg == null) {
				return new ResponseEntity<>("Item data not found", HttpStatus.CONFLICT);
			}
			
			if("H".equals(crg.getHoldStatus())) {
				return new ResponseEntity<>("Item no. "+crg.getIgmLineNo()+" is on hold.", HttpStatus.CONFLICT);
			}

			DestuffCrg crgData = destuffcrgrepo.getSingleData2(cid, bid, crg.getIgmTransId(), igm, item);

			if (crgData == null) {
				return new ResponseEntity<>("Destuff data not found", HttpStatus.CONFLICT);
			}

			if (crgData.getYardPackages().subtract(BigDecimal.valueOf(crgData.getQtyTakenOut()))
					.compareTo(BigDecimal.ZERO) <= 0) {
				return new ResponseEntity<>("Gate pass already generated.", HttpStatus.CONFLICT);
			}

			Map<String, Object> con = new HashedMap<>();
			con.put("crg", crg);
			con.put("container", data);
			con.put("containerStatus", "LCL");
			con.put("crgData", crgData);

			return new ResponseEntity<>(con, HttpStatus.OK);
		} else {
			Cfigmcrg crg = cfigmcrgrepo.getData4(cid, bid, igm, item);

			if (crg == null) {
				return new ResponseEntity<>("Item data not found", HttpStatus.CONFLICT);
			}
			
			data.stream().forEach(c->{
				if (c.getDeStuffId() != null && !c.getDeStuffId().isEmpty()) {
					DestuffCrg d = destuffcrgrepo.getSingleData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo(),
							c.getDeStuffId());
					
					c.setOldActualNoOfPackages(new BigDecimal(d.getQtyTakenOut()));
				}
			});

			Map<String, Object> con = new HashedMap<>();
			con.put("crg", crg);
			con.put("container", data);
			con.put("containerStatus", "FCL");

			return new ResponseEntity<>(con, HttpStatus.OK);
		}

	}

	@PostMapping("/saveItemwiseData")
	public ResponseEntity<?> saveItemwiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("vehicleStatus") String vehicleStatus,
			@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(data.get("crg")), ImportGatePass.class);

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

		List<ImportGatePass> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<ImportGatePass>>() {
				});
		if (container.isEmpty()) {
			return new ResponseEntity<>("Container Data not found", HttpStatus.CONFLICT);
		}

		int sr = 1;
		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05068", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("IMG%07d", nextNumericNextID1);

		for (ImportGatePass i : container) {
			if (i.getGatePassId().isEmpty()) {

				if ("N".equals(vehicleStatus)) {
					Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
							i.getContainerNo());
					Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

					DestuffCrg d = new DestuffCrg();

					if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {
						d = destuffcrgrepo.getSingleData1(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
								cn.getDeStuffId());
					}

					i.setGatePassId(HoldNextIdD1);
					// i.setVehicleGatePassId(HoldNextIdD1);
					i.setVehStatus(vehicleStatus);
					i.setSrNo(sr);

					if ("N".equals(vehicleStatus)) {
						i.setVehicleNo("");
						i.setDriverName("");
						i.setVehicleGatePassId("");
					}

					i.setCompanyId(cid);
					i.setBranchId(bid);
					i.setSl(igm.getShippingLine());
					i.setStatus("A");
					i.setShift(crg.getShift());
					i.setGrnNo(crg.getGrnNo());
					i.setGrnDate(crg.getGrnDate());
					i.setCreatedBy(user);
					i.setCreatedDate(new Date());
					i.setApprovedBy(user);
					i.setApprovedDate(new Date());
					i.setComments(crg.getComments());
					i.setStampDuty(crg.getStampDuty());
					i.setCinNo(crg.getCinNo());
					i.setCinDate(crg.getCinDate());
					i.setDoNo(crg.getDoNo());
					i.setOocNo(crg.getOocNo());
					i.setOocDate(crg.getOocDate());
					i.setDoDate(crg.getDoDate());
					i.setDoValidityDate(crg.getDoValidityDate());
					i.setMtyYardLocation(crg.getMtyYardLocation());
					i.setCha(cr.getChaCode());
					i.setBeDate(cr.getBeDate());

					if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {

						if (d != null) {
							i.setDestuffId(d.getDeStuffId());
							i.setDestuffLineId(d.getDeStuffLineId());
							
							d.setQtyTakenOut(d.getQtyTakenOut()+i.getQtyTakenOut().intValue());
							
							destuffcrgrepo.save(d);
						}
					}

					importgatepassrepo.save(i);

					if (cn != null) {
						cn.setGatePassNo(HoldNextIdD1);
						cn.setShift(i.getShift());
						cn.setGrnNo(i.getGrnNo());
						cn.setGrnDate(i.getGrnDate());
						cn.setStampDuty(i.getStampDuty());
						cn.setCinNo(i.getCinNo());
						cn.setCinDate(crg.getCinDate());
						cn.setDoNo(crg.getDoNo());
						cn.setOocNo(crg.getOocNo());
						cn.setOocDate(crg.getOocDate());
						cn.setDoDate(crg.getDoDate());
						cn.setDoValidityDate(crg.getDoValidityDate());

						cfigmcnrepo.save(cn);
					}

					if (!"N".equals(i.getVehStatus())) {
						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, i.getVehicleNo(),
								i.getVehicleGatePassId());

						if (track != null) {
							track.setGatePassNo(HoldNextIdD1);
							track.setIgmNo(i.getIgmNo());
							track.setIgmTransId(i.getIgmTransId());

							vehicleTrackRepo.save(track);
						}
						
						
						int srNo = importgatepassvehrepo.getcount(cid, bid, crg.getGatePassId());

						

						CfImportGatePassVehDtl dtl = new CfImportGatePassVehDtl();
						dtl.setCompanyId(cid);
						dtl.setBranchId(bid);
						dtl.setFinYear("2024");
						dtl.setProfitcentreId(cr.getProfitcentreId());
						dtl.setGatePassId(HoldNextIdD1);
						dtl.setSrNo(srNo + 1);
						dtl.setContainerNo("");
						dtl.setIgmNo(crg.getIgmNo());
						dtl.setIgmLineNo(crg.getIgmTransId());
						dtl.setIgmTransId(crg.getIgmTransId());
						dtl.setTransType("FCL");
						dtl.setGrossWt(crg.getGrossWt());
						dtl.setNoOfPackage(crg.getYardPackages());
						dtl.setVehicleNo(i.getVehicleNo());
						dtl.setVehicleGatePassId(i.getVehicleGatePassId());
						dtl.setQtyTakenOut(i.getQtyTakenOut());
						dtl.setGwTakenOut(i.getGwTakenOut());
						dtl.setCreatedBy(user);
						dtl.setCreatedDate(new Date());
						dtl.setStatus('A');

						importgatepassvehrepo.save(dtl);
					}

					ImportInventory existingInv = importinventoryrepo.getById(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
							cn.getContainerNo(), cn.getGateInId());

					if (existingInv != null) {
						existingInv.setGatePassDate(new Date());
						existingInv.setGatePassNo(HoldNextIdD1);

						importinventoryrepo.save(existingInv);
					}

				} else {
                      if(i.getVehicleNo() != null && !i.getVehicleNo().isEmpty()) {
                    		Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
            						i.getContainerNo());
            				Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

            				DestuffCrg d = new DestuffCrg();

            				if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {
            					d = destuffcrgrepo.getSingleData1(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
            							cn.getDeStuffId());
            				}

            				i.setGatePassId(HoldNextIdD1);
            				// i.setVehicleGatePassId(HoldNextIdD1);
            				i.setVehStatus(vehicleStatus);
            				i.setSrNo(sr);

            				if ("N".equals(vehicleStatus)) {
            					i.setVehicleNo("");
            					i.setDriverName("");
            					i.setVehicleGatePassId("");
            				}

            				i.setCompanyId(cid);
            				i.setBranchId(bid);
            				i.setSl(igm.getShippingLine());
            				i.setStatus("A");
            				i.setShift(crg.getShift());
            				i.setGrnNo(crg.getGrnNo());
            				i.setGrnDate(crg.getGrnDate());
            				i.setCreatedBy(user);
            				i.setCreatedDate(new Date());
            				i.setApprovedBy(user);
            				i.setApprovedDate(new Date());
            				i.setComments(crg.getComments());
            				i.setStampDuty(crg.getStampDuty());
            				i.setCinNo(crg.getCinNo());
            				i.setCinDate(crg.getCinDate());
            				i.setDoNo(crg.getDoNo());
            				i.setOocNo(crg.getOocNo());
            				i.setOocDate(crg.getOocDate());
            				i.setDoDate(crg.getDoDate());
            				i.setDoValidityDate(crg.getDoValidityDate());
            				i.setMtyYardLocation(crg.getMtyYardLocation());
            				i.setCha(cr.getChaCode());
            				i.setBeDate(cr.getBeDate());
            				if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {

            					if (d != null) {
            						i.setDestuffId(d.getDeStuffId());
            						i.setDestuffLineId(d.getDeStuffLineId());
            						
            						d.setQtyTakenOut(d.getQtyTakenOut()+i.getQtyTakenOut().intValue());
        							
        							destuffcrgrepo.save(d);
            					}
            				}

            				importgatepassrepo.save(i);

            				if (cn != null) {
            					cn.setGatePassNo(HoldNextIdD1);
            					cn.setShift(i.getShift());
            					cn.setGrnNo(i.getGrnNo());
            					cn.setGrnDate(i.getGrnDate());
            					cn.setStampDuty(i.getStampDuty());
            					cn.setCinNo(i.getCinNo());
            					cn.setCinDate(crg.getCinDate());
            					cn.setDoNo(crg.getDoNo());
            					cn.setOocNo(crg.getOocNo());
            					cn.setOocDate(crg.getOocDate());
            					cn.setDoDate(crg.getDoDate());
            					cn.setDoValidityDate(crg.getDoValidityDate());

            					cfigmcnrepo.save(cn);
            				}

            				if (!"N".equals(i.getVehStatus())) {
            					VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, i.getVehicleNo(),
            							i.getVehicleGatePassId());

            					if (track != null) {
            						track.setGatePassNo(HoldNextIdD1);
            						track.setIgmNo(i.getIgmNo());
            						track.setIgmTransId(i.getIgmTransId());

            						vehicleTrackRepo.save(track);
            					}
            					
            					int srNo = importgatepassvehrepo.getcount(cid, bid, crg.getGatePassId());
            					CfImportGatePassVehDtl dtl = new CfImportGatePassVehDtl();
        						dtl.setCompanyId(cid);
        						dtl.setBranchId(bid);
        						dtl.setFinYear("2024");
        						dtl.setProfitcentreId(cr.getProfitcentreId());
        						dtl.setGatePassId(HoldNextIdD1);
        						dtl.setSrNo(srNo + 1);
        						dtl.setContainerNo("");
        						dtl.setIgmNo(crg.getIgmNo());
        						dtl.setIgmLineNo(crg.getIgmTransId());
        						dtl.setIgmTransId(crg.getIgmTransId());
        						dtl.setTransType("FCL");
        						dtl.setGrossWt(crg.getGrossWt());
        						dtl.setNoOfPackage(crg.getYardPackages());
        						dtl.setVehicleNo(i.getVehicleNo());
        						dtl.setVehicleGatePassId(i.getVehicleGatePassId());
        						dtl.setQtyTakenOut(i.getQtyTakenOut());
        						dtl.setGwTakenOut(i.getGwTakenOut());
        						dtl.setCreatedBy(user);
        						dtl.setCreatedDate(new Date());
        						dtl.setStatus('A');

        						importgatepassvehrepo.save(dtl);
            				}

            				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
            						cn.getContainerNo(), cn.getGateInId());

            				if (existingInv != null) {
            					existingInv.setGatePassDate(new Date());
            					existingInv.setGatePassNo(HoldNextIdD1);

            					importinventoryrepo.save(existingInv);
            				}

                      }
				}

			
				processnextidrepo.updateAuditTrail(cid, bid, "P05068", HoldNextIdD1, "2024");
				sr++;
			} else {
				ImportGatePass existing = importgatepassrepo.getSingleData(cid, bid, i.getIgmNo(), i.getIgmLineNo(),
						i.getContainerNo(), i.getGatePassId(), i.getSrNo());

				if (existing != null) {
					existing.setEditedBy(user);
					existing.setEditedDate(new Date());
					existing.setShift(crg.getShift());
					existing.setGrnNo(crg.getGrnNo());
					existing.setGrnDate(crg.getGrnDate());
					existing.setComments(crg.getComments());
					existing.setStampDuty(crg.getStampDuty());
					existing.setCinNo(crg.getCinNo());
					existing.setCinDate(crg.getCinDate());
					existing.setDoNo(crg.getDoNo());
					existing.setOocNo(crg.getOocNo());
					existing.setOocDate(crg.getOocDate());
					existing.setDoDate(crg.getDoDate());
					existing.setDoValidityDate(crg.getDoValidityDate());
					existing.setMtyYardLocation(crg.getMtyYardLocation());
					existing.setVehStatus(vehicleStatus);
					if ("N".equals(vehicleStatus)) {

						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, existing.getVehicleNo(),
								existing.getVehicleGatePassId());

						if (track != null) {
							track.setGatePassNo("");
							track.setIgmNo("");
							track.setIgmTransId("");

							vehicleTrackRepo.save(track);
						}
						existing.setVehicleNo("");
						existing.setDriverName("");
						existing.setVehicleGatePassId("");
					} else {

						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, i.getVehicleNo(),
								i.getVehicleGatePassId());

						if (track != null) {
							track.setGatePassNo(existing.getGatePassId());
							track.setIgmNo(existing.getIgmNo());
							track.setIgmTransId(existing.getIgmTransId());

							vehicleTrackRepo.save(track);
						}
						existing.setVehicleNo(i.getVehicleNo());
						existing.setDriverName(i.getDriverName());
						existing.setVehicleGatePassId(i.getVehicleGatePassId());
					}

					importgatepassrepo.save(existing);

					Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
							i.getContainerNo());

					if (cn != null) {

						cn.setShift(i.getShift());
						cn.setGrnNo(i.getGrnNo());
						cn.setGrnDate(i.getGrnDate());
						cn.setStampDuty(i.getStampDuty());
						cn.setCinNo(i.getCinNo());
						cn.setCinDate(crg.getCinDate());
						cn.setDoNo(crg.getDoNo());
						cn.setOocNo(crg.getOocNo());
						cn.setOocDate(crg.getOocDate());
						cn.setDoDate(crg.getDoDate());
						cn.setDoValidityDate(crg.getDoValidityDate());

						cfigmcnrepo.save(cn);
					}
				}
			}
		}

		if (crg.getGatePassId() == null || crg.getGatePassId().isEmpty()) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(),
					HoldNextIdD1);

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(gatepass, HttpStatus.OK);
		} else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(),
					crg.getGatePassId());

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(gatepass, HttpStatus.OK);
		}

	}
	
	
	
	@PostMapping("/saveItemwiseCRGData")
	public ResponseEntity<?> saveItemwiseCRGData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("vehicleStatus") String vehicleStatus,
			@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(data.get("crg")), ImportGatePass.class);

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

		List<ImportGatePass> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<ImportGatePass>>() {
				});
		if (container.isEmpty()) {
			return new ResponseEntity<>("Container Data not found", HttpStatus.CONFLICT);
		}

		int sr = 1;
		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05068", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("IMG%07d", nextNumericNextID1);

		for (ImportGatePass i : container) {
			if (i.getGatePassId().isEmpty()) {

				
					Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
							i.getContainerNo());
					Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

					DestuffCrg d = new DestuffCrg();

					if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {
						d = destuffcrgrepo.getSingleData1(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
								cn.getDeStuffId());
					}

					i.setGatePassId(HoldNextIdD1);
					// i.setVehicleGatePassId(HoldNextIdD1);
					i.setVehStatus(vehicleStatus);
					i.setSrNo(sr);

					if ("N".equals(vehicleStatus)) {
						i.setVehicleNo("");
						i.setDriverName("");
						i.setVehicleGatePassId("");
					}

					i.setCompanyId(cid);
					i.setBranchId(bid);
					i.setSl(igm.getShippingLine());
					i.setStatus("A");
					i.setShift(crg.getShift());
					i.setGrnNo(crg.getGrnNo());
					i.setGrnDate(crg.getGrnDate());
					i.setCreatedBy(user);
					i.setCreatedDate(new Date());
					i.setApprovedBy(user);
					i.setApprovedDate(new Date());
					i.setComments(crg.getComments());
					i.setStampDuty(crg.getStampDuty());
					i.setCinNo(crg.getCinNo());
					i.setCinDate(crg.getCinDate());
					i.setDoNo(crg.getDoNo());
					i.setOocNo(crg.getOocNo());
					i.setOocDate(crg.getOocDate());
					i.setDoDate(crg.getDoDate());
					i.setDoValidityDate(crg.getDoValidityDate());
					i.setMtyYardLocation(crg.getMtyYardLocation());
					i.setCha(cr.getChaCode());
					i.setBeDate(cr.getBeDate());
					if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {

						if (d != null) {
							i.setDestuffId(d.getDeStuffId());
							i.setDestuffLineId(d.getDeStuffLineId());
							
							d.setQtyTakenOut(d.getQtyTakenOut()+i.getQtyTakenOut().intValue());
							
							destuffcrgrepo.save(d);
						}
					}

					importgatepassrepo.save(i);

					if (cn != null) {
						cn.setGatePassNo(HoldNextIdD1);
						cn.setShift(i.getShift());
						cn.setGrnNo(i.getGrnNo());
						cn.setGrnDate(i.getGrnDate());
						cn.setStampDuty(i.getStampDuty());
						cn.setCinNo(i.getCinNo());
						cn.setCinDate(crg.getCinDate());
						cn.setDoNo(crg.getDoNo());
						cn.setOocNo(crg.getOocNo());
						cn.setOocDate(crg.getOocDate());
						cn.setDoDate(crg.getDoDate());
						cn.setDoValidityDate(crg.getDoValidityDate());

						cfigmcnrepo.save(cn);
					}

//					if (!"N".equals(i.getVehStatus())) {
//						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, i.getVehicleNo(),
//								i.getVehicleGatePassId());
//
//						if (track != null) {
//							track.setGatePassNo(HoldNextIdD1);
//							track.setIgmNo(i.getIgmNo());
//							track.setIgmTransId(i.getIgmTransId());
//
//							vehicleTrackRepo.save(track);
//						}
//					}

					ImportInventory existingInv = importinventoryrepo.getById(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
							cn.getContainerNo(), cn.getGateInId());

					if (existingInv != null) {
						existingInv.setGatePassDate(new Date());
						existingInv.setGatePassNo(HoldNextIdD1);

						importinventoryrepo.save(existingInv);
					}

				

			
				processnextidrepo.updateAuditTrail(cid, bid, "P05068", HoldNextIdD1, "2024");
				sr++;
			} else {
				ImportGatePass existing = importgatepassrepo.getSingleData(cid, bid, i.getIgmNo(), i.getIgmLineNo(),
						i.getContainerNo(), i.getGatePassId(), i.getSrNo());

				if (existing != null) {
					existing.setEditedBy(user);
					existing.setEditedDate(new Date());
					existing.setShift(crg.getShift());
					existing.setGrnNo(crg.getGrnNo());
					existing.setGrnDate(crg.getGrnDate());
					existing.setComments(crg.getComments());
					existing.setStampDuty(crg.getStampDuty());
					existing.setCinNo(crg.getCinNo());
					existing.setCinDate(crg.getCinDate());
					existing.setDoNo(crg.getDoNo());
					existing.setOocNo(crg.getOocNo());
					existing.setOocDate(crg.getOocDate());
					existing.setDoDate(crg.getDoDate());
					existing.setDoValidityDate(crg.getDoValidityDate());
					existing.setMtyYardLocation(crg.getMtyYardLocation());
					existing.setVehStatus(vehicleStatus);
					if ("N".equals(vehicleStatus)) {

						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo1(cid, bid, existing.getVehicleNo(),
								existing.getVehicleGatePassId());

						if (track != null) {
							track.setGatePassNo("");
							track.setIgmNo("");
							track.setIgmTransId("");

							vehicleTrackRepo.save(track);
						}
						existing.setVehicleNo("");
						existing.setDriverName("");
						existing.setVehicleGatePassId("");
					} else {

						VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, i.getVehicleNo(),
								i.getVehicleGatePassId());

						if (track != null) {
							track.setGatePassNo(existing.getGatePassId());
							track.setIgmNo(existing.getIgmNo());
							track.setIgmTransId(existing.getIgmTransId());

							vehicleTrackRepo.save(track);
						}
						existing.setVehicleNo(i.getVehicleNo());
						existing.setDriverName(i.getDriverName());
						existing.setVehicleGatePassId(i.getVehicleGatePassId());
					}

					importgatepassrepo.save(existing);

					Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
							i.getContainerNo());

					if (cn != null) {

						cn.setShift(i.getShift());
						cn.setGrnNo(i.getGrnNo());
						cn.setGrnDate(i.getGrnDate());
						cn.setStampDuty(i.getStampDuty());
						cn.setCinNo(i.getCinNo());
						cn.setCinDate(crg.getCinDate());
						cn.setDoNo(crg.getDoNo());
						cn.setOocNo(crg.getOocNo());
						cn.setOocDate(crg.getOocDate());
						cn.setDoDate(crg.getDoDate());
						cn.setDoValidityDate(crg.getDoValidityDate());

						cfigmcnrepo.save(cn);
					}
				}
			}
		}

		if (crg.getGatePassId() == null || crg.getGatePassId().isEmpty()) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(),
					HoldNextIdD1);

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(gatepass, HttpStatus.OK);
		} else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(),
					crg.getGatePassId());

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(gatepass, HttpStatus.OK);
		}

	}

	@GetMapping("/searchByItemwise")
	public ResponseEntity<?> searchByItemwise(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = importgatepassrepo.searchForItemWise(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/selectSearchedItemWiseData")
	public ResponseEntity<?> selectSearchedItemWiseData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("igmline") String igmline,
			@RequestParam("gatepassid") String gatepassid, @RequestParam("type") String type) {

		if ("LCL".equals(type)) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData3(cid, bid, igm, igmline, gatepassid);

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}
			Party party = partyrepo.getDataByCustomerCode(cid, bid, gatepass.get(0).getCha());
			Map<String, Object> data = new HashMap<>();
			data.put("gatepass", gatepass);
			data.put("cha", party.getPartyName());

			return new ResponseEntity<>(data, HttpStatus.OK);
		} else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, igm, igmline, gatepassid);

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> data = new HashMap<>();
			data.put("gatepass", gatepass);
			data.put("cha", "");

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

	}

	@GetMapping("/selectSearchedItemWiseData1")
	public ResponseEntity<?> selectSearchedItemWiseData1(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm, @RequestParam("trans") String trans,
			@RequestParam("igmline") String igmline) {

		System.out.println("igm,trans, igmline " + igm + " " + trans + " " + igmline);
//		List<String> gatepassid = importgatepassrepo.getLastGatePassId(cid, bid, igm, trans, igmline);
//
//		if (gatepassid.isEmpty()) {
//			return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
//		}
//
//		List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, igm, trans, gatepassid.get(0).toString());
//
//		if (gatepass.isEmpty()) {
//			return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
//		}
//
//		return new ResponseEntity<>(gatepass, HttpStatus.OK);

		List<Object[]> gatepassid = importgatepassrepo.getLastGatePassId1(cid, bid, igm, trans, igmline);

		if (gatepassid.isEmpty()) {
			return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
		}

		System.out.println(
				"gatepassid " + cid + " " + bid + " " + igm + " " + igmline + " " + gatepassid.get(0)[0].toString());

		if ("LCL".equals(gatepassid.get(0)[1].toString())) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData3(cid, bid, igm,
					igmline.isEmpty() ? gatepassid.get(0)[2].toString() : igmline, gatepassid.get(0)[0].toString());

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}
			Party party = partyrepo.getDataByCustomerCode(cid, bid, gatepass.get(0).getCha());
			Map<String, Object> data = new HashMap<>();
			data.put("gatepass", gatepass);
			data.put("cha", party.getPartyName());

			return new ResponseEntity<>(data, HttpStatus.OK);
		} else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData(cid, bid, igm, igmline,
					gatepassid.get(0)[0].toString());

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> data = new HashMap<>();
			data.put("gatepass", gatepass);
			data.put("cha", "");

			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@PostMapping("/uploadItemwiseImage")
	public ResponseEntity<String> uploadImage(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmline") String igmline,
			@RequestParam("gatePassId") String gatepassid, @RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
		}

		try {
			// Define the path to save the file
			String uploadDir = filePath;
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdir();
			}

			// Define the compressed file path
			String compressedFilePath = uploadDir + gatepassid + ".png";
			File compressedFile = new File(compressedFilePath);

			// Initialize compression parameters
			double quality = 0.9; // Start with near lossless compression
			int width = 800; // Initial width
			int height = 600; // Initial height
			long maxFileSize = 200 * 1024; // Max file size 200KB

			// Compress and check file size
			do {
				// Compress the image
				Thumbnails.of(file.getInputStream()).size(width, height) // Resize image
						.outputQuality(quality) // Adjust compression quality
						.toFile(compressedFile);

				// Check the file size
				long fileSize = compressedFile.length();

				// If the file is larger than 200KB, reduce quality or dimensions
				if (fileSize > maxFileSize) {
					quality -= 0.05; // Reduce quality by 5% in each iteration
					width -= 100; // Reduce width by 100px
					height -= 75; // Reduce height proportionally
				}

				// Break if file is smaller than 200KB or quality is too low
			} while (compressedFile.length() > maxFileSize && quality > 0.1 && width > 100 && height > 100);

			// Update the database with the new file path
			List<ImportGatePass> gatePass = importgatepassrepo.getData1(cid, bid, igm, igmline, gatepassid);
			if (!gatePass.isEmpty()) {
				gatePass.forEach(g -> {
					g.setWebCamPath(compressedFilePath);
					importgatepassrepo.save(g);
				});
			}

			return new ResponseEntity<>("File uploaded successfully: " + compressedFilePath, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/uploadConwiseImage")
	public ResponseEntity<String> uploadImage1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("gatePassId") String gatepassid,
			@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
		}

		try {
			// Define the path to save the file
			String uploadDir = filePath;
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdir();
			}

			// Define the compressed file path
			String compressedFilePath = uploadDir + gatepassid + ".png";
			File compressedFile = new File(compressedFilePath);

			// Initialize compression parameters
			double quality = 0.9; // Start with near lossless compression
			int width = 800; // Initial width
			int height = 600; // Initial height
			long maxFileSize = 200 * 1024; // Max file size 200KB

			// Compress and check file size
			do {
				// Compress the image
				Thumbnails.of(file.getInputStream()).size(width, height) // Resize image
						.outputQuality(quality) // Adjust compression quality
						.toFile(compressedFile);

				// Check the file size
				long fileSize = compressedFile.length();

				// If the file is larger than 200KB, reduce quality or dimensions
				if (fileSize > maxFileSize) {
					quality -= 0.05; // Reduce quality by 5% in each iteration
					width -= 100; // Reduce width by 100px
					height -= 75; // Reduce height proportionally
				}

				// Break if file is smaller than 200KB or quality is too low
			} while (compressedFile.length() > maxFileSize && quality > 0.1 && width > 100 && height > 100);

			// Update the database with the new file path
			List<ImportGatePass> gatePass = importgatepassrepo.getData3(cid, bid, igm, gatepassid);
			if (!gatePass.isEmpty()) {
				gatePass.forEach(g -> {
					g.setWebCamPath(compressedFilePath);
					importgatepassrepo.save(g);
				});
			}

			return new ResponseEntity<>("File uploaded successfully: " + compressedFilePath, HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getItemwiseImage")
	public ResponseEntity<?> getImage(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmline") String igmline,
			@RequestParam("gatePassId") String gatepassid, @RequestParam("sr") int sr) {
		try {

			ImportGatePass data = importgatepassrepo.getData2(cid, bid, igm, igmline, gatepassid, sr);
			// Construct the file path

			if (data != null) {
				String filePath = data.getWebCamPath();
				File file = new File(filePath);

				// Check if the file exists
				if (!file.exists()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}

				// Serve the file
				Path path = Paths.get(file.getAbsolutePath());
				Resource resource = new UrlResource(path.toUri());

				return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG) // Adjust according to your image type
						.body(resource);
			} else {
				return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
			}

		} catch (MalformedURLException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getContWiseData")
	public ResponseEntity<?> getContWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("cont") String cont) {

		List<Cfigmcn> data = cfigmcnrepo.getDataForGatePass1(cid, bid, igm, cont);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		
		Boolean check = data.stream().anyMatch(c-> "H".equals(c.getHoldStatus()));
		
		if (check) {
			return new ResponseEntity<>("Container is hold.", HttpStatus.CONFLICT);
		}

		List<Cfigmcrg> crg = new ArrayList<>();
		
		

		data.stream().forEach(c -> {
			Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, c.getIgmNo(), c.getIgmLineNo());

			System.out.println("cr " + cr);

			if (cr != null) {
				crg.add(cr);
			}
		});

		Map<String, Object> con = new HashedMap<>();
		con.put("crg", crg);
		con.put("container", data);

		return new ResponseEntity<>(con, HttpStatus.OK);
	}

	@PostMapping("/saveContainerwiseData")
	public ResponseEntity<?> saveContainerwiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> data)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(data.get("crg")), ImportGatePass.class);

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}
		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

		List<ImportGatePass> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<ImportGatePass>>() {
				});
		if (container.isEmpty()) {
			return new ResponseEntity<>("Container Data not found", HttpStatus.CONFLICT);
		}

		int sr = 1;
		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05068", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(3));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("IMG%07d", nextNumericNextID1);

		for (ImportGatePass i : container) {
			if (i.getGatePassId().isEmpty()) {

				Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
						i.getContainerNo());
				Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

				DestuffCrg d = new DestuffCrg();

				if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {
					d = destuffcrgrepo.getSingleData1(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
							cn.getDeStuffId());
				}

				i.setGatePassId(HoldNextIdD1);
				i.setVehicleGatePassId("Y".equals(crg.getVehStatus()) ? crg.getVehicleGatePassId() : "");
				i.setVehStatus(crg.getVehStatus());
				i.setSrNo(sr);
				i.setGatePassType("CON");

//					if("N".equals(vehicleStatus)) {
//						i.setVehicleNo("");
//						i.setDriverName("");
//					}
				i.setVehicleNo("Y".equals(crg.getVehStatus()) ? crg.getVehicleNo() : "");
				i.setDriverName("Y".equals(crg.getVehStatus()) ? crg.getDriverName() : "");
				i.setCompanyId(cid);
				i.setBranchId(bid);
				i.setDpdFlag(crg.getDpdFlag());
				i.setSl(igm.getShippingLine());
				i.setStatus("A");
				i.setShift(crg.getShift());
				// i.setGrnNo(crg.getGrnNo());
				// i.setGrnDate(crg.getGrnDate());
				i.setCreatedBy(user);
				i.setCreatedDate(new Date());
				i.setApprovedBy(user);
				i.setApprovedDate(new Date());
				i.setComments(crg.getComments());
				// i.setStampDuty(crg.getStampDuty());
				// i.setCinNo(crg.getCinNo());
				// i.setCinDate(crg.getCinDate());
				i.setDoNo(crg.getDoNo());
				i.setOocNo(crg.getOocNo());
				i.setOocDate(crg.getOocDate());
				i.setDoDate(crg.getDoDate());
				i.setDoValidityDate(crg.getDoValidityDate());
				i.setMtyYardLocation(crg.getMtyYardLocation());
				i.setCha(cr.getChaCode());
				

				if (cn.getDeStuffId() != null && !cn.getDeStuffId().isEmpty()) {

					if (d != null) {
						i.setDestuffId(d.getDeStuffId());
						i.setDestuffLineId(d.getDeStuffLineId());
					}
				}
				i.setBeDate(cr.getBeDate());
				i.setBoe(cr.getBeNo());
				importgatepassrepo.save(i);

				if (cn != null) {
					cn.setGatePassNo(HoldNextIdD1);
					cn.setShift(i.getShift());
					cn.setGrnNo(i.getGrnNo());
					cn.setGrnDate(i.getGrnDate());
					cn.setStampDuty(i.getStampDuty());
					cn.setCinNo(i.getCinNo());
					cn.setCinDate(i.getCinDate());
					cn.setDoNo(crg.getDoNo());
					cn.setOocNo(crg.getOocNo());
					cn.setOocDate(crg.getOocDate());
					cn.setDoDate(crg.getDoDate());
					cn.setDoValidityDate(crg.getDoValidityDate());

					cfigmcnrepo.save(cn);
				}

				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, cn.getIgmTransId(), cn.getIgmNo(),
						cn.getContainerNo(), cn.getGateInId());

				if (existingInv != null) {
					existingInv.setGatePassDate(new Date());
					existingInv.setGatePassNo(HoldNextIdD1);

					importinventoryrepo.save(existingInv);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05068", HoldNextIdD1, "2024");
				sr++;
			} else {
				ImportGatePass existing = importgatepassrepo.getSingleData(cid, bid, i.getIgmNo(), i.getIgmLineNo(),
						i.getContainerNo(), i.getGatePassId(), i.getSrNo());

				if (existing != null) {
					existing.setEditedBy(user);
					existing.setEditedDate(new Date());
					existing.setShift(crg.getShift());
					existing.setGrnNo(i.getGrnNo());
					existing.setGrnDate(i.getGrnDate());
					existing.setComments(crg.getComments());
					existing.setStampDuty(i.getStampDuty());
					existing.setCinNo(i.getCinNo());
					existing.setVehStatus(crg.getVehStatus());
					existing.setCinDate(i.getCinDate());
					existing.setDoNo(crg.getDoNo());
					existing.setOocNo(crg.getOocNo());
					existing.setOocDate(crg.getOocDate());
					existing.setDoDate(crg.getDoDate());
					existing.setDoValidityDate(crg.getDoValidityDate());
					existing.setMtyYardLocation(crg.getMtyYardLocation());
					existing.setVehicleNo("Y".equals(crg.getVehStatus()) ? crg.getVehicleNo() : "");
					existing.setDriverName("Y".equals(crg.getVehStatus()) ? crg.getDriverName() : "");
					existing.setVehicleGatePassId("Y".equals(crg.getVehStatus()) ? crg.getVehicleGatePassId() : "");
					importgatepassrepo.save(existing);

					Cfigmcn cn = cfigmcnrepo.getSingleData4(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
							i.getContainerNo());

					if (cn != null) {

						cn.setShift(i.getShift());
						cn.setGrnNo(i.getGrnNo());
						cn.setGrnDate(i.getGrnDate());
						cn.setStampDuty(i.getStampDuty());
						cn.setCinNo(i.getCinNo());
						cn.setCinDate(i.getCinDate());
						cn.setDoNo(crg.getDoNo());
						cn.setOocNo(crg.getOocNo());
						cn.setOocDate(crg.getOocDate());
						cn.setDoDate(crg.getDoDate());
						cn.setDoValidityDate(crg.getDoValidityDate());

						cfigmcnrepo.save(cn);
					}
				}
			}
		}

		if ("Y".equals(crg.getVehStatus()) && crg.getGatePassId().isEmpty()) {
			VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, crg.getVehicleNo(),
					crg.getVehicleGatePassId());

			if (track != null) {
				track.setGatePassNo(HoldNextIdD1);
				track.setIgmNo(crg.getIgmNo());
				track.setIgmTransId(crg.getIgmTransId());

				vehicleTrackRepo.save(track);
				
				int srNo = importgatepassvehrepo.getcount(cid, bid, crg.getGatePassId());
				CfImportGatePassVehDtl dtl = new CfImportGatePassVehDtl();
				dtl.setCompanyId(cid);
				dtl.setBranchId(bid);
				dtl.setFinYear("2024");
				dtl.setProfitcentreId(igm.getProfitcentreId());
				dtl.setGatePassId(HoldNextIdD1);
				dtl.setSrNo(srNo + 1);
				dtl.setContainerNo("");
				dtl.setIgmNo(crg.getIgmNo());
				dtl.setIgmLineNo(crg.getIgmTransId());
				dtl.setIgmTransId(crg.getIgmTransId());
				dtl.setTransType("FCL");
				dtl.setGrossWt(crg.getGrossWt());
				dtl.setNoOfPackage(crg.getYardPackages());
				dtl.setVehicleNo(track.getVehicleNo());
				dtl.setVehicleGatePassId(track.getGateInId());
				dtl.setQtyTakenOut(BigDecimal.ZERO);
				dtl.setGwTakenOut(BigDecimal.ZERO);
				dtl.setCreatedBy(user);
				dtl.setCreatedDate(new Date());
				dtl.setStatus('A');

				importgatepassvehrepo.save(dtl);
			}
	
		}

		if ("Y".equals(crg.getVehStatus()) && !crg.getGatePassId().isEmpty()) {
			VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, crg.getVehicleNo(),
					crg.getVehicleGatePassId());

			if (track != null) {
				track.setGatePassNo(crg.getGatePassId());
				track.setIgmNo(crg.getIgmNo());
				track.setIgmTransId(crg.getIgmTransId());

				vehicleTrackRepo.save(track);
				
				
				int srNo = importgatepassvehrepo.getcount(cid, bid, crg.getGatePassId());
				CfImportGatePassVehDtl dtl = new CfImportGatePassVehDtl();
				dtl.setCompanyId(cid);
				dtl.setBranchId(bid);
				dtl.setFinYear("2024");
				dtl.setProfitcentreId(igm.getProfitcentreId());
				dtl.setGatePassId(HoldNextIdD1);
				dtl.setSrNo(srNo + 1);
				dtl.setContainerNo("");
				dtl.setIgmNo(crg.getIgmNo());
				dtl.setIgmLineNo(crg.getIgmTransId());
				dtl.setIgmTransId(crg.getIgmTransId());
				dtl.setTransType("FCL");
				dtl.setGrossWt(crg.getGrossWt());
				dtl.setNoOfPackage(crg.getYardPackages());
				dtl.setVehicleNo(track.getVehicleNo());
				dtl.setVehicleGatePassId(track.getGateInId());
				dtl.setQtyTakenOut(BigDecimal.ZERO);
				dtl.setGwTakenOut(BigDecimal.ZERO);
				dtl.setCreatedBy(user);
				dtl.setCreatedDate(new Date());
				dtl.setStatus('A');

				importgatepassvehrepo.save(dtl);
			}
		}

		if ("N".equals(crg.getVehStatus()) && !crg.getGatePassId().isEmpty()) {
			VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, crg.getVehicleNo(),
					crg.getVehicleGatePassId());

			if (track != null) {
				track.setGatePassNo("");
				track.setIgmNo("");
				track.setIgmTransId("");

				vehicleTrackRepo.save(track);
			}
		}

		if (crg.getGatePassId() == null || crg.getGatePassId().isEmpty()) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, crg.getIgmNo(), HoldNextIdD1);
			System.out.println(gatepass.size());

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(gatepass, HttpStatus.OK);
		} else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, crg.getIgmNo(), crg.getGatePassId());
			System.out.println(gatepass.size());
			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(gatepass, HttpStatus.OK);
		}

	}

	@GetMapping("/searchByContwise")
	public ResponseEntity<?> searchByContwise(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = importgatepassrepo.searchForContWise(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/selectSearchedContWiseData")
	public ResponseEntity<?> selectSearchedContWiseData(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm,
			@RequestParam("gatepassid") String gatepassid) {
		List<ImportGatePass> gatepass = importgatepassrepo.getData2(cid, bid, igm, gatepassid);

		if (gatepass.isEmpty()) {
			return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(gatepass, HttpStatus.OK);
	}

	@GetMapping("/getExistingItemWiseData")
	public ResponseEntity<?> getExistingItemWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("item") String item) {
		Cfigmcn i = cfigmcnrepo.getSingleDataFoeGatePass(cid, bid, igm, item);

		if (i == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		if (i.getGatePassNo().isEmpty() || i.getGatePassNo() == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(i.getGatePassNo(), HttpStatus.OK);

	}

	@GetMapping("/getExistingContWiseData")
	public ResponseEntity<?> getExistingContWiseData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("con") String con) {
		Cfigmcn i = cfigmcnrepo.getSingleDataFoeGatePass2(cid, bid, igm, con);

		if (i == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		if (i.getGatePassNo().isEmpty() || i.getGatePassNo() == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(i.getGatePassNo(), HttpStatus.OK);

	}

	@GetMapping("/getItems")
	public ResponseEntity<?> getItems(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> i = cfigmcnrepo.getItems(cid, bid, igm);

		if (i.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(i, HttpStatus.OK);

	}

	@GetMapping("/getContainers")
	public ResponseEntity<?> getContainers(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm) {
		List<String> i = cfigmcnrepo.getContainerss(cid, bid, igm);

		if (i.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(i, HttpStatus.OK);

	}

	@GetMapping("/getEmptyVehicle")
	public ResponseEntity<?> getEmptyVehicleGateIn(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "veh", required = false) String veh) {
		List<Object[]> i = vehicleTrackRepo.getEmptyVehGateIn(cid, bid, veh);

		if (i.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(i, HttpStatus.OK);

	}

	@GetMapping("/getEmptyVehicle1")
	public ResponseEntity<?> getEmptyVehicleGateIn1(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "veh", required = false) String veh) {
		List<Object[]> i = vehicleTrackRepo.getEmptyVehGateIn1(cid, bid, veh);

		if (i.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(i, HttpStatus.OK);

	}

	@PostMapping("/saveLCLGatePass")
	public ResponseEntity<?> saveLCLGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("vehicleStatus") String vehicleStatus,
			@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(data.get("crg")), ImportGatePass.class);

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

		if (igm == null) {
			return new ResponseEntity<>("IGM data not found", HttpStatus.CONFLICT);
		}

		List<ImportGatePass> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<ImportGatePass>>() {
				});

		if (container.isEmpty()) {
			return new ResponseEntity<>("Container Data not found", HttpStatus.CONFLICT);
		}

		Boolean isExistBe = cfigmcrgrepo.isExistBENo(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(), crg.getBoe());

		if (isExistBe) {
			return new ResponseEntity<>("BE No already exist.", HttpStatus.BAD_REQUEST);
		}

		int sr = 1;
		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05073", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("IMGG%06d", nextNumericNextID1);

		for (ImportGatePass i : container) {
			if (i.getGatePassId().isEmpty()) {
				Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

				if (cr == null) {
					return new ResponseEntity<>("Cargo data not found", HttpStatus.CONFLICT);
				}

				DestuffCrg destuff = destuffcrgrepo.getSingleData2(cid, bid, i.getIgmTransId(), i.getIgmNo(),
						i.getIgmLineNo());

				if (destuff == null) {
					return new ResponseEntity<>("Destuff data not found", HttpStatus.CONFLICT);
				}

				i.setGatePassId(HoldNextIdD1);
				i.setVehStatus(vehicleStatus);
				i.setSrNo(sr);
				i.setCompanyId(cid);
				i.setBranchId(bid);
				i.setSl(igm.getShippingLine());
				i.setStatus("A");
				i.setShift(crg.getShift());
				i.setGrnNo(crg.getGrnNo());
				i.setGrnDate(crg.getGrnDate());
				i.setCreatedBy(user);
				i.setCreatedDate(new Date());
				i.setApprovedBy(user);
				i.setApprovedDate(new Date());
				i.setComments(crg.getComments());
				i.setStampDuty(crg.getStampDuty());
				i.setCinNo(crg.getCinNo());
				i.setCinDate(crg.getCinDate());
				i.setDoNo(crg.getDoNo());
				i.setOocNo(crg.getOocNo());
				i.setOocDate(crg.getOocDate());
				i.setDoDate(crg.getDoDate());
				i.setDoValidityDate(crg.getDoValidityDate());
				i.setCha(crg.getCha());
				i.setCommodity(crg.getCommodity());
				i.setBlNo(crg.getBlNo());
				i.setBlDate(crg.getBlDate());
				i.setImporterName(crg.getImporterName());
				i.setBoe(crg.getBoe());
				i.setBeDate(crg.getBeDate());
				i.setCargoValue(crg.getCargoValue());
				i.setCargoDuty(crg.getCargoDuty());
				i.setLoadingStartDate(crg.getLoadingStartDate());
				i.setLoadingEndDate(crg.getLoadingEndDate());
				i.setDestuffId(destuff.getDeStuffId());
				i.setDestuffLineId(destuff.getDeStuffLineId());

				importgatepassrepo.save(i);

				int oldVal = destuff.getQtyTakenOut() == null ? 0 : destuff.getQtyTakenOut();

				destuff.setQtyTakenOut(oldVal + Integer.parseInt(i.getQtyTakenOut().toString()));

				destuffcrgrepo.save(destuff);

				BigDecimal oldQty = cr.getQtyTakenOut() == null ? BigDecimal.ZERO : cr.getQtyTakenOut();
				BigDecimal oldgw = cr.getQtyTakenOutWeight() == null ? BigDecimal.ZERO : cr.getQtyTakenOutWeight();
				BigDecimal oldArea = cr.getAreaUsed() == null ? BigDecimal.ZERO : cr.getAreaUsed();

				cr.setQtyTakenOut(oldQty.add(i.getQtyTakenOut()));
				cr.setQtyTakenOutWeight(oldgw.add(i.getGwTakenOut()));
				cr.setAreaUsed(oldArea.add(i.getAreaReleased()));

				cfigmcrgrepo.save(cr);

				int update = cfigmcnrepo.updategatePassId(cid, bid, i.getIgmTransId(), i.getIgmNo(), i.getIgmLineNo(),
						HoldNextIdD1);

				int updateInventory = importinventoryrepo.updateData(cid, bid, i.getIgmTransId(), i.getIgmNo(),
						destuff.getDeStuffId(), HoldNextIdD1);

				processnextidrepo.updateAuditTrail(cid, bid, "P05073", HoldNextIdD1, "2024");
				sr++;
			} else {
				ImportGatePass existing = importgatepassrepo.getSingleData(cid, bid, i.getIgmNo(), i.getIgmLineNo(),
						i.getContainerNo(), i.getGatePassId(), i.getSrNo());

				if (existing == null) {
					return new ResponseEntity<>("Gate pass data not found", HttpStatus.CONFLICT);
				}

				Cfigmcrg cr = cfigmcrgrepo.getData4(cid, bid, i.getIgmNo(), i.getIgmLineNo());

				if (cr == null) {
					return new ResponseEntity<>("Cargo data not found", HttpStatus.CONFLICT);
				}

				DestuffCrg destuff = destuffcrgrepo.getSingleData2(cid, bid, i.getIgmTransId(), i.getIgmNo(),
						i.getIgmLineNo());

				if (destuff == null) {
					return new ResponseEntity<>("Destuff data not found", HttpStatus.CONFLICT);
				}

				if (existing != null) {
					BigDecimal oldPack = existing.getQtyTakenOut();
					BigDecimal oldGw = existing.getGwTakenOut();
					BigDecimal oldA = existing.getAreaReleased();

					existing.setEditedBy(user);
					existing.setEditedDate(new Date());
					existing.setShift(crg.getShift());
					existing.setGrnNo(crg.getGrnNo());
					existing.setGrnDate(crg.getGrnDate());
					existing.setComments(crg.getComments());
					existing.setStampDuty(crg.getStampDuty());
					existing.setCinNo(crg.getCinNo());
					existing.setCinDate(crg.getCinDate());
					existing.setDoNo(crg.getDoNo());
					existing.setOocNo(crg.getOocNo());
					existing.setOocDate(crg.getOocDate());
					existing.setDoDate(crg.getDoDate());
					existing.setDoValidityDate(crg.getDoValidityDate());
					existing.setMtyYardLocation(crg.getMtyYardLocation());
					existing.setVehStatus(vehicleStatus);
					existing.setCha(crg.getCha());
					existing.setCommodity(crg.getCommodity());
					existing.setBlNo(crg.getBlNo());
					existing.setBlDate(crg.getBlDate());
					existing.setImporterName(crg.getImporterName());
					existing.setBoe(crg.getBoe());
					existing.setBeDate(crg.getBeDate());
					existing.setCargoValue(crg.getCargoValue());
					existing.setCargoDuty(crg.getCargoDuty());
					existing.setLoadingStartDate(crg.getLoadingStartDate());
					existing.setLoadingEndDate(crg.getLoadingEndDate());
					existing.setQtyTakenOut(i.getQtyTakenOut());
					existing.setGwTakenOut(i.getGwTakenOut());
					existing.setAreaReleased(i.getAreaReleased());

					importgatepassrepo.save(existing);

					int oldVal = (destuff.getQtyTakenOut() == null ? 0 : destuff.getQtyTakenOut())
							- Integer.parseInt(oldPack.toString());

					destuff.setQtyTakenOut(oldVal + Integer.parseInt(i.getQtyTakenOut().toString()));

					destuffcrgrepo.save(destuff);

					BigDecimal oldQty = (cr.getQtyTakenOut() == null ? BigDecimal.ZERO : cr.getQtyTakenOut())
							.subtract(oldPack);
					BigDecimal oldgw = (cr.getQtyTakenOutWeight() == null ? BigDecimal.ZERO : cr.getQtyTakenOutWeight())
							.subtract(oldGw);
					BigDecimal oldArea = (cr.getAreaUsed() == null ? BigDecimal.ZERO : cr.getAreaUsed()).subtract(oldA);

					cr.setQtyTakenOut(oldQty.add(i.getQtyTakenOut()));
					cr.setQtyTakenOutWeight(oldgw.add(i.getGwTakenOut()));
					cr.setAreaUsed(oldArea.add(i.getAreaReleased()));

					cfigmcrgrepo.save(cr);

				}
			}
		}

		if (crg.getGatePassId() == null || crg.getGatePassId().isEmpty()) {
			List<ImportGatePass> gatepass = importgatepassrepo.getData3(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(),
					HoldNextIdD1);

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}
			Map<String, Object> gatepassData = new HashMap<>();

			Party party = partyrepo.getDataByCustomerCode(cid, bid, crg.getCha());
			if (party != null) {
				gatepassData.put("cha", party.getPartyName());
			} else {
				gatepassData.put("cha", "");
			}
			gatepassData.put("gatepass", gatepass);
			return new ResponseEntity<>(gatepassData, HttpStatus.OK);
		} else {
			List<ImportGatePass> gatepass = importgatepassrepo.getData3(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(),
					crg.getGatePassId());

			if (gatepass.isEmpty()) {
				return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
			}

			Map<String, Object> gatepassData = new HashMap<>();

			Party party = partyrepo.getDataByCustomerCode(cid, bid, crg.getCha());
			if (party != null) {
				gatepassData.put("cha", party.getPartyName());
			} else {
				gatepassData.put("cha", "");
			}
			gatepassData.put("gatepass", gatepass);
			return new ResponseEntity<>(gatepassData, HttpStatus.OK);
		}

	}

	@GetMapping("/getDataByGatePassId")
	public ResponseEntity<?> getDataByGatePassId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("line") String line,
			@RequestParam("gatepassid") String gatepassid) {

		List<ImportGatePass> getData = importgatepassrepo.getData1(cid, bid, igm, line, gatepassid);

		if (getData.isEmpty()) {
			return new ResponseEntity<>("Gate Pass data not found", HttpStatus.CONFLICT);
		}

		List<CfImportGatePassVehDtl> veh = importgatepassvehrepo.getData1(cid, bid, gatepassid);

		Map<String, Object> data = new HashMap<>();

		List<VehicleDetailDTO> result = new ArrayList<>();

		if (!veh.isEmpty()) {
			veh.stream().forEach(v -> {
				VehicleDetailDTO dto = new VehicleDetailDTO();
				dto.setVehId("1");
				dto.setVehicleNo(v.getVehicleNo());
				dto.setVehicleGatePassId(v.getVehicleGatePassId());
				dto.setQtyTakenOut(v.getQtyTakenOut().intValue()); // Rounds down the value

				dto.setGwTakenOut(v.getGwTakenOut());

				result.add(dto);
			});

		}

		data.put("vehData", result);
		data.put("gatePassId", getData);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveImportGatePassVeh")
	public ResponseEntity<?> saveImportGatePassVeh(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("userId") String userId, @RequestBody Map<String, Object> vehicleDto)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		ImportGatePass crg = mapper.readValue(mapper.writeValueAsString(vehicleDto.get("crg")), ImportGatePass.class);

		if (crg == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		List<VehicleDetailDTO> vehData = mapper.readValue(mapper.writeValueAsString(vehicleDto.get("vehData")),
				new TypeReference<List<VehicleDetailDTO>>() {
				});

		if (vehData.isEmpty()) {
			return new ResponseEntity<>("Vehicle Data not found", HttpStatus.CONFLICT);
		}

		vehData.stream().forEach(v -> {
			if (v.getVehId().isEmpty()) {
				int sr = importgatepassvehrepo.getcount(cid, bid, crg.getGatePassId());

				int vehStatus = importgatepassrepo.updateVehStatus(cid, bid, crg.getGatePassId());

				CfImportGatePassVehDtl dtl = new CfImportGatePassVehDtl();
				dtl.setCompanyId(cid);
				dtl.setBranchId(bid);
				dtl.setFinYear(crg.getFinYear());
				dtl.setProfitcentreId(crg.getProfitcentreId());
				dtl.setGatePassId(crg.getGatePassId());
				dtl.setSrNo(sr + 1);
				dtl.setContainerNo("");
				dtl.setIgmNo(crg.getIgmNo());
				dtl.setIgmLineNo(crg.getIgmTransId());
				dtl.setIgmTransId(crg.getIgmTransId());
				dtl.setTransType("LCL");
				dtl.setGrossWt(crg.getGrossWt());
				dtl.setNoOfPackage(crg.getYardPackages());
				dtl.setVehicleNo(v.getVehicleNo());
				dtl.setVehicleGatePassId(v.getVehicleGatePassId());
				dtl.setQtyTakenOut(new BigDecimal(v.getQtyTakenOut()));
				dtl.setGwTakenOut(v.getGwTakenOut());
				dtl.setCreatedBy(userId);
				dtl.setCreatedDate(new Date());
				dtl.setStatus('A');

				importgatepassvehrepo.save(dtl);

				VehicleTrack track = vehicleTrackRepo.getDataByVehicleNo(cid, bid, v.getVehicleNo(),
						v.getVehicleGatePassId());

				System.out.println("track " + track);

				if (track != null) {
					track.setGatePassNo(crg.getGatePassId());
					track.setIgmNo(crg.getIgmNo());
					track.setIgmTransId(crg.getIgmTransId());

					vehicleTrackRepo.save(track);
				}

			}
		});

		List<CfImportGatePassVehDtl> veh = importgatepassvehrepo.getData1(cid, bid, crg.getGatePassId());

		List<VehicleDetailDTO> result = new ArrayList<>();

		veh.stream().forEach(v -> {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			dto.setVehId("1");
			dto.setVehicleNo(v.getVehicleNo());
			dto.setVehicleGatePassId(v.getVehicleGatePassId());
			dto.setQtyTakenOut(v.getQtyTakenOut().intValue()); // Rounds down the value

			dto.setGwTakenOut(v.getGwTakenOut());

			result.add(dto);
		});
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/getExistingVehData")
	public ResponseEntity<?> getVehData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("gatePassId") String gatepass, @RequestParam("igmtrans") String igmtrans,
			@RequestParam("igmline") String igmline) {
		List<CfImportGatePassVehDtl> veh = importgatepassvehrepo.getData(cid, bid, gatepass, igmtrans, igmline);

		List<VehicleDetailDTO> result = new ArrayList<>();

		veh.stream().forEach(v -> {
			VehicleDetailDTO dto = new VehicleDetailDTO();
			dto.setVehId("1");
			dto.setVehicleNo(v.getVehicleNo());
			dto.setVehicleGatePassId(v.getVehicleGatePassId());
			dto.setQtyTakenOut(v.getQtyTakenOut().intValue()); // Rounds down the value

			dto.setGwTakenOut(v.getGwTakenOut());

			result.add(dto);
		});
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
