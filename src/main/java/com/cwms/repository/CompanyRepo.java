package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Company;

@EnableJpaRepositories
public interface CompanyRepo extends JpaRepository<Company, String> {	
	
	@Query("SELECT NEW com.cwms.entities.Company(i.Company_Id, i.Company_name) " +
		       "FROM Company i " +
		       "WHERE i.status <> 'D'")
		List<Company> getAllCompanies();

	@Query(value="select * from company c where c.company_id=:id",nativeQuery=true)
	public Company findByCompany_Id(@Param("id") String id);
	
	@Query(value="select company_name from company c where c.company_id=:id",nativeQuery=true)
	public String findByCompany_Id1(@Param("id") String id);
}
