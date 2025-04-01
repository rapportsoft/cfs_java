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
	  
	  @Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm "
				+ "and c.containerNo=:con and c.status != 'D' and (c.gateOutId is null OR c.gateOutId = '') "
				+ "and (c.deStuffId is null OR c.deStuffId = '') and c.scanningDoneStatus != 'Y'")
		List<Cfigmcn> getDataForScanning(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
				@Param("igm") String igm,@Param("con") String con);
	  
	  
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
	    
	    @Query("SELECT COUNT(c) > 0 FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND c.status != 'D' "
	    		+ "AND c.containerNo=:con AND (c.gateOutId = '' OR c.gateOutId is null)")
	    Boolean isExistContainer2(@Param("cid") String cid,
	                             @Param("bid") String bid,
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
	           "c.containerSealNo = :customsSealNo, " +
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
	    
//	    @Query(value = "SELECT distinct cn.containerNo, cn.containerSize, cn.containerType, cn.iso, cn.containerStatus, cn.customsSealNo, i.shippingAgent, i.shippingLine, " +
//	            "cn.igmTransId, cn.igmNo, i.igmDate, i.port, i.portJo, i.docDate, i.viaNo, i.profitcentreId, " +
//	            "p.profitcentreDesc, py.partyName, py1.partyName, py2.partyName, po.portName, cn.scannerType, v.vesselName, cn.refer, cn.lowBed, cn.temperature, " +
//	            "cn.odcStatus,i.vesselId,cn.containerWeight,cn.grossWt,cn.igmLineNo,cn.typeOfContainer,crg.origin FROM Cfigmcn cn " +
//	            "LEFT OUTER JOIN CFIgm i ON cn.companyId = i.companyId AND cn.branchId = i.branchId AND cn.igmNo = i.igmNo AND cn.igmTransId = i.igmTransId " +
//	            "LEFT OUTER JOIN Cfigmcrg crg ON cn.companyId = crg.companyId AND cn.branchId = crg.branchId AND cn.igmNo = crg.igmNo AND cn.igmTransId = crg.igmTransId and cn.igmLineNo=crg.igmLineNo " +
//	            "LEFT OUTER JOIN Profitcentre p ON cn.companyId = p.companyId AND cn.branchId = p.branchId AND cn.profitcentreId = p.profitcentreId " +
//	            "LEFT OUTER JOIN Party py ON i.companyId = py.companyId AND i.branchId = py.branchId AND i.partyId = py.partyId " +
//	            "LEFT OUTER JOIN Party py1 ON i.companyId = py1.companyId AND i.branchId = py1.branchId AND i.shippingAgent = py1.partyId " +
//	            "LEFT OUTER JOIN Party py2 ON i.companyId = py2.companyId AND i.branchId = py2.branchId AND i.shippingLine = py2.partyId " +
//	            "LEFT OUTER JOIN Port po ON i.companyId = po.companyId AND i.branchId = po.branchId AND i.port = po.portCode " +
//	            "LEFT OUTER JOIN Vessel v ON i.companyId = v.companyId AND i.branchId = v.branchId AND i.vesselId = v.vesselId " +
//	            "WHERE cn.companyId = :cid AND cn.branchId = :bid AND cn.status != 'D' AND (cn.gateInId is null OR cn.gateInId = '') " +
//	            "AND cn.containerNo = :search")
//	    List<Object[]> getContainers1(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);
	    
	    @Query(value = "SELECT distinct cn.containerNo, cn.containerSize, cn.containerType, cn.iso, cn.containerStatus, cn.containerSealNo, i.shippingAgent, i.shippingLine, " +
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
	    
	    
//	    @Query("SELECT c " +
//			       "FROM Cfigmcn c " +
//			       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
//			       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
//			       "AND ((c.containerExamStatus = 'Y' AND c.gateOutType='CRG' AND c.destuffStatus='Y') OR " +
//			       "(c.containerExamStatus = 'Y' AND c.gateOutType='CON') OR (c.destuffStatus='Y' and c.containerStatus='LCL')) " +
//			       "AND (c.gatePassNo is null OR c.gatePassNo = '') " +
//			       "AND ((c.noOfItem = 1 and c.containerStatus='FCL') OR (c.noOfItem > 1 and c.containerStatus='LCL' ))")
//			List<Cfigmcn> getDataForGatePass(@Param("cid") String cid,
//			                         @Param("bid") String bid,
//			                         @Param("igm") String igm,
//			                         @Param("igmline") String igmline);
	    
	    
//	    @Query("SELECT c " +
//			       "FROM Cfigmcn c LEFT OUTER JOIN DestuffCrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId " +
//	    		   "and c.deStuffId=crg.deStuffId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo " +
//			       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
//			       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
//			       "AND ((c.containerExamStatus = 'Y' AND c.gateOutType='CRG' AND c.containerStatus='FCL' AND c.destuffStatus='Y' AND (crg.actualNoOfPackages - crg.qtyTakenOut)>0) OR " +
//			       "(c.containerExamStatus = 'Y' AND c.gateOutType='CON' AND (c.gatePassNo is null OR c.gatePassNo = '')) OR (c.destuffStatus='Y' and c.containerStatus='LCL' AND (crg.actualNoOfPackages - crg.qtyTakenOut)>0)) " +
//			       "AND ((c.noOfItem = 1 and c.containerStatus='FCL') OR (c.noOfItem > 1 and c.containerStatus='LCL' ))")
//			List<Cfigmcn> getDataForGatePass(@Param("cid") String cid,
//			                         @Param("bid") String bid,
//			                         @Param("igm") String igm,
//			                         @Param("igmline") String igmline);
	    
	    
	    @Query("SELECT c " +
			       "FROM Cfigmcn c LEFT OUTER JOIN DestuffCrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId " +
	    		   "and c.deStuffId=crg.deStuffId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo " +
			       "WHERE c.companyId = :cid AND c.branchId = :bid AND c.igmNo = :igm " +
			       "AND c.igmLineNo = :igmline AND c.status != 'D' " +
			       "AND ((c.containerExamStatus = 'Y' AND c.gateOutType='CRG' AND c.containerStatus='FCL' AND c.destuffStatus='Y' AND (crg.actualNoOfPackages - crg.qtyTakenOut)>0) OR " +
			       "(c.containerExamStatus = 'Y' AND c.gateOutType='CON' AND (c.gatePassNo is null OR c.gatePassNo = '')) OR (c.destuffStatus='Y' and c.containerStatus='LCL' AND (crg.actualNoOfPackages - crg.qtyTakenOut)>0)) " +
			       "AND ((c.noOfItem = 1 and c.containerStatus='FCL') OR (c.noOfItem > 1 and c.containerStatus='LCL' )) and c.gateOutType=:type")
			List<Cfigmcn> getDataForGatePass(@Param("cid") String cid,
			                         @Param("bid") String bid,
			                         @Param("igm") String igm,
			                         @Param("igmline") String igmline,@Param("type") String type);
	    
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
	    
	    
//	    @Query(value="select c from Cfigmcn c "
//	    		+ "LEFT JOIN Cfigmcrg crg ON c.companyId = crg.companyId AND c.branchId = crg.branchId AND c.igmNo = crg.igmNo  AND c.igmLineNo = crg.igmLineNo "
//	    		+ "where c.companyId=:cid and c.branchId=:bid and c.status != 'D' "
//	    		+ "AND (:igm is null OR :igm = '' OR  c.igmNo =:igm) "
//	    		+ "AND (:line is null OR :line = '' OR  c.igmLineNo =:line) "
//	    		+ "AND (:bl is null OR :bl = '' OR  crg.blNo =:bl) "
//	    		+ "AND (:be is null OR :be = '' OR  crg.beNo =:be) "
//	    		+ "AND (:con is null OR :con = '' OR (c.containerNo =:con AND (c.gateOutId = '' OR c.gateOutId is null))) ")
//	    List<Cfigmcn> searchMainHeader(@Param("cid") String cid,
//                @Param("bid") String bid,
//                @Param("igm") String igm,
//                @Param("line") String igmline,
//                @Param("bl") String bl,
//                @Param("be") String be,
//                @Param("con") String con);
	    
	    @Query(value="select c from Cfigmcn c "
	    		+ "LEFT JOIN Cfigmcrg crg ON c.companyId = crg.companyId AND c.branchId = crg.branchId AND c.igmNo = crg.igmNo  AND c.igmLineNo = crg.igmLineNo "
	    		+ "where c.companyId=:cid and c.branchId=:bid and c.status != 'D' "
	    		+ "AND (:igm is null OR :igm = '' OR  c.igmNo =:igm) "
	    		+ "AND (:line is null OR :line = '' OR  c.igmLineNo =:line) "
	    		+ "AND (:bl is null OR :bl = '' OR  crg.blNo =:bl) "
	    		+ "AND (:be is null OR :be = '' OR  crg.beNo =:be) "
	    		+ "AND (:con is null OR :con = '' OR c.containerNo =:con) order by c.createdDate desc ")
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
	
	@Modifying
	@Transactional
	@Query(value="Update Cfigmcn cn SET cn.gateOutId=:id , cn.gateOutDate = CURRENT_DATE, cn.doNo=:do, cn.doDate=:doDate, "
			+ "cn.doValidityDate=:doValidityDate where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.igmTransId=:trans and cn.igmNo=:igm and (:line is null OR :line = '' OR cn.igmLineNo=:line ) and cn.containerNo=:con and cn.status != 'D'")
	int updategateOutId3(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String igmline,@Param("con") String con,@Param("id") String id,@Param("do") String doNo,@Param("doDate") Date doDate,@Param("doValidityDate") Date doValidityDate);
	
	@Modifying
	@Transactional
	@Query(value="Update Cfigmcn cn SET cn.gateOutId=:id , cn.gateOutDate = CURRENT_DATE, cn.doNo=:do, cn.doDate=:doDate, "
			+ "cn.doValidityDate=:doValidityDate where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.igmTransId=:trans and cn.igmNo=:igm and (:line is null OR :line = '' OR cn.igmLineNo=:line ) and cn.status != 'D'")
	int updategateOutId1(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String igmline,@Param("id") String id,@Param("do") String doNo,@Param("doDate") Date doDate,@Param("doValidityDate") Date doValidityDate);
	
	
	@Modifying
	@Transactional
	@Query(value="Update Cfigmcn cn SET cn.doNo=:do, cn.doDate=:doDate, "
			+ "cn.doValidityDate=:doValidityDate where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.igmTransId=:trans and cn.igmNo=:igm and (:line is null OR :line = '' OR cn.igmLineNo=:line ) and cn.containerNo=:con and cn.status != 'D'")
	int updategateOutId4(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String igmline,@Param("con") String con,@Param("do") String doNo,@Param("doDate") Date doDate,@Param("doValidityDate") Date doValidityDate);
	
	@Modifying
	@Transactional
	@Query(value="Update Cfigmcn cn SET cn.doNo=:do, cn.doDate=:doDate, "
			+ "cn.doValidityDate=:doValidityDate where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.igmTransId=:trans and cn.igmNo=:igm and (:line is null OR :line = '' OR cn.igmLineNo=:line ) and cn.status != 'D'")
	int updategateOutId2(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String igmline,@Param("do") String doNo,@Param("doDate") Date doDate,@Param("doValidityDate") Date doValidityDate);
	
	
	@Modifying
	@Transactional
	@Query(value="Update Cfigmcn cn SET cn.gateOutId=:id , cn.gateOutDate = CURRENT_DATE where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.igmTransId=:trans and cn.igmNo=:igm and cn.status != 'D'")
	int updategateOutId(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,@Param("igm") String igm,
			@Param("id") String id);
	
	
	@Modifying
	@Transactional
	@Query(value="Update Cfigmcn cn SET cn.gatePassNo=:id where cn.companyId=:cid and cn.branchId=:bid and "
			+ "cn.igmTransId=:trans and cn.igmNo=:igm and  cn.igmLineNo=:line and cn.status != 'D'")
	int updategatePassId(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line,@Param("id") String id);
	
	
	@Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.containerNo=:con and c.status != 'D' and "
			+ "(c.gateOutId is null OR c.gateOutId = '')")
	List<Cfigmcn> getHoldSearch(@Param("cid") String cid, @Param("bid") String bid, @Param("con") String con);
	
	@Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm and "
			+ "c.igmLineNo=:line and c.status != 'D' and (c.gateOutId is null OR c.gateOutId = '')")
	List<Cfigmcn> getHoldSearch1(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
			@Param("igm") String igm, @Param("line") String line);
	
	@Query(value="select c from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm and "
			+ "c.containerNo=:con and c.status != 'D' and (c.gateOutId is null OR c.gateOutId = '')")
	List<Cfigmcn> getHoldSearch2(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
			@Param("igm") String igm, @Param("con") String con);
	
	@Query(value="select COUNT(c)>0 from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.igmTransId=:trans and c.igmNo=:igm and "
			+ "c.igmLineNo=:line and c.status != 'D' and c.holdStatus = 'R' AND c.holdDocRefNo IS NOT NULL AND c.holdDocRefNo != '' ")
	Boolean checkContainerHoldStatus(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
			@Param("igm") String igm, @Param("line") String line);

	
	 @Query("SELECT NEW com.cwms.entities.Cfigmcn(c.finYear, c.igmTransId, c.containerTransId, c.profitcentreId, c.igmNo, c.igmLineNo, " +
	           "c.containerNo, c.containerSize, c.containerType, c.typeOfContainer, c.iso, " +
	           "c.containerWeight, c.containerStatus, c.grossWt, c.noOfPackages, c.temperature, c.gateInId, c.scannerType,c.holdStatus,c.gateOutType,c.cargoWt) " +
	           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND c.status != 'D' " +
	           "AND c.igmNo=:igm AND c.igmTransId=:transId AND c.igmLineNo=:igmLine AND (c.gateOutId is null OR c.gateOutId = '') " +
	           "AND (c.ssrTransId is null OR c.ssrTransId = '')")
	    List<Cfigmcn> searchSSr(@Param("cid") String cid,
	                             @Param("bid") String bid,
	                             @Param("igm") String igm,
	                             @Param("transId") String transId,
	                             @Param("igmLine") String igmline);
	
	  
		@Query(value="select NEW com.cwms.entities.Cfigmcn(c.finYear, c.igmTransId, c.containerTransId, c.profitcentreId, c.igmNo, c.igmLineNo,"
				+ "c.containerNo, c.containerSize, c.containerType, c.typeOfContainer, c.iso,"
				+ "c.containerWeight, c.containerStatus, c.grossWt, c.noOfPackages, c.temperature, c.gateInId, c.scannerType,c.holdStatus,c.gateOutType,c.cargoWt) "
				+ "from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.containerNo=:con and c.status != 'D' and "
				+ "(c.gateOutId is null OR c.gateOutId = '') AND (c.ssrTransId is null OR c.ssrTransId = '')")
		List<Cfigmcn> searchSSrContainer(@Param("cid") String cid, @Param("bid") String bid, @Param("con") String con);
		
		  @Query("SELECT c " +
		           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
		           "AND c.igmTransId = :igmTrans AND c.igmNo = :igm AND c.igmLineNo=:line " +
		           " AND c.containerNo = :con  AND c.status != 'D' "
		           + "AND (c.gateOutId is null OR c.gateOutId = '') AND (c.ssrTransId is null OR c.ssrTransId = '')")
		    Cfigmcn getSingleData5(@Param("cid") String cid,
		                             @Param("bid") String bid,
		                             @Param("igmTrans") String igmTrans,
		                             @Param("igm") String igm,@Param("line") String line,@Param("con") String con);
		  
		  @Query("SELECT c " +
		           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid " +
		           "AND c.igmTransId = :igmTrans AND c.igmNo = :igm AND c.igmLineNo=:line " +
		           " AND c.containerNo = :con  AND c.status != 'D' "
		           + "AND (((c.gateOutId is null OR c.gateOutId = '') AND (c.ssrTransId is null OR c.ssrTransId = '')) OR "
		           + "(c.ssrTransId=:id))")
		    Cfigmcn getSingleData6(@Param("cid") String cid,
		                             @Param("bid") String bid,
		                             @Param("igmTrans") String igmTrans,
		                             @Param("igm") String igm,@Param("line") String line,@Param("con") String con,@Param("id") String id);
		  
		  @Query("SELECT NEW com.cwms.entities.Cfigmcn(c.finYear, c.igmTransId, c.containerTransId, c.profitcentreId, c.igmNo, c.igmLineNo, " +
		           "c.containerNo, c.containerSize, c.containerType, c.typeOfContainer, c.iso, " +
		           "c.containerWeight, c.containerStatus, c.grossWt, c.noOfPackages, c.temperature, c.gateInId, c.scannerType,c.holdStatus,c.gateOutType,c.cargoWt) " +
		           "FROM Cfigmcn c WHERE c.companyId = :cid AND c.branchId = :bid AND c.status != 'D' " +
		           "AND c.igmNo=:igm AND c.igmTransId=:igmTrans AND c.igmLineNo=:igmLine AND (((c.gateOutId is null OR c.gateOutId = '') " +
		           "AND (c.ssrTransId is null OR c.ssrTransId = '')) OR (c.ssrTransId=:id))")
		    List<Cfigmcn> getLatestSSRData(@Param("cid") String cid,
		                             @Param("bid") String bid,
		                             @Param("igmTrans") String igmTrans,
		                             @Param("igm") String igm,@Param("igmLine") String line,@Param("id") String id);
		  
		  
		  
		  
		  
		  
		  
		  
		  @Query(value="select c.sealCutWoTransId,DATE_FORMAT(c.sealCutWoTransDate,'%d/%m/%Y %H:%i'),c.containerNo,c.containerSize,"
			  		+ "c.containerType,c.containerSealNo,c.customsSealNo,DATE_FORMAT(c.gateInDate,'%d/%m/%Y %H:%i'),c.noOfPackages,"
			  		+ "c.cargoWt,c.gateOutType,c.scanningDoneStatus,c.yardLocation "
			  		+ "from Cfigmcn c "
			  		+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmLineNo=:line and c.status != 'D' and "
			  		+ "c.noOfItem=1 and c.sealCutWoTransId=:jobid and c.containerStatus='FCL'")
			  List<Object[]> getDataForSealCuttingItemWiseReport(@Param("cid") String cid,@Param("bid") String bid,
					  @Param("igm") String igm,@Param("line") String line,@Param("jobid") String jobid);
			  
			  
			  @Query(value="select c.sealCutWoTransId,DATE_FORMAT(c.sealCutWoTransDate,'%d/%m/%Y %H:%i'),crg.chaName,crg.importerName,"
			  		    + "c.igmNo,p2.partyName,c.customsSealNo,c.grossWt,crg.mobileNo,crg.commodityDescription,c.containerNo,"
			  		    + "c.containerSize,c.containerType,c.igmLineNo,crg.grossWeight,crg.beNo,crg.noOfPackages,c.gateOutType,"
			  		    + "DATE_FORMAT(c.gateInDate,'%d/%m/%Y %H:%i'),DATE_FORMAT(c.sealCutReqDate,'%d/%m/%Y %H:%i'),c.yardLocation "
				  		+ "from Cfigmcn c "
				  		+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
				  		+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
				  		+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingLine=p2.partyId "
				  		+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.containerNo=:con and c.status != 'D' and "
				  		+ "c.noOfItem > 1 and c.sealCutWoTransId=:jobid and c.containerStatus='FCL'")
				  List<Object[]> getDataForSealCuttingConWiseReport(@Param("cid") String cid,@Param("bid") String bid,
						  @Param("igm") String igm,@Param("con") String con,@Param("jobid") String jobid);
				  
				  
