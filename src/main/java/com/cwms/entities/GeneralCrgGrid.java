package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "generalcrggrid")
@IdClass(GeneralCrgGridId.class)
public class GeneralCrgGrid {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Id
    @Column(name = "BOE_No", length = 15, nullable = true)
    private String boeNo;

    @Id
    @Column(name = "Deposit_No", length = 15, nullable = true)
    private String depositNo;

    @Column(name = "Sr_No", length = 3, nullable = true)
    private String srNo;

    @Column(name = "Yard_Location", length = 20, nullable = true)
    private String yardLocation;

    @Column(name = "Yard_Block", length = 20, nullable = true)
    private String yardBlock;

    @Column(name = "Block_Cell_No", length = 20, nullable = true)
    private String blockCellNo;

    @Column(name = "Cell_Area_Allocated", precision = 8, scale = 3)
    private BigDecimal cellAreaAllocated;

    @Column(name = "Qty_Taken_Out")
    private BigDecimal qtyTakenOut;

    @Column(name = "Area_Released", precision = 8, scale = 3)
    private BigDecimal areaReleased;

    @Column(name = "Received_Packages", precision = 8, scale = 0)
    private BigDecimal receivedPackages;

    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Created_Date", nullable = true)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10, nullable = true)
    private String editedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Edited_Date", nullable = true)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10, nullable = true)
    private String approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Approved_Date", nullable = true)
    private Date approvedDate;

	public GeneralCrgGrid(String companyId, String branchId, String boeNo, String depositNo, String srNo,
			String yardLocation, String yardBlock, String blockCellNo, BigDecimal cellAreaAllocated,
			BigDecimal qtyTakenOut, BigDecimal areaReleased, BigDecimal receivedPackages, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.boeNo = boeNo;
		this.depositNo = depositNo;
		this.srNo = srNo;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.cellAreaAllocated = cellAreaAllocated;
		this.qtyTakenOut = qtyTakenOut;
		this.areaReleased = areaReleased;
		this.receivedPackages = receivedPackages;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	public GeneralCrgGrid() {
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

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
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

	public BigDecimal getCellAreaAllocated() {
		return cellAreaAllocated;
	}

	public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
		this.cellAreaAllocated = cellAreaAllocated;
	}

	public BigDecimal getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(BigDecimal qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public BigDecimal getAreaReleased() {
		return areaReleased;
	}

	public void setAreaReleased(BigDecimal areaReleased) {
		this.areaReleased = areaReleased;
	}

	public BigDecimal getReceivedPackages() {
		return receivedPackages;
	}

	public void setReceivedPackages(BigDecimal receivedPackages) {
		this.receivedPackages = receivedPackages;
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
		return "GeneralCrgGrid [companyId=" + companyId + ", branchId=" + branchId + ", boeNo=" + boeNo + ", depositNo="
				+ depositNo + ", srNo=" + srNo + ", yardLocation=" + yardLocation + ", yardBlock=" + yardBlock
				+ ", blockCellNo=" + blockCellNo + ", cellAreaAllocated=" + cellAreaAllocated + ", qtyTakenOut="
				+ qtyTakenOut + ", areaReleased=" + areaReleased + ", receivedPackages=" + receivedPackages
				+ ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy="
				+ editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate="
				+ approvedDate + "]";
	}
    
    
    
    
    
    
    
}

