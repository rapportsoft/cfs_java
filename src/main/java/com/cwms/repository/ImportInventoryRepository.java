package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.ImportInventory;

public interface ImportInventoryRepository extends JpaRepository<ImportInventory, String> {

	@Query(value="select i from ImportInventory i where i.companyId=:cid and i.branchId=:bid and i.igmTransId=:igmtrans and i.igmNo=:igm "
			+ "and i.containerNo=:cont and i.gateInId=:gatein and i.status != 'D'")
	ImportInventory getById(@Param("cid") String cid,@Param("bid") String bid,@Param("igmtrans") String igmtrans,@Param("igm") String igm,
			@Param("cont") String cont,@Param("gatein") String gatein) ;
	
	
	@Query(value="Update ImportInventory i SET i.gatePassNo=:id , i.gatePassDate = CURRENT_DATE where i.companyId=:cid and i.branchId=:bid "
			+ "and i.igmTransId=:trans and i.igmNo=:igm and i.status != 'D' and i.deStuffId=:destuff")
	int updatData(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String igmtrans,@Param("igm") String igm,
			@Param("destuff") String destuff,@Param("id") String id);
	
	@Modifying
	@Transactional
	@Query(value="Update ImportInventory i SET i.gateOutId=:id , i.gateOutDate = CURRENT_DATE where i.companyId=:cid and i.branchId=:bid "
			+ "and i.igmTransId=:trans and i.igmNo=:igm and i.status != 'D' and i.gatePassNo=:gatePass")
	int updateGateOutId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String igmtrans,@Param("igm") String igm,
			@Param("gatePass") String gatePass,@Param("id") String id);
	
	@Modifying
	@Transactional
	@Query(value="Update ImportInventory i SET i.gateOutId=:id , i.gateOutDate = CURRENT_DATE where i.companyId=:cid and i.branchId=:bid "
			+ "and i.igmTransId=:trans and i.igmNo=:igm and i.status != 'D' and i.gatePassNo=:gatePass and i.containerNo=:con")
	int updateGateOutId1(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String igmtrans,@Param("igm") String igm,
			@Param("gatePass") String gatePass,@Param("con") String con,@Param("id") String id);
}
