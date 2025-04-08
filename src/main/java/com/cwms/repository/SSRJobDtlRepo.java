package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.SSRJobDtl;

public interface SSRJobDtlRepo extends JpaRepository<SSRJobDtl, String> {

	@Query(value = "select DISTINCT NEW com.cwms.entities.SSRJobDtl(s.transId,s.containerNo) from SSRJobDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.containerNo=:con and s.status='A' and s.ssrFlag='N'")
	SSRJobDtl getExistingDataOfContainerNo(@Param("cid") String cid, @Param("bid") String bid,
			@Param("con") String con);

	@Modifying
	@Transactional
	@Query(value = "Update SSRJobDtl s SET s.status='D' where s.companyId=:cid and s.branchId=:bid "
			+ "and s.transId=:id and s.containerNo=:con and s.status='A' and s.ssrFlag='N'")
	int handleDuplicateContainer(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("con") String con);

	@Query(value = "select s from SSRJobDtl s where s.companyId=:cid and s.branchId=:bid "
			+ "and s.transId=:id and s.containerNo=:con and s.status='A' and s.ssrFlag='N'")
	List<SSRJobDtl> getDataByContainerNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("con") String con);
	
	@Query(value = "select s.serviceId from SSRJobDtl s where s.companyId=:cid and s.branchId=:bid "
			+ "and s.transId=:id and s.containerNo=:con and s.status='A' and s.ssrFlag='N'")
	List<String> getServiceDataByContainerNo(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("con") String con);
	
	
	@Query(value = "select s from SSRJobDtl s where s.companyId=:cid and s.branchId=:bid "
			+ "and s.transId=:id and s.containerNo=:con and s.serviceId=:service and s.status='A' and s.ssrFlag='N'")
	SSRJobDtl getDataByContainerNoAndServiceId(@Param("cid") String cid, @Param("bid") String bid, @Param("id") String id,
			@Param("con") String con,@Param("service") String service);
	

	
	@Query(value="select DISTINCT s.transId,s.docRefNo,s.igmLineNo,s.containerNo from SSRJobDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.status != 'D'  and "
			+ "(:val is null OR :val = '' OR s.transId LIKE CONCAT ('%',:val,'%') "
			+ "OR s.docRefNo LIKE CONCAT ('%',:val,'%') OR s.igmLineNo LIKE CONCAT ('%',:val,'%') "
			+ "OR s.containerNo LIKE CONCAT ('%',:val,'%')) order by s.transId desc")
	List<Object[]> getSearchData(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
	
	@Query(value="select s.transId,s.docRefNo,s.igmLineNo,s.containerNo,s.containerSize,a.serviceShortDesc,s.totalRate "
			+ "from SSRJobDtl s "
			+ "LEFT OUTER JOIN Services a ON s.companyId=a.companyId and s.branchId=a.branchId and s.serviceId=a.serviceId "
			+ "where s.companyId=:cid and s.branchId=:bid and s.transId=:val and s.status='A'")
	List<Object[]> getDataBySSRTransId(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);
}
