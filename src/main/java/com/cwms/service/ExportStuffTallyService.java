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
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.GateIn;
import com.cwms.entities.Vessel;
import com.cwms.entities.Voyage;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.VesselRepository;
import com.cwms.repository.VoyageRepository;

@Service
public class ExportStuffTallyService {
	
	@Autowired
	private ExportStuffTallyRepo tallyRepo;	
	
	@Autowired
	private GateInRepository gateInRepo;
	
	@Autowired
	private ProcessNextIdService processService;

	@Autowired
	private ExportInventoryRepository inventoryRepo;

	@Autowired
	private ExportSbCargoEntryRepo cargoEntry;
	
	@Autowired
	private VoyageRepository voyagerepo;

	@Autowired
	private VesselRepository vesselRepo;
	
	@Autowired
	private ExportEntryRepo entryRepo;


	
	
	public ResponseEntity<?> getSelectedBufferStuffingEntry(String companyId, String branchId, String profitCenterId, String stuffTallyId, String containerNo)
	{		
		List<ExportStuffTally> savedExportStuffTally = tallyRepo.searchStuffTallySaved(companyId, branchId, stuffTallyId, containerNo,profitCenterId);
		return ResponseEntity.ok(savedExportStuffTally);	
	}
	
	
	public List<Object[]> getBufferStuffingToSelect(String companyId, String branchId, String searchValue)
	{				
		return tallyRepo.getBufferStuffingToSelect(companyId, branchId, searchValue);
	}
	
	
	
