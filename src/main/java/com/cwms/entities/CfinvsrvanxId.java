package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class CfinvsrvanxId implements Serializable {

	private String companyId;
	private String branchId;
	private String processTransId;
	private String serviceId;
	private String taxId;
	private String erpDocRefNo;
	private BigDecimal srlNo;
//	private String processTransLineId;
	public CfinvsrvanxId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	


	public CfinvsrvanxId(String companyId, String branchId, String processTransId, String serviceId, String taxId,
			String erpDocRefNo, BigDecimal srlNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.processTransId = processTransId;
		this.serviceId = serviceId;
		this.taxId = taxId;
		this.erpDocRefNo = erpDocRefNo;
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
	public String getErpDocRefNo() {
		return erpDocRefNo;
	}
	public void setErpDocRefNo(String erpDocRefNo) {
		this.erpDocRefNo = erpDocRefNo;
	}
	public BigDecimal getSrlNo() {
		return srlNo;
	}
	public void setSrlNo(BigDecimal srlNo) {
		this.srlNo = srlNo;
	}





	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, erpDocRefNo, processTransId, serviceId, srlNo, taxId);
	}





	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CfinvsrvanxId other = (CfinvsrvanxId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(erpDocRefNo, other.erpDocRefNo)
				&& Objects.equals(processTransId, other.processTransId) && Objects.equals(serviceId, other.serviceId)
				&& Objects.equals(srlNo, other.srlNo) && Objects.equals(taxId, other.taxId);
	}



	
	

}
