package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.CfInBondGridRepository;
import com.cwms.repository.CfinbondcrgDtlRepo;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class CfInBondGridService {

	@Autowired
	public CfInBondGridRepository cfInBondGridRepository;

	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	
	
	@Autowired
	public CfinbondcrgDtlRepo cfinbondcrgDtlRepo;

//	public ResponseEntity<?> saveDataInCFbondGrid(String compnayId, String branchId, String flag, String user,
//			CfInBondGrid grid) {
//
//		System.out.println("flag________________________________" + flag);
//		
//		Boolean isDataExist = cfInBondGridRepository.isDataExist(compnayId, branchId, grid.getInBondingId(),
//				grid.getCfBondDtlId(), grid.getYardLocation(), grid.getYardBlock(), grid.getBlockCellNo(),grid.getSrNo());
//
//		System.out.println("isDataExist__________________"+isDataExist);
//		if (isDataExist) {
//			return new ResponseEntity<>("Location is in use..."+grid.getYardLocation()+"-"+grid.getYardBlock()+"-"+grid.getBlockCellNo(), HttpStatus.BAD_REQUEST);
//		}
//		CfInBondGrid saved = null;
//		if (grid != null && "add".equals(flag)) {
//			CfInBondGrid cf = new CfInBondGrid();
//			
//			Integer maxSrNo = cfInBondGridRepository.getMaxSrNo(compnayId, branchId, grid.getInBondingId(),
//					grid.getCfBondDtlId());
//
//			cf.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
//
//			cf.setCompanyId(compnayId);
//			cf.setBranchId(branchId);
//			cf.setCreatedBy(user);
//			cf.setCreatedDate(new Date());
//			cf.setApprovedBy(user);
//			cf.setApprovedDate(new Date());
//			cf.setInBondingId(grid.getInBondingId());
//			cf.setCfBondDtlId(grid.getCfBondDtlId());
//			cf.setYardLocation(grid.getYardLocation());
//			cf.setYardBlock(grid.getYardBlock());
//			cf.setBlockCellNo(grid.getBlockCellNo());
//			cf.setCellArea(grid.getCellArea());
//			cf.setCellAreaAllocated(grid.getCellAreaAllocated());
//			cf.setCellAreaUsed(grid.getCellAreaUsed());
//			cf.setNocTransId(grid.getNocTransId());
//			cf.setFinYear("2025");
//			cf.setGateInId(cf.getGateInId());
//			cf.setInBondPackages(grid.getInBondPackages());
//			cf.setStatus("A");
//
//			saved = cfInBondGridRepository.save(cf);
//			if (saved != null) {
//				YardBlockCell getExisting = yardBlockCellRepository.getAllData(compnayId, branchId,
//						saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
//
//				if (getExisting != null) {
//					getExisting.setCellAreaUsed(getExisting.getCellAreaUsed().add(saved.getCellAreaAllocated()));
//					yardBlockCellRepository.save(getExisting);
//				}
//			}
//
//			System.out.println("grid________________________________" + saved);
//		} else {
//			CfInBondGrid cf = cfInBondGridRepository.getExistingData(compnayId, branchId, grid.getInBondingId(),
//					grid.getCfBondDtlId(), grid.getSrNo());
//			if (cf != null) {
//				System.out.println("cf__________________" + cf);
//
//				
//				
//				cf.setYardLocation(grid.getYardLocation());
//				cf.setYardBlock(grid.getYardBlock());
//				cf.setBlockCellNo(grid.getBlockCellNo());
//				cf.setCellArea(grid.getCellArea());
//				cf.setCellAreaAllocated(grid.getCellAreaAllocated());
//				cf.setCellAreaUsed(grid.getCellAreaUsed());
//				cf.setInBondPackages(grid.getInBondPackages());
//				cf.setStatus("A");
//				cf.setEditedBy(user);
//				cf.setEditedDate(new Date());
//
//				saved = cfInBondGridRepository.save(cf);
//
//				if (saved != null) {
//					YardBlockCell getExisting = yardBlockCellRepository.getAllData(compnayId, branchId,
//							saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
//
//					if (getExisting != null) {
//						getExisting.setCellAreaUsed(getExisting.getCellAreaUsed().add(saved.getCellAreaAllocated())
//								.subtract(cf.getCellAreaAllocated()));
//						yardBlockCellRepository.save(getExisting);
//					}
//				}
//
//			}
//
//			System.out.println("in eidt loop");
//		}
//
//		return new ResponseEntity<>(saved, HttpStatus.OK);
//	}

	public ResponseEntity<List<CfInBondGrid>> getDataAfterSaved(String companyId, String branchId, String inBondingId,
			String cfBondDtlId) {

		List<CfInBondGrid> list = cfInBondGridRepository.getDataAfterSave(companyId, branchId, inBondingId,
				cfBondDtlId);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	public ResponseEntity<?> getDeleteRecord(String companyId, String branchId, String inBondingId, String cfBondDtlId,
			Integer srNo) {
		CfInBondGrid list = cfInBondGridRepository.getExistingData(companyId, branchId, inBondingId, cfBondDtlId, srNo);
		if (list != null) {
			list.setStatus("D");
			cfInBondGridRepository.save(list);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	
	
	
	
	public ResponseEntity<?> saveDataInCFbondGrid(String companyId, String branchId, String flag, String user,
			Map<String, Object> dataToSave) {

		
		
		
		
		System.out.println("flag________________________________" + flag);

		List<CfInBondGrid> existingData = null;
		
		List<CfInBondGrid> savedList = new ArrayList<>();
		
		CfInBondGrid saved  =null;
		
		Set<String> uniqueEntries = new HashSet<>();
        Map<String, List<Integer>> errorYardLocations = new HashMap<>();
        
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

// Convert "modal" data to CfInBondGrid
		CfInBondGrid cfinbondcrgGrid = objectMapper.convertValue(dataToSave.get("modal"), CfInBondGrid.class);

		System.out.println("modal________________________________"+cfinbondcrgGrid);
// Extract and convert the grid data
		Object nocDtlObj = dataToSave.get("grid");
		List<CfInBondGrid> cfinbondcrgDtlList = new ArrayList<>();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = objectMapper.convertValue(nocDtlObj, new TypeReference<List<CfInBondGrid>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfInBondGrid cfinbondcrgDtl = objectMapper.convertValue(entry.getValue(), CfInBondGrid.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}
		
		
		System.out.println("cfinbondcrgDtlList________________________________"+cfinbondcrgDtlList);
		


        for (CfInBondGrid impexpgrid : cfinbondcrgDtlList) 
        {
            // Create a unique key based on relevant fields
            String uniqueKey = impexpgrid.getCompanyId() + "|" +
                    impexpgrid.getBranchId() + "|" +
                    impexpgrid.getYardLocation() + "|" +
                    impexpgrid.getYardBlock() + "|" +
                    impexpgrid.getBlockCellNo() + "|" +
                    impexpgrid.getSrNo() ;
            // Check for existence in the database
            boolean existsInDatabase = cfInBondGridRepository.isDataExist(
            		companyId, branchId,
					cfinbondcrgGrid.getInBondingId(), cfinbondcrgGrid.getCfBondDtlId(), impexpgrid.getYardLocation(),
					impexpgrid.getYardBlock(), impexpgrid.getBlockCellNo(), impexpgrid.getSrNo());

            // If it exists in the database, add to errors
            if (existsInDatabase) {
                errorYardLocations
                    .computeIfAbsent("Duplicate in DB", k -> new ArrayList<>())
                    .add(impexpgrid.getSrNo());
            }

            // Check for duplicates in the list
            if (!uniqueEntries.add(uniqueKey)) {
                errorYardLocations
                    .computeIfAbsent("Duplicate in List", k -> new ArrayList<>())
                    .add(impexpgrid.getSrNo());
            }
        }
        


        if (!errorYardLocations.isEmpty()) {
            // Return a response with error yard locations
            return ResponseEntity.badRequest().body(errorYardLocations);
        }

		
		System.out.println("existingData_____________________________________"+existingData);

//		if (cfinbondcrgDtlList != null && "add".equals(flag)) 
		if (cfinbondcrgDtlList != null) 
		{
		

				for (CfInBondGrid grid : cfinbondcrgDtlList)
				{
					
					CfInBondGrid cf = cfInBondGridRepository.getExistingData(companyId, branchId,
							cfinbondcrgGrid.getInBondingId(), cfinbondcrgGrid.getCfBondDtlId(), grid.getSrNo());
					
					
				
					if (cf!=null)
					{
						BigDecimal exitpack = cf.getInBondPackages() !=null ? cf.getInBondPackages() :BigDecimal.ZERO;
						BigDecimal exitarea = cf.getCellAreaAllocated() !=null ? cf.getCellAreaAllocated() : BigDecimal.ZERO;
						cf.setYardLocation(grid.getYardLocation());
						cf.setYardBlock(grid.getYardBlock());
						cf.setBlockCellNo(grid.getBlockCellNo());
						cf.setCellArea(grid.getCellArea());
						cf.setCellAreaAllocated(grid.getCellAreaAllocated());
						cf.setCellAreaUsed(grid.getCellAreaUsed());
						cf.setInBondPackages(grid.getInBondPackages());
						cf.setStatus("A");
						cf.setEditedBy(user);
						cf.setEditedDate(new Date());

						 saved = cfInBondGridRepository.save(cf);

						savedList.add(saved);
						
						if (saved != null) {
							
							
							
	CfinbondcrgDtl toEditData =cfinbondcrgDtlRepo.toEditData(companyId, branchId, saved.getInBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
							
							if (toEditData!=null)
							{
								toEditData.setYardPackages( toEditData.getYardPackages().add(saved.getInBondPackages()).subtract(exitpack));
								toEditData.setCellAreaAllocated(toEditData.getCellAreaAllocated().add(saved.getCellAreaAllocated()).subtract(exitarea));
								 
								
								
								cfinbondcrgDtlRepo.save(toEditData);
							}
							
							
							YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
									saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

							if (existingCell != null) {
								existingCell.setCellAreaUsed(existingCell.getCellAreaUsed()
										.add(saved.getCellAreaAllocated()).subtract(exitarea));
								yardBlockCellRepository.save(existingCell);
							}
						}
					}
					else 
					{
						CfInBondGrid cf1 = new CfInBondGrid();
						Integer maxSrNo = cfInBondGridRepository.getMaxSrNo(companyId, branchId, cfinbondcrgGrid.getInBondingId(),
								cfinbondcrgGrid.getCfBondDtlId());

						cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
						cf1.setCompanyId(companyId);
						cf1.setBranchId(branchId);
						cf1.setCreatedBy(user);
						cf1.setCreatedDate(new Date());
						cf1.setApprovedBy(user);
						cf1.setApprovedDate(new Date());
						cf1.setInBondingId(cfinbondcrgGrid.getInBondingId());
						cf1.setCfBondDtlId(cfinbondcrgGrid.getCfBondDtlId());
						cf1.setYardLocation(grid.getYardLocation());
						cf1.setYardBlock(grid.getYardBlock());
						cf1.setBlockCellNo(grid.getBlockCellNo());
						cf1.setCellArea(grid.getCellArea());
						cf1.setCellAreaAllocated(grid.getCellAreaAllocated());
						cf1.setCellAreaUsed(grid.getCellAreaUsed());
						cf1.setNocTransId(cfinbondcrgGrid.getNocTransId());
						cf1.setFinYear("2025");
						cf1.setGateInId(grid.getGateInId()); // Corrected from cf.getGateInId()
						cf1.setInBondPackages(grid.getInBondPackages());
						cf1.setStatus("A");

						 saved = cfInBondGridRepository.save(cf1);
						
						
					    savedList.add(saved);
						
						
						
						if (saved != null) {
							YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
									saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());

							if (existingCell != null) {
								existingCell.setCellAreaUsed(existingCell.getCellAreaUsed().add(saved.getCellAreaAllocated()));
								yardBlockCellRepository.save(existingCell);
							}
						}
					}
		

				}
				

		} 
//		else 
//		{
//
//			if (cfinbondcrgDtlList != null) 
//			{
//					
//					for (CfInBondGrid grid :cfinbondcrgDtlList)
//					{
//						CfInBondGrid cf = cfInBondGridRepository.getExistingData(companyId, branchId,
//								cfinbondcrgGrid.getInBondingId(), cfinbondcrgGrid.getCfBondDtlId(), grid.getSrNo());
//
//						if (cf != null) {
//							cf.setYardLocation(grid.getYardLocation());
//							cf.setYardBlock(grid.getYardBlock());
//							cf.setBlockCellNo(grid.getBlockCellNo());
//							cf.setCellArea(grid.getCellArea());
//							cf.setCellAreaAllocated(grid.getCellAreaAllocated());
//							cf.setCellAreaUsed(grid.getCellAreaUsed());
//							cf.setInBondPackages(grid.getInBondPackages());
//							cf.setStatus("A");
//							cf.setEditedBy(user);
//							cf.setEditedDate(new Date());
//
//							 saved = cfInBondGridRepository.save(cf);
//
//							savedList.add(saved);
//							
//							if (saved != null) {
//								YardBlockCell existingCell = yardBlockCellRepository.getAllData(companyId, branchId,
//										saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
//
//								if (existingCell != null) {
//									existingCell.setCellAreaUsed(existingCell.getCellAreaUsed()
//											.add(saved.getCellAreaAllocated()).subtract(cf.getCellAreaAllocated()));
//									yardBlockCellRepository.save(existingCell);
//								}
//							}
//						}
//					}
//
//			}
//		}

		return new ResponseEntity<>(savedList, HttpStatus.OK);
	}
	
	
	
	

	
//	@PostMapping("/saveImpExpGrid")
//	public ResponseEntity<?> saveImpExpGrid(
//	                                         @RequestParam("userId") String userId,	                                         
//	                                         @RequestBody Map<String, Object> requestData
//	                                        ) {
//	    List<Impexpgrid> savedImpexpgrids = new ArrayList<>();
//	    try {
//	    		    	
//	    	List<Impexpgrid> impexpgrids = objectMapper.convertValue(requestData.get("yardGrid"),
//					new TypeReference<List<Impexpgrid>>() {
//					});
//	    	ExportCarting cartingObject = objectMapper.convertValue(requestData.get("cartingObject"), ExportCarting.class);
//	    			
//	    	
//	    	Set<String> uniqueEntries = new HashSet<>();
//	        Map<String, List<Integer>> errorYardLocations = new HashMap<>();
//
//	        for (Impexpgrid impexpgrid : impexpgrids) 
//	        {
//	            // Create a unique key based on relevant fields
//	            String uniqueKey = impexpgrid.getCompanyId() + "|" +
//	                    impexpgrid.getBranchId() + "|" +
//	                    impexpgrid.getYardLocation() + "|" +
//	                    impexpgrid.getYardBlock() + "|" +
//	                    impexpgrid.getBlockCellNo() + "|" +
//	                    impexpgrid.getProcessTransId() + "|" +
//	                    impexpgrid.getLineNo() + "|" +
//	                    impexpgrid.getSubSrNo();
//
//	            // Check for existence in the database
//	            boolean existsInDatabase = impGridRepo.existsByYardGrid(
//	                impexpgrid.getCompanyId(),
//	                impexpgrid.getBranchId(),
//	                impexpgrid.getYardLocation(),
//	                impexpgrid.getYardBlock(),
//	                impexpgrid.getBlockCellNo(),
//	                impexpgrid.getProcessTransId(),
//	                impexpgrid.getLineNo(),
//	                impexpgrid.getSubSrNo()
//	            );
//
//	            // If it exists in the database, add to errors
//	            if (existsInDatabase) {
//	                errorYardLocations
//	                    .computeIfAbsent("Duplicate in DB", k -> new ArrayList<>())
//	                    .add(impexpgrid.getSubSrNo());
//	            }
//
//	            // Check for duplicates in the list
//	            if (!uniqueEntries.add(uniqueKey)) {
//	                errorYardLocations
//	                    .computeIfAbsent("Duplicate in List", k -> new ArrayList<>())
//	                    .add(impexpgrid.getSubSrNo());
//	            }
//	        }
//	        
//	        
//	        
//	        
//
//	        if (!errorYardLocations.isEmpty()) {
//	            // Return a response with error yard locations
//	            return ResponseEntity.badRequest().body(errorYardLocations);
//	        }
//
//	    	
////	    	 System.out.println("cartingObject /n" +cartingObject);
//	        for (Impexpgrid impexpgrid : impexpgrids) 
//	        
//	        {
//	            System.out.println(impexpgrid);
//
//	            Impexpgrid sendImpexpgrid = new Impexpgrid();
//
//	            Impexpgrid ExistImpexpgrid = impGridRepo.getImpexpgridSubLineNo(impexpgrid.getCompanyId(),
//	                    impexpgrid.getBranchId(), impexpgrid.getYardLocation(),
//	                    impexpgrid.getYardBlock(), impexpgrid.getBlockCellNo(),
//	                    impexpgrid.getProcessTransId(), impexpgrid.getLineNo(),
//	                    impexpgrid.getSubSrNo());
//
//	            if (ExistImpexpgrid != null) 
//	            {
//	            	
//	            	
//	            	BigDecimal allocatedExist = ExistImpexpgrid.getCellAreaAllocated() != null ? ExistImpexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;
//					BigDecimal allocatedNew = impexpgrid.getCellAreaAllocated() != null ? impexpgrid.getCellAreaAllocated() : BigDecimal.ZERO;
//	
//					// Calculate the difference
//					BigDecimal Difference = allocatedExist.subtract(allocatedNew);
//	//
//					int allocatedExistPackages = ExistImpexpgrid.getYardPackages();
//					int allocatedNewPackages = impexpgrid.getYardPackages();
//					
//					System.out.println("allocatedExistPackages "+allocatedExistPackages + "allocatedNewPackages" + allocatedNewPackages );
//	
//					System.out.println("allocatedExist "+allocatedExist + "allocatedNew" + allocatedNew );
//					
//					
//					 ExportCarting existing = cartingRepo.getCartingByIds(cartingObject.getCompanyId(), cartingObject.getBranchId(), cartingObject.getProfitcentreId(),
//							 cartingObject.getGateInId(), cartingObject.getCartingTransId(),
//							 cartingObject.getSbTransId(), cartingObject.getSbNo());
//
//						
//						BigDecimal yardPackages = existing.getYardPackages();
//						BigDecimal areaOccupied = existing.getAreaOccupied();
//						
//						existing.setYardPackages(yardPackages.subtract(new BigDecimal(allocatedExistPackages)).add(new BigDecimal(allocatedNewPackages)));
//
//						existing.setAreaOccupied(areaOccupied.subtract(allocatedExist).add(allocatedNew));
//
////						existing.setAreaOccupied(areaOccupied.add(allocatedArea));
//						
//						cartingRepo.save(existing);
//	            	
//				    // Update yard cell area used
//	                updateYardCellArea(impexpgrid, Difference,cartingObject,allocatedExistPackages-allocatedNewPackages);
//	                
//	                ExistImpexpgrid.setCellAreaUsed(impexpgrid.getCellAreaUsed());
//	                ExistImpexpgrid.setCellAreaAllocated(impexpgrid.getCellAreaAllocated());
//	                ExistImpexpgrid.setYardPackages(impexpgrid.getYardPackages());
//	                ExistImpexpgrid.setEditedBy(userId);
//	                ExistImpexpgrid.setEditedDate(new Date());
//	                sendImpexpgrid = impGridRepo.save(ExistImpexpgrid);
//
//	            
//	            } else {
//	                String financialYear = helperMethods.getFinancialYear();
//	                int maxSubSrNo = impGridRepo.getMaxSubSrNo(impexpgrid.getCompanyId(), impexpgrid.getBranchId(), impexpgrid.getProcessTransId(), impexpgrid.getLineNo());
//	                
//	                impexpgrid.setSubSrNo(maxSubSrNo + 1);
//	                impexpgrid.setFinYear(financialYear);
//	                impexpgrid.setStatus("A");
//	                impexpgrid.setCreatedBy(userId);
//	                impexpgrid.setEditedBy(userId);
//	                impexpgrid.setEditedDate(new Date());
//	                impexpgrid.setCreatedDate(new Date());
//	                impexpgrid.setApprovedBy(userId);
//	                impexpgrid.setApprovedDate(new Date());
//	                sendImpexpgrid = impGridRepo.save(impexpgrid);
//	                
//	                
//	                ExportCarting existing = cartingRepo.getCartingByIds(cartingObject.getCompanyId(), cartingObject.getBranchId(), cartingObject.getProfitcentreId(), cartingObject.getGateInId(), cartingObject.getCartingTransId(), cartingObject.getSbTransId(), cartingObject.getSbNo());
//
//	                System.out.println("existing /n" +existing);
//					BigDecimal yardPackages = existing.getYardPackages();
//					BigDecimal areaOccupied = existing.getAreaOccupied();
//					
//					existing.setYardPackages(yardPackages.add(new BigDecimal(impexpgrid.getYardPackages())));
//
//					existing.setAreaOccupied(areaOccupied.add(impexpgrid.getAreaReleased()));
//
//	                // Update yard cell area used
//	                updateYardCellArea(impexpgrid, impexpgrid.getCellAreaAllocated(),cartingObject, impexpgrid.getYardPackages());
//	            }
//	            savedImpexpgrids.add(sendImpexpgrid);
//	        }
//	        return ResponseEntity.ok(savedImpexpgrids);
//	    } catch (Exception e) {
//	        System.out.println(e);
//	        // Return an appropriate error response
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                .body("An error occurred while saving the Impexpgrid data.");
//	    }
//	}
//
//	private void updateYardCellArea(Impexpgrid impexpgrid, BigDecimal allocatedArea, ExportCarting cartingEntry, int packages) {
//	    YardBlockCell yardCellByCellNo = yardBlockCellRepository.getYardCellByCellNo(
//	            impexpgrid.getCompanyId(),
//	            impexpgrid.getBranchId(),
//	            impexpgrid.getYardLocation(),
//	            impexpgrid.getYardBlock(),
//	            impexpgrid.getBlockCellNo()
//	    );
//
//	    System.out.println("packages : "+packages + " new BigDecimal(packages) "+new BigDecimal(packages));
//	    
//	   
//	    if (yardCellByCellNo != null) {
//	    	
//	        BigDecimal currentCellAreaUsed = yardCellByCellNo.getCellAreaUsed() != null ? yardCellByCellNo.getCellAreaUsed() : BigDecimal.ZERO;
//	        yardCellByCellNo.setCellAreaUsed(currentCellAreaUsed.add(allocatedArea));
//	        yardBlockCellRepository.save(yardCellByCellNo);
//	    }
//	}

}
