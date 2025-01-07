package com.cwms.entities;

import java.io.Serializable;

public class ServiceMappingId implements Serializable {

	private String companyId;
	private String branchId;
	private String profitcentreId;
	private String invoiceType;
	private String serviceId;
	private String containerSize;
	private String containerType;
	private String typeOfContainer;
	private String gateOutType;
	private String scannerType;
	public ServiceMappingId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceMappingId(String companyId, String branchId, String profitcentreId, String invoiceType,
			String serviceId, String containerSize, String containerType, String typeOfContainer, String gateOutType,
			String scannerType) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.profitcentreId = profitcentreId;
		this.invoiceType = invoiceType;
		this.serviceId = serviceId;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.typeOfContainer = typeOfContainer;
		this.gateOutType = gateOutType;
		this.scannerType = scannerType;
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
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getContainerSize() {
		return containerSize;
	}
	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public String getTypeOfContainer() {
		return typeOfContainer;
	}
	public void setTypeOfContainer(String typeOfContainer) {
		this.typeOfContainer = typeOfContainer;
	}
	public String getGateOutType() {
		return gateOutType;
	}
	public void setGateOutType(String gateOutType) {
		this.gateOutType = gateOutType;
	}
	public String getScannerType() {
		return scannerType;
	}
	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}
	
	
	
}
