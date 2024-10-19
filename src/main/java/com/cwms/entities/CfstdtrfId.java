package com.cwms.entities;

import java.io.Serializable;

public class CfstdtrfId implements Serializable {

    private String companyId;

    private String branchId;

    private String finYear;

    private String profitCentreId;

    private String cfsTariffNo;

    private String cfsAmndNo;

    private String partyId;

    private String status;

	public CfstdtrfId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CfstdtrfId(String companyId, String branchId, String finYear, String profitCentreId, String cfsTariffNo,
			String cfsAmndNo, String partyId, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitCentreId = profitCentreId;
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.partyId = partyId;
		this.status = status;
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

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}

	public String getCfsTariffNo() {
		return cfsTariffNo;
	}

	public void setCfsTariffNo(String cfsTariffNo) {
		this.cfsTariffNo = cfsTariffNo;
	}

	public String getCfsAmndNo() {
		return cfsAmndNo;
	}

	public void setCfsAmndNo(String cfsAmndNo) {
		this.cfsAmndNo = cfsAmndNo;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
