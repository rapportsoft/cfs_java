package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;

import jakarta.transaction.Transactional;

public interface ExportSbCargoEntryRepo extends JpaRepository<ExportSbCargoEntry, String>
{
	
	@Query("SELECT s.sbDocumentUploadPath " +
		       "FROM ExportSbCargoEntry s " +		       
		       "WHERE s.companyId = :companyId AND s.branchId = :branchId " +
		       "AND s.sbNo = :sbNo " +
		       "AND s.hSbTransId = :hSbTransId " +
		       "AND s.sbTransId = :sbTransId AND s.sbLineNo = :sbLineNo " +
		       "AND s.status <> 'D'")
	String getSBDocumentUploadPath(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("sbLineNo") String sbLineNo,
	        @Param("hSbTransId") String hSbTransId
		);
	
	

	@Modifying
	@Transactional
	@Query("UPDATE ExportSbCargoEntry e " +
	       "SET e.sbDocumentUploadPath = :filePath, e.sbDocumentUploadedBy = :user " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.hSbTransId = :hSbTransId " +
	       "  AND e.sbLineNo = :sbLineNo " +
	       "  AND e.status <> 'D'")
	int updateSBDocumentUload(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("sbLineNo") String sbLineNo,
	        @Param("hSbTransId") String hSbTransId,
	        @Param("filePath") String filePath,
	        @Param("user") String user);

	
	
	
	
	
	
	
	
	
	
	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ExportSbCargoEntry e " +
	       "SET e.sbType = :sbType, " +
	       "    e.sbNo = :sbNo, " +
	       "    e.sbDate = :sbDate, " +
	       "    e.drawBackValue = :drawBackValue, " +
	       "    e.cargoType = :cargoType, " +
	       "    e.commodity = :commodity, " +
	       "    e.numberOfMarks = :numberOfMarks, " +
	       "    e.noOfPackages = :noOfPackages, " +
	       "    e.typeOfPackage = :typeOfPackage, " +
	       "    e.grossWeight = :grossWeight, " +
	       "    e.fob = :fob, " +
	       "    e.invoiceNo = :invoiceNo, " +
	       "    e.invoiceDate = :invoiceDate, " +
	       "    e.haz = :haz, " +
	       "    e.unNo = :unNo, " +
	       "    e.newCommodity = :newCommodity " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.profitcentreId = :profitCentreId " +
	       "  AND e.srno = :srNo " +
	       "  AND e.status <> 'D'")
	int updateCargoEntry(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("profitCentreId") String profitCentreId,
	        @Param("sbTransId") String sbTransId,
	        @Param("sbNo") String sbNo,	        
	        @Param("sbType") String sbType,
	        @Param("sbDate") Date date,
	        @Param("drawBackValue") BigDecimal drawBackValue,
	        @Param("cargoType") String cargoType,
	        @Param("commodity") String commodity,
	        @Param("numberOfMarks") String numberOfMarks,
	        @Param("noOfPackages") BigDecimal noOfPackages,
	        @Param("typeOfPackage") String typeOfPackage,
	        @Param("grossWeight") BigDecimal grossWeight,
	        @Param("fob") BigDecimal fob,
	        @Param("invoiceNo") String invoiceNo,
	        @Param("invoiceDate") Date date2,
	        @Param("haz") String haz,
	        @Param("unNo") String unNo,
	        @Param("newCommodity") String newCommodity,
	        @Param("srNo") Long srNo
	);

	
	
	

	@Query("SELECT NEW com.cwms.entities.ExportSbCargoEntry(e.profitcentreId, e.sbTransId, e.srno, e.sbNo, "
		     + "e.noOfPackages, e.gateInPackages, e.cartedPackages, e.stuffReqQty, e.stuffedQty, e.bttOutQty, "
		     + "e.transferPackages, e.hSbTransId, e.sbType, e.backToTownPack) "
		     + "FROM ExportSbCargoEntry e "
		     + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
		     + "AND e.sbNo = :sbNo " 
		     + "AND e.sbTransId = :sbTransId "  + "AND e.srno = :sbLineNo " 
		     + "AND e.status <> 'D'")
	Page<ExportSbCargoEntry> getDataForExportMainSearchSbCargoContainer(@Param("companyId") String companyId, 
		                                         @Param("branchId") String branchId,@Param("sbNo") String sbNo,
		                                         @Param("sbTransId") String sbTransId, @Param("sbLineNo") String sbLineNo, Pageable pageable);

	
	
	@Query("SELECT NEW com.cwms.entities.ExportSbCargoEntry(e.profitcentreId, e.sbTransId, e.srno, e.sbNo, "
		     + "e.noOfPackages, e.gateInPackages, e.cartedPackages, e.stuffReqQty, e.stuffedQty, e.bttOutQty, "
		     + "e.transferPackages, e.hSbTransId, e.sbType, e.backToTownPack) "
		     + "FROM ExportSbCargoEntry e "
		     + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
		     + "AND e.sbNo = :sbNo " 
		     + "AND e.sbTransId = :sbTransId "
		     + "AND e.status <> 'D'")
	Page<ExportSbCargoEntry> getDataForExportMainSearchSbCargoContainerPortReturn(@Param("companyId") String companyId, 
		                                         @Param("branchId") String branchId,@Param("sbNo") String sbNo,
		                                         @Param("sbTransId") String sbTransId, Pageable pageable);

	
	
	
	
	
	@Query("SELECT NEW com.cwms.entities.ExportSbCargoEntry(e.profitcentreId, e.sbTransId, e.srno, e.sbNo, "
		     + "e.noOfPackages, e.gateInPackages, e.cartedPackages, e.stuffReqQty, e.stuffedQty, e.bttOutQty, "
		     + "e.transferPackages, e.hSbTransId, e.sbType, e.backToTownPack) "
		     + "FROM ExportSbCargoEntry e "
		     + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
//		     + "AND e.sbNo = :sbNo " 
		     + "AND e.sbNo LIKE CONCAT(:sbNo, '%')"
		     + "AND e.status <> 'D' "
		     + "ORDER BY e.createdDate DESC")
	Page<ExportSbCargoEntry> getDataForExportMainSearchSbCargo(@Param("companyId") String companyId, 
		                                         @Param("branchId") String branchId, @Param("sbNo") String sbNo, Pageable pageable);
		                 
	

	@Modifying
	@Transactional
	@Query("UPDATE ExportSbCargoEntry e " +
	       "SET e.cartedPackages = COALESCE(e.cartedPackages, 0) - :cartedPackes, " +
	       "    e.gateInPackages = COALESCE(e.gateInPackages, 0) - :gateInPackages " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.status <> 'D'")
	int updateFromSbNoTransfer(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("cartedPackes") BigDecimal cartedPackes,
	        @Param("gateInPackages") BigDecimal gateInPackages);

	
	
	@Modifying
	@Transactional
	@Query("UPDATE ExportSbCargoEntry e " +
	       "SET e.cartedPackages = COALESCE(e.cartedPackages, 0) + :cartedPackes, " +
	       "    e.gateInPackages = COALESCE(e.gateInPackages, 0) + :gateInPackages " +
	       "WHERE e.companyId = :companyId " +
	       "  AND e.branchId = :branchId " +
	       "  AND e.sbNo = :sbNo " +
	       "  AND e.sbTransId = :sbTransId " +
	       "  AND e.status <> 'D'")
	int updateToSbNoTransfer(	    
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("cartedPackes") BigDecimal cartedPackes,
	        @Param("gateInPackages") BigDecimal gateInPackages);

	
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ExportSbCargoEntry e " +
	       "SET e.stuffedQty = COALESCE(e.stuffedQty, 0) - :oldValue + :newValue, " +
	       "    e.stuffedWt = COALESCE(e.stuffedWt, 0) - :oldWeight + :newWeight " +
	       "WHERE e.companyId = :companyId " +
	       "AND e.branchId = :branchId " +
	       "AND e.sbNo = :sbNo " +
	       "AND e.sbTransId = :sbTransId " +
	       "AND e.status <> 'D'")
	int updateStuffTallyQtyUpdateBuffer(
	        @Param("oldValue") int oldValue,
	        @Param("newValue") int newValue,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("oldWeight") BigDecimal oldWeight,
	        @Param("newWeight") BigDecimal newWeight
	);

	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ExportSbCargoEntry e " +
	        "SET e.stuffedQty = COALESCE(e.stuffedQty, 0) + :stuffedQty, " +
	        "    e.bufferStuffing = 'Y', " +
	        "    e.stuffedWt = COALESCE(e.stuffedWt, 0) + :stuffedWt " +
	        "WHERE e.companyId = :companyId " +
	        "AND e.branchId = :branchId " +
	        "AND e.sbNo = :sbNo " +
	        "AND e.sbTransId = :sbTransId " +
	        "AND e.status <> 'D'")
	int updateStuffTallyBuffer(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("stuffedQty") Integer stuffedQty,
	        @Param("stuffedWt") BigDecimal stuffedWt
	);


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Modifying
    @Transactional
    @Query("UPDATE ExportSbCargoEntry e SET e.stuffReqQty = e.stuffReqQty - :oldValue + :newValue " +
           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
           "AND e.sbNo = :sbNo AND e.sbTransId = :sbTransId AND e.status <> 'D'")
    int updateStuffReqQtyUpdate(
        @Param("oldValue") int oldValue,
        @Param("newValue") int newValue,
        @Param("companyId") String companyId,
        @Param("branchId") String branchId,
        @Param("sbNo") String sbNo,
        @Param("sbTransId") String sbTransId
    );
		
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ExportSbCargoEntry e " +
	        "SET e.stuffReqQty = COALESCE(e.stuffReqQty, 0) + :stuffQuantity " +
	        "WHERE e.companyId = :companyId " +
	        "AND e.branchId = :branchId " +
	        "AND e.sbNo = :sbNo " +
	        "AND e.sbTransId = :sbTransId " +
	        "AND e.status <> 'D'")
	int updateStuffRequest(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("stuffQuantity") Integer stuffQuantity
	);

	
