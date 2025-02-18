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
@Table(name="TicketInfoHdr")
@IdClass(TicketInfoId.class)
public class TicketInfo {

	@Id
	@Column(name="Company_Id",length = 6)
	public String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	public String branchId;
	
	@Id
	@Column(name="Ticket_No",length = 15)
	public String ticketNo;
	
	@Column(name="Ticket_Status",length = 20)
	public String ticketStatus;
	
	@Column(name="Requester",length = 10)
	public String requester;
	
	@Column(name="Assignee",length = 10)
	public String assignee;
	
	@Column(name="Approver",length = 10)
	public String approver;
	
	@Column(name="Followers",length = 255)
	public String followers;
	
	@Column(name="Incident_Type",length = 20)
	public String incidentType;
	
	@Column(name="Incident",length = 20)
	public String incident;
	
	@Column(name="Priority",length = 15)
	public String priority;
	
	@Column(name="Subject",length = 200)
	public String subject;
	
	@Column(name="Status",length = 1)
	public String status;
	
	@Column(name="Created_By",length = 10)
	public String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Created_Date")
	public Date createdDate;
	
	@Column(name="Edited_By",length = 10)
	public String editedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Edited_Date")
	public Date editedDate;
	
	@Column(name="Approved_By",length = 10)
	public String approvedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Approved_Date")
	public Date approvedDate;

	protected TicketInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	

	protected TicketInfo(String companyId, String branchId, String ticketNo, String ticketStatus, String requester,
			String assignee, String approver, String followers, String incidentType, String incident, String priority,
			String subject, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.ticketNo = ticketNo;
		this.ticketStatus = ticketStatus;
		this.requester = requester;
		this.assignee = assignee;
		this.approver = approver;
		this.followers = followers;
		this.incidentType = incidentType;
		this.incident = incident;
		this.priority = priority;
		this.subject = subject;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}


	



	public String getIncident() {
		return incident;
	}





	public void setIncident(String incident) {
		this.incident = incident;
	}





	public String getTicketStatus() {
		return ticketStatus;
	}



	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
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

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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





	protected TicketInfo(String ticketNo, String ticketStatus, String requester, String assignee, String priority,
			String subject, String createdBy, Date createdDate) {
		super();
		this.ticketNo = ticketNo;
		this.ticketStatus = ticketStatus;
		this.requester = requester;
		this.assignee = assignee;
		this.priority = priority;
		this.subject = subject;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
	
	
	
	
	
	
}
