package com.cwms.entities;

import java.io.Serializable;
import java.util.Date;

public class CurrencyConvId implements Serializable {

	private String companyId;
	private String branchId;
	private String baseCurrency;
	private String convCurrency;
	private Date effectiveFromDate;
	public CurrencyConvId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CurrencyConvId(String companyId, String branchId, String baseCurrency, String convCurrency,
			Date effectiveFromDate) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.baseCurrency = baseCurrency;
		this.convCurrency = convCurrency;
		this.effectiveFromDate = effectiveFromDate;
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
	
	
}
