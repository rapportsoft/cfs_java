package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class AuctionRecordingId implements Serializable {
	public String companyId;
	public String branchId;
	public String bidId;
	public String auctionNo;
	public int srNo;
	public AuctionRecordingId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuctionRecordingId(String companyId, String branchId, String bidId, String auctionNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.bidId = bidId;
		this.auctionNo = auctionNo;
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
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getAuctionNo() {
		return auctionNo;
	}
	public void setAuctionNo(String auctionNo) {
		this.auctionNo = auctionNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
	
}
