package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfigmcn;
import com.cwms.entities.Cfinbondcrg;

public interface DashboardRepository extends JpaRepository<Cfigmcn, String> {

	
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
	        "LEFT OUTER JOIN party d ON b.company_id = d.company_id AND b.cha = d.party_id " +
	        "LEFT OUTER JOIN party e ON b.company_id = e.company_id AND b.on_account_of = e.party_id " +
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
	
	
	
	
	
	
	
	@Query(value="SELECT COUNT(c.noc_Trans_Id), COALESCE(SUM(c.cargo_Duty), 0),COALESCE(SUM(c.CIF_Value),0),COALESCE(SUM(c.area),0) FROM cfbondnoc c " +
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
		
		@Query(value="SELECT COUNT(c.In_Bonding_Id), COALESCE(SUM(c.cargo_Duty), 0),COALESCE(SUM(c.CIF_Value),0),COALESCE(SUM(c.Area_Occupied),0) FROM cfinbondcrg c " +
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

			
			
			@Query(value="SELECT COUNT(c.Ex_Bonding_Id), COALESCE(SUM(c.Ex_Bonded_Cargo_Duty), 0),COALESCE(SUM(c.Ex_Bonded_CIF),0),COALESCE(SUM(c.Area_Released),0) FROM cfexbondcrg c " +
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
				
				
				
				
				
				
				@Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
					       "c.inBondingDate, c.profitcentreId, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
					       "c.nocDate, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, c.bondingNo, c.bondingDate, p.partyName, c.importerId, c.importerName, " +
					       "cf.commodityDescription, c.grossWeight, c.uom, c.nocPackages, " +
					       "c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, c.exBondedPackages, " +
					       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, cf.cfBondDtlId, " +
					       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
					       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, " +
					       "cx.exBondedInsurance, cx.exBondedCIF, cx.exBondedCargoDuty, cx.exBondedGW, cx.exBondedPackages, cfx.areaReleased) " +
					       "FROM Cfinbondcrg c " +
					       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
					       "LEFT OUTER JOIN CfExBondCrg cfx ON c.companyId = cfx.companyId AND c.branchId = cfx.branchId AND c.inBondingId = cfx.inBondingId " +
					       "AND c.nocTransId = cfx.nocTransId AND cfx.status = 'A' " +
					       "LEFT OUTER JOIN CfinbondcrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.inBondingId = cf.inBondingId " +
					       "AND c.nocTransId = cf.nocTransId AND cf.status = 'A' " +
					       "LEFT OUTER JOIN CfexBondCrgDtl cx ON c.companyId = cx.companyId AND c.branchId = c.branchId AND c.inBondingId = cx.inBondingId  and cf.cfBondDtlId=cx.cfBondDtlId " +
					       "AND c.nocTransId = cx.nocTransId AND cx.status = 'A' " +
					       "WHERE c.companyId = :companyId " +
					       "AND c.branchId = :branchId " +
					       "AND (cf.inBondedPackages - COALESCE(cf.exBondedPackages, 0)) > 0 " +
					       "AND c.inBondingDate <= CURRENT_DATE " + // Replaced endDate with CURRENT_DATE
					       "AND c.status = 'A' " +
					       "ORDER BY c.inBondingDate, c.boeNo")
					List<Cfinbondcrg> getDataForInBondInventoryReport(@Param("companyId") String companyId, 
					                                                   @Param("branchId") String branchId);

				
				@Query(value="SELECT c.cus_app_cargoduty , c.inbond_cargoduty,c.exbond_cargoduty,c.cus_app_cifvalue,c.inbond_cifvalue,c.exbond_cifvalue,c.cus_app_area,c.inbond_ares,c.exbond_area  FROM cfbondinsbal c " +
				
					       "WHERE c.company_Id = :companyId " +
					       "AND c.branch_Id = :branchId " +
					       "AND c.status = 'A'",nativeQuery = true)
					List<Object[]> getCustomeApprovedDuty(
					    @Param("companyId") String companyId,
					    @Param("branchId") String branchId
					);
					
					
					
					
					
					
					
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size, " +
					        "a.in_gate_in_date AS gateindate " +
					        "FROM cfgatein a " +
					        "WHERE a.Company_Id = :companyId " +
					        "AND a.Branch_Id = :branchId " +
					        "AND a.profitcentre_id IN ('N00002') " +
					        "AND a.Status = 'A' " +
					        "AND a.Process_Id = 'P00203' " +
					        "AND a.Container_No <> '' " +
					        "AND a.in_gate_in_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
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
					        "AND a.gate_in_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE", nativeQuery = true)
					List<Object[]> findDistinctGateInDataOfLastWeek(@Param("companyId") String companyId,
					                               @Param("branchId") String branchId);
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size,a.gate_out_date, a.container_type " +
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
					        "AND a.gate_out_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE ", nativeQuery = true)
					List<Object[]> findDistinctFCLGateOutContainersLastWeek(@Param("companyId") String companyId,
					                                      @Param("branchId") String branchId);
					
					
					
					
					
					
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size,a.de_stuff_date, a.container_type " +
					        "FROM cfigmcn a " +
					        "WHERE a.company_id = :companyId " +
					        "AND a.branch_id = :branchId " +
					        "AND a.profitcentre_id IN ('N00002') " +
					        "AND a.status = 'A' " +
					        "AND a.container_status = 'FCL' " +
					        "AND a.de_stuff_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "GROUP BY a.container_no, a.igm_no", 
					nativeQuery = true)
					List<Object[]> findDistinctFCLContainersLstWeek(@Param("companyId") String companyId,
					                                  @Param("branchId") String branchId);
					
					
					
					
					
					
					@Query(value = "SELECT DISTINCT b.container_no, b.container_size,b.Gate_in_Date, b.container_type, b.ODC_Status, b.Type_of_container, 'N' " +
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
					        "AND (b.gate_out_id = '' OR b.gate_out_date > CURRENT_DATE) " +
					        "AND (b.de_stuff_id = '' OR b.de_stuff_Date > CURRENT_DATE) " +
					        "AND b.gate_in_id != '' " +
					        "AND b.Gate_in_Date < CURRENT_DATE " +
					        "GROUP BY b.container_no, b.igm_no " +
					        "UNION " +
					        "SELECT DISTINCT a.container_no, a.container_size,'', a.container_type, a.ODC_Status, 'Manual', a.Hazardous " +
					        "FROM cfgatein a " +
					        "WHERE a.Company_Id = :companyId " +
					        "AND a.Branch_Id = :branchId " +
					        "AND a.Process_Id = 'P00212' " +
					        "AND a.status = 'A'", nativeQuery = true)
					List<Object[]> getLoadedInventoryDataLastWeek(@Param("companyId") String companyId,
					                                          @Param("branchId") String branchId);
					
					
					@Query(value = "SELECT DISTINCT a.Container_No, a.Container_Size, a.in_Gate_In_Date, a.Container_Type, a.Gate_In_Type " +
					        "FROM cfgatein a " +
					        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        "AND a.status = 'A' AND a.Profitcentre_Id = 'N00004' " +
//					        "AND a.Gate_In_Type IN ('Buffer', 'ONWH', 'FDS') " +
					"AND a.Gate_In_Type IN ('Buffer') " +
					        "AND a.Process_Id = 'P00234' " +
					        "AND a.in_Gate_In_Date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "GROUP BY a.Container_No " +
					        "ORDER BY a.in_Gate_In_Date ASC", 
					nativeQuery = true)
					List<Object[]> exportBufferGateInLastWeek(@Param("companyId") String companyId,
					                             @Param("branchId") String branchId);
					
					
					
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.Gate_Out_Date AS gateOutDate, " +
					        "a.vehicle_no,  a.container_type, a.trans_type " +
					        "FROM cfgateout a " +
					        "LEFT OUTER JOIN cfexpmovementreq c ON a.company_id = c.company_id " +
					        "AND a.branch_id = c.branch_id AND a.gate_out_id = c.gate_out_id " +
					        "AND c.container_no = a.container_no " +
					        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        "AND a.Status = 'A' AND a.gate_out_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "AND a.trans_type IN ('CONT', 'MOVE', 'BOWC') " +
					        "AND a.Profitcentre_Id = 'N00004' " +
					        "GROUP BY a.container_no, a.gate_out_id", 
					nativeQuery = true)
					List<Object[]> exportEmptyMovementOutLastWeek(@Param("companyId") String companyId,
					                              @Param("branchId") String branchId);
					
					
					
					
					
					@Query(value = "SELECT DISTINCT a.Container_no, a.container_size ,a.stuff_tally_date " +
					        "FROM cfstufftally a " +
					        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        "AND a.Status = 'A' AND a.Profitcentre_Id = 'N00004' " +
					        "AND a.stuff_tally_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "AND a.MOVEMENT_TYPE = 'CLP' " +
					        "GROUP BY a.Container_no", 
					nativeQuery = true)
					List<Object[]> findStuffTallyContainersLastWeek(@Param("companyId") String companyId,
					                                 @Param("branchId") String branchId);

					
					
					
					
					
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.gate_out_date " +
					        "FROM cfgateout a " +
					        "LEFT OUTER JOIN cfcommongatepass b ON a.gate_pass_no = b.gate_pass_id " +
					        "AND a.container_no = b.container_no " +
					        "AND a.company_id = b.company_id " +
					        "AND a.branch_id = b.branch_id " +
					        "WHERE a.company_id = :companyId " +
					        "AND a.branch_id = :branchId " +
					        "AND a.status = 'A' " +
