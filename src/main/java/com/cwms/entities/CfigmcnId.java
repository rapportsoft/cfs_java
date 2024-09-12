package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class CfigmcnId implements Serializable {

    private String companyId;


    private String branchId;

    private String finYear;

    private String igmTransId;

    private String profitcentreId;

    private String igmNo;

    private String igmLineNo;

    private String containerNo;
    private String containerTransId;

	public CfigmcnId() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public String getContainerTransId() {
		return containerTransId;
	}



	public void setContainerTransId(String containerTransId) {
		this.containerTransId = containerTransId;
	}



	public CfigmcnId(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
			String igmNo, String igmLineNo, String containerNo, String containerTransId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerTransId = containerTransId;
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

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
    
    
    
    
}
