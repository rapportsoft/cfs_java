package com.cwms.entities;
import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AuctionId implements Serializable {

    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Column(name = "Notice_Id", length = 10, nullable = false)
    private String noticeId;

    @Column(name = "Container_No", length = 11, nullable = false)
    private String containerNo;

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

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
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
        AuctionId cfauccnId = (AuctionId) o;
        return Objects.equals(companyId, cfauccnId.companyId) &&
                Objects.equals(branchId, cfauccnId.branchId) &&
                Objects.equals(noticeId, cfauccnId.noticeId) &&
                Objects.equals(containerNo, cfauccnId.containerNo) &&
                Objects.equals(noticeAmndNo, cfauccnId.noticeAmndNo) &&
                Objects.equals(finalNoticeId, cfauccnId.finalNoticeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, noticeId, containerNo, noticeAmndNo, finalNoticeId);
    }
}