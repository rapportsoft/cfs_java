package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfinvsrvanxback")
@IdClass(CfinvsrvanxbackId.class)
public class Cfinvsrvanxback {
	
	   @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Process_Trans_Id", length = 10)
	    private String processTransId;

	    @Id
	    @Column(name = "Service_Id", length = 6)
	    private String serviceId;

	    @Id
	    @Column(name = "Tax_Id", length = 6)
	    private String taxId;

	    @Id
	    @Column(name = "ERP_Doc_Ref_No", length = 10)
	    private String erpDocRefNo;

	    @Id
	    @Column(name = "Srl_No", precision = 3, scale = 0)
	    private BigDecimal srlNo;

	    @Column(name = "Doc_Ref_No", length = 25)
	    private String docRefNo;

	    @Column(name = "IGM_Line_No", length = 7)
	    private String igmLineNo;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitCentreId;

	    @Column(name = "Invoice_No", length = 16)
	    private String invoiceNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Invoice_Date")
	    private Date invoiceDate;

	    @Column(name = "Invoice_Type", length = 6)
	    private String invoiceType;

	    @Column(name = "Service_Unit", length = 6)
	    private String serviceUnit;

	    @Column(name = "Execution_Unit", length = 13)
	    private String executionUnit;

	    @Column(name = "service_unit1", length = 6)
	    private String serviceUnit1;

	    @Column(name = "Execution_Unit1", length = 15)
	    private String executionUnit1;

	    @Column(name = "Rate", precision = 12, scale = 3)
	    private BigDecimal rate;

	    @Column(name = "Currency_Id", length = 6)
	    private String currencyId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Period_From")
	    private Date periodFrom;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Period_To")
	    private Date periodTo;

	    @Column(name = "Foreign_Amt", precision = 12, scale = 2)
	    private BigDecimal foreignAmt;

	    @Column(name = "Ex_Rate", precision = 12, scale = 3)
	    private BigDecimal exRate;

	    @Column(name = "Local_Amt_Foreign", precision = 12, scale = 2)
	    private BigDecimal localAmtForeign;

	    @Column(name = "Local_Amt", precision = 12, scale = 2)
	    private BigDecimal localAmt;

	    @Column(name = "Tax_Perc", precision = 7, scale = 3)
	    private BigDecimal taxPerc;

	    @Column(name = "Disc_Days", precision = 5, scale = 0)
	    private BigDecimal discDays;

	    @Column(name = "Disc_Percentage", precision = 16, scale = 3)
	    private BigDecimal discPercentage;

	    @Column(name = "Disc_Value", precision = 16, scale = 3)
	    private BigDecimal discValue;

	    @Column(name = "Invoice_Amt", precision = 12, scale = 2)
	    private BigDecimal invoiceAmt;

	    @Column(name = "Contractor", length = 6)
	    private String contractor;

	    @Column(name = "Ac_Code", length = 10)
	    private String acCode;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Process_Trans_Date")
	    private Date processTransDate;

	    @Column(name = "Process_Id", length = 6)
	    private String processId;

	    @Column(name = "Party_Id", length = 6)
	    private String partyId;

	    @Column(name = "WO_No", length = 10)
	    private String woNo;

	    @Column(name = "WO_Amnd_No", length = 3)
	    private String woAmndNo;

	    @Column(name = "Line_No", length = 3)
	    private String lineNo;

