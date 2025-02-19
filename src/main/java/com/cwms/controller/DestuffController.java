package com.cwms.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
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

import com.cwms.entities.CFIgm;
import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfigmcrg;
import com.cwms.entities.Destuff;
import com.cwms.entities.DestuffCrg;
import com.cwms.entities.DestuffDto;
import com.cwms.entities.EmptyInventory;
import com.cwms.entities.ExamCrg;
import com.cwms.entities.Impexpgrid;
import com.cwms.entities.ImportInventory;
import com.cwms.repository.CfIgmCnRepository;
import com.cwms.repository.CfIgmCrgRepository;
import com.cwms.repository.CfIgmRepository;
import com.cwms.repository.DestuffCrgRepository;
import com.cwms.repository.DestuffRepository;
import com.cwms.repository.EmptyInventoryRepo;
import com.cwms.repository.ExamCargoRepository;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.ImportInventoryRepository;
import com.cwms.repository.ProcessNextIdRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("/destuff")
@RestController
@CrossOrigin("*")
public class DestuffController {

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private CfIgmCrgRepository cfigmcrgrepo;

	@Autowired
	private CfIgmCnRepository cfigmcnrepo;

	@Autowired
	private DestuffRepository destuffRepo;

	@Autowired
	private DestuffCrgRepository destuffcrgrepo;

	@Autowired
	private CfIgmRepository cfigmrepo;

	@Autowired
	private ExamCargoRepository examcrgrepo;

	@Autowired
	private Impexpgridrepo impexpgridrepo;

	@Autowired
	private ImportInventoryRepository importinventoryrepo;

	@Autowired
	private EmptyInventoryRepo emptyinventoryrepo;
	
	

