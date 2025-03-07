package com.cwms.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportCarting;

public interface ExportCartingRepo extends JpaRepository<ExportCarting, String>
{
	
	
	@Query("SELECT NEW com.cwms.entities.ExportCarting( "
	        + "E.companyId, E.branchId, E.cartingTransId, E.cartingLineId, E.finYear, "
	        + "E.profitcentreId, E.sbTransId, E.sbNo, E.sbLineNo, E.sbDate, "
	        + "E.cartingTransDate, E.gateInId, E.gateInDate, E.crgExamId, "
	        + "E.shift, E.vehicleNo, o.partyName, E.commodity, "
	        + "E.gateInPackages, E.gateInWeight, E.actualNoOfPackages, E.actualNoOfWeight, "
	        + "E.fob, E.gateInType, E.invoiceType, E.gridLocation, "
	        + "E.gridBlock, E.gridCellNo, E.stuffReqId, E.stuffedNoOfPackages, "
	        + "E.areaOccupied, E.yardPackages, E.damageComments, E.status, "
	        + "E.createdBy, E.createdDate, E.editedBy, E.editedDate, "
	        + "E.approvedBy, E.approvedDate, E.fromSbTransId, E.fromSbNo, "
	        + "E.fromSbLineNo, E.lastStorageInvoiceDate, E.lastStorageFlag, "
	        + "E.storageWeeks, E.storageDays, E.storageMonths, "
	        + "E.handlingCharges, E.excessPackages, E.shortagePackages, "
	        + "E.damagePackages, g.cartedPackages, E.gridLoc, ex.noOfPackages, ex.gateInPackages, ex.cartedPackages ) "
	        + "FROM ExportCarting E "
	        + "LEFT JOIN Party o ON E.companyId = o.companyId AND E.branchId = o.branchId AND E.onAccountOf = o.partyId AND o.status != 'D' "
	        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.profitcentreId = g.profitcentreId AND E.sbNo = g.docRefNo AND E.sbTransId = g.erpDocRefNo AND E.gateInId = g.gateInId AND g.status != 'D' "
	        + "LEFT JOIN ExportSbCargoEntry ex ON E.companyId = ex.companyId AND E.branchId = ex.branchId AND E.sbTransId = ex.sbTransId AND E.sbNo = ex.sbNo AND ex.status <> 'D' "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId AND E.profitcentreId = :profitcentreId "
	        + "AND E.cartingTransId = :cartingTransId AND E.gateInType = :type AND E.status <> 'D'")
	List<ExportCarting> getSelectedCartingEntry(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("profitcentreId") String profitcentreId,
	    @Param("cartingTransId") String cartingTransId,
	    @Param("type") String type);
	
