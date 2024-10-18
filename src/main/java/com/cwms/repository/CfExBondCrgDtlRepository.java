package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfexBondCrgDtl;

import jakarta.transaction.Transactional;

public interface CfExBondCrgDtlRepository extends JpaRepository<CfexBondCrgDtl, String>{

//	@Modifying
//	@Transactional
//	@Query("update CfexBondCrgDtl c set c.editedBy=:editedBy, c.editedDate=:editedDate, c.exBondedPackages=:exBondedPackages, "
//	        + "c.exBondedCIF=:exBondedCIF, c.exBondedCargoDuty=:exBondedCargoDuty, c.exBondedInsurance=:exBondedInsurance, c.exBondedGW=:exBondedGW "
//	        + "where c.companyId=:companyId and "
//	        + "c.branchId=:branchId and "
//	        + "c.cfBondDtlId=:cfBondDtlId and "
//	        + "c.nocTransId=:nocTransId and "
//	        + "c.nocNo=:nocNo and "
//	        + "c.inBondingId=:inBondingId and "
//	        + "c.exBondingId=:exBondingId")
//	int updateExbondCrgDetail(@Param("editedBy") String editedBy,
//	                          @Param("editedDate") Date editedDate,
//	                          @Param("exBondedPackages") BigDecimal exBondedPackages,
//	                          @Param("exBondedCIF") BigDecimal exBondedCIF,
//	                          @Param("exBondedCargoDuty") BigDecimal exBondedCargoDuty,
//	                          @Param("exBondedInsurance") BigDecimal exBondedInsurance,
//	                          @Param("exBondedGW") BigDecimal exBondedGW,
//	                          @Param("companyId") String companyId,
//	                          @Param("branchId") String branchId,
//	                          @Param("cfBondDtlId") String cfBondDtlId,
//	                          @Param("nocTransId") String nocTransId,
//	                          @Param("nocNo") String nocNo,
//	                          @Param("inBondingId") String inBondingId,
//	                          @Param("exBondingId") String exBondingId);
	
	
	@Modifying
	@Transactional
	@Query("update CfexBondCrgDtl c set c.editedBy=:editedBy, c.editedDate=:editedDate, c.exBondedPackages=:exBondedPackages, "
	        + "c.exBondedCIF=:exBondedCIF, c.exBondedCargoDuty=:exBondedCargoDuty, c.exBondedInsurance=:exBondedInsurance, c.exBondedGW=:exBondedGW ,c.exBondyardPackages=:exBondyardPackages,c.exBondGridArea=:exBondGridArea ,c.status=:status "
	        + "where c.companyId=:companyId and "
	        + "c.branchId=:branchId and "
	        + "c.cfBondDtlId=:cfBondDtlId and "
	        + "c.nocTransId=:nocTransId and "
	        + "c.nocNo=:nocNo and "
	        + "c.inBondingId=:inBondingId and "
	        + "c.exBondingId=:exBondingId")
	int updateExbondCrgDetail(@Param("editedBy") String editedBy,
	                          @Param("editedDate") Date editedDate,
	                          @Param("exBondedPackages") BigDecimal exBondedPackages,
	                          @Param("exBondedCIF") BigDecimal exBondedCIF,
	                          @Param("exBondedCargoDuty") BigDecimal exBondedCargoDuty,
	                          @Param("exBondedInsurance") BigDecimal exBondedInsurance,
	                          @Param("exBondedGW") BigDecimal exBondedGW,
	                          @Param("exBondyardPackages") BigDecimal exBondyardPackages,
	                          @Param("exBondGridArea") BigDecimal exBondGridArea,
	                          @Param("status") Character status,
	                          @Param("companyId") String companyId,
	                          @Param("branchId") String branchId,
	                          @Param("cfBondDtlId") String cfBondDtlId,
	                          @Param("nocTransId") String nocTransId,
	                          @Param("nocNo") String nocNo,
	                          @Param("inBondingId") String inBondingId,
	                          @Param("exBondingId") String exBondingId);
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE CfexBondCrgDtl c SET c.outQty = :outQty  WHERE c.companyId = :companyId AND c.branchId = :branchId and c.exBondingId=:exBondingId and c.exBondBeNo=:exBondBeNo and c.cfBondDtlId=:cfBondDtlId")
	int updateCfexbondDtlAfterGatePass(
	    @Param("outQty") BigDecimal outQty,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("exBondingId") String exBondingId,
	    @Param("exBondBeNo") String exBondBeNo,
	    @Param("cfBondDtlId") String cfBondDtlId
	);
	
	
	@Query("SELECT c FROM CfexBondCrgDtl c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo AND c.cfBondDtlId = :cfBondDtlId AND c.inBondingId = :inBondingId AND c.exBondingId = :exBondingId")
	CfexBondCrgDtl findExistingCfexbondCrgDtl(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("nocTransId") String nocTransId,
	    @Param("nocNo") String nocNo,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("inBondingId") String inBondingId,
	    @Param("exBondingId") String exBondingId
	);

	
	@Query("SELECT NEW com.cwms.entities.CfexBondCrgDtl(" +
		       "c.companyId, c.branchId, c.finYear, c.cfBondDtlId, c.nocTransId, " +
		       "c.exBondingId, c.inBondingId, c.nocNo, c.boeNo, c.bondingNo, " +
		       "c.bondingDate, c.exBondBeNo, c.nocPackages, c.exBondedPackages, " +
		       "c.exBondType, c.exBondedCIF, c.exBondedCargoDuty, c.exBondedInsurance, " +
		       "c.status, c.inBondedPackages, c.inbondGrossWt, c.inbondInsuranceValue, " +
		       "c.inbondCifValue, c.inbondCargoDuty) " +
		       "FROM CfexBondCrgDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.nocNo = :nocNo " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.boeNo = :boeNo " +
		       "AND c.exBondingId = :exBondingId " +
		       "AND c.status != 'D'")
		List<CfexBondCrgDtl> getCfBondInBondDTLData(@Param("companyId") String companyId, 
		                                            @Param("branchId") String branchId, 
		                                            @Param("nocTransId") String nocTransId, 
		                                            @Param("nocNo") String nocNo,
		                                            @Param("inBondingId") String inBondingId, 
		                                            @Param("boeNo") String boeNo,
		                                            @Param("exBondingId") String exBondingId);


	
	
	
	
	
	

