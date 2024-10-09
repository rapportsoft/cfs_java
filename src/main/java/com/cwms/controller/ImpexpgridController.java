package com.cwms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.cwms.entities.EquipmentActivity;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.Impexpgrid;
import com.cwms.entities.YardBlockCell;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/impGrid")
public class ImpexpgridController {
	
	@Autowired
	public Impexpgridrepo impGridRepo;
	
	@Autowired
	private HelperMethods helperMethods;
	
	@Autowired
	private YardBlockCellRepository yardBlockCellRepository;
	
	@Autowired
	public ExportCartingRepo cartingRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@PostMapping("/saveImpExpGrid")
	public ResponseEntity<?> saveImpExpGrid(
	                                         @RequestParam("userId") String userId,	                                         
	                                         @RequestBody Map<String, Object> requestData
	                                        ) {
	    List<Impexpgrid> savedImpexpgrids = new ArrayList<>();
	    try {
	    		    	
	    	List<Impexpgrid> impexpgrids = objectMapper.convertValue(requestData.get("yardGrid"),
					new TypeReference<List<Impexpgrid>>() {
					});
	    	ExportCarting cartingObject = objectMapper.convertValue(requestData.get("cartingObject"), ExportCarting.class);
	    			
	    	
	    	Set<String> uniqueEntries = new HashSet<>();
	        Map<String, List<Integer>> errorYardLocations = new HashMap<>();

	        for (Impexpgrid impexpgrid : impexpgrids) {
	            // Create a unique key based on relevant fields
	            String uniqueKey = impexpgrid.getCompanyId() + "|" +
	                    impexpgrid.getBranchId() + "|" +
	                    impexpgrid.getYardLocation() + "|" +
	                    impexpgrid.getYardBlock() + "|" +
	                    impexpgrid.getBlockCellNo() + "|" +
	                    impexpgrid.getProcessTransId() + "|" +
	                    impexpgrid.getLineNo() + "|" +
	                    impexpgrid.getSubSrNo();

	            // Check for existence in the database
	            boolean existsInDatabase = impGridRepo.existsByYardGrid(
	                impexpgrid.getCompanyId(),
	                impexpgrid.getBranchId(),
	                impexpgrid.getYardLocation(),
	                impexpgrid.getYardBlock(),
	                impexpgrid.getBlockCellNo(),
	                impexpgrid.getProcessTransId(),
	                impexpgrid.getLineNo(),
	                impexpgrid.getSubSrNo()
	            );

	            // If it exists in the database, add to errors
	            if (existsInDatabase) {
	                errorYardLocations
	                    .computeIfAbsent("Duplicate in DB", k -> new ArrayList<>())
	                    .add(impexpgrid.getSubSrNo());
	            }

	            // Check for duplicates in the list
	            if (!uniqueEntries.add(uniqueKey)) {
	                errorYardLocations
	                    .computeIfAbsent("Duplicate in List", k -> new ArrayList<>())
	                    .add(impexpgrid.getSubSrNo());
	            }
	        }

	        if (!errorYardLocations.isEmpty()) {
	            // Return a response with error yard locations
	            return ResponseEntity.badRequest().body(errorYardLocations);
	        }

	    	
//	    	 System.out.println("cartingObject /n" +cartingObject);
	        for (Impexpgrid impexpgrid : impexpgrids) {
	            System.out.println(impexpgrid);

	            Impexpgrid sendImpexpgrid = new Impexpgrid();

	            Impexpgrid ExistImpexpgrid = impGridRepo.getImpexpgridSubLineNo(impexpgrid.getCompanyId(),
	                    impexpgrid.getBranchId(), impexpgrid.getYardLocation(),
	                    impexpgrid.getYardBlock(), impexpgrid.getBlockCellNo(),
	                    impexpgrid.getProcessTransId(), impexpgrid.getLineNo(),
	                    impexpgrid.getSubSrNo());

	            if (ExistImpexpgrid != null) {
	            	
	            	
	            	BigDecimal allocatedExist = ExistImpexpgrid.getCellAreaAllocated() != null ? ExistImpexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;
					BigDecimal allocatedNew = impexpgrid.getCellAreaAllocated() != null ? impexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;
	
					// Calculate the difference
					BigDecimal Difference = allocatedExist.subtract(allocatedNew);
	//
					int allocatedExistPackages = ExistImpexpgrid.getYardPackages();
					int allocatedNewPackages = impexpgrid.getYardPackages();
					
					System.out.println("allocatedExistPackages "+allocatedExistPackages + "allocatedNewPackages" + allocatedNewPackages );
	
					System.out.println("allocatedExist "+allocatedExist + "allocatedNew" + allocatedNew );
					
					
					 ExportCarting existing = cartingRepo.getCartingByIds(cartingObject.getCompanyId(), cartingObject.getBranchId(), cartingObject.getProfitcentreId(), cartingObject.getGateInId(), cartingObject.getCartingTransId(), cartingObject.getSbTransId(), cartingObject.getSbNo());

						
						BigDecimal yardPackages = existing.getYardPackages();
						BigDecimal areaOccupied = existing.getAreaOccupied();
						
						existing.setYardPackages(yardPackages.subtract(new BigDecimal(allocatedExistPackages)).add(new BigDecimal(allocatedNewPackages)));

						existing.setAreaOccupied(areaOccupied.subtract(allocatedExist).add(allocatedNew));

//						existing.setAreaOccupied(areaOccupied.add(allocatedArea));
						
						cartingRepo.save(existing);
	            	
				    // Update yard cell area used
	                updateYardCellArea(impexpgrid, Difference,cartingObject,allocatedExistPackages-allocatedNewPackages);
	                
	                ExistImpexpgrid.setCellAreaUsed(impexpgrid.getCellAreaUsed());
	                ExistImpexpgrid.setCellAreaAllocated(impexpgrid.getCellAreaAllocated());
	                ExistImpexpgrid.setYardPackages(impexpgrid.getYardPackages());
	                ExistImpexpgrid.setEditedBy(userId);
	                ExistImpexpgrid.setEditedDate(new Date());
	                sendImpexpgrid = impGridRepo.save(ExistImpexpgrid);

	            
	            } else {
	                String financialYear = helperMethods.getFinancialYear();
	                int maxSubSrNo = impGridRepo.getMaxSubSrNo(impexpgrid.getCompanyId(), impexpgrid.getBranchId(), impexpgrid.getProcessTransId(), impexpgrid.getLineNo());
	                
	                impexpgrid.setSubSrNo(maxSubSrNo + 1);
	                impexpgrid.setFinYear(financialYear);
	                impexpgrid.setStatus("A");
	                impexpgrid.setCreatedBy(userId);
	                impexpgrid.setEditedBy(userId);
	                impexpgrid.setEditedDate(new Date());
	                impexpgrid.setCreatedDate(new Date());
	                impexpgrid.setApprovedBy(userId);
	                impexpgrid.setApprovedDate(new Date());
	                sendImpexpgrid = impGridRepo.save(impexpgrid);
	                
	                
	                ExportCarting existing = cartingRepo.getCartingByIds(cartingObject.getCompanyId(), cartingObject.getBranchId(), cartingObject.getProfitcentreId(), cartingObject.getGateInId(), cartingObject.getCartingTransId(), cartingObject.getSbTransId(), cartingObject.getSbNo());

//	                System.out.println("existing /n" +existing);
					BigDecimal yardPackages = existing.getYardPackages();
					BigDecimal areaOccupied = existing.getAreaOccupied();
					
					existing.setYardPackages(yardPackages.add(new BigDecimal(impexpgrid.getYardPackages())));

					
					System.out.println("areaOccupied "+areaOccupied + "impexpgrid.getAreaReleased()" + impexpgrid.getCellAreaAllocated() );
					
					existing.setAreaOccupied(areaOccupied.add(impexpgrid.getCellAreaAllocated()));

	                // Update yard cell area used
	                updateYardCellArea(impexpgrid, impexpgrid.getCellAreaAllocated(),cartingObject, impexpgrid.getYardPackages());
	            }
	            savedImpexpgrids.add(sendImpexpgrid);
	        }
	        return ResponseEntity.ok(savedImpexpgrids);
	    } catch (Exception e) {
	        System.out.println(e);
	        // Return an appropriate error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("An error occurred while saving the Impexpgrid data.");
	    }
	}

