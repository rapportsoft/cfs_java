package com.cwms.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.CfinbondcrgHDRDtl;

import jakarta.transaction.Transactional;

public interface CfinbondCrgHdrDtlRepo extends JpaRepository<CfinbondcrgHDRDtl, String> {

	@Query(value = "select COUNT(c)>0 from CfinbondcrgHDRDtl c where c.companyId=:cid and c.branchId=:bid and c.nocTransId=:nocTransId and c.nocNo =:nocNo and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
	Boolean isDataExist(@Param("cid") String cid, @Param("bid") String bid, @Param("nocTransId") String nocTransId,
			@Param("nocNo") String nocNo, @Param("cfBondDtlId") String cfBondDtlId);

	@Query(value = "select c from CfinbondcrgHDRDtl c where c.companyId = :cid and c.branchId = :bid and c.nocTransId = :nocTransId and c.nocNo = :nocNo and c.cfBondDtlId = :cfBondDtlId and c.status != 'D'")
	CfinbondcrgHDRDtl isDataExistCfinbondcrgHDRDtl(@Param("cid") String cid, @Param("bid") String bid,
			@Param("nocTransId") String nocTransId, @Param("nocNo") String nocNo,
			@Param("cfBondDtlId") String cfBondDtlId);

//	@Modifying
//	@Transactional
//	@Query("update CfinbondcrgHDRDtl c set c.inBondedPackages = :inBondedPackages, " +
//	       "c.inbondInsuranceValue = :inbondInsuranceValue, " +
//	       "c.inbondGrossWt = :inbondGrossWt, " +
//	       "c.inbondCifValue = :inbondCifValue, " +
//	       "c.inbondCargoDuty = :inbondCargoDuty, " +
//	       "c.shortagePackages = :shortagePackages, " +
//	       "c.damagedQty = :damagedQty, " +
//	       "c.breakage = :breakage " +
//	       "where c.companyId = :companyId and " +
//	       "c.branchId = :branchId and " +
//	       "c.nocTransId = :nocTransId and " +
//	       "c.nocNo = :nocNo and "+
//	       "c.cfBondDtlId=:cfBondDtlId")
//	int updateCfInbondHeaderDtl(
//	    @Param("inBondedPackages") BigDecimal inBondedPackages,
//	    @Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
//	    @Param("inbondGrossWt") BigDecimal inbondGrossWt,
//	    @Param("inbondCifValue") BigDecimal inbondCifValue,
//	    @Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
//	    @Param("shortagePackages") BigDecimal shortagePackages,
//	    @Param("damagedQty") BigDecimal damagedQty,
//	    @Param("breakage") BigDecimal breakage,
//	    @Param("companyId") String companyId, // Corrected "compnayId" to "companyId"
//	    @Param("branchId") String branchId,
//	    @Param("nocTransId") String nocTransId,
//	    @Param("nocNo") String nocNo,
//	    @Param("cfBondDtlId") String cfBondDtlId
//	);

	@Modifying
	@Transactional
	@Query("update CfinbondcrgHDRDtl c set c.inBondedPackages = :inBondedPackages, "
			+ "c.inbondInsuranceValue = :inbondInsuranceValue, " + "c.inbondGrossWt = :inbondGrossWt, "
			+ "c.inbondCifValue = :inbondCifValue, " + "c.inbondCargoDuty = :inbondCargoDuty, "
			+ "c.shortagePackages = :shortagePackages, " + "c.damagedQty = :damagedQty, " + "c.breakage = :breakage, "
			+ "c.areaOccupied = :areaOccupied " + "where c.companyId = :companyId and " + "c.branchId = :branchId and "
			+ "c.nocTransId = :nocTransId and " + "c.nocNo = :nocNo and " + "c.cfBondDtlId = :cfBondDtlId")
	int updateCfInbondHeaderDtl(@Param("inBondedPackages") BigDecimal inBondedPackages,
			@Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
			@Param("inbondGrossWt") BigDecimal inbondGrossWt, @Param("inbondCifValue") BigDecimal inbondCifValue,
			@Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
			@Param("shortagePackages") BigDecimal shortagePackages, @Param("damagedQty") BigDecimal damagedQty,
			@Param("breakage") BigDecimal breakage, @Param("areaOccupied") BigDecimal areaOccupied,
			@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("nocTransId") String nocTransId, @Param("nocNo") String nocNo,
			@Param("cfBondDtlId") String cfBondDtlId);

	@Query("SELECT c FROM CfinbondcrgHDRDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.inBondingDtlId = :inBondingDtlId AND c.nocNo = :nocNo")
	CfinbondcrgHDRDtl findCfBondCrgHDRDTLData(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("nocTransId") String nocTransId, @Param("inBondingDtlId") String inBondingDtlId,
			@Param("nocNo") String nocNo);

	
	@Modifying
	@Transactional
	@Query("UPDATE CfinbondcrgHDRDtl c SET c.exBondedPackages = :exBondedPackages " +
	       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND " +
	       "c.bondingNo = :bondingNo AND c.nocNo = :nocNo AND " +
	       "c.nocTransId = :nocTransId AND c.boeNo = :boeNo AND " +
	       "c.cfBondDtlId = :cfBondDtlId")
	int updateCfinbondCrgDtlAfterExbond(
	    @Param("exBondedPackages") BigDecimal exBondedPackages,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("bondingNo") String bondingNo,
	    @Param("nocNo") String nocNo,
	    @Param("nocTransId") String nocTransId,
	    @Param("boeNo") String boeNo,
	    @Param("cfBondDtlId") String cfBondDtlId
	);

	@Query("SELECT c FROM CfinbondcrgHDRDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND " +
		       "c.nocTransId = :nocTransId AND c.nocNo = :nocNo AND c.bondingNo = :bondingNo AND " +
		       "c.cfBondDtlId = :cfBondDtlId")
		CfinbondcrgHDRDtl findExistingHdrdtl(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("nocTransId") String nocTransId,
		    @Param("nocNo") String nocNo,
		    @Param("bondingNo") String bondingNo,
		    @Param("cfBondDtlId") String cfBondDtlId
		);
}
