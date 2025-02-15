package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfinvsrvanx;

public interface CfinvsrvanxRepo extends JpaRepository<Cfinvsrvanx, String> {

	@Query(value="select a from Cfinvsrvanx a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.processTransId=:id")
	List<Cfinvsrvanx> getDataByProcessTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from Cfinvsrvanx a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' "
			+ "and a.processTransId=:id and a.serviceId=:sid")
	List<Cfinvsrvanx> getDataByProcessTransIdAndServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("sid") String sid);
}
