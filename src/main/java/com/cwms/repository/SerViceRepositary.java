package com.cwms.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Party;
import com.cwms.entities.Services;

import jakarta.transaction.Transactional;


public interface SerViceRepositary extends JpaRepository<Services, String>
{	 
	 
	

	@Query(value = "SELECT new com.cwms.entities.Services(" +
            "s.serviceId, s.serviceShortDesc, s.criteriaType, s.companyId, s.branchId, s.serviceUnit, s.serviceUnit1) " +
            "FROM Services s " +            
            "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.status <> 'D' AND s.serviceShortDesc = :serviceShortDesc")
	Services getServiceForTariffUpload(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("serviceShortDesc") String serviceShortDesc);

	
	
	@Query(value = "SELECT new com.cwms.entities.Services(" +
            "s.serviceId, s.serviceShortDesc, s.criteriaType, j.jarDtlDesc) " +
            "FROM Services s " +
            "LEFT JOIN JarDetail j " +
            "ON s.companyId = j.companyId AND j.status <> 'D' AND j.jarId = 'J00024' AND j.jarDtlId = s.sacCode " +
            "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.status <> 'D' " +
            "ORDER BY s.serviceId DESC")
List<Services> getActiveServicesTariff(@Param("companyId") String companyId, 
                                    @Param("branchId") String branchId);

	
	@Query(value = "SELECT new com.cwms.entities.Services(" +
            "s.serviceId, s.serviceShortDesc, s.serviceLongDesc, s.serviceUnit, s.serviceUnit1, " +
            "s.serviceType, s.typeOfCharges, s.taxApplicable, s.taxId, s.commodity, s.rangeType, " +
            "s.criteriaType, j.jarDtlDesc, s.defaultChk) " +
            "FROM Services s " +
            "LEFT join  JarDetail j on s.companyId = j.companyId And s.sacCode = j.jarDtlId AND j.jarId = 'J00024' AND j.status <> 'D' " +
            "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.serviceId = :serviceId AND s.status <> 'D' " +
            "ORDER BY s.serviceId DESC")
Services getActiveService(String companyId, String branchId, String serviceId);
	
	
	@Query(value = "SELECT * FROM Services AS s WHERE s.Company_Id = :companyId AND s.Branch_Id = :branchId AND s.Service_Id = :serviceId  AND s.status <> 'D'", nativeQuery = true)
	public Services findByService_Id(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("serviceId") String serviceId);


	 @Query(value = "SELECT New com.cwms.entities.Services(s.serviceId, s.serviceShortDesc, s.serviceType, s.sacCode, s.status,"
	 		+ "	s.createdBy) FROM Services s "
	 		+ "WHERE s.companyId = ?1 AND s.branchId = ?2 AND s.status != 'D' order by s.serviceId desc ")
	    List<Services> getActiveServices(String companyId, String branchId);
	 
	 @Query("SELECT s FROM Services s WHERE s.serviceId NOT IN :excludedServiceIds AND s.companyId = :companyId AND s.branchId = :branchId AND s.status <> 'D'")
	    List<Services> findServicesNotInIdsAndCompanyAndBranch(  @Param("companyId") String companyId, @Param("branchId") String branchId,  @Param("excludedServiceIds") List<String> excludedServiceIds);
	 
	 
	 @Query(value="select p from Services p where p.companyId=:cid and p.branchId=:bid and p.serviceId=:serviceId and p.status != 'D'")
	 Services getDataById(@Param("cid") String cid, @Param("bid") String bid, @Param("serviceId") String serviceId);
	 
//	 List<Services> findByCompanyIdAndBranchIdAndserviceShortDescription(String companyId,String branchId,String shortDesc);
	 
//	 @Query("SELECT s.taxPercentage FROM Services s WHERE s.companyId = :companyId " +
//		       "AND s.branchId = :branchId " +
//		       "AND s.serviceId = :serviceId " +
//		       "AND s.status <> 'D'")
//		String findTaxPercentage(
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,
//		    @Param("serviceId") String serviceId
//		);
	 
	 
	 
	 
	 @Query(value = "SELECT New com.cwms.entities.Services(s.serviceId, s.serviceShortDesc, s.serviceUnit) FROM Services s "
		 		+ "WHERE s.companyId = ?1 AND s.branchId = ?2 AND s.status != 'D' order by s.serviceId desc ")
		    List<Services> getActiveServices1(String companyId, String branchId);
	 
	 
	 
	 @Modifying
	    @Transactional
	    @Query("UPDATE Services p SET p.status = 'D' WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.serviceId = :serviceId")
	    int updateStatusToD(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("serviceId") String serviceId);
}