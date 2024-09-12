package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfbondinsbal;
import com.cwms.entities.CFBondGatePass;
import jakarta.transaction.Transactional;
import com.cwms.entities.Cfinbondcrg;
import java.math.BigDecimal;
@EnableJpaRepositories
public interface CfBondNocDtlRepository extends JpaRepository<CfBondNocDtl,String>{

	
//	@Query("SELECT NEW com.cwms.entities.CfBondNocDtl(c.companyId, c.branchId, c.nocTransId, c.nocNo, c.cfBondDtlId, " +
//	           "c.boeNo, c.nocPackages, c.cifValue, c.cargoDuty, c.insuranceValue, " +
//	           "c.typeOfPackage, c.commodityDescription, c.status,c.grossWeight,c.gateInPackages,c.weightTakenIn,c.inBondedPackages,c.inbondGrossWt,c.inbondCargoDuty,c.inbondCifValue,"
//	           + "c.shortagePackages,c.damagedQty,c.breakage) " +
//	           "FROM CfBondNocDtl c " +
//	           "WHERE c.companyId = :companyId " +
//	           "AND c.branchId = :branchId " +
//	           "AND c.nocTransId = :nocTransId " +
//	           "AND c.nocNo = :nocNo " +
//	           "AND c.status != 'D'")
//	    List<CfBondNocDtl> getCfBondNocDtl(@Param("companyId") String companyId, 
//	                                       @Param("branchId") String branchId, 
//	                                       @Param("nocTransId") String nocTransId, 
//	                                       @Param("nocNo") String nocNo);
	
	
	@Query("SELECT Distinct NEW com.cwms.entities.CfBondNocDtl(c.companyId, c.branchId, c.nocTransId, c.nocNo, c.cfBondDtlId, " +
		       "c.boeNo, c.nocPackages, c.cifValue, c.cargoDuty, c.insuranceValue, " +
		       "c.typeOfPackage, c.commodityDescription, c.status, c.grossWeight, c.gateInPackages, c.weightTakenIn, " +
		       "c.inBondedPackages, c.inbondGrossWt, c.inbondCargoDuty, c.inbondCifValue,  " +
		       "c.shortagePackages, c.damagedQty, c.breakage,cd.yardLocationId, cd.blockId,cd.cellNoRow,cd.areaOccupied) " +
		       "FROM CfBondNocDtl c " +
		       "LEFT OUTER JOIN CfinbondcrgDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo AND c.cfBondDtlId = cd.cfBondDtlId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.nocNo = :nocNo " +
		       "AND c.status != 'D'")
		List<CfBondNocDtl> getCfBondNocDtl(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId, 
		                                   @Param("nocTransId") String nocTransId, 
		                                   @Param("nocNo") String nocNo);

		       

	
	@Modifying
	@Transactional
	@Query("update CfBondNocDtl c set c.inBondedPackages = :inBondedPackages, c.inbondGrossWt = :inbondGrossWt, c.inbondInsuranceValue = :inbondInsuranceValue,"
			+ " c.inbondCifValue = :inbondCifValue, "
			+ "c.inbondCargoDuty = :inbondCargoDuty, "
			+ "c.shortagePackages = :shortagePackages, "
			+ "c.damagedQty = :damagedQty, "
			+ "c.breakage = :breakage, "
			+ "c.bondingNo = :bondingNo "
	        + "where c.companyId = :companyId and "
	        + "c.branchId = :branchId and "
	        + "c.nocTransId = :nocTransId and "
	        + "c.nocNo = :nocNo and "
	        + "c.cfBondDtlId = :cfBondDtlId")
	int updateNocDtlFromInBonding( 
	       @Param("inBondedPackages") BigDecimal inBondedPackages,
	       @Param("inbondGrossWt") BigDecimal inbondGrossWt,
	       @Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
	       @Param("inbondCifValue") BigDecimal inbondCifValue,
	       @Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
	       @Param("shortagePackages") BigDecimal shortagePackages,
	       @Param("damagedQty") BigDecimal damagedQty,
	       @Param("breakage") BigDecimal breakage,
	       @Param("bondingNo") String bondingNo,
	       @Param("companyId") String companyId,
	       @Param("branchId") String branchId,
	       @Param("nocTransId") String nocTransId,
	       @Param("nocNo") String nocNo,
	       @Param("cfBondDtlId") String cfBondDtlId);

	
	
	
	@Query("SELECT c FROM CfBondNocDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.cfBondDtlId = :cfBondDtlId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
	CfBondNocDtl getDataOfDtlId(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("cfBondDtlId") String cfBondDtlId, 
	    @Param("nocTransId") String nocTransId, 
	    @Param("nocNo") String nocNo
	);

	
	@Query("SELECT c FROM Cfbondinsbal c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D'")
	Cfbondinsbal getDataOfCfBondCifForValidation(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId
	);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Cfbondinsbal c SET c.inbondAres = :inbondAres, c.inbondCargoDuty = :inbondCargoDuty, c.inbondCifValue = :inbondCifValue WHERE c.companyId = :companyId AND c.branchId = :branchId")
	int updateCfbondinsbal(
	    @Param("inbondAres") BigDecimal inbondAres,
	    @Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
	    @Param("inbondCifValue") BigDecimal inbondCifValue,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);

	
	@Modifying
	@Transactional
	@Query("UPDATE CfBondNocDtl c SET c.gateInPackages = :gateInPackages,c.weightTakenIn=:weightTakenIn WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId=:nocTransId AND c.cfBondDtlId=:cfBondDtlId AND c.nocNo=:nocNo")
	int updateCfBondDetails(
	    @Param("gateInPackages") BigDecimal gateInPackages,
	    @Param("weightTakenIn") BigDecimal weightTakenIn,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("nocTransId") String nocTransId,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("nocNo") String nocNo
	);

	 @Query("SELECT c FROM CfBondNocDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.cfBondDtlId = :cfBondDtlId AND c.nocNo = :nocNo")
	    CfBondNocDtl findCfBondDetails(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("cfBondDtlId") String cfBondDtlId,
	        @Param("nocNo") String nocNo
	    );
	 
	 
	 
	 @Query("SELECT c FROM CfBondNocDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.cfBondDtlId = :cfBondDtlId AND c.nocNo = :nocNo")
	    List<CfBondNocDtl> findCfBondDetailsForUpdate(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("cfBondDtlId") String cfBondDtlId,
	        @Param("nocNo") String nocNo
	    );
	 
	 
	 
	 @Query("select count(c) > 0  from  CfBondNocDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND "
	 		+ "c.nocNo =:nocNo  and c.nocTransId =:nocTransId and (c.nocPackages - c.gateInPackages) > 0  "
			 )
	 Boolean checkGateInPackages (
			 @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("nocNo") String nocNo,
		        @Param("nocTransId") String nocTransId);
	 
	 
	 
	 
	 @Query("select count(c) > 0  from  CfBondNocDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND "
		 		+ "c.nocNo =:nocNo  and c.nocTransId =:nocTransId and (c.gateInPackages - c.inBondedPackages) > 0  "
				 )
		 Boolean checkInBondedPackages (
				 @Param("companyId") String companyId,
			        @Param("branchId") String branchId,
			        @Param("nocNo") String nocNo,
			        @Param("nocTransId") String nocTransId);
	 
	 
	 
	 @Query("select count(c) > 0 from CfBondNocDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND "
		        + "c.nocNo = :nocNo AND c.nocTransId = :nocTransId AND "
		        + "COALESCE(c.gateInPackages, 0) - c.inBondedPackages = 0 AND "
		        + "c.inBondedPackages > 0")
		Boolean checkInBondedPackagesAllDone(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("nocNo") String nocNo,
		        @Param("nocTransId") String nocTransId);

	 
	 
	 
	 @Query("select count(c) > 0  from  CfinbondcrgHDRDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND "
		 		+ "c.nocNo =:nocNo  and c.nocTransId =:nocTransId "
		 		+ "and (COALESCE(c.inBondedPackages, 0) - COALESCE(c.exBondedPackages, 0)) > 0  "
				 )
		 Boolean checkInBondedHdeDtl (
				 @Param("companyId") String companyId,
			        @Param("branchId") String branchId,
			        @Param("nocNo") String nocNo,
			        @Param("nocTransId") String nocTransId);
	 
	 
	 
	 @Query("select count(c) > 0 from CfinbondcrgHDRDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND "
		        + "c.nocNo = :nocNo AND c.nocTransId = :nocTransId AND "
		        + "COALESCE(c.inBondedPackages, 0) - c.exBondedPackages = 0 AND "
		        + "c.exBondedPackages > 0")
		Boolean checkExBondedPackagesAllDone(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("nocNo") String nocNo,
		        @Param("nocTransId") String nocTransId);

	 
//	 @Query("select count(c) > 0  from  CFBondGatePass c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND "
//		 		+ "c.nocNo =:nocNo  and c.nocTransId =:nocTransId and (COALESCE( c.exBondedPackages ,0)- COALESCE(c.qtyTakenOut,0)) > 0  "
//				 )
//		 Boolean checkInForGatePass (
//				 @Param("companyId") String companyId,
//			        @Param("branchId") String branchId,
//			        @Param("nocNo") String nocNo,
//			        @Param("nocTransId") String nocTransId);
	 
	 
	 @Query("select count(c) > 0 from CFBondGatePass c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status != 'D' AND " +
		       "c.nocNo = :nocNo AND c.nocTransId = :nocTransId AND (COALESCE(c.exBondedPackages, 0) - COALESCE(c.qtyTakenOut, 0)) > 0"
		      )
		Boolean checkInForGatePass(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("nocNo") String nocNo,
		    @Param("nocTransId") String nocTransId
		);

	 
	 
	 @Query("SELECT NEW com.cwms.entities.CfBondNocDtl(" +
		        "cc.importerName, p.partyName, c.nocTransId, c.nocNo, c.cfBondDtlId, " +
		        "c.boeNo, c.CfbondDetailDate, c.nocPackages, c.cifValue, c.cargoDuty, " +
		        "c.insuranceValue, c.typeOfPackage, c.commodityDescription, c.status, " +
		        "cc.cha, cc.nocDate, cc.igmNo, c.editedDate, cc.igmLineNo, " +
		        "c.approvedDate, c.gateInPackages, c.qtyTakenIn, c.weightTakenIn, " +
		        "c.grossWeight, c.inBondedPackages, c.inbondGrossWt, c.inbondInsuranceValue, " +
		        "c.inbondCifValue, c.inbondCargoDuty, c.shortagePackages, c.damagedQty, " +
		        "c.breakage, c.areaOccupied, c.bondingNo) " +
		        "FROM CfBondNocDtl c " +
		        "LEFT OUTER JOIN Cfbondnoc cc ON c.companyId =cc.companyId and c.branchId=cc.branchId and c.nocNo =cc.nocNo and c.nocTransId =cc.nocTransId "+
		        "LEFT OUTER JOIN Party p ON p.companyId =cc.companyId and p.branchId=cc.branchId and p.partyId =cc.cha "+
		        "WHERE c.companyId = :companyId " +
		        "AND c.branchId = :branchId " +
		        "AND c.nocTransId = :nocTransId " +
		        "AND c.nocNo = :nocNo " +
		        "AND (c.nocPackages - c.gateInPackages) > 0 "+
		        "AND c.status != 'D'")
		List<CfBondNocDtl> forMainSearhcOfGateInBondScreen(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId, 
		        @Param("nocTransId") String nocTransId, 
		        @Param("nocNo") String nocNo);


	 @Query("SELECT NEW com.cwms.entities.CFBondGatePass(c.companyId, c.branchId, c.finYear, c.gatePassId, c.exBondingId, " +
		       "c.inBondingId, c.nocNo, c.nocTransId, c.bondingNo) " +
		       "FROM CFBondGatePass c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.nocNo = :nocNo " +
		       "AND c.status != 'D' " +
		       "ORDER BY c.createdDate DESC")
		CFBondGatePass getCfBondGatePass(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("nocTransId") String nocTransId, 
		    @Param("nocNo") String nocNo);
	 
	 
	 @Query("SELECT new com.cwms.entities.Cfinbondcrg(c.inBondingId, c.boeNo) "
		     + "FROM Cfinbondcrg c "
		     + "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo "
		     + "ORDER BY c.inBondingId DESC")
		List<Cfinbondcrg> getInBondingIdAndBoeNo(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("nocTransId") String nocTransId,
		        @Param("nocNo") String nocNo);
	 
	 
	 
	 @Query("SELECT new com.cwms.entities.CfExBondCrg(c.exBondingId, c.inBondingId) "
		     + "FROM CfExBondCrg c "
		     + "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo "
		     + "ORDER BY c.createdDate DESC")
		List<CfExBondCrg> getExbondIdAndInbondId(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("nocTransId") String nocTransId,
		        @Param("nocNo") String nocNo);

		// In your repository method, limit the result to 1:


}
