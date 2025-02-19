//package com.cwms.repository;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.beust.jcommander.Parameter;
//import com.cwms.entities.ExportStuffTally;
//
//import java.math.BigDecimal;
//import java.util.*;
//
//public interface ExportStuffTallyRepo extends JpaRepository<ExportStuffTally, String> {
//	
//	
//
//
//	@Query(value="select e.stuffId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y %H:%i'),e.containerNo,e.containerSize,e.containerType,"
//			+ "e.terminal,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
//			+ "p1.partyName,p2.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,e.stuffTallyWoTransId,"
//			+ "DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y'),DATE_FORMAT(e.cartingDate,'%d/%m/%Y'),e.sbNo,"
//			+ "DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,e.consignee,e.commodity,e.typeOfPackage,e.stuffRequestQty,"
//			+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.fob,e.tareWeight "
//			+ "from ExportStuffTally e "
//			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
//			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
//			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingAgent=p2.partyId "
//			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
//			+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
//			+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
//	List<Object[]> getDataForContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
//			@Param("con") String con);
//	
//	
//	@Query(value="select e.sbNo,e.viaNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),p4.partyName,v.vesselName,sb.noOfPackages,"
//			+ "p3.partyName,e.voyageNo,sb.grossWeight,e.consignee,e.pod,sb.cargoType,p1.partyName,e.terminal,e.commodity,"
//			+ "DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y %H:%i'),e.rotationNo,e.typeOfPackage,e.yardLocation,e.yardBlock,e.blockCellNo,"
//			+ "e.stuffTallyWoTransId,DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y %H:%i'),e.containerNo,e.containerSize,"
//			+ "e.containerType,e.agentSealNo,e.customsSealNo,COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.tareWeight,e.exporterName,"
//			+ "ROUND(e.cargoWeight+e.tareWeight,0) "
//			+ "from ExportStuffTally e "
//			+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
//			+ "and e.sbTransId=sb.sbTransId "
//			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
//			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
//			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
//			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.onAccountOf=p4.partyId "
//			+ "where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:id and e.sbNo=:sb and e.status = 'A' and "
//			+ "(e.reworkId is null OR e.reworkId = '') group by e.containerNo")
//	List<Object[]> getDataForSBWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
//			@Param("sb") String sb);
//	
//	
//	
//	
//	@Query(value="select e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
//			+ "e.pol,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
//			+ "p1.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,p2.partyName,"
//			+ "e.sbNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,s.consigneeName,e.commodity,sb.typeOfPackage,sb.noOfPackages,"
//			+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,sb.fob,e.tareWeight "
//			+ "from ExportStuffTally e "
//			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
//			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
//			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
//			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
//			+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
//			+ "and e.sbTransId=sb.sbTransId "
//			+ "LEFT OUTER JOIN ExportSbEntry s ON e.companyId=s.companyId and e.branchId=s.branchId and e.sbNo=s.sbNo "
//			+ "and e.sbTransId=s.sbTransId "
//			+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
//			+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
//	List<Object[]> getDataForBufferContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
//			@Param("con") String con);
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	@Modifying
//	@Transactional
//	@Query("UPDATE ExportStuffTally e " +
//	       "SET e.ssrTransId=:id " +
//	       "WHERE e.companyId = :companyId " +
//	       "  AND e.branchId = :branchId " +
//	       "  AND e.sbNo = :sbNo " +
//	       "  AND e.sbTransId = :sbTransId " +
//	       "  AND e.containerNo = :con " +
//	       "  AND e.status = 'A'")
//	int updateSSRData(	    
//	        @Param("companyId") String companyId,
//	        @Param("branchId") String branchId,
//	        @Param("sbNo") String sbNo,
//	        @Param("sbTransId") String sbTransId,
//	        @Param("con") String con,
//	        @Param("id") String id);
//	
//	
//	
//
//	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.stuffTallyId, e.sbTransId, e.sbNo, e.movementType,"
//			+ "e.stuffTallyDate, e.sbDate, e.agentSealNo, e.vesselId, e.voyageNo,"
//			+ "e.rotationNo, e.rotationDate, e.terminal, e.pod, e.finalPod, e.containerNo,e.stuffId,"
//			+ "e.gateInId, e.containerSize, e.containerType, p4.partyName, p3.partyName,"
//			+ "e.stuffRequestQty, SUM(e.stuffedQty),(SELECT (COALESCE(SUM(car.noOfPackages), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
//			+ "FROM ExportSbCargoEntry car WHERE car.companyId = e.companyId AND "
//			+ "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalCargoWeight,"
//			+ "e.tareWeight, p1.partyName, p2.partyName, e.commodity,"
//			+ "e.customsSealNo, e.viaNo, e.exporterName, e.consignee, e.fob,"
//			+ "e.status, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, e.deliveryOrderNo,"
//			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate,e.reworkFlag,e.createdBy) "
//			+ "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
//			+ "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
//			+ "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
//			+ "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
//			+ "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
//			+ "LEFT OUTER JOIN Party p4 ON e.companyId = p4.companyId AND e.branchId = p4.branchId AND e.onAccountOf = p4.partyId "
//			+ "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
//			+ "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
//			+ "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.sbTransId=:trans and e.sbNo=:sb "
//			+ "group by e.stuffTallyId, e.sbTransId, e.sbNo, e.containerNo")
//	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid,
//			@Param("trans") String trans, @Param("sb") String sb);
//	
//	
//	
//	@Query("SELECT COUNT(e) > 0 " +
//		       "FROM ExportStuffTally e " +
//		       "WHERE e.companyId = :companyId " +
//		       "AND e.branchId = :branchId " +
//		       "AND e.containerNo = :containerNo " +
//		       "AND e.profitcentreId = :profitcentreId " +
//		       "AND e.status <> 'D' " +
//		       "AND e.sbNo LIKE CONCAT('%', :sbNo, '%')")
//		boolean existsByContainerNoMovementAndSb(
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,		    
//		    @Param("profitcentreId") String profitcentreId,
//		    @Param("containerNo") String containerNo,
//		    @Param("sbNo") String sbNo
//		);
//
//	
//	@Query("SELECT E.reworkId "
//		     + "FROM ExportStuffTally E "
//		     + "WHERE E.companyId = :companyId "
//		     + "AND E.branchId = :branchId "
//		     + "AND E.containerNo = :containerNo "
//		     + "AND E.profitcentreId = :profitcentreId "
//		     + "AND (E.reworkId IS NOT NULL AND E.reworkId <> '') "
//		     + "AND E.status <> 'D' "
//		     + "ORDER BY E.createdDate DESC")
//		List<String> getDataForExportMainSearchStuffTallyReworking(
//		        @Param("companyId") String companyId, 
//		        @Param("branchId") String branchId,
//		        @Param("containerNo") String containerNo,
//		        @Param("profitcentreId") String profitcentreId);
//
//	
//	
//	
//
//	@Query("SELECT new com.cwms.entities.ExportStuffTally(E.stuffTallyId, E.sbTransId, E.stuffTallyLineId, E.sbLineId, "
//	           + "E.sbNo, E.containerNo, E.gateInId, E.reworkId) "
//	           + "FROM ExportStuffTally E "	           
//	           + "WHERE E.companyId = :companyId "
//	           + "AND E.branchId = :branchId "
//	           + "AND E.containerNo = :containerNo "
//	           + "AND E.gateInId = :gateInId "
//	           + "AND E.profitcentreId = :profitcentreId "
//	           + "AND (:sbNo IS NULL OR :sbNo = '' OR E.sbNo LIKE CONCAT(:sbNo, '%')) "
//	           + "AND E.status <> 'D' "
//	           + "ORDER BY E.createdDate DESC")
//	   Page<ExportStuffTally> getDataForExportMainSearchStuffTallyContainerSearch(
//	        @Param("companyId") String companyId, @Param("branchId") String branchId, @Param("containerNo") String containerNo,	       
//	        @Param("gateInId") String gateInId, @Param("profitcentreId") String profitcentreId, @Param("sbNo") String sbNo, Pageable pageable
//	    );
//	
//	
//	
//	
//	
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	@Query("SELECT new com.cwms.entities.ExportStuffTally(E.stuffTallyId, E.containerNo, E.gateInId, E.reworkId)"
//	           + "FROM ExportStuffTally E "	           
//	           + "WHERE E.companyId = :companyId "
//	           + "AND E.branchId = :branchId "
//	           + "AND E.sbNo = :sbNo "
//	           + "AND E.sbTransId = :sbTransId "
//	           + "AND E.profitcentreId = :profitcentreId "	           
//	           + "AND E.status <> 'D' "
//	           + "ORDER BY E.createdDate DESC")
//	   Page<ExportStuffTally> getDataForExportMainSsearchStuffTally(
//	        @Param("companyId") String companyId, @Param("branchId") String branchId,
//	        @Param("sbNo") String sbNo,@Param("sbTransId") String sbTransId,
//	        @Param("profitcentreId") String profitcentreId, Pageable pageable
//	    );
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	Export Buffer
//	
////
////	@Query(value = "select c.stuffTallyId, c.stuffTallyDate, c.stuffTallyLineId,c.profitcentreId, p.profitcentreDesc,psa.partyName, psa.partyName, c.containerNo, c.status "
////	        + "from ExportStuffTally c "
////	        + "LEFT JOIN Profitcentre p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.profitcentreId = p.profitcentreId "
////	        + "LEFT JOIN Party psa ON c.companyId = psa.companyId AND c.branchId = psa.branchId AND c.shippingAgent = psa.partyId AND psa.status <> 'D' "
////	        + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
////	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' AND c.movementType IN ('Buffer', 'ONWH') "
////	        + "and (:searchValue is null OR :searchValue = '' OR c.containerNo LIKE %:searchValue% OR c.stuffTallyId LIKE %:searchValue%) "
////	        + "ORDER BY c.stuffTallyDate DESC")
////	List<Object[]> getBufferStuffingToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue);
////	
////	
//	
//	@Query(value = "select c.stuffTallyId, c.stuffTallyDate, c.stuffTallyLineId,c.profitcentreId, p.profitcentreDesc,psa.partyName, psa.partyName, c.containerNo, c.status "
//	        + "from ExportStuffTally c "
//	        + "LEFT JOIN Profitcentre p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.profitcentreId = p.profitcentreId "
//	        + "LEFT JOIN Party psa ON c.companyId = psa.companyId AND c.branchId = psa.branchId AND c.shippingAgent = psa.partyId AND psa.status <> 'D' "
//	        + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
//	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' AND c.movementType IN ('Buffer', 'ONWH') "
//	        + "and (:searchValue is null OR :searchValue = '' OR c.containerNo LIKE %:searchValue% OR c.stuffTallyId LIKE %:searchValue%) "
//	        + "GROUP BY c.stuffTallyId ORDER BY c.stuffTallyDate DESC")
//	List<Object[]> getBufferStuffingToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue);
//	
//	
//	
//	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
//		       "FROM ExportInventory e " +		       
//		       "WHERE e.companyId = :companyId " +
//		       "AND e.branchId = :branchId " +
//		       "AND e.containerNo = :containerNo " +
//		       "AND e.gateInId = :gateInId " +
//		       "AND e.profitcentreId = :profitcentreId " +
//		       "AND e.status <> 'D' " +
//		       "AND (e.stuffTallyId IS NOT NULL AND e.stuffTallyId <> '') " +  // Ensure stuffTallyId is not NULL or empty
//		       "AND (:stuffTallyId IS NULL OR :stuffTallyId = '' OR e.stuffTallyId <> :stuffTallyId)")
//		boolean existsContainerNo(
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,
//		    @Param("containerNo") String containerNo,
//		    @Param("gateInId") String gateInId,
//		    @Param("profitcentreId") String profitcentreId,
//		    @Param("stuffTallyId") String stuffTallyId
//		);
//
//	
//	
//	
//	@Query("SELECT new com.cwms.entities.ExportStuffTally(E.companyId, E.branchId, E.stuffTallyId, E.sbTransId, "
//	        + "E.stuffTallyLineId, E.profitcentreId, E.sbLineId, E.sbNo, E.movementType, "
//	        + "E.stuffTallyDate, E.sbDate, E.shift, E.agentSealNo, E.vesselId, E.voyageNo, "
//	        + "E.rotationNo, E.rotationDate, E.pol, E.terminal, E.pod, E.finalPod, "
//	        + "E.containerNo, E.containerStatus, E.periodFrom, E.gateInId, E.containerSize, "
//	        + "E.containerType, E.containerCondition, E.onAccountOf, E.cha, E.stuffedQty, "
//	        + "ec.stuffedQty, E.cargoWeight, E.totalCargoWeight, E.totalGrossWeight, "
//	        + "E.grossWeight, E.tareWeight, E.areaReleased, E.haz, E.imoCode, "
//	        + "E.shippingAgent, E.shippingLine, E.commodity, E.customsSealNo, E.viaNo, "
//	        + "E.exporterName, E.consignee, E.fob, E.berthingDate, E.gateOpenDate, "
//	        + "E.status, E.createdBy, E.createdDate, E.editedBy, E.editedDate, "
//	        + "E.approvedBy, E.approvedDate, E.stuffTallyFlag, E.nopGrossWeight, "
//	        + "E.deliveryOrderNo, E.stuffMode, E.typeOfPackage, v.vesselName, "
//	        + "ec.noOfPackages, ec.stuffedWt, psl.partyName, "
//	        + "psa.partyName, COALESCE(pt.portName, E.terminal), COALESCE(pf.portName, E.finalPod)) "
//	           + "FROM ExportStuffTally E "
//	           + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.shippingAgent = psa.partyId AND psa.status <> 'D' "
//	           + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
//	           + "LEFT JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId AND v.status <> 'D' "          	           
//	           + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND ec.status <> 'D' "
//	           + "LEFT JOIN Port pt ON E.companyId = pt.companyId AND E.branchId = pt.branchId AND E.terminal = pt.portCode AND pt.status <> 'D' "
//	           + "LEFT JOIN Port pf ON E.companyId = pf.companyId AND E.branchId = pf.branchId AND E.finalPod = pf.portCode AND pf.status <> 'D' "    
//	           + "WHERE E.companyId = :companyId "
//	           + "AND E.branchId = :branchId "
//	           + "AND E.stuffTallyId = :stuffTallyId "
//	           + "AND E.profitcentreId = :profitcentreId "
//	           + "AND E.containerNo = :containerNo "
//	           + "AND E.status <> 'D'")
//	    List<ExportStuffTally> searchStuffTallySaved(
//	        @Param("companyId") String companyId,	        @
//	        Param("branchId") String branchId,
//	        @Param("stuffTallyId") String stuffTallyId,
//	        @Param("containerNo") String containerNo,
//	        @Param("profitcentreId") String profitcentreId
//	    );
//	
//	
//	@Query("SELECT e "
//	        + "FROM ExportStuffTally e "
//			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
//	        + "AND e.sbNo = :sbNo " 
//	        + "AND e.sbTransId = :sbTransId "
//	        + "AND e.stuffTallyId = :stuffTallyId " 
//	        + "AND e.stuffTallyLineId = :stuffTallyLineId "
//	        + "AND e.status <> 'D'")
//	ExportStuffTally getDataForUpdateEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
//	                                    @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId,
//	                                    @Param("stuffTallyId") String stuffTallyId, @Param("stuffTallyLineId") int stuffTallyLineId);
//
//	
//	
//	
//	
//	@Query("SELECT COALESCE(MAX(E.stuffTallyLineId), 0) FROM ExportStuffTally E "
//			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitcentreId = :profitcentreId "
//			+ "AND E.stuffTallyId = :stuffTallyId")
//	int getMaxLineId(@Param("companyId") String companyId, @Param("branchId") String branchId,
//			@Param("profitcentreId") String profitcentreId, @Param("stuffTallyId") String stuffTallyId);
//
//	
//
//	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
//		       "FROM ExportStuffTally e " +
//		       "WHERE e.companyId = :companyId " +
//		       "AND e.branchId = :branchId " +
//		       "AND e.sbNo = :sbNo " +
//		       "AND e.sbTransId = :sbTransId " +
//		       "AND e.profitcentreId = :profitcentreId " +
//		       "AND e.status <> 'D' " +
//		       "AND e.stuffTallyLineId = :stuffTallyLineId " +
//		       "AND e.stuffTallyId = :stuffTallyId")
//		boolean existsBySbNoForstuffingTally(
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,
//		    @Param("sbNo") String sbNo,
//		    @Param("sbTransId") String sbTransId,
//		    @Param("profitcentreId") String profitcentreId,
//		    @Param("stuffTallyId") String stuffTallyId,
//		    @Param("stuffTallyLineId") Integer stuffTallyLineId
//		);
//
//
//	
//	
//	@Query("SELECT E.sbNo, E.sbTransId, E.srno, g.exporterId, g.exporterName, E.sbDate, E.stuffedQty, E.commodity, E.noOfPackages, g.pod, E.grossWeight, E.stuffedWt,g.cha " +
//		       "FROM ExportSbCargoEntry E " +
//		       "LEFT JOIN ExportSbEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.sbNo AND g.sbTransId = E.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
//		       "LEFT JOIN ExportStuffTally st ON E.companyId = st.companyId AND E.branchId = st.branchId AND st.sbNo = E.sbNo AND st.sbTransId = E.sbTransId AND st.profitcentreId = E.profitcentreId AND st.status <> 'D' AND st.stuffTallyId = :stuffTallyId " +
//		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
//		       "AND (:searchValue IS NULL OR :searchValue = '' OR E.sbNo LIKE CONCAT('%', :searchValue, '%')) " +
//		       "AND (g.holdStatus IS NULL OR g.holdStatus = '' OR g.holdStatus <> 'H') " +
//		       "AND (E.noOfPackages - E.stuffedQty) > 0 " +
//		       "AND E.profitcentreId = :profitcentreId " +
//		       "AND E.sbType =:sbType " +
//		       "AND st.stuffTallyId IS NULL " +
//		       "AND E.status <> 'D'")
//		List<Object[]> searchSbNoForTallyBuffer(
//		    @Param("companyId") String companyId, 
//		    @Param("branchId") String branchId, 
//		    @Param("searchValue") String searchValue, 
//		    @Param("profitcentreId") String profitcentreId,
//		    @Param("stuffTallyId") String stuffTallyId,
//		    @Param("sbType") String sbType
//		);
//
//	
//	
//	
//	
//	
//		@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, g.onAccountOf, g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.gateInId, g.gateInType,g.containerHealth,g.customsSealNo "          
//		        + "FROM ExportInventory E "
//		        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' AND (g.stuffTallyId = '' OR g.stuffTallyId IS NULL) AND g.containerNo = E.containerNo "
//		        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
//		        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
//		        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
//		        + "AND (:searchValue IS NULL OR :searchValue = '' OR E.containerNo LIKE CONCAT('%', :searchValue, '%')) "
//		        + "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') "
//		        + "AND (E.stuffTallyId = '' OR E.stuffTallyId IS NULL) "
//		        + "AND E.profitcentreId = :profitcentreId "
//		        + "AND g.gateInType In ('Buffer','ONWH') "
//		        + "AND E.status <> 'D'")
//		List<Object[]> searchContainerNoForTallyBuffer(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	@Transactional
//	@Modifying
//	@Query("UPDATE ExportStuffTally e SET e.movementReqId = :movementReqId, e.movementType = :movementType WHERE e.companyId =:companyId and e.branchId =:branchId and e.gateInId = :gateInId")
//	int updateExportTallyMovement(
//	    @Param("movementReqId") String movementReqId,
//	    @Param("movementType") String movementType,
//	    @Param("gateInId") String gateInId,
//	    @Param("companyId") String companyId,
//	    @Param("branchId") String branchId
//	);
//	
//	
//	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.companyId, e.branchId, e.stuffTallyId, e.sbTransId, "
//	        + "e.stuffTallyLineId, pr.profitcentreDesc, e.sbLineId, e.sbNo, e.stuffTallyDate, "
//	        + "e.stuffId, e.stuffDate, e.sbDate, e.shift, e.agentSealNo, e.vesselId, "
//	        + "e.voyageNo, e.rotationNo, e.rotationDate, e.pol, e.terminal, e.pod, "
//	        + "e.finalPod, e.containerNo, e.containerStatus, e.periodFrom, e.containerSize, "
//	        + "e.containerType, e.containerCondition, SUM(e.yardPackages), e.cellAreaAllocated, "
//	        + "e.onAccountOf, e.cha, e.stuffRequestQty, SUM(e.stuffedQty),SUM(e.balanceQty), "
//	        + "(SELECT (COALESCE(SUM(car.noOfPackages), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
//	        + "FROM ExportSbCargoEntry car WHERE car.companyId = e.companyId AND "
//	        + "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalGrossWeight, e.tareWeight, "
//	        + "SUM(e.areaReleased), e.genSetRequired, e.haz, p1.partyName, p2.partyName, "
//	        + "e.commodity, e.customsSealNo, e.viaNo, e.exporterName, e.consignee, "
//	        + "e.fob, e.berthingDate, e.gateOpenDate, e.sealType, e.docType, e.docNo, "
//	        + "e.status, e.createdBy, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, "
//	        + "e.deliveryOrderNo, e.stuffMode, e.typeOfPackage, v.vesselName, p3.partyName, e.totalCargoWeight) "
//	        + "FROM ExportStuffTally e "
//	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
//	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
//	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
//	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
//	        + "LEFT OUTER JOIN Profitcentre pr ON e.companyId = pr.companyId AND e.branchId = pr.branchId AND e.profitcentreId = pr.profitcentreId "
//	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.stuffTallyId = :id "
//	        + "GROUP BY e.sbTransId, e.sbNo, e.stuffTallyId")
//	List<ExportStuffTally> getDataByTallyId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
//
//	
//	
//	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.companyId, e.branchId, e.stuffTallyId, e.sbTransId, "
//	        + "e.stuffTallyLineId, pr.profitcentreDesc, e.sbLineId, e.sbNo, e.stuffTallyDate, "
//	        + "e.stuffId, e.stuffDate, e.sbDate, e.shift, e.agentSealNo, e.vesselId, "
//	        + "e.voyageNo, e.rotationNo, e.rotationDate, e.pol, e.terminal, e.pod, "
//	        + "e.finalPod, e.containerNo, e.containerStatus, e.periodFrom, e.containerSize, "
//	        + "e.containerType, e.containerCondition, SUM(e.yardPackages), e.cellAreaAllocated, "
//	        + "e.onAccountOf, e.cha, e.stuffRequestQty, SUM(e.stuffedQty),SUM(e.balanceQty), "
//	        + "(SELECT (COALESCE(SUM(car.noOfPackagesStuffed), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
//	        + "FROM ExportStuffRequest car WHERE car.companyId = e.companyId AND "
//	        + "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalGrossWeight, e.tareWeight, "
//	        + "SUM(e.areaReleased), e.genSetRequired, e.haz, p1.partyName, p2.partyName, "
//	        + "e.commodity, e.customsSealNo, e.viaNo, e.exporterName, e.consignee, "
//	        + "e.fob, e.berthingDate, e.gateOpenDate, e.sealType, e.docType, e.docNo, "
//	        + "e.status, e.createdBy, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, "
//	        + "e.deliveryOrderNo, e.stuffMode, e.typeOfPackage, v.vesselName, p3.partyName, e.totalCargoWeight) "
//	        + "FROM ExportStuffTally e "
//	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
//	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
//	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
//	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
//	        + "LEFT OUTER JOIN Profitcentre pr ON e.companyId = pr.companyId AND e.branchId = pr.branchId AND e.profitcentreId = pr.profitcentreId "
//	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.stuffTallyId = :id AND e.containerNo = :con "
//	        + "GROUP BY e.sbTransId, e.sbNo, e.stuffTallyId")
//	List<ExportStuffTally> getDataByTallyId1(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("con") String con);
//	
//	
//
//
//
//    
//	@Query(value="select distinct e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate, '%d %M %Y %H:%i'),e.containerNo from ExportStuffTally e where e.companyId=:cid and e.status != 'D' and "
//			+ "e.branchId=:bid and (:id is null OR :id = '' OR e.stuffTallyId LIKE CONCAT ('%',:id,'%') OR e.containerNo LIKE CONCAT ('%',:id,'%')) order by e.stuffTallyId desc")
//	List<Object[]> search(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
//	
//	
//	
//	
////	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.stuffTallyId, e.sbTransId, e.sbNo, e.movementType,"
////			+ "e.stuffTallyDate, e.sbDate, e.agentSealNo, e.vesselId, e.voyageNo,"
////			+ "e.rotationNo, e.rotationDate, e.terminal, e.pod, e.finalPod, e.containerNo,"
////			+ "e.gateInId, e.containerSize, e.containerType, p4.partyName, p3.partyName,"
////			+ "e.stuffRequestQty, SUM(e.stuffedQty), e.cargoWeight, e.totalCargoWeight,"
////			+ "e.tareWeight, p1.partyName, p2.partyName, e.commodity,"
////			+ "e.customsSealNo, e.viaNo, e.exporterName, e.consignee, e.fob,"
////			+ "e.status, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, e.deliveryOrderNo,"
////			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate) "
////	        + "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
////	        + "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
////	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
////	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
////	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
////	        + "LEFT OUTER JOIN Party p4 ON e.companyId = p4.companyId AND e.branchId = p4.branchId AND e.onAccountOf = p4.partyId "
////	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
////	        + "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
////	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.sbTransId=:trans and e.sbNo=:sb "
////	        + "group by e.stuffTallyId, e.sbTransId, e.sbNo, e.containerNo")
////	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
////			@Param("sb") String sb);
//	
////	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.stuffTallyId, e.sbTransId, e.sbNo, e.movementType,"
////			+ "e.stuffTallyDate, e.sbDate, e.agentSealNo, e.vesselId, e.voyageNo,"
////			+ "e.rotationNo, e.rotationDate, e.terminal, e.pod, e.finalPod, e.containerNo,e.stuffId,"
////			+ "e.gateInId, e.containerSize, e.containerType, p4.partyName, p3.partyName,"
////			+ "e.stuffRequestQty, SUM(e.stuffedQty),(SELECT (COALESCE(SUM(car.noOfPackages), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
////			+ "FROM ExportSbCargoEntry car WHERE car.companyId = e.companyId AND "
////			+ "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalCargoWeight,"
////			+ "e.tareWeight, p1.partyName, p2.partyName, e.commodity,"
////			+ "e.customsSealNo, e.viaNo, e.exporterName, e.consignee, e.fob,"
////			+ "e.status, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, e.deliveryOrderNo,"
////			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate,e.reworkFlag) "
////			+ "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
////			+ "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
////			+ "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
////			+ "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
////			+ "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
////			+ "LEFT OUTER JOIN Party p4 ON e.companyId = p4.companyId AND e.branchId = p4.branchId AND e.onAccountOf = p4.partyId "
////			+ "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
////			+ "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
////			+ "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.sbTransId=:trans and e.sbNo=:sb "
////			+ "group by e.stuffTallyId, e.sbTransId, e.sbNo, e.containerNo")
////	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid,
////			@Param("trans") String trans, @Param("sb") String sb);
////	
//	
//	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
//			+ "e.status != 'D' and (e.movementReqId is null OR e.movementReqId = '')")
//	List<ExportStuffTally> getDataByStuffTallyId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
//	
//	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
//			+ "e.status != 'D' and e.stuffId=:stuffid and (e.movementReqId is null OR e.movementReqId = '')")
//	List<ExportStuffTally> getDataByStuffTallyIdANDStuffId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid);
//	
//	
//	
//	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
//			+ "e.status != 'D' and e.stuffId=:stuffid and e.cartingTransId=:car "
//			+ "and (e.stuffRequestQty - e.stuffedQty)>0 and (e.movementReqId is null OR e.movementReqId = '')")
//	ExportStuffTally getDataByStuffTallyIdANDStuffIdAndCartingTransId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid,@Param("car") String car);
//	
//	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
//			+ "e.status != 'D' and e.stuffId=:stuffid and e.cartingTransId=:car "
//			+ "and (e.movementReqId is null OR e.movementReqId = '')")
//	ExportStuffTally getDataByStuffTallyIdANDStuffIdAndCartingTransId1(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid,@Param("car") String car);
//	
//	@Query(value="select e from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
//			+ "e.status != 'D' and e.stuffId=:stuffid and e.cartingTransId=:car and e.sbNo=:sb and e.sbTransId=:trans "
//			+ "and (e.movementReqId is null OR e.movementReqId = '')")
//	ExportStuffTally getDataByStuffTallyIdANDStuffIdAndCartingTransId2(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid,@Param("car") String car,@Param("sb") String sb,@Param("trans") String trans);
//	
//	@Query(value="select COUNT(e) from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId = :id and "
//			+ "e.status != 'D'")
//	int countOfStuffRecords(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
//	
//	@Modifying
//	@Transactional
//	@Query(value="Update ExportStuffTally e SET e.stuffRequestQty=:qty where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
//			+ "and e.stuffTallyId = :id and e.stuffId=:stuffid and e.sbTransId=:sbtrans and e.sbNo=:sb")
//	int updateStuffReqQuantity(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid,@Param("qty") BigDecimal qty,@Param("sbtrans") String sbtrans,@Param("sb") String sb);
//	
//	
//	@Transactional
//	@Modifying
//	@Query(value = "Update ExportStuffTally e SET e.gatePassNo=:id where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
//			+ "and e.stuffTallyId = :stuffid and e.containerNo=:con")
//	int updateGatePassNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid, @Param("con") String con);
//	
//	
//	@Transactional
//	@Modifying
//	@Query(value = "Update ExportStuffTally e SET e.gateOutId=:id, e.gateOutDate = CURRENT_DATE where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
//			+ "and e.stuffTallyId = :stuffid and e.containerNo=:con")
//	int updateGateOutNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
//			@Param("stuffid") String stuffid, @Param("con") String con);
//	
//	
//	@Query(value = "select e.sbTransId,e.sbNo,e.sbDate,e.commodity,SUM(e.stuffedQty),SUM(e.cargoWeight),e.agentSealNo,e.customsSealNo,e.movementReqId,e.fob "
//			+ "from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
//			+ "e.gateOutId=:outId and e.containerNo=:con group by e.sbTransId,e.sbNo")
//	List<Object[]> getStufftallyRecordsForPortContainerGateIn(@Param("cid") String cid, @Param("bid") String bid, 
//			@Param("outId") String outId,@Param("con") String con);
//	
//	
//	@Query(value = "select DISTINCT e.containerNo from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
//			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') and (e.movementReqId is null OR e.movementReqId = '') "
//			+ "and (e.gateOutId is null OR e.gateOutId = '') and (:val is null OR :val = '' OR e.containerNo LIKE CONCAT('%',:val,'%')) and e.movementType=:type")
//	List<String> getRecordsForExportReworking(@Param("cid") String cid, @Param("bid") String bid,
//			@Param("val") String val,@Param("type") String type);
//
//	@Query(value = "select e.gateInId,g.inGateInDate,e.containerNo,e.containerSize,e.containerType,e.containerStatus,"
//			+ "e.customsSealNo,p1.partyName,g.vehicleNo,COALESCE(SUM(e.areaReleased),0),COALESCE(SUM(e.stuffedQty),0),"
//			+ "e.onAccountOf,p2.partyName,e.sbTransId,e.sbNo,e.commodity,e.cargoWeight,e.fob,e.shippingAgent "
//			+ "from ExportStuffTally e LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId=g.gateInId "
//			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.shippingAgent=p1.partyId "
//			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
//			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
//			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') and (e.movementReqId is null OR e.movementReqId = '') "
//			+ "and (e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:val group by e.sbNo,e.sbTransId")
//	List<Object[]> getRecordsForExportReworkingByContainerNo(@Param("cid") String cid, @Param("bid") String bid,
//			@Param("val") String val);
//
//	@Transactional
//	@Modifying
//	@Query(value="Update ExportStuffTally e SET e.reworkFlag='Y',e.reworkId=:id,e.reworkDate=CURRENT_DATE where e.companyId=:cid and "
//			+ "e.branchId=:bid and e.status != 'D' "
//			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') "
//			+ "and (e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:val")
//	int updateReworkId(@Param("cid") String cid, @Param("bid") String bid,@Param("id") String id,
//			@Param("val") String val);
//	
//	
//	@Query(value = "select e "
//			+ "from ExportStuffTally e "
//			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
//			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') and (e.movementReqId is null OR e.movementReqId = '') "
//			+ "and (e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:val and e.sbNo=:sb and e.sbTransId=:trans")
//	List<ExportStuffTally> getRecordsForExportReworkingByContainerNo1(@Param("cid") String cid, @Param("bid") String bid,
//			@Param("val") String val,@Param("sb") String sb,@Param("trans") String trans);
//	
//	
//	
//	//Report
//	
//	
////		@Query(value="select e.stuffId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
////				+ "e.terminal,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
////				+ "p1.partyName,p2.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,e.stuffTallyWoTransId,"
////				+ "DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y'),DATE_FORMAT(e.cartingDate,'%d/%m/%Y'),e.sbNo,"
////				+ "DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,e.consignee,e.commodity,e.typeOfPackage,e.stuffRequestQty,"
////				+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.fob,e.tareWeight "
////				+ "from ExportStuffTally e "
////				+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
////				+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
////				+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingAgent=p2.partyId "
////				+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
////				+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
////				+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
////		List<Object[]> getDataForContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
////				@Param("con") String con);
////		
////		
////		@Query(value="select e.sbNo,e.viaNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),p4.partyName,v.vesselName,sb.noOfPackages,"
////				+ "p3.partyName,e.voyageNo,sb.grossWeight,e.consignee,e.pod,sb.cargoType,p1.partyName,e.terminal,e.commodity,"
////				+ "DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.rotationNo,e.typeOfPackage,e.yardLocation,e.yardBlock,e.blockCellNo,"
////				+ "e.stuffTallyWoTransId,DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y'),e.containerNo,e.containerSize,"
////				+ "e.containerType,e.agentSealNo,e.customsSealNo,COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.tareWeight,e.exporterName,"
////				+ "ROUND(e.cargoWeight+e.tareWeight,0) "
////				+ "from ExportStuffTally e "
////				+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
////				+ "and e.sbTransId=sb.sbTransId "
////				+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
////				+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
////				+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
////				+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.onAccountOf=p4.partyId "
////				+ "where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:id and e.sbNo=:sb and e.status = 'A' and "
////				+ "(e.reworkId is null OR e.reworkId = '') group by e.containerNo")
////		List<Object[]> getDataForSBWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
////				@Param("sb") String sb);
////		
////		
////		
////		
////		@Query(value="select e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
////				+ "e.pol,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
////				+ "p1.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,p2.partyName,"
////				+ "e.sbNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,s.consigneeName,e.commodity,sb.typeOfPackage,sb.noOfPackages,"
////				+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,sb.fob,e.tareWeight "
////				+ "from ExportStuffTally e "
////				+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
////				+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
////				+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
////				+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
////				+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
////				+ "and e.sbTransId=sb.sbTransId "
////				+ "LEFT OUTER JOIN ExportSbEntry s ON e.companyId=s.companyId and e.branchId=s.branchId and e.sbNo=s.sbNo "
////				+ "and e.sbTransId=s.sbTransId "
////				+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
////				+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
////		List<Object[]> getDataForBufferContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
////				@Param("con") String con);
//}



















package com.cwms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	        + "e.deliveryOrderNo, e.stuffMode, e.typeOfPackage, v.vesselName, p3.partyName, e.totalCargoWeight, e.approvedBy, "
	        + "e.length, e.height, e.weight, e.odcType, c.cargoType, e.hsnCode) "
	        + "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
			+ "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
	        + "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
	        + "LEFT OUTER JOIN Profitcentre pr ON e.companyId = pr.companyId AND e.branchId = pr.branchId AND e.profitcentreId = pr.profitcentreId "
	        + "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.stuffTallyId = :id "
	        + "GROUP BY e.sbTransId, e.sbNo, e.stuffTallyId")
	List<ExportStuffTally> getDataByTallyId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);


	@Query(value="select e.stuffId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y %H:%i'),e.containerNo,e.containerSize,e.containerType,"
			+ "e.terminal,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
			+ "p1.partyName,p2.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,e.stuffTallyWoTransId,"
			+ "DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y'),DATE_FORMAT(e.cartingDate,'%d/%m/%Y'),e.sbNo,"
			+ "DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,e.consignee,e.commodity,e.typeOfPackage,e.stuffRequestQty,"
			+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.fob,e.tareWeight "
			+ "from ExportStuffTally e "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingAgent=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
			+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
	List<Object[]> getDataForContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("con") String con);
	
	
	@Query(value="select e.sbNo,e.viaNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),p4.partyName,v.vesselName,sb.noOfPackages,"
			+ "p3.partyName,e.voyageNo,sb.grossWeight,e.consignee,e.pod,sb.cargoType,p1.partyName,e.terminal,e.commodity,"
			+ "DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y %H:%i'),e.rotationNo,e.typeOfPackage,e.yardLocation,e.yardBlock,e.blockCellNo,"
			+ "e.stuffTallyWoTransId,DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y %H:%i'),e.containerNo,e.containerSize,"
			+ "e.containerType,e.agentSealNo,e.customsSealNo,COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.tareWeight,e.exporterName,"
			+ "ROUND(e.cargoWeight+e.tareWeight,0) "
			+ "from ExportStuffTally e "
			+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
			+ "and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.onAccountOf=p4.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:id and e.sbNo=:sb and e.status = 'A' and "
			+ "(e.reworkId is null OR e.reworkId = '') group by e.containerNo")
	List<Object[]> getDataForSBWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("sb") String sb);
	
	
	
	
	@Query(value="select e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
			+ "e.pol,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
			+ "p1.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,p2.partyName,"
			+ "e.sbNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,s.consigneeName,e.commodity,sb.typeOfPackage,sb.noOfPackages,"
			+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,sb.fob,e.tareWeight "
			+ "from ExportStuffTally e "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
			+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
			+ "and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN ExportSbEntry s ON e.companyId=s.companyId and e.branchId=s.branchId and e.sbNo=s.sbNo "
			+ "and e.sbTransId=s.sbTransId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
			+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
	List<Object[]> getDataForBufferContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
			@Param("con") String con);

	
	
	
	
	
	
	
	
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ExportStuffTally e " +
	       "SET e.ssrTransId=:id " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.containerNo = :con " +
	       "  AND e.status = 'A'")
	int updateSSRData(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("con") String con,
	        @Param("id") String id);
	
	
	

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
			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate,e.reworkFlag,e.createdBy, e.approvedBy, e.length, e.height, e.weight, e.odcType,e.hsnCode) "
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
	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid,
			@Param("trans") String trans, @Param("sb") String sb);
	
	
	
	@Query("SELECT COUNT(e) > 0 " +
		       "FROM ExportStuffTally e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.containerNo = :containerNo " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.status <> 'D' " +
		       "AND e.sbNo LIKE CONCAT('%', :sbNo, '%')")
		boolean existsByContainerNoMovementAndSb(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("containerNo") String containerNo,
		    @Param("sbNo") String sbNo
		);

	
	@Query("SELECT E.reworkId "
		     + "FROM ExportStuffTally E "
		     + "WHERE E.companyId = :companyId "
		     + "AND E.branchId = :branchId "
		     + "AND E.containerNo = :containerNo "
		     + "AND E.profitcentreId = :profitcentreId "
		     + "AND (E.reworkId IS NOT NULL AND E.reworkId <> '') "
		     + "AND E.status <> 'D' "
		     + "ORDER BY E.createdDate DESC")
		List<String> getDataForExportMainSearchStuffTallyReworking(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("containerNo") String containerNo,
		        @Param("profitcentreId") String profitcentreId);

	
	
	

	@Query("SELECT new com.cwms.entities.ExportStuffTally(E.stuffTallyId, E.sbTransId, E.stuffTallyLineId, E.sbLineId, "
	           + "E.sbNo, E.containerNo, E.gateInId, E.reworkId) "
	           + "FROM ExportStuffTally E "	           
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.containerNo = :containerNo "
	           + "AND E.gateInId = :gateInId "
	           + "AND E.profitcentreId = :profitcentreId "
	           + "AND (:sbNo IS NULL OR :sbNo = '' OR E.sbNo LIKE CONCAT(:sbNo, '%')) "
	           + "AND E.status <> 'D' "
	           + "ORDER BY E.createdDate DESC")
	   Page<ExportStuffTally> getDataForExportMainSearchStuffTallyContainerSearch(
	        @Param("companyId") String companyId, @Param("branchId") String branchId, @Param("containerNo") String containerNo,	       
	        @Param("gateInId") String gateInId, @Param("profitcentreId") String profitcentreId, @Param("sbNo") String sbNo, Pageable pageable
	    );
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT new com.cwms.entities.ExportStuffTally(E.stuffTallyId, E.containerNo, E.gateInId, E.reworkId)"
	           + "FROM ExportStuffTally E "	           
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.sbNo = :sbNo "
	           + "AND E.sbTransId = :sbTransId "
	           + "AND E.profitcentreId = :profitcentreId "	           
	           + "AND E.status <> 'D' "
	           + "ORDER BY E.createdDate DESC")
	   Page<ExportStuffTally> getDataForExportMainSsearchStuffTally(
	        @Param("companyId") String companyId, @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,@Param("sbTransId") String sbTransId,
	        @Param("profitcentreId") String profitcentreId, Pageable pageable
	    );
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	Export Buffer
	
