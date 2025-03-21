package com.cwms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.ExportAudit;
import com.cwms.exportAuditTrail.BackToTownDTO;
import com.cwms.exportAuditTrail.BackToTownOutDTO;
import com.cwms.exportAuditTrail.CartingTallyDTO;
import com.cwms.exportAuditTrail.ContainerGateIn;
import com.cwms.exportAuditTrail.ContainerGateOut;
import com.cwms.exportAuditTrail.GateInJoDTO;
import com.cwms.exportAuditTrail.GateInJoDetailDTO;
import com.cwms.exportAuditTrail.ShippingBillDTO;
import com.cwms.exportAuditTrail.StuffTally;

public interface ExportAuditRepo extends JpaRepository<ExportAudit, String> {
	
	
	@Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM ExportInventory e "
	        + "WHERE e.companyId = :companyId AND e.branchId = :branchId "
	        + "AND e.status <> 'D' "
	        + "AND (e.gateOutId IS NULL OR e.gateOutId = '') "
	        + "AND e.containerNo = :containerNo "
	        + "AND e.gateInId <> :gateInId")
	boolean checkContainerInInventory(@Param("companyId") String companyId, 
	                                  @Param("branchId") String branchId, 
	                                  @Param("containerNo") String containerNo, 
	                                  @Param("gateInId") String gateInId);

	

	@Query(value="SELECT Distinct p.containerNo, p.gateInId FROM ExportInventory p " +
	        "WHERE p.companyId = :companyId AND p.branchId = :branchId " +
	        "AND p.status <> 'D' " +
	        "AND (p.gatePassNo IS NOT NULL AND p.gatePassNo <> '') " +
	        "AND (:val IS NULL OR :val = '' OR p.containerNo LIKE CONCAT(:val, '%'))")
	List<Object[]> getContainerNoList(@Param("companyId") String companyId, 
	                                  @Param("branchId") String branchId, 
	                                  @Param("val") String val);
	
	
	@Query(value="SELECT Distinct p.gatePassId FROM ExportGatePass p " +
	        "WHERE p.companyId = :companyId AND p.branchId = :branchId " +
	        "AND p.status <> 'D' " +
	        "AND (:val IS NULL OR :val = '' OR p.gatePassId LIKE CONCAT(:val, '%'))")
	List<Object[]> getGatePassNoList(@Param("companyId") String companyId, 
	                                  @Param("branchId") String branchId, 
	                                  @Param("val") String val);

	
	
	
