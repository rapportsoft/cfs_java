package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.HubDocument;
import com.cwms.entities.HubGatePass;
import com.cwms.entities.StuffRequestHub;

public interface HubGatePassRepo extends JpaRepository<HubGatePass, String>{	
	
	@Query(value = "SELECT a.gatepassId, " +
            "IFNULL(DATE_FORMAT(a.gatepassDate,'%d/%m/%Y %H:%i'), '') AS Gate_In_Date, " +
            "a.vehicleNo, " +
            "a.vehicleNo, po.partyName, psl.partyName, a.containerNo, " +
            "a.containerSize, a.containerType, hb.cargoType, a.icdDestination, " +
            "a.customSealNo, a.agentSealNo, sb.movementType, sb.destination, " +
            "a.comments, a.sbNo, a.sbLineNo, IFNULL(DATE_FORMAT(a.sbDate,'%d/%m/%Y'), '') AS SB_Date, " +
            "hb.cargoDescription, a.noOfPackagesStuffed, a.cargoWeight, a.driverName, a.transporterName " +            
            "FROM HubGatePass a " +
            "LEFT JOIN StuffRequestHub sb ON a.companyId = sb.companyId AND a.branchId = sb.branchId AND a.gatepassId = sb.gatePassId AND a.sbLineNo = sb.sbLineNo AND sb.status != 'D' " + 
            "LEFT JOIN HubDocument hb ON a.companyId = hb.companyId AND a.branchId = hb.branchId AND a.sbNo = hb.igmNo AND a.sbLineNo = hb.igmLineNo AND a.sbTransId = hb.hubTransId AND hb.status != 'D' " + 
	        "LEFT JOIN Party psl ON a.companyId = psl.companyId AND a.branchId = psl.branchId AND a.shippingLine = psl.partyId AND psl.status <> 'D' " +
	        "LEFT JOIN Party po ON a.companyId = po.companyId AND a.branchId = po.branchId AND a.onAccountOf = po.partyId AND po.status <> 'D' " +            
            
            "WHERE a.companyId = :companyId AND a.branchId = :branchId " +
            "AND a.status = 'A' AND a.gatepassId = :gatepassId " +
            "GROUP BY a.gatepassId, a.sbNo")
List<Object[]> getHubGatePassReport(@Param("companyId") String companyId,
                                  @Param("branchId") String branchId,
                                  @Param("gatepassId") String gatepassId);

	
	
	@Query(value = "SELECT a.gateInId, " +
            "IFNULL(DATE_FORMAT(a.inGateInDate,'%d/%m/%Y %H:%i'), '') AS Gate_In_Date, " +
            "a.docRefNo, " +
            "a.vehicleNo, a.importerName, sb.noOfPackages, sb.cargoWt, " +
            "a.qtyTakenIn, a.cargoWeight, a.transporterName, a.lineNo " +
            "FROM GateIn a " +
            "LEFT JOIN HubDocument sb ON a.companyId = sb.companyId AND a.branchId = sb.branchId AND a.erpDocRefNo = sb.hubTransId AND a.docRefNo = sb.igmNo AND sb.status != 'D' " +          
            "WHERE a.companyId = :companyId AND a.branchId = :branchId " +
            "AND a.status = 'A' AND a.gateInId = :gateInId " +
            "GROUP BY a.gateInId, a.docRefNo")
List<Object[]> getHubGateInReport(@Param("companyId") String companyId,
                                  @Param("branchId") String branchId,
                                  @Param("gateInId") String gateInId);

	
	
	
	
