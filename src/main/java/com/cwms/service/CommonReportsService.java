package com.cwms.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.Branch;
import com.cwms.entities.Company;
import com.cwms.entities.ContainerCountDTO;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CommonReportsRepo;
import com.cwms.repository.CompanyRepo;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;

@Service
public class CommonReportsService {

	@Autowired
	private CommonReportsRepo commonReportRepo;
	
	
	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private BranchRepo branchRepo;
	
	

	
	
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForMovementSummeryReport(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
	    List<Object[]> joGateIn = commonReportRepo.findDistinctContainersJoGateIn(companyId, branchId, startDate, endDate);
	    List<Object[]> sealCutting = commonReportRepo.findDistinctContainersJoGateInForSealCuttting(companyId, branchId, startDate, endDate);
	    List<Object[]> impGateIn = commonReportRepo.findDistinctGateInData(companyId, branchId, startDate, endDate);
	    List<Object[]> importCustomes = commonReportRepo.findDistinctContainersForImportCustomExam(companyId, branchId, startDate, endDate);

	    
	    List<Object[]> loadedInventory = commonReportRepo.getLoadedInventoryData(companyId, branchId, endDate);
	   
	        
	    
	    
	 /// Initialize a map to store counts by container type
	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();

	    if (loadedInventory != null) {
	        for (Object[] row : loadedInventory) {
	            // Assuming that row[0] is container size and row[2] is container type
	            String containerSize = (String) row[1]; // Container size (20, 40, etc.)
	            String containerType = (String) row[4]; // Container type (e.g., General, Custom)

	            // Handle if containerType is null or empty, default to "General"
	            if (containerType == null || containerType.isEmpty()) {
	                containerType = "General"; // Default to General if container type is null or empty
	            }

	            // Initialize the map for this container type if not already present
	            if (!containerCountsByType.containsKey(containerType)) {
	                containerCountsByType.put(containerType, new HashMap<>());
	            }

	            // Get the map for this container type
	            Map<String, Object> counts = containerCountsByType.get(containerType);

	            // Count based on container size
	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                // Safely cast and increment count for 20ft containers
	                int currentCount20 = (int) counts.getOrDefault("20", 0);
	                counts.put("20", currentCount20 + 1);
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                // Safely cast and increment count for 40ft containers
	                int currentCount40 = (int) counts.getOrDefault("40", 0);
	                counts.put("40", currentCount40 + 1);
	            }
	        }
	    }

