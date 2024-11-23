package com.cwms.entities;

import java.io.Serializable;

public class ExportContainerCartingId implements Serializable {

	private String companyId;
	private String branchId;
	private String deStuffId;
	public ExportContainerCartingId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExportContainerCartingId(String companyId, String branchId, String deStuffId) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.deStuffId = deStuffId;
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
	public String getDeStuffId() {
		return deStuffId;
	}
	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}
	
	
	
}
