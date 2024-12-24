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

import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.GateIn;
import com.cwms.entities.Vessel;
import com.cwms.entities.Voyage;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffRequestRepo;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.VesselRepository;
import com.cwms.repository.VoyageRepository;

@Service
public class ExportStuffRequestService {

	@Autowired
	private ExportStuffRequestRepo stuffingRepo;

	@Autowired
	private GateInRepository gateInRepo;

	@Autowired
	private ExportEntryRepo entryRepo;

	@Autowired
	private HelperMethods helperMethods;

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
	
	
	
	public ResponseEntity<?> searchSbNoForStuffing(String companyId, String branchId, String searchValue, String profitcentreId, String stuffReqId) {
		
		List<Object[]> result = stuffingRepo.searchSbNoForStuffing(companyId, branchId, searchValue, profitcentreId, stuffReqId);		
					
		

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
		        map.put("contStuffPackages", row[6]);
		        map.put("commodity", row[7]);       
		        map.put("noOfPackages", row[8]);
		        map.put("pod", row[9]); 
		        map.put("grossWeight", row[10]);
		        map.put("typeOfPackage", row[11]);
		        return map;
		    }).collect(Collectors.toList());			
		
		return ResponseEntity.ok(containerList);

}

	
	
	
	
	
	public ResponseEntity<?> searchContainerNoForStuffingContainerWise(String companyId, String branchId, String searchValue, String profitcentreId) {
		
			List<Object[]> result = stuffingRepo.searchContainerNoForStuffingContainerWise(companyId, branchId, searchValue, profitcentreId);
			
						
			
			

			List<Map<String, Object>> containerList  = result.stream().map(row -> {
			        Map<String, Object> map = new HashMap<>();
			        map.put("value", row[0]);
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
			        return map;
			    }).collect(Collectors.toList());			
			
			return ResponseEntity.ok(containerList);

	}
	
	
	
	
	
	
	
	
	
	
	

	@Transactional
	public ResponseEntity<?> saveExportStuffRequest(String companyId, String branchId,
			List<ExportStuffRequest> exportStuffRequest, String user) {
		Date currentDate = new Date();
		Date zeroDate = new Date(0);
		List<ExportStuffRequest> listToSend = new ArrayList<>();
		Integer stuffQuantity = 0;
		int oldQuantity = 0;
		int newQuantity = 0;
		String financialYear = helperMethods.getFinancialYear();
		String autoIncrementVesselId = "";
		int lineId = 1;
		try {

			ExportStuffRequest earlyStuffRequest = exportStuffRequest.get(0);
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

			if (earlyStuffRequest.getStuffReqId() == null || earlyStuffRequest.getStuffReqId().isEmpty()) {
				System.out.println("Save");
				for (ExportStuffRequest exportStuffLoop : exportStuffRequest) {

					String autoStuffingId = processService.autoExportStuffingId(companyId, branchId, "P00105");

					GateIn gateInByIdsForStuffing = gateInRepo.getGateInByIdsForStuffing(companyId, branchId,
							exportStuffLoop.getProfitcentreId(), exportStuffLoop.getGateInId(), "EXP");
					gateInByIdsForStuffing.setStuffRequestId(autoStuffingId);

					int updateExportInventoryStuffingRequest = inventoryRepo.updateExportInventoryStuffingRequest(
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
					exportStuffLoop.setStuffReqLineId(lineId);
					lineId ++;
					
					
					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						exportStuffLoop.setVesselId(autoIncrementVesselId);
					}

					System.out.println(exportStuffLoop);

					ExportStuffRequest save = stuffingRepo.save(exportStuffLoop);
					gateInRepo.save(gateInByIdsForStuffing);
					listToSend.add(save);
					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
				}

				ExportStuffRequest savedStuffRequest = listToSend.get(0);

				int updateStuffRequest = cargoEntry.updateStuffRequest(savedStuffRequest.getCompanyId(),
						savedStuffRequest.getBranchId(), savedStuffRequest.getSbNo(), savedStuffRequest.getSbTransId(),
						stuffQuantity);

				System.out.println("updateStuffRequest : " + updateStuffRequest);
				System.out.println(" savedStuffRequest \n" + savedStuffRequest + " stuffQuantity : " + stuffQuantity);
			} // FOR UPDATE
			else {

				for (ExportStuffRequest exportStuffLoop : exportStuffRequest) {

					newQuantity += exportStuffLoop.getNoOfPackagesStuffed().intValue();

					
					ExportStuffRequest dataForUpdateEntry = stuffingRepo.getDataForUpdateEntry(
							exportStuffLoop.getCompanyId(), exportStuffLoop.getBranchId(), exportStuffLoop.getSbNo(),
							exportStuffLoop.getSbTransId(), exportStuffLoop.getStuffReqId(),
							exportStuffLoop.getStuffReqLineId());
					
					oldQuantity += dataForUpdateEntry.getNoOfPackagesStuffed().intValue();

					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						dataForUpdateEntry.setVesselId(autoIncrementVesselId);
					}
					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
					dataForUpdateEntry.setViaNo(exportStuffLoop.getViaNo());
					dataForUpdateEntry.setRotationNo(exportStuffLoop.getRotationNo());
					dataForUpdateEntry.setRotationDate(exportStuffLoop.getRotationDate());
					dataForUpdateEntry.setVesselName(exportStuffLoop.getVesselName());
					dataForUpdateEntry.setBerthingDate(exportStuffLoop.getBerthingDate());
					dataForUpdateEntry.setGateOpenDate(exportStuffLoop.getGateOpenDate());
					dataForUpdateEntry.setNoOfPackagesStuffed(exportStuffLoop.getNoOfPackagesStuffed());
					dataForUpdateEntry.setCargoWeight(exportStuffLoop.getCargoWeight());
					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
					
					
					
//			GateIn gateInByIdsForStuffing = gateInRepo.getGateInByIdsForStuffing(companyId, branchId, exportStuffLoop.getProfitcentreId(), exportStuffLoop.getGateInId(), "EXP");

					// Retrieve the old and new values for noOfPackagesStuffed
					BigDecimal oldNoOfPackagesStuffed = dataForUpdateEntry.getNoOfPackagesStuffed();
					BigDecimal newNoOfPackagesStuffed = exportStuffLoop.getNoOfPackagesStuffed();

					// Calculate the difference
					BigDecimal difference = newNoOfPackagesStuffed.subtract(oldNoOfPackagesStuffed);

					// Check if the difference is positive or negative
					if (difference.compareTo(BigDecimal.ZERO) > 0) {
						System.out.println("Packages stuffed increased by: " + difference);
					} else if (difference.compareTo(BigDecimal.ZERO) < 0) {
						System.out.println("Packages stuffed decreased by: " + difference.negate());
					} else {
						System.out.println("No change in packages stuffed.");
					}

					ExportInventory dataForUpdateInvEntry = inventoryRepo.getDataForUpdateInvEntry(companyId, branchId, exportStuffLoop.getStuffReqId(), exportStuffLoop.getGateInId());

					dataForUpdateInvEntry.setStuffReqEditedBy(user);
					dataForUpdateInvEntry.setStuffReqEditedDate(currentDate);
					dataForUpdateInvEntry.setVesselId(exportStuffLoop.getVesselId());
					dataForUpdateInvEntry.setViaNo(exportStuffLoop.getViaNo());

					inventoryRepo.save(dataForUpdateInvEntry);
					ExportStuffRequest save = stuffingRepo.save(dataForUpdateEntry);
					listToSend.add(save);
//					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
				}
				ExportStuffRequest savedStuffRequest = listToSend.get(0);
				
				
				
				
				int updateStuffRequest = cargoEntry.updateStuffReqQtyUpdate(oldQuantity,newQuantity,savedStuffRequest.getCompanyId(),
						savedStuffRequest.getBranchId(), savedStuffRequest.getSbNo(), savedStuffRequest.getSbTransId());
			
			System.out.println("updateStuffRequest " + updateStuffRequest);
			}
			
			List<ExportStuffRequest> result2 = stuffingRepo.searchContainerNoForStuffingSavedNew(companyId, branchId, earlyStuffRequest.getSbNo());
			
			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}

	public ResponseEntity<?> searchContainerNoForStuffing(String companyId, String branchId, String sbNo, String type) {

		if (type.equals("N")) {
			List<Object[]> result = stuffingRepo.searchContainerNoForStuffingNew(companyId, branchId, sbNo);
			return ResponseEntity.ok(result);
		} else {
			List<ExportStuffRequest> result2 = stuffingRepo.searchContainerNoForStuffingSavedNew(companyId, branchId,
					sbNo);
			return ResponseEntity.ok(result2);
		}

	}
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveExportStuffRequestContainer(String companyId, String branchId,
			List<ExportStuffRequest> exportStuffRequest, String user) {
		Date currentDate = new Date();
		Date zeroDate = new Date(0);
		List<ExportStuffRequest> listToSend = new ArrayList<>();
		Integer stuffQuantity = 0;		
		String financialYear = helperMethods.getFinancialYear();
		String autoIncrementVesselId = "";
		int lineId = 1;
		try {
			
			ExportStuffRequest earlyStuffRequest = exportStuffRequest.get(0);			
			
			 Optional<String> firstValidStuffReqId = exportStuffRequest.stream()
				        .map(ExportStuffRequest::getStuffReqId)  // Extract the stuffReqId from each record
				        .filter(stuffReqId -> stuffReqId != null && !stuffReqId.trim().isEmpty())  // Filter non-null, non-empty strings
				        .findFirst();
			
			String autoStuffingId = (firstValidStuffReqId.isPresent() && !firstValidStuffReqId.get().trim().isEmpty()) 
				    ? firstValidStuffReqId.get() 
				    : processService.autoExportStuffingId(companyId, branchId, "P00105");


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
			
			
			
			
			for (ExportStuffRequest exportStuffLoop : exportStuffRequest) {
				
				
//				boolean existsBySbNoForstuffing = stuffingRepo.existsBySbNoForstuffing(companyId, branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getProfitcentreId(), autoStuffingId, exportStuffLoop.getStuffReqLineId());
//				
//				 if (existsBySbNoForstuffing) {
//			            String errorMessage = "Duplicate SB No found for SrNo: " + exportStuffLoop.getStuffReqLineId() + " and SB No: " + exportStuffLoop.getSbNo();
//			            return ResponseEntity.badRequest().body(errorMessage);
//			        }
				
				if (exportStuffLoop.getStuffReqId() == null || exportStuffLoop.getStuffReqId().isEmpty()) {
					System.out.println("Save");
				 
				

					int updateExportInventoryStuffingRequest = inventoryRepo.updateExportInventoryStuffingRequest(
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
					
					
					int maxSubSrNo = stuffingRepo.getMaxLineId(companyId, branchId, exportStuffLoop.getProfitcentreId(), autoStuffingId);
					
					exportStuffLoop.setStuffReqLineId(maxSubSrNo + 1);
					lineId ++;
					
					
					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
						exportStuffLoop.setVesselId(autoIncrementVesselId);
					}

					System.out.println(exportStuffLoop);

					ExportStuffRequest save = stuffingRepo.save(exportStuffLoop);
					
					listToSend.add(save);
					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
				
				
					int updateStuffRequest = cargoEntry.updateStuffRequest(companyId,
							branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(),
							exportStuffLoop.getNoOfPackagesStuffed().intValue());
					
					System.out.println("updateStuffRequest : " + updateStuffRequest);

					
					
					
					
					
					
				}				
				else
				{
					

					ExportStuffRequest dataForUpdateEntry = stuffingRepo.getDataForUpdateEntry(
							exportStuffLoop.getCompanyId(), exportStuffLoop.getBranchId(), exportStuffLoop.getSbNo(),
							exportStuffLoop.getSbTransId(), exportStuffLoop.getStuffReqId(),
							exportStuffLoop.getStuffReqLineId());
					
					
					int updateStuffRequest = cargoEntry.updateStuffReqQtyUpdate(dataForUpdateEntry.getNoOfPackagesStuffed().intValue(),exportStuffLoop.getNoOfPackagesStuffed().intValue(),companyId, branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId());
				
					System.out.println("updateStuffRequest " + updateStuffRequest);					
					
//					oldQuantity += dataForUpdateEntry.getNoOfPackagesStuffed().intValue();

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
					dataForUpdateEntry.setCargoWeight(exportStuffLoop.getCargoWeight());
					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
					dataForUpdateEntry.setShift(exportStuffLoop.getShift());				
					dataForUpdateEntry.setContainerHealth(exportStuffLoop.getContainerHealth());
					dataForUpdateEntry.setPod(exportStuffLoop.getPod());
					

					System.out.println("dataForUpdateEntry \n"+dataForUpdateEntry);
				
					ExportStuffRequest save = stuffingRepo.save(dataForUpdateEntry);
					listToSend.add(save);
//					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
					
					
				}
				
				
				
				
				
				
				
			}
			ExportStuffRequest savedStuffRequest = listToSend.get(0);
			
			int updateExportInventoryStuffingRequest = inventoryRepo.updateExportInventoryStuffingRequest(
					savedStuffRequest.getSbNo(), savedStuffRequest.getSbTransId(), savedStuffRequest.getVesselId(),
					savedStuffRequest.getViaNo(), autoStuffingId, savedStuffRequest.getStuffReqDate(), user,
					currentDate, savedStuffRequest.getGateInId(), companyId, branchId);

			int gateInByIdsForStuffing = gateInRepo.updateStuffReqIdInGateIn(savedStuffRequest.getStuffReqId(), companyId, branchId,
					savedStuffRequest.getProfitcentreId(), savedStuffRequest.getGateInId(), "EXP");
//			gateInByIdsForStuffing.setStuffRequestId(autoStuffingId);
//			gateInRepo.save(gateInByIdsForStuffing);
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

//			if (earlyStuffRequest.getStuffReqId() == null || earlyStuffRequest.getStuffReqId().isEmpty()) {
//				System.out.println("Save");
//				for (ExportStuffRequest exportStuffLoop : exportStuffRequest) {
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//					
//
////					String autoStuffingId = processService.autoExportStuffingId(companyId, branchId, "P00105");
//
//					
//					int updateExportInventoryStuffingRequest = inventoryRepo.updateExportInventoryStuffingRequest(
//							exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(), exportStuffLoop.getVesselId(),
//							exportStuffLoop.getViaNo(), autoStuffingId, exportStuffLoop.getStuffReqDate(), user,
//							currentDate, exportStuffLoop.getGateInId(), companyId, branchId);
//
//					System.out.println("updateExportInventoryStuffingRequest " + updateExportInventoryStuffingRequest);
//
//					exportStuffLoop.setStuffReqId(autoStuffingId);
//					exportStuffLoop.setFinYear(financialYear);
//					exportStuffLoop.setCreatedBy(user);
//					exportStuffLoop.setCreatedDate(currentDate);
//					exportStuffLoop.setEditedBy(user);
//					exportStuffLoop.setEditedDate(currentDate);
//					exportStuffLoop.setApprovedBy(user);
//					exportStuffLoop.setApprovedDate(currentDate);
//					exportStuffLoop.setStatus("A");
//					exportStuffLoop.setStuffReqLineId(lineId);
//					lineId ++;
//					
//					
//					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
//						exportStuffLoop.setVesselId(autoIncrementVesselId);
//					}
//
//					System.out.println(exportStuffLoop);
//
//					ExportStuffRequest save = stuffingRepo.save(exportStuffLoop);
//					
//					listToSend.add(save);
//					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
//				
//				
//					int updateStuffRequest = cargoEntry.updateStuffRequest(companyId,
//							branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId(),
//							exportStuffLoop.getNoOfPackagesStuffed().intValue());
//					
//					System.out.println("updateStuffRequest : " + updateStuffRequest);
//				
//				}
//
//				ExportStuffRequest savedStuffRequest = listToSend.get(0);
//
//				GateIn gateInByIdsForStuffing = gateInRepo.getGateInByIdsForStuffing(companyId, branchId,
//						earlyStuffRequest.getProfitcentreId(), earlyStuffRequest.getGateInId(), "EXP");
//				gateInByIdsForStuffing.setStuffRequestId(autoStuffingId);
//				gateInRepo.save(gateInByIdsForStuffing);
//								
//				System.out.println(" savedStuffRequest \n" + savedStuffRequest + " stuffQuantity : " + stuffQuantity);
//			} // FOR UPDATE
//			else {
//
//				for (ExportStuffRequest exportStuffLoop : exportStuffRequest) {
//
////					newQuantity += exportStuffLoop.getNoOfPackagesStuffed().intValue();
//
//					
//					ExportStuffRequest dataForUpdateEntry = stuffingRepo.getDataForUpdateEntry(
//							exportStuffLoop.getCompanyId(), exportStuffLoop.getBranchId(), exportStuffLoop.getSbNo(),
//							exportStuffLoop.getSbTransId(), exportStuffLoop.getStuffReqId(),
//							exportStuffLoop.getStuffReqLineId());
//					
//					
//					int updateStuffRequest = cargoEntry.updateStuffReqQtyUpdate(dataForUpdateEntry.getNoOfPackagesStuffed().intValue(),exportStuffLoop.getNoOfPackagesStuffed().intValue(),companyId, branchId, exportStuffLoop.getSbNo(), exportStuffLoop.getSbTransId());
//				
//					System.out.println("updateStuffRequest " + updateStuffRequest);					
//					
////					oldQuantity += dataForUpdateEntry.getNoOfPackagesStuffed().intValue();
//
//					if (exportStuffLoop.getVesselId() == null || exportStuffLoop.getVesselId().isEmpty()) {
//						dataForUpdateEntry.setVesselId(autoIncrementVesselId);
//					}
//					
//					dataForUpdateEntry.setViaNo(exportStuffLoop.getViaNo());
//					dataForUpdateEntry.setRotationNo(exportStuffLoop.getRotationNo());
//					dataForUpdateEntry.setRotationDate(exportStuffLoop.getRotationDate());
//					dataForUpdateEntry.setVesselName(exportStuffLoop.getVesselName());
//					dataForUpdateEntry.setBerthingDate(exportStuffLoop.getBerthingDate());
//					dataForUpdateEntry.setGateOpenDate(exportStuffLoop.getGateOpenDate());
//					dataForUpdateEntry.setNoOfPackagesStuffed(exportStuffLoop.getNoOfPackagesStuffed());
//					dataForUpdateEntry.setCargoWeight(exportStuffLoop.getCargoWeight());
//					dataForUpdateEntry.setVoyageNo(exportStuffLoop.getVoyageNo());
//					dataForUpdateEntry.setShift(exportStuffLoop.getShift());				
//					dataForUpdateEntry.setContainerHealth(exportStuffLoop.getContainerHealth());
//
//System.out.println("dataForUpdateEntry \n"+dataForUpdateEntry);
//				
//					ExportStuffRequest save = stuffingRepo.save(dataForUpdateEntry);
//					listToSend.add(save);
////					stuffQuantity += save.getNoOfPackagesStuffed().intValue();
//					
//					
//					
//				}
//				ExportStuffRequest savedStuffRequest = listToSend.get(0);
//				ExportInventory dataForUpdateInvEntry = inventoryRepo.getDataForUpdateInvEntry(companyId, branchId,
//						savedStuffRequest.getStuffReqId(),
//						savedStuffRequest.getGateInId());
//
//				dataForUpdateInvEntry.setStuffReqEditedBy(user);
//				dataForUpdateInvEntry.setStuffReqEditedDate(currentDate);
//				dataForUpdateInvEntry.setVesselId(savedStuffRequest.getVesselId());
//				dataForUpdateInvEntry.setViaNo(savedStuffRequest.getViaNo());
//
//				inventoryRepo.save(dataForUpdateInvEntry);			
//			}
			
			List<ExportStuffRequest> result2 = stuffingRepo.searchStuffingContainerWiseSaved(companyId, branchId, autoStuffingId);
			
			return ResponseEntity.ok(result2);
		} catch (Exception e) {
			// Log the exception (consider using a logging framework like SLF4J)
			System.out.println("An error occurred while adding export stuufing entries: " + e.getMessage());
			// Return a ResponseEntity with the error message
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the gate-in entries.");
		}
	}
	
	
	
	
	
	public ResponseEntity<?> searchContainerNoForStuffingConteinerWise(String companyId, String branchId, String containerNo, String type, String profitcentreId) {

		if (type.equals("N")) {
			List<Object[]> result = stuffingRepo.searchContainerNoForStuffingContainerWise(companyId, branchId, containerNo, profitcentreId);
			
			
			
			Map<String, Object> containerMap = result.stream().findFirst().map(row -> {
			    Map<String, Object> map = new HashMap<>();
			    map.put("value", row[0]);
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
			    return map;
			}).orElse(null); // This will be null if no record is found

			
			

			System.out.println("result : "+result + "  \n containerMap" + containerMap);
			
			return ResponseEntity.ok(containerMap);

		} else {
			List<ExportStuffRequest> result2 = stuffingRepo.findLatestStuffingRecordsByContainerNo(companyId, branchId,	containerNo);
			return ResponseEntity.ok(result2);
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}