package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GeneralDeliveryCrg;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingGrid;
import com.cwms.entities.GeneralReceivingGateInDtl;

public interface GeneralDeliveryCrgRepo extends JpaRepository<GeneralDeliveryCrg, String> {

	@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralReceivingCrg(" +
		       "c.companyId, c.branchId, c.receivingId, c.depositNo, c.importerName, " +
		       "c.receivingDate, c.profitcentreId, c.boeNo, c.boeDate, c.cha, " +
		       "c.noOfMarks, c.uom, c.areaOccupied, c.gateInPackages, c.gateInWeight, " +
		       "c.receivedPackages, c.receivedWeight, c.spaceAllocated, c.noOf20Ft, " +
		       "c.noOf40Ft, c.cargoValue, c.cargoDuty, pa.partyName,c.deliveredPackages,c.deliveredWeight,c.importerId) " +
		       "FROM GeneralReceivingCrg c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId " +
		       "AND c.branchId = pa.branchId " +
		       "AND c.cha = pa.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND (c.receivedPackages - COALESCE(c.deliveredPackages, 0)) > 0 " +
		       "AND (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE CONCAT('%',:partyName, '%')) " +
		       "ORDER BY c.createdDate DESC")
		List<GeneralReceivingCrg> getDataByBoeNoForDeliveryScreen(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("partyName") String partyName);
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralReceivingCrg(" +
		       "c.companyId, c.branchId, c.receivingId, c.depositNo, c.importerName, " +
		       "c.receivingDate, c.profitcentreId, c.boeNo, c.boeDate, c.cha, " +
		       "c.noOfMarks, c.uom, c.areaOccupied, c.gateInPackages, c.gateInWeight, " +
		       "c.receivedPackages, c.receivedWeight, c.spaceAllocated, c.noOf20Ft, " +
		       "c.noOf40Ft, c.cargoValue, c.cargoDuty, pa.partyName,c.deliveredPackages,c.deliveredWeight,c.importerId) " +
		       "FROM GeneralReceivingCrg c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId " +
		       "AND c.branchId = pa.branchId " +
		       "AND c.cha = pa.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND (c.receivedPackages - COALESCE(c.deliveredPackages, 0)) > 0 " +
		       "AND (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE CONCAT('%',:partyName, '%')) " +
		       "ORDER BY c.createdDate ASC")
		List<GeneralReceivingCrg> getDataByBoeNoForDeliveryScreenFIFO(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("partyName") String partyName);
	
	
	@Query("SELECT c from GeneralReceivingGrid c where c.companyId =:companyId and c.branchId=:branchId and c.receivingId=:receivingId and (c.receivedPackages - COALESCE(c.deliveredPackages, 0)) > 0 AND c.status !='D'")
	List<GeneralReceivingGrid> getDataForDeliveryScreen(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("receivingId") String receivingId);
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralReceivingGateInDtl(" +
		       "c.companyId, c.branchId, c.srNo, c.receivingId, c.gateInId, c.gateInDate, " +
		       "c.containerNo, c.containerSize, c.containerType, c.vehicleNo, c.jobNop, " +
		       "c.jobGwt, c.gateInPackages, c.gateInWeight, c.receivingPackages, " +
		       "c.receivingWeight, c.status, c.deliveredPackages, c.deliveredWeight, " +
		       "c.commodityDescription, c.typeOfPackage, c.commodityId, " +
		       "c.jobDtlTransId, c.actCommodityId,c.jobNo,c.jobTransId,c.createdDate,c.depositNo) " +
		       "FROM GeneralReceivingGateInDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.receivingId = :receivingId " +
		       "AND (c.receivingPackages - COALESCE(c.deliveredPackages, 0)) > 0 " +
		       "AND c.status != 'D'")
		List<GeneralReceivingGateInDtl> getDataForDeliveryScreenFromReceivingDTL(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param ("receivingId") String receivingId);
	
	
	@Query("SELECT c " +
		       "FROM GeneralDeliveryCrg c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.deliveryId = :deliveryId " +
		       "AND c.depositNo = :depositNo " +
		       "AND c.receivingId = :receivingId " +
		       "ORDER BY c.deliveryId DESC")
		GeneralDeliveryCrg findSavedGeneralDelivery(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("deliveryId") String deliveryId,
		    @Param("depositNo") String depositNo,
		    @Param("receivingId") String receivingId
		);
	
	
	@Query("SELECT NEW com.cwms.entities.GeneralDeliveryCrg(c.companyId, c.branchId, c.deliveryId, c.receivingId, " +
		       "c.deliveryDate, c.profitCentreId, c.boeNo, c.boeDate, c.depositNo, c.importerName, " +
		       "c.cha, c.deliveredPackages, c.status, pa.partyName, c.transporterName) " +
		       "FROM GeneralDeliveryCrg c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId " +
		       "AND c.branchId = pa.branchId " +
		       "AND c.cha = pa.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.receivingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.importerName LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.depositNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR pa.partyName LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.deliveryId LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.receivingId DESC")
		List<GeneralDeliveryCrg> findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("partyName") String partyName
		);
	
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.GeneralDeliveryCrg( " +
		       "c.companyId, c.branchId, c.deliveryId, c.receivingId, c.deliveryDate, c.profitCentreId, c.boeNo, c.boeDate, " +
		       "c.depositNo, c.onAccountOf, c.importerId, c.importerName, c.cha, c.shift, c.uom, c.areaOccupied, c.areaReleased, " +
		       "c.areaBalanced, c.areaRemaining, c.receivedPackages, c.deliveredPackages, c.balancedPackages, c.remainingPackages, " +
		       "c.receivedGw, c.deliveryGw, c.balanceGw, c.remainingGw, c.spaceAllocated, c.comments, c.gatePassAllow, c.noOf20Ft, " +
		       "c.noOf40Ft, c.status, c.createdBy, pa.partyName, c.approvedBy, c.handlingEquip, c.handlingEquip1, c.handlingEquip2, " +
		       "c.owner, c.owner1, c.owner2, c.numberOfMarks, c.transporterName,c.cargoValue,c.cargoDuty,c.rCargoValue,c.rCargoDuty) " +
		       "FROM GeneralDeliveryCrg c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId " +
		       "AND c.deliveryId = :deliveryId " +
		       "AND c.receivingId = :receivingId " +
		       "AND c.depositNo = :depositNo " +
		       "AND c.boeNo = :boeNo " +
		       "AND c.status != 'D' " +
		       "ORDER BY c.receivingId DESC")
		GeneralDeliveryCrg getGeneralDeliveryCrgData(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("deliveryId") String deliveryId,
		    @Param("receivingId") String receivingId,
		    @Param("depositNo") String depositNo,
		    @Param("boeNo") String boeNo
		);




}
