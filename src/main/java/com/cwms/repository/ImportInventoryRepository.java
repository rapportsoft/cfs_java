package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ImportInventory;

public interface ImportInventoryRepository extends JpaRepository<ImportInventory, String> {

	@Query(value="select i from ImportInventory i where i.companyId=:cid and i.branchId=:bid and i.igmTransId=:igmtrans and i.igmNo=:igm "
			+ "and i.containerNo=:cont and i.gateInId=:gatein and i.status != 'D'")
	ImportInventory getById(@Param("cid") String cid,@Param("bid") String bid,@Param("igmtrans") String igmtrans,@Param("igm") String igm,
			@Param("cont") String cont,@Param("gatein") String gatein) ;
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE ImportInventory i SET i.gatePassNo = :id, i.gatePassDate = CURRENT_DATE WHERE i.companyId = :cid AND i.branchId = :bid "
	        + "AND i.igmTransId = :trans AND i.igmNo = :igm AND i.status != 'D' AND i.deStuffId = :destuff")
	int updateData(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String igmtrans, @Param("igm") String igm,
	               @Param("destuff") String destuff, @Param("id") String id);

	
	@Modifying
	@Transactional
	@Query(value="Update ImportInventory i SET i.gateOutId=:id , i.gateOutDate = CURRENT_DATE where i.companyId=:cid and i.branchId=:bid "
			+ "and i.igmTransId=:trans and i.igmNo=:igm and i.status != 'D' and i.gatePassNo=:gatePass")
	int updateGateOutId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String igmtrans,@Param("igm") String igm,
			@Param("gatePass") String gatePass,@Param("id") String id);
	
	@Modifying
	@Transactional
	@Query(value="Update ImportInventory i SET i.gateOutId=:id , i.gateOutDate = CURRENT_DATE where i.companyId=:cid and i.branchId=:bid "
			+ "and i.igmTransId=:trans and i.igmNo=:igm and i.status != 'D' and i.gatePassNo=:gatePass and i.containerNo=:con")
	int updateGateOutId1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String igmtrans,@Param("igm") String igm,
			@Param("gatePass") String gatePass,@Param("con") String con,@Param("id") String id);
	
    @Query("SELECT COUNT(c) > 0 FROM ImportInventory c WHERE c.companyId = :cid AND c.branchId = :bid AND c.status != 'D' "
    		+ "AND c.containerNo=:con AND (c.gateOutId = '' OR c.gateOutId is null)")
    Boolean isExistContainer(@Param("cid") String cid,
                             @Param("bid") String bid,
                             @Param("con") String con);
    
    
    @Transactional
    @Modifying
    @Query(value="Update ImportInventory c SET c.assessmentId=:id "
    		+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
			+ "c.containerNo=:con")
    int updateInvData(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("con") String con,@Param("id") String id);
    
    
 // CFIGM audit trail

 	@Transactional
 	@Modifying
 	@Query(value = "Update CFIgm c SET c.shippingAgent=:agent, c.shippingLine=:liner, c.vesselId=:vId, c.editedBy=:user,c.editedDate=CURRENT_TIMESTAMP "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmTransId=:transId")
 	int updateIGMHeaderData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId,
 			@Param("agent") String agent, @Param("liner") String liner, @Param("vId") String vId,
 			@Param("user") String user);

 	@Transactional
 	@Modifying
 	@Query(value = "Update GateIn c SET c.sa=:agent, c.sl=:liner, c.vessel=:vId "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.erpDocRefNo=:transId")
 	int updateIGMHeaderGateInData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId,
 			@Param("agent") String agent, @Param("liner") String liner, @Param("vId") String vId);

 	@Transactional
 	@Modifying
 	@Query(value = "Update ImportInventory c SET c.sa=:agent, c.sl=:liner, c.vesselId=:vId "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmTransId=:transId")
 	int updateIGMHeaderImportInventoryData(@Param("cid") String cid, @Param("bid") String bid,
 			@Param("transId") String transId, @Param("agent") String agent, @Param("liner") String liner,
 			@Param("vId") String vId);

 	@Transactional
 	@Modifying
 	@Query(value = "Update Destuff c SET c.shippingAgent=:agent, c.shippingLine=:liner "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmTransId=:transId")
 	int updateIGMHeaderDestuffData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId,
 			@Param("agent") String agent, @Param("liner") String liner);

 	@Transactional
 	@Modifying
 	@Query(value = "Update ImportGatePass c SET c.sl=:liner "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmTransId=:transId")
 	int updateIGMHeaderImportGatePassData(@Param("cid") String cid, @Param("bid") String bid,
 			@Param("transId") String transId, @Param("liner") String liner);

 	@Transactional
 	@Modifying
 	@Query(value = "Update GateOut c SET c.sa=:agent, c.sl=:liner, c.vesselId=:vId "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.erpDocRefNo=:transId")
 	int updateIGMHeaderGateOutData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId,
 			@Param("agent") String agent, @Param("liner") String liner, @Param("vId") String vId);

 	// Igm Crg audit trail

 	@Query(value = "select c.containerNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:transId "
 			+ "and c.igmNo=:igm and c.igmLineNo=:line")
 	List<String> getContainers(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId,
 			@Param("igm") String igm, @Param("line") String line);

 	@Transactional
 	@Modifying
 	@Query(value = "Update ImportInventory c SET c.importerName=:impName, c.cycle=:cycle "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmTransId=:transId and c.igmNo=:igm and "
 			+ "c.containerNo=:con")
 	int updateIGMCrgImportInventoryData(@Param("cid") String cid, @Param("bid") String bid,
 			@Param("transId") String transId, @Param("igm") String igm, @Param("con") String con,
 			@Param("impName") String impName, @Param("cycle") String cycle);

 	@Transactional
 	@Modifying
 	@Query(value = "Update DestuffCrg c SET c.commodityDescription=:commodity, c.marksOfNumbers=:marks, c.grossWeight=:gross,"
 			+ "c.typeOfPackages=:top,c.noOfPackages=:nop,c.importerName=:impName,c.importerAddress1=:impAdd1,"
 			+ "c.importerAddress2=:impAdd2,c.importerAddress3=:impAdd3 "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmTransId=:transId and c.igmNo=:igm and "
 			+ "c.igmLineNo=:line")
 	int updateIGMCrgDestuffCrgData(@Param("cid") String cid, @Param("bid") String bid, @Param("transId") String transId,
 			@Param("igm") String igm, @Param("line") String line, @Param("commodity") String commodity,
 			@Param("marks") String marks, @Param("gross") BigDecimal gross, @Param("top") String top,
 			@Param("nop") int nop, @Param("impName") String impName, @Param("impAdd1") String impAdd1,
 			@Param("impAdd2") String impAdd2, @Param("impAdd3") String impAdd3);

 	// IGM Cn Audit Trail

 	@Transactional
 	@Modifying
 	@Query(value = "Update ImportInventory c SET c.iso=:iso, c.containerSize=:size, c.containerType=:type, c.containerStatus=:cstatus,"
 			+ "c.containerWeight=:conWt, c.scannerType=:scanType, c.containerSealNo=:sealNo "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm and c.containerNo=:con and c.status='A'")
 	int updateIGMCnInventoryData(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
 			@Param("igm") String igm, @Param("con") String con, @Param("iso") String iso, @Param("size") String size,
 			@Param("type") String type, @Param("cstatus") String cstatus, @Param("conWt") BigDecimal conWt,
 			@Param("scanType") String scanType, @Param("sealNo") String sealNo);

 	@Transactional
 	@Modifying
 	@Query(value = "Update GateIn c SET c.isoCode=:iso, c.containerSize=:size, c.containerType=:type,c.containerStatus=:cStatus,"
 			+ "c.odcStatus=:odc,c.lowBed=:lowbed,c.temperature=:temp, c.tareWeight=:tareWt, c.grossWeight=:gross, c.scannerType=:scan,"
 			+ "c.containerSealNo=:sealNo "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.erpDocRefNo=:erp and c.docRefNo=:doc and c.gateInId=:id and c.status='A'")
 	int updateIGMCnGateInData(@Param("cid") String cid, @Param("bid") String bid, @Param("erp") String erp,
 			@Param("doc") String doc, @Param("id") String id, @Param("iso") String iso, @Param("size") String size,@Param("type") String type,
 			@Param("cStatus") String cStatus,@Param("odc") String odc,@Param("lowbed") String lowbed,@Param("temp") String temp,
 			@Param("tareWt") BigDecimal tareWt, @Param("gross") BigDecimal gross, @Param("scan") String scan,@Param("sealNo") String sealNo);
 	
 	
 	@Transactional
 	@Modifying
 	@Query(value = "Update Destuff c SET c.containerSize=:size, c.containerType=:type,c.containerStatus=:cStatus,"
 			+ "c.grossWeight=:gross, c.containerSealNo=:sealNo "
 			+ "where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:erp and c.igmNo=:doc and c.deStuffId=:id and c.containerNo=:con and c.status='A'")
 	int updateIGMCnDestuffData(@Param("cid") String cid, @Param("bid") String bid, @Param("erp") String erp,
 			@Param("doc") String doc, @Param("id") String id, @Param("con") String con, @Param("size") String size,@Param("type") String type,
 			@Param("cStatus") String cStatus,@Param("gross") BigDecimal gross, @Param("sealNo") String sealNo);
}
