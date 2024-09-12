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
@Table(name="vessel")
@IdClass(VesselId.class)
public class Vessel {

	@Id
	@Column(name="company_id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="branch_id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="vessel_id",length = 8)
	private String vesselId;
	
	@Column(name="vessel_name",length = 25)
	private String vesselName;
	
	@Column(name="operator",length = 6)
	private String operator;
	
	@Column(name="port_of_registration",length = 50)
	private String portOfRegistration;
	
	@Column(name="vessel_flag",length = 50)
	private String vesselFlag;
	
	@Column(name="status",length=1)
	private String status;
	
	@Column(name="master_vessel",length=25)
	private String masterVessel;
	
	@Column(name="approved_by",length=30)
	private String approvedBy;
	
	@Column(name="approved_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;
	
	@Column(name="edited_by",length=30)
	private String editedBy;
	
	@Column(name="edited_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate;

	public Vessel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vessel(String companyId, String branchId, String vesselId, String vesselName, String operator,
			String portOfRegistration, String vesselFlag, String status, String masterVessel, String approvedBy,
			Date approvedDate, String editedBy, Date editedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.vesselId = vesselId;
		this.vesselName = vesselName;
		this.operator = operator;
		this.portOfRegistration = portOfRegistration;
		this.vesselFlag = vesselFlag;
		this.status = status;
		this.masterVessel = masterVessel;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
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

	public String getVesselId() {
		return vesselId;
	}

	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPortOfRegistration() {
		return portOfRegistration;
	}

	public void setPortOfRegistration(String portOfRegistration) {
		this.portOfRegistration = portOfRegistration;
	}

	public String getVesselFlag() {
		return vesselFlag;
	}

	public void setVesselFlag(String vesselFlag) {
		this.vesselFlag = vesselFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMasterVessel() {
		return masterVessel;
	}

	public void setMasterVessel(String masterVessel) {
		this.masterVessel = masterVessel;
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

	public Vessel(String vesselId, String vesselName, String portOfRegistration, String vesselFlag, String status) {
		super();
		this.vesselId = vesselId;
		this.vesselName = vesselName;
		this.portOfRegistration = portOfRegistration;
		this.vesselFlag = vesselFlag;
		this.status = status;
	}

	public Vessel(String vesselId, String vesselName) {
		super();
		this.vesselId = vesselId;
		this.vesselName = vesselName;
	}
	
	
	
}
