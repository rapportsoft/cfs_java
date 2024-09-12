

package com.cwms.entities;

import java.io.Serializable;


import java.io.Serializable;
import java.util.Objects;

public class CfbondinsbalID implements Serializable {

    private String companyId;
    private String branchId;
    private int srNo;
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
	public CfbondinsbalID(String companyId, String branchId, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.srNo = srNo;
	}
	public CfbondinsbalID() {
	
	}
	@Override
	public String toString() {
		return "CfbondinsbalID [companyId=" + companyId + ", branchId=" + branchId + ", srNo=" + srNo + "]";
	}

    
}

