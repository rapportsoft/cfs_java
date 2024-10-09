package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.HoldDetails;

public interface HoldDetailsRepository extends JpaRepository<HoldDetails, String> {

	@Query(value="select COUNT(h) from HoldDetails h where h.companyId=:cid and h.branchId=:bid and h.status != 'D' and h.gateInId=:gate and "
			+ "h.containerNo=:con and h.igmTransId=:igmtrans and h.igmNo=:igm and h.igmLineNo=:line")
	int getIndex(@Param("cid") String cid,@Param("bid") String bid,@Param("gate") String gate,@Param("con") String con,
			@Param("igmtrans") String igmtrans,@Param("igm") String igm,@Param("line") String line);
	
	@Query(value="select COUNT(h)>0 from HoldDetails h where h.companyId=:cid and h.branchId=:bid and h.status != 'D' and h.gateInId=:gate and "
			+ "h.containerNo=:con and h.igmTransId=:igmtrans and h.igmNo=:igm and h.igmLineNo=:line and holdingAgency=:holdAgency")
	Boolean isExist(@Param("cid") String cid,@Param("bid") String bid,@Param("gate") String gate,@Param("con") String con,
			@Param("igmtrans") String igmtrans,@Param("igm") String igm,@Param("line") String line,@Param("holdAgency") String holdAgency);


	@Query(value="select h from HoldDetails h where h.companyId=:cid and h.branchId=:bid and h.status != 'D' and h.gateInId=:gate and "
			+ "h.containerNo=:con and h.igmTransId=:igmtrans and h.igmNo=:igm and h.igmLineNo=:line")
	List<HoldDetails> getData(@Param("cid") String cid,@Param("bid") String bid,@Param("gate") String gate,@Param("con") String con,
			@Param("igmtrans") String igmtrans,@Param("igm") String igm,@Param("line") String line);
	
	@Query(value="select h from HoldDetails h where h.companyId=:cid and h.branchId=:bid and h.status != 'D' and h.gateInId=:gate and "
			+ "h.containerNo=:con and h.igmTransId=:igmtrans and h.igmNo=:igm and h.igmLineNo=:line and h.docRefNo=:doc and h.hldSrNo=:sr")
	HoldDetails getSingleData(@Param("cid") String cid,@Param("bid") String bid,@Param("gate") String gate,@Param("con") String con,
			@Param("igmtrans") String igmtrans,@Param("igm") String igm,@Param("line") String line,@Param("doc") String doc,
			@Param("sr") int sr);
	
	@Query(value="select COUNT(h)>0 from HoldDetails h where h.companyId=:cid and h.branchId=:bid and h.status != 'D' and h.gateInId=:gate and "
			+ "h.containerNo=:con and h.igmTransId=:igmtrans and h.igmNo=:igm and h.holdStatus = 'R' ")
	Boolean checkContainerHoldStatus(@Param("cid") String cid,@Param("bid") String bid,@Param("gate") String gate,@Param("con") String con,
			@Param("igmtrans") String igmtrans,@Param("igm") String igm);

}
