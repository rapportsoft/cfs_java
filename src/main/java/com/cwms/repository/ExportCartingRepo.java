package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportCarting;
import com.cwms.entities.GateIn;

public interface ExportCartingRepo extends JpaRepository<ExportCarting, String>
{
	
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
	        + "E.damagePackages) "
	        + "FROM ExportCarting E "
	        + "LEFT JOIN Party o ON E.companyId = o.companyId AND E.branchId = o.branchId AND E.onAccountOf = o.partyId AND o.status != 'D' "
	        + "WHERE E.companyId = :companyId AND E.branchId = :branchId AND E.profitcentreId = :profitcentreId "
	        + "AND E.cartingTransId = :cartingTransId AND E.gateInType = :type AND E.status <> 'D'")
	List<ExportCarting> getSelectedCartingEntry(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("profitcentreId") String profitcentreId,
	    @Param("cartingTransId") String cartingTransId,
	    @Param("type") String type);

	
	
	
	
	
	
	
	
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
	
	
}