package com.cwms.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwms.entities.ProcessNextId;
import com.cwms.repository.ProcessNextIdRepository;

import jakarta.transaction.Transactional;

@Service

public class ProcessNextIdService {
	
	@Autowired(required = true)
	public ProcessNextIdRepository processNextIdRepository;

	public List<ProcessNextId> getAllByProcessId(String processId) {
		return processNextIdRepository.findByProcessId(processId);
	}

	public List<ProcessNextId> getAllByProcessId() {
		return processNextIdRepository.findAll();
	}

	public ProcessNextId saveProcessNextId(ProcessNextId processNextId) {
		return processNextIdRepository.save(processNextId);
	}
	
	@Transactional
	public synchronized String autoIncrementPredictableInvoiceId() {

	    // Get the current 15-digit string from the database
	    String currentIdString = processNextIdRepository.findNextPredictableInviceId();

	    // Convert the string to a BigInteger for easy manipulation
	    BigInteger currentId = new BigInteger(currentIdString);

	    // Increment the BigInteger by one
	    BigInteger nextId = currentId.add(BigInteger.ONE);

	    // Ensure the length of the resulting string is 15 digits
	    String nextIdString = String.format("%015d", nextId);

	    // Update the Next_Id directly in the database using the repository
	    processNextIdRepository.updateNextPredictableInviceId(nextIdString);

	    return nextIdString;
	}
	
	

	public String getCurrentYear()
	{		
		LocalDate currentDate = LocalDate.now();
	    LocalTime currentTime = LocalTime.now();
	    LocalTime elevenFiftyNine = LocalTime.of(23, 59, 59);
	    String financialYear;
	    if ((currentDate.getMonthValue() == 3 && currentDate.getDayOfMonth() == 31 && currentTime.isAfter(elevenFiftyNine)) ||
	    	    currentDate.getMonthValue() > 3) {
	    	    int currentYearLastTwoDigits = currentDate.getYear() % 100;
	    	    int nextYearLastTwoDigits = (currentDate.getYear() + 1) % 100;
	    	    financialYear = String.format("%02d%02d", currentYearLastTwoDigits, nextYearLastTwoDigits);
	    	} else {
	    	    int previousYearLastTwoDigits = (currentDate.getYear() - 1) % 100;
	    	    int currentYearLastTwoDigits = currentDate.getYear() % 100;
	    	    financialYear = String.format("%02d%02d", previousYearLastTwoDigits, currentYearLastTwoDigits);
	    	}	    
	    return financialYear;
	}
	
	
	public String getCurrentDashYear() {
	    LocalDate currentDate = LocalDate.now();
	    LocalTime currentTime = LocalTime.now();
	    LocalTime elevenFiftyNine = LocalTime.of(23, 59, 59);
	    String financialYear;

	    if ((currentDate.getMonthValue() == 3 && currentDate.getDayOfMonth() == 31 && currentTime.isAfter(elevenFiftyNine)) ||
	        currentDate.getMonthValue() > 3) {
	        int currentYearLastTwoDigits = currentDate.getYear() % 100;
	        int nextYearLastTwoDigits = (currentDate.getYear() + 1) % 100;
	        financialYear = String.format("%02d-%02d", currentYearLastTwoDigits, nextYearLastTwoDigits);
	    } else {
	        int previousYearLastTwoDigits = (currentDate.getYear() - 1) % 100;
	        int currentYearLastTwoDigits = currentDate.getYear() % 100;
	        financialYear = String.format("%02d-%02d", previousYearLastTwoDigits, currentYearLastTwoDigits);
	    }

	    return financialYear;
	}	
	
	
	public int extractNumericPartBeforeSlash(String pctmNumber) throws NumberFormatException {
	    // Split the string by "/" and extract the first part before the slash
	    String numericPartBeforeSlash = pctmNumber.split("/")[0];
	    
	    return Integer.parseInt(numericPartBeforeSlash);
	}
	
	
//	Invoice Number
	 @Transactional
		public synchronized String autoIncrementInvoiceNumber() {

		 	String financialYear = getCurrentYear();	
		 
	        String lastReceiptNumber = processNextIdRepository.findNextInvoiceNumber();
		    // Extract the last numeric part from the last receipt number
		    int lastNextNumericId = Integer.parseInt(lastReceiptNumber.substring(9)); // Adjusted index to exclude the "/"

		  
		    int nextNumericNextID = lastNextNumericId + 1;

		    // Format the next numeric ID with leading zeros
		    String formattedNextNumericID = String.format("%06d", nextNumericNextID);

		    // Generate the new receipt number
		    String nextinvoiceNo = "SPZ" +"/"+ financialYear + "/" + formattedNextNumericID;	        
	    
			// Update the Next_Id directly in the database using the repository
			processNextIdRepository.updateNextextInvoiceNumber(nextinvoiceNo);

			return nextinvoiceNo;


		}
	
	
//	Receipt Number
	 @Transactional
		public String autoIncrementReceiptNumber() {
			// Get the current date			 	
			    String financialYear = getCurrentYear();			   
			 // Get the last receipt number from the database
			    String lastReceiptNumber = processNextIdRepository.findNextReceiptNumber();

			    // Extract the last numeric part from the last receipt number
			    int lastNextNumericId = Integer.parseInt(lastReceiptNumber.substring(9)); // Adjusted index to exclude the "/"

		    // Increment the last numeric ID
		    int nextNumericNextID = lastNextNumericId + 1;

		    // Format the next numeric ID with leading zeros
		    String formattedNextNumericID = String.format("%06d", nextNumericNextID);

		    // Generate the new receipt number
		    String nextReceiptNumber = "REC" + "/" +  financialYear + "/" + formattedNextNumericID;

		    // Update the next receipt number in the database
		    processNextIdRepository.updateNextextReceiptNumber(nextReceiptNumber);

		    return nextReceiptNumber;
		}
	
	
	@Transactional
	public synchronized String autoIncrementUNCTransId() {
		
		String IMPTransId = processNextIdRepository.findNextuncTransId();
		
		

        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(9));

