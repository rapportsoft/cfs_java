package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.GateIn;
import com.cwms.entities.Impexpgrid;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.Impexpgridrepo;
import com.cwms.repository.VehicleTrackRepository;

@Service
public class ExportCartingService {
	
	@Autowired
	public ExportCartingRepo cartingRepo;
	
	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private HelperMethods helperMethods;
	
	@Autowired
	private GateInRepository gateInRepo;	
	
	@Autowired
	public Impexpgridrepo impGridRepo;
	
	@Autowired
	private VehicleTrackRepository vehicleRepo;
	
	@Autowired
	private ExportEntryService exportSbService;	
	
	public ResponseEntity<?> getSelectedCartingEntry(String companyId, String branchId, String cartingTransId, String cartingLineId, String sbNo,String profitCenterId)
	{		
		List<ExportCarting> selectedGateInEntry = cartingRepo.getSelectedCartingEntry(companyId, branchId, profitCenterId, cartingTransId, "EXP");
		return ResponseEntity.ok(selectedGateInEntry);
	}
	
	
	public List<Object[]> getCartingEntriesToSelect(String companyId, String branchId, String searchValue)
	{				
		return cartingRepo.getCartingEntriesData(companyId, branchId, searchValue);
	}
	
	
	
//	@Transactional
//	public ResponseEntity<?> addExportCarting(String companyId, String branchId, List<ExportCarting> cartingList, String User)
//	{
//		Date currentDate = new Date();	
//		String autoCartingTransId = "";
//		boolean isUpdate = false;
//		
//		
//		List<ExportCarting> cartingListToSend = new ArrayList<>();
//
//		ExportCarting firstCarting = null;
//		if (cartingList != null && !cartingList.isEmpty()) {
//		    firstCarting = cartingList.get(0);
//
//		    // Check if CartingTransId exists
//		    if (firstCarting.getCartingTransId() != null && !firstCarting.getCartingTransId().trim().isEmpty()) {
//		        isUpdate = true;
//		        autoCartingTransId = firstCarting.getCartingTransId();
//		    } else {
//		        autoCartingTransId = processService.autoCartingTransId(companyId, branchId, "P00103");
//		    }
//		} 
//		
//		for(ExportCarting cartingEntry : cartingList)
//		{
//						
//			GateIn gateInByIds = gateInRepo.getGateInByIds(companyId, branchId, cartingEntry.getProfitcentreId(), cartingEntry.getGateInId(), cartingEntry.getSbTransId(), cartingEntry.getSbNo(), "EXP");
//			
//			 ExportSbCargoEntry exportSbCargoEntry = exportSbService.getExportSbCargoEntry(companyId, branchId, gateInByIds.getDocRefNo(), gateInByIds.getErpDocRefNo());
//
//			
////			For Update
//			if(isUpdate)
//			{
//				ExportCarting existing = cartingRepo.getCartingByIds(cartingEntry.getCompanyId(), cartingEntry.getBranchId(), cartingEntry.getProfitcentreId(), cartingEntry.getGateInId(), cartingEntry.getCartingTransId(), cartingEntry.getSbTransId(), cartingEntry.getSbNo());
//				
//				
//				existing.setGridBlock(cartingEntry.getGridBlock());
//				existing.setGridCellNo(cartingEntry.getGridCellNo());
//				existing.setGridLocation(cartingEntry.getGridLocation());
//				existing.setDamageComments(cartingEntry.getDamageComments());
//				
//								
//				BigDecimal ExistingPackages = existing.getActualNoOfPackages().add(existing.getShortagePackages()).subtract(existing.getExcessPackages());
//				BigDecimal newPackages = cartingEntry.getActualNoOfPackages().add(cartingEntry.getShortagePackages()).subtract(cartingEntry.getExcessPackages());
//
//				BigDecimal difference = ExistingPackages.subtract(newPackages);
//				
//				System.out.println("difference "+difference + "\n ExistingPackages :  "+ ExistingPackages + " \n newPackages "+newPackages);
//				
//				gateInByIds.setCartedPackages(gateInByIds.getCartedPackages().add(difference));
//				exportSbCargoEntry.setCartedPackages(exportSbCargoEntry.getCartedPackages().add(difference));
//				
//				
//				ExportCarting save = cartingRepo.save(existing);
//				cartingListToSend.add(save);
//				
//				
//				existing.setAreaOccupied(cartingEntry.getAreaOccupied());
//				existing.setYardPackages(cartingEntry.getYardPackages());
//				existing.setActualNoOfPackages(cartingEntry.getActualNoOfPackages());
//				existing.setExcessPackages(cartingEntry.getExcessPackages());
//				existing.setShortagePackages(cartingEntry.getShortagePackages());
//
//			}
////			Add
//			else
//			{
//				
//				gateInByIds.setCartingStatus("Y");
//				gateInByIds.setCartedPackages(
//					    gateInByIds.getCartedPackages().add(
//					        cartingEntry.getActualNoOfPackages()
//					            .add(cartingEntry.getShortagePackages())
//					            .subtract(cartingEntry.getExcessPackages())
//					    )
//					);
//				
//				
//				exportSbCargoEntry.setCartedPackages(exportSbCargoEntry.getCartedPackages()
//						.add(cartingEntry.getActualNoOfPackages()
//			            .add(cartingEntry.getShortagePackages())
//			            .subtract(cartingEntry.getExcessPackages())));
//				
//				gateInByIds.setCartingTransId(autoCartingTransId);
//				
//				
//				cartingEntry.setCartingTransId(autoCartingTransId);
//                cartingEntry.setFinYear(helperMethods.getFinancialYear());				
//				cartingEntry.setCreatedBy(User);
//				cartingEntry.setCreatedDate(currentDate);	
//				cartingEntry.setApprovedBy(User);
//				cartingEntry.setApprovedDate(currentDate);
//				cartingEntry.setStatus("A");	
//				ExportCarting save = cartingRepo.save(cartingEntry);
//				cartingListToSend.add(save);
//				
//				Impexpgrid grid = new Impexpgrid();
//				grid.setCompanyId(companyId);
//				grid.setBranchId(branchId);
//				grid.setFinYear(helperMethods.getFinancialYear());
//				grid.setProcessTransId(autoCartingTransId);
//				grid.setLineNo(Integer.parseInt(cartingEntry.getCartingLineId()));
//				grid.setSubSrNo(1);
//				grid.setYardLocation(cartingEntry.getGridBlock());
//				grid.setYardBlock(cartingEntry.getGridBlock());
//				grid.setBlockCellNo(cartingEntry.getGridBlock());
//				grid.setYardPackages(cartingEntry.getYardPackages().intValue());				
//				grid.setTransType("EXP");
//				grid.setAreaReleased(cartingEntry.getAreaOccupied());
//				grid.setCellArea(cartingEntry.getAreaOccupied());
//				grid.setCellAreaAllocated(cartingEntry.getAreaOccupied());
//				grid.setCellAreaUsed(cartingEntry.getAreaOccupied());
//				
//				grid.setCreatedBy(User);
//				grid.setCreatedDate(currentDate);
//				grid.setEditedBy(User);
//				grid.setEditedDate(currentDate);
//				grid.setApprovedBy(User);
//				grid.setApprovedDate(currentDate);
//				grid.setStatus("A");	
//				
//				impGridRepo.save(grid);
//				
//				
//				
//				int updateGateOutExport = vehicleRepo.updateGateOutExport(companyId, branchId, cartingEntry.getGateInId(), cartingEntry.getCartingTransId(), cartingEntry.getCartingTransDate(),User, new Date());
//			
//				System.out.println("updateGateOutExport : " + updateGateOutExport);
//				
//				
//			}
//			
//			
//			gateInRepo.save(gateInByIds);
//			exportSbService.saveExportSbCargoEntry(exportSbCargoEntry);
//			
//		}
//		
//		return ResponseEntity.ok(cartingListToSend);
//	}
	
	
	@Transactional
	public ResponseEntity<?> addExportCarting(String companyId, String branchId, List<ExportCarting> cartingList, String User, String status)
	{
		Date currentDate = new Date();	
		String autoCartingTransId = "";
		boolean isUpdate = false;
		
		
		List<ExportCarting> cartingListToSend = new ArrayList<>();

		ExportCarting firstCarting = null;
		if (cartingList != null && !cartingList.isEmpty()) {
		    firstCarting = cartingList.get(0);

		    // Check if CartingTransId exists
		    if (firstCarting.getCartingTransId() != null && !firstCarting.getCartingTransId().trim().isEmpty()) {
		        isUpdate = true;
		        autoCartingTransId = firstCarting.getCartingTransId();
		    } else {
		        autoCartingTransId = processService.autoCartingTransId(companyId, branchId, "P00103");
		    }
		} 
		
		for(ExportCarting cartingEntry : cartingList)
		{
						
			GateIn gateInByIds = gateInRepo.getGateInByIds(companyId, branchId, cartingEntry.getProfitcentreId(), cartingEntry.getGateInId(), cartingEntry.getSbTransId(), cartingEntry.getSbNo(), "EXP");
			
			 ExportSbCargoEntry exportSbCargoEntry = exportSbService.getExportSbCargoEntry(companyId, branchId, gateInByIds.getDocRefNo(), gateInByIds.getErpDocRefNo());

			
//			For Update
			if(isUpdate)
			{
				ExportCarting existing = cartingRepo.getCartingByIds(cartingEntry.getCompanyId(), cartingEntry.getBranchId(), cartingEntry.getProfitcentreId(), cartingEntry.getGateInId(), cartingEntry.getCartingTransId(), cartingEntry.getSbTransId(), cartingEntry.getSbNo());
				
				
				existing.setGridBlock(cartingEntry.getGridBlock());
				existing.setGridCellNo(cartingEntry.getGridCellNo());
				existing.setGridLocation(cartingEntry.getGridLocation());
				existing.setDamageComments(cartingEntry.getDamageComments());
				
								
				BigDecimal ExistingPackages = existing.getActualNoOfPackages().add(existing.getShortagePackages()).subtract(existing.getExcessPackages());
				BigDecimal newPackages = cartingEntry.getActualNoOfPackages().add(cartingEntry.getShortagePackages()).subtract(cartingEntry.getExcessPackages());

				BigDecimal difference = ExistingPackages.subtract(newPackages);
				
//				System.out.println("difference "+difference + "\n ExistingPackages :  "+ ExistingPackages + " \n newPackages "+newPackages);
				
				gateInByIds.setCartedPackages(gateInByIds.getCartedPackages().add(difference));
				exportSbCargoEntry.setCartedPackages(exportSbCargoEntry.getCartedPackages().add(difference));
				
				
				existing.setAreaOccupied(cartingEntry.getAreaOccupied());
				existing.setYardPackages(cartingEntry.getYardPackages());
				existing.setActualNoOfPackages(cartingEntry.getActualNoOfPackages());
				existing.setExcessPackages(cartingEntry.getExcessPackages());
				existing.setShortagePackages(cartingEntry.getShortagePackages());
				
				existing.setStatus(status.equals("A") ? status : existing.getStatus());
				
				System.out.println( "status "+ status + " getApprovedBy \n" + existing.getApprovedBy());
				if (existing.getApprovedBy() == null || existing.getApprovedBy().trim().isEmpty()) {
				    System.out.println(" IN ");
				    existing.setApprovedBy(status.equals("A") ? User : existing.getApprovedBy());
				}

				
				System.out.println(" AFTER "+existing.getApprovedBy());
				ExportCarting save = cartingRepo.save(existing);
				cartingListToSend.add(save);
				
				
				

			}
//			Add
			else
			{
				
				gateInByIds.setCartingStatus("Y");
				gateInByIds.setCartedPackages(
					    gateInByIds.getCartedPackages().add(
					        cartingEntry.getActualNoOfPackages()
					            .add(cartingEntry.getShortagePackages())
					            .subtract(cartingEntry.getExcessPackages())
					    )
					);
				
				
				exportSbCargoEntry.setCartedPackages(exportSbCargoEntry.getCartedPackages()
						.add(cartingEntry.getActualNoOfPackages()
			            .add(cartingEntry.getShortagePackages())
			            .subtract(cartingEntry.getExcessPackages())));
				
				gateInByIds.setCartingTransId(autoCartingTransId);
				
				
				cartingEntry.setCartingTransId(autoCartingTransId);
                cartingEntry.setFinYear(helperMethods.getFinancialYear());				
				cartingEntry.setCreatedBy(User);
				cartingEntry.setCreatedDate(currentDate);	
				cartingEntry.setApprovedBy(status.equals("A") ? User : " ");
				cartingEntry.setApprovedDate(currentDate);
//				cartingEntry.setStatus("A");	
				
				if (cartingEntry.getApprovedBy() == null || cartingEntry.getApprovedBy().trim().isEmpty()) {
				    System.out.println(" IN ");
				    cartingEntry.setApprovedBy(status.equals("A") ? User : cartingEntry.getApprovedBy());
				}
				
				cartingEntry.setStatus(status.equals("A") ? status : "N");
				ExportCarting save = cartingRepo.save(cartingEntry);
				cartingListToSend.add(save);
				
				Impexpgrid grid = new Impexpgrid();
				grid.setCompanyId(companyId);
				grid.setBranchId(branchId);
				grid.setFinYear(helperMethods.getFinancialYear());
				grid.setProcessTransId(autoCartingTransId);
				grid.setLineNo(Integer.parseInt(cartingEntry.getCartingLineId()));
				grid.setSubSrNo(1);
				grid.setYardLocation(cartingEntry.getGridBlock());
				grid.setYardBlock(cartingEntry.getGridBlock());
				grid.setBlockCellNo(cartingEntry.getGridBlock());
				grid.setYardPackages(cartingEntry.getYardPackages().intValue());				
				grid.setTransType("EXP");
				grid.setAreaReleased(BigDecimal.ZERO);
				grid.setCellArea(cartingEntry.getAreaOccupied());
				grid.setCellAreaAllocated(cartingEntry.getAreaOccupied());
				grid.setCellAreaUsed(new BigDecimal("0"));
				
				grid.setCreatedBy(User);
				grid.setCreatedDate(currentDate);
				grid.setEditedBy(User);
				grid.setEditedDate(currentDate);
				grid.setApprovedBy(User);
				grid.setApprovedDate(currentDate);
				grid.setStatus("A");	
				
				impGridRepo.save(grid);
				
				
				
				int updateGateOutExport = vehicleRepo.updateGateOutExport(companyId, branchId, cartingEntry.getGateInId(), cartingEntry.getCartingTransId(), cartingEntry.getCartingTransDate(),User, new Date());
			
				System.out.println("updateGateOutExport : " + updateGateOutExport);
				
				
			}
			
			
			gateInRepo.save(gateInByIds);
			exportSbService.saveExportSbCargoEntry(exportSbCargoEntry);
			
		}
ExportCarting exportCarting = cartingListToSend.get(0);
		
		List<ExportCarting> selectedGateInEntry = cartingRepo.getSelectedCartingEntry(companyId, branchId, exportCarting.getProfitcentreId(), exportCarting.getCartingTransId(), "EXP");

		return ResponseEntity.ok(selectedGateInEntry);
	}
	
	
	
}
