package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportStuffTally;

public interface MiscellaneousInvPrintRepository  extends JpaRepository<ExportStuffTally, String>{
	
	
	@Query(value ="select distinct a.container_no,DATE_FORMAT(a.invoice_date,'%d %b %Y'), a.party_id,b.party_name,b.pan_no, ba.address_1,ba.address_2,ba.address_3, ba.pin,h.jar_dtl_desc State, j.jar_dtl_desc Statecode, ba.gst_no,a.lock_down,u.user_name,v.user_name,a.bill_amt, a.IRN, a.signqrcode_path,  d.party_name,f.party_name, s.IGST,s.CGST,s.SGST,s.sez,a.Mail_Flag,b.FINANCE_MAIL ,a.Created_By,s.is_bos "
			+ "from cfinvsrv  a left outer join cfassesmentsheet s on a.Company_Id = s.Company_Id and a.Branch_Id = s.Branch_Id and a.Invoice_No = s.Invoice_No and a.Container_no = s.Assesment_Id and a.Profitcentre_Id = s.Profitcentre_Id left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.Acc_Sr_no "
			+ "left outer join jar_detail h on ba.company_id=h.company_id and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014'  left outer join party d on s.company_id=d.company_id and s.CHA=d.party_id left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id "
			+ "left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id  where a.Company_Id=:companyId AND  a.branch_id=:branchId and a.status='A'  and a.invoice_no =:invoiceNo",nativeQuery = true)
	Object getInvoiceDetails(
	        @Param("companyId") String companyId, 
	        @Param("branchId") String branchId,  
	        @Param("invoiceNo") String invoiceNo
	       
	    );
	
	@Query(value ="select a.payment_mode , a.cheque_no , DATE_FORMAT(a.cheque_date,'%d-%b-%Y') , a.Bank_Name , ROUND(a.document_amt,2) , ROUND(a.tds_bill_amt,2 ) ,credit_flag  "
			+ "from fintrans a where   a.Company_Id=:companyId AND  a.branch_id=:branchId  AND a.OPR_invoice_no=:invoiceNo and a.status='A'",nativeQuery = true)
	List<Object[]> getPaymentDetails (@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("invoiceNo") String invoiceNo);
	
	@Query(value ="select bank_name,bank_account_no,bank_ifsc_code,bank_address from branch a where a.Company_Id=:companyId and a.Branch_Id=:branchId"
			,nativeQuery = true)
	Object getBankDetails(@Param("companyId") String companyId, 
            @Param("branchId") String branchId);
	
	
	@Query(value ="SELECT b.Company_Name, a.Branch_Name, a.Address_1, a.Address_2, a.Address_3, e.Jar_Dtl_Desc, f.Jar_Dtl_Desc,  g.Jar_Dtl_Desc, a.Pin, a.Phone_no,b.GST_No,b.CIN_No, b.pan_no,b.Address_1, b.Address_2, b.Address_3, "
			+ "b.City_Name,b.PIN, a.email_id  "
			+ "FROM  branch a LEFT OUTER JOIN company b ON a.Company_Id = b.Company_Id LEFT OUTER JOIN jar_detail e ON a.Company_Id=e.Company_Id AND a.City=e.Jar_Dtl_Id AND e.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail f  "
			+ "ON a.Company_id=f.Company_id AND a.State=f.Jar_Dtl_Id AND f.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail g ON a.Company_id=g.Company_id AND a.Country=g.Jar_Dtl_Id AND g.Jar_Id = 'J00002' WHERE  a.Branch_Id=:branchId AND a.Company_Id=:companyId AND a.Status = 'A' ",nativeQuery = true)
	Object getCompanyAddressDetails(@Param("branchId") String branchId, 
            @Param("companyId") String companyId);
	
	@Query(value="select a.service_id,c.service_long_desc,count(a.service_id),sum(a.execution_unit),a.service_unit,sum(a.local_amt),IFNULL(ROUND( ((sum(b.invoice_Amt)/count(a.service_id))), 3 ), 0) UNITAMT,max(a.rate),DATE_FORMAT(a.approved_date,'%d %b %Y %T'),c.range_type,sum(a.execution_unit1),"
			+ "a.service_unit1,c.sac_code,IFNULL(j.Jar_Dtl_Desc,'N/A' ),IFNULL(ROUND( a.Tax_Perc_N, 1 ), 0 ) from cfinvsrvanx a  left outer join cfinvsrvdtl b on a.invoice_no=b.invoice_no and a.service_id=b.service_id  and a.company_Id=b.Company_Id and a.branch_Id=b.branch_Id  "
			+ "left outer join services c on a.service_id=c.service_id  and a.company_Id=c.Company_Id left outer join jar_detail j on c.sac_code=j.Jar_Dtl_Id  and j.company_Id=c.Company_Id and j.Jar_Id = 'J00024'  where   a.Company_Id=:companyId  AND  a.branch_id=:branchId  AND a.Invoice_No=:invoiceNo and a.status='A' and a.Invoice_Amt > 0 and b.status='A' and a.tax_id_N !=''  group by c.service_short_desc order by c.print_sequence desc",nativeQuery = true)
	List<Object[]> getDetailsOfBill(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("invoiceNo") String invoiceNo);
	
	
	
