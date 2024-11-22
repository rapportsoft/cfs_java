package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.CfinbondCommdtlEdit;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.CfinbondcrgHDR;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
public interface CfinbondcrgDtlRepo extends JpaRepository<CfinbondcrgDtl, String> {

	@Query("SELECT c FROM CfinbondcrgDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.cfBondDtlId = :cfBondDtlId AND c.nocNo = :nocNo")
	CfinbondcrgDtl findCfBondCrgDTLData(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("cfBondDtlId") String cfBondDtlId,
	        @Param("nocNo") String nocNo
	    );
	
	
	
	@Query("SELECT c FROM CfinbondcrgDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId=:inBondingId and c.nocTransId = :nocTransId AND c.cfBondDtlId = :cfBondDtlId AND c.nocNo = :nocNo")
	CfinbondcrgDtl findCfBondCrgDTLData(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("inBondingId") String inBondingId, 
	        @Param("nocTransId") String nocTransId,
	        @Param("cfBondDtlId") String cfBondDtlId,
	        @Param("nocNo") String nocNo
	    );
	
	
	@Query("SELECT c FROM CfinbondcrgDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId=:inBondingId and c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
	List<CfinbondcrgDtl> forAuditTrailCode(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("inBondingId") String inBondingId, 
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo
	    );
	
	@Query("SELECT b FROM CfinbondcrgDtl b WHERE b.companyId = :cid AND b.branchId = :bid AND b.inBondingId = :inBondingId AND b.nocTransId = :nocTransId")
	public List<CfinbondcrgDtl> findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
	    @Param("cid") String cid, 
	    @Param("bid") String bid, 
	    @Param("inBondingId") String inBondingId, 
	    @Param("nocTransId") String nocTransId
	);
	
//	@Query("SELECT Distinct NEW com.cwms.entities.CfinbondcrgDtl(c.companyId, c.branchId, c.finYear, c.inBondingDtlId, c.inBondingId, " +
//		       "c.nocTransId, c.nocNo, c.cfBondDtlId, c.boeNo, c.inBondingDate, c.nocTransDate, " +
//		       "c.nocDate, c.bondingNo, c.bondingDate, c.nocPackages, c.typeOfPackage, " +
//		       "c.commodityDescription, c.exBondedPackages, c.inBondedPackages, c.inbondGrossWt, " +
//		       "c.inbondInsuranceValue, c.inbondCifValue, c.inbondCargoDuty, c.status, c.createdBy, " +
//		       "c.editedBy, c.approvedBy, c.yardLocationId, c.blockId, c.cellNoRow, c.areaOccupied,c.exBondedCIF,c.exBondedCargoDuty,c.exBondedInsurance,c.exBondedGW) " +
//		       "FROM CfinbondcrgDtl c " +
//		       "LEFT OUTER JOIN CfexBondCrgDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo AND c.cfBondDtlId = cd.cfBondDtlId AND c.inBondingId =cd.inBondingId " +
//		       "WHERE c.companyId = :companyId " +
//		       "AND c.branchId = :branchId " +
//		       "AND c.nocTransId = :nocTransId " +
//		       "AND c.nocNo = :nocNo " +
//		       "AND c.inBondingId = :inBondingId " +
//		       "AND c.boeNo = :boeNo " +
//		       "AND c.status  = 'A'")
//		List<CfinbondcrgDtl> getCfBondInBondDTLData(@Param("companyId") String companyId, 
//		                                     @Param("branchId") String branchId, 
//		                                     @Param("nocTransId") String nocTransId, 
//		                                     @Param("nocNo") String nocNo,
//		                                     @Param("inBondingId") String inBondingId, 
//		                                     @Param("boeNo") String boeNo);
	
