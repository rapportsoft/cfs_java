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
@Table(name="taxdtl")
@IdClass(TaxDtlId.class)
public class TaxDtl {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Period_From")
	@Temporal(TemporalType.TIMESTAMP)
	private Date periodFrom;
	
	@Id
	@Column(name="Tax_Id",length = 6)
	private String taxId;
	
	@Id
	@Column(name="Tax_Type",length = 6)
	private String taxType;
	
	@Id
	@Column(name="TDS_Status",length = 6)
	private String tdsStatus;
	
	@Id
	@Column(name="TDS_Type",length = 6)
	private String tdsType;
	
	@Column(name="Period_To")
	@Temporal(TemporalType.TIMESTAMP)
	private Date periodTo;
	
	@Column(name="Ac_Code",length = 10)
	private String acCode;
	
	@Column(name="Basic_Tax",precision = 18,scale = 3)
	private BigDecimal basicTax = BigDecimal.ZERO;
	
	@Column(name="Tax_Perc",precision = 18,scale = 3)
	private BigDecimal taxPerc = BigDecimal.ZERO;
	
	@Column(name="Surcharge_Perc",precision = 18,scale = 3)
	private BigDecimal surchargePerc = BigDecimal.ZERO;
	
	@Column(name="Educational_Cess_Perc",precision = 18,scale = 3)
	private BigDecimal educationalCessPerc = BigDecimal.ZERO;
	
	@Column(name="Interest_Perc",precision = 18,scale = 3)
	private BigDecimal interestPerc = BigDecimal.ZERO;
	
	@Column(name="Other_Perc",precision = 18,scale = 3)
	private BigDecimal otherPerc = BigDecimal.ZERO;
	
    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Created_Date")
    private Date createdDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Approved_Date")
    private Date approvedDate;
    
    @Column(name="Status",length = 1)
    private String status;

	public TaxDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TaxDtl(String companyId, Date periodFrom, String taxId, String taxType, String tdsStatus, String tdsType,
			Date periodTo, String acCode, BigDecimal basicTax, BigDecimal taxPerc, BigDecimal surchargePerc,
			BigDecimal educationalCessPerc, BigDecimal interestPerc, BigDecimal otherPerc, String createdBy,
			Date createdDate, String approvedBy, Date approvedDate, String status) {
		this.companyId = companyId;
		this.periodFrom = periodFrom;
		this.taxId = taxId;
		this.taxType = taxType;
		this.tdsStatus = tdsStatus;
		this.tdsType = tdsType;
		this.periodTo = periodTo;
		this.acCode = acCode;
		this.basicTax = basicTax;
		this.taxPerc = taxPerc;
		this.surchargePerc = surchargePerc;
		this.educationalCessPerc = educationalCessPerc;
		this.interestPerc = interestPerc;
		this.otherPerc = otherPerc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getTaxType() {
		return taxType;
	}

	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}

	public String getTdsStatus() {
		return tdsStatus;
	}

	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}

	public String getTdsType() {
		return tdsType;
	}

	public void setTdsType(String tdsType) {
		this.tdsType = tdsType;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public String getAcCode() {
		return acCode;
	}

	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}

	public BigDecimal getBasicTax() {
		return basicTax;
	}

	public void setBasicTax(BigDecimal basicTax) {
		this.basicTax = basicTax;
	}

	public BigDecimal getTaxPerc() {
		return taxPerc;
	}

	public void setTaxPerc(BigDecimal taxPerc) {
		this.taxPerc = taxPerc;
	}

	public BigDecimal getSurchargePerc() {
		return surchargePerc;
	}

	public void setSurchargePerc(BigDecimal surchargePerc) {
		this.surchargePerc = surchargePerc;
	}

	public BigDecimal getEducationalCessPerc() {
		return educationalCessPerc;
	}

	public void setEducationalCessPerc(BigDecimal educationalCessPerc) {
		this.educationalCessPerc = educationalCessPerc;
	}

	public BigDecimal getInterestPerc() {
		return interestPerc;
	}

	public void setInterestPerc(BigDecimal interestPerc) {
		this.interestPerc = interestPerc;
	}

	public BigDecimal getOtherPerc() {
		return otherPerc;
	}

	public void setOtherPerc(BigDecimal otherPerc) {
		this.otherPerc = otherPerc;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
    
}
