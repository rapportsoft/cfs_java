package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfssrdtl_job")
@IdClass(SSRJobDtlId.class)
public class SSRJobDtl {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;
	
	@Id
	@Column(name = "Trans_Id", length = 10)
	private String transId;
		
	@Id
	@Column(name = "Doc_Ref_No", length = 20)
	private String docRefNo;
	
	@Id
	@Column(name = "Container_No", length = 11)
	private String containerNo;
	
	@Id
	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;
	
	@Id
	@Column(name = "Service_Id", length = 10)
	private String serviceId;
	
	@Column(name = "Trans_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
	
    @Column(name = "SSR_REF_No", length = 20)
    private String ssrRefNo;
    
    @Column(name = "Doc_Ref_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docRefDate;
    
    @Column(name = "IGM_Line_No", length = 15)
    private String igmLineNo;
    
    @Column(name = "Container_Size", length = 6)
    private String containerSize;
    
    @Column(name = "SSR_Mode_For", length = 10)
    private String ssrModeFor;
    
    @Column(name = "Srl_No", precision = 3, scale = 0)
    private BigDecimal srlNo;
    
    @Column(name = "Service_Unit", length = 6)
    private String serviceUnit;

    @Column(name = "Execution_Unit", length = 13)
    private String executionUnit;

    @Column(name = "Service_Unit1", length = 6)
    private String serviceUnit1;

    @Column(name = "Execution_Unit1", length = 15)
    private String executionUnit1;
    
    @Column(name = "Rate", precision = 16, scale = 3)
    private BigDecimal rate;

    @Column(name = "TotalRate", precision = 23, scale = 3)
    private BigDecimal totalRate;
    
    @Column(name="SSR_Flag", length = 1)
    private String ssrFlag;
    
    @Column(name = "Status", length = 1)
    private String status;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    
    @Column(name = "Srv_Sr_No", length = 5)
    private String srvSrNo;

    @Column(name = "SSR_Party_Id", length = 15)
    private String ssrPartyId;

	public SSRJobDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SSRJobDtl(String companyId, String branchId, String transId, String docRefNo, String containerNo,
			String profitcentreId, String serviceId, Date transDate, String ssrRefNo, Date docRefDate, String igmLineNo,
			String containerSize, String ssrModeFor, BigDecimal srlNo, String serviceUnit, String executionUnit,
			String serviceUnit1, String executionUnit1, BigDecimal rate, BigDecimal totalRate, String ssrFlag,
			String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String srvSrNo, String ssrPartyId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.transId = transId;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
		this.profitcentreId = profitcentreId;
		this.serviceId = serviceId;
		this.transDate = transDate;
		this.ssrRefNo = ssrRefNo;
		this.docRefDate = docRefDate;
		this.igmLineNo = igmLineNo;
		this.containerSize = containerSize;
		this.ssrModeFor = ssrModeFor;
		this.srlNo = srlNo;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.serviceUnit1 = serviceUnit1;
		this.executionUnit1 = executionUnit1;
		this.rate = rate;
		this.totalRate = totalRate;
		this.ssrFlag = ssrFlag;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.srvSrNo = srvSrNo;
		this.ssrPartyId = ssrPartyId;
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

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getSsrRefNo() {
		return ssrRefNo;
	}

	public void setSsrRefNo(String ssrRefNo) {
		this.ssrRefNo = ssrRefNo;
	}

	public Date getDocRefDate() {
		return docRefDate;
	}

	public void setDocRefDate(Date docRefDate) {
		this.docRefDate = docRefDate;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getSsrModeFor() {
		return ssrModeFor;
	}

	public void setSsrModeFor(String ssrModeFor) {
		this.ssrModeFor = ssrModeFor;
	}

	public BigDecimal getSrlNo() {
		return srlNo;
	}

	public void setSrlNo(BigDecimal srlNo) {
		this.srlNo = srlNo;
	}

	public String getServiceUnit() {
		return serviceUnit;
	}

	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	public String getExecutionUnit() {
		return executionUnit;
	}

	public void setExecutionUnit(String executionUnit) {
		this.executionUnit = executionUnit;
	}

	public String getServiceUnit1() {
		return serviceUnit1;
	}

	public void setServiceUnit1(String serviceUnit1) {
		this.serviceUnit1 = serviceUnit1;
	}

	public String getExecutionUnit1() {
		return executionUnit1;
	}

	public void setExecutionUnit1(String executionUnit1) {
		this.executionUnit1 = executionUnit1;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public String getSsrFlag() {
		return ssrFlag;
	}

	public void setSsrFlag(String ssrFlag) {
		this.ssrFlag = ssrFlag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public Date getEditedDate() {
		return editedDate;
	}

	public void setEditedDate(Date editedDate) {
		this.editedDate = editedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getSrvSrNo() {
		return srvSrNo;
	}

	public void setSrvSrNo(String srvSrNo) {
		this.srvSrNo = srvSrNo;
	}

	public String getSsrPartyId() {
		return ssrPartyId;
	}

	public void setSsrPartyId(String ssrPartyId) {
		this.ssrPartyId = ssrPartyId;
	}

	public SSRJobDtl(String transId, String containerNo) {
		super();
		this.transId = transId;
		this.containerNo = containerNo;
	}
    
	
	
	
    
}
