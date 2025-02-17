package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfigmcn;

public interface CommonReportsRepo extends JpaRepository<Cfigmcn, String>{
	
	@Query(value = "SELECT DISTINCT b.Container_No, b.Container_Size " +
            "FROM cfigm a " +
            "LEFT OUTER JOIN cfigmcn b ON b.Company_Id = a.Company_Id " +
            "AND b.Branch_Id = a.Branch_Id " +
            "AND b.IGM_Trans_Id = a.IGM_Trans_Id " +
            "AND b.Profitcentre_Id = a.Profitcentre_Id " +
            "AND b.IGM_No = a.IGM_No " +
            "WHERE a.Company_Id = :companyId " +
            "AND a.Branch_Id = :branchId " +
            "AND a.Status = 'A' " +
            "AND a.profitcentre_id IN ('N00002') " +
            "AND a.created_date BETWEEN :startDate AND :endDate", nativeQuery = true)
List<Object[]> findDistinctContainersJoGateIn(@Param("companyId") String companyId,
                                        @Param("branchId") String branchId,
                                        @Param("startDate") Date startDate,
                                        @Param("endDate") Date endDate);






@Query(value = "SELECT DISTINCT a.Container_No, a.Container_Size " +
        "FROM cfigmcn a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Status = 'A' " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND a.Seal_Cut_Trans_Id = 'A' " +
        "AND a.Seal_Cut_Req_Date BETWEEN :startDate AND :endDate", 
nativeQuery = true)
List<Object[]> findDistinctContainersJoGateInForSealCuttting(@Param("companyId") String companyId,
                                       @Param("branchId") String branchId,
                                       @Param("startDate") Date startDate,
                                       @Param("endDate") Date endDate);

@Query(value = "SELECT DISTINCT a.container_no, a.container_size, " +
        "DATE_FORMAT(a.in_gate_in_date, '%d %b %Y %T') AS gateindate " +
        "FROM cfgatein a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND a.Status = 'A' " +
        "AND a.Process_Id = 'P00203' " +
        "AND a.Container_No <> '' " +
        "AND a.in_gate_in_date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.Gate_In_Id, a.Container_No " +
        "UNION " +
        "SELECT DISTINCT a.container_no, a.container_size, " +
        "DATE_FORMAT(a.Gate_In_Date, '%d %b %Y %T') AS gateindate " +
        "FROM cfmanualgatein a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND a.Status = 'A' " +
        "AND a.Process_Id = 'P00212' " +
        "AND a.Gate_In_Type = 'IMP' " +
        "AND a.Container_No <> '' " +
        "AND a.gate_in_date BETWEEN :startDate AND :endDate", nativeQuery = true)
List<Object[]> findDistinctGateInData(@Param("companyId") String companyId,
                               @Param("branchId") String branchId,
                               @Param("startDate") Date startDate,
                               @Param("endDate") Date endDate);




@Query(value = "SELECT a.Container_Size " +
        "FROM cfigmcn a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Container_Status = 'FCL' " +
        "AND a.Seal_Cut_Trans_Id = 'A' " +
        "AND a.Container_Exam_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.Container_No", nativeQuery = true)
List<Object[]> findDistinctContainersForImportCustomExam(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("startDate") Date startDate,
@Param("endDate") Date endDate
);




@Query(value = "SELECT DISTINCT b.container_no, b.container_size, b.container_type, b.ODC_Status, b.Type_of_container, 'N' " +
        "FROM cfigmcn b " +
        "LEFT OUTER JOIN cfigmcrg f ON f.Company_Id = b.Company_Id " +
        "AND f.Branch_Id = b.Branch_Id " +
        "AND f.IGM_Trans_Id = b.IGM_Trans_Id " +
        "AND f.Profitcentre_Id = b.Profitcentre_Id " +
        "AND f.IGM_No = b.IGM_No " +
        "AND f.igm_line_no = b.igm_line_no " +
        "WHERE b.Company_Id = :companyId " +
        "AND b.Branch_Id = :branchId " +
        "AND b.status = 'A' " +
        "AND (b.gate_out_id = '' OR b.gate_out_date > :endDate) " +
        "AND (b.de_stuff_id = '' OR b.de_stuff_Date > :endDate) " +
        "AND b.gate_in_id != '' " +
        "AND b.Gate_in_Date < :endDate " +
        "GROUP BY b.container_no, b.igm_no " +
        "UNION " +
        "SELECT DISTINCT a.container_no, a.container_size, a.container_type, a.ODC_Status, 'Manual', a.Hazardous " +
        "FROM cfgatein a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Process_Id = 'P00212' " +
        "AND a.status = 'A'", nativeQuery = true)
List<Object[]> getLoadedInventoryData(@Param("companyId") String companyId,
                                          @Param("branchId") String branchId,
                                          @Param("endDate") Date endDate);

@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.Container_Type " +
        "FROM cfimpinventory a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND (a.Empty_Out_Id = '' OR a.Empty_Out_Id IS NULL OR a.Empty_Out_Date > :endDate) " +
        "AND (a.De_Stuff_Date != '0000-00-00 00:00:00' AND a.De_Stuff_Date < :endDate) " +
        "AND a.status = 'A' " +
        "GROUP BY a.container_no", 
nativeQuery = true)
List<Object[]> getInventoryETYReport(@Param("companyId") String companyId, 
                              @Param("branchId") String branchId, 
                              @Param("endDate") Date endDate);


@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
        "FROM cfexpinventory a " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND (a.stuff_req_id = '' OR stuff_req_id IS NULL OR  a.stuff_req_date > :endDate) " +
        "AND (a.empty_pass_id = '' OR a.empty_pass_id IS NULL ) " +
        "AND a.gate_in_date < :endDate " +
        "AND a.status = 'A' " +
        "AND a.container_status = 'MTY' " +
        "GROUP BY a.container_no", 
nativeQuery = true)
List<Object[]> getInventoryExportReport(@Param("companyId") String companyId, 
        @Param("branchId") String branchId, 
        @Param("endDate") Date endDate);








@Query(value = "SELECT DISTINCT c.container_no, c.container_size, c.container_type " +
        "FROM cfigmcn c " +
        "WHERE c.company_id = :companyId " +
        "AND c.branch_id = :branchId " +
        "AND c.destuff_wo_trans_id != '' " +
        "AND c.status = 'A' " +
        "AND c.destuff_wo_date BETWEEN :startDate AND :endDate " +
        "GROUP BY c.container_no, c.igm_no", nativeQuery = true)
List<Object[]> findLoadingJO(@Param("companyId") String companyId,
                               @Param("branchId") String branchId,
                               @Param("startDate") Date startDate,
                               @Param("endDate") Date endDate);






@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
        "FROM cfigmcn a " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND a.status = 'A' " +
        "AND a.container_status = 'FCL' " +
        "AND a.de_stuff_date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.container_no, a.igm_no", 
nativeQuery = true)
List<Object[]> findDistinctFCLContainers(@Param("companyId") String companyId,
                                  @Param("branchId") String branchId,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);




@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfigmcn b ON a.erp_doc_ref_no = b.igm_trans_id " +
        "AND a.doc_ref_no = b.igm_no " +
        "AND a.container_no = b.container_no " +
        "AND a.profitcentre_id = b.profitcentre_id " +
        "AND a.gate_out_id = b.gate_out_id " +
        "AND a.company_id = b.company_id " +
        "AND a.branch_id = b.branch_id " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND a.container_status = 'FCL' " +
        "AND a.container_no != '' " +
        "AND a.status = 'A' " +
        "AND b.status = 'A' " +
        "AND a.gate_out_date BETWEEN :startDate AND :endDate", 
nativeQuery = true)
List<Object[]> findDistinctFCLGateOutContainers(@Param("companyId") String companyId,
                                      @Param("branchId") String branchId,
                                      @Param("startDate") Date startDate,
                                      @Param("endDate") Date endDate);





@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
        "FROM cfigmcn a " +
        "LEFT OUTER JOIN cfimportgatepass b ON a.company_id = b.company_id " +
        "AND a.branch_id = b.branch_id " +
        "AND a.gate_pass_no = b.gate_pass_id " +
        "AND a.gate_out_id = b.gate_out_id " +
        "AND b.container_no = a.container_no " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND a.container_status = 'LCL' " +
        "AND a.status = 'A' " +
        "AND a.de_stuff_date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.container_no, a.igm_no", 
nativeQuery = true)
List<Object[]> findDistinctLCLContainers(@Param("companyId") String companyId,
                                  @Param("branchId") String branchId,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);








@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfcommongatepass b ON a.gate_pass_no = b.gate_pass_id " +
        "AND a.container_no = b.container_no " +
        "AND a.company_id = b.company_id " +
        "AND a.branch_id = b.branch_id " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND a.status = 'A' " +
//        "AND a.profitcentre_id IN ('N00002') " +
"  AND a.Profitcentre_Id IN ('N00004','N00002') " +
        "AND a.gate_out_date BETWEEN :startDate AND :endDate " +
        "AND a.container_status = 'MTY'", 
nativeQuery = true)
List<Object[]> findDistinctMTYContainers(@Param("companyId") String companyId,
                                  @Param("branchId") String branchId,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);










@Query(value = "SELECT DISTINCT a.container_no, a.container_size AS size, a.be_no, " +
        "DATE_FORMAT(a.be_date, '%d/%b/%Y %H:%i'), " +
        "e.vehicle_no, e.qty_taken_out, e.gw_taken_out, a.gate_out_id " +
        "FROM cfigmcn a " +
        "LEFT OUTER JOIN cfimportgatepass e ON a.company_id = e.company_id " +
        "AND a.branch_id = e.branch_id " +
        "AND a.igm_no = e.igm_no " +
        "AND a.igm_line_no = e.igm_line_no " +
        "AND a.igm_trans_id = e.igm_trans_id " +
        "AND a.profitcentre_id = e.profitcentre_id " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND a.status = 'A' " +
        "AND a.gate_out_date BETWEEN :startDate AND :endDate " +
        "AND a.container_status = 'LCL' " +
        "AND a.de_stuff_id != '' " +
        "AND a.status = 'A' " +
        "GROUP BY a.igm_trans_id, a.igm_no, a.igm_line_no, a.container_no " +
        "ORDER BY a.de_stuff_date", 
nativeQuery = true)
List<Object[]> findLCLContainerDetailsWithGateOutId(@Param("companyId") String companyId,
                                            @Param("branchId") String branchId,
                                            @Param("startDate") Date startDate,
                                            @Param("endDate") Date endDate);



@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
        "FROM cfgatein a " +
        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        "AND a.status = 'A' AND a.Profitcentre_Id = 'N00004' " +
        "AND a.Gate_In_Type = 'EXP' "
        + "AND a.Process_Id = 'P00219' " +
        "AND a.in_Gate_In_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.container_no", 
nativeQuery = true)
List<Object[]> findExportEMptyContainerGateIn(@Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate);





@Query(value = "SELECT DISTINCT a.Container_No, a.Container_Size, a.Container_Type, a.Gate_In_Type " +
        "FROM cfgatein a " +
        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        "AND a.status = 'A' AND a.Profitcentre_Id = 'N00004' " +
//        "AND a.Gate_In_Type IN ('Buffer', 'ONWH', 'FDS') " +
"AND a.Gate_In_Type IN ('Buffer') " +
        "AND a.Process_Id = 'P00234' " +
        "AND a.in_Gate_In_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.Container_No " +
        "ORDER BY a.in_Gate_In_Date ASC", 
nativeQuery = true)
List<Object[]> exportBufferGateIn(@Param("companyId") String companyId,
                             @Param("branchId") String branchId,
                             @Param("startDate") Date startDate,
                             @Param("endDate") Date endDate);




@Query(value = "SELECT DISTINCT DATE_FORMAT(a.Gate_Out_Date, '%d %b %Y %T') AS gateOutDate, " +
        "a.vehicle_no, a.container_no, a.container_size, a.container_type, a.trans_type " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfexpmovementreq c ON a.company_id = c.company_id " +
        "AND a.branch_id = c.branch_id AND a.gate_out_id = c.gate_out_id " +
        "AND c.container_no = a.container_no " +
        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        "AND a.Status = 'A' AND a.gate_out_date BETWEEN :startDate AND :endDate " +
        "AND a.trans_type IN ('CONT', 'MOVE', 'BOWC') " +
        "AND a.Profitcentre_Id = 'N00004' " +
        "GROUP BY a.container_no, a.gate_out_id", 
nativeQuery = true)
List<Object[]> exportEmptyMovementOut(@Param("companyId") String companyId,
                              @Param("branchId") String branchId,
                              @Param("startDate") Date startDate,
                              @Param("endDate") Date endDate);


@Query(value = "SELECT DISTINCT a.Container_no, a.container_size " +
        "FROM cfstufftally a " +
        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        "AND a.Status = 'A' AND a.Profitcentre_Id = 'N00004' " +
        "AND a.stuff_tally_date BETWEEN :startDate AND :endDate " +
        "AND a.MOVEMENT_TYPE = 'CLP' " +
        "GROUP BY a.Container_no", 
nativeQuery = true)
List<Object[]> findStuffTallyContainers(@Param("companyId") String companyId,
                                 @Param("branchId") String branchId,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);






@Query(value = "SELECT DISTINCT a.Container_No, a.Container_Size, a.Container_Type, a.Gate_In_Type " +
        "FROM cfgatein a " +
        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        "AND a.status = 'A' AND a.Profitcentre_Id = 'N00004' " +
        "AND a.Gate_In_Type IN ('ONWH', 'FDS') " +
        "AND a.Process_Id = 'P00234' " +
        "AND a.in_Gate_In_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.Container_No " +
        "ORDER BY a.in_Gate_In_Date ASC", 
nativeQuery = true)
List<Object[]> exportFcl(@Param("companyId") String companyId,
                             @Param("branchId") String branchId,
                             @Param("startDate") Date startDate,
                             @Param("endDate") Date endDate);



@Query(value = "(SELECT DISTINCT a.container_no, a.container_size, a.Container_Type " +
        " FROM cfexpinventory a " +
        " WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        " AND a.Status = 'A' " +
//        " AND (a.Gate_Out_Id = '' OR a.Gate_Out_Date > :endDate) " +
//        " AND (a.Stuff_Req_Date != '0000-00-00 00:00:00' AND a.Stuff_Req_Date < :endDate) " +
" AND (a.Gate_Out_Id = '' OR a.Gate_Out_Id IS NULL OR a.Gate_Out_Date > :endDate) " +
" AND (a.Stuff_Req_id != '' OR a.Stuff_Req_id IS NOT NULL AND a.Stuff_Req_Date < :endDate) " +
        " AND a.Container_Status = 'MTY' " +
        " AND a.cycle != 'Hub' " +
        " GROUP BY a.container_no) " +
        "UNION " +
        "(SELECT DISTINCT a.container_no, a.container_size, a.Container_Type " +
        " FROM cfexpinventory a " +
        " WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        " AND a.Status = 'A' " +
        " AND (a.Gate_Out_Id = '' OR a.Gate_Out_Id IS NULL OR  a.Gate_Out_Date > :endDate) " +
        " AND a.Container_Status IN ('LDD', 'FCL') " +
        " GROUP BY a.container_no)", 
nativeQuery = true)
List<Object[]> findExpLDDInventoryContainers(@Param("companyId") String companyId,
                                   @Param("branchId") String branchId,
                                   @Param("endDate") Date endDate);






