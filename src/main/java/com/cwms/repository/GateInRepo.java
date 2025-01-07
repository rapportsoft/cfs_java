package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.GateIn;

public interface GateInRepo extends JpaRepository<GateIn, String> {
	
	 @Query("SELECT g FROM GateIn g WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.docRefNo = :docRefNo AND g.erpDocRefNo = :erpDocRefNo AND g.gateInId = :gateInId AND g.commodity = :commodity")
	GateIn findAllRecordsByCriteria(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("docRefNo") String docRefNo,
	        @Param("erpDocRefNo") String erpDocRefNo,
	        @Param("gateInId") String gateInId,
	        @Param("commodity") String commodity
	    );
	 
	
	 
	 
	@Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.qtyTakenIn, g.weightTakenIn) " +
		       "FROM GateIn g " +
		       "WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.docRefNo = :docRefNo " +
		       "AND g.erpDocRefNo = :erpDocRefNo AND g.gateInId = :gateInId AND g.commodity = :commodity")
		GateIn findRecordByCriteria(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("docRefNo") String docRefNo,
		        @Param("erpDocRefNo") String erpDocRefNo,
		        @Param("gateInId") String gateInId,
		        @Param("commodity") String commodity);

	 
	 
	 

//	 @Query("SELECT g FROM GateIn g WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.gateInId = :gateInId AND g.srNo =:srNo")
//		List<GateIn> getDataOfRowUsingGateInId(
//	        @Param("companyId") String companyId,
//	        @Param("branchId") String branchId,
//	        @Param("gateInId") String gateInId,
//	        @Param("srNo") int srNo
//	    );
	 
	 
//	 @Query("SELECT g FROM GateIn g WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.gateInId = :gateInId")
//		List<GateIn> getDataOfRowUsingGateInId(
//	        @Param("companyId") String companyId,
//	        @Param("branchId") String branchId,
//	        @Param("gateInId") String gateInId
//	    );
//	 
	 
	 
	
	
//	 @Query("SELECT g, p.partyName FROM GateIn g LEFT JOIN Party p ON g.companyId = p.companyId AND g.branchId = p.branchId AND g.cha = p.partyId WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.gateInId = :gateInId")
//	 List<GateIn> getDataOfRowUsingGateInId(
//	     @Param("companyId") String companyId,
//	     @Param("branchId") String branchId,
//	     @Param("gateInId") String gateInId
//	 );
	
	
	@Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, g.grossWeight, g.eirGrossWeight, g.cha, g.importerName, g.commodityDescription, g.actualNoOfPackages, g.qtyTakenIn, g.shift, g.transporterStatus, g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, g.createdBy, p.partyName, g.approvedBy, g.inGateInDate, g.weightTakenIn, g.typeOfPackage,g.commodity,cd.gateInPackages) " +
		       "FROM GateIn g LEFT JOIN Party p ON g.companyId = p.companyId AND g.branchId = p.branchId AND g.cha = p.partyId " +
		       " LEFT JOIN CfBondNocDtl cd ON g.companyId = cd.companyId AND g.branchId = cd.branchId AND g.erpDocRefNo = cd.nocTransId and g.docRefNo=cd.nocNo and g.commodity =cd.cfBondDtlId " +
		       "WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.gateInId = :gateInId")
		List<GateIn> getDataOfRowUsingGateInId(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("gateInId") String gateInId);


	 
