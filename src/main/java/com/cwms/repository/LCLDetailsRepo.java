package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.LCLDetails;

public interface LCLDetailsRepo extends JpaRepository<LCLDetails, String> {

	
	@Query(value = "select c.lclTransId,c.igmNo,c.igmLineNo,c.createdDate " + "from LCLDetails c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and (:transId is null OR :transId = '' OR "
			+ "c.lclTransId LIKE CONCAT ('%',:transId,'%') OR c.igmNo LIKE CONCAT ('%',:transId,'%') OR c.igmLineNo LIKE CONCAT ('%',:transId,'%')) "
			+ "group by c.lclTransId order by c.createdDate desc")
	List<Object[]> getData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId);
	
	@Query(value = "select c.lclTransId,c.igmNo,c.igmLineNo,c.subLineNo,DATE_FORMAT(c.validityDate,'%d/%m/%Y %H:%i'),"
			+ "p.partyName,c.serviceDesc,c.serviceAmt "
			+ "from LCLDetails c "
			+ "LEFT OUTER JOIN Party p ON c.companyId=p.companyId and c.branchId=p.branchId and c.partyId=p.partyId "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.lclTransId=:transId")
	List<Object[]> getDataByTransId(@Param("cid") String cid, @Param("bid") String bid,
			@Param("transId") String transId);
}
