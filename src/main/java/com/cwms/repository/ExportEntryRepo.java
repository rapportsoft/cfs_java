package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportSbEntry;
import com.cwms.entities.GateIn;

import jakarta.transaction.Transactional;

public interface ExportEntryRepo extends JpaRepository<ExportSbEntry, String> {
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ExportSbEntry e " +
	        "SET e.sbNo = :newSbNo , e.sbDate = :newSbDate,e.totalPackages = :totalPackages,e.totalGrossWeight =:totalGrossWt, e.sbType = :sbType  " +
	        "WHERE e.companyId = :companyId " +
	        "AND e.branchId = :branchId " +
	        "AND e.sbNo = :sbNo " +
	        "AND e.sbTransId = :sbTransId " +	       
	        "AND e.status <> 'D'")
	int updateChangeSbNo(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("newSbNo") String newSbNo,
	        @Param("newSbDate") Date newSbDate,
	        @Param("totalPackages") BigDecimal totalPackages,
	        @Param("totalGrossWt") BigDecimal totalGrossWt,
	        @Param("sbType") String sbType	        
	);
	

	
	@Modifying
	@Transactional
	@Query("UPDATE ExportSbEntry e " +
	       "SET e.sbType = :sbType, " +
	       "    e.sbTransDate = :sbTransDate, " +
	       "    e.sbDate = :sbDate, " +
	       "    e.drawBackValue = :drawBackValue, " +
	       "    e.cargoType = :cargoType, " +
	       "    e.cargoLoc = :cargoLoc, " +
	       "    e.exporterId = :exporterId, " +
	       "    e.exporterName = :exporterName, " +
	       "    e.exporterAddress1 = :exporterAddress1, " +
	       "    e.exporterAddress2 = :exporterAddress2, " +
	       "    e.exporterAddress3 = :exporterAddress3, " +
	       "    e.cha = :cha, " +	      
	       "    e.consigneeName = :consigneeName, " +
	       "    e.consigneeAddress1 = :consigneeAddress1, " +
	       "    e.consigneeAddress2 = :consigneeAddress2, " +
	       "    e.consigneeAddress3 = :consigneeAddress3, " +
	       "    e.onAccountOf = :onAccountOf, " +
	       "    e.pol = :pol, " +
	       "    e.pod = :pod, " +	      
	       "    e.destinationCountry = :destinationCountry, " +
	       "    e.imoCode = :imoCode, " +
	       "    e.comments = :comments, " +
	       "    e.iecCode = :iecCode, " +      // New Field  
	       "    e.state = :state, " +         // New Field  
	       "    e.gstNo = :gstNo, " +         // New Field  
	       "    e.srNo = :srNo " +            // New Field  
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.profitcentreId = :profitCentreId " +
	       "  AND e.status <> 'D'")
	int updateSBEntry(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("profitCentreId") String profitCentreId,
	        @Param("sbTransId") String sbTransId,
	        @Param("sbNo") String sbNo,	      
	        @Param("sbType") String sbType,
	        @Param("sbTransDate") Date sbTransDate,
	        @Param("sbDate") Date sbDate,
	        @Param("drawBackValue") BigDecimal drawBackValue,
	        @Param("cargoType") String cargoType,
	        @Param("cargoLoc") String cargoLoc,
	        @Param("exporterId") String exporterId,
	        @Param("exporterName") String exporterName,
	        @Param("exporterAddress1") String exporterAddress1,
	        @Param("exporterAddress2") String exporterAddress2,
	        @Param("exporterAddress3") String exporterAddress3,
	        @Param("cha") String cha,
	        @Param("consigneeName") String consigneeName,
	        @Param("consigneeAddress1") String consigneeAddress1,
	        @Param("consigneeAddress2") String consigneeAddress2,
	        @Param("consigneeAddress3") String consigneeAddress3,
	        @Param("onAccountOf") String onAccountOf,
	        @Param("pol") String pol,
	        @Param("pod") String pod,
	        @Param("destinationCountry") String destinationCountry,
	        @Param("imoCode") String imoCode,
	        @Param("comments") String comments,
	        @Param("iecCode") String iecCode,   // New Field
	        @Param("state") String state,       // New Field
	        @Param("gstNo") String gstNo,       // New Field
	        @Param("srNo") String srNo 
	);


	
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.ExportSbEntry(e.sbTransId, e.sbNo, "
	        + "e.totalPackages, e.totalGrossWeight, e.sbType, e.sbDate) "
	        + "FROM ExportSbEntry e "
	    	+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.profitcentreId = :profitcentreId " 
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.status <> 'D'")
	ExportSbEntry getExportSbEntryBySbTransIduUpdate(@Param("companyId") String companyId, @Param("branchId") String branchId,
            										 @Param("profitcentreId") String profitcentreId, @Param("sbTransId") String sbTransId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ExportSbEntry e " +
	       "SET e.ssrTransId=:id " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.status = 'A'")
	int updateSSRData(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("id") String id);

	@Modifying
	@Transactional
	@Query("UPDATE ExportSbEntry e " +
	       "SET e.gateInPackages = COALESCE(e.gateInPackages, 0) - :gateInPackages " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.status <> 'D'")
	int updateFromSbNoTransfer(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("gateInPackages") BigDecimal gateInPackages);

	
	
	@Modifying
	@Transactional
	@Query("UPDATE ExportSbEntry e " +
	       "SET e.gateInPackages = COALESCE(e.gateInPackages, 0) + :gateInPackages " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.status <> 'D'")
	int updateToSbNoTransfer(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("gateInPackages") BigDecimal gateInPackages);

	
	
	@Query("SELECT NEW com.cwms.entities.ExportSbEntry(e.pod, e.pol)"
	        + "FROM ExportSbEntry e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.sbNo = :sbNo " 
	        + "AND e.sbTransId = :sbTransId "	      
	        + "AND e.status <> 'D'")
	ExportSbEntry getDataForVesselEntry(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                    @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId);

	
	@Query("SELECT NEW com.cwms.entities.ExportSbEntry(e.sbTransId, e.sbNo, "
	        + "e.profitcentreId, e.outOfCharge, e.outOfChargeDate, e.hSbTransId, e.leoDate) "
	        + "FROM ExportSbEntry e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.sbNo = :sbNo " 
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.hSbTransId = :hSbTransId " 
	        + "AND e.profitcentreId = :profitcentreId "
	        + "AND e.status <> 'D'")
	ExportSbEntry getDataForOutOfCharge(@Param("companyId") String companyId, @Param("branchId") String branchId,
								  @Param("hSbTransId") String hSbTransId, @Param("profitcentreId") String profitcentreId,
	                              @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId);

	

	@Transactional
	@Modifying
	@Query(value = "UPDATE ExportSbEntry e " +
	        "SET e.outOfCharge = :outOfCharge , e.outOfChargeDate = :outOfChargeDate,e.leoDate =:leoDate  " +
	        "WHERE e.companyId = :companyId " +
	        "AND e.branchId = :branchId " +
	        "AND e.sbNo = :sbNo " +
	        "AND e.sbTransId = :sbTransId " +	
	        "AND e.hSbTransId = :hsbTransId " +	
	        "AND e.status <> 'D'")
	int updateOutOfCharge(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("hsbTransId") String hsbTransId,
	        @Param("outOfCharge") String outOfCharge,
	        @Param("outOfChargeDate") Date outOfChargeDate,	       
	        @Param("leoDate") Date leoDate      
	);
	
	
	@Query("SELECT NEW com.cwms.entities.ExportSbEntry(e.sbTransId, e.sbNo, "
	        + "e.pod, e.totalPackages, e.gateInPackages, e.hSbTransId,profitcentreId) "
	        + "FROM ExportSbEntry e "
			+ "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.sbNo = :sbNo " 
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.status <> 'D'")
	ExportSbEntry getsbNoAndPrimary(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId);

	
	@Query("SELECT COUNT(e) > 0 FROM ExportSbEntry e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND ( (:pod IS NULL OR :pod = '' OR e.pod LIKE CONCAT(:pod, '%')) "
	        + "AND (:sbNo IS NULL OR :sbNo = '' OR e.sbNo LIKE CONCAT(:sbNo, '%')) ) "
	        + "AND e.status <> 'D'")
	boolean existsByCompanyIdAndBranchIdAndPodAndSbNo(@Param("companyId") String companyId,
	                                                  @Param("branchId") String branchId,
	                                                  @Param("pod") String pod,
	                                                  @Param("sbNo") String sbNo);

	@Query("SELECT NEW com.cwms.entities.ExportSbEntry(e.sbTransId, e.sbNo, "
	        + "e.pod, e.totalPackages, e.gateInPackages, e.hSbTransId,e.profitcentreId) "
	        + "FROM ExportSbEntry e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND (:pod IS NULL OR :pod = '' OR e.pod LIKE CONCAT('%', :pod, '%')) "
	        + "AND (:sbNo IS NULL OR :sbNo = '' OR e.sbNo LIKE CONCAT('%', :sbNo, '%')) "
	        + "AND e.status <> 'D' "
	        + "ORDER BY e.createdDate DESC")
	List<ExportSbEntry> searchLatestExportMain(@Param("companyId") String companyId,
	                                           @Param("branchId") String branchId,
	                                           @Param("pod") String pod,
	                                           @Param("sbNo") String sbNo, Pageable pageable);

	
	
	
	
	
	
	@Query("SELECT E FROM ExportSbEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.sbNo = :sbNo " 
	        + "AND E.sbTransId = :sbTransId "
	        + "AND E.status <> 'D'")
	ExportSbEntry getExportSbEntryBySbNoAndSbTransId(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId);

	
	@Query("SELECT E FROM ExportSbEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitcentreId = :profitcentreId " 
	        + "AND E.sbTransId = :sbTransId "
	        + "AND E.hSbTransId = :hSbTransId "
	        + "AND E.sbNo = :sbNo "
	        + "AND E.status <> 'D'")
	ExportSbEntry getExportSbEntryBySbTransIdAndSbNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("sbTransId") String sbTransId,
	                              @Param("hSbTransId") String hSbTransId, @Param("sbNo") String sbNo);

	
	
	@Query("SELECT E FROM ExportSbEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitcentreId = :profitcentreId " 
	        + "AND E.sbTransId = :sbTransId "
	        + "AND E.status <> 'D'")
	ExportSbEntry getExportSbEntryBySbTransId(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("sbTransId") String sbTransId);

	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ExportSbEntry e " +
	        "SET e.sbNo = :newSbNo , e.sbDate = :newSbDate,e.totalPackages = :totalPackages,e.totalGrossWeight =:totalGrossWt  " +
	        "WHERE e.companyId = :companyId " +
	        "AND e.branchId = :branchId " +
	        "AND e.sbNo = :sbNo " +
	        "AND e.sbTransId = :sbTransId " +	       
	        "AND e.status <> 'D'")
	int updateChangeSbNo(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("newSbNo") String newSbNo,
	        @Param("newSbDate") Date newSbDate,
	        @Param("totalPackages") BigDecimal totalPackages,
	        @Param("totalGrossWt") BigDecimal totalGrossWt
	        
	);
	
	
	
	@Query(value="select e.cha,c.partyName from ExportSbEntry e LEFT OUTER JOIN Party c ON e.companyId=c.companyId and "
			+ "e.branchId=c.branchId and e.cha = c.partyId where e.companyId=:cid and e.branchId=:bid and e.sbTransId=:sbTransid "
			+ "and e.sbNo=:sb and e.status != 'D'")
	Object[] getChaName(@Param("cid") String companyId,
	        @Param("bid") String branchId,
	        @Param("sb") String sbNo,
	        @Param("sbTransid") String sbTransId);

}