//				  @Query(value="select c.containerExamWoTransId,DATE_FORMAT(c.containerExamWoTransDate,'%d/%m/%Y %h:%i'),c.igmNo,"
//				  		+ "c.igmLineNo,p1.partyName,crg.importerName,p2.partyName,crg.commodityDescription,c.containerNo,c.containerSize,"
//				  		+ "c.containerType,DATE_FORMAT(c.gateInDate,'%d/%m/%Y %h:%i'),crg.cargoType,GROUP_CONCAT(j.jarDtlDesc),c.examinedPackages,c.igmTransId,c.createdBy "
//				  		+ "from Cfigmcn c "
//				  		+ "LEFT OUTER JOIN Party p1 ON c.companyId=p1.companyId and c.branchId=p1.branchId and c.cha=p1.partyId "
//				  		+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
//				  		+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
//				  		+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingLine=p2.partyId "
//				  		+ "LEFT OUTER JOIN EquipmentActivity e ON c.companyId=e.companyId and c.branchId=e.branchId and c.igmTransId=e.erpDocRefNo and c.igmNo=e.docRefNo and c.containerNo=e.containerNo and c.containerExamWoTransId=e.deStuffId "
//				  		+ "LEFT OUTER JOIN JarDetail j ON e.companyId=j.companyId and e.equipment=j.jarDtlId and j.jarId='J00012' "
//				  		+ "where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm and c.igmLineNo=:line and "
//				  		+ "c.noOfItem=1 and c.containerExamWoTransId=:jobid and c.igmTransId=:trans and c.containerStatus='FCL' "
//				  		+ "GROUP BY c.igmNo,c.igmTransId,c.containerNo")
//				  List<Object[]> getDataForExaminationItemWiseReport(@Param("cid") String cid,@Param("bid") String bid,
//						  @Param("igm") String igm,@Param("line") String line,@Param("jobid") String jobid,@Param("trans") String trans);
				  
				  
				  @Query(value="select c.containerExamWoTransId,DATE_FORMAT(c.containerExamWoTransDate,'%d/%m/%Y %H:%i'),c.igmNo,"
					  		+ "c.igmLineNo,p1.partyName,crg.importerName,p2.partyName,crg.commodityDescription,c.containerNo,c.containerSize,"
					  		+ "c.containerType,DATE_FORMAT(c.gateInDate,'%d/%m/%Y %H:%i'),crg.cargoType,GROUP_CONCAT(j.jarDtlDesc),c.examinedPackages,c.igmTransId,c.createdBy "
					  		+ "from Cfigmcn c "
					  		+ "LEFT OUTER JOIN Party p1 ON c.companyId=p1.companyId and c.branchId=p1.branchId and c.cha=p1.partyId "
					  		+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
					  		+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
					  		+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingLine=p2.partyId "
					  		+ "LEFT OUTER JOIN EquipmentActivity e ON c.companyId=e.companyId and c.branchId=e.branchId and c.igmTransId=e.erpDocRefNo and c.igmNo=e.docRefNo and c.containerNo=e.containerNo and c.containerExamWoTransId=e.deStuffId and e.status != 'D' "
					  		+ "LEFT OUTER JOIN JarDetail j ON e.companyId=j.companyId and e.equipment=j.jarDtlId and j.jarId='J00012' "
					  		+ "where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm and c.igmLineNo=:line and "
					  		+ "c.noOfItem=1 and c.containerExamWoTransId=:jobid and c.igmTransId=:trans and c.containerStatus='FCL' "
					  		+ "GROUP BY c.igmNo,c.igmTransId,c.containerNo")
					  List<Object[]> getDataForExaminationItemWiseReport(@Param("cid") String cid,@Param("bid") String bid,
							  @Param("igm") String igm,@Param("line") String line,@Param("jobid") String jobid,@Param("trans") String trans);
				  
				  
				  @Query(value="select c.containerExamWoTransId,DATE_FORMAT(c.containerExamWoTransDate,'%d/%m/%Y %H:%i'),c.igmNo,"
					  		+ "c.igmLineNo,crg.chaName,crg.importerName,p2.partyName,crg.commodityDescription,c.containerNo,c.containerSize,"
					  		+ "c.containerType,DATE_FORMAT(c.gateInDate,'%d/%m/%Y %H:%i'),crg.cargoType,GROUP_CONCAT(j.jarDtlDesc),c.examinedPackages,c.igmTransId,c.createdBy "
					  		+ "from Cfigmcn c "
					  		+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
					  		+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
					  		+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingLine=p2.partyId "
					  		+ "LEFT OUTER JOIN EquipmentActivity e ON c.companyId=e.companyId and c.branchId=e.branchId and c.igmTransId=e.erpDocRefNo and c.igmNo=e.docRefNo and c.containerNo=e.containerNo and c.containerExamWoTransId=e.deStuffId "
					  		+ "LEFT OUTER JOIN JarDetail j ON e.companyId=j.companyId and e.equipment=j.jarDtlId and j.jarId='J00012' "
					  		+ "where c.companyId=:cid and c.branchId=:bid and c.status != 'D' and c.igmNo=:igm and c.containerNo=:con "
					  		+ "and c.igmTransId=:trans and c.containerStatus='FCL' and c.noOfItem>1 "
					  		+ "GROUP BY c.igmNo,c.igmTransId,c.containerNo,c.igmLineNo")
					  List<Object[]> getDataForExaminationContainerWiseReport(@Param("cid") String cid,@Param("bid") String bid,
							  @Param("igm") String igm,@Param("con") String con,@Param("trans") String trans);
					  
					  
					  //assessment
					  
					  
