package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GateIn;

public interface ImportReportsRepository extends JpaRepository<GateIn, String> {

	
	@Query("SELECT c.terminal, v.vesselName, c.viaNo, i.voyageNo, p.partyName, pa.partyName, c.docRefNo, c.lineNo, "
		     + "c.containerNo, c.containerSize, c.containerType, c.isoCode, '', c.tareWeight, cfi.scannerType, "
		     + "'', c.containerSealNo, c.remarks, c.holdRemarks, c.cargoType, cfcrg.commodityDescription, cfcrg.importerName, '', "
		     + "c.jobOrderId,DATE_FORMAT(c.jobDate,'%d/%m/%Y') ,DATE_FORMAT(c.inGateInDate,'%d/%m/%Y'), '', c.vehicleNo, c.containerStatus, c.transporterName, c.lowBed, '', "
		     + "c.blNo, c.comments, "
		     + "CONCAT(TIMESTAMPDIFF(DAY, c.inGateInDate, i.createdDate), ' days') AS dwellTime, "
		     + "cfcrg.accountHolderName,'',"
		     + "cfcrg.imoCode,cfcrg.unNo, cfcrg.commodityDescription, c.yardLocation, c.yardBlock, c.yardCell "
		     + "FROM GateIn c "
		     + "LEFT OUTER JOIN Party p ON p.companyId = c.companyId AND p.branchId = c.branchId AND p.partyId = c.sl "
		     + "LEFT OUTER JOIN Party pa ON pa.companyId = c.companyId AND pa.branchId = c.branchId AND pa.partyId = c.sa "
//		     + "LEFT OUTER JOIN Party pac ON pac.companyId = c.companyId AND pac.branchId = c.branchId AND pac.partyId = cfcrg.accountHolderId "
		     + "LEFT OUTER JOIN Vessel v ON v.companyId = c.companyId AND v.branchId = c.branchId AND v.vesselId = c.vessel "
		     + "LEFT OUTER JOIN Cfigmcn cfi ON cfi.companyId = c.companyId AND cfi.branchId = c.branchId AND cfi.igmTransId = c.erpDocRefNo AND cfi.igmNo = c.docRefNo and cfi.igmLineNo=c.lineNo and cfi.containerNo=c.containerNo "
		     + "LEFT OUTER JOIN CFIgm i ON i.companyId = c.companyId AND i.branchId = c.branchId AND i.igmTransId = c.erpDocRefNo AND i.igmNo = c.docRefNo "
		     + "LEFT OUTER JOIN Cfigmcrg cfcrg ON cfcrg.companyId = c.companyId AND cfcrg.branchId = c.branchId AND cfcrg.igmTransId = c.erpDocRefNo AND cfcrg.igmNo = c.docRefNo and cfcrg.igmLineNo =c.lineNo "
		     + "WHERE c.companyId = :companyId "
		     + "AND c.branchId = :branchId "
		     + "AND (:docRefNo IS NULL OR :docRefNo = '' OR c.docRefNo = :docRefNo) "
		     + "AND (:lineNo IS NULL OR :lineNo = '' OR c.lineNo = :lineNo) "
		     + "AND (c.inGateInDate BETWEEN :startDate AND :endDate) "
		     + "AND (:cha IS NULL OR :cha = '' OR c.cha = :cha) "
		     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR cfcrg.accountHolderId = :accountHolderId) "
		     + "AND (:sl IS NULL OR :sl = '' OR c.sl = :sl) "
		     + "AND c.gateInType ='IMP'"
		     + "AND c.status ='A'"
		     + "ORDER BY c.inGateInDate")
		List<Object[]> getDataForImportGateInContainerDetailedReport(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("startDate") Date startDate,
		    @Param("endDate") Date endDate,
		    @Param("docRefNo") String docRefNo,
		    @Param("lineNo") String lineNo,
		    @Param("sl") String sl,
		    @Param("accountHolderId") String accountHolderId,
		    @Param("cha") String cha
		);
		
		
		
		
		
		
		
		
		
		
		
		@Query("SELECT c.igmNo, c.igmLineNo, cfcrg.blNo,"
				+" DATE_FORMAT(cfcrg.blDate, '%d/%m/%Y') AS blDate ," 
			     + "c.containerNo, c.containerSize, c.containerType, "
			     +"DATE_FORMAT(c.gateInDate,'%d/%m/%Y') ,"
			     + "p.partyName, cfcrg.beNo, "
			     +"DATE_FORMAT(c.beDate,'%d/%m/%Y') ,"
			     +"DATE_FORMAT(c.sealCutReqDate,'%d/%m/%Y') "
			     + "FROM Cfigmcn c "
			     + "LEFT OUTER JOIN Cfigmcrg cfcrg ON cfcrg.companyId = c.companyId AND cfcrg.branchId = c.branchId AND cfcrg.igmTransId = c.igmTransId AND cfcrg.igmNo = c.igmNo and cfcrg.igmLineNo =c.igmLineNo and cfcrg.status ='A' "
			     + "LEFT OUTER JOIN Party p ON p.companyId = c.companyId AND p.branchId = c.branchId AND p.partyId = c.cha "
			     + "WHERE c.companyId = :companyId "
			     + "AND c.branchId = :branchId "
			     + "AND (:igmNo IS NULL OR :igmNo = '' OR c.igmNo = :igmNo) "
			     + "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR c.igmLineNo = :igmLineNo) "
			     + "AND (c.sealCutReqDate BETWEEN :startDate AND :endDate) "
			     + "AND (:cha IS NULL OR :cha = '' OR c.cha = :cha) "
			     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR cfcrg.accountHolderId = :accountHolderId) "
			     + "AND (:sl IS NULL OR :sl = '' OR c.cha = :sl) "
			     + "AND cfcrg.primaryItem ='Y'"
			     + "AND c.status ='A'"
			     +"GROUP BY c.containerNo "
			     + "ORDER BY c.sealCutReqDate")
			List<Object[]> getDataForSealCuttingReport(
			    @Param("companyId") String companyId,
			    @Param("branchId") String branchId,
			    @Param("startDate") Date startDate,
			    @Param("endDate") Date endDate,
			    @Param("igmNo") String igmNo,
			    @Param("igmLineNo") String igmLineNo,
			    @Param("sl") String sl,
			    @Param("accountHolderId") String accountHolderId,
			    @Param("cha") String cha
			);
			
			
			@Query(value = "SELECT a.Container_No, a.Container_Size, c.Commodity_Description, IFNULL(a.Gross_Wt, '0.00'), " +
		            "DATE_FORMAT(a.Gate_In_Date, '%d/%b/%Y %H:%i'), a.Igm_no, a.IGM_Line_No,  m.Party_Name,  " +
		            "c.Importer_Name, DATEDIFF(CURDATE(), a.Gate_In_Date + 1) as Days, b.Shipping_line ," +
		            "p.party_Name, ac.CUSTOMER_CODE ,a.CHA " +
		            "FROM cfigmcn a " +
		            "LEFT OUTER JOIN cfigmcrg c ON a.Company_Id = c.Company_Id AND a.Branch_Id = c.Branch_Id " +
		            "AND a.Profitcentre_Id = c.Profitcentre_Id AND a.Igm_no = c.Igm_no " +
		            "AND a.IGM_Trans_Id = c.IGM_Trans_Id AND a.IGM_Line_No = c.IGM_Line_No " +
		            "LEFT OUTER JOIN cfigm b ON a.Company_Id = b.Company_Id AND a.Branch_Id = b.Branch_Id " +
		            "AND a.Profitcentre_Id = b.Profitcentre_Id " +
		            "AND a.IGM_Trans_Id = b.IGM_Trans_Id AND a.Igm_no = b.Igm_no " +
		            "LEFT OUTER JOIN party m ON b.Company_Id = m.Company_Id AND b.Shipping_line = m.Party_Id " +
		            "LEFT OUTER JOIN party p ON a.Company_Id = p.Company_Id AND a.CHA = p.Party_Id " +
		            "LEFT OUTER JOIN party ac ON b.Company_Id = ac.Company_Id AND b.Shipping_Agent = ac.Party_Id " +
		            "WHERE a.company_id = :companyId AND"
		            + " a.branch_id = :branchId "
//		            + "AND (c.sealCutReqDate BETWEEN :startDate AND :endDate) "
		            + "AND a.Gate_In_Date < :endDate " 
//		            + "AND (:igmNo IS NULL OR :igmNo = '' OR a.igmNo = :igmNo) "
//				     + "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR a.igmLineNo = :igmLineNo) "
//				     + "AND (:cha IS NULL OR :cha = '' OR a.cha = :cha) "
//				     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR cfcrg.accountHolderId = :accountHolderId) "
//				     + "AND (:sl IS NULL OR :sl = '' OR c.Shipping_line = :sl) "
		            + "AND a.status = 'A'",
		            nativeQuery = true)
		    List<Object[]> findDataForLongStandingReport( @Param("companyId") String companyId,
				    @Param("branchId") String branchId,
//				    @Param("startDate") Date startDate,
				    @Param("endDate") Date endDate
//				    @Param("igmNo") String igmNo,
//				    @Param("igmLineNo") String igmLineNo,
//				    @Param("cha") String cha,
//				    @Param("accountHolderId") String accountHolderId,
//				    @Param("sl") String sl
				    );
		
		    @Query(value = 
		    	    "SELECT " +
		    	        "a.IGM_No, " +
		    	        "a.IGM_Line_No, " +
		    	        "a.Container_No, " +
		    	        "cn.Container_Size, " +
		    	        "cn.Container_Type, " +
		    	        "cn.BE_No, " +
		    	        "DATE_FORMAT(cn.BE_Date, '%d/%b/%Y %T') AS BEDate, " +
		    	        "DATE_FORMAT(cn.Gate_In_Date, '%d/%b/%Y %T') AS GateInDate, " +
		    	        "a.Hold_Remarks, " +
		    	        "g.Importer_Name, " +
		    	        "p.Party_Name AS Shipping_Agent, " +
		    	        "cn.Container_Status, " +
		    	        "u.User_Name, " +
		    	        "a.Holding_AGency AS Hold_Type, " +
		    	        "DATE_FORMAT(a.Hold_Date, '%d/%b/%Y %T') AS HoldDate, " +
		    	        "DATE_FORMAT(a.Release_Date, '%d/%b/%Y %T') AS ReleaseDate, " +
		    	        "a.Release_Remarks, " +
		    	        "g.Commodity_Description, " +
		    	        "DATE_FORMAT(cn.Gate_out_Date, '%d/%b/%Y %T') AS GateOutDate, " +
		    	        "DATE_FORMAT(cn.Part_De_Stuff_Date, '%d/%b/%Y %T') AS DestuffDate ," +
		    	        "i.Shipping_Agent " +
		    	    "FROM " +
		    	        "cfholddtl a " +
		    	    "LEFT OUTER JOIN cfigmcn cn " +
		    	        "ON cn.Company_Id = a.Company_Id " +
		    	        "AND cn.Branch_Id = a.Branch_Id " +
		    	        "AND cn.Igm_no = a.IGM_No " +
		    	        "AND cn.IGM_Trans_Id = a.IGM_Trans_Id " +
		    	        "AND cn.IGM_Line_No = a.IGM_Line_No " +
		    	    "LEFT OUTER JOIN cfigmcrg g " +
		    	        "ON g.Company_Id = cn.Company_Id " +
		    	        "AND g.Branch_Id = cn.Branch_Id " +
		    	        "AND g.IGM_Trans_Id = cn.IGM_Trans_Id " +
		    	        "AND g.IGM_No = cn.IGM_No " +
		    	        "AND g.IGM_Line_No = cn.IGM_Line_No " +
		    	        "AND g.Profitcentre_Id = cn.Profitcentre_Id " +
		    	    "LEFT OUTER JOIN cfigm i " +
		    	        "ON i.Company_Id = g.Company_Id " +
		    	        "AND i.Branch_Id = g.Branch_Id " +
		    	        "AND i.IGM_Trans_Id = g.IGM_Trans_Id " +
		    	        "AND i.IGM_No = g.IGM_No " +
		    	        "AND i.Profitcentre_Id = g.Profitcentre_Id " +
		    	    "LEFT OUTER JOIN party p " +
		    	        "ON p.Company_Id = i.Company_Id " +
		    	        "AND p.Default_Branch = i.Branch_Id " +
		    	        "AND i.Shipping_Agent = p.Party_Id " +
		    	    "LEFT OUTER JOIN userinfo u " +
		    	        "ON u.Company_Id = a.Company_Id " +
		    	        "AND u.Branch_Id = a.Branch_Id " +
		    	        "AND u.User_Id = a.Created_By " +
		    	    "WHERE " +
		    	        "a.company_id = :companyId " +
		    	        "AND a.branch_id = :branchId " +
		    	        "AND cn.Gate_In_Date BETWEEN :startDate AND :endDate " 
		    	        + "AND (:igmNo IS NULL OR :igmNo = '' OR cn.IGM_No = :igmNo) "
					     + "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR cn.IGM_Line_No = :igmLineNo) "
					     + "AND (:sl IS NULL OR :sl = '' OR i.Shipping_line = :sl) "
					     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR g.Account_holder_id = :accountHolderId) "
					     + "AND (:cha IS NULL OR :cha = '' OR cn.cha = :cha) "
		    	     + "GROUP BY " +
		    	        "a.Container_No " +
		    	    "ORDER BY " +
		    	        "cn.Gate_In_Date",
		    	    nativeQuery = true)
		    	List<Object[]> findHoldDetails(
		    	    @Param("companyId") String companyId,
		    	    @Param("branchId") String branchId,
		    	    @Param("startDate") Date startDate,
		    	    @Param("endDate") Date endDate,
		    	    @Param("igmNo") String igmNo,
				    @Param("igmLineNo") String igmLineNo,
				    @Param("sl") String sl,
				    @Param("accountHolderId") String accountHolderId,
				    @Param("cha") String cha
				    
		    	);
		    	
		    	
		    	
		    	@Query(value = 
		    		    "SELECT " +
		    		        "a.Container_No, " +
		    		        "a.Container_Size, " +
		    		        "a.Container_Type, " +
		    		        "a.Igm_no, " +
		    		        "a.IGM_Line_No, " +
		    		        "DATE_FORMAT(f.IGM_Date, '%d-%m-%Y %T') AS IGMDate, " +
		    		        "c.bl_no, " +
		    		        "DATE_FORMAT(c.bl_Date, '%d-%m-%Y %T') AS BLDate, " +
		    		        "f.Voyage_No, " +
		    		        "DATE_FORMAT(a.Gate_In_Date, '%d-%m-%Y %H:%i') AS GateInDate, " +
		    		        "DATE_FORMAT(a.De_stuff_Date, '%d-%m-%Y %H:%i') AS DestuffDate, " +
		    		        "IFNULL(a.No_Of_Packages, '0.00') AS NoOfPackages, " +
		    		        "IFNULL(b.Actual_No_Of_Packages, '0.00') AS ActualNoOfPackages, " +
		    		        "c.Importer_Name, " +
		    		        "(DATEDIFF(:endDate, a.De_stuff_Date) + 1) AS OTY, " +
		    		        "b.Yard_Location, " +
		    		        "b.Area_Occupied, " +
		    		        "c.Cargo_Movement, " +
		    		        "'' AS Consoler, " +
		    		        "p.party_Name AS PartyName, " +
		    		        "b.Comments AS Remarks, " +
		    		        "b.Warehouse_Location AS Location, " +
		    		        "c.Hold_Remarks, " +
		    		        "b.cargo_Type, " +
		    		        "IFNULL(c.Cargo_Value, '0.000') AS CargoValue, " +
		    		        "IFNULL(c.Cargo_Duty, '0.000') AS CargoDuty, " +
		    		        "c.IMO_Code, " +
		    		        "c.UN_No " +
		    		    "FROM " +
		    		        "cfigmcn a " +
		    		    "LEFT OUTER JOIN cfdestuffcn e ON " +
		    		        "a.Company_Id = e.Company_Id " +
		    		        "AND a.Branch_Id = e.Branch_Id " +
		    		        "AND a.IGM_Trans_Id = e.IGM_Trans_Id " +
		    		        "AND a.IGM_No = e.IGM_No " +
		    		        "AND a.De_Stuff_Id = e.De_Stuff_Id " +
		    		        "AND a.Profitcentre_Id = e.Profitcentre_Id " +
		    		        "AND a.Container_No = e.Container_No " +
		    		    "LEFT OUTER JOIN cfdestuffcrg b ON " +
		    		        "a.Company_Id = b.Company_Id " +
		    		        "AND a.Branch_Id = b.Branch_Id " +
		    		        "AND a.IGM_Trans_Id = b.IGM_Trans_Id " +
		    		        "AND a.IGM_No = b.IGM_No " +
		    		        "AND a.IGM_Line_No = b.IGM_Line_No " +
		    		        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
		    		    "LEFT OUTER JOIN cfigmcrg c ON " +
		    		        "a.Company_Id = c.Company_Id " +
		    		        "AND a.Branch_Id = c.Branch_Id " +
		    		        "AND a.IGM_Trans_Id = c.IGM_Trans_Id " +
		    		        "AND a.IGM_No = c.IGM_No " +
		    		        "AND a.IGM_Line_No = c.IGM_Line_No " +
		    		        "AND a.Profitcentre_Id = c.Profitcentre_Id " +
		    		    "LEFT OUTER JOIN cfigm f ON " +
		    		        "f.Company_Id = c.Company_Id " +
		    		        "AND f.Branch_Id = c.Branch_Id " +
		    		        "AND f.IGM_Trans_Id = c.IGM_Trans_Id " +
		    		        "AND f.IGM_No = c.IGM_No " +
		    		        "AND f.Profitcentre_Id = c.Profitcentre_Id " +
		    		    "LEFT OUTER JOIN party p ON " +
		    		        "f.Company_Id = p.Company_Id " +
		    		        "AND f.Shipping_Agent = p.Party_Id " +
		    		    "WHERE " +
		    		        "a.Company_Id = :companyId " +
		    		        "AND a.Branch_Id = :branchId " +
		    		        "AND a.Profitcentre_Id ='N00002'" +
		    		        "AND a.status = 'A' " +
		    		        "AND a.Container_Status = 'LCL' " +
		    		        "AND a.De_Stuff_Id != '' " +
		    		        "AND ((b.Actual_No_Of_Packages - b.Qty_Taken_Out) > 0) " +
		    		        "AND b.De_Stuff_Date <= :endDate " 
		    		        + "AND (:igmNo IS NULL OR :igmNo = '' OR a.IGM_No = :igmNo) "
						     + "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR a.IGM_Line_No = :igmLineNo) "
						     + "AND (:sl IS NULL OR :sl = '' OR f.Shipping_line = :sl) "
						     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR c.Account_holder_id = :accountHolderId) "
						     + "AND (:cha IS NULL OR :cha = '' OR a.cha = :cha) "
		    		       + "AND b.status = 'A' " +
		    		    "ORDER BY OTY DESC", 
		    		    nativeQuery = true)
		    		List<Object[]> findContainerDetails(
		    		    @Param("companyId") String companyId,
		    		    @Param("branchId") String branchId,
		    		    @Param("endDate") Date endDate,
		    		    @Param("igmNo") String igmNo,
					    @Param("igmLineNo") String igmLineNo,
					    @Param("sl") String sl,
					    @Param("accountHolderId") String accountHolderId,
					    @Param("cha") String cha
		    		);
		    		
		    		
		    		
		    		
		    		
		    		
		    		
		    		
		    		
		    		@Query(value = "SELECT DISTINCT z.Container_No AS containerNo, " +
		                    "z.Container_Size AS containerSize, " +
		                    "z.Container_Type AS containerType, " +
		                    "z.ISO_Code AS isoCode, " +
		                    "a.Gate_In_Id AS gateInId, " +
		                    "DATE_FORMAT(z.In_Gate_In_Date,'%d/%m/%y %H:%i') AS gateInDate, " +
		                    "a.Container_Seal_No AS containerSealNo, " +
		                    "z.Customs_Seal_No AS customsSealNo, " +
		                    "z.Vehicle_No AS vehicleNo, " +
		                    "z.Port_Exit_No AS portExitNo, " +
		                    "v.Vessel_Name AS vesselName, " +
		                    "m.Party_Name AS shippingLine, " +
		                    "p.Party_Name AS shippingAgent, " +
		                    "pt.Port_Name AS portName ," +
		                    "pt.Port_Name AS scanLoc, " +
		                    "z.Scanning_Done_Status AS scanningDoneStatus " +
		                    "FROM cfgatein z " +
		                    "LEFT OUTER JOIN cfigmcn a ON a.Company_Id = z.Company_Id " +
		                    "AND a.Branch_Id = z.Branch_Id " +
		                    "AND a.Profitcentre_Id = z.Profitcentre_Id " +
		                    "AND a.Gate_In_Id = z.Gate_In_Id " +
		                    "AND a.Container_No = z.Container_No " +
		                    "AND z.ERP_Doc_Ref_No = a.IGM_Trans_Id " +
		                    "LEFT OUTER JOIN cfigm b ON z.Company_Id = b.Company_Id " +
		                    "AND z.Branch_Id = b.Branch_Id " +
		                    "AND z.Profitcentre_Id = b.Profitcentre_Id " +
		                    "AND z.ERP_Doc_Ref_No = b.IGM_Trans_Id " +
		                    "AND z.doc_ref_No = b.Igm_no " +
		                    "LEFT OUTER JOIN party m ON b.Company_Id = m.Company_Id " +
		                    "AND z.SL = m.Party_Id " +
		                    "LEFT OUTER JOIN party p ON a.Company_Id = p.Company_Id " +
		                    "AND z.SA = p.Party_Id " +
		                    "LEFT OUTER JOIN vessel v ON b.Company_Id = v.Company_Id " +
		                    "AND b.Vessel_Id = v.Vessel_Id " +
		                    "LEFT OUTER JOIN port pt ON b.Company_Id = pt.Company_Id " +
		                    "AND pt.Port_Code = b.Port " +
		                    "WHERE a.company_id = :companyId " +
		                    "AND a.branch_id = :branchId " +
		                    "AND a.Gate_In_Date BETWEEN :startDate AND :endDate " 
		                    + "AND (:igmNo IS NULL OR :igmNo = '' OR z.Doc_Ref_No = :igmNo) "
						     + "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR z.Erp_Doc_ref_no = :igmLineNo) "
						     + "AND (:sl IS NULL OR :sl = '' OR z.sl = :sl) "
						     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR z.cha = :accountHolderId) "
						     + "AND (:cha IS NULL OR :cha = '' OR z.cha = :cha) "
		                    + "ORDER BY z.In_Gate_In_Date", nativeQuery = true)
		     List<Object[]> importManualGateInReportQuery(
		             @Param("companyId") String companyId,
		             @Param("branchId") String branchId,
		             @Param("startDate") Date startDate,
		             @Param("endDate") Date endDate,
		             @Param("igmNo") String igmNo,
					    @Param("igmLineNo") String igmLineNo,
					    @Param("sl") String sl,
					    @Param("accountHolderId") String accountHolderId,
					    @Param("cha") String cha);
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     
		     @Query(value = "SELECT DISTINCT b.Container_No AS containerNo, " +
		    	        "b.Container_Size AS containerSize, " +
		    	        "b.Container_Type AS containerType, " +
		    	        "b.ISO AS isoCode, " +
		    	        "c.party_name AS shippingLine, " +
		    	        "d.party_name AS shippingAgent, " +
		    	        "DATE_FORMAT(a.Gate_Pass_Date, '%d-%m-%Y %T') AS gatePassDate, " +


//    "a.Gate_Pass_Date AS gatePassDate, " +
		    	        
		    	        "DATEDIFF(CURDATE(), a.Gate_Pass_Date + 1) AS days " +
		    	        "FROM cfimportgatepass a " +
		    	        "LEFT OUTER JOIN cfigmcn b ON b.Company_Id = a.Company_Id " +
		    	        "AND b.Branch_Id = a.Branch_Id " +
		    	        "AND b.Container_No = a.Container_No " +
		    	        "AND b.IGM_No = a.IGM_No " +
		    	        "AND b.IGM_Trans_Id = a.IGM_Trans_Id " +
		    	        "AND b.Profitcentre_Id = a.Profitcentre_Id " +
		    	        "LEFT OUTER JOIN party c ON a.Company_Id = c.Company_Id " +
		    	        "AND a.cha = c.customer_code " +
		    	        "LEFT OUTER JOIN party d ON a.Company_Id = d.Company_Id " +
		    	        "AND a.SL = d.Party_Id " +
		    	        "WHERE a.company_id = :companyId " +
		    	        "AND a.branch_id = :branchId " +
		    	        "AND a.status = 'A' " +
		    	        "AND a.Gate_Pass_Date BETWEEN :startDate AND :endDate " +
		    	        "AND a.SPLGate_Out_Flag = 'N' " +
		    	        "AND a.Gate_Out_Id != '' " +
//		    	        "AND b.Special_Delivery = 'SCON' " +
		    	        "GROUP BY a.Container_No", 
		    	        nativeQuery = true)
		    	List<Object[]> getLoadedToDistuffEmptyContainerDetails(
		    	        @Param("companyId") String companyId,
		    	        @Param("branchId") String branchId,
		    	        @Param("startDate") Date startDate,
		    	        @Param("endDate") Date endDate);


		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	
		    	@Query(value = "SELECT DISTINCT " +
		    	        "a.Container_Status AS containerStatus, " +
		    	        "a.Container_No AS containerNo, " +
		    	        "a.Container_Size AS containerSize, " +
		    	        "DATE_FORMAT(a.In_Gate_In_Date, '%d %b %Y %T') AS gateInDate, " +
		    	        "DATE_FORMAT(b.Gate_Out_Date,'%d %b %Y ') AS gateOutDate, " +
		    	        "DATE_FORMAT(m.IGM_Date, '%d %b %Y ') AS igmDate ," +
		    	        "b.Igm_no AS igmNo, " +
		    	        "b.IGM_Line_No AS igmLineNo, " +
		    	        "b.Cargo_Wt AS cargoWeight, " +
		    	        "a.Weighment_weight AS weighmentWeight, " +
		    	        "IFNULL((b.Cargo_Wt - a.Weighment_weight), '0.00') AS diffInWeight, " +
		    	        "g.Importer_Name AS importerName, " +
		    	        "g.Commodity_Description AS commodityDescription, " +
		    	        "a.Container_Type AS containerType, " +
		    	        "b.Gate_Pass_No AS gatePassNo, " +
		    	        "a.EIR_Gross_Weight AS eirGrossWeight, " +
		    	        "b.Cargo_Wt AS cargoWeight, " +
		    	        "a.Tare_Weight AS tareWeight, " +
		    	        "DATE_FORMAT(b.Weighment_Date, '%d %b %Y %T') AS weighmentDate, " +
		    	        "a.Container_Seal_No AS containerSealNo, " +
		    	        "a.Weighment_Pass_No AS weighmentPassNo, " +
		    	        "DATE_FORMAT(b.Extra_Transport_Date, '%d %b %Y %T') AS extraTransportDate " +
		    	        "FROM cfgatein a " +
		    	        "LEFT OUTER JOIN cfigmcn b ON a.Company_Id = b.Company_Id " +
		    	        "AND a.Branch_Id = b.Branch_Id " +
		    	        "AND a.Profitcentre_Id = b.Profitcentre_Id " +
		    	        "AND a.Container_No = b.Container_No " +
		    	        "AND a.doc_ref_No = b.Igm_no " +
		    	        "AND a.ERP_Doc_Ref_No = b.IGM_Trans_Id " +
		    	        "LEFT OUTER JOIN cfigmcrg g ON b.Company_Id = g.Company_Id " +
		    	        "AND b.Branch_Id = g.Branch_Id " +
		    	        "AND b.Profitcentre_Id = g.Profitcentre_Id " +
		    	        "AND b.IGM_Trans_Id = g.IGM_Trans_Id " +
		    	        "AND b.Igm_no = g.Igm_no " +
		    	        "AND b.IGM_Line_No = g.IGM_Line_No " +
		    	        "LEFT OUTER JOIN cfigm m ON m.Company_Id = g.Company_Id " +
		    	        "AND m.Branch_Id = g.Branch_Id " +
		    	        "AND m.Profitcentre_Id = g.Profitcentre_Id " +
		    	        "AND m.IGM_Trans_Id = g.IGM_Trans_Id " +
		    	        "AND m.Igm_no = g.Igm_no " +
		    	        "WHERE a.Company_Id = :companyId " +
		    	        "AND a.Branch_Id = :branchId " +
		    	        "AND a.Status = 'A' " +
		    	        "AND a.Profitcentre_Id = 'N00002' " +
		    	        "AND a.Gate_In_Type = 'IMP' " +
		    	        "AND a.Weighment_Done = 'Y' " +
		    	        "AND a.Weighment_Pass_No != '' " +
		    	        "AND g.Primary_Item = 'Y' " +
		    	        "AND b.Weighment_Date BETWEEN :startDate AND :endDate " +
		    	        "AND (:igmNo IS NULL OR :igmNo = '' OR a.Doc_Ref_No = :igmNo) " +
		    	        "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR a.Erp_Doc_ref_no = :igmLineNo) " +
		    	        "AND (:sl IS NULL OR :sl = '' OR a.sl = :sl) " +
		    	        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR g.Account_holder_id = :accountHolderId) " +
		    	        "AND (:cha IS NULL OR :cha = '' OR a.cha = :cha)", 
		    	        nativeQuery = true)
		    	List<Object[]> getWeighmentReport(
		    	        @Param("companyId") String companyId,
		    	        @Param("branchId") String branchId,
		    	        @Param("startDate") Date startDate,
		    	        @Param("endDate") Date endDate,
		    	        @Param("igmNo") String igmNo,
		    	        @Param("igmLineNo") String igmLineNo,
		    	        @Param("sl") String sl,
		    	        @Param("accountHolderId") String accountHolderId,
		    	        @Param("cha") String cha);
		    	
		    	
		    	
		    	
		    	
		    	
		    	@Query(value = 
		    	        "SELECT DISTINCT tp.party_name, " +
		    	" a.transporter_name , "+
		    	        "       COUNT(CASE WHEN a.container_size IN ('20', '22') THEN 1 END) AS count_20, " +
		    	        "       COUNT(CASE WHEN a.container_size IN ('40', '45') THEN 1 END) AS count_40, " +
		    	        "       COUNT(a.container_no) AS containerCount, " +
		    	        "       a.container_size, " +
		    	        "       a.gate_in_id " +
		    	        "FROM cfgatein a " +
		    	        "LEFT OUTER JOIN cfigm b " +
		    	        "    ON a.Company_Id = b.Company_Id " +
		    	        "    AND a.Branch_Id = b.Branch_Id " +
		    	        "    AND b.igm_Trans_Id = a.ERP_DOC_Ref_No " +
		    	        "    AND b.igm_no = a.doc_ref_no " +
		    	        "    AND a.profitcentre_id = b.profitcentre_id " +
		    	        "LEFT OUTER JOIN party tp " +
		    	        "    ON tp.company_id = a.company_id " +
		    	        "    AND a.Transporter = tp.party_id " +
		    	        "WHERE a.company_id = :companyId " +
		    	        "  AND a.branch_id = :branchId " +
		    	        "  AND a.status = 'A' " +
		    	        "  AND b.status = 'A' " +
		    	        "  AND a.Container_No <> '' " +
		    	        "AND a.Gate_In_Type = 'IMP' " +
		    	        "  AND b.profitcentre_id ='N00002' " +
		    	        "  AND a.In_gate_in_date BETWEEN :startDate AND :endDate " +
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
		    	            "SELECT a.IGM_No, " +
		    	            "       a.IGM_Line_No, " +
		    	            "       m.Container_No, " +
		    	            "       m.Container_Size, " +
		    	            "       DATE_FORMAT(m.De_Stuff_Date, '%d-%b-%Y'), " +
		    	            "       d.bl_no, " +
		    	            "       a.Importer_Name, " +
		    	            "       a.LCL_Zero_Entry_Flag, " +
		    	            "       e.party_name," +
		    	            "       DATE_FORMAT(m.Gate_Out_Date, '%d-%b-%Y'), " +
		    	            "       b.Party_Name " +
		    	            "FROM cfdestuffcrg a " +
		    	            "LEFT OUTER JOIN party b " +
		    	            "   ON b.Company_Id = a.Company_Id " +
		    	            "   AND b.Party_Id = a.on_account_of " +
		    	            "LEFT OUTER JOIN cfigmcn m " +
		    	            "   ON m.Company_Id = a.Company_Id " +
		    	            "   AND m.branch_id = a.branch_id " +
		    	            "   AND a.IGM_Trans_Id = m.IGM_Trans_Id " +
		    	            "   AND a.IGM_No = m.Igm_no " +
		    	            "   AND a.IGM_Line_No = m.IGM_Line_No " +
		    	            "   AND m.de_stuff_id = a.de_stuff_id " +
		    	            "LEFT OUTER JOIN cfigmcrg d " +
		    	            "   ON d.Company_Id = a.Company_Id " +
		    	            "   AND d.branch_id = a.branch_id " +
		    	            "   AND d.IGM_Trans_Id = a.IGM_Trans_Id " +
		    	            "   AND d.IGM_No = a.Igm_no " +
		    	            "   AND d.IGM_Line_No = a.IGM_Line_No " +
		    	            "LEFT OUTER JOIN cfigm f " +
		    	            "   ON d.Company_Id = f.Company_Id " +
		    	            "   AND d.branch_id = f.branch_id " +
		    	            "   AND d.IGM_Trans_Id = f.IGM_Trans_Id " +
		    	            "   AND d.IGM_No = f.Igm_no " +
		    	            "   AND d.profitcentre_id = f.profitcentre_id " +
		    	            "LEFT OUTER JOIN party e " +
		    	            "   ON e.Company_Id = f.Company_Id " +
		    	            "   AND e.Party_Id = f.Shipping_Agent " +
		    	            "WHERE a.company_id = :companyId " +
		    	            "  AND a.branch_id = :branchId " +
		    	            "  AND a.Status = 'A' " +
		    	            "  AND a.LCL_ZERO_ENTRY_FLAG = 'N' " +
		    	            "  AND a.LCL_ZERO_ENTRY_DATE BETWEEN :startDate AND :endDate " +
		    	            "AND (:igmNo IS NULL OR :igmNo = '' OR a.IGM_No = :igmNo) " +
		    		    	        "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR a.IGM_Line_No = :igmLineNo) " +
		    		    	        "AND (:sl IS NULL OR :sl = '' OR f.Shipping_line = :sl) " +
		    		    	        "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR d.Account_holder_id = :accountHolderId) " +
		    		    	        "AND (:cha IS NULL OR :cha = '' OR m.cha = :cha)", 
		    	            nativeQuery = true)
		    	    List<Object[]> toGetLCLZeroPaymetReport(
		    	        @Param("companyId") String companyId,
		    	        @Param("branchId") String branchId,
		    	        @Param("startDate") Date startDate,
		    	        @Param("endDate") Date endDate,
		    	        @Param("igmNo") String igmNo,
		    	        @Param("igmLineNo") String igmLineNo,
		    	        @Param("sl") String sl,
		    	        @Param("accountHolderId") String accountHolderId,
		    	        @Param("cha") String cha);



}
