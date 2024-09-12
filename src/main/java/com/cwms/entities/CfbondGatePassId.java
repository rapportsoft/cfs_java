package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class CfbondGatePassId implements Serializable {


    private String companyId;


    private String branchId;


    private String finYear;


    private String gatePassId;


    private int srNo;

    // Constructors, getters, setters, equals, and hashCode methods

    public CfbondGatePassId() {
    }

    public CfbondGatePassId(String companyId, String branchId, String finYear, String gatePassId, int srNo) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.finYear = finYear;
        this.gatePassId = gatePassId;
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

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	@Override
	public String toString() {
		return "CfbondGatePassId [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", gatePassId=" + gatePassId + ", srNo=" + srNo + "]";
	}

    // Getters and Setters

    // equals and hashCode methods
}
