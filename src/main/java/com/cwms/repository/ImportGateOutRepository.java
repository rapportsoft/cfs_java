package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GateOut;

public interface ImportGateOutRepository extends JpaRepository<GateOut, String> {

	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.erpDocRefNo=:trans "
            + "and g.docRefNo=:igm and g.gateOutId=:gateOut and g.srNo=:sr")
     GateOut getSingleData(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
                      @Param("igm") String igm, @Param("gateOut") String gateOut, @Param("sr") String sr);

	
	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.gateOutId=:gateOut")
	List<GateOut> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("gateOut") String gateOut);
	
	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.gateOutId=:gateOut "
			+ "and g.gatePassNo=:gatepass")
	List<GateOut> getDataByGateOutIdAndGatePassNo(@Param("cid") String cid,@Param("bid") String bid,
			@Param("gateOut") String gateOut,@Param("gatepass") String gatepass);
	
	@Query(value="select g.gateOutId,DATE_FORMAT(g.gateOutDate,'%d %M %y'),g.gatePassNo,DATE_FORMAT(g.gatePassDate,'%d %M %y'),g.shift,"
			+ "g.vehicleNo,g.driverName,g.transType from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and "
			+ "(:val is null OR :val = '' OR g.gateOutId LIKE CONCAT('%',:val,'%') OR g.gatePassNo LIKE CONCAT('%',:val,'%') OR "
			+ "g.vehicleNo LIKE CONCAT('%',:val,'%')) and g.processId='P00209' order by g.gateOutId desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	@Query(value="select New com.cwms.entities.GateOut(g.gateOutId,g.createdDate) from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.erpDocRefNo=:igmtrans "
			+ "and g.docRefNo=:igm and (:line is null OR :line = '' OR g.igmLineNo=:line) order by g.createdDate desc LIMIT 1")
	GateOut getLatestRecordsGateOutId(@Param("cid") String cid,@Param("bid") String bid,
			@Param("igm") String igm,@Param("igmtrans") String igmtrans,@Param("line") String line);
	
	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.erpDocRefNo=:igmtrans "
			+ "and g.docRefNo=:igm and (:line is null OR :line = '' OR g.igmLineNo = :line) and g.gateOutId=:gateout")
	List<GateOut> getLatestRecordsFromIgm(@Param("cid") String cid,@Param("bid") String bid,
			@Param("igm") String igm,@Param("igmtrans") String igmtrans,@Param("line") String line,@Param("gateout") String gateout);
	
	@Query(value="select New com.cwms.entities.GateOut(g.gateOutId,g.createdDate) from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.erpDocRefNo=:igmtrans "
			+ "and g.docRefNo=:igm and (:con is null OR :con = '' OR g.containerNo=:con) order by g.createdDate desc LIMIT 1")
	GateOut getLatestRecordsGateOutIdByContainer(@Param("cid") String cid,@Param("bid") String bid,
			@Param("igm") String igm,@Param("igmtrans") String igmtrans,@Param("con") String con);
	
	@Query(value="select g from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.erpDocRefNo=:igmtrans "
			+ "and g.docRefNo=:igm and (:con is null OR :con = '' OR g.containerNo = :con) and g.gateOutId=:gateout")
	List<GateOut> getLatestRecordsFromIgmByContainer(@Param("cid") String cid,@Param("bid") String bid,
			@Param("igm") String igm,@Param("igmtrans") String igmtrans,@Param("con") String con,@Param("gateout") String gateout);
	
	
	@Query(value="select e.erpDocRefNo,e.gateOutId,e.sl,p1.partyName,e.onAccountOf,p2.partyName,e.docRefNo,e.shipper,p3.partyName,"
			+ "e.cha,e.chaName,e.deliveryOrderNo,e.deliveryOrderDate,e.doValidityDate,e.location,e.profitcentreId,e.containerHealth,e.containerType,"
			+ "e.containerNo,e.containerSize,e.containerStatus,e.isoCode,e.gatePassNo,e.sa,p5.partyName,e.fromLocation,e.gateOutDate,"
			+ "e.gateInDate,e.tareWt,e.vehicleNo,e.vehicleId,e.srNo, e.movementType,e.gatePassDate,e.driverName from GateOut e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.shipper=p3.partyId "
			+ "LEFT OUTER JOIN Party p4 ON e.companyId=p4.companyId and e.branchId=p4.branchId and e.cha=p4.partyId "
			+ "LEFT OUTER JOIN Party p5 ON e.companyId=p5.companyId and e.branchId=p5.branchId and e.sa=p5.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.gatePassNo=:gatepass and e.gateOutId=:gateout")
	List<Object[]> getGateOutData(@Param("cid") String cid,@Param("bid") String bid,@Param("gatepass") String gatepass,@Param("gateout") String gateout);
	
	
	
	@Query(value="select distinct e.gateOutId,DATE_FORMAT(e.gateOutDate,'%d %M %y'),e.gatePassNo,DATE_FORMAT(e.gatePassDate,'%d %M %y'), "
			+ "e.containerNo,p1.partyName,e.chaName,e.docRefNo,e.deliveryOrderNo from GateOut e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sl=p1.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = ''  OR e.gatePassNo LIKE CONCAT ('%',:val,'%') OR e.gateOutId LIKE CONCAT ('%',:val,'%') "
			+ "OR e.docRefNo LIKE CONCAT ('%',:val,'%') OR e.deliveryOrderNo LIKE CONCAT ('%',:val,'%') "
			+ "OR p1.partyName LIKE CONCAT ('%',:val,'%') OR e.containerNo LIKE CONCAT ('%',:val,'%') OR e.chaName LIKE CONCAT ('%',:val,'%')) order by e.gateOutId desc")
	List<Object[]> searchGateOut(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
