package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="Cfinvsrvdtl")
@IdClass(CfinvsrvdtlId.class)
public class Cfinvsrvdtl {

	 @Id
	    @Column(name = "Tax_Id", length = 6)
	    private String taxId;

	    @Id
	    @Column(name = "Service_Id", length = 6)
	    private String serviceId;

	    @Id
	    @Column(name = "Party_Id", length = 6)
	    private String partyId;

	    @Id
	    @Column(name = "Invoice_No", length = 16)
	    private String invoiceNo;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Invoice_Date")
	    private Date invoiceDate;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitCentreId;

	    @Column(name = "WO_No", length = 10)
	    private String woNo;

	    @Column(name = "WO_Amnd_No", length = 3)
	    private String woAmndNo;

	    @Column(name = "ERP_Doc_Ref_No", length = 10)
	    private String erpDocRefNo;

	    @Column(name = "doc_ref_No", length = 25)
	    private String docRefNo;

	    @Column(name = "Foreign_Currency", length = 6)
	    private String foreignCurrency;

	    @Column(name = "Foreign_Amt", precision = 12, scale = 2)
	    private BigDecimal foreignAmt;

	    @Column(name = "Ex_Rate", precision = 12, scale = 3)
	    private BigDecimal exRate;

	    @Column(name = "Local_Amt_Foreign", precision = 12, scale = 2)
	    private BigDecimal localAmtForeign;

	    @Column(name = "Local_Amt", precision = 12, scale = 2)
	    private BigDecimal localAmt;

	    @Column(name = "Tax_Perc", precision = 7, scale = 3)
	    private BigDecimal taxPerc;

	    @Column(name = "Bill_Amt", precision = 12, scale = 2)
	    private BigDecimal billAmt;

	    @Column(name = "Invoice_Amt", precision = 12, scale = 2)
	    private BigDecimal invoiceAmt;

	    @Column(name = "Contractor", length = 6)
	    private String contractor;

	    @Column(name = "Ac_Code", length = 10)
	    private String acCode;

	    @Lob
	    @Column(name = "Comments")
	    private String comments;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Created_Date")
	    private Date createdDate;

	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Edited_Date")
	    private Date editedDate;

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Approved_Date")
	    private Date approvedDate;

	    @Column(name = "Tax_App", length = 1)
	    private String taxApp;

	    @Column(name = "Tax_Id_N", length = 10)
	    private String taxIdN;

	    @Column(name = "Tax_Perc_N", precision = 16, scale = 3)
	    private BigDecimal taxPercN;

	    @Column(name = "Tax_Amt", precision = 16, scale = 3)
	    private BigDecimal taxAmt;

	    @Column(name = "Ac_Code_N", length = 20)
	    private String acCodeN;
	    
	    @Column(name = "CreditNote_Amt", precision = 16, scale = 4, nullable = false, columnDefinition = "DECIMAL(16,4) DEFAULT 0.0000")
	    private BigDecimal creditNoteAmt = BigDecimal.ZERO;
	    	    

		public BigDecimal getCreditNoteAmt() {
			return creditNoteAmt;
		}

		public void setCreditNoteAmt(BigDecimal creditNoteAmt) {
			this.creditNoteAmt = creditNoteAmt;
		}

		public Cfinvsrvdtl() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Cfinvsrvdtl(String taxId, String serviceId, String partyId, String invoiceNo, String branchId,
				String companyId, Date invoiceDate, String profitCentreId, String woNo, String woAmndNo,
				String erpDocRefNo, String docRefNo, String foreignCurrency, BigDecimal foreignAmt, BigDecimal exRate,
				BigDecimal localAmtForeign, BigDecimal localAmt, BigDecimal taxPerc, BigDecimal billAmt,
				BigDecimal invoiceAmt, String contractor, String acCode, String comments, String status,
				String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String taxApp, String taxIdN, BigDecimal taxPercN, BigDecimal taxAmt,
				String acCodeN) {
			this.taxId = taxId;
			this.serviceId = serviceId;
			this.partyId = partyId;
			this.invoiceNo = invoiceNo;
			this.branchId = branchId;
			this.companyId = companyId;
			this.invoiceDate = invoiceDate;
			this.profitCentreId = profitCentreId;
			this.woNo = woNo;
			this.woAmndNo = woAmndNo;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.foreignCurrency = foreignCurrency;
			this.foreignAmt = foreignAmt;
			this.exRate = exRate;
			this.localAmtForeign = localAmtForeign;
			this.localAmt = localAmt;
			this.taxPerc = taxPerc;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.contractor = contractor;
			this.acCode = acCode;
			this.comments = comments;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.taxApp = taxApp;
			this.taxIdN = taxIdN;
			this.taxPercN = taxPercN;
			this.taxAmt = taxAmt;
			this.acCodeN = acCodeN;
		}

		public String getTaxId() {
			return taxId;
		}

		public void setTaxId(String taxId) {
			this.taxId = taxId;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public String getPartyId() {
			return partyId;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}

		public String getInvoiceNo() {
			return invoiceNo;
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

		public String getBranchId() {
			return branchId;
		}

		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public Date getInvoiceDate() {
			return invoiceDate;
		}

		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}

		public String getProfitCentreId() {
			return profitCentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
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

		public String getTaxApp() {
			return taxApp;
		}

		public void setTaxApp(String taxApp) {
			this.taxApp = taxApp;
		}

		public String getTaxIdN() {
			return taxIdN;
		}

		public void setTaxIdN(String taxIdN) {
			this.taxIdN = taxIdN;
		}

		public BigDecimal getTaxPercN() {
			return taxPercN;
		}

		public void setTaxPercN(BigDecimal taxPercN) {
			this.taxPercN = taxPercN;
		}

		public BigDecimal getTaxAmt() {
			return taxAmt;
		}

		public void setTaxAmt(BigDecimal taxAmt) {
			this.taxAmt = taxAmt;
		}

		public String getAcCodeN() {
			return acCodeN;
		}

		public void setAcCodeN(String acCodeN) {
			this.acCodeN = acCodeN;
		}
	    
	    
	    
	    
}
