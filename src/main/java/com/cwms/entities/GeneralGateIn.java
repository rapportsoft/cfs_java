package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "generalgatein")
@IdClass(GeneralGateInId.class)
public class GeneralGateIn {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Id
    @Column(name = "Gate_In_Id", length = 10, nullable = true)
    private String gateInId;

    @Id
    @Column(name = "Sr_No", nullable = true)
    private int srNo;

    @Column(name = "Gate_No", length = 6, nullable = true)
    private String gateNo;

    @Column(name = "Gate_In_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date gateInDate;

    @Column(name = "BOE_No", length = 15, nullable = true)
    private String boeNo;

    @Column(name = "BOE_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date boeDate;

    @Column(name = "Invoice_No", length = 15, nullable = true)
    private String invoiceNo;

    @Column(name = "Invoice_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date invoiceDate;

    @Column(name = "Doc_Type", length = 20, nullable = true)
    private String docType;

    @Column(name = "Profitcentre_Id", length = 6, nullable = true)
    private String profitcentreId;

    @Column(name = "Process_Id", length = 10, nullable = true)
    private String processId;

    @Column(name = "Container_No", length = 20, nullable = true)
    private String containerNo;

    @Column(name = "Container_Size", length = 6, nullable = true)
    private String containerSize;

    @Column(name = "Container_Type", length = 6, nullable = true)
    private String containerType;

    @Column(name = "Container_Status", length = 10, nullable = true)
    private String containerStatus;

    @Column(name = "Container_Seal_No", length = 15, nullable = true)
    private String containerSealNo;

    @Column(name = "Customs_Seal_No", length = 15, nullable = true)
    private String customsSealNo;

    @Column(name = "ISO_Code", length = 4, nullable = true)
    private String isoCode;

    @Column(name = "Gross_Weight", precision = 15, scale = 3)
    private BigDecimal grossWeight;

    @Column(name = "EIR_Gross_Weight", precision = 15, scale = 3)
    private BigDecimal eirGrossWeight;

    @Column(name = "Tare_Weight", precision = 15, scale = 3)
    private BigDecimal tareWeight;

    @Column(name = "Cargo_Weight", precision = 15, scale = 3)
    private BigDecimal cargoWeight;

    @Column(name = "Over_Dimension", length = 1, nullable = true)
    private String overDimension;

    @Column(name = "Hazardous", length = 1, nullable = true)
    private String hazardous;

    @Column(name = "CHA", length = 6, nullable = true)
    private String cha;

    @Column(name = "Party_Sr_No")
    private Integer partySrNo;

    @Column(name = "Importer_Id", length = 6, nullable = true)
    private String importerId;

    @Column(name = "Importer_Name", length = 60, nullable = true)
    private String importerName;

    @Column(name = "Address_1", length = 252, nullable = true)
    private String address1;

    @Column(name = "Address_2", length = 252, nullable = true)
    private String address2;

    @Column(name = "Address_3", length = 252, nullable = true)
    private String address3;

    @Column(name = "Commodity_Description", length = 250, nullable = true)
    private String commodityDescription;

    @Column(name = "No_Of_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal noOfPackages;

    @Column(name = "Refer", length = 1, nullable = true)
    private String refer;

    @Column(name = "Receiving_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal receivingPackages =BigDecimal.ZERO;
    
    
    @Column(name = "Receiving_Weight", precision = 18, scale = 0, nullable = true)
    private BigDecimal receivingWeight =BigDecimal.ZERO;

    @Column(name = "Short_Qty", precision = 8, scale = 0)
    private BigDecimal shortQty;

    @Column(name = "Receiving_Done", length = 1)
    private String receivingDone;

    @Column(name = "Vehicle_No", length = 15, nullable = true)
    private String vehicleNo;

    @Column(name = "Driver_Name", length = 50, nullable = true)
    private String driverName;

    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

    @Column(name = "Status", length = 1, nullable = true)
    private String status;

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

    @Column(name = "MODEL", length = 20, nullable = true)
    private String model;

    @Column(name = "CFS", length = 30, nullable = true)
    private String cfs;

    @Column(name = "Handling_Person", length = 100, nullable = true)
    private String handlingPerson;

    @Column(name = "Transporter", length = 25, nullable = true)
    private String transporter;

    @Column(name = "Transporter_Name", length = 255, nullable = true)
    private String transporterName;

    @Column(name = "Commodity_Id", length = 10, nullable = true)
    private String commodityId;
    
    @Column(name = "Act_Commodity_Id", length = 10, nullable = true)
    private String actCommodityId;

    @Column(name = "lr_no", length = 20)
    private String lrNo;

    @Column(name = "Gate_In_Packages", precision = 12, scale = 3, nullable = true)
    private BigDecimal gateInPackages;

    
    
    
    @Column(name = "Type_Of_Package", length = 10, nullable = true)
    private String typeOfPackage;
    
    @Column(name = "Job_Trans_Id", length = 10, nullable = true)
    private String jobTransId;

    @Column(name = "Job_No", length = 20)
    private String jobNo;

    @Column(name = "Job_trans_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date jobTransDate;

    @Column(name = "Job_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date jobDate;

    @Column(name = "JOB_NOP", precision = 12, scale = 3, nullable = true)
    private BigDecimal jobNop;

    @Column(name = "JOB_GWT", precision = 16, scale = 3)
    private BigDecimal jobGwt;

    @Column(name = "Vehicle_Type", length = 30, nullable = true)
    private String vehicleType;
    

   @Transient
    private BigDecimal area;
    
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

	
	
	

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getReceivingWeight() {
		return receivingWeight;
	}

	public void setReceivingWeight(BigDecimal receivingWeight) {
		this.receivingWeight = receivingWeight;
	}

	// Class Constructor
	public GeneralGateIn(String companyId, String branchId, String gateInId, int srNo, String gateNo, Date gateInDate,
			String boeNo, Date boeDate, String invoiceNo, Date invoiceDate, String docType, String profitcentreId,
			String processId, String containerNo, String containerSize, String containerType, String containerStatus,
			String containerSealNo, String customsSealNo, String isoCode, BigDecimal grossWeight,
			BigDecimal eirGrossWeight, BigDecimal tareWeight, BigDecimal cargoWeight, String overDimension,
			String hazardous, String cha, Integer partySrNo, String importerId, String importerName, String address1,
			String address2, String address3, String commodityDescription, BigDecimal noOfPackages, String refer,
			BigDecimal receivingPackages,BigDecimal receivingWeight, BigDecimal shortQty, String receivingDone, String vehicleNo,
			String driverName, String comments, String status, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String model, String cfs, String handlingPerson,
			String transporter, String transporterName, String commodityId, String actCommodityId, String lrNo,
			BigDecimal gateInPackages, String typeOfPackage, String jobTransId, String jobNo, Date jobTransDate,
			Date jobDate, BigDecimal jobNop, BigDecimal jobGwt, String vehicleType) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.srNo = srNo;
		this.gateNo = gateNo;
		this.gateInDate = gateInDate;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.docType = docType;
		this.profitcentreId = profitcentreId;
		this.processId = processId;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.customsSealNo = customsSealNo;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.eirGrossWeight = eirGrossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.overDimension = overDimension;
		this.hazardous = hazardous;
		this.cha = cha;
		this.partySrNo = partySrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.commodityDescription = commodityDescription;
		this.noOfPackages = noOfPackages;
		this.refer = refer;
		this.receivingPackages = receivingPackages;
		this.receivingWeight=receivingWeight;
		this.shortQty = shortQty;
		this.receivingDone = receivingDone;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.model = model;
		this.cfs = cfs;
		this.handlingPerson = handlingPerson;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.commodityId = commodityId;
		this.actCommodityId = actCommodityId;
		this.lrNo = lrNo;
		this.gateInPackages = gateInPackages;
		this.typeOfPackage = typeOfPackage;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.jobNop = jobNop;
		this.jobGwt = jobGwt;
		this.vehicleType = vehicleType;
	}

	public GeneralGateIn() {
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

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getGateNo() {
		return gateNo;
	}

	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
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

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public String getContainerSealNo() {
		return containerSealNo;
	}

	public void setContainerSealNo(String containerSealNo) {
		this.containerSealNo = containerSealNo;
	}

	public String getCustomsSealNo() {
		return customsSealNo;
	}

	public void setCustomsSealNo(String customsSealNo) {
		this.customsSealNo = customsSealNo;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getEirGrossWeight() {
		return eirGrossWeight;
	}

	public void setEirGrossWeight(BigDecimal eirGrossWeight) {
		this.eirGrossWeight = eirGrossWeight;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
	}

	public BigDecimal getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(BigDecimal cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public String getOverDimension() {
		return overDimension;
	}

	public void setOverDimension(String overDimension) {
		this.overDimension = overDimension;
	}

	public String getHazardous() {
		return hazardous;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public Integer getPartySrNo() {
		return partySrNo;
	}

	public void setPartySrNo(Integer partySrNo) {
		this.partySrNo = partySrNo;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public BigDecimal getReceivingPackages() {
		return receivingPackages;
	}

	public void setReceivingPackages(BigDecimal receivingPackages) {
		this.receivingPackages = receivingPackages;
	}

	public BigDecimal getShortQty() {
		return shortQty;
	}

	public void setShortQty(BigDecimal shortQty) {
		this.shortQty = shortQty;
	}

	public String getReceivingDone() {
		return receivingDone;
	}

	public void setReceivingDone(String receivingDone) {
		this.receivingDone = receivingDone;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCfs() {
		return cfs;
	}

	public void setCfs(String cfs) {
		this.cfs = cfs;
	}

	public String getHandlingPerson() {
		return handlingPerson;
	}

	public void setHandlingPerson(String handlingPerson) {
		this.handlingPerson = handlingPerson;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}

	public String getJobTransId() {
		return jobTransId;
	}

	public void setJobTransId(String jobTransId) {
		this.jobTransId = jobTransId;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public Date getJobTransDate() {
		return jobTransDate;
	}

	public void setJobTransDate(Date jobTransDate) {
		this.jobTransDate = jobTransDate;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
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

	public void setJobGwt(BigDecimal jobGwt) {
		this.jobGwt = jobGwt;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public String toString() {
		return "GeneralGateIn [companyId=" + companyId + ", branchId=" + branchId + ", gateInId=" + gateInId + ", srNo="
				+ srNo + ", gateNo=" + gateNo + ", gateInDate=" + gateInDate + ", boeNo=" + boeNo + ", boeDate="
				+ boeDate + ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", docType=" + docType
				+ ", profitcentreId=" + profitcentreId + ", processId=" + processId + ", containerNo=" + containerNo
				+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", containerStatus="
				+ containerStatus + ", containerSealNo=" + containerSealNo + ", customsSealNo=" + customsSealNo
				+ ", isoCode=" + isoCode + ", grossWeight=" + grossWeight + ", eirGrossWeight=" + eirGrossWeight
				+ ", tareWeight=" + tareWeight + ", cargoWeight=" + cargoWeight + ", overDimension=" + overDimension
				+ ", hazardous=" + hazardous + ", cha=" + cha + ", partySrNo=" + partySrNo + ", importerId="
				+ importerId + ", importerName=" + importerName + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", commodityDescription=" + commodityDescription + ", noOfPackages="
				+ noOfPackages + ", refer=" + refer + ", receivingPackages=" + receivingPackages + "receivingWeight  "+receivingWeight + ", shortQty="
				+ shortQty + ", receivingDone=" + receivingDone + ", vehicleNo=" + vehicleNo + ", driverName="
				+ driverName + ", comments=" + comments + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", model=" + model + ", cfs=" + cfs
				+ ", handlingPerson=" + handlingPerson + ", transporter=" + transporter + ", transporterName="
				+ transporterName + ", commodityId=" + commodityId + ", lrNo=" + lrNo + ", gateInPackages="
				+ gateInPackages + ", jobTransId=" + jobTransId + ", jobNo=" + jobNo + ", jobTransDate=" + jobTransDate
				+ ", jobDate=" + jobDate + ", jobNop=" + jobNop + ", jobGwt=" + jobGwt + ", vehicleType=" + vehicleType
				+ "]";
	}

	// Class Constructor to get data on screen after save and using radio button please check for any queries 
	public GeneralGateIn(String companyId, String branchId, String gateInId, int srNo, Date gateInDate, String boeNo,
			Date boeDate, String profitcentreId, String containerNo, String containerSize, String containerType,
			String isoCode, BigDecimal grossWeight, BigDecimal tareWeight, BigDecimal cargoWeight, String hazardous,
			String cha, String importerId, String importerName, String address1, String address2, String address3,
			String commodityDescription, BigDecimal noOfPackages, String vehicleNo, String comments, String status,
			String createdBy, Date createdDate, String approvedBy, String handlingPerson, String transporterName,
			String commodityId, String lrNo, BigDecimal gateInPackages, String typeOfPackage, String jobTransId,
			String jobNo, Date jobTransDate, Date jobDate, BigDecimal jobNop, BigDecimal jobGwt,String editedBy,String actCommodityId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.srNo = srNo;
		this.gateInDate = gateInDate;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.profitcentreId = profitcentreId;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.hazardous = hazardous;
		this.cha = cha;
		this.importerId = importerId;
		this.importerName = importerName;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.commodityDescription = commodityDescription;
		this.noOfPackages = noOfPackages;
		this.vehicleNo = vehicleNo;
		this.comments = comments;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.handlingPerson = handlingPerson;
		this.transporterName = transporterName;
		this.commodityId = commodityId;
		this.lrNo = lrNo;
		this.gateInPackages = gateInPackages;
		this.typeOfPackage = typeOfPackage;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.jobNop = jobNop;
		this.jobGwt = jobGwt;
		this.editedBy=editedBy;
		this.actCommodityId=actCommodityId;
	}

	
	
	public GeneralGateIn(String companyId, String branchId, String gateInId, int srNo, String gateNo, Date gateInDate,
			String boeNo, Date boeDate, String profitcentreId, String containerNo, String containerSize,
			String containerType, String containerStatus, String isoCode, BigDecimal grossWeight, BigDecimal tareWeight,
			BigDecimal cargoWeight, String cha, String importerId, String importerName, String commodityDescription,
			BigDecimal noOfPackages, BigDecimal receivingPackages, BigDecimal receivingWeight, String receivingDone,
			String vehicleNo, String handlingPerson, String transporter, String transporterName, String commodityId,
			String actCommodityId, String lrNo, BigDecimal gateInPackages, String typeOfPackage, String jobTransId,
			String jobNo, Date jobTransDate, Date jobDate, BigDecimal jobNop, BigDecimal jobGwt,String editedBy,BigDecimal area) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.srNo = srNo;
		this.gateNo = gateNo;
		this.gateInDate = gateInDate;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.profitcentreId = profitcentreId;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerStatus = containerStatus;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.cha = cha;
		this.importerId = importerId;
		this.importerName = importerName;
		this.commodityDescription = commodityDescription;
		this.noOfPackages = noOfPackages;
		this.receivingPackages = receivingPackages;
		this.receivingWeight = receivingWeight;
		this.receivingDone = receivingDone;
		this.vehicleNo = vehicleNo;
		this.handlingPerson = handlingPerson;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.commodityId = commodityId;
		this.actCommodityId = actCommodityId;
		this.lrNo = lrNo;
		this.gateInPackages = gateInPackages;
		this.typeOfPackage = typeOfPackage;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.jobNop = jobNop;
		this.jobGwt = jobGwt;
		this.editedBy=editedBy;
		this.area=area;
	}

	
	// for gate in print 
	public GeneralGateIn(String gateInId, String boeNo, String jobTransId, String jobNo) {
		super();
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
	}

	public GeneralGateIn(String companyId, String branchId, String gateInId, int srNo, String gateNo, Date gateInDate,
			String boeNo, Date boeDate, String containerNo, BigDecimal grossWeight, String cha, String importerId,
			String importerName, String address1, String address2, String address3, String commodityDescription,
			BigDecimal noOfPackages, String vehicleNo, String driverName, String comments, String status,
			String createdBy, String transporter, String transporterName, String commodityId, String actCommodityId,
			String lrNo, BigDecimal gateInPackages, String typeOfPackage, String jobTransId, String jobNo,
			Date jobTransDate, Date jobDate, BigDecimal jobNop, BigDecimal jobGwt, String vehicleType) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.srNo = srNo;
		this.gateNo = gateNo;
		this.gateInDate = gateInDate;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.containerNo = containerNo;
		this.grossWeight = grossWeight;
		this.cha = cha;
		this.importerId = importerId;
		this.importerName = importerName;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.commodityDescription = commodityDescription;
		this.noOfPackages = noOfPackages;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.status = status;
		this.createdBy = createdBy;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.commodityId = commodityId;
		this.actCommodityId = actCommodityId;
		this.lrNo = lrNo;
		this.gateInPackages = gateInPackages;
		this.typeOfPackage = typeOfPackage;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.jobNop = jobNop;
		this.jobGwt = jobGwt;
		this.vehicleType = vehicleType;
	}
    
    
    
    
    
    
    
    
    
	
	
	
	
}