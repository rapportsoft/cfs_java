package com.cwms.entities;

import java.io.Serializable;

public class ScanDetaild implements Serializable {

	private String companyId;
	private String branchId;
	private String transId;
	private String containerNo;
	public ScanDetaild() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ScanDetaild(String companyId, String branchId, String transId, String containerNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.transId = transId;
		this.containerNo = containerNo;
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
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	
	
}
