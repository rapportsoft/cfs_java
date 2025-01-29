package com.cwms.entities;

import java.io.Serializable;

public class InvCreditTrackId implements Serializable {

	public String companyId;
	public String branchId;
	public String partyId;
	public String transId;
	public String invoiceNo;
	public String assesmentId;
	public InvCreditTrackId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InvCreditTrackId(String companyId, String branchId, String partyId, String transId, String invoiceNo,
			String assesmentId) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.partyId = partyId;
		this.transId = transId;
		this.invoiceNo = invoiceNo;
		this.assesmentId = assesmentId;
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
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getAssesmentId() {
		return assesmentId;
	}
	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}
	
	
	
}
