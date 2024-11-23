package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportStuffRequest;

public interface ExportStuffRequestRepo extends JpaRepository<ExportStuffRequest, String>
{	
	
	
	@Query("SELECT new com.cwms.entities.ExportStuffRequest(E.stuffReqId, E.sbTransId, E.stuffReqLineId, E.sbLineNo, "
	           + "E.sbNo, E.stuffTally, E.stuffTallyId) "
	           + "FROM ExportStuffRequest E "	           
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.sbNo = :sbNo "
	           + "AND E.status <> 'D'")
	    List<ExportStuffRequest> exportSearchMain(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo
	    );
	
	
	
	
	
	
	@Query("SELECT E.sbNo, E.sbTransId, E.srno, g.exporterId, g.exporterName, E.sbDate, E.stuffReqQty, E.commodity, E.noOfPackages, g.pod, E.grossWeight " +
		       "FROM ExportSbCargoEntry E " +
		       "LEFT JOIN ExportSbEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.sbNo AND g.sbTransId = E.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
		       "LEFT JOIN ExportStuffRequest st ON E.companyId = st.companyId AND E.branchId = st.branchId AND st.sbNo = E.sbNo AND st.sbTransId = E.sbTransId AND st.profitcentreId = E.profitcentreId AND st.status <> 'D' AND st.stuffReqId = :stuffReqId " +
		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
		       "AND (:searchValue IS NULL OR :searchValue = '' OR E.sbNo LIKE CONCAT('%', :searchValue, '%')) " +
		       "AND (g.holdStatus IS NULL OR g.holdStatus = '' OR g.holdStatus <> 'H') " +
		       "AND E.noOfPackages = E.cartedPackages AND (E.noOfPackages - E.stuffReqQty) > 0 " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND st.stuffReqId IS NULL " +  // Exclude entries with the specified stuffReqId
		       "AND E.status <> 'D'")
		List<Object[]> searchSbNoForStuffing(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("searchValue") String searchValue, 
		    @Param("profitcentreId") String profitcentreId,
		    @Param("stuffReqId") String stuffReqId
		);

	
	
	
		@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
			       "FROM ExportStuffRequest e " +
			       "WHERE e.companyId = :companyId " +
			       "AND e.branchId = :branchId " +
			       "AND e.sbNo = :sbNo " +
			       "AND e.sbTransId = :sbTransId " +
			       "AND e.profitcentreId = :profitcentreId " +
			       "AND e.status <> 'D' " +
			       "AND (:stuffReqLineId IS NULL OR e.stuffReqLineId <> :stuffReqLineId) " +
			       "AND (:stuffReqId IS NULL OR :stuffReqId = '' OR e.stuffReqId <> :stuffReqId)")
			boolean existsBySbNoForstuffing(
			    @Param("companyId") String companyId,
			    @Param("branchId") String branchId,
			    @Param("sbNo") String sbNo,
			    @Param("sbTransId") String sbTransId,
			    @Param("profitcentreId") String profitcentreId,
			    @Param("stuffReqId") String stuffReqId,
			    @Param("stuffReqLineId") Integer stuffReqLineId
			);



	
	
	@Query("SELECT COALESCE(MAX(E.stuffReqLineId), 0) FROM ExportStuffRequest E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitcentreId = :profitcentreId "
			+ "AND E.stuffReqId = :stuffReqId")
	int getMaxLineId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("stuffReqId") String stuffReqId);

	
	
	@Query("SELECT new com.cwms.entities.ExportStuffRequest(E.companyId, E.branchId, E.finYear, E.stuffReqId, "
		       + "E.sbTransId, E.stuffReqLineId, E.sbLineNo, E.profitcentreId, E.stuffReqDate, "
		       + "E.sbNo, E.sbDate, E.shift, E.stuffTally, E.stuffTallyId, E.totalCargoWeight, "
		       + "E.shippingAgent, E.shippingLine, E.exporterName, E.cargoDescription, E.onAccountOf, "
		       + "E.vesselId, E.viaNo, E.voyageNo, E.terminal, E.coverDetails, E.coverDate, "
		       + "E.berthingDate, E.gateOpenDate, E.gateInId, E.agentSealNo, E.tareWeight, "
		       + "E.containerSize, E.containerType, E.pod, E.comments, E.typeOfPackage, "
		       + "E.noOfPackages, E.noOfPackagesStuffed, ec.stuffReqQty, E.cbm, "
		       + "E.containerNo, E.currentLocation, E.periodFrom, E.containerHealth, "
		       + "E.cargoWeight, E.status, E.createdBy, E.createdDate, E.editedBy, "
		       + "E.editedDate, E.approvedBy, E.approvedDate, E.labour, E.fk3MT, E.fk5MT, "
		       + "E.fk10MT, E.hydra12MT, E.crane, E.ssrTransId, E.deliveryOrderNo, "
		       + "E.rotationNo, E.rotationDate, g.inGateInDate, psl.partyName, "
		       + "psa.partyName, oa.partyName, v.vesselName) "
		       + "FROM ExportStuffRequest E "
		       + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.shippingAgent = psa.partyId AND psa.status <> 'D' "
		       + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
		       + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "
		       + "LEFT JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId AND v.status <> 'D' "
		       + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND ec.status <> 'D' "
		       + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.onAccountOf = g.onAccountOf AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' AND E.gateInId = g.gateInId "
		       + "WHERE E.companyId = :companyId "
		       + "AND E.branchId = :branchId "
		       + "AND E.containerNo = :containerNo "
		       + "AND E.status <> 'D' "
		       + "AND E.stuffReqId = (SELECT MAX(subE.stuffReqId) "
		       + "                    FROM ExportStuffRequest subE "
		       + "                    WHERE subE.companyId = :companyId "
		       + "                    AND subE.branchId = :branchId "
		       + "                    AND subE.containerNo = :containerNo) "
		       + "ORDER BY E.createdDate DESC")
		List<ExportStuffRequest> findLatestStuffingRecordsByContainerNo(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("containerNo") String containerNo
		);

	
	
	
	@Query("SELECT new com.cwms.entities.ExportStuffRequest(E.companyId, E.branchId, E.finYear, E.stuffReqId, "
	           + "E.sbTransId, E.stuffReqLineId, E.sbLineNo, E.profitcentreId, E.stuffReqDate, "
	           + "E.sbNo, E.sbDate, E.shift, E.stuffTally, E.stuffTallyId, E.totalCargoWeight, "
	           + "E.shippingAgent, E.shippingLine, E.exporterName, E.cargoDescription, E.onAccountOf, "
	           + "E.vesselId, E.viaNo, E.voyageNo, E.terminal, E.coverDetails, E.coverDate, "
	           + "E.berthingDate, E.gateOpenDate, E.gateInId, E.agentSealNo, E.tareWeight, "
	           + "E.containerSize, E.containerType, E.pod, E.comments, E.typeOfPackage, "
	           + "E.noOfPackages, E.noOfPackagesStuffed, ec.stuffReqQty, E.cbm, "
	           + "E.containerNo, E.currentLocation, E.periodFrom, E.containerHealth, "
	           + "E.cargoWeight, E.status, E.createdBy, E.createdDate, E.editedBy, "
	           + "E.editedDate, E.approvedBy, E.approvedDate, E.labour, E.fk3MT, E.fk5MT, "
	           + "E.fk10MT, E.hydra12MT, E.crane, E.ssrTransId, E.deliveryOrderNo, "
	           + "E.rotationNo, E.rotationDate, g.inGateInDate, psl.partyName, "
	           + "psa.partyName, oa.partyName, v.vesselName) "
	           + "FROM ExportStuffRequest E "
	           + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.shippingAgent = psa.partyId AND psa.status <> 'D' "
	           + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
	           + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "          	           
	           + "LEFT JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId AND v.status <> 'D' "          	           
	           + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND ec.status <> 'D' "
	           + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.onAccountOf = g.onAccountOf AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' and E.gateInId = g.gateInId "
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.stuffReqId = :stuffReqId "
	           + "AND E.status <> 'D'")
	    List<ExportStuffRequest> searchStuffingContainerWiseSaved(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("stuffReqId") String stuffReqId
	    );
	
	
	
	
	
	
	
	
	
	
