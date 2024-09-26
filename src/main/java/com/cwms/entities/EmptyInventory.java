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
@Table(name = "cfempinventory")
@IdClass(EmptyInventoryId.class)
public class EmptyInventory {

	@Id
	@Column(name = "Company_Id", length = 6)
	public String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	public String branchId;

	@Id
	@Column(name = "GateIn_Id", length = 10)
	public String gateInId;

	@Id
	@Column(name = "fin_year", length = 6)
	public String finYear;

	@Id
	@Column(name = "Erp_Doc_ref_no", length = 10)
	public String erpDocRefNo;

	@Id
	@Column(name = "Doc_Ref_No", length = 25)
	public String docRefNo;

	@Id
	@Column(name = "Sub_Doc_Ref_No", length = 21)
	public String subDocRefNo;

	@Id
	@Column(name = "Movement_Code", length = 4)
	public String movementCode;

	@Id
	@Column(name = "Profitcentre_Id", length = 6)
	public String profitcentreId;

	@Column(name = "DeStuff_Id", length = 10)
	public String deStuffId;

	@Column(name = "Empty_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date emptyDate;

	@Column(name = "Gate_In_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date gateInDate;

	@Column(name = "ISO_Code", length = 4, columnDefinition = "varchar(4) default ''")
	public String isoCode = "";

	@Column(name = "Container_No", length = 11, columnDefinition = "varchar(11) default ''")
	public String containerNo = "";

	@Column(name = "Container_Size", length = 6, columnDefinition = "varchar(6) default ''")
	public String containerSize = "";

	@Column(name = "Container_Type", length = 6, columnDefinition = "varchar(6) default ''")
	public String containerType = "";

	@Column(name = "SA", length = 6, columnDefinition = "varchar(6) default ''")
	public String sa = "";

	@Column(name = "SL", length = 6, columnDefinition = "varchar(6) default ''")
	public String sl = "";

	@Column(name = "On_Account_Of", length = 6, columnDefinition = "varchar(6) default ''")
	public String onAccountOf = "";

	@Column(name = "Cha", length = 6, columnDefinition = "varchar(6) default ''")
	public String cha = "";

	@Column(name = "Stuff_Id", length = 10, columnDefinition = "varchar(10) default ''")
	public String stuffId = "";

	@Column(name = "Stuff_Tally_Id", length = 10, columnDefinition = "varchar(10) default ''")
	public String stuffTallyId = "";

	@Column(name = "Empty_Job_Order", length = 10, columnDefinition = "varchar(10) default ''")
	public String emptyJobOrder = "";

	@Column(name = "Gate_Pass_Id", length = 10)
	public String gatePassId = ""; // Default value

	@Column(name = "Gate_Out_Id", length = 10)
	public String gateOutId = ""; // Default value

	@Column(name = "Gate_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date gateOutDate; // Default value

	@Column(name = "Status", length = 1)
	public String status;

	@Column(name = "Created_By", length = 10)
	private String createdBy;

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public EmptyInventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public EmptyInventory(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
			String docRefNo, String subDocRefNo, String movementCode, String profitcentreId, String deStuffId,
			Date emptyDate, Date gateInDate, String isoCode, String containerNo, String containerSize,
			String containerType, String sa, String sl, String onAccountOf, String cha, String stuffId,
			String stuffTallyId, String emptyJobOrder, String gatePassId, String gateOutId, Date gateOutDate,
			String status, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.subDocRefNo = subDocRefNo;
		this.movementCode = movementCode;
		this.profitcentreId = profitcentreId;
		this.deStuffId = deStuffId;
		this.emptyDate = emptyDate;
		this.gateInDate = gateInDate;
		this.isoCode = isoCode;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.sa = sa;
		this.sl = sl;
		this.onAccountOf = onAccountOf;
		this.cha = cha;
		this.stuffId = stuffId;
		this.stuffTallyId = stuffTallyId;
		this.emptyJobOrder = emptyJobOrder;
		this.gatePassId = gatePassId;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
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



	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
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

	public String getSubDocRefNo() {
		return subDocRefNo;
	}

	public void setSubDocRefNo(String subDocRefNo) {
		this.subDocRefNo = subDocRefNo;
	}

	public String getMovementCode() {
		return movementCode;
	}

	public void setMovementCode(String movementCode) {
		this.movementCode = movementCode;
	}

	public String getDeStuffId() {
		return deStuffId;
	}

	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}

	public Date getEmptyDate() {
		return emptyDate;
	}

	public void setEmptyDate(Date emptyDate) {
		this.emptyDate = emptyDate;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public String getSa() {
		return sa;
	}

	public void setSa(String sa) {
		this.sa = sa;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getStuffId() {
		return stuffId;
	}

	public void setStuffId(String stuffId) {
		this.stuffId = stuffId;
	}

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	public String getEmptyJobOrder() {
		return emptyJobOrder;
	}

	public void setEmptyJobOrder(String emptyJobOrder) {
		this.emptyJobOrder = emptyJobOrder;
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

}
