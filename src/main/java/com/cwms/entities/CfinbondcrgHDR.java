package com.cwms.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cfinbondcrghdr")
@IdClass(CfinbondcrgHDRId.class)
public class CfinbondcrgHDR implements Serializable {

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
    @Column(name = "In_Bonding_HDR_Id", length = 10, nullable = true)
    private String inBondingHdrId;
    
    @Column(name = "In_Bonding_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date inBondingDate;

    @Id
    @Column(name = "Profitcentre_Id", length = 6, nullable = true)
    private String profitcentreId;

    @Id
    @Column(name = "NOC_Trans_Id", length = 10, nullable = true)
    private String nocTransId;

    @Id
    @Column(name = "NOC_No", length = 25, nullable = true)
    private String nocNo;
    
    @Column(name = "NOC_Trans_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocTransDate;

    @Column(name = "IGM_No", length = 10, nullable = true)
    private String igmNo;

    @Column(name = "IGM_Date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date igmDate;

    @Column(name = "IGM_Line_No", length = 10, nullable = true)
    private String igmLineNo;

   

    @Column(name = "NOC_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocDate;

    @Column(name = "NOC_Validity_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocValidityDate;

    @Column(name = "NOC_From_Date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocFromDate;

    @Column(name = "Shift", length = 6)
    private String shift;

    @Column(name = "Gate_In_id", length = 10)
    private String gateInId;

    @Column(name = "BOE_No", length = 20, nullable = true)
    private String boeNo;

    @Column(name = "BOE_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date boeDate;

    @Column(name = "Acc_Sr_no", nullable = true)
    private int accSrNo;

    @Column(name = "On_Account_Of", length = 6, nullable = true)
    private String onAccountOf;

    @Column(name = "Shipping_Agent", length = 6, nullable = true)
    private String shippingAgent;

    @Column(name = "Shipping_Line", length = 6, nullable = true)
    private String shippingLine;

    @Column(name = "Bonding_no", length = 25)
    private String bondingNo;

    @Column(name = "Bonding_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bondingDate;

    @Column(name = "Bond_Validity_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bondValidityDate;
    
    @Column(name = "Invoice_no", length = 16)
    private String invoiceNo;

    @Column(name = "INVOICE_UPTO_DATE", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceUptoDate;

    @Column(name = "cha_sr_no", nullable = true)
    private int chaSrNo;

    @Column(name = "CHA", length = 6, nullable = true)
    private String cha;

    @Column(name = "CHA_Code", length = 18, nullable = true)
    private String chaCode;

    @Column(name = "Billing_Party", length = 6, nullable = true)
    private String billingParty;

    @Column(name = "IGST", length = 1, nullable = true)
    private String igst;

    @Column(name = "CGST", length = 1, nullable = true)
    private String cgst;

    @Column(name = "SGST", length = 1, nullable = true)
    private String sgst;

    @Column(name = "imp_sr_no", nullable = true)
    private int impSrNo;

    @Column(name = "Importer_Id", length = 7, nullable = true)
    private String importerId;

    @Column(name = "Importer_Name", length = 60, nullable = true)
    private String importerName;

    @Column(name = "importer_address1", length = 250)
    private String importerAddress1;

    @Column(name = "importer_address2", length = 100)
    private String importerAddress2;

    @Column(name = "importer_address3", length = 100)
    private String importerAddress3;

    @Id
    @Column(name = "Number_Of_Marks", length = 150, nullable = true)
    private String numberOfMarks;

    @Column(name = "Commodity_Description", length = 250, nullable = true)
    private String commodityDescription;

    @Column(name = "Gross_Weight", precision = 12, scale = 3, nullable = true)
    private BigDecimal grossWeight;

    @Column(name = "UOM", length = 6, nullable = true)
    private String uom;

    @Column(name = "CONTAINER_NO", length = 11)
    private String containerNo;

    @Column(name = "NOC_Packages", length = 6, nullable = true)
    private String nocPackages;

    @Column(name = "Sample_Qty")
    private int sampleQty;

    @Column(name = "Area_Allocated", precision = 16, scale = 3, nullable = true)
    private BigDecimal areaAllocated;

    @Column(name = "Area_Occupied", precision = 16, scale = 3, nullable = true)
    private BigDecimal areaOccupied;

    @Column(name = "Cargo_Condition", length = 6)
    private String cargoCondition;

    @Column(name = "Gate_In_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal gateInPackages;

    @Column(name = "In_Bonded_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal inBondedPackages;

    @Column(name = "Ex_Bonded_Packages", precision = 8, scale = 0)
    private BigDecimal exBondedPackages=BigDecimal.ZERO;

    @Column(name = "To_Bonded_Packages", precision = 10, scale = 0, nullable = true)
    private BigDecimal toBondedPackages;

    @Column(name = "Space_Allocated", length = 10, nullable = true)
    private String spaceAllocated;

    @Column(name = "Section_49", length = 1, nullable = true)
    private String section49;

    @Column(name = "Container_Size", length = 6, nullable = true)
    private String containerSize;

    @Column(name = "Container_Type", length = 6, nullable = true)
    private String containerType;

    @Column(name = "Examination_Id", length = 10)
    private String examinationId;

    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

    @Column(name = "CIF_Value", precision = 16, scale = 3, nullable = true)
    private BigDecimal cifValue;

    @Column(name = "Cargo_Duty", precision = 16, scale = 3, nullable = true)
    private BigDecimal cargoDuty;

    @Column(name = "Insurance_Value", precision = 16, scale = 3, nullable = true)
    private BigDecimal insuranceValue;

    @Column(name = "Inbond_Gross_Wt", precision = 16, scale = 3)
    private BigDecimal inbondGrossWt;

    @Column(name = "Inbond_Insurance_Value", precision = 16, scale = 3)
    private BigDecimal inbondInsuranceValue;

    @Column(name = "In_Bond_20FT", length = 5, nullable = true)
    private String inBond20Ft;

    @Column(name = "In_Bond_40FT", length = 5, nullable = true)
    private String inBond40Ft;

    @Column(name = "Ex_Bond_20FT", length = 5, nullable = true)
    private String exBond20Ft;

    @Column(name = "Ex_Bond_40FT", length = 5, nullable = true)
    private String exBond40Ft;

    @Column(name = "OTL_No", length = 15, nullable = true)
    private String otlNo;

    @Column(name = "Bond_Yard", length = 20, nullable = true)
    private String bondYard;

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

    @Column(name = "Reason_For_Change", length = 150)
    private String reasonForChange;

    @Column(name = "Reason_For_Change_Details", length = 250)
    private String reasonForChangeDetails;

    @Column(name = "SyncFlag", length = 1, nullable = true)
    private String syncFlag;

    @Column(name = "Document_Status", length = 1, nullable = true)
    private String documentStatus;

    @Column(name = "Shortage_Packages")
	private BigDecimal shortagePackages;

	@Column(name = "Damaged_Qty")
	private BigDecimal damagedQty;

	@Column(name = "Breakage")
	private BigDecimal breakage;
	
	
	 @Column(name = "Ex_Bonded_Cargo_Duty", precision = 16, scale = 3)
	    private BigDecimal exBondedCargoDuty=BigDecimal.ZERO;
	 
	  @Column(name = "Ex_Bonded_Insurance", precision = 16, scale = 3)
	    private BigDecimal exBondedInsurance=BigDecimal.ZERO;
	  
	  @Column(name = "Ex_Bonded_CIF", precision = 16, scale = 3)
	    private BigDecimal exBondedCif=BigDecimal.ZERO;
	  
	  
	  @Column(name = "Ex_Bonded_GW", precision = 16, scale = 3)
	    private BigDecimal exBondedGw=BigDecimal.ZERO;

	  
	
	  
	  
	
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public BigDecimal getExBondedCif() {
		return exBondedCif;
	}

	public void setExBondedCif(BigDecimal exBondedCif) {
		this.exBondedCif = exBondedCif;
	}

	public BigDecimal getExBondedGw() {
		return exBondedGw;
	}

	public void setExBondedGw(BigDecimal exBondedGw) {
		this.exBondedGw = exBondedGw;
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


	public Date getInBondingDate() {
		return inBondingDate;
	}

	public void setInBondingDate(Date inBondingDate) {
		this.inBondingDate = inBondingDate;
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

	public Date getNocTransDate() {
		return nocTransDate;
	}

	public void setNocTransDate(Date nocTransDate) {
		this.nocTransDate = nocTransDate;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
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

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public Date getNocDate() {
		return nocDate;
	}

	public void setNocDate(Date nocDate) {
		this.nocDate = nocDate;
	}

	public Date getNocValidityDate() {
		return nocValidityDate;
	}

	public void setNocValidityDate(Date nocValidityDate) {
		this.nocValidityDate = nocValidityDate;
	}

	public Date getNocFromDate() {
		return nocFromDate;
	}

	public void setNocFromDate(Date nocFromDate) {
		this.nocFromDate = nocFromDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public Date getBoeDate() {
		return boeDate;
	}

	public void setBoeDate(Date boeDate) {
		this.boeDate = boeDate;
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

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
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

	public Date getBondValidityDate() {
		return bondValidityDate;
	}

	public void setBondValidityDate(Date bondValidityDate) {
		this.bondValidityDate = bondValidityDate;
	}

	public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}

	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
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

	public String getChaCode() {
		return chaCode;
	}

	public void setChaCode(String chaCode) {
		this.chaCode = chaCode;
	}

	public String getBillingParty() {
		return billingParty;
	}

	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public int getImpSrNo() {
		return impSrNo;
	}

	public void setImpSrNo(int impSrNo) {
		this.impSrNo = impSrNo;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
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

	public String getNumberOfMarks() {
		return numberOfMarks;
	}

	public void setNumberOfMarks(String numberOfMarks) {
		this.numberOfMarks = numberOfMarks;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(String nocPackages) {
		this.nocPackages = nocPackages;
	}

	public int getSampleQty() {
		return sampleQty;
	}

	public void setSampleQty(int sampleQty) {
		this.sampleQty = sampleQty;
	}

	public BigDecimal getAreaAllocated() {
		return areaAllocated;
	}

	public void setAreaAllocated(BigDecimal areaAllocated) {
		this.areaAllocated = areaAllocated;
	}

	public BigDecimal getAreaOccupied() {
		return areaOccupied;
	}

	public void setAreaOccupied(BigDecimal areaOccupied) {
		this.areaOccupied = areaOccupied;
	}

	public String getCargoCondition() {
		return cargoCondition;
	}

	public void setCargoCondition(String cargoCondition) {
		this.cargoCondition = cargoCondition;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
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

	public String getSpaceAllocated() {
		return spaceAllocated;
	}

	public void setSpaceAllocated(String spaceAllocated) {
		this.spaceAllocated = spaceAllocated;
	}

	public String getSection49() {
		return section49;
	}

	public void setSection49(String section49) {
		this.section49 = section49;
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

	public String getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(String examinationId) {
		this.examinationId = examinationId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getInBond20Ft() {
		return inBond20Ft;
	}

	public void setInBond20Ft(String inBond20Ft) {
		this.inBond20Ft = inBond20Ft;
	}

	public String getInBond40Ft() {
		return inBond40Ft;
	}

	public void setInBond40Ft(String inBond40Ft) {
		this.inBond40Ft = inBond40Ft;
	}

	public String getExBond20Ft() {
		return exBond20Ft;
	}

	public void setExBond20Ft(String exBond20Ft) {
		this.exBond20Ft = exBond20Ft;
	}

	public String getExBond40Ft() {
		return exBond40Ft;
	}

	public void setExBond40Ft(String exBond40Ft) {
		this.exBond40Ft = exBond40Ft;
	}

	public String getOtlNo() {
		return otlNo;
	}

	public void setOtlNo(String otlNo) {
		this.otlNo = otlNo;
	}

	public String getBondYard() {
		return bondYard;
	}

	public void setBondYard(String bondYard) {
		this.bondYard = bondYard;
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

	public String getReasonForChange() {
		return reasonForChange;
	}

	public void setReasonForChange(String reasonForChange) {
		this.reasonForChange = reasonForChange;
	}

	public String getReasonForChangeDetails() {
		return reasonForChangeDetails;
	}

	public void setReasonForChangeDetails(String reasonForChangeDetails) {
		this.reasonForChangeDetails = reasonForChangeDetails;
	}

	public String getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getInBondingHdrId() {
		return inBondingHdrId;
	}

	public void setInBondingHdrId(String inBondingHdrId) {
		this.inBondingHdrId = inBondingHdrId;
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
	
	


	public CfinbondcrgHDR(String companyId, String branchId, String finYear, String inBondingHdrId, Date inBondingDate,
			String profitcentreId, String nocTransId, String nocNo, Date nocTransDate, String igmNo, Date igmDate,
			String igmLineNo, Date nocDate, Date nocValidityDate, Date nocFromDate, String shift, String gateInId,
			String boeNo, Date boeDate, int accSrNo, String onAccountOf, String shippingAgent, String shippingLine,
			String bondingNo, Date bondingDate, Date bondValidityDate, String invoiceNo, Date invoiceUptoDate,
			int chaSrNo, String cha, String chaCode, String billingParty, String igst, String cgst, String sgst,
			int impSrNo, String importerId, String importerName, String importerAddress1, String importerAddress2,
			String importerAddress3, String numberOfMarks, String commodityDescription, BigDecimal grossWeight,
			String uom, String containerNo, String nocPackages, int sampleQty, BigDecimal areaAllocated,
			BigDecimal areaOccupied, String cargoCondition, BigDecimal gateInPackages, BigDecimal inBondedPackages,
			BigDecimal exBondedPackages, BigDecimal toBondedPackages, String spaceAllocated, String section49,
			String containerSize, String containerType, String examinationId, String comments, BigDecimal cifValue,
			BigDecimal cargoDuty, BigDecimal insuranceValue, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue,
			String inBond20Ft, String inBond40Ft, String exBond20Ft, String exBond40Ft, String otlNo, String bondYard,
			String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String reasonForChange, String reasonForChangeDetails, String syncFlag,
			String documentStatus, BigDecimal shortagePackages, BigDecimal damagedQty, BigDecimal breakage,
			BigDecimal exBondedCargoDuty, BigDecimal exBondedInsurance, BigDecimal exBondedCif, BigDecimal exBondedGw) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingHdrId = inBondingHdrId;
		this.inBondingDate = inBondingDate;
		this.profitcentreId = profitcentreId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.nocTransDate = nocTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.nocDate = nocDate;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.shift = shift;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.bondValidityDate = bondValidityDate;
		this.invoiceNo = invoiceNo;
		this.invoiceUptoDate = invoiceUptoDate;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.chaCode = chaCode;
		this.billingParty = billingParty;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.numberOfMarks = numberOfMarks;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.uom = uom;
		this.containerNo = containerNo;
		this.nocPackages = nocPackages;
		this.sampleQty = sampleQty;
		this.areaAllocated = areaAllocated;
		this.areaOccupied = areaOccupied;
		this.cargoCondition = cargoCondition;
		this.gateInPackages = gateInPackages;
		this.inBondedPackages = inBondedPackages;
		this.exBondedPackages = exBondedPackages;
		this.toBondedPackages = toBondedPackages;
		this.spaceAllocated = spaceAllocated;
		this.section49 = section49;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.examinationId = examinationId;
		this.comments = comments;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inBond20Ft = inBond20Ft;
		this.inBond40Ft = inBond40Ft;
		this.exBond20Ft = exBond20Ft;
		this.exBond40Ft = exBond40Ft;
		this.otlNo = otlNo;
		this.bondYard = bondYard;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.reasonForChange = reasonForChange;
		this.reasonForChangeDetails = reasonForChangeDetails;
		this.syncFlag = syncFlag;
		this.documentStatus = documentStatus;
		this.shortagePackages = shortagePackages;
		this.damagedQty = damagedQty;
		this.breakage = breakage;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedCif = exBondedCif;
		this.exBondedGw = exBondedGw;
	}

	public CfinbondcrgHDR(String companyId, String branchId, String finYear, String inBondingHdrId, Date inBondingDate,
			String profitcentreId, String nocTransId, String nocNo, Date nocTransDate, String igmNo, Date igmDate,
			String igmLineNo, Date nocDate, Date nocValidityDate, Date nocFromDate, String shift, String gateInId,
			String boeNo, Date boeDate, int accSrNo, String onAccountOf, String shippingAgent, String shippingLine,
			String bondingNo, Date bondingDate, Date bondValidityDate, Date invoiceUptoDate, int chaSrNo, String cha,
			String chaCode, String billingParty, String igst, String cgst, String sgst, int impSrNo, String importerId,
			String importerName, String importerAddress1, String importerAddress2, String importerAddress3,
			String numberOfMarks, String commodityDescription, BigDecimal grossWeight, String uom, String containerNo,
			String nocPackages, int sampleQty, BigDecimal areaAllocated, BigDecimal areaOccupied, String cargoCondition,
			BigDecimal gateInPackages, BigDecimal inBondedPackages, BigDecimal exBondedPackages,
			BigDecimal toBondedPackages, String spaceAllocated, String section49, String containerSize,
			String containerType, String examinationId, String comments, BigDecimal cifValue, BigDecimal cargoDuty,
			BigDecimal insuranceValue, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue, String inBond20Ft,
			String inBond40Ft, String exBond20Ft, String exBond40Ft, String otlNo, String bondYard, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String reasonForChange, String reasonForChangeDetails, String syncFlag, String documentStatus,
			BigDecimal shortagePackages, BigDecimal damagedQty, BigDecimal breakage, BigDecimal exBondedCargoDuty,
			BigDecimal exBondedInsurance, BigDecimal exBondedCif, BigDecimal exBondedGw) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingHdrId = inBondingHdrId;
		this.inBondingDate = inBondingDate;
		this.profitcentreId = profitcentreId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.nocTransDate = nocTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.nocDate = nocDate;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.shift = shift;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.bondValidityDate = bondValidityDate;
		this.invoiceUptoDate = invoiceUptoDate;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.chaCode = chaCode;
		this.billingParty = billingParty;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.numberOfMarks = numberOfMarks;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.uom = uom;
		this.containerNo = containerNo;
		this.nocPackages = nocPackages;
		this.sampleQty = sampleQty;
		this.areaAllocated = areaAllocated;
		this.areaOccupied = areaOccupied;
		this.cargoCondition = cargoCondition;
		this.gateInPackages = gateInPackages;
		this.inBondedPackages = inBondedPackages;
		this.exBondedPackages = exBondedPackages;
		this.toBondedPackages = toBondedPackages;
		this.spaceAllocated = spaceAllocated;
		this.section49 = section49;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.examinationId = examinationId;
		this.comments = comments;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inBond20Ft = inBond20Ft;
		this.inBond40Ft = inBond40Ft;
		this.exBond20Ft = exBond20Ft;
		this.exBond40Ft = exBond40Ft;
		this.otlNo = otlNo;
		this.bondYard = bondYard;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.reasonForChange = reasonForChange;
		this.reasonForChangeDetails = reasonForChangeDetails;
		this.syncFlag = syncFlag;
		this.documentStatus = documentStatus;
		this.shortagePackages = shortagePackages;
		this.damagedQty = damagedQty;
		this.breakage = breakage;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedCif = exBondedCif;
		this.exBondedGw = exBondedGw;
	}

	
	
	
	
	@Override
	public String toString() {
		return "CfinbondcrgHDR [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", inBondingHdrId=" + inBondingHdrId + ", inBondingDate=" + inBondingDate + ", profitcentreId="
				+ profitcentreId + ", nocTransId=" + nocTransId + ", nocNo=" + nocNo + ", nocTransDate=" + nocTransDate
				+ ", igmNo=" + igmNo + ", igmDate=" + igmDate + ", igmLineNo=" + igmLineNo + ", nocDate=" + nocDate
				+ ", nocValidityDate=" + nocValidityDate + ", nocFromDate=" + nocFromDate + ", shift=" + shift
				+ ", gateInId=" + gateInId + ", boeNo=" + boeNo + ", boeDate=" + boeDate + ", accSrNo=" + accSrNo
				+ ", onAccountOf=" + onAccountOf + ", shippingAgent=" + shippingAgent + ", shippingLine=" + shippingLine
				+ ", bondingNo=" + bondingNo + ", bondingDate=" + bondingDate + ", bondValidityDate=" + bondValidityDate
				+ ", invoiceUptoDate=" + invoiceUptoDate + ", chaSrNo=" + chaSrNo + ", cha=" + cha + ", chaCode="
				+ chaCode + ", billingParty=" + billingParty + ", igst=" + igst + ", cgst=" + cgst + ", sgst=" + sgst
				+ ", impSrNo=" + impSrNo + ", importerId=" + importerId + ", importerName=" + importerName
				+ ", importerAddress1=" + importerAddress1 + ", importerAddress2=" + importerAddress2
				+ ", importerAddress3=" + importerAddress3 + ", numberOfMarks=" + numberOfMarks
				+ ", commodityDescription=" + commodityDescription + ", grossWeight=" + grossWeight + ", uom=" + uom
				+ ", containerNo=" + containerNo + ", nocPackages=" + nocPackages + ", sampleQty=" + sampleQty
				+ ", areaAllocated=" + areaAllocated + ", areaOccupied=" + areaOccupied + ", cargoCondition="
				+ cargoCondition + ", gateInPackages=" + gateInPackages + ", inBondedPackages=" + inBondedPackages
				+ ", exBondedPackages=" + exBondedPackages + ", toBondedPackages=" + toBondedPackages
				+ ", spaceAllocated=" + spaceAllocated + ", section49=" + section49 + ", containerSize=" + containerSize
				+ ", containerType=" + containerType + ", examinationId=" + examinationId + ", comments=" + comments
				+ ", cifValue=" + cifValue + ", cargoDuty=" + cargoDuty + ", insuranceValue=" + insuranceValue
				+ ", inbondGrossWt=" + inbondGrossWt + ", inbondInsuranceValue=" + inbondInsuranceValue
				+ ", inBond20Ft=" + inBond20Ft + ", inBond40Ft=" + inBond40Ft + ", exBond20Ft=" + exBond20Ft
				+ ", exBond40Ft=" + exBond40Ft + ", otlNo=" + otlNo + ", bondYard=" + bondYard + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", reasonForChange=" + reasonForChange + ", reasonForChangeDetails=" + reasonForChangeDetails
				+ ", syncFlag=" + syncFlag + ", documentStatus=" + documentStatus + ", shortagePackages="
				+ shortagePackages + ", damagedQty=" + damagedQty + ", breakage=" + breakage + ", exBondedCargoDuty="
				+ exBondedCargoDuty + ", exBondedInsurance=" + exBondedInsurance + ", exBondedCif=" + exBondedCif
				+ ", exBondedGw=" + exBondedGw + "]";
	}

	public CfinbondcrgHDR() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public CfinbondcrgHDR(String companyId, String branchId, String finYear, String inBondingHdrId, Date inBondingDate,
			String profitcentreId, String nocTransId, String nocNo, Date nocTransDate, String igmNo, Date igmDate,
			String igmLineNo, Date nocDate, Date nocValidityDate, Date nocFromDate, String shift, String gateInId,
			String boeNo, Date boeDate, int accSrNo, String onAccountOf, String bondingNo, Date bondingDate,
			Date bondValidityDate, int chaSrNo, String cha, String chaCode, String importerId, String importerName,
			String importerAddress1, String importerAddress2, String importerAddress3, String numberOfMarks,
			String commodityDescription, BigDecimal grossWeight, String uom, String nocPackages,
			BigDecimal areaAllocated, BigDecimal areaOccupied, String cargoCondition, BigDecimal gateInPackages,
			BigDecimal inBondedPackages, BigDecimal exBondedPackages, BigDecimal toBondedPackages,
			String spaceAllocated, String section49, String comments, BigDecimal cifValue, BigDecimal cargoDuty,
			BigDecimal insuranceValue, BigDecimal inbondGrossWt, BigDecimal inbondInsuranceValue, String inBond20Ft,
			String inBond40Ft, String exBond20Ft, String exBond40Ft, String otlNo, String bondYard, String status,
			BigDecimal shortagePackages, BigDecimal damagedQty, BigDecimal breakage,BigDecimal exBondedCargoDuty,
			BigDecimal exBondedInsurance, BigDecimal exBondedCif, BigDecimal exBondedGw) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.inBondingHdrId = inBondingHdrId;
		this.inBondingDate = inBondingDate;
		this.profitcentreId = profitcentreId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.nocTransDate = nocTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.nocDate = nocDate;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.shift = shift;
		this.gateInId = gateInId;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.bondValidityDate = bondValidityDate;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.chaCode = chaCode;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.numberOfMarks = numberOfMarks;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.uom = uom;
		this.nocPackages = nocPackages;
		this.areaAllocated = areaAllocated;
		this.areaOccupied = areaOccupied;
		this.cargoCondition = cargoCondition;
		this.gateInPackages = gateInPackages;
		this.inBondedPackages = inBondedPackages;
		this.exBondedPackages = exBondedPackages;
		this.toBondedPackages = toBondedPackages;
		this.spaceAllocated = spaceAllocated;
		this.section49 = section49;
		this.comments = comments;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inBond20Ft = inBond20Ft;
		this.inBond40Ft = inBond40Ft;
		this.exBond20Ft = exBond20Ft;
		this.exBond40Ft = exBond40Ft;
		this.otlNo = otlNo;
		this.bondYard = bondYard;
		this.status = status;
		this.shortagePackages = shortagePackages;
		this.damagedQty = damagedQty;
		this.breakage = breakage;
		this.exBondedCargoDuty = exBondedCargoDuty;
		this.exBondedInsurance = exBondedInsurance;
		this.exBondedCif = exBondedCif;
		this.exBondedGw = exBondedGw;
	}

	
	
	
}