@Query(value = "SELECT a.Port_Code, a.Port_Name " +
        "FROM port a " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND a.status = 'A' " +
        "AND a.Job_Order_Format != ''", 
        nativeQuery = true)
List<Object[]> findPorts(@Param("companyId") String companyId, 
                         @Param("branchId") String branchId);









@Query(value = "SELECT DISTINCT c.Port, a.container_no, a.container_size, " +
        "IFNULL(DATEDIFF( :endDate, c.Doc_Date), ''), " +
        "a.Container_Type, a.container_status, a.igm_no, a.scanner_type, v.vessel_name, " +
        "p.party_name, c.port, DATE_FORMAT(c.port_jo_date, '%d %b %Y'), " +
        "a.container_seal_no, a.type_of_container, q.party_name, a.profitcentre_id " +
        "FROM cfigmcn a " +
        "LEFT OUTER JOIN cfigm c ON a.company_id = c.company_id AND a.branch_id = c.branch_id " +
        "AND a.profitcentre_id = c.profitcentre_id AND a.igm_trans_id = c.igm_trans_id " +
        "AND a.igm_no = c.igm_no " +
        "LEFT OUTER JOIN vessel v ON c.company_id = v.company_id AND c.branch_id = a.branch_id " +
        "AND c.vessel_id = v.vessel_id " +
        "LEFT OUTER JOIN party p ON c.company_id = p.company_id AND c.branch_id = p.branch_id " +
        "AND c.shipping_agent = p.party_id " +
        "LEFT OUTER JOIN party q ON c.company_id = q.company_id AND c.branch_id = q.branch_id " +
        "AND c.party_id = q.party_id " +
        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
        "AND c.port_jo != '' " +
        "AND (a.gate_in_id = '' OR a.gate_in_date > :endDate) " +
//"AND (a.gate_in_id = '' OR a.gate_in_date < :endDate) " +
        "AND c.Doc_date < :endDate " +
        "AND a.status = 'A' AND c.status = 'A' AND c.shipping_line != '' " +
        "AND c.port = :port " +
        "GROUP BY a.container_no", 
        nativeQuery = true)
List<Object[]> findPortContainerDetails(@Param("companyId") String companyId,
                                    @Param("branchId") String branchId,
                                    @Param("endDate") Date endDate,
                                    @Param("port") String port);











    @Query(value = "SELECT DISTINCT c.container_no, " +
                   "c.container_size, " +
                   "c.igm_no, " +
                   "c.igm_line_no, " +
                   "DATE_FORMAT(c.Container_Exam_Date, '%d %b %Y %T') AS exam_date, " +
                   "c.examined_packages, " +
                   "e.Actual_No_Of_Packages, " +
                   "p.Party_Name, " +
                   "c.Container_Exam_Remarks, " +
                   "c.Gate_In_Date, " +
                   "m.party_Name, " +
                   "c.Container_Exam_WO_Trans_Date, " +
                   "c.Examined_Packages, " +
                   "j.Jar_Desc " +
                   "FROM cfigmcn c " +
                   "LEFT OUTER JOIN cfigm d " +
                   "ON c.Company_Id = d.Company_Id " +
                   "AND d.branch_id = c.branch_id " +
                   "AND d.igm_trans_id = c.igm_trans_id " +
                   "AND c.igm_no = d.igm_no " +
                   "AND c.Profitcentre_Id = 'N00002' " +
                   "LEFT OUTER JOIN cfigmcrg e " +
                   "ON e.Company_Id = c.Company_Id " +
                   "AND c.branch_id = e.branch_id " +
                   "AND c.igm_trans_id = e.igm_trans_id " +
                   "AND e.igm_no = c.igm_no " +
                   "AND e.igm_line_no = c.igm_line_no " +
                   "AND e.Profitcentre_Id = 'N00002' " +
                   "LEFT OUTER JOIN party p " +
                   "ON c.cha = p.party_id " +
                   "AND c.Company_Id = p.Company_Id " +
                   "AND c.branch_id = p.branch_id " +
                   "LEFT OUTER JOIN party m " +
                   "ON d.shipping_line = m.party_id " +
                   "AND d.Company_Id = m.Company_Id " +
                   "AND d.branch_id = m.branch_id " +
                   "LEFT OUTER JOIN jar j " +
                   "ON j.Company_Id = c.Company_Id " +
                   "WHERE c.Company_Id = :companyId " +
                   "AND c.Branch_Id = :branchId " +
                   "AND c.Profitcentre_Id = 'N00002' " +
                   "AND d.Company_Id = :companyId " +
                   "AND d.Branch_Id = :branchId " +
                   "AND d.Profitcentre_Id = 'N00002' " +
                   "AND e.Company_Id = :companyId " +
                   "AND e.Branch_Id = :branchId " +
                   "AND e.Profitcentre_Id = 'N00002' " +
                   "AND c.container_status = 'FCL' " +
                   "AND c.Status = 'A' " +
                   "AND d.Status = 'A' " +
                   "AND c.Seal_Cut_Trans_Id = 'A' " +
                   "AND c.Container_Exam_Date BETWEEN :startDate AND :endDate " +
                   "GROUP BY c.container_no",
           nativeQuery = true)
    List<Object[]> getIMPORTCUSTOMEXAMReport(
            @Param("companyId") String companyId,
            @Param("branchId") String branchId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );
    
    
    
    @Query(value = "" +
            "SELECT DISTINCT b.Container_No, " +
            "                b.Container_Size, " +
            "                b.Container_Type, " +
            "                z.Vessel_Name, " +
            "                b.Gate_Out_Type, " +
            "                c.IGM_No, " +
            "                a.IGM_Line_No, " +
            "                q.Party_Name, " +
            "                y.Party_Name, " +
            "                a.commodity_description " +
            "FROM cfigmcrg a " +
            "LEFT OUTER JOIN cfigmcn b " +
            "    ON a.company_id = b.company_id " +
            "   AND a.branch_id = b.branch_id " +
            "   AND a.IGM_Trans_Id = b.IGM_Trans_Id " +
            "   AND a.IGM_No = b.IGM_No " +
            "   AND a.IGM_Line_No = b.IGM_Line_No " +
            "   AND a.profitcentre_id = b.profitcentre_id " +
            "LEFT OUTER JOIN cfigm c " +
            "    ON c.company_id = b.company_id " +
            "   AND c.branch_id = b.branch_id " +
            "   AND a.IGM_Trans_Id = c.IGM_Trans_Id " +
            "   AND a.IGM_No = c.IGM_No " +
            "   AND a.profitcentre_id = c.profitcentre_id " +
            "LEFT OUTER JOIN party q " +
            "    ON c.company_id = q.company_id " +
            "   AND c.branch_id = q.branch_id " +
            "   AND b.cha = q.Party_Id " +
            "LEFT OUTER JOIN party m " +
            "    ON c.company_id = m.company_id " +
            "   AND c.branch_id = m.branch_id " +
            "   AND c.Shipping_Agent = m.Party_Id " +
            "LEFT OUTER JOIN vessel z " +
            "    ON c.company_id = z.company_id " +
            "   AND c.branch_id = z.branch_id " +
            "   AND z.Vessel_Id = c.Vessel_Id " +
            "LEFT OUTER JOIN party y " +
            "    ON c.company_id = y.company_id " +
            "   AND c.branch_id = y.branch_id " +
            "   AND c.Shipping_Agent = y.Party_Id " +
            "WHERE a.Company_Id = :companyId " +
            "  AND a.branch_Id = :branchId " +
            "  AND a.Status = 'A' " +
            "AND c.Profitcentre_Id = 'N00002' " +
            "  AND c.Created_Date BETWEEN :startDate AND :endDate " +
            "GROUP BY b.container_no " +
            "ORDER BY a.bl_no " +
            "", nativeQuery = true)
        List<Object[]> jobOrederReport(
                @Param("companyId") String companyId,
                @Param("branchId") String branchId,
                @Param("startDate") Date startDate,
                @Param("endDate") Date endDate
        );


        
        
        
        
        
        
        @Query(value = "SELECT DISTINCT " + 
                "a.container_no, " + 
                "a.container_size, " + 
                "a.Container_Type, " + 
                "a.ISO, " + 
                "s.CUSTOMER_CODE AS s_customer_code, " + 
                "w.CUSTOMER_CODE AS w_customer_code, " + 
                "DATE_FORMAT(a.De_Stuff_Date, '%d/%b/%Y %H:%i') AS de_stuff_date, " + 
                "w.party_name AS w_party_name, " + 
                "s.party_name AS s_party_name " + 
                "FROM cfimpinventory a " + 
                "LEFT OUTER JOIN party s " + 
                "ON a.Company_Id = s.Company_Id " + 
                "AND a.branch_id = s.branch_id " + 
                "AND a.Sl = s.Party_Id " + 
                "LEFT OUTER JOIN party w " + 
                "ON a.Company_Id = w.Company_Id " + 
                "AND a.branch_id = w.branch_id " + 
                "AND a.SA = w.Party_Id " + 
                "WHERE a.company_id = :companyId " + 
                "AND a.branch_id = :branchId " + 
                "AND (a.Empty_Out_Id = '' OR a.Empty_Out_Id IS NULL OR a.Empty_Out_Date > :endDate) " + 
                "AND (a.De_Stuff_Date != '0000-00-00 00:00:00' " + 
                "AND a.De_Stuff_Date < :endDate) " + 
                "AND a.status = 'A' " + 
                "GROUP BY a.container_no", nativeQuery = true)
        List<Object[]> getImportEmptyInventory(
                @Param("companyId") String companyId,
                @Param("branchId") String branchId,
                @Param("endDate") Date endDate
        );
        
        
        
        
        
        @Query(value = 
                "SELECT DISTINCT " +
                "a.container_no, " +
                "a.container_size, " +
                "a.Container_Type, " +
                "'MTY' AS container_status, " +
                "DATE_FORMAT(a.Gate_in_Date, '%d %b %Y %T') AS gate_in_date, " +
                "a.Block_Cell_No, " +
                "w.Party_Name AS w_party_name, " +
                "x.Party_Name AS x_party_name, " +
                "b.Booking_No AS booking_no, " +
                "a.Container_Seal_No, " +
                "b.Transporter_Name, " +
                "b.Vehicle_No, " +
                "b.Comments, " +
                "g.Party_Name AS g_party_name, " +
                "DATE_FORMAT(a.Stuff_Req_Date, '%d %b %Y %T') AS stuff_req_date, " +
                "DATE_FORMAT(a.Empty_Pass_Date, '%d %b %Y %T') AS empty_pass_date, " +
                "w.CUSTOMER_CODE AS w_customer_code, " +
                "b.origin " +
                "FROM cfexpinventory a " +
                "LEFT OUTER JOIN cfgatein b " +
                "ON a.Company_Id = b.Company_Id " +
                "AND a.Branch_Id = b.Branch_Id " +
                "AND a.Gate_In_Id = b.Gate_In_Id " +
                "AND a.Container_No = b.Container_No " +
                "LEFT OUTER JOIN party w " +
                "ON a.Company_Id = w.Company_Id " +
                "AND a.branch_id = w.branch_id " +
                "AND a.SL = w.Party_Id " +
                "LEFT OUTER JOIN party x " +
                "ON b.Company_Id = x.Company_Id " +
                "AND b.branch_id = x.branch_id " +
                "AND b.On_Account_Of = x.Party_Id  " +
                "LEFT OUTER JOIN party g " +
                "ON a.Company_Id = g.Company_Id " +
                "AND a.branch_id = g.branch_id " +
                "AND a.sa = g.Party_Id    " +
                "WHERE a.company_id = :companyId " +
                "AND a.branch_id = :branchId " +
//                "AND (a.Stuff_req_Id = '' OR a.Stuff_Req_Date > :endDate) " +
//                "AND a.Empty_Pass_Id = '' " +
"AND (a.stuff_req_id = '' OR stuff_req_id IS NULL OR  a.stuff_req_date > :endDate) " +
"AND (a.empty_pass_id = '' OR a.empty_pass_id IS NULL ) " +
                "AND a.Gate_In_Date < :endDate " +
                "AND a.status = 'A' " +
                "AND a.Container_Status = 'MTY' " +
                "GROUP BY a.container_no", nativeQuery = true)
            List<Object[]> getExportEmptyInventoryReport(
                    @Param("companyId") String companyId,
                    @Param("branchId") String branchId,
                    @Param("endDate") Date endDate
            );

            
            
            @Query(value = 
            	    "(SELECT DISTINCT b.container_no, b.container_size, b.container_type, q.customer_code, q.party_name, " +
            	    "b.Type_of_Container, b.Hold_Status, b.REFER, DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T'), ch.party_name, " +
            	    "d.importer_name, p.customer_code, p.party_name, b.Container_Status, d.origin " +
            	    "FROM cfigmcn b " +
            	    "LEFT OUTER JOIN cfigm c ON c.igm_trans_id = b.igm_trans_id AND c.igm_no = b.igm_no AND c.company_id = b.company_id " +
            	    "AND c.branch_id = b.branch_id AND c.profitcentre_id = b.profitcentre_id " +
            	    "LEFT OUTER JOIN cfigmcrg d ON d.igm_trans_id = b.igm_trans_id AND d.igm_no = b.igm_no AND d.company_id = b.company_id " +
            	    "AND d.branch_id = b.branch_id AND d.profitcentre_id = b.profitcentre_id AND d.igm_line_no = b.igm_line_no " +
            	    "LEFT OUTER JOIN vessel v ON c.company_id = v.company_id AND c.branch_id=v.branch_id and c.vessel_id = v.vessel_id " +
            	    "LEFT OUTER JOIN party p ON c.company_id = p.company_id AND c.branch_id=p.branch_id and c.shipping_line = p.party_id " +
            	    "LEFT OUTER JOIN party q ON c.company_id = q.company_id AND c.branch_id=q.branch_id and c.shipping_Agent = q.party_id " +
            	    "LEFT OUTER JOIN party ch ON b.company_id = ch.company_id AND b.branch_id=ch.branch_id and  b.CHA = ch.party_id " +
            	    "LEFT OUTER JOIN jar_detail j ON d.company_id = j.company_id AND d.origin = j.jar_dtl_id AND j.jar_id = 'ORIGIN' " +
            	    "WHERE b.company_id = :companyId AND b.branch_id = :branchId AND b.status = 'A' AND b.profitcentre_id = 'N00002' " +
            	    "AND b.Gate_In_Date < :date AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
            	    "AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) AND b.gate_in_id != '' AND c.status = 'A' AND d.status = 'A' " +
            	    "GROUP BY b.container_no, b.igm_no ORDER BY b.Gate_in_Date) " +

            	    "UNION ALL " +

            	    "(SELECT DISTINCT a.container_no, a.container_size, a.container_type, q.customer_code, q.party_name, 'Manual', 'Manual', 'Manual', " +
            	    "DATE_FORMAT(a.in_Gate_in_Date, '%d %b %Y %T'), 'Manual', 'Manual', p.customer_code, p.party_name, 'Manual', 'Manual' " +
            	    "FROM cfgatein a " +
            	    "LEFT OUTER JOIN party p ON a.company_id = p.company_id AND a.branch_id =p.branch_id and  a.sl = p.party_id " +
            	    "LEFT OUTER JOIN party q ON a.company_id = q.company_id AND a.branch_id =q.branch_id and  a.sa = q.party_id " +
            	    "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND a.Process_Id = 'P00212' " +
            	    "ORDER BY a.in_Gate_in_Date)",
            	    nativeQuery = true)
            	    List<Object[]> findLoadedInventoryTotal(
            	        @Param("companyId") String companyId,
            	        @Param("branchId") String branchId,
            	        @Param("date") Date date);
            	    
            	    
            	    
            	    
            	    
            	    
            	    
            	    
            	    
            	    
            	    

            	        @Query(value = 
            	            "(" +
            	            "SELECT DISTINCT " +
            	            "    d.igm_no AS igmNo, " +
            	            "    d.igm_line_no AS igmLineNo, " +
            	            "    b.container_no AS containerNo, " +
            	            "    b.container_size AS containerSize, " +
            	            "    b.container_type AS containerType, " +
            	            "    b.Container_Status AS containerStatus, " +
            	            "    d.No_Of_Packages AS noOfPackages, " +
            	            "    b.Gross_Wt AS grossWeight, " +
            	            "    d.Type_Of_Package AS typeOfPackage, " +
            	            "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	            "    d.importer_name AS importerName, " +
            	            "    d.commodity_Code AS commodityCode, " +
            	            "    d.UN_No AS unNo , " +
            	            "    b.Haz_Class AS hazClass, " +
            	            "    d.commodity_description AS commodityDescription, " +
            	            "    d.BL_No AS blNo, " +
            	            "    p.Party_Name AS agent, " +
            	            "    p.Shipping_Line_Code AS line, " +
            	            "    p.Party_Name AS cha " +
            	          
            	            "FROM " +
            	            "    cfigmcn b " +
            	            "LEFT OUTER JOIN cfigm c " +
            	            "    ON b.igm_trans_id = c.igm_trans_id " +
            	            "    AND b.igm_no = c.igm_no " +
            	            "    AND c.company_id = b.company_id " +
            	            "    AND c.branch_id = b.branch_id " +
            	            "LEFT OUTER JOIN party p " +
            	            "    ON c.company_id = p.company_id " +
            	            "    AND c.branch_id = p.branch_id " +
            	            "    AND c.shipping_line = p.Party_Id " +
            	            "LEFT OUTER JOIN cfigmcrg d " +
            	            "    ON b.igm_trans_id = d.igm_trans_id " +
            	            "    AND b.igm_no = d.igm_no " +
            	            "    AND b.company_id = d.company_id " +
            	            "    AND b.branch_id = d.branch_id " +
            	            "    AND b.igm_line_no = d.igm_line_no " +
            	            "WHERE " +
            	            "    b.Company_Id = :companyId " +
            	            "    AND b.Branch_Id = :branchId " +
            	            "    AND b.Status = 'A' " +
            	            "    AND d.status = 'A' " +
            	            "    AND c.status = 'A' " +
            	            "    AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
            	            "    AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) " +
            	            "    AND b.Gate_in_Date < :date " +
            	            "    AND b.type_of_container = 'Hazardous' " +
            	            "    AND b.gate_in_id != '' " +
            	            "GROUP BY " +
            	            "    b.container_no " +
            	            "ORDER BY " +
            	            "    b.Gate_in_Date " +
            	            ") " +
            	            "UNION " +
            	            "( " +
            	            "SELECT DISTINCT " +
            	            "    '' AS igmNo, " +
            	            "    '' AS igmLineNo, " +
            	            "    b.container_no AS containerNo, " +
            	            "    b.container_size AS containerSize, " +
            	            "    b.container_type AS containerType, " +
            	            "    '' AS containerStatus, " +
            	            "    '' AS noOfPackages, " +
            	            "    '0' AS grossWeight, " +
            	            "    '' AS typeOfPackage, " +
            	            "    DATE_FORMAT(b.in_Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	            "    b.importer_name AS importerName, " +
            	            "    '' AS commodityCode, " +
            	            "    '' AS hazClass, " +
            	            "    '' AS commodityDescription, " +
            	            "    '' AS blNo, " +
            	            "    p.party_name AS agent, " +
            	            "    p.Shipping_Line_Code AS line, " +
            	            "    p.party_name AS cha, " +
            	            "    '' AS unNo " +
            	            "FROM " +
            	            "    cfgatein b " +
            	            "LEFT OUTER JOIN party p " +
            	            "    ON b.company_id = p.company_id " +
            	            "    AND b.branch_id = p.branch_id " +
            	            "    AND b.sl = p.party_id " +
            	            "LEFT OUTER JOIN party q " +
            	            "    ON b.company_id = q.company_id " +
            	            "    AND b.branch_id = q.branch_id " +
            	            "    AND b.sa = q.party_id " +
            	            "WHERE " +
            	            "    b.Company_Id = :companyId " +
            	            "    AND b.Branch_Id = :branchId " +
            	            "    AND b.Process_Id = 'P00212' " +
            	            "    AND b.Hazardous = 'Y' " +
            	            ")",
            	            nativeQuery = true)
            	        List<Object[]> findHazardousContainers( @Param("companyId") String companyId,
                    	        @Param("branchId") String branchId,
                    	        @Param("date") Date date);

            	    
            	    
            	    
            	    
            	    
            	    
            	    
