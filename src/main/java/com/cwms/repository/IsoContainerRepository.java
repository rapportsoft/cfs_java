package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.IsoContainer;
import com.cwms.entities.IsoContainerId;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;

@Repository
public interface IsoContainerRepository extends JpaRepository<IsoContainer, IsoContainerId> {

//	List<IsoContainer> findByCompanyId(String companyId);
//	
//	List<IsoContainer> findByCompanyIdAndStatusNot(String companyId, String status);
//	
	List<IsoContainer> findByCompanyIdAndStatusNot(String companyId, String status, Sort sort);
	
	@Query("SELECT NEW com.cwms.entities.IsoContainer(p.companyId, p.isoCode, p.containerType, p.containerSize, p.grossWeight, "
			+ "p.tareWeight) "
			+ "FROM IsoContainer p "
			+ "WHERE p.companyId = :companyId AND p.status != 'D' ")
	List<IsoContainer> findIsoContainersByCriteria1(@Param("companyId") String companyId);
	

	@Query(value = "SELECT * FROM isocontainer WHERE company_id = :companyId AND (container_size = :containerSize OR container_type = :containerType OR iso_code LIKE %:isoCode%) AND status <> 'D'", nativeQuery = true)
	List<IsoContainer> findByCompanyAndContainerSizeOrTypeOrIsoCode(@Param("companyId") String companyId,
			@Param("containerSize") String containerSize, @Param("containerType") String containerType,
			@Param("isoCode") String isoCode);
	
	@Query(value = "SELECT * FROM isocontainer i " + "WHERE i.company_id = :companyId " + "AND i.status <> 'D' "
			+ "AND (:isoCode IS NULL OR i.iso_code LIKE CONCAT('', :isoCode, '%')) "
			+ "AND (:containerSize IS NULL OR i.container_size LIKE CONCAT('', :containerSize, '%')) "
			+ "AND (:containerType IS NULL OR i.container_type LIKE CONCAT('', :containerType, '%'))", nativeQuery = true)
	List<IsoContainer> findIsoContainersByCriteria(@Param("companyId") String companyId,
			@Param("isoCode") String isoCode, @Param("containerSize") String containerSize,
			@Param("containerType") String containerType);
	
	@Query("SELECT NEW com.cwms.entities.IsoContainer(p.companyId, p.isoCode, p.containerType, p.containerSize, p.grossWeight, "
			+ "p.tareWeight) "
			+ "FROM IsoContainer p "
			+ "WHERE p.companyId = :companyId AND p.status != 'D' AND  "
			+ " ((:partyName IS NULL OR :partyName = '' OR p.isoCode LIKE concat (:partyName,'%')) OR "
			+ "(:partyName IS NULL OR :partyName = '' OR p.containerSize LIKE concat (:partyName,'%')) OR "
			+ "(:partyName IS NULL OR :partyName = '' OR p.containerType LIKE concat (:partyName,'%')) )")
	List<IsoContainer> findIsoContainersByCriteria(@Param("companyId") String companyId,@Param("partyName") String partyName);
	
	@Query(value = "SELECT i.iso_code FROM isocontainer i " +
            "WHERE i.company_id = :companyId " +
            "AND i.status <> 'D' " +
            "AND i.container_size = :containerSize " +
            "AND i.container_type = :containerType ", nativeQuery = true)
String findIsoCodeByCriteria(@Param("companyId") String companyId,
                          @Param("containerSize") String containerSize,
                          @Param("containerType") String containerType);
	
	
	
	@Query(value="select New com.cwms.entities.IsoContainer(i.isoCode, i.containerType, i.containerSize, i.tareWeight) from IsoContainer i "
			+ "where i.companyId=:cid and i.isoCode=:iso and i.status != 'D'")
	IsoContainer getDataByIsoCode(@Param("cid") String cid,@Param("iso") String iso);
	
	
	@Query("SELECT NEW com.cwms.entities.IsoContainer(p.companyId, p.isoCode, p.containerType, p.containerSize, p.grossWeight, p.tareWeight) "
	        + "FROM IsoContainer p "
	        + "WHERE p.companyId = :companyId "
	        + "AND p.isoCode = :isoCode "
	        + "AND p.status != 'D' ")
	IsoContainer getIsoById(
	        @Param("companyId") String companyId,
	        @Param("isoCode") String isoCode);

}
