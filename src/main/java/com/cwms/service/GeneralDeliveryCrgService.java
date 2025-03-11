package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.GeneralDeliveryCrg;
import com.cwms.entities.GeneralDeliveryDetails;
import com.cwms.entities.GeneralDeliveryGrid;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingCrgId;
import com.cwms.entities.GeneralReceivingGateInDtl;
import com.cwms.entities.GeneralReceivingGrid;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.GeneralDeliveryCrgRepo;
import com.cwms.repository.GeneralDeliveryDetailsRepo;
import com.cwms.repository.GeneralDeliveryGridRepo;
import com.cwms.repository.GeneralReceivingCrgRepo;
import com.cwms.repository.GeneralReceivingGateInDtlRepo;
import com.cwms.repository.GeneralReceivingGridRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeneralDeliveryCrgService {

	@Autowired
	private GeneralDeliveryCrgRepo generalDeliveryCrgRepo; 
	
	@Autowired
	private GeneralDeliveryDetailsRepo generalDeliveryDetailsRepo;
	
	@Autowired
	private GeneralDeliveryGridRepo generalDeliveryGridRepo;
	
	@Autowired
	private ProcessNextIdRepository processNextIdRepository;
	
	@Autowired
	private GeneralReceivingCrgRepo generalReceivingCrgRepo;
	
	@Autowired
	private GeneralReceivingGateInDtlRepo generalReceivingGateInDtlRepo;
	
	@Autowired
	private GeneralReceivingGridRepo  generalReceivingGridRepo;
	
	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	
	public List<GeneralReceivingCrg> getDataByBoeNoForDeliveryScreen(String companyId, String branchId, String partyName) {
		return generalDeliveryCrgRepo.getDataByBoeNoForDeliveryScreen(companyId, branchId, partyName);
	}
	
	public List<GeneralReceivingCrg> generalDeliveryCrgServiceFIFO(String companyId, String branchId, String partyName) {
		return generalDeliveryCrgRepo.getDataByBoeNoForDeliveryScreenFIFO(companyId, branchId, partyName);
	}
	
	
	
	public ResponseEntity<List<GeneralReceivingGrid>> getDataAfterSaved(String companyId, String branchId, String reveivingId) {

		List<GeneralReceivingGrid> list = generalDeliveryCrgRepo.getDataForDeliveryScreen(companyId, branchId, reveivingId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	public ResponseEntity<List<GeneralDeliveryGrid>> getSavedDataOfDeliveryGrid(String companyId, String branchId,String deliveryid, String reveivingId) {

		List<GeneralDeliveryGrid> list = generalDeliveryDetailsRepo.getDataAfterSave(companyId, branchId,deliveryid, reveivingId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	public ResponseEntity<List<GeneralReceivingGateInDtl>> getDataForDeliveryScreenFromReceivingDTL(String companyId, String branchId, String reveivingId) {

		List<GeneralReceivingGateInDtl> list = generalDeliveryCrgRepo.getDataForDeliveryScreenFromReceivingDTL(companyId, branchId, reveivingId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	
	
	
	
	public ResponseEntity<List<GeneralDeliveryDetails>> findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			String compnayId, String branchId, String deliery,String recevingId,String depositeNo) {

		List<GeneralDeliveryDetails> data = generalDeliveryDetailsRepo
				.getAfterSave(compnayId, branchId, deliery,recevingId,depositeNo);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
	@Transactional
	public ResponseEntity<?> saveDataOfCfInbondCrg(String companyId, String branchId, String user, String flag,
			Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GeneralDeliveryCrg cfinbondcrg = object.convertValue(requestBody.get("inBond"), GeneralDeliveryCrg.class);

		Object nocDtlObj = requestBody.get("nocDtl");
		List<GeneralDeliveryDetails> cfinbondcrgDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = object.convertValue(nocDtlObj, new TypeReference<List<GeneralDeliveryDetails>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GeneralDeliveryDetails cfinbondcrgDtl = object.convertValue(entry.getValue(), GeneralDeliveryDetails.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		// Extract and convert "grid"
	    Object gridObj = requestBody.get("grid");
	    List<GeneralDeliveryGrid> gridList = new ArrayList<>();

	    if (gridObj instanceof List) {
	        gridList = object.convertValue(gridObj, new TypeReference<List<GeneralDeliveryGrid>>() {});
	    } else if (gridObj instanceof Map) {
	        Map<String, Object> gridMap = (Map<String, Object>) gridObj;
	        for (Map.Entry<String, Object> entry : gridMap.entrySet()) {
	        	GeneralDeliveryGrid gridEntry = object.convertValue(entry.getValue(), GeneralDeliveryGrid.class);
	            gridList.add(gridEntry);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid type for grid: " + gridObj.getClass());
	    }

		GeneralDeliveryGrid saved = null;
		
		GeneralDeliveryCrg savedDelivery=null;

		List<GeneralDeliveryDetails> savedLIst =new ArrayList<>(); 
		if (cfinbondcrg != null) 
		{
			if ("add".equals(flag)) 
			{
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P0GD54", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectInBondingId = String.format("GDLC%06d", nextNumericNextID1);


					BigDecimal[] totalPkgs = { BigDecimal.ZERO };
					
					
					BigDecimal[] totalGridPkgs = { BigDecimal.ZERO };
					
					BigDecimal[] totalGridArea = { BigDecimal.ZERO };
					BigDecimal[] totalInGrossWeight = { BigDecimal.ZERO };

					cfinbondcrgDtlList.forEach(item -> {
						BigDecimal pkgs = item.getDeliveredPackages();
						BigDecimal inGrossWeight = item.getDeliveredWeight();

						if (pkgs != null) {
							totalPkgs[0] = totalPkgs[0].add(pkgs);
						}
						if (inGrossWeight != null) {
							totalInGrossWeight[0] = totalInGrossWeight[0].add(inGrossWeight);
						}
					});
					
					gridList.forEach(item -> {
						BigDecimal pkgs = item.getDeliveryPkgs();
						
						BigDecimal area = item.getCellAreaReleased();

						if (pkgs != null) {
							totalGridPkgs[0] = totalGridPkgs[0].add(pkgs);
						}
						
						if (area != null) {
							totalGridArea[0] = totalGridArea[0].add(area);
						}
					});

System.out.println("**************************************************************************************");
					

System.out.println(totalGridPkgs[0]);
					System.out.println(totalPkgs[0]);
					System.out.println(totalGridArea[0]);
					if (totalPkgs[0].compareTo(totalGridPkgs[0]) != 0) {
					    return new ResponseEntity<>("Grid package and received package count do not match!", HttpStatus.BAD_REQUEST);
					}
					
					
					

					cfinbondcrg.setCreatedBy(user);
					cfinbondcrg.setCreatedDate(new Date());
					cfinbondcrg.setApprovedBy(user);
					cfinbondcrg.setApprovedDate(new Date());
					cfinbondcrg.setBoeDate(cfinbondcrg.getBoeDate());
					cfinbondcrg.setBoeNo(cfinbondcrg.getBoeNo());
					cfinbondcrg.setStatus('A');
					cfinbondcrg.setDeliveryDate(new Date());
					cfinbondcrg.setReceivingId(cfinbondcrg.getReceivingId());
					cfinbondcrg.setDepositNo(cfinbondcrg.getDepositNo());
					
					cfinbondcrg.setDeliveryId(nectInBondingId);
					
					cfinbondcrg.setBalancedPackages(cfinbondcrg.getReceivedPackages().subtract(totalPkgs[0]));
					
					cfinbondcrg.setBalanceGw(cfinbondcrg.getReceivedGw().subtract(totalInGrossWeight[0]));
					
					cfinbondcrg.setStatus('A');
					cfinbondcrg.setAreaRemaining(cfinbondcrg.getAreaOccupied());
					
					cfinbondcrg.setAreaBalanced(cfinbondcrg.getAreaOccupied().subtract(totalGridArea[0]));
				
					BigDecimal totalGateIn = BigDecimal.ZERO;
					BigDecimal totalGateInWeight = BigDecimal.ZERO;
					BigDecimal totalWeightd = BigDecimal.ZERO;
					BigDecimal totalInbonded = BigDecimal.ZERO;

//					 cfinbondcrgDtlList.forEach(item -> {

					int srNo = 1; // Initialize srNo
					
					for (GeneralDeliveryDetails item : cfinbondcrgDtlList) {

						totalWeightd = totalWeightd.add(item.getDeliveredWeight());
						totalInbonded = totalInbonded.add(item.getDeliveredPackages());
                        totalGateIn = totalGateIn.add(item.getGateInPackages());
						
						totalGateInWeight = totalGateInWeight.add(item.getGateInWeight());


						GeneralDeliveryDetails crgDetails = new GeneralDeliveryDetails();

						crgDetails.setCreatedBy(user);
						crgDetails.setCreatedDate(new Date());
						crgDetails.setApprovedBy(user);
						crgDetails.setApprovedDate(new Date());
						crgDetails.setSrNo(srNo);
						crgDetails.setBranchId(branchId);
						crgDetails.setCompanyId(companyId);
						crgDetails.setDeliveryId(nectInBondingId);
						crgDetails.setDeliveryDate(new Date());
						crgDetails.setReceivingId(item.getReceivingId());
						crgDetails.setReceivingDate(item.getCreatedDate());
						
						crgDetails.setJonDtlTransId(item.getJonDtlTransId());
						
						crgDetails.setDeliveredPackages(item.getDeliveredPackages());
						crgDetails.setDeliveredWeight(item.getDeliveredWeight());

						crgDetails.setDepositNo(cfinbondcrg.getDepositNo());
						
						crgDetails.setContainerNo(item.getContainerNo());
						crgDetails.setContainerSize(item.getContainerSize());
						crgDetails.setContainerType(item.getContainerType());
						
						crgDetails.setGateInPackages(item.getGateInPackages());
						crgDetails.setGateInWeight(item.getGateInWeight());
						
						crgDetails.setReceivingPackages(item.getReceivingPackages());
						crgDetails.setReceivingWeight(item.getReceivingWeight());
						
						crgDetails.setStatus("A");
						crgDetails.setCommodityDescription(item.getCommodityDescription());
						crgDetails.setTypeOfPackage(item.getTypeOfPackage());
						crgDetails.setActCommodityId(item.getActCommodityId());

						crgDetails.setCommodityId(item.getCommodityId());
						
						crgDetails.setJobTransId(item.getJobTransId());
						crgDetails.setJobNo(item.getJobNo());

						crgDetails.setStatus("A");


						
						GeneralDeliveryDetails savedDtl = generalDeliveryDetailsRepo.save(crgDetails);
						
						savedLIst.add(savedDtl);

						if (savedDtl != null) {
							
							
							
							
							
							GeneralReceivingGateInDtl  existingDetail = generalReceivingGateInDtlRepo.updateReceivingGateInDetailSfaterDelivery(companyId, branchId,savedDtl.getReceivingId(),savedDtl.getJobTransId(),savedDtl.getCommodityId());
							
							
							System.out.println("existingDetail   :"+existingDetail);
							
							if(existingDetail!=null)
							{
								BigDecimal newQtyTakenIn = (existingDetail.getDeliveredPackages() != null
										? existingDetail.getDeliveredPackages()
										: BigDecimal.ZERO)
										.add(savedDtl.getDeliveredPackages() != null ? savedDtl.getDeliveredPackages() : BigDecimal.ZERO);
								
								BigDecimal newWeightTakenIn = (existingDetail.getDeliveredWeight()!=null ? existingDetail.getDeliveredWeight() :BigDecimal.ZERO).add(
										savedDtl.getDeliveredWeight() != null ? savedDtl.getDeliveredWeight() : BigDecimal.ZERO);
								
								
								existingDetail.setDeliveredPackages(newQtyTakenIn);
								existingDetail.setDeliveredWeight(newWeightTakenIn);
								
								GeneralReceivingGateInDtl newUpdated =generalReceivingGateInDtlRepo.save(existingDetail);
								System.out
										.println("GeneralReceivingGateInDtl_________________________GeneralReceivingGateInDtl______________________Dtl" + newUpdated);
								
							}
							
						}



//					    });
						srNo++;
					}

					
					
					if (!savedLIst.isEmpty())
					{
						for(GeneralDeliveryGrid grid : gridList)
						{
							GeneralDeliveryGrid cf = new GeneralDeliveryGrid();
							
							cf.setSrNo(grid.getSrNo());
							cf.setCompanyId(companyId);
							cf.setBranchId(branchId);
							cf.setCreatedBy(user);
							cf.setCreatedDate(new Date());
							cf.setApprovedBy(user);
							cf.setApprovedDate(new Date());
							cf.setReceivingId(grid.getReceivingId());
							cf.setYardLocation(grid.getYardLocation());
							cf.setYardBlock(grid.getYardBlock());
							cf.setBlockCellNo(grid.getBlockCellNo());

							cf.setBoeNo(cfinbondcrg.getBoeNo());
							cf.setDeliveryId(nectInBondingId);
							
							cf.setCellAreaAllocated(grid.getCellAreaAllocated());
							cf.setCellAreaReleased(grid.getCellAreaReleased());
							cf.setDeliveryPkgs(grid.getDeliveryPkgs());
							cf.setStatus("A");
							cf.setReceivedPackages(grid.getReceivedPackages());

							GeneralDeliveryGrid savedGrid= generalDeliveryGridRepo.save(cf);
							
							if(savedGrid!=null)
							{
								GeneralReceivingGrid recGrid = generalReceivingGateInDtlRepo.getSavedGeneralReceivingGrid(companyId, branchId,
										savedGrid.getReceivingId(),savedGrid.getYardLocation(),savedGrid.getYardBlock(),savedGrid.getBlockCellNo());
								
								
								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
										savedGrid.getYardLocation(), savedGrid.getYardBlock(), savedGrid.getBlockCellNo());
								
								
								System.out.println("recGrid   "+recGrid); 
								if(recGrid!=null)
								{

									    BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed() : BigDecimal.ZERO;
									    BigDecimal gridCellAreaReleased = savedGrid.getCellAreaReleased() != null ? savedGrid.getCellAreaReleased() : BigDecimal.ZERO;

									    existingCell.setCellAreaUsed(existingCellAreaUsed.subtract(gridCellAreaReleased));

									    yardBlockCellRepository.save(existingCell);
									
									
									recGrid.setAreaReleased(
										    (recGrid.getAreaReleased() != null ? recGrid.getAreaReleased() : BigDecimal.ZERO)
										        .add(savedGrid.getCellAreaReleased() != null ? savedGrid.getCellAreaReleased() : BigDecimal.ZERO)
										);

										recGrid.setDeliveredPackages(
										    (recGrid.getDeliveredPackages() != null ? recGrid.getDeliveredPackages() : BigDecimal.ZERO)
										        .add(savedGrid.getDeliveryPkgs() != null ? savedGrid.getDeliveryPkgs() : BigDecimal.ZERO)
										);
										
										

										GeneralReceivingGrid recGridSaved = generalReceivingGridRepo.save(recGrid);

									
									
									System.out.println("recGrid  saved "+recGridSaved); 
								}
							}
						}
					}
					 

					cfinbondcrg.setDeliveredPackages(totalInbonded);
					cfinbondcrg.setDeliveryGw(totalWeightd);

					
					
					cfinbondcrg.setAreaReleased(totalGridArea[0]);
					savedDelivery = generalDeliveryCrgRepo.save(cfinbondcrg);
					
					if(savedDelivery!=null)
					{
						GeneralReceivingCrg findCfBondCrgData = generalReceivingCrgRepo.findCfinbondCrgAfterDelivery(companyId, branchId,
								savedDelivery.getReceivingId(),savedDelivery.getDepositNo());
						
						if(findCfBondCrgData!=null)
						{
							findCfBondCrgData.setDeliveredPackages(findCfBondCrgData.getDeliveredPackages().add(savedDelivery.getDeliveredPackages()));
							findCfBondCrgData.setDeliveredWeight(findCfBondCrgData.getDeliveredWeight().add(savedDelivery.getDeliveryGw()));
							
							generalReceivingCrgRepo.save(findCfBondCrgData);
						}
					}
					
					
					
 
					processNextIdRepository.updateAuditTrail(companyId, branchId, "P0GD54", nectInBondingId, "2025");



			}
			
			
			else {
				
				
				BigDecimal[] totalPkgs = { BigDecimal.ZERO };
				BigDecimal[] totalInGrossWeight = { BigDecimal.ZERO };
				
				
				BigDecimal[] totalGridPkgs = { BigDecimal.ZERO };
				BigDecimal[] totalGridArea = { BigDecimal.ZERO };
				


				cfinbondcrgDtlList.forEach(item -> {
					BigDecimal pkgs = item.getDeliveredPackages();
					
					if (pkgs != null) {
						totalPkgs[0] = totalPkgs[0].add(pkgs);
					}
				});
				
				gridList.forEach(item -> {
					BigDecimal pkgs = item.getDeliveryPkgs();
					
					BigDecimal area=item.getCellAreaReleased();
					if (pkgs != null) {
						totalGridPkgs[0] = totalGridPkgs[0].add(pkgs);
					}
					
					if (area != null) {
						totalGridArea[0] = totalGridArea[0].add(area);
					}
				});


				if (totalPkgs[0].compareTo(totalGridPkgs[0]) != 0) {
				    return new ResponseEntity<>("Grid package and received package count do not match!", HttpStatus.BAD_REQUEST);
				}
			 
				GeneralDeliveryDetails findCfBondCrgDTLData = null;
				
				GeneralDeliveryGrid recGrid = null;
				
				GeneralDeliveryCrg findCfBondCrgData = generalDeliveryCrgRepo.findSavedGeneralDelivery(companyId, branchId,cfinbondcrg.getDeliveryId(),cfinbondcrg.getDepositNo(),
						cfinbondcrg.getReceivingId());

				if (findCfBondCrgData != null) 
				{
					System.out.println("in edit");
					BigDecimal totalInbondedd = BigDecimal.ZERO;
					BigDecimal totalGateIn = BigDecimal.ZERO;
					BigDecimal totalGateInWeight = BigDecimal.ZERO;
					BigDecimal totalWeight = BigDecimal.ZERO;
					
					
					BigDecimal gridArea = BigDecimal.ZERO;

					for (GeneralDeliveryDetails item : cfinbondcrgDtlList) 
					{
					
						totalInbondedd = totalInbondedd.add(item.getDeliveredPackages());
					
						totalWeight = totalWeight.add(item.getDeliveredWeight());
						
						
						totalGateIn = totalGateIn.add(item.getGateInPackages());
						
						totalGateInWeight = totalGateInWeight.add(item.getGateInWeight());

						findCfBondCrgDTLData = generalDeliveryDetailsRepo.getAfterSaveForEdit(companyId, branchId,item.getDeliveryId(),item.getReceivingId(),item.getDepositNo(),item.getCommodityId());

						
						if (findCfBondCrgDTLData != null) 
						{
							
				
							findCfBondCrgData.setShift(cfinbondcrg.getShift());
							findCfBondCrgData.setGatePassAllow(cfinbondcrg.getGatePassAllow());
							findCfBondCrgData.setTransporterName(cfinbondcrg.getTransporterName());
							findCfBondCrgData.setHandlingEquip(cfinbondcrg.getHandlingEquip());
							findCfBondCrgData.setHandlingEquip1(cfinbondcrg.getHandlingEquip1());
							findCfBondCrgData.setHandlingEquip2(cfinbondcrg.getHandlingEquip2());
							
							findCfBondCrgData.setOwner(cfinbondcrg.getOwner());
							findCfBondCrgData.setOwner1(cfinbondcrg.getOwner1());
							findCfBondCrgData.setComments(cfinbondcrg.getComments());
							findCfBondCrgData.setEditedBy(user);
							findCfBondCrgData.setStatus('A');
							findCfBondCrgData.setEditedDate(new Date());
							
							findCfBondCrgData.setCargoDuty(cfinbondcrg.getCargoDuty());;
							findCfBondCrgData.setCargoValue(cfinbondcrg.getCargoValue());

							

							
							
							findCfBondCrgDTLData.setEditedBy(user);
							findCfBondCrgDTLData.setStatus("A");
							findCfBondCrgDTLData.setEditedDate(new Date());
							
									GeneralReceivingGateInDtl  existingDetail = generalReceivingGateInDtlRepo.updateReceivingGateInDetailSfaterDelivery(companyId, branchId,item.getReceivingId(),item.getJobTransId(),item.getCommodityId());
									
									System.out.println("existingDetail  edit   :"+existingDetail);
									if(existingDetail!=null)
									{
										BigDecimal newQtyTakenIn = (existingDetail.getDeliveredPackages() != null
												? existingDetail.getDeliveredPackages()
												: BigDecimal.ZERO)
												.add(item.getDeliveredPackages() != null ? item.getDeliveredPackages() : BigDecimal.ZERO).subtract(findCfBondCrgDTLData.getDeliveredPackages());
										
										BigDecimal newWeightTakenIn = (existingDetail.getDeliveredWeight()!=null ? existingDetail.getDeliveredWeight() :BigDecimal.ZERO).add(
												item.getDeliveredWeight() != null ? item.getDeliveredWeight() : BigDecimal.ZERO).subtract(findCfBondCrgDTLData.getDeliveredWeight());
										
										
										existingDetail.setDeliveredPackages(newQtyTakenIn);
										existingDetail.setDeliveredWeight(newWeightTakenIn);
										
										GeneralReceivingGateInDtl newUpdated =generalReceivingGateInDtlRepo.save(existingDetail);
										System.out
												.println("GeneralReceivingGateInDtl_________IN__EDITE________________GeneralReceivingGateInDtl______________________Dtl" + newUpdated);
										
									}
									
									findCfBondCrgDTLData.setDeliveredPackages(item.getDeliveredPackages());
									findCfBondCrgDTLData.setDeliveredWeight(item.getDeliveredWeight());
									
									GeneralDeliveryDetails savedDTL=	     generalDeliveryDetailsRepo.save(findCfBondCrgDTLData);

						}
					}
					
					

					for(GeneralDeliveryGrid grid : gridList)
					{
						
						gridArea =gridArea.add(grid.getCellAreaReleased());
						
						System.out.println("grid.getCellAreaAllocated() "+ grid.getCellAreaReleased());
								
						recGrid = generalDeliveryDetailsRepo.getSavedGeneralDeliveryGrid(companyId, branchId,cfinbondcrg.getDeliveryId(),grid.getYardLocation(),grid.getYardBlock(),grid.getBlockCellNo());
						
						
						YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
								grid.getYardLocation(), grid.getYardBlock(), grid.getBlockCellNo());
						
						
							    
						if(recGrid!=null)
						{
							
							recGrid.setEditedBy(user);
							recGrid.setEditedDate(new Date());

								GeneralReceivingGrid recGrid1 = generalReceivingGateInDtlRepo.getSavedGeneralReceivingGrid(companyId, branchId,
										recGrid.getReceivingId(),recGrid.getYardLocation(),recGrid.getYardBlock(),recGrid.getBlockCellNo());
								
								if(recGrid1!=null)
								{
									
									
									    BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed() : BigDecimal.ZERO;
									    BigDecimal gridCellAreaReleased = grid.getCellAreaReleased() != null ? grid.getCellAreaReleased() : BigDecimal.ZERO;
									    BigDecimal savedGridCellAreaReleased = recGrid.getCellAreaReleased() != null ? recGrid.getCellAreaReleased() : BigDecimal.ZERO;

									    
									    
									    existingCell.setCellAreaUsed(existingCellAreaUsed.subtract(gridCellAreaReleased).add(savedGridCellAreaReleased));

									    yardBlockCellRepository.save(existingCell);
									    
									    
									    
									recGrid1.setAreaReleased(
										    (recGrid1.getAreaReleased() != null ? recGrid1.getAreaReleased() : BigDecimal.ZERO)
										        .add(grid.getCellAreaReleased() != null ? grid.getCellAreaReleased() : BigDecimal.ZERO)
										        .subtract(recGrid.getCellAreaReleased() != null ? recGrid.getCellAreaReleased() : BigDecimal.ZERO)
										);

										recGrid1.setDeliveredPackages(
										    (recGrid1.getDeliveredPackages() != null ? recGrid1.getDeliveredPackages() : BigDecimal.ZERO)
										        .add(grid.getDeliveryPkgs() != null ? grid.getDeliveryPkgs() : BigDecimal.ZERO)
										        .subtract(recGrid.getDeliveryPkgs() != null ? recGrid.getDeliveryPkgs() : BigDecimal.ZERO)
										);

										generalReceivingGridRepo.save(recGrid1);
										
										System.out.println("sabed in edit grd "+generalReceivingGridRepo.save(recGrid1));

								}
								
								
								
								recGrid.setDeliveryPkgs(grid.getDeliveryPkgs());
								recGrid.setCellAreaReleased(grid.getCellAreaReleased());
								
								
								GeneralDeliveryGrid delGridsaved= generalDeliveryGridRepo.save(recGrid);

						}

					}	

					
					

					
					
					GeneralReceivingCrg findReceivingData = generalReceivingCrgRepo.findCfinbondCrgAfterDelivery(companyId, branchId,
							cfinbondcrg.getReceivingId(),cfinbondcrg.getDepositNo());
					
					if(findReceivingData!=null)
					{
						findReceivingData.setDeliveredPackages(findReceivingData.getDeliveredPackages().add(totalInbondedd).subtract(findCfBondCrgData.getDeliveredPackages()));
						findReceivingData.setDeliveredWeight(findReceivingData.getDeliveredWeight().add(totalWeight).subtract(findCfBondCrgData.getDeliveryGw()));
						
						generalReceivingCrgRepo.save(findReceivingData);
					}
					
					
					findCfBondCrgData.setDeliveredPackages(totalInbondedd);
					findCfBondCrgData.setDeliveryGw(totalWeight);
					findCfBondCrgData.setAreaReleased(totalGridArea[0]);
					
					findCfBondCrgData.setAreaReleased(gridArea);
					findCfBondCrgData.setAreaBalanced(cfinbondcrg.getAreaOccupied().subtract(gridArea));
					
					findCfBondCrgData.setBalancedPackages(cfinbondcrg.getReceivedPackages().subtract(totalInbondedd));
					
					findCfBondCrgData.setBalanceGw(cfinbondcrg.getReceivedGw().subtract(totalWeight));
					
					savedDelivery=generalDeliveryCrgRepo.save(findCfBondCrgData);
					
			
			
				}

			
			}

		}


		return new ResponseEntity<>(savedDelivery, HttpStatus.OK);
	}
	
	
	public List<GeneralDeliveryCrg> findAllCfinbondCrgIn(String companyId, String branchId, String search) {
		return generalDeliveryCrgRepo.findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, search);
	}
	
	
	
	
	
	
	public GeneralDeliveryCrg getCfInbondCrgDataByidOrSearch(String companyId, String branchId, String deliveryId,
			String receivingId, String depositeNo,String boeNo) {
		return generalDeliveryCrgRepo.getGeneralDeliveryCrgData(companyId, branchId, deliveryId, receivingId, depositeNo,boeNo);
	}
}
