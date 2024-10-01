package com.cwms.entities;

import java.io.Serializable;

public class EmptyJobOrderId implements Serializable {

	private String companyId;
	private String branchId;
	private String jobTransId;
	private String erpDocRefNo;
	private String docRefNo;
	private int srNo;
	public EmptyJobOrderId() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public EmptyJobOrderId(String companyId, String branchId, String jobTransId, String erpDocRefNo, String docRefNo,
			int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
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
	public String getErpDocRefNo() {
		return erpDocRefNo;
	}
	public void setErpDocRefNo(String erpDocRefNo) {
		this.erpDocRefNo = erpDocRefNo;
	}
	
	public String getDocRefNo() {
		return docRefNo;
	}



	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}



	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
}
