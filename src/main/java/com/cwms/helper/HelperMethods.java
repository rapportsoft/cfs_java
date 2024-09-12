package com.cwms.helper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class HelperMethods {
	
	 public String getFinancialYear() {
	        LocalDate currentDate = LocalDate.now();
	        int currentYear = currentDate.getYear();
	        int month = currentDate.getMonthValue();
	        
	        // If the date is before April 1st, return the previous year
	        if (month < 4) {
	            return String.valueOf(currentYear - 1);
	        } else { // If the date is April 1st or later, return the current year
	            return String.valueOf(currentYear);
	        }
	    }

}
