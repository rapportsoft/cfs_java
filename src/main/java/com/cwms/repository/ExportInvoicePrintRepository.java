package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportStuffTally;

public interface ExportInvoicePrintRepository  extends JpaRepository<ExportStuffTally, String>{
	
	@Query(value ="select distinct a.container_no,DATE_FORMAT(a.approved_date,'%d %b %Y %T'),a.party_id,b.party_name BillingParty,b.pan_no,ba.address_1,ba.address_2,ba.address_3,ba.pin,h.jar_dtl_desc State,j.jar_dtl_desc Statecode,ba.gst_no,a.lock_down,u.user_name,v.user_name,a.bill_amt, "
			+ "a.IRN,a.signqrcode_path,s.exporter_name ExporterName,f.party_name CHAName,fa.address_1,fa.address_2,fa.address_3,fa.pin,i.jar_dtl_desc,k.jar_dtl_desc,fa.gst_no,sli.party_name lineName,ag.party_name AgentName, s.igst,s.cgst,s.sgst,s.sez,a.Mail_Flag, s.sb_no , "
			+ "DATE_FORMAT( s.sb_date, '%d %b %Y' ) , DATE_FORMAT(s.min_carting_trans_date, '%d %b %Y' ), ROUND(s.sbweight, 2 ), '0.00' , s.area_used , s.commodity_description, s.transaction_type, a.invoice_type ,b.FINANCE_MAIL,a.Created_By,DATE_FORMAT(a.Invoice_Date,'%d/%b/%Y') ,s.is_bos "
			+ "from cfinvsrv  a left outer join cfassesmentsheet s on a.company_id = s.company_id and a.branch_id = s.branch_id and a.invoice_no = s.invoice_no and a.container_no = s.assesment_id and a.Profitcentre_Id = s.Profitcentre_Id  "
			+ "left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.acc_sr_no  "
			+ "left outer join jar_detail h on ba.company_id=h.company_id and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014'   "
			+ "left outer join party sli on s.company_id=sli.company_id and s.sl=sli.party_id left outer join party ag on s.company_id=ag.company_id and s.sa=ag.party_id left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id "
			+ "left outer join party d on s.company_id=d.company_id and s.cha=d.party_id   left outer join partyaddress fa on s.company_id=fa.company_id and s.cha=fa.party_id and s.cha_sr_no = fa.sr_no  left outer join jar_detail i on fa.company_id=i.company_id and fa.state=i.jar_dtl_id and i.jar_id='J00026' "
			+ " left outer join jar_detail k on fa.company_id=k.company_id and fa.state=k.jar_dtl_id and k.jar_id='J00014' left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id  left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id where a.Company_Id=:companyId AND a.branch_id=:branchId and a.status='A'  and a.invoice_no =:invoiceNo",nativeQuery = true)
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
	
	
	@Query(value = "select distinct a.container_no, a.container_size, a.container_type, v.Vessel_Name , e.VIA_No , DATE_FORMAT(a.Gate_In_Date,'%d-%m-%Y'), DATE_FORMAT(a.Stuff_Tally_Date,'%d-%m-%Y'), e.Container_Invoice_Type, e.SB_No , DATE_FORMAT( e.SB_Date, '%d %b %Y' ) , DATE_FORMAT(MIN(f.Carting_Trans_Date), '%d %b %Y' ), ROUND(g.Gross_Weight, 2 ), ROUND(SUM(DISTINCT anx.Invoice_Amt),2) Amount , ROUND(SUM(DISTINCT  f.Area_Occupied),2) , e.Commodity,IFNULL(DATE_FORMAT(c.Gate_Out_Date,'%d %b %Y' ),''), e.Exporter_Name  "
			+ "from cfassesmentsheet a left outer join cfexpmovementreq c on a.company_id=c.company_id  and a.branch_id=c.branch_id  and a.container_no=c.container_no and a.Movement_Req_Id=c.Movement_Req_Id and a.Profitcentre_Id = c.Profitcentre_Id and a.Assesment_Id = c.Assesment_Id left outer join cfstufftally e on e.company_id=c.company_id  and e.branch_id=c.branch_id  and e.Movement_Req_Id=c.Movement_Req_Id and e.Stuff_Tally_Id = c.Stuff_Tally_Id and e.Profitcentre_Id = c.Profitcentre_Id and e.container_no = c.container_No "
			+ "left outer join cfcrtg f on e.Company_Id=f.Company_Id  and e.Branch_Id=f.Branch_Id and e.Carting_Trans_Id=f.Carting_Trans_Id and e.Carting_Line_Id=f.Carting_Line_Id and e.Profitcentre_Id = f.Profitcentre_Id and e.SB_Trans_Id = f.SB_Trans_Id and e.SB_No = f.SB_No and e.SB_Line_Id = f.SB_Line_No left outer join cfsbcrg g on e.Company_Id=g.Company_Id  and e.Branch_Id=g.Branch_Id and  e.Profitcentre_Id = g.Profitcentre_Id and e.SB_Trans_Id = g.SB_Trans_Id and e.SB_No = g.SB_No and e.SB_Line_Id = g.SB_Line_No "
			+ "left outer join vessel v on e.Company_Id = v.Company_Id and e.Vessel_Id = v.Vessel_Id left outer join cfinvsrvanx anx on a.company_id=anx.company_id  and a.branch_id=anx.branch_id and a.container_no=anx.container_no and a.Profitcentre_Id = anx.Profitcentre_Id and a.Assesment_Id = anx.Process_Trans_Id and a.invoice_no=anx.Invoice_No and a.container_no=anx.container_no and anx.Service_Id='S00008' and e.sb_no=anx.CargoSBNo "
			+ "where a.Company_Id=:companyId AND  a.branch_id=:branchId and a.status='A' and a.SB_Trans_Id =:sbTransID and a.assesment_id =:assesmentId group by a.container_no , e.sb_trans_Id, e.sb_No  order by a.container_no ASC",nativeQuery = true)
	List<Object[]> getContainerAndSbDetails(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("sbTransID") String sbTransID,
            @Param("assesmentId") String assesmentId
            );
	
