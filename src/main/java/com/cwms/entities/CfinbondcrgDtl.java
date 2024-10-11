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
@Table(name = "cfinbondcrgDtl")
@IdClass(CfinbondcrgDtlId.class)
public class CfinbondcrgDtl {

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
    @Column(name = "In_Bonding_DTL_Id", length = 10, nullable = true)
    private String inBondingDtlId;
    
    @Id
    @Column(name = "In_Bonding_Id", length = 10, nullable = true)
    private String inBondingId;

    @Id
    @Column(name = "NOC_Trans_Id", length = 10, nullable = true)
    private String nocTransId;

    @Id
    @Column(name = "NOC_No", length = 25, nullable = true)
    private String nocNo;
    
    @Id
	@Column(name = "Cfbond_Detail_Id", length = 6, nullable = true)
	private String cfBondDtlId;
    
    @Id
	@Column(name = "BOE_No", length = 15, nullable = true)
	private String boeNo;
    
    @Column(name = "In_Bonding_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inBondingDate;

    
    @Column(name = "NOC_Trans_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocTransDate;
  
    @Column(name = "NOC_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocDate;

    @Column(name = "Bonding_no", length = 25)
    private String bondingNo;

    @Column(name = "Bonding_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bondingDate;

   
    @Column(name = "NOC_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal nocPackages;

	@Column(name = "CIF_Value", precision = 15, scale = 2)
	private BigDecimal cifValue;

	@Column(name = "Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal cargoDuty;

	@Column(name = "Insurance_Value", precision = 15, scale = 2)
	private BigDecimal insuranceValue;
	
	
	@Column(name = "Gross_Weight", precision = 16, scale = 3, nullable = true)
	private BigDecimal grossWeight;
	

	@Column(name = "TYPE_OF_PACKAGE", length = 15)
	private String typeOfPackage;

	@Column(name = "Commodity_Description", length = 250, nullable = true)
	private String commodityDescription;


	

	
 
    @Column(name = "Ex_Bonded_Packages", precision = 8, scale = 0)
    private BigDecimal exBondedPackages;

    @Column(name = "To_Bonded_Packages", precision = 10, scale = 0, nullable = true)
    private BigDecimal toBondedPackages;

   
    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

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
   
    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    
    private String editedBy;

    @Column(name = "Edited_Date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    
    @Column(name = "Shortage_Packages")
	private BigDecimal shortagePackages;

	@Column(name = "Damaged_Qty")
	private BigDecimal damagedQty;

	@Column(name = "Breakage")
	private BigDecimal breakage;

	
    @Column(name = "yard_location_id", length = 20, nullable = false)
    private String yardLocationId;


    @Column(name = "Block_Id", length = 20, nullable = false)
    private String blockId;


    @Column(name = "Cell_No_Row", length = 10, nullable = false)
    private String cellNoRow;
    
    @Column(name = "Area_Occupied", precision = 5, scale = 0, nullable = true)
    private BigDecimal areaOccupied;
    
    
    @Column(name = "Ex_Bonded_CIF", precision = 12, scale = 3)
	private BigDecimal exBondedCIF;

	@Column(name = "Ex_Bonded_Cargo_Duty", precision = 12, scale = 3)
	private BigDecimal exBondedCargoDuty;

	@Column(name = "Ex_Bonded_Insurance", precision = 12, scale = 3)
	private BigDecimal exBondedInsurance;

	@Column(name = "Ex_Bonded_GW", precision = 16, scale = 3)
	private BigDecimal exBondedGW;
	
	
	@Column(name = "Yard_Packages", precision = 16, scale = 3)
	private BigDecimal yardPackages;
	
	
	@Column(name = "Cell_Area_Allocated", precision = 16, scale = 3)
	private BigDecimal cellAreaAllocated;
	
	@Column(name = "Cell_Area", precision = 8, scale = 3)
    private BigDecimal cellArea;
	
	public CfinbondcrgDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public BigDecimal getCellArea() {
		return cellArea;
	}


	public void setCellArea(BigDecimal cellArea) {
		this.cellArea = cellArea;
	}


	public BigDecimal getCellAreaAllocated() {
		return cellAreaAllocated;
	}


	public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
		this.cellAreaAllocated = cellAreaAllocated;
	}


	public BigDecimal getYardPackages() {
		return yardPackages;
	}


	public void setYardPackages(BigDecimal yardPackages) {
		this.yardPackages = yardPackages;
	}


	public String getYardLocationId() {
		return yardLocationId;
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


	public BigDecimal getExBondedGW() {
		return exBondedGW;
	}


	public void setExBondedGW(BigDecimal exBondedGW) {
		this.exBondedGW = exBondedGW;
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

	public String getInBondingDtlId() {
		return inBondingDtlId;
	}

	public void setInBondingDtlId(String inBondingDtlId) {
		this.inBondingDtlId = inBondingDtlId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
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

	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
	}

	public Date getNocTransDate() {
		return nocTransDate;
	}

	public void setNocTransDate(Date nocTransDate) {
		this.nocTransDate = nocTransDate;
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

	

	


	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}


	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
	}

	

	
	
	


	
	
	
//	public CfinbondcrgDtl(String companyId, String branchId, String finYear, String inBondingDtlId, String inBondingId,
//			String nocTransId, String nocNo, String cfBondDtlId, String boeNo, Date inBondingDate, Date nocTransDate,
//			Date nocDate, String bondingNo, Date bondingDate, BigDecimal nocPackages, BigDecimal cifValue,
//			BigDecimal cargoDuty, BigDecimal insuranceValue, BigDecimal grossWeight, String typeOfPackage,
//			String commodityDescription, BigDecimal exBondedPackages, BigDecimal toBondedPackages, String comments,
//			BigDecimal inBondedPackages, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue,
//			BigDecimal inbondCifValue, BigDecimal inbondCargoDuty, String status, String createdBy, Date createdDate,
//			String editedBy, Date editedDate, String approvedBy, Date approvedDate, BigDecimal shortagePackages,
//			BigDecimal damagedQty, BigDecimal breakage, String yardLocationId, String blockId, String cellNoRow,
//			BigDecimal areaOccupied, BigDecimal exBondedCIF, BigDecimal exBondedCargoDuty, BigDecimal exBondedInsurance,
//			BigDecimal exBondedGW) {
//		super();
//		this.companyId = companyId;
//		this.branchId = branchId;
//		this.finYear = finYear;
//		this.inBondingDtlId = inBondingDtlId;
//		this.inBondingId = inBondingId;
//		this.nocTransId = nocTransId;
//		this.nocNo = nocNo;
//		this.cfBondDtlId = cfBondDtlId;
//		this.boeNo = boeNo;
//		this.inBondingDate = inBondingDate;
//		this.nocTransDate = nocTransDate;
//		this.nocDate = nocDate;
//		this.bondingNo = bondingNo;
//		this.bondingDate = bondingDate;
//		this.nocPackages = nocPackages;
//		this.cifValue = cifValue;
//		this.cargoDuty = cargoDuty;
//		this.insuranceValue = insuranceValue;
//		this.grossWeight = grossWeight;
//		this.typeOfPackage = typeOfPackage;
//		this.commodityDescription = commodityDescription;
//		this.exBondedPackages = exBondedPackages;
//		this.toBondedPackages = toBondedPackages;
//		this.comments = comments;
//		this.inBondedPackages = inBondedPackages;
//		this.inbondGrossWt = inbondGrossWt;
//		this.inbondInsuranceValue = inbondInsuranceValue;
//		this.inbondCifValue = inbondCifValue;
//		this.inbondCargoDuty = inbondCargoDuty;
//		this.status = status;
//		this.createdBy = createdBy;
//		this.createdDate = createdDate;
//		this.editedBy = editedBy;
//		this.editedDate = editedDate;
//		this.approvedBy = approvedBy;
//		this.approvedDate = approvedDate;
//		this.shortagePackages = shortagePackages;
//		this.damagedQty = damagedQty;
//		this.breakage = breakage;
//		this.yardLocationId = yardLocationId;
//		this.blockId = blockId;
//		this.cellNoRow = cellNoRow;
//		this.areaOccupied = areaOccupied;
//		this.exBondedCIF = exBondedCIF;
//		this.exBondedCargoDuty = exBondedCargoDuty;
//		this.exBondedInsurance = exBondedInsurance;
//		this.exBondedGW = exBondedGW;
//	}
	
	


	







	// Query for exbond 
	public CfinbondcrgDtl(String companyId, String branchId, String finYear, String inBondingDtlId, String inBondingId,
			String nocTransId, String nocNo, String cfBondDtlId, String boeNo, Date inBondingDate, Date nocTransDate,
			Date nocDate, String bondingNo, Date bondingDate, BigDecimal nocPackages, String typeOfPackage,
			String commodityDescription, BigDecimal exBondedPackages, BigDecimal inBondedPackages,
			BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue, BigDecimal inbondCifValue,
			BigDecimal inbondCargoDuty, String status, String createdBy, String editedBy, String approvedBy,
			String yardLocationId, String blockId, String cellNoRow, BigDecimal areaOccupied,BigDecimal exBondedCIF,
			BigDecimal exBondedCargoDuty, BigDecimal exBondedInsurance,BigDecimal exBondedGW) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingDtlId = inBondingDtlId;
		this.inBondingId = inBondingId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
		this.inBondingDate = inBondingDate;
		this.nocTransDate = nocTransDate;
		this.nocDate = nocDate;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.nocPackages = nocPackages;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.exBondedPackages = exBondedPackages;
		this.inBondedPackages = inBondedPackages;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inbondCifValue = inbondCifValue;
		this.inbondCargoDuty = inbondCargoDuty;
		this.status = status;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.areaOccupied = areaOccupied;
		this.exBondedCIF = exBondedCIF;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedGW = exBondedGW;
	}


public CfinbondcrgDtl(String companyId, String branchId, String finYear, String inBondingDtlId, String inBondingId,
		String nocTransId, String nocNo, String cfBondDtlId, String boeNo, Date inBondingDate, Date nocTransDate,
		Date nocDate, String bondingNo, Date bondingDate, BigDecimal nocPackages, BigDecimal cifValue,
		BigDecimal cargoDuty, BigDecimal insuranceValue, BigDecimal grossWeight, String typeOfPackage,
		String commodityDescription, BigDecimal exBondedPackages, BigDecimal toBondedPackages, String comments,
		BigDecimal inBondedPackages, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue,
		BigDecimal inbondCifValue, BigDecimal inbondCargoDuty, String status, String createdBy, Date createdDate,
		String editedBy, Date editedDate, String approvedBy, Date approvedDate, BigDecimal shortagePackages,
		BigDecimal damagedQty, BigDecimal breakage, String yardLocationId, String blockId, String cellNoRow,
		BigDecimal areaOccupied, BigDecimal exBondedCIF, BigDecimal exBondedCargoDuty, BigDecimal exBondedInsurance,
		BigDecimal exBondedGW, BigDecimal yardPackages, BigDecimal cellAreaAllocated, BigDecimal cellArea) {
	super();
	this.companyId = companyId;
	this.branchId = branchId;
	this.finYear = finYear;
	this.inBondingDtlId = inBondingDtlId;
	this.inBondingId = inBondingId;
	this.nocTransId = nocTransId;
	this.nocNo = nocNo;
	this.cfBondDtlId = cfBondDtlId;
	this.boeNo = boeNo;
	this.inBondingDate = inBondingDate;
	this.nocTransDate = nocTransDate;
	this.nocDate = nocDate;
	this.bondingNo = bondingNo;
	this.bondingDate = bondingDate;
	this.nocPackages = nocPackages;
	this.cifValue = cifValue;
	this.cargoDuty = cargoDuty;
	this.insuranceValue = insuranceValue;
	this.grossWeight = grossWeight;
	this.typeOfPackage = typeOfPackage;
	this.commodityDescription = commodityDescription;
	this.exBondedPackages = exBondedPackages;
	this.toBondedPackages = toBondedPackages;
	this.comments = comments;
	this.inBondedPackages = inBondedPackages;
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
	this.yardLocationId = yardLocationId;
	this.blockId = blockId;
	this.cellNoRow = cellNoRow;
	this.areaOccupied = areaOccupied;
	this.exBondedCIF = exBondedCIF;
	this.exBondedCargoDuty = exBondedCargoDuty;
	this.exBondedInsurance = exBondedInsurance;
	this.exBondedGW = exBondedGW;
	this.yardPackages = yardPackages;
	this.cellAreaAllocated = cellAreaAllocated;
	this.cellArea = cellArea;
}


@Override
public String toString() {
	return "CfinbondcrgDtl [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
			+ ", inBondingDtlId=" + inBondingDtlId + ", inBondingId=" + inBondingId + ", nocTransId=" + nocTransId
			+ ", nocNo=" + nocNo + ", cfBondDtlId=" + cfBondDtlId + ", boeNo=" + boeNo + ", inBondingDate="
			+ inBondingDate + ", nocTransDate=" + nocTransDate + ", nocDate=" + nocDate + ", bondingNo=" + bondingNo
			+ ", bondingDate=" + bondingDate + ", nocPackages=" + nocPackages + ", cifValue=" + cifValue
			+ ", cargoDuty=" + cargoDuty + ", insuranceValue=" + insuranceValue + ", grossWeight=" + grossWeight
			+ ", typeOfPackage=" + typeOfPackage + ", commodityDescription=" + commodityDescription
			+ ", exBondedPackages=" + exBondedPackages + ", toBondedPackages=" + toBondedPackages + ", comments="
			+ comments + ", inBondedPackages=" + inBondedPackages + ", inbondGrossWt=" + inbondGrossWt
			+ ", inbondInsuranceValue=" + inbondInsuranceValue + ", inbondCifValue=" + inbondCifValue
			+ ", inbondCargoDuty=" + inbondCargoDuty + ", status=" + status + ", createdBy=" + createdBy
			+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy="
			+ approvedBy + ", approvedDate=" + approvedDate + ", shortagePackages=" + shortagePackages + ", damagedQty="
			+ damagedQty + ", breakage=" + breakage + ", yardLocationId=" + yardLocationId + ", blockId=" + blockId
			+ ", cellNoRow=" + cellNoRow + ", areaOccupied=" + areaOccupied + ", exBondedCIF=" + exBondedCIF
			+ ", exBondedCargoDuty=" + exBondedCargoDuty + ", exBondedInsurance=" + exBondedInsurance + ", exBondedGW="
			+ exBondedGW + ", yardPackages=" + yardPackages + ", cellAreaAllocated=" + cellAreaAllocated + ", cellArea="
			+ cellArea + "]";
}
	
	
	
	
	

	
}
