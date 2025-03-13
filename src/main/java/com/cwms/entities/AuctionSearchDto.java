package com.cwms.entities;

public class AuctionSearchDto {

	public String noticeId;
	public String igmTransId;
	public String igmNo;
	public String igmLineNo;
	public String bidId;
	public String auctionId;
	public String gatePassId;
	public String gateOutId;
	public AuctionSearchDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AuctionSearchDto(String noticeId, String igmTransId, String igmNo, String igmLineNo, String bidId,
			String auctionId, String gatePassId, String gateOutId) {
		super();
		this.noticeId = noticeId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.bidId = bidId;
		this.auctionId = auctionId;
		this.gatePassId = gatePassId;
		this.gateOutId = gateOutId;
	}
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getIgmTransId() {
		return igmTransId;
	}
	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}
	public String getIgmNo() {
		return igmNo;
	}
	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}
	public String getIgmLineNo() {
		return igmLineNo;
	}
	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}
	public String getGatePassId() {
		return gatePassId;
	}
	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}
	public String getGateOutId() {
		return gateOutId;
	}
	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}
	
	
}
