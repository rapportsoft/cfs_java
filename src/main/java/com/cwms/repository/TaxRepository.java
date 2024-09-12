package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cwms.entities.Services;
import com.cwms.entities.Tax;
import com.cwms.entities.TaxId;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
	
	
	
	
	
	
	
	 @Query(value = "SELECT * FROM tax s WHERE s.Company_Id = ?1 AND s.Branch_Id = ?2 AND s.status <> 'D'", nativeQuery = true)
	    List<Tax> getALLTaxes(String companyId, String branchId);

	
}