//	@Query("SELECT s.sbNo " +
//		       "FROM ExportSbEntry s " +		       
//		       "WHERE s.companyId = :companyId AND s.branchId = :branchId " +
//		       "AND (s.totalPackages - s.gateInPackages) <> 0 " +
//		       "AND s.status <> 'D'")
//		List<String> searchSbNosToGateIn(
//		    @Param("companyId") String companyId,
//		    @Param("branchId") String branchId
//		);
	
	@Query("SELECT s.sbNo " +
		       "FROM ExportSbEntry s " +		       
		       "WHERE s.companyId = :companyId AND s.branchId = :branchId " +
		       "AND (s.totalPackages - s.gateInPackages) <> 0 " +
		       "AND s.sbNo LIKE %:searchValue% " +
		       "AND s.sbType = 'Normal' " +
		       "AND s.status <> 'D'")
		List<String> searchSbNosToGateIn(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("searchValue") String searchValue
		);


	
	
	@Query("SELECT E FROM ExportSbCargoEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.sbNo = :sbNo " 
	        + "AND E.sbTransId = :sbTransId "
	        + "AND E.status <> 'D'")
	ExportSbCargoEntry getExportSbEntryBySbNoAndSbTransId(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId);

	
	
	@Query("SELECT E FROM ExportSbCargoEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.profitcentreId = :profitcentreId " 
	        + "AND E.hSbTransId = :hSbTransId "	        
	        + "AND E.status <> 'D'")
	List<ExportSbCargoEntry> getExportSbCargoEntryBySbTransIdAndSbNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("profitcentreId") String profitcentreId, @Param("hSbTransId") String hSbTransId);

	
	
