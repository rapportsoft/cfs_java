package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "cfexbondcrgdtl")
@IdClass(CfexBondCrgDtlId.class)
public class CfexBondCrgDtl {
	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Fin_Year", length = 4, nullable = true)
	private String finYear;

	@Id
	@Column(name = "Commodity_Id", length = 10)
	private String cfBondDtlId;

	@Id
	@Column(name = "NOC_Trans_Id", length = 10)
	private String nocTransId;

	@Id
	@Column(name = "Ex_Bonding_Id", length = 10, nullable = false)
	private String exBondingId;

	@Id
	@Column(name = "In_Bonding_Id", length = 10)
	private String inBondingId;

	@Id
	@Column(name = "NOC_No", length = 15)
	private String nocNo;

	@Id
	@Column(name = "BOE_No", length = 20)
	private String boeNo;

	@Column(name = "Bonding_no", length = 108)
	private String bondingNo;

	@Column(name = "Bonding_Date", nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	@Temporal(TemporalType.TIMESTAMP)
	private Date bondingDate;

	@Column(name = "ExBond_BE_No", length = 20)
	private String exBondBeNo;

	@Column(name = "NOC_Packages", precision = 16, scale = 3)
	private BigDecimal nocPackages;

	@Column(name = "Ex_Bonded_Packages", nullable = false, precision = 6, scale = 0)
	private BigDecimal exBondedPackages = BigDecimal.ZERO;

	@Column(name = "ExBond_Type", length = 5)
	private String exBondType;

	@Column(name = "Ex_Bonded_CIF", precision = 12, scale = 3)
	private BigDecimal exBondedCIF;

	@Column(name = "Ex_Bonded_Cargo_Duty", precision = 12, scale = 3)
	private BigDecimal exBondedCargoDuty;

	@Column(name = "Ex_Bonded_Insurance", precision = 12, scale = 3)
	private BigDecimal exBondedInsurance;

	@Column(name = "SB_No", length = 20)
	private String sbNo;

	@Column(name = "Commodity_Description", length = 250)
	private String commodityDescription;

	@Column(name = "Cargo_Type", length = 20)
	private String cargoType;

	@Column(name = "Gross_Weight", precision = 12, scale = 3)
	private BigDecimal grossWeight;

	@Column(name = "Ex_Bonded_GW", precision = 16, scale = 3)
	private BigDecimal exBondedGW;

	@Column(name = "Remaining_GW", precision = 16, scale = 3)
	private BigDecimal remainingGW;

	@Column(name = "BALANCE_GW", precision = 16, scale = 3)
	private BigDecimal balanceGW;

	@Column(name = "TYPE_OF_PACKAGE", length = 15)
	private String typeOfPackage;

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

	@Column(name = "out_qty", precision = 16, scale = 3)
	private BigDecimal outQty = BigDecimal.ZERO;

	@Column(name = "In_Bonded_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal inBondedPackages;

	@Column(name = "Inbond_Gross_Wt", precision = 12, scale = 3)
	private BigDecimal inbondGrossWt;

	@Column(name = "Inbond_Insurance_Value", precision = 15, scale = 3)
	private BigDecimal inbondInsuranceValue;

	@Column(name = "Inbond_CIF_Value", precision = 15, scale = 2)
	private BigDecimal inbondCifValue;

	@Column(name = "Inbond_Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal inbondCargoDuty;
	
	 @Column(name = "yard_location_id", length = 20, nullable = false)
	    private String yardLocationId;


	    @Column(name = "Block_Id", length = 20, nullable = false)
	    private String blockId;


	    @Column(name = "Cell_No_Row", length = 10, nullable = false)
	    private String cellNoRow;
	    
	    @Column(name = "ExBond_Yard_Packages", precision = 16, scale = 3)
		private BigDecimal exBondyardPackages;
	    
	    
	    @Column(name = "ExBond_Grid_Area", precision = 16, scale = 3)
		private BigDecimal exBondGridArea;

	    
	    
	    @Column(name = "IGM_No", length = 10, nullable = true)
	    private String igmNo;

	    @Column(name = "IGM_Line_No", length = 10, nullable = true)
	    private String igmLineNo;

	    
	public String getIgmNo() {
			return igmNo;
		}

		public void setIgmNo(String igmNo) {
			this.igmNo = igmNo;
		}

		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}

	public CfexBondCrgDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getExBondyardPackages() {
		return exBondyardPackages;
	}

	public void setExBondyardPackages(BigDecimal exBondyardPackages) {
		this.exBondyardPackages = exBondyardPackages;
	}

	public BigDecimal getExBondGridArea() {
		return exBondGridArea;
	}

	public void setExBondGridArea(BigDecimal exBondGridArea) {
		this.exBondGridArea = exBondGridArea;
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

	public String getCfBondDtlId() {
		return cfBondDtlId;
	}

	public void setCfBondDtlId(String cfBondDtlId) {
		this.cfBondDtlId = cfBondDtlId;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
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

	public String getExBondBeNo() {
		return exBondBeNo;
	}

	public void setExBondBeNo(String exBondBeNo) {
		this.exBondBeNo = exBondBeNo;
	}

	public BigDecimal getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(BigDecimal nocPackages) {
		this.nocPackages = nocPackages;
	}

	public BigDecimal getExBondedPackages() {
		return exBondedPackages;
	}

	public void setExBondedPackages(BigDecimal exBondedPackages) {
		this.exBondedPackages = exBondedPackages;
	}

	public String getExBondType() {
		return exBondType;
	}

	public void setExBondType(String exBondType) {
		this.exBondType = exBondType;
	}

	public BigDecimal getExBondedCIF() {
		return exBondedCIF;
	}

	public void setExBondedCIF(BigDecimal exBondedCIF) {
		this.exBondedCIF = exBondedCIF;
	}

	public BigDecimal getExBondedCargoDuty() {
		return exBondedCargoDuty;
	}

	public void setExBondedCargoDuty(BigDecimal exBondedCargoDuty) {
		this.exBondedCargoDuty = exBondedCargoDuty;
	}

	public BigDecimal getExBondedInsurance() {
		return exBondedInsurance;
	}

	public void setExBondedInsurance(BigDecimal exBondedInsurance) {
		this.exBondedInsurance = exBondedInsurance;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getExBondedGW() {
		return exBondedGW;
	}

	public void setExBondedGW(BigDecimal exBondedGW) {
		this.exBondedGW = exBondedGW;
	}

	public BigDecimal getRemainingGW() {
		return remainingGW;
	}

	public void setRemainingGW(BigDecimal remainingGW) {
		this.remainingGW = remainingGW;
	}

	public BigDecimal getBalanceGW() {
		return balanceGW;
	}

	public void setBalanceGW(BigDecimal balanceGW) {
		this.balanceGW = balanceGW;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
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

	public BigDecimal getOutQty() {
		return outQty;
	}

	public void setOutQty(BigDecimal outQty) {
		this.outQty = outQty;
	}

	public BigDecimal getInBondedPackages() {
		return inBondedPackages;
	}

	public void setInBondedPackages(BigDecimal inBondedPackages) {
		this.inBondedPackages = inBondedPackages;
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
	
	

	public String getYardLocationId() {
		return yardLocationId;
	}

	public void setYardLocationId(String yardLocationId) {
		this.yardLocationId = yardLocationId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getCellNoRow() {
		return cellNoRow;
	}

	public void setCellNoRow(String cellNoRow) {
		this.cellNoRow = cellNoRow;
	}

	

	


public CfexBondCrgDtl(String companyId, String branchId, String finYear, String cfBondDtlId, String nocTransId,
			String exBondingId, String inBondingId, String nocNo, String boeNo, String bondingNo, Date bondingDate,
			String exBondBeNo, BigDecimal nocPackages, BigDecimal exBondedPackages, String exBondType,
			BigDecimal exBondedCIF, BigDecimal exBondedCargoDuty, BigDecimal exBondedInsurance, String sbNo,
			String commodityDescription, String cargoType, BigDecimal grossWeight, BigDecimal exBondedGW,
			BigDecimal remainingGW, BigDecimal balanceGW, String typeOfPackage, char status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, BigDecimal outQty,
			BigDecimal inBondedPackages, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue,
			BigDecimal inbondCifValue, BigDecimal inbondCargoDuty, String yardLocationId, String blockId,
			String cellNoRow, BigDecimal exBondyardPackages, BigDecimal exBondGridArea, String igmNo,
			String igmLineNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.cfBondDtlId = cfBondDtlId;
		this.nocTransId = nocTransId;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.nocNo = nocNo;
		this.boeNo = boeNo;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.exBondBeNo = exBondBeNo;
		this.nocPackages = nocPackages;
		this.exBondedPackages = exBondedPackages;
		this.exBondType = exBondType;
		this.exBondedCIF = exBondedCIF;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedInsurance = exBondedInsurance;
		this.sbNo = sbNo;
		this.commodityDescription = commodityDescription;
		this.cargoType = cargoType;
		this.grossWeight = grossWeight;
		this.exBondedGW = exBondedGW;
		this.remainingGW = remainingGW;
		this.balanceGW = balanceGW;
		this.typeOfPackage = typeOfPackage;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.outQty = outQty;
		this.inBondedPackages = inBondedPackages;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inbondCifValue = inbondCifValue;
		this.inbondCargoDuty = inbondCargoDuty;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.exBondyardPackages = exBondyardPackages;
		this.exBondGridArea = exBondGridArea;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
	}

	@Override
	public String toString() {
		return "CfexBondCrgDtl [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", cfBondDtlId=" + cfBondDtlId + ", nocTransId=" + nocTransId + ", exBondingId=" + exBondingId
				+ ", inBondingId=" + inBondingId + ", nocNo=" + nocNo + ", boeNo=" + boeNo + ", bondingNo=" + bondingNo
				+ ", bondingDate=" + bondingDate + ", exBondBeNo=" + exBondBeNo + ", nocPackages=" + nocPackages
				+ ", exBondedPackages=" + exBondedPackages + ", exBondType=" + exBondType + ", exBondedCIF="
				+ exBondedCIF + ", exBondedCargoDuty=" + exBondedCargoDuty + ", exBondedInsurance=" + exBondedInsurance
				+ ", sbNo=" + sbNo + ", commodityDescription=" + commodityDescription + ", cargoType=" + cargoType
				+ ", grossWeight=" + grossWeight + ", exBondedGW=" + exBondedGW + ", remainingGW=" + remainingGW
				+ ", balanceGW=" + balanceGW + ", typeOfPackage=" + typeOfPackage + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", outQty=" + outQty + ", inBondedPackages=" + inBondedPackages + ", inbondGrossWt=" + inbondGrossWt
				+ ", inbondInsuranceValue=" + inbondInsuranceValue + ", inbondCifValue=" + inbondCifValue
				+ ", inbondCargoDuty=" + inbondCargoDuty + ", yardLocationId=" + yardLocationId + ", blockId=" + blockId
				+ ", cellNoRow=" + cellNoRow + ", exBondyardPackages=" + exBondyardPackages + ", exBondGridArea="
				+ exBondGridArea + "]";
	}

	public CfexBondCrgDtl(String companyId, String branchId, String finYear, String cfBondDtlId, String nocTransId,
			String exBondingId, String inBondingId, String nocNo, String boeNo, String bondingNo, Date bondingDate,
			String exBondBeNo, BigDecimal nocPackages, BigDecimal exBondedPackages, String exBondType,
			BigDecimal exBondedCIF, BigDecimal exBondedCargoDuty, BigDecimal exBondedInsurance, char status,
			BigDecimal inBondedPackages, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue,
			BigDecimal inbondCifValue, BigDecimal inbondCargoDuty) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.cfBondDtlId = cfBondDtlId;
		this.nocTransId = nocTransId;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.nocNo = nocNo;
		this.boeNo = boeNo;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.exBondBeNo = exBondBeNo;
		this.nocPackages = nocPackages;
		this.exBondedPackages = exBondedPackages;
		this.exBondType = exBondType;
		this.exBondedCIF = exBondedCIF;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedInsurance = exBondedInsurance;
		this.status = status;
		this.inBondedPackages = inBondedPackages;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inbondCifValue = inbondCifValue;
		this.inbondCargoDuty = inbondCargoDuty;
	}

	public CfexBondCrgDtl(char status) {
		super();
		this.status = status;
	}

	

}
