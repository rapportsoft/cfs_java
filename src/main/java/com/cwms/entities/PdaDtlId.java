package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PdaDtlId implements Serializable {

	
	public String companyId;
	public String branchId;
	public String partyId;
	public Date transDate;
	public BigDecimal srNo;
	public PdaDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PdaDtlId(String companyId, String branchId, String partyId, Date transDate, BigDecimal srNo) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.transDate = transDate;
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
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public BigDecimal getSrNo() {
		return srNo;
	}
	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
	
	
	
}
