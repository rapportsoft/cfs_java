package com.cwms.entities;

import java.io.Serializable;

public class VesselId implements Serializable {

	private String companyId;
	private String branchId;
	private String vesselId;
	public VesselId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VesselId(String companyId, String branchId, String vesselId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.vesselId = vesselId;
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
	public String getVesselId() {
		return vesselId;
	}
	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}
	
	
}
