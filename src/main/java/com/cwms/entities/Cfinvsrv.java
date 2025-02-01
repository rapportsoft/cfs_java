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
@Table(name="cfinvsrv")
@IdClass(CfinvsrvId.class)
public class Cfinvsrv {

	    @Id
	    @Column(name = "Invoice_No", length = 16)
	    private String invoiceNo;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Party_Id", length = 6)
	    private String partyId;

	    @Id
	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;

	    @Id
	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Acc_Sr_no")
	    private int accSrNo;

	    @Column(name = "Invoice_Date")
	    @Temporal(TemporalType.DATE)
	    private Date invoiceDate;

	    @Column(name = "WO_No", length = 10)
	    private String woNo;

	    @Column(name = "WO_Amnd_No", length = 3)
	    private String woAmndNo;

	    @Column(name = "Party_Type", length = 6)
	    private String partyType;

	    @Column(name = "Term", precision = 3, scale = 0)
	    private BigDecimal term;

	    @Column(name = "Invoice_Due_Date")
	    @Temporal(TemporalType.DATE)
	    private Date invoiceDueDate;

	    @Column(name = "Invoice_Type", length = 6)
	    private String invoiceType;

	    @Column(name = "Trans_Type", length = 10)
	    private String transType;

	    @Column(name = "Invoice_Sub_Type", length = 10)
	    private String invoiceSubType;

	    @Column(name = "ERP_Doc_Ref_No", length = 10)
	    private String erpDocRefNo;

	    @Column(name = "doc_ref_No", length = 25)
	    private String docRefNo;
	    
	    @Column(name = "IGM_Line_No", length = 10)
	    private String igmLineNo;

	    @Column(name = "EX_Bonding_Id", length = 10)
	    private String exBondingId;

	    @Column(name = "In_Bonding_Id", length = 10)
	    private String inBondingId;

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

	    @Column(name = "Bill_Amt", precision = 12, scale = 2)
	    private BigDecimal billAmt;

	    @Column(name = "Invoice_Amt", precision = 12, scale = 2)
	    private BigDecimal invoiceAmt;

	    @Column(name = "Ac_Code", length = 10)
	    private String acCode;

	    @Column(name = "Fin_Trans_Id", length = 10)
	    private String finTransId;