//
//	@Query(value = "select c.stuffTallyId, c.stuffTallyDate, c.stuffTallyLineId,c.profitcentreId, p.profitcentreDesc,psa.partyName, psa.partyName, c.containerNo, c.status "
//	        + "from ExportStuffTally c "
//	        + "LEFT JOIN Profitcentre p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.profitcentreId = p.profitcentreId "
//	        + "LEFT JOIN Party psa ON c.companyId = psa.companyId AND c.branchId = psa.branchId AND c.shippingAgent = psa.partyId AND psa.status <> 'D' "
//	        + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
//	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' AND c.movementType IN ('Buffer', 'ONWH') "
//	        + "and (:searchValue is null OR :searchValue = '' OR c.containerNo LIKE %:searchValue% OR c.stuffTallyId LIKE %:searchValue%) "
//	        + "ORDER BY c.stuffTallyDate DESC")
//	List<Object[]> getBufferStuffingToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue);
//	
//	
	
	@Query(value = "select c.stuffTallyId, c.stuffTallyDate, c.stuffTallyLineId,c.profitcentreId, p.profitcentreDesc,psa.partyName, psa.partyName, c.containerNo, c.status "
	        + "from ExportStuffTally c "
	        + "LEFT JOIN Profitcentre p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.profitcentreId = p.profitcentreId "
	        + "LEFT JOIN Party psa ON c.companyId = psa.companyId AND c.branchId = psa.branchId AND c.shippingAgent = psa.partyId AND psa.status <> 'D' "
	        + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' AND c.movementType IN ('Buffer', 'ONWH') "
	        + "and (:searchValue is null OR :searchValue = '' OR c.containerNo LIKE %:searchValue% OR c.stuffTallyId LIKE %:searchValue%) "
	        + "GROUP BY c.stuffTallyId ORDER BY c.stuffTallyDate DESC")
	List<Object[]> getBufferStuffingToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue);
	
	
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM ExportInventory e " +		       
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.containerNo = :containerNo " +
		       "AND e.gateInId = :gateInId " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.status <> 'D' " +
		       "AND (e.stuffTallyId IS NOT NULL AND e.stuffTallyId <> '') " +  // Ensure stuffTallyId is not NULL or empty
		       "AND (:stuffTallyId IS NULL OR :stuffTallyId = '' OR e.stuffTallyId <> :stuffTallyId)")
		boolean existsContainerNo(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("containerNo") String containerNo,
		    @Param("gateInId") String gateInId,
		    @Param("profitcentreId") String profitcentreId,
		    @Param("stuffTallyId") String stuffTallyId
		);

	
	
	
	@Query("SELECT new com.cwms.entities.ExportStuffTally(E.companyId, E.branchId, E.stuffTallyId, E.sbTransId, "
	        + "E.stuffTallyLineId, E.profitcentreId, E.sbLineId, E.sbNo, E.movementType, "
	        + "E.stuffTallyDate, E.sbDate, E.shift, E.agentSealNo, E.vesselId, E.voyageNo, "
	        + "E.rotationNo, E.rotationDate, E.pol, E.terminal, E.pod, E.finalPod, "
	        + "E.containerNo, E.containerStatus, E.periodFrom, E.gateInId, E.containerSize, "
	        + "E.containerType, E.containerCondition, E.onAccountOf, E.cha, E.stuffedQty, "
	        + "ec.stuffedQty, E.cargoWeight, E.totalCargoWeight, E.totalGrossWeight, "
	        + "E.grossWeight, E.tareWeight, E.areaReleased, E.haz, E.imoCode, "
	        + "E.shippingAgent, E.shippingLine, E.commodity, E.customsSealNo, E.viaNo, "
	        + "E.exporterName, E.consignee, E.fob, E.berthingDate, E.gateOpenDate, "
	        + "E.status, E.createdBy, E.createdDate, E.editedBy, E.editedDate, "
	        + "E.approvedBy, E.approvedDate, E.stuffTallyFlag, E.nopGrossWeight, "
	        + "E.deliveryOrderNo, E.stuffMode, E.typeOfPackage, v.vesselName, "
	        + "ec.noOfPackages, ec.stuffedWt, psl.partyName, "
	        + "psa.partyName, COALESCE(pt.portName, E.terminal), COALESCE(pf.portName, E.finalPod)) "
	           + "FROM ExportStuffTally E "
	           + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.shippingAgent = psa.partyId AND psa.status <> 'D' "
	           + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
	           + "LEFT JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId AND v.status <> 'D' "          	           
	           + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND ec.status <> 'D' "
	           + "LEFT JOIN Port pt ON E.companyId = pt.companyId AND E.branchId = pt.branchId AND E.terminal = pt.portCode AND pt.status <> 'D' "
	           + "LEFT JOIN Port pf ON E.companyId = pf.companyId AND E.branchId = pf.branchId AND E.finalPod = pf.portCode AND pf.status <> 'D' "    
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.stuffTallyId = :stuffTallyId "
	           + "AND E.profitcentreId = :profitcentreId "
	           + "AND E.containerNo = :containerNo "
	           + "AND E.status <> 'D'")
	    List<ExportStuffTally> searchStuffTallySaved(
	        @Param("companyId") String companyId,	        @
	        Param("branchId") String branchId,
	        @Param("stuffTallyId") String stuffTallyId,
	        @Param("containerNo") String containerNo,
	        @Param("profitcentreId") String profitcentreId
	    );
	
	
	@Query("SELECT e "
	        + "FROM ExportStuffTally e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.sbNo = :sbNo " 
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.stuffTallyId = :stuffTallyId " 
	        + "AND e.stuffTallyLineId = :stuffTallyLineId "
	        + "AND e.status <> 'D'")
	ExportStuffTally getDataForUpdateEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                    @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId,
	                                    @Param("stuffTallyId") String stuffTallyId, @Param("stuffTallyLineId") int stuffTallyLineId);

	
	
	
	
	@Query("SELECT COALESCE(MAX(E.stuffTallyLineId), 0) FROM ExportStuffTally E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitcentreId = :profitcentreId "
			+ "AND E.stuffTallyId = :stuffTallyId")
	int getMaxLineId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("stuffTallyId") String stuffTallyId);

	

	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM ExportStuffTally e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.sbNo = :sbNo " +
		       "AND e.sbTransId = :sbTransId " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.status <> 'D' " +
		       "AND e.stuffTallyLineId = :stuffTallyLineId " +
		       "AND e.stuffTallyId = :stuffTallyId")
		boolean existsBySbNoForstuffingTally(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("sbNo") String sbNo,
		    @Param("sbTransId") String sbTransId,
		    @Param("profitcentreId") String profitcentreId,
		    @Param("stuffTallyId") String stuffTallyId,
		    @Param("stuffTallyLineId") Integer stuffTallyLineId
		);


	
	
	@Query("SELECT E.sbNo, E.sbTransId, E.srno, g.exporterId, g.exporterName, E.sbDate, E.stuffedQty, E.commodity, E.noOfPackages, g.pod, E.grossWeight, E.stuffedWt,g.cha " +
		       "FROM ExportSbCargoEntry E " +
		       "LEFT JOIN ExportSbEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.sbNo AND g.sbTransId = E.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
		       "LEFT JOIN ExportStuffTally st ON E.companyId = st.companyId AND E.branchId = st.branchId AND st.sbNo = E.sbNo AND st.sbTransId = E.sbTransId AND st.profitcentreId = E.profitcentreId AND st.status <> 'D' AND st.stuffTallyId = :stuffTallyId " +
		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
		       "AND (:searchValue IS NULL OR :searchValue = '' OR E.sbNo LIKE CONCAT('%', :searchValue, '%')) " +
		       "AND (g.holdStatus IS NULL OR g.holdStatus = '' OR g.holdStatus <> 'H') " +
		       "AND (E.noOfPackages - E.stuffedQty) > 0 " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.sbType =:sbType " +
		       "AND st.stuffTallyId IS NULL " +
		       "AND E.status <> 'D'")
		List<Object[]> searchSbNoForTallyBuffer(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("searchValue") String searchValue, 
		    @Param("profitcentreId") String profitcentreId,
		    @Param("stuffTallyId") String stuffTallyId,
		    @Param("sbType") String sbType
		);

	
	
	
	
	
		@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, g.onAccountOf, g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.gateInId, g.gateInType,g.containerHealth,g.customsSealNo "          
		        + "FROM ExportInventory E "
		        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' AND (g.stuffTallyId = '' OR g.stuffTallyId IS NULL) AND g.containerNo = E.containerNo "
		        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
		        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
		        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
		        + "AND (:searchValue IS NULL OR :searchValue = '' OR E.containerNo LIKE CONCAT('%', :searchValue, '%')) "
		        + "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') "
		        + "AND (E.stuffTallyId = '' OR E.stuffTallyId IS NULL) "
		        + "AND E.profitcentreId = :profitcentreId "
		        + "AND g.gateInType In ('Buffer','ONWH') "
		        + "AND E.status <> 'D'")
		List<Object[]> searchContainerNoForTallyBuffer(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
	
//	@Query(value = "SELECT NEW com.cwms.entities.ExportStuffTally(e.stuffTallyId, e.sbTransId, e.sbNo, e.movementType,"
//			+ "e.stuffTallyDate, e.sbDate, e.agentSealNo, e.vesselId, e.voyageNo,"
//			+ "e.rotationNo, e.rotationDate, e.terminal, e.pod, e.finalPod, e.containerNo,e.stuffId,"
//			+ "e.gateInId, e.containerSize, e.containerType, p4.partyName, p3.partyName,"
//			+ "e.stuffRequestQty, SUM(e.stuffedQty),(SELECT (COALESCE(SUM(car.noOfPackages), 0) - COALESCE(SUM(car.stuffedQty), 0)) "
//			+ "FROM ExportSbCargoEntry car WHERE car.companyId = e.companyId AND "
//			+ "car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), e.cargoWeight, e.totalCargoWeight,"
//			+ "e.tareWeight, p1.partyName, p2.partyName, e.commodity,"
//			+ "e.customsSealNo, e.viaNo, e.exporterName, e.consignee, e.fob,"
//			+ "e.status, e.stuffTallyWoTransId, e.stuffTallyCutWoTransDate, e.deliveryOrderNo,"
//			+ "e.stuffMode, v.vesselName, c.noOfPackages, c.stuffedQty, c.grossWeight,g.inGateInDate, c.cargoType,e.berthingDate,e.reworkFlag) "
//			+ "FROM ExportStuffTally e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
//			+ "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
//			+ "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId AND e.branchId = p1.branchId AND e.shippingAgent = p1.partyId "
//			+ "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId AND e.branchId = p2.branchId AND e.shippingLine = p2.partyId "
//			+ "LEFT OUTER JOIN Party p3 ON e.companyId = p3.companyId AND e.branchId = p3.branchId AND e.cha = p3.partyId "
//			+ "LEFT OUTER JOIN Party p4 ON e.companyId = p4.companyId AND e.branchId = p4.branchId AND e.onAccountOf = p4.partyId "
//			+ "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId AND e.branchId = v.branchId AND e.vesselId = v.vesselId "
//			+ "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
//			+ "WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.sbTransId=:trans and e.sbNo=:sb "
//			+ "group by e.stuffTallyId, e.sbTransId, e.sbNo, e.containerNo")
//	List<ExportStuffTally> getDataBySbNo(@Param("cid") String cid, @Param("bid") String bid,
//			@Param("trans") String trans, @Param("sb") String sb);
//	
	
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
	
	
	@Transactional
	@Modifying
	@Query(value = "Update ExportStuffTally e SET e.gateOutId=:id, e.gateOutDate = CURRENT_DATE where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and e.stuffTallyId = :stuffid and e.containerNo=:con")
	int updateGateOutNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("stuffid") String stuffid, @Param("con") String con);
	
	
	@Query(value = "select e.sbTransId,e.sbNo,e.sbDate,e.commodity,SUM(e.stuffedQty),SUM(e.cargoWeight),e.agentSealNo,e.customsSealNo,e.movementReqId,e.fob "
			+ "from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.gateOutId=:outId and e.containerNo=:con group by e.sbTransId,e.sbNo")
	List<Object[]> getStufftallyRecordsForPortContainerGateIn(@Param("cid") String cid, @Param("bid") String bid, 
			@Param("outId") String outId,@Param("con") String con);
	
	
	@Query(value = "select DISTINCT e.containerNo from ExportStuffTally e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') and (e.movementReqId is null OR e.movementReqId = '') "
			+ "and (e.gateOutId is null OR e.gateOutId = '') and (:val is null OR :val = '' OR e.containerNo LIKE CONCAT('%',:val,'%')) and e.movementType=:type")
	List<String> getRecordsForExportReworking(@Param("cid") String cid, @Param("bid") String bid,
			@Param("val") String val,@Param("type") String type);

	@Query(value = "select e.gateInId,g.inGateInDate,e.containerNo,e.containerSize,e.containerType,e.containerStatus,"
			+ "e.customsSealNo,p1.partyName,g.vehicleNo,COALESCE(SUM(e.areaReleased),0),COALESCE(SUM(e.stuffedQty),0),"
			+ "e.onAccountOf,p2.partyName,e.sbTransId,e.sbNo,e.commodity,e.cargoWeight,e.fob,e.shippingAgent "
			+ "from ExportStuffTally e LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId=g.gateInId "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.shippingAgent=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') and (e.movementReqId is null OR e.movementReqId = '') "
			+ "and (e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:val group by e.sbNo,e.sbTransId")
	List<Object[]> getRecordsForExportReworkingByContainerNo(@Param("cid") String cid, @Param("bid") String bid,
			@Param("val") String val);

	@Transactional
	@Modifying
	@Query(value="Update ExportStuffTally e SET e.reworkFlag='Y',e.reworkId=:id,e.reworkDate=CURRENT_DATE where e.companyId=:cid and "
			+ "e.branchId=:bid and e.status != 'D' "
			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') "
			+ "and (e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:val")
	int updateReworkId(@Param("cid") String cid, @Param("bid") String bid,@Param("id") String id,
			@Param("val") String val);
	
	
	@Query(value = "select e "
			+ "from ExportStuffTally e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and (e.reworkId is null OR e.reworkId = '') and (e.gatePassNo is null OR e.gatePassNo = '') and (e.movementReqId is null OR e.movementReqId = '') "
			+ "and (e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:val and e.sbNo=:sb and e.sbTransId=:trans")
	List<ExportStuffTally> getRecordsForExportReworkingByContainerNo1(@Param("cid") String cid, @Param("bid") String bid,
			@Param("val") String val,@Param("sb") String sb,@Param("trans") String trans);
	
	
	
	//Report
	
	
