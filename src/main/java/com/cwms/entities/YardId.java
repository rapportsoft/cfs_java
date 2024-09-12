package com.cwms.entities;

import java.io.Serializable;

public class YardId implements Serializable {

	public String companyId;
	public String finYear;
	public String yardId;
	public YardId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public YardId(String companyId, String finYear, String yardId) {
		super();
		this.companyId = companyId;
		this.finYear = finYear;
		this.yardId = yardId;
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
	
	
	
}
