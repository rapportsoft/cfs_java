package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.beust.jcommander.Parameter;
import com.cwms.entities.ExportStuffTally;

import java.math.BigDecimal;
import java.util.*;

public interface ExportStuffTallyRepo extends JpaRepository<ExportStuffTally, String> {

	@Transactional
	@Modifying
	@Query("UPDATE ExportStuffTally e SET e.movementReqId = :movementReqId, e.movementType = :movementType WHERE e.companyId =:companyId and e.branchId =:branchId and e.gateInId = :gateInId")
	int updateExportTallyMovement(
	    @Param("movementReqId") String movementReqId,
	    @Param("movementType") String movementType,
	    @Param("gateInId") String gateInId,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);
	
	
	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.companyId, e.branchId, e.stuffTallyId, e.sbTransId, "
	        + "e.stuffTallyLineId, pr.profitcentreDesc, e.sbLineId, e.sbNo, e.stuffTallyDate, "
	        + "e.stuffId, e.stuffDate, e.sbDate, e.shift, e.agentSealNo, e.vesselId, "
	        + "e.voyageNo, e.rotationNo, e.rotationDate, e.pol, e.terminal, e.pod, "
	        + "e.finalPod, e.containerNo, e.containerStatus, e.periodFrom, e.containerSize, "
	        + "e.containerType, e.containerCondition, SUM(e.yardPackages), e.cellAreaAllocated, "
	        + "e.onAccountOf, e.cha, e.stuffRequestQty, SUM(e.stuffedQty),SUM(e.balanceQty), "
	        + "(SELECT (COALESCE(SUM(car.noOfPackages), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
	        + "FROM ExportSbCargoEntry car WHERE car.companyId = e.companyId AND "
	        + "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalGrossWeight, e.tareWeight, "
	        + "SUM(e.areaReleased), e.genSetRequired, e.haz, p1.partyName, p2.partyName, "
	        + "e.commodity, e.customsSealNo, e.viaNo, e.exporterName, e.consignee, "
	        + "e.fob, e.berthingDate, e.gateOpenDate, e.sealType, e.docType, e.docNo, "
	        + "e.status, e.createdBy, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, "
	        + "e.deliveryOrderNo, e.stuffMode, e.typeOfPackage, v.vesselName, p3.partyName, e.totalCargoWeight) "
	        + "FROM ExportStuffTally e "
	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
	        + "LEFT OUTER JOIN Profitcentre pr ON e.companyId = pr.companyId AND e.branchId = pr.branchId AND e.profitcentreId = pr.profitcentreId "
	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.stuffTallyId = :id "
	        + "GROUP BY e.sbTransId, e.sbNo, e.stuffTallyId")
	List<ExportStuffTally> getDataByTallyId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);

	
	
	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.companyId, e.branchId, e.stuffTallyId, e.sbTransId, "
	        + "e.stuffTallyLineId, pr.profitcentreDesc, e.sbLineId, e.sbNo, e.stuffTallyDate, "
	        + "e.stuffId, e.stuffDate, e.sbDate, e.shift, e.agentSealNo, e.vesselId, "
	        + "e.voyageNo, e.rotationNo, e.rotationDate, e.pol, e.terminal, e.pod, "
	        + "e.finalPod, e.containerNo, e.containerStatus, e.periodFrom, e.containerSize, "
	        + "e.containerType, e.containerCondition, SUM(e.yardPackages), e.cellAreaAllocated, "
	        + "e.onAccountOf, e.cha, e.stuffRequestQty, SUM(e.stuffedQty),SUM(e.balanceQty), "
	        + "(SELECT (COALESCE(SUM(car.noOfPackagesStuffed), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
	        + "FROM ExportStuffRequest car WHERE car.companyId = e.companyId AND "
	        + "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalGrossWeight, e.tareWeight, "
	        + "SUM(e.areaReleased), e.genSetRequired, e.haz, p1.partyName, p2.partyName, "
	        + "e.commodity, e.customsSealNo, e.viaNo, e.exporterName, e.consignee, "
	        + "e.fob, e.berthingDate, e.gateOpenDate, e.sealType, e.docType, e.docNo, "
	        + "e.status, e.createdBy, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, "
	        + "e.deliveryOrderNo, e.stuffMode, e.typeOfPackage, v.vesselName, p3.partyName, e.totalCargoWeight) "
	        + "FROM ExportStuffTally e "
	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
	        + "LEFT OUTER JOIN Profitcentre pr ON e.companyId = pr.companyId AND e.branchId = pr.branchId AND e.profitcentreId = pr.profitcentreId "
	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.stuffTallyId = :id AND e.containerNo = :con "
	        + "GROUP BY e.sbTransId, e.sbNo, e.stuffTallyId")
	List<ExportStuffTally> getDataByTallyId1(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("con") String con);
	
	



    
	@Query(value="select distinct e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate, '%d %M %Y %H:%i'),e.containerNo from ExportStuffTally e where e.companyId=:cid and e.status != 'D' and "
			+ "e.branchId=:bid and (:id is null OR :id = '' OR e.stuffTallyId LIKE CONCAT ('%',:id,'%') OR e.containerNo LIKE CONCAT ('%',:id,'%')) order by e.stuffTallyId desc")
	List<Object[]> search(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	
	
	
//	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.stuffTallyId, e.sbTransId, e.sbNo, e.movementType,"
//			+ "e.stuffTallyDate, e.sbDate, e.agentSealNo, e.vesselId, e.voyageNo,"
//			+ "e.rotationNo, e.rotationDate, e.terminal, e.pod, e.finalPod, e.containerNo,"
//			+ "e.gateInId, e.containerSize, e.containerType, p4.partyName, p3.partyName,"
//			+ "e.stuffRequestQty, SUM(e.stuffedQty), e.cargoWeight, e.totalCargoWeight,"
//			+ "e.tareWeight, p1.partyName, p2.partyName, e.commodity,"
//			+ "e.customsSealNo, e.viaNo, e.exporterName, e.consignee, e.fob,"
//			+ "e.status, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, e.deliveryOrderNo,"
//			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate) "
//	        + "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
//	        + "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
//	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
//	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
//	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
//	        + "LEFT OUTER JOIN Party p4 ON e.companyId = p4.companyId AND e.branchId = p4.branchId AND e.onAccountOf = p4.partyId "
//	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
//	        + "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
//	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.sbTransId=:trans and e.sbNo=:sb "
//	        + "group by e.stuffTallyId, e.sbTransId, e.sbNo, e.containerNo")
//	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
//			@Param("sb") String sb);
	
	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.stuffTallyId, e.sbTransId, e.sbNo, e.movementType,"
			+ "e.stuffTallyDate, e.sbDate, e.agentSealNo, e.vesselId, e.voyageNo,"
			+ "e.rotationNo, e.rotationDate, e.terminal, e.pod, e.finalPod, e.containerNo,e.stuffId,"
			+ "e.gateInId, e.containerSize, e.containerType, p4.partyName, p3.partyName,"
			+ "e.stuffRequestQty, SUM(e.stuffedQty),(SELECT (COALESCE(SUM(car.noOfPackages), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
			+ "FROM ExportSbCargoEntry car WHERE car.companyId = e.companyId AND "
			+ "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalCargoWeight,"
			+ "e.tareWeight, p1.partyName, p2.partyName, e.commodity,"
			+ "e.customsSealNo, e.viaNo, e.exporterName, e.consignee, e.fob,"
			+ "e.status, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, e.deliveryOrderNo,"
			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate) "
	        + "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
	        + "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
	        + "LEFT OUTER JOIN Party p4 ON e.companyId = p4.companyId AND e.branchId = p4.branchId AND e.onAccountOf = p4.partyId "
	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
	        + "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.sbTransId=:trans and e.sbNo=:sb "
	        + "group by e.stuffTallyId, e.sbTransId, e.sbNo, e.containerNo")
	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
			@Param("sb") String sb);
	
	
	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
			+ "e.status != 'D' and (e.movementReqId is null OR e.movementReqId = '')")
	List<ExportStuffTally> getDataByStuffTallyId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
			+ "e.status != 'D' and e.stuffId=:stuffid and (e.movementReqId is null OR e.movementReqId = '')")
	List<ExportStuffTally> getDataByStuffTallyIdANDStuffId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid);
	
	
	
	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
			+ "e.status != 'D' and e.stuffId=:stuffid and e.cartingTransId=:car "
			+ "and (e.stuffRequestQty - e.stuffedQty)>0 and (e.movementReqId is null OR e.movementReqId = '')")
	ExportStuffTally getDataByStuffTallyIdANDStuffIdAndCartingTransId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid,@Param("car") String car);
	
	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
			+ "e.status != 'D' and e.stuffId=:stuffid and e.cartingTransId=:car "
			+ "and (e.movementReqId is null OR e.movementReqId = '')")
	ExportStuffTally getDataByStuffTallyIdANDStuffIdAndCartingTransId1(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid,@Param("car") String car);
	
	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
			+ "e.status != 'D' and e.stuffId=:stuffid and e.cartingTransId=:car and e.sbNo=:sb and e.sbTransId=:trans "
			+ "and (e.movementReqId is null OR e.movementReqId = '')")
	ExportStuffTally getDataByStuffTallyIdANDStuffIdAndCartingTransId2(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid,@Param("car") String car,@Param("sb") String sb,@Param("trans") String trans);
	
	@Query(value="select COUNT(e) from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
			+ "e.status != 'D'")
	int countOfStuffRecords(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	@Modifying
	@Transactional
	@Query(value="Update ExportStuffTally e SET e.stuffRequestQty=:qty where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and e.stuffTallyId = :id and e.stuffId=:stuffid and e.sbTransId=:sbtrans and e.sbNo=:sb")
	int updateStuffReqQuantity(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid,@Param("qty") BigDecimal qty,@Param("sbtrans") String sbtrans,@Param("sb") String sb);
	
	
	@Transactional
	@Modifying
	@Query(value = "Update ExportStuffTally e SET e.gatePassNo=:id where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and e.stuffTallyId = :stuffid and e.containerNo=:con")
	int updateGatePassNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid, @Param("con") String con);

}
