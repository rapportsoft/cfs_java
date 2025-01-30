package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.FinTrans;

public interface FintransRepo extends JpaRepository<FinTrans, String> {


//	@Query(value="select c from FinTrans c where c.companyId=:cid and c.branchId=:bid and c.oprInvoiceNo=:invNo and c.status = 'A'")
//	List<FinTrans> getDataByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo);
	
	
	@Query(value="select c from FinTrans c where c.companyId=:cid and c.branchId=:bid and c.oprInvoiceNo=:invNo and c.status = 'A' "
			+ "and c.profitcentreId=:id")
	List<FinTrans> getDataByInvoiceNo(@Param("cid") String cid,@Param("bid") String bid,@Param("invNo") String invNo,@Param("id") String id);
	
	
	@Query(value = "select t.partyId, t.transId, t.paymentMode, "
	        + "COALESCE(SUM(t.documentAmt), 0), "
	        + "SUM(CASE WHEN (COALESCE(t.documentAmt, 0) - COALESCE(t.clearedAmt, 0)) < 0 THEN 0 "
	        + "ELSE (COALESCE(t.documentAmt, 0) - COALESCE(t.clearedAmt, 0)) END) "
	        + "from FinTrans t "
	        + "where t.companyId = :cid and t.branchId = :bid and t.partyId = :id "
	        + "and t.docType = :type and t.status = 'A' "
	        + "group by t.partyId "
	        + "order by t.transId")
	Object advanceReceiptBeforeSaveSearch(@Param("cid") String cid, 
	                                      @Param("bid") String bid, 
	                                      @Param("id") String id, 
	                                      @Param("type") String type);
	
	@Query(value="select f.transId,f.docType,f.transDate,f.documentAmt,f.status,f.createdBy,f.partyId,p.partyName,f.accSrNo,"
			+ "a.gstNo,a.address1,a.address2,a.address3,f.narration,f.paymentMode,f.chequeNo,f.chequeDate,f.bankName,f.documentAmt "
			+ "from FinTrans f "
			+ "LEFT OUTER JOIN Party p ON f.companyId=p.companyId and f.branchId=p.branchId and f.partyId=p.partyId "
			+ "LEFT OUTER JOIN PartyAddress a ON f.companyId=a.companyId and f.branchId=a.branchId and f.partyId=a.partyId and "
			+ "f.accSrNo = CAST(a.srNo as INTEGER) "
			+ "where f.companyId=:cid and f.branchId=:bid and f.transId=:id and f.status='A'")
	List<Object[]> getDataByTransId(@Param("cid") String cid, 
            @Param("bid") String bid, 
            @Param("id") String id);
	
	@Query(value="select distinct f.docType,f.transId,DATE_FORMAT(f.transDate,'%d/%m/%Y %H:%i'),f.partyId,p.partyName,f.transDate "
			+ "from FinTrans f "
			+ "LEFT OUTER JOIN Party p ON f.companyId=p.companyId and f.branchId=p.branchId and f.partyId=p.partyId "
			+ "where f.companyId=:cid and f.branchId=:bid and f.status = 'A' and "
			+ "(:val is null OR :val = '' OR f.docType LIKE CONCAT(:val,'%') OR f.transId LIKE CONCAT(:val,'%') OR "
			+ "p.partyName LIKE CONCAT(:val,'%')) and f.profitcentreId='N00001' and f.docType != 'AJ' order by f.transDate desc ")
	List<Object[]> getAfterSaveData(@Param("cid") String cid, 
            @Param("bid") String bid, 
            @Param("val") String val);
	
	
	@Query(value = "select t "
	        + "from FinTrans t "
	        + "where t.companyId = :cid and t.branchId = :bid and t.partyId = :id "
	        + "and t.docType = :type and t.status = 'A' and (COALESCE(t.documentAmt, 0) - COALESCE(t.clearedAmt, 0)) > 0 "
	        + "order by t.createdDate asc")
	List<FinTrans> getAdvanceRecords(@Param("cid") String cid, 
	                                      @Param("bid") String bid, 
	                                      @Param("id") String id, 
	                                      @Param("type") String type);
}