	private void updateYardCellArea(Impexpgrid impexpgrid, BigDecimal allocatedArea, ExportCarting cartingEntry, int packages) {
	    YardBlockCell yardCellByCellNo = yardBlockCellRepository.getYardCellByCellNo(
	            impexpgrid.getCompanyId(),
	            impexpgrid.getBranchId(),
	            impexpgrid.getYardLocation(),
	            impexpgrid.getYardBlock(),
	            impexpgrid.getBlockCellNo()
	    );
	    if (yardCellByCellNo != null) {
	    	
	        BigDecimal currentCellAreaUsed = yardCellByCellNo.getCellAreaUsed() != null ? yardCellByCellNo.getCellAreaUsed() : BigDecimal.ZERO;
	        yardCellByCellNo.setCellAreaUsed(currentCellAreaUsed.add(allocatedArea));
	        yardBlockCellRepository.save(yardCellByCellNo);
	    }
	}

	
	
	
	
	
	
	@GetMapping("/deleteYardCell")
	public ResponseEntity<?> deleteYardCell(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("subLineNo") String subLineNo, 
			@RequestParam("cartingTransId") String cartingTransId,@RequestParam("userId") String userId,
			@RequestParam("cartingLineId") String cartingLineId) {
		try {
			Impexpgrid yardEntry = impGridRepo.getAllEquipmentsCommonCartingSubLine(companyId, branchId, cartingTransId, cartingLineId, subLineNo, "EXP");

			
			
			BigDecimal allocatedNew = yardEntry.getCellAreaAllocated() != null ? yardEntry.getCellAreaAllocated() : BigDecimal.ZERO;				
			YardBlockCell yardCellByCellNo = yardBlockCellRepository.getYardCellByCellNo(yardEntry.getCompanyId(), yardEntry.getBranchId(), yardEntry.getYardLocation(), yardEntry.getYardBlock(),  yardEntry.getBlockCellNo());
						
			if (yardCellByCellNo != null) {
			    BigDecimal currentCellAreaUsed = yardCellByCellNo.getCellAreaUsed() != null ? yardCellByCellNo.getCellAreaUsed() : BigDecimal.ZERO;
			    yardCellByCellNo.setCellAreaUsed(currentCellAreaUsed.subtract(allocatedNew));
			    yardBlockCellRepository.save(yardCellByCellNo);
			    yardBlockCellRepository.save(yardCellByCellNo);
			}	
			
			yardEntry.setEditedBy(userId);
			yardEntry.setEditedDate(new Date());
			yardEntry.setStatus("D");
			impGridRepo.save(yardEntry);
			return ResponseEntity.ok(yardEntry);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}
	
	
	@GetMapping("/getYardCellByCartingId")
	public ResponseEntity<?> getYardCellByCartingId(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId, @RequestParam("cartingTransId") String cartingTransId, @RequestParam("cartingLineId") String cartingLineId) {
		try {

			System.out.println("companyId : " + companyId + " branchId " + branchId
					+ " processId " + " cartingLineId " + cartingLineId
					+ " cartingTransId " + cartingTransId);

			List<Impexpgrid> equipMentEntries = impGridRepo.getAllImpExpGridCarting(companyId, branchId, cartingTransId, cartingLineId, "EXP");
			return ResponseEntity.ok(equipMentEntries);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	
	
	@GetMapping("/getimpexpGridBySubLine")
	public ResponseEntity<?> getSelectedGateInEntryBySubLine(@RequestParam("companyId") String companyId,
			@RequestParam("branchId") String branchId,
			@RequestParam("subLineNo") String subLineNo, @RequestParam("cartingTransId") String cartingTransId,
			@RequestParam("cartingLineId") String cartingLineId) {
		try {

			System.out.println("companyId : " + companyId + " branchId " + branchId 
					+ " subLineNo " + subLineNo + " profitcentreId " + cartingLineId
					+ " cartingTransId " + cartingTransId);

			Impexpgrid equipMentEntries = impGridRepo.getAllEquipmentsCommonCartingSubLine(companyId, branchId, cartingTransId, cartingLineId, subLineNo, "EXP");
			return ResponseEntity.ok(equipMentEntries);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
		}
	}

	
	
}
