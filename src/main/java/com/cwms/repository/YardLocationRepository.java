package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.YardLocation;

public interface YardLocationRepository extends JpaRepository<YardLocation, String> {

	@Query(value="select New com.cwms.entities.YardLocation(y.finYear, y.yardId, y.yardLocationId, y.yardLocationDesc,"
			+ "y.locationCategory, y.status) from YardLocation y where y.companyId=:cid and y.yardId=:yard and y.finYear=:fin and "
			+ "y.status !='D'")
	public List<YardLocation> getDataByYardId(@Param("cid") String cid,@Param("yard") String yard,@Param("fin") String finyear);
	
	@Query(value="select COUNT(y)>0 from YardLocation y where y.companyId=:cid and y.yardId=:bid and y.finYear=:fin "
			+ "and y.yardId=:yardid and y.yardLocationId=:ylocationId and y.status != 'D'")
	Boolean isExistYardLocation(@Param("cid") String cid,@Param("bid") String bid,@Param("fin") String fin,@Param("yardid") String yardid,
			@Param("ylocationId") String ylocationId);
}
