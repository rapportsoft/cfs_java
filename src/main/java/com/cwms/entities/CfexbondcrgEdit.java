package com.cwms.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cfexbondcrg_edit")
@IdClass(CfexbondcrgEditKey.class)
public class CfexbondcrgEdit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SrNo")
	private Long SrNo;

	@Id
	@Column(name = "in_bonding_id", length = 18)
	private String inBondingId;

	@Id
	@Column(name = "Audit_Id", length = 10)
	private String auditId;
	
	@Id
	@Column(name = "ex_bonding_id", length = 10, nullable = false)
	private String exBondingId;

	@Id
	@Column(name = "branch_id", length = 6, nullable = false)
	private String branchId;

	@Id
	@Column(name = "company_id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "noc_trans_id", length = 10, nullable = false)
	private String nocTransId;

	@Column(name = "approved_by", length = 10)
	private String approvedBy;

	@Column(name = "approved_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;

	@Column(name = "balance_cif", precision = 12, scale = 3)
	private BigDecimal balanceCif;

	@Column(name = "balance_cif_old", precision = 12, scale = 3)
	private BigDecimal balanceCifOld;

	@Column(name = "balance_cargo_duty", precision = 12, scale = 3)
	private BigDecimal balanceCargoDuty;

	@Column(name = "balance_cargo_duty_old", precision = 12, scale = 3)
	private BigDecimal balanceCargoDutyOld;

	@Column(name = "balance_gw", precision = 16, scale = 3)
	private BigDecimal balanceGw;

	@Column(name = "balance_gw_old", precision = 16, scale = 3)
	private BigDecimal balanceGwOld;

	@Column(name = "balance_insurance", precision = 12, scale = 3)
	private BigDecimal balanceInsurance;

	@Column(name = "balance_insurance_old", precision = 12, scale = 3)
	private BigDecimal balanceInsuranceOld;

	@Column(name = "balanced_packages_new", precision = 16, scale = 3)
	private BigDecimal balancedPackagesNew;

	@Column(name = "balanced_packages_old", precision = 16, scale = 3)
	private BigDecimal balancedPackagesOld;

	@Column(name = "balanced_qty", precision = 16, scale = 3)
	private BigDecimal balancedQty;

	@Column(name = "balanced_qty_new", precision = 16, scale = 3)
	private BigDecimal balancedQtyNew;

	@Column(name = "boe_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date boeDate;

	@Column(name = "boe_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date boeDateOld;

	@Column(name = "boe_no", length = 20)
	private String boeNo;

	@Column(name = "boe_no_old", length = 20)
	private String boeNoOld;

	@Column(name = "bond_validity_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date bondValidityDate;

	@Column(name = "bond_validity_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date bondValidityDateOld;

	@Column(name = "bonding_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date bondingDate;

	@Column(name = "bonding_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date bondingDateOld;

	@Column(name = "bonding_no", length = 65)
	private String bondingNo;

	@Column(name = "bonding_no_old", length = 65)
	private String bondingNoOld;

	@Column(name = "cha", length = 6)
	private String cha;

	@Column(name = "cha_old", length = 6)
	private String chaOld;

	@Column(name = "commodity_description", length = 250)
	private String commodityDescription;

	@Column(name = "commodity_description_old", length = 250)
	private String commodityDescriptionOld;

	@Column(name = "created_by", length = 46)
	private String createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate;

	@Column(name = "edited_by", length = 10)
	private String editedBy;

	@Column(name = "edited_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate;

	@Column(name = "ex_bond_be_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date exBondBeDate;

	@Column(name = "ex_bond_be_no", length = 20)
	private String exBondBeNo;

	@Column(name = "ex_bond_be_no_old", length = 20)
	private String exBondBeNoOld;

	@Column(name = "ex_bonded_cif", precision = 12, scale = 3)
	private BigDecimal exBondedCif;

	@Column(name = "ex_bonded_cif_old", precision = 12, scale = 3)
	private BigDecimal exBondedCifOld;

	@Column(name = "ex_bonded_cargo_duty", precision = 12, scale = 3)
	private BigDecimal exBondedCargoDuty;

	@Column(name = "ex_bonded_cargo_duty_old", precision = 12, scale = 3)
	private BigDecimal exBondedCargoDutyOld;

	@Column(name = "ex_bonded_gw", precision = 16, scale = 3)
	private BigDecimal exBondedGw;

	@Column(name = "ex_bonded_gw_old", precision = 16, scale = 3)
	private BigDecimal exBondedGwOld;

	@Column(name = "ex_bonded_insurance", precision = 12, scale = 3)
	private BigDecimal exBondedInsurance;

	@Column(name = "ex_bonded_insurance_old", precision = 12, scale = 3)
	private BigDecimal exBondedInsuranceOld;

	@Column(name = "ex_bonded_packages", precision = 16, scale = 3)
	private BigDecimal exBondedPackages;

	@Column(name = "ex_bonded_packages_old", precision = 16, scale = 3)
	private BigDecimal exBondedPackagesOld;

	@Column(name = "ex_bonding_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date exBondingDate;

	@Column(name = "ex_bonding_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date exBondingDateOld;

	@Column(name = "igm_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date igmDate;

	@Column(name = "igm_line_no", length = 4)
	private String igmLineNo;

	@Column(name = "igm_no", length = 10)
	private String igmNo;

	@Column(name = "importer_id", length = 7)
	private String importerId;

	@Column(name = "importer_id_old", length = 7)
	private String importerIdOld;

	@Column(name = "importer_name", length = 60)
	private String importerName;

	@Column(name = "importer_name_old", length = 60)
	private String importerNameOld;

	@Column(name = "in_bonded_cif", precision = 12, scale = 3)
	private BigDecimal inBondedCif;

	@Column(name = "in_bonded_cif_old", precision = 12, scale = 3)
	private BigDecimal inBondedCifOld;

	@Column(name = "in_bonded_cargo_duty", precision = 12, scale = 3)
	private BigDecimal inBondedCargoDuty;

	@Column(name = "in_bonded_cargo_duty_old", precision = 12, scale = 3)
	private BigDecimal inBondedCargoDutyOld;

	@Column(name = "in_bonded_gw", precision = 16, scale = 3)
	private BigDecimal inBondedGw;

	@Column(name = "in_bonded_gw_old", precision = 16, scale = 3)
	private BigDecimal inBondedGwOld;

	@Column(name = "in_bonded_insurance", precision = 12, scale = 3)
	private BigDecimal inBondedInsurance;

	@Column(name = "in_bonded_insurance_old", precision = 12, scale = 3)
	private BigDecimal inBondedInsuranceOld;

	@Column(name = "in_bonded_packages", precision = 16, scale = 3)
	private BigDecimal inBondedPackages;

	@Column(name = "in_bonded_packages_old", precision = 16, scale = 3)
	private BigDecimal inBondedPackagesOld;

	@Column(name = "in_bonding_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date inBondingDate;

	@Column(name = "in_bonding_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date inBondingDateOld;

	@Column(name = "noc_no", length = 15)
	private String nocNo;

	@Column(name = "noc_validity_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocValidityDate;

	@Column(name = "noc_validity_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocValidityDateOld;

	@Column(name = "remaining_cif", precision = 12, scale = 3)
	private BigDecimal remainingCif;

	@Column(name = "remaining_cif_old", precision = 12, scale = 3)
	private BigDecimal remainingCifOld;

	@Column(name = "remaining_cargo_duty", precision = 16, scale = 3)
	private BigDecimal remainingCargoDuty;

	@Column(name = "remaining_cargo_duty_old", precision = 16, scale = 3)
	private BigDecimal remainingCargoDutyOld;

	@Column(name = "remaining_gw", precision = 16, scale = 3)
	private BigDecimal remainingGw;

	@Column(name = "remaining_gw_old", precision = 16, scale = 3)
	private BigDecimal remainingGwOld;

	@Column(name = "remaining_insurance", precision = 12, scale = 3)
	private BigDecimal remainingInsurance;

	@Column(name = "remaining_insurance_old", precision = 12, scale = 3)
	private BigDecimal remainingInsuranceOld;

	@Column(name = "remaining_packages", precision = 16, scale = 3)
	private BigDecimal remainingPackages;

	@Column(name = "remaining_packages_old", precision = 16, scale = 3)
	private BigDecimal remainingPackagesOld;

	@Column(name = "sb_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sbDate;

	@Column(name = "sb_date_old")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sbDateOld;

	@Column(name = "sb_duty_new", precision = 16, scale = 3)
	private BigDecimal sbDutyNew;

	@Column(name = "sb_duty_old", precision = 16, scale = 3)
	private BigDecimal sbDutyOld;

	@Column(name = "sb_no", length = 20)
	private String sbNo;

	@Column(name = "sb_no_old", length = 20)
	private String sbNoOld;

	@Column(name = "sb_qty_new", precision = 16, scale = 3)
	private BigDecimal sbQtyNew;

	@Column(name = "sb_qty_old", precision = 16, scale = 3)
	private BigDecimal sbQtyOld;

	@Column(name = "sb_uom_new", length = 6)
	private String sbUomNew;

	@Column(name = "sb_uom_old", length = 6)
	private String sbUomOld;

	@Column(name = "sb_value_new", precision = 16, scale = 3)
	private BigDecimal sbValueNew;

	@Column(name = "sb_value_old", precision = 16, scale = 3)
	private BigDecimal sbValueOld;

	@Column(name = "section_49", length = 1)
	private String section49;

	@Column(name = "section_49_old", length = 1)
	private String section49Old;

	@Column(name = "section_60", length = 1)
	private String section60;

	@Column(name = "section_60_old", length = 1)
	private String section60Old;

	@Column(name = "status", length = 1)
	private String status;

	@Column(name = "tran_type", length = 10)
	private String tranType;

	@Column(name = "noc_trans_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocTransDate;

	@Column(name = "noc_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocDate;

	@Column(name = "importer_address1", length = 250)
	private String importerAddress1;

	@Column(name = "importer_address2", length = 250)
	private String importerAddress2;

	@Column(name = "importer_address3", length = 250)
	private String importerAddress3;

	@Column(name = "change_of_owner_name", length = 50)
	private String changeOfOwnerName;

	@Column(name = "change_of_owner_name_old", length = 50)
	private String changeOfOwnerNameOld;

	@Column(name = "change_of_owner_ship", length = 1)
	private char changeOfOwnership;

	@Column(name = "change_of_owner_ship_old", length = 1)
	private char changeOfOwnershipOld;

	@Column(name = "ex_bond_be_date_old")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date exBondBeDateOld;

	@Column(name = "owner_ship_changes", length = 1)
	private char ownershipChanges;

	@Column(name = "owner_ship_changes_old", length = 1)
	private char ownershipChangesOld;

	@Column(name = "trnsfer_bond_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date transferBondDate;

	@Column(name = "transfer_bond_date_old")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date transferBondDateOld;

	@Column(name = "transfer_bond_no", length = 20)
	private String transferBondNo;

	@Column(name = "transfer_bond_no_old", length = 20)
	private String transferBondNoOld;

	@Column(name = "ex_bond_type", length = 10)
	private String exBondType;

	@Column(name = "common_job_id", length = 10, nullable = false)
	private String commonJobId;

	@Column(name = "New_Cha_Code")
	private String newChaCode;

	@Transient
	private String newChaName;

	@Transient
	private BigDecimal oldInsuranceValue;

	@Transient
	private String oldTypeOfPackage;

	@Transient
	private BigDecimal newBondCargoDuty;

	@Transient
	private BigDecimal newBondCifValue;

	@Transient
	private BigDecimal newBondGrossWt;

	@Transient
	private BigDecimal newBondPackages;

	@Transient
	private BigDecimal newBreakage = BigDecimal.ZERO;

	@Transient
	private String newCommodityDescription;

	@Transient
	private BigDecimal newDamagedQty = BigDecimal.ZERO;
	
	@Transient
	private BigDecimal newInsuranceValue;

	@Transient
	private BigDecimal newShortagePackages = BigDecimal.ZERO;

	@Transient
	private String newTypeOfPackage;

	@Transient
	private BigDecimal oldBondCargoDuty;

	@Transient
	private BigDecimal oldBondCifValue;

	@Transient
	private BigDecimal oldBondGrossWt;

	@Transient
	private BigDecimal oldBondPackages;

	@Transient
	private BigDecimal oldBreakage;

	@Transient
	private String oldCommodityDescription;

	@Transient
	private BigDecimal oldDamagedQty;

	@Transient
	private BigDecimal oldShortagePackages;

	@Transient
	private String type;

	
	
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNewChaName() {
		return newChaName;
	}

	public void setNewChaName(String newChaName) {
		this.newChaName = newChaName;
	}

	public String getNewChaCode() {
		return newChaCode;
	}

	public void setNewChaCode(String newChaCode) {
		this.newChaCode = newChaCode;
	}

	public Long getSrNo() {
		return SrNo;
	}

	public void setSrNo(Long SrNo) {
		this.SrNo = SrNo;
	}

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
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

	public BigDecimal getBalanceCif() {
		return balanceCif;
	}

	public void setBalanceCif(BigDecimal balanceCif) {
		this.balanceCif = balanceCif;
	}

	public BigDecimal getBalanceCifOld() {
		return balanceCifOld;
	}

	public void setBalanceCifOld(BigDecimal balanceCifOld) {
		this.balanceCifOld = balanceCifOld;
	}

	public BigDecimal getBalanceCargoDuty() {
		return balanceCargoDuty;
	}

	public void setBalanceCargoDuty(BigDecimal balanceCargoDuty) {
		this.balanceCargoDuty = balanceCargoDuty;
	}

	public BigDecimal getBalanceCargoDutyOld() {
		return balanceCargoDutyOld;
	}

	public void setBalanceCargoDutyOld(BigDecimal balanceCargoDutyOld) {
		this.balanceCargoDutyOld = balanceCargoDutyOld;
	}

	public BigDecimal getBalanceGw() {
		return balanceGw;
	}

	public void setBalanceGw(BigDecimal balanceGw) {
		this.balanceGw = balanceGw;
	}

	public BigDecimal getBalanceGwOld() {
		return balanceGwOld;
	}

	public void setBalanceGwOld(BigDecimal balanceGwOld) {
		this.balanceGwOld = balanceGwOld;
	}

	public BigDecimal getBalanceInsurance() {
		return balanceInsurance;
	}

	public void setBalanceInsurance(BigDecimal balanceInsurance) {
		this.balanceInsurance = balanceInsurance;
	}

	public BigDecimal getBalanceInsuranceOld() {
		return balanceInsuranceOld;
	}

	public void setBalanceInsuranceOld(BigDecimal balanceInsuranceOld) {
		this.balanceInsuranceOld = balanceInsuranceOld;
	}

	public BigDecimal getBalancedPackagesNew() {
		return balancedPackagesNew;
	}

	public void setBalancedPackagesNew(BigDecimal balancedPackagesNew) {
		this.balancedPackagesNew = balancedPackagesNew;
	}

	public BigDecimal getBalancedPackagesOld() {
		return balancedPackagesOld;
	}

	public void setBalancedPackagesOld(BigDecimal balancedPackagesOld) {
		this.balancedPackagesOld = balancedPackagesOld;
	}

	public BigDecimal getBalancedQty() {
		return balancedQty;
	}

	public void setBalancedQty(BigDecimal balancedQty) {
		this.balancedQty = balancedQty;
	}

	public BigDecimal getBalancedQtyNew() {
		return balancedQtyNew;
	}

	public void setBalancedQtyNew(BigDecimal balancedQtyNew) {
		this.balancedQtyNew = balancedQtyNew;
	}

	public Date getBoeDate() {
		return boeDate;
	}

	public void setBoeDate(Date boeDate) {
		this.boeDate = boeDate;
	}

	public Date getBoeDateOld() {
		return boeDateOld;
	}

	public void setBoeDateOld(Date boeDateOld) {
		this.boeDateOld = boeDateOld;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public String getBoeNoOld() {
		return boeNoOld;
	}

	public void setBoeNoOld(String boeNoOld) {
		this.boeNoOld = boeNoOld;
	}

	public Date getBondValidityDate() {
		return bondValidityDate;
	}

	public void setBondValidityDate(Date bondValidityDate) {
		this.bondValidityDate = bondValidityDate;
	}

	public Date getBondValidityDateOld() {
		return bondValidityDateOld;
	}

	public void setBondValidityDateOld(Date bondValidityDateOld) {
		this.bondValidityDateOld = bondValidityDateOld;
	}

	public Date getBondingDate() {
		return bondingDate;
	}

	public void setBondingDate(Date bondingDate) {
		this.bondingDate = bondingDate;
	}

	public Date getBondingDateOld() {
		return bondingDateOld;
	}

	public void setBondingDateOld(Date bondingDateOld) {
		this.bondingDateOld = bondingDateOld;
	}

	public String getBondingNo() {
		return bondingNo;
	}

	public void setBondingNo(String bondingNo) {
		this.bondingNo = bondingNo;
	}

	public String getBondingNoOld() {
		return bondingNoOld;
	}

	public void setBondingNoOld(String bondingNoOld) {
		this.bondingNoOld = bondingNoOld;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getChaOld() {
		return chaOld;
	}

	public void setChaOld(String chaOld) {
		this.chaOld = chaOld;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getCommodityDescriptionOld() {
		return commodityDescriptionOld;
	}

	public void setCommodityDescriptionOld(String commodityDescriptionOld) {
		this.commodityDescriptionOld = commodityDescriptionOld;
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

	public Date getExBondBeDate() {
		return exBondBeDate;
	}

	public void setExBondBeDate(Date exBondBeDate) {
		this.exBondBeDate = exBondBeDate;
	}

	public String getExBondBeNo() {
		return exBondBeNo;
	}

	public void setExBondBeNo(String exBondBeNo) {
		this.exBondBeNo = exBondBeNo;
	}

	public String getExBondBeNoOld() {
		return exBondBeNoOld;
	}

	public void setExBondBeNoOld(String exBondBeNoOld) {
		this.exBondBeNoOld = exBondBeNoOld;
	}

	public BigDecimal getExBondedCif() {
		return exBondedCif;
	}

	public void setExBondedCif(BigDecimal exBondedCif) {
		this.exBondedCif = exBondedCif;
	}

	public BigDecimal getExBondedCifOld() {
		return exBondedCifOld;
	}

	public void setExBondedCifOld(BigDecimal exBondedCifOld) {
		this.exBondedCifOld = exBondedCifOld;
	}

	public BigDecimal getExBondedCargoDuty() {
		return exBondedCargoDuty;
	}

	public void setExBondedCargoDuty(BigDecimal exBondedCargoDuty) {
		this.exBondedCargoDuty = exBondedCargoDuty;
	}

	public BigDecimal getExBondedCargoDutyOld() {
		return exBondedCargoDutyOld;
	}

	public void setExBondedCargoDutyOld(BigDecimal exBondedCargoDutyOld) {
		this.exBondedCargoDutyOld = exBondedCargoDutyOld;
	}

	public BigDecimal getExBondedGw() {
		return exBondedGw;
	}

	public void setExBondedGw(BigDecimal exBondedGw) {
		this.exBondedGw = exBondedGw;
	}

	public BigDecimal getExBondedGwOld() {
		return exBondedGwOld;
	}

	public void setExBondedGwOld(BigDecimal exBondedGwOld) {
		this.exBondedGwOld = exBondedGwOld;
	}

	public BigDecimal getExBondedInsurance() {
		return exBondedInsurance;
	}

	public void setExBondedInsurance(BigDecimal exBondedInsurance) {
		this.exBondedInsurance = exBondedInsurance;
	}

	public BigDecimal getExBondedInsuranceOld() {
		return exBondedInsuranceOld;
	}

	public void setExBondedInsuranceOld(BigDecimal exBondedInsuranceOld) {
		this.exBondedInsuranceOld = exBondedInsuranceOld;
	}

	public BigDecimal getExBondedPackages() {
		return exBondedPackages;
	}

	public void setExBondedPackages(BigDecimal exBondedPackages) {
		this.exBondedPackages = exBondedPackages;
	}

	public BigDecimal getExBondedPackagesOld() {
		return exBondedPackagesOld;
	}

	public void setExBondedPackagesOld(BigDecimal exBondedPackagesOld) {
		this.exBondedPackagesOld = exBondedPackagesOld;
	}

	public Date getExBondingDate() {
		return exBondingDate;
	}

	public void setExBondingDate(Date exBondingDate) {
		this.exBondingDate = exBondingDate;
	}

	public Date getExBondingDateOld() {
		return exBondingDateOld;
	}

	public void setExBondingDateOld(Date exBondingDateOld) {
		this.exBondingDateOld = exBondingDateOld;
	}

	public Date getIgmDate() {
		return igmDate;
	}

	public void setIgmDate(Date igmDate) {
		this.igmDate = igmDate;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public String getImporterIdOld() {
		return importerIdOld;
	}

	public void setImporterIdOld(String importerIdOld) {
		this.importerIdOld = importerIdOld;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public String getImporterNameOld() {
		return importerNameOld;
	}

	public void setImporterNameOld(String importerNameOld) {
		this.importerNameOld = importerNameOld;
	}

	public BigDecimal getInBondedCif() {
		return inBondedCif;
	}

	public void setInBondedCif(BigDecimal inBondedCif) {
		this.inBondedCif = inBondedCif;
	}

	public BigDecimal getInBondedCifOld() {
		return inBondedCifOld;
	}

	public void setInBondedCifOld(BigDecimal inBondedCifOld) {
		this.inBondedCifOld = inBondedCifOld;
	}

	public BigDecimal getInBondedCargoDuty() {
		return inBondedCargoDuty;
	}

	public void setInBondedCargoDuty(BigDecimal inBondedCargoDuty) {
		this.inBondedCargoDuty = inBondedCargoDuty;
	}

	public BigDecimal getInBondedCargoDutyOld() {
		return inBondedCargoDutyOld;
	}

	public void setInBondedCargoDutyOld(BigDecimal inBondedCargoDutyOld) {
		this.inBondedCargoDutyOld = inBondedCargoDutyOld;
	}

	public BigDecimal getInBondedGw() {
		return inBondedGw;
	}

	public void setInBondedGw(BigDecimal inBondedGw) {
		this.inBondedGw = inBondedGw;
	}

	public BigDecimal getInBondedGwOld() {
		return inBondedGwOld;
	}

	public void setInBondedGwOld(BigDecimal inBondedGwOld) {
		this.inBondedGwOld = inBondedGwOld;
	}

	public BigDecimal getInBondedInsurance() {
		return inBondedInsurance;
	}

	public void setInBondedInsurance(BigDecimal inBondedInsurance) {
		this.inBondedInsurance = inBondedInsurance;
	}

	public BigDecimal getInBondedInsuranceOld() {
		return inBondedInsuranceOld;
	}

	public void setInBondedInsuranceOld(BigDecimal inBondedInsuranceOld) {
		this.inBondedInsuranceOld = inBondedInsuranceOld;
	}

	public BigDecimal getInBondedPackages() {
		return inBondedPackages;
	}

	public void setInBondedPackages(BigDecimal inBondedPackages) {
		this.inBondedPackages = inBondedPackages;
	}

	public BigDecimal getInBondedPackagesOld() {
		return inBondedPackagesOld;
	}

	public void setInBondedPackagesOld(BigDecimal inBondedPackagesOld) {
		this.inBondedPackagesOld = inBondedPackagesOld;
	}

	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
	}

	public Date getInBondingDateOld() {
		return inBondingDateOld;
	}

	public void setInBondingDateOld(Date inBondingDateOld) {
		this.inBondingDateOld = inBondingDateOld;
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

	public Date getNocValidityDate() {
		return nocValidityDate;
	}

	public void setNocValidityDate(Date nocValidityDate) {
		this.nocValidityDate = nocValidityDate;
	}

	public Date getNocValidityDateOld() {
		return nocValidityDateOld;
	}

	public void setNocValidityDateOld(Date nocValidityDateOld) {
		this.nocValidityDateOld = nocValidityDateOld;
	}

	public BigDecimal getRemainingCif() {
		return remainingCif;
	}

	public void setRemainingCif(BigDecimal remainingCif) {
		this.remainingCif = remainingCif;
	}

	public BigDecimal getRemainingCifOld() {
		return remainingCifOld;
	}

	public void setRemainingCifOld(BigDecimal remainingCifOld) {
		this.remainingCifOld = remainingCifOld;
	}

	public BigDecimal getRemainingCargoDuty() {
		return remainingCargoDuty;
	}

	public void setRemainingCargoDuty(BigDecimal remainingCargoDuty) {
		this.remainingCargoDuty = remainingCargoDuty;
	}

	public BigDecimal getRemainingCargoDutyOld() {
		return remainingCargoDutyOld;
	}

	public void setRemainingCargoDutyOld(BigDecimal remainingCargoDutyOld) {
		this.remainingCargoDutyOld = remainingCargoDutyOld;
	}

	public BigDecimal getRemainingGw() {
		return remainingGw;
	}

	public void setRemainingGw(BigDecimal remainingGw) {
		this.remainingGw = remainingGw;
	}

	public BigDecimal getRemainingGwOld() {
		return remainingGwOld;
	}

	public void setRemainingGwOld(BigDecimal remainingGwOld) {
		this.remainingGwOld = remainingGwOld;
	}

	public BigDecimal getRemainingInsurance() {
		return remainingInsurance;
	}

	public void setRemainingInsurance(BigDecimal remainingInsurance) {
		this.remainingInsurance = remainingInsurance;
	}

	public BigDecimal getRemainingInsuranceOld() {
		return remainingInsuranceOld;
	}

	public void setRemainingInsuranceOld(BigDecimal remainingInsuranceOld) {
		this.remainingInsuranceOld = remainingInsuranceOld;
	}

	public BigDecimal getRemainingPackages() {
		return remainingPackages;
	}

	public void setRemainingPackages(BigDecimal remainingPackages) {
		this.remainingPackages = remainingPackages;
	}

	public BigDecimal getRemainingPackagesOld() {
		return remainingPackagesOld;
	}

	public void setRemainingPackagesOld(BigDecimal remainingPackagesOld) {
		this.remainingPackagesOld = remainingPackagesOld;
	}

	public Date getSbDate() {
		return sbDate;
	}

	public void setSbDate(Date sbDate) {
		this.sbDate = sbDate;
	}

	public Date getSbDateOld() {
		return sbDateOld;
	}

	public void setSbDateOld(Date sbDateOld) {
		this.sbDateOld = sbDateOld;
	}

	public BigDecimal getSbDutyNew() {
		return sbDutyNew;
	}

	public void setSbDutyNew(BigDecimal sbDutyNew) {
		this.sbDutyNew = sbDutyNew;
	}

	public BigDecimal getSbDutyOld() {
		return sbDutyOld;
	}

	public void setSbDutyOld(BigDecimal sbDutyOld) {
		this.sbDutyOld = sbDutyOld;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getSbNoOld() {
		return sbNoOld;
	}

	public void setSbNoOld(String sbNoOld) {
		this.sbNoOld = sbNoOld;
	}

	public BigDecimal getSbQtyNew() {
		return sbQtyNew;
	}

	public void setSbQtyNew(BigDecimal sbQtyNew) {
		this.sbQtyNew = sbQtyNew;
	}

	public BigDecimal getSbQtyOld() {
		return sbQtyOld;
	}

	public void setSbQtyOld(BigDecimal sbQtyOld) {
		this.sbQtyOld = sbQtyOld;
	}

	public String getSbUomNew() {
		return sbUomNew;
	}

	public void setSbUomNew(String sbUomNew) {
		this.sbUomNew = sbUomNew;
	}

	public String getSbUomOld() {
		return sbUomOld;
	}

	public void setSbUomOld(String sbUomOld) {
		this.sbUomOld = sbUomOld;
	}

	public BigDecimal getSbValueNew() {
		return sbValueNew;
	}

	public void setSbValueNew(BigDecimal sbValueNew) {
		this.sbValueNew = sbValueNew;
	}

	public BigDecimal getSbValueOld() {
		return sbValueOld;
	}

	public void setSbValueOld(BigDecimal sbValueOld) {
		this.sbValueOld = sbValueOld;
	}

	public String getSection49() {
		return section49;
	}

	public void setSection49(String section49) {
		this.section49 = section49;
	}

	public String getSection49Old() {
		return section49Old;
	}

	public void setSection49Old(String section49Old) {
		this.section49Old = section49Old;
	}

	public String getSection60() {
		return section60;
	}

	public void setSection60(String section60) {
		this.section60 = section60;
	}

	public String getSection60Old() {
		return section60Old;
	}

	public void setSection60Old(String section60Old) {
		this.section60Old = section60Old;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
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

	public String getImporterAddress1() {
		return importerAddress1;
	}

	public void setImporterAddress1(String importerAddress1) {
		this.importerAddress1 = importerAddress1;
	}

	public String getImporterAddress2() {
		return importerAddress2;
	}

	public void setImporterAddress2(String importerAddress2) {
		this.importerAddress2 = importerAddress2;
	}

	public String getImporterAddress3() {
		return importerAddress3;
	}

	public void setImporterAddress3(String importerAddress3) {
		this.importerAddress3 = importerAddress3;
	}

	public String getChangeOfOwnerName() {
		return changeOfOwnerName;
	}

	public void setChangeOfOwnerName(String changeOfOwnerName) {
		this.changeOfOwnerName = changeOfOwnerName;
	}

	public String getChangeOfOwnerNameOld() {
		return changeOfOwnerNameOld;
	}

	public void setChangeOfOwnerNameOld(String changeOfOwnerNameOld) {
		this.changeOfOwnerNameOld = changeOfOwnerNameOld;
	}

	public char getChangeOfOwnership() {
		return changeOfOwnership;
	}

	public void setChangeOfOwnership(char changeOfOwnership) {
		this.changeOfOwnership = changeOfOwnership;
	}

	public char getChangeOfOwnershipOld() {
		return changeOfOwnershipOld;
	}

	public void setChangeOfOwnershipOld(char changeOfOwnershipOld) {
		this.changeOfOwnershipOld = changeOfOwnershipOld;
	}

	public Date getExBondBeDateOld() {
		return exBondBeDateOld;
	}

	public void setExBondBeDateOld(Date exBondBeDateOld) {
		this.exBondBeDateOld = exBondBeDateOld;
	}

	public char getOwnershipChanges() {
		return ownershipChanges;
	}

	public void setOwnershipChanges(char ownershipChanges) {
		this.ownershipChanges = ownershipChanges;
	}

	public char getOwnershipChangesOld() {
		return ownershipChangesOld;
	}

	public void setOwnershipChangesOld(char ownershipChangesOld) {
		this.ownershipChangesOld = ownershipChangesOld;
	}

	public Date getTransferBondDate() {
		return transferBondDate;
	}

	public void setTransferBondDate(Date transferBondDate) {
		this.transferBondDate = transferBondDate;
	}

	public Date getTransferBondDateOld() {
		return transferBondDateOld;
	}

	public void setTransferBondDateOld(Date transferBondDateOld) {
		this.transferBondDateOld = transferBondDateOld;
	}

	public String getTransferBondNo() {
		return transferBondNo;
	}

	public void setTransferBondNo(String transferBondNo) {
		this.transferBondNo = transferBondNo;
	}

	public String getTransferBondNoOld() {
		return transferBondNoOld;
	}

	public void setTransferBondNoOld(String transferBondNoOld) {
		this.transferBondNoOld = transferBondNoOld;
	}

	public String getExBondType() {
		return exBondType;
	}

	public void setExBondType(String exBondType) {
		this.exBondType = exBondType;
	}

	public String getCommonJobId() {
		return commonJobId;
	}

	public void setCommonJobId(String commonJobId) {
		this.commonJobId = commonJobId;
	}

	public CfexbondcrgEdit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CfexbondcrgEdit(Long srNo, String inBondingId, String exBondingId, String branchId, String companyId,
			String nocTransId, String approvedBy, Date approvedDate, BigDecimal balanceCif, BigDecimal balanceCifOld,
			BigDecimal balanceCargoDuty, BigDecimal balanceCargoDutyOld, BigDecimal balanceGw, BigDecimal balanceGwOld,
			BigDecimal balanceInsurance, BigDecimal balanceInsuranceOld, BigDecimal balancedPackagesNew,
			BigDecimal balancedPackagesOld, BigDecimal balancedQty, BigDecimal balancedQtyNew, Date boeDate,
			Date boeDateOld, String boeNo, String boeNoOld, Date bondValidityDate, Date bondValidityDateOld,
			Date bondingDate, Date bondingDateOld, String bondingNo, String bondingNoOld, String cha, String chaOld,
			String commodityDescription, String commodityDescriptionOld, String createdBy, Date createdDate,
			String editedBy, Date editedDate, Date exBondBeDate, String exBondBeNo, String exBondBeNoOld,
			BigDecimal exBondedCif, BigDecimal exBondedCifOld, BigDecimal exBondedCargoDuty,
			BigDecimal exBondedCargoDutyOld, BigDecimal exBondedGw, BigDecimal exBondedGwOld,
			BigDecimal exBondedInsurance, BigDecimal exBondedInsuranceOld, BigDecimal exBondedPackages,
			BigDecimal exBondedPackagesOld, Date exBondingDate, Date exBondingDateOld, Date igmDate, String igmLineNo,
			String igmNo, String importerId, String importerIdOld, String importerName, String importerNameOld,
			BigDecimal inBondedCif, BigDecimal inBondedCifOld, BigDecimal inBondedCargoDuty,
			BigDecimal inBondedCargoDutyOld, BigDecimal inBondedGw, BigDecimal inBondedGwOld,
			BigDecimal inBondedInsurance, BigDecimal inBondedInsuranceOld, BigDecimal inBondedPackages,
			BigDecimal inBondedPackagesOld, Date inBondingDate, Date inBondingDateOld, String nocNo,
			Date nocValidityDate, Date nocValidityDateOld, BigDecimal remainingCif, BigDecimal remainingCifOld,
			BigDecimal remainingCargoDuty, BigDecimal remainingCargoDutyOld, BigDecimal remainingGw,
			BigDecimal remainingGwOld, BigDecimal remainingInsurance, BigDecimal remainingInsuranceOld,
			BigDecimal remainingPackages, BigDecimal remainingPackagesOld, Date sbDate, Date sbDateOld,
			BigDecimal sbDutyNew, BigDecimal sbDutyOld, String sbNo, String sbNoOld, BigDecimal sbQtyNew,
			BigDecimal sbQtyOld, String sbUomNew, String sbUomOld, BigDecimal sbValueNew, BigDecimal sbValueOld,
			String section49, String section49Old, String section60, String section60Old, String status,
			String tranType, Date nocTransDate, Date nocDate, String importerAddress1, String importerAddress2,
			String importerAddress3, String changeOfOwnerName, String changeOfOwnerNameOld, char changeOfOwnership,
			char changeOfOwnershipOld, Date exBondBeDateOld, char ownershipChanges, char ownershipChangesOld,
			Date transferBondDate, Date transferBondDateOld, String transferBondNo, String transferBondNoOld,
			String exBondType, String commonJobId, String newChaCode,String auditId) {
		super();
		SrNo = srNo;
		this.inBondingId = inBondingId;
		this.exBondingId = exBondingId;
		this.branchId = branchId;
		this.companyId = companyId;
		this.nocTransId = nocTransId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.balanceCif = balanceCif;
		this.balanceCifOld = balanceCifOld;
		this.balanceCargoDuty = balanceCargoDuty;
		this.balanceCargoDutyOld = balanceCargoDutyOld;
		this.balanceGw = balanceGw;
		this.balanceGwOld = balanceGwOld;
		this.balanceInsurance = balanceInsurance;
		this.balanceInsuranceOld = balanceInsuranceOld;
		this.balancedPackagesNew = balancedPackagesNew;
		this.balancedPackagesOld = balancedPackagesOld;
		this.balancedQty = balancedQty;
		this.balancedQtyNew = balancedQtyNew;
		this.boeDate = boeDate;
		this.boeDateOld = boeDateOld;
		this.boeNo = boeNo;
		this.boeNoOld = boeNoOld;
		this.bondValidityDate = bondValidityDate;
		this.bondValidityDateOld = bondValidityDateOld;
		this.bondingDate = bondingDate;
		this.bondingDateOld = bondingDateOld;
		this.bondingNo = bondingNo;
		this.bondingNoOld = bondingNoOld;
		this.cha = cha;
		this.chaOld = chaOld;
		this.commodityDescription = commodityDescription;
		this.commodityDescriptionOld = commodityDescriptionOld;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.exBondBeDate = exBondBeDate;
		this.exBondBeNo = exBondBeNo;
		this.exBondBeNoOld = exBondBeNoOld;
		this.exBondedCif = exBondedCif;
		this.exBondedCifOld = exBondedCifOld;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedCargoDutyOld = exBondedCargoDutyOld;
		this.exBondedGw = exBondedGw;
		this.exBondedGwOld = exBondedGwOld;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedInsuranceOld = exBondedInsuranceOld;
		this.exBondedPackages = exBondedPackages;
		this.exBondedPackagesOld = exBondedPackagesOld;
		this.exBondingDate = exBondingDate;
		this.exBondingDateOld = exBondingDateOld;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.importerId = importerId;
		this.importerIdOld = importerIdOld;
		this.importerName = importerName;
		this.importerNameOld = importerNameOld;
		this.inBondedCif = inBondedCif;
		this.inBondedCifOld = inBondedCifOld;
		this.inBondedCargoDuty = inBondedCargoDuty;
		this.inBondedCargoDutyOld = inBondedCargoDutyOld;
		this.inBondedGw = inBondedGw;
		this.inBondedGwOld = inBondedGwOld;
		this.inBondedInsurance = inBondedInsurance;
		this.inBondedInsuranceOld = inBondedInsuranceOld;
		this.inBondedPackages = inBondedPackages;
		this.inBondedPackagesOld = inBondedPackagesOld;
		this.inBondingDate = inBondingDate;
		this.inBondingDateOld = inBondingDateOld;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.nocValidityDateOld = nocValidityDateOld;
		this.remainingCif = remainingCif;
		this.remainingCifOld = remainingCifOld;
		this.remainingCargoDuty = remainingCargoDuty;
		this.remainingCargoDutyOld = remainingCargoDutyOld;
		this.remainingGw = remainingGw;
		this.remainingGwOld = remainingGwOld;
		this.remainingInsurance = remainingInsurance;
		this.remainingInsuranceOld = remainingInsuranceOld;
		this.remainingPackages = remainingPackages;
		this.remainingPackagesOld = remainingPackagesOld;
		this.sbDate = sbDate;
		this.sbDateOld = sbDateOld;
		this.sbDutyNew = sbDutyNew;
		this.sbDutyOld = sbDutyOld;
		this.sbNo = sbNo;
		this.sbNoOld = sbNoOld;
		this.sbQtyNew = sbQtyNew;
		this.sbQtyOld = sbQtyOld;
		this.sbUomNew = sbUomNew;
		this.sbUomOld = sbUomOld;
		this.sbValueNew = sbValueNew;
		this.sbValueOld = sbValueOld;
		this.section49 = section49;
		this.section49Old = section49Old;
		this.section60 = section60;
		this.section60Old = section60Old;
		this.status = status;
		this.tranType = tranType;
		this.nocTransDate = nocTransDate;
		this.nocDate = nocDate;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.changeOfOwnerName = changeOfOwnerName;
		this.changeOfOwnerNameOld = changeOfOwnerNameOld;
		this.changeOfOwnership = changeOfOwnership;
		this.changeOfOwnershipOld = changeOfOwnershipOld;
		this.exBondBeDateOld = exBondBeDateOld;
		this.ownershipChanges = ownershipChanges;
		this.ownershipChangesOld = ownershipChangesOld;
		this.transferBondDate = transferBondDate;
		this.transferBondDateOld = transferBondDateOld;
		this.transferBondNo = transferBondNo;
		this.transferBondNoOld = transferBondNoOld;
		this.exBondType = exBondType;
		this.commonJobId = commonJobId;
		this.newChaCode = newChaCode;
		this.auditId=auditId;
	}

	@Override
	public String toString() {
		return "CfexbondcrgEdit [SrNo=" + SrNo + ", inBondingId=" + inBondingId + ", exBondingId=" + exBondingId
				+ ", branchId=" + branchId + ", companyId=" + companyId + ", nocTransId=" + nocTransId + ", approvedBy="
				+ approvedBy + ", approvedDate=" + approvedDate + ", balanceCif=" + balanceCif + ", balanceCifOld="
				+ balanceCifOld + ", balanceCargoDuty=" + balanceCargoDuty + ", balanceCargoDutyOld="
				+ balanceCargoDutyOld + ", balanceGw=" + balanceGw + ", balanceGwOld=" + balanceGwOld
				+ ", balanceInsurance=" + balanceInsurance + ", balanceInsuranceOld=" + balanceInsuranceOld
				+ ", balancedPackagesNew=" + balancedPackagesNew + ", balancedPackagesOld=" + balancedPackagesOld
				+ ", balancedQty=" + balancedQty + ", balancedQtyNew=" + balancedQtyNew + ", boeDate=" + boeDate
				+ ", boeDateOld=" + boeDateOld + ", boeNo=" + boeNo + ", boeNoOld=" + boeNoOld + ", bondValidityDate="
				+ bondValidityDate + ", bondValidityDateOld=" + bondValidityDateOld + ", bondingDate=" + bondingDate
				+ ", bondingDateOld=" + bondingDateOld + ", bondingNo=" + bondingNo + ", bondingNoOld=" + bondingNoOld
				+ ", cha=" + cha + ", chaOld=" + chaOld + ", commodityDescription=" + commodityDescription
				+ ", commodityDescriptionOld=" + commodityDescriptionOld + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", exBondBeDate="
				+ exBondBeDate + ", exBondBeNo=" + exBondBeNo + ", exBondBeNoOld=" + exBondBeNoOld + ", exBondedCif="
				+ exBondedCif + ", exBondedCifOld=" + exBondedCifOld + ", exBondedCargoDuty=" + exBondedCargoDuty
				+ ", exBondedCargoDutyOld=" + exBondedCargoDutyOld + ", exBondedGw=" + exBondedGw + ", exBondedGwOld="
				+ exBondedGwOld + ", exBondedInsurance=" + exBondedInsurance + ", exBondedInsuranceOld="
				+ exBondedInsuranceOld + ", exBondedPackages=" + exBondedPackages + ", exBondedPackagesOld="
				+ exBondedPackagesOld + ", exBondingDate=" + exBondingDate + ", exBondingDateOld=" + exBondingDateOld
				+ ", igmDate=" + igmDate + ", igmLineNo=" + igmLineNo + ", igmNo=" + igmNo + ", importerId="
				+ importerId + ", importerIdOld=" + importerIdOld + ", importerName=" + importerName
				+ ", importerNameOld=" + importerNameOld + ", inBondedCif=" + inBondedCif + ", inBondedCifOld="
				+ inBondedCifOld + ", inBondedCargoDuty=" + inBondedCargoDuty + ", inBondedCargoDutyOld="
				+ inBondedCargoDutyOld + ", inBondedGw=" + inBondedGw + ", inBondedGwOld=" + inBondedGwOld
				+ ", inBondedInsurance=" + inBondedInsurance + ", inBondedInsuranceOld=" + inBondedInsuranceOld
				+ ", inBondedPackages=" + inBondedPackages + ", inBondedPackagesOld=" + inBondedPackagesOld
				+ ", inBondingDate=" + inBondingDate + ", inBondingDateOld=" + inBondingDateOld + ", nocNo=" + nocNo
				+ ", nocValidityDate=" + nocValidityDate + ", nocValidityDateOld=" + nocValidityDateOld
				+ ", remainingCif=" + remainingCif + ", remainingCifOld=" + remainingCifOld + ", remainingCargoDuty="
				+ remainingCargoDuty + ", remainingCargoDutyOld=" + remainingCargoDutyOld + ", remainingGw="
				+ remainingGw + ", remainingGwOld=" + remainingGwOld + ", remainingInsurance=" + remainingInsurance
				+ ", remainingInsuranceOld=" + remainingInsuranceOld + ", remainingPackages=" + remainingPackages
				+ ", remainingPackagesOld=" + remainingPackagesOld + ", sbDate=" + sbDate + ", sbDateOld=" + sbDateOld
				+ ", sbDutyNew=" + sbDutyNew + ", sbDutyOld=" + sbDutyOld + ", sbNo=" + sbNo + ", sbNoOld=" + sbNoOld
				+ ", sbQtyNew=" + sbQtyNew + ", sbQtyOld=" + sbQtyOld + ", sbUomNew=" + sbUomNew + ", sbUomOld="
				+ sbUomOld + ", sbValueNew=" + sbValueNew + ", sbValueOld=" + sbValueOld + ", section49=" + section49
				+ ", section49Old=" + section49Old + ", section60=" + section60 + ", section60Old=" + section60Old
				+ ", status=" + status + ", tranType=" + tranType + ", nocTransDate=" + nocTransDate + ", nocDate="
				+ nocDate + ", importerAddress1=" + importerAddress1 + ", importerAddress2=" + importerAddress2
				+ ", importerAddress3=" + importerAddress3 + ", changeOfOwnerName=" + changeOfOwnerName
				+ ", changeOfOwnerNameOld=" + changeOfOwnerNameOld + ", changeOfOwnership=" + changeOfOwnership
				+ ", changeOfOwnershipOld=" + changeOfOwnershipOld + ", exBondBeDateOld=" + exBondBeDateOld
				+ ", ownershipChanges=" + ownershipChanges + ", ownershipChangesOld=" + ownershipChangesOld
				+ ", transferBondDate=" + transferBondDate + ", transferBondDateOld=" + transferBondDateOld
				+ ", transferBondNo=" + transferBondNo + ", transferBondNoOld=" + transferBondNoOld + ", exBondType="
				+ exBondType + ", commonJobId=" + commonJobId + ", newChaCode=" + newChaCode + "]";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// for bond audit trail screen
	public CfexbondcrgEdit(Long srNo, String inBondingId, String exBondingId, String branchId, String companyId,
			String nocTransId, String approvedBy, Date approvedDate, Date boeDate, Date boeDateOld, String boeNo,
			String boeNoOld, Date bondValidityDate, Date bondValidityDateOld, Date bondingDate, Date bondingDateOld,
			String bondingNo, String bondingNoOld, String cha, String chaOld, String commodityDescription,
			String commodityDescriptionOld, String createdBy, Date createdDate, String editedBy, Date editedDate,
			Date igmDate, String igmLineNo, String igmNo, String importerId, String importerIdOld, String importerName,
			String importerNameOld, BigDecimal inBondedCif, BigDecimal inBondedCifOld, BigDecimal inBondedCargoDuty,
			BigDecimal inBondedCargoDutyOld, BigDecimal inBondedGw, BigDecimal inBondedGwOld,
			BigDecimal inBondedInsurance, BigDecimal inBondedInsuranceOld, BigDecimal inBondedPackages,
			BigDecimal inBondedPackagesOld, Date inBondingDate, Date inBondingDateOld, String nocNo,
			Date nocValidityDate, Date nocValidityDateOld, String section49, String section49Old, String section60,
			String section60Old, String status, String tranType, Date nocTransDate, Date nocDate,
			String importerAddress1, String importerAddress2, String importerAddress3, String newChaCode,
			String newChaName,String auditId) {
		super();
		SrNo = srNo;
		this.inBondingId = inBondingId;
		this.exBondingId = exBondingId;
		this.branchId = branchId;
		this.companyId = companyId;
		this.nocTransId = nocTransId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.boeDate = boeDate;
		this.boeDateOld = boeDateOld;
		this.boeNo = boeNo;
		this.boeNoOld = boeNoOld;
		this.bondValidityDate = bondValidityDate;
		this.bondValidityDateOld = bondValidityDateOld;
		this.bondingDate = bondingDate;
		this.bondingDateOld = bondingDateOld;
		this.bondingNo = bondingNo;
		this.bondingNoOld = bondingNoOld;
		this.cha = cha;
		this.chaOld = chaOld;
		this.commodityDescription = commodityDescription;
		this.commodityDescriptionOld = commodityDescriptionOld;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.importerId = importerId;
		this.importerIdOld = importerIdOld;
		this.importerName = importerName;
		this.importerNameOld = importerNameOld;
		this.inBondedCif = inBondedCif;
		this.inBondedCifOld = inBondedCifOld;
		this.inBondedCargoDuty = inBondedCargoDuty;
		this.inBondedCargoDutyOld = inBondedCargoDutyOld;
		this.inBondedGw = inBondedGw;
		this.inBondedGwOld = inBondedGwOld;
		this.inBondedInsurance = inBondedInsurance;
		this.inBondedInsuranceOld = inBondedInsuranceOld;
		this.inBondedPackages = inBondedPackages;
		this.inBondedPackagesOld = inBondedPackagesOld;
		this.inBondingDate = inBondingDate;
		this.inBondingDateOld = inBondingDateOld;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.nocValidityDateOld = nocValidityDateOld;
		this.section49 = section49;
		this.section49Old = section49Old;
		this.section60 = section60;
		this.section60Old = section60Old;
		this.status = status;
		this.tranType = tranType;
		this.nocTransDate = nocTransDate;
		this.nocDate = nocDate;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.newChaCode = newChaCode;
		this.newChaName = newChaName;
		this.auditId=auditId;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// query constructor for the exbondAuditTrailScreen to get data after saved .
	public CfexbondcrgEdit(Long srNo, String inBondingId, String exBondingId, String branchId, String companyId,
			String nocTransId, String approvedBy, Date approvedDate, BigDecimal balanceCif, BigDecimal balanceCifOld,
			BigDecimal balanceCargoDuty, BigDecimal balanceCargoDutyOld, BigDecimal balanceGw, BigDecimal balanceGwOld,
			BigDecimal balanceInsurance, BigDecimal balanceInsuranceOld, BigDecimal balancedPackagesNew,
			BigDecimal balancedPackagesOld, BigDecimal balancedQty, BigDecimal balancedQtyNew, Date boeDate,
			Date boeDateOld, String boeNo, String boeNoOld, Date bondValidityDate, Date bondValidityDateOld,
			Date bondingDate, Date bondingDateOld, String bondingNo, String bondingNoOld, String cha, String chaOld,
			String commodityDescription, String commodityDescriptionOld, String createdBy, Date createdDate,
			String editedBy, Date editedDate, Date exBondBeDate, String exBondBeNo, String exBondBeNoOld,
			BigDecimal exBondedCif, BigDecimal exBondedCifOld, BigDecimal exBondedCargoDuty,
			BigDecimal exBondedCargoDutyOld, BigDecimal exBondedGw, BigDecimal exBondedGwOld,
			BigDecimal exBondedInsurance, BigDecimal exBondedInsuranceOld, BigDecimal exBondedPackages,
			BigDecimal exBondedPackagesOld, Date exBondingDate, Date exBondingDateOld, Date igmDate, String igmLineNo,
			String igmNo, String importerId, String importerIdOld, String importerName, String importerNameOld,
			BigDecimal inBondedCif, BigDecimal inBondedCifOld, BigDecimal inBondedCargoDuty,
			BigDecimal inBondedCargoDutyOld, BigDecimal inBondedGw, BigDecimal inBondedGwOld,
			BigDecimal inBondedInsurance, BigDecimal inBondedInsuranceOld, BigDecimal inBondedPackages,
			BigDecimal inBondedPackagesOld, Date inBondingDate, Date inBondingDateOld, String nocNo,
			Date nocValidityDate, Date nocValidityDateOld, BigDecimal remainingCif, BigDecimal remainingCifOld,
			BigDecimal remainingCargoDuty, BigDecimal remainingCargoDutyOld, BigDecimal remainingGw,
			BigDecimal remainingGwOld, BigDecimal remainingInsurance, BigDecimal remainingInsuranceOld,
			BigDecimal remainingPackages, BigDecimal remainingPackagesOld, Date sbDate, Date sbDateOld,
			BigDecimal sbDutyNew, BigDecimal sbDutyOld, String sbNo, String sbNoOld, BigDecimal sbQtyNew,
			BigDecimal sbQtyOld, String sbUomNew, String sbUomOld, BigDecimal sbValueNew, BigDecimal sbValueOld,
			String section49, String section49Old, String section60, String section60Old, String status,
			String tranType, Date nocTransDate, Date nocDate, Date exBondBeDateOld, Date transferBondDate,
			Date transferBondDateOld, String transferBondNo, String transferBondNoOld, String exBondType,String auditId) {
		super();
		SrNo = srNo;
		this.inBondingId = inBondingId;
		this.exBondingId = exBondingId;
		this.branchId = branchId;
		this.companyId = companyId;
		this.nocTransId = nocTransId;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.balanceCif = balanceCif;
		this.balanceCifOld = balanceCifOld;
		this.balanceCargoDuty = balanceCargoDuty;
		this.balanceCargoDutyOld = balanceCargoDutyOld;
		this.balanceGw = balanceGw;
		this.balanceGwOld = balanceGwOld;
		this.balanceInsurance = balanceInsurance;
		this.balanceInsuranceOld = balanceInsuranceOld;
		this.balancedPackagesNew = balancedPackagesNew;
		this.balancedPackagesOld = balancedPackagesOld;
		this.balancedQty = balancedQty;
		this.balancedQtyNew = balancedQtyNew;
		this.boeDate = boeDate;
		this.boeDateOld = boeDateOld;
		this.boeNo = boeNo;
		this.boeNoOld = boeNoOld;
		this.bondValidityDate = bondValidityDate;
		this.bondValidityDateOld = bondValidityDateOld;
		this.bondingDate = bondingDate;
		this.bondingDateOld = bondingDateOld;
		this.bondingNo = bondingNo;
		this.bondingNoOld = bondingNoOld;
		this.cha = cha;
		this.chaOld = chaOld;
		this.commodityDescription = commodityDescription;
		this.commodityDescriptionOld = commodityDescriptionOld;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.exBondBeDate = exBondBeDate;
		this.exBondBeNo = exBondBeNo;
		this.exBondBeNoOld = exBondBeNoOld;
		this.exBondedCif = exBondedCif;
		this.exBondedCifOld = exBondedCifOld;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedCargoDutyOld = exBondedCargoDutyOld;
		this.exBondedGw = exBondedGw;
		this.exBondedGwOld = exBondedGwOld;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedInsuranceOld = exBondedInsuranceOld;
		this.exBondedPackages = exBondedPackages;
		this.exBondedPackagesOld = exBondedPackagesOld;
		this.exBondingDate = exBondingDate;
		this.exBondingDateOld = exBondingDateOld;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.importerId = importerId;
		this.importerIdOld = importerIdOld;
		this.importerName = importerName;
		this.importerNameOld = importerNameOld;
		this.inBondedCif = inBondedCif;
		this.inBondedCifOld = inBondedCifOld;
		this.inBondedCargoDuty = inBondedCargoDuty;
		this.inBondedCargoDutyOld = inBondedCargoDutyOld;
		this.inBondedGw = inBondedGw;
		this.inBondedGwOld = inBondedGwOld;
		this.inBondedInsurance = inBondedInsurance;
		this.inBondedInsuranceOld = inBondedInsuranceOld;
		this.inBondedPackages = inBondedPackages;
		this.inBondedPackagesOld = inBondedPackagesOld;
		this.inBondingDate = inBondingDate;
		this.inBondingDateOld = inBondingDateOld;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.nocValidityDateOld = nocValidityDateOld;
		this.remainingCif = remainingCif;
		this.remainingCifOld = remainingCifOld;
		this.remainingCargoDuty = remainingCargoDuty;
		this.remainingCargoDutyOld = remainingCargoDutyOld;
		this.remainingGw = remainingGw;
		this.remainingGwOld = remainingGwOld;
		this.remainingInsurance = remainingInsurance;
		this.remainingInsuranceOld = remainingInsuranceOld;
		this.remainingPackages = remainingPackages;
		this.remainingPackagesOld = remainingPackagesOld;
		this.sbDate = sbDate;
		this.sbDateOld = sbDateOld;
		this.sbDutyNew = sbDutyNew;
		this.sbDutyOld = sbDutyOld;
		this.sbNo = sbNo;
		this.sbNoOld = sbNoOld;
		this.sbQtyNew = sbQtyNew;
		this.sbQtyOld = sbQtyOld;
		this.sbUomNew = sbUomNew;
		this.sbUomOld = sbUomOld;
		this.sbValueNew = sbValueNew;
		this.sbValueOld = sbValueOld;
		this.section49 = section49;
		this.section49Old = section49Old;
		this.section60 = section60;
		this.section60Old = section60Old;
		this.status = status;
		this.tranType = tranType;
		this.nocTransDate = nocTransDate;
		this.nocDate = nocDate;
		this.exBondBeDateOld = exBondBeDateOld;
		this.transferBondDate = transferBondDate;
		this.transferBondDateOld = transferBondDateOld;
		this.transferBondNo = transferBondNo;
		this.transferBondNoOld = transferBondNoOld;
		this.exBondType = exBondType;
		this.auditId=auditId;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// query constructor for the audit trail report please check for any correction 
	public CfexbondcrgEdit(Long srNo, String inBondingId, String exBondingId, String branchId, String companyId,
			String nocTransId, Date boeDate, Date boeDateOld, String boeNo, String boeNoOld, Date bondValidityDate,
			Date bondValidityDateOld, Date bondingDate, Date bondingDateOld, String bondingNo, String bondingNoOld,
			String cha, String chaOld, String commodityDescription, String commodityDescriptionOld, Date exBondBeDate,
			String exBondBeNo, String exBondBeNoOld, BigDecimal exBondedCif, BigDecimal exBondedCifOld,
			BigDecimal exBondedCargoDuty, BigDecimal exBondedCargoDutyOld, BigDecimal exBondedGw,
			BigDecimal exBondedGwOld, BigDecimal exBondedInsurance, BigDecimal exBondedInsuranceOld,
			BigDecimal exBondedPackages, BigDecimal exBondedPackagesOld, Date exBondingDate, Date exBondingDateOld,
			String importerId, String importerIdOld, String importerName, String importerNameOld,
			BigDecimal inBondedCif, BigDecimal inBondedCifOld, BigDecimal inBondedCargoDuty,
			BigDecimal inBondedCargoDutyOld, BigDecimal inBondedGw, BigDecimal inBondedGwOld,
			BigDecimal inBondedInsurance, BigDecimal inBondedInsuranceOld, BigDecimal inBondedPackages,
			BigDecimal inBondedPackagesOld, Date inBondingDate, Date inBondingDateOld, String nocNo,
			Date nocValidityDate, Date nocValidityDateOld, Date sbDate, Date sbDateOld, BigDecimal sbDutyNew,
			BigDecimal sbDutyOld, String sbNo, String sbNoOld, BigDecimal sbQtyNew, BigDecimal sbQtyOld,
			String sbUomNew, String sbUomOld, BigDecimal sbValueNew, BigDecimal sbValueOld, String section49,
			String section49Old, String section60, String section60Old, String status, String tranType,
			Date nocTransDate, Date nocDate, Date exBondBeDateOld, Date transferBondDate, Date transferBondDateOld,
			String transferBondNo, String transferBondNoOld, String exBondType, String commonJobId, String newChaCode,
			String newChaName, BigDecimal oldInsuranceValue, String oldTypeOfPackage,
            BigDecimal newBondCargoDuty, BigDecimal newBondCifValue, BigDecimal newBondGrossWt,
            BigDecimal newBondPackages, BigDecimal newBreakage, String newCommodityDescription,
            BigDecimal newDamagedQty, BigDecimal newInsuranceValue, BigDecimal newShortagePackages,
            String newTypeOfPackage, BigDecimal oldBondCargoDuty, BigDecimal oldBondCifValue,
            BigDecimal oldBondGrossWt, BigDecimal oldBondPackages, BigDecimal oldBreakage,
            String oldCommodityDescription, BigDecimal oldDamagedQty, BigDecimal oldShortagePackages,
            String type) {
		super();
		SrNo = srNo;
		this.inBondingId = inBondingId;
		this.exBondingId = exBondingId;
		this.branchId = branchId;
		this.companyId = companyId;
		this.nocTransId = nocTransId;
		this.boeDate = boeDate;
		this.boeDateOld = boeDateOld;
		this.boeNo = boeNo;
		this.boeNoOld = boeNoOld;
		this.bondValidityDate = bondValidityDate;
		this.bondValidityDateOld = bondValidityDateOld;
		this.bondingDate = bondingDate;
		this.bondingDateOld = bondingDateOld;
		this.bondingNo = bondingNo;
		this.bondingNoOld = bondingNoOld;
		this.cha = cha;
		this.chaOld = chaOld;
		this.commodityDescription = commodityDescription;
		this.commodityDescriptionOld = commodityDescriptionOld;
		this.exBondBeDate = exBondBeDate;
		this.exBondBeNo = exBondBeNo;
		this.exBondBeNoOld = exBondBeNoOld;
		this.exBondedCif = exBondedCif;
		this.exBondedCifOld = exBondedCifOld;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedCargoDutyOld = exBondedCargoDutyOld;
		this.exBondedGw = exBondedGw;
		this.exBondedGwOld = exBondedGwOld;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedInsuranceOld = exBondedInsuranceOld;
		this.exBondedPackages = exBondedPackages;
		this.exBondedPackagesOld = exBondedPackagesOld;
		this.exBondingDate = exBondingDate;
		this.exBondingDateOld = exBondingDateOld;
		this.importerId = importerId;
		this.importerIdOld = importerIdOld;
		this.importerName = importerName;
		this.importerNameOld = importerNameOld;
		this.inBondedCif = inBondedCif;
		this.inBondedCifOld = inBondedCifOld;
		this.inBondedCargoDuty = inBondedCargoDuty;
		this.inBondedCargoDutyOld = inBondedCargoDutyOld;
		this.inBondedGw = inBondedGw;
		this.inBondedGwOld = inBondedGwOld;
		this.inBondedInsurance = inBondedInsurance;
		this.inBondedInsuranceOld = inBondedInsuranceOld;
		this.inBondedPackages = inBondedPackages;
		this.inBondedPackagesOld = inBondedPackagesOld;
		this.inBondingDate = inBondingDate;
		this.inBondingDateOld = inBondingDateOld;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.nocValidityDateOld = nocValidityDateOld;
		this.sbDate = sbDate;
		this.sbDateOld = sbDateOld;
		this.sbDutyNew = sbDutyNew;
		this.sbDutyOld = sbDutyOld;
		this.sbNo = sbNo;
		this.sbNoOld = sbNoOld;
		this.sbQtyNew = sbQtyNew;
		this.sbQtyOld = sbQtyOld;
		this.sbUomNew = sbUomNew;
		this.sbUomOld = sbUomOld;
		this.sbValueNew = sbValueNew;
		this.sbValueOld = sbValueOld;
		this.section49 = section49;
		this.section49Old = section49Old;
		this.section60 = section60;
		this.section60Old = section60Old;
		this.status = status;
		this.tranType = tranType;
		this.nocTransDate = nocTransDate;
		this.nocDate = nocDate;
		this.exBondBeDateOld = exBondBeDateOld;
		this.transferBondDate = transferBondDate;
		this.transferBondDateOld = transferBondDateOld;
		this.transferBondNo = transferBondNo;
		this.transferBondNoOld = transferBondNoOld;
		this.exBondType = exBondType;
		this.commonJobId = commonJobId;
		this.newChaCode = newChaCode;
		this.newChaName = newChaName;
		 this.oldInsuranceValue = oldInsuranceValue;
	        this.oldTypeOfPackage = oldTypeOfPackage;
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
	        this.oldBondCargoDuty = oldBondCargoDuty;
	        this.oldBondCifValue = oldBondCifValue;
	        this.oldBondGrossWt = oldBondGrossWt;
	        this.oldBondPackages = oldBondPackages;
	        this.oldBreakage = oldBreakage;
	        this.oldCommodityDescription = oldCommodityDescription;
	        this.oldDamagedQty = oldDamagedQty;
	        this.oldShortagePackages = oldShortagePackages;
	        this.type = type;
	}

}
