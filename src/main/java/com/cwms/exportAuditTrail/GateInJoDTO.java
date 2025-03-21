package com.cwms.exportAuditTrail;

import java.util.Date;

public class GateInJoDTO {	
	
	private String sbNo; // Shipping Bill Number
    private String containerNo; // Container Number
    private String auditremarks; // Audit remarks
    private String profitCentreId; // Profit Centre ID
    private String companyId; // Company ID
    private String branchId; // Branch ID
    private String finYear; // Financial Year
    private String userId; // User ID
    private Date dor; // Date of Record

    private String sbtransId; // SB Transaction ID
    private String newVehicleNo; // Truck Number
    private Date newGateInDate; // Gate In Date
    private Date newCartingTransDate; // Cargo Receipt Date
    private String oldVehicleNo; // Old Truck Number
    private Date oldGateInDate; // Old Gate In Date
    private Date oldCartingTransDate;
    private String gateInId;
	public GateInJoDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getAuditremarks() {
		return auditremarks;
	}
	public void setAuditremarks(String auditremarks) {
		this.auditremarks = auditremarks;
	}
	public String getProfitCentreId() {
		return profitCentreId;
	}
	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDor() {
		return dor;
	}
	public void setDor(Date dor) {
		this.dor = dor;
	}
	public String getSbtransId() {
		return sbtransId;
	}
	public void setSbtransId(String sbtransId) {
		this.sbtransId = sbtransId;
	}
	public String getNewVehicleNo() {
		return newVehicleNo;
	}
	public void setNewVehicleNo(String newVehcleNo) {
		this.newVehicleNo = newVehcleNo;
	}
	public Date getNewGateInDate() {
		return newGateInDate;
	}
	public void setNewGateInDate(Date newGateInDate) {
		this.newGateInDate = newGateInDate;
	}
	public Date getNewCartingTransDate() {
		return newCartingTransDate;
	}
	public void setNewCartingTransDate(Date newCartingTransDate) {
		this.newCartingTransDate = newCartingTransDate;
	}
	public String getOldVehicleNo() {
		return oldVehicleNo;
	}
	public void setOldVehicleNo(String oldVehicleNo) {
		this.oldVehicleNo = oldVehicleNo;
	}
	public Date getOldGateInDate() {
		return oldGateInDate;
	}
	public void setOldGateInDate(Date oldGatenDate) {
		this.oldGateInDate = oldGatenDate;
	}
	public Date getOldCartingTransDate() {
		return oldCartingTransDate;
	}
	public void setOldCartingTransDate(Date oldCartingTransDate) {
		this.oldCartingTransDate = oldCartingTransDate;
	}
	public String getGateInId() {
		return gateInId;
	}
	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}
	public GateInJoDTO(String sbNo, String containerNo, String auditremarks, String profitCentreId, String companyId,
			String branchId, String finYear, String userId, Date dor, String sbtransId, String newVehcleNo,
			Date newGateInDate, Date newCartingTransDate, String oldVehicleNo, Date oldGatenDate,
			Date oldCartingTransDate, String gateInId) {
		super();
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.auditremarks = auditremarks;
		this.profitCentreId = profitCentreId;
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.userId = userId;
		this.dor = dor;
		this.sbtransId = sbtransId;
		this.newVehicleNo = newVehcleNo;
		this.newGateInDate = newGateInDate;
		this.newCartingTransDate = newCartingTransDate;
		this.oldVehicleNo = oldVehicleNo;
		this.oldGateInDate = oldGatenDate;
		this.oldCartingTransDate = oldCartingTransDate;
		this.gateInId = gateInId;
		this.auditremarks = "";
	}
	@Override
	public String toString() {
		return "GateInJoDTO [sbNo=" + sbNo + ", containerNo=" + containerNo + ", auditremarks=" + auditremarks
				+ ", profitCentreId=" + profitCentreId + ", companyId=" + companyId + ", branchId=" + branchId
				+ ", finYear=" + finYear + ", userId=" + userId + ", dor=" + dor + ", sbtransId=" + sbtransId
				+ ", newVehicleNo=" + newVehicleNo + ", newGateInDate=" + newGateInDate + ", newCartingTransDate="
				+ newCartingTransDate + ", oldVehicleNo=" + oldVehicleNo + ", oldGateInDate=" + oldGateInDate
				+ ", oldCartingTransDate=" + oldCartingTransDate + ", gateInId=" + gateInId + "]";
	}
	
	
	public GateInJoDTO( String sbtransId, String sbNo, Date sbDate,
			  String gateInId, Date newGateInDate, String oldVehicleNo) {
		super();
		this.sbNo = sbNo;		
		this.sbtransId = sbtransId;
		this.newVehicleNo = oldVehicleNo;
		this.newGateInDate = newGateInDate;
		this.newCartingTransDate = sbDate;
		this.oldVehicleNo = oldVehicleNo;
		this.oldGateInDate = newGateInDate;
		this.oldCartingTransDate = sbDate;
		this.gateInId = gateInId;
		this.auditremarks = "";
	}
	
	
	
	
}
