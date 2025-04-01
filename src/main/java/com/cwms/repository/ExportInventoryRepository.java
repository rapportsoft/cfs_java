package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cwms.entities.ExportInventory;

public interface ExportInventoryRepository extends JpaRepository<ExportInventory, String> {


	@Query("SELECT new com.cwms.entities.ExportInventory(E.containerNo, E.gateInId, E.eBookingNo, E.stuffReqId, "
		       + "E.stuffTallyId, E.movementReqId, E.backToTownId, E.gateOutId, E.gatePassNo, "
		       + "E.emptyPassId, E.emptyGateoutId) "
		       + "FROM ExportInventory E "
		       + "WHERE E.companyId = :companyId "
		       + "AND E.branchId = :branchId "
		       + "AND E.containerNo = :containerNo "
		       + "AND E.gateInId = :gateInId "
		       + "AND E.profitcentreId = :profitCenterId")
		ExportInventory getDataForExportMainSearchInventory(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("containerNo") String containerNo,
		    @Param("gateInId") String gateInId,
		    @Param("profitCenterId") String profitCenterId
		);

	
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


	@Query(value="select e.sbTransId,e.sbNo,e.containerNo,e.gateInId,e.createdDate "
			+ "from ExportInventory e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.containerNo=:con and e.status='A' order by e.createdDate desc")
	List<Object[]> getDataByContNo(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query(value="select e.containerNo,e.containerSize,'CFS Export',p1.partyName,p2.partyName,e.gateInId,e.viaNo,v.vesselName,"
			+ "DATE_FORMAT(e.gateInDate,'%d/%m/%Y %H:%i'),CONCAT(e.yardLocation,'-',e.yardBlock,'-',e.blockCellNo),e.createdBy,e.stuffReqId,"
			+ "DATE_FORMAT(sr.stuffReqDate,'%d/%m/%Y %H:%i'),sr.createdBy,sr.pod,t.stuffTallyId,DATE_FORMAT(t.stuffTallyDate,'%d/%m/%Y %H:%i'),"
			+ "t.createdBy,t.pod,m.movementReqId,DATE_FORMAT(m.movementReqDate,'%d/%m/%Y %H:%i'),m.createdBy,m.pod,"
			+ "DATE_FORMAT(m.forceEntryDate,'%d/%m/%Y %H:%i'),f.oprInvoiceNo,DATE_FORMAT(e.invoiceDate,'%d/%m/%Y %H:%i'),f.createdBy,"
			+ "e.gatePassNo,DATE_FORMAT(gp.gatePassDate,'%d/%m/%Y %H:%i'),gp.createdBy,go.gateOutId,DATE_FORMAT(go.gateOutDate,'%d/%m/%Y %H:%i'),go.createdBy "
			+ "from ExportInventory e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sa=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.sl=p2.partyId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "LEFT OUTER JOIN ExportStuffRequest sr ON e.companyId=sr.companyId and e.branchId=sr.branchId and e.stuffReqId=sr.stuffReqId and e.containerNo=sr.containerNo "
			+ "LEFT OUTER JOIN ExportStuffTally t ON e.companyId=t.companyId and e.branchId=t.branchId and e.stuffTallyId=t.stuffTallyId and e.containerNo=t.containerNo "
			+ "LEFT OUTER JOIN ExportMovement m ON e.companyId=m.companyId and e.branchId=m.branchId and e.movementReqId=m.movementReqId "
    		+ "LEFT OUTER JOIN FinTrans f ON e.companyId=f.companyId and e.branchId=f.branchId and e.invoiceNo=f.oprInvoiceNo "
    		+ "LEFT OUTER JOIN ExportGatePass gp ON e.companyId=gp.companyId and e.branchId=gp.branchId and e.gatePassNo=gp.gatePassId and gp.srNo=1 "
    		+ "LEFT OUTER JOIN GateOut go ON e.companyId=go.companyId and e.branchId=go.branchId and e.gateOutId=go.gateOutId and go.srNo='1' "
			+ "where e.companyId=:cid and e.branchId=:bid and e.gateInId=:id and e.containerNo=:con and e.status='A' group by e.containerNo")
	List<Object[]> getDataByGateInIdFOrHistory(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("con") String con);
}
