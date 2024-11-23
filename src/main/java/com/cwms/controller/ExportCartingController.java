package com.cwms.controller;

import java.math.BigDecimal;
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

import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportContainerCarting;
import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.GateIn;
import com.cwms.entities.Impexpgrid;
import com.cwms.entities.YardBlockCell;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportContainerCartingRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.cwms.service.ExportCartingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/carting")
@CrossOrigin("*")
public class ExportCartingController {
	
	@Autowired
	public ExportCartingService cartingService;

	@Autowired
	public ExportContainerCartingRepo exportContainerCartingRepo;

	@Autowired
	public ExportCartingRepo exportCartingRepo;

	@Autowired
	public ExportStuffTallyRepo exportStuffTallyRepo;

	@Autowired
	private Impexpgridrepo impexpgridrepo;

	@Autowired
	private ExportInventoryRepository exportinvrepo;

	@Autowired
	private ProcessNextIdRepository processnextidrepo;

	@Autowired
	private GateInRepository gateInRepo;

	@Autowired
	private ExportSbCargoEntryRepo exportSbCargoEntryRepo;

	@Autowired
	private YardBlockCellRepository yardblockcellrepo;

	@Autowired
	private HelperMethods helperMethods;

	

	@PostMapping("/addExportCarting")
	public ResponseEntity<?> addExportCarting(@RequestParam("companyId") String companyId, @RequestParam("branchId") String branchId,
			@RequestBody List<ExportCarting> cartingList, @RequestParam("userId") String User, @RequestParam("status") String status)
		{				
			ResponseEntity<?> addExportCartingEntry = cartingService.addExportCarting(companyId, branchId, cartingList, User, status);
			return addExportCartingEntry;
		}

