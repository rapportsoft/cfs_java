package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class AuctionDetailId implements Serializable {

    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
    private String profitcentreId;

    @Column(name = "Notice_Id", length = 10, nullable = false)
    private String noticeId;

    @Column(name = "Notice_Amnd_No", length = 3, nullable = false)
    private String noticeAmndNo;

    @Column(name = "Final_Notice_Id", length = 10, nullable = false)
    private String finalNoticeId;

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

    public String getProfitcentreId() {
        return profitcentreId;
    }

    public void setProfitcentreId(String profitcentreId) {
        this.profitcentreId = profitcentreId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeAmndNo() {
        return noticeAmndNo;
    }

    public void setNoticeAmndNo(String noticeAmndNo) {
        this.noticeAmndNo = noticeAmndNo;
    }

    public String getFinalNoticeId() {
        return finalNoticeId;
    }

    public void setFinalNoticeId(String finalNoticeId) {
        this.finalNoticeId = finalNoticeId;
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionDetailId that = (AuctionDetailId) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(profitcentreId, that.profitcentreId) &&
                Objects.equals(noticeId, that.noticeId) &&
                Objects.equals(noticeAmndNo, that.noticeAmndNo) &&
                Objects.equals(finalNoticeId, that.finalNoticeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, profitcentreId, noticeId, noticeAmndNo, finalNoticeId);
    }
}