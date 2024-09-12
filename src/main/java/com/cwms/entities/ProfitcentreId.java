package com.cwms.entities;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ProfitcentreId implements Serializable {
    private String companyId;
    private String profitcentreId;

    private String branchId;

   
    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }


	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProfitcentreId() {
        return profitcentreId;
    }

    public void setProfitcentreId(String profitcentreId) {
        this.profitcentreId = profitcentreId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfitcentreId that = (ProfitcentreId) o;
        return Objects.equals(companyId, that.companyId) &&
               Objects.equals(profitcentreId, that.profitcentreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, profitcentreId);
    }
}