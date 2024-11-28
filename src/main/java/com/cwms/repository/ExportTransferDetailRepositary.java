package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.ExportStuffRequest;
import com.cwms.entities.ExportTransferDetail;
import com.cwms.entities.GateIn;

public interface ExportTransferDetailRepositary  extends JpaRepository<ExportTransferDetail, String>{

	

	@Query("SELECT E FROM ExportTransferDetail E " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.transLineId = :srNo " +
		       "AND E.sbChangeTransId = :sbChangeTransId " +
		       "AND E.status <> 'D'")
		List<ExportTransferDetail> getSelectedTransferDetailEntry(
		        @Param("companyId") String companyId, 
		        @Param("branchId") String branchId,
		        @Param("sbChangeTransId") String sbChangeTransId,
		        @Param("srNo") String srNo
		);

	
	
//	@Query("SELECT E.gateInId, E.inGateInDate, E.vehicleNo, E.qtyTakenIn, E.cartedPackages, " +
//		       "(E.qtyTakenIn - g.stuffReqQty) AS difference, E.nilPackages, E.srNo,E.cargoWeight,E.actualNoOfPackages,g.stuffReqQty " +
//		       "FROM GateIn E " +
//		       "LEFT JOIN ExportSbCargoEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.docRefNo AND g.erpDocRefNo = E.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
//		       "WHERE E.companyId = :companyId " +
//		       "AND E.branchId = :branchId " +
//		       "AND E.profitcentreId = :profitcentreId " +
//		       "AND E.docRefNo = :sbNo " +
//		       "AND E.erpDocRefNo = :sbTransId " +
//		       "AND E.status <> 'D'")
//		List<Object[]> gateTateInEntriesFromSbNo(
//		        @Param("companyId") String companyId,
//		        @Param("branchId") String branchId,
//		        @Param("profitcentreId") String profitcentreId,
//		        @Param("sbNo") String sbNo,
//		        @Param("sbTransId") String sbTransId
//		);

	
//	@Query("SELECT E.gateInId, E.inGateInDate, E.vehicleNo, E.qtyTakenIn, E.cartedPackages, " +
//		       "GREATEST((E.qtyTakenIn - g.stuffReqQty), 0) AS difference, E.nilPackages, E.srNo, E.cargoWeight, E.actualNoOfPackages, g.stuffReqQty " +
//		       "FROM GateIn E " +
//		       "LEFT JOIN ExportSbCargoEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.docRefNo AND E.erpDocRefNo = g.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
//		       "WHERE E.companyId = :companyId " +
//		       "AND E.branchId = :branchId " +
//		       "AND E.profitcentreId = :profitcentreId " +
//		       "AND E.docRefNo = :sbNo " +
//		       "AND E.erpDocRefNo = :sbTransId " +
//		       "AND E.status <> 'D'")
//		List<Object[]> gateTateInEntriesFromSbNo(
//		        @Param("companyId") String companyId,
//		        @Param("branchId") String branchId,
//		        @Param("profitcentreId") String profitcentreId,
//		        @Param("sbNo") String sbNo,
//		        @Param("sbTransId") String sbTransId
//		);

	
	@Query("SELECT E.gateInId, E.inGateInDate, E.vehicleNo, E.qtyTakenIn, E.cartedPackages, " +
		       "g.stuffReqQty, E.nilPackages, E.srNo, E.cargoWeight, E.actualNoOfPackages " +
		       "FROM GateIn E " +
		       "LEFT JOIN ExportSbCargoEntry g ON E.companyId = g.companyId AND E.branchId = g.branchId AND g.sbNo = E.docRefNo AND E.erpDocRefNo = g.sbTransId AND g.profitcentreId = E.profitcentreId AND g.status <> 'D' " +
		       "WHERE E.companyId = :companyId " +
		       "AND E.branchId = :branchId " +
		       "AND E.profitcentreId = :profitcentreId " +
		       "AND E.docRefNo = :sbNo " +
		       "AND E.erpDocRefNo = :sbTransId " +
		       "AND E.status <> 'D'")
		List<Object[]> gateTateInEntriesFromSbNo(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("profitcentreId") String profitcentreId,
		        @Param("sbNo") String sbNo,
		        @Param("sbTransId") String sbTransId
		);
	
	
}
