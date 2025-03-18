package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AuctionDetail;
import com.cwms.entities.AuctionServiceMap;

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
	
	
	@Query(value="SELECT a.noticeId, a.igmNo, a.igmTransId, a.igmLineNo, a.cvStatus, a.bidId, a.qtyTakenOut, r.auctionNo "
	        + "FROM AuctionDetail a "
	        + "LEFT OUTER JOIN Auction c ON a.companyId = c.companyId AND a.branchId = c.branchId AND a.noticeId = c.noticeId "
	        + "LEFT OUTER JOIN AuctionRecording r ON a.companyId = r.companyId AND a.branchId = r.branchId AND a.bidId = r.bidId "
	        + "WHERE a.companyId = :cid AND a.branchId = :bid AND a.status = 'A' AND a.noticeId = :id "
	        + "GROUP BY r.bidId")
	public List<Object[]> mainSearch(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	@Query(value="select g.gateOutId "
			+ "from GateOut g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status='A' and g.erpDocRefNo=:erp and g.docRefNo=:doc and "
			+ "g.igmLineNo=:line group by g.gateOutId order by g.gateOutId desc LIMIT 1")
	String findGateOutId(@Param("cid") String cid, @Param("bid") String bid, @Param("erp") String erp, @Param("doc") String doc,
			@Param("line") String line);
	
	@Query(value="select g.gatePassId "
			+ "from ImportGatePass g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status='A' and g.igmTransId=:erp and g.igmNo=:doc and "
			+ "g.igmLineNo=:line and g.transType='Auction' group by g.gatePassId order by g.gatePassId desc LIMIT 1")
	String findGatePassId(@Param("cid") String cid, @Param("bid") String bid, @Param("erp") String erp, @Param("doc") String doc,
			@Param("line") String line);
	
	// Auction Invoice

		@Query(value = "select a.igmTransId,a.igmNo,a.igmLineNo,d.blNo,d.noticeId from AuctionRecording a "
				+ "LEFT OUTER JOIN AuctionDetail d ON a.companyId=d.companyId and a.branchId=d.branchId and a.igmTransId=d.igmTransId and "
				+ "a.igmNo=d.igmNo and a.igmLineNo=d.igmLineNo and d.noticeType='F' "
				+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and (d.invoiceNo is null OR d.invoiceNo = '') and "
				+ "(:id is null OR :id = '' OR a.igmNo LIKE CONCAT(:id,'%') OR a.igmLineNo LIKE CONCAT(:id,'%') OR d.blNo LIKE CONCAT(:id,'%'))")
		List<Object[]> getBeforeAssessmentData(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);

		@Query(value = "select crg.igmTransId,crg.igmNo,crg.igmLineNo,crg.blNo,crg.blDate,p.profitcentreDesc,crg.boeNo,crg.viaNo,crg.igmDate,"
				+ "i.shippingAgent,p1.partyName,i.shippingLine,p2.partyName,crg.accessableValueAsValuation,crg.duty,crg.commodityDescription,"
				+ "crg.noticeId,crg.noticeDate,crg.noticeType,crg.importerName,crg.importerAddress1,crg.importerAddress2,crg.importerAddress3,"
				+ "c.containerNo,c.containerSize,c.containerType,c.gateInDate,c.profitcentreId,c.containerStatus,crg.hsnNo,crg.fileNo,crg.lotNo,"
				+ "crg.bidAmt,crg.tcs,crg.igst,crg.cgst,crg.sgst,crg.rateOfDuty "
				+ "from Auction c "
				+ "LEFT OUTER JOIN AuctionDetail crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.noticeId=crg.noticeId "
				+ "LEFT OUTER JOIN CFIgm i ON crg.companyId=i.companyId and crg.branchId=i.branchId and crg.igmNo=i.igmNo and crg.igmTransId=i.igmTransId "
				+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
				+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.shippingAgent=p1.partyId "
				+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingLine=p2.partyId "
				+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and crg.igmTransId=:trans and crg.igmNo=:igm and "
				+ "crg.igmLineNo=:lineNo and c.noticeId=:id and (crg.invoiceNo is null OR crg.invoiceNo = '')")
		List<Object[]> getBeforeSaveAssessmentData(@Param("cid") String cid, @Param("bid") String bid,@Param("trans") String trans,
				 @Param("igm") String igm, @Param("lineNo") String lineNo, @Param("id") String id);
		
		@Query(value="select a from AuctionServiceMap a where a.auctionType=:type")
		AuctionServiceMap getFromType(@Param("type") String type);

}