//            	    @Query(value = 
//            	    	    "SELECT DISTINCT " +
//            	    	    "    d.igm_no AS igmNo, " +
//            	    	    "    d.igm_line_no AS igmLineNo, " +
//            	    	    "    b.container_no AS containerNo, " +
//            	    	    "    b.container_size AS containerSize, " +
//            	    	    "    b.container_type AS containerType, " +
//            	    	    "    b.Container_Status AS containerStatus, " +
//            	    	    "    d.No_Of_Packages AS noOfPackages, " +
//            	    	    "    b.Gross_Wt AS grossWeight, " +
//            	    	    "    d.Type_Of_Package AS typeOfPackage, " +
//            	    	    "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
//            	    	    "    d.importer_name AS importerName, " +
//            	    	    "    d.commodity_Code AS commodityCode, " +
//            	    	    "    d.UN_No AS unNo, " +
//            	    	    "    b.Haz_Class AS hazClass, " +
//            	    	    "    d.commodity_description AS commodityDescription, " +
//            	    	    "    d.BL_No AS blNo, " +
//            	    	    "    p.Party_Name AS agent, " +
//            	    	    "    p.Shipping_Line_Code AS line, " +
//            	    	    "    p.Party_Name AS cha " +
//            	    	    "FROM " +
//            	    	    "    cfigmcn b " +
//            	    	    "LEFT OUTER JOIN cfigm c " +
//            	    	    "    ON b.igm_trans_id = c.igm_trans_id " +
//            	    	    "    AND b.igm_no = c.igm_no " +
//            	    	    "    AND c.company_id = b.company_id " +
//            	    	    "    AND c.branch_id = b.branch_id " +
//            	    	    "LEFT OUTER JOIN party p " +
//            	    	    "    ON c.company_id = p.company_id " +
//            	    	    "    AND c.branch_id = p.branch_id " +
//            	    	    "    AND c.shipping_line = p.Party_Id " +
//            	    	    "LEFT OUTER JOIN cfigmcrg d " +
//            	    	    "    ON b.igm_trans_id = d.igm_trans_id " +
//            	    	    "    AND b.igm_no = d.igm_no " +
//            	    	    "    AND b.company_id = d.company_id " +
//            	    	    "    AND b.branch_id = d.branch_id " +
//            	    	    "    AND b.igm_line_no = d.igm_line_no " +
//            	    	    "WHERE " +
//            	    	    "    b.Company_Id = :companyId " +
//            	    	    "    AND b.Branch_Id = :branchId " +
//            	    	    "    AND b.Status = 'A' " +
//            	    	    "    AND d.status = 'A' " +
//            	    	    "    AND c.status = 'A' " +
//            	    	    "    AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
//            	    	    "    AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) " +
//            	    	    "    AND b.Gate_in_Date < :date " +
//            	    	    "    AND b.type_of_container = 'Hazardous' " +
//            	    	    "    AND b.gate_in_id != '' " +
//            	    	    "GROUP BY " +
//            	    	    "    b.container_no " +
//            	    	    "ORDER BY " +
//            	    	    "    b.Gate_in_Date",
//            	    	    nativeQuery = true)
//            	    	List<Object[]> findHazardousContainers( 
//            	    	    @Param("companyId") String companyId,
//            	    	    @Param("branchId") String branchId,
//            	    	    @Param("date") Date date);

            	        @Query(value = 
            	                "(" +
            	                "SELECT DISTINCT " +
            	                "    b.container_no AS containerNo, " +
            	                "    b.container_size AS containerSize, " +
            	                "    b.container_type AS containerType, " +
            	                "    c.shipping_Agent AS shippingAgent, " +
            	                "    q.party_name AS shippingAgentName, " +
            	                "    b.Type_of_Container AS typeOfContainer, " +
            	                "    b.Hold_Status AS holdStatus, " +
            	                "    b.Type_of_Container AS typeOfContainer, " +
            	                "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	                "    ch.party_name AS cha, " +
            	                "    d.importer_name AS importerName, " +
            	                "    d.Commodity_Description AS commodityDescription, " +
            	                "    b.block_cell_no AS blockCellNo, " +
            	                "    p.party_name AS shippingLineName " +          
            	                "FROM " +
            	                "    cfigmcn b " +
            	                "LEFT OUTER JOIN cfigm c " +
            	                "    ON b.igm_trans_id = c.igm_trans_id " +
            	                "    AND b.igm_no = c.igm_no " +
            	                "    AND c.company_id = b.company_id " +
            	                "    AND c.branch_id = b.branch_id " +
            	                "    AND c.profitcentre_id = b.profitcentre_id " +
            	                "LEFT OUTER JOIN cfigmcrg d " +
            	                "    ON b.igm_trans_id = d.igm_trans_id " +
            	                "    AND b.igm_no = d.igm_no " +
            	                "    AND b.company_id = d.company_id " +
            	                "    AND b.branch_id = d.branch_id " +
            	                "    AND b.profitcentre_id = d.profitcentre_id " +
            	                "    AND b.igm_line_no = d.igm_line_no " +
            	                "LEFT OUTER JOIN vessel v " +
            	                "    ON c.company_id = v.company_id " +
            	                "    AND c.branch_id = v.branch_id " +
            	                "    AND c.vessel_id = v.vessel_id " +
            	                "LEFT OUTER JOIN party p " +
            	                "    ON c.company_id = p.company_id " +
            	                "    AND c.branch_id = p.branch_id " +
            	                "    AND c.shipping_line = p.party_id " +
            	                "LEFT OUTER JOIN party q " +
            	                "    ON c.company_id = q.company_id " +
            	                "    AND c.branch_id = q.branch_id " +
            	                "    AND c.shipping_Agent = q.party_id " +
            	                "LEFT OUTER JOIN party ch " +
            	                "    ON b.company_id = ch.company_id " +
            	                "    AND b.branch_id = ch.branch_id " +
            	                "    AND b.CHA = ch.party_id " +
            	                "LEFT OUTER JOIN jar_detail j " +
            	                "    ON d.company_id = j.company_id " +
            	                "    AND d.origin = j.jar_dtl_id " +
            	                "    AND j.jar_id = 'ORIGIN' " +
            	                "WHERE " +
            	                "    b.company_id = :companyId " +
            	                "    AND b.branch_id = :branchId " +
//            	                "    AND b.container_type IN ('RF', 'HRF', 'HR')  OR b.Type_of_container ='Reefer' " +
"    AND b.Type_of_container ='Reefer' " +
            	                "    AND b.status = 'A' " +
            	                "    AND b.Gate_In_Date < :date " +
            	                "    AND (b.gate_out_id = '' OR b.gate_out_id IS NULL OR b.gate_out_date > :date) " +
            	                "    AND (b.de_stuff_id = '' OR b.de_stuff_id IS NULL OR b.de_stuff_Date > :date) " +
            	                "    AND b.gate_in_id != '' " +
            	                "    AND b.profitcentre_id = 'N00002' " +
            	                "GROUP BY " +
            	                "    b.container_no " +
            	                "ORDER BY " +
            	                "    b.Gate_in_Date " +
            	                ") " +
            	                "UNION " +
            	                "( " +
            	                "SELECT DISTINCT " +
            	                "    b.container_no AS containerNo, " +
            	                "    b.container_size AS containerSize, " +
            	                "    b.container_type AS containerType, " +
            	                "    b.SA AS shippingAgent, " +
            	                "    p.party_name AS shippingAgentName, " +
            	                "    '' AS typeOfContainer, " +
            	                "    b.Hold_Status AS holdStatus, " +
            	                "    '' AS typeOfContainerr, " +
            	                "    DATE_FORMAT(b.in_Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	                "    q.party_name AS cha, " +
            	                "    b.importer_name AS importerName, " +          
            	                "    '' AS commodityDescription, " +
            	                "    '' AS blockCellNo, " +
            	                "    '' AS shippingLineName " +
            	                "FROM " +
            	                "    cfgatein b " +
            	                "LEFT OUTER JOIN party p " +
            	                "    ON b.company_id = p.company_id " +
            	                "AND b.branch_id = p.branch_id " +
            	                "    AND b.sl = p.party_id " +
            	                "LEFT OUTER JOIN party q " +
            	                "    ON b.company_id = q.company_id " +
            	                "AND b.branch_id = q.branch_id " +
            	                "    AND b.sa = q.party_id " +
            	                "WHERE " +
            	                "    b.company_id = :companyId " +
            	                "    AND b.branch_id = :branchId " +
            	                "    AND b.Process_Id = 'P00212' " +
//            	                "    AND b.container_type IN ('RF', 'HRF', 'HR') OR b.Type_of_container IN ('Reefer') " +
"    AND b.container_type IN ('RF', 'HRF', 'HR') " +
            	                ")",
            	                nativeQuery = true)
            	            List<Object[]> findReeferContainers(
            	                @Param("companyId") String companyId,
            	                @Param("branchId") String branchId,
            	                @Param("date") Date date
            	            );



            	            
            	            @Query(value = "" +
            	                    "SELECT DISTINCT " +
            	                    "    b.container_no AS containerNo, " +
            	                    "    b.container_size AS containerSize, " +
            	                    "    b.container_type AS containerType, " +
            	                    "    c.shipping_Agent AS shippingAgent, " +
            	                    "    q.party_name AS shippingAgentName, " +
            	                    "    b.Type_of_Container AS typeOfContainer, " +
            	                    "    b.Hold_Status AS holdStatus, " +
            	                    "    b.REFER AS reference, " +
            	                    "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	                    "    ch.party_name AS chaName, " +
            	                    "    d.importer_name AS importerName, " +
            	                    "    b.block_cell_no AS blockCellNo, " +
            	                    "    p.party_name AS shippingLineName, " +
            	                    "    c.via_no AS viaNo, " +
            	                    "    v.vessel_name AS vesselName, " +
            	                    "    c.port AS portCode, " +
            	                    "    b.profitcentre_id AS profitcentreId, " +
            	                    "    d.Commodity_Description AS commodityDescription, " +
            	                    "    d.origin AS origin, " +
            	                    "    j.jar_dtl_desc AS jarDescription, " +
            	                    "    b.be_no AS beNo, " +
            	                    "    DATE_FORMAT(b.BE_Date, '%d %b %Y') AS beDate, " +
            	                    "    c.igm_no AS igmNo, " +
            	                    "    DATE_FORMAT(c.igm_Date, '%d %b %Y') AS igmDate, " +
            	                    "    b.drt AS drt, " +
            	                    "    b.igm_line_no AS igmLineNo, " +
            	                    "    d.bl_no AS blNo, " +
            	                    "    d.gross_weight AS grossWeight, " +
            	                    "    d.no_of_packages AS noOfPackages, " +
            	                    "    b.container_status AS containerStatus " +
            	                    "FROM " +
            	                    "    cfigmcn b " +
            	                    "LEFT JOIN cfigm c " +
            	                    "    ON b.igm_trans_id = c.igm_trans_id " +
            	                    "    AND b.igm_no = c.igm_no " +
            	                    "    AND c.company_id = b.company_id " +
            	                    "    AND c.branch_id = b.branch_id " +
            	                    "    AND c.profitcentre_id = b.profitcentre_id " +
            	                    "LEFT JOIN cfigmcrg d " +
            	                    "    ON b.igm_trans_id = d.igm_trans_id " +
            	                    "    AND b.igm_no = d.igm_no " +
            	                    "    AND b.company_id = d.company_id " +
            	                    "    AND b.branch_id = d.branch_id " +
            	                    "    AND b.profitcentre_id = d.profitcentre_id " +
            	                    "    AND b.igm_line_no = d.igm_line_no " +
            	                    "LEFT JOIN vessel v " +
            	                    "    ON c.company_id = v.company_id " +
            	                    "    AND c.branch_id = v.branch_id " +
            	                    "    AND c.vessel_id = v.vessel_id " +
            	                    "LEFT JOIN party p " +
            	                    "    ON c.company_id = p.company_id " +
            	                    "    AND c.branch_id = p.branch_id " +
            	                    "    AND c.shipping_line = p.party_id " +
            	                    "LEFT JOIN party q " +
            	                    "    ON c.company_id = q.company_id " +
            	                    "    AND c.branch_id = q.branch_id " +
            	                    "    AND c.shipping_Agent = q.party_id " +
            	                    "LEFT JOIN party ch " +
            	                    "    ON b.company_id = ch.company_id " +
            	                    "    AND b.branch_id = ch.branch_id " +
            	                    "    AND b.CHA = ch.party_id " +
            	                    "LEFT JOIN jar_detail j " +
            	                    "    ON d.company_id = j.company_id " +
            	                    "    AND d.origin = j.jar_dtl_id " +
            	                    "    AND j.jar_id = 'ORIGIN' " +
            	                    "WHERE " +
            	                    "    b.company_id = :companyId " +
            	                    "    AND b.branch_id = :branchId " +
            	                    "    AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
            	                    "    AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) " +
            	                    "    AND b.status = 'A' " +
            	                    "    AND b.gate_in_id != '' " +
            	                    "    AND b.gate_in_date < :date " +
            	                    "    AND (b.ODC_Status = 'Y' OR b.container_type = 'FR' OR b.type_of_container='ODC' ) " +
            	                    "    AND b.profitcentre_id = 'N00002' " +
            	                    "UNION " +
            	                    "SELECT DISTINCT " +
            	                    "    b.container_no AS containerNo, " +
            	                    "    b.container_size AS containerSize, " +
            	                    "    b.container_type AS containerType, " +
            	                    "    b.container_status AS containerStatus, " +
            	                    "    DATE_FORMAT(b.in_Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	                    "    '' AS blockCellNo, " +
            	                    "    p.party_name AS shippingLineName, " +
            	                    "    '' AS viaNo, " +
            	                    "    '' AS vesselName, " +
            	                    "    q.party_name AS shippingAgentName, " +
            	                    "    b.Terminal AS portCode, " +
            	                    "    '' AS chaName, " +
            	                    "    b.importer_name AS importerName, " +
            	                    "    b.profitcentre_id AS profitcentreId, " +
            	                    "    '' AS commodityDescription, " +
            	                    "    '' AS origin, " +
            	                    "    '' AS jarDescription, " +
            	                    "    b.doc_ref_no AS beNo, " +
            	                    "    DATE_FORMAT(b.Doc_Ref_Date, '%d %b %Y') AS beDate, " +
            	                    "    '' AS igmNo, " +
            	                    "    DATE_FORMAT(b.Doc_Ref_Date, '%d %b %Y') AS igmDate, " +
            	                    "    b.drt AS drt, " +
            	                    "    '' AS igmLineNo, " +
            	                    "    '' AS blNo, " +
            	                    "    '0' AS grossWeight, " +
            	                    "    '1' AS noOfPackages, " +
            	                    "    b.sa AS shippingAgent, " +
            	                    "    '' AS typeOfContainer, " +
            	                    "    b.Hold_Status AS holdStatus, " +
            	                    "    b.REFER AS reference " +
            	                    "FROM " +
            	                    "    cfgatein b " +
            	                    "LEFT JOIN party p " +
            	                    "    ON b.company_id = p.company_id " +
            	                    "    AND b.branch_id = p.branch_id " +
            	                    "    AND b.sl = p.party_id " +
            	                    "LEFT JOIN party q " +
            	                    "    ON b.company_id = q.company_id " +
            	                    "    AND b.branch_id = q.branch_id " +
            	                    "    AND b.sa = q.party_id " +
            	                    "WHERE " +
            	                    "    b.company_id = :companyId " +
            	                    "    AND b.branch_id = :branchId " +
            	                    "    AND b.Process_Id = 'P00212' " +
            	                    "    AND (b.ODC_Status = 'Y' OR b.container_type = 'FR')", 
            	                    nativeQuery = true)
            	            List<Object[]> findODCContainerDetails(
            	                @Param("companyId") String companyId, 
            	                @Param("branchId") String branchId, 
            	                @Param("date") Date date
            	            );

            	            
            	            
            	            
            	            
            	            @Query(value = "SELECT DISTINCT " +
            	                    "    b.container_no, " +
            	                    "    b.container_size, " +
            	                    "    b.container_type, " +
            	                    "    q.customer_code, " +
            	                    "    q.party_name, " +
            	                    "    b.Type_of_Container, " +
            	                    "    b.Hold_Status, " +
            	                    "    b.REFER, " +
            	                    "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
            	                    "    ch.party_name AS chaName, " +
            	                    "    d.importer_name " +
            	                    "FROM " +
            	                    "    cfigmcn b " +
            	                    "LEFT OUTER JOIN cfigm c " +
            	                    "    ON c.igm_trans_id = b.igm_trans_id " +
            	                    "    AND c.igm_no = b.igm_no " +
            	                    "    AND c.company_id = b.company_id " +
            	                    "    AND c.branch_id = b.branch_id " +
            	                    "    AND c.profitcentre_id = b.profitcentre_id " +
            	                    "LEFT OUTER JOIN cfigmcrg d " +
            	                    "    ON d.igm_trans_id = b.igm_trans_id " +
            	                    "    AND d.igm_no = b.igm_no " +
            	                    "    AND d.company_id = b.company_id " +
            	                    "    AND d.branch_id = b.branch_id " +
            	                    "    AND d.profitcentre_id = b.profitcentre_id " +
            	                    "    AND d.igm_line_no = b.igm_line_no " +
            	                    "LEFT OUTER JOIN vessel v " +
            	                    "    ON c.company_id = v.company_id " +
            	                    "    AND c.vessel_id = v.vessel_id " +
            	                    "    AND c.branch_id = v.branch_id " +
            	                    "LEFT OUTER JOIN party p " +
            	                    "    ON c.company_id = p.company_id " +
            	                    "    AND c.shipping_line = p.party_id " +
            	                    "    AND c.branch_id = p.branch_id " +
            	                    "LEFT OUTER JOIN party q " +
            	                    "    ON c.company_id = q.company_id " +
            	                    "    AND c.shipping_Agent = q.party_id " +
            	                    "    AND c.branch_id = q.branch_id " +
            	                    "LEFT OUTER JOIN party ch " +
            	                    "    ON b.company_id = ch.company_id " +
            	                    "    AND b.CHA = ch.party_id " +
            	                    "    AND b.branch_id = ch.branch_id " +
            	                    "WHERE " +
            	                    "    b.company_id = :companyId " +
            	                    "    AND b.branch_id = :branchId " +
            	                    "    AND b.status = 'A' " +
            	                    "    AND b.profitcentre_id = 'N00002' " +
            	                    "    AND b.Gate_In_Date < :date " +
            	                    "    AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
            	                    "    AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) " +
            	                    "    AND b.gate_in_id != '' " +
            	                    "    AND c.status = 'A' " +
            	                    "    AND d.status = 'A' " +
            	                    "    AND b.Type_of_Container IN ('', 'General') " +
            	                    "GROUP BY " +
            	                    "    b.container_no, " +
            	                    "    b.igm_no " +
            	                    "ORDER BY " +
            	                    "    b.Gate_in_Date", 
            	             nativeQuery = true)
            	     List<Object[]> findGeneralConContainerDetails(
            	         @Param("companyId") String companyId, 
            	         @Param("branchId") String branchId, 
            	         @Param("date") Date date
            	     );

            	     
            	     
            	     
            	     
            	     
            	     
            	     @Query(value = "SELECT DISTINCT " +
                             "    b.container_no, " +
                             "    b.container_size, " +
                             "    b.container_type, " +
                             "    b.container_status, " +
                             "    c.port, " +
                             "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %H:%i') AS gateInDate, " +
                             "    IFNULL(DATE_FORMAT(c.Vessel_ETA, '%d/%m/%Y %H:%i'), '') AS vesselEta, " +
                             
 "    b.Container_Seal_No, " +
 "    q.party_name AS shippingAgentName, " +
 "    v.vessel_name, " +
 "    c.via_no, " +
 "    c.igm_no, " +
 "    d.Commodity_Description, " +

 "    b.Container_Weight, " +

 "    d.importer_name, " +
                             "    b.block_cell_no, " +
                             "    IFNULL(DATEDIFF(:date, c.Doc_Date), '') AS docDateDiff ," +
                             "    p.party_name AS shippingLineName, " +
           
                             
                           
                           
                             "    ch.party_name AS chaName, " +
                            
                             "    b.profitcentre_id, " +
                           
                             "    d.origin, " +
                             "    j.jar_dtl_desc, " +
                             "    b.be_no, " +
                             "    DATE_FORMAT(b.BE_Date, '%d %b %Y') AS beDate, " +
                             "    d.no_of_packages, " +
                             "    DATE_FORMAT(c.igm_Date, '%d %b %Y') AS igmDate, " +
                             "    b.drt, " +
                             "    b.igm_line_no, " +
                             "    d.bl_no, " +
                            
                             "    b.Scanner_Type, " +
                             "    d.gross_weight, " +
                             "    IFNULL(DATE_FORMAT(c.Doc_Date, '%d/%m/%Y %T'), '') AS docDate, " +
                           
                             "    IFNULL(DATE_FORMAT(c.Vessel_ETA, '%d %b %Y %T'), '') AS vesselEtaShort, " +
                             "    IFNULL(DATE_FORMAT(c.Doc_Date, '%d %b %Y %T'), '') AS docDateShort " +
                            
                             "FROM " +
                             "    cfigmcn b " +
                             "LEFT OUTER JOIN cfigmcrg d " +
                             "    ON b.igm_trans_id = d.igm_trans_id " +
                             "    AND b.igm_no = d.igm_no " +
                             "    AND b.company_id = d.company_id " +
                             "    AND b.branch_id = d.branch_id " +
                             "    AND b.profitcentre_id = d.profitcentre_id " +
                             "    AND b.igm_line_no = d.igm_line_no " +
                             "LEFT OUTER JOIN cfigm c " +
                             "    ON d.igm_trans_id = c.igm_trans_id " +
                             "    AND d.igm_no = c.igm_no " +
                             "    AND c.company_id = d.company_id " +
                             "    AND c.branch_id = d.branch_id " +
                             "    AND c.profitcentre_id = d.profitcentre_id " +
                             "LEFT OUTER JOIN vessel v " +
                             "    ON c.company_id = v.company_id " +
                             "    AND c.vessel_id = v.vessel_id " +
                             "LEFT OUTER JOIN party p " +
                             "    ON c.company_id = p.company_id " +
                             "AND c.branch_id = p.branch_id " +
                             "    AND c.shipping_line = p.party_id " +
                             "LEFT OUTER JOIN party q " +
                             "    ON c.company_id = q.company_id " +
                             "AND c.branch_id = q.branch_id " +
                             "    AND c.shipping_Agent = q.party_id " +
                             "LEFT OUTER JOIN party ch " +
                             "    ON b.company_id = ch.company_id " +
                             "AND b.branch_id = ch.branch_id " +
                             "    AND b.CHA = ch.party_id " +
                             "LEFT OUTER JOIN jar_detail j " +
                             "    ON d.company_id = j.company_id " +
                             "    AND d.origin = j.jar_dtl_id " +
                             "    AND j.jar_id = 'ORIGIN' " +
                             "WHERE " +
                             "    b.company_id = :companyId " +
                             "    AND b.branch_id = :branchId " +
                             "    AND c.port_jo != '' " +
                             "    AND c.port = :port " +
                             "    AND (b.gate_in_id = '' OR b.gate_in_date > :date) " +
                             "    AND b.status = 'A' " +
                             "    AND c.status = 'A' " +
                             "    AND c.shipping_line != '' " +
                             "    AND c.Doc_date < :date " +
                             "GROUP BY b.container_no " +
                             "ORDER BY c.port_jo_date, c.vessel_id, c.shipping_line", 
                      nativeQuery = true)
              List<Object[]> findPortWisePendencyContainerDetails(
                  @Param("companyId") String companyId, 
                  @Param("branchId") String branchId, 
                  @Param("date") Date date,
                  @Param("port") String port
              );
              @Query(value = "SELECT DISTINCT " +
                      "    b.container_no, " +
                      "    b.container_size, " +
                      "    b.container_type, " +
                      "    b.container_status, " +
                      "    c.port, " +
                      "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %H:%i') AS gateInDate, " +
                      "    IFNULL(DATE_FORMAT(c.Vessel_ETA, '%d/%m/%Y %H:%i'), '') AS vesselEta, " +
                      
"    b.Container_Seal_No, " +
"    q.party_name AS shippingAgentName, " +
"    v.vessel_name, " +
"    c.via_no, " +
"    c.igm_no, " +
"    d.Commodity_Description, " +

"    b.Container_Weight, " +

"    d.importer_name, " +
                      "    b.block_cell_no, " +
                      "    IFNULL(DATEDIFF(:date, c.Doc_Date), '') AS docDateDiff ," +
                      "    p.party_name AS shippingLineName, " +
    
                      
                    
                    
                      "    ch.party_name AS chaName, " +
                     
                      "    b.profitcentre_id, " +
                    
                      "    d.origin, " +
                      "    j.jar_dtl_desc, " +
                      "    b.be_no, " +
                      "    DATE_FORMAT(b.BE_Date, '%d %b %Y') AS beDate, " +
                      "    d.no_of_packages, " +
                      "    DATE_FORMAT(c.igm_Date, '%d %b %Y') AS igmDate, " +
                      "    b.drt, " +
                      "    b.igm_line_no, " +
                      "    d.bl_no, " +
                   
                   
                     
                      "    b.Scanner_Type, " +
                      "    d.gross_weight, " +
                      "    IFNULL(DATE_FORMAT(c.Doc_Date, '%d/%m/%Y %T'), '') AS docDate, " +
                    
                      "    IFNULL(DATE_FORMAT(c.Vessel_ETA, '%d %b %Y %T'), '') AS vesselEtaShort, " +
                      "    IFNULL(DATE_FORMAT(c.Doc_Date, '%d %b %Y %T'), '') AS docDateShort " +
                     
                      "FROM " +
                      "    cfigmcn b " +
                      "LEFT OUTER JOIN cfigmcrg d " +
                      "    ON b.igm_trans_id = d.igm_trans_id " +
                      "    AND b.igm_no = d.igm_no " +
                      "    AND b.company_id = d.company_id " +
                      "    AND b.branch_id = d.branch_id " +
                      "    AND b.profitcentre_id = d.profitcentre_id " +
                      "    AND b.igm_line_no = d.igm_line_no " +
                      "LEFT OUTER JOIN cfigm c " +
                      "    ON d.igm_trans_id = c.igm_trans_id " +
                      "    AND d.igm_no = c.igm_no " +
                      "    AND c.company_id = d.company_id " +
                      "    AND c.branch_id = d.branch_id " +
                      "    AND c.profitcentre_id = d.profitcentre_id " +
                      "LEFT OUTER JOIN vessel v " +
                      "    ON c.company_id = v.company_id " +
                      "    AND c.vessel_id = v.vessel_id " +
                      "LEFT OUTER JOIN party p " +
                      "    ON c.company_id = p.company_id " +
                      "AND c.branch_id = p.branch_id " +
                      "    AND c.shipping_line = p.party_id " +
                      "LEFT OUTER JOIN party q " +
                      "    ON c.company_id = q.company_id " +
                      "AND c.branch_id = q.branch_id " +
                      "    AND c.shipping_Agent = q.party_id " +
                      "LEFT OUTER JOIN party ch " +
                      "    ON b.company_id = ch.company_id " +
                      "AND b.branch_id = ch.branch_id " +
                      "    AND b.CHA = ch.party_id " +
                      "LEFT OUTER JOIN jar_detail j " +
                      "    ON d.company_id = j.company_id " +
                      "    AND d.origin = j.jar_dtl_id " +
                      "    AND j.jar_id = 'ORIGIN' " +
                      "WHERE " +
                      "    b.company_id = :companyId " +
                      "    AND b.branch_id = :branchId " +
                      "    AND c.port_jo != '' " +
//                      "    AND c.port = :port " +
                      "    AND (b.gate_in_id = '' OR b.gate_in_date > :date) " +
                      "    AND b.status = 'A' " +
                      "    AND c.status = 'A' " +
                      "    AND c.shipping_line != '' " +
                      "    AND c.Doc_date < :date " +
                      "GROUP BY b.container_no " +
                      "ORDER BY c.port_jo_date, c.vessel_id, c.shipping_line", 
               nativeQuery = true)
       List<Object[]> findAllPortWisePendencyContainerDetails(
           @Param("companyId") String companyId, 
           @Param("branchId") String branchId, 
           @Param("date") Date date
       );
       
       @Query(value = "SELECT DISTINCT " +
    		   "b.igm_no, " +
   	        "b.IGM_Line_No, " +
    	        "b.container_no, " +
    	        "b.container_size, " +
    	       
    	        "q.customer_code, " +
    	        "DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %H:%i') AS GateInDate, " +
    	        "q.party_name, " +
    	        "d.Commodity_Description, " +
    	        "b.No_Of_Packages, " +
    	        "b.Gross_Wt, " +
    	        "d.BL_No, " +
    	        "v.vessel_name, " +
    	        "c.Voyage_No, " +
    	        "c.VIA_NO, " +
    	        " '' ," +
    	        "IFNULL(DATEDIFF(:date, b.Gate_in_Date), '') AS days ," +
    	        "DATE_FORMAT(c.igm_Date, '%d %b %Y %H:%i') AS IGMDate, " +
    	        "'P' AS Status, " +
    	        "DATE_FORMAT(b.Notice_Date, '%d %b %Y %H:%i') AS NoticeDate, " +
     	       
    	        "DATE_FORMAT(b.Second_Notice_Date, '%d %b %Y %H:%i') AS SecondNoticeDate, " +
    	        "b.Type_of_Container, " +
    	        "b.Hold_Status, " +
    	        "b.REFER, " +
    	        "ch.party_name AS CHAName, " +
    	        "d.importer_name, " +
    	        "b.container_type, " +
    	        "d.Origin, " +
    	        
    	        "DATE_FORMAT(b.Final_Notice_Date, '%d %b %Y') AS FinalNoticeDate " +
    	        
    	    "FROM cfigmcn b " +
    	    "LEFT OUTER JOIN cfigm c ON c.igm_trans_id = b.igm_trans_id " +
    	        "AND c.igm_no = b.igm_no " +
    	        "AND c.company_id = b.company_id " +
    	        "AND c.branch_id = b.branch_id " +
    	        "AND c.profitcentre_id = b.profitcentre_id " +
    	    "LEFT OUTER JOIN cfigmcrg d ON d.igm_trans_id = b.igm_trans_id " +
    	        "AND d.igm_no = b.igm_no " +
    	        "AND d.company_id = b.company_id " +
    	        "AND d.branch_id = b.branch_id " +
    	        "AND d.profitcentre_id = b.profitcentre_id " +
    	        "AND d.igm_line_no = b.igm_line_no " +
    	    "LEFT OUTER JOIN vessel v ON c.company_id = v.company_id " +
    	        "AND c.vessel_id = v.vessel_id " +
    	        "AND c.branch_id = v.branch_id " +
    	    "LEFT OUTER JOIN party p ON c.company_id = p.company_id " +
    	        "AND c.shipping_line = p.party_id " +
    	        "AND c.branch_id = p.branch_id " +
    	    "LEFT OUTER JOIN party q ON c.company_id = q.company_id " +
    	        "AND c.shipping_Agent = q.party_id " +
    	        "AND c.branch_id = q.branch_id " +
    	    "LEFT OUTER JOIN party ch ON b.company_id = ch.company_id " +
    	        "AND b.CHA = ch.party_id " +
    	        "AND b.branch_id = ch.branch_id " +
    	    "LEFT OUTER JOIN jar_detail j ON d.company_id = j.company_id " +
    	        "AND d.origin = j.jar_dtl_id " +
    	        "AND j.jar_id = 'ORIGIN' " +
    	    "WHERE b.company_id = :companyId " +
                "    AND b.branch_id = :branchId " +
    	        "AND b.status = 'A' " +
    	        "AND b.profitcentre_id = 'N00002' " +
    	        "AND b.Gate_In_Date < :date " +
    	        "AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
    	        "AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) " +
    	        "AND b.gate_in_id != '' " +
    	        "AND c.status = 'A' " +
    	        "AND d.status = 'A' " +
    	    "GROUP BY b.container_no, b.igm_no " +
    	    "ORDER BY b.Gate_in_Date", 
    	    nativeQuery = true)
    	List<Object[]> londStandingCargoNewReport( @Param("companyId") String companyId, 
    	           @Param("branchId") String branchId, 
    	           @Param("date") Date date
    	       );

    	
    	
    	
    	@Query(value = "SELECT DISTINCT c.Destuff_WO_Trans_Id, " +
                "DATE_FORMAT(c.Destuff_WO_Date, '%d %b %Y %H:%i') AS DestuffWODate, " +
                "c.container_no, c.container_size, c.Container_Type, " +
                "c.Container_Status, c.Gate_Out_Type, " +
                "DATE_FORMAT(c.Gate_In_Date, '%d %b %Y %H:%i') AS GateInDate, " +
                "v.Vessel_Name, c.igm_no, c.igm_line_no, " +
                "c.Actual_No_Of_Packages, c.Gross_Wt, m.CUSTOMER_CODE " +
                "FROM cfigmcn c " +
                "LEFT OUTER JOIN cfigm d ON c.Company_Id = d.Company_Id " +
                "AND d.branch_id = c.branch_id " +
                "AND d.igm_trans_id = c.igm_trans_id " +
                "AND c.igm_no = d.igm_no " +
                "AND c.profitcentre_id = d.profitcentre_id " +
                "LEFT OUTER JOIN cfigmcrg e ON e.Company_Id = c.Company_Id " +
                "AND c.branch_id = e.branch_id " +
                "AND e.igm_trans_id = c.igm_trans_id " +
                "AND e.igm_no = c.igm_no " +
                "AND e.igm_line_no = c.igm_line_no " +
                "AND e.profitcentre_id = c.profitcentre_id " +
                "LEFT OUTER JOIN party p ON c.cha = p.party_id " +
                "AND c.Company_Id = p.Company_Id " +
                "AND c.branch_id = p.branch_id " +
                "LEFT OUTER JOIN party m ON d.shipping_line = m.party_id " +
                "AND d.Company_Id = m.Company_Id " +
                "AND d.branch_id = m.branch_id " +
                "LEFT OUTER JOIN vessel v ON v.Company_Id = d.Company_Id " +
                "AND v.Vessel_Id = d.Vessel_Id " +
                "WHERE c.company_id = :companyId " +
                "AND c.branch_id = :branchId " +
                "AND c.Destuff_WO_Trans_Id != '' " +
                "AND c.status = 'A' " +
                "AND c.Destuff_WO_Date BETWEEN :startDate AND :date " +
                "GROUP BY c.Container_no, c.igm_no", nativeQuery = true)
 List<Object[]> findLCLDestuffDetailsLoadingJo( @Param("companyId") String companyId, 
         @Param("branchId") String branchId, 
         @Param("startDate") Date startDate, 
         @Param("date") Date date
     );
 
 
 
 
 
 
 
 
 
 @Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.Container_Type, " +
         "DATE_FORMAT(a.Gate_In_Date, '%d %b %Y %H:%i'), v.Vessel_Name, a.IGM_No, " +
         "a.igm_Line_no, r.CUSTOMER_CODE, a.Actual_No_Of_Packages, p.Party_Name, " +
         "p.CUSTOMER_CODE, ag.party_name, DATE_FORMAT(b.Gate_Out_Date, '%d %b %Y %H:%i'), " +
         "b.Via_No, a.Gross_Wt,ag.CUSTOMER_CODE,  m.origin, h.Port, a.IGM_Trans_Id " +
         "FROM cfdestuffcn d " +
         "LEFT OUTER JOIN cfigmcn a ON a.company_id = d.company_id AND a.branch_id = d.branch_id " +
         "AND a.IGM_Trans_Id = d.IGM_Trans_Id AND a.Container_No = d.Container_No " +
         "LEFT OUTER JOIN cfimportgatepass b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id " +
         "AND a.gate_pass_no = b.gate_pass_id AND a.gate_out_Id = b.gate_out_Id AND b.container_no = a.container_no " +
         "LEFT OUTER JOIN party r ON r.company_id = b.company_id AND r.party_id = b.sl AND r.branch_id = b.branch_id " +
         "LEFT OUTER JOIN party p ON p.company_id = a.company_id AND b.cha = p.party_id AND p.branch_id = a.branch_id " +
         "LEFT OUTER JOIN party ag ON ag.company_id = d.company_id AND d.Shipping_Agent = ag.party_id AND ag.branch_id = d.branch_id " +
         "LEFT OUTER JOIN cfigm h ON a.Company_Id = h.Company_Id AND a.Branch_Id = h.Branch_Id AND a.IGM_Trans_Id = h.IGM_Trans_Id " +
         "AND a.igm_no = h.igm_no AND a.profitcentre_id = h.profitcentre_id " +
         "LEFT OUTER JOIN vessel v ON h.company_id = v.company_id AND h.vessel_id = v.vessel_id AND h.branch_id = v.branch_id " +
         "LEFT OUTER JOIN cfigmcrg m ON m.Company_Id = a.Company_Id AND m.Branch_Id = a.Branch_Id AND m.IGM_Trans_Id = a.IGM_Trans_Id " +
         "AND m.igm_no = a.igm_no AND m.igm_Line_no = a.igm_Line_no " +
         "WHERE a.company_id = :companyId AND a.branch_id = :branchId"
         + " AND d.Profitcentre_Id = 'N00002' " +
         "AND a.Container_status = 'FCL' AND a.status = 'A' AND d.Status = 'A' " +
         "AND a.De_stuff_Date BETWEEN :startDate AND :date " +
         "GROUP BY a.container_no, a.IGM_No", nativeQuery = true)
