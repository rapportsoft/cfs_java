package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AuctionDuty;

public interface AuctionDutyRepo extends JpaRepository<AuctionDuty, String> {

	@Query(value="select a.noticeId,a.dutyType,a.dutyRate,a.dutyValue "
			+ "from AuctionDuty a "
			+ "where a.companyId=:cid and a.branchId=:bid and a.noticeId=:id and a.status = 'A'")
	public List<Object[]> getDataByNoticeId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
    @Query(value="select a from AuctionDuty a where a.companyId=:cid and a.branchId=:bid and a.status='A' and "
    		+ "a.noticeId=:id and a.dutyType=:type")
    public AuctionDuty getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("type") String type);
	
	
}
