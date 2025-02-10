package com.cwms.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cwms.entities.Cfinbondcrg;
import com.cwms.repository.BranchRepo;
import com.cwms.repository.CommonReportsRepo;
import com.cwms.repository.CompanyRepo;
import com.cwms.repository.DashboardRepository;

@Service
public class DashBoardService {

	@Autowired
	private DashboardRepository dashboardRepository;
	
	
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
//		  List<Object[]> fclDestuff = commonReportRepo.findDistinctFCLContainers(companyId, branchId, startDate, endDate);
//		    List<Object[]> fclLoaded = commonReportRepo.findDistinctFCLGateOutContainers(companyId, branchId, startDate, endDate);
//	    List<Object[]> impGateIn = commonReportRepo.findDistinctGateInData(companyId, branchId, startDate, endDate);
//	    List<Object[]> loadedInventory = commonReportRepo.getLoadedInventoryData(companyId, branchId, endDate);
//	    List<Object[]> emptyOutt = commonReportRepo.findDistinctMTYContainers(companyId, branchId, startDate, endDate);
		
		  List<Object[]> fclDestuff = dashboardRepository.findDistinctFCLContainers(companyId, branchId, startDate);
    	  List<Object[]> fclLoaded = dashboardRepository.findDistinctFCLGateOutContainers(companyId, branchId, startDate);
    	  List<Object[]> impGateIn = dashboardRepository.findDistinctGateInData(companyId, branchId, startDate);
    	  List<Object[]> loadedInventory = dashboardRepository.getLoadedInventoryData(companyId, branchId);
	  

	   
	    List<Object[]> emptyOutt = dashboardRepository.findDistinctMTYContainers(companyId, branchId, startDate);
	    
	    
	    
	    
	    
	    
	    
//	    List<Object[]> exportBuffer = commonReportRepo.exportBufferGateIn(companyId, branchId, startDate, endDate);
//	    List<Object[]> emptyMovementOut = commonReportRepo.exportEmptyMovementOut(companyId, branchId, startDate, endDate);
//	    List<Object[]> stuffTally = commonReportRepo.findStuffTallyContainers(companyId, branchId, startDate, endDate);
//	    List<Object[]> exportLDD = commonReportRepo.findExpLDDInventoryContainers(companyId, branchId, endDate);
	    
	    List<Object[]> exportBuffer = dashboardRepository.exportBufferGateIn(companyId, branchId, startDate);
	    List<Object[]> emptyMovementOut = dashboardRepository.exportEmptyMovementOut(companyId, branchId, startDate);
	    List<Object[]> stuffTally = dashboardRepository.findStuffTallyContainers(companyId, branchId, startDate);
	    List<Object[]> exportLDD = dashboardRepository.findExpLDDInventoryContainers(companyId, branchId);
	    
	    
	    
	    
	    List<Object[]> nocData = dashboardRepository.getCountAndSumOfCargoDuty(companyId, branchId, startDate);
	    List<Object[]> inbondData = dashboardRepository.getCountAndSumOfInbond(companyId, branchId, startDate);
	    List<Object[]> exBondData = dashboardRepository.getCountAndSumOfExbond(companyId, branchId, startDate);
	    
	    List<Cfinbondcrg> getBondInventory =dashboardRepository.getDataForInBondInventoryReport(companyId,branchId);
	    
	    List<Object[]> custoneApproved =dashboardRepository.getCustomeApprovedDuty(companyId, branchId);
	    
	    
	    List<Object[]> lastWeekGateIn= dashboardRepository.findDistinctGateInDataOfLastWeek(companyId, branchId);
	    List<Object[]> lastWeekLclLoaded= dashboardRepository.findDistinctFCLGateOutContainersLastWeek(companyId, branchId);
	    List<Object[]> lastWeekFclDestuff= dashboardRepository.findDistinctFCLContainersLstWeek(companyId, branchId);
	    List<Object[]> lddInventoryLastWeek= dashboardRepository.getLoadedInventoryDataLastWeek(companyId, branchId);
	    
	    
	    List<Object[]> exportBufferLastWeek= dashboardRepository.exportBufferGateInLastWeek(companyId, branchId);
	    List<Object[]> exportMovementOutLastWeek= dashboardRepository.exportEmptyMovementOutLastWeek(companyId, branchId);
	    List<Object[]> exportStuffTallyLastWeek= dashboardRepository.findStuffTallyContainersLastWeek(companyId, branchId);
	    List<Object[]> exportLDDLastWeek= dashboardRepository.findExpLDDInventoryContainersLastWeek(companyId, branchId);
	    
	    
	    
	    SimpleDateFormat dateFormatFcl = new SimpleDateFormat("yyyy-MM-dd");
	    
	    
	    Map<String, Object> getBondInventoryMap = new HashMap<>();

	 // Initialize counters for total inBondedPackages and inBondGrossWt
	    BigDecimal totalInBondedPackages = BigDecimal.ZERO;
	    BigDecimal totalInBondGrossWt = BigDecimal.ZERO;

	    if (getBondInventory != null) {
	        for (Cfinbondcrg row : getBondInventory) {
	            // Sum up inBondedPackages and inBondGrossWt using add() method
	            totalInBondedPackages = totalInBondedPackages.add(row.getInBondedPackagesDtl());
	            totalInBondGrossWt = totalInBondGrossWt.add(row.getInbondGrossWtDtl());
	        }
	    }

	    // Optionally, you can put these totals in the result map
	    getBondInventoryMap.put("totalInBondedPackages", totalInBondedPackages);
	    getBondInventoryMap.put("totalInBondGrossWt", totalInBondGrossWt);
	    getBondInventoryMap.put("name", "inventory Loaded");


	    Map<String, Object> containerCountsByType = new HashMap<>();

	    int count20L = 0;
	    int count40L = 0;
	    int totalL = 0;
	    
	    if (loadedInventory != null) {
	        for (Object[] row : loadedInventory) {
	            // Assuming that row[0] is container size and row[2] is container type
	            String containerSize = (String) row[1]; // Container size (20, 40, etc.)
	            String countCategory = getContainerSizeCategory(containerSize);

	            

	            // Get the map for this container type
	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	            	count20L++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	            	count40L++;
	            }
	            containerCountsByType.put(countCategory, (Integer) containerCountsByType.getOrDefault(countCategory, 0) + 1);
	        
	        }
	    }
	    int tuesCountL = (count40L * 2) + count20L;
	    int totalCountL = count40L + count20L;
	    containerCountsByType.put("total", totalCountL);
	    containerCountsByType.put("Tues", tuesCountL);
	    containerCountsByType.put("name", "inventory Loaded");


	   
	    Map<String, Object> containerCounts = new HashMap<>();
	    int count20 = 0;
	    int count40 = 0;
	    int total = 0;

	    // Process job order data
	    if (fclDestuff != null) {
	        for (Object[] row : fclDestuff) {
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
	    containerCounts.put("name", "FCL Destuff");
	   

	    // Initialize container counts for seal cutting
	    Map<String, Object> containerCountsSealCutting = new HashMap<>();
	    int countSeal20 = 0;
	    int countSeal40 = 0;

	    // Process seal cutting data (corrected loop)
	    if (fclLoaded != null) {
	        for (Object[] row : fclLoaded) { // Corrected: use sealCutting instead of joGateIn
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
	    containerCountsSealCutting.put("name", "FCL LOADED");
	    
	    
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
	    
	    
	    Map<String, Object> containerCountsEmptyOut = new HashMap<>();
	    int countEmpty20 = 0;
	    int countEmpty40 = 0;

	    // Process import gate-in data
	    if (emptyOutt != null) {
	        for (Object[] row : emptyOutt) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countEmpty20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countEmpty40++;
	                }
	                containerCountsImpGateIn.put(countCategory, (Integer) containerCountsImpGateIn.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountEmpty = (countEmpty40 * 2) + countEmpty20;
	    int totalCountEmpty =countEmpty40 + countEmpty20;
	    containerCountsEmptyOut.put("total", totalCountEmpty);
	    containerCountsEmptyOut.put("Tues", tuesCountEmpty);
	    containerCountsEmptyOut.put("name", "containerCountsEmptyOut");
	    
	    


//	    List<Object[]> lastWeekGateIn = dashboardRepository.findDistinctGateInDataOfLastWeek(companyId, branchId, endDate);
//	    Map<String, Object> lastWeekGateInData = new HashMap<>();
//
//	    Date endDateNew = new Date();
//
//        // Initialize calendar with the end date
//	    
//	    Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(endDateNew);
//
//        calendar1.add(Calendar.DATE, -1);
//        calendar1.set(Calendar.HOUR_OF_DAY, 23);
//        calendar1.set(Calendar.MINUTE, 59);
//        calendar1.set(Calendar.SECOND, 0);
//        calendar1.set(Calendar.MILLISECOND, 0);
//        // Set time to 23:59 for day1
//        
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(endDateNew);
//
//        // Exclude the current date by moving back one day
//        calendar.add(Calendar.DATE, -1);
//
//        // Date formatter
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
//
//        // Print day1 (yesterday) with time 23:59
//        Date day1 = calendar1.getTime();
//        System.out.println("Date: " + dateFormat.format(day1) + ", day1: " + dayFormat.format(day1));
//
//        // Get the 6 days before yesterday without setting time
//        calendar.add(Calendar.DATE, -1);
//        Date day2 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day2) + ", Day: " + dayFormat.format(day2));
//
//        calendar.add(Calendar.DATE, -1);
//        Date day3 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day3) + ", Day: " + dayFormat.format(day3));
//
//        calendar.add(Calendar.DATE, -1);
//        Date day4 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day4) + ", Day: " + dayFormat.format(day4));
//
//        calendar.add(Calendar.DATE, -1);
//        Date day5 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day5) + ", Day: " + dayFormat.format(day5));
//
//        calendar.add(Calendar.DATE, -1);
//        Date day6 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day6) + ", day6: " + dayFormat.format(day6));
//
//        // Get day7 and reset the time to 00:00
//        calendar.add(Calendar.DATE, -1);
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        Date day7 = calendar.getTime();
//        System.out.println("Date: " + dateFormat7.format(day7) + ", Day7: " + dayFormat.format(day7));
//
//	    
//	    // Initialize counters for each day (Monday to Saturday)
//	    Map<String, Integer> dayCounts20W = new HashMap<>();
//	    Map<String, Integer> dayCounts40W = new HashMap<>();
//	    Map<String, Integer> dayWiseTuesCounts = new HashMap<>();
//
//	    dayCounts20W.put("Sunday", 0);
//	    dayCounts20W.put("Monday", 0);
//	    dayCounts20W.put("Tuesday", 0);
//	    dayCounts20W.put("Wednesday", 0);
//	    dayCounts20W.put("Thursday", 0);
//	    dayCounts20W.put("Friday", 0);
//	    dayCounts20W.put("Saturday", 0);
//
//	    dayCounts40W.put("Sunday", 0);
//	    dayCounts40W.put("Monday", 0);
//	    dayCounts40W.put("Tuesday", 0);
//	    dayCounts40W.put("Wednesday", 0);
//	    dayCounts40W.put("Thursday", 0);
//	    dayCounts40W.put("Friday", 0);
//	    dayCounts40W.put("Saturday", 0);
//
//	    dayWiseTuesCounts.put("Sunday", 0);
//	    dayWiseTuesCounts.put("Monday", 0);
//	    dayWiseTuesCounts.put("Tuesday", 0);
//	    dayWiseTuesCounts.put("Wednesday", 0);
//	    dayWiseTuesCounts.put("Thursday", 0);
//	    dayWiseTuesCounts.put("Friday", 0);
//	    dayWiseTuesCounts.put("Saturday", 0);
//
//
//	    
//	    SimpleDateFormat
//    	dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Adjust this pattern as per the format in your data
//	    if (lastWeekGateIn != null) {
//	        for (Object[] row : lastWeekGateIn) {
//	            if (row != null && row.length > 1) { // Null check for row and container size
//	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	                String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	                
//	                
////	                System.out.println( " gateInDateStr  :"+gateInDateStr);;
//
//////	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]  
//	                
//	                try {
//	                    // Get the day of the week for the gate-in date
//	                	 Date gateInDate = dateFormat1.parse(gateInDateStr);
//	                    Calendar gateInCalendar = Calendar.getInstance();
//	                    gateInCalendar.setTime(gateInDate);
//	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                    // Find the corresponding day name
//	                    String dayName = "";
//	                    switch (dayOfWeek) {
//	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                        case Calendar.MONDAY: dayName = "Monday"; break;
//	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                        case Calendar.FRIDAY: dayName = "Friday"; break;
//	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                        default: break;
//	                    }
//
//	                    // Check if the gate-in date is within the Sunday to Saturday week
//	                    if (dayName != "" && isDateBetween(gateInDate, day7, day1)) {
//	                        // Count based on container size
//	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                            dayCounts20W.put(dayName, dayCounts20W.get(dayName) + 1);
//	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                            dayCounts40W.put(dayName, dayCounts40W.get(dayName) + 1);
//	                        }
//	                    }
//	                } catch (Exception e) {
//	                    System.out.println("Error parsing date: ");
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	    }
//
//	    // Calculate tuesCountInW for each day using the formula
//	    for (String day : dayCounts20W.keySet()) {
//	        int countIn20W = dayCounts20W.get(day);
//	        int countIn40W = dayCounts40W.get(day);
//	        int tuesCountInW = (countIn40W * 2) + countIn20W;
//	        dayWiseTuesCounts.put(day, tuesCountInW);
//	    }
//
//	    // Store the results
//	    lastWeekGateInData.put("Monday", dayWiseTuesCounts.get("Monday"));
//	    lastWeekGateInData.put("Tuesday", dayWiseTuesCounts.get("Tuesday"));
//	    lastWeekGateInData.put("Wednesday", dayWiseTuesCounts.get("Wednesday"));
//	    lastWeekGateInData.put("Thursday", dayWiseTuesCounts.get("Thursday"));
//	    lastWeekGateInData.put("Friday", dayWiseTuesCounts.get("Friday"));
//	    lastWeekGateInData.put("Saturday", dayWiseTuesCounts.get("Saturday"));
//	    lastWeekGateInData.put("Sunday", dayWiseTuesCounts.get("Sunday"));
//	    lastWeekGateInData.put("name", "IMPORT GATE IN WEEK");
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
//	    Map<String, Object> lastWeekLclLoadedMap = new HashMap<>();
//
//	    Date endDateNew1 = new Date();
//
//        // Initialize calendar with the end date
//	    
//	    Calendar calendar11 = Calendar.getInstance();
//        calendar11.setTime(endDateNew1);
//
//        calendar11.add(Calendar.DATE, -1);
//        calendar11.set(Calendar.HOUR_OF_DAY, 23);
//        calendar11.set(Calendar.MINUTE, 59);
//        calendar11.set(Calendar.SECOND, 0);
//        calendar11.set(Calendar.MILLISECOND, 0);
//        // Set time to 23:59 for day1
//        
//        Calendar calendar111 = Calendar.getInstance();
//        calendar111.setTime(endDateNew1);
//
//        // Exclude the current date by moving back one day
//        calendar111.add(Calendar.DATE, -1);
//
//        // Print day1 (yesterday) with time 23:59
//        Date day11 = calendar11.getTime();
//        System.out.println("Date: " + dateFormat.format(day11) + ", day1: " + dayFormat.format(day11));
//
//        // Get the 6 days before yesterday without setting time
//        calendar111.add(Calendar.DATE, -1);
//        Date day21 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day21) + ", Day: " + dayFormat.format(day21));
//
//        calendar111.add(Calendar.DATE, -1);
//        Date day31 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day31) + ", Day: " + dayFormat.format(day31));
//
//        calendar111.add(Calendar.DATE, -1);
//        Date day41 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day41) + ", Day: " + dayFormat.format(day41));
//
//        calendar111.add(Calendar.DATE, -1);
//        Date day51 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day51) + ", Day: " + dayFormat.format(day51));
//
//        calendar111.add(Calendar.DATE, -1);
//        Date day61 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day61) + ", day6: " + dayFormat.format(day61));
//
//        // Get day7 and reset the time to 00:00
//        calendar111.add(Calendar.DATE, -1);
//        calendar111.set(Calendar.HOUR_OF_DAY, 0);
//        calendar111.set(Calendar.MINUTE, 0);
//        calendar111.set(Calendar.SECOND, 0);
//        calendar111.set(Calendar.MILLISECOND, 0);
//        Date day71 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat7.format(day71) + ", Day7: " + dayFormat.format(day71));
//
//	    
//	    // Initialize counters for each day (Monday to Saturday)
//	    Map<String, Integer> dayCounts20W1 = new HashMap<>();
//	    Map<String, Integer> dayCounts40W1 = new HashMap<>();
//	    Map<String, Integer> dayWiseTuesCounts1 = new HashMap<>();
//
//	    dayCounts20W1.put("Sunday", 0);
//	    dayCounts20W1.put("Monday", 0);
//	    dayCounts20W1.put("Tuesday", 0);
//	    dayCounts20W1.put("Wednesday", 0);
//	    dayCounts20W1.put("Thursday", 0);
//	    dayCounts20W1.put("Friday", 0);
//	    dayCounts20W1.put("Saturday", 0);
//
//	    dayCounts40W1.put("Sunday", 0);
//	    dayCounts40W1.put("Monday", 0);
//	    dayCounts40W1.put("Tuesday", 0);
//	    dayCounts40W1.put("Wednesday", 0);
//	    dayCounts40W1.put("Thursday", 0);
//	    dayCounts40W1.put("Friday", 0);
//	    dayCounts40W1.put("Saturday", 0);
//
//	    dayWiseTuesCounts1.put("Sunday", 0);
//	    dayWiseTuesCounts1.put("Monday", 0);
//	    dayWiseTuesCounts1.put("Tuesday", 0);
//	    dayWiseTuesCounts1.put("Wednesday", 0);
//	    dayWiseTuesCounts1.put("Thursday", 0);
//	    dayWiseTuesCounts1.put("Friday", 0);
//	    dayWiseTuesCounts1.put("Saturday", 0);
//
//
//	    
//	    SimpleDateFormat
//    	dateFormat11 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Adjust this pattern as per the format in your data
//	    if (lastWeekLclLoaded != null) {
//	        for (Object[] row : lastWeekLclLoaded) {
//	            if (row != null && row.length > 1) { // Null check for row and container size
//	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	                
//	                
////	                System.out.println( " gateInDateStr  :"+gateInDateStr);;
//
//////	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]  
//	                
//	                try {
//	                    // Get the day of the week for the gate-in date
////	                	 Date gateInDate = dateFormat11.parse(gateInDateStr);
//	                    Calendar gateInCalendar = Calendar.getInstance();
//	                    gateInCalendar.setTime(gateInDate);
//	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                    // Find the corresponding day name
//	                    String dayName = "";
//	                    switch (dayOfWeek) {
//	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                        case Calendar.MONDAY: dayName = "Monday"; break;
//	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                        case Calendar.FRIDAY: dayName = "Friday"; break;
//	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                        default: break;
//	                    }
//
//	                    // Check if the gate-in date is within the Sunday to Saturday week
//	                    if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                        // Count based on container size
//	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                            dayCounts20W1.put(dayName, dayCounts20W1.get(dayName) + 1);
//	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                            dayCounts40W1.put(dayName, dayCounts40W1.get(dayName) + 1);
//	                        }
//	                    }
//	                } catch (Exception e) {
//	                    System.out.println("Error parsing date: ");
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	    }
//
//	    // Calculate tuesCountInW for each day using the formula
//	    for (String day : dayCounts20W1.keySet()) {
//	        int countIn20W = dayCounts20W1.get(day);
//	        int countIn40W = dayCounts40W1.get(day);
//	        int tuesCountInW = (countIn40W * 2) + countIn20W;
//	        dayWiseTuesCounts1.put(day, tuesCountInW);
//	    }
//
//	    // Store the results
//	    lastWeekLclLoadedMap.put("Monday", dayWiseTuesCounts1.get("Monday"));
//	    lastWeekLclLoadedMap.put("Tuesday", dayWiseTuesCounts1.get("Tuesday"));
//	    lastWeekLclLoadedMap.put("Wednesday", dayWiseTuesCounts1.get("Wednesday"));
//	    lastWeekLclLoadedMap.put("Thursday", dayWiseTuesCounts1.get("Thursday"));
//	    lastWeekLclLoadedMap.put("Friday", dayWiseTuesCounts1.get("Friday"));
//	    lastWeekLclLoadedMap.put("Saturday", dayWiseTuesCounts1.get("Saturday"));
//	    lastWeekLclLoadedMap.put("Sunday", dayWiseTuesCounts1.get("Sunday"));
//	    lastWeekLclLoadedMap.put("name", "lastWeekLclLoadedMap");
//
//
//
//	    
//	    
//
//	    
//	 // Initialize map for FclDestuff
//	    Map<String, Object> fclDestuffMap = new HashMap<>();
//
//	    // Initialize counters for each day (Monday to Saturday) for FclDestuff
//	    Map<String, Integer> dayCounts20Fcl = new HashMap<>();
//	    Map<String, Integer> dayCounts40Fcl = new HashMap<>();
//	    Map<String, Integer> dayWiseTuesCountsFcl = new HashMap<>();
//
//	    // Initialize day counts for FclDestuff
//	    dayCounts20Fcl.put("Sunday", 0);
//	    dayCounts20Fcl.put("Monday", 0);
//	    dayCounts20Fcl.put("Tuesday", 0);
//	    dayCounts20Fcl.put("Wednesday", 0);
//	    dayCounts20Fcl.put("Thursday", 0);
//	    dayCounts20Fcl.put("Friday", 0);
//	    dayCounts20Fcl.put("Saturday", 0);
//
//	    dayCounts40Fcl.put("Sunday", 0);
//	    dayCounts40Fcl.put("Monday", 0);
//	    dayCounts40Fcl.put("Tuesday", 0);
//	    dayCounts40Fcl.put("Wednesday", 0);
//	    dayCounts40Fcl.put("Thursday", 0);
//	    dayCounts40Fcl.put("Friday", 0);
//	    dayCounts40Fcl.put("Saturday", 0);
//
//	    dayWiseTuesCountsFcl.put("Sunday", 0);
//	    dayWiseTuesCountsFcl.put("Monday", 0);
//	    dayWiseTuesCountsFcl.put("Tuesday", 0);
//	    dayWiseTuesCountsFcl.put("Wednesday", 0);
//	    dayWiseTuesCountsFcl.put("Thursday", 0);
//	    dayWiseTuesCountsFcl.put("Friday", 0);
//	    dayWiseTuesCountsFcl.put("Saturday", 0);
//
//	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	    if (lastWeekFclDestuff != null) {
//	        for (Object[] row : lastWeekFclDestuff) {
//	            if (row != null && row.length > 1) {
//	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	                
//	                try {
////	                    Date gateInDate = dateFormat111.parse(gateInDateStr);
//	                    Calendar gateInCalendar = Calendar.getInstance();
//	                    gateInCalendar.setTime(gateInDate);
//	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                    String dayName = "";
//	                    switch (dayOfWeek) {
//	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                        case Calendar.MONDAY: dayName = "Monday"; break;
//	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                        case Calendar.FRIDAY: dayName = "Friday"; break;
//	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                        default: break;
//	                    }
//
//	                    // Check if the gate-in date is within the Sunday to Saturday week
//	                    if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                        // Count based on container size for FclDestuff
//	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                            dayCounts20Fcl.put(dayName, dayCounts20Fcl.get(dayName) + 1);
//	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                            dayCounts40Fcl.put(dayName, dayCounts40Fcl.get(dayName) + 1);
//	                        }
//	                    }
//	                } catch (Exception e) {
//	                    System.out.println("Error parsing date: ");
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	    }
//
//	    // Calculate tuesCountInW for each day using the formula for FclDestuff
//	    for (String day : dayCounts20Fcl.keySet()) {
//	        int countIn20Fcl = dayCounts20Fcl.get(day);
//	        int countIn40Fcl = dayCounts40Fcl.get(day);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
//	        dayWiseTuesCountsFcl.put(day, tuesCountInFcl);
//	    }
//
//	    // Store the results in the map
//	    fclDestuffMap.put("Monday", dayWiseTuesCountsFcl.get("Monday"));
//	    fclDestuffMap.put("Tuesday", dayWiseTuesCountsFcl.get("Tuesday"));
//	    fclDestuffMap.put("Wednesday", dayWiseTuesCountsFcl.get("Wednesday"));
//	    fclDestuffMap.put("Thursday", dayWiseTuesCountsFcl.get("Thursday"));
//	    fclDestuffMap.put("Friday", dayWiseTuesCountsFcl.get("Friday"));
//	    fclDestuffMap.put("Saturday", dayWiseTuesCountsFcl.get("Saturday"));
//	    fclDestuffMap.put("Sunday", dayWiseTuesCountsFcl.get("Sunday"));
//	    fclDestuffMap.put("name", "fclDestuffMap");
//
//	    
//	    
//	    
//	    
//	    Map<String, Object> lddMapLastWeek = new HashMap<>();
//
//	 // Initialize counters for each day (Monday to Saturday) for LDD
//	 Map<String, Integer> dayCounts20Ldd = new HashMap<>();
//	 Map<String, Integer> dayCounts40Ldd = new HashMap<>();
//	 Map<String, Integer> dayWiseTuesCountsLdd = new HashMap<>();
//
//	 // Initialize day counts for LDD
//	 dayCounts20Ldd.put("Sunday", 0);
//	 dayCounts20Ldd.put("Monday", 0);
//	 dayCounts20Ldd.put("Tuesday", 0);
//	 dayCounts20Ldd.put("Wednesday", 0);
//	 dayCounts20Ldd.put("Thursday", 0);
//	 dayCounts20Ldd.put("Friday", 0);
//	 dayCounts20Ldd.put("Saturday", 0);
//
//	 dayCounts40Ldd.put("Sunday", 0);
//	 dayCounts40Ldd.put("Monday", 0);
//	 dayCounts40Ldd.put("Tuesday", 0);
//	 dayCounts40Ldd.put("Wednesday", 0);
//	 dayCounts40Ldd.put("Thursday", 0);
//	 dayCounts40Ldd.put("Friday", 0);
//	 dayCounts40Ldd.put("Saturday", 0);
//
//	 dayWiseTuesCountsLdd.put("Sunday", 0);
//	 dayWiseTuesCountsLdd.put("Monday", 0);
//	 dayWiseTuesCountsLdd.put("Tuesday", 0);
//	 dayWiseTuesCountsLdd.put("Wednesday", 0);
//	 dayWiseTuesCountsLdd.put("Thursday", 0);
//	 dayWiseTuesCountsLdd.put("Friday", 0);
//	 dayWiseTuesCountsLdd.put("Saturday", 0);
//
//	 // Loop through LDD data (similar to lastWeekFclDestuff)
//	 SimpleDateFormat dateFormatLdd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	 if (lddInventoryLastWeek != null) {
//	     for (Object[] row : lddInventoryLastWeek) {
//	         if (row != null && row.length > 1) {
//	             String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	             String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	             
//	             try {
//	                 Date gateInDate = dateFormatLdd.parse(gateInDateStr);
//	                 Calendar gateInCalendar = Calendar.getInstance();
//	                 gateInCalendar.setTime(gateInDate);
//	                 int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                 String dayName = "";
//	                 switch (dayOfWeek) {
//	                     case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                     case Calendar.MONDAY: dayName = "Monday"; break;
//	                     case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                     case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                     case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                     case Calendar.FRIDAY: dayName = "Friday"; break;
//	                     case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                     default: break;
//	                 }
//
//	                 // Check if the gate-in date is within the Sunday to Saturday week
//	                 if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                     // Count based on container size for LDD
//	                     if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                         dayCounts20Ldd.put(dayName, dayCounts20Ldd.get(dayName) + 1);
//	                     } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                         dayCounts40Ldd.put(dayName, dayCounts40Ldd.get(dayName) + 1);
//	                     }
//	                 }
//	             } catch (Exception e) {
//	                 System.out.println("Error parsing date: ");
//	                 e.printStackTrace();
//	             }
//	         }
//	     }
//	 }
//
//	 // Calculate tuesCountInLdd for each day using the formula for LDD
//	 for (String day : dayCounts20Ldd.keySet()) {
//	     int countIn20Ldd = dayCounts20Ldd.get(day);
//	     int countIn40Ldd = dayCounts40Ldd.get(day);
//	     int tuesCountInLdd = (countIn40Ldd * 2) + countIn20Ldd;
//	     dayWiseTuesCountsLdd.put(day, tuesCountInLdd);
//	 }
//
//	 // Store the results in the map
//	 lddMapLastWeek.put("Monday", dayWiseTuesCountsLdd.get("Monday"));
//	 lddMapLastWeek.put("Tuesday", dayWiseTuesCountsLdd.get("Tuesday"));
//	 lddMapLastWeek.put("Wednesday", dayWiseTuesCountsLdd.get("Wednesday"));
//	 lddMapLastWeek.put("Thursday", dayWiseTuesCountsLdd.get("Thursday"));
//	 lddMapLastWeek.put("Friday", dayWiseTuesCountsLdd.get("Friday"));
//	 lddMapLastWeek.put("Saturday", dayWiseTuesCountsLdd.get("Saturday"));
//	 lddMapLastWeek.put("Sunday", dayWiseTuesCountsLdd.get("Sunday"));
//	 lddMapLastWeek.put("name", "lddMapLastWeek");
	    
	    
	    
	    
	    
	    
	    
	    
	    Map<String, Object> lastWeekGateInData = new HashMap<>();

	    Date endDateNew = new Date();

        // Initialize calendar with the end date
	    
	    Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(endDateNew);

        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        // Set time to 23:59 for day1
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDateNew);

        // Exclude the current date by moving back one day
        calendar.add(Calendar.DATE, -1);

        // Date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

        // Print day1 (yesterday) with time 23:59
        Date day1 = calendar1.getTime();
//        System.out.println("Date: " + dateFormat.format(day1) + ", day1: " + dayFormat.format(day1));

        // Get the 6 days before yesterday without setting time
        calendar.add(Calendar.DATE, -1);
        Date day2 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day2) + ", Day: " + dayFormat.format(day2));

        calendar.add(Calendar.DATE, -1);
        Date day3 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day3) + ", Day: " + dayFormat.format(day3));

        calendar.add(Calendar.DATE, -1);
        Date day4 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day4) + ", Day: " + dayFormat.format(day4));

        calendar.add(Calendar.DATE, -1);
        Date day5 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day5) + ", Day: " + dayFormat.format(day5));

        calendar.add(Calendar.DATE, -1);
        Date day6 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day6) + ", day6: " + dayFormat.format(day6));

        // Get day7 and reset the time to 00:00
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date day7 = calendar.getTime();
//        System.out.println("Date: " + dateFormat7.format(day7) + ", Day7: " + dayFormat.format(day7));

	    
	    Date endDateNew1 = new Date();

        // Initialize calendar with the end date
	    
	    Calendar calendar11 = Calendar.getInstance();
        calendar11.setTime(endDateNew1);

        calendar11.add(Calendar.DATE, -1);
        calendar11.set(Calendar.HOUR_OF_DAY, 23);
        calendar11.set(Calendar.MINUTE, 59);
        calendar11.set(Calendar.SECOND, 0);
        calendar11.set(Calendar.MILLISECOND, 0);
        // Set time to 23:59 for day1
        
        Calendar calendar111 = Calendar.getInstance();
        calendar111.setTime(endDateNew1);

        // Exclude the current date by moving back one day
        calendar111.add(Calendar.DATE, -1);

        // Print day1 (yesterday) with time 23:59
        Date day11 = calendar11.getTime();
