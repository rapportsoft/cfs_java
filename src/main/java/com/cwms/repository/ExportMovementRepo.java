package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportMovement;

public interface ExportMovementRepo extends JpaRepository<ExportMovement, String>{
	
	
	
	@Query(value = "SELECT DISTINCT e.containerNo, e.movementReqId " +
            "FROM ExportMovement e " +
            "WHERE e.companyId = :cid " +
            "AND e.branchId = :bid " +
            "AND e.status != 'D' " +
            "AND (e.gateOutId IS NULL OR e.gateOutId = '') " +
            "AND (e.gatePassNo IS NULL OR e.gatePassNo = '') " +
            "AND (:val IS NULL OR :val = '' OR e.containerNo LIKE CONCAT('%', :val, '%'))")
List<Object[]> getDataForGatePass(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

	
	
	@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, st.onAccountOf, st.viaNo, st.voyageNo ,E.vesselId, vs.vesselName, st.stuffTallyId, E.gateInId, st.agentSealNo, st.customsSealNo, st.totalGrossWeight, st.sbNo, st.pod, st.pol, st.sbDate, st.stuffId, st.stuffDate,st.stuffTallyDate, st.stuffTallyLineId "          
	        + "FROM ExportInventory E "
	        + "LEFT JOIN Vessel vs ON E.companyId = vs.companyId AND E.branchId = vs.branchId AND E.vesselId = vs.vesselId AND vs.status <> 'D' "
	        + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.sa = psa.partyId AND psa.status <> 'D' "
	        + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.sl = psl.partyId AND psl.status <> 'D' "
	        + "LEFT JOIN ExportStuffTally st ON E.companyId = st.companyId AND E.branchId = st.branchId AND st.profitcentreId = E.profitcentreId AND E.stuffTallyId = st.stuffTallyId AND st.status <> 'D' AND (st.movementReqId = '' OR st.movementReqId IS NULL) "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') "
	        + "AND (E.stuffTallyId <> '' OR E.stuffTallyId IS NOT NULL) "
	        + "AND (E.movementReqId = '' OR E.movementReqId IS NULL) "
	        + "AND (E.gateOutId = '' OR E.gateOutId IS NULL) "
	        + "AND E.profitcentreId = :profitcentreId "
	        + "AND st.movementType = :movementType "
	        + "AND E.containerNo LIKE %:searchValue% "
	        + "AND E.status <> 'D'")
	List<Object[]> searchContainerNoForMovement(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId , @Param("movementType") String movementType);

	
	
	
	@Query(value = "select c.profitcentreId, c.movementReqId, c.movementReqLineId, c.movementReqDate, c.movementOrderDate, c.containerNo, c.shippingAgent, c.shippingLine, psa.partyName, psl.partyName, c.voyageNo, c.viaNo, c.status "
			+ "from ExportMovement c "
			+ "LEFT JOIN Party psa ON c.companyId = psa.companyId AND c.branchId = psa.branchId AND c.shippingAgent = psa.partyId AND psa.status <> 'D' "
		    + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
			+ "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' "
			+ "and (:searchValue is null OR :searchValue = '' OR c.movementReqId LIKE %:searchValue% OR c.movementReqDate LIKE %:searchValue% OR c.containerNo LIKE %:searchValue% OR c.voyageNo LIKE %:searchValue% OR c.viaNo LIKE %:searchValue%) "
			+ "ORDER BY c.movementOrderDate DESC")
	List<Object[]> getMovementEntriesToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("searchValue") String searchValue);
	
	
	
	
	
	
	@Query("SELECT new ExportMovement(e.companyId, e.branchId, e.finYear, e.movementReqId, e.movementReqLineId, "
	        + "e.movementReqDate, e.movementOrderDate, e.stuffTallyId, e.movReqType, e.stuffTallyLineId, "
	        + "e.profitcentreId, e.stuffTallyDate, e.stuffId, e.stuffDate, e.sbNo, e.sbTransId, e.sbDate, e.shift, "
	        + "e.agentSealNo, e.vesselId, e.voyageNo, e.pol, e.pod, e.containerNo, e.containerSize, e.containerType, "
	        + "e.containerStatus, e.periodFrom, e.accSrNo, e.onAccountOf, e.totalCargoWt, e.grossWeight, "
	        + "e.tareWeight, e.shippingAgent, e.shippingLine, e.customsSealNo, e.viaNo, e.holdingAgent, "
	        + "e.holdingAgentName, e.holdDate, e.releaseDate, e.holdRemarks, e.gatePassNo, e.addServices, "
	        + "e.typeOfContainer, e.gateOutId, e.gateOutDate, e.impSrNo, e.billingParty, e.igst, e.cgst, e.sgst, "
	        + "e.status, e.comments, e.createdBy, e.createdDate, e.editedBy, e.editedDate, e.approvedBy, "
	        + "e.approvedDate, e.currentLocation, e.othPartyId, e.invoiceAssessed, e.assessmentId, e.invoiceNo, "
	        + "e.invoiceDate, e.creditType, e.invoiceCategory, e.billAmt, e.invoiceAmt, e.forceEntryFlag, "
	        + "e.forceEntryDate, e.forceEntryApproval, e.forceEntryRemarks, e.sSRTransId, e.forceEntryFlagInv, "
	        + "e.trailerType, psl.partyName, psa.partyName, vs.vesselName, st.gateInId) "
	        + "FROM ExportMovement e "
	        + "LEFT JOIN ExportStuffTally st ON e.companyId = st.companyId AND e.branchId = st.branchId AND st.profitcentreId = e.profitcentreId AND e.stuffTallyId = st.stuffTallyId AND st.stuffTallyLineId = 1 AND st.status <> 'D' "
	        + "LEFT JOIN Vessel vs ON e.companyId = vs.companyId AND e.branchId = vs.branchId AND e.vesselId = vs.vesselId AND vs.status <> 'D' "
	        + "LEFT JOIN Party psa ON e.companyId = psa.companyId AND e.branchId = psa.branchId AND e.shippingAgent = psa.partyId AND psa.status <> 'D' "
	        + "LEFT JOIN Party psl ON e.companyId = psl.companyId AND e.branchId = psl.branchId AND e.shippingLine = psl.partyId AND psl.status <> 'D' "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.profitcentreId = :profitcentreId AND e.movementReqId = :movementReqId "
	        + "AND e.status <> 'D'")
	List<ExportMovement> getSelectedMovementEntry(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
//	    @Param("containerNo") String containerNo, 
	    @Param("profitcentreId") String profitcentreId,
	    @Param("movementReqId") String movementReqId
	);

	
	
	
	@Query("SELECT e "
	        + "FROM ExportMovement e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.containerNo = :containerNo " 
	        + "AND e.profitcentreId = :profitcentreId "
	        + "AND e.movementReqId = :movementReqId " 
	        + "AND e.movementReqLineId = :movementReqLineId "
	        + "AND e.status <> 'D'")
	ExportMovement getDataForUpdate(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                    @Param("containerNo") String containerNo, @Param("profitcentreId") String profitcentreId,
	                                    @Param("movementReqId") String movementReqId, @Param("movementReqLineId") String movementReqLineId);

	
	
	@Query("SELECT COALESCE(MAX(CAST(E.movementReqLineId AS int)), 0) FROM ExportMovement E "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitcentreId = :profitcentreId "
	        + "AND E.movementReqId = :movementReqId")
	int getMaxLineId(@Param("companyId") String companyId, 
	                 @Param("branchId") String branchId,
	                 @Param("profitcentreId") String profitcentreId, 
	                 @Param("movementReqId") String movementReqId);


	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM ExportMovement e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.containerNo = :containerNo " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.status <> 'D' " +
		       "AND (:movementReqLineId IS NULL OR e.movementReqLineId <> :movementReqLineId) " +
		       "AND (:movementReqId IS NULL OR :movementReqId = '' OR e.movementReqId <> :movementReqId)")
		boolean existsByContainerNoMovement(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("containerNo") String containerNo,
		    @Param("movementReqId") String movementReqId,
		    @Param("movementReqLineId") String movementReqLineId
		);


	
	
	
