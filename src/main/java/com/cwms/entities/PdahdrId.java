package com.cwms.entities;

import java.io.Serializable;
import java.util.Date;

public class PdahdrId implements Serializable {

	public String companyId;
	public String branchId;
	public String partyId;
	public Date transDate;
	public PdahdrId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PdahdrId(String companyId, String branchId, String partyId, Date transDate) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.transDate = transDate;
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
	
	
	
}
