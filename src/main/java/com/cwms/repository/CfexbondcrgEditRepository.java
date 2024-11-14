package com.cwms.repository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Branch;
import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.CfexBondCrgDtl;
import com.cwms.entities.CfexbondcrgEdit;
import com.cwms.entities.CfinbondCommdtlEdit;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgDtl;

import jakarta.transaction.Transactional;

public interface CfexbondcrgEditRepository extends JpaRepository<CfexbondcrgEdit, String> {
    // Add custom queries if needed
	
	
	
	@Query("SELECT NEW com.cwms.entities.Cfinbondcrg(c.companyId, c.branchId, c.finYear, c.inBondingHdrId, c.inBondingId, c.inBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocTransDate, c.igmNo, c.igmDate, c.igmLineNo, c.nocNo, c.nocDate, " +
		       "c.nocValidityDate, c.nocFromDate, c.boeNo, c.boeDate, c.bondingNo, c.bondingDate, c.bondValidityDate, " +
		       "c.chaSrNo, c.cha, c.chaCode, c.importerId, c.importerName, c.importerAddress1, c.importerAddress2, c.importerAddress3, " +
		       "c.grossWeight, c.nocPackages, c.areaAllocated, c.areaOccupied, c.gateInPackages, c.inBondedPackages, " +
		       "c.section49, c.cifValue, c.cargoDuty, c.insuranceValue, c.inbondGrossWt, c.inbondInsuranceValue , " +
		       "pa.partyName,c.exBondedPackages ) " +
		   "FROM Cfinbondcrg c " +
		   "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		   "WHERE c.companyId = :companyId " +
       	   "AND c.branchId = :branchId " +
//"AND (c.exBondedPackages IS NULL OR c.exBondedPackages = 0) " +  // This line filters for exBondedPackages that are null or zero
		   "AND c.status = 'A' " +
		   "AND (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE CONCAT(:partyName, '%')) " +
		   "ORDER BY c.boeNo DESC")
		List<Cfinbondcrg> getDataUsingBoeNoForInBondAuditTrailScreen(@Param("companyId") String companyId, 
		                                                                   @Param("branchId") String branchId,
		                                                                   @Param("partyName") String partyName);

	
    //"GROUP BY c.nocTransId, c.nocNo, c.cfBondDtlId"
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.CfinbondcrgDtl( " +
		       "c.companyId, c.branchId, c.inBondingDtlId, c.inBondingId, c.nocTransId, c.nocNo, c.cfBondDtlId, " +
		       "c.boeNo, c.inBondingDate, c.nocTransDate, c.nocDate, c.bondingNo, c.bondingDate, " +
		       "c.typeOfPackage, c.commodityDescription, c.exBondedPackages, c.inBondedPackages, c.inbondGrossWt, " +
		       "c.inbondInsuranceValue, c.inbondCifValue, c.inbondCargoDuty, c.shortagePackages, c.damagedQty, " +
		       "c.breakage, c.yardLocationId, c.blockId, c.cellNoRow, c.areaOccupied, c.yardPackages, " +
		       "c.cellAreaAllocated, c.cellArea,cd.gateInPackages,c.cifValue,c.cargoDuty) " +
		       "FROM CfinbondcrgDtl c " +
		       "LEFT OUTER JOIN CfBondNocDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo AND c.cfBondDtlId = cd.cfBondDtlId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.status = 'A' ")
		List<CfinbondcrgDtl> getDataOfCfInBodnCrgDtl(@Param("companyId") String companyId, 
		                                     @Param("branchId") String branchId, 
		                                     @Param("inBondingId") String inBondingId, 
		                                     @Param("nocTransId") String nocTransId);


	
	@Query("SELECT b FROM CfinbondCommdtlEdit b WHERE b.companyId = :cid AND b.branchId = :bid AND b.commonBondingId = :inbondingId AND b.nocTransId = :nocTransId")
	public List<CfinbondCommdtlEdit> findByCompanyIdAndBranchIdAndCommonBondingIdAndNocTransId(
	    @Param("cid") String cid, 
	    @Param("bid") String bid, 
	    @Param("inbondingId") String inbondingId, 
	    @Param("nocTransId") String nocTransId
	);
	
	
	
