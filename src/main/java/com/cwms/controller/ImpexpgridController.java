package com.cwms.controller;

import java.math.BigDecimal;
import java.util.Date;
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

import com.cwms.entities.EquipmentActivity;
import com.cwms.entities.Impexpgrid;
import com.cwms.entities.YardBlockCell;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.YardBlockCellRepository;

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
	
	
	@PostMapping("/saveImpExpGrid")
	public ResponseEntity<?> saveImpExpGrid(@RequestBody Impexpgrid impexpgrid,
			@RequestParam("userId") String userId) {
		try {

			System.out.println(impexpgrid);

			boolean existsByYardGrid = impGridRepo.existsByYardGrid(impexpgrid.getCompanyId(),
					impexpgrid.getBranchId(), impexpgrid.getYardLocation(),
					 impexpgrid.getYardBlock(),  impexpgrid.getBlockCellNo(), impexpgrid.getProcessTransId(),
					 impexpgrid.getLineNo(), impexpgrid.getSubSrNo()); 

			System.out.println("existsByYardGrid : " + existsByYardGrid);
			if (existsByYardGrid) {
				String errorMessage = "Duplicate Yard Cell found for Line No : " + impexpgrid.getLineNo()
						+ " and Yard Cell : " + impexpgrid.getYardLocation() + ""+ impexpgrid.getYardBlock() + "" + impexpgrid.getBlockCellNo();
				return ResponseEntity.badRequest().body(errorMessage);
			}
//	    	
			Impexpgrid sendImpexpgrid = new Impexpgrid();

			Impexpgrid ExistImpexpgrid = impGridRepo.getImpexpgridSubLineNo(impexpgrid.getCompanyId(),
					impexpgrid.getBranchId(), impexpgrid.getYardLocation(),
					 impexpgrid.getYardBlock(),  impexpgrid.getBlockCellNo(), impexpgrid.getProcessTransId(),
					 impexpgrid.getLineNo(), impexpgrid.getSubSrNo());

			if (ExistImpexpgrid != null) {				

				ExistImpexpgrid.setCellAreaUsed(impexpgrid.getCellAreaUsed());
				ExistImpexpgrid.setCellAreaAllocated(impexpgrid.getCellAreaAllocated());
				ExistImpexpgrid.setYardPackages(impexpgrid.getYardPackages());

				ExistImpexpgrid.setEditedBy(userId);
				ExistImpexpgrid.setEditedDate(new Date());
				sendImpexpgrid = impGridRepo.save(ExistImpexpgrid);	
				
				// Get allocated cell area, treating null as BigDecimal.ZERO
				BigDecimal allocatedExist = ExistImpexpgrid.getCellAreaAllocated() != null ? ExistImpexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;
				BigDecimal allocatedNew = impexpgrid.getCellAreaAllocated() != null ? impexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;

				// Calculate the difference
				BigDecimal Difference = allocatedExist.subtract(allocatedNew);

				// Retrieve the yard cell by cell number
				YardBlockCell yardCellByCellNo = yardBlockCellRepository.getYardCellByCellNo(
				    impexpgrid.getCompanyId(),
				    impexpgrid.getBranchId(),
				    impexpgrid.getYardLocation(),
				    impexpgrid.getYardBlock(),
				    impexpgrid.getBlockCellNo()
				);

				// If the retrieved yard cell is not null, update the cellAreaUsed
				if (yardCellByCellNo != null) {
				    BigDecimal currentCellAreaUsed = yardCellByCellNo.getCellAreaUsed() != null ? yardCellByCellNo.getCellAreaUsed() : BigDecimal.ZERO;
				    yardCellByCellNo.setCellAreaUsed(currentCellAreaUsed.add(Difference));
				    yardBlockCellRepository.save(yardCellByCellNo);
				}
			
				 
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
				
				BigDecimal allocatedNew = impexpgrid.getCellAreaAllocated() != null ? impexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;				
				YardBlockCell yardCellByCellNo = yardBlockCellRepository.getYardCellByCellNo(impexpgrid.getCompanyId(), impexpgrid.getBranchId(), impexpgrid.getYardLocation(), impexpgrid.getYardBlock(),  impexpgrid.getBlockCellNo());
							
				if (yardCellByCellNo != null) {
				    BigDecimal currentCellAreaUsed = yardCellByCellNo.getCellAreaUsed() != null ? yardCellByCellNo.getCellAreaUsed() : BigDecimal.ZERO;
				    yardCellByCellNo.setCellAreaUsed(currentCellAreaUsed.add(allocatedNew));
				    yardBlockCellRepository.save(yardCellByCellNo);
				    yardBlockCellRepository.save(yardCellByCellNo);
				}	
			}
			System.out.println(sendImpexpgrid);
			return ResponseEntity.ok(sendImpexpgrid);
		} catch (Exception e) {
			System.out.println(e);
			// Return an appropriate error response
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking duplicate SB No.");
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
