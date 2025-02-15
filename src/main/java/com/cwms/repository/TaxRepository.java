package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.Services;
import com.cwms.entities.Tax;
import com.cwms.entities.TaxId;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
	
	
	
	
	
	
	
	 @Query(value = "SELECT * FROM tax s WHERE s.Company_Id = ?1 AND s.Branch_Id = ?2 AND s.status <> 'D'", nativeQuery = true)
	    List<Tax> getALLTaxes(String companyId, String branchId);

	 @Query(value="select t.taxDesc,d.taxPerc,t.taxId "
		 		+ "from Tax t "
		 		+ "LEFT OUTER JOIN TaxDtl d ON t.companyId=d.companyId and t.taxId=d.taxId "
		 		+ "where t.companyId=:cid and t.branchId=:bid and t.status = 'A' and d.status = 'A'")
		 List<Object[]> getTaxData(@Param("cid") String cid,@Param("bid") String bid);
	
}