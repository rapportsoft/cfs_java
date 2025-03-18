package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ServiceMapping;

public interface ServiceMappingRepo extends JpaRepository<ServiceMapping, String> {

//	
//	@Query(value = "select s.serviceId "
//            + "from ServiceMapping s "
//            + "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
//            + "s.profitcentreId = :profit and s.invoiceType = :type and s.gateOutType = :outType "
//            + "and s.containerSize IN :size and s.typeOfContainer IN :toc and s.scannerType IN :scan")
//List<String> getServicesForImportInvoice1(
//   @Param("cid") String cid,
//   @Param("bid") String bid,
//   @Param("profit") String profit,
//   @Param("type") String type,
//   @Param("outType") String outType,
//   @Param("size") List<String> size,  // Changed type to List<String>
//   @Param("toc") List<String> toc ,    // Changed type to List<String>
//   @Param("scan") List<String> scan 
//);

	@Query(value = "select s.serviceId " + "from ServiceMapping s "
			+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
			+ "s.profitcentreId = :profit and s.invoiceType = :type and s.gateOutType = :outType "
			+ "and s.containerSize IN :size and s.typeOfContainer IN :toc and s.scannerType IN :scan "
			+ "and s.examType IN :exam")
	List<String> getServicesForImportInvoice1(@Param("cid") String cid, @Param("bid") String bid,
			@Param("profit") String profit, @Param("type") String type, @Param("outType") String outType,
			@Param("size") List<String> size, // Changed type to List<String>
			@Param("toc") List<String> toc, // Changed type to List<String>
			@Param("scan") List<String> scan, @Param("exam") List<String> exam

	);

	@Query(value = "select s.serviceId " + "from ServiceMapping s "
			+ "LEFT OUTER JOIN Services s1 ON s.companyId=s1.companyId and s.branchId=s1.branchId and s.serviceId=s1.serviceId "
			+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
			+ "s.profitcentreId = :profit and s.invoiceType = :type and s.gateOutType = :outType "
			+ "and s.containerSize IN :size and s.typeOfContainer IN :toc and s.scannerType IN :scan "
			+ "and s.examType IN :exam and s1.serviceGroup=:sgroup")
	List<String> getServicesForImportInvoice2(@Param("cid") String cid, @Param("bid") String bid,
			@Param("profit") String profit, @Param("type") String type, @Param("outType") String outType,
			@Param("size") List<String> size, // Changed type to List<String>
			@Param("toc") List<String> toc, // Changed type to List<String>
			@Param("scan") List<String> scan, @Param("exam") List<String> exam,@Param("sgroup") String group

	);
	
	
	@Query(value = "select s.serviceId " + "from ServiceMapping s "
			+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
			+ "s.profitcentreId = :profit and s.invoiceType = :type and s.gateOutType = :outType "
			+ "and s.containerSize IN :size and s.typeOfContainer IN :toc ")
	List<String> getServicesForImportInvoice3(@Param("cid") String cid, @Param("bid") String bid,
			@Param("profit") String profit, @Param("type") String type, @Param("outType") String outType,
			@Param("size") List<String> size, // Changed type to List<String>
			@Param("toc") List<String> toc

	);
	
	@Query(value = "select s from ServiceMapping s WHERE "+
			"s.companyId =:cid and s.branchId = :bid and "+
					"s.profitcentreId =:profitcentreId and s.invoiceType = :invoiceType  and "+
			"(s.containerSize = :containerSize OR :containerSize IS NULL OR :containerSize='' ) and s.gateOutType =:gateOutType and (s.typeOfContainer = :typeOfContainer OR :typeOfContainer IS NULL OR :typeOfContainer='' ) ORDER BY s.serviceId")
			List<ServiceMapping> searchMapping(@Param("cid")String companyId, @Param("bid") String branchId, @Param("profitcentreId") String profitcentreId,
					 @Param("invoiceType") String invoiceType,
					 @Param("containerSize") String containerSize,@Param("gateOutType") String gateOutType, @Param("typeOfContainer") String typeOfContainer);
			
			
			@Query("SELECT COUNT(s)>0 FROM ServiceMapping s " +
				       "WHERE s.companyId = :cid " +
				       "AND s.branchId = :bid " +
				       "AND s.serviceId = :sid " +
				       "AND s.profitcentreId = :pid " +
				       "AND (s.containerSize = :containerSize OR :containerSize IS NULL OR :containerSize='' ) " +
				       "AND (s.containerType = :containerType OR :containerType IS NULL OR :containerType='' ) " +
				       "AND s.invoiceType = :InvoiceType " +
				       "AND s.gateOutType = :getOutType " +
				       "AND (s.scannerType = :scannerType OR :scannerType IS NULL OR :scannerType='' ) " +
				       "AND (s.typeOfContainer = :typeOfContainer OR :typeOfContainer IS NULL OR :typeOfContainer='' )")
			Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("sid") String sid,@Param("pid") String pid,
					@Param("containerSize") String containerSize,@Param("containerType") String containerType,@Param("InvoiceType") String InvoiceType,@Param("getOutType") String getOutType,
					@Param("scannerType") String scannerType,@Param("typeOfContainer") String typeOfContainer);

			
			
			@Transactional
			@Modifying
			@Query("DELETE  FROM ServiceMapping s " +
				       "WHERE s.companyId = :cid " +
				       "AND s.branchId = :bid " +
				       "AND s.serviceId = :sid " +
				       "AND s.profitcentreId = :pid " +
				       "AND (s.containerSize = :containerSize OR :containerSize IS NULL OR :containerSize='' ) " +
				       "AND (s.containerType = :containerType OR :containerType IS NULL OR :containerType='' ) " +
				       "AND s.invoiceType = :InvoiceType " +
				       "AND s.gateOutType = :getOutType " +
				       "AND (s.scannerType = :scannerType OR :scannerType IS NULL OR :scannerType='' ) " +
				       "AND (s.typeOfContainer = :typeOfContainer OR :typeOfContainer IS NULL OR :typeOfContainer='' )")
			int deleteServiceData(@Param("cid") String cid,@Param("bid") String bid,@Param("sid") String sid,@Param("pid") String pid,
					@Param("containerSize") String containerSize,@Param("containerType") String containerType,@Param("InvoiceType") String InvoiceType,@Param("getOutType") String getOutType,
					@Param("scannerType") String scannerType,@Param("typeOfContainer") String typeOfContainer);
			
			
			@Query(value = "select s.serviceId " + "from ServiceMapping s "
					+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
					+ "s.profitcentreId = :profit and s.invoiceType = :type ")
			List<String> getServicesForBondNocInvoice1(@Param("cid") String cid, @Param("bid") String bid,
					@Param("profit") String profit, @Param("type") String type

			);

			@Query(value = "select s.serviceId " + "from ServiceMapping s "
					+ "LEFT OUTER JOIN Services s1 ON s.companyId=s1.companyId and s.branchId=s1.branchId and s.serviceId=s1.serviceId "
					+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
					+ "s.profitcentreId = :profit and s.invoiceType = :type and s1.serviceGroup=:sgroup")
			List<String> getServicesForBondNocInvoice2(@Param("cid") String cid, @Param("bid") String bid,
					@Param("profit") String profit, @Param("type") String type,@Param("sgroup") String group

			);
			
			
			
			@Query(value = "select s.serviceId " + "from ServiceMapping s "
					+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
					+ "s.profitcentreId = :profit and s.invoiceType = :type group by s.serviceId")
			List<String> getServicesForAuctionInvoice(@Param("cid") String cid, @Param("bid") String bid,
					@Param("profit") String profit, @Param("type") String type);

}
