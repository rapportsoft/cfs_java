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
@Table(name="cfexbonddetails")
@IdClass(CfexbonddtsId.class)
public class Cfexbonddts {
	
	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Noc_Trans_Id",length = 10)
	private String nocTransId;
	
	@Id
	@Column(name="In_Bonding_Id",length = 10)
	private String inBondingId;
	
	@Id
	@Column(name="Ex_Bonding_Id",length = 10)
	private String exBondingId;
	
	@Id
	@Column(name="Sr_No")
	private int srNo;
	
	@Column(name="Noc_No",length = 25)
	private String nocNo;
	
	@Column(name="Noc_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date nocValidityDate;
	
	@Column(name="Noc_Packages",precision = 8,scale = 0)
	private BigDecimal nocPackages;
	
	@Column(name="In_Bonding_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inBondingDate;
	
	@Column(name="In_Bond_Packages",precision = 8,scale = 0)
	private BigDecimal inBondPackages;
	
	
	@Column(name="Ex_Bonding_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date exBondingDate;
	
	
	@Column(name="Ex_Bond_Packages",precision = 8,scale = 0)
	private BigDecimal exBondPackages;
	
	@Column(name="Storage_Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date storageStartDate;
	
	
	@Column(name="Storage_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date storageValidityDate;
	
	
	@Column(name="Insurance_Start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insuranceStartDate;
	
	
	@Column(name="Insurance_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insuranceValidityDate;
	
	@Column(name="Balanced_Qty",precision = 8,scale = 0)
	private BigDecimal balancedQty;
	
	@Column(name = "Status", length = 1)
	private String status; // Default value
	
	@Column(name = "Assessment_Id", length = 20)
	private String assessmentId; // Default value

	@Column(name = "Created_By", length = 10)
	private String createdBy; // Default value
	
	@Column(name="Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "Edited_By", length = 10)
	private String editedBy; // Default value
	
	@Column(name="Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date editedDate;
	
	@Column(name = "Approved_By", length = 10)
	private String approvedBy; // Default value
	
	@Column(name="Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	public Cfexbonddts() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Cfexbonddts(String companyId, String branchId, String nocTransId, String inBondingId, String exBondingId,
			int srNo, String nocNo, Date nocValidityDate, BigDecimal nocPackages, Date inBondingDate,
			BigDecimal inBondPackages, Date exBondingDate, BigDecimal exBondPackages, Date storageStartDate,
			Date storageValidityDate, Date insuranceStartDate, Date insuranceValidityDate, BigDecimal balancedQty,
			String status, String assessmentId, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.inBondingId = inBondingId;
		this.exBondingId = exBondingId;
		this.srNo = srNo;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.nocPackages = nocPackages;
		this.inBondingDate = inBondingDate;
		this.inBondPackages = inBondPackages;
		this.exBondingDate = exBondingDate;
		this.exBondPackages = exBondPackages;
		this.storageStartDate = storageStartDate;
		this.storageValidityDate = storageValidityDate;
		this.insuranceStartDate = insuranceStartDate;
		this.insuranceValidityDate = insuranceValidityDate;
		this.balancedQty = balancedQty;
		this.status = status;
		this.assessmentId = assessmentId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}


	

	public String getAssessmentId() {
		return assessmentId;
	}



	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
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

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public Date getNocValidityDate() {
		return nocValidityDate;
	}

	public void setNocValidityDate(Date nocValidityDate) {
		this.nocValidityDate = nocValidityDate;
	}

	public BigDecimal getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(BigDecimal nocPackages) {
		this.nocPackages = nocPackages;
	}

	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
	}

	public BigDecimal getInBondPackages() {
		return inBondPackages;
	}

	public void setInBondPackages(BigDecimal inBondPackages) {
		this.inBondPackages = inBondPackages;
	}

	public Date getExBondingDate() {
		return exBondingDate;
	}

	public void setExBondingDate(Date exBondingDate) {
		this.exBondingDate = exBondingDate;
	}

	public BigDecimal getExBondPackages() {
		return exBondPackages;
	}

	public void setExBondPackages(BigDecimal exBondPackages) {
		this.exBondPackages = exBondPackages;
	}

	public Date getStorageStartDate() {
		return storageStartDate;
	}

	public void setStorageStartDate(Date storageStartDate) {
		this.storageStartDate = storageStartDate;
	}

	public Date getStorageValidityDate() {
		return storageValidityDate;
	}

	public void setStorageValidityDate(Date storageValidityDate) {
		this.storageValidityDate = storageValidityDate;
	}

	public Date getInsuranceStartDate() {
		return insuranceStartDate;
	}

	public void setInsuranceStartDate(Date insuranceStartDate) {
		this.insuranceStartDate = insuranceStartDate;
	}

	public Date getInsuranceValidityDate() {
		return insuranceValidityDate;
	}

	public void setInsuranceValidityDate(Date insuranceValidityDate) {
		this.insuranceValidityDate = insuranceValidityDate;
	}

	public BigDecimal getBalancedQty() {
		return balancedQty;
	}

	public void setBalancedQty(BigDecimal balancedQty) {
		this.balancedQty = balancedQty;
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
