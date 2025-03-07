package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class IgmServiceDtlDocId implements Serializable {

	public String companyId;
	public String branchId;
	private String igmTransId;
	private String igmNo;
	private String igmLineNo;
	private int srNo;
	protected IgmServiceDtlDocId() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected IgmServiceDtlDocId(String companyId, String branchId, String igmTransId, String igmNo, String igmLineNo,
			int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
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
	public String getIgmTransId() {
		return igmTransId;
	}
	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}
	public String getIgmNo() {
		return igmNo;
	}
	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}
	public String getIgmLineNo() {
		return igmLineNo;
	}
	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
}
