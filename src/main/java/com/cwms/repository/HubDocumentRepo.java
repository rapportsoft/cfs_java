package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.GateIn;
import com.cwms.entities.HubDocument;

public interface HubDocumentRepo extends JpaRepository<HubDocument, String>{	
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e " +
	       "SET e.vesselId = :vesselId, " +	    
	       "    e.viaNo = :viaNo, " +	      
	       "    e.stuffReqEditedBy = :stuffReqEditedBy, " +
	       "    e.hubStuffing = 'Y', " +	 
	       "    e.stuffReqEditedDate = :editedDate, " +	 
	       "    e.hubStfEditedBy = :stuffReqEditedBy, " +
	       "    e.hubStfEditedDate = :editedDate " +
	       "WHERE e.companyId = :companyId " +
	       "AND e.branchId = :branchId " +
	       "AND e.gateInId = :gateInId")
	int updateHubInventoryStuffingRequestShort(	   
	    @Param("vesselId") String vesselId,
	    @Param("viaNo") String viaNo,
	    @Param("stuffReqEditedBy") String stuffReqEditedBy,
	    @Param("editedDate") Date editedDate,
	    @Param("gateInId") String gateInId,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);

	
	
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE HubDocument e " +
	       "SET e.stuffReqWeight = COALESCE(e.stuffReqWeight, 0) + :stuffReqWeight, " +
	       "    e.stuffReqQty = COALESCE(e.stuffReqQty, 0) + :stuffReqQty " +
	       "WHERE e.companyId = :companyId " +
	       "AND e.branchId = :branchId " +
	       "AND e.hubTransId = :hubTransId " +
	       "AND e.igmNo = :igmNo " +
	       "AND e.igmLineNo = :igmLineNo")
	int updateHubGateInStuffingRequest(          
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("stuffReqQty") Integer stuffReqQty,
	    @Param("stuffReqWeight") BigDecimal stuffReqWeight, 
	    @Param("hubTransId") String hubTransId, 
	    @Param("igmNo") String igmNo, 
	    @Param("igmLineNo") String igmLineNo
	);

	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE GateIn e " +
	       "SET e.hubStuffId = :hubStuffId " +	      
	       "WHERE e.companyId = :companyId " +
	       "AND e.branchId = :branchId " +
	       "AND e.gateInId = :gateInId")
	int updateHubGateInContainerStuffingRequest(		  
	    @Param("companyId") String companyId, @Param("branchId") String branchId,
	    @Param("hubStuffId") String hubStuffId, @Param("gateInId") String gateInId
	);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e " +
	       "SET e.sbNo = :sbNo, " +
	       "    e.sbTransId = :sbTransId, " +
	       "    e.vesselId = :vesselId, " +
	       "    e.viaNo = :viaNo, " +
	       "    e.stuffReqId = :stuffReqId, " +
	       "    e.stuffReqDate = :stuffReqDate, " +
	       "    e.stuffReqEditedBy = :stuffReqEditedBy, " +
	       "    e.stuffReqEditedDate = :editedDate, " +
	       "    e.movementReqId = :stuffReqId, " +
	       "    e.movementReqDate = :stuffReqDate, " +
	       "    e.stuffTallyId = :stuffReqId, " +
	       "    e.stuffTallyDate = :stuffReqDate, " +
	       "    e.hubStfEditedBy = :stuffReqEditedBy, " +
	       "    e.hubStfEditedDate = :editedDate, " +
	       "    e.cycle = 'HUB' " +
	       "WHERE e.companyId = :companyId " +
	       "AND e.branchId = :branchId " +
	       "AND e.gateInId = :gateInId")
	int updateHubInventoryStuffingRequest(
	    @Param("sbNo") String sbNo,
	    @Param("sbTransId") String sbTransId,
	    @Param("vesselId") String vesselId,
	    @Param("viaNo") String viaNo,
	    @Param("stuffReqId") String stuffReqId,
	    @Param("stuffReqDate") Date stuffReqDate,
	    @Param("stuffReqEditedBy") String stuffReqEditedBy,
	    @Param("editedDate") Date editedDate,
	    @Param("gateInId") String gateInId,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);

	
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM StuffRequestHub e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.sbNo = :sbNo " +
		       "AND e.sbTransId = :sbTransId AND e.containerNo = :containerNo " +
		       "AND e.profitCentreId = :profitcentreId " +
		       "AND e.status <> 'D' " +
		       "AND (:stuffReqLineId IS NULL OR e.stuffReqLineId <> :stuffReqLineId) " +
		       "AND (:stuffReqId IS NULL OR :stuffReqId = '' OR e.stuffReqId <> :stuffReqId)")
		boolean existsByIgmNoForstuffing(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("sbNo") String sbNo,
		    @Param("sbTransId") String sbTransId,
		    @Param("profitcentreId") String profitcentreId,
		    @Param("stuffReqId") String stuffReqId,
		    @Param("stuffReqLineId") Integer stuffReqLineId,
		    @Param("containerNo") String containerNo
		);
	
	
	@Query(value = "SELECT c.igmNo, c.igmLineNo, c.hubTransDate, c.importerName, c.noOfPackages, c.grossWt, c.cargoWt, c.cargoDescription, c.hubTransId, (c.noOfPackages - c.stuffReqQty) As balanceQuantity, (c.cargoWt - c.stuffReqWeight) As balanceWeight "
	        + "FROM HubDocument c "
	        + "LEFT JOIN StuffRequestHub st ON c.companyId = st.companyId "
	        + "AND c.branchId = st.branchId "
	        + "AND st.sbNo = c.igmNo "
	        + "AND st.sbLineNo = c.igmLineNo "
	        + "AND st.sbTransId = c.hubTransId "
	        + "AND st.profitCentreId = c.profitCentreId "
	        + "AND st.status <> 'D' " 
	        + "WHERE c.companyId = :companyId "
	        + "AND c.branchId = :branchId "
	        + "AND c.noOfPackages = c.gateInPackages "
	        + "AND c.status != 'D' "
	        + "AND c.profitCentreId = :profitcentreId "
	        + "AND (COALESCE(:searchValue, '') = '' OR c.igmNo LIKE CONCAT('%', :searchValue, '%') OR c.igmLineNo LIKE CONCAT('%', :searchValue, '%')) "
	        + "AND (st.stuffReqId IS NULL OR st.stuffReqId != :stuffReqId) "
	        + "ORDER BY c.hubTransDate DESC")
	List<Object[]> searchIgmNoForStuffing(@Param("companyId") String companyId, 
	                                      @Param("branchId") String branchId,
	                                      @Param("searchValue") String searchValue, 
	                                      @Param("profitcentreId") String profitcentreId, 
	                                      @Param("stuffReqId") String stuffReqId);

	
	
	@Query("SELECT E.containerNo, E.containerSize, E.containerType, E.sa, psa.partyName, E.sl, psl.partyName, g.onAccountOf, g.tareWeight, g.inGateInDate, g.deliveryOrderNo, g.gateInId, g.containerHealth, pon.partyName " +  
		       "FROM ExportInventory E " +  
		       "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId " +  
		       "AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +  
		       "AND g.containerNo = E.containerNo AND (g.stuffRequestId IS NULL OR g.stuffRequestId = '') " +  
		       "LEFT JOIN Party psa ON g.companyId = psa.companyId AND g.branchId = psa.branchId " +  
		       "AND g.sa = psa.partyId AND psa.status <> 'D' " +  
		       "LEFT JOIN Party psl ON g.companyId = psl.companyId AND g.branchId = psl.branchId " +  
		       "AND g.sl = psl.partyId AND psl.status <> 'D' " + 
		       "LEFT JOIN Party pon ON g.companyId = pon.companyId AND g.branchId = pon.branchId " +  
		       "AND g.onAccountOf = pon.partyId AND pon.status <> 'D' " + 
		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +  
		       "AND (E.holdStatus IS NULL OR E.holdStatus = '' OR E.holdStatus <> 'H') " +  
		       "AND (E.stuffReqId IS NULL OR E.stuffReqId = '') " +  
		       "AND E.profitcentreId = :profitcentreId " +  
		       "AND E.containerNo LIKE %:searchValue% " +  
		       "AND E.status <> 'D' " +  
		       "AND (g.containerStatus IS NULL OR g.containerStatus = 'MTY')")  
		List<Object[]> searchContainerNoForHubCLP(@Param("companyId") String companyId,  
		                                          @Param("branchId") String branchId,  
		                                          @Param("searchValue") String searchValue,  
		                                          @Param("profitcentreId") String profitcentreId);  

	
	 @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM HubDocument e " +
	           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
	           "AND e.igmNo = :igmNo " +
	           "AND e.igmLineNo = :igmLineNo " +
	           "AND (:hubTransId IS NULL OR :hubTransId = '' OR e.hubTransId <> :hubTransId) " +
	           "AND e.profitCentreId = :profitcentreId " +
	           "AND e.status <> 'D'")
	    boolean existsByIGMAndLineNo(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("profitcentreId") String profitcentreId,	        
	        @Param("igmNo") String igmNo,
	        @Param("igmLineNo") String igmLineNo, @Param("hubTransId") String hubTransId
	    );
	
	
	@Query(value = "select c.gateInId, c.inGateInDate, c.docRefNo, c.erpDocRefNo, c.profitcentreId, po.profitcentreDesc, c.transporterStatus, c.transporterName, c.driverName, c.vehicleNo, c.status "
	        + "from GateIn c "
	        + "Left Join Profitcentre po on c.companyId = po.companyId and c.branchId = po.branchId and c.profitcentreId = po.profitcentreId and po.status != 'D' "
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' and c.gateInType= :type AND c.processId = :processId " + "AND c.profitcentreId = :profitcentreId "
	        + "and (:searchValue is null OR :searchValue = '' OR c.docRefNo LIKE %:searchValue% OR c.gateInId LIKE %:searchValue% OR c.vehicleNo LIKE %:searchValue%) "
	        + "ORDER BY c.inGateInDate DESC")
	List<Object[]> getGateInEntriesData(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                @Param("searchValue") String searchValue,@Param("processId") String processId, @Param("type") String type, @Param("profitcentreId") String profitcentreId);
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.GateIn(E.companyId, E.branchId, E.gateInId, E.finYear, E.erpDocRefNo, "
			+ "E.docRefNo, E.lineNo, E.srNo, E.docRefDate, E.gateNo, "
			+ "sb.gateInPackages, E.gateInType, E.profitcentreId, E.processId, "
			+ "E.grossWeight, c.partyName , E.qtyTakenIn, E.shift, "
			+ "E.status, E.createdBy, E.approvedBy, E.transporterStatus, E.transporterName, E.vehicleNo, E.driverName, "
			+ "E.cargoWeight, E.commodityDescription, E.actualNoOfPackages, E.inGateInDate, E.weightTakenIn, sb.gateInWt, E.cargoType, E.importerName, E.area) " + "FROM GateIn E "
			+ "LEFT JOIN Party c ON E.companyId = c.companyId AND E.branchId = c.branchId AND E.sl = c.partyId AND c.status != 'D' "
			+ "LEFT JOIN HubDocument sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.erpDocRefNo = sb.hubTransId AND E.docRefNo = sb.igmNo AND sb.status != 'D' "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitcentreId = :profitcentreId "
			+ "AND E.gateInId = :gateInId " + "AND E.gateInType = :type " + "AND E.status <> 'D'")
	List<GateIn> getSelectedGateInEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("gateInId") String gateInId,
			@Param("type") String type);
	
	
	@Query("SELECT s.igmNo, s.igmLineNo, s.hubTransId, s.hubTransDate, s.sl, c.partyName, s.cargoDescription, s.noOfPackages, s.gateInPackages, s.cargoType, s.importerName, s.area, s.grossWt, s.cargoWt, s.gateInWt  " +
		       "FROM HubDocument s " +		     
		       "LEFT JOIN Party c ON s.companyId = c.companyId AND s.branchId = c.branchId " +
		       "AND s.sl = c.partyId AND c.status != 'D' " +		     
		       "LEFT JOIN GateIn g ON g.companyId = s.companyId AND g.branchId = s.branchId AND g.docRefNo = s.igmNo " +
		       "AND g.gateInId = :gateInId " +  
		       "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.igmNo = :igmNo AND s.igmNo = :igmNo AND s.hubTransId = :hubTransId " +
		       "AND (s.noOfPackages - s.gateInPackages) > 0 AND s.profitCentreId = :profitCentreId " +
		       "AND s.status <> 'D' " +
		       "AND g.gateInId IS NULL")
		List<Object[]> getIgmCargoGateIn(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("hubTransId") String hubTransId,
		    @Param("gateInId") String gateInId,
		    @Param("igmNo") String igmNo,
		    @Param("profitCentreId") String profitCentreId
		);

	
	
	@Query("SELECT Distinct s.igmNo, s.hubTransId, s.igmLineNo " +
		       "FROM HubDocument s " +			     
		       "LEFT JOIN GateIn g ON g.companyId = s.companyId AND g.branchId = s.branchId AND g.docRefNo = s.igmNo " +
		       "AND g.gateInId = :gateInId AND g.processId = 'P00102' AND g.profitcentreId = :profitCentreId " + 
		       "WHERE s.companyId = :companyId AND s.branchId = :branchId " +
		       "AND (s.noOfPackages - s.gateInPackages) > 0 " +
		       "AND s.profitCentreId = :profitCentreId " +
		       "AND s.igmNo LIKE %:searchValue% " +		      
		       "AND s.status <> 'D' AND g.gateInId IS NULL")
		List<Object[]> searchIgmNosToGateIn(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("profitCentreId") String profitCentreId,
		    @Param("searchValue") String searchValue,
		    @Param("gateInId") String gateInId
		);
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE HubDocument E SET E.hubTransDate = :hubTransDate, E.igmNo = :igmNo, E.igmLineNo = :igmLineNo, "
	        + "E.noOfPackages = :noOfPackages, E.grossWt = :grossWt, E.cargoWt = :cargoWt, "
	        + "E.cargoDescription = :cargoDescription, E.cargoType = :cargoType, E.area = :area, "
	        + "E.importerName = :importerName, E.importerAddress = :importerAddress, "
	        + "E.editedBy = :user, E.editedDate = CURRENT_TIMESTAMP "
	        + "WHERE E.companyId = :companyId "
	        + "AND E.branchId = :branchId "
	        + "AND E.profitCentreId = :profitcentreId "
	        + "AND E.hubTransId = :hubTransId "
	        + "AND E.status <> 'D'")
	int updateHubDocumentEntry(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("profitcentreId") String profitcentreId,
	        @Param("hubTransId") String hubTransId, 
	        @Param("user") String user,	                             
	        @Param("hubTransDate") Date hubTransDate,
	        @Param("igmNo") String igmNo,
	        @Param("igmLineNo") String igmLineNo,
	        @Param("noOfPackages") Integer noOfPackages,
	        @Param("grossWt") BigDecimal grossWt, 
	        @Param("cargoWt") BigDecimal cargoWt,		                             
	        @Param("cargoDescription") String cargoDescription,
	        @Param("cargoType") String cargoType,
	        @Param("area") String area,
	        @Param("importerName") String importerName, 
	        @Param("importerAddress") String importerAddress
	);

	
	
	
	
	@Query("SELECT NEW com.cwms.entities.HubDocument( " +
		       "E.companyId, E.branchId, E.hubTransId, E.igmLineNo, E.igmNo, " +
		       "E.hubTransDate, E.noOfPackages, E.grossWt, E.cargoWt, E.cargoDescription, " +
		       "E.cargoType, E.importerName, E.importerAddress, E.sa, E.area, E.status, " +
		       "E.createdBy, E.approvedBy, E.sl, E.profitCentreId, " +
		       "pc.profitcentreDesc, psa.partyName, psl.partyName) " +
		       "FROM HubDocument E "
		       + "LEFT JOIN Profitcentre pc ON E.profitCentreId = pc.profitcentreId AND E.companyId = pc.companyId And E.branchId = pc.branchId AND pc.status <> 'D' "
		       + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.sa = psa.partyId AND psa.status != 'D' "
				+ "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.sl = psl.partyId AND psl.status != 'D' "
				+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitCentreId = :profitcentreId "
				+ "AND E.hubTransId = :hubTransId " + "AND E.igmNo = :igmNo " + "AND E.status <> 'D'")
	HubDocument getSelectedHubEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("hubTransId") String hubTransId,
			@Param("igmNo") String igmNo);

	
	
