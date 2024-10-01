package com.cwms.entities;

public class CommonGatePassId {
	private String companyId;
	private String branchId;
	private String jobTransId;
	private String gatePassId;
	private String containerNo;
	private int srNo;
	public CommonGatePassId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommonGatePassId(String companyId, String branchId, String jobTransId, String gatePassId, String containerNo,
			int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.gatePassId = gatePassId;
		this.containerNo = containerNo;
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
	public String getJobTransId() {
		return jobTransId;
	}
	public void setJobTransId(String jobTransId) {
		this.jobTransId = jobTransId;
	}
	public String getGatePassId() {
		return gatePassId;
	}
	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
}
