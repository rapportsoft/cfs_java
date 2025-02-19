package com.cwms.repository;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Party;
import com.cwms.entities.Profitcentre;

public interface DMRRepository extends JpaRepository<Profitcentre, String> {

//	@Query("SELECT DISTINCT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName) "
//	        + "FROM Party p "
//	        + "WHERE p.companyId = :companyId "
//	        + "AND p.branchId = :branchId "
//	        + "AND p.status != 'D' "
//	        + "AND ("
//	        + "  (:lin IS NULL OR :lin = '' OR p.lin = :lin) "
//	        + "  AND (:agt IS NULL OR :agt = '' OR p.agt = :agt) "
//	        + "  OR (:lin IS NULL AND :agt IS NULL) "
//	        + "  OR (:partyName IS NULL OR :partyName = '' OR p.partyName LIKE concat(:partyName, '%')) "
//	        + ")")
//	List<Party> getAllAccountHolder(@Param("companyId") String companyId, 
//	                                @Param("branchId") String branchId, 
//	                                @Param("lin") String lin,
//	                                @Param("agt") String agt,
//	                                @Param("partyName") String partyName);
	
//	@Query("SELECT DISTINCT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName) "
//	        + "FROM Party p "
//	        + "WHERE p.companyId = :companyId "
//	        + "AND p.branchId = :branchId "
//	        + "AND p.status != 'D' "
//	        + "AND ("
//	        + "  (:lin IS NULL OR :lin = '' OR p.lin = :lin) "
//	        + "  AND (:agt IS NULL OR :agt = '' OR p.agt = :agt) "
//	        + "  AND (:cha IS NULL OR :cha = '' OR p.cha = :cha) "
//	        + "  OR (:lin IS NULL AND :agt IS NULL AND :cha IS NULL) "
//	        + "  OR (:partyName IS NULL OR :partyName = '' OR p.partyName LIKE CONCAT('%',:partyName, '%')) "
//	        + ")")
//	List<Party> getAllAccountHolder(@Param("companyId") String companyId, 
//	                                @Param("branchId") String branchId, 
//	                                @Param("lin") String lin,
//	                                @Param("agt") String agt,
//	                                @Param("cha") String cha,
//	                                @Param("partyName") String partyName);
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName) "
	        + "FROM Party p "
	        + "WHERE p.companyId = :companyId "
	        + "AND p.branchId = :branchId "
	        + "AND p.status != 'D' "
	        + "AND ("
	        + "  (:cha = 'Y' AND p.cha = :cha) "  // If cha = 'Y', filter only by cha
	        + "  OR (:cha IS NULL OR :cha = '' OR :cha != 'Y') AND (:agt = 'Y' AND p.agt = :agt) "  // If agt = 'Y', filter only by agt
	        + "  OR (:cha IS NULL OR :cha = '' OR :cha != 'Y') AND (:agt IS NULL OR :agt = '' OR :agt != 'Y') AND (:lin = 'Y' AND p.lin = :lin) "  // If lin = 'Y', filter only by lin
	        + "  OR (:cha IS NULL OR :cha = '' OR :cha != 'Y') AND (:agt IS NULL OR :agt = '' OR :agt != 'Y') AND (:lin IS NULL OR :lin = '' OR :lin != 'Y') "  // If none are 'Y', apply general filters
	        + "      AND (:lin IS NULL OR :lin = '' OR p.lin = :lin) "
	        + "      AND (:agt IS NULL OR :agt = '' OR p.agt = :agt) "
	        + "      AND (:cha IS NULL OR :cha = '' OR p.cha = :cha) "
	        + "      AND (:partyName IS NULL OR :partyName = '' OR p.partyName LIKE CONCAT('%', :partyName, '%')) "
	        + ")")
	List<Party> getAllAccountHolder(@Param("companyId") String companyId, 
	                                @Param("branchId") String branchId, 
	                                @Param("lin") String lin,
	                                @Param("agt") String agt,
	                                @Param("cha") String cha,
	                                @Param("partyName") String partyName);


	
	

	@Query(value = "SELECT DATE_FORMAT(a.Carting_Trans_Date ,'%d %b %Y') AS CartingTransDate, " +
		       " b.SB_No, " +
		       " DATE_FORMAT(b.SB_Date,'%d %b %Y') AS SBDate, " +
		       " b.Exporter_Name, " +
		       " d.Party_name AS CHA, " +
		       " e.Party_Name AS OnAccountOf, " +
		       " b.Consignee_Name, " +
		       " c.Commodity, " +
		       " c.No_Of_Packages, " +
		       " a.Actual_No_Of_Packages, " +
		       " c.Gross_Weight, " +
		       " c.FOB, " +
		       " b.POD, " +
		       " c.Cargo_Type, " +
		       " GROUP_CONCAT(DISTINCT eq.Equipment) AS Used, " +
		       " '' AS Status, " +
		       " a.Grid_Location, " +
		       " a.Area_Occupied, " +
		       " a.Vehicle_No, " +
		       " '' AS ContainerSize " +
		       " FROM cfcrtg a " +
		       " LEFT OUTER JOIN cfsbcrg c ON c.Company_id = a.Company_id AND c.branch_id = a.branch_id " +
		       " AND c.profitcentre_id = a.profitcentre_id AND c.SB_Trans_Id = a.SB_Trans_Id " +
		       " AND c.SB_No = a.SB_No AND c.SB_Line_No = a.SB_Line_No " +
		       " LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id AND b.Branch_Id = a.Branch_Id " +
		       " AND a.SB_Trans_Id = b.SB_Trans_Id AND a.SB_No = b.SB_No AND a.Profitcentre_Id = b.Profitcentre_Id " +
		       " LEFT OUTER JOIN party d ON a.Company_id = d.Company_id AND d.Party_Id = b.CHA AND a.branch_id = d.branch_id " +
		       " LEFT OUTER JOIN party e ON b.Company_id = e.Company_id AND b.On_Account_Of = e.Party_Id AND b.branch_id = e.branch_id " +
		       " LEFT OUTER JOIN cfimpexpgrid cg ON cg.company_id = a.company_id AND cg.branch_id = a.branch_id " +
		       " AND cg.process_trans_id = a.carting_trans_id AND cg.Line_No = a.Carting_Line_Id " +
		       " LEFT OUTER JOIN cfequipmentactivity eq ON a.Company_Id = eq.Company_Id AND a.Branch_Id = eq.Branch_Id " +
		       " AND a.Gate_In_Id = eq.De_Stuff_Id AND eq.Doc_ref_no = a.SB_No AND eq.Erp_Doc_ref_no = a.SB_Trans_Id " +
		       " AND a.Profitcentre_Id = eq.Profitcentre_Id " +
		       " WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
		       " AND b.On_Account_Of = :onAccountOf " +
		       " AND a.Profitcentre_Id IN ('N00004') " +
		       " AND b.status = 'A' AND c.status = 'A' AND a.Status = 'A' " +
		       " AND (c.No_Of_Packages - (c.stuffed_qty + c.back_to_town_pack)) > 0 " +
		       " AND a.Carting_Trans_Date < :cartingTransDate " +
		       " GROUP BY a.SB_Trans_Id, a.Carting_Trans_Id, a.Carting_Line_Id, a.SB_No, a.SB_Line_No " +
		       " HAVING (SUM(cg.Yard_Packages) - SUM(cg.QTY_TAKEN_OUT)) > 0 " +
		       " ORDER BY a.Carting_Trans_Date ASC", nativeQuery = true)
		List<Object[]> exportCargoBalanceReport(@Param("companyId") String companyId, 
		                                        @Param("branchId") String branchId, 
		                                        @Param("onAccountOf") String onAccountOf, 
		                                        @Param("cartingTransDate") Date cartingTransDate);

		@Query(value = "SELECT DISTINCT k.Back_To_Town_Trans_Id, " +
	               "k.SB_No, " +
	               "DATE_FORMAT(k.SB_Date,'%d %b %Y'), " +
	               "DATE_FORMAT(k.Back_To_Town_Trans_Date,'%d %b %Y %H:%i'), " +
	               "e.Party_Name AS agent, " +
	               "d.Party_Name AS cha, " +
	               "b.Exporter_Name AS exporter, " +
	               "b.Consignee_Name AS consignee, " +
	               "k.Commodity, " +
	               "k.Back_To_Town_Packages, " +
	               "GROUP_CONCAT(DISTINCT a.Vehicle_No), " +
	               "k.Comments, " +
	               "'' AS contsize " +
	               "FROM cfbacktotown k " +
	               "LEFT OUTER JOIN cfcrtg a ON k.Company_id = a.Company_id " +
	               "AND k.branch_id = a.branch_id " +
	               "AND k.profitcentre_id = a.profitcentre_id " +
	               "AND k.SB_Trans_Id = a.SB_Trans_Id " +
	               "AND k.SB_No = a.SB_No " +
	               "AND k.SB_Line_No = a.SB_Line_No " +
	               "LEFT OUTER JOIN cfsbcrg c ON c.Company_id = a.Company_id " +
	               "AND c.branch_id = a.branch_id " +
	               "AND c.profitcentre_id = a.profitcentre_id " +
	               "AND c.SB_Trans_Id = a.SB_Trans_Id " +
	               "AND c.SB_No = a.SB_No " +
	               "AND c.SB_Line_No = a.SB_Line_No " +
	               "LEFT OUTER JOIN cfsb b ON a.Company_Id = b.Company_Id " +
	               "AND b.Branch_Id = a.Branch_Id " +
	               "AND a.SB_Trans_Id = b.SB_Trans_Id " +
	               "AND a.SB_No = b.SB_No " +
	               "AND a.Profitcentre_Id = b.Profitcentre_Id " +
	               "LEFT OUTER JOIN party d ON a.Company_id = d.Company_id " +
	               "AND d.Party_Id = b.CHA " +
	               "AND a.branch_id = d.branch_id " +
	               "LEFT OUTER JOIN party e ON b.Company_id = e.Company_Id " +
	               "AND b.On_Account_Of = e.Party_Id " +
	               "AND b.branch_id = e.branch_id " +
	               "WHERE k.Company_Id = :companyId " +
	               "AND k.Branch_Id = :branchId " +
	               "AND b.On_Account_Of = :onAccountOf " +
	               "AND k.Profitcentre_Id IN ('N00004') " +
	               "AND k.Back_To_Town_Trans_Date BETWEEN :startDate AND :endDate " +
	               "AND k.Status = 'A' " +
	               "GROUP BY k.Back_To_Town_Trans_Id " +
	               "ORDER BY k.Back_To_Town_Trans_Id", 
	       nativeQuery = true)
	List<Object[]> fetchBackToTownDetails(@Param("companyId") String companyId, 
	                                      @Param("branchId") String branchId, 
	                                      @Param("onAccountOf") String onAccountOf, 
	                                      @Param("startDate") Date startDate, 
	                                      @Param("endDate") Date endDate);
	
	
	
	@Query(value = "SELECT DISTINCT a.Container_no, " +
            "a.Container_Size, " +
            "a.Container_type, " +
            "IF(g.Cargo_Type = 'NAGRO', 'NON AGRO', 'AGRO') AS cargotype, " +
            "y.party_name AS agent, " +
            "w.party_name AS line, " +
            "DATE_FORMAT(a.Gate_In_Date, '%d/%b/%Y %H:%i'), " +
            "DATE_FORMAT(a.Stuff_Tally_Date, '%d/%b/%Y') AS stuffingdate, " +
            "c.Agent_Seal_No AS agentselno, " +
            "c.Customs_Seal_No, " +
            "v.Vessel_Name AS vessel, " +
            "c.POL AS port, " +
            "c.POD AS pod, " +
            "b.comments, " +
            "c.MOVEMENT_TYPE " +
            "FROM cfexpinventory a " +
            "LEFT OUTER JOIN cfgatein b ON a.Company_Id = b.Company_Id " +
            "AND a.Branch_Id = b.Branch_Id " +
            "AND a.Gate_In_Id = b.Gate_In_Id " +
            "AND a.Container_No = b.Container_No " +
            "LEFT OUTER JOIN cfstufftally c ON c.Company_Id = a.Company_Id " +
            "AND c.Branch_Id = a.Branch_Id " +
            "AND c.Profitcentre_Id = a.Profitcentre_Id " +
            "AND c.Container_No = a.Container_No " +
            "AND a.Stuff_Tally_Id = c.Stuff_Tally_Id " +
            "LEFT OUTER JOIN cfsbcrg g ON c.Company_id = g.Company_id " +
            "AND c.branch_id = g.branch_id " +
            "AND c.profitcentre_id = g.profitcentre_id " +
            "AND c.SB_Trans_Id = g.SB_Trans_Id " +
            "AND c.SB_No = g.SB_No " +
            "AND c.SB_Line_Id = g.SB_Line_No " +
            "LEFT OUTER JOIN party w ON a.Company_Id = w.Company_Id " +
            "AND a.sl = w.Party_Id " +
            "AND a.branch_id = w.branch_id " +
            "LEFT OUTER JOIN party y ON a.Company_Id = y.Company_Id " +
            "AND a.sa = y.Party_Id " +
            "AND a.branch_id = y.branch_id " +
            "LEFT OUTER JOIN vessel v ON a.Company_Id = v.Company_Id " +
            "AND a.Vessel_Id = v.Vessel_Id " +
            "AND a.branch_id = v.branch_id " +
            "WHERE a.Company_Id = :companyId " +
            "AND a.Branch_Id = :branchId " +
            "AND c.On_Account_Of = :onAccountOf " +
            "AND a.Profitcentre_Id IN ('N00004') " +
            "AND a.Container_No != '' " +
            "AND a.Status = 'A' " +
            "AND c.MOVEMENT_TYPE IN ('Buffer', 'ONWH', 'FDSGI', 'CLP') " +
            "AND (a.Gate_Out_Id = '' OR a.gate_out_Id IS NULL OR a.Gate_Out_Date > :gateOutDate) " +
            "AND (a.Stuff_Tally_Date != '0000-00-00 00:00:00' " +
            "AND a.Stuff_Tally_Date <= :gateOutDate) " +
            "GROUP BY a.container_no " +
            "ORDER BY a.Gate_In_Id, a.Stuff_Tally_Date ASC", 
    nativeQuery = true)