	@PostMapping("/saveData")
	public ResponseEntity<?> saveData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("flag") String flag, @RequestParam("haz") String haz,
			@RequestBody DestuffDto data) {

		Destuff destuff = data.getDestuff();
		List<DestuffCrg> crg = data.getCrg();
		List<Cfigmcn> con = cfigmcnrepo.getContainers(cid, bid, destuff.getIgmNo(), destuff.getIgmTransId(),
				destuff.getContainerNo());

		if (con.isEmpty()) {
			return new ResponseEntity<>("Container data not found.", HttpStatus.BAD_REQUEST);
		}
		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo());

		if (igm == null) {
			return new ResponseEntity<>("Igm data not found.", HttpStatus.BAD_REQUEST);
		}

		if ("add".equals(flag)) {

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05066", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("DSAR%06d", nextNumericNextID1);

			BigDecimal totalNop = BigDecimal.ZERO;
			BigDecimal totalArea = BigDecimal.ZERO;
			int totalNop1 = 0;
			if (!crg.isEmpty()) {
				int sr = 1;
				for (DestuffCrg c : crg) {
					c.setCompanyId(cid);
					c.setBranchId(bid);
					c.setCreatedBy(user);
					c.setCreatedDate(new Date());
					c.setYardBlock(destuff.getYardBlock());
					c.setYardLocation(destuff.getYardLocation());
					c.setBlockCellNo(destuff.getBlockCellNo());
					c.setStatus("A");
					c.setApprovedBy(user);
					c.setApprovedDate(new Date());
					c.setDeStuffId(HoldNextIdD1);
					c.setDeStuffDate(new Date());
					c.setDeStuffLineId(String.valueOf(sr));
					c.setDestuffType(destuff.getDestuffType());
					c.setOnAccountOf(destuff.getOnAccountOf());
					c.setOldActualNoOfPackages(c.getActualNoOfPackages());
					c.setOldYardPackages(c.getYardPackages());
					c.setOldActualWeight(c.getActualWeight());
					c.setLength((c.getLength() == null) ? BigDecimal.ZERO
							: c.getLength());
					c.setHeight((c.getHeight() == null) ? BigDecimal.ZERO
							: c.getHeight());
					c.setWeight((c.getWeight() == null) ? BigDecimal.ZERO
							: c.getWeight());

//					if (c.getNoOfPackages().compareTo(c.getActualNoOfPackages()) > 0) {
//						c.setGainLossPackages(String.valueOf(c.getNoOfPackages() - c.getActualNoOfPackages()));
//						c.setShortagePackages(new BigDecimal(c.getNoOfPackages() - c.getActualNoOfPackages()));
//					} else if (c.getNoOfPackages().compareTo(c.getActualNoOfPackages()) < 0) {
//						c.setGainLossPackages(String.valueOf(c.getActualNoOfPackages() - c.getNoOfPackages()));
//						c.setExcessPackages(new BigDecimal(c.getActualNoOfPackages() - c.getNoOfPackages()));
//					} else {
//						c.setGainLossPackages("0");
//						c.setShortagePackages(BigDecimal.ZERO);
//						c.setExcessPackages(BigDecimal.ZERO);
//					}

					totalNop = totalNop.add(c.getYardPackages());
					totalNop1 = totalNop1 + c.getActualNoOfPackages();
					totalArea = totalArea.add(c.getAreaOccupied());

					destuffcrgrepo.save(c);

//					Cfigmcrg cr = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());
//					
//					if(cr != null) {
//					     if("LCL".equals(destuff.getTransType())) {
//					    	 BigDecimal actualGrossWeight = cr.getActualGrossWeight() != null 
//						             ? cr.getActualGrossWeight() 
//						             : BigDecimal.ZERO;
//
//						         BigDecimal grossWeight = c.getActualWeight() != null 
//						             ? c.getActualWeight() 
//						             : BigDecimal.ZERO;
//
//						         cr.setActualGrossWeight(actualGrossWeight.add(grossWeight));
//							cfigmcrgrepo.save(cr);
//					     }
//					}

					Cfigmcrg cg = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());

					if (cg != null) {
						cg.setCommodityDescription(c.getCommodityDescription());
						cg.setMarksOfNumbers(c.getMarksOfNumbers());
						cg.setNoOfDestuffContainers(cg.getNoOfDestuffContainers() + 1);

						if ("LCL".equals(destuff.getTransType())) {
							BigDecimal actualGrossWeight = cg.getActualGrossWeight() != null ? cg.getActualGrossWeight()
									: BigDecimal.ZERO;

							BigDecimal grossWeight = c.getActualWeight() != null ? c.getActualWeight()
									: BigDecimal.ZERO;

							cg.setActualGrossWeight(actualGrossWeight.add(grossWeight));

						}

						cfigmcrgrepo.save(cg);
					}
					Impexpgrid grid = new Impexpgrid();
					grid.setCompanyId(cid);
					grid.setBranchId(bid);
					grid.setCreatedBy(user);
					grid.setCreatedDate(new Date());
					grid.setApprovedBy(user);
					grid.setApprovedDate(new Date());
					grid.setStatus("A");
					grid.setAreaReleased(BigDecimal.ZERO);
					grid.setCellArea(c.getAreaOccupied());
					grid.setCellAreaAllocated(c.getAreaOccupied());
					grid.setCellAreaUsed(c.getAreaOccupied());
					Date r = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(r);
					int year = cal.get(Calendar.YEAR);
					grid.setFinYear(String.valueOf(year));
					grid.setLineNo(sr);
					grid.setProcessTransId(HoldNextIdD1);
					grid.setQtyTakenOut(BigDecimal.ZERO);
					grid.setStuffReqQty(0);
					grid.setSubSrNo(sr);
					grid.setTransType("IMP");
					grid.setYardBlock(destuff.getYardBlock());
					grid.setYardLocation(destuff.getYardLocation());
					grid.setBlockCellNo(destuff.getBlockCellNo());
					grid.setYardPackages(c.getYardPackages().intValue());

					impexpgridrepo.save(grid);

					sr++;
				}

			}

			String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05067", "2024");

			String[] parts = lastValue.split("/");
			String baseId = parts[0];
			String financialYear = parts[1];

			// Increment the base ID
			String newBaseId = incrementBaseId(baseId);

			// Get the current financial year
			String currentFinancialYear = getCurrentFinancialYear();

			String newId = newBaseId + "/" + currentFinancialYear;
			destuff.setCompanyId(cid);
			destuff.setBranchId(bid);
			destuff.setStatus("A");
			destuff.setCreatedBy(user);
			destuff.setCreatedDate(new Date());
			destuff.setHaz(haz);
			destuff.setApprovedBy(user);
			destuff.setApprovedDate(new Date());
			destuff.setProfitcentreId(con.get(0).getProfitcentreId());
			destuff.setDeStuffDate(new Date());
			destuff.setDeStuffId(HoldNextIdD1);
			destuff.setYardPackages(totalNop);
			destuff.setWorkOrderNo(newId);
			destuff.setAreaOccupied(totalArea);
			final int totalNopFinal = totalNop1;

			EmptyInventory empinv = new EmptyInventory();
			empinv.setBranchId(bid);
			empinv.setCha(con.get(0).getCha());
			empinv.setCompanyId(cid);
			empinv.setContainerNo(con.get(0).getContainerNo());
			empinv.setContainerSize(con.get(0).getContainerSize());
			empinv.setContainerType(con.get(0).getContainerType());
			empinv.setDeStuffId(HoldNextIdD1);
			empinv.setDocRefNo(con.get(0).getIgmNo());
			empinv.setEmptyDate(new Date());
			empinv.setErpDocRefNo(con.get(0).getIgmTransId());
			empinv.setFinYear(con.get(0).getFinYear());
			empinv.setGateInDate(con.get(0).getGateInDate());
			empinv.setGateInId(con.get(0).getGateInId());
			empinv.setIsoCode(con.get(0).getIso());
			empinv.setMovementCode("DEVC");
			empinv.setOnAccountOf(destuff.getOnAccountOf());
			empinv.setProfitcentreId(con.get(0).getProfitcentreId());
			empinv.setSa(igm.getShippingAgent());
			empinv.setSl(igm.getShippingLine());
			empinv.setSubDocRefNo(con.get(0).getIgmLineNo());
			empinv.setStatus("A");
			empinv.setCreatedBy(user);
			empinv.setCreatedDate(new Date());

			emptyinventoryrepo.save(empinv);

			con.stream().forEach(c -> {

				DestuffCrg data1 = crg.stream().filter(d -> d.getIgmLineNo().equals(c.getIgmLineNo())) // Match igmLineNo
						.findFirst() // Get the first matching element
						.orElse(null); // Handle cases where no match is found

				if (data1 != null) {
					c.setTypeOfCargo(data1.getTypeOfCargo());
					c.setOdcType(data1.getOdcType());
					c.setLength((data1.getLength() == null) ? BigDecimal.ZERO
							: data1.getLength());
					c.setHeight((data1.getHeight() == null) ? BigDecimal.ZERO
							: data1.getHeight());
					c.setWeight((data1.getWeight() == null) ? BigDecimal.ZERO
							: data1.getWeight());
				}
				c.setHaz(haz);
				c.setDeStuffDate(new Date());
				c.setDeStuffId(HoldNextIdD1);
				c.setDestuffStatus("Y");
				c.setDestuffWoCreatedBy(user);
				c.setDestuffWoDate(new Date());
				// c.setPackagesDeStuffed(totalNopFinal);
				c.setDestuffWoTransId(newId);

				if ("LCL".equals(c.getContainerStatus())) {
					c.setOocDate(data.getOocDate());
					c.setOocNo(data.getOocNo());
					c.setDoNo(data.getDoNo());
					c.setDoDate(data.getDoDate());
					c.setDoValidityDate(data.getDoValidityDate());
				}

				cfigmcnrepo.save(c);

				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, c.getIgmTransId(), c.getIgmNo(),
						c.getContainerNo(), c.getGateInId());

				if (existingInv != null) {
					existingInv.setDeStuffDate(new Date());
					existingInv.setDeStuffId(HoldNextIdD1);

					importinventoryrepo.save(existingInv);
				}
				processnextidrepo.updateAuditTrail(cid, bid, "P05067", newId, "2024");
			});
			destuff.setMtyStatus("N");
			destuffRepo.save(destuff);

			processnextidrepo.updateAuditTrail(cid, bid, "P05066", HoldNextIdD1, "2024");

			Boolean checkStatus = destuffRepo.checkMtyStatus(cid, bid, HoldNextIdD1);

			if (checkStatus) {
				int updateCheck = destuffRepo.updateMtyStatus(cid, bid, HoldNextIdD1);
			}

			DestuffDto dto = new DestuffDto();
			List<DestuffCrg> crgData = destuffcrgrepo.getData1(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo(),
					HoldNextIdD1);
			dto.setCrg(crgData);

			Destuff d = destuffRepo.getData1(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo(),
					destuff.getIgmLineNo(), destuff.getDeStuffId());
			dto.setDestuff(d);
			dto.setDoDate(data.getDoDate());
			dto.setDoNo(data.getDoNo());
			dto.setDoValidityDate(data.getDoValidityDate());
			dto.setOocDate(data.getOocDate());
			dto.setOocNo(data.getOocNo());

			return new ResponseEntity<>(dto, HttpStatus.OK);

		} else {
			BigDecimal totalNop = BigDecimal.ZERO;
			BigDecimal totalArea = BigDecimal.ZERO;
			int totalNop1 = 0;
			Destuff d = destuffRepo.getData(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo(),
					destuff.getIgmLineNo(), destuff.getDeStuffId());

			if (d == null) {
				return new ResponseEntity<>("Destuff data not found.", HttpStatus.BAD_REQUEST);
			}
			for (DestuffCrg c : crg) {
				DestuffCrg existing = destuffcrgrepo.getSingleData(cid, bid, c.getIgmTransId(), c.getIgmNo(),
						c.getIgmLineNo(), c.getDeStuffId(), c.getDeStuffLineId());

				if (existing == null) {
					return new ResponseEntity<>("DestuffCrg data not found.", HttpStatus.BAD_REQUEST);
				}
				existing.setActualNoOfPackages(existing.getActualNoOfPackages() + (c.getActualNoOfPackages() == null ? 0 : c.getActualNoOfPackages()));
				existing.setActualWeight(existing.getActualWeight().add(c.getActualWeight() == null ? BigDecimal.ZERO : c.getActualWeight()));
				existing.setCommodityDescription(c.getCommodityDescription());
				existing.setWarehouseLocation(c.getWarehouseLocation());
				existing.setCargoType(c.getCargoType());
				existing.setMarksOfNumbers(c.getMarksOfNumbers());
				existing.setDamagedPackages(c.getDamagedPackages());
				existing.setAreaOccupied(c.getAreaOccupied());
				existing.setGrossWeight(c.getGrossWeight());
				existing.setBlGainLoss(c.getBlGainLoss());
				existing.setGainLossPackages(c.getGainLossPackages());
				existing.setShortagePackages(c.getShortagePackages());
				existing.setExcessPackages(c.getExcessPackages());
				existing.setYardPackages(existing.getYardPackages().add((c.getYardPackages() == null ? BigDecimal.ZERO : c.getYardPackages())));
				existing.setComments(c.getComments());
				existing.setEditedBy(user);
				existing.setEditedDate(new Date());
				existing.setOnAccountOf(destuff.getOnAccountOf());
				existing.setOldActualNoOfPackages(existing.getOldActualNoOfPackages() + (c.getActualNoOfPackages() == null ? 0 : c.getActualNoOfPackages()));
				existing.setOldYardPackages(existing.getOldYardPackages().add((c.getYardPackages() == null ? BigDecimal.ZERO : c.getYardPackages())));
				existing.setOldActualWeight((existing.getOldActualWeight() == null ? BigDecimal.ZERO : existing.getOldActualWeight()).add((c.getActualWeight() == null ? BigDecimal.ZERO : c.getActualWeight())));
				existing.setLength((c.getLength() == null) ? BigDecimal.ZERO
						: c.getLength());
				existing.setHeight((c.getHeight() == null) ? BigDecimal.ZERO
						: c.getHeight());
				existing.setWeight((c.getWeight() == null) ? BigDecimal.ZERO
						: c.getWeight());
				existing.setTypeOfCargo(c.getTypeOfCargo());
				existing.setOdcType(c.getOdcType());
				d.setYardPackages(d.getYardPackages().add((c.getYardPackages() == null ? BigDecimal.ZERO : c.getYardPackages())));
				// d.setAreaOccupied(d.getAreaOccupied().add(c.getAreaOccupied()));
				totalArea = totalArea.add((c.getAreaOccupied() == null ? BigDecimal.ZERO : c.getAreaOccupied()));

//				if (c.getNoOfPackages().compareTo(c.getActualNoOfPackages()) == 1) {
//
//					int a = c.getNoOfPackages() - c.getActualNoOfPackages();
//					existing.setGainLossPackages(String.valueOf(a));
//					existing.setShortagePackages(new BigDecimal(a));
//				} else if (c.getNoOfPackages().compareTo(c.getActualNoOfPackages()) == -1) {
//					int a = c.getActualNoOfPackages() - c.getNoOfPackages();
//					existing.setGainLossPackages(String.valueOf(a));
//					existing.setExcessPackages(new BigDecimal(a));
//				} else {
//					existing.setGainLossPackages("0");
//					existing.setShortagePackages(BigDecimal.ZERO);
//					existing.setExcessPackages(BigDecimal.ZERO);
//				}

				Cfigmcrg cr = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());

//					if(cr != null) {
//					     if("LCL".equals(destuff.getTransType())) {
//					    	 BigDecimal actualGrossWeight = cr.getActualGrossWeight() != null 
//						             ? cr.getActualGrossWeight() 
//						             : BigDecimal.ZERO;
//
//						         BigDecimal grossWeight = c.getActualWeight() != null 
//						             ? c.getActualWeight() 
//						             : BigDecimal.ZERO;
//
//						         cr.setActualGrossWeight((actualGrossWeight.add(grossWeight)).subtract(existing.getActualWeight()));
//							cfigmcrgrepo.save(cr);
//					     }
//					}
				destuffcrgrepo.save(existing);

				Cfigmcrg cg = cfigmcrgrepo.getData1(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo());

				if (cg != null) {
					cg.setCommodityDescription(c.getCommodityDescription());
					cg.setMarksOfNumbers(c.getMarksOfNumbers());

					if ("LCL".equals(destuff.getTransType())) {
						BigDecimal actualGrossWeight = cg.getActualGrossWeight() != null ? cg.getActualGrossWeight()
								: BigDecimal.ZERO;

						BigDecimal grossWeight = c.getActualWeight() != null ? c.getActualWeight() : BigDecimal.ZERO;

						cg.setActualGrossWeight(
								(actualGrossWeight.add(grossWeight)).subtract(existing.getActualWeight()));

					}

					cfigmcrgrepo.save(cg);
				}

				Impexpgrid existingGrid = impexpgridrepo.getDataByIdAndLineId(cid, bid, c.getDeStuffId(),
						c.getDeStuffLineId());

				if (existingGrid != null) {

					existingGrid.setCellArea(c.getAreaOccupied());
					existingGrid.setCellAreaAllocated(c.getAreaOccupied());
					existingGrid.setCellAreaUsed(c.getAreaOccupied());
					existingGrid.setYardPackages(c.getYardPackages().intValue());

					impexpgridrepo.save(existingGrid);
				}

			}

			BigDecimal oldGw = BigDecimal.ZERO;
			d.setHaz(haz);
			d.setEditedBy(user);
			d.setEditedDate(new Date());
