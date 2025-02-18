package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class AssigneeMasterId implements Serializable {

	public String companyId;
	public String branchId;
	public String assigneeId;
	public String assigneeName;
	protected AssigneeMasterId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected AssigneeMasterId(String companyId, String branchId, String assigneeId, String assigneeName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.assigneeId = assigneeId;
		this.assigneeName = assigneeName;
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
	public String getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	
	
	
	
}