//	
//	@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, st.onAccountOf, st.viaNo, st.voyageNo ,E.vesselId, vs.vesselName, st.stuffTallyId, E.gateInId, st.agentSealNo, st.customsSealNo, st.totalGrossWeight, st.sbNo, st.pod, st.pol, st.sbDate, st.stuffId, st.stuffDate,st.stuffTallyDate, st.stuffTallyLineId "          
//	        + "FROM ExportInventory E "
//	        + "LEFT JOIN Vessel vs ON E.companyId = vs.companyId AND E.branchId = vs.branchId AND E.vesselId = vs.vesselId AND vs.status <> 'D' "
//	        + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.sa = psa.partyId AND psa.status <> 'D' "
//	        + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.sl = psl.partyId AND psl.status <> 'D' "
//	        + "LEFT JOIN ExportStuffTally st ON E.companyId = st.companyId AND E.branchId = st.branchId AND st.profitcentreId = E.profitcentreId AND E.stuffTallyId = st.stuffTallyId AND st.status <> 'D' AND (st.movementReqId = '' OR st.movementReqId IS NULL) "
//	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
//	        + "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') "
//	        + "AND (E.stuffTallyId <> '' OR E.stuffTallyId IS NOT NULL) "
//	        + "AND (E.movementReqId = '' OR E.movementReqId IS NULL) "
//	        + "AND (E.gateOutId = '' OR E.gateOutId IS NULL) "
//	        + "AND E.profitcentreId = :profitcentreId "
////	        + "AND E.containerNo = :searchValue "
//	        + "AND E.containerNo LIKE %:searchValue% "
//	        + "AND E.status <> 'D'")
//	List<Object[]> searchContainerNoForMovement(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);

