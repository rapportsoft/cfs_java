package com.cwms.entities;

import java.io.Serializable;

public class AuctionDutyId implements Serializable {

	public String companyId;
	public String branchId;
	public String noticeId;
	public String dutyType;
	public String noticeAmndNo;
	public String finalNoticeId;
	public AuctionDutyId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuctionDutyId(String companyId, String branchId, String noticeId, String dutyType, String noticeAmndNo,
			String finalNoticeId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.noticeId = noticeId;
		this.dutyType = dutyType;
		this.noticeAmndNo = noticeAmndNo;
		this.finalNoticeId = finalNoticeId;
	}

	
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
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getDutyType() {
		return dutyType;
	}
	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
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
	
	
	
}
