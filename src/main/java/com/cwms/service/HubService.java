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
import com.cwms.entities.HubDocument;
import com.cwms.entities.VehicleTrack;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.HubDocumentRepo;
import com.cwms.repository.VehicleTrackRepository;

@Service
public class HubService {

	@Autowired
	private HubDocumentRepo hubRepo;
	
	@Autowired
	private HelperMethods helperMethods;	
	
	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private GateInRepository gateInRepo;	
	
	@Autowired
	private VehicleTrackRepository vehicleRepo;
	
	
	
	
	public ResponseEntity<?> getSelectedGateInEntry(String companyId, String branchId, String igmNo, String gateInId, String hubTransId,String profitCenterId)
	{		
		List<GateIn> selectedGateInEntry = hubRepo.getSelectedGateInEntry(companyId, branchId, profitCenterId, gateInId, "HUB");
		return ResponseEntity.ok(selectedGateInEntry);	
	}
	
	
	public List<Object[]> getGateInEntriesToSelect(String companyId, String branchId, String searchValue, String processId, String profitCenterId)
	{				
		return hubRepo.getGateInEntriesData(companyId, branchId, searchValue, processId, "HUB", profitCenterId);
	}
	
	
	@Transactional
	public ResponseEntity<?> addHubGateIn(String companyId, String branchId, List<GateIn> gateIn, String user) {
	    Date currentDate = new Date();	    
	    List<GateIn> listToSend = new ArrayList<>();
	    String financialYear = helperMethods.getFinancialYear();
	    try {
	        for (GateIn gateInLoop : gateIn) {    
	            if (gateInLoop.getGateInId() != null && !gateInLoop.getGateInId().trim().isEmpty()) {
	            	
	            	System.out.println("Here for : "+ gateInLoop.getGateInId() + " gateInLoop.getDocRefNo() "+gateInLoop.getDocRefNo());
	            	
	                GateIn gateInByIds = gateInRepo.getGateInByIds(companyId, branchId, gateInLoop.getProfitcentreId(), 
	                                                               gateInLoop.getGateInId(), gateInLoop.getErpDocRefNo(), 
	                                                               gateInLoop.getDocRefNo(), "HUB");
	            	System.out.println("gateInByIds : \n"+gateInByIds);
	            	HubDocument exportSbEntry = hubRepo.getSinleHubEntry(companyId, branchId, gateInLoop.getProfitcentreId(), 
	            										gateInLoop.getErpDocRefNo(), gateInLoop.getDocRefNo());
	            	 
	            		            	
	            	if(gateInByIds != null)
	            	{
	            		
	            		BigDecimal previousPackages = gateInByIds.getQtyTakenIn();
	 	                BigDecimal currentPackages = gateInLoop.getQtyTakenIn();
	 	                BigDecimal difference = currentPackages.subtract(previousPackages);

	 	                
	 	           	BigDecimal previousWeight = gateInByIds.getWeightTakenIn();
 	                BigDecimal currentWeight = gateInLoop.getWeightTakenIn();
 	                BigDecimal differenceWeight = currentWeight.subtract(previousWeight);

	 	                
	 	                if (difference.compareTo(BigDecimal.ZERO) != 0) {
	 	                   
	 	                    // Update entries with the difference
	 	                    exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(difference));
	 	                    exportSbEntry.setGateInWt(exportSbEntry.getGateInWt().add(differenceWeight));
	 	                    
	 	                   hubRepo.save(exportSbEntry);
	 	                }
	 	                
	 	                gateInByIds.setQtyTakenIn(gateInLoop.getQtyTakenIn());
	 	                gateInByIds.setGateInPackages(gateInLoop.getQtyTakenIn());
	 	                gateInByIds.setWeightTakenIn(gateInLoop.getWeightTakenIn());
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
		                gateInLoop.setGateInType("HUB");
		                
		                gateInLoop.setGateInPackages(gateInLoop.getGateInPackages());
		                gateInLoop.setFinYear(financialYear);		                
		                
		                exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
		                exportSbEntry.setGateInWt(exportSbEntry.getGateInWt().add(gateInLoop.getWeightTakenIn()));
 	                    
	 	                hubRepo.save(exportSbEntry);
		                
		                gateInLoop.setFob(new BigDecimal("0"));
		                GateIn save = gateInRepo.save(gateInLoop);
	 	                listToSend.add(save);
	            		
	            	}
	            	
	               	} 
	            
//	            First Save
	            else {
	            	

	            	String autoGateInId = processService.autoExportGateInId(companyId, branchId, "P00202");              

	               
	                HubDocument exportSbEntry = hubRepo.getSinleHubEntry(companyId, branchId, gateInLoop.getProfitcentreId(), 
							gateInLoop.getErpDocRefNo(), gateInLoop.getDocRefNo());
	                exportSbEntry.setGateInPackages(exportSbEntry.getGateInPackages().add(gateInLoop.getQtyTakenIn()));
	                exportSbEntry.setGateInWt(exportSbEntry.getGateInWt().add(gateInLoop.getWeightTakenIn()));
	                    
 	                hubRepo.save(exportSbEntry);
	                
	                
	                gateInLoop.setGateInPackages(gateInLoop.getQtyTakenIn());
	                gateInLoop.setGateInId(autoGateInId);
	                gateInLoop.setStatus("A");
	                gateInLoop.setGateInType("HUB");
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
					v.setVehicleStatus('L');
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

	        List<GateIn> selectedGateInEntry = hubRepo.getSelectedGateInEntry(companyId, branchId, firstGateIn.getProfitcentreId(), firstGateIn.getGateInId(), "HUB");

	        return ResponseEntity.ok(selectedGateInEntry);
	    } catch (Exception e) {
	        // Log the exception (consider using a logging framework like SLF4J)
	        System.out.println("An error occurred while adding export gate-in entries: " + e.getMessage());
	        
	        // Return a ResponseEntity with the error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("An error occurred while processing the gate-in entries.");
	    }
	}

	
	
	
	
	
	public ResponseEntity<?> getIgmCargoGateIn(String companyId, String branchId, String hubTransId, String gateInId, String igmNo, String profitCentreId)
	{			
		List<Object[]> igmCargoGateIn = hubRepo.getIgmCargoGateIn(companyId, branchId, hubTransId, gateInId, igmNo, profitCentreId);		
		return ResponseEntity.ok(igmCargoGateIn);
	}
	
	
	
	private List<Map<String, String>> convertToValueLabelList(List<Object[]> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", (String)obj[1]);
	        map.put("label", (String)obj[0]);
	        return map;
	    }).collect(Collectors.toList());
	}
	
	public ResponseEntity<?> searchIgmNosToGateIn(String companyId, String branchId, String profitCentreId, String searchValue, String gateInId)
	{			
		List<Object[]> igmNos = hubRepo.searchIgmNosToGateIn(companyId, branchId, profitCentreId, searchValue, gateInId);
		List<Map<String, String>> igmNoList = convertToValueLabelList(igmNos);		
		return ResponseEntity.ok(igmNoList);
	}
	
	
	
	@Transactional
	public ResponseEntity<?> addHubDocumentEntry(String companyId, String branchId, HubDocument hubEntry, String user) {
	    Date currentDate = new Date();	    
	    String financialYear = helperMethods.getFinancialYear();
	    try {
	    	
	    	
	    	boolean existsBySbNo = hubRepo.existsByIGMAndLineNo(companyId, branchId, hubEntry.getProfitCentreId(), hubEntry.getIgmNo(), hubEntry.getIgmLineNo(), hubEntry.getHubTransId());
			
			
			 if (existsBySbNo) {
		            String errorMessage = "Duplicate IGM  " + hubEntry.getIgmNo() + " and ITEM No: " + hubEntry.getIgmLineNo();
		            return ResponseEntity.badRequest().body(errorMessage);
		        }
	    	
	    	
	    	
	    	if (hubEntry.getHubTransId() == null || hubEntry.getHubTransId().trim().equals("")) {
	    	hubEntry.setCompanyId(companyId);
	    	hubEntry.setBranchId(branchId);
	    	hubEntry.setCreatedBy(user);
	    	hubEntry.setFinYear(financialYear);
	    	hubEntry.setCreatedDate(currentDate);
	    	hubEntry.setApprovedBy(user);
	    	hubEntry.setApprovedDate(currentDate);   	
	    	hubEntry.setStatus('A');
	    	
	    	String transId = processService.autoSBTransId(companyId, branchId, "P00201");
	    	hubEntry.setHubTransId(transId);	    	
	    	    	
	    	hubRepo.save(hubEntry);	    
	    	}else
	    	{
	    		
	    		 // Call the update query from the repository
	            int updatedRows = hubRepo.updateHubDocumentEntry(
	                companyId, 
	                branchId, 
	                hubEntry.getProfitCentreId(), 
	                hubEntry.getHubTransId(), 
	                user, 
	                hubEntry.getHubTransDate(),
	                hubEntry.getIgmNo(),
	                hubEntry.getIgmLineNo(),
	                hubEntry.getNoOfPackages(),
	                hubEntry.getGrossWt(),
	                hubEntry.getCargoWt(),
	                hubEntry.getCargoDescription(),
	                hubEntry.getCargoType(),
	                hubEntry.getArea(),
	                hubEntry.getImporterName(),
	                hubEntry.getImporterAddress()
	            );

	            if (updatedRows == 0) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body("No record found to update for given HubTransId.");
	            }
	    		
	    	}
	    	
	    	HubDocument selectedHubEntry = hubRepo.getSelectedHubEntry(companyId, branchId, hubEntry.getProfitCentreId(), hubEntry.getHubTransId(), hubEntry.getIgmNo());

		        return ResponseEntity.ok(selectedHubEntry);	    	
	    	
	    }catch (Exception e) {
	        System.out.println("An error occurred while adding Hub Document entry: " + e.getMessage());	        
	        // Return a ResponseEntity with the error message
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("An error occurred while processing the Hub Document Entry");
	    }
	}

	public List<HubDocument> getHubEntriesToSelect(String companyId, String branchId, String searchValue, String profitCenterId)
	{				
		return hubRepo.getHubEntriesToSelect(companyId, branchId, profitCenterId, searchValue);
	}
	
	public ResponseEntity<?> getSelectedHubEntry(String companyId, String branchId, String transId, String igmNo, String profitCenterId)
	{		
		HubDocument selectedHubEntry = hubRepo.getSelectedHubEntry(companyId, branchId, profitCenterId, transId, igmNo);
		return ResponseEntity.ok(selectedHubEntry);	
	}

}
