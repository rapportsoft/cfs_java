package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.VehicleTrack;


public interface VehicleTrackRepository extends JpaRepository<VehicleTrack, String> {

	@Query(value="select v from VehicleTrack v where v.companyId=:cid and v.branchId=:bid and v.gateInId=:id and v.status != 'D'")
	VehicleTrack getByGateInId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select NEW com.cwms.entities.VehicleTrack(v.vehicleNo, p.profitcentreDesc, v.gateInId, v.transporterStatus,"
			+ "v.transporterName, v.transporter, v.driverName, v.vehicleStatus, v.gateInDate,"
			+ "v.gateNoIn, v.shiftIn, v.gateOutId, v.gateOutDate, v.gateNoOut,v.shiftOut) from VehicleTrack v "
			+ "LEFT OUTER JOIN Profitcentre p ON v.companyId=p.companyId and v.branchId=p.branchId and v.profitcentreId=p.profitcentreId "
			+ "where v.companyId=:cid and v.branchId=:bid and v.gateInId=:id and v.status != 'D'")
	VehicleTrack getByGateInId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select v from VehicleTrack v where v.companyId=:cid and v.branchId=:bid and v.gateInId=:id and v.vehicleNo=:veh and v.status != 'D'")
	VehicleTrack getByGateInId2(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("veh") String veh);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE VehicleTrack v SET v.vehicleNo = :vehicleNo, v.profitcentreId = :profitcentreId, v.transporterStatus = :transporterStatus, "
	        + "v.transporterName = :transporterName, v.transporter = :transporter, v.driverName = :driverName, v.vehicleStatus = :vehicleStatus, "
	        + "v.gateNoIn = :gateNoIn, v.shiftIn = :shiftIn, v.editedBy = :editedBy, v.editedDate = CURRENT_DATE "
	        + "WHERE v.companyId = :cid AND v.branchId = :bid AND v.gateInId = :id AND v.status != 'D'")
	int updateData(@Param("cid") String cid, 
	               @Param("bid") String bid, 
	               @Param("id") String id,
	               @Param("vehicleNo") String vehicleNo,
	               @Param("profitcentreId") String profitcentreId,
	               @Param("transporterStatus") char transporterStatus,
	               @Param("transporterName") String transporterName,
	               @Param("transporter") String transporter,
	               @Param("driverName") String driverName,
	               @Param("vehicleStatus") char vehicleStatus,
	               @Param("gateNoIn") String gateNoIn,
	               @Param("shiftIn") String shiftIn,
	               @Param("editedBy") String editedBy);
	
	
	@Query(value="select COUNT(c)>0 from VehicleTrack c where c.companyId=:cid and c.branchId=:bid and c.vehicleNo=:veh and c.status != 'D' "
			+ "and (c.gateOutId is null OR c.gateOutId = '')")
	Boolean checkVehicleNo(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh);
	
	@Query(value="select COUNT(c)>0 from VehicleTrack c where c.companyId=:cid and c.branchId=:bid and c.vehicleNo=:veh and c.status != 'D' "
			+ "and (c.gateOutId is null OR c.gateOutId = '') and c.gateInId != :id")
	Boolean checkVehicleNo1(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh,@Param("id") String id);
	
	@Query(value="select v.vehicleNo,v.gateInId from VehicleTrack v where v.companyId=:cid and v.branchId=:bid and v.status != 'D' and "
			+ "(v.gateOutId is null OR v.gateOutId = '') and (:val is null OR :val = '' OR v.vehicleNo LIKE CONCAT ('%',:val,'%'))")
	List<Object[]> getEmptyGateOutIdDataFromVehTrk(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select COUNT(c)>0 from VehicleTrack c where c.companyId=:cid and c.branchId=:bid and c.vehicleNo=:veh "
			+ "and c.status != 'D'")
	Boolean checkVehicleNoSearch(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh);
	
	@Query(value="select v from VehicleTrack v where v.companyId=:cid and v.branchId=:bid and v.vehicleNo=:veh and v.status != 'D' and v.vehicleStatus = 'E' "
			+ "order by v.createdDate desc limit 1")
	VehicleTrack vehNoSearch(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh);
	
	@Query(value="select v from VehicleTrack v where v.companyId=:cid and v.branchId=:bid and v.vehicleNo=:veh and v.status != 'D' and v.gateInId=:inId and "
			+ "(v.gatePassNo is null OR v.gatePassNo = '') and (v.gateOutId is null OR v.gateOutId = '')")
	VehicleTrack getDataByVehicleNo(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh,@Param("inId") String inId);

	@Query(value="select v.vehicleNo,v.gateInId,v.driverName from VehicleTrack v where v.companyId=:cid and v.branchId=:bid "
			+ "and v.status != 'D' and v.vehicleStatus = 'E' and (v.gateOutId = '' OR v.gateOutId is null) "
			+ "and (:veh is null OR :veh = '' OR v.vehicleNo LIKE CONCAT ('%',:veh,'%')) ")
	List<Object[]> getEmptyVehGateIn(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh);
	
	
	@Query(value="select v.vehicleNo,v.gateInId from VehicleTrack v where v.companyId=:cid and v.branchId=:bid "
			+ "and v.status != 'D' and v.vehicleStatus = 'E' and (v.gateOutId = '' OR v.gateOutId is null) and (v.gatePassNo is null OR v.gatePassNo = '') "
			+ "and (:veh is null OR :veh = '' OR v.vehicleNo LIKE CONCAT ('%',:veh,'%')) ")
	List<Object[]> getEmptyVehGateIn1(@Param("cid") String cid,@Param("bid") String bid,@Param("veh") String veh);
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE cfvehtrck v "
	             + "SET v.gate_Out_Id = :id, v.gate_Out_Date = CURRENT_DATE "
	             + "WHERE EXISTS ("
	             + "  SELECT 1 FROM CfImportGatePassVehDtl c "
	             + "  WHERE v.company_Id = c.company_Id "
	             + "    AND v.branch_Id = c.branch_Id "
	             + "    AND v.gate_In_Id = c.vehicle_Gate_Pass_Id "
	             + "    AND c.company_Id = :cid "
	             + "    AND c.branch_Id = :bid "
	             + "    AND c.status != 'D' "
	             + "    AND c.gate_Pass_Id = :gatpass)"
	             , nativeQuery = true)
	int updateVehicleData(@Param("cid") String cid, 
	                      @Param("bid") String bid, 
	                      @Param("gatpass") String gatpass, 
	                      @Param("id") String id);
}
