package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AuctionDetail;

public interface AuctionCrgRepo extends JpaRepository<AuctionDetail, String>{

	@Query(value="select a.noticeId, DATE_FORMAT(a.noticeDate,'%d/%M/%y %H:%m'), a.igmNo, DATE_FORMAT(a.igmDate,'%d/%M/%y'),a.igmTransId,"
			+ "a.igmLineNo,a.vessel,p.profitcentreDesc,a.importerName,a.blNo "
			+ "from AuctionDetail a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.noticeType='F' and "
			+ "(:id is null OR :id = '' OR a.noticeId LIKE CONCAT(:id,'%') OR a.igmNo LIKE CONCAT(:id,'%') OR "
			+ "a.igmTransId LIKE CONCAT(:id,'%') OR a.igmLineNo LIKE CONCAT(:id,'%') OR a.vessel LIKE CONCAT(:id,'%') "
			+ "OR p.profitcentreDesc LIKE CONCAT(:id,'%') OR a.importerName LIKE CONCAT(:id,'%') "
			+ "OR a.blNo LIKE CONCAT(:id,'%')) "
			+ "order by a.createdDate desc")
	public List<Object[]> getFinalNoticeData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a.noticeId,a.noticeDate,a.igmNo,a.igmDate,a.cvStatus,a.cvCreatedBy,a.igmTransId,a.igmLineNo,a.blNo,a.blDate,"
			+ "a.viaNo,a.vessel,a.importerName,a.importerAddress1,a.importerAddress2,a.importerAddress3,a.noOfPackages,"
			+ "a.typeOfPackage,a.notifyParty,a.notifyPartyAddress1,a.notifyPartyAddress2,a.notifyPartyAddress3,a.grossWt,a.uom,"
			+ "p.profitcentreDesc,a.accessableValueAsValuation,a.mop,a.rateOfDuty,a.amtOfDuty,a.assessiableAvailable,a.duty,"
			+ "a.fairValueOfGoods,a.pmv,a.shift,a.fileNo,a.lotNo,a.auctionStatus,a.hsnNo,a.fileStatus,c.containerNo,c.containerSize,"
			+ "c.containerType,c.gateInDate,c.noOfPackages,c.grossWt,a.tcs,a.igst,a.cgst,a.sgst,a.auctionType,a.bidAmt,"
			+ "a.stcStatus,a.acceptRejectStatus,a.customeOutOfChargeDate "
			+ "from AuctionDetail a "
			+ "LEFT OUTER JOIN Auction c ON a.companyId=c.companyId and a.branchId=c.branchId and a.noticeId=c.noticeId and c.status='A' "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.noticeId=:id and a.status = 'A' ")
	public List<Object[]> getDataByFinalNoticeId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from AuctionDetail a where a.companyId=:cid and a.branchId=:bid and a.noticeId=:id and a.status='A'")
	public AuctionDetail getDataById(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select a.noticeId "
			+ "from AuctionDetail a "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.cvStatus='A' and (:id is null OR :id = '' OR "
			+ "a.noticeId LIKE CONCAT(:id,'%')) and (a.bidId is null OR a.bidId = '')")
	public List<String> getBeforeSaveDataForAuctionBid(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select p.profitcentreDesc,a.noticeId,a.noticeDate,a.igmNo,a.igmDate,a.igmLineNo,a.commodityDescription,a.noOfPackages,"
			+ "a.grossWt,a.uom,a.duty "
			+ "from AuctionDetail a "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.cvStatus='A' and a.noticeId =:id")
	public Object getSelectedBeforeSaveDataForAuctionBid(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from AuctionDetail a where a.companyId=:cid and a.branchId=:bid and a.igmTransId=:trans and a.igmNo=:igm "
			+ "and a.igmLineNo=:line and a.noticeType = 'F' and a.status='A'")
	public AuctionDetail getDataByIgmDtls(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,
			@Param("igm") String igm,@Param("line") String line);
}
