package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportContainerCarting;

public interface ExportContainerCartingRepo extends JpaRepository<ExportContainerCarting, String> {

	@Query(value="select e from ExportContainerCarting e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.deStuffId=:id")
	ExportContainerCarting getDataBuDestuffId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select NEW com.cwms.entities.ExportContainerCarting(e.deStuffId, e.deStuffDate, e.profitCentreId, e.shippingAgent,"
			+ "e.containerNo, e.containerType, e.containerSize, e.containerStatus, e.customSealNo,"
			+ "e.saSealNo, e.onAccountOf, e.gateInId, e.gateInDate, e.status, e.createdBy,"
			+ "e.vehicleNo, e.fromDate, e.toDate, e.remark, e.containerSearchType,"
			+ "p2.partyName, p1.partyName) "
			+ "from ExportContainerCarting e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.shippingAgent=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.onAccountOf=p2.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.deStuffId=:id")
	ExportContainerCarting getDataBuDestuffId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select e.deStuffId,DATE_FORMAT(e.deStuffDate,'%d %M %y'),e.containerNo,e.containerSize,e.containerType "
			+ "from ExportContainerCarting e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(:val is null OR :val = '' OR e.deStuffId LIKE CONCAT('%',:val,'%') OR e.containerType =:val "
			+ "OR e.containerSize =:val OR e.containerNo LIKE CONCAT('%',:val,'%')) order by e.deStuffDate desc")
	List<Object[]> search(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
