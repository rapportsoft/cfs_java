package com.cwms.entities;

import java.util.Date;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="TicketInfoDtl")
@IdClass(TicketInfoDtlId.class)
public class TicketInfoDtl {


	@Id
	@Column(name="Company_Id",length = 6)
	public String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	public String branchId;
	
	@Id
	@Column(name="Ticket_No",length = 15)
	public String ticketNo;
	
	@Id
	@Column(name="Sr_No")
	public int srNo;
	
	@Column(name="Ticket_Status",length = 20)
	public String ticketStatus;
	
	@Column(name="Message_From",length = 10)
	public String messageFrom;
	
	@Column(name = "Message", columnDefinition = "TEXT")
	public String message;
	
	@Column(name = "Attached-Files", columnDefinition = "TEXT")
	public String attachedFiles;
	
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
	
	@Transient
	public Map<String,byte[]> fileByteData;

	protected TicketInfoDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	



	protected TicketInfoDtl(String companyId, String branchId, String ticketNo, int srNo, String ticketStatus,
			String messageFrom, String message, String attachedFiles, String status, String createdBy, Date createdDate,
			String editedBy, Date editedDate, String approvedBy, Date approvedDate, Map<String, byte[]> fileByteData) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.ticketNo = ticketNo;
		this.srNo = srNo;
		this.ticketStatus = ticketStatus;
		this.messageFrom = messageFrom;
		this.message = message;
		this.attachedFiles = attachedFiles;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.fileByteData = fileByteData;
	}







	public Map<String, byte[]> getFileByteData() {
		return fileByteData;
	}







	public void setFileByteData(Map<String, byte[]> fileByteData) {
		this.fileByteData = fileByteData;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getMessageFrom() {
		return messageFrom;
	}

	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAttachedFiles() {
		return attachedFiles;
	}

	public void setAttachedFiles(String attachedFiles) {
		this.attachedFiles = attachedFiles;
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

	protected TicketInfoDtl(String ticketNo, int srNo, String ticketStatus, String messageFrom, String message,
			String attachedFiles, String status, String createdBy, Date createdDate) {
		super();
		this.ticketNo = ticketNo;
		this.srNo = srNo;
		this.ticketStatus = ticketStatus;
		this.messageFrom = messageFrom;
		this.message = message;
		this.attachedFiles = attachedFiles;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
	
	
	
	
	
	

}
