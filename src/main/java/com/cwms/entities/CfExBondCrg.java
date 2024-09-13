package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cfexbondcrg")
@IdClass(CfExBondCrgId.class)
public class CfExBondCrg {

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
    @Column(name = "Ex_Bonding_Id", length = 10, nullable = true)
    private String exBondingId;

    @Column(name = "Ex_Bonding_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date exBondingDate;

    @Column(name = "Profitcentre_Id", length = 6, nullable = true)
    private String profitcentreId;

    @Column(name = "NOC_Trans_Id", length = 10, nullable = true)
    private String nocTransId;

    @Column(name = "NOC_No", length = 25, nullable = true)
    private String nocNo;

    @Column(name = "NOC_Validity_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocValidityDate;

    @Column(name = "BOE_No", length = 20)
    private String boeNo;

    @Column(name = "Bonding_no", length = 25)
    private String bondingNo;

    @Column(name = "Bonding_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date bondingDate;

    @Column(name = "ExBond_BE_No", length = 20)
    private String exBondBeNo;

    @Column(name = "ExBond_BE_Date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date exBondBeDate;

    @Column(name = "In_Bonding_Id", length = 10, nullable = true)
    private String inBondingId;

    @Column(name = "In_Bonding_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date inBondingDate;

    @Column(name = "INVOICE_UPTO_DATE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date invoiceUptoDate;

    @Column(name = "IGM_No", length = 10, nullable = true)
    private String igmNo;

    @Column(name = "IGM_Line_No", length = 10, nullable = true)
    private String igmLineNo;

    
    @Column(name = "Acc_Sr_no", nullable = true)
    private int accSrNo;

    @Column(name = "On_Account_Of", length = 6, nullable = true)
    private String onAccountOf;

    @Column(name = "Cha_Sr_No", nullable = true)
    private int chaSrNo;

    @Column(name = "CHA", length = 6, nullable = true)
    private String cha;

    @Column(name = "Shift", length = 6)
    private String shift;

    @Column(name = "Commodity_Description", length = 250, nullable = true)
    private String commodityDescription;

    @Column(name = "Gross_Weight", nullable = true, precision = 12, scale = 3)
    private BigDecimal grossWeight;

    @Column(name = "In_Bonded_GW", precision = 10, scale = 3)
    private BigDecimal inBondedGw;

    @Column(name = "Ex_Bonded_GW", precision = 10, scale = 3)
    private BigDecimal exBondedGw;

    @Column(name = "Remaining_GW", precision = 10, scale = 3)
    private BigDecimal remainingGw;

    @Column(name = "Balance_GW", precision = 10, scale = 3)
    private BigDecimal balanceGw;

    @Column(name = "Number_Of_Marks", columnDefinition = "TEXT")
    private String numberOfMarks;

    @Column(name = "UOM", length = 6, nullable = true)
    private String uom;

    @Column(name = "Periodic_Bill", length = 1)
    private char periodicBill;

    @Column(name = "NOC_Packages", length = 6, nullable = true)
    private String nocPackages;

    @Column(name = "Area_Occupied", nullable = true, precision = 10, scale = 3)
    private BigDecimal areaOccupied;

    @Column(name = "Area_Released", nullable = true, precision = 10, scale = 3)
    private BigDecimal areaReleased;

    @Column(name = "Area_Balanced", nullable = true, precision = 10, scale = 3)
    private BigDecimal areaBalanced;

    @Column(name = "Area_Remaining", precision = 10, scale = 3)
    private BigDecimal areaRemaining;

    @Column(name = "In_Bonded_Packages", nullable = true, precision = 6, scale = 0)
    private BigDecimal inBondedPackages;

    @Column(name = "Ex_Bonded_Packages", nullable = true, precision = 6, scale = 0)
    private BigDecimal exBondedPackages =BigDecimal.ZERO;

    @Column(name = "Remaining_Packages", precision = 6, scale = 0)
    private BigDecimal remainingPackages;

    @Column(name = "Balanced_Qty", precision = 6, scale = 0)
    private BigDecimal balancedQty;

    @Column(name = "Balanced_Packages", nullable = true, precision = 6, scale = 0)
    private BigDecimal balancedPackages;

    @Column(name = "Qty_Taken_Out", nullable = true, precision = 8, scale = 0)
    private BigDecimal qtyTakenOut;

    @Column(name = "Space_Allocated", length = 10, nullable = true)
    private String spaceAllocated;

    @Column(name = "CIF_Value", nullable = true, precision = 15, scale = 3)
    private BigDecimal cifValue;

    @Column(name = "In_Bonded_CIF", precision = 15, scale = 3)
    private BigDecimal inBondedCif;

    @Column(name = "Ex_Bonded_CIF", precision = 15, scale = 3)
    private BigDecimal exBondedCif;

    @Column(name = "Remaining_CIF", precision = 15, scale = 3)
    private BigDecimal remainingCif;

    @Column(name = "Balance_CIF", precision = 15, scale = 3)
    private BigDecimal balanceCif;

    @Column(name = "In_Bonded_Cargo_Duty", precision = 15, scale = 3)
    private BigDecimal inBondedCargoDuty;

    @Column(name = "Ex_Bonded_Cargo_Duty", precision = 15, scale = 3)
    private BigDecimal exBondedCargoDuty;

    @Column(name = "Remaining_Cargo_Duty", precision = 15, scale = 3)
    private BigDecimal remainingCargoDuty;

    @Column(name = "Balance_Cargo_Duty", precision = 15, scale = 3)
    private BigDecimal balanceCargoDuty;

    @Column(name = "In_Bonded_Insurance", precision = 15, scale = 3)
    private BigDecimal inBondedInsurance;

    @Column(name = "Ex_Bonded_Insurance", precision = 15, scale = 3)
    private BigDecimal exBondedInsurance;

    @Column(name = "Remaining_Insurance", precision = 15, scale = 3)
    private BigDecimal remainingInsurance;

    @Column(name = "Balance_Insurance", precision = 15, scale = 3)
    private BigDecimal balanceInsurance;

    @Column(name = "CIF_Qty", nullable = true, precision = 12, scale = 3)
    private BigDecimal cifQty;

    @Column(name = "ExBond_No", length = 10)
    private String exBondNo;

    @Column(name = "ExBond_Date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date exBondDate;

    @Column(name = "No_of_20FT", length = 5, nullable = true)
    private String noOf20Ft;

    @Column(name = "No_of_40FT", length = 5, nullable = true)
    private String noOf40Ft;

    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

    @Column(name = "GI_Transporter_Status", length = 1, nullable = true)
    private String giTransporterStatus;

    @Column(name = "GI_Transporter", length = 6, nullable = true)
    private String giTransporter;

    @Column(name = "GI_Transporter_Name", length = 35, nullable = true)
    private String giTransporterName;

    @Column(name = "GI_Vehicle_No", length = 15, nullable = true)
    private String giVehicleNo;

    @Column(name = "GI_Driver_Name", length = 50, nullable = true)
    private String giDriverName;

    @Column(name = "Gate_Out_Id", length = 10, nullable = true)
    private String gateOutId;

    @Column(name = "Gate_Out_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date gateOutDate;

    @Column(name = "Gate_Out_Transporter", length = 6, nullable = true)
    private String gateOutTransporter;

    @Column(name = "Gate_Out_Vehicle_No", length = 15, nullable = true)
    private String gateOutVehicleNo;

    @Column(name = "Gate_Out_Driver_Name", length = 50, nullable = true)
    private String gateOutDriverName;

    
    
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
    // Getters and Setters for all the fields

    // Constructors

    public CfExBondCrg() {
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

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public Date getExBondingDate() {
		return exBondingDate;
	}

	public void setExBondingDate(Date exBondingDate) {
		this.exBondingDate = exBondingDate;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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

	public Date getNocValidityDate() {
		return nocValidityDate;
	}

	public void setNocValidityDate(Date nocValidityDate) {
		this.nocValidityDate = nocValidityDate;
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

	public Date getExBondBeDate() {
		return exBondBeDate;
	}

	public void setExBondBeDate(Date exBondBeDate) {
		this.exBondBeDate = exBondBeDate;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
	}

	public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}

	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
	}

	public int getAccSrNo() {
		return accSrNo;
	}

	public void setAccSrNo(int accSrNo) {
		this.accSrNo = accSrNo;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public int getChaSrNo() {
		return chaSrNo;
	}

	public void setChaSrNo(int chaSrNo) {
		this.chaSrNo = chaSrNo;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
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

	public BigDecimal getInBondedGw() {
		return inBondedGw;
	}

	public void setInBondedGw(BigDecimal inBondedGw) {
		this.inBondedGw = inBondedGw;
	}

	public BigDecimal getExBondedGw() {
		return exBondedGw;
	}

	public void setExBondedGw(BigDecimal exBondedGw) {
		this.exBondedGw = exBondedGw;
	}

	public BigDecimal getRemainingGw() {
		return remainingGw;
	}

	public void setRemainingGw(BigDecimal remainingGw) {
		this.remainingGw = remainingGw;
	}

	public BigDecimal getBalanceGw() {
		return balanceGw;
	}

	public void setBalanceGw(BigDecimal balanceGw) {
		this.balanceGw = balanceGw;
	}

	public String getNumberOfMarks() {
		return numberOfMarks;
	}

	public void setNumberOfMarks(String numberOfMarks) {
		this.numberOfMarks = numberOfMarks;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public char getPeriodicBill() {
		return periodicBill;
	}

	public void setPeriodicBill(char periodicBill) {
		this.periodicBill = periodicBill;
	}

	public String getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(String nocPackages) {
		this.nocPackages = nocPackages;
	}

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
	}

	public BigDecimal getAreaReleased() {
		return areaReleased;
	}

	public void setAreaReleased(BigDecimal areaReleased) {
		this.areaReleased = areaReleased;
	}

	public BigDecimal getAreaBalanced() {
		return areaBalanced;
	}

	public void setAreaBalanced(BigDecimal areaBalanced) {
		this.areaBalanced = areaBalanced;
	}

	public BigDecimal getAreaRemaining() {
		return areaRemaining;
	}

	public void setAreaRemaining(BigDecimal areaRemaining) {
		this.areaRemaining = areaRemaining;
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

	public BigDecimal getRemainingPackages() {
		return remainingPackages;
	}

	public void setRemainingPackages(BigDecimal remainingPackages) {
		this.remainingPackages = remainingPackages;
	}

	public BigDecimal getBalancedQty() {
		return balancedQty;
	}

	public void setBalancedQty(BigDecimal balancedQty) {
		this.balancedQty = balancedQty;
	}

	public BigDecimal getBalancedPackages() {
		return balancedPackages;
	}

	public void setBalancedPackages(BigDecimal balancedPackages) {
		this.balancedPackages = balancedPackages;
	}

	public BigDecimal getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(BigDecimal qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public String getSpaceAllocated() {
		return spaceAllocated;
	}

	public void setSpaceAllocated(String spaceAllocated) {
		this.spaceAllocated = spaceAllocated;
	}

	public BigDecimal getCifValue() {
		return cifValue;
	}

	public void setCifValue(BigDecimal cifValue) {
		this.cifValue = cifValue;
	}

	public BigDecimal getInBondedCif() {
		return inBondedCif;
	}

	public void setInBondedCif(BigDecimal inBondedCif) {
		this.inBondedCif = inBondedCif;
	}

	public BigDecimal getExBondedCif() {
		return exBondedCif;
	}

	public void setExBondedCif(BigDecimal exBondedCif) {
		this.exBondedCif = exBondedCif;
	}

	public BigDecimal getRemainingCif() {
		return remainingCif;
	}

	public void setRemainingCif(BigDecimal remainingCif) {
		this.remainingCif = remainingCif;
	}

	public BigDecimal getBalanceCif() {
		return balanceCif;
	}

	public void setBalanceCif(BigDecimal balanceCif) {
		this.balanceCif = balanceCif;
	}

	public BigDecimal getInBondedCargoDuty() {
		return inBondedCargoDuty;
	}

	public void setInBondedCargoDuty(BigDecimal inBondedCargoDuty) {
		this.inBondedCargoDuty = inBondedCargoDuty;
	}

	public BigDecimal getExBondedCargoDuty() {
		return exBondedCargoDuty;
	}

	public void setExBondedCargoDuty(BigDecimal exBondedCargoDuty) {
		this.exBondedCargoDuty = exBondedCargoDuty;
	}

	public BigDecimal getRemainingCargoDuty() {
		return remainingCargoDuty;
	}

	public void setRemainingCargoDuty(BigDecimal remainingCargoDuty) {
		this.remainingCargoDuty = remainingCargoDuty;
	}

	public BigDecimal getBalanceCargoDuty() {
		return balanceCargoDuty;
	}

	public void setBalanceCargoDuty(BigDecimal balanceCargoDuty) {
		this.balanceCargoDuty = balanceCargoDuty;
	}

	public BigDecimal getInBondedInsurance() {
		return inBondedInsurance;
	}

	public void setInBondedInsurance(BigDecimal inBondedInsurance) {
		this.inBondedInsurance = inBondedInsurance;
	}

	public BigDecimal getExBondedInsurance() {
		return exBondedInsurance;
	}

	public void setExBondedInsurance(BigDecimal exBondedInsurance) {
		this.exBondedInsurance = exBondedInsurance;
	}

	public BigDecimal getRemainingInsurance() {
		return remainingInsurance;
	}

	public void setRemainingInsurance(BigDecimal remainingInsurance) {
		this.remainingInsurance = remainingInsurance;
	}

	public BigDecimal getBalanceInsurance() {
		return balanceInsurance;
	}

	public void setBalanceInsurance(BigDecimal balanceInsurance) {
		this.balanceInsurance = balanceInsurance;
	}

	public BigDecimal getCifQty() {
		return cifQty;
	}

	public void setCifQty(BigDecimal cifQty) {
		this.cifQty = cifQty;
	}

	public String getExBondNo() {
		return exBondNo;
	}

	public void setExBondNo(String exBondNo) {
		this.exBondNo = exBondNo;
	}

	public Date getExBondDate() {
		return exBondDate;
	}

	public void setExBondDate(Date exBondDate) {
		this.exBondDate = exBondDate;
	}

	public String getNoOf20Ft() {
		return noOf20Ft;
	}

	public void setNoOf20Ft(String noOf20Ft) {
		this.noOf20Ft = noOf20Ft;
	}

	public String getNoOf40Ft() {
		return noOf40Ft;
	}

	public void setNoOf40Ft(String noOf40Ft) {
		this.noOf40Ft = noOf40Ft;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getGiTransporterStatus() {
		return giTransporterStatus;
	}

	public void setGiTransporterStatus(String giTransporterStatus) {
		this.giTransporterStatus = giTransporterStatus;
	}

	public String getGiTransporter() {
		return giTransporter;
	}

	public void setGiTransporter(String giTransporter) {
		this.giTransporter = giTransporter;
	}

	public String getGiTransporterName() {
		return giTransporterName;
	}

	public void setGiTransporterName(String giTransporterName) {
		this.giTransporterName = giTransporterName;
	}

	public String getGiVehicleNo() {
		return giVehicleNo;
	}

	public void setGiVehicleNo(String giVehicleNo) {
		this.giVehicleNo = giVehicleNo;
	}

	public String getGiDriverName() {
		return giDriverName;
	}

	public void setGiDriverName(String giDriverName) {
		this.giDriverName = giDriverName;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

	public String getGateOutTransporter() {
		return gateOutTransporter;
	}

	public void setGateOutTransporter(String gateOutTransporter) {
		this.gateOutTransporter = gateOutTransporter;
	}

	public String getGateOutVehicleNo() {
		return gateOutVehicleNo;
	}

	public void setGateOutVehicleNo(String gateOutVehicleNo) {
		this.gateOutVehicleNo = gateOutVehicleNo;
	}

	public String getGateOutDriverName() {
		return gateOutDriverName;
	}

	public void setGateOutDriverName(String gateOutDriverName) {
		this.gateOutDriverName = gateOutDriverName;
	}



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



	public CfExBondCrg(String companyId, String branchId, String finYear, String exBondingId, Date exBondingDate,
			String profitcentreId, String nocTransId, String nocNo, Date nocValidityDate, String boeNo,
			String bondingNo, Date bondingDate, String exBondBeNo, Date exBondBeDate, String inBondingId,
			Date inBondingDate, Date invoiceUptoDate, String igmNo, String igmLineNo, int accSrNo, String onAccountOf,
			int chaSrNo, String cha, String shift, String commodityDescription, BigDecimal grossWeight,
			BigDecimal inBondedGw, BigDecimal exBondedGw, BigDecimal remainingGw, BigDecimal balanceGw,
			String numberOfMarks, String uom, char periodicBill, String nocPackages, BigDecimal areaOccupied,
			BigDecimal areaReleased, BigDecimal areaBalanced, BigDecimal areaRemaining, BigDecimal inBondedPackages,
			BigDecimal exBondedPackages, BigDecimal remainingPackages, BigDecimal balancedQty,
			BigDecimal balancedPackages, BigDecimal qtyTakenOut, String spaceAllocated, BigDecimal cifValue,
			BigDecimal inBondedCif, BigDecimal exBondedCif, BigDecimal remainingCif, BigDecimal balanceCif,
			BigDecimal inBondedCargoDuty, BigDecimal exBondedCargoDuty, BigDecimal remainingCargoDuty,
			BigDecimal balanceCargoDuty, BigDecimal inBondedInsurance, BigDecimal exBondedInsurance,
			BigDecimal remainingInsurance, BigDecimal balanceInsurance, BigDecimal cifQty, String exBondNo,
			Date exBondDate, String noOf20Ft, String noOf40Ft, String comments, String giTransporterStatus,
			String giTransporter, String giTransporterName, String giVehicleNo, String giDriverName, String gateOutId,
			Date gateOutDate, String gateOutTransporter, String gateOutVehicleNo, String gateOutDriverName,
			String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.exBondingId = exBondingId;
		this.exBondingDate = exBondingDate;
		this.profitcentreId = profitcentreId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.boeNo = boeNo;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.exBondBeNo = exBondBeNo;
		this.exBondBeDate = exBondBeDate;
		this.inBondingId = inBondingId;
		this.inBondingDate = inBondingDate;
		this.invoiceUptoDate = invoiceUptoDate;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.shift = shift;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.inBondedGw = inBondedGw;
		this.exBondedGw = exBondedGw;
		this.remainingGw = remainingGw;
		this.balanceGw = balanceGw;
		this.numberOfMarks = numberOfMarks;
		this.uom = uom;
		this.periodicBill = periodicBill;
		this.nocPackages = nocPackages;
		this.areaOccupied = areaOccupied;
		this.areaReleased = areaReleased;
		this.areaBalanced = areaBalanced;
		this.areaRemaining = areaRemaining;
		this.inBondedPackages = inBondedPackages;
		this.exBondedPackages = exBondedPackages;
		this.remainingPackages = remainingPackages;
		this.balancedQty = balancedQty;
		this.balancedPackages = balancedPackages;
		this.qtyTakenOut = qtyTakenOut;
		this.spaceAllocated = spaceAllocated;
		this.cifValue = cifValue;
		this.inBondedCif = inBondedCif;
		this.exBondedCif = exBondedCif;
		this.remainingCif = remainingCif;
		this.balanceCif = balanceCif;
		this.inBondedCargoDuty = inBondedCargoDuty;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.remainingCargoDuty = remainingCargoDuty;
		this.balanceCargoDuty = balanceCargoDuty;
		this.inBondedInsurance = inBondedInsurance;
		this.exBondedInsurance = exBondedInsurance;
		this.remainingInsurance = remainingInsurance;
		this.balanceInsurance = balanceInsurance;
		this.cifQty = cifQty;
		this.exBondNo = exBondNo;
		this.exBondDate = exBondDate;
		this.noOf20Ft = noOf20Ft;
		this.noOf40Ft = noOf40Ft;
		this.comments = comments;
		this.giTransporterStatus = giTransporterStatus;
		this.giTransporter = giTransporter;
		this.giTransporterName = giTransporterName;
		this.giVehicleNo = giVehicleNo;
		this.giDriverName = giDriverName;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.gateOutTransporter = gateOutTransporter;
		this.gateOutVehicleNo = gateOutVehicleNo;
		this.gateOutDriverName = gateOutDriverName;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}



	@Override
	public String toString() {
		return "CfExBondCrg [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", exBondingId=" + exBondingId + ", exBondingDate=" + exBondingDate + ", profitcentreId="
				+ profitcentreId + ", nocTransId=" + nocTransId + ", nocNo=" + nocNo + ", nocValidityDate="
				+ nocValidityDate + ", boeNo=" + boeNo + ", bondingNo=" + bondingNo + ", bondingDate=" + bondingDate
				+ ", exBondBeNo=" + exBondBeNo + ", exBondBeDate=" + exBondBeDate + ", inBondingId=" + inBondingId
				+ ", inBondingDate=" + inBondingDate + ", invoiceUptoDate=" + invoiceUptoDate + ", igmNo=" + igmNo
				+ ", igmLineNo=" + igmLineNo + ", accSrNo=" + accSrNo + ", onAccountOf=" + onAccountOf + ", chaSrNo="
				+ chaSrNo + ", cha=" + cha + ", shift=" + shift + ", commodityDescription=" + commodityDescription
				+ ", grossWeight=" + grossWeight + ", inBondedGw=" + inBondedGw + ", exBondedGw=" + exBondedGw
				+ ", remainingGw=" + remainingGw + ", balanceGw=" + balanceGw + ", numberOfMarks=" + numberOfMarks
				+ ", uom=" + uom + ", periodicBill=" + periodicBill + ", nocPackages=" + nocPackages + ", areaOccupied="
				+ areaOccupied + ", areaReleased=" + areaReleased + ", areaBalanced=" + areaBalanced
				+ ", areaRemaining=" + areaRemaining + ", inBondedPackages=" + inBondedPackages + ", exBondedPackages="
				+ exBondedPackages + ", remainingPackages=" + remainingPackages + ", balancedQty=" + balancedQty
				+ ", balancedPackages=" + balancedPackages + ", qtyTakenOut=" + qtyTakenOut + ", spaceAllocated="
				+ spaceAllocated + ", cifValue=" + cifValue + ", inBondedCif=" + inBondedCif + ", exBondedCif="
				+ exBondedCif + ", remainingCif=" + remainingCif + ", balanceCif=" + balanceCif + ", inBondedCargoDuty="
				+ inBondedCargoDuty + ", exBondedCargoDuty=" + exBondedCargoDuty + ", remainingCargoDuty="
				+ remainingCargoDuty + ", balanceCargoDuty=" + balanceCargoDuty + ", inBondedInsurance="
				+ inBondedInsurance + ", exBondedInsurance=" + exBondedInsurance + ", remainingInsurance="
				+ remainingInsurance + ", balanceInsurance=" + balanceInsurance + ", cifQty=" + cifQty + ", exBondNo="
				+ exBondNo + ", exBondDate=" + exBondDate + ", noOf20Ft=" + noOf20Ft + ", noOf40Ft=" + noOf40Ft
				+ ", comments=" + comments + ", giTransporterStatus=" + giTransporterStatus + ", giTransporter="
				+ giTransporter + ", giTransporterName=" + giTransporterName + ", giVehicleNo=" + giVehicleNo
				+ ", giDriverName=" + giDriverName + ", gateOutId=" + gateOutId + ", gateOutDate=" + gateOutDate
				+ ", gateOutTransporter=" + gateOutTransporter + ", gateOutVehicleNo=" + gateOutVehicleNo
				+ ", gateOutDriverName=" + gateOutDriverName + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + "]";
	}


	
	
//query to get all recoord using exbondingid search 
	
	public CfExBondCrg(String companyId, String branchId, String finYear, String exBondingId, Date exBondingDate,
			String profitcentreId, String nocTransId, String nocNo, Date nocValidityDate, String boeNo,
			String bondingNo, Date bondingDate, String exBondBeNo, Date exBondBeDate, String inBondingId,
			Date inBondingDate, Date invoiceUptoDate, String igmNo, String cha, String exBondNo, Date exBondDate,
			String giTransporterName, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.exBondingId = exBondingId;
		this.exBondingDate = exBondingDate;
		this.profitcentreId = profitcentreId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.boeNo = boeNo;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.exBondBeNo = exBondBeNo;
		this.exBondBeDate = exBondBeDate;
		this.inBondingId = inBondingId;
		this.inBondingDate = inBondingDate;
		this.invoiceUptoDate = invoiceUptoDate;
		this.igmNo = igmNo;
		this.cha = cha;
		this.exBondNo = exBondNo;
		this.exBondDate = exBondDate;
		this.giTransporterName = giTransporterName;
		this.status = status;
	}



	public CfExBondCrg(String exBondingId, String inBondingId,String exBondBeNo) {
		super();
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.exBondBeNo = exBondBeNo;
	}



	
	
	
	
	
}
