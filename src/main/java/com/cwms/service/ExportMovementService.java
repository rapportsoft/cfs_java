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

import com.cwms.entities.ExportMovement;
import com.cwms.entities.Vessel;
import com.cwms.entities.Voyage;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportMovementRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.VesselRepository;
import com.cwms.repository.VoyageRepository;

@Service
public class ExportMovementService {
	
	@Autowired
	private ExportMovementRepo movementRepo;
	
	@Autowired
	private ExportStuffTallyRepo tallyRepo;

	@Autowired
	private HelperMethods helperMethods;

	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private VoyageRepository voyagerepo;

	@Autowired
	private VesselRepository vesselRepo;
	
	@Autowired
	private GateInRepository gateInRepo;
	
	@Autowired
	private ExportInventoryRepository inventoryRepo;
	
	
	
	
	public List<Object[]> getMovementEntriesToSelect(String companyId, String branchId, String searchValue)
	{				
		return movementRepo.getMovementEntriesToSelect(companyId, branchId, searchValue);
	}
	
	
	
	
	
	
	
	
	public ResponseEntity<?> getSelectedMovementEntry(String companyId, String branchId, String movementReqId, String lineId, String containerNo, String profitcentreId) {
			
		 List<ExportMovement> selectedMovementEntry = movementRepo.getSelectedMovementEntry(companyId, branchId, profitcentreId, movementReqId);		
	
	return ResponseEntity.ok(selectedMovementEntry);
	}
	
	
	
	
	
	
	

//	public ResponseEntity<?> searchContainerNoForStuffingConteinerWise(String companyId, String branchId, String containerNo, String profitcentreId) {
//
//		
//			List<Object[]> result = movementRepo.searchContainerNoForMovement(companyId, branchId, containerNo, profitcentreId);	
//			
//			List<Map<String, Object>> containerMap = result.stream().map(row -> {
//			    Map<String, Object> map = new HashMap<>();
//			    map.put("value", row[0]);
//			    map.put("label", row[0]);
//			    map.put("containerNo", row[0]);
//			    map.put("containerSize", row[1]);
//			    map.put("containerType", row[2]);
//			    map.put("shippingAgent", row[3]);
//			    map.put("shippingAgentName", row[4]);
//			    map.put("shippingLine", row[5]);
//			    map.put("shippingLineName", row[6]);
//			    map.put("onAccountOf", row[7]);
//			    map.put("viaNo", row[8]);
//			    map.put("voyageNo", row[9]);
//			    map.put("vesselId", row[10]);
//			    map.put("vesselName", row[11]);
//			    map.put("stuffTallyId", row[12]);
//			    map.put("gateInId", row[13]);
//			    map.put("agentSealNo", row[14]);
//			    map.put("customsSealNo", row[15]);
//			    map.put("grossWeight", row[16]);
//			    map.put("sbNo", row[17]);
//			    
//			    map.put("pod", row[18]);
//			    map.put("pol", row[19]);
//			    map.put("sbDate", row[20]);
//			    
//			    map.put("stuffId", row[21]);
//			    map.put("stuffDate", row[22]);
//			    
//			    map.put("stuffTallyDate", row[23]);
//			    map.put("stuffTallyLineId", row[24]);
//			    
//			    
//			    return map;
//			}).collect(Collectors.toList());			
//
//			System.out.println("result : "+result + "  \n containerMap" + containerMap);
//			
//			return ResponseEntity.ok(containerMap);
//	}
//	
	
//	public ResponseEntity<?> searchContainerNoForStuffingConteinerWise(String companyId, String branchId, String containerNo, String profitcentreId, String movementType) {
//
//		
//		List<Object[]> result = movementRepo.searchContainerNoForMovement(companyId, branchId, containerNo, profitcentreId, movementType);	
//		
//		List<Map<String, Object>> containerMap = result.stream().map(row -> {
//		    Map<String, Object> map = new HashMap<>();
//		    map.put("value", row[0]);
//		    map.put("label", row[0]);
//		    map.put("containerNo", row[0]);
//		    map.put("containerSize", row[1]);
//		    map.put("containerType", row[2]);
//		    map.put("shippingAgent", row[3]);
//		    map.put("shippingAgentName", row[4]);
//		    map.put("shippingLine", row[5]);
//		    map.put("shippingLineName", row[6]);
//		    map.put("onAccountOf", row[7]);
//		    map.put("viaNo", row[8]);
//		    map.put("voyageNo", row[9]);
//		    map.put("vesselId", row[10]);
//		    map.put("vesselName", row[11]);
//		    map.put("stuffTallyId", row[12]);
//		    map.put("gateInId", row[13]);
//		    map.put("agentSealNo", row[14]);
//		    map.put("customsSealNo", row[15]);
//		    map.put("grossWeight", row[16]);
//		    map.put("sbNo", row[17]);
//		    
//		    map.put("pod", row[18]);
//		    map.put("pol", row[19]);
//		    map.put("sbDate", row[20]);
//		    
//		    map.put("stuffId", row[21]);
//		    map.put("stuffDate", row[22]);
//		    
//		    map.put("stuffTallyDate", row[23]);
//		    map.put("stuffTallyLineId", row[24]);
//		    
//		    
//		    return map;
//		}).collect(Collectors.toList());			
//
//		System.out.println("result : "+result + "  \n containerMap" + containerMap);
//		
//		return ResponseEntity.ok(containerMap);
//}
//	
	
	
public ResponseEntity<?> searchContainerNoForStuffingConteinerWise(String companyId, String branchId, String containerNo, String profitcentreId, String movementType) {

		
		if("PortRn".equals(movementType)) {
			List<Object[]> result = gateInRepo.getPortRnDataForMovement(companyId, branchId, containerNo, profitcentreId);	
			
			List<Map<String, Object>> containerMap = result.stream().map(row -> {
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
			    map.put("viaNo", "");
			    map.put("voyageNo", "");
			    map.put("vesselId", "");
			    map.put("vesselName", "");
			    map.put("stuffTallyId", "");
			    map.put("gateInId", row[8]);
			    map.put("agentSealNo", "");
			    map.put("customsSealNo", row[9]);
			    map.put("grossWeight", row[10]);
			    map.put("sbNo", row[11]);
			    
			    map.put("pod", "");
			    map.put("pol", "");
			    map.put("sbDate", row[12]);
			    
			    map.put("stuffId", "");
			    map.put("stuffDate", null);
			    
			    map.put("stuffTallyDate", null);
			    map.put("stuffTallyLineId", "");
			    
			    
			    return map;
			}).collect(Collectors.toList());			

	
			
			return ResponseEntity.ok(containerMap);
		}
		else {
			List<Object[]> result = movementRepo.searchContainerNoForMovement(companyId, branchId, containerNo, profitcentreId, movementType);	
			
			List<Map<String, Object>> containerMap = result.stream().map(row -> {
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
			    map.put("viaNo", row[8]);
			    map.put("voyageNo", row[9]);
			    map.put("vesselId", row[10]);
			    map.put("vesselName", row[11]);
			    map.put("stuffTallyId", row[12]);
			    map.put("gateInId", row[13]);
			    map.put("agentSealNo", row[14]);
			    map.put("customsSealNo", row[15]);
			    map.put("grossWeight", row[16]);
			    map.put("sbNo", row[17]);
			    
			    map.put("pod", row[18]);
			    map.put("pol", row[19]);
			    map.put("sbDate", row[20]);
			    
			    map.put("stuffId", row[21]);
			    map.put("stuffDate", row[22]);
			    
			    map.put("stuffTallyDate", row[23]);
			    map.put("stuffTallyLineId", row[24]);
			    
			    
			    return map;
			}).collect(Collectors.toList());			

			System.out.println("result : "+result + "  \n containerMap" + containerMap);
			
			return ResponseEntity.ok(containerMap);
		}
}
	
	
	
