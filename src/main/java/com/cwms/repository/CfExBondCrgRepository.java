package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgHDR;

import jakarta.transaction.Transactional;

@Repository
public interface CfExBondCrgRepository extends JpaRepository<CfExBondCrg, String> {
	
    // You can add custom query methods if needed
	
	@Modifying
	@Transactional
	@Query("UPDATE Cfbondinsbal c SET c.exbondArea = :exbondArea, c.exbondCargoDuty = :exbondCargoDuty, c.exbondCifValue = :exbondCifValue WHERE c.companyId = :companyId AND c.branchId = :branchId")
	int updateCfbondinsbalAfterExbond(
	    @Param("exbondArea") BigDecimal exbondArea,
	    @Param("exbondCargoDuty") BigDecimal exbondCargoDuty,
	    @Param("exbondCifValue") BigDecimal exbondCifValue,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);
	 
	
	@Modifying
	@Transactional
	@Query("UPDATE CfExBondCrg c SET c.qtyTakenOut = :qtyTakenOut  WHERE c.companyId = :companyId AND c.branchId = :branchId and c.exBondingId=:exBondingId and c.exBondBeNo=:exBondBeNo")
	int updateCfexbondAfterGatePass(
	    @Param("qtyTakenOut") BigDecimal qtyTakenOut,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("exBondingId") String exBondingId,
	    @Param("exBondBeNo") String exBondBeNo
	);
	
	 @Query("SELECT c FROM CfExBondCrg c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo AND c.exBondingId = :exBondingId")
	 CfExBondCrg findExistingCfexbondCrg(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo,
	        @Param("exBondingId") String exBondingId
	    );
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, c.exBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, " +
		       "c.exBondBeDate, c.inBondingId, c.inBondingDate, c.invoiceUptoDate, c.igmNo, p.partyName, c.exBondNo, c.exBondDate, " +
		       "c.giTransporterName, c.status) " +
		       "FROM CfExBondCrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.inBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.exBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR p.partyName LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.bondingNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.exBondingId DESC")
		List<CfExBondCrg> findCfinbondcrgByCompanyIdAndBranchIdForExbondScreen(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("partyName") String partyName
		);

	 
	 
	 
