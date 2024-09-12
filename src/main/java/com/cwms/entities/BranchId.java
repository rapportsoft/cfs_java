package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class BranchId implements Serializable {
    
    private String companyId;
    
   
    private String branchId;

    public BranchId() {
        // Default constructor required for JPA
    }

    public BranchId(String companyId, String branchId) {
        this.companyId = companyId;
        this.branchId = branchId;
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

	@Override
	public String toString() {
		return "BranchCompany [companyId=" + companyId + ", branchId=" + branchId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BranchId other = (BranchId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId);
	}
    
	
	
}
