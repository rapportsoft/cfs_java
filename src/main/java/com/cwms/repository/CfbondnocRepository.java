package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.CfBondNocDtl;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.Party;
import com.cwms.entities.PartyAddress;

import jakarta.transaction.Transactional;

public interface CfbondnocRepository extends JpaRepository<Cfbondnoc, String> {
	// Additional query methods (if needed) can be defined here

	
	
	@Query("SELECT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName, p.partyType, "
			+ "p.customerCode, pa.state, pa.srNo, p.bondnocWeek,pa.address1,pa.address2,pa.address3) "
			+ "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId =pa.companyId AND p.branchId =pa.branchId AND p.partyId =pa.partyId "
			+ "WHERE p.companyId = :companyId AND p.branchId=:branchId AND p.status != 'D' AND p.cha ='Y' AND pa.status != 'D' AND "
			+ " ((:partyName IS NULL OR :partyName = '' OR p.partyName LIKE concat (:partyName,'%')) OR "
			+ "(:partyName IS NULL OR :partyName = '' OR pa.gstNo LIKE concat (:partyName,'%')) OR "
			+ "(:partyName IS NULL OR :partyName = '' OR p.partyId LIKE concat (:partyName,'%')) )")
	List<Party> findPartyDTOByPartyId(@Param("companyId") String companyId, @Param("branchId") String branchId,@Param("partyName") String partyName);
	
	
	
	
//	 @Query("SELECT c FROM Party c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status !='D' AND"
//	 		+ "((:partyName IS NULL OR :partyName ='' OR c.partyName LIKE concat (:partyName,'%')) OR "
//	 		+ "(:partyName IS NULL OR :partyName ='' OR c.partyId LIKE concat (:partyName)))")
//	List<Party> getAllPartyAsFoeworder(@Param("companyId") String companyId, @Param("branchId") String branchId,@Param("partyName") String partyName);
//	

	
	
	
	
	@Query("SELECT c FROM Party c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.status != 'D' " +
		       "AND (:partyName IS NULL OR :partyName = '' OR c.partyName LIKE concat(:partyName, '%'))")
		List<Party> getAllPartyAsFoeworder(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId, 
		                                   @Param("partyName") String partyName);

	 
	 
	 
	
	 @Query("Select c.partyId ,c.partyName,c.partyType from Party c where c.companyId =:companyId and c.branchId=:branchId and c.partyId=:partyId")
	 Object[] getForworderData(@Param ("companyId") String companyId,
			 @Param ("branchId") String branchId,
			 @Param ("partyId") String partyId);
	 
	
	 
