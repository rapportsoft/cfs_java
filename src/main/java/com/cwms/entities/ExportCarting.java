package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@IdClass(ExportCartingId.class)
@Table(name = "cfcrtg")
public class ExportCarting implements Cloneable{

    @Id
    @Column(name = "Company_Id", length = 6, columnDefinition = "varchar(6) default ''")
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, columnDefinition = "varchar(6) default ''")
    private String branchId;

    @Id
    @Column(name = "Carting_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String cartingTransId;

    @Id
    @Column(name = "Carting_Line_Id", length = 4, columnDefinition = "varchar(4) default ''")
    private String cartingLineId;

    @Id
    @Column(name = "Fin_Year", length = 4, columnDefinition = "varchar(4) default ''")
    private String finYear;

    @Id
    @Column(name = "Profitcentre_Id", length = 6, columnDefinition = "varchar(6) default ''")
    private String profitcentreId;

    @Id
    @Column(name = "SB_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String sbTransId;

    @Id
    @Column(name = "SB_No", length = 15, columnDefinition = "varchar(15) default ''")
    private String sbNo;

    @Id
    @Column(name = "SB_Line_No", length = 6, columnDefinition = "varchar(6) default ''")
    private String sbLineNo;
    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Sb_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sbDate;    
    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Carting_Trans_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cartingTransDate;

    @Column(name = "Gate_In_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String gateInId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Gate_In_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateInDate;

    @Column(name = "Crg_Exam_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String crgExamId;

    @Column(name = "Shift", length = 6, columnDefinition = "varchar(6) default null")
    private String shift;

    @Column(name = "Vehicle_No", length = 15, columnDefinition = "varchar(15) default ''")
    private String vehicleNo;

    @Column(name = "On_Account_Of", length = 6, columnDefinition = "varchar(6) default ''")
    private String onAccountOf;

    @Column(name = "Commodity", length = 250, columnDefinition = "varchar(250) default null")
    private String commodity;

    @Column(name = "Gate_In_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal gateInPackages;

    @Column(name = "Gate_in_Weight", precision = 15, scale = 3, columnDefinition = "decimal(15,3) default '0.000'")
    private BigDecimal gateInWeight;

    @Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal actualNoOfPackages;

    @Column(name = "Actual_No_Of_Weight", precision = 16, scale = 4, columnDefinition = "decimal(16,4) default '0.0000'")
    private BigDecimal actualNoOfWeight;

    @Column(name = "FOB", precision = 16, scale = 4, columnDefinition = "decimal(16,4) default null")
    private BigDecimal fob;

    @Column(name = "Gate_In_Type", length = 10, columnDefinition = "varchar(10) default null")
    private String gateInType;

    @Column(name = "Invoice_Type", length = 5, columnDefinition = "varchar(5) default null")
    private String invoiceType;

    @Column(name = "Grid_Location", length = 10, columnDefinition = "varchar(10) default ''")
    private String gridLocation;

    @Column(name = "Grid_Block", length = 10, columnDefinition = "varchar(10) default ''")
    private String gridBlock;

    @Column(name = "Grid_Cell_No", length = 10, columnDefinition = "varchar(10) default null")
    private String gridCellNo;

    @Column(name = "Stuff_Req_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String stuffReqId;

    @Column(name = "Stuffed_No_Of_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal stuffedNoOfPackages;

    @Column(name = "Area_Occupied", precision = 8, scale = 3, columnDefinition = "decimal(8,3) default '0.000'")
    private BigDecimal areaOccupied;

    @Column(name = "Yard_Packages", precision = 8, scale = 3, columnDefinition = "decimal(8,3) default '0.000'")
    private BigDecimal yardPackages;

    @Column(name = "Damage_Comments", length = 150, columnDefinition = "varchar(150) default ''")
    private String damageComments;

    @Column(name = "Status", length = 1, columnDefinition = "char(1) default ''")
    private String status;

    @Column(name = "Created_By", length = 10, columnDefinition = "varchar(10) default ''")
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Created_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10, columnDefinition = "varchar(10) default null")
    private String editedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Edited_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10, columnDefinition = "varchar(10) default null")
    private String approvedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Approved_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Column(name = "From_SB_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String fromSbTransId;

    @Column(name = "From_SB_No", length = 25, columnDefinition = "varchar(25) default ''")
    private String fromSbNo;

    @Column(name = "From_SB_Line_No", length = 10, columnDefinition = "varchar(10) default ''")
    private String fromSbLineNo;

    @Column(name = "Last_Storage_Invoice_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastStorageInvoiceDate;

    @Column(name = "Last_Storage_Flag", length = 1, columnDefinition = "char(1) default 'N'")
    private String lastStorageFlag;

    @Column(name = "Storage_Weeks", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal storageWeeks;

    @Column(name = "Storage_Days", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal storageDays;

    @Column(name = "Storage_Months", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal storageMonths;

    @Column(name = "Handling_Charges", precision = 16, scale = 4, columnDefinition = "decimal(16,4) default null")
    private BigDecimal handlingCharges;
    
    
    @Column(name = "Excess_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal excessPackages;
    
    
    @Column(name = "Shortage_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal shortagePackages;
    
    
    @Column(name = "Damage_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
    private BigDecimal damagePackages;
    
    transient private BigDecimal cartedPackages;
    
    @Transient
    private String yardLocation;

    @Transient
    private String yardBlock;

    @Transient
    private String blockCellNo;
    
    @Transient
    private BigDecimal yardArea;
    
    @Transient
    private BigDecimal yardPack;

   	public BigDecimal getCartedPackages() {
   		return cartedPackages;
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






	public BigDecimal getYardArea() {
		return yardArea;
	}






	public void setYardArea(BigDecimal yardArea) {
		this.yardArea = yardArea;
	}






	public BigDecimal getYardPack() {
		return yardPack;
	}






	public void setYardPack(BigDecimal yardPack) {
		this.yardPack = yardPack;
	}






	public void setCartedPackages(BigDecimal cartedPackages) {
   		this.cartedPackages = cartedPackages;
   	}


	public ExportCarting() {
		super();
		// TODO Auto-generated constructor stub
	}

	



	
	public ExportCarting(String companyId, String branchId, String cartingTransId, String cartingLineId, String finYear,
			String profitcentreId, String sbTransId, String sbNo, String sbLineNo, Date sbDate, Date cartingTransDate,
			String gateInId, Date gateInDate, String crgExamId, String shift, String vehicleNo, String onAccountOf,
			String commodity, BigDecimal gateInPackages, BigDecimal gateInWeight, BigDecimal actualNoOfPackages,
			BigDecimal actualNoOfWeight, BigDecimal fob, String gateInType, String invoiceType, String gridLocation,
			String gridBlock, String gridCellNo, String stuffReqId, BigDecimal stuffedNoOfPackages,
			BigDecimal areaOccupied, BigDecimal yardPackages, String damageComments, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String fromSbTransId, String fromSbNo, String fromSbLineNo, Date lastStorageInvoiceDate,
			String lastStorageFlag, BigDecimal storageWeeks, BigDecimal storageDays, BigDecimal storageMonths,
			BigDecimal handlingCharges, BigDecimal excessPackages, BigDecimal shortagePackages,
			BigDecimal damagePackages, BigDecimal cartedPackages, String yardLocation, String yardBlock,
			String blockCellNo, BigDecimal yardArea, BigDecimal yardPack) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbLineNo = sbLineNo;
		this.sbDate = sbDate;
		this.cartingTransDate = cartingTransDate;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.crgExamId = crgExamId;
		this.shift = shift;
		this.vehicleNo = vehicleNo;
		this.onAccountOf = onAccountOf;
		this.commodity = commodity;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.actualNoOfPackages = actualNoOfPackages;
		this.actualNoOfWeight = actualNoOfWeight;
		this.fob = fob;
		this.gateInType = gateInType;
		this.invoiceType = invoiceType;
		this.gridLocation = gridLocation;
		this.gridBlock = gridBlock;
		this.gridCellNo = gridCellNo;
		this.stuffReqId = stuffReqId;
		this.stuffedNoOfPackages = stuffedNoOfPackages;
		this.areaOccupied = areaOccupied;
		this.yardPackages = yardPackages;
		this.damageComments = damageComments;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.fromSbTransId = fromSbTransId;
		this.fromSbNo = fromSbNo;
		this.fromSbLineNo = fromSbLineNo;
		this.lastStorageInvoiceDate = lastStorageInvoiceDate;
		this.lastStorageFlag = lastStorageFlag;
		this.storageWeeks = storageWeeks;
		this.storageDays = storageDays;
		this.storageMonths = storageMonths;
		this.handlingCharges = handlingCharges;
		this.excessPackages = excessPackages;
		this.shortagePackages = shortagePackages;
		this.damagePackages = damagePackages;
		this.cartedPackages = cartedPackages;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.yardArea = yardArea;
		this.yardPack = yardPack;
	}






	public BigDecimal getDamagePackages() {
		return damagePackages;
	}

	public void setDamagePackages(BigDecimal damagePackages) {
		this.damagePackages = damagePackages;
	}

	public BigDecimal getExcessPackages() {
		return excessPackages;
	}

	public void setExcessPackages(BigDecimal excessPackages) {
		this.excessPackages = excessPackages;
	}

	public BigDecimal getShortagePackages() {
		return shortagePackages;
	}




	public void setShortagePackages(BigDecimal shortagePackages) {
		this.shortagePackages = shortagePackages;
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

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public String getCartingLineId() {
		return cartingLineId;
	}

	public void setCartingLineId(String cartingLineId) {
		this.cartingLineId = cartingLineId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getSbLineNo() {
		return sbLineNo;
	}

	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}

	public Date getSbDate() {
		return sbDate;
	}

	public void setSbDate(Date sbDate) {
		this.sbDate = sbDate;
	}

	public Date getCartingTransDate() {
		return cartingTransDate;
	}

	public void setCartingTransDate(Date cartingTransDate) {
		this.cartingTransDate = cartingTransDate;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public String getCrgExamId() {
		return crgExamId;
	}

	public void setCrgExamId(String crgExamId) {
		this.crgExamId = crgExamId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
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

	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}

	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}

	public BigDecimal getActualNoOfWeight() {
		return actualNoOfWeight;
	}

	public void setActualNoOfWeight(BigDecimal actualNoOfWeight) {
		this.actualNoOfWeight = actualNoOfWeight;
	}

	public BigDecimal getFob() {
		return fob;
	}

	public void setFob(BigDecimal fob) {
		this.fob = fob;
	}

	public String getGateInType() {
		return gateInType;
	}

	public void setGateInType(String gateInType) {
		this.gateInType = gateInType;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getGridLocation() {
		return gridLocation;
	}

	public void setGridLocation(String gridLocation) {
		this.gridLocation = gridLocation;
	}

	public String getGridBlock() {
		return gridBlock;
	}

	public void setGridBlock(String gridBlock) {
		this.gridBlock = gridBlock;
	}

	public String getGridCellNo() {
		return gridCellNo;
	}

	public void setGridCellNo(String gridCellNo) {
		this.gridCellNo = gridCellNo;
	}

	public String getStuffReqId() {
		return stuffReqId;
	}

	public void setStuffReqId(String stuffReqId) {
		this.stuffReqId = stuffReqId;
	}

	public BigDecimal getStuffedNoOfPackages() {
		return stuffedNoOfPackages;
	}

	public void setStuffedNoOfPackages(BigDecimal stuffedNoOfPackages) {
		this.stuffedNoOfPackages = stuffedNoOfPackages;
	}

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
	}

	public BigDecimal getYardPackages() {
		return yardPackages;
	}

	public void setYardPackages(BigDecimal yardPackages) {
		this.yardPackages = yardPackages;
	}

	public String getDamageComments() {
		return damageComments;
	}

	public void setDamageComments(String damageComments) {
		this.damageComments = damageComments;
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

	public String getFromSbTransId() {
		return fromSbTransId;
	}

	public void setFromSbTransId(String fromSbTransId) {
		this.fromSbTransId = fromSbTransId;
	}

	public String getFromSbNo() {
		return fromSbNo;
	}

	public void setFromSbNo(String fromSbNo) {
		this.fromSbNo = fromSbNo;
	}

	public String getFromSbLineNo() {
		return fromSbLineNo;
	}

	public void setFromSbLineNo(String fromSbLineNo) {
		this.fromSbLineNo = fromSbLineNo;
	}

	public Date getLastStorageInvoiceDate() {
		return lastStorageInvoiceDate;
	}

	public void setLastStorageInvoiceDate(Date lastStorageInvoiceDate) {
		this.lastStorageInvoiceDate = lastStorageInvoiceDate;
	}

	public String getLastStorageFlag() {
		return lastStorageFlag;
	}

	public void setLastStorageFlag(String lastStorageFlag) {
		this.lastStorageFlag = lastStorageFlag;
	}

	public BigDecimal getStorageWeeks() {
		return storageWeeks;
	}

	public void setStorageWeeks(BigDecimal storageWeeks) {
		this.storageWeeks = storageWeeks;
	}

	public BigDecimal getStorageDays() {
		return storageDays;
	}

	public void setStorageDays(BigDecimal storageDays) {
		this.storageDays = storageDays;
	}

	public BigDecimal getStorageMonths() {
		return storageMonths;
	}

	public void setStorageMonths(BigDecimal storageMonths) {
		this.storageMonths = storageMonths;
	}

	public BigDecimal getHandlingCharges() {
		return handlingCharges;
	}

	public void setHandlingCharges(BigDecimal handlingCharges) {
		this.handlingCharges = handlingCharges;
	}


	@Override
	public String toString() {
		return "ExportCarting [companyId=" + companyId + ", branchId=" + branchId + ", cartingTransId=" + cartingTransId
				+ ", cartingLineId=" + cartingLineId + ", finYear=" + finYear + ", profitcentreId=" + profitcentreId
				+ ", sbTransId=" + sbTransId + ", sbNo=" + sbNo + ", sbLineNo=" + sbLineNo + ", sbDate=" + sbDate
				+ ", cartingTransDate=" + cartingTransDate + ", gateInId=" + gateInId + ", gateInDate=" + gateInDate
				+ ", crgExamId=" + crgExamId + ", shift=" + shift + ", vehicleNo=" + vehicleNo + ", onAccountOf="
				+ onAccountOf + ", commodity=" + commodity + ", gateInPackages=" + gateInPackages + ", gateInWeight="
				+ gateInWeight + ", actualNoOfPackages=" + actualNoOfPackages + ", actualNoOfWeight=" + actualNoOfWeight
				+ ", fob=" + fob + ", gateInType=" + gateInType + ", invoiceType=" + invoiceType + ", gridLocation="
				+ gridLocation + ", gridBlock=" + gridBlock + ", gridCellNo=" + gridCellNo + ", stuffReqId="
				+ stuffReqId + ", stuffedNoOfPackages=" + stuffedNoOfPackages + ", areaOccupied=" + areaOccupied
				+ ", yardPackages=" + yardPackages + ", damageComments=" + damageComments + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", fromSbTransId=" + fromSbTransId + ", fromSbNo=" + fromSbNo + ", fromSbLineNo=" + fromSbLineNo
				+ ", lastStorageInvoiceDate=" + lastStorageInvoiceDate + ", lastStorageFlag=" + lastStorageFlag
				+ ", storageWeeks=" + storageWeeks + ", storageDays=" + storageDays + ", storageMonths=" + storageMonths
				+ ", handlingCharges=" + handlingCharges + "]";
	}




	public ExportCarting(String companyId, String branchId, String cartingTransId, String cartingLineId, String finYear,
			String profitcentreId, String sbTransId, String sbNo, String sbLineNo, Date sbDate, Date cartingTransDate,
			String gateInId, Date gateInDate, String crgExamId, String shift, String vehicleNo, String onAccountOf,
			String commodity, BigDecimal gateInPackages, BigDecimal gateInWeight, BigDecimal actualNoOfPackages,
			BigDecimal actualNoOfWeight, BigDecimal fob, String gateInType, String invoiceType, String gridLocation,
			String gridBlock, String gridCellNo, String stuffReqId, BigDecimal stuffedNoOfPackages,
			BigDecimal areaOccupied, BigDecimal yardPackages, String damageComments, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String fromSbTransId, String fromSbNo, String fromSbLineNo, Date lastStorageInvoiceDate,
			String lastStorageFlag, BigDecimal storageWeeks, BigDecimal storageDays, BigDecimal storageMonths,
			BigDecimal handlingCharges, BigDecimal excessPackages, BigDecimal shortagePackages,
			BigDecimal damagePackages) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbLineNo = sbLineNo;
		this.sbDate = sbDate;
		this.cartingTransDate = cartingTransDate;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.crgExamId = crgExamId;
		this.shift = shift;
		this.vehicleNo = vehicleNo;
		this.onAccountOf = onAccountOf;
		this.commodity = commodity;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.actualNoOfPackages = actualNoOfPackages;
		this.actualNoOfWeight = actualNoOfWeight;
		this.fob = fob;
		this.gateInType = gateInType;
		this.invoiceType = invoiceType;
		this.gridLocation = gridLocation;
		this.gridBlock = gridBlock;
		this.gridCellNo = gridCellNo;
		this.stuffReqId = stuffReqId;
		this.stuffedNoOfPackages = stuffedNoOfPackages;
		this.areaOccupied = areaOccupied;
		this.yardPackages = yardPackages;
		this.damageComments = damageComments;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.fromSbTransId = fromSbTransId;
		this.fromSbNo = fromSbNo;
		this.fromSbLineNo = fromSbLineNo;
		this.lastStorageInvoiceDate = lastStorageInvoiceDate;
		this.lastStorageFlag = lastStorageFlag;
		this.storageWeeks = storageWeeks;
		this.storageDays = storageDays;
		this.storageMonths = storageMonths;
		this.handlingCharges = handlingCharges;
		this.excessPackages = excessPackages;
		this.shortagePackages = shortagePackages;
		this.damagePackages = damagePackages;
	}




	public ExportCarting(String cartingTransId, String sbTransId, String sbNo, String vehicleNo) {
		super();
		this.cartingTransId = cartingTransId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.vehicleNo = vehicleNo;
	}	
	
	public ExportCarting(String cartingTransId, String cartingLineId, String sbNo, String profitcentreId, String createdBy) {
		super();
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.sbNo = sbNo;
		this.profitcentreId = profitcentreId;
		this.createdBy = createdBy;
	}	
	
	
	
	public ExportCarting(String companyId, String branchId, String cartingTransId, String cartingLineId, String finYear,
			String profitcentreId, String sbTransId, String sbNo, String sbLineNo, Date sbDate, Date cartingTransDate,
			String gateInId, Date gateInDate, String crgExamId, String shift, String vehicleNo, String onAccountOf,
			String commodity, BigDecimal gateInPackages, BigDecimal gateInWeight, BigDecimal actualNoOfPackages,
			BigDecimal actualNoOfWeight, BigDecimal fob, String gateInType, String invoiceType, String gridLocation,
			String gridBlock, String gridCellNo, String stuffReqId, BigDecimal stuffedNoOfPackages,
			BigDecimal areaOccupied, BigDecimal yardPackages, String damageComments, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String fromSbTransId, String fromSbNo, String fromSbLineNo, Date lastStorageInvoiceDate,
			String lastStorageFlag, BigDecimal storageWeeks, BigDecimal storageDays, BigDecimal storageMonths,
			BigDecimal handlingCharges, BigDecimal excessPackages, BigDecimal shortagePackages,
			BigDecimal damagePackages, BigDecimal cartedPackages) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbLineNo = sbLineNo;
		this.sbDate = sbDate;
		this.cartingTransDate = cartingTransDate;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.crgExamId = crgExamId;
		this.shift = shift;
		this.vehicleNo = vehicleNo;
		this.onAccountOf = onAccountOf;
		this.commodity = commodity;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.actualNoOfPackages = actualNoOfPackages;
		this.actualNoOfWeight = actualNoOfWeight;
		this.fob = fob;
		this.gateInType = gateInType;
		this.invoiceType = invoiceType;
		this.gridLocation = gridLocation;
		this.gridBlock = gridBlock;
		this.gridCellNo = gridCellNo;
		this.stuffReqId = stuffReqId;
		this.stuffedNoOfPackages = stuffedNoOfPackages;
		this.areaOccupied = areaOccupied;
		this.yardPackages = yardPackages;
		this.damageComments = damageComments;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.fromSbTransId = fromSbTransId;
		this.fromSbNo = fromSbNo;
		this.fromSbLineNo = fromSbLineNo;
		this.lastStorageInvoiceDate = lastStorageInvoiceDate;
		this.lastStorageFlag = lastStorageFlag;
		this.storageWeeks = storageWeeks;
		this.storageDays = storageDays;
		this.storageMonths = storageMonths;
		this.handlingCharges = handlingCharges;
		this.excessPackages = excessPackages;
		this.shortagePackages = shortagePackages;
		this.damagePackages = damagePackages;
		this.cartedPackages = cartedPackages;
		
	}
	
	public ExportCarting(String cartingTransId, String cartingLineId, String sbTransId, String sbNo, String commodity,
			BigDecimal gateInPackages, BigDecimal gateInWeight, BigDecimal actualNoOfPackages,
			BigDecimal actualNoOfWeight, BigDecimal fob, String gridLocation, String gridBlock, String gridCellNo,
			BigDecimal areaOccupied, BigDecimal yardPackages, BigDecimal yardArea, BigDecimal yardPack) {
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.commodity = commodity;
		this.gateInPackages = gateInPackages;
		this.gateInWeight = gateInWeight;
		this.actualNoOfPackages = actualNoOfPackages;
		this.actualNoOfWeight = actualNoOfWeight;
		this.fob = fob;
		this.gridLocation = gridLocation;
		this.gridBlock = gridBlock;
		this.gridCellNo = gridCellNo;
		this.areaOccupied = areaOccupied;
		this.yardPackages = yardPackages;
		this.yardArea = yardArea;
		this.yardPack = yardPack;
	}


	@Override
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
}
