package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ManualGateIn;

public interface ManualContainerGateInRepo extends JpaRepository<ManualGateIn, String> {

	@Query(value="select g from ManualGateIn g where g.companyId=:cid and g.branchId=:bid and g.gateInId=:gateinid and g.status !='D'")
	ManualGateIn getSinggleData(@Param("cid") String cid,@Param("bid") String bid,@Param("gateinid") String gateinid);
	
	@Query(value="select m.gateInId, DATE_FORMAT(m.gateInDate,'%d %M %y'), m.containerNo, m.vehicleNo from ManualGateIn m where m.companyId=:cid and m.branchId=:bid and m.status != 'D' and "
			+ "(:val is null OR :val = '' OR m.gateInId LIKE CONCAT('%',:val,'%') OR m.containerNo LIKE CONCAT('%',:val,'%') OR "
			+ "m.vehicleNo LIKE CONCAT('%',:val,'%')) order by m.gateInId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select COUNT(m) > 0 from ManualGateIn m where m.companyId=:cid and m.branchId=:bid and m.status != 'D' and "
			+ "m.containerNo=:con and (m.erpDocRefNo is null OR m.erpDocRefNo = '')")
	Boolean isExistContainerNo(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Query(value="select m from ManualGateIn m where m.companyId=:cid and m.branchId=:bid and m.containerNo=:con and "
			+ "(m.erpDocRefNo is null OR m.erpDocRefNo = '')")
	ManualGateIn getDataByContainerNo(@Param("cid") String cid,@Param("bid") String bid,@Param("con") String con);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE ManualGateIn m SET m.erpDocRefNo = :erp, m.docRefNo = :doc, m.lineNo = :line, " +
	              "m.containerStatus = :containerStatus, m.isoCode = :isoCode, m.odcStatus = :odcStatus, " +
	              "m.lowBed = :lowBed, m.grossWeight = :grossWeight, m.scannerType = :scannerType, " +
	              "m.customsSealNo = :customsSealNo, m.scanningDoneStatus = :scanningDoneStatus, " +
	              "m.vessel = :vessel, m.viaNo = :viaNo, m.voyageNo = :voyageNo, m.refer = :refer, " +
	              "m.docRefDate = :docRefDate " +
	              "WHERE m.companyId = :cid AND m.branchId = :bid AND m.status != 'D' AND m.gateInId = :gateinid")
	int updateManualGateIn(
	    @Param("cid") String cid,
	    @Param("bid") String bid,
	    @Param("gateinid") String gateinid,
	    @Param("erp") String erp,
	    @Param("doc") String doc,
	    @Param("line") String line,
	    @Param("containerStatus") String containerStatus,
	    @Param("isoCode") String isoCode,
	    @Param("odcStatus") String odcStatus,
	    @Param("lowBed") String lowBed,
	    @Param("grossWeight") BigDecimal grossWeight,
	    @Param("scannerType") String scannerType,
	    @Param("customsSealNo") String customsSealNo,
	    @Param("scanningDoneStatus") String scanningDoneStatus,
	    @Param("vessel") String vessel,
	    @Param("viaNo") String viaNo,
	    @Param("voyageNo") String voyageNo,
	    @Param("refer") String refer,
	    @Param("docRefDate") Date docRefDate
	);

	
	@Query(value="select m.gateInId,DATE_FORMAT(m.gateInDate,'%d/%m/%Y %h:%i'),m.portExitNo,DATE_FORMAT(m.portExitDate,'%d/%m/%Y %h:%i'),"
			+ "m.containerNo,m.isoCode,m.containerSize,m.containerType,m.vehicleNo,m.transporterName,m.scannerType,m.viaNo,"
			+ "m.containerSealNo,p1.partyName,m.importerName,m.containerHealth,m.comments "
			+ "from ManualGateIn m "
			+ "LEFT OUTER JOIN Party p1 ON m.companyId=p1.companyId and m.branchId=p1.branchId and m.sl=p1.partyId "
			+ "where m.companyId=:cid and m.branchId=:bid and m.status != 'D' and m.gateInId=:id")
	Object getDataForImportReport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);

}