	    // Add total and type_of_container to each container type
	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
	        Map<String, Object> counts = entry.getValue();
	        int count20 = (int) counts.getOrDefault("20", 0);
	        int count40 = (int) counts.getOrDefault("40", 0);
	        int totalCount = count20 + count40;
	        counts.put("total", totalCount); // Add total count for this container type
	        counts.put("type_of_container", entry.getKey()); // Add the type of container
	    }


	    System.out.println("importCustomes :"+importCustomes.size());
	    Map<String, Object> containerCounts = new HashMap<>();
	    int count20 = 0;
	    int count40 = 0;
	    int total = 0;

	    // Process job order data
	    if (joGateIn != null) {
	        for (Object[] row : joGateIn) {
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                count20++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                count40++;
	            }
	            containerCounts.put(countCategory, (Integer) containerCounts.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCount = (count40 * 2) + count20;
	    int totalCount = count40 + count20;
	    containerCounts.put("total", totalCount);
	    containerCounts.put("Tues", tuesCount);
	    containerCounts.put("name", "JOB ORDER");
	   

	    // Initialize container counts for seal cutting
	    Map<String, Object> containerCountsSealCutting = new HashMap<>();
	    int countSeal20 = 0;
	    int countSeal40 = 0;

	    // Process seal cutting data (corrected loop)
	    if (sealCutting != null) {
	        for (Object[] row : sealCutting) { // Corrected: use sealCutting instead of joGateIn
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                countSeal20++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                countSeal40++;
	            }
	            containerCountsSealCutting.put(countCategory, (Integer) containerCountsSealCutting.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountSeal = (countSeal40 * 2) + countSeal20;
	    int totalCountSeal =countSeal40 + countSeal20;
	    containerCountsSealCutting.put("total", totalCountSeal);
	    containerCountsSealCutting.put("Tues", tuesCountSeal);
	    containerCountsSealCutting.put("name", "SEAL CUTTING");
	    
	    
	    Map<String, Object> containerCountsImpGateIn = new HashMap<>();
	    int countIn20 = 0;
	    int countIn40 = 0;

	    // Process import gate-in data
	    if (impGateIn != null) {
	        for (Object[] row : impGateIn) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                    countIn20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                    countIn40++;
	                }
	                containerCountsImpGateIn.put(countCategory, (Integer) containerCountsImpGateIn.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountIn = (countIn40 * 2) + countIn20;
	    int totalCountIn =countIn40 + countIn20;
	    containerCountsImpGateIn.put("total", totalCountIn);
	    containerCountsImpGateIn.put("Tues", tuesCountIn);
	    containerCountsImpGateIn.put("name", "IMPORT GATE IN");
	    
	    
	    Map<String, Object> importCutsomesData = new HashMap<>();
	    int countCus20 = 0;
	    int countCus40 = 0;

	    if (importCustomes != null) {
	        for (Object[] row : importCustomes) {
	          
	            	System.out.println("row :"+row);
	                String containerSize = (String) row[0]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countCus20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countCus40++;
	                }
	                
	                importCutsomesData.put(countCategory, (Integer) importCutsomesData.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountCus = (countCus40 * 2) + countCus20;
	    int totalCountCus =countCus40 + countCus20;
	    importCutsomesData.put("total", totalCountCus);
	    importCutsomesData.put("Tues", tuesCountCus);
	    importCutsomesData.put("name", "IMPORT CUSTOM EXAM");
	    
	    
	    
	    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    result.put("JOB ORDER", containerCounts);
	    result.put("SEAL CUTTING", containerCountsSealCutting);
	    result.put("IMPORT GATE IN", containerCountsImpGateIn);
	    result.put("IMPORT CUSTOM EXAM", importCutsomesData);

	    return ResponseEntity.ok(result);
	}
	
	
//	public ResponseEntity<Map<String, Map<String, Object>>> getDataForLoadedInventoryReport(
//	        String companyId, String branchId,
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {
//
//	 
//	    List<Object[]> loadedInventory = commonReportRepo.getLoadedInventoryData(companyId, branchId, endDate);
//
//	 /// Initialize a map to store counts by container type
//	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();
//	    
//	  
//	    
//	    if (loadedInventory != null) {
//	    	
//	        for (Object[] row : loadedInventory) {
//	        	
//	            String containerSize = (String) row[1]; // Container size (20, 40, etc.)
//	            
//	            //this is daynanic code 
//	            
//	            String containerType = (String) row[4]; // Container type (e.g., General, Custom)
//	            
//	            // Handle if containerType is null or empty, default to "General"
//	            if (containerType == null || containerType.isEmpty()) {
//	                containerType = "General"; // Default to General if container type is null or empty
//	            }
//
//	            // Initialize the map for this container type if not already present
//	            if (!containerCountsByType.containsKey(containerType)) {
//	                containerCountsByType.put(containerType, new HashMap<>());
//	            }
//
//	            // Get the map for this container type
//	            Map<String, Object> counts = containerCountsByType.get(containerType);
//
//	            // Count based on container size
//	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                // Safely cast and increment count for 20ft containers
//	                int currentCount20 = (int) counts.getOrDefault("20", 0);
//	                counts.put("20", currentCount20 + 1);
//	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                // Safely cast and increment count for 40ft containers
//	                int currentCount40 = (int) counts.getOrDefault("40", 0);
//	                counts.put("40", currentCount40 + 1);
//	            }
//	        }
//	        
//	   
//	    }
//	   
//
//	    Map<String, Map<String, Object>> finalCountsMap = new HashMap<>();
//
//	    int overallCount20 = 0;
//	    int overallCount40 = 0;
//	    int overallTues = 0;
//	    int overallTotal = 0;
//	    
//	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
//	        Map<String, Object> counts = entry.getValue();
//
//	        // Get the counts for 20ft and 40ft containers
//	        int count20 = (int) counts.getOrDefault("20", 0);
//	        int count40 = (int) counts.getOrDefault("40", 0);
//	        
//	        // Calculate the total and the special Tuesday count
//	        int totalCount = count20 + count40;
//	        int totalTues = (count40 * 2) + count20;
//	        
//	        // Add the total count, Tuesday count, and container type to the current container's map
//	        counts.put("total", totalCount);
//	        counts.put("Tues", totalTues); 
//	        counts.put("typeOfContainer", entry.getKey()); // Add the type of container
//
//	     
//	        finalCountsMap.put(entry.getKey(), counts);
//	        
//	        overallCount20 += count20;
//	        overallCount40 += count40;
//	        overallTues +=totalTues;
//	        overallTotal+=totalCount;
//	    }
//	    
//	    Map<String, Object> totalCounts = new HashMap<>();
//	    totalCounts.put("20", overallCount20);
//	    totalCounts.put("40", overallCount40);
//	    totalCounts.put("Tues", overallTues); 
//	    totalCounts.put("total", overallTotal);
//	    finalCountsMap.put("Loaded Total ", totalCounts);
//	
//	    return ResponseEntity.ok(finalCountsMap);
//
//	}


	public ResponseEntity<Map<String, Map<String, Object>>> getDataForLoadedInventoryReport(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    List<Object[]> loadedInventory = commonReportRepo.getLoadedInventoryData(companyId, branchId, endDate);

	    // Initialize a map to store counts by container type
	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();

	    List<String> containerTypessss = Arrays.asList("General", "Manual", "Hazardous", "ODC", "Reefer");

	    if (loadedInventory != null) {
	        // Iterate over each containerType in containerTypessss
	        for (String containerType : containerTypessss) {
	            // Initialize counts for each containerType in the map
	            Map<String, Object> containerTypeCounts = new HashMap<>();
	            containerTypeCounts.put("20", 0);
	            containerTypeCounts.put("40", 0);
	            containerTypeCounts.put("total", 0);
	            containerTypeCounts.put("Tues", 0);

	            // Iterate over each row in the loaded inventory
	            for (Object[] row : loadedInventory) {
	                String containerSize = (String) row[1]; // Container size (20, 40, etc.)
	                String currentContainerType = (String) row[4]; // Container type from row
	                
	                String type = (String) row[2]; // Container size (20, 40, etc.)
	                String status = (String) row[3]; // Container type from row

	                
	    

	                
		            if (currentContainerType == null || currentContainerType.isEmpty() || "General".equals(currentContainerType)) {
		            	currentContainerType = "General";
	            }
		            
//		            if ("FR".equalsIgnoreCase(type) || "Y".equalsIgnoreCase(status)) {
//		                currentContainerType = "ODC";
//		            } else if ("ODC".equalsIgnoreCase(currentContainerType)) {
//		                currentContainerType = "ODC";
//		            }
	                
	                // If the current container type from row matches the type we are iterating on
	                if (containerType.equals(currentContainerType)) {
	                    // Handle 20ft containers
	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        int currentCount20 = (int) containerTypeCounts.get("20");
	                        containerTypeCounts.put("20", currentCount20 + 1);
	                    } 
	                    // Handle 40ft containers
	                    else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        int currentCount40 = (int) containerTypeCounts.get("40");
	                        containerTypeCounts.put("40", currentCount40 + 1);
	                    }
	                }
	            }

	            // After processing all rows for this containerType, calculate totals
	            int count20 = (int) containerTypeCounts.get("20");
	            int count40 = (int) containerTypeCounts.get("40");

	            // Total count and Tuesday special count (Tues = count40 * 2 + count20)
	            int totalCount = count20 + count40;
	            int totalTues = (count40 * 2) + count20;

	            // Update the map with the final counts for this containerType
	            containerTypeCounts.put("total", totalCount);
	            containerTypeCounts.put("Tues", totalTues);

	            // Add the counts to the final map
	            containerCountsByType.put(containerType, containerTypeCounts);
	        }
	    }

	    // Prepare the final result with overall totals
	    Map<String, Map<String, Object>> finalCountsMap = new HashMap<>();
	    int overallCount20 = 0;
	    int overallCount40 = 0;
	    int overallTues = 0;
	    int overallTotal = 0;

	    // Calculate totals for each container type
	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
	        Map<String, Object> counts = entry.getValue();

	        // Get the counts for 20ft and 40ft containers
	        int count20 = (int) counts.get("20");
	        int count40 = (int) counts.get("40");

	        // Calculate the total and special Tuesday count
	        int totalCount = count20 + count40;
	        int totalTues = (count40 * 2) + count20;

	        // Add the total count, Tuesday count, and container type to the current container's map
	        counts.put("total", totalCount);
	        counts.put("Tues", totalTues);
	        counts.put("typeOfContainer", entry.getKey()); // Add the type of container

	        // Update overall counts
	        overallCount20 += count20;
	        overallCount40 += count40;
	        overallTues += totalTues;
	        overallTotal += totalCount;

	        finalCountsMap.put(entry.getKey(), counts);
	    }

	    // Add the grand total for all container types
	    Map<String, Object> totalCounts = new HashMap<>();
	    totalCounts.put("20", overallCount20);
	    totalCounts.put("40", overallCount40);
	    totalCounts.put("Tues", overallTues);
	    totalCounts.put("total", overallTotal);
	    finalCountsMap.put("Total", totalCounts);

	    return ResponseEntity.ok(finalCountsMap);
	}


	
	
	
	
	
	
	public ResponseEntity<Map<String, Map<String, Object>>> getMTYData(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	 
	    List<Object[]> etyInventory = commonReportRepo.getInventoryETYReport(companyId, branchId, endDate);
	    
	    List<Object[]> exportInventory = commonReportRepo.getInventoryExportReport(companyId, branchId, endDate);
	    
	    Map<String, Object> mty = new HashMap<>();
	    int countMty20 = 0;
	    int countMty40 = 0;

	    if (etyInventory != null) {
	        for (Object[] row : etyInventory) {
	          
	            	System.out.println("row :"+row);
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countMty20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countMty40++;
	                }
	                
	                mty.put(countCategory, (Integer) mty.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountMty = (countMty40 * 2) + countMty20;
	    int totalCountMty =countMty40 + countMty20;
	    
	    mty.put("total", totalCountMty);
	    mty.put("Tues", tuesCountMty);
	    mty.put("name", "Import Empty Inventory");
	    
	    
	    
	    Map<String, Object> exp = new HashMap<>();
	    int countExp20 = 0;
	    int countExp40 = 0;

	    if (exportInventory != null) {
	        for (Object[] row : exportInventory) {
	          
	            	System.out.println("row :"+row);
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countExp20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countExp40++;
	                }
	                
	                exp.put(countCategory, (Integer) exp.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountExp = (countExp40 * 2) + countExp20;
	    int totalCountExp =countExp40 + countExp20;
	    
	    exp.put("total", totalCountExp);
	    exp.put("Tues", tuesCountExp);
	    exp.put("name", "Export Empty Inventory");

	    Map<String, Map<String, Object>> result = new HashMap<>();
	  
	    result.put("IMPORT", mty);
	    
	    result.put("EXPORT", exp);
	    

	    return ResponseEntity.ok(result);
	}

	private String getContainerSizeCategory(String containerSize) {
		if ("20".equals(containerSize) || "22".equals(containerSize)) {
			return "20";
		} else if ("40".equals(containerSize) || "45".equals(containerSize)) {
			return "40";
		}
		return "Other"; // Return a default category for other sizes if needed
	}
	
	
	
	
	
	
	
	
	
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForDeliveryReport(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
	    List<Object[]> joGateIn = commonReportRepo.findLoadingJO(companyId, branchId, startDate, endDate);
	    List<Object[]> fclDestuff = commonReportRepo.findDistinctFCLContainers(companyId, branchId, startDate, endDate);
	    List<Object[]> fclLoaded = commonReportRepo.findDistinctFCLGateOutContainers(companyId, branchId, startDate, endDate);
	    List<Object[]> lclDestuff = commonReportRepo.findDistinctLCLContainers(companyId, branchId, startDate, endDate);
	    List<Object[]> emptyOutt = commonReportRepo.findDistinctMTYContainers(companyId, branchId, startDate, endDate);
	    List<Object[]> lclDelivery = commonReportRepo.findLCLContainerDetailsWithGateOutId(companyId, branchId, startDate, endDate);



	    System.out.println("lclDestuff :"+lclDestuff.size());
	    Map<String, Object> containerCounts = new HashMap<>();
	    int count20 = 0;
	    int count40 = 0;
	    int total = 0;

	    // Process job order data
	    if (joGateIn != null) {
	        for (Object[] row : joGateIn) {
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                count20++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                count40++;
	            }
	            containerCounts.put(countCategory, (Integer) containerCounts.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCount = (count40 * 2) + count20;
	    int totalCount = count40 + count20;
	    containerCounts.put("total", totalCount);
	    containerCounts.put("Tues", tuesCount);
	    containerCounts.put("name", "LOADING JO");
	   

	    // Initialize container counts for seal cutting
	    Map<String, Object> containerCountsSealCutting = new HashMap<>();
	    int countSeal20 = 0;
	    int countSeal40 = 0;

	    // Process seal cutting data (corrected loop)
	    if (fclDestuff != null) {
	        for (Object[] row : fclDestuff) { // Corrected: use sealCutting instead of joGateIn
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                countSeal20++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                countSeal40++;
	            }
	            containerCountsSealCutting.put(countCategory, (Integer) containerCountsSealCutting.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountSeal = (countSeal40 * 2) + countSeal20;
	    int totalCountSeal =countSeal40 + countSeal20;
	    containerCountsSealCutting.put("total", totalCountSeal);
	    containerCountsSealCutting.put("Tues", tuesCountSeal);
	    containerCountsSealCutting.put("name", "FCL DESTUFF");
	    
	    
	    Map<String, Object> containerCountsImpGateIn = new HashMap<>();
	    int countIn20 = 0;
	    int countIn40 = 0;

	    // Process import gate-in data
	    if (fclLoaded != null) {
	        for (Object[] row : fclLoaded) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                    countIn20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                    countIn40++;
	                }
	                containerCountsImpGateIn.put(countCategory, (Integer) containerCountsImpGateIn.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountIn = (countIn40 * 2) + countIn20;
	    int totalCountIn =countIn40 + countIn20;
	    containerCountsImpGateIn.put("total", totalCountIn);
	    containerCountsImpGateIn.put("Tues", tuesCountIn);
	    containerCountsImpGateIn.put("name", "FCL LOADED");
	    
	    
	    Map<String, Object> importCutsomesData = new HashMap<>();
	    int countCus20 = 0;
	    int countCus40 = 0;

	    if (lclDestuff != null) {
	        for (Object[] row : lclDestuff) {
	          
	            	
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countCus20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countCus40++;
	                }
	                
	                importCutsomesData.put(countCategory, (Integer) importCutsomesData.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountCus = (countCus40 * 2) + countCus20;
	    int totalCountCus =countCus40 + countCus20;
	    importCutsomesData.put("total", totalCountCus);
	    importCutsomesData.put("Tues", tuesCountCus);
	    importCutsomesData.put("name", "LCL DESTUFF");
	    
	    
	    
	    
	    
	    Map<String, Object> MTYOUT = new HashMap<>();
	    int countMTY20 = 0;
	    int countMTY40 = 0;

	    if (emptyOutt != null) {
	        for (Object[] row : emptyOutt) {
	          
	            	
	            	
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countMTY20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countMTY40++;
	                }
	                
	                MTYOUT.put(countCategory, (Integer) MTYOUT.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountMTY = (countMTY40 * 2) + countMTY20;
	    int totalCountMTY =countMTY40 + countMTY20;
	    
	    
	   
	    MTYOUT.put("total", totalCountMTY);
	    MTYOUT.put("Tues", tuesCountMTY);
	    MTYOUT.put("name", "EMPTY OUT");
	    
	    Map<String, Object> lclDeliveryMap = new HashMap<>();
	    BigDecimal totalQty = BigDecimal.ZERO;
	    BigDecimal totalWeight = BigDecimal.ZERO;

	    if (lclDelivery != null) {
	        for (Object[] row : lclDelivery) {
	            
	            
//	            BigDecimal qty = (BigDecimal) row[5]; // Assuming qty is at index [5]
//	            BigDecimal weight = (BigDecimal) row[6]; // Assuming weight is at index [6]
	        	
	        	BigDecimal qty = row[5] != null ? (BigDecimal) row[5] : BigDecimal.ZERO;
	            BigDecimal weight = row[6] != null ? (BigDecimal) row[6] : BigDecimal.ZERO;


	            // Use the add() method to add the values
	            totalQty = totalQty.add(qty);
	            totalWeight = totalWeight.add(weight);
	        }
	    }


	    lclDeliveryMap.put("20", "QTY");
	    lclDeliveryMap.put("40", totalQty);
	    lclDeliveryMap.put("total", "WEIGHT");
	    lclDeliveryMap.put("Tues", totalWeight);
	    lclDeliveryMap.put("name", "LCL DELIVERED");
	    
	    
	    
	    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    result.put("Loading_JO", containerCounts);
	    result.put("FCL_Destuff", containerCountsSealCutting);
	    result.put("FCL_Loaded", containerCountsImpGateIn);
	    result.put("LCL_Destuff", importCutsomesData);
	    result.put("MTYOUT", MTYOUT);
	    result.put("TlclDeliveryMap", lclDeliveryMap);

	    return ResponseEntity.ok(result);
	}
	
	
	
	
	
	
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForExportSection(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
	    List<Object[]> emptyGateIn = commonReportRepo.findExportEMptyContainerGateIn(companyId, branchId, startDate, endDate);
	    List<Object[]> exportBuffer = commonReportRepo.exportBufferGateIn(companyId, branchId, startDate, endDate);
	    List<Object[]> emptyMovementOut = commonReportRepo.exportEmptyMovementOut(companyId, branchId, startDate, endDate);
	    List<Object[]> stuffTally = commonReportRepo.findStuffTallyContainers(companyId, branchId, startDate, endDate);
	    List<Object[]> exportFcl = commonReportRepo.exportFcl(companyId, branchId, startDate, endDate);
	    List<Object[]> exportLDD = commonReportRepo.findExpLDDInventoryContainers(companyId, branchId, endDate);
	    
	    
	    Map<String, Object> containerCounts = new HashMap<>();
	    int count20 = 0;
	    int count40 = 0;
	    int total = 0;

	    // Process job order data
	    if (emptyGateIn != null) {
	        for (Object[] row : emptyGateIn) {
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                count20++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                count40++;
	            }
	            containerCounts.put(countCategory, (Integer) containerCounts.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCount = (count40 * 2) + count20;
	    int totalCount = count40 + count20;
	    containerCounts.put("total", totalCount);
	    containerCounts.put("Tues", tuesCount);
	    containerCounts.put("name", "EXPORT EMPTY IN");
	   

	    // Initialize container counts for seal cutting
	    Map<String, Object> exportBufferContainer = new HashMap<>();
	    int countSeal20 = 0;
	    int countSeal40 = 0;

	    // Process seal cutting data (corrected loop)
	    if (exportBuffer != null) {
	        for (Object[] row : exportBuffer) { // Corrected: use sealCutting instead of joGateIn
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                countSeal20++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                countSeal40++;
	            }
	            exportBufferContainer.put(countCategory, (Integer) exportBufferContainer.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountSeal = (countSeal40 * 2) + countSeal20;
	    int totalCountSeal =countSeal40 + countSeal20;
	    exportBufferContainer.put("total", totalCountSeal);
	    exportBufferContainer.put("Tues", tuesCountSeal);
	    exportBufferContainer.put("name", "BUFFER GATE IN");
	    
	    
	    Map<String, Object> emptyMovementOutMap = new HashMap<>();
	    int countIn20 = 0;
	    int countIn40 = 0;

	    // Process import gate-in data
	    if (emptyMovementOut != null) {
	        for (Object[] row : emptyMovementOut) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[3]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                    countIn20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                    countIn40++;
	                }
	                emptyMovementOutMap.put(countCategory, (Integer) emptyMovementOutMap.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountIn = (countIn40 * 2) + countIn20;
	    int totalCountIn =countIn40 + countIn20;
	    emptyMovementOutMap.put("total", totalCountIn);
	    emptyMovementOutMap.put("Tues", tuesCountIn);
	    emptyMovementOutMap.put("name", "EXP MOVEMENT OUT");
	    
	    
	    Map<String, Object> exportStuffTally = new HashMap<>();
	    int countCus20 = 0;
	    int countCus40 = 0;

	    if (stuffTally != null) {
	        for (Object[] row : stuffTally) {
	          
	            	System.out.println("row :"+row);
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countCus20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countCus40++;
	                }
	                
	                exportStuffTally.put(countCategory, (Integer) exportStuffTally.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountCus = (countCus40 * 2) + countCus20;
	    int totalCountCus =countCus40 + countCus20;
	    exportStuffTally.put("total", totalCountCus);
	    exportStuffTally.put("Tues", tuesCountCus);
	    exportStuffTally.put("name", "STUFFING TALLY");
	    
	    
	    
	    
	    
	    Map<String, Object> exportFCLMap = new HashMap<>();
	    int countMTY20 = 0;
	    int countMTY40 = 0;

	    if (exportFcl != null) {
	        for (Object[] row : exportFcl) {
	          
	            	System.out.println("row :"+row);
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countMTY20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countMTY40++;
	                }
	                
	                exportFCLMap.put(countCategory, (Integer) exportFCLMap.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountMTY = (countMTY40 * 2) + countMTY20;
	    int totalCountMTY =countMTY40 + countMTY20;
	    exportFCLMap.put("total", totalCountMTY);
	    exportFCLMap.put("Tues", tuesCountMTY);
	    exportFCLMap.put("name", "EXPORT FCL");
	    
	    
	    
	    Map<String, Object> exportLDDMap = new HashMap<>();
	    int countLDD20 = 0;
	    int countLDD40 = 0;

	    if (exportLDD != null) {
	        for (Object[] row : exportLDD) {
	          
	            	System.out.println("row :"+row);
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countLDD20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countLDD40++;
	                }
	                
	                exportLDDMap.put(countCategory, (Integer) exportLDDMap.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountLDD = (countLDD40 * 2) + countLDD20;
	    int totalCountLDD =countLDD40 + countLDD20;
	    exportLDDMap.put("total", totalCountLDD);
	    exportLDDMap.put("Tues", tuesCountLDD);
	    exportLDDMap.put("name", "EXP LDD PENDENCY");
	    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    result.put("exportEmptyIn", containerCounts);
	    result.put("exportBufferGateIn", exportBufferContainer);
	    result.put("emptyMovementOutMap", emptyMovementOutMap);
	    result.put("LCL_Destuff", exportStuffTally);
	    result.put("exportFcl", exportFCLMap);
	    result.put("exportLDDMap", exportLDDMap);
//	    result.put("exportCarting", exportCarting);

	    return ResponseEntity.ok(result);
	}
	
	
// this is all correct for till tues calculation headers in table th row 
	
//	public ResponseEntity<Map<String, Map<String, Object>>> getDataForPortWisePendency(
//	        String companyId, String branchId,
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {
//
//	 
//		List<Object[]> ports = commonReportRepo.findPorts(companyId, branchId);
//		
////	    List<Object[]> loadedInventory = commonReportRepo.getLoadedInventoryData(companyId, branchId, endDate);
//	    
//	    List<String> portCodes = new ArrayList<>();
//	    if (ports != null) {
//	    for (Object[] port : ports) {
//	        String portCode = (String) port[0]; // Assuming the port code is the first element in the array
//	        portCodes.add(portCode);
//	    }
//	    }
//
//	    // Step 3: Retrieve loaded inventory data for each port
//	    List<Object[]> loadedInventory = new ArrayList<>();
//	    for (String portCode : portCodes) {
//	        List<Object[]> loadedInventoryList = commonReportRepo.findPortContainerDetails(companyId, branchId, endDate, portCode);
//	        loadedInventory.addAll(loadedInventoryList); // Add the result of each call to the final list
//	    }
//
//
//	 /// Initialize a map to store counts by container type
//	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();
//
//	    if (loadedInventory != null) {
//	        for (Object[] row : loadedInventory) {
//	            // Assuming that row[0] is container size and row[2] is container type
//	            String containerSize = (String) row[2]; // Container size (20, 40, etc.)
//	            String containerType = (String) row[0]; // Container type (e.g., General, Custom)
//
//	            // Handle if containerType is null or empty, default to "General"
////	            if (containerType == null || containerType.isEmpty()) {
////	                containerType = "General"; // Default to General if container type is null or empty
////	            }
//
//	            // Initialize the map for this container type if not already present
//	            if (!containerCountsByType.containsKey(containerType)) {
//	                containerCountsByType.put(containerType, new HashMap<>());
//	            }
//
//	            // Get the map for this container type
//	            Map<String, Object> counts = containerCountsByType.get(containerType);
//
//	            // Count based on container size
//	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                // Safely cast and increment count for 20ft containers
//	                int currentCount20 = (int) counts.getOrDefault("20", 0);
//	                counts.put("20", currentCount20 + 1);
//	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                // Safely cast and increment count for 40ft containers
//	                int currentCount40 = (int) counts.getOrDefault("40", 0);
//	                counts.put("40", currentCount40 + 1);
//	            }
//	        }
//	    }
//	   
//
//	    Map<String, Map<String, Object>> finalCountsMap = new HashMap<>();
//
//	    int overallCount20 = 0;
//	    int overallCount40 = 0;
//	    int overallTues = 0;
//	    int overallTotal = 0;
//	    
//	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
//	        Map<String, Object> counts = entry.getValue();
//
//	        // Get the counts for 20ft and 40ft containers
//	        int count20 = (int) counts.getOrDefault("20", 0);
//	        int count40 = (int) counts.getOrDefault("40", 0);
//	        
//	        // Calculate the total and the special Tuesday count
//	        int totalCount = count20 + count40;
//	        int totalTues = (count40 * 2) + count20;
//	        
//	        // Add the total count, Tuesday count, and container type to the current container's map
//	        counts.put("total", totalCount);
//	        counts.put("Tues", totalTues); 
//	        counts.put("name", entry.getKey()); // Add the type of container
//
//	     
//	        finalCountsMap.put(entry.getKey(), counts);
//	        
//	        overallCount20 += count20;
//	        overallCount40 += count40;
//	        overallTues +=totalTues;
//	        overallTotal+=totalCount;
//	    }
//	    
//	    Map<String, Object> totalCounts = new HashMap<>();
//	    totalCounts.put("20", overallCount20);
//	    totalCounts.put("40", overallCount40);
//	    totalCounts.put("Tues", overallTues); 
//	    totalCounts.put("total", overallTotal);
////	    finalCountsMap.put("Loaded Total ", totalCounts);
//	
//	    return ResponseEntity.ok(finalCountsMap);
//
//
//
//	    
////	    return ResponseEntity.ok(result);
//	}
	
	
	
	
	
// this is completed for all except last grand total	

	
//public ResponseEntity<Map<String, Map<String, Object>>> getDataForPortWisePendency(
//	        String companyId, String branchId,
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
//	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {
//
//	    List<Object[]> ports = commonReportRepo.findPorts(companyId, branchId);
//	    List<String> portCodes = new ArrayList<>();
//	    
//	    if (ports != null) {
//	        for (Object[] port : ports) {
//	            String portCode = (String) port[0];
//	            portCodes.add(portCode);
//	        }
//	    }
//
//	    List<Object[]> loadedInventory = new ArrayList<>();
//	    for (String portCode : portCodes) {
//	        List<Object[]> loadedInventoryList = commonReportRepo.findPortContainerDetails(companyId, branchId, endDate, portCode);
//	        loadedInventory.addAll(loadedInventoryList);
//	    }
//
//	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();
//
//	    // Predefine all day categories with zero counts
//	    String[] dayCategories = { "Less than 0", "0-3 days", "3-4 days", "4-5 days", "5-6 days", "6-7 days", "7-8 days", "Greater than 9" };
//
//	    for (Object[] row : loadedInventory) {
//	        String containerSize = (String) row[2];  // Container size (20, 40, etc.)
//	        String containerType = (String) row[0];  // Container type (e.g., General, Custom)
//	        String daysString = (String) row[3];
//	        int days;
//
//	        try {
//	            days = Integer.parseInt(daysString);
//	        } catch (NumberFormatException e) {
//	            days = 0;
//	        }
//
//	        // Determine the day category
//	        String dayCategory;
//	        if (days < 0) {
//	            dayCategory = "Less than 0";
//	        } else if (days >= 0 && days <= 3) {
//	            dayCategory = "0-3 days";
//	        } else if (days > 3 && days <= 4) {
//	            dayCategory = "3-4 days";
//	        } else if (days > 4 && days <= 5) {
//	            dayCategory = "4-5 days";
//	        } else if (days > 5 && days <= 6) {
//	            dayCategory = "5-6 days";
//	        } else if (days > 6 && days <= 7) {
//	            dayCategory = "6-7 days";
//	        } else if (days > 7 && days <= 8) {
//	            dayCategory = "7-8 days";
//	        } else {
//	            dayCategory = "Greater than 9";
//	        }
//
//	        // Initialize the map for this container type if not already present
//	        containerCountsByType.putIfAbsent(containerType, new HashMap<>());
//
//	        // Get the map for this container type
//	        Map<String, Object> counts = containerCountsByType.get(containerType);
//
//	        // Predefine zero counts for all day categories
//	        for (String category : dayCategories) {
//	            counts.putIfAbsent(category, 0);
//	        }
//
//	        // Increment the count for the determined day category
//	        counts.put(dayCategory, (int) counts.get(dayCategory) + 1);
//	    }
//
//	    // Prepare the final result with totals
//	    
//	    int overallCount20 = 0;
//	    int overallCount40 = 0;
//	    int overallTues = 0;
//	    int overallTotal = 0;
//	    
//	    Map<String, Map<String, Object>> finalCountsMap = new HashMap<>();
//
//	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
//	        Map<String, Object> counts = entry.getValue();
//
//	        int total20 = 0;
//	        int total40 = 0;
//
//	        // Calculate overall totals for 20ft and 40ft containers
//	        for (Object[] row : loadedInventory) {
//	            String containerType = (String) row[0];
//	            String containerSize = (String) row[2];
//	            if (entry.getKey().equals(containerType)) {
//	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                    total20++;
//	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                    total40++;
//	                }
//	            }
//	        }
//
//	        int totalCount = total20 + total40;
//	        int totalTues = (total40 * 2) + total20;
//
//	        counts.put("20", total20);
//	        counts.put("40", total40);
//	        counts.put("total", totalCount);
//	        counts.put("Tues", totalTues);
//	        counts.put("name", entry.getKey());
//
//	        finalCountsMap.put(entry.getKey(), counts);
//	        
//	        overallCount20 += total20;
//	        overallCount40 += total40;
//	        overallTues +=totalTues;
//	        overallTotal+=totalCount;
//	    }
//	    
//	    Map<String, Object> totalCounts = new HashMap<>();
//	    totalCounts.put("20", overallCount20);
//	    totalCounts.put("40", overallCount40);
//	    totalCounts.put("Tues", overallTues); 
//	    totalCounts.put("total", overallTotal);
//	    finalCountsMap.put("Pendency Total ", totalCounts);
//
//	    return ResponseEntity.ok(finalCountsMap);
//	}

	
	
	
	
	
	
	
	public ResponseEntity<Map<String, Map<String, Object>>> getDataForPortWisePendency(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    List<Object[]> ports = commonReportRepo.findPorts(companyId, branchId);
	    List<String> portCodes = new ArrayList<>();

	    if (ports != null) {
	        for (Object[] port : ports) {
	            String portCode = (String) port[0];
	            
	            System.out.println("portCode :" +portCode);
	            portCodes.add(portCode);
	        }
	    }

	    List<Object[]> loadedInventory = new ArrayList<>();
	    for (String portCode : portCodes) {
	        List<Object[]> loadedInventoryList = commonReportRepo.findPortContainerDetails(companyId, branchId, endDate, portCode);
	        loadedInventory.addAll(loadedInventoryList);
	    }

	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();

	    // Predefine all day categories with zero counts
	    String[] dayCategories = { "Less than 0", "0-3 days", "3-4 days", "4-5 days", "5-6 days", "6-7 days", "7-8 days", "Greater than 9" };
	    Map<String, Integer> overallDayCategoryCounts = new HashMap<>();
	    for (String category : dayCategories) {
	        overallDayCategoryCounts.put(category, 0);
	    }

	    int overallCount20 = 0;
	    int overallCount40 = 0;
	    int overallTues = 0;
	    int overallTotal = 0;

	    for (Object[] row : loadedInventory) {
	        String containerSize = (String) row[2];  // Container size (20, 40, etc.)
	        String containerType = (String) row[0];  // Container type (e.g., General, Custom)
	        String daysString = (String) row[3];
	        int days;

	        try {
	            days = Integer.parseInt(daysString);
	        } catch (NumberFormatException e) {
	            days = 0;
	        }

	        // Determine the day category
	        String dayCategory;
	        if (days < 0) {
	            dayCategory = "Less than 0";
	        } else if (days >= 0 && days <= 3) {
	            dayCategory = "0-3 days";
	        } else if (days > 3 && days <= 4) {
	            dayCategory = "3-4 days";
	        } else if (days > 4 && days <= 5) {
	            dayCategory = "4-5 days";
	        } else if (days > 5 && days <= 6) {
	            dayCategory = "5-6 days";
	        } else if (days > 6 && days <= 7) {
	            dayCategory = "6-7 days";
	        } else if (days > 7 && days <= 8) {
	            dayCategory = "7-8 days";
	        } else {
	            dayCategory = "Greater than 9";
	        }

	        // Initialize the map for this container type if not already present
	        containerCountsByType.putIfAbsent(containerType, new HashMap<>());

	        // Get the map for this container type
	        Map<String, Object> counts = containerCountsByType.get(containerType);

	        // Predefine zero counts for all day categories
	        for (String category : dayCategories) {
	            counts.putIfAbsent(category, 0);
	        }

	        // Increment the count for the determined day category
	        counts.put(dayCategory, (int) counts.get(dayCategory) + 1);
	        overallDayCategoryCounts.put(dayCategory, overallDayCategoryCounts.get(dayCategory) + 1);

	        // Count container sizes for total
	        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	            counts.put("20", (int) counts.getOrDefault("20", 0) + 1);
	            overallCount20++;
	        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	            counts.put("40", (int) counts.getOrDefault("40", 0) + 1);
	            overallCount40++;
	        }
	    }

//	    Map<String, Map<String, Object>> finalCountsMap = new HashMap<>();
//
//	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
//	        Map<String, Object> counts = entry.getValue();
//
//	        int total20 = (int) counts.getOrDefault("20", 0);
//	        int total40 = (int) counts.getOrDefault("40", 0);
//	        int totalCount = total20 + total40;
//	        int totalTues = (total40 * 2) + total20;
//
//	        counts.put("total", totalCount);
//	        counts.put("Tues", totalTues);
//	        counts.put("name", entry.getKey());
//	        
//	        
//	        
////	        overallCount20 += total20;
////	        overallCount40 += total40;
//	        overallTotal += totalCount;  // Accumulate totalCount here
//	        overallTues += totalTues;    // Accumulate totalTues here
//
//	        finalCountsMap.put(entry.getKey(), counts);
//	        
//	       
//	    }
	    
	    Map<String, Map<String, Object>> finalCountsMap = new HashMap<>();

	    for (Map.Entry<String, Map<String, Object>> entry : containerCountsByType.entrySet()) {
	        Map<String, Object> counts = entry.getValue();

	        int total20 = 0;
	        int total40 = 0;

	        // Calculate overall totals for 20ft and 40ft containers
	        for (Object[] row : loadedInventory) {
	            String containerType = (String) row[0];
	            String containerSize = (String) row[2];
	            if (entry.getKey().equals(containerType)) {
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                    total20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                    total40++;
	                }
	            }
	        }

	        int totalCount = total20 + total40;
	        int totalTues = (total40 * 2) + total20;

	        counts.put("20", total20);
	        counts.put("40", total40);
	        counts.put("total", totalCount);
	        counts.put("Tues", totalTues);
	        counts.put("name", entry.getKey());

	        finalCountsMap.put(entry.getKey(), counts);
	        
//	        overallCount20 += total20;
//	        overallCount40 += total40;
	        overallTues +=totalTues;
	        overallTotal+=totalCount;
	    }
	    // Add overall totals for day categories and container sizes
	    Map<String, Object> totalCounts = new HashMap<>();
	    for (String category : dayCategories) {
	        totalCounts.put(category, overallDayCategoryCounts.get(category));
	    }
	    
	    totalCounts.put("20", overallCount20);
	    totalCounts.put("40", overallCount40);
	    totalCounts.put("total", overallTotal);
	    totalCounts.put("Tues", overallTues);
	    totalCounts.put("name", "Pendency Total");

	    finalCountsMap.put("Pendency Total", totalCounts);

	    return ResponseEntity.ok(finalCountsMap);
	}

	
	
	public byte[] createExcelReportOfPortWisePendency(String companyId,
		    String branchId, String username, String type, String companyname,
		    String branchname,
		    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
	        String igmNo,
	        String itemNo,
	        String sl,
	        String acc,
	        String cha,
	        String selectedReport
			) throws DocumentException 
	{
		
        if ("Pendency Total".equals(selectedReport))
        {
        	 try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
 		    {
 		    	System.out.println("startDate_______________________"+startDate);
 		    	
 		    	
 		    	System.out.println("endDate_______________________"+endDate);
 		    	
 		    	
 		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

 		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
 		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

 		        Calendar cal = Calendar.getInstance();

 		     // Set startDate to 00:00 if the time component is not set
 		     if (startDate != null) {
 		         cal.setTime(startDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 0);
 		         cal.set(Calendar.MINUTE, 0);
 		         cal.set(Calendar.SECOND, 0);
 		         cal.set(Calendar.MILLISECOND, 0);
 		         startDate = cal.getTime();
 		     }

 		     // Set endDate to 23:59 if the time component is not set
 		     if (endDate != null) {
 		         cal.setTime(endDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 23);
 		         cal.set(Calendar.MINUTE, 59);
 		         cal.set(Calendar.SECOND, 59);
 		         cal.set(Calendar.MILLISECOND, 999);
 		         endDate = cal.getTime();
 		     }
 		     
 		        
 		        String user = username;
 		        String companyName = companyname;
 		        String branchName = branchname;

 		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
 		        Branch branchAddress = branchRepo.findByBranchId(branchId);

 		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
 		                + companyAddress.getAddress_3() + companyAddress.getCity();

 		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
 		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

 		        double widthFactor = 1;
 		        


 		        
 		        List<Object[]> importDetails = commonReportRepo.findAllPortWisePendencyContainerDetails(companyId, branchId, endDate);
 		        
 		        
 		        Sheet sheet = workbook.createSheet("Port Wise Pendency Report");

 		        CellStyle dateCellStyle = workbook.createCellStyle();
 		        CreationHelper createHelper = workbook.getCreationHelper();
 		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
 		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
 		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
 		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle.setBorderTop(BorderStyle.THIN);
 		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
 		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle.setBorderRight(BorderStyle.THIN);
 		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        
 		        CellStyle dateCellStyle1 = workbook.createCellStyle();
 		        CreationHelper createHelper1 = workbook.getCreationHelper();
 		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
 		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
 		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
 		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
 		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
 		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
 		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
 		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        
 		        
 		        // Create numeric format for Excel
 		        CellStyle numberCellStyle = workbook.createCellStyle();
 		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
 		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
 		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        numberCellStyle.setBorderTop(BorderStyle.THIN);
 		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
 		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        numberCellStyle.setBorderRight(BorderStyle.THIN);
 		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

 		        

 		        String[] columnsHeader = {
 		        	    "SR NO",                // This corresponds to "SR NO"
 		        	    "Container No",         // This corresponds to "Container No"
 		        	    "Size",                 // This corresponds to "Size"
 		        	    "Type",                 // This corresponds to "Type"
 		        	    "CtrMode",              // This corresponds to "CtrMode"
 		        	    "Port",                 // This corresponds to "Port"
 		        	    "Sysdate",              // This corresponds to "Sysdate"
 		        	    "Vsl Berthing Date",    // This corresponds to "Vsl Berthing Date"
 		        	    "Days",                 // This corresponds to "Days"
 		        	    "IgmSealNo",            // This corresponds to "Igm Seal No"
 		        	    "Agent",                // This corresponds to "Agent"
 		        	    "Vessel",               // This corresponds to "Vessel"
 		        	    "VCN",                  // This corresponds to "VCN"
 		        	    "Igm",                  // This corresponds to "Igm"
 		        	    "Item",                 // This corresponds to "Item"
 		        	    "CtrWt",                // This corresponds to "CtrWt"
 		        	    "Importer",             // This corresponds to "Importer"
 		        	    "Scancode"              // This corresponds to "Scancode"
 		        	};


 	
 		        // Add Company Name (Centered)
 		        Row companyRow = sheet.createRow(0);
 		        Cell companyCell = companyRow.createCell(0);
 		        companyCell.setCellValue(companyName);

 		        // Create and style for centering
 		        CellStyle companyStyle = workbook.createCellStyle();
 		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
 		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font companyFont = workbook.createFont();
 		        companyFont.setBold(true);
 		        companyFont.setFontHeightInPoints((short)18);
 		        companyStyle.setFont(companyFont);
 		        companyCell.setCellStyle(companyStyle);
 		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

 		        // Add Branch Address (Centered)
 		        Row branchRow = sheet.createRow(1);
 		        Cell branchCell = branchRow.createCell(0);
 		        branchCell.setCellValue(branchAdd);
 		        CellStyle branchStyle = workbook.createCellStyle();
 		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
 		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font branchFont = workbook.createFont();
 		        branchFont.setFontHeightInPoints((short) 12);
 		        branchStyle.setFont(branchFont);
 		        branchCell.setCellStyle(branchStyle);
 		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

 		        
 		        Row branchRow1 = sheet.createRow(2);
 		        Cell branchCell1 = branchRow1.createCell(0);
// 		        branchCell1.setCellValue(branchAdd);
 		        CellStyle branchStyle1 = workbook.createCellStyle();
 		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
 		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font branchFont1 = workbook.createFont();
 		        branchFont1.setFontHeightInPoints((short) 12);
 		        branchStyle1.setFont(branchFont1);
 		        branchCell1.setCellStyle(branchStyle1);
 		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
 	
 		        // Add Report Title "Bond cargo Inventory Report"
 		        Row reportTitleRow = sheet.createRow(3);
 		        Cell reportTitleCell = reportTitleRow.createCell(0);
 		        reportTitleCell.setCellValue("Port Wise Pendency Report");

 		        // Set alignment and merge cells for the heading
 		        CellStyle reportTitleStyle = workbook.createCellStyle();
 		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
 		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font reportTitleFont = workbook.createFont();
 		        reportTitleFont.setBold(true);
 		        reportTitleFont.setFontHeightInPoints((short) 16);
 		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
 		        reportTitleStyle.setFont(reportTitleFont);
 		        reportTitleCell.setCellStyle(reportTitleStyle);
 		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
 		        		        
 		        Row reportTitleRow1 = sheet.createRow(4);
 		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
// 		        if(formattedStartDate.equals("N/A"))
// 		        {
 		        	 reportTitleCell1.setCellValue("Port Wise Pendency Report As On Date : " + formattedEndDate);
// 		        }
// 		        else 
// 		        {
// 		        	 reportTitleCell1.setCellValue("Port Wise Pendency Report From : " + formattedStartDate + " to " + formattedEndDate);
// 		        }
 		       

 		        // Create and set up the CellStyle for the report title
 		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
 		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

 		        // Create the font and set its properties
 		        Font reportTitleFont1 = workbook.createFont();
 		        reportTitleFont1.setBold(true); // Make font bold
 		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

 		        // Apply the font to the CellStyle
 		        reportTitleStyle1.setFont(reportTitleFont1);

 		        // Set the style to the cell
 		        reportTitleCell1.setCellStyle(reportTitleStyle1);
 		     // Create a font and set its properties
 		    

 		        
 		        // Set headers after the title
 		        int headerRowIndex = 6; // Adjusted for the title rows above
 		        Row headerRow = sheet.createRow(headerRowIndex);
 		        
 		        CellStyle borderStyle = workbook.createCellStyle();
 		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
 		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        borderStyle.setBorderBottom(BorderStyle.THIN);
 		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        borderStyle.setBorderTop(BorderStyle.THIN);
 		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        borderStyle.setBorderLeft(BorderStyle.THIN);
 		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        borderStyle.setBorderRight(BorderStyle.THIN);
 		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        Font boldFont = workbook.createFont();
 		        boldFont.setBold(true);
 		        boldFont.setFontHeightInPoints((short) 16);

 		        for (int i = 0; i < columnsHeader.length; i++) 
 		        {
 		            Cell cell = headerRow.createCell(i);
 		            cell.setCellValue(columnsHeader[i]);

 		            CellStyle headerStyle = workbook.createCellStyle();
 		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		            
 		
 			        
 		            Font headerFont = workbook.createFont();
 		            headerFont.setBold(true);
 		            headerFont.setFontHeightInPoints((short) 11);

 		            headerStyle.setFont(headerFont);
 		            headerStyle.setBorderBottom(BorderStyle.THIN);
 		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderTop(BorderStyle.THIN);
 		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderLeft(BorderStyle.THIN);
 		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderRight(BorderStyle.THIN);
 		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
 		            
 		            
 		            headerFont.setColor(IndexedColors.WHITE.getIndex());
 					   
 			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		            cell.setCellStyle(headerStyle);
 		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
 		            sheet.setColumnWidth(i, headerWidth);
 		        }
 		        
 		        

 		        // Populate data rows
 		        int rowNum = headerRowIndex + 1;


 		        BigDecimal totalInbondPkgs = BigDecimal.ZERO;
 		        BigDecimal totalInbondWeight = BigDecimal.ZERO;
 		        BigDecimal totalInbondAssetValue = BigDecimal.ZERO;
 		        BigDecimal totalInbondDutyValue = BigDecimal.ZERO;
 		        BigDecimal totalBalPkgs = BigDecimal.ZERO;
 		        BigDecimal totalBalWeight = BigDecimal.ZERO;
 		        BigDecimal totalBalAssetValue = BigDecimal.ZERO;
 		        BigDecimal totalBalDutyValue = BigDecimal.ZERO;
 		        BigDecimal totalAreaBalance = BigDecimal.ZERO;
 		        
 		        
 		        int serialNo = 1; // Initialize serial number counter
 		        for (Object[] resultData1 : importDetails) {
 		            Row dataRow = sheet.createRow(rowNum++);
 		    
 		            int cellNum = 0;

 		            for (int i = 0; i < columnsHeader.length; i++) {
 		                Cell cell = dataRow.createCell(i);
 		                cell.setCellStyle(borderStyle);

 		                // Switch case to handle each column header

 		                switch (columnsHeader[i]) {
 		                case "SR NO":
 		                    cell.setCellValue(serialNo++); // Serial Number increment
 		                    break;

 		                case "Container No":
 		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
 		                    break;

 		                case "Size":
 		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
 		                    break;

 		                case "Type":
 		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
 		                    break;

 		                case "CtrMode":
 		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
 		                    break;

 		                case "Port":
 		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
 		                    break;

 		                case "Sysdate":
 		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
 		                    break;

 		                case "Vsl Berthing Date":
 		                    if (resultData1[6] != null) {
 		                        cell.setCellValue(resultData1[6].toString());
 		                        cell.setCellStyle(dateCellStyle);
 		                    } else {
 		                        cell.setBlank();
 		                        cell.setCellStyle(dateCellStyle);
 		                    }
 		                    break;

 		                case "Days":
 		                	  cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
 		                    break;

 		                case "IgmSealNo":
 		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
 		                    break;

 		                case "Agent":
 		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
 		                    break;

 		                case "Vessel":
 		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
 		                    break;

 		                case "VCN":
 		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
 		                    break;

 		                case "Igm":
 		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
 		                    break;

 		                case "Item":
 		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
 		                    break;

 		                case "CtrWt":
 		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
 		                    break;

 		                case "Importer":
 		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
 		                    break;

 		                case "Scancode":
 		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
 		                    break;

 		                default:
 		                    cell.setCellValue(""); // Handle undefined columns
 		                    break;
 		            }

 		            }
 		        }
 		       
 		        // Create a font for bold text
 		     /// Create a row for spacing before totals
 		     // Create a row for spacing before totals
 		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Shipping Agent Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Create a row for totals
// 		        Row totalRow = sheet.createRow(rowNum+ 2);

 		        // Header row for summary
 		        String[] columnsHeaderSummery = {
 		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		        };

 		        // Create a map to store container count for each agent
 		        Map<String, int[]> agentContainerCount = new HashMap<>();

 		        // Iterate over importDetails and count the container sizes for each shipping agent
 		        importDetails.forEach(i -> {
 		            String agent = i[8] != null ? i[8].toString() : "Unknown"; // Shipping Agent at index 10
 		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		            // Initialize counts for this agent if not already present
 		            if (!agentContainerCount.containsKey(agent)) {
 		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		            }

 		            // Increment the count based on container size
 		            switch (containerSize) {
 		                case "20":
 		                    agentContainerCount.get(agent)[0]++;
 		                    break;
 		                case "22":
 		                    agentContainerCount.get(agent)[1]++;
 		                    break;
 		                case "40":
 		                    agentContainerCount.get(agent)[2]++;
 		                    break;
 		                case "45":
 		                    agentContainerCount.get(agent)[3]++;
 		                    break;
 		                default:
 		                    break;
 		            }
 		        });

 		        // Apply header row styling and set values
 		        Row headerRow1 = sheet.createRow(rowNum++);
 		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		            Cell cell = headerRow1.createCell(i);
 		            cell.setCellValue(columnsHeaderSummery[i]);

 		            // Set cell style for header
 		            CellStyle headerStyle = workbook.createCellStyle();
 		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		            Font headerFont = workbook.createFont();
 		            headerFont.setBold(true);
 		            headerFont.setFontHeightInPoints((short) 11);
 		            headerStyle.setFont(headerFont);
 		            headerStyle.setBorderBottom(BorderStyle.THIN);
 		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderTop(BorderStyle.THIN);
 		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderLeft(BorderStyle.THIN);
 		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderRight(BorderStyle.THIN);
 		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		            headerFont.setColor(IndexedColors.WHITE.getIndex());
 		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		            cell.setCellStyle(headerStyle);
 		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
 		            sheet.setColumnWidth(i, headerWidth);
 		        }

 		        // Iterate over the map to create rows for each shipping agent and their container count
 		        int srNo = 1;
 		        
 		        String shippingAgent =null;
 		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
 		             shippingAgent = entry.getKey();
 		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		            // Create a new row for this shipping agent
 		            Row row = sheet.createRow(rowNum++);
 		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
 		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
 		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
// 		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
 		            
 		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		        }


 		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
 		        for (int[] sizes : agentContainerCount.values()) {
 		            totalSize20 += sizes[0];
 		            totalSize22 += sizes[1];
 		            totalSize40 += sizes[2];
 		            totalSize45 += sizes[3];
 		        }

 		     // Create a row for the totals
 		        Row totalRow = sheet.createRow(rowNum++);

 		        // Set the values for the total row
 		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
 		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
 		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
 		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
 		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
 		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers

 		        // Calculate and set the total TUE'S value
 		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
 		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value

 		     // Apply the total row style (light green background and bold font)

 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary Vessel Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		     String[] columnsHeaderVesselSummery = {
 		         "Sr No", "Vessel", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		     };

 		     // Create a map to store container count for each vessel
 		     Map<String, int[]> vesselContainerCount = new HashMap<>();

 		     // Iterate over importDetails and count the container sizes for each vessel
 		     importDetails.forEach(i -> {
 		         String vessel = i[9] != null ? i[9].toString() : "Unknown"; // Vessel at index 10
 		         String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		         // Initialize counts for this vessel if not already present
 		         if (!vesselContainerCount.containsKey(vessel)) {
 		             vesselContainerCount.put(vessel, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		         }

 		         // Increment the count based on container size
 		         switch (containerSize) {
 		             case "20":
 		                 vesselContainerCount.get(vessel)[0]++;
 		                 break;
 		             case "22":
 		                 vesselContainerCount.get(vessel)[1]++;
 		                 break;
 		             case "40":
 		                 vesselContainerCount.get(vessel)[2]++;
 		                 break;
 		             case "45":
 		                 vesselContainerCount.get(vessel)[3]++;
 		                 break;
 		             default:
 		                 break;
 		         }
 		     });

 		     // Apply header row styling and set values for Vessel Wise Summary
 		     Row headerRowVessel = sheet.createRow(rowNum++);
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = headerRowVessel.createCell(i);
 		         cell.setCellValue(columnsHeaderVesselSummery[i]);

 		         // Set cell style for header
 		         CellStyle headerStyle = workbook.createCellStyle();
 		         headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		         headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		         Font headerFont = workbook.createFont();
 		         headerFont.setBold(true);
 		         headerFont.setFontHeightInPoints((short) 11);
 		         headerStyle.setFont(headerFont);
 		         headerStyle.setBorderBottom(BorderStyle.THIN);
 		         headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		         headerStyle.setBorderTop(BorderStyle.THIN);
 		         headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		         headerStyle.setBorderLeft(BorderStyle.THIN);
 		         headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		         headerStyle.setBorderRight(BorderStyle.THIN);
 		         headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		         headerFont.setColor(IndexedColors.WHITE.getIndex());
 		         headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		         headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		         cell.setCellStyle(headerStyle);
 		         int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		         sheet.setColumnWidth(i, headerWidth);
 		     }

 		     // Iterate over the map to create rows for each vessel and their container count
 		     int srNoVessel = 1;
 		     for (Map.Entry<String, int[]> entry : vesselContainerCount.entrySet()) {
 		         String vessel = entry.getKey();
 		         int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		         // Create a new row for this vessel
 		         Row row = sheet.createRow(rowNum++);
 		         row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		         row.createCell(1).setCellValue(vessel); // Vessel Name
 		         row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		         row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		         row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		         row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		         row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers

 		         // Calculate and set the TUE'S value
 		         int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		         row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		     }

 		     // Calculate totals for all vessel data
 		     int totalVesselSize20 = 0, totalVesselSize22 = 0, totalVesselSize40 = 0, totalVesselSize45 = 0;
 		     for (int[] sizes : vesselContainerCount.values()) {
 		         totalVesselSize20 += sizes[0];
 		         totalVesselSize22 += sizes[1];
 		         totalVesselSize40 += sizes[2];
 		         totalVesselSize45 += sizes[3];
 		     }

 		     // Create a row for the totals of Vessel Wise Summary
 		     Row totalVesselRow = sheet.createRow(rowNum++);

 		     // Set the values for the total row
 		     totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
 		     totalVesselRow.createCell(2).setCellValue(totalVesselSize20); // Total Size 20
 		     totalVesselRow.createCell(3).setCellValue(totalVesselSize22); // Total Size 22
 		     totalVesselRow.createCell(4).setCellValue(totalVesselSize40); // Total Size 40
 		     totalVesselRow.createCell(5).setCellValue(totalVesselSize45); // Total Size 45
 		     totalVesselRow.createCell(6).setCellValue(totalVesselSize20 + totalVesselSize22 + totalVesselSize40 + totalVesselSize45); // Total of all containers

 		     // Calculate and set the total TUE'S value for Vessel Wise
 		     int totalVesselTuesValue = (totalVesselSize20 * 1) + (totalVesselSize22 * 1) + (totalVesselSize40 * 2) + (totalVesselSize45 * 2);
 		     totalVesselRow.createCell(7).setCellValue(totalVesselTuesValue); // Total TUE'S value

 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }




 		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
 		        sheet.setColumnWidth(0,  9 * 306); 
 		        sheet.setColumnWidth(1, 18 * 306); 
 		        sheet.setColumnWidth(2, 9 * 306); 
 		        sheet.setColumnWidth(3, 18 * 306); 
 		        
 		        sheet.setColumnWidth(4, 18 * 306); 
 		        sheet.setColumnWidth(5, 18 * 306); 
 		        
 		        sheet.setColumnWidth(6, 18 * 306); 
 		        sheet.setColumnWidth(7, 18 * 306); 
 		        
 		        sheet.setColumnWidth(8, 18 * 306); 
 		        sheet.setColumnWidth(9, 18 * 306); 
 		        sheet.setColumnWidth(10, 45 * 306); // Set width for "Importer" (40 characters wide)
 		        
 		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
 		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
 		        
 		        sheet.setColumnWidth(13,  18 * 306); 
 		        sheet.setColumnWidth(14, 18 * 306); 
 		        sheet.setColumnWidth(15, 18 * 306); 
 		        sheet.setColumnWidth(16, 45 * 306); 

 		        
 		        sheet.setColumnWidth(17, 18 * 306); 
 		        sheet.setColumnWidth(18, 18 * 306); 
 		        sheet.setColumnWidth(19, 18 * 306); 
 		        sheet.setColumnWidth(20,18 * 306); 
 		       
 		        
 		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));
 		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

 		        workbook.write(outputStream);
 		        return outputStream.toByteArray();

 		    }
 		        
 		        catch (IOException e) {
 		        e.printStackTrace();
 		    }
        }
        else 
        {
        	 try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream())
 		    {
 		    	System.out.println("startDate_______________________"+startDate);
 		    	
 		    	
 		    	System.out.println("endDate_______________________"+endDate);
 		    	
 		    	
 		    	SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy HH:mm");

 		        String formattedStartDate = startDate != null ? dateFormatService.format(startDate) : "N/A";
 		        String formattedEndDate = endDate != null ? dateFormatService.format(endDate) : "N/A";

 		        Calendar cal = Calendar.getInstance();

 		     // Set startDate to 00:00 if the time component is not set
 		     if (startDate != null) {
 		         cal.setTime(startDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 0);
 		         cal.set(Calendar.MINUTE, 0);
 		         cal.set(Calendar.SECOND, 0);
 		         cal.set(Calendar.MILLISECOND, 0);
 		         startDate = cal.getTime();
 		     }

 		     // Set endDate to 23:59 if the time component is not set
 		     if (endDate != null) {
 		         cal.setTime(endDate);
 		         cal.set(Calendar.HOUR_OF_DAY, 23);
 		         cal.set(Calendar.MINUTE, 59);
 		         cal.set(Calendar.SECOND, 59);
 		         cal.set(Calendar.MILLISECOND, 999);
 		         endDate = cal.getTime();
 		     }
 		     
 		        
 		        String user = username;
 		        String companyName = companyname;
 		        String branchName = branchname;

 		        Company companyAddress = companyRepo.findByCompany_Id(companyId);
 		        Branch branchAddress = branchRepo.findByBranchId(branchId);

 		        String companyAdd = companyAddress.getAddress_1() + companyAddress.getAddress_2()
 		                + companyAddress.getAddress_3() + companyAddress.getCity();

 		        String branchAdd = branchAddress.getAddress1() + " " + branchAddress.getAddress2() + " "
 		                + branchAddress.getAddress3() + " " + branchAddress.getCity() + " " + branchAddress.getPin();

 		        double widthFactor = 1;
 		        


 		        
 		        List<Object[]> importDetails = commonReportRepo.findPortWisePendencyContainerDetails(companyId, branchId, endDate,selectedReport);
 		        
 		        
 		        Sheet sheet = workbook.createSheet("Port Wise Pendency Report "+selectedReport);

 		        CellStyle dateCellStyle = workbook.createCellStyle();
 		        CreationHelper createHelper = workbook.getCreationHelper();
 		        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MMM/yyyy HH:mm:ss"));
 		        dateCellStyle.setAlignment(HorizontalAlignment.CENTER);
 		        dateCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        dateCellStyle.setBorderBottom(BorderStyle.THIN);
 		        dateCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle.setBorderTop(BorderStyle.THIN);
 		        dateCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle.setBorderLeft(BorderStyle.THIN);
 		        dateCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle.setBorderRight(BorderStyle.THIN);
 		        dateCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        
 		        CellStyle dateCellStyle1 = workbook.createCellStyle();
 		        CreationHelper createHelper1 = workbook.getCreationHelper();
 		        dateCellStyle1.setDataFormat(createHelper1.createDataFormat().getFormat("dd/MMM/yyyy"));
 		        dateCellStyle1.setAlignment(HorizontalAlignment.CENTER);
 		        dateCellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
 		        dateCellStyle1.setBorderBottom(BorderStyle.THIN);
 		        dateCellStyle1.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle1.setBorderTop(BorderStyle.THIN);
 		        dateCellStyle1.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle1.setBorderLeft(BorderStyle.THIN);
 		        dateCellStyle1.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        dateCellStyle1.setBorderRight(BorderStyle.THIN);
 		        dateCellStyle1.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        
 		        
 		        // Create numeric format for Excel
 		        CellStyle numberCellStyle = workbook.createCellStyle();
 		        numberCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
 		        numberCellStyle.setBorderBottom(BorderStyle.THIN);
 		        numberCellStyle.setBottomBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        numberCellStyle.setBorderTop(BorderStyle.THIN);
 		        numberCellStyle.setTopBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        numberCellStyle.setBorderLeft(BorderStyle.THIN);
 		        numberCellStyle.setLeftBorderColor(IndexedColors.DARK_BLUE.getIndex());
 		        numberCellStyle.setBorderRight(BorderStyle.THIN);
 		        numberCellStyle.setRightBorderColor(IndexedColors.DARK_BLUE.getIndex());

 		        

 		        String[] columnsHeader = {
 		        	    "SR NO",                // This corresponds to "SR NO"
 		        	    "Container No",         // This corresponds to "Container No"
 		        	    "Size",                 // This corresponds to "Size"
 		        	    "Type",                 // This corresponds to "Type"
 		        	    "CtrMode",              // This corresponds to "CtrMode"
 		        	    "Port",                 // This corresponds to "Port"
 		        	    "Sysdate",              // This corresponds to "Sysdate"
 		        	    "Vsl Berthing Date",    // This corresponds to "Vsl Berthing Date"
 		        	    "Days",                 // This corresponds to "Days"
 		        	    "IgmSealNo",            // This corresponds to "Igm Seal No"
 		        	    "Agent",                // This corresponds to "Agent"
 		        	    "Vessel",               // This corresponds to "Vessel"
 		        	    "VCN",                  // This corresponds to "VCN"
 		        	    "Igm",                  // This corresponds to "Igm"
 		        	    "Item",                 // This corresponds to "Item"
 		        	    "CtrWt",                // This corresponds to "CtrWt"
 		        	    "Importer",             // This corresponds to "Importer"
 		        	    "Scancode"              // This corresponds to "Scancode"
 		        	};


 	
 		        // Add Company Name (Centered)
 		        Row companyRow = sheet.createRow(0);
 		        Cell companyCell = companyRow.createCell(0);
 		        companyCell.setCellValue(companyName);

 		        // Create and style for centering
 		        CellStyle companyStyle = workbook.createCellStyle();
 		        companyStyle.setAlignment(HorizontalAlignment.CENTER);
 		        companyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font companyFont = workbook.createFont();
 		        companyFont.setBold(true);
 		        companyFont.setFontHeightInPoints((short)18);
 		        companyStyle.setFont(companyFont);
 		        companyCell.setCellStyle(companyStyle);
 		        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

 		        // Add Branch Address (Centered)
 		        Row branchRow = sheet.createRow(1);
 		        Cell branchCell = branchRow.createCell(0);
 		        branchCell.setCellValue(branchAdd);
 		        CellStyle branchStyle = workbook.createCellStyle();
 		        branchStyle.setAlignment(HorizontalAlignment.CENTER);
 		        branchStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font branchFont = workbook.createFont();
 		        branchFont.setFontHeightInPoints((short) 12);
 		        branchStyle.setFont(branchFont);
 		        branchCell.setCellStyle(branchStyle);
 		        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

 		        
 		        Row branchRow1 = sheet.createRow(2);
 		        Cell branchCell1 = branchRow1.createCell(0);
// 		        branchCell1.setCellValue(branchAdd);
 		        CellStyle branchStyle1 = workbook.createCellStyle();
 		        branchStyle1.setAlignment(HorizontalAlignment.CENTER);
 		        branchStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font branchFont1 = workbook.createFont();
 		        branchFont1.setFontHeightInPoints((short) 12);
 		        branchStyle1.setFont(branchFont1);
 		        branchCell1.setCellStyle(branchStyle1);
 		        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 6));
 	
 		        // Add Report Title "Bond cargo Inventory Report"
 		        Row reportTitleRow = sheet.createRow(3);
 		        Cell reportTitleCell = reportTitleRow.createCell(0);
 		        reportTitleCell.setCellValue("Port Wise Pendency Report "+selectedReport);

 		        // Set alignment and merge cells for the heading
 		        CellStyle reportTitleStyle = workbook.createCellStyle();
 		        reportTitleStyle.setAlignment(HorizontalAlignment.CENTER);
 		        reportTitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        Font reportTitleFont = workbook.createFont();
 		        reportTitleFont.setBold(true);
 		        reportTitleFont.setFontHeightInPoints((short) 16);
 		        reportTitleFont.setColor(IndexedColors.BLACK.getIndex()); // Set font color to red
 		        reportTitleStyle.setFont(reportTitleFont);
 		        reportTitleCell.setCellStyle(reportTitleStyle);
 		        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 6));
 		        		        
 		        Row reportTitleRow1 = sheet.createRow(4);
 		        Cell reportTitleCell1 = reportTitleRow1.createCell(0);
// 		        if(formattedStartDate.equals("N/A"))
// 		        {
 		        	 reportTitleCell1.setCellValue("Port Wise Pendency Report As On Date : " + formattedEndDate);
// 		        }
// 		        else 
// 		        {
// 		        	 reportTitleCell1.setCellValue("Port Wise Pendency Report From : " + formattedStartDate + " to " + formattedEndDate);
// 		        }
 		       

 		        // Create and set up the CellStyle for the report title
 		        CellStyle reportTitleStyle1 = workbook.createCellStyle();
 		        reportTitleStyle1.setAlignment(HorizontalAlignment.LEFT); // Set alignment

 		        // Create the font and set its properties
 		        Font reportTitleFont1 = workbook.createFont();
 		        reportTitleFont1.setBold(true); // Make font bold
 		        reportTitleFont1.setFontHeightInPoints((short) 12); // Set font size

 		        // Apply the font to the CellStyle
 		        reportTitleStyle1.setFont(reportTitleFont1);

 		        // Set the style to the cell
 		        reportTitleCell1.setCellStyle(reportTitleStyle1);
 		     // Create a font and set its properties
 		    

 		        
 		        // Set headers after the title
 		        int headerRowIndex = 6; // Adjusted for the title rows above
 		        Row headerRow = sheet.createRow(headerRowIndex);
 		        
 		        CellStyle borderStyle = workbook.createCellStyle();
 		        borderStyle.setAlignment(HorizontalAlignment.CENTER);
 		        borderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		        borderStyle.setBorderBottom(BorderStyle.THIN);
 		        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		        borderStyle.setBorderTop(BorderStyle.THIN);
 		        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		        borderStyle.setBorderLeft(BorderStyle.THIN);
 		        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		        borderStyle.setBorderRight(BorderStyle.THIN);
 		        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		        Font boldFont = workbook.createFont();
 		        boldFont.setBold(true);
 		        boldFont.setFontHeightInPoints((short) 16);

 		        for (int i = 0; i < columnsHeader.length; i++) 
 		        {
 		            Cell cell = headerRow.createCell(i);
 		            cell.setCellValue(columnsHeader[i]);

 		            CellStyle headerStyle = workbook.createCellStyle();
 		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		            
 		
 			        
 		            Font headerFont = workbook.createFont();
 		            headerFont.setBold(true);
 		            headerFont.setFontHeightInPoints((short) 11);

 		            headerStyle.setFont(headerFont);
 		            headerStyle.setBorderBottom(BorderStyle.THIN);
 		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderTop(BorderStyle.THIN);
 		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderLeft(BorderStyle.THIN);
 		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderRight(BorderStyle.THIN);
 		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
 		            
 		            
 		            headerFont.setColor(IndexedColors.WHITE.getIndex());
 					   
 			        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 			        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		            cell.setCellStyle(headerStyle);
 		            int headerWidth = (int) (columnsHeader[i].length() * 360 * widthFactor);
 		            sheet.setColumnWidth(i, headerWidth);
 		        }
 		        
 		        

 		        // Populate data rows
 		        int rowNum = headerRowIndex + 1;


 		      
 		        
 		        
 		        int serialNo = 1; // Initialize serial number counter
 		        for (Object[] resultData1 : importDetails) {
 		            Row dataRow = sheet.createRow(rowNum++);
 		    
 		            int cellNum = 0;

 		            for (int i = 0; i < columnsHeader.length; i++) {
 		                Cell cell = dataRow.createCell(i);
 		                cell.setCellStyle(borderStyle);

 		                // Switch case to handle each column header

 		                switch (columnsHeader[i]) {
 		                case "SR NO":
 		                    cell.setCellValue(serialNo++); // Serial Number increment
 		                    break;

 		                case "Container No":
 		                    cell.setCellValue(resultData1[0] != null ? resultData1[0].toString() : "");
 		                    break;

 		                case "Size":
 		                    cell.setCellValue(resultData1[1] != null ? resultData1[1].toString() : "");
 		                    break;

 		                case "Type":
 		                    cell.setCellValue(resultData1[2] != null ? resultData1[2].toString() : "");
 		                    break;

 		                case "CtrMode":
 		                    cell.setCellValue(resultData1[3] != null ? resultData1[3].toString() : "");
 		                    break;

 		                case "Port":
 		                    cell.setCellValue(resultData1[4] != null ? resultData1[4].toString() : "");
 		                    break;

 		                case "Sysdate":
 		                    cell.setCellValue(resultData1[5] != null ? resultData1[5].toString() : "");
 		                    break;

 		                case "Vsl Berthing Date":
 		                    if (resultData1[6] != null) {
 		                        cell.setCellValue(resultData1[6].toString());
 		                        cell.setCellStyle(dateCellStyle);
 		                    } else {
 		                        cell.setBlank();
 		                        cell.setCellStyle(dateCellStyle);
 		                    }
 		                    break;

 		                case "Days":
 		                	  cell.setCellValue(resultData1[16] != null ? resultData1[16].toString() : "");
 		                    break;

 		                case "IgmSealNo":
 		                    cell.setCellValue(resultData1[7] != null ? resultData1[7].toString() : "");
 		                    break;

 		                case "Agent":
 		                    cell.setCellValue(resultData1[8] != null ? resultData1[8].toString() : "");
 		                    break;

 		                case "Vessel":
 		                    cell.setCellValue(resultData1[9] != null ? resultData1[9].toString() : "");
 		                    break;

 		                case "VCN":
 		                    cell.setCellValue(resultData1[10] != null ? resultData1[10].toString() : "");
 		                    break;

 		                case "Igm":
 		                    cell.setCellValue(resultData1[11] != null ? resultData1[11].toString() : "");
 		                    break;

 		                case "Item":
 		                    cell.setCellValue(resultData1[12] != null ? resultData1[12].toString() : "");
 		                    break;

 		                case "CtrWt":
 		                    cell.setCellValue(resultData1[13] != null ? resultData1[13].toString() : "");
 		                    break;

 		                case "Importer":
 		                    cell.setCellValue(resultData1[14] != null ? resultData1[14].toString() : "");
 		                    break;

 		                case "Scancode":
 		                    cell.setCellValue(resultData1[15] != null ? resultData1[15].toString() : "");
 		                    break;

 		                default:
 		                    cell.setCellValue(""); // Handle undefined columns
 		                    break;
 		            }

 		            }
 		        }
 		       
 		        // Create a font for bold text
 		     /// Create a row for spacing before totals
 		     // Create a row for spacing before totals
 		        Row emptyRow = sheet.createRow(rowNum++);
 		        emptyRow.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		        

 		        Row empty = sheet.createRow(rowNum++);
 		        empty.createCell(0).setCellValue("Summery Shipping Agent Wise"); // You can set a value or keep it blank
 		        
 		        		
 		        		
 		        Row emptyRow1 = sheet.createRow(rowNum++);
 		        
 		        emptyRow1.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		        // Create a row for totals
// 		        Row totalRow = sheet.createRow(rowNum+ 2);

 		        // Header row for summary
 		        String[] columnsHeaderSummery = {
 		            "Sr No", "Shipping Agent", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		        };

 		        // Create a map to store container count for each agent
 		        Map<String, int[]> agentContainerCount = new HashMap<>();

 		        // Iterate over importDetails and count the container sizes for each shipping agent
 		        importDetails.forEach(i -> {
 		            String agent = i[8] != null ? i[8].toString() : "Unknown"; // Shipping Agent at index 10
 		            String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		            // Initialize counts for this agent if not already present
 		            if (!agentContainerCount.containsKey(agent)) {
 		                agentContainerCount.put(agent, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		            }

 		            // Increment the count based on container size
 		            switch (containerSize) {
 		                case "20":
 		                    agentContainerCount.get(agent)[0]++;
 		                    break;
 		                case "22":
 		                    agentContainerCount.get(agent)[1]++;
 		                    break;
 		                case "40":
 		                    agentContainerCount.get(agent)[2]++;
 		                    break;
 		                case "45":
 		                    agentContainerCount.get(agent)[3]++;
 		                    break;
 		                default:
 		                    break;
 		            }
 		        });

 		        // Apply header row styling and set values
 		        Row headerRow1 = sheet.createRow(rowNum++);
 		        for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		            Cell cell = headerRow1.createCell(i);
 		            cell.setCellValue(columnsHeaderSummery[i]);

 		            // Set cell style for header
 		            CellStyle headerStyle = workbook.createCellStyle();
 		            headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		            Font headerFont = workbook.createFont();
 		            headerFont.setBold(true);
 		            headerFont.setFontHeightInPoints((short) 11);
 		            headerStyle.setFont(headerFont);
 		            headerStyle.setBorderBottom(BorderStyle.THIN);
 		            headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderTop(BorderStyle.THIN);
 		            headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderLeft(BorderStyle.THIN);
 		            headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		            headerStyle.setBorderRight(BorderStyle.THIN);
 		            headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		            headerFont.setColor(IndexedColors.WHITE.getIndex());
 		            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		            cell.setCellStyle(headerStyle);
 		            int headerWidth = (int) (columnsHeaderSummery[i].length() * 360); // Adjust width based on column header length
 		            sheet.setColumnWidth(i, headerWidth);
 		        }

 		        // Iterate over the map to create rows for each shipping agent and their container count
 		        int srNo = 1;
 		        
 		        String shippingAgent =null;
 		        for (Map.Entry<String, int[]> entry : agentContainerCount.entrySet()) {
 		             shippingAgent = entry.getKey();
 		            int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		            // Create a new row for this shipping agent
 		            Row row = sheet.createRow(rowNum++);
 		            row.createCell(0).setCellValue(srNo++); // Sr No (1, 2, 3,...)
 		            row.createCell(1).setCellValue(shippingAgent); // Shipping Agent
 		            row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		            row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		            row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		            row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		            row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers
// 		            row.createCell(7).setCellValue(""); // TUE'S (empty or can be filled if necessary)
 		            
 		            int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		            row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		        }


 		        int totalSize20 = 0, totalSize22 = 0, totalSize40 = 0, totalSize45 = 0;
 		        for (int[] sizes : agentContainerCount.values()) {
 		            totalSize20 += sizes[0];
 		            totalSize22 += sizes[1];
 		            totalSize40 += sizes[2];
 		            totalSize45 += sizes[3];
 		        }

 		     // Create a row for the totals
 		        Row totalRow = sheet.createRow(rowNum++);

 		        // Set the values for the total row
 		        totalRow.createCell(0).setCellValue("Total"); // Label for the total row
 		        totalRow.createCell(2).setCellValue(totalSize20); // Total Size 20
 		        totalRow.createCell(3).setCellValue(totalSize22); // Total Size 22
 		        totalRow.createCell(4).setCellValue(totalSize40); // Total Size 40
 		        totalRow.createCell(5).setCellValue(totalSize45); // Total Size 45
 		        totalRow.createCell(6).setCellValue(totalSize20 + totalSize22 + totalSize40 + totalSize45); // Total of all containers

 		        // Calculate and set the total TUE'S value
 		        int totalTuesValue = (totalSize20 * 1) + (totalSize22 * 1) + (totalSize40 * 2) + (totalSize45 * 2);
 		        totalRow.createCell(7).setCellValue(totalTuesValue); // Total TUE'S value

 		     // Apply the total row style (light green background and bold font)

 		     // Create a CellStyle for the background color
 		     CellStyle totalRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     // Create a font for bold text
 		     Font boldFont1 = workbook.createFont();
 		     boldFont1.setBold(true);
 		     totalRowStyle.setFont(boldFont1);

 		     // Apply bold font and other styling
 		     for (int i = 0; i < columnsHeaderSummery.length; i++) {
 		         Cell cell = totalRow.getCell(i);
 		         cell.setCellStyle(totalRowStyle);
 		     }

 		     Row emptyRow11 = sheet.createRow(rowNum++);
 		        emptyRow11.createCell(0).setCellValue(""); // You can set a value or keep it blank

 		     
 		  // Create a new row for Vessel Wise Summary title
 		     Row vesselSummaryTitle = sheet.createRow(rowNum++);
 		     vesselSummaryTitle.createCell(0).setCellValue("Summary Vessel Wise"); // You can set a value or keep it blank

 		     
 		     // Create an empty row for spacing
 		     Row emptyRowVessel = sheet.createRow(rowNum++);
 		     emptyRowVessel.createCell(0).setCellValue(""); // You can set a value or keep it blank
 		     
 		    

 		     // Header row for vessel summary
 		     String[] columnsHeaderVesselSummery = {
 		         "Sr No", "Vessel", "Size 20", "Size 22", "Size 40", "Size 45", "Total", "TUE'S"
 		     };

 		     // Create a map to store container count for each vessel
 		     Map<String, int[]> vesselContainerCount = new HashMap<>();

 		     // Iterate over importDetails and count the container sizes for each vessel
 		     importDetails.forEach(i -> {
 		         String vessel = i[9] != null ? i[9].toString() : "Unknown"; // Vessel at index 10
 		         String containerSize = i[1] != null ? i[1].toString() : ""; // Container Size at index 3

 		         // Initialize counts for this vessel if not already present
 		         if (!vesselContainerCount.containsKey(vessel)) {
 		             vesselContainerCount.put(vessel, new int[4]); // Index 0 - Size 20, 1 - Size 22, 2 - Size 40, 3 - Size 45
 		         }

 		         // Increment the count based on container size
 		         switch (containerSize) {
 		             case "20":
 		                 vesselContainerCount.get(vessel)[0]++;
 		                 break;
 		             case "22":
 		                 vesselContainerCount.get(vessel)[1]++;
 		                 break;
 		             case "40":
 		                 vesselContainerCount.get(vessel)[2]++;
 		                 break;
 		             case "45":
 		                 vesselContainerCount.get(vessel)[3]++;
 		                 break;
 		             default:
 		                 break;
 		         }
 		     });

 		     // Apply header row styling and set values for Vessel Wise Summary
 		     Row headerRowVessel = sheet.createRow(rowNum++);
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = headerRowVessel.createCell(i);
 		         cell.setCellValue(columnsHeaderVesselSummery[i]);

 		         // Set cell style for header
 		         CellStyle headerStyle = workbook.createCellStyle();
 		         headerStyle.setAlignment(HorizontalAlignment.CENTER);
 		         headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

 		         Font headerFont = workbook.createFont();
 		         headerFont.setBold(true);
 		         headerFont.setFontHeightInPoints((short) 11);
 		         headerStyle.setFont(headerFont);
 		         headerStyle.setBorderBottom(BorderStyle.THIN);
 		         headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
 		         headerStyle.setBorderTop(BorderStyle.THIN);
 		         headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
 		         headerStyle.setBorderLeft(BorderStyle.THIN);
 		         headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
 		         headerStyle.setBorderRight(BorderStyle.THIN);
 		         headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

 		         headerFont.setColor(IndexedColors.WHITE.getIndex());
 		         headerStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
 		         headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		         cell.setCellStyle(headerStyle);
 		         int headerWidth = (int) (columnsHeaderVesselSummery[i].length() * 360); // Adjust width based on column header length
 		         sheet.setColumnWidth(i, headerWidth);
 		     }

 		     // Iterate over the map to create rows for each vessel and their container count
 		     int srNoVessel = 1;
 		     for (Map.Entry<String, int[]> entry : vesselContainerCount.entrySet()) {
 		         String vessel = entry.getKey();
 		         int[] sizes = entry.getValue(); // Sizes[0] = Size 20, Sizes[1] = Size 22, Sizes[2] = Size 40, Sizes[3] = Size 45

 		         // Create a new row for this vessel
 		         Row row = sheet.createRow(rowNum++);
 		         row.createCell(0).setCellValue(srNoVessel++); // Sr No (1, 2, 3,...)
 		         row.createCell(1).setCellValue(vessel); // Vessel Name
 		         row.createCell(2).setCellValue(sizes[0]); // Size 20 count
 		         row.createCell(3).setCellValue(sizes[1]); // Size 22 count
 		         row.createCell(4).setCellValue(sizes[2]); // Size 40 count
 		         row.createCell(5).setCellValue(sizes[3]); // Size 45 count
 		         row.createCell(6).setCellValue(sizes[0] + sizes[1] + sizes[2] + sizes[3]); // Total count of containers

 		         // Calculate and set the TUE'S value
 		         int tuesValue = (sizes[0] * 1) + (sizes[1] * 1) + (sizes[2] * 2) + (sizes[3] * 2);
 		         row.createCell(7).setCellValue(tuesValue); // TUE'S value
 		     }

 		     // Calculate totals for all vessel data
 		     int totalVesselSize20 = 0, totalVesselSize22 = 0, totalVesselSize40 = 0, totalVesselSize45 = 0;
 		     for (int[] sizes : vesselContainerCount.values()) {
 		         totalVesselSize20 += sizes[0];
 		         totalVesselSize22 += sizes[1];
 		         totalVesselSize40 += sizes[2];
 		         totalVesselSize45 += sizes[3];
 		     }

 		     // Create a row for the totals of Vessel Wise Summary
 		     Row totalVesselRow = sheet.createRow(rowNum++);

 		     // Set the values for the total row
 		     totalVesselRow.createCell(0).setCellValue("Total"); // Label for the total row
 		     totalVesselRow.createCell(2).setCellValue(totalVesselSize20); // Total Size 20
 		     totalVesselRow.createCell(3).setCellValue(totalVesselSize22); // Total Size 22
 		     totalVesselRow.createCell(4).setCellValue(totalVesselSize40); // Total Size 40
 		     totalVesselRow.createCell(5).setCellValue(totalVesselSize45); // Total Size 45
 		     totalVesselRow.createCell(6).setCellValue(totalVesselSize20 + totalVesselSize22 + totalVesselSize40 + totalVesselSize45); // Total of all containers

 		     // Calculate and set the total TUE'S value for Vessel Wise
 		     int totalVesselTuesValue = (totalVesselSize20 * 1) + (totalVesselSize22 * 1) + (totalVesselSize40 * 2) + (totalVesselSize45 * 2);
 		     totalVesselRow.createCell(7).setCellValue(totalVesselTuesValue); // Total TUE'S value

 		     // Apply the total row style for Vessel Wise
 		     CellStyle totalVesselRowStyle = sheet.getWorkbook().createCellStyle();
 		     totalVesselRowStyle.cloneStyleFrom(numberCellStyle); // Copy base style
 		     totalVesselRowStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex()); // Set to light green
 		     totalVesselRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

 		     // Apply the style to the cells of the total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         // Ensure the cell exists before applying style
 		         Cell cell = totalVesselRow.getCell(i);
 		         if (cell == null) {
 		             cell = totalVesselRow.createCell(i);
 		         }
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }

 		     // Create a font for bold text in the total row
 		     Font boldFontVessel = workbook.createFont();
 		     boldFontVessel.setBold(true);
 		     totalVesselRowStyle.setFont(boldFontVessel);

 		     // Apply bold font and other styling for total row
 		     for (int i = 0; i < columnsHeaderVesselSummery.length; i++) {
 		         Cell cell = totalVesselRow.getCell(i);
 		         cell.setCellStyle(totalVesselRowStyle);
 		     }




 		        // Assuming 'CHA' is at index 14 and 'Importer' at index 15 based on your headers
 		        sheet.setColumnWidth(0,  9 * 306); 
 		        sheet.setColumnWidth(1, 18 * 306); 
 		        sheet.setColumnWidth(2, 9 * 306); 
 		        sheet.setColumnWidth(3, 18 * 306); 
 		        
 		        sheet.setColumnWidth(4, 18 * 306); 
 		        sheet.setColumnWidth(5, 18 * 306); 
 		        
 		        sheet.setColumnWidth(6, 18 * 306); 
 		        sheet.setColumnWidth(7, 18 * 306); 
 		        
 		        sheet.setColumnWidth(8, 18 * 306); 
 		        sheet.setColumnWidth(9, 18 * 306); 
 		        sheet.setColumnWidth(10, 45 * 306); // Set width for "Importer" (40 characters wide)
 		        
 		        sheet.setColumnWidth(11, 18 * 306); // Set width for "CHA" (30 characters wide)
 		        sheet.setColumnWidth(12, 18 * 306); // Set width for "Importer" (40 characters wide)
 		        
 		        sheet.setColumnWidth(13,  18 * 306); 
 		        sheet.setColumnWidth(14, 18 * 306); 
 		        sheet.setColumnWidth(15, 18 * 306); 
 		        sheet.setColumnWidth(16, 45 * 306); 

 		        
 		        sheet.setColumnWidth(17, 18 * 306); 
 		        sheet.setColumnWidth(18, 18 * 306); 
 		        sheet.setColumnWidth(19, 18 * 306); 
 		        sheet.setColumnWidth(20,18 * 306); 
 		       
 		        
 		        sheet.setAutoFilter(new CellRangeAddress(headerRowIndex, headerRowIndex, 0, columnsHeader.length - 1));
 		        sheet.createFreezePane(0, headerRowIndex + 1); // Freeze rows above the 6th row

 		        workbook.write(outputStream);
 		        return outputStream.toByteArray();

 		    }
 		        
 		        catch (IOException e) {
 		        e.printStackTrace();
 		    }
        }
        
		   

		    return null;
	}	
	
	
	
	
	
	
	
	
	
	
	public ResponseEntity<Map<String, Map<String, Object>>> getCFSBondingSectionData(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
	    List<Object[]> nocData = commonReportRepo.getCountAndSumOfCargoDuty(companyId, branchId, startDate, endDate);
	    List<Object[]> inbondData = commonReportRepo.getCountAndSumOfInbond(companyId, branchId, startDate, endDate);
	    List<Object[]> exBondData = commonReportRepo.getCountAndSumOfExbond(companyId, branchId, startDate, endDate);
	   
	    
	    
	    Map<String, Object> noc = new HashMap<>();
	 // Process job order data
	 if (nocData != null) {
	     for (Object[] row : nocData) {
	         // Handle nocCount as Long
	         Long nocCount = row[0] != null ? ((Number) row[0]).longValue() : 0L; 
	         
	         // Handle dutysum as BigDecimal or Long and convert to BigDecimal
	         BigDecimal dutysum = row[1] != null 
	             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[1] : BigDecimal.valueOf(((Number) row[1]).longValue())) 
	             : BigDecimal.ZERO;

	         noc.put("nocCount", nocCount);
	         noc.put("dutysum", dutysum);
	     }
	 }


	    noc.put("name", "CFS NOC");
	   

	    // Initialize container counts for seal cutting
	    Map<String, Object> inbond = new HashMap<>();
	    // Process seal cutting data (corrected loop)
	    if (inbondData != null) {
	        for (Object[] row : inbondData) { // Corrected: use sealCutting instead of joGateIn
	        	  Long nocCount = row[0] != null ? ((Number) row[0]).longValue() : 0L; 
	 	         
	 	         // Handle dutysum as BigDecimal or Long and convert to BigDecimal
	 	         BigDecimal dutysum = row[1] != null 
	 	             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[1] : BigDecimal.valueOf(((Number) row[1]).longValue())) 
	 	             : BigDecimal.ZERO;

	 	        inbond.put("nocCount", nocCount);
	 	       inbond.put("dutysum", dutysum);
	        }
	    }
	    inbond.put("name", "INBOND DATA");
	    
	    
	    Map<String, Object> exbond = new HashMap<>();
	    // Process seal cutting data (corrected loop)
	    if (exBondData != null) {
	        for (Object[] row : exBondData) { // Corrected: use sealCutting instead of joGateIn
	        	  Long nocCount = row[0] != null ? ((Number) row[0]).longValue() : 0L; 
	 	         
	 	         // Handle dutysum as BigDecimal or Long and convert to BigDecimal
	 	         BigDecimal dutysum = row[1] != null 
	 	             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[1] : BigDecimal.valueOf(((Number) row[1]).longValue())) 
	 	             : BigDecimal.ZERO;

	 	        exbond.put("nocCount", nocCount);
	 	       exbond.put("dutysum", dutysum);
	        }
	    }
	    exbond.put("name", "EXBOND DATA");
	    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    result.put("noc", noc);
	    result.put("inbond", inbond);
	    result.put("exbond", exbond);

//	    result.put("exportCarting", exportCarting);

	    return ResponseEntity.ok(result);
	}
}
