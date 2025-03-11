package com.cwms.entities;

import java.io.Serializable;


public class GeneralJobDetailId implements Serializable {

	private String companyId;
	private String branchId;
	private String jobTransId;
	private String jobDtlTransId;
	private String jobNo;
	
	
	public GeneralJobDetailId(String companyId, String branchId, String jobTransId, String jobDtlTransId,
			String jobNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobDtlTransId = jobDtlTransId;
		this.jobNo = jobNo;
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
	public String getJobDtlTransId() {
		return jobDtlTransId;
	}
	public void setJobDtlTransId(String jobDtlTransId) {
		this.jobDtlTransId = jobDtlTransId;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public GeneralJobDetailId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
