package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.GeneralJobOrderEntryDetails;
import com.cwms.entities.GenerelJobEntry;
import com.cwms.entities.Party;

public interface GeneralJobEntryRepository extends JpaRepository<GenerelJobEntry, String> {

	@Query("SELECT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName, p.partyType, "
			+ "p.customerCode, pa.state, pa.srNo, p.bondnocWeek,pa.address1,pa.address2,pa.address3) "
			+ "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId =pa.companyId AND p.branchId =pa.branchId AND p.partyId =pa.partyId "
			+ "WHERE p.companyId = :companyId AND p.branchId=:branchId AND p.status != 'D' AND p.frw ='Y' AND pa.status != 'D' AND "
			+ " ((:partyName IS NULL OR :partyName = '' OR p.partyName LIKE concat ('%',:partyName,'%')) OR "
			+ "(:partyName IS NULL OR :partyName = '' OR pa.gstNo LIKE concat (:partyName,'%')) OR "
			+ "(:partyName IS NULL OR :partyName = '' OR p.partyId LIKE concat (:partyName,'%')) )")
	List<Party> findPartyDTOByPartyId(@Param("companyId") String companyId, @Param("branchId") String branchId,@Param("partyName") String partyName);
	
	
	@Query(value="select COUNT(c)>0 from GenerelJobEntry c where c.companyId=:cid and c.branchId=:bid and c.jobNo=:jobNo and c.status !='D'")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("jobNo") String jobNo);
	
	
	@Query(value="select COUNT(c)>0 from GenerelJobEntry c where c.companyId=:cid and c.branchId=:bid and c.jobNo=:jobNo "
			+ "and c.jobTransId!=:nocTransId and c.status !='D'")
	Boolean isDataExist1(@Param("cid") String cid,@Param("bid") String bid,@Param("jobNo") String jobNo,@Param("nocTransId") String nocTransId);
	
	
	
	@Query("SELECT NEW com.cwms.entities.GenerelJobEntry(c.companyId, c.branchId, c.jobTransId, c.jobNo, c.profitcentreId, " +
		       "c.jobTransDate, c.jobDate, c.gateInId, c.boeNo, c.boeDate, c.cha, c.cargoType, c.impSrNo, c.importerId, " +
		       "c.importerName, c.importerAddress1, c.importerAddress2, c.importerAddress3, c.forwarder, c.grossWeight, " +
		       "c.area, c.noOfPackages, c.numberOfMarks, c.packageOrWeight, c.noOf20ft, c.noOf40ft, c.godownNo, " +
		       "c.approvedDate, c.createdBy, c.createdDate, c.approvedBy, c.comments, c.status) " +
		       "FROM GenerelJobEntry c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.jobTransId LIKE CONCAT('%',:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.jobNo LIKE CONCAT('%',:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.importerName LIKE CONCAT('%',:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE CONCAT('%',:partyName, '%'))) " +
		       "ORDER BY c.jobTransId DESC")
		List<GenerelJobEntry> findCfbondnocByCompanyIdAndBranchId(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("partyName") String partyName);
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.GeneralJobOrderEntryDetails( " +
		       "c.companyId, c.branchId, c.jobTransId, c.jobDtlTransId, c.jobNo, c.commodityId, " +
		       "c.jobTransDate, c.jobDate, c.boeNo, c.profitcentreId, c.typeOfPackage, c.commodityDescription, " +
		       "c.grossWeight, c.noOfPackages, c.areaOccupied, c.status) " +
		       "FROM GeneralJobOrderEntryDetails c " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.jobTransId = :jobTransId " +
		       "AND c.jobNo = :jobNo " +
		       "AND c.status != 'D' " +
		       "GROUP BY c.jobTransId, c.jobNo, c.jobDtlTransId")
		List<GeneralJobOrderEntryDetails> getCfBondNocDtlForNocScreen(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId, 
		        @Param("jobTransId") String jobTransId, 
		        @Param("jobNo") String jobNo);


	
	
	

	
	
	
	@Query("SELECT NEW com.cwms.entities.GenerelJobEntry( " +
		       "c.companyId, c.branchId, c.jobTransId, c.jobNo, c.profitcentreId, c.jobTransDate, c.jobDate, " +
		       "c.gateInId, c.boeNo, c.boeDate, c.cha, c.cargoType, c.impSrNo, c.importerId, c.importerName, " +
		       "c.importerAddress1, c.importerAddress2, c.importerAddress3, c.forwarder, c.grossWeight, c.area, " +
		       "c.noOfPackages, c.numberOfMarks, c.packageOrWeight, c.noOf20ft, c.noOf40ft, c.godownNo, " +
		       "c.approvedDate, c.createdBy, c.createdDate, c.approvedBy, c.comments, c.status, ca.partyName,pa.partyName) " +
		       "FROM GenerelJobEntry c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.forwarder = pa.partyId " +
		       "LEFT OUTER JOIN Party ca ON c.companyId = ca.companyId AND c.branchId = ca.branchId AND c.cha = ca.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.jobTransId = :jobTransId " +
		       "AND c.jobNo = :jobNo " +
		       "AND c.status != 'D'")
		GenerelJobEntry findCfbondnocByCompanyIdAndBranchIdOrSerach(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("jobTransId") String jobTransId, 
		        @Param("jobNo") String jobNo);
	
	
	
	
	
	
	
	
	@Query("SELECT c FROM GeneralJobOrderEntryDetails c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.jobDtlTransId = :jobDtlTransId AND c.jobTransId = :jobTransId AND c.jobNo = :jobNo")
	GeneralJobOrderEntryDetails getDataOfDtlId(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("jobDtlTransId") String jobDtlTransId, 
	    @Param("jobTransId") String jobTransId, 
	    @Param("jobNo") String jobNo
	);
	
	@Query("SELECT c FROM GeneralJobOrderEntryDetails c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.jobDtlTransId = :jobDtlTransId AND c.jobTransId = :jobTransId AND c.jobNo = :jobNo")
	GeneralJobOrderEntryDetails getDataOfDtlIdEdit(
			   @Param("companyId") String companyId, 
			    @Param("branchId") String branchId, 
			    @Param("jobDtlTransId") String jobDtlTransId, 
			    @Param("jobTransId") String jobTransId, 
			    @Param("jobNo") String jobNo
			);

}
