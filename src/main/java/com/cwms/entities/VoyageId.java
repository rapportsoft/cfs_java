package com.cwms.entities;

import java.io.Serializable;

public class VoyageId implements Serializable {

	private String companyId;
	private String branchId;
	private String pod;
	private String pol;
	private String vesselCode;
	private String voyageNo;
	private String viaNo;
	private String igmNo;
	private int srNo;
	
	
	
	
	public VoyageId() {
		super();
		// TODO Auto-generated constructor stub
	}




	public VoyageId(String companyId, String branchId, String pod, String pol, String vesselCode, String voyageNo,
			String viaNo, String igmNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.pod = pod;
		this.pol = pol;
		this.vesselCode = vesselCode;
		this.voyageNo = voyageNo;
		this.viaNo = viaNo;
		this.igmNo = igmNo;
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




	public String getPod() {
		return pod;
	}




	public void setPod(String pod) {
		this.pod = pod;
	}




	public String getPol() {
		return pol;
	}




	public void setPol(String pol) {
		this.pol = pol;
	}




	public String getVesselCode() {
		return vesselCode;
	}




	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}




	public String getVoyageNo() {
		return voyageNo;
	}




	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}




	public String getViaNo() {
		return viaNo;
	}




	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}




	public String getIgmNo() {
		return igmNo;
	}




	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}




	public int getSrNo() {
		return srNo;
	}




	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}




	
	
	
}
