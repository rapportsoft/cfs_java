package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class HubDocumentId implements Serializable {
    private String finYear;
    private String companyId;
    private String branchId;
    private String hubTransId;
    private String igmLineNo;
    private String igmNo;
	public HubDocumentId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HubDocumentId(String finYear, String companyId, String branchId, String hubTransId, String igmLineNo,
			String igmNo) {
		super();
		this.finYear = finYear;
		this.companyId = companyId;
		this.branchId = branchId;
		this.hubTransId = hubTransId;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, finYear, hubTransId, igmLineNo, igmNo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HubDocumentId other = (HubDocumentId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(finYear, other.finYear) && Objects.equals(hubTransId, other.hubTransId)
				&& Objects.equals(igmLineNo, other.igmLineNo) && Objects.equals(igmNo, other.igmNo);
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
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
	public String getHubTransId() {
		return hubTransId;
	}
	public void setHubTransId(String hubTransId) {
		this.hubTransId = hubTransId;
	}
	public String getIgmLineNo() {
		return igmLineNo;
	}
	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}
	public String getIgmNo() {
		return igmNo;
	}
	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}
    
    
    
}
