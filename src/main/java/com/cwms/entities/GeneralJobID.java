package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class GeneralJobID implements Serializable {
    private String companyId;
    private String branchId;
    private String jobTransId;
    private String jobNo;

    public GeneralJobID() {
    }

    public GeneralJobID(String companyId, String branchId, String jobTransId, String jobNo) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.jobTransId = jobTransId;
        this.jobNo = jobNo;
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

    public String getJobTransId() {
        return jobTransId;
    }

    public void setJobTransId(String jobTransId) {
        this.jobTransId = jobTransId;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralJobID that = (GeneralJobID) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(jobTransId, that.jobTransId) &&
                Objects.equals(jobNo, that.jobNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, jobTransId, jobNo);
    }
}
