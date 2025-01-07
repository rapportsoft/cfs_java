package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentReceiptDTO {
   
	public String payMode;
	public String chequeNo;
	public Date chequeDate;
	public String bankDetails;
	public BigDecimal amount;
	public String status;
	
	
	
	
	public PaymentReceiptDTO() {
		super();
		// TODO Auto-generated constructor stub
	}




	public PaymentReceiptDTO(String payMode, String chequeNo, Date chequeDate, String bankDetails, BigDecimal amount,
			String status) {
		this.payMode = payMode;
		this.chequeNo = chequeNo;
		this.chequeDate = chequeDate;
		this.bankDetails = bankDetails;
		this.amount = amount;
		this.status = status;
	}




	public String getPayMode() {
		return payMode;
	}




	public void setPayMode(String payMode) {
		this.payMode = payMode;
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




	public String getBankDetails() {
		return bankDetails;
	}




	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}




	public BigDecimal getAmount() {
		return amount;
	}




	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
