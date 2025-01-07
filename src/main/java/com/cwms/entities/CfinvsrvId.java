package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


public class CfinvsrvId implements Serializable {

    private String invoiceNo;
    private String branchId;
    private String companyId;
    private String partyId;
    private String profitcentreId;
    private String containerNo;
	public CfinvsrvId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CfinvsrvId(String invoiceNo, String branchId, String companyId, String partyId, String profitcentreId,
			String containerNo) {
		this.invoiceNo = invoiceNo;
		this.branchId = branchId;
		this.companyId = companyId;
		this.partyId = partyId;
		this.profitcentreId = profitcentreId;
		this.containerNo = containerNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
    
    
}
