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
@Table(name="fintrans")
@IdClass(FinTransId.class)
public class FinTrans {

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
	    @Column(name = "Line_Id",precision = 3,scale = 0)
	    private BigDecimal lineId;

	    @Id
	    @Column(name = "Trans_Id", length = 20)
	    private String transId;

	    @Id
	    @Column(name = "OPR_INVOICE_NO", length = 16)
	    private String oprInvoiceNo;

	    @Column(name = "Trans_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date transDate;

	    @Column(name = "OPR_ADJ_Trans_Id", length = 20)
	    private String oprAdjTransId;

	    @Column(name = "Payment_Mode", length = 8)
	    private String paymentMode;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;

	    @Column(name = "Credit_Type", length = 1)
	    private String creditType;

	    @Column(name = "Party_Id", length = 8)
	    private String partyId;

	    @Column(name = "Acc_Sr_no")
	    private int accSrNo;

	    @Column(name = "Ac_Code", length = 10)
	    private String acCode;

	    @Column(name = "Cheque_No", length = 25)
	    private String chequeNo;

	    @Column(name = "Cheque_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date chequeDate;

	    @Column(name = "Bank_Name", length = 50)
	    private String bankName;

	    @Column(name = "Document_Amt", precision = 15, scale = 2)
	    private BigDecimal documentAmt;

	    @Column(name = "Narration", columnDefinition = "TEXT")
	    private String narration;

	    @Column(name = "Cleared_Amt", precision = 15, scale = 2)
	    private BigDecimal clearedAmt;

	    @Column(name = "Credit_Amount", precision = 17, scale = 3)
	    private BigDecimal creditAmount;

	    @Column(name = "Credit_Flag", length = 1)
	    private String creditFlag;

	    @Column(name = "Credit_Adjust_Flag", length = 1)
	    private String creditAdjustFlag;

	    @Column(name = "Bank_Recon_Flag", length = 1)
	    private String bankReconFlag;

	    @Column(name = "Bank_Recon_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date bankReconDate;

	    @Column(name = "Bank_Recon_Amt", precision = 3, scale = 2)
	    private BigDecimal bankReconAmt;

	    @Column(name = "TDS_Percentage", precision = 5, scale = 3)
	    private BigDecimal tdsPercentage;

	    @Column(name = "TDS_Bill_Amt", precision = 15, scale = 2)
	    private BigDecimal tdsBillAmt;

	    @Column(name = "TDS_Type", length = 6)
	    private String tdsType;

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

	    @Column(name = "Billing_Party", length = 10)
	    private String billingParty;

	    @Column(name = "Importer_Id", length = 6)
	    private String importerId;

	    @Column(name = "imp_Sr_no")
	    private int impSrNo;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Assesment_id", length = 20)
	    private String assesmentId;

	    @Column(name = "Record_Type", length = 5)
	    private String recordType;

	    @Column(name = "OLD_INVOICENO", length = 16)
	    private String oldInvoiceNo;

	    @Column(name = "CN_Adjust_Flag", length = 1)
	    private String cnAdjustFlag;

	    @Column(name = "Old_OPR_ADJ_Trans_Id", length = 20)
	    private String oldOprAdjTransId;

	    @Column(name = "Old_OPR_ADJ_Trans_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date oldOprAdjTransDate;

	    @Column(name = "CREDAMTADJ", precision = 16, scale = 3)
	    private BigDecimal credAmtAdj;

	    @Column(name = "is_On_Acc_Pay", length = 1)
	    private String isOnAccPay;

	    @Column(name = "Customer_Ledger_Flag", length = 1)
	    private String customerLedgerFlag;

		public FinTrans() {
			super();
			// TODO Auto-generated constructor stub
		}

		public FinTrans(String companyId, String branchId, String docType, String ledgerType, BigDecimal lineId,
				String transId, String oprInvoiceNo, Date transDate, String oprAdjTransId, String paymentMode,
				String profitcentreId, String creditType, String partyId, int accSrNo, String acCode, String chequeNo,
				Date chequeDate, String bankName, BigDecimal documentAmt, String narration, BigDecimal clearedAmt,
				BigDecimal creditAmount, String creditFlag, String creditAdjustFlag, String bankReconFlag,
				Date bankReconDate, BigDecimal bankReconAmt, BigDecimal tdsPercentage, BigDecimal tdsBillAmt,
				String tdsType, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String billingParty, String importerId, int impSrNo, String status,
				String assesmentId, String recordType, String oldInvoiceNo, String cnAdjustFlag,
				String oldOprAdjTransId, Date oldOprAdjTransDate, BigDecimal credAmtAdj, String isOnAccPay,
				String customerLedgerFlag) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.docType = docType;
			this.ledgerType = ledgerType;
			this.lineId = lineId;
			this.transId = transId;
			this.oprInvoiceNo = oprInvoiceNo;
			this.transDate = transDate;
			this.oprAdjTransId = oprAdjTransId;
			this.paymentMode = paymentMode;
			this.profitcentreId = profitcentreId;
			this.creditType = creditType;
			this.partyId = partyId;
			this.accSrNo = accSrNo;
			this.acCode = acCode;
			this.chequeNo = chequeNo;
			this.chequeDate = chequeDate;
			this.bankName = bankName;
			this.documentAmt = documentAmt;
			this.narration = narration;
			this.clearedAmt = clearedAmt;
			this.creditAmount = creditAmount;
			this.creditFlag = creditFlag;
			this.creditAdjustFlag = creditAdjustFlag;
			this.bankReconFlag = bankReconFlag;
			this.bankReconDate = bankReconDate;
			this.bankReconAmt = bankReconAmt;
			this.tdsPercentage = tdsPercentage;
			this.tdsBillAmt = tdsBillAmt;
			this.tdsType = tdsType;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.billingParty = billingParty;
			this.importerId = importerId;
			this.impSrNo = impSrNo;
			this.status = status;
			this.assesmentId = assesmentId;
			this.recordType = recordType;
			this.oldInvoiceNo = oldInvoiceNo;
			this.cnAdjustFlag = cnAdjustFlag;
			this.oldOprAdjTransId = oldOprAdjTransId;
			this.oldOprAdjTransDate = oldOprAdjTransDate;
			this.credAmtAdj = credAmtAdj;
			this.isOnAccPay = isOnAccPay;
			this.customerLedgerFlag = customerLedgerFlag;
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

		public Date getTransDate() {
			return transDate;
		}

		public void setTransDate(Date transDate) {
			this.transDate = transDate;
		}

		public String getOprAdjTransId() {
			return oprAdjTransId;
		}

		public void setOprAdjTransId(String oprAdjTransId) {
			this.oprAdjTransId = oprAdjTransId;
		}

		public String getPaymentMode() {
			return paymentMode;
		}

		public void setPaymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
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

		public String getAcCode() {
			return acCode;
		}

		public void setAcCode(String acCode) {
			this.acCode = acCode;
		}

		public String getChequeNo() {
			return chequeNo;
		}

		public void setChequeNo(String chequeNo) {
			this.chequeNo = chequeNo;
		}

		public Date getChequeDate() {
			return chequeDate;
		}

		public void setChequeDate(Date chequeDate) {
			this.chequeDate = chequeDate;
		}

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public BigDecimal getDocumentAmt() {
			return documentAmt;
		}

		public void setDocumentAmt(BigDecimal documentAmt) {
			this.documentAmt = documentAmt;
		}

		public String getNarration() {
			return narration;
		}

		public void setNarration(String narration) {
			this.narration = narration;
		}

		public BigDecimal getClearedAmt() {
			return clearedAmt;
		}

		public void setClearedAmt(BigDecimal clearedAmt) {
			this.clearedAmt = clearedAmt;
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

		public String getBankReconFlag() {
			return bankReconFlag;
		}

		public void setBankReconFlag(String bankReconFlag) {
			this.bankReconFlag = bankReconFlag;
		}

		public Date getBankReconDate() {
			return bankReconDate;
		}

		public void setBankReconDate(Date bankReconDate) {
			this.bankReconDate = bankReconDate;
		}

		public BigDecimal getBankReconAmt() {
			return bankReconAmt;
		}

		public void setBankReconAmt(BigDecimal bankReconAmt) {
			this.bankReconAmt = bankReconAmt;
		}

		public BigDecimal getTdsPercentage() {
			return tdsPercentage;
		}

		public void setTdsPercentage(BigDecimal tdsPercentage) {
			this.tdsPercentage = tdsPercentage;
		}

		public BigDecimal getTdsBillAmt() {
			return tdsBillAmt;
		}

		public void setTdsBillAmt(BigDecimal tdsBillAmt) {
			this.tdsBillAmt = tdsBillAmt;
		}

		public String getTdsType() {
			return tdsType;
		}

		public void setTdsType(String tdsType) {
			this.tdsType = tdsType;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getAssesmentId() {
			return assesmentId;
		}

		public void setAssesmentId(String assesmentId) {
			this.assesmentId = assesmentId;
		}

		public String getRecordType() {
			return recordType;
		}

		public void setRecordType(String recordType) {
			this.recordType = recordType;
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

		public BigDecimal getCredAmtAdj() {
			return credAmtAdj;
		}

		public void setCredAmtAdj(BigDecimal credAmtAdj) {
			this.credAmtAdj = credAmtAdj;
		}

		public String getIsOnAccPay() {
			return isOnAccPay;
		}

		public void setIsOnAccPay(String isOnAccPay) {
			this.isOnAccPay = isOnAccPay;
		}

		public String getCustomerLedgerFlag() {
			return customerLedgerFlag;
		}

		public void setCustomerLedgerFlag(String customerLedgerFlag) {
			this.customerLedgerFlag = customerLedgerFlag;
		}
	    
	    
	    
}
