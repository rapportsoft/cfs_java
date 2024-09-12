package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import com.cwms.entities.Jar;
import com.cwms.entities.JarDetail;

@Repository
public interface JarDetailRepository extends JpaRepository<JarDetail, String> {

	@Query(value = "select * from jar_detail where jar_dtl_id=:id and company_id=:cid AND status != 'd' " ,nativeQuery = true)
	JarDetail findJarDetailByID(@Param("id")String id,@Param("cid") String cid );

	@Query(value="select * from jar_detail where company_id=:cid  AND status != 'd'",nativeQuery=true)
	public List<JarDetail>  getalldata(@Param("cid") String cid );


	@Query(value = "SELECT * FROM jar_detail WHERE jar_id =:jarid AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findByID(@Param("jarid") String  jarid, @Param("cid") String  cid );

	@Query(value = "SELECT * FROM jar_detail WHERE jar_id = 'J00009' AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findDataByID(@Param("cid") String  cid );
	
	@Query(value = "SELECT * FROM jar_detail WHERE jar_id = 'J00011' AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findDataByIDForSub(@Param("cid") String  cid );
	
	@Query(value = "SELECT * FROM jar_detail WHERE jar_id = 'J00012' AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findNSDLExpDataByID(@Param("cid") String  cid );
	
	@Query(value = "SELECT * FROM jar_detail WHERE jar_id = 'J00014' AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findInternalUser(@Param("cid") String  cid );
	
	
	@Query(value = "SELECT * FROM jar_detail WHERE jar_id = 'J00031' AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findNSDLIMPDataByID(@Param("cid") String  cid );
	
	@Query(value = "select j from JarDetail j where j.jarId =:jarid and j.jarDtlId=:id and j.companyId=:cid AND j.status != 'd'")
	JarDetail findJarDetailANDJarId(@Param("jarid") String  jarid,@Param("id")String id,@Param("cid") String cid );
	
	@Query(value = "select New com.cwms.entities.JarDetail(j.jarDtlId, j.jarId, j.jarDtlDesc, j.comments) from JarDetail j where j.companyId=:cid and j.jarId =:jarid AND j.status != 'D'")
	List<JarDetail> getDataByJarId(@Param("cid") String cid,@Param("jarid") String jarId);
	
	@Query(value = "SELECT j.jarDtlId,j.jarDtlDesc FROM JarDetail j WHERE j.jarId =:jarid  AND j.status != 'D' and j.companyId=:cid ")
	List<Object[]> findByJarId(@Param("cid") String  cid,@Param("jarid") String jarid );
	
	
	@Query("SELECT p.jarDtlId, p.jarDtlDesc FROM JarDetail p WHERE p.companyId = :companyId AND p.jarId = :jarId AND p.status <> 'D' ORDER BY p.jarDtlId")
	List<Object[]> getJarDtlById(@Param("companyId") String companyId, @Param("jarId") String jarId);
}