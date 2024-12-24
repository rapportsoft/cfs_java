package com.cwms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cwms.entities.GateIn;

public interface ExportReportRepositary extends JpaRepository<GateIn, String>{
	
	
	@Query(value = "SELECT a.gateInId, " +
            "IFNULL(DATE_FORMAT(a.inGateInDate,'%d/%m/%Y %H:%i'), '') AS Gate_In_Date, " +
            "a.docRefNo, IFNULL(DATE_FORMAT(a.docRefDate,'%d/%m/%Y %H:%i'), '') AS Doc_Ref_Date, " +
            "a.vehicleNo, c.exporterName, p.partyName, b.noOfPackages, b.grossWeight, " +
            "a.transporterName, b.typeOfPackage, " +
            "a.qtyTakenIn, a.cargoWeight, b.haz, a.createdBy, GROUP_CONCAT(jj.jarDtlDesc) AS Equipment, " +
            "n1.partyName AS SA, a.jobOrderId, DATE_FORMAT(a.jobDate,'%d %b %Y') AS JOB_Date, q.partyName, b.newCommodity " +
            "FROM GateIn a " +
            "LEFT JOIN ExportSbCargoEntry b ON b.companyId = a.companyId " +
            "AND b.branchId = a.branchId AND b.profitcentreId = a.profitcentreId " +
            "AND b.sbTransId = a.erpDocRefNo AND b.sbNo = a.docRefNo " +
            "AND b.sbLineNo = a.lineNo " +
            "LEFT JOIN ExportSbEntry c ON c.companyId = b.companyId " +
            "AND c.branchId = b.branchId AND c.profitcentreId = b.profitcentreId " +
            "AND c.sbTransId = b.sbTransId AND c.sbNo = b.sbNo " +
            "LEFT JOIN Party p ON p.companyId = c.companyId AND c.cha = p.partyId " +
            "LEFT JOIN Party q ON q.companyId = c.companyId AND c.onAccountOf = q.partyId " +
            "LEFT JOIN EquipmentActivity b1 ON b1.companyId = a.companyId " +
            "AND b1.branchId = a.branchId " +
            "AND b1.profitCenterId = a.profitcentreId AND b1.deStuffId = a.gateInId " +
            "AND b1.erpDocRefNo = a.erpDocRefNo AND b1.docRefNo = a.docRefNo " +
            "LEFT JOIN JarDetail jj ON b1.companyId = jj.companyId AND b1.equipment = jj.jarDtlId AND jj.jarId = 'J00012' " +
            "LEFT JOIN Party n1 ON n1.companyId = a.companyId And n1.branchId = a.branchId AND a.sa = n1.partyId " +
            "WHERE a.companyId = :companyId AND a.branchId = :branchId " +
            "AND a.status = 'A' AND a.gateInId = :gateInId " +
            "GROUP BY a.gateInId, a.docRefNo")
List<Object[]> getExportTruckWiseGateInReport(@Param("companyId") String companyId,
                                  @Param("branchId") String branchId,
                                  @Param("gateInId") String gateInId);



@Query(value = "SELECT a.sbNo, " +
        "DATE_FORMAT(a.sbDate, '%d-%m-%Y') as SB_Date, " +
        "a.vehicleNo, " +
        "a.onAccountOf, " +
        "a.backToTownTransId, " +
        "DATE_FORMAT(a.backToTownTransDate, '%d-%m-%Y %T') as Back_To_Town_Trans_Date, " +
        "a.backToTownPackages, " +
        "a.importerId, " +
        "a.commodity, " +
        "a.backToTownWeight, " +
        "b.typeOfPackage, " +
        "p.partyName as On_Account_Party_Name, " +
        "m.partyName as Importer_Party_Name " +
        "FROM ExportBackToTown a " +
        "LEFT OUTER JOIN ExportSbCargoEntry b ON a.companyId = b.companyId " +
        "AND a.branchId = b.branchId " +
        "AND a.sbTransId = b.sbTransId " +
        "AND a.sbNo = b.sbNo " +
        "AND a.sbLineNo = b.sbLineNo " +
        "LEFT OUTER JOIN Party p ON a.companyId = p.companyId And p.branchId = a.branchId " +
        "AND a.onAccountOf = p.partyId " +
        "LEFT OUTER JOIN Party m ON a.companyId = m.companyId And m.branchId = a.branchId " +
        "AND a.importerId = m.partyId " +
        "WHERE a.companyId = :companyId " +
        "AND a.profitcentreId = :profitcentreId " +
        "AND a.branchId = :branchId " +
        "AND a.status = 'A' " +
        "AND a.backToTownTransId = :backToTownTransId")
List<Object[]> getExportBackToTownReport(@Param("companyId") String companyId,
                                          @Param("branchId") String branchId,
                                          @Param("profitcentreId") String profitcentreId,
                                          @Param("backToTownTransId") String backToTownTransId);



//@Query(value = "SELECT NEW com.cwms.entities.GateIn(a.gateInId, " +
//        "a.inGateInDate as gateInDateString, " +
//        "a.containerNo as containerNo, " +
//        "a.containerSize as containerSize, " +
//        "a.containerType as containerType, " +
//        "a.containerSealNo as containerSealNo, " +
//        "a.vehicleNo as vehicleNo, " +
//        "a.transporterName as transporterName, " +
//        "a.gateInType as gateInType, " +
//        "a.importerName as importerName, " +
//        "a.cha as cha, " +
//        "a.sl as sl, " +
//        "a.comments as comments, " +
//        "a.commodityDescription as commodityDescription, " +
//        "a.containerHealth as containerHealth, " +
//        "s.partyName as shippingLineName, " +
//        "p.partyName as transporter) " +
//        "FROM GateIn a " +
//        "LEFT OUTER JOIN Party s ON s.companyId = a.companyId AND s.partyId = a.sl " +
//        "LEFT OUTER JOIN Party p ON p.companyId = a.companyId AND p.partyId = a.transporter " +
//        "WHERE a.companyId = :companyId " +
//        "AND a.branchId = :branchId " +
//        "AND a.profitcentreId = :profitcentreId " +
//        "AND a.status = 'A' " +
//        "AND a.gateInId = :gateInId")
//List<GateIn> getExportPortReturnReport(@Param("companyId") String companyId,
//                               @Param("branchId") String branchId,
//                               @Param("profitcentreId") String profitcentreId,
//                               @Param("gateInId") String gateInId);


@Query(value = "SELECT NEW com.cwms.entities.GateIn(a.gateInId, " +
        "a.inGateInDate as gateInDateString, " +
        "a.containerNo as containerNo, " +
        "a.containerSize as containerSize, " +
        "a.containerType as containerType, " +
        "a.containerSealNo as containerSealNo, " +
        "a.vehicleNo as vehicleNo, " +
        "a.transporterName as transporterName, " +
        "a.gateInType as gateInType, " +
        "a.importerName as importerName, " +
        "a.cha as cha, " +
        "a.sl as sl, " +
        "a.comments as comments, " +
        "a.commodityDescription as commodityDescription, " +
        "a.containerHealth as containerHealth, " +
        "s.partyName as shippingLineName, " +
        "p.partyName as transporter, " +
        "o.partyName as onAccountOf) " +
        "FROM GateIn a " +
        "LEFT OUTER JOIN Party s ON s.companyId = a.companyId AND s.branchId = a.branchId AND s.partyId = a.sl " +
        "LEFT OUTER JOIN Party p ON p.companyId = a.companyId AND p.branchId = a.branchId AND p.partyId = a.transporter " +
        "LEFT OUTER JOIN Party o ON o.companyId = a.companyId AND o.branchId = a.branchId AND o.partyId = a.onAccountOf " +
        "WHERE a.companyId = :companyId " +
        "AND a.branchId = :branchId " +
        "AND a.profitcentreId = :profitcentreId " +
        "AND a.status = 'A' " +
        "AND a.gateInId = :gateInId")
List<GateIn> getExportPortReturnReport(@Param("companyId") String companyId,
                               @Param("branchId") String branchId,
                               @Param("profitcentreId") String profitcentreId,
                               @Param("gateInId") String gateInId);
	

}
