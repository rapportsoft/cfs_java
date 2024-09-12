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
@Table(name="YardLocation")
@IdClass(YardlocationId.class)
public class YardLocation  {
	@Id
	@Column(name = "company_id", length = 6)
	public String companyId;

	@Id
	@Column(name = "fin_year", length = 4)
	public String finYear;

	@Id
	@Column(name = "yard_id", length = 6)
	public String yardId;
	
	
	@Id
	@Column(name = "yard_location_id", length = 10)
	public String yardLocationId;
	
	@Column(name = "Yard_Location_Desc", length = 35, nullable = false)
	private String yardLocationDesc;
	
	@Column(name = "Location_Category", length = 1, nullable = false)
	private String locationCategory;
	
	@Column(name = "Created_By", length = 30, nullable = false)
	private String createdBy = "";

	@Column(name = "Created_Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate;


	@Column(name = "Approved_By", length = 30)
	private String approvedBy;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;
	
	@Column(name="status",length = 1)
	public String status;

	public YardLocation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public YardLocation(String companyId, String finYear, String yardId, String yardLocationId, String yardLocationDesc,
			String locationCategory, String createdBy, Date createdDate, String approvedBy, Date approvedDate,
			String status) {
		super();
		this.companyId = companyId;
		this.finYear = finYear;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
		this.yardLocationDesc = yardLocationDesc;
		this.locationCategory = locationCategory;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getYardId() {
		return yardId;
	}

	public void setYardId(String yardId) {
		this.yardId = yardId;
	}

	public String getYardLocationId() {
		return yardLocationId;
	}

	public void setYardLocationId(String yardLocationId) {
		this.yardLocationId = yardLocationId;
	}

	public String getYardLocationDesc() {
		return yardLocationDesc;
	}

	public void setYardLocationDesc(String yardLocationDesc) {
		this.yardLocationDesc = yardLocationDesc;
	}

	public String getLocationCategory() {
		return locationCategory;
	}

	public void setLocationCategory(String locationCategory) {
		this.locationCategory = locationCategory;
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

	public YardLocation(String finYear, String yardId, String yardLocationId, String yardLocationDesc,
			String locationCategory, String status) {
		super();
		this.finYear = finYear;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
		this.yardLocationDesc = yardLocationDesc;
		this.locationCategory = locationCategory;
		this.status = status;
	}
	
	
	
	
}