//					  @Query(value = "select c.igmTransId,c.igmNo,c.igmLineNo,crg.blNo,crg.beNo "
//								+ "from Cfigmcn c "
//								+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
//								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and (c.invoiceAssesed != 'Y' OR CAST(c.invCount AS INTEGER)>0) and "
//								+ "crg.status = 'A' and (:val is null OR :val = '' OR c.igmNo LIKE CONCAT('%',:val,'%') OR "
//								+ "crg.blNo LIKE CONCAT('%',:val,'%') OR crg.beNo LIKE CONCAT('%',:val,'%')) ")
//						List<Object[]> getBeforeAssessmentData(@Param("cid") String cid, @Param("bid") String bid,
//								@Param("val") String val);
					  
						@Query(value = "select DISTINCT c.igmTransId,c.igmNo,c.igmLineNo,crg.blNo,crg.beNo "
								+ "from Cfigmcn c "
								+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and "
								+ "crg.status = 'A' and (crg.beNo is not null AND crg.beNo != '') and (:val is null OR :val = '' OR c.igmNo LIKE CONCAT('%',:val,'%') OR "
								+ "crg.blNo LIKE CONCAT('%',:val,'%') OR crg.beNo LIKE CONCAT('%',:val,'%')) ")
						List<Object[]> getBeforeAssessmentData(@Param("cid") String cid, @Param("bid") String bid,
								@Param("val") String val);
						
						
