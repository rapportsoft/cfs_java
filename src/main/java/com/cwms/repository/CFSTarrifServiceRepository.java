package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.CFSTariffService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

public interface CFSTarrifServiceRepository extends JpaRepository<CFSTariffService, String> {

	@Modifying
	@Transactional
	@Query("UPDATE CFSTariffService e " + "SET e.ammendStatus = 'A', " + "    e.editedBy = :user, "
			+ "    e.editedDate = CURRENT_TIMESTAMP " + "WHERE e.companyId = :companyId "
			+ "  AND e.branchId = :branchId " + "  AND e.cfsTariffNo = :cfsTariffNo "
			+ "  AND e.cfsAmendNo = :cfsAmendNo " + "  AND e.ammendStatus = 'N' " + "  AND e.status <> 'A'")
	int updateSubmitCfsService(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cfsTariffNo") String cfsTariffNo, @Param("cfsAmendNo") String cfsAmendNo,
			@Param("user") String user);

	@Modifying
	@Transactional
	@Query("UPDATE CFSTariffService e " + "SET e.containerSize = :containerSize, "
			+ "    e.commodityCode = :commodityCode, " + "    e.cargoType = :cargoType, "
			+ "    e.fromRange = :fromRange, " + "    e.toRange = :toRange, " + "    e.rate = :rate, "
			+ "    e.minimumRate = :minimumRate, " + "    e.defaultChk = :defaultChk, " + "    e.editedBy = :user, "
			+ "    e.editedDate = CURRENT_TIMESTAMP " + "WHERE e.companyId = :companyId "
			+ "  AND e.branchId = :branchId " + "  AND e.cfsTariffNo = :cfsTariffNo "
			+ "  AND e.cfsAmendNo = :cfsAmendNo " + "  AND e.serviceId = :serviceId " + "  AND e.srNo = :srNo "
			+ "  AND e.status <> 'D'")
	int updateAmmendCfsService(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cfsTariffNo") String cfsTariffNo, @Param("cfsAmendNo") String cfsAmendNo,
			@Param("serviceId") String serviceId, @Param("srNo") BigDecimal srNo,
			@Param("containerSize") String containerSize, @Param("commodityCode") String commodityCode,
			@Param("cargoType") String cargoType, @Param("fromRange") BigDecimal fromRange,
			@Param("toRange") BigDecimal toRange, @Param("rate") BigDecimal rate,
			@Param("minimumRate") BigDecimal minimumRate, @Param("defaultChk") String defaultChk,
			@Param("user") String user);

	@Query("SELECT COUNT(e) > 0 FROM CFSTariffService e " + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
			+ "AND e.cfsTariffNo = :cfsTariffNo AND e.cfsAmendNo = :cfsAmendNo AND e.serviceId = :serviceId "
			+ "AND e.status = 'A'")
	boolean existByTariffAndAmndNoAndServiceId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cfsTariffNo") String cfsTariffNo, @Param("cfsAmendNo") String cfsAmendNo,
			@Param("serviceId") String serviceId);

	@Query(value = "select DISTINCT NEW com.cwms.entities.CFSTariffService(s.serviceId,s.rate) from CFSTariffService s where "
			+ "s.companyId=:cid and s.branchId=:bid and s.status = 'A' and s.cfsTariffNo='CFS1000001' and s.rangeType='NA'")
	List<CFSTariffService> getGeneralTarrifData(@Param("cid") String cid, @Param("bid") String bid);

	@Query(value = "SELECT new com.cwms.entities.CFSTariffService("
			+ "s.companyId, s.branchId, s.finYear, s.cfsTariffNo, s.cfsAmendNo, s.serviceId, "
			+ "s.srNo, s.containerSize, s.commodityCode, s.cargoType, s.status, s.rangeType, "
			+ "s.profitCentreId, s.serviceUnit, s.serviceUnitI, s.fromRange, s.toRange, s.rate, "
			+ "s.minimumRate, s.createdBy, s.createdDate, s.editedBy, s.editedDate, s.approvedBy, "
			+ "s.approvedDate, s.currencyId, s.ammendStatus, s.defaultChk, j.jarDtlDesc) " + "FROM CFSTariffService s "
			+ "LEFT JOIN Services se "
			+ "ON s.companyId = se.companyId  AND s.branchId = se.branchId AND se.status <> 'D' AND s.serviceId = se.serviceId "
			+ "LEFT JOIN JarDetail j "
			+ "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00024' AND se.sacCode = j.jarDtlId "
			+ "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.status = 'A' "
			+ "AND s.cfsTariffNo = :tariffNo AND s.cfsAmendNo = :cfsAmendNo AND s.serviceId = :serviceId")
	List<CFSTariffService> getCFSTariffServiceByServiceId(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("tariffNo") String tariffNo,
			@Param("cfsAmendNo") String cfsAmendNo, @Param("serviceId") String serviceId);

	@Query(value = "SELECT new com.cwms.entities.CFSTariffService("
			+ "s.srNo, s.containerSize, j.jarDtlDesc, s.cargoType, s.rangeType, "
			+ "s.fromRange, s.toRange, s.rate, se.serviceShortDesc, s.currencyId) " + "FROM CFSTariffService s "
			+ "LEFT JOIN Services se "
			+ "ON s.companyId = se.companyId  AND s.branchId = se.branchId AND se.status <> 'D' AND s.serviceId = se.serviceId "
			+ "LEFT JOIN JarDetail j "
			+ "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00006' AND s.commodityCode = j.jarDtlId "
			+ "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.status = 'A' "
			+ "AND s.cfsTariffNo = :tariffNo AND s.cfsAmendNo = :cfsAmendNo")
	List<CFSTariffService> getForTariffReport(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("tariffNo") String tariffNo, @Param("cfsAmendNo") String cfsAmendNo);
	
	
	@Query(value = "SELECT new com.cwms.entities.CFSTariffService("
			+ "s.srNo, s.containerSize, j.jarDtlDesc, s.cargoType, s.rangeType, "
			+ "s.fromRange, s.toRange, s.rate, se.serviceShortDesc, s.currencyId, s.cfsAmendNo, s.serviceId, s.editedBy, s.editedDate) " + "FROM CFSTariffService s "
			+ "LEFT JOIN Services se "
			+ "ON s.companyId = se.companyId  AND s.branchId = se.branchId AND se.status <> 'D' AND s.serviceId = se.serviceId "
			+ "LEFT JOIN JarDetail j "
			+ "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00006' AND s.commodityCode = j.jarDtlId "
			+ "WHERE s.companyId = :companyId AND s.branchId = :branchId "
			+ "AND s.cfsTariffNo = :tariffNo AND s.status In ('A' , 'C')")
	List<CFSTariffService> getForTariffAuditTrailReport(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("tariffNo") String tariffNo);
	

}