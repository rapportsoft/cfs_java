package com.cwms.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="Approver_Master")
@IdClass(ApproverMasterId.class)
public class ApproverMaster {

	@Id
	@Column(name="Company_Id",length = 6)
	public String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	public String branchId;
	
	@Id
	@Column(name="Approver_Id",length = 10)
	public String approverId;
	
	@Id
	@Column(name="Approver_Name",length = 50)
	public String approverName;
	
	@Column(name="User_Id",length = 10)
	public String userId;
	
	@Column(name="Email",length = 100)
	public String email;
	
	@Column(name="Status",length = 1)
	public String status;
	
	@Column(name="Created_By",length = 10)
	public String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date createdDate;

	protected ApproverMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	protected ApproverMaster(String companyId, String branchId, String approverId, String approverName, String userId,
			String email, String status, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.approverId = approverId;
		this.approverName = approverName;
		this.userId = userId;
		this.email = email;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	protected ApproverMaster(String companyId, String branchId, String approverId, String approverName, String email,
			String status, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.approverId = approverId;
		this.approverName = approverName;
		this.email = email;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
	
	
	
}
