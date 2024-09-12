package com.cwms.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import com.cwms.entities.*;

@Entity
@Table(name = "yardblockcell")
@IdClass(YardBlockCellID.class)
public class YardBlockCell {

    @Id
    @Column(name = "Company_Id", length = 30, nullable = false)
    private String companyId;

    @Id
    @Column(name = "Yard_Id", length = 30, nullable = false)
    private String yardId;

    @Id
    @Column(name = "yard_location_id", length = 20, nullable = false)
    private String yardLocationId;

    @Id
    @Column(name = "Block_Id", length = 20, nullable = false)
    private String blockId;

    @Id
    @Column(name = "Cell_No_Row", length = 10, nullable = false)
    private String cellNoRow;
    
    @Id
    @Column(name = "yard_trans_id", length = 10, nullable = false)
    private String yardTransId;

    @Column(name = "Cell_Area", precision = 12, scale = 3, nullable = true)
    private BigDecimal cellArea;

    @Column(name = "Cell_Area_Used", precision = 12, scale = 3, nullable = true)
    private BigDecimal cellAreaUsed;

    @Column(name = "Location_Category", length = 1, nullable = false)
    private String locationCategory;
    
    @Column(name = "YardLocation_Desc", length = 50, nullable = false)
    private String yardLocationDesc;

    @Column(name = "Movement_Id", length = 30, nullable = true)
    private String movementId;

    @Column(name = "Cell_Status", length = 1, nullable = false)
    private String cellStatus;

    @Column(name = "Created_By", length = 30, nullable = true)
    private String createdBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Created_Date", nullable = true)
    private Date createdDate;

    @Column(name = "Approved_By", length = 30)
    private String approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Approved_Date")
    private Date approvedDate;

    @Column(name = "Status", length = 1, nullable = false)
    private String status;

	public YardBlockCell() {
		super();
	}

	

	public YardBlockCell(String companyId, String yardId, String yardLocationId, String blockId, String cellNoRow,
			String yardTransId, BigDecimal cellArea, BigDecimal cellAreaUsed, String locationCategory,
			String yardLocationDesc, String movementId, String cellStatus, String createdBy, Date createdDate,
			String approvedBy, Date approvedDate, String status) {
		super();
		this.companyId = companyId;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.yardTransId = yardTransId;
		this.cellArea = cellArea;
		this.cellAreaUsed = cellAreaUsed;
		this.locationCategory = locationCategory;
		this.yardLocationDesc = yardLocationDesc;
		this.movementId = movementId;
		this.cellStatus = cellStatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}

	


	public String getYardTransId() {
		return yardTransId;
	}



	public void setYardTransId(String yardTransId) {
		this.yardTransId = yardTransId;
	}



	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getYardId() {
		return yardId;
	}

	public void setYardId(String yardId) {
		this.yardId = yardId;
	}

	public String getYardLocationId() {
		return yardLocationId;
	}

	public void setYardLocationId(String yardLocationId) {
		this.yardLocationId = yardLocationId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getCellNoRow() {
		return cellNoRow;
	}

	public void setCellNoRow(String cellNoRow) {
		this.cellNoRow = cellNoRow;
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

	public String getLocationCategory() {
		return locationCategory;
	}

	public void setLocationCategory(String locationCategory) {
		this.locationCategory = locationCategory;
	}

	public String getYardLocationDesc() {
		return yardLocationDesc;
	}

	public void setYardLocationDesc(String yardLocationDesc) {
		this.yardLocationDesc = yardLocationDesc;
	}

	public String getMovementId() {
		return movementId;
	}

	public void setMovementId(String movementId) {
		this.movementId = movementId;
	}

	public String getCellStatus() {
		return cellStatus;
	}

	public void setCellStatus(String cellStatus) {
		this.cellStatus = cellStatus;
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

	@Override
	public String toString() {
		return "YardBlockCell [companyId=" + companyId + ", yardId=" + yardId + ", yardLocationId=" + yardLocationId
				+ ", blockId=" + blockId + ", cellNoRow=" + cellNoRow + ", cellArea=" + cellArea + ", cellAreaUsed="
				+ cellAreaUsed + ", locationCategory=" + locationCategory + ", yardLocationDesc=" + yardLocationDesc
				+ ", movementId=" + movementId + ", cellStatus=" + cellStatus + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", status=" + status + "]";
	}

	public YardBlockCell(String companyId, String yardId, String yardLocationId, String blockId, String cellNoRow,
			BigDecimal cellArea, BigDecimal cellAreaUsed, String locationCategory, String cellStatus, String status) {
		super();
		this.companyId = companyId;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.cellArea = cellArea;
		this.cellAreaUsed = cellAreaUsed;
		this.locationCategory = locationCategory;
		this.cellStatus = cellStatus;
		this.status = status;
	}



	public YardBlockCell(String yardLocationId, String blockId, String cellNoRow, String yardLocationDesc) {
		super();
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.yardLocationDesc = yardLocationDesc;
	}
	
	
	

	
    
}

