package com.cwms.entities;

import java.io.Serializable;

public class BufferWorkOrderId implements Serializable {

	private String companyId;
	private String branchId;
	private String woNo;
	private String profitcentreId;
	private int srNo;
	private String containerNo;
	public BufferWorkOrderId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BufferWorkOrderId(String companyId, String branchId, String woNo, String profitcentreId, int srNo,
			String containerNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.woNo = woNo;
		this.profitcentreId = profitcentreId;
		this.srNo = srNo;
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
	public String getWoNo() {
		return woNo;
	}
	public void setWoNo(String woNo) {
		this.woNo = woNo;
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
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	
	
	
}
