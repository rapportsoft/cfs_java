package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.Profitcentre;

import jakarta.transaction.Transactional;



public interface ProfitcentreRepository extends JpaRepository<Profitcentre, String> {

	
	@Query(value="SELECT p FROM Profitcentre p WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.profitcentreId = :profitcentreId AND p.status != 'D'")
     Profitcentre getAllDataByID(@Param("companyId") String companyId, @Param("branchId") String branchId,@Param("profitcentreId") String profitcentreId);
	
//	@Query("SELECT New com.cwms.entities.Profitcenter(p.profitcentreId, p.profitcentreDesc) FROM Profitcentre p WHERE p.companyId = :companyId AND p.branchId = :branchId "
//			+ "AND p.profitcentreId = :profitcentreId AND p.status != 'D'")
//    Profitcentre getAllDataByID1(@Param("companyId") String companyId, @Param("branchId") String branchId,@Param("profitcentreId") String profitcentreId);
//
//	
	@Query("SELECT p FROM Profitcentre p "
	        + "WHERE p.companyId = :cid AND p.branchId = :bid AND p.status != 'D' "
	        + "AND (:vessel IS NULL OR :vessel = '' OR p.vesselMandatory LIKE CONCAT('%', :vessel ,'%'))"
	        + "AND (:jo IS NULL OR :jo = '' OR p.joMandatory LIKE CONCAT('%', :jo ,'%'))"
	        + "AND (:con IS NULL OR :con = '' OR p.containerMandatory LIKE CONCAT('%', :con ,'%'))"
	        + "AND (:name IS NULL OR :name = '' OR p.profitcentreDesc LIKE CONCAT('%', :name ,'%'))")
	public List<Profitcentre> search(@Param("cid") String cid, @Param("bid") String bid,@Param("vessel") String vessel,@Param("jo") String jo,@Param("con") String con,@Param("name") String name);
	
	@Query("SELECT p FROM Profitcentre p "
	        + "WHERE p.companyId = :cid AND p.branchId = :bid AND p.status != 'D' "
	        + "AND (:vessel IS NULL OR :vessel = '' OR p.vesselMandatory LIKE CONCAT('%', :vessel ,'%'))"
	        + "AND (:jo IS NULL OR :jo = '' OR p.joMandatory LIKE CONCAT('%', :jo ,'%'))"
	        + "AND (:con IS NULL OR :con = '' OR p.containerMandatory LIKE CONCAT('%', :con ,'%'))"
	        + "AND (:name IS NULL OR :name = '' OR p.profitcentreDesc LIKE CONCAT('%', :name ,'%')) order by p.profitcentreId desc")
	public List<Profitcentre> search1(@Param("cid") String cid, @Param("bid") String bid,@Param("vessel") String vessel,@Param("jo") String jo,@Param("con") String con,@Param("name") String name);

	
	
	@Modifying
    @Transactional
    @Query("UPDATE Profitcentre p SET p.status = 'D' WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.profitcentreId = :profitcentreId")
    int updateStatusToD(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profitcentreId") String profitcentreId);
	
	
	
	@Query(value = "SELECT NEW com.cwms.entities.Profitcentre(i.profitcentreId, i.profitcentreDesc) " + "FROM Profitcentre i " +
			"WHERE i.companyId = :companyId " + "AND i.branchId = :branchId "
			+ "AND i.status <> 'D'")
	List<Profitcentre> getProfitcenters(@Param("companyId") String companyId, @Param("branchId") String branchId);
}
