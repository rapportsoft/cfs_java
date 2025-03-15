package com.cwms.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "generalcargogatepass")
@IdClass(GeneralCargoGatePassId.class)
public class GeneralGatePassCargo {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Id
    @Column(name = "Gate_Pass_Id", length = 10, nullable = true)
    private String gatePassId;

    @Column(name = "Gate_Pass_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date gatePassDate;

    @Id
    @Column(name = "Sr_No", length = 100, nullable = true)
    private int srNo;

    @Id
    @Column(name = "Deposit_No", length = 17, nullable = true)
    private String depositNo;
    
    @Column(name = "Receiving_Id", length = 10, nullable = true)
	private String receivingId;

    @Column(name = "Job_No", length = 17, nullable = true)
    private String jobNo;
    
    @Column(name = "Job_trans_id", length = 17, nullable = true)
    private String jobTransId;
    
    @Column(name = "commodity_id", length = 17, nullable = true)
    private String commodityId;
    
    @Column(name = "act_commodity_id", length = 17, nullable = true)
    private String actCommodityId;
    
    @Column(name = "type_of_package", length = 17, nullable = true)
    private String typeOfPackage;
    
    @Column(name = "commodity_description", length = 250, nullable = true)
    private String commodityDescription;
    
    @Column(name = "Profitcentre_Id", length = 100, nullable = true)
    private String profitCentreId;

    @Column(name = "Delivery_Id", length = 10, nullable = true)
    private String deliveryId;

    @Column(name = "Invoice_No", length = 10, nullable = true)
    private String invoiceNo;

    @Column(name = "Invoice_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "Gate_Out_Id", length = 10, nullable = true)
    private String gateOutId;

    @Column(name = "Gate_Out_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateOutDate;

    @Column(name = "Shift", length = 6, nullable = true)
    private String shift;

    @Column(name = "Trans_Type", length = 10, nullable = true)
    private String transType;

    @Column(name = "Importer_Name", length = 90, nullable = true)
    private String importerName;

    @Column(name = "Container_No", length = 11, nullable = true)
    private String containerNo;

    @Column(name = "BOE_No", length = 20, nullable = true)
    private String boeNo;

    @Column(name = "BOE_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date boeDate;

    @Column(name = "Gross_Weight", precision = 12, scale = 3, nullable = true)
    private BigDecimal grossWeight;

    @Column(name = "receiving_packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal receivingPackages;
    
    @Column(name = "receiving_weight", precision = 8, scale = 0, nullable = true)
    private BigDecimal receivingWeight;

    @Column(name = "Delivered_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal deliveredPackages;
    
    @Column(name = "Delivered_Weight", precision = 18, scale = 2, nullable = true)
    private BigDecimal deliveredWeight;

    @Column(name = "Transporter_Name", length = 255, nullable = true)
    private String transporterName;

    @Column(name = "Vehicle_No", length = 15, nullable = true)
    private String vehicleNo;

    @Column(name = "Driver_Name", length = 50, nullable = true)
    private String driverName;

    @Column(name = "Comments", length = 250, nullable = true)
    private String comments;

    @Column(name = "Gate_Pass_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal gatePassPackages;

    @Column(name = "Gate_Pass_Weight", precision = 8, scale = 0, nullable = true)
    private BigDecimal gatePassWeight;

    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;

    @Column(name = "Created_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10, nullable = true)
    private String editedBy;

    @Column(name = "Edited_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10, nullable = true)
    private String approvedBy;

    @Column(name = "Approved_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Column(name = "BOTPL_Recpt_No", length = 30, nullable = true)
    private String botplRecptNo;

    @Column(name = "Prev_Qty_Taken_Out", precision = 6, scale = 0, nullable = true)
    private BigDecimal prevQtyTakenOut;

	public GeneralGatePassCargo(String companyId, String branchId, String gatePassId, Date gatePassDate, int srNo,
			String depositNo, String receivingId, String jobNo, String jobTransId, String commodityId,
			String actCommodityId, String typeOfPackage, String commodityDescription, String profitCentreId,
			String deliveryId, String invoiceNo, Date invoiceDate, String gateOutId, Date gateOutDate, String shift,
			String transType, String importerName, String containerNo, String boeNo, Date boeDate,
			BigDecimal grossWeight, BigDecimal receivingPackages, BigDecimal receivingWeight,
			BigDecimal deliveredPackages, BigDecimal deliveredWeight, String transporterName, String vehicleNo,
			String driverName, String comments, BigDecimal gatePassPackages, BigDecimal gatePassWeight, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String botplRecptNo, BigDecimal prevQtyTakenOut) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassId = gatePassId;
		this.gatePassDate = gatePassDate;
		this.srNo = srNo;
		this.depositNo = depositNo;
		this.receivingId = receivingId;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.commodityId = commodityId;
		this.actCommodityId = actCommodityId;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.profitCentreId = profitCentreId;
		this.deliveryId = deliveryId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.shift = shift;
		this.transType = transType;
		this.importerName = importerName;
		this.containerNo = containerNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.grossWeight = grossWeight;
		this.receivingPackages = receivingPackages;
		this.receivingWeight = receivingWeight;
		this.deliveredPackages = deliveredPackages;
		this.deliveredWeight = deliveredWeight;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.gatePassPackages = gatePassPackages;
		this.gatePassWeight = gatePassWeight;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.botplRecptNo = botplRecptNo;
		this.prevQtyTakenOut = prevQtyTakenOut;
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

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public String getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
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

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
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

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getBotplRecptNo() {
		return botplRecptNo;
	}

	public void setBotplRecptNo(String botplRecptNo) {
		this.botplRecptNo = botplRecptNo;
	}

	public BigDecimal getPrevQtyTakenOut() {
		return prevQtyTakenOut;
	}

	public void setPrevQtyTakenOut(BigDecimal prevQtyTakenOut) {
		this.prevQtyTakenOut = prevQtyTakenOut;
	}

	public GeneralGatePassCargo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "GeneralGatePassCargo [companyId=" + companyId + ", branchId=" + branchId + ", gatePassId=" + gatePassId
				+ ", gatePassDate=" + gatePassDate + ", srNo=" + srNo + ", depositNo=" + depositNo + ", receivingId="
				+ receivingId + ", jobNo=" + jobNo + ", jobTransId=" + jobTransId + ", commodityId=" + commodityId
				+ ", actCommodityId=" + actCommodityId + ", typeOfPackage=" + typeOfPackage + ", commodityDescription="
				+ commodityDescription + ", profitCentreId=" + profitCentreId + ", deliveryId=" + deliveryId
				+ ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", gateOutId=" + gateOutId
				+ ", gateOutDate=" + gateOutDate + ", shift=" + shift + ", transType=" + transType + ", importerName="
				+ importerName + ", containerNo=" + containerNo + ", boeNo=" + boeNo + ", boeDate=" + boeDate
				+ ", grossWeight=" + grossWeight + ", receivingPackages=" + receivingPackages + ", receivingWeight="
				+ receivingWeight + ", deliveredPackages=" + deliveredPackages + ", deliveredWeight=" + deliveredWeight
				+ ", transporterName=" + transporterName + ", vehicleNo=" + vehicleNo + ", driverName=" + driverName
				+ ", comments=" + comments + ", gatePassPackages=" + gatePassPackages + ", gatePassWeight="
				+ gatePassWeight + ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", botplRecptNo=" + botplRecptNo + ", prevQtyTakenOut="
				+ prevQtyTakenOut + "]";
	}

	public GeneralGatePassCargo(String companyId, String branchId, String gatePassId, Date gatePassDate, int srNo,
			String receivingId, String deliveryId, String gateOutId, Date gateOutDate, String importerName,
			String transporterName, String vehicleNo, BigDecimal gatePassPackages, BigDecimal gatePassWeight,
			String status,String boeNo,String createdBy,String approvedBy,String transType,String shift,String comments,String driverName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassId = gatePassId;
		this.gatePassDate = gatePassDate;
		this.srNo = srNo;
		this.receivingId = receivingId;
		this.deliveryId = deliveryId;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.importerName = importerName;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.gatePassPackages = gatePassPackages;
		this.gatePassWeight = gatePassWeight;
		this.status = status;
		this.boeNo=boeNo;
		this.createdBy = createdBy;
		this.approvedBy=approvedBy;
		this.transType=transType;
		this.shift=shift;
		this.comments=comments;
		this.driverName=driverName;
	}

	public GeneralGatePassCargo(String companyId, String branchId, String gatePassId, Date gatePassDate, int srNo,
			String depositNo, String receivingId, String jobNo, String jobTransId, String commodityId,
			String actCommodityId, String typeOfPackage, String commodityDescription, String profitCentreId,
			String deliveryId, String gateOutId, Date gateOutDate, String shift, String transType, String importerName,
			String containerNo, String boeNo, Date boeDate, BigDecimal grossWeight, BigDecimal receivingPackages,
			BigDecimal receivingWeight, BigDecimal deliveredPackages, BigDecimal deliveredWeight,
			String transporterName, String vehicleNo, String driverName, String comments, BigDecimal gatePassPackages,
			BigDecimal gatePassWeight, String status, String createdBy, Date createdDate, String approvedBy,
			Date approvedDate, String botplRecptNo, BigDecimal prevQtyTakenOut) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassId = gatePassId;
		this.gatePassDate = gatePassDate;
		this.srNo = srNo;
		this.depositNo = depositNo;
		this.receivingId = receivingId;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.commodityId = commodityId;
		this.actCommodityId = actCommodityId;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.profitCentreId = profitCentreId;
		this.deliveryId = deliveryId;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.shift = shift;
		this.transType = transType;
		this.importerName = importerName;
		this.containerNo = containerNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.grossWeight = grossWeight;
		this.receivingPackages = receivingPackages;
		this.receivingWeight = receivingWeight;
		this.deliveredPackages = deliveredPackages;
		this.deliveredWeight = deliveredWeight;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.gatePassPackages = gatePassPackages;
		this.gatePassWeight = gatePassWeight;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.botplRecptNo = botplRecptNo;
		this.prevQtyTakenOut = prevQtyTakenOut;
	}

	
    
    
    
    
    
    
    
   
	
	
	
}
