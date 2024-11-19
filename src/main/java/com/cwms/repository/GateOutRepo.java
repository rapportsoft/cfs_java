package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.GateOut;

@Repository
public interface GateOutRepo extends JpaRepository<GateOut, String>{


	@Query(value="select g.gateOutId, DATE_FORMAT(g.gateOutDate, '%d %M %y'), g.gatePassNo, DATE_FORMAT(g.gatePassDate, '%d %M %y'), "
            + "g.containerNo, g.vehicleNo "
            + "from GateOut g where g.companyId = :cid and g.branchId = :bid and g.status != 'D' and g.processId = 'P00223' "
            + "and (:val is null OR :val = '' OR g.gateOutId LIKE CONCAT('%', :val, '%') OR g.gatePassNo LIKE CONCAT('%', :val, '%') "
            + "OR g.containerNo LIKE CONCAT('%', :val, '%') OR g.vehicleNo LIKE CONCAT('%', :val, '%')) "
            + "ORDER BY g.gateOutDate")
List<Object[]> searchExportGateOut(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);

	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.gateOutDate, c.shift, c.gatePassNo, " +
	        "c.commodityDescription, c.grossWt, c.vehicleNo, c.driverName, " +
	        "c.deliveryOrderNo, c.deliveryOrderDate, c.doValidityDate, c.exBondBeNo, " +
	        "c.comments, c.transporterStatus, c.transporter, c.transporterName, c.tripType, " +
	        "c.gatePassDate, c.gateNoIn, c.createdBy, c.approvedBy, c.status,c.profitcentreId,c.driverContactNo,c.licenceNo) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.processId='P00500' "+
	    "AND c.status != 'D' " +
	    "AND ((:partyName IS NULL OR :partyName = '' OR c.gateOutId LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.gatePassNo LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.erpDocRefNo LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.docRefNo LIKE concat(:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.exBondBeNo LIKE concat(:partyName, '%'))) " +
	    "ORDER BY c.gateOutId DESC")
	List<GateOut> findDataOfGateOutDetails(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("partyName") String partyName);

	@Query("select c from GateOut c where c.companyId=:companyId and c.branchId=:branchId and c.gateOutId =:gateOutId and c.commodity =:commodity and c.srNo =:srNo and c.status !='D'")
	GateOut getExistingDataOfGateOut(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("gateOutId") String gateOutId, @Param("commodity") String commodity, @Param("srNo") String srNo);
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.gateOutDate, c.shift, c.gatePassNo, " +
	        "c.commodityDescription, c.grossWt, c.vehicleNo, c.driverName, " +
	        "c.deliveryOrderNo, c.deliveryOrderDate, c.doValidityDate, c.exBondBeNo, " +
	        "c.comments, c.transporterStatus, c.transporter, c.transporterName, c.tripType, " +
	        "c.gatePassDate, c.gateNoIn, c.createdBy, c.approvedBy, c.status,c.profitcentreId,c.driverContactNo,c.licenceNo) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.gateOutId = :gateOutId " +
	    "AND c.exBondBeNo = :exBondBeNo " +
	    "AND c.status != 'D' " +
	    "ORDER BY c.gateOutId DESC")
	List<GateOut> getAllListOfGateOut(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("gateOutId") String gateOutId,
	    @Param("exBondBeNo") String exBondBeNo);
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.igmLineNo, c.grossWt, c.natureOfCargo, c.actualNoOfPackages, " +
	        "c.qtyTakenOut, c.gatePassNo,c.commodityDescription) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.gateOutId = :gateOutId " +
	    "AND c.status != 'D' " +
	    "AND c.vehicleNo = :vehicleNo " +
	    "ORDER BY c.gateOutId DESC")
	List<GateOut> getCommodityDetailsOfVehicleNo(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("gateOutId") String gateOutId, 
	    @Param("vehicleNo") String vehicleNo);

	
	
	@Query("SELECT NEW com.cwms.entities.GateOut(c.gateOutId, c.transType, c.gateOutDate, c.gateNoOut, p.partyName," +
		    "c.transporterStatus, c.transporterName, c.vehicleNo, c.driverName, c.gatePassNo," +
	        "c.gatePassDate, c.comments, c.status, c.createdBy) " +
		    "FROM GateOut c " +
	        "LEFT OUTER JOIN Party p ON c.companyId=p.companyId and c.branchId=p.branchId and c.sl=p.partyId " +
		    "WHERE c.companyId = :companyId " +
		    "AND c.branchId = :branchId " +
		    "AND c.gateOutId = :gateOutId " +
		    "AND c.status != 'D' ")
		List<GateOut> getEportGateOutData(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("gateOutId") String gateOutId);

		
//		@Query(value="select g.gateOutId,DATE_FORMAT(g.gateOutDate,'%d %M %y'),g.gatePassNo,DATE_FORMAT(g.gatePassDate,'%d %M %y'),"
//				+ "g.containerNo,g.vehicleNo "
//				+ "from GateOut g where g.companyId=:cid and g.branchId=:bid and g.status != 'D' and g.processId='P00223' "
//				+ "and (:val is null OR :val = '' OR g.gateOutId LIKE CONCAT('%',:val,'%') OR g.gatePassNo LIKE CONCAT('%',:val,'%') "
//				+ "OR g.containerNo LIKE CONCAT('%',:val,'%') OR g.vehicleNo LIKE CONCAT('%',:val,'%'))")
//		List<Object[]> searchExportGateOut(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}