//	 @Query("SELECT g FROM GateIn g WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.docRefNo = :docRefNo AND g.erpDocRefNo = :erpDocRefNo AND g.gateInId = :gateInId AND g.commodity = :commodity")
//		List<GateIn> findAllRecordFORTOATL(
//		        @Param("companyId") String companyId,
//		        @Param("branchId") String branchId,
//		        @Param("docRefNo") String docRefNo,
//		        @Param("erpDocRefNo") String erpDocRefNo,
//		        @Param("gateInId") String gateInId,
//		        @Param("commodity") String commodity
//		    );
	 
	 
	 
	 @Query("SELECT g FROM GateIn g WHERE g.companyId = :companyId AND g.branchId = :branchId AND g.docRefNo = :docRefNo AND g.erpDocRefNo = :erpDocRefNo AND g.gateInId = :gateInId AND g.commodity = :commodity")
		GateIn findAllRecordFORTOATL(
		        @Param("companyId") String companyId,
		        @Param("branchId") String branchId,
		        @Param("docRefNo") String docRefNo,
		        @Param("erpDocRefNo") String erpDocRefNo,
		        @Param("gateInId") String gateInId,
		        @Param("commodity") String commodity
		    );
	 
	 
	 
	 

	 @Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
		       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
		       "g.grossWeight, g.eirGrossWeight, a.partyName, pa.partyName, g.commodityDescription, g.actualNoOfPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
		       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, g.createdBy, g.editedBy, g.approvedBy, g.inGateInDate, g.weightTakenIn,g.typeOfPackage,g.commodity,g.gateInPackages) " +
		       "FROM GateIn g " +
		       "LEFT OUTER JOIN Party pa ON g.companyId = pa.companyId AND g.branchId = pa.branchId AND g.importerName = pa.partyId " +
		       "LEFT OUTER JOIN Party a ON g.companyId = a.companyId AND g.branchId = a.branchId AND g.cha = a.partyId " +
		       "WHERE g.companyId = :companyId " +
		       "AND g.branchId = :branchId " +
		       "AND g.status != 'D' AND g.gateInType = 'BON' " +
		       "AND ((:search IS NULL OR :search = '' OR g.gateInId LIKE concat (:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR g.shift LIKE concat (:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR g.transporterName LIKE concat (:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR g.transporterStatus LIKE concat (:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR g.vehicleNo LIKE concat (:search, '%')) " +
		       "OR (:search IS NULL OR :search = '' OR g.importerName LIKE concat (:search, '%'))) " +
		       "GROUP BY g.gateInId "+
		   "ORDER BY g.gateInId DESC")
	 List<GateIn> findGateInByCriteria(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("search") String search);
	 
	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
		       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
		       "g.grossWeight, g.eirGrossWeight, g.cha, g.importerName, g.commodityDescription, g.actualNoOfPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
		       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, g.createdBy, g.editedBy, g.approvedBy, g.inGateInDate, g.weightTakenIn, g.typeOfPackage, g.commodity,g.gateInPackages) " +
		       "FROM GateIn g " +
		       "WHERE g.companyId = :companyId " +
		       "AND g.branchId = :branchId " +
		       "AND g.gateInId = :gateInId " +
		       "AND g.status != 'D'")
		List<GateIn> getAllDataOfGateInBond(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("gateInId") String gateInId);

	 
	 
	 
	 @Query("SELECT DISTINCT NEW com.cwms.entities.GateIn( " +
		       "g.gateInId, g.erpDocRefNo, g.docRefNo, " +
		       "g.boeNo " +
		       ") " +
		       "FROM GateIn g " +
		       "WHERE g.companyId = :companyId " +
		       "AND g.branchId = :branchId " +
		       "AND g.gateInId = :gateInId " +
		       "AND g.status != 'D'")
		List<GateIn> getDistinct(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("gateInId") String gateInId);


	 
	 
