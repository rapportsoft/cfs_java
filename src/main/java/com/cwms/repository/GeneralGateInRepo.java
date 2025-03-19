package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GateIn;
import com.cwms.entities.GeneralGateIn;
import com.cwms.entities.GeneralJobOrderEntryDetails;
import com.cwms.entities.GenerelJobEntry;

import jakarta.transaction.Transactional;

public interface GeneralGateInRepo extends JpaRepository<GeneralGateIn, String> {

	
	 @Query(value = "select DISTINCT c.boeNo, c.jobNo, c.jobTransId, c.jobTransDate, c.cha, p.partyName,c.importerId,c.importerName,c.boeDate,c.jobDate ,c.importerAddress1,c.importerAddress2,c.importerAddress3,c.noOfPackages,c.gateInPackages " +
             "from GenerelJobEntry c " +
             "LEFT OUTER JOIN Party p ON c.companyId = p.companyId and c.branchId = p.branchId and c.cha = p.partyId " +
             "where c.companyId = :cid and c.branchId = :bid " +
             "AND (c.noOfPackages - COALESCE(c.gateInPackages, 0)) > 0 " +
             "and c.status != 'D' " +
             "and (:val is null OR :val = '' OR c.boeNo LIKE CONCAT('%',:val, '%'))")
List<Object[]> getAllBoeNoFromJobEntry(@Param("cid") String cid, 
                                @Param("bid") String bid, 
                                @Param("val") String val);



@Query(value = "select c.jobDtlTransId, c.commodityDescription, c.noOfPackages, c.gateInPackages,c.grossWeight, c.weightTakenIn,c.typeOfPackage,c.commodityId " +
        "from GeneralJobOrderEntryDetails c " +
        "where c.companyId = :cid and c.branchId = :bid and c.boeNo = :boeNo and c.jobTransId = :nocTransId " +
        "and c.status != 'D' " +
        "AND (c.noOfPackages - c.gateInPackages > 0) " +
        "and (:val is null OR :val = '' OR c.commodityDescription LIKE CONCAT('%',:val, '%'))")
List<Object[]> getCommodityDtlFromNocDtl(@Param("cid") String cid, 
                                  @Param("bid") String bid, 
                                  @Param("boeNo") String boeNo, 
                                  @Param("nocTransId") String nocTransId, 
                                  @Param("val") String val);

@Query("SELECT g FROM GeneralGateIn g WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.jobNo = :docRefNo AND g.jobTransId = :erpDocRefNo AND g.gateInId = :gateInId AND g.commodityId = :commodity")
	GeneralGateIn findAllRecordsByCriteria(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("docRefNo") String docRefNo,
	        @Param("erpDocRefNo") String erpDocRefNo,
	        @Param("gateInId") String gateInId,
	        @Param("commodity") String commodity
	    );


@Query("SELECT c FROM GeneralJobOrderEntryDetails c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.jobTransId = :nocTransId AND c.jobDtlTransId = :cfBondDtlId AND c.jobNo = :nocNo")
GeneralJobOrderEntryDetails findCfBondDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("nocTransId") String nocTransId,
    @Param("cfBondDtlId") String cfBondDtlId,
    @Param("nocNo") String nocNo
);



@Query("SELECT c FROM GenerelJobEntry c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.jobTransId = :nocTransId AND c.jobNo = :nocNo")
GenerelJobEntry findCfBondNoc(
       @Param("companyId") String companyId,
       @Param("branchId") String branchId,
       @Param("nocTransId") String nocTransId,
       @Param("nocNo") String nocNo
   );








@Modifying
@Transactional
@Query("UPDATE GeneralJobOrderEntryDetails c SET c.gateInPackages = :gateInPackages,c.weightTakenIn=:weightTakenIn WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.jobTransId=:nocTransId AND c.jobDtlTransId=:cfBondDtlId AND c.jobNo=:nocNo")
int updateCfBondDetails(
    @Param("gateInPackages") BigDecimal gateInPackages,
    @Param("weightTakenIn") BigDecimal weightTakenIn,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("nocTransId") String nocTransId,
    @Param("cfBondDtlId") String cfBondDtlId,
    @Param("nocNo") String nocNo
);