List<Object[]> fetchLoadedBalance(@Param("companyId") String companyId, 
                               @Param("branchId") String branchId, 
                               @Param("onAccountOf") String onAccountOf, 
                               @Param("gateOutDate") Date gateOutDate);






@Query(value = "SELECT DISTINCT a.container_No, " +
        "a.Container_Size, " +
        "a.Container_Type, " +
        "v.Vessel_Name AS vessel, " +
        "c.Period_From AS gateindate, " +
        "DATE_FORMAT(c.Stuff_Tally_Date, '%d/%b/%Y') AS stuffdate, " +
        "DATE_FORMAT(a.Gate_Out_Date, '%d/%b/%Y') AS outdate, " +
        "DATE_FORMAT(a.Gate_Out_Date, '%H:%i') AS outtime, " +
        "a.Gross_Wt AS wt, " +
        "b.Agent_Seal_No AS agentsealno, " +
        "b.Customs_Seal_No, " +
        "p.Party_Name AS party, " +
        "q.Party_Name AS line, " +
        "a.Transporter_Name AS transporter, " +
        "a.Vehicle_No AS veh, " +
        "b.POL AS pol, " +
        "'INMUN1CON1' AS cfscode " +
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
        "LEFT OUTER JOIN cfexpinventory d ON a.Company_Id = d.Company_Id " +
        "AND a.Branch_Id = d.Branch_Id " +
        "AND a.Container_No = d.Container_No " +
        "AND a.Gate_Out_Id = d.Gate_Out_Id " +
        "AND a.Gate_Pass_No = d.Gate_Pass_No " +
        "LEFT OUTER JOIN party p ON c.Company_Id = p.Company_Id " +
        "AND c.Shipping_Agent = p.Party_Id " +
        "LEFT OUTER JOIN party q ON c.Company_Id = q.Company_Id " +
        "AND c.Shipping_Line = q.Party_Id " +
        "AND c.Branch_Id = q.Branch_Id " +
        "LEFT OUTER JOIN vessel v ON b.Company_Id = v.Company_Id " +
        "AND a.VESSEL_ID = v.Vessel_Id " +
        "AND b.Branch_Id = v.Branch_Id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND c.On_Account_Of = :onAccountOf " +
        "AND a.Profitcentre_Id IN ('N00004') " +
        "AND a.Container_No != '' " +
        "AND a.Gate_Out_Date BETWEEN :gateOutStart AND :gateOutEnd " +
        "AND a.Status = 'A' " +
        "AND b.Status = 'A' " +
        "AND c.Status = 'A' " +
        "AND Mov_Req_Type IN ('Buffer', 'ONWH', 'FDSGI', 'CLP') " +
        "ORDER BY a.Gate_Out_Date",
nativeQuery = true)
List<Object[]> fetchGateLaodedOutDetails(@Param("companyId") String companyId, 
                            @Param("branchId") String branchId, 
                            @Param("onAccountOf") String onAccountOf, 
                            @Param("gateOutStart") Date gateOutStart, 
                            @Param("gateOutEnd") Date gateOutEnd);



