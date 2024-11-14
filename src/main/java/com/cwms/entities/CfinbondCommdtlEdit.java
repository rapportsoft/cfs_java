package com.cwms.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cfinbondcommdtl_edit")
@IdClass(CfinbondCommdtlEditId.class)
public class CfinbondCommdtlEdit {

	
	@Id
	@Column(name = "SrNo")
	private Long SrNo;
	
    @Id
    @Column(name = "branch_id", length = 20, nullable = false)
    private String branchId;

    @Id
    @Column(name = "commodity_id", length = 10,nullable = false)
    private String commodityId;

    @Id
    @Column(name = "common_bonding_id", length = 10, nullable = false)
    private String commonBondingId;

    @Id
    @Column(name = "company_id", length = 6, nullable = false)
    private String companyId;

    @Id
    @Column(name = "noc_no", length = 22, nullable = false)
    private String nocNo;

    @Id
    @Column(name = "noc_trans_id", length = 10, nullable = false)
    private String nocTransId;

    @Column(name = "old_insurance_value", precision = 38, scale = 2)
    private BigDecimal oldInsuranceValue;

    @Column(name = "old_type_of_package", length = 255)
    private String oldTypeOfPackage;

    @Column(name = "approved_by", length = 255)
    private String approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "boe_no", length = 255)
    private String boeNo;

    @Column(name = "bonding_no", length = 255)
    private String bondingNo;

    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate;

    @Column(name = "edited_by", length = 255)
    private String editedBy;

    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate;

    @Column(name = "gross_weight", precision = 38, scale = 2)
    private BigDecimal grossWeight;

    @Column(name = "in_bonding_hdr_id", length = 10)
    private String inBondingHdrId;

    @Column(name = "new_bond_cargo_duty", precision = 38, scale = 2)
    private BigDecimal newBondCargoDuty;

    @Column(name = "new_bond_cif_value", precision = 38, scale = 2)
    private BigDecimal newBondCifValue;

    @Column(name = "new_bond_gross_wt", precision = 38, scale = 2)
    private BigDecimal newBondGrossWt;

    @Column(name = "new_bond_packages", precision = 38, scale = 2)
    private BigDecimal newBondPackages;

    @Column(name = "new_breakage", precision = 38, scale = 2)
    private BigDecimal newBreakage= BigDecimal.ZERO;

    @Column(name = "new_commodity_description", length = 255)
    private String newCommodityDescription;

    @Column(name = "new_damaged_qty", precision = 38, scale = 2)
    private BigDecimal newDamagedQty = BigDecimal.ZERO;

    @Column(name = "new_insurance_value", precision = 38, scale = 2)
    private BigDecimal newInsuranceValue;

    @Column(name = "new_shortage_packages", precision = 38, scale = 2)
    private BigDecimal newShortagePackages= BigDecimal.ZERO;

    @Column(name = "new_type_of_package", length = 255)
    private String newTypeOfPackage;

    @Column(name = "noc_packages", precision = 38, scale = 2)
    private BigDecimal nocPackages;

    @Column(name = "old_bond_cargo_duty", precision = 38, scale = 2)
    private BigDecimal oldBondCargoDuty;

    @Column(name = "old_bond_cif_value", precision = 38, scale = 2)
    private BigDecimal oldBondCifValue;

    @Column(name = "old_bond_gross_wt", precision = 38, scale = 2)
    private BigDecimal oldBondGrossWt;

    @Column(name = "old_bond_packages", precision = 38, scale = 2)
    private BigDecimal oldBondPackages;

    @Column(name = "old_breakage", precision = 38, scale = 2)
    private BigDecimal oldBreakage;

    @Column(name = "old_commodity_description", length = 255)
    private String oldCommodityDescription;

    @Column(name = "old_damaged_qty", precision = 38, scale = 2)
    private BigDecimal oldDamagedQty;

    @Column(name = "old_shortage_packages", precision = 38, scale = 2)
    private BigDecimal oldShortagePackages;

    @Column(name = "status", length = 1)
    private String status;

    @Column(name = "type", length = 10)
    private String type;
    
	@Column(name = "Old_Yard_Packages", precision = 16, scale = 3)
	private BigDecimal oldYardPackages;
	
	
	@Column(name = "New_Yard_Packages", precision = 16, scale = 3)
	private BigDecimal newYardPackages;

	
	
	  @Column(name = "Old_Area_Occupied")
	    private BigDecimal oldAreaOccupied;
	  
	  
	  @Column(name = "New_Area_Occupied")
	    private BigDecimal newAreaOccupied;
	  
	   @Column(name = "yard_location_id", length = 20, nullable = false)
	    private String yardLocationId;


	    @Column(name = "Block_Id", length = 20, nullable = false)
	    private String blockId;


	    @Column(name = "Cell_No_Row", length = 10, nullable = false)
	    private String cellNoRow;
	    
	    @Column(name = "Cell_Area_Allocated")
	  private BigDecimal cellAreaAllocated;
	  
	  
	  
	public BigDecimal getCellAreaAllocated() {
		return cellAreaAllocated;
	}

	public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
		this.cellAreaAllocated = cellAreaAllocated;
	}

	public CfinbondCommdtlEdit() {
		super();
		// TODO Auto-generated constructor stub
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

	


	public BigDecimal getOldYardPackages() {
		return oldYardPackages;
	}

	public void setOldYardPackages(BigDecimal oldYardPackages) {
		this.oldYardPackages = oldYardPackages;
	}

	public BigDecimal getNewYardPackages() {
		return newYardPackages;
	}

	public void setNewYardPackages(BigDecimal newYardPackages) {
		this.newYardPackages = newYardPackages;
	}

	public BigDecimal getOldAreaOccupied() {
		return oldAreaOccupied;
	}

	public void setOldAreaOccupied(BigDecimal oldAreaOccupied) {
		this.oldAreaOccupied = oldAreaOccupied;
	}

	public BigDecimal getNewAreaOccupied() {
		return newAreaOccupied;
	}

	public void setNewAreaOccupied(BigDecimal newAreaOccupied) {
		this.newAreaOccupied = newAreaOccupied;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommonBondingId() {
		return commonBondingId;
	}

	public void setCommonBondingId(String commonBondingId) {
		this.commonBondingId = commonBondingId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public BigDecimal getOldInsuranceValue() {
		return oldInsuranceValue;
	}

	public void setOldInsuranceValue(BigDecimal oldInsuranceValue) {
		this.oldInsuranceValue = oldInsuranceValue;
	}

	public String getOldTypeOfPackage() {
		return oldTypeOfPackage;
	}

	public void setOldTypeOfPackage(String oldTypeOfPackage) {
		this.oldTypeOfPackage = oldTypeOfPackage;
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

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getInBondingHdrId() {
		return inBondingHdrId;
	}

	public void setInBondingHdrId(String inBondingHdrId) {
		this.inBondingHdrId = inBondingHdrId;
	}

	public BigDecimal getNewBondCargoDuty() {
		return newBondCargoDuty;
	}

	public void setNewBondCargoDuty(BigDecimal newBondCargoDuty) {
		this.newBondCargoDuty = newBondCargoDuty;
	}

	public BigDecimal getNewBondCifValue() {
		return newBondCifValue;
	}

	public void setNewBondCifValue(BigDecimal newBondCifValue) {
		this.newBondCifValue = newBondCifValue;
	}

	public BigDecimal getNewBondGrossWt() {
		return newBondGrossWt;
	}

	public void setNewBondGrossWt(BigDecimal newBondGrossWt) {
		this.newBondGrossWt = newBondGrossWt;
	}

	public BigDecimal getNewBondPackages() {
		return newBondPackages;
	}

	public void setNewBondPackages(BigDecimal newBondPackages) {
		this.newBondPackages = newBondPackages;
	}

	public BigDecimal getNewBreakage() {
		return newBreakage;
	}

	public void setNewBreakage(BigDecimal newBreakage) {
		this.newBreakage = newBreakage;
	}

	public String getNewCommodityDescription() {
		return newCommodityDescription;
	}

	public void setNewCommodityDescription(String newCommodityDescription) {
		this.newCommodityDescription = newCommodityDescription;
	}

	public BigDecimal getNewDamagedQty() {
		return newDamagedQty;
	}

	public void setNewDamagedQty(BigDecimal newDamagedQty) {
		this.newDamagedQty = newDamagedQty;
	}

	public BigDecimal getNewInsuranceValue() {
		return newInsuranceValue;
	}

	public void setNewInsuranceValue(BigDecimal newInsuranceValue) {
		this.newInsuranceValue = newInsuranceValue;
	}

	public BigDecimal getNewShortagePackages() {
		return newShortagePackages;
	}

	public void setNewShortagePackages(BigDecimal newShortagePackages) {
		this.newShortagePackages = newShortagePackages;
	}

	public String getNewTypeOfPackage() {
		return newTypeOfPackage;
	}

	public void setNewTypeOfPackage(String newTypeOfPackage) {
		this.newTypeOfPackage = newTypeOfPackage;
	}

	public BigDecimal getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(BigDecimal nocPackages) {
		this.nocPackages = nocPackages;
	}

	public BigDecimal getOldBondCargoDuty() {
		return oldBondCargoDuty;
	}

	public void setOldBondCargoDuty(BigDecimal oldBondCargoDuty) {
		this.oldBondCargoDuty = oldBondCargoDuty;
	}

	public BigDecimal getOldBondCifValue() {
		return oldBondCifValue;
	}

	public void setOldBondCifValue(BigDecimal oldBondCifValue) {
		this.oldBondCifValue = oldBondCifValue;
	}

	public BigDecimal getOldBondGrossWt() {
		return oldBondGrossWt;
	}

	public void setOldBondGrossWt(BigDecimal oldBondGrossWt) {
		this.oldBondGrossWt = oldBondGrossWt;
	}

	public BigDecimal getOldBondPackages() {
		return oldBondPackages;
	}

	public void setOldBondPackages(BigDecimal oldBondPackages) {
		this.oldBondPackages = oldBondPackages;
	}

	public BigDecimal getOldBreakage() {
		return oldBreakage;
	}

	public void setOldBreakage(BigDecimal oldBreakage) {
		this.oldBreakage = oldBreakage;
	}

	public String getOldCommodityDescription() {
		return oldCommodityDescription;
	}

	public void setOldCommodityDescription(String oldCommodityDescription) {
		this.oldCommodityDescription = oldCommodityDescription;
	}

	public BigDecimal getOldDamagedQty() {
		return oldDamagedQty;
	}

	public void setOldDamagedQty(BigDecimal oldDamagedQty) {
		this.oldDamagedQty = oldDamagedQty;
	}

	public BigDecimal getOldShortagePackages() {
		return oldShortagePackages;
	}

	public void setOldShortagePackages(BigDecimal oldShortagePackages) {
		this.oldShortagePackages = oldShortagePackages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSrNo() {
		return SrNo;
	}

	public void setSrNo(Long srNo) {
		SrNo = srNo;
	}

	public CfinbondCommdtlEdit(Long srNo, String branchId, String commodityId, String commonBondingId, String companyId,
			String nocNo, String nocTransId, BigDecimal oldInsuranceValue, String oldTypeOfPackage, String approvedBy,
			Date approvedDate, String boeNo, String bondingNo, String createdBy, Date createdDate, String editedBy,
			Date editedDate, BigDecimal grossWeight, String inBondingHdrId, BigDecimal newBondCargoDuty,
			BigDecimal newBondCifValue, BigDecimal newBondGrossWt, BigDecimal newBondPackages, BigDecimal newBreakage,
			String newCommodityDescription, BigDecimal newDamagedQty, BigDecimal newInsuranceValue,
			BigDecimal newShortagePackages, String newTypeOfPackage, BigDecimal nocPackages,
			BigDecimal oldBondCargoDuty, BigDecimal oldBondCifValue, BigDecimal oldBondGrossWt,
			BigDecimal oldBondPackages, BigDecimal oldBreakage, String oldCommodityDescription,
			BigDecimal oldDamagedQty, BigDecimal oldShortagePackages, String status, String type,
			BigDecimal oldYardPackages, BigDecimal newYardPackages, BigDecimal oldAreaOccupied,
			BigDecimal newAreaOccupied, String yardLocationId, String blockId, String cellNoRow,
			BigDecimal cellAreaAllocated) {
		super();
		SrNo = srNo;
		this.branchId = branchId;
		this.commodityId = commodityId;
		this.commonBondingId = commonBondingId;
		this.companyId = companyId;
		this.nocNo = nocNo;
		this.nocTransId = nocTransId;
		this.oldInsuranceValue = oldInsuranceValue;
		this.oldTypeOfPackage = oldTypeOfPackage;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.boeNo = boeNo;
		this.bondingNo = bondingNo;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.grossWeight = grossWeight;
		this.inBondingHdrId = inBondingHdrId;
		this.newBondCargoDuty = newBondCargoDuty;
		this.newBondCifValue = newBondCifValue;
		this.newBondGrossWt = newBondGrossWt;
		this.newBondPackages = newBondPackages;
		this.newBreakage = newBreakage;
		this.newCommodityDescription = newCommodityDescription;
		this.newDamagedQty = newDamagedQty;
		this.newInsuranceValue = newInsuranceValue;
		this.newShortagePackages = newShortagePackages;
		this.newTypeOfPackage = newTypeOfPackage;
		this.nocPackages = nocPackages;
		this.oldBondCargoDuty = oldBondCargoDuty;
		this.oldBondCifValue = oldBondCifValue;
		this.oldBondGrossWt = oldBondGrossWt;
		this.oldBondPackages = oldBondPackages;
		this.oldBreakage = oldBreakage;
		this.oldCommodityDescription = oldCommodityDescription;
		this.oldDamagedQty = oldDamagedQty;
		this.oldShortagePackages = oldShortagePackages;
		this.status = status;
		this.type = type;
		this.oldYardPackages = oldYardPackages;
		this.newYardPackages = newYardPackages;
		this.oldAreaOccupied = oldAreaOccupied;
		this.newAreaOccupied = newAreaOccupied;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.cellAreaAllocated = cellAreaAllocated;
	}

	@Override
	public String toString() {
		return "CfinbondCommdtlEdit [SrNo=" + SrNo + ", branchId=" + branchId + ", commodityId=" + commodityId
				+ ", commonBondingId=" + commonBondingId + ", companyId=" + companyId + ", nocNo=" + nocNo
				+ ", nocTransId=" + nocTransId + ", oldInsuranceValue=" + oldInsuranceValue + ", oldTypeOfPackage="
				+ oldTypeOfPackage + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", boeNo="
				+ boeNo + ", bondingNo=" + bondingNo + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", grossWeight=" + grossWeight
				+ ", inBondingHdrId=" + inBondingHdrId + ", newBondCargoDuty=" + newBondCargoDuty + ", newBondCifValue="
				+ newBondCifValue + ", newBondGrossWt=" + newBondGrossWt + ", newBondPackages=" + newBondPackages
				+ ", newBreakage=" + newBreakage + ", newCommodityDescription=" + newCommodityDescription
				+ ", newDamagedQty=" + newDamagedQty + ", newInsuranceValue=" + newInsuranceValue
				+ ", newShortagePackages=" + newShortagePackages + ", newTypeOfPackage=" + newTypeOfPackage
				+ ", nocPackages=" + nocPackages + ", oldBondCargoDuty=" + oldBondCargoDuty + ", oldBondCifValue="
				+ oldBondCifValue + ", oldBondGrossWt=" + oldBondGrossWt + ", oldBondPackages=" + oldBondPackages
				+ ", oldBreakage=" + oldBreakage + ", oldCommodityDescription=" + oldCommodityDescription
				+ ", oldDamagedQty=" + oldDamagedQty + ", oldShortagePackages=" + oldShortagePackages + ", status="
				+ status + ", type=" + type + ", oldYardPackages=" + oldYardPackages + ", newYardPackages="
				+ newYardPackages + ", oldAreaOccupied=" + oldAreaOccupied + ", newAreaOccupied=" + newAreaOccupied
				+ ", yardLocationId=" + yardLocationId + ", blockId=" + blockId + ", cellNoRow=" + cellNoRow
				+ ", cellAreaAllocated=" + cellAreaAllocated + "]";
	}

//	public String getCommonCommodityId() {
//		return commonCommodityId;
//	}
//
//	public void setCommonCommodityId(String commonCommodityId) {
//		this.commonCommodityId = commonCommodityId;
//	}
//
//	public String getCommonJobId() {
//		return commonJobId;
//	}
//
//	public void setCommonJobId(String commonJobId) {
//		this.commonJobId = commonJobId;
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

    // Getters and Setters

    // Constructors
}
