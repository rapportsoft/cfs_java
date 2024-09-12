package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class CfinbondcrgId implements Serializable {

    private String companyId;
    private String branchId;
    private String finYear;
    private String inBondingId;
    private String inBondingHdrId;
    private String profitcentreId;
    private String nocTransId;
    private String nocNo;

    // Default constructor
    public CfinbondcrgId() {
    }

    // Parameterized constructor
   
    // Getters and Setters
 

    public CfinbondcrgId(String companyId, String branchId, String finYear, String inBondingId, String inBondingHdrId,
			String profitcentreId, String nocTransId, String nocNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingId = inBondingId;
		this.inBondingHdrId = inBondingHdrId;
		this.profitcentreId = profitcentreId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
	}
    
    
    public String getInBondingHdrId() {
		return inBondingHdrId;
	}

	public void setInBondingHdrId(String inBondingHdrId) {
		this.inBondingHdrId = inBondingHdrId;
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

    public String getProfitcentreId() {
        return profitcentreId;
    }

    public void setProfitcentreId(String profitcentreId) {
        this.profitcentreId = profitcentreId;
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

    // Override equals() and hashCode() for composite key comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfinbondcrgId that = (CfinbondcrgId) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(branchId, that.branchId) &&
               Objects.equals(finYear, that.finYear) &&
               Objects.equals(inBondingId, that.inBondingId) &&
               Objects.equals(inBondingHdrId, that.inBondingHdrId) &&
               Objects.equals(profitcentreId, that.profitcentreId) &&
               Objects.equals(nocTransId, that.nocTransId) &&
               Objects.equals(nocNo, that.nocNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, finYear, inBondingId,inBondingHdrId, profitcentreId, nocTransId, nocNo);
    }
}

