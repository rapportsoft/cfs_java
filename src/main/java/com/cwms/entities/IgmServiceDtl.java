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
import jakarta.persistence.Transient;

@Entity
@Table(name = "cfigmservicedtl")
@IdClass(IgmServiceDtlId.class)
public class IgmServiceDtl {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;

	@Id
	@Column(name = "IGM_No", length = 10)
	private String igmNo;

	@Id
	@Column(name = "IGM_Line_No", length = 10)
	private String igmLineNo;

	@Id
	@Column(name = "Container_No", length = 11)
	private String containerNo;

	@Id
	@Column(name = "Service_Id", length = 10)
	private String serviceId;

	@Column(name = "Be_No", length = 20)
	private String beNo;

	@Column(name = "Bl_No", length = 40)
	private String blNo;

	@Column(name = "Container_Size", length = 4)
	private String containerSize;

	@Column(name = "Container_Type", length = 4)
	private String containerType;

	@Column(name = "CFS_Tariff_No", length = 20)
	private String cfsTariffNo;

	@Column(name = "CFS_Amnd_No", length = 20)
	private String cfsAmndNo;

	@Column(name = "Percentage", precision = 16, scale = 3)
	private BigDecimal percentage;

	@Column(name = "Amount", precision = 16, scale = 3)
	private BigDecimal amount;

	@Column(name = "Remark", length = 250)
	private String remark;

	@Column(name = "MPercentage", precision = 16, scale = 3)
	private BigDecimal mPercentage;

	@Column(name = "MAmount", precision = 16, scale = 3)
	private BigDecimal mAmount;

	@Column(name = "MRemark", length = 250)
	private String mRemark;

	@Column(name = "Assesment_Id", length = 20)
	private String assessmentId;

	@Column(name = "Created_By", length = 10)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_Date")
	private Date createdDate;

	@Column(name = "Edited_By", length = 10)
	private String editedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Edited_Date")
	private Date editedDate;

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Approved_Date")
	private Date approvedDate;
	
	@Column(name="Status",length = 1)
	private String status;
	
	@Transient
	private String serviceDesc;

	public IgmServiceDtl() {
		super();
		// TODO Auto-generated constructor stub
	}



	protected IgmServiceDtl(String companyId, String branchId, String igmTransId, String igmNo, String igmLineNo,
			String containerNo, String serviceId, String beNo, String blNo, String containerSize, String containerType,
			String cfsTariffNo, String cfsAmndNo, BigDecimal percentage, BigDecimal amount, String remark,
			BigDecimal mPercentage, BigDecimal mAmount, String mRemark, String assessmentId, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status,
			String serviceDesc) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.serviceId = serviceId;
		this.beNo = beNo;
		this.blNo = blNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.percentage = percentage;
		this.amount = amount;
		this.remark = remark;
		this.mPercentage = mPercentage;
		this.mAmount = mAmount;
		this.mRemark = mRemark;
		this.assessmentId = assessmentId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.serviceDesc = serviceDesc;
	}

	


	public String getServiceDesc() {
		return serviceDesc;
	}



	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
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

	public String getBeNo() {
		return beNo;
	}

	public void setBeNo(String beNo) {
		this.beNo = beNo;
	}

	public String getBlNo() {
		return blNo;
	}

	public void setBlNo(String blNo) {
		this.blNo = blNo;
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

	public String getCfsTariffNo() {
		return cfsTariffNo;
	}

	public void setCfsTariffNo(String cfsTariffNo) {
		this.cfsTariffNo = cfsTariffNo;
	}

	public String getCfsAmndNo() {
		return cfsAmndNo;
	}

	public void setCfsAmndNo(String cfsAmndNo) {
		this.cfsAmndNo = cfsAmndNo;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getmPercentage() {
		return mPercentage;
	}

	public void setmPercentage(BigDecimal mPercentage) {
		this.mPercentage = mPercentage;
	}

	public BigDecimal getmAmount() {
		return mAmount;
	}

	public void setmAmount(BigDecimal mAmount) {
		this.mAmount = mAmount;
	}

	public String getmRemark() {
		return mRemark;
	}

	public void setmRemark(String mRemark) {
		this.mRemark = mRemark;
	}

	public String getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
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

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getEditedDate() {
		return editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
