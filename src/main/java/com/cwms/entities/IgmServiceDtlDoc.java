package com.cwms.entities;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="cfigmservicedtl_doc")
@IdClass(IgmServiceDtlDocId.class)
public class IgmServiceDtlDoc {

	@Id
	@Column(name="Company_Id",length = 6)
	public String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	public String branchId;
	

	@Id
	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;

	@Id
	@Column(name = "IGM_No", length = 10)
	private String igmNo;

	@Id
	@Column(name = "IGM_Line_No", length = 10)
	private String igmLineNo;
	
	@Id
	@Column(name="Sr_No")
	private int srNo;
	
	@Column(name = "Doc_Path", length = 300)
	private String docPath;
	
	@Column(name = "Created_By", length = 10)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_Date")
	private Date createdDate;
	
	@Column(name="Status",length = 1)
	private String status;
	
	@Transient
	@JsonIgnore
	private MultipartFile file;

	protected IgmServiceDtlDoc() {
		super();
		// TODO Auto-generated constructor stub
	}



	protected IgmServiceDtlDoc(String companyId, String branchId, String igmTransId, String igmNo, String igmLineNo,
			int srNo, String docPath, String createdBy, Date createdDate, String status, MultipartFile file) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.srNo = srNo;
		this.docPath = docPath;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.status = status;
		this.file = file;
	}


	

	public MultipartFile getFile() {
		return file;
	}



	public void setFile(MultipartFile file) {
		this.file = file;
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

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
