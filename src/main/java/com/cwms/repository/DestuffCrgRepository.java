package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.DestuffCrg;

public interface DestuffCrgRepository extends JpaRepository<DestuffCrg, String> {
	@Query(value="select d from DestuffCrg d where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
			+ "and d.igmLineNo=:line and d.deStuffId=:destuff and d.status != 'D'")
	List<DestuffCrg> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm,
			@Param("line") String line,@Param("destuff") String destuff);
	
	
	@Query(value="select d from DestuffCrg d where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
			+ " and d.deStuffId=:destuff and d.status != 'D'")
	List<DestuffCrg> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm,
			@Param("destuff") String destuff);
	
	@Query(value="select d from DestuffCrg d where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
			+ "and d.igmLineNo=:line and d.deStuffId=:destuff and d.deStuffLineId=:deStuffLineId and d.status != 'D'")
	DestuffCrg getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm,
			@Param("line") String line,@Param("destuff") String destuff,@Param("deStuffLineId") String deStuffLineId);
	
	@Query(value="select d from DestuffCrg d where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
			+ "and d.igmLineNo=:line and d.deStuffId=:destuff and d.status != 'D'")
	DestuffCrg getSingleData1(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm,
			@Param("line") String line,@Param("destuff") String destuff);
	
	@Query(value="select d from DestuffCrg d where d.companyId=:cid and d.branchId=:bid and d.igmTransId=:igmTransId and d.igmNo=:igm "
			+ "and d.igmLineNo=:line and d.status != 'D'")
	DestuffCrg getSingleData2(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm,
			@Param("line") String line);
	
	
	@Query(value="select New com.cwms.entities.DestuffCrg(d.deStuffId, d.deStuffLineId, d.deStuffDate, d.igmTransId, d.igmNo,"
			+ "d.igmLineNo, d.commodityDescription, d.grossWeight, d.actualNoOfPackages,d.importerName, d.importerAddress1, "
			+ "d.importerAddress2, d.importerAddress3,d.areaOccupied, d.cargoType, d.profitcentreId) from DestuffCrg d where d.companyId=:cid and "
			+ "d.branchId=:bid and d.igmNo=:igm and d.igmLineNo=:igmline and d.igmTransId=:igmTransId and d.status != 'D'")
	List<DestuffCrg> getDataByIgmAndTransId(@Param("cid") String cid,@Param("bid") String bid,
			@Param("igmTransId") String igmTransId,@Param("igm") String igm,@Param("igmline") String igmline);
	
	
	@Query(value="select distinct d.igmLineNo from DestuffCrg d where d.companyId=:cid and "
			+ "d.branchId=:bid and d.igmNo=:igm and d.igmTransId=:igmTransId and d.status != 'D' and "
			+ "(d.examTallyId is null OR d.examTallyId = '')")
	List<String> getLineDataDataByIgmAndTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("igmTransId") String igmTransId,@Param("igm") String igm);
	
	
	
	
	
	
	
	
	
	
	@Query(value = "select DISTINCT cn.destuffWoTransId, DATE_FORMAT(cn.destuffWoDate, '%d/%m/%Y %h:%i'), c.igmLineNo, c.igmNo, crg.beNo, "
	        + "DATE_FORMAT(crg.beDate, '%d/%m/%Y %h:%i'), crg.chaName, crg.importerName, p1.partyName, crg.commodityDescription, d.containerNo, "
	        + "d.containerSize, d.containerType, DATE_FORMAT(d.gateInDate, '%d/%m/%Y %h:%i'), GROUP_CONCAT(j.jarDtlDesc), cn.typeOfContainer, d.containerSealNo "
	        + "from DestuffCrg c "
	        + "LEFT OUTER JOIN Destuff d ON c.companyId = d.companyId and c.branchId = d.branchId and c.igmTransId = d.igmTransId "
	        + "and c.profitcentreId = d.profitcentreId and c.igmNo = d.igmNo and c.deStuffId = d.deStuffId "
	        + "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId = crg.companyId and c.branchId = crg.branchId and c.igmTransId = crg.igmTransId "
	        + "and c.igmNo = crg.igmNo and c.igmLineNo = crg.igmLineNo and c.profitcentreId = crg.profitcentreId "
	        + "LEFT OUTER JOIN CFIgm i ON c.companyId = i.companyId and c.profitcentreId = i.profitcentreId and c.branchId = i.branchId "
	        + "and c.igmNo = i.igmNo and c.igmTransId = i.igmTransId "
	        + "LEFT OUTER JOIN Cfigmcn cn ON d.companyId = cn.companyId and d.profitcentreId = cn.profitcentreId and d.branchId = cn.branchId "
	        + "and d.igmNo = cn.igmNo and d.igmTransId = cn.igmTransId and d.igmLineNo = cn.igmLineNo and d.containerNo = cn.containerNo "
	        + "LEFT OUTER JOIN EquipmentActivity e ON cn.companyId = e.companyId and cn.branchId = e.branchId and "
	        + "cn.igmTransId = e.erpDocRefNo and cn.igmNo = e.docRefNo and cn.containerNo = e.containerNo and cn.igmLineNo = e.subDocRefNo "
	        + "LEFT OUTER JOIN JarDetail j ON e.companyId = j.companyId and e.equipment = j.jarDtlId and j.jarId = 'J00012' "
	        + "LEFT OUTER JOIN Party p1 ON i.companyId = p1.companyId and i.branchId = p1.branchId and i.shippingLine = p1.partyId "
	        + "where c.companyId = :cid and c.branchId = :bid and c.status != 'D' and c.igmNo = :igm and c.igmLineNo = :line and "
	        + "c.igmTransId = :trans and d.containerStatus <> 'E' group by d.containerNo")
	List<Object[]> getImportReportsDataByDestuffIgmWise(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
	                                                    @Param("trans") String trans, @Param("line") String line);
	
	
	
	@Query(value = "select DISTINCT cn.destuffWoTransId, DATE_FORMAT(cn.destuffWoDate, '%d/%m/%Y %h:%i'), c.igmLineNo, c.igmNo, crg.beNo, "
	        + "DATE_FORMAT(crg.beDate, '%d/%m/%Y %h:%i'), crg.chaName, crg.importerName, p1.partyName, crg.commodityDescription, d.containerNo, "
	        + "d.containerSize, d.containerType, DATE_FORMAT(d.gateInDate, '%d/%m/%Y %h:%i'), GROUP_CONCAT(j.jarDtlDesc), cn.typeOfContainer, d.containerSealNo "
	        + "from DestuffCrg c "
	        + "LEFT OUTER JOIN Destuff d ON c.companyId = d.companyId and c.branchId = d.branchId and c.igmTransId = d.igmTransId "
	        + "and c.profitcentreId = d.profitcentreId and c.igmNo = d.igmNo and c.deStuffId = d.deStuffId "
	        + "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId = crg.companyId and c.branchId = crg.branchId and c.igmTransId = crg.igmTransId "
	        + "and c.igmNo = crg.igmNo and c.igmLineNo = crg.igmLineNo and c.profitcentreId = crg.profitcentreId "
	        + "LEFT OUTER JOIN CFIgm i ON c.companyId = i.companyId and c.profitcentreId = i.profitcentreId and c.branchId = i.branchId "
	        + "and c.igmNo = i.igmNo and c.igmTransId = i.igmTransId "
	        + "LEFT OUTER JOIN Cfigmcn cn ON d.companyId = cn.companyId and d.profitcentreId = cn.profitcentreId and d.branchId = cn.branchId "
	        + "and d.igmNo = cn.igmNo and d.igmTransId = cn.igmTransId and d.igmLineNo = cn.igmLineNo and d.containerNo = cn.containerNo "
	        + "LEFT OUTER JOIN EquipmentActivity e ON cn.companyId = e.companyId and cn.branchId = e.branchId and "
	        + "cn.igmTransId = e.erpDocRefNo and cn.igmNo = e.docRefNo and cn.containerNo = e.containerNo and cn.igmLineNo = e.subDocRefNo "
	        + "LEFT OUTER JOIN JarDetail j ON e.companyId = j.companyId and e.equipment = j.jarDtlId and j.jarId = 'J00012' "
	        + "LEFT OUTER JOIN Party p1 ON i.companyId = p1.companyId and i.branchId = p1.branchId and i.shippingLine = p1.partyId "
	        + "where c.companyId = :cid and c.branchId = :bid and c.status != 'D' and c.igmNo = :igm and d.containerNo = :con and "
	        + "c.igmTransId = :trans and d.containerStatus <> 'E' group by d.containerNo,c.igmLineNo")
	List<Object[]> getImportReportsDataByDestuffConWiseFCL(@Param("cid") String cid, @Param("bid") String bid, @Param("igm") String igm,
	                                                    @Param("trans") String trans, @Param("con") String con);
}
