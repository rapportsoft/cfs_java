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
@Table(name = "cfdestuffcrg")
@IdClass(DestuffCrgId.class)
public class DestuffCrg {
	  @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Fin_Year", length = 4)
	    private String finYear;

	    @Id
	    @Column(name = "DeStuff_Id", length = 10)
	    private String deStuffId;

	    @Id
	    @Column(name = "DeStuff_Line_Id", length = 4)
	    private String deStuffLineId;

	    @Column(name = "DeStuff_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date deStuffDate;

	    @Column(name = "IGM_Trans_Id", length = 10)
	    private String igmTransId;

	    @Column(name = "IGM_No", length = 10)
	    private String igmNo;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;

	    @Column(name = "IGM_Line_No", length = 7)
	    private String igmLineNo;

	    @Column(name = "Commodity_Description", length = 250)
	    private String commodityDescription;

	    @Column(name = "Comments", length = 250)
	    private String comments;

	    @Column(name = "Marks_Of_Numbers", columnDefinition = "TEXT")
	    private String marksOfNumbers;

	    @Column(name = "Gross_Weight", precision = 12, scale = 3)
	    private BigDecimal grossWeight;

	    @Column(name = "Type_Of_Packages", length = 6)
	    private String typeOfPackages;

	    @Column(name = "No_Of_Packages", precision = 8, scale = 0)
	    private Integer noOfPackages;

	    @Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	    private Integer actualNoOfPackages;

	    @Column(name = "On_Account_Of", length = 6)
	    private String onAccountOf;

	    @Column(name = "Gain_Loss_Packages", length = 10)
	    private String gainLossPackages;

	    @Column(name = "Damaged_Packages", precision = 8, scale = 0)
	    private Integer damagedPackages;

	    @Column(name = "BLGain_Loss", length = 10)
	    private String blGainLoss;

	    @Column(name = "Importer_Name", length = 60)
	    private String importerName;

	    @Column(name = "importer_address1", length = 250)
	    private String importerAddress1;

	    @Column(name = "importer_address2", length = 100)
	    private String importerAddress2;

	    @Column(name = "importer_address3", length = 100)
	    private String importerAddress3;

	    @Column(name = "Yard_Location", length = 250)
	    private String yardLocation;

	    @Column(name = "Yard_Block", length = 10)
	    private String yardBlock;

	    @Column(name = "Block_Cell_No", length = 10)
	    private String blockCellNo;

	    @Column(name = "Area_Occupied", precision = 8, scale = 3)
	    private BigDecimal areaOccupied;

	    @Column(name = "Yard_Packages", precision = 8, scale = 3)
	    private BigDecimal yardPackages;

	    @Column(name = "Exam_Tally_Id", length = 10)
	    private String examTallyId;

	    @Column(name = "Exam_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date examDate;

	    @Column(name = "Exam_Tally_Line_Id", length = 3)
	    private String examTallyLineId;

	    @Column(name = "Qty_Taken_Out", precision = 8, scale = 0)
	    private Integer qtyTakenOut;

	    @Column(name = "Invoice_Type", length = 3)
	    private String invoiceType;

	    @Column(name = "Gate_In_Type", length = 6)
	    private String gateInType;

	    @Column(name = "Destuff_Charges", precision = 8, scale = 2)
	    private BigDecimal destuffCharges;

	    @Column(name = "FOB", precision = 12, scale = 2)
	    private BigDecimal fob;

	    @Column(name = "Sample_Slip_Id", length = 10)
	    private String sampleSlipId;

	    @Column(name = "Force_Entry_Flag", length = 1)
	    private String forceEntryFlag;

	    @Column(name = "Force_Entry_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date forceEntryDate;

	    @Column(name = "Force_Entry_Approval", length = 50)
	    private String forceEntryApproval;

	    @Column(name = "Force_Entry_Remarks", length = 250)
	    private String forceEntryRemarks;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Column(name = "Created_Date")
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

	    @Column(name = "Destuff_Type", length = 20)
	    private String destuffType;

	    @Column(name = "LCL_Zero_Entry_Flag", length = 1)
	    private String lclZeroEntryFlag;

	    @Column(name = "LCL_Zero_Entry_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date lclZeroEntryDate;

	    @Column(name = "LCL_ZERO_ENTRY_Validity_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date lclZeroEntryValidityDate;

	    @Column(name = "LCL_ZERO_ENTRY_Created_By", length = 50)
	    private String lclZeroEntryCreatedBy;

	    @Column(name = "LCL_Zero_Entry_Approval", length = 50)
	    private String lclZeroEntryApproval;

	    @Column(name = "LCL_Zero_Entry_Remarks", length = 250)
	    private String lclZeroEntryRemarks;

	    @Column(name = "Actual_Weight", precision = 16, scale = 3)
	    private BigDecimal actualWeight;

	    @Column(name = "Cargo_Type", length = 10)
	    private String cargoType;

	    @Column(name = "Warehouse_Location", length = 10)
	    private String warehouseLocation;

	    @Column(name = "Movement_Type", length = 10)
	    private String movementType;

	    @Column(name = "Excess_Packages", precision = 16, scale = 3)
	    private BigDecimal excessPackages;

	    @Column(name = "Shortage_Packages", precision = 16, scale = 3)
	    private BigDecimal shortagePackages;

	    @Column(name = "Force_Entry_Flag_INV", length = 1)
	    private String forceEntryFlagInv;

		public DestuffCrg() {
			super();
			// TODO Auto-generated constructor stub
		}

		public DestuffCrg(String companyId, String branchId, String finYear, String deStuffId, String deStuffLineId,
				Date deStuffDate, String igmTransId, String igmNo, String profitcentreId, String igmLineNo,
				String commodityDescription, String comments, String marksOfNumbers, BigDecimal grossWeight,
				String typeOfPackages, Integer noOfPackages, Integer actualNoOfPackages, String onAccountOf,
				String gainLossPackages, Integer damagedPackages, String blGainLoss, String importerName,
				String importerAddress1, String importerAddress2, String importerAddress3, String yardLocation,
				String yardBlock, String blockCellNo, BigDecimal areaOccupied, BigDecimal yardPackages,
				String examTallyId, Date examDate, String examTallyLineId, Integer qtyTakenOut, String invoiceType,
				String gateInType, BigDecimal destuffCharges, BigDecimal fob, String sampleSlipId,
				String forceEntryFlag, Date forceEntryDate, String forceEntryApproval, String forceEntryRemarks,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String destuffType, String lclZeroEntryFlag, Date lclZeroEntryDate,
				Date lclZeroEntryValidityDate, String lclZeroEntryCreatedBy, String lclZeroEntryApproval,
				String lclZeroEntryRemarks, BigDecimal actualWeight, String cargoType, String warehouseLocation,
				String movementType, BigDecimal excessPackages, BigDecimal shortagePackages, String forceEntryFlagInv) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.deStuffId = deStuffId;
			this.deStuffLineId = deStuffLineId;
			this.deStuffDate = deStuffDate;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.commodityDescription = commodityDescription;
			this.comments = comments;
			this.marksOfNumbers = marksOfNumbers;
			this.grossWeight = grossWeight;
			this.typeOfPackages = typeOfPackages;
			this.noOfPackages = noOfPackages;
			this.actualNoOfPackages = actualNoOfPackages;
			this.onAccountOf = onAccountOf;
			this.gainLossPackages = gainLossPackages;
			this.damagedPackages = damagedPackages;
			this.blGainLoss = blGainLoss;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.areaOccupied = areaOccupied;
			this.yardPackages = yardPackages;
			this.examTallyId = examTallyId;
			this.examDate = examDate;
			this.examTallyLineId = examTallyLineId;
			this.qtyTakenOut = qtyTakenOut;
			this.invoiceType = invoiceType;
			this.gateInType = gateInType;
			this.destuffCharges = destuffCharges;
			this.fob = fob;
			this.sampleSlipId = sampleSlipId;
			this.forceEntryFlag = forceEntryFlag;
			this.forceEntryDate = forceEntryDate;
			this.forceEntryApproval = forceEntryApproval;
			this.forceEntryRemarks = forceEntryRemarks;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.destuffType = destuffType;
			this.lclZeroEntryFlag = lclZeroEntryFlag;
			this.lclZeroEntryDate = lclZeroEntryDate;
			this.lclZeroEntryValidityDate = lclZeroEntryValidityDate;
			this.lclZeroEntryCreatedBy = lclZeroEntryCreatedBy;
			this.lclZeroEntryApproval = lclZeroEntryApproval;
			this.lclZeroEntryRemarks = lclZeroEntryRemarks;
			this.actualWeight = actualWeight;
			this.cargoType = cargoType;
			this.warehouseLocation = warehouseLocation;
			this.movementType = movementType;
			this.excessPackages = excessPackages;
			this.shortagePackages = shortagePackages;
			this.forceEntryFlagInv = forceEntryFlagInv;
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

		public String getDeStuffId() {
			return deStuffId;
		}

		public void setDeStuffId(String deStuffId) {
			this.deStuffId = deStuffId;
		}

		public String getDeStuffLineId() {
			return deStuffLineId;
		}

		public void setDeStuffLineId(String deStuffLineId) {
			this.deStuffLineId = deStuffLineId;
		}

		public Date getDeStuffDate() {
			return deStuffDate;
		}

		public void setDeStuffDate(Date deStuffDate) {
			this.deStuffDate = deStuffDate;
		}

		public String getIgmTransId() {
			return igmTransId;
		}

		public void setIgmTransId(String igmTransId) {
			this.igmTransId = igmTransId;
		}

		public String getIgmNo() {
			return igmNo;
		}

		public void setIgmNo(String igmNo) {
			this.igmNo = igmNo;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}

		public String getCommodityDescription() {
			return commodityDescription;
		}

		public void setCommodityDescription(String commodityDescription) {
			this.commodityDescription = commodityDescription;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getMarksOfNumbers() {
			return marksOfNumbers;
		}

		public void setMarksOfNumbers(String marksOfNumbers) {
			this.marksOfNumbers = marksOfNumbers;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public String getTypeOfPackages() {
			return typeOfPackages;
		}

		public void setTypeOfPackages(String typeOfPackages) {
			this.typeOfPackages = typeOfPackages;
		}

		public Integer getNoOfPackages() {
			return noOfPackages;
		}

		public void setNoOfPackages(Integer noOfPackages) {
			this.noOfPackages = noOfPackages;
		}

		public Integer getActualNoOfPackages() {
			return actualNoOfPackages;
		}

		public void setActualNoOfPackages(Integer actualNoOfPackages) {
			this.actualNoOfPackages = actualNoOfPackages;
		}

		public String getOnAccountOf() {
			return onAccountOf;
		}

		public void setOnAccountOf(String onAccountOf) {
			this.onAccountOf = onAccountOf;
		}

		public String getGainLossPackages() {
			return gainLossPackages;
		}

		public void setGainLossPackages(String gainLossPackages) {
			this.gainLossPackages = gainLossPackages;
		}

		public Integer getDamagedPackages() {
			return damagedPackages;
		}

		public void setDamagedPackages(Integer damagedPackages) {
			this.damagedPackages = damagedPackages;
		}

		public String getBlGainLoss() {
			return blGainLoss;
		}

		public void setBlGainLoss(String blGainLoss) {
			this.blGainLoss = blGainLoss;
		}

		public String getImporterName() {
			return importerName;
		}

		public void setImporterName(String importerName) {
			this.importerName = importerName;
		}

		public String getImporterAddress1() {
			return importerAddress1;
		}

		public void setImporterAddress1(String importerAddress1) {
			this.importerAddress1 = importerAddress1;
		}

		public String getImporterAddress2() {
			return importerAddress2;
		}

		public void setImporterAddress2(String importerAddress2) {
			this.importerAddress2 = importerAddress2;
		}

		public String getImporterAddress3() {
			return importerAddress3;
		}

		public void setImporterAddress3(String importerAddress3) {
			this.importerAddress3 = importerAddress3;
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

		public String getExamTallyId() {
			return examTallyId;
		}

		public void setExamTallyId(String examTallyId) {
			this.examTallyId = examTallyId;
		}

		public Date getExamDate() {
			return examDate;
		}

		public void setExamDate(Date examDate) {
			this.examDate = examDate;
		}

		public String getExamTallyLineId() {
			return examTallyLineId;
		}

		public void setExamTallyLineId(String examTallyLineId) {
			this.examTallyLineId = examTallyLineId;
		}

		public Integer getQtyTakenOut() {
			return qtyTakenOut;
		}

		public void setQtyTakenOut(Integer qtyTakenOut) {
			this.qtyTakenOut = qtyTakenOut;
		}

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getGateInType() {
			return gateInType;
		}

		public void setGateInType(String gateInType) {
			this.gateInType = gateInType;
		}

		public BigDecimal getDestuffCharges() {
			return destuffCharges;
		}

		public void setDestuffCharges(BigDecimal destuffCharges) {
			this.destuffCharges = destuffCharges;
		}

		public BigDecimal getFob() {
			return fob;
		}

		public void setFob(BigDecimal fob) {
			this.fob = fob;
		}

		public String getSampleSlipId() {
			return sampleSlipId;
		}

		public void setSampleSlipId(String sampleSlipId) {
			this.sampleSlipId = sampleSlipId;
		}

		public String getForceEntryFlag() {
			return forceEntryFlag;
		}

		public void setForceEntryFlag(String forceEntryFlag) {
			this.forceEntryFlag = forceEntryFlag;
		}

		public Date getForceEntryDate() {
			return forceEntryDate;
		}

		public void setForceEntryDate(Date forceEntryDate) {
			this.forceEntryDate = forceEntryDate;
		}

		public String getForceEntryApproval() {
			return forceEntryApproval;
		}

		public void setForceEntryApproval(String forceEntryApproval) {
			this.forceEntryApproval = forceEntryApproval;
		}

		public String getForceEntryRemarks() {
			return forceEntryRemarks;
		}

		public void setForceEntryRemarks(String forceEntryRemarks) {
			this.forceEntryRemarks = forceEntryRemarks;
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

		public String getDestuffType() {
			return destuffType;
		}

		public void setDestuffType(String destuffType) {
			this.destuffType = destuffType;
		}

		public String getLclZeroEntryFlag() {
			return lclZeroEntryFlag;
		}

		public void setLclZeroEntryFlag(String lclZeroEntryFlag) {
			this.lclZeroEntryFlag = lclZeroEntryFlag;
		}

		public Date getLclZeroEntryDate() {
			return lclZeroEntryDate;
		}

		public void setLclZeroEntryDate(Date lclZeroEntryDate) {
			this.lclZeroEntryDate = lclZeroEntryDate;
		}

		public Date getLclZeroEntryValidityDate() {
			return lclZeroEntryValidityDate;
		}

		public void setLclZeroEntryValidityDate(Date lclZeroEntryValidityDate) {
			this.lclZeroEntryValidityDate = lclZeroEntryValidityDate;
		}

		public String getLclZeroEntryCreatedBy() {
			return lclZeroEntryCreatedBy;
		}

		public void setLclZeroEntryCreatedBy(String lclZeroEntryCreatedBy) {
			this.lclZeroEntryCreatedBy = lclZeroEntryCreatedBy;
		}

		public String getLclZeroEntryApproval() {
			return lclZeroEntryApproval;
		}

		public void setLclZeroEntryApproval(String lclZeroEntryApproval) {
			this.lclZeroEntryApproval = lclZeroEntryApproval;
		}

		public String getLclZeroEntryRemarks() {
			return lclZeroEntryRemarks;
		}

		public void setLclZeroEntryRemarks(String lclZeroEntryRemarks) {
			this.lclZeroEntryRemarks = lclZeroEntryRemarks;
		}

		public BigDecimal getActualWeight() {
			return actualWeight;
		}

		public void setActualWeight(BigDecimal actualWeight) {
			this.actualWeight = actualWeight;
		}

		public String getCargoType() {
			return cargoType;
		}

		public void setCargoType(String cargoType) {
			this.cargoType = cargoType;
		}

		public String getWarehouseLocation() {
			return warehouseLocation;
		}

		public void setWarehouseLocation(String warehouseLocation) {
			this.warehouseLocation = warehouseLocation;
		}

		public String getMovementType() {
			return movementType;
		}

		public void setMovementType(String movementType) {
			this.movementType = movementType;
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

		public String getForceEntryFlagInv() {
			return forceEntryFlagInv;
		}

		public void setForceEntryFlagInv(String forceEntryFlagInv) {
			this.forceEntryFlagInv = forceEntryFlagInv;
		}

		public DestuffCrg(String deStuffId, String deStuffLineId, Date deStuffDate, String igmTransId, String igmNo,
				String igmLineNo, String commodityDescription, BigDecimal grossWeight, Integer noOfPackages,
				String importerName, String importerAddress1, String importerAddress2, String importerAddress3,
				BigDecimal areaOccupied, String cargoType, String profitcentreId) {
			super();
			this.deStuffId = deStuffId;
			this.deStuffLineId = deStuffLineId;
			this.deStuffDate = deStuffDate;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.igmLineNo = igmLineNo;
			this.commodityDescription = commodityDescription;
			this.grossWeight = grossWeight;
			this.noOfPackages = noOfPackages;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.areaOccupied = areaOccupied;
			this.cargoType = cargoType;
			this.profitcentreId = profitcentreId;
		}
	    
	    
	    
}
