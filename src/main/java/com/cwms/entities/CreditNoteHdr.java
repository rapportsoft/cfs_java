package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="cfinvsrvcn")
@IdClass(CreditNoteHdrId.class)
public class CreditNoteHdr {

	    @Id
	    @Column(name = "invoice_no", length = 20, nullable = false)
	    private String invoiceNo;

	    @Id
	    @Column(name = "Fin_Year", length = 4, nullable = false)
	    private String finYear;

	    @Id
	    @Column(name = "Branch_Id", length = 6, nullable = false)
	    private String branchId;

	    @Id
	    @Column(name = "Company_Id", length = 6, nullable = false)
	    private String companyId;

	    @Id
	    @Column(name = "Fin_Period", length = 5, nullable = false)
	    private String finPeriod;

	    @Column(name = "Party_Id", length = 6, nullable = false)
	    private String partyId;

	    @Column(name = "Acc_Sr_no", nullable = false)
	    private int accSrNo;

	    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
	    private String profitcentreId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Invoice_Date", nullable = false)
	    private Date invoiceDate;

	    @Column(name = "Container_No", length = 250)
	    private String containerNo;

	    @Column(name = "WO_No", length = 20)
	    private String woNo;

	    @Column(name = "WO_Amnd_No", length = 3)
	    private String woAmndNo;

	    @Column(name = "Party_Type", length = 6)
	    private String partyType;

	    @Column(name = "Term", precision = 3, scale = 0)
	    private BigDecimal term;

	    @Temporal(TemporalType.DATE)
	    @Column(name = "Invoice_Due_Date")
	    private Date invoiceDueDate;

	    @Column(name = "old_invoice_no", length = 20, nullable = false)
	    private String oldInvoiceNo;

	    @Temporal(TemporalType.DATE)
	    @Column(name = "old_Invoice_Date", nullable = false)
	    private Date oldInvoiceDate;

	    @Column(name = "old_Invoice_Amt", precision = 12, scale = 2, nullable = false)
	    private BigDecimal oldInvoiceAmt;

	    @Column(name = "Invoice_Type", length = 3)
	    private String invoiceType;

	    @Column(name = "Invoice_Sub_Type", length = 10)
	    private String invoiceSubType;

	    @Column(name = "Trans_Type", length = 10)
	    private String transType;

	    @Column(name = "ERP_Doc_Ref_No", length = 10)
	    private String erpDocRefNo;

	    @Column(name = "Doc_Ref_No", length = 25)
	    private String docRefNo;

	    @Temporal(TemporalType.DATE)
	    @Column(name = "Doc_Ref_Date")
	    private Date docRefDate;

	    @Column(name = "BE_No", length = 20)
	    private String beNo;

	    @Column(name = "Foreign_Currency", length = 6)
	    private String foreignCurrency;

	    @Column(name = "Foreign_Amt", precision = 12, scale = 2)
	    private BigDecimal foreignAmt;

	    @Column(name = "Ex_Rate", precision = 12, scale = 3)
	    private BigDecimal exRate;

	    @Column(name = "Local_Amt_Foreign", precision = 12, scale = 2)
	    private BigDecimal localAmtForeign;

	    @Column(name = "Local_Amt", precision = 12, scale = 2, nullable = false)
	    private BigDecimal localAmt;

	    @Column(name = "Bill_Amt", precision = 12, scale = 2, nullable = false)
	    private BigDecimal billAmt;

	    @Column(name = "Invoice_Amt", precision = 12, scale = 2, nullable = false)
	    private BigDecimal invoiceAmt;

	    @Column(name = "Ac_Code", length = 10)
	    private String acCode;

	    @Column(name = "Fin_Trans_Id", length = 10)
	    private String finTransId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Fin_Trans_Date")
	    private Date finTransDate;

	    @Column(name = "DebitNote_Id", length = 10)
	    private String debitNoteId;

	    @Column(name = "DN_Receipt_Trans_Id", length = 10)
	    private String dnReceiptTransId;

	    @Column(name = "Receipt_Trans_Id", length = 10)
	    private String receiptTransId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Receipt_Trans_Date")
	    private Date receiptTransDate;

	    @Column(name = "Post_Flag", length = 1)
	    private String postFlag;

	    @Column(name = "Post_By", length = 10)
	    private String postBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Post_Date")
	    private Date postDate;

	    @Column(name = "Comments", length = 250)
	    private String comments;

	    @Column(name = "Special_Comments", length = 250)
	    private String specialComments;

	    @Column(name = "inv_ref_no", length = 20)
	    private String invRefNo;

