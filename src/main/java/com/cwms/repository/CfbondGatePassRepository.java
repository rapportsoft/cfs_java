package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFBondGatePass;

public interface CfbondGatePassRepository extends JpaRepository<CFBondGatePass, String> {

	@Query("select c from CFBondGatePass c where c.companyId=:companyId and c.branchId=:branchId and c.gatePassId =:gatePassId and c.commodity =:commodity and c.srNo =:srNo and c.status !='D'")
	CFBondGatePass getExistingDataOfGatePass(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("gatePassId") String gatePassId, @Param("commodity") String commodity, @Param("srNo") int srNo);
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.CFBondGatePass(c.companyId, c.branchId, c.gatePassId, c.srNo, c.subSrNo, " +
	        "c.profitcentreId, c.exBondingId, c.inBondingId, c.invoiceNo, c.invoiceDate, " +
	        "c.gatePassDate, c.nocNo, c.nocTransId, c.bondingNo, c.gateOutId, c.igmLineNo, " +
	        "c.shift, c.transType, c.importerName, c.importerAddress1, c.importerAddress2, " +
	        "c.importerAddress3, c.cha, c.containerNo, c.boe, c.boeNo, c.boeDate, " +
	        "c.commodity, c.grossWt, c.noOfPackage, c.inBondPackages, " +
	        "c.transporterStatus, c.transporter, c.transporterName, c.vehicleNo, c.driverName, " +
	        "c.comments, c.blockCellNo, c.sl, c.noOfPackages, c.qtyTakenOut, " +
	        "c.areaAllocated, c.areaReleased, c.examiner, c.status, c.yardLocation, " +
	        "c.yardBlock, c.contactNo, c.licenceNo, c.balQtyOut, c.totalGrossWt, " +
	        "c.tareWeight, c.netWeight, c.deliveryPersonName, c.deliveryPersonAddrs, " +
	        "c.vehGateInId, c.exBondBeNo, c.exBondedPackages, c.commodityDescription,c.createdBy,pa.partyName,c.approvedBy) " +
	    "FROM CFBondGatePass c " +
	    "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.status != 'D' " +
	    "AND ((:partyName IS NULL OR :partyName = '' OR c.gatePassId LIKE concat (:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.exBondingId LIKE concat (:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.exBondBeNo LIKE concat (:partyName, '%'))) " +
	    "ORDER BY c.gatePassId DESC")
	List<CFBondGatePass> findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("partyName") String partyName);
	
	
	
	@Query("SELECT NEW com.cwms.entities.CFBondGatePass(c.companyId, c.branchId, c.gatePassId, c.srNo, c.subSrNo, " +
	        "c.profitcentreId, c.exBondingId, c.inBondingId, c.invoiceNo, c.invoiceDate, " +
	        "c.gatePassDate, c.nocNo, c.nocTransId, c.bondingNo, c.gateOutId, c.igmLineNo, " +
	        "c.shift, c.transType, c.importerName, c.importerAddress1, c.importerAddress2, " +
	        "c.importerAddress3, c.cha, c.containerNo, c.boe, c.boeNo, c.boeDate, " +
	        "c.commodity, c.grossWt, c.noOfPackage, c.inBondPackages, " +
	        "c.transporterStatus, c.transporter, c.transporterName, c.vehicleNo, c.driverName, " +
	        "c.comments, c.blockCellNo, c.sl, c.noOfPackages, c.qtyTakenOut, " +
	        "c.areaAllocated, c.areaReleased, c.examiner, c.status, c.yardLocation, " +
	        "c.yardBlock, c.contactNo, c.licenceNo, c.balQtyOut, c.totalGrossWt, " +
	        "c.tareWeight, c.netWeight, c.deliveryPersonName, c.deliveryPersonAddrs, " +
	        "c.vehGateInId, c.exBondBeNo, c.exBondedPackages, c.commodityDescription,c.createdBy,pa.partyName,c.approvedBy) " +
	    "FROM CFBondGatePass c " +
	    "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.gatePassId = :gatePassId " +
	    "AND c.exBondBeNo = :exBondBeNo " +
	    "AND c.status != 'D' " +
	    "ORDER BY c.gatePassId DESC")
List<CFBondGatePass> getAllListOfGatePass(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("gatePassId") String gatePassId,
	    @Param("exBondBeNo") String exBondBeNo);

	
	
	
	
	
	
	
	
	@Query(value = "select DISTINCT c.exBondBeNo " +
	         "from CFBondGatePass c " +
	         "where c.companyId = :cid and c.branchId = :bid " +
	         "and c.status != 'D' " +
	         "and c.gateOutId IS NULL " +
	         "and (:val is null OR :val = '' OR c.exBondBeNo LIKE CONCAT(:val, '%'))")
	List<Object[]> getAllExbondBeNoFromGatePass(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	@Query(value = "select DISTINCT c.vehicleNo,c.driverName,c.contactNo,c.transType,c.profitcentreId,c.transporterStatus,c.transporterName,c.transporter,c.licenceNo " +
	         "from CFBondGatePass c " +
	         "where c.companyId = :cid and c.branchId = :bid and c.exBondBeNo=:exBondBeNo " +
	         "and c.status != 'D' " +
	         "and c.gateOutId IS NULL " +
	         "and (:val is null OR :val = '' OR c.exBondBeNo LIKE CONCAT(:val, '%'))")
	List<Object[]> getVehicleNoOfExbondBeNoFromGatePass(@Param("cid") String cid, @Param("bid") String bid, @Param("exBondBeNo") String exBondBeNo,@Param("val") String val);



}
