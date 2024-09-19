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
	
}