@Query(value = "SELECT p.Party_Name, " +
        "DATE_FORMAT(a.Carting_Trans_Date, '%d/%b/%Y') AS cartingDate, " +
        "DATE_FORMAT(a.Carting_Trans_Date, '%H:%i') AS cartingTime, " +
        "a.SB_No, " +
        "DATE_FORMAT(a.SB_Date, '%d/%b/%Y') AS sbDate, " +
        "b.Exporter_Name, " +
        "q.Party_Name AS chaName, " +
        "b.Consignee_Name, " +
        "a.Commodity, " +
        "c.Gross_Weight, " +
        "c.No_Of_Packages, " +
        "a.Actual_No_Of_Packages, " +
        "c.fob, " +
        "b.POD, " +
        "b.Cargo_Type, " +
        " '' ,"+
        " '' ,"+
        "a.Grid_Location, " +
        "a.Area_Occupied, " +
        "cf.Vehicle_No, " +
        "'', " +
        "'' " +
        "FROM cfcrtg a " +
        "LEFT OUTER JOIN cfsbcrg c ON a.Company_Id = c.Company_Id " +
        "AND a.Branch_Id = c.Branch_Id " +
        "AND a.Profitcentre_Id = c.Profitcentre_Id " +
        "AND a.SB_Trans_Id = c.SB_Trans_Id " +
        "AND a.SB_Line_No = c.SB_Line_No " +
        "AND a.sb_no = c.Sb_No " +
        "LEFT OUTER JOIN cfsb b ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id " +
        "AND b.Profitcentre_Id = c.Profitcentre_Id " +
        "AND b.SB_Trans_Id = c.SB_Trans_Id " +
        "AND b.sb_no = c.Sb_No " +
        "LEFT OUTER JOIN party p ON b.Company_Id = p.Company_Id " +
        "AND b.On_Account_Of = p.Party_Id " +
        "AND b.Branch_Id = p.Branch_Id " +
        "LEFT OUTER JOIN party q ON b.Company_Id = q.Company_Id " +
        "AND b.CHA = q.Party_Id " +
        "AND b.Branch_Id = q.Branch_Id " +
        "LEFT OUTER JOIN cfgatein cf ON cf.Company_Id = a.Company_Id " +
        "AND cf.Branch_Id = a.Branch_Id " +
        "AND cf.Profitcentre_Id = a.Profitcentre_Id " +
        "AND cf.ERP_Doc_Ref_No = a.SB_Trans_Id " +
        "AND cf.Line_No = a.SB_Line_No " +
        "AND cf.Gate_In_Id = a.Gate_In_Id " +
        "AND cf.doc_ref_No = a.SB_No " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND b.On_Account_Of = :onAccountOf " +
        "AND a.Profitcentre_Id IN ('N00004') " +
        "AND a.Carting_Trans_Date BETWEEN :cartingStart AND :cartingEnd " +
        "AND a.Status = 'A' " +
        "ORDER BY a.Carting_Trans_Date",
nativeQuery = true)
List<Object[]> fetchCartingDetails(@Param("companyId") String companyId, 
                            @Param("branchId") String branchId, 
                            @Param("onAccountOf") String onAccountOf, 
                            @Param("cartingStart") Date cartingStart, 
                            @Param("cartingEnd") Date cartingEnd);