//	 @Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
//		       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
//		       "cf.grossWeight, g.eirGrossWeight, a.partyName, pa.partyName, g.commodityDescription, cf.nocPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
//		       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, add.address1, add.address2, add.address3, g.inGateInDate, g.weightTakenIn,g.typeOfPackage,g.commodity) " +
//		       "FROM GateIn g " +
//		       "LEFT OUTER JOIN Party pa ON g.companyId = pa.companyId AND g.branchId = pa.branchId AND g.importerName = pa.partyId " +
//		       "LEFT OUTER JOIN Cfbondnoc cf ON g.companyId = cf.companyId AND g.branchId = cf.branchId AND g.docRefNo = cf.nocNo AND g.erpDocRefNo =cf.nocTransId " +
//		       "LEFT OUTER JOIN Party a ON g.companyId = a.companyId AND g.branchId = a.branchId AND g.cha = a.partyId " +
//		       "LEFT OUTER JOIN PartyAddress add ON g.companyId = add.companyId AND g.branchId = add.branchId AND g.importerName = add.partyId " +
//		       "WHERE g.companyId = :companyId " +
//		       "AND g.branchId = :branchId " +
//		       "AND g.gateInId = :gateInId " +
//		       "AND g.erpDocRefNo = :erpDocRefNo " +
//		       "AND g.status != 'D' ")
//	 List<GateIn> findDataForGettingPrint(
//		       @Param("companyId") String companyId,
//		       @Param("branchId") String branchId,
//		       @Param("gateInId") String gateInId,
//		       @Param("erpDocRefNo") String erpDocRefNo);
//
//	 
//	 
//
//	 
//	 
//	 
//	 
//	 @Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
//		       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
//		       "g.grossWeight, g.eirGrossWeight, g.cha, g.importerName, g.commodityDescription, g.actualNoOfPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
//		       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, '', '', '', g.inGateInDate, g.weightTakenIn,g.typeOfPackage,g.commodity) " +
//		       "FROM GateIn g " +
//		       "WHERE g.companyId = :companyId " +
//		       "AND g.branchId = :branchId " +
//		       "AND g.gateInId = :gateInId " +
//		       "AND g.erpDocRefNo = :erpDocRefNo " +
//		       "AND g.status != 'D' ")
//	 List<GateIn> findDataForGettingPrintDetails(
//		       @Param("companyId") String companyId,
//		       @Param("branchId") String branchId,
//		       @Param("gateInId") String gateInId,
//		       @Param("erpDocRefNo") String erpDocRefNo);
	 
	 
	 @Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
		       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
		       "cf.grossWeight, g.eirGrossWeight, a.partyName, pa.partyName, g.commodityDescription, cf.nocPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
		       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, add.address1, add.address2, add.address3, g.inGateInDate, g.weightTakenIn,g.typeOfPackage,g.commodity,g.gateInPackages) " +
		       "FROM GateIn g " +
		       "LEFT OUTER JOIN Party pa ON g.companyId = pa.companyId AND g.branchId = pa.branchId AND g.importerName = pa.partyId " +
		       "LEFT OUTER JOIN Cfbondnoc cf ON g.companyId = cf.companyId AND g.branchId = cf.branchId AND g.docRefNo = cf.nocNo AND g.erpDocRefNo =cf.nocTransId " +
		       "LEFT OUTER JOIN Party a ON g.companyId = a.companyId AND g.branchId = a.branchId AND g.cha = a.partyId " +
		       "LEFT OUTER JOIN PartyAddress add ON g.companyId = add.companyId AND g.branchId = add.branchId AND g.importerName = add.partyId " +
		       "WHERE g.companyId = :companyId " +
		       "AND g.branchId = :branchId " +
		       "AND g.gateInId = :gateInId " +
		       "AND g.erpDocRefNo = :erpDocRefNo " +
		       "AND g.boeNo = :boeNo " +
//		       "AND g.srNo = :srNo " +
		       "AND g.status != 'D' ")
	 List<GateIn> findDataForGettingPrint(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("gateInId") String gateInId,
		       @Param("erpDocRefNo") String erpDocRefNo,
		       @Param("boeNo") String boeNo
//		       @Param("srNo") int srNo
		       )
	 ;

	 
	 

	 
	 
	 
	 
	 @Query("SELECT NEW com.cwms.entities.GateIn(g.companyId, g.branchId, g.gateInId, g.finYear, g.erpDocRefNo, g.docRefNo, g.lineNo, g.srNo, g.docRefDate, " +
		       "g.boeNo, g.boeDate, g.nocNo, g.nocDate, g.gateInType, g.profitcentreId, g.containerNo, g.containerSize, g.containerType, g.isoCode, " +
		       "g.grossWeight, g.eirGrossWeight, g.cha, g.importerName, g.commodityDescription, g.actualNoOfPackages, g.qtyTakenIn, g.shift, g.transporterStatus, " +
		       "g.transporterName, g.transporter, g.vehicleNo, g.driverName, g.status, '', '', '', g.inGateInDate, g.weightTakenIn,g.typeOfPackage,g.commodity,g.gateInPackages) " +
		       "FROM GateIn g " +
		       "WHERE g.companyId = :companyId " +
		       "AND g.branchId = :branchId " +
		       "AND g.gateInId = :gateInId " +
		       "AND g.erpDocRefNo = :erpDocRefNo " +
		       "AND g.boeNo = :boeNo " +
//		       "AND g.srNo = :srNo " +
		       "AND g.status != 'D' ")
	 List<GateIn> findDataForGettingPrintDetails(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("gateInId") String gateInId,
		       @Param("erpDocRefNo") String erpDocRefNo,
		       @Param("boeNo") String boeNo
//		       @Param("srNo") int srNo
		       );
}
