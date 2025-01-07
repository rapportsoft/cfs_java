package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.FinTrans;

public interface FintransRepo extends JpaRepository<FinTrans, String> {


	@Query(value="select c from FinTrans c where c.companyId=:cid and c.branchId=:bid and c.oprInvoiceNo=:invNo and c.status = 'A'")
	List<FinTrans> getDataByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo);
}