//					        "AND a.profitcentre_id IN ('N00002') " +
					"  AND a.Profitcentre_Id IN ('N00004','N00002') " +
					        "AND a.gate_out_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "AND a.container_status = 'MTY'", 
					nativeQuery = true)
					List<Object[]> findDistinctMTYContainersLastWeek(@Param("companyId") String companyId,
					                                  @Param("branchId") String branchId);
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size,a.de_stuff_date, a.container_type " +
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
					        "AND a.de_stuff_date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "GROUP BY a.container_no, a.igm_no", 
					nativeQuery = true)
					List<Object[]> findDistinctLCLContainersLastWeek(@Param("companyId") String companyId,
					                                  @Param("branchId") String branchId);
					
					@Query(value = "(SELECT DISTINCT a.container_no, a.container_size,a.Stuff_Req_Date, a.Container_Type " +
					        " FROM cfexpinventory a " +
					        " WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        " AND a.Status = 'A' " +
					        " AND (a.Gate_Out_Id = '' OR a.Gate_Out_Date > CURRENT_DATE ) " +
					        " AND (a.Stuff_Req_Date != '0000-00-00 00:00:00' AND a.Stuff_Req_Date < CURRENT_DATE) " +
					        " AND a.Container_Status = 'MTY' " +
					        " AND a.cycle != 'Hub' " +
					        " GROUP BY a.container_no) " +
					        "UNION " +
					        "(SELECT DISTINCT a.container_no, a.container_size,'', a.Container_Type " +
					        " FROM cfexpinventory a " +
					        " WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        " AND a.Status = 'A' " +
					        " AND (a.Gate_Out_Id = '' OR a.Gate_Out_Date > CURRENT_DATE) " +
					        " AND a.Container_Status IN ('LDD', 'FCL') " +
					        " GROUP BY a.container_no)", 
					nativeQuery = true)
					List<Object[]> findExpLDDInventoryContainersLastWeek(@Param("companyId") String companyId,
					                                   @Param("branchId") String branchId);
					
					
					
					
					
					
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size, a.container_type " +
					        "FROM cfexpinventory a " +
					        "WHERE a.company_id = :companyId " +
					        "AND a.branch_id = :branchId " +
					        "AND (a.stuff_req_id = '' OR a.stuff_req_date > CURRENT_DATE) " +
					        "AND (a.empty_pass_id = '' OR a.empty_pass_id IS NULL ) " +
					        "AND a.gate_in_date < CURRENT_DATE " +
					        "AND a.status = 'A' " +
					        "AND a.container_status = 'MTY' " +
					        "GROUP BY a.container_no", 
					nativeQuery = true)
					List<Object[]> getInventoryExportReport(@Param("companyId") String companyId, 
					        @Param("branchId") String branchId);
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size,a.in_Gate_In_Date, a.container_type " +
					        "FROM cfgatein a " +
					        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        "AND a.status = 'A' AND a.Profitcentre_Id = 'N00004' " +
					        "AND a.Gate_In_Type = 'EXP' "
					        + "AND a.Process_Id = 'P00219' " +
					        "AND a.in_Gate_In_Date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "GROUP BY a.container_no", 
					nativeQuery = true)
					List<Object[]> findExportEMptyContainerGateInLastWeek(@Param("companyId") String companyId,
					        @Param("branchId") String branchId);
					
					@Query(value = "SELECT DISTINCT a.container_no, a.container_size,a.gate_in_date, a.container_type " +
					        "FROM cfexpinventory a " +
					        "WHERE a.company_id = :companyId " +
					        "AND a.branch_id = :branchId " +
					        "AND (a.stuff_req_id = '' OR a.stuff_req_date > CURRENT_DATE) " +
					        "AND a.empty_pass_id = '' " +
//					        "AND a.gate_in_date < CURRENT_DATE " +
"AND a.gate_in_date  BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
					        "AND a.status = 'A' " +
					        "AND a.container_status = 'MTY' " +
					        "GROUP BY a.container_no", 
					nativeQuery = true)
					List<Object[]> getInventoryExportReportLastWeek(@Param("companyId") String companyId, 
					        @Param("branchId") String branchId);
					
					
					
					
					
					
					@Query(value = "SELECT DISTINCT DATE_FORMAT(a.Gate_Out_Date, '%d %b %Y %T') AS gateOutDate, " +
					        "a.vehicle_no, a.container_no, a.container_size,c.mov_req_type, a.container_type, a.trans_type " +
					        "FROM cfgateout a " +
					        "LEFT OUTER JOIN cfexpmovementreq c ON a.company_id = c.company_id " +
					        "AND a.branch_id = c.branch_id AND a.gate_out_id = c.gate_out_id " +
					        "AND c.container_no = a.container_no " +
					        "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId " +
					        "AND a.Status = 'A' AND a.gate_out_date BETWEEN :startDate AND CURRENT_DATE " +
					        "AND a.trans_type IN ('CONT', 'MOVE', 'BOWC') " +
					        "AND c.mov_req_type IN ('CLP', 'Buffer', 'ONWH') " +
					        "AND a.Profitcentre_Id = 'N00004' " +
					        "GROUP BY a.container_no, a.gate_out_id", 
					nativeQuery = true)
					List<Object[]> exportEmptyMovementOutBarChart(@Param("companyId") String companyId,
					                              @Param("branchId") String branchId,
					                              @Param("startDate") Date startDate);
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					@Query(value = 
						    "SELECT b.Profitcentre_Desc, COUNT(a.Invoice_No), " + 
						    "SUM(a.Local_Amt), SUM(a.Invoice_Amt) " + 
						    "FROM cfinvsrv a " + 
						    "LEFT OUTER JOIN profitcentre b " + 
						    "ON a.Company_Id = b.Company_Id " + 
						    "AND a.Profitcentre_Id = b.Profitcentre_Id " + 
						    "WHERE a.Status = 'A' " + 
						    "AND a.Company_Id = :companyId " + 
						    "AND a.Branch_Id = :branchId " + 
						    "AND a.Created_Date BETWEEN :startDate AND :endDate " + 
						    "GROUP BY a.Profitcentre_Id " + 
						    "ORDER BY b.Profitcentre_Desc", 
						    nativeQuery = true)
						List<Object[]> findInvoiceSummary(@Param("companyId") String companyId,
							    @Param("branchId") String branchId,
							    @Param("startDate") Date startDate,
							    @Param("endDate") Date endDate
							);
						
						
						
						
						
						
						
						
						@Query(value = 
							    "SELECT b.Profitcentre_Desc, COUNT(a.Invoice_No), " + 
							    "SUM(a.Local_Amt), SUM(a.Invoice_Amt), DATE(a.Created_Date) " +  // Ensure date grouping
							    "FROM cfinvsrv a " + 
							    "LEFT OUTER JOIN profitcentre b " + 
							    "ON a.Company_Id = b.Company_Id " + 
							    "AND a.Profitcentre_Id = b.Profitcentre_Id " + 
							    "WHERE a.Status = 'A' " + 
							    "AND a.Company_Id = :companyId " + 
							    "AND a.Branch_Id = :branchId " + 
							    "AND a.Created_Date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " + 
							    "GROUP BY a.Profitcentre_Id, DATE(a.Created_Date) " +  // Include DATE(a.Created_Date)
							    "ORDER BY b.Profitcentre_Desc, DATE(a.Created_Date)",  // Ensure sorting by date
							    nativeQuery = true)
							List<Object[]> findInvoiceSummaryLastWeek(@Param("companyId") String companyId,
							                                          @Param("branchId") String branchId);

							
							@Query(value = 
							        "SELECT a.Profitcentre_Id, b.Profitcentre_Desc, COUNT(DISTINCT a.Trans_Id), " + 
							        "SUM(a.Document_Amt),DATE(a.Created_Date) " + 
							        "FROM fintrans a " + 
							        "LEFT OUTER JOIN profitcentre b " + 
							        "ON a.Company_Id = b.Company_Id " + 
							        "AND a.Profitcentre_Id = b.Profitcentre_Id " + 
							        "WHERE a.Status = 'A' " + 
							        "AND a.Company_Id = :companyId " + 
							        "AND a.Branch_Id = :branchId " + 
							        "AND a.Created_Date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " + 
							        "AND a.Credit_Type = 'N' " + 
							        "AND a.Payment_Mode NOT IN ('ADVANCE', 'CREDIT') " + 
							        "AND a.Ledger_Type = 'AR' " + 
							        "AND a.Doc_Type IN ('AD', 'RE') " + 
							        "GROUP BY a.Profitcentre_Id, DATE(a.Created_Date) " +  // Include DATE(a.Created_Date)
								    "ORDER BY b.Profitcentre_Desc, DATE(a.Created_Date)",  // Ensure sorting by date
							        nativeQuery = true)
							List<Object[]> findInvoiceTotalCollectionLastWeek(@Param("companyId") String companyId,
									 @Param("branchId") String branchId);
							

						@Query(value = 
						        "SELECT a.Profitcentre_Id, b.Profitcentre_Desc, COUNT(DISTINCT a.Trans_Id), " + 
						        "SUM(a.Document_Amt) " + 
						        "FROM fintrans a " + 
						        "LEFT OUTER JOIN profitcentre b " + 
						        "ON a.Company_Id = b.Company_Id " + 
						        "AND a.Profitcentre_Id = b.Profitcentre_Id " + 
						        "WHERE a.Status = 'A' " + 
						        "AND a.Company_Id = :companyId " + 
						        "AND a.Branch_Id = :branchId " + 
						        "AND a.Created_Date BETWEEN :startDate AND :endDate " + 
						        "AND a.Credit_Type = 'N' " + 
						        "AND a.Payment_Mode NOT IN ('ADVANCE', 'CREDIT') " + 
						        "AND a.Ledger_Type = 'AR' " + 
						        "AND a.Doc_Type IN ('RE') " + 
						        "GROUP BY a.Profitcentre_Id " + 
						        "ORDER BY b.Profitcentre_Desc", 
						        nativeQuery = true)
						List<Object[]> findInvoiceTotalCollection(@Param("companyId") String companyId,
								 @Param("branchId") String branchId,
								    @Param("startDate") Date startDate,
								    @Param("endDate") Date endDate);
						
						
						@Query(value = 
						        "SELECT a.Profitcentre_Id, b.Profitcentre_Desc, COUNT(DISTINCT a.Trans_Id), " + 
						        "SUM(a.Document_Amt) " + 
						        "FROM fintrans a " + 
						        "LEFT OUTER JOIN profitcentre b " + 
						        "ON a.Company_Id = b.Company_Id " + 
						        "AND a.Profitcentre_Id = b.Profitcentre_Id " + 
						        "WHERE a.Status = 'A' " + 
						        "AND a.Company_Id = :companyId " + 
						        "AND a.Branch_Id = :branchId " + 
						        "AND a.Created_Date BETWEEN :startDate AND :endDate " + 
						        "AND a.Payment_Mode NOT IN ('ADVANCE', 'CREDIT') " + 
						        "AND a.Ledger_Type = 'AR' " + 
						        "AND a.Doc_Type IN ('AD') " + 
						        "GROUP BY a.Profitcentre_Id " + 
						        "ORDER BY b.Profitcentre_Desc", 
						        nativeQuery = true)
						List<Object[]> findInvoiceAdvance(@Param("companyId") String companyId,
								 @Param("branchId") String branchId,
								    @Param("startDate") Date startDate,
								    @Param("endDate") Date endDate);
						
						
						
						@Query(value = 
							    "SELECT a.Profitcentre_Id, b.Profitcentre_Desc, COUNT(a.Invoice_No), " +
							    "SUM(a.Invoice_Amt), (SUM(a.Invoice_Amt) - SUM(a.Receipt_Amt)) AS OutStandings " +
							    "FROM cfinvsrv a " +
							    "LEFT OUTER JOIN profitcentre b " +
							    "ON a.Company_Id = b.Company_Id " +
							    "AND a.Profitcentre_Id = b.Profitcentre_Id " +
							    "WHERE a.Status = 'A' " +
							    "AND a.Company_Id = :companyId " +
							    "AND a.Branch_Id = :branchId " +
							    "AND a.Created_Date BETWEEN :startDate AND :endDate " +
							    "AND a.Credit_Type = 'Y' " +
							    "GROUP BY a.Profitcentre_Id " +
							    "HAVING (SUM(a.Invoice_Amt) - SUM(a.Receipt_Amt)) > 0 " +
							    "ORDER BY b.Profitcentre_Desc", 
							    nativeQuery = true)
							List<Object[]> findOutstandingInvoices(
							    @Param("companyId") String companyId,
							    @Param("branchId") String branchId,
							    @Param("startDate") Date startDate,
							    @Param("endDate") Date endDate
							);
							
							@Query(value = 
								    "SELECT a.Profitcentre_Id, b.Profitcentre_Desc, COUNT(a.Invoice_No), " +
								    "SUM(a.Invoice_Amt), (SUM(a.Invoice_Amt) - SUM(a.Receipt_Amt)) AS OutStandings ,DATE(a.Created_Date) " +
								    "FROM cfinvsrv a " +
								    "LEFT OUTER JOIN profitcentre b " +
								    "ON a.Company_Id = b.Company_Id " +
								    "AND a.Profitcentre_Id = b.Profitcentre_Id " +
								    "WHERE a.Status = 'A' " +
								    "AND a.Company_Id = :companyId " +
								    "AND a.Branch_Id = :branchId " +
								    "AND a.Created_Date BETWEEN DATE_SUB(CURRENT_DATE, INTERVAL 7 DAY) AND CURRENT_DATE " +
								    "AND a.Credit_Type = 'Y' " +
								    "GROUP BY a.Profitcentre_Id " +
								    "HAVING (SUM(a.Invoice_Amt) - SUM(a.Receipt_Amt)) > 0 " +
								    "ORDER BY b.Profitcentre_Desc , DATE(a.Created_Date) ", 
								    nativeQuery = true)
								List<Object[]> findOutstandingInvoicesLastWeek(
								    @Param("companyId") String companyId,
								    @Param("branchId") String branchId
								);
								
								
								
								
								
								
								
								
								
								 @Query(value = "SELECT a.Profitcentre_Id, " +
								            "CONCAT(a.Payment_Mode, '-', b.Profitcentre_Desc) AS Paymode, " +
								            "a.Payment_Mode, COUNT(DISTINCT a.Trans_Id), " +
								            "SUM(a.Document_Amt) " +
								            "FROM fintrans a " +
								            "LEFT OUTER JOIN profitcentre b " +
								            "ON a.Company_Id = b.Company_Id " +
								            "AND a.Profitcentre_Id = b.Profitcentre_Id " +
								            "WHERE a.Status = 'A' " +
								            "AND a.Company_Id = :companyId " +
								            "AND a.Branch_Id = :branchId " +
								            "AND a.Created_Date BETWEEN :startDate AND :endDate " +
								            "AND a.Ledger_Type = 'AR' " +
								            "AND a.Doc_Type IN ('AD', 'RE') " +
								            "AND a.Payment_Mode != 'TDS' " +
								            "GROUP BY Paymode " +
								            "ORDER BY Paymode",
								            nativeQuery = true)
								    List<Object[]> findPaymentSummaryForPieChart(
								            @Param("companyId") String companyId,
								            @Param("branchId") String branchId,
								            @Param("startDate") Date startDate,
								            @Param("endDate") Date endDate
								    );

								   
								    
								    @Query(value = "SELECT " + 
								               "    a.Party_Id as partyId, " + 
								               "    p.Party_Name as partyName, " + 
								               "    COUNT(a.Invoice_No) as invoiceCount, " + 
								               "    SUM(a.Invoice_Amt) as totalInvoiceAmount, " + 
								               "    (SUM(a.Invoice_Amt) - SUM(a.Receipt_Amt)) as Total_Outstanding " + 
								               "FROM " + 
								               "    cfinvsrv a " + 
								               "LEFT JOIN " + 
								               "    party p " + 
								               "    ON a.Company_Id = p.Company_Id " + 
								               "    AND a.Branch_Id = p.Branch_Id " + 
								               "    AND a.Party_Id = p.Party_Id " + 
								               "WHERE " + 
								               "    a.Status = 'A' " + 
								               "    AND a.Company_Id = :companyId " + 
								               "    AND a.Branch_Id = :branchId " + 
								               "    AND a.Created_Date BETWEEN :startDate AND :endDate " + 
								               "    AND a.Credit_Type = 'Y' " + 
								               "GROUP BY a.Party_Id " + 
								               "HAVING " + 
								               "    Total_Outstanding > 0 " + 
								               "ORDER BY " + 
								               "    Total_Outstanding DESC " + 
								               "LIMIT 10", 
								       nativeQuery = true)
								List<Object[]> findTopOutstandingInvoices(
								    @Param("companyId") String companyId,
								    @Param("branchId") String branchId,
								    @Param("startDate") Date startDate,
								    @Param("endDate") Date endDate
								);


}
