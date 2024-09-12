package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "cfinbondcrgHDRDtl")
@IdClass(CfinbondcrgHDRDtlId.class)
public class CfinbondcrgHDRDtl {

	@Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Id
    @Column(name = "Fin_Year", length = 4, nullable = true)
    private String finYear;

    
    @Id
    @Column(name = "In_Bonding_Id", length = 10, nullable = true)
    private String inBondingId;

    @Id
    @Column(name = "In_Bonding_DTL_Id", length = 10, nullable = true)
    private String inBondingDtlId;
    
    @Column(name = "In_Bonding_Date", nullable = true)
    private Date inBondingDate;

    @Id
    @Column(name = "NOC_Trans_Id", length = 10, nullable = true)
    private String nocTransId;

    @Column(name = "NOC_Trans_Date", nullable = true)
    private Date nocTransDate;

    @Id
    @Column(name = "NOC_No", length = 25, nullable = true)
    private String nocNo;
    
    @Id
	@Column(name = "Cfbond_Detail_Id", length = 6, nullable = true)
	private String cfBondDtlId;
    
    @Id
	@Column(name = "BOE_No", length = 15, nullable = true)
	private String boeNo;
    
  
    @Column(name = "NOC_Date", nullable = true)
    private Date nocDate;

    @Column(name = "Bonding_no", length = 25)
    private String bondingNo;

