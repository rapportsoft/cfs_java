package com.cwms.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.PdaDtl;

public interface PdaDtlRepo extends JpaRepository<PdaDtl, String> {

	@Query(value="select COALESCE(MAX(p.srNo),0) "
			+ "from PdaDtl p "
			+ "where p.companyId=:cid and p.branchId=:bid and p.status='A' and p.partyId=:id")
	BigDecimal maxId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
