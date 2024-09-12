package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Yard;

public interface YardRepository extends JpaRepository<Yard, String> {

	@Query(value="select y from Yard y where y.companyId=:cid and y.yardId=:yard and y.status != 'D'")
	public Yard getDataByYardId(@Param("cid") String cid,@Param("yard") String yard);
}
