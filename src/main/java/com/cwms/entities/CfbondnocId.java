package com.cwms.entities;

import java.io.Serializable;


import java.io.Serializable;
import java.util.Objects;

public class CfbondnocId implements Serializable {

    private String companyId;
    private String branchId;
    private String nocTransId;

    public CfbondnocId() {
    }

    public CfbondnocId(String companyId, String branchId, String nocTransId) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.nocTransId = nocTransId;
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

    public String getNocTransId() {
        return nocTransId;
    }

    public void setNocTransId(String nocTransId) {
        this.nocTransId = nocTransId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfbondnocId that = (CfbondnocId) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(nocTransId, that.nocTransId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, nocTransId);
    }
}

