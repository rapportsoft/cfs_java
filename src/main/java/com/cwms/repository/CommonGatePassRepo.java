package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CommonGatePass;

public interface CommonGatePassRepo extends JpaRepository<CommonGatePass, String> {

	@Query(value="select c from CommonGatePass c where c.companyId=:cid and c.branchId=:bid and c.jobTransId=:job and c.gatePassId=:gate "
			+ "and c.srNo=:sr and c.status != 'D'")
	CommonGatePass getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("job") String job,@Param("gate") String gate,
			@Param("sr") int sr);
	
	
	@Query(value="select e.jobTransId,e.jobTransDate,e.sl,p1.partyName,e.onAccountOf,p2.partyName,e.bookingNo,e.shipper,p3.partyName,"
			+ "e.cha,p4.partyName,e.doNo,e.doDate,e.doValidityDate,e.toLocation,e.profitcentreId,e.containerHealth,e.containerType,"
			+ "e.containerNo,e.containerSize,e.containerType,e.iso,e.gatePassId,e.sa,p5.partyName,e.fromLocation,e.gateInId,"
			+ "e.gateInDate,e.tareWt,e.vehicleNo,e.vehicleId,e.srNo, e.movementType,e.gatePassDate,e.driverName from CommonGatePass e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shipper=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.cha=p4.partyId "
			+ "LEFT OUTER JOIN Party p5 ON e.companyId=p5.companyId and e.branchId=p5.branchId and e.sa=p5.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.jobTransId=:job and e.gatePassId=:gate")
	List<Object[]> getGatePassData(@Param("cid") String cid,@Param("bid") String bid,@Param("job") String val,@Param("gate") String gate);
	
	
	@Query(value="select distinct e.gatePassId,DATE_FORMAT(e.gatePassDate,'%d %M %y'),e.jobTransId,DATE_FORMAT(e.jobTransDate,'%d %M %y'), "
			+ "e.containerNo,p1.partyName,p2.partyName,e.bookingNo,e.doNo from CommonGatePass e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.cha=p2.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.jobTransId LIKE CONCAT ('%',:val,'%') OR e.gatePassId LIKE CONCAT ('%',:val,'%') "
			+ "OR e.bookingNo LIKE CONCAT ('%',:val,'%') OR e.doNo LIKE CONCAT ('%',:val,'%') "
			+ "OR p1.partyName LIKE CONCAT ('%',:val,'%') OR e.containerNo LIKE CONCAT ('%',:val,'%') OR p2.partyName LIKE CONCAT ('%',:val,'%')) order by e.gatePassId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select distinct e.gatePassId, e.vehicleNo from CommonGatePass e where e.companyId=:cid and "
			+ "e.branchId=:bid and e.status != 'D' and (e.gateOutId is null OR e.gateOutId = '') and "
			+ "(:val is null OR :val = '' OR e.vehicleNo LIKE CONCAT ('%',:val,'%'))")
	List<Object[]> getPendingVehicleData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select e.jobTransId,e.jobTransDate,e.sl,p1.partyName,e.onAccountOf,p2.partyName,e.bookingNo,e.shipper,p3.partyName,"
			+ "e.cha,p4.partyName,e.doNo,e.doDate,e.doValidityDate,e.toLocation,e.profitcentreId,e.containerHealth,e.containerType,"
			+ "e.containerNo,e.containerSize,e.containerStatus,e.iso,e.gatePassId,e.sa,p5.partyName,e.fromLocation,e.gateInId,"
			+ "e.gateInDate,e.tareWt,e.vehicleNo,e.vehicleId,e.srNo, e.movementType,e.gatePassDate,e.driverName from CommonGatePass e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shipper=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.cha=p4.partyId "
			+ "LEFT OUTER JOIN Party p5 ON e.companyId=p5.companyId and e.branchId=p5.branchId and e.sa=p5.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.gatePassId=:gate and (e.gateOutId is null OR e.gateOutId = '')")
	List<Object[]> getGatePassDataForGateOut(@Param("cid") String cid,@Param("bid") String bid,@Param("gate") String gate);
}