@Query(value = "SELECT a.Gate_Pass_No, " +
        "DATE_FORMAT(b.Gate_In_Date, '%d.%m.%Y') AS Gate_In_Date, " +
        "DATE_FORMAT(a.GATE_OUT_DATE, '%d.%m.%Y') AS GATE_OUT_DATE, " +
        "DATE_FORMAT(a.GATE_OUT_DATE, '%H:%i') AS OutTime, " +
        "a.Container_No, a.Container_Size, a.Container_Type, " +
        "s.Party_Name AS accholder, " +
        "t.Party_Name AS agentname, " +
        "l.Party_Name AS shipline, " +
        "b.Booking_No, a.Vehicle_No, a.Transporter_Name, " +
        "a.Location, c.e_booking_no, '', a.Comments, '' " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfcommongatepass b ON a.Company_Id = b.company_id " +
        "AND a.Branch_Id = b.Branch_Id " +
        "AND a.Gate_Out_Id = b.Gate_Out_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "AND a.Gate_Pass_No = b.Gate_Pass_Id " +
        "AND a.Container_No = b.Container_No " +
        "LEFT OUTER JOIN cfexpinventory c ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id " +
        "AND b.Profitcentre_Id = c.Profitcentre_Id " +
        "AND b.Gate_Out_Id = c.Empty_Gateout_Id " +
        "AND b.Gate_In_Id = c.Gate_In_Id " +
        "AND b.Container_No = c.Container_No " +
        "LEFT OUTER JOIN party s ON a.Company_Id = s.Company_Id " +
        "AND a.On_Account_Of = s.Party_Id  and a.branch_id =s.branch_id " +
        "LEFT OUTER JOIN party l ON a.Company_Id = l.Company_Id " +
        "AND a.SL = l.Party_Id and a.branch_id = l.branch_id " +
        "LEFT OUTER JOIN party t ON c.Company_Id = t.Company_Id " +
        "AND c.SA = t.Party_Id  and c.branch_id = t.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Profitcentre_Id IN ('N00004') " +
        "AND a.Status = 'A' " +
        "AND a.Process_Id = 'P00223' " +
        "AND a.On_Account_Of = :onAccountOf " +
        "AND a.GATE_OUT_DATE BETWEEN :startDate AND :endDate " +
        "AND a.Container_Status = 'MTY' " +
        "ORDER BY a.GATE_OUT_DATE",
nativeQuery = true)
List<Object[]> fetchGateOutDetails(@Param("companyId") String companyId, 
                            @Param("branchId") String branchId, 
                            @Param("onAccountOf") String onAccountOf, 
                            @Param("startDate") Date startDate, 
                            @Param("endDate") Date endDate);


@Query(value = "SELECT DISTINCT " +
        "DATE_FORMAT(MIN(cr.Carting_Trans_Date), '%d.%m.%Y') AS Carting_Trans_Date, " +
        "a.SB_No, DATE_FORMAT(a.SB_Date, '%d.%m.%Y') AS SB_Date, " +
        "a.Exporter_Name, pa.Party_Name AS cha, ph.Party_Name AS onAccount, " +
        "a.Consignee, a.Commodity, cf.No_Of_Packages, SUM(a.Stuffed_Qty), " +
        "cf.Gross_Weight, a.FOB, a.POD, " +
        "IF(cf.Cargo_Type = 'NAGRO', 'NON AGRO', 'AGRO') AS Cargo_Type, " +
        "GROUP_CONCAT(DISTINCT eq.Equipment) AS Used, " +
        "cr.Grid_Location, cg.Vehicle_No, " +
        "DATE_FORMAT(i.Gate_In_Date, '%d.%m.%Y') AS Gate_In_Date, " +
        "cg.Transporter_Name, DATE_FORMAT(a.Stuff_Tally_Date, '%d.%m.%Y') AS Stuff_Tally_Date, " +
        "a.Container_No, a.Container_Size, a.Container_Type, q.Party_Name, " +
        "a.Agent_Seal_No, a.Customs_Seal_No, v.Vessel_Name, a.Voyage_No, " +
        "'', '', '', a.POL, a.STUFFTALLY_WO_TRANS_ID, " +
        "DATE_FORMAT(a.Stuff_Tally_Date, '%H:%i') AS Stuff_Tally_Time, '' " +
        "FROM cfstufftally a " +
        "LEFT OUTER JOIN cfcrtg cr ON a.Company_Id = cr.Company_Id " +
        "AND a.Branch_Id = cr.Branch_Id " +
        "AND a.SB_Trans_Id = cr.SB_Trans_Id " +
        "AND a.SB_No = cr.SB_No " +
        "AND a.SB_Line_Id = cr.SB_Line_No " +
        "AND a.Carting_Trans_Id = cr.Carting_Trans_Id " +
        "AND a.Carting_Line_Id = cr.Carting_Line_Id " +
        "LEFT OUTER JOIN cfsbcrg cf ON cf.Company_Id = a.Company_Id " +
        "AND cf.Branch_Id = a.Branch_Id " +
        "AND cf.SB_Trans_Id = a.SB_Trans_Id " +
        "AND cf.SB_No = a.SB_No " +
        "AND cf.Profitcentre_Id = a.Profitcentre_Id " +
        "AND cf.SB_Line_No = a.SB_Line_Id " +
        "LEFT OUTER JOIN cfexpinventory i ON a.Company_Id = i.Company_Id " +
        "AND a.Branch_Id = i.Branch_Id " +
        "AND a.Container_No = i.Container_No " +
        "AND a.Profitcentre_Id = i.Profitcentre_Id " +
        "AND a.Stuff_Tally_Id = i.Stuff_Tally_Id " +
        "LEFT OUTER JOIN cfgatein cg ON cg.Company_Id = i.Company_Id " +
        "AND cg.Branch_Id = i.Branch_Id " +
        "AND cg.Container_No = i.Container_No " +
        "AND cg.Profitcentre_Id = i.Profitcentre_Id " +
        "AND cg.Stuff_Tally_Id = i.Stuff_Tally_Id " +
        "LEFT OUTER JOIN vessel v ON v.Company_Id = a.Company_Id " +
        "AND v.Vessel_Id = a.Vessel_Id " +
        "LEFT OUTER JOIN cfequipmentactivity eq ON a.Company_Id = eq.Company_Id " +
        "AND a.Branch_Id = eq.Branch_Id " +
        "AND a.Stuff_Id = eq.De_Stuff_Id " +
        "AND eq.Doc_ref_no = a.SB_No " +
        "AND eq.Erp_Doc_ref_no = a.SB_Trans_Id " +
        "AND a.Profitcentre_Id = eq.Profitcentre_Id " +
        "AND a.Container_No = eq.Container_No " +
        "LEFT OUTER JOIN party p ON a.Company_Id = p.Company_Id " +
        "AND a.Shipping_Agent = p.Party_Id " +
        "AND a.Branch_Id = p.Branch_Id " +
        "LEFT OUTER JOIN party q ON a.Company_Id = q.Company_Id " +
        "AND a.Shipping_Line = q.Party_Id " +
        "AND a.Branch_Id = q.Branch_Id " +
        "LEFT OUTER JOIN party pa ON pa.Company_Id = a.Company_Id " +
        "AND pa.Party_Id = a.CHA " +
        "AND pa.Branch_Id = a.Branch_Id " +
        "LEFT OUTER JOIN party ph ON ph.Company_Id = a.Company_Id " +
        "AND ph.Party_Id = a.On_Account_Of " +
        "AND ph.Branch_Id = a.Branch_Id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.On_Account_Of = :onAccount " +
        "AND a.Profitcentre_Id IN ('N00004') " +
        "AND a.MOVEMENT_TYPE IN ('CLP') " +
        "AND a.Container_No != '' " +
        "AND a.Stuff_Tally_Date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' " +
        "GROUP BY a.Stuff_Tally_Id, a.Container_No, a.SB_Trans_Id, a.SB_Line_Id " +
        "ORDER BY a.Stuff_Tally_Date",
nativeQuery = true)
List<Object[]> fetchStuffTallyDetails(@Param("companyId") String companyId, 
                               @Param("branchId") String branchId, 
                               @Param("onAccount") String onAccount, 
                               @Param("startDate") Date startDate, 
                               @Param("endDate") Date endDate);





