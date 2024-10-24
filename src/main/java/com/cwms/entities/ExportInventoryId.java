package com.cwms.entities;

public class ExportInventoryId {
	  private String companyId;
	    private String branchId;
	    private String sbTransId;
	    private String profitcentreId;
	    private String sbNo;
	    private String containerNo;
	    private String gateInId;
		public ExportInventoryId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public ExportInventoryId(String companyId, String branchId, String sbTransId, String profitcentreId,
				String sbNo, String containerNo, String gateInId) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.sbTransId = sbTransId;
			this.profitcentreId = profitcentreId;
			this.sbNo = sbNo;
			this.containerNo = containerNo;
			this.gateInId = gateInId;
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
		public String getSbTransId() {
			return sbTransId;
		}
		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}
		public String getProfitcentreId() {
			return profitcentreId;
		}
		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}
		public String getSbNo() {
			return sbNo;
		}
		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}
		public String getContainerNo() {
			return containerNo;
		}
		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
		}
		public String getGateInId() {
			return gateInId;
		}
		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
		}
	    
	    
}
