package com.cwms.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfscandetails")
@IdClass(ScanDetaild.class)
public class ScanDetail {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Trans_Id", length = 10)
	private String transId;

	@Id
	@Column(name = "Container_No", length = 11)
	private String containerNo;

	@Column(name = "Ref_No", length = 20)
	private String refNo;

	@Column(name = "Profitcentre_Id", length = 6)
	private String profitCentreId;

	@Column(name = "Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transDate;

	@Column(name = "Scan_Type", length = 11)
	private String scanType;

	@Column(name = "File_Name", length = 250)
	private String fileName;

	@Column(name = "Full_Line_Details",length = 400)
	private String fullLineDetails;

	@Column(name = "Scanner_Type", length = 15)
	private String scannerType;

	@Column(name = "Scanning_Updated", length = 1)
	private String scanningUpdated;

	@Column(name = "Status", length = 1)
	private String status;

	@Column(name = "Created_By", length = 10)
	private String createdBy;

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "scan_link_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date scanLinkDate;

	public ScanDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScanDetail(String companyId, String branchId, String transId, String containerNo, String refNo,
			String profitCentreId, Date transDate, String scanType, String fileName, String fullLineDetails,
			String scannerType, String scanningUpdated, String status, String createdBy, Date createdDate,
			Date scanLinkDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.transId = transId;
		this.containerNo = containerNo;
		this.refNo = refNo;
		this.profitCentreId = profitCentreId;
		this.transDate = transDate;
		this.scanType = scanType;
		this.fileName = fileName;
		this.fullLineDetails = fullLineDetails;
		this.scannerType = scannerType;
		this.scanningUpdated = scanningUpdated;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.scanLinkDate = scanLinkDate;
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

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullLineDetails() {
		return fullLineDetails;
	}

	public void setFullLineDetails(String fullLineDetails) {
		this.fullLineDetails = fullLineDetails;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}

	public String getScanningUpdated() {
		return scanningUpdated;
	}

	public void setScanningUpdated(String scanningUpdated) {
		this.scanningUpdated = scanningUpdated;
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

	public Date getScanLinkDate() {
		return scanLinkDate;
	}

	public void setScanLinkDate(Date scanLinkDate) {
		this.scanLinkDate = scanLinkDate;
	}
	
	
}
