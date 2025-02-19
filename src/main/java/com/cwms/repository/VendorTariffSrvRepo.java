package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFSTariffService;
import com.cwms.entities.VendorTariffSrv;

import jakarta.transaction.Transactional;

public interface VendorTariffSrvRepo extends JpaRepository<VendorTariffSrv, String> {
	
	@Query(value="select s.serviceId, c.serviceShortDesc, c.serviceUnit, s.rate "
			+ "from VendorTariffSrv s "
			+ "LEFT OUTER JOIN APServices c ON s.companyId=c.companyId and s.branchId=c.branchId and s.serviceId=c.serviceId "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status = 'A' and c.status = 'A' and s.cfsTariffNo='CFS1000001' "
			+ "and c.serviceGroup NOT IN ('G','H')  group by s.serviceId")
	List<Object[]> getGeneralTarrifData1(@Param("cid") String cid,@Param("bid") String bid);

	@Modifying
	@Transactional
	@Query("UPDATE VendorTariffSrv e " + "SET e.ammendStatus = 'A', " + "    e.editedBy = :user, "
			+ "    e.editedDate = CURRENT_TIMESTAMP " + "WHERE e.companyId = :companyId "
			+ "  AND e.branchId = :branchId " + "  AND e.cfsTariffNo = :cfsTariffNo "
			+ "  AND e.cfsAmendNo = :cfsAmendNo " + "  AND e.ammendStatus = 'N' " + "  AND e.status <> 'A'")
	int updateSubmitCfsService(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cfsTariffNo") String cfsTariffNo, @Param("cfsAmendNo") String cfsAmendNo,
			@Param("user") String user);

	@Modifying
	@Transactional
	@Query("UPDATE VendorTariffSrv e " + "SET e.containerSize = :containerSize, "
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

	@Query("SELECT COUNT(e) > 0 FROM VendorTariffSrv e " + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
			+ "AND e.cfsTariffNo = :cfsTariffNo AND e.cfsAmendNo = :cfsAmendNo AND e.serviceId = :serviceId "
			+ "AND e.status = 'A'")
	boolean existByTariffAndAmndNoAndServiceId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cfsTariffNo") String cfsTariffNo, @Param("cfsAmendNo") String cfsAmendNo,
			@Param("serviceId") String serviceId);

	@Query(value = "select DISTINCT NEW com.cwms.entities.VendorTariffSrv(s.serviceId,s.rate) from VendorTariffSrv s where "
			+ "s.companyId=:cid and s.branchId=:bid and s.status = 'A' and s.cfsTariffNo='CFS1000001' and s.rangeType='NA'")
	List<VendorTariffSrv> getGeneralTarrifData(@Param("cid") String cid, @Param("bid") String bid);

	@Query(value = "SELECT new com.cwms.entities.VendorTariffSrv("
			+ "s.companyId, s.branchId, s.finYear, s.cfsTariffNo, s.cfsAmendNo, s.serviceId, "
			+ "s.srNo, s.containerSize, s.commodityCode, s.cargoType, s.status, s.rangeType, "
			+ "s.profitCentreId, s.serviceUnit, s.serviceUnitI, s.fromRange, s.toRange, s.rate, "
			+ "s.minimumRate, s.createdBy, s.createdDate, s.editedBy, s.editedDate, s.approvedBy, "
			+ "s.approvedDate, s.currencyId, s.ammendStatus, s.defaultChk, j.jarDtlDesc) " + "FROM VendorTariffSrv s "
			+ "LEFT JOIN APServices se "
			+ "ON s.companyId = se.companyId  AND s.branchId = se.branchId AND se.status <> 'D' AND s.serviceId = se.serviceId "
			+ "LEFT JOIN JarDetail j "
			+ "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00024' AND se.sacCode = j.jarDtlId "
			+ "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.status = 'A' "
			+ "AND s.cfsTariffNo = :tariffNo AND s.cfsAmendNo = :cfsAmendNo AND s.serviceId = :serviceId")
	List<VendorTariffSrv> getCFSTariffServiceByServiceId(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("tariffNo") String tariffNo,
			@Param("cfsAmendNo") String cfsAmendNo, @Param("serviceId") String serviceId);

