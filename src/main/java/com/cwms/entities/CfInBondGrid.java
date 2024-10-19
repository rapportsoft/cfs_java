package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "cfinbondgrid")
@IdClass(CfInBondGridId.class)
public class CfInBondGrid {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Id
    @Column(name = "Fin_Year", length = 4, nullable = false)
    private String finYear;

    @Id
    @Column(name = "NOC_Trans_Id", length = 10, nullable = false)
    private String nocTransId;

    @Id
    @Column(name = "In_Bonding_Id", length = 10, nullable = false)
    private String inBondingId;

    @Id
    @Column(name = "Sr_No", length = 3, nullable = false)
    private Integer srNo;


    @Column(name = "Gate_In_Id", length = 10, nullable = true)
    private String gateInId;

    @Id
	@Column(name = "Cfbond_Detail_Id", length = 6, nullable = false)
	private String cfBondDtlId;
	
    @Column(name = "Yard_Location", length = 10, nullable = true)
    private String yardLocation;

    @Column(name = "Yard_Block", length = 10, nullable = true)
    private String yardBlock;

    @Column(name = "Block_Cell_No", length = 10)
    private String blockCellNo;

    @Column(name = "Cell_Area", precision = 8, scale = 3)
    private BigDecimal cellArea;

    @Column(name = "Cell_Area_Used", precision = 8, scale = 2)
    private BigDecimal cellAreaUsed;

    @Column(name = "cell_Area_Allocated", precision = 8, scale = 2)
    private BigDecimal cellAreaAllocated;

    @Column(name = "Qty_Taken_Out", columnDefinition = "int default 0")
    private BigDecimal qtyTakenOut;

    @Column(name = "Area_Released", precision = 8, scale = 3, columnDefinition = "decimal(8,3) default '0.000'")
    private BigDecimal areaReleased;

    @Column(name = "Grid_Released", length = 1, nullable = true)
    private String gridReleased;

    @Column(name = "In_Bond_Packages", precision = 8, scale = 0)
    private BigDecimal inBondPackages;

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
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    
    
    @Transient
    @Column(name = "Ex_Cell_Area_Allocated", precision = 8, scale = 2)
    private BigDecimal exCellAreaAllocated;
    
    @Transient
    @Column(name = "Ex_Bond_Packages", precision = 8, scale = 0)
    private BigDecimal exBondPackages;



	public BigDecimal getExCellAreaAllocated() {
		return exCellAreaAllocated;
	}

	public void setExCellAreaAllocated(BigDecimal exCellAreaAllocated) {
		this.exCellAreaAllocated = exCellAreaAllocated;
	}

	public BigDecimal getExBondPackages() {
		return exBondPackages;
	}

	public void setExBondPackages(BigDecimal exBondPackages) {
		this.exBondPackages = exBondPackages;
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

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public Integer getSrNo() {
		return srNo;
	}

	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
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

	public String getGridReleased() {
		return gridReleased;
	}

	public void setGridReleased(String gridReleased) {
		this.gridReleased = gridReleased;
	}

	public BigDecimal getInBondPackages() {
		return inBondPackages;
	}

	public void setInBondPackages(BigDecimal inBondPackages) {
		this.inBondPackages = inBondPackages;
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
	
	
	public String getCfBondDtlId() {
		return cfBondDtlId;
	}

	public void setCfBondDtlId(String cfBondDtlId) {
		this.cfBondDtlId = cfBondDtlId;
	}

	public CfInBondGrid(String companyId, String branchId, String finYear, String nocTransId, String inBondingId,
			Integer srNo, String gateInId, String cfBondDtlId, String yardLocation, String yardBlock, String blockCellNo,
			BigDecimal cellArea, BigDecimal cellAreaUsed, BigDecimal cellAreaAllocated, BigDecimal qtyTakenOut,
			BigDecimal areaReleased, String gridReleased, BigDecimal inBondPackages, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.nocTransId = nocTransId;
		this.inBondingId = inBondingId;
		this.srNo = srNo;
		this.gateInId = gateInId;
		this.cfBondDtlId = cfBondDtlId;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.cellArea = cellArea;
		this.cellAreaUsed = cellAreaUsed;
		this.cellAreaAllocated = cellAreaAllocated;
		this.qtyTakenOut = qtyTakenOut;
		this.areaReleased = areaReleased;
		this.gridReleased = gridReleased;
		this.inBondPackages = inBondPackages;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}

	@Override
	public String toString() {
		return "CfInBondGrid [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", nocTransId=" + nocTransId + ", inBondingId=" + inBondingId + ", srNo=" + srNo + ", gateInId="
				+ gateInId + ", cfBondDtlId=" + cfBondDtlId + ", yardLocation=" + yardLocation + ", yardBlock="
				+ yardBlock + ", blockCellNo=" + blockCellNo + ", cellArea=" + cellArea + ", cellAreaUsed="
				+ cellAreaUsed + ", cellAreaAllocated=" + cellAreaAllocated + ", qtyTakenOut=" + qtyTakenOut
				+ ", areaReleased=" + areaReleased + ", gridReleased=" + gridReleased + ", inBondPackages="
				+ inBondPackages + ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + "]";
	}

	public CfInBondGrid() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	// to get data in exbond for exbond grid table 
	public CfInBondGrid(String companyId, String branchId, String finYear, String nocTransId, String inBondingId,
			Integer srNo, String gateInId, String cfBondDtlId, String yardLocation, String yardBlock,
			String blockCellNo, BigDecimal cellArea, BigDecimal cellAreaUsed, BigDecimal cellAreaAllocated,
			BigDecimal inBondPackages, BigDecimal exCellAreaAllocated, BigDecimal exBondPackages) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.nocTransId = nocTransId;
		this.inBondingId = inBondingId;
		this.srNo = srNo;
		this.gateInId = gateInId;
		this.cfBondDtlId = cfBondDtlId;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.cellArea = cellArea;
		this.cellAreaUsed = cellAreaUsed;
		this.cellAreaAllocated = cellAreaAllocated;
		this.inBondPackages = inBondPackages;
		this.exCellAreaAllocated = exCellAreaAllocated;
		this.exBondPackages = exBondPackages;
	}

	
	
    // Getters and Setters

}
