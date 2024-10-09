package com.cwms.entities;

import java.io.Serializable;

public class HoldDetailsId implements Serializable {
	    private String companyId;
	    private String branchId;
	    private String containerNo;
	    private String gateInId;
	    private String docRefNo;
	    private String igmTransId;
	    private String igmNo;
	    private int hldSrNo;
		public HoldDetailsId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public HoldDetailsId(String companyId, String branchId, String containerNo, String gateInId, String docRefNo,
				String igmTransId, String igmNo, int hldSrNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.containerNo = containerNo;
			this.gateInId = gateInId;
			this.docRefNo = docRefNo;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.hldSrNo = hldSrNo;
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
		public String getDocRefNo() {
			return docRefNo;
		}
		public void setDocRefNo(String docRefNo) {
			this.docRefNo = docRefNo;
		}
		public String getIgmTransId() {
			return igmTransId;
		}
		public void setIgmTransId(String igmTransId) {
			this.igmTransId = igmTransId;
		}
		public String getIgmNo() {
			return igmNo;
		}
		public void setIgmNo(String igmNo) {
			this.igmNo = igmNo;
		}
		public int getHldSrNo() {
			return hldSrNo;
		}
		public void setHldSrNo(int hldSrNo) {
			this.hldSrNo = hldSrNo;
		}
	    
	    
}
