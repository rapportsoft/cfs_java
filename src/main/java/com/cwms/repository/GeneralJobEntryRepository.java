package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
	
	// Invoice queries

		@Query(value = "select DISTINCT c.jobTransId, c.jobNo, c.boeNo " + "from GenerelJobEntry c "
				+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and "
				+ "(select (COALESCE(r.receivedPackages,0) - COALESCE(r.deliveredPackages,0)) from GeneralReceivingCrg r "
				+ "where r.companyId=:cid and r.branchId=:bid and r.jobNo=c.jobNo and "
				+ "r.jobTransId=c.jobTransId and r.boeNo=c.boeNo and r.status='A' group by r.jobTransId, r.jobNo, r.boeNo) > 0 and "
				+ "(:val is null OR :val = '' OR c.boeNo LIKE CONCAT(:val,'%'))")
		List<Object[]> getDataBeforeAssessment(@Param("cid") String cid, @Param("bid") String bid,
				@Param("val") String val);

		@Query(value = "select c.jobTransId,c.profitcentreId,p.profitcentreDesc,c.jobNo,c.jobDate,COALESCE(SUM(r.cargoValue),0),COALESCE(SUM(r.cargoDuty),0),"
				+ "c.importerId,c.importerName,c.impSrNo,c.importerAddress1,c.importerAddress2,c.importerAddress3,pa1.gstNo,c.cha,p1.partyName,"
				+ "GROUP_CONCAT(dtl.commodityDescription),(COALESCE(SUM(r.receivedPackages),0) - COALESCE(SUM(r.deliveredPackages),0)),c.noOf20ft,c.noOf40ft,"
				+ "(COALESCE(r.areaOccupied,0) * (COALESCE(SUM(r.receivedPackages),0) - COALESCE(SUM(r.deliveredPackages),0)))/COALESCE(SUM(r.receivedPackages),0),c.grossWeight,c.invoiceUptoDate,c.assesmentId,c.invoiceNo,c.assesmentDate,c.invoiceDate,"
				+ "CEIL(DATEDIFF(CURRENT_TIMESTAMP, c.jobDate) / 7) from GenerelJobEntry c "
				+ "LEFT OUTER JOIN GeneralReceivingCrg r ON c.companyId=r.companyId and c.branchId=r.branchId and c.jobNo=r.jobNo and r.status='A' "
				+ "and c.jobTransId=r.jobTransId and c.boeNo=r.boeNo and (COALESCE(r.receivedPackages,0) - COALESCE(r.deliveredPackages,0)) > 0 "
				+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
				+ "LEFT OUTER JOIN PartyAddress pa1 ON c.companyId=pa1.companyId and c.branchId=pa1.branchId and c.importerId=pa1.partyId and c.impSrNo=CAST(pa1.srNo AS INTEGER) "
				+ "LEFT OUTER JOIN Party p1 ON c.companyId=p1.companyId and c.branchId=p1.branchId and c.cha=p1.partyId "
				+ "LEFT OUTER JOIN GeneralJobOrderEntryDetails dtl ON c.companyId=dtl.companyId and c.branchId=dtl.branchId and c.jobTransId=dtl.jobTransId and "
				+ "c.jobNo=dtl.jobNo and dtl.status = 'A' and c.boeNo=dtl.boeNo "
				+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.jobTransId=:trans and c.boeNo=:boe group by c.jobNo,c.jobTransId,c.boeNo")
		List<Object[]> getSelectedDataBeforeAssessment(@Param("cid") String cid, @Param("bid") String bid,
				@Param("trans") String trans, @Param("boe") String boe);
		
		@Query(value="select c from GenerelJobEntry c where c.companyId=:cid and c.branchId=:bid and c.jobTransId=:trans and c.status='A' "
				+ "and c.jobNo=:job")
		GenerelJobEntry getGeneralData(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("job") String job);
		
		@Transactional
		@Modifying
		@Query(value="Update GenerelJobEntry c SET c.invoiceAssesed='Y', c.assesmentId=:assId, c.invoiceNo=:invNo,"
				+ "c.invoiceDate=CURRENT_TIMESTAMP, c.assesmentDate=CURRENT_TIMESTAMP,c.invoiceUptoDate=:uptoDate "
				+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.jobTransId=:trans and c.jobNo=:job")
		int updateJobEntry(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,@Param("job") String job,
				@Param("assId") String assId,@Param("invNo") String invNo,@Param("uptoDate") Date uptoDate);
		
		
		@Query(value="select c.deliveryId,r.jobNo,r.jobTransId,c.boeNo "
				+ "from GeneralDeliveryCrg c "
				+ "LEFT OUTER JOIN GeneralReceivingCrg r ON c.companyId=r.companyId and c.branchId=r.branchId and "
				+ "c.receivingId=r.receivingId and c.boeNo=r.boeNo "
				+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and (c.invoiceAssesed is null OR c.invoiceAssesed='' OR "
				+ "c.invoiceAssesed='N') and (:val is null OR :val = '' OR c.boeNo LIKE CONCAT(:val,'%') OR c.deliveryId LIKE CONCAT(:val,'%'))")
		List<Object[]> getBeforeAssessmentDeliveryData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
		
		
	    @Query(value="select j.jobTransId,j.profitcentreId,p.profitcentreDesc,j.jobNo,j.jobDate,c.cargoValue,c.cargoDuty,"
	    		+ "j.importerId,j.importerName,j.impSrNo,j.importerAddress1,j.importerAddress2,j.importerAddress3,pa1.gstNo,j.cha,p1.partyName,"
	    		+ "GROUP_CONCAT(dtl.commodityDescription),c.receivedPackages,c.noOf20Ft,c.noOf40Ft,c.areaRemaining,c.remainingGw,j.invoiceUptoDate,c.deliveryId "
	    		+ "from GeneralDeliveryCrg c "
	    		+ "LEFT OUTER JOIN GeneralReceivingCrg r ON c.companyId=r.companyId and c.branchId=r.branchId and "
				+ "c.receivingId=r.receivingId and c.boeNo=r.boeNo "
	    		+ "LEFT OUTER JOIN GenerelJobEntry j ON r.companyId=j.companyId and r.branchId=j.branchId and r.jobNo=j.jobNo and r.jobTransId=j.jobTransId and "
	    		+ "r.jobNo=j.jobNo and j.status='A' "
	    		+ "LEFT OUTER JOIN Profitcentre p ON j.companyId=p.companyId and j.branchId=p.branchId and j.profitcentreId=p.profitcentreId "
				+ "LEFT OUTER JOIN PartyAddress pa1 ON j.companyId=pa1.companyId and j.branchId=pa1.branchId and j.importerId=pa1.partyId and j.impSrNo=CAST(pa1.srNo AS INTEGER) "
				+ "LEFT OUTER JOIN Party p1 ON j.companyId=p1.companyId and j.branchId=p1.branchId and j.cha=p1.partyId "
				+ "LEFT OUTER JOIN GeneralDeliveryDetails dtl ON c.companyId=dtl.companyId and c.branchId=dtl.branchId and c.deliveryId=dtl.deliveryId and "
				+ "c.receivingId=dtl.receivingId "
	    		+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.deliveryId=:val and (c.invoiceAssesed is null OR c.invoiceAssesed='' OR c.invoiceAssesed='N')")
		List<Object[]> getGeneralDeliveryBeforeSaveAssessmentData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);

		@Transactional
		@Modifying
		@Query(value="Update GeneralDeliveryCrg c SET c.invoiceAssesed='Y', c.assessmentId=:assId, c.invoiceNo=:invNo,"
				+ "c.invoiceDate=CURRENT_TIMESTAMP, c.assesmentDate=CURRENT_TIMESTAMP,c.invoiceUptoDate=:uptoDate "
				+ "where c.companyId=:cid and c.branchId=:bid and c.status='A' and c.deliveryId=:id")
		int updateDeliveryEntry(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id,
				@Param("assId") String assId,@Param("invNo") String invNo,@Param("uptoDate") Date uptoDate);

}
