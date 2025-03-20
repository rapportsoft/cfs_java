package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AssigneeMaster;

public interface AssigneeMasterRepo extends JpaRepository<AssigneeMaster, String> {

	@Query(value="select a.email from AssigneeMaster a where a.companyId=:cid and a.branchId=:bid and a.assigneeId=:id")
	String getEmailById(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
