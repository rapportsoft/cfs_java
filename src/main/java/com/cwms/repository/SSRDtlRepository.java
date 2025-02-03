package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.SSRDtl;

import java.math.BigDecimal;
import java.util.*;

public interface SSRDtlRepository extends JpaRepository<SSRDtl, String> {
	
	@Query(value="select a.profitcentreId,a.sbTransId,a.sbNo,a.sbDate,b.exporterId,b.exporterName,b.cha,p1.partyName,b.onAccountOf,"
			+ "p2.partyName,a.commodity,c.shippingLine,p3.partyName,c.shippingAgent,p4.partyName,c.containerNo,c.containerSize,"
			+ "c.containerType,COALESCE(SUM(c.stuffedQty),0),COALESCE(SUM(c.cargoWeight),0),b.ssrTransId,c.stuffTallyId "
			+ "from ExportSbCargoEntry a "
			+ "LEFT OUTER JOIN ExportSbEntry b ON a.companyId=b.companyId and a.branchId=b.branchId and a.sbTransId=b.sbTransId and "
			+ "a.sbNo=b.sbNo "
			+ "LEFT OUTER JOIN Party p1 ON b.companyId=p1.companyId and b.branchId=p1.branchId and b.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON b.companyId=p2.companyId and b.branchId=p2.branchId and b.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN ExportStuffTally  c ON a.companyId=c.companyId  and a.branchId=c.branchId and a.sbTransId=c.sbTransId and "
			+ "a.sbNo=c.sbNo "
			+ "LEFT OUTER JOIN Party p3 ON c.companyId=p3.companyId and c.branchId=p3.branchId and c.shippingLine=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON c.companyId=p4.companyId and c.branchId=p4.branchId and c.shippingAgent=p4.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.sbNo=:sb and a.status='A' group by c.containerNo,c.stuffTallyId ")
	List<Object[]> getBeforeSaveDataFromSbNo1(@Param("cid") String cid,@Param("bid") String bid,@Param("sb") String sb);

	
	@Query(value="select a.profitcentreId,a.sbTransId,a.sbNo,a.sbDate,b.exporterId,b.exporterName,b.cha,p1.partyName,b.onAccountOf,"
			+ "p2.partyName,a.commodity,c.shippingLine,p3.partyName,c.shippingAgent,p4.partyName,c.containerNo,c.containerSize,"
			+ "c.containerType,COALESCE(SUM(c.stuffedQty),0),COALESCE(SUM(c.cargoWeight),0),b.ssrTransId,c.stuffTallyId "
			+ "from ExportSbCargoEntry a "
			+ "LEFT OUTER JOIN ExportSbEntry b ON a.companyId=b.companyId and a.branchId=b.branchId and a.sbTransId=b.sbTransId and "
			+ "a.sbNo=b.sbNo "
			+ "LEFT OUTER JOIN Party p1 ON b.companyId=p1.companyId and b.branchId=p1.branchId and b.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON b.companyId=p2.companyId and b.branchId=p2.branchId and b.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN ExportStuffTally  c ON a.companyId=c.companyId  and a.branchId=c.branchId and a.sbTransId=c.sbTransId and "
			+ "a.sbNo=c.sbNo "
			+ "LEFT OUTER JOIN Party p3 ON c.companyId=p3.companyId and c.branchId=p3.branchId and c.shippingLine=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON c.companyId=p4.companyId and c.branchId=p4.branchId and c.shippingAgent=p4.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.sbNo=:sb and a.status='A' "
			+ "and (b.ssrTransId is null OR b.ssrTransId = '' ) group by c.containerNo,c.stuffTallyId ")
	List<Object[]> getBeforeSaveDataFromSbNo(@Param("cid") String cid,@Param("bid") String bid,@Param("sb") String sb);
	
	
	@Query(value="select DISTINCT s.transId,s.erpDocRefNo,s.docRefNo,s.igmLineNo,s.blNo,s.beNo from SSRDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status != 'D' and s.profitcentreId=:profit  and "
			+ "(:val is null OR :val = '' OR s.transId LIKE CONCAT ('%',:val,'%') OR s.erpDocRefNo LIKE CONCAT ('%',:val,'%') "
			+ "OR s.docRefNo LIKE CONCAT ('%',:val,'%') OR s.igmLineNo LIKE CONCAT ('%',:val,'%') OR s.blNo LIKE CONCAT ('%',:val,'%') "
			+ "OR s.beNo LIKE CONCAT ('%',:val,'%')) order by s.transId desc")
	List<Object[]> searchSSR(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val, @Param("profit") String profit);
	

