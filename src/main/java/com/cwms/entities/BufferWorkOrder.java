package com.cwms.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "cfbufferworkorder")
@IdClass(BufferWorkOrderId.class)
public class BufferWorkOrder {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Wo_No", length = 10)
	private String woNo;

	@Id
	@Column(name = "Profit_Centre_Id", length = 6)
	private String profitcentreId;

	@Id
	@Column(name = "Sr_No")
	private int srNo;

	@Id
	@Column(name = "Container_No", length = 11)
	private String containerNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Wo_Date")
	private Date woDate;

	@Column(name = "Shipping_Agent", length = 6)
	private String shippingAgent;

	@Column(name = "Container_Size", length = 6)
	private String containerSize;

	@Column(name = "Container_Type", length = 6)
	private String containerType;

	@Column(name = "Container_Status", length = 20)
	private String containerStatus;

	@Column(name = "On_Account_Of", length = 6)
	private String onAccountOf;

	@Column(name = "CHA", length = 6)
	private String cha;

	@Column(name = "Shipping_Line", length = 6)
	private String shippingLine;

	@Column(name = "Shipper", length = 30)
	private String shipper;

	@Column(name = "Commodity", length = 30)
	private String commodity;

	@Column(name = "Booking-No", length = 20)
	private String bookingNo;

	@Column(name = "Movement_Type", length = 10)
	private String movementType;

	@Column(name = "Move_From", length = 30)
	private String moveFrom;

	@Column(name = "Iso", length = 10)
	private String iso;

	@Column(name = "Weight", length = 20)
	private String weight;

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

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;

	@Column(name = "Process_Id", length = 10)
	private String processId;

	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId;

	@Transient
	private String chaName;

	@Transient
	private String accountHolderName;

	@Transient
	private String shippingLineName;

	public BufferWorkOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BufferWorkOrder(String companyId, String branchId, String woNo, String profitcentreId, int srNo,
			String containerNo, Date woDate, String shippingAgent, String containerSize, String containerType,
			String containerStatus, String onAccountOf, String cha, String shippingLine, String shipper,
			String commodity, String bookingNo, String movementType, String moveFrom, String iso, String weight,
			String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String processId, String gateInId, String chaName, String accountHolderName,
			String shippingLineName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.woNo = woNo;
		this.profitcentreId = profitcentreId;
		this.srNo = srNo;
		this.containerNo = containerNo;
		this.woDate = woDate;
		this.shippingAgent = shippingAgent;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerStatus = containerStatus;
		this.onAccountOf = onAccountOf;
		this.cha = cha;
		this.shippingLine = shippingLine;
		this.shipper = shipper;
		this.commodity = commodity;
		this.bookingNo = bookingNo;
		this.movementType = movementType;
		this.moveFrom = moveFrom;
		this.iso = iso;
		this.weight = weight;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.processId = processId;
		this.gateInId = gateInId;
		this.chaName = chaName;
		this.accountHolderName = accountHolderName;
		this.shippingLineName = shippingLineName;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getChaName() {
		return chaName;
	}

	public void setChaName(String chaName) {
		this.chaName = chaName;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public String getShippingLineName() {
		return shippingLineName;
	}

	public void setShippingLineName(String shippingLineName) {
		this.shippingLineName = shippingLineName;
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

	public String getWoNo() {
		return woNo;
	}

	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public Date getWoDate() {
		return woDate;
	}

	public void setWoDate(Date woDate) {
		this.woDate = woDate;
	}

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
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

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
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

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public String getMoveFrom() {
		return moveFrom;
	}

	public void setMoveFrom(String moveFrom) {
		this.moveFrom = moveFrom;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
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

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

}
