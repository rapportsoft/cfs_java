package com.cwms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CFSTariffService;
import java.util.*;

public interface CFSTarrifServiceRepository extends JpaRepository<CFSTariffService, String> {

	@Query(value="select DISTINCT NEW com.cwms.entities.CFSTariffService(s.serviceId,s.rate) from CFSTariffService s where "
			+ "s.companyId=:cid and s.branchId=:bid and s.status = 'A' and s.cfsTariffNo='CFS1000001' and s.rangeType='NA'")
	List<CFSTariffService> getGeneralTarrifData(@Param("cid") String cid,@Param("bid") String bid);
}
