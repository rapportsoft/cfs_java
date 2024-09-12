package com.cwms.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "port")
@IdClass(PortId.class)
public class Port {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId;
	
	@Id
	@Column(name = "Port_Trans_Id", length = 20, nullable = false)
	private String portTransId;

	@Id
	@Column(name = "Port_Code", length = 6, nullable = false)
	private String portCode;

	@Column(name = "ISO_Port_Code", length = 6, nullable = false)
	private String isoPortCode;

	@Column(name = "Port_Name", length = 50)
	private String portName;

	@Column(name = "Port_Type", length = 20, nullable = false)
	private String portType;

	@Column(name = "Job_Order_Prefix", length = 5, nullable = false)
	private String jobOrderPrefix;

	@Column(name = "Job_Order_Next_No", length = 5, nullable = false)
	private String jobOrderNextNo;

	@Column(name = "job_order_format", length = 10)
	private String jobOrderFormat;

	@Column(name = "Agent_Code", length = 10, nullable = false)
	private String agentCode;

	@Column(name = "Address_1", length = 100, nullable = false)
	private String address1;

	@Column(name = "Address_2", length = 30, nullable = false)
	private String address2;

	@Column(name = "Address_3", length = 100, nullable = false)
	private String address3;

	@Column(name = "City", length = 50, nullable = false)
	private String city;

	@Column(name = "PIN", length = 10, nullable = false)
	private String pin;

	@Column(name = "State", length = 50, nullable = false)
	private String state;

	@Column(name = "Phone_No", length = 15, nullable = false)
	private String phoneNo;

	@Column(name = "Fax_No", length = 15, nullable = false)
	private String faxNo;

	@Column(name = "Contact_Person", length = 25, nullable = false)
	private String contactPerson;

	@Column(name = "Contact_Designation", length = 25, nullable = false)
	private String contactDesignation;

	@Column(name = "Contact_Phone", length = 15, nullable = false)
	private String contactPhone;

	@Column(name = "Contact_Fax_No", length = 15, nullable = false)
	private String contactFaxNo;

	@Column(name = "Contact_Email", length = 25, nullable = false)
	private String contactEmail;

	@Column(name = "Country", length = 50, nullable = false)
	private String country;

	@Column(name = "Status", length = 1, nullable = false)
	private String status;

	@Column(name = "Created_By", length = 30, nullable = false)
	private String createdBy = "";

	@Column(name = "Created_Date", nullable = false)
	private Date createdDate;

	@Column(name = "Edited_By", length = 30)
	private String editedBy;

	@Column(name = "Edited_Date")
	private Date editedDate;

	@Column(name = "Approved_By", length = 30)
	private String approvedBy;

	@Column(name = "Approved_Date")
	private Date approvedDate;

	public Port() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Port(String companyId, String branchId, String portTransId, String portCode, String isoPortCode,
			String portName, String portType, String jobOrderPrefix, String jobOrderNextNo, String jobOrderFormat,
			String agentCode, String address1, String address2, String address3, String city, String pin, String state,
			String phoneNo, String faxNo, String contactPerson, String contactDesignation, String contactPhone,
			String contactFaxNo, String contactEmail, String country, String status, String createdBy, Date createdDate,
			String editedBy, Date editedDate, String approvedBy, Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.portTransId = portTransId;
		this.portCode = portCode;
		this.isoPortCode = isoPortCode;
		this.portName = portName;
		this.portType = portType;
		this.jobOrderPrefix = jobOrderPrefix;
		this.jobOrderNextNo = jobOrderNextNo;
		this.jobOrderFormat = jobOrderFormat;
		this.agentCode = agentCode;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.city = city;
		this.pin = pin;
		this.state = state;
		this.phoneNo = phoneNo;
		this.faxNo = faxNo;
		this.contactPerson = contactPerson;
		this.contactDesignation = contactDesignation;
		this.contactPhone = contactPhone;
		this.contactFaxNo = contactFaxNo;
		this.contactEmail = contactEmail;
		this.country = country;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
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

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public String getIsoPortCode() {
		return isoPortCode;
	}

	public void setIsoPortCode(String isoPortCode) {
		this.isoPortCode = isoPortCode;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getPortType() {
		return portType;
	}

	public void setPortType(String portType) {
		this.portType = portType;
	}

	public String getJobOrderPrefix() {
		return jobOrderPrefix;
	}

	public void setJobOrderPrefix(String jobOrderPrefix) {
		this.jobOrderPrefix = jobOrderPrefix;
	}

	public String getJobOrderNextNo() {
		return jobOrderNextNo;
	}

	public void setJobOrderNextNo(String jobOrderNextNo) {
		this.jobOrderNextNo = jobOrderNextNo;
	}

	public String getJobOrderFormat() {
		return jobOrderFormat;
	}

	
	
	public String getPortTransId() {
		return portTransId;
	}



	public void setPortTransId(String portTransId) {
		this.portTransId = portTransId;
	}



	public void setJobOrderFormat(String jobOrderFormat) {
		this.jobOrderFormat = jobOrderFormat;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactDesignation() {
		return contactDesignation;
	}

	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactFaxNo() {
		return contactFaxNo;
	}

	public void setContactFaxNo(String contactFaxNo) {
		this.contactFaxNo = contactFaxNo;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	@Override
	public String toString() {
		return "Port [companyId=" + companyId + ", branchId=" + branchId + ", portCode=" + portCode + ", isoPortCode="
				+ isoPortCode + ", portName=" + portName + ", portType=" + portType + ", jobOrderPrefix="
				+ jobOrderPrefix + ", jobOrderNextNo=" + jobOrderNextNo + ", jobOrderFormat=" + jobOrderFormat
				+ ", agentCode=" + agentCode + ", address1=" + address1 + ", address2=" + address2 + ", address3="
				+ address3 + ", city=" + city + ", pin=" + pin + ", state=" + state + ", phoneNo=" + phoneNo
				+ ", faxNo=" + faxNo + ", contactPerson=" + contactPerson + ", contactDesignation=" + contactDesignation
				+ ", contactPhone=" + contactPhone + ", contactFaxNo=" + contactFaxNo + ", contactEmail=" + contactEmail
				+ ", country=" + country + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + "]";
	}


//  Port Search
	public Port(String companyId, String branchId, String portTransId, String portCode, String isoPortCode,
			String portName, String portType, String agentCode) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.portTransId = portTransId;
		this.portCode = portCode;
		this.isoPortCode = isoPortCode;
		this.portName = portName;
		this.portType = portType;
		this.agentCode = agentCode;
	}
	
//	Select Tags
	public Port(String portCode, String portName) {
		super();		
		this.portCode = portCode;		
		this.portName = portName;
		
	}
	
}
