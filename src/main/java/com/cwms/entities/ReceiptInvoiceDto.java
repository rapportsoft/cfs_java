package com.cwms.entities;

import java.math.BigDecimal;

public class ReceiptInvoiceDto {

	public String invoiceNo;
	public String billingTo;
	public String billingParty;
	public String comments;
	public String importerId;
	public int importerSrNo;
	public String importerName;
	public BigDecimal invoiceAmt;
	public BigDecimal invoiceBalAmt;
	public BigDecimal receiptAmt;
	public String tdsStatus;
	public String tdsDeductee;
	public String tdsPercentage;
	public BigDecimal tdsAmt;
	public BigDecimal localAmount;
	public String transId;
	
	public ReceiptInvoiceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ReceiptInvoiceDto(String invoiceNo, String billingTo, String billingParty, String comments,
			String importerId, int importerSrNo, String importerName, BigDecimal invoiceAmt, BigDecimal invoiceBalAmt,
			BigDecimal receiptAmt, String tdsStatus, String tdsDeductee, String tdsPercentage, BigDecimal tdsAmt,
			BigDecimal localAmount, String transId) {
		this.invoiceNo = invoiceNo;
		this.billingTo = billingTo;
		this.billingParty = billingParty;
		this.comments = comments;
		this.importerId = importerId;
		this.importerSrNo = importerSrNo;
		this.importerName = importerName;
		this.invoiceAmt = invoiceAmt;
		this.invoiceBalAmt = invoiceBalAmt;
		this.receiptAmt = receiptAmt;
		this.tdsStatus = tdsStatus;
		this.tdsDeductee = tdsDeductee;
		this.tdsPercentage = tdsPercentage;
		this.tdsAmt = tdsAmt;
		this.localAmount = localAmount;
		this.transId = transId;
	}

	
	

	public String getTransId() {
		return transId;
	}


	public void setTransId(String transId) {
		this.transId = transId;
	}


	public BigDecimal getLocalAmount() {
		return localAmount;
	}






	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}






	public String getTdsStatus() {
		return tdsStatus;
	}





	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}





	public BigDecimal getTdsAmt() {
		return tdsAmt;
	}



	public void setTdsAmt(BigDecimal tdsAmt) {
		this.tdsAmt = tdsAmt;
	}



	public String getTdsPercentage() {
		return tdsPercentage;
	}






	public void setTdsPercentage(String tdsPercentage) {
		this.tdsPercentage = tdsPercentage;
	}






	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getBillingTo() {
		return billingTo;
	}
	public void setBillingTo(String billingTo) {
		this.billingTo = billingTo;
	}
	public String getBillingParty() {
		return billingParty;
	}
	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getImporterName() {
		return importerName;
	}
	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}
	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public BigDecimal getInvoiceBalAmt() {
		return invoiceBalAmt;
	}
	public void setInvoiceBalAmt(BigDecimal invoiceBalAmt) {
		this.invoiceBalAmt = invoiceBalAmt;
	}
	public BigDecimal getReceiptAmt() {
		return receiptAmt;
	}
	public void setReceiptAmt(BigDecimal receiptAmt) {
		this.receiptAmt = receiptAmt;
	}

	public String getTdsDeductee() {
		return tdsDeductee;
	}

	public void setTdsDeductee(String tdsDeductee) {
		this.tdsDeductee = tdsDeductee;
	}



	public String getImporterId() {
		return importerId;
	}



	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}



	public int getImporterSrNo() {
		return importerSrNo;
	}



	public void setImporterSrNo(int importerSrNo) {
		this.importerSrNo = importerSrNo;
	}
	
	
	
}
