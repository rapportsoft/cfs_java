package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GeneralReceivingGrid;

public interface GeneralReceivingGridRepo extends JpaRepository<GeneralReceivingGrid, String> {

	
	
	@Query("SELECT MAX(c.srNo) FROM GeneralReceivingGrid c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.receivingId = :receivingId ")
	Integer getMaxSrNo(@Param("companyId") String companyId,
	                   @Param("branchId") String branchId,
	                   @Param("receivingId") String receivingId);
}
