package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class SSRDtlId implements Serializable {
	private String companyId;
	private String branchId;
	private String transId;
	private String erpDocRefNo;
	private String docRefNo;
	private String containerNo;
	private String profitcentreId;
	private String serviceId;
	private BigDecimal transLineNo;
	public SSRDtlId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SSRDtlId(String companyId, String branchId, String transId, String erpDocRefNo, String docRefNo,
			String containerNo, String profitcentreId, String serviceId, BigDecimal transLineNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.transId = transId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
		this.profitcentreId = profitcentreId;
		this.serviceId = serviceId;
		this.transLineNo = transLineNo;
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
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getErpDocRefNo() {
		return erpDocRefNo;
	}
	public void setErpDocRefNo(String erpDocRefNo) {
		this.erpDocRefNo = erpDocRefNo;
	}
	public String getDocRefNo() {
		return docRefNo;
	}
	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	public String getProfitcentreId() {
		return profitcentreId;
	}
	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public BigDecimal getTransLineNo() {
		return transLineNo;
	}
	public void setTransLineNo(BigDecimal transLineNo) {
		this.transLineNo = transLineNo;
	}
	
	
	
}