List<Object[]> findFCLDestuffContainerDetails( @Param("companyId") String companyId, 
        @Param("branchId") String branchId, 
        @Param("startDate") Date startDate, 
        @Param("date") Date date
    );







@Query(value = "SELECT DISTINCT b.container_no, b.container_size, b.container_type, "
        + "IFNULL(DATE_FORMAT(b.Gate_in_Date,'%d/%m/%Y %H:%i'),'') AS GateInDate,v.vessel_name,c.igm_no,"
        + "b.igm_line_no,k.CUSTOMER_CODE,d.no_of_packages,ch.party_name AS CHA,  c.shipping_Agent, q.party_name AS ShippingAgent,IFNULL(DATE_FORMAT(b.Gate_Out_Date,'%d/%m/%Y %T'),'') AS GateOutDate, "
        + "c.via_no,d.gross_weight,q.CUSTOMER_CODE,d.origin,c.port,  g.Container_Health,    p.party_name,   "
        + "d.importer_name, b.profitcentre_id, "
        + "d.Commodity_Description,  j.jar_dtl_desc, b.be_no, DATE_FORMAT(b.BE_Date,'%d %b %Y') AS BEDate, "
        + " DATE_FORMAT(c.igm_Date,'%d %b %Y') AS IgmDate, b.drt,  d.bl_no, "
        + "b.container_status, b.block_cell_no  "
        + "FROM cfigmcn b "
        + "LEFT OUTER JOIN cfgateout z ON z.erp_doc_ref_no = b.igm_trans_id " +
        "AND z.doc_ref_no = b.igm_no " +
        "AND z.container_no = b.container_no " +
        "AND z.profitcentre_id = b.profitcentre_id " +
        "AND z.gate_out_id = b.gate_out_id " +
        "AND z.company_id = b.company_id " +
        "AND z.branch_id = b.branch_id " 
        + "LEFT OUTER JOIN cfigm c ON b.igm_trans_id = c.igm_trans_id AND b.igm_no = c.igm_no "
        + "AND c.company_id = b.company_id AND c.branch_id = b.branch_id AND c.profitcentre_id = b.profitcentre_id "
        + "LEFT OUTER JOIN cfigmcrg d ON b.igm_trans_id = d.igm_trans_id AND b.igm_no = d.igm_no "
        + "AND b.company_id = d.company_id AND b.branch_id = d.branch_id AND b.profitcentre_id = d.profitcentre_id "
        + "AND b.igm_line_no = d.igm_line_no "
        + "LEFT OUTER JOIN vessel v ON c.company_id = v.company_id AND c.vessel_id = v.vessel_id "
        + "AND c.branch_id = v.branch_id "
        + "LEFT OUTER JOIN party p ON c.company_id = p.company_id AND c.shipping_line = p.party_id "
        + "AND c.branch_id = p.branch_id "
        + "LEFT OUTER JOIN party q ON c.company_id = q.company_id AND c.shipping_Agent = q.party_id "
        + "AND c.branch_id = q.branch_id "
        + "LEFT OUTER JOIN party ch ON b.company_id = ch.company_id AND b.CHA = ch.party_id "
        + "AND b.branch_id = ch.branch_id "
        + "LEFT OUTER JOIN jar_detail j ON d.company_id = j.company_id AND d.origin = j.jar_dtl_id "
        + "AND j.jar_id = 'ORIGIN' "
        + "LEFT OUTER JOIN cfgatein g ON g.Gate_In_Id = b.Gate_In_Id AND g.company_id = b.company_id "
        + "AND g.branch_id = b.branch_id AND g.Container_No = b.Container_No and g.Profitcentre_Id = b.Profitcentre_Id  "
        + "LEFT OUTER JOIN party k ON k.company_id = c.company_id AND c.shipping_line = k.party_id "
        + "WHERE b.company_id = :companyId AND b.branch_id = :branchId AND"
        + " b.container_status = 'FCL' "
        + "AND b.profitcentre_id = 'N00002' "
//        + "AND (b.de_stuff_id = '' OR b.de_stuff_id IS NULL OR b.de_stuff_Date > :date) "
        + "AND b.status = 'A' "
        + " AND b.gate_in_id != '' "
        + " AND b.Gate_Out_Id != '' "
//        + "AND b.gate_in_date < :date "
        + "AND z.Gate_Out_date BETWEEN :startDate AND :date "
//        + " AND b.container_no != '' OR  b.container_no IS NOT NULL " 
+ " AND b.container_no != '' " 
        + "GROUP BY b.container_no "
        + "ORDER BY b.Gate_in_Date", nativeQuery = true)