//	@Query(value="select distinct e.containerNo,e.movementReqId from ExportMovement e where e.companyId=:cid and e.branchId=:bid "
//			+ "and e.status != 'D' and (e.gateOutId = '' OR e.gateOutId is null) and (:val = '' OR :val is null OR e.containerNo "
//			+ "LIKE CONCAT ('%',:val,'%'))")
//	List<Object[]> getDataForGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);


	@Query(value="select NEW com.cwms.entities.ExportMovement(e.movementReqId, e.movementReqLineId, e.sbNo, sb.sbTransId,"
			+ "e.sbDate, e.agentSealNo, e.pol, e.pod, e.containerNo, e.containerSize,"
			+ "e.containerType, e.containerStatus, e.grossWeight, e.customsSealNo,"
			+ "e.viaNo, sb.cha,p.partyName) "
			+ "from ExportMovement e LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId "
			+ "and e.sbNo=sb.sbNo "
			+ "LEFT OUTER JOIN Party p ON sb.companyId=p.companyId and sb.branchId=p.branchId and sb.cha=p.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.movementReqId=:reqId "
			+ "and e.containerNo=:con and e.status != 'D' and (e.gateOutId = '' OR e.gateOutId is null)")
	ExportMovement getSingleDataForGatePass(@Param("cid") String cid,@Param("bid") String bid,@Param("reqId") String reqId,
			@Param("con") String con);
	
	
	@Query(value="select e from ExportMovement e where e.companyId=:cid and e.branchId=:bid and e.movementReqId=:reqId "
			+ "and e.containerNo=:con and e.status != 'D' and (e.gateOutId = '' OR e.gateOutId is null)")
	ExportMovement getSingleDataForGatePass1(@Param("cid") String cid,@Param("bid") String bid,@Param("reqId") String reqId,
			@Param("con") String con);
	
}