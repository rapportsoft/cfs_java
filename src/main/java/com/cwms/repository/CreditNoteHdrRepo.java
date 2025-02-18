package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.CreditNoteHdr;
import com.cwms.entities.FinTrans;

public interface CreditNoteHdrRepo extends JpaRepository<CreditNoteHdr, String>{
	
	@Query(value = "SELECT c.oldInvoiceNo, "
            + "c.oldInvoiceAmt AS oldInvoiceAmt, "
            + "c.invoiceNo, "           
            + "c.localAmt, "
            + "(c.invoiceAmt - c.billAmt) AS taxAMT, c.invoiceAmt "
            + "FROM CreditNoteHdr c "           
            + "WHERE c.companyId = :companyId AND c.branchId = :branchId "
            + "AND c.status = 'A' "
            + "AND c.oldInvoiceNo = :oldInvoiceNo "
            + "AND (:invoiceNo IS NULL OR :invoiceNo = '' OR c.invoiceNo <> :invoiceNo) "
            + "ORDER BY c.invoiceDate DESC")
List<Object[]> getCreditNoteToSelectHistory(@Param("companyId") String companyId, 
                                     @Param("branchId") String branchId,
                                     @Param("oldInvoiceNo") String oldInvoiceNo, 
                                     @Param("invoiceNo") String invoiceNo);

	
	
	
	
	
	@Query(value = "SELECT c.invoiceNo, "
            + "DATE_FORMAT(c.invoiceDate, '%d %b %Y') AS invoiceDate, "
            + "c.docRefNo, c.oldInvoiceNo, "
            + "DATE_FORMAT(c.oldInvoiceDate, '%d %b %Y') AS oldInvoiceDate, " 
            + "p.partyName AS partyName, "
            + "c.status, c.profitcentreId "
            + "FROM CreditNoteHdr c "
            + "LEFT JOIN Party p ON c.companyId = p.companyId AND c.branchId = p.branchId "
            + "AND c.partyId = p.partyId AND p.status <> 'D' "
            + "WHERE c.companyId = :companyId AND c.branchId = :branchId "
            + "AND c.status = 'A' "
            + "AND (:searchValue IS NULL OR :searchValue = '' "
            + "OR c.invoiceNo LIKE CONCAT(:searchValue, '%') "
            + "OR c.oldInvoiceNo LIKE CONCAT(:searchValue, '%') "
            + "OR p.partyName LIKE CONCAT(:searchValue, '%')) "
            + "ORDER BY c.invoiceNo DESC")
List<Object[]> getCreditNoteToSelect(@Param("companyId") String companyId, 
                                     @Param("branchId") String branchId,
                                     @Param("searchValue") String searchValue);


	
	
	
	
	
	
