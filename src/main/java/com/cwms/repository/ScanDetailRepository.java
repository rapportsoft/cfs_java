package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ScanDetail;

public interface ScanDetailRepository extends JpaRepository<ScanDetail, String> {

	@Query(value="select s from ScanDetail s where s.companyId=:cid and s.branchId=:bid and s.transId=:id and s.status != 'D'")
	List<ScanDetail> getDataByTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select distinct s.transId, s.refNo, s.fileName from ScanDetail s where s.companyId=:cid and s.branchId=:bid and "
			+ "s.status != 'D' and (:id is null OR :id = '' OR s.transId LIKE CONCAT ('%',:id,'%') OR s.refNo LIKE CONCAT ('%',:id,'%')) order by s.transId desc")
	List<Object[]> searchData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
