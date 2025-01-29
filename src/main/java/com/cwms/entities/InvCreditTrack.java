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
@Table(name="invcredittrck")
@IdClass(InvCreditTrackId.class)
public class InvCreditTrack {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Party_Id",length = 8)
	private String partyId;
	
	@Id
	@Column(name="Trans_Id",length = 16)
	private String transId;
	
	@Id
	@Column(name="Invoice_No",length = 16)
	private String invoiceNo;
	
	@Id
	@Column(name="Assesment_Id",length = 20)
	private String assesmentId;
	
	@Column(name="Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transDate;
	
	@Column(name="Invoice_Amount",precision = 16,scale = 3)
	private BigDecimal invoiceAmount;
	
	@Column(name="Local_Amount",precision = 16,scale = 3)
	private BigDecimal localAmount;
	
	
	@Column(name="Credit_Amount",precision = 16,scale = 3)
	private BigDecimal creditAmount;
	
	@Column(name="Credit_Adjust_Amount",precision = 16,scale = 3)
	private BigDecimal creditAdjustAmount;
	
	
	@Column(name="Created_By",length = 10)
	private String createdby;
	
	@Column(name="Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="Status",length = 1)
	private String status;
	
	
	@Column(name="Credit-Flag",length = 1)
	private String creditFlag;
	
	
	@Column(name="DB_Type",length = 10)
	private String dbType;
	
	@Column(name="Tds_Status",length = 1)
	private String tdsStatus;
	
	
	@Column(name="Tds_Amt",precision = 16,scale = 3)
	private BigDecimal tdsAmt;
	
	

	@Column(name="Tds_Deductee",length = 10)
	private String tdsDeductee;


	public InvCreditTrack() {
		super();
		// TODO Auto-generated constructor stub
	}



	public InvCreditTrack(String companyId, String branchId, String partyId, String transId, String invoiceNo,
			String assesmentId, Date transDate, BigDecimal invoiceAmount, BigDecimal localAmount,
			BigDecimal creditAmount, BigDecimal creditAdjustAmount, String createdby, Date createdDate, String status,
			String creditFlag, String dbType, String tdsStatus, BigDecimal tdsAmt, String tdsDeductee) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.transId = transId;
		this.invoiceNo = invoiceNo;
		this.assesmentId = assesmentId;
		this.transDate = transDate;
		this.invoiceAmount = invoiceAmount;
		this.localAmount = localAmount;
		this.creditAmount = creditAmount;
		this.creditAdjustAmount = creditAdjustAmount;
		this.createdby = createdby;
		this.createdDate = createdDate;
		this.status = status;
		this.creditFlag = creditFlag;
		this.dbType = dbType;
		this.tdsStatus = tdsStatus;
		this.tdsAmt = tdsAmt;
		this.tdsDeductee = tdsDeductee;
	}

	
	


	public BigDecimal getLocalAmount() {
		return localAmount;
	}



	public void setLocalAmount(BigDecimal localAmount) {
		this.localAmount = localAmount;
	}



	public String getTdsDeductee() {
		return tdsDeductee;
	}




	public void setTdsDeductee(String tdsDeductee) {
		this.tdsDeductee = tdsDeductee;
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


	public String getTransId() {
		return transId;
	}


	public void setTransId(String transId) {
		this.transId = transId;
	}


	public String getInvoiceNo() {
		return invoiceNo;
	}


	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public String getAssesmentId() {
		return assesmentId;
	}


	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}


	public Date getTransDate() {
		return transDate;
	}


	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}


	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}


	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}


	public BigDecimal getCreditAmount() {
		return creditAmount;
	}


	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}


	public BigDecimal getCreditAdjustAmount() {
		return creditAdjustAmount;
	}


	public void setCreditAdjustAmount(BigDecimal creditAdjustAmount) {
		this.creditAdjustAmount = creditAdjustAmount;
	}


	public String getCreatedby() {
		return createdby;
	}


	public void setCreatedby(String createdby) {
		this.createdby = createdby;
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


	public String getCreditFlag() {
		return creditFlag;
	}


	public void setCreditFlag(String creditFlag) {
		this.creditFlag = creditFlag;
	}


	public String getDbType() {
		return dbType;
	}


	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	
	
	
	
}