//	@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, g.onAccountOf, g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.gateInId "          
//	        + "FROM ExportInventory E "
//	        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' AND (g.stuffRequestId = '' OR g.stuffRequestId IS NULL) AND g.containerNo = E.containerNo "
//	        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
//	        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
//	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
//	        + "AND (:searchValue IS NULL OR :searchValue = '' OR E.containerNo LIKE CONCAT('%', :searchValue, '%')) "
//	        + "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') "
//	        + "AND (E.stuffReqId = '' OR E.stuffReqId IS NULL) "
//	        + "AND E.profitcentreId = :profitcentreId "
//	        + "AND E.status <> 'D'")
//	List<Object[]> searchContainerNoForStuffingContainerWise(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);

	
	@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, g.onAccountOf, g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.gateInId "          
	        + "FROM ExportInventory E "
	        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' AND (g.stuffRequestId = '' OR g.stuffRequestId IS NULL) AND g.containerNo = E.containerNo "
	        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
	        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') "
	        + "AND (E.stuffReqId = '' OR E.stuffReqId IS NULL) "
	        + "AND E.profitcentreId = :profitcentreId "
	        + "AND E.containerNo = :searchValue "
	        + "AND E.status <> 'D'")
	List<Object[]> searchContainerNoForStuffingContainerWise(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT new com.cwms.entities.ExportStuffRequest(E.companyId, E.branchId, E.finYear, E.stuffReqId, "
	           + "E.sbTransId, E.stuffReqLineId, E.sbLineNo, E.profitcentreId, E.stuffReqDate, "
	           + "E.sbNo, E.sbDate, E.shift, E.stuffTally, E.stuffTallyId, E.totalCargoWeight, "
	           + "E.shippingAgent, E.shippingLine, E.exporterName, E.cargoDescription, E.onAccountOf, "
	           + "E.vesselId, E.viaNo, E.voyageNo, E.terminal, E.coverDetails, E.coverDate, "
	           + "E.berthingDate, E.gateOpenDate, E.gateInId, E.agentSealNo, E.tareWeight, "
	           + "E.containerSize, E.containerType, E.pod, E.comments, E.typeOfPackage, "
	           + "E.noOfPackages, E.noOfPackagesStuffed, ec.stuffReqQty, E.cbm, "
	           + "E.containerNo, E.currentLocation, E.periodFrom, E.containerHealth, "
	           + "E.cargoWeight, E.status, E.createdBy, E.createdDate, E.editedBy, "
	           + "E.editedDate, E.approvedBy, E.approvedDate, E.labour, E.fk3MT, E.fk5MT, "
	           + "E.fk10MT, E.hydra12MT, E.crane, E.ssrTransId, E.deliveryOrderNo, "
	           + "E.rotationNo, E.rotationDate, g.inGateInDate, psl.partyName, "
	           + "psa.partyName, oa.partyName, v.vesselName) "
	           + "FROM ExportStuffRequest E "
	           + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.shippingAgent = psa.partyId AND psa.status <> 'D' "
	           + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
	           + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "          	           
	           + "LEFT JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId AND v.status <> 'D' "          	           
	           + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND ec.status <> 'D' "
	           + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.onAccountOf = g.onAccountOf AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' and E.gateInId = g.gateInId "
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.sbNo = :sbNo "
	           + "AND E.status <> 'D'")
	    List<ExportStuffRequest> searchContainerNoForStuffingSavedNew(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo
	    );
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT e "
	        + "FROM ExportStuffRequest e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.sbNo = :sbNo " 
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.stuffReqId = :stuffReqId " 
	        + "AND e.stuffReqLineId = :stuffReqLineId "
	        + "AND e.status <> 'D'")
	ExportStuffRequest getDataForUpdateEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                    @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId,
	                                    @Param("stuffReqId") String stuffReqId, @Param("stuffReqLineId") int stuffReqLineId);

	
	
	
