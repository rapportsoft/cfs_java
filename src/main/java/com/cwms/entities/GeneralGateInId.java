package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
public class GeneralGateInId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String companyId;
    private String branchId;
    private String gateInId;
    private int srNo;

    public GeneralGateInId() {
    }

    public GeneralGateInId(String companyId, String branchId, String gateInId, int srNo) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.gateInId = gateInId;
        this.srNo = srNo;
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

    public String getGateInId() {
        return gateInId;
    }

    public void setGateInId(String gateInId) {
        this.gateInId = gateInId;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralGateInId that = (GeneralGateInId) o;
        return srNo == that.srNo &&
                Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(gateInId, that.gateInId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, gateInId, srNo);
    }
}
