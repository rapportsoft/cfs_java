package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class BackToTownOutDTO {

	private String gateOutId;
	private String gatePassNo;
	private String sbNo;
	private String sbTransId;
	private String auditremarks;
	private String backToTownTransId;
	private BigDecimal actualNoOfPackages;
	private BigDecimal grossWeight;

	private BigDecimal oldBackToTownPackages;
	private Date oldBackToTownTransDate;
	private BigDecimal backToTownWeight;

	private String newTruckNo;
	private Date newGateOutDate;
	private String oldTruckNo;
	private Date oldGateOutDate;

	private BigDecimal newBackToTownPackages;

	private BigDecimal newQtyTakenOut;
	private BigDecimal oldQtyTakenOut;

	public BackToTownOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BackToTownOutDTO(String gateOutId, String gatePassNo, String sbNo, String sbTransId, String auditremarks,
			String backToTownTransId, BigDecimal actualNoOfPackages, BigDecimal grossWeight,
			BigDecimal oldBackToTownPackages, Date oldBackToTownTransDate, BigDecimal backToTownWeight,
			String newTruckNo, Date newGateOutDate, String oldTruckNo, Date oldGateOutDate,
			BigDecimal newBackToTownPackages, BigDecimal newQtyTakenOut, BigDecimal oldQtyTakenOut) {
		super();
		this.gateOutId = gateOutId;
		this.gatePassNo = gatePassNo;
		this.sbNo = sbNo;
		this.sbTransId = sbTransId;
		this.auditremarks = auditremarks;
		this.backToTownTransId = backToTownTransId;
		this.actualNoOfPackages = actualNoOfPackages;
		this.grossWeight = grossWeight;
		this.oldBackToTownPackages = oldBackToTownPackages;
		this.oldBackToTownTransDate = oldBackToTownTransDate;
		this.backToTownWeight = backToTownWeight;
		this.newTruckNo = newTruckNo;
		this.newGateOutDate = newGateOutDate;
		this.oldTruckNo = oldTruckNo;
		this.oldGateOutDate = oldGateOutDate;
		this.newBackToTownPackages = newBackToTownPackages;
		this.newQtyTakenOut = newQtyTakenOut;
		this.oldQtyTakenOut = oldQtyTakenOut;
	}

	
	
	
	
	@Override
	public String toString() {
		return "BackToTownOutDTO [gateOutId=" + gateOutId + ", gatePassNo=" + gatePassNo + ", sbNo=" + sbNo
				+ ", sbTransId=" + sbTransId + ", auditremarks=" + auditremarks + ", backToTownTransId="
				+ backToTownTransId + ", actualNoOfPackages=" + actualNoOfPackages + ", grossWeight=" + grossWeight
				+ ", oldBackToTownPackages=" + oldBackToTownPackages + ", oldBackToTownTransDate="
				+ oldBackToTownTransDate + ", backToTownWeight=" + backToTownWeight + ", newTruckNo=" + newTruckNo
				+ ", newGateOutDate=" + newGateOutDate + ", oldTruckNo=" + oldTruckNo + ", oldGateOutDate="
				+ oldGateOutDate + ", newBackToTownPackages=" + newBackToTownPackages + ", newQtyTakenOut="
				+ newQtyTakenOut + ", oldQtyTakenOut=" + oldQtyTakenOut + "]";
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

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getAuditremarks() {
		return auditremarks;
	}

	public void setAuditremarks(String auditremarks) {
		this.auditremarks = auditremarks;
	}

	public String getBackToTownTransId() {
		return backToTownTransId;
	}

	public void setBackToTownTransId(String backToTownTransId) {
		this.backToTownTransId = backToTownTransId;
	}

	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}

	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getOldBackToTownPackages() {
		return oldBackToTownPackages;
	}

	public void setOldBackToTownPackages(BigDecimal oldBackToTownPackages) {
		this.oldBackToTownPackages = oldBackToTownPackages;
	}

	public Date getOldBackToTownTransDate() {
		return oldBackToTownTransDate;
	}

	public void setOldBackToTownTransDate(Date oldBackToTownTransDate) {
		this.oldBackToTownTransDate = oldBackToTownTransDate;
	}

	public BigDecimal getBackToTownWeight() {
		return backToTownWeight;
	}

	public void setBackToTownWeight(BigDecimal backToTownWeight) {
		this.backToTownWeight = backToTownWeight;
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

	public BigDecimal getNewBackToTownPackages() {
		return newBackToTownPackages;
	}

	public void setNewBackToTownPackages(BigDecimal newBackToTownPackages) {
		this.newBackToTownPackages = newBackToTownPackages;
	}

	public BigDecimal getNewQtyTakenOut() {
		return newQtyTakenOut;
	}

	public void setNewQtyTakenOut(BigDecimal newQtyTakenOut) {
		this.newQtyTakenOut = newQtyTakenOut;
	}

	public BigDecimal getOldQtyTakenOut() {
		return oldQtyTakenOut;
	}

	public void setOldQtyTakenOut(BigDecimal oldQtyTakenOut) {
		this.oldQtyTakenOut = oldQtyTakenOut;
	}

	public BackToTownOutDTO(Date gateOutDate, String vehicleNo, BigDecimal qtyTakenOut, String gatePassNo,
			Date gatePassDate, String backToTownTransId, Date backToTownTransDate, String sbTransId, String sbNo,
			BigDecimal backToTownPackages, BigDecimal backToTownWeight, String gateOutId) {
		
		this.gateOutId = gateOutId;
		this.newGateOutDate = gateOutDate;
		this.oldGateOutDate = gateOutDate;
		this.oldTruckNo = vehicleNo;
		this.newTruckNo = vehicleNo;
		this.oldQtyTakenOut = qtyTakenOut;
		this.newQtyTakenOut = qtyTakenOut;
		this.gatePassNo = gatePassNo;
		
		this.backToTownTransId = backToTownTransId;
		this.oldBackToTownTransDate = backToTownTransDate;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.newBackToTownPackages = backToTownPackages;
		this.oldBackToTownPackages = backToTownPackages;
		this.backToTownWeight = backToTownWeight;
		this.auditremarks = "";
	}

}
