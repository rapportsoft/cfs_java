package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class GateInJoDetailDTO {
	
		private String sbNo; // Shipping Bill Number
		private String sbTransId; // SB Transaction ID
	    private String containerNo; // Container Number
	    private String auditremarks; // Audit remarks
	    private String profitCentreId; // Profit Centre ID
	    private String companyId; // Company ID
	    private String branchId; // Branch ID
	    private String finYear; // Financial Year
	    private String userId; // User ID
	    private Date dor; // Date of Record
	    
	 
        private BigDecimal noOfPackages; // Job Number Packages
        private BigDecimal grossWeight; // Job Weight
        
        private BigDecimal newCartedpPkg; // Received Packages
        private BigDecimal newCartedWt; // Received Weight
     
        private BigDecimal oldCartedpPkg; // Received Packages
        private BigDecimal oldCartedWt; // Received Weight
     
        private String gateInId; // Cargo Gate In ID
        private String cartingTransId; // Cargo Carting In ID
        private String cartingLineId; // Cargo Carting Line ID
        
        private int subSrNo;   
        
        
		public int getSubSrNo() {
			return subSrNo;
		}

		public void setSubSrNo(int subSrNo) {
			this.subSrNo = subSrNo;
		}

		public GateInJoDetailDTO() {
			super();
			// TODO Auto-generated constructor stub
		}

		public GateInJoDetailDTO(String sbNo, String sbTransId, String containerNo, String auditremarks,
				String profitCentreId, String companyId, String branchId, String finYear, String userId, Date dor,
				BigDecimal noOfPackages, BigDecimal grossWeight, BigDecimal newCartedpPkg, BigDecimal newCartedWt,
				BigDecimal oldCartedpPkg, BigDecimal oldCartedWt, String gateInId, String cartingTransId,
				String cartingLineId) {
			super();
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.containerNo = containerNo;
			this.auditremarks = auditremarks;
			this.profitCentreId = profitCentreId;
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.userId = userId;
			this.dor = dor;
			this.noOfPackages = noOfPackages;
			this.grossWeight = grossWeight;
			this.newCartedpPkg = newCartedpPkg;
			this.newCartedWt = newCartedWt;
			this.oldCartedpPkg = oldCartedpPkg;
			this.oldCartedWt = oldCartedWt;
			this.gateInId = gateInId;
			this.cartingTransId = cartingTransId;
			this.cartingLineId = cartingLineId;
			this.auditremarks = "";
		}

		public String getSbNo() {
			return sbNo;
		}

		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}

		public String getSbTransId() {
			return sbTransId;
		}

		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}

		public String getContainerNo() {
			return containerNo;
		}

		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
		}

		public String getAuditremarks() {
			return auditremarks;
		}

		public void setAuditremarks(String auditremarks) {
			this.auditremarks = auditremarks;
		}

		public String getProfitCentreId() {
			return profitCentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
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

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Date getDor() {
			return dor;
		}

		public void setDor(Date dor) {
			this.dor = dor;
		}

		public BigDecimal getNoOfPackages() {
			return noOfPackages;
		}

		public void setNoOfPackages(BigDecimal noOfPackages) {
			this.noOfPackages = noOfPackages;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public BigDecimal getNewCartedpPkg() {
			return newCartedpPkg;
		}

		public void setNewCartedpPkg(BigDecimal newCartedpPkg) {
			this.newCartedpPkg = newCartedpPkg;
		}

		public BigDecimal getNewCartedWt() {
			return newCartedWt;
		}

		public void setNewCartedWt(BigDecimal newCartedWt) {
			this.newCartedWt = newCartedWt;
		}

		public BigDecimal getOldCartedpPkg() {
			return oldCartedpPkg;
		}

		public void setOldCartedpPkg(BigDecimal oldCartedpPkg) {
			this.oldCartedpPkg = oldCartedpPkg;
		}

		public BigDecimal getOldCartedWt() {
			return oldCartedWt;
		}

		public void setOldCartedWt(BigDecimal oldCartedWt) {
			this.oldCartedWt = oldCartedWt;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
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

		@Override
		public String toString() {
			return "GateInJoDetailDTO [sbNo=" + sbNo + ", sbTransId=" + sbTransId + ", containerNo=" + containerNo
					+ ", auditremarks=" + auditremarks + ", profitCentreId=" + profitCentreId + ", companyId="
					+ companyId + ", branchId=" + branchId + ", finYear=" + finYear + ", userId=" + userId + ", dor="
					+ dor + ", noOfPackages=" + noOfPackages + ", grossWeight=" + grossWeight + ", newCartedpPkg="
					+ newCartedpPkg + ", newCartedWt=" + newCartedWt + ", oldCartedpPkg=" + oldCartedpPkg
					+ ", oldCartedWt=" + oldCartedWt + ", gateInId=" + gateInId + ", cartingTransId=" + cartingTransId
					+ ", cartingLineId=" + cartingLineId + "]";
		}
		
		
		
		

		public GateInJoDetailDTO(String sbTransId, String sbNo, String gateInId, String cartingTransId,
				BigDecimal noOfPackages, BigDecimal grossWeight, int newCartedpPkg, 
				BigDecimal newCartedWt, String cartingLineId, int subSrNo) {
			super();
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;		
			this.noOfPackages = noOfPackages;
			this.grossWeight = grossWeight;
			this.newCartedpPkg = BigDecimal.valueOf(newCartedpPkg);
			this.newCartedWt = newCartedWt;
			this.oldCartedpPkg = BigDecimal.valueOf(newCartedpPkg);
			this.oldCartedWt = newCartedWt;
			this.gateInId = gateInId;
			this.cartingTransId = cartingTransId;
			this.cartingLineId = cartingLineId;
			this.subSrNo = subSrNo;
			this.auditremarks = "";
		}

		public GateInJoDetailDTO(String sbTransId, String sbNo, String gateInId, String cartingTransId,
				BigDecimal noOfPackages, BigDecimal grossWeight, BigDecimal newCartedpPkg, 
				BigDecimal newCartedWt, String cartingLineId, int subSrNo) {
			super();
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;		
			this.noOfPackages = noOfPackages;
			this.grossWeight = grossWeight;
			this.newCartedpPkg =newCartedpPkg;
			this.newCartedWt = newCartedWt;
			this.oldCartedpPkg = newCartedpPkg;
			this.oldCartedWt = newCartedWt;
			this.gateInId = gateInId;
			this.cartingTransId = cartingTransId;
			this.cartingLineId = cartingLineId;
			this.subSrNo = subSrNo;
			this.auditremarks = "";
		}


}