	@Query("SELECT NEW com.cwms.entities.ExportCarting(E.cartingTransId, E.cartingLineId, E.sbNo, E.profitcentreId, E.createdBy) " +
		       "FROM ExportCarting E " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.sbNo = :sbNo " +	
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.sbTransId = :sbTransId " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
	Page<ExportCarting> getDataForExportMainSearchCarting(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId,
		                                   @Param("profitcentreId") String profitcentreId, @Param("sbNo") String sbNo,	
		                                   @Param("sbTransId") String sbTransId, Pageable pageable);	
		     
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.profitcentreId = :profitcentreId " 
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.gateInId = :gateInId "
			+ "AND E.status <> 'D'")
	List<ExportCarting> getCartingForSbTransfer(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("gateInId") String gateInId,	@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);
	
	
	
//	@Query("SELECT NEW com.cwms.entities.ExportCarting( "
//	        + "E.companyId, E.branchId, E.cartingTransId, E.cartingLineId, E.finYear, "
//	        + "E.profitcentreId, E.sbTransId, E.sbNo, E.sbLineNo, E.sbDate, "
//	        + "E.cartingTransDate, E.gateInId, E.gateInDate, E.crgExamId, "
//	        + "E.shift, E.vehicleNo, o.partyName, E.commodity, "
//	        + "E.gateInPackages, E.gateInWeight, E.actualNoOfPackages, E.actualNoOfWeight, "
//	        + "E.fob, E.gateInType, E.invoiceType, E.gridLocation, "
//	        + "E.gridBlock, E.gridCellNo, E.stuffReqId, E.stuffedNoOfPackages, "
//	        + "E.areaOccupied, E.yardPackages, E.damageComments, E.status, "
//	        + "E.createdBy, E.createdDate, E.editedBy, E.editedDate, "
//	        + "E.approvedBy, E.approvedDate, E.fromSbTransId, E.fromSbNo, "
//	        + "E.fromSbLineNo, E.lastStorageInvoiceDate, E.lastStorageFlag, "
//	        + "E.storageWeeks, E.storageDays, E.storageMonths, "
//	        + "E.handlingCharges, E.excessPackages, E.shortagePackages, "
//	        + "E.damagePackages, g.cartedPackages) "
//	        + "FROM ExportCarting E "
//	        + "LEFT JOIN Party o ON E.companyId = o.companyId AND E.branchId = o.branchId AND E.onAccountOf = o.partyId AND o.status != 'D' "
//	        + "LEFT JOIN GateIn g ON E.companyId = g.companyId AND E.branchId = g.branchId AND E.profitcentreId = g.profitcentreId AND E.sbNo = g.docRefNo AND E.sbTransId = g.erpDocRefNo AND E.gateInId = g.gateInId AND g.status != 'D' "
//	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId AND E.profitcentreId = :profitcentreId "
//	        + "AND E.cartingTransId = :cartingTransId AND E.gateInType = :type AND E.status <> 'D'")
//	List<ExportCarting> getSelectedCartingEntry(
//	    @Param("companyId") String companyId,
//	    @Param("branchId") String branchId,
//	    @Param("profitcentreId") String profitcentreId,
//	    @Param("cartingTransId") String cartingTransId,
//	    @Param("type") String type);

	
	
	
	@Query("SELECT NEW com.cwms.entities.ExportCarting(E.cartingTransId, E.cartingLineId, E.sbNo, E.profitcentreId, E.createdBy) " +
		       "FROM ExportCarting E " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.sbNo = :sbNo " +		     
		       "AND E.cartingTransId = :cartingTransId " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
		ExportCarting cartingsForMainSearch(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId,
		                                   @Param("sbNo") String sbNo,		                                  
		                                   @Param("cartingTransId") String cartingTransId);

	
	
	@Query("SELECT NEW com.cwms.entities.ExportCarting(E.cartingTransId, E.sbTransId, E.sbNo, E.vehicleNo) " +
		       "FROM ExportCarting E " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +		     
		       "AND E.cartingTransId = :cartingTransId " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.createdDate DESC")
		List<ExportCarting> cartingsForMultipleEquipment(@Param("companyId") String companyId, 
		                                   @Param("branchId") String branchId,
		                                   @Param("profitcentreId") String profitcentreId,		                                  
		                                   @Param("cartingTransId") String cartingTransId);

	
	
	@Query(value = "select c.profitcentreId, c.cartingTransId, c.cartingLineId, c.cartingTransDate, c.sbNo, c.sbLineNo, c.status "
			+ "from ExportCarting c "
			+ "where c.companyId = :companyId and c.branchId = :branchId and c.status != 'D' and c.gateInType='EXP' "
			+ "and (:searchValue is null OR :searchValue = '' OR c.cartingTransId LIKE %:searchValue% OR c.sbNo LIKE %:searchValue% OR c.vehicleNo LIKE %:searchValue%) "
			+ "ORDER BY c.cartingTransDate DESC")
	List<Object[]> getCartingEntriesData(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("searchValue") String searchValue);	
	
	
//	@Query("SELECT NEW com.cwms.entities.ExportCarting( "
//	        + "E.companyId, E.branchId, E.cartingTransId, E.cartingLineId, E.finYear, "
//	        + "E.profitcentreId, E.sbTransId, E.sbNo, E.sbLineNo, E.sbDate, "
//	        + "E.cartingTransDate, E.gateInId, E.gateInDate, E.crgExamId, "
//	        + "E.shift, E.vehicleNo, o.partyName, E.commodity, "
//	        + "E.gateInPackages, E.gateInWeight, E.actualNoOfPackages, E.actualNoOfWeight, "
//	        + "E.fob, E.gateInType, E.invoiceType, E.gridLocation, "
//	        + "E.gridBlock, E.gridCellNo, E.stuffReqId, E.stuffedNoOfPackages, "
//	        + "E.areaOccupied, E.yardPackages, E.damageComments, E.status, "
//	        + "E.createdBy, E.createdDate, E.editedBy, E.editedDate, "
//	        + "E.approvedBy, E.approvedDate, E.fromSbTransId, E.fromSbNo, "
//	        + "E.fromSbLineNo, E.lastStorageInvoiceDate, E.lastStorageFlag, "
//	        + "E.storageWeeks, E.storageDays, E.storageMonths, "
//	        + "E.handlingCharges, E.excessPackages, E.shortagePackages, "
//	        + "E.damagePackages) "
//	        + "FROM ExportCarting E "
//	        + "LEFT JOIN Party o ON E.companyId = o.companyId AND E.branchId = o.branchId AND E.onAccountOf = o.partyId AND o.status != 'D' "
//	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId AND E.profitcentreId = :profitcentreId "
//	        + "AND E.cartingTransId = :cartingTransId AND E.gateInType = :type AND E.status <> 'D'")
//	List<ExportCarting> getSelectedCartingEntry(
//	    @Param("companyId") String companyId,
//	    @Param("branchId") String branchId,
//	    @Param("profitcentreId") String profitcentreId,
//	    @Param("cartingTransId") String cartingTransId,
//	    @Param("type") String type);
//
//	
	
	
	
	
	
	
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.profitcentreId = :profitcentreId " 
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.gateInId = :gateInId "
			+ "AND E.cartingTransId = :cartingTransId "
			+ "AND E.status <> 'D'")
	ExportCarting getCartingByIds(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("gateInId") String gateInId, @Param("cartingTransId") String cartingTransId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);
	
	
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.sbLineNo = :sbline "
			+ "AND (E.actualNoOfPackages -  E.stuffedNoOfPackages) > 0 "
			+ "AND E.status <> 'D' order by (E.actualNoOfPackages -  E.stuffedNoOfPackages) asc")
	List<ExportCarting> getDataBySbNoSbTransAndLineNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo, @Param("sbline") String sbline);
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.cartingTransId = :cartrans "
			+ "AND E.cartingLineId = :cartline "
			+ "AND E.status <> 'D'")
	ExportCarting getDataBySbNoSbTransAndLineNoAndCartingTransId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo,@Param("cartrans") String cartrans, @Param("cartline") String cartline);
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.status <> 'D' order by (E.actualNoOfPackages -  E.stuffedNoOfPackages) asc")
	List<ExportCarting> getDataBySbNoSbTrans(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);
	
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND (E.actualNoOfPackages -  E.stuffedNoOfPackages) > 0 "
			+ "AND E.status <> 'D' order by (E.actualNoOfPackages -  E.stuffedNoOfPackages) asc")
	List<ExportCarting> getDataBySbNoSbTrans1(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);

	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.status <> 'D'")
	List<ExportCarting> getDataBySbNoSbTrans2(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.cartingTransId = :id "
			+ "AND E.sbTransId = :sbTransId "
			+ "AND E.sbNo = :sbNo "
			+ "AND E.status <> 'D'")
	ExportCarting getDataByCartingTransIdSBTransAndSb(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("id") String id,@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);
	
	
	@Query("SELECT E.cartingTransId, E.cartingLineId, E.sbTransId, E.sbNo, E.commodity,"
			+ "E.gateInPackages, E.gateInWeight, E.actualNoOfPackages,"
			+ "E.actualNoOfWeight, E.fob, E.gridLocation, E.gridBlock, E.gridCellNo,"
			+ "E.areaOccupied, E.yardPackages, i.cellAreaAllocated, i.yardPackages "
			+ "FROM ExportCarting E "
			+ "LEFT OUTER JOIN Impexpgrid i ON E.companyId=i.companyId and E.branchId=i.branchId and E.cartingTransId=i.processTransId "
			+ "and E.cartingLineId=CAST(i.lineNo AS String) and E.gridLocation=i.yardLocation and E.gridBlock=i.yardBlock and E.gridCellNo=i.blockCellNo " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.cartingTransId = :id "
			+ "AND E.status <> 'D'")
	List<Object[]> getDataByCartingTransId1(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("id") String id);
	
	
	@Query("SELECT E FROM ExportCarting E " 
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.cartingTransId = :cartrans "
			+ "AND E.cartingLineId = :cartline "
			+ "AND E.status <> 'D'")
	ExportCarting getDataByLineNoAndCartingTransId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("cartrans") String cartrans, @Param("cartline") String cartline);
	
	
	
	
	//Reports
	
		@Query(value="select e.cartingTransId,DATE_FORMAT(e.cartingTransDate,'%d/%m/%Y %H:%i'),e.vehicleNo,e.sbNo,"
				+ "e.gateInPackages,e.actualNoOfPackages "
				+ "from ExportCarting e "
				+ "where e.companyId=:cid and e.branchId=:bid and e.status != 'D' and e.cartingTransId=:id")
		List<Object[]> getDataForCartingreport(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
}