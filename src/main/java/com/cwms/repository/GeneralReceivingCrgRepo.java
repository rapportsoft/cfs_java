package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.GeneralGateIn;
import com.cwms.entities.GeneralReceivingCrg;
import com.cwms.entities.GeneralReceivingCrgId;

public interface GeneralReceivingCrgRepo extends JpaRepository<GeneralReceivingCrg, GeneralReceivingCrgId>{

	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralGateIn(" +
		       "c.companyId, c.branchId, c.gateInId, c.srNo, c.gateNo, c.gateInDate, " +
		       "c.boeNo, c.boeDate, c.profitcentreId, c.containerNo, c.containerSize, " +
		       "c.containerType, c.containerStatus, c.isoCode, c.grossWeight, c.tareWeight, " +
		       "c.cargoWeight, c.cha, c.importerId, c.importerName, c.commodityDescription, " +
		       "c.noOfPackages, c.receivingPackages, c.receivingWeight, c.receivingDone, " +
		       "c.vehicleNo, c.handlingPerson, c.transporter, c.transporterName, c.commodityId, " +
		       "c.actCommodityId, c.lrNo, c.gateInPackages, c.typeOfPackage, c.jobTransId, " +
		       "c.jobNo, c.jobTransDate, c.jobDate, c.jobNop, c.jobGwt,pa.partyName,g.area) " +
		       "FROM GeneralGateIn c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		       "LEFT OUTER JOIN GenerelJobEntry g ON c.companyId = g.companyId AND c.branchId = g.branchId AND c.jobNo = g.jobNo and c.jobTransId = g.jobTransId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND (c.noOfPackages - COALESCE(c.receivingPackages, 0)) > 0 " +
		       "AND (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE CONCAT('%',:partyName, '%')) " +
		       "ORDER BY c.boeNo DESC")
		List<GeneralGateIn> findCfbondnocByCompanyIdAndBranchIdForInbondScreen(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("partyName") String partyName);
	
	
	@Query("SELECT NEW com.cwms.entities.GeneralGateIn(" +
		       "c.companyId, c.branchId, c.gateInId, c.srNo, c.gateNo, c.gateInDate, " +
		       "c.boeNo, c.boeDate, c.profitcentreId, c.containerNo, c.containerSize, " +
		       "c.containerType, c.containerStatus, c.isoCode, c.grossWeight, c.tareWeight, " +
		       "c.cargoWeight, c.cha, c.importerId, c.importerName, c.commodityDescription, " +
		       "c.noOfPackages, c.receivingPackages, c.receivingWeight, c.receivingDone, " +
		       "c.vehicleNo, c.handlingPerson, c.transporter, c.transporterName, c.commodityId, " +
		       "c.actCommodityId, c.lrNo, c.gateInPackages, c.typeOfPackage, c.jobTransId, " +
		       "c.jobNo, c.jobTransDate, c.jobDate, c.jobNop, c.jobGwt,pa.partyName,c.jobGwt) " +
		       "FROM GeneralGateIn c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.jobTransId = :nocTransId " +
		       "AND c.jobNo = :nocNo " +
		       "AND c.noOfPackages > 0 " +
		       "AND (c.noOfPackages - COALESCE(c.receivingPackages, 0)) > 0 " +
		       "AND c.status != 'D'" +
			  "GROUP BY c.jobTransId,c.jobNo,c.commodityId ")
		List<GeneralGateIn> getCfBondNocDtl(
				@Param("companyId") String companyId, 
                @Param("branchId") String branchId, 
                @Param("nocTransId") String nocTransId, 
                @Param("nocNo") String nocNo);
	
	
	 @Query("SELECT c " +
		       "FROM GeneralReceivingCrg c " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.receivingId = :receivingId " +
		       "ORDER BY c.receivingId DESC")
	 GeneralReceivingCrg findCfinbondCrg(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("receivingId") String receivingId
		);
	 
	 
	 @Query("SELECT c " +
		       "FROM GeneralReceivingCrg c " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.receivingId = :receivingId and c.depositNo=:depositNo " +
		       "ORDER BY c.receivingId DESC")
	 GeneralReceivingCrg findCfinbondCrgAfterDelivery(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("receivingId") String receivingId,
		    @Param("depositNo") String depositNo
		);
	 
	 
	 @Query("SELECT NEW com.cwms.entities.GeneralReceivingCrg(c.companyId, c.branchId, c.receivingId, c.depositNo, " +
		       "c.importerName, c.areaOccupied, c.cargoCondition, c.gateInPackages, c.gateInWeight, " +
		       "c.receivedPackages, c.receivedWeight, c.spaceAllocated, c.status, c.jobNo, c.jobTransId,c.boeNo,c.boeDate,c.receivingDate) " +
		       "FROM GeneralReceivingCrg c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.receivingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.importerName LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.jobTransId LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.receivingId DESC")
		List<GeneralReceivingCrg> findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("partyName") String partyName
		);

	 
	 @Query("SELECT NEW com.cwms.entities.GeneralReceivingCrg(c.companyId, c.branchId, c.receivingId, c.depositNo, " +
		       "c.importerName, c.receivingDate, c.jobDate, c.jobTransDate, c.profitcentreId, c.gateInId, c.boeNo, c.boeDate, " +
		       "c.invoiceNo, c.invoiceDate, c.challanNo, c.challanDate, c.cha, c.importerId, c.crossingCargo, c.noOfMarks, " +
		       "c.grossWeight, c.uom, c.areaOccupied, c.areaAllocated, c.cargoCondition, c.gateInPackages, c.gateInWeight, " +
		       "c.totalDeliveredPkg, c.receivedPackages, c.receivedWeight, c.deliveredPackages, c.spaceAllocated, c.shortQty, " +
		       "c.comments, c.invoiceStatus, c.status, c.createdBy, c.createdDate, pa.partyName, c.approvedBy, c.approvedDate, " +
		       "c.remark, c.jobNo, c.jobTransId, c.noOf20Ft, c.noOf40Ft, c.assesmentId, c.transporterName,c.cargoValue,c.cargoDuty) " +
		       "FROM GeneralReceivingCrg c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.jobTransId = :nocTransId " +
		       "AND c.receivingId = :inBondingId AND c.jobNo = :nocNo AND c.status != 'D' " +
		       "ORDER BY c.receivingId DESC")
		GeneralReceivingCrg findCfBondCrgData(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("nocTransId") String nocTransId,
		    @Param("inBondingId") String inBondingId,
		    @Param("nocNo") String nocNo
		);


}