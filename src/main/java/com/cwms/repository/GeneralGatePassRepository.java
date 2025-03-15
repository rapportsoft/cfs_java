package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfExBondGrid;
import com.cwms.entities.GeneralGatePassCargo;

public interface GeneralGatePassRepository extends JpaRepository<GeneralGatePassCargo, String> {

	@Query(value = "select DISTINCT c.deliveryId,c.jobNo,c.boeNo,c.commodityId,c.jobTransId,c.receivingId,c.status,c.boeDate,c.depositNo " +
            "from GeneralDeliveryDetails c " +
            "where c.companyId = :cid and c.branchId = :bid " +
            "and COALESCE(c.deliveredPackages, 0) - COALESCE(c.gatePassPackages, 0) > 0 " +
            "and c.status != 'D' " +
            "and (:val is null OR :val = '' OR c.deliveryId LIKE CONCAT('%',:val, '%'))")
List<Object[]> getAllDataFormDeliveryDetails(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);







@Query(value = "select DISTINCT c.commodityId, c.commodityDescription,c.deliveredPackages,"
		+ "c.deliveryId,c.receivingId,c.receivingPackages,c.deliveredWeight ,c.receivingWeight, c.jobTransId ,c.gatePassPackages,c.actCommodityId,c.typeOfPackage,c.containerNo " +
        "from GeneralDeliveryDetails c " +
        "where c.companyId = :cid and c.branchId = :bid and c.deliveryId = :deliveryId " +
        "and COALESCE(c.deliveredPackages, 0) - COALESCE(c.gatePassPackages, 0) > 0 " +
        "and c.status != 'D' " +
        "and (:val is null OR :val = '' OR c.commodityDescription LIKE CONCAT('%',:val, '%'))")
List<Object[]> generalGatePassRepository(@Param("cid") String cid, 
                                       @Param("bid") String bid, 
                                       @Param("deliveryId") String deliveryId, 
                                       @Param("val") String val);




//@Query(value = "select DISTINCT c.importerName " +
//        "from GeneralDeliveryCrg c " +
//        "where c.companyId = :cid and c.branchId = :bid and c.deliveryId = :deliveryId  and c.receivingId=:receivingId" +
//        "and c.status != 'D' ")
//Object[] getImpoterName(@Param("cid") String cid, 
//                                       @Param("bid") String bid, 
//                                       @Param("deliveryId") String deliveryId, 
//                                       @Param("receivingId") String receivingId);


@Query(value = "SELECT DISTINCT c.importerName " +
        "FROM GeneralDeliveryCrg c " +
        "WHERE c.companyId = :cid " +
        "AND c.branchId = :bid " +
        "AND c.deliveryId = :deliveryId " +
        "AND c.receivingId = :receivingId " +
        "AND c.status != 'D'")
List<String> getImporterName(@Param("cid") String cid, 
                      @Param("bid") String bid, 
                      @Param("deliveryId") String deliveryId, 
                      @Param("receivingId") String receivingId);



@Query("SELECT c from CfExBondGrid c where c.companyId =:companyId and c.branchId=:branchId and c.exBondingId=:exBondingId and c.cfBondDtlId=:cfBondDtlId and c.status !='D'")
List<CfExBondGrid> getDataAfterSave(@Param ("companyId") String companyId,
		@Param ("branchId") String branchId ,
		@Param ("exBondingId") String exBondingId,
		@Param ("cfBondDtlId") String cfBondDtlId);



@Query("SELECT COALESCE(SUM(c.gatePassPackages), 0) FROM GeneralGatePassCargo c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.deliveryId = :deliveryId " +
	       "AND c.gatePassId = :gatePassId " +
	       "AND c.commodityId = :commodityId ")
BigDecimal getSumOfQtyTakenOutForCommodity(@Param("companyId") String companyId,
	                            @Param("branchId") String branchId,
	                            @Param("deliveryId") String deliveryId,
	                            @Param("gatePassId") String gatePassId,
	                            @Param("commodityId") String commodityId);


@Query("SELECT COALESCE(SUM(c.qtyTakenOut), 0) FROM GeneralDeliveryGrid c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.status != 'D' " +
	       "AND c.deliveryId = :deliveryId " +
	       "AND c.receivingId = :receivingId ")
BigDecimal  getSumOfQtyTakenOutCommodityWise(@Param("companyId") String companyId,
	                            @Param("branchId") String branchId,
	                            @Param("deliveryId") String deliveryId,
	                            @Param("receivingId") String receivingId);


//@Query("SELECT COALESCE(SUM(c.gatePassPackages), 0) FROM GeneralGatePassCargo c " +
//	       "WHERE c.companyId = :companyId " +
//	       "AND c.branchId = :branchId " +
//	       "AND c.gatePassId = :gatePassId " )
//BigDecimal getSUmOfGatePassQuntity(@Param("companyId") String companyId,
//	                            @Param("branchId") String branchId,
//	                            @Param("gatePassId") String gatePassId);


@Query("SELECT COALESCE(SUM(c.gatePassPackages), 0) FROM GeneralGatePassCargo c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.gatePassId = :gatePassId " )
BigDecimal getSUmOfGatePassQuntity(@Param("companyId") String companyId,
	                            @Param("branchId") String branchId,
	                            @Param("gatePassId") String gatePassId);


@Query("SELECT c FROM GeneralGatePassCargo c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.gatePassId = :gatePassId " +
	       "AND c.status != 'D'")
	List<GeneralGatePassCargo> updateAfterApprove(@Param("companyId") String companyId, 
	                                        @Param("branchId") String branchId, 
	                                        @Param("gatePassId") String gatePassId);


@Query("SELECT c FROM GeneralGatePassCargo c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.gatePassId = :gatePassId " +
	       "AND c.commodityId = :commodityId " +
	       "AND c.srNo = :srNo " +
	       "AND c.status != 'D'")
GeneralGatePassCargo toUpdateGatePassCargo(@Param("companyId") String companyId, 
	                                        @Param("branchId") String branchId, 
	                                        @Param("gatePassId") String gatePassId,
	                                        @Param("commodityId") String commodityId,
	                                        @Param("srNo") int srNo
	                                        );




@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralGatePassCargo(c.companyId, c.branchId, c.gatePassId, c.gatePassDate, " +
        "c.srNo, c.receivingId, c.deliveryId, c.gateOutId, c.gateOutDate, c.importerName, " +
        "c.transporterName, c.vehicleNo, c.gatePassPackages, c.gatePassWeight, c.status,c.boeNo,c.createdBy,c.approvedBy,c.transType,c.shift,c.comments,c.driverName) " +
    "FROM GeneralGatePassCargo c " +
    "WHERE c.companyId = :companyId " +
    "AND c.branchId = :branchId " +
    "AND c.status != 'D' " +
    "AND ((:partyName IS NULL OR :partyName = '' OR c.gatePassId LIKE concat (:partyName, '%')) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.deliveryId LIKE concat (:partyName, '%')) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.transporterName LIKE concat (:partyName, '%')) " +
        "OR (:partyName IS NULL OR :partyName = '' OR c.importerName LIKE concat (:partyName, '%'))) " +
    "GROUP BY c.gatePassId " +
    "ORDER BY c.gatePassId DESC")
List<GeneralGatePassCargo> findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId,
    @Param("partyName") String partyName);

