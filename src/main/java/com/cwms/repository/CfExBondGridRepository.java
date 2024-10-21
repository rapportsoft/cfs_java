package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.CfInBondGrid;

import jakarta.transaction.Transactional;

public interface CfExBondGridRepository extends JpaRepository<CfExBondGrid, String> {
	// Custom queries (if any) can be added here

	@Query("SELECT c from CfExBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.exBondingId=:exBondingId and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
	List<CfExBondGrid> getDataAfterSave(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("exBondingId") String exBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId);
	
	@Query("SELECT MAX(c.srNo) FROM CfExBondGrid c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.exBondingId = :exBondingId AND c.cfBondDtlId = :cfBondDtlId")
	Integer getMaxSrNo(@Param("companyId") String companyId,
	                   @Param("branchId") String branchId,
	                   @Param("exBondingId") String exBondingId,
	                   @Param("cfBondDtlId") String cfBondDtlId);
	
	
	@Query(value="SELECT CASE WHEN COUNT(c) > 0  THEN true ELSE false END  from CfExBondGrid c where c.companyId=:cid and c.branchId=:bid and c.exBondingId=:exBondingId and c.cfBondDtlId =:cfBondDtlId and c.status !='D' and c.yardLocation=:yardLocation "
			+ "and c.yardBlock=:yardBlock and c.blockCellNo=:blockCellNo and c.srNo <>:srNo")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("exBondingId") String exBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("yardLocation") String yardLocation,
			@Param ("yardBlock") String yardBlock,
			@Param ("blockCellNo") String blockCellNo,
			@Param ("srNo") Integer srNo);
	

	@Query("SELECT c from CfExBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.exBondingId=:exBondingId and c.cfBondDtlId=:cfBondDtlId and c.srNo=:srNo")
	CfExBondGrid getExistingData(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("exBondingId") String exBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("srNo") Integer srNo);
	
	@Query(value="SELECT c from CfExBondGrid c where c.companyId=:cid and c.branchId=:bid and c.exBondingId=:exBondingId and c.cfBondDtlId =:cfBondDtlId and c.status !='D' and c.yardLocation=:yardLocation "
			+ "and c.yardBlock=:yardBlock and c.blockCellNo=:blockCellNo")
	CfExBondGrid toEditData(@Param("cid") String cid,@Param("bid") String bid,@Param("exBondingId") String exBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("yardLocation") String yardLocation,
			@Param ("yardBlock") String yardBlock,
			@Param ("blockCellNo") String blockCellNo);
	
	
	
	@Query(value="SELECT c from CfExBondGrid c where c.companyId=:cid and c.branchId=:bid and c.exBondingId=:exBondingId and c.cfBondDtlId =:cfBondDtlId and c.status !='D' and c.yardLocation=:yardLocation "
			+ "and c.yardBlock=:yardBlock and c.blockCellNo=:blockCellNo and c.srNo=:srNo")
	CfExBondGrid toEditData(@Param("cid") String cid,@Param("bid") String bid,@Param("exBondingId") String exBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("yardLocation") String yardLocation,
			@Param ("yardBlock") String yardBlock,
			@Param ("blockCellNo") String blockCellNo,
			@Param ("srNo") Integer srNo);
	
	
	@Query("SELECT COALESCE(SUM(c.exBondPackages), 0) FROM CfExBondGrid c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.exBondingId = :exBondingId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal  getSumOfInBondPackages(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("exBondingId") String exBondingId,
		                            @Param("nocTransId") String nocTransId);
	

	@Query("SELECT c from CfExBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.exBondingId=:exBondingId and c.nocTransId=:nocTransId and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
	List<CfExBondGrid> getDataForExbondGrid(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("exBondingId") String exBondingId,
			@Param ("nocTransId") String nocTransId,
			@Param ("cfBondDtlId") String cfBondDtlId);
	
	
	
	
	@Query("SELECT c from CfExBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.exBondingId=:exBondingId and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
	List<CfExBondGrid> getDataForBondGatePassAfterExbond(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("exBondingId") String exBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId);
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE CfExBondGrid c SET c.exBondPackages = :exBondPackages, c.exCellAreaAllocated = :exCellAreaAllocated " +
	       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.exBondingId = :exBondingId " +
	       "AND c.cfBondDtlId = :cfBondDtlId AND c.yardLocation = :yardLocation AND c.yardBlock = :yardBlock AND c.blockCellNo = :blockCellNo")
	int updateCfexBondDtlAfterExBondGrid(
	    @Param("exBondPackages") BigDecimal exBondPackages,
	    @Param("exCellAreaAllocated") BigDecimal exCellAreaAllocated,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("exBondingId") String exBondingId,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("yardLocation") String yardLocation,
	    @Param("yardBlock") String yardBlock,
	    @Param("blockCellNo") String blockCellNo
	);
	
	@Modifying
	@Transactional
	@Query("UPDATE CfExBondGrid c SET c.exBondPackages = :exBondPackages, c.exCellAreaAllocated = :exCellAreaAllocated " +
	       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.exBondingId = :exBondingId " +
	       "AND c.cfBondDtlId = :cfBondDtlId AND c.yardLocation = :yardLocation AND c.yardBlock = :yardBlock AND c.blockCellNo = :blockCellNo and c.srNo =:srNo")
	int updateCfexBondDtlAfterExBondGrid(
	    @Param("exBondPackages") BigDecimal exBondPackages,
	    @Param("exCellAreaAllocated") BigDecimal exCellAreaAllocated,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("exBondingId") String exBondingId,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("yardLocation") String yardLocation,
	    @Param("yardBlock") String yardBlock,
	    @Param("blockCellNo") String blockCellNo,
	    @Param ("srNo") Integer srNo
	);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE CfExBondGrid c SET c.qtyTakenOut = :qtyTakenOut, c.areaReleased = :areaReleased " +
	       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.exBondingId = :exBondingId " +
	       "AND c.cfBondDtlId = :cfBondDtlId AND c.yardLocation = :yardLocation AND c.yardBlock = :yardBlock AND c.blockCellNo = :blockCellNo ")
	int updateExbondGridAfterGatePass(
	    @Param("qtyTakenOut") BigDecimal qtyTakenOut,
	    @Param("areaReleased") BigDecimal areaReleased,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("exBondingId") String exBondingId,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("yardLocation") String yardLocation,
	    @Param("yardBlock") String yardBlock,
	    @Param("blockCellNo") String blockCellNo
	);
	
	
	
	@Query("SELECT COALESCE(SUM(c.qtyTakenOut), 0) FROM CfExBondGrid c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.exBondingId = :exBondingId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal  getSumOfQtyTakenOut(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("exBondingId") String exBondingId,
		                            @Param("nocTransId") String nocTransId);
	
	
	@Query("SELECT COALESCE(SUM(c.qtyTakenOut), 0) FROM CfExBondGrid c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.exBondingId = :exBondingId " +
		       "AND c.cfBondDtlId = :cfBondDtlId ")
	BigDecimal  getSumOfQtyTakenOutCommodityWise(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("exBondingId") String exBondingId,
		                            @Param("cfBondDtlId") String cfBondDtlId);
}
