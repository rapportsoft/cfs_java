package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.FinTransInvAP;

public interface FinTransInvAPRepo extends JpaRepository<FinTransInvAP, String> {

	 @Query(value="select f.oprInvoiceNo,f.partyId,p1.partyName,f.billingParty,f.narration,f.importerId,p2.partyName,f.documentAmt,"
	    		+ "f.invoiceBalAmt,f.receiptAmt "
	    		+ "from FinTransInvAP f "
	    		+ "LEFT OUTER JOIN Party p1 ON f.companyId=p1.companyId and f.branchId=p1.branchId and f.partyId=p1.partyId "
	    		+ "LEFT OUTER JOIN Party p2 ON f.companyId=p2.companyId and f.branchId=p2.branchId and f.importerId=p2.partyId "
	    		+ "where f.companyId=:cid and f.branchId=:bid and f.status='A' and f.transId=:id order by f.transId")	
		List<Object[]> getAfterSaveDataByTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
