package com.cwms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Branch;

@EnableJpaRepositories
public interface BranchRepo extends JpaRepository<Branch, String> {
	
	@Query("SELECT NEW com.cwms.entities.Branch(i.companyId, i.branchId, i.branchName, i.address1, i.address2, i.GST_No, i.Pan_No, i.address3, i.city, i.state, i.country, i.pin, i.stdCode, i.phoneNo, i.cfsCode, c.Company_name) " +
		       "FROM Branch i " +
		       "LEFT JOIN Company c on i.companyId = c.Company_Id AND c.status <> 'D' " +
		       "WHERE i.companyId = :companyId AND i.branchId = :branchId AND i.status <> 'D'")
	Branch getCompleteCompanyAndBranch(@Param("companyId") String companyId, @Param("branchId") String branchId);

	
	
	
	@Query("SELECT NEW com.cwms.entities.Branch(i.companyId, i.branchId, i.branchName) " +
	           "FROM Branch i " +
	           "WHERE i.companyId = :companyId AND i.status <> 'D'")
	    List<Branch> getBranchesByCompany(@Param("companyId") String companyId);


	@Query(value="select * from branch b where b.company_id =:cid",nativeQuery=true)
	public List<Branch> findByCompanyId(@Param("cid") String cid);
	
	//public List<Branch> findByCompany_id(String company_id_company_id);
	
	@Query(value="select * from branch b where b.branch_id=:bid",nativeQuery=true)
	public Optional<Branch> findById(@Param("bid") String bid);
	
	
	@Query(value="select * from branch b where b.branch_id=:bid",nativeQuery=true)
	public Branch findByBranchId(@Param("bid") String bid);
	
	@Query(value="select * from branch b where b.company_id=:cid AND b.branch_id = :bid",nativeQuery=true)
	public Branch findByBranchIdWithCompanyId(@Param("cid") String cid,@Param("bid") String bid);
	
	@Query(value = "SELECT * FROM branch b WHERE b.company_id = :companyId AND b.branch_id = :branchId", nativeQuery = true)
    Branch findByCompanyIdAndBranchId(@Param("companyId") String companyId, @Param("branchId") String branchId);
	
	@Query(value = "SELECT DISTINCT company_id, branch_id FROM Branch", nativeQuery = true)
    List<Object[]> findDistinctCompanyIdAndBranchId();
	
    @Query(value="select New com.cwms.entities.Branch(b.companyId, b.branchId, b.branchName) from Branch b where b.companyId=:cid")
    List<Branch> getBranchByCompany(@Param("cid") String cid);
    
    
	@Query("SELECT i.invoiceRoundOff " +
		       "FROM Branch i " +
		       "WHERE i.companyId = :companyId AND i.branchId = :branchId AND i.status <> 'D'")
	String getInvoiceRoundOffStatus(@Param("companyId") String companyId, @Param("branchId") String branchId);
	
	
	@Query("SELECT i " +
		       "FROM Branch i " +
		       "WHERE i.companyId = :companyId AND i.branchId = :branchId AND i.status = 'A'")
	Branch getDataByCompanyAndBranch(@Param("companyId") String companyId, @Param("branchId") String branchId);
}