	@Transactional
	public ResponseEntity<?> saveExportTallyBuffer(String companyId, String branchId,
			List<ExportStuffTally> exportStuffRequest, String user) {
		Date currentDate = new Date();
		Date zeroDate = new Date(0);
		List<ExportStuffTally> listToSend = new ArrayList<>();
		String autoIncrementVesselId = "";
		try {
			
			ExportStuffTally earlyStuffRequest = exportStuffRequest.get(0);	
			
		
			
			boolean existsContainerNo = tallyRepo.existsContainerNo(companyId, branchId, earlyStuffRequest.getContainerNo(), earlyStuffRequest.getGateInId(), earlyStuffRequest.getProfitcentreId(), earlyStuffRequest.getStuffTallyId());
			
			 if (existsContainerNo) {
				 Map<String, Object> errorResponse = new HashMap<>();
				    errorResponse.put("type", "VALIDATION_ERROR");
				    errorResponse.put("field", "containerNo");
				    errorResponse.put("details", Map.of(
				        "containerNo", earlyStuffRequest.getContainerNo()
				    ));
				    errorResponse.put("message", "Container No " + earlyStuffRequest.getContainerNo() + " is Already Stuffed");
				    return ResponseEntity.badRequest().body(errorResponse);
		        }
			
			 Optional<String> firstValidStuffReqId = exportStuffRequest.stream()
				        .map(ExportStuffTally::getStuffTallyId)  // Extract the stuffReqId from each record
				        .filter(stuffReqId -> stuffReqId != null && !stuffReqId.trim().isEmpty())  // Filter non-null, non-empty strings
				        .findFirst();
			
			String autoStuffingId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty()) 
				    ? firstValidStuffReqId.get() 
				    : processService.autoExportStuffingId(companyId, branchId, "P00110");


//	    	Checking for a vessel entry	    	
			if (earlyStuffRequest.getVesselId() == null || earlyStuffRequest.getVesselId().isEmpty()) {
				autoIncrementVesselId = processService.autoIncrementVesselId(companyId, branchId, "P03202");

				ExportSbEntry dataForVesselEntry = entryRepo.getDataForVesselEntry(companyId, branchId,
						earlyStuffRequest.getSbNo(), earlyStuffRequest.getSbTransId());

				Vessel newVessel = new Vessel(companyId, branchId, autoIncrementVesselId,
						earlyStuffRequest.getVesselName(), "P00220", earlyStuffRequest.getTerminal(), "Y", "Y",
						earlyStuffRequest.getVesselName(), user, currentDate, user, currentDate);

				Voyage newVoyage = new Voyage(companyId, branchId, dataForVesselEntry.getPod(),
						dataForVesselEntry.getPol(), autoIncrementVesselId, earlyStuffRequest.getVoyageNo(),
						earlyStuffRequest.getViaNo(), " ", " ", " ", 1, zeroDate, zeroDate,
						earlyStuffRequest.getGateOpenDate(), zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, zeroDate,
						0, 0, "", earlyStuffRequest.getVesselName(), "", earlyStuffRequest.getBerthingDate(),
						earlyStuffRequest.getRotationNo(), earlyStuffRequest.getRotationDate(), zeroDate, " ", zeroDate,
						" ", zeroDate, new BigDecimal("0"), user, currentDate, user, currentDate, user, currentDate,
						"A");

				vesselRepo.save(newVessel);
				voyagerepo.save(newVoyage);

			}
			
			
			
			for (ExportStuffTally exportStuffLoop : exportStuffRequest) {
				
				
				boolean existsBySbNoForstuffing = tallyRepo.existsBySbNoForstuffingTally(companyId, branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getProfitcentreId(), autoStuffingId, exportStuffLoop.getStuffLineId());
				
				 if (existsBySbNoForstuffing) {
					 Map<String, Object> errorResponse = new HashMap<>();
					    errorResponse.put("type", "VALIDATION_ERROR");
					    errorResponse.put("field", "sbNo");
					    errorResponse.put("details", Map.of(
					        "sbNo", exportStuffLoop.getSbNo(),
					        "srNo", exportStuffLoop.getStuffLineId()
					    ));
					    errorResponse.put("message", "Duplicate SB No found for SrNo: " + exportStuffLoop.getStuffLineId() + " and SB No: " + exportStuffLoop.getSbNo());
					    return ResponseEntity.badRequest().body(errorResponse);
			        }
				
				if (exportStuffLoop.getStuffTallyId() == null || exportStuffLoop.getStuffTallyId().isEmpty()) {
					System.out.println("Save");
				 
				

					int updateExportInventoryStuffingRequest = inventoryRepo.updateExportInventoryStuffingTally(
							exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getVesselId(),
							exportStuffLoop.getViaNo(), autoStuffingId, exportStuffLoop.getStuffTallyDate(), user,
							currentDate, exportStuffLoop.getGateInId(), companyId, branchId);

					System.out.println("updateExportInventoryStuffingRequest " + updateExportInventoryStuffingRequest);

					exportStuffLoop.setStuffTallyId(autoStuffingId);
					exportStuffLoop.setCreatedBy(user);
					exportStuffLoop.setCreatedDate(currentDate);
					exportStuffLoop.setEditedBy(user);
					exportStuffLoop.setEditedDate(currentDate);
					exportStuffLoop.setApprovedBy(user);
					exportStuffLoop.setApprovedDate(currentDate);
					exportStuffLoop.setStatus("A");
					
					
					int maxSubSrNo = tallyRepo.getMaxLineId(companyId, branchId, exportStuffLoop.getProfitcentreId(), autoStuffingId);
					
					exportStuffLoop.setStuffTallyLineId(maxSubSrNo + 1);
					
					
					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						exportStuffLoop.setVesselId(autoIncrementVesselId);
					}


					ExportStuffTally save = tallyRepo.save(exportStuffLoop);
					
					listToSend.add(save);
				
				
					int updateStuffRequest = cargoEntry.updateStuffTallyBuffer(companyId,
							branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(),
							exportStuffLoop.getStuffedQty().intValue(), exportStuffLoop.getCargoWeight());
					
					System.out.println("updateStuffRequest : " + updateStuffRequest);

				}				
				else
				{
					

					ExportStuffTally dataForUpdateEntry = tallyRepo.getDataForUpdateEntry(
							exportStuffLoop.getCompanyId(), exportStuffLoop.getBranchId(), exportStuffLoop.getSbNo(),
							exportStuffLoop.getSbTransId(), exportStuffLoop.getStuffTallyId(),
							exportStuffLoop.getStuffTallyLineId());
					
					
					int updateStuffRequest = cargoEntry.updateStuffTallyQtyUpdateBuffer(dataForUpdateEntry.getStuffedQty().intValue(),exportStuffLoop.getStuffedQty().intValue(),companyId, branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(),dataForUpdateEntry.getCargoWeight(), exportStuffLoop.getCargoWeight());
				
					System.out.println("updateStuffRequest " + updateStuffRequest);					
					
					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						dataForUpdateEntry.setVesselId(autoIncrementVesselId);
					}
					
					dataForUpdateEntry.setViaNo(exportStuffLoop.getViaNo());
					dataForUpdateEntry.setRotationNo(exportStuffLoop.getRotationNo());
					dataForUpdateEntry.setRotationDate(exportStuffLoop.getRotationDate());
					dataForUpdateEntry.setVesselName(exportStuffLoop.getVesselName());
					dataForUpdateEntry.setBerthingDate(exportStuffLoop.getBerthingDate());
					dataForUpdateEntry.setGateOpenDate(exportStuffLoop.getGateOpenDate());
					dataForUpdateEntry.setStuffedQty(exportStuffLoop.getStuffedQty());
					dataForUpdateEntry.setCargoWeight(exportStuffLoop.getCargoWeight());
					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
					dataForUpdateEntry.setShift(exportStuffLoop.getShift());				
					dataForUpdateEntry.setContainerCondition(exportStuffLoop.getContainerCondition());
					dataForUpdateEntry.setTotalCargoWeight(exportStuffLoop.getTotalCargoWeight());
					dataForUpdateEntry.setTotalGrossWeight(exportStuffLoop.getTotalGrossWeight());
					dataForUpdateEntry.setPod(exportStuffLoop.getPod());
					dataForUpdateEntry.setFinalPod(exportStuffLoop.getFinalPod());	
					
					dataForUpdateEntry.setAgentSealNo(exportStuffLoop.getAgentSealNo());
					dataForUpdateEntry.setCustomsSealNo(exportStuffLoop.getCustomsSealNo());
					dataForUpdateEntry.setBackToTownRemark(exportStuffLoop.getBackToTownRemark());
					dataForUpdateEntry.setTareWeight(exportStuffLoop.getTareWeight());
					dataForUpdateEntry.setTerminal(exportStuffLoop.getTerminal());;
					
					
					
					
					

					System.out.println("dataForUpdateEntry \n"+dataForUpdateEntry);
				
					ExportStuffTally save = tallyRepo.save(dataForUpdateEntry);
					listToSend.add(save);
				
					
				}
								
			}
			ExportStuffTally savedStuffRequest = listToSend.get(0);
			
			int updateExportInventoryStuffingRequest = inventoryRepo.updateExportInventoryStuffingTally(
					savedStuffRequest.getSbNo(), savedStuffRequest.getSbTransId(), savedStuffRequest.getVesselId(),
					savedStuffRequest.getViaNo(), autoStuffingId, savedStuffRequest.getStuffTallyDate(), user,
					currentDate, savedStuffRequest.getGateInId(), companyId, branchId);

			int gateInByIdsForStuffing = gateInRepo.updateStuffTallyBufferdGateIn(savedStuffRequest.getStuffTallyId(), companyId, branchId,
					savedStuffRequest.getProfitcentreId(), savedStuffRequest.getGateInId(), savedStuffRequest.getStuffTallyDate());

			
			List<ExportStuffTally> result2 = tallyRepo.searchStuffTallySaved(companyId, branchId, savedStuffRequest.getStuffTallyId(), savedStuffRequest.getContainerNo(), savedStuffRequest.getProfitcentreId());
			
			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}
	
	
	
	
