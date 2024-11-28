package com.cwms.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.EquipmentActivity;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.ExportTransfer;
import com.cwms.entities.ExportTransferDetail;
import com.cwms.entities.GateIn;
import com.cwms.entities.Impexpgrid;
import com.cwms.helper.HelperMethods;
import com.cwms.repository.EquipmentActivityRepository;
import com.cwms.repository.ExportCartingRepo;
import com.cwms.repository.ExportEntryRepo;
import com.cwms.repository.ExportSbCargoEntryRepo;
import com.cwms.repository.ExportTransferDetailRepositary;
import com.cwms.repository.ExportTransferRepositary;
import com.cwms.repository.GateInRepository;
import com.cwms.repository.Impexpgridrepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ExportTransferService {

	@Autowired
	private ExportTransferDetailRepositary transDtlRepo; 

	@Autowired
	private ExportTransferRepositary transRepo;	

	@Autowired
	private ObjectMapper objectMapper; 
	
	@Autowired
	private ProcessNextIdService processService;
	
	@Autowired
	private HelperMethods helperMethods;
	
	@Autowired
	private GateInRepository gateInRepo;
	
	@Autowired
	public ExportCartingRepo cartingRepo;
	
	@Autowired
	public Impexpgridrepo impGridRepo;
	
	@Autowired
	public EquipmentActivityRepository equipmentRepo;
	
	@Autowired
	private ExportSbCargoEntryRepo cargoEntry;
	
	@Autowired
	private ExportEntryRepo sbEntry;
	
	
	public ResponseEntity<?> getSelectedTransferEntry(String companyId, String branchId, String sbChangeTransId, String srNo,String profitCenterId)
	{		
		ExportTransfer selectedGateInEntry = transRepo.getSelectedTransferEntry(companyId, branchId, profitCenterId, sbChangeTransId, srNo);
				
		System.out.println(companyId+ " " + branchId+ " " + sbChangeTransId+ " " + srNo);
		List<ExportTransferDetail> selectedTransferDetailEntry = transDtlRepo.getSelectedTransferDetailEntry(companyId, branchId, sbChangeTransId, srNo);
		
		Map<String, Object> response = new HashMap<>();
		response.put("exportTransfer", selectedGateInEntry);
		response.put("exportTransferDetail", selectedTransferDetailEntry);
		
		return ResponseEntity.ok(response);
	}
	
	
	public List<ExportTransfer> getTransferEntriesToSelect(String companyId, String branchId, String searchValue, String profitCenter)
	{				
		return transRepo.getSavedTransferRecords(companyId, branchId, profitCenter, searchValue);
	}
	
	
	
	
	@Transactional
	public ResponseEntity<?> addExportTransferEntry(String companyId, String branchId, Map<String, Object> requestData, String user)
	{
		Date currentDate = new Date();
		String financialYear = helperMethods.getFinancialYear();
		
		
		
		
		List<ExportTransferDetail> transferDetail = objectMapper.convertValue(requestData.get("exportTransferDetail"),
				new TypeReference<List<ExportTransferDetail>>() {
				});				
		
		List<ExportTransferDetail> listToSave = new ArrayList<>();
		
		ExportTransfer transfer = objectMapper.convertValue(requestData.get("exportTransfer"), ExportTransfer.class);
				
		String autoExportTransferId = processService.autoExportStuffingId(companyId, branchId, "P00111");
		
		
		
		BigDecimal trfCartedPackages = BigDecimal.ZERO;
		BigDecimal trfGateInWeight = BigDecimal.ZERO;
		BigDecimal trfCartedWeight = BigDecimal.ZERO;
		

		BigDecimal cartedWeight = BigDecimal.ZERO;
		BigDecimal cartedPackages = BigDecimal.ZERO;
			
		
		
		
		
		
		
		
		
		transfer.setSbChangeTransId(autoExportTransferId);
		transfer.setSbChangeTransDate(currentDate);
		transfer.setFinYear(financialYear);
		transfer.setStatus("A");
		transfer.setCreatedBy(user);
		transfer.setCreatedDate(currentDate);
		transfer.setEditedBy(user);
		transfer.setEditedDate(currentDate);
		transfer.setApprovedBy(user);
		transfer.setApprovedDate(currentDate);	
		
		
		System.out.println("transfer : "+ transfer);
		
		int transLineId = 1;
		
		for( ExportTransferDetail detail : transferDetail )
		{		
			
			
			System.out.println("Detail : "+ detail);
			if (detail.getTrfGateInPackages().compareTo(BigDecimal.ZERO) > 0) {		    
				
				
				GateIn gateInRecord = gateInRepo.getGateInByIds(companyId, branchId, transfer.getProfitcentreId(), detail.getCartingTransId(), detail.getSbTransId(), detail.getSbNo(), "EXP");
            	String autoGateInId = processService.autoExportGateInId(companyId, branchId, "P00102");
            	System.out.println("gateInRecord : "+ gateInRecord);
				
				if (detail.getTrfGateInPackages() != null && gateInRecord.getQtyTakenIn() != null &&
					    detail.getTrfGateInPackages().compareTo(gateInRecord.getQtyTakenIn()) == 0) {
					

//					int maxLineId = gateInRepo.getMaxLineId(companyId, branchId, transfer.getProfitcentreId(), autoGateInId,  detail.getTrfSbTransId(), detail.getTrfSbNo(), "EXP");
					
					List<EquipmentActivity> allEquipmentTransfer = equipmentRepo.getAllEquipmentTransfer(companyId, branchId, transfer.getProfitcentreId(),detail.getSbTransId(), detail.getSbNo(), gateInRecord.getGateInId());
					
					List<EquipmentActivity> newCloneEquipment = new ArrayList<>();
					
					int getsrNo = 1;
					for(EquipmentActivity euip : allEquipmentTransfer)
					{
						try {
							EquipmentActivity equipClone = (EquipmentActivity) euip.clone();
							
//							int getsrNo = equipmentRepo.getHighestSrNoNew(companyId, branchId, detail.getTrfSbTransId(), detail.getTrfSbNo(), autoGateInId);

							equipClone.setSrNo(getsrNo + 1);
							equipClone.setDocRefNo(detail.getTrfSbNo());
							equipClone.setErpDocRefNo(detail.getTrfSbTransId());
							equipClone.setDeStuffId(autoGateInId);
							
							getsrNo++;
							newCloneEquipment.add(equipClone);
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}			
						
						euip.setStatus("D");
					}
					
					newCloneEquipment.addAll(allEquipmentTransfer);
					equipmentRepo.saveAll(newCloneEquipment);
					
					
						gateInRecord.setStatus("D");										
					}
//						gateInRecord.setQtyTakenIn(gateInRecord.getQtyTakenIn().subtract(detail.getTrfGateInPackages()));
//						gateInRecord.setCargoWeight(gateInRecord.getCargoWeight().subtract(detail.getTrfgateInWeight()));
//						gateInRecord.setTransferPackages(gateInRecord.getTransferPackages().add(detail.getTrfGateInPackages()));
					
				
								
				
				
				
				System.out.println("detail.getTrfGateInPackages() "+ detail.getTrfGateInPackages() + " gateInRecord.getQtyTakenIn( )"+ gateInRecord.getQtyTakenIn() + " gateInRecord.getQtyTakenIn().subtract(gateInRecord.getCartedPackages())"+gateInRecord.getQtyTakenIn().subtract(gateInRecord.getCartedPackages()));
				
				BigDecimal packagesToTransfer = detail.getTrfGateInPackages();
				BigDecimal totalTakenIn = gateInRecord.getQtyTakenIn();
				BigDecimal totalCarted = gateInRecord.getCartedPackages();
				BigDecimal remainingNonCarted = totalTakenIn.subtract(totalCarted);
				BigDecimal fromCarted = packagesToTransfer.subtract(remainingNonCarted);
				
				System.out.println("remainingNonCarted " +remainingNonCarted + " fromCarted "+fromCarted );
				// Calculate remaining non-carted packages
				
				if (detail.getTrfGateInPackages().compareTo(gateInRecord.getQtyTakenIn().subtract(gateInRecord.getCartedPackages())) > 0) {
					
					
					
					String autoCartingTransId = processService.autoCartingTransId(companyId, branchId, "P00103");

//					int maxLineId = cartingRepo.getMaxLineId(companyId, branchId, transfer.getProfitcentreId(), autoCartingTransId);
					int maxLineId = 1;
					
					
					gateInRecord.setCartedPackages(gateInRecord.getCartedPackages().subtract(fromCarted));
					
					System.out.println(" in loop detail.getTrfCartedPackages().compareTo(gateInRecord.getQtyTakenIn().subtract(gateInRecord.getCartedPackages()))" + detail.getTrfGateInPackages().compareTo(gateInRecord.getQtyTakenIn().subtract(gateInRecord.getCartedPackages())));
				List<ExportCarting> cartingForSbTransfer = cartingRepo.getCartingForSbTransfer(companyId, branchId, gateInRecord.getProfitcentreId(), gateInRecord.getGateInId(), gateInRecord.getErpDocRefNo(), gateInRecord.getDocRefNo());
				
				if (cartingForSbTransfer != null && !cartingForSbTransfer.isEmpty()) {
				    
					BigDecimal transferedPackages = fromCarted;

					for (ExportCarting carting : cartingForSbTransfer) {
						
						
						
						
						 BigDecimal actualNoOfPackages = carting.getActualNoOfPackages();
						    BigDecimal toSubtract = actualNoOfPackages.min(transferedPackages);
						    
						    
						    
						    BigDecimal areaCarted = gateInRecord.getGrossWeight().multiply(actualNoOfPackages)
	                                .divide(gateInRecord.getQtyTakenIn(), MathContext.DECIMAL128);							 
						    
							
							
							cartedWeight = cartedWeight.add(areaCarted);
							cartedPackages = cartedPackages.add(actualNoOfPackages);
//							detail.setTrfCartedPackages(detail.getTrfCartedPackages().add(toSubtract));
//							detail.setTrfCartedWeight(detail.getCartedWeight().add(areaCarted));
							
							detail.setTrfCartedPackages(
								    (detail.getTrfCartedPackages() != null ? detail.getTrfCartedPackages() : BigDecimal.ZERO).add(toSubtract)
								);

								detail.setTrfCartedWeight(
								    (detail.getCartedWeight() != null ? detail.getCartedWeight() : BigDecimal.ZERO).add(areaCarted)
								);

							
							
						    BigDecimal proportionalArea = gateInRecord.getGrossWeight().multiply(toSubtract)
                                    .divide(gateInRecord.getQtyTakenIn(), MathContext.DECIMAL128);							 
						    
						    trfCartedWeight = trfCartedWeight.add(proportionalArea);	    
						    
						    trfCartedPackages = trfCartedPackages.add(toSubtract);
						
						if (detail.getTrfGateInPackages() != null && carting.getActualNoOfPackages() != null &&
							    detail.getTrfGateInPackages().compareTo(carting.getActualNoOfPackages()) == 0) {
														
//							Equipments 
							List<EquipmentActivity> allEquipmentTransfer = equipmentRepo.getAllEquipmentTransfer(companyId, branchId, transfer.getProfitcentreId(),detail.getSbTransId(), detail.getSbNo(), carting.getCartingTransId());
							List<EquipmentActivity> newCloneEquipment = new ArrayList<>();
							
							int getsrNo = 1;
							for(EquipmentActivity euip : allEquipmentTransfer)
							{
								try {
									EquipmentActivity equipClone = (EquipmentActivity) euip.clone();
									
//									int getsrNo = equipmentRepo.getHighestSrNoNew(companyId, branchId, detail.getTrfSbTransId(), detail.getTrfSbNo(), autoCartingTransId);

									equipClone.setSrNo(getsrNo);
									equipClone.setDocRefNo(detail.getTrfSbNo());
									equipClone.setErpDocRefNo(detail.getTrfSbTransId());
									equipClone.setDeStuffId(autoCartingTransId);
									
									getsrNo++;
									newCloneEquipment.add(equipClone);
								} catch (CloneNotSupportedException e) {
									e.printStackTrace();
								}			
								
								euip.setStatus("D");
							}
							
							newCloneEquipment.addAll(allEquipmentTransfer);
							equipmentRepo.saveAll(newCloneEquipment);
													
							carting.setStatus("D");	
							
						}
						
						
//						impExpGrid
						
						
					   
					    carting.setActualNoOfPackages(actualNoOfPackages.subtract(toSubtract));
					    carting.setYardPackages(carting.getYardPackages().subtract(toSubtract));
					    
					    
					    transferedPackages = transferedPackages.subtract(toSubtract);
					    carting.setFromSbLineNo(detail.getTrfSbLineNo());
					    carting.setFromSbNo(detail.getTrfSbNo());	
					    carting.setFromSbTransId(detail.getTrfSbTransId());
					    
					    
					    try {
							ExportCarting cartingClone = (ExportCarting) carting.clone();	
							
							
														
							cartingClone.setCartingTransId(autoCartingTransId);
							cartingClone.setCartingLineId(String.valueOf(maxLineId + 1));
							cartingClone.setGateInId(autoGateInId);
							cartingClone.setStatus("A");
							
							cartingClone.setActualNoOfPackages(toSubtract);
							cartingClone.setGateInPackages(toSubtract);
							cartingClone.setYardPackages(toSubtract);
							
							
							cartingClone.setSbNo(detail.getTrfSbNo());
							cartingClone.setSbTransId(detail.getTrfSbTransId());
							cartingClone.setSbLineNo(detail.getSbLineNo());
							cartingClone.setSbDate(transfer.getTrfSbDate());
							cartingClone.setOnAccountOf(detail.getOnAccountOf());
							
							
							
							
							 BigDecimal yardPackagesToBeTransfered = toSubtract;

								List<Impexpgrid> gridEntries = impGridRepo.getAllImpExpGridCarting(companyId, branchId, carting.getCartingTransId(), carting.getCartingLineId(), "EXP");

								List<Impexpgrid> newCloneGrid = new ArrayList<>();

								int maxSubSrNo = 1;
								for(Impexpgrid grid : gridEntries)
								{
									 BigDecimal actualGridPackages = BigDecimal.valueOf(grid.getYardPackages());
									 BigDecimal yardToSubtract = actualGridPackages.min(yardPackagesToBeTransfered);
									 BigDecimal cellAreaAllocated = grid.getCellAreaAllocated();
									
									 BigDecimal proportionalArea2 = cellAreaAllocated.multiply(yardToSubtract)
                                             .divide(actualGridPackages, MathContext.DECIMAL128);
									 
									 
									
									 
									 
									 try {
										Impexpgrid gridClone = (Impexpgrid) grid.clone();								
										
										
//						                int maxSubSrNo = impGridRepo.getMaxSubSrNo(companyId, branchId, cartingClone.getCartingTransId(), Integer.parseInt(cartingClone.getCartingLineId()));
						                gridClone.setSubSrNo(maxSubSrNo + 1);
						                gridClone.setProcessTransId(cartingClone.getCartingTransId());
						                gridClone.setLineNo(Integer.parseInt(cartingClone.getCartingLineId()));
						                gridClone.setYardPackages(yardToSubtract.intValue());
						                
						                
						                gridClone.setCellAreaAllocated(proportionalArea2);

										newCloneGrid.add(gridClone);
										
										yardPackagesToBeTransfered = yardPackagesToBeTransfered.subtract(yardToSubtract);
										maxSubSrNo++;
										
									} catch (CloneNotSupportedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}								
									
									 grid.setYardPackages(grid.getYardPackages() - yardToSubtract.intValue());
									 grid.setCellAreaAllocated(grid.getCellAreaAllocated().subtract(proportionalArea));
									 grid.setCellAreaUsed(grid.getCellAreaUsed().subtract(proportionalArea));
									 grid.setStatus(grid.getYardPackages() == 0 ? "D" : grid.getStatus());
									 
									 
									 if (yardPackagesToBeTransfered.compareTo(BigDecimal.ZERO) == 0) {
									        break;
									    } 
									 
								}
								
								newCloneGrid.addAll(gridEntries);
								impGridRepo.saveAll(newCloneGrid);
								
								
//							impExpGrid
							
							
							cartingRepo.save(cartingClone);
							
						} catch (CloneNotSupportedException e) {
							e.printStackTrace();
						}
					    
					    
					    
					    
					    if (transferedPackages.compareTo(BigDecimal.ZERO) == 0) {
					        break;
					    }
					}

					
					
				}
				
				
			}
				
				gateInRecord.setQtyTakenIn(
					    gateInRecord.getQtyTakenIn().subtract(detail.getTrfGateInPackages() != null ? detail.getTrfGateInPackages() : BigDecimal.ZERO)
					);

					gateInRecord.setCargoWeight(
					    gateInRecord.getCargoWeight().subtract(detail.getTrfGateInWeight() != null ? detail.getTrfGateInWeight() : BigDecimal.ZERO)
					);

					gateInRecord.setTransferPackages(
					    gateInRecord.getTransferPackages().add(detail.getTrfGateInPackages() != null ? detail.getTrfGateInPackages() : BigDecimal.ZERO)
					);


						
						trfGateInWeight = trfGateInWeight.add(detail.getTrfGateInWeight());
				
				gateInRecord.setEditedBy(user);
				gateInRecord.setEditedDate(currentDate);
				gateInRecord.setFromSbLineNo(detail.getTrfSbLineNo());
				gateInRecord.setFromSbNo(detail.getTrfSbNo());	
				gateInRecord.setFromSbTransId(detail.getTrfSbTransId());
				System.out.println("gateInRecord AFTER : "+gateInRecord);
				
				gateInRepo.save(gateInRecord);
				try {
										
					GateIn newGateIn = (GateIn) gateInRecord.clone();					
					
					
					newGateIn.setGateInId(autoGateInId);
					newGateIn.setSrNo(1);
					
					newGateIn.setDocRefNo(detail.getTrfSbNo());
					newGateIn.setErpDocRefNo(detail.getTrfSbTransId());
					newGateIn.setLineNo(detail.getTrfSbLineNo());
					newGateIn.setDocRefDate(transfer.getTrfSbDate());
					newGateIn.setOnAccountOf(detail.getOnAccountOf());
					newGateIn.setCha(detail.getCha());
					newGateIn.setCommodityDescription(transfer.getToCommodity());
					newGateIn.setActualNoOfPackages(detail.getActualNoOfPackages());					
					
					newGateIn.setGrossWeight(detail.getGrossWeight());
					newGateIn.setQtyTakenIn(detail.getTrfGateInPackages());
					newGateIn.setCargoWeight(detail.getTrfCartedWeight());
					
					newGateIn.setCartedPackages(fromCarted);
					
					
					int maxSubSrNo = gateInRepo.getMaxLineId(companyId, branchId, transfer.getProfitcentreId(), detail.getCartingLineId(), detail.getSbTransId(), detail.getSbNo(), "EXP");
					newGateIn.setSrNo(maxSubSrNo + 1);
					newGateIn.setEditedBy(user);
					newGateIn.setEditedDate(currentDate);
					newGateIn.setStatus("A");
					
					gateInRepo.save(newGateIn);				
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				
				
			
			detail.setSbChangeTransId(autoExportTransferId);
			detail.setFinYear(financialYear);
			detail.setStatus("A");
			detail.setCreatedBy(user);
			detail.setCreatedDate(currentDate);
			detail.setEditedBy(user);
			detail.setEditedDate(currentDate);
			detail.setApprovedBy(user);
			detail.setApprovedDate(currentDate);	
			detail.setFinYear(helperMethods.getFinancialYear());
			detail.setSbChangeTransId(autoExportTransferId);
			detail.setTransLineId(String.valueOf(transLineId));
			
			transLineId++;
			listToSave.add(detail);
			}
		}
		
		
		
		transfer.setTrfCartedPackages(trfCartedPackages);
		transfer.setTrfCartedPackages(trfCartedWeight);
		transfer.setTrfGateInWeight(trfGateInWeight);
		transfer.setCartedPackages(cartedPackages);
		transfer.setCartedWeight(cartedWeight);
		transfer.setSrNo("1");
		
		
		
		BigDecimal trfGrossWeight = transfer.getTrfGrossWeight();
		BigDecimal trfNoOfPackages = transfer.getTrfNoOfPackages();
		BigDecimal transferPackages = transfer.getTransferPackages();
		
		BigDecimal transferGrossWeight = trfGrossWeight
			        .divide(trfNoOfPackages, MathContext.DECIMAL64) 
			        .multiply(transferPackages != null ? transferPackages : BigDecimal.ZERO);
		transfer.setTransferGrossWeight(transferGrossWeight);
			
		
		int updateFromSbNoTransfer = cargoEntry.updateFromSbNoTransfer(companyId, branchId, transfer.getSbNo(), transfer.getSbTransId(), trfCartedPackages, transfer.getTransferPackages());
				
		int updateToSbNoTransfer = cargoEntry.updateToSbNoTransfer(companyId, branchId, transfer.getTrfSbNo(), transfer.getTrfSbTransId(), trfCartedPackages, transfer.getTransferPackages());

		int updateFromSbNoTransferSb = sbEntry.updateFromSbNoTransfer(companyId, branchId, transfer.getSbNo(), transfer.getSbTransId(), transfer.getTransferPackages());
		
		int updateToSbNoTransferSb = sbEntry.updateToSbNoTransfer(companyId, branchId, transfer.getTrfSbNo(), transfer.getTrfSbTransId(), transfer.getTransferPackages());

		
		
//		main update to a ExportCrgoEntry
		
		System.out.println("transfer AFTER \n"+ transfer + " \n AFTER transferDetail  " + listToSave 
				+ " \n updateFromSbNoTransfer : "+updateFromSbNoTransfer + " updateToSbNoTransfer " + updateToSbNoTransfer);
		
		ExportTransfer exportTransfer = transRepo.save(transfer);
		List<ExportTransferDetail> exportTransferDetail = transDtlRepo.saveAll(listToSave);
		
		Map<String, Object> response = new HashMap<>();
		response.put("exportTransfer", exportTransfer);
		response.put("exportTransferDetail", exportTransferDetail);
		
		return ResponseEntity.ok(response);
	}
	
	
//	public ResponseEntity<?> gateTateInEntriesFromSbNo(String companyId, String branchId, String profitcentreId, String sbNo, String sbTransId, String sbLineNo) {
//
//	    List<Object[]> gateTateInEntriesFromSbNo = transDtlRepo.gateTateInEntriesFromSbNo(companyId, branchId, profitcentreId, sbNo, sbTransId);
//
//	    List<ExportTransferDetail> detailList = new ArrayList<>();
//	    BigDecimal remainingOutPackages = gateTateInEntriesFromSbNo.stream()
//	        .map(entry -> entry[5] != null ? new BigDecimal(entry[5].toString()) : BigDecimal.ZERO)
//	        .reduce(BigDecimal.ZERO, BigDecimal::add); // Total outPackages from all records
//
//	    for (Object[] gateIn : gateTateInEntriesFromSbNo) {
//	        ExportTransferDetail detail = new ExportTransferDetail();
//
//	        // Convert and set fields
//	        detail.setCompanyId(companyId);
//	        detail.setBranchId(branchId);
//	        detail.setCartingLineId(gateIn[7] != null ? gateIn[7].toString() : null);
//	        detail.setCartingTransId((String) gateIn[0]);
//	        detail.setVehicleNo((String) gateIn[2]);
//	        detail.setGateInId((String) gateIn[0]);
//	        detail.setSbNo(sbNo);
//	        detail.setSbTransId(sbTransId);
//	        detail.setSbLineNo(sbLineNo);
//
//	        detail.setGateInDate(gateIn[1] instanceof java.sql.Timestamp ?
//	            new java.util.Date(((java.sql.Timestamp) gateIn[1]).getTime()) :
//	            (java.util.Date) gateIn[1]);
//
//	        // Actual number of packages for the gate-in
//	        BigDecimal actualNoOfPackages = gateIn[9] != null ? new BigDecimal(gateIn[9].toString()) : BigDecimal.ZERO;
//
//	        // Gate-in and out package information
//	        BigDecimal gateInPackages = gateIn[3] != null ? new BigDecimal(gateIn[3].toString()) : BigDecimal.ZERO;
//
//	        
//	        
//	        // Calculate outPackages for this gate-in
//	        BigDecimal currentOutPackages;
//	        if (remainingOutPackages.compareTo(gateInPackages) <= 0) {
//	            currentOutPackages = remainingOutPackages;
//	            remainingOutPackages = BigDecimal.ZERO;
//	        } else {
//	            currentOutPackages = gateInPackages;
//	            remainingOutPackages = remainingOutPackages.subtract(gateInPackages);
//	        }
//	        System.out.println(" currentOutPackages "+ currentOutPackages + " remainingOutPackages "+remainingOutPackages+"");
//	        
//	        // Calculate balance packages
//	        BigDecimal balancePkgs = gateInPackages.subtract(currentOutPackages);
//
//	        // Set values in detail object
//	        detail.setGateInPackages(gateInPackages);
//	        detail.setActualNoOfPackages(actualNoOfPackages);
//	        detail.setBalancePkgs(balancePkgs);
//	        detail.setOutPkgs(currentOutPackages);
//	        detail.setNilPackages(gateIn[6] != null ? new BigDecimal(gateIn[6].toString()) : BigDecimal.ZERO);
//	        detail.setGateInWeight(gateIn[8] != null ? new BigDecimal(gateIn[8].toString()) : BigDecimal.ZERO);
//	        detail.setCartingPackages(gateIn[4] != null ? new BigDecimal(gateIn[4].toString()) : BigDecimal.ZERO);
//	        detail.setTrfGateInWeight(BigDecimal.ZERO);
//
//	        
//	        System.out.println(" detail.getBalancePkgs() "+ detail.getBalancePkgs());
//	        // Add detail to the list
//	        detailList.add(detail);
//	    }
//
//	    return ResponseEntity.ok(detailList);
//	}

	
	
	public ResponseEntity<?> gateTateInEntriesFromSbNo(String companyId, String branchId, String profitcentreId, String sbNo, String sbTransId, String sbLineNo) {

	    List<Object[]> gateTateInEntriesFromSbNo = transDtlRepo.gateTateInEntriesFromSbNo(companyId, branchId, profitcentreId, sbNo, sbTransId);

	    List<ExportTransferDetail> detailList = new ArrayList<>();

	    // Initialize remainingOutPackages with the total outPackages (sum of all entries' outPackages)
	    BigDecimal totalOutPackages = gateTateInEntriesFromSbNo.stream()
	        .map(entry -> entry[5] != null ? new BigDecimal(entry[5].toString()) : BigDecimal.ZERO)
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    BigDecimal remainingOutPackages = totalOutPackages;

	    for (Object[] gateIn : gateTateInEntriesFromSbNo) {
	        ExportTransferDetail detail = new ExportTransferDetail();

	        // Convert and set fields
	        detail.setCompanyId(companyId);
	        detail.setBranchId(branchId);
	        detail.setCartingLineId(gateIn[7] != null ? gateIn[7].toString() : null);
	        detail.setCartingTransId((String) gateIn[0]);
	        detail.setVehicleNo((String) gateIn[2]);
	        detail.setGateInId((String) gateIn[0]);
	        detail.setSbNo(sbNo);
	        detail.setSbTransId(sbTransId);
	        detail.setSbLineNo(sbLineNo);

	        detail.setGateInDate(gateIn[1] instanceof java.sql.Timestamp ?
	            new java.util.Date(((java.sql.Timestamp) gateIn[1]).getTime()) :
	            (java.util.Date) gateIn[1]);

	        BigDecimal actualNoOfPackages = gateIn[9] != null ? new BigDecimal(gateIn[9].toString()) : BigDecimal.ZERO;
	        BigDecimal gateInPackages = gateIn[3] != null ? new BigDecimal(gateIn[3].toString()) : BigDecimal.ZERO;

	        // Calculate currentOutPackages
	        BigDecimal currentOutPackages = remainingOutPackages.min(gateInPackages);
	        remainingOutPackages = remainingOutPackages.subtract(currentOutPackages);

	        // Calculate balance packages
	        BigDecimal balancePkgs = gateInPackages.subtract(currentOutPackages);

	        // Set fields
	        detail.setGateInPackages(gateInPackages);
	        detail.setActualNoOfPackages(actualNoOfPackages);
	        detail.setBalancePkgs(balancePkgs);
	        detail.setOutPkgs(currentOutPackages);
	        detail.setNilPackages(gateIn[6] != null ? new BigDecimal(gateIn[6].toString()) : BigDecimal.ZERO);
	        detail.setGateInWeight(gateIn[8] != null ? new BigDecimal(gateIn[8].toString()) : BigDecimal.ZERO);
	        detail.setCartingPackages(gateIn[4] != null ? new BigDecimal(gateIn[4].toString()) : BigDecimal.ZERO);
	        detail.setTrfGateInWeight(BigDecimal.ZERO);
	        
	        System.out.println(" balancePkgs : "+ balancePkgs);

	        // Add to detailList only if balancePkgs is greater than 0
	        if (balancePkgs.compareTo(BigDecimal.ZERO) > 0) {
	            detailList.add(detail);
	        }
	    }

	    return ResponseEntity.ok(detailList);
	}

	

	
	
	public ResponseEntity<?> searchSbNoForTransfer(String companyId, String branchId, String searchValue,
			String profitcentreId, String type) {

		List<Map<String, Object>> containerList = new ArrayList<>();
		if (type.equals("from")) {
			List<Object[]> result = transRepo.searchSbNoForTransferFrom(companyId, branchId, searchValue,
					profitcentreId);

			containerList = result.stream().map(row -> {
				Map<String, Object> map = new HashMap<>();
				map.put("value", row[0]);
				map.put("label", row[0]);
				map.put("sbNo", row[0]);
				map.put("sbLineNo", row[2]);
				map.put("sbTransId", row[1]);
				
				map.put("sbDate", row[3]);
				map.put("sbTransDate", row[4]);
				
				map.put("commodity", row[5]);
				map.put("noOfPackages", row[6]);
				
				map.put("grossWeight", row[7]);
				
				map.put("exporterId", row[8]);
				map.put("exporterName", row[9]);
				
				map.put("cha", row[10]);
				map.put("chaName", row[11]);
				
				map.put("stuffReqQty", row[12]);
				map.put("gateInPackages", row[13]);
				map.put("backToTownPack", row[14]);
				map.put("transferPackages", row[15]);
				map.put("onAccountOf", row[16]);
				map.put("cartedPackages", row[17]);
				map.put("nilPackages", row[18]);
				map.put("maxQuantity", row[19]);
				
				return map;
			}).collect(Collectors.toList());

		} else {
			List<Object[]> result = transRepo.searchSbNoForTransferTo(companyId, branchId, searchValue, profitcentreId);

			containerList = result.stream().map(row -> {
				Map<String, Object> map = new HashMap<>();
				map.put("value", row[0]);
				map.put("label", row[0]);
				map.put("sbNo", row[0]);
				map.put("sbLineNo", row[2]);
				map.put("sbTransId", row[1]);
				
				map.put("sbDate", row[3]);
				map.put("sbTransDate", row[4]);
				
				map.put("commodity", row[5]);
				map.put("actualNoOfPackages", row[6]);
				
				map.put("grossWeight", row[7]);
				
				map.put("exporterId", row[8]);
				map.put("exporterName", row[9]);
				
				map.put("cha", row[10]);
				
				map.put("stuffReqQty", row[11]);
				map.put("gateInPackages", row[12]);
				map.put("backToTownPack", row[13]);
				map.put("transferPackages", row[14]);
				map.put("onAccountOf", row[15]);				
				
				return map;
			}).collect(Collectors.toList());

		}

		return ResponseEntity.ok(containerList);

	}

}
