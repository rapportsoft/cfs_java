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
		            "DATE_FORMAT(a.Gate_In_Date, '%d/%b/%Y %H:%i'), a.Igm_no, a.IGM_Line_No, b.Shipping_line, " +
		            "c.Importer_Name, a.CHA, m.Party_Name, DATEDIFF(CURDATE(), a.Gate_In_Date + 1) as Days, " +
		            "p.party_Name, ac.CUSTOMER_CODE " +
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
		            + "AND (c.sealCutReqDate BETWEEN :startDate AND :endDate) "
		            + "AND (:igmNo IS NULL OR :igmNo = '' OR a.igmNo = :igmNo) "
				     + "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR a.igmLineNo = :igmLineNo) "
				     + "AND (:cha IS NULL OR :cha = '' OR a.cha = :cha) "
				     + "AND (:accountHolderId IS NULL OR :accountHolderId = '' OR cfcrg.accountHolderId = :accountHolderId) "
				     + "AND (:sl IS NULL OR :sl = '' OR c.Shipping_line = :sl) "
		            + "AND a.status = 'A'",
		            nativeQuery = true)
		    List<Object[]> findInventoryData( @Param("companyId") String companyId,
				    @Param("branchId") String branchId,
				    @Param("startDate") Date startDate,
				    @Param("endDate") Date endDate,
				    @Param("igmNo") String igmNo,
				    @Param("igmLineNo") String igmLineNo,
				    @Param("cha") String cha,
				    @Param("accountHolderId") String accountHolderId,
				    @Param("sl") String sl);
		

}
