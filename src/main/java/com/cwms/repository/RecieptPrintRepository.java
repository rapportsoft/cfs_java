package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.FinTrans;

public interface RecieptPrintRepository extends JpaRepository<FinTrans, String> {
	
	
	@Query(value ="select group_concat( a.Doc_Type ) Doc_Type, a.Trans_Id,DATE_FORMAT(Trans_Date,'%d-%m-%Y %H:%i') Trans_Date,a.Party_Id,a.Acc_Sr_no,SUM(a.Document_Amt),a.Created_By,a.Approved_By , b.Party_Name, b.IEC_CODE ,c.address_1,c.address_2, c.address_3,c.GST_No, a.Status,Line_Id,a.Payment_Mode,a.cheque_no,DATE_FORMAT(a.cheque_date,'%d/%m/%Y %H:%i'),a.Document_Amt,a.OPR_ADJ_Trans_Id,a.status,coalesce(a.TDS_Bill_Amt,0), a.Bank_Name "
			+ "from fintrans a left outer join party b on a.Company_Id = b.Company_Id and a.Party_Id = b.Party_Id left outer join partyaddress c on a.Company_Id = c.Company_Id and a.Party_Id = c.Party_Id and a.Acc_Sr_no = c.Sr_No  "
			+ "where  a.Company_Id =:companyId and a.Branch_Id=:branchId AND   a.Trans_Id=:transId AND a.Status IN('A') and   Ledger_Type = 'AR' and Doc_Type in ('RE', 'AD','AJ' ) group by trans_id,payment_mode;", nativeQuery = true)
	List<Object[]> getRecieptDetails(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("transId") String transId);
	
	
	@Query(value ="SELECT b.Company_Name, a.Branch_Name, a.Address_1, a.Address_2, a.Address_3, e.Jar_Dtl_Desc, f.Jar_Dtl_Desc,  g.Jar_Dtl_Desc, a.Pin, a.Phone_no,b.GST_No,b.CIN_No, b.pan_no,b.Address_1, b.Address_2, b.Address_3, "
			+ "b.City_Name,b.PIN, a.email_id  "
			+ "FROM  branch a LEFT OUTER JOIN company b ON a.Company_Id = b.Company_Id LEFT OUTER JOIN jar_detail e ON a.Company_Id=e.Company_Id AND a.City=e.Jar_Dtl_Id AND e.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail f  "
			+ "ON a.Company_id=f.Company_id AND a.State=f.Jar_Dtl_Id AND f.Jar_Id = 'J00026' LEFT OUTER JOIN jar_detail g ON a.Company_id=g.Company_id AND a.Country=g.Jar_Dtl_Id AND g.Jar_Id = 'J00002' WHERE  a.Branch_Id=:branchId AND a.Company_Id=:companyId AND a.Status = 'A' ",nativeQuery = true)
	Object getCompanyAddressDetails(@Param("companyId") String companyId,@Param("branchId") String branchId );
	
	
	@Query(value = "select a.OPR_INVOICE_NO invoiceNo , DATE_FORMAT(b.Invoice_Date,'%d-%m-%Y') invoiceDate, a.Document_Amt ,  a.Receipt_Amt , a.Invoice_bal_amt, IF(b.Invoice_Type = 'IMP' , 'Import Invoice' , IF(b.Invoice_Type = 'EXP' , 'Export Invoice' , 'Tax Invoice' ) ) Invoice_Type "
			+ "from fintransinv a left outer join cfinvsrv b on a.OPR_INVOICE_NO = b.Invoice_No and a.Company_Id = b.Company_Id  and a.Branch_Id = b.Branch_Id and a.Party_Id = b.Party_Id  "
			+ "where  a.Company_Id =:companyId and a.Branch_Id=:branchId  AND a.Trans_Id=:transId AND a.Status IN('A') and   a.Ledger_Type = 'AR' and a.Doc_Type in ('RE', 'AD','AJ' ) ",nativeQuery = true)
	List<Object[]> getRecieptInvDetails(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("transId") String transId);
	
	
	@Query(value ="select group_concat( a.Doc_Type ) Doc_Type, a.Trans_Id,DATE_FORMAT(Trans_Date,'%d-%m-%Y %H:%i') Trans_Date,a.Party_Id,a.Acc_Sr_no,SUM(a.Document_Amt),a.Created_By,a.Approved_By , b.Party_Name, b.IEC_CODE ,c.address_1,c.address_2, c.address_3,c.GST_No, a.Status,Line_Id,a.Payment_Mode,a.cheque_no,DATE_FORMAT(a.cheque_date,'%d/%m/%Y %H:%i'),a.Document_Amt,a.OPR_ADJ_Trans_Id,a.status,coalesce(a.TDS_Bill_Amt,0), a.Bank_Name "
			+ "from fintransadj a left outer join party b on a.Company_Id = b.Company_Id and a.Party_Id = b.Party_Id left outer join partyaddress c on a.Company_Id = c.Company_Id and a.Party_Id = c.Party_Id and a.Acc_Sr_no = c.Sr_No  "
			+ "where  a.Company_Id =:companyId and a.Branch_Id=:branchId AND   a.Trans_Id=:transId AND a.Status IN('A') and   Ledger_Type = 'AR' and Doc_Type in ('RE', 'AD','AJ' ) group by trans_id,payment_mode;", nativeQuery = true)
	List<Object[]>getRecieptDetailsAdj(@Param("companyId") String companyId, 
            @Param("branchId") String branchId,
            @Param("transId") String transId);

}

