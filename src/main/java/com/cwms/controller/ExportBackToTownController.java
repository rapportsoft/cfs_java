package com.cwms.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

import com.cwms.entities.ExportBackToTown;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.Impexpgrid;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.ExportBackToTownRepo;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/exportBackToTown")
public class ExportBackToTownController {

	@Autowired
	private ExportBackToTownRepo exportbacktotownrepo;

	@Autowired
	private ExportSbCargoEntryRepo exportcargoentryrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private ExportEntryRepo exportentryrepo;

	@Autowired
	private ExportCartingRepo exportcartingrepo;

	@Autowired
	private YardBlockCellRepository yardBlockCellRepository;

	@Autowired
	private Impexpgridrepo impexpgridrepo;

	@GetMapping("/searchSb")
	public ResponseEntity<?> searchSb(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {
		List<Object[]> data = exportcargoentryrepo.searchDataForBacktoTown(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/selectSbData")
	public ResponseEntity<?> selectSbData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("sb") String sb, @RequestParam("trans") String trans) {
		Object[] data = exportcargoentryrepo.getSelectedBackToTownData(cid, bid, sb, trans);

		if (data == null) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PostMapping("/saveBackToTownData")
	public ResponseEntity<?> saveBackToTownData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("user") String user, @RequestBody ExportBackToTown data) throws CloneNotSupportedException {

		if (data == null) {
			return new ResponseEntity<>("Data not found.", HttpStatus.CONFLICT);
		}

		ExportSbCargoEntry cargo = exportcargoentryrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, data.getSbNo(),
				data.getSbTransId());

		if (cargo == null) {
			return new ResponseEntity<>("SB cargo data not found.", HttpStatus.CONFLICT);
		}

		if (data.getBackToTownTransId().isEmpty() || data.getBackToTownTransId() == null) {

			ExportSbEntry sbEntry = exportentryrepo.getExportSbEntryBySbNoAndSbTransId(cid, bid, data.getSbNo(),
					data.getSbTransId());

			if (sbEntry == null) {
				return new ResponseEntity<>("SB data not found.", HttpStatus.CONFLICT);
			}

			String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05088", "2024");

			int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

			int nextNumericNextID1 = lastNextNumericId1 + 1;

			String HoldNextIdD1 = String.format("BKTY%06d", nextNumericNextID1);

			data.setCompanyId(cid);
			data.setBranchId(bid);
			data.setStatus("A");
			data.setCreatedBy(user);
			data.setCreatedDate(new Date());
			data.setApprovedBy(user);
			data.setApprovedDate(new Date());
			data.setImporterId(sbEntry.getExporterId());
			data.setImpSrNo(Integer.parseInt(sbEntry.getSrNo()));
			data.setBackToTownTransId(HoldNextIdD1);
			data.setBackToTownTransDate(new Date());
			data.setProfitcentreId("N00004");
			data.setSbLineNo(cargo.getSbLineNo());
			data.setAccSrNo(1);
			data.setIgst("N");
			data.setSgst("N");
			data.setCgst("N");
			data.setGateOutPackages(BigDecimal.ZERO);

			exportbacktotownrepo.save(data);

			processnextidrepo.updateAuditTrail(cid, bid, "P05088", HoldNextIdD1, "2024");

			cargo.setBackToTownPack((cargo.getBackToTownPack() == null ? 0 : cargo.getBackToTownPack())
					+ (data.getBackToTownPackages() == null ? 0 : data.getBackToTownPackages().intValue()));

			exportcargoentryrepo.save(cargo);

			List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTrans2(cid, bid, data.getSbTransId(),
					data.getSbNo());

			if (cartingData.isEmpty()) {
				return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
			}

			BigDecimal qty = data.getBackToTownPackages();

			for (ExportCarting c : cartingData) {
				List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid, c.getCartingTransId(),
						c.getCartingLineId());

				if (!grid.isEmpty()) {

					for (Impexpgrid g : grid) {

						if (qty.compareTo(BigDecimal.ZERO) == 0) {
							break;
						}

						BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages());
						BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut() : BigDecimal.ZERO;
						BigDecimal remainingYardPackages = yardPackages.subtract(qtyTakenOut);

						YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid, g.getYardLocation(),
								g.getYardBlock(), g.getBlockCellNo());

						if (qty.compareTo(remainingYardPackages) >= 0) {
							// Case where gridVal is greater than or equal to remaining yard packages
							g.setQtyTakenOut(qtyTakenOut.add(remainingYardPackages));

							BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingYardPackages)
									.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

							g.setAreaReleased(
									(g.getAreaReleased() != null ? g.getAreaReleased() : BigDecimal.ZERO).add(tenArea));

							qty = qty.subtract(remainingYardPackages);

							if (yard != null) {
								yard.setCellAreaUsed(
										(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
												.subtract(tenArea));
								yardBlockCellRepository.save(yard);
							}
						} else {
							// Case where gridVal is less than remaining yard packages
							g.setQtyTakenOut(qtyTakenOut.add(qty));

							BigDecimal tenArea = g.getCellAreaAllocated().multiply(qty).divide(yardPackages,
									BigDecimal.ROUND_HALF_UP);

							g.setAreaReleased(
									(g.getAreaReleased() != null ? g.getAreaReleased() : BigDecimal.ZERO).add(tenArea));

							qty = BigDecimal.ZERO;

							if (yard != null) {
								yard.setCellAreaUsed(
										(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
												.subtract(tenArea));
								yardBlockCellRepository.save(yard);
							}
						}

						impexpgridrepo.save(g);

						// Exit the loop if gridVal is zero

					}
				}
			}

			ExportBackToTown result = exportbacktotownrepo.getDataByIdAndSbNoAndSbTrans1(cid, bid, HoldNextIdD1,
					data.getSbNo(), data.getSbTransId());

			if (result == null) {
				return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		} else {
			ExportBackToTown exist = exportbacktotownrepo.getDataByIdAndSbNoAndSbTrans(cid, bid,
					data.getBackToTownTransId(), data.getSbNo(), data.getSbTransId());

			if (exist == null) {
				return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
			}

			ExportBackToTown cloneExist = (ExportBackToTown) exist.clone();

			exist.setOnAccountOf(data.getOnAccountOf());
			exist.setRequestReferenceNo(data.getRequestReferenceNo());
			exist.setRequestReferenceDate(data.getRequestReferenceDate());
			exist.setTypeOfContainer(data.getTypeOfContainer());
			exist.setBackToTownPackages(data.getBackToTownPackages());
			exist.setBackToTownWeight(data.getBackToTownWeight());
			exist.setEditedBy(user);
			exist.setEditedDate(new Date());

			exportbacktotownrepo.save(exist);

			cargo.setBackToTownPack((cargo.getBackToTownPack() - cloneExist.getBackToTownPackages().intValue())
					+ data.getBackToTownPackages().intValue());

			exportcargoentryrepo.save(cargo);

			List<ExportCarting> cartingData = exportcartingrepo.getDataBySbNoSbTrans2(cid, bid, data.getSbTransId(),
					data.getSbNo());

			if (cartingData.isEmpty()) {
				return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
			}

			if (cloneExist.getBackToTownPackages().compareTo(data.getBackToTownPackages()) > 0) {
				BigDecimal qty = cloneExist.getBackToTownPackages().subtract(data.getBackToTownPackages());

				for (ExportCarting c : cartingData) {

					List<Impexpgrid> grid = impexpgridrepo.getDataForTally1(cid, bid, c.getCartingTransId(),
							c.getCartingLineId());

					if (!grid.isEmpty()) {

						for (Impexpgrid g : grid) {

							if (qty.compareTo(BigDecimal.ZERO) == 0) {
								break;
							}

							BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages()); // Convert int to
																								// BigDecimal
							YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
									g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());

							BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut() : BigDecimal.ZERO;

							if (qty.compareTo(qtyTakenOut) >= 0) {
								// Full deduction of qtyTakenOut from gridVal
								g.setQtyTakenOut(BigDecimal.ZERO); // Set qtyTakenOut to zero after fully deducting it

								// Calculate area to be released based on the amount in qtyTakenOut
								BigDecimal tenArea = g.getCellAreaAllocated().multiply(qtyTakenOut).divide(yardPackages,
										BigDecimal.ROUND_HALF_UP);

								// Safely subtract the calculated tenArea from AreaReleased, handling null
								BigDecimal newAreaReleased = (g.getAreaReleased() != null ? g.getAreaReleased()
										: BigDecimal.ZERO).subtract(tenArea);
								g.setAreaReleased(newAreaReleased);

								// Deduct qtyTakenOut amount from gridVal
								qty = qty.subtract(qtyTakenOut);

								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.add(newAreaReleased));
									yardBlockCellRepository.save(yard);
								}
							} else {
								// Partial deduction from gridVal based on gridVal’s amount
								g.setQtyTakenOut(qtyTakenOut.subtract(qty));

								// Calculate area to release based on gridVal
								BigDecimal tenArea = g.getCellAreaAllocated().multiply(qty).divide(yardPackages,
										BigDecimal.ROUND_HALF_UP);

								// Update AreaReleased by subtracting tenArea
								BigDecimal newAreaReleased = (g.getAreaReleased() != null ? g.getAreaReleased()
										: BigDecimal.ZERO).subtract(tenArea);
								g.setAreaReleased(newAreaReleased);

								// Set gridVal to zero after full deduction
								qty = BigDecimal.ZERO;

								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.add(newAreaReleased));
									yardBlockCellRepository.save(yard);
								}
							}

