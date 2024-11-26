package com.cwms.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cwms.entities.ExportInventory;

public interface ExportInventoryRepository extends JpaRepository<ExportInventory, String> {

	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e SET e.sbNo = :sbNo, e.sbTransId = :sbTransId, e.vesselId = :vesselId, e.viaNo = :viaNo, e.stuffTallyId = :stuffTallyId, e.stuffTallyDate = :stuffTallyDate, e.stuffTallyEditedBy = :stuffTallyEditedBy, e.stuffTallyEditedDate = :stuffTallyEditedDate WHERE e.companyId =:companyId and e.branchId =:branchId and e.gateInId = :gateInId")
	int updateExportInventoryStuffingTally(
	    @Param("sbNo") String sbNo,
	    @Param("sbTransId") String sbTransId,
	    @Param("vesselId") String vesselId,
	    @Param("viaNo") String viaNo,
	    @Param("stuffTallyId") String stuffTallyId,
	    @Param("stuffTallyDate") Date stuffTallyDate,
	    @Param("stuffTallyEditedBy") String stuffTallyEditedBy,
	    @Param("stuffTallyEditedDate") Date stuffTallyEditedDate,
	    @Param("gateInId") String gateInId,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);

	
	
	
	
	
	
	
	
	@Query("SELECT COUNT(e) > 0 FROM ExportInventory e WHERE e.companyId = :cid AND e.branchId = :bid AND e.status != 'D' AND e.containerNo = :con AND (e.gateOutId IS NULL OR e.gateOutId = '')")
	Boolean checkContainerInInventoryWithoutGateOut(@Param("cid") String cid, @Param("bid") String bId, @Param("con") String con);

	
	
	

	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e SET e.movementReqId = :movementReqId, e.movementReqDate = :movementReqDate, e.movementEditedBy = :movementEditedBy, e.movementEditedDate = :movementEditedDate WHERE e.companyId =:companyId and e.branchId =:branchId and e.gateInId = :gateInId")
	int updateExportInventoryMovement(
	    @Param("movementReqId") String movementReqId,
	    @Param("movementReqDate") Date movementReqDate,
	    @Param("movementEditedBy") String movementEditedBy,
	    @Param("movementEditedDate") Date movementEditedDate,
	    @Param("gateInId") String gateInId,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);
	
	
	
	
	
	
	
	@Query("SELECT e "
	        + "FROM ExportInventory e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.gateInId = :gateInId " 
	        + "AND e.stuffReqId = :stuffReqId " 
	        + "AND e.status <> 'D'")
	ExportInventory getDataForUpdateInvEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,	                                    
	                                    @Param("stuffReqId") String stuffReqId, @Param("gateInId") String gateInId);
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e SET e.sbNo = :sbNo, e.sbTransId = :sbTransId, e.vesselId = :vesselId, e.viaNo = :viaNo, e.stuffReqId = :stuffReqId, e.stuffReqDate = :stuffReqDate, e.stuffReqEditedBy = :stuffReqEditedBy, e.stuffReqEditedDate = :editedDate WHERE e.companyId =:companyId and e.branchId =:branchId and e.gateInId = :gateInId")
	int updateExportInventoryStuffingRequest(
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

	
	
	
	
	
	@Query(value="select e from ExportInventory e where e.companyId=:cid and e.branchId=:bid and e.gateInId=:gateId and e.status != 'D'")
	ExportInventory getSingleDataByGateInId(@Param("cid") String cid,@Param("bid") String bId,@Param("gateId") String gate);
	
	@Query(value="select COUNT(e) > 0 from ExportInventory e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(e.gateOutId is null OR e.gateOutId = '') and e.containerNo=:con")
	Boolean checkContainerInInventory(@Param("cid") String cid,@Param("bid") String bId,@Param("con") String con);
	
	
	@Query(value="select i from ExportInventory i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and i.containerNo=:con "
			+ "and (i.gateOutId is null OR i.gateOutId = '')")
	ExportInventory getDataByContainerNo(@Param("cid") String cid,@Param("bid") String bId,@Param("con") String con);
	
	
	@Query(value="select i from ExportInventory i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' "
			+ "and i.containerNo=:con and i.sbNo=:sb "
			+ "and (i.gateOutId is null OR i.gateOutId = '')")
	ExportInventory getDataByContainerNoAndSb(@Param("cid") String cid,@Param("bid") String bId,@Param("con") String con,@Param("sb") String sb);
	
	
	@Query(value="select i from ExportInventory i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' "
			+ "and i.containerNo=:con and i.gateInId=:gate "
			+ "and (i.gateOutId is null OR i.gateOutId = '')")
	ExportInventory getDataByContainerNoAndGateIn(@Param("cid") String cid,@Param("bid") String bId,@Param("con") String con,@Param("gate") String gate);
}
