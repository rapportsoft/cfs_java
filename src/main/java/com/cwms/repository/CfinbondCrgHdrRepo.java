package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgHDR;

import jakarta.transaction.Transactional;

public interface CfinbondCrgHdrRepo extends JpaRepository<CfinbondcrgHDR, String>{

	
	@Query(value="select COUNT(c)>0 from CfinbondcrgHDR c where c.companyId=:cid and c.branchId=:bid and c.nocTransId=:nocTransId and c.nocNo =:nocNo and c.status !='D'")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("nocTransId") String nocTransId,@Param ("nocNo") String nocNo);
	
	
	
//	@Query(value="select COUNT(c)>0 from CfinbondcrgHDR c where c.companyId=:cid and c.branchId=:bid "
//			+ "and c.nocTransId!=:nocTransId and c.nocNo =:nocNo and inBondingId=:inBondingId and  c.status !='D'")
//	Boolean isDataExist1(@Param("cid") String cid,@Param("bid") String bid,@Param("nocTransId") String nocTransId,@Param ("nocNo") String nocNo,@Param ("inBondingId") String inBondingId);
//	
	
	@Modifying
	@Transactional
	@Query("update CfinbondcrgHDR c set c.inBondedPackages = :inBondedPackages, " +
	       "c.inbondInsuranceValue = :inbondInsuranceValue, " +
	       "c.inbondGrossWt = :inbondGrossWt, " +
	       "c.cifValue = :cifValue, " +
	       "c.cargoDuty = :cargoDuty, " +
	       "c.shortagePackages = :shortagePackages, " +
	       "c.damagedQty = :damagedQty, " +
	       "c.breakage = :breakage, " +
	       "c.areaOccupied = :areaOccupied " +
	       "where c.companyId = :companyId and " +
	       "c.branchId = :branchId and " +
	       "c.nocTransId = :nocTransId and " +
	       "c.nocNo = :nocNo")
	int updateCfInbondHeader(
	    @Param("inBondedPackages") BigDecimal inBondedPackages,
	    @Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
	    @Param("inbondGrossWt") BigDecimal inbondGrossWt,
	    @Param("cifValue") BigDecimal cifValue,
	    @Param("cargoDuty") BigDecimal cargoDuty,
	    @Param("shortagePackages") BigDecimal shortagePackages,
	    @Param("damagedQty") BigDecimal damagedQty,
	    @Param("breakage") BigDecimal breakage,
	    @Param("areaOccupied") BigDecimal areaOccupied,
	    @Param("companyId") String companyId, // Corrected "compnayId" to "companyId"
	    @Param("branchId") String branchId,
	    @Param("nocTransId") String nocTransId,
	    @Param("nocNo") String nocNo
	);
	
	
	
	
	
	
	
	
	 @Query("SELECT c FROM CfinbondcrgHDR c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
	 CfinbondcrgHDR findCfInBondCrgHdrForUpdationg(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo
	    );
	 
	 
	 
	 @Query("SELECT c FROM CfinbondcrgHDR c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.inBondingHdrId = :inBondingHdrId AND c.nocNo = :nocNo")
	 CfinbondcrgHDR findCfBondCrgHDRData(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("inBondingHdrId") String inBondingHdrId,
	        @Param("nocNo") String nocNo
	    );

	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingId, c.inBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocDate, " +
		       "c.nocValidityDate, c.nocFromDate, c.shift, c.gateInId, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, " +
		       "c.bondingNo, c.bondingDate, c.bondValidityDate, c.chaSrNo, c.cha, c.chaCode, c.importerId, c.importerName, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.numberOfMarks, c.commodityDescription, " +
		       "c.grossWeight, c.uom, c.nocPackages, c.areaAllocated, c.areaOccupied, c.cargoCondition, c.gateInPackages, " +
		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.comments, " +
		       "c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, " +
		       "c.inBond40Ft, c.exBond20Ft, c.exBond40Ft, c.otlNo, c.bondYard, c.status, c.shortagePackages, " +
		       "c.damagedQty, c.breakage,c.exBondedCargoDuty,c.exBondedInsurance,c.exBondedCif,c.exBondedGw) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND (c.inBondedPackages - COALESCE(c.exBondedPackages, 0)) > 0 " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.inBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.inBondingId DESC")
		List<Cfinbondcrg> findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(@Param("companyId") String companyId, 
		                                                                           @Param("branchId") String branchId,
		                                                                           @Param("partyName") String partyName);

	 
	 
	
	 
	 
	 
	 
//	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingId, c.inBondingDate, " +
//		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocDate, " +
//		       "c.nocValidityDate, c.nocFromDate, c.shift, c.gateInId, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, " +
//		       "c.bondingNo, c.bondingDate, c.bondValidityDate, c.chaSrNo, c.cha, c.chaCode, c.importerId, c.importerName, " +
//		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.numberOfMarks, c.commodityDescription, " +
//		       "c.grossWeight, c.uom, c.nocPackages, c.areaAllocated, c.areaOccupied, c.cargoCondition, c.gateInPackages, " +
//		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.comments, " +
//		       "c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, " +
//		       "c.inBond40Ft, c.exBond20Ft, c.exBond40Ft, c.otlNo, c.bondYard, c.status, c.shortagePackages, " +
//		       "c.damagedQty, c.breakage,c.exBondedCargoDuty,c.exBondedInsurance,c.exBondedCif,c.exBondedGw) " +
//		       "FROM Cfinbondcrg c " +
//		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
//		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId " +
//		       "AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo " +
//		       "ORDER BY c.inBondingId DESC")
//	 Cfinbondcrg findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(
//  @Param("companyId") String companyId,
//  @Param("branchId") String branchId,
//  @Param("nocTransId") String nocTransId,
//  @Param("inBondingId") String inBondingId,
//  @Param("nocNo") String nocNo
//);
	 
	 
	
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingId, c.inBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocDate, " +
		       "c.nocValidityDate, c.nocFromDate, c.shift, c.gateInId, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, " +
		       "c.bondingNo, c.bondingDate, c.bondValidityDate, c.chaSrNo, c.cha, c.chaCode, c.importerId, c.importerName, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.numberOfMarks, c.commodityDescription, " +
		       "c.grossWeight, c.uom, c.nocPackages, c.areaAllocated, c.areaOccupied, c.cargoCondition, c.gateInPackages, " +
		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.comments, " +
		       "c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, " +
		       "c.inBond40Ft, c.exBond20Ft, c.exBond40Ft, c.otlNo, c.bondYard, c.status, c.shortagePackages, " +
		       "c.damagedQty, c.breakage, c.exBondedCargoDuty, c.exBondedInsurance, c.exBondedCif, c.exBondedGw) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId " +
		       "AND (c.inBondedPackages - COALESCE(c.exBondedPackages, 0)) > 0 " +
		       "AND c.status != 'D' " +
//"AND c.status = 'A' " +
		       "AND (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%')) " +
		       "ORDER BY c.boeNo DESC")
		List<Cfinbondcrg> findCfinbondcrgByCompanyIdAndBranchIdForInbond(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("partyName") String partyName
		);

	 
	 
	 @Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingId, c.inBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocDate, " +
		       "c.nocValidityDate, c.nocFromDate, c.shift, c.gateInId, c.boeNo, c.boeDate, c.accSrNo, c.onAccountOf, " +
		       "c.bondingNo, c.bondingDate, c.bondValidityDate, c.chaSrNo, c.cha, c.chaCode, c.importerId, c.importerName, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.numberOfMarks, c.commodityDescription, " +
		       "c.grossWeight, c.uom, c.nocPackages, c.areaAllocated, c.areaOccupied, c.cargoCondition, c.gateInPackages, " +
		       "c.inBondedPackages, c.exBondedPackages, c.toBondedPackages, c.spaceAllocated, c.section49, c.comments, " +
		       "c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue, c.inBond20Ft, " +
		       "c.inBond40Ft, c.exBond20Ft, c.exBond40Ft, c.otlNo, c.bondYard, c.status, c.shortagePackages, " +
		       "c.damagedQty, c.breakage, c.exBondedCargoDuty, c.exBondedInsurance, c.exBondedCif, c.exBondedGw) " +
		       "FROM Cfinbondcrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId"   +
		       " AND c.branchId = :branchId " + 
		       "AND (c.inBondedPackages - COALESCE(c.exBondedPackages, 0)) > 0 " +
		       "AND c.status != 'D' " +
		       " AND c.nocTransId = :nocTransId " + 
		       " AND c.nocNo = :nocNo " + 
		       " AND c.boeNo = :boeNo " + 
		       "ORDER BY c.boeNo DESC")
	Cfinbondcrg getDataOfBoeNoForEntryInExbond(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("nocTransId") String nocTransId,
		    @Param("nocNo") String nocNo,
		    @Param("boeNo") String boeNo
		);

	 
	 
	 
	 
	 
	 @Modifying
	 @Transactional
	 @Query("UPDATE CfinbondcrgHDR c SET c.exBondedPackages = :exBondedPackages, " +
	        "c.exBondedCargoDuty = :exBondedCargoDuty, " +
	        "c.exBondedInsurance = :exBondedInsurance, " +
	        "c.exBondedCif = :exBondedCif, " +
	        "c.exBondedGw = :exBondedGw " +
	        "WHERE c.companyId = :companyId AND " +
	        "c.branchId = :branchId AND " +
	        "c.nocTransId = :nocTransId AND " +
	        "c.nocNo = :nocNo AND " +
	        "c.inBondingHdrId = :inBondingHdrId AND " +
	        "c.boeNo = :boeNo")
	 int updateCfInbondHeaderAfterExbond(
	     @Param("exBondedPackages") BigDecimal exBondedPackages,
	     @Param("exBondedCargoDuty") BigDecimal exBondedCargoDuty,
	     @Param("exBondedInsurance") BigDecimal exBondedInsurance,
	     @Param("exBondedCif") BigDecimal exBondedCif,
	     @Param("exBondedGw") BigDecimal exBondedGw,
	     @Param("companyId") String companyId,
	     @Param("branchId") String branchId,
	     @Param("nocTransId") String nocTransId,
	     @Param("nocNo") String nocNo,
	     @Param("inBondingHdrId") String inBondingHdrId,
	     @Param("boeNo") String boeNo
	 );

		
}