//        System.out.println("Date: " + dateFormat.format(day11) + ", day1: " + dayFormat.format(day11));

        // Get the 6 days before yesterday without setting time
        calendar111.add(Calendar.DATE, -1);
        Date day21 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day21) + ", Day: " + dayFormat.format(day21));

        calendar111.add(Calendar.DATE, -1);
        Date day31 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day31) + ", Day: " + dayFormat.format(day31));

        calendar111.add(Calendar.DATE, -1);
        Date day41 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day41) + ", Day: " + dayFormat.format(day41));

        calendar111.add(Calendar.DATE, -1);
        Date day51 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day51) + ", Day: " + dayFormat.format(day51));

        calendar111.add(Calendar.DATE, -1);
        Date day61 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day61) + ", day6: " + dayFormat.format(day61));

        // Get day7 and reset the time to 00:00
        calendar111.add(Calendar.DATE, -1);
        calendar111.set(Calendar.HOUR_OF_DAY, 0);
        calendar111.set(Calendar.MINUTE, 0);
        calendar111.set(Calendar.SECOND, 0);
        calendar111.set(Calendar.MILLISECOND, 0);
        Date day71 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat7.format(day71) + ", Day7: " + dayFormat.format(day71));

        
	    // Initialize counters for each day (Monday to Saturday)
	    Map<String, Integer> dayCounts20W = new HashMap<>();
	    Map<String, Integer> dayCounts40W = new HashMap<>();
	    Map<String, Integer> dayWiseTuesCounts = new HashMap<>();
	    
	    dayCounts20W.put(dateFormatFcl.format(day11), 0);
	    dayCounts20W.put(dateFormatFcl.format(day21), 0);
	    dayCounts20W.put(dateFormatFcl.format(day31), 0);
	    dayCounts20W.put(dateFormatFcl.format(day41), 0);
	    dayCounts20W.put(dateFormatFcl.format(day51), 0);
	    dayCounts20W.put(dateFormatFcl.format(day61), 0);
	    dayCounts20W.put(dateFormatFcl.format(day71), 0);

	    dayCounts40W.put(dateFormatFcl.format(day11), 0);
	    dayCounts40W.put(dateFormatFcl.format(day21), 0);
	    dayCounts40W.put(dateFormatFcl.format(day31), 0);
	    dayCounts40W.put(dateFormatFcl.format(day41), 0);
	    dayCounts40W.put(dateFormatFcl.format(day51), 0);
	    dayCounts40W.put(dateFormatFcl.format(day61), 0);
	    dayCounts40W.put(dateFormatFcl.format(day71), 0);

	    dayWiseTuesCounts.put(dateFormatFcl.format(day11), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day21), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day31), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day41), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day51), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day61), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (lastWeekGateIn != null) {
	        for (Object[] row : lastWeekGateIn) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                	Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20W.put(gateInDateString, dayCounts20W.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40W.put(gateInDateString, dayCounts40W.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20W.keySet()) {
	        int countIn20Fcl = dayCounts20W.get(date);
	        int countIn40Fcl = dayCounts40W.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCounts.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    lastWeekGateInData.put(dateFormatFcl.format(day11), dayWiseTuesCounts.get(dateFormatFcl.format(day11)));
	    lastWeekGateInData.put(dateFormatFcl.format(day21), dayWiseTuesCounts.get(dateFormatFcl.format(day21)));
	    lastWeekGateInData.put(dateFormatFcl.format(day31), dayWiseTuesCounts.get(dateFormatFcl.format(day31)));
	    lastWeekGateInData.put(dateFormatFcl.format(day41), dayWiseTuesCounts.get(dateFormatFcl.format(day41)));
	    lastWeekGateInData.put(dateFormatFcl.format(day51), dayWiseTuesCounts.get(dateFormatFcl.format(day51)));
	    lastWeekGateInData.put(dateFormatFcl.format(day61), dayWiseTuesCounts.get(dateFormatFcl.format(day61)));
	    lastWeekGateInData.put(dateFormatFcl.format(day71), dayWiseTuesCounts.get(dateFormatFcl.format(day71)));
	    lastWeekGateInData.put("name", "IMPORT GATE IN WEEK");
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    Map<String, Object> lastWeekLclLoadedMap = new HashMap<>();
	    
	    // Initialize counters for each day (Monday to Saturday)
	    Map<String, Integer> dayCounts20W1 = new HashMap<>();
	    Map<String, Integer> dayCounts40W1 = new HashMap<>();
	    Map<String, Integer> dayWiseTuesCounts1 = new HashMap<>();
	    
	    dayCounts20W1.put(dateFormatFcl.format(day11), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day21), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day31), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day41), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day51), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day61), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day71), 0);

	    dayCounts40W1.put(dateFormatFcl.format(day11), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day21), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day31), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day41), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day51), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day61), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day71), 0);

	    dayWiseTuesCounts1.put(dateFormatFcl.format(day11), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day21), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day31), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day41), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day51), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day61), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (lastWeekLclLoaded != null) {
	        for (Object[] row : lastWeekLclLoaded) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20W1.put(gateInDateString, dayCounts20W1.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40W1.put(gateInDateString, dayCounts40W1.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20W1.keySet()) {
	        int countIn20Fcl = dayCounts20W1.get(date);
	        int countIn40Fcl = dayCounts40W1.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCounts1.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day11), dayWiseTuesCounts1.get(dateFormatFcl.format(day11)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day21), dayWiseTuesCounts1.get(dateFormatFcl.format(day21)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day31), dayWiseTuesCounts1.get(dateFormatFcl.format(day31)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day41), dayWiseTuesCounts1.get(dateFormatFcl.format(day41)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day51), dayWiseTuesCounts1.get(dateFormatFcl.format(day51)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day61), dayWiseTuesCounts1.get(dateFormatFcl.format(day61)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day71), dayWiseTuesCounts1.get(dateFormatFcl.format(day71)));
	    lastWeekLclLoadedMap.put("name", "lastWeekLclLoadedMap");


	    
	    

	    
	 // Initialize map for FclDestuff
	    Map<String, Object> fclDestuffMap = new HashMap<>();

	    
	 // Initialize counters for each date (last 7 days)
	    Map<String, Integer> dateCounts20Fcl = new HashMap<>();
	    Map<String, Integer> dateCounts40Fcl = new HashMap<>();
	    Map<String, Integer> dateWiseTuesCountsFcl = new HashMap<>();

	    // Initialize date counts for FclDestuff with last 7 dates (from day1 to day7)
	  
	    dateCounts20Fcl.put(dateFormatFcl.format(day11), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day21), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day31), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day41), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day51), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day61), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day71), 0);

	    dateCounts40Fcl.put(dateFormatFcl.format(day11), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day21), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day31), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day41), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day51), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day61), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day71), 0);

	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day11), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day21), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day31), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day41), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day51), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day61), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (lastWeekFclDestuff != null) {
	        for (Object[] row : lastWeekFclDestuff) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                            dateCounts20Fcl.put(gateInDateString, dateCounts20Fcl.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                            dateCounts40Fcl.put(gateInDateString, dateCounts40Fcl.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dateCounts20Fcl.keySet()) {
	        int countIn20Fcl = dateCounts20Fcl.get(date);
	        int countIn40Fcl = dateCounts40Fcl.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dateWiseTuesCountsFcl.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    fclDestuffMap.put(dateFormatFcl.format(day11), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day11)));
	    fclDestuffMap.put(dateFormatFcl.format(day21), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day21)));
	    fclDestuffMap.put(dateFormatFcl.format(day31), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day31)));
	    fclDestuffMap.put(dateFormatFcl.format(day41), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day41)));
	    fclDestuffMap.put(dateFormatFcl.format(day51), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day51)));
	    fclDestuffMap.put(dateFormatFcl.format(day61), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day61)));
	    fclDestuffMap.put(dateFormatFcl.format(day71), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day71)));

	    // Adding name to the map
	    fclDestuffMap.put("name", "fclDestuffMap");

	    
	    
	    Map<String, Object> lddMapLastWeek = new HashMap<>();

	 // Initialize counters for each day (Monday to Saturday) for LDD
	 Map<String, Integer> dayCounts20Ldd = new HashMap<>();
	 Map<String, Integer> dayCounts40Ldd = new HashMap<>();
	 Map<String, Integer> dayWiseTuesCountsLdd = new HashMap<>();

	 // Initialize day counts for LDD
	 
	 dayCounts20Ldd.put(dateFormatFcl.format(day11), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day21), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day31), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day41), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day51), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day61), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day71), 0);

	 dayCounts40Ldd.put(dateFormatFcl.format(day11), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day21), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day31), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day41), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day51), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day61), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day71), 0);

	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day11), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day21), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day31), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day41), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day51), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day61), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day71), 0);
	    
	 if (lddInventoryLastWeek != null) {
	        for (Object[] row : lddInventoryLastWeek) {
	            if (row != null && row.length > 1) {
	            	 String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		             String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                	 Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                            dateCounts20Fcl.put(gateInDateString, dateCounts20Fcl.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                            dateCounts40Fcl.put(gateInDateString, dateCounts40Fcl.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	 for (String date : dayCounts20Ldd.keySet()) {
	     int countIn20Ldd = dayCounts20Ldd.get(date);
	     int countIn40Ldd = dayCounts40Ldd.get(date);
//	     int tuesCountInLdd = (countIn40Ldd * 2) + countIn20Ldd;
	     int tuesCountInLdd = (countIn40Ldd) + countIn20Ldd;
	     dayWiseTuesCountsLdd.put(date, tuesCountInLdd);
	 }

	 lddMapLastWeek.put(dateFormatFcl.format(day11), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day11)));
	 lddMapLastWeek.put(dateFormatFcl.format(day21), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day21)));
	 lddMapLastWeek.put(dateFormatFcl.format(day31), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day31)));
	 lddMapLastWeek.put(dateFormatFcl.format(day41), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day41)));
	 lddMapLastWeek.put(dateFormatFcl.format(day51), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day51)));
	 lddMapLastWeek.put(dateFormatFcl.format(day61), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day61)));
	 lddMapLastWeek.put(dateFormatFcl.format(day71), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day71)));
	 lddMapLastWeek.put("name", "lddMapLastWeek");
	 
	 
	 
	 
