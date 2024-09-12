package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


public class CfigmcrgId implements Serializable {

	    private String companyId = "";

	    private String branchId = "";

	    private String finYear = "";

	    private String igmTransId = "";

	    private String profitcentreId = "";

	    private String igmLineNo = "";

	    private String igmNo = "";
	    
	    private String igmCrgTransId;

		public CfigmcrgId() {
			super();
			// TODO Auto-generated constructor stub
		}

		
		public CfigmcrgId(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
				String igmLineNo, String igmNo, String igmCrgTransId) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.igmTransId = igmTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.igmCrgTransId = igmCrgTransId;
		}
		
		


		public String getIgmCrgTransId() {
			return igmCrgTransId;
		}


		public void setIgmCrgTransId(String igmCrgTransId) {
			this.igmCrgTransId = igmCrgTransId;
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

		public String getIgmTransId() {
			return igmTransId;
		}

		public void setIgmTransId(String igmTransId) {
			this.igmTransId = igmTransId;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
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
