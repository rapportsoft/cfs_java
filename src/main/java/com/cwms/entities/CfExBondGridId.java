package com.cwms.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CfExBondGridId implements Serializable {
    
    private String companyId;
    private String branchId;
    private String finYear;
    private String exBondingId;
    private String inBondingId;
    private String nocTransId;
    private Integer srNo;
    private String cfBondDtlId;

    // Default Constructor
    public CfExBondGridId() {}

    // Parameterized Constructor
    public CfExBondGridId(String companyId, String branchId, String finYear, String exBondingId, 
                          String inBondingId, String nocTransId, Integer srNo, String cfBondDtlId) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.finYear = finYear;
        this.exBondingId = exBondingId;
        this.inBondingId = inBondingId;
        this.nocTransId = nocTransId;
        this.srNo = srNo;
        this.cfBondDtlId = cfBondDtlId;
    }

    // Getters and Setters
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

    public String getNocTransId() {
        return nocTransId;
    }

    public void setNocTransId(String nocTransId) {
        this.nocTransId = nocTransId;
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

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfExBondGridId that = (CfExBondGridId) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(finYear, that.finYear) &&
                Objects.equals(exBondingId, that.exBondingId) &&
                Objects.equals(inBondingId, that.inBondingId) &&
                Objects.equals(nocTransId, that.nocTransId) &&
                Objects.equals(srNo, that.srNo) &&
                Objects.equals(cfBondDtlId, that.cfBondDtlId);
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, finYear, exBondingId, inBondingId, nocTransId, srNo, cfBondDtlId);
    }
}