	@GetMapping("/getSelectedCartingEntry")
	public ResponseEntity<?> getSelectedCartingEntry(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam("cartingTransId") String cartingTransId,
	        @RequestParam("cartingLineId") String cartingLineId, 
	        @RequestParam("sbNo") String sbNo,
	        @RequestParam("profitCenterId") String profitCenterId
	       ) {	    
	    try {	    	
	    	ResponseEntity<?>  cartingEntries = cartingService.getSelectedCartingEntry(companyId, branchId,  cartingTransId, cartingLineId,sbNo, profitCenterId);
	        return cartingEntries;
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	@GetMapping("/getCartingEntriesToSelect")
	public ResponseEntity<?> getCartingEntriesToSelect(
	        @RequestParam("companyId") String companyId, 
	        @RequestParam("branchId") String branchId,	        
	        @RequestParam(value="searchValue", required = false) String searchValue
	       ) {	    
	    try {	    	
	    	List<Object[]> cartingEntries = cartingService.getCartingEntriesToSelect(companyId, branchId, searchValue);
	        return ResponseEntity.ok(cartingEntries);
	    } catch (Exception e) {	       
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while checking duplicate SB No.");
	    }
	}
	
	
	
	// Export Reworking

		@GetMapping("/getContainerNoForExportReworking")
		public ResponseEntity<?> getContainerNoForExportReworking(@RequestParam("cid") String cid,
				@RequestParam("bid") String bid, @RequestParam(name = "val", required = false) String val,
				@RequestParam("type") String type) {

			String finalType = "Normal Container".equals(type) ? "CLP"
					: "Port Return Container".equals(type) ? "PortRn" : "Buffer";

			if ("CLP".equals(finalType) || "Buffer".equals(finalType)) {
				List<String> data = exportStuffTallyRepo.getRecordsForExportReworking(cid, bid, val, finalType);

				if (data.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				List<String> data = gateInRepo.getPortReturnConForReworking(cid, bid, val);

				if (data.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(data, HttpStatus.OK);
			}

		}

		@GetMapping("/getSelectedContainerNoForExportReworking")
		public ResponseEntity<?> getSelectedContainerNoForExportReworking(@RequestParam("cid") String cid,
				@RequestParam("bid") String bid, @RequestParam("val") String val, @RequestParam("type") String type) {

			String finalType = "Normal Container".equals(type) ? "CLP"
					: "Port Return Container".equals(type) ? "PortRn" : "Buffer";

			if ("CLP".equals(finalType) || "Buffer".equals(finalType)) {
				List<Object[]> data = exportStuffTallyRepo.getRecordsForExportReworkingByContainerNo(cid, bid, val);

				if (data.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				List<Object[]> data = gateInRepo.getPortReturnConForReworkingData(cid, bid, val);

				if (data.isEmpty()) {
					return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
				}

				return new ResponseEntity<>(data, HttpStatus.OK);
			}
		}

		@PostMapping("/saveExportReworkingContainer")
		public ResponseEntity<?> saveExportReworkingContainer(@RequestParam("cid") String cid,
				@RequestParam("bid") String bid, @RequestParam("user") String user, @RequestParam("status") String status,
				@RequestBody Map<String, Object> data) throws JsonMappingException, JsonProcessingException {

			ObjectMapper mapper = new ObjectMapper();

			ExportContainerCarting conCarting = mapper.readValue(mapper.writeValueAsString(data.get("conCarting")),
					ExportContainerCarting.class);

			List<ExportCarting> carting = mapper.readValue(mapper.writeValueAsString(data.get("carting")),
					new TypeReference<List<ExportCarting>>() {
					});

			if (carting.isEmpty()) {
				return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
			}

			

			String financialYear = helperMethods.getFinancialYear();

			System.out.println("status " + conCarting.getContainerSearchType());

			if ("N".equals(status)) {

				if ("Normal Container".equals(conCarting.getContainerSearchType())
						|| "Buffer Container".equals(conCarting.getContainerSearchType())) {
					
					GateIn gate = gateInRepo.getData2(cid, bid, conCarting.getGateInId());

					if (gate == null) {
						return new ResponseEntity<>("Gate in data not found", HttpStatus.CONFLICT);
					}
					if (conCarting.getDeStuffId().isEmpty()) {

						for (ExportCarting c : carting) {

							YardBlockCell cell = yardblockcellrepo.getAllData(cid, bid, c.getGridLocation(),
									c.getGridBlock(), c.getGridCellNo());

							if (cell != null) {
								BigDecimal remainArea = cell.getCellArea().subtract(
										cell.getCellAreaUsed() == null ? BigDecimal.ZERO : cell.getCellAreaUsed());

								if (c.getYardArea().compareTo(remainArea) > 0) {
									return new ResponseEntity<>("The maximum remaining area for location "
											+ c.getGridLocation() + ", block " + c.getGridBlock() + ", and cell "
											+ c.getGridCellNo() + " is " + remainArea + ".", HttpStatus.CONFLICT);
								}
							}

						}

						String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05090", "2024");

						int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

						int nextNumericNextID1 = lastNextNumericId1 + 1;

						String HoldNextIdD1 = String.format("RTDN%06d", nextNumericNextID1);

						int sr = 1;

						for (ExportCarting c : carting) {
							ExportSbCargoEntry sbCargo = exportSbCargoEntryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
									c.getSbNo(), c.getSbTransId());

							if (sbCargo == null) {
								return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
							}

							c.setCompanyId(cid);
							c.setBranchId(bid);
							c.setStatus("N");
							c.setCreatedBy(user);
							c.setCreatedDate(new Date());
							c.setProfitcentreId("N00004");
							c.setCartingLineId(String.valueOf(sr));
							c.setFinYear(financialYear);
							c.setSbLineNo("1");
							c.setSbDate(sbCargo == null ? null : sbCargo.getSbDate());
							c.setCartingTransId(HoldNextIdD1);
							c.setCartingTransDate(new Date());
							c.setGateInId(conCarting.getGateInId());
							c.setGateInDate(conCarting.getGateInDate());
							c.setShift("Day");
							c.setVehicleNo(conCarting.getVehicleNo());
							c.setOnAccountOf(conCarting.getOnAccountOf());
							c.setInvoiceType("EXP");
							c.setGateInType(gate.getGateInType());
							c.setAreaOccupied((c.getAreaOccupied() == null ? BigDecimal.ZERO : c.getAreaOccupied())
									.add(c.getYardArea()));
							c.setYardPackages((c.getYardPackages() == null ? BigDecimal.ZERO : c.getYardPackages())
									.add(c.getYardPack()));

							exportCartingRepo.save(c);

							YardBlockCell cell = yardblockcellrepo.getAllData(cid, bid, c.getGridLocation(),
									c.getGridBlock(), c.getGridCellNo());

							if (cell != null) {
								cell.setCellAreaUsed(
										(cell.getCellAreaUsed() == null ? BigDecimal.ZERO : cell.getCellAreaUsed())
												.add(c.getYardArea()));
								yardblockcellrepo.save(cell);
							}

							Impexpgrid grid = new Impexpgrid();
							grid.setCompanyId(cid);
							grid.setBranchId(bid);
							grid.setFinYear(helperMethods.getFinancialYear());
							grid.setProcessTransId(HoldNextIdD1);
							grid.setLineNo(sr);
							grid.setSubSrNo(1);
							grid.setYardLocation(c.getGridLocation());
							grid.setYardBlock(c.getGridBlock());
							grid.setBlockCellNo(c.getGridCellNo());
							grid.setYardPackages(c.getYardPack().intValue());
							grid.setTransType("EXP");
							grid.setAreaReleased(BigDecimal.ZERO);
							grid.setCellArea(cell.getCellArea());
							grid.setCellAreaAllocated(c.getYardArea());
							grid.setCellAreaUsed(cell.getCellAreaUsed());

							grid.setCreatedBy(user);
							grid.setCreatedDate(new Date());

							grid.setApprovedBy(user);
							grid.setApprovedDate(new Date());
							grid.setStatus("A");

							impexpgridrepo.save(grid);

							if ("Normal Container".equals(conCarting.getContainerSearchType())) {
								sbCargo.setStuffReqQty(sbCargo.getStuffReqQty() - c.getActualNoOfPackages().intValue());
							}
							sbCargo.setStuffedQty(sbCargo.getStuffedQty().subtract(c.getActualNoOfPackages()));

							exportSbCargoEntryRepo.save(sbCargo);

							sr++;

							List<ExportStuffTally> tally = exportStuffTallyRepo.getRecordsForExportReworkingByContainerNo1(
									cid, bid, conCarting.getContainerNo(), c.getSbNo(), c.getSbTransId());

							if (!tally.isEmpty()) {
								tally.stream().forEach(t -> {
									t.setReworkFlag("Y");
									t.setReworkDate(new Date());
									t.setReworkId(HoldNextIdD1);

									exportStuffTallyRepo.save(t);

									ExportCarting existCart = exportCartingRepo.getDataByLineNoAndCartingTransId(cid, bid,
											t.getCartingTransId(), t.getCartingLineId());

									if (existCart != null) {
										existCart.setStuffedNoOfPackages(
												existCart.getStuffedNoOfPackages().subtract(t.getStuffedQty()));

										exportCartingRepo.save(existCart);
									}
								});
							}

						}

						conCarting.setCompanyId(cid);
						conCarting.setBranchId(bid);
						conCarting.setStatus("N");
						conCarting.setCreatedBy(user);
						conCarting.setCreatedDate(new Date());
						conCarting.setDeStuffId(HoldNextIdD1);
						conCarting.setDeStuffDate(new Date());
						conCarting.setProfitCentreId("N00004");
						conCarting.setInvoiceType("EXP");
						conCarting.setGateInType(gate.getGateInType());
						conCarting.setShift("Day");

						exportContainerCartingRepo.save(conCarting);

						ExportInventory inv = exportinvrepo.getDataByContainerNoAndGateIn(cid, bid,
								conCarting.getContainerNo(), conCarting.getGateInId());

						if (inv != null) {
							inv.setStuffReqDate(null);
							inv.setStuffReqEditedBy("");
							inv.setStuffReqEditedDate(null);
							inv.setStuffReqId("");
							inv.setStuffTallyDate(null);
							inv.setStuffTallyEditedBy("");
							inv.setStuffTallyEditedDate(null);
							inv.setStuffTallyId("");

							exportinvrepo.save(inv);
						}

						gate.setStuffRequestId("");
						gate.setStuffTallyId("");
						gate.setStuffTallyDate(null);
						gate.setStuffTallyStatus("N");

						gateInRepo.save(gate);

						processnextidrepo.updateAuditTrail(cid, bid, "P05090", HoldNextIdD1, "2024");

//						int updateReworkId = exportStuffTallyRepo.updateReworkId(cid, bid, HoldNextIdD1,
//								conCarting.getContainerNo());

						Map<String, Object> result = new HashMap<>();

						ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid,
								bid, HoldNextIdD1);

						result.put("conCarting", resultContainerCarting);

						List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid, HoldNextIdD1);

						result.put("carting", resultCarting);

						return new ResponseEntity<>(result, HttpStatus.OK);

					} else {

						ExportContainerCarting existingConCarting = exportContainerCartingRepo.getDataBuDestuffId(cid, bid,
								conCarting.getDeStuffId());

						if (existingConCarting != null) {
							existingConCarting.setEditedBy(user);
							existingConCarting.setEditedDate(new Date());
							// existingConCarting.setContainerSearchType(conCarting.getContainerSearchType());
							existingConCarting.setSaSealNo(conCarting.getSaSealNo());
							existingConCarting.setOnAccountOf(conCarting.getOnAccountOf());
							existingConCarting.setFromDate(conCarting.getFromDate());
							existingConCarting.setToDate(conCarting.getToDate());
							existingConCarting.setRemark(conCarting.getRemark());

							exportContainerCartingRepo.save(existingConCarting);
						}

						for (ExportCarting c : carting) {
							ExportCarting exist = exportCartingRepo.getDataByCartingTransIdSBTransAndSb(cid, bid,
									conCarting.getDeStuffId(), c.getSbTransId(), c.getSbNo());

							ExportSbCargoEntry sbCargo = exportSbCargoEntryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
									c.getSbNo(), c.getSbTransId());

							if (sbCargo == null) {
								return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
							}

							if (exist != null) {

								if ("Normal Container".equals(conCarting.getContainerSearchType())) {
									sbCargo.setStuffReqQty((sbCargo.getStuffReqQty() + c.getActualNoOfPackages().intValue())
											- exist.getActualNoOfPackages().intValue());

								}
								sbCargo.setStuffedQty((sbCargo.getStuffedQty().add(c.getActualNoOfPackages()))
										.subtract(exist.getActualNoOfPackages()));

								exportSbCargoEntryRepo.save(sbCargo);

								exist.setEditedBy(user);
								exist.setEditedDate(new Date());
								exist.setActualNoOfPackages(c.getActualNoOfPackages());
								exist.setActualNoOfWeight(c.getActualNoOfWeight());
								exist.setOnAccountOf(conCarting.getOnAccountOf());

								exportCartingRepo.save(exist);
							}
						}

						Map<String, Object> result = new HashMap<>();

						ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid,
								bid, conCarting.getDeStuffId());

						result.put("conCarting", resultContainerCarting);

						List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid,
								conCarting.getDeStuffId());

						result.put("carting", resultCarting);

						return new ResponseEntity<>(result, HttpStatus.OK);

					}

				}

				else {

					if (conCarting.getDeStuffId().isEmpty()) {
						for (ExportCarting c : carting) {

							YardBlockCell cell = yardblockcellrepo.getAllData(cid, bid, c.getGridLocation(),
									c.getGridBlock(), c.getGridCellNo());

							if (cell != null) {
								BigDecimal remainArea = cell.getCellArea().subtract(
										cell.getCellAreaUsed() == null ? BigDecimal.ZERO : cell.getCellAreaUsed());

								if (c.getYardArea().compareTo(remainArea) > 0) {
									return new ResponseEntity<>("The maximum remaining area for location "
											+ c.getGridLocation() + ", block " + c.getGridBlock() + ", and cell "
											+ c.getGridCellNo() + " is " + remainArea + ".", HttpStatus.CONFLICT);
								}
							}

						}

						String holdId1 = processnextidrepo.findAuditTrail(cid, bid, "P05090", "2024");

						int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

						int nextNumericNextID1 = lastNextNumericId1 + 1;

						String HoldNextIdD1 = String.format("RTDN%06d", nextNumericNextID1);

						int sr = 1;

						for (ExportCarting c : carting) {
							ExportSbCargoEntry sbCargo = exportSbCargoEntryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
									c.getSbNo(), c.getSbTransId());

							if (sbCargo == null) {
								return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
							}

							c.setCompanyId(cid);
							c.setBranchId(bid);
							c.setStatus("N");
							c.setCreatedBy(user);
							c.setCreatedDate(new Date());
							c.setProfitcentreId("N00004");
							c.setCartingLineId(String.valueOf(sr));
							c.setFinYear(financialYear);
							c.setSbLineNo("1");
							c.setSbDate(sbCargo == null ? null : sbCargo.getSbDate());
							c.setCartingTransId(HoldNextIdD1);
							c.setCartingTransDate(new Date());
							c.setGateInId(conCarting.getGateInId());
							c.setGateInDate(conCarting.getGateInDate());
							c.setShift("Day");
							c.setVehicleNo(conCarting.getVehicleNo());
							c.setOnAccountOf(conCarting.getOnAccountOf());
							c.setInvoiceType("EXP");
							c.setGateInType("PortRn");
							c.setAreaOccupied((c.getAreaOccupied() == null ? BigDecimal.ZERO : c.getAreaOccupied())
									.add(c.getYardArea()));
							c.setYardPackages((c.getYardPackages() == null ? BigDecimal.ZERO : c.getYardPackages())
									.add(c.getYardPack()));

							exportCartingRepo.save(c);

							YardBlockCell cell = yardblockcellrepo.getAllData(cid, bid, c.getGridLocation(),
									c.getGridBlock(), c.getGridCellNo());

							if (cell != null) {
								cell.setCellAreaUsed(
										(cell.getCellAreaUsed() == null ? BigDecimal.ZERO : cell.getCellAreaUsed())
												.add(c.getYardArea()));
								yardblockcellrepo.save(cell);
							}

							Impexpgrid grid = new Impexpgrid();
							grid.setCompanyId(cid);
							grid.setBranchId(bid);
							grid.setFinYear(helperMethods.getFinancialYear());
							grid.setProcessTransId(HoldNextIdD1);
							grid.setLineNo(sr);
							grid.setSubSrNo(1);
							grid.setYardLocation(c.getGridLocation());
							grid.setYardBlock(c.getGridBlock());
							grid.setBlockCellNo(c.getGridCellNo());
							grid.setYardPackages(c.getYardPack().intValue());
							grid.setTransType("EXP");
							grid.setAreaReleased(BigDecimal.ZERO);
							grid.setCellArea(cell.getCellArea());
							grid.setCellAreaAllocated(c.getYardArea());
							grid.setCellAreaUsed(cell.getCellAreaUsed());

							grid.setCreatedBy(user);
							grid.setCreatedDate(new Date());

							grid.setApprovedBy(user);
							grid.setApprovedDate(new Date());
							grid.setStatus("A");

							impexpgridrepo.save(grid);
							
							GateIn gate1 = gateInRepo.getData(cid, bid, conCarting.getGateInId(), c.getSbTransId(), c.getSbNo());
							
							if(gate1 != null) {
					
								gate1.setReworkId(HoldNextIdD1);

								gateInRepo.save(gate1);
							}
						

							sr++;

						}

						conCarting.setCompanyId(cid);
						conCarting.setBranchId(bid);
						conCarting.setStatus("N");
						conCarting.setCreatedBy(user);
						conCarting.setCreatedDate(new Date());
						conCarting.setDeStuffId(HoldNextIdD1);
						conCarting.setDeStuffDate(new Date());
						conCarting.setProfitCentreId("N00004");
						conCarting.setInvoiceType("EXP");
						conCarting.setGateInType("PortRn");
						conCarting.setShift("Day");

						exportContainerCartingRepo.save(conCarting);

//						ExportInventory inv = exportinvrepo.getDataByContainerNoAndGateIn(cid, bid,
//								conCarting.getContainerNo(), conCarting.getGateInId());
	//
//						if (inv != null) {
//							inv.setStuffReqDate(null);
//							inv.setStuffReqEditedBy("");
//							inv.setStuffReqEditedDate(null);
//							inv.setStuffReqId("");
//							inv.setStuffTallyDate(null);
//							inv.setStuffTallyEditedBy("");
//							inv.setStuffTallyEditedDate(null);
//							inv.setStuffTallyId("");
	//
//							exportinvrepo.save(inv);
//						}

		

						processnextidrepo.updateAuditTrail(cid, bid, "P05090", HoldNextIdD1, "2024");

						Map<String, Object> result = new HashMap<>();
						ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid,
								bid, conCarting.getDeStuffId());

						result.put("conCarting", resultContainerCarting);

						List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid,
								conCarting.getDeStuffId());

