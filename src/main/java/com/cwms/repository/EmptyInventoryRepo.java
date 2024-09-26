package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.EmptyInventory;

public interface EmptyInventoryRepo extends JpaRepository<EmptyInventory, String> {

	
	@Query(value="select e from EmptyInventory e where e.companyId=:cid and e.branchId=:bid and e.erpDocRefNo=:erp and e.docRefNo=:doc and "
	        + "e.subDocRefNo=:sub and e.gateInId=:gatein and e.containerNo=:cont and e.status != 'D'")
	EmptyInventory getById(@Param("cid") String cid, 
	                       @Param("bid") String bid, 
	                       @Param("erp") String erp, 
	                       @Param("doc") String doc, 
	                       @Param("sub") String sub, 
	                       @Param("gatein") String gatein, 
	                       @Param("cont") String cont);

}
