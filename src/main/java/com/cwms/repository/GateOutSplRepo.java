package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.GateOutSpl;

public interface GateOutSplRepo extends JpaRepository<GateOutSpl, String>{
	
	
	
	@Query("SELECT new com.cwms.entities.GateOutSpl(E.companyId, E.branchId, E.profitcentreId, E.processId , E.gatePassNo, E.gatePassDate, E.gateOutId, E.gateOutDate, E.sl, psl.partyName, E.transType, E.cha, pc.partyName, "  
			+ "E.vehicleNo, E.driverName, E.transporter, E.transporterName, E.containerNo, "
			+ "E.srNo, E.docRefNo, E.igmLineNo, E.containerSize, E.containerType, E.shift, E.gateNo, E.comments, E.destination, E.shipperName, E.movementBy, E.outBookingType, E.status, E.createdBy) "
	        + "FROM GateOutSpl E "	        
	        + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.sl = psl.partyId AND psl.status <> 'D' "
	        + "LEFT JOIN Party pc ON E.companyId = pc.companyId AND E.branchId = pc.branchId AND E.cha = pc.partyId AND pc.status <> 'D' "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitcentreId = :profitcentreId AND E.processId = 'P00219' "
	        + "AND E.gateOutId = :gateOutId "
	        + "AND E.status <> 'D'")
	List<GateOutSpl> getSelectedSplOutEntry(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("gateOutId") String gateOutId, @Param("profitcentreId") String profitcentreId);

	
	
	@Query(value = "select c.gateOutId, c.gateOutDate, c.gatePassNo,c.gatePassDate, c.transporterName, c.vehicleNo, c.transType, c.containerNo, c.status "
	        + "from GateOutSpl c "	       
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' AND c.processId = 'P00219' "
	        + "and (:searchValue is null OR :searchValue = '' OR c.containerNo LIKE %:searchValue% OR c.gateOutId LIKE %:searchValue% OR c.gatePassNo LIKE %:searchValue%) "
	        + "GROUP BY c.gateOutId ORDER BY c.gateOutDate DESC")
	List<Object[]> getSplOutToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue);
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE GateOutSpl e " +
	       "SET e.shift = :shift, e.gateNo = :gateNo, e.destination = :destination, " +
	       "e.transporter = :transporter, e.transporterName = :transporterName, e.outBookingType = :outBookingType, e.movementBy = :movementBy, e.shipperName = :shipperName, e.comments = :comments " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.gateOutId = :gateOutId " +
	       "  AND e.containerNo = :containerNo " +
	       "  AND e.status = 'A'")
	int updateSplOutGateOut(	    
	        @Param("companyId") String companyId, @Param("branchId") String branchId, @Param("containerNo") String containerNo, 
	        @Param("gateOutId") String gateOutId, @Param("shift") String shift, @Param("gateNo") String gateNo,      
	        @Param("destination") String destination, @Param("transporter") String transporter,	@Param("transporterName") String transporterName,
	        @Param("outBookingType") String outBookingType, @Param("movementBy") String movementBy, @Param("shipperName") String shipperName,
	        @Param("comments") String comments
			);
	
	
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ImportGatePass e " +
	       "SET e.splGateOutFlag = 'Y' " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.gatePassId = :gatePassNo " +
	       "  AND e.containerNo = :containerNo " +
	       "  AND e.status = 'A'")
	int updateSplOutGatePass(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("containerNo") String containerNo,
	        @Param("gatePassNo") String gatePassNo);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleTrack e " +
	       "SET e.gateOutId = :gateOutId, e.gateOutDate = :gateOutDate " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.vehicleNo = :vehicleNo " +
	       "  AND (e.gateOutId = '' OR e.gateOutId IS NULL)")
	int updateSplOutVehicleTrack(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("vehicleNo") String vehicleNo,
	        @Param("gateOutId") String gateOut,
	        @Param("gateOutDate") Date gateOutDate);

	
	
	

	@Query("SELECT Distinct E.gatePassId "          
	        + "FROM ImportGatePass E "
	        + "LEFT JOIN Cfigmcn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.profitcentreId = E.profitcentreId AND g.gatePassNo = E.gatePassId AND g.containerNo = E.containerNo "
	        + "LEFT JOIN AssessmentSheet as ON as.companyId = E.companyId AND E.branchId = as.branchId AND as.igmTransId = E.igmTransId AND as.igmLineNo = E.igmLineNo AND as.igmNo = E.igmNo AND as.containerNo = E.containerNo "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND (:searchValue IS NULL OR :searchValue = '' OR E.gatePassId LIKE CONCAT('%', :searchValue, '%')) "
	        + "AND E.profitcentreId = :profitcentreId "
	        + "AND g.specialDelivery = 'SCON' AND as.gateOutType = 'CON' AND E.splGateOutFlag = 'N' "
	        + "AND E.status <> 'D'")
	List<String> searchGatePassNoForSPLImport(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);


	@Query("SELECT new com.cwms.entities.GateOutSpl(E.gatePassId, E.gatePassDate, E.profitcentreId, E.sl, psl.partyName, E.transType, pc.partyId, pc.partyName, "  
			+ "E.vehicleNo, E.driverName, E.transporterStatus, E.transporter, E.transporterName, E.containerNo, "
			+ "E.srNo, E.igmNo, E.igmTransId, E.igmLineNo, g.containerSize, g.containerType, E.importerName, c.shippingAgent, g.containerStatus, g.iso, g.gateInDate) "
	        + "FROM ImportGatePass E "
	        + "LEFT JOIN Cfigmcn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.profitcentreId = E.profitcentreId AND g.gatePassNo = E.gatePassId AND g.containerNo = E.containerNo "
	        + "LEFT JOIN CFIgm c ON c.companyId = g.companyId AND c.branchId = g.branchId AND c.profitcentreId = E.profitcentreId AND g.igmTransId = c.igmTransId AND g.igmNo = c.igmNo   "
	        + "LEFT JOIN AssessmentSheet as ON as.companyId = E.companyId AND E.branchId = as.branchId AND as.igmTransId = E.igmTransId AND as.igmLineNo = E.igmLineNo AND as.igmNo = E.igmNo AND as.containerNo = E.containerNo "
	        + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.sl = psl.partyId AND psl.status <> 'D' "
	        + "LEFT JOIN Party pc ON E.companyId = pc.companyId AND E.branchId = pc.branchId AND E.cha = pc.customerCode AND pc.status <> 'D' "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.gatePassId = :searchValue "
	        + "AND E.profitcentreId = :profitcentreId "
	        + "AND g.specialDelivery = 'SCON' AND as.gateOutType = 'CON' AND E.splGateOutFlag = 'N' "
	        + "AND E.status <> 'D' GROUP BY E.igmNo, E.igmLineNo")
	List<GateOutSpl> selectGatePassNoForSPLImport(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);


	
	
	
}