//	@Query("SELECT E.sbNo, E.sbTransId, E.sbDate, s.onAccountOf, o.partyName, s.cha, c.partyName, E.newCommodity, s.totalPackages, s.gateInPackages, E.sbLineNo, s.totalGrossWeight " +
//		       "FROM ExportSbEntry s " +
//		       "LEFT JOIN ExportSbCargoEntry E ON E.companyId = s.companyId AND E.branchId = s.branchId " +
//		       "AND E.hSbTransId = s.hSbTransId AND E.sbTransId = s.sbTransId AND E.sbNo = s.sbNo " +
//		       "LEFT JOIN Party c ON s.companyId = c.companyId AND s.branchId = c.branchId " +
//		       "AND s.cha = c.partyId AND c.status != 'D' " +
//		       "LEFT JOIN Party o ON s.companyId = o.companyId AND s.branchId = o.branchId " +
//		       "AND s.onAccountOf = o.partyId AND o.status != 'D' " +
//		       "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.sbNo = :sbNo " +
//		       "AND (s.totalPackages - s.gateInPackages) <> 0 " +			       
//		       "AND s.status <> 'D'")
//		List<Object[]> getSbCargoGateIn(
//		    @Param("companyId") String companyId, 
//		    @Param("branchId") String branchId,
//		    @Param("sbNo") String sbNo
//		);

	
	@Query("SELECT E.sbNo, E.sbTransId, E.sbDate, s.onAccountOf, o.partyName, s.cha, c.partyName, E.commodity, s.totalPackages, s.gateInPackages, E.sbLineNo, s.totalGrossWeight " +
		       "FROM ExportSbEntry s " +
		       "LEFT JOIN ExportSbCargoEntry E ON E.companyId = s.companyId AND E.branchId = s.branchId " +
		       "AND E.hSbTransId = s.hSbTransId AND E.sbTransId = s.sbTransId AND E.sbNo = s.sbNo " +
		       "LEFT JOIN Party c ON s.companyId = c.companyId AND s.branchId = c.branchId " +
		       "AND s.cha = c.partyId AND c.status != 'D' " +
		       "LEFT JOIN Party o ON s.companyId = o.companyId AND s.branchId = o.branchId " +
		       "AND s.onAccountOf = o.partyId AND o.status != 'D' " +
		       // Conditional LEFT JOIN with GateIn
		       "LEFT JOIN GateIn g ON g.companyId = s.companyId AND g.branchId = s.branchId AND g.docRefNo = s.sbNo " +
		       "AND g.gateInId = :gateInId " +  // Ensures that the combination of sbNo and gateInId is checked
		       "WHERE s.companyId = :companyId AND s.branchId = :branchId AND s.sbNo = :sbNo " +
		       "AND (s.totalPackages - s.gateInPackages) <> 0 " +
		       "AND s.status <> 'D' " +
		       // Exclude records where the combination of sbNo and gateInId exists
		       "AND g.gateInId IS NULL")
		List<Object[]> getSbCargoGateIn(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId,
		    @Param("sbNo") String sbNo,
		    @Param("gateInId") String gateInId
		);



	
	
	
