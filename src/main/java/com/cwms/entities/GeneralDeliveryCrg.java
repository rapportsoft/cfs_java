package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "generaldeliverycrg")
@IdClass(GeneralDeliveryCrgId.class)
public class GeneralDeliveryCrg {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Id
    @Column(name = "Delivery_Id", length = 10, nullable = true)
    private String deliveryId;
    
    

    @Column(name = "Receiving_Id", length = 10, nullable = true)
    private String receivingId;

    @Column(name = "Delivery_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date deliveryDate;

    @Column(name = "Profitcentre_Id", length = 6, nullable = true)
    private String profitCentreId;

    @Column(name = "BOE_No", length = 20, nullable = true)
    private String boeNo;

    @Column(name = "BOE_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date boeDate;

    @Column(name = "Deposit_No", length = 15, nullable = true)
    private String depositNo;

    @Column(name = "Storage_Validity_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date storageValidityDate;

    @Column(name = "On_Account_Of", length = 6, nullable = true)
    private String onAccountOf;

    @Column(name = "Importer_Id", length = 6, nullable = true)
    private String importerId;

    @Column(name = "Importer_Name", length = 60, nullable = true)
    private String importerName;

    @Column(name = "CHA", length = 6, nullable = true)
    private String cha;

    @Column(name = "Shift", length = 6)
    private String shift;

  

    @Column(name = "UOM", length = 6, nullable = true)
    private String uom;

    @Column(name = "Area_Occupied", precision = 8, scale = 3, nullable = true)
    private BigDecimal areaOccupied=BigDecimal.ZERO;


    @Column(name = "Area_Released", precision = 8, scale = 3, nullable = true)
    private BigDecimal areaReleased=BigDecimal.ZERO;


    @Column(name = "Area_Balanced", precision = 8, scale = 3, nullable = true)
    private BigDecimal areaBalanced=BigDecimal.ZERO;


    @Column(name = "Area_Remaining", precision = 8, scale = 3, nullable = true)
    private BigDecimal areaRemaining=BigDecimal.ZERO;


    @Column(name = "Received_Packages", precision = 6, scale = 0, nullable = true)
    private BigDecimal receivedPackages=BigDecimal.ZERO;


    @Column(name = "Delivered_Packages", precision = 6, scale = 0, nullable = true)
    private BigDecimal deliveredPackages=BigDecimal.ZERO;


    @Column(name = "Balanced_Packages", precision = 6, scale = 0, nullable = true)
    private BigDecimal balancedPackages=BigDecimal.ZERO;


    @Column(name = "Remaining_Packages", precision = 6, scale = 0, nullable = true)
    private BigDecimal remainingPackages=BigDecimal.ZERO;


    @Column(name = "Received_GW", precision = 12, scale = 3, nullable = true)
    private BigDecimal receivedGw = BigDecimal.ZERO;

    @Column(name = "Delivery_GW", precision = 12, scale = 3, nullable = true)
    private BigDecimal deliveryGw = BigDecimal.ZERO;

    @Column(name = "Balance_GW", precision = 12, scale = 3, nullable = true)
    private BigDecimal balanceGw = BigDecimal.ZERO;

    @Column(name = "Remaining_GW", precision = 12, scale = 3, nullable = true)
    private BigDecimal remainingGw = BigDecimal.ZERO;
    
    @Column(name = "Qty_Taken_Out", precision = 8, scale = 0, nullable = true)
    private BigDecimal qtyTakenOut=BigDecimal.ZERO;


    @Column(name = "Space_Allocated", length = 10, nullable = true)
    private String spaceAllocated;

    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

    @Column(name = "Gate_Pass_Allow", length = 1, nullable = true)
    private char gatePassAllow;

    @Column(name = "Gate_Out_Id", length = 10)
    private String gateOutId;

    @Column(name = "Assesment_Id", length = 10)
    private String assessmentId;

    @Column(name = "Assesment_Status", length = 1, nullable = true)
    private char assessmentStatus;

    @Column(name = "Storage_Based_On", length = 10, nullable = true)
    private String storageBasedOn;

    @Column(name = "Storage_Unit", length = 10, nullable = true)
    private String storageUnit;

    @Column(name = "No_Of_20FT", length = 5, nullable = true)
    private String noOf20Ft;

    @Column(name = "No_Of_40FT", length = 5, nullable = true)
    private String noOf40Ft;

    @Column(name = "Status", length = 1, nullable = true)
    private char status;

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

    @Column(name = "Cargo_Value", precision = 15, scale = 2)
    private BigDecimal cargoValue = BigDecimal.ZERO;

    @Column(name = "Cargo_Duty", precision = 15, scale = 2)
    private BigDecimal cargoDuty = BigDecimal.ZERO;

    @Column(name = "CIF_Value", precision = 15, scale = 2, nullable = true)
    private BigDecimal cifValue = BigDecimal.ZERO;

    @Column(name = "RCargo_Value", precision = 15, scale = 2, nullable = true)
    private BigDecimal rCargoValue = BigDecimal.ZERO;

    @Column(name = "RCargo_Duty", precision = 15, scale = 2, nullable = true)
    private BigDecimal rCargoDuty = BigDecimal.ZERO;

    @Column(name = "RCIF_Value", precision = 15, scale = 2, nullable = true)
    private BigDecimal rCifValue = BigDecimal.ZERO;

    @Column(name = "Handling_Equip", length = 30, nullable = true)
    private String handlingEquip;

    @Column(name = "Handling_Equip1", length = 20, nullable = true)
    private String handlingEquip1;

    @Column(name = "Handling_Equip2", length = 20, nullable = true)
    private String handlingEquip2;

    @Column(name = "Owner", length = 20, nullable = true)
    private String owner;

    @Column(name = "Owner1", length = 20, nullable = true)
    private String owner1;

    @Column(name = "Owner2", length = 20, nullable = true)
    private String owner2;

    @Column(name = "Transporter", length = 255, nullable = true)
    private String transporter;

    @Column(name = "Number_Of_Marks", length = 18)
    private String numberOfMarks;

    @Column(name = "Handling_Status", length = 1, nullable = true)
    private char handlingStatus;

    @Column(name = "Transporter_Name", length = 255, nullable = true)
    private String transporterName;

    @Column(name = "Handling_Invoice_No", length = 10, nullable = true)
    private String handlingInvoiceNo;

	public GeneralDeliveryCrg(String companyId, String branchId, String deliveryId, String receivingId,
			Date deliveryDate, String profitCentreId, String boeNo, Date boeDate, String depositNo,
			Date storageValidityDate, String onAccountOf, String importerId, String importerName, String cha,
			String shift, String uom, BigDecimal areaOccupied, BigDecimal areaReleased, BigDecimal areaBalanced,
			BigDecimal areaRemaining, BigDecimal receivedPackages, BigDecimal deliveredPackages,
			BigDecimal balancedPackages, BigDecimal remainingPackages, BigDecimal receivedGw, BigDecimal deliveryGw,
			BigDecimal balanceGw, BigDecimal remainingGw, BigDecimal qtyTakenOut, String spaceAllocated,
			String comments, char gatePassAllow, String gateOutId, String assessmentId, char assessmentStatus,
			String storageBasedOn, String storageUnit, String noOf20Ft, String noOf40Ft, char status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			BigDecimal cargoValue, BigDecimal cargoDuty, BigDecimal cifValue, BigDecimal rCargoValue,
			BigDecimal rCargoDuty, BigDecimal rCifValue, String handlingEquip, String handlingEquip1,
			String handlingEquip2, String owner, String owner1, String owner2, String transporter, String numberOfMarks,
			char handlingStatus, String transporterName, String handlingInvoiceNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.deliveryId = deliveryId;
		this.receivingId = receivingId;
		this.deliveryDate = deliveryDate;
		this.profitCentreId = profitCentreId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.depositNo = depositNo;
		this.storageValidityDate = storageValidityDate;
		this.onAccountOf = onAccountOf;
		this.importerId = importerId;
		this.importerName = importerName;
		this.cha = cha;
		this.shift = shift;
		this.uom = uom;
		this.areaOccupied = areaOccupied;
		this.areaReleased = areaReleased;
		this.areaBalanced = areaBalanced;
		this.areaRemaining = areaRemaining;
		this.receivedPackages = receivedPackages;
		this.deliveredPackages = deliveredPackages;
		this.balancedPackages = balancedPackages;
		this.remainingPackages = remainingPackages;
		this.receivedGw = receivedGw;
		this.deliveryGw = deliveryGw;
		this.balanceGw = balanceGw;
		this.remainingGw = remainingGw;
		this.qtyTakenOut = qtyTakenOut;
		this.spaceAllocated = spaceAllocated;
		this.comments = comments;
		this.gatePassAllow = gatePassAllow;
		this.gateOutId = gateOutId;
		this.assessmentId = assessmentId;
		this.assessmentStatus = assessmentStatus;
		this.storageBasedOn = storageBasedOn;
		this.storageUnit = storageUnit;
		this.noOf20Ft = noOf20Ft;
		this.noOf40Ft = noOf40Ft;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.cargoValue = cargoValue;
		this.cargoDuty = cargoDuty;
		this.cifValue = cifValue;
		this.rCargoValue = rCargoValue;
		this.rCargoDuty = rCargoDuty;
		this.rCifValue = rCifValue;
		this.handlingEquip = handlingEquip;
		this.handlingEquip1 = handlingEquip1;
		this.handlingEquip2 = handlingEquip2;
		this.owner = owner;
		this.owner1 = owner1;
		this.owner2 = owner2;
		this.transporter = transporter;
		this.numberOfMarks = numberOfMarks;
		this.handlingStatus = handlingStatus;
		this.transporterName = transporterName;
		this.handlingInvoiceNo = handlingInvoiceNo;
	}

	public GeneralDeliveryCrg() {
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
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

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public Date getStorageValidityDate() {
		return storageValidityDate;
	}

	public void setStorageValidityDate(Date storageValidityDate) {
		this.storageValidityDate = storageValidityDate;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
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

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
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

	public BigDecimal getAreaReleased() {
		return areaReleased;
	}

	public void setAreaReleased(BigDecimal areaReleased) {
		this.areaReleased = areaReleased;
	}

	public BigDecimal getAreaBalanced() {
		return areaBalanced;
	}

	public void setAreaBalanced(BigDecimal areaBalanced) {
		this.areaBalanced = areaBalanced;
	}

	public BigDecimal getAreaRemaining() {
		return areaRemaining;
	}

	public void setAreaRemaining(BigDecimal areaRemaining) {
		this.areaRemaining = areaRemaining;
	}

	public BigDecimal getReceivedPackages() {
		return receivedPackages;
	}

	public void setReceivedPackages(BigDecimal receivedPackages) {
		this.receivedPackages = receivedPackages;
	}

	public BigDecimal getDeliveredPackages() {
		return deliveredPackages;
	}

	public void setDeliveredPackages(BigDecimal deliveredPackages) {
		this.deliveredPackages = deliveredPackages;
	}

	public BigDecimal getBalancedPackages() {
		return balancedPackages;
	}

	public void setBalancedPackages(BigDecimal balancedPackages) {
		this.balancedPackages = balancedPackages;
	}

	public BigDecimal getRemainingPackages() {
		return remainingPackages;
	}

	public void setRemainingPackages(BigDecimal remainingPackages) {
		this.remainingPackages = remainingPackages;
	}

	public BigDecimal getReceivedGw() {
		return receivedGw;
	}

	public void setReceivedGw(BigDecimal receivedGw) {
		this.receivedGw = receivedGw;
	}

	public BigDecimal getDeliveryGw() {
		return deliveryGw;
	}

	public void setDeliveryGw(BigDecimal deliveryGw) {
		this.deliveryGw = deliveryGw;
	}

	public BigDecimal getBalanceGw() {
		return balanceGw;
	}

	public void setBalanceGw(BigDecimal balanceGw) {
		this.balanceGw = balanceGw;
	}

	public BigDecimal getRemainingGw() {
		return remainingGw;
	}

	public void setRemainingGw(BigDecimal remainingGw) {
		this.remainingGw = remainingGw;
	}

	public BigDecimal getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(BigDecimal qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public String getSpaceAllocated() {
		return spaceAllocated;
	}

	public void setSpaceAllocated(String spaceAllocated) {
		this.spaceAllocated = spaceAllocated;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public char getGatePassAllow() {
		return gatePassAllow;
	}

	public void setGatePassAllow(char gatePassAllow) {
		this.gatePassAllow = gatePassAllow;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public String getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
	}

	public char getAssessmentStatus() {
		return assessmentStatus;
	}

	public void setAssessmentStatus(char assessmentStatus) {
		this.assessmentStatus = assessmentStatus;
	}

	public String getStorageBasedOn() {
		return storageBasedOn;
	}

	public void setStorageBasedOn(String storageBasedOn) {
		this.storageBasedOn = storageBasedOn;
	}

	public String getStorageUnit() {
		return storageUnit;
	}

	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
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

	public BigDecimal getCifValue() {
		return cifValue;
	}

	public void setCifValue(BigDecimal cifValue) {
		this.cifValue = cifValue;
	}

	public BigDecimal getrCargoValue() {
		return rCargoValue;
	}

	public void setrCargoValue(BigDecimal rCargoValue) {
		this.rCargoValue = rCargoValue;
	}

	public BigDecimal getrCargoDuty() {
		return rCargoDuty;
	}

	public void setrCargoDuty(BigDecimal rCargoDuty) {
		this.rCargoDuty = rCargoDuty;
	}

	public BigDecimal getrCifValue() {
		return rCifValue;
	}

	public void setrCifValue(BigDecimal rCifValue) {
		this.rCifValue = rCifValue;
	}

	public String getHandlingEquip() {
		return handlingEquip;
	}

	public void setHandlingEquip(String handlingEquip) {
		this.handlingEquip = handlingEquip;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getNumberOfMarks() {
		return numberOfMarks;
	}

	public void setNumberOfMarks(String numberOfMarks) {
		this.numberOfMarks = numberOfMarks;
	}

	public char getHandlingStatus() {
		return handlingStatus;
	}

	public void setHandlingStatus(char handlingStatus) {
		this.handlingStatus = handlingStatus;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getHandlingInvoiceNo() {
		return handlingInvoiceNo;
	}

	public void setHandlingInvoiceNo(String handlingInvoiceNo) {
		this.handlingInvoiceNo = handlingInvoiceNo;
	}

	@Override
	public String toString() {
		return "GeneralDeliveryCrg [companyId=" + companyId + ", branchId=" + branchId + ", deliveryId=" + deliveryId
				+ ", receivingId=" + receivingId + ", deliveryDate=" + deliveryDate + ", profitCentreId="
				+ profitCentreId + ", boeNo=" + boeNo + ", boeDate=" + boeDate + ", depositNo=" + depositNo
				+ ", storageValidityDate=" + storageValidityDate + ", onAccountOf=" + onAccountOf + ", importerId="
				+ importerId + ", importerName=" + importerName + ", cha=" + cha + ", shift=" + shift + ", uom=" + uom
				+ ", areaOccupied=" + areaOccupied + ", areaReleased=" + areaReleased + ", areaBalanced=" + areaBalanced
				+ ", areaRemaining=" + areaRemaining + ", receivedPackages=" + receivedPackages + ", deliveredPackages="
				+ deliveredPackages + ", balancedPackages=" + balancedPackages + ", remainingPackages="
				+ remainingPackages + ", receivedGw=" + receivedGw + ", deliveryGw=" + deliveryGw + ", balanceGw="
				+ balanceGw + ", remainingGw=" + remainingGw + ", qtyTakenOut=" + qtyTakenOut + ", spaceAllocated="
				+ spaceAllocated + ", comments=" + comments + ", gatePassAllow=" + gatePassAllow + ", gateOutId="
				+ gateOutId + ", assessmentId=" + assessmentId + ", assessmentStatus=" + assessmentStatus
				+ ", storageBasedOn=" + storageBasedOn + ", storageUnit=" + storageUnit + ", noOf20Ft=" + noOf20Ft
				+ ", noOf40Ft=" + noOf40Ft + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", cargoValue=" + cargoValue + ", cargoDuty=" + cargoDuty
				+ ", cifValue=" + cifValue + ", rCargoValue=" + rCargoValue + ", rCargoDuty=" + rCargoDuty
				+ ", rCifValue=" + rCifValue + ", handlingEquip=" + handlingEquip + ", handlingEquip1=" + handlingEquip1
				+ ", handlingEquip2=" + handlingEquip2 + ", owner=" + owner + ", owner1=" + owner1 + ", owner2="
				+ owner2 + ", transporter=" + transporter + ", numberOfMarks=" + numberOfMarks + ", handlingStatus="
				+ handlingStatus + ", transporterName=" + transporterName + ", handlingInvoiceNo=" + handlingInvoiceNo
				+ "]";
	}

	public GeneralDeliveryCrg(String companyId, String branchId, String deliveryId, String receivingId,
			Date deliveryDate, String profitCentreId, String boeNo, Date boeDate, String depositNo, String importerName,
			String cha, BigDecimal deliveredPackages, char status, String editedBy, String transporterName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.deliveryId = deliveryId;
		this.receivingId = receivingId;
		this.deliveryDate = deliveryDate;
		this.profitCentreId = profitCentreId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.depositNo = depositNo;
		this.importerName = importerName;
		this.cha = cha;
		this.deliveredPackages = deliveredPackages;
		this.status = status;
		this.editedBy = editedBy;
		this.transporterName = transporterName;
	}

	
	
	
	
	
	// to get the data on screen after selecting the radio button please check this for any changes 
	public GeneralDeliveryCrg(String companyId, String branchId, String deliveryId, String receivingId,
			Date deliveryDate, String profitCentreId, String boeNo, Date boeDate, String depositNo, String onAccountOf,
			String importerId, String importerName, String cha, String shift, String uom, BigDecimal areaOccupied,
			BigDecimal areaReleased, BigDecimal areaBalanced, BigDecimal areaRemaining, BigDecimal receivedPackages,
			BigDecimal deliveredPackages, BigDecimal balancedPackages, BigDecimal remainingPackages,
			BigDecimal receivedGw, BigDecimal deliveryGw, BigDecimal balanceGw, BigDecimal remainingGw,
			String spaceAllocated, String comments, char gatePassAllow, String noOf20Ft, String noOf40Ft, char status,
			String createdBy, String editedBy, String approvedBy, String handlingEquip, String handlingEquip1,
			String handlingEquip2, String owner, String owner1, String owner2, String numberOfMarks,
			String transporterName,BigDecimal cargoValue, BigDecimal cargoDuty,BigDecimal rCargoValue,
			BigDecimal rCargoDuty) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.deliveryId = deliveryId;
		this.receivingId = receivingId;
		this.deliveryDate = deliveryDate;
		this.profitCentreId = profitCentreId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.depositNo = depositNo;
		this.onAccountOf = onAccountOf;
		this.importerId = importerId;
		this.importerName = importerName;
		this.cha = cha;
		this.shift = shift;
		this.uom = uom;
		this.areaOccupied = areaOccupied;
		this.areaReleased = areaReleased;
		this.areaBalanced = areaBalanced;
		this.areaRemaining = areaRemaining;
		this.receivedPackages = receivedPackages;
		this.deliveredPackages = deliveredPackages;
		this.balancedPackages = balancedPackages;
		this.remainingPackages = remainingPackages;
		this.receivedGw = receivedGw;
		this.deliveryGw = deliveryGw;
		this.balanceGw = balanceGw;
		this.remainingGw = remainingGw;
		this.spaceAllocated = spaceAllocated;
		this.comments = comments;
		this.gatePassAllow = gatePassAllow;
		this.noOf20Ft = noOf20Ft;
		this.noOf40Ft = noOf40Ft;
		this.status = status;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.handlingEquip = handlingEquip;
		this.handlingEquip1 = handlingEquip1;
		this.handlingEquip2 = handlingEquip2;
		this.owner = owner;
		this.owner1 = owner1;
		this.owner2 = owner2;
		this.numberOfMarks = numberOfMarks;
		this.transporterName = transporterName;
		this.cargoValue = cargoValue;
		this.cargoDuty = cargoDuty;
		this.rCargoValue = rCargoValue;
		this.rCargoDuty = rCargoDuty;
	}
    
 
    
   
	
	
	
}