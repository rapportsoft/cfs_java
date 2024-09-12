package com.cwms.entities;

import java.io.Serializable;

public class CfinbondcrgDtlId implements Serializable{

	
    private String companyId;

    
    private String branchId;

 
    private String finYear;

   
    private String inBondingDtlId;

    
    private String inBondingId;


    private String nocTransId;


    private String nocNo;
    
    
	private String cfBondDtlId;
 
	
	private String boeNo;


	public CfinbondcrgDtlId() {
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


	public String getInBondingDtlId() {
		return inBondingDtlId;
	}


	public void setInBondingDtlId(String inBondingDtlId) {
		this.inBondingDtlId = inBondingDtlId;
	}


	public String getInBondingId() {
		return inBondingId;
	}


	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
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


	public CfinbondcrgDtlId(String companyId, String branchId, String finYear, String inBondingDtlId,
			String inBondingId, String nocTransId, String nocNo, String cfBondDtlId, String boeNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingDtlId = inBondingDtlId;
		this.inBondingId = inBondingId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
	}


	@Override
	public String toString() {
		return "CfinbondcrgDtlId [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", inBondingDtlId=" + inBondingDtlId + ", inBondingId=" + inBondingId + ", nocTransId=" + nocTransId
				+ ", nocNo=" + nocNo + ", cfBondDtlId=" + cfBondDtlId + ", boeNo=" + boeNo + "]";
	}
    
	
	
	
}