//	@Query("SELECT NEW com.cwms.exportAuditTrail.ShippingBillDTO(E.noOfPackages, E.grossWeight, E.typeOfPackage, "
//			+ "E.fob, E.sbNo, E.sbDate, "
//			+ "E.sbType, E.sbTransId, E.cargoType, E.numberOfMarks, "
//			+ "E.commodity, "
//			+ "sb.exporterName, sb.consigneeName, sb.cha, pc.partyName, sb.onAccountOf, po.partyName, pe.partyName, sb.exporterAddress1, sb.exporterAddress2, sb.exporterAddress3, sb.srNo, sb.gstNo, sb.state, sb.iecCode, E.finYear) "
//			+ "FROM ExportSbCargoEntry E "
//			+ "LEFT JOIN ExportSbEntry sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.sbTransId = sb.sbTransId AND E.sbNo = sb.sbNo AND sb.status != 'D' "
//			+ "LEFT JOIN Party pc ON sb.companyId = pc.companyId AND sb.branchId = pc.branchId AND sb.cha = pc.partyId AND pc.status != 'D' "
//			+ "LEFT JOIN Party po ON sb.companyId = po.companyId AND sb.branchId = po.branchId AND sb.onAccountOf = po.partyId AND po.status != 'D' "
//			+ "LEFT JOIN Party pe ON sb.companyId = pe.companyId AND sb.branchId = pe.branchId AND sb.exporterId = pe.partyId AND pe.status != 'D' "
//			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId " + "AND E.profitcentreId = :profitcentreId "
//			+ "AND E.sbNo = :sbNo " + "AND E.status <> 'D'")
//	ShippingBillDTO getShippingBill1StTable(@Param("companyId") String companyId, @Param("branchId") String branchId,
//											@Param("profitcentreId") String profitcentreId, @Param("sbNo") String sbNo);
//	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.ShippingBillDTO(E.noOfPackages, E.grossWeight, E.typeOfPackage, "
			+ "E.fob, E.sbNo, E.sbDate, "
			+ "E.sbType, E.sbTransId, E.cargoType, E.numberOfMarks, "
			+ "E.commodity, "
			+ "sb.exporterName, sb.consigneeName, sb.cha, pc.partyName, sb.onAccountOf, po.partyName, pe.partyName, sb.exporterAddress1, sb.exporterAddress2, sb.exporterAddress3, sb.srNo, sb.gstNo, sb.state, sb.iecCode, E.finYear) "
			+ "FROM ExportSbCargoEntry E "
			+ "LEFT JOIN ExportSbEntry sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.sbTransId = sb.sbTransId AND E.sbNo = sb.sbNo AND sb.status != 'D' "
			+ "LEFT JOIN Party pc ON sb.companyId = pc.companyId AND sb.branchId = pc.branchId AND sb.cha = pc.partyId AND pc.status != 'D' "
			+ "LEFT JOIN Party po ON sb.companyId = po.companyId AND sb.branchId = po.branchId AND sb.onAccountOf = po.partyId AND po.status != 'D' "
			+ "LEFT JOIN Party pe ON sb.companyId = pe.companyId AND sb.branchId = pe.branchId AND sb.exporterId = pe.partyId AND pe.status != 'D' "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.profitcentreId = :profitcentreId "
			+ "AND E.sbNo = :sbNo " 
			+ "AND E.status <> 'D' "
			+ "ORDER BY E.createdDate DESC") // 👈 Sort by createdDate (latest first)")
	List<ShippingBillDTO> getShippingBill1StTable(@Param("companyId") String companyId, 
											@Param("branchId") String branchId,
											@Param("profitcentreId") String profitcentreId, 
											@Param("sbNo") String sbNo,
											Pageable pageable); // 👈 Add Pageable for LIMIT 1


	
	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.GateInJoDTO(E.sbTransId, E.sbNo, E.sbDate, "
			+ "sb.gateInId, sb.inGateInDate, sb.vehicleNo) "			
			+ "FROM ExportSbCargoEntry E "
			+ "LEFT JOIN GateIn sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.sbTransId = sb.erpDocRefNo AND E.sbNo = sb.docRefNo AND sb.gateInType = 'EXP' AND sb.profitcentreId = E.profitcentreId AND sb.status != 'D' "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbNo = :sbNo AND sb.gateInId IS NOT NULL " + "AND E.status <> 'D'")
	List<GateInJoDTO> getGateInJO2ndTable(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	

	@Query("SELECT NEW com.cwms.exportAuditTrail.GateInJoDetailDTO(E.sbTransId, E.sbNo, sb.gateInId, "
			+ "sb.cartingTransId, E.noOfPackages, E.grossWeight, im.yardPackages, im.yardWeight, sb.cartingLineId, im.subSrNo) "			
			+ "FROM ExportSbCargoEntry E "
			+ "LEFT JOIN ExportCarting sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.sbTransId = sb.sbTransId AND E.sbNo = sb.sbNo AND sb.profitcentreId = E.profitcentreId AND sb.status != 'D' "
			+ "LEFT JOIN Impexpgrid im ON sb.companyId = im.companyId AND sb.branchId = im.branchId AND sb.cartingTransId = im.processTransId AND sb.cartingLineId = CAST(im.lineNo AS STRING) AND im.status != 'D' "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbNo = :sbNo AND sb.cartingTransId IS NOT NULL " + "AND E.status <> 'D'")
	List<GateInJoDetailDTO> getGateInJODTL3rdTable(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	
	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.CartingTallyDTO(E.sbTransId, E.sbNo, sb.gateInId, "
			+ "sb.cartingTransId, sb.actualNoOfPackages, sb.actualNoOfWeight, sb.cartingLineId, im.yardLocation, im.yardPackages, im.cellAreaAllocated, im.yardBlock, im.blockCellNo, im.yardWeight, im.subSrNo) "			
			+ "FROM ExportSbCargoEntry E "
			+ "LEFT JOIN ExportCarting sb ON E.companyId = sb.companyId AND E.branchId = sb.branchId AND E.sbTransId = sb.sbTransId AND E.sbNo = sb.sbNo AND sb.profitcentreId = E.profitcentreId AND sb.status != 'D' "
			+ "LEFT JOIN Impexpgrid im ON sb.companyId = im.companyId AND sb.branchId = im.branchId AND sb.cartingTransId = im.processTransId AND sb.cartingLineId = CAST(im.lineNo AS STRING) AND im.status != 'D' "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "
			+ "AND E.sbNo = :sbNo AND sb.cartingTransId IS NOT NULL " + "AND E.status <> 'D'")
	List<CartingTallyDTO> getCartingTallyDTO4thTable(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	
	
	
	
//	@Query("SELECT NEW com.cwms.exportAuditTrail.ContainerGateIn(E.gateInId, E.inGateInDate, E.containerNo, "
//			+ "E.containerSize, E.containerType, E.isoCode, E.tareWeight, E.vehicleNo, E.grossWeight, E.containerSealNo, E.customsSealNo, E.transporter, E.transporterName, E.damageDetails, E.sl, "
//			+ "psl.partyName, E.sa, psa.partyName, E.createdBy, E.profitcentreId, inv.stuffReqId, inv.stuffTallyId, inv.movementReqId, inv.gateOutId,inv.gatePassNo, E.importerName, E.cha, ch.partyName) "			
//			+ "FROM GateIn E "
//			+ "LEFT JOIN Party psl ON E.companyId = psl.companyId AND psl.branchId = E.branchId AND E.sl = psl.partyId AND psl.status != 'D' "
//			+ "LEFT JOIN Party ch ON E.companyId = ch.companyId AND ch.branchId = E.branchId AND E.cha = ch.partyId AND ch.status != 'D' "
//			+ "LEFT JOIN Party psa ON E.companyId = psa.companyId AND psa.branchId = E.branchId AND E.sa = psa.partyId AND psa.status != 'D' "
//			+ "LEFT JOIN ExportInventory inv ON E.companyId = inv.companyId AND inv.branchId = E.branchId AND E.gateInId = inv.gateInId AND E.containerNo = inv.containerNo "
//			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "			
//			+ "AND (:sbNo IS NULL OR :sbNo = '' OR E.docRefNo = :sbNo) " 
//			+ "AND (:containerNo IS NULL OR :containerNo = '' OR E.containerNo = :containerNo) " 
//			+ "AND E.gateInType IN ('EXP', 'BUFFER') AND E.processId In ('P00219', 'P00234') "
//			+ "AND E.status <> 'D'")
//	ContainerGateIn getContainerGateIn(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo, @Param("containerNo") String containerNo);
//	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.ContainerGateIn(E.gateInId, E.inGateInDate, E.containerNo, "
			+ "E.containerSize, E.containerType, E.isoCode, E.tareWeight, E.vehicleNo, E.grossWeight, "
			+ "E.containerSealNo, E.customsSealNo, E.transporter, E.transporterName, E.damageDetails, E.sl, "
			+ "psl.partyName, E.sa, psa.partyName, E.createdBy, E.profitcentreId, inv.stuffReqId, inv.stuffTallyId, "
			+ "inv.movementReqId, inv.gateOutId, inv.gatePassNo, E.importerName, E.cha, ch.partyName) "
			+ "FROM GateIn E "
			+ "LEFT JOIN Party psl ON E.companyId = psl.companyId AND psl.branchId = E.branchId AND E.sl = psl.partyId AND psl.status != 'D' "
			+ "LEFT JOIN Party ch ON E.companyId = ch.companyId AND ch.branchId = E.branchId AND E.cha = ch.partyId AND ch.status != 'D' "
			+ "LEFT JOIN Party psa ON E.companyId = psa.companyId AND psa.branchId = E.branchId AND E.sa = psa.partyId AND psa.status != 'D' "
			+ "LEFT JOIN ExportInventory inv ON E.companyId = inv.companyId AND inv.branchId = E.branchId AND E.gateInId = inv.gateInId AND E.containerNo = inv.containerNo "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "			
			+ "AND (:sbNo IS NULL OR :sbNo = '' OR E.docRefNo = :sbNo) " 
			+ "AND (:containerNo IS NULL OR :containerNo = '' OR E.containerNo = :containerNo) " 
			+ "AND E.gateInType IN ('EXP', 'BUFFER') AND E.processId IN ('P00219', 'P00234') "
			+ "AND E.status <> 'D' "
			+ "ORDER BY E.inGateInDate DESC") // 👈 Order by latest inGateInDate
List<ContainerGateIn> getContainerGateIn(@Param("companyId") String companyId, 
								   @Param("branchId") String branchId, 
								   @Param("sbNo") String sbNo, 
								   @Param("containerNo") String containerNo,
								   Pageable pageable); // 👈 Add Pageable for LIMIT 1

	
	
	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.ContainerGateOut(E.gatePassId, E.gatePassDate, E.gateOutId, "
			+ "out.gateOutDate, E.containerNo, E.containerSize, E.containerType, E.vehicleNo, E.transporterName, E.transporter, E.profitcentreId) "			
			+ "FROM ExportGatePass E "
			+ "LEFT JOIN GateOut out ON E.companyId = out.companyId AND out.branchId = E.branchId AND E.containerNo = out.containerNo AND E.gateOutId = out.gateOutId AND E.gatePassId = out.gatePassNo "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "			
			+ "AND (:sbNo IS NULL OR :sbNo = '' OR E.sbNo = :sbNo) " 
			+ "AND (:containerNo IS NULL OR :containerNo = '' OR E.containerNo = :containerNo) " 
			+ "AND E.transType = 'CONT' "
			+ "AND E.status <> 'D' "
			+ "ORDER BY E.gatePassDate DESC") // 👈 Order by latest inGateInDate
	List<ContainerGateOut> getContainerGateOut(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo, @Param("containerNo") String containerNo, Pageable pageable);
	
	
	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.StuffTally(E.sbTransId, E.stuffTallyDate, E.containerNo, "
			+ "E.containerSize, E.containerType, E.voyageNo, E.viaNo, E.vesselId, v.vesselName, E.rotationNo, E.pod, E.finalPod, E.pol, E.agentSealNo, E.customsSealNo, E.tareWeight, E.movementReqId, mov.movementReqDate, E.stuffDate, E.stuffTallyDate, "
			+ "E.sbNo, E.stuffedQty, E.cargoWeight, E.profitcentreId, E.stuffId, E.gateInId, E.gatePassNo, E.gateOutId, E.finalPod, E.stuffTallyId, sb.stuffedQty, sb.grossWeight) "			
			+ "FROM ExportStuffTally E "
			+ "LEFT JOIN ExportStuffRequest st ON E.companyId = st.companyId AND st.branchId = E.branchId AND E.sbNo = st.sbNo AND E.sbTransId= st.sbTransId AND E.containerNo = st.containerNo AND E.stuffId= st.stuffReqId  "
			+ "LEFT JOIN ExportSbCargoEntry sb ON E.companyId = sb.companyId AND sb.branchId = E.branchId AND E.sbNo = sb.sbNo AND E.sbTransId= sb.sbTransId  "
			+ "LEFT JOIN ExportMovement mov ON E.companyId = mov.companyId AND mov.branchId = E.branchId AND E.stuffTallyId = mov.stuffTallyId AND E.movementReqId= mov.movementReqId AND E.containerNo = st.containerNo  "
	        + "LEFT OUTER JOIN Vessel v ON E.companyId = v.companyId AND E.branchId = v.branchId AND E.vesselId = v.vesselId "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "			
			+ "AND (:sbNo IS NULL OR :sbNo = '' OR E.sbNo = :sbNo) " 
			+ "AND (:containerNo IS NULL OR :containerNo = '' OR E.containerNo = :containerNo) " 
			+ "AND E.status <> 'D'")
	List<StuffTally> getContainerStuffTally(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo, @Param("containerNo") String containerNo);
	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.BackToTownDTO(E.backToTownTransId, E.backToTownTransDate, E.sbTransId, "
			+ "E.sbNo, E.backToTownPackages, E.backToTownWeight, E.actualNoOfPackages, E.grossWeight) "			
			+ "FROM ExportBackToTown E "		
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "			
			+ "AND E.sbNo = :sbNo " 
			+ "AND E.status <> 'D'")
	List<BackToTownDTO> getBackToTownDTO(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	
	
	
	@Query("SELECT NEW com.cwms.exportAuditTrail.BackToTownOutDTO(E.gateOutDate, E.vehicleNo, E.qtyTakenOut, "
			+ "E.gatePassNo, E.gatePassDate, b.backToTownTransId, b.backToTownTransDate, b.sbTransId, b.sbNo, "			
			+ "b.backToTownPackages, b.backToTownWeight, E.gateOutId) "			
			+ "FROM GateOut E "	
			+ "LEFT JOIN ExportBackToTown b ON E.companyId = b.companyId AND b.branchId = E.branchId AND E.tallyId = b.backToTownTransId  "
			+ "WHERE E.companyId = :companyId AND E.branchId = :branchId "			
			+ "AND E.docRefNo = :sbNo AND E.transType = 'CRG' " 
			+ "AND E.status <> 'D'")
	List<BackToTownOutDTO> getBackToTownOutDTO(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("sbNo") String sbNo);
	
	
}
