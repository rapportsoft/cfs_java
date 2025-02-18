package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportBackToTown;
import com.cwms.entities.Party;

public interface ExportOperationalReportRepository extends JpaRepository<ExportBackToTown, String>{

	
	
	
	
	@Query(value = "SELECT p.party_id, p.party_name " +
            "FROM party p " +
            "WHERE p.company_id = :cid " +
            "AND p.branch_id = :bid " +
            "AND p.status != 'D' " +
            "AND p.exp = 'Y' " +
            "AND (:val IS NULL OR :val = '' OR p.party_name LIKE CONCAT(:val, '%'))", 
            nativeQuery = true)
List<Object[]> getAllExporter(
 @Param("cid") String cid,
 @Param("bid") String bid,
 @Param("val") String val
);
@Query(value ="SELECT DISTINCT p.party_id, p.party_name "
        + "FROM party p "
        + "WHERE p.company_Id = :companyId "
        + "AND p.branch_Id = :branchId "
        + "AND p.status != 'D' "
        + "AND (:partyName IS NULL OR :partyName = '' OR "
        + "p.party_Name LIKE concat(:partyName, '%'))",nativeQuery = true)
List<Object[]> getAllAccountHolder(@Param("companyId") String companyId, 
                                    @Param("branchId") String branchId, 
                                    @Param("partyName") String partyName);



	@Query(value = "SELECT DISTINCT b.SB_No, " +
			"DATE_FORMAT(b.SB_Date,'%d/%b/%Y') AS SB_Date, " +
            "c.Commodity, " +
            "d.Party_name AS CHA_Party_Name, " +
            "e.party_name AS On_Account_Of, " +
            "b.Exporter_Name, " +
            "IFNULL(c.No_Of_Packages,'0.00') AS No_Of_Packages, " +
            "IFNULL(c.gross_Weight,'0.00') AS Gross_Weight, " +
            "DATE_FORMAT(cr.Carting_Trans_Date,'%d/%b/%Y') AS CartingDate, " +
            "(c.Gate_in_Packages - c.stuff_req_qty - c.Back_To_Town_Pack) AS BalPkg, " +
           " '', "+
  "SUM(cr.Area_Occupied) AS Area_Occupied, " +
  "cg.Yard_Location ," +
  "cr.Grid_Location, " +
           
            "DATEDIFF(:endDate, cr.Carting_Trans_Date) AS Days_Difference, " +
            "p.port_name AS Port_Name, " +
            "IFNULL(c.FOB,'0.00') AS FOB, " +
            "IFNULL(cr.Grid_Block,'') AS Grid_Block, " +
            "cr.Grid_Location, " +
            "IFNULL(cr.Grid_Location,'') AS Grid_Location " +
            "FROM cfgatein a " +
            "LEFT OUTER JOIN cfsb b ON b.Company_id = a.Company_id AND " +
            "b.branch_id = a.branch_id AND " +
            "b.profitcentre_id = a.profitcentre_id AND " +
            "a.erp_doc_ref_no = b.SB_Trans_Id " +
            "LEFT OUTER JOIN cfsbcrg c ON c.Company_id = b.Company_id AND " +
            "c.branch_id = b.branch_id AND " +
            "c.profitcentre_id = b.profitcentre_id AND " +
            "c.SB_Trans_Id = b.SB_Trans_Id AND " +
            "c.SB_No = b.SB_No AND " +
            "c.SB_Line_No = a.Line_No " +
            "LEFT OUTER JOIN party d ON a.Company_id = d.Company_id AND " +
            " d.Party_Id = b.CHA AND a.branch_id =d.branch_id " +
            "LEFT OUTER JOIN port p ON b.company_id = p.company_id AND " +
            "b.pod = p.port_code AND b.branch_id =p.branch_id " +
            "LEFT OUTER JOIN party e ON b.Company_id = e.Company_id AND " +
            "b.On_Account_Of = e.Party_Id AND b.branch_id =e.branch_id " +
            "LEFT OUTER JOIN cfcrtg cr ON c.company_id = cr.company_id AND " +
            "c.branch_id = cr.branch_id AND " +
            "c.fin_year = cr.fin_year AND " +
            "c.sb_trans_id = cr.sb_trans_id AND " +
            "c.sb_no = cr.sb_no AND " +
            "c.profitcentre_id = cr.profitcentre_id AND " +
            "c.sb_line_no = cr.sb_line_no " +
            "LEFT OUTER JOIN cfimpexpgrid cg ON cg.company_id = cr.company_id AND " +
            "cg.branch_id = cr.branch_id AND " +
            "cg.process_trans_id = cr.carting_trans_id " +
            "WHERE a.company_id = :companyId AND " +
            "a.branch_id = :branchId AND " +
            "(:sbNo IS NULL OR :sbNo = '' OR c.SB_No = :sbNo) AND " +
//            "(:bookingNo IS NULL OR :bookingNo = '' OR c.booking_No = :bookingNo) AND " +
            // Uncomment the next line if date filtering is needed
            // "(c.inGateInDate BETWEEN :startDate AND :endDate) AND " +
            "(:exporterName IS NULL OR :exporterName = '' OR b.exporter_id = :exporterName) AND " +
            "(:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) AND " +
            "(:cha IS NULL OR :cha = '' OR b.cha = :cha) AND " +
            "cr.Carting_Trans_Date <= :endDate AND " +
            "a.profitcentre_id = 'N00004' AND " +
            "b.status = 'A' AND " +
            "c.status = 'A' AND " +
            "a.Status = 'A' AND " +
            "((c.no_of_packages - (c.stuffed_qty + c.back_to_town_pack)) > 0 AND " +
            "(c.gate_in_packages - (c.stuffed_qty + c.back_to_town_pack)) > 0) " +
            "GROUP BY b.sb_trans_id " +
            "ORDER BY b.sb_trans_date",
    nativeQuery = true)
List<Object[]> getCustomReport(
  @Param("companyId") String companyId,
  @Param("branchId") String branchId,
  // Uncomment the next line if startDate is used
  // @Param("startDate") Date startDate,
  @Param("endDate") Date endDate,
  @Param("sbNo") String sbNo,
//  @Param("bookingNo") String bookingNo,
  @Param("exporterName") String exporterName,
  @Param("accountHolderId") String accountHolderId,
  @Param("cha") String cha);



@Query(value = "SELECT m.Party_Name, " +
        "DATE_FORMAT(a.Carting_Trans_Date,'%d %b %Y %T'), " +
        "a.Vehicle_No, " +
        "IFNULL(a.Actual_No_Of_Packages,'0.00'), " +
        "IFNULL(a.Actual_No_Of_Weight,'0.00'), " +
        "a.SB_No, " +
        "DATE_FORMAT(a.SB_Date,'%d %b %Y %T'), " +
        "IFNULL(c.No_Of_Packages,'0.00'), " +
        "IFNULL(c.Gross_Weight,'0.00'), " +
        "IFNULL(a.Area_Occupied,'0.00'), " +
        "a.Grid_Location, " +
        "b.Exporter_Name, " +
        "n.Party_Name, " +
        "a.Commodity, " +
        "b.POD, " +
        "a.Damage_Comments " +
        "FROM cfcrtg a " +
        "LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "AND a.SB_No = b.SB_No " +
        "LEFT OUTER JOIN cfsbcrg c ON a.Company_Id = c.Company_Id " +
        "AND a.Branch_Id = c.Branch_Id " +
        "AND a.Profitcentre_Id = c.Profitcentre_Id " +
        "AND a.SB_Trans_Id = c.SB_Trans_Id " +
        "AND a.SB_No = c.SB_No " +
        "LEFT OUTER JOIN party m ON b.Company_Id = m.Company_Id " +
        "AND b.On_Account_Of = m.Party_Id AND b.branch_id =m.branch_id " +
        "LEFT OUTER JOIN party n ON b.Company_Id = n.Company_Id " +
        "AND b.CHA = n.Party_Id AND b.branch_id =n.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Carting_Trans_Date BETWEEN :startDate AND :endDate " +
        "AND (:sbNo IS NULL OR :sbNo = '' OR c.SB_No = :sbNo) " +
        "AND (:exporterName IS NULL OR :exporterName = '' OR b.Exporter_Id = :exporterName) " +
        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) " +
        "AND (:cha IS NULL OR :cha = '' OR b.CHA = :cha) " +
        "GROUP BY a.Carting_Trans_Id, a.SB_No, a.SB_Trans_Id",
 nativeQuery = true)
