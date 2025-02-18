package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class CreditNoteHdrId implements Serializable {
	
	private String companyId;
	private String branchId;
	private String invoiceNo;
	private String finYear;
	private String finPeriod;
	private String profitcentreId;
	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, finPeriod, finYear, invoiceNo, profitcentreId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditNoteHdrId other = (CreditNoteHdrId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(finPeriod, other.finPeriod) && Objects.equals(finYear, other.finYear)
				&& Objects.equals(invoiceNo, other.invoiceNo) && Objects.equals(profitcentreId, other.profitcentreId);
	}
	public CreditNoteHdrId(String companyId, String branchId, String invoiceNo, String finYear, String finPeriod,
			String profitcentreId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.invoiceNo = invoiceNo;
		this.finYear = finYear;
		this.finPeriod = finPeriod;
		this.profitcentreId = profitcentreId;
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
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getFinPeriod() {
		return finPeriod;
	}
	public void setFinPeriod(String finPeriod) {
		this.finPeriod = finPeriod;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public CreditNoteHdrId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
