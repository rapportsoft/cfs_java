package com.cwms.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "jobentry")
@IdClass(GeneralJobID.class)
public class GenerelJobEntry implements Serializable {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId;

	@Id
	@Column(name = "Jon_Trans_Id", length = 10, nullable = false)
	private String jobTransId;

	@Id
	@Column(name = "Job_No", length = 25, nullable = true)
	private String jobNo;
	
	@Column(name = "Profitcentre_Id", length = 6, nullable = true)
	private String profitcentreId;

	@Column(name = "JOB_Trans_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobTransDate;
	

	@Column(name = "Job_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobDate;
	
	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId;

	@Column(name = "BOE_No", length = 15, nullable = true)
	private String boeNo;

	@Column(name = "BOE_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date boeDate;

	@Column(name = "CHA", length = 6)
	private String cha;

	@Column(name = "Cargo_Type", length = 6)
	private String cargoType;

	@Column(name = "Imp_Sr_No", nullable = true)
	private int impSrNo;

	@Column(name = "Importer_Id", length = 7, nullable = true)
	private String importerId;

	@Column(name = "Importer_Name", length = 60, nullable = true)
	private String importerName;

	@Column(name = "importer_address1", length = 250)
	private String importerAddress1;

	@Column(name = "importer_address2", length = 100)
	private String importerAddress2;

	@Column(name = "importer_address3", length = 100)
	private String importerAddress3;

	@Column(name = "Forwarder", length = 90, nullable = true)
	private String forwarder;
	
	@Column(name = "Gross_Weight", precision = 16, scale = 3, nullable = true)
	private BigDecimal grossWeight;

	@Column(name = "Area", precision = 5, scale = 0, nullable = true)
	private BigDecimal area;
	
	@Column(name = "NO_Of_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal noOfPackages;
	
	@Column(name = "Gate_In_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal gateInPackages;
	
	@Column(name = "Number_Of_Marks", columnDefinition = "TEXT")
	private String numberOfMarks;
	
	@Column(name = "Package_Or_Weight", length = 10, nullable = true)
	private String packageOrWeight;

	@Column(name = "No_of_20FT", length = 5, nullable = true)
	private String noOf20ft;

	@Column(name = "No_of_40FT", length = 5, nullable = true)
	private String noOf40ft;
	
	@Column(name = "Go_down_no", length = 5, nullable = true)
	private String godownNo;

	
	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;


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

	@Column(name = "Comments", length = 150)
	private String comments;

	@Column(name = "Status", length = 1, nullable = true)
	private String status;
	
	@Transient
	private String forwarderName;
	
	

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}

	public String getForwarderName() {
		return forwarderName;
	}

	public void setForwarderName(String forwarderName) {
		this.forwarderName = forwarderName;
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

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public Date getBoeDate() {
		return boeDate;
	}

	public void setBoeDate(Date boeDate) {
		this.boeDate = boeDate;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public int getImpSrNo() {
		return impSrNo;
	}

	public void setImpSrNo(int impSrNo) {
		this.impSrNo = impSrNo;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
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

	public String getForwarder() {
		return forwarder;
	}

	public void setForwarder(String forwarder) {
		this.forwarder = forwarder;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public String getNumberOfMarks() {
		return numberOfMarks;
	}

	public void setNumberOfMarks(String numberOfMarks) {
		this.numberOfMarks = numberOfMarks;
	}

	public String getPackageOrWeight() {
		return packageOrWeight;
	}

	public void setPackageOrWeight(String packageOrWeight) {
		this.packageOrWeight = packageOrWeight;
	}

	public String getNoOf20ft() {
		return noOf20ft;
	}

	public void setNoOf20ft(String noOf20ft) {
		this.noOf20ft = noOf20ft;
	}

	public String getNoOf40ft() {
		return noOf40ft;
	}

	public void setNoOf40ft(String noOf40ft) {
		this.noOf40ft = noOf40ft;
	}

	public String getGodownNo() {
		return godownNo;
	}

	public void setGodownNo(String godownNo) {
		this.godownNo = godownNo;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public GenerelJobEntry(String companyId, String branchId, String jobTransId, String jobNo, String profitcentreId,
			Date jobTransDate, Date jobDate, String gateInId, String boeNo, Date boeDate, String cha, String cargoType,
			int impSrNo, String importerId, String importerName, String importerAddress1, String importerAddress2,
			String importerAddress3, String forwarder, BigDecimal grossWeight, BigDecimal area, BigDecimal noOfPackages,
			BigDecimal gateInPackages, String numberOfMarks, String packageOrWeight, String noOf20ft, String noOf40ft,
			String godownNo, Date approvedDate, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, String comments, String status, String forwarderName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.profitcentreId = profitcentreId;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.cha = cha;
		this.cargoType = cargoType;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.forwarder = forwarder;
		this.grossWeight = grossWeight;
		this.area = area;
		this.noOfPackages = noOfPackages;
		this.gateInPackages = gateInPackages;
		this.numberOfMarks = numberOfMarks;
		this.packageOrWeight = packageOrWeight;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.godownNo = godownNo;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.comments = comments;
		this.status = status;
		this.forwarderName = forwarderName;
	}

	public GenerelJobEntry() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	@Override
	public String toString() {
		return "GenerelJobEntry [companyId=" + companyId + ", branchId=" + branchId + ", jobTransId=" + jobTransId
				+ ", jobNo=" + jobNo + ", profitcentreId=" + profitcentreId + ", jobTransDate=" + jobTransDate
				+ ", jobDate=" + jobDate + ", gateInId=" + gateInId + ", boeNo=" + boeNo + ", boeDate=" + boeDate
				+ ", cha=" + cha + ", cargoType=" + cargoType + ", impSrNo=" + impSrNo + ", importerId=" + importerId
				+ ", importerName=" + importerName + ", importerAddress1=" + importerAddress1 + ", importerAddress2="
				+ importerAddress2 + ", importerAddress3=" + importerAddress3 + ", forwarder=" + forwarder
				+ ", grossWeight=" + grossWeight + ", area=" + area + ", noOfPackages=" + noOfPackages
				+ ", gateInPackages=" + gateInPackages + ", numberOfMarks=" + numberOfMarks + ", packageOrWeight="
				+ packageOrWeight + ", noOf20ft=" + noOf20ft + ", noOf40ft=" + noOf40ft + ", godownNo=" + godownNo
				+ ", approvedDate=" + approvedDate + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", comments="
				+ comments + ", status=" + status + ", forwarderName=" + forwarderName + "]";
	}

	//getAllData
	public GenerelJobEntry(String companyId, String branchId, String jobTransId, String jobNo, String profitcentreId,
			Date jobTransDate, Date jobDate, String gateInId, String boeNo, Date boeDate, String cha, String cargoType,
			int impSrNo, String importerId, String importerName, String importerAddress1, String importerAddress2,
			String importerAddress3, String forwarder, BigDecimal grossWeight, BigDecimal area, BigDecimal noOfPackages,
			String numberOfMarks, String packageOrWeight, String noOf20ft, String noOf40ft, String godownNo,
			Date approvedDate, String createdBy, Date createdDate, String approvedBy, String comments, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.profitcentreId = profitcentreId;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.cha = cha;
		this.cargoType = cargoType;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.forwarder = forwarder;
		this.grossWeight = grossWeight;
		this.area = area;
		this.noOfPackages = noOfPackages;
		this.numberOfMarks = numberOfMarks;
		this.packageOrWeight = packageOrWeight;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.godownNo = godownNo;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.comments = comments;
		this.status = status;
	}

	public GenerelJobEntry(String companyId, String branchId, String jobTransId, String jobNo, String profitcentreId,
			Date jobTransDate, Date jobDate, String gateInId, String boeNo, Date boeDate, String cha, String cargoType,
			int impSrNo, String importerId, String importerName, String importerAddress1, String importerAddress2,
			String importerAddress3, String forwarder, BigDecimal grossWeight, BigDecimal area, BigDecimal noOfPackages,
			String numberOfMarks, String packageOrWeight, String noOf20ft, String noOf40ft, String godownNo,
			Date approvedDate, String createdBy, Date createdDate, String approvedBy, String comments, String status,String editedBy,String forwarderName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.profitcentreId = profitcentreId;
		this.jobTransDate = jobTransDate;
		this.jobDate = jobDate;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.cha = cha;
		this.cargoType = cargoType;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.forwarder = forwarder;
		this.grossWeight = grossWeight;
		this.area = area;
		this.noOfPackages = noOfPackages;
		this.numberOfMarks = numberOfMarks;
		this.packageOrWeight = packageOrWeight;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.godownNo = godownNo;
		this.approvedDate = approvedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.comments = comments;
		this.status = status;
		this.editedBy=editedBy;
		this.forwarderName=forwarderName;
	}
	
	
	public GenerelJobEntry(String companyId, String branchId, String jobTransId, String jobNo,String boeNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.jobNo = jobNo;
		this.boeNo = boeNo;

	}



	
	
	
}
