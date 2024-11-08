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

	
	
	
	@Query("SELECT E.sbNo, E.sbDate, E.sbTransId, E.hSbTransId, E.exporterId, E.exporterName, E.totalPackages, E.pod, E.onAccountOf, E.srNo, "
            + "ec.commodity, ec.typeOfPackage, g.containerNo, g.containerSize, g.containerType, g.containerSealNo, "
            + "g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.sa, psa.partyName, g.sl, psl.partyName, oa.partyName, E.pol, g.gateInId, ec.grossWeight, ec.stuffedQty "
        + "FROM ExportSbEntry E "
        + "LEFT JOIN ExportSbCargoEntry ec ON E.companyId = ec.companyId AND E.branchId = ec.branchId AND E.sbNo = ec.sbNo AND E.sbTransId = ec.sbTransId AND E.hSbTransId = ec.hSbTransId AND ec.status <> 'D' "
        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.onAccountOf = g.onAccountOf AND g.containerStatus = 'MTY' AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' and (stuffRequestId = '' OR stuffRequestId IS NULL) "
        + "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId AND g.sa = psa.partyId AND psa.status <> 'D' "
        + "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId AND g.sl = psl.partyId AND psl.status <> 'D' "
        + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "
        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
        + "AND E.sbNo = :sbNo "	  
        + "AND ec.noOfPackages = ec.cartedPackages AND (E.totalPackages - E.stuffedQty) > 0 "
        + "AND E.status <> 'D'")
List<Object[]> searchContainerNoForStuffingNew(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);

	
	
	
	
	
	
	
	
	
	@Query("SELECT E FROM ExportStuffRequest E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.sbNo = :sbNo "	      
	        + "AND E.status <> 'D'")
	List<ExportStuffRequest> searchContainerNoForStuffingSaved(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	
	
}