@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralGatePassCargo( " +
        "c.companyId, c.branchId, c.gatePassId, c.gatePassDate, c.srNo, c.depositNo, " +
        "c.receivingId, c.jobNo, c.jobTransId, c.commodityId, c.actCommodityId, " +
        "c.typeOfPackage, c.commodityDescription, c.profitCentreId, c.deliveryId, " +
        "c.gateOutId, c.gateOutDate, c.shift, c.transType, c.importerName, " +
        "c.containerNo, c.boeNo, c.boeDate, c.grossWeight, c.receivingPackages, " +
        "c.receivingWeight, c.deliveredPackages, c.deliveredWeight, " +
        "c.transporterName, c.vehicleNo, c.driverName, c.comments, " +
        "c.gatePassPackages, c.gatePassWeight, c.status, c.createdBy, " +
        "c.createdDate, c.approvedBy, c.approvedDate, c.botplRecptNo, c.prevQtyTakenOut) " +
    "FROM GeneralGatePassCargo c " +
    "WHERE c.companyId = :companyId " +
    "AND c.branchId = :branchId " +
    "AND c.gatePassId = :gatePassId " +
    "AND c.status != 'D' " +
    "ORDER BY c.gatePassId DESC")
List<GeneralGatePassCargo> getAllListOfGatePass(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId,
    @Param("gatePassId") String gatePassId
);



}
