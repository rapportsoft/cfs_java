package com.cwms.entities;

import java.io.Serializable;

public class ApproverMasterId implements Serializable {

	public String companyId;
	public String branchId;
	public String approverId;
	public String approverName;
	protected ApproverMasterId() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected ApproverMasterId(String companyId, String branchId, String approverId, String approverName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.approverId = approverId;
		this.approverName = approverName;
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
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	
}
