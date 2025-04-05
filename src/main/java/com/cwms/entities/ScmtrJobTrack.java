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
@Table(name = "scmtrjobtrck")
@IdClass(ScmtrJobTrackId.class)
public class ScmtrJobTrack {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "SB_Trans_Id", length = 10)
	private String sbTransId;
	
    @Id
    @Column(name = "SB_no", length = 10)
    private String sbNo;
    
    @Id
    @Column(name = "Container_No", length = 11)
    private String containerNo;

    @Id
    @Column(name = "Job_No", length = 20)
    private String jobNo;
    
    @Id
    @Column(name = "Stuff_Tally_Id", length = 10)
    private String stuffTallyId;
    
    @Column(name = "Message_Id", length = 5)
    private String messageId;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Job_Date")
    private Date jobDate;
    
    @Column(name = "Is_Ack", length = 1)
    private String isAck;
    
    @Column(name = "Ack_Status", length = 20)
    private String ackStatus;
    
    @Column(name = "Message_String", columnDefinition = "LONGTEXT")
    private String messageString;
    
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
    
    @Column(name = "Is_Cancel_Status", length = 1)
    private String isCancelStatus;
    
    @Column(name = "Cancel_Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelCreatedDate;
    
    @Column(name = "Cancel_Ack_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cancelAckDate;
    
    @Column(name = "Cancel_Desc", length = 20)
    private String cancelDesc;
    
    @Column(name="Json_File_Path",length = 300)
    private String jsonFilePath;

	

	public ScmtrJobTrack(String companyId, String branchId, String sbTransId, String sbNo, String containerNo,
			String jobNo, String stuffTallyId, String messageId, Date jobDate, String isAck, String ackStatus,
			String messageString, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String isCancelStatus, Date cancelCreatedDate, Date cancelAckDate, String cancelDesc, String jsonFilePath) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.jobNo = jobNo;
		this.stuffTallyId = stuffTallyId;
		this.messageId = messageId;
		this.jobDate = jobDate;
		this.isAck = isAck;
		this.ackStatus = ackStatus;
		this.messageString = messageString;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.isCancelStatus = isCancelStatus;
		this.cancelCreatedDate = cancelCreatedDate;
		this.cancelAckDate = cancelAckDate;
		this.cancelDesc = cancelDesc;
		this.jsonFilePath = jsonFilePath;
	}

	public ScmtrJobTrack() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public String getJsonFilePath() {
		return jsonFilePath;
	}

	public void setJsonFilePath(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
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

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	public String getIsAck() {
		return isAck;
	}

	public void setIsAck(String isAck) {
		this.isAck = isAck;
	}

	public String getAckStatus() {
		return ackStatus;
	}

	public void setAckStatus(String ackStatus) {
		this.ackStatus = ackStatus;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
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

	public String getIsCancelStatus() {
		return isCancelStatus;
	}

	public void setIsCancelStatus(String isCancelStatus) {
		this.isCancelStatus = isCancelStatus;
	}

	public Date getCancelCreatedDate() {
		return cancelCreatedDate;
	}

	public void setCancelCreatedDate(Date cancelCreatedDate) {
		this.cancelCreatedDate = cancelCreatedDate;
	}

	public Date getCancelAckDate() {
		return cancelAckDate;
	}

	public void setCancelAckDate(Date cancelAckDate) {
		this.cancelAckDate = cancelAckDate;
	}

	public String getCancelDesc() {
		return cancelDesc;
	}

	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc;
	}

	@Override
	public String toString() {
		return "ScmtrJobTrack [companyId=" + companyId + ", branchId=" + branchId + ", sbTransId=" + sbTransId
				+ ", sbNo=" + sbNo + ", containerNo=" + containerNo + ", jobNo=" + jobNo + ", stuffTallyId="
				+ stuffTallyId + ", messageId=" + messageId + ", jobDate=" + jobDate + ", isAck=" + isAck
				+ ", ackStatus=" + ackStatus + ", messageString=" + messageString + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", isCancelStatus=" + isCancelStatus + ", cancelCreatedDate="
				+ cancelCreatedDate + ", cancelAckDate=" + cancelAckDate + ", cancelDesc=" + cancelDesc
				+ ", jsonFilePath=" + jsonFilePath + "]";
	}
    
    
}
