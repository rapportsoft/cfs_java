package com.cwms.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AssessmentSheet;
import com.cwms.entities.AssessmentSheetPro;

public interface AssessmentSheetProRepository extends JpaRepository<AssessmentSheetPro, String> {


	@Query(value = "SELECT DISTINCT c.igmTransId, c.igmNo, c.igmLineNo, crg.blNo, crg.beNo "
	        + "FROM Cfigmcn c "
	        + "LEFT OUTER JOIN Cfigmcrg crg "
	        + "ON c.companyId = crg.companyId "
	        + "AND c.branchId = crg.branchId "
	        + "AND c.igmNo = crg.igmNo "
	        + "AND c.igmTransId = crg.igmTransId "
	        + "AND c.igmLineNo = crg.igmLineNo "
	        + "WHERE c.companyId = :cid "
	        + "AND c.branchId = :bid "
	        + "AND c.status = 'A' "
	        + "AND crg.status = 'A' "
	        + "AND (c.gateInId IS NOT NULL AND c.gateInId != '') "
	        + "AND (c.gateOutId IS NULL OR c.gateOutId = '') "
	        + "AND (:val IS NULL OR :val = '' "
	        + "OR c.igmNo LIKE %:val% "
	        + "OR crg.blNo LIKE %:val% "
	        + "OR crg.beNo LIKE %:val%)")
	List<Object[]> getBeforeAssessmentData(
	        @Param("cid") String cid,
	        @Param("bid") String bid,
	        @Param("val") String val);
	
	
	@Query(value = "select c.igmTransId,c.igmNo,c.igmLineNo,crg.blNo,crg.beNo,p.profitcentreDesc,i.viaNo,i.igmDate,crg.blDate,"
			+ "p1.partyName,p2.partyName,crg.cargoValue,crg.cargoDuty,crg.commodityDescription,crg.importerId,crg.importerName,"
			+ "crg.importerSr,crg.importerAddress1,crg.importerAddress2,crg.importerAddress3,pa1.gstNo,c.cha,p3.partyName,"
			+ "pa2.srNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,crg.accountHolderId,crg.accountHolderName,pa3.srNo,"
			+ "pa3.gstNo,pa3.state,c.containerNo,c.containerSize,c.containerType,c.gateInDate,c.deStuffDate,c.gateOutDate"
			+ ",c.examinedPackages,c.typeOfContainer,c.scannerType,c.gateOutType,c.upTariffNo,c.profitcentreId,"
			+ "c.grossWt,c.cargoWt,c.ssrTransId,i.shippingAgent,i.shippingLine,c.containerStatus,c.gatePassNo,c.gateOutId,"
			+ "c.assesmentId,c.lastAssesmentDate,c.invoiceNo,c.invoiceDate,c.invoiceUptoDate,d.areaOccupied "
			+ "from Cfigmcn c "
			+ "LEFT OUTER JOIN Cfigmcrg crg ON c.companyId=crg.companyId and c.branchId=crg.branchId and c.igmNo=crg.igmNo and c.igmTransId=crg.igmTransId and c.igmLineNo=crg.igmLineNo "
			+ "LEFT OUTER JOIN Profitcentre p ON c.companyId=p.companyId and c.branchId=p.branchId and c.profitcentreId=p.profitcentreId "
			+ "LEFT OUTER JOIN CFIgm i ON c.companyId=i.companyId and c.branchId=i.branchId and c.igmNo=i.igmNo and c.igmTransId=i.igmTransId "
			+ "LEFT OUTER JOIN Party p1 ON i.companyId=p1.companyId and i.branchId=p1.branchId and i.shippingLine=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON i.companyId=p2.companyId and i.branchId=p2.branchId and i.shippingAgent=p2.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa1 ON crg.companyId=pa1.companyId and crg.branchId=pa1.branchId and crg.importerId=pa1.partyId and crg.importerSr=pa1.srNo "
			+ "LEFT OUTER JOIN Party p3 ON c.companyId=p3.companyId and c.branchId=p3.branchId and c.cha=p3.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa2 ON p3.companyId=pa2.companyId and p3.branchId=pa2.branchId and p3.partyId=pa2.partyId and pa2.defaultChk = 'Y' "
			+ "LEFT OUTER JOIN PartyAddress pa3 ON crg.companyId=pa3.companyId and crg.branchId=pa3.branchId and crg.accountHolderId=pa3.partyId and pa3.defaultChk = 'Y' "
			+ "LEFT OUTER JOIN DestuffCrg d ON c.companyId=d.companyId and c.branchId=d.branchId and c.igmTransId=d.igmTransId and c.igmNo=d.igmNo and c.igmLineNo=d.igmLineNo and c.deStuffId=d.deStuffId and d.status = 'A' "
			+ "where c.companyId=:cid and c.branchId=:bid and c.status = 'A' and (c.gateInId is not null AND c.gateInId != '') and "
			+ "crg.status = 'A' and c.igmTransId=:trans and c.igmNo=:igm and c.igmLineNo=:lineNo and "
			+ "(c.gateOutId is null OR c.gateOutId = '') ")
	List<Object[]> getBeforeSaveAssessmentData(@Param("cid") String cid, @Param("bid") String bid,
			@Param("trans") String trans,@Param("igm") String igm,@Param("lineNo") String lineNo);
	
	
	@Query(value="select a from AssessmentSheetPro a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id")
	List<AssessmentSheetPro> getDataByAssessmentId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select DISTINCT a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.igmTransId,a.status,a.igmNo,a.igmLineNo,"
			+ "a.viaNo,a.igmDate,a.createdBy,a.blNo,a.blDate,a.profitcentreId,p.profitcentreDesc,a.sl,p1.partyName,a.sa,p2.partyName,"
			+ "pa1.address1,pa1.address2,pa1.address3,pa1.gstNo,"
			+ "a.insuranceValue,a.dutyValue,a.commodityDescription,a.commodityCode,a.importerId,a.importerName,a.impSrNo,"
			+ "a.cha,a.chaSrNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,"
			+ "a.sez,a.taxApplicable,a.onAccountOf,p5.partyName,a.accSrNo,pa4.gstNo,pa4.state,"
			+ "a.comments,a.othPartyId,p4.partyName,a.othSrNo,pa3.gstNo,pa3.state,a.billingParty,"
			+ "a.invoiceNo,a.creditType,a.invoiceCategory,a.isAncillary,a.invoiceDate,a.irn,a.receiptNo,a.intComments,"
			+ "a.containerNo,a.containerSize,a.containerType,a.gateInDate,a.destuffDate,a.gateOutDate,a.examinedPercentage,"
			+ "a.typeOfContainer,a.scanerType,a.gateOutType,i.invoiceUptoDate,i.serviceId,s.serviceShortDesc,i.actualNoOfPackages,p3.partyName,"
			+ "i.rate,i.taxPerc,a.lastAssesmentId,a.lastAssesmentDate,a.lastInvoiceNo,a.lastInvoiceDate,a.lastInvoiceUptoDate "
			+ "from AssessmentSheetPro a "
			+ "LEFT OUTER JOIN Party p1 ON a.companyId=p1.companyId and a.branchId=p1.branchId and a.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON a.companyId=p2.companyId and a.branchId=p2.branchId and a.sa=p2.partyId "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "LEFT OUTER JOIN PartyAddress pa1 ON a.companyId=pa1.companyId and a.branchId=pa1.branchId and a.importerId=pa1.partyId and a.impSrNo=CAST(pa1.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p3 ON a.companyId=p3.companyId and a.branchId=p3.branchId and a.cha=p3.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa2 ON a.companyId=pa2.companyId and a.branchId=pa2.branchId and a.cha=pa2.partyId and a.chaSrNo=CAST(pa2.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p4 ON a.companyId=p4.companyId and a.branchId=p4.branchId and a.othPartyId=p4.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa3 ON a.companyId=pa3.companyId and a.branchId=pa3.branchId and a.othPartyId=pa3.partyId and a.othSrNo=pa3.srNo "
			+ "LEFT OUTER JOIN Party p5 ON a.companyId=p5.companyId and a.branchId=p5.branchId and a.onAccountOf=p5.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa4 ON a.companyId=pa4.companyId and a.branchId=pa4.branchId and a.onAccountOf=pa4.partyId and a.accSrNo=CAST(pa4.srNo as INTEGER) "
			+ "LEFT OUTER JOIN CfinvsrvanxPro i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId and a.igmTransId=i.erpDocRefNo and a.containerNo=i.containerNo "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getAssessmentData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from AssessmentSheetPro a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id "
			+ "and (a.invoiceNo = '' OR a.invoiceNo is null)")
	List<AssessmentSheetPro> getDataByAssessmentId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	@Query(value="select DISTINCT NEW com.cwms.entities.AssessmentSheetPro(a.tdsDeductee, a.tds) from AssessmentSheetPro a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id")
	AssessmentSheetPro getTdsData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select DISTINCT a.invoiceNo,a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %h:%i'),a.igmTransId,"
			+ "a.igmNo,a.igmLineNo,DATE_FORMAT(a.igmDate,'%d/%m/%Y %h:%i'),a.blNo,DATE_FORMAT(a.blDate,'%d/%m/%Y %h:%i'),"
			+ "p.partyName "
			+ "from AssessmentSheetPro a "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.importerId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "a.igmTransId LIKE CONCAT('%',:val,'%') OR a.igmNo LIKE CONCAT('%',:val,'%') OR a.blNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='Import Container' order by a.assesmentDate desc")
	List<Object[]> searchInvoiceData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select DISTINCT a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.igmTransId,a.status,a.igmNo,a.igmLineNo,"
			+ "a.viaNo,a.igmDate,a.createdBy,a.blNo,a.blDate,a.profitcentreId,p.profitcentreDesc,a.sl,p1.partyName,a.sa,p2.partyName,"
			+ "pa1.address1,pa1.address2,pa1.address3,pa1.gstNo,"
			+ "a.insuranceValue,a.dutyValue,a.commodityDescription,a.commodityCode,a.importerId,a.importerName,a.impSrNo,"
			+ "a.cha,a.chaSrNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,"
			+ "a.sez,a.taxApplicable,a.onAccountOf,p5.partyName,a.accSrNo,pa4.gstNo,pa4.state,"
			+ "a.comments,a.othPartyId,p4.partyName,a.othSrNo,pa3.gstNo,pa3.state,a.billingParty,"
			+ "a.invoiceNo,a.creditType,a.invoiceCategory,a.isAncillary,a.invoiceDate,a.irn,a.receiptNo,a.intComments,"
			+ "a.noOfPackages,a.areaUsed,a.grossWeight,a.insuranceValue,a.noOf20ft,a.noOf40ft,i.startDate,i.gateOutDate,a.nocWeeks,"
			+ "i.invoiceUptoDate,i.serviceId,s.serviceShortDesc,i.actualNoOfPackages,p3.partyName,"
			+ "i.rate,i.taxPerc,a.lastAssesmentId,a.lastAssesmentDate,a.lastInvoiceNo,a.lastInvoiceDate,a.lastInvoiceUptoDate "
			+ "from AssessmentSheetPro a "
			+ "LEFT OUTER JOIN Party p1 ON a.companyId=p1.companyId and a.branchId=p1.branchId and a.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON a.companyId=p2.companyId and a.branchId=p2.branchId and a.sa=p2.partyId "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "LEFT OUTER JOIN PartyAddress pa1 ON a.companyId=pa1.companyId and a.branchId=pa1.branchId and a.importerId=pa1.partyId and a.impSrNo=CAST(pa1.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p3 ON a.companyId=p3.companyId and a.branchId=p3.branchId and a.cha=p3.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa2 ON a.companyId=pa2.companyId and a.branchId=pa2.branchId and a.cha=pa2.partyId and a.chaSrNo=CAST(pa2.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p4 ON a.companyId=p4.companyId and a.branchId=p4.branchId and a.othPartyId=p4.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa3 ON a.companyId=pa3.companyId and a.branchId=pa3.branchId and a.othPartyId=pa3.partyId and a.othSrNo=pa3.srNo "
			+ "LEFT OUTER JOIN Party p5 ON a.companyId=p5.companyId and a.branchId=p5.branchId and a.onAccountOf=p5.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa4 ON a.companyId=pa4.companyId and a.branchId=pa4.branchId and a.onAccountOf=pa4.partyId and a.accSrNo=CAST(pa4.srNo as INTEGER) "
			+ "LEFT OUTER JOIN CfinvsrvanxPro i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId and a.igmTransId=i.erpDocRefNo and a.containerNo=i.containerNo "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getAssessmentDataForNoc(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select DISTINCT a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.igmTransId,a.status,a.igmNo,a.igmLineNo,"
			+ "a.viaNo,a.igmDate,a.createdBy,a.blNo,a.blDate,a.profitcentreId,p.profitcentreDesc,a.sl,p1.partyName,a.sa,p2.partyName,"
			+ "pa1.address1,pa1.address2,pa1.address3,pa1.gstNo,"
			+ "a.insuranceValue,a.dutyValue,a.commodityDescription,a.commodityCode,a.importerId,a.importerName,a.impSrNo,"
			+ "a.cha,a.chaSrNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,"
			+ "a.sez,a.taxApplicable,a.onAccountOf,p5.partyName,a.accSrNo,pa4.gstNo,pa4.state,"
			+ "a.comments,a.othPartyId,p4.partyName,a.othSrNo,pa3.gstNo,pa3.state,a.billingParty,"
			+ "a.invoiceNo,a.creditType,a.invoiceCategory,a.isAncillary,a.invoiceDate,a.irn,a.receiptNo,a.intComments,"
			+ "a.noOfPackages,a.areaUsed,a.grossWeight,a.insuranceValue,a.noOf20ft,a.noOf40ft,a.nocFromDate,a.nocValidityDate,a.nocWeeks,"
			+ "i.invoiceUptoDate,i.serviceId,s.serviceShortDesc,i.actualNoOfPackages,p3.partyName,"
			+ "i.rate,i.taxPerc,a.lastAssesmentId,a.lastAssesmentDate,a.lastInvoiceNo,a.lastInvoiceDate,a.lastInvoiceUptoDate "
			+ "from AssessmentSheetPro a "
			+ "LEFT OUTER JOIN Party p1 ON a.companyId=p1.companyId and a.branchId=p1.branchId and a.sl=p1.partyId "
			+ "LEFT OUTER JOIN Party p2 ON a.companyId=p2.companyId and a.branchId=p2.branchId and a.sa=p2.partyId "
			+ "LEFT OUTER JOIN Profitcentre p ON a.companyId=p.companyId and a.branchId=p.branchId and a.profitcentreId=p.profitcentreId "
			+ "LEFT OUTER JOIN PartyAddress pa1 ON a.companyId=pa1.companyId and a.branchId=pa1.branchId and a.importerId=pa1.partyId and a.impSrNo=CAST(pa1.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p3 ON a.companyId=p3.companyId and a.branchId=p3.branchId and a.cha=p3.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa2 ON a.companyId=pa2.companyId and a.branchId=pa2.branchId and a.cha=pa2.partyId and a.chaSrNo=CAST(pa2.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p4 ON a.companyId=p4.companyId and a.branchId=p4.branchId and a.othPartyId=p4.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa3 ON a.companyId=pa3.companyId and a.branchId=pa3.branchId and a.othPartyId=pa3.partyId and a.othSrNo=pa3.srNo "
			+ "LEFT OUTER JOIN Party p5 ON a.companyId=p5.companyId and a.branchId=p5.branchId and a.onAccountOf=p5.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa4 ON a.companyId=pa4.companyId and a.branchId=pa4.branchId and a.onAccountOf=pa4.partyId and a.accSrNo=CAST(pa4.srNo as INTEGER) "
			+ "LEFT OUTER JOIN CfinvsrvanxPro i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId and a.igmTransId=i.erpDocRefNo and a.containerNo=i.containerNo "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getAssessmentDataForExbond(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select DISTINCT a.invoiceNo,a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %h:%i'),a.igmTransId,"
			+ "a.igmNo,DATE_FORMAT(a.igmDate,'%d/%m/%Y %h:%i'),p.partyName,a.sbNo "
			+ "from AssessmentSheetPro a "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.importerId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "a.igmTransId LIKE CONCAT('%',:val,'%') OR a.igmNo LIKE CONCAT('%',:val,'%') OR a.blNo LIKE CONCAT('%',:val,'%') OR a.sbNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='Bond Cargo' and a.invType='NOCBondInv' order by a.assesmentDate desc")
	List<Object[]> searchBondNocInvoiceData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	@Query(value="select DISTINCT a.invoiceNo,a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %h:%i'),a.igmTransId,"
			+ "a.igmNo,DATE_FORMAT(a.igmDate,'%d/%m/%Y %h:%i'),p.partyName,a.sbNo "
			+ "from AssessmentSheetPro a "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.importerId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "a.igmTransId LIKE CONCAT('%',:val,'%') OR a.igmNo LIKE CONCAT('%',:val,'%') OR a.blNo LIKE CONCAT('%',:val,'%') OR a.sbNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='Bond Cargo' and a.invType='ExBondInv' order by a.assesmentDate desc")
	List<Object[]> searchExbondInvoiceData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	

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
}