//		@Query(value="select e.stuffId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
//				+ "e.terminal,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
//				+ "p1.partyName,p2.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,e.stuffTallyWoTransId,"
//				+ "DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y'),DATE_FORMAT(e.cartingDate,'%d/%m/%Y'),e.sbNo,"
//				+ "DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,e.consignee,e.commodity,e.typeOfPackage,e.stuffRequestQty,"
//				+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.fob,e.tareWeight "
//				+ "from ExportStuffTally e "
//				+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
//				+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
//				+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingAgent=p2.partyId "
//				+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
//				+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
//				+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
//		List<Object[]> getDataForContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
//				@Param("con") String con);
//		
//		
//		@Query(value="select e.sbNo,e.viaNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),p4.partyName,v.vesselName,sb.noOfPackages,"
//				+ "p3.partyName,e.voyageNo,sb.grossWeight,e.consignee,e.pod,sb.cargoType,p1.partyName,e.terminal,e.commodity,"
//				+ "DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.rotationNo,e.typeOfPackage,e.yardLocation,e.yardBlock,e.blockCellNo,"
//				+ "e.stuffTallyWoTransId,DATE_FORMAT(e.stuffTallyCutWoTransDate,'%d/%m/%Y'),e.containerNo,e.containerSize,"
//				+ "e.containerType,e.agentSealNo,e.customsSealNo,COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,e.tareWeight,e.exporterName,"
//				+ "ROUND(e.cargoWeight+e.tareWeight,0) "
//				+ "from ExportStuffTally e "
//				+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
//				+ "and e.sbTransId=sb.sbTransId "
//				+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
//				+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
//				+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
//				+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.onAccountOf=p4.partyId "
//				+ "where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:id and e.sbNo=:sb and e.status = 'A' and "
//				+ "(e.reworkId is null OR e.reworkId = '') group by e.containerNo")
//		List<Object[]> getDataForSBWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
//				@Param("sb") String sb);
//		
//		
//		
//		
//		@Query(value="select e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
//				+ "e.pol,e.pod,e.finalPod,e.viaNo,e.voyageNo,v.vesselName,e.rotationNo,DATE_FORMAT(e.rotationDate,'%d/%m/%Y'),"
//				+ "p1.partyName,p3.partyName,e.agentSealNo,e.customsSealNo,p2.partyName,"
//				+ "e.sbNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.exporterName,s.consigneeName,e.commodity,sb.typeOfPackage,sb.noOfPackages,"
//				+ "COALESCE(SUM(e.stuffedQty),0),e.cargoWeight,sb.fob,e.tareWeight "
//				+ "from ExportStuffTally e "
//				+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
//				+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.cha=p1.partyId "
//				+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
//				+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shippingLine=p3.partyId "
//				+ "LEFT OUTER JOIN ExportSbCargoEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
//				+ "and e.sbTransId=sb.sbTransId "
//				+ "LEFT OUTER JOIN ExportSbEntry s ON e.companyId=s.companyId and e.branchId=s.branchId and e.sbNo=s.sbNo "
//				+ "and e.sbTransId=s.sbTransId "
//				+ "where e.companyId=:cid and e.branchId=:bid and e.stuffTallyId=:id and e.containerNo=:con and e.status = 'A' and "
//				+ "(e.reworkId is null OR e.reworkId = '') group by e.sbNo")
//		List<Object[]> getDataForBufferContWiseReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
//				@Param("con") String con);
}

