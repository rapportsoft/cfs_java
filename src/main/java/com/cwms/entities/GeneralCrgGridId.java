package com.cwms.entities;
import java.io.Serializable;
import java.util.Objects;

class GeneralCrgGridId implements Serializable {
    private String companyId;
    private String branchId;
    private String boeNo;
    private String depositNo;

    public GeneralCrgGridId() {}

    public GeneralCrgGridId(String companyId, String branchId, String boeNo, String depositNo) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.boeNo = boeNo;
        this.depositNo = depositNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralCrgGridId that = (GeneralCrgGridId) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(boeNo, that.boeNo) &&
                Objects.equals(depositNo, that.depositNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, boeNo, depositNo);
    }
}
