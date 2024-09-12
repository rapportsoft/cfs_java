package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class CfbondnocdtlId implements Serializable{

	private String companyId;
	private String branchId;
	private String nocTransId;
	private String nocNo;
	private String cfBondDtlId;
	private String boeNo;
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
	public String getNocTransId() {
		return nocTransId;
	}
	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}
	public String getNocNo() {
		return nocNo;
	}
	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}
	public String getCfBondDtlId() {
		return cfBondDtlId;
	}
	public void setCfBondDtlId(String cfBondDtlId) {
		this.cfBondDtlId = cfBondDtlId;
	}
	public String getBoeNo() {
		return boeNo;
	}
	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}
	public CfbondnocdtlId(String companyId, String branchId, String nocTransId, String nocNo, String cfBondDtlId,
			String boeNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
	}
	public CfbondnocdtlId() {
	}
	
	
}
