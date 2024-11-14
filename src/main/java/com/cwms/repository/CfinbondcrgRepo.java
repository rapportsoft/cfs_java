package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.Cfinbondcrg;

import jakarta.persistence.Column;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;

public interface CfinbondcrgRepo extends JpaRepository<Cfinbondcrg, String> {

	
	
	@Query(value="select COUNT(c)>0 from Cfinbondcrg c where c.companyId=:cid and c.branchId=:bid and c.nocTransId=:nocTransId and c.nocNo =:nocNo and c.status !='D'")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("nocTransId") String nocTransId,@Param ("nocNo") String nocNo);
	
	
	
	@Query(value="select COUNT(c)>0 from Cfinbondcrg c where c.companyId=:cid and c.branchId=:bid "
			+ "and c.nocTransId!=:nocTransId and c.nocNo =:nocNo and inBondingId=:inBondingId and  c.status !='D'")
	Boolean isDataExist1(@Param("cid") String cid,@Param("bid") String bid,@Param("nocTransId") String nocTransId,@Param ("nocNo") String nocNo,@Param ("inBondingId") String inBondingId);
	
	
	
//	 @Query("SELECT c FROM Cfinbondcrg c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo AND c.status !='D'")
//	 Cfinbondcrg findCfBondCrgData(
//	        @Param("companyId") String companyId,
//	        @Param("branchId") String branchId,
//	        @Param("nocTransId") String nocTransId,
//	        @Param("inBondingId") String inBondingId,
//	        @Param("nocNo") String nocNo
//	    );
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.profitcentreId, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.nocValidityDate, c.nocFromDate, c.shift, c.gateInId, c.boeNo, c.boeDate, c.accSrNo, c.bondingNo, " +
		       "c.bondingDate, c.bondValidityDate, c.invoiceUptoDate, c.chaSrNo, c.cha, p.customerCode, c.importerId, c.importerName, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.numberOfMarks, p.partyName, c.grossWeight, " +
		       "c.uom, c.containerNo, c.nocPackages, c.sampleQty, c.areaAllocated, c.areaOccupied, c.cargoCondition, c.gateInPackages, " +
		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.examinationId, c.comments, " +
		       "c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, c.inBond40Ft, " +
		       "c.exBond20Ft, c.exBond40Ft, c.otlNo, c.bondYard, c.status, c.createdBy, c.createdDate, c.editedBy, c.editedDate, " +
		       "c.approvedBy, c.approvedDate, c.shortagePackages, c.damagedQty, c.breakage,c.extenstionDate1,c.extenstionDate2,c.extenstionDate3) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo AND c.status !='D' " +
		       "ORDER BY c.inBondingHdrId DESC")
	 Cfinbondcrg findCfBondCrgData(      @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("nocTransId") String nocTransId,
		        @Param("inBondingId") String inBondingId,
		        @Param("nocNo") String nocNo);
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.profitcentreId, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.nocValidityDate, c.nocFromDate, c.shift, c.gateInId, c.boeNo, c.boeDate, c.accSrNo, c.bondingNo, " +
		       "c.bondingDate, c.bondValidityDate, c.invoiceUptoDate, c.chaSrNo, c.cha, p.customerCode, c.importerId, c.importerName, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.numberOfMarks, c.commodityDescription, c.grossWeight, " +
		       "c.uom, c.containerNo, c.nocPackages, c.sampleQty, c.areaAllocated, c.areaOccupied, c.cargoCondition, c.gateInPackages, " +
		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.examinationId, c.comments, " +
		       "c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, c.inBond40Ft, " +
		       "c.exBond20Ft, c.exBond40Ft, c.otlNo, c.bondYard, c.status, c.createdBy, c.createdDate, c.editedBy, c.editedDate, " +
		       "c.approvedBy, c.approvedDate, c.shortagePackages, c.damagedQty, c.breakage,c.extenstionDate1,c.extenstionDate2,c.extenstionDate3) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.inBondingHdrId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.inBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.inBondingHdrId DESC")
		List<Cfinbondcrg> findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(@Param("companyId") String companyId, 
		                                                                       @Param("branchId") String branchId,
		                                                                       @Param("partyName") String partyName);
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Modifying
	 @Transactional
	 @Query("UPDATE Cfinbondcrg c SET c.exBondedPackages = :exBondedPackages, c.exBondedCargoDuty = :exBondedCargoDuty, c.exBondedCif = :exBondedCif, c.exBondedInsurance = :exBondedInsurance, c.exBondedGw = :exBondedGw,c.exBond20Ft=:exBond20Ft,c.exBond40Ft=:exBond40Ft " +
	        "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo AND c.nocTransId = :nocTransId AND c.boeNo = :boeNo")
	 int updateCfinbondCrgAfterExbond(
	         @Param("exBondedPackages") BigDecimal exBondedPackages,
	         @Param("exBondedCargoDuty") BigDecimal exBondedCargoDuty,
	         @Param("exBondedCif") BigDecimal exBondedCif,
	         @Param("exBondedInsurance") BigDecimal exBondedInsurance,
	         @Param("exBondedGw") BigDecimal exBondedGw,
	         @Param("exBond20Ft") String exBond20Ft,
	         @Param("exBond40Ft") String exBond40Ft,
	         @Param("companyId") String companyId,
	         @Param("branchId") String branchId,
	         @Param("inBondingId") String inBondingId,
	         @Param("nocNo") String nocNo,
	         @Param("nocTransId") String nocTransId,
	         @Param("boeNo") String boeNo
	 );
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.inBondingHdrId) " +
		       "FROM Cfinbondcrg c " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId " +
		       "AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo " +
		       "ORDER BY c.inBondingId DESC")
	 Cfinbondcrg findCfinbondHdr(
  @Param("companyId") String companyId,
  @Param("branchId") String branchId,
  @Param("nocTransId") String nocTransId,
  @Param("inBondingId") String inBondingId,
  @Param("nocNo") String nocNo
);
	
	 
	 
	 @Query("SELECT c " +
		       "FROM Cfinbondcrg c " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId " +
		       "AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo " +
		       "ORDER BY c.inBondingId DESC")
		Cfinbondcrg findCfinbondCrg(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("nocTransId") String nocTransId,
		    @Param("inBondingId") String inBondingId,
		    @Param("nocNo") String nocNo
		);


	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE Cfinbondcrg c SET c.status =:status WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo AND c.nocTransId = :nocTransId")
	 int updateAfterApprove(
			 @Param("status") String status,
			 @Param("companyId") String companyId,
	         @Param("branchId") String branchId,
	         @Param("inBondingId") String inBondingId,
	         @Param("nocNo") String nocNo,
	         @Param("nocTransId") String nocTransId);
	 
	 
	 
	 
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.profitcentreId, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.nocValidityDate, c.nocFromDate, c.boeNo, c.boeDate, c.bondingNo, c.bondingDate, c.bondValidityDate, " +
		       "p.partyName, c.chaCode, p.customerCode, c.importerId, pa.partyName, c.importerAddress1, c.importerAddress2, c.importerAddress3, " +
		       "cf.commodityDescription, c.grossWeight, c.uom, c.nocPackages, c.areaAllocated, cf.areaOccupied, c.cargoCondition, cf.nocPackages, " +
		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.cifValue, c.cargoDuty, " +
		       "c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, c.inBond40Ft, c.otlNo, c.bondYard, c.status, " +
		       "c.shortagePackages, c.damagedQty, c.breakage, cf.cfBondDtlId, cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, " +
		       "cf.inbondCifValue, cf.inbondCargoDuty, cf.inbondGrossWt, noc.nocWeek,noc.spaceType,cf.grossWeight) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.importerId = pa.partyId " +
		       "LEFT OUTER JOIN CfinbondcrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.inBondingId = cf.inBondingId and c.nocTransId =cf.nocTransId and c.nocNo =cf.nocNo " +
		       "LEFT OUTER JOIN Cfbondnoc noc ON c.companyId = noc.companyId AND c.branchId = noc.branchId AND  c.nocTransId =noc.nocTransId and c.nocNo =noc.nocNo " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.status != 'D' ")
		List<Cfinbondcrg> getDataForCustomesBondInBondPrint(@Param("companyId") String companyId, 
		                                                                       @Param("branchId") String branchId, 
		                                                                       @Param("inBondingId") String inBondingId);

	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.profitcentreId, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, c.bondingNo, c.bondingDate, p.partyName, c.importerId, c.importerName, " +
		       "cf.commodityDescription, c.grossWeight, c.uom, c.nocPackages, " +
		       "c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, c.exBondedPackages, " +
		       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, cf.cfBondDtlId, " +
		       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
		       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, " +
		       "cx.exBondedInsurance, cx.exBondedCIF, cx.exBondedCargoDuty, cx.exBondedGW, cx.exBondedPackages,cfx.areaReleased) " +
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
		       "AND (c.inBondingDate BETWEEN :startDate AND :endDate " +
		       "OR (:startDate IS NULL OR :startDate = '') AND c.inBondingDate <= :endDate) " +
		       "AND c.status = 'A' "+
			   "ORDER BY c.inBondingDate, c.boeNo " )
		List<Cfinbondcrg> getDataForInBondInventoryReport(@Param("companyId") String companyId, 
		                                                 @Param("branchId") String branchId, 
		                                                 @Param("startDate") Date startDate,
		                                                 @Param("endDate") Date endDate);
	 
	 
	
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, c.bondingNo, c.bondingDate, p.partyName, c.importerId, c.importerName, " +
		       "cf.commodityDescription, c.grossWeight, c.nocPackages, " +
		       "c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, c.exBondedPackages, " +
		       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, cf.cfBondDtlId, " +
		       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
		       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, c.numberOfMarks,cf.shortagePackages,cf.damagedQty,cf.breakage) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "LEFT OUTER JOIN CfinbondcrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.inBondingId = cf.inBondingId " +
		       "AND c.nocTransId = cf.nocTransId AND cf.status = 'A' " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND (c.boeNo = :boeNo OR :boeNo IS NULL OR :boeNo = '') " + // Allow null or empty boeNo
		       "AND c.inBondingDate BETWEEN :startDate AND :endDate " + // Keep the date range check
		       "AND c.status = 'A' " +
		       "ORDER BY c.inBondingDate, c.boeNo")
		List<Cfinbondcrg> getDataForInBondDepositeReport(@Param("companyId") String companyId, 
		                                                 @Param("branchId") String branchId, 
		                                                 @Param("startDate") Date startDate,
		                                                 @Param("endDate") Date endDate,
		                                                 @Param("boeNo") String boeNo);
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, c.bondingNo, c.bondingDate, p.partyName, c.importerId, c.importerName, " +
		       "cf.commodityDescription, c.grossWeight, c.nocPackages, " +
		       "c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, c.exBondedPackages, " +
		       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, cf.cfBondDtlId, " +
		       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
		       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, c.numberOfMarks,cf.shortagePackages,cf.damagedQty,cf.breakage) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "LEFT OUTER JOIN CfinbondcrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.inBondingId = cf.inBondingId " +
		       "AND c.nocTransId = cf.nocTransId AND cf.status = 'A' " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND (c.boeNo = :boeNo OR :boeNo IS NULL OR :boeNo = '') " + // Allow null or empty boeNo
		       "AND c.status = 'A' " +
		       "ORDER BY c.inBondingDate, c.boeNo")
		List<Cfinbondcrg> getDataForInBondDepositeReportWithoutDates(@Param("companyId") String companyId, 
		                                                 @Param("branchId") String branchId,
		                                                 @Param("boeNo") String boeNo);


	
	 
	 
	 
	 

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 //"AND COALESCE(GREATEST(c.bondValidityDate, c.extensionDate1, c.extensionDate2, c.extensionDate3), c.bondValidityDate) < CURRENT_DATE " + 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.boeNo, c.boeDate, c.bondingNo, c.bondingDate, p.partyName, c.importerId, c.importerName, " +
		       "cf.commodityDescription, c.grossWeight,c.nocPackages, " +
		       "c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, c.exBondedPackages, " +
		       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, cf.cfBondDtlId, " +
		       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
		       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, " +
		       "cx.exBondedInsurance, cx.exBondedCIF, cx.exBondedCargoDuty, cx.exBondedGW, cx.exBondedPackages,cfx.areaReleased,c.comments,c.bondValidityDate) " +
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
		       "AND COALESCE(GREATEST( " +
		       "  CASE WHEN c.bondValidityDate IS NOT NULL THEN c.bondValidityDate ELSE NULL END, " +
		       "  CASE WHEN c.extenstionDate1 IS NOT NULL THEN c.extenstionDate1 ELSE NULL END, " +
		       "  CASE WHEN c.extenstionDate2 IS NOT NULL THEN c.extenstionDate2 ELSE NULL END, " +
		       "  CASE WHEN c.extenstionDate3 IS NOT NULL THEN c.extenstionDate3 ELSE NULL END " +
		       "), c.bondValidityDate) < CURRENT_DATE " + // Bond validity expired condition
		       "AND (cf.inBondedPackages - COALESCE(cf.exBondedPackages, 0)) > 0 " +
		       "AND (c.inBondingDate BETWEEN :startDate AND :endDate " +
		       "OR (:startDate IS NULL OR :startDate = '') AND c.inBondingDate <= :endDate) " +
		       "AND c.status = 'A' "+
		       "AND c.status = 'A' "+
			   "ORDER BY c.inBondingDate, c.boeNo " )
		List<Cfinbondcrg> getDataForExpiredBondReport(@Param("companyId") String companyId, 
		                                                 @Param("branchId") String branchId, 
		                                                 @Param("startDate") Date startDate,
		                                                 @Param("endDate") Date endDate);

	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.boeNo, c.boeDate, c.bondingNo, c.bondingDate, p.partyName, c.importerId, c.importerName, " +
		       "cf.commodityDescription, c.grossWeight,c.nocPackages, " +
		       "c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, c.exBondedPackages, " +
		       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, " +
		       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
		       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, " +
		       "cx.exBondedInsurance, cx.exBondedCIF, cx.exBondedCargoDuty, cx.exBondedGW, cx.exBondedPackages,cfx.areaReleased,c.comments,c.bondValidityDate) " +
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
		       "AND COALESCE(GREATEST( " +
		       "  CASE WHEN c.bondValidityDate IS NOT NULL THEN c.bondValidityDate ELSE NULL END, " +
		       "  CASE WHEN c.extenstionDate1 IS NOT NULL THEN c.extenstionDate1 ELSE NULL END, " +
		       "  CASE WHEN c.extenstionDate2 IS NOT NULL THEN c.extenstionDate2 ELSE NULL END, " +
		       "  CASE WHEN c.extenstionDate3 IS NOT NULL THEN c.extenstionDate3 ELSE NULL END " +
		       "), c.bondValidityDate) < CURRENT_DATE " + // Bond validity expired condition
		       "AND (cf.inBondedPackages - COALESCE(cf.exBondedPackages, 0)) > 0 " +
		       "AND (c.inBondingDate BETWEEN :startDate AND :endDate " +
		       "OR (:startDate IS NULL OR :startDate = '') AND c.inBondingDate <= :endDate) " +
		       "AND c.status = 'A' "+
		       "AND c.section49 = 'Y' "+
			   "ORDER BY c.inBondingDate, c.boeNo " )
		List<Cfinbondcrg> getDataForSection49ExpiredBondReport(@Param("companyId") String companyId, 
		                                                 @Param("branchId") String branchId, 
		                                                 @Param("startDate") Date startDate,
		                                                 @Param("endDate") Date endDate);
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(" +
		       "c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, " +
		       "c.inBondingDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, " +
		       "c.nocDate, c.boeNo, c.boeDate, c.bondingNo, c.bondingDate, " +
		       "p.partyName, c.importerId, c.importerName, cf.commodityDescription, " +
		       "c.grossWeight, c.nocPackages, c.areaAllocated, c.areaOccupied, " +
		       "c.gateInPackages, c.inBondedPackages, c.exBondedPackages, c.cifValue, " +
		       "c.cargoDuty, c.insuranceValue, cf.cfBondDtlId, cf.typeOfPackage, " +
		       "cf.inBondedPackages, cf.inbondInsuranceValue, cf.inbondCifValue, " +
		       "cf.inbondCargoDuty, cf.inbondGrossWt, cf.grossWeight, " +
		       "cf.shortagePackages, cf.damagedQty, cf.breakage, c.bondValidityDate) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "LEFT OUTER JOIN CfinbondcrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.inBondingId = cf.inBondingId " +
		       "AND c.nocTransId = cf.nocTransId AND cf.status = 'A' " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.inBondingDate BETWEEN :startDate AND :endDate " +
		       "AND c.status = 'A' " +
		       "AND c.section49 = :section49 " +
		       "ORDER BY c.inBondingDate, c.boeNo")
		List<Cfinbondcrg> getDataForInBondInLiveInBondReport(@Param("companyId") String companyId, 
		                                                     @Param("branchId") String branchId, 
		                                                     @Param("startDate") Date startDate,
		                                                     @Param("endDate") Date endDate,
		                                                     @Param("section49") String section49);
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Modifying
	 @Transactional
	 @Query("UPDATE Cfinbondcrg c SET c.exBondedPackages = :exBondedPackages, c.exBondedCargoDuty = :exBondedCargoDuty, c.exBondedCif = :exBondedCif, c.exBondedInsurance = :exBondedInsurance, "
	 		+ "c.exBondedGw = :exBondedGw " +
	        "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo AND c.nocTransId = :nocTransId AND c.boeNo = :boeNo")
	 int updateCfInBondCrgAfterExBondAuditTrail(
	         @Param("exBondedPackages") BigDecimal exBondedPackages,
	         @Param("exBondedCargoDuty") BigDecimal exBondedCargoDuty,
	         @Param("exBondedCif") BigDecimal exBondedCif,
	         @Param("exBondedInsurance") BigDecimal exBondedInsurance,
	         @Param("exBondedGw") BigDecimal exBondedGw,
	         @Param("companyId") String companyId,
	         @Param("branchId") String branchId,
	         @Param("inBondingId") String inBondingId,
	         @Param("nocNo") String nocNo,
	         @Param("nocTransId") String nocTransId,
	         @Param("boeNo") String boeNo
	 );

	 
	 
	 
	 
	 
	 
	 
	 
		@Modifying
		@Transactional
		@Query("UPDATE Cfbondinsbal c SET c.exbondArea = :exbondArea, c.exbondCargoDuty = :exbondCargoDuty, c.exbondCifValue = :exbondCifValue WHERE c.companyId = :companyId AND c.branchId = :branchId")
		int updateCfbondinsbalAfterInbond(
		    @Param("exbondArea") BigDecimal exbondArea,
		    @Param("exbondCargoDuty") BigDecimal exbondCargoDuty,
		    @Param("exbondCifValue") BigDecimal exbondCifValue,
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId
		);
		
		
		@Modifying
		@Transactional
		@Query("UPDATE Cfbondinsbal c SET c.exbondCargoDuty = :exbondCargoDuty, c.exbondCifValue = :exbondCifValue WHERE c.companyId = :companyId AND c.branchId = :branchId")
		int updateCfbondinsbalAfterInbondAuditTrail(
		    @Param("exbondCargoDuty") BigDecimal exbondCargoDuty,
		    @Param("exbondCifValue") BigDecimal exbondCifValue,
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId
		);
}
