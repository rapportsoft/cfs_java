package com.cwms.entities;

import java.io.Serializable;

public class ExportGatePassId implements Serializable {

	private String companyId;
	private String branchId;
	private String gatePassId;
	private String profitcentreId;
	private int srNo;
	public ExportGatePassId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExportGatePassId(String companyId, String branchId, String gatePassId, String profitcentreId, int srNo) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassId = gatePassId;
		this.profitcentreId = profitcentreId;
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
	public String getGatePassId() {
		return gatePassId;
	}
	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
}