List<Object[]> findTruckWiseCartingReport(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("startDate") Date startDate,
@Param("endDate") Date endDate,
@Param("sbNo") String sbNo,
@Param("exporterName") String exporterName,
@Param("accountHolderId") String accountHolderId,
@Param("cha") String cha
);





@Query(value = "SELECT DISTINCT b.Container_No, b.Container_Size, b.Container_Type, m.Party_Name AS Shipping_Line, " +
        "DATE_FORMAT(a.In_Gate_In_Date,'%d/%m/%y %H:%i'), DATE_FORMAT(b.Gate_Out_Date,'%d/%m/%y %H:%i'), a.Gate_In_Type,a.Delivery_Order_No,a.Container_Seal_No,n.Party_Name AS On_Account_Of,  a.Transporter_Name, " +
        "  a.Origin, a.Comments, a.On_Account_Of, b.CHA " +
        "FROM cfgatein a " +
        "LEFT OUTER JOIN cfexpinventory b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "AND a.Container_No = b.Container_No " +
        "AND a.Gate_In_Id = b.Gate_In_Id " +
        "LEFT OUTER JOIN party m ON a.Company_Id = m.Company_Id AND a.SL = m.Party_Id AND a.branch_id = m.branch_id " +
        "LEFT OUTER JOIN party n ON a.Company_Id = n.Company_Id AND a.On_Account_Of = n.Party_Id AND a.branch_id = n.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Container_No != '' " +
        "AND a.Process_Id = 'P00219' " +
        "AND a.Profitcentre_Id = 'N00004' " +
        "AND a.In_Gate_In_Date BETWEEN :startDate AND :endDate " +
        "AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " +
        "AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
        "AND (:exporterName IS NULL OR :exporterName = '' OR a.sl = :exporterName) " +
        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR a.On_Account_Of = :accountHolderId) " +
                "AND (:cha IS NULL OR :cha = '' OR a.CHA = :cha) " ,
        nativeQuery = true)
List<Object[]> findContainerDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate,
    @Param("sbNo") String sbNo,
    @Param("bookingNo") String bookingNo,
    @Param("exporterName") String exporterName,
    @Param("accountHolderId") String accountHolderId,
    @Param("cha") String cha
);



@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type, a.Commodity_Description, " +
        "DATE_FORMAT(d.Stuff_Tally_Date, '%d-%m-%Y %H:%i'), DATE_FORMAT(a.Gate_Out_Date, '%d-%m-%Y %H:%i'), " +
        "m.party_Name, n.Party_Name, v.vessel_Name, d.POL, d.Customs_Seal_No, d.Agent_Seal_No, d.VIA_No, " +
        "IFNULL(d.Stuffed_Qty, '0.00'), IFNULL(d.Cargo_weight, '0.00'), IFNULL(d.Tare_Weight, '0.00'), " +
        "a.Transporter_Name, '', a.Vehicle_No, DATE_FORMAT(d.Period_From, '%d-%m-%Y %H:%i'), a.Location, " +
        "d.MOVEMENT_TYPE, a.Comments, c.Invoice_No, d.On_Account_Of, a.SL " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfexpmovementreq c ON a.company_id = c.company_id AND a.branch_id = c.branch_id " +
        "AND a.gate_out_id = c.gate_out_id AND c.Container_no = a.Container_no AND c.Profitcentre_Id = a.Profitcentre_Id " +
        "LEFT OUTER JOIN party m ON c.Company_Id = m.company_id AND c.On_Account_Of = m.party_id AND c.branch_id =m.branch_id " +
        "LEFT OUTER JOIN party n ON n.company_id = c.company_id AND c.shipping_line = n.party_id AND n.branch_id =c.branch_id " +
        "LEFT OUTER JOIN cfstufftally d ON d.company_id = c.company_id AND d.branch_id = c.branch_id " +
        "AND d.movement_req_id = c.movement_req_id AND c.Container_no = d.Container_no AND d.Profitcentre_Id = c.Profitcentre_Id " +
        "LEFT OUTER JOIN vessel v ON a.Company_Id = v.Company_Id AND a.Vessel_Id = v.Vessel_Id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId " +
        "AND a.Status = 'A' "
        + "AND a.Gate_Out_Date BETWEEN :startDate AND :endDate " +
        "AND a.process_id = 'P00223' AND a.trans_type IN ('CONT', 'MOVE', 'BOWC') " +
        "AND a.Status != 'D' AND a.Profitcentre_Id IN ('N00004') " +
        "AND (:sbNo IS NULL OR :sbNo = '' OR c.SB_No = :sbNo) " +
        "AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
//        "AND (:exporterName IS NULL OR :exporterName = '' OR a.sl = :exporterName) " +
        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR a.On_Account_Of = :accountHolderId) " +
                "AND (:cha IS NULL OR :cha = '' OR a.CHA = :cha) " +
        "ORDER BY a.Gate_Out_Date", nativeQuery = true)
List<Object[]> findEmptyGateOutContainerDetails(
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate, 
        @Param("sbNo") String sbNo,
        @Param("bookingNo") String bookingNo,
//        @Param("exporterName") String exporterName,
        @Param("accountHolderId") String accountHolderId,
        @Param("cha") String cha );

@Query(value = "SELECT DISTINCT a.Container_No, a.Container_Size, a.Container_Type, " +
        "DATE_FORMAT(a.Gate_In_Date, '%d/%m/%Y %H:%i'), " +
        "DATE_FORMAT(a.De_Stuff_Date, '%d/%b/%Y %H:%i') AS Carting_Trans_Date, " +
        "IFNULL(a.Yard_Packages, '0.00') AS Yard_Packages, " +
        "IFNULL(a.Gross_Weight, '0.00') AS Gross_Weight, " +
        "sl.Party_Name, a.Container_Search_Type, p.Party_Name, a.On_Account_Of " +
        "FROM cfcrtgcn a " +
        "LEFT OUTER JOIN Party p ON a.Company_id = p.Company_Id AND a.branch_id =p.branch_id AND a.On_Account_Of = p.Party_Id " +
        "LEFT OUTER JOIN Party sl ON a.Company_id = sl.Company_Id AND a.branch_id =sl.branch_id AND  a.shipping_agent = sl.Party_Id " +
        "LEFT OUTER JOIN cfcrtg b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id AND a.Gate_In_Id = b.Gate_In_Id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND a.status = 'A' "+
         "AND a.Gate_In_Date BETWEEN :startDate AND :endDate " +
         "AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " + 
         "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR a.On_Account_Of = :accountHolderId) " , 
        nativeQuery = true)
