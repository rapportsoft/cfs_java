package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.Cfinbondcrg;


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
		       "c.approvedBy, c.approvedDate, c.shortagePackages, c.damagedQty, c.breakage) " +
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
		       "c.approvedBy, c.approvedDate, c.shortagePackages, c.damagedQty, c.breakage) " +
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


}
