package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.InvCreditTrack;

public interface InvCreditTrackRepo extends JpaRepository<InvCreditTrack, String> {

	
	@Query(value="select i.invoiceNo,i.partyId,p1.partyName,c.accSrNo,c.creditType,c.billingParty,c.comments,c.importerId,c.impSrNo,"
			+ "IFNULL(p2.partyName,''),ROUND(i.invoiceAmount,2),ROUND((i.creditAmount-i.creditAdjustAmount),2),c.profitcentreId,"
			+ "i.assesmentId,i.transId,ROUND(i.creditAmount,2),ROUND(i.creditAdjustAmount,2),"
			+ "DATE_FORMAT(i.transDate, '%Y-%m-%d %T'), i.dbType,i.tdsStatus,i.tdsAmt,i.tdsDeductee,p3.tdsPercentage,i.localAmount "
			+ "from InvCreditTrack i "
			+ "Left Outer Join Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.partyId=p1.partyId "
			+ "Left Outer Join Cfinvsrv c ON i.companyId=c.companyId and i.branchId=c.branchId and i.invoiceNo=c.invoiceNo and "
			+ "i.partyId=c.partyId "
			+ "Left Outer Join Party p2 ON c.companyId=p2.companyId and c.branchId=p2.branchId and c.importerId=p2.partyId "
			+ "Left Outer Join Party p3 ON i.companyId=p3.companyId and i.branchId=p3.branchId and i.tdsDeductee=p3.partyId "
			+ "where i.companyId=:cid and i.branchId=:bid and i.status='A' and c.status='A' and i.partyId=:id and "
			+ "(i.creditAmount-i.creditAdjustAmount) > 0 order by i.transDate asc")
	List<Object[]> getBeforeSearchDataForeceipt(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select COALESCE(c.creditAdjustAmount,0) from InvCreditTrack c where c.companyId=:cid and c.branchId=:bid and c.invoiceNo=:invNo and c.status = 'A'")
	BigDecimal getCreditAdjAmtByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE InvCreditTrack c " +
	               "SET c.creditAdjustAmount = :amt, " +
	               "c.tdsStatus = CASE WHEN c.tdsStatus != 'Y' THEN :status ELSE c.tdsStatus END " +
	               "WHERE c.companyId = :cid AND c.branchId = :bid AND c.invoiceNo = :inv AND c.status = 'A'")
	int updateCreditAdjAmt(@Param("cid") String cid,@Param("bid") String bid,@Param("inv") String invNo,@Param("amt") BigDecimal amt,
			@Param("status") String status);
	
}
