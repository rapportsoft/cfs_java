package com.cwms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cwms.entities.AssessmentSheet;
import com.cwms.entities.AssessmentSheetAP;

public interface AssessmentSheetAPRepo extends JpaRepository<AssessmentSheetAP, String> {

	@Query(value="select a from AssessmentSheetAP a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id")
	List<AssessmentSheetAP> getDataByAssessmentId(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select DISTINCT a.assesmentId,a.assesmentLineNo,a.transType,a.assesmentDate,a.status,"
			+ "a.createdBy,a.profitcentreId,pa1.address1,pa1.address2,pa1.address3,pa1.gstNo,"
			+ "a.importerId,a.importerName,a.impSrNo,a.cha,p3.partyName,a.chaSrNo,pa2.address1,pa2.address2,pa2.address3,pa2.gstNo,"
			+ "a.sez,a.taxApplicable,a.onAccountOf,p5.partyName,a.accSrNo,pa4.gstNo,pa4.state,"
			+ "a.comments,a.othPartyId,p4.partyName,a.othSrNo,pa3.gstNo,pa3.state,a.billingParty,"
			+ "a.invoiceNo,a.creditType,a.invType,a.invoiceDate,a.irn,a.receiptNo,a.intComments,"
			+ "i.serviceId,s.serviceShortDesc,i.acCode,i.taxId,i.taxPerc,i.serviceUnit,i.executionUnit,i.serviceUnit1,"
			+ "i.executionUnit1,i.rate,i.actualNoOfPackages,a.lastInvoiceNo,a.lastInvoiceDate "
			+ "from AssessmentSheetAP a "
			+ "LEFT OUTER JOIN PartyAddress pa1 ON a.companyId=pa1.companyId and a.branchId=pa1.branchId and a.importerId=pa1.partyId and a.impSrNo=CAST(pa1.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p3 ON a.companyId=p3.companyId and a.branchId=p3.branchId and a.cha=p3.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa2 ON a.companyId=pa2.companyId and a.branchId=pa2.branchId and a.cha=pa2.partyId and a.chaSrNo=CAST(pa2.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Party p4 ON a.companyId=p4.companyId and a.branchId=p4.branchId and a.othPartyId=p4.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa3 ON a.companyId=pa3.companyId and a.branchId=pa3.branchId and a.othPartyId=pa3.partyId and a.othSrNo=pa3.srNo "
			+ "LEFT OUTER JOIN Party p5 ON a.companyId=p5.companyId and a.branchId=p5.branchId and a.onAccountOf=p5.partyId "
			+ "LEFT OUTER JOIN PartyAddress pa4 ON a.companyId=pa4.companyId and a.branchId=pa4.branchId and a.onAccountOf=pa4.partyId and a.accSrNo=CAST(pa4.srNo as INTEGER) "
			+ "LEFT OUTER JOIN Cfinvsrvanxap i ON a.companyId=i.companyId and a.branchId=i.branchId and a.assesmentId=i.processTransId "
			+ "LEFT OUTER JOIN APServices s ON i.companyId=s.companyId and i.branchId=s.branchId and i.serviceId=s.serviceId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id order by i.serviceId")
	List<Object[]> getMISCAssessmentData(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	@Query(value="select a from AssessmentSheetAP a where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and a.assesmentId=:id "
			+ "and (a.invoiceNo = '' OR a.invoiceNo is null)")
	List<AssessmentSheetAP> getDataByAssessmentId1(@Param("cid") String cid,@Param("bid") String bid,@Param("id") String id);
	
	
	
	
	
	@Query(value="select DISTINCT a.invoiceNo,DATE_FORMAT(a.invoiceDate,'%d/%m/%Y %H:%i'),a.assesmentId,DATE_FORMAT(a.assesmentDate,'%d/%m/%Y %H:%i'),a.transType,"
			+ "p.partyName,pr.profitcentreDesc,a.receiptNo "
			+ "from AssessmentSheetAP a "
			+ "LEFT OUTER JOIN Profitcentre pr ON a.companyId=pr.companyId and a.branchId=pr.branchId and a.profitcentreId=pr.profitcentreId "
			+ "LEFT OUTER JOIN Party p ON a.companyId=p.companyId and a.branchId=p.branchId and a.partyId=p.partyId "
			+ "where a.companyId=:cid and a.branchId=:bid and a.status = 'A' and (:val is null OR :val = '' OR "
			+ "a.invoiceNo LIKE CONCAT('%',:val,'%') OR a.assesmentId LIKE CONCAT('%',:val,'%') OR "
			+ "pr.profitcentreDesc LIKE CONCAT('%',:val,'%') OR p.partyName LIKE CONCAT('%',:val,'%') OR a.receiptNo LIKE CONCAT('%',:val,'%')) "
			+ "and (a.invoiceNo is not null AND a.invoiceNo != '') and a.transType='VEN' order by a.assesmentDate desc")
	List<Object[]> searchMISCInvoiceData1(@Param("cid") String cid,@Param("bid") String bid,@Param("val") String val);
	
}
