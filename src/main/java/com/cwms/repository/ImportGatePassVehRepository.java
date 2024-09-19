package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfImportGatePassVehDtl;

public interface ImportGatePassVehRepository extends JpaRepository<CfImportGatePassVehDtl, String> {

	@Query(value="select COUNT(v) from CfImportGatePassVehDtl v where v.companyId=:cid and v.branchId=:bid and v.gatePassId=:gatepass and v.status != 'D'")
	int getcount(@Param("cid") String cid,@Param("bid") String bid,@Param("gatepass") String gatepass);
	
	@Query(value="select v from CfImportGatePassVehDtl v where v.companyId=:cid and v.branchId=:bid and v.gatePassId=:gatepass "
			+ "and v.igmTransId=:igmtrans and v.igmLineNo=:line and v.status != 'D'")
	List<CfImportGatePassVehDtl> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("gatepass") String gatepass,@Param("igmtrans") String igmtrans,
			@Param("line") String line);


	@Query(value="select v from CfImportGatePassVehDtl v where v.companyId=:cid and v.branchId=:bid and v.gatePassId=:gatepass and v.status != 'D'")
	List<CfImportGatePassVehDtl> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("gatepass") String gatepass);
	
	
	
}
