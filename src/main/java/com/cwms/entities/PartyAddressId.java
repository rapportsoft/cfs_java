package com.cwms.entities;

import java.io.Serializable;

public class PartyAddressId implements Serializable {

	public String companyId;
	
	public String branchId;

	public String partyId;

	public String srNo;

	public PartyAddressId() {
		super();
		// TODO Auto-generated constructor stub
	}

	


    

	
	public PartyAddressId(String companyId, String branchId, String partyId, String srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.srNo = srNo;
	}







	public String getBranchId() {
		return branchId;
	}



	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}



	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}







	public String getSrNo() {
		return srNo;
	}







	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	
}
