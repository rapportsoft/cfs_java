package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "cfsbldetails")
@IdClass(CFSBLDetailsId.class)
public class CFSBLDetails {

	@Id
	@Column(name = "Company_Id", length = 6)
	public String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	public String branchId;

	@Id
	@Column(name = "BL_Trans_Id", length = 10)
	public String blTransId;

	@Id
	@Column(name = "Igm_Line_No", length = 7)
	public String igmLineNo;

	@Id
	@Column(name = "Igm_No", length = 10)
	public String igmNo;

	@Id
	@Column(name = "Container_No", length = 11)
	public String containerNo;

	@Id
	@Column(name = "Sr_No")
	public int srNo;

	@Column(name = "Up_Tariff_No", length = 10)
	public String upTariffNo;

	@Column(name = "Up_Tariff_Amnd_No", length = 5)
	public String upTariffAmndNo;

	@Column(name = "Container_Size", length = 6)
	public String containerSize;

	@Column(name = "Container_Type", length = 6)
	public String containerType;

	@Column(name = "Cycle", length = 10)
	public String cycle;

	@Column(name = "Delivery_Mode", length = 10)
	public String deliveryMode;

	@Column(name = "Special_Delivery", length = 10)
	public String specialDelivery;

	@Column(name = "Act_Delivery_Mode", length = 10)
	public String actDeliveryMode;

	@Column(name = "Service_Id", length = 10)
	public String serviceId;

	@Column(name = "Service_Desc", length = 100)
	public String serviceDesc;

	@Column(name = "Service_Amt", precision = 16, scale = 3)
	public BigDecimal serviceAmt;
	
	@Column(name = "Status", length = 1)
	public String status;
	
	@Column(name = "Profitcentre_Id", length = 20)
	private String profitcentreId;
	
	@Column(name = "Party_Id", length = 10)
	public String partyId;
	
	@Column(name = "File_Upload_Path", length = 300)
	public String fileUploadPath;
	
	@Column(name = "Created_By", length = 10)
	public String createdBy;
	
	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date createdDate;

	public CFSBLDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	




	protected CFSBLDetails(String companyId, String branchId, String blTransId, String igmLineNo, String igmNo,
			String containerNo, int srNo, String upTariffNo, String upTariffAmndNo, String containerSize,
			String containerType, String cycle, String deliveryMode, String specialDelivery, String actDeliveryMode,
			String serviceId, String serviceDesc, BigDecimal serviceAmt, String status, String profitcentreId,
			String partyId, String fileUploadPath, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.blTransId = blTransId;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.containerNo = containerNo;
		this.srNo = srNo;
		this.upTariffNo = upTariffNo;
		this.upTariffAmndNo = upTariffAmndNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.cycle = cycle;
		this.deliveryMode = deliveryMode;
		this.specialDelivery = specialDelivery;
		this.actDeliveryMode = actDeliveryMode;
		this.serviceId = serviceId;
		this.serviceDesc = serviceDesc;
		this.serviceAmt = serviceAmt;
		this.status = status;
		this.profitcentreId = profitcentreId;
		this.partyId = partyId;
		this.fileUploadPath = fileUploadPath;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}







	public String getFileUploadPath() {
		return fileUploadPath;
	}







	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}







	public String getPartyId() {
		return partyId;
	}






	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}






	public String getProfitcentreId() {
		return profitcentreId;
	}


	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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

	public String getBlTransId() {
		return blTransId;
	}

	public void setBlTransId(String blTransId) {
		this.blTransId = blTransId;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
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

	public String getUpTariffNo() {
		return upTariffNo;
	}

	public void setUpTariffNo(String upTariffNo) {
		this.upTariffNo = upTariffNo;
	}

	public String getUpTariffAmndNo() {
		return upTariffAmndNo;
	}

	public void setUpTariffAmndNo(String upTariffAmndNo) {
		this.upTariffAmndNo = upTariffAmndNo;
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

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getSpecialDelivery() {
		return specialDelivery;
	}

	public void setSpecialDelivery(String specialDelivery) {
		this.specialDelivery = specialDelivery;
	}

	public String getActDeliveryMode() {
		return actDeliveryMode;
	}

	public void setActDeliveryMode(String actDeliveryMode) {
		this.actDeliveryMode = actDeliveryMode;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceDesc() {
		return serviceDesc;
	}

	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	public BigDecimal getServiceAmt() {
		return serviceAmt;
	}

	public void setServiceAmt(BigDecimal serviceAmt) {
		this.serviceAmt = serviceAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
}