	    @Column(name = "Fin_Trans_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date finTransDate;

	    @Column(name = "DebitNote_Id", length = 16)
	    private String debitNoteId;

	    @Column(name = "DN_Receipt_Trans_Id", length = 10)
	    private String dnReceiptTransId;

	    @Column(name = "RECEIPT_TRANS_ID", length = 30)
	    private String receiptTransId;

	    @Column(name = "Receipt_Trans_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date receiptTransDate;

	    @Column(name = "Receipt_Amt", precision = 16, scale = 3)
	    private BigDecimal receiptAmt;

	    @Column(name = "Post_Flag", length = 1)
	    private String postFlag;

	    @Column(name = "Post_By", length = 10)
	    private String postBy;

	    @Column(name = "Post_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date postDate;

	    @Column(name = "LockDown", length = 1)
	    private String lockDown;

	    @Column(name = "Checked_Flag", length = 1)
	    private String checkedFlag;

	    @Column(name = "Checked_by", length = 10)
	    private String checkedBy;

	    @Column(name = "Checked_date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date checkedDate;

	    @Column(name = "Comments")
	    private String comments;

	    @Column(name = "Special_Comments", length = 250)
	    private String specialComments;

	    @Column(name = "inv_ref_no", length = 20)
	    private String invRefNo;

	    @Column(name = "Periodic", length = 1)
	    private String periodic;

	    @Column(name = "Credit_Type", length = 1)
	    private String creditType;

	    @Column(name = "Mail_Flag", length = 1)
	    private String mailFlag;

	    @Column(name = "Billing_Party", length = 3)
	    private String billingParty;

	    @Column(name = "Importer_Id", length = 7)
	    private String importerId;

	    @Column(name = "imp_Sr_no")
	    private int impSrNo;

	    @Column(name = "IGST", length = 1)
	    private String igst;

	    @Column(name = "CGST", length = 1)
	    private String cgst;

	    @Column(name = "SGST", length = 1)
	    private String sgst;

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
	    
	    @Column(name = "Post_Invocie_Resp", length = 2)
	    private String postInvoiceResp;

	    @Column(name = "PostInvocie_Rep", length = 2)
	    private String postInvoiceRep;

	    @Column(name = "IRN", length = 200)
	    private String irn;

	    @Lob
	    @Column(name = "SignQRCode")
	    private String signQRCode;

	    @Lob
	    @Column(name = "SignInvoice")
	    private String signInvoice;

	    @Column(name = "AckNo", length = 30)
	    private String ackNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "AckDt")
	    private Date ackDt;

	    @Lob
	    @Column(name = "QRImage")
	    private byte[] qrImage;

	    @Column(name = "Can_PostInvocie_Rep", length = 2)
	    private String canPostInvoiceRep;

	    @Column(name = "Can_Post_Invocie_Resp", length = 2)
	    private String canPostInvoiceResp;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CanDt")
	    private Date canDt;

	    @Column(name = "Invoice_path", length = 250)
	    private String invoicePath;

	    @Column(name = "SignQRCodePath", length = 90)
	    private String signQRCodePath;

	    @Column(name = "IsBos", length = 1)
	    private String isBos;

	    @Column(name = "Bond_Type", length = 5)
	    private String bondType;

	    @Column(name = "Other_Deduction", precision = 16, scale = 3)
	    private BigDecimal otherDeduction;

	    @Column(name = "Customer_Ledger_Flag", length = 1)
	    private String customerLedgerFlag;

	    @Column(name = "Payable_Party", length = 6)
	    private String payableParty;
	    
		public Cfinvsrv() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		


		public Cfinvsrv(String invoiceNo, String branchId, String companyId, String partyId, String profitcentreId,
				String containerNo, int accSrNo, Date invoiceDate, String woNo, String woAmndNo, String partyType,
				BigDecimal term, Date invoiceDueDate, String invoiceType, String transType, String invoiceSubType,
				String erpDocRefNo, String docRefNo, String igmLineNo, String exBondingId, String inBondingId,
				String foreignCurrency, BigDecimal foreignAmt, BigDecimal exRate, BigDecimal localAmtForeign,
				BigDecimal localAmt, BigDecimal billAmt, BigDecimal invoiceAmt, String acCode, String finTransId,
				Date finTransDate, String debitNoteId, String dnReceiptTransId, String receiptTransId,
				Date receiptTransDate, BigDecimal receiptAmt, String postFlag, String postBy, Date postDate,
				String lockDown, String checkedFlag, String checkedBy, Date checkedDate, String comments,
				String specialComments, String invRefNo, String periodic, String creditType, String mailFlag,
				String billingParty, String importerId, int impSrNo, String igst, String cgst, String sgst,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String postInvoiceResp, String postInvoiceRep, String irn, String signQRCode,
				String signInvoice, String ackNo, Date ackDt, byte[] qrImage, String canPostInvoiceRep,
				String canPostInvoiceResp, Date canDt, String invoicePath, String signQRCodePath, String isBos,
				String bondType, BigDecimal otherDeduction, String customerLedgerFlag, String payableParty) {
			this.invoiceNo = invoiceNo;
			this.branchId = branchId;
			this.companyId = companyId;
			this.partyId = partyId;
			this.profitcentreId = profitcentreId;
			this.containerNo = containerNo;
			this.accSrNo = accSrNo;
			this.invoiceDate = invoiceDate;
			this.woNo = woNo;
			this.woAmndNo = woAmndNo;
			this.partyType = partyType;
			this.term = term;
			this.invoiceDueDate = invoiceDueDate;
			this.invoiceType = invoiceType;
			this.transType = transType;
			this.invoiceSubType = invoiceSubType;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.igmLineNo = igmLineNo;
			this.exBondingId = exBondingId;
			this.inBondingId = inBondingId;
			this.foreignCurrency = foreignCurrency;
			this.foreignAmt = foreignAmt;
			this.exRate = exRate;
			this.localAmtForeign = localAmtForeign;
			this.localAmt = localAmt;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.acCode = acCode;
			this.finTransId = finTransId;
			this.finTransDate = finTransDate;
			this.debitNoteId = debitNoteId;
			this.dnReceiptTransId = dnReceiptTransId;
			this.receiptTransId = receiptTransId;
			this.receiptTransDate = receiptTransDate;
			this.receiptAmt = receiptAmt;
			this.postFlag = postFlag;
			this.postBy = postBy;
			this.postDate = postDate;
			this.lockDown = lockDown;
			this.checkedFlag = checkedFlag;
			this.checkedBy = checkedBy;
			this.checkedDate = checkedDate;
			this.comments = comments;
			this.specialComments = specialComments;
			this.invRefNo = invRefNo;
			this.periodic = periodic;
			this.creditType = creditType;
			this.mailFlag = mailFlag;
			this.billingParty = billingParty;
			this.importerId = importerId;
			this.impSrNo = impSrNo;
			this.igst = igst;
			this.cgst = cgst;
			this.sgst = sgst;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.postInvoiceResp = postInvoiceResp;
			this.postInvoiceRep = postInvoiceRep;
			this.irn = irn;
			this.signQRCode = signQRCode;
			this.signInvoice = signInvoice;
			this.ackNo = ackNo;
			this.ackDt = ackDt;
			this.qrImage = qrImage;
			this.canPostInvoiceRep = canPostInvoiceRep;
			this.canPostInvoiceResp = canPostInvoiceResp;
			this.canDt = canDt;
			this.invoicePath = invoicePath;
			this.signQRCodePath = signQRCodePath;
			this.isBos = isBos;
			this.bondType = bondType;
			this.otherDeduction = otherDeduction;
			this.customerLedgerFlag = customerLedgerFlag;
			this.payableParty = payableParty;
		}






		public String getPayableParty() {
			return payableParty;
		}






		public void setPayableParty(String payableParty) {
			this.payableParty = payableParty;
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

		public String getPartyId() {
			return partyId;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getContainerNo() {
			return containerNo;
		}

		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
		}

		public int getAccSrNo() {
			return accSrNo;
		}

		public void setAccSrNo(int accSrNo) {
			this.accSrNo = accSrNo;
		}

		public Date getInvoiceDate() {
			return invoiceDate;
		}

		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
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

		public String getPartyType() {
			return partyType;
		}

		public void setPartyType(String partyType) {
			this.partyType = partyType;
		}

		public BigDecimal getTerm() {
			return term;
		}

		public void setTerm(BigDecimal term) {
			this.term = term;
		}

		public Date getInvoiceDueDate() {
			return invoiceDueDate;
		}

		public void setInvoiceDueDate(Date invoiceDueDate) {
			this.invoiceDueDate = invoiceDueDate;
		}

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
		}

		public String getInvoiceSubType() {
			return invoiceSubType;
		}

		public void setInvoiceSubType(String invoiceSubType) {
			this.invoiceSubType = invoiceSubType;
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

		public String getExBondingId() {
			return exBondingId;
		}

		public void setExBondingId(String exBondingId) {
			this.exBondingId = exBondingId;
		}

		public String getInBondingId() {
			return inBondingId;
		}

		public void setInBondingId(String inBondingId) {
			this.inBondingId = inBondingId;
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

		public String getAcCode() {
			return acCode;
		}

		public void setAcCode(String acCode) {
			this.acCode = acCode;
		}

		public String getFinTransId() {
			return finTransId;
		}

		public void setFinTransId(String finTransId) {
			this.finTransId = finTransId;
		}

		public Date getFinTransDate() {
			return finTransDate;
		}

		public void setFinTransDate(Date finTransDate) {
			this.finTransDate = finTransDate;
		}

		public String getDebitNoteId() {
			return debitNoteId;
		}

		public void setDebitNoteId(String debitNoteId) {
			this.debitNoteId = debitNoteId;
		}

		public String getDnReceiptTransId() {
			return dnReceiptTransId;
		}

		public void setDnReceiptTransId(String dnReceiptTransId) {
			this.dnReceiptTransId = dnReceiptTransId;
		}

		public String getReceiptTransId() {
			return receiptTransId;
		}

		public void setReceiptTransId(String receiptTransId) {
			this.receiptTransId = receiptTransId;
		}

		public Date getReceiptTransDate() {
			return receiptTransDate;
		}

		public void setReceiptTransDate(Date receiptTransDate) {
			this.receiptTransDate = receiptTransDate;
		}

		public BigDecimal getReceiptAmt() {
			return receiptAmt;
		}

		public void setReceiptAmt(BigDecimal receiptAmt) {
			this.receiptAmt = receiptAmt;
		}

		public String getPostFlag() {
			return postFlag;
		}

		public void setPostFlag(String postFlag) {
			this.postFlag = postFlag;
		}

		public String getPostBy() {
			return postBy;
		}

		public void setPostBy(String postBy) {
			this.postBy = postBy;
		}

		public Date getPostDate() {
			return postDate;
		}

		public void setPostDate(Date postDate) {
			this.postDate = postDate;
		}

		public String getLockDown() {
			return lockDown;
		}

		public void setLockDown(String lockDown) {
			this.lockDown = lockDown;
		}

		public String getCheckedFlag() {
			return checkedFlag;
		}

		public void setCheckedFlag(String checkedFlag) {
			this.checkedFlag = checkedFlag;
		}

		public String getCheckedBy() {
			return checkedBy;
		}

		public void setCheckedBy(String checkedBy) {
			this.checkedBy = checkedBy;
		}

		public Date getCheckedDate() {
			return checkedDate;
		}

		public void setCheckedDate(Date checkedDate) {
			this.checkedDate = checkedDate;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getSpecialComments() {
			return specialComments;
		}

		public void setSpecialComments(String specialComments) {
			this.specialComments = specialComments;
		}

		public String getInvRefNo() {
			return invRefNo;
		}

		public void setInvRefNo(String invRefNo) {
			this.invRefNo = invRefNo;
		}

		public String getPeriodic() {
			return periodic;
		}

		public void setPeriodic(String periodic) {
			this.periodic = periodic;
		}

		public String getCreditType() {
			return creditType;
		}

		public void setCreditType(String creditType) {
			this.creditType = creditType;
		}

		public String getMailFlag() {
			return mailFlag;
		}

		public void setMailFlag(String mailFlag) {
			this.mailFlag = mailFlag;
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

		public String getIgst() {
			return igst;
		}

		public void setIgst(String igst) {
			this.igst = igst;
		}

		public String getCgst() {
			return cgst;
		}

		public void setCgst(String cgst) {
			this.cgst = cgst;
		}

		public String getSgst() {
			return sgst;
		}

		public void setSgst(String sgst) {
			this.sgst = sgst;
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

		public String getPostInvoiceResp() {
			return postInvoiceResp;
		}

		public void setPostInvoiceResp(String postInvoiceResp) {
			this.postInvoiceResp = postInvoiceResp;
		}

		public String getPostInvoiceRep() {
			return postInvoiceRep;
		}

		public void setPostInvoiceRep(String postInvoiceRep) {
			this.postInvoiceRep = postInvoiceRep;
		}

		public String getIrn() {
			return irn;
		}

		public void setIrn(String irn) {
			this.irn = irn;
		}

		public String getSignQRCode() {
			return signQRCode;
		}

		public void setSignQRCode(String signQRCode) {
			this.signQRCode = signQRCode;
		}

		public String getSignInvoice() {
			return signInvoice;
		}

		public void setSignInvoice(String signInvoice) {
			this.signInvoice = signInvoice;
		}

		public String getAckNo() {
			return ackNo;
		}

		public void setAckNo(String ackNo) {
			this.ackNo = ackNo;
		}

		public Date getAckDt() {
			return ackDt;
		}

		public void setAckDt(Date ackDt) {
			this.ackDt = ackDt;
		}

		public byte[] getQrImage() {
			return qrImage;
		}

		public void setQrImage(byte[] qrImage) {
			this.qrImage = qrImage;
		}

		public String getCanPostInvoiceRep() {
			return canPostInvoiceRep;
		}

		public void setCanPostInvoiceRep(String canPostInvoiceRep) {
			this.canPostInvoiceRep = canPostInvoiceRep;
		}

		public String getCanPostInvoiceResp() {
			return canPostInvoiceResp;
		}

		public void setCanPostInvoiceResp(String canPostInvoiceResp) {
			this.canPostInvoiceResp = canPostInvoiceResp;
		}

		public Date getCanDt() {
			return canDt;
		}

		public void setCanDt(Date canDt) {
			this.canDt = canDt;
		}

		public String getInvoicePath() {
			return invoicePath;
		}

		public void setInvoicePath(String invoicePath) {
			this.invoicePath = invoicePath;
		}

		public String getSignQRCodePath() {
			return signQRCodePath;
		}

		public void setSignQRCodePath(String signQRCodePath) {
			this.signQRCodePath = signQRCodePath;
		}

		public String getIsBos() {
			return isBos;
		}

		public void setIsBos(String isBos) {
			this.isBos = isBos;
		}

		public String getBondType() {
			return bondType;
		}

		public void setBondType(String bondType) {
			this.bondType = bondType;
		}

		public BigDecimal getOtherDeduction() {
			return otherDeduction;
		}

		public void setOtherDeduction(BigDecimal otherDeduction) {
			this.otherDeduction = otherDeduction;
		}

		public String getCustomerLedgerFlag() {
			return customerLedgerFlag;
		}

		public void setCustomerLedgerFlag(String customerLedgerFlag) {
			this.customerLedgerFlag = customerLedgerFlag;
		}

		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}
	    
	    
//		Export Container Invoice
		public Cfinvsrv(BigDecimal billAmt, BigDecimal invoiceAmt, BigDecimal receiptAmt)
	    {
	    	this.billAmt = billAmt;
	    	this.invoiceAmt = invoiceAmt;
	    	this.receiptAmt = receiptAmt;
	    }
	    
}
