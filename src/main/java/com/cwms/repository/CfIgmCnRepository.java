package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.Cfigmcn;



public interface CfIgmCnRepository extends JpaRepository<Cfigmcn, String> {
	  @Query("SELECT NEW com.cwms.entities.Cfigmcn(c.finYear, c.igmTransId, c.containerTransId, c.profitcentreId, c.igmNo, c.igmLineNo, " +
	           "c.containerNo, c.containerSize, c.containerType, c.typeOfContainer, c.iso, " +
	           "c.containerWeight, c.containerStatus, c.grossWt, c.noOfPackages, c.temperature, c.gateInId, c.scannerType,c.holdStatus) " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.profitcentreId = :profit AND c.igmNo = :igm " +
	           "AND c.igmLineNo = :igmline AND c.status != 'D'")
	    List<Cfigmcn> getAllData(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("profit") String profit,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline);
	  
	  
//	  @Query("SELECT c " +
//	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid  AND c.igmNo = :igm " +
//	           "AND c.igmLineNo = :igmline AND c.status != 'D' AND (c.gateInId is not null and c.gateInId != '')")
//	    List<Cfigmcn> getAllData1(@Param("cid") String cid,
//	                             @Param("bid") String bid,
//	                             @Param("igm") String igm,
//	                             @Param("igmline") String igmline);
//	  
	  
	  
	  @Query("SELECT c " +
		       "FROM Cfigmcn c " +
		       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
		       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
		       "AND (c.gateInId is not null AND c.gateInId != '') " +
		       "AND c.containerNo IN (" +
		       "    SELECT c2.containerNo " +
		       "    FROM Cfigmcn c2 " +
		       "    WHERE c2.igmNo = :igm " +
		       "    GROUP BY c2.containerNo " +
		       "    HAVING COUNT(c2.containerNo) = 1" +
		       ")")
		List<Cfigmcn> getAllData1(@Param("cid") String cid,
		                         @Param("bid") String bid,
		                         @Param("igm") String igm,
		                         @Param("igmline") String igmline);
	  
	  @Query("SELECT c " +
		       "FROM Cfigmcn c " +
		       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
		       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
		       "AND (c.gateInId is not null AND c.gateInId != '') " +
		       "AND c.noOfItem = 1 and c.containerStatus = 'FCL'")
		List<Cfigmcn> getSealCutting(@Param("cid") String cid,
		                         @Param("bid") String bid,
		                         @Param("igm") String igm,
		                         @Param("igmline") String igmline);

	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid  AND c.igmNo = :igm " +
	           "AND c.igmLineNo = :igmline AND c.status != 'D' AND (c.sealCutWoTransId is not null and c.sealCutWoTransId != '')")
	    List<Cfigmcn> getAllData2(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline);
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid  AND c.igmNo = :igm " +
	           "AND c.igmLineNo = :igmline AND c.status != 'D' AND (c.sealCutWoTransId is not null and c.sealCutWoTransId != '') and c.noOfItem=1")
	    List<Cfigmcn> getAllData4(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline);
	  
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid  AND c.igmNo = :igm " +
	           "AND c.igmLineNo = :igmline AND c.status != 'D' AND (c.sealCutWoTransId is not null and c.sealCutWoTransId != '') " +
	           "AND c.containerNo IN (" +
		       "    SELECT c2.containerNo " +
		       "    FROM Cfigmcn c2 " +
		       "    WHERE c2.igmNo = :igm " +
		       "    GROUP BY c2.containerNo " +
		       "    HAVING COUNT(c2.containerNo) = 1" +
		       ")")
	    List<Cfigmcn> getAllData3(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline);
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.profitcentreId = :profit AND c.igmNo = :igm " +
	           "AND c.igmLineNo = :igmline AND c.containerNo = :con  AND c.status != 'D'")
	    Cfigmcn getSingleData(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("profit") String profit,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline,@Param("con") String con);
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.profitcentreId = :profit AND c.igmNo = :igm " +
	           "AND c.igmLineNo = :igmline AND c.containerTransId = :con  AND c.status != 'D'")
	    Cfigmcn getSingleData4(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("profit") String profit,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline,@Param("con") String con);

	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.profitcentreId = :profit AND c.igmNo = :igm " +
	           " AND c.containerNo = :con  AND c.status != 'D'")
	    Cfigmcn getSingleData1(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("profit") String profit,
	                             @Param("igm") String igm,@Param("con") String con);
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.profitcentreId = :profit AND c.igmNo = :igm " +
	           " AND c.containerNo = :con AND c.igmLineNo=:line  AND c.status != 'D'")
	    Cfigmcn getSingleData2(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("profit") String profit,
	                             @Param("igm") String igm,@Param("con") String con,@Param("line") String line);
	  

	  
	  
