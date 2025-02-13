package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.cwms.entities.CfinvsrvPro;
import com.cwms.entities.CfinvsrvanxPro;
import com.cwms.entities.ExportStuffTally;
import com.cwms.entities.FinTrans;
import com.cwms.entities.Party;
import com.cwms.entities.SSRDtl;
import com.cwms.helper.ExportContainerAssessmentData;

public interface ExportInvoiceRepoPro extends JpaRepository<ExportStuffTally, String> {
	
	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId, a.branchId, a.assesmentId,a.assesmentLineNo,a.assesmentDate,a.sbNo,a.sbTransId,a.commodityDescription,a.minCartingTransDate "
			+ ", a.grossWeight, a.areaUsed, a.invoiceUptoDate) "
			+ "from AssessmentSheetPro a "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Order By a.sbNo")
	List<CfinvsrvanxPro> getAllContainerListOfAssessMentSheetBackToTown(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId, a.branchId, a.assesmentId,a.assesmentLineNo,a.assesmentDate,a.containerNo,a.containerSize,a.containerType,a.gateInDate,a.invoiceUptoDate "
			+ ",e.gateOutId,e.gatePassNo, a.partyId, e.gateOutDate, a.movReqDate, a.stuffTallyDate) "
			+ "from AssessmentSheetPro a "
			+ "LEFT JOIN ExportInventory e on a.companyId = e.companyId AND a.branchId = e.branchId AND a.assesmentId = e.assessmentId AND a.containerNo = e.containerNo AND e.status <> 'D' "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Order By a.containerNo")
	List<CfinvsrvanxPro> getAllContainerListOfAssessMentSheet(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	
	
	
	
	
	
	@Query(value="select NEW com.cwms.helper.ExportContainerAssessmentData(a.assesmentId, a.assesmentLineNo, a.transType, a.assesmentDate, a.commodityDescription, a.minCartingTransDate, a.grossWeight, a.areaUsed, a.invoiceUptoDate, "
			+ "s.serviceShortDesc, SUM(c.actualNoOfPackages), c.taxPerc) "
			+ "from AssessmentSheetPro a "
	        + "LEFT OUTER JOIN CfinvsrvanxPro c ON a.companyId = c.companyId AND a.branchId = c.branchId AND a.assesmentId = c.processTransId AND a.assesmentLineNo = c.lineNo  "
	        + "LEFT OUTER JOIN Services s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Group by c.lineNo, c.serviceId Order By a.containerNo")
	List<ExportContainerAssessmentData> getSelectedExportAssesmentSheetBackToTown(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	
	@Query(value="select NEW com.cwms.helper.ExportContainerAssessmentData(a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.commodityDescription,a.commodityCode,a.noOfPackages,a.typeOfCargo, "
			+ "s.serviceShortDesc, SUM(c.actualNoOfPackages), a.grossWeight, c.taxPerc) "
			+ "from AssessmentSheetPro a "
	        + "LEFT OUTER JOIN CfinvsrvanxPro c ON a.companyId = c.companyId AND a.branchId = c.branchId AND a.assesmentId = c.processTransId  "
	        + "LEFT OUTER JOIN Services s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Group by a.containerNo, c.serviceId Order By a.containerNo")
	List<ExportContainerAssessmentData> getSelectedExportAssesmentSheetContainerDataCarting(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportBackToTown e SET e.assesmentId = :assesmentId "
	        + "WHERE e.companyId = :companyId "
	        + "AND e.branchId = :branchId "
	        + "AND e.backToTownTransId = :backToTownTransId "
	        + "AND e.status <> 'D'")
	int updateExpBackToTownInvoiceSave(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("assesmentId") String assesmentId,
	        @Param("backToTownTransId") String backToTownTransId
	);
	
	

	
	@Transactional
	@Modifying
	@Query("UPDATE ExportBackToTown e SET e.invoiceAssessed = 'Y', e.invoiceNo = :invoiceNo, e.invoiceDate = :invoiceDate, "
	        + "e.creditType = :creditType, e.billAmt = :billAmt, e.invoiceAmt = :invoiceAmt "
	        + "WHERE e.companyId = :companyId "
	        + "AND e.branchId = :branchId "
	        + "AND e.status <> 'D' "
	        + "AND e.assesmentId = :assessmentId")
	int updateExpBackToTownInvoiceProcess(
	        @Param("assessmentId") String assessmentId,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("invoiceNo") String invoiceNo,
	        @Param("invoiceDate") Date invoiceDate,
	        @Param("creditType") String creditType,
	        @Param("billAmt") BigDecimal billAmt,
	        @Param("invoiceAmt") BigDecimal invoiceAmt
	);
	
	
	
	@Query(value = "select NEW com.cwms.entities.SSRDtl(s.containerNo, s.serviceId, s.serviceUnit, s.executionUnit,s.serviceUnit1, s.executionUnit1, s.rate,"
			+ "s.totalRate) " + "from SSRDtl s "
			+ "where s.companyId=:companyId and s.branchId=:branchId and s.transId=:ssrTransId and s.status = 'A' AND s.erpDocRefNo = :sbTransId AND s.docRefNo = :sbNo AND s.backToTownTransId = :backToTownTransId ")
	List<SSRDtl> getServiceIdCargoBackToTown(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("ssrTransId") String ssrTransId,
											 @Param("sbNo") String sbNo, @Param("sbTransId") String sbTransId, @Param("backToTownTransId") String backToTownTransId);
	
	
	
	
	
	@Query(value = " " +
		    "SELECT DISTINCT NEW com.cwms.helper.ExportContainerAssessmentData( " +
		    "    a.sbTransId, a.sbNo, a.sbDate, a.commodity, a.profitcentreId, c.profitcentreDesc, " +
		    "    f.typeOfPackage, a.invoiceAssessed, a.assesmentId, a.invoiceNo, " +
		    "    a.importerId, i.partyName, " +
		    "    e.exporterAddress1, e.exporterAddress2, e.exporterAddress3, e.state, e.gstNo, " +
		    "    e.srNo, i.tanNoId, " +
		    "    a.onAccountOf, onp.partyName, " +
		    "    onAc.address1, onAc.address2, onAc.address3, onAc.state, onAc.gstNo, " +
		    "    onAc.srNo, onp.tanNoId, " +
		    "    e.cha, ch.partyName, " +
		    "    cha.address1, cha.address2, cha.address3, cha.state, cha.gstNo, " +
		    "    cha.srNo, ch.tanNoId, " +
		    "    d.cartingTransDate, a.backToTownWeight, d.areaOccupied, a.backToTownTransId, " +
		    "    f.newCommodity, f.grossWeight, a.typeOfContainer, a.ssrTransId, " +
		    "    e.holdStatus, d.gridLocation, f.grossWeight " +
		    ") " +
		    "FROM ExportBackToTown a " +
		    "LEFT OUTER JOIN Profitcentre c " +
		    "    ON c.companyId = a.companyId AND c.profitcentreId = a.profitcentreId " +
		    "LEFT OUTER JOIN ExportCarting d " +
		    "    ON a.companyId = d.companyId AND a.branchId = d.branchId " +
		    "    AND a.sbTransId = d.sbTransId AND a.sbNo = d.sbNo " +
		    "LEFT OUTER JOIN ExportSbEntry e " +
		    "    ON e.companyId = a.companyId AND e.branchId = a.branchId " +
		    "    AND e.sbTransId = a.sbTransId AND e.sbNo = a.sbNo " +
		    "LEFT OUTER JOIN ExportSbCargoEntry f " +
		    "    ON f.companyId = a.companyId AND f.branchId = e.branchId " +
		    "    AND f.sbTransId = e.sbTransId AND f.sbNo = e.sbNo " +
		    "LEFT OUTER JOIN Party i " +
		    "    ON i.companyId = a.companyId AND i.partyId = a.importerId " +
		    "    AND a.branchId = i.branchId " +
		    "LEFT OUTER JOIN Party onp " +
		    "    ON onp.companyId = a.companyId AND onp.partyId = a.onAccountOf " +
		    "    AND a.branchId = onp.branchId " +
		    "LEFT OUTER JOIN Party ch " +
		    "    ON ch.companyId = a.companyId AND ch.partyId = e.cha " +
		    "    AND a.branchId = ch.branchId " +
		    "LEFT OUTER JOIN PartyAddress onAc " +
		    "    ON onAc.companyId = a.companyId AND onAc.partyId = a.onAccountOf " +
		    "    AND onAc.defaultChk = 'Y' AND a.branchId = onAc.branchId " +
		    "LEFT OUTER JOIN PartyAddress imp " +
		    "    ON imp.companyId = a.companyId AND imp.partyId = a.importerId " +
		    "    AND imp.defaultChk = 'Y' AND a.branchId = imp.branchId " +
		    "LEFT OUTER JOIN PartyAddress cha " +
		    "    ON cha.companyId = a.companyId AND cha.partyId = e.cha " +
		    "    AND cha.defaultChk = 'Y' AND a.branchId = cha.branchId " +
		    "WHERE " +
		    "    a.companyId = :companyId " +
		    "    AND a.branchId = :branchId " +
		    "    AND a.status = 'A' " +
		    "    AND a.profitcentreId = :profitcentreId " +
		    "    AND (a.invoiceAssessed IS NULL OR a.invoiceAssessed <> 'Y') " +
		    "    AND a.sbNo = :sbNo " +
		    "GROUP BY a.backToTownTransId"
		)
		List<ExportContainerAssessmentData> getContainerAssessmentDataBackToTown(
		    @Param("companyId") String companyId, 
		    @Param("branchId") String branchId, 
		    @Param("profitcentreId") String profitcentreId, 
		    @Param("sbNo") String sbNo
		);

		
	
	@Query("SELECT DISTINCT a.sbNo " +
		       "FROM ExportBackToTown a " +
		       "WHERE a.companyId = :companyId " +
		       "AND a.branchId = :branchId " +
		       "AND (a.invoiceAssessed IS NULL OR a.invoiceAssessed <> 'Y') " +
		       "AND a.profitcentreId = :profitcentreId " +
		       "AND (COALESCE(:searchValue, '') = '' OR a.sbNo LIKE CONCAT(:searchValue, '%')) " +
		       "AND a.status = 'A'")
		List<String> getDistinctSbNoSearchBackToTownList(
		       @Param("companyId") String companyId,
		       @Param("branchId") String branchId,
		       @Param("profitcentreId") String profitcentreId,
		       @Param("searchValue") String searchValue);

	
	
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportSbCargoEntry e SET e.crgInvoiceNo = :invoiceNo "
	        + "WHERE e.companyId = :companyId "
	        + "AND e.branchId = :branchId "
	        + "AND e.sbTransId = :sbTransId "
	        + "AND e.status <> 'D'")
	int updateExpSbCargoEntryCartingInvoiceProcess(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("invoiceNo") String invoiceNo,
	        @Param("sbTransId") String sbTransId
	);
	
	
	

	
	
	@Query(value="select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate "
			+ "from CFSTariffService c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and :rtype between c.fromRange and c.toRange ")
	Object getRangeDataByServiceIdCarting(@Param("cid") String cid, @Param("bid") String bid,@Param("tariffNo") String tariffNo,
			@Param("amd") String amd,@Param("service") String service,@Param("rtype") BigDecimal rtype);
	
	@Query(value="select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate "
			+ "from CFSTariffService c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and c.rangeType=:rtype ")
	List<Object[]> getDataByServiceIdCarting(@Param("cid") String cid, @Param("bid") String bid,@Param("tariffNo") String tariffNo,
											 @Param("amd") String amd,@Param("service") String service,@Param("rtype") String rtype);
	
	
	
	
	@Query(value = "SELECT c.serviceId, c.serviceUnit, c.rate, c.cfsTariffNo, c.cfsAmendNo, c.currencyId, c.srNo, "
			+ "c.serviceUnitI, c.rangeType, cu.exrate, c.minimumRate, s.taxId, COALESCE(tx.taxPerc, 0) AS taxPerc, s.acCode, s.serviceGroup, c.fromRange, c.toRange, s.criteriaType,s.serviceShortDesc,GROUP_CONCAT(distinct sm.storageType) AS storageTypes "
			+ "FROM CFSTariffService c "
			+ "LEFT OUTER JOIN CfsTarrif t ON c.companyId = t.companyId AND c.branchId = t.branchId AND c.cfsTariffNo = t.cfsTariffNo "
			+ "AND c.cfsAmendNo = t.cfsAmndNo "
			+ "LEFT OUTER JOIN Services s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
			+ "LEFT OUTER JOIN ServiceMapping sm ON c.companyId = sm.companyId AND c.branchId = sm.branchId AND s.serviceId = sm.serviceId "
			+ "LEFT OUTER JOIN CurrencyConv cu ON c.companyId = cu.companyId AND c.branchId = cu.branchId AND c.currencyId = cu.convCurrency "
			+ "LEFT OUTER JOIN TaxDtl tx ON s.companyId = tx.companyId AND s.taxId = tx.taxId "
			+ "AND DATE(:assessDate) BETWEEN tx.periodFrom AND tx.periodTo "
			+ "WHERE c.companyId = :cid AND c.branchId = :bid "
			+ "AND c.status = 'A' AND :assessDate < t.cfsValidateDate AND c.serviceId IN :serviceList "
			+ "AND c.cfsTariffNo = :tariffNo "
			+ "GROUP BY c.serviceId " + "ORDER BY c.serviceId")
	List<Object[]> getServiceRateForCarting(@Param("cid") String cid, @Param("bid") String bid,
											@Param("assessDate") Date assessDate, @Param("serviceList") List<String> serviceList, @Param("tariffNo") String tariffNo);

	
	
	
	
	
	@Query(value = "SELECT c.srno, c.commodity, c.newCommodity, c.noOfPackages, c.grossWeight, "
            + "c.typeOfPackage, c.cargoType "
            + "FROM ExportSbCargoEntry c "          
            + "WHERE c.companyId = :companyId AND c.branchId = :branchId "
            + "AND c.status != 'D' AND c.sbTransId = :sbTransId")           
List<Object[]> getExportDetailsCartingDetailsList(@Param("companyId") String companyId, 
                                                  @Param("branchId") String branchId,
                                                  @Param("sbTransId") String sbTransId);

	

@Query("SELECT new com.cwms.helper.ExportContainerAssessmentData( " +
	       "a.sbTransId, a.sbNo, a.sbDate, a.cha, d.partyName, da.gstNo, da.address1, da.address2, da.address3, " +
	       "da.state, da.srNo, d.tanNoId, a.exporterId, a.exporterName, imp.gstNo, imp.address1, imp.address2, imp.address3, " +
	       "imp.state, imp.srNo, i.tanNoId, a.onAccountOf, ch.partyName, cha.gstNo, cha.address1, cha.address2, cha.address3, " +
	       "cha.state, cha.srNo, ch.tanNoId, b.commodity, a.profitcentreId, c.profitcentreDesc, b.typeOfPackage, " +
	       "b.invoiceAssesed, b.assesmentId, b.crgInvoiceNo, b.grossWeight, b.newCommodity, a.holdStatus) " +
	       "FROM ExportSbEntry a " +
	       "LEFT JOIN ExportSbCargoEntry b ON a.companyId = b.companyId AND a.branchId = b.branchId AND a.sbNo = b.sbNo AND a.sbTransId = b.sbTransId " +
	       "LEFT JOIN Party i ON i.companyId = a.companyId AND i.partyId = a.exporterId AND i.branchId = a.branchId " +
	       "LEFT JOIN PartyAddress imp ON imp.companyId = a.companyId AND imp.partyId = a.exporterId AND imp.defaultChk = 'Y' AND imp.branchId = a.branchId " +
	       "LEFT JOIN Party ch ON ch.companyId = a.companyId AND ch.partyId = a.onAccountOf AND ch.branchId = a.branchId " +
	       "LEFT JOIN PartyAddress cha ON cha.companyId = a.companyId AND cha.partyId = a.onAccountOf AND cha.defaultChk = 'Y' AND cha.branchId = a.branchId " +
	       "LEFT JOIN Party d ON d.companyId = a.companyId AND d.partyId = a.cha AND d.branchId = a.branchId " +
	       "LEFT JOIN PartyAddress da ON da.companyId = a.companyId AND da.partyId = a.cha AND da.defaultChk = 'Y' AND da.branchId = a.branchId " +
	       "LEFT JOIN Profitcentre c ON c.companyId = a.companyId AND c.profitcentreId = a.profitcentreId AND c.branchId = a.branchId " +
	       "WHERE a.companyId = :companyId " +
	       "AND a.branchId = :branchId " +
	       "AND a.status = 'A' " +
	       "AND a.sbNo = :sbNo " +
	       "AND a.profitcentreId = :profitcentreId " +
	       "AND b.status = 'A' " +
	       "AND (b.crgInvoiceNo = '' OR b.crgInvoiceNo IS NULL) " +
	       "ORDER BY a.sbNo")
	List<ExportContainerAssessmentData> getExportDetailsCartingDetails(
	    @Param("companyId") String companyId,
	    @Param("branchId") String branchId,
	    @Param("sbNo") String sbNo,
	    @Param("profitcentreId") String profitcentreId);

	
	
	
	
	@Query(value = "SELECT c.assesmentId, "
            + "DATE_FORMAT(c.assesmentDate, '%d %b %Y') AS assesmentDate, "
            + "psl.profitcentreDesc, c.sbTransId, c.sbNo, "
            + "DATE_FORMAT(c.sbDate, '%d %b %Y') AS sbDate, " 
            + "j.jarDtlDesc, "
            + "c.exporterName, c.status, c.invoiceNo, c.transType, c.profitcentreId, c.containerNo "
            + "FROM AssessmentSheetPro c "
            + "LEFT JOIN Profitcentre psl ON c.companyId = psl.companyId AND c.branchId = psl.branchId "
            + "AND c.profitcentreId = psl.profitcentreId AND psl.status <> 'D' "
            + "LEFT JOIN JarDetail j ON c.companyId = j.companyId AND j.jarId = 'J00006' "
            + "AND j.jarDtlId = c.commodityCode AND j.status <> 'D' "
            + "WHERE c.companyId = :companyId AND c.branchId = :branchId AND c.transType = :transType "
            + "AND (c.invoiceNo IS NOT NULL AND c.invoiceNo != '') "
            + "AND c.status != 'D' AND c.assesmentLineNo = '1' "
            + "AND (:searchValue IS NULL OR :searchValue = '' "
            + "OR c.invoiceNo LIKE :searchValue "
            + "OR c.assesmentId LIKE :searchValue OR c.sbNo LIKE :searchValue "
            + "OR c.containerNo LIKE :searchValue) "
            + "ORDER BY c.invoiceDate DESC")
List<Object[]> getAssesMentEntriesToSelect(@Param("companyId") String companyId, 
                                        @Param("branchId") String branchId,
                                        @Param("searchValue") String searchValue, @Param("transType") String transType);

	
	
	
	
	@Query(value="select NEW com.cwms.entities.FinTrans(a.tdsType, a.tdsPercentage, a.paymentMode, a.chequeNo, a.chequeDate, a.bankName, a.documentAmt, a.status, a.creditAmount) "
			+ "from FinTrans a "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.oprInvoiceNo = :oprInvoiceNo and a.status <> 'D' ")
	List<FinTrans> getFinTransGenerated(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("oprInvoiceNo") String oprInvoiceNo);
	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvPro(a.billAmt, a.invoiceAmt, a.receiptAmt, a.partyId) "
			+ "from CfinvsrvPro a "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.invoiceNo = :invoiceNo and a.status <> 'D' ")
	CfinvsrvPro getInvoiceGenerated(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("invoiceNo") String invoiceNo);
	
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportInventory e SET e.invoiceNo = :invoiceNo, e.invoiceAssessed = :invoiceAssessed, e.invoiceDate = :invoiceDate "
	        + "WHERE e.companyId = :companyId "
	        + "AND e.branchId = :branchId "
	        + "AND e.assessmentId = :assessmentId "
	        + "AND e.status <> 'D'")
	int updateExpInventoryContainerInvoiceProcess(
	        @Param("assessmentId") String assessmentId,
	        @Param("invoiceAssessed") String invoiceAssessed,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("invoiceNo") String invoiceNo,
	        @Param("invoiceDate") Date invoiceDate
	);
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE ExportStuffTally e SET e.invoiceAssesed = 'Y', e.invoiceNo = :invoiceNo, e.invoiceDate = :invoiceDate, "
	        + "e.creditType = :creditType, e.invoiceCategory = :invoiceCategory, e.billAmt = :billAmt, e.invoiceAmt = :invoiceAmt "
	        + "WHERE e.companyId = :companyId "
	        + "AND e.branchId = :branchId "
	        + "AND e.status <> 'D' "
	        + "AND e.assesmentId = :assessmentId")
	int updateExportStuffTallyContainerInvoiceProcess(
	        @Param("assessmentId") String assessmentId,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("invoiceNo") String invoiceNo,
	        @Param("invoiceDate") Date invoiceDate,
	        @Param("creditType") String creditType,
	        @Param("invoiceCategory") String invoiceCategory,
	        @Param("billAmt") BigDecimal billAmt,
	        @Param("invoiceAmt") BigDecimal invoiceAmt
	);

	
	
	


	@Transactional
	@Modifying
	@Query("UPDATE ExportMovement e SET e.invoiceAssessed = 'Y', e.invoiceNo = :invoiceNo, e.invoiceDate = :invoiceDate, "
	        + "e.creditType = :creditType, e.invoiceCategory = :invoiceCategory, e.billAmt = :billAmt, e.invoiceAmt = :invoiceAmt "
	        + "WHERE e.companyId = :companyId "
	        + "AND e.branchId = :branchId "
	        + "AND e.status <> 'D' "
	        + "AND e.assessmentId = :assessmentId")
	int updateMovementRequestContainerInvoiceProcess(
	        @Param("assessmentId") String assessmentId,
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("invoiceNo") String invoiceNo,
	        @Param("invoiceDate") Date invoiceDate,
	        @Param("creditType") String creditType,
	        @Param("invoiceCategory") String invoiceCategory,
	        @Param("billAmt") BigDecimal billAmt,
	        @Param("invoiceAmt") BigDecimal invoiceAmt
	);

	
	
	
	
	@Query(value="select NEW com.cwms.entities.Party(a.tanNoId,a.tdsPercentage, a.crAmtLmt, a.crAmtLmtUse) "
			+ "from Party a "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.partyId=:partyId ")
	Party getPartyForTanNo(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("partyId") String partyId);
	
	
	
	
	
	@Query("SELECT MAX(e.srlNo) " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.status <> 'D' " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.processTransId = :assesmentId")
		Optional<BigDecimal> getHighestSrlNoByContainerNo(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId);
	
	
	@Query("SELECT MAX(e.srlNo) " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.status <> 'D' " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.processTransId = :assesmentId")
		Optional<BigDecimal> getHighestSrlNoByContainerNo1(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId);

	
	
	@Query("SELECT MAX(e.srlNo) " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.status <> 'D' " +
		       "AND e.containerNo = :containerNo " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.processTransId = :assesmentId")
		Optional<BigDecimal> getHighestSrlNoByContainerNo2(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId,
		    @Param("containerNo") String containerNo);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CfinvsrvanxPro SET executionUnit = :executionUnit, executionUnit1 = :executionUnit1, rate = :rate, actualNoOfPackages = :actualNoOfPackages, localAmt = :actualNoOfPackages , invoiceAmt = :actualNoOfPackages "
			+ "WHERE companyId = :companyId " + "AND branchId = :branchId " + "AND processTransId = :assesmentId "
			+ "AND status <> 'D' AND profitcentreId = :profiCentreId AND srlNo = :srlNo ")
	int updateCfinvsrvanx(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId,
									  @Param("assesmentId") String assesmentId,  @Param("srlNo") BigDecimal srlNo, @Param("executionUnit") String executionUnit,
									  @Param("executionUnit1") String executionUnit1, @Param("rate") BigDecimal rate, @Param("actualNoOfPackages") BigDecimal actualNoOfPackages);
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CfinvsrvanxPro SET executionUnit = :executionUnit, executionUnit1 = :executionUnit1, rate = :rate, actualNoOfPackages = :actualNoOfPackages, localAmt = :actualNoOfPackages , invoiceAmt = :actualNoOfPackages "
			+ "WHERE companyId = :companyId " + "AND branchId = :branchId " + "AND processTransId = :assesmentId "
			+ "AND status <> 'D' AND profitcentreId = :profiCentreId AND srlNo = :srlNo AND containerNo = :containerNo")
	int updateCfinvsrvanx1(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId,
									  @Param("assesmentId") String assesmentId,  @Param("srlNo") BigDecimal srlNo, @Param("executionUnit") String executionUnit,
									  @Param("executionUnit1") String executionUnit1, @Param("rate") BigDecimal rate, @Param("actualNoOfPackages") BigDecimal actualNoOfPackages, @Param("containerNo") String containerNo);
	
	
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.srlNo = :srlNo AND e.status <> 'D' " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.lineNo = :lineNo " +
		       "AND e.serviceId = :serviceId " +
		       "AND e.processTransId = :assesmentId ")		      
		boolean existsByAssesmentIdAndSrlNo(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId,
		    @Param("srlNo") BigDecimal srlNo, @Param("lineNo") String lineNo, @Param("serviceId") String serviceid);
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM CfinvsrvanxPro e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.srlNo = :srlNo AND e.status <> 'D' AND e.containerNo = :containerNo " +
		       "AND e.profitcentreId = :profitcentreId " +
		       "AND e.processTransId = :assesmentId ")		      
		boolean existsByAssesmentIdAndSrlNo1(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("profitcentreId") String profitcentreId,
		    @Param("assesmentId") String assesmentId,
		    @Param("srlNo") BigDecimal srlNo, @Param("containerNo") String containerNo);
	
