package com.cwms.entities;

import java.util.Objects;

public class CreditNoteDtlId {
	
	 private String companyId;
	 private String branchId;
	 private String finYear;
	 private String invoiceNo;
	 private String partyId;
	 private String serviceId;
	 private String taxId;
	public CreditNoteDtlId(String companyId, String branchId, String finYear, String invoiceNo, String partyId,
			String serviceId, String taxId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.invoiceNo = invoiceNo;
		this.partyId = partyId;
		this.serviceId = serviceId;
		this.taxId = taxId;
	}
	public CreditNoteDtlId() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, finYear, invoiceNo, partyId, serviceId, taxId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditNoteDtlId other = (CreditNoteDtlId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(finYear, other.finYear) && Objects.equals(invoiceNo, other.invoiceNo)
				&& Objects.equals(partyId, other.partyId) && Objects.equals(serviceId, other.serviceId)
				&& Objects.equals(taxId, other.taxId);
	}
	 
	 
	 
	 

}
