package com.cwms.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.Cfinvsrv;
import com.cwms.entities.Cfinvsrvap;

public interface Cfinvsrvaprepo extends JpaRepository<Cfinvsrvap, String> {

	@Query(value="select c from Cfinvsrvap c where c.companyId=:cid and c.branchId=:bid and c.invoiceNo=:invNo and c.status = 'A'")
	Cfinvsrvap getDataByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo);
	
	@Query(value="select COALESCE(c.receiptAmt,0) from Cfinvsrvap c where c.companyId=:cid and c.branchId=:bid and c.invoiceNo=:invNo and c.status = 'A'")
	BigDecimal getReceiptAmtByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE Cfinvsrvap c SET c.receiptTransId=:id, c.receiptAmt=:amt where c.companyId=:cid and c.branchId=:bid and "
			+ "c.invoiceNo=:inv and c.status='A'")
	int updateReceiptAmount(@Param("cid") String cid,@Param("bid") String bid,@Param("inv") String invNo,@Param("id") String id,
			@Param("amt") BigDecimal amt);
}