//	Add Servie
	@Query(value = "select NEW com.cwms.entities.CfinvsrvanxPro(a.serviceId, a.serviceUnit, a.serviceUnitI, a.currencyId, a.rangeType, a.rate, a.cfsTariffNo, a.cfsAmendNo, MAX(a.srNo), s.serviceShortDesc, s.acCode, s.taxId, tx.taxPerc) "
            + "from CFSTariffService a "
            + "LEFT OUTER JOIN Services s ON a.companyId = s.companyId AND a.branchId = s.branchId AND a.serviceId = s.serviceId "
            + "LEFT OUTER JOIN TaxDtl tx ON s.companyId = tx.companyId AND s.taxId = tx.taxId "
            + "WHERE a.companyId= :companyId AND a.branchId = :branchId AND  a.cfsTariffNo IN (:tariffNo) AND a.status = 'A' "
            + "AND (:searchValue IS NULL OR :searchValue = '' OR s.serviceShortDesc LIKE CONCAT(:searchValue, '%')) "
            + "AND a.containerSize IN (:contSize) "
            + "AND a.cargoType IN (:cargoType) "
            + "AND a.commodityCode IN (:commodityCodeList) "
            + "AND a.rangeType NOT IN ('SA', 'RA')"
            + "GROUP BY a.serviceId, a.serviceUnit, a.serviceUnitI, a.currencyId, a.rangeType, a.rate, a.cfsTariffNo, a.cfsAmendNo, s.serviceShortDesc "
            + "ORDER BY a.serviceId ASC limit 50")