	@Query("SELECT b FROM CfinbondCommdtlEdit b WHERE b.companyId = :cid AND b.branchId = :bid AND b.commonBondingId = :exBondingId AND b.nocTransId = :nocTransId")
	public List<CfinbondCommdtlEdit> getForExBondDtl(
	    @Param("cid") String cid, 
	    @Param("bid") String bid, 
	    @Param("exBondingId") String exBondingId, 
	    @Param("nocTransId") String nocTransId
	);


	@Query("SELECT COALESCE(SUM(c.newBondPackages), 0) FROM CfinbondCommdtlEdit c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.commonBondingId = :commonBondingId " +
		       "AND c.commodityId = :commodityId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal getSumOfInBondPackagesForCommodity(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("commonBondingId") String commonBondingId,
		                            @Param("commodityId") String commodityId,
		                            @Param("nocTransId") String nocTransId);
	
	@Query("SELECT COALESCE(SUM(c.newBondPackages), 0) FROM CfinbondCommdtlEdit c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.commonBondingId = :commonBondingId " +
		       "AND c.nocTransId = :nocTransId")
	BigDecimal getSumOfInBondPackages(@Param("companyId") String companyId,
		                            @Param("branchId") String branchId,
		                            @Param("commonBondingId") String commonBondingId,
		                            @Param("nocTransId") String nocTransId);
	
	
	@Transactional
	 @Modifying
	 @Query("UPDATE CfexbondcrgEdit c SET c.status =:status WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.nocNo = :nocNo AND c.nocTransId = :nocTransId")
	 int updateAfterApprove(
			 @Param("status") String status,
			 @Param("companyId") String companyId,
	         @Param("branchId") String branchId,
	         @Param("inBondingId") String inBondingId,
	         @Param("nocNo") String nocNo,
	         @Param("nocTransId") String nocTransId);

	@Query("SELECT NEW com.cwms.entities.CfexbondcrgEdit(c.SrNo, c.inBondingId, c.exBondingId, c.branchId, c.companyId, " +
		       "c.nocTransId, c.approvedBy, c.approvedDate, c.boeDate, c.boeDateOld, c.boeNo, " +
		       "c.boeNoOld, c.bondValidityDate, c.bondValidityDateOld, c.bondingDate, c.bondingDateOld, " +
		       "c.bondingNo, c.bondingNoOld, c.cha, c.chaOld, c.commodityDescription, " +
		       "c.commodityDescriptionOld, c.createdBy, c.createdDate, c.editedBy, c.editedDate, " +
		       "c.igmDate, c.igmLineNo, c.igmNo, c.importerId, c.importerIdOld, c.importerName, " +
		       "c.importerNameOld, c.inBondedCif, c.inBondedCifOld, c.inBondedCargoDuty, " +
		       "c.inBondedCargoDutyOld, c.inBondedGw, c.inBondedGwOld, c.inBondedInsurance, c.inBondedInsuranceOld, " +
		       "c.inBondedPackages, c.inBondedPackagesOld, c.inBondingDate, c.inBondingDateOld, c.nocNo, " +
		       "c.nocValidityDate, c.nocValidityDateOld, c.section49, c.section49Old, c.section60, " +
		       "c.section60Old, c.status, c.tranType, c.nocTransDate, c.nocDate, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.newChaCode,p.partyName) " +
		       "FROM CfexbondcrgEdit c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.chaOld = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.tranType = 'INBOND' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.importerNameOld LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.inBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.inBondingId DESC")
		List<CfexbondcrgEdit> findCfinbondcrgByCompanyIdAndBranchIdForInbondScreen(@Param("companyId") String companyId, 
		                                                                           @Param("branchId") String branchId,
		                                                                           @Param("partyName") String partyName);
	
	
	
