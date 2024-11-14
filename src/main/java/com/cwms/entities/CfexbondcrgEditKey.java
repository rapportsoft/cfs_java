package com.cwms.entities;

import java.io.Serializable;
import jakarta.persistence.Column;


public class CfexbondcrgEditKey implements Serializable {
	
	private  Long SrNo;
	
	private String inBondingId;
	
    private String branchId;

    private String companyId;

    private String exBondingId;

    private String nocTransId;

	public Long getSrNo() {
		return SrNo;
	}

	public void setSrNo(Long SrNo) {
		this.SrNo = SrNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
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

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}



	

	public CfexbondcrgEditKey(Long SrNo, String inBondingId, String branchId, String companyId, String exBondingId,
			String nocTransId) {
		super();
		this.SrNo = SrNo;
		this.inBondingId = inBondingId;
		this.branchId = branchId;
		this.companyId = companyId;
		this.exBondingId = exBondingId;
		this.nocTransId = nocTransId;
	}

	public CfexbondcrgEditKey() {
		super();
	}

    // Getters and Setters
}
