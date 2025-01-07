package com.cwms.entities;

import java.io.Serializable;
import java.util.Date;

public class TaxDtlId implements Serializable {

	private String companyId;
	private Date periodFrom;
	private String taxId;
	private String taxType;
	private String tdsStatus;
	private String tdsType;
	public TaxDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaxDtlId(String companyId, Date periodFrom, String taxId, String taxType, String tdsStatus, String tdsType) {
		this.companyId = companyId;
		this.periodFrom = periodFrom;
		this.taxId = taxId;
		this.taxType = taxType;
		this.tdsStatus = tdsStatus;
		this.tdsType = tdsType;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public Date getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getTdsStatus() {
		return tdsStatus;
	}
	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}
	public String getTdsType() {
		return tdsType;
	}
	public void setTdsType(String tdsType) {
		this.tdsType = tdsType;
	}
	
	
}
