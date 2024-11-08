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
@Table(name = "cfsexpaudit")
@IdClass(ExportAuditId.class)
public class ExportAudit {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Audit_Id",length = 10)
	private String auditId;
	
	@Column(name="Audit_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auditDate;
	
	@Column(name="Profitcentre_Id",length = 6)
	private String profitcentreId;
	
	@Column(name="IGM_No",length = 10)
	private String igmNo;
	
	@Column(name="IGM_Line_No",length = 7)
	private String igmLineNo;
	
	@Column(name="Sb_No",length = 10)
	private String sbNo;
	
	@Column(name="Container_No",length = 12)
	private String containerNo;
	
	@Column(name="Bond_No",length = 10)
	private String bondNo;
	
	@Column(name="Noc_No",length = 10)
	private String nocNo;
	
	@Column(name="Audit_Remark",length = 250)
	private String auditRemark;
	
	@Column(name="Field",length = 250)
	private String field;
	
	@Column(name="Old_Value",length = 250)
	private String oldValue;
	
	@Column(name="New_Value",length = 250)
	private String newValue;
	
	@Column(name="Created_By",length = 10)
	private String createdBy;
	
	@Column(name="Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="Edited_By",length = 10)
	private String editedBy;
	
	@Column(name="Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date editedDate;
	
	@Column(name="Approved_By",length = 10)
	private String approvedBy;
	
	@Column(name="Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;
	
	@Column(name="Status",length = 1)
	private String status;
	
	@Column(name="Table_Name",length = 200)
	private String tableName;

	public ExportAudit() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ExportAudit(String companyId, String branchId, String auditId, Date auditDate, String profitcentreId,
			String igmNo, String igmLineNo, String sbNo, String containerNo, String bondNo, String nocNo,
			String auditRemark, String field, String oldValue, String newValue, String createdBy, Date createdDate,
			String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status, String tableName) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.auditId = auditId;
		this.auditDate = auditDate;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.bondNo = bondNo;
		this.nocNo = nocNo;
		this.auditRemark = auditRemark;
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.tableName = tableName;
	}


	

	public String getOldValue() {
		return oldValue;
	}



	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}



	public String getNewValue() {
		return newValue;
	}



	public void setNewValue(String newValue) {
		this.newValue = newValue;
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

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
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

	public String getBondNo() {
		return bondNo;
	}

	public void setBondNo(String bondNo) {
		this.bondNo = bondNo;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
	
	
}
