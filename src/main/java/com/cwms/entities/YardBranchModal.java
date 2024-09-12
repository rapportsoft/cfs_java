package com.cwms.entities;

public class YardBranchModal {

	public Branch branch;
	public Yard yard;
	public YardBranchModal() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YardBranchModal(Branch branch, Yard yard) {
		super();
		this.branch = branch;
		this.yard = yard;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public Yard getYard() {
		return yard;
	}
	public void setYard(Yard yard) {
		this.yard = yard;
	}
	
	
	
	
}
