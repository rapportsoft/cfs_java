package com.cwms.entities;

import java.io.Serializable;
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
import jakarta.persistence.Transient;

@Entity
@Table(name = "Jobentrydetails")
@IdClass(GeneralJobDetailId.class)
public class GeneralJobOrderEntryDetails implements Serializable {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = true)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = true)
	private String branchId;

	@Id
	@Column(name = "Jon_Trans_Id", length = 10, nullable = true)
	private String jobTransId;
	
	@Id
	@Column(name = "Jon_Dtl_Trans_Id", length = 10, nullable = true)
	private String jobDtlTransId;

	@Id
	@Column(name = "Job_No", length = 25, nullable = true)
	private String jobNo;
	

	@Column(name = "Commodity_Id", length = 25, nullable = true)
	private String commodityId;

	@Column(name = "JOB_Trans_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobTransDate;
	
	@Column(name = "Job_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobDate;


	@Column(name = "BOE_No", length = 15, nullable = true)
	private String boeNo;

	
	@Column(name = "Profitcentre_Id", length = 6, nullable = true)
	private String profitcentreId;

	
	





	@Column(name = "Gate_In_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal gateInPackages = BigDecimal.ZERO;

	@Column(name = "Weight_Taken_In", precision = 12, scale = 2)
	private BigDecimal weightTakenIn = BigDecimal.ZERO;

	
	@Column(name = "TYPE_OF_PACKAGE", length = 15)
	private String typeOfPackage;

	@Column(name = "Commodity_Description", length = 250, nullable = true)
	private String commodityDescription;
	
	@Column(name = "Gross_Weight", precision = 16, scale = 3, nullable = true)
	private BigDecimal grossWeight;

	@Column(name = "No_Of_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal noOfPackages = BigDecimal.ZERO;

	@Column(name = "Area_Occupied", precision = 16, scale = 3, nullable = true)
	private BigDecimal areaOccupied;


	@Column(name = "Created_By", length = 10)
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
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;
	
	@Column(name = "Status", length = 1, nullable = true)
	private String status;

	public GeneralJobOrderEntryDetails() {
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

	public String getJobTransId() {
		return jobTransId;
	}

	public void setJobTransId(String jobTransId) {
		this.jobTransId = jobTransId;
	}

	public String getJobDtlTransId() {
		return jobDtlTransId;
	}

	public void setJobDtlTransId(String jobDtlTransId) {
		this.jobDtlTransId = jobDtlTransId;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public Date getJobTransDate() {
		return jobTransDate;
	}

	public void setJobTransDate(Date jobTransDate) {
		this.jobTransDate = jobTransDate;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}


	public BigDecimal getWeightTakenIn() {
		return weightTakenIn;
	}

	public void setWeightTakenIn(BigDecimal weightTakenIn) {
		this.weightTakenIn = weightTakenIn;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public String getCommodityId() {
		return commodityId;
	}



	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}



	public GeneralJobOrderEntryDetails(String companyId, String branchId, String jobTransId, String jobDtlTransId,
			String jobNo, String commodityId, Date jobTransDate, Date jobDate, String boeNo, String profitcentreId,
			BigDecimal gateInPackages,BigDecimal weightTakenIn, String typeOfPackage,
			String commodityDescription, BigDecimal grossWeight, BigDecimal noOfPackages, BigDecimal areaOccupied,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobDtlTransId = jobDtlTransId;
		this.jobNo = jobNo;
		this.commodityId = commodityId;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.boeNo = boeNo;
		this.profitcentreId = profitcentreId;
		this.gateInPackages = gateInPackages;
		this.weightTakenIn = weightTakenIn;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.noOfPackages = noOfPackages;
		this.areaOccupied = areaOccupied;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}



	@Override
	public String toString() {
		return "GeneralJobOrderEntryDetails [companyId=" + companyId + ", branchId=" + branchId + ", jobTransId="
				+ jobTransId + ", jobDtlTransId=" + jobDtlTransId + ", jobNo=" + jobNo + ", commodityId=" + commodityId
				+ ", jobTransDate=" + jobTransDate + ", jobDate=" + jobDate + ", boeNo=" + boeNo + ", profitcentreId="
				+ profitcentreId + ", gateInPackages=" + gateInPackages + ", qtyTakenIn=" + gateInPackages
				+ ", weightTakenIn=" + weightTakenIn + ", typeOfPackage=" + typeOfPackage + ", commodityDescription="
				+ commodityDescription + ", grossWeight=" + grossWeight + ", noOfPackages=" + noOfPackages
				+ ", areaOccupied=" + areaOccupied + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", status=" + status + "]";
	}



	public GeneralJobOrderEntryDetails(String companyId, String branchId, String jobTransId, String jobDtlTransId,
			String jobNo, String commodityId, Date jobTransDate, Date jobDate, String boeNo, String profitcentreId,
			String typeOfPackage, String commodityDescription, BigDecimal grossWeight, BigDecimal noOfPackages,
			BigDecimal areaOccupied, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobDtlTransId = jobDtlTransId;
		this.jobNo = jobNo;
		this.commodityId = commodityId;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.boeNo = boeNo;
		this.profitcentreId = profitcentreId;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.noOfPackages = noOfPackages;
		this.areaOccupied = areaOccupied;
		this.status = status;
	}

	
	
	
	
	
	
	
	
	

	
	
}
