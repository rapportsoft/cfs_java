package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Vessel;

public interface VesselRepository extends JpaRepository<Vessel, String> {

	@Query(value="select New com.cwms.entities.Vessel(v.vesselId, v.vesselName, p.portName, j.jarDtlDesc, v.status) "
			+ "from Vessel v LEFT JOIN Port p ON v.companyId=p.companyId and v.branchId=p.branchId and v.portOfRegistration=p.portCode "
			+ "LEFT JOIN JarDetail j ON v.companyId=j.companyId and v.vesselFlag=j.jarDtlId where v.companyId=:cid and v.branchId=:bid and v.status != 'D' "
			+ "AND (:vname is null OR :vname = '' OR v.vesselName like CONCAT ('%', :vname ,'%')) order BY v.approvedDate desc")
	List<Vessel> search(@Param("cid") String cid,@Param("bid") String bid,@Param("vname") String vname);
	
	@Query(value="select v from Vessel v where v.companyId=:cid and v.branchId=:bid and v.vesselId=:vid")
	Vessel getData(@Param("cid") String cid,@Param("bid") String bid,@Param("vid") String vid);
	
	@Query(value="select New com.cwms.entities.Vessel(v.vesselId, v.vesselName) from Vessel v where v.companyId=:cid and v.branchId=:bid and v.status != 'D'")
	List<Vessel> getVesselData(@Param("cid") String cid,@Param("bid") String bid);
	
	
	@Query(value="select v.vesselId,v.vesselName from Vessel v where v.companyId=:cid and v.branchId=:bid and v.status != 'D'")
	List<Object[]> getData(@Param("cid") String cid,@Param("bid") String bid);
	
	
	@Query(value="select v.vesselId,v.vesselName from Vessel v where v.companyId=:cid and v.branchId=:bid "
			+ "and v.status != 'D' and (:val is null OR :val = '' OR v.vesselName like CONCAT(:val,'%'))")
	List<Object[]> getData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
