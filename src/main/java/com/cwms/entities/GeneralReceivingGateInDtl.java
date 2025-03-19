package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "generalreceivinggateindtl")
@IdClass(GeneralReceivingGateInDtlId.class)
public class GeneralReceivingGateInDtl {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Id
    @Column(name = "Sr_No", length = 10, nullable = false)
    private int srNo;

    @Id
    @Column(name = "Receiving_Id", length = 10, nullable = false)
    private String receivingId;

    @Id
    @Column(name = "Gate_In_Id", length = 10, nullable = false)
    private String gateInId;

    @Column(name = "Gate_In_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date gateInDate;

    @Column(name = "Container_No", length = 14, nullable = true)
    private String containerNo;

    @Column(name = "Container_Size", length = 10, nullable = true)
    private String containerSize;

    @Column(name = "Container_Type", length = 10, nullable = true)
    private String containerType;

    @Column(name = "Vehicle_No", length = 15, nullable = true)
    private String vehicleNo;
    
    @Column(name = "Job_packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal jobNop = BigDecimal.ZERO;

    @Column(name = "Job_Gwt", precision = 8, scale = 0, nullable = true)
    private BigDecimal jobGwt = BigDecimal.ZERO;

    @Column(name = "gate_in_packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal gateInPackages = BigDecimal.ZERO;

    @Column(name = "gate_in_weight", precision = 8, scale = 0, nullable = true)
    private BigDecimal gateInWeight = BigDecimal.ZERO;

    @Column(name = "reciving_packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal receivingPackages = BigDecimal.ZERO;

    @Column(name = "reciving_weight", precision = 8, scale = 0, nullable = true)
    private BigDecimal receivingWeight = BigDecimal.ZERO;

    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Delivered_Packages", length = 50, nullable = true)
    private BigDecimal deliveredPackages = BigDecimal.ZERO;

    @Column(name = "Delivered_Weight", precision = 8, scale = 0, nullable = true)
    private BigDecimal deliveredWeight = BigDecimal.ZERO;

    @Column(name = "Commodity_Description", length = 250, nullable = true)
    private String commodityDescription;

    @Column(name = "type_Of_Package", length = 14, nullable = true)
    private String typeOfPackage;

    @Column(name = "Commodity_Id", length = 10, nullable = true)
    private String commodityId;

    @Column(name = "Jon_Dtl_Trans_Id", length = 10, nullable = true)
    private String jobDtlTransId;
    
    @Column(name = "Job_No", length = 10, nullable = true)
    private String jobNo;
    
    @Column(name = "Job_trans_id", length = 10, nullable = true)
    private String jobTransId;
    
    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;
    
    @Column(name = "Created_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate;
    
    @Column(name = "Edited_By", length = 10)
    private String editedBy;
    
    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate;
    
    @Column(name = "Approved_By", length = 10)
    private String approvedBy;
    
    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date approvedDate;

    @Column(name = "Act_Commodity_Id", length = 10, nullable = true)
    private String actCommodityId;
    
    @Column(name = "Deposit_No", length = 15, nullable = true)
	private String depositNo;
    
   @Transient
    private BigDecimal grossWeight;




	
	
	
	
	
	public GeneralReceivingGateInDtl(String companyId, String branchId, int srNo, String receivingId, String gateInId,
		Date gateInDate, String containerNo, String containerSize, String containerType, String vehicleNo,
		BigDecimal jobNop, BigDecimal jobGwt, BigDecimal gateInPackages, BigDecimal gateInWeight,
		BigDecimal receivingPackages, BigDecimal receivingWeight, String status, BigDecimal deliveredPackages,
		BigDecimal deliveredWeight, String commodityDescription, String typeOfPackage, String commodityId,
		String jobDtlTransId, String jobNo, String jobTransId, String createdBy, Date createdDate, String editedBy,
		Date editedDate, String approvedBy, Date approvedDate, String actCommodityId,String depositNo) {
	super();
	this.companyId = companyId;
	this.branchId = branchId;
	this.srNo = srNo;
	this.receivingId = receivingId;
	this.gateInId = gateInId;
	this.gateInDate = gateInDate;
	this.containerNo = containerNo;
	this.containerSize = containerSize;
	this.containerType = containerType;
	this.vehicleNo = vehicleNo;
	this.jobNop = jobNop;
	this.jobGwt = jobGwt;
	this.gateInPackages = gateInPackages;
	this.gateInWeight = gateInWeight;
	this.receivingPackages = receivingPackages;
	this.receivingWeight = receivingWeight;
	this.status = status;
	this.deliveredPackages = deliveredPackages;
	this.deliveredWeight = deliveredWeight;
	this.commodityDescription = commodityDescription;
	this.typeOfPackage = typeOfPackage;
	this.commodityId = commodityId;
	this.jobDtlTransId = jobDtlTransId;
	this.jobNo = jobNo;
	this.jobTransId = jobTransId;
	this.createdBy = createdBy;
	this.createdDate = createdDate;
	this.editedBy = editedBy;
	this.editedDate = editedDate;
	this.approvedBy = approvedBy;
	this.approvedDate = approvedDate;
	this.actCommodityId = actCommodityId;
	this.depositNo = depositNo;
}






	public String getDepositNo() {
		return depositNo;
	}






	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}






	public String getJobNo() {
		return jobNo;
	}






	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}






	public String getJobTransId() {
		return jobTransId;
	}






	public void setJobTransId(String jobTransId) {
		this.jobTransId = jobTransId;
	}






	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getActCommodityId() {
		return actCommodityId;
	}

	public void setActCommodityId(String actCommodityId) {
		this.actCommodityId = actCommodityId;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public BigDecimal getJobNop() {
		return jobNop;
	}

	public void setJobNop(BigDecimal jobNop) {
		this.jobNop = jobNop;
	}

	public BigDecimal getJobGwt() {
		return jobGwt;
	}

	public void setJobGwt(BigDecimal jobWeight) {
		this.jobGwt = jobWeight;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}

	public BigDecimal getGateInWeight() {
		return gateInWeight;
	}

	public void setGateInWeight(BigDecimal gateInWeight) {
		this.gateInWeight = gateInWeight;
	}

	public BigDecimal getReceivingPackages() {
		return receivingPackages;
	}

	public void setReceivingPackages(BigDecimal receivingPackages) {
		this.receivingPackages = receivingPackages;
	}

	public BigDecimal getReceivingWeight() {
		return receivingWeight;
	}

	public void setReceivingWeight(BigDecimal receivingWeight) {
		this.receivingWeight = receivingWeight;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public BigDecimal getDeliveredPackages() {
		return deliveredPackages;
	}






	public void setDeliveredPackages(BigDecimal deliveredPackages) {
		this.deliveredPackages = deliveredPackages;
	}






	public BigDecimal getDeliveredWeight() {
		return deliveredWeight;
	}






	public void setDeliveredWeight(BigDecimal deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}






	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getJobDtlTransId() {
		return jobDtlTransId;
	}

	public void setJobDtlTransId(String jobDtlTransId) {
		this.jobDtlTransId = jobDtlTransId;
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

	public GeneralReceivingGateInDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "GeneralReceivingGateInDtl [companyId=" + companyId + ", branchId=" + branchId + ", srNo=" + srNo
				+ ", receivingId=" + receivingId + ", gateInId=" + gateInId + ", gateInDate=" + gateInDate
				+ ", containerNo=" + containerNo + ", containerSize=" + containerSize + ", containerType="
				+ containerType + ", vehicleNo=" + vehicleNo + ", jobNop=" + jobNop + ", jobGwt=" + jobGwt
				+ ", gateInPackages=" + gateInPackages + ", gateInWeight=" + gateInWeight + ", receivingPackages="
				+ receivingPackages + ", receivingWeight=" + receivingWeight + ", status=" + status + ", deliveredPackages="
				+ deliveredPackages + ", deliveredWeight=" + deliveredWeight + ", commodityDescription=" + commodityDescription
				+ ", typeOfPackage=" + typeOfPackage + ", commodityId=" + commodityId + ", jobDtlTransId="
				+ jobDtlTransId + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", actCommodityId=" + actCommodityId + "]";
	}

	
	
	// to get data on delivery screen please check for any query or future change  
	public GeneralReceivingGateInDtl(String companyId, String branchId, int srNo, String receivingId, String gateInId,
			Date gateInDate, String containerNo, String containerSize, String containerType, String vehicleNo,
			BigDecimal jobNop, BigDecimal jobGwt, BigDecimal gateInPackages, BigDecimal gateInWeight,
			BigDecimal receivingPackages, BigDecimal receivingWeight, String status, BigDecimal deliveredPackages,
			BigDecimal deliveredWeight, String commodityDescription, String typeOfPackage, String commodityId,
			String jobDtlTransId, String actCommodityId, String jobNo, String jobTransId,Date createdDate,String depositNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.srNo = srNo;
		this.receivingId = receivingId;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.vehicleNo = vehicleNo;
		this.jobNop = jobNop;
		this.jobGwt = jobGwt;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.receivingPackages = receivingPackages;
		this.receivingWeight = receivingWeight;
		this.status = status;
		this.deliveredPackages = deliveredPackages;
		this.deliveredWeight = deliveredWeight;
		this.commodityDescription = commodityDescription;
		this.typeOfPackage = typeOfPackage;
		this.commodityId = commodityId;
		this.jobDtlTransId = jobDtlTransId;
		this.actCommodityId = actCommodityId;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.createdDate=createdDate;
		this.depositNo=depositNo;
	}

	

	
    
}
