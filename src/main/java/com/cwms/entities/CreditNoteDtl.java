package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="cfinvsrvcndtl")
@IdClass(CreditNoteDtlId.class)
public class CreditNoteDtl {
	
	  	@Id
	    @Column(name = "Company_Id", length = 6, nullable = false)
	    private String companyId;
	    
	    @Id
	    @Column(name = "Branch_Id", length = 6, nullable = false)
	    private String branchId;
	    
	    @Id
	    @Column(name = "Fin_Year", length = 4, nullable = false)
	    private String finYear;
	    
	    @Id
	    @Column(name = "invoice_no", length = 20, nullable = false)
	    private String invoiceNo;
	    
	    @Id
	    @Column(name = "Party_Id", length = 6, nullable = false)
	    private String partyId;
	    
	    @Id
	    @Column(name = "Service_Id", length = 6, nullable = false)
	    private String serviceId;
	    
	    @Id
	    @Column(name = "Tax_Id", length = 6, nullable = false)
	    private String taxId;
	    
	    @Column(name = "FIN_PERIOD", length = 2)
	    private String finPeriod;
	    
	    @Column(name = "Tax_Id_N", length = 6)
	    private String taxIdN;
	    
	    @Column(name = "Invoice_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date invoiceDate;
	    
	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;
	    
	    @Column(name = "WO_No", length = 10)
	    private String woNo;
	    
	    @Column(name = "WO_Amnd_No", length = 3)
	    private String woAmndNo;
	    
	    @Column(name = "ERP_Doc_Ref_No", length = 10)
	    private String erpDocRefNo;
	    
	    @Column(name = "Doc_Ref_No", length = 25)
	    private String docRefNo;
	    
	    @Column(name = "Doc_Ref_Date")
	    @Temporal(TemporalType.DATE)
	    private Date docRefDate;
	    
	    @Column(name = "Foreign_Currency", length = 6)
	    private String foreignCurrency;
	    
	    @Column(name = "Foreign_Amt", precision = 16, scale = 3)
	    private BigDecimal foreignAmt;
	    
	    @Column(name = "Ex_Rate", precision = 16, scale = 3)
	    private BigDecimal exRate;
	    
	    @Column(name = "Local_Amt_Foreign", precision = 16, scale = 3)
	    private BigDecimal localAmtForeign;
	    
	    @Column(name = "Local_Amt", precision = 16, scale = 3)
	    private BigDecimal localAmt;
	    
	    @Column(name = "Tax_Perc", precision = 16, scale = 3)
	    private BigDecimal taxPerc;
	    
	    @Column(name = "Tax_Perc_N", precision = 16, scale = 3)
	    private BigDecimal taxPercN;
	    
	    @Column(name = "Bill_Amt", precision = 16, scale = 3)
	    private BigDecimal billAmt;
	    
	    @Column(name = "Invoice_Amt", precision = 16, scale = 3)
	    private BigDecimal invoiceAmt;
	    
	    @Column(name = "Tax_Amt", precision = 16, scale = 3)
	    private BigDecimal taxAmt;
	    
	    @Column(name = "Contractor", length = 6)
	    private String contractor;
	    
	    @Column(name = "Ac_Code", length = 10)
	    private String acCode;
	    
	    @Column(name = "Ac_Code_N", length = 10)
	    private String acCodeN;
	    
	    @Column(name = "Comments", length = 250)
	    private String comments;
	    
	    @Column(name = "Status", length = 1)
	    private String status;
	    
	    @Column(name = "Created_By", length = 10)
	    private String createdBy;
	    
	    @Column(name = "Created_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdDate;
	    
	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;
	    
	    @Column(name = "Edited_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date editedDate;
	    
	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;
	    
	    @Column(name = "Approved_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date approvedDate;
	    
	    @Column(name = "old_Invoice_Amt", precision = 16, scale = 3)
	    private BigDecimal oldInvoiceAmt;
	    
	    @Column(name = "Old_Tax_Perc", precision = 16, scale = 3)
	    private BigDecimal oldTaxPerc;
	    
	    @Column(name = "Old_Tax_Id", length = 6)
	    private String oldTaxId;
	    
	    @Column(name = "Credit_Note_GST_Amt", precision = 16, scale = 3)
	    private BigDecimal creditNoteGstAmt;
	    
	    @Column(name = "old_invoice_no", length = 20)
	    private String oldInvoiceNo;
	    
	    
	    transient private String serviceName;
	    
	    @Transient
	    private BigDecimal creditNoteAmt;
	    
	    @Transient
	    private BigDecimal totalAmt;
	    
	    
	    @Transient
	    private BigDecimal alreadySavedCreditAmt;
	    	    
		public BigDecimal getTotalAmt() {
			return totalAmt;
		}

		public void setTotalAmt(BigDecimal totalAmt) {
			this.totalAmt = totalAmt;
		}

		public BigDecimal getCreditNoteAmt() {
			return creditNoteAmt;
		}

		public void setCreditNoteAmt(BigDecimal creditNoteAmt) {
			this.creditNoteAmt = creditNoteAmt;
		}

		public CreditNoteDtl() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CreditNoteDtl(String companyId, String branchId, String finYear, String invoiceNo, String partyId,
				String serviceId, String taxId, String finPeriod, String taxIdN, Date invoiceDate,
				String profitcentreId, String woNo, String woAmndNo, String erpDocRefNo, String docRefNo,
				Date docRefDate, String foreignCurrency, BigDecimal foreignAmt, BigDecimal exRate,
				BigDecimal localAmtForeign, BigDecimal localAmt, BigDecimal taxPerc, BigDecimal taxPercN,
				BigDecimal billAmt, BigDecimal invoiceAmt, BigDecimal taxAmt, String contractor, String acCode,
				String acCodeN, String comments, String status, String createdBy, Date createdDate, String editedBy,
				Date editedDate, String approvedBy, Date approvedDate, BigDecimal oldInvoiceAmt, BigDecimal oldTaxPerc,
				String oldTaxId, BigDecimal creditNoteGstAmt, String oldInvoiceNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.invoiceNo = invoiceNo;
			this.partyId = partyId;
			this.serviceId = serviceId;
			this.taxId = taxId;
			this.finPeriod = finPeriod;
			this.taxIdN = taxIdN;
			this.invoiceDate = invoiceDate;
			this.profitcentreId = profitcentreId;
			this.woNo = woNo;
			this.woAmndNo = woAmndNo;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.docRefDate = docRefDate;
			this.foreignCurrency = foreignCurrency;
			this.foreignAmt = foreignAmt;
			this.exRate = exRate;
			this.localAmtForeign = localAmtForeign;
			this.localAmt = localAmt;
			this.taxPerc = taxPerc;
			this.taxPercN = taxPercN;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.taxAmt = taxAmt;
			this.contractor = contractor;
			this.acCode = acCode;
			this.acCodeN = acCodeN;
			this.comments = comments;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.oldInvoiceAmt = oldInvoiceAmt;
			this.oldTaxPerc = oldTaxPerc;
			this.oldTaxId = oldTaxId;
			this.creditNoteGstAmt = creditNoteGstAmt;
			this.oldInvoiceNo = oldInvoiceNo;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getBranchId() {
			return branchId;
		}

		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}

		public String getFinYear() {
			return finYear;
		}

		public void setFinYear(String finYear) {
			this.finYear = finYear;
		}

		public String getInvoiceNo() {
			return invoiceNo;
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

		public String getPartyId() {
			return partyId;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public String getTaxId() {
			return taxId;
		}

		public void setTaxId(String taxId) {
			this.taxId = taxId;
		}

		public String getFinPeriod() {
			return finPeriod;
		}

		public void setFinPeriod(String finPeriod) {
			this.finPeriod = finPeriod;
		}

		public String getTaxIdN() {
			return taxIdN;
		}

		public void setTaxIdN(String taxIdN) {
			this.taxIdN = taxIdN;
		}

		public Date getInvoiceDate() {
			return invoiceDate;
		}

		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getWoNo() {
			return woNo;
		}

		public void setWoNo(String woNo) {
			this.woNo = woNo;
		}

		public String getWoAmndNo() {
			return woAmndNo;
		}

		public void setWoAmndNo(String woAmndNo) {
			this.woAmndNo = woAmndNo;
		}

		public String getErpDocRefNo() {
			return erpDocRefNo;
		}

		public void setErpDocRefNo(String erpDocRefNo) {
			this.erpDocRefNo = erpDocRefNo;
		}

		public String getDocRefNo() {
			return docRefNo;
		}

		public void setDocRefNo(String docRefNo) {
			this.docRefNo = docRefNo;
		}

		public Date getDocRefDate() {
			return docRefDate;
		}

		public void setDocRefDate(Date docRefDate) {
			this.docRefDate = docRefDate;
		}

		public String getForeignCurrency() {
			return foreignCurrency;
		}

		public void setForeignCurrency(String foreignCurrency) {
			this.foreignCurrency = foreignCurrency;
		}

		public BigDecimal getForeignAmt() {
			return foreignAmt;
		}

		public void setForeignAmt(BigDecimal foreignAmt) {
			this.foreignAmt = foreignAmt;
		}

		public BigDecimal getExRate() {
			return exRate;
		}

		public void setExRate(BigDecimal exRate) {
			this.exRate = exRate;
		}

		public BigDecimal getLocalAmtForeign() {
			return localAmtForeign;
		}

		public void setLocalAmtForeign(BigDecimal localAmtForeign) {
			this.localAmtForeign = localAmtForeign;
		}

		public BigDecimal getLocalAmt() {
			return localAmt;
		}

		public void setLocalAmt(BigDecimal localAmt) {
			this.localAmt = localAmt;
		}

		public BigDecimal getTaxPerc() {
			return taxPerc;
		}

		public void setTaxPerc(BigDecimal taxPerc) {
			this.taxPerc = taxPerc;
		}

		public BigDecimal getTaxPercN() {
			return taxPercN;
		}

		public void setTaxPercN(BigDecimal taxPercN) {
			this.taxPercN = taxPercN;
		}

		public BigDecimal getBillAmt() {
			return billAmt;
		}

		public void setBillAmt(BigDecimal billAmt) {
			this.billAmt = billAmt;
		}

		public BigDecimal getInvoiceAmt() {
			return invoiceAmt;
		}

		public void setInvoiceAmt(BigDecimal invoiceAmt) {
			this.invoiceAmt = invoiceAmt;
		}

		public BigDecimal getTaxAmt() {
			return taxAmt;
		}

		public void setTaxAmt(BigDecimal taxAmt) {
			this.taxAmt = taxAmt;
		}

		public String getContractor() {
			return contractor;
		}

		public void setContractor(String contractor) {
			this.contractor = contractor;
		}

		public String getAcCode() {
			return acCode;
		}

		public void setAcCode(String acCode) {
			this.acCode = acCode;
		}

		public String getAcCodeN() {
			return acCodeN;
		}

		public void setAcCodeN(String acCodeN) {
			this.acCodeN = acCodeN;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getEditedBy() {
			return editedBy;
		}

		public void setEditedBy(String editedBy) {
			this.editedBy = editedBy;
		}

		public Date getEditedDate() {
			return editedDate;
		}

		public void setEditedDate(Date editedDate) {
			this.editedDate = editedDate;
		}

		public String getApprovedBy() {
			return approvedBy;
		}

		public void setApprovedBy(String approvedBy) {
			this.approvedBy = approvedBy;
		}

		public Date getApprovedDate() {
			return approvedDate;
		}

		public void setApprovedDate(Date approvedDate) {
			this.approvedDate = approvedDate;
		}

		public BigDecimal getOldInvoiceAmt() {
			return oldInvoiceAmt;
		}

		public void setOldInvoiceAmt(BigDecimal oldInvoiceAmt) {
			this.oldInvoiceAmt = oldInvoiceAmt;
		}

		public BigDecimal getOldTaxPerc() {
			return oldTaxPerc;
		}

		public void setOldTaxPerc(BigDecimal oldTaxPerc) {
			this.oldTaxPerc = oldTaxPerc;
		}

		public String getOldTaxId() {
			return oldTaxId;
		}

		public void setOldTaxId(String oldTaxId) {
			this.oldTaxId = oldTaxId;
		}

		public BigDecimal getCreditNoteGstAmt() {
			return creditNoteGstAmt;
		}

		public void setCreditNoteGstAmt(BigDecimal creditNoteGstAmt) {
			this.creditNoteGstAmt = creditNoteGstAmt;
		}

		public String getOldInvoiceNo() {
			return oldInvoiceNo;
		}

		public void setOldInvoiceNo(String oldInvoiceNo) {
			this.oldInvoiceNo = oldInvoiceNo;
		}

		

		
		
		@Override
		public String toString() {
			return "CreditNoteDtl [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
					+ ", invoiceNo=" + invoiceNo + ", partyId=" + partyId + ", serviceId=" + serviceId + ", taxId="
					+ taxId + ", finPeriod=" + finPeriod + ", taxIdN=" + taxIdN + ", invoiceDate=" + invoiceDate
					+ ", profitcentreId=" + profitcentreId + ", woNo=" + woNo + ", woAmndNo=" + woAmndNo
					+ ", erpDocRefNo=" + erpDocRefNo + ", docRefNo=" + docRefNo + ", docRefDate=" + docRefDate
					+ ", foreignCurrency=" + foreignCurrency + ", foreignAmt=" + foreignAmt + ", exRate=" + exRate
					+ ", localAmtForeign=" + localAmtForeign + ", localAmt=" + localAmt + ", taxPerc=" + taxPerc
					+ ", taxPercN=" + taxPercN + ", billAmt=" + billAmt + ", invoiceAmt=" + invoiceAmt + ", taxAmt="
					+ taxAmt + ", contractor=" + contractor + ", acCode=" + acCode + ", acCodeN=" + acCodeN
					+ ", comments=" + comments + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
					+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy="
					+ approvedBy + ", approvedDate=" + approvedDate + ", oldInvoiceAmt=" + oldInvoiceAmt
					+ ", oldTaxPerc=" + oldTaxPerc + ", oldTaxId=" + oldTaxId + ", creditNoteGstAmt=" + creditNoteGstAmt
					+ ", oldInvoiceNo=" + oldInvoiceNo + ", creditNoteAmt=" + creditNoteAmt + ", totalAmt=" + totalAmt
					+ "]";
		}





		transient private String taxDesc;
				
		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public String getTaxDesc() {
			return taxDesc;
		}

		public void setTaxDesc(String taxDesc) {
			this.taxDesc = taxDesc;
		}
		
		public BigDecimal getAlreadySavedCreditAmt() {
			return alreadySavedCreditAmt;
		}

		public void setAlreadySavedCreditAmt(BigDecimal alreadySavedCreditAmt) {
			this.alreadySavedCreditAmt = alreadySavedCreditAmt;
		}

		public CreditNoteDtl(String serviceId, String serviceName, String taxIdN, String taxDesc, BigDecimal oldTaxPerc,
				BigDecimal oldInvoiceAmt, String acCode, String acCodeN, BigDecimal creditNoteAmt) {
			super();
			this.serviceId = serviceId;
			this.taxIdN = taxIdN;
			this.oldInvoiceAmt = oldInvoiceAmt;
			this.oldTaxPerc = oldTaxPerc;
			this.oldTaxId = taxIdN;
			this.taxId = taxIdN;
			this.taxPerc = oldTaxPerc;
			this.taxPercN = oldTaxPerc;
			this.serviceName = serviceName;
			this.taxDesc = taxDesc;		
			this.acCode = acCode;
			this.acCodeN = acCodeN;
			this.creditNoteAmt = creditNoteAmt;
			this.localAmt = BigDecimal.ZERO;

		}
		
		public CreditNoteDtl(String serviceId, String serviceName, String taxIdN, String taxDesc, BigDecimal oldTaxPerc,
				BigDecimal oldInvoiceAmt, String acCode, String acCodeN, BigDecimal creditNoteAmt, BigDecimal taxAmt, String invoiceNo, String oldInvoiceNo,
				BigDecimal localAmt, BigDecimal creditNoteGstAmt, String status) {
			super();
			this.serviceId = serviceId;
			this.taxIdN = taxIdN;
			this.oldInvoiceAmt = oldInvoiceAmt;
			this.oldTaxPerc = oldTaxPerc;
			this.oldTaxId = taxIdN;
			this.taxId = taxIdN;
			this.taxPerc = oldTaxPerc;
			this.taxPercN = oldTaxPerc;
			this.serviceName = serviceName;
			this.taxDesc = taxDesc;		
			this.acCode = acCode;
			this.acCodeN = acCodeN;
			this.creditNoteAmt = creditNoteAmt;
			this.invoiceNo = invoiceNo;
			this.oldInvoiceNo = oldInvoiceNo;
			this.localAmt = localAmt;
			this.creditNoteGstAmt = creditNoteGstAmt;
			this.status = status;
			this.alreadySavedCreditAmt = localAmt;
		}
		
		
		
		
		
}
