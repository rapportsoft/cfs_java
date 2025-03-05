package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFSBLDetails;

public interface CFSBLDetailsrepo extends JpaRepository<CFSBLDetails, String> {

	@Query(value = "select c.blTransId,c.igmNo,c.igmLineNo,c.containerNo,c.createdDate " + "from CFSBLDetails c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and (:transId is null OR :transId = '' OR "
			+ "c.blTransId LIKE CONCAT ('%',:transId,'%') OR c.igmNo LIKE CONCAT ('%',:transId,'%') OR c.igmLineNo LIKE CONCAT ('%',:transId,'%') "
			+ "OR c.containerNo LIKE CONCAT ('%',:transId,'%')) group by c.blTransId order by c.createdDate desc")
	List<Object[]> getData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId);

	@Query(value = "select c.blTransId,c.igmNo,c.igmLineNo,c.containerNo,c.containerSize,c.deliveryMode,c.specialDelivery,"
			+ "p.partyName,c.serviceDesc,c.serviceAmt "
			+ "from CFSBLDetails c "
			+ "LEFT OUTER JOIN Party p ON c.companyId=p.companyId and c.branchId=p.branchId and c.partyId=p.partyId "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.blTransId=:transId")
	List<Object[]> getDataByTransId(@Param("cid") String cid, @Param("bid") String bid,
			@Param("transId") String transId);
}
