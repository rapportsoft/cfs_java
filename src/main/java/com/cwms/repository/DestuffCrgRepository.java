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
}
