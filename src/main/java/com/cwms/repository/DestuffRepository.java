package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.Destuff;

public interface DestuffRepository extends JpaRepository<Destuff, String> {

	@Query(value="select d from Destuff d where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
			+ "and d.igmLineNo=:line and d.deStuffId=:destuff and d.status != 'D'")
	Destuff getData(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm,
			@Param("line") String line,@Param("destuff") String destuff);
	
	
	
	@Query(value="select new com.cwms.entities.Destuff(d.deStuffId, d.deStuffDate, p.profitcentreDesc, d.igmTransId, d.igmNo,"
            + " d.igmLineNo, d.igmDate, d.transType, d.drt, d.viaNo, d.shippingAgent,"
            + " d.shippingLine, d.containerNo, d.containerType, d.containerSize,"
            + " d.containerStatus, d.haz, d.grossWeight, d.containerSealNo, d.customSealNo,"
            + " d.onAccountOf, d.gateInId, d.gateInDate, d.yardLocation, d.yardBlock,"
            + " d.blockCellNo, d.yardLocation1, d.yardBlock1, d.blockCellNo1,"
            + " d.areaOccupied, d.yardPackages, d.pod, d.gateInType, d.shift,"
            + " d.status, d.createdBy, p1.partyName, d.destuffType, d.destuffFromDate,"
            + " d.destuffToDate, d.workOrderNo) "
            + "from Destuff d LEFT OUTER JOIN Profitcentre p ON d.companyId=p.companyId and d.branchId=p.branchId and d.profitcentreId=p.profitcentreId "
            + "LEFT OUTER JOIN Party p1 ON d.companyId=p1.companyId and d.branchId=p1.branchId and d.onAccountOf=p1.partyId "
            + "where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
            + "and d.igmLineNo=:line and d.deStuffId=:destuff and d.status != 'D'")
Destuff getData1(@Param("cid") String cid, @Param("bid") String bid, @Param("igmTransId") String igmTransId, 
                 @Param("igm") String igm, @Param("line") String line, @Param("destuff") String destuff);
	
	@Query(value="select new com.cwms.entities.Destuff(d.deStuffId, d.deStuffDate, p.profitcentreDesc, d.igmTransId, d.igmNo,"
            + " d.igmLineNo, d.igmDate, d.transType, d.drt, d.viaNo, d.shippingAgent,"
            + " d.shippingLine, d.containerNo, d.containerType, d.containerSize,"
            + " d.containerStatus, d.haz, d.grossWeight, d.containerSealNo, d.customSealNo,"
            + " d.onAccountOf, d.gateInId, d.gateInDate, d.yardLocation, d.yardBlock,"
            + " d.blockCellNo, d.yardLocation1, d.yardBlock1, d.blockCellNo1,"
            + " d.areaOccupied, d.yardPackages, d.pod, d.gateInType, d.shift,"
            + " d.status, d.createdBy, p1.partyName, d.destuffType, d.destuffFromDate,"
            + " d.destuffToDate, d.workOrderNo) "
            + "from Destuff d LEFT OUTER JOIN Profitcentre p ON d.companyId=p.companyId and d.branchId=p.branchId and d.profitcentreId=p.profitcentreId "
            + "LEFT OUTER JOIN Party p1 ON d.companyId=p1.companyId and d.branchId=p1.branchId and d.onAccountOf=p1.partyId "
            + "where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
            + " and d.deStuffId=:destuff and d.status != 'D'")
Destuff getData2(@Param("cid") String cid, @Param("bid") String bid, @Param("igmTransId") String igmTransId, 
                 @Param("igm") String igm,  @Param("destuff") String destuff);
	
	
	@Query(value="select d.deStuffId, DATE_FORMAT(d.deStuffDate,'%d %b %Y %H:%i'), d.profitcentreId, d.igmTransId, d.igmNo,"
			+ "d.transType, d.containerNo, d.gateInId, DATE_FORMAT(d.gateInDate,'%d %b %Y %H:%i'), d.status, d.igmLineNo from Destuff d "
			+ "where d.companyId=:cid and d.branchId=:bid and d.status != 'D' "
			+ "and ((:search is null OR :search = '' OR d.deStuffId LIKE CONCAT (:search,'%')) OR "
			+ "(:search is null OR :search = '' OR d.igmNo LIKE CONCAT (:search,'%')) OR "
			+ "(:search is null OR :search = '' OR d.containerNo LIKE CONCAT (:search,'%'))) ORDER BY d.deStuffId desc")
	List<Object[]> searchData(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);
	
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE Destuff d "
	        + "SET d.mtyStatus = 'Y', d.mtyDate = CURRENT_DATE "
	        + "WHERE d.companyId = :cid "
	        + "AND d.branchId = :bid "
	        + "AND d.deStuffId = :destuff "
	        + "AND d.status != 'D' "
	        + "AND d.mtyStatus != 'Y'")
	int updateMtyStatus(@Param("cid") String cid, @Param("bid") String bid, @Param("destuff") String destuff);
	
	
	
	@Query(value = "SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END "
	        + "FROM DestuffCrg d "
	        + "WHERE d.companyId = :cid "
	        + "AND d.branchId = :bid "
	        + "AND d.status != 'D' "
	        + "AND d.deStuffId = :destuff "
	        + "AND (COALESCE(d.actualNoOfPackages, 0) + COALESCE(d.damagedPackages, 0) + "
	        + "COALESCE(d.gainLossPackages, 0) + COALESCE(d.excessPackages, 0) + "
	        + "COALESCE(d.shortagePackages, 0)) >= COALESCE(d.noOfPackages, 0)")
	Boolean checkMtyStatus(@Param("cid") String cid, @Param("bid") String bid, @Param("destuff") String destuff);






}
