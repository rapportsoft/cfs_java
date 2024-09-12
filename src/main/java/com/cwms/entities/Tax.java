package com.cwms.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "tax")
@IdClass(TaxId.class)
public class Tax {

    @Id
    @Column(name = "Company_Id", length = 10, nullable = false)
    private String companyId;

    @Column(name = "Branch_Id", length = 10, nullable = false)
    private String branchId;

    @Id
    @Column(name = "Tax_Id", length = 6, nullable = false)
    private String taxId;

    @Id
    @Column(name = "Tax_Type", length = 6, nullable = false)
    private String taxType;

    @Id
    @Column(name = "TDS_Type", length = 6, nullable = false)
    private String tdsType;

    @Id
    @Column(name = "TDS_Status", length = 6, nullable = false)
    private String tdsStatus;

    @Column(name = "Tax_Desc", length = 50)
    private String taxDesc;

    @Column(name = "Tax_Section", length = 5)
    private String taxSection;

    @Column(name = "TDS_No_Less_Deduction", length = 1, nullable = false)
    private char tdsNoLessDeduction;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Column(name = "Status", length = 1, nullable = false)
    private char status;

	public Tax() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tax(String companyId, String branchId, String taxId, String taxType, String tdsType, String tdsStatus,
			String taxDesc, String taxSection, char tdsNoLessDeduction, String createdBy, Date createdDate,
			String approvedBy, Date approvedDate, char status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.taxId = taxId;
		this.taxType = taxType;
		this.tdsType = tdsType;
		this.tdsStatus = tdsStatus;
		this.taxDesc = taxDesc;
		this.taxSection = taxSection;
		this.tdsNoLessDeduction = tdsNoLessDeduction;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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

	public String getTdsType() {
		return tdsType;
	}

	public void setTdsType(String tdsType) {
		this.tdsType = tdsType;
	}

	public String getTdsStatus() {
		return tdsStatus;
	}

	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}

	public String getTaxDesc() {
		return taxDesc;
	}

	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}

	public String getTaxSection() {
		return taxSection;
	}

	public void setTaxSection(String taxSection) {
		this.taxSection = taxSection;
	}

	public char getTdsNoLessDeduction() {
		return tdsNoLessDeduction;
	}

	public void setTdsNoLessDeduction(char tdsNoLessDeduction) {
		this.tdsNoLessDeduction = tdsNoLessDeduction;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Tax [companyId=" + companyId + ", branchId=" + branchId + ", taxId=" + taxId + ", taxType=" + taxType
				+ ", tdsType=" + tdsType + ", tdsStatus=" + tdsStatus + ", taxDesc=" + taxDesc + ", taxSection="
				+ taxSection + ", tdsNoLessDeduction=" + tdsNoLessDeduction + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", status=" + status + "]";
	}

    // Getters and Setters
    
    
    
    
    
    
}
