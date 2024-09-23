package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Impexpgrid;

public interface Impexpgridrepo extends JpaRepository<Impexpgrid, String> {

	@Query(value="select i from Impexpgrid i where i.companyId=:cid and i.branchId=:bid and i.processTransId=:id and i.status != 'D' and "
			+ "i.transType='IMP'")
	Impexpgrid getDataById(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select i from Impexpgrid i where i.companyId=:cid and i.branchId=:bid and i.processTransId=:id and i.status != 'D' and "
			+ "i.transType='IMP' and i.lineNo=:line")
	Impexpgrid getDataByIdAndLineId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("line") String line);
}
