package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ScmtrJobTrackId implements Serializable {

	public String companyId;
	public String branchId;
	public String sbTransId;
	public String sbNo;
	public String containerNo;
	public String jobNo;
	public String stuffTallyId;
	public ScmtrJobTrackId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ScmtrJobTrackId(String companyId, String branchId, String sbTransId, String sbNo, String containerNo,
			String jobNo, String stuffTallyId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.jobNo = jobNo;
		this.stuffTallyId = stuffTallyId;
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
	public String getSbTransId() {
		return sbTransId;
	}
	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}
	public String getSbNo() {
		return sbNo;
	}
	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getJobNo() {
		return jobNo;
	}
	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}
	public String getStuffTallyId() {
		return stuffTallyId;
	}
	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}
	
	
	
}