//						@Query(value = "select c.igmTransId,c.igmNo,c.igmLineNo,crg.blNo,crg.beNo,p.profitcentreDesc,i.viaNo,i.igmDate,crg.blDate,"
//								+ "p1.partyName,p2.partyName,crg.cargoValue,crg.cargoDuty,crg.commodityDescription,crg.importerId,crg.importerName,"
//								+ "crg.importerSr,crg.importerAddress1,crg.importerAddress2,crg.importerAddress3,pa1.gstNo,c.cha,p3.partyName,"
//								+ "pa2.srNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,crg.accountHolderId,crg.accountHolderName,pa3.srNo,"
//								+ "pa3.gstNo,pa3.state,c.containerNo,c.containerSize,c.containerType,c.gateInDate,c.deStuffDate,c.gateOutDate"
//								+ ",c.examinedPackages,c.typeOfContainer,c.scannerType,c.gateOutType,c.upTariffNo,c.profitcentreId,"
//								+ "c.grossWt,c.cargoWt,c.ssrTransId,i.shippingAgent,i.shippingLine,c.containerStatus,c.gatePassNo,c.gateOutId "
//								+ "from Cfigmcn c "
//								+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
//								+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
//								+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
//								+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.shippingLine=p1.partyId "
//								+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingAgent=p2.partyId "
//								+ "LEFT OUTER JOIN PartyAddress pa1 ON crg.companyId=pa1.companyId and crg.branchId=pa1.branchId and crg.importerId=pa1.partyId and crg.importerSr=pa1.srNo "
//								+ "LEFT OUTER JOIN Party p3 ON c.companyId=p3.companyId and c.branchId=p3.branchId and c.cha=p3.partyId "
//								+ "LEFT OUTER JOIN PartyAddress pa2 ON p3.companyId=pa2.companyId and p3.branchId=pa2.branchId and p3.partyId=pa2.partyId and pa2.defaultChk = 'Y' "
//								+ "LEFT OUTER JOIN PartyAddress pa3 ON crg.companyId=pa3.companyId and crg.branchId=pa3.branchId and crg.accountHolderId=pa3.partyId and pa3.defaultChk = 'Y' "
//								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and (c.invoiceAssesed != 'Y' OR CAST(c.invCount AS INTEGER)>0) and "
//								+ "crg.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and c.igmLineNo=:lineNo ")
//						List<Object[]> getBeforeSaveAssessmentData(@Param("cid") String cid, @Param("bid") String bid,
//								@Param("trans") String trans,@Param("igm") String igm,@Param("lineNo") String lineNo);
						
						
//						@Query(value = "select c.igmTransId,c.igmNo,c.igmLineNo,crg.blNo,crg.beNo,p.profitcentreDesc,i.viaNo,i.igmDate,crg.blDate,"
//								+ "p1.partyName,p2.partyName,crg.cargoValue,crg.cargoDuty,crg.commodityDescription,crg.importerId,crg.importerName,"
//								+ "crg.importerSr,crg.importerAddress1,crg.importerAddress2,crg.importerAddress3,pa1.gstNo,c.cha,p3.partyName,"
//								+ "pa2.srNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,crg.accountHolderId,crg.accountHolderName,pa3.srNo,"
//								+ "pa3.gstNo,pa3.state,c.containerNo,c.containerSize,c.containerType,c.gateInDate,c.deStuffDate,c.gateOutDate"
//								+ ",c.examinedPackages,c.typeOfContainer,c.scannerType,c.gateOutType,c.upTariffNo,c.profitcentreId,"
//								+ "c.grossWt,c.cargoWt,c.ssrTransId,i.shippingAgent,i.shippingLine,c.containerStatus,c.gatePassNo,c.gateOutId,"
//								+ "c.assesmentId,c.lastAssesmentDate,c.invoiceNo,c.invoiceDate,c.invoiceUptoDate "
//								+ "from Cfigmcn c "
//								+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
//								+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
//								+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
//								+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.shippingLine=p1.partyId "
//								+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingAgent=p2.partyId "
//								+ "LEFT OUTER JOIN PartyAddress pa1 ON crg.companyId=pa1.companyId and crg.branchId=pa1.branchId and crg.importerId=pa1.partyId and crg.importerSr=pa1.srNo "
//								+ "LEFT OUTER JOIN Party p3 ON c.companyId=p3.companyId and c.branchId=p3.branchId and c.cha=p3.partyId "
//								+ "LEFT OUTER JOIN PartyAddress pa2 ON p3.companyId=pa2.companyId and p3.branchId=pa2.branchId and p3.partyId=pa2.partyId and pa2.defaultChk = 'Y' "
//								+ "LEFT OUTER JOIN PartyAddress pa3 ON crg.companyId=pa3.companyId and crg.branchId=pa3.branchId and crg.accountHolderId=pa3.partyId and pa3.defaultChk = 'Y' "
//								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and (crg.beNo is not null AND crg.beNo != '') and "
//								+ "crg.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and c.igmLineNo=:lineNo and "
//								+ "((c.containerStatus = 'FCL' AND c.containerExamStatus = 'Y') OR c.containerStatus='LCL') and (c.gateOutId is null OR c.gateOutId = '') ")
//						List<Object[]> getBeforeSaveAssessmentData(@Param("cid") String cid, @Param("bid") String bid,
//								@Param("trans") String trans,@Param("igm") String igm,@Param("lineNo") String lineNo);
						
						
						@Query(value = "select c.igmTransId,c.igmNo,c.igmLineNo,crg.blNo,crg.beNo,p.profitcentreDesc,i.viaNo,i.igmDate,crg.blDate,"
								+ "p1.partyName,p2.partyName,crg.cargoValue,crg.cargoDuty,crg.commodityDescription,crg.importerId,crg.importerName,"
								+ "crg.importerSr,crg.importerAddress1,crg.importerAddress2,crg.importerAddress3,pa1.gstNo,c.cha,p3.partyName,"
								+ "pa2.srNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,crg.accountHolderId,crg.accountHolderName,pa3.srNo,"
								+ "pa3.gstNo,pa3.state,c.containerNo,c.containerSize,c.containerType,c.gateInDate,c.deStuffDate,c.gateOutDate"
								+ ",c.examinedPackages,c.typeOfContainer,c.scannerType,c.gateOutType,c.upTariffNo,c.profitcentreId,"
								+ "c.grossWt,c.cargoWt,c.ssrTransId,i.shippingAgent,i.shippingLine,c.containerStatus,c.gatePassNo,c.gateOutId,"
								+ "c.assesmentId,c.lastAssesmentDate,c.invoiceNo,c.invoiceDate,c.invoiceUptoDate,d.areaOccupied "
								+ "from Cfigmcn c "
								+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
								+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
								+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
								+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.shippingLine=p1.partyId "
								+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingAgent=p2.partyId "
								+ "LEFT OUTER JOIN PartyAddress pa1 ON crg.companyId=pa1.companyId and crg.branchId=pa1.branchId and crg.importerId=pa1.partyId and crg.importerSr=pa1.srNo "
								+ "LEFT OUTER JOIN Party p3 ON c.companyId=p3.companyId and c.branchId=p3.branchId and c.cha=p3.partyId "
								+ "LEFT OUTER JOIN PartyAddress pa2 ON p3.companyId=pa2.companyId and p3.branchId=pa2.branchId and p3.partyId=pa2.partyId and pa2.defaultChk = 'Y' "
								+ "LEFT OUTER JOIN PartyAddress pa3 ON crg.companyId=pa3.companyId and crg.branchId=pa3.branchId and crg.accountHolderId=pa3.partyId and pa3.defaultChk = 'Y' "
								+ "LEFT OUTER JOIN DestuffCrg d ON c.companyId=d.companyId and c.branchId=d.branchId and c.igmTransId=d.igmTransId and c.igmNo=d.igmNo and c.igmLineNo=d.igmLineNo and c.deStuffId=d.deStuffId and d.status = 'A' "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and (crg.beNo is not null AND crg.beNo != '') and "
								+ "crg.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and c.igmLineNo=:lineNo and "
								+ "((c.containerStatus = 'FCL' AND c.containerExamStatus = 'Y') OR c.containerStatus='LCL') and (c.gateOutId is null OR c.gateOutId = '') ")
						List<Object[]> getBeforeSaveAssessmentData(@Param("cid") String cid, @Param("bid") String bid,
								@Param("trans") String trans,@Param("igm") String igm,@Param("lineNo") String lineNo);
						
						@Modifying
						@Transactional
						@Query(value="UPDATE Cfigmcn c SET c.invoiceAmtOld=:invAmt,c.cargoDuty=:duty,c.assesmentId=:id,c.lastAssesmentId=:id,"
								+ "c.crgStorageDay=:crgday,c.cargoValue=:cvalue ,c.invoiceDaysOld=:invday,c.invoiceUptoDate=:invDate,"
								+ "c.lastAssesmentDate=:assDate,c.crgStorageAmt=:crgStorage "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
								+ "c.containerNo=:con")
						int updateInvoiceData(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
								@Param("con") String con,@Param("invAmt") BigDecimal invAmt,@Param("duty") BigDecimal cargoDuty,@Param("id") String id,
								@Param("crgday") BigDecimal crgStorageDay,@Param("cvalue") BigDecimal cvalue,@Param("invday")BigDecimal invoiceDaysOld,
								@Param("invDate") Date invDate,@Param("assDate") Date assDate,@Param("crgStorage") BigDecimal crgStorage);
						
						