//	  @Query("SELECT c " +
//	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
//	           "AND c.igmTransId = :igmTrans AND c.profitcentreId = :profit AND c.igmNo = :igm " +
//	           " AND c.containerNo = :con AND c.status != 'D'")
//	    List<Cfigmcn> getSingleData5(@Param("cid") String cid,
//	                             @Param("bid") String bid,
//	                             @Param("igmTrans") String igmTrans,
//	                             @Param("profit") String profit,
//	                             @Param("igm") String igm,@Param("con") String con);
	  
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           " AND c.profitcentreId = :profit AND c.igmNo = :igm " +
	           " AND c.containerNo = :con AND c.status != 'D'")
	    List<Cfigmcn> getSingleData5(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("profit") String profit,
	                             @Param("igm") String igm,@Param("con") String con);
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.igmNo = :igm " +
	           " AND c.containerNo = :con  AND c.status != 'D'")
	    Cfigmcn getSingleData3(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("igm") String igm,@Param("con") String con);
	  
	  @Query("SELECT c " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTrans AND c.igmNo = :igm AND c.igmLineNo=:line " +
	           " AND c.containerNo = :con  AND c.status != 'D'")
	    Cfigmcn getSingleData4(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("igm") String igm,@Param("line") String line,@Param("con") String con);

	    @Query("SELECT COUNT(c) > 0 FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND ((c.igmTransId = :igmTrans AND c.igmNo = :igm "
	    		+ "AND c.igmLineNo = :igmline AND  c.containerNo = :con) OR (c.containerNo = :con and c.igmTransId != :igmTrans)) AND c.status != 'D' "
	    		+ "AND (c.gateOutId = '' OR c.gateOutId is null)")
	    Boolean isExistContainer(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igmTrans") String igmTrans,
	                             @Param("igm") String igm,
	                             @Param("igmline") String igmline,
	                             @Param("con") String con);

	    @Query("SELECT COUNT(c) > 0 FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND ((c.igmTransId = :igmTrans AND c.igmNo = :igm "
	    		+ "AND c.igmLineNo = :igmline AND c.containerNo = :con AND c.containerTransId != :trans) OR (c.containerNo = :con and c.igmTransId != :igmTrans)) "
	    		+ " AND c.status != 'D' AND (c.gateOutId = '' OR c.gateOutId is null)")
	    Boolean isExistContainer1(@Param("cid") String cid,
	                              @Param("bid") String bid,
	                              @Param("igmTrans") String igmTrans,
		                             @Param("igm") String igm,
		                             @Param("igmline") String igmline,
	                              @Param("con") String con,@Param("trans") String trans);
	  
	  
