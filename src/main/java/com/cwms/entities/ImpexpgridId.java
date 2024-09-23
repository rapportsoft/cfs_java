package com.cwms.entities;

import java.io.Serializable;

public class ImpexpgridId implements Serializable {


    private String companyId;
    private String finYear;
    private String branchId;
    private String processTransId;
    private int lineNo;
    private int subSrNo;
	public ImpexpgridId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ImpexpgridId(String companyId, String finYear, String branchId, String processTransId, int lineNo,
			int subSrNo) {
		super();
		this.companyId = companyId;
		this.finYear = finYear;
		this.branchId = branchId;
		this.processTransId = processTransId;
		this.lineNo = lineNo;
		this.subSrNo = subSrNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getProcessTransId() {
		return processTransId;
	}
	public void setProcessTransId(String processTransId) {
		this.processTransId = processTransId;
	}
	public int getLineNo() {
		return lineNo;
	}
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
	public int getSubSrNo() {
		return subSrNo;
	}
	public void setSubSrNo(int subSrNo) {
		this.subSrNo = subSrNo;
	}
    
    
}
