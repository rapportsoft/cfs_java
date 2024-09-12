package com.cwms.entities;
import java.io.Serializable;
import java.util.Objects;

public class TaxId implements Serializable {

    private String companyId;
    private String taxId;
    private String taxType;
    private String tdsType;
    private String tdsStatus;
	public TaxId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaxId(String companyId, String taxId, String taxType, String tdsType, String tdsStatus) {
		super();
		this.companyId = companyId;
		this.taxId = taxId;
		this.taxType = taxType;
		this.tdsType = tdsType;
		this.tdsStatus = tdsStatus;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	public String getTdsType() {
		return tdsType;
	}
	public void setTdsType(String tdsType) {
		this.tdsType = tdsType;
	}
	public String getTdsStatus() {
		return tdsStatus;
	}
	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}

    // Constructors, equals and hashCode methods
    
}