	@Query(value = "select distinct s.ssrRefNo from SSRDtl s where s.companyId=:cid and s.branchId=:bid and s.status != 'D' "
			+ "and s.transId=:id and s.serviceId=:sid")
	String getSSRReferenceNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("sid") String sid);

	@Query(value = "select COUNT(s)>0 from SSRDtl s where s.companyId=:cid and s.branchId=:bid and s.status != 'D' "
			+ "and s.transId=:id and s.serviceId=:sid and s.containerNo=:con")
	Boolean checkSSRReferenceNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("sid") String sid, @Param("con") String con);

	@Query(value = "select DISTINCT NEW com.cwms.entities.SSRDtl(s.serviceId, c.serviceShortDesc, s.ssrRefNo, s.serviceUnit, s.executionUnit,"
			+ "s.totalRate) from SSRDtl s LEFT OUTER JOIN Services c ON s.companyId=c.companyId and s.branchId=c.branchId and "
			+ "s.serviceId=c.serviceId where s.companyId=:cid and s.branchId=:bid and s.transId=:trans and s.status != 'D'")
	List<SSRDtl> getRefNoData(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans);
	
	@Query(value="select NEW com.cwms.entities.SSRDtl(s.companyId, s.branchId, s.transId, s.erpDocRefNo, s.docRefNo,"
			+ "s.transDate, s.docRefDate, s.igmLineNo, s.blNo, s.blDate, s.beNo, s.beDate,s.commodityDescription, "
			+ "p1.partyName, p2.partyName, p3.partyName, p4.partyName, s.ssrModeFor, s.status,s.createdBy,p5.partyName) "
			+ "from SSRDtl s LEFT OUTER JOIN Party p1 ON s.companyId=p1.companyId and s.branchId=p1.branchId and s.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON s.companyId=p2.companyId and s.branchId=p2.branchId and s.sa=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON s.companyId=p3.companyId and s.branchId=p3.branchId and s.cha=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON s.companyId=p4.companyId and s.branchId=p4.branchId and s.accId=p4.partyId "
			+ "LEFT OUTER JOIN Party p5 ON s.companyId=p5.companyId and s.branchId=p5.branchId and s.impId=p5.partyId "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status != 'D' and "
			+ "s.transId=:trans")
	List<SSRDtl> getSingleData(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans);
	
	@Query(value="select DISTINCT s.transId,s.erpDocRefNo,s.docRefNo,s.igmLineNo,s.blNo,s.beNo from SSRDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status != 'D' and "
			+ "(:val is null OR :val = '' OR s.transId LIKE CONCAT ('%',:val,'%') OR s.erpDocRefNo LIKE CONCAT ('%',:val,'%') "
			+ "OR s.docRefNo LIKE CONCAT ('%',:val,'%') OR s.igmLineNo LIKE CONCAT ('%',:val,'%') OR s.blNo LIKE CONCAT ('%',:val,'%') "
			+ "OR s.beNo LIKE CONCAT ('%',:val,'%'))")
	List<Object[]> searchSSR(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	
	@Query(value="select NEW com.cwms.entities.SSRDtl(s.containerNo, s.serviceId, s.serviceUnit, s.executionUnit,s.serviceUnit1, s.executionUnit1, s.rate,"
			+ "s.totalRate) "
			+ "from SSRDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.transId=:trans and s.containerNo=:con and s.status = 'A'")
	List<SSRDtl> getServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,
			@Param("con") String con);
	
	
	@Modifying
	@Transactional
	@Query(value="UPDATE SSRDtl s SET s.assessmentId=:id where s.companyId=:cid and s.branchId=:bid and s.transId=:trans and "
			+ "s.containerNo=:con and s.status = 'A' and (s.assessmentId is null OR s.assessmentId = '')")
	int updateAssessmentId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,
			@Param("con") String con,@Param("id") String id);
	
	
	@Query(value="select NEW com.cwms.entities.SSRDtl(s.containerNo, s.serviceId, s.serviceUnit, s.executionUnit,s.serviceUnit1, s.executionUnit1, s.rate,"
			+ "s.totalRate) "
			+ "from SSRDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.transId=:trans and s.status = 'A' and (s.assessmentId is null OR s.assessmentId = '')")
	List<SSRDtl> getServiceId1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans);
	
	@Modifying
	@Transactional
	@Query(value="UPDATE SSRDtl s SET s.assessmentId=:id where s.companyId=:cid and s.branchId=:bid and s.transId=:trans and "
			+ "s.status = 'A' and (s.assessmentId is null OR s.assessmentId = '')")
	int updateAssessmentId1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("id") String id);
}
