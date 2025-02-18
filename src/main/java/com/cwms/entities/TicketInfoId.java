package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class TicketInfoId implements Serializable {
	public String companyId;
	public String branchId;
	public String ticketNo;
	protected TicketInfoId() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected TicketInfoId(String companyId, String branchId, String ticketNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.ticketNo = ticketNo;
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
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	
	
}