@Query(value = "SELECT DISTINCT " +
        "DATE_FORMAT(a.Gate_In_Date, '%d.%m.%Y') AS Gate_In_Date, " +
        "DATE_FORMAT(a.Gate_In_Date, '%H:%i') AS InTime, " +
        "a.Container_No, a.Container_Size, a.Container_Type, " +
        "d.Tare_Weight, (d.Gross_Weight - d.Tare_Weight) AS Payload, " +
        "d.Container_Seal_No, q.Party_Name, p.Party_Name, " +
        "d.Delivery_Order_No, d.Vehicle_No, d.Transporter_Name, '', " +
        "d.Comments, q.Customer_Code " +
        "FROM cfexpinventory a " +
        "LEFT OUTER JOIN cfgatein d ON a.Company_Id = d.Company_Id " +
        "AND a.Branch_Id = d.Branch_Id " +
        "AND a.Gate_In_Id = d.Gate_In_Id " +
        "AND a.Container_No = d.Container_No " +
        "LEFT OUTER JOIN party p ON p.Company_Id = a.Company_Id " +
        "AND a.SL = p.Party_Id " +
        "AND p.Branch_Id = a.Branch_Id " +
        "LEFT OUTER JOIN party q ON a.Company_Id = q.Company_Id " +
        "AND a.SA = q.Party_Id " +
        "AND a.Branch_Id = q.Branch_Id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Profitcentre_Id IN ('N00004') " +
        "AND a.Container_No != '' " +
//        "AND d.On_Account_Of = :onAccount " +
"AND ((:partyName IS NULL OR :partyName = '' OR d.On_Account_Of =:partyName) " +
"OR (:partyName IS NULL OR :partyName = '' OR a.SA =:partyName) " +
"OR (:partyName IS NULL OR :partyName = '' OR a.SL =:partyName )) " +
        "AND a.Gate_In_Date <= :gateInDate " +
        "AND d.Gate_In_Type NOT IN ('Buffer', 'ONWH', 'FDSGI') " +
        "AND ((a.Stuff_Tally_Id = '' OR a.Stuff_Tally_Id IS NULL OR " +
        "(a.Stuff_Tally_Id != '' AND a.Stuff_Tally_Date > :gateInDate)) " +
        "AND (a.Empty_Gateout_Id = '' OR a.Empty_Gateout_Id IS NULL OR " +
        "(a.Empty_Gateout_Id != '' AND a.Empty_Gateout_Date > :gateInDate))) " +
        "AND a.Status = 'A' " +
        "ORDER BY a.Gate_In_Date",
nativeQuery = true)
List<Object[]> fetchExportBalanceDetails(@Param("companyId") String companyId, 
                           @Param("branchId") String branchId, 
                           @Param("partyName") String partyName, 
                           @Param("gateInDate") Date gateInDate);

//AND (:partyName IS NULL OR :partyName = '' 
//OR d.On_Account_Of = :partyName 
//OR a.SA = :partyName 
//OR a.SL = :partyName)


@Query(value = "SELECT DATE_FORMAT(a.in_Gate_In_Date,'%d.%m.%Y') as Gate_In_Date, " +
        "DATE_FORMAT(a.in_Gate_In_Date,'%H:%i') as InTime, " +
        "a.Container_No, a.Container_Size, a.Container_Type, a.Tare_Weight, " +
        "(a.Gross_Weight - a.Tare_Weight) as payload, a.Container_Seal_No, " +
        "m.Party_Name as shippingAgent, p.Party_Name as shippingLine, " +
        "a.Delivery_Order_No, a.Vehicle_No, a.Transporter_Name, a.Terminal, a.Comments, '' " +
        "FROM cfgatein a " +
        "LEFT OUTER JOIN party p ON a.company_id = p.Company_Id " +
        "AND a.SL = p.Party_Id AND a.branch_id = p.branch_id " +
        "LEFT OUTER JOIN party m ON a.Company_Id = m.Company_Id " +
        "AND a.SA = m.Party_Id AND a.branch_id = m.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
//        "AND a.On_Account_Of = :onAccount " +
"AND ((:partyName IS NULL OR :partyName = '' OR a.On_Account_Of =:partyName) " +
"OR (:partyName IS NULL OR :partyName = '' OR a.SA =:partyName) " +
"OR (:partyName IS NULL OR :partyName = '' OR a.SL =:partyName )) " +
        "AND a.Profitcentre_Id IN ('N00004') " +
        "AND a.Status = 'A' " +
        "AND a.Process_Id = 'P00219' " +
        "AND a.in_Gate_In_Date BETWEEN :startDate AND :endDate " +
        "ORDER BY a.in_Gate_In_Date",
        nativeQuery = true)
List<Object[]> exportEmptyInDetails(@Param("companyId") String companyId, 
                                  @Param("branchId") String branchId, 
                                  @Param("partyName") String partyName, 
                                  @Param("startDate") Date startDate, 
                                  @Param("endDate") Date endDate);





@Query(value = "SELECT DISTINCT DATE_FORMAT(a.In_Gate_In_Date,'%d/%b/%Y') AS inGateDate, " +
        "DATE_FORMAT(a.In_Gate_In_Date,'%H:%i') AS inGateTime, " +
        "a.Container_No AS containerNo, " +
        "a.Container_Size AS containerSize, " +
        "a.Container_Type AS containerType, " +
        "d.BL_No AS blNo, " +
        "d.igm_no AS igmNo, " +
        "d.IGM_Line_No AS igmLineNo, " +
        "'' AS subitems, " +
        "d.commodity_Code AS commodityCode, " +
        "c.Scanner_Type AS scannerType, " +
        "c.Scanning_Done_Status AS scanningDoneStatus, " +
        "o.Party_Name AS partyName, " +
        "m.Party_Name AS la, " +
        "c.Container_Seal_No AS ls, " +
        "v.Vessel_Name AS vesselName, " +
        "b.VIA_NO AS viaNo, " +
        "b.Port AS port, " +
        "a.Comments AS comments, " +
        "a.Profitcentre_Id AS profitcentreId " +
        "FROM cfgatein a " +
        "LEFT OUTER JOIN cfigmcn c ON a.Company_Id = c.Company_Id AND a.Branch_Id = c.Branch_Id AND a.Profitcentre_Id = c.Profitcentre_Id " +
        "AND a.ERP_Doc_Ref_No = c.IGM_Trans_Id AND a.doc_ref_No = c.igm_no AND a.container_no = c.container_no AND a.Gate_In_Id = c.Gate_In_Id " +
        "LEFT OUTER JOIN cfigmcrg d ON d.Company_Id = c.Company_Id AND d.Branch_Id = c.Branch_Id AND d.Profitcentre_Id = c.Profitcentre_Id " +
        "AND c.igm_trans_id = d.igm_trans_id AND c.igm_no = d.igm_no AND c.igm_line_no = d.igm_line_no " +
        "LEFT OUTER JOIN cfigm b ON d.Company_Id = b.Company_Id AND d.Branch_Id = b.Branch_Id AND d.Profitcentre_Id = b.Profitcentre_Id " +
        "AND d.igm_no = b.IGM_No AND d.IGM_Trans_Id = b.IGM_Trans_Id " +
        "LEFT OUTER JOIN vessel v ON b.Company_Id = v.Company_Id AND b.Vessel_Id = v.Vessel_Id AND b.branch_id = v.branch_id " +
        "LEFT OUTER JOIN party p ON a.company_id = p.Company_Id AND a.SL = p.Party_Id AND a.branch_id = p.branch_id " +
        "LEFT OUTER JOIN party o ON a.company_id = o.Company_Id AND d.oth_party_Id = o.Party_Id AND a.branch_id = o.branch_id " +
        "LEFT OUTER JOIN party m ON a.Company_Id = m.Company_Id AND a.SL = m.Party_Id and a.branch_id = m.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Gate_In_Type = 'IMP' " +
        "AND a.Container_No != '' " +
        "AND ((:partyName IS NULL OR :partyName = '' OR a.On_Account_Of =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR a.SA =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR a.SL =:partyName )) " +
        "AND a.Container_Status != 'MTY' " +
        "AND a.Profitcentre_Id IN ('N00002') " +
        "AND a.Status = 'A' " +
        "AND b.status = 'A' " +
        "AND a.In_Gate_In_Date BETWEEN :startDate AND :endDate " +
        "ORDER BY a.In_Gate_In_Date", nativeQuery = true)
