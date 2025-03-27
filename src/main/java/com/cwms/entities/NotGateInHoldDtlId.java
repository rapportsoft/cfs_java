package com.cwms.entities;

import java.io.Serializable;

public class NotGateInHoldDtlId implements Serializable {

	public String companyId;
	public String branchId;
	public int srNo;
	public String docRefNo;
	public String containerNo;
	public NotGateInHoldDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NotGateInHoldDtlId(String companyId, String branchId, int srNo, String docRefNo, String containerNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.srNo = srNo;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
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
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
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
	
	
}
