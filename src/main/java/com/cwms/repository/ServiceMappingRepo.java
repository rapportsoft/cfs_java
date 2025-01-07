package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
