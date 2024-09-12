package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.PartyAddress;

import jakarta.transaction.Transactional;

public interface PartyAddressRepository extends JpaRepository<PartyAddress, String> {

	@Query(value="select COUNT(a) > 0 from PartyAddress a where a.companyId=:cid and a.branchId=:bid and a.gstNo=:gst and a.status != 'D'")
	Boolean isGSTExist(@Param("cid") String cid,@Param("bid") String bid,@Param("gst") String gst);
	
	@Query(value="select COUNT(a) > 0 from PartyAddress a where a.companyId=:cid and a.branchId=:bid and a.gstNo=:gst and a.partyId != :id and a.srNo!=:sr and a.status != 'D'")
	Boolean isGSTExist1(@Param("cid") String cid,@Param("bid") String bid,@Param("gst") String gst,@Param("id") String id,@Param("sr") String sr);
	
	@Query(value="select COUNT(a) from PartyAddress a where a.companyId=:cid and a.branchId=:bid and a.partyId=:id")
	int getCount(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select a from PartyAddress a where a.companyId=:cid and a.branchId=:bid "
			+ "and a.partyId=:party and a.srNo=:sr and a.status != 'D'")
	PartyAddress getData(@Param("cid") String cid,@Param("bid") String bid,@Param("party") String party,@Param("sr") String sr);
	
	
	@Transactional
	@Modifying
	@Query(value="update PartyAddress a SET a.defaultChk = 'N' where a.companyId=:cid and a.branchId=:bid and a.partyId = :id and a.srNo != :sr and a.status != 'D'")
	int updateDefault(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,@Param("sr") String sr);
	
	@Transactional
	@Modifying
	@Query(value="update PartyAddress a SET a.status = 'D' where a.companyId=:cid and a.branchId=:bid and a.partyId = :id and a.status != 'D'")
	int updateStatus(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from PartyAddress a where a.companyId=:cid and a.branchId=:bid and a.partyId = :id and a.status != 'D'")
	List<PartyAddress> getDataByPartyId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}