//			d.setYardPackages(totalNop);
			d.setAreaOccupied(totalArea);
			d.setDestuffFromDate(destuff.getDestuffFromDate());
			d.setDestuffToDate(destuff.getDestuffToDate());
			d.setOnAccountOf(destuff.getOnAccountOf());
//			d.setGrossWeight(destuff.getGrossWeight());
			d.setShift(destuff.getShift());
			destuffRepo.save(d);

			Cfigmcrg cr = cfigmcrgrepo.getData1(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo(),
					destuff.getIgmLineNo());

			if (cr != null) {
				if ("LCL".equals(destuff.getTransType())) {
					BigDecimal actualGrossWeight = cr.getActualGrossWeight() != null ? cr.getActualGrossWeight()
							: BigDecimal.ZERO;

					BigDecimal grossWeight = destuff.getGrossWeight() != null ? destuff.getGrossWeight()
							: BigDecimal.ZERO;

					cr.setActualGrossWeight((actualGrossWeight.add(grossWeight)).subtract(oldGw));
					cfigmcrgrepo.save(cr);
				}
			}
			final int totalNopFinal = totalNop1;
			con.stream().forEach(c -> {
				DestuffCrg data1 = crg.stream().filter(d1 -> d1.getIgmLineNo().equals(c.getIgmLineNo())) // Match igmLineNo
						.findFirst() // Get the first matching element
						.orElse(null); // Handle cases where no match is found

				if (data1 != null) {
					c.setTypeOfCargo(data1.getTypeOfCargo());
					c.setOdcType(data1.getOdcType());
					c.setLength((data1.getLength() == null) ? BigDecimal.ZERO
							: data1.getLength());
					c.setHeight((data1.getHeight() == null) ? BigDecimal.ZERO
							: data1.getHeight());
					c.setWeight((data1.getWeight() == null) ? BigDecimal.ZERO
							: data1.getWeight());
				}
				c.setHaz(haz);
				if ("LCL".equals(c.getContainerStatus())) {
					c.setOocDate(data.getOocDate());
					c.setOocNo(data.getOocNo());
					c.setDoNo(data.getDoNo());
					c.setDoDate(data.getDoDate());
					c.setDoValidityDate(data.getDoValidityDate());

				}
				// c.setPackagesDeStuffed(totalNopFinal);
				cfigmcnrepo.save(c);
			});

			Boolean checkStatus = destuffRepo.checkMtyStatus(cid, bid, destuff.getDeStuffId());

			if (checkStatus) {
				int updateCheck = destuffRepo.updateMtyStatus(cid, bid, destuff.getDeStuffId());
			}

			DestuffDto dto = new DestuffDto();
			List<DestuffCrg> crgData = destuffcrgrepo.getData1(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo(),
					d.getDeStuffId());
			dto.setCrg(crgData);
			Destuff d1 = destuffRepo.getData1(cid, bid, destuff.getIgmTransId(), destuff.getIgmNo(),
					destuff.getIgmLineNo(), destuff.getDeStuffId());
			dto.setDestuff(d1);
			dto.setDoDate(data.getDoDate());
			dto.setDoNo(data.getDoNo());
			dto.setDoValidityDate(data.getDoValidityDate());
			dto.setOocDate(data.getOocDate());
			dto.setOocNo(data.getOocNo());
			return new ResponseEntity<>(dto, HttpStatus.OK);

		}

	}


	@GetMapping("/searchData")
	public List<Object[]> searchData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "search", required = false) String search) {
		return destuffRepo.searchData(cid, bid, search);
	}

	@GetMapping("/selectSearchedData")
	public ResponseEntity<?> selectSearchedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmTransId") String igmtrans,
			@RequestParam("line") String line, @RequestParam("destuffid") String destuffid) {
		DestuffDto dto = new DestuffDto();
		List<DestuffCrg> crgData = destuffcrgrepo.getData1(cid, bid, igmtrans, igm, destuffid);

		if (crgData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
		}

		dto.setCrg(crgData);

		Destuff d1 = destuffRepo.getData2(cid, bid, igmtrans, igm, destuffid);

		if (d1 == null) {
			return new ResponseEntity<>("Data not found", HttpStatus.BAD_REQUEST);
		}
		dto.setDestuff(d1);

		List<Cfigmcn> con = cfigmcnrepo.getContainers(cid, bid, d1.getIgmNo(), d1.getIgmTransId(), d1.getContainerNo());

		dto.setDoDate(con.get(0).getDoDate());
		dto.setDoNo(con.get(0).getDoNo());
		dto.setDoValidityDate(con.get(0).getDoValidityDate());
		dto.setOocDate(con.get(0).getOocDate());
		dto.setOocNo(con.get(0).getOocNo());

		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@GetMapping("/getDataForDestuffItemWise")
	public ResponseEntity<?> getDataForDestuffItemWise(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igmNo") String igmNo, @RequestParam("itemNo") String itemNo) {
		Object[] igmcrg = cfigmcrgrepo.getDataByIgmAndLineForDestuff(cid, bid, igmNo, itemNo);

		if (igmcrg == null) {
			return new ResponseEntity<>("Data not found.", HttpStatus.BAD_REQUEST);
		}

		List<Cfigmcn> container = cfigmcnrepo.getDataForDestuff(cid, bid, igmNo, itemNo);

		if (container.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.BAD_REQUEST);
		}

		Map<String, Object> data = new HashMap<>();
		data.put("crg", igmcrg);
		data.put("container", container);

		return new ResponseEntity<>(data, HttpStatus.OK);

	}

	@PostMapping("/saveItemWiseDestuff")
	public ResponseEntity<?> saveItenWiseDestuff(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestParam("shift") String shift, @RequestParam("haz") String haz,
			@RequestBody Map<String, Object> data) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);

		// Convert JSON string to Cfigmcrg and List<Cfigmcn> objects
		Cfigmcrg crg1 = mapper.readValue(mapper.writeValueAsString(data.get("crg")), Cfigmcrg.class);

		if (crg1 == null) {
			return new ResponseEntity<>("Commodity data not found", HttpStatus.BAD_GATEWAY);
		}

		Cfigmcrg crg = cfigmcrgrepo.getData1(cid, bid, crg1.getIgmTransId(), crg1.getIgmNo(), crg1.getIgmLineNo());

		if (crg == null) {
			return new ResponseEntity<>("Commodity data not found", HttpStatus.BAD_GATEWAY);
		}

		CFIgm igm = cfigmrepo.getDataByIgmNoAndtrans(cid, bid, crg.getIgmTransId(), crg.getIgmNo());

		List<Cfigmcn> container = mapper.readValue(mapper.writeValueAsString(data.get("container")),
				new TypeReference<List<Cfigmcn>>() {
				});

		if (container.isEmpty()) {
			return new ResponseEntity<>("Container data not found", HttpStatus.BAD_GATEWAY);
		}

		for (Cfigmcn c : container) {
			Cfigmcn existingCn = cfigmcnrepo.getSingleData(cid, bid, c.getIgmTransId(), c.getProfitcentreId(),
					c.getIgmNo(), c.getIgmLineNo(), c.getContainerNo());

			if (existingCn == null) {
				return new ResponseEntity<>("Container data not found", HttpStatus.BAD_GATEWAY);
			}

			if ("Y".equals(existingCn.getDestuffStatus())) {
				Destuff d = destuffRepo.getData(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo(),
						c.getDeStuffId());

				if (d == null) {
					return new ResponseEntity<>("Destuff data not found.", HttpStatus.BAD_REQUEST);
				}
				d.setEditedBy(user);
				d.setEditedDate(new Date());
				d.setShift(shift);
				d.setHaz(haz);

				BigDecimal checkMtyVal = ((c.getActualNoOfPackages().add(c.getOldActualNoOfPackages()))
						.add(c.getDamagedNoOfPackages())).add(c.getGainOrLossPkgs());

				if ((checkMtyVal.compareTo(new BigDecimal(c.getNoOfPackages())) == 0
						|| checkMtyVal.compareTo(new BigDecimal(c.getNoOfPackages())) == 1)
						&& !"Y".equals(d.getMtyStatus())) {
					d.setMtyStatus("Y");
					d.setMtyDate(new Date());

				} else {
					if (!"Y".equals(d.getMtyStatus())) {
						d.setMtyStatus("N");
					}
				}

				destuffRepo.save(d);

				DestuffCrg existingCrg = destuffcrgrepo.getSingleData1(cid, bid, c.getIgmTransId(), c.getIgmNo(),
						c.getIgmLineNo(), c.getDeStuffId());

				if (existingCrg == null) {
					return new ResponseEntity<>("DestuffCrg data not found.", HttpStatus.BAD_REQUEST);
				}
				existingCrg.setEditedBy(user);
				existingCrg.setEditedDate(new Date());
				// Update actual number of packages
				existingCrg.setActualNoOfPackages(existingCrg.getActualNoOfPackages()
						+ new BigDecimal(c.getActualNoOfPackages().toString()).intValue());

				// Update damaged packages
				existingCrg.setDamagedPackages(existingCrg.getDamagedPackages()
						+ new BigDecimal(c.getDamagedNoOfPackages().toString()).intValue());

				existingCrg.setOldActualNoOfPackages(existingCrg.getOldActualNoOfPackages()
						+ new BigDecimal(c.getActualNoOfPackages().toString()).intValue());

				// Update damaged packages
				existingCrg.setOldYardPackages(existingCrg.getOldYardPackages().add(c.getActualNoOfPackages()));

				existingCrg.setTypeOfCargo(crg1.getTypeOfCargo());
				existingCrg.setOdcType(crg1.getOdcType());
				existingCrg.setLength((crg1.getLength() == null || crg1.getLength().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getLength()));
				existingCrg.setHeight((crg1.getHeight() == null || crg1.getHeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getHeight()));
				existingCrg.setWeight((crg1.getWeight() == null || crg1.getWeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getWeight()));

				destuffcrgrepo.save(existingCrg);

				existingCn.setActualNoOfPackages(existingCn.getActualNoOfPackages().add(c.getActualNoOfPackages()));
				existingCn.setDamagedNoOfPackages(c.getDamagedNoOfPackages());
				existingCn
						.setOldActualNoOfPackages(existingCn.getOldActualNoOfPackages().add(c.getActualNoOfPackages()));
				existingCn.setShift(shift);
				existingCn.setHaz(haz);
				existingCn.setGainOrLossPkgs(c.getGainOrLossPkgs());
				existingCn.setTypeOfCargo(crg1.getTypeOfCargo());
				existingCn.setOdcType(crg1.getOdcType());
				existingCn.setLength((crg1.getLength() == null || crg1.getLength().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getLength()));
				existingCn.setHeight((crg1.getHeight() == null || crg1.getHeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getHeight()));
				existingCn.setWeight((crg1.getWeight() == null || crg1.getWeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getWeight()));
				// existingCn.setPackagesDeStuffed(new
				// BigDecimal(c.getActualNoOfPackages().toString()).intValue());
//				String lastValue1 = processnextidrepo.findAuditTrail(cid, bid, "P05069", "2024");
//
//				String[] parts1 = lastValue1.split("/");
//				String baseId1 = parts1[0];
//				String financialYear1 = parts1[1];
//
//				// Increment the base ID
//				String newBaseId1 = incrementBaseId(baseId1);
//
//				// Get the current financial year
//				String currentFinancialYear1 = getCurrentFinancialYear();
//
//				// Construct the new ID
//				String newId1 = newBaseId1 + "/" + currentFinancialYear1;
//				processnextidrepo.updateAuditTrail(cid, bid, "P05069", newId1, "2024");
				cfigmcnrepo.save(existingCn);

			} else {

				String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05066", "2024");

				int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

				int nextNumericNextID1 = lastNextNumericId1 + 1;

				String HoldNextIdD1 = String.format("DSAR%06d", nextNumericNextID1);

				DestuffCrg dcrg = new DestuffCrg();
				dcrg.setCompanyId(cid);
				dcrg.setBranchId(bid);
				dcrg.setFinYear(c.getFinYear());
				dcrg.setActualNoOfPackages(new BigDecimal(c.getActualNoOfPackages().toString()).intValue());
				dcrg.setActualWeight(BigDecimal.ZERO);
				dcrg.setApprovedBy(user);
				dcrg.setApprovedDate(new Date());
				dcrg.setAreaOccupied(BigDecimal.ZERO);
				dcrg.setBlockCellNo(c.getBlockCellNo());
				dcrg.setCargoType(c.getTypeOfCargo());
				dcrg.setCommodityDescription(crg.getCommodityDescription());
				dcrg.setCreatedBy(user);
				dcrg.setCreatedDate(new Date());
				if (c.getDamagedNoOfPackages() != null && !c.getDamagedNoOfPackages().toString().trim().isEmpty()) {
					dcrg.setDamagedPackages(new BigDecimal(c.getDamagedNoOfPackages().toString()).intValue());
				} else {
					dcrg.setDamagedPackages(0); // Set default value, e.g., 0, when null or empty
				}

				dcrg.setDestuffCharges(BigDecimal.ZERO);
				dcrg.setDeStuffDate(new Date());
				dcrg.setDeStuffId(HoldNextIdD1);
				dcrg.setDeStuffLineId("1");
				dcrg.setGainLossPackages(c.getGainOrLossPkgs().toString());
				dcrg.setGrossWeight(crg.getGrossWeight());
				dcrg.setIgmLineNo(c.getIgmLineNo());
				dcrg.setIgmNo(c.getIgmNo());
				dcrg.setIgmTransId(c.getIgmTransId());
				dcrg.setImporterAddress1(crg.getImporterAddress1());
				dcrg.setImporterAddress2(crg.getImporterAddress2());
				dcrg.setImporterAddress3(crg.getImporterAddress3());
				dcrg.setImporterName(crg.getImporterName());
				dcrg.setNoOfPackages(c.getNoOfPackages());
				dcrg.setProfitcentreId(c.getProfitcentreId());
				dcrg.setQtyTakenOut(0);
				dcrg.setShortagePackages(BigDecimal.ZERO);
				dcrg.setStatus("A");
				dcrg.setTypeOfPackages(crg.getTypeOfPackage());
				dcrg.setYardBlock(c.getYardBlock());
				dcrg.setYardLocation(c.getYardLocation());
				dcrg.setYardPackages(c.getActualNoOfPackages());
				dcrg.setOldActualNoOfPackages(new BigDecimal(c.getActualNoOfPackages().toString()).intValue());
				dcrg.setOldYardPackages(c.getActualNoOfPackages());
				dcrg.setTypeOfCargo(crg1.getTypeOfCargo());
				dcrg.setOdcType(crg1.getOdcType());
				dcrg.setLength((crg1.getLength() == null || crg1.getLength().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getLength()));
				dcrg.setHeight((crg1.getHeight() == null || crg1.getHeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getHeight()));
				dcrg.setWeight((crg1.getWeight() == null || crg1.getWeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getWeight()));

				destuffcrgrepo.save(dcrg);
				String lastValue = processnextidrepo.findAuditTrail(cid, bid, "P05067", "2024");

				String[] parts = lastValue.split("/");
				String baseId = parts[0];
				String financialYear = parts[1];

				// Increment the base ID
				String newBaseId = incrementBaseId(baseId);

				// Get the current financial year
				String currentFinancialYear = getCurrentFinancialYear();

				// Construct the new ID
				String newId = newBaseId + "/" + currentFinancialYear;

				Destuff cn = new Destuff();
				cn.setApprovedBy(user);
				cn.setApprovedDate(new Date());
				cn.setAreaOccupied(BigDecimal.ZERO);
				cn.setBlockCellNo(c.getBlockCellNo());
				cn.setBlockCellNo1(c.getBlockCellNo1());
				cn.setBranchId(bid);
				cn.setCompanyId(cid);
				cn.setContainerNo(c.getContainerNo());
				cn.setContainerSealNo(c.getContainerSealNo());
				cn.setContainerSize(c.getContainerSize());
				cn.setWorkOrderNo(newId);
				cn.setContainerStatus(c.getContainerStatus());
				cn.setContainerType(c.getContainerType());
				cn.setCreatedBy(user);
				cn.setCreatedDate(new Date());
				cn.setCustomSealNo(c.getCustomsSealNo());
				cn.setDeStuffDate(new Date());
				cn.setDeStuffId(HoldNextIdD1);
				cn.setFinYear(c.getFinYear());
				cn.setGateInDate(c.getGateInDate());
				cn.setGateInId(c.getGateInId());
				cn.setGrossWeight(c.getGrossWt());
				cn.setHaz(haz);
				cn.setIgmDate(igm.getIgmDate());
				cn.setIgmLineNo(c.getIgmLineNo());
				cn.setIgmNo(c.getIgmNo());
				cn.setIgmTransId(c.getIgmTransId());
				cn.setProfitcentreId(c.getProfitcentreId());
				cn.setShift(shift);
				cn.setShippingAgent(igm.getShippingAgent());
				cn.setShippingLine(igm.getShippingLine());
				cn.setStatus("A");
				cn.setViaNo(igm.getViaNo());
				cn.setYardBlock(c.getYardBlock());
				cn.setYardBlock1(c.getYardBlock1());
				cn.setYardLocation(c.getYardLocation());
				cn.setYardLocation1(c.getYardLocation1());
				cn.setTransType("FCL");
				cn.setContainerStatus("FCL");
				cn.setYardPackages(c.getActualNoOfPackages());

				BigDecimal checkMtyVal = (c.getActualNoOfPackages().add(c.getDamagedNoOfPackages()))
						.add(c.getGainOrLossPkgs());

				if (checkMtyVal.compareTo(new BigDecimal(c.getNoOfPackages())) == 0
						|| checkMtyVal.compareTo(new BigDecimal(c.getNoOfPackages())) == 1) {
					cn.setMtyStatus("Y");
					cn.setMtyDate(new Date());

				} else {
					cn.setMtyStatus("N");
				}

				destuffRepo.save(cn);

				existingCn.setActualNoOfPackages(c.getActualNoOfPackages());

				existingCn.setDamagedNoOfPackages(c.getDamagedNoOfPackages());
				existingCn.setDeStuffDate(new Date());
				existingCn.setDeStuffId(HoldNextIdD1);
				existingCn.setDestuffStatus("Y");
				existingCn.setDestuffWoCreatedBy(user);
				existingCn.setDestuffWoDate(new Date());
				existingCn.setDestuffWoTransId(newId);
				existingCn.setPackagesDeStuffed(new BigDecimal(c.getActualNoOfPackages().toString()).intValue());
				existingCn.setShift(shift);
				existingCn.setHaz(haz);
				existingCn.setGainOrLossPkgs(c.getGainOrLossPkgs());
				existingCn.setOldActualNoOfPackages(c.getActualNoOfPackages());
				existingCn.setTypeOfCargo(crg1.getTypeOfCargo());
				existingCn.setOdcType(crg1.getOdcType());
				existingCn.setLength((crg1.getLength() == null || crg1.getLength().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getLength()));
				existingCn.setHeight((crg1.getHeight() == null || crg1.getHeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getHeight()));
				existingCn.setWeight((crg1.getWeight() == null || crg1.getWeight().isEmpty()) ? BigDecimal.ZERO
						: new BigDecimal(crg1.getWeight()));

				cfigmcnrepo.save(existingCn);

				EmptyInventory empinv = new EmptyInventory();
				empinv.setBranchId(bid);
				empinv.setCha(existingCn.getCha());
				empinv.setCompanyId(cid);
				empinv.setContainerNo(existingCn.getContainerNo());
				empinv.setContainerSize(existingCn.getContainerSize());
				empinv.setContainerType(existingCn.getContainerType());
				empinv.setDeStuffId(HoldNextIdD1);
				empinv.setDocRefNo(existingCn.getIgmNo());
				empinv.setEmptyDate(new Date());
				empinv.setErpDocRefNo(existingCn.getIgmTransId());
				empinv.setFinYear(existingCn.getFinYear());
				empinv.setGateInDate(existingCn.getGateInDate());
				empinv.setGateInId(existingCn.getGateInId());
				empinv.setIsoCode(existingCn.getIso());
				empinv.setMovementCode("DEVC");
				empinv.setOnAccountOf("");
				empinv.setProfitcentreId(existingCn.getProfitcentreId());
				empinv.setSa(igm.getShippingAgent());
				empinv.setSl(igm.getShippingLine());
				empinv.setSubDocRefNo(existingCn.getIgmLineNo());
				empinv.setStatus("A");
				empinv.setCreatedBy(user);
				empinv.setCreatedDate(new Date());

				emptyinventoryrepo.save(empinv);

				Impexpgrid grid = new Impexpgrid();
				grid.setCompanyId(cid);
				grid.setBranchId(bid);
				grid.setCreatedBy(user);
				grid.setCreatedDate(new Date());
				grid.setApprovedBy(user);
				grid.setApprovedDate(new Date());
				grid.setStatus("A");
				grid.setAreaReleased(BigDecimal.ZERO);
				grid.setCellArea(BigDecimal.ZERO);
				grid.setCellAreaAllocated(BigDecimal.ZERO);
				grid.setCellAreaUsed(BigDecimal.ZERO);
				Date r = new Date();
				Calendar cal = Calendar.getInstance();
				cal.setTime(r);
				int year = cal.get(Calendar.YEAR);
				grid.setFinYear(String.valueOf(year));
				grid.setLineNo(1);
				grid.setProcessTransId(HoldNextIdD1);
				grid.setQtyTakenOut(BigDecimal.ZERO);
				grid.setStuffReqQty(0);
				grid.setSubSrNo(1);
				grid.setTransType("IMP");
				grid.setYardBlock(c.getYardBlock());
				grid.setYardLocation(c.getYardLocation());
				grid.setBlockCellNo(c.getBlockCellNo());
				grid.setYardPackages(0);
				impexpgridrepo.save(grid);

				ImportInventory existingInv = importinventoryrepo.getById(cid, bid, c.getIgmTransId(), c.getIgmNo(),
						c.getContainerNo(), c.getGateInId());

				if (existingInv != null) {
					existingInv.setDeStuffDate(new Date());
					existingInv.setDeStuffId(HoldNextIdD1);

					importinventoryrepo.save(existingInv);
				}

				processnextidrepo.updateAuditTrail(cid, bid, "P05067", newId, "2024");
				processnextidrepo.updateAuditTrail(cid, bid, "P05066", HoldNextIdD1, "2024");

			}

		}

		Object[] igmcrg1 = cfigmcrgrepo.getDataByIgmAndLineForDestuff(cid, bid, crg1.getIgmNo(), crg1.getIgmLineNo());

		if (igmcrg1 == null) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		List<Cfigmcn> container1 = cfigmcnrepo.getDataForDestuff(cid, bid, crg1.getIgmNo(), crg1.getIgmLineNo());

		if (container1.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		Map<String, Object> data1 = new HashMap<>();
		data1.put("crg", igmcrg1);
		data1.put("container", container1);

		System.out.println(igmcrg1);
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

	@GetMapping("/getLineDataFromIgmAndTrans")
	public ResponseEntity<?> getLineDataFromIgmAndTrans(@RequestParam("cid") String cid,
			@RequestParam("bid") String bid, @RequestParam("igm") String igm,
			@RequestParam("igmtrans") String igmtrans) {
		List<String> crgData = destuffcrgrepo.getLineDataDataByIgmAndTransId(cid, bid, igmtrans, igm);

		if (crgData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(crgData, HttpStatus.OK);
	}

	@GetMapping("/getDataFromIgmAndTrans")
	public ResponseEntity<?> getDataFromIgmAndTrans(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmtrans") String igmtrans,
			@RequestParam("igmLineNo") String igmLineNo) {
		List<DestuffCrg> crgData = destuffcrgrepo.getDataByIgmAndTransId(cid, bid, igmtrans, igm, igmLineNo);

		if (crgData.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(crgData, HttpStatus.OK);
	}

	@PostMapping("/saveCargoExaminationData")
	public ResponseEntity<?> saveCargoExaminationData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody Map<String, Object> crgData)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ExamCrg crg = mapper.readValue(mapper.writeValueAsString(crgData.get("crg")), ExamCrg.class);

		List<ExamCrg> crgs = mapper.readValue(mapper.writeValueAsString(crgData.get("crgs")),
				new TypeReference<List<ExamCrg>>() {
				});

		if (crgs.isEmpty()) {
			return new ResponseEntity<>("Exam Data not found", HttpStatus.CONFLICT);
		}

		String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05070", "2024");

		int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

		int nextNumericNextID1 = lastNextNumericId1 + 1;

		String HoldNextIdD1 = String.format("DSLC%06d", nextNumericNextID1);

		int sr = 1;

		Cfigmcrg cr = cfigmcrgrepo.getData1(cid, bid, crg.getIgmTransId(), crg.getIgmNo(), crg.getIgmLineNo());

		if (cr == null) {
			return new ResponseEntity<>("Cargo data for item no" + crg.getIgmLineNo() + " not found",
					HttpStatus.CONFLICT);
		}

		Boolean isExistBe = cfigmcrgrepo.isExistBENo(cid, bid, crg.getIgmNo(), crg.getIgmLineNo(), crg.getBeNo());

		if (isExistBe) {
			return new ResponseEntity<>("BE No already exist.", HttpStatus.BAD_REQUEST);
		}
	

		for (ExamCrg c : crgs) {

			DestuffCrg decr = destuffcrgrepo.getSingleData(cid, bid, c.getIgmTransId(), c.getIgmNo(), c.getIgmLineNo(),
					c.getDeStuffId(), c.getDeStuffLineId());

			if (decr == null) {
				return new ResponseEntity<>("Destuff data for item no" + c.getIgmLineNo() + " not found",
						HttpStatus.CONFLICT);
			}

			if (decr.getExamTallyId() == null || decr.getExamTallyId().isEmpty()) {

				c.setExamTallyId(HoldNextIdD1);
				c.setExamTallyLineId(String.valueOf(sr));
				c.setExamTallyDate(new Date());
				c.setOnAccountOf(decr.getOnAccountOf());
				c.setActualNoOfPackages(new BigDecimal(decr.getActualNoOfPackages()));
				c.setBlNo(cr.getBlNo());
				c.setBlDate(cr.getBlDate());
				c.setStatus("A");
				c.setCreatedBy(user);
				c.setCreatedDate(new Date());
				c.setApprovedBy(user);
				c.setApprovedDate(new Date());
				c.setShift(crg.getShift());
				c.setChaCode(crg.getChaCode());
				c.setChaName(crg.getChaName());
				c.setBeDate(crg.getBeDate());
				c.setBeNo(crg.getBeNo());

				examcrgrepo.save(c);

				cr.setExamTallyId(HoldNextIdD1);
				cr.setExamTallyDate(new Date());
				cr.setBeNo(crg.getBeNo());
				cr.setBeDate(crg.getBeDate());

				cfigmcrgrepo.save(cr);

				decr.setExamTallyId(HoldNextIdD1);
				decr.setExamTallyLineId(String.valueOf(sr));
				decr.setExamDate(new Date());
				destuffcrgrepo.save(decr);

				processnextidrepo.updateAuditTrail(cid, bid, "P05070", HoldNextIdD1, "2024");
				sr++;
			} else {
				ExamCrg exam = examcrgrepo.getSingleData(cid, bid, c.getExamTallyId(), c.getExamTallyLineId());
				if (exam != null) {
					exam.setEditedBy(user);
					exam.setEditedDate(new Date());
					exam.setShift(crg.getShift());
					exam.setChaCode(crg.getChaCode());
					exam.setChaName(crg.getChaName());
					exam.setBeDate(crg.getBeDate());
					exam.setBeNo(crg.getBeNo());
					exam.setSampleQty(c.getSampleQty());
					exam.setExaminedPercentage(c.getExaminedPercentage());

					exam.setTypeOfCargo(c.getTypeOfCargo());
					exam.setExaminer(c.getExaminer());
					exam.setPurpose(c.getPurpose());

					examcrgrepo.save(exam);

					cr.setBeNo(crg.getBeNo());
					cr.setBeDate(crg.getBeDate());

					cfigmcrgrepo.save(cr);
				}
			}
		}

		List<ExamCrg> allData = examcrgrepo.getData1(cid, bid, crg.getIgmNo(), crg.getIgmTransId(), crg.getIgmLineNo());

		return new ResponseEntity<>(allData, HttpStatus.OK);

	}

	@GetMapping("/searchExaminationData")
	public ResponseEntity<?> searchCargoExamination(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "value", required = false) String value) {
		List<Object[]> data = examcrgrepo.search(cid, bid, value);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/getSearchedData")
	public ResponseEntity<?> getSearchedData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmtrans") String igmtrans,
			@RequestParam(name = "examTallyId", required = false) String examTallyId) {
		List<ExamCrg> data = examcrgrepo.getData2(cid, bid, igm, igmtrans, examTallyId);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}

	@GetMapping("/getLatestExamCargoId")
	public ResponseEntity<?> getLatestDestuffId(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("igm") String igm, @RequestParam("igmtrans") String igmtrans,
			@RequestParam(name = "line", required = false) String line) {
		List<String> data = examcrgrepo.getLastCargoExaminationId(cid, bid, igm, igmtrans, line);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found!!", HttpStatus.CONFLICT);
		} else {
			return new ResponseEntity<>(data.get(0), HttpStatus.OK);
		}
	}
}
