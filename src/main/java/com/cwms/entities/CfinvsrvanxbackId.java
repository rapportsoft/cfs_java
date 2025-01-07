package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class CfinvsrvanxbackId implements Serializable {

	    private String companyId;
	    private String branchId;
	    private String erpDocRefNo;
	    private String processTransId;
	    private String serviceId;
	    private String taxId;
	    private BigDecimal srlNo;
		public CfinvsrvanxbackId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public CfinvsrvanxbackId(String companyId, String branchId, String erpDocRefNo, String processTransId,
				String serviceId, String taxId, BigDecimal srlNo) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.erpDocRefNo = erpDocRefNo;
			this.processTransId = processTransId;
			this.serviceId = serviceId;
			this.taxId = taxId;
			this.srlNo = srlNo;
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
		public String getErpDocRefNo() {
			return erpDocRefNo;
		}
		public void setErpDocRefNo(String erpDocRefNo) {
			this.erpDocRefNo = erpDocRefNo;
		}
		public String getProcessTransId() {
			return processTransId;
		}
		public void setProcessTransId(String processTransId) {
			this.processTransId = processTransId;
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
		public BigDecimal getSrlNo() {
			return srlNo;
		}
		public void setSrlNo(BigDecimal srlNo) {
			this.srlNo = srlNo;
		}
	    
	    
	    
}