//	@Query("SELECT E.sbNo, E.sbDate, E.sbTransId, E.hSbTransId, E.exporterId, E.exporterName, E.totalPackages, E.pod, E.onAccountOf, E.srNo, "
//            + "ec.commodity, ec.typeOfPackage, g.containerNo, g.containerSize, g.containerType, g.containerSealNo, "
//            + "g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.sa, psa.partyName, g.sl, psl.partyName, oa.partyName, E.pol, g.gateInId, ec.grossWeight, ec.stuffedQty "
//        + "FROM ExportSbEntry E "
//        + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND E.hSbTransId = ec.hSbTransId AND ec.status <> 'D' "
//        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.onAccountOf = g.onAccountOf AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' and (stuffRequestId = '' OR stuffRequestId IS NULL) "
//        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
//        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
//        + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "
//        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
//        + "AND E.sbNo = :sbNo "	  
//        + "AND ec.noOfPackages = ec.cartedPackages AND (E.totalPackages - ec.stuffedQty) > 0 "
//        + "AND E.status <> 'D'")
//List<Object[]> searchContainerNoForStuffingNew(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);

	
	
	@Query("SELECT E.sbNo, E.sbDate, E.sbTransId, E.hSbTransId, E.exporterId, E.exporterName, E.totalPackages, E.pod, E.onAccountOf, E.srNo, "
            + "ec.commodity, ec.typeOfPackage, g.containerNo, g.containerSize, g.containerType, g.containerSealNo, "
            + "g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.sa, psa.partyName, g.sl, psl.partyName, oa.partyName, E.pol, g.gateInId, ec.grossWeight, ec.stuffReqQty "
        + "FROM ExportSbEntry E "
        + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND E.hSbTransId = ec.hSbTransId AND ec.status <> 'D' "
        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.onAccountOf = g.onAccountOf AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' and (stuffRequestId = '' OR stuffRequestId IS NULL) "
        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
        + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "
        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
        + "AND E.sbNo = :sbNo "	  
        + "AND ec.noOfPackages = ec.cartedPackages AND (E.totalPackages - (ec.stuffReqQty * 1.0)) > 0 "
        + "AND E.status <> 'D'")
