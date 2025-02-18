package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportStuffTally;

public interface CreditNotePrintRepository extends JpaRepository<ExportStuffTally, String>{
	
	
	
	@Query(value ="select distinct a.container_no,DATE_FORMAT(a.approved_date,'%d %b %Y %T'), a.party_id,b.party_name,b.pan_no, ba.address_1,ba.address_2,ba.address_3, ba.pin,h.jar_dtl_desc State, j.jar_dtl_desc Statecode, ba.gst_no,'',u.user_name,v.user_name,a.bill_amt, a.IRN, a.SignQRCodePath,  d.party_name,da.address_1,da.address_2,da.address_3, da.pin,m.jar_dtl_desc,n.jar_dtl_desc,da.gst_no,f.party_name,fa.address_1,fa.address_2,fa.address_3, fa.pin,i.jar_dtl_desc,k.jar_dtl_desc,fa.gst_no, s.IGST,s.CGST,s.SGST,s.sez,s.IGM_No ,   DATE_FORMAT(s.IGM_Date,'%d/%m/%Y'),s.igm_line_no,s.SB_No ,  DATE_FORMAT(s.SB_Date,'%d/%m/%Y'),s.Commodity_Description, DATE_FORMAT(a.Invoice_Date,'%d/%m/%Y'), DATE_FORMAT(a.Old_Invoice_Date,'%d/%m/%Y'), REPLACE(REPLACE(a.Comments, '\\r', ''), '\\n', ''),s.Tax_Applicable,a.Created_By   "
			+ "from cfinvsrvcn  a left outer join cfassesmentsheet s on a.Company_Id = s.Company_Id and a.Branch_Id = s.Branch_Id and a.old_invoice_no = s.Invoice_No and a.Container_no = s.Assesment_Id and a.Profitcentre_Id = s.Profitcentre_Id left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.Acc_Sr_no  left outer join jar_detail h on ba.company_id=h.company_id and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014'  left outer join party d on s.company_id=d.company_id and s.CHA=d.party_id  "
			+ "left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id   left outer join partyaddress fa on s.company_id=fa.company_id and s.importer_id=fa.party_id and s.imp_sr_no = fa.sr_no  left outer join jar_detail i on fa.company_id=i.company_id and fa.state=i.jar_dtl_id and i.jar_id='J00026'  left outer join jar_detail k on fa.company_id=k.company_id and fa.state=k.jar_dtl_id and k.jar_id='J00014' left outer join partyaddress da on s.company_id=da.company_id and s.CHA=da.party_id and s.cha_sr_no = da.sr_no  left outer join jar_detail m on da.company_id=m.company_id and da.state=m.jar_dtl_id and m.jar_id='J00026'  left outer join jar_detail n on da.company_id=n.company_id and da.state=n.jar_dtl_id and n.jar_id='J00014' "
			+ "left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id  left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id  where a.Company_Id=:companyId AND a.branch_id=:branchId and a.status in ('A','N','U')  and a.invoice_no = :invoiceNo and a.Profitcentre_Id =:ProfitcentreId ",nativeQuery = true)
	Object getInvoiceDetails(
	        @Param("companyId") String companyId, 
	        @Param("branchId") String branchId,  
	        @Param("invoiceNo") String invoiceNo,
	        @Param("ProfitcentreId") String ProfitcentreId     
	    );
	
	@Query(value ="SELECT b.Company_Name, a.Branch_Name, a.Address_1, a.Address_2, a.Address_3, e.Jar_Dtl_Desc, f.Jar_Dtl_Desc,  g.Jar_Dtl_Desc, a.Pin, a.Phone_no,b.GST_No,b.CIN_No, b.pan_no,b.Address_1, b.Address_2, b.Address_3, "
			+ "b.City_Name,b.PIN, a.email_id  "
			+ "FROM  branch a LEFT OUTER JOIN company b ON a.Company_Id = b.Company_Id LEFT OUTER JOIN jar_detail e ON a.Company_Id=e.Company_Id AND a.City=e.Jar_Dtl_Id AND e.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail f  "
			+ "ON a.Company_id=f.Company_id AND a.State=f.Jar_Dtl_Id AND f.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail g ON a.Company_id=g.Company_id AND a.Country=g.Jar_Dtl_Id AND g.Jar_Id = 'J00002' WHERE  a.Branch_Id=:branchId AND a.Company_Id=:companyId AND a.Status = 'A' ",nativeQuery = true)
	Object getCompanyAddressDetails(@Param("branchId") String branchId, 
            @Param("companyId") String companyId);
	
	
	@Query(value="select a.service_id,c.service_short_desc,ROUND(sum(a.Invoice_Amt),3),c.sac_code,IFNULL(j.Jar_Dtl_Desc,'N/A' ),IFNULL(ROUND( a.Tax_Perc_N, 1 ), 0 ) "
			+ "from cfinvsrvcndtl a left outer join services c on a.service_id=c.service_id  and a.company_Id=c.Company_Id left outer join jar_detail j on c.sac_code=j.Jar_Dtl_Id  and j.company_Id=c.Company_Id and j.Jar_Id = 'J00024' "
			+ " where a.Company_Id=:companyId AND  a.branch_id=:branchId AND a.Invoice_No=:invoiceNo and a.status in ('N','U','A') and a.Invoice_Amt > 0 and a.tax_id_N !=''  group by c.service_short_desc order by c.print_sequence desc ",nativeQuery = true)
	List<Object[]> getDetailsOfBill(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("invoiceNo") String invoiceNo);
	
	

}