List<Object[]> getImportInMovementReport(@Param("companyId") String companyId,
                                 @Param("branchId") String branchId,
                                 @Param("partyName") String partyName, 
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);


@Query(value = "SELECT DISTINCT DATE_FORMAT(a.Gate_In_Date,'%d/%b/%Y'), " +
        "DATE_FORMAT(a.Gate_In_Date,'%H:%i'), " +
        "a.Container_no, a.Container_Size, a.container_type, " +
        "s.Container_Seal_No, s.Type_of_Container, s.Scanner_Type, " +
        "v.Vessel_Name, t.VIA_NO, p.Party_Name, pa.Party_Name, " +
        "ds.Origin, s.Container_Status as status, '' AS Remarks, " +
        "s.Profitcentre_Id, ds.commodity_Code " +
"FROM cfimpinventory a " +
"LEFT OUTER JOIN cfigmcn s ON a.Company_Id = s.Company_Id " +
    "AND a.Branch_id = s.Branch_id AND a.container_no = s.container_no " +
    "AND a.Igm_no = s.igm_no AND s.IGM_Trans_Id = a.IGM_Trans_Id " +
    "AND a.Gate_In_Id = s.Gate_In_Id  " +
"LEFT OUTER JOIN cfigmcrg ds ON ds.Company_Id = s.Company_Id " +
    "AND ds.Branch_id = s.Branch_id AND ds.Profitcentre_Id = s.Profitcentre_Id " +
    "AND s.igm_trans_id = ds.igm_trans_id AND s.igm_no = ds.igm_no " +
    "AND s.igm_line_no = ds.igm_line_no " +
"LEFT OUTER JOIN cfigm t ON t.Company_Id = ds.Company_Id " +
    "AND t.Branch_id = ds.Branch_id AND t.igm_trans_id = ds.igm_trans_id " +
    "AND t.igm_no = ds.igm_no AND s.Profitcentre_Id = ds.Profitcentre_Id   " +
"LEFT OUTER JOIN vessel v ON t.company_id = v.company_id " +
    "AND t.vessel_id = v.vessel_id AND t.branch_id = v.branch_id " +
"LEFT OUTER JOIN party p ON p.company_id = t.company_id " +
    "AND t.Shipping_line = p.party_id AND p.branch_id = t.branch_id " +
"LEFT OUTER JOIN party q ON a.Company_Id = q.Company_Id " +
    "AND t.Shipping_Agent = q.Party_Id AND a.branch_id = q.branch_id " +
"LEFT OUTER JOIN party pa ON pa.company_id = ds.company_id " +
    "AND ds.oth_party_Id = pa.party_id AND pa.branch_id = ds.branch_id " +
"WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId  " +
//    "AND ds.oth_party_Id = 'P04385'  " +
//"AND ds.On_Account_Of = :onAccount " +
"AND ((:partyName IS NULL OR :partyName = '' OR ds.oth_party_Id =:partyName) " +
"OR (:partyName IS NULL OR :partyName = '' OR t.Shipping_Agent =:partyName) " +
"OR (:partyName IS NULL OR :partyName = '' OR t.Shipping_line =:partyName )) " +
    "AND a.Profitcentre_Id IN ('N00002')  " +
    "AND (a.Gate_Out_Id = '' OR a.Gate_Out_Id IS NULL " +
        "OR (a.Gate_Out_Id != '' AND a.Gate_Out_Date > :endDate))  " +
    "AND (a.De_Stuff_Id = '' OR a.De_Stuff_Id IS NULL " +
        "OR (a.De_Stuff_Id != '' AND a.De_Stuff_Date > :endDate))  " +
    "AND a.Status = 'A'  " +
"ORDER BY a.De_Stuff_Date", nativeQuery = true)
List<Object[]> fetchYardBalanceDetails(@Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("partyName") String partyName, 
        @Param("endDate") Date endDate);