	@Query("SELECT E.gateInId " +
		       "FROM GateIn E " +
		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.erpDocRefNo = :erpDocRefNo " +
		       "AND E.docRefNo = :docRefNo " +
		       "AND E.gateInType = :type " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.gateInId ASC")
	List<String> getDataForHubMainSearchGateInNormal(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("profitcentreId") String profitcentreId,
		        @Param("erpDocRefNo") String erpDocRefNo,
		        @Param("docRefNo") String docRefNo,
		        @Param("type") String type,
		        Pageable pageable);
	
	@Query("SELECT NEW com.cwms.entities.HubDocument(e.profitCentreId, e.hubTransId, e.igmLineNo, e.igmNo, "
		     + "e.noOfPackages, e.gateInPackages, e.stuffReqQty) "
		     + "FROM HubDocument e "
		     + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
		     + "AND e.igmNo LIKE CONCAT(:sbNo, '%')"
		     + "AND e.igmLineNo LIKE CONCAT(:sbLineNo, '%')"
		     + "AND e.status <> 'D' "
		     + "ORDER BY e.createdDate DESC")
	Page<HubDocument> getDataForHubMainSearchHubDocument(@Param("companyId") String companyId, 
		                                         @Param("branchId") String branchId, @Param("sbNo") String sbNo, @Param("sbLineNo") String sbLineNo, Pageable pageable);
		                 
	
	@Query("SELECT new com.cwms.entities.StuffRequestHub(E.stuffReqId, E.gateInId, E.containerNo, E.gatePassId) "	        
	           + "FROM StuffRequestHub E "	           
	           + "WHERE E.companyId = :companyId "
	           + "AND E.branchId = :branchId "
	           + "AND E.sbNo = :sbNo "
	           + "AND E.sbLineNo = :sbLineNo "
	           + "AND E.sbTransId = :sbTransId "
	           + "AND E.status <> 'D' "
	           + "ORDER BY E.createdDate DESC")
	   Page<StuffRequestHub> getDataForHubMainSearchStuff(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,@Param("sbTransId") String sbTransId ,@Param("sbLineNo") String sbLineNo, Pageable pageable
	    );
	
	
	
	
	
	
	
	
	
	
	
	
	@Query(value = "select Distinct c.gatepassId, DATE_FORMAT(c.gatepassDate, '%d %b %Y'), c.profitcentreId, po.profitcentreDesc, psl.partyName, c.containerNo, c.status "
	        + "from HubGatePass c "
	        + "Left Join Profitcentre po on c.companyId = po.companyId and c.branchId = po.branchId and c.profitcentreId = po.profitcentreId and po.status != 'D' "
	        + "LEFT JOIN Party psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId AND c.shippingLine = psl.partyId AND psl.status <> 'D' "
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' " + "AND c.profitcentreId = :profitcentreId "
	        + "and (:searchValue is null OR :searchValue = '' OR c.gatepassId LIKE %:searchValue% OR psl.partyName LIKE %:searchValue% OR c.containerNo LIKE %:searchValue%) "
	        + "ORDER BY c.gatepassDate DESC")
	List<Object[]> getGatePassEntriesToSelect(@Param("companyId") String companyId, @Param("branchId") String branchId,
											  @Param("searchValue") String searchValue, @Param("profitcentreId") String profitcentreId);
	
	
	@Query("SELECT new com.cwms.entities.HubGatePass(E.companyId, E.branchId, E.profitcentreId, E.gatepassId, "
		       + "E.gatepassDate, E.shift, E.status, E.createdBy, E.containerNo, E.containerSize, E.containerType, "
		       + "E.containerHealth, E.gateInId, psl.partyName, " 
		       + "E.vehicleNo, E.periodFrom, E.transporterName, E.icdDestination, E.comments, " 
		       + "E.sbNo, E.sbLineNo, E.sbTransId, E.sbDate, E.cargoDescription, E.exporterName, E.noOfPackages, E.podDesc, "
		       + "hub.stuffReqQty, E.cargoWeight, E.noOfPackagesStuffed, E.driverName) "
		       + "FROM HubGatePass E "
		       + "LEFT JOIN Party psl ON E.companyId = psl.companyId AND E.branchId = psl.branchId AND E.shippingLine = psl.partyId AND psl.status <> 'D' "
		       + "LEFT JOIN HubDocument hub ON E.companyId = hub.companyId AND E.branchId = hub.branchId AND E.sbTransId = hub.hubTransId "
		       + "AND E.sbNo = hub.igmNo AND E.sbLineNo = hub.igmLineNo AND hub.status <> 'D' "
		       + "WHERE E.companyId = :companyId "
		       + "AND E.branchId = :branchId "
		       + "AND E.gatepassId = :gatePassId "
		       + "AND E.status <> 'D'")
		List<HubGatePass> getSelectedGatePassEntry(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("gatePassId") String gatePassId
		);

	
	
	
	
	
	
	
	
	
	
	
	
	@Modifying
    @Transactional
    @Query("UPDATE HubGatePass e SET e.transporterName = :transporterName, e.icdDestination = :icdDestination, e.comments = :comments,e.editedBy = :user, e.editedDate = :editedDate  " +
           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
           "AND e.containerNo = :containerNo AND e.gatepassId = :gatePassId AND e.status <> 'D'")
    int updateGatePassEdit(       
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("containerNo") String containerNo,
        @Param("transporterName") String transporterName,
        @Param("icdDestination") String icdDestination,
        @Param("comments") String comments,
        @Param("gatePassId") String gatePassId,
        @Param("user") String user,
        @Param("editedDate") Date editedDate
    );
	
	@Modifying
    @Transactional
    @Query("UPDATE GateOut e SET e.comments = :comments, e.transporterName = :transporterName  " +
           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
           "AND e.containerNo = :containerNo AND e.gateOutId = :gateOutId AND e.status <> 'D'")
    int updateGatePassGateOutEdit(       
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("containerNo") String containerNo,
        @Param("gateOutId") String gateOutId,
        @Param("transporterName") String transporterName,
        @Param("comments") String comments
    );
	
	
	
	
	
	
	
	
	@Modifying
    @Transactional
    @Query("UPDATE StuffRequestHub e SET e.gatePassId = :gatePassId  " +
           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
           "AND e.containerNo = :containerNo AND e.stuffReqId = :stuffReqId AND e.status <> 'D'")
    int updateGatePassStuffingReqUpdate(       
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("containerNo") String containerNo,
        @Param("stuffReqId") String stuffReqId,
        @Param("gatePassId") String gatePassId
    );
	
	

	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e " +
	       "SET e.gateOutId = :gateOutId, " +	    
	       "    e.gatePassNo = :gateOutId, " +	      
	       "    e.gatePassDate = :editedDate, " +	
	       "    e.gateOutDate = :editedDate, " +	 
	       "    e.gatePassEditedDate = :editedDate, " +	 
	       "    e.gatePassEditedBy = :stuffReqEditedBy, " +
	       "    e.gateOutEditedDate = :editedDate, " +	 
	       "    e.gateOutEditedBy = :stuffReqEditedBy, " +
	       "    e.hubGatePassEditedBy = :stuffReqEditedBy, " +
	       "    e.hubGatePassEditedDate = :editedDate " +
	       "WHERE e.companyId = :companyId " +
	       "AND e.branchId = :branchId " +
	       "AND e.stuffTallyId = :gateInId")
	int updateHubInventoryGatePass(	   
	    @Param("gateOutId") String gateOutId,
	    @Param("stuffReqEditedBy") String stuffReqEditedBy,
	    @Param("editedDate") Date editedDate,
	    @Param("gateInId") String gateInId,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);

	
	
	
}
