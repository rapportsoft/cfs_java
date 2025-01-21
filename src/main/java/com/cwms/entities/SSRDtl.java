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
@Table(name = "cfssrdtl")
@IdClass(SSRDtlId.class)
public class SSRDtl {
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
	@Column(name = "Erp_Doc_Ref_No", length = 10)
	private String erpDocRefNo;
	
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
	
	@Id
	@Column(name = "Trans_Line_No", precision = 16, scale = 3)
	private BigDecimal transLineNo;
	
    @Column(name = "Trans_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;

    @Column(name = "SSR_REF_No", length = 20)
    private String ssrRefNo;

    @Column(name = "Doc_Ref_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docRefDate;

    @Column(name = "IGM_Line_No", length = 7)
    private String igmLineNo;

    @Column(name = "BL_No", length = 55)
    private String blNo;

    @Column(name = "BL_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date blDate;

    @Column(name = "BE_No", length = 55)
    private String beNo;

    @Column(name = "BE_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date beDate;

    @Column(name = "Container_Size", length = 6)
    private String containerSize;

    @Column(name = "Container_Type", length = 6)
    private String containerType;

    @Column(name = "No_Of_Packages")
    private int noOfPackages;

    @Column(name = "Cargo_Wt", precision = 16, scale = 3)
    private BigDecimal cargoWt;

    @Column(name = "Gate_Out_Type", length = 10)
    private String gateOutType;

    @Column(name = "Container_Invoice_Type", length = 20)
    private String containerInvoiceType;

    @Column(name = "Commodity_Description", length = 250)
    private String commodityDescription;

    @Column(name = "Imp_Id", length = 6)
    private String impId;

    @Column(name = "SL", length = 6)
    private String sl;

    @Column(name = "SA", length = 6)
    private String sa;

    @Column(name = "CHA", length = 6)
    private String cha;

    @Column(name = "Acc_Id", length = 6)
    private String accId;

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

    @Column(name = "Status", length = 1)
    private char status;

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

    @Column(name = "Assesment_Id", length = 20)
    private String assessmentId;

    @Column(name = "Sr_No", precision = 3, scale = 0)
    private BigDecimal srNo;

    @Column(name = "Back_To_Town_Trans_Id", length = 10)
    private String backToTownTransId;

    @Column(name = "EX_BOND_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exBondDate;

    @Column(name = "SSR_Type", length = 10)
    private String ssrType;

    @Column(name = "NOC_NO", length = 50)
    private String nocNo;

    @Column(name = "IN_BOND_ID", length = 30)
    private String inBondId;

    @Column(name = "EX_BOND_BE_ID", length = 30)
    private String exBondBeId;

    @Column(name = "EX_BOND_BE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exBondBeDate;

    @Column(name = "EX_BOND_ID", length = 30)
    private String exBondId;

    @Column(name = "NOC_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocDate;

    @Column(name = "In_Bond_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inBondDate;

    @Column(name = "Srv_Sr_No", length = 5)
    private String srvSrNo;

    @Column(name = "SSR_Party_Id", length = 15)
    private String ssrPartyId;

	public SSRDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SSRDtl(String companyId, String branchId, String transId, String erpDocRefNo, String docRefNo,
			String containerNo, String profitcentreId, String serviceId, BigDecimal transLineNo, Date transDate,
			String ssrRefNo, Date docRefDate, String igmLineNo, String blNo, Date blDate, String beNo, Date beDate,
			String containerSize, String containerType, int noOfPackages, BigDecimal cargoWt, String gateOutType,
			String containerInvoiceType, String commodityDescription, String impId, String sl, String sa, String cha,
			String accId, String ssrModeFor, BigDecimal srlNo, String serviceUnit, String executionUnit,
			String serviceUnit1, String executionUnit1, BigDecimal rate, BigDecimal totalRate, char status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String assessmentId, BigDecimal srNo, String backToTownTransId, Date exBondDate, String ssrType,
			String nocNo, String inBondId, String exBondBeId, Date exBondBeDate, String exBondId, Date nocDate,
			Date inBondDate, String srvSrNo, String ssrPartyId) {
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
		this.transDate = transDate;
		this.ssrRefNo = ssrRefNo;
		this.docRefDate = docRefDate;
		this.igmLineNo = igmLineNo;
		this.blNo = blNo;
		this.blDate = blDate;
		this.beNo = beNo;
		this.beDate = beDate;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.noOfPackages = noOfPackages;
		this.cargoWt = cargoWt;
		this.gateOutType = gateOutType;
		this.containerInvoiceType = containerInvoiceType;
		this.commodityDescription = commodityDescription;
		this.impId = impId;
		this.sl = sl;
		this.sa = sa;
		this.cha = cha;
		this.accId = accId;
		this.ssrModeFor = ssrModeFor;
		this.srlNo = srlNo;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.serviceUnit1 = serviceUnit1;
		this.executionUnit1 = executionUnit1;
		this.rate = rate;
		this.totalRate = totalRate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.assessmentId = assessmentId;
		this.srNo = srNo;
		this.backToTownTransId = backToTownTransId;
		this.exBondDate = exBondDate;
		this.ssrType = ssrType;
		this.nocNo = nocNo;
		this.inBondId = inBondId;
		this.exBondBeId = exBondBeId;
		this.exBondBeDate = exBondBeDate;
		this.exBondId = exBondId;
		this.nocDate = nocDate;
		this.inBondDate = inBondDate;
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

	public String getBlNo() {
		return blNo;
	}

	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}

	public Date getBlDate() {
		return blDate;
	}

	public void setBlDate(Date blDate) {
		this.blDate = blDate;
	}

	public String getBeNo() {
		return beNo;
	}

	public void setBeNo(String beNo) {
		this.beNo = beNo;
	}

	public Date getBeDate() {
		return beDate;
	}

	public void setBeDate(Date beDate) {
		this.beDate = beDate;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public int getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(int noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public BigDecimal getCargoWt() {
		return cargoWt;
	}

	public void setCargoWt(BigDecimal cargoWt) {
		this.cargoWt = cargoWt;
	}

	public String getGateOutType() {
		return gateOutType;
	}

	public void setGateOutType(String gateOutType) {
		this.gateOutType = gateOutType;
	}

	public String getContainerInvoiceType() {
		return containerInvoiceType;
	}

	public void setContainerInvoiceType(String containerInvoiceType) {
		this.containerInvoiceType = containerInvoiceType;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getImpId() {
		return impId;
	}

	public void setImpId(String impId) {
		this.impId = impId;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getSa() {
		return sa;
	}

	public void setSa(String sa) {
		this.sa = sa;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
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

	public String getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
	}

	public BigDecimal getSrNo() {
		return srNo;
	}

	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}

	public String getBackToTownTransId() {
		return backToTownTransId;
	}

	public void setBackToTownTransId(String backToTownTransId) {
		this.backToTownTransId = backToTownTransId;
	}

	public Date getExBondDate() {
		return exBondDate;
	}

	public void setExBondDate(Date exBondDate) {
		this.exBondDate = exBondDate;
	}

	public String getSsrType() {
		return ssrType;
	}

	public void setSsrType(String ssrType) {
		this.ssrType = ssrType;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getInBondId() {
		return inBondId;
	}

	public void setInBondId(String inBondId) {
		this.inBondId = inBondId;
	}

	public String getExBondBeId() {
		return exBondBeId;
	}

	public void setExBondBeId(String exBondBeId) {
		this.exBondBeId = exBondBeId;
	}

	public Date getExBondBeDate() {
		return exBondBeDate;
	}

	public void setExBondBeDate(Date exBondBeDate) {
		this.exBondBeDate = exBondBeDate;
	}

	public String getExBondId() {
		return exBondId;
	}

	public void setExBondId(String exBondId) {
		this.exBondId = exBondId;
	}

	public Date getNocDate() {
		return nocDate;
	}

	public void setNocDate(Date nocDate) {
		this.nocDate = nocDate;
	}

	public Date getInBondDate() {
		return inBondDate;
	}

	public void setInBondDate(Date inBondDate) {
		this.inBondDate = inBondDate;
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

	public SSRDtl(String serviceId, String createdBy, String ssrRefNo, String serviceUnit, String executionUnit,
			BigDecimal totalRate) {
		super();
		this.serviceId = serviceId;
		this.createdBy = createdBy;
		this.ssrRefNo = ssrRefNo;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.totalRate = totalRate;
	}

	public SSRDtl(String companyId, String branchId, String transId, String erpDocRefNo, String docRefNo,
			Date transDate, Date docRefDate, String igmLineNo, String blNo, Date blDate, String beNo, Date beDate,
			String commodityDescription, String sl, String sa, String cha, String accId, String ssrModeFor, char status,
			String createdBy,String impId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.transId = transId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.transDate = transDate;
		this.docRefDate = docRefDate;
		this.igmLineNo = igmLineNo;
		this.blNo = blNo;
		this.blDate = blDate;
		this.beNo = beNo;
		this.beDate = beDate;
		this.commodityDescription = commodityDescription;
		this.sl = sl;
		this.sa = sa;
		this.cha = cha;
		this.accId = accId;
		this.ssrModeFor = ssrModeFor;
		this.status = status;
		this.createdBy = createdBy;
		this.impId = impId;
	}
    
	public SSRDtl(String containerNo, String serviceId, String serviceUnit, String executionUnit,String serviceUnit1, String executionUnit1, BigDecimal rate,
			BigDecimal totalRate) {
		this.containerNo = containerNo;
		this.serviceId = serviceId;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.serviceUnit1 = serviceUnit1;
		this.executionUnit1 = executionUnit1;
		this.rate = rate;
		this.totalRate = totalRate;
	}
	
    
	
}