							impexpgridrepo.save(g);

							// Exit the loop if gridVal is zero

						}
					}
				}

			} else if (cloneExist.getBackToTownPackages().compareTo(data.getBackToTownPackages()) < 0) {

				BigDecimal qty = data.getBackToTownPackages().subtract(cloneExist.getBackToTownPackages());

				for (ExportCarting c : cartingData) {
					List<Impexpgrid> grid = impexpgridrepo.getDataForTally(cid, bid, c.getCartingTransId(),
							c.getCartingLineId());

					if (!grid.isEmpty()) {

						for (Impexpgrid g : grid) {

							if (qty.compareTo(BigDecimal.ZERO) == 0) {
								break;
							}

							BigDecimal yardPackages = BigDecimal.valueOf(g.getYardPackages());
							BigDecimal qtyTakenOut = g.getQtyTakenOut() != null ? g.getQtyTakenOut() : BigDecimal.ZERO;
							BigDecimal remainingYardPackages = yardPackages.subtract(qtyTakenOut);

							YardBlockCell yard = yardBlockCellRepository.getYardCellByCellNo(cid, bid,
									g.getYardLocation(), g.getYardBlock(), g.getBlockCellNo());

							if (qty.compareTo(remainingYardPackages) >= 0) {
								// Case where gridVal is greater than or equal to remaining yard packages
								g.setQtyTakenOut(qtyTakenOut.add(remainingYardPackages));

								BigDecimal tenArea = g.getCellAreaAllocated().multiply(remainingYardPackages)
										.divide(yardPackages, BigDecimal.ROUND_HALF_UP);

								g.setAreaReleased((g.getAreaReleased() != null ? g.getAreaReleased() : BigDecimal.ZERO)
										.add(tenArea));

								qty = qty.subtract(remainingYardPackages);

								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.subtract(tenArea));
									yardBlockCellRepository.save(yard);
								}
							} else {
								// Case where gridVal is less than remaining yard packages
								g.setQtyTakenOut(qtyTakenOut.add(qty));

								BigDecimal tenArea = g.getCellAreaAllocated().multiply(qty).divide(yardPackages,
										BigDecimal.ROUND_HALF_UP);

								g.setAreaReleased((g.getAreaReleased() != null ? g.getAreaReleased() : BigDecimal.ZERO)
										.add(tenArea));

								qty = BigDecimal.ZERO;

								if (yard != null) {
									yard.setCellAreaUsed(
											(yard.getCellAreaUsed() == null ? BigDecimal.ZERO : yard.getCellAreaUsed())
													.subtract(tenArea));
									yardBlockCellRepository.save(yard);
								}
							}

							impexpgridrepo.save(g);

							// Exit the loop if gridVal is zero

						}
					}
				}
			}

			ExportBackToTown result = exportbacktotownrepo.getDataByIdAndSbNoAndSbTrans1(cid, bid,
					data.getBackToTownTransId(), data.getSbNo(), data.getSbTransId());

			if (result == null) {
				return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(result, HttpStatus.OK);

		}

	}

	@GetMapping("/searchbacktoTownData")
	public ResponseEntity<?> searchBackToTownData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name = "val", required = false) String val) {

		List<Object[]> data = exportbacktotownrepo.searchBacktotownData(cid, bid, val);

		if (data.isEmpty()) {
			return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/selectbacktoTownData")
	public ResponseEntity<?> selectbacktoTownData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id, @RequestParam("sb") String sb, @RequestParam("trans") String trans) {

		ExportBackToTown result = exportbacktotownrepo.getDataByIdAndSbNoAndSbTrans1(cid, bid, id, sb, trans);

		if (result == null) {
			return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getDataForGatePass")
	public ResponseEntity<?> getDataForGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam(name="val", required = false) String val){
		List<Object[]> result = exportbacktotownrepo.getDataForGatePass(cid, bid, val);
		
		if (result.isEmpty()) {
			return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/getSingleDataForGatePass")
	public ResponseEntity<?> getSingleDataForGatePass(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
			@RequestParam("id") String id,@RequestParam("sb") String sb,@RequestParam("trans") String trans){
		ExportBackToTown result = exportbacktotownrepo.getSingleDataForGatePass(cid, bid, id,sb,trans);
		
		if (result == null) {
			return new ResponseEntity<>("Back to town data not found.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
