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
	

}