	 @Query(value = "SELECT NEW com.cwms.entities.FinTrans(a.transId, a.oprInvoiceNo, a.oprAdjTransId, a.paymentMode, a.partyId, " +
             "a.accSrNo, " +
             "a.acCode, a.documentAmt, a.creditAmount, a.creditFlag, a.transDate, a.billingParty, a.credAmtAdj, a.lineId) " +
             "FROM FinTrans a " +           
             "WHERE a.companyId = :companyId AND a.branchId = :branchId AND a.oprInvoiceNo = :invoiceNo")            
	 FinTrans getFinTransDataOfInvoice(@Param("companyId") String companyId, 
                                @Param("branchId") String branchId, 
                                @Param("invoiceNo") String invoiceNo);
	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CreditNoteHdr SET comments = :comments, billAmt = :localAmt, invoiceAmt = :invoiceAmt, localAmt = :localAmt, editedBy = :user, editedDate = CURRENT_TIMESTAMP "
			  + "WHERE companyId = :companyId " +
		       "AND branchId = :branchId " +
		       "AND status <> 'D' " +
		       "AND invoiceNo = :invoiceNo " +
		       "AND oldInvoiceNo = :oldInvoiceNo")
	int updateCreditNoteHdr( @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("invoiceNo") String invoiceNo,
		    @Param("oldInvoiceNo") String oldInvoiceNo,
		    @Param("comments") String comments,
		    @Param("localAmt") BigDecimal localAmt,
		    @Param("invoiceAmt") BigDecimal invoiceAmt,
		    @Param("user") String user);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CreditNoteHdr SET comments = :comments, billAmt = :localAmt, invoiceAmt = :invoiceAmt, localAmt = :localAmt, invoiceNo = :newInvoiceNo, invoiceDate = :invoiceDate,editedBy = :user, editedDate = CURRENT_TIMESTAMP, approvedBy = :user, approvedDate = CURRENT_TIMESTAMP, status = 'A' "
			  + "WHERE companyId = :companyId " +
		       "AND branchId = :branchId " +
		       "AND status <> 'D' " +
		       "AND invoiceNo = :invoiceNo " +
		       "AND oldInvoiceNo = :oldInvoiceNo")
	int updateCreditNoteHdrProcess( @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("invoiceNo") String invoiceNo,
		    @Param("oldInvoiceNo") String oldInvoiceNo,
		    @Param("comments") String comments,
		    @Param("localAmt") BigDecimal localAmt,
		    @Param("invoiceAmt") BigDecimal invoiceAmt,
		    @Param("newInvoiceNo") String newInvoiceNo, @Param("invoiceDate") Date invoiceDate, @Param("user") String user
		    );
	
	
	
	@Query("SELECT DISTINCT a.invoiceNo " + "FROM AssessmentSheet a "
			+ "WHERE a.companyId = :companyId " 
			+ "AND a.branchId = :branchId "
//			+ "AND a.profitcentreId = :profitcentreId "
			+ "AND a.invoiceNo LIKE CONCAT(:searchValue, '%') "
			+ "AND a.status = 'A'")
	List<String> getInvoiceNoForCreditNote(@Param("companyId") String companyId, @Param("branchId") String branchId,
										   @Param("searchValue") String searchValue);

	
	
	
	@Query(value = "SELECT DISTINCT NEW com.cwms.entities.CreditNoteHdr(c.invoiceNo, c.invoiceDate, a.igmTransId, a.profitcentreId, b.profitcentreDesc, " + ""
		    + "a.igmNo, a.igmDate, a.igmLineNo, a.viaNo, a.blNo, a.blDate, a.importerName, a.commodityDescription, " + ""
		    + "a.noOfPackages, a.sa, e.partyName AS partyName1, a.sl, f.partyName AS partyName2, a.status, " + ""
		    + "i.User_Name AS userName1, j.User_Name AS userName2, a.insuranceValue, a.dutyValue, a.specialDelivery, " + ""
		    + "a.onlineStampDuty, a.agroProductStatus, e.facilitationCharge, e.facilitationUnit, e.facilitationRate, " + ""
		    + "a.importerId, a.billingParty, k.gstNo, a.discountAmt, a.addMovFlag, a.addMovementAmt, " + ""
		    + "a.partyId, l.partyName AS partyName3, a.taxApplicable, a.dpdTariff, a.discountStatus, " + ""
		    + "a.sez, a.impSrNo, a.chaSrNo, a.cha, p.partyName AS partyName4, " + ""
		    + "a.partySrNo, pAc.address1, pAc.address2, a.invoiceNo, " + ""
		    + "a.assesmentId, c.partyId, c.partyType, c.invoiceType, c.invoiceSubType, c.transType, " + ""
		    + "c.erpDocRefNo, c.docRefNo, c.igst, c.cgst, c.sgst, a.sbTransId, a.sbNo, " + ""
		    + "a.sbDate, a.exporterName, c.invoiceAmt, " + ""		   
		    + "a.payingParty, a.othPartyId, po.partyName, pf.partyName, c.billAmt) " + ""
		    + "FROM AssessmentSheet a " + ""
		    + "LEFT OUTER JOIN Cfinvsrv c " + ""
		    + "ON c.companyId = a.companyId " + ""
		    + "AND c.branchId = a.branchId " + ""
		    + "AND a.assesmentId = c.containerNo " + ""
		    + "AND a.invoiceNo = c.invoiceNo " + ""
		    + "AND a.profitcentreId = c.profitcentreId " + ""
		    + "LEFT OUTER JOIN Profitcentre b " + ""
		    + "ON b.companyId = a.companyId " + ""
		    + "AND b.profitcentreId = a.profitcentreId " + ""
		    + "LEFT OUTER JOIN Party e " + ""
		    + "ON e.companyId = a.companyId " + ""
		    + "AND e.partyId = a.sl " + ""
		    + "AND e.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party f " + ""
		    + "ON f.companyId = a.companyId " + ""
		    + "AND f.partyId = a.sa " + ""
		    + "AND f.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party k " + ""
		    + "ON k.companyId = a.companyId " + ""
		    + "AND k.partyId = a.importerId " + ""
		    + "AND k.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN User i " + ""
		    + "ON i.Company_Id = a.companyId " + ""
		    + "AND i.User_Id = a.createdBy " + ""
		    + "AND i.Branch_Id = a.branchId " + ""
		    + "LEFT OUTER JOIN User j " + ""
		    + "ON j.Company_Id = a.companyId " + ""
		    + "AND j.User_Id = a.approvedBy " + ""
		    + "AND j.Branch_Id = a.branchId " + ""
		    + "LEFT OUTER JOIN Party l " + ""
		    + "ON l.companyId = a.companyId " + ""
		    + "AND l.partyId = a.partyId " + ""
		    + "AND l.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party p " + ""
		    + "ON a.companyId = p.companyId " + ""
		    + "AND a.cha = p.partyId " + ""
		    + "AND p.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party po " + ""
		    + "ON a.companyId = po.companyId " + ""
		    + "AND a.othPartyId = po.partyId " + ""
		    + "AND po.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party pf " + ""
		    + "ON a.companyId = pf.companyId " + ""
		    + "AND a.onAccountOf = pf.partyId " + ""
		    + "AND pf.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN PartyAddress pAc " + ""
		    + "ON pAc.companyId = a.companyId AND pAc.partyId = a.partyId " + ""
		    + "AND CAST(pAc.srNo  AS INTEGER) = a.partySrNo AND a.branchId = pAc.branchId " + ""
		    + "WHERE a.companyId = :companyId " + ""
		    + "AND c.invoiceNo = :invoiceNo " + ""
		    + "AND a.branchId = :branchId " + ""
		    + "AND a.status = 'A' " + ""
		    + "AND c.status = 'A' " + ""
		    + "GROUP BY a.assesmentId " 
		    + "ORDER BY a.invoiceNo ")
		List<CreditNoteHdr> getDataForSeletdInvoiceNo(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("invoiceNo") String invoiceNo 
		    );
	
	
	@Query(value = "SELECT DISTINCT NEW com.cwms.entities.CreditNoteHdr(c.invoiceNo, c.invoiceDate, a.igmTransId, a.profitcentreId, b.profitcentreDesc, " + ""
		    + "a.igmNo, a.igmDate, a.igmLineNo, a.viaNo, a.blNo, a.blDate, a.importerName, a.commodityDescription, " + ""
		    + "a.noOfPackages, a.sa, e.partyName AS partyName1, a.sl, f.partyName AS partyName2, a.status, " + ""
		    + "i.User_Name AS userName1, j.User_Name AS userName2, a.insuranceValue, a.dutyValue, a.specialDelivery, " + ""
		    + "a.onlineStampDuty, a.agroProductStatus, e.facilitationCharge, e.facilitationUnit, e.facilitationRate, " + ""
		    + "a.importerId, a.billingParty, k.gstNo, a.discountAmt, a.addMovFlag, a.addMovementAmt, " + ""
		    + "a.partyId, l.partyName AS partyName3, a.taxApplicable, a.dpdTariff, a.discountStatus, " + ""
		    + "a.sez, a.impSrNo, a.chaSrNo, a.cha, p.partyName AS partyName4, " + ""
		    + "a.partySrNo, pAc.address1, pAc.address2, a.invoiceNo, " + ""
		    + "a.assesmentId, c.partyId, c.partyType, c.invoiceType, c.invoiceSubType, c.transType, " + ""
		    + "c.erpDocRefNo, c.docRefNo, c.igst, c.cgst, c.sgst, a.sbTransId, a.sbNo, " + ""
		    + "a.sbDate, a.exporterName, c.invoiceAmt, " + ""		   
		    + "a.payingParty, a.othPartyId, po.partyName, pf.partyName, c.billAmt, cr.comments, cr.invoiceNo, cr.invoiceDate) " + ""
		    + "FROM AssessmentSheet a " + ""
		    + "LEFT OUTER JOIN Cfinvsrv c " + ""
		    + "ON c.companyId = a.companyId " + ""
		    + "AND c.branchId = a.branchId " + ""
		    + "AND a.assesmentId = c.containerNo " + ""
		    + "AND a.invoiceNo = c.invoiceNo " + ""
		    + "AND a.profitcentreId = c.profitcentreId " + ""
		    + "LEFT OUTER JOIN Profitcentre b " + ""
		    + "ON b.companyId = a.companyId " + ""
		    + "AND b.profitcentreId = a.profitcentreId " + ""
		    + "LEFT OUTER JOIN Party e " + ""
		    + "ON e.companyId = a.companyId " + ""
		    + "AND e.partyId = a.sl " + ""
		    + "AND e.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party f " + ""
		    + "ON f.companyId = a.companyId " + ""
		    + "AND f.partyId = a.sa " + ""
		    + "AND f.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party k " + ""
		    + "ON k.companyId = a.companyId " + ""
		    + "AND k.partyId = a.importerId " + ""
		    + "AND k.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN User i " + ""
		    + "ON i.Company_Id = a.companyId " + ""
		    + "AND i.User_Id = a.createdBy " + ""
		    + "AND i.Branch_Id = a.branchId " + ""
		    + "LEFT OUTER JOIN User j " + ""
		    + "ON j.Company_Id = a.companyId " + ""
		    + "AND j.User_Id = a.approvedBy " + ""
		    + "AND j.Branch_Id = a.branchId " + ""
		    + "LEFT OUTER JOIN Party l " + ""
		    + "ON l.companyId = a.companyId " + ""
		    + "AND l.partyId = a.partyId " + ""
		    + "AND l.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party p " + ""
		    + "ON a.companyId = p.companyId " + ""
		    + "AND a.cha = p.partyId " + ""
		    + "AND p.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party po " + ""
		    + "ON a.companyId = po.companyId " + ""
		    + "AND a.othPartyId = po.partyId " + ""
		    + "AND po.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN Party pf " + ""
		    + "ON a.companyId = pf.companyId " + ""
		    + "AND a.onAccountOf = pf.partyId " + ""
		    + "AND pf.branchId = a.branchId " + ""
		    + "LEFT OUTER JOIN PartyAddress pAc " + ""
		    + "ON pAc.companyId = a.companyId AND pAc.partyId = a.partyId " + ""
		    + "AND CAST(pAc.srNo  AS INTEGER) = a.partySrNo AND a.branchId = pAc.branchId " + ""
		    + "LEFT JOIN CreditNoteHdr cr ON a.companyId = cr.companyId AND a.branchId = cr.branchId AND a.invoiceNo = cr.oldInvoiceNo AND cr.invoiceNo = :preInvoiceNo AND cr.status <> 'D' "
		    + "WHERE a.companyId = :companyId " + ""
		    + "AND c.invoiceNo = :invoiceNo " + ""
		    + "AND a.branchId = :branchId " + ""
		    + "AND a.status = 'A' " + ""
		    + "AND c.status = 'A' " + ""
		    + "GROUP BY a.assesmentId " 
		    + "ORDER BY a.invoiceNo ")
		List<CreditNoteHdr> getDataForSeletdInvoiceNoAfterSave(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,
		    @Param("invoiceNo") String invoiceNo ,
		    @Param("preInvoiceNo") String preInvoiceNo		    
		    );


	

}