//	 Map<String, Object> bufferInUser = new HashMap<>();
//
//	// Initialize counters for each day (Monday to Saturday) for LDD
//	Map<String, Integer> dayCounts20Buffer = new HashMap<>();
//	Map<String, Integer> dayCounts40Buffer = new HashMap<>();
//	Map<String, Integer> dayWiseTuesCountsBuffer = new HashMap<>();
//
//	// Initialize day counts for LDD
//	dayCounts20Buffer.put("Sunday", 0);
//	dayCounts20Buffer.put("Monday", 0);
//	dayCounts20Buffer.put("Tuesday", 0);
//	dayCounts20Buffer.put("Wednesday", 0);
//	dayCounts20Buffer.put("Thursday", 0);
//	dayCounts20Buffer.put("Friday", 0);
//	dayCounts20Buffer.put("Saturday", 0);
//
//	dayCounts40Buffer.put("Sunday", 0);
//	dayCounts40Buffer.put("Monday", 0);
//	dayCounts40Buffer.put("Tuesday", 0);
//	dayCounts40Buffer.put("Wednesday", 0);
//	dayCounts40Buffer.put("Thursday", 0);
//	dayCounts40Buffer.put("Friday", 0);
//	dayCounts40Buffer.put("Saturday", 0);
//
//	dayWiseTuesCountsBuffer.put("Sunday", 0);
//	dayWiseTuesCountsBuffer.put("Monday", 0);
//	dayWiseTuesCountsBuffer.put("Tuesday", 0);
//	dayWiseTuesCountsBuffer.put("Wednesday", 0);
//	dayWiseTuesCountsBuffer.put("Thursday", 0);
//	dayWiseTuesCountsBuffer.put("Friday", 0);
//	dayWiseTuesCountsBuffer.put("Saturday", 0);
//
//	// Loop through Buffer data (similar to lastWeekFclDestuff)
//	SimpleDateFormat dateFormatBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (exportBufferLastWeek != null) {  // Assuming bufferInUserData is the data source you have
//	    for (Object[] row : exportBufferLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	            
//	            try {
////	                Date gateInDate = dateFormatBuffer.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                // Check if the gate-in date is within the Sunday to Saturday week
//	                if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                    // Count based on container size for Buffer
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        dayCounts20Buffer.put(dayName, dayCounts20Buffer.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        dayCounts40Buffer.put(dayName, dayCounts40Buffer.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate tuesCountInBuffer for each day using the formula for Buffer
//	for (String day : dayCounts20Buffer.keySet()) {
//	    int countIn20Buffer = dayCounts20Buffer.get(day);
//	    int countIn40Buffer = dayCounts40Buffer.get(day);
//	    int tuesCountInBuffer = (countIn40Buffer * 2) + countIn20Buffer;
//	    dayWiseTuesCountsBuffer.put(day, tuesCountInBuffer);
//	}
//
//	// Store the results in the map
//	bufferInUser.put("Monday", dayWiseTuesCountsBuffer.get("Monday"));
//	bufferInUser.put("Tuesday", dayWiseTuesCountsBuffer.get("Tuesday"));
//	bufferInUser.put("Wednesday", dayWiseTuesCountsBuffer.get("Wednesday"));
//	bufferInUser.put("Thursday", dayWiseTuesCountsBuffer.get("Thursday"));
//	bufferInUser.put("Friday", dayWiseTuesCountsBuffer.get("Friday"));
//	bufferInUser.put("Saturday", dayWiseTuesCountsBuffer.get("Saturday"));
//	bufferInUser.put("Sunday", dayWiseTuesCountsBuffer.get("Sunday"));
//	bufferInUser.put("name", "bufferInUser"); // Correct name for bufferInUser
//
//
//	
//	
//	
//	
//	Map<String, Object> exportMovementOutLastWeekMap = new HashMap<>();
//
//	// Initialize counters for each day (Monday to Saturday) for Export Movement Out
//	Map<String, Integer> dayCounts20Export = new HashMap<>();
//	Map<String, Integer> dayCounts40Export = new HashMap<>();
//	Map<String, Integer> dayWiseTuesCountsExport = new HashMap<>();
//
//	// Initialize day counts for Export Movement Out
//	dayCounts20Export.put("Sunday", 0);
//	dayCounts20Export.put("Monday", 0);
//	dayCounts20Export.put("Tuesday", 0);
//	dayCounts20Export.put("Wednesday", 0);
//	dayCounts20Export.put("Thursday", 0);
//	dayCounts20Export.put("Friday", 0);
//	dayCounts20Export.put("Saturday", 0);
//
//	dayCounts40Export.put("Sunday", 0);
//	dayCounts40Export.put("Monday", 0);
//	dayCounts40Export.put("Tuesday", 0);
//	dayCounts40Export.put("Wednesday", 0);
//	dayCounts40Export.put("Thursday", 0);
//	dayCounts40Export.put("Friday", 0);
//	dayCounts40Export.put("Saturday", 0);
//
//	dayWiseTuesCountsExport.put("Sunday", 0);
//	dayWiseTuesCountsExport.put("Monday", 0);
//	dayWiseTuesCountsExport.put("Tuesday", 0);
//	dayWiseTuesCountsExport.put("Wednesday", 0);
//	dayWiseTuesCountsExport.put("Thursday", 0);
//	dayWiseTuesCountsExport.put("Friday", 0);
//	dayWiseTuesCountsExport.put("Saturday", 0);
//
//	// Loop through Export Movement Out data (similar to the previous ones)
//	SimpleDateFormat dateFormatExport = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (exportMovementOutLastWeek != null) {  // Assuming exportMovementOutData is the data source you have
//	    for (Object[] row : exportMovementOutLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	            
//	            try {
////	                Date gateInDate = dateFormatExport.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                // Check if the gate-in date is within the Sunday to Saturday week
//	                if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                    // Count based on container size for Export Movement Out
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        dayCounts20Export.put(dayName, dayCounts20Export.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        dayCounts40Export.put(dayName, dayCounts40Export.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate tuesCountInExport for each day using the formula for Export Movement Out
//	for (String day : dayCounts20Export.keySet()) {
//	    int countIn20Export = dayCounts20Export.get(day);
//	    int countIn40Export = dayCounts40Export.get(day);
//	    int tuesCountInExport = (countIn40Export * 2) + countIn20Export;
//	    dayWiseTuesCountsExport.put(day, tuesCountInExport);
//	}
//
//	// Store the results in the map
//	exportMovementOutLastWeekMap.put("Monday", dayWiseTuesCountsExport.get("Monday"));
//	exportMovementOutLastWeekMap.put("Tuesday", dayWiseTuesCountsExport.get("Tuesday"));
//	exportMovementOutLastWeekMap.put("Wednesday", dayWiseTuesCountsExport.get("Wednesday"));
//	exportMovementOutLastWeekMap.put("Thursday", dayWiseTuesCountsExport.get("Thursday"));
//	exportMovementOutLastWeekMap.put("Friday", dayWiseTuesCountsExport.get("Friday"));
//	exportMovementOutLastWeekMap.put("Saturday", dayWiseTuesCountsExport.get("Saturday"));
//	exportMovementOutLastWeekMap.put("Sunday", dayWiseTuesCountsExport.get("Sunday"));
//	exportMovementOutLastWeekMap.put("name", "exportMovementOutLastWeekMap"); // Correct name for exportMovementOut
//
//	
//	
//	
//	
//	
//	
//	Map<String, Object> exportStuffTallyData = new HashMap<>();
//	Map<String, Integer> exportDayCounts20W = new HashMap<>();
//	Map<String, Integer> exportDayCounts40W = new HashMap<>();
//	Map<String, Integer> exportDayWiseTuesCounts = new HashMap<>();
//
//	exportDayCounts20W.put("Sunday", 0);
//	exportDayCounts20W.put("Monday", 0);
//	exportDayCounts20W.put("Tuesday", 0);
//	exportDayCounts20W.put("Wednesday", 0);
//	exportDayCounts20W.put("Thursday", 0);
//	exportDayCounts20W.put("Friday", 0);
//	exportDayCounts20W.put("Saturday", 0);
//
//	exportDayCounts40W.put("Sunday", 0);
//	exportDayCounts40W.put("Monday", 0);
//	exportDayCounts40W.put("Tuesday", 0);
//	exportDayCounts40W.put("Wednesday", 0);
//	exportDayCounts40W.put("Thursday", 0);
//	exportDayCounts40W.put("Friday", 0);
//	exportDayCounts40W.put("Saturday", 0);
//
//	exportDayWiseTuesCounts.put("Sunday", 0);
//	exportDayWiseTuesCounts.put("Monday", 0);
//	exportDayWiseTuesCounts.put("Tuesday", 0);
//	exportDayWiseTuesCounts.put("Wednesday", 0);
//	exportDayWiseTuesCounts.put("Thursday", 0);
//	exportDayWiseTuesCounts.put("Friday", 0);
//	exportDayWiseTuesCounts.put("Saturday", 0);
//
//	SimpleDateFormat dateFormat1Exp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (exportStuffTallyLastWeek != null) {
//	    for (Object[] row : exportStuffTallyLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; 
//	            Date gateInDate = (Date) row[2];
//
//	            try {
////	                Date gateInDate = dateFormat1Exp.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                if (!dayName.isEmpty() && isDateBetween(gateInDate, day7, day1)) {
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        exportDayCounts20W.put(dayName, exportDayCounts20W.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        exportDayCounts40W.put(dayName, exportDayCounts40W.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate exportDayWiseTuesCounts
//	for (String day : exportDayCounts20W.keySet()) {
//	    int countIn20W = exportDayCounts20W.get(day);
//	    int countIn40W = exportDayCounts40W.get(day);
//	    int tuesCountInW = (countIn40W * 2) + countIn20W;
//	    exportDayWiseTuesCounts.put(day, tuesCountInW);
//	}
//
//	
//	exportStuffTallyData.put("Monday", exportDayWiseTuesCounts.get("Monday"));
//	exportStuffTallyData.put("Tuesday", exportDayWiseTuesCounts.get("Tuesday"));
//	exportStuffTallyData.put("Wednesday", exportDayWiseTuesCounts.get("Wednesday"));
//	exportStuffTallyData.put("Thursday", exportDayWiseTuesCounts.get("Thursday"));
//	exportStuffTallyData.put("Friday", exportDayWiseTuesCounts.get("Friday"));
//	exportStuffTallyData.put("Saturday", exportDayWiseTuesCounts.get("Saturday"));
//	exportStuffTallyData.put("Sunday", exportDayWiseTuesCounts.get("Sunday"));
//	exportStuffTallyData.put("name", "EXPORT STUFF TALLY WEEK");
//	
	 
	 Map<String, Object> bufferInUser = new HashMap<>();

		// Initialize counters for each day (Monday to Saturday) for LDD
		Map<String, Integer> dayCounts20Buffer = new HashMap<>();
		Map<String, Integer> dayCounts40Buffer = new HashMap<>();
		Map<String, Integer> dayWiseTuesCountsBuffer = new HashMap<>();


		dayCounts20Buffer.put(dateFormatFcl.format(day1), 0);
		dayCounts20Buffer.put(dateFormatFcl.format(day2), 0);
		dayCounts20Buffer.put(dateFormatFcl.format(day3), 0);
		dayCounts20Buffer.put(dateFormatFcl.format(day4), 0);
		dayCounts20Buffer.put(dateFormatFcl.format(day5), 0);
		    dayCounts20Buffer.put(dateFormatFcl.format(day6), 0);
		    dayCounts20Buffer.put(dateFormatFcl.format(day7), 0);

		    dayCounts40Buffer.put(dateFormatFcl.format(day1), 0);
		    dayCounts40Buffer.put(dateFormatFcl.format(day2), 0);
		    dayCounts40Buffer.put(dateFormatFcl.format(day3), 0);
		    dayCounts40Buffer.put(dateFormatFcl.format(day4), 0);
		    dayCounts40Buffer.put(dateFormatFcl.format(day5), 0);
		    dayCounts40Buffer.put(dateFormatFcl.format(day6), 0);
		    dayCounts40Buffer.put(dateFormatFcl.format(day7), 0);

		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day1), 0);
		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day2), 0);
		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day3), 0);
		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day4), 0);
		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day5), 0);
		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day6), 0);
		    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day7), 0);

		    // Loop through FclDestuff data (just like lastWeekLclLoaded)
		    SimpleDateFormat dateFormat11169 = new SimpleDateFormat("yyyy-MM-dd");
		    if (exportBufferLastWeek != null) {
		        for (Object[] row : exportBufferLastWeek) {
		            if (row != null && row.length > 1) {
		                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

		                try {
		                    // Create a Calendar instance for gate-in date
//		                	Date gateInDate = dateFormat111.parse(gateInDateStr);
		                    Calendar gateInCalendar = Calendar.getInstance();
		                    gateInCalendar.setTime(gateInDate);

		                    // Format gate-in date to match the date format
		                    String gateInDateString = dateFormat11169.format(gateInCalendar.getTime());

		                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
		                    if (isDateBetween(gateInDate, day7, day1)) {
		                        // Count based on container size for FclDestuff
		                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                        	dayCounts20Buffer.put(gateInDateString, dayCounts20Buffer.get(gateInDateString) + 1);
		                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                        	dayCounts40Buffer.put(gateInDateString, dayCounts40Buffer.get(gateInDateString) + 1);
		                        }
		                    }
		                } catch (Exception e) {
		                    System.out.println("Error parsing date: ");
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
		    for (String date : dayCounts20Buffer.keySet()) {
		        int countIn20Fcl = dayCounts20Buffer.get(date);
		        int countIn40Fcl = dayCounts40Buffer.get(date);
//		        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
		        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
		        dayWiseTuesCountsBuffer.put(date, tuesCountInFcl);
		    }

		    // Store the results in the map with dates instead of days
		    bufferInUser.put(dateFormatFcl.format(day1), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day1)));
		    bufferInUser.put(dateFormatFcl.format(day2), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day2)));
		    bufferInUser.put(dateFormatFcl.format(day3), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day3)));
		    bufferInUser.put(dateFormatFcl.format(day4), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day4)));
		    bufferInUser.put(dateFormatFcl.format(day5), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day5)));
		    bufferInUser.put(dateFormatFcl.format(day6), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day6)));
		    bufferInUser.put(dateFormatFcl.format(day7), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day7)));
			bufferInUser.put("name", "bufferInUser"); // Correct name for bufferInUser
		
		
		
		Map<String, Object> exportMovementOutLastWeekMap = new HashMap<>();

		// Initialize counters for each day (Monday to Saturday) for Export Movement Out
		Map<String, Integer> dayCounts20Export = new HashMap<>();
		Map<String, Integer> dayCounts40Export = new HashMap<>();
		Map<String, Integer> dayWiseTuesCountsExport = new HashMap<>();
		
		dayCounts20Export.put(dateFormatFcl.format(day1), 0);
		dayCounts20Export.put(dateFormatFcl.format(day2), 0);
		dayCounts20Export.put(dateFormatFcl.format(day3), 0);
		dayCounts20Export.put(dateFormatFcl.format(day4), 0);
		dayCounts20Export.put(dateFormatFcl.format(day5), 0);
		dayCounts20Export.put(dateFormatFcl.format(day6), 0);
		dayCounts20Export.put(dateFormatFcl.format(day7), 0);

		dayCounts40Export.put(dateFormatFcl.format(day1), 0);
		dayCounts40Export.put(dateFormatFcl.format(day2), 0);
		dayCounts40Export.put(dateFormatFcl.format(day3), 0);
		dayCounts40Export.put(dateFormatFcl.format(day4), 0);
		dayCounts40Export.put(dateFormatFcl.format(day5), 0);
		dayCounts40Export.put(dateFormatFcl.format(day6), 0);
		dayCounts40Export.put(dateFormatFcl.format(day7), 0);

		dayWiseTuesCountsExport.put(dateFormatFcl.format(day1), 0);
		dayWiseTuesCountsExport.put(dateFormatFcl.format(day2), 0);
		dayWiseTuesCountsExport.put(dateFormatFcl.format(day3), 0);
		dayWiseTuesCountsExport.put(dateFormatFcl.format(day4), 0);
		dayWiseTuesCountsExport.put(dateFormatFcl.format(day5), 0);
		dayWiseTuesCountsExport.put(dateFormatFcl.format(day6), 0);
		dayWiseTuesCountsExport.put(dateFormatFcl.format(day7), 0);

		    // Loop through FclDestuff data (just like lastWeekLclLoaded)
		  
		    if (exportMovementOutLastWeek != null) {
		        for (Object[] row : exportMovementOutLastWeek) {
		            if (row != null && row.length > 1) {
		                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

		                try {
		                    // Create a Calendar instance for gate-in date
//		                	Date gateInDate = dateFormat111.parse(gateInDateStr);
		                    Calendar gateInCalendar = Calendar.getInstance();
		                    gateInCalendar.setTime(gateInDate);

		                    // Format gate-in date to match the date format
		                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

		                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
		                    if (isDateBetween(gateInDate, day7, day1)) {
		                        // Count based on container size for FclDestuff
		                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                        	dayCounts20Export.put(gateInDateString, dayCounts20Export.get(gateInDateString) + 1);
		                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                        	dayCounts40Export.put(gateInDateString, dayCounts40Export.get(gateInDateString) + 1);
		                        }
		                    }
		                } catch (Exception e) {
		                    System.out.println("Error parsing date: ");
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
		    for (String date : dayCounts20Export.keySet()) {
		        int countIn20Fcl = dayCounts20Export.get(date);
		        int countIn40Fcl = dayCounts40Export.get(date);
//		        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
		        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
		        dayWiseTuesCountsExport.put(date, tuesCountInFcl);
		    }

		    // Store the results in the map with dates instead of days
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day1), dayWiseTuesCountsExport.get(dateFormatFcl.format(day1)));
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day2), dayWiseTuesCountsExport.get(dateFormatFcl.format(day2)));
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day3), dayWiseTuesCountsExport.get(dateFormatFcl.format(day3)));
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day4), dayWiseTuesCountsExport.get(dateFormatFcl.format(day4)));
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day5), dayWiseTuesCountsExport.get(dateFormatFcl.format(day5)));
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day6), dayWiseTuesCountsExport.get(dateFormatFcl.format(day6)));
		    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day7), dayWiseTuesCountsExport.get(dateFormatFcl.format(day7)));
		    exportMovementOutLastWeekMap.put("name", "exportMovementOutLastWeekMap"); // Correct name for exportMovementOut
		
		
		
		
		
		Map<String, Object> exportStuffTallyData = new HashMap<>();
		Map<String, Integer> exportDayCounts20W = new HashMap<>();
		Map<String, Integer> exportDayCounts40W = new HashMap<>();
		Map<String, Integer> exportDayWiseTuesCounts = new HashMap<>();


		
		exportDayCounts20W.put(dateFormatFcl.format(day1), 0);
		exportDayCounts20W.put(dateFormatFcl.format(day2), 0);
		exportDayCounts20W.put(dateFormatFcl.format(day3), 0);
		exportDayCounts20W.put(dateFormatFcl.format(day4), 0);
		exportDayCounts20W.put(dateFormatFcl.format(day5), 0);
		exportDayCounts20W.put(dateFormatFcl.format(day6), 0);
		exportDayCounts20W.put(dateFormatFcl.format(day7), 0);

		exportDayCounts40W.put(dateFormatFcl.format(day1), 0);
		exportDayCounts40W.put(dateFormatFcl.format(day2), 0);
		exportDayCounts40W.put(dateFormatFcl.format(day3), 0);
		exportDayCounts40W.put(dateFormatFcl.format(day4), 0);
		exportDayCounts40W.put(dateFormatFcl.format(day5), 0);
		exportDayCounts40W.put(dateFormatFcl.format(day6), 0);
		exportDayCounts40W.put(dateFormatFcl.format(day7), 0);

		exportDayWiseTuesCounts.put(dateFormatFcl.format(day1), 0);
		exportDayWiseTuesCounts.put(dateFormatFcl.format(day2), 0);
		exportDayWiseTuesCounts.put(dateFormatFcl.format(day3), 0);
		exportDayWiseTuesCounts.put(dateFormatFcl.format(day4), 0);
		exportDayWiseTuesCounts.put(dateFormatFcl.format(day5), 0);
		exportDayWiseTuesCounts.put(dateFormatFcl.format(day6), 0);
		exportDayWiseTuesCounts.put(dateFormatFcl.format(day7), 0);

		    // Loop through FclDestuff data (just like lastWeekLclLoaded)
		  
		    if (exportStuffTallyLastWeek != null) {
		        for (Object[] row : exportStuffTallyLastWeek) {
		            if (row != null && row.length > 1) {
		                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

		                try {
		                    // Create a Calendar instance for gate-in date
//		                	Date gateInDate = dateFormat111.parse(gateInDateStr);
		                    Calendar gateInCalendar = Calendar.getInstance();
		                    gateInCalendar.setTime(gateInDate);

		                    // Format gate-in date to match the date format
		                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

		                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
		                    if (isDateBetween(gateInDate, day7, day1)) {
		                        // Count based on container size for FclDestuff
		                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                        	exportDayCounts20W.put(gateInDateString, exportDayCounts20W.get(gateInDateString) + 1);
		                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                        	exportDayCounts40W.put(gateInDateString, exportDayCounts40W.get(gateInDateString) + 1);
		                        }
		                    }
		                } catch (Exception e) {
		                    System.out.println("Error parsing date: ");
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
		    for (String date : exportDayCounts20W.keySet()) {
		        int countIn20Fcl = exportDayCounts20W.get(date);
		        int countIn40Fcl = exportDayCounts40W.get(date);
//		        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
		        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
		        exportDayWiseTuesCounts.put(date, tuesCountInFcl);
		    }

		    // Store the results in the map with dates instead of days
		    exportStuffTallyData.put(dateFormatFcl.format(day1), exportDayWiseTuesCounts.get(dateFormatFcl.format(day1)));
		    exportStuffTallyData.put(dateFormatFcl.format(day2), exportDayWiseTuesCounts.get(dateFormatFcl.format(day2)));
		    exportStuffTallyData.put(dateFormatFcl.format(day3), exportDayWiseTuesCounts.get(dateFormatFcl.format(day3)));
		    exportStuffTallyData.put(dateFormatFcl.format(day4), exportDayWiseTuesCounts.get(dateFormatFcl.format(day4)));
		    exportStuffTallyData.put(dateFormatFcl.format(day5), exportDayWiseTuesCounts.get(dateFormatFcl.format(day5)));
		    exportStuffTallyData.put(dateFormatFcl.format(day6), exportDayWiseTuesCounts.get(dateFormatFcl.format(day6)));
		    exportStuffTallyData.put(dateFormatFcl.format(day7), exportDayWiseTuesCounts.get(dateFormatFcl.format(day7)));
		    exportStuffTallyData.put("name", "EXPORT STUFF TALLY WEEK");
		    
	
	Map<String, Object> exportLaddLastWeekData = new HashMap<>();
	

	 Map<String, Integer> dayCounts20 = new HashMap<>();
	    Map<String, Integer> dayCounts40 = new HashMap<>();
	    Map<String, Integer> dayWiseTuesCount = new HashMap<>();

	    dayCounts20.put("Sunday", 0);
	    dayCounts20.put("Monday", 0);
	    dayCounts20.put("Tuesday", 0);
	    dayCounts20.put("Wednesday", 0);
	    dayCounts20.put("Thursday", 0);
	    dayCounts20.put("Friday", 0);
	    dayCounts20.put("Saturday", 0);

	    dayCounts40.put("Sunday", 0);
	    dayCounts40.put("Monday", 0);
	    dayCounts40.put("Tuesday", 0);
	    dayCounts40.put("Wednesday", 0);
	    dayCounts40.put("Thursday", 0);
	    dayCounts40.put("Friday", 0);
	    dayCounts40.put("Saturday", 0);

	    dayWiseTuesCount.put("Sunday", 0);
	    dayWiseTuesCount.put("Monday", 0);
	    dayWiseTuesCount.put("Tuesday", 0);
	    dayWiseTuesCount.put("Wednesday", 0);
	    dayWiseTuesCount.put("Thursday", 0);
	    dayWiseTuesCount.put("Friday", 0);
	    dayWiseTuesCount.put("Saturday", 0);


	    
	    if (exportLDDLastWeek != null) {
	        for (Object[] row : exportLDDLastWeek) {
	            if (row != null && row.length > 1) { // Null check for row and container size
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
	                
	                
//	                String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]
	                	
	                
	                	
//	                System.out.println( " gateInDateStr  :"+gateInDateStr);

////	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]  
	                
	                try {
	                    // Get the day of the week for the gate-in date
//	                	 Date gateInDate = dateFormat1.parse(gateInDateStr);
//	                	Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);
	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...

	                    // Find the corresponding day name
	                    String dayName = "";
	                    switch (dayOfWeek) {
	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
	                        case Calendar.MONDAY: dayName = "Monday"; break;
	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
	                        case Calendar.FRIDAY: dayName = "Friday"; break;
	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
	                        default: break;
	                    }

	                    // Check if the gate-in date is within the Sunday to Saturday week
	                    if (dayName != "" && isDateBetween(gateInDate, day7, day1)) {
	                        // Count based on container size
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20.put(dayName, dayCounts20.get(dayName) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                            dayCounts40.put(dayName, dayCounts40.get(dayName) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInW for each day using the formula
	    for (String day : dayCounts20.keySet()) {
	        int countIn20LDDD = dayCounts20.get(day);
	        int countIn40LDDD = dayCounts40.get(day);
	        int tuesCountInLDDD = (countIn40LDDD * 2) + countIn20LDDD;
	        dayWiseTuesCount.put(day, tuesCountInLDDD);
	    }

	    // Store the results
	    exportLaddLastWeekData.put("Monday", dayWiseTuesCount.get("Monday"));
	    exportLaddLastWeekData.put("Tuesday", dayWiseTuesCount.get("Tuesday"));
	    exportLaddLastWeekData.put("Wednesday", dayWiseTuesCount.get("Wednesday"));
	    exportLaddLastWeekData.put("Thursday", dayWiseTuesCount.get("Thursday"));
	    exportLaddLastWeekData.put("Friday", dayWiseTuesCount.get("Friday"));
	    exportLaddLastWeekData.put("Saturday", dayWiseTuesCount.get("Saturday"));
	    exportLaddLastWeekData.put("Sunday", dayWiseTuesCount.get("Sunday"));
	    exportLaddLastWeekData.put("name", "exportLaddLastWeekData");
	    
	    
	    Map<String, Object> exportBufferContainer = new HashMap<>();
	    int countSeal201 = 0;
	    int countSeal401 = 0;

	    // Process seal cutting data (corrected loop)
	    if (exportBuffer != null) {
	        for (Object[] row : exportBuffer) { // Corrected: use sealCutting instead of joGateIn
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                countSeal201++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                countSeal401++;
	            }
	            exportBufferContainer.put(countCategory, (Integer) exportBufferContainer.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountSeal1 = (countSeal401 * 2) + countSeal201;
	    int totalCountSeal1 =countSeal401 + countSeal201;
	    exportBufferContainer.put("total", totalCountSeal1);
	    exportBufferContainer.put("Tues", tuesCountSeal1);
	    exportBufferContainer.put("name", "BUFFER GATE IN");
	    
	    
	    Map<String, Object> emptyMovementOutMap = new HashMap<>();
	    int countIn201 = 0;
	    int countIn401 = 0;

	    // Process import gate-in data
	    if (emptyMovementOut != null) {
	        for (Object[] row : emptyMovementOut) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[3]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                    countIn201++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                    countIn401++;
	                }
	                emptyMovementOutMap.put(countCategory, (Integer) emptyMovementOutMap.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountIn1 = (countIn401 * 2) + countIn201;
	    int totalCountIn1 =countIn401 + countIn201;
	    emptyMovementOutMap.put("total", totalCountIn1);
	    emptyMovementOutMap.put("Tues", tuesCountIn1);
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
		         
		         BigDecimal cifSum = row[2] != null 
			             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[2] : BigDecimal.valueOf(((Number) row[2]).longValue())) 
			             : BigDecimal.ZERO;
		         
		         
		         BigDecimal area = row[3] != null 
			             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[3] : BigDecimal.valueOf(((Number) row[3]).longValue())) 
			             : BigDecimal.ZERO;

		         noc.put("nocCount", nocCount);
		         noc.put("dutysum", dutysum);
		         noc.put("cifSum", cifSum);
		         noc.put("area", area);
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

		 	        BigDecimal cifSum = row[2] != null 
				             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[2] : BigDecimal.valueOf(((Number) row[2]).longValue())) 
				             : BigDecimal.ZERO;
			         
			         
			         BigDecimal area = row[3] != null 
				             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[3] : BigDecimal.valueOf(((Number) row[3]).longValue())) 
				             : BigDecimal.ZERO;
			         
		 	        inbond.put("nocCount", nocCount);
		 	       inbond.put("dutysum", dutysum);
		 	      inbond.put("cifSum", cifSum);
		 	     inbond.put("area", area);
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

		 	        BigDecimal cifSum = row[2] != null 
				             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[2] : BigDecimal.valueOf(((Number) row[2]).longValue())) 
				             : BigDecimal.ZERO;
			         
			         
			         BigDecimal area = row[3] != null 
				             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[3] : BigDecimal.valueOf(((Number) row[3]).longValue())) 
				             : BigDecimal.ZERO;
			         
		 	        exbond.put("nocCount", nocCount);
		 	       exbond.put("dutysum", dutysum);
		 	      exbond.put("cifSum", cifSum);
		 	     exbond.put("area", area);
		        }
		    }
		    exbond.put("name", "EXBOND DATA");
		    
		    
		    
		    
		    
		    
		    Map<String, Object> custom = new HashMap<>();
		    // Process seal cutting data (corrected loop)
		    if (custoneApproved != null) {
		        for (Object[] row : custoneApproved) { // Corrected: use sealCutting instead of joGateIn
		        	
		 	         BigDecimal approvedDuty = row[0] != null 
		 	             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[0] : BigDecimal.valueOf(((Number) row[0]).longValue())) 
		 	             : BigDecimal.ZERO;

		 	        BigDecimal inBondDuty = row[1] != null 
				             ? (row[1] instanceof BigDecimal ? (BigDecimal) row[1] : BigDecimal.valueOf(((Number) row[1]).longValue())) 
				             : BigDecimal.ZERO;
		 	        
		 	       BigDecimal exBondDuty = row[2] != null 
				             ? (row[2] instanceof BigDecimal ? (BigDecimal) row[2] : BigDecimal.valueOf(((Number) row[2]).longValue())) 
				             : BigDecimal.ZERO;
		 	       
		 	       
		 	      BigDecimal approvedCif = row[3] != null 
			 	             ? (row[3] instanceof BigDecimal ? (BigDecimal) row[3] : BigDecimal.valueOf(((Number) row[3]).longValue())) 
			 	             : BigDecimal.ZERO;

			 	        BigDecimal inBondCif = row[4] != null 
					             ? (row[4] instanceof BigDecimal ? (BigDecimal) row[4] : BigDecimal.valueOf(((Number) row[4]).longValue())) 
					             : BigDecimal.ZERO;
			 	        
			 	       BigDecimal exBondCif = row[5] != null 
					             ? (row[5] instanceof BigDecimal ? (BigDecimal) row[5] : BigDecimal.valueOf(((Number) row[5]).longValue())) 
					             : BigDecimal.ZERO;
			 	       
			 	      BigDecimal approvedArea = row[6] != null 
				 	             ? (row[6] instanceof BigDecimal ? (BigDecimal) row[6] : BigDecimal.valueOf(((Number) row[6]).longValue())) 
				 	             : BigDecimal.ZERO;

				 	        BigDecimal inBondArea = row[7] != null 
						             ? (row[7] instanceof BigDecimal ? (BigDecimal) row[7] : BigDecimal.valueOf(((Number) row[7]).longValue())) 
						             : BigDecimal.ZERO;
				 	        
				 	       BigDecimal exBondArea = row[8] != null 
						             ? (row[8] instanceof BigDecimal ? (BigDecimal) row[8] : BigDecimal.valueOf(((Number) row[8]).longValue())) 
						             : BigDecimal.ZERO;
			         
		 	       
		 	       BigDecimal remaining = approvedDuty.subtract(inBondDuty).add(exBondDuty);
		 	      BigDecimal remainingCif = approvedCif.subtract(inBondCif).add(exBondCif);
		 	     BigDecimal remainingArea = approvedArea.subtract(inBondArea).add(exBondArea);
		 	       
		 	       custom.put("approvedDuty", approvedDuty);
		 	      custom.put("inBondDuty", inBondDuty);
		 	     custom.put("remaining", remaining);
		 	     
		 	     
		 	    custom.put("approvedCif", approvedCif);
		 	      custom.put("inBondCif", inBondCif);
		 	     custom.put("remainingCif", remainingCif);
		 	     
		 	    custom.put("approvedArea", approvedArea);
		 	      custom.put("inBondArea", inBondArea);
		 	     custom.put("remainingArea", remainingArea);
		 	     
		        }
		    }
		    custom.put("name", "custom");
		    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    result.put("fclDestuff", containerCounts);
	    result.put("containerCountsSealCutting", containerCountsSealCutting);
	    result.put("gateIn", containerCountsImpGateIn);
	    result.put("inventory", containerCountsByType);
	    result.put("containerCountsEmptyOut", containerCountsEmptyOut);
	    
	    result.put("lastWeekGateInData",lastWeekGateInData);
	    result.put("lastWeekLclLoadedMap",lastWeekLclLoadedMap);
	    result.put("fclDestuffMap",fclDestuffMap);
	    result.put("lddMapLastWeek",lddMapLastWeek);
	    
	    
	    result.put("exportLDDMap", exportLDDMap);
	    result.put("exportBufferContainer", exportBufferContainer);
	    result.put("emptyMovementOutMap", emptyMovementOutMap);
	    result.put("exportStuffTally", exportStuffTally);
	    
	    result.put("bufferInUser",bufferInUser);
	    result.put("exportMovementOutLastWeekMap",exportMovementOutLastWeekMap);
	    result.put("exportStuffTallyData",exportStuffTallyData);
	    result.put("exportLaddLastWeekData",exportLaddLastWeekData);
	    
	    
	    result.put("noc", noc);
	    result.put("inbond", inbond);
	    result.put("exbond", exbond);
	    result.put("custom", custom);
	    result.put("getBondInventoryMap", getBondInventoryMap);
	    
	    
	   
	    
	   
	    
	    
	    
	    
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
	
	
	
	
	
	
	
	
    private boolean isDateBetween(Date date, Date startDate, Date endDate) {
        return !date.before(startDate) && !date.after(endDate);
    }
    
    
    
    
    
    
    
    public ResponseEntity<Map<String, Map<String, Object>>> getDataForImportDashBoard(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
//		  List<Object[]> fclDestuff = commonReportRepo.findDistinctFCLContainers(companyId, branchId, startDate, endDate);
//    	 List<Object[]> fclLoaded = commonReportRepo.findDistinctFCLGateOutContainers(companyId, branchId, startDate, endDate);
 //   	  List<Object[]> impGateIn = commonReportRepo.findDistinctGateInData(companyId, branchId, startDate, endDate);
  //  	 List<Object[]> loadedInventory = commonReportRepo.getLoadedInventoryData(companyId, branchId, endDate);
  //  	  List<Object[]> emptyOutt = commonReportRepo.findDistinctMTYContainers(companyId, branchId, startDate, endDate);
  //        List<Object[]> lclDestuff = commonReportRepo.findDistinctLCLContainers(companyId, branchId, startDate, endDate);
    	
    	  List<Object[]> fclDestuff = dashboardRepository.findDistinctFCLContainers(companyId, branchId, startDate);
    	  List<Object[]> fclLoaded = dashboardRepository.findDistinctFCLGateOutContainers(companyId, branchId, startDate);
    	  List<Object[]> impGateIn = dashboardRepository.findDistinctGateInData(companyId, branchId, startDate);
    	  List<Object[]> loadedInventory = dashboardRepository.getLoadedInventoryData(companyId, branchId);
	  

	   
	    List<Object[]> emptyOutt = dashboardRepository.findDistinctMTYContainers(companyId, branchId, startDate);
	    List<Object[]> lclDestuff = dashboardRepository.findDistinctLCLContainers(companyId, branchId, startDate);
	    
	    
	    
	    
	    
	    
	   
	    
	    List<Object[]> lastWeekGateIn= dashboardRepository.findDistinctGateInDataOfLastWeek(companyId, branchId);
	    List<Object[]> lastWeekLclLoaded= dashboardRepository.findDistinctFCLGateOutContainersLastWeek(companyId, branchId);
	    List<Object[]> lastWeekFclDestuff= dashboardRepository.findDistinctFCLContainersLstWeek(companyId, branchId);
	    List<Object[]> lddInventoryLastWeek= dashboardRepository.getLoadedInventoryDataLastWeek(companyId, branchId);
	    
	    List<Object[]> emptyOuttLastWeek= dashboardRepository.findDistinctMTYContainersLastWeek(companyId, branchId);
	    List<Object[]> lclDestuffLastWeek= dashboardRepository.findDistinctLCLContainersLastWeek(companyId, branchId);
	    
	    
	   
	    
	    
	    
	    
	    SimpleDateFormat dateFormatFcl = new SimpleDateFormat("yyyy-MM-dd");
	    
	   

	    Map<String, Object> containerCountsByType = new HashMap<>();

	    int count20L = 0;
	    int count40L = 0;
	    int totalL = 0;
	    
	    if (loadedInventory != null) {
	        for (Object[] row : loadedInventory) {
	            // Assuming that row[0] is container size and row[2] is container type
	            String containerSize = (String) row[1]; // Container size (20, 40, etc.)
	            String countCategory = getContainerSizeCategory(containerSize);

	            

	            // Get the map for this container type
	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	            	count20L++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	            	count40L++;
	            }
	            containerCountsByType.put(countCategory, (Integer) containerCountsByType.getOrDefault(countCategory, 0) + 1);
	        
	        }
	    }
	    int tuesCountL = (count40L * 2) + count20L;
	    int totalCountL = count40L + count20L;
	    containerCountsByType.put("total", totalCountL);
	    containerCountsByType.put("Tues", tuesCountL);
	    containerCountsByType.put("name", "inventory Loaded");


	   
	    Map<String, Object> containerCounts = new HashMap<>();
	    int count20 = 0;
	    int count40 = 0;
	    int total = 0;

	    // Process job order data
	    if (fclDestuff != null) {
	        for (Object[] row : fclDestuff) {
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
	    containerCounts.put("name", "FCL Destuff");
	   

	    // Initialize container counts for seal cutting
	    Map<String, Object> containerCountsSealCutting = new HashMap<>();
	    int countSeal20 = 0;
	    int countSeal40 = 0;

	    // Process seal cutting data (corrected loop)
	    if (fclLoaded != null) {
	        for (Object[] row : fclLoaded) { // Corrected: use sealCutting instead of joGateIn
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
	    containerCountsSealCutting.put("name", "FCL LOADED");
	    
	    
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
	    
	    
	    Map<String, Object> containerCountsEmptyOut = new HashMap<>();
	    int countEmpty20 = 0;
	    int countEmpty40 = 0;

	    // Process import gate-in data
	    if (emptyOutt != null) {
	        for (Object[] row : emptyOutt) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                	countEmpty20++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                	countEmpty40++;
	                }
	                containerCountsEmptyOut.put(countCategory, (Integer) containerCountsEmptyOut.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountEmpty = (countEmpty40 * 2) + countEmpty20;
	    int totalCountEmpty =countEmpty40 + countEmpty20;
	    containerCountsEmptyOut.put("total", totalCountEmpty);
	    containerCountsEmptyOut.put("Tues", tuesCountEmpty);
	    containerCountsEmptyOut.put("name", "containerCountsEmptyOut");
	    
	    


	    
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
	    
//	    List<Object[]> lastWeekGateIn = dashboardRepository.findDistinctGateInDataOfLastWeek(companyId, branchId, endDate);
	    Map<String, Object> lastWeekGateInData = new HashMap<>();

	    Date endDateNew = new Date();

        // Initialize calendar with the end date
	    
	    Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(endDateNew);

        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        // Set time to 23:59 for day1
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDateNew);

        // Exclude the current date by moving back one day
        calendar.add(Calendar.DATE, -1);

        // Date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

        // Print day1 (yesterday) with time 23:59
        Date day1 = calendar1.getTime();
//        System.out.println("Date: " + dateFormat.format(day1) + ", day1: " + dayFormat.format(day1));

        // Get the 6 days before yesterday without setting time
        calendar.add(Calendar.DATE, -1);
        Date day2 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day2) + ", Day: " + dayFormat.format(day2));

        calendar.add(Calendar.DATE, -1);
        Date day3 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day3) + ", Day: " + dayFormat.format(day3));

        calendar.add(Calendar.DATE, -1);
        Date day4 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day4) + ", Day: " + dayFormat.format(day4));

        calendar.add(Calendar.DATE, -1);
        Date day5 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day5) + ", Day: " + dayFormat.format(day5));

        calendar.add(Calendar.DATE, -1);
        Date day6 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day6) + ", day6: " + dayFormat.format(day6));

        // Get day7 and reset the time to 00:00
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date day7 = calendar.getTime();
//        System.out.println("Date: " + dateFormat7.format(day7) + ", Day7: " + dayFormat.format(day7));

	    
	    Date endDateNew1 = new Date();

        // Initialize calendar with the end date
	    
	    Calendar calendar11 = Calendar.getInstance();
        calendar11.setTime(endDateNew1);

        calendar11.add(Calendar.DATE, -1);
        calendar11.set(Calendar.HOUR_OF_DAY, 23);
        calendar11.set(Calendar.MINUTE, 59);
        calendar11.set(Calendar.SECOND, 0);
        calendar11.set(Calendar.MILLISECOND, 0);
        // Set time to 23:59 for day1
        
        Calendar calendar111 = Calendar.getInstance();
        calendar111.setTime(endDateNew1);

        // Exclude the current date by moving back one day
        calendar111.add(Calendar.DATE, -1);

        // Print day1 (yesterday) with time 23:59
        Date day11 = calendar11.getTime();