List<CfinvsrvanxPro> searchServicesForAddService(
    @Param("companyId") String companyId, 
    @Param("branchId") String branchId, 
    @Param("searchValue") String searchValue, 
    @Param("tariffNo") List<String> tariffNo, 
    @Param("contSize") List<String> contSize, 
    @Param("commodityCodeList") List<String> commodityCodeList, 
    @Param("cargoType") List<String> cargoType);


	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId,a.branchId,a.processTransId,a.serviceId,a.taxId,a.erpDocRefNo,a.srlNo,a.docRefNo,a.profitcentreId "
			+ ",a.serviceUnit,a.executionUnit, a.serviceUnit1, a.executionUnit1, a.rate, a.currencyId, a.partyId, a.woNo, a.woAmndNo, a.criteria, a.rangeFrom, a.rangeTo, a.rangeType, a.containerNo, a.containerStatus, a.commodityDescription, a.actualNoOfPackages, a.startDate, a.status, a.srvManualFlag, s.serviceShortDesc, a.addServices, a.lineNo) "
			+ "from CfinvsrvanxPro a "
	        + "LEFT OUTER JOIN Services s ON a.companyId = s.companyId AND a.branchId = s.branchId AND a.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.processTransId=:assesmentId AND a.profitcentreId = :profiCentreId AND a.lineNo = :lineNo order by a.srlNo DESC")
	List<CfinvsrvanxPro> getAllCfInvSrvAnxListByAssesmentId(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId, @Param("lineNo") String lineNo);
	
	
	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId,a.branchId,a.processTransId,a.serviceId,a.taxId,a.erpDocRefNo,a.srlNo,a.docRefNo,a.profitcentreId "
			+ ",a.serviceUnit,a.executionUnit, a.serviceUnit1, a.executionUnit1, a.rate, a.currencyId, a.partyId, a.woNo, a.woAmndNo, a.criteria, a.rangeFrom, a.rangeTo, a.rangeType, a.containerNo, a.containerStatus, a.commodityDescription, a.actualNoOfPackages, a.startDate, a.status, a.srvManualFlag, s.serviceShortDesc, a.addServices) "
			+ "from CfinvsrvanxPro a "
	        + "LEFT OUTER JOIN Services s ON a.companyId = s.companyId AND a.branchId = s.branchId AND a.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.processTransId=:assesmentId AND a.profitcentreId = :profiCentreId AND a.containerNo = :containerNo order by a.srlNo DESC")
	List<CfinvsrvanxPro> getAllCfInvSrvAnxListByAssesmentId1(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId, @Param("containerNo") String containerNo);
	
	
	
	
	
	
	
	

