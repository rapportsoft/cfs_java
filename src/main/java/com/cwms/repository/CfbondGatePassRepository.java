package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFBondGatePass;
import com.cwms.entities.CfexBondCrgDtl;

import jakarta.transaction.Transactional;

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
	        "c.vehGateInId, c.exBondBeNo, c.exBondedPackages, c.commodityDescription,c.createdBy,pa.partyName,c.approvedBy,c.deliveryOrderNo,c.deliveryOrderDate,c.doValidityDate,c.yardQtyTakenOut,c.yardAreaReleased) " +
	    "FROM CFBondGatePass c " +
	    "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.status != 'D' " +
	    "AND ((:partyName IS NULL OR :partyName = '' OR c.gatePassId LIKE concat (:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.exBondingId LIKE concat (:partyName, '%')) " +
	        "OR (:partyName IS NULL OR :partyName = '' OR c.exBondBeNo LIKE concat (:partyName, '%'))) " +
	        "GROUP BY c.gatePassId " +
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
	        "c.vehGateInId, c.exBondBeNo, c.exBondedPackages, c.commodityDescription,c.createdBy,pa.partyName,c.approvedBy,c.deliveryOrderNo,c.deliveryOrderDate,c.doValidityDate,c.yardQtyTakenOut,c.yardAreaReleased) " +
	    "FROM CFBondGatePass c " +
	    "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
	    "WHERE c.companyId = :companyId " +
	    "AND c.branchId = :branchId " +
	    "AND c.gatePassId = :gatePassId " +
//	    "AND c.exBondBeNo = :exBondBeNo " +
	    "AND c.status != 'D' " +
	    "ORDER BY c.gatePassId DESC")