public ResponseEntity<?> searchSbNoForBuffer(String companyId, String branchId, String searchValue, String profitcentreId, String stuffTallyId, String sbType) {
		
		List<Object[]> result = tallyRepo.searchSbNoForTallyBuffer(companyId, branchId, searchValue, profitcentreId, stuffTallyId, sbType);		
					
		

		List<Map<String, Object>> containerList  = result.stream().map(row -> {
		        Map<String, Object> map = new HashMap<>();
		        map.put("value", row[0]);
		        map.put("label", row[0]);
		        map.put("sbNo", row[0]);
		        map.put("sbLineNo", row[2]);
		        map.put("sbTransId", row[1]);
		        map.put("exporterId", row[3]);
		        map.put("exporterName", row[4]);
		        map.put("sbDate", row[5]);
		        map.put("prvStuffedQty", row[6]);
		        map.put("commodity", row[7]);       
		        map.put("noOfPackages", row[8]);
		        map.put("pod", row[9]); 
		        map.put("grossWeight", row[10]);
		        map.put("stuffedWt", row[11]);
		        map.put("cha", row[12]);
		        return map;
		    }).collect(Collectors.toList());			
		
		return ResponseEntity.ok(containerList);

}


public ResponseEntity<?> searchContainerNoBuffer(String companyId, String branchId, String searchValue, String profitcentreId) {
	
	List<Object[]> result = tallyRepo.searchContainerNoForTallyBuffer(companyId, branchId, searchValue, profitcentreId);		
				
	

	List<Map<String, Object>> containerList  = result.stream().map(row -> {
	        Map<String, Object> map = new HashMap<>();
	        map.put("value", row[0]);
		    map.put("label", row[0]);
		    map.put("containerNo", row[0]);
		    map.put("containerSize", row[1]);
		    map.put("containerType", row[2]);
		    map.put("shippingAgent", row[3]);
		    map.put("shippingAgentName", row[4]);
		    map.put("shippingLine", row[5]);
		    map.put("shippingLineName", row[6]);
		    map.put("onAccountOf", row[7]);
		    map.put("tareWeight", row[8]);
		    map.put("inGateInDate", row[9]);
		    map.put("deliveryOrderNo", row[10]);
		    map.put("gateInId", row[11]);
	        map.put("gateInType", row[12]);
	        map.put("containerCondition", row[13]);
	        map.put("customsSealNo", row[14]);
	        
	        return map;
	    }).collect(Collectors.toList());			
	
	return ResponseEntity.ok(containerList);

}

	


	
}
