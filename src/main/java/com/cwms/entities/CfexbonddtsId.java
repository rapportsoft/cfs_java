package com.cwms.entities;

import java.io.Serializable;

public class CfexbonddtsId implements Serializable {

	public String companyId;
	public String branchId;
	public String nocTransId;
	public String inBondingId;
	public String exBondingId;
	public int srNo;
	public CfexbonddtsId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CfexbonddtsId(String companyId, String branchId, String nocTransId, String inBondingId, String exBondingId,
			int srNo) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.inBondingId = inBondingId;
		this.exBondingId = exBondingId;
		this.srNo = srNo;
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
	public String getNocTransId() {
		return nocTransId;
	}
	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}
	public String getInBondingId() {
		return inBondingId;
	}
	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}
	public String getExBondingId() {
		return exBondingId;
	}
	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
}
