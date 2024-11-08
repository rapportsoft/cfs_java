package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class ExportStuffRequestId implements Serializable
{	
		private String companyId;
	    private String branchId;
	    private String finYear;
	    private String stuffReqId;
	    private String sbTransId;
	    private int stuffReqLineId;
		public ExportStuffRequestId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ExportStuffRequestId(String companyId, String branchId, String finYear, String stuffReqId,
				String sbTransId, int stuffReqLineId) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.stuffReqId = stuffReqId;
			this.sbTransId = sbTransId;
			this.stuffReqLineId = stuffReqLineId;
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
		public String getStuffReqId() {
			return stuffReqId;
		}
		public void setStuffReqId(String stuffReqId) {
			this.stuffReqId = stuffReqId;
		}
		public String getSbTransId() {
			return sbTransId;
		}
		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}
		public int getStuffReqLineId() {
			return stuffReqLineId;
		}
		public void setStuffReqLineId(int stuffReqLineId) {
			this.stuffReqLineId = stuffReqLineId;
		}
		@Override
		public int hashCode() {
			return Objects.hash(branchId, companyId, finYear, sbTransId, stuffReqId, stuffReqLineId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ExportStuffRequestId other = (ExportStuffRequestId) obj;
			return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
					&& Objects.equals(finYear, other.finYear) && Objects.equals(sbTransId, other.sbTransId)
					&& Objects.equals(stuffReqId, other.stuffReqId) && stuffReqLineId == other.stuffReqLineId;
		}
	    
	    
	    

}
