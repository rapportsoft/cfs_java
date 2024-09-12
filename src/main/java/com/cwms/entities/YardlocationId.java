package com.cwms.entities;

import java.io.Serializable;

public class YardlocationId implements Serializable {

	public String companyId;
	public String finYear;
	public String yardId;
	public String yardLocationId;
	public YardlocationId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YardlocationId(String companyId, String finYear, String yardId, String yardLocationId) {
		super();
		this.companyId = companyId;
		this.finYear = finYear;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
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
	
	
}
