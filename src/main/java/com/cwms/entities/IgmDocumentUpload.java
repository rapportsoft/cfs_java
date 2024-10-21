package com.cwms.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfigmdocupload")
@IdClass(IgmDocumentUploadId.class)
public class IgmDocumentUpload {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;
	
	
	@Id
	@Column(name = "Igm_no", length = 10)
	private String igmNo;

	@Id
	@Column(name = "IGM_Line_No", length = 7)
	private String igmLineNo;
	
	@Id
	@Column(name="Sr_No")
	private int srNo;
	
	@Column(name="Doc_Type",length = 10)
	private String docType;
	
	@Column(name="Doc_Path",length = 250)
	private String docPath;
	
	@Column(name="Status",length = 1)
	private String status;
	
	@Column(name = "Created_By", length = 10)
	private String createdBy = "";
	
	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate;

	public IgmDocumentUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IgmDocumentUpload(String companyId, String branchId, String igmTransId, String igmNo, String igmLineNo,
			int srNo, String docType, String docPath, String status, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.srNo = srNo;
		this.docType = docType;
		this.docPath = docPath;
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

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
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
