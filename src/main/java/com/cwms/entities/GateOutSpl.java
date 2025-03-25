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
@Table(name = "cfgateoutspl")
@IdClass(GateOutSplId.class)
public class GateOutSpl {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId;

	@Id
	@Column(name = "Fin_Year", length = 4, nullable = false)
	private String finYear;

	@Id
	@Column(name = "Gate_Out_Id", length = 10, nullable = false)
	private String gateOutId;

	@Id
	@Column(name = "ERP_Doc_Ref_No", length = 10, nullable = false)
	private String erpDocRefNo;

	@Id
	@Column(name = "Doc_Ref_No", length = 10, nullable = false)
	private String docRefNo;

	@Id
	@Column(name = "Sr_No", length = 10, nullable = false)
	private int srNo;

	@Column(name = "Profitcentre_Id", length = 6, nullable = false)
	private String profitcentreId;

	@Column(name = "Trans_Type", length = 5)
	private String transType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Gate_Out_Date", nullable = false)
	private Date gateOutDate;

	@Column(name = "Process_Id", length = 10, nullable = false)
	private String processId;

	@Column(name = "Shift", length = 6, nullable = false)
	private String shift;

	@Column(name = "Gate_No", length = 6, nullable = false)
	private String gateNo;

	@Column(name = "IGM_Line_No", length = 10, nullable = false)
	private String igmLineNo;

	@Column(name = "Container_No", length = 11, nullable = false)
	private String containerNo;

	@Column(name = "Container_Size", length = 6, nullable = false)
	private String containerSize;

	@Column(name = "Container_Type", length = 6, nullable = false)
	private String containerType;

	@Column(name = "Container_Status", length = 3, nullable = false)
	private String containerStatus;

	@Column(name = "SL", length = 6, nullable = false)
	private String sl;

	@Column(name = "CHA", length = 6, nullable = false)
	private String cha;

	@Column(name = "destination", length = 50, nullable = false)
	private String destination;

	@Column(name = "Transporter_Status", length = 1, nullable = false)
	private String transporterStatus;

	@Column(name = "Transporter", length = 6, nullable = false)
	private String transporter;

	@Column(name = "Transporter_Name", length = 50, nullable = false)
	private String transporterName;

	@Column(name = "Vehicle_No", length = 15, nullable = false)
	private String vehicleNo;

	@Column(name = "Driver_Name", length = 50, nullable = false)
	private String driverName;

	@Column(name = "Gate_Pass_No", length = 10, nullable = false)
	private String gatePassNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Gate_Pass_Date", nullable = false)
	private Date gatePassDate;

	@Column(name = "Comments", length = 150, nullable = false)
	private String comments;

	@Column(name = "Status", length = 1, nullable = false)
	private String status;

	@Column(name = "Created_By", length = 10, nullable = false)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Created_Date", nullable = false)
	private Date createdDate;

	@Column(name = "Edited_By", length = 10, nullable = false)
	private String editedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Edited_Date", nullable = false)
	private Date editedDate;

	@Column(name = "Approved_By", length = 10, nullable = false)
	private String approvedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Approved_Date", nullable = false)
	private Date approvedDate;

	@Column(name = "Out_Booking_Type", length = 15, nullable = false)
	private String outBookingType;

	@Column(name = "Movement_By", length = 5, nullable = false)
	private String movementBy;

	@Column(name = "shipper_Name", length = 35, nullable = false)
	private String shipperName;

	@Column(name = "iso", length = 10)
	private String iso;

	@Column(name = "SA", length = 10)
	private String sa;

	@Column(name = "Importer_Name", length = 100)
	private String importerName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Gate_In_Date")
	private Date gateInDate;

	transient private String shippingLineName;

	transient private String chaName;
	transient private String selected;

