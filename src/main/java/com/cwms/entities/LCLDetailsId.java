package com.cwms.entities;

import java.io.Serializable;

public class LCLDetailsId implements Serializable {

	public String companyId;
	public String branchId;
	public String lclTransId;
	public String igmLineNo;
	public String igmNo;
	public String subLineNo;
	public int srNo;
	public LCLDetailsId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LCLDetailsId(String companyId, String branchId, String lclTransId, String igmLineNo, String igmNo,
			String subLineNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.lclTransId = lclTransId;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.subLineNo = subLineNo;
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
	public String getLclTransId() {
		return lclTransId;
	}
	public void setLclTransId(String lclTransId) {
		this.lclTransId = lclTransId;
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
	public String getSubLineNo() {
		return subLineNo;
	}
	public void setSubLineNo(String subLineNo) {
		this.subLineNo = subLineNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
}