List<Object[]> findFCLLoadedContainerInfo(@Param("companyId") String companyId, 
        @Param("branchId") String branchId, 
        @Param("startDate") Date startDate, 
        @Param("date") Date date
    ); 




@Query(value = 
"SELECT DISTINCT " + 
"    a.container_no, " +
"    a.container_size, " +
//"    a.Container_Type, " +
"    DATE_FORMAT(a.Gate_In_Date, '%d %b %Y %H:%i') AS gateInDate, " +
"    DATE_FORMAT(a.De_Stuff_Date, '%d %b %Y %H:%i') AS deStuffDate, " +
"    v.Vessel_Name, " +
"    a.IGM_No, " +
"    a.igm_Line_no, " +
"    a.Actual_No_Of_Packages, " +
"    a.Gross_Wt, " +
"    '' ," +
"    '' ," +
"    '' ," +
"    r.CUSTOMER_CODE, " +
"    p.party_name, " +
"    jr.CUSTOMER_CODE AS jrCustomerCode, " +
"    jr.Party_Name AS jrPartyName, " +
"    m.Importer_Name ," +
"    DATE_FORMAT(b.Gate_Out_Date,'%d/%m/%y %H:%i'), " +
"    '' ," +
"    b.Via_No, " +
"    j.Jar_Dtl_Desc, " +
"    b.Vehicle_No, " +
"    a.Remarks, " +
"    a.BE_No, " +
"    DATE_FORMAT(a.BE_Date ,'%d/%m/%y %H:%i') ," +
"    m.origin, " +
"    h.Port  " +
"FROM cfigmcn a " +
"LEFT OUTER JOIN cfimportgatepass b " +
"    ON a.Company_Id = b.Company_Id " +
"    AND a.Branch_Id = b.Branch_Id " +
"    AND a.gate_pass_no = b.gate_pass_id " +
"    AND a.gate_out_Id = b.gate_out_Id " +
"    AND b.container_no = a.container_no " +
"LEFT OUTER JOIN cfigm h " +
"    ON a.Company_Id = h.Company_Id " +
"    AND a.Branch_Id = h.Branch_Id " +
"    AND a.IGM_Trans_Id = h.IGM_Trans_Id " +
"    AND a.igm_no = h.igm_no " +
"    AND a.profitcentre_id = h.profitcentre_id " +
"LEFT OUTER JOIN vessel v " +
"    ON h.company_id = v.company_id " +
"    AND h.vessel_id = v.vessel_id " +
"    AND h.branch_id = v.branch_id " +
"LEFT OUTER JOIN cfigmcrg m " +
"    ON m.Company_Id = a.Company_Id " +
"    AND m.Branch_Id = a.Branch_Id " +
"    AND m.IGM_Trans_Id = a.IGM_Trans_Id " +
"    AND m.igm_no = a.igm_no " +
"    AND m.Profitcentre_Id = a.Profitcentre_Id " +
"    AND m.igm_line_no = a.IGM_Line_No " +
"LEFT OUTER JOIN party r " +
"    ON r.company_id = h.company_id " +
"    AND h.Shipping_line = r.party_id " +
"    AND r.branch_id = h.branch_id " +
"LEFT OUTER JOIN party p " +
"    ON p.company_id = a.company_id " +
"    AND b.cha = p.party_id " +
"    AND p.branch_id = a.branch_id " +
"LEFT OUTER JOIN party jr " +
"    ON jr.Company_Id = h.Company_Id " +
"    AND h.Shipping_agent = jr.party_id " +
"    AND jr.branch_id = h.branch_id " +
"LEFT OUTER JOIN cfequipmentactivity eq " +
"    ON a.Company_Id = eq.Company_Id " +
"    AND a.Branch_Id = eq.Branch_Id " +
"    AND a.IGM_Trans_Id = eq.Erp_Doc_ref_no " +
"    AND a.igm_no = eq.Doc_ref_no " +
"    AND a.De_Stuff_Id = eq.De_Stuff_Id " +
"LEFT OUTER JOIN jar_detail j " +
"    ON j.Company_Id = eq.Company_Id " +
"    AND j.jar_dtl_id = eq.Equipment " +
"WHERE a.company_id = :companyId " +
"    AND a.branch_id = :branchId " +
"    AND a.Profitcentre_Id = 'N00002' " +
"    AND a.De_Stuff_Date BETWEEN :startDate AND :date " +
"    AND a.Container_status = 'LCL' " +
"    AND a.status = 'A' " +
"GROUP BY a.container_no, a.IGM_No",
nativeQuery = true)
List<Object[]> getLCLDestuffReportInDeleverySection(
@Param("companyId") String companyId, 
@Param("branchId") String branchId, 
@Param("startDate") Date startDate, 
@Param("date") Date date
);