	@Transactional
	public ResponseEntity<?> saveExportMovement(String companyId, String branchId, List<ExportMovement> exportMovement, String user) {
		Date currentDate = new Date();
		Date zeroDate = new Date(0);
		List<ExportMovement> listToSend = new ArrayList<>();
		String financialYear = helperMethods.getFinancialYear();
		String autoIncrementVesselId = "";
//		List<ExportMovement> listToBeSend = new ArrayList<>();
		
		try {
			
			ExportMovement earlyMovement = exportMovement.get(0);			
			
			 Optional<String> firstValidStuffReqId = exportMovement.stream()
				        .map(ExportMovement::getMovementReqId)  // Extract the stuffReqId from each record
				        .filter(stuffReqId -> stuffReqId != null && !stuffReqId.trim().isEmpty())  // Filter non-null, non-empty strings
				        .findFirst();
			
			String autoMovementId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty()) 
				    ? firstValidStuffReqId.get() 
				    : processService.autoExportMovementId(companyId, branchId, "P00108");


//	    	Checking for a vessel entry	    	
			if (earlyMovement.getVesselId() == null || earlyMovement.getVesselId().isEmpty()) {
				autoIncrementVesselId = processService.autoIncrementVesselId(companyId, branchId, "P03202");

//				ExportSbEntry dataForVesselEntry = entryRepo.getDataForVesselEntry(companyId, branchId,
//						earlyStuffRequest.getSbNo(), earlyStuffRequest.getSbTransId());

				Vessel newVessel = new Vessel(companyId, branchId, autoIncrementVesselId,
						earlyMovement.getVesselName(), "P00220", " ", "Y", "Y",
						earlyMovement.getVesselName(), user, currentDate, user, currentDate);

				Voyage newVoyage = new Voyage(companyId, branchId, " ",
						" ", autoIncrementVesselId, earlyMovement.getVoyageNo(),
						earlyMovement.getViaNo(), " ", " ", " ", 1, zeroDate, zeroDate,
						zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, zeroDate, zeroDate,
						0, 0, "", earlyMovement.getVesselName(), "", zeroDate,
						" ", zeroDate, zeroDate, " ", zeroDate,
						" ", zeroDate, new BigDecimal("0"), user, currentDate, user, currentDate, user, currentDate,
						"A");

				vesselRepo.save(newVessel);
				voyagerepo.save(newVoyage);

			}
			
			for(ExportMovement move : exportMovement)
			{
				
					boolean existsBySbNoForstuffing = movementRepo.existsByContainerNoMovement(companyId, branchId, move.getProfitcentreId(), move.getContainerNo(), autoMovementId, move.getMovementReqLineId());
					
					 if (existsBySbNoForstuffing) {
				            String errorMessage = "Duplicate Container No found for SrNo: " + move.getMovementReqLineId() + " and Container No: " + move.getContainerNo();
				            return ResponseEntity.badRequest().body(errorMessage);
				        }
					
					if (move.getMovementReqId() == null || move.getMovementReqId().isEmpty()) {
						System.out.println("Save");
					 
						move.setMovementReqId(autoMovementId);
						move.setFinYear(financialYear);
						move.setCreatedBy(user);
						move.setCreatedDate(currentDate);
						move.setEditedBy(user);
						move.setEditedDate(currentDate);
						move.setApprovedBy(user);
						move.setApprovedDate(currentDate);
						move.setStatus("A");
						
						
						int maxSubSrNo = movementRepo.getMaxLineId(companyId, branchId, move.getProfitcentreId(), autoMovementId);
						
						move.setMovementReqLineId(String.valueOf(maxSubSrNo + 1));
						
						
						if (move.getVesselId() == null || move.getVesselId().isEmpty()) {
							move.setVesselId(autoIncrementVesselId);
						}

						System.out.println(move);

						ExportMovement save = movementRepo.save(move);
						
						listToSend.add(save);
					}				
					else
					{
						

						ExportMovement dataForUpdateEntry = movementRepo.getDataForUpdate(
								move.getCompanyId(), move.getBranchId(), move.getContainerNo(),
								move.getProfitcentreId(), move.getMovementReqId(),
								move.getMovementReqLineId());
						
						
					
						System.out.println("dataForUpdateEntry " + dataForUpdateEntry);					
						
						
						if (move.getVesselId() == null || move.getVesselId().isEmpty()) {
							dataForUpdateEntry.setVesselId(autoIncrementVesselId);
						}
						
						dataForUpdateEntry.setViaNo(move.getViaNo());
						dataForUpdateEntry.setAgentSealNo(move.getAgentSealNo());
						dataForUpdateEntry.setCustomsSealNo(move.getCustomsSealNo());
						dataForUpdateEntry.setVesselName(move.getVesselName());
						dataForUpdateEntry.setPol(move.getPol());
						dataForUpdateEntry.setPod(move.getPod());
						dataForUpdateEntry.setTypeOfContainer(move.getTypeOfContainer());
						dataForUpdateEntry.setMovementReqDate(move.getMovementReqDate());
						dataForUpdateEntry.setVoyageNo(move.getVoyageNo());
						dataForUpdateEntry.setShift(move.getShift());				
						dataForUpdateEntry.setForceEntryFlag(move.getForceEntryFlag());
						dataForUpdateEntry.setComments(move.getComments());
						
						dataForUpdateEntry.setForceEntryApproval(move.getForceEntryApproval());				
						dataForUpdateEntry.setForceEntryDate(move.getForceEntryDate());
						dataForUpdateEntry.setForceEntryRemarks(move.getForceEntryRemarks());
						
						dataForUpdateEntry.setMovReqType(move.getMovReqType());
						
						
						System.out.println("dataForUpdateEntry \n"+dataForUpdateEntry);
					
						ExportMovement save = movementRepo.save(dataForUpdateEntry);
						listToSend.add(save);					
						
					}
					if("PortRn".equals(move.getMovReqType())) {
						int updateMovementIdToGateIn = gateInRepo.updatePortReturnId(companyId, branchId, move.getGateInId(), move.getMovementReqId());
					}
					else {
						int updateExportTallyMovement = tallyRepo.updateExportTallyMovement(move.getMovementReqId(), move.getMovReqType(), move.getGateInId(), move.getCompanyId(), move.getBranchId());
					}
					int updateExportInventoryMovement = inventoryRepo.updateExportInventoryMovement(move.getMovementReqId(),move.getMovementReqDate(), user, currentDate, move.getGateInId(), move.getCompanyId(), move.getBranchId());
					
				
				
				
				
				
			}		
			
			
			ExportMovement saved = listToSend.get(0);		
			
			
			List<ExportMovement> result2 = movementRepo.getSelectedMovementEntry(companyId, branchId, saved.getProfitcentreId(), saved.getMovementReqId());
			
			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}
	
	
}
