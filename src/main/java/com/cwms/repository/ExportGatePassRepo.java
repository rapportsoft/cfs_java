package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.*;
import com.cwms.entities.ExportGatePass;

public interface ExportGatePassRepo extends JpaRepository<ExportGatePass, String> {

	
	@Query(value="select New com.cwms.entities.ExportGatePass(e.movementReqId,e.gatePassId, e.sbTransId, e.gatePassDate, e.transType, e.sbNo,"
			+ "e.sbDate, e.tripType, e.vehicleNo, e.vehicleId, e.driverName,e.pol, e.transporterName,"
			+ "e.transporter, e.customsSealNo, e.agentSealNo, e.pod, e.containerNo,"
			+ "e.containerSize, e.containerType, e.grossWt, e.containerStatus, e.comments,"
			+ "e.status, e.createdBy, p.partyName, e.viaNo) from ExportGatePass e "
			+ "LEFT OUTER JOIN Party p ON e.companyId=p.companyId and e.branchId=p.branchId and e.cha=p.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id and e.status != 'D'")
	List<ExportGatePass> getDataByGatePassId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select New com.cwms.entities.ExportGatePass(e.gatePassId, e.sbTransId, e.stuffTallyId, e.gatePassDate,"
			+ "e.transType, e.sbNo, e.sbDate, e.vehicleNo, e.vehicleId, e.importerName,"
			+ "e.commodity, e.driverName, e.transporterName, e.transporter,"
			+ "e.backToTownPackages, e.vehicleWt, e.comments, e.status, e.createdBy) from ExportGatePass e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id and e.status != 'D'")
	List<ExportGatePass> getDataByGatePassIdFORCRG(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select e from ExportGatePass e where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id and e.status != 'D'")
	List<ExportGatePass> getDataByGatePassId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	@Query(value="select e.gatePassId,e.sbNo,e.transType,DATE_FORMAT(e.gatePassDate,'%d %M %y'),e.containerNo,e.vehicleNo from ExportGatePass e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.gatePassId LIKE CONCAT('%',:val,'%') OR e.sbNo LIKE CONCAT('%',:val,'%') "
			+ "OR e.containerNo LIKE CONCAT('%',:val,'%') OR e.vehicleNo LIKE CONCAT('%',:val,'%')) order by e.gatePassId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	
	@Query(value="select e.gatePassId,e.containerNo from ExportGatePass e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and (e.gateOutId is null OR e.gateOutId = '') and "
			+ "(:val is null OR :val = '' OR e.gatePassId LIKE CONCAT('%',:val,'%') OR e.containerNo LIKE CONCAT('%',:val,'%')) order by e.gatePassId desc")
	List<Object[]> searchForGateout(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select New com.cwms.entities.ExportGatePass(e.gatePassId, e.gatePassDate, e.transType, e.vehicleNo,"
			+ "e.vehicleId, e.pol, e.driverName, e.transporterName, e.transporterStatus,"
			+ "e.customsSealNo, p.partyName, v.vesselName, e.pod, e.viaNo, e.containerNo,"
			+ "e.containerSize, e.containerType, e.grossWt, e.sbNo,e.sbDate,e.backToTownPackages,e.commodity,e.srNo) from ExportGatePass e "
			+ "LEFT OUTER JOIN Party p ON e.companyId=p.companyId and e.branchId=p.branchId and e.sl=p.partyId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.gatePassId=:val "
			+ "and (e.gateOutId is null OR e.gateOutId = '')")
	List<ExportGatePass> searchForGateoutSelectedData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select New com.cwms.entities.ExportGatePass(e.gatePassId, e.gatePassDate, e.transType, e.vehicleNo,"
			+ "e.vehicleId, e.pol, e.driverName, e.transporterName, e.transporterStatus,"
			+ "e.customsSealNo, p.partyName, v.vesselName, e.pod, e.viaNo, e.containerNo,"
			+ "e.containerSize, e.containerType, e.grossWt, e.sbNo,e.sbDate,e.backToTownPackages,e.commodity,e.srNo) from ExportGatePass e "
			+ "LEFT OUTER JOIN Party p ON e.companyId=p.companyId and e.branchId=p.branchId and e.sl=p.partyId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.gatePassId=:val")
	List<ExportGatePass> searchForGateoutSelectedData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select e from ExportGatePass e where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id "
			+ "and e.containerNo=:con and e.status != 'D' and (e.gateOutId is null OR e.gateOutId = '')")
	ExportGatePass getDataPassData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("con") String con);
	
	
	@Query(value="select e from ExportGatePass e where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id "
			+ "and e.srNo=:sr and e.status != 'D' and (e.gateOutId is null OR e.gateOutId = '')")
	ExportGatePass getGataPassData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("sr") int sr);
}
