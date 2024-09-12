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
@Table(name = "cfbondnocdtl")
@IdClass(CfbondnocdtlId.class)
public class CfBondNocDtl {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId;

	@Id
	@Column(name = "NOC_Trans_Id", length = 10, nullable = false)
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

	@Column(name = "Cfbond_Detail_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date CfbondDetailDate;

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

	@Column(name = "Status", length = 1, nullable = true)
	private String status;

	@Column(name = "Created_By", length = 10)
	private String createdBy;

	@Column(name = "Created_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate;

	@Column(name = "Edited_By", length = 10)
	private String editedBy;

	@Column(name = "Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate;

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;
	
	@Column(name = "Gate_In_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal gateInPackages =BigDecimal.ZERO;
	
	
	@Column(name = "Qty_Taken_In", precision = 8, scale = 0)
	private BigDecimal qtyTakenIn =BigDecimal.ZERO;

	
	
	@Column(name = "Weight_Taken_In", precision = 12, scale = 2)
	private BigDecimal weightTakenIn =BigDecimal.ZERO;

	@Column(name = "Gross_Weight", precision = 16, scale = 3, nullable = true)
	private BigDecimal grossWeight;
	

	  @Column(name = "In_Bonded_Packages", precision = 8, scale = 0, nullable = true)
	    private BigDecimal inBondedPackages =BigDecimal.ZERO;


	    @Column(name = "Inbond_Gross_Wt", precision = 12, scale = 3)
	    private BigDecimal inbondGrossWt=BigDecimal.ZERO;

	    @Column(name = "Inbond_Insurance_Value", precision = 15, scale = 3)
	    private BigDecimal inbondInsuranceValue=BigDecimal.ZERO;

	    @Column(name = "Inbond_CIF_Value", precision = 15, scale = 2)
		private BigDecimal inbondCifValue=BigDecimal.ZERO;
	    
		@Column(name = "Inbond_Cargo_Duty", precision = 15, scale = 2)
		private BigDecimal inbondCargoDuty=BigDecimal.ZERO;
		
		
	    @Column(name = "Shortage_Packages")
		private BigDecimal shortagePackages=BigDecimal.ZERO;

		@Column(name = "Damaged_Qty")
		private BigDecimal damagedQty=BigDecimal.ZERO;

		@Column(name = "Breakage")
		private BigDecimal breakage=BigDecimal.ZERO;
		
		  @Column(name = "Area_Occupied", precision = 5, scale = 0, nullable = true)
		    private BigDecimal areaOccupied;
		  
		  @Column(name = "Bonding_no", length = 25)
		    private String bondingNo;

		  
		  
		  
		  
	public String getBondingNo() {
			return bondingNo;
		}

		public void setBondingNo(String bondingNo) {
			this.bondingNo = bondingNo;
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

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	

	// all object 
	

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
	}

	public BigDecimal getQtyTakenIn() {
		return qtyTakenIn;
	}
	

	public void setQtyTakenIn(BigDecimal qtyTakenIn) {
		this.qtyTakenIn = qtyTakenIn;
	}

	public BigDecimal getWeightTakenIn() {
		return weightTakenIn;
	}

	public void setWeightTakenIn(BigDecimal weightTakenIn) {
		this.weightTakenIn = weightTakenIn;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
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

	public Date getCfbondDetailDate() {
		return CfbondDetailDate;
	}

	public void setCfbondDetailDate(Date cfbondDetailDate) {
		CfbondDetailDate = cfbondDetailDate;
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

	public CfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo, String cfBondDtlId,
			String boeNo, Date cfbondDetailDate, BigDecimal nocPackages, BigDecimal cifValue, BigDecimal cargoDuty,
			BigDecimal insuranceValue, String typeOfPackage, String commodityDescription, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			BigDecimal gateInPackages, BigDecimal qtyTakenIn, BigDecimal weightTakenIn, BigDecimal grossWeight,
			BigDecimal inBondedPackages, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue,
			BigDecimal inbondCifValue, BigDecimal inbondCargoDuty, BigDecimal shortagePackages, BigDecimal damagedQty,
			BigDecimal breakage, BigDecimal areaOccupied, String bondingNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
		CfbondDetailDate = cfbondDetailDate;
		this.nocPackages = nocPackages;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.gateInPackages = gateInPackages;
		this.qtyTakenIn = qtyTakenIn;
		this.weightTakenIn = weightTakenIn;
		this.grossWeight = grossWeight;
		this.inBondedPackages = inBondedPackages;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inbondCifValue = inbondCifValue;
		this.inbondCargoDuty = inbondCargoDuty;
		this.shortagePackages = shortagePackages;
		this.damagedQty = damagedQty;
		this.breakage = breakage;
		this.areaOccupied = areaOccupied;
		this.bondingNo = bondingNo;
	}

	public CfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo, String cfBondDtlId,
			String boeNo, Date cfbondDetailDate, BigDecimal nocPackages, BigDecimal cifValue, BigDecimal cargoDuty,
			BigDecimal insuranceValue, String typeOfPackage, String commodityDescription, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
		CfbondDetailDate = cfbondDetailDate;
		this.nocPackages = nocPackages;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}
	
	
	

	public CfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo, String cfBondDtlId,
			String boeNo, Date cfbondDetailDate, BigDecimal nocPackages, BigDecimal cifValue, BigDecimal cargoDuty,
			BigDecimal insuranceValue, String typeOfPackage, String commodityDescription, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			BigDecimal gateInPackages) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
		CfbondDetailDate = cfbondDetailDate;
		this.nocPackages = nocPackages;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.gateInPackages = gateInPackages;
	}
	
	

	public CfBondNocDtl() {
		super();
	}

	
	
	
	
	


	
	public CfBondNocDtl(String companyId, String branchId, String nocTransId, String nocNo, String cfBondDtlId,
			String boeNo, BigDecimal nocPackages, BigDecimal cifValue, BigDecimal cargoDuty, BigDecimal insuranceValue,
			String typeOfPackage, String commodityDescription, String status,BigDecimal grossWeight,BigDecimal gateInPackages,BigDecimal weightTakenIn,BigDecimal inBondedPackages,
			BigDecimal inbondGrossWt,BigDecimal inbondCargoDuty,BigDecimal inbondCifValue,BigDecimal shortagePackages,BigDecimal damagedQty,BigDecimal breakage,String createdBy, String editedBy, String approvedBy,BigDecimal areaOccupied) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.cfBondDtlId = cfBondDtlId;
		this.boeNo = boeNo;
		this.nocPackages = nocPackages;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.typeOfPackage = typeOfPackage;
		this.commodityDescription = commodityDescription;
		this.status = status;
		this.grossWeight = grossWeight;
		this.gateInPackages = gateInPackages;
		this.weightTakenIn = weightTakenIn;
		this.inBondedPackages = inBondedPackages;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondCargoDuty = inbondCargoDuty;
		this.inbondCifValue = inbondCifValue;
		this.shortagePackages = shortagePackages;
		this.damagedQty = damagedQty;
		this.breakage = breakage;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.areaOccupied = areaOccupied;
	}

	@Override
	public String toString() {
		return "CfBondNocDtl [companyId=" + companyId + ", branchId=" + branchId + ", nocTransId=" + nocTransId
				+ ", nocNo=" + nocNo + ", cfBondDtlId=" + cfBondDtlId + ", boeNo=" + boeNo + ", CfbondDetailDate="
				+ CfbondDetailDate + ", nocPackages=" + nocPackages + ", cifValue=" + cifValue + ", cargoDuty="
				+ cargoDuty + ", insuranceValue=" + insuranceValue + ", typeOfPackage=" + typeOfPackage
				+ ", commodityDescription=" + commodityDescription + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", gateInPackages=" + gateInPackages
				+ ", qtyTakenIn=" + qtyTakenIn + ", weightTakenIn=" + weightTakenIn + ", grossWeight=" + grossWeight
				+ ", inBondedPackages=" + inBondedPackages + ", inbondGrossWt=" + inbondGrossWt
				+ ", inbondInsuranceValue=" + inbondInsuranceValue + ", inbondCifValue=" + inbondCifValue
				+ ", inbondCargoDuty=" + inbondCargoDuty + ", shortagePackages=" + shortagePackages + ", damagedQty="
				+ damagedQty + ", breakage=" + breakage + ", areaOccupied=" + areaOccupied + ", bondingNo=" + bondingNo
				+ "]";
	}
	
	
	
	
	
	
	
	

}