    @Column(name = "Bonding_Date", nullable = true)
    private Date bondingDate;

   
    @Column(name = "NOC_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal nocPackages;

	@Column(name = "CIF_Value", precision = 15, scale = 2)
	private BigDecimal cifValue;

	@Column(name = "Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal cargoDuty;

	@Column(name = "Insurance_Value", precision = 15, scale = 2)
	private BigDecimal insuranceValue;

	@Column(name = "TYPE_OF_PACKAGE", length = 15)
	private String typeOfPackage;

	@Column(name = "Commodity_Description", length = 250, nullable = true)
	private String commodityDescription;


	@Column(name = "Gross_Weight", precision = 16, scale = 3, nullable = true)
	private BigDecimal grossWeight;
	

	
   
    @Column(name = "In_Bonded_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal inBondedPackages;

    @Column(name = "Ex_Bonded_Packages", precision = 8, scale = 0)
    private BigDecimal exBondedPackages;

    @Column(name = "To_Bonded_Packages", precision = 10, scale = 0, nullable = true)
    private BigDecimal toBondedPackages;

   
    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

 

    @Column(name = "Inbond_Gross_Wt", precision = 12, scale = 3)
    private BigDecimal inbondGrossWt;

    @Column(name = "Inbond_Insurance_Value", precision = 15, scale = 3)
    private BigDecimal inbondInsuranceValue;

    @Column(name = "Inbond_CIF_Value", precision = 15, scale = 2)
	private BigDecimal inbondCifValue;

	@Column(name = "Inbond_Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal inbondCargoDuty;
   
    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date", nullable = true)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    private Date approvedDate;

    
    @Column(name = "Shortage_Packages")
	private BigDecimal shortagePackages;

	@Column(name = "Damaged_Qty")
	private BigDecimal damagedQty;

	@Column(name = "Breakage")
	private BigDecimal breakage;
	
	 @Column(name = "Area_Occupied", precision = 5, scale = 0, nullable = true)
	    private BigDecimal areaOccupied;

	public CfinbondcrgHDRDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
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

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public String getInBondingDtlId() {
		return inBondingDtlId;
	}

	public void setInBondingDtlId(String inBondingDtlId) {
		this.inBondingDtlId = inBondingDtlId;
	}

	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public Date getNocTransDate() {
		return nocTransDate;
	}

	public void setNocTransDate(Date nocTransDate) {
		this.nocTransDate = nocTransDate;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getCfBondDtlId() {
		return cfBondDtlId;
	}

	public void setCfBondDtlId(String cfBondDtlId) {
		this.cfBondDtlId = cfBondDtlId;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public Date getNocDate() {
		return nocDate;
	}

	public void setNocDate(Date nocDate) {
		this.nocDate = nocDate;
	}

	public String getBondingNo() {
		return bondingNo;
	}

	public void setBondingNo(String bondingNo) {
		this.bondingNo = bondingNo;
	}

	public Date getBondingDate() {
		return bondingDate;
	}

	public void setBondingDate(Date bondingDate) {
		this.bondingDate = bondingDate;
	}

	public BigDecimal getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(BigDecimal nocPackages) {
		this.nocPackages = nocPackages;
	}

	public BigDecimal getCifValue() {
		return cifValue;
	}

	public void setCifValue(BigDecimal cifValue) {
		this.cifValue = cifValue;
	}

	public BigDecimal getCargoDuty() {
		return cargoDuty;
	}

	public void setCargoDuty(BigDecimal cargoDuty) {
		this.cargoDuty = cargoDuty;
	}

	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}

	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getInBondedPackages() {
		return inBondedPackages;
	}

	public void setInBondedPackages(BigDecimal inBondedPackages) {
		this.inBondedPackages = inBondedPackages;
	}

	public BigDecimal getExBondedPackages() {
		return exBondedPackages;
	}

	public void setExBondedPackages(BigDecimal exBondedPackages) {
		this.exBondedPackages = exBondedPackages;
	}

	public BigDecimal getToBondedPackages() {
		return toBondedPackages;
	}

	public void setToBondedPackages(BigDecimal toBondedPackages) {
		this.toBondedPackages = toBondedPackages;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getInbondGrossWt() {
		return inbondGrossWt;
	}

	public void setInbondGrossWt(BigDecimal inbondGrossWt) {
		this.inbondGrossWt = inbondGrossWt;
	}

	public BigDecimal getInbondInsuranceValue() {
		return inbondInsuranceValue;
	}

	public void setInbondInsuranceValue(BigDecimal inbondInsuranceValue) {
		this.inbondInsuranceValue = inbondInsuranceValue;
	}

	public BigDecimal getInbondCifValue() {
		return inbondCifValue;
	}

	public void setInbondCifValue(BigDecimal inbondCifValue) {
		this.inbondCifValue = inbondCifValue;
	}

	public BigDecimal getInbondCargoDuty() {
		return inbondCargoDuty;
	}

	public void setInbondCargoDuty(BigDecimal inbondCargoDuty) {
		this.inbondCargoDuty = inbondCargoDuty;
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

	public BigDecimal getShortagePackages() {
		return shortagePackages;
	}

	public void setShortagePackages(BigDecimal shortagePackages) {
		this.shortagePackages = shortagePackages;
	}

	public BigDecimal getDamagedQty() {
		return damagedQty;
	}

	public void setDamagedQty(BigDecimal damagedQty) {
		this.damagedQty = damagedQty;
	}

	public BigDecimal getBreakage() {
		return breakage;
	}

	public void setBreakage(BigDecimal breakage) {
		this.breakage = breakage;
	}

	public CfinbondcrgHDRDtl(String companyId, String branchId, String finYear, String inBondingId,
			String inBondingDtlId, Date inBondingDate, String nocTransId, Date nocTransDate, String nocNo,
			String cfBondDtlId, String boeNo, Date nocDate, String bondingNo, Date bondingDate, BigDecimal nocPackages,
			BigDecimal cifValue, BigDecimal cargoDuty, BigDecimal insuranceValue, String typeOfPackage,
			String commodityDescription, BigDecimal grossWeight, BigDecimal inBondedPackages,
			BigDecimal exBondedPackages, BigDecimal toBondedPackages, String comments, BigDecimal inbondGrossWt,
			BigDecimal inbondInsuranceValue, BigDecimal inbondCifValue, BigDecimal inbondCargoDuty, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			BigDecimal shortagePackages, BigDecimal damagedQty, BigDecimal breakage, BigDecimal areaOccupied) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingId = inBondingId;
		this.inBondingDtlId = inBondingDtlId;
		this.inBondingDate = inBondingDate;
		this.nocTransId = nocTransId;
		this.nocTransDate = nocTransDate;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
		this.nocDate = nocDate;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.nocPackages = nocPackages;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.inBondedPackages = inBondedPackages;
		this.exBondedPackages = exBondedPackages;
		this.toBondedPackages = toBondedPackages;
		this.comments = comments;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inbondCifValue = inbondCifValue;
		this.inbondCargoDuty = inbondCargoDuty;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.shortagePackages = shortagePackages;
		this.damagedQty = damagedQty;
		this.breakage = breakage;
		this.areaOccupied = areaOccupied;
	}

	@Override
	public String toString() {
		return "CfinbondcrgHDRDtl [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", inBondingId=" + inBondingId + ", inBondingDtlId=" + inBondingDtlId + ", inBondingDate="
				+ inBondingDate + ", nocTransId=" + nocTransId + ", nocTransDate=" + nocTransDate + ", nocNo=" + nocNo
				+ ", cfBondDtlId=" + cfBondDtlId + ", boeNo=" + boeNo + ", nocDate=" + nocDate + ", bondingNo="
				+ bondingNo + ", bondingDate=" + bondingDate + ", nocPackages=" + nocPackages + ", cifValue=" + cifValue
				+ ", cargoDuty=" + cargoDuty + ", insuranceValue=" + insuranceValue + ", typeOfPackage=" + typeOfPackage
				+ ", commodityDescription=" + commodityDescription + ", grossWeight=" + grossWeight
				+ ", inBondedPackages=" + inBondedPackages + ", exBondedPackages=" + exBondedPackages
				+ ", toBondedPackages=" + toBondedPackages + ", comments=" + comments + ", inbondGrossWt="
				+ inbondGrossWt + ", inbondInsuranceValue=" + inbondInsuranceValue + ", inbondCifValue="
				+ inbondCifValue + ", inbondCargoDuty=" + inbondCargoDuty + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", shortagePackages="
				+ shortagePackages + ", damagedQty=" + damagedQty + ", breakage=" + breakage + ", areaOccupied="
				+ areaOccupied + "]";
	}

	
	
	
	
	
	
}
