package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.NotGateInHoldDtl;

public interface NotGateInHoldDtlRepo extends JpaRepository<NotGateInHoldDtl, String> {

	@Query(value="select COUNT(c)>0 from NotGateInHoldDtl c where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and "
			+ "c.containerNo=:con")
	boolean checkContainerNoAlreadyExistOrNot(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query(value="select COUNT(c) from NotGateInHoldDtl c where c.companyId=:cid and c.branchId=:bid and c.containerNo=:con")
	int getSrNoByContainerWise(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query(value="select c.containerNo,c.holdStatus,c.csd,c.csdHoldRemarks,c.csdHoldUserName,c.createdBy,c.createdDate "
			+ "from NotGateInHoldDtl c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' order by c.createdDate desc")
	List<Object[]> getAdvanceHoldData(@Param("cid") String cid,@Param("bid") String bid);
}