	    @Column(name = "BE_No", length = 20)
	    private String beNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "BE_Date")
	    private Date beDate;

	    @Column(name = "Criteria", length = 4)
	    private String criteria;

	    @Column(name = "Range_From", precision = 10, scale = 3)
	    private BigDecimal rangeFrom;

	    @Column(name = "Range_To", precision = 10, scale = 3)
	    private BigDecimal rangeTo;

	    @Column(name = "Range_Type", length = 5)
	    private String rangeType;

	    @Column(name = "Negeotiable", length = 3)
	    private String negotiable;

	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Container_Status", length = 3)
	    private String containerStatus;

	    @Column(name = "Commodity_Description", length = 250)
	    private String commodityDescription;

	    @Column(name = "Actual_No_Of_Packages", precision = 12, scale = 2)
	    private BigDecimal actualNoOfPackages;

	    @Column(name = "Gate_Out_Id", length = 10)
	    private String gateOutId;

	    @Column(name = "Gate_Pass_No", length = 10)
	    private String gatePassNo;

	    @Column(name = "Add_Services", length = 1)
	    private String addServices;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Gate_Out_Date")
	    private Date gateOutDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Start_Date")
	    private Date startDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Invoice_Upto_Date")
	    private Date invoiceUptoDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Invoice_Upto_Week")
	    private Date invoiceUptoWeek;

	    @Column(name = "DebitNote_Exe", precision = 10, scale = 2)
	    private BigDecimal debitNoteExe;

	    @Column(name = "DebitNote_Amt", precision = 10, scale = 3)
	    private BigDecimal debitNoteAmt;

	    @Column(name = "CFS_Base_Rate", precision = 5, scale = 0)
	    private BigDecimal cfsBaseRate;

	    @Column(name = "Party_Base_Rate", precision = 5, scale = 0)
	    private BigDecimal partyBaseRate;

	    @Column(name = "Rebate", precision = 10, scale = 0)
	    private BigDecimal rebate;

	    @Column(name = "Profitability", precision = 5, scale = 0)
	    private BigDecimal profitability;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Created_Date")
	    private Date createdDate;

	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Edited_Date")
	    private Date editedDate;

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Approved_Date")
	    private Date approvedDate;

	    @Column(name = "Bill_Amt", precision = 12, scale = 2)
	    private BigDecimal billAmt;

	    @Column(name = "JO_Service_Id", length = 100)
	    private String joServiceId;

	    @Column(name = "JO_No", length = 10)
	    private String joNo;

	    @Column(name = "JO_Amnd_No", length = 3)
	    private String joAmndNo;

	    @Column(name = "Tax_App", length = 1)
	    private String taxApp;

	    @Column(name = "Tax_Id_N", length = 10)
	    private String taxIdN;

	    @Column(name = "Tax_Perc_N", precision = 16, scale = 3)
	    private BigDecimal taxPercN;

	    @Column(name = "Tax_Amt", precision = 16, scale = 3)
	    private BigDecimal taxAmt;

	    @Column(name = "Ac_Code_N", length = 20)
	    private String acCodeN;

		public Cfinvsrvanxback() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Cfinvsrvanxback(String companyId, String branchId, String processTransId, String serviceId, String taxId,
				String erpDocRefNo, BigDecimal srlNo, String docRefNo, String igmLineNo, String profitCentreId,
				String invoiceNo, Date invoiceDate, String invoiceType, String serviceUnit, String executionUnit,
				String serviceUnit1, String executionUnit1, BigDecimal rate, String currencyId, Date periodFrom,
				Date periodTo, BigDecimal foreignAmt, BigDecimal exRate, BigDecimal localAmtForeign,
				BigDecimal localAmt, BigDecimal taxPerc, BigDecimal discDays, BigDecimal discPercentage,
				BigDecimal discValue, BigDecimal invoiceAmt, String contractor, String acCode, Date processTransDate,
				String processId, String partyId, String woNo, String woAmndNo, String lineNo, String beNo, Date beDate,
				String criteria, BigDecimal rangeFrom, BigDecimal rangeTo, String rangeType, String negotiable,
				String containerNo, String containerStatus, String commodityDescription, BigDecimal actualNoOfPackages,
				String gateOutId, String gatePassNo, String addServices, Date gateOutDate, Date startDate,
				Date invoiceUptoDate, Date invoiceUptoWeek, BigDecimal debitNoteExe, BigDecimal debitNoteAmt,
				BigDecimal cfsBaseRate, BigDecimal partyBaseRate, BigDecimal rebate, BigDecimal profitability,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, BigDecimal billAmt, String joServiceId, String joNo, String joAmndNo, String taxApp,
				String taxIdN, BigDecimal taxPercN, BigDecimal taxAmt, String acCodeN) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.processTransId = processTransId;
			this.serviceId = serviceId;
			this.taxId = taxId;
			this.erpDocRefNo = erpDocRefNo;
			this.srlNo = srlNo;
			this.docRefNo = docRefNo;
			this.igmLineNo = igmLineNo;
			this.profitCentreId = profitCentreId;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.invoiceType = invoiceType;
			this.serviceUnit = serviceUnit;
			this.executionUnit = executionUnit;
			this.serviceUnit1 = serviceUnit1;
			this.executionUnit1 = executionUnit1;
			this.rate = rate;
			this.currencyId = currencyId;
			this.periodFrom = periodFrom;
			this.periodTo = periodTo;
			this.foreignAmt = foreignAmt;
			this.exRate = exRate;
			this.localAmtForeign = localAmtForeign;
			this.localAmt = localAmt;
			this.taxPerc = taxPerc;
			this.discDays = discDays;
			this.discPercentage = discPercentage;
			this.discValue = discValue;
			this.invoiceAmt = invoiceAmt;
			this.contractor = contractor;
			this.acCode = acCode;
			this.processTransDate = processTransDate;
			this.processId = processId;
			this.partyId = partyId;
			this.woNo = woNo;
			this.woAmndNo = woAmndNo;
			this.lineNo = lineNo;
			this.beNo = beNo;
			this.beDate = beDate;
			this.criteria = criteria;
			this.rangeFrom = rangeFrom;
			this.rangeTo = rangeTo;
			this.rangeType = rangeType;
			this.negotiable = negotiable;
			this.containerNo = containerNo;
			this.containerStatus = containerStatus;
			this.commodityDescription = commodityDescription;
			this.actualNoOfPackages = actualNoOfPackages;
			this.gateOutId = gateOutId;
			this.gatePassNo = gatePassNo;
			this.addServices = addServices;
			this.gateOutDate = gateOutDate;
			this.startDate = startDate;
			this.invoiceUptoDate = invoiceUptoDate;
			this.invoiceUptoWeek = invoiceUptoWeek;
			this.debitNoteExe = debitNoteExe;
			this.debitNoteAmt = debitNoteAmt;
			this.cfsBaseRate = cfsBaseRate;
			this.partyBaseRate = partyBaseRate;
			this.rebate = rebate;
			this.profitability = profitability;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.billAmt = billAmt;
			this.joServiceId = joServiceId;
			this.joNo = joNo;
			this.joAmndNo = joAmndNo;
			this.taxApp = taxApp;
			this.taxIdN = taxIdN;
			this.taxPercN = taxPercN;
			this.taxAmt = taxAmt;
			this.acCodeN = acCodeN;
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

		public String getProcessTransId() {
			return processTransId;
		}

		public void setProcessTransId(String processTransId) {
			this.processTransId = processTransId;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public String getTaxId() {
			return taxId;
		}

		public void setTaxId(String taxId) {
			this.taxId = taxId;
		}

		public String getErpDocRefNo() {
			return erpDocRefNo;
		}

		public void setErpDocRefNo(String erpDocRefNo) {
			this.erpDocRefNo = erpDocRefNo;
		}

		public BigDecimal getSrlNo() {
			return srlNo;
		}

		public void setSrlNo(BigDecimal srlNo) {
			this.srlNo = srlNo;
		}

		public String getDocRefNo() {
			return docRefNo;
		}

		public void setDocRefNo(String docRefNo) {
			this.docRefNo = docRefNo;
		}

		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}

		public String getProfitCentreId() {
			return profitCentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
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

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getServiceUnit() {
			return serviceUnit;
		}

		public void setServiceUnit(String serviceUnit) {
			this.serviceUnit = serviceUnit;
		}

		public String getExecutionUnit() {
			return executionUnit;
		}

		public void setExecutionUnit(String executionUnit) {
			this.executionUnit = executionUnit;
		}

		public String getServiceUnit1() {
			return serviceUnit1;
		}

		public void setServiceUnit1(String serviceUnit1) {
			this.serviceUnit1 = serviceUnit1;
		}

		public String getExecutionUnit1() {
			return executionUnit1;
		}

		public void setExecutionUnit1(String executionUnit1) {
			this.executionUnit1 = executionUnit1;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}

		public String getCurrencyId() {
			return currencyId;
		}

		public void setCurrencyId(String currencyId) {
			this.currencyId = currencyId;
		}

		public Date getPeriodFrom() {
			return periodFrom;
		}

		public void setPeriodFrom(Date periodFrom) {
			this.periodFrom = periodFrom;
		}

		public Date getPeriodTo() {
			return periodTo;
		}

		public void setPeriodTo(Date periodTo) {
			this.periodTo = periodTo;
		}

		public BigDecimal getForeignAmt() {
			return foreignAmt;
		}

		public void setForeignAmt(BigDecimal foreignAmt) {
			this.foreignAmt = foreignAmt;
		}

		public BigDecimal getExRate() {
			return exRate;
		}

		public void setExRate(BigDecimal exRate) {
			this.exRate = exRate;
		}

		public BigDecimal getLocalAmtForeign() {
			return localAmtForeign;
		}

		public void setLocalAmtForeign(BigDecimal localAmtForeign) {
			this.localAmtForeign = localAmtForeign;
		}

		public BigDecimal getLocalAmt() {
			return localAmt;
		}

		public void setLocalAmt(BigDecimal localAmt) {
			this.localAmt = localAmt;
		}

		public BigDecimal getTaxPerc() {
			return taxPerc;
		}

		public void setTaxPerc(BigDecimal taxPerc) {
			this.taxPerc = taxPerc;
		}

		public BigDecimal getDiscDays() {
			return discDays;
		}

		public void setDiscDays(BigDecimal discDays) {
			this.discDays = discDays;
		}

		public BigDecimal getDiscPercentage() {
			return discPercentage;
		}

		public void setDiscPercentage(BigDecimal discPercentage) {
			this.discPercentage = discPercentage;
		}

		public BigDecimal getDiscValue() {
			return discValue;
		}

		public void setDiscValue(BigDecimal discValue) {
			this.discValue = discValue;
		}

		public BigDecimal getInvoiceAmt() {
			return invoiceAmt;
		}

		public void setInvoiceAmt(BigDecimal invoiceAmt) {
			this.invoiceAmt = invoiceAmt;
		}

		public String getContractor() {
			return contractor;
		}

		public void setContractor(String contractor) {
			this.contractor = contractor;
		}

		public String getAcCode() {
			return acCode;
		}

		public void setAcCode(String acCode) {
			this.acCode = acCode;
		}

		public Date getProcessTransDate() {
			return processTransDate;
		}

		public void setProcessTransDate(Date processTransDate) {
			this.processTransDate = processTransDate;
		}

		public String getProcessId() {
			return processId;
		}

		public void setProcessId(String processId) {
			this.processId = processId;
		}

		public String getPartyId() {
			return partyId;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}

		public String getWoNo() {
			return woNo;
		}

		public void setWoNo(String woNo) {
			this.woNo = woNo;
		}

		public String getWoAmndNo() {
			return woAmndNo;
		}

		public void setWoAmndNo(String woAmndNo) {
			this.woAmndNo = woAmndNo;
		}

		public String getLineNo() {
			return lineNo;
		}

		public void setLineNo(String lineNo) {
			this.lineNo = lineNo;
		}

		public String getBeNo() {
			return beNo;
		}

		public void setBeNo(String beNo) {
			this.beNo = beNo;
		}

		public Date getBeDate() {
			return beDate;
		}

		public void setBeDate(Date beDate) {
			this.beDate = beDate;
		}

		public String getCriteria() {
			return criteria;
		}

		public void setCriteria(String criteria) {
			this.criteria = criteria;
		}

		public BigDecimal getRangeFrom() {
			return rangeFrom;
		}

		public void setRangeFrom(BigDecimal rangeFrom) {
			this.rangeFrom = rangeFrom;
		}

		public BigDecimal getRangeTo() {
			return rangeTo;
		}

		public void setRangeTo(BigDecimal rangeTo) {
			this.rangeTo = rangeTo;
		}

		public String getRangeType() {
			return rangeType;
		}

		public void setRangeType(String rangeType) {
			this.rangeType = rangeType;
		}

		public String getNegotiable() {
			return negotiable;
		}

		public void setNegotiable(String negotiable) {
			this.negotiable = negotiable;
		}

		public String getContainerNo() {
			return containerNo;
		}

		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
		}

		public String getContainerStatus() {
			return containerStatus;
		}

		public void setContainerStatus(String containerStatus) {
			this.containerStatus = containerStatus;
		}

		public String getCommodityDescription() {
			return commodityDescription;
		}

		public void setCommodityDescription(String commodityDescription) {
			this.commodityDescription = commodityDescription;
		}

		public BigDecimal getActualNoOfPackages() {
			return actualNoOfPackages;
		}

		public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
			this.actualNoOfPackages = actualNoOfPackages;
		}

		public String getGateOutId() {
			return gateOutId;
		}

		public void setGateOutId(String gateOutId) {
			this.gateOutId = gateOutId;
		}

		public String getGatePassNo() {
			return gatePassNo;
		}

		public void setGatePassNo(String gatePassNo) {
			this.gatePassNo = gatePassNo;
		}

		public String getAddServices() {
			return addServices;
		}

		public void setAddServices(String addServices) {
			this.addServices = addServices;
		}

		public Date getGateOutDate() {
			return gateOutDate;
		}

		public void setGateOutDate(Date gateOutDate) {
			this.gateOutDate = gateOutDate;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getInvoiceUptoDate() {
			return invoiceUptoDate;
		}

		public void setInvoiceUptoDate(Date invoiceUptoDate) {
			this.invoiceUptoDate = invoiceUptoDate;
		}

		public Date getInvoiceUptoWeek() {
			return invoiceUptoWeek;
		}

		public void setInvoiceUptoWeek(Date invoiceUptoWeek) {
			this.invoiceUptoWeek = invoiceUptoWeek;
		}

		public BigDecimal getDebitNoteExe() {
			return debitNoteExe;
		}

		public void setDebitNoteExe(BigDecimal debitNoteExe) {
			this.debitNoteExe = debitNoteExe;
		}

		public BigDecimal getDebitNoteAmt() {
			return debitNoteAmt;
		}

		public void setDebitNoteAmt(BigDecimal debitNoteAmt) {
			this.debitNoteAmt = debitNoteAmt;
		}

		public BigDecimal getCfsBaseRate() {
			return cfsBaseRate;
		}

		public void setCfsBaseRate(BigDecimal cfsBaseRate) {
			this.cfsBaseRate = cfsBaseRate;
		}

		public BigDecimal getPartyBaseRate() {
			return partyBaseRate;
		}

		public void setPartyBaseRate(BigDecimal partyBaseRate) {
			this.partyBaseRate = partyBaseRate;
		}

		public BigDecimal getRebate() {
			return rebate;
		}

		public void setRebate(BigDecimal rebate) {
			this.rebate = rebate;
		}

		public BigDecimal getProfitability() {
			return profitability;
		}

		public void setProfitability(BigDecimal profitability) {
			this.profitability = profitability;
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

		public BigDecimal getBillAmt() {
			return billAmt;
		}

		public void setBillAmt(BigDecimal billAmt) {
			this.billAmt = billAmt;
		}

		public String getJoServiceId() {
			return joServiceId;
		}

		public void setJoServiceId(String joServiceId) {
			this.joServiceId = joServiceId;
		}

		public String getJoNo() {
			return joNo;
		}

		public void setJoNo(String joNo) {
			this.joNo = joNo;
		}

		public String getJoAmndNo() {
			return joAmndNo;
		}

		public void setJoAmndNo(String joAmndNo) {
			this.joAmndNo = joAmndNo;
		}

		public String getTaxApp() {
			return taxApp;
		}

		public void setTaxApp(String taxApp) {
			this.taxApp = taxApp;
		}

		public String getTaxIdN() {
			return taxIdN;
		}

		public void setTaxIdN(String taxIdN) {
			this.taxIdN = taxIdN;
		}

		public BigDecimal getTaxPercN() {
			return taxPercN;
		}

		public void setTaxPercN(BigDecimal taxPercN) {
			this.taxPercN = taxPercN;
		}

		public BigDecimal getTaxAmt() {
			return taxAmt;
		}

		public void setTaxAmt(BigDecimal taxAmt) {
			this.taxAmt = taxAmt;
		}

		public String getAcCodeN() {
			return acCodeN;
		}

		public void setAcCodeN(String acCodeN) {
			this.acCodeN = acCodeN;
		}

	    
	    
	    

}
