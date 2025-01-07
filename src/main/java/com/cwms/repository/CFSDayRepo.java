package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFSDay;

public interface CFSDayRepo extends JpaRepository<CFSDay, String> {

	@Query(value="select c from CFSDay c where c.companyId=:cid and c.branchId=:bid")
	public CFSDay getData(@Param("cid") String cid,@Param("bid") String bid);
}
