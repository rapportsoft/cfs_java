package com.cwms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name="cfservicemapping")
@IdClass(ServiceMappingId.class)
public class ServiceMapping {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="BranchId",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Profitcentre_Id",length = 20)
	private String profitcentreId;
	
	@Id
	@Column(name="Invoice_Type",length = 20)
	private String invoiceType;
	
	@Id
	@Column(name="Service_Id",length = 20)
	private String serviceId;
	
	@Id
	@Column(name="Container_Size",length = 2)
	private String containerSize;
	
	@Id
	@Column(name="Container_Type",length = 3)
	private String containerType;
	
	@Id
	@Column(name="Type_of_Container",length = 20)
	private String typeOfContainer;
	
	@Id
	@Column(name="Gate_Out_Type",length = 5)
	private String gateOutType;
	
	@Id
	@Column(name="Scanner_Type",length = 20)
	private String scannerType;
	
	
	@Column(name="Service_Short_desc",length = 100)
	private String serviceShortDesc;
	
	@Column(name="Special_Delivery",length = 5)
	private String specialDelivery;
	
	@Column(name="Storage_Type",length = 10)
	private String storageType;
	
	
	@Column(name="Equipment",length = 10)
	private String equipment;
	
	@Column(name="Seal_Type",length = 20)
	private String sealType;
	
	@Column(name="Exam_Type",length = 20)
	private String examType;
	
	@Column(name="Status",length = 1)
	private String status;
	
	@Column(name="Examination",length = 10)
	private String examination = "EXAM";
	
	@Column(name="BL_Applicable_Flag",length = 1)
	private String blApplicableFlag = "N";
	
	@Column(name="Secondary_Item",length = 1)
	private String secondaryItem = "N";

	public ServiceMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ServiceMapping(String companyId, String branchId, String profitcentreId, String invoiceType,
			String serviceId, String containerSize, String containerType, String typeOfContainer, String gateOutType,
			String scannerType, String serviceShortDesc, String specialDelivery, String storageType, String equipment,
			String sealType, String examType, String status, String examination, String blApplicableFlag,
			String secondaryItem) {
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
		this.serviceShortDesc = serviceShortDesc;
		this.specialDelivery = specialDelivery;
		this.storageType = storageType;
		this.equipment = equipment;
		this.sealType = sealType;
		this.examType = examType;
		this.status = status;
		this.examination = examination;
		this.blApplicableFlag = blApplicableFlag;
		this.secondaryItem = secondaryItem;
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

	public String getServiceShortDesc() {
		return serviceShortDesc;
	}

	public void setServiceShortDesc(String serviceShortDesc) {
		this.serviceShortDesc = serviceShortDesc;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getSealType() {
		return sealType;
	}

	public void setSealType(String sealType) {
		this.sealType = sealType;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	public String getBlApplicableFlag() {
		return blApplicableFlag;
	}

	public void setBlApplicableFlag(String blApplicableFlag) {
		this.blApplicableFlag = blApplicableFlag;
	}

	public String getSecondaryItem() {
		return secondaryItem;
	}

	public void setSecondaryItem(String secondaryItem) {
		this.secondaryItem = secondaryItem;
	}


	public String getSpecialDelivery() {
		return specialDelivery;
	}


	public void setSpecialDelivery(String specialDelivery) {
		this.specialDelivery = specialDelivery;
	}
	
	
	
}
