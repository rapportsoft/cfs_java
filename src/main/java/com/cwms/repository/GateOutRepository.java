package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GateOut;

public interface GateOutRepository extends JpaRepository<GateOut, String> {

	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.erpDocRefNo=:gatein and g.gateOutId=:gateout and g.status != 'D'")
	GateOut getDataByGateOutIdAndGateInId(@Param("cid") String cid,@Param("bid") String bid,@Param("gatein") String gatein,@Param("gateout") String gateout);
	
	@Query(value = "select new com.cwms.entities.GateOut(g.companyId, g.branchId, g.finYear, g.gateOutId, g.erpDocRefNo, "
            + "g.srNo, p.profitcentreDesc, g.gateOutDate, g.processId, g.shift, g.gateNoIn, g.gateNoOut, "
            + "g.transporterStatus, g.transporter, g.transporterName, g.vehicleNo, g.tripType, g.driverName,g.status, "
            + "g.createdBy, g.createdDate, g.editedBy, g.editedDate, g.approvedBy, g.approvedDate,g.comments) "
            + "from GateOut g "
            + "LEFT OUTER JOIN Profitcentre p ON g.companyId=p.companyId and g.branchId=p.branchId and g.profitcentreId=p.profitcentreId "
            + "where g.companyId = :cid and g.branchId = :bid and g.erpDocRefNo = :gatein "
            + "and g.gateOutId = :gateout and g.status != 'D'")
GateOut getDataByGateOutIdAndGateInId1(@Param("cid") String cid, @Param("bid") String bid, 
                                       @Param("gatein") String gatein, @Param("gateout") String gateout);

	
	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.gateOutId=:gateout and g.status != 'D'")
	GateOut getDataByGateOutId(@Param("cid") String cid,@Param("bid") String bid,@Param("gateout") String gateout);
	
	
	@Query(value="select g.gateOutId,DATE_FORMAT(g.gateOutDate,'%d %M %Y'),g.gateNoOut,g.shift,g.driverName,g.vehicleNo,g.erpDocRefNo "
			+ "from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' "
			+ "and (:val is null OR :val = '' OR g.gateOutId LIKE CONCAT ('%',:val,'%') OR g.vehicleNo LIKE CONCAT ('%',:val,'%')) "
			+ "order by g.gateOutId desc")
	List<Object[]> searchGateOutData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
