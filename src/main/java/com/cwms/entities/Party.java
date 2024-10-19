
package com.cwms.entities;


import java.io.Serializable;
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
@IdClass(PartyId.class)
@Table(name="Party")
public class Party {

	   @Id
	    @Column(name = "Company_Id", length = 6, nullable = false)
	    private String companyId = "";
	   
	   @Id
	    @Column(name = "branch_Id", length = 6, nullable = false)
	    private String branchId = "";

	    @Id
	    @Column(name = "Party_Id", length = 6, nullable = false)
	    private String partyId = "";

	    @Column(name = "Master_Party_Id", length = 6, nullable = false)
	    private String masterPartyId = "";

	    @Column(name = "Party_Name", length = 100)
	    private String partyName = "";

	    @Column(name = "Party_Type", length = 6, nullable = false)
	    private String partyType = "CHA";

	    @Column(name = "address_1", length = 100, nullable = false)
	    private String address1 = "";

	    @Column(name = "address_2", length = 100, nullable = false)
	    private String address2 = "";

	    @Column(name = "address_3", length = 100, nullable = false)
	    private String address3 = "";

	    @Column(name = "City", length = 15)
	    private String city;

	    @Column(name = "PIN", length = 10)
	    private String pin;

	    @Column(name = "State", length = 15)
	    private String state;

	    @Column(name = "Country", length = 15)
	    private String country;

	    @Column(name = "Movement_Block", length = 3, nullable = false)
	    private String movementBlock = "ACT";

	    @Column(name = "Credit_Limit", precision = 16, scale = 3, nullable = false)
	    private BigDecimal creditLimit = BigDecimal.ZERO;

	    @Column(name = "Phone_No", length = 15)
	    private String phoneNo;

	    @Column(name = "Fax_No", length = 15)
	    private String faxNo;

	    @Column(name = "Party_Ac_Code", length = 10)
	    private String partyAcCode;

	    @Column(name = "Party_Term_Id", length = 3)
	    private String partyTermId;

	    @Column(name = "Bank_Id", length = 6)
	    private String bankId;

	    @Column(name = "PAN_No", length = 25)
	    private String panNo;

	    @Column(name = "PAN_Applied_Ref_No", length = 10)
	    private String panAppliedRefNo = "";

	    @Column(name = "TDS_Applicable", length = 1)
	    private String tdsApplicable;

	    @Column(name = "Currency", length = 6)
	    private String currency;

	    @Column(name = "Tariff_Type", length = 3, nullable = false)
	    private String tariffType = "";

	    @Column(name = "Discount_days", length = 2, nullable = false)
	    private String discountDays = "0";

	    @Column(name = "Free_Days", length = 2, nullable = false)
	    private String freeDays = "0";

	    @Column(name = "Service_Tax_Applicable", length = 1)
	    private String serviceTaxApplicable;

	    @Column(name = "Contact_Person", length = 25)
	    private String contactPerson;

	    @Column(name = "Contact_Designation", length = 25)
	    private String contactDesignation;

	    @Column(name = "Contact_Phone", length = 15)
	    private String contactPhone;

	    @Column(name = "Contact_Fax_No", length = 15)
	    private String contactFaxNo;

	    @Column(name = "Contact_Email", length = 100)
	    private String contactEmail;

	    @Column(name = "Port_Party_Id", length = 5)
	    private String portPartyId = "";

	    @Column(name = "Tan_No_Id", length = 10)
	    private String tanNoId = "";

	    @Column(name = "TDS_Range", length = 150)
	    private String tdsRange = "";

	    @Column(name = "Default_Branch", length = 6)
	    private String defaultBranch;

	    @Column(name = "Shipping_Line_Code", length = 100, nullable = false)
	    private String shippingLineCode = "";

	    @Column(name = "Customs_Exporter_Id", length = 10)
	    private String customsExporterId;

	    @Column(name = "STD_Code", length = 5)
	    private String stdCode;

	    @Column(name = "hide", length = 1, nullable = false)
	    private String hide = "N";

	    @Column(name = "Marketing_Person", length = 20)
	    private String marketingPerson;

	    @Column(name = "Movement_Type", length = 6)
	    private String movementType;

	    @Column(name = "Facilitation_Charge", length = 1, nullable = false)
	    private String facilitationCharge = "N";

	    @Column(name = "Facilitation_Unit", length = 4, nullable = false)
	    private String facilitationUnit = "";

	    @Column(name = "Facilitation_Rate", length = 4, nullable = false)
	    private String facilitationRate = "0";

	    @Column(name = "Facilitation_Rate1", length = 4, nullable = false)
	    private String facilitationRate1 = "0";

	    @Column(name = "Internal_Shifting", length = 1, nullable = false)
	    private String internalShifting = "N";

	    @Column(name = "Internal_Shifting_Unit", length = 4, nullable = false)
	    private String internalShiftingUnit = "";

	    @Column(name = "Internal_Shifting_Rate", length = 4, nullable = false)
	    private String internalShiftingRate = "0";

	    @Column(name = "Internal_Shifting_Rate1", length = 4, nullable = false)
	    private String internalShiftingRate1 = "0";

	    @Column(name = "Additional_Remarks", length = 250, nullable = false)
	    private String additionalRemarks = "";

	    @Column(name = "Free_Days_Tariff", length = 10, nullable = false)
	    private String freeDaysTariff = "";

	    @Column(name = "Extra_Services", length = 50, nullable = false)
	    private String extraServices = "";

	    @Column(name = "Tariff_Active_Flag", length = 1, nullable = false)
	    private String tariffActiveFlag = "Y";

	    @Column(name = "Online_Flag", length = 1, nullable = false)
	    private String onlineFlag = "Y";

	    @Column(name = "Movement_Charge", length = 1, nullable = false)
	    private String movementCharge = "N";

	    @Column(name = "Truck_Handling", length = 1, nullable = false)
	    private String truckHandling = "Y";

	    @Column(name = "CFS_Tariff_No", length = 10, nullable = false)
	    private String cfsTariffNo = "";

	    @Column(name = "GST_No", length = 30, nullable = false)
	    private String gstNo = "UNREGISTER";

	    @Column(name = "IEC_CODE", length = 20, nullable = false)
	    private String iecCode = "";

	    @Column(name = "Monthly_Report", length = 1, nullable = false)
	    private String monthlyReport = "N";

	    @Column(name = "BL_UserId", length = 25, nullable = false)
	    private String blUserId = "";

	    @Column(name = "Created_By", length = 10, nullable = false)
	    private String createdBy = "";

	    @Column(name = "Created_Date", nullable = false)
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

	    @Column(name = "DPD_Daily_Report", length = 1, nullable = false)
	    private String dpdDailyReport = "N";

	    @Column(name = "Daily_Report", length = 1, nullable = false)
	    private String dailyReport = "N";

	    @Column(name = "DSRPRTType", length = 20, nullable = false)
	    private String dsrprtType = "LIN";

	    @Column(name = "LIN_Daily_Report", length = 1, nullable = false)
	    private String linDailyReport = "N";

	    @Column(name = "AGT_Daily_Report", length = 1, nullable = false)
	    private String agtDailyReport = "N";

	    @Column(name = "CHA_Daily_Report", length = 1, nullable = false)
	    private String chaDailyReport = "N";

	    @Column(name = "IMP_Daily_Report", length = 1, nullable = false)
	    private String impDailyReport = "N";

	    @Column(name = "FRW_Daily_Report", length = 1, nullable = false)
	    private String frwDailyReport = "N";

	    @Column(name = "DSRXLSType", length = 20, nullable = false)
	    private String dsrxlsType = "Standard";

	    @Column(name = "Excel_To_Mail", length = 600)
	    private String excelToMail;