	@Query(value = "SELECT new com.cwms.entities.VendorTariffSrv("
			+ "s.srNo, s.containerSize, j.jarDtlDesc, s.cargoType, s.rangeType, "
			+ "s.fromRange, s.toRange, s.rate, se.serviceShortDesc, s.currencyId) " + "FROM VendorTariffSrv s "
			+ "LEFT JOIN APServices se "
			+ "ON s.companyId = se.companyId  AND s.branchId = se.branchId AND se.status <> 'D' AND s.serviceId = se.serviceId "
			+ "LEFT JOIN JarDetail j "
			+ "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00006' AND s.commodityCode = j.jarDtlId "
			+ "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.status = 'A' AND se.status = 'A' "
			+ "AND s.cfsTariffNo = :tariffNo AND s.cfsAmendNo = :cfsAmendNo")
	List<VendorTariffSrv> getForTariffReport(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("tariffNo") String tariffNo, @Param("cfsAmendNo") String cfsAmendNo);
	
	
	@Query(value = "SELECT new com.cwms.entities.VendorTariffSrv("
			+ "s.srNo, s.containerSize, j.jarDtlDesc, s.cargoType, s.rangeType, "
			+ "s.fromRange, s.toRange, s.rate, se.serviceShortDesc, s.currencyId, s.cfsAmendNo, s.serviceId, s.editedBy, s.editedDate) " + "FROM VendorTariffSrv s "
			+ "LEFT JOIN APServices se "
			+ "ON s.companyId = se.companyId  AND s.branchId = se.branchId AND se.status <> 'D' AND s.serviceId = se.serviceId "
			+ "LEFT JOIN JarDetail j "
			+ "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00006' AND s.commodityCode = j.jarDtlId "
			+ "WHERE s.companyId = :companyId AND s.branchId = :branchId AND se.status <> 'D' "
			+ "AND s.cfsTariffNo = :tariffNo AND s.status In ('A' , 'C')")
	List<VendorTariffSrv> getForTariffAuditTrailReport(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("tariffNo") String tariffNo);
	
	
	
