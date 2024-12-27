package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.CfsTarrif;

public interface CFSRepositary extends JpaRepository<CfsTarrif, String>
{
	
	@Query("SELECT new com.cwms.entities.CfsTarrif( " +
		       "c.cfsTariffNo, c.cfsAmndNo,c.contractName, pa.partyName, agent.partyName,line.partyName, ch.partyName, imp.partyName, fo.partyName) " +
		        "FROM CfsTarrif c " 
		       + "LEFT JOIN Party agent ON c.companyId = agent.companyId AND c.branchId = agent.branchId AND c.shippingAgent = agent.partyId AND agent.status != 'D' AND c.shippingAgent IS NOT NULL AND c.shippingAgent <> '' "
		       + "LEFT JOIN Party line ON c.companyId = line.companyId AND c.branchId = line.branchId AND c.shippingLine = line.partyId AND line.status != 'D' AND c.shippingLine IS NOT NULL AND c.shippingLine <> '' "
		       + "LEFT JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.partyId = pa.partyId AND pa.status != 'D' AND c.partyId IS NOT NULL AND c.partyId <> '' "
		       + "LEFT JOIN Party fo ON c.companyId = fo.companyId AND c.branchId = fo.branchId AND c.forwarderId = fo.partyId AND fo.status != 'D' AND c.forwarderId IS NOT NULL AND c.forwarderId <> '' "
		       + "LEFT JOIN Party imp ON c.companyId = imp.companyId AND c.branchId = imp.branchId AND c.importerId = imp.partyId AND imp.status != 'D' AND c.importerId IS NOT NULL AND c.importerId <> '' "
		       + "LEFT JOIN Party ch ON c.companyId = ch.companyId AND c.branchId = ch.branchId AND c.cha = ch.partyId AND ch.status != 'D' AND c.cha IS NOT NULL AND c.cha <> '' "
		       + "WHERE c.companyId = :companyId " + 
		       "AND c.branchId = :branchId " +
		       "AND c.cfsTariffNo = :cfsTariffNo " +
		       "AND c.cfsAmndNo = :cfsAmndNo " +
		       "AND c.status = 'A'")
		CfsTarrif getSavedTariffAuditTrailReport(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	@Query("SELECT NEW com.cwms.entities.CfsTarrif(i.partyId, i.status, i.contractName, i.shippingLine,i.cha, i.importerId, i.exporterId, i.shippingAgent, i.forwarderId,i.consolerId, i.cfsTariffDate, i.cfsFromDate, i.cfsValidateDate) " +
		       "FROM CfsTarrif i " +
		       "WHERE " +
		       "i.companyId = :companyId " +
		       "AND i.cfsTariffNo = :cfsTariffNo AND i.cfsAmndNo = :cfsAmndNo " +
		       "AND i.branchId = :branchId " +
		       "AND i.ammendStatus = 'N' " +
		       "AND i.status = 'A' " )		     
		CfsTarrif getSavedTariffSubmit(@Param("companyId") String companyId, @Param("branchId") String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	
	@Query("SELECT new com.cwms.entities.CfsTarrif( " +
		       "c.companyId, c.branchId, c.finYear, c.profitCentreId, c.cfsTariffNo, c.cfsAmndNo, " +
		       "c.partyId, c.status, c.contractName, c.shippingLine, c.cha, " +
		       "c.importerId, c.exporterId, c.shippingAgent, c.forwarderId, c.consolerId, " +
		       "c.cfsTariffDate, c.cfsFromDate, c.cfsValidateDate, c.filePath, c.refTariffNo, " +
		       "c.comments, c.createdBy, c.createdDate, c.editedBy, c.editedDate, c.approvedBy, " +
		       "c.approvedDate, c.ammendStatus, c.nvoccTariff, c.offdocTariff, c.tariffType, " +
		       "c.refTariffId, c.refTariffAmndId, pa.partyName, agent.partyName, " +
		       "line.partyName, ch.partyName, imp.partyName, fo.partyName) " +
		       "FROM CfsTarrif c " 
		       + "LEFT JOIN Party agent ON c.companyId = agent.companyId AND c.branchId = agent.branchId AND c.shippingAgent = agent.partyId AND agent.status != 'D' AND c.shippingAgent IS NOT NULL AND c.shippingAgent <> '' "
		       + "LEFT JOIN Party line ON c.companyId = line.companyId AND c.branchId = line.branchId AND c.shippingLine = line.partyId AND line.status != 'D' AND c.shippingLine IS NOT NULL AND c.shippingLine <> '' "
		       + "LEFT JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.partyId = pa.partyId AND pa.status != 'D' AND c.partyId IS NOT NULL AND c.partyId <> '' "
		       + "LEFT JOIN Party fo ON c.companyId = fo.companyId AND c.branchId = fo.branchId AND c.forwarderId = fo.partyId AND fo.status != 'D' AND c.forwarderId IS NOT NULL AND c.forwarderId <> '' "
		       + "LEFT JOIN Party imp ON c.companyId = imp.companyId AND c.branchId = imp.branchId AND c.importerId = imp.partyId AND imp.status != 'D' AND c.importerId IS NOT NULL AND c.importerId <> '' "
		       + "LEFT JOIN Party ch ON c.companyId = ch.companyId AND c.branchId = ch.branchId AND c.cha = ch.partyId AND ch.status != 'D' AND c.cha IS NOT NULL AND c.cha <> '' "
		       + "WHERE c.companyId = :companyId " + 
		       "AND c.branchId = :branchId " +
		       "AND c.cfsTariffNo = :cfsTariffNo " +
		       "AND c.cfsAmndNo = :cfsAmndNo " +
		       "AND c.status = 'A'")
		CfsTarrif getSavedTariff(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);

	
	@Query("SELECT NEW com.cwms.entities.CfsTarrif(i.cfsTariffNo, i.cfsAmndNo, i.status, i.ammendStatus) " +
		       "FROM CfsTarrif i " +
		       "WHERE " +
		       "i.companyId = :companyId " +
		       "AND i.cfsTariffNo = :cfsTariffNo AND i.cfsAmndNo = :cfsAmndNo " +
		       "AND i.branchId = :branchId " +
		       "AND i.status = 'A' " )		     
		CfsTarrif getSavedTariffValidateUpdatable(@Param("companyId") String companyId, @Param("branchId") String branchId, String cfsTariffNo, String cfsAmndNo);

	@Query("SELECT NEW com.cwms.entities.CfsTarrif(i.cfsTariffNo, i.cfsAmndNo, i.status, i.ammendStatus) " +
		       "FROM CfsTarrif i " +
		       "WHERE " +
		       "i.companyId = :companyId " +
		       "AND i.cfsTariffNo = :cfsTariffNo AND i.cfsAmndNo = :cfsAmndNo " +
		       "AND i.branchId = :branchId " +
		       "AND i.ammendStatus = 'N' " +
		       "AND i.status = 'A' " )		     
		CfsTarrif getSavedTariffValidateAmmendable(@Param("companyId") String companyId, @Param("branchId") String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	@Query("SELECT COUNT(e) > 0 FROM CfsTarrif e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.contractName = :contractName "
	        + "AND (:cfsTariffNo IS NULL OR e.cfsTariffNo <> :cfsTariffNo) " 
		    + "AND (:cfsAmndNo IS NULL OR :cfsAmndNo = '' OR e.cfsAmndNo <> :cfsAmndNo)	"   
	        + "AND e.status = 'A'")
	boolean existByCombosOfContractName(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("contractName") String contractName, String cfsTariffNo, String cfsAmndNo);
	
	
	@Query("SELECT COUNT(e) > 0 FROM CfsTarrif e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND ( (:consolerId IS NULL OR :consolerId = '' OR e.consolerId = :consolerId) "
	        + "AND (:partyId IS NULL OR :partyId = '' OR e.partyId = :partyId ) "
	        + "AND (:cha IS NULL OR :cha = '' OR e.cha = :cha )"
	        + "AND (:shippingAgent IS NULL OR :shippingAgent = '' OR e.shippingAgent = :shippingAgent )"
	        + "AND (:shippingLine IS NULL OR :shippingLine = '' OR e.shippingLine = :shippingLine )"
	        + "AND (:forwarderId IS NULL OR :forwarderId = '' OR e.forwarderId = :forwarderId )"
	        + "AND (:importerId IS NULL OR :importerId = '' OR e.importerId = :importerId ))"
	        + "AND (:cfsTariffNo IS NULL OR e.cfsTariffNo <> :cfsTariffNo) " 
		    + "AND (:cfsAmndNo IS NULL OR :cfsAmndNo = '' OR e.cfsAmndNo <> :cfsAmndNo)	"   
	        + "AND e.status = 'A'")
	boolean existByCombosOfParties(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                               @Param("consolerId") String consolerId, @Param("partyId") String partyId, @Param("cha") String cha,
	                               @Param("shippingAgent") String shippingAgent, @Param("shippingLine") String shippingLine,
	                               @Param("forwarderId") String forwarderId, @Param("importerId") String importerId, String cfsTariffNo, String cfsAmndNo);
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.CfsTarrif(i.cfsTariffNo, i.cfsAmndNo, i.contractName) " +
		       "FROM CfsTarrif i " +
		       "WHERE " +
		       "i.companyId = :companyId " +		      
		       "AND i.branchId = :branchId " +
		       "AND i.status = 'A' " +
		       "AND (" +
		       "   (:type = 'A') " +
		       "   OR (:type = 'L' AND DATE(i.cfsValidateDate) >= CURRENT_DATE) " +
		       "   OR (:type = 'E' AND DATE(i.cfsValidateDate) < CURRENT_DATE)" +
		       ") " +
		       "ORDER BY i.contractName DESC")
		List<CfsTarrif> getSavedTariffSearch(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("type") String type
		);

	
	

	 @Query("SELECT c " +
		        "FROM CfsTarrif c " +
		        "WHERE " +		       
		        "c.companyId = :companyId " +
		        "AND c.branchId = :branchId AND c.cfsTariffNo = :cfsTariffNo AND c.cfsAmndNo = :cfsAmndNo AND status = 'A' ")		        		       
	    CfsTarrif getSavedTariffNotJoin(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);
	
	

	 
	 List<CfsTarrif> findByCompanyIdAndBranchId(String companyId, String branchId);
	 
	 @Query(value = "SELECT * FROM cfstdtrf WHERE cfs_tariff_no = ?1 AND company_id = ?2 AND branch_id = ?3", nativeQuery = true)
	    CfsTarrif findByCfsTariffNoAndCompanyIdAndBranchId(String cfsTariffNo, String companyId, String branchId);
	 
	 CfsTarrif findByCompanyIdAndBranchIdAndPartyId(String companyId, String branchId,String partyId);
	 

}