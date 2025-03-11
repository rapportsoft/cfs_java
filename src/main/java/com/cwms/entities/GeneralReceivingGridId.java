package com.cwms.entities;

import java.io.Serializable;

public class GeneralReceivingGridId implements Serializable{

	private String companyId;
    private String branchId;
    private String receivingId;
//    private String gateInId;
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
	public String getReceivingId() {
		return receivingId;
	}
	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
	}
//	public String getGateInId() {
//		return gateInId;
//	}
//	public void setGateInId(String gateInId) {
//		this.gateInId = gateInId;
//	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public GeneralReceivingGridId(String companyId, String branchId, String receivingId, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
//		this.gateInId = gateInId;
		this.srNo = srNo;
	}
	public GeneralReceivingGridId() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
