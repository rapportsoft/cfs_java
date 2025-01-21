package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Cfexbonddts;

public interface CfexbonddtsRepo extends JpaRepository<Cfexbonddts, String> {

	@Query(value="select COUNT(c) from Cfexbonddts c where c.companyId=:cid and c.branchId=:bid and c.nocTransId=:id and c.status='A'")
	int countOfRecordsByNocTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select c from Cfexbonddts c where c.companyId=:cid and c.branchId=:bid and c.nocTransId=:id and "
			+ "c.assessmentId=:assid and c.status='A'")
	Cfexbonddts getDataByNocTransIdAndAssessment(@Param("cid") String cid,@Param("bid") String bid,
			@Param("id") String id,@Param("assid") String assid);
	
	
	@Query(value="select COUNT(c) from Cfexbonddts c where c.companyId=:cid and c.branchId=:bid and c.nocTransId=:id and c.status='A' "
			+ "and c.inBondingId=:inbond and c.exBondingId=:exb")
	int countOfRecordsByNocTransIdAndExBondingId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("inbond") String inbond,@Param("exb") String exb);
}
