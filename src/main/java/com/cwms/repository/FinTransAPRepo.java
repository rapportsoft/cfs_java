package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.FinTransAP;

public interface FinTransAPRepo extends JpaRepository<FinTransAP, String> {

	@Query(value="select f.transId,f.docType,f.transDate,f.documentAmt,f.status,f.createdBy,f.partyId,p.partyName,f.accSrNo,"
			+ "a.gstNo,a.address1,a.address2,a.address3,f.narration,f.paymentMode,f.chequeNo,f.chequeDate,f.bankName,f.documentAmt,"
			+ "COALESCE(p.accountName,''),COALESCE(p.bankName,''),COALESCE(p.bankAddress,''),COALESCE(p.ifscCode,''),COALESCE(p.accountNo,'') "
			+ "from FinTransAP f "
			+ "LEFT OUTER JOIN Party p ON f.companyId=p.companyId and f.branchId=p.branchId and f.partyId=p.partyId "
			+ "LEFT OUTER JOIN PartyAddress a ON f.companyId=a.companyId and f.branchId=a.branchId and f.partyId=a.partyId and "
			+ "f.accSrNo = CAST(a.srNo as INTEGER) "
			+ "where f.companyId=:cid and f.branchId=:bid and f.transId=:id and f.status='A'")
	List<Object[]> getDataByTransId(@Param("cid") String cid, 
            @Param("bid") String bid, 
            @Param("id") String id);
}