//						@Modifying
//						@Transactional
//						@Query(value="UPDATE Cfigmcn c SET c.cargoDuty=:duty,c.assesmentId=:id,c.lastAssesmentId=:id,"
//								+ "c.crgStorageDay=c.crgStorageDay + :crgday,c.cargoValue=:cvalue ,c.invoiceUptoDate=:invDate,"
//								+ "c.lastAssesmentDate=:assDate,c.crgStorageAmt=ROUND(c.crgStorageAmt + :crgStorage,3) "
//								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
//								+ "c.containerNo=:con")
//						int updateInvoiceData2(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
//								@Param("con") String con,@Param("duty") BigDecimal cargoDuty,@Param("id") String id,@Param("crgday") BigDecimal crgStorageDay,
//								@Param("cvalue") BigDecimal cvalue,
//								@Param("invDate") Date invDate,@Param("assDate") Date assDate,@Param("crgStorage") BigDecimal crgStorage);
//						
//						
//						@Modifying
//						@Transactional
//						@Query(value="UPDATE Cfigmcn c SET c.cargoDuty=:duty,c.assesmentId=:id,c.lastAssesmentId=:id,"
//								+ "c.crgStorageDay=:crgday,c.cargoValue=:cvalue ,c.invoiceUptoDate=:invDate,"
//								+ "c.lastAssesmentDate=:assDate,c.crgStorageAmt=:crgStorage "
//								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
//								+ "c.containerNo=:con")
//						int updateInvoiceData1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
//								@Param("con") String con,@Param("duty") BigDecimal cargoDuty,@Param("id") String id,@Param("crgday") BigDecimal crgStorageDay,
//								@Param("cvalue") BigDecimal cvalue,
//								@Param("invDate") Date invDate,@Param("assDate") Date assDate,@Param("crgStorage") BigDecimal crgStorage);
//						
//						
//						@Modifying
//						@Transactional
//						@Query(value="UPDATE Cfigmcn c SET c.invoiceDate=:invDate,c.invoiceAssesed=:invass,c.invoiceNo=:invNo,c.creditType=:type,"
//								+ "c.billAmt=:billAmt,c.invoiceAmt=:invAmt,c.lastInvoiceNo=:invNo where c.companyId=:cid and c.branchId=:bid and "
//								+ "c.status='A' and c.assesmentId=:id")
//						int updateInvoiceDataAtProcess(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("invDate") 
//						Date invDate,@Param("invass") char invass,@Param("invNo") String invNo,@Param("type") char type,@Param("billAmt") BigDecimal billAMt,
//						@Param("invAmt") BigDecimal invAmt);
						
						@Modifying
						@Transactional
						@Query(value="UPDATE Cfigmcn c SET c.cargoDuty=:duty,c.assesmentId=:id,c.lastAssesmentId=:id,"
								+ "c.crgStorageDay=:crgday,c.cargoValue=:cvalue ,"
								+ "c.lastAssesmentDate=:assDate,c.crgStorageAmt=:crgStorage "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
								+ "c.containerNo=:con")
						int updateInvoiceData1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
								@Param("con") String con,@Param("duty") BigDecimal cargoDuty,@Param("id") String id,@Param("crgday") BigDecimal crgStorageDay,
								@Param("cvalue") BigDecimal cvalue,@Param("assDate") Date assDate,@Param("crgStorage") BigDecimal crgStorage);
						
