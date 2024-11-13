package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.*;
import com.cwms.entities.ExportGatePass;

public interface ExportGatePassRepo extends JpaRepository<ExportGatePass, String> {

	
	@Query(value="select New com.cwms.entities.ExportGatePass(e.movementReqId,e.gatePassId, e.sbTransId, e.gatePassDate, e.transType, e.sbNo,"
			+ "e.sbDate, e.tripType, e.vehicleNo, e.vehicleId, e.pol, e.transporterName,"
			+ "e.transporter, e.customsSealNo, e.agentSealNo, e.pod, e.containerNo,"
			+ "e.containerSize, e.containerType, e.grossWt, e.containerStatus, e.comments,"
			+ "e.status, e.createdBy, p.partyName, e.viaNo) from ExportGatePass e "
			+ "LEFT OUTER JOIN Party p ON e.companyId=p.companyId and e.branchId=p.branchId and e.cha=p.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id and e.status != 'D'")
	List<ExportGatePass> getDataByGatePassId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select e from ExportGatePass e where e.companyId=:cid and e.branchId=:bid and e.gatePassId=:id and e.status != 'D'")
	List<ExportGatePass> getDataByGatePassId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	@Query(value="select e.gatePassId,e.sbNo,e.transType,DATE_FORMAT(e.gatePassDate,'%d %M %y'),e.containerNo,e.vehicleNo from ExportGatePass e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.gatePassId LIKE CONCAT('%',:val,'%') OR e.sbNo LIKE CONCAT('%',:val,'%') "
			+ "OR e.containerNo LIKE CONCAT('%',:val,'%') OR e.vehicleNo LIKE CONCAT('%',:val,'%')) order by e.gatePassId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