//        System.out.println("Date: " + dateFormat.format(day11) + ", day1: " + dayFormat.format(day11));

        // Get the 6 days before yesterday without setting time
        calendar111.add(Calendar.DATE, -1);
        Date day21 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day21) + ", Day: " + dayFormat.format(day21));

        calendar111.add(Calendar.DATE, -1);
        Date day31 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day31) + ", Day: " + dayFormat.format(day31));

        calendar111.add(Calendar.DATE, -1);
        Date day41 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day41) + ", Day: " + dayFormat.format(day41));

        calendar111.add(Calendar.DATE, -1);
        Date day51 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day51) + ", Day: " + dayFormat.format(day51));

        calendar111.add(Calendar.DATE, -1);
        Date day61 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat.format(day61) + ", day6: " + dayFormat.format(day61));

        // Get day7 and reset the time to 00:00
        calendar111.add(Calendar.DATE, -1);
        calendar111.set(Calendar.HOUR_OF_DAY, 0);
        calendar111.set(Calendar.MINUTE, 0);
        calendar111.set(Calendar.SECOND, 0);
        calendar111.set(Calendar.MILLISECOND, 0);
        Date day71 = calendar111.getTime();
//        System.out.println("Date: " + dateFormat7.format(day71) + ", Day7: " + dayFormat.format(day71));

        
	    // Initialize counters for each day (Monday to Saturday)
	    Map<String, Integer> dayCounts20W = new HashMap<>();
	    Map<String, Integer> dayCounts40W = new HashMap<>();
	    Map<String, Integer> dayWiseTuesCounts = new HashMap<>();

//	    dayCounts20W.put("Sunday", 0);
//	    dayCounts20W.put("Monday", 0);
//	    dayCounts20W.put("Tuesday", 0);
//	    dayCounts20W.put("Wednesday", 0);
//	    dayCounts20W.put("Thursday", 0);
//	    dayCounts20W.put("Friday", 0);
//	    dayCounts20W.put("Saturday", 0);
//
//	    dayCounts40W.put("Sunday", 0);
//	    dayCounts40W.put("Monday", 0);
//	    dayCounts40W.put("Tuesday", 0);
//	    dayCounts40W.put("Wednesday", 0);
//	    dayCounts40W.put("Thursday", 0);
//	    dayCounts40W.put("Friday", 0);
//	    dayCounts40W.put("Saturday", 0);
//
//	    dayWiseTuesCounts.put("Sunday", 0);
//	    dayWiseTuesCounts.put("Monday", 0);
//	    dayWiseTuesCounts.put("Tuesday", 0);
//	    dayWiseTuesCounts.put("Wednesday", 0);
//	    dayWiseTuesCounts.put("Thursday", 0);
//	    dayWiseTuesCounts.put("Friday", 0);
//	    dayWiseTuesCounts.put("Saturday", 0);
//
//
//	    
//	    SimpleDateFormat
//    	dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Adjust this pattern as per the format in your data
//	    if (lastWeekGateIn != null) {
//	        for (Object[] row : lastWeekGateIn) {
//	            if (row != null && row.length > 1) { // Null check for row and container size
//	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	                String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	                
//	                
////	                System.out.println( " gateInDateStr  :"+gateInDateStr);;
//
//////	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]  
//	                
//	                try {
//	                    // Get the day of the week for the gate-in date
//	                	 Date gateInDate = dateFormat1.parse(gateInDateStr);
//	                    Calendar gateInCalendar = Calendar.getInstance();
//	                    gateInCalendar.setTime(gateInDate);
//	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                    // Find the corresponding day name
//	                    String dayName = "";
//	                    switch (dayOfWeek) {
//	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                        case Calendar.MONDAY: dayName = "Monday"; break;
//	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                        case Calendar.FRIDAY: dayName = "Friday"; break;
//	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                        default: break;
//	                    }
//
//	                    // Check if the gate-in date is within the Sunday to Saturday week
//	                    if (dayName != "" && isDateBetween(gateInDate, day7, day1)) {
//	                        // Count based on container size
//	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                            dayCounts20W.put(dayName, dayCounts20W.get(dayName) + 1);
//	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                            dayCounts40W.put(dayName, dayCounts40W.get(dayName) + 1);
//	                        }
//	                    }
//	                } catch (Exception e) {
//	                    System.out.println("Error parsing date: ");
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	    }
//
//	    // Calculate tuesCountInW for each day using the formula
//	    for (String day : dayCounts20W.keySet()) {
//	        int countIn20W = dayCounts20W.get(day);
//	        int countIn40W = dayCounts40W.get(day);
//	        int tuesCountInW = (countIn40W * 2) + countIn20W;
//	        dayWiseTuesCounts.put(day, tuesCountInW);
//	    }
//
//	    // Store the results
//	    lastWeekGateInData.put("Monday", dayWiseTuesCounts.get("Monday"));
//	    lastWeekGateInData.put("Tuesday", dayWiseTuesCounts.get("Tuesday"));
//	    lastWeekGateInData.put("Wednesday", dayWiseTuesCounts.get("Wednesday"));
//	    lastWeekGateInData.put("Thursday", dayWiseTuesCounts.get("Thursday"));
//	    lastWeekGateInData.put("Friday", dayWiseTuesCounts.get("Friday"));
//	    lastWeekGateInData.put("Saturday", dayWiseTuesCounts.get("Saturday"));
//	    lastWeekGateInData.put("Sunday", dayWiseTuesCounts.get("Sunday"));
//	    lastWeekGateInData.put("name", "IMPORT GATE IN WEEK");
	    
	    
	    dayCounts20W.put(dateFormatFcl.format(day11), 0);
	    dayCounts20W.put(dateFormatFcl.format(day21), 0);
	    dayCounts20W.put(dateFormatFcl.format(day31), 0);
	    dayCounts20W.put(dateFormatFcl.format(day41), 0);
	    dayCounts20W.put(dateFormatFcl.format(day51), 0);
	    dayCounts20W.put(dateFormatFcl.format(day61), 0);
	    dayCounts20W.put(dateFormatFcl.format(day71), 0);

	    dayCounts40W.put(dateFormatFcl.format(day11), 0);
	    dayCounts40W.put(dateFormatFcl.format(day21), 0);
	    dayCounts40W.put(dateFormatFcl.format(day31), 0);
	    dayCounts40W.put(dateFormatFcl.format(day41), 0);
	    dayCounts40W.put(dateFormatFcl.format(day51), 0);
	    dayCounts40W.put(dateFormatFcl.format(day61), 0);
	    dayCounts40W.put(dateFormatFcl.format(day71), 0);

	    dayWiseTuesCounts.put(dateFormatFcl.format(day11), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day21), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day31), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day41), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day51), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day61), 0);
	    dayWiseTuesCounts.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (lastWeekGateIn != null) {
	        for (Object[] row : lastWeekGateIn) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                	Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20W.put(gateInDateString, dayCounts20W.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40W.put(gateInDateString, dayCounts40W.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20W.keySet()) {
	        int countIn20Fcl = dayCounts20W.get(date);
	        int countIn40Fcl = dayCounts40W.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCounts.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    lastWeekGateInData.put(dateFormatFcl.format(day11), dayWiseTuesCounts.get(dateFormatFcl.format(day11)));
	    lastWeekGateInData.put(dateFormatFcl.format(day21), dayWiseTuesCounts.get(dateFormatFcl.format(day21)));
	    lastWeekGateInData.put(dateFormatFcl.format(day31), dayWiseTuesCounts.get(dateFormatFcl.format(day31)));
	    lastWeekGateInData.put(dateFormatFcl.format(day41), dayWiseTuesCounts.get(dateFormatFcl.format(day41)));
	    lastWeekGateInData.put(dateFormatFcl.format(day51), dayWiseTuesCounts.get(dateFormatFcl.format(day51)));
	    lastWeekGateInData.put(dateFormatFcl.format(day61), dayWiseTuesCounts.get(dateFormatFcl.format(day61)));
	    lastWeekGateInData.put(dateFormatFcl.format(day71), dayWiseTuesCounts.get(dateFormatFcl.format(day71)));
	    lastWeekGateInData.put("name", "IMPORT GATE IN WEEK");
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    Map<String, Object> lastWeekLclLoadedMap = new HashMap<>();
	    
	    // Initialize counters for each day (Monday to Saturday)
	    Map<String, Integer> dayCounts20W1 = new HashMap<>();
	    Map<String, Integer> dayCounts40W1 = new HashMap<>();
	    Map<String, Integer> dayWiseTuesCounts1 = new HashMap<>();

//	    dayCounts20W1.put("Sunday", 0);
//	    dayCounts20W1.put("Monday", 0);
//	    dayCounts20W1.put("Tuesday", 0);
//	    dayCounts20W1.put("Wednesday", 0);
//	    dayCounts20W1.put("Thursday", 0);
//	    dayCounts20W1.put("Friday", 0);
//	    dayCounts20W1.put("Saturday", 0);
//
//	    dayCounts40W1.put("Sunday", 0);
//	    dayCounts40W1.put("Monday", 0);
//	    dayCounts40W1.put("Tuesday", 0);
//	    dayCounts40W1.put("Wednesday", 0);
//	    dayCounts40W1.put("Thursday", 0);
//	    dayCounts40W1.put("Friday", 0);
//	    dayCounts40W1.put("Saturday", 0);
//
//	    dayWiseTuesCounts1.put("Sunday", 0);
//	    dayWiseTuesCounts1.put("Monday", 0);
//	    dayWiseTuesCounts1.put("Tuesday", 0);
//	    dayWiseTuesCounts1.put("Wednesday", 0);
//	    dayWiseTuesCounts1.put("Thursday", 0);
//	    dayWiseTuesCounts1.put("Friday", 0);
//	    dayWiseTuesCounts1.put("Saturday", 0);
//
//
//	    
//	    SimpleDateFormat
//    	dateFormat11 = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Adjust this pattern as per the format in your data
//	    if (lastWeekLclLoaded != null) {
//	        for (Object[] row : lastWeekLclLoaded) {
//	            if (row != null && row.length > 1) { // Null check for row and container size
//	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	                
//	                
////	                System.out.println( " gateInDateStr  :"+gateInDateStr);;
//
//////	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]  
//	                
//	                try {
//	                    // Get the day of the week for the gate-in date
////	                	 Date gateInDate = dateFormat11.parse(gateInDateStr);
//	                    Calendar gateInCalendar = Calendar.getInstance();
//	                    gateInCalendar.setTime(gateInDate);
//	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                    // Find the corresponding day name
//	                    String dayName = "";
//	                    switch (dayOfWeek) {
//	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                        case Calendar.MONDAY: dayName = "Monday"; break;
//	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                        case Calendar.FRIDAY: dayName = "Friday"; break;
//	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                        default: break;
//	                    }
//
//	                    // Check if the gate-in date is within the Sunday to Saturday week
//	                    if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                        // Count based on container size
//	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                            dayCounts20W1.put(dayName, dayCounts20W1.get(dayName) + 1);
//	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                            dayCounts40W1.put(dayName, dayCounts40W1.get(dayName) + 1);
//	                        }
//	                    }
//	                } catch (Exception e) {
//	                    System.out.println("Error parsing date: ");
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	    }
//
//	    // Calculate tuesCountInW for each day using the formula
//	    for (String day : dayCounts20W1.keySet()) {
//	        int countIn20W = dayCounts20W1.get(day);
//	        int countIn40W = dayCounts40W1.get(day);
//	        int tuesCountInW = (countIn40W * 2) + countIn20W;
//	        dayWiseTuesCounts1.put(day, tuesCountInW);
//	    }
//
//	    // Store the results
//	    lastWeekLclLoadedMap.put("Monday", dayWiseTuesCounts1.get("Monday"));
//	    lastWeekLclLoadedMap.put("Tuesday", dayWiseTuesCounts1.get("Tuesday"));
//	    lastWeekLclLoadedMap.put("Wednesday", dayWiseTuesCounts1.get("Wednesday"));
//	    lastWeekLclLoadedMap.put("Thursday", dayWiseTuesCounts1.get("Thursday"));
//	    lastWeekLclLoadedMap.put("Friday", dayWiseTuesCounts1.get("Friday"));
//	    lastWeekLclLoadedMap.put("Saturday", dayWiseTuesCounts1.get("Saturday"));
//	    lastWeekLclLoadedMap.put("Sunday", dayWiseTuesCounts1.get("Sunday"));
//	    lastWeekLclLoadedMap.put("name", "lastWeekLclLoadedMap");
	    
	    
	    dayCounts20W1.put(dateFormatFcl.format(day11), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day21), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day31), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day41), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day51), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day61), 0);
	    dayCounts20W1.put(dateFormatFcl.format(day71), 0);

	    dayCounts40W1.put(dateFormatFcl.format(day11), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day21), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day31), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day41), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day51), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day61), 0);
	    dayCounts40W1.put(dateFormatFcl.format(day71), 0);

	    dayWiseTuesCounts1.put(dateFormatFcl.format(day11), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day21), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day31), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day41), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day51), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day61), 0);
	    dayWiseTuesCounts1.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (lastWeekLclLoaded != null) {
	        for (Object[] row : lastWeekLclLoaded) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20W1.put(gateInDateString, dayCounts20W1.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40W1.put(gateInDateString, dayCounts40W1.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20W1.keySet()) {
	        int countIn20Fcl = dayCounts20W1.get(date);
	        int countIn40Fcl = dayCounts40W1.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCounts1.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day11), dayWiseTuesCounts1.get(dateFormatFcl.format(day11)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day21), dayWiseTuesCounts1.get(dateFormatFcl.format(day21)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day31), dayWiseTuesCounts1.get(dateFormatFcl.format(day31)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day41), dayWiseTuesCounts1.get(dateFormatFcl.format(day41)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day51), dayWiseTuesCounts1.get(dateFormatFcl.format(day51)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day61), dayWiseTuesCounts1.get(dateFormatFcl.format(day61)));
	    lastWeekLclLoadedMap.put(dateFormatFcl.format(day71), dayWiseTuesCounts1.get(dateFormatFcl.format(day71)));
	    lastWeekLclLoadedMap.put("name", "lastWeekLclLoadedMap");


	    
	    

	    
	 // Initialize map for FclDestuff
	    Map<String, Object> fclDestuffMap = new HashMap<>();
//
//	    // Initialize counters for each day (Monday to Saturday) for FclDestuff
//	    Map<String, Integer> dayCounts20Fcl = new HashMap<>();
//	    Map<String, Integer> dayCounts40Fcl = new HashMap<>();
//	    Map<String, Integer> dayWiseTuesCountsFcl = new HashMap<>();
//
//	    // Initialize day counts for FclDestuff
//	    dayCounts20Fcl.put("Sunday", 0);
//	    dayCounts20Fcl.put("Monday", 0);
//	    dayCounts20Fcl.put("Tuesday", 0);
//	    dayCounts20Fcl.put("Wednesday", 0);
//	    dayCounts20Fcl.put("Thursday", 0);
//	    dayCounts20Fcl.put("Friday", 0);
//	    dayCounts20Fcl.put("Saturday", 0);
//
//	    dayCounts40Fcl.put("Sunday", 0);
//	    dayCounts40Fcl.put("Monday", 0);
//	    dayCounts40Fcl.put("Tuesday", 0);
//	    dayCounts40Fcl.put("Wednesday", 0);
//	    dayCounts40Fcl.put("Thursday", 0);
//	    dayCounts40Fcl.put("Friday", 0);
//	    dayCounts40Fcl.put("Saturday", 0);
//
//	    dayWiseTuesCountsFcl.put("Sunday", 0);
//	    dayWiseTuesCountsFcl.put("Monday", 0);
//	    dayWiseTuesCountsFcl.put("Tuesday", 0);
//	    dayWiseTuesCountsFcl.put("Wednesday", 0);
//	    dayWiseTuesCountsFcl.put("Thursday", 0);
//	    dayWiseTuesCountsFcl.put("Friday", 0);
//	    dayWiseTuesCountsFcl.put("Saturday", 0);
//
//	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	    if (lastWeekFclDestuff != null) {
//	        for (Object[] row : lastWeekFclDestuff) {
//	            if (row != null && row.length > 1) {
//	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	                
//	                try {
////	                    Date gateInDate = dateFormat111.parse(gateInDateStr);
//	                    Calendar gateInCalendar = Calendar.getInstance();
//	                    gateInCalendar.setTime(gateInDate);
//	                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                    String dayName = "";
//	                    switch (dayOfWeek) {
//	                        case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                        case Calendar.MONDAY: dayName = "Monday"; break;
//	                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                        case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                        case Calendar.FRIDAY: dayName = "Friday"; break;
//	                        case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                        default: break;
//	                    }
//
//	                    // Check if the gate-in date is within the Sunday to Saturday week
//	                    if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                        // Count based on container size for FclDestuff
//	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                            dayCounts20Fcl.put(dayName, dayCounts20Fcl.get(dayName) + 1);
//	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                            dayCounts40Fcl.put(dayName, dayCounts40Fcl.get(dayName) + 1);
//	                        }
//	                    }
//	                } catch (Exception e) {
//	                    System.out.println("Error parsing date: ");
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	    }
//
//	    // Calculate tuesCountInW for each day using the formula for FclDestuff
//	    for (String day : dayCounts20Fcl.keySet()) {
//	        int countIn20Fcl = dayCounts20Fcl.get(day);
//	        int countIn40Fcl = dayCounts40Fcl.get(day);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
//	        dayWiseTuesCountsFcl.put(day, tuesCountInFcl);
//	    }
//
//	    // Store the results in the map
//	    fclDestuffMap.put("Monday", dayWiseTuesCountsFcl.get("Monday"));
//	    fclDestuffMap.put("Tuesday", dayWiseTuesCountsFcl.get("Tuesday"));
//	    fclDestuffMap.put("Wednesday", dayWiseTuesCountsFcl.get("Wednesday"));
//	    fclDestuffMap.put("Thursday", dayWiseTuesCountsFcl.get("Thursday"));
//	    fclDestuffMap.put("Friday", dayWiseTuesCountsFcl.get("Friday"));
//	    fclDestuffMap.put("Saturday", dayWiseTuesCountsFcl.get("Saturday"));
//	    fclDestuffMap.put("Sunday", dayWiseTuesCountsFcl.get("Sunday"));
//	    fclDestuffMap.put("name", "fclDestuffMap");

	    
	 // Initialize counters for each date (last 7 days)
	    Map<String, Integer> dateCounts20Fcl = new HashMap<>();
	    Map<String, Integer> dateCounts40Fcl = new HashMap<>();
	    Map<String, Integer> dateWiseTuesCountsFcl = new HashMap<>();

	    // Initialize date counts for FclDestuff with last 7 dates (from day1 to day7)
	  
	    dateCounts20Fcl.put(dateFormatFcl.format(day11), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day21), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day31), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day41), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day51), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day61), 0);
	    dateCounts20Fcl.put(dateFormatFcl.format(day71), 0);

	    dateCounts40Fcl.put(dateFormatFcl.format(day11), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day21), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day31), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day41), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day51), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day61), 0);
	    dateCounts40Fcl.put(dateFormatFcl.format(day71), 0);

	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day11), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day21), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day31), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day41), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day51), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day61), 0);
	    dateWiseTuesCountsFcl.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (lastWeekFclDestuff != null) {
	        for (Object[] row : lastWeekFclDestuff) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                            dateCounts20Fcl.put(gateInDateString, dateCounts20Fcl.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                            dateCounts40Fcl.put(gateInDateString, dateCounts40Fcl.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dateCounts20Fcl.keySet()) {
	        int countIn20Fcl = dateCounts20Fcl.get(date);
	        int countIn40Fcl = dateCounts40Fcl.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dateWiseTuesCountsFcl.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    fclDestuffMap.put(dateFormatFcl.format(day11), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day11)));
	    fclDestuffMap.put(dateFormatFcl.format(day21), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day21)));
	    fclDestuffMap.put(dateFormatFcl.format(day31), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day31)));
	    fclDestuffMap.put(dateFormatFcl.format(day41), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day41)));
	    fclDestuffMap.put(dateFormatFcl.format(day51), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day51)));
	    fclDestuffMap.put(dateFormatFcl.format(day61), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day61)));
	    fclDestuffMap.put(dateFormatFcl.format(day71), dateWiseTuesCountsFcl.get(dateFormatFcl.format(day71)));

	    // Adding name to the map
	    fclDestuffMap.put("name", "fclDestuffMap");

	    
	    
	    Map<String, Object> lddMapLastWeek = new HashMap<>();

	 // Initialize counters for each day (Monday to Saturday) for LDD
	 Map<String, Integer> dayCounts20Ldd = new HashMap<>();
	 Map<String, Integer> dayCounts40Ldd = new HashMap<>();
	 Map<String, Integer> dayWiseTuesCountsLdd = new HashMap<>();

	 // Initialize day counts for LDD
	 
	 dayCounts20Ldd.put(dateFormatFcl.format(day11), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day21), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day31), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day41), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day51), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day61), 0);
	 dayCounts20Ldd.put(dateFormatFcl.format(day71), 0);

	 dayCounts40Ldd.put(dateFormatFcl.format(day11), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day21), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day31), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day41), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day51), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day61), 0);
	 dayCounts40Ldd.put(dateFormatFcl.format(day71), 0);

	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day11), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day21), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day31), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day41), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day51), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day61), 0);
	 dayWiseTuesCountsLdd.put(dateFormatFcl.format(day71), 0);
	    
