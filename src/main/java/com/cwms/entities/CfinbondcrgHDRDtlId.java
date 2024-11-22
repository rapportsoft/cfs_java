package com.cwms.entities;

import java.io.Serializable;
import java.util.Date;

public class CfinbondcrgHDRDtlId implements Serializable{

    private String companyId;

    private String branchId;

    private String finYear;

    private String inBondingId;

    private String inBondingDtlId;
    
    private Date inBondingDate;

    private String nocTransId;

    private Date nocTransDate;

    private String nocNo;
    
	private String cfBondDtlId;
    
//	private String boeNo;
	
	
	
	
	
	
	
	

	public CfinbondcrgHDRDtlId() {
		super();
	}

	public CfinbondcrgHDRDtlId(String companyId, String branchId, String finYear, String inBondingId,
			String inBondingDtlId, Date inBondingDate, String nocTransId, Date nocTransDate, String nocNo,
			String cfBondDtlId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingId = inBondingId;
		this.inBondingDtlId = inBondingDtlId;
		this.inBondingDate = inBondingDate;
		this.nocTransId = nocTransId;
		this.nocTransDate = nocTransDate;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
//		this.boeNo = boeNo;
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

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public String getInBondingDtlId() {
		return inBondingDtlId;
	}

	public void setInBondingDtlId(String inBondingDtlId) {
		this.inBondingDtlId = inBondingDtlId;
	}

	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public Date getNocTransDate() {
		return nocTransDate;
	}

	public void setNocTransDate(Date nocTransDate) {
		this.nocTransDate = nocTransDate;
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

//	public String getBoeNo() {
//		return boeNo;
//	}
//
//	public void setBoeNo(String boeNo) {
//		this.boeNo = boeNo;
//	}


    
}