	@Query("SELECT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName, p.partyType, " +
		       "pa.gstNo, pa.state, pa.srNo, p.bondnocWeek) " +
		       "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId " +
		       "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.partyId = :partyId AND pa.gstNo = :gstNo " +
		       "AND p.status <> 'D' AND p.cha = 'Y' AND pa.status <> 'D'")
		Party getDataByPartyIdAndGstNo(@Param("companyId") String companyId, 
		                               @Param("branchId") String branchId, 
		                               @Param("partyId") String partyId, 
		                               @Param("gstNo") String gstNo);
	
	
	@Query("SELECT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.partyName, p.partyType, " +
		       "pa.gstNo, pa.state, pa.srNo, p.bondnocWeek) " +
		       "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId " +
		       "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.partyId = :partyId AND pa.gstNo = :gstNo AND pa.srNo = :sr " +
		       "AND p.status <> 'D' AND p.cha = 'Y' AND pa.status <> 'D'")
		Party getDataByPartyIdAndGstNo1(@Param("companyId") String companyId, 
		                               @Param("branchId") String branchId, 
		                               @Param("partyId") String partyId, 
		                               @Param("gstNo") String gstNo,
		                               @Param("sr") String sr);
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.iecCode, p.partyName, pa.address1, "
	        + "pa.address2, pa.address3, pa.state, pa.gstNo, pa.srNo) "
	        + "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId "
	        + "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.status != 'D' AND p.imp = 'Y' AND pa.status != 'D' AND "
	        + "(:partyName IS NULL OR :partyName = '' OR "
	        + "p.partyName LIKE concat(:partyName, '%') OR "
	        + "pa.gstNo LIKE concat(:partyName, '%') OR "
	        + "p.iecCode LIKE concat(:partyName, '%') OR "
	        + "pa.state LIKE concat(:partyName, '%') OR "
	        + "p.partyId LIKE concat(:partyName, '%'))")
	List<Party> findImporterDataByPartyId(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("partyName") String partyName);

	
	
	
	
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.iecCode, p.partyName, pa.address1, "
	        + "pa.address2, pa.address3, pa.state, pa.gstNo, pa.srNo) "
	        + "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId "
	        + "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.status != 'D' AND p.lin = 'Y' AND pa.status != 'D' AND "
	        + "(:partyName IS NULL OR :partyName = '' OR "
	        + "p.partyName LIKE concat(:partyName, '%') OR "
	        + "pa.gstNo LIKE concat(:partyName, '%') OR "
	        + "p.iecCode LIKE concat(:partyName, '%') OR "
	        + "pa.state LIKE concat(:partyName, '%') OR "
	        + "p.partyId LIKE concat(:partyName, '%'))")
	List<Party> getAllShippingLine(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("partyName") String partyName);

	
	
	
	
	
	
	@Query("SELECT DISTINCT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.iecCode, p.partyName, pa.address1, "
	        + "pa.address2, pa.address3, pa.state, pa.gstNo, pa.srNo) "
	        + "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId "
	        + "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.status != 'D' AND pa.status != 'D' AND "
	        + "(:partyName IS NULL OR :partyName = '' OR "
	        + "p.partyName LIKE concat(:partyName, '%') OR "
	        + "pa.gstNo LIKE concat(:partyName, '%') OR "
	        + "p.iecCode LIKE concat(:partyName, '%') OR "
	        + "pa.state LIKE concat(:partyName, '%') OR "
	        + "p.partyId LIKE concat(:partyName, '%'))")
	List<Party> getAllAccountHolder(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("partyName") String partyName);

	
	@Query("SELECT NEW com.cwms.entities.PartyAddress(pa.companyId, pa.branchId, pa.partyId, pa.srNo, pa.address1, "
	        + "pa.address2, pa.address3, pa.city, pa.gstNo) "
	        + "FROM PartyAddress pa "
	        + "WHERE pa.companyId = :companyId AND pa.branchId = :branchId AND pa.partyId = :partyId AND pa.status != 'D'")
	List<PartyAddress> findPartyAddress(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("partyId") String partyId);

	
	
	@Query("SELECT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.iecCode, p.partyName, pa.address1, "
	        + "pa.address2, pa.address3, pa.state, pa.gstNo, pa.srNo) "
	        + "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId "+
	        "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.partyId = :partyId AND pa.gstNo = :gstNo " +
	        "AND p.status != 'D' AND p.imp = 'Y' AND pa.status != 'D'")
	Party getDataOfImporter(@Param("companyId") String companyId, 
            @Param("branchId") String branchId, 
            @Param("partyId") String partyId, 
            @Param("gstNo") String gstNo);
	
	@Query("SELECT NEW com.cwms.entities.Party(p.companyId, p.branchId, p.partyId, p.iecCode, p.partyName, pa.address1, "
	        + "pa.address2, pa.address3, pa.state, pa.gstNo, pa.srNo) "
	        + "FROM Party p LEFT OUTER JOIN PartyAddress pa ON p.companyId = pa.companyId AND p.branchId = pa.branchId AND p.partyId = pa.partyId "+
	        "WHERE p.companyId = :companyId AND p.branchId = :branchId AND p.partyId = :partyId AND pa.gstNo = :gstNo " +
	        "AND p.status != 'D' AND pa.srNo=:sr AND p.imp = 'Y' AND pa.status != 'D'")
	Party getDataOfImporter1(@Param("companyId") String companyId, 
            @Param("branchId") String branchId, 
            @Param("partyId") String partyId, 
            @Param("gstNo") String gstNo,@Param("sr") String sr);
	
	
	
	
	
	
	@Query(value="select COUNT(c)>0 from Cfbondnoc c where c.companyId=:cid and c.branchId=:bid and c.boeNo=:boeNo and c.status !='D'")
	Boolean isDataExist(@Param("cid") String cid,@Param("bid") String bid,@Param("boeNo") String boeNo);
	
	
	
	@Query(value="select COUNT(c)>0 from Cfbondnoc c where c.companyId=:cid and c.branchId=:bid and c.boeNo=:boeNo "
			+ "and c.nocTransId!=:nocTransId and c.status !='D'")
	Boolean isDataExist1(@Param("cid") String cid,@Param("bid") String bid,@Param("boeNo") String boeNo,@Param("nocTransId") String nocTransId);
	
	

	
//	@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId,c.nocTransDate, c.profitcentreId, c.nocNo, " +
//		       "c.nocDate, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
//		       "c.igmLineNo, c.boeNo, c.boeDate, c.importerId, c.importerName, c.importerAddress1, " +
//		       "c.importerAddress2, c.importerAddress3, c.grossWeight, c.nocValidityDate, c.nocFromDate, " +
//		       "c.licenceValidDate, c.uom, c.nocPackages, c.area, c.cifValue, c.cargoDuty, " +
//		       "c.insuranceValue, c.insuranceAmt, c.status,c.createdBy,c.approvedBy,c.cha) " +
//		   "FROM Cfbondnoc c " +
//		   "WHERE c.companyId = :companyId " +
//		   "AND c.branchId = :branchId " +
//		   "AND c.status != 'D' AND "
//		   + " ((:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat (:partyName,'%')) OR "
//			+ "(:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat (:partyName,'%')) OR "
//			+ "(:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat (:partyName,'%')) )")
//		List<Cfbondnoc> findCfbondnocByCompanyIdAndBranchId(@Param("companyId") String companyId, 
//		                                                     @Param("branchId") String branchId,
//		                                                     @Param("partyName") String partyName);
	
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.nocTransDate, c.profitcentreId, c.nocNo, " +
		       "c.nocDate, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
		       "c.igmLineNo, c.boeNo, c.boeDate, c.importerId, c.importerName, c.importerAddress1, " +
		       "c.importerAddress2, c.importerAddress3, c.grossWeight, c.nocValidityDate, c.nocFromDate, " +
		       "c.licenceValidDate, c.uom, c.nocPackages, c.area, c.cifValue, c.cargoDuty, " +
		       "c.insuranceValue, c.insuranceAmt, c.status, c.createdBy,c.editedBy,c.approvedBy, c.cha,c.chaCode,c.nocWeek,c.gateInPackages,c.noOf20ft, c.noOf40ft,c.numberOfMarks,c.sourcePort,c.vesselId ) " +
		   "FROM Cfbondnoc c " +
		   "WHERE c.companyId = :companyId " +
		   "AND c.branchId = :branchId " +
		   "AND c.status != 'D' " +
		   "AND ((:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat (:partyName, '%')) " +
		       "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat (:partyName, '%')) " +
		       "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat (:partyName, '%'))) " +
		   "ORDER BY c.nocTransId DESC")
		List<Cfbondnoc> findCfbondnocByCompanyIdAndBranchId(@Param("companyId") String companyId, 
		                                                     @Param("branchId") String branchId,
		                                                     @Param("partyName") String partyName);

