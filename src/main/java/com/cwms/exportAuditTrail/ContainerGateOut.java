package com.cwms.exportAuditTrail;

import java.util.Date;

public class ContainerGateOut {

	private String auditremarks;
	private String gateInId;
	private String sbNo;
	private String containerNo;
	private String sbTransId;
	private String gateOutId;
	private String gatePassNo;
	private Date newGatePassDate;
	private String newTruckNo;
	private Date newGateOutDate;
	private String newTransporter;
	private String newTransporterName;
	private Date oldGatePassDate;
	private String oldTruckNo;
	private Date oldGateOutDate;
	private String oldTransporter;
	private String oldTransporterName;

	private String profitCentreId;
	private String containerSize;
	private String containerType;
	
	

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

	public ContainerGateOut() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContainerGateOut(String gateInId, String sbNo, String containerNo, String sbTransId, String gateOutId,
			String gatePassNo, Date newGatePassDate, String newTruckNo, Date newGateOutDate, String newTransporter,
			String newTransporterName, Date oldGatePassDate, String oldTruckNo, Date oldGateOutDate,
			String oldTransporter, String oldTransporterName, String profitCentreId) {
		super();
		this.gateInId = gateInId;
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.sbTransId = sbTransId;
		this.gateOutId = gateOutId;
		this.gatePassNo = gatePassNo;
		this.newGatePassDate = newGatePassDate;
		this.newTruckNo = newTruckNo;
		this.newGateOutDate = newGateOutDate;
		this.newTransporter = newTransporter;
		this.newTransporterName = newTransporterName;
		this.oldGatePassDate = oldGatePassDate;
		this.oldTruckNo = oldTruckNo;
		this.oldGateOutDate = oldGateOutDate;
		this.oldTransporter = oldTransporter;
		this.oldTransporterName = oldTransporterName;
		this.profitCentreId = profitCentreId;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
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

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public Date getNewGatePassDate() {
		return newGatePassDate;
	}

	public void setNewGatePassDate(Date newGatePassDate) {
		this.newGatePassDate = newGatePassDate;
	}

	public String getNewTruckNo() {
		return newTruckNo;
	}

	public void setNewTruckNo(String newTruckNo) {
		this.newTruckNo = newTruckNo;
	}

	public Date getNewGateOutDate() {
		return newGateOutDate;
	}

	public void setNewGateOutDate(Date newGateOutDate) {
		this.newGateOutDate = newGateOutDate;
	}

	public String getNewTransporter() {
		return newTransporter;
	}

	public void setNewTransporter(String newTransporter) {
		this.newTransporter = newTransporter;
	}

	public String getNewTransporterName() {
		return newTransporterName;
	}

	public void setNewTransporterName(String newTransporterName) {
		this.newTransporterName = newTransporterName;
	}

	public Date getOldGatePassDate() {
		return oldGatePassDate;
	}

	public void setOldGatePassDate(Date oldGatePassDate) {
		this.oldGatePassDate = oldGatePassDate;
	}

	public String getOldTruckNo() {
		return oldTruckNo;
	}

	public void setOldTruckNo(String oldTruckNo) {
		this.oldTruckNo = oldTruckNo;
	}

	public Date getOldGateOutDate() {
		return oldGateOutDate;
	}

	public void setOldGateOutDate(Date oldGateOutDate) {
		this.oldGateOutDate = oldGateOutDate;
	}

	public String getOldTransporter() {
		return oldTransporter;
	}

	public void setOldTransporter(String oldTransporter) {
		this.oldTransporter = oldTransporter;
	}

	public String getOldTransporterName() {
		return oldTransporterName;
	}

	public void setOldTransporterName(String oldTransporterName) {
		this.oldTransporterName = oldTransporterName;
	}

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}

	@Override
	public String toString() {
		return "ContainerGateOut [gateInId=" + gateInId + ", sbNo=" + sbNo + ", containerNo=" + containerNo
				+ ", sbTransId=" + sbTransId + ", gateOutId=" + gateOutId + ", gatePassNo=" + gatePassNo
				+ ", newGatePassDate=" + newGatePassDate + ", newTruckNo=" + newTruckNo + ", newGateOutDate="
				+ newGateOutDate + ", newTransporter=" + newTransporter + ", newTransporterName=" + newTransporterName
				+ ", oldGatePassDate=" + oldGatePassDate + ", oldTruckNo=" + oldTruckNo + ", oldGateOutDate="
				+ oldGateOutDate + ", oldTransporter=" + oldTransporter + ", oldTransporterName=" + oldTransporterName
				+ ", profitCentreId=" + profitCentreId + "]";
	}
	
	

	public String getAuditremarks() {
		return auditremarks;
	}

	public void setAuditremarks(String auditRemarks) {
		this.auditremarks = auditRemarks;
	}

	public ContainerGateOut(String gatePassNo, Date gatePassDate, String gateOutId, Date gateOutDate,
			String containerNo, String containerSize, String containerType, String vehicleNo,
			String transporterName, String transporter, String profitCentreId) {
		this.gatePassNo = gatePassNo;
		this.oldGatePassDate = gatePassDate;
		this.newGatePassDate = gatePassDate;
		this.gateOutId = gateOutId;
		this.oldGateOutDate = gateOutDate;
		this.newGateOutDate = gateOutDate;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.oldTruckNo = vehicleNo;
		this.newTruckNo = vehicleNo;
		
	
		this.newTransporter = (transporter == null || transporter.isEmpty()) ? transporterName : transporter;
		this.oldTransporter = (transporter == null || transporter.isEmpty()) ? transporterName : transporter;	
		this.newTransporterName = transporterName;
		this.oldTransporterName = transporterName;
		this.auditremarks = "";
		this.profitCentreId = profitCentreId;
	}

}
