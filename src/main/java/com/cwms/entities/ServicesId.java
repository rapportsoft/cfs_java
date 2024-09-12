package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class ServicesId implements Serializable
{
	private String serviceId;

    
    private String companyId;
    
   
    private String branchId;


	public ServicesId(String serviceId, String companyId, String branchId) {
		super();
		this.serviceId = serviceId;
		this.companyId = companyId;
		this.branchId = branchId;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
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


	public ServicesId() {
		super();
		// TODO Auto-generated constructor stub
	}

}