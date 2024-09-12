package com.cwms.entities;

import java.io.Serializable;

public class CfExBondCrgId implements Serializable {

    private String companyId;
    
    private String branchId;

    private String finYear;

    private String exBondingId;

	public CfExBondCrgId(String companyId, String branchId, String finYear, String exBondingId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.exBondingId = exBondingId;
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

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public CfExBondCrgId() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    
    
    
    
    
    
    
}