List<Object[]> findReworkingContainerDetails(@Param("companyId") String companyId, 
                                    @Param("branchId") String branchId, @Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate, 
                                    @Param("sbNo") String sbNo,
                                    @Param("accountHolderId") String accountHolderId);





@Query(value = 
"SELECT DISTINCT a.Container_No, " +
"                a.Container_Size, " +
"                a.Container_Type, " +
"                a.iso_code, " +
"                DATE_FORMAT(a.Gate_In_Date, '%d/%m/%y %H:%i') AS Empty_In_Date, " +
"                DATE_FORMAT(a.Gate_Out_Date, '%d/%m/%y %H:%i') AS Empty_Out_Date, " +
"                a.Vehicle_No, " +
"                a.Transporter_Name, " +
"                p.Party_Name, " +
"                p1.Party_Name AS SL, " +
"                '', " +
"                a.Gate_In_Type, " +
"                a.container_health, " +
"                a.Comments, " +
"                a.Delivery_Order_No, " +
"                a.Exporter_Name, " +
"                a.Location, " +
"                a.Comments " +
"FROM cfgateout a " +
"LEFT OUTER JOIN cfcommongatepass b " +
"    ON a.Company_Id = b.Company_Id " +
"    AND a.Branch_Id = b.Branch_Id " +
"    AND a.Gate_Out_Id = b.Gate_Out_Id " +
"    AND a.Profitcentre_Id = b.Profitcentre_Id " +
"LEFT OUTER JOIN party p " +
"    ON a.company_id = p.company_id " +
"    AND a.On_Account_Of = p.Party_Id AND a.branch_id =p.branch_id " +
"LEFT OUTER JOIN party p1 " +
"    ON a.company_id = p1.company_id " +
"    AND a.SL = p1.Party_Id AND a.branch_id =p1.branch_id " +
"WHERE a.company_id = :companyId " +
"  AND a.branch_id = :branchId " +
"  AND a.Profitcentre_Id = 'N00004' " +
"  AND a.Process_Id = 'P00223' " +
"  AND a.trans_type = 'MTY' " +
"  AND a.Gate_Out_Date BETWEEN :startDate AND :endDate " +
"ORDER BY a.Gate_Out_Date "+
"AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
"AND (:accountHolderId IS NULL OR :accountHolderId = '' OR a.On_Account_Of = :accountHolderId) " +
      "AND (:cha IS NULL OR :cha = '' OR a.CHA = :cha) ",
nativeQuery = true)
List<Object[]> fetchExporGateInAndGateOutDetails(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("startDate") Date startDate,
@Param("endDate") Date endDate,
@Param("bookingNo") String bookingNo,
//@Param("exporterName") String exporterName,
@Param("accountHolderId") String accountHolderId,
@Param("cha") String cha );












@Query(value = "SELECT a.SB_No, " +
        "DATE_FORMAT(a.SB_Date, '%d-%m-%Y %H:%i') as SB_Date, " +
        "d.Party_Name as chaname, " +
        "e.Party_Name as accntholder, " +
        "b.Exporter_Name, " +
        "b.Consignee_Name, " +
        "IFNULL(c.gross_weight, '0.00'), " +
        "IFNULL(a.Damage_Comments, '') as remark, " +
        "DATE_FORMAT(a.Carting_Trans_Date, '%d-%m-%Y %H:%i') as Carting_Date, " +
        "a.Commodity, " +
        "IFNULL(a.Area_Occupied, '0.00'), " +
        "a.Grid_Location, " +
        "IFNULL(c.No_Of_Packages, '0.00'), " +
        "IFNULL(a.Actual_No_Of_Packages, '0.00'), " +
        "IFNULL(a.Actual_No_Of_Weight, '0.00'), " +
        "IFNULL(a.Area_Occupied, '0.00') as unloadarea, " +
        "b.POD, " +
        "cq.Equipment as equipment, " +
        "IFNULL(c.FOB, '0.00') ," +
        "a.Carting_Trans_Id "+
        "FROM cfcrtg a " +
        "LEFT OUTER JOIN cfsbcrg c ON c.Company_Id = a.Company_Id AND c.Branch_Id = a.Branch_Id AND c.SB_Trans_Id = a.SB_Trans_Id AND c.SB_Line_No = a.SB_Line_No AND c.SB_No = a.SB_No " +
        "LEFT OUTER JOIN cfsb b ON b.company_id = c.company_id AND b.Branch_Id = c.Branch_Id AND b.SB_Trans_Id = c.SB_Trans_Id AND b.SB_No = c.SB_No " +
        "LEFT OUTER JOIN party d ON b.company_id = d.company_id AND b.cha = d.party_id AND b.branch_id =d.branch_id " +
        "LEFT OUTER JOIN party e ON b.company_id = e.company_id AND b.on_account_of = e.party_id AND b.branch_id =e.branch_id " +
        "LEFT OUTER JOIN cfEquipmentActivity cq ON a.company_id = cq.company_id AND a.Carting_Trans_Id = cq.De_Stuff_Id " +
        "WHERE a.company_id =:companyId AND a.branch_id = :branchId AND"
        + " a.Carting_Trans_Date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' "+
        "AND (:sbNo IS NULL OR :sbNo = '' OR a.SB_No = :sbNo) " +
        "AND (:exporterName IS NULL OR :exporterName = '' OR b.Exporter_Id = :exporterName) " +
        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) " +
                "AND (:cha IS NULL OR :cha = '' OR b.CHA = :cha) " , nativeQuery = true)
List<Object[]> findExportCartingData(@Param("companyId") String companyId,
		@Param("branchId") String branchId,
		@Param("startDate") Date startDate, 
		@Param("endDate") Date endDate,
		@Param("sbNo") String sbNo,
		@Param("exporterName") String exporterName,
		@Param("accountHolderId") String accountHolderId,
		@Param("cha") String cha);












