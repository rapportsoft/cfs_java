package com.cwms.entities;

import java.io.Serializable;

public class GateInId implements Serializable {

	public String companyId;
	public String branchId;
	public String gateInId;
	public String finYear;
	public String erpDocRefNo;
	public String docRefNo;
	public String lineNo;
	public int srNo;
	public GateInId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GateInId(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
			String docRefNo, String lineNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.lineNo = lineNo;
		this.srNo = srNo;
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
	public String getLineNo() {
		return lineNo;
	}
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
}
