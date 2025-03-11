package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;
public class GeneralReceivingGateInDtlId implements Serializable{

	    private String companyId;

	    private String branchId;

	    private int srNo;

	    private String receivingId;

	    private String gateInId;

		public GeneralReceivingGateInDtlId(String companyId, String branchId, int srNo, String receivingId,
				String gateInId) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.srNo = srNo;
			this.receivingId = receivingId;
			this.gateInId = gateInId;
		}

		public GeneralReceivingGateInDtlId() {
			super();
			// TODO Auto-generated constructor stub
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

		public int getSrNo() {
			return srNo;
		}

		public void setSrNo(int srNo) {
			this.srNo = srNo;
		}

		public String getReceivingId() {
			return receivingId;
		}

		public void setReceivingId(String receivingId) {
			this.receivingId = receivingId;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
		}
	    
	    
	    
	    
	    
	    
	    
		// Override equals() and hashCode()
	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        GeneralReceivingGateInDtlId that = (GeneralReceivingGateInDtlId) o;
	        return Objects.equals(companyId, that.companyId) &&
	               Objects.equals(branchId, that.branchId) &&
	               Objects.equals(srNo, that.srNo) &&
	               Objects.equals(receivingId, that.receivingId) &&
	               Objects.equals(gateInId, that.gateInId);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(companyId, branchId, srNo, receivingId, gateInId);
	    }	    
}