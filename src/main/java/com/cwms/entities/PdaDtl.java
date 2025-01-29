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
@Table(name="pdadtl")
@IdClass(PdaDtlId.class)
public class PdaDtl {

	@Id
    @Column(name = "Company_Id", length = 6)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6)
    private String branchId;

    @Id
    @Column(name = "Party_Id", length = 8)
    private String partyId;

    @Id
    @Column(name = "Trans_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;

    @Id
    @Column(name = "SR_No", precision = 8, scale = 0)
    private BigDecimal srNo;

    @Column(name = "Receipt_NO", length = 16)
    private String receiptNo;

    @Column(name = "Invoice_NO", length = 16)
    private String invoiceNo;

    @Column(name = "ADJ_Trans_Id", length = 20)
    private String adjTransId;

    @Column(name = "Advance_Amount", precision = 16, scale = 3)
    private BigDecimal advanceAmount;

    @Column(name = "Adjust_Amount", precision = 16, scale = 3)
    private BigDecimal adjustAmount;

    @Column(name = "Credit_Amount", precision = 17, scale = 3)
    private BigDecimal creditAmount;

    @Column(name = "Credit_Flag", length = 1)
    private String creditFlag;

    @Column(name = "Credit_Adjust_Flag", length = 1)
    private String creditAdjustFlag;

    @Column(name = "Reverse_Advance", precision = 16, scale = 3)
    private BigDecimal reverseAdvance;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Status", length = 1)
    private String status;

    @Column(name = "Old_OPR_ADJ_Trans_Id", length = 20)
    private String oldOprAdjTransId;

    @Column(name = "Old_OPR_ADJ_Trans_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oldOprAdjTransDate;

    @Column(name = "Old_Invoice_NO", length = 16)
    private String oldInvoiceNo;

    @Column(name = "CN_Adjust_Flag", length = 1)
    private String cnAdjustFlag;

	public PdaDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PdaDtl(String companyId, String branchId, String partyId, Date transDate, BigDecimal srNo, String receiptNo,
			String invoiceNo, String adjTransId, BigDecimal advanceAmount, BigDecimal adjustAmount,
			BigDecimal creditAmount, String creditFlag, String creditAdjustFlag, BigDecimal reverseAdvance,
			String createdBy, Date createdDate, String status, String oldOprAdjTransId, Date oldOprAdjTransDate,
			String oldInvoiceNo, String cnAdjustFlag) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.transDate = transDate;
		this.srNo = srNo;
		this.receiptNo = receiptNo;
		this.invoiceNo = invoiceNo;
		this.adjTransId = adjTransId;
		this.advanceAmount = advanceAmount;
		this.adjustAmount = adjustAmount;
		this.creditAmount = creditAmount;
		this.creditFlag = creditFlag;
		this.creditAdjustFlag = creditAdjustFlag;
		this.reverseAdvance = reverseAdvance;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.status = status;
		this.oldOprAdjTransId = oldOprAdjTransId;
		this.oldOprAdjTransDate = oldOprAdjTransDate;
		this.oldInvoiceNo = oldInvoiceNo;
		this.cnAdjustFlag = cnAdjustFlag;
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

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public BigDecimal getSrNo() {
		return srNo;
	}

	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getAdjTransId() {
		return adjTransId;
	}

	public void setAdjTransId(String adjTransId) {
		this.adjTransId = adjTransId;
	}

	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}

	public String getCreditAdjustFlag() {
		return creditAdjustFlag;
	}

	public void setCreditAdjustFlag(String creditAdjustFlag) {
		this.creditAdjustFlag = creditAdjustFlag;
	}

	public BigDecimal getReverseAdvance() {
		return reverseAdvance;
	}

	public void setReverseAdvance(BigDecimal reverseAdvance) {
		this.reverseAdvance = reverseAdvance;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOldOprAdjTransId() {
		return oldOprAdjTransId;
	}

	public void setOldOprAdjTransId(String oldOprAdjTransId) {
		this.oldOprAdjTransId = oldOprAdjTransId;
	}

	public Date getOldOprAdjTransDate() {
		return oldOprAdjTransDate;
	}

	public void setOldOprAdjTransDate(Date oldOprAdjTransDate) {
		this.oldOprAdjTransDate = oldOprAdjTransDate;
	}

	public String getOldInvoiceNo() {
		return oldInvoiceNo;
	}

	public void setOldInvoiceNo(String oldInvoiceNo) {
		this.oldInvoiceNo = oldInvoiceNo;
	}

	public String getCnAdjustFlag() {
		return cnAdjustFlag;
	}

	public void setCnAdjustFlag(String cnAdjustFlag) {
		this.cnAdjustFlag = cnAdjustFlag;
	}
    
    
    
}
