package com.cwms.entities;

public class DestuffId {
	private String companyId;
	private String branchId;
	private String finYear;
	private String deStuffId;
	public DestuffId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DestuffId(String companyId, String branchId, String finYear, String deStuffId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
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
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getDeStuffId() {
		return deStuffId;
	}
	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}
	
	
	
}
