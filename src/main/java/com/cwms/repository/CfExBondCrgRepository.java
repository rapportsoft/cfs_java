package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cwms.entities.CfExBondCrg;
import com.cwms.entities.Cfinbondcrg;
import com.cwms.entities.CfinbondcrgHDR;

import jakarta.transaction.Transactional;

@Repository
public interface CfExBondCrgRepository extends JpaRepository<CfExBondCrg, String> {
	
    // You can add custom query methods if needed
	
	@Modifying
	@Transactional
	@Query("UPDATE Cfbondinsbal c SET c.exbondArea = :exbondArea, c.exbondCargoDuty = :exbondCargoDuty, c.exbondCifValue = :exbondCifValue WHERE c.companyId = :companyId AND c.branchId = :branchId")
	int updateCfbondinsbalAfterExbond(
	    @Param("exbondArea") BigDecimal exbondArea,
	    @Param("exbondCargoDuty") BigDecimal exbondCargoDuty,
	    @Param("exbondCifValue") BigDecimal exbondCifValue,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId
	);
	 
	
	@Modifying
	@Transactional
	@Query("UPDATE CfExBondCrg c SET c.qtyTakenOut = :qtyTakenOut  WHERE c.companyId = :companyId AND c.branchId = :branchId and c.exBondingId=:exBondingId and c.exBondBeNo=:exBondBeNo")
	int updateCfexbondAfterGatePass(
	    @Param("qtyTakenOut") BigDecimal qtyTakenOut,
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("exBondingId") String exBondingId,
	    @Param("exBondBeNo") String exBondBeNo
	);
	
	 @Query("SELECT c FROM CfExBondCrg c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo AND c.exBondingId = :exBondingId")
	 CfExBondCrg findExistingCfexbondCrg(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo,
	        @Param("exBondingId") String exBondingId
	    );
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.CfExBondCrg(c.companyId, c.branchId, c.finYear, c.exBondingId, c.exBondingDate, " +
		       "c.profitcentreId, c.nocTransId, c.nocNo, c.nocValidityDate, c.boeNo, c.bondingNo, c.bondingDate, c.exBondBeNo, " +
		       "c.exBondBeDate, c.inBondingId, c.inBondingDate, c.invoiceUptoDate, c.igmNo, p.partyName, c.exBondNo, c.exBondDate, " +
		       "c.giTransporterName, c.status) " +
		       "FROM CfExBondCrg c " +
		       "LEFT OUTER JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId AND c.cha = p.partyId " +
		       "WHERE c.companyId = :companyId " +
		       "AND c.branchId = :branchId " +
		       "AND c.status != 'D' " +
		       "AND ((:partyName IS NULL OR :partyName = '' OR c.inBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.nocTransId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.exBondingId LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR p.partyName LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.bondingNo LIKE concat(:partyName, '%')) " +
		           "OR (:partyName IS NULL OR :partyName = '' OR c.boeNo LIKE concat(:partyName, '%'))) " +
		       "ORDER BY c.exBondingId DESC")
		List<CfExBondCrg> findCfinbondcrgByCompanyIdAndBranchIdForExbondScreen(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("partyName") String partyName
		);

	 
	 
	 @Query("SELECT c FROM CfExBondCrg c WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.nocTransId = :nocTransId AND c.nocNo = :nocNo AND c.exBondingId = :exBondingId and c.inBondingId=:inBondingId")
	 CfExBondCrg getDataOfExbond(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("nocTransId") String nocTransId,
	        @Param("nocNo") String nocNo,
	        @Param("exBondingId") String exBondingId,
	        @Param("inBondingId") String inBondingId
	    );
	 
	 @Query(value = "select c.giTransporterName, p.partyName, pa.address1, pa.address2, pa.address3  " +
             "from CfExBondCrg c " +
             "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.giTransporterName = p.partyId " +
             "LEFT OUTER JOIN PartyAddress pa on c.companyId = pa.companyId and c.branchId = pa.branchId and c.giTransporterName = pa.partyId " +
             "where c.companyId = :cid and c.branchId = :bid " +
            " and c.cha = :cha " +
             "and c.status != 'D' ")
//			 "and (:val is null OR :val = '' OR p.partyName LIKE CONCAT(:val, '%'))")
List<Object[]> getDataOfImporterOfCha(@Param("cid") String cid, @Param("bid") String bid, @Param("cha") String cha);
	 

	 
	 @Query(value = "select DISTINCT c.cha, p.partyName " +
             "from CfExBondCrg c " +
             "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.cha = p.partyId " +
             "where c.companyId = :cid and c.branchId = :bid " +
             "and c.status != 'D' " +
             "and (:val is null OR :val = '' OR p.partyName LIKE CONCAT(:val, '%'))")
List<Object[]> getData1(@Param("cid") String cid, @Param("bid") String bid, @Param("val") String val);





//
//@Query(value = "select DISTINCT c.giTransporterName, p.partyName " +
//        "from CfExBondCrg c " +
//        "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.giTransporterName = p.partyId " +
//        "where c.companyId = :cid and c.branchId = :bid and c.cha = :cha " +
//        "and c.status != 'D'")
//List<Object[]> getDataOfImporterOfCha(@Param("cid") String cid, @Param("bid") String bid, @Param("cha") String cha);





















@Query(value = "select pa.srNo, pa.address1, pa.address2, pa.address3 " +
        "from PartyAddress pa " +
        "where pa.companyId = :cid and pa.branchId = :bid and pa.partyId = :partyId")
List<Object[]> getAddressOfImporterOfCha(@Param("cid") String cid, @Param("bid") String bid, @Param("partyId") String partyId);


@Query(value = "select DISTINCT c.giTransporterName, p.partyName " +
        "from CfExBondCrg c " +
        "LEFT OUTER JOIN Party p on c.companyId = p.companyId and c.branchId = p.branchId and c.giTransporterName = p.partyId " +
        "where c.companyId = :cid and c.branchId = :bid and c.cha = :cha " +
        "and c.status != 'D'")
List<Object[]> getAllExbondBeNofromExbondCrgDtl(@Param("cid") String cid, @Param("bid") String bid, @Param("cha") String cha);


}
