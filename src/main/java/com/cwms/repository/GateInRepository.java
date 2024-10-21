package com.cwms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.cwms.entities.GateIn;

public interface GateInRepository extends JpaRepository<GateIn, String> {
	
	
	@Query("SELECT NEW com.cwms.entities.GateIn(E.gateInId, E.docRefNo, E.erpDocRefNo, E.vehicleNo, E.grossWeight) " +
		       "FROM GateIn E " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.gateInType = :type " +
		       "AND E.gateInId = :gateInId " +
		       "AND E.processId = :processId " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
		List<GateIn> gateInsForMultipleEquipment(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId,
		                                   @Param("profitcentreId") String profitcentreId,
		                                   @Param("processId") String processId,
		                                   @Param("gateInId") String gateInId,		                                  
		                                   @Param("type") String type);

	
	@Query("SELECT NEW com.cwms.entities.GateIn(E.gateInId, E.erpDocRefNo, E.docRefNo, E.srNo, E.onAccountOf, pa.partyName, E.commodityDescription, E.actualNoOfPackages, E.qtyTakenIn, E.vehicleNo, E.cargoWeight, E.fob, E.inGateInDate, E.docRefDate, E.grossWeight) " +
		       "FROM GateIn E " +
		       "Left Join Party pa on E.companyId = pa.companyId and E.branchId = pa.branchId and E.onAccountOf = pa.partyId and pa.status != 'D' " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.vehicleNo = :vehicleNo " +
		       "AND E.gateInType = :type " +		       
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
		List<GateIn> getGateInEntryFromVehicleNo(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId,
		                                   @Param("profitcentreId") String profitcentreId,
		                                   @Param("vehicleNo") String vehicleNo,
		                                   @Param("type") String type);

	
	
	
	
	@Query("SELECT Distinct s.vehicleNo " +
		       "FROM GateIn s " +		       
		       "WHERE s.companyId = :companyId AND s.branchId = :branchId " +
		       "AND (s.qtyTakenIn - s.cartedPackages) <> 0 " +
		       "AND s.status <> 'D' " +
		       "AND s.vehicleNo LIKE %:searchValue% " +
		       "ORDER BY s.createdDate")
		List<String> searchVehicleNosToCarting(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("searchValue") String searchValue
		);


	
	
	@Query(value="select g from GateIn g where g.companyId=:cid and g.branchId=:bid and g.erpDocRefNo=:igmtrans and "
			+ "g.docRefNo=:igm and g.status !='D' order by g.createdDate desc limit 1")
	GateIn getExistingData(@Param("cid") String cid,@Param("bid") String bid,@Param("igmtrans") String igmtrans,
			@Param("igm") String igm);
	
	@Query("SELECT g.gateInId, g.erpDocRefNo, g.docRefNo, g.containerNo, g.vehicleNo, "
	        + "g.status, DATE_FORMAT(g.inGateInDate, '%d %M %y'),p.profitcentreDesc, py1.partyName, py2.partyName, po.portName, v.vesselName "
	        + "FROM GateIn g "
	        + "LEFT JOIN Profitcentre p ON g.companyId = p.companyId AND g.branchId = p.branchId AND g.profitcentreId = p.profitcentreId "
	        + "LEFT JOIN Party py1 ON g.companyId = py1.companyId AND g.branchId = py1.branchId AND g.sa = py1.partyId "
	        + "LEFT JOIN Party py2 ON g.companyId = py2.companyId AND g.branchId = py2.branchId AND g.sl = py2.partyId "
	        + "LEFT JOIN Port po ON g.companyId = po.companyId AND g.branchId = po.branchId AND g.terminal = po.portCode "
	        + "LEFT JOIN Vessel v ON g.companyId = v.companyId AND g.branchId = v.branchId AND g.vessel = v.vesselId "
	        + "WHERE g.companyId = :cid "
	        + "AND g.branchId = :bid "
	        + "AND g.gateInType = 'IMP' "
	        + "AND g.status != 'D' "
	        + "AND g.erpDocRefNo=:igmtrans AND g.docRefNo=:igm  order by g.inGateInDate desc")
	Object[] getAll1(@Param("cid") String cid, @Param("bid") String bid, @Param("igmtrans") String igmtrans,@Param("igm") String igm);

	@Query(value="select g from GateIn g where g.companyId=:cid and g.branchId=:bid and g.gateInId=:gateinid and g.erpDocRefNo=:igmtrans and "
			+ "g.docRefNo=:igm and g.status !='D'")
	GateIn getData(@Param("cid") String cid,@Param("bid") String bid,@Param("gateinid") String gateinid,@Param("igmtrans") String igmtrans,
			@Param("igm") String igm);
	
	@Query(value="select g from GateIn g where g.companyId=:cid and g.branchId=:bid and g.gateInId=:gateinid and g.status !='D'")
	GateIn getData2(@Param("cid") String cid,@Param("bid") String bid,@Param("gateinid") String gateinid);
	
	@Query(value="select g from GateIn g where g.companyId=:cid and g.branchId=:bid and "
			+ "g.docRefNo=:igm and g.containerNo=:cont and g.status !='D'")
	GateIn getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("igm") String igm,@Param("cont") String cont);
	
	
	@Query("SELECT g.gateInId, g.erpDocRefNo, g.docRefNo, g.containerNo, g.vehicleNo, "
	        + "g.status, DATE_FORMAT(g.inGateInDate, '%d %M %y'),p.profitcentreDesc, py1.partyName, py2.partyName, po.portName, v.vesselName "
	        + "FROM GateIn g "
	        + "LEFT JOIN Profitcentre p ON g.companyId = p.companyId AND g.branchId = p.branchId AND g.profitcentreId = p.profitcentreId "
	        + "LEFT JOIN Party py1 ON g.companyId = py1.companyId AND g.branchId = py1.branchId AND g.sa = py1.partyId "
	        + "LEFT JOIN Party py2 ON g.companyId = py2.companyId AND g.branchId = py2.branchId AND g.sl = py2.partyId "
	        + "LEFT JOIN Port po ON g.companyId = po.companyId AND g.branchId = po.branchId AND g.terminal = po.portCode "
	        + "LEFT JOIN Vessel v ON g.companyId = v.companyId AND g.branchId = v.branchId AND g.vessel = v.vesselId "
	        + "WHERE g.companyId = :cid "
	        + "AND g.branchId = :bid "
	        + "AND g.gateInType = 'IMP' "
	        + "AND g.status != 'D' "
	        + "AND (:search IS NULL OR :search = '' OR g.gateInId LIKE CONCAT(:search, '%') "
	        + "OR g.containerNo LIKE CONCAT('%', :search, '%')) order by g.gateInId desc")
	List<Object[]> getAll(@Param("cid") String cid, @Param("bid") String bid, @Param("search") String search);


//	@Query("SELECT NEW com.cwms.entities.GateIn(E.companyId, E.branchId, E.gateInId, E.finYear, E.erpDocRefNo, " +
//		       "E.docRefNo, E.lineNo, E.srNo, E.docRefDate, E.gateNo, " +
//		       "sb.gateInPackages, E.gateInType, E.profitcentreId, E.processId, " +
//		       "E.grossWeight, o.partyName AS onAccountOf, c.partyName AS cha, E.qtyTakenIn, E.shift, " +
//		       "E.status, E.createdBy, E.approvedBy, E.transporterStatus, E.transporterName, E.vehicleNo, E.driverName, " +
//		       "E.cargoWeight, E.commodityDescription, E.actualNoOfPackages, E.inGateInDate) " +
//		       "FROM GateIn E " +
//		       "LEFT JOIN Party c ON E.companyId = c.companyId AND E.branchId = c.branchId AND E.cha = c.partyId AND c.status != 'D' " +
//		       "LEFT JOIN Party o ON E.companyId = o.companyId AND E.branchId = o.branchId AND E.onAccountOf = o.partyId AND o.status != 'D' " +
//		       "LEFT JOIN ExportSbEntry sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.erpDocRefNo = sb.sbTransId AND E.docRefNo = sb.sbNo AND sb.status != 'D' " +
//		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
//		       "AND E.profitcentreId = :profitcentreId " +
//		       "AND E.gateInId = :gateInId " +
//		       "AND E.gateInType = :type " +
//		       "AND E.status <> 'D'")
//		List<GateIn> getSelectedGateInEntry(@Param("companyId") String companyId, 
//		                                    @Param("branchId") String branchId,
//		                                    @Param("profitcentreId") String profitcentreId, 
//		                                    @Param("gateInId") String gateInId, 
//		                                    @Param("type") String type);

	
	
	@Query(value = "select c.gateInId, c.inGateInDate, c.docRefNo, c.erpDocRefNo, c.profitcentreId, po.profitcentreDesc, c.transporterStatus, c.transporterName, c.driverName, c.vehicleNo, c.status "
	        + "from GateIn c "
	        + "Left Join Profitcentre po on c.companyId = po.companyId and c.branchId = po.branchId and c.profitcentreId = po.profitcentreId and po.status != 'D' "
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' and c.gateInType='EXP' "
	        + "and (:searchValue is null OR :searchValue = '' OR c.docRefNo LIKE %:searchValue% OR c.gateInId LIKE %:searchValue% OR c.vehicleNo LIKE %:searchValue%) "
	        + "ORDER BY c.inGateInDate DESC")
	List<Object[]> getGateInEntriesData(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                @Param("searchValue") String searchValue);
	
	
	
	@Query("SELECT E FROM GateIn E "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitcentreId = :profitcentreId " 
	        + "AND E.erpDocRefNo = :erpDocRefNo "
	        + "AND E.docRefNo = :docRefNo "
	        + "AND E.gateInId = :gateInId "
	        + "AND E.gateInType = :type "
	        + "AND E.status <> 'D'")
	GateIn getGateInByIds(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("gateInId") String gateInId,
	                              @Param("erpDocRefNo") String erpDocRefNo, @Param("docRefNo") String docRefNo, @Param("type") String type);
	
	@Query("SELECT NEW com.cwms.entities.GateIn(E.companyId, E.branchId, E.gateInId, E.finYear, E.erpDocRefNo, "
			+ "E.docRefNo, E.lineNo, E.srNo, E.docRefDate, E.gateNo, "
			+ "sb.gateInPackages, E.gateInType, E.profitcentreId, E.processId, "
			+ "E.grossWeight, o.partyName AS onAccountOf, c.partyName AS cha, E.qtyTakenIn, E.shift, "
			+ "E.status, E.createdBy, E.approvedBy, E.transporterStatus, E.transporterName, E.vehicleNo, E.driverName, "
			+ "E.cargoWeight, E.commodityDescription, E.actualNoOfPackages, E.inGateInDate, E.remarks) " + "FROM GateIn E "
			+ "LEFT JOIN Party c ON E.companyId = c.companyId AND E.branchId = c.branchId AND E.cha = c.partyId AND c.status != 'D' "
			+ "LEFT JOIN Party o ON E.companyId = o.companyId AND E.branchId = o.branchId AND E.onAccountOf = o.partyId AND o.status != 'D' "
			+ "LEFT JOIN ExportSbEntry sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.erpDocRefNo = sb.sbTransId AND E.docRefNo = sb.sbNo AND sb.status != 'D' "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitcentreId = :profitcentreId "
			+ "AND E.gateInId = :gateInId " + "AND E.gateInType = :type " + "AND E.status <> 'D'")
	List<GateIn> getSelectedGateInEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("gateInId") String gateInId,
			@Param("type") String type);
	