	public GateOutSpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GateOutSpl(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
			String docRefNo, int srNo, String profitcentreId, String transType, Date gateOutDate, String processId,
			String shift, String gateNo, String igmLineNo, String containerNo, String containerSize,
			String containerType, String containerStatus, String sl, String cha, String destination,
			String transporterStatus, String transporter, String transporterName, String vehicleNo, String driverName,
			String gatePassNo, Date gatePassDate, String comments, String status, String createdBy, Date createdDate,
			String editedBy, Date editedDate, String approvedBy, Date approvedDate, String outBookingType,
			String movementBy, String shipperName, String iso, String sa, String importerName, Date gateInDate,
			String shippingLineName, String chaName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gateOutId = gateOutId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.srNo = srNo;
		this.profitcentreId = profitcentreId;
		this.transType = transType;
		this.gateOutDate = gateOutDate;
		this.processId = processId;
		this.shift = shift;
		this.gateNo = gateNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerStatus = containerStatus;
		this.sl = sl;
		this.cha = cha;
		this.destination = destination;
		this.transporterStatus = transporterStatus;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.gatePassNo = gatePassNo;
		this.gatePassDate = gatePassDate;
		this.comments = comments;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.outBookingType = outBookingType;
		this.movementBy = movementBy;
		this.shipperName = shipperName;
		this.iso = iso;
		this.sa = sa;
		this.importerName = importerName;
		this.gateInDate = gateInDate;
		this.shippingLineName = shippingLineName;
		this.chaName = chaName;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
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

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getGateNo() {
		return gateNo;
	}

	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
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

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getTransporterStatus() {
		return transporterStatus;
	}

	public void setTransporterStatus(String transporterStatus) {
		this.transporterStatus = transporterStatus;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getOutBookingType() {
		return outBookingType;
	}

	public void setOutBookingType(String outBookingType) {
		this.outBookingType = outBookingType;
	}

	public String getMovementBy() {
		return movementBy;
	}

	public void setMovementBy(String movementBy) {
		this.movementBy = movementBy;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getSa() {
		return sa;
	}

	public void setSa(String sa) {
		this.sa = sa;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public String getShippingLineName() {
		return shippingLineName;
	}

	public void setShippingLineName(String shippingLineName) {
		this.shippingLineName = shippingLineName;
	}

	public String getChaName() {
		return chaName;
	}

	public void setChaName(String chaName) {
		this.chaName = chaName;
	}

	@Override
	public String toString() {
		return "GateOutSpl [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear + ", gateOutId="
				+ gateOutId + ", erpDocRefNo=" + erpDocRefNo + ", docRefNo=" + docRefNo + ", srNo=" + srNo
				+ ", profitcentreId=" + profitcentreId + ", transType=" + transType + ", gateOutDate=" + gateOutDate
				+ ", processId=" + processId + ", shift=" + shift + ", gateNo=" + gateNo + ", igmLineNo=" + igmLineNo
				+ ", containerNo=" + containerNo + ", containerSize=" + containerSize + ", containerType="
				+ containerType + ", containerStatus=" + containerStatus + ", sl=" + sl + ", cha=" + cha
				+ ", destination=" + destination + ", transporterStatus=" + transporterStatus + ", transporter="
				+ transporter + ", transporterName=" + transporterName + ", vehicleNo=" + vehicleNo + ", driverName="
				+ driverName + ", gatePassNo=" + gatePassNo + ", gatePassDate=" + gatePassDate + ", comments="
				+ comments + ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", outBookingType=" + outBookingType + ", movementBy=" + movementBy
				+ ", shipperName=" + shipperName + ", iso=" + iso + ", sa=" + sa + ", importerName=" + importerName
				+ ", gateInDate=" + gateInDate + "]";
	}

	public GateOutSpl(String gatePassId, Date gatePassDate, String profitcentreId, String sl, String partyNameSL,
			String transType, String cha, String partyNameCHA, String vehicleNo, String driverName,
			String transporterStatus, String transporter, String transporterName, String containerNo, int srNo,
			String igmNo, String igmTransId, String igmLineNo, String containerSize, String containerType,
			String importerName, String shippingAgent, String containerStatus, String iso, Date gateInDate) {
		this.gatePassNo = gatePassId;
		this.gatePassDate = gatePassDate;
		this.profitcentreId = profitcentreId;
		this.sl = sl;
		this.shippingLineName = partyNameSL;
		this.transType = transType;
		this.cha = cha;
		this.chaName = partyNameCHA;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.transporterStatus = transporterStatus;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.containerNo = containerNo;
		this.srNo = srNo;
		this.docRefNo = igmNo;
		this.erpDocRefNo = igmTransId;
		this.igmLineNo = igmLineNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.importerName = importerName;
		this.sa = shippingAgent;
		this.containerStatus = containerStatus;
		this.iso = iso;
		this.gateInDate = gateInDate;
		this.selected = "N";
		this.profitcentreId = "N00002";
		this.shift = "DAY";
		this.processId = "P00219";
		this.gateNo = "Gate01";
		this.gateOutDate = new Date();
	}

//		 After Save Select

	public GateOutSpl(String companyId, String branchId, String profitcentreId, String processId, String gatePassId, Date gatePassDate, String gateOutId,
			Date gateOutDate, String sl, String partyNameSL, String transType,
			String cha, String partyNameCHA, String vehicleNo, String driverName, String transporter,
			String transporterName, String containerNo, int srNo, String igmNo, String igmLineNo, String containerSize,
			String containerType, String shift, String gateNo, String comments, String destination, String shipperName, String movementBy, String outBookingType,
			String status, String createdBy) {
		
		this.status = status;
		this.createdBy = createdBy;
		this.destination = destination;
		this.shipperName= shipperName;
		this.movementBy = movementBy;
		this.outBookingType = outBookingType;

		this.gateNo = gateNo;
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassNo = gatePassId;
		this.profitcentreId = profitcentreId;
		this.processId = processId;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.gatePassNo = gatePassId;
		this.gatePassDate = gatePassDate;
		this.profitcentreId = profitcentreId;
		this.sl = sl;
		this.shippingLineName = partyNameSL;
		this.transType = transType;
		this.cha = cha;
		this.chaName = partyNameCHA;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.containerNo = containerNo;
		this.srNo = srNo;
		this.docRefNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.selected = "Y";
		this.shift = shift;
		this.comments = comments;
	}

}
