package com.cwms.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportSbCargoEntry;
import com.cwms.entities.ExportSbEntry;

import jakarta.transaction.Transactional;

public interface ExportSbCargoEntryRepo extends JpaRepository<ExportSbCargoEntry, String>
{
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
	        + "AND E.sbLineNo = :sbLineNo "
	        + "AND E.status != 'D'")
	ExportSbCargoEntry getExportSbEntryBySbNoAndSbTransIdAndSbLine(@Param("companyId") String companyId, @Param("branchId") String branchId,
	                              @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId,@Param("sbLineNo") String sbLineNo);
	
}