	    @Column(name = "Periodic", length = 1)
	    private String periodic;

	    @Column(name = "Credit_Type", length = 1)
	    private String creditType;

	    @Column(name = "Billing_Party", length = 10)
	    private String billingParty;

	    @Column(name = "Importer_Id", length = 7)
	    private String importerId;

	    @Column(name = "imp_Sr_no", precision = 8, scale = 0)
	    private BigDecimal impSrNo;

	    @Column(name = "CHA", length = 7)
	    private String cha;

	    @Column(name = "cha_sr_no", precision = 8, scale = 0)
	    private BigDecimal chaSrNo;

	    @Column(name = "IGST", length = 1, nullable = false)
	    private String igst;

	    @Column(name = "CGST", length = 1, nullable = false)
	    private String cgst;

	    @Column(name = "SGST", length = 1, nullable = false)
	    private String sgst;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "IRN", length = 200)
	    private String irn;

	    @Column(name = "SignQRCodePath", length = 180)
	    private String signQRCodePath;

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

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CanDt")
	    private Date canDt;
	    
	    
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
	    
	    
	    
	    
		@Transient
	    private String igmTransId;
	    @Transient
	    private String profitcentreName;
	    @Transient
	    private String igmNo;
	    @Transient
	    private Date igmDate;
	    @Transient
	    private String igmLineNo;
	    @Transient
	    private String viaNo;
	    @Transient
	    private String blNo;
	    @Transient
	    private Date blDate;
	    @Transient
	    private String importerName;
	    @Transient
	    private String commodityDescription;
	    @Transient
	    private BigDecimal noOfPackages;
	    @Transient
	    private String sa;
	    @Transient
	    private String shipingAgentName;
	    @Transient
	    private String sl;
	    @Transient
	    private String shippingLineName;
	    	  
	    @Transient
	    private BigDecimal insuranceValue;
	    @Transient
	    private BigDecimal dutyValue;
	    @Transient
	    private String specialDelivery;
	    @Transient
	    private char onlineStampDuty;
	    @Transient
	    private String agroProductStatus;
	    @Transient
	    private String facilitationCharge;
	    @Transient
	    private String facilitationUnit;
	    @Transient
	    private String facilitationRate;
	    @Transient
	    private String expGst;
	    @Transient
	    private int discountAmt;
	    @Transient
	    private char addMovFlag;
	    @Transient
	    private int addMovementAmt;
	    @Transient
	    private String billingPartyName;
	    @Transient
	    private char taxApplicable;
	    @Transient
	    private String chaName;
	    @Transient
	    private BigDecimal partySrNo;
	    @Transient
	    private String chaAddress;
	    @Transient
	    private String chaAddress2;
	    @Transient
	    private String assesmentId;
	    @Transient
	    private String invType;
	    @Transient
	    private char dpdTariff;
	    @Transient
	    private char discountStatus;
	    @Transient
	    private char sez;
	    @Transient
	    private String sbTransId;
	    @Transient
	    private String sbNo;
	    @Transient
	    private Date sbDate;
	    @Transient
	    private String exporterName;
	    @Transient
	    private String billingParty2;
	    @Transient
	    private String accHolderName;
	    @Transient
	    private String fwdName;	    
	    @Transient
	    private String payingParty;
	    @Transient
	    private String othPartyId;
	    
	    @Transient
	    private BigDecimal oldBillAmt;
	        
	    
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

		public Date getApprovedDate() {
			return approvedDate;
		}

		public void setApprovedDate(Date approvedDate) {
			this.approvedDate = approvedDate;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getOldInvoiceNo() {
			return oldInvoiceNo;
		}

		public void setOldInvoiceNo(String oldInvoiceNo) {
			this.oldInvoiceNo = oldInvoiceNo;
		}

		public Date getOldInvoiceDate() {
			return oldInvoiceDate;
		}

		public void setOldInvoiceDate(Date oldInvoiceDate) {
			this.oldInvoiceDate = oldInvoiceDate;
		}

		public BigDecimal getOldInvoiceAmt() {
			return oldInvoiceAmt;
		}

		public void setOldInvoiceAmt(BigDecimal oldInvoiceAmt) {
			this.oldInvoiceAmt = oldInvoiceAmt;
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

		public BigDecimal getImpSrNo() {
			return impSrNo;
		}

		public void setImpSrNo(BigDecimal impSrNo) {
			this.impSrNo = impSrNo;
		}

		public String getCha() {
			return cha;
		}

		public void setCha(String cha) {
			this.cha = cha;
		}

		public BigDecimal getChaSrNo() {
			return chaSrNo;
		}

		public void setChaSrNo(BigDecimal chaSrNo) {
			this.chaSrNo = chaSrNo;
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

		public byte[] getQrImage() {
			return qrImage;
		}

		public void setQrImage(byte[] qrImage) {
			this.qrImage = qrImage;
		}

		public void setAccSrNo(int accSrNo) {
			this.accSrNo = accSrNo;
		}

		public CreditNoteHdr(String invoiceNo, String finYear, String branchId, String companyId, String finPeriod,
				String partyId, int accSrNo, String profitcentreId, Date invoiceDate, String containerNo, String woNo,
				String woAmndNo, String partyType, BigDecimal term, Date invoiceDueDate, String oldInvoiceNo,
				Date oldInvoiceDate, BigDecimal oldInvoiceAmt, String invoiceType, String invoiceSubType,
				String transType, String erpDocRefNo, String docRefNo, Date docRefDate, String beNo,
				String foreignCurrency, BigDecimal foreignAmt, BigDecimal exRate, BigDecimal localAmtForeign,
				BigDecimal localAmt, BigDecimal billAmt, BigDecimal invoiceAmt, String acCode, String finTransId,
				Date finTransDate, String debitNoteId, String dnReceiptTransId, String receiptTransId,
				Date receiptTransDate, String postFlag, String postBy, Date postDate, String comments,
				String specialComments, String invRefNo, String periodic, String creditType, String billingParty,
				String importerId, BigDecimal impSrNo, String cha, BigDecimal chaSrNo, String igst, String cgst,
				String sgst, String status, String irn, String signQRCodePath, String signQRCode, String signInvoice,
				String ackNo, Date ackDt, byte[] qrImage, Date canDt) {
			super();
			this.invoiceNo = invoiceNo;
			this.finYear = finYear;
			this.branchId = branchId;
			this.companyId = companyId;
			this.finPeriod = finPeriod;
			this.partyId = partyId;
			this.accSrNo = accSrNo;
			this.profitcentreId = profitcentreId;
			this.invoiceDate = invoiceDate;
			this.containerNo = containerNo;
			this.woNo = woNo;
			this.woAmndNo = woAmndNo;
			this.partyType = partyType;
			this.term = term;
			this.invoiceDueDate = invoiceDueDate;
			this.oldInvoiceNo = oldInvoiceNo;
			this.oldInvoiceDate = oldInvoiceDate;
			this.oldInvoiceAmt = oldInvoiceAmt;
			this.invoiceType = invoiceType;
			this.invoiceSubType = invoiceSubType;
			this.transType = transType;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.docRefDate = docRefDate;
			this.beNo = beNo;
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
			this.postFlag = postFlag;
			this.postBy = postBy;
			this.postDate = postDate;
			this.comments = comments;
			this.specialComments = specialComments;
			this.invRefNo = invRefNo;
			this.periodic = periodic;
			this.creditType = creditType;
			this.billingParty = billingParty;
			this.importerId = importerId;
			this.impSrNo = impSrNo;
			this.cha = cha;
			this.chaSrNo = chaSrNo;
			this.igst = igst;
			this.cgst = cgst;
			this.sgst = sgst;
			this.status = status;
			this.irn = irn;
			this.signQRCodePath = signQRCodePath;
			this.signQRCode = signQRCode;
			this.signInvoice = signInvoice;
			this.ackNo = ackNo;
			this.ackDt = ackDt;
			this.qrImage = qrImage;
			this.canDt = canDt;
		}

		public CreditNoteHdr() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getInvoiceNo() {
			return invoiceNo;
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

		public String getFinYear() {
			return finYear;
		}

		public void setFinYear(String finYear) {
			this.finYear = finYear;
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

		public String getFinPeriod() {
			return finPeriod;
		}

		public void setFinPeriod(String finPeriod) {
			this.finPeriod = finPeriod;
		}

		public String getPartyId() {
			return partyId;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}

		public Integer getAccSrNo() {
			return accSrNo;
		}

		public void setAccSrNo(Integer accSrNo) {
			this.accSrNo = accSrNo;
		}

		public String getProfitCentreId() {
			return profitcentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitcentreId = profitCentreId;
		}

		public Date getInvoiceDate() {
			return invoiceDate;
		}

		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}

		public String getContainerNo() {
			return containerNo;
		}

		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
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

		public String getInvoiceSubType() {
			return invoiceSubType;
		}

		public void setInvoiceSubType(String invoiceSubType) {
			this.invoiceSubType = invoiceSubType;
		}

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
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

		public String getBeNo() {
			return beNo;
		}

		public void setBeNo(String beNo) {
			this.beNo = beNo;
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

		public String getIrn() {
			return irn;
		}

		public void setIrn(String irn) {
			this.irn = irn;
		}

		public String getSignQRCodePath() {
			return signQRCodePath;
		}

		public void setSignQRCodePath(String signQRCodePath) {
			this.signQRCodePath = signQRCodePath;
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

		public Date getCanDt() {
			return canDt;
		}

		public void setCanDt(Date canDt) {
			this.canDt = canDt;
		}

	
		    
		    
	
		public String getIgmTransId() {
				return igmTransId;
			}

			public void setIgmTransId(String igmTransId) {
				this.igmTransId = igmTransId;
			}

			public String getProfitcentreName() {
				return profitcentreName;
			}

			public void setProfitcentreName(String profitcentreName) {
				this.profitcentreName = profitcentreName;
			}

			public String getIgmNo() {
				return igmNo;
			}

			public void setIgmNo(String igmNo) {
				this.igmNo = igmNo;
			}

			public Date getIgmDate() {
				return igmDate;
			}

			public void setIgmDate(Date igmDate) {
				this.igmDate = igmDate;
			}

			public String getIgmLineNo() {
				return igmLineNo;
			}

			public void setIgmLineNo(String igmLineNo) {
				this.igmLineNo = igmLineNo;
			}

			public String getViaNo() {
				return viaNo;
			}

			public void setViaNo(String viaNo) {
				this.viaNo = viaNo;
			}

			public String getBlNo() {
				return blNo;
			}

			public void setBlNo(String blNo) {
				this.blNo = blNo;
			}

			public Date getBlDate() {
				return blDate;
			}

			public void setBlDate(Date blDate) {
				this.blDate = blDate;
			}

			public String getImporterName() {
				return importerName;
			}

			public void setImporterName(String importerName) {
				this.importerName = importerName;
			}

			public String getCommodityDescription() {
				return commodityDescription;
			}

			public void setCommodityDescription(String commodityDescription) {
				this.commodityDescription = commodityDescription;
			}

			public BigDecimal getNoOfPackages() {
				return noOfPackages;
			}

			public void setNoOfPackages(BigDecimal noOfPackages) {
				this.noOfPackages = noOfPackages;
			}

			public String getSa() {
				return sa;
			}

			public void setSa(String sa) {
				this.sa = sa;
			}

			public String getShipingAgentName() {
				return shipingAgentName;
			}

			public void setShipingAgentName(String shipingAgentName) {
				this.shipingAgentName = shipingAgentName;
			}

			public String getSl() {
				return sl;
			}

			public void setSl(String sl) {
				this.sl = sl;
			}

			public String getShippingLineName() {
				return shippingLineName;
			}

			public void setShippingLineName(String shippingLineName) {
				this.shippingLineName = shippingLineName;
			}

			public String getCreatedBy() {
				return createdBy;
			}

			public void setCreatedBy(String createdBy) {
				this.createdBy = createdBy;
			}

			public String getApprovedBy() {
				return approvedBy;
			}

			public void setApprovedBy(String approvedBy) {
				this.approvedBy = approvedBy;
			}

			public BigDecimal getInsuranceValue() {
				return insuranceValue;
			}

			public void setInsuranceValue(BigDecimal insuranceValue) {
				this.insuranceValue = insuranceValue;
			}

			public BigDecimal getDutyValue() {
				return dutyValue;
			}

			public void setDutyValue(BigDecimal dutyValue) {
				this.dutyValue = dutyValue;
			}

			public String getSpecialDelivery() {
				return specialDelivery;
			}

			public void setSpecialDelivery(String specialDelivery) {
				this.specialDelivery = specialDelivery;
			}

			public char getOnlineStampDuty() {
				return onlineStampDuty;
			}

			public void setOnlineStampDuty(char onlineStampDuty) {
				this.onlineStampDuty = onlineStampDuty;
			}

			public String getAgroProductStatus() {
				return agroProductStatus;
			}

			public void setAgroProductStatus(String agroProductStatus) {
				this.agroProductStatus = agroProductStatus;
			}

			public String getFacilitationCharge() {
				return facilitationCharge;
			}

			public void setFacilitationCharge(String facilitationCharge) {
				this.facilitationCharge = facilitationCharge;
			}

			public String getFacilitationUnit() {
				return facilitationUnit;
			}

			public void setFacilitationUnit(String facilitationUnit) {
				this.facilitationUnit = facilitationUnit;
			}

			public String getFacilitationRate() {
				return facilitationRate;
			}

			public void setFacilitationRate(String facilitationRate) {
				this.facilitationRate = facilitationRate;
			}

			public String getExpGst() {
				return expGst;
			}

			public void setExpGst(String expGst) {
				this.expGst = expGst;
			}

			public int getDiscountAmt() {
				return discountAmt;
			}

			public void setDiscountAmt(int discountAmt) {
				this.discountAmt = discountAmt;
			}

			public char getAddMovFlag() {
				return addMovFlag;
			}

			public void setAddMovFlag(char addMovFlag) {
				this.addMovFlag = addMovFlag;
			}

			public int getAddMovementAmt() {
				return addMovementAmt;
			}

			public void setAddMovementAmt(int addMovementAmt) {
				this.addMovementAmt = addMovementAmt;
			}

			public String getBillingPartyName() {
				return billingPartyName;
			}

			public void setBillingPartyName(String billingPartyName) {
				this.billingPartyName = billingPartyName;
			}

			public char getTaxApplicable() {
				return taxApplicable;
			}

			public void setTaxApplicable(char taxApplicable) {
				this.taxApplicable = taxApplicable;
			}

			public String getChaName() {
				return chaName;
			}

			public void setChaName(String chaName) {
				this.chaName = chaName;
			}

			public BigDecimal getPartySrNo() {
				return partySrNo;
			}

			public void setPartySrNo(BigDecimal partySrNo) {
				this.partySrNo = partySrNo;
			}

			public String getChaAddress() {
				return chaAddress;
			}

			public void setChaAddress(String chaAddress) {
				this.chaAddress = chaAddress;
			}

			public String getChaAddress2() {
				return chaAddress2;
			}

			public void setChaAddress2(String chaAddress2) {
				this.chaAddress2 = chaAddress2;
			}

			public String getAssesmentId() {
				return assesmentId;
			}

			public void setAssesmentId(String assesmentId) {
				this.assesmentId = assesmentId;
			}

			public String getInvType() {
				return invType;
			}

			public void setInvType(String invType) {
				this.invType = invType;
			}

			public char getDpdTariff() {
				return dpdTariff;
			}

			public void setDpdTariff(char dpdTariff) {
				this.dpdTariff = dpdTariff;
			}

			public char getDiscountStatus() {
				return discountStatus;
			}

			public void setDiscountStatus(char discountStatus) {
				this.discountStatus = discountStatus;
			}

			public char getSez() {
				return sez;
			}

			public void setSez(char sez) {
				this.sez = sez;
			}

			public String getSbTransId() {
				return sbTransId;
			}

			public void setSbTransId(String sbTransId) {
				this.sbTransId = sbTransId;
			}

			public String getSbNo() {
				return sbNo;
			}

			public void setSbNo(String sbNo) {
				this.sbNo = sbNo;
			}

			public Date getSbDate() {
				return sbDate;
			}

			public void setSbDate(Date sbDate) {
				this.sbDate = sbDate;
			}

			public String getExporterName() {
				return exporterName;
			}

			public void setExporterName(String exporterName) {
				this.exporterName = exporterName;
			}

			public String getBillingParty2() {
				return billingParty2;
			}

			public void setBillingParty2(String billingParty2) {
				this.billingParty2 = billingParty2;
			}

			public String getAccHolderName() {
				return accHolderName;
			}

			public void setAccHolderName(String accHolderName) {
				this.accHolderName = accHolderName;
			}

			public String getFwdName() {
				return fwdName;
			}

			public void setFwdName(String fwdName) {
				this.fwdName = fwdName;
			}

			public String getPayingParty() {
				return payingParty;
			}

			public void setPayingParty(String payingParty) {
				this.payingParty = payingParty;
			}

			public String getOthPartyId() {
				return othPartyId;
			}

			public void setOthPartyId(String othPartyId) {
				this.othPartyId = othPartyId;
			}

			public BigDecimal getOldBillAmt() {
				return oldBillAmt;
			}

			public void setOldBillAmt(BigDecimal oldBillAmt) {
				this.oldBillAmt = oldBillAmt;
			}
			

		@Override
			public String toString() {
				return "CreditNoteHdr [invoiceNo=" + invoiceNo + ", finYear=" + finYear + ", branchId=" + branchId
						+ ", companyId=" + companyId + ", finPeriod=" + finPeriod + ", partyId=" + partyId
						+ ", accSrNo=" + accSrNo + ", profitcentreId=" + profitcentreId + ", invoiceDate=" + invoiceDate
						+ ", containerNo=" + containerNo + ", woNo=" + woNo + ", woAmndNo=" + woAmndNo + ", partyType="
						+ partyType + ", term=" + term + ", invoiceDueDate=" + invoiceDueDate + ", oldInvoiceNo="
						+ oldInvoiceNo + ", oldInvoiceDate=" + oldInvoiceDate + ", oldInvoiceAmt=" + oldInvoiceAmt
						+ ", invoiceType=" + invoiceType + ", invoiceSubType=" + invoiceSubType + ", transType="
						+ transType + ", erpDocRefNo=" + erpDocRefNo + ", docRefNo=" + docRefNo + ", docRefDate="
						+ docRefDate + ", beNo=" + beNo + ", foreignCurrency=" + foreignCurrency + ", foreignAmt="
						+ foreignAmt + ", exRate=" + exRate + ", localAmtForeign=" + localAmtForeign + ", localAmt="
						+ localAmt + ", billAmt=" + billAmt + ", invoiceAmt=" + invoiceAmt + ", acCode=" + acCode
						+ ", finTransId=" + finTransId + ", finTransDate=" + finTransDate + ", debitNoteId="
						+ debitNoteId + ", dnReceiptTransId=" + dnReceiptTransId + ", receiptTransId=" + receiptTransId
						+ ", receiptTransDate=" + receiptTransDate + ", postFlag=" + postFlag + ", postBy=" + postBy
						+ ", postDate=" + postDate + ", comments=" + comments + ", specialComments=" + specialComments
						+ ", invRefNo=" + invRefNo + ", periodic=" + periodic + ", creditType=" + creditType
						+ ", billingParty=" + billingParty + ", importerId=" + importerId + ", impSrNo=" + impSrNo
						+ ", cha=" + cha + ", chaSrNo=" + chaSrNo + ", igst=" + igst + ", cgst=" + cgst + ", sgst="
						+ sgst + ", status=" + status + ", irn=" + irn + ", signQRCodePath=" + signQRCodePath
						+ ", signQRCode=" + signQRCode + ", signInvoice=" + signInvoice + ", ackNo=" + ackNo
						+ ", ackDt=" + ackDt + ", qrImage=" + Arrays.toString(qrImage) + ", canDt=" + canDt
						+ ", igmTransId=" + igmTransId + ", profitcentreName=" + profitcentreName + ", igmNo=" + igmNo
						+ ", igmDate=" + igmDate + ", igmLineNo=" + igmLineNo + ", viaNo=" + viaNo + ", blNo=" + blNo
						+ ", blDate=" + blDate + ", importerName=" + importerName + ", commodityDescription="
						+ commodityDescription + ", noOfPackages=" + noOfPackages + ", sa=" + sa + ", shipingAgentName="
						+ shipingAgentName + ", sl=" + sl + ", shippingLineName=" + shippingLineName + ", createdBy="
						+ createdBy + ", approvedBy=" + approvedBy + ", insuranceValue=" + insuranceValue
						+ ", dutyValue=" + dutyValue + ", specialDelivery=" + specialDelivery + ", onlineStampDuty="
						+ onlineStampDuty + ", agroProductStatus=" + agroProductStatus + ", facilitationCharge="
						+ facilitationCharge + ", facilitationUnit=" + facilitationUnit + ", facilitationRate="
						+ facilitationRate + ", expGst=" + expGst + ", discountAmt=" + discountAmt + ", addMovFlag="
						+ addMovFlag + ", addMovementAmt=" + addMovementAmt + ", billingPartyName=" + billingPartyName
						+ ", taxApplicable=" + taxApplicable + ", chaName=" + chaName + ", partySrNo=" + partySrNo
						+ ", chaAddress=" + chaAddress + ", chaAddress2=" + chaAddress2 + ", assesmentId=" + assesmentId
						+ ", invType=" + invType + ", dpdTariff=" + dpdTariff + ", discountStatus=" + discountStatus
						+ ", sez=" + sez + ", sbTransId=" + sbTransId + ", sbNo=" + sbNo + ", sbDate=" + sbDate
						+ ", exporterName=" + exporterName + ", billingParty2=" + billingParty2 + ", accHolderName="
						+ accHolderName + ", fwdName=" + fwdName + ", payingParty=" + payingParty + ", othPartyId="
						+ othPartyId + ", oldBillAmt=" + oldBillAmt + "]";
			}

		public CreditNoteHdr(
			    String invoiceNo, Date invoiceDate, String igmTransId, String profitcentreId, String profitcentreDesc,
			    String igmNo, Date igmDate, String igmLineNo, String viaNo, String blNo, Date blDate,
			    String importerName, String commodityDescription, BigDecimal noOfPackages, String sa, String partyName1,
			    String sl, String partyName2, char status, String userName1, String userName2, BigDecimal insuranceValue,
			    BigDecimal dutyValue, String specialDelivery, char onlineStampDuty, String agroProductStatus,
			    String facilitationCharge, String facilitationUnit, String facilitationRate, String importerId,
			    String billingParty, String gstNo, int discountAmt, char addMovFlag, int addMovementAmt,
			    String partyId, String partyName3, char taxApplicable, char dpdTariff, char discountStatus,
			    char sez, int impSrNo, int chaSrNo, String cha, String partyName4, BigDecimal partySrNo, String address1,
			    String address2, String invoiceNo2, 
			    String assesmentId, String billingParty2, String partyType, String invoiceType, String invoiceSubType,
			    String transType, String erpDocRefNo, String docRefNo, String igst, String cgst, String sgst,
			    String sbTransId, String sbNo, Date sbDate, String exporterName, BigDecimal invoiceAmt, 
			    String payingParty, String othPartyId, String onAccountOfName, String fwdName, BigDecimal billAmt
			) { 
		        this.oldInvoiceNo = invoiceNo;
		        this.oldInvoiceDate = invoiceDate;
		        this.igmTransId = igmTransId;
		        this.profitcentreId = profitcentreId;
		        this.profitcentreName = profitcentreDesc;
		        this.igmNo = igmNo;
		        this.igmDate = igmDate;
		        this.igmLineNo = igmLineNo;
		        this.viaNo = viaNo;
		        this.blNo = blNo;
		        this.blDate = blDate;
		        this.importerName = importerName;
		        this.commodityDescription = commodityDescription;
		        this.noOfPackages = noOfPackages;
		        this.sa = sa;
		        this.shipingAgentName = partyName1;
		        this.sl = sl;
		        this.shippingLineName = partyName2;
//		        this.createdBy = userName1;
//		        this.approvedBy = userName2;
		        this.insuranceValue = insuranceValue;
		        this.dutyValue = dutyValue;
		        this.specialDelivery = specialDelivery;
		        this.onlineStampDuty = onlineStampDuty;
		        this.agroProductStatus = agroProductStatus;
		        this.facilitationCharge = facilitationCharge;
		        this.facilitationUnit = facilitationUnit;
		        this.facilitationRate = facilitationRate;
		        this.importerId = importerId;
		        this.billingParty = billingParty;
		        this.expGst = gstNo;
		        this.discountAmt = discountAmt;
		        this.addMovFlag = addMovFlag;
		        this.addMovementAmt = addMovementAmt;
		        this.partyId = partyId;
		        this.billingPartyName = partyName3;
		        this.taxApplicable = taxApplicable;
		        this.dpdTariff = dpdTariff;
		        this.discountStatus = discountStatus;
		        this.sez = sez;
		        this.impSrNo = impSrNo == 0 ? null : BigDecimal.valueOf(impSrNo);
		        this.chaSrNo = chaSrNo == 0 ? null : BigDecimal.valueOf(chaSrNo);
		        this.cha = cha;
		        this.chaName = partyName4;
		        this.invoiceType = invoiceType;
		        this.partySrNo = partySrNo;
		        this.chaAddress = address1;
		        this.chaAddress2 = address2;
		        this.assesmentId = assesmentId;
		        this.partyType = partyType;
		        this.invType = invoiceType;
		        this.invoiceSubType = invoiceSubType;
		        this.transType = transType;
		        this.erpDocRefNo = erpDocRefNo;
		        this.docRefNo = docRefNo;
		        this.igst = igst;
		        this.cgst = cgst;
		        this.sgst = sgst;
		        this.sbTransId = sbTransId;
		        this.sbNo = sbNo;
		        this.sbDate = sbDate;
		        this.exporterName = exporterName;
		        this.oldInvoiceAmt = invoiceAmt;				        
		        this.payingParty = payingParty;
		        this.othPartyId = othPartyId;
		        this.oldBillAmt = billAmt;
		        this.billingParty2 = billingParty2;
		        this.accHolderName = onAccountOfName;
		        this.fwdName = fwdName;
		        this.partyType = billingParty;
		        this.containerNo = assesmentId;
		    }
		
		
		
		public CreditNoteHdr(
			    String invoiceNo, Date invoiceDate, String igmTransId, String profitcentreId, String profitcentreDesc,
			    String igmNo, Date igmDate, String igmLineNo, String viaNo, String blNo, Date blDate,
			    String importerName, String commodityDescription, BigDecimal noOfPackages, String sa, String partyName1,
			    String sl, String partyName2, char status, String userName1, String userName2, BigDecimal insuranceValue,
			    BigDecimal dutyValue, String specialDelivery, char onlineStampDuty, String agroProductStatus,
			    String facilitationCharge, String facilitationUnit, String facilitationRate, String importerId,
			    String billingParty, String gstNo, int discountAmt, char addMovFlag, int addMovementAmt,
			    String partyId, String partyName3, char taxApplicable, char dpdTariff, char discountStatus,
			    char sez, int impSrNo, int chaSrNo, String cha, String partyName4, BigDecimal partySrNo, String address1,
			    String address2, String invoiceNo2, 
			    String assesmentId, String billingParty2, String partyType, String invoiceType, String invoiceSubType,
			    String transType, String erpDocRefNo, String docRefNo, String igst, String cgst, String sgst,
			    String sbTransId, String sbNo, Date sbDate, String exporterName, BigDecimal invoiceAmt, 
			    String payingParty, String othPartyId, String onAccountOfName, String fwdName, BigDecimal billAmt, 
			    String comments, String invoiceNoNew, Date invoiceDateNew
			) { 
		        this.oldInvoiceNo = invoiceNo;
		        this.oldInvoiceDate = invoiceDate;
		        this.igmTransId = igmTransId;
		        this.profitcentreId = profitcentreId;
		        this.profitcentreName = profitcentreDesc;
		        this.igmNo = igmNo;
		        this.igmDate = igmDate;
		        this.igmLineNo = igmLineNo;
		        this.viaNo = viaNo;
		        this.blNo = blNo;
		        this.blDate = blDate;
		        this.importerName = importerName;
		        this.commodityDescription = commodityDescription;
		        this.noOfPackages = noOfPackages;
		        this.sa = sa;
		        this.shipingAgentName = partyName1;
		        this.sl = sl;
		        this.shippingLineName = partyName2;
//		        this.createdBy = userName1;
//		        this.approvedBy = userName2;
		        this.insuranceValue = insuranceValue;
		        this.dutyValue = dutyValue;
		        this.specialDelivery = specialDelivery;
		        this.onlineStampDuty = onlineStampDuty;
		        this.agroProductStatus = agroProductStatus;
		        this.facilitationCharge = facilitationCharge;
		        this.facilitationUnit = facilitationUnit;
		        this.facilitationRate = facilitationRate;
		        this.importerId = importerId;
		        this.billingParty = billingParty;
		        this.expGst = gstNo;
		        this.discountAmt = discountAmt;
		        this.addMovFlag = addMovFlag;
		        this.addMovementAmt = addMovementAmt;
		        this.partyId = partyId;
		        this.billingPartyName = partyName3;
		        this.taxApplicable = taxApplicable;
		        this.dpdTariff = dpdTariff;
		        this.discountStatus = discountStatus;
		        this.sez = sez;
		        this.impSrNo = impSrNo == 0 ? null : BigDecimal.valueOf(impSrNo);
		        this.chaSrNo = chaSrNo == 0 ? null : BigDecimal.valueOf(chaSrNo);
		        this.cha = cha;
		        this.chaName = partyName4;
		        this.partySrNo = partySrNo;
		        this.chaAddress = address1;
		        this.chaAddress2 = address2;
		        this.assesmentId = assesmentId;
		        this.partyType = partyType;
		        this.invType = invoiceType;
		        this.invoiceType = invoiceType;
		        this.invoiceSubType = invoiceSubType;
		        this.transType = transType;
		        this.erpDocRefNo = erpDocRefNo;
		        this.docRefNo = docRefNo;
		        this.igst = igst;
		        this.cgst = cgst;
		        this.sgst = sgst;
		        this.sbTransId = sbTransId;
		        this.sbNo = sbNo;
		        this.sbDate = sbDate;
		        this.exporterName = exporterName;
		        this.oldInvoiceAmt = invoiceAmt;				        
		        this.payingParty = payingParty;
		        this.othPartyId = othPartyId;
		        this.oldBillAmt = billAmt;
		        this.billingParty2 = billingParty2;
		        this.accHolderName = onAccountOfName;
		        this.fwdName = fwdName;
		        this.comments = comments;
		        this.partyType = billingParty;
		        this.invoiceNo = invoiceNoNew;
		        this.invoiceDate = invoiceDateNew;
		        this.containerNo = assesmentId;		        
		    }
		
	

}
