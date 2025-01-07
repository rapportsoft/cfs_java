package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class FinTransId implements Serializable {

	    private String companyId;
	    private String branchId;
	    private String docType;
	    private String ledgerType;
	    private BigDecimal lineId;
	    private String transId;
	    private String oprInvoiceNo;
		public FinTransId() {
			super();
			// TODO Auto-generated constructor stub
		}
		public FinTransId(String companyId, String branchId, String docType, String ledgerType, BigDecimal lineId,
				String transId, String oprInvoiceNo) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.docType = docType;
			this.ledgerType = ledgerType;
			this.lineId = lineId;
			this.transId = transId;
			this.oprInvoiceNo = oprInvoiceNo;
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
		public String getDocType() {
			return docType;
		}
		public void setDocType(String docType) {
			this.docType = docType;
		}
		public String getLedgerType() {
			return ledgerType;
		}
		public void setLedgerType(String ledgerType) {
			this.ledgerType = ledgerType;
		}
		public BigDecimal getLineId() {
			return lineId;
		}
		public void setLineId(BigDecimal lineId) {
			this.lineId = lineId;
		}
		public String getTransId() {
			return transId;
		}
		public void setTransId(String transId) {
			this.transId = transId;
		}
		public String getOprInvoiceNo() {
			return oprInvoiceNo;
		}
		public void setOprInvoiceNo(String oprInvoiceNo) {
			this.oprInvoiceNo = oprInvoiceNo;
		}
	    
	    
	    
}
