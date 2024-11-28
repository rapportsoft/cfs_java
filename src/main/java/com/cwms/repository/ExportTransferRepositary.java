package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportTransfer;
import com.cwms.entities.GateIn;

public interface ExportTransferRepositary extends JpaRepository<ExportTransfer, String> {

	
	
	@Query("SELECT NEW com.cwms.entities.ExportTransfer(" +
		       "E.companyId, E.branchId, E.sbChangeTransId, E.srNo, E.finYear, " +
		       "E.profitcentreId, E.sbTransId, E.trfSbTransId, E.sbLineNo, E.trfSbLineNo, " +
		       "E.sbNo, E.trfSbNo, E.sbChangeTransDate, E.sbTransDate, E.exporter, E.cha, " +
		       "E.onAccountOf, E.transferPackages, E.transferGrossWeight, E.sbDate, E.trfSbDate, " +
		       "E.noOfPackages, E.trfNoOfPackages, E.gateInPackages, E.trfGateInPackages, " +
		       "E.cartedPackages, E.trfCartedPackages, E.nilPackages, E.grossWeight, E.trfGrossWeight, " +
		       "E.gateInWeight, E.trfGateInWeight, E.cartedWeight, E.trfCartedWeight, " +
		       "E.prevTransferPack, E.stuffedQty, E.backToTownPack, E.commodity, E.toCommodity, " +
		       "E.comments, E.status, E.createdBy, E.createdDate, E.editedBy, E.editedDate, " +
		       "E.approvedBy, E.approvedDate, E.nilFlag, e.partyName, c.partyName) " +
		       "FROM ExportTransfer E " +
		       "LEFT JOIN Party c ON E.companyId = c.companyId AND E.branchId = c.branchId AND E.cha = c.partyId AND c.status != 'D' " +
		       "LEFT JOIN Party e ON E.companyId = e.companyId AND E.branchId = e.branchId AND E.exporter = e.partyId AND e.status != 'D' " +		
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.srNo = :srNo " +
		       "AND E.sbChangeTransId = :sbChangeTransId " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.sbChangeTransDate DESC")
		ExportTransfer getSelectedTransferEntry(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("profitcentreId") String profitcentreId,
		        @Param("sbChangeTransId") String sbChangeTransId,
		        @Param("srNo") String srNo
		        );

	

	@Query("SELECT NEW com.cwms.entities.ExportTransfer(" +
		       "E.sbChangeTransId, E.profitcentreId, E.sbTransId, E.trfSbTransId, " +
		       "E.sbNo, E.trfSbNo, E.sbChangeTransDate, E.sbDate, E.trfSbDate, " +
		       "E.status, po.profitcentreDesc,E.srNo) " +
		       "FROM ExportTransfer E " +
		       "LEFT JOIN Profitcentre po ON E.companyId = po.companyId AND E.branchId = po.branchId AND E.profitcentreId = po.profitcentreId AND po.status != 'D' " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND (:searchValue IS NULL OR :searchValue = '' OR " +
		       "E.sbChangeTransId LIKE %:searchValue% OR E.sbNo LIKE %:searchValue% OR " +
		       "E.sbTransId LIKE %:searchValue% OR E.trfSbNo LIKE %:searchValue% OR E.trfSbTransId LIKE %:searchValue%) " +
		       "AND E.status <> 'D' " +
		       "ORDER BY E.sbChangeTransDate DESC")
		List<ExportTransfer> getSavedTransferRecords(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("profitcentreId") String profitcentreId,
		        @Param("searchValue") String searchValue);

		
	
	
	
	@Query("SELECT E.sbNo, E.sbTransId, E.srno, E.sbDate, g.sbTransDate,E.commodity, E.noOfPackages, E.grossWeight, g.exporterId, g.exporterName, g.cha, pca.partyName, E.stuffReqQty, E.gateInPackages, E.backToTownPack, E.transferPackages, g.onAccountOf,E.cartedPackages, E.nilPackages,(E.gateInPackages - E.stuffReqQty) " +
		       "FROM ExportSbCargoEntry E " +
		       "LEFT JOIN ExportSbEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.sbNo AND g.sbTransId = E.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
		       "LEFT JOIN Party pca ON E.companyId = pca.companyId AND E.branchId = pca.branchId AND g.cha = pca.partyId AND pca.status <> 'D' " +
		       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
		       "AND (:searchValue IS NULL OR :searchValue = '' OR E.sbNo LIKE CONCAT('%', :searchValue, '%')) " +
		       "AND (E.noOfPackages - E.stuffReqQty) > 0 " +
		       "AND E.gateInPackages > 0 " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.status <> 'D'")
		List<Object[]> searchSbNoForTransferFrom(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("searchValue") String searchValue, 
		    @Param("profitcentreId") String profitcentreId
		);
		
		@Query("SELECT E.sbNo, E.sbTransId, E.srno, E.sbDate, g.sbTransDate,E.commodity, E.noOfPackages, E.grossWeight, g.exporterId, g.exporterName, g.cha, E.stuffReqQty, E.gateInPackages, E.backToTownPack, E.transferPackages, g.onAccountOf " +
			       "FROM ExportSbCargoEntry E " +
			       "LEFT JOIN ExportSbEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.sbNo AND g.sbTransId = E.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
			       "WHERE E.companyId = :companyId AND E.branchId = :branchId " +
			       "AND (:searchValue IS NULL OR :searchValue = '' OR E.sbNo LIKE CONCAT('%', :searchValue, '%')) " +
			       "AND (E.noOfPackages - E.gateInPackages) > 0 " +
			       "AND E.profitcentreId = :profitcentreId " +
			       "AND E.status <> 'D'")
			List<Object[]> searchSbNoForTransferTo(
			    @Param("companyId") String companyId, 
			    @Param("branchId") String branchId, 
			    @Param("searchValue") String searchValue, 
			    @Param("profitcentreId") String profitcentreId
			);
	
}
