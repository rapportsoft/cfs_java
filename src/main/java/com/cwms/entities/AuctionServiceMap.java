package com.cwms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Auction_Service_Map")
public class AuctionServiceMap {

	@Id
	@Column(name="Auction_Type",length = 20)
	public String auctionType;
	
	@Column(name="Auction_Key",length = 20)
	public String auctionKey;
	
	@Column(name="Auction_Value", length = 20)
	public String auctionValue;

	public AuctionServiceMap() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuctionServiceMap(String auctionType, String auctionKey, String auctionValue) {
		super();
		this.auctionType = auctionType;
		this.auctionKey = auctionKey;
		this.auctionValue = auctionValue;
	}

	public String getAuctionType() {
		return auctionType;
	}

	public void setAuctionType(String auctionType) {
		this.auctionType = auctionType;
	}

	public String getAuctionKey() {
		return auctionKey;
	}

	public void setAuctionKey(String auctionKey) {
		this.auctionKey = auctionKey;
	}

	public String getAuctionValue() {
		return auctionValue;
	}

	public void setAuctionValue(String auctionValue) {
		this.auctionValue = auctionValue;
	}
	
	
	
}
