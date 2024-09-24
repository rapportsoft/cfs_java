package com.cwms.entities;

import java.io.Serializable;

public class ImportInventoryId implements Serializable {

 
    private String companyId;
    private String branchId;
    private String finYear;
    private String igmTransId;
    private String profitcentreId;
    private String igmNo;
    private String containerNo;
    private String gateInId;
	public ImportInventoryId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ImportInventoryId(String companyId, String branchId, String finYear, String igmTransId,
			String profitcentreId, String igmNo, String containerNo, String gateInId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.containerNo = containerNo;
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
	public String getIgmTransId() {
		return igmTransId;
	}
	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getIgmNo() {
		return igmNo;
	}
	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getGateInId() {
		return gateInId;
	}
	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}
    
    
    
}
