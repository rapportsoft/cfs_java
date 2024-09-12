package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfexmcrg")
@IdClass(ExamCrgId.class)
public class ExamCrg {
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
	    @Column(name = "Exam_Tally_Id", length = 10, nullable = false)
	    private String examTallyId;

	    @Id
	    @Column(name = "Exam_Tally_Line_Id", length = 10, nullable = false)
	    private String examTallyLineId;

	    @Column(name = "DeStuff_Id", length = 10)
	    private String deStuffId;

	    @Column(name = "DeStuff_Line_Id", length = 5)
	    private String deStuffLineId;

	    @Column(name = "DeStuff_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date deStuffDate;

	    @Column(name = "Area_Occupied", precision = 8, scale = 3)
	    private BigDecimal areaOccupied;

	    @Column(name = "On_Account_Of", length = 6)
	    private String onAccountOf;

	    @Column(name = "Exam_Tally_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date examTallyDate;

	    @Column(name = "IGM_Trans_Id", length = 10)
	    private String igmTransId;

	    @Column(name = "Igm_no", length = 10)
	    private String igmNo;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitCentreId;

	    @Column(name = "IGM_Line_No", length = 7)
	    private String igmLineNo;

	    @Column(name = "Shift", length = 6)
	    private String shift;

	    @Column(name = "Commodity_Description", length = 250)
	    private String commodityDescription;

	    @Column(name = "Periodic_Bill", length = 1)
	    private String periodicBill;

	    @Column(name = "No_Of_Packages", precision = 8)
	    private BigDecimal noOfPackages;

	    @Column(name = "Actual_No_Of_Packages", precision = 8)
	    private BigDecimal actualNoOfPackages;

	    @Column(name = "Gross_Weight", precision = 15, scale = 3)
	    private BigDecimal grossWeight;

	    @Column(name = "Gate_Out_Packages", precision = 8)
	    private BigDecimal gateOutPackages;

	    @Column(name = "Importer_Name", length = 60)
	    private String importerName;

	    @Column(name = "importer_address1", length = 250)
	    private String importerAddress1;

	    @Column(name = "importer_address2", length = 100)
	    private String importerAddress2;

	    @Column(name = "importer_address3", length = 100)
	    private String importerAddress3;

	    @Column(name = "Sample_Qty", precision = 8)
	    private BigDecimal sampleQty;

	    @Column(name = "BE_No", length = 15)
	    private String beNo;

	    @Column(name = "BE_Date")
	    @Temporal(TemporalType.DATE)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date beDate;

	    @Column(name = "BL_No", length = 20)
	    private String blNo;

	    @Column(name = "BL_Date")
	    @Temporal(TemporalType.DATE)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date blDate;

	    @Column(name = "Examiner", length = 250)
	    private String examiner;

	    @Column(name = "Examined_Percentage", precision = 3)
	    private BigDecimal examinedPercentage;

	    @Column(name = "Type_of_Cargo", length = 10)
	    private String typeOfCargo;

	    @Column(name = "Purpose", length = 250)
	    private String purpose;

	    @Column(name = "Destuff_Charges", precision = 8, scale = 2)
	    private BigDecimal destuffCharges;
	    
	    @Column(name="Cha_Code",length = 30)
	    private String chaCode;
	    
	    @Column(name="Cha_Name",length = 100)
	    private String chaName;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Column(name = "Created_Date")
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

		public ExamCrg() {
			super();
			// TODO Auto-generated constructor stub
		}

	

		public ExamCrg(String companyId, String branchId, String finYear, String examTallyId, String examTallyLineId,
				String deStuffId, String deStuffLineId, Date deStuffDate, BigDecimal areaOccupied, String onAccountOf,
				Date examTallyDate, String igmTransId, String igmNo, String profitCentreId, String igmLineNo,
				String shift, String commodityDescription, String periodicBill, BigDecimal noOfPackages,
				BigDecimal actualNoOfPackages, BigDecimal grossWeight, BigDecimal gateOutPackages, String importerName,
				String importerAddress1, String importerAddress2, String importerAddress3, BigDecimal sampleQty,
				String beNo, Date beDate, String blNo, Date blDate, String examiner, BigDecimal examinedPercentage,
				String typeOfCargo, String purpose, BigDecimal destuffCharges, String chaCode, String chaName,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.examTallyId = examTallyId;
			this.examTallyLineId = examTallyLineId;
			this.deStuffId = deStuffId;
			this.deStuffLineId = deStuffLineId;
			this.deStuffDate = deStuffDate;
			this.areaOccupied = areaOccupied;
			this.onAccountOf = onAccountOf;
			this.examTallyDate = examTallyDate;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.profitCentreId = profitCentreId;
			this.igmLineNo = igmLineNo;
			this.shift = shift;
			this.commodityDescription = commodityDescription;
			this.periodicBill = periodicBill;
			this.noOfPackages = noOfPackages;
			this.actualNoOfPackages = actualNoOfPackages;
			this.grossWeight = grossWeight;
			this.gateOutPackages = gateOutPackages;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.sampleQty = sampleQty;
			this.beNo = beNo;
			this.beDate = beDate;
			this.blNo = blNo;
			this.blDate = blDate;
			this.examiner = examiner;
			this.examinedPercentage = examinedPercentage;
			this.typeOfCargo = typeOfCargo;
			this.purpose = purpose;
			this.destuffCharges = destuffCharges;
			this.chaCode = chaCode;
			this.chaName = chaName;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
		}


		

		public String getChaCode() {
			return chaCode;
		}



		public void setChaCode(String chaCode) {
			this.chaCode = chaCode;
		}



		public String getChaName() {
			return chaName;
		}



		public void setChaName(String chaName) {
			this.chaName = chaName;
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

		public String getExamTallyId() {
			return examTallyId;
		}

		public void setExamTallyId(String examTallyId) {
			this.examTallyId = examTallyId;
		}

		public String getExamTallyLineId() {
			return examTallyLineId;
		}

		public void setExamTallyLineId(String examTallyLineId) {
			this.examTallyLineId = examTallyLineId;
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

		public BigDecimal getAreaOccupied() {
			return areaOccupied;
		}

		public void setAreaOccupied(BigDecimal areaOccupied) {
			this.areaOccupied = areaOccupied;
		}

		public String getOnAccountOf() {
			return onAccountOf;
		}

		public void setOnAccountOf(String onAccountOf) {
			this.onAccountOf = onAccountOf;
		}

		public Date getExamTallyDate() {
			return examTallyDate;
		}

		public void setExamTallyDate(Date examTallyDate) {
			this.examTallyDate = examTallyDate;
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

		public String getProfitCentreId() {
			return profitCentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
		}

		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}

		public String getShift() {
			return shift;
		}

		public void setShift(String shift) {
			this.shift = shift;
		}

		public String getCommodityDescription() {
			return commodityDescription;
		}

		public void setCommodityDescription(String commodityDescription) {
			this.commodityDescription = commodityDescription;
		}

		public String getPeriodicBill() {
			return periodicBill;
		}

		public void setPeriodicBill(String periodicBill) {
			this.periodicBill = periodicBill;
		}

		public BigDecimal getNoOfPackages() {
			return noOfPackages;
		}

		public void setNoOfPackages(BigDecimal noOfPackages) {
			this.noOfPackages = noOfPackages;
		}

		public BigDecimal getActualNoOfPackages() {
			return actualNoOfPackages;
		}

		public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
			this.actualNoOfPackages = actualNoOfPackages;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public BigDecimal getGateOutPackages() {
			return gateOutPackages;
		}

		public void setGateOutPackages(BigDecimal gateOutPackages) {
			this.gateOutPackages = gateOutPackages;
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

		public BigDecimal getSampleQty() {
			return sampleQty;
		}

		public void setSampleQty(BigDecimal sampleQty) {
			this.sampleQty = sampleQty;
		}

		public String getBeNo() {
			return beNo;
		}

		public void setBeNo(String beNo) {
			this.beNo = beNo;
		}

		public Date getBeDate() {
			return beDate;
		}

		public void setBeDate(Date beDate) {
			this.beDate = beDate;
		}

		public String getBlNo() {
			return blNo;
		}

		public void setBlNo(String blNo) {
			this.blNo = blNo;
		}

		public Date getBlDate() {
			return blDate;
		}

		public void setBlDate(Date blDate) {
			this.blDate = blDate;
		}

		public String getExaminer() {
			return examiner;
		}

		public void setExaminer(String examiner) {
			this.examiner = examiner;
		}

		public BigDecimal getExaminedPercentage() {
			return examinedPercentage;
		}

		public void setExaminedPercentage(BigDecimal examinedPercentage) {
			this.examinedPercentage = examinedPercentage;
		}

		public String getTypeOfCargo() {
			return typeOfCargo;
		}

		public void setTypeOfCargo(String typeOfCargo) {
			this.typeOfCargo = typeOfCargo;
		}

		public String getPurpose() {
			return purpose;
		}

		public void setPurpose(String purpose) {
			this.purpose = purpose;
		}

		public BigDecimal getDestuffCharges() {
			return destuffCharges;
		}

		public void setDestuffCharges(BigDecimal destuffCharges) {
			this.destuffCharges = destuffCharges;
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
	    
	    
	    
	    
	    
}
