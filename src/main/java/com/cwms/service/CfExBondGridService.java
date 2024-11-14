package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.YardBlockCell;
import com.cwms.repository.CfExBondCrgDtlRepository;
import com.cwms.repository.CfExBondGridRepository;
import com.cwms.repository.CfInBondGridRepository;
import com.cwms.repository.CfbondGatePassRepository;
import com.cwms.repository.YardBlockCellRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CfExBondGridService {
	
	@Autowired
	private CfExBondGridRepository cfExBondGridRepository;

	@Autowired
	public YardBlockCellRepository yardBlockCellRepository;
	
	@Autowired
	private CfInBondGridRepository cfInBondGridRepository;
	
	@Autowired
	private CfExBondCrgDtlRepository cfExBondCrgDtlRepository;
	
	@Autowired
	private CfbondGatePassRepository cfbondGatePassRepository;
	
	
	public ResponseEntity<List<CfInBondGrid>> getDataOfInBondGrid(
		    String companyId, String branchId, String inBondingId, String nocTransId, String cfBondDtlId) {

		    List<CfInBondGrid> list = cfInBondGridRepository.getDataForExbondGrid(
		        companyId, branchId, inBondingId, nocTransId, cfBondDtlId);

		    //return ResponseEntity.ok(list); // Cleaner way to return OK status with data
		    return  new ResponseEntity<>(list,HttpStatus.OK); 
		}
	
	
	public ResponseEntity<List<CfExBondGrid>> getDataAfterSave(String companyId, String branchId, String exBondingId, String cfBondDtlId) {
        List<CfExBondGrid> list = cfExBondGridRepository.getDataAfterSave(companyId, branchId, exBondingId, cfBondDtlId);
        
        return new ResponseEntity<List<CfExBondGrid>>(list,HttpStatus.OK);
    }
	
	
	
	
	
	
	@Transactional
	public ResponseEntity<?> saveDataInCFExbondGrid(String companyId, String branchId, String flag, String user,
			Map<String, Object> dataToSave) {

		System.out.println("flag________________________________" + flag);

		List<CfExBondGrid> existingData = null;
		
		List<CfExBondGrid> savedList = new ArrayList<>();
		
		CfExBondGrid saved  =null;
		
		Set<String> uniqueEntries = new HashSet<>();
        Map<String, List<Integer>> errorYardLocations = new HashMap<>();
        
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

// Convert "modal" data to CfInBondGrid
		CfExBondGrid cfinbondcrgGrid = objectMapper.convertValue(dataToSave.get("modal"), CfExBondGrid.class);

		System.out.println("modal________________________________"+cfinbondcrgGrid);
// Extract and convert the grid data
		Object nocDtlObj = dataToSave.get("grid");
		List<CfExBondGrid> cfinbondcrgDtlList = new ArrayList<>();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = objectMapper.convertValue(nocDtlObj, new TypeReference<List<CfExBondGrid>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfExBondGrid cfinbondcrgDtl = objectMapper.convertValue(entry.getValue(), CfExBondGrid.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}
		
		
		System.out.println("cfinbondcrgDtlList________________________________"+cfinbondcrgDtlList);
		

        for (CfExBondGrid impexpgrid : cfinbondcrgDtlList) 
        {
            // Create a unique key based on relevant fields
            String uniqueKey = impexpgrid.getCompanyId() + "|" +
                    impexpgrid.getBranchId() + "|" +
                    impexpgrid.getYardLocation() + "|" +
                    impexpgrid.getYardBlock() + "|" +
                    impexpgrid.getBlockCellNo() + "|" +
                    impexpgrid.getSrNo() ;
            // Check for existence in the database
            boolean existsInDatabase = cfExBondGridRepository.isDataExist(
            		companyId, branchId,
					cfinbondcrgGrid.getExBondingId(), cfinbondcrgGrid.getCfBondDtlId(), impexpgrid.getYardLocation(),
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
				for (CfExBondGrid grid : cfinbondcrgDtlList)
				{
					
					CfExBondGrid cf = cfExBondGridRepository.getExistingData(companyId, branchId,
							cfinbondcrgGrid.getExBondingId(), cfinbondcrgGrid.getCfBondDtlId(), grid.getSrNo());
					
					if (cf!=null)
					{
						cf.setYardLocation(grid.getYardLocation());
						cf.setYardBlock(grid.getYardBlock());
						cf.setBlockCellNo(grid.getBlockCellNo());
						
						cf.setCellAreaAllocated(grid.getCellAreaAllocated());
						cf.setInBondPackages(grid.getInBondPackages());
						cf.setExBondPackages(grid.getExBondPackages());
						cf.setExCellAreaAllocated(grid.getExCellAreaAllocated());
						cf.setStatus("A");
						cf.setEditedBy(user);
						cf.setEditedDate(new Date());

						 saved = cfExBondGridRepository.save(cf);

						savedList.add(saved);
						
						if (saved != null) {
							
							CfexBondCrgDtl toEditData =cfExBondCrgDtlRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
							
							if (toEditData!=null)
							{
								int updateGrid =cfExBondCrgDtlRepository.updateCfexBondDtlAfterExBondGrid(saved.getExCellAreaAllocated(),saved.getExCellAreaAllocated(),
										companyId,branchId,saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
								
								System.out.println("updated row in CfexBondCrgDtl :"+updateGrid);
							}
							
							
							
						}
					}
					else 
					{
						CfExBondGrid cf1 = new CfExBondGrid();
						
						Integer maxSrNo = cfExBondGridRepository.getMaxSrNo(companyId, branchId, cfinbondcrgGrid.getExBondingId(),
								cfinbondcrgGrid.getCfBondDtlId());

//						cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
						cf1.setSrNo(grid.getSrNo());
						cf1.setCompanyId(companyId);
						cf1.setBranchId(branchId);
						cf1.setCreatedBy(user);
						cf1.setCreatedDate(new Date());
						cf1.setApprovedBy(user);
						cf1.setApprovedDate(new Date());
						cf1.setInBondingId(grid.getInBondingId());
						cf1.setExBondingId(cfinbondcrgGrid.getExBondingId());
						cf1.setCfBondDtlId(cfinbondcrgGrid.getCfBondDtlId());
						
						cf1.setYardLocation(grid.getYardLocation());
						cf1.setYardBlock(grid.getYardBlock());
						cf1.setBlockCellNo(grid.getBlockCellNo());
						cf1.setExBondPackages(grid.getExBondPackages());
						cf1.setExCellAreaAllocated(grid.getExCellAreaAllocated());
						cf1.setInBondPackages(grid.getInBondPackages());

						cf1.setCellAreaAllocated(grid.getCellAreaAllocated());
					
						cf1.setNocTransId(cfinbondcrgGrid.getNocTransId());
						cf1.setFinYear("2025");
					
						cf1.setInBondPackages(grid.getInBondPackages());
						cf1.setStatus("A");

						 saved = cfExBondGridRepository.save(cf1);
						
						
					    savedList.add(saved);
						
						
						
						if (saved != null) 
						{
							
							
							
						}
					}
		

				}
				

		} 

		return new ResponseEntity<>(savedList, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Transactional
	public ResponseEntity<?> updateDataInExbondGridAfterBondGatePass(String companyId, String branchId, String flag, String user,
			Map<String, Object> dataToSave) {

		System.out.println("flag________________________________" + flag);

		List<CfExBondGrid> existingData = null;
		
		List<CfExBondGrid> savedList = new ArrayList<>();
		
		CfExBondGrid saved  =null;
		
		Set<String> uniqueEntries = new HashSet<>();
        Map<String, List<Integer>> errorYardLocations = new HashMap<>();
        
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		CfExBondGrid cfinbondcrgGrid = objectMapper.convertValue(dataToSave.get("modal"), CfExBondGrid.class);

		System.out.println("modal________________________________"+cfinbondcrgGrid.getGatePassId());

		Object nocDtlObj = dataToSave.get("grid");
		List<CfExBondGrid> cfinbondcrgDtlList = new ArrayList<>();

		if (nocDtlObj instanceof List) {
			cfinbondcrgDtlList = objectMapper.convertValue(nocDtlObj, new TypeReference<List<CfExBondGrid>>() {
			});
		} else if (nocDtlObj instanceof Map) {
			Map<String, Object> nocDtlMap = (Map<String, Object>) nocDtlObj;
			for (Map.Entry<String, Object> entry : nocDtlMap.entrySet()) {
				CfExBondGrid cfinbondcrgDtl = objectMapper.convertValue(entry.getValue(), CfExBondGrid.class);
				cfinbondcrgDtlList.add(cfinbondcrgDtl);
			}
		} else {
			throw new IllegalArgumentException("Invalid type for nocDtl: " + nocDtlObj.getClass());
		}
		
		
		System.out.println("cfinbondcrgDtlList________________________________"+cfinbondcrgDtlList);
		

//        for (CfExBondGrid impexpgrid : cfinbondcrgDtlList) 
//        {
//            // Create a unique key based on relevant fields
//            String uniqueKey = impexpgrid.getCompanyId() + "|" +
//                    impexpgrid.getBranchId() + "|" +
//                    impexpgrid.getYardLocation() + "|" +
//                    impexpgrid.getYardBlock() + "|" +
//                    impexpgrid.getBlockCellNo() + "|" +
//                    impexpgrid.getSrNo() ;
//            // Check for existence in the database
//            boolean existsInDatabase = cfExBondGridRepository.isDataExist(
//            		companyId, branchId,
//					cfinbondcrgGrid.getExBondingId(), cfinbondcrgGrid.getCfBondDtlId(), impexpgrid.getYardLocation(),
//					impexpgrid.getYardBlock(), impexpgrid.getBlockCellNo(), impexpgrid.getSrNo());
//
//            // If it exists in the database, add to errors
//            if (existsInDatabase) {
//                errorYardLocations
//                    .computeIfAbsent("Duplicate in DB", k -> new ArrayList<>())
//                    .add(impexpgrid.getSrNo());
//            }
//
//            // Check for duplicates in the list
//            if (!uniqueEntries.add(uniqueKey)) {
//                errorYardLocations
//                    .computeIfAbsent("Duplicate in List", k -> new ArrayList<>())
//                    .add(impexpgrid.getSrNo());
//            }
//        }
//        
//
//
//        if (!errorYardLocations.isEmpty()) {
//            return ResponseEntity.badRequest().body(errorYardLocations);
//        }

		
		System.out.println("existingData_____________________________________"+existingData);

		if (cfinbondcrgDtlList != null) 
		{
				for (CfExBondGrid grid : cfinbondcrgDtlList)
				{
					
					CfExBondGrid cf = cfExBondGridRepository.getExistingData(companyId, branchId,
							cfinbondcrgGrid.getExBondingId(), cfinbondcrgGrid.getCfBondDtlId(), grid.getSrNo());

					if (cf!=null)
					{
						
						BigDecimal exitsQty =cf.getQtyTakenOut()!=null ? cf.getQtyTakenOut() :BigDecimal.ZERO;
						BigDecimal existArea =cf.getAreaReleased() !=null ? cf.getAreaReleased() : BigDecimal.ZERO;
						
						System.out.println("exitsQty______________________"+exitsQty);
						
						cf.setQtyTakenOut(grid.getQtyTakenOut());
						cf.setAreaReleased(grid.getAreaReleased());
						cf.setStatus("A");
						cf.setEditedBy(user);
						cf.setEditedDate(new Date());

						 saved = cfExBondGridRepository.save(cf);

						savedList.add(saved);
						
						if (saved != null) {
							
							CFBondGatePass toEditData =cfbondGatePassRepository.toEditData(companyId, branchId,cfinbondcrgGrid.getGatePassId(),saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
							
							if (toEditData!=null)
							{
								
								BigDecimal existingQtyTakenOut = toEditData.getYardQtyTakenOut() != null ? toEditData.getYardQtyTakenOut() : BigDecimal.ZERO;
								BigDecimal savedQtyTakenOut = toEditData.getYardAreaReleased() != null ? toEditData.getYardAreaReleased() : BigDecimal.ZERO;
								
								int updateGrid =cfbondGatePassRepository.updateBondGatePassAtterOutExBondGrid(existingQtyTakenOut.add(saved.getQtyTakenOut()).subtract(exitsQty),
										savedQtyTakenOut.add(saved.getAreaReleased()).subtract(existArea),
										
										companyId,branchId,cfinbondcrgGrid.getGatePassId(),saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
								
								System.out.println("updated row in CfexBondCrgDtl :"+updateGrid);
							}
							
//							CfInBondGrid getExistingData=cfInBondGridRepository.getExistingData(companyId, branchId, saved.getInBondingId(), saved.getCfBondDtlId(), saved.getSrNo());
							CfInBondGrid getExistingData=cfInBondGridRepository.toEditDataAFTERGATEPASS(companyId, branchId, saved.getInBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo(), saved.getSrNo());
							if(getExistingData!=null)
							{
								BigDecimal existingQtyTakenOut = getExistingData.getQtyTakenOut() != null ? getExistingData.getQtyTakenOut() : BigDecimal.ZERO;
								BigDecimal savedQtyTakenOut = saved.getQtyTakenOut() != null ? saved.getQtyTakenOut() : BigDecimal.ZERO;
								
								getExistingData.setQtyTakenOut(existingQtyTakenOut.add(savedQtyTakenOut).subtract(exitsQty));

								BigDecimal existingAreaReleased = getExistingData.getAreaReleased() != null ? getExistingData.getAreaReleased() : BigDecimal.ZERO;
								BigDecimal savedAreaReleased = saved.getAreaReleased() != null ? saved.getAreaReleased() : BigDecimal.ZERO;
								getExistingData.setAreaReleased(existingAreaReleased.add(savedAreaReleased).subtract(existArea));

								
//								getExistingData.setQtyTakenOut(getExistingData.getQtyTakenOut().add(saved.getQtyTakenOut()));
//								getExistingData.setAreaReleased(getExistingData.getAreaReleased().add(saved.getAreaReleased()));
								
								cfInBondGridRepository.save(getExistingData);
							}
							
							CfExBondGrid toEditData1=cfExBondGridRepository.toEditData(companyId, branchId, saved.getExBondingId(), saved.getCfBondDtlId(), saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo(), saved.getSrNo());
							if(toEditData1!=null)
							{
//								toEditData1.setQtyTakenOut(toEditData1.getQtyTakenOut().add(saved.getQtyTakenOut()));
//								toEditData1.setAreaReleased(toEditData1.getAreaReleased().add(saved.getAreaReleased()));
								
								
								BigDecimal editQtyTakenOut = toEditData1.getQtyTakenOut() != null ? toEditData1.getQtyTakenOut() : BigDecimal.ZERO;
								BigDecimal savedQtyTakenOut = saved.getQtyTakenOut() != null ? saved.getQtyTakenOut() : BigDecimal.ZERO;
								
								
								System.out.println("editQtyTakenOut______________________"+editQtyTakenOut);
								System.out.println("savedQtyTakenOut______________________"+savedQtyTakenOut);
								System.out.println("exitsQty______________________"+exitsQty);
								
								
								toEditData1.setQtyTakenOut(exitsQty.add(savedQtyTakenOut).subtract(exitsQty));

								BigDecimal editAreaReleased = toEditData1.getAreaReleased() != null ? toEditData1.getAreaReleased() : BigDecimal.ZERO;
								BigDecimal savedAreaReleased = saved.getAreaReleased() != null ? saved.getAreaReleased() : BigDecimal.ZERO;
								
								
								toEditData1.setAreaReleased(existArea.add(savedAreaReleased).subtract(existArea));
								cfExBondGridRepository.save(toEditData1);
							}
							
							YardBlockCell getAllData=  yardBlockCellRepository.getAllData(companyId, branchId, saved.getYardLocation(), saved.getYardBlock(), saved.getBlockCellNo());
							if(getAllData!=null)
							{
//								getAllData.setCellAreaUsed(getAllData.getCellAreaUsed().subtract(saved.getAreaReleased()));
								BigDecimal cellAreaUsed = getAllData.getCellAreaUsed() != null ? getAllData.getCellAreaUsed() : BigDecimal.ZERO;
							    BigDecimal areaReleased = saved.getAreaReleased() != null ? saved.getAreaReleased() : BigDecimal.ZERO;

							    // Subtract AreaReleased from CellAreaUsed
							    getAllData.setCellAreaUsed(cellAreaUsed.subtract(areaReleased).add(existArea));
								
								yardBlockCellRepository.save(getAllData);
							}
						}
					}
					else 
					{
						CfExBondGrid cf1 = new CfExBondGrid();
						
						Integer maxSrNo = cfExBondGridRepository.getMaxSrNo(companyId, branchId, cfinbondcrgGrid.getExBondingId(),
								cfinbondcrgGrid.getCfBondDtlId());

						cf1.setSrNo((maxSrNo == null) ? 1 : maxSrNo + 1);
						cf1.setCompanyId(companyId);
						cf1.setBranchId(branchId);
						cf1.setCreatedBy(user);
						cf1.setCreatedDate(new Date());
						cf1.setApprovedBy(user);
						cf1.setApprovedDate(new Date());
						cf1.setInBondingId(grid.getInBondingId());
						cf1.setExBondingId(cfinbondcrgGrid.getExBondingId());
						cf1.setCfBondDtlId(cfinbondcrgGrid.getCfBondDtlId());
						
						cf1.setYardLocation(grid.getYardLocation());
						cf1.setYardBlock(grid.getYardBlock());
						cf1.setBlockCellNo(grid.getBlockCellNo());
						cf1.setExBondPackages(grid.getExBondPackages());
						cf1.setExCellAreaAllocated(grid.getExCellAreaAllocated());
						cf1.setInBondPackages(grid.getInBondPackages());

						cf1.setCellAreaAllocated(grid.getCellAreaAllocated());
					
						cf1.setNocTransId(cfinbondcrgGrid.getNocTransId());
						cf1.setFinYear("2025");
					
						cf1.setInBondPackages(grid.getInBondPackages());
						cf1.setStatus("A");

						 saved = cfExBondGridRepository.save(cf1);
						
						
					    savedList.add(saved);
						
						
						
						if (saved != null) 
						{
							
							
							
						}
					}
		

				}
				

		} 

		return new ResponseEntity<>(savedList, HttpStatus.OK);
	}
}
