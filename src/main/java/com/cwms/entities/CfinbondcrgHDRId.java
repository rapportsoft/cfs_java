package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class CfinbondcrgHDRId implements Serializable {

    private String companyId;
    private String branchId;
    private String finYear;
    private String inBondingHdrId;
    private String profitcentreId;
    private String nocTransId;
    private String nocNo;
    private String numberOfMarks;

    // Default constructor
    public CfinbondcrgHDRId() {
    }

    // Parameterized constructor
    public CfinbondcrgHDRId(String companyId, String branchId, String finYear, String inBondingHdrId, 
                         String profitcentreId, String nocTransId, String nocNo, String numberOfMarks) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.finYear = finYear;
        this.inBondingHdrId = inBondingHdrId;
        this.profitcentreId = profitcentreId;
        this.nocTransId = nocTransId;
        this.nocNo = nocNo;
        this.numberOfMarks = numberOfMarks;
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

	public String getInBondingHdrId() {
		return inBondingHdrId;
	}

	public void setInBondingHdrId(String inBondingHdrId) {
		this.inBondingHdrId = inBondingHdrId;
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

	public String getNumberOfMarks() {
		return numberOfMarks;
	}

	public void setNumberOfMarks(String numberOfMarks) {
		this.numberOfMarks = numberOfMarks;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfinbondcrgHDRId that = (CfinbondcrgHDRId) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(branchId, that.branchId) &&
               Objects.equals(finYear, that.finYear) &&
               Objects.equals(inBondingHdrId, that.inBondingHdrId) &&
               Objects.equals(profitcentreId, that.profitcentreId) &&
               Objects.equals(nocTransId, that.nocTransId) &&
               Objects.equals(nocNo, that.nocNo) &&
               Objects.equals(numberOfMarks, that.numberOfMarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, finYear, inBondingHdrId, profitcentreId, nocTransId, nocNo, numberOfMarks);
    }
}

