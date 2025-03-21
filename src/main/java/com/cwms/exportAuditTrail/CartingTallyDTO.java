package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class CartingTallyDTO {

	// Common fields
	private String sbNo; // Shipping Bill Number
	private String sbTransId; // SB Transaction ID
	private String containerNo; // Container Number
	private String auditremarks; // Audit remarks
	private String profitCentreId; // Profit Centre ID
	private String companyId; // Company ID
	private String branchId; // Branch ID
	private String finYear; // Financial Year
	private String userId; // User ID
	private Date dor; // Date of Record

	private BigDecimal oldCartedpPkg; // Received Packages
	private BigDecimal newCartedpPkg; // Received Packages

	private String newYardLocation; // Grid Location
	private String newBlock; // Grid Location
	private String newCell; // Grid Location
	private int newYardPackages; // Grid Packages
	private BigDecimal newGridweight; // Grid Weight
	private BigDecimal newCellAreaAllocated; // Grid Area

	private String oldYardLocation; // Old Grid Location
	private String oldBlock; // Grid Location
	private String oldCell; // Grid Location
	private int oldYardPackages; // Old Grid Packages
	private BigDecimal oldGridweight; // Old Grid Weight
	private BigDecimal oldCellAreaAllocated; // Old Grid Area

	private String gateInId; // Cargo Gate In ID
	private String cartingTransId; // Cargo Carting In ID
	private String cartingLineId; // Cargo Carting Line ID
	private BigDecimal cellArea; // Received Packages
	private int subSrNo;

	public int getSubSrNo() {
		return subSrNo;
	}

	public void setSubSrNo(int subSrNo) {
		this.subSrNo = subSrNo;
	}

	public BigDecimal getCellArea() {
		return cellArea;
	}

	public void setCellArea(BigDecimal cellArea) {
		this.cellArea = cellArea;
	}

	public CartingTallyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartingTallyDTO(String sbNo, String sbTransId, String containerNo, String auditremarks,
			String profitCentreId, String companyId, String branchId, String finYear, String userId, Date dor,
			BigDecimal oldCartedpPkg, BigDecimal newCartedpPkg, String newYardLocation, int newYardPackages,
			BigDecimal newGridweight, BigDecimal newCellAreaAllocated, String oldYardLocation, int oldYardPackages,
			BigDecimal oldGridweight_old, BigDecimal oldCellAreaAllocated, String gateInId, String cartingTransId,
			String cartingLineId) {
		super();
		this.sbNo = sbNo;
		this.sbTransId = sbTransId;
		this.containerNo = containerNo;
		this.auditremarks = auditremarks;
		this.profitCentreId = profitCentreId;
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.userId = userId;
		this.dor = dor;
		this.oldCartedpPkg = oldCartedpPkg;
		this.newCartedpPkg = newCartedpPkg;
		this.newYardLocation = newYardLocation;
		this.newYardPackages = newYardPackages;
		this.newGridweight = newGridweight;
		this.newCellAreaAllocated = newCellAreaAllocated;
		this.oldYardLocation = oldYardLocation;
		this.oldYardPackages = oldYardPackages;
		this.oldGridweight = oldGridweight_old;
		this.oldCellAreaAllocated = oldCellAreaAllocated;
		this.gateInId = gateInId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
	}

	public String getNewBlock() {
		return newBlock;
	}

	public void setNewBlock(String newBlock) {
		this.newBlock = newBlock;
	}

	public String getNewCell() {
		return newCell;
	}

	public void setNewCell(String newCell) {
		this.newCell = newCell;
	}

	public String getOldBlock() {
		return oldBlock;
	}

	public void setOldBlock(String oldBlock) {
		this.oldBlock = oldBlock;
	}

	public String getOldCell() {
		return oldCell;
	}

	public void setOldCell(String oldCell) {
		this.oldCell = oldCell;
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

	public BigDecimal getOldCartedpPkg() {
		return oldCartedpPkg;
	}

	public void setOldCartedpPkg(BigDecimal oldCartedpPkg) {
		this.oldCartedpPkg = oldCartedpPkg;
	}

	public BigDecimal getNewCartedpPkg() {
		return newCartedpPkg;
	}

	public void setNewCartedpPkg(BigDecimal newCartedpPkg) {
		this.newCartedpPkg = newCartedpPkg;
	}

	public String getNewYardLocation() {
		return newYardLocation;
	}

	public void setNewYardLocation(String newYardLocation) {
		this.newYardLocation = newYardLocation;
	}

	public int getNewYardPackages() {
		return newYardPackages;
	}

	public void setNewYardPackages(int newYardPackages) {
		this.newYardPackages = newYardPackages;
	}

	public BigDecimal getNewGridweight() {
		return newGridweight;
	}

	public void setNewGridweight(BigDecimal newGridweight) {
		this.newGridweight = newGridweight;
	}

	public BigDecimal getNewCellAreaAllocated() {
		return newCellAreaAllocated;
	}

	public void setNewCellAreaAllocated(BigDecimal newCellAreaAllocated) {
		this.newCellAreaAllocated = newCellAreaAllocated;
	}

	public String getOldYardLocation() {
		return oldYardLocation;
	}

	public void setOldYardLocation(String oldYardLocation) {
		this.oldYardLocation = oldYardLocation;
	}

	public int getOldYardPackages() {
		return oldYardPackages;
	}

	public void setOldYardPackages(int oldYardPackages) {
		this.oldYardPackages = oldYardPackages;
	}

	public BigDecimal getOldGridweight_old() {
		return oldGridweight;
	}

	public void setOldGridweight_old(BigDecimal oldGridweight_old) {
		this.oldGridweight = oldGridweight_old;
	}

	public BigDecimal getOldCellAreaAllocated() {
		return oldCellAreaAllocated;
	}

	public void setOldCellAreaAllocated(BigDecimal oldCellAreaAllocated) {
		this.oldCellAreaAllocated = oldCellAreaAllocated;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public String getCartingLineId() {
		return cartingLineId;
	}

	public void setCartingLineId(String cartingLineId) {
		this.cartingLineId = cartingLineId;
	}

	@Override
	public String toString() {
		return "CartingTallyDTO [sbNo=" + sbNo + ", sbTransId=" + sbTransId + ", containerNo=" + containerNo
				+ ", auditremarks=" + auditremarks + ", profitCentreId=" + profitCentreId + ", companyId=" + companyId
				+ ", branchId=" + branchId + ", finYear=" + finYear + ", userId=" + userId + ", dor=" + dor
				+ ", oldCartedpPkg=" + oldCartedpPkg + ", newCartedpPkg=" + newCartedpPkg + ", newYardLocation="
				+ newYardLocation + ", newYardPackages=" + newYardPackages + ", newGridweight=" + newGridweight
				+ ", newCellAreaAllocated=" + newCellAreaAllocated + ", oldYardLocation=" + oldYardLocation
				+ ", oldYardPackages=" + oldYardPackages + ", oldGridweight=" + oldGridweight
				+ ", oldCellAreaAllocated=" + oldCellAreaAllocated + ", gateInId=" + gateInId + ", cartingTransId="
				+ cartingTransId + ", cartingLineId=" + cartingLineId + "]";
	}

	public CartingTallyDTO(String sbTransId, String sbNo, String gateInId, String cartingTransId,
			BigDecimal newCartedpPkg, BigDecimal newCartedWt, String cartingLineId, String yardLocation,
			int yardPackages, BigDecimal cellAreaAllocated, String yardBlock, String blockCellNo,
			BigDecimal newGridweight, int subSrNo) {
		super();
		this.oldBlock = yardBlock;
		this.newBlock = yardBlock;

		this.oldCell = blockCellNo;
		this.newCell = blockCellNo;

		this.sbNo = sbNo;
		this.sbTransId = sbTransId;
		this.newCartedpPkg = newCartedpPkg;
		this.oldCartedpPkg = newCartedpPkg;
		this.newYardLocation = yardLocation;
		this.oldYardLocation = yardLocation;
		this.oldYardPackages = yardPackages;
		this.newYardPackages = yardPackages;
		this.newCellAreaAllocated = cellAreaAllocated;
		this.oldCellAreaAllocated = cellAreaAllocated;
		this.gateInId = gateInId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.auditremarks = "";
		this.newGridweight = newGridweight;
		this.oldGridweight = newGridweight;
		this.subSrNo = subSrNo;
	}

	public CartingTallyDTO(String sbTransId, String sbNo, String gateInId, String cartingTransId,
			BigDecimal newCellAreaAllocated, BigDecimal newCartedpPkg, String cartingLineId, int subSrNo) {
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.gateInId = gateInId;
		this.cartingTransId = cartingTransId;
		this.newCellAreaAllocated = newCellAreaAllocated;
		this.newCartedpPkg = newCartedpPkg;
		this.cartingLineId = cartingLineId;
		this.subSrNo = subSrNo;
	}

}