List<CFBondGatePass> getAllListOfGatePass(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId,
	    @Param("gatePassId") String gatePassId
//	    @Param("exBondBeNo") String exBondBeNo
	    );


	
	
	
	
	
	
	@Query(value = "select DISTINCT c.exBondBeNo " +
	         "from CFBondGatePass c " +
	         "where c.companyId = :cid and c.branchId = :bid " +
	         "and c.status = 'A' " +
	         "and (c.gateOutId IS NULL OR c.gateOutId = '') " +
	         "and (:val is null OR :val = '' OR c.exBondBeNo LIKE CONCAT(:val, '%'))")
	List<Object[]> getAllExbondBeNoFromGatePass(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	
	@Query(value = "select DISTINCT c.vehicleNo,c.driverName,c.contactNo,c.transType,c.profitcentreId,c.transporterStatus,c.transporterName,c.transporter,c.licenceNo,c.exBondBeNo,c.gatePassId,c.gatePassDate,c.vehGateInId,c.deliveryOrderNo,c.deliveryOrderDate,c.doValidityDate " +
	         "from CFBondGatePass c " +
	         "where c.companyId = :cid and c.branchId = :bid and c.exBondBeNo=:exBondBeNo " +
	         "and c.status = 'A' " +
	         "and (c.gateOutId IS NULL OR c.gateOutId = '') " +
	         "and (:val is null OR :val = '' OR c.exBondBeNo LIKE CONCAT(:val, '%'))")
	List<Object[]> getVehicleNoOfExbondBeNoFromGatePass(@Param("cid") String cid, @Param("bid") String bid, @Param("exBondBeNo") String exBondBeNo,@Param("val") String val);


	
	
	
	
	
	
	
	
	@Query(value = "select DISTINCT c.vehicleNo,c.driverName,c.contactNo,c.transType,c.profitcentreId,c.transporterStatus,c.transporterName,c.transporter,c.licenceNo,c.exBondBeNo,c.gatePassId,c.gatePassDate,c.vehGateInId,c.deliveryOrderNo,c.deliveryOrderDate,c.doValidityDate " +
	         "from CFBondGatePass c " +
	         "where c.companyId = :cid and c.branchId = :bid " +
	         "and c.status = 'A' " +
	         "and (c.gateOutId IS NULL OR c.gateOutId = '') " +
	         "and (:val is null OR :val = '' OR c.vehicleNo LIKE CONCAT(:val, '%'))")
	List<Object[]> getVehicleNo(@Param("cid") String cid, @Param("bid") String bid,@Param("val") String val);

	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.CFBondGatePass(c.companyId, c.branchId, c.finYear, c.gatePassId, " +
		       "c.srNo, c.nocTransId,c.nocNo, c.bondingNo, c.igmLineNo, c.noOfPackage, c.noOfPackages, " +
		       "c.qtyTakenOut, c.exBondBeNo, c.exBondedPackages, c.commodityDescription,c.commodity,c.grossWt,cf.typeOfPackage,c.deliveryOrderNo,c.deliveryOrderDate,c.doValidityDate ) " +
		       "FROM CFBondGatePass c " +
		       "LEFT OUTER JOIN CfBondNocDtl cf ON c.companyId=cf.companyId AND c.branchId=cf.branchId AND c.nocNo = cf.nocNo AND c.commodity=cf.cfBondDtlId "+
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "and (c.gateOutId IS NULL OR c.gateOutId = '') " +
		       "AND c.status != 'D' " +
		       "AND c.vehicleNo = :vehicleNo " +
		       "ORDER BY c.gatePassId DESC")
		List<CFBondGatePass> getCommodityDetailsOfVehicleNo(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("vehicleNo") String vehicleNo);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CFBondGatePass c set c.gateOutId=:gateOutId where c.companyId=:companyId and c.branchId=:branchId and c.gatePassId=:gatePassId and c.commodity=:commodity")
	int updateGatePassAfterGateOut(@Param ("gateOutId") String gateOutId,
			 @Param("companyId") String companyId, 
			    @Param("branchId") String branchId, 
			    @Param("gatePassId") String gatePassId,
			    @Param("commodity") String commodity);


	
	
	
	
	
	

@Query("SELECT COALESCE(SUM(c.qtyTakenOut), 0) FROM CFBondGatePass c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.exBondingId = :exBondingId " +
	       "AND c.gatePassId = :gatePassId " +
	       "AND c.commodity = :commodity ")
BigDecimal getSumOfQtyTakenOutForCommodity(@Param("companyId") String companyId,
	                            @Param("branchId") String branchId,
	                            @Param("exBondingId") String exBondingId,
	                            @Param("gatePassId") String gatePassId,
	                            @Param("commodity") String commodity);




//@Query("SELECT COALESCE(SUM(c.qtyTakenOut), 0) FROM CFBondGatePass c " +
//	       "WHERE c.companyId = :companyId " +
//	       "AND c.branchId = :branchId " +
//	       "AND c.status = 'A' +
//	       "AND c.gatePassId = :gatePassId ")
//BigDecimal getSumOfQtyTakenOut(@Param("companyId") String companyId,
//	                            @Param("branchId") String branchId,
//	                            @Param("gatePassId") String gatePassId);

@Query("SELECT COALESCE(SUM(c.qtyTakenOut), 0) FROM CFBondGatePass c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.status != 'D' " +
	       "AND c.gatePassId = :gatePassId")
	BigDecimal getSumOfQtyTakenOut(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("gatePassId") String gatePassId);


@Modifying
@Transactional
@Query("UPDATE CFBondGatePass c SET c.yardQtyTakenOut = :yardQtyTakenOut, c.yardAreaReleased = :yardAreaReleased " +
       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.gatePassId=:gatePassId and c.exBondingId = :exBondingId " +
       "AND c.commodity = :commodity AND c.yardLocation = :yardLocation AND c.yardBlock = :yardBlock AND c.blockCellNo = :blockCellNo")
int updateBondGatePassAtterOutExBondGrid(
    @Param("yardQtyTakenOut") BigDecimal yardQtyTakenOut,
    @Param("yardAreaReleased") BigDecimal yardAreaReleased,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("gatePassId") String gatePassId,
    @Param("exBondingId") String exBondingId,
    @Param("commodity") String commodity,
    @Param("yardLocation") String yardLocation,
    @Param("yardBlock") String yardBlock,
    @Param("blockCellNo") String blockCellNo
);


@Query("SELECT c FROM CFBondGatePass c WHERE c.companyId = :cid AND c.branchId = :bid AND c.gatePassId=:gatePassId and c.exBondingId = :exBondingId " +
	       "AND c.commodity = :commodity AND c.status != 'D' AND c.yardLocation = :yardLocation " +
	       "AND c.yardBlock = :yardBlock AND c.blockCellNo = :blockCellNo")
CFBondGatePass toEditData(
	    @Param("cid") String cid,
	    @Param("bid") String bid,
	    @Param("gatePassId") String gatePassId,
	    @Param("exBondingId") String exBondingId,
	    @Param("commodity") String commodity,
	    @Param("yardLocation") String yardLocation,
	    @Param("yardBlock") String yardBlock,
	    @Param("blockCellNo") String blockCellNo
	);



//@Query("SELECT c FROM CFBondGatePass c " +
//	       "WHERE c.companyId = :companyId " +
//	       "AND c.branchId = :branchId " +
//	       "AND c.gatePassId = :gatePassId " +
//	       "AND c.inBondingId = :inBondingId " +
//	       "AND c.exBondingId = :exBondingId " +
//	       "AND c.status != 'D'")
//	List<CFBondGatePass> updateAfterApprove(@Param("companyId") String companyId, 
//	                                        @Param("branchId") String branchId, 
//	                                        @Param("gatePassId") String gatePassId, 
//	                                        @Param("inBondingId") String inBondingId,
//	                                        @Param("exBondingId") String exBondingId);


@Query("SELECT c FROM CFBondGatePass c " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.gatePassId = :gatePassId " +
	       "AND c.status != 'D'")
	List<CFBondGatePass> updateAfterApprove(@Param("companyId") String companyId, 
	                                        @Param("branchId") String branchId, 
	                                        @Param("gatePassId") String gatePassId);



//
//@Query("SELECT NEW com.cwms.entities.CFBondGatePass(c.companyId, c.branchId, c.gatePassId, c.srNo, " +
//        "c.exBondingId, c.inBondingId, c.gatePassDate, c.bondingNo, ex.igmLineNo, c.importerName, " +
//        "c.importerAddress1, c.importerAddress2, c.importerAddress3, pa.partyName, c.boeNo, c.boeDate, " +
//        "c.commodity, c.grossWt, c.noOfPackage, c.inBondPackages, c.transporterStatus, c.transporter, " +
//        "c.transporterName, c.vehicleNo, c.driverName, c.noOfPackages, c.qtyTakenOut, c.areaAllocated, " +
//        "c.areaReleased, c.totalGrossWt, c.vehGateInId, c.exBondBeNo, c.exBondedPackages, " +
//        "c.commodityDescription, ex.igmNo, ex.bondingDate, ex.inBondingDate, ex.inBondedGw, ex.exBondedGw, ex.exBondBeDate,exx.typeOfPackage) " +
//    "FROM CFBondGatePass c " +
//    "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
//    "LEFT OUTER JOIN CfExBondCrg ex ON c.companyId = ex.companyId AND c.branchId = ex.branchId AND c.exBondingId = ex.exBondingId AND c.exBondBeNo = ex.exBondBeNo " +
//    "LEFT OUTER JOIN CfexBondCrgDtl exx ON c.companyId = exx.companyId AND c.branchId = exx.branchId AND c.exBondingId = exx.exBondingId AND c.exBondBeNo = exx.exBondBeNo AND c.commodity=exx.cfBondDtlId " +
//    "WHERE c.companyId = :companyId " +
//    "AND c.branchId = :branchId " +
//    "AND c.gatePassId = :gatePassId " +
//    "AND c.status = 'A' " +
//    "ORDER BY c.gatePassId DESC")
//List<CFBondGatePass> getDataForBondGateOutPass(
//    @Param("companyId") String companyId, 
//    @Param("branchId") String branchId,
//    @Param("gatePassId") String gatePassId
//);








@Query("SELECT NEW com.cwms.entities.CFBondGatePass(c.companyId, c.branchId, c.gatePassId, c.srNo, " +
        "c.exBondingId, c.inBondingId, c.gatePassDate, c.bondingNo, ex.igmLineNo, c.importerName, " +
        "c.importerAddress1, c.importerAddress2, c.importerAddress3, pa.partyName, c.boeNo, c.boeDate, " +
        "c.commodity, c.grossWt, c.noOfPackage, c.inBondPackages, c.transporterStatus, c.transporter, " +
        "c.transporterName, c.vehicleNo, c.driverName, c.noOfPackages, c.qtyTakenOut, c.areaAllocated, " +
        "c.areaReleased, c.totalGrossWt, c.vehGateInId, c.exBondBeNo, c.exBondedPackages, " +
        "c.commodityDescription, ex.igmNo, ex.bondingDate, ex.inBondingDate, ex.inBondedGw, ex.exBondedGw, ex.exBondBeDate,exx.typeOfPackage) " +
    "FROM CFBondGatePass c " +
    "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
    "LEFT OUTER JOIN CfExBondCrg ex ON c.companyId = ex.companyId AND c.branchId = ex.branchId AND c.exBondingId = ex.exBondingId AND c.exBondBeNo = ex.exBondBeNo and c.inBondingId =ex.inBondingId and c.nocTransId =ex.nocTransId " +
    "LEFT OUTER JOIN CfexBondCrgDtl exx ON c.companyId = exx.companyId AND c.branchId = exx.branchId AND c.exBondingId = exx.exBondingId AND c.exBondBeNo = exx.exBondBeNo AND c.commodity=exx.cfBondDtlId and c.inBondingId =exx.inBondingId and c.nocTransId =ex.nocTransId " +
    "WHERE c.companyId = :companyId " +
    "AND c.branchId = :branchId " +
    "AND c.gatePassId = :gatePassId " +
    "AND c.status = 'A' " +
    "ORDER BY c.gatePassId DESC")
List<CFBondGatePass> getDataForBondGateOutPass(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId,
    @Param("gatePassId") String gatePassId
);


}
