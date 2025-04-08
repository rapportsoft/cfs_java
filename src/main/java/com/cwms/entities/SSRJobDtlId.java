package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class SSRJobDtlId implements Serializable {

	public String companyId;
	public String branchId;
	public String transId;
	public String docRefNo;
	public String containerNo;
	public String profitcentreId;
	public String serviceId;
	public SSRJobDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SSRJobDtlId(String companyId, String branchId, String transId, String docRefNo, String containerNo,
			String profitcentreId, String serviceId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.transId = transId;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
		this.profitcentreId = profitcentreId;
		this.serviceId = serviceId;
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
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getDocRefNo() {
		return docRefNo;
	}
	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	
}
