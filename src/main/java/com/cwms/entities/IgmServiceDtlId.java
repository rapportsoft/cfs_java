package com.cwms.entities;

import java.io.Serializable;

public class IgmServiceDtlId implements Serializable {

	private String companyId;
	private String branchId;
	private String igmTransId;
	private String igmNo;
	private String igmLineNo;
	private String containerNo;
	private String serviceId;
	public IgmServiceDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IgmServiceDtlId(String companyId, String branchId, String igmTransId, String igmNo, String igmLineNo,
			String containerNo, String serviceId) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.serviceId = serviceId;
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
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	
	
}