	    @Column(name = "Excel_Cc_Mail", length = 600)
	    private String excelCcMail;

	    @Column(name = "Sent_Status", length = 1, nullable = false)
	    private String sentStatus = "N";

	    @Column(name = "Report_Time", nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date reportTime;

	    @Column(name = "Activation_Remarks", length = 150, nullable = false)
	    private String activationRemarks = "";

	    @Column(name = "Deactivation_Remarks", length = 150, nullable = false)
	    private String deactivationRemarks = "";

	    @Column(name = "Status", length = 1, nullable = false)
	    private String status = "";

	    @Column(name = "Export_marketing_person", length = 20, nullable = false)
	    private String exportMarketingPerson = "";

	    @Column(name = "Export_To_Mail", length = 600)
	    private String exportToMail;

	    @Column(name = "Export_CC_Mail", length = 600)
	    private String exportCcMail;

	    @Column(name = "Import_To_Mail", length = 600)
	    private String importToMail;

	    @Column(name = "Import_CC_Mail", length = 600)
	    private String importCcMail;

	    @Column(name = "CODACO_CC_Mail", length = 600)
	    private String codacoCcMail;

	    @Column(name = "CODACO_To_Mail", length = 600)
	    private String codacoToMail;

	    @Column(name = "EDI_flag", length = 1, nullable = false)
	    private String ediFlag = "N";

	    @Column(name = "AGT", length = 1, nullable = false)
	    private String agt = "N";

	    @Column(name = "CHA", length = 1, nullable = false)
	    private String cha = "N";

	    @Column(name = "FRW", length = 1, nullable = false)
	    private String frw = "N";

	    @Column(name = "IMP", length = 1, nullable = false)
	    private String imp = "N";

	    @Column(name = "LIN", length = 1, nullable = false)
	    private String lin = "N";

	    @Column(name = "exp", length = 1, nullable = false)
	    private String exp = "N";

	    @Column(name = "BIDDR", length = 1, nullable = false)
	    private String biddr = "N";

	    @Column(name = "CUSTOMER_TYPE", length = 15, nullable = false)
	    private String customerType = "";

	    @Column(name = "ACCOUNT_TYPE", length = 15)
	    private String accountType;

	    @Column(name = "CUSTOMER_CODE", length = 25)
	    private String customerCode;

	    @Column(name = "OPERATIONAL_MAIL", length = 600)
	    private String operationalMail;

	    @Column(name = "LIN_OPERATIONAL_MAIL_TO", length = 600, nullable = false)
	    private String linOperationalMailTo = "";

	    @Column(name = "AGT_OPERATIONAL_MAIL_TO", length = 600, nullable = false)
	    private String agtOperationalMailTo = "";

	    @Column(name = "CHA_OPERATIONAL_MAIL_TO", length = 600, nullable = false)
	    private String chaOperationalMailTo = "";

	    @Column(name = "IMP_OPERATIONAL_MAIL_TO", length = 600, nullable = false)
	    private String impOperationalMailTo = "";

	    @Column(name = "FRW_OPERATIONAL_MAIL_TO", length = 600, nullable = false)
	    private String frwOperationalMailTo;

	    @Column(name = "OPERATIONAL_MAIL_CC", length = 600, nullable = false)
	    private String operationalMailCc = "";

	    @Column(name = "OPERATIONAL_MAIL_BCC", length = 600, nullable = false)
	    private String operationalMailBcc = "";

	    @Column(name = "FINANCE_MAIL", length = 250, nullable = false)
	    private String financeMail = "";

	    @Column(name = "CODECO_MAIL", length = 250, nullable = false)
	    private String codecoMail = "";

	    @Column(name = "IMP_GRDRENT_FRM", length = 25, nullable = false)
	    private String impGrdrentFrm = "";

	    @Column(name = "IMP_MTGRDRENT_FRM", length = 25, nullable = false)
	    private String impMtgrdrentFrm = "";

	    @Column(name = "IMP_CRG_STORAGE", length = 15, nullable = false)
	    private String impCrgStorage = "";

	    @Column(name = "EXP_CRG_STORAGE", length = 15, nullable = false)
	    private String expCrgStorage = "";

	    @Column(name = "EXP_CRG_STO_FACTOR", length = 15, nullable = false)
	    private String expCrgStoFactor = "";

	    @Column(name = "EXP_CRG_FR_DAYS", length = 15, nullable = false)
	    private String expCrgFrDays = "";

	    @Column(name = "IMPORTER_MAIL", length = 250, nullable = false)
	    private String importerMail = "";

	    @Column(name = "SHIPPING_TYPE_LINE", length = 15, nullable = false)
	    private String shippingTypeLine = "";

	    @Column(name = "INBOND_INVOICE_CHECK", length = 1, nullable = false)
	    private String inbondInvoiceCheck = "N";

	    @Column(name = "BONDNOC_WEEK", length = 2, nullable = false)
	    private String bondnocWeek = "0";

	    @Column(name = "Carting_Invoice", length = 1, nullable = false)
	    private String cartingInvoice = "N";

	    @Column(name = "Cust_ledgerCode", length = 25)
	    private String custLedgerCode;

	    @Column(name = "Check_Deposite", length = 1, nullable = false)
	    private String checkDeposite = "N";

	    @Column(name = "Cr_AmtLmt", precision = 16, scale = 3, nullable = false)
	    private BigDecimal crAmtLmt = BigDecimal.ZERO;

	    @Column(name = "Cr_Period", precision = 6, scale = 3, nullable = false)
	    private BigDecimal crPeriod = BigDecimal.ZERO;

	    @Column(name = "Current_Bal", precision = 16, scale = 3, nullable = false)
	    private BigDecimal currentBal = BigDecimal.ZERO;

	    @Column(name = "LCL_spaceOccupied", length = 12, nullable = false)
	    private String lclSpaceOccupied = "";

	    @Column(name = "IMP_HubOccupied", length = 12, nullable = false)
	    private String impHubOccupied = "";

	    @Column(name = "EXP_HubOccupied", length = 12, nullable = false)
	    private String expHubOccupied = "";

	    @Column(name = "BOND_HubOccupied", length = 12, nullable = false)
	    private String bondHubOccupied = "";

	    @Column(name = "LCL_AreaOccupied", precision = 16, scale = 3, nullable = false)
	    private BigDecimal lclAreaOccupied = BigDecimal.ZERO;

	    @Column(name = "IMPhAreaOccupied", precision = 16, scale = 3, nullable = false)
	    private BigDecimal imphAreaOccupied = BigDecimal.ZERO;

	    @Column(name = "EXPhAreaOccupied", precision = 16, scale = 3, nullable = false)
	    private BigDecimal exphAreaOccupied = BigDecimal.ZERO;

	    @Column(name = "BONDhAreaOccupied", precision = 16, scale = 3, nullable = false)
	    private BigDecimal bondhAreaOccupied = BigDecimal.ZERO;

	    @Column(name = "sl_Type", length = 1, nullable = false)
	    private String slType = "N";

	    @Column(name = "up_Dock_Type", length = 1, nullable = false)
	    private String upDockType = "N";

	    @Column(name = "VND", length = 1, nullable = false)
	    private String vnd = "N";

	    @Column(name = "ACTON", length = 1, nullable = false)
	    private String acton = "N";

	    @Column(name = "CRTLBR", length = 1, nullable = false)
	    private String crtlbr = "N";

	    @Column(name = "DSTLBR", length = 1, nullable = false)
	    private String dstlbr = "N";

	    @Column(name = "DSTRCT", length = 1, nullable = false)
	    private String dstrct = "N";

	    @Column(name = "EQPMSP", length = 1, nullable = false)
	    private String eqpmsp = "N";

	    @Column(name = "FMGTN", length = 1, nullable = false)
	    private String fmgtN = "N";

	    @Column(name = "SCNOPR", length = 1, nullable = false)
	    private String scnopr = "N";

	    @Column(name = "STUFLB", length = 1, nullable = false)
	    private String stuflb = "N";

	    @Column(name = "SUBCTR", length = 1, nullable = false)
	    private String subctr = "N";

	    @Column(name = "SURVEY", length = 1, nullable = false)
	    private String survey = "N";

	    @Column(name = "TRNS", length = 1, nullable = false)
	    private String trns = "N";

	    @Column(name = "VALER", length = 1, nullable = false)
	    private String valer = "N";

	    @Column(name = "resend_flag", length = 1)
	    private String resendFlag = "N";

	    @Column(name = "UI_flag", length = 1)
	    private String uiFlag = "N";

	    @Column(name = "DO_Submit_Req_Flag", length = 1)
	    private String doSubmitReqFlag = "N";

	    @Column(name = "nvocc_tariff", length = 1)
	    private String nvoccTariff = "N";

	    @Column(name = "offdoc_tariff", length = 1)
	    private String offdocTariff = "N";

	    @Column(name = "leschaco", length = 1)
	    private String leschaco = "N";

	    @Column(name = "Cr_AmtLmt_use", precision = 16, scale = 3, nullable = false)
	    private BigDecimal crAmtLmtUse = BigDecimal.ZERO;

	    @Column(name = "Monthly_agent_report_Flag", length = 1, nullable = false)
	    private String monthlyAgentReportFlag = "N";

	    @Column(name = "Weekly_LCL_Agent_report_Flag", length = 1, nullable = false)
	    private String weeklyLclAgentReportFlag = "N";

	    @Column(name = "Invoice_From_Mail", length = 50)
	    private String invoiceFromMail = "cs.belsare91@gmail.com";

	    @Column(name = "Cycle", length = 10, nullable = false)
	    private String cycle = "";

	    @Column(name = "Dmr_interval", length = 1, nullable = false)
	    private String dmrInterval = "Y";

		public Party() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		public String getBranchId() {
			return branchId;
		}



		public void setBranchId(String branchId) {
			this.branchId = branchId;
		}



		public Party(String companyId, String branchId, String partyId, String masterPartyId, String partyName,
				String partyType, String address1, String address2, String address3, String city, String pin,
				String state, String country, String movementBlock, BigDecimal creditLimit, String phoneNo,
				String faxNo, String partyAcCode, String partyTermId, String bankId, String panNo,
				String panAppliedRefNo, String tdsApplicable, String currency, String tariffType, String discountDays,
				String freeDays, String serviceTaxApplicable, String contactPerson, String contactDesignation,
				String contactPhone, String contactFaxNo, String contactEmail, String portPartyId, String tanNoId,
				String tdsRange, String defaultBranch, String shippingLineCode, String customsExporterId,
				String stdCode, String hide, String marketingPerson, String movementType, String facilitationCharge,
				String facilitationUnit, String facilitationRate, String facilitationRate1, String internalShifting,
				String internalShiftingUnit, String internalShiftingRate, String internalShiftingRate1,
				String additionalRemarks, String freeDaysTariff, String extraServices, String tariffActiveFlag,
				String onlineFlag, String movementCharge, String truckHandling, String cfsTariffNo, String gstNo,
				String iecCode, String monthlyReport, String blUserId, String createdBy, Date createdDate,
				String editedBy, Date editedDate, String approvedBy, Date approvedDate, String dpdDailyReport,
				String dailyReport, String dsrprtType, String linDailyReport, String agtDailyReport,
				String chaDailyReport, String impDailyReport, String frwDailyReport, String dsrxlsType,
				String excelToMail, String excelCcMail, String sentStatus, Date reportTime, String activationRemarks,
				String deactivationRemarks, String status, String exportMarketingPerson, String exportToMail,
				String exportCcMail, String importToMail, String importCcMail, String codacoCcMail, String codacoToMail,
				String ediFlag, String agt, String cha, String frw, String imp, String lin, String exp, String biddr,
				String customerType, String accountType, String customerCode, String operationalMail,
				String linOperationalMailTo, String agtOperationalMailTo, String chaOperationalMailTo,
				String impOperationalMailTo, String frwOperationalMailTo, String operationalMailCc,
				String operationalMailBcc, String financeMail, String codecoMail, String impGrdrentFrm,
				String impMtgrdrentFrm, String impCrgStorage, String expCrgStorage, String expCrgStoFactor,
				String expCrgFrDays, String importerMail, String shippingTypeLine, String inbondInvoiceCheck,
				String bondnocWeek, String cartingInvoice, String custLedgerCode, String checkDeposite,
				BigDecimal crAmtLmt, BigDecimal crPeriod, BigDecimal currentBal, String lclSpaceOccupied,
				String impHubOccupied, String expHubOccupied, String bondHubOccupied, BigDecimal lclAreaOccupied,
				BigDecimal imphAreaOccupied, BigDecimal exphAreaOccupied, BigDecimal bondhAreaOccupied, String slType,
				String upDockType, String vnd, String acton, String crtlbr, String dstlbr, String dstrct, String eqpmsp,
				String fmgtN, String scnopr, String stuflb, String subctr, String survey, String trns, String valer,
				String resendFlag, String uiFlag, String doSubmitReqFlag, String nvoccTariff, String offdocTariff,
				String leschaco, BigDecimal crAmtLmtUse, String monthlyAgentReportFlag, String weeklyLclAgentReportFlag,
				String invoiceFromMail, String cycle, String dmrInterval) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.partyId = partyId;
			this.masterPartyId = masterPartyId;
			this.partyName = partyName;
			this.partyType = partyType;
			this.address1 = address1;
			this.address2 = address2;
			this.address3 = address3;
			this.city = city;
			this.pin = pin;
			this.state = state;
			this.country = country;
			this.movementBlock = movementBlock;
			this.creditLimit = creditLimit;
			this.phoneNo = phoneNo;
			this.faxNo = faxNo;
			this.partyAcCode = partyAcCode;
			this.partyTermId = partyTermId;
			this.bankId = bankId;
			this.panNo = panNo;
			this.panAppliedRefNo = panAppliedRefNo;
			this.tdsApplicable = tdsApplicable;
			this.currency = currency;
			this.tariffType = tariffType;
			this.discountDays = discountDays;
			this.freeDays = freeDays;
			this.serviceTaxApplicable = serviceTaxApplicable;
			this.contactPerson = contactPerson;
			this.contactDesignation = contactDesignation;
			this.contactPhone = contactPhone;
			this.contactFaxNo = contactFaxNo;
			this.contactEmail = contactEmail;
			this.portPartyId = portPartyId;
			this.tanNoId = tanNoId;
			this.tdsRange = tdsRange;
			this.defaultBranch = defaultBranch;
			this.shippingLineCode = shippingLineCode;
			this.customsExporterId = customsExporterId;
			this.stdCode = stdCode;
			this.hide = hide;
			this.marketingPerson = marketingPerson;
			this.movementType = movementType;
			this.facilitationCharge = facilitationCharge;
			this.facilitationUnit = facilitationUnit;
			this.facilitationRate = facilitationRate;
			this.facilitationRate1 = facilitationRate1;
			this.internalShifting = internalShifting;
			this.internalShiftingUnit = internalShiftingUnit;
			this.internalShiftingRate = internalShiftingRate;
			this.internalShiftingRate1 = internalShiftingRate1;
			this.additionalRemarks = additionalRemarks;
			this.freeDaysTariff = freeDaysTariff;
			this.extraServices = extraServices;
			this.tariffActiveFlag = tariffActiveFlag;
			this.onlineFlag = onlineFlag;
			this.movementCharge = movementCharge;
			this.truckHandling = truckHandling;
			this.cfsTariffNo = cfsTariffNo;
			this.gstNo = gstNo;
			this.iecCode = iecCode;
			this.monthlyReport = monthlyReport;
			this.blUserId = blUserId;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.dpdDailyReport = dpdDailyReport;
			this.dailyReport = dailyReport;
			this.dsrprtType = dsrprtType;
			this.linDailyReport = linDailyReport;
			this.agtDailyReport = agtDailyReport;
			this.chaDailyReport = chaDailyReport;
			this.impDailyReport = impDailyReport;
			this.frwDailyReport = frwDailyReport;
			this.dsrxlsType = dsrxlsType;
			this.excelToMail = excelToMail;
			this.excelCcMail = excelCcMail;
			this.sentStatus = sentStatus;
			this.reportTime = reportTime;
			this.activationRemarks = activationRemarks;
			this.deactivationRemarks = deactivationRemarks;
			this.status = status;
			this.exportMarketingPerson = exportMarketingPerson;
			this.exportToMail = exportToMail;
			this.exportCcMail = exportCcMail;
			this.importToMail = importToMail;
			this.importCcMail = importCcMail;
			this.codacoCcMail = codacoCcMail;
			this.codacoToMail = codacoToMail;
			this.ediFlag = ediFlag;
			this.agt = agt;
			this.cha = cha;
			this.frw = frw;
			this.imp = imp;
			this.lin = lin;
			this.exp = exp;
			this.biddr = biddr;
			this.customerType = customerType;
			this.accountType = accountType;
			this.customerCode = customerCode;
			this.operationalMail = operationalMail;
			this.linOperationalMailTo = linOperationalMailTo;
			this.agtOperationalMailTo = agtOperationalMailTo;
			this.chaOperationalMailTo = chaOperationalMailTo;
			this.impOperationalMailTo = impOperationalMailTo;
			this.frwOperationalMailTo = frwOperationalMailTo;
			this.operationalMailCc = operationalMailCc;
			this.operationalMailBcc = operationalMailBcc;
			this.financeMail = financeMail;
			this.codecoMail = codecoMail;
			this.impGrdrentFrm = impGrdrentFrm;
			this.impMtgrdrentFrm = impMtgrdrentFrm;
			this.impCrgStorage = impCrgStorage;
			this.expCrgStorage = expCrgStorage;
			this.expCrgStoFactor = expCrgStoFactor;
			this.expCrgFrDays = expCrgFrDays;
			this.importerMail = importerMail;
			this.shippingTypeLine = shippingTypeLine;
			this.inbondInvoiceCheck = inbondInvoiceCheck;
			this.bondnocWeek = bondnocWeek;
			this.cartingInvoice = cartingInvoice;
			this.custLedgerCode = custLedgerCode;
			this.checkDeposite = checkDeposite;
			this.crAmtLmt = crAmtLmt;
			this.crPeriod = crPeriod;
			this.currentBal = currentBal;
			this.lclSpaceOccupied = lclSpaceOccupied;
			this.impHubOccupied = impHubOccupied;
			this.expHubOccupied = expHubOccupied;
			this.bondHubOccupied = bondHubOccupied;
			this.lclAreaOccupied = lclAreaOccupied;
			this.imphAreaOccupied = imphAreaOccupied;
			this.exphAreaOccupied = exphAreaOccupied;
			this.bondhAreaOccupied = bondhAreaOccupied;
			this.slType = slType;
			this.upDockType = upDockType;
			this.vnd = vnd;
			this.acton = acton;
			this.crtlbr = crtlbr;
			this.dstlbr = dstlbr;
			this.dstrct = dstrct;
			this.eqpmsp = eqpmsp;
			this.fmgtN = fmgtN;
			this.scnopr = scnopr;
			this.stuflb = stuflb;
			this.subctr = subctr;
			this.survey = survey;
			this.trns = trns;
			this.valer = valer;
			this.resendFlag = resendFlag;
			this.uiFlag = uiFlag;
			this.doSubmitReqFlag = doSubmitReqFlag;
			this.nvoccTariff = nvoccTariff;
			this.offdocTariff = offdocTariff;
			this.leschaco = leschaco;
			this.crAmtLmtUse = crAmtLmtUse;
			this.monthlyAgentReportFlag = monthlyAgentReportFlag;
			this.weeklyLclAgentReportFlag = weeklyLclAgentReportFlag;
			this.invoiceFromMail = invoiceFromMail;
			this.cycle = cycle;
			this.dmrInterval = dmrInterval;
		}



