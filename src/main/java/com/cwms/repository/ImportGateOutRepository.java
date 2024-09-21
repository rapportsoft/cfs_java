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
}
