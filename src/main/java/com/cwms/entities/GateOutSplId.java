package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class GateOutSplId implements Serializable{
	
	  private String companyId;
	  private String branchId;
	  private String finYear;
	  private String gateOutId;
	  private String erpDocRefNo;
	  private String docRefNo;
	  private int srNo;
	public GateOutSplId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GateOutSplId(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
			String docRefNo, int srNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gateOutId = gateOutId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
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
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getGateOutId() {
		return gateOutId;
	}
	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}
	public String getErpDocRefNo() {
		return erpDocRefNo;
	}
	public void setErpDocRefNo(String erpDocRefNo) {
		this.erpDocRefNo = erpDocRefNo;
	}
	public String getDocRefNo() {
		return docRefNo;
	}
	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, docRefNo, erpDocRefNo, finYear, gateOutId, srNo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GateOutSplId other = (GateOutSplId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(docRefNo, other.docRefNo) && Objects.equals(erpDocRefNo, other.erpDocRefNo)
				&& Objects.equals(finYear, other.finYear) && Objects.equals(gateOutId, other.gateOutId)
				&& Objects.equals(srNo, other.srNo);
	}
	  
	  
	  
	  
}
