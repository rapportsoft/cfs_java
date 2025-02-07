package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AssessmentSheet;

@EnableJpaRepositories
public interface ImportInvoiceRepository extends JpaRepository<AssessmentSheet, String> {
	
	@Query(value = "select distinct a.container_no,DATE_FORMAT(a.approved_date,'%d %b %Y %T'),a.party_id,b.party_name,b.pan_no,ba.address_1,ba.address_2,ba.address_3,ba.pin,h.jar_dtl_desc State,"
			+ "	j.jar_dtl_desc Statecode,ba.gst_no,a.lock_down,u.user_name,v.user_name,a.bill_amt, a.IRN,d.party_name,s.Importer_Name,fa.address_1,fa.address_2,fa.address_3,"
			+ "	fa.pin,i.jar_dtl_desc,k.jar_dtl_desc,fa.gst_no,sl.party_name,ag.party_name, s.IGST,s.CGST,s.SGST,s.sez,a.Mail_Flag,DATE_FORMAT(a.Invoice_Date,'%d/%m/%Y'),b.FINANCE_MAIL,a.Created_By,"
			+ "	s.Last_Invoice_No, igmcrg.importer_address1 ,igmcrg.importer_address2,igmcrg.importer_address3,DATE_FORMAT(s.invoice_upto_date,'%d/%m/%Y %H:%i:%s') "
			+ "	from cfinvsrv  a left outer join cfassesmentsheet s "
			+ "	on a.Company_Id = s.Company_Id and a.Branch_Id = s.Branch_Id  and a.Invoice_No = s.Invoice_No and a.Container_no = s.Assesment_Id and a.Profitcentre_Id = s.Profitcentre_Id "
			+ "	left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.Acc_Sr_no  left outer join jar_detail h on ba.company_id=h.company_id"
			+ "	and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014'   left outer join party sl on s.company_id=sl.company_id and s.sl=sl.party_id left outer join party ag on s.company_id=ag.company_id "
			+ "	and s.sa=ag.party_id left outer join party d on s.company_id=d.company_id and s.CHA=d.party_id left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id   left outer join partyaddress fa on s.company_id=fa.company_id and s.importer_id=fa.party_id and s.imp_sr_no = fa.sr_no "
			+ "	left outer join jar_detail i on fa.company_id=i.company_id and fa.state=i.jar_dtl_id and i.jar_id='J00026'  left outer join jar_detail k on fa.company_id=k.company_id and fa.state=k.jar_dtl_id and k.jar_id='J00014' left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id "
			+ "	left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id left outer join cfigmcrg igmcrg on s.Company_Id = igmcrg.Company_Id and s.Branch_Id = igmcrg.Branch_Id and  s.IGM_Trans_Id = igmcrg.IGM_Trans_Id "
			+ "	and s.Igm_No = igmcrg.Igm_No and s.igm_line_no = igmcrg.igm_line_no  where a.Company_Id=:companyId AND  a.branch_id=:branchId and a.status='A'  and a.invoice_no = :invoiceNo ",nativeQuery = true)
	Object getInvoiceDetails(
	        @Param("companyId") String companyId, 
	        @Param("branchId") String branchId,  
	        @Param("invoiceNo") String invoiceNo
	    );
	
	
	@Query(value = "select distinct a.container_no,DATE_FORMAT(a.approved_date,'%d %b %Y %T'),a.party_id,b.party_name,b.pan_no,ba.address_1,ba.address_2,ba.address_3,ba.pin,h.jar_dtl_desc State,"
			+ "	j.jar_dtl_desc Statecode,ba.gst_no,a.lock_down,u.user_name,v.user_name,a.bill_amt, a.IRN,d.party_name,s.Importer_Name,fa.address_1,fa.address_2,fa.address_3,"
			+ "	fa.pin,i.jar_dtl_desc,k.jar_dtl_desc,fa.gst_no,sl.party_name,ag.party_name, s.IGST,s.CGST,s.SGST,s.sez,a.Mail_Flag,DATE_FORMAT(a.Invoice_Date,'%d %b %Y %T'),b.FINANCE_MAIL,a.Created_By,"
			+ "	s.Last_Invoice_No, igmcrg.importer_address1 ,igmcrg.importer_address2,igmcrg.importer_address3,DATE_FORMAT(s.Invoice_Upto_Date,'%d %b %Y %T'),"
			+ " s.igm_no,DATE_FORMAT(s.Igm_Date,'%d %b %Y %T'),noc.bonding_no,DATE_FORMAT(noc.bonding_Date,'%d %b %Y %T'),DATE_FORMAT(noc.bond_validity_Date,'%d %b %Y %T'),DATE_FORMAT(s.noc_from_Date,'%d %b %Y %T'),"
			+ " s.sb_No,DATE_FORMAT(s.Sb_Date,'%d %b %Y %T'),s.no_of_packages,s.gross_weight,s.area_used,s.insurance_value,s.duty_value "
			+ "	from cfinvsrv  a left outer join cfassesmentsheet s "
			+ "	on a.Company_Id = s.Company_Id and a.Branch_Id = s.Branch_Id  and a.Invoice_No = s.Invoice_No and a.Container_no = s.Assesment_Id and a.Profitcentre_Id = s.Profitcentre_Id "
			+ "	left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.Acc_Sr_no  left outer join jar_detail h on ba.company_id=h.company_id"
			+ "	and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014'   left outer join party sl on s.company_id=sl.company_id and s.sl=sl.party_id left outer join party ag on s.company_id=ag.company_id "
			+ "	and s.sa=ag.party_id left outer join party d on s.company_id=d.company_id and s.CHA=d.party_id left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id   left outer join partyaddress fa on s.company_id=fa.company_id and s.importer_id=fa.party_id and s.imp_sr_no = fa.sr_no "
			+ "	left outer join jar_detail i on fa.company_id=i.company_id and fa.state=i.jar_dtl_id and i.jar_id='J00026'  left outer join jar_detail k on fa.company_id=k.company_id and fa.state=k.jar_dtl_id and k.jar_id='J00014' left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id "
			+ "	left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id left outer join cfigmcrg igmcrg on s.Company_Id = igmcrg.Company_Id and s.Branch_Id = igmcrg.Branch_Id and  s.IGM_Trans_Id = igmcrg.IGM_Trans_Id"
			+ " left outer join cfbondnoc noc ON s.company_id=noc.company_id and s.branch_id=noc.branch_id and s.igm_no=noc.noc_no and s.igm_trans_id=noc.noc_trans_id "
			+ "	and s.Igm_No = igmcrg.Igm_No and s.igm_line_no = igmcrg.igm_line_no  where a.Company_Id=:companyId AND  a.branch_id=:branchId and a.status='A'  and a.invoice_no = :invoiceNo ",nativeQuery = true)
	Object getInvoiceDetails1(
	        @Param("companyId") String companyId, 
	        @Param("branchId") String branchId,  
	        @Param("invoiceNo") String invoiceNo
	    );
	