	@Query(value ="select distinct a.SB_No , DATE_FORMAT( a.SB_Date, '%d %b %Y' ) , DATE_FORMAT(a.Min_Carting_Trans_Date, '%d %b %Y' ), a.Gross_Weight ,a.Invoice_Amt Amount , ROUND(SUM(f.Area_Occupied),2) , a.Commodity_Description,IFNULL(DATE_FORMAT(a.invoice_Cal_Date,'%d %b %Y' ),''),a.Exporter_Name  "
			+ "from cfassesmentsheet a left outer join cfbacktotown e on e.company_id=a.company_id  and e.branch_id=a.branch_id  and a.Invoice_No=e.Invoice_No and e.Assesment_Id = a.Assesment_Id and e.Profitcentre_Id = a.Profitcentre_Id and a.sb_no = e.SB_No "
			+ "left outer join cfcrtg f on e.Company_Id=f.Company_Id  and e.Branch_Id=f.Branch_Id and   e.Profitcentre_Id = f.Profitcentre_Id and e.SB_Trans_Id = f.SB_Trans_Id and e.SB_No = f.SB_No and e.SB_Line_No = f.SB_Line_No "
			+ "left outer join cfsbcrg g on e.Company_Id=g.Company_Id  and e.Branch_Id=g.Branch_Id and  e.Profitcentre_Id = g.Profitcentre_Id and e.SB_Trans_Id = g.SB_Trans_Id and e.SB_No = g.SB_No and e.SB_Line_No = g.SB_Line_No "
			+ "where a.Company_Id=:companyId AND a.branch_id=:branchId and a.status='A' and a.assesment_id =:assesmentId group by a.container_no , e.sb_trans_Id, e.sb_No  order by a.container_no ASC",nativeQuery = true)
	List<Object[]> getcartingSbDetails(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("assesmentId") String assesmentId
            );
	
	
	@Query(value ="select distinct a.container_no,DATE_FORMAT(a.approved_date,'%d %b %Y %T'),a.party_id,b.party_name BillingParty,b.pan_no,ba.address_1,ba.address_2,ba.address_3,ba.pin,h.jar_dtl_desc State,j.jar_dtl_desc Statecode,ba.gst_no,a.lock_down,u.user_name,v.user_name,a.bill_amt, a.IRN,a.signqrcode_path,f.party_name ExporterName,d.party_name CHAName,fa.address_1,fa.address_2,fa.address_3,fa.pin,i.jar_dtl_desc,k.jar_dtl_desc, "
			+ "fa.gst_no,sl.party_name lineName,ag.party_name AgentName, s.IGST,s.CGST,s.SGST,s.sez,a.Mail_Flag, s.SB_No , DATE_FORMAT( s.SB_Date, '%d %b %Y' ) , DATE_FORMAT(s.Min_Carting_Trans_Date, '%d %b %Y' ), ROUND(s.SBWeight, 2 ), '0.00' , '0.00' , s.Commodity_Description,b.FINANCE_MAIL,a.Created_By,aa.address_1,aa.address_2,aa.address_3,aa.pin, DATE_FORMAT(s.final_invoice_date,'%d %b %Y')"
			+ "from cfinvsrv  a left outer join cfassesmentsheet s on a.Company_Id = s.Company_Id and a.Branch_Id = s.Branch_Id and a.Invoice_No = s.Invoice_No and a.Container_no = s.Assesment_Id and a.Profitcentre_Id = s.Profitcentre_Id left outer join party b on a.company_id=b.company_id and b.party_id=a.party_id left outer join partyaddress ba on a.company_id=ba.company_id and ba.party_id=a.party_id and ba.sr_no = a.acc_sr_no  "
			+ "left outer join jar_detail h on ba.company_id=h.company_id and ba.state=h.jar_dtl_id and h.jar_id='J00026' left outer join jar_detail j on ba.company_id=j.company_id and ba.state=j.jar_dtl_id and j.jar_id='J00014' left outer join party sl on s.company_id=sl.company_id and s.sl=sl.party_id  "
			+ "left outer join party ag on s.company_id=ag.company_id and s.Oth_Party_id=ag.party_id left outer join party f on s.company_id=f.company_id and s.importer_id=f.party_id left outer join party d on s.company_id=d.company_id and s.CHA=d.party_id   left outer join partyaddress fa on s.company_id=fa.company_id and s.CHA=fa.party_id and s.CHA_sr_no = fa.sr_no  left outer join jar_detail i on fa.company_id=i.company_id and fa.state=i.jar_dtl_id and i.jar_id='J00026' "
			+ "left outer join jar_detail k on fa.company_id=k.company_id and fa.state=k.jar_dtl_id and k.jar_id='J00014' left outer join userinfo u on a.company_id=u.company_id and a.post_by=u.user_id  left outer join userinfo v on a.company_id=v.company_id and a.approved_by=v.user_id  "
			+ "left outer join partyaddress aa on s.company_id=aa.company_id and s.Oth_Party_id=aa.party_id and s.Oth_Sr_no = aa.sr_no  where a.Company_Id=:companyId  AND a.branch_id=:branchId and a.status='A'  and a.invoice_no =:invoiceNo  and a.Profitcentre_Id = 'N00004'",nativeQuery = true)
	Object getInvoiceDetailscarting(@Param("companyId") String companyId, 
	        @Param("branchId") String branchId,  
	        @Param("invoiceNo") String invoiceNo);
	
