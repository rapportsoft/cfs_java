package com.cwms.entities;

import java.io.Serializable;

public class EmptyInventoryId implements Serializable {

	private String companyId;
	private String branchId;
	private String finYear;
	private String erpDocRefNo;
	private String docRefNo;
	private String subDocRefNo;
	private String profitcentreId;
	private String movementCode;
	private String gateInId;
	public EmptyInventoryId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmptyInventoryId(String companyId, String branchId, String finYear, String erpDocRefNo, String docRefNo,
			String subDocRefNo, String profitcentreId, String movementCode, String gateInId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.subDocRefNo = subDocRefNo;
		this.profitcentreId = profitcentreId;
		this.movementCode = movementCode;
		this.gateInId = gateInId;
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
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getMovementCode() {
		return movementCode;
	}
	public void setMovementCode(String movementCode) {
		this.movementCode = movementCode;
	}
	public String getGateInId() {
		return gateInId;
	}
	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}
	
	
	
}
