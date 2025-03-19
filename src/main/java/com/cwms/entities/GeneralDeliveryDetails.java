package com.cwms.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "generaldeliverydetails")
@IdClass(GeneralDeliveryDetailsId.class)
public class GeneralDeliveryDetails {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Id
    @Column(name = "Delivery_Id", length = 10, nullable = false)
    private String deliveryId;

    @Id
    @Column(name = "Receiving_Id", length = 10, nullable = false)
    private String receivingId;

    @Id
    @Column(name = "Sr_No", precision = 8, scale = 0, nullable = false)
    private int srNo;

    @Column(name = "Delivery_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDate;

    @Column(name = "jon_dtl_trans_id", length = 10)
    private String jonDtlTransId;

    @Column(name = "commodity_description", length = 250)
    private String commodityDescription;

    @Column(name = "commodity_id", length = 10)
    private String commodityId;

    @Column(name = "act_commodity_id", length = 10)
    private String actCommodityId;

    @Column(name = "type_of_package", length = 14)
    private String typeOfPackage;

    @Column(name = "Deposit_No", length = 10, nullable = false)
    private String depositNo;

    @Column(name = "Receiving_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivingDate;

    @Column(name = "receiving_packages", precision = 8, scale = 0)
    private BigDecimal receivingPackages =BigDecimal.ZERO;

    @Column(name = "receiving_weight", precision = 8, scale = 0)
    private BigDecimal receivingWeight=BigDecimal.ZERO;

    @Column(name = "gate_in_packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal gateInPackages;

    @Column(name = "gate_in_weight", precision = 8, scale = 0, nullable = true)
    private BigDecimal gateInWeight;
    
    @Column(name = "Delivered_Packages", precision = 8, scale = 0, nullable = false)
    private BigDecimal deliveredPackages=BigDecimal.ZERO;


    @Column(name = "Delivered_Weight", precision = 8, scale = 0, nullable = false)
    private BigDecimal deliveredWeight=BigDecimal.ZERO;


    @Column(name = "Gate_Pass_Packages", precision = 8, scale = 0, nullable = false)
    private BigDecimal gatePassPackages=BigDecimal.ZERO;


    @Column(name = "Gate_Pass_Weight", precision = 8, scale = 0, nullable = false)
    private BigDecimal gatePassWeight=BigDecimal.ZERO;


    @Column(name = "Remaining_Packages", precision = 8, scale = 0, nullable = false)
    private BigDecimal remainingPackages=BigDecimal.ZERO;

    @Column(name = "Remaining_Weight", precision = 8, scale = 0, nullable = false)
    private BigDecimal remainingWeight=BigDecimal.ZERO;


    @Column(name = "Created_By", length = 10, nullable = false)
    private String createdBy;

    @Column(name = "Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Column(name = "Status", length = 1, nullable = false)
    private String status;

    @Column(name = "Container_No", length = 15, nullable = false)
    private String containerNo;
    
    @Column(name = "Container_Size", length = 10, nullable = true)
    private String containerSize;

    @Column(name = "Container_Type", length = 10, nullable = true)
    private String containerType;
    
    @Column(name = "Job_No", length = 10, nullable = true)
    private String jobNo;
    
    @Column(name = "Job_trans_id", length = 10, nullable = true)
    private String jobTransId;

    @Column(name = "BOE_No", length = 20, nullable = true)
    private String boeNo;

    @Column(name = "BOE_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date boeDate;
    
    
	public GeneralDeliveryDetails(String companyId, String branchId, String deliveryId, String receivingId, int srNo,
			Date deliveryDate, String jonDtlTransId, String commodityDescription, String commodityId,
			String actCommodityId, String typeOfPackage, String depositNo, Date receivingDate,
			BigDecimal receivingPackages, BigDecimal receivingWeight, BigDecimal gateInPackages,
			BigDecimal gateInWeight, BigDecimal deliveredPackages, BigDecimal deliveredWeight,
			BigDecimal gatePassPackages, BigDecimal gatePassWeight, BigDecimal remainingPackages,
			BigDecimal remainingWeight, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, String status, String containerNo, String containerSize,
			String containerType, String jobNo, String jobTransId,String boeNo,Date boeDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.deliveryId = deliveryId;
		this.receivingId = receivingId;
		this.srNo = srNo;
		this.deliveryDate = deliveryDate;
		this.jonDtlTransId = jonDtlTransId;
		this.commodityDescription = commodityDescription;
		this.commodityId = commodityId;
		this.actCommodityId = actCommodityId;
		this.typeOfPackage = typeOfPackage;
		this.depositNo = depositNo;
		this.receivingDate = receivingDate;
		this.receivingPackages = receivingPackages;
		this.receivingWeight = receivingWeight;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.deliveredPackages = deliveredPackages;
		this.deliveredWeight = deliveredWeight;
		this.gatePassPackages = gatePassPackages;
		this.gatePassWeight = gatePassWeight;
		this.remainingPackages = remainingPackages;
		this.remainingWeight = remainingWeight;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
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

	public GeneralDeliveryDetails() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getJonDtlTransId() {
		return jonDtlTransId;
	}

	public void setJonDtlTransId(String jonDtlTransId) {
		this.jonDtlTransId = jonDtlTransId;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getActCommodityId() {
		return actCommodityId;
	}

	public void setActCommodityId(String actCommodityId) {
		this.actCommodityId = actCommodityId;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public Date getReceivingDate() {
		return receivingDate;
	}

	public void setReceivingDate(Date receivingDate) {
		this.receivingDate = receivingDate;
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

	public BigDecimal getGatePassPackages() {
		return gatePassPackages;
	}

	public void setGatePassPackages(BigDecimal gatePassPackages) {
		this.gatePassPackages = gatePassPackages;
	}

	public BigDecimal getGatePassWeight() {
		return gatePassWeight;
	}

	public void setGatePassWeight(BigDecimal gatePassWeight) {
		this.gatePassWeight = gatePassWeight;
	}

	public BigDecimal getRemainingPackages() {
		return remainingPackages;
	}

	public void setRemainingPackages(BigDecimal remainingPackages) {
		this.remainingPackages = remainingPackages;
	}

	public BigDecimal getRemainingWeight() {
		return remainingWeight;
	}

	public void setRemainingWeight(BigDecimal remainingWeight) {
		this.remainingWeight = remainingWeight;
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

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public Date getBoeDate() {
		return boeDate;
	}

	public void setBoeDate(Date boeDate) {
		this.boeDate = boeDate;
	}

	@Override
	public String toString() {
		return "GeneralDeliveryDetails [companyId=" + companyId + ", branchId=" + branchId + ", deliveryId="
				+ deliveryId + ", receivingId=" + receivingId + ", srNo=" + srNo + ", deliveryDate=" + deliveryDate
				+ ", jonDtlTransId=" + jonDtlTransId + ", commodityDescription=" + commodityDescription
				+ ", commodityId=" + commodityId + ", actCommodityId=" + actCommodityId + ", typeOfPackage="
				+ typeOfPackage + ", depositNo=" + depositNo + ", receivingDate=" + receivingDate
				+ ", receivingPackages=" + receivingPackages + ", receivingWeight=" + receivingWeight
				+ ", gateInPackages=" + gateInPackages + ", gateInWeight=" + gateInWeight + ", deliveredPackages="
				+ deliveredPackages + ", deliveredWeight=" + deliveredWeight + ", gatePassPackages=" + gatePassPackages
				+ ", gatePassWeight=" + gatePassWeight + ", remainingPackages=" + remainingPackages
				+ ", remainingWeight=" + remainingWeight + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", status=" + status + ", containerNo=" + containerNo
				+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", jobNo=" + jobNo
				+ ", jobTransId=" + jobTransId + "]";
	}

	
    
	public GeneralDeliveryDetails(String deliveryId, String receivingId,String boeNo,String depositNo) {
		super();
		this.deliveryId = deliveryId;
		this.receivingId = receivingId;
		this.boeNo = boeNo;
		this.depositNo = depositNo;
	}
    
	
	
}