	@Query("SELECT NEW com.cwms.entities.GateIn(E.gateInId, E.docRefNo, E.qtyTakenIn, E.vehicleNo, E.cartingStatus, E.inGateInDate, E.transporterName) " +
		       "FROM GateIn E " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.docRefNo IN (SELECT G.docRefNo FROM GateIn G WHERE G.gateInId = :gateInId AND G.gateInType = :type AND G.companyId = :companyId AND G.branchId = :branchId AND G.profitcentreId = :profitcentreId AND G.status <> 'D') " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.gateInType = :type " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
		List<GateIn> getSavedGateInRecords(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId,
		                                   @Param("profitcentreId") String profitcentreId,
		                                   @Param("gateInId") String gateInId,
		                                   @Param("type") String type);
	
	
	@Query("SELECT NEW com.cwms.entities.GateIn(E.gateInId, E.erpDocRefNo, "
	        + "E.docRefNo, E.cartingTransId, E.cartedPackages, E.containerNo, E.actualNoOfPackages, "
	        + "E.qtyTakenIn, E.portExitNo, E.vehicleNo, E.cartingStatus, "
	        + "E.stuffTallyId, E.prGatePassNo, E.prGateOutId, E.stuffTallyStatus, sb.totalPackages, sb.gateInPackages, E.profitcentreId) "
	        + "FROM GateIn E "
	        + "JOIN ExportSbEntry sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.erpDocRefNo = sb.sbTransId  "
	        + "AND (:docRefNo IS NULL OR :docRefNo = '' OR sb.sbNo LIKE CONCAT(:docRefNo, '%')) "
	        + "AND (:pod IS NULL OR :pod = '' OR sb.pod LIKE CONCAT(:pod, '%')) "
	        + "WHERE E.companyId = :companyId "
	        + "AND E.branchId = :branchId "
	        + "AND (:docRefNo IS NULL OR :docRefNo = '' OR E.docRefNo LIKE CONCAT(:docRefNo, '%')) "
	        + "AND (:vehicleNo IS NULL OR :vehicleNo = '' OR E.vehicleNo LIKE CONCAT(:vehicleNo, '%')) "
	        + "AND E.gateInType = :type "
	        + "AND E.status <> 'D' "
	        + "ORDER BY E.createdDate DESC")
	List<GateIn> searchLatestExportMain(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                    @Param("docRefNo") String docRefNo, @Param("vehicleNo") String vehicleNo, 
	                                    @Param("type") String type, @Param("pod") String pod, Pageable pageable);
	
	
	@Query(value="select g.gateInId, DATE_FORMAT(g.inGateInDate,'%d %M %Y'),g.gateNo,g.shift,g.driverName,g.vehicleNo,g.tripType from GateIn g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.processId='P00601' and "
			+ "(:val is null OR :val = '' OR g.gateInId LIKE CONCAT ('%',:val,'%') OR g.vehicleNo LIKE CONCAT ('%',:val,'%')) order by g.gateInId desc")
	List<Object[]> searchEmptyVehicleGateInRecords(@Param("cid") String cid,@Param("bid") String bid,@RequestParam("val") String val);
	
	
	@Query(value="select New com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.erpDocRefNo, g.docRefNo, g.lineNo,"
			+ "p.partyName, g.imagePath, g.backImage, g.inGateInDate) "
			+ "from GateIn g LEFT OUTER JOIN Party p ON g.companyId=p.companyId and g.branchId=p.branchId and g.sl=p.partyId "
			+ "where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and "
			+ "g.docRefNo=:doc and g.containerNo=:con")
	GateIn getDataByIgmNoAndContainerNo(@Param("cid") String cid,@Param("bid") String bid,@Param("doc") String doc,
			@Param("con") String con);
	
	@Query(value="select New com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.erpDocRefNo, g.docRefNo, g.lineNo,"
			+ "p.partyName, g.imagePath, g.backImage, g.inGateInDate) "
			+ "from GateIn g LEFT OUTER JOIN Party p ON g.companyId=p.companyId and g.branchId=p.branchId and g.sl=p.partyId "
			+ "where g.companyId=:cid and g.branchId=:bid and g.gateInId=:gateinid and g.erpDocRefNo=:igmtrans and "
			+ "g.docRefNo=:igm and g.status !='D'")
	GateIn getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("gateinid") String gateinid,@Param("igmtrans") String igmtrans,
			@Param("igm") String igm);
	
}
