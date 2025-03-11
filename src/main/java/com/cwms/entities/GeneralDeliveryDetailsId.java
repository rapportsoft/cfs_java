package com.cwms.entities;

import java.io.Serializable;

public class GeneralDeliveryDetailsId implements Serializable {

	    private String companyId;

	    private String branchId;

	    private String deliveryId;

	    private String receivingId;

	    private int srNo;

		public GeneralDeliveryDetailsId(String companyId, String branchId, String deliveryId, String receivingId,
				int srNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.deliveryId = deliveryId;
			this.receivingId = receivingId;
			this.srNo = srNo;
		}

		public GeneralDeliveryDetailsId() {
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

		public String getDeliveryId() {
			return deliveryId;
		}

		public void setDeliveryId(String deliveryId) {
			this.deliveryId = deliveryId;
		}

		public String getReceivingId() {
			return receivingId;
		}

		public void setReceivingId(String receivingId) {
			this.receivingId = receivingId;
		}

		public int getSrNo() {
			return srNo;
		}

		public void setSrNo(int srNo) {
			this.srNo = srNo;
		}
	    
	    
	    

}
