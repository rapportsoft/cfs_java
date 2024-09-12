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
@Table(name = "partyaddress")
@IdClass(PartyAddressId.class)
public class PartyAddress {
	@Id
	@Column(name = "Company_Id", nullable = false, length = 6)
	private String companyId = "";
	
	@Id
	@Column(name = "Branch_Id", nullable = false, length = 6)
	private String branchId = "";

	@Id
	@Column(name = "Party_Id", nullable = false, length = 6)
	private String partyId = "";

	@Id
	@Column(name = "Sr_No", length = 10,nullable = false)
	private String srNo = "1";

	@Column(name = "Party_Type", nullable = false, length = 30)
	private String partyType = "";

	@Column(name = "address_1", nullable = false, length = 100)
	private String address1 = "";

	@Column(name = "address_2", nullable = false, length = 100)
	private String address2 = "";

	@Column(name = "address_3", nullable = false, length = 100)
	private String address3 = "";

	@Column(name = "City", length = 50)
	private String city;

	@Column(name = "PIN", length = 10)
	private String pin;

	@Column(name = "State", length = 15)
	private String state;

	@Column(name = "GST_No", nullable = false, length = 30)
	private String gstNo = "UNREGISTER";

    @Column(name = "Created_Date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date approvedDate;

	@Column(name = "Status", nullable = false, length = 1)
	private String status = "";

	@Column(name = "Default_chk", nullable = false, length = 1)
	private String defaultChk = "N";

	public PartyAddress() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public PartyAddress(String companyId, String branchId, String partyId, String srNo, String partyType,
			String address1, String address2, String address3, String city, String pin, String state, String gstNo,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status,
			String defaultChk) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.srNo = srNo;
		this.partyType = partyType;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.city = city;
		this.pin = pin;
		this.state = state;
		this.gstNo = gstNo;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.defaultChk = defaultChk;
	}




	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	

	public String getSrNo() {
		return srNo;
	}




	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}




	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
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

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
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

	public String getDefaultChk() {
		return defaultChk;
	}

	public void setDefaultChk(String defaultChk) {
		this.defaultChk = defaultChk;
	}
	
	public PartyAddress(String companyId, String branchId, String partyId, String srNo, String address1,
			String address2, String address3, String city, String gstNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.srNo = srNo;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.city = city;
		this.gstNo = gstNo;
	}
	
	

}
