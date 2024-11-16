package com.cwms.entities;

import java.io.Serializable;
import java.util.Date;

public class ExportBackToTownId implements Serializable {
    private String companyId;
    private String branchId;
    private String backToTownTransId;
    private String backToTownLineId;
    private String profitcentreId;
    private String sbTransId;
    private String sbLineNo;
    private String sbNo;
	public ExportBackToTownId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExportBackToTownId(String companyId, String branchId, String backToTownTransId, String backToTownLineId,
			String profitcentreId, String sbTransId, String sbLineNo, String sbNo) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.backToTownTransId = backToTownTransId;
		this.backToTownLineId = backToTownLineId;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.sbLineNo = sbLineNo;
		this.sbNo = sbNo;
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
	public String getBackToTownTransId() {
		return backToTownTransId;
	}
	public void setBackToTownTransId(String backToTownTransId) {
		this.backToTownTransId = backToTownTransId;
	}
	public String getBackToTownLineId() {
		return backToTownLineId;
	}
	public void setBackToTownLineId(String backToTownLineId) {
		this.backToTownLineId = backToTownLineId;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	
	public String getSbTransId() {
		return sbTransId;
	}
	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}
	public String getSbLineNo() {
		return sbLineNo;
	}
	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}
	public String getSbNo() {
		return sbNo;
	}
	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}
    
    
    
    
}
