package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.Jar;
import com.cwms.entities.JarId;
@Repository
public interface JarRepository extends JpaRepository<Jar, String> {


	@Query(value="select * from jar where company_id=:cid AND status != 'd'",nativeQuery=true)
	public List<Jar> getAlldata(@Param("cid") String cid);
	
	@Query(value="select * from jar where company_id=:cid AND status != 'd' order by Jar_Id desc",nativeQuery=true)
	public List<Jar> getAlldata1(@Param("cid") String cid);
	
	@Query(value="select * from jar where jar_id=:jid and company_id=:cid ",nativeQuery=true)
	public Jar findByjarid(@Param("jid") String jid,@Param("cid") String cid);
	
	@Query(value="select COUNT(j)>0 from Jar j where j.jarType=:jid and j.companyId=:cid ")
	public Boolean isExist(@Param("jid") String jid,@Param("cid") String cid);
	
	@Query(value="select COUNT(j)>0 from Jar j where j.jarType=:jid and j.companyId=:cid and j.jarId=:id")
	public Boolean isExist1(@Param("jid") String jid,@Param("id") String id,@Param("cid") String cid);
}