@Query("SELECT NEW com.cwms.entities.GeneralGateIn(g.companyId, g.branchId, g.gateInId, g.srNo, g.gateInDate, " +
	       "g.boeNo, g.boeDate, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
	       "g.grossWeight, g.tareWeight, g.cargoWeight, g.hazardous, g.cha, g.importerId, g.importerName, " +
	       "g.address1, g.address2, g.address3, g.commodityDescription, g.noOfPackages, g.vehicleNo, g.comments, " +
	       "g.status, g.createdBy, g.createdDate, g.approvedBy, g.handlingPerson, g.transporterName, g.commodityId, " +
	       "g.lrNo, g.gateInPackages, g.typeOfPackage, g.jobTransId, g.jobNo, g.jobTransDate, g.jobDate, g.jobNop, g.jobGwt,a.partyName,g.actCommodityId) " +
	       "FROM GeneralGateIn g " +
	       "LEFT OUTER JOIN Party pa ON g.companyId = pa.companyId AND g.branchId = pa.branchId AND g.importerName = pa.partyId " +
	       "LEFT OUTER JOIN Party a ON g.companyId = a.companyId AND g.branchId = a.branchId AND g.cha = a.partyId " +
	       "WHERE g.companyId = :companyId " +
	       "AND g.branchId = :branchId " +
	       "AND g.status != 'D' " +
//	       "AND g.gateInType = 'BON' " +
	       "AND ((:search IS NULL OR :search = '' OR g.gateInId LIKE concat(:search, '%')) " +
	       "OR (:search IS NULL OR :search = '' OR g.transporterName LIKE concat(:search, '%')) " +
	       "OR (:search IS NULL OR :search = '' OR g.vehicleNo LIKE concat(:search, '%')) " +
	       "OR (:search IS NULL OR :search = '' OR g.importerName LIKE concat(:search, '%'))) " +
	       "GROUP BY g.gateInId " +
	       "ORDER BY g.gateInId DESC")
	List<GeneralGateIn> findGateInByCriteria(
	       @Param("companyId") String companyId,
	       @Param("branchId") String branchId,
	       @Param("search") String search);






@Query("SELECT NEW com.cwms.entities.GeneralGateIn(g.companyId, g.branchId, g.gateInId, g.srNo, g.gateInDate, " +
	       "g.boeNo, g.boeDate, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
	       "g.grossWeight, g.tareWeight, g.cargoWeight, g.hazardous, g.cha, g.importerId, g.importerName, " +
	       "g.address1, g.address2, g.address3, g.commodityDescription, g.noOfPackages, g.vehicleNo, g.comments, " +
	       "g.status, g.createdBy, g.createdDate, g.approvedBy, g.handlingPerson, g.transporterName, g.commodityId, " +
	       "g.lrNo, g.gateInPackages, g.typeOfPackage, g.jobTransId, g.jobNo, g.jobTransDate, g.jobDate, g.jobNop, g.jobGwt,p.partyName,g.actCommodityId) " +
	       "FROM GeneralGateIn g " +
	       "LEFT JOIN Party p ON g.companyId = p.companyId AND g.branchId = p.branchId AND g.cha = p.partyId " +
	       "WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.gateInId = :gateInId")
	List<GeneralGateIn> getDataOfRowUsingGateInId(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("gateInId") String gateInId);


@Modifying
@Transactional
@Query("UPDATE GeneralGateIn c SET c.receivingPackages = :receivingPackages, c.receivingWeight = :receivingWeight " + 
       "WHERE c.companyId = :companyId AND " + 
       "c.branchId = :branchId AND " + 
       "c.gateInId = :gateInId AND " + 
       "c.commodityId = :commodityId")
