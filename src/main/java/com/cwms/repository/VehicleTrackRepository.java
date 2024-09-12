package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.VehicleTrack;


public interface VehicleTrackRepository extends JpaRepository<VehicleTrack, String> {

	@Query(value="select v from VehicleTrack v where v.companyId=:cid and v.branchId=:bid and v.gateInId=:id and v.status != 'D'")
	VehicleTrack getByGateInId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
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

}