//	@Query("SELECT NEW com.cwms.entities.HubDocument( " +
//		       "E.companyId, E.branchId, E.hubTransId, E.igmNo, " +
//		       "E.hubTransDate, E.noOfPackages, E.grossWt, E.cargoWt, E.cargoDescription, " +
//		       "E.cargoType, E.importerName, E.area, E.status, " +
//		       "psa.partyName, E.profitCentreId) " +
//		       "FROM HubDocument E "
//		       + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.sa = psa.partyId AND psa.status != 'D' "
//				+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitCentreId = :profitcentreId "
//				 + "and (:searchValue is null OR :searchValue = '' OR E.igmNo LIKE %:searchValue% OR E.igmLineNo LIKE %:searchValue% OR E.importerName LIKE %:searchValue%) "
//				+ "AND E.status <> 'D'")
//	List<HubDocument> getHubEntriesToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId,
//											@Param("profitcentreId") String profitcentreId, @Param("searchValue") String searchValue);

	
	@Query("SELECT NEW com.cwms.entities.HubDocument( " +
		       "E.companyId, E.branchId, E.hubTransId, E.igmNo, " +
		       "E.hubTransDate, E.noOfPackages, E.grossWt, E.cargoWt, E.cargoDescription, " +
		       "E.cargoType, E.importerName, E.area, E.status, " +
		       "psa.partyName, E.profitCentreId) " +
		       "FROM HubDocument E "
		       + "LEFT JOIN Party psa ON E.companyId = psa.companyId AND E.branchId = psa.branchId AND E.sa = psa.partyId AND psa.status != 'D' "
		       + "WHERE E.companyId = :companyId AND E.branchId = :branchId "
		       + "AND E.profitCentreId = :profitcentreId "
		       + "AND (:searchValue IS NULL OR :searchValue = '' OR E.igmNo LIKE %:searchValue% OR E.igmLineNo LIKE %:searchValue% OR E.importerName LIKE %:searchValue%) "
		       + "AND E.status <> 'D' "
		       + "ORDER BY E.hubTransDate DESC")
		List<HubDocument> getHubEntriesToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId,
		                                        @Param("profitcentreId") String profitcentreId, @Param("searchValue") String searchValue);

	
	
	
	@Query("SELECT E " +
		       "FROM HubDocument E "		     
				+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitCentreId = :profitcentreId "
				+ "AND E.hubTransId = :hubTransId " + "AND E.igmNo = :igmNo " + "AND E.status <> 'D'")
	HubDocument getSinleHubEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("hubTransId") String hubTransId,
			@Param("igmNo") String igmNo);




	

}
