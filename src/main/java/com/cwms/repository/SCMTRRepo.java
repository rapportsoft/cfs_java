package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ExportStuffTally;

public interface SCMTRRepo extends JpaRepository<ExportStuffTally, String> {

	@Query(value="select GROUP_CONCAT(DISTINCT CONCAT(e.sbNo)),GROUP_CONCAT(DISTINCT CONCAT(e.sbTransId)), GROUP_CONCAT(DISTINCT CONCAT(e.stuffTallyId)) "
			+ "from ExportStuffTally e "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN ExportSbCargoEntry crg ON e.companyId=crg.companyId and e.branchId=crg.branchId and e.sbNo=crg.sbNo and e.sbTransId=crg.sbTransId "
			+ "LEFT OUTER JOIN ExportInventory i ON e.companyId=i.companyId and e.branchId=i.branchId and e.containerNo=i.containerNo and e.stuffTallyId=i.stuffTallyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status='A' and sb.status='A' and crg.status='A' and sb.outOfCharge='Y' and "
			+ "(i.gateOutId is null OR i.gateOutId = '') and e.stuffTallyDate between :startDate and :endDate and "
			+ "(sb.mpcin is null OR sb.mpcin = '') group by e.sbNo order by e.stuffTallyId")
	public List<Object[]> getDataByStuffTallyDate(@Param("cid") String cid,@Param("bid") String bid,@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
	
	
	@Query(value="select DISTINCT e.sbNo,e.sbTransId,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.containerNo,e.containerSize,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y %H:%i'),"
			+ "sb.mpcin,e.pol,e.terminal,crg.noOfPackages,SUM(e.stuffedQty),crg.typeOfPackage,e.fromPkg,e.toPkg,e.isSfAck,e.ackSfStatus,e.stuffTallyId,e.customsSealNo,'N',e.sfJobNo "
			+ "from ExportStuffTally e "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN ExportSbCargoEntry crg ON e.companyId=crg.companyId and e.branchId=crg.branchId and e.sbNo=crg.sbNo and e.sbTransId=crg.sbTransId "
			+ "LEFT OUTER JOIN ExportInventory i ON e.companyId=i.companyId and e.branchId=i.branchId and e.containerNo=i.containerNo and e.stuffTallyId=i.stuffTallyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status='A' and sb.status='A' and crg.status='A' and sb.outOfCharge='Y' and "
			+ "(i.gateOutId is null OR i.gateOutId = '') and e.stuffTallyDate between :startDate and :endDate and "
			+ "(sb.mpcin is not null and sb.mpcin != '') group by e.stuffTallyId,e.sbNo order by e.stuffTallyId")
	public List<Object[]> getDataByStuffTallyDate1(@Param("cid") String cid,@Param("bid") String bid,@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
	
	@Transactional
	@Modifying
	@Query(value="Update ExportStuffTally e SET e.sfJobNo=:sfNo, e.sfJobDate=:sfDate,e.fromPkg=:fromPkg,e.toPkg=:toPkg where e.companyId=:cid and e.branchId=:bid and "
			+ "e.status='A' and e.stuffTallyId=:id and e.containerNo=:con")
	int updateStuffTallyData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("con") String con,
			@Param("sfNo") String sfNo,@Param("sfDate") Date sfDate,@Param("fromPkg") int fromPkg, @Param("toPkg")int toPkg);
	
	@Transactional
	@Modifying
	@Query(value="Update ExportStuffTally e SET e.isSfAck=:isAck, e.ackSfStatus=:ackSf where e.companyId=:cid and e.branchId=:bid and "
			+ "e.status='A' and e.stuffTallyId=:id and e.containerNo=:con")
	int updateStuffTallyData1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("con") String con,
			@Param("isAck") String isAck,@Param("ackSf") String ackSf);
	
	@Transactional
	@Modifying
	@Query(value="Update ExportSbCargoEntry e SET e.typeOfPackage=:pkg where e.companyId=:cid and e.branchId=:bid and "
			+ "e.status='A' and e.sbNo=:sbNo and e.sbTransId=:trans")
	int updateSbCrgData(@Param("cid") String cid,@Param("bid") String bid,@Param("sbNo") String sbNo,@Param("trans") String trans,
			@Param("pkg") String pkg);
	
	
	
	// ASR Bulk
	
	
	@Query(value="select DISTINCT e.sbNo,e.sbTransId,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),e.containerNo,e.containerSize,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y %H:%i'),"
			+ "sb.mpcin,e.pol,e.terminal,crg.noOfPackages,SUM(e.stuffedQty),crg.typeOfPackage,e.fromPkg,e.toPkg,e.isAsrAck,e.ackAsrStatus,e.stuffTallyId,e.customsSealNo,'N',e.asrJobNo "
			+ "from ExportStuffTally e "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN ExportSbCargoEntry crg ON e.companyId=crg.companyId and e.branchId=crg.branchId and e.sbNo=crg.sbNo and e.sbTransId=crg.sbTransId "
			+ "LEFT OUTER JOIN ExportInventory i ON e.companyId=i.companyId and e.branchId=i.branchId and e.containerNo=i.containerNo and e.stuffTallyId=i.stuffTallyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status='A' and sb.status='A' and crg.status='A' and sb.outOfCharge='Y' and "
			+ "(i.gateOutId is null OR i.gateOutId = '') and e.stuffTallyDate between :startDate and :endDate and "
			+ "e.isSfAck='S' group by e.stuffTallyId,e.sbNo order by e.stuffTallyId")
	public List<Object[]> getDataByStuffTallyDateForASR(@Param("cid") String cid,@Param("bid") String bid,@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
	
	
	@Transactional
	@Modifying
	@Query(value="Update ExportStuffTally e SET e.asrJobNo=:sfNo, e.asrJobDate=:sfDate where e.companyId=:cid and e.branchId=:bid and "
			+ "e.status='A' and e.stuffTallyId=:id and e.containerNo=:con")
	int updateStuffTallyData2(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("con") String con,
			@Param("sfNo") String sfNo,@Param("sfDate") Date sfDate);
	
	@Transactional
	@Modifying
	@Query(value="Update ExportStuffTally e SET e.isAsrAck=:isAck, e.ackAsrStatus=:ackSf where e.companyId=:cid and e.branchId=:bid and "
			+ "e.status='A' and e.stuffTallyId=:id and e.containerNo=:con")
	int updateStuffTallyData3(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("con") String con,
			@Param("isAck") String isAck,@Param("ackSf") String ackSf);
}
