package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.CfsTarrifQuatation;

public interface CFSRepositaryQuatation extends JpaRepository<CfsTarrifQuatation, String>
{
	
	
	@Query("SELECT c " +
		       "FROM CfsTarrifQuatation c " + 
		       "WHERE c.companyId = :companyId " + 
		       "AND c.branchId = :branchId " +
		       "AND c.cfsTariffNo = :cfsTariffNo " +
		       "AND c.cfsAmndNo = :cfsAmndNo " +
		       "AND c.status = 'A'")
		CfsTarrifQuatation getSavedTariffToTransfer(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	
	@Query("SELECT new com.cwms.entities.CfsTarrifQuatation( " +
		       "c.cfsTariffNo, c.cfsAmndNo,c.contractName, pa.partyName, agent.partyName,line.partyName, ch.partyName, imp.partyName, fo.partyName) " +
		        "FROM CfsTarrifQuatation c " 
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
		CfsTarrifQuatation getSavedTariffAuditTrailReport(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	@Query("SELECT NEW com.cwms.entities.CfsTarrifQuatation(i.partyId, i.status, i.contractName, i.shippingLine,i.cha, i.importerId, i.exporterId, i.shippingAgent, i.forwarderId,i.consolerId, i.cfsTariffDate, i.cfsFromDate, i.cfsValidateDate) " +
		       "FROM CfsTarrifQuatation i " +
		       "WHERE " +
		       "i.companyId = :companyId " +
		       "AND i.cfsTariffNo = :cfsTariffNo AND i.cfsAmndNo = :cfsAmndNo " +
		       "AND i.branchId = :branchId " +
		       "AND i.ammendStatus = 'N' " +
		       "AND i.status = 'A' " )		     
		CfsTarrifQuatation getSavedTariffSubmit(@Param("companyId") String companyId, @Param("branchId") String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	
	@Query("SELECT new com.cwms.entities.CfsTarrifQuatation( " +
		       "c.companyId, c.branchId, c.finYear, c.profitCentreId, c.cfsTariffNo, c.cfsAmndNo, " +
		       "c.partyId, c.status, c.contractName, c.shippingLine, c.cha, " +
		       "c.importerId, c.exporterId, c.shippingAgent, c.forwarderId, c.consolerId, " +
		       "c.cfsTariffDate, c.cfsFromDate, c.cfsValidateDate, c.filePath, c.refTariffNo, " +
		       "c.comments, c.createdBy, c.createdDate, c.editedBy, c.editedDate, c.approvedBy, " +
		       "c.approvedDate, c.ammendStatus, c.nvoccTariff, c.offdocTariff, c.tariffType, " +
		       "c.refTariffId, c.refTariffAmndId, pa.partyName, agent.partyName, " +
		       "line.partyName, ch.partyName, imp.partyName, fo.partyName) " +
		       "FROM CfsTarrifQuatation c " 
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
		CfsTarrifQuatation getSavedTariff(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);

	
	@Query("SELECT NEW com.cwms.entities.CfsTarrifQuatation(i.cfsTariffNo, i.cfsAmndNo, i.status, i.ammendStatus) " +
		       "FROM CfsTarrifQuatation i " +
		       "WHERE " +
		       "i.companyId = :companyId " +
		       "AND i.cfsTariffNo = :cfsTariffNo AND i.cfsAmndNo = :cfsAmndNo " +
		       "AND i.branchId = :branchId " +
		       "AND i.status = 'A' " )		     
		CfsTarrifQuatation getSavedTariffValidateUpdatable(@Param("companyId") String companyId, @Param("branchId") String branchId, String cfsTariffNo, String cfsAmndNo);

	@Query("SELECT NEW com.cwms.entities.CfsTarrifQuatation(i.cfsTariffNo, i.cfsAmndNo, i.status, i.ammendStatus) " +
		       "FROM CfsTarrifQuatation i " +
		       "WHERE " +
		       "i.companyId = :companyId " +
		       "AND i.cfsTariffNo = :cfsTariffNo AND i.cfsAmndNo = :cfsAmndNo " +
		       "AND i.branchId = :branchId " +
		       "AND i.ammendStatus = 'N' " +
		       "AND i.status = 'A' " )		     
		CfsTarrifQuatation getSavedTariffValidateAmmendable(@Param("companyId") String companyId, @Param("branchId") String branchId, String cfsTariffNo, String cfsAmndNo);

	
	
	@Query("SELECT COUNT(e) > 0 FROM CfsTarrifQuatation e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.contractName = :contractName "
	        + "AND (:cfsTariffNo IS NULL OR e.cfsTariffNo <> :cfsTariffNo) " 
		    + "AND (:cfsAmndNo IS NULL OR :cfsAmndNo = '' OR e.cfsAmndNo <> :cfsAmndNo)	"   
	        + "AND e.status = 'A'")
	boolean existByCombosOfContractName(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("contractName") String contractName, String cfsTariffNo, String cfsAmndNo);
	
	
//	@Query("SELECT COUNT(e) > 0 FROM CfsTarrifQuatation e "
//	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
//	        + "AND ( (:consolerId IS NULL OR :consolerId = '' OR e.consolerId = :consolerId) "
//	        + "AND (:partyId IS NULL OR :partyId = '' OR e.partyId = :partyId ) "
//	        + "AND (:cha IS NULL OR :cha = '' OR e.cha = :cha )"
//	        + "AND (:shippingAgent IS NULL OR :shippingAgent = '' OR e.shippingAgent = :shippingAgent )"
//	        + "AND (:shippingLine IS NULL OR :shippingLine = '' OR e.shippingLine = :shippingLine )"
//	        + "AND (:forwarderId IS NULL OR :forwarderId = '' OR e.forwarderId = :forwarderId )"
//	        + "AND (:importerId IS NULL OR :importerId = '' OR e.importerId = :importerId ))"
//	        + "AND (:cfsTariffNo IS NULL OR e.cfsTariffNo <> :cfsTariffNo) " 
//		    + "AND (:cfsAmndNo IS NULL OR :cfsAmndNo = '' OR e.cfsAmndNo <> :cfsAmndNo)	"   
//	        + "AND e.status = 'A'")
//	boolean existByCombosOfParties(@Param("companyId") String companyId, @Param("branchId") String branchId,
//	                               @Param("consolerId") String consolerId, @Param("partyId") String partyId, @Param("cha") String cha,
//	                               @Param("shippingAgent") String shippingAgent, @Param("shippingLine") String shippingLine,
//	                               @Param("forwarderId") String forwarderId, @Param("importerId") String importerId, String cfsTariffNo, String cfsAmndNo);
//	
	
	
	@Query("SELECT COUNT(e) > 0 FROM CfsTarrifQuatation e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.consolerId = :consolerId "
	        + "AND e.partyId = :partyId "
	        + "AND e.cha = :cha "
	        + "AND e.shippingAgent = :shippingAgent "
	        + "AND e.shippingLine = :shippingLine "
	        + "AND e.forwarderId = :forwarderId "
	        + "AND e.importerId = :importerId "
	        + "AND (:cfsTariffNo IS NULL OR e.cfsTariffNo <> :cfsTariffNo) " 
		    + "AND (:cfsAmndNo IS NULL OR :cfsAmndNo = '' OR e.cfsAmndNo <> :cfsAmndNo)	"   
	        + "AND e.status = 'A'")
	boolean existByCombosOfParties(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                               @Param("consolerId") String consolerId, @Param("partyId") String partyId, @Param("cha") String cha,
	                               @Param("shippingAgent") String shippingAgent, @Param("shippingLine") String shippingLine,
	                               @Param("forwarderId") String forwarderId, @Param("importerId") String importerId, String cfsTariffNo, String cfsAmndNo);
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.CfsTarrifQuatation(i.cfsTariffNo, i.cfsAmndNo, i.contractName) " +
		       "FROM CfsTarrifQuatation i " +
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
		List<CfsTarrifQuatation> getSavedTariffSearch(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("type") String type
		);

	
	

	 @Query("SELECT c " +
		        "FROM CfsTarrifQuatation c " +
		        "WHERE " +		       
		        "c.companyId = :companyId " +
		        "AND c.branchId = :branchId AND c.cfsTariffNo = :cfsTariffNo AND c.cfsAmndNo = :cfsAmndNo AND status = 'A' ")		        		       
	    CfsTarrifQuatation getSavedTariffNotJoin(String companyId, String branchId, String cfsTariffNo, String cfsAmndNo);
	
	

	 
	 List<CfsTarrifQuatation> findByCompanyIdAndBranchId(String companyId, String branchId);
	 
	 @Query(value = "SELECT * FROM cfstdtrf WHERE cfs_tariff_no = ?1 AND company_id = ?2 AND branch_id = ?3", nativeQuery = true)
	    CfsTarrifQuatation findByCfsTariffNoAndCompanyIdAndBranchId(String cfsTariffNo, String companyId, String branchId);
	 
	 CfsTarrifQuatation findByCompanyIdAndBranchIdAndPartyId(String companyId, String branchId,String partyId);
	 
	 
	 

	 @Query(value="select c.cfsTariffNo "
	 		+ "from CfsTarrifQuatation c "
	 		+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.partyId IN :partyid and "
	 		+ "c.importerId IN :importer and c.cha IN :cha and c.forwarderId IN :forwarder and c.nvoccTariff='N' "
	 		+ "and c.offdocTariff='N' and c.tariffType = 'Standard' order by FIELD(c.partyId,:partyid1,''),FIELD(c.importerId,:importer1,''),"
	 		+ "FIELD(c.cha,:cha1,''),FIELD(c.forwarderId,:forwarder1,'') limit 1")
	 String getTarrifNo(@Param("cid") String cid,@Param("bid") String bid,@Param("partyid") List<String> partyid,
			 @Param("importer") List<String> importer,@Param("cha") List<String> cha,@Param("forwarder") List<String> forwarder,
			 @Param("partyid1") String partyid1,@Param("importer1") String importer1,@Param("cha1") String cha1,@Param("forwarder1") String forwarder1);
	 
	 
	 @Query(value="select c.serviceId "
	 		+ "from CFSTariffService c "
	 		+ "LEFT OUTER JOIN CfsTarrifQuatation t ON c.companyId=t.companyId and c.branchId=t.branchId and c.cfsTariffNo=t.cfsTariffNo "
	 		+ "and c.cfsAmendNo=t.cfsAmndNo where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and t.status='A' and "
	 		+ "c.cfsTariffNo=:tarrifNo and c.defaultChk='Y' group by c.serviceId order by c.serviceId")
	 List<String> getDefaultServiceId(@Param("cid") String cid,@Param("bid") String bid,@Param("tarrifNo") String tarrifNo);

}