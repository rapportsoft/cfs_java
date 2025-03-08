package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.StuffRequestHub;
import jakarta.transaction.Transactional;

public interface HubStuffRequestRepo extends JpaRepository<StuffRequestHub, String>{	
	
	@Query(value = "select c.stuffReqId, DATE_FORMAT(c.stuffReqDate, '%d %b %Y'), c.profitCentreId, po.profitcentreDesc, psa.partyName, psl.partyName, c.containerNo, c.status "
	        + "from StuffRequestHub c "
	        + "Left Join Profitcentre po on c.companyId = po.companyId and c.branchId = po.branchId and c.profitCentreId = po.profitcentreId and po.status != 'D' "
	        + "LEFT JOIN Party psa ON c.companyId = psa.companyId AND c.branchId = psa.branchId AND c.shippingAgent = psa.partyId AND psa.status <> 'D' "
	        + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' " + "AND c.profitCentreId = :profitcentreId "
	        + "and (:searchValue is null OR :searchValue = '' OR c.stuffReqId LIKE %:searchValue% OR psa.partyName LIKE %:searchValue% OR psl.partyName LIKE %:searchValue% OR c.containerNo LIKE %:searchValue%) "
	        + "ORDER BY c.stuffReqDate DESC")
	List<Object[]> getStuffingEntriesToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId,
											  @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);
	
	
	
	
	
	@Query("SELECT new com.cwms.entities.StuffRequestHub(E.companyId, E.branchId, E.finYear, E.profitCentreId, E.stuffReqId, "
	           + "E.stuffReqDate, E.shift, E.status, E.createdBy, E.containerNo, E.containerSize, E.containerType, "
	           + "E.containerHealth, E.gateInId, E.vesselId, E.viaNo, E.voyageNo, v.vesselName, E.terminal, psa.partyName, "
	           + "psl.partyName, E.onAccountOf, oa.partyName, E.destination, E.pod, E.podDesc, E.customSealNo, E.agentSealNo, "
	           + "E.movementOrderDate, E.placementDate, E.stuffDate, E.beginDateTime, E.endDateTime, E.stuffingLocation, "
	           + "E.movementType, E.rotationNo, E.rotationDate, E.holdStatus, E.mtyCountWt, "
	           + "E.sbNo, E.sbLineNo, E.exporterName, E.cha, cha.partyName, "
	           + "E.cargoDescription, E.comments, "
	           + "E.commodityCode, E.commodityDesc, E.noOfPackages, E.noOfPackagesStuffed, "
	           + "E.cargoWeight, E.totalCargoWeight, "
	           + "E.smtpFlag, hub.stuffReqQty, hub.stuffReqWeight, E.sbTransId, E.stuffReqLineId, E.shippingAgent, E.shippingLine, E.tareWeight) "
	           + "FROM StuffRequestHub E "
	           + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.shippingAgent = psa.partyId AND psa.status <> 'D' "
	           + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
	           + "LEFT JOIN Party oa ON E.companyId = oa.companyId AND E.branchId = oa.branchId AND E.onAccountOf = oa.partyId AND oa.status <> 'D' "           
	           + "LEFT JOIN Party cha ON E.companyId = cha.companyId AND E.branchId = cha.branchId AND E.cha = cha.partyId AND cha.status <> 'D' "           
	           + "LEFT JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId AND v.status <> 'D' "           
	           + "LEFT JOIN HubDocument hub ON E.companyId = hub.companyId AND E.branchId = hub.branchId AND E.sbTransId = hub.hubTransId "
	           + "AND E.sbNo = hub.igmNo AND E.sbLineNo = hub.igmLineNo AND hub.status <> 'D' "
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.stuffReqId = :stuffReqId "
	           + "AND E.status <> 'D'")
	List<StuffRequestHub> searchStuffingContainerWiseSaved(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("stuffReqId") String stuffReqId
	);

	
	
	
	
	@Modifying
    @Transactional
    @Query("UPDATE HubDocument e SET e.stuffReqQty = e.stuffReqQty - :oldPackageValue + :newPackageValue, e.stuffReqWeight = e.stuffReqWeight - :oldWeightValue + :newWeightValue " +
           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
           "AND e.igmNo = :sbNo AND e.igmLineNo = :sbLineNo AND e.hubTransId = :sbTransId AND e.status <> 'D'")
    int updateStuffReqQtyUpdate(
        @Param("oldPackageValue") int oldPackageValue,
        @Param("newPackageValue") int newPackageValue,
        @Param("oldWeightValue") BigDecimal oldWeightValue,
        @Param("newWeightValue") BigDecimal newWeightValue,
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("sbNo") String sbNo,
        @Param("sbLineNo") String sbLineNo,
        @Param("sbTransId") String sbTransId
    );
	
	
	@Query("SELECT e "
	        + "FROM StuffRequestHub e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.sbNo = :sbNo " 
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.stuffReqId = :stuffReqId " 
	        + "AND e.stuffReqLineId = :stuffReqLineId "
	        + "AND e.status <> 'D'")
	StuffRequestHub getDataForUpdateEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                    @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId,
	                                    @Param("stuffReqId") String stuffReqId, @Param("stuffReqLineId") int stuffReqLineId);

	
	
	
	@Query("SELECT COALESCE(MAX(E.stuffReqLineId), 0) FROM StuffRequestHub E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitCentreId = :profitcentreId "
			+ "AND E.stuffReqId = :stuffReqId")
	int getMaxLineId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("stuffReqId") String stuffReqId);
	
	
	
	
}
