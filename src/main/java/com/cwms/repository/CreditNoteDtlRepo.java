package com.cwms.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwms.entities.CreditNoteDtl;

public interface CreditNoteDtlRepo extends JpaRepository<CreditNoteDtl, String>{	
	
	
	@Query("SELECT COALESCE(e.localAmt, 0) " +
		       "FROM CreditNoteDtl e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.serviceId = :serviceId " +
		       "AND e.status <> 'D' " +
		       "AND e.invoiceNo = :invoiceNo " +
		       "AND e.oldInvoiceNo = :oldInvoiceNo")
		BigDecimal findLocalAmtByInvoiceNoAndServiceId(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("invoiceNo") String invoiceNo,
		    @Param("oldInvoiceNo") String oldInvoiceNo,
		    @Param("serviceId") String serviceId);

	
	
	
	@Transactional
	@Modifying
	@Query("UPDATE Cfinvsrvdtl SET creditNoteAmt = COALESCE(creditNoteAmt, 0) + :creditNoteAmt "
	      + "WHERE companyId = :companyId " 
	      + "AND branchId = :branchId " 
	      + "AND serviceId = :serviceId " 
	      + "AND status <> 'D' " 
	      + "AND invoiceNo = :invoiceNo")
	int updateInvoiceDtl(@Param("companyId") String companyId,
	                     @Param("branchId") String branchId,		    
	                     @Param("invoiceNo") String invoiceNo,
	                     @Param("serviceId") String serviceId,
	                     @Param("creditNoteAmt") BigDecimal creditNoteAmt);

	
	
	
//	@Transactional
//	@Modifying
//	@Query("UPDATE Cfinvsrvdtl SET creditNoteAmt = :creditNoteAmt "
//			  + "WHERE companyId = :companyId " +
//		       "AND branchId = :branchId " +
//		       "AND serviceId = :serviceId AND status <> 'D' " +
//		       "AND invoiceNo = :invoiceNo")
//	int updateInvoiceDtl( @Param("companyId") String companyId,
//		    @Param("branchId") String branchId,		    
//		    @Param("invoiceNo") String invoiceNo,
//		    @Param("serviceId") String serviceId,
//		    @Param("creditNoteAmt") BigDecimal creditNoteAmt);
//	
	@Transactional
	@Modifying
	@Query("UPDATE CreditNoteDtl SET localAmt = :localAmt, billAmt = :localAmt, invoiceAmt = :localAmt, taxAmt = :taxAmt, creditNoteGstAmt = :taxAmt, comments = :comments, editedBy = :user, editedDate = CURRENT_TIMESTAMP "
			  + "WHERE companyId = :companyId " +
		       "AND branchId = :branchId " +
		       "AND serviceId = :serviceId AND status <> 'D' " +
		       "AND invoiceNo = :invoiceNo " +
		       "AND oldInvoiceNo = :oldInvoiceNo")
	int updateCreditNoteDtl( @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("invoiceNo") String invoiceNo,
		    @Param("oldInvoiceNo") String oldInvoiceNo,
		    @Param("serviceId") String serviceId,
		    @Param("localAmt") BigDecimal localAmt,
		    @Param("taxAmt") BigDecimal taxAmt,
		    @Param("comments") String comments,
		    @Param("user") String user
		    );
	
	
	@Transactional
	@Modifying
	@Query("UPDATE CreditNoteDtl SET localAmt = :localAmt, billAmt = :localAmt, invoiceAmt = :localAmt, taxAmt = :taxAmt, creditNoteGstAmt = :taxAmt, invoiceNo = :newInvoiceNo, invoiceDate = :invoiceDate, comments = :comments, editedBy = :user, editedDate = CURRENT_TIMESTAMP, approvedBy = :user, approvedDate = CURRENT_TIMESTAMP, status = 'A' "
			  + "WHERE companyId = :companyId " +
		       "AND branchId = :branchId " +
		       "AND serviceId = :serviceId AND status <> 'D' " +
		       "AND invoiceNo = :invoiceNo " +
		       "AND oldInvoiceNo = :oldInvoiceNo")
	int updateCreditNoteDtlProcess( @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("invoiceNo") String invoiceNo,
		    @Param("oldInvoiceNo") String oldInvoiceNo,
		    @Param("serviceId") String serviceId,
		    @Param("localAmt") BigDecimal localAmt,
		    @Param("taxAmt") BigDecimal taxAmt, @Param("newInvoiceNo") String newInvoiceNo, @Param("invoiceDate") Date invoiceDate,@Param("comments") String comments, @Param("user") String user);
	
	
	
	
	
