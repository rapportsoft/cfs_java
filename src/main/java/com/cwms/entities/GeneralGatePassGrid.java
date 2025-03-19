package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "generalgatepassgrid")
@IdClass(GeneralGatePassGridId.class)
public class GeneralGatePassGrid {
    
    @Id
    @Column(name = "Delivery_Id", length = 10, nullable = false)
    private String deliveryId;
    
    @Id
    @Column(name = "Gate_Pass_Id", length = 10, nullable = false)
    private String gatePassId;
    
    @Id
    @Column(name = "BOE_No", length = 15, nullable = false)
    private String boeNo;
    
    @Id
    @Column(name = "Sr_No", precision = 8, scale = 0, nullable = false)
    private int srNo;
    
    @Id
    @Column(name = "Yard_Location", length = 15, nullable = false)
    private String yardLocation;
    
    @Id
    @Column(name = "Yard_Block", length = 15, nullable = false)
    private String yardBlock;
    
    @Id
    @Column(name = "Block_Cell_No", length = 15, nullable = false)
    private String blockCellNo;
    
    @Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;
    
    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;
    
    @Column(name = "Receiving_Id", length = 10, nullable = false)
    private String receivingId;
    
    @Column(name = "Cell_Area_Allocated", precision = 18, scale = 3, nullable = false)
    private BigDecimal cellAreaAllocated =BigDecimal.ZERO;
    
    @Column(name = "Cell_Area_Released", precision = 18, scale = 3, nullable = false)
    private BigDecimal cellAreaReleased =BigDecimal.ZERO;
    
    @Column(name = "Delivery_Pkgs", precision = 8, scale = 0, nullable = false)
    private BigDecimal deliveryPkgs =BigDecimal.ZERO;
    
    @Column(name = "Status", length = 1, nullable = false)
    private String status;
    
    @Column(name = "Created_By", length = 10, nullable = false)
    private String createdBy;
    
    @Column(name = "Created_Date", nullable = false)
    private Date createdDate;
    
    @Column(name = "Edited_By", length = 10)
    private String editedBy;
    
    @Column(name = "Edited_Date")
    private Date editedDate;
    
    @Column(name = "Approved_By", length = 10)
    private String approvedBy;
    
    @Column(name = "Approved_Date")
    private Date approvedDate;
    
  
    
    @Column(name = "Qty_Taken_Out", precision = 8, scale = 0, nullable = false)
    private BigDecimal qtyTakenOut =BigDecimal.ZERO;
    
    
    @Column(name = "Weight_Taken_Out", precision = 18, scale = 0, nullable = false)
    private BigDecimal weightTakenOut =BigDecimal.ZERO;
    
    @Column(name = "Received_Packages", precision = 8, scale = 0, nullable = false)
    private BigDecimal receivedPackages =BigDecimal.ZERO;
    
    @Column(name = "Gate_Cell_Area_Released", precision = 18, scale = 3, nullable = false)
    private BigDecimal gateCellAreaReleased =BigDecimal.ZERO;

	
	public GeneralGatePassGrid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getYardLocation() {
		return yardLocation;
	}

	public void setYardLocation(String yardLocation) {
		this.yardLocation = yardLocation;
	}

	public String getYardBlock() {
		return yardBlock;
	}

	public void setYardBlock(String yardBlock) {
		this.yardBlock = yardBlock;
	}

	public String getBlockCellNo() {
		return blockCellNo;
	}

	public void setBlockCellNo(String blockCellNo) {
		this.blockCellNo = blockCellNo;
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

	public BigDecimal getCellAreaAllocated() {
		return cellAreaAllocated;
	}

	public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
		this.cellAreaAllocated = cellAreaAllocated;
	}

	public BigDecimal getCellAreaReleased() {
		return cellAreaReleased;
	}

	public void setCellAreaReleased(BigDecimal cellAreaReleased) {
		this.cellAreaReleased = cellAreaReleased;
	}

	public BigDecimal getDeliveryPkgs() {
		return deliveryPkgs;
	}

	public void setDeliveryPkgs(BigDecimal deliveryPkgs) {
		this.deliveryPkgs = deliveryPkgs;
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

	public BigDecimal getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(BigDecimal qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public BigDecimal getReceivedPackages() {
		return receivedPackages;
	}

	public void setReceivedPackages(BigDecimal receivedPackages) {
		this.receivedPackages = receivedPackages;
	}

	public BigDecimal getWeightTakenOut() {
		return weightTakenOut;
	}

	public void setWeightTakenOut(BigDecimal weightTakenOut) {
		this.weightTakenOut = weightTakenOut;
	}

	public BigDecimal getGateCellAreaReleased() {
		return gateCellAreaReleased;
	}

	public void setGateCellAreaReleased(BigDecimal gateCellAreaReleased) {
		this.gateCellAreaReleased = gateCellAreaReleased;
	}

	@Override
	public String toString() {
		return "GeneralGatePassGrid [deliveryId=" + deliveryId + ", gatePassId=" + gatePassId + ", boeNo=" + boeNo
				+ ", srNo=" + srNo + ", yardLocation=" + yardLocation + ", yardBlock=" + yardBlock + ", blockCellNo="
				+ blockCellNo + ", companyId=" + companyId + ", branchId=" + branchId + ", receivingId=" + receivingId
				+ ", cellAreaAllocated=" + cellAreaAllocated + ", cellAreaReleased=" + cellAreaReleased
				+ ", deliveryPkgs=" + deliveryPkgs + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", qtyTakenOut=" + qtyTakenOut
				+ ", weightTakenOut=" + weightTakenOut + ", receivedPackages=" + receivedPackages
				+ ", gateCellAreaReleased=" + gateCellAreaReleased + "]";
	}

	public GeneralGatePassGrid(String deliveryId, String gatePassId, String boeNo, int srNo, String yardLocation,
			String yardBlock, String blockCellNo, String companyId, String branchId, String receivingId,
			BigDecimal cellAreaAllocated, BigDecimal cellAreaReleased, BigDecimal deliveryPkgs, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			BigDecimal qtyTakenOut, BigDecimal weightTakenOut, BigDecimal receivedPackages,
			BigDecimal gateCellAreaReleased) {
		super();
		this.deliveryId = deliveryId;
		this.gatePassId = gatePassId;
		this.boeNo = boeNo;
		this.srNo = srNo;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.cellAreaAllocated = cellAreaAllocated;
		this.cellAreaReleased = cellAreaReleased;
		this.deliveryPkgs = deliveryPkgs;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.qtyTakenOut = qtyTakenOut;
		this.weightTakenOut = weightTakenOut;
		this.receivedPackages = receivedPackages;
		this.gateCellAreaReleased = gateCellAreaReleased;
	}

	
	
    
    
    
    
    
    
    
    
    
}

