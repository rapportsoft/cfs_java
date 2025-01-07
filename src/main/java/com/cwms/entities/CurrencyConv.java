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
@Table(name = "currencyconv")
@IdClass(CurrencyConvId.class)
public class CurrencyConv {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Base_Currency", length = 6)
	private String baseCurrency;

	@Id
	@Column(name = "Conv_Currency", length = 6)
	private String convCurrency;

	@Id
	@Column(name = "Effective_From_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveFromDate;

	@Column(name = "Effective_To_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveToDate;

	@Column(name = "Foreign_Currency_Gain_Ac", length = 7)
	private String foreignCurrencyGainAc;

	@Column(name = "Foreign_Currency_Loss_Ac", length = 7)
	private String foreignCurrencyLossAc;

	@Column(name = "Ex_Rate", precision = 12, scale = 3)
	private BigDecimal exrate = BigDecimal.ZERO;

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
	
	@Column(name="Status",length = 1)
	private String status;

	public CurrencyConv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CurrencyConv(String companyId, String branchId, String baseCurrency, String convCurrency,
			Date effectiveFromDate, Date effectiveToDate, String foreignCurrencyGainAc, String foreignCurrencyLossAc,
			BigDecimal exrate, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String status) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.baseCurrency = baseCurrency;
		this.convCurrency = convCurrency;
		this.effectiveFromDate = effectiveFromDate;
		this.effectiveToDate = effectiveToDate;
		this.foreignCurrencyGainAc = foreignCurrencyGainAc;
		this.foreignCurrencyLossAc = foreignCurrencyLossAc;
		this.exrate = exrate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
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

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getConvCurrency() {
		return convCurrency;
	}

	public void setConvCurrency(String convCurrency) {
		this.convCurrency = convCurrency;
	}

	public Date getEffectiveFromDate() {
		return effectiveFromDate;
	}

	public void setEffectiveFromDate(Date effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}

	public Date getEffectiveToDate() {
		return effectiveToDate;
	}

	public void setEffectiveToDate(Date effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}

	public String getForeignCurrencyGainAc() {
		return foreignCurrencyGainAc;
	}

	public void setForeignCurrencyGainAc(String foreignCurrencyGainAc) {
		this.foreignCurrencyGainAc = foreignCurrencyGainAc;
	}

	public String getForeignCurrencyLossAc() {
		return foreignCurrencyLossAc;
	}

	public void setForeignCurrencyLossAc(String foreignCurrencyLossAc) {
		this.foreignCurrencyLossAc = foreignCurrencyLossAc;
	}

	public BigDecimal getExrate() {
		return exrate;
	}

	public void setExrate(BigDecimal exrate) {
		this.exrate = exrate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
