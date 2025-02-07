package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ImportGatePass;

public interface ImportGatePassRepo extends JpaRepository<ImportGatePass, String> {

	@Query(value="select i from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.igmNo=:igm and i.igmLineNo=:line "
			+ "and i.containerNo=:con and i.gatePassId=:gate and i.srNo=:sr and i.status !='D'")
	ImportGatePass getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("line") String line,
			@Param("con") String con,@Param("gate") String gate,@Param("sr") int sr);
	
	
	@Query("SELECT new com.cwms.entities.ImportGatePass(i.gatePassId, i.srNo, i.profitcentreId, i.gatePassDate, i.igmNo, i.igmLineNo, "
			+ "i.igmTransId, i.shift, i.transType, i.importerName, i.importerAddress1, i.importerAddress2, i.importerAddress3, p.partyName,"
			+ " i.containerNo, i.containerSize, i.containerType, i.blNo, i.blDate, i.viaNo, i.commodity, i.grossWt, i.noOfPackage, "
			+ "i.qtyTakenOut, i.vehicleQtyTakenOut, i.gwTakenOut, i.vehicleNo, i.driverName, i.comments, i.destuffId, i.grnNo, i.grnDate, "
			+ "i.cinNo, i.cinDate, i.stampDuty, i.doNo, i.doDate, i.doValidityDate, i.oocNo, i.oocDate, i.status, i.createdBy, "
			+ "i.yardLocation, i.yardBlock, i.blockCellNo, i.vehStatus, i.mtyYardLocation, i.gateOutQty, i.boe) FROM ImportGatePass i "
			+ "LEFT OUTER JOIN Party p ON i.companyId=p.companyId and i.branchId=p.branchId and i.cha = p.customerCode "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm AND i.igmLineNo=:line AND i.gatePassId=:gate AND i.status != 'D'")
	List<ImportGatePass> getData(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("line") String line, @Param("gate") String gate);
	
	@Query("SELECT i FROM ImportGatePass i "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm AND i.igmLineNo=:line AND i.gatePassId=:gate AND i.status != 'D'")
	List<ImportGatePass> getData3(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("line") String line, @Param("gate") String gate);
	
	@Query("SELECT new com.cwms.entities.ImportGatePass(i.gatePassId, i.srNo, i.profitcentreId, i.gatePassDate, i.igmNo, i.igmLineNo, "
			+ "i.igmTransId, i.shift, i.transType, i.importerName, i.importerAddress1, i.importerAddress2, i.importerAddress3, p.partyName,"
			+ " i.containerNo, i.containerSize, i.containerType, i.blNo, i.blDate, i.viaNo, i.commodity, i.grossWt, i.noOfPackage, "
			+ "i.qtyTakenOut, i.vehicleQtyTakenOut, i.gwTakenOut, i.vehicleNo, i.driverName, i.comments, i.destuffId, i.grnNo, i.grnDate, "
			+ "i.cinNo, i.cinDate, i.stampDuty, i.doNo, i.doDate, i.doValidityDate, i.oocNo, i.oocDate, i.status, i.createdBy, "
			+ "i.yardLocation, i.yardBlock, i.blockCellNo, i.vehStatus, i.mtyYardLocation, i.gateOutQty, i.boe) FROM ImportGatePass i "
			+ "LEFT OUTER JOIN Party p ON i.companyId=p.companyId and i.branchId=p.branchId and i.cha = p.customerCode "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm AND i.igmTransId=:line AND i.gatePassId=:gate AND i.status != 'D'")
	List<ImportGatePass> getData2(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("line") String line, @Param("gate") String gate);
	
	
	@Query(value="select i.gatePassId from ImportGatePass i WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm "
			+ "AND i.igmTransId=:trans AND (:line is null OR :line = '' OR i.igmLineNo=:line)  AND i.status != 'D' "
			+ "AND i.gatePassType='ITEM' order by i.gatePassId desc")
	List<String> getLastGatePassId(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, 
			@Param("trans") String trans,@Param("line") String line);
	
	@Query(value="select i.gatePassId,i.transType,i.igmLineNo from ImportGatePass i WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm "
			+ "AND i.igmTransId=:trans AND (:line is null OR :line = '' OR i.igmLineNo=:line)  AND i.status != 'D' "
			+ "AND i.gatePassType='ITEM' order by i.gatePassId desc")
	List<Object[]> getLastGatePassId1(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, 
			@Param("trans") String trans,@Param("line") String line);
	
	@Query("SELECT new com.cwms.entities.ImportGatePass(i.gatePassId, i.srNo, i.profitcentreId, i.gatePassDate, i.igmNo, i.igmLineNo, "
			+ "i.igmTransId, i.shift, i.transType, i.importerName, i.importerAddress1, i.importerAddress2, i.importerAddress3, p.partyName,"
			+ " i.containerNo, i.containerSize, i.containerType, i.blNo, i.blDate, i.viaNo, i.commodity, i.grossWt, i.noOfPackage, "
			+ "i.qtyTakenOut, i.vehicleQtyTakenOut, i.gwTakenOut, i.vehicleNo, i.driverName, i.comments, i.destuffId, i.grnNo, i.grnDate, "
			+ "i.cinNo, i.cinDate, i.stampDuty, i.doNo, i.doDate, i.doValidityDate, i.oocNo, i.oocDate, i.status, i.createdBy, "
			+ "i.yardLocation, i.yardBlock, i.blockCellNo, i.vehStatus, i.mtyYardLocation, i.gateOutQty, i.boe) FROM ImportGatePass i "
			+ "LEFT OUTER JOIN Party p ON i.companyId=p.companyId and i.branchId=p.branchId and i.cha = p.customerCode "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm AND i.gatePassId=:gate AND i.status != 'D'")
	List<ImportGatePass> getData2(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("gate") String gate);
	
	@Query("SELECT i FROM ImportGatePass i "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm AND i.igmLineNo=:line AND i.gatePassId=:gate AND i.status != 'D'")
	List<ImportGatePass> getData1(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("line") String line, @Param("gate") String gate);
	
	@Query("SELECT i FROM ImportGatePass i "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm  AND i.gatePassId=:gate AND i.status != 'D' and i.gatePassType='CON'")
	List<ImportGatePass> getData3(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("gate") String gate);
	
	@Query("SELECT i FROM ImportGatePass i "
			+ "WHERE i.companyId=:cid AND i.branchId=:bid AND i.igmNo=:igm AND i.igmLineNo=:line "
			+ "AND i.gatePassId=:gate AND i.status != 'D' AND i.srNo=:sr")
	ImportGatePass getData2(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm, @Param("line") String line, 
			@Param("gate") String gate,@Param("sr") int sr);

	
	@Query(value="select i.gatePassId, i.srNo, i.profitcentreId, i.invoiceNo, i.igmNo,"
			+ "i.igmLineNo, i.igmTransId, DATE_FORMAT(i.gatePassDate,'%d %M %Y'), i.transType, i.importerName,i.containerNo "
			+ "from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and i.gatePassType='ITEM' "
			+ "AND ((:val is null OR :val = '' OR i.containerNo LIKE CONCAT('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR i.gatePassId LIKE CONCAT('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR i.igmNo LIKE CONCAT('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR i.igmLineNo LIKE CONCAT('%',:val,'%'))) order by i.gatePassId desc")
	List<Object[]> searchForItemWise(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value="select i.gatePassId, i.srNo, i.profitcentreId, i.invoiceNo, i.igmNo,"
			+ "i.igmLineNo, i.igmTransId, DATE_FORMAT(i.gatePassDate,'%d %M %Y'), i.transType, i.importerName,i.containerNo "
			+ "from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and i.gatePassType='CON' "
			+ "AND ((:val is null OR :val = '' OR i.containerNo LIKE CONCAT('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR i.gatePassId LIKE CONCAT('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR i.igmNo LIKE CONCAT('%',:val,'%')) OR "
			+ "(:val is null OR :val = '' OR i.igmLineNo LIKE CONCAT('%',:val,'%'))) order by i.gatePassId desc")
	List<Object[]> searchForContWise(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Transactional
	@Modifying
	@Query(value="Update ImportGatePass i SET i.vehStatus = 'Y' where i.companyId=:cid and i.branchId=:bid and i.gatePassId=:id")
	int updateVehStatus(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id);
	
	@Query(value = "select i from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and i.gatePassType = 'ITEM' "
			+ "and i.igmNo=:igm and i.igmLineNo=:line and (i.gateOutId is null OR i.gateOutId = '') order by i.gatePassDate desc")
	List<ImportGatePass> searchDataForGateOut(@Param("cid") String cid, @Param("bid") String bid,@Param("igm") String igm,@Param("line") String line);
	
	
//	@Query(value="select distinct i.gatePassId,i.vehicleNo from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and "
//			+ "(:val is null OR :val = '' OR i.gatePassId LIKE CONCAT ('%',:val,'%') OR i.vehicleNo LIKE CONCAT ('%',:val,'%')) "
//			+ "and (i.gateOutId is null OR i.gateOutId = '')")
//	List<Object[]> getGatePassDataByGatePassIdAndVehicleNo(@Param("cid") String cid, @Param("bid") String bid,@Param("val") String val);
//	
	
	@Query(value="select distinct i.gatePassId,c.vehicleNo "
			+ "from ImportGatePass i "
			+ "LEFT OUTER JOIN CfImportGatePassVehDtl c ON i.companyId=c.companyId and i.branchId=c.branchId and i.gatePassId=c.gatePassId "
			+ "where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and "
			+ "(:val is null OR :val = '' OR i.gatePassId LIKE CONCAT ('%',:val,'%') OR c.vehicleNo LIKE CONCAT ('%',:val,'%')) "
			+ "and (i.gateOutId is null OR i.gateOutId = '')")
	List<Object[]> getGatePassDataByGatePassIdAndVehicleNo(@Param("cid") String cid, @Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select i from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and "
			+ "i.gatePassId=:val and (i.gateOutId is null OR i.gateOutId = '')")
	List<ImportGatePass> getDataByGatePassId(@Param("cid") String cid, @Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select i from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and "
			+ "i.gatePassId=:val and i.srNo=:sr")
	ImportGatePass getDataByGatePassIdAndSrNo(@Param("cid") String cid, @Param("bid") String bid,@Param("val") String val,@Param("sr") int sr);
	
	@Query(value="select i.igmLineNo from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and "
			+ "i.igmNo=:igm and i.igmTransId=:trans and (:line is null OR :line = '' OR i.igmLineNo=:line) "
			+ "and (i.gateOutId is null OR i.gateOutId = '')")
	List<String> getItems(@Param("cid") String cid, @Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
			@Param("line") String line);
	
	@Query(value="select distinct i.gatePassId from ImportGatePass i where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and "
			+ "i.igmNo=:igm and i.igmTransId=:trans and i.containerNo=:con and (i.gateOutId is null OR i.gateOutId = '')")
	String getGatePassIdFromContainerNo(@Param("cid") String cid, @Param("bid") String bid,@Param("igm") String igm,@Param("trans") String trans,
			@Param("con") String con);
	
	
	
	
	
	
	
	
	@Query(value="select DISTINCT i.gatePassId,DATE_FORMAT(i.gatePassDate,'%d/%m/%Y %H:%i'),i.igmNo,i.igmLineNo,i.blNo,"
			+ "DATE_FORMAT(i.blDate,'%d/%m/%Y'),i.boe,DATE_FORMAT(i.beDate,'%d/%m/%Y'),i.doNo,DATE_FORMAT(i.doValidityDate,'%d/%m/%Y'),"
			+ "p1.partyName,i.importerName,p2.partyName,v.vesselName,c.viaNo,i.commodity,i.comments,i.containerNo,i.containerSize,"
			+ "i.containerType,i.vehicleNo,DATE_FORMAT(cn.gateInDate,'%d/%m/%Y'),i.yardLocation "
			+ "From ImportGatePass i "
			+ "LEFT OUTER JOIN Cfigmcn cn ON i.companyId = cn.companyId and i.profitcentreId = cn.profitcentreId and i.branchId = cn.branchId "
		    + "and i.igmNo = cn.igmNo and i.igmTransId = cn.igmTransId and i.igmLineNo = cn.igmLineNo and i.containerNo = cn.containerNo "
			+ "LEFT OUTER JOIN CFIgm c ON i.companyId=c.companyId and i.branchId=c.branchId and i.igmNo=c.igmNo and i.igmTransId=c.igmTransId "
			+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.cha=p1.customerCode "
			+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.sl=p2.partyId "
			+ "LEFT OUTER JOIN Vessel v ON c.companyId=v.companyId and c.branchId=v.branchId and c.vesselId=v.vesselId "
			+ "where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and i.gatePassId=:gate")
	List<Object[]> getDataForImportGatePassItemWiseReport(@Param("cid") String cid, @Param("bid") String bid,@Param("gate") String gate);
	
	
	@Query(value="select DISTINCT i.igmNo,i.igmLineNo,i.gatePassId,DATE_FORMAT(c.igmDate,'%d/%m/%Y'),DATE_FORMAT(i.gatePassDate,'%d/%m/%Y %H:%i'),"
			+ "i.boe,DATE_FORMAT(i.beDate,'%d/%m/%Y'),i.blNo,v.vesselName,c.voyageNo,d.containerNo,p1.partyName,d.containerSize,"
			+ "i.importerName,p2.partyName,i.qtyTakenOut,DATE_FORMAT(i.invoiceDate,'%d/%m/%Y'),i.gwTakenOut,i.comments,"
			+ "veh.vehicleNo,veh.qtyTakenOut,i.commodity "
			+ "From ImportGatePass i "
			+ "LEFT OUTER JOIN Destuff d ON i.companyId=d.companyId and i.branchId=d.branchId and i.destuffId=d.deStuffId and "
			+ "i.igmTransId=d.igmTransId and i.igmNo=d.igmNo "
			+ "LEFT OUTER JOIN CFIgm c ON i.companyId=c.companyId and i.branchId=c.branchId and i.igmNo=c.igmNo and i.igmTransId=c.igmTransId "
			+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.cha=p1.customerCode "
			+ "LEFT OUTER JOIN Party p2 ON c.companyId=p2.companyId and c.branchId=p2.branchId and c.shippingAgent=p2.partyId "
			+ "LEFT OUTER JOIN Vessel v ON c.companyId=v.companyId and c.branchId=v.branchId and c.vesselId=v.vesselId "
			+ "LEFT OUTER JOIN CfImportGatePassVehDtl veh ON i.companyId=veh.companyId and i.branchId=veh.branchId and "
			+ "i.igmNo=veh.igmNo and i.igmTransId=veh.igmTransId and i.gatePassId=veh.gatePassId "
			+ "where i.companyId=:cid and i.branchId=:bid and i.status != 'D' and i.gatePassId=:gate")
	List<Object[]> getDataForImportGatePassItemWiseLCLReport(@Param("cid") String cid, @Param("bid") String bid,@Param("gate") String gate);
}
