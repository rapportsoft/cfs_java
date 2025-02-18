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
@Table(name="AssigneeMaster")
@IdClass(AssigneeMasterId.class)
public class AssigneeMaster {

	@Id
	@Column(name="Company_Id",length = 6)
	public String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	public String branchId;
	
	@Id
	@Column(name="Assignee_Id",length = 10)
	public String assigneeId;
	
	@Id
	@Column(name="Assignee_Name",length = 50)
	public String assigneeName;
	
	@Column(name="Email",length = 100)
	public String email;
	
	@Column(name="Status",length = 1)
	public String status;
	
	@Column(name="Created_By",length = 10)
	public String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date createdDate;

	protected AssigneeMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected AssigneeMaster(String companyId, String branchId, String assigneeId, String assigneeName, String email,
			String status, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.assigneeId = assigneeId;
		this.assigneeName = assigneeName;
		this.email = email;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
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

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
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

	
}
