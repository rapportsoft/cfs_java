package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class SplitTallyPkgId implements Serializable {

	public String companyId;
	public String branchId;
	public String stuffTallyId;
	public String sbTransId;
	public String sbNo;
	public String containerNo;
	public int srNo;
	public SplitTallyPkgId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SplitTallyPkgId(String companyId, String branchId, String stuffTallyId, String sbTransId, String sbNo,
			String containerNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.stuffTallyId = stuffTallyId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
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
	public String getStuffTallyId() {
		return stuffTallyId;
	}
	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
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
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
}
