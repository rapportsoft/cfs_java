package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AssessmentSheet;

public interface AssessmentSheetRepo extends JpaRepository<AssessmentSheet, String> {

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
			+ "from AssessmentSheet a "
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
			+ "LEFT OUTER JOIN Cfinvsrvanx i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId and a.igmTransId=i.erpDocRefNo and a.containerNo=i.containerNo "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getAssessmentData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select a from AssessmentSheet a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id")
	List<AssessmentSheet> getDataByAssessmentId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from AssessmentSheet a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id "
			+ "and (a.invoiceNo = '' OR a.invoiceNo is null)")
	List<AssessmentSheet> getDataByAssessmentId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	@Query(value="select DISTINCT a.invoiceNo,a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %h:%i'),a.igmTransId,"
			+ "a.igmNo,a.igmLineNo,DATE_FORMAT(a.igmDate,'%d/%m/%Y %h:%i'),a.blNo,DATE_FORMAT(a.blDate,'%d/%m/%Y %h:%i'),"
			+ "p.partyName "
			+ "from AssessmentSheet a "
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
			+ "from AssessmentSheet a "
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
			+ "LEFT OUTER JOIN Cfinvsrvanx i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId and a.igmTransId=i.erpDocRefNo and a.containerNo=i.containerNo "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getAssessmentDataForNoc(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	
	
	
	@Query(value="select DISTINCT a.invoiceNo,a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %h:%i'),a.igmTransId,"
			+ "a.igmNo,DATE_FORMAT(a.igmDate,'%d/%m/%Y %h:%i'),p.partyName,a.sbNo "
			+ "from AssessmentSheet a "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.importerId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "a.igmTransId LIKE CONCAT('%',:val,'%') OR a.igmNo LIKE CONCAT('%',:val,'%') OR a.blNo LIKE CONCAT('%',:val,'%') OR a.sbNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='Bond Cargo' and a.invType='NOCBondInv' order by a.assesmentDate desc")
	List<Object[]> searchBondNocInvoiceData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	
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
			+ "from AssessmentSheet a "
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
			+ "LEFT OUTER JOIN Cfinvsrvanx i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId and a.igmTransId=i.erpDocRefNo and a.containerNo=i.containerNo "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getAssessmentDataForExbond(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	@Query(value="select DISTINCT a.invoiceNo,a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %h:%i'),a.igmTransId,"
			+ "a.igmNo,DATE_FORMAT(a.igmDate,'%d/%m/%Y %h:%i'),p.partyName,a.sbNo "
			+ "from AssessmentSheet a "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.importerId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "a.igmTransId LIKE CONCAT('%',:val,'%') OR a.igmNo LIKE CONCAT('%',:val,'%') OR a.blNo LIKE CONCAT('%',:val,'%') OR a.sbNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='Bond Cargo' and a.invType='ExBondInv' order by a.assesmentDate desc")
	List<Object[]> searchExbondInvoiceData(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
	
	
	@Query(value="select DISTINCT a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.status,"
			+ "a.createdBy,a.profitcentreId,pa1.address1,pa1.address2,pa1.address3,pa1.gstNo,"
			+ "a.importerId,a.importerName,a.impSrNo,a.cha,p3.partyName,a.chaSrNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,"
			+ "a.sez,a.taxApplicable,a.onAccountOf,p5.partyName,a.accSrNo,pa4.gstNo,pa4.state,"
			+ "a.comments,a.othPartyId,p4.partyName,a.othSrNo,pa3.gstNo,pa3.state,a.billingParty,"
			+ "a.invoiceNo,a.creditType,a.invType,a.invoiceDate,a.irn,a.receiptNo,a.intComments,"
			+ "i.serviceId,s.serviceShortDesc,i.acCode,i.taxId,i.taxPerc,i.serviceUnit,i.executionUnit,i.serviceUnit1,"
			+ "i.executionUnit1,i.rate,i.actualNoOfPackages "
			+ "from AssessmentSheet a "
			+ "LEFT OUTER JOIN PartyAddress pa1 ON a.companyId=pa1.companyId and a.branchId=pa1.branchId and a.importerId=pa1.partyId and a.impSrNo=CAST(pa1.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p3 ON a.companyId=p3.companyId and a.branchId=p3.branchId and a.cha=p3.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa2 ON a.companyId=pa2.companyId and a.branchId=pa2.branchId and a.cha=pa2.partyId and a.chaSrNo=CAST(pa2.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p4 ON a.companyId=p4.companyId and a.branchId=p4.branchId and a.othPartyId=p4.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa3 ON a.companyId=pa3.companyId and a.branchId=pa3.branchId and a.othPartyId=pa3.partyId and a.othSrNo=pa3.srNo "
			+ "LEFT OUTER JOIN Party p5 ON a.companyId=p5.companyId and a.branchId=p5.branchId and a.onAccountOf=p5.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa4 ON a.companyId=pa4.companyId and a.branchId=pa4.branchId and a.onAccountOf=pa4.partyId and a.accSrNo=CAST(pa4.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Cfinvsrvanx i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId "
			+ "LEFT OUTER JOIN Services s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getMISCAssessmentData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	@Query(value="select DISTINCT a.invoiceNo,DATE_FORMAT(a.invoiceDate,'%d/%m/%Y %H:%i'),a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %H:%i'),a.transType,"
			+ "p.partyName,pr.profitcentreDesc,a.receiptNo "
			+ "from AssessmentSheet a "
			+ "LEFT OUTER JOIN Profitcentre pr ON a.companyId=pr.companyId and a.branchId=pr.branchId and a.profitcentreId=pr.profitcentreId "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.partyId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "pr.profitcentreDesc LIKE CONCAT('%',:val,'%') OR p.partyName LIKE CONCAT('%',:val,'%') OR a.receiptNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='MISC' order by a.assesmentDate desc")
	List<Object[]> searchMISCInvoiceData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
}