	@Query(value = "select distinct a.container_no, a.container_size, a.container_type, '' , '' , DATE_FORMAT(a.Gate_In_Date,'%d-%m-%Y'), DATE_FORMAT(a.Stuff_Tally_Date,'%d-%m-%Y'), '', e.SB_No , DATE_FORMAT( e.SB_Date, '%d %b %Y' ) , DATE_FORMAT(a.Min_Carting_Trans_Date, '%d %b %Y' ), ROUND(g.Gross_Weight, 2 ), ROUND(g.FOB,2 ) Amount , ROUND(SUM(f.Area_Occupied),2) , e.Commodity,IFNULL(DATE_FORMAT(a.invoice_Cal_Date,'%d %b %Y' ),''),a.Exporter_Name  "
			+ "from cfassesmentsheet a left outer join cfbacktotown e on e.company_id=a.company_id  and e.branch_id=a.branch_id and a.Invoice_No=e.Invoice_No and e.Assesment_Id = a.Assesment_Id and e.Profitcentre_Id = a.Profitcentre_Id and a.sb_no = e.SB_No left outer join cfcrtg f on e.Company_Id=f.Company_Id  and e.Branch_Id=f.Branch_Id and e.Profitcentre_Id = f.Profitcentre_Id and e.SB_Trans_Id = f.SB_Trans_Id and e.SB_No = f.SB_No and e.SB_Line_No = f.SB_Line_No "
			+ "left outer join cfsbcrg g on e.Company_Id=g.Company_Id  and e.Branch_Id=g.Branch_Id and  e.Profitcentre_Id = g.Profitcentre_Id and e.SB_Trans_Id = g.SB_Trans_Id and e.SB_No = g.SB_No and e.SB_Line_No = g.SB_Line_No where a.Company_Id=:companyId AND  a.branch_id=:branchId and a.status='A' and a.SB_Trans_Id =:sbTransID  and a.assesment_id = :assesmentId group by a.container_no , e.sb_trans_Id, e.sb_No  order by a.container_no ASC",nativeQuery = true)
	List<Object[]> getContainerAndSbDetailsbackttown(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("sbTransID") String sbTransID,
            @Param("assesmentId") String assesmentId
            );
	

}