	@Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
		       "FROM CreditNoteDtl e " +
		       "WHERE e.companyId = :companyId " +
		       "AND e.branchId = :branchId " +
		       "AND e.serviceId = :serviceId AND e.status <> 'D' " +
		       "AND e.invoiceNo = :invoiceNo " +
		       "AND e.oldInvoiceNo = :oldInvoiceNo")	      
		boolean existsByInvoiceNoAndServiceId(
		    @Param("companyId") String companyId,
		    @Param("branchId") String branchId,		    
		    @Param("invoiceNo") String invoiceNo,
		    @Param("oldInvoiceNo") String oldInvoiceNo,
		    @Param("serviceId") String serviceId);
	

	    @Query(value = "SELECT NEW com.cwms.entities.CreditNoteDtl(a.serviceId, b.serviceShortDesc, a.taxIdN, c.taxDesc, a.taxPercN, " +
	                   "a.localAmt, " +
	                   "a.acCode, a.acCodeN, a.creditNoteAmt) " +
	                   "FROM Cfinvsrvdtl a " +
	                   "LEFT OUTER JOIN Services b ON b.companyId = a.companyId AND b.serviceId = a.serviceId " +
	                   "LEFT OUTER JOIN Tax c ON c.companyId = a.companyId AND c.taxId = a.taxIdN " +
	                   "LEFT OUTER JOIN TaxDtl e ON e.companyId = c.companyId AND c.taxId = e.taxId AND e.status = 'A' " +
	                   "WHERE a.companyId = :companyId AND a.branchId = :branchId AND a.invoiceNo = :invoiceNo " +
	                   "AND a.invoiceAmt <> 0.00 " +
	                   "ORDER BY a.serviceId ")
	    List<CreditNoteDtl> getServiceDetailsOfInvoice(@Param("companyId") String companyId, 
	                                      @Param("branchId") String branchId, 
	                                      @Param("invoiceNo") String invoiceNo);
	    
	    
	    
	    @Query(value = "SELECT NEW com.cwms.entities.CreditNoteDtl(a.serviceId, b.serviceShortDesc, a.taxIdN, c.taxDesc, a.taxPercN, " +
                "cl.localAmt, " +
                "a.acCode, a.acCodeN, cl.creditNoteAmt, a.taxAmt, a.invoiceNo, a.oldInvoiceNo, a.localAmt, a.creditNoteGstAmt, a.status) " +
                "FROM CreditNoteDtl a " +
                "LEFT OUTER JOIN Services b ON b.companyId = a.companyId AND b.serviceId = a.serviceId " +
                "LEFT OUTER JOIN Tax c ON c.companyId = a.companyId AND c.taxId = a.taxIdN " +
                "LEFT OUTER JOIN TaxDtl e ON e.companyId = c.companyId AND c.taxId = e.taxId AND e.status = 'A' " +
                "LEFT OUTER JOIN Cfinvsrvdtl cl ON a.companyId = cl.companyId AND a.branchId = cl.branchId AND a.serviceId = cl.serviceId AND a.oldInvoiceNo = cl.invoiceNo AND cl.status = 'A' " +
                "WHERE a.companyId = :companyId AND a.branchId = :branchId AND a.oldInvoiceNo = :invoiceNo AND a.invoiceNo = :preInvoiceNo " +
                "AND a.invoiceAmt <> 0.00 " +
                "ORDER BY a.serviceId ")
 List<CreditNoteDtl> getServiceDetailsOfInvoiceAfterSave(@Param("companyId") String companyId, 
                                   @Param("branchId") String branchId, 
                                   @Param("invoiceNo") String invoiceNo,
                                   @Param("preInvoiceNo") String preInvoiceNo);
	    

	
	    @Query(value = "SELECT NEW com.cwms.entities.CreditNoteDtl(a.serviceId, b.serviceShortDesc, a.taxIdN, c.taxDesc, a.taxPercN, " +
                "a.localAmt, " +
                "a.acCode, a.acCodeN, a.creditNoteAmt) " +
                "FROM Cfinvsrvdtl a " +
                "LEFT OUTER JOIN Services b ON b.companyId = a.companyId AND b.serviceId = a.serviceId " +
                "LEFT OUTER JOIN Tax c ON c.companyId = a.companyId AND c.taxId = a.taxIdN " +
                "LEFT OUTER JOIN TaxDtl e ON e.companyId = c.companyId AND c.taxId = e.taxId AND e.status = 'A' " +
                "WHERE a.companyId = :companyId AND a.branchId = :branchId AND a.invoiceNo = :invoiceNo " +
                "AND a.invoiceAmt <> 0.00 AND a.serviceId NOT IN (:serviceIds) " + // <-- Space before ORDER BY
                "ORDER BY a.serviceId")
List<CreditNoteDtl> getServiceDetailsOfInvoice(@Param("companyId") String companyId, 
                                   @Param("branchId") String branchId, 
                                   @Param("invoiceNo") String invoiceNo, 
                                   @Param("serviceIds") List<String> serviceIds);

}
