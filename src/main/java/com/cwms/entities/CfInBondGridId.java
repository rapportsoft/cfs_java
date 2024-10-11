package com.cwms.entities;

import java.io.Serializable;
import jakarta.persistence.*;

public class CfInBondGridId implements Serializable {

    @Column(length = 6)
    private String companyId;

    @Column(length = 6)
    private String branchId;

    @Column(length = 4)
    private String finYear;

    @Column(length = 10)
    private String nocTransId;

    @Column(length = 10)
    private String inBondingId;

    @Column(length = 3)
    private Integer srNo;
   
 	@Column(length = 6)
 	private String cfBondDtlId;


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

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}
	
	public Integer getSrNo() {
		return srNo;
	}
	public void setSrNo(Integer srNo) {
		this.srNo = srNo;
	}


	public String getCfBondDtlId() {
		return cfBondDtlId;
	}

	public void setCfBondDtlId(String cfBondDtlId) {
		this.cfBondDtlId = cfBondDtlId;
	}

	
	public CfInBondGridId(String companyId, String branchId, String finYear, String nocTransId, String inBondingId,
			Integer srNo, String cfBondDtlId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.nocTransId = nocTransId;
		this.inBondingId = inBondingId;
		this.srNo = srNo;
		this.cfBondDtlId = cfBondDtlId;
	}

	public CfInBondGridId() {
		super();
		// TODO Auto-generated constructor stub
	}

    // Getters and Setters

    // equals() and hashCode() methods
	
	
	
}
