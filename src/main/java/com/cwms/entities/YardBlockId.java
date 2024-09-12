package com.cwms.entities;

public class YardBlockId {

	public String companyId;
	public String finYear;
	public String yardId;
	public String yardLocationId;
	public String blockId;
	public YardBlockId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YardBlockId(String companyId, String finYear, String yardId, String yardLocationId, String blockId) {
		super();
		this.companyId = companyId;
		this.finYear = finYear;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getYardId() {
		return yardId;
	}
	public void setYardId(String yardId) {
		this.yardId = yardId;
	}
	public String getYardLocationId() {
		return yardLocationId;
	}
	public void setYardLocationId(String yardLocationId) {
		this.yardLocationId = yardLocationId;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	
	
}
