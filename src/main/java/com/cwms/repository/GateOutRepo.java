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

	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GateOut(c.companyId, c.branchId, c.finYear, c.gateOutId, " +
	        "c.erpDocRefNo, c.docRefNo, c.srNo, c.gateOutDate, c.shift, c.gatePassNo, " +
	        "c.commodityDescription, c.grossWt, c.vehicleNo, c.driverName, " +
	        "c.deliveryOrderNo, c.deliveryOrderDate, c.doValidityDate, c.exBondBeNo, " +
	        "c.comments, c.transporterStatus, c.transporter, c.transporterName, c.tripType, " +
	        "c.gatePassDate, c.gateNoIn, c.createdBy, c.approvedBy, c.status,c.profitcentreId,c.driverContactNo,c.licenceNo) " +
	    "FROM GateOut c " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
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

	
}