	@Query("SELECT NEW com.cwms.entities.CfexbondcrgEdit(c.SrNo, c.inBondingId, c.exBondingId, c.branchId, c.companyId, " +
		       "c.nocTransId, c.approvedBy, c.approvedDate, c.boeDate, c.boeDateOld, c.boeNo, " +
		       "c.boeNoOld, c.bondValidityDate, c.bondValidityDateOld, c.bondingDate, c.bondingDateOld, " +
		       "c.bondingNo, c.bondingNoOld, c.cha, c.chaOld, c.commodityDescription, " +
		       "c.commodityDescriptionOld, c.createdBy, c.createdDate, c.editedBy, c.editedDate, " +
		       "c.igmDate, c.igmLineNo, c.igmNo, c.importerId, c.importerIdOld, c.importerName, " +
		       "c.importerNameOld, c.inBondedCif, c.inBondedCifOld, c.inBondedCargoDuty, " +
		       "c.inBondedCargoDutyOld, c.inBondedGw, c.inBondedGwOld, c.inBondedInsurance, c.inBondedInsuranceOld, " +
		       "c.inBondedPackages, c.inBondedPackagesOld, c.inBondingDate, c.inBondingDateOld, c.nocNo, " +
		       "c.nocValidityDate, c.nocValidityDateOld, c.section49, c.section49Old, c.section60, " +
		       "c.section60Old, c.status, c.tranType, c.nocTransDate, c.nocDate, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.newChaCode,p.partyName) " +
		       "FROM CfexbondcrgEdit c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.chaOld = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.tranType = 'INBOND' " +
		 
"AND c.SrNo = :SrNo " +
"AND c.nocTransId = :nocTransId " +
"AND c.inBondingId = :inBondingId " +
"AND c.nocNo = :nocNo " +
		       "ORDER BY c.inBondingId DESC")
		CfexbondcrgEdit getSavedData(@Param("companyId") String companyId, 
		                                                                           @Param("branchId") String branchId,
		                                                                           @Param("SrNo") Long SrNo,
		                                                                           @Param("nocTransId") String nocTransId,
		                                                                           @Param("inBondingId") String inBondingId,
		                                                                           @Param("nocNo") String nocNo);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		       "c.status, c.createdBy, c.createdDate, c.editedBy, c.approvedBy, c.approvedDate,p.partyName,pp.partyName,c.gateInType,c.spaceType,"
		       + "c.exBondType ,c.sbNo,c.sbDate,c.sbValue,c.sbDuty,c.sbQty,c.trnsferBondNo,c.trnsferBondDate,c.section49 ) " +
		       "FROM CfExBondCrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "LEFT OUTER JOIN Party pp ON c.companyId = pp.companyId AND c.branchId = pp.branchId AND c.giTransporterName = pp.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
//		       "AND (c.qtyTakenOut IS NULL OR c.qtyTakenOut = 0) " +  // This line filters for exBondedPackages that are null or zero
		       "AND c.status = 'A' " +
			   "AND (:partyName IS NULL OR :partyName = '' OR c.exBondBeNo LIKE CONCAT(:partyName, '%')) " +
		       "ORDER BY c.exBondingId DESC")
	 List<CfExBondCrg>  getDataForExBondAuditTrailScreen(
		      @Param("companyId") String companyId,
		      @Param("branchId") String branchId,
		      @Param("partyName") String partyName
		);

	 
	 
	 
	 
	 
	 
	 
	 @Query("SELECT c FROM CfexBondCrgDtl c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.inBondingId = :inBondingId " +
		       "AND c.exBondBeNo = :exBondBeNo " +
		       "AND c.exBondingId = :exBondingId " +
		       "AND c.status != 'D'")
		List<CfexBondCrgDtl> getDataForExBondAuditTrailToUpdate(@Param("companyId") String companyId, 
		                                        @Param("branchId") String branchId, 
		                                        @Param("nocTransId") String nocTransId, 
		                                        @Param("inBondingId") String inBondingId, 
		                                        @Param("exBondBeNo") String exBondBeNo,
		                                        @Param("exBondingId") String exBondingId);


	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.CfexbondcrgEdit(c.SrNo, c.inBondingId, c.exBondingId, c.branchId, c.companyId, " +
		       "c.nocTransId, c.approvedBy, c.approvedDate, c.balanceCif, c.balanceCifOld, " +
		       "c.balanceCargoDuty, c.balanceCargoDutyOld, c.balanceGw, c.balanceGwOld, " +
		       "c.balanceInsurance, c.balanceInsuranceOld, c.balancedPackagesNew, c.balancedPackagesOld, " +
		       "c.balancedQty, c.balancedQtyNew, c.boeDate, c.boeDateOld, c.boeNo, c.boeNoOld, " +
		       "c.bondValidityDate, c.bondValidityDateOld, c.bondingDate, c.bondingDateOld, " +
		       "c.bondingNo, c.bondingNoOld, c.cha, c.chaOld, c.commodityDescription, " +
		       "c.commodityDescriptionOld, c.createdBy, c.createdDate, c.editedBy, c.editedDate, " +
		       "c.exBondBeDate, c.exBondBeNo, c.exBondBeNoOld, c.exBondedCif, c.exBondedCifOld, " +
		       "c.exBondedCargoDuty, c.exBondedCargoDutyOld, c.exBondedGw, c.exBondedGwOld, " +
		       "c.exBondedInsurance, c.exBondedInsuranceOld, c.exBondedPackages, c.exBondedPackagesOld, " +
		       "c.exBondingDate, c.exBondingDateOld, c.igmDate, c.igmLineNo, c.igmNo, c.importerId, " +
		       "c.importerIdOld, c.importerName, c.importerNameOld, c.inBondedCif, c.inBondedCifOld, " +
		       "c.inBondedCargoDuty, c.inBondedCargoDutyOld, c.inBondedGw, c.inBondedGwOld, " +
		       "c.inBondedInsurance, c.inBondedInsuranceOld, c.inBondedPackages, c.inBondedPackagesOld, " +
		       "c.inBondingDate, c.inBondingDateOld, c.nocNo, c.nocValidityDate, c.nocValidityDateOld, " +
		       "c.remainingCif, c.remainingCifOld, c.remainingCargoDuty, c.remainingCargoDutyOld, " +
		       "c.remainingGw, c.remainingGwOld, c.remainingInsurance, c.remainingInsuranceOld, " +
		       "c.remainingPackages, c.remainingPackagesOld, c.sbDate, c.sbDateOld, c.sbDutyNew, " +
		       "c.sbDutyOld, c.sbNo, c.sbNoOld, c.sbQtyNew, c.sbQtyOld, c.sbUomNew, c.sbUomOld, " +
		       "c.sbValueNew, c.sbValueOld, c.section49, c.section49Old, c.section60, c.section60Old, " +
		       "c.status, c.tranType, c.nocTransDate, c.nocDate, c.exBondBeDateOld, c.transferBondDate, " +
		       "c.transferBondDateOld, c.transferBondNo, c.transferBondNoOld, c.exBondType) " +
		       "FROM CfexbondcrgEdit c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.chaOld = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.tranType = 'EXBOND' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.exBondBeNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.exBondBeNoOld LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.exBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.exBondingId DESC")
		List<CfexbondcrgEdit> getDataAfterSaveForExBondAuditTrailScreen(@Param("companyId") String companyId, 
		                                                                           @Param("branchId") String branchId,
		                                                                           @Param("partyName") String partyName);
	 
	 
	 @Query("SELECT NEW com.cwms.entities.CfexbondcrgEdit(c.SrNo, c.inBondingId, c.exBondingId, c.branchId, c.companyId, " +
		       "c.nocTransId, c.approvedBy, c.approvedDate, c.balanceCif, c.balanceCifOld, " +
		       "c.balanceCargoDuty, c.balanceCargoDutyOld, c.balanceGw, c.balanceGwOld, " +
		       "c.balanceInsurance, c.balanceInsuranceOld, c.balancedPackagesNew, c.balancedPackagesOld, " +
		       "c.balancedQty, c.balancedQtyNew, c.boeDate, c.boeDateOld, c.boeNo, c.boeNoOld, " +
		       "c.bondValidityDate, c.bondValidityDateOld, c.bondingDate, c.bondingDateOld, " +
		       "c.bondingNo, c.bondingNoOld, c.cha, p.partyName, c.commodityDescription, " +
		       "c.commodityDescriptionOld, c.createdBy, c.createdDate, c.editedBy, c.editedDate, " +
		       "c.exBondBeDate, c.exBondBeNo, c.exBondBeNoOld, c.exBondedCif, c.exBondedCifOld, " +
		       "c.exBondedCargoDuty, c.exBondedCargoDutyOld, c.exBondedGw, c.exBondedGwOld, " +
		       "c.exBondedInsurance, c.exBondedInsuranceOld, c.exBondedPackages, c.exBondedPackagesOld, " +
		       "c.exBondingDate, c.exBondingDateOld, c.igmDate, c.igmLineNo, c.igmNo, c.importerId, " +
		       "c.importerIdOld, c.importerName, c.importerNameOld, c.inBondedCif, c.inBondedCifOld, " +
		       "c.inBondedCargoDuty, c.inBondedCargoDutyOld, c.inBondedGw, c.inBondedGwOld, " +
		       "c.inBondedInsurance, c.inBondedInsuranceOld, c.inBondedPackages, c.inBondedPackagesOld, " +
		       "c.inBondingDate, c.inBondingDateOld, c.nocNo, c.nocValidityDate, c.nocValidityDateOld, " +
		       "c.remainingCif, c.remainingCifOld, c.remainingCargoDuty, c.remainingCargoDutyOld, " +
		       "c.remainingGw, c.remainingGwOld, c.remainingInsurance, c.remainingInsuranceOld, " +
		       "c.remainingPackages, c.remainingPackagesOld, c.sbDate, c.sbDateOld, c.sbDutyNew, " +
		       "c.sbDutyOld, c.sbNo, c.sbNoOld, c.sbQtyNew, c.sbQtyOld, c.sbUomNew, c.sbUomOld, " +
		       "c.sbValueNew, c.sbValueOld, c.section49, c.section49Old, c.section60, c.section60Old, " +
		       "c.status, c.tranType, c.nocTransDate, c.nocDate, c.exBondBeDateOld, c.transferBondDate, " +
		       "c.transferBondDateOld, c.transferBondNo, c.transferBondNoOld, c.exBondType) " +
		       "FROM CfexbondcrgEdit c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.chaOld = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND c.tranType = 'EXBOND' " +
				 
		"AND c.SrNo = :SrNo " +
		"AND c.nocTransId = :nocTransId " +
		"AND c.exBondingId = :exBondingId " +
		"AND c.nocNo = :nocNo " +
				       "ORDER BY c.exBondingId DESC")
		CfexbondcrgEdit getBySelectingRadioButton(@Param("companyId") String companyId, 
                @Param("branchId") String branchId,
                @Param("SrNo") Long SrNo,
                @Param("nocTransId") String nocTransId,
                @Param("exBondingId") String exBondingId,
                @Param("nocNo") String nocNo);

		@Transactional
		 @Modifying
		 @Query("UPDATE CfexbondcrgEdit c SET c.status =:status WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.inBondingId = :inBondingId AND c.exBondingId = :exBondingId AND c.nocNo = :nocNo AND c.nocTransId = :nocTransId")
		 int updateApproveAfterExBondAuditTrail(
				 @Param("status") String status,
				 @Param("companyId") String companyId,
		         @Param("branchId") String branchId,
		         @Param("inBondingId") String inBondingId,
		         @Param("exBondingId") String exBondingId,
		         @Param("nocNo") String nocNo,
		         @Param("nocTransId") String nocTransId);
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		@Query("SELECT NEW com.cwms.entities.CfexbondcrgEdit(" +
		           "c.SrNo, c.inBondingId, c.exBondingId, c.branchId, c.companyId, " +
		           "c.nocTransId, c.boeDate, c.boeDateOld, c.boeNo, c.boeNoOld, " +
		           "c.bondValidityDate, c.bondValidityDateOld, c.bondingDate, c.bondingDateOld, " +
		           "c.bondingNo, c.bondingNoOld, pp.partyName, p.partyName, c.commodityDescription, " +
		           "c.commodityDescriptionOld, c.exBondBeDate, c.exBondBeNo, c.exBondBeNoOld, " +
		           "c.exBondedCif, c.exBondedCifOld, c.exBondedCargoDuty, c.exBondedCargoDutyOld, " +
		           "c.exBondedGw, c.exBondedGwOld, c.exBondedInsurance, c.exBondedInsuranceOld, " +
		           "c.exBondedPackages, c.exBondedPackagesOld, c.exBondingDate, c.exBondingDateOld, " +
		           "c.importerId, c.importerIdOld, c.importerName, c.importerNameOld, " +
		           "c.inBondedCif, c.inBondedCifOld, c.inBondedCargoDuty, c.inBondedCargoDutyOld, " +
		           "c.inBondedGw, c.inBondedGwOld, c.inBondedInsurance, c.inBondedInsuranceOld, " +
		           "c.inBondedPackages, c.inBondedPackagesOld, c.inBondingDate, c.inBondingDateOld, " +
		           "c.nocNo, c.nocValidityDate, c.nocValidityDateOld, c.sbDate, c.sbDateOld, " +
		           "c.sbDutyNew, c.sbDutyOld, c.sbNo, c.sbNoOld, c.sbQtyNew, c.sbQtyOld, " +
		           "c.sbUomNew, c.sbUomOld, c.sbValueNew, c.sbValueOld, c.section49, c.section49Old, " +
		           "c.section60, c.section60Old, c.status, c.tranType, c.nocTransDate, " +
		           "c.nocDate, c.exBondBeDateOld, c.transferBondDate, c.transferBondDateOld, " +
		           "c.transferBondNo, c.transferBondNoOld, c.exBondType, c.commonJobId, " +
		           "c.newChaCode, p.partyName, cf.oldInsuranceValue, cf.oldTypeOfPackage, " +
		           "cf.newBondCargoDuty, cf.newBondCifValue, cf.newBondGrossWt, cf.newBondPackages, " +
		           "cf.newBreakage, cf.newCommodityDescription, cf.newDamagedQty, cf.newInsuranceValue, " +
		           "cf.newShortagePackages, cf.newTypeOfPackage, cf.oldBondCargoDuty, cf.oldBondCifValue, " +
		           "cf.oldBondGrossWt, cf.oldBondPackages, cf.oldBreakage, cf.oldCommodityDescription, " +
		           "cf.oldDamagedQty, cf.oldShortagePackages, cf.type) " +
		       "FROM CfexbondcrgEdit c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.chaOld = p.partyId " +
		       "LEFT OUTER JOIN Party pp ON c.companyId = pp.companyId AND c.branchId = pp.branchId AND c.cha = pp.partyId " +
		       "LEFT OUTER JOIN CfinbondCommdtlEdit cf ON c.companyId = cf.companyId AND c.branchId = cf.branchId AND c.nocTransId = cf.nocTransId and c.SrNo=cf.SrNo " +
		       "AND cf.status = 'A' " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.createdDate BETWEEN :startDate AND :endDate " +
		       "AND (c.boeNo = :boeNo OR :boeNo IS NULL OR :boeNo = '') " + // Allow null or empty boeNo
		       "AND c.status = 'A' " +
		       "ORDER BY c.createdDate")
		List<CfexbondcrgEdit> getdataForAuditTrailReport(@Param("companyId") String companyId, 
		                                                         @Param("branchId") String branchId, 
		                                                         @Param("startDate") Date startDate,
		                                                         @Param("endDate") Date endDate,
		                                                         @Param("boeNo") String boeNo);

}
