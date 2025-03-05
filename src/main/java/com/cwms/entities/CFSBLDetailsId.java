package com.cwms.entities;

import java.io.Serializable;

public class CFSBLDetailsId implements Serializable {

	public String companyId;

	public String branchId;

	public String blTransId;
	
	public String igmLineNo;

	public String igmNo;

	public String containerNo;

	public int srNo;

	protected CFSBLDetailsId() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected CFSBLDetailsId(String companyId, String branchId, String blTransId, String igmLineNo, String igmNo,
			String containerNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.blTransId = blTransId;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
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

	public String getBlTransId() {
		return blTransId;
	}

	public void setBlTransId(String blTransId) {
		this.blTransId = blTransId;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
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