//	 dayCounts20Ldd.put("Sunday", 0);
//	 dayCounts20Ldd.put("Monday", 0);
//	 dayCounts20Ldd.put("Tuesday", 0);
//	 dayCounts20Ldd.put("Wednesday", 0);
//	 dayCounts20Ldd.put("Thursday", 0);
//	 dayCounts20Ldd.put("Friday", 0);
//	 dayCounts20Ldd.put("Saturday", 0);
//
//	 dayCounts40Ldd.put("Sunday", 0);
//	 dayCounts40Ldd.put("Monday", 0);
//	 dayCounts40Ldd.put("Tuesday", 0);
//	 dayCounts40Ldd.put("Wednesday", 0);
//	 dayCounts40Ldd.put("Thursday", 0);
//	 dayCounts40Ldd.put("Friday", 0);
//	 dayCounts40Ldd.put("Saturday", 0);
//
//	 dayWiseTuesCountsLdd.put("Sunday", 0);
//	 dayWiseTuesCountsLdd.put("Monday", 0);
//	 dayWiseTuesCountsLdd.put("Tuesday", 0);
//	 dayWiseTuesCountsLdd.put("Wednesday", 0);
//	 dayWiseTuesCountsLdd.put("Thursday", 0);
//	 dayWiseTuesCountsLdd.put("Friday", 0);
//	 dayWiseTuesCountsLdd.put("Saturday", 0);

	 // Loop through LDD data (similar to lastWeekFclDestuff)
//	 SimpleDateFormat dateFormatLdd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 
	 
	 if (lddInventoryLastWeek != null) {
	        for (Object[] row : lddInventoryLastWeek) {
	            if (row != null && row.length > 1) {
	            	 String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		             String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                	 Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                            dateCounts20Fcl.put(gateInDateString, dateCounts20Fcl.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                            dateCounts40Fcl.put(gateInDateString, dateCounts40Fcl.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	 
//	 if (lddInventoryLastWeek != null) {
//	     for (Object[] row : lddInventoryLastWeek) {
//	         if (row != null && row.length > 1) {
//	             String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	             String gateInDateStr = (String) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	             
//	             try {
//	                 Date gateInDate = dateFormat111.parse(gateInDateStr);
//	                 Calendar gateInCalendar = Calendar.getInstance();
//	                 gateInCalendar.setTime(gateInDate);
//	                 int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                 String dayName = "";
//	                 switch (dayOfWeek) {
//	                     case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                     case Calendar.MONDAY: dayName = "Monday"; break;
//	                     case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                     case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                     case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                     case Calendar.FRIDAY: dayName = "Friday"; break;
//	                     case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                     default: break;
//	                 }
//
//	                 // Check if the gate-in date is within the Sunday to Saturday week
//	                 if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                     // Count based on container size for LDD
//	                     if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                    	 dayCounts20Ldd.put(dayName, dayCounts20Ldd.get(dayName) + 1);
//	                     } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                         dayCounts40Ldd.put(dayName, dayCounts40Ldd.get(dayName) + 1);
//	                     }
//	                 }
//	             } catch (Exception e) {
//	                 System.out.println("Error parsing date: ");
//	                 e.printStackTrace();
//	             }
//	         }
//	     }
//	 }

	 // Calculate tuesCountInLdd for each day using the formula for LDD
	 for (String date : dayCounts20Ldd.keySet()) {
	     int countIn20Ldd = dayCounts20Ldd.get(date);
	     int countIn40Ldd = dayCounts40Ldd.get(date);
//	     int tuesCountInLdd = (countIn40Ldd * 2) + countIn20Ldd;
	     int tuesCountInLdd = (countIn40Ldd) + countIn20Ldd;
	     dayWiseTuesCountsLdd.put(date, tuesCountInLdd);
	 }

	 lddMapLastWeek.put(dateFormatFcl.format(day11), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day11)));
	 lddMapLastWeek.put(dateFormatFcl.format(day21), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day21)));
	 lddMapLastWeek.put(dateFormatFcl.format(day31), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day31)));
	 lddMapLastWeek.put(dateFormatFcl.format(day41), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day41)));
	 lddMapLastWeek.put(dateFormatFcl.format(day51), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day51)));
	 lddMapLastWeek.put(dateFormatFcl.format(day61), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day61)));
	 lddMapLastWeek.put(dateFormatFcl.format(day71), dayWiseTuesCountsLdd.get(dateFormatFcl.format(day71)));
	 lddMapLastWeek.put("name", "lddMapLastWeek");
	 
	 
	 // Store the results in the map
//	 lddMapLastWeek.put("Monday", dayWiseTuesCountsLdd.get("Monday"));
//	 lddMapLastWeek.put("Tuesday", dayWiseTuesCountsLdd.get("Tuesday"));
//	 lddMapLastWeek.put("Wednesday", dayWiseTuesCountsLdd.get("Wednesday"));
//	 lddMapLastWeek.put("Thursday", dayWiseTuesCountsLdd.get("Thursday"));
//	 lddMapLastWeek.put("Friday", dayWiseTuesCountsLdd.get("Friday"));
//	 lddMapLastWeek.put("Saturday", dayWiseTuesCountsLdd.get("Saturday"));
//	 lddMapLastWeek.put("Sunday", dayWiseTuesCountsLdd.get("Sunday"));
//	 lddMapLastWeek.put("name", "lddMapLastWeek");
	 
	 
	 
	 
	Map<String, Object> bufferInUser = new HashMap<>();

	// Initialize counters for each day (Monday to Saturday) for LDD
	Map<String, Integer> dayCounts20Buffer = new HashMap<>();
	Map<String, Integer> dayCounts40Buffer = new HashMap<>();
	Map<String, Integer> dayWiseTuesCountsBuffer = new HashMap<>();

	// Initialize day counts for LDD
//	dayCounts20Buffer.put("Sunday", 0);
//	dayCounts20Buffer.put("Monday", 0);
//	dayCounts20Buffer.put("Tuesday", 0);
//	dayCounts20Buffer.put("Wednesday", 0);
//	dayCounts20Buffer.put("Thursday", 0);
//	dayCounts20Buffer.put("Friday", 0);
//	dayCounts20Buffer.put("Saturday", 0);
//
//	dayCounts40Buffer.put("Sunday", 0);
//	dayCounts40Buffer.put("Monday", 0);
//	dayCounts40Buffer.put("Tuesday", 0);
//	dayCounts40Buffer.put("Wednesday", 0);
//	dayCounts40Buffer.put("Thursday", 0);
//	dayCounts40Buffer.put("Friday", 0);
//	dayCounts40Buffer.put("Saturday", 0);
//
//	dayWiseTuesCountsBuffer.put("Sunday", 0);
//	dayWiseTuesCountsBuffer.put("Monday", 0);
//	dayWiseTuesCountsBuffer.put("Tuesday", 0);
//	dayWiseTuesCountsBuffer.put("Wednesday", 0);
//	dayWiseTuesCountsBuffer.put("Thursday", 0);
//	dayWiseTuesCountsBuffer.put("Friday", 0);
//	dayWiseTuesCountsBuffer.put("Saturday", 0);
//
//	// Loop through Buffer data (similar to lastWeekFclDestuff)
//	SimpleDateFormat dateFormatBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (emptyOuttLastWeek != null) {  // Assuming bufferInUserData is the data source you have
//	    for (Object[] row : emptyOuttLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	            
//	            try {
////	                Date gateInDate = dateFormatBuffer.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                // Check if the gate-in date is within the Sunday to Saturday week
//	                if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                    // Count based on container size for Buffer
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        dayCounts20Buffer.put(dayName, dayCounts20Buffer.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        dayCounts40Buffer.put(dayName, dayCounts40Buffer.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate tuesCountInBuffer for each day using the formula for Buffer
//	for (String day : dayCounts20Buffer.keySet()) {
//	    int countIn20Buffer = dayCounts20Buffer.get(day);
//	    int countIn40Buffer = dayCounts40Buffer.get(day);
//	    int tuesCountInBuffer = (countIn40Buffer * 2) + countIn20Buffer;
//	    dayWiseTuesCountsBuffer.put(day, tuesCountInBuffer);
//	}
//
//	// Store the results in the map
//	bufferInUser.put("Monday", dayWiseTuesCountsBuffer.get("Monday"));
//	bufferInUser.put("Tuesday", dayWiseTuesCountsBuffer.get("Tuesday"));
//	bufferInUser.put("Wednesday", dayWiseTuesCountsBuffer.get("Wednesday"));
//	bufferInUser.put("Thursday", dayWiseTuesCountsBuffer.get("Thursday"));
//	bufferInUser.put("Friday", dayWiseTuesCountsBuffer.get("Friday"));
//	bufferInUser.put("Saturday", dayWiseTuesCountsBuffer.get("Saturday"));
//	bufferInUser.put("Sunday", dayWiseTuesCountsBuffer.get("Sunday"));
//	bufferInUser.put("name", "bufferInUser"); // Correct name for bufferInUser


	dayCounts20Buffer.put(dateFormatFcl.format(day11), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day21), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day31), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day41), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day51), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day61), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day71), 0);

	dayCounts40Buffer.put(dateFormatFcl.format(day11), 0);
	dayCounts40Buffer.put(dateFormatFcl.format(day21), 0);
	dayCounts40Buffer.put(dateFormatFcl.format(day31), 0);
	dayCounts40Buffer.put(dateFormatFcl.format(day41), 0);
	dayCounts40Buffer.put(dateFormatFcl.format(day51), 0);
	dayCounts40Buffer.put(dateFormatFcl.format(day61), 0);
	dayCounts40Buffer.put(dateFormatFcl.format(day71), 0);

	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day11), 0);
	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day21), 0);
	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day31), 0);
	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day41), 0);
	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day51), 0);
	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day61), 0);
	dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day71), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
//	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (emptyOuttLastWeek != null) {
	        for (Object[] row : emptyOuttLastWeek) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20Buffer.put(gateInDateString, dayCounts20Buffer.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40Buffer.put(gateInDateString, dayCounts40Buffer.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20Buffer.keySet()) {
	        int countIn20Fcl = dayCounts20Buffer.get(date);
	        int countIn40Fcl = dayCounts40Buffer.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCountsBuffer.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    bufferInUser.put(dateFormatFcl.format(day11), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day11)));
	    bufferInUser.put(dateFormatFcl.format(day21), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day21)));
	    bufferInUser.put(dateFormatFcl.format(day31), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day31)));
	    bufferInUser.put(dateFormatFcl.format(day41), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day41)));
	    bufferInUser.put(dateFormatFcl.format(day51), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day51)));
	    bufferInUser.put(dateFormatFcl.format(day61), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day61)));
	    bufferInUser.put(dateFormatFcl.format(day71), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day71)));
	    bufferInUser.put("name", "bufferInUser"); // Correct name for bufferInUser
	    
	
	Map<String, Object> lclDestuffWeekData = new HashMap<>();

	// Initialize counters for each day (Monday to Saturday) for LDD
	Map<String, Integer> dayCounts20Lcl = new HashMap<>();
	Map<String, Integer> dayCounts40Lcl = new HashMap<>();
	Map<String, Integer> dayWiseTuesCountsLcl = new HashMap<>();

	// Initialize day counts for LDD
//	dayCounts20Lcl.put("Sunday", 0);
//	dayCounts20Lcl.put("Monday", 0);
//	dayCounts20Lcl.put("Tuesday", 0);
//	dayCounts20Lcl.put("Wednesday", 0);
//	dayCounts20Lcl.put("Thursday", 0);
//	dayCounts20Lcl.put("Friday", 0);
//	dayCounts20Lcl.put("Saturday", 0);
//
//	dayCounts40Lcl.put("Sunday", 0);
//	dayCounts40Lcl.put("Monday", 0);
//	dayCounts40Lcl.put("Tuesday", 0);
//	dayCounts40Lcl.put("Wednesday", 0);
//	dayCounts40Lcl.put("Thursday", 0);
//	dayCounts40Lcl.put("Friday", 0);
//	dayCounts40Lcl.put("Saturday", 0);
//
//	dayWiseTuesCountsLcl.put("Sunday", 0);
//	dayWiseTuesCountsLcl.put("Monday", 0);
//	dayWiseTuesCountsLcl.put("Tuesday", 0);
//	dayWiseTuesCountsLcl.put("Wednesday", 0);
//	dayWiseTuesCountsLcl.put("Thursday", 0);
//	dayWiseTuesCountsLcl.put("Friday", 0);
//	dayWiseTuesCountsLcl.put("Saturday", 0);

	// Loop through Buffer data (similar to lastWeekFclDestuff)
