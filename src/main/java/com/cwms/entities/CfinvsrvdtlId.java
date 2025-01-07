package com.cwms.entities;

import java.io.Serializable;

public class CfinvsrvdtlId implements Serializable {

	    private String taxId;
	    private String serviceId;
	    private String partyId;
	    private String invoiceNo;
	    private String branchId;
	    private String companyId;
		public CfinvsrvdtlId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public CfinvsrvdtlId(String taxId, String serviceId, String partyId, String invoiceNo, String branchId,
				String companyId) {
			this.taxId = taxId;
			this.serviceId = serviceId;
			this.partyId = partyId;
			this.invoiceNo = invoiceNo;
			this.branchId = branchId;
			this.companyId = companyId;
		}
		public String getTaxId() {
			return taxId;
		}
		public void setTaxId(String taxId) {
			this.taxId = taxId;
		}
		public String getServiceId() {
			return serviceId;
		}
		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}
		public String getPartyId() {
			return partyId;
		}
		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}
		public String getInvoiceNo() {
			return invoiceNo;
		}
		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
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
	    
	    
	    
}
