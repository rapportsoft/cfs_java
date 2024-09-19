package com.cwms.entities;

import java.io.Serializable;

public class CfImportGatePassVehDtlId implements Serializable {
	private String companyId;
	private String branchId;
	private String finYear;
	private String profitcentreId;
	private String gatePassId;
	private int srNo;
	private String containerNo;
	private String igmNo;
	private String igmLineNo;
	private String igmTransId;
	public CfImportGatePassVehDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CfImportGatePassVehDtlId(String companyId, String branchId, String finYear, String profitcentreId,
			String gatePassId, int srNo, String containerNo, String igmNo, String igmLineNo, String igmTransId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.containerNo = containerNo;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmTransId = igmTransId;
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
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getGatePassId() {
		return gatePassId;
	}
	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
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
	public String getIgmNo() {
		return igmNo;
	}
	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}
	public String getIgmLineNo() {
		return igmLineNo;
	}
	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}
	public String getIgmTransId() {
		return igmTransId;
	}
	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}
	
	
	
}
