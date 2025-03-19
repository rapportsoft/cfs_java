package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GeneralGatePassGrid;
import com.cwms.entities.JarDetail;
public interface GeneralGatePassGridRepo extends JpaRepository<GeneralGatePassGrid, String> {

	
	@Query("SELECT c from GeneralGatePassGrid c where c.companyId =:companyId and c.branchId=:branchId and c.deliveryId=:deliveryId and c.gatePassId=:gatePassId and c.srNo=:srNo and  c.status !='D'")
	GeneralGatePassGrid getToUpdateData(@Param ("companyId") String companyId,
			@Param ("branchId") String branchId ,
			@Param ("deliveryId") String deliveryId,
			@Param ("gatePassId") String gatePassId,
			@Param ("srNo") int srNo);
	
	@Query(value = "SELECT * FROM jar_detail WHERE jar_id =:jarid AND Status != 'd' and company_id=:cid " ,nativeQuery = true)
	List<JarDetail> findByID(@Param("jarid") String  jarid, @Param("cid") String  cid );
}