	@Query(value = "select DISTINCT c.exBondBeNo,c.nocNo,c.boeNo,c.cfBondDtlId,c.nocTransId,c.bondingNo,c.status " +
            "from CfexBondCrgDtl c " +
            "where c.companyId = :cid and c.branchId = :bid " +
            "and COALESCE(c.exBondedPackages, 0) - COALESCE(c.outQty, 0) > 0 " +
            "and c.status != 'D' " +
            "and (:val is null OR :val = '' OR c.exBondBeNo LIKE CONCAT(:val, '%'))")
List<Object[]> getAllExbondBeNoFromExbondDtl(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);






@Query(value = "select DISTINCT c.cfBondDtlId, c.commodityDescription,c.yardLocationId,c.blockId,c.cellNoRow,c.exBondedPackages,"
		+ "c.exBondingId,c.inBondingId,c.nocPackages,c.inBondedPackages,c.exBondedGW ,c.exBondGridArea, c.nocTransId ,c.outQty " +
        "from CfexBondCrgDtl c " +
        "where c.companyId = :cid and c.branchId = :bid and c.exBondBeNo = :exBondBeNo " +
        "and COALESCE(c.exBondedPackages, 0) - COALESCE(c.outQty, 0) > 0 " +
        "and c.status != 'D' " +
        "and (:val is null OR :val = '' OR c.commodityDescription LIKE CONCAT(:val, '%'))")
List<Object[]> getAllExbondCargoFromExbondDtl(@Param("cid") String cid, 
                                       @Param("bid") String bid, 
                                       @Param("exBondBeNo") String exBondBeNo, 
                                       @Param("val") String val);




@Modifying
@Transactional
@Query("UPDATE CfexBondCrgDtl c SET c.exBondyardPackages = :exBondyardPackages, c.exBondGridArea = :exBondGridArea " +
       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.exBondingId = :exBondingId " +
       "AND c.cfBondDtlId = :cfBondDtlId AND c.yardLocationId = :yardLocationId AND c.blockId = :blockId AND c.cellNoRow = :cellNoRow")
int updateCfexBondDtlAfterExBondGrid(
    @Param("exBondyardPackages") BigDecimal exBondyardPackages,
    @Param("exBondGridArea") BigDecimal exBondGridArea,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("exBondingId") String exBondingId,
    @Param("cfBondDtlId") String cfBondDtlId,
    @Param("yardLocationId") String yardLocationId,
    @Param("blockId") String blockId,
    @Param("cellNoRow") String cellNoRow
);


@Query("SELECT c FROM CfexBondCrgDtl c WHERE c.companyId = :cid AND c.branchId = :bid AND c.exBondingId = :exBondingId " +
	       "AND c.cfBondDtlId = :cfBondDtlId AND c.status != 'D' AND c.yardLocationId = :yardLocationId " +
	       "AND c.blockId = :blockId AND c.cellNoRow = :cellNoRow")
	CfexBondCrgDtl toEditData(
	    @Param("cid") String cid,
	    @Param("bid") String bid,
	    @Param("exBondingId") String exBondingId,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("yardLocationId") String yardLocationId,
	    @Param("blockId") String blockId,
	    @Param("cellNoRow") String cellNoRow
	);






@Query("SELECT COALESCE(SUM(c.exBondedPackages), 0) FROM CfexBondCrgDtl c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.exBondingId = :exBondingId " +
	       "AND c.nocTransId = :nocTransId")
BigDecimal getSumOfInBondPackages(@Param("companyId") String companyId,
	                            @Param("branchId") String branchId,
	                            @Param("exBondingId") String exBondingId,
	                            @Param("nocTransId") String nocTransId);

@Query("SELECT COALESCE(SUM(c.exBondedPackages), 0) FROM CfexBondCrgDtl c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.exBondingId = :exBondingId " +
	       "AND c.cfBondDtlId = :cfBondDtlId " +
	       "AND c.nocTransId = :nocTransId")
BigDecimal getSumOfInBondPackagesForCommodity(@Param("companyId") String companyId,
	                            @Param("branchId") String branchId,
	                            @Param("exBondingId") String exBondingId,
	                            @Param("cfBondDtlId") String cfBondDtlId,
	                            @Param("nocTransId") String nocTransId);
@Transactional
@Modifying
@Query("UPDATE CfExBondCrg c SET c.status =:status WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.exBondingId = :exBondingId AND c.nocNo = :nocNo AND c.nocTransId = :nocTransId")
int updateAfterApprove(
		 @Param("status") String status,
		 @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("exBondingId") String exBondingId,
        @Param("nocNo") String nocNo,
        @Param("nocTransId") String nocTransId);


@Query("SELECT c FROM CfexBondCrgDtl c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.nocTransId = :nocTransId " +
	       "AND c.nocNo = :nocNo " +
	       "AND c.inBondingId = :inBondingId " +
	       "AND c.boeNo = :boeNo " +
	       "AND c.exBondingId = :exBondingId " +
	       "AND c.status != 'D'")
	List<CfexBondCrgDtl> updateAfterApprove(@Param("companyId") String companyId, 
	                                        @Param("branchId") String branchId, 
	                                        @Param("nocTransId") String nocTransId, 
	                                        @Param("nocNo") String nocNo,
	                                        @Param("inBondingId") String inBondingId, 
	                                        @Param("boeNo") String boeNo,
	                                        @Param("exBondingId") String exBondingId);


}