	@Query(value = "SELECT c.serviceId, c.serviceUnit, c.rate, c.cfsTariffNo, c.cfsAmendNo, c.currencyId, c.srNo, "
	        + "c.serviceUnitI, c.rangeType, cu.exrate, c.minimumRate, s.taxId, tx.taxPerc, igm.percentage, igm.amount, "
	        + "igm.mPercentage, igm.mAmount, s.acCode, s.serviceGroup, c.fromRange, c.toRange, s.criteriaType,s.serviceShortDesc "
	        + "FROM VendorTariffSrv c "
	        + "LEFT OUTER JOIN VendorTariff t ON c.companyId = t.companyId AND c.branchId = t.branchId AND c.cfsTariffNo = t.cfsTariffNo "
	        + "AND c.cfsAmendNo = t.cfsAmndNo AND c.profitCentreId = t.profitCentreId "
	        + "LEFT OUTER JOIN APServices s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
	        + "LEFT OUTER JOIN CurrencyConv cu ON c.companyId = cu.companyId AND c.branchId = cu.branchId AND c.currencyId = cu.convCurrency "
	        + "LEFT OUTER JOIN TaxDtl tx ON s.companyId = tx.companyId AND s.taxId = tx.taxId "
	        + "AND DATE(:assessDate) BETWEEN tx.periodFrom AND tx.periodTo "
	        + "LEFT OUTER JOIN IgmServiceDtl igm ON c.companyId = igm.companyId AND c.branchId = igm.branchId "
	        + "AND c.serviceId = igm.serviceId AND igm.containerNo = :con AND igm.igmTransId = :trans AND "
	        + "igm.igmNo = :igm AND igm.igmLineNo = :lineNo and igm.companyId = :cid AND igm.branchId = :bid "
	        + "WHERE c.companyId = :cid AND c.branchId = :bid "
	        + "AND c.status = 'A' AND :assessDate < t.cfsValidateDate AND c.serviceId IN :serviceList "
	        + "AND c.cfsTariffNo = :tariffNo AND c.containerSize IN :consize AND c.cargoType IN :type "
	        + "GROUP BY c.serviceId "
	        + "ORDER BY c.serviceId")
	List<Object[]> getServiceRate(@Param("cid") String cid, @Param("bid") String bid, @Param("assessDate") Date assessDate,
			@Param("con") String con,@Param("trans") String trans, @Param("igm") String igm, @Param("lineNo") String lineNo,
	                              @Param("serviceList") List<String> serviceList, @Param("tariffNo") String tariffNo,
	                              @Param("consize") List<String> consize, @Param("type") List<String> type);

	
	@Query(value = "SELECT c.serviceId, c.serviceUnit, c.rate, c.cfsTariffNo, c.cfsAmendNo, c.currencyId, c.srNo, "
	        + "c.serviceUnitI, c.rangeType, cu.exrate, c.minimumRate, s.taxId, tx.taxPerc, igm.percentage, igm.amount, "
	        + "igm.mPercentage, igm.mAmount, s.acCode, s.serviceGroup, c.fromRange, c.toRange, s.criteriaType "
	        + "FROM VendorTariffSrv c "
	        + "LEFT OUTER JOIN VendorTariff t ON c.companyId = t.companyId AND c.branchId = t.branchId AND c.cfsTariffNo = t.cfsTariffNo "
	        + "AND c.cfsAmendNo = t.cfsAmndNo AND c.profitCentreId = t.profitCentreId "
	        + "LEFT OUTER JOIN APServices s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
	        + "LEFT OUTER JOIN CurrencyConv cu ON c.companyId = cu.companyId AND c.branchId = cu.branchId AND c.currencyId = cu.convCurrency "
	        + "LEFT OUTER JOIN TaxDtl tx ON s.companyId = tx.companyId AND s.taxId = tx.taxId "
	        + "AND DATE(:assessDate) BETWEEN tx.periodFrom AND tx.periodTo "
	        + "LEFT OUTER JOIN IgmServiceDtl igm ON c.companyId = igm.companyId AND c.branchId = igm.branchId "
	        + "AND c.serviceId = igm.serviceId "
	        + "WHERE c.companyId = :cid AND c.branchId = :bid "
	        + "AND igm.containerNo = :con AND igm.igmTransId = :trans AND igm.igmNo = :igm AND igm.igmLineNo = :lineNo "
	        + "AND c.status = 'A' AND :assessDate < t.cfsValidateDate AND c.serviceId IN :serviceList "
	        + "AND c.cfsTariffNo = :tariffNo AND c.containerSize IN :consize "
	        + "GROUP BY c.serviceId "
	        + "ORDER BY c.serviceId")
	List<Object[]> getServiceRateGeneral(@Param("cid") String cid, @Param("bid") String bid, @Param("assessDate") Date assessDate,
			@Param("con") String con,@Param("trans") String trans, @Param("igm") String igm, @Param("lineNo") String lineNo,
	                              @Param("serviceList") List<String> serviceList, @Param("tariffNo") String tariffNo,
	                              @Param("consize") List<String> consize);
	
	@Query(value="select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate "
			+ "from VendorTariffSrv c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and c.rangeType=:rtype and c.containerSize IN :size and c.cargoType IN :ctype and "
			+ "c.commodityCode IN :code order by c.cfsTariffNo,c.serviceId,FIELD(c.containerSize,:size1,'ALL'),"
			+ "FIELD(c.cargoType,:ctype1,'ALL'),FIELD(c.commodityCode,:code1,'ALL')")
	List<Object[]> getDataByServiceId(@Param("cid") String cid, @Param("bid") String bid,@Param("tariffNo") String tariffNo,
			@Param("amd") String amd,@Param("service") String service,@Param("rtype") String rtype,@Param("size") List<String> size,
			@Param("ctype") List<String> ctype,@Param("code") List<String> code,@Param("size1") String size1,
			@Param("ctype1") String ctype1,@Param("code1") String code1);
	
	
	@Query(value="select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate "
			+ "from VendorTariffSrv c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and :rtype between c.fromRange and c.toRange and c.containerSize IN :size and c.cargoType IN :ctype and "
			+ "c.commodityCode IN :code order by c.cfsTariffNo,c.serviceId,FIELD(c.containerSize,:size1,'ALL'),"
			+ "FIELD(c.cargoType,:ctype1,'ALL'),FIELD(c.commodityCode,:code1,'ALL')")
	Object getRangeDataByServiceId(@Param("cid") String cid, @Param("bid") String bid,@Param("tariffNo") String tariffNo,
			@Param("amd") String amd,@Param("service") String service,@Param("rtype") BigDecimal rtype,@Param("size") List<String> size,
			@Param("ctype") List<String> ctype,@Param("code") List<String> code,@Param("size1") String size1,
			@Param("ctype1") String ctype1,@Param("code1") String code1);
	
