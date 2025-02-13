package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.Cfinvsrvanx;
import com.cwms.entities.CfinvsrvanxPro;

public interface CfinvsrvanxproRepository extends JpaRepository<CfinvsrvanxPro, String> {
	@Query(value="select a from CfinvsrvanxPro a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.processTransId=:id")
	List<CfinvsrvanxPro> getDataByProcessTransId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId,a.branchId,a.processTransId,a.serviceId,a.taxId,a.erpDocRefNo,a.srlNo,a.docRefNo,a.profitcentreId "
			+ ",a.serviceUnit,a.executionUnit, a.serviceUnit1, a.executionUnit1, a.rate, a.currencyId, a.partyId, a.woNo, a.woAmndNo, a.criteria, a.rangeFrom, a.rangeTo, a.rangeType, a.containerNo, a.containerStatus, a.commodityDescription, a.actualNoOfPackages, a.startDate, a.status, a.srvManualFlag, s.serviceShortDesc, a.addServices, a.lineNo) "
			+ "from CfinvsrvanxPro a "
	        + "LEFT OUTER JOIN Services s ON a.companyId = s.companyId AND a.branchId = s.branchId AND a.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.processTransId=:assesmentId AND a.profitcentreId = :profiCentreId AND a.lineNo = :lineNo order by a.srlNo DESC")
	List<CfinvsrvanxPro> getAllCfInvSrvAnxListByAssesmentId(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId, @Param("lineNo") String lineNo);
	
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.srlNo = :srlNo AND e.status <> 'D' AND e.containerNo = :containerNo " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.processTransId = :assesmentId ")		      
		boolean existsByAssesmentIdAndSrlNo1(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId,
		    @Param("srlNo") BigDecimal srlNo, @Param("containerNo") String containerNo);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CfinvsrvanxPro SET executionUnit = :executionUnit, executionUnit1 = :executionUnit1, rate = :rate, actualNoOfPackages = :actualNoOfPackages, localAmt = :actualNoOfPackages , invoiceAmt = :actualNoOfPackages "
			+ "WHERE companyId = :companyId " + "AND branchId = :branchId " + "AND processTransId = :assesmentId "
			+ "AND status <> 'D' AND profitcentreId = :profiCentreId AND srlNo = :srlNo AND containerNo = :containerNo")
	int updateCfinvsrvanx1(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId,
									  @Param("assesmentId") String assesmentId,  @Param("srlNo") BigDecimal srlNo, @Param("executionUnit") String executionUnit,
									  @Param("executionUnit1") String executionUnit1, @Param("rate") BigDecimal rate, @Param("actualNoOfPackages") BigDecimal actualNoOfPackages, @Param("containerNo") String containerNo);
	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId,a.branchId,a.processTransId,a.serviceId,a.taxId,a.erpDocRefNo,a.srlNo,a.docRefNo,a.profitcentreId "
			+ ",a.serviceUnit,a.executionUnit, a.serviceUnit1, a.executionUnit1, a.rate, a.currencyId, a.partyId, a.woNo, a.woAmndNo, a.criteria, a.rangeFrom, a.rangeTo, a.rangeType, a.containerNo, a.containerStatus, a.commodityDescription, a.actualNoOfPackages, a.startDate, a.status, a.srvManualFlag, s.serviceShortDesc, a.addServices) "
			+ "from CfinvsrvanxPro a "
	        + "LEFT OUTER JOIN Services s ON a.companyId = s.companyId AND a.branchId = s.branchId AND a.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.processTransId=:assesmentId AND a.profitcentreId = :profiCentreId AND a.containerNo = :containerNo order by a.srlNo DESC")
	List<CfinvsrvanxPro> getAllCfInvSrvAnxListByAssesmentId1(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId, @Param("containerNo") String containerNo);
	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId, a.branchId, a.assesmentId,a.assesmentLineNo,a.assesmentDate,a.containerNo,a.containerSize,a.containerType,a.gateInDate,a.invoiceUptoDate "
			+ ",e.gateOutId,e.gatePassNo, a.partyId, e.gateOutDate, a.destuffDate, a.stuffTallyDate) "
			+ "from AssessmentSheetPro a "
			+ "LEFT JOIN ImportInventory e on a.companyId = e.companyId AND a.branchId = e.branchId AND a.assesmentId = e.assessmentId AND a.containerNo = e.containerNo AND e.status <> 'D' "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Order By a.containerNo")
	List<CfinvsrvanxPro> getAllContainerListOfAssessMentSheet1(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	
	@Query("SELECT MAX(e.srlNo) " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.status <> 'D' " +
		       "AND e.containerNo = :containerNo " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.processTransId = :assesmentId")
		Optional<BigDecimal> getHighestSrlNoByContainerNo2(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId,
		    @Param("containerNo") String containerNo);

}
