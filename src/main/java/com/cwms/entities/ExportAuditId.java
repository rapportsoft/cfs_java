package com.cwms.entities;

import java.io.Serializable;

public class ExportAuditId implements Serializable {

	public String companyId;
	public String branchId;
	public String auditId;
	public ExportAuditId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExportAuditId(String companyId, String branchId, String auditId) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.auditId = auditId;
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
	public String getAuditId() {
		return auditId;
	}
	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	
	
	
}