int updateGateInDetailAfterReceiving( 
       @Param("receivingPackages") BigDecimal receivingPackages,
       @Param("receivingWeight") BigDecimal receivingWeight,
       @Param("companyId") String companyId,
       @Param("branchId") String branchId,
       @Param("gateInId") String gateInId,
       @Param("commodityId") String commodityId);


@Query("SELECT c FROM GeneralGateIn c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.gateInId = :gateInId AND c.commodityId = :commodityId")
GeneralGateIn getGeneralGateIn(
       @Param("companyId") String companyId,
       @Param("branchId") String branchId,
       @Param("gateInId") String gateInId,
       @Param("commodityId") String commodityId
   );
@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralGateIn( " +
	       "g.gateInId, g.boeNo, g.jobTransId, " +
	       "g.jobNo " +
	       ") " +
	       "FROM GeneralGateIn g " +
	       "WHERE g.companyId = :companyId " +
	       "AND g.branchId = :branchId " +
	       "AND g.gateInId = :gateInId " +
	       "AND g.status != 'D'")
	List<GeneralGateIn> getDistinct(
	       @Param("companyId") String companyId,
	       @Param("branchId") String branchId,
	       @Param("gateInId") String gateInId);












@Query("SELECT NEW com.cwms.entities.GeneralGateIn(g.companyId, g.branchId, g.gateInId, g.srNo, g.gateNo, g.gateInDate, " +
	       "g.boeNo, g.boeDate, g.containerNo, g.grossWeight, g.cha, g.importerId, g.importerName, g.address1, g.address2, g.address3, " +
	       "g.commodityDescription, g.noOfPackages, g.vehicleNo, g.driverName, g.comments, g.status, a.partyName, " +
	       "g.transporter, g.transporterName, g.commodityId, g.actCommodityId, g.lrNo, g.gateInPackages, g.typeOfPackage, " +
	       "g.jobTransId, g.jobNo, g.jobTransDate, g.jobDate, g.jobNop, g.jobGwt, g.vehicleType) " +
	       "FROM GeneralGateIn g " +
	       "LEFT OUTER JOIN Party a ON g.companyId = a.companyId AND g.branchId = a.branchId AND g.cha = a.partyId " +
	       "WHERE g.companyId = :companyId " +
	       "AND g.branchId = :branchId " +
	       "AND g.gateInId = :gateInId " +
	       "AND g.jobTransId = :jobTransId " +
	       "AND g.boeNo = :boeNo " +
	       "AND g.status != 'D' ")
	List<GeneralGateIn> findDataForGettingPrint(
	       @Param("companyId") String companyId,
	       @Param("branchId") String branchId,
	       @Param("gateInId") String gateInId,
	       @Param("jobTransId") String jobTransId,
	       @Param("boeNo") String boeNo
	);








//@Query("SELECT NEW com.cwms.entities.GeneralGateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
//	       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
//	       "g.grossWeight, g.eirGrossWeight, g.cha, g.importerName, g.commodityDescription, g.actualNoOfPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
//	       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, '', '', '', g.inGateInDate, g.weightTakenIn,g.typeOfPackage,g.commodity,g.gateInPackages) " +
//	       "FROM GeneralGateIn g " +
//	       "WHERE g.companyId = :companyId " +
//	       "AND g.branchId = :branchId " +
//	       "AND g.gateInId = :gateInId " +
//	       "AND g.erpDocRefNo = :erpDocRefNo " +
//	       "AND g.boeNo = :boeNo " +
//	       "AND g.status != 'D' ")
//List<GeneralGateIn> findDataForGettingPrintDetails(
//	       @Param("companyId") String companyId,
//	       @Param("branchId") String branchId,
//	       @Param("gateInId") String gateInId,
//	       @Param("erpDocRefNo") String erpDocRefNo,
//	       @Param("boeNo") String boeNo
//	       );
}