List<Object[]> searchContainerNoForStuffingNew(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);

	
	
	
	
	
	
	@Query("SELECT E FROM ExportStuffRequest E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.sbNo = :sbNo "	      
	        + "AND E.status <> 'D'")
	List<ExportStuffRequest> searchContainerNoForStuffingSaved(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	
	
	
	
	
	
	
	//Sanket
	
	
	@Query(value="select Distinct e.stuffReqId from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(e.stuffTallyId is null OR e.stuffTallyId = '') and (:id is null OR :id = '' OR e.stuffReqId LIKE CONCAT('%',:id,'%')) "
			+ "and (COALESCE(e.noOfPackagesStuffed,0)-COALESCE(e.stuffedQty,0) > 0)")
	List<String> getDataBystuffReqId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select NEW com.cwms.entities.ExportStuffRequest(e.stuffReqId, e.sbTransId, e.stuffReqLineId, e.sbLineNo,"
			+ "pr.profitcentreDesc, e.stuffReqDate, e.sbNo, e.sbDate, e.stuffTally, e.totalCargoWeight, p1.partyName,"
			+ "p2.partyName, e.exporterName, e.cargoDescription, e.onAccountOf, e.vesselId,"
			+ "e.viaNo, e.voyageNo, e.terminal, e.berthingDate, e.gateOpenDate, e.gateInId,"
			+ "e.agentSealNo, e.tareWeight, e.containerSize, e.containerType, c.cartedPackages, SUM(car.areaOccupied),"
			+ "e.pod, e.typeOfPackage, e.noOfPackages,"
			+ "(e.noOfPackagesStuffed - COALESCE(e.stuffedQty,0)), e.containerNo, e.currentLocation, e.periodFrom,"
			+ "e.containerHealth, e.cargoWeight, e.deliveryOrderNo, e.rotationNo,"
			+ "e.rotationDate, v.vesselName, sb.consigneeName, c.fob, e.noOfPackagesStuffed) "
			+ "from ExportStuffRequest e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
			+ "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
			+ "and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.shippingAgent = p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingLine = p2.partyId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId = v.vesselId "
			+ "LEFT OUTER JOIN ExportCarting car ON e.companyId=car.companyId and e.branchId=car.branchId and e.sbTransId=car.sbTransId and e.sbNo=car.sbNo "
			+ "LEFT OUTER JOIN Profitcentre pr ON e.companyId=pr.companyId and e.branchId=pr.branchId and e.profitcentreId = pr.profitcentreId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.stuffReqId=:id and (e.stuffTallyId is null OR e.stuffTallyId = '')  group by e.sbTransId,e.sbNo")
	List<ExportStuffRequest> getDataByStuffReqId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
//	@Query(value = "select NEW com.cwms.entities.ExportStuffRequest(e.stuffReqId, e.sbTransId, e.stuffReqLineId, e.sbLineNo, "
//	        + "pr.profitcentreDesc, e.stuffReqDate, e.sbNo, e.sbDate, e.stuffTally, e.totalCargoWeight, p1.partyName, "
//	        + "p2.partyName, e.exporterName, e.cargoDescription, e.onAccountOf, e.vesselId, "
//	        + "e.viaNo, e.voyageNo, e.terminal, e.berthingDate, e.gateOpenDate, e.gateInId, "
//	        + "e.agentSealNo, e.tareWeight, e.containerSize, e.containerType, c.cartedPackages, "
//	        + "(SELECT SUM(car.areaOccupied) FROM ExportCarting car WHERE car.companyId = e.companyId AND car.branchId = e.branchId AND car.sbTransId = e.sbTransId AND car.sbNo = e.sbNo), "
//	        + "e.pod, e.typeOfPackage, e.noOfPackages, "
//	        + "e.noOfPackagesStuffed, e.containerNo, e.currentLocation, e.periodFrom, "
//	        + "e.containerHealth, e.cargoWeight, e.deliveryOrderNo, e.rotationNo, "
//	        + "e.rotationDate, v.vesselName, sb.consigneeName, c.fob) "
//	        + "from ExportStuffRequest e "
//	        + "LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId = c.companyId "
//	        + "and e.branchId = c.branchId and e.sbNo = c.sbNo and e.sbTransId = c.sbTransId and e.sbLineNo = c.sbLineNo "
//	        + "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId = sb.companyId and e.branchId = sb.branchId and e.sbNo = sb.sbNo "
//	        + "and e.sbTransId = sb.sbTransId "
//	        + "LEFT OUTER JOIN Party p1 ON e.companyId = p1.companyId and e.branchId = p1.branchId and e.shippingAgent = p1.partyId "
//	        + "LEFT OUTER JOIN Party p2 ON e.companyId = p2.companyId and e.branchId = p2.branchId and e.shippingLine = p2.partyId "
//	        + "LEFT OUTER JOIN Vessel v ON e.companyId = v.companyId and e.branchId = v.branchId and e.vesselId = v.vesselId "
//	        + "LEFT OUTER JOIN Profitcentre pr ON e.companyId = pr.companyId and e.branchId = pr.branchId and e.profitcentreId = pr.profitcentreId "
//	        + "where e.companyId = :cid and e.branchId = :bid and e.status != 'D' and "
//	        + "e.stuffReqId = :id and (e.stuffTallyId is null OR e.stuffTallyId = '')")
//	List<ExportStuffRequest> getDataByStuffReqId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);

	
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqLineId=:line and e.status != 'D' and (e.stuffTallyId is null OR e.stuffTallyId = '')")
	ExportStuffRequest getDataBySbNoSbTransAndStuffReqLineId(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("line") int line);
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqId=:con and e.status != 'D' and (e.stuffTallyId is null OR e.stuffTallyId = '')")
	ExportStuffRequest getDataBySbNoSbTransAndStuffReqLineId2(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("con") String con);
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqId=:con and e.status != 'D'")
	ExportStuffRequest getDataBySbNoSbTransAndStuffReqLineId3(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("con") String con);
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqLineId=:line and e.status != 'D'")
	ExportStuffRequest getDataBySbNoSbTransAndStuffReqLineId1(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("line") int line);
	
	
	@Query(value = "select distinct e.sbNo,e.sbTransId from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and "
			+ "(:sb is null OR :sb = '' OR e.sbNo LIKE CONCAT ('%',:sb,'%')) and e.status != 'D'")
	List<Object[]> getSbNoForTally(@Param("cid") String cid,@Param("bid") String bid,@Param("sb") String sb);
	
	@Query(value = "select distinct e.sbNo,e.sbTransId from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and "
			+ "(:con is null OR :con = '' OR  e.containerNo LIKE CONCAT ('%',:con,'%')) and e.status != 'D'")
	List<Object[]> getSbNoForTally1(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query(value="select NEW com.cwms.entities.ExportStuffRequest(e.sbTransId, e.profitcentreId, e.sbNo, e.sbDate, p1.partyName,"
			+ "p2.partyName, e.cargoDescription, p3.partyName, e.vesselId, e.viaNo,"
			+ "e.voyageNo, e.terminal, e.berthingDate, e.gateOpenDate, e.gateInId, e.agentSealNo,"
			+ "e.tareWeight, e.containerSize, e.containerType, e.pod, e.noOfPackagesStuffed,"
			+ "e.containerNo, e.cargoWeight, e.status, e.deliveryOrderNo, e.rotationNo,"
			+ "e.rotationDate, g.inGateInDate, v.vesselName, sb.consigneeName, c.fob, p4.partyName, e.exporterName,"
			+ "c.noOfPackages,c.grossWeight, c.stuffedQty, e.stuffReqId, c.cargoType) "
			+ "from ExportStuffRequest e LEFT OUTER JOIN ExportSbCargoEntry c ON e.companyId=c.companyId "
			+ "and e.branchId=c.branchId and e.sbNo=c.sbNo and e.sbTransId=c.sbTransId "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.shippingAgent = p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingLine = p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.onAccountOf = p3.partyId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId = v.vesselId "
			+ "LEFT OUTER JOIN GateIn g ON e.companyId=g.companyId and e.branchId=g.branchId and e.gateInId = g.gateInId "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo "
			+ "and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN Party p4 ON sb.companyId=p4.companyId and sb.branchId=p4.branchId and sb.cha = p4.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and e.sbNo=:sb and e.status != 'D' "
			+ "and (e.stuffTallyId is null OR e.stuffTallyId = '')")
	List<ExportStuffRequest> getDataForSbWiseStuff(@Param("cid") String cid,@Param("bid") String bid,@Param("sb") String sb,
			@Param("sbtrans") String sbtrans);
	
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqId=:id and e.status != 'D' and (e.stuffTallyId is null OR e.stuffTallyId = '')")
	ExportStuffRequest getDataBySbNoSbTransAndStuffReqId(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("id") String id);
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqId!=:id and e.status != 'D' and (e.noOfPackagesStuffed - e.stuffedQty) > 0 order by "
			+ "(e.noOfPackagesStuffed - e.stuffedQty) asc")
	List<ExportStuffRequest> getDataBySbNoSbTrans(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("id") String id);
	
	
	@Query(value="select e from ExportStuffRequest e where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbtrans and "
			+ "e.sbNo=:sb and e.stuffReqId=:id and e.status != 'D'")
	ExportStuffRequest getDataBySbNoSbTransAndStuffReqId1(@Param("cid") String cid,@Param("bid") String bid,
			@Param("sbtrans") String sbtrans,@Param("sb") String sb,@Param("id") String id);
}