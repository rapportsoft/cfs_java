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

@Entity
@Table(name = "fintransinvap")
@IdClass(FinTransId.class)
public class FinTransInvAP {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Doc_Type", length = 2)
	private String docType;

	@Id
	@Column(name = "Ledger_Type", length = 2)
	private String ledgerType;

	@Id
	@Column(name = "Line_Id", precision = 3, scale = 0)
	private BigDecimal lineId;

	@Id
	@Column(name = "Trans_Id", length = 20)
	private String transId;

	@Id
	@Column(name = "OPR_INVOICE_NO", length = 16)
	private String oprInvoiceNo;

	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;

	@Column(name = "Credit_Type", length = 1)
	private String creditType;

	@Column(name = "Party_Id", length = 8)
	private String partyId;

	@Column(name = "Acc_Sr_no")
	private int accSrNo;

	@Column(name = "Billing_Party", length = 10)
	private String billingParty;

	@Column(name = "Importer_Id", length = 6)
	private String importerId;

	@Column(name = "imp_Sr_no")
	private int impSrNo;

	@Column(name = "Document_Amt", precision = 15, scale = 2)
	private BigDecimal documentAmt;

	@Column(name = "Receipt_Amt", precision = 16, scale = 3)
	private BigDecimal receiptAmt;

	@Column(name = "Narration", columnDefinition = "TEXT")
	private String narration;

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

	@Column(name = "Invoice_Bal_Amt", precision = 16, scale = 3)
	private BigDecimal invoiceBalAmt;

	public FinTransInvAP() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FinTransInvAP(String companyId, String branchId, String docType, String ledgerType, BigDecimal lineId,
			String transId, String oprInvoiceNo, String profitcentreId, String creditType, String partyId, int accSrNo,
			String billingParty, String importerId, int impSrNo, BigDecimal documentAmt, BigDecimal receiptAmt,
			String narration, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, BigDecimal invoiceBalAmt) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.docType = docType;
		this.ledgerType = ledgerType;
		this.lineId = lineId;
		this.transId = transId;
		this.oprInvoiceNo = oprInvoiceNo;
		this.profitcentreId = profitcentreId;
		this.creditType = creditType;
		this.partyId = partyId;
		this.accSrNo = accSrNo;
		this.billingParty = billingParty;
		this.importerId = importerId;
		this.impSrNo = impSrNo;
		this.documentAmt = documentAmt;
		this.receiptAmt = receiptAmt;
		this.narration = narration;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.invoiceBalAmt = invoiceBalAmt;
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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getLedgerType() {
		return ledgerType;
	}

	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}

	public BigDecimal getLineId() {
		return lineId;
	}

	public void setLineId(BigDecimal lineId) {
		this.lineId = lineId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getOprInvoiceNo() {
		return oprInvoiceNo;
	}

	public void setOprInvoiceNo(String oprInvoiceNo) {
		this.oprInvoiceNo = oprInvoiceNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public int getAccSrNo() {
		return accSrNo;
	}

	public void setAccSrNo(int accSrNo) {
		this.accSrNo = accSrNo;
	}

	public String getBillingParty() {
		return billingParty;
	}

	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public int getImpSrNo() {
		return impSrNo;
	}

	public void setImpSrNo(int impSrNo) {
		this.impSrNo = impSrNo;
	}

	public BigDecimal getDocumentAmt() {
		return documentAmt;
	}

	public void setDocumentAmt(BigDecimal documentAmt) {
		this.documentAmt = documentAmt;
	}

	public BigDecimal getReceiptAmt() {
		return receiptAmt;
	}

	public void setReceiptAmt(BigDecimal receiptAmt) {
		this.receiptAmt = receiptAmt;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
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

	public BigDecimal getInvoiceBalAmt() {
		return invoiceBalAmt;
	}

	public void setInvoiceBalAmt(BigDecimal invoiceBalAmt) {
		this.invoiceBalAmt = invoiceBalAmt;
	}

}

