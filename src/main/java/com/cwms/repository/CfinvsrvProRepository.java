package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.CfinvsrvPro;

public interface CfinvsrvProRepository extends JpaRepository<CfinvsrvPro, String> {
	@Query(value="select c from CfinvsrvPro c where c.companyId=:cid and c.branchId=:bid and c.invoiceNo=:invNo and c.status = 'A'")
	CfinvsrvPro getDataByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo);
}