	@Query("SELECT Distinct NEW com.cwms.entities.CfinbondcrgDtl(c.companyId, c.branchId, c.finYear, c.inBondingDtlId, c.inBondingId, " +
		       "c.nocTransId, c.nocNo, c.cfBondDtlId, c.boeNo, c.inBondingDate, c.nocTransDate, " +
		       "c.nocDate, c.bondingNo, c.bondingDate, c.nocPackages, c.typeOfPackage, " +
		       "c.commodityDescription, c.exBondedPackages, c.inBondedPackages, c.inbondGrossWt, " +
		       "c.inbondInsuranceValue, c.inbondCifValue, c.inbondCargoDuty, c.status, c.createdBy, " +
		       "c.editedBy, c.approvedBy, c.yardLocationId, c.blockId, c.cellNoRow, c.areaOccupied,c.exBondedCIF,c.exBondedCargoDuty,c.exBondedInsurance,c.exBondedGW,c.yardPackages,c.cellAreaAllocated,c.cellArea,cd.exBondyardPackages,cd.exBondGridArea) " +
		       "FROM CfinbondcrgDtl c " +
		       "LEFT OUTER JOIN CfexBondCrgDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo AND c.cfBondDtlId = cd.cfBondDtlId AND c.inBondingId =cd.inBondingId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.nocNo = :nocNo " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.boeNo = :boeNo " +
		       "AND (c.inBondedPackages - COALESCE(c.exBondedPackages, 0) > 0) " +
		       "AND c.status  = 'A' " +
		       "GROUP BY c.inBondingId,c.cfBondDtlId,c.nocTransId ,c.nocNo")
		List<CfinbondcrgDtl> getCfBondInBondDTLData(@Param("companyId") String companyId, 
		                                     @Param("branchId") String branchId, 
		                                     @Param("nocTransId") String nocTransId, 
		                                     @Param("nocNo") String nocNo,
		                                     @Param("inBondingId") String inBondingId, 
		                                     @Param("boeNo") String boeNo);
	
	
	@Query("SELECT Distinct NEW com.cwms.entities.CfinbondcrgDtl(c.companyId, c.branchId, c.finYear, c.inBondingDtlId, c.inBondingId, " +
		       "c.nocTransId, c.nocNo, c.cfBondDtlId, c.boeNo, c.inBondingDate, c.nocTransDate, " +
		       "c.nocDate, c.bondingNo, c.bondingDate, c.nocPackages, c.typeOfPackage, " +
		       "c.commodityDescription, c.exBondedPackages, c.inBondedPackages, c.inbondGrossWt, " +
		       "c.inbondInsuranceValue, c.inbondCifValue, c.inbondCargoDuty, c.status, c.createdBy, " +
		       "c.editedBy, c.approvedBy, c.yardLocationId, c.blockId, c.cellNoRow, c.areaOccupied,c.exBondedCIF,c.exBondedCargoDuty,c.exBondedInsurance,c.exBondedGW,c.yardPackages,c.cellAreaAllocated,c.cellArea,cd.exBondyardPackages,cd.exBondGridArea) " +
		       "FROM CfinbondcrgDtl c " +
		       "LEFT OUTER JOIN CfexBondCrgDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo AND c.cfBondDtlId = cd.cfBondDtlId AND c.inBondingId =cd.inBondingId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.nocNo = :nocNo " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.boeNo = :boeNo " +
		       "AND (c.inBondedPackages - COALESCE(c.exBondedPackages, 0) > 0) " +
		       "AND c.status  = 'A' " +
		       "GROUP BY c.inBondingId,c.cfBondDtlId,c.nocTransId ,c.nocNo")
		List<CfinbondcrgDtl> getCfBondInBondDTLDataToCheck(@Param("companyId") String companyId, 
		                                     @Param("branchId") String branchId, 
		                                     @Param("nocTransId") String nocTransId, 
		                                     @Param("nocNo") String nocNo,
		                                     @Param("inBondingId") String inBondingId, 
		                                     @Param("boeNo") String boeNo);
	
	
	@Query("SELECT Distinct NEW com.cwms.entities.CfinbondcrgDtl(c.companyId, c.branchId, c.finYear, c.inBondingDtlId, c.inBondingId, " +
		       "c.nocTransId, c.nocNo, c.cfBondDtlId, c.boeNo, c.inBondingDate, c.nocTransDate, " +
		       "c.nocDate, c.bondingNo, c.bondingDate, c.nocPackages, c.cifValue, c.cargoDuty, cd.insuranceValue, " +
		       "c.grossWeight, c.typeOfPackage, c.commodityDescription, c.exBondedPackages, c.toBondedPackages, c.comments, " +
		       "c.inBondedPackages, c.inbondGrossWt, c.inbondInsuranceValue, c.inbondCifValue, c.inbondCargoDuty, " +
		       "c.status, c.createdBy, c.createdDate, c.editedBy, c.editedDate, c.approvedBy, c.approvedDate, " +
		       "c.shortagePackages, c.damagedQty, c.breakage, c.yardLocationId, c.blockId, c.cellNoRow, c.areaOccupied, " +
		       "c.yardPackages, c.cellAreaAllocated, c.cellArea, cd.gateInPackages) " +
		       "FROM CfinbondcrgDtl c " +
		       "LEFT OUTER JOIN CfBondNocDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo AND c.cfBondDtlId = cd.cfBondDtlId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.status = 'A' " +
		       "GROUP BY c.inBondingId, c.cfBondDtlId, c.nocTransId, c.nocNo")
		List<CfinbondcrgDtl> getAfterSave( @Param("companyId") String companyId, 
			    @Param("branchId") String branchId, 
			    @Param("inBondingId") String inBondingId, 
			    @Param("nocTransId") String nocTransId);

	
	
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE CfinbondcrgDtl c SET c.exBondedPackages = :exBondedPackages, " +
	       "c.exBondedCargoDuty = :exBondedCargoDuty, " +
	       "c.exBondedCIF = :exBondedCIF, " +
	       "c.exBondedInsurance = :exBondedInsurance, " +
	       "c.exBondedGW = :exBondedGW " +
	       "WHERE c.companyId = :companyId AND " +
	       "c.branchId = :branchId AND " +
	       "c.inBondingId = :inBondingId AND " +
	       "c.nocNo = :nocNo AND " +
	       "c.nocTransId = :nocTransId AND " +
	       "c.boeNo = :boeNo AND " +
	       "c.cfBondDtlId = :cfBondDtlId")
	int updateCfinbondCrgDtlAfterExbond(
	    @Param("exBondedPackages") BigDecimal exBondedPackages,
	    @Param("exBondedCargoDuty") BigDecimal exBondedCargoDuty,
	    @Param("exBondedCIF") BigDecimal exBondedCIF,
	    @Param("exBondedInsurance") BigDecimal exBondedInsurance,
	    @Param("exBondedGW") BigDecimal exBondedGW,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("inBondingId") String inBondingId,
	    @Param("nocNo") String nocNo,
	    @Param("nocTransId") String nocTransId,
	    @Param("boeNo") String boeNo,
	    @Param("cfBondDtlId") String cfBondDtlId
	);

	
	@Query("SELECT c FROM CfinbondcrgDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.cfBondDtlId = :cfBondDtlId AND c.nocNo = :nocNo and c.inBondingId=:inBondingId")
	CfinbondcrgDtl toUpdateInBondCrgDtl(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("cfBondDtlId") String cfBondDtlId,
	        @Param("nocNo") String nocNo,
	        @Param("inBondingId") String inBondingId
	    );

	
	@Query("SELECT c FROM CfinbondcrgDtl c WHERE c.companyId = :cid AND c.branchId = :bid AND c.inBondingId = :inBondingId " +
		       "AND c.cfBondDtlId = :cfBondDtlId AND c.status != 'D' AND c.yardLocationId = :yardLocationId " +
		       "AND c.blockId = :blockId AND c.cellNoRow = :cellNoRow")
	CfinbondcrgDtl toEditData(
		    @Param("cid") String cid,
		    @Param("bid") String bid,
		    @Param("inBondingId") String inBondingId,
		    @Param("cfBondDtlId") String cfBondDtlId,
		    @Param("yardLocationId") String yardLocationId,
		    @Param("blockId") String blockId,
		    @Param("cellNoRow") String cellNoRow
		);
	
	
	@Query("SELECT COALESCE(SUM(c.inBondedPackages), 0) FROM CfinbondcrgDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal getSumOfInBondPackages(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("inBondingId") String inBondingId,
		                            @Param("nocTransId") String nocTransId);
	
	
	
	@Query("SELECT COALESCE(SUM(c.inBondedPackages), 0) FROM CfinbondcrgDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.cfBondDtlId = :cfBondDtlId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal getSumOfInBondPackagesForCommodity(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("inBondingId") String inBondingId,
		                            @Param("cfBondDtlId") String cfBondDtlId,
		                            @Param("nocTransId") String nocTransId);
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE CfinbondcrgDtl c SET c.boeNo = :boeNo, " +
	       "c.bondingNo = :bondingNo, " +
	       "c.bondingDate = :bondingDate " +
	       "WHERE c.companyId = :companyId AND " +
	       "c.branchId = :branchId AND " +
	       "c.inBondingId = :inBondingId AND " +
	       "c.nocNo = :nocNo AND " +
	       "c.nocTransId = :nocTransId ")
	int updateCfinbondCrgDtlAfterInBondAuditTrailHeaderChange(
	    @Param("boeNo") String boeNo,
	    @Param("bondingNo") String bondingNo,
	    @Param("bondingDate") Date bondingDate,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("inBondingId") String inBondingId,
	    @Param("nocNo") String nocNo,
	    @Param("nocTransId") String nocTransId
	);
}
