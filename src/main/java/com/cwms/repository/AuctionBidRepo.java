package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AuctionBid;

public interface AuctionBidRepo extends JpaRepository<AuctionBid, String> {

	@Query(value="select a from AuctionBid a where a.companyId=:cid and a.branchId=:bid and a.status='A' and a.bidId=:id")
	AuctionBid getDataById(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select NEW com.cwms.entities.AuctionBid(a.bidId, a.bidDate, p.profitcentreDesc, a.noticeId, a.noticeDate, a.shift,"
			+ "a.igmNo, a.igmDate, a.igmLineNo, a.commodityDescription, a.actualNoOfPackages,"
			+ "a.packageType, a.grossWt, a.uom, a.duty, a.cfsDues,a.reservePrice, a.createdBy, a.status, a.auctionItemNo) "
			+ "from AuctionBid a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and a.bidId=:id")
	AuctionBid getDataById1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a.bidId, DATE_FORMAT(a.bidDate,'%d/%M/%Y %H:%m'), a.noticeId, DATE_FORMAT(a.noticeDate,'%d/%M/%Y %H:%m'), p.profitcentreDesc "
			+ "from AuctionBid a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and (:id is null OR :id = '' OR a.bidId LIKE CONCAT(:id,'%') "
			+ "OR a.noticeId LIKE CONCAT(:id,'%') OR p.profitcentreDesc LIKE CONCAT(:id,'%'))")
	List<Object[]> getBidAfterSearchData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select a.bidId from AuctionBid a where a.companyId=:cid and a.branchId=:bid and a.status='A' and (:id is null OR :id = '' OR a.bidId LIKE "
			+ "CONCAT(:id,'%')) and a.bidId NOT IN(select r.bidId from AuctionRecording r where r.companyId=:cid and r.branchId=:bid and "
			+ "r.status != 'D') order by a.bidId")
	List<String> getDataForBeforeSaverecordingData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
