package com.cwms.entities;

import java.io.Serializable;


public class CfexBondCrgDtlId implements Serializable{

	private String companyId;

	private String branchId;

	private String finYear;

	private String cfBondDtlId;

	private String nocTransId;

	private String exBondingId;

	private String inBondingId;

	private String nocNo;

	private String boeNo;

	public CfexBondCrgDtlId() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getCfBondDtlId() {
		return cfBondDtlId;
	}

	public void setCfBondDtlId(String cfBondDtlId) {
		this.cfBondDtlId = cfBondDtlId;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public CfexBondCrgDtlId(String companyId, String branchId, String finYear, String cfBondDtlId, String nocTransId,
			String exBondingId, String inBondingId, String nocNo, String boeNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.cfBondDtlId = cfBondDtlId;
		this.nocTransId = nocTransId;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.nocNo = nocNo;
		this.boeNo = boeNo;
	}

	
	
	
}
