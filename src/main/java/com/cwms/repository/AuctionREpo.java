package com.cwms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.Auction;
import com.cwms.entities.AuctionDetail;
import com.cwms.entities.Cfbondnoc;
import com.cwms.entities.ContainerDTO;

import jakarta.transaction.Transactional;

public interface AuctionREpo extends JpaRepository<Auction, String>{

	 @Query(value = "SELECT DISTINCT c.IGM_Trans_Id, c.IGM_No, c.IGM_Line_No " +
             "FROM cfimpinventory a " +
             "LEFT OUTER JOIN cfigmcn c ON c.Company_Id = a.Company_Id " +
             "AND c.branch_id = a.branch_id " +
             "AND c.Container_No = a.Container_No " +
             "WHERE a.Company_Id = :companyId " +
             "AND a.Branch_Id = :branchId " +
             "AND a.empty_out_date IS NULL " +
             "AND a.container_status != 'MTY' " +
             "AND a.Status = 'A' " +
             "AND a.profitcentre_id IN ('N00002') " +
             "AND c.Auction_Status = 'N' " +
             "AND c.container_status = 'FCL' " +
             "AND c.notice_type NOT IN  ('P','S','F') " +
             "AND (c.gate_out_id = '' AND c.de_stuff_id = '') " +
             "AND c.Status = 'A' " +
//             "AND ((:id IS NULL OR :id = '' OR c.IGM_Trans_Id LIKE concat ('%', :id, '%')) " +
//             "AND (:id IS NULL OR :id = '' OR c.IGM_No LIKE concat ('%', :id, '%')) " +
//             "AND (:id IS NULL OR :id = '' OR c.IGM_Line_No LIKE concat ('%', :id, '%'))) " +
"AND (:id IS NULL OR :id = '' OR " +
"     c.IGM_Trans_Id LIKE CONCAT('%', :id, '%') OR " +
"     c.IGM_No LIKE CONCAT('%', :id, '%') OR " +
"     c.IGM_Line_No LIKE CONCAT('%', :id, '%')) " +
             "ORDER BY a.Container_No",
     nativeQuery = true)
List<Object[]> findDistinctIGMDetails(
  @Param("companyId") String companyId,
  @Param("branchId") String branchId,
  @Param("id") String id
);



@Query(value = "SELECT DISTINCT c.IGM_Trans_Id, c.IGM_No, c.IGM_Line_No " +
        "FROM cfimpinventory a " +
        "LEFT OUTER JOIN cfigmcn c ON c.Company_Id = a.Company_Id " +
        "AND c.branch_id = a.branch_id " +
        "AND c.Container_No = a.Container_No " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.empty_out_date IS NULL " +
        "AND a.container_status != 'MTY' " +
        "AND a.Status = 'A' " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND c.Auction_Status = 'N' " +
        "AND c.container_status = 'FCL' " +
        "AND (c.gate_out_id = '' AND c.de_stuff_id = '') " +
        "AND c.Status = 'A' " +
        "AND c.notice_type = 'P' " +
        "AND c.Second_Notice_Id = '' " +
        "AND (:id IS NULL OR :id = '' OR " +
        "     c.IGM_Trans_Id LIKE CONCAT('%', :id, '%') OR " +
        "     c.IGM_No LIKE CONCAT('%', :id, '%') OR " +
        "     c.IGM_Line_No LIKE CONCAT('%', :id, '%')) " +
        "ORDER BY a.Container_No",
nativeQuery = true)
List<Object[]> findDistinctIGMDetailsForSecondNotice(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("id") String id
);

@Query(value = "SELECT DISTINCT c.IGM_Trans_Id, c.IGM_No, c.IGM_Line_No " +
        "FROM cfimpinventory a " +
        "LEFT OUTER JOIN cfigmcn c ON c.Company_Id = a.Company_Id " +
        "AND c.branch_id = a.branch_id " +
        "AND c.Container_No = a.Container_No " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.empty_out_date IS NULL " +
        "AND a.container_status != 'MTY' " +
        "AND a.Status = 'A' " +
        "AND a.profitcentre_id IN ('N00002') " +
        "AND c.Auction_Status = 'N' " +
        "AND c.container_status = 'FCL' " +
        "AND (c.gate_out_id = '' AND c.de_stuff_id = '') " +
        "AND c.Status = 'A' " +
        "AND c.notice_type = 'S' " +
        "AND c.Final_Notice_Id = '' " +
        "AND (:id IS NULL OR :id = '' OR " +
        "     c.IGM_Trans_Id LIKE CONCAT('%', :id, '%') OR " +
        "     c.IGM_No LIKE CONCAT('%', :id, '%') OR " +
        "     c.IGM_Line_No LIKE CONCAT('%', :id, '%')) " +
        "ORDER BY a.Container_No",
nativeQuery = true)
List<Object[]> findDistinctIGMDetailsForFinalNotice(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("id") String id
);

@Query(value = 
"SELECT a.IGM_Trans_Id, " +
"       e.Doc_Date, " +
"       a.IGM_No, " +
"       e.IGM_Date, " +
"       a.Profitcentre_Id, " +
"       b.Profitcentre_Desc, " +
"       e.Vessel_Id, " +
"       c.Vessel_Name, " +
"       a.Via_No, " +
"       e.Shipping_Agent, " +
"       d.Party_Name, " +
"       a.IGM_Line_No, " +
"       a.Importer_Name, " +
"       a.Importer_Address1, " +
"       a.Importer_Address2, " +
"       a.Importer_Address3, " +
"       a.Notify_Party_Name, " +
"       a.Notified_Address1, " +
"       a.Notified_Address2, " +
"       a.Notified_Address3, " +
"       a.Commodity_Description, " +
"       a.No_Of_Packages, " +
"       a.Actual_No_Of_Packages, " +
"       a.Gross_Weight, " +
"       a.Type_Of_Package, " +
"       a.BL_No, " +
"       a.BL_Date, " +
"       a.Unit_Of_Weight " +
"FROM cfigmcrg a " +
"LEFT OUTER JOIN profitcentre b " +
"    ON b.Company_Id = a.Company_Id " +
"    AND b.branch_id = a.branch_id " +
"    AND b.Profitcentre_Id = a.Profitcentre_Id " +
"LEFT OUTER JOIN cfigm e " +
"    ON e.Company_Id = a.Company_Id " +
"    AND e.IGM_Trans_Id = a.IGM_Trans_Id " +
"    AND e.branch_id = a.branch_id " +
"LEFT OUTER JOIN vessel c " +
"    ON c.Company_Id = a.Company_Id " +
"    AND c.Vessel_Id = e.Vessel_Id " +
"    AND c.branch_id = e.branch_id " +
"LEFT OUTER JOIN party d " +
"    ON d.Company_Id = a.Company_Id " +
"    AND d.Party_Id = e.Shipping_Agent " +
"    AND d.branch_id = a.branch_id " +
"WHERE a.Company_Id = :companyId " +
"AND a.Branch_Id = :branchId " +
"AND a.IGM_Line_No = :igmLineNo " +
"AND a.IGM_No = :igmNo " +
"AND a.IGM_Trans_Id = :igmTransId", 
nativeQuery = true)
List<Object[]> findIgmDetails(
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("igmLineNo") String igmLineNo,
    @Param("igmNo") String igmNo,
    @Param("igmTransId") String igmTransId
);




//@Query(value = "SELECT DISTINCT " +
//        "a.Container_No, " +
//        "a.Container_Size, " +
//        "a.Container_Type, " +
//        "DATE_FORMAT(a.Gate_In_Date, '%d %b %Y') as Gate_In_Date, " +
//        "a.No_Of_Packages, " +
//        "a.Container_Weight, " +
//        "a.Gate_In_Id " +
//        "FROM cfigmcn a " +
//        "WHERE a.Company_Id = :companyId " +
//        "AND a.Branch_Id = :branchId " +
//        "AND a.IGM_No = :igmNo " +
//        "AND a.IGM_Trans_Id = :igmTransId " +
//        "AND a.IGM_Line_No = :igmLineNo " +
//        "ORDER BY a.Container_No", nativeQuery = true)
//List<Object[]> findDistinctContainers(
//    @Param("companyId") String companyId,
//    @Param("branchId") String branchId,
//    @Param("igmNo") String igmNo,
//    @Param("igmTransId") String igmTransId,
//    @Param("igmLineNo") String igmLineNo
//);


@Query("SELECT DISTINCT NEW com.cwms.entities.ContainerDTO( " +
	       "a.containerNo, " +
	       "a.containerSize, " +
	       "a.containerType, " +
	       "a.gateInDate, " + // Format in Java, not in JPQL
	       "a.noOfPackages, " +
	       "a.containerWeight, " +
	       "a.gateInId) " +
	       "FROM Cfigmcn a " +
	       "WHERE a.companyId = :companyId " +
	       "AND a.branchId = :branchId " +
	       "AND a.igmNo = :igmNo " +
	       "AND a.igmTransId = :igmTransId " +
	       "AND a.igmLineNo = :igmLineNo " +
	       "ORDER BY a.containerNo")
	List<ContainerDTO> findDistinctContainers(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("igmNo") String igmNo,
	    @Param("igmTransId") String igmTransId,
	    @Param("igmLineNo") String igmLineNo
	);


@Query("SELECT DISTINCT NEW com.cwms.entities.Auction( " +
	       "a.containerNo, " +
	       "a.containerSize, " +
	       "a.containerType, " +
	       "a.gateInDate, " +
	       "a.noOfPackages, " +
	       "a.grossWt, " + // Ensure this maps correctly to containerWeight
	       "a.gateInId) " +
	       "FROM Auction a " +
	       "WHERE a.companyId = :companyId " +
	       "AND a.branchId = :branchId " +
	       "AND a.noticeId = :noticeId " +
	       "AND a.noticeAmndNo = :noticeAmndNo " +
	       "ORDER BY a.containerNo")
	List<Auction> findDistinctContainersAfterSave(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("noticeId") String noticeId,
	    @Param("noticeAmndNo") String noticeAmndNo
	);





@Modifying
@Transactional
@Query("UPDATE Cfigmcn c "
       + "SET c.noticeType = :noticeType, "
       + "c.noticeId = :noticeId, "
       + "c.noticeDate = :noticeDate "
       + "WHERE c.companyId = :companyId "
       + "AND c.branchId = :branchId "
       + "AND c.igmNo = :igmNo "
       + "AND c.igmTransId = :igmTransId "
       + "AND c.igmLineNo = :igmLineNo "
       + "AND c.containerNo = :containerNo")
int updateCfigmCnAfterAuctionNotice(
    @Param("noticeType") char noticeType,
    @Param("noticeId") String noticeId,  // Changed from char to String
    @Param("noticeDate") Date noticeDate,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("igmNo") String igmNo,
    @Param("igmTransId") String igmTransId,
    @Param("igmLineNo") String igmLineNo,
    @Param("containerNo") String containerNo
);


@Modifying
@Transactional
@Query("UPDATE Cfigmcn c "
       + "SET c.noticeType = :noticeType, "
       + "c.secondNoticeId = :secondNoticeId, "
       + "c.secondNoticeDate = :secondNoticeDate "
       + "WHERE c.companyId = :companyId "
       + "AND c.branchId = :branchId "
       + "AND c.igmNo = :igmNo "
       + "AND c.igmTransId = :igmTransId "
       + "AND c.igmLineNo = :igmLineNo "
       + "AND c.containerNo = :containerNo")
int updateCfigmCnAfterAuctionNotice2(
    @Param("noticeType") char noticeType,
    @Param("secondNoticeId") String secondNoticeId,  // Changed from char to String
    @Param("secondNoticeDate") Date secondNoticeDate, // Corrected parameter name
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("igmNo") String igmNo,
    @Param("igmTransId") String igmTransId,
    @Param("igmLineNo") String igmLineNo,
    @Param("containerNo") String containerNo
);


@Modifying
@Transactional
@Query("UPDATE Cfigmcn c "
       + "SET c.noticeType = :noticeType, "
       + "c.finalNoticeId = :finalNoticeId, "
       + "c.finalNoticeDate = :finalNoticeDate "
       + "WHERE c.companyId = :companyId "
       + "AND c.branchId = :branchId "
       + "AND c.igmNo = :igmNo "
       + "AND c.igmTransId = :igmTransId "
       + "AND c.igmLineNo = :igmLineNo "
       + "AND c.containerNo = :containerNo")
int updateCfigmCnAfterAuctionNotice3(
    @Param("noticeType") char noticeType,
    @Param("finalNoticeId") String finalNoticeId,  // Changed from char to String
    @Param("finalNoticeDate") Date finalNoticeDate,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("igmNo") String igmNo,
    @Param("igmTransId") String igmTransId,
    @Param("igmLineNo") String igmLineNo,
    @Param("containerNo") String containerNo
);





@Modifying
@Transactional
@Query("UPDATE AuctionDetail c "
       + "SET c.finalNoticeId = :finalNoticeId "
       + "WHERE c.companyId = :companyId "
       + "AND c.branchId = :branchId "
       + "AND c.igmNo = :igmNo "
       + "AND c.igmTransId = :igmTransId "
       + "AND c.igmLineNo = :igmLineNo ")
int updateActionCrgAfterFinalNotice(
    @Param("finalNoticeId") String finalNoticeId,
    @Param("companyId") String companyId,
    @Param("branchId") String branchId,
    @Param("igmNo") String igmNo,
    @Param("igmTransId") String igmTransId,
    @Param("igmLineNo") String igmLineNo
);


@Query("SELECT DISTINCT NEW com.cwms.entities.AuctionDetail(c.companyId, c.branchId, c.profitcentreId, "
        + "c.noticeId, c.noticeAmndNo, c.noticeType, c.transType, c.noticeDate, "
        + "c.igmTransId, c.igmTransDate, c.igmNo, c.igmDate, c.igmLineNo, "
        + "c.noOfPackages, c.grossWt, c.status,c.blNo) "
    + "FROM AuctionDetail c "
    + "WHERE c.companyId = :companyId "
    + "AND c.branchId = :branchId "
    + "AND c.status ='A' "
    + "AND c.noticeType ='P' "
    + "AND ((:partyName IS NULL OR :partyName = '' OR c.igmTransId LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.igmNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.igmLineNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.transType LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.blNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.noticeId LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.noticeType LIKE concat(:partyName, '%'))) " 
    + "ORDER BY c.noticeDate DESC")
List<AuctionDetail> findAuctionDetails(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId,
    @Param("partyName") String partyName);



@Query("SELECT DISTINCT NEW com.cwms.entities.AuctionDetail(c.companyId, c.branchId, c.profitcentreId, "
        + "c.noticeId, c.noticeAmndNo, c.noticeType, c.transType, c.noticeDate, "
        + "c.igmTransId, c.igmTransDate, c.igmNo, c.igmDate, c.igmLineNo, "
        + "c.noOfPackages, c.grossWt, c.status,c.blNo) "
    + "FROM AuctionDetail c "
    + "WHERE c.companyId = :companyId "
    + "AND c.branchId = :branchId "
    + "AND c.status ='A' "
    + "AND c.noticeType ='S' "
    + "AND ((:partyName IS NULL OR :partyName = '' OR c.igmTransId LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.igmNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.igmLineNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.transType LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.blNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.noticeId LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.noticeType LIKE concat(:partyName, '%'))) " 
    + "ORDER BY c.noticeDate DESC")
List<AuctionDetail> findAuctionDetailsSecond(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId,
    @Param("partyName") String partyName);








@Query("SELECT DISTINCT NEW com.cwms.entities.AuctionDetail(c.companyId, c.branchId, c.profitcentreId, "
        + "c.noticeId, c.noticeAmndNo, c.noticeType, c.transType, c.noticeDate, "
        + "c.igmTransId, c.igmTransDate, c.igmNo, c.igmDate, c.igmLineNo, "
        + "c.noOfPackages, c.grossWt, c.status,c.blNo) "
    + "FROM AuctionDetail c "
    + "WHERE c.companyId = :companyId "
    + "AND c.branchId = :branchId "
    + "AND c.status ='A' "
    + "AND c.noticeType ='F' "
    + "AND ((:partyName IS NULL OR :partyName = '' OR c.igmTransId LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.igmNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.igmLineNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.transType LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.blNo LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.noticeId LIKE concat(:partyName, '%')) " +
    "OR (:partyName IS NULL OR :partyName = '' OR c.noticeType LIKE concat(:partyName, '%'))) " 
    + "ORDER BY c.noticeDate DESC")
List<AuctionDetail> findAuctionDetailsFinal(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId,
    @Param("partyName") String partyName);






@Query("SELECT DISTINCT new com.cwms.entities.AuctionDetail( " +
	       "a.companyId, " +
	       "a.branchId, " +
	       "a.profitcentreId, " +
	       "a.noticeId, " +
	       "a.noticeAmndNo, " +
	       "a.finalNoticeId, " +
	       "a.noticeType, " +
	       "a.transType, " +
	       "a.noticeDate, " +
	       "a.igmTransId, " +
	       "a.igmTransDate, " +
	       "a.igmNo, " +
	       "a.igmDate, " +
	       "a.igmLineNo, " +
	       "a.viaNo, " +
	       "a.shift, " +
	       "a.source, " +
	       "a.boeNo, " +
	       "a.boeDate, " +
	       "a.vessel, " +
	       "p.partyName, " +
	       "a.importerName, " +
	       "a.importerAddress1, " +
	       "a.importerAddress2, " +
	       "a.importerAddress3, " +
	       "a.notifyParty, " +
	       "a.notifyPartyAddress1, " +
	       "a.notifyPartyAddress2, " +
	       "a.notifyPartyAddress3, " +
	       "a.commodityDescription, " +
	       "a.noOfPackages, " +
	       "a.actualNoOfPackages, " +
	       "a.typeOfPackage, " +
	       "a.grossWt, " +
	       "a.uom, " +
	       "a.blNo, " +
	       "a.blDate, " +
	       "a.assessiableAvailable, " +
	       "a.accessableValueAsValuation, " +
	       "a.bidId, " +
	       "a.bidDate, " +
	       "a.comments, " +
	       "a.cvStatus, " +
	       "a.createdBy, " +
	       "a.createdDate, " +
	       "a.approvedBy, " +
	       "a.approvedDate, " +
	       "a.status, " +
	       "a.pol, " +
	       "a.auctionType) " +
	       "FROM AuctionDetail a " +
	       "LEFT JOIN Party p ON p.companyId = a.companyId " +
	       "AND p.branchId = a.branchId " +
	       "AND p.partyId = a.sa " +
	       "WHERE a.companyId = :companyId " +
	       "AND a.branchId = :branchId " +
	       "AND a.noticeId = :noticeId " +
	       "AND a.noticeAmndNo = :noticeAmndNo " +
	       "AND a.igmNo = :igmNo " +
	       "AND a.igmLineNo = :igmLineNo " +
	       "AND a.igmTransId = :igmTransId " +
	       "ORDER BY a.noticeId")
	AuctionDetail findDistinctAuctionDetailsAfterSave(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("noticeId") String noticeId,
	    @Param("noticeAmndNo") String noticeAmndNo,
	    @Param("igmNo") String igmNo,
	    @Param("igmLineNo") String igmLineNo,
	    @Param("igmTransId") String igmTransId
	);









@Query(value = "SELECT a.Container_no, a.Container_Size, " +
        "DATE_FORMAT(a.Gate_In_Date,'%d/%m/%Y') AS GateInDate, " +
        "b.POL, a.Container_status, b.Commodity_Description, " +
        "b.IGM_No, b.IGM_Line_No, b.BL_No, d.Vessel_Name, " +
        "e.party_Name, e1.Party_Name AS SL " +
        "FROM cfauccn a " +
        "LEFT OUTER JOIN cfauccrg b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id AND a.Notice_Id = b.Notice_Id " +
        "LEFT OUTER JOIN cfigm c ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id AND b.IGM_Trans_Id = c.IGM_Trans_Id " +
        "AND b.IGM_No = c.IGM_No " +
        "LEFT OUTER JOIN vessel d ON c.Company_Id = d.Company_Id " +
        "AND c.Vessel_Id = d.Vessel_Id AND c.branch_id = d.branch_id " +
        "LEFT OUTER JOIN party e ON c.Company_Id = e.Company_Id " +
        "AND c.Shipping_Agent = e.Party_Id AND c.branch_id = e.branch_id " +
        "LEFT OUTER JOIN party e1 ON c.Company_Id = e1.Company_Id " +
        "AND c.Shipping_line = e1.Party_Id AND c.branch_id = e1.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.notice_type = 'P' " +
        "AND b.notice_type = 'P' " +
        "AND b.Igm_no = :igmNo " +
        "AND b.IGM_Line_No = :igmLineNo " +
        "AND b.Notice_Id = :noticeId", 
nativeQuery = true)
List<Object[]> findContainerDetailsFirstNoticePrint(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("igmNo") String igmNo,
@Param("igmLineNo") String igmLineNo,
@Param("noticeId") String noticeId
);




@Query(value = "SELECT a.Container_no, a.Container_Size, " +
        "DATE_FORMAT(a.Gate_In_Date,'%d/%m/%Y') AS GateInDate, " +
        "b.POL, a.Container_status, b.Commodity_Description, " +
        "b.IGM_No, b.IGM_Line_No, b.BL_No, d.Vessel_Name, " +
        "e.party_Name, e1.Party_Name AS SL " +
        "FROM cfauccn a " +
        "LEFT OUTER JOIN cfauccrg b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id AND a.Notice_Id = b.Notice_Id " +
        "LEFT OUTER JOIN cfigm c ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id AND b.IGM_Trans_Id = c.IGM_Trans_Id " +
        "AND b.IGM_No = c.IGM_No " +
        "LEFT OUTER JOIN vessel d ON c.Company_Id = d.Company_Id " +
        "AND c.Vessel_Id = d.Vessel_Id AND c.branch_id = d.branch_id " +
        "LEFT OUTER JOIN party e ON c.Company_Id = e.Company_Id " +
        "AND c.Shipping_Agent = e.Party_Id AND c.branch_id = e.branch_id " +
        "LEFT OUTER JOIN party e1 ON c.Company_Id = e1.Company_Id " +
        "AND c.Shipping_line = e1.Party_Id AND c.branch_id = e1.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.notice_type = 'S' " +
        "AND b.notice_type = 'S' " +
        "AND b.Igm_no = :igmNo " +
        "AND b.IGM_Line_No = :igmLineNo " +
        "AND b.Notice_Id = :noticeId", 
nativeQuery = true)
List<Object[]> findContainerDetailsSecondNoticePrint(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("igmNo") String igmNo,
@Param("igmLineNo") String igmLineNo,
@Param("noticeId") String noticeId
);













@Query(value = "SELECT a.Container_no, a.Container_Size, " +
        "DATE_FORMAT(a.Gate_In_Date,'%d/%m/%Y') AS GateInDate, " +
        "b.POL, a.Container_status, b.Commodity_Description, " +
        "b.IGM_No, b.IGM_Line_No, b.BL_No, d.Vessel_Name, " +
        "e.party_Name, e1.Party_Name AS SL " +
        "FROM cfauccn a " +
        "LEFT OUTER JOIN cfauccrg b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id AND a.Notice_Id = b.Notice_Id " +
        "LEFT OUTER JOIN cfigm c ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id AND b.IGM_Trans_Id = c.IGM_Trans_Id " +
        "AND b.IGM_No = c.IGM_No " +
        "LEFT OUTER JOIN vessel d ON c.Company_Id = d.Company_Id " +
        "AND c.Vessel_Id = d.Vessel_Id AND c.branch_id = d.branch_id " +
        "LEFT OUTER JOIN party e ON c.Company_Id = e.Company_Id " +
        "AND c.Shipping_Agent = e.Party_Id AND c.branch_id = e.branch_id " +
        "LEFT OUTER JOIN party e1 ON c.Company_Id = e1.Company_Id " +
        "AND c.Shipping_line = e1.Party_Id AND c.branch_id = e1.branch_id " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.notice_type = 'F' " +
        "AND b.notice_type = 'F' " +
        "AND b.Igm_no = :igmNo " +
        "AND b.IGM_Line_No = :igmLineNo " +
        "AND b.Notice_Id = :noticeId", 
nativeQuery = true)
List<Object[]> findContainerDetailsFinalNoticePrint(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("igmNo") String igmNo,
@Param("igmLineNo") String igmLineNo,
@Param("noticeId") String noticeId
);




@Query(value = "SELECT a.Notice_Id, " +
        "DATE_FORMAT(DATE_ADD(MIN(c.Gate_In_Date), INTERVAL 30 DAY), '%d/%b/%Y') AS Notice_date, " +
        "b.Importer_Name, b.importer_address1, b.importer_address2, b.importer_address3, " +
        "b.Notify_Party_Name, b.Notified_Address1, b.Notified_Address2, b.Notified_Address3 " +
        "FROM cfauccrg a " +
        "LEFT OUTER JOIN cfigmcrg b ON a.Company_Id = b.Company_Id " +
        "AND a.Branch_Id = b.Branch_Id " +
        "AND a.IGM_Trans_Id = b.IGM_Trans_Id " +
        "AND a.IGM_No = b.igm_no " +
        "AND a.IGM_Line_No = b.IGM_Line_No " +
        "LEFT OUTER JOIN cfigmcn c ON b.Company_Id = c.Company_Id " +
        "AND b.Branch_Id = c.Branch_Id " +
        "AND b.Profitcentre_Id = c.Profitcentre_Id " +
        "AND b.IGM_Trans_Id = c.IGM_Trans_Id " +
        "AND b.IGM_No = c.IGM_No " +
        "AND b.IGM_Line_No = c.IGM_Line_No " +
        "WHERE a.Company_Id = :companyId " +
        "AND a.Branch_Id = :branchId " +
        "AND a.Igm_no = :igmNo " +
        "AND a.IGM_Line_No = :igmLineNo " +
        "AND a.Notice_Id = :noticeId " +
        "GROUP BY a.Notice_Id " +
        "LIMIT 1", 
nativeQuery = true)
List<Object[]> firstNoticePrintHead(
@Param("companyId") String companyId,
@Param("branchId") String branchId,
@Param("igmNo") String igmNo,
@Param("igmLineNo") String igmLineNo,
@Param("noticeId") String noticeId
);














//@Query("SELECT NEW com.cwms.entities.AuctionDetail(c.companyId, c.branchId, c.noticeId, c.noticeAmndNo, c.finalNoticeId, c.igmTransId, c.igmNo, " +
//        "c.igmLineNo, c.blNo) " +
//"FROM AuctionDetail c " +
//"WHERE c.companyId = :companyId " +
//"AND c.branchId = :branchId " +
//"AND c.status != 'D' " +
//"AND ((:igmTransId IS NULL OR :igmTransId = '' OR c.igmTransId LIKE CONCAT(:igmTransId, '%')) " +
//"AND (:igmNo IS NULL OR :igmNo = '' OR c.igmNo LIKE CONCAT(:igmNo, '%')) " +
//"AND (:igmLineNo IS NULL OR :igmLineNo = '' OR c.igmLineNo LIKE CONCAT(:igmLineNo, '%')) " +
//"AND (:blNo IS NULL OR :blNo = '' OR c.blNo LIKE CONCAT(:blNo, '%')))")
//List<AuctionDetail> getForMainAuctionSearch(@Param("companyId") String companyId, 
//                                               @Param("branchId") String branchId,
//                                               @Param("igmTransId") String igmTransId,
//                                               @Param("igmNo") String igmNo,
//                                               @Param("igmLineNo") String igmLineNo,
//                                               @Param("blNo") String blNo);










//@Query(value = "SELECT DISTINCT c.notice_type,c.IGM_Trans_Id, c.IGM_No, c.IGM_Line_No ,c.container_no ,a.bl_no ,c.notice_id,c.second_notice_id,c.final_notice_id " +
//        "FROM cfigmcn c " +
//        "LEFT OUTER JOIN cfigmcrg a ON c.Company_Id = a.Company_Id " +
//        "AND c.branch_id = a.branch_id " +
//        "AND c.igm_trans_id = a.igm_trans_id " +
//        "AND c.igm_no = a.igm_no " +
//        "AND c.Profitcentre_Id = a.Profitcentre_Id " +
//        "AND c.igm_line_no = a.igm_line_no " +
//        "WHERE c.Company_Id = :companyId " +
//        "AND c.Branch_Id = :branchId " +
////        "AND c.empty_out_date IS NULL " +
//        "AND c.container_status != 'MTY' " +
//        "AND c.Status = 'A' " +
//        "AND c.Profitcentre_Id ='N00002' "+
//        "AND (c.gate_out_id = '' AND c.de_stuff_id = '') " +
//        "AND c.Status = 'A' " +
//        "AND ((:igmTransId IS NULL OR :igmTransId = '' OR c.container_no LIKE CONCAT(:igmTransId, '%')) " +
//        "AND (:igmNo IS NULL OR :igmNo = '' OR c.igm_no LIKE CONCAT(:igmNo, '%')) " +
//        "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR c.igm_line_no LIKE CONCAT(:igmLineNo, '%')) " +
//        "AND (:blNo IS NULL OR :blNo = '' OR a.bl_no LIKE CONCAT(:blNo, '%'))) ",
//nativeQuery = true)
//List<Object[]> getForMainAuctionSearch(
//		@Param("companyId") String companyId, 
//        @Param("branchId") String branchId,
//        @Param("igmTransId") String igmTransId,
//        @Param("igmNo") String igmNo,
//        @Param("igmLineNo") String igmLineNo,
//        @Param("blNo") String blNo);


@Query(value = "SELECT DISTINCT c.notice_type,c.IGM_Trans_Id, c.IGM_No, c.IGM_Line_No ,c.container_no ,a.bl_no ,c.notice_id," +
		"c.second_notice_id,c.final_notice_id,au.cv_status " +
        "FROM cfigmcn c " +
        "LEFT OUTER JOIN cfigmcrg a ON c.Company_Id = a.Company_Id " +
        "AND c.branch_id = a.branch_id " +
        "AND c.igm_trans_id = a.igm_trans_id " +
        "AND c.igm_no = a.igm_no " +
        "AND c.Profitcentre_Id = a.Profitcentre_Id " +
        "AND c.igm_line_no = a.igm_line_no " +
        "LEFT OUTER JOIN cfauccrg au ON c.company_Id=au.company_Id and c.branch_Id=au.branch_Id and c.igm_No=au.igm_No and " +
        "c.igm_Trans_Id=au.igm_Trans_Id and c.igm_Line_No=au.igm_Line_No and c.final_notice_id=au.notice_id " +
        "WHERE c.Company_Id = :companyId " +
        "AND c.Branch_Id = :branchId " +
//        "AND c.empty_out_date IS NULL " +
        "AND c.container_status != 'MTY' " +
        "AND c.Status = 'A' " +
        "AND c.Profitcentre_Id ='N00002' "+
        "AND (c.gate_out_id = '' AND c.de_stuff_id = '') " +
        "AND c.Status = 'A' " +
        "AND ((:igmTransId IS NULL OR :igmTransId = '' OR c.container_no LIKE CONCAT(:igmTransId, '%')) " +
        "AND (:igmNo IS NULL OR :igmNo = '' OR c.igm_no LIKE CONCAT(:igmNo, '%')) " +
        "AND (:igmLineNo IS NULL OR :igmLineNo = '' OR c.igm_line_no LIKE CONCAT(:igmLineNo, '%')) " +
        "AND (:blNo IS NULL OR :blNo = '' OR a.bl_no LIKE CONCAT(:blNo, '%'))) ",
nativeQuery = true)
List<Object[]> getForMainAuctionSearch(
		@Param("companyId") String companyId, 
        @Param("branchId") String branchId,
        @Param("igmTransId") String igmTransId,
        @Param("igmNo") String igmNo,
        @Param("igmLineNo") String igmLineNo,
        @Param("blNo") String blNo);
}


