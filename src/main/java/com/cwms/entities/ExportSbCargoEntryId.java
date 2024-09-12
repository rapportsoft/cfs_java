package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class ExportSbCargoEntryId implements Serializable {
	
	private Long srno;
	private String companyId;
    private String branchId;
    private String finYear;
    private String profitcentreId;
    private String sbTransId;
    private String sbLineNo;
    private String sbNo;
    
    
    
    
	public ExportSbCargoEntryId() {
		super();
		// TODO Auto-generated constructor stub
	}




	public ExportSbCargoEntryId(Long srno, String companyId, String branchId, String finYear, String profitcentreId,
			String sbTransId, String sbLineNo, String sbNo) {
		super();
		this.srno = srno;
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.sbLineNo = sbLineNo;
		this.sbNo = sbNo;
	}




	public Long getSrno() {
		return srno;
	}




	public void setSrno(Long srno) {
		this.srno = srno;
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




	public String getFinYear() {
		return finYear;
	}




	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}




	public String getProfitcentreId() {
		return profitcentreId;
	}




	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}




	public String getSbTransId() {
		return sbTransId;
	}




	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}




	public String getSbLineNo() {
		return sbLineNo;
	}




	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}




	public String getSbNo() {
		return sbNo;
	}




	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}




	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, finYear, profitcentreId, sbLineNo, sbNo, sbTransId, srno);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportSbCargoEntryId other = (ExportSbCargoEntryId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(finYear, other.finYear) && Objects.equals(profitcentreId, other.profitcentreId)
				&& Objects.equals(sbLineNo, other.sbLineNo) && Objects.equals(sbNo, other.sbNo)
				&& Objects.equals(sbTransId, other.sbTransId) && Objects.equals(srno, other.srno);
	}
}