//	Export Container Invoice	
	
	@Query(value = "SELECT a.service_Id AS cargoFreeDays " +
            "FROM cfservicemapping a " +
            "WHERE a.company_Id = :companyId AND a.branch_Id = :branchId " +
            "AND a.status = 'A' " +
            "AND a.storage_Type = 'CRG' " +
            "AND a.invoice_Type = 'EXP' " +
            "LIMIT 1", 
    nativeQuery = true)
String getCargoStorageServiceId(@Param("companyId") String companyId, 
                             @Param("branchId") String branchId);

	
	    @Query(value = """
	            SELECT DISTINCT 
	                anx.CargoSBNo, 
	                DATE_FORMAT(MIN(anx.start_date), '%d %b %Y') AS formattedStartDate, 
	                ROUND(SUM(anx.invoice_Amt), 2) AS totalInvoiceAmt, 
	                ROUND(SUM(anx.execution_unit1), 2) AS totalExecutionUnit1, 
	                ROUND(SUM(anx.actual_no_of_packages), 2) AS totalPackages
	            FROM cfassesmentsheet a
	            LEFT OUTER JOIN cfinvsrvanx anx 
	                ON a.company_id = anx.company_id 
	                AND a.branch_id = anx.branch_id 
	                AND a.container_no = anx.container_no 
	                AND a.profitcentre_id = anx.profitcentre_id 
	                AND a.Assesment_Id = anx.Process_Trans_Id 
	                AND (a.invoice_no = anx.Invoice_No OR (a.invoice_no IS NULL AND anx.Invoice_No IS NULL)) 
	                AND a.SB_Trans_Id = anx.ERP_Doc_Ref_No 
	                AND anx.Service_Id = :serviceId
	            WHERE a.Company_Id = :companyId 
	                AND a.branch_id = :branchId 
	                AND a.status = 'A' 
	                AND a.SB_Trans_Id = :sbTransId 
	                AND a.assesment_id = :assesmentId
	            GROUP BY anx.CargoSBNo
	            ORDER BY anx.CargoSBNo ASC
	            """, nativeQuery = true)
	    List<Object[]> getCargoStorageOfAssessmentSheetDetails(
	            @Param("companyId") String companyId, 
	            @Param("branchId") String branchId, 
	            @Param("sbTransId") String sbTransId, 
	            @Param("assesmentId") String assesmentId, @Param("serviceId") String serviceId);
	

	
	@Modifying
	@Query("UPDATE AssessmentSheetPro SET comments = :comments, intComments = :intComments "
			+ "WHERE companyId = :companyId " + "AND branchId = :branchId " + "AND assesmentId = :assesmentId "
			+ "AND status = 'A' AND profitcentreId = :profiCentreId")
	int updateAssessmentContainerEdit(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId,
									  @Param("assesmentId") String assesmentId, @Param("comments") String comments, @Param("intComments") String intComments);
	

	@Query(value = "SELECT "
	        + "a.companyId, a.branchId, a.assesmentId, a.assesmentLineNo, a.transType, a.assesmentDate, a.sbNo, a.sbTransId, a.sbDate, a.status, a.createdBy, "
	        + "a.profitcentreId, p.profitcentreDesc, a.billingParty, a.sl, a.sa, a.commodityDescription, a.minCartingTransDate, "
	        + "a.importerId, a.exporterName, "
	        + "COALESCE(CONCAT(pa1.address1, '', pa1.address2, '', pa1.address3), '') AS exporterAddress, pa1.gstNo, pa1.state, pa1.srNo, "
	        + "a.cha, p3.partyName, COALESCE(CONCAT(pa2.address1, '', pa2.address2, '', pa2.address3), '') AS chaAddress, pa2.gstNo, pa2.state, pa2.srNo, "
	        + "a.othPartyId, p4.partyName, COALESCE(CONCAT(pa3.address1, '', pa3.address2, '', pa3.address3), '') AS otherAddress, pa3.gstNo, pa3.state, pa3.srNo, "
	        + "a.onAccountOf, p5.partyName, COALESCE(CONCAT(pa4.address1, '', pa4.address2, '', pa4.address3), '') AS forwarderAddress, pa4.gstNo, pa4.state, pa4.srNo, "
	        + "a.invoiceNo, a.invoiceDate, a.taxApplicable, a.sez, a.commodityCode, a.creditType, a.invoiceCategory, "
	        + "a.irn, a.receiptNo, '0000' AS creditAllowed, '0000' AS pendingCredit, a.comments, a.intComments, a.partyId, a.transactionType, a.containerNo, a.cgst, a.sgst, a.igst, a.tdsDeductee, a.tds "
	        + "FROM AssessmentSheetPro a "
	        + "LEFT OUTER JOIN Party p2 ON a.companyId = p2.companyId AND a.branchId = p2.branchId AND a.importerId = p2.partyId "
	        + "LEFT OUTER JOIN Profitcentre p ON a.companyId = p.companyId AND a.branchId = p.branchId AND a.profitcentreId = p.profitcentreId "
	        + "LEFT OUTER JOIN PartyAddress pa1 ON a.companyId = pa1.companyId AND a.branchId = pa1.branchId AND a.importerId = pa1.partyId AND a.impSrNo = CAST(pa1.srNo AS INTEGER) "
	        + "LEFT OUTER JOIN Party p3 ON a.companyId = p3.companyId AND a.branchId = p3.branchId AND a.cha = p3.partyId "
	        + "LEFT OUTER JOIN PartyAddress pa2 ON a.companyId = pa2.companyId AND a.branchId = pa2.branchId AND a.cha = pa2.partyId AND a.chaSrNo = CAST(pa2.srNo AS INTEGER) "
	        + "LEFT OUTER JOIN Party p4 ON a.companyId = p4.companyId AND a.branchId = p4.branchId AND a.othPartyId = p4.partyId "
	        + "LEFT OUTER JOIN PartyAddress pa3 ON a.companyId = pa3.companyId AND a.branchId = pa3.branchId AND a.othPartyId = pa3.partyId AND pa3.srNo = a.othSrNo "
	        + "LEFT OUTER JOIN Party p5 ON a.companyId = p5.companyId AND a.branchId = p5.branchId AND a.onAccountOf = p5.partyId "
	        + "LEFT OUTER JOIN PartyAddress pa4 ON a.companyId = pa4.companyId AND a.branchId = pa4.branchId AND a.onAccountOf = pa4.partyId AND a.accSrNo = CAST(pa4.srNo AS INTEGER) "
	        + "WHERE a.companyId = :companyId AND a.branchId = :branchId AND a.status <> 'D' AND a.assesmentId = :assesmentId "
	        + "AND a.profitcentreId = :profiCentreId AND a.assesmentLineNo = '1'")
	Object[] getSelectedExportAssesmentSheet(
	        @Param("companyId") String companyId, 
	        @Param("branchId") String branchId, 
	        @Param("profiCentreId") String profiCentreId, 
	        @Param("assesmentId") String assesmentId);

	
	
	


	
	@Query(value="select NEW com.cwms.helper.ExportContainerAssessmentData(a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.containerNo,a.containerSize,a.containerType,a.gateInDate,a.stuffTallyDate "
			+ ",a.movReqDate,a.gateOutDate, c.invoiceUptoDate, s.serviceShortDesc, SUM(c.actualNoOfPackages), e.gatePassNo, e.gateOutId, c.taxPerc) "
			+ "from AssessmentSheetPro a "
	        + "LEFT OUTER JOIN CfinvsrvanxPro c ON a.companyId = c.companyId AND a.branchId = c.branchId AND a.assesmentId = c.processTransId AND a.containerNo = c.containerNo "
	        + "LEFT OUTER JOIN ExportInventory e ON a.companyId = e.companyId AND a.branchId = e.branchId AND a.assesmentId = e.assessmentId AND a.containerNo = e.containerNo "
	        + "LEFT OUTER JOIN Services s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Group by a.containerNo, c.serviceId Order By a.containerNo")
	List<ExportContainerAssessmentData> getSelectedExportAssesmentSheetContainerData(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	
	
	
	
	
	@Query(value = "SELECT a.expCrgStorage AS cargoFreeDays " + "FROM Party a "
			+ "WHERE a.companyId = :companyId AND a.branchId = :branchId " + "AND a.status = 'A' "
			+ "AND a.partyId = :partyId")
	String getFreeDaysForExportCargoStorage(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("partyId") String partyId);

	@Query(value = "SELECT MAX(a.toRange) AS containerFreeDays " + "FROM CFSTariffService a "
			+ "WHERE a.companyId = :companyId AND a.branchId = :branchId " + "AND a.status = 'A' "
			+ "AND a.cfsTariffNo = :cfsTariffNo AND a.cfsAmendNo = :cfsAmendNo AND a.serviceId = :serviceId AND a.rate = 0")
	BigDecimal getFreeDaysForExportContainerStorage(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("cfsTariffNo") String cfsTariffNo,
			@Param("cfsAmendNo") String cfsAmendNo, @Param("serviceId") String serviceId);

	@Modifying
	@Query("UPDATE ExportInventory SET assessmentId = :assessmentId, invoiceAssessed = :invoiceAssessed "
			+ "WHERE companyId = :companyId " + "AND branchId = :branchId " + "AND stuffTallyId = :stuffTallyId "
			+ "AND containerNo = :containerNo")
	int updateExpInventoryContainerInvoice(@Param("assessmentId") String assessmentId,
			@Param("invoiceAssessed") String invoiceAssessed, @Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("stuffTallyId") String stuffTallyId,
			@Param("containerNo") String containerNo);

	@Modifying
	@Query("UPDATE ExportStuffTally SET assesmentId = :assessmentId " + "WHERE companyId = :companyId "
			+ "AND branchId = :branchId " + "AND stuffTallyId = :stuffTallyId " + "AND containerNo = :containerNo")
	int updateStuffTallyContainerInvoice(@Param("assessmentId") String assessmentId,
			@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("stuffTallyId") String stuffTallyId, @Param("containerNo") String containerNo);

	@Modifying
	@Query("UPDATE ExportMovement SET assessmentId = :assessmentId " + "WHERE companyId = :companyId "
			+ "AND branchId = :branchId " + "AND stuffTallyId = :stuffTallyId " + "AND movementReqId = :movementReqId "
			+ "AND containerNo = :containerNo")
	int updateMovementRequestContainerInvoice(@Param("assessmentId") String assessmentId,
			@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("stuffTallyId") String stuffTallyId, @Param("movementReqId") String movementReqId,
			@Param("containerNo") String containerNo);

	@Query(value = "SELECT DISTINCT a.sbNo AS sbNo, " + "a.stuffTallyDate AS stuffTallyDate, "
			+ "v.inGateInDate AS inGateInDate, " + "ROUND((a.areaReleased), 2) AS areaReleased, "
			+ "b.vehicleNo AS vehicleNo, " + "b.cartingTransDate AS cartingTransDate, "
			+ "ROUND((SUM(a.cargoWeight)/1000), 3) AS totalCargoWeight " + "FROM ExportStuffTally a "
			+ "LEFT OUTER JOIN ExportCarting b " + "ON a.companyId = b.companyId AND a.branchId = b.branchId "
			+ "AND a.sbNo = b.sbNo AND a.sbTransId = b.sbTransId "
			+ "AND a.cartingTransId = b.cartingTransId AND a.cartingLineId = b.cartingLineId "
			+ "LEFT OUTER JOIN GateIn v " + "ON v.companyId = b.companyId AND v.branchId = b.branchId "
			+ "AND v.docRefNo = b.sbNo AND v.erpDocRefNo = b.sbTransId "
			+ "AND v.cartingTransId = b.cartingTransId AND v.gateInId = b.gateInId "
			+ "WHERE a.companyId = :companyId AND a.branchId = :branchId " + "AND a.status <> 'D' AND b.status <> 'D' "
			+ "AND a.stuffTallyId = :stuffTallyId " + "AND a.containerNo = :containerNo "
			+ "GROUP BY a.cartingTransId, a.containerNo, a.cartingLineId")
	List<Object[]> getExportCartingStorageDays(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("stuffTallyId") String stuffTallyId, @Param("containerNo") String containerNo);

	@Query(value = "select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate " + "from CFSTariffService c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and :rtype between c.fromRange and c.toRange and c.containerSize IN :size and c.cargoType IN :ctype and "
			+ "c.commodityCode IN :code order by c.cfsTariffNo,c.serviceId,FIELD(c.containerSize,:size1,'ALL'),"
			+ "FIELD(c.cargoType,:ctype1,'ALL'),FIELD(c.commodityCode,:code1,'ALL')")
	Object getRangeDataByServiceId(@Param("cid") String cid, @Param("bid") String bid,
			@Param("tariffNo") String tariffNo, @Param("amd") String amd, @Param("service") String service,
			@Param("rtype") BigDecimal rtype, @Param("size") List<String> size, @Param("ctype") List<String> ctype,
			@Param("code") List<String> code, @Param("size1") String size1, @Param("ctype1") String ctype1,
			@Param("code1") String code1);

	@Query(value = "select c.cfsTariffNo,c.serviceId,c.cfsAmendNo,c.containerSize,c.cargoType,c.commodityCode,c.fromRange,"
			+ "c.toRange,c.rate " + "from CFSTariffService c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.cfsTariffNo=:tariffNo and c.cfsAmendNo=:amd "
			+ "and c.serviceId=:service and c.rangeType=:rtype and c.containerSize IN :size and c.cargoType IN :ctype and "
			+ "c.commodityCode IN :code order by c.cfsTariffNo,c.serviceId,FIELD(c.containerSize,:size1,'ALL'),"
			+ "FIELD(c.cargoType,:ctype1,'ALL'),FIELD(c.commodityCode,:code1,'ALL')")
	List<Object[]> getDataByServiceId(@Param("cid") String cid, @Param("bid") String bid,
			@Param("tariffNo") String tariffNo, @Param("amd") String amd, @Param("service") String service,
			@Param("rtype") String rtype, @Param("size") List<String> size, @Param("ctype") List<String> ctype,
			@Param("code") List<String> code, @Param("size1") String size1, @Param("ctype1") String ctype1,
			@Param("code1") String code1);

	@Query(value = "SELECT c.serviceId, c.serviceUnit, c.rate, c.cfsTariffNo, c.cfsAmendNo, c.currencyId, c.srNo, "
			+ "c.serviceUnitI, c.rangeType, cu.exrate, c.minimumRate, s.taxId, COALESCE(tx.taxPerc, 0) AS taxPerc, s.acCode, s.serviceGroup, c.fromRange, c.toRange, s.criteriaType,s.serviceShortDesc,GROUP_CONCAT(distinct sm.storageType) AS storageTypes "
			+ "FROM CFSTariffService c "
			+ "LEFT OUTER JOIN CfsTarrif t ON c.companyId = t.companyId AND c.branchId = t.branchId AND c.cfsTariffNo = t.cfsTariffNo "
			+ "AND c.cfsAmendNo = t.cfsAmndNo "
			+ "LEFT OUTER JOIN Services s ON c.companyId = s.companyId AND c.branchId = s.branchId AND c.serviceId = s.serviceId "
			+ "LEFT OUTER JOIN ServiceMapping sm ON c.companyId = sm.companyId AND c.branchId = sm.branchId AND s.serviceId = sm.serviceId "
			+ "LEFT OUTER JOIN CurrencyConv cu ON c.companyId = cu.companyId AND c.branchId = cu.branchId AND c.currencyId = cu.convCurrency "
			+ "LEFT OUTER JOIN TaxDtl tx ON s.companyId = tx.companyId AND s.taxId = tx.taxId "
			+ "AND DATE(:assessDate) BETWEEN tx.periodFrom AND tx.periodTo "
			+ "WHERE c.companyId = :cid AND c.branchId = :bid "
			+ "AND c.status = 'A' AND :assessDate < t.cfsValidateDate AND c.serviceId IN :serviceList "
			+ "AND c.cfsTariffNo = :tariffNo AND c.containerSize IN :consize AND c.cargoType IN :type "
			+ "GROUP BY c.serviceId " + "ORDER BY c.serviceId")
	List<Object[]> getServiceRate(@Param("cid") String cid, @Param("bid") String bid,
			@Param("assessDate") Date assessDate, @Param("con") String con,
			@Param("serviceList") List<String> serviceList, @Param("tariffNo") String tariffNo,
			@Param("consize") List<String> consize, @Param("type") List<String> type);

	@Query(value = "select NEW com.cwms.entities.SSRDtl(s.containerNo, s.serviceId, s.serviceUnit, s.executionUnit,s.serviceUnit1, s.executionUnit1, s.rate,"
			+ "s.totalRate) " + "from SSRDtl s "
			+ "where s.companyId=:cid and s.branchId=:bid and s.transId=:trans and s.containerNo=:con and s.status = 'A'")
	List<SSRDtl> getServiceId(@Param("cid") String cid, @Param("bid") String bid, @Param("trans") String trans,
			@Param("con") String con);

	@Query(value = "select c.cfsTariffNo " + "from CfsTarrif c "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and c.partyId IN :partyid and "
			+ "c.importerId IN :importer and c.cha IN :cha and c.forwarderId IN :forwarder and c.nvoccTariff='N' "
			+ "and c.offdocTariff='N' and c.tariffType = 'Standard' order by FIELD(c.partyId,:partyid1,''),FIELD(c.importerId,:importer1,''),"
			+ "FIELD(c.cha,:cha1,''),FIELD(c.forwarderId,:forwarder1,'') limit 1")
	String getTarrifNo(@Param("cid") String cid, @Param("bid") String bid, @Param("partyid") List<String> partyid,
			@Param("importer") List<String> importer, @Param("cha") List<String> cha,
			@Param("forwarder") List<String> forwarder, @Param("partyid1") String partyid1,
			@Param("importer1") String importer1, @Param("cha1") String cha1, @Param("forwarder1") String forwarder1);

	@Query(value = "select c.serviceId " + "from CFSTariffService c "
			+ "LEFT OUTER JOIN CfsTarrif t ON c.companyId=t.companyId and c.branchId=t.branchId and c.cfsTariffNo=t.cfsTariffNo "
			+ "and c.cfsAmendNo=t.cfsAmndNo where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and t.status='A' and "
			+ "c.cfsTariffNo=:tarrifNo and c.defaultChk='Y' group by c.serviceId order by c.serviceId")
	List<String> getDefaultServiceId(@Param("cid") String cid, @Param("bid") String bid,
			@Param("tarrifNo") String tarrifNo);

	@Query(value = "select s.serviceId " + "from ServiceMapping s "
			+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
			+ "s.profitcentreId = :profit and s.invoiceType = :type")
	List<String> getServicesForExportInvoiceAll(@Param("cid") String cid, @Param("bid") String bid,
			@Param("profit") String profit, @Param("type") String type);

	@Query(value = "select s.serviceId " + "from ServiceMapping s "
			+ "LEFT OUTER JOIN Services s1 ON s.companyId=s1.companyId and s.branchId=s1.branchId and s.serviceId=s1.serviceId "
			+ "where s.companyId = :cid and s.branchId = :bid and s.status = 'A' and "
			+ "s.profitcentreId = :profit and s.invoiceType = :type and s1.serviceGroup=:sgroup")
	List<String> getServicesForExportInvoiceByType(@Param("cid") String cid, @Param("bid") String bid,
			@Param("profit") String profit, @Param("type") String type, @Param("sgroup") String sgroup);

	@Query("SELECT MIN(a.cartingTransDate), MIN(a.cartingTransId) " + "FROM ExportCarting a "
			+ "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId " + "AND a.sbTransId = :sbTransId "
			+ "AND a.sbNo = :sbNo " + "AND a.status <> 'D'")
	Object[] getCartingDateAndId(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("sbTransId") String sbTransId, @Param("sbNo") String sbNo);

	@Query(value = "SELECT a.Container_No, a.Container_Size, a.Container_Type, a.Stuff_Tally_Date, "
			+ "r.Gross_Weight, r.Type_of_Container, a.movement_req_date, a.gate_in_date, a.stuff_tally_Id, "
			+ "SUM(b.cargo_weight) AS CargoWeight, b.Gate_Out_date, c.SSR_Trans_Id, "
			+ "GROUP_CONCAT(DISTINCT CONCAT(e.Service_Id, '~', e.rate, '~', e.Srl_No, '~', e.Sr_No)) AS ServiceDetails, "
			+ "a.Movement_Req_Id, a.Hold_Status, r.Force_Entry_Flag, b.Container_Invoice_Type "
			+ "FROM cfexpinventory a " + "LEFT OUTER JOIN cfstufftally b "
			+ "ON b.Company_Id = a.Company_Id AND b.Branch_Id = a.Branch_Id AND b.stuff_tally_Id = a.stuff_tally_Id AND b.container_no = a.container_no "
			+ "LEFT OUTER JOIN cfstuffrq c "
			+ "ON c.Company_Id = a.Company_Id AND c.Branch_Id = b.Branch_Id AND c.container_no = b.container_no AND c.sb_trans_id = b.sb_trans_id AND c.Stuff_req_Id = b.stuff_Id "
			+ "LEFT OUTER JOIN cfssrdtl e "
			+ "ON b.Company_Id = e.Company_Id AND c.Branch_Id = e.Branch_Id AND c.SSR_Trans_Id = e.Trans_Id AND c.ProfitCentre_Id = e.ProfitCentre_Id "
			+ "AND c.SB_Trans_Id = e.Erp_Doc_ref_no AND c.SB_No = e.Doc_ref_no AND c.Container_no = e.Container_no AND e.status = 'A' AND e.Assesment_Id = '' "
			+ "LEFT OUTER JOIN cfexpmovementreq r "
			+ "ON b.Company_Id = r.Company_Id AND b.Branch_Id = r.Branch_Id AND b.stuff_tally_Id = r.stuff_tally_Id AND b.container_no = r.container_no AND b.Movement_Req_Id = r.Movement_Req_Id "
			+ "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId "
			+ "AND (b.Invoice_Assesed <> 'Y' OR b.Invoice_Assesed IS NULL OR b.Invoice_Assesed = '') AND r.Type_of_Container != '' AND c.SB_Trans_Id = :sbTransId AND r.mov_req_type = :movReqType "
			+ "GROUP BY a.Container_No " + "ORDER BY a.Container_No " + "LIMIT 1000", nativeQuery = true)
	List<Object[]> findContainerDetailsForContainerInvoice(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("sbTransId") String sbTransId, @Param("movReqType") String movReqType);
	
	
	
	
	@Query(value = "SELECT a.Container_No, a.Container_Size, a.Container_Type, a.Stuff_Tally_Date, "
			+ "r.Gross_Weight, r.Type_of_Container, a.movement_req_date, a.gate_in_date, a.stuff_tally_Id, "
			+ "SUM(b.cargo_weight) AS CargoWeight, b.Gate_Out_date, b.SSR_Trans_Id, "
			+ "GROUP_CONCAT(DISTINCT CONCAT(e.Service_Id, '~', e.rate, '~', e.Srl_No, '~', e.Sr_No)) AS ServiceDetails, "
			+ "a.Movement_Req_Id, a.Hold_Status, r.Force_Entry_Flag, b.Container_Invoice_Type "
			+ "FROM cfexpinventory a " + "LEFT OUTER JOIN cfstufftally b "
			+ "ON b.Company_Id = a.Company_Id AND b.Branch_Id = a.Branch_Id AND b.stuff_tally_Id = a.stuff_tally_Id AND b.container_no = a.container_no "			
			+ "LEFT OUTER JOIN cfssrdtl e "
			+ "ON b.Company_Id = e.Company_Id AND b.Branch_Id = e.Branch_Id AND b.SSR_Trans_Id = e.Trans_Id AND b.ProfitCentre_Id = e.ProfitCentre_Id "
			+ "AND b.Container_no = e.Container_no AND e.status = 'A' AND e.Assesment_Id = '' "
			+ "LEFT OUTER JOIN cfexpmovementreq r "
			+ "ON b.Company_Id = r.Company_Id AND b.Branch_Id = r.Branch_Id AND b.stuff_tally_Id = r.stuff_tally_Id AND b.container_no = r.container_no AND b.Movement_Req_Id = r.Movement_Req_Id "
			+ "WHERE a.Company_Id = :companyId AND a.Branch_Id = :branchId "
			+ "AND (b.Invoice_Assesed <> 'Y' OR b.Invoice_Assesed IS NULL OR b.Invoice_Assesed = '') AND r.Type_of_Container != '' AND b.SB_Trans_Id = :sbTransId AND r.mov_req_type = :movReqType "
			+ "GROUP BY a.Container_No " + "ORDER BY a.Container_No " + "LIMIT 1000", nativeQuery = true)
	List<Object[]> findContainerDetailsForContainerInvoiceBufferAndONWH(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("sbTransId") String sbTransId, @Param("movReqType") String movReqType);
	
	
	
	
	
	@Query(value = "SELECT a.Container_No, a.Container_Size, a.Container_Type, a.Stuff_Tally_Date, " +
	        "r.Gross_Weight, r.Type_of_Container, a.movement_req_date, a.gate_in_date, a.stuff_tally_Id, " +
	        "'0' AS CargoWeight, a.Gate_Out_date, '' AS SSR_Trans_Id, '' AS ServiceDetails, " +
	        "a.Movement_Req_Id, a.Hold_Status, r.Force_Entry_Flag, '' " +
	        "FROM cfexpinventory a " + 
	        
	        "LEFT OUTER JOIN cfexpmovementreq r " +
	        "ON a.Company_Id = r.Company_Id " +
	        "AND a.Branch_Id = r.Branch_Id " +
	        "AND a.stuff_tally_Id = r.stuff_tally_Id " +
	        "AND a.container_no = r.container_no " +
	        "AND a.Movement_Req_Id = r.Movement_Req_Id " +

	        "LEFT OUTER JOIN cfgatein b " +
	        "ON a.Company_Id = b.Company_Id " +
	        "AND b.Branch_Id = a.Branch_Id " +
	        "AND b.gate_in_id = a.stuff_tally_Id " +
	        "AND b.erp_doc_ref_no = :sbTransId " +

	        "WHERE a.Company_Id = :companyId " +
	        "AND a.Branch_Id = :branchId " +
	        "AND COALESCE(r.invoice_assesed, '') <> 'Y' " +
	        "AND r.Type_of_Container != '' " +
	        "AND b.erp_doc_ref_no = :sbTransId " +
	        "AND r.mov_req_type = :movReqType " +

	        "GROUP BY a.Container_No, a.Container_Size, a.Container_Type, a.Stuff_Tally_Date, " +
	        "r.Gross_Weight, r.Type_of_Container, a.movement_req_date, a.gate_in_date, " +
	        "a.stuff_tally_Id, a.Gate_Out_date, a.Movement_Req_Id, a.Hold_Status, " +
	        "r.Force_Entry_Flag " +

	        "ORDER BY a.Container_No " +
	        "LIMIT 1000", nativeQuery = true)
	List<Object[]> findContainerDetailsForContainerInvoicePortReturn(
	        @Param("companyId") String companyId,
	        @Param("branchId") String branchId,
	        @Param("sbTransId") String sbTransId,
	        @Param("movReqType") String movReqType);

	
	
	
	
	
	
	@Query("SELECT DISTINCT " + "    new com.cwms.helper.ExportContainerAssessmentData("
			+ "        a.erpDocRefNo, a.docRefNo, a.docRefDate, e.cha, b.partyName, chaA.gstNo, chaA.address1, chaA.address2, chaA.address3, chaA.state, chaA.srNo, "
			+ "        a.commodity, a.profitcentreId, c.profitcentreDesc, i.partyName, e.exporterId, e.exporterAddress1, e.exporterAddress2, e.exporterAddress3, "
			+ "        e.state, e.gstNo, e.srNo, f.typeOfPackage, s.invoiceAssessed, s.assessmentId, s.invoiceNo, "
			+ "        i.tanNoId, ch.tanNoId, fc.tanNoId, cha.gstNo, cha.address1, cha.address2, cha.address3, cha.state, cha.srNo, "
			+ "        imp.gstNo, imp.address1, imp.address2, imp.address3, imp.state, imp.srNo, cus.gstNo, cus.address1, cus.address2, "
			+ "        cus.address3, cus.state, cus.srNo, e.onAccountOf, fc.partyName, f.grossWeight, e.holdStatus, s.movementReqId, "
			+ "        f.newCommodity, s.shippingAgent, s.shippingLine, '', '', s.movementReqDate, s.movementReqDate" + "    ) "
			+ "FROM ExportMovement s " + "LEFT JOIN GateIn a ON " + "    a.companyId = s.companyId AND "
			+ "    a.branchId = s.branchId AND " + "    a.profitcentreId = s.profitcentreId AND "
			+ "    a.gateInId = s.stuffTallyId " + "LEFT JOIN Profitcentre c ON "
			+ "    a.companyId = c.companyId AND " + "    a.branchId = c.branchId AND "
			+ "    a.profitcentreId = c.profitcentreId " + "LEFT JOIN ExportSbEntry e ON "
			+ "    a.companyId = e.companyId AND " + "    a.branchId = e.branchId AND "
			+ "    a.profitcentreId = e.profitcentreId AND " + "    e.sbTransId = a.erpDocRefNo AND "
			+ "    e.sbNo = a.docRefNo " + "LEFT JOIN ExportSbCargoEntry f ON " + "    e.companyId = f.companyId AND "
			+ "    e.branchId = f.branchId AND " + "    e.profitcentreId = f.profitcentreId AND "
			+ "    f.sbTransId = e.sbTransId AND " + "    f.sbNo = e.sbNo " + "LEFT JOIN Party i ON "
			+ "    i.companyId = e.companyId AND " + "    i.branchId = e.branchId AND "
			+ "    e.exporterId = i.partyId AND " + "    i.status <> 'D' " + "LEFT JOIN Party b ON "
			+ "    b.companyId = e.companyId AND " + "    b.branchId = e.branchId AND " + "    e.cha = b.partyId AND "
			+ "    b.status <> 'D' " + "LEFT JOIN Party ch ON " + "    ch.companyId = s.companyId AND "
			+ "    ch.branchId = s.branchId AND " + "    s.onAccountOf = ch.partyId AND " + "    ch.status <> 'D' "
			+ "LEFT JOIN Party fc ON " + "    fc.companyId = e.companyId AND " + "    fc.branchId = e.branchId AND "
			+ "    e.onAccountOf = fc.partyId AND " + "    fc.status <> 'D' " + "LEFT JOIN PartyAddress cha ON "
			+ "    cha.companyId = s.companyId AND " + "    cha.branchId = s.branchId AND "
			+ "    s.onAccountOf = cha.partyId AND " + "    cha.defaultChk = 'Y' AND " + "    cha.status <> 'D' "
			+ "LEFT JOIN PartyAddress imp ON " + "    imp.companyId = s.companyId AND "
			+ "    imp.branchId = s.branchId AND " + "    e.exporterId = imp.partyId AND "
			+ "    imp.defaultChk = 'Y' AND " + "    imp.status <> 'D' " + "LEFT JOIN PartyAddress chaA ON "
			+ "    chaA.companyId = s.companyId AND " + "    chaA.branchId = s.branchId AND "
			+ "    e.cha = chaA.partyId AND " + "    chaA.defaultChk = 'Y' AND " + "    chaA.status <> 'D' "
			+ "LEFT JOIN PartyAddress cus ON " + "    cus.companyId = s.companyId AND "
			+ "    cus.branchId = s.branchId AND " + "    e.onAccountOf = cus.partyId AND "
			+ "    cus.defaultChk = 'Y' AND " + "    cus.status <> 'D' " + "WHERE a.companyId = :companyId "
			+ "AND a.branchId = :branchId "
			+ "AND a.profitcentreId = :profitcentreId "
			+ "AND (:containerNo IS NULL OR :containerNo = '' OR s.containerNo = :containerNo) AND a.docRefNo = :sbNo "
			+ "AND s.status = 'A' " + "AND a.status = 'A' AND s.movReqType = :movReqType "
			+ "ORDER BY s.movementReqId, a.createdDate DESC")
	List<ExportContainerAssessmentData> getContainerAssessmentDataPortReturn(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("profitcentreId") String profitcentreId,
			@Param("containerNo") String containerNo, @Param("sbNo") String sbNo,
			@Param("movReqType") String movReqType);
	
	
	
	
	

	@Query("SELECT DISTINCT " + "    new com.cwms.helper.ExportContainerAssessmentData("
			+ "        a.sbTransId, a.sbNo, a.sbDate, e.cha, b.partyName, chaA.gstNo, chaA.address1, chaA.address2, chaA.address3, chaA.state, chaA.srNo, "
			+ "        a.commodity, a.profitcentreId, c.profitcentreDesc, a.exporterName, e.exporterId, e.exporterAddress1, e.exporterAddress2, e.exporterAddress3, "
			+ "        e.state, e.gstNo, e.srNo, f.typeOfPackage, a.invoiceAssesed, a.assesmentId, a.invoiceNo, "
			+ "        i.tanNoId, ch.tanNoId, fc.tanNoId, cha.gstNo, cha.address1, cha.address2, cha.address3, cha.state, cha.srNo, "
			+ "        imp.gstNo, imp.address1, imp.address2, imp.address3, imp.state, imp.srNo, cus.gstNo, cus.address1, cus.address2, "
			+ "        cus.address3, cus.state, cus.srNo, e.onAccountOf, fc.partyName, f.grossWeight, e.holdStatus, s.movementReqId, "
			+ "        f.newCommodity, s.shippingAgent, s.shippingLine, a.stuffTallyWoTransId, a.stuffMode, s.movementReqDate, s.movementReqDate" + "    ) "
			+ "FROM ExportMovement s " + "LEFT JOIN ExportStuffTally a ON " + "    a.companyId = s.companyId AND "
			+ "    a.branchId = s.branchId AND " + "    a.profitcentreId = s.profitcentreId AND "
			+ "    a.containerNo = s.containerNo " + "LEFT JOIN Profitcentre c ON "
			+ "    a.companyId = c.companyId AND " + "    a.branchId = c.branchId AND "
			+ "    a.profitcentreId = c.profitcentreId " + "LEFT JOIN ExportSbEntry e ON "
			+ "    a.companyId = e.companyId AND " + "    a.branchId = e.branchId AND "
			+ "    a.profitcentreId = e.profitcentreId AND " + "    e.sbTransId = a.sbTransId AND "
			+ "    e.sbNo = a.sbNo " + "LEFT JOIN ExportSbCargoEntry f ON " + "    e.companyId = f.companyId AND "
			+ "    e.branchId = f.branchId AND " + "    e.profitcentreId = f.profitcentreId AND "
			+ "    f.sbTransId = e.sbTransId AND " + "    f.sbNo = e.sbNo " + "LEFT JOIN Party i ON "
			+ "    i.companyId = e.companyId AND " + "    i.branchId = e.branchId AND "
			+ "    e.exporterId = i.partyId AND " + "    i.status <> 'D' " + "LEFT JOIN Party b ON "
			+ "    b.companyId = e.companyId AND " + "    b.branchId = e.branchId AND " + "    e.cha = b.partyId AND "
			+ "    b.status <> 'D' " + "LEFT JOIN Party ch ON " + "    ch.companyId = s.companyId AND "
			+ "    ch.branchId = s.branchId AND " + "    s.onAccountOf = ch.partyId AND " + "    ch.status <> 'D' "
			+ "LEFT JOIN Party fc ON " + "    fc.companyId = e.companyId AND " + "    fc.branchId = e.branchId AND "
			+ "    e.onAccountOf = fc.partyId AND " + "    fc.status <> 'D' " + "LEFT JOIN PartyAddress cha ON "
			+ "    cha.companyId = s.companyId AND " + "    cha.branchId = s.branchId AND "
			+ "    s.onAccountOf = cha.partyId AND " + "    cha.defaultChk = 'Y' AND " + "    cha.status <> 'D' "
			+ "LEFT JOIN PartyAddress imp ON " + "    imp.companyId = s.companyId AND "
			+ "    imp.branchId = s.branchId AND " + "    e.exporterId = imp.partyId AND "
			+ "    imp.defaultChk = 'Y' AND " + "    imp.status <> 'D' " + "LEFT JOIN PartyAddress chaA ON "
			+ "    chaA.companyId = s.companyId AND " + "    chaA.branchId = s.branchId AND "
			+ "    e.cha = chaA.partyId AND " + "    chaA.defaultChk = 'Y' AND " + "    chaA.status <> 'D' "
			+ "LEFT JOIN PartyAddress cus ON " + "    cus.companyId = s.companyId AND "
			+ "    cus.branchId = s.branchId AND " + "    e.onAccountOf = cus.partyId AND "
			+ "    cus.defaultChk = 'Y' AND " + "    cus.status <> 'D' " + "WHERE a.companyId = :companyId "
			+ "AND a.branchId = :branchId "
			+ "AND (a.invoiceAssesed <> 'Y' OR a.invoiceAssesed IS NULL OR a.invoiceAssesed = '') "
			+ "AND a.profitcentreId = :profitcentreId "
			+ "AND (:containerNo IS NULL OR :containerNo = '' OR a.containerNo = :containerNo) AND a.sbNo = :sbNo "
			+ "AND s.status = 'A' " + "AND a.status = 'A' AND s.movReqType = :movReqType "
			+ "ORDER BY s.movementReqId, a.createdDate DESC")
	List<ExportContainerAssessmentData> getContainerAssessmentData(@Param("companyId") String companyId,
			@Param("branchId") String branchId, @Param("profitcentreId") String profitcentreId,
			@Param("containerNo") String containerNo, @Param("sbNo") String sbNo,
			@Param("movReqType") String movReqType);

	@Query("SELECT DISTINCT a.movReqType " + "FROM ExportMovement a " + "WHERE a.companyId = :companyId "
			+ "AND a.branchId = :branchId " + "AND a.profitcentreId = :profitcentreId " + "AND a.sbNo = :sbNo "
			+ "AND a.status = 'A' order By a.createdDate DESC")
	List<String> getDistinctMovementType(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("sbNo") String sbNo);

	@Query("SELECT DISTINCT a.sbNo " + "FROM ExportMovement a "
			+ "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId "
			+ "AND a.invoiceAssessed <> 'Y' " + "AND a.profitcentreId = :profitcentreId "
			+ "AND a.containerNo = :containerNo " + "AND a.status = 'A' Order By a.createdDate DESC")
	List<String> getDistinctSbNoForContainerNew(@Param("companyId") String companyId, @Param("branchId") String branchId,
										  @Param("profitcentreId") String profitcentreId, @Param("containerNo") String containerNo);
	
	
	@Query("SELECT DISTINCT a.sbNo " + "FROM ExportStuffTally a "
			+ "LEFT OUTER JOIN ExportMovement m ON a.companyId = m.companyId " + "AND a.branchId = m.branchId "
			+ "AND a.profitcentreId = m.profitcentreId " + "AND a.stuffTallyId = m.stuffTallyId "
			+ "AND a.containerNo = m.containerNo " + "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId "
			+ "AND m.invoiceAssessed <> 'Y' " + "AND a.profitcentreId = :profitcentreId "
			+ "AND a.containerNo = :containerNo " + "AND a.status = 'A'")
	List<String> getDistinctSbNoForContainer(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("containerNo") String containerNo);
	
	
	
	@Query("SELECT DISTINCT a.containerNo " + "FROM ExportMovement a "
			+ "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId "
			+ "AND a.invoiceAssessed <> 'Y' " + "AND a.profitcentreId = :profitcentreId "
			+ "AND (:searchValue IS NULL OR :searchValue = '' OR a.containerNo LIKE CONCAT(:searchValue, '%')) "
			+ "AND a.status = 'A'")
	List<String> getDistinctContainerNoNew(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("searchValue") String searchValue);
	

	@Query("SELECT DISTINCT a.containerNo " + "FROM ExportStuffTally a "
			+ "LEFT OUTER JOIN ExportMovement m ON a.companyId = m.companyId " + "AND a.branchId = m.branchId "
			+ "AND a.profitcentreId = m.profitcentreId " + "AND a.stuffTallyId = m.stuffTallyId "
			+ "AND a.containerNo = m.containerNo " + "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId "
			+ "AND m.invoiceAssessed <> 'Y' " + "AND a.profitcentreId = :profitcentreId "
			+ "AND (:searchValue IS NULL OR :searchValue = '' OR a.containerNo LIKE CONCAT(:searchValue, '%')) "
			+ "AND a.status = 'A'")
	List<String> getDistinctContainerNo(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("searchValue") String searchValue);

	@Query("SELECT DISTINCT a.sbNo " + "FROM ExportStuffTally a "
			+ "LEFT OUTER JOIN ExportMovement m ON a.companyId = m.companyId " + "AND a.branchId = m.branchId "
			+ "AND a.profitcentreId = m.profitcentreId " + "AND a.stuffTallyId = m.stuffTallyId "
			+ "AND a.containerNo = m.containerNo " + "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId "
			+ "AND m.invoiceAssessed <> 'Y' " + "AND (m.movementReqId <> '' OR m.movementReqId <> NULL) "
			+ "AND a.profitcentreId = :profitcentreId "
			+ "AND (:searchValue IS NULL OR :searchValue = '' OR a.sbNo LIKE CONCAT(:searchValue, '%')) "
			+ "AND a.status = 'A'")
	List<String> getDistinctSbNoSearchList(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("searchValue") String searchValue);

	
	
	
	
	@Query("SELECT DISTINCT a.sbNo " + "FROM ExportSbEntry a "
			+ "LEFT OUTER JOIN ExportSbCargoEntry m ON a.companyId = m.companyId " + "AND a.branchId = m.branchId "
			+ "AND a.profitcentreId = m.profitcentreId " + "AND a.sbNo = m.sbNo "
			+ "AND a.sbTransId = m.sbTransId " + "WHERE a.companyId = :companyId " + "AND a.branchId = :branchId "
			+ "AND (m.crgInvoiceNo = '' OR m.crgInvoiceNo = NULL) "
			+ "AND a.profitcentreId = :profitcentreId "
			+ "AND (:searchValue IS NULL OR :searchValue = '' OR a.sbNo LIKE CONCAT(:searchValue, '%')) "
			+ "AND a.status = 'A'")
	List<String> getDistinctSbNoSearchCartingList(@Param("companyId") String companyId, @Param("branchId") String branchId,
			@Param("profitcentreId") String profitcentreId, @Param("searchValue") String searchValue);
	
	
	
	
	
	@Query(value = "SELECT p.party_id, p.party_name " + "FROM party p " + "WHERE p.company_id = :cid "
			+ "AND p.branch_id = :bid " + "AND p.status != 'D' " + "AND (" + "   (:type = 'agent') "
			+ "   OR (:type = 'cha' AND p.cha = 'Y') " + "   OR (:type = 'exp' AND p.exp = 'Y') "
			+ "   OR (:type = 'forwarder' AND p.frw = 'Y') " + ") "

			+ "AND (:val IS NULL OR :val = '' OR p.party_name LIKE CONCAT(:val, '%'))", nativeQuery = true)
	List<Object[]> getPartyByTypeValueTariff(@Param("cid") String cid, @Param("bid") String bid,
			@Param("val") String val, @Param("type") String type);

	@Query(value = "SELECT " + "    p.party_Id AS partyId, " + "    p.party_Name AS partyName, "
			+ "    a.address_1 AS address1, " + "    a.address_2 AS address2, " + "    a.address_3 AS address3, "
			+ "    a.sr_No AS srNo, " + "    a.gst_No AS gstNo, " + "    a.State AS state, "
			+ "    p.iec_Code AS iecCode " + "FROM " + "    Party p " + "LEFT JOIN " + "    PartyAddress a "
			+ "    ON p.company_Id = a.company_Id " + "    AND p.branch_Id = a.branch_Id "
			+ "    AND p.party_Id = a.party_Id " + "WHERE " + "    p.company_Id = :cid " + "    AND p.branch_Id = :bid "
			+ "    AND p.status != 'D' " + "    "

			+ "AND (" + "   (:type = 'agent') " + "   OR (:type = 'cha' AND p.cha = 'Y') "
			+ "   OR (:type = 'exp' AND p.exp = 'Y') " + "   OR (:type = 'forwarder' AND p.frw = 'Y') " + ") "

			+ "    AND (:val IS NULL OR p.party_Name LIKE CONCAT(:val, '%'))", nativeQuery = true)
	List<Map<String, Object>> getPartyByTypeWithAddress(@Param("cid") String cid, @Param("bid") String bid,
			@Param("val") String val, @Param("type") String type);


	
	@Query(value="select NEW com.cwms.entities.CfinvsrvanxPro(a.companyId, a.branchId, a.assesmentId,a.assesmentLineNo,a.assesmentDate,a.containerNo,a.containerSize,a.containerType,a.gateInDate,a.invoiceUptoDate "
			+ ",e.gateOutId,e.gatePassNo, a.partyId, e.gateOutDate, a.destuffDate, a.stuffTallyDate) "
			+ "from AssessmentSheetPro a "
			+ "LEFT JOIN ExportInventory e on a.companyId = e.companyId AND a.branchId = e.branchId AND a.assesmentId = e.assessmentId AND a.containerNo = e.containerNo AND e.status <> 'D' "
	        + "where a.companyId=:companyId and a.branchId=:branchId and a.status <> 'D' and a.assesmentId=:assesmentId AND a.profitcentreId = :profiCentreId Order By a.containerNo")
	List<CfinvsrvanxPro> getAllContainerListOfAssessMentSheet1(@Param("companyId") String companyId, @Param("branchId") String branchId, @Param("profiCentreId") String profiCentreId, @Param("assesmentId") String assesmentId);
	
	

}