//	SimpleDateFormat dateFormatBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (lclDestuffLastWeek != null) {  // Assuming bufferInUserData is the data source you have
//	    for (Object[] row : lclDestuffLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	            
//	            try {
////	                Date gateInDate = dateFormatBuffer.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                // Check if the gate-in date is within the Sunday to Saturday week
//	                if (dayName != "" && isDateBetween(gateInDate, day71, day11)) {
//	                    // Count based on container size for Buffer
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                    	dayCounts20Lcl.put(dayName, dayCounts20Lcl.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                    	dayCounts40Lcl.put(dayName, dayCounts40Lcl.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
	
	// Store the results in the map
//	lclDestuffWeekData.put("Monday", dayWiseTuesCountsLcl.get("Monday"));
//	lclDestuffWeekData.put("Tuesday", dayWiseTuesCountsLcl.get("Tuesday"));
//	lclDestuffWeekData.put("Wednesday", dayWiseTuesCountsLcl.get("Wednesday"));
//	lclDestuffWeekData.put("Thursday", dayWiseTuesCountsLcl.get("Thursday"));
//	lclDestuffWeekData.put("Friday", dayWiseTuesCountsLcl.get("Friday"));
//	lclDestuffWeekData.put("Saturday", dayWiseTuesCountsLcl.get("Saturday"));
//	lclDestuffWeekData.put("Sunday", dayWiseTuesCountsLcl.get("Sunday"));	
//	lclDestuffWeekData.put("name", "lclDestuffWeekData"); // Correct name for bufferInUser
	
	dayCounts20Lcl.put(dateFormatFcl.format(day11), 0);
	dayCounts20Lcl.put(dateFormatFcl.format(day21), 0);
	dayCounts20Lcl.put(dateFormatFcl.format(day31), 0);
	dayCounts20Lcl.put(dateFormatFcl.format(day41), 0);
	dayCounts20Lcl.put(dateFormatFcl.format(day51), 0);
	dayCounts20Lcl.put(dateFormatFcl.format(day61), 0);
	dayCounts20Lcl.put(dateFormatFcl.format(day71), 0);

	dayCounts40Lcl.put(dateFormatFcl.format(day11), 0);
	dayCounts40Lcl.put(dateFormatFcl.format(day21), 0);
	dayCounts40Lcl.put(dateFormatFcl.format(day31), 0);
	dayCounts40Lcl.put(dateFormatFcl.format(day41), 0);
	dayCounts40Lcl.put(dateFormatFcl.format(day51), 0);
	dayCounts40Lcl.put(dateFormatFcl.format(day61), 0);
	dayCounts40Lcl.put(dateFormatFcl.format(day71), 0);

	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day11), 0);
	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day21), 0);
	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day31), 0);
	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day41), 0);
	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day51), 0);
	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day61), 0);
	dayWiseTuesCountsLcl.put(dateFormatFcl.format(day71), 0);
    

	
	 if (lclDestuffLastWeek != null) {
	        for (Object[] row : lclDestuffLastWeek) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day71, day11)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20Lcl.put(gateInDateString, dayCounts20Lcl.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40Lcl.put(gateInDateString, dayCounts40Lcl.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	// Calculate tuesCountInBuffer for each day using the formula for Buffer
	for (String date : dayCounts20Lcl.keySet()) {
	    int countIn20LCL = dayCounts20Lcl.get(date);
	    int countIn40LCL = dayCounts40Lcl.get(date);
//	    int tuesCountInLCL = (countIn40LCL * 2) + countIn20LCL;
	    int tuesCountInLCL = (countIn40LCL) + countIn20LCL;
	    dayWiseTuesCountsLcl.put(date, tuesCountInLCL);
	}


	
	lclDestuffWeekData.put(dateFormatFcl.format(day11), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day11)));
	lclDestuffWeekData.put(dateFormatFcl.format(day21), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day21)));
	lclDestuffWeekData.put(dateFormatFcl.format(day31), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day31)));
	lclDestuffWeekData.put(dateFormatFcl.format(day41), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day41)));
	lclDestuffWeekData.put(dateFormatFcl.format(day51), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day51)));
	lclDestuffWeekData.put(dateFormatFcl.format(day61), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day61)));
	lclDestuffWeekData.put(dateFormatFcl.format(day71), dayWiseTuesCountsLcl.get(dateFormatFcl.format(day71)));

	    
	lclDestuffWeekData.put("name", "lclDestuffWeekData"); // Correct name for bufferInUser
	
	
	
	
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    result.put("fclDestuff", containerCounts);
	    result.put("containerCountsSealCutting", containerCountsSealCutting);
	    result.put("gateIn", containerCountsImpGateIn);
	    result.put("inventory", containerCountsByType);
	    result.put("containerCountsEmptyOut", containerCountsEmptyOut);
	    result.put("importCutsomesData", importCutsomesData);
	    
	    result.put("lastWeekGateInData",lastWeekGateInData);
	    result.put("lastWeekLclLoadedMap",lastWeekLclLoadedMap);
	    result.put("fclDestuffMap",fclDestuffMap);
	    result.put("lddMapLastWeek",lddMapLastWeek);
	    result.put("bufferInUser",bufferInUser);
	    result.put("lclDestuffWeekData", lclDestuffWeekData);
	    return ResponseEntity.ok(result);
	}
    
    
    
    
    
    
    
    
    
    public ResponseEntity<Map<String, Map<String, Object>>> getDataForExportDashBoard(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
		
//	    List<Object[]> exportBuffer = commonReportRepo.exportBufferGateIn(companyId, branchId, startDate, endDate);
//	    List<Object[]> emptyMovementOut = commonReportRepo.exportEmptyMovementOut(companyId, branchId, startDate, endDate);
//	    List<Object[]> stuffTally = commonReportRepo.findStuffTallyContainers(companyId, branchId, startDate, endDate);
//	    List<Object[]> exportLDD = commonReportRepo.findExpLDDInventoryContainers(companyId, branchId, endDate);
//	    List<Object[]> emptyGateIn = commonReportRepo.findExportEMptyContainerGateIn(companyId, branchId, startDate, endDate);
	    List<Object[]> exportInventory = dashboardRepository.getInventoryExportReport(companyId, branchId);
	    
	    
	    List<Object[]> exportBuffer = dashboardRepository.exportBufferGateIn(companyId, branchId, startDate);
	    List<Object[]> emptyMovementOut = dashboardRepository.exportEmptyMovementOut(companyId, branchId, startDate);
	    List<Object[]> stuffTally = dashboardRepository.findStuffTallyContainers(companyId, branchId, startDate);
	    List<Object[]> exportLDD = dashboardRepository.findExpLDDInventoryContainers(companyId, branchId);
	    List<Object[]> emptyGateIn = dashboardRepository.findExportEMptyContainerGateIn(companyId, branchId, startDate);
	    
	    
	    List<Object[]> exportBufferLastWeek= dashboardRepository.exportBufferGateInLastWeek(companyId, branchId);
	    List<Object[]> exportMovementOutLastWeek= dashboardRepository.exportEmptyMovementOutLastWeek(companyId, branchId);
	    List<Object[]> exportStuffTallyLastWeek= dashboardRepository.findStuffTallyContainersLastWeek(companyId, branchId);
	    List<Object[]> exportLDDLastWeek= dashboardRepository.findExpLDDInventoryContainersLastWeek(companyId, branchId);
	    List<Object[]> emptyGateInLastWeek = dashboardRepository.findExportEMptyContainerGateInLastWeek(companyId, branchId);
	    List<Object[]> exportInventoryLastWeek = dashboardRepository.getInventoryExportReportLastWeek(companyId, branchId);
	    
	    
	    System.out.println("startDate   :" + startDate    +" endDate  :"+endDate);
	    SimpleDateFormat dateFormatFcl = new SimpleDateFormat("yyyy-MM-dd");
	    
	    


//	    List<Object[]> lastWeekGateIn = dashboardRepository.findDistinctGateInDataOfLastWeek(companyId, branchId, endDate);
	    Map<String, Object> lastWeekGateInData = new HashMap<>();

	    Date endDateNew = new Date();

        // Initialize calendar with the end date
	    
	    Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(endDateNew);

        calendar1.add(Calendar.DATE, -1);
        calendar1.set(Calendar.HOUR_OF_DAY, 23);
        calendar1.set(Calendar.MINUTE, 59);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        // Set time to 23:59 for day1
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDateNew);

        // Exclude the current date by moving back one day
        calendar.add(Calendar.DATE, -1);

        // Date formatter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

        // Print day1 (yesterday) with time 23:59
        Date day1 = calendar1.getTime();
//        System.out.println("Date: " + dateFormat.format(day1) + ", day1: " + dayFormat.format(day1));

        // Get the 6 days before yesterday without setting time
        calendar.add(Calendar.DATE, -1);
        Date day2 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day2) + ", Day: " + dayFormat.format(day2));

        calendar.add(Calendar.DATE, -1);
        Date day3 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day3) + ", Day: " + dayFormat.format(day3));

        calendar.add(Calendar.DATE, -1);
        Date day4 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day4) + ", Day: " + dayFormat.format(day4));

        calendar.add(Calendar.DATE, -1);
        Date day5 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day5) + ", Day: " + dayFormat.format(day5));

        calendar.add(Calendar.DATE, -1);
        Date day6 = calendar.getTime();
//        System.out.println("Date: " + dateFormat.format(day6) + ", day6: " + dayFormat.format(day6));

        // Get day7 and reset the time to 00:00
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date day7 = calendar.getTime();
//        System.out.println("Date: " + dateFormat7.format(day7) + ", Day7: " + dayFormat.format(day7));


	 Map<String, Object> bufferInUser = new HashMap<>();

	// Initialize counters for each day (Monday to Saturday) for LDD
	Map<String, Integer> dayCounts20Buffer = new HashMap<>();
	Map<String, Integer> dayCounts40Buffer = new HashMap<>();
	Map<String, Integer> dayWiseTuesCountsBuffer = new HashMap<>();

	// Initialize day counts for LDD
//	dayCounts20Buffer.put("Sunday", 0);
//	dayCounts20Buffer.put("Monday", 0);
//	dayCounts20Buffer.put("Tuesday", 0);
//	dayCounts20Buffer.put("Wednesday", 0);
//	dayCounts20Buffer.put("Thursday", 0);
//	dayCounts20Buffer.put("Friday", 0);
//	dayCounts20Buffer.put("Saturday", 0);
//
//	dayCounts40Buffer.put("Sunday", 0);
//	dayCounts40Buffer.put("Monday", 0);
//	dayCounts40Buffer.put("Tuesday", 0);
//	dayCounts40Buffer.put("Wednesday", 0);
//	dayCounts40Buffer.put("Thursday", 0);
//	dayCounts40Buffer.put("Friday", 0);
//	dayCounts40Buffer.put("Saturday", 0);
//
//	dayWiseTuesCountsBuffer.put("Sunday", 0);
//	dayWiseTuesCountsBuffer.put("Monday", 0);
//	dayWiseTuesCountsBuffer.put("Tuesday", 0);
//	dayWiseTuesCountsBuffer.put("Wednesday", 0);
//	dayWiseTuesCountsBuffer.put("Thursday", 0);
//	dayWiseTuesCountsBuffer.put("Friday", 0);
//	dayWiseTuesCountsBuffer.put("Saturday", 0);
//
//	// Loop through Buffer data (similar to lastWeekFclDestuff)
//	SimpleDateFormat dateFormatBuffer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (exportBufferLastWeek != null) {  // Assuming bufferInUserData is the data source you have
//	    for (Object[] row : exportBufferLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	            
//	            try {
////	                Date gateInDate = dateFormatBuffer.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                // Check if the gate-in date is within the Sunday to Saturday week
//	                if (dayName != "" && isDateBetween(gateInDate, day7, day1)) {
//	                    // Count based on container size for Buffer
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        dayCounts20Buffer.put(dayName, dayCounts20Buffer.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        dayCounts40Buffer.put(dayName, dayCounts40Buffer.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate tuesCountInBuffer for each day using the formula for Buffer
//	for (String day : dayCounts20Buffer.keySet()) {
//	    int countIn20Buffer = dayCounts20Buffer.get(day);
//	    int countIn40Buffer = dayCounts40Buffer.get(day);
//	    int tuesCountInBuffer = (countIn40Buffer * 2) + countIn20Buffer;
//	    dayWiseTuesCountsBuffer.put(day, tuesCountInBuffer);
//	}
//
//	// Store the results in the map
//	bufferInUser.put("Monday", dayWiseTuesCountsBuffer.get("Monday"));
//	bufferInUser.put("Tuesday", dayWiseTuesCountsBuffer.get("Tuesday"));
//	bufferInUser.put("Wednesday", dayWiseTuesCountsBuffer.get("Wednesday"));
//	bufferInUser.put("Thursday", dayWiseTuesCountsBuffer.get("Thursday"));
//	bufferInUser.put("Friday", dayWiseTuesCountsBuffer.get("Friday"));
//	bufferInUser.put("Saturday", dayWiseTuesCountsBuffer.get("Saturday"));
//	bufferInUser.put("Sunday", dayWiseTuesCountsBuffer.get("Sunday"));
//	bufferInUser.put("name", "bufferInUser"); // Correct name for bufferInUser


	dayCounts20Buffer.put(dateFormatFcl.format(day1), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day2), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day3), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day4), 0);
	dayCounts20Buffer.put(dateFormatFcl.format(day5), 0);
	    dayCounts20Buffer.put(dateFormatFcl.format(day6), 0);
	    dayCounts20Buffer.put(dateFormatFcl.format(day7), 0);

	    dayCounts40Buffer.put(dateFormatFcl.format(day1), 0);
	    dayCounts40Buffer.put(dateFormatFcl.format(day2), 0);
	    dayCounts40Buffer.put(dateFormatFcl.format(day3), 0);
	    dayCounts40Buffer.put(dateFormatFcl.format(day4), 0);
	    dayCounts40Buffer.put(dateFormatFcl.format(day5), 0);
	    dayCounts40Buffer.put(dateFormatFcl.format(day6), 0);
	    dayCounts40Buffer.put(dateFormatFcl.format(day7), 0);

	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day1), 0);
	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day2), 0);
	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day3), 0);
	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day4), 0);
	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day5), 0);
	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day6), 0);
	    dayWiseTuesCountsBuffer.put(dateFormatFcl.format(day7), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
	    SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
	    if (exportBufferLastWeek != null) {
	        for (Object[] row : exportBufferLastWeek) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
//	                	Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day7, day1)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20Buffer.put(gateInDateString, dayCounts20Buffer.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40Buffer.put(gateInDateString, dayCounts40Buffer.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20Buffer.keySet()) {
	        int countIn20Fcl = dayCounts20Buffer.get(date);
	        int countIn40Fcl = dayCounts40Buffer.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCountsBuffer.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    bufferInUser.put(dateFormatFcl.format(day1), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day1)));
	    bufferInUser.put(dateFormatFcl.format(day2), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day2)));
	    bufferInUser.put(dateFormatFcl.format(day3), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day3)));
	    bufferInUser.put(dateFormatFcl.format(day4), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day4)));
	    bufferInUser.put(dateFormatFcl.format(day5), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day5)));
	    bufferInUser.put(dateFormatFcl.format(day6), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day6)));
	    bufferInUser.put(dateFormatFcl.format(day7), dayWiseTuesCountsBuffer.get(dateFormatFcl.format(day7)));
		bufferInUser.put("name", "bufferInUser"); // Correct name for bufferInUser
	
	
	
	Map<String, Object> exportMovementOutLastWeekMap = new HashMap<>();

	// Initialize counters for each day (Monday to Saturday) for Export Movement Out
	Map<String, Integer> dayCounts20Export = new HashMap<>();
	Map<String, Integer> dayCounts40Export = new HashMap<>();
	Map<String, Integer> dayWiseTuesCountsExport = new HashMap<>();

	// Initialize day counts for Export Movement Out
//	dayCounts20Export.put("Sunday", 0);
//	dayCounts20Export.put("Monday", 0);
//	dayCounts20Export.put("Tuesday", 0);
//	dayCounts20Export.put("Wednesday", 0);
//	dayCounts20Export.put("Thursday", 0);
//	dayCounts20Export.put("Friday", 0);
//	dayCounts20Export.put("Saturday", 0);
//
//	dayCounts40Export.put("Sunday", 0);
//	dayCounts40Export.put("Monday", 0);
//	dayCounts40Export.put("Tuesday", 0);
//	dayCounts40Export.put("Wednesday", 0);
//	dayCounts40Export.put("Thursday", 0);
//	dayCounts40Export.put("Friday", 0);
//	dayCounts40Export.put("Saturday", 0);
//
//	dayWiseTuesCountsExport.put("Sunday", 0);
//	dayWiseTuesCountsExport.put("Monday", 0);
//	dayWiseTuesCountsExport.put("Tuesday", 0);
//	dayWiseTuesCountsExport.put("Wednesday", 0);
//	dayWiseTuesCountsExport.put("Thursday", 0);
//	dayWiseTuesCountsExport.put("Friday", 0);
//	dayWiseTuesCountsExport.put("Saturday", 0);
//
//	// Loop through Export Movement Out data (similar to the previous ones)
//	SimpleDateFormat dateFormatExport = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (exportMovementOutLastWeek != null) {  // Assuming exportMovementOutData is the data source you have
//	    for (Object[] row : exportMovementOutLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//	            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
//	            
//	            try {
////	                Date gateInDate = dateFormatExport.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                // Check if the gate-in date is within the Sunday to Saturday week
//	                if (dayName != "" && isDateBetween(gateInDate, day7, day1)) {
//	                    // Count based on container size for Export Movement Out
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        dayCounts20Export.put(dayName, dayCounts20Export.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        dayCounts40Export.put(dayName, dayCounts40Export.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate tuesCountInExport for each day using the formula for Export Movement Out
//	for (String day : dayCounts20Export.keySet()) {
//	    int countIn20Export = dayCounts20Export.get(day);
//	    int countIn40Export = dayCounts40Export.get(day);
//	    int tuesCountInExport = (countIn40Export * 2) + countIn20Export;
//	    dayWiseTuesCountsExport.put(day, tuesCountInExport);
//	}
//
//	// Store the results in the map
//	exportMovementOutLastWeekMap.put("Monday", dayWiseTuesCountsExport.get("Monday"));
//	exportMovementOutLastWeekMap.put("Tuesday", dayWiseTuesCountsExport.get("Tuesday"));
//	exportMovementOutLastWeekMap.put("Wednesday", dayWiseTuesCountsExport.get("Wednesday"));
//	exportMovementOutLastWeekMap.put("Thursday", dayWiseTuesCountsExport.get("Thursday"));
//	exportMovementOutLastWeekMap.put("Friday", dayWiseTuesCountsExport.get("Friday"));
//	exportMovementOutLastWeekMap.put("Saturday", dayWiseTuesCountsExport.get("Saturday"));
//	exportMovementOutLastWeekMap.put("Sunday", dayWiseTuesCountsExport.get("Sunday"));
//	exportMovementOutLastWeekMap.put("name", "exportMovementOutLastWeekMap"); // Correct name for exportMovementOut

	dayCounts20Export.put(dateFormatFcl.format(day1), 0);
	dayCounts20Export.put(dateFormatFcl.format(day2), 0);
	dayCounts20Export.put(dateFormatFcl.format(day3), 0);
	dayCounts20Export.put(dateFormatFcl.format(day4), 0);
	dayCounts20Export.put(dateFormatFcl.format(day5), 0);
	dayCounts20Export.put(dateFormatFcl.format(day6), 0);
	dayCounts20Export.put(dateFormatFcl.format(day7), 0);

	dayCounts40Export.put(dateFormatFcl.format(day1), 0);
	dayCounts40Export.put(dateFormatFcl.format(day2), 0);
	dayCounts40Export.put(dateFormatFcl.format(day3), 0);
	dayCounts40Export.put(dateFormatFcl.format(day4), 0);
	dayCounts40Export.put(dateFormatFcl.format(day5), 0);
	dayCounts40Export.put(dateFormatFcl.format(day6), 0);
	dayCounts40Export.put(dateFormatFcl.format(day7), 0);

	dayWiseTuesCountsExport.put(dateFormatFcl.format(day1), 0);
	dayWiseTuesCountsExport.put(dateFormatFcl.format(day2), 0);
	dayWiseTuesCountsExport.put(dateFormatFcl.format(day3), 0);
	dayWiseTuesCountsExport.put(dateFormatFcl.format(day4), 0);
	dayWiseTuesCountsExport.put(dateFormatFcl.format(day5), 0);
	dayWiseTuesCountsExport.put(dateFormatFcl.format(day6), 0);
	dayWiseTuesCountsExport.put(dateFormatFcl.format(day7), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
	  
	    if (exportMovementOutLastWeek != null) {
	        for (Object[] row : exportMovementOutLastWeek) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
//	                	Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day7, day1)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	dayCounts20Export.put(gateInDateString, dayCounts20Export.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	dayCounts40Export.put(gateInDateString, dayCounts40Export.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : dayCounts20Export.keySet()) {
	        int countIn20Fcl = dayCounts20Export.get(date);
	        int countIn40Fcl = dayCounts40Export.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        dayWiseTuesCountsExport.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day1), dayWiseTuesCountsExport.get(dateFormatFcl.format(day1)));
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day2), dayWiseTuesCountsExport.get(dateFormatFcl.format(day2)));
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day3), dayWiseTuesCountsExport.get(dateFormatFcl.format(day3)));
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day4), dayWiseTuesCountsExport.get(dateFormatFcl.format(day4)));
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day5), dayWiseTuesCountsExport.get(dateFormatFcl.format(day5)));
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day6), dayWiseTuesCountsExport.get(dateFormatFcl.format(day6)));
	    exportMovementOutLastWeekMap.put(dateFormatFcl.format(day7), dayWiseTuesCountsExport.get(dateFormatFcl.format(day7)));
	    exportMovementOutLastWeekMap.put("name", "exportMovementOutLastWeekMap"); // Correct name for exportMovementOut
	
	
	
	
	
	Map<String, Object> exportStuffTallyData = new HashMap<>();
	Map<String, Integer> exportDayCounts20W = new HashMap<>();
	Map<String, Integer> exportDayCounts40W = new HashMap<>();
	Map<String, Integer> exportDayWiseTuesCounts = new HashMap<>();

