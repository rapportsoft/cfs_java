package com.cwms.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GateIn;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

public interface FinanceReportsRepository extends JpaRepository<GateIn, String>{

	@Query(value = "SELECT IFNULL(DATE_FORMAT(b.Invoice_Date, '%d-%m-%Y %H:%i'), '') AS Invoice_Date, " +
            "a.Invoice_No AS invno, " +
            "'SALES INVOICE' AS INVOICE_DEBITNOTEFLAG, " +
            "a.Receipt_No, " +
            "a.importer_name, " +
            "IF(a.Profitcentre_Id = 'N00002', a.igm_no, a.SB_No) AS Document_No, " +
            "IFNULL(bl.party_name, '') AS BillTo_Party, pa.address_1, pa.address_2, pa.address_3, '' AS address_4, " +
            "'' AS vatno, '' AS cstno, bl.GST_No AS GST_No, x.Service_Short_desc, " +
            "a.Invoice_No AS invoice_no, c.Local_Amt AS amt, a.CGST, a.SGST, a.IGST, '' AS CREDIT_PERIOD, " +
            "CONCAT(a.Invoice_No, '/', bl.party_name, '/', " +
            "IF(a.Profitcentre_Id = 'N00002', a.Importer_Name, a.Exporter_Name), '/', " +
            "IF(a.Profitcentre_Id = 'N00002', a.igm_no, a.SB_No), '/', a.IRN) AS NARRATION, " +
            "x.Sac_Code AS scacode, a.Int_Comments AS remark, a.IRN AS IRN, " +
            "IF(a.Profitcentre_Id = 'N00002', m.Container_Status, s.container_Status) AS Container_Status,'',jr.jar_dtl_desc, " +
            "c.Tax_Perc_N AS Tax_Amt, b.Created_Date AS OTY, IFNULL(ch.party_name, '') AS cha_name, a.Billing_Party AS Billing_Partyid " +
            "FROM cfassesmentsheet a " +
            "LEFT OUTER JOIN cfinvsrv b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id AND " +
            "a.Assesment_id = b.container_no AND " +
             "b.invoice_no = a.invoice_no AND a.profitcentre_id = b.profitcentre_id " +
            "LEFT OUTER JOIN cfinvsrvdtl c ON b.Company_Id = c.Company_Id AND b.Branch_Id = c.Branch_Id AND " +
            "b.invoice_no = c.invoice_no AND b.profitcentre_id = c.profitcentre_id AND b.party_Id = c.party_id " +
            "LEFT OUTER JOIN cfigmcn m ON a.Company_Id = m.Company_Id AND a.Branch_Id = m.Branch_Id AND " +
            "a.igm_trans_id = m.igm_trans_id AND a.igm_no = m.igm_no AND a.profitcentre_id = m.profitcentre_id AND " +
            "a.igm_Line_no = m.igm_Line_no " +
            "LEFT OUTER JOIN cfstufftally s ON a.Company_Id = s.Company_Id AND a.Branch_Id = s.Branch_Id AND " +
            "a.Container_no = s.Container_no AND a.Movement_Req_Id = s.Movement_Req_Id AND a.Profitcentre_Id = s.Profitcentre_Id " +
            "LEFT OUTER JOIN party ch ON a.company_id = ch.company_id AND a.cha = ch.party_id AND a.branch_id = ch.branch_id " +
            "LEFT OUTER JOIN partyaddress pa ON a.company_id = pa.company_id AND a.party_id = pa.party_id AND a.branch_id = pa.branch_id and a.party_sr_no =pa.sr_no " +
            "LEFT OUTER JOIN party bl ON a.Company_Id = bl.Company_Id AND a.party_id = bl.party_id AND a.branch_id = bl.branch_id " +
            "LEFT OUTER JOIN services x ON c.Company_Id = x.Company_Id AND c.service_id = x.service_id AND c.branch_id = x.branch_id " +
            "LEFT OUTER JOIN jar_detail jr ON a.company_id = jr.company_id AND x.Sac_Code = jr.jar_dtl_id AND jr.jar_id='J00024' " +
            "WHERE a.company_id = :companyId AND a.Branch_Id = :branchId " +
            "AND a.status = 'A' AND b.status = 'A' AND c.status = 'A' " +
            "AND b.Created_Date BETWEEN :startDate AND :endDate  AND " +
            "(:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty)  " +
            "GROUP BY a.assesment_id, a.Container_no, b.invoice_no, c.service_id " +
            "UNION " +
            "SELECT IFNULL(DATE_FORMAT(cj.Invoice_Date, '%d-%m-%Y %H:%i'), '') AS Invoice_Date, cj.invoice_no AS invno, " +
            "'CREDIT NOTE INVOICE' AS INVOICE_DEBITNOTEFLAG, aj.Receipt_No, aj.importer_name,'', " +
            "lj.Party_Name AS BillTo_Party, pa.address_1, pa.address_2, pa.address_3, '' AS address_4, '' AS vatno, " +
            "'' AS cstno, lj.GST_No AS GST_No, xj.Service_Short_desc, cj.invoice_no AS invoice_no, " +
            "bj.Local_Amt AS amt, cj.CGST, cj.SGST, cj.IGST, '' AS CREDIT_PERIOD, " +
            "CONCAT(cj.invoice_no, '/', lj.Party_Name, '/', IF(cj.Profitcentre_Id = 'N00002', aj.Importer_Name, aj.Exporter_Name), '/', " +
            "IF(cj.Profitcentre_Id = 'N00002', aj.Igm_No, aj.SB_No), '/', cj.IRN) AS NARRATION, xj.Sac_Code AS scacode, " +
            "cj.Comments AS remark, cj.IRN AS IRN, mj.Container_Status AS Container_Status,'',jr.jar_dtl_desc, bj.Tax_Perc_N AS Tax_Amt, " +
            "cj.Created_Date AS OTY, IFNULL(pj.party_name, '') AS cha_name, '' AS Billing_Partyid " +
            "FROM cfinvsrvcn cj " +
            "LEFT OUTER JOIN cfassesmentsheet aj ON cj.Company_Id = aj.Company_Id AND cj.branch_id = aj.branch_id AND " +
            "(aj.Assesment_Id = cj.Container_No OR aj.Assesment_Id = cj.ERP_Doc_Ref_No) " +
            "LEFT OUTER JOIN cfinvsrvcndtl bj ON cj.Company_Id = bj.Company_Id AND cj.Branch_Id = bj.Branch_Id AND " +
            "cj.invoice_no = bj.invoice_no AND bj.profitcentre_id = cj.profitcentre_id AND bj.party_Id = cj.party_id " +
            "LEFT OUTER JOIN party lj ON lj.Company_Id = aj.Company_Id AND lj.Party_Id = aj.Party_Id AND lj.branch_id = aj.branch_id " +
            "LEFT OUTER JOIN party pj ON aj.Company_Id=pj.Company_Id AND aj.CHA=pj.Party_Id AND aj.branch_id=pj.branch_id " +
            "LEFT OUTER JOIN services xj ON bj.Company_Id = xj.Company_Id AND bj.service_id = xj.service_id AND bj.branch_id = xj.branch_id " +
            "LEFT OUTER JOIN partyaddress pa ON aj.company_id = pa.company_id AND aj.party_id = pa.party_id AND aj.branch_id = pa.branch_id and aj.party_sr_no =pa.sr_no " +
            "LEFT OUTER JOIN cfigmcn mj ON aj.Company_Id = mj.Company_Id AND aj.Branch_Id = mj.Branch_Id AND " +
            "aj.igm_trans_id = mj.igm_trans_id AND aj.igm_no = mj.igm_no AND aj.profitcentre_id = mj.profitcentre_id AND " +
            "aj.igm_Line_no = mj.igm_Line_no " +
            "LEFT OUTER JOIN jar_detail jr ON cj.company_id = jr.company_id AND xj.Sac_Code = jr.jar_dtl_id AND jr.jar_id='J00024' " +
            "WHERE cj.company_id = :companyId AND cj.Branch_Id = :branchId AND aj.status = 'A' AND bj.status = 'A' AND cj.status = 'A' " +
            "AND cj.Created_Date BETWEEN :startDate AND :endDate " +
            "GROUP BY aj.assesment_id, aj.Container_no, cj.invoice_no, bj.service_id " +
            "ORDER BY OTY ASC, amt DESC", nativeQuery = true)
List<Object[]> getInvoiceSalesRegisterData(
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("billParty") String billParty);







@Query(value = "SELECT " +
        "IF(a.Profitcentre_Id = 'N00002', a.Importer_Name, a.Exporter_Name) AS Importer_Name, " +
        "IFNULL(ln.party_name, '') AS line_Name, " +
        "IFNULL(ch.party_name, '') AS cha_name, " +
        "ac.Party_Name, " +
        "IF(a.Profitcentre_Id = 'N00002', g.igm_no, a.SB_No) AS igm_or_sb_no, " +
        "g.IGM_Line_No, " +
        "a.Container_no, " +
        "a.Container_Size, " +
        "a.Container_Type, " +
        "a.Cargo_Weight, " +
        "a.Gross_Weight, " +
        "IFNULL(DATE_FORMAT(a.Destuff_Date, '%d-%m-%Y %H:%i:%s'), '') AS Destuff_Date, " +
        "g.Cargo_Type, " +
        "IFNULL(DATE_FORMAT(a.Gate_In_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_In_Date, " +
        "IFNULL(DATE_FORMAT(m.Gate_Out_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_Out_Date, " +
        "cm.Jar_Dtl_Desc, " +
        "g.Yard_Location, " +
        "a.Invoice_No, " +
        "IFNULL(DATE_FORMAT(b.Invoice_Date, '%d-%m-%Y'), '') AS Invoice_Date, " +
        "a.Invoice_Amt, " +
        "a.IGST, " +
        "a.CGST, " +
        "a.SGST, " +
        "GROUP_CONCAT(DISTINCT CONCAT(c.service_id, '~', c.Local_Amt, '~', c.Tax_Perc_N, '~', c.invoice_Amt, '~', x.Service_short_desc, '~', c.Execution_Unit, '~', c.Free_Days, '~', c.Chargable_Days, '~', x.Service_Group)) AS tot_services, " +
        "c.service_id, " +
        "c.Local_Amt, " +
        "c.Tax_Perc_N, " +
        "c.invoice_Amt, " +
        "a.Billing_Party, " +
        "IFNULL(bl.party_name, '') AS BillTo_Party, " +
        "a.Credit_Type, " +
        "a.Area_Used, " +
        "m.Container_Weight, " +
        "m.Container_Status, " +
        "m.Gate_Out_Type, " +
        "a.Profitcentre_Id, " +
        "a.Inv_Type, " +
        "IF(a.Profitcentre_Id = 'N00002', i.Gate_In_Date, a.Gate_In_Date) AS empty_Gatein, " +
        "IF(a.Profitcentre_Id = 'N00002', i.Gate_Out_Date, a.Gate_Out_Date) AS emptygateout, " +
        "'INVOICE' AS INVOICE_DEBITNOTEFLAG, " +
        "b.created_Date AS OCD, " +
        "a.Assesment_Id ," +
        "g.cycle ," +
        "b.invoice_Amt ," +
        "jr.jar_dtl_desc "+
        "FROM cfassesmentsheet a " +
        "LEFT OUTER JOIN cfigmcrg g ON a.Company_Id = g.Company_Id AND a.Branch_Id = g.Branch_Id AND a.igm_trans_id = g.igm_trans_id AND a.igm_no = g.igm_no AND a.profitcentre_id = g.profitcentre_id AND a.igm_Line_no = g.igm_Line_no " +
        "LEFT OUTER JOIN cfigmcn m ON a.Company_Id = m.Company_Id AND a.Branch_Id = m.Branch_Id AND a.igm_trans_id = m.igm_trans_id AND a.igm_no = m.igm_no AND a.profitcentre_id = m.profitcentre_id AND a.igm_Line_no = m.igm_Line_no " +
        "LEFT OUTER JOIN cfinvsrv b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id AND a.Assesment_id = b.container_no AND b.invoice_no = a.invoice_no AND a.profitcentre_id = b.profitcentre_id " +
        "LEFT OUTER JOIN cfinvsrvanx c ON b.Company_Id = c.Company_Id AND b.Branch_Id = c.Branch_Id AND b.invoice_no = c.invoice_no AND b.profitcentre_id = c.profitcentre_id AND b.party_Id = c.party_id AND a.Container_no = c.Container_no " +
        "LEFT OUTER JOIN party ch ON a.company_id = ch.company_id AND a.cha = ch.party_id AND a.branch_id = ch.branch_id " +
        "LEFT OUTER JOIN party ln ON a.company_id = ln.company_id AND a.SL = ln.party_id AND a.branch_id = ln.branch_id " +
        "LEFT OUTER JOIN party ac ON a.company_id = ac.company_id AND a.party_id = ac.party_id AND a.branch_id = ac.branch_id " +
        "LEFT OUTER JOIN party bl ON a.Company_Id = bl.Company_Id AND a.party_id = bl.party_id AND a.branch_id = bl.branch_id " +
        "LEFT OUTER JOIN services x ON c.Company_Id = x.Company_Id AND c.service_id = x.service_id AND c.branch_id = x.branch_id " +
        "LEFT OUTER JOIN jar_detail cm ON a.Company_Id = cm.Company_Id AND a.commodity_Code = cm.Jar_Dtl_Id AND cm.Jar_Id = 'J00006' " +
        "LEFT OUTER JOIN jar_detail jr ON a.company_id = jr.company_id AND x.Sac_Code = jr.jar_dtl_id AND jr.jar_id='J00024' " +
        "LEFT OUTER JOIN cfimpinventory i ON a.Company_Id = i.Company_Id AND a.Branch_Id = i.Branch_Id AND a.Container_no = i.Container_no AND a.IGM_Trans_Id = i.IGM_Trans_Id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND b.company_id = :companyId AND b.branch_id = :branchId AND c.company_id = :companyId AND c.branch_id = :branchId " +
        "AND a.status = 'A' AND a.trans_type != 'MISC' AND b.status = 'A' AND c.status = 'A'"
//        + "and c.container_no !='' "
//        + "AND c.container_no IS NOT NULL "
+ "AND (c.container_no !='' AND c.container_no IS NOT NULL) "
        + "AND b.Created_Date BETWEEN :startDate AND :endDate " +
        " AND (:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty)  " +
        "GROUP BY a.assesment_id, a.Container_no, b.invoice_no " +
        "UNION " +
        "SELECT " +
        "    IF(aj.Profitcentre_Id = 'N00002', aj.Importer_Name, aj.Exporter_Name) AS Importer_Name, " +
        "    IFNULL(ln.party_name, '') AS line_Name, " +
        "    IFNULL(pj.party_name, '') AS cha_name, " +
        "    lj.Party_Name AS BillTo_Party, " +
        "    IF(aj.Profitcentre_Id = 'N00002', aj.Igm_No, aj.SB_No) AS igm_or_sb_no, " +
        "    aj.igm_line_no, aj.Container_no, aj.Container_Size, aj.Container_Type, " +
        "    aj.Cargo_Weight, aj.Gross_Weight, " +
        "    IFNULL(DATE_FORMAT(aj.Destuff_Date, '%d-%m-%Y %H:%i:%s'), '') AS Destuff_Date, " +
        "    g.Cargo_Type, " +
        "    IFNULL(DATE_FORMAT(aj.Gate_In_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_In_Date, " +
        "    IFNULL(DATE_FORMAT(m.Gate_Out_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_Out_Date, " +
        "    cm.Jar_Dtl_Desc, g.Yard_Location,  " +
        "    cj.invoice_no AS invno, " +
        "    IFNULL(DATE_FORMAT(cj.Invoice_Date, '%d-%m-%Y'), '') AS Invoice_Date, " +
        "    bj.Local_Amt AS amt, " +
        "    cj.IGST, cj.CGST, cj.SGST,  " +
        "    GROUP_CONCAT(CONCAT( " +
        "        bj.service_id, '~', bj.Local_Amt, '~', bj.Tax_Perc_N, '~', " +
        "        bj.invoice_Amt, '~', xj.Service_short_desc, '~', '0', '~', " +
        "        '0', '~', '0', '~', xj.Service_Group " +
        "    )) AS tot_services,  " +
        "    bj.service_id, bj.Local_Amt, bj.Tax_Perc_N, bj.invoice_Amt,  " +
        "    aj.Billing_Party, IFNULL(bl.party_name, '') AS BillTo_Party, " +
        "    aj.Credit_Type, aj.Area_Used, m.Container_Weight,  " +
        "    m.Container_Status, m.Gate_Out_Type, aj.Profitcentre_Id,  " +
        "    aj.Inv_Type, IF(aj.Profitcentre_Id = 'N00002', i.Gate_In_Date, aj.Gate_In_Date) AS empty_Gatein,  " +
        "    IF(aj.Profitcentre_Id = 'N00002', i.Gate_Out_Date, aj.Gate_Out_Date) AS emptygateout,  " +
        "    'CREDIT NOTE INVOICE' AS INVOICE_DEBITNOTEFLAG,  " +
        "    cj.created_Date AS OCD, cj.Container_No ,g.cycle ," +
        "    '' ," +
        "    jr.jar_dtl_desc "+
        "FROM cfinvsrvcn cj " +
        "LEFT OUTER JOIN cfassesmentsheet  aj  on cj.Company_Id = aj.Company_Id and cj.branch_id = aj.branch_id  and ( aj.Assesment_Id=cj.Container_No or aj.Assesment_Id=cj.ERP_Doc_Ref_No  ) "+
        "LEFT OUTER JOIN cfigmcrg g ON aj.Company_Id = g.Company_Id AND aj.Branch_Id = g.Branch_Id AND aj.igm_trans_id = g.igm_trans_id AND aj.igm_no = g.igm_no AND aj.profitcentre_id = g.profitcentre_id AND aj.igm_Line_no = g.igm_Line_no " +
        " left outer join cfigmcn m on  aj.Company_Id = m.Company_Id  and aj.Branch_Id = m.Branch_Id and aj.igm_trans_id=m.igm_trans_id  and aj.igm_no=m.igm_no "
        + " and aj.profitcentre_id=m.profitcentre_id  and aj.igm_Line_no=m.igm_Line_no " +
        "left outer join cfinvsrvcndtl bj on  cj.Company_Id = cj.Company_Id and cj.Branch_Id = bj.Branch_Id    and cj.invoice_no=bj.invoice_no and bj.profitcentre_id=cj.profitcentre_id "
        + " and bj.party_Id = cj.party_id " +
        "LEFT JOIN  " +
        "    party bl ON aj.Company_Id = bl.Company_Id  " +
        "    AND aj.party_id = bl.party_id  " +
        "    AND aj.branch_id = bl.branch_id    " +
        "LEFT JOIN  " +
        "    party lj ON lj.Company_Id = aj.Company_Id    " +
        "    AND lj.Party_Id = aj.Party_Id   " +
        "    AND lj.branch_id = aj.branch_id  " +
        "LEFT JOIN  " +
        "    party pj ON aj.Company_Id = pj.Company_Id  " +
        "    AND aj.CHA = pj.Party_Id     " +
        "    AND aj.branch_id = pj.branch_id  " +
        "LEFT JOIN  " +
        "    services xj ON bj.Company_Id = xj.Company_Id  " +
        "    AND bj.service_id = xj.service_id   " +
        "    AND bj.branch_id = xj.branch_id   " +
        "LEFT JOIN  " +
        "    party ln ON aj.company_id = ln.company_id  " +
        "    AND aj.SL = ln.party_id   " +
        "    AND aj.branch_id = ln.branch_id  " +
        "LEFT OUTER JOIN jar_detail cm ON aj.Company_Id = cm.Company_Id AND aj.commodity_Code = cm.Jar_Dtl_Id AND cm.Jar_Id = 'J00006' " +
        "LEFT JOIN  " +
        "    cfimpinventory i ON aj.Company_Id = i.Company_Id  " +
        "    AND aj.Branch_Id = i.Branch_Id   " +
        "    AND aj.Container_no = i.Container_no  " +
        "    AND aj.IGM_Trans_Id = i.IGM_Trans_Id   " +
        "LEFT OUTER JOIN jar_detail jr ON cj.company_id = jr.company_id AND xj.Sac_Code = jr.jar_dtl_id AND jr.jar_id='J00024' " +
        "WHERE aj.company_id = :companyId AND aj.branch_id = :branchId AND cj.company_id = :companyId AND cj.branch_id = :branchId " +
        "AND aj.status = 'A' AND aj.trans_type != 'MISC' AND cj.status = 'A' AND bj.status = 'A' AND cj.Created_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY aj.assesment_id, aj.Container_no, cj.invoice_no", nativeQuery = true)
List<Object[]> getInvoiceDetailsContainerWise( @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("billParty") String billParty);



@Query(value = "SELECT a.Invoice_No, c.Container_no, c.Process_Trans_Id, " +
        "GROUP_CONCAT(CONCAT(c.service_id, '~', c.Local_Amt, '~', c.Tax_Perc_N, '~', c.invoice_Amt, '~', x.Service_short_desc, '~', c.Execution_Unit, '~', c.Free_Days, '~', c.Chargable_Days, '~', x.Service_Group)) AS tot_services " +
        "FROM cfinvsrv a " +
        "LEFT OUTER JOIN cfinvsrvanx c ON a.Company_Id = c.Company_Id AND a.Branch_Id = c.Branch_Id AND a.Invoice_No = c.Invoice_No AND a.Container_No = c.Process_Trans_Id " +
        "AND a.Profitcentre_Id = c.Profitcentre_Id and c.invoice_type NOT IN ('PD') " +
        "LEFT OUTER JOIN services x ON c.Company_Id = x.Company_Id AND c.service_id = x.service_id AND c.branch_id = x.branch_id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND c.company_id = :companyId AND c.branch_id = :branchId " +
        "AND c.status = 'A' AND  a.status ='A' and a.invoice_type NOT IN ('MISC') AND c.process_id!='P01109' " +
        "AND a.Created_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.Container_No, c.Container_no, a.invoice_no " +
        "UNION " +
        "SELECT cj.Invoice_No, cj.Container_no, cj.ERP_Doc_Ref_No, " +
        "GROUP_CONCAT(CONCAT(bj.service_id, '~', bj.Local_Amt, '~', bj.Tax_Perc_N, '~', bj.invoice_Amt, '~', xj.Service_short_desc, '~', '0', '~', '0', '~', '0', '~', xj.Service_Group)) AS tot_services " +
        "FROM cfinvsrvcn cj " +
        "LEFT OUTER JOIN cfinvsrvcndtl bj ON cj.Company_Id = cj.Company_Id AND cj.Branch_Id = bj.Branch_Id AND cj.invoice_no = bj.invoice_no " +
        "AND bj.profitcentre_id = cj.profitcentre_id AND bj.party_Id = cj.party_id " +
        "LEFT OUTER JOIN services xj ON bj.Company_Id = xj.Company_Id AND bj.service_id = xj.service_id AND bj.branch_id = xj.branch_id " +
        "WHERE cj.company_id = :companyId AND cj.Branch_Id = :branchId AND bj.company_id = :companyId AND bj.Branch_Id = :branchId " +
        "AND cj.status = 'A' AND cj.invoice_type NOT IN ('MISC')  AND bj.status = 'A' AND cj.status = 'A' AND cj.Created_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY cj.Container_no, cj.invoice_no " +
        "ORDER BY 1"
        ,
        nativeQuery = true)
List<Object[]> findInvoiceServices( @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate);


//@Query(value = "SELECT a.Invoice_No, c.Container_no, c.Process_Trans_Id, " +
//        "GROUP_CONCAT(CONCAT(c.service_id, '~', c.Local_Amt, '~', c.Tax_Perc_N, '~', c.invoice_Amt, '~', " +
//        "x.Service_short_desc, '~', c.Execution_Unit, '~', c.Free_Days, '~', c.Chargable_Days, '~', x.Service_Group)) AS tot_services " +
//        "FROM cfinvsrv a " +
//        "LEFT OUTER JOIN cfinvsrvanx c ON a.Company_Id = c.Company_Id " +
//        "    AND a.Branch_Id = c.Branch_Id " +
//        "    AND a.Invoice_No = c.Invoice_No " +
//        "    AND a.Container_No = c.Process_Trans_Id " +
//        "    AND a.Profitcentre_Id = c.Profitcentre_Id " +
//        "LEFT OUTER JOIN services x ON c.Company_Id = x.Company_Id " +
//        "    AND c.service_id = x.service_id " +
//        "    AND c.branch_id = x.branch_id " +
//        "WHERE a.company_id = :companyId " +
//        "  AND a.branch_id = :branchId " +
//        "  AND c.company_id = :companyId " +
//        "  AND c.branch_id = :branchId " +
//        "  AND c.status = 'A' " +
//        "  AND a.Created_Date BETWEEN :startDate AND :endDate " +
//        "GROUP BY a.Container_No, c.Container_no, a.invoice_no " +
//        "UNION ALL " +
//        "SELECT cj.Invoice_No, cj.Container_no, cj.ERP_Doc_Ref_No, " +
//        "GROUP_CONCAT(CONCAT(bj.service_id, '~', bj.Local_Amt, '~', bj.Tax_Perc_N, '~', bj.invoice_Amt, '~', " +
//        "xj.Service_short_desc, '~', '0', '~', '0', '~', '0', '~', xj.Service_Group)) AS tot_services " +
//        "FROM cfinvsrvcn cj " +
//        "LEFT OUTER JOIN cfinvsrvcndtl bj ON cj.Company_Id = bj.Company_Id " + // Corrected join condition
//        "    AND cj.Branch_Id = bj.Branch_Id " +
//        "    AND cj.invoice_no = bj.invoice_no " +
//        "    AND bj.profitcentre_id = cj.profitcentre_id " +
//        "    AND bj.party_Id = cj.party_id " +
//        "LEFT OUTER JOIN services xj ON bj.Company_Id = xj.Company_Id " +
//        "    AND bj.service_id = xj.service_id " +
//        "    AND bj.branch_id = xj.branch_id " +
//        "WHERE cj.company_id = :companyId " +
//        "  AND cj.Branch_Id = :branchId " +
//        "  AND bj.company_id = :companyId " +
//        "  AND bj.Branch_Id = :branchId " +
//        "  AND cj.status = 'A' " +
//        "  AND bj.status = 'A' " +
//        "  AND cj.Created_Date BETWEEN :startDate AND :endDate " +
//        "GROUP BY cj.Container_no, cj.invoice_no " +
//        "ORDER BY 1", nativeQuery = true)
//List<Object[]> findInvoiceServices(
//        @Param("companyId") String companyId,
//        @Param("branchId") String branchId,
//        @Param("startDate") Date startDate,
//        @Param("endDate") Date endDate);


//@Repository
//public interface YourRepository extends JpaRepository<YourEntity, Long> {
//
//    @Query(value = "" +
//            "SELECT  " +
//            "    IF(aj.Profitcentre_Id = 'N00002', aj.Importer_Name, aj.Exporter_Name) AS Importer_Name, " +
//            "    IFNULL(ln.party_name, '') AS line_Name, " +
//            "    IFNULL(pj.party_name, '') AS cha_name, " +
//            "    lj.Party_Name AS BillTo_Party, " +
//            "    IF(aj.Profitcentre_Id = 'N00002', aj.Igm_No, aj.SB_No) AS igm_or_sb_no, " +
//            "    aj.igm_line_no, aj.Container_no, aj.Container_Size, aj.Container_Type, " +
//            "    aj.Cargo_Weight, aj.Gross_Weight, " +
//            "    IFNULL(DATE_FORMAT(aj.Destuff_Date, '%d-%m-%Y %H:%i:%s'), '') AS Destuff_Date, " +
//            "    g.Cargo_Type, " +
//            "    IFNULL(DATE_FORMAT(aj.Gate_In_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_In_Date, " +
//            "    IFNULL(DATE_FORMAT(m.Gate_Out_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_Out_Date, " +
//            "    cm.Jar_Dtl_Desc, g.Yard_Location,  " +
//            "    cj.invoice_no AS invno, " +
//            "    IFNULL(DATE_FORMAT(cj.Invoice_Date, '%d-%m-%Y'), '') AS Invoice_Date, " +
//            "    bj.Local_Amt AS amt, " +
//            "    cj.IGST, cj.CGST, cj.SGST,  " +
//            "    GROUP_CONCAT(CONCAT( " +
//            "        bj.service_id, '~', bj.Local_Amt, '~', bj.Tax_Perc_N, '~', " +
//            "        bj.invoice_Amt, '~', xj.Service_short_desc, '~', '0', '~', " +
//            "        '0', '~', '0', '~', xj.Service_Group " +
//            "    )) AS tot_services,  " +
//            "    bj.service_id, bj.Local_Amt, bj.Tax_Perc_N, bj.invoice_Amt,  " +
//            "    aj.Billing_Party, IFNULL(bl.party_name, '') AS BillTo_Party, " +
//            "    aj.Credit_Type, aj.Area_Used, m.Container_Weight,  " +
//            "    m.Container_Status, m.Gate_Out_Type, aj.Profitcentre_Id,  " +
//            "    aj.Inv_Type, IF(aj.Profitcentre_Id = 'N00002', i.Gate_In_Date, aj.Gate_In_Date) AS empty_Gatein,  " +
//            "    IF(aj.Profitcentre_Id = 'N00002', i.Gate_Out_Date, aj.Gate_Out_Date) AS emptygateout,  " +
//            "    'CREDIT NOTE INVOICE' AS INVOICE_DEBITNOTEFLAG,  " +
//            "    cj.created_Date AS OCD, cj.Container_No  " +
//            "FROM  " +
//            "    cfinvsrvcn cj  " +
//            "LEFT JOIN  " +
//            "    cfassesmentsheet aj ON cj.Company_Id = aj.Company_Id  " +
//            "    AND cj.branch_id = aj.branch_id  " +
//            "    AND (aj.Assesment_Id = cj.Container_No OR aj.Assesment_Id = cj.ERP_Doc_Ref_No)  " +
//            "LEFT JOIN  " +
//            "    cfigmcrg g ON aj.Company_Id = g.Company_Id  " +
//            "    AND aj.Branch_Id = g.Branch_Id  " +
//            "    AND aj.igm_trans_id = g.igm_trans_id  " +
//            "    AND aj.igm_no = g.igm_no  " +
//            "    AND aj.profitcentre_id = g.profitcentre_id   " +
//            "    AND aj.igm_Line_no = g.igm_Line_no  " +
//            "LEFT JOIN  " +
//            "    cfigmcn m ON aj.Company_Id = m.Company_Id  " +
//            "    AND aj.Branch_Id = m.Branch_Id  " +
//            "    AND aj.igm_trans_id = m.igm_trans_id  " +
//            "    AND aj.igm_no = m.igm_no  " +
//            "    AND aj.profitcentre_id = m.profitcentre_id  " +
//            "    AND aj.igm_Line_no = m.igm_Line_no  " +
//            "LEFT JOIN  " +
//            "    cfinvsrvcndtl bj ON cj.Company_Id = cj.Company_Id  " +
//            "    AND cj.Branch_Id = bj.Branch_Id  " +
//            "    AND cj.invoice_no = bj.invoice_no  " +
//            "    AND bj.profitcentre_id = cj.profitcentre_id " +
//            "    AND bj.party_Id = cj.party_id  " +
//            "LEFT JOIN  " +
//            "    party lj ON lj.Company_Id = aj.Company_Id    " +
//            "    AND lj.Party_Id = aj.Party_Id   " +
//            "    AND lj.branch_id = aj.branch_id  " +
//            "LEFT JOIN  " +
//            "    party pj ON aj.Company_Id = pj.Company_Id  " +
//            "    AND aj.CHA = pj.Party_Id     " +
//            "    AND aj.branch_id = pj.branch_id  " +
//            "LEFT JOIN  " +
//            "    services xj ON bj.Company_Id = xj.Company_Id  " +
//            "    AND bj.service_id = xj.service_id   " +
//            "    AND bj.branch_id = xj.branch_id   " +
//            "LEFT JOIN  " +
//            "    party ln ON aj.company_id = ln.company_id  " +
//            "    AND aj.SL = ln.party_id   " +
//            "    AND aj.branch_id = ln.branch_id  " +
//            "LEFT JOIN  " +
//            "    jar_detail cm ON aj.Company_Id = cm.Company_Id  " +
//            "    AND aj.commodity_Code = cm.Jar_Dtl_Id   " +
//            "    AND cm.Jar_Id = 'J00006'   " +
//            "LEFT JOIN  " +
//            "    party bl ON aj.Company_Id = bl.Company_Id  " +
//            "    AND aj.party_id = bl.party_id  " +
//            "    AND aj.branch_id = bl.branch_id    " +
//            "LEFT JOIN  " +
//            "    cfimpinventory i ON aj.Company_Id = i.Company_Id  " +
//            "    AND aj.Branch_Id = i.Branch_Id   " +
//            "    AND aj.Container_no = i.Container_no  " +
//            "    AND aj.IGM_Trans_Id = i.IGM_Trans_Id   " +
//            "WHERE   " +
//            "    cj.company_id = :companyId  " +
//            "    AND cj.Branch_Id = :branchId  " +
//            "    AND bj.company_id = :companyId  " +
//            "    AND bj.Branch_Id = :branchId  " +
//            "    AND aj.company_id = :companyId   " +
//            "    AND aj.Branch_Id = :branchId  " +
//            "    AND aj.status IN ('D', 'A')  " +
//            "    AND aj.Invoice_No != ''  " +
//            "    AND bj.status = 'A'  " +
//            "    AND cj.status = 'A'   " +
//            "    AND cj.Created_Date BETWEEN '2021-11-01 08:00' AND '2025-01-16 11:50'   " +
//            "GROUP BY   " +
//            "    aj.assesment_id, cj.invoice_no   " +
//            "ORDER BY   " +
//            "    OCD ASC " +
//            "", nativeQuery = true)
//    List<Object[]> getCreditNoteInvoiceDetails();
//}

//0.  IF(a.Profitcentre_Id = 'N00002', a.Importer_Name, a.Exporter_Name) AS Importer_Name,
//1.  IFNULL(ln.party_name, '') AS line_Name,
//2.  IFNULL(ch.party_name, '') AS cha_name,
//3.  ac.Party_Name,
//4.  IF(a.Profitcentre_Id = 'N00002', g.igm_no, a.SB_No) AS igm_or_sb_no,
//5.  g.IGM_Line_No,
//6.  a.Container_no,
//7.  a.Container_Size,
//8.  a.Container_Type,
//9.  a.Cargo_Weight,
//10. a.Gross_Weight,
//11. IFNULL(DATE_FORMAT(a.Destuff_Date, '%d-%m-%Y %H:%i:%s'), '') AS Destuff_Date,
//12. g.Cargo_Type,
//13. IFNULL(DATE_FORMAT(a.Gate_In_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_In_Date,
//14. IFNULL(DATE_FORMAT(m.Gate_Out_Date, '%d-%m-%Y %H:%i:%s'), '') AS Gate_Out_Date,
//15. cm.Jar_Dtl_Desc,
//16. g.Yard_Location,
//17. a.Invoice_No,
//18. IFNULL(DATE_FORMAT(b.Invoice_Date, '%d-%m-%Y'), '') AS Invoice_Date,
//19. a.Invoice_Amt,
//20. a.IGST,
//21. a.CGST,
//22. a.SGST,
//23. GROUP_CONCAT(DISTINCT CONCAT(c.service_id, '~', c.Local_Amt, '~', c.Tax_Perc_N, '~', c.invoice_Amt, '~', x.Service_short_desc, '~', c.Execution_Unit, '~', c.Free_Days, '~', c.Chargable_Days, '~', x.Service_Group)) AS tot_services,
//24. c.service_id,
//25. c.Local_Amt,
//26. c.Tax_Perc_N,
//27. c.invoice_Amt,
//28. a.Billing_Party,
//29. IFNULL(bl.party_name, '') AS BillTo_Party,
//30. a.Credit_Type,
//31. a.Area_Used,
//32. m.Container_Weight,
//33. m.Container_Status,
//34. m.Gate_Out_Type,
//35. a.Profitcentre_Id,
//36. a.Inv_Type,
//37. IF(a.Profitcentre_Id = 'N00002', i.Gate_In_Date, a.Gate_In_Date) AS empty_Gatein,
//38. IF(a.Profitcentre_Id = 'N00002', i.Gate_Out_Date, a.Gate_Out_Date) AS emptygateout,
//39. 'INVOICE' AS INVOICE_DEBITNOTEFLAG,
//40. b.created_Date AS OCD,
//41. a.Assesment_Id






@Query(value = 
"SELECT pp.Party_Name AS partyName, " +
"       a.Invoice_No AS invoiceNo, " +
"       DATE_FORMAT(a.Created_Date, '%d/%b/%Y %H:%i') AS createdDate, " +
"       a.Invoice_Amt AS invoiceAmount, " +
"       a.Comments AS comments, " +
"       b.Container_No AS containerNo, " +
"       b.Container_Size AS containerSize, " +
"       a.Invoice_Amt AS partyAmount " +
"FROM cfinvsrv a " +
"LEFT OUTER JOIN cfassesmentsheet b " +
"    ON a.Company_Id = b.Company_Id " +
"    AND a.Branch_Id = b.Branch_Id " +
"    AND a.Profitcentre_Id = b.Profitcentre_Id " +
"    AND a.Invoice_No = b.Invoice_No " +
"    AND a.Container_no = b.Assesment_Id " +
"LEFT OUTER JOIN cfigmcn c " +
"    ON b.Company_Id = c.Company_Id " +
"    AND b.Branch_Id = c.Branch_Id " +
"    AND b.IGM_Trans_Id = c.IGM_Trans_Id " +
"    AND b.Igm_No = c.Igm_No " +
"    AND b.igm_line_no = c.IGM_Line_No " +
"    AND b.Container_No = c.Container_no " +
"LEFT OUTER JOIN party pp " +
"    ON b.Company_Id = pp.Company_Id " +
"    AND b.SL = pp.Party_Id " +
"    AND b.branch_id = pp.branch_id " +
"WHERE a.company_id = :companyId " +
"  AND a.branch_id = :branchId " +
"  AND a.Profitcentre_Id = 'N00002' " +
"  AND c.Container_Status = 'FCL' " +
"  AND a.Created_Date BETWEEN :startDate AND :endDate " +
"  AND a.Status = 'A' " +
" AND (:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty)  " +
"GROUP BY a.Invoice_No " +
"ORDER BY a.Created_Date", 
nativeQuery = true)
List<Object[]> findReceiptFCLReportDataOnly(
@Param("companyId") String companyId, 
@Param("branchId") String branchId, 
@Param("startDate") Date startDate, 
@Param("endDate") Date endDate,
@Param ("billParty") String billParty);


@Query(value = "SELECT pp.Party_Name,b.Container_No, b.Container_Size, a.Invoice_No, DATE_FORMAT(a.Created_Date, '%d-%b-%Y %T'), a.Comments,  " +
        "a.Invoice_Amt  " +
        "FROM cfinvsrv a " +
        "LEFT OUTER JOIN cfassesmentsheet b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id AND a.Invoice_No = b.Invoice_No AND a.Container_no = b.Assesment_Id " +
        "LEFT OUTER JOIN cfigmcn c ON b.Company_Id = c.Company_Id AND b.Branch_Id = c.Branch_Id AND b.IGM_Trans_Id = c.IGM_Trans_Id " +
        "AND b.Igm_No = c.Igm_No AND b.igm_line_no = c.IGM_Line_No AND b.Container_No = c.Container_no " +
        "LEFT OUTER JOIN party pp ON b.Company_Id = pp.Company_Id AND b.SL = pp.Party_Id AND b.branch_id = pp.branch_id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND a.Profitcentre_Id = 'N00002' " +
        "AND c.Container_Status = 'FCL' AND a.Created_Date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' " +
        " AND (:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty)  " +
        "GROUP BY c.IGM_Trans_Id, c.Container_No " +
        "ORDER BY a.Created_Date", 
nativeQuery = true)
List<Object[]> findInvoiceDetailsFCLTeusOnly(@Param("companyId") String companyId, 
		@Param("branchId") String branchId, 
		@Param("startDate") Date startDate, 
		@Param("endDate") Date endDate,
		@Param ("billParty") String billParty);







@Query(value = "SELECT pp.Party_Name, a.Invoice_No, " +
        "DATE_FORMAT(a.Created_Date, '%d/%b/%Y %H:%i') AS createdDate, " +
        "a.Invoice_Amt, a.Comments, b.Container_No, b.Container_Size, " +
        "a.Invoice_Amt AS Prtyamt " +
        "FROM cfinvsrv a " +
        "LEFT OUTER JOIN cfassesmentsheet b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "AND a.Invoice_No = b.Invoice_No AND a.Container_no = b.Assesment_Id " +
        "LEFT OUTER JOIN cfexpmovementreq c ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id " +
        "AND b.SB_No = c.SB_No AND b.Movement_Req_Id = c.Movement_Req_Id " +
        "AND b.Container_No = c.Container_no " +
        "LEFT OUTER JOIN party pp ON b.Company_Id = pp.Company_Id " +
        "AND b.SL = pp.Party_Id AND b.branch_id = pp.branch_id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId " +
        "AND a.Profitcentre_Id = 'N00004' AND c.Mov_Req_Type IN ('CLP') " +
        "AND a.Created_Date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' " +
        " AND (:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty)  " +
        "GROUP BY a.Invoice_No, c.Movement_Req_Id, c.Container_No " +
        "ORDER BY a.Created_Date", nativeQuery = true)
List<Object[]>  findExportCLPInvoiceReceiptOnly(@Param("companyId") String companyId,
                                           @Param("branchId") String branchId,
                                           @Param("startDate") Date startDate,
                                           @Param("endDate") Date endDate,
                                           @Param("billParty") String billParty);

@Query(value = "SELECT pp.Party_Name, b.Container_No, b.Container_Size, a.Invoice_No, " +
        "DATE_FORMAT(a.Created_Date, '%d-%b-%Y %H:%i') AS createdDate, a.Comments, a.Invoice_Amt " +
        "FROM cfinvsrv a " +
        "LEFT OUTER JOIN cfassesmentsheet b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id AND a.Invoice_No = b.Invoice_No AND a.Container_no = b.Assesment_Id " +
        "LEFT OUTER JOIN cfexpmovementreq c ON b.Company_Id = c.Company_Id AND b.Branch_Id = c.Branch_Id " +
        "AND b.SB_No = c.SB_No AND b.Movement_Req_Id = c.Movement_Req_Id " +
        "AND b.Container_No = c.Container_no " +
        "LEFT OUTER JOIN party pp ON b.Company_Id = pp.Company_Id AND b.SL = pp.Party_Id " +
        "AND b.branch_id = pp.branch_id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId " +
        "AND a.Profitcentre_Id = 'N00004' AND c.Mov_Req_Type IN ('CLP') " +
        "AND a.Created_Date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' " +
        "AND (:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty) " +
        "GROUP BY a.Invoice_No, c.Movement_Req_Id, c.Container_No " +
        "ORDER BY a.Created_Date", nativeQuery = true)
List<Object[]> findExportCLPTeusReportOnly(@Param("companyId") String companyId,
                                               @Param("branchId") String branchId,
                                               @Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate,
                                               @Param("billParty") String billParty);





@Query(value = 
"SELECT " +
"b.Invoice_No, " +
"ta.tan_no_id, " +
"DATE_FORMAT(b.Invoice_Date, '%d-%b-%Y') AS Invoice_Date, " +
"pb.Party_Name AS Billing_Party_Name, " +
"p.Party_Name AS CHA_Name, " +
"b.Importer_Name, " +
"IFNULL(b.Bill_Amt, '0.00') AS Bill_Amount, " +
"IFNULL((b.Invoice_Amt - b.Bill_Amt), '0.00') AS ServiceTax, " +
"IFNULL(b.Invoice_Amt, '0.00') AS Invoice_Amount, " +
"GROUP_CONCAT(DISTINCT a.Payment_Mode, '~', IFNULL(a.Document_Amt, '0.00'), '~', a.Line_Id) AS Payment_Details, " +
"'' AS Extra_Column " +
"FROM fintrans a " +
"LEFT OUTER JOIN cfassesmentsheet b " +
"ON b.Company_Id = a.Company_Id " +
"AND b.Branch_Id = a.Branch_Id " +
"AND b.Receipt_No = a.Trans_Id " +
"AND b.Assesment_Id = a.Assesment_Id " +
"LEFT OUTER JOIN party p " +
"ON b.Company_Id = p.Company_Id " +
"AND b.CHA = p.Party_Id " +
"AND b.Branch_Id = p.Branch_Id " +
"LEFT OUTER JOIN party pb " +
"ON pb.Company_Id = a.Company_Id " +
"AND pb.Party_Id = a.Party_Id " +
"AND pb.Branch_Id = a.Branch_Id " +
"LEFT OUTER JOIN party ta " +
"ON ta.Company_Id = a.Company_Id " +
"AND ta.Party_Id = a.Party_Id " +
"AND ta.Branch_Id = a.Branch_Id " +
"WHERE " +
"a.Company_Id = :companyId " +
"AND a.Branch_Id = :branchId " +
"AND a.Status = 'A' " +
"AND b.Invoice_Date BETWEEN :startDate AND :endDate " +
"AND (:billParty IS NULL OR :billParty = '' OR a.party_id = :billParty) " +
"AND b.Invoice_No != '' " +
"GROUP BY a.Trans_Id", nativeQuery = true)
List<Object[]> findFinanceTransactions(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate,
    @Param("billParty") String billParty);


}