@Query(value = "SELECT b.Gate_In_Type, '' as work_order_no, '' as work_order_date, " +
        "DATE_FORMAT(b.in_Gate_In_Date,'%d-%m-%Y %H:%i') as gateInDate, b.Vehicle_No, b.Container_No, " +
        "b.Container_Size, b.Container_Type, IFNULL((b.Gross_Weight - b.Tare_Weight), '0.00') as cargoWeight, " +
        "IFNULL(b.Tare_Weight, '0.00') as tareWeight, o.Party_Name as accountHolder, n.Party_Name as chaName, " +
        "m.Party_Name as lineName,'','', b.Container_Health, DATE_FORMAT(v.Gate_Out_Date, '%d-%m-%Y %H:%i') as gateOutDate, " +
        "d.SB_No, DATE_FORMAT(d.SB_Date, '%d-%m-%Y %H:%i') as sbDate, IFNULL(SUM(st.stuffed_qty), '0.00') as stuffedQty, " +
        "IFNULL(SUM(st.cargo_weight), '0.00') as cargoWeightStuffed, vs.vessel_name, st.POD as pod, " +
        "st.POL as pol, st.agent_seal_no, st.customs_seal_no, st.Gate_Out_Id, b.Gate_In_Id " +
        "FROM cfgatein b " +
        "LEFT OUTER JOIN cfexpinventory v ON b.Company_Id = v.Company_Id AND b.Branch_Id = v.Branch_Id " +
        "AND b.Container_No = v.Container_No AND b.Profitcentre_Id = v.Profitcentre_Id AND b.Gate_In_Id = v.Gate_In_Id " +
        "LEFT OUTER JOIN cfsb d ON v.Company_Id = d.Company_Id AND v.Branch_Id = d.Branch_Id AND v.Profitcentre_Id = d.Profitcentre_Id " +
        "AND v.SB_Trans_Id = d.SB_Trans_Id AND v.SB_No = d.SB_No " +
        "LEFT OUTER JOIN cfstufftally st ON b.Company_Id = st.Company_Id AND b.Branch_Id = st.Branch_Id " +
        "AND b.Gate_In_Id = st.Carting_Trans_Id AND b.Stuff_Tally_Id = st.Stuff_Tally_Id " +
        "LEFT OUTER JOIN cfexpmovementreq mq ON mq.Company_Id = st.Company_Id AND mq.Branch_Id = st.Branch_Id " +
        "AND mq.Movement_Req_Id = st.Movement_Req_Id AND mq.Stuff_Tally_Id = st.Stuff_Tally_Id " +
        "LEFT OUTER JOIN cfgateout c ON mq.Company_Id = c.Company_Id AND mq.Branch_Id = c.Branch_Id " +
        "AND mq.Profitcentre_Id = c.Profitcentre_Id AND mq.Gate_Out_Id = c.Gate_Out_Id AND mq.Container_No = c.Container_No " +
        "LEFT OUTER JOIN party m ON b.Company_Id = m.Company_Id AND b.branch_id =m.branch_id AND b.SL = m.Party_Id " +
        "LEFT OUTER JOIN Party o ON b.Company_Id = o.Company_Id AND b.branch_id =o.branch_id AND b.On_Account_Of = o.Party_Id " +
        "LEFT OUTER JOIN party n ON b.Company_Id = n.Company_Id AND b.branch_id =n.branch_id AND b.CHA = n.Party_Id " +
        "LEFT OUTER JOIN vessel vs ON st.company_id = vs.company_id AND st.vessel_id = vs.vessel_id AND st.branch_id = vs.branch_id " +
        "WHERE b.company_id = :companyId AND b.branch_id = :branchId AND "
        + "b.status = 'A' " +
        "AND b.in_gate_in_date BETWEEN :startDate AND :endDate " +
        "AND b.Gate_In_Type IN ('Buffer', 'ONWH', 'FDS') "
//        + "AND b.Process_Id = 'P00223' "
        + "AND b.Profitcentre_Id = 'N00004' " +
        "AND (:sbNo IS NULL OR :sbNo = '' OR d.SB_No = :sbNo) " +
        "AND (:bookingNo IS NULL OR :bookingNo = '' OR b.Delivery_Order_No = :bookingNo) " +
        "AND (:exporterName IS NULL OR :exporterName = '' OR d.exporter_id = :exporterName) " +
        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) " +
        "AND (:cha IS NULL OR :cha = '' OR b.CHA = :cha) " +
        "GROUP BY b.Container_No, b.Gate_In_Id ORDER BY b.in_Gate_In_Date ASC", nativeQuery = true)
List<Object[]> exportFactoryStuffGateInReport(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("startDate") Date startDate, 
    @Param("endDate") Date endDate,
    @Param("sbNo") String sbNo,
    @Param("bookingNo") String bookingNo,
	@Param("exporterName") String exporterName,
    @Param("accountHolderId") String accountHolderId,
    @Param("cha") String cha);






@Query(value = "SELECT DISTINCT pr.Party_Name AS accountholder, a.container_no, a.container_size, a.container_type, " +
        "DATE_FORMAT(a.In_Gate_in_Date,'%d %b %Y %H:%i') AS gateInDate, " +
        "DATE_FORMAT(i.Stuff_Tally_Date,'%d %b %Y %H:%i') AS stuffTallyDate, " +
        "DATE_FORMAT(i.Gate_Out_Date,'%d %b %Y %H:%i') AS gateOutDate, i.POD, v.Vessel_Name, eq.Equipment, i.POL, " +
        "SUM(IFNULL(i.Stuffed_Qty, '0.00')) AS stuffedQty, '', " +
        "SUM(IFNULL(i.Total_GW, '0.00')) AS totalGW, i.VIA_NO, i.Cover_Details AS rotationNo, i.Voyage_No, " +
        "n.party_name AS shippingline, i.Customs_Seal_No, i.Agent_Seal_No, p.party_name AS shippingagent, " +
        "DATE_FORMAT(i.Period_From,'%d %b %Y %H:%i') AS periodFrom, i.On_Account_Of " +
        "FROM cfstufftally i " +
        "LEFT OUTER JOIN cfgatein a ON i.Company_Id = a.Company_Id AND i.Branch_id = a.Branch_id AND i.Container_no = a.Container_no " +
        "AND i.Profitcentre_Id = a.Profitcentre_Id " +
//        + "AND DATE_FORMAT(i.Period_From,'%d %b %Y') = DATE_FORMAT(a.in_gate_in_date,'%d %b %Y') " +
        "AND i.gate_in_id = a.gate_in_id  " +
        "LEFT OUTER JOIN cfsb b ON a.company_id = b.company_id AND b.SB_Trans_Id = a.erp_doc_ref_no AND a.Branch_Id = b.Branch_Id " +
        "LEFT OUTER JOIN party p ON p.company_id = a.company_id AND a.sa = p.party_id AND p.branch_id =a.branch_id " +
//        "LEFT OUTER JOIN cfigm c ON c.company_id = b.company_id AND c.branch_id = b.branch_id AND a.Profitcentre_Id = c.Profitcentre_Id " +
//        "AND a.VIA_NO = c.VIA_NO AND a.Doc_Ref_Date = c.Doc_Date AND a.profitcentre_id = c.profitcentre_id " +
        "LEFT OUTER JOIN party q ON q.company_id = a.company_id AND a.on_account_of = q.party_id AND q.branch_id =a.branch_id " +
        "LEFT OUTER JOIN party n ON a.company_id = n.company_id AND a.sl = n.party_id AND a.branch_id =n.branch_id " +
        "LEFT OUTER JOIN vessel v ON i.company_id = v.company_id AND i.Vessel_Id = v.Vessel_Id  AND i.branch_id =v.branch_id " +
        "LEFT OUTER JOIN party tp ON tp.company_id = a.company_id AND a.Transporter = tp.party_id AND tp.branch_id =a.branch_id " +
        "LEFT OUTER JOIN party pr ON pr.Company_Id = i.Company_Id AND pr.Party_Id = i.On_Account_Of  AND pr.branch_id =i.branch_id " +
        "LEFT OUTER JOIN cfequipmentactivity eq ON i.company_id = eq.company_id AND i.branch_id = eq.branch_id " +
        "AND i.stuff_id = eq.de_stuff_id " +
        "WHERE a.company_id = :companyId AND a.branch_id = :branchId AND a.Profitcentre_Id ='N00004' " +
        "AND i.MOVEMENT_TYPE IN ('CLP') " +
        "AND i.container_no != '' "
        + "AND i.Stuff_Tally_Date BETWEEN :startDate AND :endDate " +
        "AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " +
        "AND (:bookingNo IS NULL OR :bookingNo = '' OR a.Delivery_Order_No = :bookingNo) " +
        "AND (:exporterName IS NULL OR :exporterName = '' OR b.exporter_id = :exporterName) " +
        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR a.On_Account_Of = :accountHolderId) " +
        "AND (:cha IS NULL OR :cha = '' OR a.CHA = :cha) " +
        "GROUP BY i.container_no " +
        "ORDER BY i.Stuff_Tally_Date", nativeQuery = true)
List<Object[]> exportStuffGateInReport(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("startDate") Date startDate, 
    @Param("endDate") Date endDate,
    @Param("sbNo") String sbNo,
    @Param("bookingNo") String bookingNo,
    @Param("exporterName") String exporterName,
    @Param("accountHolderId") String accountHolderId,
    @Param("cha") String cha);

