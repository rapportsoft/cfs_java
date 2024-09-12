package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "isocontainer")
@Entity
@IdClass(IsoContainerId.class)
public class IsoContainer {
	@Id
	@Column(name = "Company_Id", length = 10, nullable = false)
	private String companyId;

	@Id
	@Column(name = "ISO_Code", length = 4, nullable = false)
	private String isoCode;

	@Column(name = "Container_Type", length = 6, nullable = false)
	private String containerType;

	@Column(name = "Container_Size", length = 6, nullable = false)
	private String containerSize;

	@Column(name = "Gross_Weight", precision = 18, scale = 3)
	private BigDecimal grossWeight;

	@Column(name = "Tare_Weight", precision = 18, scale = 3)
	private BigDecimal tareWeight;

	@Column(name = "Reefer_Type", length = 1, nullable = false)
	private char reeferType;

	@Column(name = "Open_Top_Type", length = 1, nullable = false)
	private char openTopType;

	@Column(name = "Created_By", length = 10, nullable = false)
	private String createdBy;

	@Column(name = "Created_Date", nullable = false)
	private Date createdDate;

	@Column(name = "Edited_By", length = 10)
	private String editedBy;

	@Column(name = "Edited_Date")
	private Date editedDate;

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Column(name = "Approved_Date")
	private Date approvedDate;

	@Column(name = "Status", length = 1, nullable = false)
	private String status;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
	}

	public char getReeferType() {
		return reeferType;
	}

	public void setReeferType(char reeferType) {
		this.reeferType = reeferType;
	}

	public char getOpenTopType() {
		return openTopType;
	}

	public void setOpenTopType(char openTopType) {
		this.openTopType = openTopType;
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

	public IsoContainer() {
		super();
	}

	public IsoContainer(String companyId, String isoCode, String containerType, String containerSize,
			BigDecimal grossWeight, BigDecimal tareWeight, char reeferType, char openTopType, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status) {
		super();
		this.companyId = companyId;
		this.isoCode = isoCode;
		this.containerType = containerType;
		this.containerSize = containerSize;
		this.grossWeight = grossWeight;
		this.tareWeight = tareWeight;
		this.reeferType = reeferType;
		this.openTopType = openTopType;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}

	@Override
	public String toString() {
		return "IsoContainer [companyId=" + companyId + ", isoCode=" + isoCode + ", containerType=" + containerType
				+ ", containerSize=" + containerSize + ", grossWeight=" + grossWeight + ", tareWeight=" + tareWeight
				+ ", reeferType=" + reeferType + ", openTopType=" + openTopType + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", status=" + status + "]";
	}

	public void setCreatedate() {
		createdDate = new Date();
		approvedDate = new Date();
		editedDate = new Date();

	}

	public IsoContainer(String isoCode, String containerType, String containerSize, BigDecimal tareWeight) {
		super();
		this.isoCode = isoCode;
		this.containerType = containerType;
		this.containerSize = containerSize;
		this.tareWeight = tareWeight;
	}
	
	public IsoContainer(String companyId, String isoCode, String containerType, String containerSize,
			BigDecimal grossWeight, BigDecimal tareWeight) {
		super();
		this.companyId = companyId;
		this.isoCode = isoCode;
		this.containerType = containerType;
		this.containerSize = containerSize;
		this.grossWeight = grossWeight;
		this.tareWeight = tareWeight;
	}
	
	
	
}