//						
//						@Modifying
//						@Transactional
//						@Query(value="UPDATE Cfigmcn c SET c.cargoDuty=:duty,c.assesmentId=:id,c.lastAssesmentId=:id,"
//								+ "c.crgStorageDay=c.crgStorageDay + :crgday,c.cargoValue=:cvalue ,"
//								+ "c.lastAssesmentDate=:assDate,c.crgStorageAmt=ROUND(c.crgStorageAmt + :crgStorage,3) "
//								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
//								+ "c.containerNo=:con")
//						int updateInvoiceData2(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
//								@Param("con") String con,@Param("duty") BigDecimal cargoDuty,@Param("id") String id,@Param("crgday") BigDecimal crgStorageDay,
//								@Param("cvalue") BigDecimal cvalue,@Param("assDate") Date assDate,@Param("crgStorage") BigDecimal crgStorage);
						
						@Modifying
						@Transactional
						@Query(value="UPDATE Cfigmcn c SET c.cargoDuty=:duty,c.assesmentId=:id,c.lastAssesmentId=:id,"
								+ "c.crgStorageDay=c.crgStorageDay + :crgday,c.cargoValue=:cvalue ,"
								+ "c.lastAssesmentDate=:assDate "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
								+ "c.containerNo=:con")
						int updateInvoiceData2(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
								@Param("con") String con,@Param("duty") BigDecimal cargoDuty,@Param("id") String id,@Param("crgday") BigDecimal crgStorageDay,
								@Param("cvalue") BigDecimal cvalue,@Param("assDate") Date assDate);
						
						
						
						
						@Modifying
						@Transactional
						@Query(value="UPDATE Cfigmcn c SET c.invoiceDate=:invDate,c.invoiceAssesed=:invass,c.invoiceNo=:invNo,c.creditType=:type,"
								+ "c.billAmt=:billAmt,c.invoiceAmt=:invAmt,c.lastInvoiceNo=:invNo,c.invoiceUptoDate=:invUptoDate where c.companyId=:cid and c.branchId=:bid and "
								+ "c.status='A' and c.assesmentId=:id")
						int updateInvoiceDataAtProcess(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("invDate") 
						Date invDate,@Param("invass") char invass,@Param("invNo") String invNo,@Param("type") char type,@Param("billAmt") BigDecimal billAMt,
						@Param("invAmt") BigDecimal invAmt,@Param("invUptoDate") Date invUptoDate);
						
						@Modifying
						@Transactional
						@Query(value = "UPDATE Cfigmcn c SET c.invoiceDate=:invDate,c.invoiceAssesed=:invass,c.invoiceNo=:invNo,c.creditType=:type,"
								+ "c.billAmt=:billAmt,c.invoiceAmt=:invAmt,c.lastInvoiceNo=:invNo,c.invoiceUptoDate=:invUptoDate,c.crgStorageAmt=ROUND(c.crgStorageAmt + :crgStorage,3) "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
								+ "c.containerNo=:con")
						int updateInvoiceDataAtProcess1(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
								@Param("igm") String igm, @Param("con") String con, @Param("invDate") Date invDate,
								@Param("invass") char invass, @Param("invNo") String invNo, @Param("type") char type,
								@Param("billAmt") BigDecimal billAMt, @Param("invAmt") BigDecimal invAmt,
								@Param("invUptoDate") Date invUptoDate,@Param("crgStorage") BigDecimal crgStorage);
						
						@Modifying
						@Transactional
						@Query(value = "UPDATE Cfigmcn c SET c.invoiceDate=:invDate,c.invoiceAssesed=:invass,c.invoiceNo=:invNo,c.creditType=:type,"
								+ "c.billAmt=:billAmt,c.invoiceAmt=:invAmt,c.lastInvoiceNo=:invNo,c.invoiceUptoDate=:invUptoDate "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and "
								+ "c.containerNo=:con")
						int updateInvoiceDataAtProcess2(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
								@Param("igm") String igm, @Param("con") String con, @Param("invDate") Date invDate,
								@Param("invass") char invass, @Param("invNo") String invNo, @Param("type") char type,
								@Param("billAmt") BigDecimal billAMt, @Param("invAmt") BigDecimal invAmt,
								@Param("invUptoDate") Date invUptoDate);
						
						
						
						@Query(value="select COALESCE(a.crgStorageAmt,0) from Cfigmcn a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.igmTransId=:trans and "
								+ "a.igmNo=:igm and a.igmLineNo=:line and a.containerNo=:con")
						BigDecimal getRateByServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,
								@Param("igm") String igm,@Param("line") String line,@Param("con") String con);
					  
						
						@Query(value = "select COUNT(c) > 0 from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status='A' and "
								+ "(:igm is null OR :igm = '' OR c.igmNo=:igm) and (:item is null OR :item = '' OR c.igmLineNo=:item) and "
								+ "c.containerNo=:con and (c.gateOutId is null OR c.gateOutId = '')")
						boolean checkContainer(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
								@Param("item") String item, @Param("con") String con);

						@Query(value = "select NEW com.cwms.entities.Cfigmcn(c.igmTransId, c.profitcentreId, c.igmNo, c.igmLineNo, c.containerNo,"
								+ "c.containerSize, c.containerType, c.haz, c.containerStatus, c.upTariffFwd,"
								+ "c.upTariffNo, c.upTariffAmndNo, c.typeOfContainer) "
								+ "from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and c.status='A' and "
								+ "(:igm is null OR :igm = '' OR c.igmNo=:igm) and (:item is null OR :item = '' OR c.igmLineNo=:item) and "
								+ "(:con is null OR :con = '' OR c.containerNo=:con) and (c.gateOutId is null OR c.gateOutId = '')")
						List<Cfigmcn> conList(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
								@Param("item") String item, @Param("con") String con);

						@Modifying
						@Transactional
						@Query(value = "Update Cfigmcn c SET c.upTariffFwd=:fwdId,c.upTariffNo=:tarrifNo,c.upTariffAmndNo=:amdNo "
								+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmNo=:igm and c.igmTransId=:trans and "
								+ "c.containerNo=:con and (c.gateOutId is null OR c.gateOutId = '')")
						int updateConList(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
								@Param("trans") String trans, @Param("con") String con, @Param("fwdId") String fwdId,@Param("tarrifNo") String tariffNo,
								@Param("amdNo") String amdNo);
						
						
						@Query(value = "select DISTINCT c.igmNo,c.igmTransId,c.igmLineNo from Cfigmcn c where c.companyId=:cid and c.branchId=:bid and "
								+ "c.status = 'A' and c.igmNo=:igm and c.igmLineNo=:line and (c.gateOutId is null OR c.gateOutId = '')")
						Object getDataByIGMAndLineNo(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
								@Param("line") String line);
						
						@Modifying
						@Transactional
						@Query(value = "Update Cfigmcn cn SET cn.gateOutId=:id , cn.gateOutDate = CURRENT_DATE where cn.companyId=:cid and cn.branchId=:bid and "
								+ "cn.igmTransId=:trans and cn.igmNo=:igm and cn.igmLineNo=:line and cn.status != 'D'")
						int updategateOutId1(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
								@Param("igm") String igm,@Param("line") String line, @Param("id") String id);
						
						
					    @Query(value="select c.igmTransId,c.igmNo,c.igmLineNo,a.containerNo,a.containerSize,a.containerType,c.containerStatus,"
					    		+ "c.yardLocation,c.yardBlock,c.blockCellNo,DATE_FORMAT(a.gateInDate,'%d/%m/%Y'),c.holdStatus,DATE_FORMAT(c.holdDate,'%d/%m/%Y %H:%i'),"
					    		+ "DATE_FORMAT(c.releaseDate,'%d/%m/%Y %H:%i'),c.auctionStatus,a.noticeType "
					    		+ "from Auction a "
					    		+ "LEFT OUTER JOIN AuctionDetail d ON a.companyId=d.companyId and a.branchId=d.branchId and a.noticeId=d.noticeId "
					    		+ "LEFT OUTER JOIN Cfigmcn c ON a.companyId=c.companyId and a.branchId=c.branchId and d.igmTransId=c.igmTransId "
					    		+ "and d.igmNo=c.igmNo and d.igmLineNo=c.igmLineNo and a.containerNo=c.containerNo "
					    		+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and a.noticeType='F' and "
					    		+ "c.containerStatus = 'FCL' and (c.gateOutId is null OR c.gateOutId='') group by a.containerNo")
					    List<Object[]> getAuctionContainerWiseData(@Param("cid") String cid, @Param("bid") String bid);
					    
						@Modifying
						@Transactional
						@Query(value = "Update Cfigmcn cn SET cn.auctionStatus=:id where cn.companyId=:cid and cn.branchId=:bid and "
								+ "cn.igmTransId=:trans and cn.igmNo=:igm and cn.igmLineNo=:line and cn.containerNo=:con and cn.status != 'D'")
						int updateAuctionExaminationStatus(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
								@Param("igm") String igm,@Param("line") String line, @Param("con") String con,@Param("id") char id);
						
						  @Query(value="select a.igmTransId,a.igmNo,a.igmLineNo,a.importerName,a.commodityDescription,DATE_FORMAT(c.gateInDate,'%d/%m/%Y'),"
						  		    + "i.holdStatus,DATE_FORMAT(i.holdDate,'%d/%m/%Y %H:%i'),DATE_FORMAT(i.releaseDate,'%d/%m/%Y %H:%i'),i.auctionStatus,a.noticeType "
						    		+ "from AuctionDetail a "
						    		+ "LEFT OUTER JOIN Cfigmcn c ON a.companyId=c.companyId and a.branchId=c.branchId and a.igmTransId=c.igmTransId "
						    		+ "and a.igmNo=c.igmNo and a.igmLineNo=c.igmLineNo "
						    		+ "LEFT OUTER JOIN Cfigmcrg i ON a.companyId=i.companyId and a.branchId=i.branchId and a.igmTransId=i.igmTransId "
						    		+ "and a.igmNo=i.igmNo and a.igmLineNo=i.igmLineNo "
						    		+ "where a.companyId=:cid and a.branchId=:bid and a.status='A' and a.noticeType='F' "
						    		+ "and (c.gateOutId is null OR c.gateOutId='') group by a.igmTransId,a.igmNo,a.igmLineNo")
						    List<Object[]> getAuctionCargoWiseData(@Param("cid") String cid, @Param("bid") String bid);
						    
							@Modifying
							@Transactional
							@Query(value = "Update Cfigmcrg cn SET cn.auctionStatus=:id where cn.companyId=:cid and cn.branchId=:bid and "
									+ "cn.igmTransId=:trans and cn.igmNo=:igm and cn.igmLineNo=:line and cn.status != 'D'")
							int updateAuctionExaminationStatus1(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
									@Param("igm") String igm,@Param("line") String line, @Param("id") String id);
							
							
							@Modifying
							@Transactional
							@Query(value = "Update Cfigmcn c SET c.upTariffFwd=:fwdId,c.upTariffNo=:tarrifNo,c.upTariffAmndNo=:amdNo,"
									+ "c.tariffCode=:code, c.subItemNo=:subItem "
									+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmNo=:igm and c.igmTransId=:trans and "
									+ "c.containerNo=:con and (c.gateOutId is null OR c.gateOutId = '')")
							int updateConList1(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
									@Param("trans") String trans, @Param("con") String con, @Param("fwdId") String fwdId,@Param("tarrifNo") String tariffNo,
									@Param("amdNo") String amdNo,@Param("code") String code,@Param("subItem") String subItem);
							
							@Modifying
							@Transactional
							@Query(value = "Update Cfigmcrg c SET c.customerId=:fwdId "
									+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.igmNo=:igm and c.igmTransId=:trans and "
									+ "c.igmLineNo=:line")
							int updateIGMCrg(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
									@Param("trans") String trans, @Param("line") String line, @Param("fwdId") String fwdId);
							
							
							@Modifying
							@Transactional
							@Query(value = "Update Cfigmcn cn SET cn.ssrTransId=:id where cn.companyId=:cid and cn.branchId=:bid and "
									+ "cn.igmTransId=:trans and cn.igmNo=:igm and cn.containerNo=:con and cn.status != 'D'")
							int updateSSRID(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
									@Param("igm") String igm, @Param("con") String con,@Param("id") String id);
							
							
							@Query(value="select c.igmNo,c.igmTransId,c.igmLineNo,i.blNo,c.beNo,c.containerNo,c.containerNo,c.createdDate "
									+ "from Cfigmcn c "
									+ "LEFT OUTER JOIN Cfigmcrg i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and "
									+ "c.igmTransId=i.igmTransId and c.igmLineNo=i.igmLineNo "
									+ "where c.companyId=:cid and c.branchId=:bid and (:igm is null OR :igm = '' OR c.igmNo=:igm) and "
									+ "(:item is null OR :item = '' OR c.igmLineNo=:item) and (:blNo is null OR :blNo = '' OR i.blNo=:blNo) and "
									+ "(:con is null OR :con = '' OR c.containerNo=:con) and (:beNo is null OR :beNo = '' OR c.beNo=:beNo) order by c.createdDate desc")
						    List<Object[]> importContainerHistory1(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
									@Param("item") String item, @Param("blNo") String blNo, @Param("con") String con,@Param("beNo") String beNo);
						    
							@Query(value="select c.igmNo,DATE_FORMAT(igm.igmDate,'%d/%m/%Y'),c.igmLineNo,c.beNo,DATE_FORMAT(c.beDate,'%d/%m/%Y'),c.cargoValue,"
									+ "c.cargoDuty,i.blNo,DATE_FORMAT(i.blDate,'%d/%m/%Y'),p.profitcentreDesc,i.noOfPackages,i.grossWeight,DATE_FORMAT(c.oocDate,'%d/%m/%Y'),"
									+ "v.vesselName,igm.viaNo,c.doNo,(select COUNT(c1) from Cfigmcn c1 where c1.companyId=:cid and c1.branchId=:bid and "
									+ "c1.igmTransId=:trans and c1.igmNo=:igm and c1.igmLineNo=:item),sl.partyName,i.importerName,ch.partyName,igm.port,i.origin,"
									+ "DATE_FORMAT(c.doDate,'%d/%m/%Y'),i.commodityDescription,DATE_FORMAT(c.forceEntryDate,'%d/%m/%Y'),c.forceEntryApproval "
									+ "from Cfigmcn c "
									+ "LEFT OUTER JOIN Cfigmcrg i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and "
									+ "c.igmTransId=i.igmTransId and c.igmLineNo=i.igmLineNo "
									+ "LEFT OUTER JOIN CFIgm igm ON c.companyId=igm.companyId and c.branchId=igm.branchId and c.igmNo=igm.igmNo and "
									+ "c.igmTransId=igm.igmTransId "
									+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
									+ "LEFT OUTER JOIN Vessel v ON igm.companyId=v.companyId and igm.branchId=v.branchId and igm.vesselId=v.vesselId "
									+ "LEFT OUTER JOIN Party sl ON igm.companyId=sl.companyId and igm.branchId=sl.branchId and igm.shippingLine=sl.partyId "
									+ "LEFT OUTER JOIN Party ch ON c.companyId=ch.companyId and c.branchId=ch.branchId and c.cha=ch.partyId "
									+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmTransId=:trans and "
									+ "c.igmLineNo=:item and c.containerNo=:con and c.status = 'A' group by c.igmNo,c.igmLineNo")
						    Object importContainerHistory2(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
									@Param("item") String item, @Param("con") String con);
							
							
							@Query(value="select c.containerNo,c.containerSize,c.containerType,DATE_FORMAT(c.gateInDate,'%d/%m/%Y %H:%i'),c.scannerType,"
									+ "c.scanningDoneStatus,DATE_FORMAT(c.scanningDoneDate,'%d/%m/%Y'),c.noOfPackages,c.examinedPackages,c.cargoWt,"
									+ "c.typeOfContainer,DATE_FORMAT(c.sealCutReqDate,'%d/%m/%Y %H:%i'),DATE_FORMAT(c.containerExamDate,'%d/%m/%Y %H:%i'),"
									+ "c.gateOutType,c.gatePassNo,DATE_FORMAT(p.gatePassDate,'%d/%m/%Y %H:%i'),DATE_FORMAT(c.gateOutDate,'%d/%m/%Y %H:%i'),"
									+ "c.deStuffId,DATE_FORMAT(c.deStuffDate,'%d/%m/%Y'),em.gatePassId,DATE_FORMAT(em.gatePassDate,'%d/%m/%Y %H:%i'),"
									+ "em.gateOutId,DATE_FORMAT(em.gateOutDate,'%d/%m/%Y %H:%i'),c.holdStatus "
									+ "from Cfigmcn c "
									+ "LEFT OUTER JOIN ImportGatePass p ON c.companyId=p.companyId and c.branchId=p.branchId and c.gatePassNo=p.gatePassId and p.srNo=1 "
									+ "LEFT OUTER JOIN ImportInventory in ON c.companyId=in.companyId and c.branchId=in.branchId and c.igmTransId=in.igmTransId and "
									+ "c.igmNo=in.igmNo and c.containerNo=in.containerNo and c.gateInId=in.gateInId "
									+ "LEFT OUTER JOIN EmptyInventory em ON c.companyId=em.companyId and c.branchId=em.branchId and c.gateInId=em.gateInId and "
									+ "c.igmTransId=em.erpDocRefNo and c.igmNo=em.docRefNo and c.containerNo=em.containerNo "
									+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmTransId=:trans and "
									+ "c.igmLineNo=:item and c.status = 'A'")
						    List<Object[]> importContainerHistory3(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
									@Param("item") String item);
						    
							@Query(value="select DISTINCT c.gateInId,DATE_FORMAT(c.gateInDate,'%d/%m/%Y %H:%i'),c.containerNo "
									+ "from ManualGateIn c "
									+ "where c.companyId=:cid and c.branchId=:bid and c.docRefNo=:igm and c.erpDocRefNo=:trans and "
									+ "c.lineNo=:item and c.status = 'A'")
						    List<Object[]> importContainerHistory4(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
									@Param("item") String item);
						    
							@Query(value="select DISTINCT c.invoiceNo,DATE_FORMAT(c.invoiceDate,'%d/%m/%Y %H:%i') "
									+ "from AssessmentSheet c "
									+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmTransId=:trans and "
									+ "c.igmLineNo=:item and c.status = 'A' and (c.invoiceNo is not null and c.invoiceNo != '')")
						    List<Object[]> importContainerHistory5(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
									@Param("item") String item);
						    
						    
						    // Common Container History 
						    
						    @Query(value="select c.igmNo,c.igmTransId,c.igmLineNo,i.blNo,c.beNo,c.containerNo,c.containerNo,c.createdDate "
						    		+ "from Cfigmcn c "
									+ "LEFT OUTER JOIN Cfigmcrg i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and "
									+ "c.igmTransId=i.igmTransId and c.igmLineNo=i.igmLineNo "
						    		+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.containerNo=:con order by c.createdDate desc")
						    List<Object[]> getDataByContainerNo(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
						    
						    @Query(value="select c.containerNo,c.containerSize,'CFS Import',c.containerStatus,c.gateOutType,p1.partyName,CONCAT(c.igmNo,' : ',c.igmLineNo),"
						    		+ "i.viaNo,v.vesselName,DATE_FORMAT(c.createdDate,'%d/%m/%Y %H:%i'),c.createdBy,CONCAT(c.yardLocation,'-',c.yardBlock,'-',c.blockCellNo),"
						    		+ "crg.destination,DATE_FORMAT(c.forceEntryDate,'%d/%m/%Y %H:%i'),c.holdStatus,c.gateInId,DATE_FORMAT(g.inGateInDate,'%d/%m/%Y %H:%i'),"
						    		+ "g.createdBy,c.sealCutWoTransId,c.sealCutApprovedBy,DATE_FORMAT(c.sealCutApprovedDate,'%d/%m/%Y %H:%i'),c.containerExamWoTransId,"
						    		+ "DATE_FORMAT(c.containerExamDate,'%d/%m/%Y %H:%i'),c.containerExamApprovedBy,c.deStuffId,DATE_FORMAT(c.destuffWoDate,'%d/%m/%Y %H:%i'),"
						    		+ "c.destuffWoCreatedBy,f.oprInvoiceNo,DATE_FORMAT(c.invoiceDate,'%d/%m/%Y %H:%i'),f.approvedBy,gp.gatePassId,gp.approvedBy,"
						    		+ "DATE_FORMAT(gp.gatePassDate,'%d/%m/%Y %H:%i'),go.gateOutId,DATE_FORMAT(go.gateOutDate,'%d/%m/%Y %H:%i'),go.approvedBy "
						    		+ "from Cfigmcn c "
						    		+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
						    		+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.shippingAgent=p1.partyId "
						    		+ "LEFT OUTER JOIN Vessel v ON i.companyId=v.companyId and i.branchId=v.branchId and i.vesselId=v.vesselId "
						    		+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId "
						    		+ "and c.igmLineNo=crg.igmLineNo "
						    		+ "LEFT OUTER JOIN GateIn g ON c.companyId=g.companyId and c.branchId=g.branchId and c.gateInId=g.gateInId "
						    		+ "LEFT OUTER JOIN FinTrans f ON c.companyId=f.companyId and c.branchId=f.branchId and c.invoiceNo=f.oprInvoiceNo "
						    		+ "LEFT OUTER JOIN ImportGatePass gp ON c.companyId=gp.companyId and c.branchId=gp.branchId and c.gatePassNo=gp.gatePassId and gp.srNo=1 "
						    		+ "LEFT OUTER JOIN GateOut go ON c.companyId=go.companyId and c.branchId=go.branchId and c.gateOutId=go.gateOutId and go.srNo='1'"
						    		+ "where c.companyId=:cid and c.branchId=:bid and c.igmNo=:igm and c.igmTransId=:trans and "
									+ "c.igmLineNo=:item and c.containerNo=:con and c.status = 'A' group by c.igmNo,c.igmLineNo")
						    List<Object[]> getContatainerData(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
									@Param("item") String item, @Param("con") String con);
							
							
}