//	  @Query("SELECT COUNT(c) > 0 FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND (c.igmNo = :igm "
//	    		+ "AND c.igmLineNo = :igmline AND  c.containerNo = :con) AND c.status != 'D' "
//	    		+ "AND (c.gateOutId = '' OR c.gateOutId is null)")
//	    Boolean isExistContainer(@Param("cid") String cid,
//	                             @Param("bid") String bid,
//	                             @Param("igm") String igm,
//	                             @Param("igmline") String igmline,
//	                             @Param("con") String con);
//
//	    @Query("SELECT COUNT(c) > 0 FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND (c.igmNo = :igm "
//	    		+ "AND c.igmLineNo = :igmline AND c.containerNo = :con AND c.containerTransId != :trans) "
//	    		+ " AND c.status != 'D' AND (c.gateOutId = '' OR c.gateOutId is null)")
//	    Boolean isExistContainer1(@Param("cid") String cid,
//	                              @Param("bid") String bid,
//		                             @Param("igm") String igm,
//		                             @Param("igmline") String igmline,
//	                              @Param("con") String con,@Param("trans") String trans);
	    
	    @Modifying
	    @Transactional
	    @Query("UPDATE Cfigmcn c SET " +
	           "c.containerNo = :containerNo1, " +
	           "c.iso = :iso, " +
	           "c.containerSize = :containerSize, " +
	           "c.containerType = :containerType, " +
	           "c.typeOfContainer = :typeOfContainer, " +
	           "c.containerStatus = :containerStatus, " +
	           "c.temperature = :temperature, " +
	           "c.containerWeight = :containerWeight, " +
	           "c.grossWt = :grossWt, " +
	           "c.noOfPackages = :noOfPackages, " +
	           "c.customsSealNo = :customsSealNo, " +
	           "c.upTariffDelMode = :upTariffDelMode, " +
	           "c.gateOutType = :upTariffDelMode, " +
	           "c.scannerType = :scannerType, " +
	           "c.scanningDoneStatus = :scanningDoneStatus, " +
	           "c.odcStatus = :odcStatus, " +
	           "c.lowBed = :lowBed, " +
	           "c.editedBy = :editedBy, " +
	           "c.editedDate = :editedDate, " +
	           "c.holdStatus = :holdStatus, " +
	           "c.holdRemarks = :holdRemarks, " +
	           "c.holdDate = :hdate, " +
	           "c.holdingAgentName = :hagent, " +
	           "c.releaseDate = :rdate, " +
	           "c.releaseAgent = :ragent " +
	           "WHERE c.companyId = :cid " +
	           "AND c.branchId = :bid " +
	           "AND c.igmTransId = :igmTransId " +
	           "AND c.profitcentreId = :profitcentreId " +
	           "AND c.igmNo = :igmNo " +
	           "AND c.igmLineNo = :igmLineNo " +
	           "AND c.containerTransId = :containerNo")
	    int updateContainerData(@Param("cid") String cid, 
	                            @Param("bid") String bid, 
	                            @Param("igmTransId") String igmTransId,
	                            @Param("profitcentreId") String profitcentreId, 
	                            @Param("igmNo") String igmNo, 
	                            @Param("igmLineNo") String igmLineNo,
	                            @Param("containerNo") String containerNo, 
	                            @Param("containerNo1") String containerNo1, 
	                            @Param("iso") String iso, 
	                            @Param("containerSize") String containerSize, 
	                            @Param("containerType") String containerType, 
	                            @Param("typeOfContainer") String typeOfContainer, 
	                            @Param("containerStatus") String containerStatus, 
	                            @Param("temperature") String temperature, 
	                            @Param("containerWeight") BigDecimal containerWeight, 
	                            @Param("grossWt") BigDecimal grossWt, 
	                            @Param("noOfPackages") int noOfPackages, 
	                            @Param("customsSealNo") String customsSealNo, 
	                            @Param("upTariffDelMode") String upTariffDelMode, 
	                            @Param("scannerType") String scannerType, 
	                            @Param("scanningDoneStatus") String scanningDoneStatus, 
	                            @Param("odcStatus") char odcStatus, 
	                            @Param("lowBed") char lowBed, 
	                            @Param("editedBy") String editedBy, 
	                            @Param("editedDate") Date editedDate, 
	                            @Param("holdRemarks") String holdRemarks, 
	                            @Param("holdStatus") String holdStatus,
	                            @Param("hdate") Date hdate,
	                            @Param("hagent") String hagent,
	                            @Param("rdate") Date rdate,
	                            @Param("ragent") String ragent);

	    
	    
	    @Modifying
	    @Transactional
	    @Query("UPDATE Cfigmcn c SET c.igmLineNo=:newIgm "+
	           "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmTransId = :igmTransId AND " +
	           "c.profitcentreId = :profitcentreId AND c.igmNo = :igmNo AND c.igmLineNo = :igmLineNo and c.status != 'D'")
	    int updateIGMLineData(@Param("cid") String cid, @Param("bid") String bid, @Param("igmTransId") String igmTransId,
	                            @Param("profitcentreId") String profitcentreId, @Param("igmNo") String igmNo, @Param("igmLineNo") String igmLineNo,
	                            @Param("newIgm") String newIgm);
	    
	    
	    @Transactional
		 @Modifying
		 @Query(value="update Cfigmcn c SET c.igmNo=:newigm where c.companyId=:cid and c.branchId=:bid and c.igmTransId =:igmtrans and c.igmNo=:igm "
		 		+ "and c.status != 'D'")
		 int updateIgmNo(@Param("cid") String cid, @Param("bid") String bid, @Param("igmtrans") String igmtrans, @Param("igm") String igm,@Param("newigm") String newigm);