@Query(value = "SELECT DISTINCT DATE_FORMAT(b.Gate_In_Date, '%d/%b/%Y') AS gateInDate, " +
        "IFNULL(DATE_FORMAT(a.Gate_Out_date, '%d/%b/%Y'), 0) AS loadedOutDate, " +
        "IFNULL(DATE_FORMAT(a.Gate_Out_date, '%H:%i'), 0) AS gateOutTime, " +
        "a.Container_no AS containerNo, " +
        "a.Container_Size AS containerSize, " +
        "a.Container_Type AS containerType, " +
        "o.Party_Name AS ac, " +
        "m.Party_Name AS partyName, " +
        "b.BE_No AS beNo, " +
        "DATE_FORMAT(b.BE_Date, '%d/%b/%Y') AS beDate, " +
        "g.BL_No AS blNo, " +
        "b.Igm_no AS igmNo, " +
        "b.IGM_Line_No AS igmLineNo, " +
        "p.Party_Name AS cha, " +
        "g.Importer_Name AS importerName, " +
        "g.Commodity AS commodity, " +
        "g.Remarks AS remarks, " +
        "a.Profitcentre_Id AS profitcentreId, " +
        "v.vessel_name AS vesselName, " +
        "c.VIA_NO AS viaNo, " +
        "g.Vehicle_No AS vehicleNo, " +
        "g.Transporter AS transporter " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfimportgatepass g ON a.company_id = g.company_id AND a.branch_id = g.branch_id " +
        "AND a.Profitcentre_Id = g.Profitcentre_Id AND a.Gate_Out_Id = g.Gate_Out_Id AND a.Gate_Pass_No = g.Gate_Pass_Id " +
        "LEFT OUTER JOIN cfigmcn b ON g.IGM_Trans_Id = b.IGM_Trans_Id AND g.igm_no = b.igm_no " +
        "AND g.container_no = b.container_no AND g.profitcentre_id = b.profitcentre_id " +
        "AND g.Gate_Out_Id = b.gate_out_id AND g.company_id = b.company_id AND g.branch_id = b.branch_id " +
        "LEFT OUTER JOIN cfigm c ON b.igm_trans_id = c.igm_trans_id AND b.igm_no = c.igm_no " +
        "AND c.company_id = b.company_id AND c.branch_id = b.branch_id AND b.Profitcentre_Id = c.Profitcentre_Id " +
        "LEFT OUTER JOIN cfigmcrg n ON b.igm_trans_id = n.igm_trans_id AND b.igm_no = n.igm_no " +
        "AND n.company_id = b.company_id AND n.branch_id = b.branch_id AND n.profitcentre_id = b.profitcentre_id " +
        "AND b.igm_line_no = n.igm_line_no " +
        "LEFT OUTER JOIN vessel v ON c.company_id = v.company_id AND c.vessel_id = v.vessel_id " +
        "LEFT OUTER JOIN party q ON q.Party_Id = c.Shipping_Agent AND c.Company_Id = q.Company_Id " +
        "LEFT OUTER JOIN party p ON p.Party_Id = b.CHA AND b.Company_Id = p.Company_Id " +
        "LEFT OUTER JOIN party m ON m.Party_Id = c.Shipping_line AND c.Company_Id = m.Company_Id " +
        "LEFT OUTER JOIN party o ON n.oth_party_Id = o.Party_Id AND n.Company_Id = o.Company_Id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Profitcentre_Id IN ('N00002') " +
        "AND ((:partyName IS NULL OR :partyName = '' OR n.oth_party_Id =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.Shipping_Agent =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.Shipping_line =:partyName )) " +
        "AND a.Trans_Type = 'CONT' " +
        "AND a.Container_no != '' " +
        "AND a.Gate_Out_date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' " +
        "AND b.status = 'A' " +
        "AND c.status = 'A' " +
        "GROUP BY a.Gate_Out_Id, a.Container_No " +
        "ORDER BY a.Gate_Out_Id, a.Container_No", 
        nativeQuery = true)
List<Object[]> fetchImportGateOutDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("partyName") String partyName,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate
);




@Query(value = "SELECT DISTINCT a.Container_No AS containerNo, " +
        "a.Container_Size AS size, " +
        "a.Container_Type AS type, " +
        "DATE_FORMAT(r.Gate_In_Date, '%d/%b/%Y') AS gateInDate, " +
        "DATE_FORMAT(r.De_Stuff_Date, '%d/%b/%Y') AS deStuffDate, " +
        "DATE_FORMAT(r.De_Stuff_Date, '%H:%i') AS deStuffTime, " +
        "v.Vessel_Name AS vesselName, " +
        "a.Igm_no AS igmNo, " +
        "a.IGM_Line_No AS igmLineNo, " +
        "qc.Party_Name AS chaName, " +
        "b.Importer_Name AS importerName, " +
        "q.party_name AS shippingAgent, " +
        "'' AS gatepassDate, " +
        "a.BE_No AS beNo, " +
        "b.BL_No AS blNo, " +
        "a.Remarks AS remarks, " +
        "a.Profitcentre_Id AS profitcentreId " +
        "FROM cfigmcn a " +
        "LEFT OUTER JOIN cfdestuffcn r ON a.Company_Id = r.Company_Id " +
        "AND a.Branch_Id = r.Branch_Id " +
        "AND a.De_Stuff_Id = r.De_Stuff_Id " +
        "AND a.Profitcentre_Id = r.Profitcentre_Id " +
        "AND a.Container_No = r.Container_No " +
        "AND a.IGM_Trans_Id = r.IGM_Trans_Id " +
        "AND a.IGM_No = r.IGM_No " +
        "AND a.IGM_Line_No = r.IGM_Line_No " +
        "LEFT OUTER JOIN cfigmcrg b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id " +
        "AND a.Igm_no = b.Igm_no " +
        "AND a.IGM_Line_No = b.IGM_Line_No " +
        "AND a.IGM_Trans_Id = b.IGM_Trans_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "LEFT OUTER JOIN cfigm c ON a.Company_Id = c.Company_Id " +
        "AND a.Branch_Id = c.Branch_Id " +
        "AND a.Igm_no = c.IGM_No " +
        "AND a.IGM_Trans_Id = c.IGM_Trans_Id " +
        "AND a.Profitcentre_Id = c.Profitcentre_Id " +
        "LEFT OUTER JOIN cfdestuffcrg d ON d.Company_Id = r.Company_Id " +
        "AND r.Branch_Id = d.Branch_Id " +
        "AND r.IGM_Trans_Id = d.IGM_Trans_Id " +
        "AND r.Igm_no = d.IGM_No " +
        "AND r.IGM_Line_No = d.IGM_Line_No " +
        "AND r.De_Stuff_Id = d.De_Stuff_Id " +
        "AND r.Profitcentre_Id = d.Profitcentre_Id " +
        "LEFT OUTER JOIN vessel v ON a.Company_Id = v.Company_Id " +
        "AND c.Vessel_Id = v.Vessel_Id " +
        "AND a.branch_id = v.branch_id " +
        "LEFT OUTER JOIN party q ON r.Company_Id = q.Company_Id " +
        "AND r.Shipping_Agent = q.Party_Id " +
        "AND r.branch_id = q.branch_id " +
        "LEFT OUTER JOIN party p ON c.Company_Id = p.Company_Id " +
        "AND c.Shipping_line = p.Party_Id " +
        "AND c.branch_id = p.branch_id " +
        "LEFT OUTER JOIN party qc ON a.Company_Id = qc.Company_Id " +
        "AND a.CHA = qc.Party_Id " +
        "AND a.branch_id = qc.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Profitcentre_Id IN ('N00002') " +
        "AND ((:partyName IS NULL OR :partyName = '' OR b.oth_party_Id =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR r.Shipping_Agent =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.Shipping_line =:partyName )) " +
        "AND a.Gate_Out_Type = 'CRG' " +
        "AND a.De_Stuff_Date BETWEEN :startDate AND :endDate " +
        "AND a.Status = 'A' " +
        "AND a.De_Stuff_Id != '' " +
        "AND d.status = 'A' " +
        "ORDER BY a.De_Stuff_Date ASC",
        nativeQuery = true)
List<Object[]> fetchDeStuffDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("partyName") String partyName,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate
);