//	 
//	 @Query("SELECT c FROM CfExBondCrg c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo AND c.exBondingId = :exBondingId and c.inBondingId=:inBondingId")
//	 CfExBondCrg getDataOfExbond(
//	        @Param("companyId") String companyId,
//	        @Param("branchId") String branchId,
//	        @Param("nocTransId") String nocTransId,
//	        @Param("nocNo") String nocNo,
//	        @Param("exBondingId") String exBondingId,
//	        @Param("inBondingId") String inBondingId
//	    );
//	 
	 
	 
		
	 
	 @Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, c.exBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, " +
		       "c.exBondBeDate, c.inBondingId, c.inBondingDate, c.invoiceUptoDate, c.igmNo, c.igmLineNo, c.accSrNo, c.onAccountOf, " +
		       "c.chaSrNo, c.cha, c.shift, c.commodityDescription, c.grossWeight, c.inBondedGw, c.exBondedGw, c.remainingGw, " +
		       "c.balanceGw, c.numberOfMarks, c.uom, c.periodicBill, c.nocPackages, c.areaOccupied, c.areaReleased, c.areaBalanced, " +
		       "c.areaRemaining, c.inBondedPackages, c.exBondedPackages, c.remainingPackages, c.balancedQty, c.balancedPackages, " +
		       "c.qtyTakenOut, c.spaceAllocated, c.cifValue, c.inBondedCif, c.exBondedCif, c.remainingCif, c.balanceCif, " +
		       "c.inBondedCargoDuty, c.exBondedCargoDuty, c.remainingCargoDuty, c.balanceCargoDuty, c.inBondedInsurance, " +
		       "c.exBondedInsurance, c.remainingInsurance, c.balanceInsurance, c.cifQty, c.exBondNo, c.exBondDate, " +
		       "c.noOf20Ft, c.noOf40Ft, c.comments, c.giTransporterStatus, c.giTransporter, c.giTransporterName, c.giVehicleNo, " +
		       "c.giDriverName, c.gateOutId, c.gateOutDate, c.gateOutTransporter, c.gateOutVehicleNo, c.gateOutDriverName, " +
		       "c.status, c.createdBy, c.createdDate, c.editedBy, c.approvedBy, c.approvedDate,p.partyName,pp.partyName,c.gateInType,c.spaceType ) " +
		       "FROM CfExBondCrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "LEFT OUTER JOIN Party pp ON c.companyId = pp.companyId AND c.branchId = pp.branchId AND c.giTransporterName = pp.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND (:nocTransId IS NULL OR c.nocTransId = :nocTransId) " +
		       "AND (:nocNo IS NULL OR c.nocNo = :nocNo) " +
		       "AND (:exBondingId IS NULL OR c.exBondingId = :exBondingId) " +
		       "AND (:inBondingId IS NULL OR c.inBondingId = :inBondingId) " +
		       "AND c.status != 'D' " +
		       "ORDER BY c.exBondingId DESC")
	CfExBondCrg  getDataOfExbond(
		      @Param("companyId") String companyId,
		      @Param("branchId") String branchId,
		      @Param("nocTransId") String nocTransId,
		      @Param("nocNo") String nocNo,
		      @Param("exBondingId") String exBondingId,
		      @Param("inBondingId") String inBondingId
		);


	 
	 
	 
	 
	 @Query(value = "select c.giTransporterName, p.partyName, pa.address1, pa.address2, pa.address3  " +
             "from CfExBondCrg c " +
             "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.giTransporterName = p.partyId " +
             "LEFT OUTER JOIN PartyAddress pa on c.companyId = pa.companyId and c.branchId = pa.branchId and c.giTransporterName = pa.partyId " +
             "where c.companyId = :cid and c.branchId = :bid " +
            " and c.cha = :cha " +
             "and c.status != 'D' ")
//			 "and (:val is null OR :val = '' OR p.partyName LIKE CONCAT(:val, '%'))")
List<Object[]> getDataOfImporterOfCha(@Param("cid") String cid, @Param("bid") String bid, @Param("cha") String cha);
	 

	 
	 @Query(value = "select DISTINCT c.cha, p.partyName " +
             "from CfExBondCrg c " +
             "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.cha = p.partyId " +
             "where c.companyId = :cid and c.branchId = :bid " +
             "and c.status != 'D' " +
             "and (:val is null OR :val = '' OR p.partyName LIKE CONCAT(:val, '%'))")
List<Object[]> getData1(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);





//
//@Query(value = "select DISTINCT c.giTransporterName, p.partyName " +
//        "from CfExBondCrg c " +
//        "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.giTransporterName = p.partyId " +
//        "where c.companyId = :cid and c.branchId = :bid and c.cha = :cha " +
//        "and c.status != 'D'")
//List<Object[]> getDataOfImporterOfCha(@Param("cid") String cid, @Param("bid") String bid, @Param("cha") String cha);





















@Query(value = "select pa.srNo, pa.address1, pa.address2, pa.address3 " +
        "from PartyAddress pa " +
        "where pa.companyId = :cid and pa.branchId = :bid and pa.partyId = :partyId")
List<Object[]> getAddressOfImporterOfCha(@Param("cid") String cid, @Param("bid") String bid, @Param("partyId") String partyId);


@Query(value = "select DISTINCT c.giTransporterName, p.partyName " +
        "from CfExBondCrg c " +
        "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.giTransporterName = p.partyId " +
        "where c.companyId = :cid and c.branchId = :bid and c.cha = :cha " +
        "and c.status != 'D'")