	@Query(value ="SELECT b.Company_Name, a.Branch_Name, a.Address_1, a.Address_2, a.Address_3, e.Jar_Dtl_Desc, f.Jar_Dtl_Desc,  g.Jar_Dtl_Desc, a.Pin, a.Phone_no,b.GST_No,b.CIN_No, b.pan_no,b.Address_1, b.Address_2, b.Address_3, "
			+ "b.City_Name,b.PIN, a.email_id "
			+ "FROM  branch a LEFT OUTER JOIN company b ON a.Company_Id = b.Company_Id LEFT OUTER JOIN jar_detail e ON a.Company_Id=e.Company_Id AND a.City=e.Jar_Dtl_Id AND e.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail f "
			+ "ON a.Company_id=f.Company_id AND a.State=f.Jar_Dtl_Id AND f.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail g ON a.Company_id=g.Company_id AND a.Country=g.Jar_Dtl_Id AND g.Jar_Id = 'J00002' WHERE  a.Branch_Id=:branchId  AND a.Company_Id=:companyId AND a.Status = 'A'",nativeQuery = true)
	Object getCompanyAddressDetails(@Param("branchId") String branchId, 
            @Param("companyId") String companyId);
	
	
	@Query(value = "select a.IGM_No , a.igm_line_no , a.BL_No, DATE_FORMAT(a.BL_Date,'%d-%m-%Y'), c.BE_No, DATE_FORMAT(c.BE_Date,'%d-%m-%Y'), a.container_no, a.container_size, a.container_type, DATE_FORMAT(a.Gate_In_Date,'%d-%m-%Y'), IFNULL(ROUND((a.Gross_Weight / 1000 ) , 2 ),0) cargo_wtmton, IFNULL(ROUND((e.Gross_Weight) , 2 ),0) cargo_wt, "
			+ "c.No_Of_Packages, IFNULL(e.No_Of_Packages,0), c.Type_of_Container, c.Gate_out_Type,ROUND(a.Container_Handling_Amt,2), ROUND(a.Container_Storage_Amt,2), v.Vessel_Name,e.Type_Of_Package, a.Seal_Cutting_Type ,IFNULL( e.Commodity_Description , '' ),DATE_FORMAT(a.Invoice_Date,'%d/%m/%Y'),e.cha_name,a.last_invoice_no,DATE_FORMAT(a.invoice_upto_date,'%d/%m/%Y %H:%i:%s') "
			+ "from cfassesmentsheet a left outer join cfigmcn c "
			+ "on a.company_id=c.company_id  and a.branch_id=c.branch_id  and a.container_no=c.container_no and a.IGM_Trans_id=c.IGM_Trans_id and a.igm_No = c.Igm_No "
			+ "and a.Igm_Line_No = c.Igm_Line_No and a.Profitcentre_Id = c.Profitcentre_Id /*and a.Assesment_Id = c.Assesment_Id*/ left outer join cfigmcrg e on e.company_id=c.company_id  and e.branch_id=c.branch_id  "
			+ "and e.IGM_Trans_id=c.IGM_Trans_id and e.igm_No = c.Igm_No and e.Igm_Line_No = c.Igm_Line_No and e.Profitcentre_Id = c.Profitcentre_Id left outer join cfigm d on d.company_id=e.company_id  and d.branch_id=e.branch_id  "
			+ "and d.IGM_Trans_id=e.IGM_Trans_id and d.igm_No = e.Igm_No and d.Profitcentre_Id = e.Profitcentre_Id left outer join vessel v on d.Company_Id = v.Company_Id and d.Vessel_Id = v.Vessel_Id where a.Company_Id=:companyId "
			+ "AND a.branch_id=:branchId and a.status='A' and a.IGM_Trans_id = :igmTransId and a.assesment_id = :assessmentId  /*and (a.Container_Handling_Amt > 0 or  a.Container_Storage_Amt > 0)*/ order by a.container_no ASC",nativeQuery = true)
	List<Object[]> getOperationalDetails(@Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("igmTransId") String igmTransId,
	        @Param("assessmentId") String assessmentId);
	
	
	
	
	@Query(value ="select bank_name,bank_account_no,bank_ifsc_code,bank_address from branch a where a.Company_Id=:companyId and a.Branch_Id=:branchId"
			,nativeQuery = true)
	Object getBankDetails(@Param("companyId") String companyId, 
            @Param("branchId") String branchId);
	
	
	@Query(value ="select a.payment_mode , a.cheque_no , DATE_FORMAT(a.cheque_date,'%d-%b-%Y') , a.Bank_Name , ROUND(a.document_amt,2) , ROUND(a.tds_bill_amt,2 ) ,credit_flag  "
			+ "from fintrans a where   a.Company_Id=:companyId AND  a.branch_id=:branchId  AND a.OPR_invoice_no=:invoiceNo and a.status='A'",nativeQuery = true)
	List<Object[]> getPaymentDetails (@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("invoiceNo") String invoiceNo);
	
