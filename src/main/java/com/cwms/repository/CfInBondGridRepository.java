package com.cwms.repository;

import java.util.List;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CfInBondGrid;

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

}