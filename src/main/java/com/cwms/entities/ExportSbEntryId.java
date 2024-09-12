package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class ExportSbEntryId implements Serializable
{
	
	    private String companyId;
	    private String branchId;
	    private String finYear;
	    private String profitcentreId;
	    private String sbTransId;
	    private String sbNo;
		public ExportSbEntryId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ExportSbEntryId(String companyId, String branchId, String finYear, String profitcentreId,
				String sbTransId, String sbNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.sbNo = sbNo;
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
		public String getSbNo() {
			return sbNo;
		}
		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}
		@Override
		public int hashCode() {
			return Objects.hash(branchId, companyId, finYear, profitcentreId, sbNo, sbTransId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExportSbEntryId other = (ExportSbEntryId) obj;
			return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
					&& Objects.equals(finYear, other.finYear) && Objects.equals(profitcentreId, other.profitcentreId)
					&& Objects.equals(sbNo, other.sbNo) && Objects.equals(sbTransId, other.sbTransId);
		}
}
