package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class HubGatePassId implements Serializable {	
	  
	    private String companyId;	   
	    private String branchId;
	    private String finYear;
	    private String gatepassId;
	    private String sbTransId;
	    private int stuffReqLineId;
		public HubGatePassId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public HubGatePassId(String companyId, String branchId, String finYear, String gatepassId, String sbTransId,
				int stuffReqLineId) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.gatepassId = gatepassId;
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
		public String getGatepassId() {
			return gatepassId;
		}
		public void setGatepassId(String gatepassId) {
			this.gatepassId = gatepassId;
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
			return Objects.hash(branchId, companyId, finYear, gatepassId, sbTransId, stuffReqLineId);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			HubGatePassId other = (HubGatePassId) obj;
			return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
					&& Objects.equals(finYear, other.finYear) && Objects.equals(gatepassId, other.gatepassId)
					&& Objects.equals(sbTransId, other.sbTransId) && stuffReqLineId == other.stuffReqLineId;
		}
	
}
