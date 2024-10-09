package com.cwms.entities;

import java.io.Serializable;

public class ManualGateInId implements Serializable {
	public String companyId;
	public String branchId;
	public String gateNo;
	public String gateInId;
	public String erpDocRefNo;
	public String docRefNo;
	public String lineNo;
	public int srNo;
	public ManualGateInId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ManualGateInId(String companyId, String branchId, String gateNo, String gateInId, String erpDocRefNo,
			String docRefNo, String lineNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateNo = gateNo;
		this.gateInId = gateInId;
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
	public String getGateNo() {
		return gateNo;
	}
	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}
	public String getGateInId() {
		return gateInId;
	}
	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
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