@Query(value = "SELECT DISTINCT " +
        "  a.container_no, " +
        "  a.container_size, " +
        "  a.container_type, " +
        "  IFNULL(DATE_FORMAT(c.de_stuff_date, '%d %b %Y %H:%i'), '0') AS DSTF, " +
        "  g.customer_code, " +
        "  g.party_name, " +
        "  s.customer_code, " +
        "  IFNULL(DATE_FORMAT(a.Gate_Out_Date, '%d %b %Y %H:%i'), 0) AS MTOUTDATE, " +
        "  IFNULL(DATE_FORMAT(a.Gate_Out_Date, '%H%i%s'), 0) AS MTOUTTIME, " +
        "  q.party_Name AS transportname, " +
        "  b.to_location, " +
        "  a.delivery_order_no, " +
        "  a.comments, " +
        "  a.vehicle_no " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfcommongatepass b " +
        "  ON a.company_id = b.company_id " +
        "  AND a.Branch_Id = b.Branch_Id " +
        "  AND a.Gate_Pass_No = b.Gate_Pass_Id " +
        "  AND a.Container_no = b.Container_no " +
        "  AND a.ERP_Doc_Ref_No = a.Gate_Pass_No " +
        "LEFT OUTER JOIN party q " +
        "  ON q.company_id = a.company_id and q.branch_id=a.branch_id " +
        "  AND a.transporter = q.party_id " +
        "LEFT OUTER JOIN cfdestuffcn c " +
        "  ON b.company_id = c.company_id " +
        "  AND b.Branch_Id = c.Branch_Id " +
        "  AND b.Container_no = c.Container_no " +
        "  AND b.gate_in_id = c.gate_in_id " +
        "  AND b.De_stuff_Id = c.De_Stuff_Id " +
        "LEFT OUTER JOIN party s " +
        "  ON s.company_id = a.company_id and s.branch_id=a.branch_id " +
        "  AND s.party_id = a.sl " +
        "LEFT OUTER JOIN party g " +
        "  ON g.company_id = b.company_id and g.branch_id=b.branch_id  " +
        "  AND g.party_id = b.sa " +
        "WHERE a.company_id = :companyId " +
        "  AND a.branch_id = :branchId " +
        "  AND a.status = 'A' " +
        "  AND a.Profitcentre_Id IN ('N00004','N00002') " +
        "  AND a.Container_Status = 'MTY' " +
        "  AND a.Gate_Out_Date BETWEEN :startDate AND :endDate " +
        "GROUP BY a.container_no " +
        "ORDER BY a.Gate_Out_Date, a.container_no",
nativeQuery = true)
List<Object[]> findEmptyGateOutDetails(@Param("companyId") String companyId,
                           @Param("branchId") String branchId,
                           @Param("startDate") Date startDate,
                           @Param("endDate") Date endDate);



@Query(value = "SELECT DISTINCT " +
        "  a.Container_No, " +
        "  a.Container_Size AS Size, " +
        "  DATE_FORMAT(d.De_Stuff_Date, '%d/%b/%Y %H:%i'), " +
        "'' ,"+
        "  v.Vessel_Name, " +
        "  a.Igm_no, " +
        "  a.IGM_Line_No, " +
        "  e.QTY_TAKEN_OUT, " +
        "  e.GW_Taken_Out, " +
        "  q.CUSTOMER_CODE, " +
        "  r.party_name, " +
        "  p.CUSTOMER_CODE, " +
        "  p.Party_Name, " +
        "  DATE_FORMAT(a.Gate_Out_Date, '%d/%b/%Y %H:%i') AS deliverydate, " +
        " '' , " +
        "  c.Voyage_No, " +
        "  GROUP_CONCAT(DISTINCT eq.Jar_Dtl_Desc) AS equipment ," +
        "  e.vehicle_no, " +
        "  a.Remarks, " +
        "  a.BE_No, " +
        "  DATE_FORMAT(a.BE_Date, '%d/%b/%Y %H:%i'), " +
        "  a.No_Of_Packages, " +
        "  a.Container_Type AS Type, " +
       
        "  DATE_FORMAT(c.IGM_Date, '%d/%b/%Y %H:%i') AS igmdate, " +
        "  DATE_FORMAT(a.Gate_In_Date, '%d/%b/%Y %H:%i'), " +
       
      
       
        "  b.BL_No, " +
       
        
       
        
        "  d.Actual_No_Of_Packages, " +
        "  b.Type_Of_Package AS pkgtype, " +
        "  d.Movement_Type AS movtype, " +
        "  b.Importer_Name, " +
       
        "  '' AS Consoler, " +
        "  d.Yard_Location AS location, " +
        "  d.Area_Occupied AS area, " +
        "  b.Commodity_Description, " +
       
        "  a.Cargo_Value, " +
        "  a.Cargo_Duty " +
       
      
        
       
      
        
        "FROM cfigmcn a " +
        "LEFT OUTER JOIN cfigmcrg b " +
        "  ON a.Company_Id = b.Company_Id " +
        "  AND a.Branch_Id = b.Branch_Id " +
        "  AND a.Igm_no = b.Igm_no " +
        "  AND a.IGM_Line_No = b.IGM_Line_No " +
        "  AND a.IGM_Trans_Id = b.IGM_Trans_Id " +
        "  AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "LEFT OUTER JOIN cfigm c " +
        "  ON a.Company_Id = c.Company_Id " +
        "  AND a.Branch_Id = c.Branch_Id " +
        "  AND a.Igm_no = c.IGM_No " +
        "  AND a.IGM_Trans_Id = c.IGM_Trans_Id " +
        "  AND a.Profitcentre_Id = c.Profitcentre_Id " +
        "LEFT OUTER JOIN cfdestuffcrg d " +
        "  ON a.Company_Id = b.Company_Id " +
        "  AND a.Branch_Id = d.Branch_Id " +
        "  AND a.IGM_Trans_Id = d.IGM_Trans_Id " +
        "  AND a.Igm_no = d.IGM_No " +
        "  AND a.IGM_Line_No = d.IGM_Line_No " +
        "  AND a.De_Stuff_Id = d.De_Stuff_Id " +
        "LEFT OUTER JOIN vessel v " +
        "  ON a.Company_Id = v.Company_Id  and a.branch_id=v.branch_id" +
        "  AND c.Vessel_Id = v.Vessel_Id " +
        "LEFT OUTER JOIN party p " +
        "  ON a.Company_Id = p.Company_Id and a.branch_id=p.branch_id" +
        "  AND c.Shipping_Agent = p.Party_Id " +
        "LEFT OUTER JOIN party q " +
        "  ON a.Company_Id = q.Company_Id and a.branch_id=q.branch_id" +
        "  AND c.Shipping_Line = q.Party_Id " +
        "LEFT OUTER JOIN cfimportgatepass e " +
        "  ON a.Company_Id = e.Company_Id " +
        "  AND a.Branch_Id = e.Branch_Id " +
        "  AND a.Igm_no = e.Igm_no " +
        "  AND a.IGM_Line_No = e.IGM_Line_No " +
        "  AND a.IGM_Trans_Id = e.IGM_Trans_Id " +
        "  AND a.Profitcentre_Id = e.Profitcentre_Id " +
        "LEFT OUTER JOIN party r " +
        "  ON e.Company_Id = r.Company_Id and e.branch_id=r.branch_id" +
        "  AND e.cha = r.Party_Id " +
        "LEFT OUTER JOIN cfequipmentactivity ab " +
        "  ON a.Company_Id = ab.Company_Id " +
        "  AND a.Branch_Id = ab.Branch_Id " +
        "  AND a.Profitcentre_Id = ab.Profitcentre_Id " +
        "  AND a.IGM_No = ab.DOC_REF_NO " +
        "  AND a.Container_No = ab.Container_No " +
        "  AND a.IGM_Trans_Id = ab.Erp_Doc_ref_no " +
        "  AND a.De_Stuff_Id = ab.De_Stuff_Id " +
        "LEFT OUTER JOIN jar_detail eq " +
        "  ON ab.Company_Id = eq.Company_Id " +
        "  AND ab.Equipment = eq.Jar_Dtl_Id " +
        "  AND eq.Jar_Id = 'J00012' " +
        "WHERE a.company_id = :companyId " +
        "  AND a.branch_id = :branchId " +
        "  AND a.status = 'A' " +
        "  AND a.Gate_Out_Date BETWEEN :startDate AND :endDate " +
        "  AND a.Container_Status = 'LCL' " +
        "  AND a.De_Stuff_Id != '' " +
        "  AND a.Status != 'D' " +
        "GROUP BY b.IGM_Trans_Id, b.IGM_No, b.IGM_Line_No, a.container_no " +
        "ORDER BY a.De_Stuff_Date",
nativeQuery = true)
List<Object[]> findLCLCargoDelivered(@Param("companyId") String companyId,
                       @Param("branchId") String branchId,
                       @Param("startDate") Date startDate,
                       @Param("endDate") Date endDate);








