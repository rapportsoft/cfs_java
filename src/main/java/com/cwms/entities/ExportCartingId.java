package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class ExportCartingId implements Serializable
{
	    private String companyId;
	    private String branchId;
	    private String cartingTransId;
	    private String cartingLineId;
	    private String finYear;
	    private String profitcentreId;
	    private String sbTransId;
	    private String sbNo;
	    private String sbLineNo;
	    
	    
		public ExportCartingId(String companyId, String branchId, String cartingTransId, String cartingLineId,
				String finYear, String profitcentreId, String sbTransId, String sbNo, String sbLineNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.cartingTransId = cartingTransId;
			this.cartingLineId = cartingLineId;
			this.finYear = finYear;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.sbNo = sbNo;
			this.sbLineNo = sbLineNo;
		}
		public ExportCartingId() {
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
		public String getCartingTransId() {
			return cartingTransId;
		}
		public void setCartingTransId(String cartingTransId) {
			this.cartingTransId = cartingTransId;
		}
		public String getCartingLineId() {
			return cartingLineId;
		}
		public void setCartingLineId(String cartingLineId) {
			this.cartingLineId = cartingLineId;
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
		public String getSbNo() {
			return sbNo;
		}
		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}
		public String getSbLineNo() {
			return sbLineNo;
		}
		public void setSbLineNo(String sbLineNo) {
			this.sbLineNo = sbLineNo;
		}
		@Override
		public int hashCode() {
			return Objects.hash(branchId, cartingLineId, cartingTransId, companyId, finYear, profitcentreId, sbLineNo,
					sbNo, sbTransId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExportCartingId other = (ExportCartingId) obj;
			return Objects.equals(branchId, other.branchId) && Objects.equals(cartingLineId, other.cartingLineId)
					&& Objects.equals(cartingTransId, other.cartingTransId)
					&& Objects.equals(companyId, other.companyId) && Objects.equals(finYear, other.finYear)
					&& Objects.equals(profitcentreId, other.profitcentreId) && Objects.equals(sbLineNo, other.sbLineNo)
					&& Objects.equals(sbNo, other.sbNo) && Objects.equals(sbTransId, other.sbTransId);
		}

	    
	    
}