//	exportDayCounts20W.put("Sunday", 0);
//	exportDayCounts20W.put("Monday", 0);
//	exportDayCounts20W.put("Tuesday", 0);
//	exportDayCounts20W.put("Wednesday", 0);
//	exportDayCounts20W.put("Thursday", 0);
//	exportDayCounts20W.put("Friday", 0);
//	exportDayCounts20W.put("Saturday", 0);
//
//	exportDayCounts40W.put("Sunday", 0);
//	exportDayCounts40W.put("Monday", 0);
//	exportDayCounts40W.put("Tuesday", 0);
//	exportDayCounts40W.put("Wednesday", 0);
//	exportDayCounts40W.put("Thursday", 0);
//	exportDayCounts40W.put("Friday", 0);
//	exportDayCounts40W.put("Saturday", 0);
//
//	exportDayWiseTuesCounts.put("Sunday", 0);
//	exportDayWiseTuesCounts.put("Monday", 0);
//	exportDayWiseTuesCounts.put("Tuesday", 0);
//	exportDayWiseTuesCounts.put("Wednesday", 0);
//	exportDayWiseTuesCounts.put("Thursday", 0);
//	exportDayWiseTuesCounts.put("Friday", 0);
//	exportDayWiseTuesCounts.put("Saturday", 0);
//
//	SimpleDateFormat dateFormat1Exp = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	if (exportStuffTallyLastWeek != null) {
//	    for (Object[] row : exportStuffTallyLastWeek) {
//	        if (row != null && row.length > 1) {
//	            String containerSize = (String) row[1]; 
//	            Date gateInDate = (Date) row[2];
//
//	            try {
////	                Date gateInDate = dateFormat1Exp.parse(gateInDateStr);
//	                Calendar gateInCalendar = Calendar.getInstance();
//	                gateInCalendar.setTime(gateInDate);
//	                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);
//
//	                String dayName = "";
//	                switch (dayOfWeek) {
//	                    case Calendar.SUNDAY: dayName = "Sunday"; break;
//	                    case Calendar.MONDAY: dayName = "Monday"; break;
//	                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
//	                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//	                    case Calendar.THURSDAY: dayName = "Thursday"; break;
//	                    case Calendar.FRIDAY: dayName = "Friday"; break;
//	                    case Calendar.SATURDAY: dayName = "Saturday"; break;
//	                    default: break;
//	                }
//
//	                if (!dayName.isEmpty() && isDateBetween(gateInDate, day7, day1)) {
//	                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
//	                        exportDayCounts20W.put(dayName, exportDayCounts20W.get(dayName) + 1);
//	                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//	                        exportDayCounts40W.put(dayName, exportDayCounts40W.get(dayName) + 1);
//	                    }
//	                }
//	            } catch (Exception e) {
//	                System.out.println("Error parsing date: ");
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//	}
//
//	// Calculate exportDayWiseTuesCounts
//	for (String day : exportDayCounts20W.keySet()) {
//	    int countIn20W = exportDayCounts20W.get(day);
//	    int countIn40W = exportDayCounts40W.get(day);
//	    int tuesCountInW = (countIn40W * 2) + countIn20W;
//	    exportDayWiseTuesCounts.put(day, tuesCountInW);
//	}
//
//	
//	exportStuffTallyData.put("Monday", exportDayWiseTuesCounts.get("Monday"));
//	exportStuffTallyData.put("Tuesday", exportDayWiseTuesCounts.get("Tuesday"));
//	exportStuffTallyData.put("Wednesday", exportDayWiseTuesCounts.get("Wednesday"));
//	exportStuffTallyData.put("Thursday", exportDayWiseTuesCounts.get("Thursday"));
//	exportStuffTallyData.put("Friday", exportDayWiseTuesCounts.get("Friday"));
//	exportStuffTallyData.put("Saturday", exportDayWiseTuesCounts.get("Saturday"));
//	exportStuffTallyData.put("Sunday", exportDayWiseTuesCounts.get("Sunday"));
//	exportStuffTallyData.put("name", "EXPORT STUFF TALLY WEEK");

	
	exportDayCounts20W.put(dateFormatFcl.format(day1), 0);
	exportDayCounts20W.put(dateFormatFcl.format(day2), 0);
	exportDayCounts20W.put(dateFormatFcl.format(day3), 0);
	exportDayCounts20W.put(dateFormatFcl.format(day4), 0);
	exportDayCounts20W.put(dateFormatFcl.format(day5), 0);
	exportDayCounts20W.put(dateFormatFcl.format(day6), 0);
	exportDayCounts20W.put(dateFormatFcl.format(day7), 0);

	exportDayCounts40W.put(dateFormatFcl.format(day1), 0);
	exportDayCounts40W.put(dateFormatFcl.format(day2), 0);
	exportDayCounts40W.put(dateFormatFcl.format(day3), 0);
	exportDayCounts40W.put(dateFormatFcl.format(day4), 0);
	exportDayCounts40W.put(dateFormatFcl.format(day5), 0);
	exportDayCounts40W.put(dateFormatFcl.format(day6), 0);
	exportDayCounts40W.put(dateFormatFcl.format(day7), 0);

	exportDayWiseTuesCounts.put(dateFormatFcl.format(day1), 0);
	exportDayWiseTuesCounts.put(dateFormatFcl.format(day2), 0);
	exportDayWiseTuesCounts.put(dateFormatFcl.format(day3), 0);
	exportDayWiseTuesCounts.put(dateFormatFcl.format(day4), 0);
	exportDayWiseTuesCounts.put(dateFormatFcl.format(day5), 0);
	exportDayWiseTuesCounts.put(dateFormatFcl.format(day6), 0);
	exportDayWiseTuesCounts.put(dateFormatFcl.format(day7), 0);

	    // Loop through FclDestuff data (just like lastWeekLclLoaded)
	  
	    if (exportStuffTallyLastWeek != null) {
	        for (Object[] row : exportStuffTallyLastWeek) {
	            if (row != null && row.length > 1) {
	                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

	                try {
	                    // Create a Calendar instance for gate-in date
//	                	Date gateInDate = dateFormat111.parse(gateInDateStr);
	                    Calendar gateInCalendar = Calendar.getInstance();
	                    gateInCalendar.setTime(gateInDate);

	                    // Format gate-in date to match the date format
	                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

	                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
	                    if (isDateBetween(gateInDate, day7, day1)) {
	                        // Count based on container size for FclDestuff
	                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                        	exportDayCounts20W.put(gateInDateString, exportDayCounts20W.get(gateInDateString) + 1);
	                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                        	exportDayCounts40W.put(gateInDateString, exportDayCounts40W.get(gateInDateString) + 1);
	                        }
	                    }
	                } catch (Exception e) {
	                    System.out.println("Error parsing date: ");
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
	    for (String date : exportDayCounts20W.keySet()) {
	        int countIn20Fcl = exportDayCounts20W.get(date);
	        int countIn40Fcl = exportDayCounts40W.get(date);
//	        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
	        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
	        exportDayWiseTuesCounts.put(date, tuesCountInFcl);
	    }

	    // Store the results in the map with dates instead of days
	    exportStuffTallyData.put(dateFormatFcl.format(day1), exportDayWiseTuesCounts.get(dateFormatFcl.format(day1)));
	    exportStuffTallyData.put(dateFormatFcl.format(day2), exportDayWiseTuesCounts.get(dateFormatFcl.format(day2)));
	    exportStuffTallyData.put(dateFormatFcl.format(day3), exportDayWiseTuesCounts.get(dateFormatFcl.format(day3)));
	    exportStuffTallyData.put(dateFormatFcl.format(day4), exportDayWiseTuesCounts.get(dateFormatFcl.format(day4)));
	    exportStuffTallyData.put(dateFormatFcl.format(day5), exportDayWiseTuesCounts.get(dateFormatFcl.format(day5)));
	    exportStuffTallyData.put(dateFormatFcl.format(day6), exportDayWiseTuesCounts.get(dateFormatFcl.format(day6)));
	    exportStuffTallyData.put(dateFormatFcl.format(day7), exportDayWiseTuesCounts.get(dateFormatFcl.format(day7)));
	    exportStuffTallyData.put("name", "EXPORT STUFF TALLY WEEK");
	    
	    Map<String, Object> Empty = new HashMap<>();

		 // Initialize counters for each day (Monday to Saturday) for LDD
		 Map<String, Integer> dayCounts20Empty = new HashMap<>();
		 Map<String, Integer> dayCounts40Empty = new HashMap<>();
		 Map<String, Integer> dayWiseTuesCountsEmpty = new HashMap<>();

		 // Initialize day counts for LDD
//		 dayCounts20Empty.put("Sunday", 0);
//		 dayCounts20Empty.put("Monday", 0);
//		 dayCounts20Empty.put("Tuesday", 0);
//		 dayCounts20Empty.put("Wednesday", 0);
//		 dayCounts20Empty.put("Thursday", 0);
//		 dayCounts20Empty.put("Friday", 0);
//		 dayCounts20Empty.put("Saturday", 0);
//
//		 dayCounts40Empty.put("Sunday", 0);
//		 dayCounts40Empty.put("Monday", 0);
//		 dayCounts40Empty.put("Tuesday", 0);
//		 dayCounts40Empty.put("Wednesday", 0);
//		 dayCounts40Empty.put("Thursday", 0);
//		 dayCounts40Empty.put("Friday", 0);
//		 dayCounts40Empty.put("Saturday", 0);
//
//		 dayWiseTuesCountsEmpty.put("Sunday", 0);
//		 dayWiseTuesCountsEmpty.put("Monday", 0);
//		 dayWiseTuesCountsEmpty.put("Tuesday", 0);
//		 dayWiseTuesCountsEmpty.put("Wednesday", 0);
//		 dayWiseTuesCountsEmpty.put("Thursday", 0);
//		 dayWiseTuesCountsEmpty.put("Friday", 0);
//		 dayWiseTuesCountsEmpty.put("Saturday", 0);
//
//		 // Loop through Empty data (similar to lastWeekFclDestuff)
//		 SimpleDateFormat dateFormatEmpty = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		 if (emptyGateInLastWeek != null) {  // Assuming EmptyData is the data source you have
//		     for (Object[] row : emptyGateInLastWeek) {
//		         if (row != null && row.length > 1) {
//		             String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
//		             Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]
//		             
//		             try {
//		                 Calendar gateInCalendar = Calendar.getInstance();
//		                 gateInCalendar.setTime(gateInDate);
//		                 int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...
//
//		                 String dayName = "";
//		                 switch (dayOfWeek) {
//		                     case Calendar.SUNDAY: dayName = "Sunday"; break;
//		                     case Calendar.MONDAY: dayName = "Monday"; break;
//		                     case Calendar.TUESDAY: dayName = "Tuesday"; break;
//		                     case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
//		                     case Calendar.THURSDAY: dayName = "Thursday"; break;
//		                     case Calendar.FRIDAY: dayName = "Friday"; break;
//		                     case Calendar.SATURDAY: dayName = "Saturday"; break;
//		                     default: break;
//		                 }
//
//		                 // Check if the gate-in date is within the Sunday to Saturday week
//		                 if (!dayName.isEmpty() && isDateBetween(gateInDate, day7, day1)) {
//		                     // Count based on container size for Empty
//		                     if ("20".equals(containerSize) || "22".equals(containerSize)) {
//		                         dayCounts20Empty.put(dayName, dayCounts20Empty.get(dayName) + 1);
//		                     } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
//		                         dayCounts40Empty.put(dayName, dayCounts40Empty.get(dayName) + 1);
//		                     }
//		                 }
//		             } catch (Exception e) {
//		                 System.out.println("Error parsing date: ");
//		                 e.printStackTrace();
//		             }
//		         }
//		     }
//		 }
//
//		 // Calculate tuesCountInEmpty for each day using the formula for Empty
//		 for (String day : dayCounts20Empty.keySet()) {
//		     int countIn20Empty = dayCounts20Empty.get(day);
//		     int countIn40Empty = dayCounts40Empty.get(day);
//		     int tuesCountInEmpty = (countIn40Empty * 2) + countIn20Empty;
//		     dayWiseTuesCountsEmpty.put(day, tuesCountInEmpty);
//		 }
//
//		 // Store the results in the map
//		 Empty.put("Monday", dayWiseTuesCountsEmpty.get("Monday"));
//		 Empty.put("Tuesday", dayWiseTuesCountsEmpty.get("Tuesday"));
//		 Empty.put("Wednesday", dayWiseTuesCountsEmpty.get("Wednesday"));
//		 Empty.put("Thursday", dayWiseTuesCountsEmpty.get("Thursday"));
//		 Empty.put("Friday", dayWiseTuesCountsEmpty.get("Friday"));
//		 Empty.put("Saturday", dayWiseTuesCountsEmpty.get("Saturday"));
//		 Empty.put("Sunday", dayWiseTuesCountsEmpty.get("Sunday"));
//		 Empty.put("name", "Empty"); // Correct name for Empty

	    
		 dayCounts20Empty.put(dateFormatFcl.format(day1), 0);
		 dayCounts20Empty.put(dateFormatFcl.format(day2), 0);
		 dayCounts20Empty.put(dateFormatFcl.format(day3), 0);
		 dayCounts20Empty.put(dateFormatFcl.format(day4), 0);
		 dayCounts20Empty.put(dateFormatFcl.format(day5), 0);
		 dayCounts20Empty.put(dateFormatFcl.format(day6), 0);
		 dayCounts20Empty.put(dateFormatFcl.format(day7), 0);

		 dayCounts40Empty.put(dateFormatFcl.format(day1), 0);
		 dayCounts40Empty.put(dateFormatFcl.format(day2), 0);
		 dayCounts40Empty.put(dateFormatFcl.format(day3), 0);
		 dayCounts40Empty.put(dateFormatFcl.format(day4), 0);
		 dayCounts40Empty.put(dateFormatFcl.format(day5), 0);
		 dayCounts40Empty.put(dateFormatFcl.format(day6), 0);
		 dayCounts40Empty.put(dateFormatFcl.format(day7), 0);

		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day1), 0);
		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day2), 0);
		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day3), 0);
		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day4), 0);
		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day5), 0);
		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day6), 0);
		 dayWiseTuesCountsEmpty.put(dateFormatFcl.format(day7), 0);

			    // Loop through FclDestuff data (just like lastWeekLclLoaded)
			  
			    if (emptyGateInLastWeek != null) {
			        for (Object[] row : emptyGateInLastWeek) {
			            if (row != null && row.length > 1) {
			                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
			                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]

			                try {
			                    // Create a Calendar instance for gate-in date
//			                	Date gateInDate = dateFormat111.parse(gateInDateStr);
			                    Calendar gateInCalendar = Calendar.getInstance();
			                    gateInCalendar.setTime(gateInDate);

			                    // Format gate-in date to match the date format
			                    String gateInDateString = dateFormat111.format(gateInCalendar.getTime());

			                    // Check if the gate-in date is within the Sunday to Saturday week (dates, not days)
			                    if (isDateBetween(gateInDate, day7, day1)) {
			                        // Count based on container size for FclDestuff
			                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
			                        	dayCounts20Empty.put(gateInDateString, dayCounts20Empty.get(gateInDateString) + 1);
			                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
			                        	dayCounts40Empty.put(gateInDateString, dayCounts40Empty.get(gateInDateString) + 1);
			                        }
			                    }
			                } catch (Exception e) {
			                    System.out.println("Error parsing date: ");
			                    e.printStackTrace();
			                }
			            }
			        }
			    }

			    // Calculate tuesCountInFcl for each date using the formula for FclDestuff
			    for (String date : dayCounts20Empty.keySet()) {
			        int countIn20Fcl = dayCounts20Empty.get(date);
			        int countIn40Fcl = dayCounts40Empty.get(date);
//			        int tuesCountInFcl = (countIn40Fcl * 2) + countIn20Fcl;
			        int tuesCountInFcl = (countIn40Fcl) + countIn20Fcl;
			        dayWiseTuesCountsEmpty.put(date, tuesCountInFcl);
			    }

			    // Store the results in the map with dates instead of days
			    Empty.put(dateFormatFcl.format(day1), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day1)));
			    Empty.put(dateFormatFcl.format(day2), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day2)));
			    Empty.put(dateFormatFcl.format(day3), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day3)));
			    Empty.put(dateFormatFcl.format(day4), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day4)));
			    Empty.put(dateFormatFcl.format(day5), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day5)));
			    Empty.put(dateFormatFcl.format(day6), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day6)));
			    Empty.put(dateFormatFcl.format(day7), dayWiseTuesCountsEmpty.get(dateFormatFcl.format(day7)));
			    Empty.put("name", "Empty"); // Correct name for Empty
			    

	    Map<String, Object> exportBufferContainer = new HashMap<>();
	    int countSeal201 = 0;
	    int countSeal401 = 0;

	    // Process seal cutting data (corrected loop)
	    if (exportBuffer != null) {
	        for (Object[] row : exportBuffer) { // Corrected: use sealCutting instead of joGateIn
	            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
	            String countCategory = getContainerSizeCategory(containerSize);

	            if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                countSeal201++;
	            } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                countSeal401++;
	            }
	            exportBufferContainer.put(countCategory, (Integer) exportBufferContainer.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCountSeal1 = (countSeal401 * 2) + countSeal201;
	    int totalCountSeal1 =countSeal401 + countSeal201;
	    exportBufferContainer.put("total", totalCountSeal1);
	    exportBufferContainer.put("Tues", tuesCountSeal1);
	    exportBufferContainer.put("name", "BUFFER GATE IN");
	    
	    
	    Map<String, Object> emptyMovementOutMap = new HashMap<>();
	    int countIn201 = 0;
	    int countIn401 = 0;

	    // Process import gate-in data
	    if (emptyMovementOut != null) {
	        for (Object[] row : emptyMovementOut) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	                String containerSize = (String) row[3]; // Assuming Container_Size is at index [1]
	                String countCategory = getContainerSizeCategory(containerSize);
	                if ("20".equals(containerSize) || "22".equals(containerSize)) {
	                    countIn201++;
	                } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
	                    countIn401++;
	                }
	                emptyMovementOutMap.put(countCategory, (Integer) emptyMovementOutMap.getOrDefault(countCategory, 0) + 1);
	            }
	        }
	    }

	    int tuesCountIn1 = (countIn401 * 2) + countIn201;
	    int totalCountIn1 =countIn401 + countIn201;
	    emptyMovementOutMap.put("total", totalCountIn1);
	    emptyMovementOutMap.put("Tues", tuesCountIn1);
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

	    
	    
	    Map<String, Object> exportLDDMap = new HashMap<>();
	    int countLDD20 = 0;
	    int countLDD40 = 0;

	    if (exportLDD != null) {
	        for (Object[] row : exportLDD) {
	          
	            
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
	    
	    
	    Map<String, Object> ExportcontainerCounts = new HashMap<>();
	    int count20 = 0;
	    int count40 = 0;

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
	            ExportcontainerCounts.put(countCategory, (Integer) ExportcontainerCounts.getOrDefault(countCategory, 0) + 1);
	        }
	    }

	    int tuesCount = (count40 * 2) + count20;
	    int totalCount = count40 + count20;
	    ExportcontainerCounts.put("total", totalCount);
	    ExportcontainerCounts.put("Tues", tuesCount);
	    ExportcontainerCounts.put("name", "EXPORT EMPTY IN"); 
	    
	   
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
	    
	    
	    Map<String, Object> exportLaddLastWeekData = new HashMap<>();
		

		 Map<String, Integer> dayCounts20 = new HashMap<>();
		    Map<String, Integer> dayCounts40 = new HashMap<>();
		    Map<String, Integer> dayWiseTuesCount = new HashMap<>();

		    dayCounts20.put("Sunday", 0);
		    dayCounts20.put("Monday", 0);
		    dayCounts20.put("Tuesday", 0);
		    dayCounts20.put("Wednesday", 0);
		    dayCounts20.put("Thursday", 0);
		    dayCounts20.put("Friday", 0);
		    dayCounts20.put("Saturday", 0);

		    dayCounts40.put("Sunday", 0);
		    dayCounts40.put("Monday", 0);
		    dayCounts40.put("Tuesday", 0);
		    dayCounts40.put("Wednesday", 0);
		    dayCounts40.put("Thursday", 0);
		    dayCounts40.put("Friday", 0);
		    dayCounts40.put("Saturday", 0);

		    dayWiseTuesCount.put("Sunday", 0);
		    dayWiseTuesCount.put("Monday", 0);
		    dayWiseTuesCount.put("Tuesday", 0);
		    dayWiseTuesCount.put("Wednesday", 0);
		    dayWiseTuesCount.put("Thursday", 0);
		    dayWiseTuesCount.put("Friday", 0);
		    dayWiseTuesCount.put("Saturday", 0);


		    
		    if (exportLDDLastWeek != null) {
		        for (Object[] row : exportLDDLastWeek) {
		            if (row != null && row.length > 1) { // Null check for row and container size
		                String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is a String at index [2]
		                
		                
//		                System.out.println( " gateInDateStr  :"+gateInDateStr);;

////		                Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]  
		                
		                try {
		                    // Get the day of the week for the gate-in date
//		                	 Date gateInDate = dateFormat.parse(gateInDateStr);
		                    Calendar gateInCalendar = Calendar.getInstance();
		                    gateInCalendar.setTime(gateInDate);
		                    int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...

		                    // Find the corresponding day name
		                    String dayName = "";
		                    switch (dayOfWeek) {
		                        case Calendar.SUNDAY: dayName = "Sunday"; break;
		                        case Calendar.MONDAY: dayName = "Monday"; break;
		                        case Calendar.TUESDAY: dayName = "Tuesday"; break;
		                        case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
		                        case Calendar.THURSDAY: dayName = "Thursday"; break;
		                        case Calendar.FRIDAY: dayName = "Friday"; break;
		                        case Calendar.SATURDAY: dayName = "Saturday"; break;
		                        default: break;
		                    }

		                    // Check if the gate-in date is within the Sunday to Saturday week
		                    if (dayName != "" && isDateBetween(gateInDate, day7, day1)) {
		                        // Count based on container size
		                        if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                        	dayCounts20.put(dayName, dayCounts20.get(dayName) + 1);
		                        } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                            dayCounts40.put(dayName, dayCounts40.get(dayName) + 1);
		                        }
		                    }
		                } catch (Exception e) {
		                    System.out.println("Error parsing date: ");
		                    e.printStackTrace();
		                }
		            }
		        }
		    }

		    // Calculate tuesCountInW for each day using the formula
		    for (String day : dayCounts20.keySet()) {
		        int countIn20LDDD = dayCounts20.get(day);
		        int countIn40LDDD = dayCounts40.get(day);
		        int tuesCountInLDDD = (countIn40LDDD * 2) + countIn20LDDD;
		        dayWiseTuesCount.put(day, tuesCountInLDDD);
		    }

		    // Store the results
		    exportLaddLastWeekData.put("Monday", dayWiseTuesCount.get("Monday"));
		    exportLaddLastWeekData.put("Tuesday", dayWiseTuesCount.get("Tuesday"));
		    exportLaddLastWeekData.put("Wednesday", dayWiseTuesCount.get("Wednesday"));
		    exportLaddLastWeekData.put("Thursday", dayWiseTuesCount.get("Thursday"));
		    exportLaddLastWeekData.put("Friday", dayWiseTuesCount.get("Friday"));
		    exportLaddLastWeekData.put("Saturday", dayWiseTuesCount.get("Saturday"));
		    exportLaddLastWeekData.put("Sunday", dayWiseTuesCount.get("Sunday"));
		    exportLaddLastWeekData.put("name", "exportLaddLastWeekData");    
	    
	    
	    
		   
		 
		 
		 
		 
		 
		 
		 Map<String, Object> ExpInventoryLast = new HashMap<>();

		// Initialize counters for each day (Monday to Saturday) for LDD
		Map<String, Integer> dayCounts20ExpInventoryLast = new HashMap<>();
		Map<String, Integer> dayCounts40ExpInventoryLast = new HashMap<>();
		Map<String, Integer> dayWiseTuesCountsExpInventoryLast = new HashMap<>();

		// Initialize day counts for LDD
		dayCounts20ExpInventoryLast.put("Sunday", 0);
		dayCounts20ExpInventoryLast.put("Monday", 0);
		dayCounts20ExpInventoryLast.put("Tuesday", 0);
		dayCounts20ExpInventoryLast.put("Wednesday", 0);
		dayCounts20ExpInventoryLast.put("Thursday", 0);
		dayCounts20ExpInventoryLast.put("Friday", 0);
		dayCounts20ExpInventoryLast.put("Saturday", 0);

		dayCounts40ExpInventoryLast.put("Sunday", 0);
		dayCounts40ExpInventoryLast.put("Monday", 0);
		dayCounts40ExpInventoryLast.put("Tuesday", 0);
		dayCounts40ExpInventoryLast.put("Wednesday", 0);
		dayCounts40ExpInventoryLast.put("Thursday", 0);
		dayCounts40ExpInventoryLast.put("Friday", 0);
		dayCounts40ExpInventoryLast.put("Saturday", 0);

		dayWiseTuesCountsExpInventoryLast.put("Sunday", 0);
		dayWiseTuesCountsExpInventoryLast.put("Monday", 0);
		dayWiseTuesCountsExpInventoryLast.put("Tuesday", 0);
		dayWiseTuesCountsExpInventoryLast.put("Wednesday", 0);
		dayWiseTuesCountsExpInventoryLast.put("Thursday", 0);
		dayWiseTuesCountsExpInventoryLast.put("Friday", 0);
		dayWiseTuesCountsExpInventoryLast.put("Saturday", 0);

		// Loop through ExpInventoryLast data (similar to lastWeekFclDestuff)
		SimpleDateFormat dateFormatExpInventoryLast = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (exportInventoryLastWeek != null) {  // Assuming ExpInventoryLastData is the data source you have
		    for (Object[] row : exportInventoryLastWeek) {
		        if (row != null && row.length > 1) {
		            String containerSize = (String) row[1]; // Assuming Container_Size is at index [1]
		            Date gateInDate = (Date) row[2]; // Assuming Gate_In_Date is at index [2]
		            
		            try {
		                Calendar gateInCalendar = Calendar.getInstance();
		                gateInCalendar.setTime(gateInDate);
		                int dayOfWeek = gateInCalendar.get(Calendar.DAY_OF_WEEK);  // Sunday = 1, Monday = 2, ...

		                String dayName = "";
		                switch (dayOfWeek) {
		                    case Calendar.SUNDAY: dayName = "Sunday"; break;
		                    case Calendar.MONDAY: dayName = "Monday"; break;
		                    case Calendar.TUESDAY: dayName = "Tuesday"; break;
		                    case Calendar.WEDNESDAY: dayName = "Wednesday"; break;
		                    case Calendar.THURSDAY: dayName = "Thursday"; break;
		                    case Calendar.FRIDAY: dayName = "Friday"; break;
		                    case Calendar.SATURDAY: dayName = "Saturday"; break;
		                    default: break;
		                }

		                // Check if the gate-in date is within the Sunday to Saturday week
		                if (!dayName.isEmpty() && isDateBetween(gateInDate, day7, day1)) {
		                    // Count based on container size for ExpInventoryLast
		                    if ("20".equals(containerSize) || "22".equals(containerSize)) {
		                        dayCounts20ExpInventoryLast.put(dayName, dayCounts20ExpInventoryLast.get(dayName) + 1);
		                    } else if ("40".equals(containerSize) || "45".equals(containerSize)) {
		                        dayCounts40ExpInventoryLast.put(dayName, dayCounts40ExpInventoryLast.get(dayName) + 1);
		                    }
		                }
		            } catch (Exception e) {
		                System.out.println("Error parsing date: ");
		                e.printStackTrace();
		            }
		        }
		    }
		}

		// Calculate tuesCountInExpInventoryLast for each day using the formula for ExpInventoryLast
		for (String day : dayCounts20ExpInventoryLast.keySet()) {
		    int countIn20ExpInventoryLast = dayCounts20ExpInventoryLast.get(day);
		    int countIn40ExpInventoryLast = dayCounts40ExpInventoryLast.get(day);
		    int tuesCountInExpInventoryLast = (countIn40ExpInventoryLast * 2) + countIn20ExpInventoryLast;
		    dayWiseTuesCountsExpInventoryLast.put(day, tuesCountInExpInventoryLast);
		}

		// Store the results in the map
		ExpInventoryLast.put("Monday", dayWiseTuesCountsExpInventoryLast.get("Monday"));
		ExpInventoryLast.put("Tuesday", dayWiseTuesCountsExpInventoryLast.get("Tuesday"));
		ExpInventoryLast.put("Wednesday", dayWiseTuesCountsExpInventoryLast.get("Wednesday"));
		ExpInventoryLast.put("Thursday", dayWiseTuesCountsExpInventoryLast.get("Thursday"));
		ExpInventoryLast.put("Friday", dayWiseTuesCountsExpInventoryLast.get("Friday"));
		ExpInventoryLast.put("Saturday", dayWiseTuesCountsExpInventoryLast.get("Saturday"));
		ExpInventoryLast.put("Sunday", dayWiseTuesCountsExpInventoryLast.get("Sunday"));
		ExpInventoryLast.put("name", "ExpInventoryLast"); // Correct name for ExpInventoryLast

	   
		    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    
	    
	    
	    result.put("exportLDDMap", exportLDDMap);
	    result.put("exportBufferContainer", exportBufferContainer);
	    result.put("emptyMovementOutMap", emptyMovementOutMap);
	    result.put("exportStuffTally", exportStuffTally);
	    result.put("ExportcontainerCounts", ExportcontainerCounts);
	    result.put("exp", exp);
	    
	    result.put("bufferInUser",bufferInUser);
	    result.put("exportMovementOutLastWeekMap",exportMovementOutLastWeekMap);
	    result.put("exportStuffTallyData",exportStuffTallyData);
	    result.put("exportLaddLastWeekData",exportLaddLastWeekData);
	    result.put("Empty",Empty);
	    result.put("ExpInventoryLast",ExpInventoryLast);
	    
	    
	   
	    
	   
	    
	    
	    
	    
	    return ResponseEntity.ok(result);
	}
    
    
    
    public ResponseEntity<Map<String, Map<String, Object>>> getDataForExportInventoryBarChart(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    List<Object[]> loadedInventory = dashboardRepository.exportEmptyMovementOutBarChart(companyId, branchId, startDate);

	    // Initialize a map to store counts by container type
	    Map<String, Map<String, Object>> containerCountsByType = new HashMap<>();

	    List<String> containerTypessss = Arrays.asList("CLP", "Buffer", "ONWH");

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
	                String containerSize = (String) row[3]; // Container size (20, 40, etc.)
	                String currentContainerType = (String) row[4]; // Container type from row
	                
	                
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
    
    
    
    
    
    
    
    
    
    
    
    
    public ResponseEntity<Map<String, Map<String, Object>>> getDataForInvoiceDashboard(
	        String companyId, String branchId,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {

	    // Fetch data for job order and seal cutting
		
	    List<Object[]> invoiceDetails = dashboardRepository.findInvoiceSummary(companyId, branchId, startDate, endDate);
	    List<Object[]> totalCollections = dashboardRepository.findInvoiceTotalCollection(companyId, branchId, startDate, endDate);
	    List<Object[]> outStanding = dashboardRepository.findOutstandingInvoices(companyId, branchId);
	    
	    List<Object[]> advance = dashboardRepository.findInvoiceAdvance(companyId, branchId, startDate, endDate);
	  
	    
	    
	    List<Object[]> lastWeekInvoiceDetails= dashboardRepository.findInvoiceSummaryLastWeek(companyId, branchId);
	    List<Object[]> totalCollectionsLastWeek= dashboardRepository.findInvoiceTotalCollectionLastWeek(companyId, branchId);
	    List<Object[]> outStandingLastWeek= dashboardRepository.findOutstandingInvoicesLastWeek(companyId, branchId);
	    
	    List<Object []> getDataForPieChart =dashboardRepository.findPaymentSummaryForPieChart(companyId, branchId, startDate, endDate);
	    
//	    List<Object []> top10OutStanding =dashboardRepository.findTopOutstandingInvoices(companyId, branchId, startDate, endDate);
	    List<Object []> top10OutStanding =dashboardRepository.findTopOutstandingInvoices(companyId, branchId);
	    
	    
	    
	    SimpleDateFormat dateFormatFcl = new SimpleDateFormat("yyyy-MM-dd");


	    
	    Map<String, Object> top10OutStandingChart = new HashMap<>();
	    Map<String, Object> advanceMap = new HashMap<>();
	    
//	    if(!advance.isEmpty() && advance!=null)
//	    {
	    	advanceMap.put("advance", advance);
//	    }
	    
//	    if(!top10OutStanding.isEmpty() && top10OutStanding!=null)
//	    {
	    	top10OutStandingChart.put("top10OutStanding", top10OutStanding);
//	    }
	    
//	    List<Object[]> lastWeekGateIn = dashboardRepository.findDistinctGateInDataOfLastWeek(companyId, branchId, endDate);
	   
//	    Map<String, Object> pieChart = new HashMap<>();
//	    Map<String, Map<String, Object>> paymentModeData = new HashMap<>();
//
//
//	    // Define Payment Mode Groups
//	    Map<String, String> paymentModeMapping = new HashMap<>();
//	    paymentModeMapping.put("ADVANCE", "PDA");
//	    paymentModeMapping.put("CASH", "CASH");
//	    paymentModeMapping.put("RTGS", "CASH");
//	    paymentModeMapping.put("EFT", "CASH");
//	    paymentModeMapping.put("CHEQUE", "CASH");
//	    paymentModeMapping.put("CREDIT", "CREDIT");
//
//	    // Process import gate-in data
//	    if (getDataForPieChart != null) {
//	        for (Object[] row : getDataForPieChart) {
//	            if (row != null && row.length > 4) { // Ensure row has valid data
//	                Long count = (Long) row[3]; // Invoice count at index 3
//	                String paymentMode = (String) row[2]; // Payment mode at index 2
//	                BigDecimal amount = (BigDecimal) row[4]; // Amount at index 4
//
//	                // Determine the grouped payment mode
//	                String groupedPaymentMode = paymentModeMapping.getOrDefault(paymentMode, "OTHER");
//
//
//	                // Check if payment mode already exists
//	                if (!paymentModeData.containsKey(groupedPaymentMode)) {
//	                    // Initialize new payment mode entry
//	                    Map<String, Object> modeData = new HashMap<>();
//	                    modeData.put("count", count.intValue());
//	                    modeData.put("amount", amount);
//	                    paymentModeData.put(groupedPaymentMode, modeData);
//	                } else {
//	                    // Update existing payment mode data
//	                    Map<String, Object> modeData = paymentModeData.get(groupedPaymentMode);
//	                    modeData.put("count", (int) modeData.get("count") + count.intValue());
//	                    modeData.put("amount", ((BigDecimal) modeData.get("amount")).add(amount));
//	                }
//	            }
//	        }
//	    }
//
//	    pieChart.put("paymentModeData", paymentModeData);
//	    System.out.println("Payment Mode Data: " + paymentModeData);

	    

	    
	    Map<String, Object> pieChart = new HashMap<>();
	    Map<String, Map<String, Object>> paymentModeData = new HashMap<>();

	    // Define Payment Mode Groups
	    Map<String, String> paymentModeMapping = new HashMap<>();
	    paymentModeMapping.put("ADVANCE", "PDA");
	    paymentModeMapping.put("CASH", "CASH");
	    paymentModeMapping.put("RTGS", "CASH");
	    paymentModeMapping.put("EFT", "CASH");
	    paymentModeMapping.put("CHEQUE", "CASH");
	    paymentModeMapping.put("CREDIT", "CREDIT");

	    // Initialize payment modes with zero values to ensure they exist
	    List<String> predefinedModes = Arrays.asList("PDA", "CASH", "CREDIT");

	    for (String mode : predefinedModes) {
	        Map<String, Object> modeData = new HashMap<>();
	        modeData.put("count", 0);
	        modeData.put("amount", BigDecimal.ZERO);
	        paymentModeData.put(mode, modeData);
	    }

	    // Process import gate-in data
	    if (getDataForPieChart != null) {
	        for (Object[] row : getDataForPieChart) {
	            if (row != null && row.length > 1) { // Ensure row has valid data
	                Long count = (Long) row[3]; // Invoice count at index 3
	                String paymentMode = (String) row[2]; // Payment mode at index 2
	                BigDecimal amount = (BigDecimal) row[4]; // Amount at index 4

	                // Determine the grouped payment mode
	                String groupedPaymentMode = paymentModeMapping.getOrDefault(paymentMode, "OTHER");

	                // Check if payment mode already exists (it will exist due to initialization)
	                Map<String, Object> modeData = paymentModeData.get(groupedPaymentMode);
	                modeData.put("count", (int) modeData.get("count") + count.intValue());
	                modeData.put("amount", ((BigDecimal) modeData.get("amount")).add(amount));
	            }
	        }
	    }

	    pieChart.put("paymentModeData", paymentModeData);
	    System.out.println("Payment Mode Data: " + paymentModeData);

	int invoiceCount = 0;
    BigDecimal totalAmount = BigDecimal.ZERO;

    // Iterate through invoiceDetails
    for (Object[] row : invoiceDetails) {
        if (row != null && row.length > 1) { // Ensure row has enough elements
        	 Long count = (Long) row[1]; // Invoice count at index 1
            BigDecimal amount = (BigDecimal) row[3]; // Amount at index 3

            // Update totals
            invoiceCount += count.intValue();
            totalAmount = totalAmount.add(amount);
        }
    }

    // Prepare the result map
    Map<String, Object> invocesRecord = new HashMap<>();
    invocesRecord.put("totalInvoiceCount", invoiceCount);
    invocesRecord.put("totalAmount", totalAmount);
    invocesRecord.put("invoiceDetails", invoiceDetails);

    
	    
	    Map<String, Object> collection = new HashMap<>();
	    int collectionCount = 0;
	    BigDecimal collectionAmount = BigDecimal.ZERO;

	    // Process import gate-in data
	    if (totalCollections != null) {
	        for (Object[] row : totalCollections) {
	            if (row != null && row.length > 1 ) { // Null check for row and container size
	            	 Long count = (Long) row[2]; // Invoice count at index 1
	                 BigDecimal amount = (BigDecimal) row[3]; // Amount at index 3
	               
	                 collectionCount += count.intValue();
	                 collectionAmount = collectionAmount.add(amount);
	            }
	        }
	    }

	    collection.put("totalCount", collectionCount);
	    collection.put("totalCollection", collectionAmount);
	    collection.put("totalCollections", totalCollections);
	    
	    
	    Map<String, Object> outStandingMap = new HashMap<>();
	    int outCount = 0;
	    
	    BigDecimal outAmount = BigDecimal.ZERO;
	    
	    if (outStanding != null) {
	        for (Object[] row : outStanding) {
	          
	        	 Long count = (Long) row[2]; // Invoice count at index 1
                 BigDecimal amount = (BigDecimal) row[4]; // Amount at index 3
               
                 outCount += count.intValue();
                 outAmount = outAmount.add(amount);
	        }
	    }
	    outStandingMap.put("totalCoOutStanding", outCount);
	    outStandingMap.put("totalOutStandingAmount", outAmount);

	    
		 
		 
		 
		 
	    Date endDateNew1 = new Date();

        // Initialize calendar with the end date
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        
	    Calendar calendar11 = Calendar.getInstance();
        calendar11.setTime(endDateNew1);

        calendar11.add(Calendar.DATE, -1);
        calendar11.set(Calendar.HOUR_OF_DAY, 7);
        calendar11.set(Calendar.MINUTE, 59);
        calendar11.set(Calendar.SECOND, 59);
        calendar11.set(Calendar.MILLISECOND, 0);
        // Set time to 23:59 for day1
        
        Calendar calendar111 = Calendar.getInstance();
        calendar111.setTime(endDateNew1);

        // Exclude the current date by moving back one day
        calendar111.add(Calendar.DATE, -1);

        // Print day1 (yesterday) with time 23:59
        Date day11 = calendar11.getTime();
        System.out.println("Date: " + dateFormat.format(day11) + ", day1: " + dayFormat.format(day11));

        // Get the 6 days before yesterday without setting time
        calendar111.add(Calendar.DATE, -1);
        Date day21 = calendar111.getTime();
        System.out.println("Date: " + dateFormat.format(day21) + ", Day: " + dayFormat.format(day21));

        calendar111.add(Calendar.DATE, -1);
        Date day31 = calendar111.getTime();
        System.out.println("Date: " + dateFormat.format(day31) + ", Day: " + dayFormat.format(day31));

        calendar111.add(Calendar.DATE, -1);
        Date day41 = calendar111.getTime();
        System.out.println("Date: " + dateFormat.format(day41) + ", Day: " + dayFormat.format(day41));

        calendar111.add(Calendar.DATE, -1);
        Date day51 = calendar111.getTime();
        System.out.println("Date: " + dateFormat.format(day51) + ", Day: " + dayFormat.format(day51));

        calendar111.add(Calendar.DATE, -1);
        Date day61 = calendar111.getTime();
        System.out.println("Date: " + dateFormat.format(day61) + ", day6: " + dayFormat.format(day61));

        // Get day7 and reset the time to 00:00
        calendar111.add(Calendar.DATE, -1);
        calendar111.set(Calendar.HOUR_OF_DAY, 8);
        calendar111.set(Calendar.MINUTE, 0);
        calendar111.set(Calendar.SECOND, 0);
        calendar111.set(Calendar.MILLISECOND, 0);
        Date day71 = calendar111.getTime();
        System.out.println("Date: " + dateFormat7.format(day71) + ", Day7: " + dayFormat.format(day71));

        
     // Initialize maps for each date
//        Map<String, Object> invoicesDayWise = new HashMap<>();
//        Map<String, Integer> dayWiseCounts = new HashMap<>();
//        Map<String, BigDecimal> dayWiseAmounts = new HashMap<>();
//
//        // Initialize maps with default values (0 count and 0 amount)
//        List<Date> days = Arrays.asList(day11, day21, day31, day41, day51, day61, day71);
//        for (Date day : days) {
//            String formattedDate = dateFormatFcl.format(day);
//            dayWiseCounts.put(formattedDate, 0);
//            dayWiseAmounts.put(formattedDate, BigDecimal.ZERO);
//        }
//
//        // Loop through invoice data
//        SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
//        if (lastWeekInvoiceDetails != null) {
//            for (Object[] row : lastWeekInvoiceDetails) {
//                if (row != null && row.length > 1) { // Ensure row has valid data
//                    try {
//                        // Handle potential casting issues safely
//                        Long count = (row[1] != null) ? ((Number) row[1]).longValue() : 0L;
//                        BigDecimal amount = (row[3] != null) ? new BigDecimal(row[3].toString()) : BigDecimal.ZERO;
//                        Date createdDate = (Date) row[4]; // Ensure proper date parsing
//                        String collectionDateString = dateFormat111.format(createdDate);
//                       
//                        dayWiseCounts.put(collectionDateString, 
//                            dayWiseCounts.getOrDefault(collectionDateString, 0) + count.intValue());
//
//                        dayWiseAmounts.put(collectionDateString, 
//                            dayWiseAmounts.getOrDefault(collectionDateString, BigDecimal.ZERO).add(amount));
//
//                    } catch (Exception e) {
//                        System.out.println("Error processing row: " + Arrays.toString(row));
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        // **Store results in ExpInventoryLast** (Ensure safe data retrieval)
//        for (Date day : days) {
//            String formattedDate = dateFormatFcl.format(day);
//            Map<String, Object> dailyData = new HashMap<>();
//            dailyData.put("count", dayWiseCounts.getOrDefault(formattedDate, 0)); // Ensure default value
//            dailyData.put("amount", dayWiseAmounts.getOrDefault(formattedDate, BigDecimal.ZERO)); // Ensure default value
//            invoicesDayWise.put(formattedDate, dailyData);
//        }
//        invoicesDayWise.put("name", "invoicesDayWise");
        
        Map<String, Object> invoicesDayWise = new HashMap<>();
        Map<String, Integer> dayWiseCounts = new HashMap<>();
        Map<String, BigDecimal> dayWiseAmounts = new HashMap<>();

        // Initialize maps with default values (0 count and 0 amount)
        List<Date> days = Arrays.asList(day11, day21, day31, day41, day51, day61, day71);
        for (Date day : days) {
            String formattedDate = dateFormatFcl.format(day);
            dayWiseCounts.put(formattedDate, 0);
            dayWiseAmounts.put(formattedDate, BigDecimal.ZERO);
        }

        // Loop through invoice data
        SimpleDateFormat dateFormat111 = new SimpleDateFormat("yyyy-MM-dd");
        if (lastWeekInvoiceDetails != null) {
            for (Object[] row : lastWeekInvoiceDetails) {
                if (row != null && row.length > 1) {
                	 Long count = (Long) row[1]; // Invoice count at index 1
                   BigDecimal amount = (BigDecimal) row[3]; // Amount at index 3
                   Date createdDate = (Date) row[4]; // Date at index 4

                    try {
    
                        String createdDateString = dateFormat111.format(createdDate);

                        // Check if createdDate is within the specified week
                        if (isDateBetween(createdDate, day71, day11)) {
                            System.out.println("Valid Entry for: " + createdDateString);
                            dayWiseCounts.put(createdDateString, dayWiseCounts.getOrDefault(createdDateString, 0) + count.intValue());
                            dayWiseAmounts.put(createdDateString, dayWiseAmounts.getOrDefault(createdDateString, BigDecimal.ZERO).add(amount));
                        }
                    } catch (Exception e) {
                        System.out.println("Error processing row: " + Arrays.toString(row));
                        e.printStackTrace();
                    }
                }
            }
        }

        // Store results in ExpInventoryLast
        for (Date day : days) {
            String formattedDate = dateFormatFcl.format(day);
            Map<String, Object> dailyData = new HashMap<>();
            dailyData.put("count", dayWiseCounts.get(formattedDate));
            dailyData.put("amount", dayWiseAmounts.get(formattedDate));
            invoicesDayWise.put(formattedDate, dailyData);
        }

        // Add title
        invoicesDayWise.put("name", "invoicesDayWise");
        
        
        Map<String, Object> invociceCollection = new HashMap<>();
        Map<String, Integer> dayWiseColCounts = new HashMap<>();
        Map<String, BigDecimal> dayWiseColAmounts = new HashMap<>();

        // Initialize maps with default values (0 count and 0 amount)
        List<Date> collectionDays = Arrays.asList(day11, day21, day31, day41, day51, day61, day71);
        for (Date day : collectionDays) {
            String formattedDate = dateFormatFcl.format(day);
            dayWiseColCounts.put(formattedDate, 0);
            dayWiseColAmounts.put(formattedDate, BigDecimal.ZERO);
        }

        if (totalCollectionsLastWeek != null) {
            for (Object[] row : totalCollectionsLastWeek) {
                if (row != null && row.length > 1) {
                	 Long count = (Long) row[2]; // Invoice count at index 1
	                 BigDecimal amount = (BigDecimal) row[3]; // Amount at index 3
	                 
	                 Date createdDate =(Date) row[4];

                    try {
    
                        String createdDateString = dateFormat111.format(createdDate);

                        // Check if createdDate is within the specified week
                        if (isDateBetween(createdDate, day71, day11)) {
                            System.out.println("Valid Entry for: " + createdDateString);
                            dayWiseColCounts.put(createdDateString, dayWiseColCounts.getOrDefault(createdDateString, 0) + count.intValue());
                            dayWiseColAmounts.put(createdDateString, dayWiseColAmounts.getOrDefault(createdDateString, BigDecimal.ZERO).add(amount));
                        }
                    } catch (Exception e) {
                        System.out.println("Error processing row: " + Arrays.toString(row));
                        e.printStackTrace();
                    }
                }
            }
        }

        // Store results in ExpInventoryLast
        for (Date day : collectionDays) {
            String formattedDate = dateFormatFcl.format(day);
            Map<String, Object> dailyData = new HashMap<>();
            dailyData.put("count", dayWiseColCounts.get(formattedDate));
            dailyData.put("amount", dayWiseColAmounts.get(formattedDate));
            invociceCollection.put(formattedDate, dailyData);
        }

        // Add title
        invociceCollection.put("name", "invociceCollection");
        
        
        
        Map<String, Object> outStandingLastWeekMap = new HashMap<>();
        Map<String, Integer> outCounts = new HashMap<>();
        Map<String, BigDecimal>  outAmounts = new HashMap<>();

        // Initialize maps with default values (0 count and 0 amount)
        List<Date> outStandingDays = Arrays.asList(day11, day21, day31, day41, day51, day61, day71);
        for (Date day : outStandingDays) {
            String formattedDate = dateFormatFcl.format(day);
            outCounts.put(formattedDate, 0);
            outAmounts.put(formattedDate, BigDecimal.ZERO);
        }

        if (outStandingLastWeek != null) {
            for (Object[] row : outStandingLastWeek) {
                if (row != null && row.length > 1) {
                	 Long count = (Long) row[2]; // Invoice count at index 1
                     BigDecimal amount = (BigDecimal) row[4]; // Amount at index 3
	                 
	                 Date createdDate =(Date) row[5];

                    try {
    
                        String createdDateString = dateFormat111.format(createdDate);

                        // Check if createdDate is within the specified week
                        if (isDateBetween(createdDate, day71, day11)) {
                            System.out.println("Valid Entry for: " + createdDateString);
                            outCounts.put(createdDateString, outCounts.getOrDefault(createdDateString, 0) + count.intValue());
                            outAmounts.put(createdDateString, outAmounts.getOrDefault(createdDateString, BigDecimal.ZERO).add(amount));
                        }
                    } catch (Exception e) {
                        System.out.println("Error processing row: " + Arrays.toString(row));
                        e.printStackTrace();
                    }
                }
            }
        }

        // Store results in ExpInventoryLast
        for (Date day : outStandingDays) {
            String formattedDate = dateFormatFcl.format(day);
            Map<String, Object> dailyData = new HashMap<>();
            dailyData.put("count", outCounts.get(formattedDate));
            dailyData.put("amount", outAmounts.get(formattedDate));
            outStandingLastWeekMap.put(formattedDate, dailyData);
        }

        // Add title
        outStandingLastWeekMap.put("name", "outStandingLastWeekMap");

		 
		 
		

		

	   
		    
	    // Combine results
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    
	    
	    
//	    result.put("exportLDDMap", exportLDDMap);
	    result.put("invocesRecord", invocesRecord);
	    result.put("collection", collection);
	    result.put("outStandingMap", outStandingMap);
	    result.put("invoicesDayWise", invoicesDayWise);
	    result.put("invociceCollection",invociceCollection);
	    result.put("outStandingLastWeekMap", outStandingLastWeekMap);
	    result.put("pieChart", pieChart);
	    result.put("top10OutStandingChart", top10OutStandingChart);
//	    
	
	    result.put("advanceMap",advanceMap);
//	    result.put("exportStuffTallyData",exportStuffTallyData);
//	    result.put("exportLaddLastWeekData",exportLaddLastWeekData);
//	    result.put("Empty",Empty);
//	    result.put("ExpInventoryLast",ExpInventoryLast);
	    
	    
	   
	    
	   
	    
	    
	    
	    
	    return ResponseEntity.ok(result);
	}
    
    
}