@Query(value = 
" SELECT DISTINCT " +
"     a.container_no, " +
"     a.container_size, " +
"     a.container_type, " +
"     DATE_FORMAT(a.in_Gate_In_Date, '%d %b %Y %H:%i') AS inDate, " +
"     p.party_name, " +
"     n.CUSTOMER_CODE AS agentCode, " +
"     p.CUSTOMER_CODE AS lineCode, " +
"     c.Port AS origin, " +
"     a.Comments " +
" FROM cfgatein a " +
" LEFT OUTER JOIN party p " +
"     ON p.company_id = a.company_id " +
"     AND a.sa = p.party_id " +
"     AND p.branch_id = a.branch_id " +
" LEFT OUTER JOIN cfigm c " +
"     ON c.company_id = a.company_id " +
"     AND c.branch_id = a.branch_id " +
"     AND a.Profitcentre_Id = c.Profitcentre_Id " +
"     AND a.VIA_NO = c.VIA_NO " +
"     AND a.Doc_Ref_Date = c.Doc_Date " +
"     AND a.profitcentre_id = c.profitcentre_id " +
" LEFT OUTER JOIN party n " +
"     ON a.company_id = n.company_id " +
"     AND a.sl = n.party_id " +
"     AND a.branch_id = n.branch_id " +
" WHERE a.company_id = :companyId " +
"   AND a.branch_id = :branchId " +
"   AND a.status = 'A' " +
"   AND a.Profitcentre_Id = 'N00004' " +
"   AND a.Gate_In_Type = 'EXP' " +
"   AND a.Process_Id = 'P00219' " +
"   AND a.in_Gate_In_Date BETWEEN :startDate AND :endDate ", 
nativeQuery = true)
List<Object[]> getExportEmptyContainerGateInDetails(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("startDate") Date startDate,
@Param("endDate") Date endDate
);



@Query(value = 
"SELECT " + 
"b.Gate_In_Type, " + 
"'', '', " + 
"DATE_FORMAT(b.in_Gate_In_Date, '%d-%m-%Y %H:%i') AS GateInDate, " + 
"b.Vehicle_No, " + 
"b.Container_No, " + 
"b.Container_Size, " + 
"b.Container_Type, " + 
"IFNULL((b.Gross_Weight - b.Tare_Weight), '0.00') AS CargoWt, " + 
"IFNULL(b.Tare_Weight, '0.00') AS TareWt, " + 
"o.Party_Name AS AccountHolder, " + 
"n.Party_Name AS CHAName, " + 
"m.Party_Name AS LineName, " + 
"'', '', " + 
"b.Container_Health, " + 
"DATE_FORMAT(v.Gate_Out_Date, '%d-%m-%Y %H:%i') AS GateOutDate, " + 
"d.SB_No, " + 
"DATE_FORMAT(d.SB_Date, '%d-%m-%Y %H:%i') AS SBDate, " + 
"IFNULL(SUM(st.stuffed_qty), '0.00') AS StuffedQty, " + 
"IFNULL(SUM(st.cargo_weight), '0.00') AS CargoWeight, " + 
"vs.vessel_name AS VesselName, " + 
"st.POD, " + 
"st.POL, " + 
"st.agent_seal_no AS AgentSeal, " + 
"st.customs_seal_no AS CustomsSeal, " + 
"st.Gate_Out_Id, " + 
"b.Gate_In_Id " + 
"FROM cfgatein b " + 
"LEFT OUTER JOIN cfexpinventory v " + 
"ON b.Company_Id = v.Company_Id " + 
"AND b.Branch_Id = v.Branch_Id " + 
"AND b.Profitcentre_Id = v.Profitcentre_Id " + 
"AND b.Gate_In_Id = v.Gate_In_Id " + 
"LEFT OUTER JOIN cfsb d " + 
"ON v.Company_Id = d.Company_Id " + 
"AND v.Branch_Id = d.Branch_Id " + 
"AND v.Profitcentre_Id = d.Profitcentre_Id " + 
"AND v.SB_Trans_Id = d.SB_Trans_Id " + 
"AND v.SB_No = d.SB_No " + 
"LEFT OUTER JOIN cfstufftally st " + 
"ON b.Company_Id = st.Company_Id " + 
"AND b.Branch_Id = st.Branch_Id " + 
"AND b.Gate_In_Id = st.Carting_Trans_Id " + 
"AND b.Stuff_Tally_Id = st.Stuff_Tally_Id " + 
"LEFT OUTER JOIN cfexpmovementreq mq " + 
"ON mq.Company_Id = st.Company_Id " + 
"AND mq.Branch_Id = st.Branch_Id " + 
"AND mq.Movement_Req_Id = st.Movement_Req_Id " + 
"AND mq.Stuff_Tally_Id = st.Stuff_Tally_Id " + 
"LEFT OUTER JOIN cfgateout c " + 
"ON mq.Company_Id = c.Company_Id " + 
"AND mq.Branch_Id = c.Branch_Id " + 
"AND mq.Profitcentre_Id = c.Profitcentre_Id " + 
"AND mq.Gate_Out_Id = c.Gate_Out_Id " + 
"AND mq.Container_No = c.Container_No " + 
"LEFT OUTER JOIN party m " + 
"ON b.Company_Id = m.Company_Id and b.branch_id =m.branch_id " + 
"AND b.SL = m.Party_Id " + 
"LEFT OUTER JOIN party o " + 
"ON b.Company_Id = o.Company_Id and b.branch_id =o.branch_id " + 
"AND b.On_Account_Of = o.Party_Id " + 
"LEFT OUTER JOIN party n " + 
"ON b.Company_Id = n.Company_Id and b.branch_id =n.branch_id  " + 
"AND b.CHA = n.Party_Id " + 
"LEFT OUTER JOIN vessel vs " + 
"ON st.Company_Id = vs.Company_Id and st.branch_id =vs.branch_id " + 
"AND st.Vessel_Id = vs.Vessel_Id " + 
"WHERE " + 
"b.Company_Id = :companyId " + 
"AND b.Branch_Id = :branchId " + 
"AND b.Status = 'A' " + 
"AND b.in_Gate_In_Date BETWEEN :startDate AND :endDate " + 
"AND b.Gate_In_Type = 'Buffer' " + 
"AND b.Process_Id = 'P00234' " + 
"AND b.Profitcentre_Id = 'N00004' " + 
"GROUP BY b.Container_No, b.Gate_In_Id " + 
"ORDER BY b.in_Gate_In_Date ASC", nativeQuery = true)
List<Object[]> getExportBufferGateInReport(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("startDate") Date startDate,
@Param("endDate") Date endDate
);






@Query(value = 
"SELECT " + 
"b.Gate_In_Type, " + 
"'', '', " + 
"DATE_FORMAT(b.in_Gate_In_Date, '%d-%m-%Y %H:%i') AS GateInDate, " + 
"b.Vehicle_No, " + 
"b.Container_No, " + 
"b.Container_Size, " + 
"b.Container_Type, " + 
"IFNULL((b.Gross_Weight - b.Tare_Weight), '0.00') AS CargoWt, " + 
"IFNULL(b.Tare_Weight, '0.00') AS TareWt, " + 
"o.Party_Name AS AccountHolder, " + 
"n.Party_Name AS CHAName, " + 
"m.Party_Name AS LineName, " + 
"'', '', " + 
"b.Container_Health, " + 
"DATE_FORMAT(v.Gate_Out_Date, '%d-%m-%Y %H:%i') AS GateOutDate, " + 
"d.SB_No, " + 
"DATE_FORMAT(d.SB_Date, '%d-%m-%Y %H:%i') AS SBDate, " + 
"IFNULL(SUM(st.stuffed_qty), '0.00') AS StuffedQty, " + 
"IFNULL(SUM(st.cargo_weight), '0.00') AS CargoWeight, " + 
"vs.vessel_name AS VesselName, " + 
"st.POD, " + 
"st.POL, " + 
"st.agent_seal_no AS AgentSeal, " + 
"st.customs_seal_no AS CustomsSeal, " + 
"st.Gate_Out_Id, " + 
"b.Gate_In_Id " + 
"FROM cfgatein b " + 
"LEFT OUTER JOIN cfexpinventory v " + 
"ON b.Company_Id = v.Company_Id " + 
"AND b.Branch_Id = v.Branch_Id " + 
"AND b.Profitcentre_Id = v.Profitcentre_Id " + 
"AND b.Gate_In_Id = v.Gate_In_Id " + 
"LEFT OUTER JOIN cfsb d " + 
"ON v.Company_Id = d.Company_Id " + 
"AND v.Branch_Id = d.Branch_Id " + 
"AND v.Profitcentre_Id = d.Profitcentre_Id " + 
"AND v.SB_Trans_Id = d.SB_Trans_Id " + 
"AND v.SB_No = d.SB_No " + 
"LEFT OUTER JOIN cfstufftally st " + 
"ON b.Company_Id = st.Company_Id " + 
"AND b.Branch_Id = st.Branch_Id " + 
"AND b.Gate_In_Id = st.Carting_Trans_Id " + 
"AND b.Stuff_Tally_Id = st.Stuff_Tally_Id " + 
"LEFT OUTER JOIN cfexpmovementreq mq " + 
"ON mq.Company_Id = st.Company_Id " + 
"AND mq.Branch_Id = st.Branch_Id " + 
"AND mq.Movement_Req_Id = st.Movement_Req_Id " + 
"AND mq.Stuff_Tally_Id = st.Stuff_Tally_Id " + 
"LEFT OUTER JOIN cfgateout c " + 
"ON mq.Company_Id = c.Company_Id " + 
"AND mq.Branch_Id = c.Branch_Id " + 
"AND mq.Profitcentre_Id = c.Profitcentre_Id " + 
"AND mq.Gate_Out_Id = c.Gate_Out_Id " + 
"AND mq.Container_No = c.Container_No " + 
"LEFT OUTER JOIN party m " + 
"ON b.Company_Id = m.Company_Id and b.branch_id =m.branch_id " + 
"AND b.SL = m.Party_Id " + 
"LEFT OUTER JOIN party o " + 
"ON b.Company_Id = o.Company_Id and b.branch_id =o.branch_id " + 
"AND b.On_Account_Of = o.Party_Id " + 
"LEFT OUTER JOIN party n " + 
"ON b.Company_Id = n.Company_Id and b.branch_id =n.branch_id  " + 
"AND b.CHA = n.Party_Id " + 
"LEFT OUTER JOIN vessel vs " + 
"ON st.Company_Id = vs.Company_Id and st.branch_id =vs.branch_id " + 
"AND st.Vessel_Id = vs.Vessel_Id " + 
"WHERE " + 
"b.Company_Id = :companyId " + 
"AND b.Branch_Id = :branchId " + 
"AND b.Status = 'A' " + 
"AND b.in_Gate_In_Date BETWEEN :startDate AND :endDate " + 
"AND b.Gate_In_Type IN ('ONWH','FDS') " + 
"AND b.Process_Id = 'P00234' " + 
"AND b.Profitcentre_Id = 'N00004' " + 
"GROUP BY b.Container_No, b.Gate_In_Id " + 
"ORDER BY b.in_Gate_In_Date ASC", nativeQuery = true)
List<Object[]> getExportFCLGateInReport(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("startDate") Date startDate,
@Param("endDate") Date endDate
);






@Query(value = "SELECT DISTINCT "
		 + " a.container_no, a.container_size, " 
		 + "a.container_type,d.commodity,DATE_FORMAT(d.Stuff_Tally_Date, '%d %b %Y %H:%i'),"+
		
        "DATE_FORMAT(a.Gate_Out_Date, '%d %b %Y %H:%i'), n.Party_Name, q.party_name AS shippingLine,v.vessel_name,c.pol,c.Customs_Seal_No,"

       
      
        + " c.agent_seal_no,  c.via_no,SUM(d.Stuffed_Qty),SUM(d.Cargo_weight),c.Tare_Weight,tp.party_name, '',  a.vehicle_no,  DATE_FORMAT(d.sb_date, '%d %b %Y %H:%i '), '',c.mov_req_type ,"
        + "a.Comments ,a.invoice_no,  a.gate_out_id, d.sb_no, " +
        "c.vessel_id,  c.pod, c.shipping_agent, p.party_name AS shippingAgent, " +
        "c.shipping_line,   c.movement_req_id, a.Gross_Wt, " +
        " a.transporter_name, c.type_of_container, a.Trip_Type,  e.cargo_type," +
        "c.Mov_Req_Type, c.mov_req_type, c.stuff_tally_id,   " +
        "  d.Tare_Weight,  a.SL, x.Party_Name, m.Party_Name, " +
        "IFNULL(DATE_FORMAT(d.Period_From, '%d-%m-%Y %H:%i'), '') " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfexpmovementreq c ON a.company_id = c.company_id AND a.branch_id = c.branch_id AND a.gate_out_id = c.gate_out_id " +
        "AND c.Container_no = a.Container_no " +
        "LEFT OUTER JOIN party p ON p.company_id = c.company_id AND c.shipping_agent = p.party_id AND p.branch_id = c.branch_id " +
        "LEFT OUTER JOIN party q ON q.company_id = c.company_id AND c.shipping_line = q.party_id AND c.branch_id = q.branch_id " +
        "LEFT OUTER JOIN cfstufftally d ON d.company_id = c.company_id AND d.branch_id = c.branch_id AND d.movement_req_id = c.movement_req_id " +
        "AND c.Container_no = d.Container_no " +
        "LEFT OUTER JOIN cfsb e ON d.company_id = e.company_id AND d.branch_id = e.branch_id AND d.sb_trans_id = e.sb_trans_id AND d.sb_no = e.sb_no " +
        "LEFT OUTER JOIN vessel v ON v.company_id = c.company_id AND c.vessel_id = v.vessel_id AND v.branch_id = c.branch_id " +
        "LEFT OUTER JOIN party tp ON tp.company_id = a.company_id AND a.Transporter = tp.party_id AND tp.branch_id = a.branch_id " +
        "LEFT OUTER JOIN party x ON x.company_id = a.company_id AND a.SL = x.party_id AND x.branch_id = a.branch_id " +
        "LEFT OUTER JOIN party m ON m.company_id = a.company_id AND a.SA = m.party_id AND m.branch_id = a.branch_id " +
        "LEFT OUTER JOIN party n ON n.company_id = c.company_id AND c.On_Account_Of = n.party_id AND n.branch_id = c.branch_id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND a.status = 'A' " +
        "AND a.Gate_Out_Date BETWEEN :startDate AND :endDate AND a.trans_type IN ('CONT', 'MOVE', 'BOWC') " +
        "AND a.Profitcentre_Id = 'N00004' " +
        "GROUP BY a.container_no, a.gate_out_id " +
        "ORDER BY a.Gate_Out_Date, a.container_no", nativeQuery = true)
List<Object[]> findExportMovementGateOutData(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate);






