package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class TicketInfoDtlId implements Serializable {

	public String companyId;
	public String branchId;
	public String ticketNo;
	public int srNo;
	protected TicketInfoDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected TicketInfoDtlId(String companyId, String branchId, String ticketNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.ticketNo = ticketNo;
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
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	
	
}
