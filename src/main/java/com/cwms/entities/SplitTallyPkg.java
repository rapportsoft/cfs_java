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
@Table(name = "cfsplittallypkg")
@IdClass(SplitTallyPkgId.class)
public class SplitTallyPkg {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Stuff_Tally_Id", length = 10)
	private String stuffTallyId;

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
	@Column(name = "Sr_No")
	private int srNo;

	@Column(name = "From_Pkg")
	private int fromPkg;

	@Column(name = "To_Pkg")
	private int toPkg;

	@Column(name = "Qty")
	private int qty;

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

	public SplitTallyPkg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SplitTallyPkg(String companyId, String branchId, String stuffTallyId, String sbTransId, String sbNo,
			String containerNo, int srNo, int fromPkg, int toPkg, int qty, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.stuffTallyId = stuffTallyId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.srNo = srNo;
		this.fromPkg = fromPkg;
		this.toPkg = toPkg;
		this.qty = qty;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
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

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getFromPkg() {
		return fromPkg;
	}

	public void setFromPkg(int fromPkg) {
		this.fromPkg = fromPkg;
	}

	public int getToPkg() {
		return toPkg;
	}

	public void setToPkg(int toPkg) {
		this.toPkg = toPkg;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
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
	
	
	
}
