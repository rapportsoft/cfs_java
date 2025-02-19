package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfinvsrvanx;
import com.cwms.entities.Cfinvsrvanxap;

public interface Cfinvsrvanxaprepo extends JpaRepository<Cfinvsrvanxap, String> {

	@Query(value="select a from Cfinvsrvanxap a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' "
			+ "and a.processTransId=:id and a.serviceId=:sid")
	List<Cfinvsrvanxap> getDataByProcessTransIdAndServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("sid") String sid);
	
	@Query(value="select a from Cfinvsrvanxap a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.processTransId=:id")
	List<Cfinvsrvanxap> getDataByProcessTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select COUNT(a) from Cfinvsrvanxap a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.processTransId=:id")
	int getCountByAssessmentId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
