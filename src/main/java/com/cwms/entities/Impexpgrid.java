package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfimpexpgrid")
@IdClass(ImpexpgridId.class)
public class Impexpgrid {

	    @Id
	    @Column(name = "Company_Id",  length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Fin_Year",  length = 4)
	    private String finYear;

	    @Id
	    @Column(name = "Branch_Id",  length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Process_Trans_Id",  length = 10)
	    private String processTransId;

	    @Id
	    @Column(name = "Line_No")
	    private int lineNo;

	    @Id
	    @Column(name = "Sub_Sr_No")
	    private int subSrNo;

	    @Column(name = "Yard_Location",  length = 250)
	    private String yardLocation;

	    @Column(name = "Yard_Block",  length = 10)
	    private String yardBlock;

	    @Column(name = "Block_Cell_No", length = 10)
	    private String blockCellNo;

	    @Column(name = "Yard_Packages")
	    private int yardPackages;

	    @Column(name = "Cell_Area",  precision = 18, scale = 3)
	    private BigDecimal cellArea;

	    @Column(name = "Cell_Area_Used",  precision = 18, scale = 3)
	    private BigDecimal cellAreaUsed;

	    @Column(name = "Cell_Area_Allocated",  precision = 18, scale = 3)
	    private BigDecimal cellAreaAllocated;

	    @Column(name = "QTY_TAKEN_OUT", precision = 18, scale = 3)
	    private BigDecimal qtyTakenOut;

	    @Column(name = "Area_Released",  precision = 18, scale = 3)
	    private BigDecimal areaReleased;

	    @Column(name = "Trans_type", length = 3)
	    private String transType;

	    @Column(name = "Stuff_Req_Qty")
	    private int stuffReqQty;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Column(name = "Created_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdDate;
	    
	    
	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Editeded_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    private Date editedDate;	    

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Column(name = "Approved_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date approvedDate;

	    @Column(name = "Status",  length = 1)
	    private String status;

		public Impexpgrid() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Impexpgrid(String companyId, String finYear, String branchId, String processTransId, int lineNo,
				int subSrNo, String yardLocation, String yardBlock, String blockCellNo, int yardPackages,
				BigDecimal cellArea, BigDecimal cellAreaUsed, BigDecimal cellAreaAllocated, BigDecimal qtyTakenOut,
				BigDecimal areaReleased, String transType, int stuffReqQty, String createdBy, Date createdDate,
				String approvedBy, Date approvedDate, String status) {
			super();
			this.companyId = companyId;
			this.finYear = finYear;
			this.branchId = branchId;
			this.processTransId = processTransId;
			this.lineNo = lineNo;
			this.subSrNo = subSrNo;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.yardPackages = yardPackages;
			this.cellArea = cellArea;
			this.cellAreaUsed = cellAreaUsed;
			this.cellAreaAllocated = cellAreaAllocated;
			this.qtyTakenOut = qtyTakenOut;
			this.areaReleased = areaReleased;
			this.transType = transType;
			this.stuffReqQty = stuffReqQty;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.status = status;
		}	
		

		public Impexpgrid(String companyId, String finYear, String branchId, String processTransId, int lineNo,
				int subSrNo, String yardLocation, String yardBlock, String blockCellNo, int yardPackages,
				BigDecimal cellArea, BigDecimal cellAreaUsed, BigDecimal cellAreaAllocated, BigDecimal qtyTakenOut,
				BigDecimal areaReleased, String transType, int stuffReqQty, String createdBy, Date createdDate,
				String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status) {
			super();
			this.companyId = companyId;
			this.finYear = finYear;
			this.branchId = branchId;
			this.processTransId = processTransId;
			this.lineNo = lineNo;
			this.subSrNo = subSrNo;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.yardPackages = yardPackages;
			this.cellArea = cellArea;
			this.cellAreaUsed = cellAreaUsed;
			this.cellAreaAllocated = cellAreaAllocated;
			this.qtyTakenOut = qtyTakenOut;
			this.areaReleased = areaReleased;
			this.transType = transType;
			this.stuffReqQty = stuffReqQty;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.status = status;
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

		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getFinYear() {
			return finYear;
		}

		public void setFinYear(String finYear) {
			this.finYear = finYear;
		}

		public String getBranchId() {
			return branchId;
		}

		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}

		public String getProcessTransId() {
			return processTransId;
		}

		public void setProcessTransId(String processTransId) {
			this.processTransId = processTransId;
		}

		public int getLineNo() {
			return lineNo;
		}

		public void setLineNo(int lineNo) {
			this.lineNo = lineNo;
		}

		public int getSubSrNo() {
			return subSrNo;
		}

		public void setSubSrNo(int subSrNo) {
			this.subSrNo = subSrNo;
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

		public int getYardPackages() {
			return yardPackages;
		}

		public void setYardPackages(int yardPackages) {
			this.yardPackages = yardPackages;
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

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
		}

		public int getStuffReqQty() {
			return stuffReqQty;
		}

		public void setStuffReqQty(int stuffReqQty) {
			this.stuffReqQty = stuffReqQty;
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
	    
}
