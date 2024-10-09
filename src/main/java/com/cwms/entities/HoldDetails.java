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
@IdClass(HoldDetailsId.class)
@Table(name="cfholddtl")
public class HoldDetails {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Container_No", length = 11)
	private String containerNo;

	@Id
	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId;

	@Id
	@Column(name = "Doc_Ref_No", length = 10)
	private String docRefNo;

	@Id
	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;

	@Id
	@Column(name = "IGM_No", length = 21)
	private String igmNo;

	@Id
	@Column(name = "Hld_Sr_No")
	private int hldSrNo;

	@Column(name = "IGM_Line_No", length = 10)
	private String igmLineNo;

	@Column(name = "Doc_Ref_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date docRefDate;

	@Column(name = "Hold_Status", length = 1)
	private String holdStatus;

	@Column(name = "Holding_AGency", length = 25)
	private String holdingAgency;

	@Column(name = "Hold_User", length = 35)
	private String holdUser;

	@Column(name = "Hold_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date holdDate;

	@Column(name = "Hold_Remarks", columnDefinition = "TEXT")
	private String holdRemarks;

	@Column(name = "Release_User", length = 35)
	private String releaseUser;

	@Column(name = "Release_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseDate;

	@Column(name = "Release_Remarks", columnDefinition = "TEXT")
	private String releaseRemarks;

	@Column(name = "File_no", length = 50)
	private String fileNo;


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

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "Status", length = 1)
	private String status;

	@Column(name = "hold_send_Flag", length = 1)
	private String holdSendFlag;

	@Column(name = "hold_send_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date holdSendDate;

	@Column(name = "hold_send_Response", length = 100)
	private String holdSendResponse;

	@Column(name = "Release_Send_Flag", length = 1)
	private String releaseSendFlag;

	@Column(name = "Release_Send_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date releaseSendDate;

	@Column(name = "Release_Send_Response", length = 100)
	private String releaseSendResponse;

	public HoldDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HoldDetails(String companyId, String branchId, String containerNo, String gateInId, String docRefNo,
			String igmTransId, String igmNo, int hldSrNo, String igmLineNo, Date docRefDate, String holdStatus,
			String holdingAgency, String holdUser, Date holdDate, String holdRemarks, String releaseUser,
			Date releaseDate, String releaseRemarks, String fileNo, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String status, String holdSendFlag,
			Date holdSendDate, String holdSendResponse, String releaseSendFlag, Date releaseSendDate,
			String releaseSendResponse) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.containerNo = containerNo;
		this.gateInId = gateInId;
		this.docRefNo = docRefNo;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.hldSrNo = hldSrNo;
		this.igmLineNo = igmLineNo;
		this.docRefDate = docRefDate;
		this.holdStatus = holdStatus;
		this.holdingAgency = holdingAgency;
		this.holdUser = holdUser;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.releaseUser = releaseUser;
		this.releaseDate = releaseDate;
		this.releaseRemarks = releaseRemarks;
		this.fileNo = fileNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.holdSendFlag = holdSendFlag;
		this.holdSendDate = holdSendDate;
		this.holdSendResponse = holdSendResponse;
		this.releaseSendFlag = releaseSendFlag;
		this.releaseSendDate = releaseSendDate;
		this.releaseSendResponse = releaseSendResponse;
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

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
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

	public int getHldSrNo() {
		return hldSrNo;
	}

	public void setHldSrNo(int hldSrNo) {
		this.hldSrNo = hldSrNo;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public Date getDocRefDate() {
		return docRefDate;
	}

	public void setDocRefDate(Date docRefDate) {
		this.docRefDate = docRefDate;
	}

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getHoldingAgency() {
		return holdingAgency;
	}

	public void setHoldingAgency(String holdingAgency) {
		this.holdingAgency = holdingAgency;
	}

	public String getHoldUser() {
		return holdUser;
	}

	public void setHoldUser(String holdUser) {
		this.holdUser = holdUser;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public String getHoldRemarks() {
		return holdRemarks;
	}

	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}

	public String getReleaseUser() {
		return releaseUser;
	}

	public void setReleaseUser(String releaseUser) {
		this.releaseUser = releaseUser;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseRemarks() {
		return releaseRemarks;
	}

	public void setReleaseRemarks(String releaseRemarks) {
		this.releaseRemarks = releaseRemarks;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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

	public String getHoldSendFlag() {
		return holdSendFlag;
	}

	public void setHoldSendFlag(String holdSendFlag) {
		this.holdSendFlag = holdSendFlag;
	}

	public Date getHoldSendDate() {
		return holdSendDate;
	}

	public void setHoldSendDate(Date holdSendDate) {
		this.holdSendDate = holdSendDate;
	}

	public String getHoldSendResponse() {
		return holdSendResponse;
	}

	public void setHoldSendResponse(String holdSendResponse) {
		this.holdSendResponse = holdSendResponse;
	}

	public String getReleaseSendFlag() {
		return releaseSendFlag;
	}

	public void setReleaseSendFlag(String releaseSendFlag) {
		this.releaseSendFlag = releaseSendFlag;
	}

	public Date getReleaseSendDate() {
		return releaseSendDate;
	}

	public void setReleaseSendDate(Date releaseSendDate) {
		this.releaseSendDate = releaseSendDate;
	}

	public String getReleaseSendResponse() {
		return releaseSendResponse;
	}

	public void setReleaseSendResponse(String releaseSendResponse) {
		this.releaseSendResponse = releaseSendResponse;
	}

	
	
	
}
