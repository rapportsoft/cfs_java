package com.cwms.entities;

import java.io.Serializable;

public class GateOutId implements Serializable {
	
	    private String companyId;

	    private String branchId;

	    private String finYear;

	    private String gateOutId;

	    private String erpDocRefNo;

	    private String docRefNo;

	    private String srNo;

		public GateOutId() {
			super();
			// TODO Auto-generated constructor stub
		}

		public GateOutId(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
				String docRefNo, String srNo) {
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

		public String getSrNo() {
			return srNo;
		}

		public void setSrNo(String srNo) {
			this.srNo = srNo;
		}
	    
	    
	    
}