@Query(value = 
"SELECT DISTINCT " +
"c.SB_No AS sbNo, " +
"IFNULL(DATE_FORMAT(c.Stuff_Tally_Date, '%d-%b-%Y'), '') AS stuffTallyDate, " +
"sb.No_Of_Packages AS pack, " +
"SUM(c.Stuffed_Qty) AS stuffPack, " +
"sb.Type_Of_Package AS typeOfPackage, " +
"cb.MPCIN AS mpcin, " +
"a.Container_No AS containerNo, " +
"a.Container_Size AS containerSize, " +
"IFNULL(DATE_FORMAT(c.SF_Job_Date, '%d-%b-%Y'), '') AS sfJobDate, " +
"c.Customs_Seal_No AS customsSealNo, " +
"c.Agent_Seal_No AS agentSealNo, " +
"c.ASR_Container_Status AS asrContainerStatus, " +
"'' AS emptyField1, " +
"'' AS emptyField2, " +
"IFNULL(DATE_FORMAT(a.Gate_Out_Date, '%d-%b-%Y'), '') AS gateOutDate, " +
"IFNULL(DATE_FORMAT(c.DP_Job_Date, '%d-%b-%Y'), '') AS dpJobDate, " +
"IFNULL(DATE_FORMAT(c.SB_Date, '%d-%b-%Y'), '') AS sbDate " +
"FROM cfgateout a " +
"LEFT OUTER JOIN cfexportgatepass b ON a.Company_Id = b.Company_Id " +
"AND a.Branch_Id = b.Branch_Id " +
"AND a.Profitcentre_Id = b.Profitcentre_Id " +
"AND a.Gate_Pass_No = b.Gate_Pass_Id " +
"AND a.Container_No = b.Container_No " +
"AND a.Gate_Out_Id = b.Gate_Out_Id " +
"LEFT OUTER JOIN cfstufftally c ON c.Company_Id = b.Company_Id " +
"AND c.Branch_Id = b.Branch_Id " +
"AND c.Profitcentre_Id = b.Profitcentre_Id " +
"AND c.Movement_Req_Id = b.Movement_Req_Id " +
"AND c.Container_No = b.Container_No " +
"AND c.Stuff_Tally_Id = b.Stuff_Tally_Id " +
"LEFT OUTER JOIN cfsbcrg sb ON c.Company_Id = sb.Company_Id " +
"AND c.Branch_Id = sb.Branch_Id " +
"AND c.Profitcentre_Id = sb.Profitcentre_Id " +
"AND c.SB_Trans_Id = sb.SB_Trans_Id " +
"AND c.SB_No = sb.SB_No " +
"AND c.SB_Line_Id = sb.SB_Line_No " +
"LEFT OUTER JOIN cfsb cb ON cb.Company_Id = sb.Company_Id " +
"AND cb.Branch_Id = sb.Branch_Id " +
"AND cb.Profitcentre_Id = sb.Profitcentre_Id " +
"AND cb.SB_Trans_Id = sb.SB_Trans_Id " +
"AND cb.SB_No = sb.SB_No " +
"WHERE a.Company_Id = :companyId " +
"AND a.Branch_Id = :branchId " +
"AND a.Profitcentre_Id IN ('N00004') " +
"AND a.trans_type IN ('CONT', 'MOVE', 'BOWC') " +
"AND a.Container_no != '' " +
"AND a.Gate_Out_Date BETWEEN :startDate AND :endDate " +
"AND a.Status = 'A' " +
"AND c.Status = 'A' " +
"AND b.Status = 'A' " +
"AND a.Gate_Out_Id != '' " +
"AND b.Container_No != '' " +
"AND (:sbNo IS NULL OR :sbNo = '' OR cb.SB_No = :sbNo) " +
"AND (:bookingNo IS NULL OR :bookingNo = '' OR a.Delivery_Order_No = :bookingNo) " +
"AND (:exporterName IS NULL OR :exporterName = '' OR cb.exporter_id = :exporterName) " +
"AND (:accountHolderId IS NULL OR :accountHolderId = '' OR cb.On_Account_Of = :accountHolderId) " +
"AND (:cha IS NULL OR :cha = '' OR cb.CHA = :cha) " +
"GROUP BY c.Stuff_Tally_Id, a.Container_No, c.SB_Trans_Id, c.SB_No " +
"ORDER BY c.Stuff_Tally_Date",
nativeQuery = true)
List<Object[]> findExportGateOutBasedData(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId, 
    @Param("startDate") Date startDate, 
    @Param("endDate") Date endDate,
    @Param("sbNo") String sbNo,
    @Param("bookingNo") String bookingNo,
    @Param("exporterName") String exporterName,
    @Param("accountHolderId") String accountHolderId,
    @Param("cha") String cha);