	// Vendor Invoice
	
	
		@Query(value ="select distinct a.container_no,DATE_FORMAT(a.invoice_date,'%d %b %Y'), a.party_id,b.party_name,b.pan_no, "
				+ "ba.address_1,ba.address_2,ba.address_3, ba.pin,h.jar_dtl_desc State, j.jar_dtl_desc Statecode, ba.gst_no,"
				+ "a.lock_down,u.user_name,v.user_name,a.bill_amt, a.IRN, a.signqrcode_path,  d.party_name,f.party_name, "
				+ "s.IGST,s.CGST,s.SGST,s.sez,a.Mail_Flag,b.FINANCE_MAIL ,a.Created_By,s.is_bos,s.last_invoice_no,"
				+ "DATE_FORMAT(s.last_invoice_date,'%d %b %Y'),COALESCE(b.account_Name,''),COALESCE(b.bank_Name,''),COALESCE(b.bank_Address,''),COALESCE(b.ifsc_Code,''),COALESCE(b.account_No,'') "
				+ "from cfinvsrvap  a left outer join cfassesmentsheetap s on a.Company_Id = s.Company_Id and a.Branch_Id = s.Branch_Id and a.Invoice_No = s.Invoice_No and a.Container_no = s.Assesment_Id and a.Profitcentre_Id = s.Profitcentre_Id left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.Acc_Sr_no "
				+ "left outer join jar_detail h on ba.company_id=h.company_id and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014'  left outer join party d on s.company_id=d.company_id and s.CHA=d.party_id left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id "
				+ "left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id  where a.Company_Id=:companyId AND  a.branch_id=:branchId and a.status='A'  and a.invoice_no =:invoiceNo",nativeQuery = true)
		Object getInvoiceDetails1(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,  
		        @Param("invoiceNo") String invoiceNo
		       
		    );
		
		@Query(value="select a.service_id,c.service_long_desc,count(a.service_id),sum(a.execution_unit),a.service_unit,sum(a.local_amt),IFNULL(ROUND( ((sum(b.invoice_Amt)/count(a.service_id))), 3 ), 0) UNITAMT,max(a.rate),DATE_FORMAT(a.approved_date,'%d %b %Y %T'),c.range_type,sum(a.execution_unit1),"
				+ "a.service_unit1,c.sac_code,IFNULL(j.Jar_Dtl_Desc,'N/A' ),IFNULL(ROUND( a.Tax_Perc_N, 1 ), 0 ) from cfinvsrvanxap a  left outer join cfinvsrvdtlap b on a.invoice_no=b.invoice_no and a.service_id=b.service_id  and a.company_Id=b.Company_Id and a.branch_Id=b.branch_Id  "
				+ "left outer join services c on a.service_id=c.service_id  and a.company_Id=c.Company_Id left outer join jar_detail j on c.sac_code=j.Jar_Dtl_Id  and j.company_Id=c.Company_Id and j.Jar_Id = 'J00024'  where   a.Company_Id=:companyId  AND  a.branch_id=:branchId  AND a.Invoice_No=:invoiceNo and a.status='A' and a.Invoice_Amt > 0 and b.status='A' and a.tax_id_N !=''  group by c.service_short_desc order by c.print_sequence desc",nativeQuery = true)
		List<Object[]> getDetailsOfBill1(@Param("companyId") String companyId, 
	            @Param("branchId") String branchId,
	            @Param("invoiceNo") String invoiceNo);
		
		@Query(value="select a.service_id,c.service_long_desc,count(a.service_id),sum(a.execution_unit),a.service_unit,sum(a.local_amt),IFNULL(ROUND( ((sum(b.invoice_Amt)/count(a.service_id))), 3 ), 0) UNITAMT,max(a.rate),DATE_FORMAT(a.approved_date,'%d %b %Y %T'),c.range_type,sum(a.execution_unit1),"
				+ "a.service_unit1,a.ac_code,IFNULL(j.Jar_Dtl_Desc,'N/A' ),IFNULL(ROUND( a.Tax_Perc_N, 1 ), 0 ) from cfinvsrvanxap a  left outer join cfinvsrvdtlap b on a.invoice_no=b.invoice_no and a.service_id=b.service_id  and a.company_Id=b.Company_Id and a.branch_Id=b.branch_Id  "
				+ "left outer join services c on a.service_id=c.service_id  and a.company_Id=c.Company_Id left outer join jar_detail j on a.ac_code=j.Jar_Dtl_Id  and j.company_Id=c.Company_Id and j.Jar_Id = 'J00024'  where   a.Company_Id=:companyId  AND  a.branch_id=:branchId  AND a.Invoice_No=:invoiceNo and a.status='A' and a.Invoice_Amt > 0 and b.status='A' and a.tax_id_N !=''  group by c.service_short_desc order by c.print_sequence desc",nativeQuery = true)
		List<Object[]> getDetailsOfBill2(@Param("companyId") String companyId, 
	            @Param("branchId") String branchId,
	            @Param("invoiceNo") String invoiceNo);
	
}


