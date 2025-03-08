package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.GateIn;
import com.cwms.entities.HubDocument;
import com.cwms.entities.StuffRequestHub;
import com.cwms.entities.VehicleTrack;
import com.cwms.entities.Vessel;
import com.cwms.entities.Voyage;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.HubDocumentRepo;
import com.cwms.repository.HubStuffRequestRepo;
import com.cwms.repository.VehicleTrackRepository;
import com.cwms.repository.VesselRepository;
import com.cwms.repository.VoyageRepository;

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
	
	@Autowired
	private VoyageRepository voyagerepo;

	@Autowired
	private VesselRepository vesselRepo;
	
	
	@Autowired
	private HubStuffRequestRepo stuffingRepo;	
	

	
	public ResponseEntity<?> getSelectedStuffingEntry(String companyId, String branchId, String stuffingReqId)
	{		
		List<StuffRequestHub> selectedGateInEntry = stuffingRepo.searchStuffingContainerWiseSaved(companyId, branchId, stuffingReqId);
		return ResponseEntity.ok(selectedGateInEntry);	
	}
	
	
	public List<Object[]> getStuffingEntriesToSelect(String companyId, String branchId, String searchValue, String profitCenterId)
	{				
		return stuffingRepo.getStuffingEntriesToSelect(companyId, branchId, searchValue, profitCenterId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveHubStuffRequestContainer(String companyId, String branchId,
			List<StuffRequestHub> stuffRequestHub, String user) {
		Date currentDate = new Date();
		Date zeroDate = new Date(0);
		List<StuffRequestHub> listToSend = new ArrayList<>();
		Integer stuffQuantity = 0;		
		String financialYear = helperMethods.getFinancialYear();
		String autoIncrementVesselId = "";
		int lineId = 1;
		try {
			
			StuffRequestHub earlyStuffRequest = stuffRequestHub.get(0);			
			
			 Optional<String> firstValidStuffReqId = stuffRequestHub.stream()
				        .map(StuffRequestHub::getStuffReqId)  // Extract the stuffReqId from each record
				        .filter(stuffReqId -> stuffReqId != null && !stuffReqId.trim().isEmpty())  // Filter non-null, non-empty strings
				        .findFirst();
			
			String autoStuffingId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty()) 
				    ? firstValidStuffReqId.get() 
				    : processService.autoExportStuffingId(companyId, branchId, "P00203");


//	    	Checking for a vessel entry	    	
			if (earlyStuffRequest.getVesselId() == null || earlyStuffRequest.getVesselId().isEmpty()) {
				autoIncrementVesselId = processService.autoIncrementVesselId(companyId, branchId, "P03202");

			
				Vessel newVessel = new Vessel(companyId, branchId, autoIncrementVesselId,
						earlyStuffRequest.getVesselName(), "P00103", earlyStuffRequest.getTerminal(), "Y", "Y",
						earlyStuffRequest.getVesselName(), user, currentDate, user, currentDate);

				Voyage newVoyage = new Voyage(companyId, branchId, earlyStuffRequest.getPod(),
						" ", autoIncrementVesselId, earlyStuffRequest.getVoyageNo(),
						earlyStuffRequest.getViaNo(), " ", " ", " ", 1, zeroDate, zeroDate,
						earlyStuffRequest.getGateOpenDate(), zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, zeroDate,
						0, 0, "", earlyStuffRequest.getVesselName(), "", earlyStuffRequest.getBerthingDate(),
						earlyStuffRequest.getRotationNo(), earlyStuffRequest.getRotationDate(), zeroDate, " ", zeroDate,
						" ", zeroDate, new BigDecimal("0"), user, currentDate, user, currentDate, user, currentDate,
						"A");

				vesselRepo.save(newVessel);
				voyagerepo.save(newVoyage);
			}		
			
			
			for (StuffRequestHub exportStuffLoop : stuffRequestHub) {			
				
				boolean existsBySbNoForstuffing = hubRepo.existsByIgmNoForstuffing(companyId, branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getProfitCentreId(), autoStuffingId, exportStuffLoop.getStuffReqLineId(), exportStuffLoop.getContainerNo());
				
				 if (existsBySbNoForstuffing) {
			            String errorMessage = "Duplicate IGM NO found for SrNo: " + exportStuffLoop.getStuffReqLineId() + " and IGM No: " + exportStuffLoop.getSbNo();
			            return ResponseEntity.badRequest().body(errorMessage);
			        }
				
				if (exportStuffLoop.getStuffReqId() == null || exportStuffLoop.getStuffReqId().isEmpty()) {
					System.out.println("Save");				

					int updateExportInventoryStuffingRequest = hubRepo.updateHubInventoryStuffingRequest(
							exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getVesselId(),
							exportStuffLoop.getViaNo(), autoStuffingId, exportStuffLoop.getStuffReqDate(), user,
							currentDate, exportStuffLoop.getGateInId(), companyId, branchId);

					System.out.println("updateExportInventoryStuffingRequest " + updateExportInventoryStuffingRequest);

					exportStuffLoop.setStuffReqId(autoStuffingId);
					exportStuffLoop.setFinYear(financialYear);
					exportStuffLoop.setCreatedBy(user);
					exportStuffLoop.setCreatedDate(currentDate);
					exportStuffLoop.setEditedBy(user);
					exportStuffLoop.setEditedDate(currentDate);
					exportStuffLoop.setApprovedBy(user);
					exportStuffLoop.setApprovedDate(currentDate);
					exportStuffLoop.setStatus("A");
					
					
					int maxSubSrNo = stuffingRepo.getMaxLineId(companyId, branchId, exportStuffLoop.getProfitCentreId(), autoStuffingId);
					
					exportStuffLoop.setStuffReqLineId(maxSubSrNo + 1);
					lineId ++;
					
					
					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						exportStuffLoop.setVesselId(autoIncrementVesselId);
					}

					System.out.println(exportStuffLoop);

					StuffRequestHub save = stuffingRepo.save(exportStuffLoop);
					
					listToSend.add(save);
					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
				
				
					int updateGateInStuffRequest = hubRepo.updateHubGateInContainerStuffingRequest(companyId,
							branchId, autoStuffingId, exportStuffLoop.getGateInId());
					
					
					
					int updateStuffRequest = hubRepo.updateHubGateInStuffingRequest(companyId,
							branchId, exportStuffLoop.getNoOfPackagesStuffed().intValue(), exportStuffLoop.getTotalCargoWeight(), exportStuffLoop.getSbTransId(), exportStuffLoop.getSbNo(), exportStuffLoop.getSbLineNo());
					
					
					
					System.out.println("updateStuffRequest : " + updateStuffRequest);
				
				}				
				else
				{					

					StuffRequestHub dataForUpdateEntry = stuffingRepo.getDataForUpdateEntry(
							exportStuffLoop.getCompanyId(), exportStuffLoop.getBranchId(), exportStuffLoop.getSbNo(),
							exportStuffLoop.getSbTransId(), exportStuffLoop.getStuffReqId(),
							exportStuffLoop.getStuffReqLineId());
					
					
					int updateStuffRequest = stuffingRepo.updateStuffReqQtyUpdate(dataForUpdateEntry.getNoOfPackagesStuffed().intValue(),exportStuffLoop.getNoOfPackagesStuffed().intValue(), dataForUpdateEntry.getTotalCargoWeight(), exportStuffLoop.getTotalCargoWeight(), companyId, branchId, exportStuffLoop.getSbNo() , exportStuffLoop.getSbLineNo(), exportStuffLoop.getSbTransId());
									

					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						dataForUpdateEntry.setVesselId(autoIncrementVesselId);
					}
					
					dataForUpdateEntry.setViaNo(exportStuffLoop.getViaNo());
					dataForUpdateEntry.setRotationNo(exportStuffLoop.getRotationNo());
					dataForUpdateEntry.setRotationDate(exportStuffLoop.getRotationDate());
					dataForUpdateEntry.setVesselName(exportStuffLoop.getVesselName());
					dataForUpdateEntry.setBerthingDate(exportStuffLoop.getBerthingDate());
					dataForUpdateEntry.setGateOpenDate(exportStuffLoop.getGateOpenDate());
					dataForUpdateEntry.setNoOfPackagesStuffed(exportStuffLoop.getNoOfPackagesStuffed());
					dataForUpdateEntry.setTotalCargoWeight(exportStuffLoop.getTotalCargoWeight());
					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
					dataForUpdateEntry.setShift(exportStuffLoop.getShift());				
					dataForUpdateEntry.setContainerHealth(exportStuffLoop.getContainerHealth());
					dataForUpdateEntry.setPod(exportStuffLoop.getPod());
					dataForUpdateEntry.setPodDesc(exportStuffLoop.getPodDesc());
					dataForUpdateEntry.setOnAccountOf(exportStuffLoop.getOnAccountOf());
					dataForUpdateEntry.setDestination(exportStuffLoop.getDestination());
					
					
					dataForUpdateEntry.setCustomSealNo(exportStuffLoop.getCustomSealNo());
					dataForUpdateEntry.setAgentSealNo(exportStuffLoop.getAgentSealNo());
					dataForUpdateEntry.setMovementOrderDate(exportStuffLoop.getMovementOrderDate());
					
					dataForUpdateEntry.setPlacementDate(exportStuffLoop.getPlacementDate());
					dataForUpdateEntry.setStuffDate(exportStuffLoop.getStuffDate());
					dataForUpdateEntry.setBeginDateTime(exportStuffLoop.getBeginDateTime());
					dataForUpdateEntry.setEndDateTime(exportStuffLoop.getEndDateTime());
					dataForUpdateEntry.setMovementOrderDate(exportStuffLoop.getMovementOrderDate());
					dataForUpdateEntry.setMovementOrderDate(exportStuffLoop.getMovementOrderDate());
					dataForUpdateEntry.setMtyCountWt(exportStuffLoop.getMtyCountWt());
					
					

					System.out.println("dataForUpdateEntry \n"+dataForUpdateEntry);
				
					StuffRequestHub save = stuffingRepo.save(dataForUpdateEntry);
					listToSend.add(save);
								
				}			
			}
			StuffRequestHub savedStuffRequest = listToSend.get(0);
			
			int updateExportInventoryStuffingRequest = hubRepo.updateHubInventoryStuffingRequestShort(
					savedStuffRequest.getVesselId(), savedStuffRequest.getViaNo(), user,
					currentDate, savedStuffRequest.getGateInId(), companyId, branchId);

			
			List<StuffRequestHub> result2 = stuffingRepo.searchStuffingContainerWiseSaved(companyId, branchId, autoStuffingId);
			
			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<?> searchIgmNoForStuffing(String companyId, String branchId, String searchValue, String profitcentreId, String stuffReqId) {
		
		List<Object[]> result = hubRepo.searchIgmNoForStuffing(companyId, branchId, searchValue, profitcentreId, stuffReqId);		
							

		List<Map<String, Object>> containerList  = result.stream().map(row -> {
		        Map<String, Object> map = new HashMap<>();
		        map.put("value", row[0]);
		        map.put("label", row[0]);
		        map.put("igmNo", row[0]);
		        map.put("igmLineNo", row[1]);
		        map.put("hubTransDate", row[2]);
		        map.put("importerName", row[3]);
		        map.put("cargoDescription", row[7]);
		        map.put("noOfPackages", row[4]);
		        map.put("cargoWt", row[6]); 
		        map.put("grossWeight", row[5]);
		        map.put("hubTransId", row[8]);
		        map.put("balanceQuantity", row[9]); 
		        map.put("balanceWeight", row[10]);	      
		        return map;
		    }).collect(Collectors.toList());			
		
		return ResponseEntity.ok(containerList);

}

	
	public ResponseEntity<?> searchContainerNoForHubCLP(String companyId, String branchId, String containerNo, String profitcentreId) {
	    List<Object[]> result = hubRepo.searchContainerNoForHubCLP(companyId, branchId, containerNo, profitcentreId);    

	    List<Map<String, Object>> containerMap = result.stream().map(row -> {
		    Map<String, Object> map = new HashMap<>();    
	  
	        map.put("value", row[0] + "" + row[8]);
	        map.put("label", row[0]);
	        map.put("containerNo", row[0]);
	        map.put("containerSize", row[1]);
	        map.put("containerType", row[2]);
	        map.put("sa", row[3]);
	        map.put("shippingAgentName", row[4]);
	        map.put("sl", row[5]);
	        map.put("shippingLineName", row[6]);
	        map.put("onAccountOf", row[7]);
	        map.put("tareWeight", row[8]);
	        map.put("inGateInDate", row[9]);
	        map.put("deliveryOrderNo", row[10]);
	        map.put("gateInId", row[11]);
	        map.put("containerHealth", row[12]);
	        map.put("onAccountName", row[13]);
	        return map;
		}).collect(Collectors.toList());	

	    return ResponseEntity.ok(containerMap);
	}

	
	
	
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
	        map.put("igmLine", (String)obj[2]);
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
