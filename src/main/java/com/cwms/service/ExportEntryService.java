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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportInventory;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.ExportTransfer;
import com.cwms.entities.GateIn;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.ExportBackToTownRepo;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportInventoryRepository;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportStuffRequestRepo;
import com.cwms.repository.ExportStuffTallyRepo;
import com.cwms.repository.ExportTransferRepositary;
import com.cwms.repository.GateInRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExportEntryService {
	
	@Autowired
	private ObjectMapper objectMapper;	
	
	@Autowired
	private HelperMethods helperMethods;	
	
	@Autowired
	private ExportEntryRepo entryRepo;	
	
	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private ExportSbCargoEntryRepo entryCargoRepo;
	
	@Autowired
	private GateInRepository gateInRepo;	
	
	@Autowired
	private ExportCartingRepo cartingRepo;
	
	
	@Autowired
	private ExportStuffRequestRepo stuffRequestRepo;
	
	@Autowired
	private ExportStuffTallyRepo stuffTallyRepo;
	
	@Autowired
	private ExportInventoryRepository inventoryRepo;
	
	@Autowired
	private ExportTransferRepositary transferRepo;	
	
	@Autowired
	private ExportBackToTownRepo backToTownRepo;
	
	
	public ResponseEntity<?> searchExportMain(String companyId, String branchId, String sbNo, String containerNo)
	{		
		
		try {
		
		System.out.println(companyId +" companyId "+ branchId + " branchId "+ sbNo + " sbNo "+ containerNo + " containerNo ");
		
		List<String> allowedList = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<>();
		Map<String,Object> mainData = new HashMap<>();
		String reWorrkingContainerNo = null;
		
		Pageable pageable = PageRequest.of(0, 1);
		
		if (sbNo != null && !sbNo.trim().isEmpty() 
		        && (containerNo == null || containerNo.trim().isEmpty())) {		
			
			System.out.println("In sbSearch Null 1 ");
			Page<ExportSbCargoEntry> result = entryCargoRepo.getDataForExportMainSearchSbCargo(companyId, branchId, sbNo, pageable);

//			ExportSbCargoEntry existinSbCargoEntry = entryCargoRepo.findFirstByCompanyIdAndBranchIdAndSbNoOrderByCreatedDateDesc(companyId, branchId, sbNo);
			
			ExportSbCargoEntry existinSbCargoEntry = null;
			System.out.println("sbSearched Null 1/2 ");
			if(!result.hasContent())
			{
				System.out.println("sbSearch Null 2 ");
				return ResponseEntity.badRequest().body("No Data Found");
			}
			existinSbCargoEntry = result.getContent().get(0);
			
			String sbType = existinSbCargoEntry.getSbType();
			String mainSbNo = existinSbCargoEntry.getSbNo();
			String mainSbTransId = existinSbCargoEntry.getSbTransId();
			String profitCenter = existinSbCargoEntry.getProfitcentreId();
			
			dataMap.put("sbNo", existinSbCargoEntry.getSbNo());
			dataMap.put("sbLineNo", existinSbCargoEntry.getSrno());
	        dataMap.put("sbTransId", existinSbCargoEntry.getSbTransId());
	        dataMap.put("hsbSbTransId", existinSbCargoEntry.gethSbTransId());
	        dataMap.put("profitCenterId", profitCenter);
	        
	        allowedList.add("P00216");       
			
	        
			
			
			System.out.println(" sbType " + sbType + " mainSbNo "+ mainSbNo + " mainSbTransId " + mainSbTransId);
			if("Normal".equals(sbType))
			{
				
				System.out.println( " checking stuffing quantity 1 : "+ existinSbCargoEntry.getStuffReqQty());
				if(existinSbCargoEntry.getStuffReqQty() > 0)
				{	
					System.out.println( " In stuffing Search : 2 ");

					
					
					
					Page<ExportStuffRequest> stuffingResult = stuffRequestRepo.getDataForExportMainSearchStuff(companyId, branchId, mainSbNo, mainSbTransId, pageable);
//					ExportStuffRequest existinStuffingEntry = stuffRequestRepo.findFirstByCompanyIdAndBranchIdAndSbNoAndSbTransIdOrderByCreatedDateDesc(companyId, branchId, mainSbNo, mainSbTransId);
					
					ExportStuffRequest existinStuffingEntry = null;
					if (stuffingResult.hasContent()) {
						existinStuffingEntry = stuffingResult.getContent().get(0);
					}
					
					
					List<String> resultGateIn = gateInRepo.getDataForExportMainSearchGateInNormal(companyId, branchId, profitCenter, mainSbTransId, mainSbNo, "EXP",pageable);
					String sbGateInId = resultGateIn.isEmpty() ? null : resultGateIn.get(0);
					
					dataMap.put("gateInId", sbGateInId);				        
				        allowedList.add("P00217"); //sbGateIn
					
					
					
					Page<ExportCarting> cartingResult = cartingRepo.getDataForExportMainSearchCarting(companyId, branchId, profitCenter, mainSbNo, mainSbTransId, pageable);
					ExportCarting cartingEntity = cartingResult.getContent().get(0);
					
					
					allowedList.add("P00218"); //carting
					dataMap.put("cartingTransId", cartingEntity.getCartingTransId());	
					dataMap.put("cartingLineId", cartingEntity.getCartingLineId());	
					
					
					
					
					 dataMap.put("stuffingReqId", existinStuffingEntry.getStuffReqId());
					 dataMap.put("containerNo", existinStuffingEntry.getContainerNo());
					 dataMap.put("containerGateInId", existinStuffingEntry.getGateInId());					 
				        
					 allowedList.add("P00219"); //emptyConatiner GateIn
				     allowedList.add("P00220");  // stuffing req
					
				     
				    String mainContainerNo = existinStuffingEntry.getContainerNo();
				    String containerGateInId = existinStuffingEntry.getGateInId();
				    reWorrkingContainerNo = mainContainerNo;
				    
				     
				     ExportInventory inventory = inventoryRepo.getDataForExportMainSearchInventory(companyId, branchId, mainContainerNo, containerGateInId, profitCenter);
					
				     String stuffTallyId = inventory.getStuffTallyId();
				     if(stuffTallyId != null && !stuffTallyId.trim().isEmpty())
				     {				    	 
				    	 dataMap.put("stuffTallyId", stuffTallyId);       
						 
						 						 
						 String movementReqId = inventory.getMovementReqId();						 
						 
						 if(movementReqId != null && !movementReqId.trim().isEmpty())
					     {					     
							 dataMap.put("movementReqId", movementReqId);       
							 
							 
							 String gatePassNo = inventory.getGatePassNo();							 
							 
							 if(gatePassNo != null && !gatePassNo.trim().isEmpty())
						     {						     
								 dataMap.put("gatePassNo", gatePassNo); 
								 dataMap.put("containerType", "CLP"); 
								 
												 
								 
								 String gateOutId = inventory.getGateOutId(); 
								 
									 if(gateOutId != null && !gateOutId.trim().isEmpty())
								     {								     
										 dataMap.put("gateOutId", gateOutId); 
										
								     }//GateOut End				 
									 allowedList.add("P00223"); //gateOutId	 				 
						     } //GatePass End
							 allowedList.add("P00231"); //gatePassNo	
							 
					     } //Movement  End
				    	 
						 allowedList.add("P00238"); //movementReqId
						 
				     } // Tally End
				     
				     allowedList.add("P00221"); //stuffTally				    	 
					 allowedList.add("P00222"); //stuffTally
				     
				     
				     
				     
				     
					
					
				}else
				{
					System.out.println( " In No Stuffing packages : 0 step 1 " + " existinSbCargoEntry.getGateInPackages() : " + existinSbCargoEntry.getGateInPackages());
					
					
					if (existinSbCargoEntry.getGateInPackages() != null && existinSbCargoEntry.getGateInPackages().compareTo(BigDecimal.ZERO) > 0) {
						
						List<String> resultSbGateInId = gateInRepo.getDataForExportMainSearchGateInNormal(companyId, branchId, profitCenter, mainSbTransId, mainSbNo, "EXP",pageable);
						
						
						String sbGateInId = resultSbGateInId.isEmpty() ? null : resultSbGateInId.get(0);
						
						dataMap.put("gateInId", sbGateInId);				        
				      
				        
				        if (existinSbCargoEntry.getCartedPackages() != null && existinSbCargoEntry.getCartedPackages().compareTo(BigDecimal.ZERO) > 0) {

							Page<ExportCarting> cartingResult = cartingRepo.getDataForExportMainSearchCarting(companyId, branchId, profitCenter, mainSbNo, mainSbTransId, pageable);
							ExportCarting cartingEntity = cartingResult.getContent().get(0);							
							
//							allowedList.add("P00218"); //carting
							dataMap.put("cartingTransId", cartingEntity.getCartingTransId());	
							dataMap.put("cartingLineId", cartingEntity.getCartingLineId());							
							
							
							System.out.println(" existinSbCargoEntry.getCartedPackages() "+ existinSbCargoEntry.getCartedPackages() + " existinSbCargoEntry.getNoOfPackages(): "+existinSbCargoEntry.getNoOfPackages());
							if (existinSbCargoEntry.getCartedPackages().compareTo(existinSbCargoEntry.getNoOfPackages()) == 0)
							{
								 allowedList.add("P00219"); //emptyConatiner GateIn
							     allowedList.add("P00220");  // stuffing req
								
							}		
				        }
				        allowedList.add("P00218"); //Carting
						
					}
					  allowedList.add("P00217"); //sbGateIn
					
					
				}
				
				
				
				
			}
//			Buffer and On Wheel
			else
			{	
				 allowedList.add("P00234");
				
//				If Buffer Tally
		    if (existinSbCargoEntry.getStuffedQty() != null && existinSbCargoEntry.getStuffedQty().compareTo(BigDecimal.ZERO) > 0) {
				
				Page<ExportStuffTally> stuffTallyEntity = stuffTallyRepo.getDataForExportMainSsearchStuffTally(companyId, branchId, mainSbNo, mainSbTransId, profitCenter, pageable);
				
//				ExportStuffTally stuffEntity = stuffTallyRepo.findFirstByCompanyIdAndBranchIdAndSbNoAndSbTransIdAndProfitcentreIdOrderByCreatedDateDesc(companyId, branchId, mainSbNo, mainSbTransId, profitCenter);
				ExportStuffTally stuffEntity = stuffTallyEntity.getContent().get(0);
				
				String bufferStuffTallyId = stuffEntity.getStuffTallyId();
				String	mainContainerNoBuffer = stuffEntity.getContainerNo();
				String	containerGateInIdBuffer = stuffEntity.getGateInId();				
				
				ExportInventory inventory = inventoryRepo.getDataForExportMainSearchInventory(companyId, branchId, mainContainerNoBuffer, containerGateInIdBuffer, profitCenter);
				
				 dataMap.put("bufferStuffTallyId", bufferStuffTallyId);       
				 allowedList.add("P00236");
				 
				 dataMap.put("containerNo", mainContainerNoBuffer);
				 dataMap.put("containerGateInId", containerGateInIdBuffer);	
				 reWorrkingContainerNo = mainContainerNoBuffer;
				 
//				 allowedList.add("P00234");
//				 allowedList.add("P00235");
				
				 String movementReqId = inventory.getMovementReqId();						 
				 
				 if(movementReqId != null && !movementReqId.trim().isEmpty())
			     {					     
					 dataMap.put("movementReqId", movementReqId);       
					 
					 
					 String gatePassNo = inventory.getGatePassNo();							 
					 
					 if(gatePassNo != null && !gatePassNo.trim().isEmpty())
				     {						     
						 dataMap.put("gatePassNo", gatePassNo);  
						 dataMap.put("containerType", "Buffer");
										 
						 
						 String gateOutId = inventory.getGateOutId(); 
						 
							 if(gateOutId != null && !gateOutId.trim().isEmpty())
						     {								     
								 dataMap.put("gateOutId", gateOutId);							
						     } //GateOut End				 
							 allowedList.add("P00223"); //gateOutId		 				 
				     } //GatePass End					 
					 allowedList.add("P00231"); //gatePassNo					 
			     } //Movement End
				 allowedList.add("P00238"); //movementReqId			
		     }
		    allowedList.add("P00235");
		    allowedList.add("P00236");
		    
		    	
		}
			
		
			
			
				
				Page<ExportTransfer> sbTransfer = transferRepo.getDataForExportMainSearchSbTransfer(companyId, branchId, profitCenter, mainSbNo, mainSbTransId, pageable);
				
				if(sbTransfer.hasContent())
				{
					ExportTransfer sbTransferEntity = sbTransfer.getContent().get(0);					
					dataMap.put("sbChangeTransId", sbTransferEntity.getSbChangeTransId());  
					 dataMap.put("sbChangeSrNo", sbTransferEntity.getSrNo());
					 allowedList.add("P00228");					
				}
				
				int backToTownPack = existinSbCargoEntry.getBackToTownPack();
				if(backToTownPack > 0) 
				{					
					List<String> backToTownRecord = backToTownRepo.getDataForExportMainSearchBackToTown(companyId, branchId, profitCenter, mainSbTransId, mainSbNo, pageable);
					String backToTownTransId = backToTownRecord.isEmpty() ? null : backToTownRecord.get(0);
					
					 dataMap.put("backToTownTransId", backToTownTransId);
					 allowedList.add("P00225");					
				}
			
			
				 List<String> tallyData = stuffTallyRepo.getDataForExportMainSearchStuffTallyReworking(companyId, branchId, reWorrkingContainerNo, profitCenter);
				 
				 String reworkingId = tallyData.isEmpty() ? null : tallyData.get(0);
				
				 if(reworkingId != null)
				 {
					 dataMap.put("reworkingId", reworkingId);
					 allowedList.add("P00227");		
					 
				 }
				 
			
			
				mainData.put("allowedList", allowedList);
				mainData.put("data", dataMap);
				return ResponseEntity.ok(mainData);
			
		}		
//		Only container or both container and SbNo Present
		else
		{
	
			Page<GateIn> resultGateIn = gateInRepo.getDataForExportMainSearchGateIn(companyId, branchId, containerNo, pageable);

			
//			GateIn gateInEntity = gateInRepo.findFirstByCompanyIdAndBranchIdAndContainerNoOrderByCreatedDateDesc(companyId, branchId, containerNo);

			GateIn gateInEntity = null;
			if (!resultGateIn.hasContent()) {
				System.out.println("No Data found for container");
				return ResponseEntity.badRequest().body("No Data Found");				
			}			
			gateInEntity = resultGateIn.getContent().get(0);
			
			 
			
			
			String profitCenterContainer = gateInEntity.getProfitcentreId();
			String containerNoSearchCont = gateInEntity.getContainerNo();
			String gateInIdContainer = gateInEntity.getGateInId();
			String portSbNo = gateInEntity.getDocRefNo();
			String portSbTransId = gateInEntity.getErpDocRefNo();
//			String portSbLineNo = gateInEntity.getLineNo();
			
			
			
			reWorrkingContainerNo = containerNoSearchCont;
			
			dataMap.put("containerNo", containerNoSearchCont);
			dataMap.put("containerGateInId", gateInIdContainer);	
			 
			ExportSbCargoEntry mainSbCargoEntry = null;
			
			if("EXP".equals(gateInEntity.getGateInType()))// For Normal Container GateIn
			{
				    
				 allowedList.add("P00219"); //emptyConatiner GateIn

				
				
				System.out.println( " In stuffing ContainerNo Search : 1 ");
				Page<ExportStuffRequest> stuffingResult = stuffRequestRepo.getDataForExportMainSearchStuffContainerSearch(companyId, branchId, containerNoSearchCont, gateInIdContainer, profitCenterContainer, sbNo, pageable);
				
//				ExportStuffRequest existinStuffingEntry = stuffRequestRepo.findFirstByCompanyIdAndBranchIdAndContainerNoOrderByCreatedDateDesc(companyId, branchId, containerNoSearchCont, gateInIdContainer, profitCenterContainer, sbNo);
				
				ExportStuffRequest existinStuffingEntry = null;
				if (!stuffingResult.hasContent()) {				
					
					if (sbNo != null && !sbNo.trim().isEmpty())
					{
						System.out.println("No Data found for container");
						return ResponseEntity.badRequest().body("No Data Found");						
					}	
					
					mainData.put("allowedList", allowedList);
					mainData.put("data", dataMap);
					return ResponseEntity.ok(mainData);					
				}
				
				
				existinStuffingEntry = stuffingResult.getContent().get(0);

				String sbNoContainer = existinStuffingEntry.getSbNo();
				String sbTransIdSearchCont = existinStuffingEntry.getSbTransId();
				String sbLineNoContainer = existinStuffingEntry.getSbLineNo();								
				
				Page<ExportSbCargoEntry> result = entryCargoRepo.getDataForExportMainSearchSbCargoContainer(companyId, branchId, sbNoContainer, sbTransIdSearchCont, sbLineNoContainer,pageable);

//				ExportSbCargoEntry existinSbCargoEntry = entryCargoRepo.findFirstByCompanyIdAndBranchIdAndSbNoAndSbTransIdAndSrnoOrderByCreatedDateDesc(companyId, branchId, sbNoContainer, sbTransIdSearchCont, sbLineNoContainer);
				
				ExportSbCargoEntry existinSbCargoEntry = result.getContent().get(0);
				mainSbCargoEntry = existinSbCargoEntry;
				mainSbCargoEntry.setProfitcentreId(profitCenterContainer);
				
				dataMap.put("sbNo", existinSbCargoEntry.getSbNo());
				dataMap.put("sbLineNo", existinSbCargoEntry.getSrno());
		        dataMap.put("sbTransId", existinSbCargoEntry.getSbTransId());
		        dataMap.put("hsbSbTransId", existinSbCargoEntry.gethSbTransId());
		        dataMap.put("profitCenterId", profitCenterContainer);
		        
		        allowedList.add("P00216"); 
				
				
		        List<String> resultSbGateInId = gateInRepo.getDataForExportMainSearchGateInNormal(companyId, branchId, profitCenterContainer, sbTransIdSearchCont, sbNoContainer, "EXP",pageable);
					
				String sbGateInId = resultSbGateInId.isEmpty() ? null : resultSbGateInId.get(0);

					dataMap.put("gateInId", sbGateInId);    				        
			        allowedList.add("P00217"); //sbGateIn			
				
				
				
				Page<ExportCarting> cartingResult = cartingRepo.getDataForExportMainSearchCarting(companyId, branchId, profitCenterContainer, sbNoContainer, sbTransIdSearchCont, pageable);
				ExportCarting cartingEntity = cartingResult.getContent().get(0);
				
				
				allowedList.add("P00218"); //carting
				dataMap.put("cartingTransId", cartingEntity.getCartingTransId());	
				dataMap.put("cartingLineId", cartingEntity.getCartingLineId());	
				
				
				
				
				 dataMap.put("stuffingReqId", existinStuffingEntry.getStuffReqId());
				 dataMap.put("containerNo", existinStuffingEntry.getContainerNo());
				 dataMap.put("containerGateInId", existinStuffingEntry.getGateInId());					 
			        
				 
			     allowedList.add("P00220");  // stuffing req
				
			     
			    String mainContainerNo = existinStuffingEntry.getContainerNo();
			    String containerGateInId = existinStuffingEntry.getGateInId();
			    
			    
			     ExportInventory inventory = inventoryRepo.getDataForExportMainSearchInventory(companyId, branchId, mainContainerNo, containerGateInId, profitCenterContainer);
				
			     String stuffTallyId = inventory.getStuffTallyId();
			     if(stuffTallyId != null && !stuffTallyId.trim().isEmpty())
			     {				    	 
			    	 dataMap.put("stuffTallyId", stuffTallyId);       
					 
					 
					 String movementReqId = inventory.getMovementReqId();						 
					 
					 if(movementReqId != null && !movementReqId.trim().isEmpty())
				     {					     
						 dataMap.put("movementReqId", movementReqId);       
						
						 
						 String gatePassNo = inventory.getGatePassNo();							 
						 
						 if(gatePassNo != null && !gatePassNo.trim().isEmpty())
					     {						     
							 dataMap.put("gatePassNo", gatePassNo); 
							 dataMap.put("containerType", "CLP");
											 
							 
							 String gateOutId = inventory.getGateOutId(); 
							 
								 if(gateOutId != null && !gateOutId.trim().isEmpty())
							     {								     
									 dataMap.put("gateOutId", gateOutId);       
									
							     }//GateOut End				 
								 allowedList.add("P00223"); //gateOutId		 				 
					     } //GatePass End
						 allowedList.add("P00231"); //gatePassNo	
				     } //Movement  End
					 allowedList.add("P00238"); //movementReqId
			     } // Tally End
			     allowedList.add("P00221"); //stuffTally				    	 
				 allowedList.add("P00222");
			     
			     
			     
			    
				
			}
			
			
			
			
			
			
			
			
			
			
			
			else if("Buffer".equals(gateInEntity.getGateInType()) || "ONWH".equals(gateInEntity.getGateInType()))// For Buffer/OnWheel Container GateIn
			{
				allowedList.add("P00234"); //Buffer GateIn
				
				Page<ExportStuffTally> stuffTallyEntity = stuffTallyRepo.getDataForExportMainSearchStuffTallyContainerSearch(companyId, branchId, containerNoSearchCont, gateInIdContainer, profitCenterContainer, sbNo, pageable);
				
				
//				ExportStuffTally existinStuffingEntry = stuffTallyRepo.findFirstByCompanyIdAndBranchIdAndContainerNoAndGateInIdAndProfitcentreIdOrderByCreatedDateDesc(companyId, branchId, containerNoSearchCont, gateInIdContainer, profitCenterContainer, sbNo);
				
				ExportStuffTally existinStuffingEntry = null;
				if (!stuffTallyEntity.hasContent()) {				
					
					if (sbNo != null && !sbNo.trim().isEmpty())
					{
						System.out.println("No Data found for container");
						return ResponseEntity.badRequest().body("No Data Found");						
					}	
						allowedList.add("P00236");
						mainData.put("allowedList", allowedList);
						mainData.put("data", dataMap);
						return ResponseEntity.ok(mainData);					
				}
				existinStuffingEntry = stuffTallyEntity.getContent().get(0);				
				
				Page<ExportSbCargoEntry> result = entryCargoRepo.getDataForExportMainSearchSbCargoContainer(companyId, branchId, existinStuffingEntry.getSbNo(), existinStuffingEntry.getSbTransId(), existinStuffingEntry.getSbLineId(),pageable);

//				ExportSbCargoEntry existinSbCargoEntry = entryCargoRepo.findFirstByCompanyIdAndBranchIdAndSbNoAndSbTransIdAndSrnoOrderByCreatedDateDesc(companyId, branchId, existinStuffingEntry.getSbNo(), existinStuffingEntry.getSbTransId(), existinStuffingEntry.getSbLineId());
				
				ExportSbCargoEntry existinSbCargoEntry = result.getContent().get(0);
				
				mainSbCargoEntry = existinSbCargoEntry;
				mainSbCargoEntry.setProfitcentreId(profitCenterContainer);
				
				dataMap.put("sbNo", existinSbCargoEntry.getSbNo());
				dataMap.put("sbLineNo", existinSbCargoEntry.getSrno());
		        dataMap.put("sbTransId", existinSbCargoEntry.getSbTransId());
		        dataMap.put("hsbSbTransId", existinSbCargoEntry.gethSbTransId());
		        dataMap.put("profitCenterId", profitCenterContainer);
		        
		        allowedList.add("P00216"); 
				
				
				String bufferStuffTallyId = existinStuffingEntry.getStuffTallyId();
				String	mainContainerNoBuffer = existinStuffingEntry.getContainerNo();
				String	containerGateInIdBuffer = existinStuffingEntry.getGateInId();	
				
				
				
				ExportInventory inventory = inventoryRepo.getDataForExportMainSearchInventory(companyId, branchId, mainContainerNoBuffer, containerGateInIdBuffer, profitCenterContainer);
				
				 dataMap.put("bufferStuffTallyId", bufferStuffTallyId);       
				 allowedList.add("P00236");
				
				 String movementReqId = inventory.getMovementReqId();						 
				 
				 if(movementReqId != null && !movementReqId.trim().isEmpty())
			     {					     
					 dataMap.put("movementReqId", movementReqId);       
					
					 
					 String gatePassNo = inventory.getGatePassNo();							 
					 
					 if(gatePassNo != null && !gatePassNo.trim().isEmpty())
				     {						     
						 dataMap.put("gatePassNo", gatePassNo);
						 dataMap.put("containerType", "Buffer");
						 				 
						 
						 String gateOutId = inventory.getGateOutId(); 
						 
							 if(gateOutId != null && !gateOutId.trim().isEmpty())
						     {								     
								 dataMap.put("gateOutId", gateOutId);       
								 
						     } //GateOut End				 
							 allowedList.add("P00223"); //gateOutId	 				 
				     } //GatePass End
					 allowedList.add("P00231"); //gatePassNo	
			     } //Movement End
				 allowedList.add("P00238"); //movementReqId
				
				
				
				
				
				
				
			}
//			Port Return
			else
			{			

				allowedList.add("P00226");
				dataMap.put("portReturnId",  gateInIdContainer);
				
				
				ExportInventory inventory = inventoryRepo.getDataForExportMainSearchInventory(companyId, branchId, containerNoSearchCont, gateInIdContainer, profitCenterContainer);
				
						
				String movementReqId = inventory.getMovementReqId();	
				 
				
				 
				 if (movementReqId == null) {				
						
						if (sbNo != null && !sbNo.trim().isEmpty())
						{
							System.out.println("No Data found for container");
							return ResponseEntity.badRequest().body("No Data Found");						
						}	
						
						
						
						
							mainData.put("allowedList", allowedList);
							mainData.put("data", dataMap);
							return ResponseEntity.ok(mainData);					
					}
				 
				 
				 if (sbNo != null && !sbNo.trim().isEmpty())
					{
				 boolean existInTally = stuffTallyRepo.existsByContainerNoMovementAndSb(companyId, branchId, profitCenterContainer, containerNoSearchCont, sbNo);

					System.out.println("existInTally : "+existInTally);
					
					if(!existInTally)
					{
						System.out.println("No Data found for container");
						return ResponseEntity.badRequest().body("No Data Found");							
					}
					}
				 
				 Page<ExportSbCargoEntry> result = entryCargoRepo.getDataForExportMainSearchSbCargoContainerPortReturn(companyId, branchId, portSbNo, portSbTransId, pageable);

				 ExportSbCargoEntry existinSbCargoEntry = result.getContent().get(0);
					
					mainSbCargoEntry = existinSbCargoEntry;
					mainSbCargoEntry.setProfitcentreId(profitCenterContainer);
					
					dataMap.put("sbNo", existinSbCargoEntry.getSbNo());
					dataMap.put("sbLineNo", existinSbCargoEntry.getSrno());
			        dataMap.put("sbTransId", existinSbCargoEntry.getSbTransId());
			        dataMap.put("hsbSbTransId", existinSbCargoEntry.gethSbTransId());
			        dataMap.put("profitCenterId", profitCenterContainer);
			        
			        allowedList.add("P00216");
				 
				 
			        dataMap.put("movementReqId", movementReqId);       
					 allowedList.add("P00238"); //movementReqId
					 
					 String gatePassNo = inventory.getGatePassNo();							 
					 
					 if(gatePassNo != null && !gatePassNo.trim().isEmpty())
				     {						     
						 dataMap.put("gatePassNo", gatePassNo);
						 dataMap.put("containerType", "Buffer");
						 allowedList.add("P00231"); //gatePassNo					 
						 
						 String gateOutId = inventory.getGateOutId(); 
						 
							 if(gateOutId != null && !gateOutId.trim().isEmpty())
						     {								     
								 dataMap.put("gateOutId", gateOutId);       
								 allowedList.add("P00223"); //gateOutId
						     } //GateOut End				 
							 			 				 
				     } //GatePass End
					 
			    
								
			}
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		
			String profitCenterIdNew = null;
			if(mainSbCargoEntry != null)
			{
				
				String mainSbNo = mainSbCargoEntry.getSbNo();
				String mainSbTransId = mainSbCargoEntry.getSbTransId();
				String profitCenterId = mainSbCargoEntry.getProfitcentreId();
				profitCenterIdNew = profitCenterId;
				
			Page<ExportTransfer> sbTransfer = transferRepo.getDataForExportMainSearchSbTransfer(companyId, branchId, profitCenterId, mainSbNo, mainSbTransId, pageable);
			
			if(sbTransfer.hasContent())
			{
				ExportTransfer sbTransferEntity = sbTransfer.getContent().get(0);					
				dataMap.put("sbChangeTransId", sbTransferEntity.getSbChangeTransId());  
				 dataMap.put("sbChangeSrNo", sbTransferEntity.getSrNo());
				 allowedList.add("P00228");					
			}
			
			int backToTownPack = mainSbCargoEntry.getBackToTownPack();
			
			if(backToTownPack > 0) 
			{					
				List<String> backToTownRecord = backToTownRepo.getDataForExportMainSearchBackToTown(companyId, branchId, profitCenterId, mainSbTransId, mainSbNo, pageable);
				String backToTownTransId = backToTownRecord.isEmpty() ? null : backToTownRecord.get(0);
				
				 dataMap.put("backToTownTransId", backToTownTransId);
				 allowedList.add("P00225");					
			}
			
			}
			

			 List<String> tallyData = stuffTallyRepo.getDataForExportMainSearchStuffTallyReworking(companyId, branchId, reWorrkingContainerNo, profitCenterIdNew);
			 
			 String reworkingId = tallyData.isEmpty() ? null : tallyData.get(0);
			
			 if(reworkingId != null)
			 {
				 dataMap.put("reworkingId", reworkingId);
				 allowedList.add("P00227");		
				 
			 }
			
        
        mainData.put("allowedList", allowedList);
		mainData.put("data", dataMap);
		return ResponseEntity.ok(mainData);
	}
	
}
		
		catch(Exception e)
		{
			System.out.println(" error in a export search : "+ e);
			return ResponseEntity.badRequest().body("Oops something went wrong!!!");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private List<Map<String, String>> convertToValueLabelList(List<String> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", obj);
	        map.put("label", obj);
	        return map;
	    }).collect(Collectors.toList());
	}
	
	
	
	
	public ExportSbEntry getDataForOutOfCharge(String companyId, String branchId, String profitcentreId, String sbTransId, String hSbTransId, String sbNo)
	{			
		return entryRepo.getDataForOutOfCharge(companyId, branchId, hSbTransId, profitcentreId, sbNo, sbTransId);
	}
	
	@Transactional
	public int updateOutOfCharge(String companyId, String branchId, ExportSbEntry exportSbEntry, String user) {
	    try {
	        int updateOutOfCharge = entryRepo.updateOutOfCharge(
	            companyId, 
	            branchId, 
	            exportSbEntry.getSbNo(), 
	            exportSbEntry.getSbTransId(), 
	            exportSbEntry.gethSbTransId(), 
	            exportSbEntry.getOutOfCharge(), 
	            exportSbEntry.getOutOfChargeDate(),	            
	            exportSbEntry.getLeoDate()
	        );

	        System.out.println("updateOutOfCharge : " + updateOutOfCharge);
	        
	        // Optionally handle cases where no rows were updated
	       return updateOutOfCharge;
	    } catch (Exception e) {
	        // Handle other potential exceptions
	        System.err.println("An error occurred while updating OutOfCharge: " + e.getMessage());
	        throw e; // or handle it based on your application's requirement
	    }
	}
	
	
	
	public ResponseEntity<?> searchExportMain(String companyId, String branchId, String sbNo, String vehicleNo, String containerNo, String bookingNo, String pod)
	{		
		
		System.out.println(companyId +" companyId "+ branchId + " branchId "+ sbNo + " sbNo "+ vehicleNo + " containerNo "+ bookingNo + " bookingNo "+ pod + " pod" );
		
		List<String> allowedList = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<>();
		Map<String,Object> mainData = new HashMap<>();
		
		Pageable pageable = PageRequest.of(0, 100);
		List<GateIn> gateInList = gateInRepo.searchLatestExportMain(companyId, branchId, sbNo, vehicleNo, "EXP", pod, pageable);

		if (gateInList.isEmpty()) {		
			System.out.println("1");
			List<ExportSbEntry> existinSbEntry = entryRepo.searchLatestExportMain(companyId, branchId, pod, sbNo, pageable);
			System.out.println("2");
			if(existinSbEntry.isEmpty())
			{
				System.out.println("3");
				return ResponseEntity.badRequest().body("No Data Found");
				
			}
			else
			{
				ExportSbEntry exportSbEntry = existinSbEntry.get(0);
				
				dataMap.put("sbNo", exportSbEntry.getSbNo());
		        dataMap.put("sbTransId", exportSbEntry.getSbTransId());
		        dataMap.put("hsbSbTransId", exportSbEntry.gethSbTransId());
		        dataMap.put("gateInId", "");
		        dataMap.put("profitCenterId", exportSbEntry.getProfitcentreId());
		        
		        allowedList.add("P00216");
		        
				mainData.put("allowedList", allowedList);
				mainData.put("data", dataMap);
				
				return ResponseEntity.ok(mainData);
			}			
		}

		GateIn gateIn = gateInList.get(0);
		
		allowedList.add("P00216");
//		allowedList.add("P00217");
		
		boolean containsEmptyGateInId = gateInList.stream().anyMatch(c -> c.getGateInId() == null || c.getGateInId().isEmpty());
		boolean containsEmptyCartingInId = gateInList.stream().anyMatch(c -> c.getCartingTransId() == null || c.getCartingTransId().isEmpty());

		Optional<GateIn> firstValidCartingIn = gateInList.stream()
			    .filter(c -> c.getCartingTransId() != null && !c.getCartingTransId().isEmpty())
			    .findFirst();
		
		
		System.out.println("containsEmptyGateInId : "+containsEmptyGateInId);
		System.out.println("firstValidCartingId.get() : \n"+ firstValidCartingIn != null ? firstValidCartingIn.get() : "");
		
		System.out.println("EP1 : ");
		ExportSbEntry getsbNoAndPrimary = entryRepo.getsbNoAndPrimary(companyId, branchId, gateIn.getDocRefNo(), gateIn.getErpDocRefNo());
		System.out.println("EP2 : ");
		if (!containsEmptyGateInId) {
			
			if (!containsEmptyGateInId) {
				
				if (firstValidCartingIn != null) {
					System.out.println("EP3 : ");
					GateIn newGateIn = firstValidCartingIn.get();		
					System.out.println("EP4 : ");
					ExportCarting cartingsForMainSearch = cartingRepo.cartingsForMainSearch(companyId, branchId,newGateIn.getDocRefNo(), newGateIn.getCartingTransId());
					System.out.println("EP5 : ");
					allowedList.add("P00218");
					dataMap.put("cartingTransId", newGateIn.getCartingTransId());	
					dataMap.put("cartingLineId", cartingsForMainSearch.getCartingLineId());	
					dataMap.put("cartingprofitCentre", cartingsForMainSearch.getProfitcentreId());
					dataMap.put("cartingSbNo", cartingsForMainSearch.getSbNo());
				}
				
			}
			
			
				allowedList.add("P00217");
				dataMap.put("sbNo", getsbNoAndPrimary.getSbNo());
		        dataMap.put("sbTransId", getsbNoAndPrimary.getSbTransId());
		        dataMap.put("gateInId", gateIn.getGateInId());
		        dataMap.put("profitCenterId", gateIn.getProfitcentreId());
		        dataMap.put("hsbSbTransId", getsbNoAndPrimary.gethSbTransId());
//		        System.out.println("profitCenterId"+ gateIn.getProfitcentreId());
		        mainData.put("allowedList", allowedList);
				mainData.put("data", dataMap);
				return ResponseEntity.ok(mainData);
		}
		
		allowedList.add("P00217");
		dataMap.put("sbNo", getsbNoAndPrimary.getSbNo());
        dataMap.put("sbTransId", getsbNoAndPrimary.getSbTransId());
        dataMap.put("gateInId", gateIn.getGateInId());
        dataMap.put("profitCenterId", getsbNoAndPrimary.getProfitcentreId());
        dataMap.put("hsbSbTransId", getsbNoAndPrimary.gethSbTransId());
        dataMap.put("profitCenterId", gateIn.getProfitcentreId());
		
        System.out.println("profitCenterId"+ gateIn.getProfitcentreId());

        
        mainData.put("allowedList", allowedList);
		mainData.put("data", dataMap);
		return ResponseEntity.ok(mainData);
	}
	
	public ResponseEntity<?> searchSbNosToGateIn(String companyId, String branchId, String searchValue)
	{			
		List<String> sbNos = entryCargoRepo.searchSbNosToGateIn(companyId, branchId, searchValue);
		List<Map<String, String>> sbNoList = convertToValueLabelList(sbNos);		
		return ResponseEntity.ok(sbNoList);
	}
	
	
	public ExportSbEntry saveExportSbEntry(ExportSbEntry exportSbEntry)
	{			
		return entryRepo.save(exportSbEntry);	
	}
	
	
	public ExportSbCargoEntry saveExportSbCargoEntry(ExportSbCargoEntry exportSbCargoEntry)
	{			
		return entryCargoRepo.save(exportSbCargoEntry);
	}
	
	public ResponseEntity<?> getSbCargoGateIn(String companyId, String branchId, String sbNo, String gateInId)
	{			
		List<Object[]> sbCargoGateIn = entryCargoRepo.getSbCargoGateIn(companyId, branchId, sbNo, gateInId);		
		return ResponseEntity.ok(sbCargoGateIn);
	}
	
		
	
	public ExportSbEntry getExportSbEntry(String companyId, String branchId, String sbNo, String sbTransId)
	{			
		return entryRepo.getExportSbEntryBySbNoAndSbTransId(companyId, branchId, sbNo, sbTransId);		
	}
	
	
	public ExportSbCargoEntry getExportSbCargoEntry(String companyId, String branchId, String sbNo, String sbTransId)
	{			
		return entryCargoRepo.getExportSbEntryBySbNoAndSbTransId(companyId, branchId, sbNo, sbTransId);		
	}
	
	
	public List<Object[]> getSbEntriesToSelect(String companyId, String branchId, String searchValue)
	{
				
		return entryCargoRepo.getSbEntriesData(companyId, branchId, searchValue);
	}
	
	
	public ResponseEntity<?> getSelectedSbEntry(String companyId, String branchId, String sbTransId, String hsbTransId, String sbNo,String profitCenterId)
	{
		
		ExportSbEntry sbEntrySaved = entryRepo.getExportSbEntryBySbTransIdAndSbNo(companyId, branchId, profitCenterId, sbTransId, hsbTransId, sbNo);		
		List<ExportSbCargoEntry> cargoEntries = entryCargoRepo.getExportSbCargoEntryBySbTransIdAndSbNo(companyId, branchId, profitCenterId, hsbTransId);
		
		Map<String, Object> response = new HashMap<>();
		response.put("cargoEntries", cargoEntries);
		response.put("sbEntrySaved", sbEntrySaved);

		// Return the map wrapped in a ResponseEntity
		return ResponseEntity.ok(response);

	
	}
	
	
	
	public boolean checkDuplicateSbNo(String companyId, String branchId, String finYear, String profitCenterId, String sbTransId, String sbNo)
	{
		
		String updatedFinYear = (finYear != null && !finYear.trim().isEmpty()) 
                ? finYear 
                : helperMethods.getFinancialYear();
		
		return entryCargoRepo.existsBySbNo(companyId, branchId, updatedFinYear, profitCenterId,sbTransId, sbNo);
	}
	
	
	@Transactional
	public ResponseEntity<?> addExportSbEntry(String companyId, String branchId, Map<String, Object> requestData, String User)
	{
		Date currentDate = new Date();
		
		
		List<ExportSbCargoEntry> sbCargoEntry = objectMapper.convertValue(requestData.get("exportSbCargoEntry"),
				new TypeReference<List<ExportSbCargoEntry>>() {
				});
				
		
		ExportSbEntry sbEntry = objectMapper.convertValue(requestData.get("exportSbEntry"), ExportSbEntry.class);
			
		
//		String autoSBTransId = processService.autoSBTransId(companyId, branchId, "P00101");	
		// Check if any sbTransId is null, empty, or contains only whitespace
		String autoSBTransId = "";
		
		if(sbEntry.getSbNo() != null && !sbEntry.getSbNo().trim().isEmpty())
		{
			autoSBTransId = (sbEntry.getSbTransId() != null && !sbEntry.getSbTransId().trim().isEmpty()) 
	                ? sbEntry.gethSbTransId() 
	                : processService.autoSBTransId(companyId, branchId, "P00101"); 
		}
		
//		String autoSBTransId = (sbEntry.getSbNo() != null && !sbEntry.getSbNo().trim().isEmpty() &&
//                sbEntry.getSbTransId() != null && !sbEntry.getSbTransId().trim().isEmpty())
//				? sbEntry.gethSbTransId()
//				: processService.autoSBTransId(companyId, branchId, "P00101");


		
		String existingTransId = (sbEntry.gethSbTransId() != null && !sbEntry.gethSbTransId().trim().isEmpty()) 
                ? sbEntry.gethSbTransId() 
                : autoSBTransId;
		
		String existingSBTransId = (sbEntry.getSbTransId() != null && !sbEntry.getSbTransId().trim().isEmpty()) 
                ? sbEntry.getSbTransId() 
                : autoSBTransId;
		
		sbEntry.setSbTransId(existingSBTransId);
		sbEntry.sethSbTransId(existingTransId);
		
		sbEntry.setFinYear(helperMethods.getFinancialYear());
		
		
		System.out.println("helperMethods.getFinancialYear() \n"+helperMethods.getFinancialYear());
		if(!sbEntry.getStatus().equals("A"))
		{
			sbEntry.setStatus("A");
			sbEntry.setCreatedBy(User);
			sbEntry.setCreatedDate(currentDate);
			sbEntry.setApprovedBy(User);
			sbEntry.setApprovedDate(currentDate);
		}
		
		
		
		sbEntry.setEditedBy(User);
		sbEntry.setEditedDate(currentDate);
		
		
		
		System.out.println(sbEntry);
		for(ExportSbCargoEntry cargoEntry : sbCargoEntry)
		{
			boolean existsBySbNo = entryCargoRepo.existsBySbNo(cargoEntry.getCompanyId(), cargoEntry.getBranchId(), sbEntry.getFinYear(), cargoEntry.getProfitcentreId(),cargoEntry.getSbTransId(),cargoEntry.getSbNo());
			
			
			 if (existsBySbNo) {
		            String errorMessage = "Duplicate SB No found for SrNo: " + cargoEntry.getSrno() + " and SB No: " + cargoEntry.getSbNo();
		            return ResponseEntity.badRequest().body(errorMessage);
		        }
			
			ExportSbEntry sbEntryNew = entryRepo.getExportSbEntryBySbTransId(cargoEntry.getCompanyId(), cargoEntry.getBranchId(), cargoEntry.getProfitcentreId(), cargoEntry.getSbTransId());
//			if (sbEntryNew != null && !sbEntryNew.getSbNo().equals(cargoEntry.getSbNo())) {			
//			    int updateChangeSbNo = entryRepo.updateChangeSbNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate());
//			    int updateChangeSbCargoNo = entryCargoRepo.updateChangeSbCargoNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate());
//			    System.out.println("updateChangeSbNo: " + updateChangeSbNo + " updateChangeSbCargoNo "+updateChangeSbCargoNo);			    			
//			}
			
			if (sbEntryNew != null && 
				    (!sbEntryNew.getSbNo().equals(cargoEntry.getSbNo()) || 
				     sbEntryNew.getTotalPackages().compareTo(cargoEntry.getNoOfPackages()) != 0 || 
				     sbEntryNew.getTotalGrossWeight().compareTo(cargoEntry.getGrossWeight()) != 0)) {
				    
				    int updateChangeSbNo = entryRepo.updateChangeSbNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate(),cargoEntry.getNoOfPackages(),cargoEntry.getGrossWeight());
				    int updateChangeSbCargoNo = entryCargoRepo.updateChangeSbCargoNo(companyId, branchId, sbEntryNew.getSbNo(), sbEntryNew.getSbTransId(), cargoEntry.getSbNo(), cargoEntry.getSbDate());
				    System.out.println("updateChangeSbNo: " + updateChangeSbNo + " updateChangeSbCargoNo "+updateChangeSbCargoNo);			    			
				}

			
			
			
			
			
			System.out.println("Before \n "+cargoEntry);
			String existingCargoTransId = (cargoEntry.getSbTransId() != null && !cargoEntry.getSbTransId().trim().isEmpty()) 
	                ? cargoEntry.getSbTransId() 
	                : autoSBTransId;
			
			System.out.println("Applied existingCargoTransId : "+existingCargoTransId);
			
			cargoEntry.setSbTransId(existingCargoTransId);
			
			
			cargoEntry.sethSbTransId(existingTransId);
			
			
			if(!cargoEntry.getStatus().equals("A"))
			{
				cargoEntry.setFinYear(helperMethods.getFinancialYear());				
				cargoEntry.setCreatedBy(User);
				cargoEntry.setCreatedDate(currentDate);	
				cargoEntry.setApprovedBy(User);
				cargoEntry.setApprovedDate(currentDate);
				cargoEntry.setStatus("A");
			}
			
				
			
			System.out.println("After \n"+cargoEntry);
			
		}
		List<ExportSbCargoEntry> cargoEntries = entryCargoRepo.saveAll(sbCargoEntry);
		ExportSbEntry sbEntrySaved = (sbEntry.getSbNo() != null && !sbEntry.getSbNo().isEmpty()) ? entryRepo.save(sbEntry) : sbEntry;
		
		Map<String, Object> response = new HashMap<>();
		response.put("cargoEntries", cargoEntries);
		response.put("sbEntrySaved", sbEntrySaved);

		// Return the map wrapped in a ResponseEntity
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	

}