@Query(value = "SELECT DISTINCT a.Container_no AS containerNo, " +
        "a.Container_Size AS containerSize, " +
        "a.container_type AS containerType, " +
        "v.vessel_name AS vessel, " +
        "DATE_FORMAT(a.De_Stuff_Date, '%d/%b/%Y') AS deStuffDate, " +
        "s.Remarks AS remarks, " +
        "s.Profitcentre_Id AS profitcentreId " +
        "FROM cfimpinventory a " +
        "LEFT OUTER JOIN cfigmcn s ON a.Company_Id = s.Company_Id " +
        "AND a.Branch_id = s.Branch_id " +
        "AND a.container_no = s.container_no " +
        "AND a.Igm_no = s.igm_no " +
        "AND s.IGM_Trans_Id = a.IGM_Trans_Id " +
        "LEFT OUTER JOIN cfigmcrg b ON s.Company_Id = b.Company_Id " +
        "AND s.Branch_Id = b.Branch_Id " +
        "AND s.Igm_no = b.Igm_no " +
        "AND s.IGM_Line_No = b.IGM_Line_No " +
        "AND s.IGM_Trans_Id = b.IGM_Trans_Id " +
        "AND s.Profitcentre_Id = b.Profitcentre_Id " +
        "LEFT OUTER JOIN cfigm t ON t.Company_Id = s.Company_Id " +
        "AND t.Branch_id = s.Branch_id " +
        "AND t.igm_trans_id = s.igm_trans_id " +
        "AND t.igm_no = s.igm_no " +
        "AND t.Profitcentre_Id = s.Profitcentre_Id " +
        "LEFT OUTER JOIN vessel v ON t.company_id = v.company_id " +
        "AND v.vessel_id = t.vessel_id " +
        "AND t.branch_id = v.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND t.shipping_agent = :onAccountOf " +
        "AND (a.Empty_Out_Id = '' OR a.Empty_Out_Id IS NULL OR a.Empty_Out_Date > :deStuffDate) " +
        "AND (a.De_Stuff_Date != '0000-00-00 00:00:00' AND a.De_Stuff_Date < :deStuffDate) " +
        "AND a.status = 'A' " +
        "GROUP BY a.igm_Trans_Id, a.container_no",
        nativeQuery = true)
List<Object[]> importEmptyBalanceReport(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("onAccountOf") String onAccountOf,
    @Param("deStuffDate") Date deStuffDate
);




@Query(value = "SELECT DISTINCT b.Gate_Pass_Id AS gatePassId, " +
        "DATE_FORMAT(b.Gate_In_Date, '%d/%b/%Y') AS gateInDate, " +
        "DATE_FORMAT(a.Gate_Out_Date, '%d/%b/%Y') AS gateOutDate, " +
        "DATE_FORMAT(a.Gate_Out_Date, '%H:%i') AS gateOutTime, " +
        "a.Container_No AS containerNo, " +
        "a.Container_Size AS containerSize, " +
        "a.Container_Type AS containerType, " +
        "q.Party_Name AS ac, " +
        "qs.Party_Name AS shippingLine, " +
        "d.Container_Status AS status, " +
        "b.Booking_No AS bookingNo, " +
        "b.Vehicle_No AS vehicleNo, " +
        "a.Transporter_Name AS transporterName, " +
        "d.yard_location AS yardLocation, " +
        "v.vessel_name AS vesselName, " +
        "a.VIA_No AS viaNo, " +
        "a.Delivery_Order_No AS deliveryOrderNo, " +
        "a.Comments AS comments, " +
        "b.Profitcentre_Id AS profitcentreId " +
        "FROM cfgateout a " +
        "LEFT OUTER JOIN cfcommongatepass b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id " +
        "AND a.Gate_Out_Id = b.Gate_Out_Id " +
        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
        "AND a.gate_Pass_No = b.Gate_Pass_Id " +
        "AND a.Container_No = b.Container_No " +
        "LEFT OUTER JOIN cfimpinventory d ON d.Company_Id = b.Company_Id " +
        "AND d.Branch_Id = b.Branch_Id " +
        "AND d.Empty_Pass_Id = b.Gate_Pass_Id " +
        "AND d.Profitcentre_Id = b.Profitcentre_Id " +
        "AND d.Empty_Out_Id = b.Gate_Out_Id " +
        "LEFT OUTER JOIN party q ON b.Company_Id = q.Company_Id " +
        "AND b.On_Account_Of = q.Party_Id " +
        "AND b.branch_id = q.branch_id " +
        "LEFT OUTER JOIN party qs ON b.Company_Id = qs.Company_Id " +
        "AND b.SL = qs.Party_Id " +
        "AND b.branch_id = qs.branch_id " +
        "LEFT OUTER JOIN vessel v ON a.company_id = v.company_id " +
        "AND a.VESSEL_ID = v.vessel_id " +
        "AND a.branch_id = v.branch_id " +
        "WHERE a.company_id = :companyId " +
        "AND a.branch_id = :branchId " +
        "AND ((:partyName IS NULL OR :partyName = '' OR b.on_account_of =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR b.sa =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR b.sl =:partyName )) " +
        "AND a.Profitcentre_Id IN ('N00002') " +
        "AND a.Container_Status = 'MTY' " +
        "AND a.Process_Id = 'P00605' " +
        "AND a.Status = 'A' " +
        "AND a.Gate_Out_Date BETWEEN :startDate AND :endDate " +
        "ORDER BY a.Gate_Out_Date",
        nativeQuery = true)
List<Object[]> fetchImportEmptyOutDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("partyName") String partyName,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate
);



@Query(value = "SELECT DISTINCT " +
        "DATE_FORMAT(c.Doc_Date, '%d/%b/%Y %H:%i') AS docDate, " +
        "c.Port AS port, " +
        "DATE_FORMAT(c.Vessel_Arv_Date, '%d/%b/%Y') AS berthing, " +
        "v.vessel_name AS vesselName, " +
        "c.VIA_NO AS viaNo, " +
        "p.party_name AS shippingLine, " +
        "'' AS jobOp, " +
        "'' AS pen, " +
        "a.container_no AS containerNo, " +
        "a.container_size AS containerSize, " +
        "c.Port AS birthingPlace, " +
        "DATE_FORMAT(c.Vessel_ETA, '%d/%b/%Y %H:%i') AS berthTime " +
        "FROM cfigmcn a " +
        "LEFT OUTER JOIN cfigmcrg ds ON ds.Company_Id = a.Company_Id " +
        "AND ds.Branch_id = a.Branch_id " +
        "AND ds.Profitcentre_Id = a.Profitcentre_Id " +
        "AND a.igm_trans_id = ds.igm_trans_id " +
        "AND a.igm_no = ds.igm_no " +
        "AND a.igm_line_no = ds.igm_line_no " +
        "LEFT OUTER JOIN cfigm c ON c.Company_Id = ds.Company_Id " +
        "AND c.Branch_id = ds.Branch_id " +
        "AND c.igm_trans_id = ds.igm_trans_id " +
        "AND c.igm_no = ds.igm_no " +
        "AND c.Profitcentre_Id = ds.Profitcentre_Id " +
        "LEFT OUTER JOIN vessel v ON c.company_id = v.company_id " +
        "AND c.vessel_id = v.vessel_id and c.branch_id =v.branch_id " +
        "LEFT OUTER JOIN party p ON c.company_id = p.company_id " +
        "AND c.Shipping_line = p.party_id and c.branch_id =p.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND ((:partyName IS NULL OR :partyName = '' OR ds.oth_party_Id =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.Shipping_line =:partyName) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.Shipping_agent =:partyName )) " +
        "AND c.port_jo != '' " +
        "AND (a.gate_in_id = '' OR a.gate_in_date > :gateInDate) " +
        "AND c.Doc_date < :gateInDate " +
        "AND a.status = 'A' " +
        "AND c.status = 'A' " +
        "GROUP BY a.gate_in_id, a.container_no",
        nativeQuery = true)
List<Object[]> fetchVesselBerthingDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("partyName") String partyName,
    @Param("gateInDate") Date gateInDate
);
}