	@Query(value="select a.service_id,c.service_long_desc,count(a.service_id),sum(a.execution_unit),a.service_unit,sum(a.local_amt),IFNULL(ROUND( ((sum(b.invoice_Amt)/count(a.service_id))), 3 ), 0) UNITAMT,max(a.rate),DATE_FORMAT(a.approved_date,'%d %b %Y %T'),c.range_type,sum(a.execution_unit1),"
			+ "a.service_unit1,c.sac_code,IFNULL(j.jar_dtl_desc,'N/A' ),IFNULL(ROUND( a.Tax_Perc_N, 1 ), 0 ) "
			+ "from cfinvsrvanx a  left outer join cfinvsrvdtl b on a.invoice_no=b.invoice_no and a.service_id=b.service_id   and a.company_Id=b.Company_Id and a.branch_Id=b.branch_Id  left outer join services c on a.service_id=c.service_id  "
			+ "and a.company_Id=c.Company_Id left outer join jar_detail j on c.sac_code=j.jar_dtl_id  and j.company_Id=c.company_Id and j.jar_Id = 'J00024'  where   a.Company_Id=:companyId AND  a.branch_id=:branchId  AND a.Invoice_No=:invoiceNo and a.status='A' "
			+ "and a.Invoice_Amt > 0 and b.status='A' and a.tax_id_N !='' group by c.service_long_desc order by c.print_sequence desc",nativeQuery = true)
	List<Object[]> getDetailsOfBill(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("invoiceNo") String invoiceNo);
	
	
	@Query(value ="select distinct assesment_id,invoice_no,last_invoice_no FROM cfassesmentsheet where invoice_no=:invoiceNo and assesment_id =:assessmentId and igm_trans_id =:igmTransId and  company_id =:companyId and branch_id=:branchId"
			,nativeQuery = true)
	Object getPrevInvDtl (@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("invoiceNo") String invoiceNo,@Param("assessmentId") String assessmentId ,
            @Param("igmTransId") String igmTransId);
	
	

}