//	
//	@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId,c.nocTransDate, c.profitcentreId, c.nocNo, " +
//		       "c.nocDate, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
//		       "c.igmLineNo, c.boeNo, c.boeDate, c.importerId, c.importerName, c.importerAddress1, " +
//		       "c.importerAddress2, c.importerAddress3, c.grossWeight, c.nocValidityDate, c.nocFromDate, " +
//		       "c.licenceValidDate, c.uom, c.nocPackages, c.area, c.cifValue, c.cargoDuty, " +
//		       "c.insuranceValue, c.insuranceAmt, c.status,pa.partyName,c.approvedBy,c.cha,pa.customerCode,c.nocWeek,c.gateInPackages) " +
//		   "FROM Cfbondnoc c " +
//	       "LEFT OUTER JOIN Party pa ON c.companyId =pa.companyId AND c.branchId =pa.branchId AND c.cha =pa.partyId "+
//		   "WHERE c.companyId = :companyId " +
//		   "AND c.branchId = :branchId " +
//		   "AND c.nocTransId = :nocTransId " +
//		   "AND c.nocNo = :nocNo " +
//		   "AND c.status != 'D'")
//		Cfbondnoc findCfbondnocByCompanyIdAndBranchIdOrSerach(@Param("companyId") String companyId, 
//		                                                     @Param("branchId") String branchId,
//		                                                     @Param("nocTransId") String nocTransId, 
//		                                                     @Param("nocNo") String nocNo);
	
	


	@Query("SELECT c FROM Cfbondnoc c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
	Cfbondnoc getDataOfDtlId(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("nocTransId") String nocTransId, 
	    @Param("nocNo") String nocNo
	);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.nocTransDate, c.nocNo, " +
		       "c.nocDate, c.source, c.igmTransId, c.igmNo, c.igmDate, c.igmLineNo, c.boeNo, c.boeDate, " +
		       "pa.partyName, c.chaCode, c.haz, cd.typeOfPackage, c.importerName, c.importerAddress1, c.importerAddress2, " +
		       "c.importerAddress3, cd.commodityDescription, c.grossWeight, c.nocValidityDate, c.nocFromDate, c.uom, " +
		       "c.nocPackages, c.cifValue, c.cargoDuty, cbl.cusAppCargoDuty, c.storedCragoDuty, c.storedCifValue, " +
		       "c.status, c.nocWeek, c.noOf20ft, c.noOf40ft, c.spaceType, c.gateInType,c.area) " +
		       "FROM Cfbondnoc c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		       "LEFT OUTER JOIN Cfbondinsbal cbl ON c.companyId = cbl.companyId AND c.branchId = cbl.branchId " +
		       "LEFT OUTER JOIN CfBondNocDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.nocTransId = :nocTransId " +
		       "AND c.nocNo = :nocNo " +
		       "AND c.status != 'D'")
	List<Cfbondnoc> findCfbondnocByCompanyIdAndBranchIdAndNocTransIdAndNocNo(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("nocTransId") String nocTransId, 
		    @Param("nocNo") String nocNo
		);

	
	
	
	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.nocTransDate, c.profitcentreId, c.nocNo, " +
		       "c.nocDate, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
		       "c.igmLineNo, c.boeNo, c.boeDate, c.importerId, c.importerName, c.importerAddress1, " +
		       "c.importerAddress2, c.importerAddress3, cd.grossWeight, c.nocValidityDate, c.nocFromDate, " +
		       "c.licenceValidDate, c.uom, cd.nocPackages, c.area, c.cifValue, c.cargoDuty, " +
		       "c.insuranceValue, c.insuranceAmt, c.status, pa.partyName, cd.cfBondDtlId, c.cha,cd.commodityDescription,cd.gateInPackages,cd.typeOfPackage) " +
		   "FROM Cfbondnoc c " +
		   "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		   "LEFT OUTER JOIN CfBondNocDtl cd ON c.companyId = cd.companyId AND c.branchId = cd.branchId AND c.nocTransId = cd.nocTransId AND c.nocNo = cd.nocNo " +
		   "WHERE c.companyId = :companyId " +
		   "AND c.branchId = :branchId " +
		   "AND c.status != 'D' " +
		   "AND (cd.nocPackages - cd.gateInPackages > 0) " +
		   "AND ((:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat (:partyName, '%')) " +
		       "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat (:partyName, '%')) " +
		       "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat (:partyName, '%'))) " +
		   "ORDER BY c.nocTransId DESC")
		List<Cfbondnoc> findCfbondnocByCompanyIdAndBranchIdForCfbondGateIn(@Param("companyId") String companyId, 
		                                                     @Param("branchId") String branchId,
		                                                     @Param("partyName") String partyName);

	@Modifying
	@Transactional
	@Query("UPDATE Cfbondnoc c SET c.gateInPackages = :gateInPackages WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId=:nocTransId AND c.nocNo=:nocNo")
	int updateCfBondNoc(
	    @Param("gateInPackages") BigDecimal gateInPackages,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("nocTransId") String nocTransId,
	    @Param("nocNo") String nocNo
	);
	
	
	
	 @Query("SELECT c FROM Cfbondnoc c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
	 Cfbondnoc findCfBondNoc(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo
	    );
	 
	 
	 
	 @Query("SELECT c FROM Cfbondnoc c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
	 Cfbondnoc findCfBondNocForUpdationg(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo
	    );
	 
	 
	 
	 
	 // query fpr getting data of trasnid for In Bond Screen 
//	 @Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.profitcentreId, c.nocTransDate, " +
//		       "c.nocNo, c.nocDate, c.shift, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
//		       "c.igmLineNo, c.boeNo, c.boeDate, c.shippingAgent, c.shippingLine, c.chaSrNo, pa.partyName, c.chaCode, c.haz, " +
//		       "c.periodicBill, c.typeOfPackage, c.billingParty, c.importerId, c.importerName, c.importerAddress1, " +
//		       "c.importerAddress2, c.importerAddress3, c.accSrNo, c.onAccountOf, c.commodityDescription, " +
//		       "c.commodityCode, c.grossWeight, c.sampleQty, c.nocValidityDate, c.nocFromDate, c.licenceValidDate, " +
//		       "c.numberOfMarks, c.uom, c.nocPackages, c.gateInPackages, c.area, c.newArea, c.cifValue, c.imoCode, " +
//		       "c.cargoDuty, c.insuranceValue, c.insuranceAmt, c.storedCragoDuty, c.storedCifValue, c.inBondedPackages, " +
//		       "c.noticeDate, c.comments, c.status, c.createdBy, c.editedBy, c.approvedBy, c.nocWeek, c.dcaNo, c.sapNo, " +
//		       "c.jobNo, c.sourcePort, c.noOf20ft, c.noOf40ft, c.spaceAllocated, c.cargoSource, c.balCifValue, " +
//		       "c.spaceType, c.gateInType) " +
//		   "FROM Cfbondnoc c " +
//		   "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
//		   "WHERE c.companyId = :companyId " +
//		   "AND c.branchId = :branchId " +
//		   "AND c.status != 'D' " +
////		   "AND (c.gateInPackages - c.inBondedPackages) > 0 " +
//"AND (c.gateInPackages - COALESCE(c.inBondedPackages, 0)) > 0 " +
//		   "AND ((:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat (:partyName, '%')) " +
//		       "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat (:partyName, '%')) " +
//		       "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat (:partyName, '%'))) " +
//		   "ORDER BY c.nocTransId DESC")
//		List<Cfbondnoc> findCfbondnocByCompanyIdAndBranchIdForInbondScreen(@Param("companyId") String companyId, 
//		                                                     @Param("branchId") String branchId,
//		                                                     @Param("partyName") String partyName);

	 
	  // correcting this for the autocomplete search using boeNo 
	 @Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.profitcentreId, c.nocTransDate, " +
		       "c.nocNo, c.nocDate, c.shift, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
		       "c.igmLineNo, c.boeNo, c.boeDate, c.shippingAgent, c.shippingLine, c.chaSrNo, c.cha, pa.customerCode, c.haz, " +
		       "c.periodicBill, c.typeOfPackage, c.billingParty, c.importerId, c.importerName, c.importerAddress1, " +
		       "c.importerAddress2, c.importerAddress3, c.accSrNo, c.onAccountOf, c.commodityDescription, " +
		       "c.commodityCode, c.grossWeight, c.sampleQty, c.nocValidityDate, c.nocFromDate, c.licenceValidDate, " +
		       "c.numberOfMarks, c.uom, c.nocPackages, c.gateInPackages, c.area, c.newArea, c.cifValue, c.imoCode, " +
		       "c.cargoDuty, c.insuranceValue, c.insuranceAmt, c.storedCragoDuty, c.storedCifValue, c.inBondedPackages, " +
		       "c.noticeDate, c.comments, c.status, c.createdBy, pa.partyName, c.approvedBy, c.nocWeek, c.dcaNo, c.sapNo, " +
		       "c.jobNo, c.sourcePort, c.noOf20ft, c.noOf40ft, c.spaceAllocated, c.cargoSource, c.balCifValue, " +
		       "c.spaceType, c.gateInType,c.bondingNo,c.bondingDate,c.bondValidityDate) " +
		   "FROM Cfbondnoc c " +
		   "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		   "WHERE c.companyId = :companyId " +
		   "AND c.branchId = :branchId " +
		   "AND c.status != 'D' " +
		   "AND (c.gateInPackages - COALESCE(c.inBondedPackages, 0)) > 0 " +
		   "AND (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE CONCAT(:partyName, '%')) " +  // Corrected LIKE clause
		   "ORDER BY c.boeNo DESC")
		List<Cfbondnoc> findCfbondnocByCompanyIdAndBranchIdForInbondScreen(@Param("companyId") String companyId, 
			                                                @Param("branchId") String branchId,
			                                                @Param("partyName") String partyName);

	 
	 @Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.profitcentreId, c.nocTransDate, " +
		       "c.nocNo, c.nocDate, c.shift, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
		       "c.igmLineNo, c.boeNo, c.boeDate, c.shippingAgent, c.shippingLine, c.chaSrNo, c.cha, pa.customerCode, c.haz, " +
		       "c.periodicBill, c.typeOfPackage, c.billingParty, c.importerId, c.importerName, c.importerAddress1, " +
		       "c.importerAddress2, c.importerAddress3, c.accSrNo, c.onAccountOf, c.commodityDescription, " +
		       "c.commodityCode, c.grossWeight, c.sampleQty, c.nocValidityDate, c.nocFromDate, c.licenceValidDate, " +
		       "c.numberOfMarks, c.uom, c.nocPackages, c.gateInPackages, c.area, c.newArea, c.cifValue, c.imoCode, " +
		       "c.cargoDuty, c.insuranceValue, c.insuranceAmt, c.storedCragoDuty, c.storedCifValue, c.inBondedPackages, " +
		       "c.noticeDate, c.comments, c.status, c.createdBy, pa.partyName, c.approvedBy, c.nocWeek, c.dcaNo, c.sapNo, " +
		       "c.jobNo, c.sourcePort, c.noOf20ft, c.noOf40ft, c.spaceAllocated, c.cargoSource, c.balCifValue, " +
		       "c.spaceType, c.gateInType,c.bondingNo,c.bondingDate,c.bondValidityDate) " +
		   "FROM Cfbondnoc c " +
		   "LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
		   "WHERE c.companyId = :companyId " +
		   "AND c.branchId = :branchId " +
		   "AND c.status != 'D' " +
		   "AND (c.gateInPackages - COALESCE(c.inBondedPackages, 0)) > 0 " +
		   "AND c.nocTransId = :nocTransId " +
		   "AND c.nocNo = :nocNo " +
		   "AND c.boeNo = :boeNo " +
		   "ORDER BY c.boeNo DESC")
		Cfbondnoc  dataOfBoeNoForNeeEntry(@Param("companyId") String companyId, 
			                                                @Param("branchId") String branchId,
			                                                @Param("nocTransId") String nocTransId, 
			                                                @Param("nocNo") String nocNo,
			                                                @Param("boeNo") String boeNo);
	 
	 
	 

		@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId,c.nocTransDate, c.profitcentreId, c.nocNo, " +
			       "c.nocDate, c.source, c.gateInId, c.igmTransId, c.igmNo, c.igmDate, " +
			       "c.igmLineNo, c.boeNo, c.boeDate, c.importerId, c.importerName, c.importerAddress1, " +
			       "c.importerAddress2, c.importerAddress3, c.grossWeight, c.nocValidityDate, c.nocFromDate, " +
			       "c.licenceValidDate, c.uom, c.nocPackages, c.area, c.cifValue, c.cargoDuty, " +
			       "c.insuranceValue, c.insuranceAmt, c.status,c.createdBy,pa.partyName,c.approvedBy,c.cha,pa.customerCode,c.nocWeek,c.gateInPackages,c.noOf20ft, c.noOf40ft,c.numberOfMarks,c.sourcePort,v.vesselName) " +
			   "FROM Cfbondnoc c " +
		       "LEFT OUTER JOIN Party pa ON c.companyId =pa.companyId AND c.branchId =pa.branchId AND c.cha =pa.partyId "+
		       "LEFT OUTER JOIN Vessel v ON c.companyId =v.companyId AND c.branchId =v.branchId AND c.vesselId =v.vesselId "+
			   "WHERE c.companyId = :companyId " +
			   "AND c.branchId = :branchId " +
			   "AND c.nocTransId = :nocTransId " +
			   "AND c.nocNo = :nocNo " +
			   "AND c.status != 'D'")
			Cfbondnoc findCfbondnocByCompanyIdAndBranchIdOrSerach(@Param("companyId") String companyId, 
			                                                     @Param("branchId") String branchId,
			                                                     @Param("nocTransId") String nocTransId, 
			                                                     @Param("nocNo") String nocNo);
		
		
	 
	 
		
		
