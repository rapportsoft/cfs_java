package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.GateIn;
import com.cwms.entities.VehicleTrack;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.VehicleTrackRepository;

@Service
public class GateInService {
	
	@Autowired
	private GateInRepository gateInRepo;	
	
	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private ExportEntryService exportSbService;
	
	@Autowired
	private HelperMethods helperMethods;	
	
	@Autowired
	private VehicleTrackRepository vehicleRepo;
	
	
	private List<Map<String, String>> convertToValueLabelList(List<String> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", obj);
	        map.put("label", obj);
	        return map;
	    }).collect(Collectors.toList());
	}
	
	public ResponseEntity<?> getGateInEntryFromVehicleNo(String companyId, String branchId, String vehicleNo, String profitCenter)
	{			
		List<GateIn> gateInEntries = gateInRepo.getGateInEntryFromVehicleNo(companyId, branchId, profitCenter, vehicleNo, "EXP");
		return ResponseEntity.ok(gateInEntries);
	}
	
	public ResponseEntity<?> searchSbNosToGateIn(String companyId, String branchId, String searchValue)
	{			
		List<String> sbNos = gateInRepo.searchVehicleNosToCarting(companyId, branchId, searchValue);
		List<Map<String, String>> sbNoList = convertToValueLabelList(sbNos);		
		return ResponseEntity.ok(sbNoList);
	}
	
	
	
	
	
	
	public ResponseEntity<?> getSavedGateInRecords(String companyId, String branchId, String profitCenterId, String gateInId)
	{		
		List<GateIn> selectedGateInEntry = gateInRepo.getSavedGateInRecords(companyId, branchId, profitCenterId, gateInId, "EXP");
		return ResponseEntity.ok(selectedGateInEntry);	
	}
	
	
	
	public ResponseEntity<?> getSelectedGateInEntry(String companyId, String branchId, String sbTransId, String gateInId, String sbNo,String profitCenterId)
	{		
		List<GateIn> selectedGateInEntry = gateInRepo.getSelectedGateInEntry(companyId, branchId, profitCenterId, gateInId, "EXP");
		return ResponseEntity.ok(selectedGateInEntry);	
	}
	
	
	public List<Object[]> getGateInEntriesToSelect(String companyId, String branchId, String searchValue)
	{				
		return gateInRepo.getGateInEntriesData(companyId, branchId, searchValue);
	}
	
	
	
	@Transactional
	public ResponseEntity<?> addExportGateIn(String companyId, String branchId, List<GateIn> gateIn, String user) {
	    Date currentDate = new Date();	    
	    List<GateIn> listToSend = new ArrayList<>();
	    String financialYear = helperMethods.getFinancialYear();
	    try {
	        for (GateIn gateInLoop : gateIn) {    
	            if (gateInLoop.getGateInId() != null && !gateInLoop.getGateInId().trim().isEmpty()) {
	            	
	            	System.out.println("Here for : "+ gateInLoop.getGateInId() + " gateInLoop.getDocRefNo() "+gateInLoop.getDocRefNo());
	            	
	                GateIn gateInByIds = gateInRepo.getGateInByIds(companyId, branchId, gateInLoop.getProfitcentreId(), 
	                                                               gateInLoop.getGateInId(), gateInLoop.getErpDocRefNo(), 
	                                                               gateInLoop.getDocRefNo(), "EXP");
	            	System.out.println("gateInByIds : \n"+gateInByIds);
	            	 ExportSbEntry exportSbEntry = exportSbService.getExportSbEntry(companyId, branchId, 
	            			 gateInLoop.getDocRefNo(), 
                              gateInLoop.getErpDocRefNo());
	            	 
	            	 ExportSbCargoEntry exportSbCargoEntry = exportSbService.getExportSbCargoEntry(companyId, branchId, 
	            			 gateInLoop.getDocRefNo(), 
	            			 gateInLoop.getErpDocRefNo());

	            	
	            	if(gateInByIds != null)
	            	{
	            		
	            		BigDecimal previousPackages = gateInByIds.getQtyTakenIn();
	 	                BigDecimal currentPackages = gateInLoop.getQtyTakenIn();
	 	                BigDecimal difference = currentPackages.subtract(previousPackages);

	 	                if (difference.compareTo(BigDecimal.ZERO) != 0) {
	 	                   
	 	                    // Update entries with the difference
	 	                    exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(difference));
	 	                    exportSbCargoEntry.setGateInPackages(exportSbCargoEntry.getGateInPackages().add(difference));

	 	                    exportSbService.saveExportSbEntry(exportSbEntry);
	 	                    exportSbService.saveExportSbCargoEntry(exportSbCargoEntry);
	 	                  // gateInByIds.setGateInPackages(gateInByIds.getGateInPackages().add(difference));
	 	                }
	 	                gateInByIds.setQtyTakenIn(gateInLoop.getQtyTakenIn());
	 	                gateInByIds.setCargoWeight(gateInLoop.getCargoWeight());
	 	                gateInByIds.setRemarks(gateInLoop.getRemarks());
	 	                gateInByIds.setFob(new BigDecimal("0"));
	 	                GateIn save = gateInRepo.save(gateInByIds);
	 	                
	 	           	System.out.println("GateIn save : \n"+save);
	 	                listToSend.add(save);

	            		
	            	}else
	            	{
		            	System.out.println("Here for :  }else\r\n"
		            			+ "	            	{ "+ gateInLoop.getGateInId() + " gateInLoop.getDocRefNo() "+gateInLoop.getDocRefNo());

	            		
	            		gateInLoop.setStatus("A");
		                gateInLoop.setCreatedBy(user);
		                gateInLoop.setCreatedDate(currentDate);
		                gateInLoop.setEditedBy(user);
		                gateInLoop.setEditedDate(currentDate);
		                gateInLoop.setApprovedBy(user);
		                gateInLoop.setApprovedDate(currentDate);        
		           //     gateInLoop.setGateInPackages(gateInLoop.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
		                gateInLoop.setFinYear(financialYear);
		                
		                
		                exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
		                exportSbCargoEntry.setGateInPackages(exportSbCargoEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
		                exportSbService.saveExportSbEntry(exportSbEntry);
		                exportSbService.saveExportSbCargoEntry(exportSbCargoEntry);
		                gateInLoop.setFob(new BigDecimal("0"));
		                GateIn save = gateInRepo.save(gateInLoop);
	 	                listToSend.add(save);
	            		
	            	}
	            	
	               	            } else {
	            	
	            	System.out.println("Here for :  } else { "+ gateInLoop.getGateInId() + " gateInLoop.getDocRefNo() "+gateInLoop.getDocRefNo());

	            	String autoGateInId = processService.autoExportGateInId(companyId, branchId, "P00102");

	                ExportSbEntry exportSbEntry = exportSbService.getExportSbEntry(companyId, branchId, 
	                                                                                gateInLoop.getDocRefNo(), 
	                                                                                gateInLoop.getErpDocRefNo());
	                ExportSbCargoEntry exportSbCargoEntry = exportSbService.getExportSbCargoEntry(companyId, branchId, 
	                                                                                              gateInLoop.getDocRefNo(), 
	                                                                                              gateInLoop.getErpDocRefNo());

	                exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
	                exportSbCargoEntry.setGateInPackages(exportSbCargoEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
	                exportSbService.saveExportSbEntry(exportSbEntry);
	                exportSbService.saveExportSbCargoEntry(exportSbCargoEntry);

	              //  gateInLoop.setGateInPackages(gateInLoop.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
	                gateInLoop.setGateInId(autoGateInId);
	                gateInLoop.setStatus("A");
	                gateInLoop.setCreatedBy(user);
	                gateInLoop.setCreatedDate(currentDate);
	                gateInLoop.setEditedBy(user);
	                gateInLoop.setEditedDate(currentDate);
	                gateInLoop.setApprovedBy(user);
	                gateInLoop.setApprovedDate(currentDate);        
	                gateInLoop.setFob(new BigDecimal("0"));
	                gateInLoop.setFinYear(financialYear);
	                GateIn save = gateInRepo.save(gateInLoop);
	                listToSend.add(save);
	                
	                
	                
	                
	                VehicleTrack v = new VehicleTrack();
					v.setCompanyId(companyId);
					v.setBranchId(branchId);
					v.setFinYear(gateInLoop.getFinYear());
					v.setVehicleNo(gateInLoop.getVehicleNo());
					v.setProfitcentreId(gateInLoop.getProfitcentreId());
					v.setSrNo(1);
					v.setTransporterStatus(gateInLoop.getTransporterStatus().charAt(0));
					v.setTransporterName(gateInLoop.getTransporterName());
					v.setTransporter(gateInLoop.getTransporter());
					v.setDriverName(gateInLoop.getDriverName());					
					v.setVehicleStatus('E');
					v.setGateInId(gateInLoop.getGateInId());
					v.setGateInDate(new Date());
					v.setGateNoIn("Gate01");
					v.setShiftIn(gateInLoop.getShift());
					v.setStatus('A');
					v.setCreatedBy(user);
					v.setCreatedDate(new Date());
					v.setApprovedBy(user);
					v.setApprovedDate(new Date());
	                
	                
	                
	                vehicleRepo.save(v);
	                
	                
	                
	                
	                
	                
	                
	                
	                
	            }
	        }
	        
	        GateIn firstGateIn = listToSend.get(0);

	        List<GateIn> selectedGateInEntry = gateInRepo.getSelectedGateInEntry(companyId, branchId, firstGateIn.getProfitcentreId(), firstGateIn.getGateInId(), "EXP");

	        return ResponseEntity.ok(selectedGateInEntry);
	    } catch (Exception e) {
	        // Log the exception (consider using a logging framework like SLF4J)
	        System.out.println("An error occurred while adding export gate-in entries: " + e.getMessage());
	        
	        // Return a ResponseEntity with the error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("An error occurred while processing the gate-in entries.");
	    }
	}

}