		public String getCompanyId() {
			return companyId;
		}

		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}

		public String getPartyId() {
			return partyId;
		}

		public void setPartyId(String partyId) {
			this.partyId = partyId;
		}

		public String getMasterPartyId() {
			return masterPartyId;
		}

		public void setMasterPartyId(String masterPartyId) {
			this.masterPartyId = masterPartyId;
		}

		public String getPartyName() {
			return partyName;
		}

		public void setPartyName(String partyName) {
			this.partyName = partyName;
		}

		public String getPartyType() {
			return partyType;
		}

		public void setPartyType(String partyType) {
			this.partyType = partyType;
		}

		public String getAddress1() {
			return address1;
		}

		public void setAddress1(String address1) {
			this.address1 = address1;
		}

		public String getAddress2() {
			return address2;
		}

		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		public String getAddress3() {
			return address3;
		}

		public void setAddress3(String address3) {
			this.address3 = address3;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPin() {
			return pin;
		}

		public void setPin(String pin) {
			this.pin = pin;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getMovementBlock() {
			return movementBlock;
		}

		public void setMovementBlock(String movementBlock) {
			this.movementBlock = movementBlock;
		}

		public BigDecimal getCreditLimit() {
			return creditLimit;
		}

		public void setCreditLimit(BigDecimal creditLimit) {
			this.creditLimit = creditLimit;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		public String getFaxNo() {
			return faxNo;
		}

		public void setFaxNo(String faxNo) {
			this.faxNo = faxNo;
		}

		public String getPartyAcCode() {
			return partyAcCode;
		}

		public void setPartyAcCode(String partyAcCode) {
			this.partyAcCode = partyAcCode;
		}

		public String getPartyTermId() {
			return partyTermId;
		}

		public void setPartyTermId(String partyTermId) {
			this.partyTermId = partyTermId;
		}

		public String getBankId() {
			return bankId;
		}

		public void setBankId(String bankId) {
			this.bankId = bankId;
		}

		public String getPanNo() {
			return panNo;
		}

		public void setPanNo(String panNo) {
			this.panNo = panNo;
		}

		public String getPanAppliedRefNo() {
			return panAppliedRefNo;
		}

		public void setPanAppliedRefNo(String panAppliedRefNo) {
			this.panAppliedRefNo = panAppliedRefNo;
		}

		public String getTdsApplicable() {
			return tdsApplicable;
		}

		public void setTdsApplicable(String tdsApplicable) {
			this.tdsApplicable = tdsApplicable;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public String getTariffType() {
			return tariffType;
		}

		public void setTariffType(String tariffType) {
			this.tariffType = tariffType;
		}

		public String getDiscountDays() {
			return discountDays;
		}

		public void setDiscountDays(String discountDays) {
			this.discountDays = discountDays;
		}

		public String getFreeDays() {
			return freeDays;
		}

		public void setFreeDays(String freeDays) {
			this.freeDays = freeDays;
		}

		public String getServiceTaxApplicable() {
			return serviceTaxApplicable;
		}

		public void setServiceTaxApplicable(String serviceTaxApplicable) {
			this.serviceTaxApplicable = serviceTaxApplicable;
		}

		public String getContactPerson() {
			return contactPerson;
		}

		public void setContactPerson(String contactPerson) {
			this.contactPerson = contactPerson;
		}

		public String getContactDesignation() {
			return contactDesignation;
		}

		public void setContactDesignation(String contactDesignation) {
			this.contactDesignation = contactDesignation;
		}

		public String getContactPhone() {
			return contactPhone;
		}

		public void setContactPhone(String contactPhone) {
			this.contactPhone = contactPhone;
		}

		public String getContactFaxNo() {
			return contactFaxNo;
		}

		public void setContactFaxNo(String contactFaxNo) {
			this.contactFaxNo = contactFaxNo;
		}

		public String getContactEmail() {
			return contactEmail;
		}

		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}

		public String getPortPartyId() {
			return portPartyId;
		}

		public void setPortPartyId(String portPartyId) {
			this.portPartyId = portPartyId;
		}

		public String getTanNoId() {
			return tanNoId;
		}

		public void setTanNoId(String tanNoId) {
			this.tanNoId = tanNoId;
		}

		public String getTdsRange() {
			return tdsRange;
		}

		public void setTdsRange(String tdsRange) {
			this.tdsRange = tdsRange;
		}

		public String getDefaultBranch() {
			return defaultBranch;
		}

		public void setDefaultBranch(String defaultBranch) {
			this.defaultBranch = defaultBranch;
		}

		public String getShippingLineCode() {
			return shippingLineCode;
		}

		public void setShippingLineCode(String shippingLineCode) {
			this.shippingLineCode = shippingLineCode;
		}

		public String getCustomsExporterId() {
			return customsExporterId;
		}

		public void setCustomsExporterId(String customsExporterId) {
			this.customsExporterId = customsExporterId;
		}

		public String getStdCode() {
			return stdCode;
		}

		public void setStdCode(String stdCode) {
			this.stdCode = stdCode;
		}

		public String getHide() {
			return hide;
		}

		public void setHide(String hide) {
			this.hide = hide;
		}

		public String getMarketingPerson() {
			return marketingPerson;
		}

		public void setMarketingPerson(String marketingPerson) {
			this.marketingPerson = marketingPerson;
		}

		public String getMovementType() {
			return movementType;
		}

		public void setMovementType(String movementType) {
			this.movementType = movementType;
		}

		public String getFacilitationCharge() {
			return facilitationCharge;
		}

		public void setFacilitationCharge(String facilitationCharge) {
			this.facilitationCharge = facilitationCharge;
		}

		public String getFacilitationUnit() {
			return facilitationUnit;
		}

		public void setFacilitationUnit(String facilitationUnit) {
			this.facilitationUnit = facilitationUnit;
		}

		public String getFacilitationRate() {
			return facilitationRate;
		}

		public void setFacilitationRate(String facilitationRate) {
			this.facilitationRate = facilitationRate;
		}

		public String getFacilitationRate1() {
			return facilitationRate1;
		}

		public void setFacilitationRate1(String facilitationRate1) {
			this.facilitationRate1 = facilitationRate1;
		}

		public String getInternalShifting() {
			return internalShifting;
		}

		public void setInternalShifting(String internalShifting) {
			this.internalShifting = internalShifting;
		}

		public String getInternalShiftingUnit() {
			return internalShiftingUnit;
		}

		public void setInternalShiftingUnit(String internalShiftingUnit) {
			this.internalShiftingUnit = internalShiftingUnit;
		}

		public String getInternalShiftingRate() {
			return internalShiftingRate;
		}

		public void setInternalShiftingRate(String internalShiftingRate) {
			this.internalShiftingRate = internalShiftingRate;
		}

		public String getInternalShiftingRate1() {
			return internalShiftingRate1;
		}

		public void setInternalShiftingRate1(String internalShiftingRate1) {
			this.internalShiftingRate1 = internalShiftingRate1;
		}

		public String getAdditionalRemarks() {
			return additionalRemarks;
		}

		public void setAdditionalRemarks(String additionalRemarks) {
			this.additionalRemarks = additionalRemarks;
		}

		public String getFreeDaysTariff() {
			return freeDaysTariff;
		}

		public void setFreeDaysTariff(String freeDaysTariff) {
			this.freeDaysTariff = freeDaysTariff;
		}

		public String getExtraServices() {
			return extraServices;
		}

		public void setExtraServices(String extraServices) {
			this.extraServices = extraServices;
		}

		public String getTariffActiveFlag() {
			return tariffActiveFlag;
		}

		public void setTariffActiveFlag(String tariffActiveFlag) {
			this.tariffActiveFlag = tariffActiveFlag;
		}

		public String getOnlineFlag() {
			return onlineFlag;
		}

		public void setOnlineFlag(String onlineFlag) {
			this.onlineFlag = onlineFlag;
		}

		public String getMovementCharge() {
			return movementCharge;
		}

		public void setMovementCharge(String movementCharge) {
			this.movementCharge = movementCharge;
		}

		public String getTruckHandling() {
			return truckHandling;
		}

		public void setTruckHandling(String truckHandling) {
			this.truckHandling = truckHandling;
		}

		public String getCfsTariffNo() {
			return cfsTariffNo;
		}

		public void setCfsTariffNo(String cfsTariffNo) {
			this.cfsTariffNo = cfsTariffNo;
		}

		public String getGstNo() {
			return gstNo;
		}

		public void setGstNo(String gstNo) {
			this.gstNo = gstNo;
		}

		public String getIecCode() {
			return iecCode;
		}

		public void setIecCode(String iecCode) {
			this.iecCode = iecCode;
		}

		public String getMonthlyReport() {
			return monthlyReport;
		}

		public void setMonthlyReport(String monthlyReport) {
			this.monthlyReport = monthlyReport;
		}

		public String getBlUserId() {
			return blUserId;
		}

		public void setBlUserId(String blUserId) {
			this.blUserId = blUserId;
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

		public String getDpdDailyReport() {
			return dpdDailyReport;
		}

		public void setDpdDailyReport(String dpdDailyReport) {
			this.dpdDailyReport = dpdDailyReport;
		}

		public String getDailyReport() {
			return dailyReport;
		}

		public void setDailyReport(String dailyReport) {
			this.dailyReport = dailyReport;
		}

		public String getDsrprtType() {
			return dsrprtType;
		}

		public void setDsrprtType(String dsrprtType) {
			this.dsrprtType = dsrprtType;
		}

		public String getLinDailyReport() {
			return linDailyReport;
		}

		public void setLinDailyReport(String linDailyReport) {
			this.linDailyReport = linDailyReport;
		}

		public String getAgtDailyReport() {
			return agtDailyReport;
		}

		public void setAgtDailyReport(String agtDailyReport) {
			this.agtDailyReport = agtDailyReport;
		}

		public String getChaDailyReport() {
			return chaDailyReport;
		}

		public void setChaDailyReport(String chaDailyReport) {
			this.chaDailyReport = chaDailyReport;
		}

		public String getImpDailyReport() {
			return impDailyReport;
		}

		public void setImpDailyReport(String impDailyReport) {
			this.impDailyReport = impDailyReport;
		}

		public String getFrwDailyReport() {
			return frwDailyReport;
		}

		public void setFrwDailyReport(String frwDailyReport) {
			this.frwDailyReport = frwDailyReport;
		}

		public String getDsrxlsType() {
			return dsrxlsType;
		}

		public void setDsrxlsType(String dsrxlsType) {
			this.dsrxlsType = dsrxlsType;
		}

		public String getExcelToMail() {
			return excelToMail;
		}

		public void setExcelToMail(String excelToMail) {
			this.excelToMail = excelToMail;
		}

		public String getExcelCcMail() {
			return excelCcMail;
		}

		public void setExcelCcMail(String excelCcMail) {
			this.excelCcMail = excelCcMail;
		}

		public String getSentStatus() {
			return sentStatus;
		}

		public void setSentStatus(String sentStatus) {
			this.sentStatus = sentStatus;
		}

		public Date getReportTime() {
			return reportTime;
		}

		public void setReportTime(Date reportTime) {
			this.reportTime = reportTime;
		}

		public String getActivationRemarks() {
			return activationRemarks;
		}

		public void setActivationRemarks(String activationRemarks) {
			this.activationRemarks = activationRemarks;
		}

		public String getDeactivationRemarks() {
			return deactivationRemarks;
		}

		public void setDeactivationRemarks(String deactivationRemarks) {
			this.deactivationRemarks = deactivationRemarks;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getExportMarketingPerson() {
			return exportMarketingPerson;
		}

		public void setExportMarketingPerson(String exportMarketingPerson) {
			this.exportMarketingPerson = exportMarketingPerson;
		}

		public String getExportToMail() {
			return exportToMail;
		}

		public void setExportToMail(String exportToMail) {
			this.exportToMail = exportToMail;
		}

		public String getExportCcMail() {
			return exportCcMail;
		}

		public void setExportCcMail(String exportCcMail) {
			this.exportCcMail = exportCcMail;
		}

		public String getImportToMail() {
			return importToMail;
		}

		public void setImportToMail(String importToMail) {
			this.importToMail = importToMail;
		}

		public String getImportCcMail() {
			return importCcMail;
		}

		public void setImportCcMail(String importCcMail) {
			this.importCcMail = importCcMail;
		}

		public String getCodacoCcMail() {
			return codacoCcMail;
		}

		public void setCodacoCcMail(String codacoCcMail) {
			this.codacoCcMail = codacoCcMail;
		}

		public String getCodacoToMail() {
			return codacoToMail;
		}

		public void setCodacoToMail(String codacoToMail) {
			this.codacoToMail = codacoToMail;
		}

		public String getEdiFlag() {
			return ediFlag;
		}

		public void setEdiFlag(String ediFlag) {
			this.ediFlag = ediFlag;
		}

		public String getAgt() {
			return agt;
		}

		public void setAgt(String agt) {
			this.agt = agt;
		}

		public String getCha() {
			return cha;
		}

		public void setCha(String cha) {
			this.cha = cha;
		}

		public String getFrw() {
			return frw;
		}

		public void setFrw(String frw) {
			this.frw = frw;
		}

		public String getImp() {
			return imp;
		}

		public void setImp(String imp) {
			this.imp = imp;
		}

		public String getLin() {
			return lin;
		}

		public void setLin(String lin) {
			this.lin = lin;
		}

		public String getExp() {
			return exp;
		}

		public void setExp(String exp) {
			this.exp = exp;
		}

		public String getBiddr() {
			return biddr;
		}

		public void setBiddr(String biddr) {
			this.biddr = biddr;
		}

		public String getCustomerType() {
			return customerType;
		}

		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}

		public String getAccountType() {
			return accountType;
		}

		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}

		public String getCustomerCode() {
			return customerCode;
		}

		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}

		public String getOperationalMail() {
			return operationalMail;
		}

		public void setOperationalMail(String operationalMail) {
			this.operationalMail = operationalMail;
		}

		public String getLinOperationalMailTo() {
			return linOperationalMailTo;
		}

		public void setLinOperationalMailTo(String linOperationalMailTo) {
			this.linOperationalMailTo = linOperationalMailTo;
		}

		public String getAgtOperationalMailTo() {
			return agtOperationalMailTo;
		}

		public void setAgtOperationalMailTo(String agtOperationalMailTo) {
			this.agtOperationalMailTo = agtOperationalMailTo;
		}

		public String getChaOperationalMailTo() {
			return chaOperationalMailTo;
		}

		public void setChaOperationalMailTo(String chaOperationalMailTo) {
			this.chaOperationalMailTo = chaOperationalMailTo;
		}

		public String getImpOperationalMailTo() {
			return impOperationalMailTo;
		}

		public void setImpOperationalMailTo(String impOperationalMailTo) {
			this.impOperationalMailTo = impOperationalMailTo;
		}

		public String getFrwOperationalMailTo() {
			return frwOperationalMailTo;
		}

		public void setFrwOperationalMailTo(String frwOperationalMailTo) {
			this.frwOperationalMailTo = frwOperationalMailTo;
		}

		public String getOperationalMailCc() {
			return operationalMailCc;
		}

		public void setOperationalMailCc(String operationalMailCc) {
			this.operationalMailCc = operationalMailCc;
		}

		public String getOperationalMailBcc() {
			return operationalMailBcc;
		}

		public void setOperationalMailBcc(String operationalMailBcc) {
			this.operationalMailBcc = operationalMailBcc;
		}

		public String getFinanceMail() {
			return financeMail;
		}

		public void setFinanceMail(String financeMail) {
			this.financeMail = financeMail;
		}

		public String getCodecoMail() {
			return codecoMail;
		}

		public void setCodecoMail(String codecoMail) {
			this.codecoMail = codecoMail;
		}

		public String getImpGrdrentFrm() {
			return impGrdrentFrm;
		}

		public void setImpGrdrentFrm(String impGrdrentFrm) {
			this.impGrdrentFrm = impGrdrentFrm;
		}

		public String getImpMtgrdrentFrm() {
			return impMtgrdrentFrm;
		}

		public void setImpMtgrdrentFrm(String impMtgrdrentFrm) {
			this.impMtgrdrentFrm = impMtgrdrentFrm;
		}

		public String getImpCrgStorage() {
			return impCrgStorage;
		}

		public void setImpCrgStorage(String impCrgStorage) {
			this.impCrgStorage = impCrgStorage;
		}

		public String getExpCrgStorage() {
			return expCrgStorage;
		}

		public void setExpCrgStorage(String expCrgStorage) {
			this.expCrgStorage = expCrgStorage;
		}

		public String getExpCrgStoFactor() {
			return expCrgStoFactor;
		}

		public void setExpCrgStoFactor(String expCrgStoFactor) {
			this.expCrgStoFactor = expCrgStoFactor;
		}

		public String getExpCrgFrDays() {
			return expCrgFrDays;
		}

		public void setExpCrgFrDays(String expCrgFrDays) {
			this.expCrgFrDays = expCrgFrDays;
		}

		public String getImporterMail() {
			return importerMail;
		}

		public void setImporterMail(String importerMail) {
			this.importerMail = importerMail;
		}

		public String getShippingTypeLine() {
			return shippingTypeLine;
		}

		public void setShippingTypeLine(String shippingTypeLine) {
			this.shippingTypeLine = shippingTypeLine;
		}

		public String getInbondInvoiceCheck() {
			return inbondInvoiceCheck;
		}

		public void setInbondInvoiceCheck(String inbondInvoiceCheck) {
			this.inbondInvoiceCheck = inbondInvoiceCheck;
		}

		public String getBondnocWeek() {
			return bondnocWeek;
		}

		public void setBondnocWeek(String bondnocWeek) {
			this.bondnocWeek = bondnocWeek;
		}

		public String getCartingInvoice() {
			return cartingInvoice;
		}

		public void setCartingInvoice(String cartingInvoice) {
			this.cartingInvoice = cartingInvoice;
		}

		public String getCustLedgerCode() {
			return custLedgerCode;
		}

		public void setCustLedgerCode(String custLedgerCode) {
			this.custLedgerCode = custLedgerCode;
		}

		public String getCheckDeposite() {
			return checkDeposite;
		}

		public void setCheckDeposite(String checkDeposite) {
			this.checkDeposite = checkDeposite;
		}

		public BigDecimal getCrAmtLmt() {
			return crAmtLmt;
		}

		public void setCrAmtLmt(BigDecimal crAmtLmt) {
			this.crAmtLmt = crAmtLmt;
		}

		public BigDecimal getCrPeriod() {
			return crPeriod;
		}

		public void setCrPeriod(BigDecimal crPeriod) {
			this.crPeriod = crPeriod;
		}

		public BigDecimal getCurrentBal() {
			return currentBal;
		}

		public void setCurrentBal(BigDecimal currentBal) {
			this.currentBal = currentBal;
		}

		public String getLclSpaceOccupied() {
			return lclSpaceOccupied;
		}

		public void setLclSpaceOccupied(String lclSpaceOccupied) {
			this.lclSpaceOccupied = lclSpaceOccupied;
		}

		public String getImpHubOccupied() {
			return impHubOccupied;
		}

		public void setImpHubOccupied(String impHubOccupied) {
			this.impHubOccupied = impHubOccupied;
		}

		public String getExpHubOccupied() {
			return expHubOccupied;
		}

		public void setExpHubOccupied(String expHubOccupied) {
			this.expHubOccupied = expHubOccupied;
		}

		public String getBondHubOccupied() {
			return bondHubOccupied;
		}

		public void setBondHubOccupied(String bondHubOccupied) {
			this.bondHubOccupied = bondHubOccupied;
		}

		public BigDecimal getLclAreaOccupied() {
			return lclAreaOccupied;
		}

		public void setLclAreaOccupied(BigDecimal lclAreaOccupied) {
			this.lclAreaOccupied = lclAreaOccupied;
		}

		public BigDecimal getImphAreaOccupied() {
			return imphAreaOccupied;
		}

		public void setImphAreaOccupied(BigDecimal imphAreaOccupied) {
			this.imphAreaOccupied = imphAreaOccupied;
		}

		public BigDecimal getExphAreaOccupied() {
			return exphAreaOccupied;
		}

		public void setExphAreaOccupied(BigDecimal exphAreaOccupied) {
			this.exphAreaOccupied = exphAreaOccupied;
		}

		public BigDecimal getBondhAreaOccupied() {
			return bondhAreaOccupied;
		}

		public void setBondhAreaOccupied(BigDecimal bondhAreaOccupied) {
			this.bondhAreaOccupied = bondhAreaOccupied;
		}

		public String getSlType() {
			return slType;
		}

		public void setSlType(String slType) {
			this.slType = slType;
		}

		public String getUpDockType() {
			return upDockType;
		}

		public void setUpDockType(String upDockType) {
			this.upDockType = upDockType;
		}

		public String getVnd() {
			return vnd;
		}

		public void setVnd(String vnd) {
			this.vnd = vnd;
		}

		public String getActon() {
			return acton;
		}

		public void setActon(String acton) {
			this.acton = acton;
		}

		public String getCrtlbr() {
			return crtlbr;
		}

		public void setCrtlbr(String crtlbr) {
			this.crtlbr = crtlbr;
		}

		public String getDstlbr() {
			return dstlbr;
		}

		public void setDstlbr(String dstlbr) {
			this.dstlbr = dstlbr;
		}

		public String getDstrct() {
			return dstrct;
		}

		public void setDstrct(String dstrct) {
			this.dstrct = dstrct;
		}

		public String getEqpmsp() {
			return eqpmsp;
		}

		public void setEqpmsp(String eqpmsp) {
			this.eqpmsp = eqpmsp;
		}

		public String getFmgtN() {
			return fmgtN;
		}

		public void setFmgtN(String fmgtN) {
			this.fmgtN = fmgtN;
		}

		public String getScnopr() {
			return scnopr;
		}

		public void setScnopr(String scnopr) {
			this.scnopr = scnopr;
		}

		public String getStuflb() {
			return stuflb;
		}

		public void setStuflb(String stuflb) {
			this.stuflb = stuflb;
		}

		public String getSubctr() {
			return subctr;
		}

		public void setSubctr(String subctr) {
			this.subctr = subctr;
		}

		public String getSurvey() {
			return survey;
		}

		public void setSurvey(String survey) {
			this.survey = survey;
		}

		public String getTrns() {
			return trns;
		}

		public void setTrns(String trns) {
			this.trns = trns;
		}

		public String getValer() {
			return valer;
		}

		public void setValer(String valer) {
			this.valer = valer;
		}

		public String getResendFlag() {
			return resendFlag;
		}

		public void setResendFlag(String resendFlag) {
			this.resendFlag = resendFlag;
		}

		public String getUiFlag() {
			return uiFlag;
		}

		public void setUiFlag(String uiFlag) {
			this.uiFlag = uiFlag;
		}

		public String getDoSubmitReqFlag() {
			return doSubmitReqFlag;
		}

		public void setDoSubmitReqFlag(String doSubmitReqFlag) {
			this.doSubmitReqFlag = doSubmitReqFlag;
		}

		public String getNvoccTariff() {
			return nvoccTariff;
		}

		public void setNvoccTariff(String nvoccTariff) {
			this.nvoccTariff = nvoccTariff;
		}

		public String getOffdocTariff() {
			return offdocTariff;
		}

		public void setOffdocTariff(String offdocTariff) {
			this.offdocTariff = offdocTariff;
		}

		public String getLeschaco() {
			return leschaco;
		}

		public void setLeschaco(String leschaco) {
			this.leschaco = leschaco;
		}

		public BigDecimal getCrAmtLmtUse() {
			return crAmtLmtUse;
		}

		public void setCrAmtLmtUse(BigDecimal crAmtLmtUse) {
			this.crAmtLmtUse = crAmtLmtUse;
		}

		public String getMonthlyAgentReportFlag() {
			return monthlyAgentReportFlag;
		}

		public void setMonthlyAgentReportFlag(String monthlyAgentReportFlag) {
			this.monthlyAgentReportFlag = monthlyAgentReportFlag;
		}

		public String getWeeklyLclAgentReportFlag() {
			return weeklyLclAgentReportFlag;
		}

		public void setWeeklyLclAgentReportFlag(String weeklyLclAgentReportFlag) {
			this.weeklyLclAgentReportFlag = weeklyLclAgentReportFlag;
		}

		public String getInvoiceFromMail() {
			return invoiceFromMail;
		}

		public void setInvoiceFromMail(String invoiceFromMail) {
			this.invoiceFromMail = invoiceFromMail;
		}

		public String getCycle() {
			return cycle;
		}

		public void setCycle(String cycle) {
			this.cycle = cycle;
		}

		public String getDmrInterval() {
			return dmrInterval;
		}

		public void setDmrInterval(String dmrInterval) {
			this.dmrInterval = dmrInterval;
		}



		public Party(String partyId, String partyName, String gstNo, String iecCode, String customerCode, String status) {
			super();
			this.partyId = partyId;
			this.partyName = partyName;
			this.gstNo = gstNo;
			this.iecCode = iecCode;
			this.customerCode = customerCode;
			this.status = status;
		}



		@Override
		public String toString() {
			return "Party [companyId=" + companyId + ", branchId=" + branchId + ", partyId=" + partyId
					+ ", masterPartyId=" + masterPartyId + ", partyName=" + partyName + ", partyType=" + partyType
					+ ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", city=" + city
					+ ", pin=" + pin + ", state=" + state + ", country=" + country + ", movementBlock=" + movementBlock
					+ ", creditLimit=" + creditLimit + ", phoneNo=" + phoneNo + ", faxNo=" + faxNo + ", partyAcCode="
					+ partyAcCode + ", partyTermId=" + partyTermId + ", bankId=" + bankId + ", panNo=" + panNo
					+ ", panAppliedRefNo=" + panAppliedRefNo + ", tdsApplicable=" + tdsApplicable + ", currency="
					+ currency + ", tariffType=" + tariffType + ", discountDays=" + discountDays + ", freeDays="
					+ freeDays + ", serviceTaxApplicable=" + serviceTaxApplicable + ", contactPerson=" + contactPerson
					+ ", contactDesignation=" + contactDesignation + ", contactPhone=" + contactPhone
					+ ", contactFaxNo=" + contactFaxNo + ", contactEmail=" + contactEmail + ", portPartyId="
					+ portPartyId + ", tanNoId=" + tanNoId + ", tdsRange=" + tdsRange + ", defaultBranch="
					+ defaultBranch + ", shippingLineCode=" + shippingLineCode + ", customsExporterId="
					+ customsExporterId + ", stdCode=" + stdCode + ", hide=" + hide + ", marketingPerson="
					+ marketingPerson + ", movementType=" + movementType + ", facilitationCharge=" + facilitationCharge
					+ ", facilitationUnit=" + facilitationUnit + ", facilitationRate=" + facilitationRate
					+ ", facilitationRate1=" + facilitationRate1 + ", internalShifting=" + internalShifting
					+ ", internalShiftingUnit=" + internalShiftingUnit + ", internalShiftingRate="
					+ internalShiftingRate + ", internalShiftingRate1=" + internalShiftingRate1 + ", additionalRemarks="
					+ additionalRemarks + ", freeDaysTariff=" + freeDaysTariff + ", extraServices=" + extraServices
					+ ", tariffActiveFlag=" + tariffActiveFlag + ", onlineFlag=" + onlineFlag + ", movementCharge="
					+ movementCharge + ", truckHandling=" + truckHandling + ", cfsTariffNo=" + cfsTariffNo + ", gstNo="
					+ gstNo + ", iecCode=" + iecCode + ", monthlyReport=" + monthlyReport + ", blUserId=" + blUserId
					+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
					+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
					+ ", dpdDailyReport=" + dpdDailyReport + ", dailyReport=" + dailyReport + ", dsrprtType="
					+ dsrprtType + ", linDailyReport=" + linDailyReport + ", agtDailyReport=" + agtDailyReport
					+ ", chaDailyReport=" + chaDailyReport + ", impDailyReport=" + impDailyReport + ", frwDailyReport="
					+ frwDailyReport + ", dsrxlsType=" + dsrxlsType + ", excelToMail=" + excelToMail + ", excelCcMail="
					+ excelCcMail + ", sentStatus=" + sentStatus + ", reportTime=" + reportTime + ", activationRemarks="
					+ activationRemarks + ", deactivationRemarks=" + deactivationRemarks + ", status=" + status
					+ ", exportMarketingPerson=" + exportMarketingPerson + ", exportToMail=" + exportToMail
					+ ", exportCcMail=" + exportCcMail + ", importToMail=" + importToMail + ", importCcMail="
					+ importCcMail + ", codacoCcMail=" + codacoCcMail + ", codacoToMail=" + codacoToMail + ", ediFlag="
					+ ediFlag + ", agt=" + agt + ", cha=" + cha + ", frw=" + frw + ", imp=" + imp + ", lin=" + lin
					+ ", exp=" + exp + ", biddr=" + biddr + ", customerType=" + customerType + ", accountType="
					+ accountType + ", customerCode=" + customerCode + ", operationalMail=" + operationalMail
					+ ", linOperationalMailTo=" + linOperationalMailTo + ", agtOperationalMailTo="
					+ agtOperationalMailTo + ", chaOperationalMailTo=" + chaOperationalMailTo
					+ ", impOperationalMailTo=" + impOperationalMailTo + ", frwOperationalMailTo="
					+ frwOperationalMailTo + ", operationalMailCc=" + operationalMailCc + ", operationalMailBcc="
					+ operationalMailBcc + ", financeMail=" + financeMail + ", codecoMail=" + codecoMail
					+ ", impGrdrentFrm=" + impGrdrentFrm + ", impMtgrdrentFrm=" + impMtgrdrentFrm + ", impCrgStorage="
					+ impCrgStorage + ", expCrgStorage=" + expCrgStorage + ", expCrgStoFactor=" + expCrgStoFactor
					+ ", expCrgFrDays=" + expCrgFrDays + ", importerMail=" + importerMail + ", shippingTypeLine="
					+ shippingTypeLine + ", inbondInvoiceCheck=" + inbondInvoiceCheck + ", bondnocWeek=" + bondnocWeek
					+ ", cartingInvoice=" + cartingInvoice + ", custLedgerCode=" + custLedgerCode + ", checkDeposite="
					+ checkDeposite + ", crAmtLmt=" + crAmtLmt + ", crPeriod=" + crPeriod + ", currentBal=" + currentBal
					+ ", lclSpaceOccupied=" + lclSpaceOccupied + ", impHubOccupied=" + impHubOccupied
					+ ", expHubOccupied=" + expHubOccupied + ", bondHubOccupied=" + bondHubOccupied
					+ ", lclAreaOccupied=" + lclAreaOccupied + ", imphAreaOccupied=" + imphAreaOccupied
					+ ", exphAreaOccupied=" + exphAreaOccupied + ", bondhAreaOccupied=" + bondhAreaOccupied
					+ ", slType=" + slType + ", upDockType=" + upDockType + ", vnd=" + vnd + ", acton=" + acton
					+ ", crtlbr=" + crtlbr + ", dstlbr=" + dstlbr + ", dstrct=" + dstrct + ", eqpmsp=" + eqpmsp
					+ ", fmgtN=" + fmgtN + ", scnopr=" + scnopr + ", stuflb=" + stuflb + ", subctr=" + subctr
					+ ", survey=" + survey + ", trns=" + trns + ", valer=" + valer + ", resendFlag=" + resendFlag
					+ ", uiFlag=" + uiFlag + ", doSubmitReqFlag=" + doSubmitReqFlag + ", nvoccTariff=" + nvoccTariff
					+ ", offdocTariff=" + offdocTariff + ", leschaco=" + leschaco + ", crAmtLmtUse=" + crAmtLmtUse
					+ ", monthlyAgentReportFlag=" + monthlyAgentReportFlag + ", weeklyLclAgentReportFlag="
					+ weeklyLclAgentReportFlag + ", invoiceFromMail=" + invoiceFromMail + ", cycle=" + cycle
					+ ", dmrInterval=" + dmrInterval + "]";
		}
	    
		//for gettimg cha  data 
		
				public Party(String companyId, String branchId, String partyId, String partyName, String partyType,
						String createdBy, String editedBy, String approvedBy, String bondnocWeek) {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.partyId = partyId;
					this.partyName = partyName;
					this.partyType = partyType;
					this.createdBy = createdBy;
					this.editedBy = editedBy;
					this.approvedBy = approvedBy;
					this.bondnocWeek = bondnocWeek;
				}



				public Party(String companyId, String branchId, String partyId,String iecCode, String partyName, String address1,
						String address2, String address3, String state, String gstNo,String pin) {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.partyId = partyId;
					this.iecCode = iecCode;
					this.partyName = partyName;
					this.address1 = address1;
					this.address2 = address2;
					this.address3 = address3;
					this.state = state;
					this.gstNo = gstNo;
					this.pin = pin;
					
				}
				
				
				public Party(String partyId, String partyName, String customerCode) {
					super();
					this.partyId = partyId;
					this.partyName = partyName;
					this.customerCode = customerCode;
				}
				
				public Party(String companyId, String branchId, String partyId, String partyName, String partyType,
						String customerCode, String editedBy, String approvedBy, String bondnocWeek,String address1,String address2,String address3) {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.partyId = partyId;
					this.partyName = partyName;
					this.partyType = partyType;
					this.customerCode = customerCode;
					this.editedBy = editedBy;
					this.approvedBy = approvedBy;
					this.bondnocWeek = bondnocWeek;
					this.address1 = address1;
					this.address2 = address2;
					this.address3 = address3;
				}
	    
	    
}

