package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "generalreceivingcrg")
@IdClass(GeneralReceivingCrgId.class)
public class GeneralReceivingCrg {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = true)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = true)
	private String branchId;

	@Id
	@Column(name = "Receiving_Id", length = 10, nullable = true)
	private String receivingId;

	@Id
	@Column(name = "Deposit_No", length = 15, nullable = true)
	private String depositNo;

	@Id
	@Column(name = "Importer_Name", length = 150, nullable = true)
	private String importerName;

	@Column(name = "On_Account_Of", length = 15, nullable = true)
	private String onAccountOf;

	@Column(name = "Receiving_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date receivingDate;

	@Column(name = "Job_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobDate;

	@Column(name = "Job_Trans_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobTransDate;

	@Column(name = "Profitcentre_Id", length = 6, nullable = true)
	private String profitcentreId;

	@Column(name = "Shift", length = 6)
	private String shift;

	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId;

	@Column(name = "Doc_Type", length = 15, nullable = true)
	private String docType;

	@Column(name = "BOE_No", length = 20, nullable = true)
	private String boeNo;

	@Column(name = "BOE_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date boeDate;

	@Column(name = "Invoice_No", length = 50, nullable = true)
	private String invoiceNo;

	@Column(name = "Invoice_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceDate;

	@Column(name = "Challan_No", length = 50, nullable = true)
	private String challanNo;

	@Column(name = "Challan_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date challanDate;

	@Column(name = "Process_Id", length = 15)
	private String processId;

	@Column(name = "CHA", length = 6, nullable = true)
	private String cha;

	@Column(name = "Importer_Id", length = 6, nullable = true)
	private String importerId;

	@Column(name = "Crossing_Cargo", length = 5, nullable = true)
	private String crossingCargo;

	@Column(name = "No_Of_Marks", length = 15, nullable = true)
	private String noOfMarks;

	@Column(name = "Gross_Weight", precision = 12, scale = 3, nullable = true)
	private BigDecimal grossWeight = BigDecimal.ZERO;

	@Column(name = "UOM", length = 6, nullable = true)
	private String uom;

	@Column(name = "Area_Occupied", precision = 8, scale = 3, nullable = true)
	private BigDecimal areaOccupied = BigDecimal.ZERO;

	@Column(name = "Area_Allocated", precision = 8, scale = 3, nullable = true)
	private BigDecimal areaAllocated = BigDecimal.ZERO;

	@Column(name = "Cargo_Condition", length = 6)
	private String cargoCondition;

	@Column(name = "Gate_In_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal gateInPackages = BigDecimal.ZERO;

	@Column(name = "Gate_In_Weight", precision = 18, scale = 0, nullable = true)
	private BigDecimal gateInWeight = BigDecimal.ZERO;

	@Column(name = "TOTAL_DELIVERED_PKG", precision = 8, scale = 0)
	private BigDecimal totalDeliveredPkg = BigDecimal.ZERO;

	@Column(name = "Received_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal receivedPackages = BigDecimal.ZERO;

	@Column(name = "Received_Weight", precision = 12, scale = 3, nullable = true)
	private BigDecimal receivedWeight = BigDecimal.ZERO;

	@Column(name = "Delivered_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal deliveredPackages = BigDecimal.ZERO;
	
	@Column(name = "Delivered_Weigh", precision = 12, scale = 3, nullable = true)
	private BigDecimal deliveredWeight = BigDecimal.ZERO;

	@Column(name = "Space_Allocated", length = 10, nullable = true)
	private String spaceAllocated;

	@Column(name = "Short_Qty", precision = 8, scale = 0)
	private BigDecimal shortQty = BigDecimal.ZERO;

	@Column(name = "Comments", length = 150, nullable = true)
	private String comments;

	@Column(name = "Invoice_Status", length = 1, nullable = true)
	private String invoiceStatus;

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

	@Column(name = "Remark", length = 250)
	private String remark;

	@Column(name = "Job_No", length = 30, nullable = true)
	private String jobNo;

	@Column(name = "Job_Trans_Id", length = 30, nullable = true)
	private String jobTransId;

	@Column(name = "MODEL", length = 30, nullable = true)
	private String model;

	@Column(name = "CFS", length = 60, nullable = true)
	private String cfs;

	@Column(name = "No_Of_20FT", length = 5, nullable = true)
	private String noOf20Ft;

	@Column(name = "No_Of_40FT", length = 5, nullable = true)
	private String noOf40Ft;

	@Column(name = "Assesment_Id", length = 10, nullable = true)
	private String assesmentId;

	@Column(name = "Handling_Equip1", length = 60, nullable = true)
	private String handlingEquip1;

	@Column(name = "Handling_Equip2", length = 60, nullable = true)
	private String handlingEquip2;

	@Column(name = "Handling_Equip3", length = 60, nullable = true)
	private String handlingEquip3;

	@Column(name = "Owner1", length = 60, nullable = true)
	private String owner1;

	@Column(name = "Owner2", length = 60, nullable = true)
	private String owner2;

	@Column(name = "Owner3", length = 60, nullable = true)
	private String owner3;

	@Column(name = "Source", length = 60, nullable = true)
	private String source;

	@Column(name = "Transporter", length = 25, nullable = true)
	private String transporter;

	@Column(name = "Transporter_Name", length = 255, nullable = true)
	private String transporterName;
	
	@Column(name = "Cargo_Value", precision = 15, scale = 2)
    private BigDecimal cargoValue = BigDecimal.ZERO;

    @Column(name = "Cargo_Duty", precision = 15, scale = 2)
    private BigDecimal cargoDuty = BigDecimal.ZERO;

	public GeneralReceivingCrg(String companyId, String branchId, String receivingId, String depositNo,
			String importerName, String onAccountOf, Date receivingDate, Date jobDate, Date jobTransDate,
			String profitcentreId, String shift, String gateInId, String docType, String boeNo, Date boeDate,
			String invoiceNo, Date invoiceDate, String challanNo, Date challanDate, String processId, String cha,
			String importerId, String crossingCargo, String noOfMarks, BigDecimal grossWeight, String uom,
			BigDecimal areaOccupied, BigDecimal areaAllocated, String cargoCondition, BigDecimal gateInPackages,
			BigDecimal gateInWeight, BigDecimal totalDeliveredPkg, BigDecimal receivedPackages,
			BigDecimal receivedWeight, BigDecimal deliveredPackages,BigDecimal deliveredWeight, String spaceAllocated, BigDecimal shortQty,
			String comments, String invoiceStatus, String status, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String remark, String jobNo, String jobTransId,
			String model, String cfs, String noOf20Ft, String noOf40Ft, String assesmentId, String handlingEquip1,
			String handlingEquip2, String handlingEquip3, String owner1, String owner2, String owner3, String source,
			String transporter, String transporterName,BigDecimal cargoValue,BigDecimal cargoDuty) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.depositNo = depositNo;
		this.importerName = importerName;
		this.onAccountOf = onAccountOf;
		this.receivingDate = receivingDate;
		this.jobDate = jobDate;
		this.jobTransDate = jobTransDate;
		this.profitcentreId = profitcentreId;
		this.shift = shift;
		this.gateInId = gateInId;
		this.docType = docType;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.processId = processId;
		this.cha = cha;
		this.importerId = importerId;
		this.crossingCargo = crossingCargo;
		this.noOfMarks = noOfMarks;
		this.grossWeight = grossWeight;
		this.uom = uom;
		this.areaOccupied = areaOccupied;
		this.areaAllocated = areaAllocated;
		this.cargoCondition = cargoCondition;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.totalDeliveredPkg = totalDeliveredPkg;
		this.receivedPackages = receivedPackages;
		this.receivedWeight = receivedWeight;
		this.deliveredPackages = deliveredPackages;
		this.deliveredWeight=deliveredWeight;
		this.spaceAllocated = spaceAllocated;
		this.shortQty = shortQty;
		this.comments = comments;
		this.invoiceStatus = invoiceStatus;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remark = remark;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.model = model;
		this.cfs = cfs;
		this.noOf20Ft = noOf20Ft;
		this.noOf40Ft = noOf40Ft;
		this.assesmentId = assesmentId;
		this.handlingEquip1 = handlingEquip1;
		this.handlingEquip2 = handlingEquip2;
		this.handlingEquip3 = handlingEquip3;
		this.owner1 = owner1;
		this.owner2 = owner2;
		this.owner3 = owner3;
		this.source = source;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.cargoValue=cargoValue;
		this.cargoDuty=cargoDuty;
		
	}

	public BigDecimal getDeliveredWeight() {
		return deliveredWeight;
	}

	public void setDeliveredWeight(BigDecimal deliveredWeight) {
		this.deliveredWeight = deliveredWeight;
	}

	public GeneralReceivingCrg() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	 // Getters and Setters
    public BigDecimal getCargoValue() {
        return cargoValue;
    }

    public void setCargoValue(BigDecimal cargoValue) {
        this.cargoValue = cargoValue;
    }
    
	 public BigDecimal getCargoDuty() {
	        return cargoDuty;
	    }

	    public void setCargoDuty(BigDecimal cargoDuty) {
	        this.cargoDuty = cargoDuty;
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

	public String getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
	}

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public Date getReceivingDate() {
		return receivingDate;
	}

	public void setReceivingDate(Date receivingDate) {
		this.receivingDate = receivingDate;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	public Date getJobTransDate() {
		return jobTransDate;
	}

	public void setJobTransDate(Date jobTransDate) {
		this.jobTransDate = jobTransDate;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Date getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public String getCrossingCargo() {
		return crossingCargo;
	}

	public void setCrossingCargo(String crossingCargo) {
		this.crossingCargo = crossingCargo;
	}

	public String getNoOfMarks() {
		return noOfMarks;
	}

	public void setNoOfMarks(String noOfMarks) {
		this.noOfMarks = noOfMarks;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
	}

	public BigDecimal getAreaAllocated() {
		return areaAllocated;
	}

	public void setAreaAllocated(BigDecimal areaAllocated) {
		this.areaAllocated = areaAllocated;
	}

	public String getCargoCondition() {
		return cargoCondition;
	}

	public void setCargoCondition(String cargoCondition) {
		this.cargoCondition = cargoCondition;
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

	public BigDecimal getTotalDeliveredPkg() {
		return totalDeliveredPkg;
	}

	public void setTotalDeliveredPkg(BigDecimal totalDeliveredPkg) {
		this.totalDeliveredPkg = totalDeliveredPkg;
	}

	public BigDecimal getReceivedPackages() {
		return receivedPackages;
	}

	public void setReceivedPackages(BigDecimal receivedPackages) {
		this.receivedPackages = receivedPackages;
	}

	public BigDecimal getReceivedWeight() {
		return receivedWeight;
	}

	public void setReceivedWeight(BigDecimal receivedWeight) {
		this.receivedWeight = receivedWeight;
	}

	public BigDecimal getDeliveredPackages() {
		return deliveredPackages;
	}

	public void setDeliveredPackages(BigDecimal deliveredPackages) {
		this.deliveredPackages = deliveredPackages;
	}

	public String getSpaceAllocated() {
		return spaceAllocated;
	}

	public void setSpaceAllocated(String spaceAllocated) {
		this.spaceAllocated = spaceAllocated;
	}

	public BigDecimal getShortQty() {
		return shortQty;
	}

	public void setShortQty(BigDecimal shortQty) {
		this.shortQty = shortQty;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getNoOf20Ft() {
		return noOf20Ft;
	}

	public void setNoOf20Ft(String noOf20Ft) {
		this.noOf20Ft = noOf20Ft;
	}

	public String getNoOf40Ft() {
		return noOf40Ft;
	}

	public void setNoOf40Ft(String noOf40Ft) {
		this.noOf40Ft = noOf40Ft;
	}

	public String getAssesmentId() {
		return assesmentId;
	}

	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}

	public String getHandlingEquip1() {
		return handlingEquip1;
	}

	public void setHandlingEquip1(String handlingEquip1) {
		this.handlingEquip1 = handlingEquip1;
	}

	public String getHandlingEquip2() {
		return handlingEquip2;
	}

	public void setHandlingEquip2(String handlingEquip2) {
		this.handlingEquip2 = handlingEquip2;
	}

	public String getHandlingEquip3() {
		return handlingEquip3;
	}

	public void setHandlingEquip3(String handlingEquip3) {
		this.handlingEquip3 = handlingEquip3;
	}

	public String getOwner1() {
		return owner1;
	}

	public void setOwner1(String owner1) {
		this.owner1 = owner1;
	}

	public String getOwner2() {
		return owner2;
	}

	public void setOwner2(String owner2) {
		this.owner2 = owner2;
	}

	public String getOwner3() {
		return owner3;
	}

	public void setOwner3(String owner3) {
		this.owner3 = owner3;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	@Override
	public String toString() {
		return "GeneralReceivingCrg [companyId=" + companyId + ", branchId=" + branchId + ", receivingId=" + receivingId
				+ ", depositNo=" + depositNo + ", importerName=" + importerName + ", onAccountOf=" + onAccountOf
				+ ", receivingDate=" + receivingDate + ", jobDate=" + jobDate + ", jobTransDate=" + jobTransDate
				+ ", profitcentreId=" + profitcentreId + ", shift=" + shift + ", gateInId=" + gateInId + ", docType="
				+ docType + ", boeNo=" + boeNo + ", boeDate=" + boeDate + ", invoiceNo=" + invoiceNo + ", invoiceDate="
				+ invoiceDate + ", challanNo=" + challanNo + ", challanDate=" + challanDate + ", processId=" + processId
				+ ", cha=" + cha + ", importerId=" + importerId + ", crossingCargo=" + crossingCargo + ", noOfMarks="
				+ noOfMarks + ", grossWeight=" + grossWeight + ", uom=" + uom + ", areaOccupied=" + areaOccupied
				+ ", areaAllocated=" + areaAllocated + ", cargoCondition=" + cargoCondition + ", gateInPackages="
				+ gateInPackages + ", gateInWeight=" + gateInWeight + ", totalDeliveredPkg=" + totalDeliveredPkg
				+ ", receivedPackages=" + receivedPackages + ", receivedWeight=" + receivedWeight
				+ ", deliveredPackages=" + deliveredPackages + ", spaceAllocated=" + spaceAllocated + ", shortQty="
				+ shortQty + ", comments=" + comments + ", invoiceStatus=" + invoiceStatus + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", remark=" + remark + ", jobNo=" + jobNo + ", jobTransId=" + jobTransId + ", model=" + model
				+ ", cfs=" + cfs + ", noOf20Ft=" + noOf20Ft + ", noOf40Ft=" + noOf40Ft + ", assesmentId=" + assesmentId
				+ ", handlingEquip1=" + handlingEquip1 + ", handlingEquip2=" + handlingEquip2 + ", handlingEquip3="
				+ handlingEquip3 + ", owner1=" + owner1 + ", owner2=" + owner2 + ", owner3=" + owner3 + ", source="
				+ source + ", transporter=" + transporter + ", transporterName=" + transporterName + "]";
	}

	public GeneralReceivingCrg(String companyId, String branchId, String receivingId, String depositNo,
			String importerName, BigDecimal areaOccupied, String cargoCondition, BigDecimal gateInPackages,
			BigDecimal gateInWeight, BigDecimal receivedPackages, BigDecimal receivedWeight, String spaceAllocated,
			String status, String jobNo, String jobTransId,String boeNo,Date boeDate,Date receivingDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.depositNo = depositNo;
		this.importerName = importerName;
		this.areaOccupied = areaOccupied;
		this.cargoCondition = cargoCondition;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.receivedPackages = receivedPackages;
		this.receivedWeight = receivedWeight;
		this.spaceAllocated = spaceAllocated;
		this.status = status;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.receivingDate=receivingDate;
	}

	// to get data after save using radio button please check for any query
	public GeneralReceivingCrg(String companyId, String branchId, String receivingId, String depositNo,
			String importerName,Date receivingDate, Date jobDate, Date jobTransDate,
			String profitcentreId, String gateInId, String boeNo, Date boeDate,
			String invoiceNo, Date invoiceDate, String challanNo, Date challanDate,String cha,
			String importerId, String crossingCargo, String noOfMarks, BigDecimal grossWeight, String uom,
			BigDecimal areaOccupied, BigDecimal areaAllocated, String cargoCondition, BigDecimal gateInPackages,
			BigDecimal gateInWeight, BigDecimal totalDeliveredPkg, BigDecimal receivedPackages,
			BigDecimal receivedWeight, BigDecimal deliveredPackages, String spaceAllocated, BigDecimal shortQty,
			String comments, String invoiceStatus, String status, String createdBy, Date createdDate, String editedBy,
			String approvedBy, Date approvedDate, String remark, String jobNo, String jobTransId,
			String noOf20Ft, String noOf40Ft, String assesmentId,String transporterName,BigDecimal cargoValue,BigDecimal cargoDuty, String handlingEquip1,
			String handlingEquip2, String handlingEquip3) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.depositNo = depositNo;
		this.importerName = importerName;
		this.receivingDate = receivingDate;
		this.jobDate = jobDate;
		this.jobTransDate = jobTransDate;
		this.profitcentreId = profitcentreId;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.challanNo = challanNo;
		this.challanDate = challanDate;
		this.cha = cha;
		this.importerId = importerId;
		this.crossingCargo = crossingCargo;
		this.noOfMarks = noOfMarks;
		this.grossWeight = grossWeight;
		this.uom = uom;
		this.areaOccupied = areaOccupied;
		this.areaAllocated = areaAllocated;
		this.cargoCondition = cargoCondition;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.totalDeliveredPkg = totalDeliveredPkg;
		this.receivedPackages = receivedPackages;
		this.receivedWeight = receivedWeight;
		this.deliveredPackages = deliveredPackages;
		this.spaceAllocated = spaceAllocated;
		this.shortQty = shortQty;
		this.comments = comments;
		this.invoiceStatus = invoiceStatus;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remark = remark;
		this.jobNo = jobNo;
		this.jobTransId = jobTransId;
		this.noOf20Ft = noOf20Ft;
		this.noOf40Ft = noOf40Ft;
		this.assesmentId = assesmentId;
		this.transporterName = transporterName;
		this.cargoValue=cargoValue;
		this.cargoDuty=cargoDuty;
		this.handlingEquip1 = handlingEquip1;
		this.handlingEquip2 = handlingEquip2;
		this.handlingEquip3 = handlingEquip3;
	}

	
	// to get data on delivery screen please check this for any query 
	public GeneralReceivingCrg(String companyId, String branchId, String receivingId, String depositNo,
			String importerName, Date receivingDate, String profitcentreId, String boeNo, Date boeDate, String cha,
			String noOfMarks, String uom, BigDecimal areaOccupied, BigDecimal gateInPackages, BigDecimal gateInWeight,
			BigDecimal receivedPackages, BigDecimal receivedWeight, String spaceAllocated, String noOf20Ft,
			String noOf40Ft, BigDecimal cargoValue, BigDecimal cargoDuty,String editedBy,BigDecimal deliveredPackages,BigDecimal deliveredWeight) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.depositNo = depositNo;
		this.importerName = importerName;
		this.receivingDate = receivingDate;
		this.profitcentreId = profitcentreId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.cha = cha;
		this.noOfMarks = noOfMarks;
		this.uom = uom;
		this.areaOccupied = areaOccupied;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.receivedPackages = receivedPackages;
		this.receivedWeight = receivedWeight;
		this.spaceAllocated = spaceAllocated;
		this.noOf20Ft = noOf20Ft;
		this.noOf40Ft = noOf40Ft;
		this.cargoValue = cargoValue;
		this.cargoDuty = cargoDuty;
		this.editedBy=editedBy;
		this.deliveredPackages=deliveredPackages;
		this.deliveredWeight=deliveredWeight;
	}

	
	public GeneralReceivingCrg(String receivingId,String boeNo) {
		super();
		this.receivingId = receivingId;
		this.boeNo = boeNo;
	}

	
}
