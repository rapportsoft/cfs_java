package com.cwms.entities;

import java.io.Serializable;

public class ImportGatePassId implements Serializable {

	    private String companyId;

	    private String branchId;

	    private String finYear;

	    private String gatePassId;

	    private int srNo;

		public ImportGatePassId() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ImportGatePassId(String companyId, String branchId, String finYear, String gatePassId, int srNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.gatePassId = gatePassId;
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

		public String getGatePassId() {
			return gatePassId;
		}

		public void setGatePassId(String gatePassId) {
			this.gatePassId = gatePassId;
		}

		public int getSrNo() {
			return srNo;
		}

		public void setSrNo(int srNo) {
			this.srNo = srNo;
		}
	    
	    
	    
	    
}