@Query(value = 
"SELECT DISTINCT " +
"pr.party_name AS accountHolder, " +
"a.container_no, " +
"a.container_size, " +
"a.container_type, " +
"DATE_FORMAT(i.Period_From, '%d %b %Y %H:%i') AS periodFrom, " +
"DATE_FORMAT(i.Stuff_Tally_Date, '%d %b %Y %H:%i') AS stuffTallyDate, " +
"DATE_FORMAT(i.Gate_Out_Date, '%d %b %Y %H:%i') AS gateOutDate, " +
"i.POD, " +
"v.Vessel_Name AS vesselName, " +
"eq.Equipment AS equipment, " +
"i.POL, " +
"IFNULL(i.Stuffed_Qty, '0.00') AS stuffedQty, " +
"a.comments, " +
"IFNULL(i.Total_GW, '0.00') AS totalGW, " +
"i.VIA_NO, " +
"i.Cover_Details AS rotationNo, " +
"i.Voyage_No, " +
"n.party_name AS shippingLine, " +
"i.Customs_Seal_No AS customsSealNo, " +
"i.Agent_Seal_No AS agentSealNo, " +
"p.Party_Name AS shippingAgent, " +
"i.On_Account_Of AS onAccountOf " +
"FROM cfstufftally i " +
"LEFT OUTER JOIN cfgatein a " +
"ON i.Company_Id = a.Company_Id " +
"AND i.Branch_id = a.Branch_id " +
"AND i.Container_no = a.Container_no " +
"AND i.Profitcentre_Id = a.Profitcentre_Id " +
"AND DATE_FORMAT(i.Period_From, '%d %b %Y') = DATE_FORMAT(a.in_gate_in_date, '%d %b %Y') " +
"LEFT OUTER JOIN cfsb b " +
"ON a.company_id = b.company_id " +
"AND b.SB_Trans_Id = a.erp_doc_ref_no " +
"AND a.Branch_Id = b.Branch_Id " +
"LEFT OUTER JOIN party p " +
"ON p.company_id = a.company_id " +
"AND a.sa = p.party_id AND a.branch_id =p.branch_id " +
"LEFT OUTER JOIN cfigm c " +
"ON c.company_id = b.company_id " +
"AND c.branch_id = b.branch_id " +
"AND a.Profitcentre_Id = c.Profitcentre_Id " +
"AND a.VIA_NO = c.VIA_NO " +
"AND a.Doc_Ref_Date = c.Doc_Date " +
"AND a.profitcentre_id = c.profitcentre_id " +
"LEFT OUTER JOIN party q " +
"ON q.company_id = a.company_id " +
"AND a.on_account_of = q.party_id AND a.branch_id =q.branch_id " +
"LEFT OUTER JOIN party n " +
"ON a.company_id = n.company_id " +
"AND a.sl = n.party_id " +
"LEFT OUTER JOIN vessel v " +
"ON i.company_id = v.company_id " +
"AND i.Vessel_Id = v.Vessel_Id " +
"LEFT OUTER JOIN party tp " +
"ON tp.company_id = a.company_id " +
"AND a.Transporter = tp.party_id AND a.branch_id =tp.branch_id " +
"LEFT OUTER JOIN party pr " +
"ON pr.Company_Id = i.Company_Id " +
"AND pr.Party_Id = i.On_Account_Of AND pr.branch_id =i.branch_id " +
"LEFT OUTER JOIN cfequipmentactivity eq " +
"ON i.company_id = eq.company_id " +
"AND i.branch_id = eq.branch_id " +
"AND i.stuff_id = eq.de_stuff_id " +
"WHERE a.company_id = :companyId " +
"AND a.branch_id = :branchId " +
"AND a.Profitcentre_Id = 'N00004' " +
//"AND i.MOVEMENT_TYPE = 'ONWH' " +
"AND i.MOVEMENT_TYPE IN ('ONWH','Buffer') " +
"AND i.Stuff_Tally_Date BETWEEN :startDate AND :endDate " +
"AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " +
"AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
"AND (:exporterName IS NULL OR :exporterName = '' OR b.Exporter_Id = :exporterName) " +
"AND (:accountHolderId IS NULL OR :accountHolderId = '' OR i.On_Account_Of = :accountHolderId) " +
"AND (:cha IS NULL OR :cha = '' OR b.CHA = :cha) " +
"GROUP BY i.container_no " +
"ORDER BY i.Stuff_Tally_Date", 
nativeQuery = true
)
List<Object[]> findExportContainerONWHBuffer(
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("startDate") Date startDate,
        @Param("endDate") Date endDate,
        @Param("sbNo") String sbNo,
        @Param("bookingNo") String bookingNo,
        @Param("exporterName") String exporterName,
        @Param("accountHolderId") String accountHolderId,
        @Param("cha") String cha);




















	@Query(value = 
	        "SELECT DISTINCT tp.party_name, " +
	" a.transporter_name , "+
	        "       COUNT(CASE WHEN a.container_size IN ('20', '22') THEN 1 END) AS count_20, " +
	        "       COUNT(CASE WHEN a.container_size IN ('40', '45') THEN 1 END) AS count_40, " +
	        "       COUNT(a.container_no) AS containerCount, " +
	        "       a.container_size, " +
	        "       a.gate_out_id " +
	        "FROM cfgateout a " +
	        "LEFT OUTER JOIN cfexpmovementreq b " +
	        "    ON a.Company_Id = b.Company_Id " +
	        "    AND a.Branch_Id = b.Branch_Id " +
	        "    AND b.gate_out_id = a.gate_out_id " +
	        "    AND b.Container_no = a.Container_no " +
	        "    AND a.profitcentre_id = b.profitcentre_id " +
	        "LEFT OUTER JOIN party tp " +
	        "    ON tp.company_id = a.company_id " +
	        "    AND a.Transporter = tp.party_id AND tp.branch_id =a.branch_id " +
	        "WHERE a.company_id = :companyId " +
	        "  AND a.branch_id = :branchId " +
	        "  AND a.status = 'A' " +
	        "  AND b.status = 'A' " +
	        "  AND a.Container_No <> '' " +
	        "AND a.trans_type in ('CONT','MOVE') " +
	        "  AND b.profitcentre_id ='N00004' " +
//	        "  AND a.Process_Id ='P00223' " +
	        "  AND a.gate_out_date BETWEEN :startDate AND :endDate " +
	        "GROUP BY a.transporter, a.container_size " +
	        "ORDER BY tp.party_name, a.container_size", 
	        nativeQuery = true)
	    List<Object[]> getTransporterWiseTuesReport(
	    		  @Param("companyId") String companyId,
	    	        @Param("branchId") String branchId,
	    	        @Param("startDate") Date startDate,
	    	        @Param("endDate") Date endDate
	    );
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    @Query(value = 
	            "SELECT DISTINCT a.Container_no, a.Container_Size, a.Container_type, " +
	            "IFNULL(DATE_FORMAT(a.Stuff_Tally_Date, '%d %b %Y %H:%i'), '') AS Stuff_Tally_Date, " +
	            "IFNULL(DATE_FORMAT(a.Movement_req_Date, '%d-%m-%Y %H:%i'), '') AS Movement_req_Date, " +
	            "p.Party_Name, p1.Party_Name AS SL, e.Agent_Seal_No, e.Customs_Seal_No, " +
	            "x.Vessel_Name, e.VIA_No, e.Voyage_No, e.POL, " +
	            "IFNULL(c.Gross_Weight, '0.00'), e.MOVEMENT_TYPE, c.On_Account_Of " +
	            "FROM cfexpinventory a " +
	            "LEFT OUTER JOIN cfstufftally e ON a.Company_Id = e.Company_Id " +
	            "AND a.Branch_id = e.Branch_id " +
	            "AND a.Container_No = e.Container_No " +
	            "AND a.profitcentre_id = e.profitcentre_id " +
	            "AND e.status = 'A' " +
	            "AND a.Stuff_Tally_Id = e.stuff_tally_id " +
	            "AND a.Stuff_req_Id = e.stuff_id " +
	            "LEFT OUTER JOIN cfgatein c ON a.Company_Id = c.Company_Id " +
	            "AND a.Branch_Id = c.Branch_Id " +
	            "AND a.Container_No = c.Container_No " +
	            "AND a.Gate_In_Id = c.Gate_In_Id " +
	            "LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id " +
	            "AND a.Branch_id = b.Branch_id " +
	            "AND a.SB_Trans_Id = b.SB_Trans_Id " +
	            "AND a.profitcentre_id = b.profitcentre_id " +
	            "AND b.status = 'A' " +
	            "AND a.SB_no = b.SB_no " +
	            "LEFT OUTER JOIN party w ON a.Company_Id = w.Company_Id " +
	            "AND a.sl = w.Party_Id AND a.branch_id =w.branch_id " +
	            "LEFT OUTER JOIN party y ON a.Company_Id = y.Company_Id " +
	            "AND a.sa = y.Party_Id AND a.branch_id =y.branch_id " +
	            "LEFT OUTER JOIN vessel x ON e.Company_Id = x.Company_Id " +
	            "AND e.Vessel_id = x.Vessel_Id " +
	            "LEFT OUTER JOIN party p ON a.company_id = p.company_id " +
	            "AND b.On_Account_Of = p.Party_Id AND a.branch_id =p.branch_id " +
	            "LEFT OUTER JOIN party p1 ON a.company_id = p1.company_id " +
	            "AND a.SL = p1.Party_Id AND a.branch_id =p1.branch_id " +
	            "WHERE a.company_id = :companyId " +
	            "AND a.branch_id = :branchId " +
	            "AND a.Profitcentre_Id = 'N00004' " +
	            "AND a.status = 'A' " +
	            "AND (a.Gate_Out_Id = '' OR a.Gate_Out_Id IS NULL OR a.Gate_Out_Date > :endDate) " +
	            "AND (a.Stuff_Req_Date != '0000-00-00 00:00:00' " +
	            "AND a.Stuff_Req_Date < :endDate) " +
	            "AND a.Container_Status = 'MTY' " +
	            "AND a.cycle != 'Hub' " +
	            "AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " +
	            "AND (:bookingNo IS NULL OR :bookingNo = '' OR c.delivery_order_no = :bookingNo) " +
	            "AND (:exporterName IS NULL OR :exporterName = '' OR b.Exporter_Id = :exporterName) " +
	            "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) " +
	            "AND (:cha IS NULL OR :cha = '' OR b.CHA = :cha) " +
	            "GROUP BY a.container_no " +
	            
	            "UNION " +

	            "SELECT DISTINCT a.Container_no, a.Container_Size, a.Container_type, " +
	            "IFNULL(DATE_FORMAT(a.Stuff_Tally_Date, '%d %b %Y %H:%i'), '') AS Stuff_Tally_Date, " +
	            "IFNULL(DATE_FORMAT(a.Movement_req_Date, '%d-%m-%Y %H:%i'), '') AS Movement_req_Date, " +
	            "p.Party_Name, p1.Party_Name AS SL, e.Agent_Seal_No, e.Customs_Seal_No, " +
	            "x.Vessel_Name, e.VIA_No, e.Voyage_No, e.POL, " +
	            "IFNULL(c.Gross_Weight, '0.00'), e.MOVEMENT_TYPE, c.On_Account_Of " +
	            "FROM cfexpinventory a " +
	            "LEFT OUTER JOIN cfstufftally e ON a.Company_Id = e.Company_Id " +
	            "AND a.Branch_id = e.Branch_id " +
	            "AND a.Container_No = e.Container_No " +
	            "AND a.profitcentre_id = e.profitcentre_id " +
	            "AND e.status = 'A' " +
	            "AND a.Stuff_Tally_Id = e.stuff_tally_id " +
	            "AND a.Stuff_req_Id = e.stuff_id " +
	            "LEFT OUTER JOIN cfgatein c ON a.Company_Id = c.Company_Id " +
	            "AND a.Branch_Id = c.Branch_Id " +
	            "AND a.Container_No = c.Container_No " +
	            "AND a.Gate_In_Id = c.Gate_In_Id " +
	            "LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id " +
	            "AND a.Branch_id = b.Branch_id " +
	            "AND a.SB_Trans_Id = b.SB_Trans_Id " +
	            "AND a.profitcentre_id = b.profitcentre_id " +
	            "AND b.status = 'A' " +
	            "AND a.SB_no = b.SB_no " +
	            "LEFT OUTER JOIN party w ON a.Company_Id = w.Company_Id " +
	            "AND a.sl = w.Party_Id AND a.branch_id =w.branch_id " +
	            "LEFT OUTER JOIN party y ON a.Company_Id = y.Company_Id " +
	            "AND a.sa = y.Party_Id AND a.branch_id =y.branch_id  " +
	            "LEFT OUTER JOIN vessel x ON e.Company_Id = x.Company_Id " +
	            "AND e.Vessel_id = x.Vessel_Id " +
	            "LEFT OUTER JOIN party p ON a.company_id = p.company_id " +
	            "AND b.On_Account_Of = p.Party_Id AND a.branch_id =p.branch_id " +
	            "LEFT OUTER JOIN party p1 ON a.company_id = p1.company_id " +
	            "AND a.SL = p1.Party_Id AND a.branch_id =p1.branch_id " +
	            "WHERE a.company_id = :companyId " +
	            "AND a.branch_id = :branchId " +
	            "AND a.Profitcentre_Id = 'N00004' " +
	            "AND a.status = 'A' " +
	            "AND (a.Gate_Out_Id = '' OR a.Gate_Out_Id IS NULL OR a.Gate_Out_Date > :endDate) " +
	            "AND a.Container_Status IN ('LDD', 'FCL') " +
	            "AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " +
	            "AND (:bookingNo IS NULL OR :bookingNo = '' OR c.delivery_order_no = :bookingNo) " +
	            "AND (:exporterName IS NULL OR :exporterName = '' OR b.Exporter_Id = :exporterName) " +
	            "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) " +
	            "AND (:cha IS NULL OR :cha = '' OR b.CHA = :cha) " +
	            "GROUP BY a.container_no", nativeQuery = true)
	        List<Object[]> findExportLoadedBalanceReport( @Param("companyId") String companyId,
	                @Param("branchId") String branchId,
//	                @Param("startDate") Date startDate,
	                @Param("endDate") Date endDate,
	                @Param("sbNo") String sbNo,
	                @Param("bookingNo") String bookingNo,
	                @Param("exporterName") String exporterName,
	                @Param("accountHolderId") String accountHolderId,
	                @Param("cha") String cha
	 );
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        @Query(value = "SELECT a.Doc_Ref_No, " +
	                "DATE_FORMAT(a.Doc_Ref_Date, '%d/%m/%Y'), " +
	                "c.Exporter_Name, " +
	                "p.Party_Name, " +
	                "q.Party_Name, " +
	                "a.Vehicle_No, " +
	                "DATE_FORMAT(a.in_Gate_In_Date, '%d/%m/%Y %H:%i'), " +
	                "a.Commodity_Description, " +
	                "b.No_Of_Packages, " +
	                "b.Gross_Weight, " +
	                "(b.Gross_Weight / b.No_Of_Packages), " +
	                "a.Qty_Taken_in, " +
	                "a.Cargo_Weight, " +
	                "b.Type_Of_Package, " +
	                "a.Gate_In_Id " +
	                "FROM cfgatein a " +
	                "LEFT OUTER JOIN cfsbcrg b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id " +
	                "AND a.Doc_Ref_No = b.SB_No AND a.ERP_Doc_Ref_No = b.SB_Trans_Id AND a.Profitcentre_Id = b.Profitcentre_Id " +
	                "AND a.Line_No = b.SB_Line_No " +
	                "LEFT OUTER JOIN cfsb c ON b.Company_Id = c.Company_Id AND b.Branch_Id = c.Branch_Id AND b.SB_No = c.SB_No " +
	                "AND b.SB_Trans_Id = c.SB_Trans_Id AND b.Profitcentre_Id = c.Profitcentre_Id " +
	                "LEFT OUTER JOIN party p ON c.Company_Id = p.Company_Id AND c.CHA = p.Party_Id AND c.branch_id =p.branch_id " +
	                "LEFT OUTER JOIN party q ON c.Company_Id = q.Company_Id AND c.On_Account_Of = q.Party_Id AND c.branch_id =q.branch_id " +
	                "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId AND a.profitcentre_id = 'N00004' " +
	                "AND b.status = 'A' AND a.Process_Id = 'P00217' AND a.Status = 'A' " +
	                "AND a.Carting_Trans_Id = '' AND a.in_Gate_In_Date <= :endDate " +
	                "AND (:sbNo IS NULL OR :sbNo = '' OR c.SB_No = :sbNo) " +
	                "AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
	                "AND (:exporterName IS NULL OR :exporterName = '' OR c.Exporter_Id = :exporterName) " +
	                "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR c.On_Account_Of = :accountHolderId) " +
	                "AND (:cha IS NULL OR :cha = '' OR c.CHA = :cha)",
	                nativeQuery = true)
	        List<Object[]> findCartingPendancyData(@Param("companyId") String companyId,
	                                               @Param("branchId") String branchId,
	                                               @Param("endDate") Date endDate,
	                                               @Param("sbNo") String sbNo,
	                                               @Param("bookingNo") String bookingNo,
	                                               @Param("exporterName") String exporterName,
	                                               @Param("accountHolderId") String accountHolderId,
	                                               @Param("cha") String cha);


	        
	        
	        
	        @Query(value = "SELECT a.Container_No, a.Container_Size, b.Party_Name, " +
	                "GROUP_CONCAT(g.SB_No) AS SBNO, d.Party_Name AS CHA_Party_Name, g.Exporter_Name, " +
	                "g.Commodity, DATE_FORMAT(g.Stuff_Tally_Date, '%d/%m/%Y %H:%i') AS Stuff_Tally_Date, " +
	                "DATE_FORMAT(a.Movement_Req_Date, '%d/%m/%Y %H:%i') AS Movement_Req_Date, '' AS movement_validity, " +
	                "a.via_no, e.Vessel_Name, a.Type_of_Container, g.Terminal " +
	                "FROM cfexpmovementreq a " +
	                "LEFT OUTER JOIN cfstufftally g ON a.Company_Id = g.Company_Id " +
	                "AND a.Branch_Id = g.Branch_Id " +
	                "AND a.Profitcentre_Id = g.Profitcentre_Id " +
	                "AND a.Movement_Req_Id = g.Movement_Req_Id " +
	                "AND a.Container_No = g.Container_No " +
	                "AND a.Stuff_Tally_Id = g.Stuff_Tally_Id " +
	                "LEFT OUTER JOIN party b ON b.Company_Id = a.Company_Id " +
	                "AND b.Party_Id = a.Shipping_Line AND b.branch_id =a.branch_id " +
	                "LEFT OUTER JOIN party c ON c.Company_Id = a.Company_Id " +
	                "AND c.Party_Id = a.Shipping_Agent AND c.branch_id =a.branch_id " +
	                "LEFT OUTER JOIN party d ON d.Company_Id = g.Company_Id " +
	                "AND d.Party_Id = g.CHA AND d.branch_id =g.branch_id " +
	                "LEFT OUTER JOIN Vessel e ON e.Company_Id = a.Company_Id " +
	                "AND e.Vessel_Id = a.Vessel_Id " +
	                "LEFT OUTER JOIN Voyage f ON f.Company_Id = a.Company_Id " +
	                "AND f.Vessel_code = a.Vessel_Id " +
	                "AND f.voyage_no = a.voyage_no " +
	                "WHERE a.Company_Id = :companyId " +
	                "AND a.Branch_Id = :branchId " +
	                "AND a.Profitcentre_Id = 'N00004' " +
	                "AND a.Movement_Req_Id != '' OR a.Movement_Req_Id IS NULL " +
	                "AND a.status = 'A' " +
	                "AND a.Movement_Req_Date >= :endDate " +
	                "AND a.ProfitCentre_Id = 'N00004' " +
	                "AND a.status = 'A' " +
	                "AND (a.Gate_Out_Id = '' OR a.Gate_Out_Id IS NULL OR a.Gate_Out_Date > :endDate) " +
	                "AND (:sbNo IS NULL OR :sbNo = '' OR g.SB_No = :sbNo) " +
//	                "AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
//	                "AND (:exporterName IS NULL OR :exporterName = '' OR c.Exporter_Id = :exporterName) " +
	                "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR g.On_Account_Of = :accountHolderId) " +
	                "AND (:cha IS NULL OR :cha = '' OR g.CHA = :cha) " +
	                "GROUP BY a.container_no " +
	                "ORDER BY a.Movement_Req_Date DESC", nativeQuery = true)
	        List<Object[]> getContainerMovements(@Param("companyId") String companyId,
                    @Param("branchId") String branchId,
                    @Param("endDate") Date endDate,
                    @Param("sbNo") String sbNo,
//                    @Param("bookingNo") String bookingNo,
//                    @Param("exporterName") String exporterName,
                    @Param("accountHolderId") String accountHolderId,
                    @Param("cha") String cha);

	        
	        
	        
	        
	        
	        
	        @Query(value = "SELECT a.Vehicle_No, b.SB_No, a.Transporter_Name, o.Party_Name, " +
	                "IFNULL(DATE_FORMAT(a.Gate_Pass_Date, '%d/ %m/ %Y %H:%i'), '') AS Gate_Pass_Date, " +
	                "a.QTY_TAKEN_OUT, a.Gross_Wt " +
	                "FROM cfexportgatepass a " +
	                "LEFT OUTER JOIN cfbacktotown b ON a.company_id = b.company_id AND a.branch_id = b.branch_id AND a.SB_No = b.SB_No " +
	                "LEFT OUTER JOIN party o ON b.company_Id = o.company_id AND b.On_Account_Of = o.Party_Id AND b.branch_id =o.branch_id " +
	                "WHERE b.company_id = :companyId AND b.branch_id = :branchId " +
	                "AND b.Back_To_Town_Trans_Date BETWEEN :startDate AND :endDate "+
	                "AND (:sbNo IS NULL OR :sbNo = '' OR b.SB_No = :sbNo) " +
//	                "AND (:bookingNo IS NULL OR :bookingNo = '' OR a.delivery_order_no = :bookingNo) " +
//	                "AND (:exporterName IS NULL OR :exporterName = '' OR c.Exporter_Id = :exporterName) " +
	                "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR b.On_Account_Of = :accountHolderId) " +
	                "AND (:cha IS NULL OR :cha = '' OR a.CHA = :cha) " , nativeQuery = true)
	 List<Object[]> getCargoBackToTownDetails(@Param("companyId") String companyId,
             @Param("branchId") String branchId,
             @Param("startDate") Date startDate,
             @Param("endDate") Date endDate,
             @Param("sbNo") String sbNo,
//             @Param("bookingNo") String bookingNo,
//             @Param("exporterName") String exporterName,
             @Param("accountHolderId") String accountHolderId,
             @Param("cha") String cha);
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Query(value = 
			    "SELECT " +
			    "    b.Container_No, " +
			    "    b.CONTAINER_SIZE, " +
			    "    DATE_FORMAT(a.Stuff_Date, '%d/%b/%Y %H:%i'), " +
			    "    c.New_Commodity, " +
			    "    a.Agent_Seal_No, " +
			    "    a.Cargo_Weight, " +
			    "    a.Tare_Weight, " +
			    "    GROUP_CONCAT(d.Jar_Dtl_Desc) AS Jar_Details, " +
			    " a.Stuff_Tally_Id "+
			    "FROM " +
			    "    cfequipmentactivity b " +
			    "LEFT OUTER JOIN " +
			    "    cfstufftally a " +
			    "    ON a.Company_Id = b.Company_Id " +
			    "    AND a.Branch_Id = b.Branch_Id " +
			    "    AND a.Profitcentre_Id = b.Profitcentre_Id " +
			    "    AND a.Stuff_Id = b.De_Stuff_Id " +
			    "    AND a.SB_Trans_Id = b.Erp_Doc_ref_no " +
			    "    AND a.SB_No = b.Doc_ref_no " +
			    "    AND a.Container_No = b.Container_No " +
			    "LEFT OUTER JOIN " +
			    "    cfsbcrg c " +
			    "    ON a.Company_Id = c.Company_Id " +
			    "    AND a.Branch_Id = c.Branch_Id " +
			    "    AND a.SB_Trans_Id = c.SB_Trans_Id " +
			    "    AND a.SB_No = c.SB_No " +
			    "LEFT OUTER JOIN " +
			    "    jar_detail d " +
			    "    ON b.Company_Id = d.Company_Id " +
			    "    AND b.Equipment = d.Jar_Dtl_Id " +
			    "    AND d.Jar_Id = 'J00012' " +
			    "WHERE " +
			    "    a.Company_Id = :companyId " +  // Corrected placeholder here
			    "    AND a.Branch_Id = :branchId " + 
			    "    AND b.Status = 'A' " +
			    "    AND a.Status = 'A' " +
			    "GROUP BY " +
			    "    a.Container_No, a.Stuff_Tally_Id", 
			    nativeQuery = true)
			List<Object[]> getEquipmentActivityDetails(@Param("companyId") String companyId,
			         @Param("branchId") String branchId);


}
