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
import org.thymeleaf.TemplateEngine;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.GeneralGateIn;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingGateInDtl;
import com.cwms.entities.GeneralReceivingGrid;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CfBondNocDtlRepository;
import com.cwms.repository.CfInBondGridRepository;
import com.cwms.repository.CfbondGatePassRepository;
import com.cwms.repository.CfbondnocRepository;
import com.cwms.repository.CfinbondCrgHdrDtlRepo;
import com.cwms.repository.CfinbondCrgHdrRepo;
import com.cwms.repository.CfinbondcrgDtlRepo;
import com.cwms.repository.CfinbondcrgRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.GeneralGateInRepo;
import com.cwms.repository.GeneralReceivingCrgRepo;
import com.cwms.repository.GeneralReceivingGateInDtlRepo;
import com.cwms.repository.GeneralReceivingGridRepo;
import com.cwms.repository.ProcessNextIdRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeneralReceivingCrgService {

	@Autowired
	private GeneralReceivingCrgRepo generalReceivingCrgRepo;
	
	
	@Autowired
	private GeneralReceivingGateInDtlRepo generalReceivingGateInDtlRepo;
	
	@Autowired
	private GeneralReceivingGridRepo generalReceivingGridRepo;
	
	
	@Autowired
	private GeneralGateInRepo generalGateInRepo;
	
	
	@Autowired
	public CfinbondcrgRepo cfinbondcrgRepo;

	@Autowired
	public CfinbondcrgDtlRepo cfinbondcrgDtlRepo;

	@Autowired
	private ProcessNextIdRepository processNextIdRepository;

	@Autowired
	private CfinbondCrgHdrRepo cfinbondCrgHdrRepo;

	@Autowired
	private CfinbondCrgHdrDtlRepo cfinbondCrgHdrDtlRepo;

	@Autowired
	private CfBondNocDtlRepository cfBondNocDtlRepository;

	@Autowired
	private CfbondnocRepository cfbondnocRepository;

	@Autowired
	public CfInBondGridRepository cfInBondGridRepository;
	
	
	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	
	
	
	
	
	
	@Autowired
	public CfbondGatePassRepository cfbondGatePassRepository;
	
	public List<GeneralGateIn> getCfbondnocDataForInBondScreen(String companyId, String branchId, String partyName) {
		return generalReceivingCrgRepo.findCfbondnocByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, partyName);
	}
	
	public List<GeneralGateIn> findAllCfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo) {
		return generalReceivingCrgRepo.getCfBondNocDtl(companyId, branchId, nocTransId, nocNo);
	}
	
	public ResponseEntity<List<GeneralReceivingGateInDtl>> findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
			String compnayId, String branchId, String inBondingId) {

		List<GeneralReceivingGateInDtl> data = generalReceivingGateInDtlRepo
				.getAfterSave(compnayId, branchId, inBondingId);

		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	public List<GeneralReceivingCrg> findAllCfinbondCrgIn(String companyId, String branchId, String search) {
		return generalReceivingCrgRepo.findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(companyId, branchId, search);
	}
	
	
	
	
	public GeneralReceivingCrg getCfInbondCrgDataByidOrSearch(String companyId, String branchId, String transId,
			String inBondingId, String nocNo) {
		return generalReceivingCrgRepo.findCfBondCrgData(companyId, branchId, transId, inBondingId, nocNo);
	}
	
	@Transactional
	public ResponseEntity<?> saveDataOfCfInbondCrg(String companyId, String branchId, String user, String flag,
			Map<String, Object> requestBody) {
		ObjectMapper object = new ObjectMapper();

		object.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		GeneralReceivingCrg cfinbondcrg = object.convertValue(requestBody.get("inBond"), GeneralReceivingCrg.class);

		Object nocDtlObj = requestBody.get("nocDtl");
		List<GeneralReceivingGateInDtl> cfinbondcrgDtlList = new ArrayList();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = object.convertValue(nocDtlObj, new TypeReference<List<GeneralReceivingGateInDtl>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				GeneralReceivingGateInDtl cfinbondcrgDtl = object.convertValue(entry.getValue(), GeneralReceivingGateInDtl.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			// Handle unexpected types
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}

		// Extract and convert "grid"
	    Object gridObj = requestBody.get("grid");
	    List<GeneralReceivingGrid> gridList = new ArrayList<>();

	    if (gridObj instanceof List) {
	        gridList = object.convertValue(gridObj, new TypeReference<List<GeneralReceivingGrid>>() {});
	    } else if (gridObj instanceof Map) {
	        Map<String, Object> gridMap = (Map<String, Object>) gridObj;
	        for (Map.Entry<String, Object> entry : gridMap.entrySet()) {
	            GeneralReceivingGrid gridEntry = object.convertValue(entry.getValue(), GeneralReceivingGrid.class);
	            gridList.add(gridEntry);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid type for grid: " + gridObj.getClass());
	    }
	    
		System.out.println("flag________________" + flag);
		
		System.out.println("cfinbondcrg________________" + cfinbondcrg);

		System.out.println("cfinbondcrgDtlList________________" + cfinbondcrgDtlList);

		CfInBondGrid saved = null;

		List<GeneralReceivingGateInDtl> savedLIst =new ArrayList<>(); 
		if (cfinbondcrg != null) 
		{
			if ("add".equals(flag)) 
			{
					String holdId1 = processNextIdRepository.findAuditTrail(companyId, branchId, "P00025", "2025");

					int lastNextNumericId1 = Integer.parseInt(holdId1.substring(4));

					int nextNumericNextID1 = lastNextNumericId1 + 1;

					String nectInBondingId = String.format("GRCD%06d", nextNumericNextID1);

					String holdId = processNextIdRepository.findAuditTrail(companyId, branchId, "PDEP25", "2025");

					int lastNextNumericId = Integer.parseInt(holdId.substring(4));

					int nextNumericNextID = lastNextNumericId + 1;

					String nectInBondingHDRId = String.format("DEPO%06d", nextNumericNextID);

//					BigDecimal[] totalCif = { BigDecimal.ZERO };
//					BigDecimal[] totalCargo = { BigDecimal.ZERO };
					BigDecimal[] totalPkgs = { BigDecimal.ZERO };
					
					
					BigDecimal[] totalGridPkgs = { BigDecimal.ZERO };
					
					BigDecimal[] totalGridArea = { BigDecimal.ZERO };
					BigDecimal[] totalInGrossWeight = { BigDecimal.ZERO };

					cfinbondcrgDtlList.forEach(item -> {
						BigDecimal pkgs = item.getReceivingPackages();
						BigDecimal inGrossWeight = item.getReceivingWeight();

						if (pkgs != null) {
							totalPkgs[0] = totalPkgs[0].add(pkgs);
						}
						if (inGrossWeight != null) {
							totalInGrossWeight[0] = totalInGrossWeight[0].add(inGrossWeight);
						}
					});
					
					gridList.forEach(item -> {
						BigDecimal pkgs = item.getReceivedPackages();
						
						BigDecimal area = item.getCellAreaAllocated();

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
					cfinbondcrg.setStatus("A");
					cfinbondcrg.setReceivingDate(new Date());
					cfinbondcrg.setReceivingId(nectInBondingId);
					cfinbondcrg.setDepositNo(nectInBondingHDRId);
					
					cfinbondcrg.setReceivedPackages(totalPkgs[0]);
					cfinbondcrg.setReceivedWeight(totalInGrossWeight[0]);

//					cfinbondCrgHdrRepo.save(cfinbondHDR);
//					cfinbondcrgRepo.save(cfinbondcrg);
//	
//					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03205", nectInBondingId, "2242");
//					processNextIdRepository.updateAuditTrail(companyId, branchId, "P03206", nectInBondingHDRId, "2243");
//					
					BigDecimal totalGateIn = BigDecimal.ZERO;
					BigDecimal totalGateInWeight = BigDecimal.ZERO;
					BigDecimal totalWeightd = BigDecimal.ZERO;
					BigDecimal totalInbonded = BigDecimal.ZERO;
					BigDecimal totalArea = BigDecimal.ZERO;

//					 cfinbondcrgDtlList.forEach(item -> {

					int srNo = 1; // Initialize srNo
					
					for (GeneralReceivingGateInDtl item : cfinbondcrgDtlList) {

						totalWeightd = totalWeightd.add(item.getReceivingWeight());
						totalInbonded = totalInbonded.add(item.getReceivingPackages());
                        totalGateIn = totalGateIn.add(item.getGateInPackages());
						
						totalGateInWeight = totalGateInWeight.add(item.getGrossWeight());


						GeneralReceivingGateInDtl crgDetails = new GeneralReceivingGateInDtl();

						crgDetails.setCreatedBy(user);
						crgDetails.setCreatedDate(new Date());
						crgDetails.setApprovedBy(user);
						crgDetails.setApprovedDate(new Date());
						crgDetails.setSrNo(srNo);
						crgDetails.setBranchId(branchId);
						crgDetails.setCompanyId(companyId);
						crgDetails.setDepositNo(nectInBondingHDRId);
						
						crgDetails.setReceivingId(nectInBondingId);
						crgDetails.setGateInId(item.getGateInId());
						crgDetails.setGateInDate(item.getGateInDate());
						crgDetails.setContainerNo(item.getContainerNo());
						crgDetails.setContainerSize(item.getContainerSize());
						crgDetails.setContainerType(item.getContainerType());
						crgDetails.setVehicleNo(item.getVehicleNo());
						crgDetails.setJobNop(item.getJobNop());
						crgDetails.setJobGwt(item.getJobGwt());
						crgDetails.setGateInPackages(item.getGateInPackages());
						crgDetails.setGateInWeight(item.getGrossWeight());
						crgDetails.setReceivingPackages(item.getReceivingPackages());
						crgDetails.setReceivingWeight(item.getReceivingWeight());
						crgDetails.setStatus("A");
						crgDetails.setCommodityDescription(item.getCommodityDescription());
						crgDetails.setTypeOfPackage(item.getTypeOfPackage());
						crgDetails.setActCommodityId(item.getActCommodityId());
						crgDetails.setJobDtlTransId(item.getCommodityId());
						crgDetails.setCommodityId(item.getCommodityId());
						
						crgDetails.setJobTransId(item.getJobTransId());
						crgDetails.setJobNo(item.getJobNo());

						crgDetails.setStatus("A");


						
						GeneralReceivingGateInDtl savedDtl = generalReceivingGateInDtlRepo.save(crgDetails);
						
						savedLIst.add(savedDtl);

						if (savedDtl != null) {
							
							GeneralGateIn existingDetail = generalGateInRepo.getGeneralGateIn(companyId, branchId,
									savedDtl.getGateInId(), savedDtl.getCommodityId());
							
							if(existingDetail!=null)
							{
								BigDecimal newQtyTakenIn = (existingDetail.getReceivingPackages() != null
										? existingDetail.getReceivingPackages()
										: BigDecimal.ZERO)
										.add(savedDtl.getReceivingPackages() != null ? savedDtl.getReceivingPackages() : BigDecimal.ZERO);
								
								BigDecimal newWeightTakenIn = (existingDetail.getReceivingWeight()!=null ? existingDetail.getReceivingWeight() :BigDecimal.ZERO).add(
										savedDtl.getReceivingWeight() != null ? savedDtl.getReceivingWeight() : BigDecimal.ZERO);
								
								
								int updateNoCDtl = generalGateInRepo.updateGateInDetailAfterReceiving(newQtyTakenIn,
										newWeightTakenIn, companyId, branchId,
										savedDtl.getGateInId(), savedDtl.getCommodityId());

								System.out
										.println("updateNoCDtl__________Row___In__NOC______________________Dtl" + updateNoCDtl);
								
							}
							
						}



//					    });
						srNo++;
					}

					
					
					if (!savedLIst.isEmpty())
					{
						for(GeneralReceivingGrid grid : gridList)
						{
							GeneralReceivingGrid cf = new GeneralReceivingGrid();
							
							cf.setSrNo(grid.getSrNo());
							cf.setCompanyId(companyId);
							cf.setBranchId(branchId);
							cf.setCreatedBy(user);
							cf.setCreatedDate(new Date());
							cf.setApprovedBy(user);
							cf.setApprovedDate(new Date());
							cf.setReceivingId(nectInBondingId);
							cf.setYardLocation(grid.getYardLocation());
							cf.setYardBlock(grid.getYardBlock());
							cf.setBlockCellNo(grid.getBlockCellNo());

							cf.setCellArea(grid.getCellArea());
							cf.setCellAreaUsed(grid.getCellAreaAllocated());
							cf.setCellAreaAllocated(grid.getCellAreaAllocated());

							cf.setReceivedPackages(grid.getReceivedPackages());
							cf.setStatus("A");

							GeneralReceivingGrid  gridSaved = generalReceivingGridRepo.save(cf);
							
							
							if (gridSaved != null) 
							{
								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
										gridSaved.getYardLocation(), gridSaved.getYardBlock(), gridSaved.getBlockCellNo());

								if (existingCell != null) {
								    BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed() : BigDecimal.ZERO;
								    BigDecimal gridCellAreaAllocated = gridSaved.getCellAreaAllocated() != null ? gridSaved.getCellAreaAllocated() : BigDecimal.ZERO;

								    existingCell.setCellAreaUsed(existingCellAreaUsed.add(gridCellAreaAllocated));

								    yardBlockCellRepository.save(existingCell);
								}
							}
						}
					}
					 
					cfinbondcrg.setReceivedWeight(totalWeightd);
					cfinbondcrg.setReceivedPackages(totalInbonded);
					cfinbondcrg.setGateInPackages(totalGateIn);
					cfinbondcrg.setGateInWeight(totalGateInWeight);
					
					cfinbondcrg.setAreaOccupied(totalGridArea[0]);
					generalReceivingCrgRepo.save(cfinbondcrg);
					
					
					

					processNextIdRepository.updateAuditTrail(companyId, branchId, "P00025", nectInBondingId, "2025");
					processNextIdRepository.updateAuditTrail(companyId, branchId, "PDEP25", nectInBondingHDRId, "2025");
					
//					int updateInCfBondNoc = cfbondnocRepository.updateCfBondNocAfterInBonding(totalInbonded,
//							totalWeightd, totalInsuranced, totalCifD, totalCargod, cfinbondcrg.getBondingNo(),
//							cfinbondcrg.getBondingDate(), cfinbondcrg.getBondValidityDate(), companyId, branchId,
//							cfinbondcrg.getNocTransId(), cfinbondcrg.getNocNo());
//					System.out.println("Update noc after inbond " + updateInCfBondNoc);



			}
			
			
			else {
				
				
				BigDecimal[] totalPkgs = { BigDecimal.ZERO };
				
				
				BigDecimal[] totalGridPkgs = { BigDecimal.ZERO };
				


				cfinbondcrgDtlList.forEach(item -> {
					BigDecimal pkgs = item.getReceivingPackages();
					if (pkgs != null) {
						totalPkgs[0] = totalPkgs[0].add(pkgs);
					}
				});
				
				gridList.forEach(item -> {
					BigDecimal pkgs = item.getReceivedPackages();
					if (pkgs != null) {
						totalGridPkgs[0] = totalGridPkgs[0].add(pkgs);
					}
				});

System.out.println("**************************************************************************************");
				System.out.println(totalGridPkgs[0]);
				System.out.println(totalPkgs[0]);
				if (totalPkgs[0].compareTo(totalGridPkgs[0]) != 0) {
				    return new ResponseEntity<>("Grid package and received package count do not match!", HttpStatus.BAD_REQUEST);
				}
			 
				GeneralReceivingGateInDtl findCfBondCrgDTLData = null;
				
				GeneralReceivingGrid recGrid = null;
				
				GeneralReceivingCrg findCfBondCrgData = generalReceivingCrgRepo.findCfinbondCrg(companyId, branchId,
						cfinbondcrg.getReceivingId());

				if (findCfBondCrgData != null) 
				{
					System.out.println("in edit");
					BigDecimal totalInbondedd = BigDecimal.ZERO;
					BigDecimal totalGateIn = BigDecimal.ZERO;
					BigDecimal totalGateInWeight = BigDecimal.ZERO;
					BigDecimal totalWeight = BigDecimal.ZERO;
					
					
					BigDecimal gridArea = BigDecimal.ZERO;

					for (GeneralReceivingGateInDtl item : cfinbondcrgDtlList) 
					{
					
						totalInbondedd = totalInbondedd.add(item.getReceivingPackages());
					
						totalWeight = totalWeight.add(item.getReceivingWeight());
						
						
						totalGateIn = totalGateIn.add(item.getGateInPackages());
						
						totalGateInWeight = totalGateInWeight.add(item.getGateInWeight());

						findCfBondCrgDTLData = generalReceivingGateInDtlRepo.getAfterSaveForEdit(companyId, branchId,item.getReceivingId(),item.getGateInId(),item.getCommodityId());

						
						if (findCfBondCrgDTLData != null) 
						{
							
							findCfBondCrgData.setNoOf20Ft(cfinbondcrg.getNoOf20Ft());
							findCfBondCrgData.setNoOf40Ft(cfinbondcrg.getNoOf40Ft());
							findCfBondCrgData.setReceivedWeight(totalWeight);
							findCfBondCrgData.setReceivedPackages(totalInbondedd);
							findCfBondCrgData.setComments(cfinbondcrg.getComments());
							findCfBondCrgData.setNoOfMarks(cfinbondcrg.getNoOfMarks());
							findCfBondCrgData.setSpaceAllocated(cfinbondcrg.getSpaceAllocated());
							findCfBondCrgData.setCrossingCargo(cfinbondcrg.getCrossingCargo());
							findCfBondCrgData.setCargoCondition(cfinbondcrg.getCargoCondition());
							findCfBondCrgData.setCargoDuty(cfinbondcrg.getCargoDuty());
							findCfBondCrgData.setCargoValue(cfinbondcrg.getCargoValue());
							findCfBondCrgData.setGateInPackages(totalGateIn);
							findCfBondCrgData.setHandlingEquip1(cfinbondcrg.getHandlingEquip1());
							findCfBondCrgData.setHandlingEquip2(cfinbondcrg.getHandlingEquip2());
							findCfBondCrgData.setHandlingEquip3(cfinbondcrg.getHandlingEquip3());
							findCfBondCrgData.setGateInWeight(totalGateInWeight);
							findCfBondCrgData.setEditedBy(user);
							findCfBondCrgData.setStatus("A");
							findCfBondCrgData.setEditedDate(new Date());


							
							
							findCfBondCrgDTLData.setEditedBy(user);
							findCfBondCrgDTLData.setStatus("A");
							findCfBondCrgDTLData.setEditedDate(new Date());
							
//							GeneralReceivingGateInDtl savedDTL=	     generalReceivingGateInDtlRepo.save(findCfBondCrgDTLData);
							
//							if(savedDTL!=null) {
								
								GeneralGateIn existingDetail = generalGateInRepo.getGeneralGateIn(companyId, branchId,
										item.getGateInId(), item.getCommodityId());
								
								if(existingDetail!=null)
								{
									
									System.out.println( findCfBondCrgDTLData.getReceivingPackages() );
									System.out.println( existingDetail.getReceivingPackages() );
									
									System.out.println( item.getReceivingPackages() );
				
									
									System.out.println( findCfBondCrgDTLData.getReceivingWeight() );
									
									System.out.println( item.getReceivingWeight() );
									
									
									BigDecimal newQtyTakenIn = (existingDetail.getReceivingPackages() != null
											? existingDetail.getReceivingPackages()
											: BigDecimal.ZERO)
											.add(item.getReceivingPackages() != null ? item.getReceivingPackages() : BigDecimal.ZERO)
											.subtract(findCfBondCrgDTLData.getReceivingPackages());

									System.out.println("newQtyTakenIn+++++++++++++++++" + newQtyTakenIn);

									BigDecimal newWeightTakenIn = existingDetail.getReceivingWeight()
											.add(item.getReceivingWeight() != null ? item.getReceivingWeight() : BigDecimal.ZERO)
											.subtract(findCfBondCrgDTLData.getReceivingWeight());

									
									existingDetail.setReceivingPackages(newQtyTakenIn);
									existingDetail.setReceivingWeight(newWeightTakenIn);

									generalGateInRepo.save(existingDetail);

									
								}
								
								findCfBondCrgDTLData.setReceivingPackages(item.getReceivingPackages());
								findCfBondCrgDTLData.setReceivingWeight(item.getReceivingWeight());
								
								GeneralReceivingGateInDtl savedDTL=	     generalReceivingGateInDtlRepo.save(findCfBondCrgDTLData);
//							}

						}
					}
					
					

					for(GeneralReceivingGrid grid : gridList)
					{
						
						gridArea =gridArea.add(grid.getCellAreaAllocated());
						
						System.out.println("grid.getCellAreaAllocated() "+ grid.getCellAreaAllocated());
								
						recGrid = generalReceivingGateInDtlRepo.getSavedGeneralReceivingGrid(companyId, branchId,cfinbondcrg.getReceivingId(),grid.getYardLocation(),grid.getYardBlock(),grid.getBlockCellNo());
						
						if(recGrid!=null)
						{
							
							System.out.println("existingCell  :"+recGrid.getYardLocation());
							
							System.out.println("recGrid.getYardBlock()  :"+recGrid.getYardBlock());
							
							
							System.out.println("existingCell  recGrid.getBlockCellNo()  :"+recGrid.getBlockCellNo());
							
							YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
									recGrid.getYardLocation(), recGrid.getYardBlock(), recGrid.getBlockCellNo());

							
							System.out.println("existingCell  :"+existingCell);
							if (existingCell != null) {
							    BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed() : BigDecimal.ZERO;
							    BigDecimal gridCellAreaAllocated = grid.getCellAreaAllocated() != null ? grid.getCellAreaAllocated() : BigDecimal.ZERO;
							    BigDecimal recGridCellAreaAllocated = recGrid.getCellAreaAllocated() != null ? recGrid.getCellAreaAllocated() : BigDecimal.ZERO;

							    existingCell.setCellAreaUsed(existingCellAreaUsed.add(gridCellAreaAllocated).subtract(recGridCellAreaAllocated));

							    YardBlockCell  jashdg =  yardBlockCellRepository.save(existingCell);
							    
							    System.out.println("jashdg             :"+jashdg);
							}
							
							recGrid.setReceivedPackages(grid.getReceivedPackages());
							recGrid.setCellAreaAllocated(grid.getCellAreaAllocated());
							
							recGrid.setCellAreaUsed(grid.getCellAreaAllocated());
							
							 generalReceivingGridRepo.save(recGrid);
						}
						else
						{
							
							GeneralReceivingGrid cf = new GeneralReceivingGrid();
							
							
							Integer maxSrNo = generalReceivingGridRepo.getMaxSrNo(companyId, branchId,
									cfinbondcrg.getReceivingId()); 
							
							cf.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
							
							cf.setCompanyId(companyId);
							cf.setBranchId(branchId);
							cf.setCreatedBy(user);
							cf.setCreatedDate(new Date());
							cf.setApprovedBy(user);
							cf.setApprovedDate(new Date());
							cf.setReceivingId(cfinbondcrg.getReceivingId());
							cf.setYardLocation(grid.getYardLocation());
							cf.setYardBlock(grid.getYardBlock());
							cf.setBlockCellNo(grid.getBlockCellNo());

							cf.setCellArea(grid.getCellArea());
							cf.setCellAreaUsed(grid.getCellAreaAllocated());
							cf.setCellAreaAllocated(grid.getCellAreaAllocated());

							cf.setReceivedPackages(grid.getReceivedPackages());
							cf.setStatus("A");

							GeneralReceivingGrid	gridSaved = generalReceivingGridRepo.save(cf);
			                
			                
			                if (gridSaved != null) 
							{
								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
										gridSaved.getYardLocation(), gridSaved.getYardBlock(), gridSaved.getBlockCellNo());

								if (existingCell != null) {
								    BigDecimal existingCellAreaUsed = existingCell.getCellAreaUsed() != null ? existingCell.getCellAreaUsed() : BigDecimal.ZERO;
								    BigDecimal gridCellAreaAllocated = gridSaved.getCellAreaAllocated() != null ? gridSaved.getCellAreaAllocated() : BigDecimal.ZERO;

								    existingCell.setCellAreaUsed(existingCellAreaUsed.add(gridCellAreaAllocated));

								    yardBlockCellRepository.save(existingCell);
								}
							}
			                
						}

					}
				
					
					
					System.out.println("**************************************************************************************");
					System.out.println(gridArea);		

					findCfBondCrgData.setAreaOccupied(gridArea);

					generalReceivingCrgRepo.save(findCfBondCrgData);
					
			
			
				}

			
			}

		}


		return new ResponseEntity<>(cfinbondcrg, HttpStatus.OK);
	}
	
	public ResponseEntity<List<GeneralReceivingGrid>> getDataAfterSaved(String companyId, String branchId, String reveivingId) {

		List<GeneralReceivingGrid> list = generalReceivingGateInDtlRepo.getDataAfterSave(companyId, branchId, reveivingId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
