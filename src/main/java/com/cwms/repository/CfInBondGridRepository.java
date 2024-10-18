package com.cwms.repository;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CfInBondGrid;

import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;

@Repository
public interface CfInBondGridRepository extends JpaRepository<CfInBondGrid, String>{

	@Query("SELECT c from CfInBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.inBondingId=:inBondingId and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
	List<CfInBondGrid> getDataAfterSave(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("inBondingId") String inBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId);
	
	@Query("SELECT MAX(c.srNo) FROM CfInBondGrid c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.cfBondDtlId = :cfBondDtlId")
	Integer getMaxSrNo(@Param("companyId") String companyId,
	                   @Param("branchId") String branchId,
	                   @Param("inBondingId") String inBondingId,
	                   @Param("cfBondDtlId") String cfBondDtlId);
	
	
	@Query(value="SELECT CASE WHEN COUNT(c) > 0  THEN true ELSE false END  from CfInBondGrid c where c.companyId=:cid and c.branchId=:bid and c.inBondingId=:inBondingId and c.cfBondDtlId =:cfBondDtlId and c.status !='D' and c.yardLocation=:yardLocation "
			+ "and c.yardBlock=:yardBlock and c.blockCellNo=:blockCellNo and c.srNo <>:srNo")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("inBondingId") String inBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("yardLocation") String yardLocation,
			@Param ("yardBlock") String yardBlock,
			@Param ("blockCellNo") String blockCellNo,
			@Param ("srNo") Integer srNo);
	

	@Query("SELECT c from CfInBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.inBondingId=:inBondingId and c.cfBondDtlId=:cfBondDtlId and c.srNo=:srNo")
	CfInBondGrid getExistingData(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("inBondingId") String inBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("srNo") Integer srNo);
	
	@Query(value="SELECT c from CfInBondGrid c where c.companyId=:cid and c.branchId=:bid and c.inBondingId=:inBondingId and c.cfBondDtlId =:cfBondDtlId and c.status !='D' and c.yardLocation=:yardLocation "
			+ "and c.yardBlock=:yardBlock and c.blockCellNo=:blockCellNo")
	CfInBondGrid toEditData(@Param("cid") String cid,@Param("bid") String bid,@Param("inBondingId") String inBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("yardLocation") String yardLocation,
			@Param ("yardBlock") String yardBlock,
			@Param ("blockCellNo") String blockCellNo);
	
	
	@Query("SELECT COALESCE(SUM(c.inBondPackages), 0) FROM CfInBondGrid c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal  getSumOfInBondPackages(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("inBondingId") String inBondingId,
		                            @Param("nocTransId") String nocTransId);
	
	
	
	
	
	
	
	@Query("SELECT c from CfInBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.inBondingId=:inBondingId and c.nocTransId=:nocTransId and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
	List<CfInBondGrid> getDataForExbondGrid(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("inBondingId") String inBondingId,
			@Param ("nocTransId") String nocTransId,
			@Param ("cfBondDtlId") String cfBondDtlId);
	
		
//	@Query("SELECT new com.cwms.entities.CfInBondGrid(c.companyId, c.branchId, c.finYear, c.nocTransId, c.inBondingId, c.srNo, " +
//		       "c.gateInId, c.cfBondDtlId, c.yardLocation, c.yardBlock, c.blockCellNo, c.cellArea, c.cellAreaUsed, " +
//		       "c.cellAreaAllocated, c.inBondPackages, cf.exCellAreaAllocated, cf.exBondPackages) " +
//		       "FROM CfInBondGrid c " +
//		       "LEFT OUTER JOIN CfExBondGrid cf ON c.companyId =cf.companyId and c.branchId =cf.branchId and c.inBondingId=cf.inBondingId and c.nocTransId=cf.nocTransId and c.cfBondDtlId=cf.cfBondDtlId "+
//		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.nocTransId = :nocTransId " +
//		       "AND c.cfBondDtlId = :cfBondDtlId AND c.status != 'D'")
//		List<CfInBondGrid> getDataForExbondGrid(
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,
//		    @Param("inBondingId") String inBondingId,
//		    @Param("nocTransId") String nocTransId,
//		    @Param("cfBondDtlId") String cfBondDtlId
//		);

	
	@Modifying
	@Transactional
	@Query("UPDATE CfInBondGrid c SET c.qtyTakenOut = :qtyTakenOut, c.areaReleased = :areaReleased " +
	       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId " +
	       "AND c.cfBondDtlId = :cfBondDtlId AND c.yardLocation = :yardLocation AND c.yardBlock = :yardBlock AND c.blockCellNo = :blockCellNo ")
	int updateInbondGridAfterGatePass(
	    @Param("qtyTakenOut") BigDecimal qtyTakenOut,
	    @Param("areaReleased") BigDecimal areaReleased,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("inBondingId") String inBondingId,
	    @Param("cfBondDtlId") String cfBondDtlId,
	    @Param("yardLocation") String yardLocation,
	    @Param("yardBlock") String yardBlock,
	    @Param("blockCellNo") String blockCellNo
	);
	
	
	
	
	@Query(value="SELECT c from CfInBondGrid c where c.companyId=:cid and c.branchId=:bid and c.inBondingId=:inBondingId and c.cfBondDtlId =:cfBondDtlId and c.status !='D' and c.yardLocation=:yardLocation "
			+ "and c.yardBlock=:yardBlock and c.blockCellNo=:blockCellNo and c.srNo =:srNo")
	CfInBondGrid toEditDataAFTERGATEPASS(@Param("cid") String cid,@Param("bid") String bid,@Param("inBondingId") String inBondingId,
			@Param ("cfBondDtlId") String cfBondDtlId,
			@Param ("yardLocation") String yardLocation,
			@Param ("yardBlock") String yardBlock,
			@Param ("blockCellNo") String blockCellNo,
			@Param ("srNo") Integer srNo);
	
}