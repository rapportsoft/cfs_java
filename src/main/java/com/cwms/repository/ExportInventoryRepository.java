package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportInventory;

public interface ExportInventoryRepository extends JpaRepository<ExportInventory, String> {

	@Query(value="select e from ExportInventory e where e.companyId=:cid and e.branchId=:bid and e.gateInId=:gateId and e.status != 'D'")
	ExportInventory getSingleDataByGateInId(@Param("cid") String cid,@Param("bid") String bId,@Param("gateId") String gate);
	
	@Query(value="select COUNT(e) > 0 from ExportInventory e where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "(e.gateOutId is not null OR e.gateOutId != '') and e.containerNo=:con")
	Boolean checkContainerInInventory(@Param("cid") String cid,@Param("bid") String bId,@Param("con") String con);
}
