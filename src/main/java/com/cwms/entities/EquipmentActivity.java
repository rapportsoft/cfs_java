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
@Table(name="cfequipmentactivity")
@IdClass(EquipmentActivityId.class)
public class EquipmentActivity {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Fin_year",length = 4)
	private String finYear;
	
	@Id
	@Column(name="Profitcentre_Id",length = 20)
	private String profitCenterId;
	
	@Id
	@Column(name="Process_Id",length = 20)
	private String processId;
	
	@Id
	@Column(name="Erp_Doc_ref_no",length = 20)
	private String erpDocRefNo;
	
	@Id
	@Column(name="Doc_ref_no",length = 25)
	private String docRefNo;
	
	@Id
	@Column(name="Container_No",length = 20)
	private String containerNo;
	
	@Id
	@Column(name="Sr_No")
	private int srNo;
	
	@Id
	@Column(name="DeStuff_Id",length = 15)
	private String deStuffId;
	
	@Id
	@Column(name="Sub_Doc_ref_no",length = 20)
	private String subDocRefNo;
	
	@Column(name="Container_size",length = 3)
	private String containerSize;
	
	@Column(name="Container_Type",length = 3)
	private String containerType;
	
	@Column(name="Equipment",length = 20)
	private String equipment;
	
	@Column(name="Equipment_Nm",length = 255)
	private String equipmentNm;
	
	@Column(name="Vendor_Id",length = 20)
	private String vendorId;
	
	@Column(name="Vendor_Nm",length = 100)
	private String vendorNm;
	
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
	
	@Column(name="status",length = 1)
	private String status;

	public EquipmentActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public EquipmentActivity(String companyId, String branchId, String finYear, String profitCenterId, String processId,
			String erpDocRefNo, String docRefNo, String containerNo, int srNo, String deStuffId, String subDocRefNo,
			String containerSize, String containerType, String equipment, String equipmentNm, String vendorId,
			String vendorNm, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitCenterId = profitCenterId;
		this.processId = processId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
		this.srNo = srNo;
		this.deStuffId = deStuffId;
		this.subDocRefNo = subDocRefNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.equipment = equipment;
		this.equipmentNm = equipmentNm;
		this.vendorId = vendorId;
		this.vendorNm = vendorNm;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}

	


	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
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

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getProfitCenterId() {
		return profitCenterId;
	}

	public void setProfitCenterId(String profitCenterId) {
		this.profitCenterId = profitCenterId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getErpDocRefNo() {
		return erpDocRefNo;
	}

	public void setErpDocRefNo(String erpDocRefNo) {
		this.erpDocRefNo = erpDocRefNo;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDeStuffId() {
		return deStuffId;
	}

	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}

	public String getSubDocRefNo() {
		return subDocRefNo;
	}

	public void setSubDocRefNo(String subDocRefNo) {
		this.subDocRefNo = subDocRefNo;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getEquipmentNm() {
		return equipmentNm;
	}

	public void setEquipmentNm(String equipmentNm) {
		this.equipmentNm = equipmentNm;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorNm() {
		return vendorNm;
	}

	public void setVendorNm(String vendorNm) {
		this.vendorNm = vendorNm;
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
	
	
	
}
