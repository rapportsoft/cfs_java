package com.cwms.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cfbondnoc")
@IdClass(CfbondnocId.class)
public class Cfbondnoc {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId;

	@Id
	@Column(name = "NOC_Trans_Id", length = 10, nullable = false)
	private String nocTransId;

	@Column(name = "Profitcentre_Id", length = 6, nullable = true)
	private String profitcentreId;

	@Column(name = "NOC_Trans_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocTransDate;

	@Column(name = "NOC_No", length = 25, nullable = true)
	private String nocNo;

	@Column(name = "NOC_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocDate;

	@Column(name = "Shift", length = 6, nullable = true)
	private String shift;

	@Column(name = "Source", length = 3, nullable = true)
	private String source;

	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId;

	@Column(name = "IGM_Trans_Id", length = 10, nullable = true)
	private String igmTransId;

	@Column(name = "IGM_No", length = 10, nullable = true)
	private String igmNo;

	@Column(name = "IGM_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date igmDate;

	@Column(name = "IGM_Line_No", length = 10, nullable = true)
	private String igmLineNo;

	@Column(name = "BOE_No", length = 15, nullable = true)
	private String boeNo;

	@Column(name = "BOE_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date boeDate;

	@Column(name = "Shipping_Agent", length = 6, nullable = true)
	private String shippingAgent;

	@Column(name = "Shipping_Line", length = 6, nullable = true)
	private String shippingLine;

	@Column(name = "Cha_Sr_No", nullable = true)
	private int chaSrNo;

	@Column(name = "CHA", length = 6)
	private String cha;

	@Column(name = "CHA_Code", length = 27)
	private String chaCode;

	@Column(name = "HAZ", length = 6)
	private String haz;

	@Column(name = "Periodic_Bill", length = 1)
	private String periodicBill;

	@Column(name = "TYPE_OF_PACKAGE", length = 15)
	private String typeOfPackage;

	@Column(name = "Billing_Party", length = 6, nullable = true)
	private String billingParty;

	@Column(name = "IGST", length = 1, nullable = true)
	private String igst;

	@Column(name = "CGST", length = 1, nullable = true)
	private String cgst;

	@Column(name = "SGST", length = 1, nullable = true)
	private String sgst;

	@Column(name = "Sez", length = 1, nullable = true)
	private String sez;

	@Column(name = "Imp_Sr_No", nullable = true)
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

	@Column(name = "Acc_Sr_no", nullable = true)
	private int accSrNo;

	@Column(name = "On_Account_Of", length = 6, nullable = true)
	private String onAccountOf;

	@Column(name = "Commodity_Description", length = 250, nullable = true)
	private String commodityDescription;

	@Column(name = "commodity_Code", length = 20, nullable = true)
	private String commodityCode;

	@Column(name = "Gross_Weight", precision = 16, scale = 3, nullable = true)
	private BigDecimal grossWeight;

	@Column(name = "Sample_Qty", nullable = true)
	private Integer sampleQty;

	@Column(name = "NOC_Validity_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocValidityDate;

	@Column(name = "NOC_From_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocFromDate;

	@Column(name = "Licence_Valid_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date licenceValidDate;

	@Column(name = "Number_Of_Marks", columnDefinition = "TEXT")
	private String numberOfMarks;

	@Column(name = "UOM", length = 6, nullable = true)
	private String uom;

	@Column(name = "NOC_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal nocPackages;

	@Column(name = "Gate_In_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal gateInPackages;

	@Column(name = "Area", precision = 5, scale = 0, nullable = true)
	private BigDecimal area;

	@Column(name = "New_Area", precision = 5, scale = 0, nullable = true)
	private BigDecimal newArea;

	@Column(name = "CIF_Value", precision = 15, scale = 2)
	private BigDecimal cifValue;

	@Column(name = "IMO_CODE", length = 10, nullable = true)
	private String imoCode;

	@Column(name = "Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal cargoDuty;

	@Column(name = "Insurance_Value", precision = 15, scale = 2)
	private BigDecimal insuranceValue;

	@Column(name = "Insurance_Amt", precision = 15, scale = 2)
	private BigDecimal insuranceAmt;

	@Column(name = "Stored_Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal storedCragoDuty;

	@Column(name = "Stored_Cif_Duty", precision = 15, scale = 2)
	private BigDecimal storedCifValue;

	@Column(name = "Notice_Id", length = 10)
	private String noticeId;

	@Column(name = "Notice_Type", length = 1)
	private String noticeType;

	@Column(name = "IN_BONDED_PACKAGES", precision = 10, scale = 0)
	private BigDecimal inBondedPackages = BigDecimal.ZERO;

	@Column(name = "Notice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date noticeDate;

	@Column(name = "Comments", length = 150)
	private String comments;

	@Column(name = "Status", length = 1, nullable = true)
	private String status;

	@Column(name = "EMAIL_FLAG", length = 1, nullable = true)
	private String emailFlag;

	@Column(name = "EMAIL_DATE", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date emailDate;

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

	@Column(name = "Noc_Week", length = 10, nullable = true)
	private String nocWeek;

	@Column(name = "DCA_No", length = 10, nullable = true)
	private String dcaNo;

	@Column(name = "SAP_No", length = 10, nullable = true)
	private String sapNo;

	@Column(name = "Job_No", length = 10, nullable = true)
	private String jobNo;

	@Column(name = "Source_Port", length = 10, nullable = true)
	private String sourcePort;

	@Column(name = "No_of_20FT", length = 5, nullable = true)
	private String noOf20ft;

	@Column(name = "No_of_40FT", length = 5, nullable = true)
	private String noOf40ft;

	@Column(name = "Space_Allocated", length = 10, nullable = true)
	private String spaceAllocated;

	@Column(name = "Cargo_Source", length = 10, nullable = true)
	private String cargoSource;

	@Column(name = "Bal_CIF_Value", precision = 15, scale = 3, nullable = true)
	private BigDecimal balCifValue;

	@Column(name = "Bal_Cargo_Duty", precision = 15, scale = 3, nullable = true)
	private BigDecimal balCargoDuty;

	@Column(name = "Block_Move_Allow", length = 1, nullable = true)
	private String blockMoveAllow;

	@Column(name = "Email_id", length = 50, nullable = true)
	private String emailId;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;

	@Column(name = "Space_Type", length = 10, nullable = true)
	private String spaceType;

	@Column(name = "Gate_In_Type", length = 10, nullable = true)
	private String gateInType;

	@Column(name = "oth_party_Id", length = 10, nullable = true)
	private String othPartyId;

	@Column(name = "Invoice_Assesed", length = 1, nullable = true)
	private String invoiceAssesed;

	@Column(name = "Assesment_Id", length = 20, nullable = true)
	private String assesmentId;

	@Column(name = "Invoice_No", length = 16, nullable = true)
	private String invoiceNo;

	@Column(name = "Invoice_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceDate;

	@Column(name = "Credit_Type", length = 1, nullable = true)
	private String creditType;

	@Column(name = "Invoice_Category", length = 10, nullable = true)
	private String invoiceCategory;

	@Column(name = "Bill_Amt", precision = 12, scale = 2, nullable = true)
	private BigDecimal billAmt;

	@Column(name = "Invoice_Amt", precision = 12, scale = 2, nullable = true)
	private BigDecimal invoiceAmt;

	@Column(name = "Last_Assesment_Id", length = 20, nullable = true)
	private String lastAssesmentId;

	@Column(name = "Last_Assesment_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lastAssesmentDate;

	@Column(name = "Last_Invoice_No", length = 16, nullable = true)
	private String lastInvoiceNo;

	@Column(name = "Last_Invoice_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lastInvoiceDate;

	@Column(name = "Invoice_Upto_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceUptoDate;

	@Column(name = "Last_Invoice_Assesed", length = 1, nullable = true)
	private String lastInvoiceAssesed;

	@Column(name = "Last_Bill_Amt", precision = 12, scale = 2, nullable = true)
	private BigDecimal lastBillAmt;

	@Column(name = "Last_Invoice_Amt", precision = 12, scale = 2, nullable = true)
	private BigDecimal lastInvoiceAmt;

	@Column(name = "Last_Credit_Type", length = 16, nullable = true)
	private String lastCreditType;

	@Column(name = "New_Commodity", length = 20, nullable = true)
	private String newCommodity;

	@Column(name = "SSR_TRANS_ID", length = 20, nullable = true)
	private String ssrTransId;

	@Column(name = "Inbond_Gross_Wt", precision = 12, scale = 3)
	private BigDecimal inbondGrossWt = BigDecimal.ZERO;

	@Column(name = "Inbond_Insurance_Value", precision = 15, scale = 3)
	private BigDecimal inbondInsuranceValue = BigDecimal.ZERO;

	@Column(name = "Inbond_CIF_Value", precision = 15, scale = 2)
	private BigDecimal inbondCifValue = BigDecimal.ZERO;

	@Column(name = "Inbond_Cargo_Duty", precision = 15, scale = 2)
	private BigDecimal inbondCargoDuty = BigDecimal.ZERO;

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

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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

	public Date getNocDate() {
		return nocDate;
	}

	public void setNocDate(Date nocDate) {
		this.nocDate = nocDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getIgmTransId() {
		return igmTransId;
	}

	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
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

	public String getHaz() {
		return haz;
	}

	public void setHaz(String haz) {
		this.haz = haz;
	}

	public String getPeriodicBill() {
		return periodicBill;
	}

	public void setPeriodicBill(String periodicBill) {
		this.periodicBill = periodicBill;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
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

	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
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

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getSampleQty() {
		return sampleQty;
	}

	public void setSampleQty(Integer sampleQty) {
		this.sampleQty = sampleQty;
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

	public Date getLicenceValidDate() {
		return licenceValidDate;
	}

	public void setLicenceValidDate(Date licenceValidDate) {
		this.licenceValidDate = licenceValidDate;
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

	public BigDecimal getNocPackages() {
		return nocPackages;
	}

	public void setNocPackages(BigDecimal nocPackages) {
		this.nocPackages = nocPackages;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getNewArea() {
		return newArea;
	}

	public void setNewArea(BigDecimal newArea) {
		this.newArea = newArea;
	}

	public BigDecimal getCifValue() {
		return cifValue;
	}

	public void setCifValue(BigDecimal cifValue) {
		this.cifValue = cifValue;
	}

	public String getImoCode() {
		return imoCode;
	}

	public void setImoCode(String imoCode) {
		this.imoCode = imoCode;
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

	public BigDecimal getInsuranceAmt() {
		return insuranceAmt;
	}

	public void setInsuranceAmt(BigDecimal insuranceAmt) {
		this.insuranceAmt = insuranceAmt;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public BigDecimal getInBondedPackages() {
		return inBondedPackages;
	}

	public void setInBondedPackages(BigDecimal inBondedPackages) {
		this.inBondedPackages = inBondedPackages;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	public Date getEmailDate() {
		return emailDate;
	}

	public void setEmailDate(Date emailDate) {
		this.emailDate = emailDate;
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

	public String getNocWeek() {
		return nocWeek;
	}

	public void setNocWeek(String nocWeek) {
		this.nocWeek = nocWeek;
	}

	public String getDcaNo() {
		return dcaNo;
	}

	public void setDcaNo(String dcaNo) {
		this.dcaNo = dcaNo;
	}

	public String getSapNo() {
		return sapNo;
	}

	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(String sourcePort) {
		this.sourcePort = sourcePort;
	}

	public String getNoOf20ft() {
		return noOf20ft;
	}

	public void setNoOf20ft(String noOf20ft) {
		this.noOf20ft = noOf20ft;
	}

	public String getNoOf40ft() {
		return noOf40ft;
	}

	public void setNoOf40ft(String noOf40ft) {
		this.noOf40ft = noOf40ft;
	}

	public String getSpaceAllocated() {
		return spaceAllocated;
	}

	public void setSpaceAllocated(String spaceAllocated) {
		this.spaceAllocated = spaceAllocated;
	}

	public String getCargoSource() {
		return cargoSource;
	}

	public void setCargoSource(String cargoSource) {
		this.cargoSource = cargoSource;
	}

	public BigDecimal getBalCifValue() {
		return balCifValue;
	}

	public void setBalCifValue(BigDecimal balCifValue) {
		this.balCifValue = balCifValue;
	}

	public BigDecimal getBalCargoDuty() {
		return balCargoDuty;
	}

	public void setBalCargoDuty(BigDecimal balCargoDuty) {
		this.balCargoDuty = balCargoDuty;
	}

	public String getBlockMoveAllow() {
		return blockMoveAllow;
	}

	public void setBlockMoveAllow(String blockMoveAllow) {
		this.blockMoveAllow = blockMoveAllow;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getSpaceType() {
		return spaceType;
	}

	public void setSpaceType(String spaceType) {
		this.spaceType = spaceType;
	}

	public String getGateInType() {
		return gateInType;
	}

	public void setGateInType(String gateInType) {
		this.gateInType = gateInType;
	}

	public String getOthPartyId() {
		return othPartyId;
	}

	public void setOthPartyId(String othPartyId) {
		this.othPartyId = othPartyId;
	}

	public String getInvoiceAssesed() {
		return invoiceAssesed;
	}

	public void setInvoiceAssesed(String invoiceAssesed) {
		this.invoiceAssesed = invoiceAssesed;
	}

	public String getAssesmentId() {
		return assesmentId;
	}

	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getInvoiceCategory() {
		return invoiceCategory;
	}

	public void setInvoiceCategory(String invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}

	public BigDecimal getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(BigDecimal billAmt) {
		this.billAmt = billAmt;
	}

	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public String getLastAssesmentId() {
		return lastAssesmentId;
	}

	public void setLastAssesmentId(String lastAssesmentId) {
		this.lastAssesmentId = lastAssesmentId;
	}

	public Date getLastAssesmentDate() {
		return lastAssesmentDate;
	}

	public void setLastAssesmentDate(Date lastAssesmentDate) {
		this.lastAssesmentDate = lastAssesmentDate;
	}

	public String getLastInvoiceNo() {
		return lastInvoiceNo;
	}

	public void setLastInvoiceNo(String lastInvoiceNo) {
		this.lastInvoiceNo = lastInvoiceNo;
	}

	public Date getLastInvoiceDate() {
		return lastInvoiceDate;
	}

	public void setLastInvoiceDate(Date lastInvoiceDate) {
		this.lastInvoiceDate = lastInvoiceDate;
	}

	public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}

	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
	}

	public String getLastInvoiceAssesed() {
		return lastInvoiceAssesed;
	}

	public void setLastInvoiceAssesed(String lastInvoiceAssesed) {
		this.lastInvoiceAssesed = lastInvoiceAssesed;
	}

	public BigDecimal getLastBillAmt() {
		return lastBillAmt;
	}

	public void setLastBillAmt(BigDecimal lastBillAmt) {
		this.lastBillAmt = lastBillAmt;
	}

	public BigDecimal getLastInvoiceAmt() {
		return lastInvoiceAmt;
	}

	public void setLastInvoiceAmt(BigDecimal lastInvoiceAmt) {
		this.lastInvoiceAmt = lastInvoiceAmt;
	}

	public String getLastCreditType() {
		return lastCreditType;
	}

	public void setLastCreditType(String lastCreditType) {
		this.lastCreditType = lastCreditType;
	}

	public String getNewCommodity() {
		return newCommodity;
	}

	public void setNewCommodity(String newCommodity) {
		this.newCommodity = newCommodity;
	}

	public String getSsrTransId() {
		return ssrTransId;
	}

	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}

	public Cfbondnoc() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getStoredCragoDuty() {
		return storedCragoDuty;
	}

	public void setStoredCragoDuty(BigDecimal storedCragoDuty) {
		this.storedCragoDuty = storedCragoDuty;
	}

	public BigDecimal getStoredCifValue() {
		return storedCifValue;
	}

	public void setStoredCifValue(BigDecimal storedCifValue) {
		this.storedCifValue = storedCifValue;
	}

	public Cfbondnoc(String companyId, String branchId, String nocTransId, String profitcentreId, Date nocTransDate,
			String nocNo, Date nocDate, String shift, String source, String gateInId, String igmTransId, String igmNo,
			Date igmDate, String igmLineNo, String boeNo, Date boeDate, String shippingAgent, String shippingLine,
			int chaSrNo, String cha, String chaCode, String haz, String periodicBill, String typeOfPackage,
			String billingParty, String igst, String cgst, String sgst, String sez, int impSrNo, String importerId,
			String importerName, String importerAddress1, String importerAddress2, String importerAddress3, int accSrNo,
			String onAccountOf, String commodityDescription, String commodityCode, BigDecimal grossWeight,
			Integer sampleQty, Date nocValidityDate, Date nocFromDate, Date licenceValidDate, String numberOfMarks,
			String uom, BigDecimal nocPackages, BigDecimal gateInPackages, BigDecimal area, BigDecimal newArea,
			BigDecimal cifValue, String imoCode, BigDecimal cargoDuty, BigDecimal insuranceValue,
			BigDecimal insuranceAmt, BigDecimal storedCragoDuty, BigDecimal storedCifValue, String noticeId,
			String noticeType, BigDecimal inBondedPackages, Date noticeDate, String comments, String status,
			String emailFlag, Date emailDate, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, String nocWeek, String dcaNo, String sapNo, String jobNo, String sourcePort,
			String noOf20ft, String noOf40ft, String spaceAllocated, String cargoSource, BigDecimal balCifValue,
			BigDecimal balCargoDuty, String blockMoveAllow, String emailId, Date approvedDate, String spaceType,
			String gateInType, String othPartyId, String invoiceAssesed, String assesmentId, String invoiceNo,
			Date invoiceDate, String creditType, String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt,
			String lastAssesmentId, Date lastAssesmentDate, String lastInvoiceNo, Date lastInvoiceDate,
			Date invoiceUptoDate, String lastInvoiceAssesed, BigDecimal lastBillAmt, BigDecimal lastInvoiceAmt,
			String lastCreditType, String newCommodity, String ssrTransId, BigDecimal inbondGrossWt,
			BigDecimal inbondInsuranceValue, BigDecimal inbondCifValue, BigDecimal inbondCargoDuty, String bondingNo,
			Date bondingDate, Date bondValidityDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.profitcentreId = profitcentreId;
		this.nocTransDate = nocTransDate;
		this.nocNo = nocNo;
		this.nocDate = nocDate;
		this.shift = shift;
		this.source = source;
		this.gateInId = gateInId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.chaCode = chaCode;
		this.haz = haz;
		this.periodicBill = periodicBill;
		this.typeOfPackage = typeOfPackage;
		this.billingParty = billingParty;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
		this.sez = sez;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.commodityDescription = commodityDescription;
		this.commodityCode = commodityCode;
		this.grossWeight = grossWeight;
		this.sampleQty = sampleQty;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.licenceValidDate = licenceValidDate;
		this.numberOfMarks = numberOfMarks;
		this.uom = uom;
		this.nocPackages = nocPackages;
		this.gateInPackages = gateInPackages;
		this.area = area;
		this.newArea = newArea;
		this.cifValue = cifValue;
		this.imoCode = imoCode;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.insuranceAmt = insuranceAmt;
		this.storedCragoDuty = storedCragoDuty;
		this.storedCifValue = storedCifValue;
		this.noticeId = noticeId;
		this.noticeType = noticeType;
		this.inBondedPackages = inBondedPackages;
		this.noticeDate = noticeDate;
		this.comments = comments;
		this.status = status;
		this.emailFlag = emailFlag;
		this.emailDate = emailDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.nocWeek = nocWeek;
		this.dcaNo = dcaNo;
		this.sapNo = sapNo;
		this.jobNo = jobNo;
		this.sourcePort = sourcePort;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.spaceAllocated = spaceAllocated;
		this.cargoSource = cargoSource;
		this.balCifValue = balCifValue;
		this.balCargoDuty = balCargoDuty;
		this.blockMoveAllow = blockMoveAllow;
		this.emailId = emailId;
		this.approvedDate = approvedDate;
		this.spaceType = spaceType;
		this.gateInType = gateInType;
		this.othPartyId = othPartyId;
		this.invoiceAssesed = invoiceAssesed;
		this.assesmentId = assesmentId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.creditType = creditType;
		this.invoiceCategory = invoiceCategory;
		this.billAmt = billAmt;
		this.invoiceAmt = invoiceAmt;
		this.lastAssesmentId = lastAssesmentId;
		this.lastAssesmentDate = lastAssesmentDate;
		this.lastInvoiceNo = lastInvoiceNo;
		this.lastInvoiceDate = lastInvoiceDate;
		this.invoiceUptoDate = invoiceUptoDate;
		this.lastInvoiceAssesed = lastInvoiceAssesed;
		this.lastBillAmt = lastBillAmt;
		this.lastInvoiceAmt = lastInvoiceAmt;
		this.lastCreditType = lastCreditType;
		this.newCommodity = newCommodity;
		this.ssrTransId = ssrTransId;
		this.inbondGrossWt = inbondGrossWt;
		this.inbondInsuranceValue = inbondInsuranceValue;
		this.inbondCifValue = inbondCifValue;
		this.inbondCargoDuty = inbondCargoDuty;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.bondValidityDate = bondValidityDate;
	}

	@Override
	public String toString() {
		return "Cfbondnoc [companyId=" + companyId + ", branchId=" + branchId + ", nocTransId=" + nocTransId
				+ ", profitcentreId=" + profitcentreId + ", nocTransDate=" + nocTransDate + ", nocNo=" + nocNo
				+ ", nocDate=" + nocDate + ", shift=" + shift + ", source=" + source + ", gateInId=" + gateInId
				+ ", igmTransId=" + igmTransId + ", igmNo=" + igmNo + ", igmDate=" + igmDate + ", igmLineNo="
				+ igmLineNo + ", boeNo=" + boeNo + ", boeDate=" + boeDate + ", shippingAgent=" + shippingAgent
				+ ", shippingLine=" + shippingLine + ", chaSrNo=" + chaSrNo + ", cha=" + cha + ", chaCode=" + chaCode
				+ ", haz=" + haz + ", periodicBill=" + periodicBill + ", typeOfPackage=" + typeOfPackage
				+ ", billingParty=" + billingParty + ", igst=" + igst + ", cgst=" + cgst + ", sgst=" + sgst + ", sez="
				+ sez + ", impSrNo=" + impSrNo + ", importerId=" + importerId + ", importerName=" + importerName
				+ ", importerAddress1=" + importerAddress1 + ", importerAddress2=" + importerAddress2
				+ ", importerAddress3=" + importerAddress3 + ", accSrNo=" + accSrNo + ", onAccountOf=" + onAccountOf
				+ ", commodityDescription=" + commodityDescription + ", commodityCode=" + commodityCode
				+ ", grossWeight=" + grossWeight + ", sampleQty=" + sampleQty + ", nocValidityDate=" + nocValidityDate
				+ ", nocFromDate=" + nocFromDate + ", licenceValidDate=" + licenceValidDate + ", numberOfMarks="
				+ numberOfMarks + ", uom=" + uom + ", nocPackages=" + nocPackages + ", gateInPackages=" + gateInPackages
				+ ", area=" + area + ", newArea=" + newArea + ", cifValue=" + cifValue + ", imoCode=" + imoCode
				+ ", cargoDuty=" + cargoDuty + ", insuranceValue=" + insuranceValue + ", insuranceAmt=" + insuranceAmt
				+ ", storedCragoDuty=" + storedCragoDuty + ", storedCifValue=" + storedCifValue + ", noticeId="
				+ noticeId + ", noticeType=" + noticeType + ", inBondedPackages=" + inBondedPackages + ", noticeDate="
				+ noticeDate + ", comments=" + comments + ", status=" + status + ", emailFlag=" + emailFlag
				+ ", emailDate=" + emailDate + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", nocWeek="
				+ nocWeek + ", dcaNo=" + dcaNo + ", sapNo=" + sapNo + ", jobNo=" + jobNo + ", sourcePort=" + sourcePort
				+ ", noOf20ft=" + noOf20ft + ", noOf40ft=" + noOf40ft + ", spaceAllocated=" + spaceAllocated
				+ ", cargoSource=" + cargoSource + ", balCifValue=" + balCifValue + ", balCargoDuty=" + balCargoDuty
				+ ", blockMoveAllow=" + blockMoveAllow + ", emailId=" + emailId + ", approvedDate=" + approvedDate
				+ ", spaceType=" + spaceType + ", gateInType=" + gateInType + ", othPartyId=" + othPartyId
				+ ", invoiceAssesed=" + invoiceAssesed + ", assesmentId=" + assesmentId + ", invoiceNo=" + invoiceNo
				+ ", invoiceDate=" + invoiceDate + ", creditType=" + creditType + ", invoiceCategory=" + invoiceCategory
				+ ", billAmt=" + billAmt + ", invoiceAmt=" + invoiceAmt + ", lastAssesmentId=" + lastAssesmentId
				+ ", lastAssesmentDate=" + lastAssesmentDate + ", lastInvoiceNo=" + lastInvoiceNo + ", lastInvoiceDate="
				+ lastInvoiceDate + ", invoiceUptoDate=" + invoiceUptoDate + ", lastInvoiceAssesed="
				+ lastInvoiceAssesed + ", lastBillAmt=" + lastBillAmt + ", lastInvoiceAmt=" + lastInvoiceAmt
				+ ", lastCreditType=" + lastCreditType + ", newCommodity=" + newCommodity + ", ssrTransId=" + ssrTransId
				+ ", inbondGrossWt=" + inbondGrossWt + ", inbondInsuranceValue=" + inbondInsuranceValue
				+ ", inbondCifValue=" + inbondCifValue + ", inbondCargoDuty=" + inbondCargoDuty + ", bondingNo="
				+ bondingNo + ", bondingDate=" + bondingDate + ", bondValidityDate=" + bondValidityDate + "]";
	}

	public Cfbondnoc(String companyId, String branchId, String nocTransId, Date nocTransDate, String profitcentreId,
			String nocNo, Date nocDate, String source, String gateInId, String igmTransId, String igmNo, Date igmDate,
			String igmLineNo, String boeNo, Date boeDate, String importerId, String importerName,
			String importerAddress1, String importerAddress2, String importerAddress3, BigDecimal grossWeight,
			Date nocValidityDate, Date nocFromDate, Date licenceValidDate, String uom, BigDecimal nocPackages,
			BigDecimal area, BigDecimal cifValue, BigDecimal cargoDuty, BigDecimal insuranceValue,
			BigDecimal insuranceAmt, String status, String createdBy,String editedBy, String approvedBy, String cha, String chaCode,
			String nocWeek, BigDecimal gateInPackages,String noOf20ft, String noOf40ft,String numberOfMarks) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocTransDate = nocTransDate;
		this.profitcentreId = profitcentreId;
		this.nocNo = nocNo;
		this.nocDate = nocDate;
		this.source = source;
		this.gateInId = gateInId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.grossWeight = grossWeight;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.licenceValidDate = licenceValidDate;
		this.uom = uom;
		this.nocPackages = nocPackages;
		this.area = area;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.insuranceAmt = insuranceAmt;
		this.status = status;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.cha = cha;
		this.chaCode = chaCode;
		this.nocWeek = nocWeek;
		this.gateInPackages = gateInPackages;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.numberOfMarks = numberOfMarks;
	}

	// constructor for NOC Certificate

	public Cfbondnoc(String companyId, String branchId, String nocTransId, Date nocTransDate, String nocNo,
			Date nocDate, String source, String igmTransId, String igmNo, Date igmDate, String igmLineNo, String boeNo,
			Date boeDate, String cha, String chaCode, String haz, String typeOfPackage, String importerName,
			String importerAddress1, String importerAddress2, String importerAddress3, String commodityDescription,
			BigDecimal grossWeight, Date nocValidityDate, Date nocFromDate, String uom, BigDecimal nocPackages,
			BigDecimal cifValue, BigDecimal cargoDuty, BigDecimal insuranceValue, BigDecimal storedCragoDuty,
			BigDecimal storedCifValue, String status, String nocWeek, String noOf20ft, String noOf40ft,
			String spaceType, String gateInType, BigDecimal area) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocTransDate = nocTransDate;
		this.nocNo = nocNo;
		this.nocDate = nocDate;
		this.source = source;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.cha = cha;
		this.chaCode = chaCode;
		this.haz = haz;
		this.typeOfPackage = typeOfPackage;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.commodityDescription = commodityDescription;
		this.grossWeight = grossWeight;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.uom = uom;
		this.nocPackages = nocPackages;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.storedCragoDuty = storedCragoDuty;
		this.storedCifValue = storedCifValue;
		this.status = status;
		this.nocWeek = nocWeek;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.spaceType = spaceType;
		this.gateInType = gateInType;
		this.area = area;
	}

	// for Gate In Bonded Cargo

	public Cfbondnoc(String companyId, String branchId, String nocTransId, Date nocTransDate, String profitcentreId,
			String nocNo, Date nocDate, String source, String gateInId, String igmTransId, String igmNo, Date igmDate,
			String igmLineNo, String boeNo, Date boeDate, String importerId, String importerName,
			String importerAddress1, String importerAddress2, String importerAddress3, BigDecimal grossWeight,
			Date nocValidityDate, Date nocFromDate, Date licenceValidDate, String uom, BigDecimal nocPackages,
			BigDecimal area, BigDecimal cifValue, BigDecimal cargoDuty, BigDecimal insuranceValue,
			BigDecimal insuranceAmt, String status, String createdBy, String approvedBy, String cha,
			String commodityDescription, BigDecimal gateInPackages, String typeOfPackage) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocTransDate = nocTransDate;
		this.profitcentreId = profitcentreId;
		this.nocNo = nocNo;
		this.nocDate = nocDate;
		this.source = source;
		this.gateInId = gateInId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.grossWeight = grossWeight;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.licenceValidDate = licenceValidDate;
		this.uom = uom;
		this.nocPackages = nocPackages;
		this.area = area;
		this.cifValue = cifValue;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.insuranceAmt = insuranceAmt;
		this.status = status;
		this.createdBy = createdBy;
		this.approvedBy = approvedBy;
		this.cha = cha;
		this.commodityDescription = commodityDescription;
		this.gateInPackages = gateInPackages;
		this.typeOfPackage = typeOfPackage;

	}

	// constrictor for getting data of noctrasnid for in bond

	public Cfbondnoc(String companyId, String branchId, String nocTransId, String profitcentreId, Date nocTransDate,
			String nocNo, Date nocDate, String shift, String source, String gateInId, String igmTransId, String igmNo,
			Date igmDate, String igmLineNo, String boeNo, Date boeDate, String shippingAgent, String shippingLine,
			int chaSrNo, String cha, String chaCode, String haz, String periodicBill, String typeOfPackage,
			String billingParty, String importerId, String importerName, String importerAddress1,
			String importerAddress2, String importerAddress3, int accSrNo, String onAccountOf,
			String commodityDescription, String commodityCode, BigDecimal grossWeight, Integer sampleQty,
			Date nocValidityDate, Date nocFromDate, Date licenceValidDate, String numberOfMarks, String uom,
			BigDecimal nocPackages, BigDecimal gateInPackages, BigDecimal area, BigDecimal newArea, BigDecimal cifValue,
			String imoCode, BigDecimal cargoDuty, BigDecimal insuranceValue, BigDecimal insuranceAmt,
			BigDecimal storedCragoDuty, BigDecimal storedCifValue, BigDecimal inBondedPackages, Date noticeDate,
			String comments, String status, String createdBy, String editedBy, String approvedBy, String nocWeek,
			String dcaNo, String sapNo, String jobNo, String sourcePort, String noOf20ft, String noOf40ft,
			String spaceAllocated, String cargoSource, BigDecimal balCifValue, String spaceType, String gateInType,String bondingNo ,Date bondingDate,Date bondValidityDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.profitcentreId = profitcentreId;
		this.nocTransDate = nocTransDate;
		this.nocNo = nocNo;
		this.nocDate = nocDate;
		this.shift = shift;
		this.source = source;
		this.gateInId = gateInId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.chaCode = chaCode;
		this.haz = haz;
		this.periodicBill = periodicBill;
		this.typeOfPackage = typeOfPackage;
		this.billingParty = billingParty;
		this.importerId = importerId;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.commodityDescription = commodityDescription;
		this.commodityCode = commodityCode;
		this.grossWeight = grossWeight;
		this.sampleQty = sampleQty;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.licenceValidDate = licenceValidDate;
		this.numberOfMarks = numberOfMarks;
		this.uom = uom;
		this.nocPackages = nocPackages;
		this.gateInPackages = gateInPackages;
		this.area = area;
		this.newArea = newArea;
		this.cifValue = cifValue;
		this.imoCode = imoCode;
		this.cargoDuty = cargoDuty;
		this.insuranceValue = insuranceValue;
		this.insuranceAmt = insuranceAmt;
		this.storedCragoDuty = storedCragoDuty;
		this.storedCifValue = storedCifValue;
		this.inBondedPackages = inBondedPackages;
		this.noticeDate = noticeDate;
		this.comments = comments;
		this.status = status;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.nocWeek = nocWeek;
		this.dcaNo = dcaNo;
		this.sapNo = sapNo;
		this.jobNo = jobNo;
		this.sourcePort = sourcePort;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.spaceAllocated = spaceAllocated;
		this.cargoSource = cargoSource;
		this.balCifValue = balCifValue;
		this.spaceType = spaceType;
		this.gateInType = gateInType;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.bondValidityDate = bondValidityDate;
	}
	
	
	
	
	
	//cfbond main search

	public Cfbondnoc(String companyId, String branchId, String nocTransId, String nocNo, String gateInId, String igmNo,
			String boeNo, Date boeDate, String cha, String importerId, String importerName, String bondingNo,
			Date bondingDate, Date bondValidityDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.gateInId = gateInId;
		this.igmNo = igmNo;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.cha = cha;
		this.importerId = importerId;
		this.importerName = importerName;
		this.bondingNo = bondingNo;
		this.bondingDate = bondingDate;
		this.bondValidityDate = bondValidityDate;
	}
	
	
	
	

}