List<Object[]> getAllExbondBeNofromExbondCrgDtl(@Param("cid") String cid, @Param("bid") String bid, @Param("cha") String cha);
















@Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, c.exBondingDate, " +
	       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, " +
	       "c.exBondBeDate, c.inBondingId, c.inBondingDate, c.igmNo, c.igmLineNo, p.partyName, c.commodityDescription, " +
	       "c.grossWeight, c.inBondedGw, c.exBondedGw, c.uom, c.nocPackages, c.areaOccupied, c.areaReleased, c.inBondedPackages, " +
	       "c.exBondedPackages, c.cifValue, c.inBondedCif, c.exBondedCif, c.inBondedCargoDuty, c.exBondedCargoDuty, " +
	       "c.remainingCargoDuty, c.balanceCargoDuty, c.inBondedInsurance, pp.partyName, c.gateInType, c.spaceType, " +
	       "cf.exBondedPackages, cf.exBondedCIF, cf.exBondedCargoDuty, cf.exBondedGW, " +
	       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondGrossWt, cf.inbondInsuranceValue, cf.inbondCifValue, cf.inbondCargoDuty,pa.address1,pa.address2,pa.address3,pp.partyName) " +
	       "FROM CfExBondCrg c " +
	       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
	       "LEFT OUTER JOIN Party pp ON c.companyId = pp.companyId AND c.branchId = pp.branchId AND c.giTransporterName = pp.partyId " +
	       "LEFT OUTER JOIN PartyAddress pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.giTransporterName = pa.partyId " +
	       "LEFT OUTER JOIN CfexBondCrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.exBondingId = cf.exBondingId and c.inBondingId=cf.inBondingId and c.nocTransId=cf.nocTransId and c.boeNo =cf.boeNo " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.exBondingId = :exBondingId " +
	       "AND c.status != 'D' ")
	List<CfExBondCrg> getDataForCustomsBondExbondReport(
	      @Param("companyId") String companyId,
	      @Param("branchId") String branchId,
	      @Param("exBondingId") String exBondingId
	);


@Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, c.exBondingDate, " +
	       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, " +
	       "c.exBondBeDate, c.inBondingId, c.inBondingDate, c.igmNo, c.igmLineNo, p.partyName, c.commodityDescription, " +
	       "c.grossWeight, c.inBondedGw, c.exBondedGw, c.uom, c.nocPackages, c.areaOccupied, c.areaReleased, c.inBondedPackages, " +
	       "c.exBondedPackages, c.cifValue, c.inBondedCif, c.exBondedCif, c.inBondedCargoDuty, c.exBondedCargoDuty, " +
	       "c.remainingCargoDuty, c.balanceCargoDuty, c.inBondedInsurance, pp.partyName, c.gateInType, c.spaceType, " +
	       "cf.exBondedPackages, cf.exBondedCIF, cf.exBondedCargoDuty, cf.exBondedGW, " +
	       "cf.typeOfPackage, cf.inBondedPackages, cf.inbondGrossWt, cf.inbondInsuranceValue, cf.inbondCifValue, cf.inbondCargoDuty,pp.partyName) " +
	       "FROM CfExBondCrg c " +
	       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
	       "LEFT OUTER JOIN Party pp ON c.companyId = pp.companyId AND c.branchId = pp.branchId AND c.giTransporterName = pp.partyId " +
//	       "LEFT OUTER JOIN PartyAddress pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.giTransporterName = pa.partyId " +
	       "LEFT OUTER JOIN CfexBondCrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.exBondingId = cf.exBondingId and c.inBondingId=cf.inBondingId and c.nocTransId=cf.nocTransId " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.exBondingId = :exBondingId " +
	       "AND c.status != 'D' ")
	List<CfExBondCrg> getDataForCustomsBondExbond(
	      @Param("companyId") String companyId,
	      @Param("branchId") String branchId,
	      @Param("exBondingId") String exBondingId
	);



@Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, " +
	       "c.exBondingDate, c.profitcentreId, c.nocTransId, cf.nocNo, cff.nocDate, " +  // Added nocNo from c
	       "c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, c.exBondBeDate, " +  // Updated exBondBeNo and exBondBeDate
	       "c.inBondingId, c.inBondingDate, c.igmNo, c.igmLineNo, c.onAccountOf, " + 
	       "cf.commodityDescription, c.numberOfMarks, c.exBondNo, c.exBondDate, " +
	       "pa.partyName, cff.bondValidityDate, " +
	       "cf.exBondedPackages, cf.exBondedCIF, cf.exBondedCargoDuty, " +
	       "cf.exBondedGW, cf.typeOfPackage,p.partyName) " +
	       "FROM CfExBondCrg c " +
	       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
	       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.giTransporterName = pa.partyId " +
	       "LEFT OUTER JOIN CfexBondCrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.exBondingId = cf.exBondingId " +
	       "LEFT OUTER JOIN Cfinbondcrg cff ON c.companyId = cff.companyId AND c.branchId = cff.branchId AND c.inBondingId = cff.inBondingId " +
	       "AND cf.status = 'A' " +
	       "WHERE c.companyId = :companyId " +
	       "AND c.branchId = :branchId " +
	       "AND c.exBondingDate BETWEEN :startDate AND :endDate " + // Keep the date range check
	       "AND (c.boeNo = :boeNo OR :boeNo IS NULL OR :boeNo = '') " + // Allow null or empty boeNo
	       "AND (c.exBondBeNo = :exBondBeNo OR :exBondBeNo IS NULL OR :exBondBeNo = '') " + // Allow null or empty exBondBeNo
	       "AND c.status = 'A' " +
	       "ORDER BY c.exBondingDate, c.boeNo")
	List<CfExBondCrg> getDataForBondDeliveryReport(@Param("companyId") String companyId, 
	                                                 @Param("branchId") String branchId, 
	                                                 @Param("startDate") Date startDate,
	                                                 @Param("endDate") Date endDate,
	                                                 @Param("boeNo") String boeNo,
	                                                 @Param("exBondBeNo") String exBondBeNo);



@Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, " +
        "c.exBondingDate, c.profitcentreId, c.nocTransId, cf.nocNo, cff.nocDate, " +
        "c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, c.exBondBeDate, " +
        "c.inBondingId, c.inBondingDate, c.igmNo, c.igmLineNo, c.onAccountOf, " + 
        "cf.commodityDescription, c.numberOfMarks, c.exBondNo, c.exBondDate, " +
        "pa.partyName, cff.bondValidityDate, " +
        "cf.exBondedPackages, cf.exBondedCIF, cf.exBondedCargoDuty, " +
        "cf.exBondedGW, cf.typeOfPackage, p.partyName) " +
        "FROM CfExBondCrg c " +
        "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
        "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.giTransporterName = pa.partyId " +
        "LEFT OUTER JOIN CfexBondCrgDtl cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.exBondingId = cf.exBondingId " +
        "LEFT OUTER JOIN Cfinbondcrg cff ON c.companyId = cff.companyId AND c.branchId = cff.branchId AND c.inBondingId = cff.inBondingId " +
        "WHERE c.companyId = :companyId " +
        "AND c.branchId = :branchId " +
        "AND (c.boeNo = :boeNo OR :boeNo IS NULL OR :boeNo = '') " +
        "AND (c.exBondBeNo = :exBondBeNo OR :exBondBeNo IS NULL OR :exBondBeNo = '') " +
        "AND c.status = 'A' " +
        "ORDER BY c.exBondingDate, c.boeNo")
List<CfExBondCrg> getDataForBondDeliveryReportWithoutDates(@Param("companyId") String companyId,
                                                           @Param("branchId") String branchId,
                                                           @Param("boeNo") String boeNo,
                                                           @Param("exBondBeNo") String exBondBeNo);



}