//	    @Query(value = "SELECT cn.containerNo, cn.containerSize, cn.containerType, cn.iso, cn.containerStatus, cn.customsSealNo, i.shippingAgent, i.shippingLine, " +
//	            "cn.igmTransId, cn.igmNo, i.igmDate, i.port, i.portJo, i.docDate, i.viaNo, i.profitcentreId, " +
//	            "p.profitcentreDesc, py.partyName, py1.partyName, py2.partyName, po.portName, cn.scannerType, v.vesselName, cn.refer, cn.lowBed, cn.temperature, " +
//	            "cn.odcStatus,i.vesselId,cn.containerWeight,cn.grossWt,cn.containerTransId,cn.igmLineNo FROM Cfigmcn cn " +
//	            "LEFT OUTER JOIN CFIgm i ON cn.companyId = i.companyId AND cn.branchId = i.branchId AND cn.igmNo = i.igmNo AND cn.igmTransId = i.igmTransId " +
//	            "LEFT OUTER JOIN Profitcentre p ON cn.companyId = p.companyId AND cn.branchId = p.branchId AND cn.profitcentreId = p.profitcentreId " +
//	            "LEFT OUTER JOIN Party py ON i.companyId = py.companyId AND i.branchId = py.branchId AND i.partyId = py.partyId " +
//	            "LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId " +
//	            "LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId " +
//	            "LEFT OUTER JOIN Port po ON i.companyId = po.companyId AND i.branchId = po.branchId AND i.port = po.portCode " +
//	            "LEFT OUTER JOIN Vessel v ON i.companyId = v.companyId AND i.branchId = v.branchId AND i.vesselId = v.vesselId " +
//	            "WHERE cn.companyId = :cid AND cn.branchId = :bid AND cn.status != 'D' AND (cn.gateInId is null OR cn.gateInId = '') " +
//	            "AND (:search IS NULL OR :search = '' OR cn.containerNo LIKE CONCAT('%', :search, '%'))")
//	    List<Object[]> getContainers(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);
	    
	    
	    @Query(value = "SELECT distinct cn.containerNo, cn.containerSize, cn.containerType, cn.iso, cn.containerStatus, cn.customsSealNo, i.shippingAgent, i.shippingLine, " +
	            "cn.igmTransId, cn.igmNo, i.igmDate, i.port, i.portJo, i.docDate, i.viaNo, i.profitcentreId, " +
	            "p.profitcentreDesc, py.partyName, py1.partyName, py2.partyName, po.portName, cn.scannerType, v.vesselName, cn.refer, cn.lowBed, cn.temperature, " +
	            "cn.odcStatus,i.vesselId,cn.containerWeight,cn.grossWt,cn.igmLineNo,cn.typeOfContainer,crg.origin FROM Cfigmcn cn " +
	            "LEFT OUTER JOIN CFIgm i ON cn.companyId = i.companyId AND cn.branchId = i.branchId AND cn.igmNo = i.igmNo AND cn.igmTransId = i.igmTransId " +
	            "LEFT OUTER JOIN Cfigmcrg crg ON cn.companyId = crg.companyId AND cn.branchId = crg.branchId AND cn.igmNo = crg.igmNo AND cn.igmTransId = crg.igmTransId and cn.igmLineNo=crg.igmLineNo " +
	            "LEFT OUTER JOIN Profitcentre p ON cn.companyId = p.companyId AND cn.branchId = p.branchId AND cn.profitcentreId = p.profitcentreId " +
	            "LEFT OUTER JOIN Party py ON i.companyId = py.companyId AND i.branchId = py.branchId AND i.partyId = py.partyId " +
	            "LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId " +
	            "LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId " +
	            "LEFT OUTER JOIN Port po ON i.companyId = po.companyId AND i.branchId = po.branchId AND i.port = po.portCode " +
	            "LEFT OUTER JOIN Vessel v ON i.companyId = v.companyId AND i.branchId = v.branchId AND i.vesselId = v.vesselId " +
	            "WHERE cn.companyId = :cid AND cn.branchId = :bid AND cn.status != 'D' AND (cn.gateInId is null OR cn.gateInId = '') " +
	            "AND (:search IS NULL OR :search = '' OR cn.containerNo LIKE CONCAT('%', :search, '%'))")
	    List<Object[]> getContainers(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);
	    
	    @Query(value = "SELECT distinct cn.containerNo, cn.containerSize, cn.containerType, cn.iso, cn.containerStatus, cn.customsSealNo, i.shippingAgent, i.shippingLine, " +
	            "cn.igmTransId, cn.igmNo, i.igmDate, i.port, i.portJo, i.docDate, i.viaNo, i.profitcentreId, " +
	            "p.profitcentreDesc, py.partyName, py1.partyName, py2.partyName, po.portName, cn.scannerType, v.vesselName, cn.refer, cn.lowBed, cn.temperature, " +
	            "cn.odcStatus,i.vesselId,cn.containerWeight,cn.grossWt,cn.igmLineNo,cn.typeOfContainer,crg.origin FROM Cfigmcn cn " +
	            "LEFT OUTER JOIN CFIgm i ON cn.companyId = i.companyId AND cn.branchId = i.branchId AND cn.igmNo = i.igmNo AND cn.igmTransId = i.igmTransId " +
	            "LEFT OUTER JOIN Cfigmcrg crg ON cn.companyId = crg.companyId AND cn.branchId = crg.branchId AND cn.igmNo = crg.igmNo AND cn.igmTransId = crg.igmTransId and cn.igmLineNo=crg.igmLineNo " +
	            "LEFT OUTER JOIN Profitcentre p ON cn.companyId = p.companyId AND cn.branchId = p.branchId AND cn.profitcentreId = p.profitcentreId " +
	            "LEFT OUTER JOIN Party py ON i.companyId = py.companyId AND i.branchId = py.branchId AND i.partyId = py.partyId " +
	            "LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId " +
	            "LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId " +
	            "LEFT OUTER JOIN Port po ON i.companyId = po.companyId AND i.branchId = po.branchId AND i.port = po.portCode " +
	            "LEFT OUTER JOIN Vessel v ON i.companyId = v.companyId AND i.branchId = v.branchId AND i.vesselId = v.vesselId " +
	            "WHERE cn.companyId = :cid AND cn.branchId = :bid AND cn.status != 'D' AND (cn.gateInId is null OR cn.gateInId = '') " +
	            "AND cn.containerNo = :search")
	    List<Object[]> getContainers1(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);


	    
	    

	    @Query("select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.containerNo=:container and c.status!='D'")
	    List<Cfigmcn> getSingleContainerForSealCutting(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("container") String container);

	    
	    @Query("select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmTransId=:igmtrans and c.containerNo=:container and c.status!='D'")
	    List<Cfigmcn> getContainers(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,@Param("igmtrans") String igmtrans, @Param("container") String container);


	    

	    @Query("SELECT c.igmNo, c.igmTransId, c.igmLineNo, c.containerNo, c.gateInId, c.containerTransId, p.partyName, i.viaNo, crg.mobileNo, c.gateOutType, "
	            + "c.containerSize, c.containerType, c.customsSealNo, c.grossWt, c.gateInDate, c.sealCutReqDate, c.weighmentWeight, c.sealCuttingType, "
	            + "c.sealCuttingStatus, c.sealCutWoTransId, crg.cycle, c.vehicleType, c.sealCutRemarks, crg.notifyPartyName, c.scanningDoneStatus, c.odcStatus, c.lowBed, c.sealCuttingStatus, "
	            + "c.containerExamWoTransId,c.containerExamWoTransDate,c.containerExamDate,c.examinedPackages,c.packagesDeStuffed, "
	            + "c.packagesStuffed,c.typeOfContainer,c.containerExamRemarks,c.specialDelivery,c.scannerType,c.noOfPackages,c.containerExamStatus,c.holdStatus,"
	            + "c.doNo,c.doDate,c.doValidityDate,c.oocNo,c.oocDate FROM Cfigmcn c "
	            + "LEFT JOIN CFIgm i ON c.companyId = i.companyId AND c.branchId = i.branchId AND c.igmNo = i.igmNo AND c.igmTransId = i.igmTransId "
	            + "LEFT JOIN Cfigmcrg crg ON c.companyId = crg.companyId AND c.branchId = crg.branchId AND c.igmNo = crg.igmNo AND c.igmTransId = crg.igmTransId AND c.igmLineNo = crg.igmLineNo "
	            + "LEFT JOIN Party p ON i.companyId = p.companyId AND i.branchId = p.branchId AND i.shippingLine = p.partyId "
	            + "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm AND c.containerNo = :container "
	            + "and c.noOfItem>1 and sealCuttingStatus = 'Y' and c.containerStatus = 'FCL'  AND c.status != 'D'")
	    List<Object[]> getSingleContainerForSealCutting1(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("container") String container);
	    
	    
	    @Query("SELECT c.igmNo, c.igmTransId, c.igmLineNo, c.containerNo, c.gateInId, c.containerTransId, p.partyName, i.viaNo, crg.mobileNo, c.gateOutType, "
	            + "c.containerSize, c.containerType, c.customsSealNo, c.grossWt, c.gateInDate, c.sealCutReqDate, c.weighmentWeight, c.sealCuttingType, "
	            + "c.sealCuttingStatus, c.sealCutWoTransId, crg.cycle, c.vehicleType, c.sealCutRemarks, crg.notifyPartyName, c.scanningDoneStatus, c.odcStatus, c.lowBed, c.sealCuttingStatus, "
	            + "c.containerExamWoTransId,c.containerExamWoTransDate,c.containerExamDate,c.examinedPackages,c.packagesDeStuffed, "
	            + "c.packagesStuffed,c.typeOfContainer,c.containerExamRemarks,c.specialDelivery,c.scannerType,c.noOfPackages,c.containerExamStatus,c.holdStatus FROM Cfigmcn c "
	            + "LEFT JOIN CFIgm i ON c.companyId = i.companyId AND c.branchId = i.branchId AND c.igmNo = i.igmNo AND c.igmTransId = i.igmTransId "
	            + "LEFT JOIN Cfigmcrg crg ON c.companyId = crg.companyId AND c.branchId = crg.branchId AND c.igmNo = crg.igmNo AND c.igmTransId = crg.igmTransId AND c.igmLineNo = crg.igmLineNo "
	            + "LEFT JOIN Party p ON i.companyId = p.companyId AND i.branchId = p.branchId AND i.shippingLine = p.partyId "
	            + "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm AND c.containerNo = :container AND c.status != 'D' AND c.noOfItem > 1 and c.containerStatus = 'FCL'")
	    List<Object[]> getSingleContainerForSealCutting2(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("container") String container);


	    @Query("SELECT c FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND c.containerNo = :container "
	            + "AND c.status != 'D' "
	            + "AND ((c.containerStatus = 'FCL' AND c.containerExamStatus = 'Y') OR c.containerStatus != 'FCL') "
	            + "AND (c.gateInId IS NOT NULL AND c.gateInId != '') "
	            + "AND (c.deStuffId IS NULL OR c.deStuffId = '') "
	            + "AND ((c.gateOutType = 'CRG' AND c.containerStatus = 'FCL') OR "
	            + "(c.upTariffDelMode = 'CRG' AND c.containerStatus = 'LCL')) "
	            + "AND c.noOfItem > 1")
	    List<Cfigmcn> searchContainer(@Param("cid") String cid, @Param("bid") String bid, @Param("container") String container);

	    
	    @Query("SELECT c " +
			       "FROM Cfigmcn c " +
			       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
			       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
			       "AND c.containerExamStatus = 'Y' AND c.gateOutType='CRG' AND c.containerStatus='FCL' " +
			       "AND c.containerNo IN (" +
			       "    SELECT c2.containerNo " +
			       "    FROM Cfigmcn c2 " +
			       "    WHERE c2.igmNo = :igm " +
			       "    GROUP BY c2.containerNo " +
			       "    HAVING COUNT(c2.containerNo) = 1" +
			       ")")
			List<Cfigmcn> getDataForDestuff(@Param("cid") String cid,
			                         @Param("bid") String bid,
			                         @Param("igm") String igm,
			                         @Param("igmline") String igmline);
	    
	    
	    @Query("SELECT c " +
			       "FROM Cfigmcn c " +
			       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
			       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
			       "AND ((c.containerExamStatus = 'Y' AND c.gateOutType='CRG' AND c.destuffStatus='Y') OR " +
			       "(c.containerExamStatus = 'Y' AND c.gateOutType='CON') OR (c.destuffStatus='Y' and c.containerStatus='LCL')) " +
			       "AND (c.gatePassNo is null OR c.gatePassNo = '') " +
			       "AND ((c.noOfItem = 1 and c.containerStatus='FCL') OR (c.noOfItem > 1 and c.containerStatus='LCL' ))")
			List<Cfigmcn> getDataForGatePass(@Param("cid") String cid,
			                         @Param("bid") String bid,
			                         @Param("igm") String igm,
			                         @Param("igmline") String igmline);
	    
	    @Query("SELECT c " +
			       "FROM Cfigmcn c " +
			       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
			       "AND c.containerNo = :con AND c.status != 'D' " +
			       "AND ((c.containerExamStatus = 'Y' AND c.gateOutType='CRG' AND c.destuffStatus='Y' AND c.containerStatus = 'FCL') OR " +
			       "(c.containerExamStatus = 'Y' AND c.gateOutType='CON' AND c.containerStatus = 'FCL')) " +
			       "AND (c.gatePassNo is null OR c.gatePassNo = '') " +
			       "AND c.noOfItem > 1")
			List<Cfigmcn> getDataForGatePass1(@Param("cid") String cid,
			                         @Param("bid") String bid,
			                         @Param("igm") String igm,
			                         @Param("con") String con);
	    
	    
	    @Query(value="select c from Cfigmcn c "
	    		+ "LEFT JOIN Cfigmcrg crg ON c.companyId = crg.companyId AND c.branchId = crg.branchId AND c.igmNo = crg.igmNo  AND c.igmLineNo = crg.igmLineNo "
	    		+ "where c.companyId=:cid and c.branchId=:bid and c.status != 'D' "
	    		+ "AND (:igm is null OR :igm = '' OR  c.igmNo =:igm) "
	    		+ "AND (:line is null OR :line = '' OR  c.igmLineNo =:line) "
	    		+ "AND (:bl is null OR :bl = '' OR  crg.blNo =:bl) "
	    		+ "AND (:be is null OR :be = '' OR  crg.beNo =:be) "
	    		+ "AND (:con is null OR :con = '' OR (c.containerNo =:con AND (c.gateOutId = '' OR c.gateOutId is null))) ")
	    List<Cfigmcn> searchMainHeader(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm,
                @Param("line") String igmline,
                @Param("bl") String bl,
                @Param("be") String be,
                @Param("con") String con);
	    
	    @Modifying
	    @Transactional
	    @Query(value = "UPDATE Cfigmcn c SET c.noOfItem = c.noOfItem + 1 WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm AND c.igmTransId = :igmtrans "
	                 + "AND c.status != 'D'")
	    int addItem(@Param("cid") String cid,
	                @Param("bid") String bid,
	                @Param("igm") String igm,
	                @Param("igmtrans") String igmtrans);
	    
	    @Modifying
	    @Transactional
	    @Query(value = "UPDATE cfigmcn c " +
	                   "JOIN (SELECT c2.container_no, COUNT(*) as itemCount " +
	                         "FROM cfigmcn c2 " +
	                         "WHERE c2.igm_no = :igm AND c2.igm_Trans_Id = :igmtrans AND c2.status != 'D' " +
	                         "GROUP BY c2.container_no) temp " +
	                   "ON c.container_No = temp.container_No " +
	                   "SET c.no_Of_Item = temp.itemCount " +
	                   "WHERE c.company_Id = :cid AND c.branch_Id = :bid AND c.igm_No = :igm AND c.igm_Trans_Id = :igmtrans " +
	                   "AND c.container_No = :containerNo AND c.status != 'D'",nativeQuery=true)
	    int updateNoOfItem(@Param("cid") String cid,
	                       @Param("bid") String bid,
	                       @Param("igm") String igm,
	                       @Param("igmtrans") String igmtrans,
	                       @Param("containerNo") String containerNo);


	    
	    @Query(value="select distinct c.containerNo from Cfigmcn c where c.companyId=:cid "
	    		+ "and c.branchId=:bid and (c.gateInId is null OR c.gateInId = '') and "
	    		+ "(:val is null OR :val = '' OR c.containerNo LIKE CONCAT (:val,'%'))")
	    List<String> getSearcgCon(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("val") String val);
	    
	    
	    @Query(value="select distinct c.containerNo from Cfigmcn c "
	    		+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and  "
	    		+ "c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
	    		+ "where c.companyId=:cid "
	    		+ "and c.branchId=:bid and (c.gateInId is null OR c.gateInId = '') and "
	    		+ "(:igm is null OR :igm = '' OR c.igmNo=:igm) and "
	    		+ "(:item is null OR :item = '' OR c.igmLineNo=:item) and "
	    		+ "(:bl is null OR :bl = '' OR crg.blNo=:bl) and "
	    		+ "(:val is null OR :val = '' OR c.containerNo =:val) and c.status != 'D'")
	    List<String> getSearcgCon1(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm,
                @Param("item") String item,
                @Param("val") String val,
                @Param("bl") String bl);
	    
	    
	    @Query(value="select distinct c.igmNo, c.igmLineNo from Cfigmcn c "
	    	    + "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and  "
	    	    + "c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
	    	    + "where c.companyId=:cid "
	    	    + "and c.branchId=:bid and "
	    	    + "(:igm is null OR :igm = '' OR c.igmNo=:igm) and "
	    	    + "(:item is null OR :item = '' OR c.igmLineNo=:item) and "
	    	    + "(:bl is null OR :bl = '' OR crg.blNo=:bl) and "
	    	    + "(:be is null OR :be = '' OR crg.beNo=:be) and "
	    	    + "(:val is null OR :val = '' OR (c.containerNo =:val)) and  c.status != 'D'")
	    Object[] getSealCutData(@Param("cid") String cid,
	                    @Param("bid") String bid,
	                    @Param("igm") String igm,
	                    @Param("item") String item,
	                    @Param("val") String val,
	                    @Param("bl") String bl,
	                    @Param("be") String be);
	    
	    
	    @Query(value="select distinct c.igmNo, c.igmLineNo, c.createdDate from Cfigmcn c "
	    	    + "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and  "
	    	    + "c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
	    	    + "where c.companyId=:cid "
	    	    + "and c.branchId=:bid and "
	    	    + "(:igm is null OR :igm = '' OR c.igmNo=:igm) and "
	    	    + "(:item is null OR :item = '' OR c.igmLineNo=:item) and "
	    	    + "(:bl is null OR :bl = '' OR crg.blNo=:bl) and "
	    	    + "(:be is null OR :be = '' OR crg.beNo=:be) and "
	    	    + "(:val is null OR :val = '' OR (c.containerNo =:val)) and  c.status != 'D' order by c.createdDate desc limit 1")
	    Object[] getSealCutData1(@Param("cid") String cid,
	                    @Param("bid") String bid,
	                    @Param("igm") String igm,
	                    @Param("item") String item,
	                    @Param("val") String val,
	                    @Param("bl") String bl,
	                    @Param("be") String be);


	    
	    @Query(value="select distinct c.igmLineNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm "
	    		+ "and (c.gateInId != '' AND c.gateInId is not null) and c.containerStatus = 'FCL'")
	    List<String> getItem(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    
	    @Query(value="select distinct c.containerNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm "
	    		+ "and (c.gateInId != '' AND c.gateInId is not null) and c.noOfItem > 1 and c.containerStatus = 'FCL'")
	    List<String> getCon(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    
	    @Query(value="select distinct c.igmLineNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm "
	    		+ "and c.sealCuttingStatus = 'Y' and  c.containerStatus = 'FCL'")
	    List<String> getItem1(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    
	    @Query(value="select distinct c.containerNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm "
	    		+ "and c.sealCuttingStatus = 'Y' and c.noOfItem > 1 and c.containerStatus = 'FCL'")
	    List<String> getCon1(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    
	    @Query(value="select distinct c.igmLineNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm "
	    		+ "and c.containerExamStatus = 'Y' and c.containerStatus = 'FCL' and c.gateOutType='CRG'")
	    List<String> getItem2(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    
	    @Query(value = "select distinct c.containerNo from Cfigmcn c " +
	            "where c.companyId = :cid and c.branchId = :bid and c.status != 'D' and c.igmNo = :igm " +
	            "and ((c.containerExamStatus = 'Y' and c.containerStatus = 'FCL' and c.gateOutType = 'CRG') OR " +
	            "(c.containerStatus = 'LCL' and (c.gateInId != '' or c.gateInId is not null))) " +
	            "and c.destuffStatus != 'Y' and c.noOfItem > 1")
	    List<String> getCon2(@Param("cid") String cid,
	                         @Param("bid") String bid,
	                         @Param("igm") String igm);

	    
	    @Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.containerNo =:con order by c.createdDate desc limit 1")
	    Cfigmcn getConByConNo(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("con") String con);
	    
	    
	    @Query(value = "SELECT distinct cn.containerNo, cn.containerSize, cn.containerType, cn.iso, cn.containerStatus, cn.customsSealNo, i.shippingAgent, i.shippingLine, " +
	            "cn.igmTransId, cn.igmNo, i.igmDate, i.port, i.portJo, i.docDate, i.viaNo, i.profitcentreId, " +
	            "p.profitcentreDesc, py.partyName, py1.partyName, py2.partyName, po.portName, cn.scannerType, v.vesselName, cn.refer, cn.lowBed, cn.temperature, " +
	            "cn.odcStatus,i.vesselId,cn.containerWeight,cn.grossWt,cn.igmLineNo,cn.typeOfContainer,crg.origin,cn.gateInId,cn.createdDate FROM Cfigmcn cn " +
	            "LEFT OUTER JOIN CFIgm i ON cn.companyId = i.companyId AND cn.branchId = i.branchId AND cn.igmNo = i.igmNo AND cn.igmTransId = i.igmTransId " +
	            "LEFT OUTER JOIN Cfigmcrg crg ON cn.companyId = crg.companyId AND cn.branchId = crg.branchId AND cn.igmNo = crg.igmNo AND cn.igmTransId = crg.igmTransId and cn.igmLineNo=crg.igmLineNo " +
	            "LEFT OUTER JOIN Profitcentre p ON cn.companyId = p.companyId AND cn.branchId = p.branchId AND cn.profitcentreId = p.profitcentreId " +
	            "LEFT OUTER JOIN Party py ON i.companyId = py.companyId AND i.branchId = py.branchId AND i.partyId = py.partyId " +
	            "LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId " +
	            "LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId " +
	            "LEFT OUTER JOIN Port po ON i.companyId = po.companyId AND i.branchId = po.branchId AND i.port = po.portCode " +
	            "LEFT OUTER JOIN Vessel v ON i.companyId = v.companyId AND i.branchId = v.branchId AND i.vesselId = v.vesselId " +
	            "WHERE cn.companyId = :cid AND cn.branchId = :bid AND cn.status != 'D' " +
	            "AND cn.containerNo = :search order by cn.createdDate desc limit 1")
	    Object[] getConByConNo1(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);
	    
	    @Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmLineNo=:line and c.status != 'D' "
	    		+ "and c.noOfItem = 1 order by c.createdDate desc limit 1 ")
	    Cfigmcn getSingleDataFoeGatePass(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm,
                @Param("line") String line);
	    
	    @Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.containerNo=:con and c.status != 'D' "
	    		+ "and c.noOfItem > 1 order by c.createdDate desc limit 1 ")
	    Cfigmcn getSingleDataFoeGatePass1(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm,
                @Param("con") String con);
	    
	    @Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.containerNo=:con and c.status != 'D' "
	    		+ "and c.noOfItem > 1 and c.containerStatus = 'FCL' order by c.createdDate desc limit 1 ")
	    Cfigmcn getSingleDataFoeGatePass2(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm,
                @Param("con") String con);
	    
	    
	    @Query(value="select distinct c.igmLineNo from Cfigmcn c LEFT OUTER JOIN DestuffCrg crg ON c.companyId=crg.companyId and "
	    		+ "c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
	    		+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.status != 'D' "
	    		+ "and ((c.containerStatus = 'FCL' and c.noOfItem = 1) OR (c.containerStatus = 'LCL' and c.noOfItem > 1 and (crg.yardPackages - crg.qtyTakenOut) > 0)) and "
	    		+ "(c.gatePassNo is null OR c.gatePassNo = '') "
	    		+ "and ((c.gateOutType = 'CRG' and c.destuffStatus='Y') OR (c.gateOutType = 'CON' and c.containerExamStatus = 'Y')) ")
	    List<String> getItems(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    @Query(value="select distinct c.containerNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.status != 'D' "
	    		+ "and c.noOfItem > 1 and c.containerStatus = 'FCL' and (c.gatePassNo is null OR c.gatePassNo = '') "
	    		+ "and ((c.gateOutType = 'CRG' and c.destuffStatus='Y') OR (c.gateOutType = 'CON' and c.containerExamStatus = 'Y')) ")
	    List<String> getContainerss(@Param("cid") String cid,
                @Param("bid") String bid,
                @Param("igm") String igm);
	    
	    @Query(value = "SELECT DISTINCT c.igmNo, c.igmTransId, c.containerNo " +
	               "FROM Cfigmcn c " +
	               "LEFT OUTER JOIN Cfigmcrg cr ON c.companyId = cr.companyId AND c.branchId = cr.branchId AND c.igmNo = cr.igmNo " +
	               "AND c.igmTransId = cr.igmTransId AND c.igmLineNo = cr.igmLineNo " +
	               "WHERE c.companyId = :cid AND c.branchId = :bid " +
	               "AND c.status != 'D' AND c.destuffStatus = 'Y' AND c.containerStatus = 'LCL' " +
	               "AND (cr.examTallyId IS NULL OR cr.examTallyId = '') " +
	               "AND ((:val IS NULL OR :val = '' OR c.igmNo LIKE CONCAT(:val, '%')) " +
	               "OR (:val IS NULL OR :val = '' OR c.containerNo LIKE CONCAT(:val, '%')))")
	List<Object[]> getSearchForExamination(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

}