//		@Modifying
//		@Transactional
//		@Query("UPDATE Cfbondnoc c SET c.inBondedPackages = :inBondedPackages,c.inbondGrossWt=:inbondGrossWt,c.inbondInsuranceValue=:inbondInsuranceValue,c.inbondCifValue=:inbondCifValue,c.inbondCargoDuty=:inbondCargoDuty,c.bondingNo=:bondingNo,c.bondingDate=:bondingDate,c.bondValidityDate WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId=:nocTransId AND c.nocNo=:nocNo")
//		int updateCfBondNocAfterInBonding(
//		    @Param("inBondedPackages") BigDecimal inBondedPackages,
//		    @Param("inbondGrossWt") BigDecimal inbondGrossWt,
//		    @Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
//		    @Param("inbondCifValue") BigDecimal inbondCifValue,
//		    @Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
//		    @Param("bondingNo") String bondingNo,
//		    @Param("bondingDate") Date bondingDate,
//		    @Param("bondValidityDate") Date bondValidityDate,
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,
//		    @Param("nocTransId") String nocTransId,
//		    @Param("nocNo") String nocNo
//		);

		
		
		
		
		@Modifying
		@Transactional
		@Query("UPDATE Cfbondnoc c SET c.inBondedPackages = :inBondedPackages, " +
		       "c.inbondGrossWt = :inbondGrossWt, c.inbondInsuranceValue = :inbondInsuranceValue, " +
		       "c.inbondCifValue = :inbondCifValue, c.inbondCargoDuty = :inbondCargoDuty, " +
		       "c.bondingNo = :bondingNo, c.bondingDate = :bondingDate, c.bondValidityDate = :bondValidityDate " +
		       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
		int updateCfBondNocAfterInBonding(
		    @Param("inBondedPackages") BigDecimal inBondedPackages,
		    @Param("inbondGrossWt") BigDecimal inbondGrossWt,
		    @Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
		    @Param("inbondCifValue") BigDecimal inbondCifValue,
		    @Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
		    @Param("bondingNo") String bondingNo,
		    @Param("bondingDate") Date bondingDate,
		    @Param("bondValidityDate") Date bondValidityDate,
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("nocTransId") String nocTransId,
		    @Param("nocNo") String nocNo
		);

		
		
		
		
		
		
		
		 @Query(value = "select DISTINCT c.boeNo, c.nocNo, c.nocTransId, c.nocTransDate, c.cha, p.partyName,c.igmNo,c.igmLineNo,c.importerId,c.boeDate,c.igmNo,c.nocDate,c.nocPackages,c.gateInPackages " +
                 "from Cfbondnoc c " +
                 "LEFT OUTER JOIN Party p ON c.companyId = p.companyId and c.branchId = p.branchId and c.cha = p.partyId " +
                 "where c.companyId = :cid and c.branchId = :bid " +
                 "AND (c.nocPackages - COALESCE(c.gateInPackages, 0)) > 0 " +
                 "and c.status != 'D' " +
                 "and (:val is null OR :val = '' OR c.boeNo LIKE CONCAT(:val, '%'))")
  List<Object[]> getAllBoeNoFromNoc(@Param("cid") String cid, 
                                    @Param("bid") String bid, 
                                    @Param("val") String val);
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  @Query(value = "select c.cfBondDtlId, c.commodityDescription, c.nocPackages, c.gateInPackages, c.qtyTakenIn, c.grossWeight, c.weightTakenIn,c.typeOfPackage " +
          "from CfBondNocDtl c " +
          "where c.companyId = :cid and c.branchId = :bid and c.boeNo = :boeNo and c.nocTransId = :nocTransId " +
          "and c.status != 'D' " +
          "AND (c.nocPackages - c.gateInPackages > 0) " +
          "and (:val is null OR :val = '' OR c.commodityDescription LIKE CONCAT(:val, '%'))")
List<Object[]> getCommodityDtlFromNocDtl(@Param("cid") String cid, 
                                    @Param("bid") String bid, 
                                    @Param("boeNo") String boeNo, 
                                    @Param("nocTransId") String nocTransId, 
                                    @Param("val") String val);

 
  
  
  
@Query(value = "select DISTINCT c.nocNo ,c.boeNo, c.nocTransId, c.nocTransDate, c.cha, p.partyName,c.igmNo,c.igmLineNo,c.importerId,c.boeDate,c.igmNo,c.nocDate " +
        "from Cfbondnoc c " +
        "LEFT OUTER JOIN Party p ON c.companyId = p.companyId and c.branchId = p.branchId and c.cha = p.partyId " +
        "where c.companyId = :cid and c.branchId = :bid " +
        "and c.status != 'D' " +
        "and (:val is null OR :val = '' OR c.nocNo LIKE CONCAT(:val, '%'))")
List<Object[]> getAllNocNoForBondingSearch(@Param("cid") String cid, 
                           @Param("bid") String bid, 
                           @Param("val") String val);


@Query(value = "select DISTINCT c.bondingNo, c.boeNo, c.nocTransId, c.nocTransDate, c.cha, " +
        "c.igmNo, c.igmLineNo, c.importerId, c.boeDate, c.nocDate " +
        "from Cfbondnoc c " +
        "where c.companyId = :cid and c.branchId = :bid " +
        "and c.status != 'D' " +
        "and (:val is null OR :val = '' OR c.bondingNo LIKE CONCAT(:val, '%'))")
List<Object[]> getAllBondingNoForBondingSearch(@Param("cid") String cid, 
                           @Param("bid") String bid, 
                           @Param("val") String val);




//@Query(value = "SELECT c.nocNo, c.boeNo, c.nocTransId, c.nocTransDate, c.cha, " +
//        "c.igmNo, c.igmLineNo, c.importerId, c.boeDate, c.nocDate " +
//        "FROM Cfbondnoc c " +
//        "WHERE c.companyId = :cid AND c.branchId = :bid " +
//        "AND c.status != 'D' " +
//        "AND ((:nocNo IS NULL OR :nocNo = '' OR c.nocNo LIKE CONCAT(:nocNo, '%')) " +
//        "AND (:boeNo IS NULL OR :boeNo = '' OR c.boeNo LIKE CONCAT(:boeNo, '%')) " +
//        "AND (:bondingNo IS NULL OR :bondingNo = '' OR c.bondingNo LIKE CONCAT(:bondingNo, '%')))")
//List<Object[]> getForBondingSearch(@Param("cid") String cid, 
//                            @Param("bid") String bid, 
//                            @Param("nocNo") String nocNo,
//                            @Param("boeNo") String boeNo,
//                            @Param("bondingNo") String bondingNo);




@Query("SELECT NEW com.cwms.entities.Cfbondnoc(c.companyId, c.branchId, c.nocTransId, c.nocNo, c.gateInId, c.igmNo, c.boeNo, " +
        "c.boeDate, c.cha, c.importerId, c.importerName, c.bondingNo, c.bondingDate, c.bondValidityDate) " +
"FROM Cfbondnoc c " +
"LEFT OUTER JOIN Party pa ON c.companyId = pa.companyId AND c.branchId = pa.branchId AND c.cha = pa.partyId " +
"WHERE c.companyId = :companyId " +
"AND c.branchId = :branchId " +
"AND c.status != 'D' " +
"AND ((:nocNo IS NULL OR :nocNo = '' OR c.nocNo LIKE CONCAT(:nocNo, '%')) " +
"AND (:boeNo IS NULL OR :boeNo = '' OR c.boeNo LIKE CONCAT(:boeNo, '%')) " +
"AND (:bondingNo IS NULL OR :bondingNo = '' OR c.bondingNo LIKE CONCAT(:bondingNo, '%')))")
Cfbondnoc getForBondingSearch(@Param("companyId") String companyId, 
                                               @Param("branchId") String branchId,
                                               @Param("nocNo") String nocNo,
                                               @Param("boeNo") String boeNo,
                                               @Param("bondingNo") String bondingNo);








@Modifying
@Transactional
@Query("UPDATE Cfbondnoc c SET c.inBondedPackages = :inBondedPackages, " +
       "c.inbondGrossWt = :inbondGrossWt, c.inbondInsuranceValue = :inbondInsuranceValue, " +
       "c.inbondCifValue = :inbondCifValue, c.inbondCargoDuty = :inbondCargoDuty, " +
       "c.bondingNo = :bondingNo, c.bondingDate = :bondingDate, c.bondValidityDate = :bondValidityDate , c.boeNo = :boeNo , c.boeDate = :boeDate ,"
       + "c.cha =:cha ,c.chaCode=:chaCode ,c.importerId =:importerId ,c.importerName =:importerName , c.importerAddress1=:importerAddress1 ,c.importerAddress2=:importerAddress2 ,c.importerAddress3=:importerAddress3 " +
       "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
int updateCfBondNocAfterAuditTrail(
    @Param("inBondedPackages") BigDecimal inBondedPackages,
    @Param("inbondGrossWt") BigDecimal inbondGrossWt,
    @Param("inbondInsuranceValue") BigDecimal inbondInsuranceValue,
    @Param("inbondCifValue") BigDecimal inbondCifValue,
    @Param("inbondCargoDuty") BigDecimal inbondCargoDuty,
    @Param("bondingNo") String bondingNo,
    @Param("bondingDate") Date bondingDate,
    @Param("bondValidityDate") Date bondValidityDate,
    @Param("boeNo") String boeNo,
    @Param("boeDate") Date boeDate,
    
    @Param("cha") String cha,
    @Param("chaCode") String chaCode,
    @Param("importerId") String importerId,
    @Param("importerName") String importerName,
    @Param("importerAddress1") String importerAddress1,
    @Param("importerAddress2") String importerAddress2,
    @Param("importerAddress3") String importerAddress3,
    
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("nocTransId") String nocTransId,
    @Param("nocNo") String nocNo
);


@Modifying
@Transactional
@Query("UPDATE Cfbondnoc c "
       + "SET c.bondingNo = :bondingNo, "
       + "c.bondingDate = :bondingDate, "
       + "c.bondValidityDate = :bondValidityDate, "
       + "c.boeNo = :boeNo, "
       + "c.boeDate = :boeDate, "
       + "c.cha = :cha, "
       + "c.chaCode = :chaCode, "
       + "c.importerId = :importerId, "
       + "c.importerName = :importerName, "
       + "c.importerAddress1 = :importerAddress1, "
       + "c.importerAddress2 = :importerAddress2, "
       + "c.importerAddress3 = :importerAddress3 "
       + "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo")
int updateCfBondNocAfterAuditTrailForHeaderChange(
    @Param("bondingNo") String bondingNo,
    @Param("bondingDate") Date bondingDate,
    @Param("bondValidityDate") Date bondValidityDate,
    @Param("boeNo") String boeNo,
    @Param("boeDate") Date boeDate,
    @Param("cha") String cha,
    @Param("chaCode") String chaCode,
    @Param("importerId") String importerId,
    @Param("importerName") String importerName,
    @Param("importerAddress1") String importerAddress1,
    @Param("importerAddress2") String importerAddress2,
    @Param("importerAddress3") String importerAddress3,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("nocTransId") String nocTransId,
    @Param("nocNo") String nocNo
);





@Query(value = "select c.boeNo, c.bondingNo, c.nocTransId " +
        "from Cfbondnoc c " +
        "where c.companyId = :cid and c.branchId = :bid and c.status = 'A' " +
        "and (:val is null OR :val = '' OR c.boeNo LIKE CONCAT('%', :val, '%') OR c.bondingNo LIKE CONCAT('%', :val, '%'))")
List<Object[]> getDataBeforeAssessment(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);



@Query(value="select c.nocTransId,c.profitcentreId,p.profitcentreDesc,c.nocNo,c.nocDate,c.insuranceValue,c.cargoDuty,"
 		+ "c.importerId,c.importerName,c.impSrNo,c.importerAddress1,c.importerAddress2,c.importerAddress3,pa1.gstNo,"
 		+ "c.cha,p1.partyName,c.chaSrNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,GROUP_CONCAT(dtl.commodityDescription),c.nocPackages,c.area,"
 		+ "c.grossWeight,c.insuranceValue,c.noOf20ft,c.noOf40ft,c.nocFromDate,c.nocValidityDate,"
 		+ "CEIL(DATEDIFF(c.nocValidityDate, c.nocFromDate) / 7),c.ssrTransId,c.invoiceUptoDate,c.assesmentId,c.invoiceNo,c.lastAssesmentDate,c.invoiceDate,"
 		+ "(select COALESCE(SUM(g.qtyTakenOut),0) from GateOut g where g.companyId=:cid and g.branchId=:bid and g.erpDocRefNo=:trans and g.status='A')  "
  		+ "from Cfbondnoc c "
		+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
		+ "LEFT OUTER JOIN PartyAddress pa1 ON c.companyId=pa1.companyId and c.branchId=pa1.branchId and c.importerId=pa1.partyId and c.impSrNo=CAST(pa1.srNo AS INTEGER) "
		+ "LEFT OUTER JOIN Party p1 ON c.companyId=p1.companyId and c.branchId=p1.branchId and c.cha=p1.partyId "
		+ "LEFT OUTER JOIN PartyAddress pa2 ON c.companyId=pa2.companyId and c.branchId=pa2.branchId and c.cha=pa2.partyId and c.chaSrNo=CAST(pa2.srNo AS INTEGER) "
		+ "LEFT OUTER JOIN CfBondNocDtl dtl ON c.companyId=dtl.companyId and c.branchId=dtl.branchId and c.nocTransId=dtl.nocTransId and "
		+ "c.nocNo=dtl.nocNo and dtl.status = 'A' and c.boeNo=dtl.boeNo "
  		+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.nocTransId=:trans and c.boeNo=:boe group by c.nocTransId,c.nocNo")
  List<Object[]> getSelectedDataBeforeAssessment(@Param("cid") String cid,@Param("bid") String bid,@Param("trans") String trans,
		  @Param("boe") String boe);
  
  
  @Query("SELECT c FROM Cfbondnoc c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId "
    		+ "AND c.nocNo = :nocNo and c.status = 'A'")
	Cfbondnoc getDataByNocAndTrans(
	    @Param("companyId") String companyId, 
	    @Param("branchId") String branchId, 
	    @Param("nocTransId") String nocTransId, 
	    @Param("nocNo") String nocNo
	);
    
  @Transactional
  @Modifying
  @Query(value="UPDATE Cfbondnoc c SET c.invoiceAssesed='Y',c.invoiceNo=:invNo,c.invoiceDate=CURRENT_DATE,"
  		+ "c.invoiceUptoDate=:uptoDate,c.billAmt=:billAmt,c.invoiceAmt=:invAmt,c.creditType=:ctype "
  		+ "where c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId "
  		+ "AND c.nocNo = :nocNo and c.status = 'A'")
  int updateInvoiceData( @Param("companyId") String companyId, 
	  	    @Param("branchId") String branchId, 
	  	    @Param("nocTransId") String nocTransId, 
	  	    @Param("nocNo") String nocNo,@Param("invNo") String invNo,@Param("uptoDate") Date uptoDate,
	  	    @Param("billAmt") BigDecimal billAmt,@Param("invAmt") BigDecimal invAmt,@Param("ctype") String ctype);

  
  
  @Transactional
  @Modifying
  @Query(value="UPDATE Cfbondnoc c SET c.lastInvoiceNo=:invNo,c.lastInvoiceDate=CURRENT_DATE,c.lastInvoiceAssesed='Y',"
  		+ "c.lastBillAmt=:billAmt,c.lastInvoiceAmt=:invAmt,c.lastCreditType=:ctype "
  		+ "where c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId "
  		+ "AND c.nocNo = :nocNo and c.status = 'A'")
  int updateInvoiceData1( @Param("companyId") String companyId, 
	  	    @Param("branchId") String branchId, 
	  	    @Param("nocTransId") String nocTransId, 
	  	    @Param("nocNo") String nocNo,@Param("invNo") String invNo,
	  	    @Param("billAmt") BigDecimal billAmt,@Param("invAmt") BigDecimal invAmt,@Param("ctype") String ctype);

}
