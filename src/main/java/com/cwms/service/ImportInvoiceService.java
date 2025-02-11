package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.cwms.repository.ImportInvoiceRepository;

@Service
public class ImportInvoiceService {
	
	@Autowired
	ImportInvoiceRepository importinvoicerepo;
	
	
	public Map<String, String> getInvoiceDetails(String companyId, 
            String branchId,    
            String invoiceNo) {
		
		Map<String, String> invDtl = new HashMap<>();
		
        Object result = importinvoicerepo.getInvoiceDetails(companyId,branchId, invoiceNo);
        
        if(result!=null) {
        	Object[] fields  = (Object[]) result;
        	
//        	if (result != null) {
//        	    Object[] fields = (Object[]) result;
//        	    for (int i = 0; i < fields.length; i++) {
//        	        System.out.println("Index " + i + ": " + fields[i]);
//        	    }
//        	}
        	
//        	invDtl.put("containerNo",(String)fields[0] );
        	invDtl.put("invoiceDate",(String)fields[33] );
        	invDtl.put("invoiceUptoDate",(String)fields[40] );
        	invDtl.put("shippingLine",(String)fields[26]);
        	invDtl.put("shippingAgent",(String)fields[27]);
        	
        	
        	invDtl.put("BillingParty",(String)fields[3]);
        	
        	
        	invDtl.put("billingPartyAddress1",(String)fields[5]);
        	invDtl.put("billingPartyAddress2",(String)fields[6]);
        	invDtl.put("billingPartyAddress3",(String)fields[7]);
        	
        	invDtl.put("GSTIN",(String)fields[11]);
        	invDtl.put("PAN",(String)fields[4]);
        	invDtl.put("State",(String)fields[9]);
        	
        	invDtl.put("consignee",(String)fields[18]);
        	invDtl.put("consigneeAddress1",(String)fields[37]);
        	invDtl.put("consigneeAddress2",(String)fields[38]);
        	invDtl.put("consigneeAddress3",(String)fields[39]);
        	invDtl.put("IRN",(String)fields[16]);
        	invDtl.put("Igst",String.valueOf(fields[28])); 
        	invDtl.put("Cgst",String.valueOf(fields[29]));
        	invDtl.put("Sgst",String.valueOf(fields[30]));
//        	invDtl.put("sez",(String)fields[30]);    how to convert char to String 
        	
        	invDtl.put("isBOS",String.valueOf(fields[41]));
        	invDtl.put("createdBy",(String)fields[35]);
        	
        	
        	

   	
        }
        
        
        return invDtl;
        

	}
	
	
	public Map<String, String> getInvoiceDetails1(String companyId, 
            String branchId,    
            String invoiceNo) {
		
		Map<String, String> invDtl = new HashMap<>();
		
        Object result = importinvoicerepo.getInvoiceDetails1(companyId,branchId, invoiceNo);
        
        if(result!=null) {
        	Object[] fields  = (Object[]) result;
        	
//        	if (result != null) {
//        	    Object[] fields = (Object[]) result;
//        	    for (int i = 0; i < fields.length; i++) {
//        	        System.out.println("Index " + i + ": " + fields[i]);
//        	    }
//        	}
        	
//        	invDtl.put("containerNo",(String)fields[0] );
        	invDtl.put("invoiceDate",(String)fields[33] );
        	invDtl.put("invoiceUptoDate",(String)fields[40] );
        	invDtl.put("shippingLine",(String)fields[26]);
        	invDtl.put("shippingAgent",(String)fields[27]);
        	invDtl.put("nocNo",(String)fields[41]);
        	invDtl.put("nocDate",(String)fields[42]);
        	invDtl.put("bondNo",(String)fields[43]);
        	invDtl.put("bondDate",(String)fields[44]);
        	invDtl.put("bondValidityDate",(String)fields[45]);
        	invDtl.put("nocFromDate",(String)fields[46]);
        	
        	invDtl.put("boeNo",(String)fields[47]);
        	invDtl.put("boeDate",(String)fields[48]);
        	invDtl.put("nop", String.valueOf(fields[49]));

        	invDtl.put("wt",String.valueOf(fields[50]));
        	invDtl.put("area",String.valueOf(fields[51]));
        	invDtl.put("cif",String.valueOf(fields[52]));
        	invDtl.put("duty",String.valueOf(fields[53]));
        	
        	invDtl.put("BillingParty",(String)fields[3]);
        	
        	
        	invDtl.put("billingPartyAddress1",(String)fields[5]);
        	invDtl.put("billingPartyAddress2",(String)fields[6]);
        	invDtl.put("billingPartyAddress3",(String)fields[7]);
        	
        	invDtl.put("GSTIN",(String)fields[11]);
        	invDtl.put("PAN",(String)fields[4]);
        	invDtl.put("State",(String)fields[9]);
        	
        	invDtl.put("consignee",(String)fields[18]);
        	invDtl.put("consigneeAddress1",(String)fields[37]);
        	invDtl.put("consigneeAddress2",(String)fields[38]);
        	invDtl.put("consigneeAddress3",(String)fields[39]);
        	invDtl.put("IRN",(String)fields[16]);
        	invDtl.put("Igst",String.valueOf(fields[28])); 
        	invDtl.put("Cgst",String.valueOf(fields[29]));
        	invDtl.put("Sgst",String.valueOf(fields[30]));
//        	invDtl.put("sez",(String)fields[30]);    how to convert char to String 
        	
        	
        	invDtl.put("isBOS",String.valueOf(fields[41]));
        	invDtl.put("createdBy",(String)fields[35]);

   	
        }
        
        
        return invDtl;
        

	}
	
	
	public Map<String, String> getCompanyAddressDetails( String branchId, 
            String companyId) {
		
		Map<String, String> company = new HashMap<>();
		
		Object companyDtl = importinvoicerepo.getCompanyAddressDetails(branchId, companyId);
		
		
		
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
	
	public List<Object[]> getOperationalDetails (String comapnyId,String branchId,String igmTransId,String assessmentId) {
		
		List<Object[]> opdtls = importinvoicerepo.getOperationalDetails(comapnyId, branchId, igmTransId, assessmentId);
		System.out.println("is empty"+opdtls.isEmpty());
		

		return opdtls;
		
		
	}
	
	
	
	
	public List<Map<String,String>> getDetailsOfBill(String companyId,String branchId, String invoiceNo) {
		
		List<Map<String,String>> result = new ArrayList<>();
		
		List<Object[]> billdtl = importinvoicerepo.getDetailsOfBill(companyId, branchId, invoiceNo);
		
		
		
		if(!billdtl.isEmpty()) {
			System.out.println("not empty");
			
			for (Object[] row : billdtl) {
				
				
				Map<String,String> map1 = new HashMap<>();
				
				map1.put("serviceDesc",(String)row[1]);
				map1.put("sac",(String)row[12] );
				
				BigDecimal amnt = (BigDecimal) row[5];
				
				
				String amount1 =String.format("%.2f",amnt);
				map1.put("amount1", amount1);
				
				map1.put("sacDesc", (String)row[13]);
				
				BigDecimal taxperc = (BigDecimal) row[14];	
				
				
				
				
				String taxperc1 =String.format("%.2f",taxperc);
				
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
