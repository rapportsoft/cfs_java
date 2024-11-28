package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;


public class ExportTransferId implements Serializable{
	
	private String companyId;

    private String branchId;

    private String finYear;

    private String srNo;
    private String sbChangeTransId;
    private String profitcentreId;
    private String sbTransId;
    private String trfSbTransId;
    private String sbLineNo;
    private String trfSbLineNo;
    private String sbNo;
    private String trfSbNo;
    
	public ExportTransferId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExportTransferId(String companyId, String branchId, String finYear, String srNo, String sbChangeTransId,
			String profitcentreId, String sbTransId, String trfSbTransId, String sbLineNo, String trfSbLineNo,
			String sbNo, String trfSbNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.srNo = srNo;
		this.sbChangeTransId = sbChangeTransId;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.trfSbTransId = trfSbTransId;
		this.sbLineNo = sbLineNo;
		this.trfSbLineNo = trfSbLineNo;
		this.sbNo = sbNo;
		this.trfSbNo = trfSbNo;
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
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	public String getSbChangeTransId() {
		return sbChangeTransId;
	}
	public void setSbChangeTransId(String sbChangeTransId) {
		this.sbChangeTransId = sbChangeTransId;
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
	public String getTrfSbTransId() {
		return trfSbTransId;
	}
	public void setTrfSbTransId(String trfSbTransId) {
		this.trfSbTransId = trfSbTransId;
	}
	public String getSbLineNo() {
		return sbLineNo;
	}
	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}
	public String getTrfSbLineNo() {
		return trfSbLineNo;
	}
	public void setTrfSbLineNo(String trfSbLineNo) {
		this.trfSbLineNo = trfSbLineNo;
	}
	public String getSbNo() {
		return sbNo;
	}
	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, finYear, profitcentreId, sbChangeTransId, sbLineNo, sbNo, sbTransId,
				srNo, trfSbLineNo, trfSbNo, trfSbTransId);
	}

	public String getTrfSbNo() {
		return trfSbNo;
	}

	public void setTrfSbNo(String trfSbNo) {
		this.trfSbNo = trfSbNo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExportTransferId other = (ExportTransferId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(finYear, other.finYear) && Objects.equals(profitcentreId, other.profitcentreId)
				&& Objects.equals(sbChangeTransId, other.sbChangeTransId) && Objects.equals(sbLineNo, other.sbLineNo)
				&& Objects.equals(sbNo, other.sbNo) && Objects.equals(sbTransId, other.sbTransId)
				&& Objects.equals(srNo, other.srNo) && Objects.equals(trfSbLineNo, other.trfSbLineNo)
				&& Objects.equals(trfSbNo, other.trfSbNo) && Objects.equals(trfSbTransId, other.trfSbTransId);
	}
	
    
    
}
