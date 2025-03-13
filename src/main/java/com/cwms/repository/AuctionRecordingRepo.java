package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AuctionRecording;

public interface AuctionRecordingRepo extends JpaRepository<AuctionRecording, String> {

	@Query(value="select COUNT(a) from AuctionRecording a where a.companyId=:cid and a.branchId=:bid and a.auctionNo=:id")
	int getSrNo(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from AuctionRecording a where a.companyId=:cid and a.branchId=:bid and a.auctionNo=:id "
			+ "and a.status='A' and a.srNo=:sr")
	AuctionRecording getDataByAuctionIdAndSrNo(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("sr") int sr);
	
	
	@Query(value="select NEW com.cwms.entities.AuctionRecording(a.bidId, a.auctionNo, a.srNo, a.auctionItemNo, a.auctionDate,"
			+ "a.bidDate, p.profitcentreDesc, a.igmNo, a.igmDate, a.igmLineNo, a.shift,"
			+ "a.commodityDescription, a.actualNoOfPackages, a.grossWt, a.uom, a.duty,"
			+ "a.cfsDues, a.reservePrice, a.bidderName, a.bidderAddress1,"
			+ "a.bidderAddress2, a.bidderAddress3, a.stc, a.bidderAmount,"
			+ "a.amountPaid, a.confirmDate, a.createdBy, a.status) "
			+ "from AuctionRecording a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.auctionNo=:id "
			+ "and a.status='A'")
	List<AuctionRecording> getDataByAuctionId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a.auctionNo,DATE_FORMAT(a.auctionDate,'%d/%M/%Y %H:%i'),a.bidId,DATE_FORMAT(a.bidDate,'%d/%M/%Y %H:%i'),"
			+ "a.auctionItemNo,p.profitcentreDesc,a.igmNo,DATE_FORMAT(a.igmDate,'%d/%M/%Y'),a.igmLineNo "
			+ "from AuctionRecording a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:id is null OR :id = '' OR "
			+ "a.auctionNo LIKE CONCAT(:id,'%') OR a.bidId LIKE CONCAT(:id,'%') OR a.auctionItemNo LIKE CONCAT(:id,'%') OR "
			+ "p.profitcentreDesc LIKE CONCAT(:id,'%') OR a.igmNo LIKE CONCAT(:id,'%') OR a.igmLineNo LIKE CONCAT(:id,'%')) "
			+ "group by a.auctionNo order by a.auctionDate desc")
	List<Object[]> searchAuctionRecordingData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select a.igmNo,a.igmLineNo,a.igmTransId "
			+ "from AuctionRecording a "
			+ "LEFT OUTER JOIN AuctionDetail d ON a.companyId=d.companyId and a.branchId=d.branchId and a.igmNo=d.igmNo and "
			+ "a.igmLineNo=d.igmLineNo and a.igmTransId=d.igmTransId and d.noticeType='F' "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and (:id is null OR :id = '' OR a.igmNo LIKE CONCAT(:id,'%') "
			+ "OR a.igmLineNo LIKE CONCAT(:id,'%')) and (COALESCE(d.noOfPackages,0) - COALESCE(d.qtyTakenOut,0)) > 0 and "
			+ "(d.invoiceNo is not null and d.invoiceNo != '') group by a.igmNo,a.igmLineNo")
	List<Object[]> searchBeforeSaveGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select p.profitcentreDesc,d.invoiceNo,d.invoiceDate,a.igmNo,a.igmTransId,a.igmLineNo,a.commodityDescription,d.noOfPackages,"
			+ "(COALESCE(d.noOfPackages,0) - COALESCE(d.qtyTakenOut,0)) "
			+ "from AuctionRecording a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "LEFT OUTER JOIN AuctionDetail d ON a.companyId=d.companyId and a.branchId=d.branchId and a.igmNo=d.igmNo and "
			+ "a.igmLineNo=d.igmLineNo and a.igmTransId=d.igmTransId and d.noticeType='F' "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and a.igmTransId=:transId and a.igmNo=:igm and "
			+ "a.igmLineNo=:lineNo group by a.igmNo,a.igmTransId,a.igmLineNo")
	Object getSelectedDataBeforSaveGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("transId") String transId,
			@Param("igm") String igm,@Param("lineNo") String lineNo);
}
