package com.cwms.entities;

import java.io.Serializable;

public class AuctionBidId implements Serializable {
	public String companyId;
	public String branchId;
	public String bidId;
	public String auctionItemNo;
	public AuctionBidId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuctionBidId(String companyId, String branchId, String bidId, String auctionItemNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.bidId = bidId;
		this.auctionItemNo = auctionItemNo;
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
	public String getAuctionItemNo() {
		return auctionItemNo;
	}
	public void setAuctionItemNo(String auctionItemNo) {
		this.auctionItemNo = auctionItemNo;
	}
	
	
	
	
}