//	@Query(value = "select e.sbTransId, c.hSbTransId, e.sbTransDate, c.sbNo, c.sbDate, e.pol, p.portName, e.pod, e.exporterName, pa.partyName, e.profitcentreId, po.profitcentreDesc,e.status "
//	        + "from ExportSbCargoEntry c "
//	        + "Left Join ExportSbEntry e on c.companyId = e.companyId and c.branchId = e.branchId and c.profitcentreId = e.profitcentreId and c.sbTransId = e.sbTransId and e.status != 'D' "
//	        + "Left Join Port p on c.companyId = p.companyId and c.branchId = p.branchId and e.pol = p.portCode and p.status != 'D' "
//	        + "Left Join Party pa on e.companyId = pa.companyId and e.branchId = pa.branchId and e.cha = pa.partyId and pa.status != 'D' "
//	        + "Left Join Profitcentre po on e.companyId = po.companyId and e.branchId = po.branchId and e.profitcentreId = po.profitcentreId and po.status != 'D' "
//	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' "
//	        + "and (:searchValue is null OR :searchValue = '' OR c.sbNo LIKE %:searchValue% OR c.hSbTransId LIKE %:searchValue% OR c.sbTransId LIKE %:searchValue%)")
//	List<Object[]> getSbEntriesData(@Param("companyId") String companyId, @Param("branchId") String branchId,
//	                                @Param("searchValue") String searchValue);

	
	@Query(value = "select e.sbTransId, c.hSbTransId, e.sbTransDate, c.sbNo, c.sbDate, e.pol, p.portName, e.pod, e.exporterName, pa.partyName, e.profitcentreId, po.profitcentreDesc, e.status "
	        + "from ExportSbCargoEntry c "
	        + "Left Join ExportSbEntry e on c.companyId = e.companyId and c.branchId = e.branchId and c.profitcentreId = e.profitcentreId and c.sbTransId = e.sbTransId and e.status != 'D' "
	        + "Left Join Port p on c.companyId = p.companyId and c.branchId = p.branchId and e.pol = p.portCode and p.status != 'D' "
	        + "Left Join Party pa on e.companyId = pa.companyId and e.branchId = pa.branchId and e.cha = pa.partyId and pa.status != 'D' "
	        + "Left Join Profitcentre po on e.companyId = po.companyId and e.branchId = po.branchId and e.profitcentreId = po.profitcentreId and po.status != 'D' "
	        + "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' "
	        + "and (:searchValue is null OR :searchValue = '' OR c.sbNo LIKE %:searchValue% OR c.hSbTransId LIKE %:searchValue% OR c.sbTransId LIKE %:searchValue%) "
	        + "ORDER BY e.sbTransDate DESC")
	List<Object[]> getSbEntriesData(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                                @Param("searchValue") String searchValue);


	 
	 @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ExportSbCargoEntry e " +
	           "WHERE e.companyId = :companyId AND e.branchId = :branchId " +
	           "AND e.finYear = :finYear " +
	           "AND e.sbNo = :sbNo " +
	           "AND (:sbTransId IS NULL OR :sbTransId = '' OR e.sbTransId <> :sbTransId) " +
	           "AND (:profitcentreId IS NULL OR :profitcentreId = '' OR e.profitcentreId = :profitcentreId) " +
	           "AND e.status <> 'D'")
	    boolean existsBySbNo(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("finYear") String finYear,
	        @Param("profitcentreId") String profitcentreId,	        
	        @Param("sbTransId") String sbTransId,
	        @Param("sbNo") String sbNo
	    );
	 
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE ExportSbCargoEntry e " +
	        "SET e.sbNo = :newSbNo , e.sbDate = :newSbDate " +
	        "WHERE e.companyId = :companyId " +
	        "AND e.branchId = :branchId " +
	        "AND e.sbNo = :sbNo " +
	        "AND e.sbTransId = :sbTransId " +	       
	        "AND e.status <> 'D'")
	int updateChangeSbCargoNo(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbNo") String sbNo,
	        @Param("sbTransId") String sbTransId,
	        @Param("newSbNo") String newSbNo,
	        @Param("newSbDate") Date newSbDate
	);
	
	@Query("SELECT E FROM ExportSbCargoEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.sbNo = :sbNo " 
	        + "AND E.sbTransId = :sbTransId "
//	        + "AND E.sbLineNo = :sbLineNo "
	        + "AND E.status != 'D'")
	ExportSbCargoEntry getExportSbEntryBySbNoAndSbTransIdAndSbLine(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId);
	
	
	@Query(value = "select e.sbNo, e.sbTransId from ExportSbCargoEntry e where e.companyId = :cid and e.branchId = :bid "
			+ "and e.status != 'D' and e.cartedPackages > 0 and (e.stuffReqQty is null OR e.stuffReqQty <= 0) "
			+ "and (:val is null OR :val = '' OR e.sbNo LIKE CONCAT('%', :val, '%')) and "
			+ "(COALESCE(e.cartedPackages,0) - COALESCE(e.backToTownPack,0)) > 0")
	List<Object[]> searchDataForBacktoTown(@Param("cid") String cid, @Param("bid") String bid,
			@Param("val") String val);

	
	@Query(value = "select e.sbTransId,sb.sbTransDate,e.sbNo,e.sbDate,sb.exporterName,sb.exporterAddress1,sb.exporterAddress2,"
			+ "sb.exporterAddress3,sb.onAccountOf,p.partyName,e.commodity,e.numberOfMarks,e.cartedPackages,(e.cartedPackages - e.backToTownPack),"
			+ "e.grossWeight "
			+ "from ExportSbCargoEntry e "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and e.sbNo=sb.sbNo and e.sbTransId=sb.sbTransId "
			+ "LEFT OUTER JOIN Party p ON sb.companyId=p.companyId and sb.branchId=p.branchId and sb.onAccountOf=p.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' "
			+ "and e.sbNo=:sb and e.sbTransId=:trans")
	Object[] getSelectedBackToTownData(@Param("cid") String cid, @Param("bid") String bid,@Param("sb") String sb,
			@Param("trans") String trans);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Query(value="select e.sbTransId,e.sbNo,DATE_FORMAT(e.sbDate,'%d/%m/%Y'),sb.exporterName,sb.consigneeName,p1.partyName,sb.pod,sb.destinationCountry,e.commodity,"
			+ "e.noOfPackages,e.typeOfPackage,sb.outOfCharge,DATE_FORMAT(sb.outOfChargeDate,'%d/%m/%Y'),e.grossWeight,e.fob,e.drawBackValue "
			+ "from ExportSbCargoEntry e "
			+ "LEFT OUTER JOIN ExportSbEntry sb ON e.companyId=sb.companyId and e.branchId=sb.branchId and "
			+ "e.sbNo=sb.sbNo and e.sbTransId=sb.sbTransId and sb.status != 'D' "
			+ "LEFT OUTER JOIN Party p1 ON sb.companyId=p1.companyId and sb.branchId=p1.branchId and sb.cha=p1.partyId and p1.status != 'D' "
			+ "where e.companyId=:cid and e.branchId=:bid and e.sbNo=:sb and e.status != 'D' ")
	List<Object[]> getHistoryDataForSB(@Param("cid") String cid, @Param("bid") String bid,@Param("sb") String sb);
	
	
	@Query(value="select g.docRefNo,g.gateInId,DATE_FORMAT(g.inGateInDate,'%d/%m/%Y'),g.vehicleNo,g.actualNoOfPackages,"
			+ "g.qtyTakenIn,g.cargoWeight "
			+ "from GateIn g "
			+ "where g.companyId=:cid and g.branchId=:bid and g.docRefNo=:sb and g.status != 'D' order by g.inGateInDate asc")
	List<Object[]> getHistoryDataForGateIn(@Param("cid") String cid, @Param("bid") String bid,@Param("sb") String sb);
	
	@Query(value="select c.sbNo,c.cartingTransId,DATE_FORMAT(c.cartingTransDate,'%d/%m/%Y'),c.gateInPackages,c.actualNoOfPackages,"
			+ "c.areaOccupied,c.yardPackages "
			+ "from ExportCarting c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.sbNo=:sb and c.status != 'D' order by c.cartingTransDate asc")
	List<Object[]> getHistoryDataForCartingData(@Param("cid") String cid, @Param("bid") String bid,@Param("sb") String sb);
	
	@Query(value="select e.sbNo,p2.partyName,p1.partyName,p3.partyName,e.stuffReqId,DATE_FORMAT(e.stuffReqDate,'%d/%m/%Y'),"
			+ "e.noOfPackages,e.noOfPackagesStuffed,e.containerNo,e.containerSize,e.containerType,v.vesselName "
			+ "from ExportStuffRequest e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.shippingAgent=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.shippingLine=p2.partyId "
			+ "LEFT OUTER JOIN Party p3 ON e.companyId=p3.companyId and e.branchId=p3.branchId and e.onAccountOf=p3.partyId "
			+ "LEFT OUTER JOIN Vessel v ON e.companyId=v.companyId and e.branchId=v.branchId and e.vesselId=v.vesselId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and "
			+ "e.sbNo=:sb and e.sbTransId=:sbtrans order by e.stuffReqDate asc")
	List<Object[]> getHistoryDataForStuffReqData(@Param("cid") String cid, @Param("bid") String bid,@Param("sb") String sb,
			@Param("sbtrans") String sbtrans);
	
	
	@Query(value="select e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),e.cartingTransId,e.pol,e.pod,e.stuffRequestQty,"
			+ "e.stuffedQty,e.balanceQty,e.areaReleased,e.haz,e.clpStatus,e.containerNo,e.containerSize,e.containerType,"
			+ "e.agentSealNo,e.customsSealNo,e.movementReqId "
			+ "from ExportStuffTally e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.sbNo=:sb and e.sbTransId=:sbtrans and e.status != 'D' order by e.stuffTallyDate asc")
	List<Object[]> getHistoryDataForStuffTallyData(@Param("cid") String cid, @Param("bid") String bid,@Param("sb") String sb,
			@Param("sbtrans") String sbtrans);
	
	@Query(value="select e.movementReqId,DATE_FORMAT(e.movementReqDate,'%d/%m/%Y'),e.containerNo,e.containerSize,e.containerType,"
			+ "e.invoiceNo,e.gatePassNo,e.gateOutId,DATE_FORMAT(e.gateOutDate,'%d/%m/%Y') "
			+ "from ExportMovement e "
			+ "where e.companyId=:cid and e.branchId=:bid and e.movementReqId IN :val and e.status != 'D' order by e.movementReqDate asc")
	List<Object[]> getHistoryDataForExportMovementData(@Param("cid") String cid, @Param("bid") String bid,@Param("val") List<String> val);
	
	
	
	@Query(value="select e.containerNo,e.containerSize,e.containerType,s.exporterName,p3.partyName,p1.partyName,p2.partyName,e.containerStatus,"
			+ "mov.movReqType,e.containerSealNo,e.stuffReqId,DATE_FORMAT(e.stuffReqDate,'%d/%m/%Y'),e.stuffTallyId,DATE_FORMAT(e.stuffTallyDate,'%d/%m/%Y'),"
			+ "e.movementReqId,DATE_FORMAT(e.movementReqDate,'%d/%m/%Y'),mov.pol,mov.pod,COALESCE(SUM(s.stuffedQty),0),mov.grossWeight,e.gateInId,e.createdDate "
			+ "from ExportInventory e "
			+ "LEFT OUTER JOIN Party p1 ON e.companyId=p1.companyId and e.branchId=p1.branchId and e.sa=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON e.companyId=p2.companyId and e.branchId=p2.branchId and e.sl=p2.partyId "
			+ "LEFT OUTER JOIN ExportMovement mov ON e.companyId=mov.companyId and e.branchId=mov.branchId and e.movementReqId=mov.movementReqId "
			+ "LEFT OUTER JOIN ExportStuffTally s ON e.companyId=s.companyId and e.branchId=s.branchId and e.stuffTallyId=s.stuffTallyId and e.gateInId=s.gateInId "
			+ "LEFT OUTER JOIN Party p3 ON s.companyId=p3.companyId and s.branchId=p3.branchId and s.cha=p3.partyId "
			+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.containerNo=:con "
			+ "group by e.gateInId order by e.createdDate desc")
	List<Object[]> getHistoryDataWithContainerNo(@Param("cid") String cid, @Param("bid") String bid,@Param("con") String con);
	
	
	
	@Query("SELECT E.sbTransId,E.sbNo,E.sbLineNo FROM ExportSbCargoEntry E WHERE E.companyId = :companyId AND E.branchId = :branchId "
	        + "AND E.sbNo = :sbNo "
	        + "AND E.status = 'A'")
	Object getExportSbEntryBySbNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("sbNo") String sbNo);

	
}