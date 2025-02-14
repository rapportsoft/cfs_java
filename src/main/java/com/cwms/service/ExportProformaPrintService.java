package com.cwms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwms.repository.ExportInvoicePrintRepository;
import com.cwms.repository.ExportProformaPrintRepository;

@Service
public class ExportProformaPrintService {
	
	@Autowired
	ExportProformaPrintRepository 	exportProformaPrintRepository;
	
	
	public Map<String, String> getCompanyAddressDetails( String branchId, 
            String companyId) {
		
		Map<String, String> company = new HashMap<>();
		
		Object companyDtl = exportProformaPrintRepository.getCompanyAddressDetails(branchId, companyId);
		
		
		
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
	
	
	public Map<String, String> getInvoiceDetails(String companyId, 
            String branchId,    
            String invoiceNo) {
		
		Map<String, String> invDtl = new HashMap<>();
		
        Object result = exportProformaPrintRepository.getInvoiceDetails(companyId,branchId, invoiceNo);
        
        if(result!=null) {
        	Object[] fields  = (Object[]) result;
        	

        	

        	invDtl.put("invoiceDate",(String)fields[45] );
//        	invDtl.put("invoicevalDate",(String)fields[40]);
        	invDtl.put("shippingLine",(String)fields[27]);
        	invDtl.put("shippingAgent",(String)fields[28]);
        	
        	
        	invDtl.put("BillingParty",(String)fields[3]);
        	
        	
        	invDtl.put("billingPartyAddress1",(String)fields[5]);
        	invDtl.put("billingPartyAddress2",(String)fields[6]);
        	invDtl.put("billingPartyAddress3",(String)fields[7]);
        	invDtl.put("pin",(String)fields[8]);
        	
        	
        	invDtl.put("GSTIN",(String)fields[11]);
        	invDtl.put("PAN",(String)fields[4]);
        	invDtl.put("State",(String)fields[9]);
        	
        	invDtl.put("cha",(String)fields[19]);
        	invDtl.put("chaAddress1",(String)fields[20]);
        	invDtl.put("chaAddress2",(String)fields[21]);
        	invDtl.put("chaAddress2",(String)fields[22]);
        	invDtl.put("IRN",(String)fields[16]);
        	invDtl.put("Igst",String.valueOf(fields[29])); 
        	invDtl.put("Cgst",String.valueOf(fields[30]));
        	invDtl.put("Sgst",String.valueOf(fields[31]));
//        	invDtl.put("sez",(String)fields[32]);    how to convert char to String 
        	
        	invDtl.put("createdBy",(String)fields[44]);
        	invDtl.put("isBOS",String.valueOf(fields[46]));
        	
        	
        	

   	
        }
        
        
        return invDtl;
        

	}
	
	
public List<Map<String,String>> getDetailsOfBill(String companyId,String branchId, String invoiceNo) {
		
		List<Map<String,String>> result = new ArrayList<>();
		
		List<Object[]> billdtl = exportProformaPrintRepository.getDetailsOfBill(companyId, branchId, invoiceNo);
		
		
		
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


public Map<String,List<Map<String,String>>> getContainerAndSbDetails(String comapnyId, String branchId,String sbTransID,String assesmentId) {
	
	List<Object[]> cnSbdtl = exportProformaPrintRepository.getContainerAndSbDetails(comapnyId, branchId, sbTransID, assesmentId);
	
	List<Map<String,String>> conList = new ArrayList<>();
	List<Map<String,String>> sbList = new ArrayList<>();
	Set<String> conset = new HashSet<>();
	Set<String> sbset = new HashSet<>();
	if(!cnSbdtl.isEmpty()) {
		System.out.println("not empty");

		for (Object[] row : cnSbdtl) {
			
			Map<String ,String> conMap = new HashMap<>();
			Map<String ,String> sbMap = new HashMap<>();
			
			
			String containerNo = (String)row[0];
			String containerSize =(String)row[1];
			String containerType =(String)row[2];
			String vesselName =(String)row[3];
			String viaNo =(String)row[4];
			String GateInDate =(String)row[5];
			String StufftallyDate = (String) row[6];
			String containerInvoiceType = (String) row[7];
			
			containerInvoiceType= (containerInvoiceType != null && !containerInvoiceType.trim().isEmpty() 
				                    && containerInvoiceType.toUpperCase().equals("HAZ")) 
				                    ? "Yes" 
				                    : "No";
//			containerInvoiceType =containerInvoiceType.toUpperCase().equals("Haz".toUpperCase()) ?  "Yes" : "No";
			
			if(!conset.contains(containerNo)) {
				conMap.put("containerNo",containerNo);
				conMap.put("containerSize",containerSize);
				conMap.put("containerType",containerType);
				conMap.put("vesselName",vesselName);
				conMap.put("viaNo",viaNo);
				conMap.put("GateInDate",GateInDate);
				conMap.put("StufftallyDate",StufftallyDate);
				conMap.put("containerInvoiceType",containerInvoiceType);
				
			
				
				conset.add(containerNo);
				conList.add(conMap);
				
				
			}
			
	
			
			
			String SBNO				= (String)row[8];
			String SBDATE			= (String)row[9];
			String CartingDate		= (String)row[10];
			BigDecimal SbWeight1	= (BigDecimal)row[11];
			
			String SbWeight 		= (SbWeight1 != null) ? SbWeight1.toString() : "0";
			BigDecimal Amount1		= (BigDecimal)row[12];
			String Amount 			= (Amount1 != null) ? Amount1.toString() : "0";
			BigDecimal Area1 		= (BigDecimal)row[13];
			String Area				= (Area1 != null) ? Area1.toString() : "0";
			String commoditydesc	= (String)row[14];
			String Gateoutdate    	= (String)row[15];
			String Exportername     = (String)row[16];
			
			
			if(!sbset.contains(SBNO)) {
				sbMap.put("SBNO",SBNO);
				sbMap.put("SBDATE",SBDATE);
				sbMap.put("CartingDate",CartingDate);
				sbMap.put("SbWeight",SbWeight);
				sbMap.put("Amount",Amount);
				sbMap.put("Area",Area);
				sbMap.put("commoditydesc",commoditydesc);
				sbMap.put("Gateoutdate",Gateoutdate);
				sbMap.put("Exportername",Exportername);
				
				sbset.add(SBNO);
				sbList.add(sbMap);
				
				
			}
			
			
			
			
			
			}
		
		
		
		
		
		}
	

		Map<String,List<Map<String,String>>> result = new HashMap<>();
		
		result.put("conList", conList);
		
		result.put("sbList", sbList);
		
				
		return result;
	
	
	
}


public Map<String, String> getcartingInvoiceDetails(String companyId, 
        String branchId,    
        String invoiceNo) {
	
	Map<String, String> invDtl = new HashMap<>();
	
    Object result = exportProformaPrintRepository.getInvoiceDetailscarting(companyId,branchId, invoiceNo);
    
    if(result!=null) {
    	Object[] fields  = (Object[]) result;
    	

    	

    	invDtl.put("invoiceDate",(String)fields[47] );
//    	invDtl.put("invoicevalDate",(String)fields[40]);
//    	invDtl.put("shippingLine",(String)fields[27]);
    	invDtl.put("shippingAgent",(String)fields[28]);
    	
    	
    	invDtl.put("BillingParty",(String)fields[3]);
    	
    	
    	invDtl.put("billingPartyAddress1",(String)fields[5]);
    	invDtl.put("billingPartyAddress2",(String)fields[6]);
    	invDtl.put("billingPartyAddress3",(String)fields[7]);
    	invDtl.put("pin",(String)fields[8]);
    	
    	
    	invDtl.put("GSTIN",(String)fields[11]);
    	invDtl.put("PAN",(String)fields[4]);
    	invDtl.put("State",(String)fields[9]);
    	
    	invDtl.put("cha",(String)fields[19]);

    	invDtl.put("IRN",(String)fields[16]);
    	invDtl.put("Igst",String.valueOf(fields[29])); 
    	invDtl.put("Cgst",String.valueOf(fields[30]));
    	invDtl.put("Sgst",String.valueOf(fields[31]));
//    	invDtl.put("sez",(String)fields[32]);    how to convert char to String 
    	
    	invDtl.put("AgAddress1",(String)fields[43]);
    	invDtl.put("AgAddress2",(String)fields[44]);
    	invDtl.put("AgAddress3",(String)fields[45]);
    	invDtl.put("Agpin",(String)fields[46]);
    	
    	invDtl.put("createdBy",(String)fields[42]);
    	
    	
    	
    	
    	

	
    }
    
    
    return invDtl;
    

}

 public List<Map<String,String>> getCartingSbDetails(String comapnyId, String branchId,String assesmentId) {
	 
	 List<Object[]> cartSbdtl = exportProformaPrintRepository.getcartingSbDetails(comapnyId, branchId, assesmentId);
	 
	 List<Map<String,String>> cartSbdtllist = new ArrayList<>();
	 
	 
	 if(!cartSbdtl.isEmpty()) {
			System.out.println("not empty getCartingSbDetails");

			for (Object[] row : cartSbdtl) { 
				
				Map<String,String> mapsb = new HashMap<>();
				
				String SBNO				=  (String)row[0];
				String SBDATE			=  (String)row[1];
				String CartingDate		=  (String)row[2];
			
				BigDecimal SbWeight1	= (BigDecimal)row[3];
				String SbWeight 		= (SbWeight1 != null) ? SbWeight1.toString() : "0";
				
				BigDecimal Amount1		= (BigDecimal)row[4];
				String Amount 			= (Amount1 != null) ? Amount1.toString() : "0";

				BigDecimal Area1 		= (BigDecimal)row[5];
				String Area				= (Area1 != null) ? Area1.toString() : "0";
						
				String commoditydesc	= (String)row[6];
				String Gateout_date     = (String)row[7];
				String Exportername     = (String)row[8];
				
				mapsb.put("SBNO",SBNO);
				mapsb.put("SBDATE",SBDATE);
				mapsb.put("CartingDate",CartingDate);
				mapsb.put("SbWeight",SbWeight);
				mapsb.put("Amount",Amount);
				mapsb.put("Area",Area);
				mapsb.put("commoditydesc",commoditydesc);
				mapsb.put("Gateout_date",Gateout_date);
				mapsb.put("Exportername",Exportername);
				
				
				cartSbdtllist.add(mapsb);
				
			}
			
	 }
	 
	 return cartSbdtllist;
	 
 }
 
//container backttown service 
public Map<String,List<Map<String,String>>> getContainerAndSbDetailsbackttown(String comapnyId, String branchId,String sbTransID,String assesmentId) {
	
	List<Object[]> cnSbdtl = exportProformaPrintRepository.getContainerAndSbDetailsbackttown(comapnyId, branchId, sbTransID, assesmentId);
	
	List<Map<String,String>> conList = new ArrayList<>();
	List<Map<String,String>> sbList = new ArrayList<>();
	Set<String> conset = new HashSet<>();
	Set<String> sbset = new HashSet<>();
	if(!cnSbdtl.isEmpty()) {
		System.out.println("not empty");

		for (Object[] row : cnSbdtl) {
			
			Map<String ,String> conMap = new HashMap<>();
			Map<String ,String> sbMap = new HashMap<>();
			
			
			String containerNo = (String)row[0];
			String containerSize =(String)row[1];
			String containerType =(String)row[2];
			String vesselName =(String)row[3];
			String viaNo =(String)row[4];
			String GateInDate =(String)row[5];
			String StufftallyDate = (String) row[6];
			String containerInvoiceType = (String) row[7];
			
			containerInvoiceType= (containerInvoiceType != null && !containerInvoiceType.trim().isEmpty() 
				                    && containerInvoiceType.toUpperCase().equals("HAZ")) 
				                    ? "Yes" 
				                    : "No";
//			containerInvoiceType =containerInvoiceType.toUpperCase().equals("Haz".toUpperCase()) ?  "Yes" : "No";
			
			if(!conset.contains(containerNo)) {
				conMap.put("containerNo",containerNo);
				conMap.put("containerSize",containerSize);
				conMap.put("containerType",containerType);
				conMap.put("vesselName",vesselName);
				conMap.put("viaNo",viaNo);
				conMap.put("GateInDate",GateInDate);
				conMap.put("StufftallyDate",StufftallyDate);
				conMap.put("containerInvoiceType",containerInvoiceType);
				
			
				
				conset.add(containerNo);
				conList.add(conMap);
				
				
			}
			
	
			
			
			String SBNO				= (String)row[8];
			String SBDATE			= (String)row[9];
			String CartingDate		= (String)row[10];
			BigDecimal SbWeight1	= (BigDecimal)row[11];
			
			String SbWeight 		= (SbWeight1 != null) ? SbWeight1.toString() : "0";
			BigDecimal Amount1		= (BigDecimal)row[12];
			String Amount 			= (Amount1 != null) ? Amount1.toString() : "0";
			BigDecimal Area1 		= (BigDecimal)row[13];
			String Area				= (Area1 != null) ? Area1.toString() : "0";
			String commoditydesc	= (String)row[14];
			String Gateoutdate    	= (String)row[15];
			String Exportername     = (String)row[16];
			
			
			if(!sbset.contains(SBNO)) {
				sbMap.put("SBNO",SBNO);
				sbMap.put("SBDATE",SBDATE);
				sbMap.put("CartingDate",CartingDate);
				sbMap.put("SbWeight",SbWeight);
				sbMap.put("Amount",Amount);
				sbMap.put("Area",Area);
				sbMap.put("commoditydesc",commoditydesc);
				sbMap.put("Gateoutdate",Gateoutdate);
				sbMap.put("Exportername",Exportername);
				
				sbset.add(SBNO);
				sbList.add(sbMap);
				
				
			}
			
			
			
			
			
			}
		
		
		
		
		
		}
	

		Map<String,List<Map<String,String>>> result = new HashMap<>();
		
		result.put("conList", conList);
		
		result.put("sbList", sbList);
		
				
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
