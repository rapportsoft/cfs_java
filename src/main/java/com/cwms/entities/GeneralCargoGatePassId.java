package com.cwms.entities;

import java.io.Serializable;

public class GeneralCargoGatePassId implements Serializable {

	private String companyId;

    private String branchId;

    private String gatePassId;

    private int srNo;

    private String depositNo;

//    private String boeNo;

	public GeneralCargoGatePassId(String companyId, String branchId, String gatePassId, int srNo, String depositNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.depositNo = depositNo;
//		this.boeNo = boeNo;
	}

	public GeneralCargoGatePassId() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}


	@Override
	public String toString() {
		return "GeneralCargoGatePassId [companyId=" + companyId + ", branchId=" + branchId + ", gatePassId="
				+ gatePassId + ", srNo=" + srNo + ", depositNo=" + depositNo + ", depositNo=" + depositNo + "]";
	}
    
    
    
    
    
    
    
   
    
    
}
