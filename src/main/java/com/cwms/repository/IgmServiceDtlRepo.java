package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.IgmServiceDtl;

public interface IgmServiceDtlRepo extends JpaRepository<IgmServiceDtl, String> {

	@Query(value="select COUNT(i)>0 from IgmServiceDtl i where i.companyId=:cid and i.branchId=:bid and i.status='A' and i.igmTransId=:trans "
			+ "and i.igmNo=:igm and i.igmLineNo=:line and i.serviceId=:service")
	boolean isExistTheServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line,@Param("service") String service);
	
	@Query(value="select i from IgmServiceDtl i where i.companyId=:cid and i.branchId=:bid and i.status='A' and i.igmTransId=:trans "
			+ "and i.igmNo=:igm and i.igmLineNo=:line and i.containerNo=:con and i.serviceId=:service")
	IgmServiceDtl getDataByServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line,@Param("con") String con,@Param("service") String service);
	
	@Query(value="select DISTINCT i.igmTransId,i.igmNo,i.igmLineNo,i.serviceId,s.serviceShortDesc,i.percentage,i.amount,i.remark "
			+ "from IgmServiceDtl i "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where i.companyId=:cid and i.branchId=:bid and i.status='A' and i.igmTransId=:trans "
			+ "and i.igmNo=:igm and i.igmLineNo=:line")
	List<Object[]> getDataByIgmDtls(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("igm") String igm,
			@Param("line") String line);
}
