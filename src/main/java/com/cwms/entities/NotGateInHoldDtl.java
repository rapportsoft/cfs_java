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
@Table(name="cfnotgateinholddtl")
@IdClass(NotGateInHoldDtlId.class)
public class NotGateInHoldDtl {

	@Id
	@Column(name = "Company_Id", length = 6)
	public String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	public String branchId;
	
	@Id
	@Column(name = "Sr_No")
	public int srNo;
	
	@Id
	@Column(name = "Doc_Ref_No", length = 10)
	public String docRefNo;
	
	@Id
	@Column(name = "Container_No", length = 11)
	public String containerNo;
	
	@Column(name = "Gate_In_Id", length = 20)
	public String gateInId;
	
	@Column(name = "Hold_Status", length = 1)
	public String holdStatus;
	
	@Column(name = "CSD", length = 10)
	public String csd;
	
	@Column(name = "Csd_Hold_User_Name", length = 35)
	public String csdHoldUserName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="Csd_Hold_Date")
	public Date csdHoldDate;
	
	@Column(name = "Csd_Hold_Remarks", columnDefinition = "TEXT")
	public String csdHoldRemarks;
	
	@Column(name = "Status", length = 1)
	public String status;
	
	@Column(name = "Created_By", length = 10)
	public String createdBy;
	
	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date createdDate;
	
	@Column(name = "Edited_By", length = 10)
	public String editedBy;
	
	@Column(name = "Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date editedDate;
	
	@Column(name = "Approved_By", length = 10)
	public String approvedBy;
	
	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date approvedDate;
	
	@Column(name = "File_Upload_Path", length = 300)
	public String fileUploadPath;
	
	@Column(name = "Merge_Flag", length = 1)
	public String mergeFlag;
	
	@Column(name = "Merge_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date mergeDate;

	public NotGateInHoldDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public NotGateInHoldDtl(String companyId, String branchId, int srNo, String docRefNo, String containerNo,
			String gateInId, String holdStatus, String csd, String csdHoldUserName, Date csdHoldDate,
			String csdHoldRemarks, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, String fileUploadPath, String mergeFlag, Date mergeDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.srNo = srNo;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
		this.gateInId = gateInId;
		this.holdStatus = holdStatus;
		this.csd = csd;
		this.csdHoldUserName = csdHoldUserName;
		this.csdHoldDate = csdHoldDate;
		this.csdHoldRemarks = csdHoldRemarks;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.fileUploadPath = fileUploadPath;
		this.mergeFlag = mergeFlag;
		this.mergeDate = mergeDate;
	}

	


	public String getFileUploadPath() {
		return fileUploadPath;
	}



	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
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

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getCsd() {
		return csd;
	}

	public void setCsd(String csd) {
		this.csd = csd;
	}

	public String getCsdHoldUserName() {
		return csdHoldUserName;
	}

	public void setCsdHoldUserName(String csdHoldUserName) {
		this.csdHoldUserName = csdHoldUserName;
	}

	public Date getCsdHoldDate() {
		return csdHoldDate;
	}

	public void setCsdHoldDate(Date csdHoldDate) {
		this.csdHoldDate = csdHoldDate;
	}

	public String getCsdHoldRemarks() {
		return csdHoldRemarks;
	}

	public void setCsdHoldRemarks(String csdHoldRemarks) {
		this.csdHoldRemarks = csdHoldRemarks;
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

	public String getMergeFlag() {
		return mergeFlag;
	}

	public void setMergeFlag(String mergeFlag) {
		this.mergeFlag = mergeFlag;
	}

	public Date getMergeDate() {
		return mergeDate;
	}

	public void setMergeDate(Date mergeDate) {
		this.mergeDate = mergeDate;
	}
	
	
}
