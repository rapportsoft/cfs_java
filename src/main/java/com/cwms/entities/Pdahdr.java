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
@Table(name="pdahdr")
@IdClass(PdahdrId.class)
public class Pdahdr {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Party_Id")
	private String partyId;
	
	@Id
	@Column(name="Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transDate;
	
	@Column(name="Opening_Bal",precision = 16,scale = 3)
	private BigDecimal openingBal;
	
	@Column(name="Closing_Bal",precision = 16,scale = 3)
	private BigDecimal closingBal;
	
	@Column(name="Credit_Bal",precision = 16,scale = 3)
	private BigDecimal creditBal;
	
	@Column(name="Created_By",length = 10)
	private String createdBy;
	
	@Column(name="Status",length = 1)
	private String status;

	public Pdahdr() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pdahdr(String companyId, String branchId, String partyId, Date transDate, BigDecimal openingBal,
			BigDecimal closingBal, BigDecimal creditBal, String createdBy, String status) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.transDate = transDate;
		this.openingBal = openingBal;
		this.closingBal = closingBal;
		this.creditBal = creditBal;
		this.createdBy = createdBy;
		this.status = status;
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

	public BigDecimal getOpeningBal() {
		return openingBal;
	}

	public void setOpeningBal(BigDecimal openingBal) {
		this.openingBal = openingBal;
	}

	public BigDecimal getClosingBal() {
		return closingBal;
	}

	public void setClosingBal(BigDecimal closingBal) {
		this.closingBal = closingBal;
	}

	public BigDecimal getCreditBal() {
		return creditBal;
	}

	public void setCreditBal(BigDecimal creditBal) {
		this.creditBal = creditBal;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
	
}