	@Query(value="select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate "
			+ "from VendorTariffSrv c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and :rtype between c.fromRange and c.toRange and "
			+ "c.commodityCode IN :code order by c.cfsTariffNo,c.serviceId,FIELD(c.commodityCode,:code1,'ALL')")
	Object getRangeDataByForbondNocServiceId(@Param("cid") String cid, @Param("bid") String bid,@Param("tariffNo") String tariffNo,
			@Param("amd") String amd,@Param("service") String service,@Param("rtype") BigDecimal rtype,@Param("code") List<String> code,@Param("code1") String code1);
	
	
	
	@Query(value = "SELECT c.serviceId, c.serviceUnit, c.rate, c.cfsTariffNo, c.cfsAmendNo, c.currencyId, c.srNo, "
	        + "c.serviceUnitI, c.rangeType, cu.exrate, c.minimumRate, s.taxId, tx.taxPerc, '', '', "
	        + "'', '', s.acCode, s.serviceGroup, c.fromRange, c.toRange, s.criteriaType,s.serviceShortDesc "
	        + "FROM VendorTariffSrv c "
	        + "LEFT OUTER JOIN VendorTariff t ON c.companyId = t.companyId AND c.branchId = t.branchId AND c.cfsTariffNo = t.cfsTariffNo "
	        + "AND c.cfsAmendNo = t.cfsAmndNo AND c.profitCentreId = t.profitCentreId "
	        + "LEFT OUTER JOIN APServices s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
	        + "LEFT OUTER JOIN CurrencyConv cu ON c.companyId = cu.companyId AND c.branchId = cu.branchId AND c.currencyId = cu.convCurrency "
	        + "LEFT OUTER JOIN TaxDtl tx ON s.companyId = tx.companyId AND s.taxId = tx.taxId "
	        + "AND DATE(:assessDate) BETWEEN tx.periodFrom AND tx.periodTo "
	        + "WHERE c.companyId = :cid AND c.branchId = :bid "
	        + "AND c.status = 'A' AND :assessDate < t.cfsValidateDate AND c.serviceId IN :serviceList "
	        + "AND c.cfsTariffNo = :tariffNo "
	        + "GROUP BY c.serviceId "
	        + "ORDER BY c.serviceId")
	List<Object[]> getServiceRateForBondNoc(@Param("cid") String cid, @Param("bid") String bid, @Param("assessDate") Date assessDate,
	                              @Param("serviceList") List<String> serviceList, @Param("tariffNo") String tariffNo);
	
	
	
	@Query(value="select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate "
			+ "from VendorTariffSrv c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and c.rangeType=:rtype and "
			+ "c.commodityCode IN :code order by c.cfsTariffNo,c.serviceId,FIELD(c.commodityCode,:code1,'ALL')")
	List<Object[]> getDataByServiceIdForBondNOC(@Param("cid") String cid, @Param("bid") String bid,@Param("tariffNo") String tariffNo,
			@Param("amd") String amd,@Param("service") String service,@Param("rtype") String rtype,@Param("code") List<String> code,@Param("code1") String code1);
	
	
	@Query(value="select s.serviceId, c.serviceShortDesc, c.serviceUnit, s.rate, s.serviceUnitI,s.cfsTariffNo,s.cfsAmendNo "
			+ "from VendorTariffSrv s "
			+ "LEFT OUTER JOIN APServices c ON s.companyId=c.companyId and s.branchId=c.branchId and s.serviceId=c.serviceId "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status = 'A' and c.status = 'A' and s.cfsTariffNo='CFS1000001' "
			+ "group by s.serviceId")
	List<Object[]> getGeneralTarrifData2(@Param("cid") String cid,@Param("bid") String bid);

}