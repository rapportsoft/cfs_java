package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "cfbondinsbal")
@IdClass(CfbondinsbalID.class)
public class Cfbondinsbal {

    @Id
    @Column(name = "branch_id", length = 6)
    private String branchId;

    @Id
    @Column(name = "company_id", length = 6)
    private String companyId;

    @Id
    @Column(name = "sr_no")
    private int srNo;

    @Column(name = "approved_by", length = 30)
    private String approvedBy;

    @Column(name = "approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date approvedDate;

    @Column(name = "cus_app_area", precision = 16, scale = 3)
    private BigDecimal cusAppArea;

    @Column(name = "cus_app_cargoduty", precision = 16, scale = 3)
    private BigDecimal cusAppCargoDuty;

    @Column(name = "cus_app_cifvalue", precision = 16, scale = 3)
    private BigDecimal cusAppCifValue;

    @Column(name = "edited_by", length = 30)
    private String editedBy;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate;

    @Column(name = "exbond_area", precision = 16, scale = 3)
    private BigDecimal exbondArea;

    @Column(name = "exbond_cargoduty", precision = 16, scale = 3)
    private BigDecimal exbondCargoDuty;

    @Column(name = "exbond_cifvalue", precision = 16, scale = 3)
    private BigDecimal exbondCifValue;

    @Column(name = "inbond_ares", precision = 16, scale = 3)
    private BigDecimal inbondAres;

    @Column(name = "inbond_cargoduty", precision = 16, scale = 3)
    private BigDecimal inbondCargoDuty;

    @Column(name = "inbond_cifvalue", precision = 16, scale = 3)
    private BigDecimal inbondCifValue;

    @Column(name = "policy_amt", precision = 16, scale = 3)
    private BigDecimal policyAmt;

    @Column(name = "policy_company", length = 30)
    private String policyCompany;

    @Column(name = "policy_no", length = 15)
    private String policyNo;

    @Column(name = "status", length = 255)
    private String status;

    @Column(name = "valid_from")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date validFrom;

    @Column(name = "valid_to")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date validTo;

    // Getters and Setters

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public BigDecimal getCusAppArea() {
        return cusAppArea;
    }

    public void setCusAppArea(BigDecimal cusAppArea) {
        this.cusAppArea = cusAppArea;
    }

    public BigDecimal getCusAppCargoDuty() {
        return cusAppCargoDuty;
    }

    public void setCusAppCargoDuty(BigDecimal cusAppCargoDuty) {
        this.cusAppCargoDuty = cusAppCargoDuty;
    }

    public BigDecimal getCusAppCifValue() {
        return cusAppCifValue;
    }

    public void setCusAppCifValue(BigDecimal cusAppCifValue) {
        this.cusAppCifValue = cusAppCifValue;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public BigDecimal getExbondArea() {
        return exbondArea;
    }

    public void setExbondArea(BigDecimal exbondArea) {
        this.exbondArea = exbondArea;
    }

    public BigDecimal getExbondCargoDuty() {
        return exbondCargoDuty;
    }

    public void setExbondCargoDuty(BigDecimal exbondCargoDuty) {
        this.exbondCargoDuty = exbondCargoDuty;
    }

    public BigDecimal getExbondCifValue() {
        return exbondCifValue;
    }

    public void setExbondCifValue(BigDecimal exbondCifValue) {
        this.exbondCifValue = exbondCifValue;
    }

    public BigDecimal getInbondAres() {
        return inbondAres;
    }

    public void setInbondAres(BigDecimal inbondAres) {
        this.inbondAres = inbondAres;
    }

    public BigDecimal getInbondCargoDuty() {
        return inbondCargoDuty;
    }

    public void setInbondCargoDuty(BigDecimal inbondCargoDuty) {
        this.inbondCargoDuty = inbondCargoDuty;
    }

    public BigDecimal getInbondCifValue() {
        return inbondCifValue;
    }

    public void setInbondCifValue(BigDecimal inbondCifValue) {
        this.inbondCifValue = inbondCifValue;
    }

    public BigDecimal getPolicyAmt() {
        return policyAmt;
    }

    public void setPolicyAmt(BigDecimal policyAmt) {
        this.policyAmt = policyAmt;
    }

    public String getPolicyCompany() {
        return policyCompany;
    }

    public void setPolicyCompany(String policyCompany) {
        this.policyCompany = policyCompany;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "Cfbondinsbal{" +
                "branchId='" + branchId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", srNo=" + srNo +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", cusAppArea=" + cusAppArea +
                ", cusAppCargoDuty=" + cusAppCargoDuty +
                ", cusAppCifValue=" + cusAppCifValue +
                ", editedBy='" + editedBy + '\'' +
                ", editedDate=" + editedDate +
                ", exbondArea=" + exbondArea +
                ", exbondCargoDuty=" + exbondCargoDuty +
                ", exbondCifValue=" + exbondCifValue +
                ", inbondAres=" + inbondAres +
                ", inbondCargoDuty=" + inbondCargoDuty +
                ", inbondCifValue=" + inbondCifValue +
                ", policyAmt=" + policyAmt +
                ", policyCompany='" + policyCompany + '\'' +
                ", policyNo='" + policyNo + '\'' +
                ", status='" + status + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                '}';
    }
}