@Query(value = "SELECT DISTINCT a.Container_no, a.Container_Size, a.Container_type, "
        + "DATE_FORMAT(a.Gate_In_Date, '%d %b %Y %H:%i'), y.party_name, w.CUSTOMER_CODE, "
        + "y.CUSTOMER_CODE, DATE_FORMAT(e.Stuff_Tally_Date, '%d %b %Y %H:%i'), "
        + "DATE_FORMAT(a.Movement_req_Date, '%d %b %Y %H:%i'),'','', e.pol, "
        + "b.sb_type, x.Vessel_Name , e.Hold_Remarks "
        + "FROM cfexpinventory a "
        + "LEFT OUTER JOIN cfstufftally e ON a.Company_Id = e.Company_Id "
        + "AND a.Branch_id = e.Branch_id AND a.Container_No = e.Container_No "
        + "AND a.profitcentre_id = e.profitcentre_id AND e.status = 'A' "
        + "AND a.Stuff_Tally_Id = e.stuff_tally_id AND a.Stuff_req_Id = e.stuff_id "
        + "LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id "
        + "AND a.Branch_id = b.Branch_id AND a.SB_Trans_Id = b.SB_Trans_Id "
        + "AND a.profitcentre_id = b.profitcentre_id AND b.status = 'A' "
        + "AND a.SB_no = b.SB_no "
        + "LEFT OUTER JOIN party w ON a.Company_Id = w.Company_Id "
        + "AND a.sl = w.Party_Id AND a.branch_id = w.branch_id "
        + "LEFT OUTER JOIN party y ON a.Company_Id = y.Company_Id "
        + "AND a.sa = y.Party_Id AND a.branch_id = y.branch_id "
        + "LEFT OUTER JOIN vessel x ON e.Company_Id = x.Company_Id "
        + "AND e.Vessel_id = x.Vessel_Id AND e.branch_id = x.branch_id "
        + "WHERE a.company_id = :companyId AND a.branch_id = :branchId "
        + "AND a.ProfitCentre_Id = 'N00004' AND a.status = 'A' "
        + "AND (a.Gate_Out_Id = '' OR a.Gate_Out_Date > :endDate) "
        + "AND (a.Stuff_Req_Date != '0000-00-00 00:00:00' "
        + "AND a.Stuff_Req_Date < :endDate) AND a.Container_Status = 'MTY' "
        + "AND a.cycle != 'Hub' "
        + "GROUP BY a.container_no "
        + "UNION "
        + "SELECT DISTINCT a.Container_no, a.Container_Size, a.Container_type, "
        + "DATE_FORMAT(a.Gate_In_Date, '%d %b %Y %H:%i'), y.party_name, w.CUSTOMER_CODE, "
        + "y.CUSTOMER_CODE, DATE_FORMAT(e.Stuff_Tally_Date, '%d %b %Y %H:%i'), "
        + "DATE_FORMAT(a.Movement_req_Date, '%d %b %Y %H:%i'),'','',  e.pol, "
        + "a.cycle, x.Vessel_Name,e.Hold_Remarks "
        + "FROM cfexpinventory a "
        + "LEFT OUTER JOIN cfstufftally e ON a.Company_Id = e.Company_Id "
        + "AND a.Branch_id = e.Branch_id AND a.Container_No = e.Container_No "
        + "AND a.profitcentre_id = e.profitcentre_id AND e.status = 'A' "
        + "AND a.Stuff_Tally_Id = e.stuff_tally_id AND a.Stuff_req_Id = e.stuff_id "
        + "LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id "
        + "AND a.Branch_id = b.Branch_id AND a.SB_Trans_Id = b.SB_Trans_Id "
        + "AND a.profitcentre_id = b.profitcentre_id AND b.status = 'A' "
        + "AND a.SB_no = b.SB_no "
        + "LEFT OUTER JOIN party w ON a.Company_Id = w.Company_Id "
        + "AND a.sl = w.Party_Id AND a.branch_id = w.branch_id "
        + "LEFT OUTER JOIN party y ON a.Company_Id = y.Company_Id "
        + "AND a.sa = y.Party_Id AND a.branch_id = y.branch_id "
        + "LEFT OUTER JOIN vessel x ON e.Company_Id = x.Company_Id "
        + "AND e.Vessel_id = x.Vessel_Id AND e.branch_id = x.branch_id "
        + "WHERE a.company_id = :companyId AND a.branch_id = :branchId "
        + "AND a.ProfitCentre_Id = 'N00004' AND a.status = 'A' "
        + "AND (a.Gate_Out_Id = '' OR a.Gate_Out_Date > :endDate) "
        + "AND a.Container_Status IN ('LDD', 'FCL') "
        + "GROUP BY a.container_no", nativeQuery = true)
List<Object[]> findExportLDDPendencyContainerDetails(
		@Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("endDate") Date endDate);






@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type, " +
	       "DATE_FORMAT(a.in_Gate_In_Date, '%d %b %Y %H:%i'), p.party_name, n.party_name, " +
	       "p.CUSTOMER_CODE, i.Customs_Seal_No, i.VIA_NO, i.POD, i.POL, i.Gross_Weight, i.Tare_Weight, " +
	       "i.Stuff_Tally_Id, DATE_FORMAT(i.Stuff_Tally_Date, '%d %b %Y %H:%i'), v.Vessel_Name, '' AS emptyColumn, " +
	       "DATE_FORMAT(i.Stuff_Date, '%d %b %Y %H:%i'), a.Comments " +
	       "FROM cfstufftally i " +
	       "LEFT OUTER JOIN cfgatein a ON i.Company_Id = a.Company_Id AND i.Branch_id = a.Branch_id AND i.Container_no = a.Container_no " +
	       "AND i.Profitcentre_Id = a.Profitcentre_Id AND i.gate_In_id =a.gate_in_id " +
	       "LEFT OUTER JOIN cfsb b ON a.company_id = b.company_id AND b.SB_Trans_Id = a.erp_doc_ref_no AND a.Branch_Id = b.Branch_Id " +
	       "LEFT OUTER JOIN party p ON p.company_id = a.company_id AND a.sa = p.party_id AND p.branch_id = a.branch_id " +
	       "LEFT OUTER JOIN cfigm c ON c.company_id = b.company_id AND c.branch_id = b.branch_id AND a.Profitcentre_Id = c.Profitcentre_Id " +
	       "AND a.VIA_NO = c.VIA_NO AND a.Doc_Ref_Date = c.Doc_Date AND a.profitcentre_id = c.profitcentre_id " +
	       "LEFT OUTER JOIN party q ON q.company_id = a.company_id AND a.on_account_of = q.party_id AND q.branch_id = a.branch_id " +
	       "LEFT OUTER JOIN party n ON a.company_id = n.company_id AND a.sl = n.party_id AND a.branch_id = n.branch_id " +
	       "LEFT OUTER JOIN vessel v ON i.company_id = v.company_id AND i.Vessel_Id = v.Vessel_Id AND i.branch_id = v.branch_id " +
	       "LEFT OUTER JOIN party tp ON tp.company_id = a.company_id AND a.Transporter = tp.party_id AND tp.branch_id = a.branch_id " +
	       "WHERE a.company_id = :companyId AND a.branch_id = :branchId "
	       + " AND a.status = 'A' AND i.status = 'A' " +
	       "AND a.Gate_In_Type = 'EXP' AND a.Profitcentre_Id = 'N00004' " +
	       "AND i.Stuff_Tally_Date BETWEEN :startDate AND :endDate " +
	       "GROUP BY i.container_no " +
	       "ORDER BY i.Stuff_Tally_Date ",nativeQuery = true)
	List<Object[]> getStuffTallyData(@Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("startDate") Date startDate,
		    @Param("endDate") Date endDate);
	
//	@Query(value = "SELECT DISTINCT " +
//            "    b.container_no, " +
//            "    b.container_size, " +
//            "    b.container_type, " +
//            "    q.customer_code, " +
//            "    q.party_name, " +
//            "    b.Type_of_Container, " +
//            "    b.Hold_Status, " +
//            "    b.REFER, " +
//            "    DATE_FORMAT(b.Gate_in_Date, '%d %b %Y %T') AS gateInDate, " +
//            "    ch.party_name AS chaName, " +
//            "    d.importer_name " +
//            "FROM " +
//            "    cfigmcn b " +
//            "LEFT OUTER JOIN cfigm c " +
//            "    ON c.igm_trans_id = b.igm_trans_id " +
//            "    AND c.igm_no = b.igm_no " +
//            "    AND c.company_id = b.company_id " +
//            "    AND c.branch_id = b.branch_id " +
//            "    AND c.profitcentre_id = b.profitcentre_id " +
//            "LEFT OUTER JOIN cfigmcrg d " +
//            "    ON d.igm_trans_id = b.igm_trans_id " +
//            "    AND d.igm_no = b.igm_no " +
//            "    AND d.company_id = b.company_id " +
//            "    AND d.branch_id = b.branch_id " +
//            "    AND d.profitcentre_id = b.profitcentre_id " +
//            "    AND d.igm_line_no = b.igm_line_no " +
//            "LEFT OUTER JOIN vessel v " +
//            "    ON c.company_id = v.company_id " +
//            "    AND c.vessel_id = v.vessel_id " +
//            "    AND c.branch_id = v.branch_id " +
//            "LEFT OUTER JOIN party p " +
//            "    ON c.company_id = p.company_id " +
//            "    AND c.shipping_line = p.party_id " +
//            "    AND c.branch_id = p.branch_id " +
//            "LEFT OUTER JOIN party q " +
//            "    ON c.company_id = q.company_id " +
//            "    AND c.shipping_Agent = q.party_id " +
//            "    AND c.branch_id = q.branch_id " +
//            "LEFT OUTER JOIN party ch " +
//            "    ON b.company_id = ch.company_id " +
//            "    AND b.CHA = ch.party_id " +
//            "    AND b.branch_id = ch.branch_id " +
//            "WHERE " +
//            "    b.company_id = :companyId " +
//            "    AND b.branch_id = :branchId " +
//            "    AND b.status = 'A' " +
//            "    AND b.profitcentre_id = 'N00002' " +
//            "    AND b.Gate_In_Date < :date " +
//            "    AND (b.gate_out_id = '' OR b.gate_out_date > :date) " +
//            "    AND (b.de_stuff_id = '' OR b.de_stuff_Date > :date) " +
//            "    AND b.gate_in_id != '' " +
//            "    AND c.status = 'A' " +
//            "    AND d.status = 'A' " +
//            "    AND b.Type_of_Container ='Manual' " +
//            "GROUP BY " +
//            "    b.container_no, " +
//            "    b.igm_no " +
//            "ORDER BY " +
//            "    b.Gate_in_Date", 
//     nativeQuery = true)
//List<Object[]> findManualConContainerDetails(
// @Param("companyId") String companyId, 
// @Param("branchId") String branchId, 
// @Param("date") Date date
//);

	
	
	
	
	
@Query(value = "SELECT DISTINCT " + 
        "a.container_no, " + 
        "a.container_size, " + 
        "a.container_type, " + 
        "'Manual' AS agentName, " + 
        "'Manual' AS line, " + 
        "'Manual' AS agentCode, " + 
        "'Manual' AS origin, " + 
        "'Manual' AS remark, " + 
        "DATE_FORMAT(a.in_Gate_in_Date, '%d %b %Y %H:%i') AS inDate, " + 
        "'Manual' AS vessel, " + 
        "'Manual' AS pod " + 
        "FROM cfgatein a " + 
        "WHERE " + 
        "a.company_id = :companyId " + 
        "AND a.branch_id = :branchId " + 
        "AND a.Process_Id ='P00212' " + 
        "ORDER BY a.in_Gate_in_Date", 
       nativeQuery = true)
List<Object[]> findManualConContainerDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId
);

//@Query("SELECT COALESCE(SUM(c.inBondPackages), 0) FROM CfInBondGrid c " +
//	       "WHERE c.companyId = :companyId " +
//	       "AND c.branchId = :branchId " +
//	       "AND c.status != 'D' " +
//	       "AND c.inBondingId = :inBondingId " +
//	       "AND c.nocTransId = :nocTransId")
//BigDecimal  getCOuntOfNoc(@Param("companyId") String companyId,
//	                            @Param("branchId") String branchId,
//	                            @Param("inBondingId") String inBondingId,
//	                            @Param("nocTransId") String nocTransId);


@Query(value="SELECT COUNT(c.noc_Trans_Id), COALESCE(SUM(c.cargo_Duty), 0) FROM cfbondnoc c " +
	       "WHERE c.company_Id = :companyId " +
	       "AND c.branch_Id = :branchId " +
	       "AND c.status = 'A' " +
	       "AND c.noc_Trans_Date BETWEEN :startDate AND :endDate",nativeQuery = true)
	List<Object[]> getCountAndSumOfCargoDuty(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("startDate") Date startDate,
	    @Param("endDate") Date endDate
	);
	
	@Query(value="SELECT COUNT(c.In_Bonding_Id), COALESCE(SUM(c.cargo_Duty), 0) FROM cfinbondcrg c " +
		       "WHERE c.company_Id = :companyId " +
		       "AND c.branch_Id = :branchId " +
		       "AND c.status = 'A' " +
		       "AND c.In_Bonding_Date BETWEEN :startDate AND :endDate",nativeQuery = true)
		List<Object[]> getCountAndSumOfInbond(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("startDate") Date startDate,
		    @Param("endDate") Date endDate
		);

		
		
		@Query(value="SELECT COUNT(c.Ex_Bonding_Id), COALESCE(SUM(c.Ex_Bonded_Cargo_Duty), 0) FROM cfexbondcrg c " +
			       "WHERE c.company_Id = :companyId " +
			       "AND c.branch_Id = :branchId " +
			       "AND c.status = 'A' " +
			       "AND c.Ex_Bonding_Date BETWEEN :startDate AND :endDate",nativeQuery = true)
			List<Object[]> getCountAndSumOfExbond(
			    @Param("companyId") String companyId,
			    @Param("branchId") String branchId,
			    @Param("startDate") Date startDate,
			    @Param("endDate") Date endDate
			);
			
			
			
			
			
			
			
			@Query(value = 
				    "SELECT " + 
				    		 "DATE_FORMAT(a.NOC_Date, '%d-%b-%Y %H:%i') AS NOC_Date, " + 
				    "a.NOC_No, " + 
				    "a.importer_name, " + 
				    "p.party_name, " + 
				    "a.BOE_No, " + 
				    "DATE_FORMAT(a.boe_date, '%d-%b-%Y') AS boe_date, " + 
				    "a.NOC_Packages, " + 
				    "a.Gross_Weight, " + 
				    "a.Area, " + 
				    "a.cif_value, " + 
				    "a.cargo_duty, " + 
				    "DATE_FORMAT(a.NOC_Validity_Date, '%d-%b-%Y %H:%i') AS NOC_Validity_Date, " + 
				    "DATE_FORMAT(b.In_Bonding_Date,'%d-%b-%Y %H:%i' )," + 
				    "b.in_Bonded_Packages " + 
				    "FROM cfbondnoc a " + 
				    "LEFT OUTER JOIN cfinbondcrg b " + 
				    "ON a.company_id = b.company_Id " + 
				    "AND a.Branch_Id = b.Branch_Id " + 
				    "AND a.NOC_Trans_Id = b.NOC_Trans_Id " + 
				    "LEFT OUTER JOIN party p " + 
				    "ON a.company_id = p.company_Id " + 
				    "AND a.cha = p.party_id " + 
				    "AND a.branch_id = p.branch_id " + 
				    "WHERE " + 
				    "a.Company_Id = :companyId " + 
				    "AND a.Branch_Id = :branchId " + 
				    "AND a.status = 'A' " + 
				    "AND b.Status = 'A' " + 
				    "AND a.Noc_Trans_Date BETWEEN :startDate AND :endDate " + 
				    "ORDER BY a.Noc_Trans_Date", 
				    nativeQuery = true)
				List<Object[]> findNocData(
				        @Param("companyId") String companyId,
				        @Param("branchId") String branchId,
				        @Param("startDate") Date startDate,
				        @Param("endDate") Date endDate
				);

}
