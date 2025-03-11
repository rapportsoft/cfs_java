package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfInBondGrid;
import com.cwms.entities.CfinbondcrgDtl;
import com.cwms.entities.GeneralReceivingGateInDtl;
import com.cwms.entities.GeneralReceivingGrid;

public interface GeneralReceivingGateInDtlRepo extends JpaRepository<GeneralReceivingGateInDtl, String>{

	
	
	@Query("SELECT c FROM GeneralReceivingGateInDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.receivingId = :inBondingId " +
		       "AND c.status = 'A'")
		List<GeneralReceivingGateInDtl> getAfterSave( 
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("inBondingId") String inBondingId);
	
	
	
	@Query("SELECT c FROM GeneralReceivingGateInDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.receivingId = :inBondingId " +
		       "AND c.gateInId = :gateInId " +
		       "AND c.jobDtlTransId = :jobDtlTransId " +
		       "AND c.status = 'A'")
		GeneralReceivingGateInDtl getAfterSaveForEdit( 
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("inBondingId") String inBondingId,
		    @Param("gateInId") String gateInId,
		    @Param("jobDtlTransId") String jobDtlTransId);
	
	
	
	
	@Query("SELECT c FROM GeneralReceivingGrid c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.receivingId = :receivingId " +
		       "AND c.yardLocation = :yardLocation " +  // Fixed parameter mapping
		       "AND c.yardBlock = :yardBlock " +
		       "AND c.blockCellNo = :blockCellNo " +
		       "AND c.status = 'A'")
	GeneralReceivingGrid getSavedGeneralReceivingGrid( 
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("receivingId") String receivingId,
		    @Param("yardLocation") String yardLocation,  // Corrected
		    @Param("yardBlock") String yardBlock,
		    @Param("blockCellNo") String blockCellNo);


	@Query("SELECT c from GeneralReceivingGrid c where c.companyId =:companyId and c.branchId=:branchId and c.receivingId=:receivingId and c.status !='D'")
	List<GeneralReceivingGrid> getDataAfterSave(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("receivingId") String receivingId);
	
	
	
	
	@Query("SELECT c FROM GeneralReceivingGateInDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.receivingId = :inBondingId " +
		       "AND c.jobTransId = :jobTransId " +
		       "AND c.commodityId = :jobDtlTransId " +
		       "AND c.status = 'A'")
		GeneralReceivingGateInDtl updateReceivingGateInDetailSfaterDelivery( 
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("inBondingId") String inBondingId,
		    @Param("jobTransId") String jobTransId,
		    @Param("jobDtlTransId") String jobDtlTransId);
}
