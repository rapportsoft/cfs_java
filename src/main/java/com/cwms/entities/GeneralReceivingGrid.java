package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "generalreceivinggrid")
@IdClass(GeneralReceivingGridId.class)
public class GeneralReceivingGrid {
    
    @Id
    private String companyId;
    
    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;
    
    @Id
    @Column(name = "Receiving_Id", length = 10, nullable = true)
    private String receivingId;
    
//    @Id
    @Column(name = "Gate_In_Id", length = 10, nullable = true)
    private String gateInId;
    
    @Id
    @Column(name = "Sr_No", length = 3, nullable = true)
    private int srNo;
    
    @Column(name = "Yard_Location", length = 20, nullable = true)
    private String yardLocation;
    
    @Column(name = "Yard_Block", length = 20, nullable = true)
    private String yardBlock;
    
    @Column(name = "Block_Cell_No", length = 20)
    private String blockCellNo;
    
    @Column(name = "Cell_Area", precision = 8, scale = 3)
    private BigDecimal cellArea = BigDecimal.ZERO;
    
    @Column(name = "Cell_Area_Used", precision = 8, scale = 3)
    private BigDecimal cellAreaUsed = BigDecimal.ZERO;
    
    @Column(name = "Cell_Area_Allocated", precision = 8, scale = 3)
    private BigDecimal cellAreaAllocated = BigDecimal.ZERO;
    
    @Column(name = "Qty_Taken_Out")
    private Integer qtyTakenOut = 0;
    
    @Column(name = "Area_Released", precision = 8, scale = 3)
    private BigDecimal areaReleased = BigDecimal.ZERO;
    
    @Column(name = "Grid_Realesed", length = 1, nullable = true)
    private String gridReleased;
    
    @Column(name = "Received_Packages", precision = 8, scale = 0)
    private BigDecimal receivedPackages = BigDecimal.ZERO;
    
    @Column(name = "Delivered_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal deliveredPackages = BigDecimal.ZERO;
    
    @Column(name = "Status", length = 1, nullable = true)
    private String status;
    
    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;
    
    @Column(name = "Created_Date", nullable = true)
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

	public GeneralReceivingGrid(String companyId, String branchId, String receivingId, String gateInId, int srNo,
			String yardLocation, String yardBlock, String blockCellNo, BigDecimal cellArea, BigDecimal cellAreaUsed,
			BigDecimal cellAreaAllocated, Integer qtyTakenOut, BigDecimal areaReleased, String gridReleased,
			BigDecimal receivedPackages, BigDecimal deliveredPackages, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.gateInId = gateInId;
		this.srNo = srNo;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.cellArea = cellArea;
		this.cellAreaUsed = cellAreaUsed;
		this.cellAreaAllocated = cellAreaAllocated;
		this.qtyTakenOut = qtyTakenOut;
		this.areaReleased = areaReleased;
		this.gridReleased = gridReleased;
		this.receivedPackages = receivedPackages;
		this.deliveredPackages = deliveredPackages;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	public GeneralReceivingGrid() {
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

	public BigDecimal getCellArea() {
		return cellArea;
	}

	public void setCellArea(BigDecimal cellArea) {
		this.cellArea = cellArea;
	}

	public BigDecimal getCellAreaUsed() {
		return cellAreaUsed;
	}

	public void setCellAreaUsed(BigDecimal cellAreaUsed) {
		this.cellAreaUsed = cellAreaUsed;
	}

	public BigDecimal getCellAreaAllocated() {
		return cellAreaAllocated;
	}

	public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
		this.cellAreaAllocated = cellAreaAllocated;
	}

	public Integer getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(Integer qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public BigDecimal getAreaReleased() {
		return areaReleased;
	}

	public void setAreaReleased(BigDecimal areaReleased) {
		this.areaReleased = areaReleased;
	}

	public String getGridReleased() {
		return gridReleased;
	}

	public void setGridReleased(String gridReleased) {
		this.gridReleased = gridReleased;
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

	@Override
	public String toString() {
		return "GeneralReceivingGrid [companyId=" + companyId + ", branchId=" + branchId + ", receivingId="
				+ receivingId + ", gateInId=" + gateInId + ", srNo=" + srNo + ", yardLocation=" + yardLocation
				+ ", yardBlock=" + yardBlock + ", blockCellNo=" + blockCellNo + ", cellArea=" + cellArea
				+ ", cellAreaUsed=" + cellAreaUsed + ", cellAreaAllocated=" + cellAreaAllocated + ", qtyTakenOut="
				+ qtyTakenOut + ", areaReleased=" + areaReleased + ", gridReleased=" + gridReleased
				+ ", receivedPackages=" + receivedPackages + ", deliveredPackages=" + deliveredPackages + ", status="
				+ status + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + "]";
	}
    
    
}