        int nextNumericNextID = lastNextNumericId + 1;
        String id = IMPTransId.substring(0,9);

        String NextimpTransId = String.format(id+"%06d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextuncTransId(NextimpTransId);

        return NextimpTransId;

	}
	
	
	
	@Transactional
	public synchronized String autoIncrementUNCId() {
		
		String SIRNo = processNextIdRepository.findNextUNCSIRNo();

        int lastNextNumericId = Integer.parseInt(SIRNo.substring(7));

        int nextNumericNextID = lastNextNumericId + 1;
        
        String id = SIRNo.substring(0,7);

        String NextSIRNo = String.format(id+"%06d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextUNCSIRNo(NextSIRNo);

        return NextSIRNo;

	}
	
	
	
	
	
	@Transactional
	public synchronized String autoIncrementSIRId() {
		
		String SIRNo = processNextIdRepository.findNextSIRNo();

        int lastNextNumericId = Integer.parseInt(SIRNo.substring(6));

        int nextNumericNextID = lastNextNumericId + 1;
        
        String id = SIRNo.substring(0,6);

        String NextSIRNo = String.format(id+"%06d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextSIRNo(NextSIRNo);

        return NextSIRNo;

	}
	
	
	@Transactional
	public synchronized String autoIncrementIMPTransId() {
		
		String IMPTransId = processNextIdRepository.findNextimpTransId();
		
		

        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(8));

        int nextNumericNextID = lastNextNumericId + 1;
        String id = IMPTransId.substring(0,8);

        String NextimpTransId = String.format(id+"%05d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextimpTransId(NextimpTransId);

        return NextimpTransId;

	}
	
	
	@Transactional
	public synchronized String autoIncrementSubImpId( ) {
		
		String IMPTransId = processNextIdRepository.findNextsubimpid();

        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(8));

        int nextNumericNextID = lastNextNumericId + 1;
        String id = IMPTransId.substring(0,8);

        String NextimpTransId = String.format(id+"%06d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNexsubimpid(NextimpTransId);

        return NextimpTransId;

	}
	
	@Transactional
	public String autoIncrementSIRExportId() {
	    synchronized (this) {
	        String SIRNo = processNextIdRepository.findNextSIRExportNo();
	        int lastNextNumericId = Integer.parseInt(SIRNo.substring(6));
	        int nextNumericNextID = lastNextNumericId + 1;
	        String id = SIRNo.substring(0,6);
	        String NextSIRNo = String.format(id+"%06d", nextNumericNextID);
	        processNextIdRepository.updateNextSIRExportNo(NextSIRNo);
	        return NextSIRNo;
	    }
	}
	
	
	@Transactional
	public synchronized String autoIncrementSubImpTransId( ) {
		
		String IMPTransId = processNextIdRepository.findNextsubimptransid();

        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(7));

        int nextNumericNextID = lastNextNumericId + 1;
        String id = IMPTransId.substring(0,7);
        String NextimpTransId = String.format(id+"%05d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNexsubimptransid(NextimpTransId);

        return NextimpTransId;

	}
	
	@Transactional
	public synchronized String autoIncrementSubExpId( ) {
		
		String IMPTransId = processNextIdRepository.findNextsubexpid();

        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(8));

        int nextNumericNextID = lastNextNumericId + 1;
        String id = IMPTransId.substring(0,8);
        String NextimpTransId = String.format(id+"%06d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNexsubexpid(NextimpTransId);

        return NextimpTransId;

	}
	
	@Transactional
	public synchronized String autoIncrementSubExpTransId( ) {
		
		String IMPTransId = processNextIdRepository.findNextsubexptransid();

        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(7));
        String id = IMPTransId.substring(0,7);

        int nextNumericNextID = lastNextNumericId + 1;

        String NextimpTransId = String.format(id+"%05d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNexsubexptransid(NextimpTransId);

        return NextimpTransId;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public String autoIncrementProcessId() {
//
//		String maxProcessID = processNextIdRepository.findLastProcessId();
//
//		int lastNumericId = Integer.parseInt(maxProcessID.substring(1));
//
//		int nextNumericId = lastNumericId + 1;
//
//		String nextProcessId = String.format("P%05d", nextNumericId);
//
//		return nextProcessId;
//
//		// String processID="POOOO1";
//
//	}
//
//	public String autoIncrementNextId() {
//		String maxNextId = processNextIdRepository.findLastNextId();
//
//		int lastNextNumericId = Integer.parseInt(maxNextId.substring(4));
//
//		int nextNumericNextID = lastNextNumericId + 1;
//
//		String nextNextId = String.format("BKTQ%06d", nextNumericNextID);
//		return nextNextId;
//
//	}
	@Transactional
	public synchronized String autoIncrementNextIdNext() {
//		String NextId = processNextIdRepository.findNextId();
//
//		int lastNextNumericId = Integer.parseInt(NextId.substring(4));
//
//		int nextNumericNextID = lastNextNumericId + 1;
//
//		String NextIdD = String.format("H%05d", nextNumericNextID);
//		processNextIdRepository.updateNextId(NextIdD);
//		return NextIdD;
		
		
		String nextId = processNextIdRepository.findNextId();

        int lastNextNumericId = Integer.parseInt(nextId.substring(1));

        int nextNumericNextID = lastNextNumericId + 1;

        String nextIdD = String.format("M%05d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextId(nextIdD);

        return nextIdD;

	}
	
	@Transactional
	public synchronized String autoIncrementNextIdHoliday() {
//		String NextId = processNextIdRepository.findNextId();
//
//		int lastNextNumericId = Integer.parseInt(NextId.substring(4));
//
//		int nextNumericNextID = lastNextNumericId + 1;
//
//		String NextIdD = String.format("H%05d", nextNumericNextID);
//		processNextIdRepository.updateNextId(NextIdD);
//		return NextIdD;
		
		
		String nextholi = processNextIdRepository.findNextIdforHoliday();

        int lastNextNumericIdh = Integer.parseInt(nextholi.substring(1));

        int nextNumericNextIDh = lastNextNumericIdh + 1;

        String nextIdDholi = String.format("H%05d", nextNumericNextIDh);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextIdforHoliday(nextIdDholi);

        return nextIdDholi;

	}

	@Transactional
	 public synchronized String autoIncrementMailId() {
	  
	  String nextMailId = processNextIdRepository.findNextMailId();

	        int lastNextNumericId = Integer.parseInt(nextMailId.substring(4));

	        int nextNumericNextID = lastNextNumericId + 1;

	        String MailId = String.format("MAIL%06d", nextNumericNextID);
	        // Update the Next_Id directly in the database using the repository
	        processNextIdRepository.updateNextMailId(MailId);

	        return MailId;

	 }

	@Transactional
	public synchronized String autoIncrementNextJarIdNext(String compnayId) {

		
		String nextJarId = processNextIdRepository.findNextReceiptTransId(compnayId,"B00001","P00001","2006");

        int lastNextNumericId = Integer.parseInt(nextJarId.substring(1));

        int nextNumericNextID = lastNextNumericId + 1;

        String nextJD = String.format("J%05d", nextNumericNextID);

        processNextIdRepository.updateNetReceiptTransId(nextJD,compnayId,"B00001","P00001","2006");

        return nextJD;

	}


	
	@Transactional
	public synchronized String autoIncrementReprsentiveiD() {
		
		String nextReId = processNextIdRepository.findNextReId();

        int lastNextNumericId = Integer.parseInt(nextReId.substring(1));

        int nextNumericNextID = lastNextNumericId + 1;

        String ReId = String.format("R%05d", nextNumericNextID);
        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextReId(ReId);

        return ReId;

	}
	
	
	
	@Transactional
	public synchronized String autoIncrementServiceNextId() {
		
		String serviceId = processNextIdRepository.findNextServiceId();

        int lastNextNumericId = Integer.parseInt(serviceId.substring(1));

        int nextNumericNextID = lastNextNumericId + 1;

        String serviceNextIdD = String.format("S%05d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextServiceId(serviceNextIdD);

        return serviceNextIdD;

	}
	
	@Transactional
	public synchronized String autoIncrementCFSTarrifNextId() {
		
		String CFSTarrifNo = processNextIdRepository.findNextCFSTarrifNo();

        int lastNextNumericId = Integer.parseInt(CFSTarrifNo.substring(4));

        int nextNumericNextID = lastNextNumericId + 1;

        String CFSTTArrifNextIdD = String.format("CFST%06d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextCFSTarrifNo(CFSTTArrifNextIdD);

        return CFSTTArrifNextIdD;

	}
	
//	@Transactional
//	public synchronized String autoIncrementSIRId() {
//		
//		String SIRNo = processNextIdRepository.findNextSIRNo();
//
//        int lastNextNumericId = Integer.parseInt(SIRNo.substring(2));
//
//        int nextNumericNextID = lastNextNumericId + 1;
//
//        String NextSIRNo = String.format("IM%06d", nextNumericNextID);
//
//        // Update the Next_Id directly in the database using the repository
//        processNextIdRepository.updateNextSIRNo(NextSIRNo);
//
//        return NextSIRNo;
//
//	}
//	
//	
//	@Transactional
//	public synchronized String autoIncrementIMPTransId() {
//		
//		String IMPTransId = processNextIdRepository.findNextimpTransId();
//
//        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(4));
//
//        int nextNumericNextID = lastNextNumericId + 1;
//
//        String NextimpTransId = String.format("IMPT%04d", nextNumericNextID);
//
//        // Update the Next_Id directly in the database using the repository
//        processNextIdRepository.updateNextimpTransId(NextimpTransId);
//
//        return NextimpTransId;
//
//	}
//	
//	
//	@Transactional
//	public synchronized String autoIncrementSubImpId( ) {
//		
//		String IMPTransId = processNextIdRepository.findNextsubimpid();
//
//        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(4));
//
//        int nextNumericNextID = lastNextNumericId + 1;
//
//        String NextimpTransId = String.format("D-IM%06d", nextNumericNextID);
//
//        // Update the Next_Id directly in the database using the repository
//        processNextIdRepository.updateNexsubimpid(NextimpTransId);
//
//        return NextimpTransId;
//
//	}
//	
//	@Transactional
//	public String autoIncrementSIRExportId() {
//	    synchronized (this) {
//	        String SIRNo = processNextIdRepository.findNextSIRExportNo();
//	        int lastNextNumericId = Integer.parseInt(SIRNo.substring(2));
//	        int nextNumericNextID = lastNextNumericId + 1;
//	        String NextSIRNo = String.format("EX%06d", nextNumericNextID);
//	        processNextIdRepository.updateNextSIRExportNo(NextSIRNo);
//	        return NextSIRNo;
//	    }
//	}
//	
//	
//	@Transactional
//	public synchronized String autoIncrementSubImpTransId( ) {
//		
//		String IMPTransId = processNextIdRepository.findNextsubimptransid();
//
//        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(3));
//
//        int nextNumericNextID = lastNextNumericId + 1;
//
//        String NextimpTransId = String.format("SIM%05d", nextNumericNextID);
//
//        // Update the Next_Id directly in the database using the repository
//        processNextIdRepository.updateNexsubimptransid(NextimpTransId);
//
//        return NextimpTransId;
//
//	}
//	
//	@Transactional
//	public synchronized String autoIncrementSubExpId( ) {
//		
//		String IMPTransId = processNextIdRepository.findNextsubexpid();
//
//        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(4));
//
//        int nextNumericNextID = lastNextNumericId + 1;
//
//        String NextimpTransId = String.format("D-EX%06d", nextNumericNextID);
//
//        // Update the Next_Id directly in the database using the repository
//        processNextIdRepository.updateNexsubexpid(NextimpTransId);
//
//        return NextimpTransId;
//
//	}
//	
//	@Transactional
//	public synchronized String autoIncrementSubExpTransId( ) {
//		
//		String IMPTransId = processNextIdRepository.findNextsubexptransid();
//
//        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(3));
//
//        int nextNumericNextID = lastNextNumericId + 1;
//
//        String NextimpTransId = String.format("SER%05d", nextNumericNextID);
//
//        // Update the Next_Id directly in the database using the repository
//        processNextIdRepository.updateNexsubexptransid(NextimpTransId);
//
//        return NextimpTransId;
//
//	}
	
	
	
	
	
	
	private static final String COMMON_PREFIX = "000";
	private static final String FIXED_PART = "/23-24";
    private static final String SEPARATOR = "/";
    private static final String PREFIX = COMMON_PREFIX + SEPARATOR;
                                                          
//	@Transactional
//	public synchronized String generateAndIncrementPCTMNumber() {
//	    String nextPCTMNumber = processNextIdRepository.findNextPctmNo();
//
//	    // Extract the fixed part from the constant
//	    String fixedPart = FIXED_PART;
//
//	    // Extract and increment the numeric part
//	    int lastNumericPart = extractNumericPart(nextPCTMNumber);
//	    int nextNumericPart = lastNumericPart + 1;
//
//	    // Format the numeric part with zero-padding
//	    String formattedNumericPart = formatNumericPart(nextNumericPart);
//
//	    // Combine the formatted numeric part, slash, and the rest of the string to create the new PCTM number
//	    String newPCTMNumber = formattedNumericPart + "/" + nextPCTMNumber.substring(9);
//
//	    // Update the Next_PCTM_Number directly in the database using the repository
//	    processNextIdRepository.updateNextPctmNo(newPCTMNumber);
//
//	    return newPCTMNumber;
//	}
    
    
    
    

	public int extractNumericPart(String pctmNumber) throws NumberFormatException {
	    // Use a regular expression to match the expected format "00000000/23-24"
	    Pattern pattern = Pattern.compile("(\\d{8}/\\d{2}-\\d{2})");
	    Matcher matcher = pattern.matcher(pctmNumber);

	    if (!matcher.matches()) {
	        throw new NumberFormatException("Invalid PCTM Number format: " + pctmNumber);
	    }

	    // Extract the numeric part
	    String numericPart = pctmNumber.substring(0, 8); // Assuming "00000000/23-24", this extracts "00000000"

	    return Integer.parseInt(numericPart);
	}

	private String formatNumericPart(int numericPart) {
	    return String.format("%08d", numericPart); // Assuming you want an 8-digit zero-padded numeric part
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Transactional
//	public synchronized String generateAndIncrementTPumber() {
//	    String nextTPumber = processNextIdRepository.findNexttpNo();
//
//	    // Extract the fixed part from the constant
//	    String fixedPart = FIXED_PART;
//
//	    // Extract and increment the numeric part
//	    int lastNumericPartTp = extractNumericPart(nextTPumber);
//	    int nextNumericPartTP = lastNumericPartTp + 1;
//
//	    // Format the numeric part with zero-padding
//	    String formattedNumericPart = formatNumericPart(nextNumericPartTP);
//
//	    // Combine the formatted numeric part, slash, and the rest of the string to create the new PCTM number
//	    String newTPNumber = formattedNumericPart + "/" + nextTPumber.substring(9);
//
//	    // Update the Next_PCTM_Number directly in the database using the repository
//	    processNextIdRepository.updateNexttpNo(newTPNumber);
//
//	    return newTPNumber;
//	}

	
	@Transactional
	public synchronized String generateAndIncrementTPumber() {
	
		 String lastValue = processNextIdRepository.findNexttpNo();

	        // Get the current date
	        LocalDate currentDate = LocalDate.now();

	        // Determine the year part based on the current date
	        String yearPart = determineYearPart(currentDate);

	        // Increment the last value
	        String nextValue = incrementTPNo(lastValue, yearPart);

	        // Update the database with the new value
	        processNextIdRepository.updateNexttpNo(nextValue);

	        return nextValue;
	}
	
	@Transactional
	public synchronized String generateAndIncrementPCTMNumber() {
//	    String nextPCTMNumber = processNextIdRepository.findNextPctmNo();
//
//	    // Extract the fixed part from the constant
//	    String fixedPart = FIXED_PART;
//
//	    // Extract and increment the numeric part
//	    int lastNumericPart = extractNumericPart(nextPCTMNumber);
//	    int nextNumericPart = lastNumericPart + 1;
//
//	    // Format the numeric part with zero-padding
//	    String formattedNumericPart = formatNumericPart(nextNumericPart);
//
//	    // Combine the formatted numeric part, slash, and the rest of the string to create the new PCTM number
//	    String newPCTMNumber = formattedNumericPart + "/" + nextPCTMNumber.substring(9);
//
//	    // Update the Next_PCTM_Number directly in the database using the repository
//	    processNextIdRepository.updateNextPctmNo(newPCTMNumber);
//
//	    return newPCTMNumber;
		
		
		 String lastValue = processNextIdRepository.findNextPctmNo();

	        // Get the current date
	        LocalDate currentDate = LocalDate.now();

	        // Determine the year part based on the current date
	        String yearPart = determineYearPart(currentDate);

	        // Increment the last value
	        String nextValue = incrementPctmNo(lastValue, yearPart);

	        // Update the database with the new value
	        processNextIdRepository.updateNextPctmNo(nextValue);

	        return nextValue;
	}
	
	
	
	
	public int extractNumericPartOfTp(String tpNumber) throws NumberFormatException {
	    // Use a regular expression to match the expected format "00000000/23-24"
	    Pattern pattern = Pattern.compile("(\\d{8}/\\d{2}-\\d{2})");
	    Matcher matcher = pattern.matcher(tpNumber);

	    if (!matcher.matches()) {
	        throw new NumberFormatException("Invalid PCTM Number format: " + tpNumber);
	    }

	    // Extract the numeric part
	    String numericPartTP = tpNumber.substring(0, 8); // Assuming "00000000/23-24", this extracts "00000000"

	    return Integer.parseInt(numericPartTP);
	}

	private String formatNumericPartTP(int numericPartTP) {
	    return String.format("%08d", numericPartTP); // Assuming you want an 8-digit zero-padded numeric part
	}
	
	
	
	@Transactional
	public synchronized String autoIncrementExternalUserId() {
		
		String SIRNo = processNextIdRepository.findNextexternalUserId();

        int lastNextNumericId = Integer.parseInt(SIRNo.substring(2));

        int nextNumericNextID = lastNextNumericId + 1;

        String NextSIRNo = String.format("EU%04d", nextNumericNextID);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextexternalUserId(NextSIRNo);

        return NextSIRNo;

	}
	
	@Transactional
	public String autoIncrementDetentionId( ) {
		
		String detentionId = processNextIdRepository.findDetentionId();

        int lastNextNumericId = Integer.parseInt(detentionId.substring(1));

        int nextNumericDeten = lastNextNumericId + 1;

        String NextidetentionId = String.format("D%09d", nextNumericDeten);

        // Update the Next_Id directly in the database using the repository
        processNextIdRepository.updateNextDetentionId(NextidetentionId);

        return NextidetentionId;

	}
	
//	@Transactional
//	public synchronized String autoIncrementDoNumber() {
//
//		String doNumber = processNextIdRepository.findDoNumber();
//
//		int lastNextNumericId = Integer.parseInt(doNumber);
//		lastNextNumericId = lastNextNumericId+1;
//		String nextDoNumber = String.valueOf(lastNextNumericId);
//
//		// Update the Next_Id directly in the database using the repository
//		processNextIdRepository.updateNextDoNumber(nextDoNumber);
//
//		return doNumber;
//
//	}
	
	 public String getNextPctmNo() {
	        // Get the last value from the database
	        String lastValue = processNextIdRepository.findNextEXPPctmNo();

	        // Get the current date
	        LocalDate currentDate = LocalDate.now();

	        // Determine the year part based on the current date
	        String yearPart = determineYearPart(currentDate);

	        // Increment the last value
	        String nextValue = incrementPctmNo(lastValue, yearPart);

	        // Update the database with the new value
	        processNextIdRepository.updateNextEXPPctmNo(nextValue);

	        return nextValue;
	    }

	 private String determineYearPart(LocalDate currentDate) {
		    LocalDate april1 = LocalDate.of(currentDate.getYear(), Month.APRIL, 1);

		    if (currentDate.isAfter(april1) || currentDate.isEqual(april1)) {
		        // If the current date is on or after April 1, use the current year and the next year
		        return (currentDate.getYear() % 100) + "-" + ((currentDate.getYear() % 100) + 1);
		    } else {
		        // If the current date is before April 1, use the previous year and the current year
		        return ((currentDate.getYear() - 1) % 100) + "-" + (currentDate.getYear() % 100);
		    }
		}


	    private String incrementPctmNo(String lastValue, String yearPart) {
	        if (lastValue == null || lastValue.isEmpty()) {
	            // Handle the case where the last value is not found or empty
	            // You can start with the initial value, e.g., "000000/23-24"
	            return "000000/" + yearPart;
	        }

	        // Split the last value into parts
	        String[] parts = lastValue.split("/");

	        if (parts.length != 2) {
	            // Handle the case where the last value is not in the expected format
	            // You can throw an exception or handle it as needed
	            throw new IllegalArgumentException("Invalid last value format: " + lastValue);
	        }

	        int numericPart = Integer.parseInt(parts[0]);

	        // Increment the numeric part
	        numericPart++;

	        // Format the incremented value
	        String incrementedValue = String.format("%d/%s", numericPart, yearPart);

	        return incrementedValue;
	    }
	    
		
		@Transactional
		public synchronized String autoIncrementGateInId( ) {
			
			String detentionId = processNextIdRepository.findGateInId();

	        int lastNextNumericId = Integer.parseInt(detentionId.substring(2));

	        int nextNumericDeten = lastNextNumericId + 1;

	        String NextidetentionId = String.format("GI%04d", nextNumericDeten);

	        // Update the Next_Id directly in the database using the repository
	        processNextIdRepository.updateGateInId(NextidetentionId);

	        return NextidetentionId;

		}
	
	    
	    public String getNextTPNo() {
	        // Get the last value from the database
	        String lastValue = processNextIdRepository.findNextEXPtpNo();

	        // Get the current date
	        LocalDate currentDate = LocalDate.now();

	        // Determine the year part based on the current date
	        String yearPart = determineYearPart(currentDate);

	        // Increment the last value
	        String nextValue = incrementTPNo(lastValue, yearPart);

	        // Update the database with the new value
	        processNextIdRepository.updateNextEXPtpNo(nextValue);

	        return nextValue;
	    }



	    private String incrementTPNo(String lastValue, String yearPart) {
	        if (lastValue == null || lastValue.isEmpty()) {
	            // Handle the case where the last value is not found or empty
	            // You can start with the initial value, e.g., "000000/23-24"
	            return "000000/" + yearPart;
	        }

	        // Split the last value into parts
	        String[] parts = lastValue.split("/");

	        if (parts.length != 2) {
	            // Handle the case where the last value is not in the expected format
	            // You can throw an exception or handle it as needed
	            throw new IllegalArgumentException("Invalid last value format: " + lastValue);
	        }

	        int numericPart = Integer.parseInt(parts[0]);

	        // Increment the numeric part
	        numericPart++;

	        // Format the incremented value
	        String incrementedValue = String.format("%d/%s", numericPart, yearPart);

	        return incrementedValue;
	    }
	    
//	    @Transactional
//		public synchronized String autoIncrementInvoiceNumber() {
//			
////			String SIRNo = processNextIdRepository.findNextInvoiceNumber();
////
////	        int lastNextNumericId = Integer.parseInt(SIRNo.substring(2));
////
////	        int nextNumericNextID = lastNextNumericId + 1;
//
////	        String NextInvoiceNumber = String.format("IN%04d", nextNumericNextID);
//	        
//	        
//	        String invoiceNo = processNextIdRepository.findNextInvoiceNumber();
//
//			int lastNextNumericId = Integer.parseInt(invoiceNo);
//			int newNextNumericId = lastNextNumericId+1;
//			String nextinvoiceNo = String.valueOf(newNextNumericId);
//
//			// Update the Next_Id directly in the database using the repository
//			processNextIdRepository.updateNextextInvoiceNumber(nextinvoiceNo);
//
//			return nextinvoiceNo;
//
//	        // Update the Next_Id directly in the database using the repository
////	        processNextIdRepository.updateNextextInvoiceNumber(nextNumericNextID);
//
////	        return nextDoNumber;
//
//		}
		
//		 Bill Number
		
		@Transactional
		public synchronized String autoIncrementBillNumber() {
			
			String SIRNo = processNextIdRepository.findNextBillNumber();

	        int lastNextNumericId = Integer.parseInt(SIRNo.substring(2));

	        int nextNumericNextID = lastNextNumericId + 1;

	        String NextBillNumber = String.format("BL%04d", nextNumericNextID);

	        // Update the Next_Id directly in the database using the repository
	        processNextIdRepository.updateNextextBillNumber(NextBillNumber);

	        return NextBillNumber;

		}
		
		
		@Transactional
		public synchronized String autoIncrementRepresentativePartyId() {

			
			String nextRepresentativePartyId = processNextIdRepository.findNextRepresentativePartyId();

	        int lastNextNumericId = Integer.parseInt(nextRepresentativePartyId.substring(2));

	        int nextNumericNextID = lastNextNumericId + 1;

	        String nextID = String.format("RI%04d", nextNumericNextID);

	        processNextIdRepository.updateNextRepresentativePartyId(nextID);

	        return nextID;

		}	
		
//		@Transactional
//		public String autoIncrementReceiptNumber() {
//			
//			String receiptNumber = processNextIdRepository.findNextReceiptNumber();
//
//	       int lastNextNumericId = Integer.parseInt(receiptNumber.substring(2));
//
//	       int nextNumericNextID = lastNextNumericId + 1;
//
//	       String NextReceiptNumber = String.format("RE%04d", nextNumericNextID);
//
//	       // Update the Next_Id directly in the database using the repository
//	       processNextIdRepository.updateNextextReceiptNumber(NextReceiptNumber);
//
//	       return NextReceiptNumber;
//
//		}
//		
		
		@Transactional
		public String autoIncrementProformaNo() {

			
			String nextRepresentativePartyId = processNextIdRepository.findNextProformaId();

	        int lastNextNumericId = Integer.parseInt(nextRepresentativePartyId.substring(2));

	        int nextNumericNextID = lastNextNumericId + 1;

	        String nextID = String.format("PR%04d", nextNumericNextID);

	        processNextIdRepository.updateNextProformaId(nextID);

	        return nextID;

		}
		
		//Personal Gate Pass
		
		
				@Transactional
				 public synchronized String autoIncrementGatePassId() {
				  
				  String nextMailId = processNextIdRepository.findNextPCGatePassId();

				        int lastNextNumericId = Integer.parseInt(nextMailId.substring(2));

				        int nextNumericNextID = lastNextNumericId + 1;

				        String MailId = String.format("GP%06d", nextNumericNextID);
				        // Update the Next_Id directly in the database using the repository
				        processNextIdRepository.updateNextPCGatePassId(MailId);

				        return MailId;

				 }
				
				@Transactional
				 public synchronized String autoIncrementMOPGatePassId() {
				  
				  String nextMailId = processNextIdRepository.findNextMOPGatePassId();

				        int lastNextNumericId = Integer.parseInt(nextMailId.substring(3));

				        int nextNumericNextID = lastNextNumericId + 1;

				        String MailId = String.format("MOP%05d", nextNumericNextID);
				        // Update the Next_Id directly in the database using the repository
				        processNextIdRepository.updateNextMOPGatePassId(MailId);

				        return MailId;

				 }
				
				
//				@Transactional
//				 public synchronized String autoIncrementCommonGatePassId() {
//				  
//				  String nextMailId = processNextIdRepository.findNextCommonGatePassId();
//
//				        int lastNextNumericId = Integer.parseInt(nextMailId.substring(3));
//
//				        int nextNumericNextID = lastNextNumericId + 1;
//
//				        String MailId = String.format("COM%05d", nextNumericNextID);
//				        // Update the Next_Id directly in the database using the repository
//				        processNextIdRepository.updateNextCommonGatePassId(MailId);
//
//				        return MailId;
//
//				 }
				
				
				@Transactional
				 public synchronized String autoIncrementCommonGatePassId() {
				  
//				  String nextMailId = processNextIdRepository.findNextCommonGatePassId();
//
//				        int lastNextNumericId = Integer.parseInt(nextMailId.substring(3));
//
//				        int nextNumericNextID = lastNextNumericId + 1;
//
//				        String MailId = String.format("COM%05d", nextNumericNextID);
//				        // Update the Next_Id directly in the database using the repository
//				        processNextIdRepository.updateNextCommonGatePassId(MailId);
//
//				        return MailId;
//				        
				        
				        
				        
				        String SIRNo = processNextIdRepository.findNextCommonGatePassId();

				        int lastNextNumericId = Integer.parseInt(SIRNo.substring(7));

				        int nextNumericNextID = lastNextNumericId + 1;
				        
				        String id = SIRNo.substring(0,7);

				        String NextSIRNo = String.format(id+"%05d", nextNumericNextID);

				        // Update the Next_Id directly in the database using the repository
				        processNextIdRepository.updateNextCommonGatePassId(NextSIRNo);

				        return NextSIRNo;

				 }
				
				@Transactional
				 public synchronized String autoIncrementCombineRepresentativeId() {
				  
				  String nextMailId = processNextIdRepository.findNextCombineReprentativeId();

				        int lastNextNumericId = Integer.parseInt(nextMailId.substring(4));

				        int nextNumericNextID = lastNextNumericId + 1;

				        String MailId = String.format("CR%04d", nextNumericNextID);
				        // Update the Next_Id directly in the database using the repository
				        processNextIdRepository.updateNextCombineReprentativeId(MailId);

				        return MailId;

				 }
		
//				@Transactional
//				 public synchronized String autoIncrementPredictableInvoiceId() {
//				  
//				  String nextMailId = processNextIdRepository.findNextPredictableInviceId();
//
//				        int lastNextNumericId = Integer.parseInt(nextMailId.substring(4));
//
//				        int nextNumericNextID = lastNextNumericId + 1;
//
//				        String MailId = String.format("PRIN%06d", nextNumericNextID);
//				        // Update the Next_Id directly in the database using the repository
//				        processNextIdRepository.updateNextPredictableInviceId(MailId);
//
//				        return MailId;
//
//				 }
				
				
				@Transactional
				public synchronized String autoIncrementDoNumber() {
					
					String IMPTransId = processNextIdRepository.findDoNumber();

			        int lastNextNumericId = Integer.parseInt(IMPTransId.substring(5));

			        int nextNumericNextID = lastNextNumericId + 1;
			        String id = IMPTransId.substring(0,5);
			        String NextimpTransId = String.format(id+"%05d", nextNumericNextID);

			        // Update the Next_Id directly in the database using the repository
			        processNextIdRepository.updateNextDoNumber(NextimpTransId);

			        return NextimpTransId;

					
					//

//					String doNumber = processNextIdRepository.findDoNumber();
			//
//					int lastNextNumericId = Integer.parseInt(doNumber);
//					lastNextNumericId = lastNextNumericId+1;
//					String nextDoNumber = String.valueOf(lastNextNumericId);
			//
//					// Update the Next_Id directly in the database using the repository
//					processNextIdRepository.updateNextDoNumber(nextDoNumber);
			//
//					return doNumber;

				}
				
				
				@Transactional
				public synchronized String autoIncrementPortTransId(String companyId, String branchId, String processId) {
				    // Get the current financial year
				    String financialYear = getCurrentYear();

				    // Find the next vehicle ID from the database
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    // Extract the first two characters and the last 5 digits from nextholi
				    String firstTwoCharacters = nextholi.substring(0, 2);
				    String lastFiveDigits = nextholi.substring(nextholi.length() - 5);
				    System.out.println("financialYear: " + financialYear + " firstTwoCharacters: " + firstTwoCharacters + " lastFiveDigits: " + lastFiveDigits);

				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%05d", incrementedNumber);

				    // Concatenate the first two characters, the financial year, and the formatted incremented number
				    String nextIdDholi = firstTwoCharacters + financialYear + formattedIncrementedNumber;

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				
				@Transactional
				public synchronized String autoIncrementNextProfitCenterId(String compnayId,String branchId) {

					
					String nextProfitid = processNextIdRepository.findNextReceiptTransId(compnayId,branchId,"P05058","2025");

			        int lastNextNumericId = Integer.parseInt(nextProfitid.substring(1));

			        int nextNumericNextID = lastNextNumericId + 1;

			        String nextJD = String.format("N%05d", nextNumericNextID);

			        processNextIdRepository.updateNetReceiptTransId(nextJD,compnayId,branchId,"P05058","2025");

			        return nextJD;

				}
				
				
				@Transactional
				public synchronized String autoIncrementServiceNextId(String compnayId,String branchId) {
					
					String serviceId = processNextIdRepository.findNextReceiptTransId(compnayId,branchId,"P05056","2233");

			        int lastNextNumericId = Integer.parseInt(serviceId.substring(1));

			        int nextNumericNextID = lastNextNumericId + 1;

			        String serviceNextIdD = String.format("S%05d", nextNumericNextID);

			        // Update the Next_Id directly in the database using the repository
			        processNextIdRepository.updateNetReceiptTransId(serviceNextIdD,compnayId,branchId,"P05056","2233");

			        return serviceNextIdD;

				}
				
				@Transactional
				public synchronized String autoSBTransId(String companyId, String branchId, String processId) {
				    // Get the current financial year
				 
				    // Find the next vehicle ID from the database
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    // Extract the first two characters and the last 5 digits from nextholi
				    String firstTwoCharacters = nextholi.substring(0, 4);
				    String lastFiveDigits = nextholi.substring(nextholi.length() - 6);
				    System.out.println("financialYear: firstTwoCharacters: " + firstTwoCharacters + " lastFiveDigits: " + lastFiveDigits);

				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%06d", incrementedNumber);

				    // Concatenate the first two characters, the financial year, and the formatted incremented number
				    String nextIdDholi = firstTwoCharacters  + formattedIncrementedNumber;

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				
				@Transactional
				public synchronized String autoExportGateInId(String companyId, String branchId, String processId) {
				    // Find the next vehicle ID from the database
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    // Extract the first two characters and the last 5 digits from nextholi
				    String firstTwoCharacters = nextholi.substring(0, 4);
				    String lastFiveDigits = nextholi.substring(nextholi.length() - 6);
				    System.out.println("financialYear: firstTwoCharacters: " + firstTwoCharacters + " lastFiveDigits: " + lastFiveDigits);

				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%06d", incrementedNumber);

				    // Concatenate the first two characters, the financial year, and the formatted incremented number
				    String nextIdDholi = firstTwoCharacters  + formattedIncrementedNumber;

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				@Transactional
				public synchronized String autoCartingTransId(String companyId, String branchId, String processId) {
				    // Get the current financial year
				 
				    // Find the next vehicle ID from the database
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    String firstTwoCharacters = nextholi.substring(0, 3);
				    String lastFiveDigits = nextholi.substring(nextholi.length() - 7);
				    System.out.println("financialYear: firstTwoCharacters: " + firstTwoCharacters + " lastFiveDigits: " + lastFiveDigits);

				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%07d", incrementedNumber);

				    // Concatenate the first two characters, the financial year, and the formatted incremented number
				    String nextIdDholi = firstTwoCharacters  + formattedIncrementedNumber;

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				@Transactional
				public synchronized String autoExportStuffingId(String companyId, String branchId, String processId) {
				    // Find the next vehicle ID from the database CPAQ054141
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    // Extract the first two characters and the last 5 digits from nextholi
				    String firstTwoCharacters = nextholi.substring(0, 4);
				    String lastFiveDigits = nextholi.substring(nextholi.length() - 6);
				    System.out.println("financialYear: firstTwoCharacters: " + firstTwoCharacters + " lastFiveDigits: " + lastFiveDigits);

				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%06d", incrementedNumber);

				    // Concatenate the first two characters, the financial year, and the formatted incremented number
				    String nextIdDholi = firstTwoCharacters  + formattedIncrementedNumber;

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				@Transactional
				public synchronized String autoIncrementVesselId(String companyId, String branchId, String processId) {
				    // Find the next vehicle ID from the database
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);
				    
				    // Extract the first character and the last 5 digits
				    char firstCharacter = nextholi.charAt(0); // Get the first character (e.g., 'V')
				    String lastFiveDigits = nextholi.substring(1); // Get everything after the first character (e.g., '00005')
				    
				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%05d", incrementedNumber); // Format to 5 digits

				    // Concatenate the first character with the formatted incremented number
				    String nextIdDholi = firstCharacter + formattedIncrementedNumber; // Recreate the ID

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				@Transactional
				public synchronized String autoExportMovementId(String companyId, String branchId, String processId) {
				    // Find the next vehicle ID from the database CPAQ054141
				    String nextholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    // Extract the first two characters and the last 5 digits from nextholi
				    String firstTwoCharacters = nextholi.substring(0, 3);
				    String lastFiveDigits = nextholi.substring(nextholi.length() - 7);
				    System.out.println("financialYear: firstTwoCharacters: " + firstTwoCharacters + " lastFiveDigits: " + lastFiveDigits);

				    // Increment the last 5 digits by 1
				    int incrementedNumber = Integer.parseInt(lastFiveDigits) + 1;

				    // Format the incremented number to ensure it has 5 digits
				    String formattedIncrementedNumber = String.format("%07d", incrementedNumber);

				    // Concatenate the first two characters, the financial year, and the formatted incremented number
				    String nextIdDholi = firstTwoCharacters  + formattedIncrementedNumber;

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, nextIdDholi);

				    return nextIdDholi;
				}
				
				@Transactional
				public synchronized String autoExportGateInJobOrderId(String companyId, String branchId, String processId) {
				    // Find the next vehicle ID from the database
				    String nextIdDholi = processNextIdRepository.findNextIdByCompositeKey(companyId, branchId, processId);

				    // Convert the String to an int
				    int nextId = Integer.parseInt(nextIdDholi);

				    // Increment the ID by 1
				    nextId++;

				    // Format it as a 4-digit number and convert it back to a String
				    String formattedNextId = String.format("%04d", nextId);

				    // Update the Next_Id directly in the database using the repository
				    processNextIdRepository.updateNextIdByCompositeKey(companyId, branchId, processId, formattedNextId);

				    // Return the formatted next ID as a String
				    return formattedNextId;
				}
}
