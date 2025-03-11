package com.cwms.entities;

import java.io.Serializable;

public class GeneralDeliveryCrgId implements Serializable {

    private String companyId;

    private String branchId;

    private String deliveryId;

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

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public GeneralDeliveryCrgId(String companyId, String branchId, String deliveryId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.deliveryId = deliveryId;
	}

	public GeneralDeliveryCrgId() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
	
}
