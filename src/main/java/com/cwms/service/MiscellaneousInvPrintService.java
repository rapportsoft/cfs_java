package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwms.repository.MiscellaneousInvPrintRepository;

@Service
public class MiscellaneousInvPrintService {
	
	@Autowired
	MiscellaneousInvPrintRepository miscellaneousInvPrintRepository;
	
	
	
	
	public Map<String, String> getInvoiceDetails(String companyId, 
            String branchId,    
            String invoiceNo ) {
		
		Map<String, String> invDtl = new HashMap<>();
		
        Object result = miscellaneousInvPrintRepository.getInvoiceDetails(companyId,branchId, invoiceNo);
        
        if(result!=null) {
        	Object[] fields  = (Object[]) result;
        	

        	

        	invDtl.put("invoiceDate",(String)fields[1] );
//        	invDtl.put("invoicevalDate",(String)fields[40]);
//        	invDtl.put("shippingLine",(String)fields[27]);
//        	invDtl.put("shippingAgent",(String)fields[28]);
        	
        	
        	invDtl.put("BillingParty",(String)fields[3]);
        	
        	
        	invDtl.put("billingPartyAddress1",(String)fields[5]);
        	invDtl.put("billingPartyAddress2",(String)fields[6]);
        	invDtl.put("billingPartyAddress3",(String)fields[7]);
        	invDtl.put("pin",(String)fields[8]);
        	
        	
        	invDtl.put("GSTIN",(String)fields[11]);
        	invDtl.put("PAN",(String)fields[4]);
        	invDtl.put("State",(String)fields[9]);
        	
//        	invDtl.put("cha",(String)fields[19]);
//        	invDtl.put("chaAddress1",(String)fields[20]);
//        	invDtl.put("chaAddress2",(String)fields[21]);
//        	invDtl.put("chaAddress2",(String)fields[22]);
//        	invDtl.put("IRN",(String)fields[16]);
        	invDtl.put("Igst",String.valueOf(fields[20])); 
        	invDtl.put("Cgst",String.valueOf(fields[21]));
        	invDtl.put("Sgst",String.valueOf(fields[22]));
//        	invDtl.put("sez",(String)fields[32]);    how to convert char to String 
        	
        	invDtl.put("createdBy",(String)fields[26]);
        	invDtl.put("isBOS",String.valueOf(fields[27]));
        	
        	
        	

   	
        }
        
        
        return invDtl;
        

	}
	
	
	public Map<String, String> getCompanyAddressDetails( String branchId, 
            String companyId) {
		
		Map<String, String> company = new HashMap<>();
		
		Object companyDtl = miscellaneousInvPrintRepository.getCompanyAddressDetails(branchId, companyId);
		
		
		
		if(companyDtl!=null) {
        	Object[] fields  = (Object[]) companyDtl;
        	
        	
        	company.put("cname",(String) fields[0]);
        	company.put("cadd1",(String) fields[13]);
        	company.put("cadd2",(String) fields[14]);
        	company.put("cadd3",(String) fields[15]);
        	company.put("gst",(String) fields[10]);
        	company.put("cin",(String) fields[11]);
        	company.put("pan",(String) fields[12]);
        	company.put("state",(String) fields[6]);
        	company.put("email",(String) fields[18]);
        	
        	company.put("baddr1",(String) fields[2]);
        	company.put("baddr2",(String) fields[3]);
        	company.put("baddr3",(String) fields[4]);
        	company.put("branchPin",(String) fields[8]);
        	company.put("companyPin",(String) fields[17]);
        	
        	
        	

		}
		return company;
	}
	
	
public List<Map<String,String>> getDetailsOfBill(String companyId,String branchId, String invoiceNo) {
		
		List<Map<String,String>> result = new ArrayList<>();
		
		List<Object[]> billdtl = miscellaneousInvPrintRepository.getDetailsOfBill(companyId, branchId, invoiceNo);
		
		
		
		if(!billdtl.isEmpty()) {
			System.out.println("not empty");
			
			for (Object[] row : billdtl) {
				
				
				Map<String,String> map1 = new HashMap<>();
				
				map1.put("serviceDesc",(String)row[1]);
				map1.put("sac",(String)row[12] );
				
				BigDecimal amnt = (BigDecimal) row[5];
				
				
//				String amount1 =String.format("%.2f",amnt);
				String amount1 =amnt.setScale(2, BigDecimal.ROUND_DOWN).toString();
				
				map1.put("amount1", amount1);
				
				map1.put("sacDesc", (String)row[13]);
				
				BigDecimal taxperc = (BigDecimal) row[14];	
				
				
				
				
//				String taxperc1 =String.format("%.2f",taxperc);
				String taxperc1 = taxperc.setScale(2, BigDecimal.ROUND_DOWN).toString();
				
				map1.put("taxPerc",taxperc1);
				result.add(map1);
				
			}
			
			
			
			
		}
		return result;
		
		
	}
	
	public boolean sacNoAndTaxpercMatch(String sac ,String taxperc1 ,List<Map<String,String>> invdtl) {
		System.out.println("values of tax per in matching"+taxperc1);
		
		System.out.println("values of sac per in matching"+sac);
		System.out.println("values of sac2 per in matching"+invdtl.get(0).get("sac"));
		System.out.println("values of tax perc in matching"+invdtl.get(0).get("taxPerc"));
		
		for(Map<String,String> m1:invdtl) {
			
			if(sac.equals(m1.get("sac")) && taxperc1.equals(m1.get("taxPerc"))) {
				System.out.println(taxperc1+" "+m1.get("taxPerc"));
				System.out.println(sac+" "+m1.get("sac"));
				return true;
				
			}
			
				
		}
		return false;
		
		
		
	};

}