						result.put("carting", resultCarting);

						return new ResponseEntity<>(result, HttpStatus.OK);

					} else {

						ExportContainerCarting existingConCarting = exportContainerCartingRepo.getDataBuDestuffId(cid, bid,
								conCarting.getDeStuffId());

						if (existingConCarting != null) {
							existingConCarting.setEditedBy(user);
							existingConCarting.setEditedDate(new Date());
							// existingConCarting.setContainerSearchType(conCarting.getContainerSearchType());
							existingConCarting.setSaSealNo(conCarting.getSaSealNo());
							existingConCarting.setOnAccountOf(conCarting.getOnAccountOf());
							existingConCarting.setFromDate(conCarting.getFromDate());
							existingConCarting.setToDate(conCarting.getToDate());
							existingConCarting.setRemark(conCarting.getRemark());

							exportContainerCartingRepo.save(existingConCarting);
						}

						for (ExportCarting c : carting) {
							ExportCarting exist = exportCartingRepo.getDataByCartingTransIdSBTransAndSb(cid, bid,
									conCarting.getDeStuffId(), c.getSbTransId(), c.getSbNo());

							ExportSbCargoEntry sbCargo = exportSbCargoEntryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
									c.getSbNo(), c.getSbTransId());

							if (sbCargo == null) {
								return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
							}

							if (exist != null) {

								exist.setEditedBy(user);
								exist.setEditedDate(new Date());
								exist.setActualNoOfPackages(c.getActualNoOfPackages());
								exist.setActualNoOfWeight(c.getActualNoOfWeight());
								exist.setOnAccountOf(conCarting.getOnAccountOf());

								exportCartingRepo.save(exist);
							}
						}

						Map<String, Object> result = new HashMap<>();
						ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid,
								bid, conCarting.getDeStuffId());

						result.put("conCarting", resultContainerCarting);

						List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid,
								conCarting.getDeStuffId());

						result.put("carting", resultCarting);

						return new ResponseEntity<>(result, HttpStatus.OK);
					}

				}
			} else {
				
				if ("Normal Container".equals(conCarting.getContainerSearchType())
						|| "Buffer Container".equals(conCarting.getContainerSearchType())) {
					ExportContainerCarting existingConCarting = exportContainerCartingRepo.getDataBuDestuffId(cid, bid,
							conCarting.getDeStuffId());

					if (existingConCarting != null) {
						existingConCarting.setApprovedBy(user);
						existingConCarting.setApprovedDate(new Date());
						// existingConCarting.setContainerSearchType(conCarting.getContainerSearchType());
						existingConCarting.setStatus("A");
						existingConCarting.setSaSealNo(conCarting.getSaSealNo());
						existingConCarting.setOnAccountOf(conCarting.getOnAccountOf());
						existingConCarting.setFromDate(conCarting.getFromDate());
						existingConCarting.setToDate(conCarting.getToDate());
						existingConCarting.setRemark(conCarting.getRemark());

						exportContainerCartingRepo.save(existingConCarting);
					}

					for (ExportCarting c : carting) {
						ExportCarting exist = exportCartingRepo.getDataByCartingTransIdSBTransAndSb(cid, bid,
								conCarting.getDeStuffId(), c.getSbTransId(), c.getSbNo());

						ExportSbCargoEntry sbCargo = exportSbCargoEntryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
								c.getSbNo(), c.getSbTransId());

						if (sbCargo == null) {
							return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
						}

						if (exist != null) {

							
								if ("Normal Container".equals(conCarting.getContainerSearchType())) {
									sbCargo.setStuffReqQty((sbCargo.getStuffReqQty() + c.getActualNoOfPackages().intValue())
											- exist.getActualNoOfPackages().intValue());

								}
								sbCargo.setStuffedQty((sbCargo.getStuffedQty().add(c.getActualNoOfPackages()))
										.subtract(exist.getActualNoOfPackages()));

								exportSbCargoEntryRepo.save(sbCargo);

							

							exist.setApprovedBy(user);
							exist.setApprovedDate(new Date());
							exist.setActualNoOfPackages(c.getActualNoOfPackages());
							exist.setActualNoOfWeight(c.getActualNoOfWeight());
							exist.setOnAccountOf(conCarting.getOnAccountOf());

							exportCartingRepo.save(exist);
						}
					}

					Map<String, Object> result = new HashMap<>();
					ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid, bid,
							conCarting.getDeStuffId());

					result.put("conCarting", resultContainerCarting);

					List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid,
							conCarting.getDeStuffId());

					result.put("carting", resultCarting);

					return new ResponseEntity<>(result, HttpStatus.OK);
				}
				else {
					ExportContainerCarting existingConCarting = exportContainerCartingRepo.getDataBuDestuffId(cid, bid,
							conCarting.getDeStuffId());

					if (existingConCarting != null) {
						existingConCarting.setApprovedBy(user);
						existingConCarting.setApprovedDate(new Date());
						// existingConCarting.setContainerSearchType(conCarting.getContainerSearchType());
						existingConCarting.setStatus("A");
						existingConCarting.setSaSealNo(conCarting.getSaSealNo());
						existingConCarting.setOnAccountOf(conCarting.getOnAccountOf());
						existingConCarting.setFromDate(conCarting.getFromDate());
						existingConCarting.setToDate(conCarting.getToDate());
						existingConCarting.setRemark(conCarting.getRemark());

						exportContainerCartingRepo.save(existingConCarting);
					}

					for (ExportCarting c : carting) {
						ExportCarting exist = exportCartingRepo.getDataByCartingTransIdSBTransAndSb(cid, bid,
								conCarting.getDeStuffId(), c.getSbTransId(), c.getSbNo());

						ExportSbCargoEntry sbCargo = exportSbCargoEntryRepo.getExportSbEntryBySbNoAndSbTransId(cid, bid,
								c.getSbNo(), c.getSbTransId());

						if (sbCargo == null) {
							return new ResponseEntity<>("SB data not found", HttpStatus.CONFLICT);
						}

						if (exist != null) {

						

							

							exist.setApprovedBy(user);
							exist.setApprovedDate(new Date());
							exist.setActualNoOfPackages(c.getActualNoOfPackages());
							exist.setActualNoOfWeight(c.getActualNoOfWeight());
							exist.setOnAccountOf(conCarting.getOnAccountOf());

							exportCartingRepo.save(exist);
						}
					}

					Map<String, Object> result = new HashMap<>();
					ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid, bid,
							conCarting.getDeStuffId());

					result.put("conCarting", resultContainerCarting);

					List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid,
							conCarting.getDeStuffId());

					result.put("carting", resultCarting);

					return new ResponseEntity<>(result, HttpStatus.OK);
				}

				

			}

		}

		@GetMapping("/searchReworking")
		public ResponseEntity<?> search(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam(name = "val", required = false) String val) {

			List<Object[]> data = exportContainerCartingRepo.search(cid, bid, val);

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

		@GetMapping("/getSearchedReworkingData")
		public ResponseEntity<?> getSearchedReworkingData(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("val") String val) {

			Map<String, Object> result = new HashMap<>();
			ExportContainerCarting resultContainerCarting = exportContainerCartingRepo.getDataBuDestuffId1(cid, bid, val);

			if (resultContainerCarting == null) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("conCarting", resultContainerCarting);

			List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid, val);

			if (resultCarting.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("carting", resultCarting);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		@GetMapping("/getGridDataForReworking")
		public ResponseEntity<?> getGridDataForReworking(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("id") String id, @RequestParam("lineid") String lineid) {

			List<Impexpgrid> data = impexpgridrepo.getAllImpExpGridCarting(cid, bid, id, lineid, "EXP");

			if (data.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			return new ResponseEntity<>(data, HttpStatus.OK);
		}

		@PostMapping("/saveyardPackForReworking")
		public ResponseEntity<?> saveYardPackages(@RequestParam("cid") String cid, @RequestParam("bid") String bid,
				@RequestParam("user") String user, @RequestBody List<Impexpgrid> data) {

			ExportCarting carting = exportCartingRepo.getDataByLineNoAndCartingTransId(cid, bid,
					data.get(0).getProcessTransId(), String.valueOf(data.get(0).getLineNo()));

			if (carting == null) {
				return new ResponseEntity<>("Carting data not found", HttpStatus.CONFLICT);
			}

			data.stream().forEach(c -> {
				if (!c.getProcessTransId().isEmpty()) {
					Impexpgrid existData = impexpgridrepo.getImpexpgridSubLineNo1(cid, bid, c.getProcessTransId(),
							c.getLineNo(), c.getSubSrNo());

					if (existData != null) {
						if (carting.getGridLocation().equals(existData.getYardLocation())
								&& carting.getGridBlock().equals(existData.getYardBlock())
								&& carting.getGridCellNo().equals(existData.getBlockCellNo())) {
							carting.setGridLocation(c.getYardLocation());
							carting.setGridBlock(c.getYardBlock());
							carting.setGridCellNo(c.getBlockCellNo());
						}

						existData.setYardLocation(c.getYardLocation());
						existData.setYardBlock(c.getYardBlock());
						existData.setBlockCellNo(c.getBlockCellNo());
						existData.setCellArea(c.getCellArea());
						existData.setCellAreaUsed(c.getCellAreaUsed());
						existData.setCellAreaAllocated(c.getCellAreaAllocated());
						existData.setYardPackages(c.getYardPackages());
						existData.setEditedBy(user);
						existData.setEditedDate(new Date());

						impexpgridrepo.save(existData);
					}
				} else {

					int sr = impexpgridrepo.getMaxSubSrNo(cid, bid, carting.getCartingTransId(),
							Integer.parseInt(carting.getCartingLineId()));

					c.setCompanyId(cid);
					c.setBranchId(bid);
					c.setFinYear(helperMethods.getFinancialYear());
					c.setProcessTransId(carting.getCartingTransId());
					c.setLineNo(Integer.parseInt(carting.getCartingLineId()));
					c.setSubSrNo(sr + 1);
					c.setTransType("EXP");
					c.setAreaReleased(BigDecimal.ZERO);
					c.setCreatedBy(user);
					c.setCreatedDate(new Date());
					c.setApprovedBy(user);
					c.setApprovedDate(new Date());
					c.setStatus("A");

					impexpgridrepo.save(c);
				}
			});

			int total = data.stream().mapToInt(Impexpgrid::getYardPackages).sum();

			BigDecimal totalYardArea = data.stream()
					.map(item -> item.getCellAreaAllocated() != null ? item.getCellAreaAllocated() : BigDecimal.ZERO) // Handle
																														// nulls
					.reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up the values

			System.out.println("totalYardArea " + totalYardArea);
			carting.setYardPackages(new BigDecimal(total));
			carting.setAreaOccupied(totalYardArea);

			exportCartingRepo.save(carting);

			Map<String, Object> result = new HashMap<>();

			List<Impexpgrid> data1 = impexpgridrepo.getAllImpExpGridCarting(cid, bid, carting.getCartingTransId(),
					carting.getCartingLineId(), "EXP");

			if (data1.isEmpty()) {
				return new ResponseEntity<>("Grid data not found", HttpStatus.CONFLICT);
			}

			result.put("gridData", data1);

			List<Object[]> resultCarting = exportCartingRepo.getDataByCartingTransId1(cid, bid,
					carting.getCartingTransId());

			if (resultCarting.isEmpty()) {
				return new ResponseEntity<>("Data not found", HttpStatus.CONFLICT);
			}

			result.put("carting", resultCarting);

			return new ResponseEntity<>(result, HttpStatus.OK);
		}

}
