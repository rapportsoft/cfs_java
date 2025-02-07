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
import jakarta.persistence.Transient;

@Entity
@Table(name="cfassesmentsheet")
@IdClass(AssessmentSheetId.class)
public class AssessmentSheet implements Cloneable {


    @Id
    @Column(name = "Assesment_Id", length = 20)
    private String assesmentId;

    @Id
    @Column(name = "Company_Id", length = 6)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6)
    private String branchId;

    @Id
    @Column(name = "Assesment_Line_No", length = 5)
    private String assesmentLineNo;
    
    @Id
    @Column(name = "Trans_Type", length = 30)
    private String transType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Assesment_Date")
    private Date assesmentDate;

    @Column(name = "IGM_Trans_Id", length = 10)
    private String igmTransId;

    @Column(name = "SB_Trans_Id", length = 10)
    private String sbTransId;

    @Column(name = "imp_Sr_no")
    private int impSrNo = 1;

    @Column(name = "Profitcentre_Id", length = 6)
    private String profitcentreId;

    @Column(name = "Igm_No", length = 25)
    private String igmNo;

    @Column(name = "SB_No", length = 20)
    private String sbNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "SB_Date")
    private Date sbDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "IGM_Date")
    private Date igmDate;

    @Column(name = "igm_line_no", length = 10)
    private String igmLineNo;

    @Column(name = "VIA_NO", length = 10)
    private String viaNo;

    @Column(name = "bl_no", length = 50)
    private String blNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BL_Date")
    private Date blDate;

    @Column(name = "Billing_Party", length = 6)
    private String billingParty;

    @Column(name = "IGST", length = 1)
    private String igst;

    @Column(name = "CGST", length = 1)
    private String cgst;

    @Column(name = "SGST", length = 1)
    private String sgst;

    @Column(name = "Importer_Id", length = 7)
    private String importerId;

    @Column(name = "Importer_Name", length = 100)
    private String importerName;

    @Column(name = "Exporter_Name", length = 100)
    private String exporterName;

    @Column(name = "Commodity_Description", length = 250)
    private String commodityDescription;

    @Column(name = "commodity_Code", length = 20)
    private String commodityCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Exam_Date")
    private Date examDate;

    @Column(name = "Area_Used", precision = 10, scale = 3)
    private BigDecimal areaUsed = BigDecimal.ZERO;

    @Column(name = "No_Of_Packages", precision = 8, scale = 0)
    private BigDecimal noOfPackages = BigDecimal.ZERO;

    @Column(name = "Carted_Packages", precision = 8, scale = 0)
    private BigDecimal cartedPackages = BigDecimal.ZERO;

    @Column(name = "Container_no", length = 11)
    private String containerNo;

    @Column(name = "Container_Size", length = 6)
    private String containerSize;

    @Column(name = "Container_Type", length = 6)
    private String containerType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Gate_In_Date")
    private Date gateInDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Stuff_Tally_Date")
    private Date stuffTallyDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Mov_Req_Date")
    private Date movReqDate;
    
    @Column(name = "Min_Carting_Trans_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date minCartingTransDate;

    @Column(name = "Movement_Req_Id", length = 20)
    private String movementReqId = "";

    @Column(name = "Gate_Out_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateOutDate;

    @Column(name = "Gross_Weight", precision = 12, scale = 3)
    private BigDecimal grossWeight = BigDecimal.ZERO;

    @Column(name = "SBWeight", precision = 16, scale = 3)
    private BigDecimal sbWeight = BigDecimal.ZERO;

    @Column(name = "Gate_out_Type", length = 6)
    private String gateOutType = "";

    @Column(name = "Auction_Status", length = 1)
    private String auctionStatus = "N";

    @Column(name = "Count_Auc_Notice_Chrg", length = 1)
    private String countAucNoticeChrg = "0";

    @Column(name = "Special_Delivery", length = 6)
    private String specialDelivery = "NA";

    @Column(name = "Agro_Product_Status", length = 1)
    private String agroProductStatus = "N";

    @Column(name = "cha_Sr_no")
    private int chaSrNo = 1;

    @Column(name = "CHA", length = 6)
    private String cha = "";

    @Column(name = "SA", length = 6)
    private String sa = "";

    @Column(name = "SL", length = 6)
    private String sl = "";

    @Column(name = "Acc_Sr_no")
    private int accSrNo = 1;

    @Column(name = "On_Account_Of", length = 6)
    private String onAccountOf = "";

    @Column(name = "Party_Id", length = 6)
    private String partyId = "";

    @Column(name = "party_Sr_no", precision = 8, scale = 0)
    private BigDecimal partySrNo = BigDecimal.ZERO;

    @Column(name = "Type_of_Container", length = 30)
    private String typeOfContainer = "";

    @Column(name = "Type_of_Cargo", length = 10)
    private String typeOfCargo = "";

    @Column(name = "Scaner_Type", length = 20)
    private String scanerType = "";

    @Column(name = "Examined_Percentage", precision = 3, scale = 0)
    private BigDecimal examinedPercentage = BigDecimal.ZERO;

    @Column(name = "Seal_Cutting_Type", length = 20)
    private String sealCuttingType = "";

    @Column(name = "Weighment_Flag", length = 1)
    private String weighmentFlag = "N";

    @Column(name = "Labour", length = 1)
    private String labour = "N";

    @Column(name = "Fk_3MT", length = 1)
    private String fk3MT = "N";

    @Column(name = "Fk_5MT", length = 1)
    private String fk5MT = "N";

    @Column(name = "Fk_10MT", length = 1)
    private String fk10MT = "N";

    @Column(name = "Hydra_12MT", length = 1)
    private String hydra12MT = "N";

    @Column(name = "Crane", length = 1)
    private String crane = "N";

    @Column(name = "Kalmar", length = 1)
    private String kalmar = "N";
    
    @Column(name = "Pusher", length = 1)
    private char pusher = 'N';

    @Column(name = "Empty_Use", length = 1)
    private char emptyUse = 'N';

    @Column(name = "Clamp", length = 1)
    private char clamp = 'N';

    @Column(name = "carpenter", length = 1)
    private char carpenter = 'N';

    @Column(name = "Online_Stamp_Duty", length = 1)
    private char onlineStampDuty = 'N';

    @Column(name = "Cargo_Weight", precision = 12, scale = 3)
    private BigDecimal cargoWeight = BigDecimal.ZERO;

    @Column(name = "Destuff_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date destuffDate;

    @Column(name = "Calculate_Invoice", length = 1)
    private Character calculateInvoice;

    @Column(name = "Gatepass_allowed", length = 1)
    private char gatepassAllowed = 'N';

    @Column(name = "insurance_Value", precision = 15, scale = 3)
    private BigDecimal insuranceValue = BigDecimal.ZERO;

    @Column(name = "duty_Value", precision = 15, scale = 3)
    private BigDecimal dutyValue = BigDecimal.ZERO;

    @Column(name = "Invoice_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "invoice_Cal_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceCalDate;

    @Column(name = "Invoice_Upto_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceUptoDate;

    @Column(name = "NOC_Validity_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocValidityDate;

    @Column(name = "NOC_From_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nocFromDate;

    @Column(name = "NOC_WEEKS", precision = 8, scale = 0)
    private BigDecimal nocWeeks = BigDecimal.ZERO;

    @Column(name = "Movement_Charges", length = 1)
    private char movementCharges = 'N';

    @Column(name = "DPD_Tariff", length = 1)
    private char dpdTariff = 'D';

    @Column(name = "Tax_Applicable", length = 1)
    private char taxApplicable = 'Y';

    @Column(name = "SEZ", length = 1)
    private char sez = 'N';

    @Column(name = "Status", length = 1)
    private char status;

    @Column(name = "Add_Mov_Flag", length = 1)
    private char addMovFlag = 'N';

    @Column(name = "Add_Movement_Amt")
    private int addMovementAmt = 0;

    @Column(name = "Discount_Status", length = 1)
    private char discountStatus = 'N';

    @Column(name = "Discount_Amt")
    private int discountAmt = 0;

    @Column(name = "Discount_Percentage", precision = 4, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Column(name = "Discount_days", length = 2)
    private String discountDays = "0";

    @Column(name = "Free_Days", length = 2)
    private String freeDays = "0";

    @Column(name = "Created_By", length = 10)
    private String createdBy = "";
    
    
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

    @Column(name = "Multy_Invoice_Flag", length = 1)
    private char multyInvoiceFlag = 'S';

    @Column(name = "Invoice_Sr_No")
    private int invoiceSrNo = 1;

    @Column(name = "Block_Move_Allow", length = 1)
    private char blockMoveAllow = 'N';

    @Column(name = "LockDown", length = 1)
    private char lockDown = 'N';

    @Column(name = "Invoice_No", length = 16)
    private String invoiceNo = "";

    @Column(name = "Final_Invoice_Date")
    @Temporal(TemporalType.DATE)
    private Date finalInvoiceDate;

    @Column(name = "Container_Handling_Amt", precision = 16, scale = 3)
    private BigDecimal containerHandlingAmt = BigDecimal.ZERO;

    @Column(name = "Container_Storage_Amt", precision = 16, scale = 3)
    private BigDecimal containerStorageAmt = BigDecimal.ZERO;

    @Column(name = "Credit_Type", length = 1)
    private char creditType = 'N';

    @Column(name = "Invoice_Category", length = 10)
    private String invoiceCategory = "Single";

    @Column(name = "Bill_Amt", precision = 12, scale = 2)
    private BigDecimal billAmt = BigDecimal.ZERO;

    @Column(name = "Invoice_Amt", precision = 12, scale = 2)
    private BigDecimal invoiceAmt = BigDecimal.ZERO;

    @Column(name = "IRN", length = 200)
    private String irn = "";

    @Column(name = "File_No", length = 21)
    private String fileNo = "";

    @Column(name = "RefInvoiceNo", length = 21)
    private String refInvoiceNo = "";

    @Column(name = "Receipt_No", length = 10)
    private String receiptNo = "";

    @Column(name = "Receipt_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiptDate;

    @Column(name = "TDS_Deductee", length = 100)
    private String tdsDeductee = "";

    @Column(name = "TDS_Deductee_Id", length = 20)
    private String tdsDeducteeId = "";

    @Column(name = "TDS", length = 6)
    private String tds = "";

    @Column(name = "Comments", length = 255)
    private String comments = "";

    @Column(name = "Int_Comments", length = 255)
    private String intComments = "";

    @Column(name = "DRT", length = 1)
    private char drt = 'N';

    @Column(name = "cha_rebate_20", precision = 5, scale = 2)
    private BigDecimal chaRebate20 = BigDecimal.ZERO;

    @Column(name = "cha_rebate_40", precision = 5, scale = 2)
    private BigDecimal chaRebate40 = BigDecimal.ZERO;
    
    
    @Column(name = "crg_allow_flag", length = 1)
    private char crgAllowFlag = 'N';

    @Column(name = "Oth_Party_id", length = 10)
    private String othPartyId = "";

    @Column(name = "Oth_Sr_no", length = 3)
    private String othSrNo = "";

    @Column(name = "Equipment", length = 250)
    private String equipment = "";

    @Column(name = "SSR_TRANS_ID", length = 20)
    private String ssrTransId = "";

    @Column(name = "SSR_Service_Id", length = 250)
    private String ssrServiceId = "";

    @Column(name = "Inv_Type", length = 20)
    private String invType = "";

    @Column(name = "Last_Assesment_Id", length = 20)
    private String lastAssesmentId = "";

    @Column(name = "Last_Assesment_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAssesmentDate;

    @Column(name = "Last_Invoice_No", length = 16)
    private String lastInvoiceNo = "";

    @Column(name = "Last_Invoice_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastInvoiceDate;

    @Column(name = "Last_Invoice_Upto_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastInvoiceUptoDate;

    @Column(name = "No_of_20FT", length = 20)
    private String noOf20ft = "";

    @Column(name = "No_of_40FT", length = 20)
    private String noOf40ft = "";

    @Column(name = "EX_Bonding_Id", length = 10)
    private String exBondingId = "";

    @Column(name = "In_Bonding_Id", length = 10)
    private String inBondingId = "";

    @Column(name = "Paying_Party", length = 10)
    private String payingParty = "";

    @Column(name = "Transaction_Type", length = 10)
    private String transactionType = "";

    @Column(name = "STUFFTALLY_WO_TRANS_ID", length = 25)
    private String stuffTallyWoTransId = "";

    @Column(name = "IsBos", length = 1)
    private char isBos = 'N';

    @Column(name = "New_Commodity", length = 50)
    private String newCommodity = "";

    @Column(name = "Invoice_Days_Old", precision = 8, scale = 0)
    private BigDecimal invoiceDaysOld = BigDecimal.ZERO;

    @Column(name = "Invoice_Amt_Old", precision = 16, scale = 3)
    private BigDecimal invoiceAmtOld = BigDecimal.ZERO;

    @Column(name = "OLD_INVOICE_UPTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oldInvoiceUpto;

    @Column(name = "FORCE_ENTRY_FLAG", length = 1)
    private char forceEntryFlag = 'N';

    @Column(name = "Min_GateIn_Trans_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date minGateInTransDate;

    @Column(name = "No_of_Item")
    private int noOfItem = 0;

    @Column(name = "Other_Deduction", precision = 16, scale = 3)
    private BigDecimal otherDeduction = BigDecimal.ZERO;

    @Column(name = "stuff_mode", length = 15)
    private String stuffMode = "";

    @Column(name = "Form_Thirteen_Entry_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date formThirteenEntryDate;

    @Column(name = "Inv_Cat_H", length = 1)
    private char invCatH = 'N';

    @Column(name = "Inv_Cat_G", length = 1)
    private char invCatG = 'N';

    @Column(name = "Is_Ancillary", length = 1)
    private char isAncillary = 'N';

    @Column(name = "Rebate_Cal_Flag", length = 1)
    private char rebateCalFlag = 'N';

    @Column(name = "Customer_Ledger_Flag", length = 1)
    private char customerLedgerFlag = 'N';

    @Column(name = "Src_Collected_Flag", length = 1)
    private char srcCollectedFlag = 'N';
    
    @Column(name = "Payable_Party", length = 6)
    private String payableParty;
    
    
    @Column(name = "Payable_Party_Id", length = 6)
    private String payablePartyId;
    
    @Transient
    private String impAddress;
    
    @Transient
    private String impGst;
    
    @Transient
    private String chaName;
    
    @Transient
    private String chaAddress;
    
    @Transient
    private String chaGst;
    
    @Transient
    private String fwdGst;
    
    @Transient
    private String fwdName;
    
    @Transient
    private String fwdState;
    
    @Transient
    private String accHolderGst;
    
    @Transient
    private String accHolderName;
    
    @Transient
    private String accHolderState;
    
    @Transient
    private String creditAllowed;
    
    @Transient
    private String saId;
    
    @Transient
    private String slId;

	public AssessmentSheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	




	public AssessmentSheet(String assesmentId, String companyId, String branchId, String assesmentLineNo,
			String transType, Date assesmentDate, String igmTransId, String sbTransId, int impSrNo,
			String profitcentreId, String igmNo, String sbNo, Date sbDate, Date igmDate, String igmLineNo, String viaNo,
			String blNo, Date blDate, String billingParty, String igst, String cgst, String sgst, String importerId,
			String importerName, String exporterName, String commodityDescription, String commodityCode, Date examDate,
			BigDecimal areaUsed, BigDecimal noOfPackages, BigDecimal cartedPackages, String containerNo,
			String containerSize, String containerType, Date gateInDate, Date stuffTallyDate, Date movReqDate,
			Date minCartingTransDate, String movementReqId, Date gateOutDate, BigDecimal grossWeight,
			BigDecimal sbWeight, String gateOutType, String auctionStatus, String countAucNoticeChrg,
			String specialDelivery, String agroProductStatus, int chaSrNo, String cha, String sa, String sl,
			int accSrNo, String onAccountOf, String partyId, BigDecimal partySrNo, String typeOfContainer,
			String typeOfCargo, String scanerType, BigDecimal examinedPercentage, String sealCuttingType,
			String weighmentFlag, String labour, String fk3mt, String fk5mt, String fk10mt, String hydra12mt,
			String crane, String kalmar, char pusher, char emptyUse, char clamp, char carpenter, char onlineStampDuty,
			BigDecimal cargoWeight, Date destuffDate, Character calculateInvoice, char gatepassAllowed,
			BigDecimal insuranceValue, BigDecimal dutyValue, Date invoiceDate, Date invoiceCalDate,
			Date invoiceUptoDate, Date nocValidityDate, Date nocFromDate, BigDecimal nocWeeks, char movementCharges,
			char dpdTariff, char taxApplicable, char sez, char status, char addMovFlag, int addMovementAmt,
			char discountStatus, int discountAmt, BigDecimal discountPercentage, String discountDays, String freeDays,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			char multyInvoiceFlag, int invoiceSrNo, char blockMoveAllow, char lockDown, String invoiceNo,
			Date finalInvoiceDate, BigDecimal containerHandlingAmt, BigDecimal containerStorageAmt, char creditType,
			String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt, String irn, String fileNo,
			String refInvoiceNo, String receiptNo, Date receiptDate, String tdsDeductee, String tdsDeducteeId,
			String tds, String comments, String intComments, char drt, BigDecimal chaRebate20, BigDecimal chaRebate40,
			char crgAllowFlag, String othPartyId, String othSrNo, String equipment, String ssrTransId,
			String ssrServiceId, String invType, String lastAssesmentId, Date lastAssesmentDate, String lastInvoiceNo,
			Date lastInvoiceDate, Date lastInvoiceUptoDate, String noOf20ft, String noOf40ft, String exBondingId,
			String inBondingId, String payingParty, String transactionType, String stuffTallyWoTransId, char isBos,
			String newCommodity, BigDecimal invoiceDaysOld, BigDecimal invoiceAmtOld, Date oldInvoiceUpto,
			char forceEntryFlag, Date minGateInTransDate, int noOfItem, BigDecimal otherDeduction, String stuffMode,
			Date formThirteenEntryDate, char invCatH, char invCatG, char isAncillary, char rebateCalFlag,
			char customerLedgerFlag, char srcCollectedFlag, String payableParty, String payablePartyId,
			String impAddress, String impGst, String chaName, String chaAddress, String chaGst, String fwdGst,
			String fwdName, String fwdState, String accHolderGst, String accHolderName, String accHolderState,
			String creditAllowed, String saId, String slId) {
		this.assesmentId = assesmentId;
		this.companyId = companyId;
		this.branchId = branchId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
		this.assesmentDate = assesmentDate;
		this.igmTransId = igmTransId;
		this.sbTransId = sbTransId;
		this.impSrNo = impSrNo;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.viaNo = viaNo;
		this.blNo = blNo;
		this.blDate = blDate;
		this.billingParty = billingParty;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
		this.importerId = importerId;
		this.importerName = importerName;
		this.exporterName = exporterName;
		this.commodityDescription = commodityDescription;
		this.commodityCode = commodityCode;
		this.examDate = examDate;
		this.areaUsed = areaUsed;
		this.noOfPackages = noOfPackages;
		this.cartedPackages = cartedPackages;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateInDate = gateInDate;
		this.stuffTallyDate = stuffTallyDate;
		this.movReqDate = movReqDate;
		this.minCartingTransDate = minCartingTransDate;
		this.movementReqId = movementReqId;
		this.gateOutDate = gateOutDate;
		this.grossWeight = grossWeight;
		this.sbWeight = sbWeight;
		this.gateOutType = gateOutType;
		this.auctionStatus = auctionStatus;
		this.countAucNoticeChrg = countAucNoticeChrg;
		this.specialDelivery = specialDelivery;
		this.agroProductStatus = agroProductStatus;
		this.chaSrNo = chaSrNo;
		this.cha = cha;
		this.sa = sa;
		this.sl = sl;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.partyId = partyId;
		this.partySrNo = partySrNo;
		this.typeOfContainer = typeOfContainer;
		this.typeOfCargo = typeOfCargo;
		this.scanerType = scanerType;
		this.examinedPercentage = examinedPercentage;
		this.sealCuttingType = sealCuttingType;
		this.weighmentFlag = weighmentFlag;
		this.labour = labour;
		fk3MT = fk3mt;
		fk5MT = fk5mt;
		fk10MT = fk10mt;
		hydra12MT = hydra12mt;
		this.crane = crane;
		this.kalmar = kalmar;
		this.pusher = pusher;
		this.emptyUse = emptyUse;
		this.clamp = clamp;
		this.carpenter = carpenter;
		this.onlineStampDuty = onlineStampDuty;
		this.cargoWeight = cargoWeight;
		this.destuffDate = destuffDate;
		this.calculateInvoice = calculateInvoice;
		this.gatepassAllowed = gatepassAllowed;
		this.insuranceValue = insuranceValue;
		this.dutyValue = dutyValue;
		this.invoiceDate = invoiceDate;
		this.invoiceCalDate = invoiceCalDate;
		this.invoiceUptoDate = invoiceUptoDate;
		this.nocValidityDate = nocValidityDate;
		this.nocFromDate = nocFromDate;
		this.nocWeeks = nocWeeks;
		this.movementCharges = movementCharges;
		this.dpdTariff = dpdTariff;
		this.taxApplicable = taxApplicable;
		this.sez = sez;
		this.status = status;
		this.addMovFlag = addMovFlag;
		this.addMovementAmt = addMovementAmt;
		this.discountStatus = discountStatus;
		this.discountAmt = discountAmt;
		this.discountPercentage = discountPercentage;
		this.discountDays = discountDays;
		this.freeDays = freeDays;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.multyInvoiceFlag = multyInvoiceFlag;
		this.invoiceSrNo = invoiceSrNo;
		this.blockMoveAllow = blockMoveAllow;
		this.lockDown = lockDown;
		this.invoiceNo = invoiceNo;
		this.finalInvoiceDate = finalInvoiceDate;
		this.containerHandlingAmt = containerHandlingAmt;
		this.containerStorageAmt = containerStorageAmt;
		this.creditType = creditType;
		this.invoiceCategory = invoiceCategory;
		this.billAmt = billAmt;
		this.invoiceAmt = invoiceAmt;
		this.irn = irn;
		this.fileNo = fileNo;
		this.refInvoiceNo = refInvoiceNo;
		this.receiptNo = receiptNo;
		this.receiptDate = receiptDate;
		this.tdsDeductee = tdsDeductee;
		this.tdsDeducteeId = tdsDeducteeId;
		this.tds = tds;
		this.comments = comments;
		this.intComments = intComments;
		this.drt = drt;
		this.chaRebate20 = chaRebate20;
		this.chaRebate40 = chaRebate40;
		this.crgAllowFlag = crgAllowFlag;
		this.othPartyId = othPartyId;
		this.othSrNo = othSrNo;
		this.equipment = equipment;
		this.ssrTransId = ssrTransId;
		this.ssrServiceId = ssrServiceId;
		this.invType = invType;
		this.lastAssesmentId = lastAssesmentId;
		this.lastAssesmentDate = lastAssesmentDate;
		this.lastInvoiceNo = lastInvoiceNo;
		this.lastInvoiceDate = lastInvoiceDate;
		this.lastInvoiceUptoDate = lastInvoiceUptoDate;
		this.noOf20ft = noOf20ft;
		this.noOf40ft = noOf40ft;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.payingParty = payingParty;
		this.transactionType = transactionType;
		this.stuffTallyWoTransId = stuffTallyWoTransId;
		this.isBos = isBos;
		this.newCommodity = newCommodity;
		this.invoiceDaysOld = invoiceDaysOld;
		this.invoiceAmtOld = invoiceAmtOld;
		this.oldInvoiceUpto = oldInvoiceUpto;
		this.forceEntryFlag = forceEntryFlag;
		this.minGateInTransDate = minGateInTransDate;
		this.noOfItem = noOfItem;
		this.otherDeduction = otherDeduction;
		this.stuffMode = stuffMode;
		this.formThirteenEntryDate = formThirteenEntryDate;
		this.invCatH = invCatH;
		this.invCatG = invCatG;
		this.isAncillary = isAncillary;
		this.rebateCalFlag = rebateCalFlag;
		this.customerLedgerFlag = customerLedgerFlag;
		this.srcCollectedFlag = srcCollectedFlag;
		this.payableParty = payableParty;
		this.payablePartyId = payablePartyId;
		this.impAddress = impAddress;
		this.impGst = impGst;
		this.chaName = chaName;
		this.chaAddress = chaAddress;
		this.chaGst = chaGst;
		this.fwdGst = fwdGst;
		this.fwdName = fwdName;
		this.fwdState = fwdState;
		this.accHolderGst = accHolderGst;
		this.accHolderName = accHolderName;
		this.accHolderState = accHolderState;
		this.creditAllowed = creditAllowed;
		this.saId = saId;
		this.slId = slId;
	}






	public String getPayablePartyId() {
		return payablePartyId;
	}






	public void setPayablePartyId(String payablePartyId) {
		this.payablePartyId = payablePartyId;
	}






	public String getPayableParty() {
		return payableParty;
	}





	public void setPayableParty(String payableParty) {
		this.payableParty = payableParty;
	}





	public String getCreditAllowed() {
		return creditAllowed;
	}




	public void setCreditAllowed(String creditAllowed) {
		this.creditAllowed = creditAllowed;
	}




	public String getChaName() {
		return chaName;
	}







	public void setChaName(String chaName) {
		this.chaName = chaName;
	}







	public String getFwdName() {
		return fwdName;
	}














	public void setFwdName(String fwdName) {
		this.fwdName = fwdName;
	}














	public String getAccHolderName() {
		return accHolderName;
	}







	public String getSaId() {
		return saId;
	}







	public void setSaId(String saId) {
		this.saId = saId;
	}







	public String getSlId() {
		return slId;
	}







	public void setSlId(String slId) {
		this.slId = slId;
	}







	public void setAccHolderName(String accHolderName) {
		this.accHolderName = accHolderName;
	}







	public String getAssesmentId() {
		return assesmentId;
	}

	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
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

	public String getAssesmentLineNo() {
		return assesmentLineNo;
	}

	public void setAssesmentLineNo(String assesmentLineNo) {
		this.assesmentLineNo = assesmentLineNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getAssesmentDate() {
		return assesmentDate;
	}

	public void setAssesmentDate(Date assesmentDate) {
		this.assesmentDate = assesmentDate;
	}

	public String getIgmTransId() {
		return igmTransId;
	}

	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public int getImpSrNo() {
		return impSrNo;
	}

	public void setImpSrNo(int impSrNo) {
		this.impSrNo = impSrNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public Date getSbDate() {
		return sbDate;
	}

	public void setSbDate(Date sbDate) {
		this.sbDate = sbDate;
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

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}

	public String getBlNo() {
		return blNo;
	}

	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}

	public Date getBlDate() {
		return blDate;
	}

	public void setBlDate(Date blDate) {
		this.blDate = blDate;
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

	public String getExporterName() {
		return exporterName;
	}

	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
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

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public BigDecimal getAreaUsed() {
		return areaUsed;
	}

	public void setAreaUsed(BigDecimal areaUsed) {
		this.areaUsed = areaUsed;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public BigDecimal getCartedPackages() {
		return cartedPackages;
	}

	public void setCartedPackages(BigDecimal cartedPackages) {
		this.cartedPackages = cartedPackages;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public Date getStuffTallyDate() {
		return stuffTallyDate;
	}

	public void setStuffTallyDate(Date stuffTallyDate) {
		this.stuffTallyDate = stuffTallyDate;
	}

	public Date getMovReqDate() {
		return movReqDate;
	}

	public void setMovReqDate(Date movReqDate) {
		this.movReqDate = movReqDate;
	}

	public Date getMinCartingTransDate() {
		return minCartingTransDate;
	}

	public void setMinCartingTransDate(Date minCartingTransDate) {
		this.minCartingTransDate = minCartingTransDate;
	}

	public String getMovementReqId() {
		return movementReqId;
	}

	public void setMovementReqId(String movementReqId) {
		this.movementReqId = movementReqId;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getSbWeight() {
		return sbWeight;
	}

	public void setSbWeight(BigDecimal sbWeight) {
		this.sbWeight = sbWeight;
	}

	public String getGateOutType() {
		return gateOutType;
	}

	public void setGateOutType(String gateOutType) {
		this.gateOutType = gateOutType;
	}

	public String getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public String getCountAucNoticeChrg() {
		return countAucNoticeChrg;
	}

	public void setCountAucNoticeChrg(String countAucNoticeChrg) {
		this.countAucNoticeChrg = countAucNoticeChrg;
	}

	public String getSpecialDelivery() {
		return specialDelivery;
	}

	public void setSpecialDelivery(String specialDelivery) {
		this.specialDelivery = specialDelivery;
	}

	public String getAgroProductStatus() {
		return agroProductStatus;
	}

	public void setAgroProductStatus(String agroProductStatus) {
		this.agroProductStatus = agroProductStatus;
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

	public String getSa() {
		return sa;
	}

	public void setSa(String sa) {
		this.sa = sa;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
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

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public BigDecimal getPartySrNo() {
		return partySrNo;
	}

	public void setPartySrNo(BigDecimal partySrNo) {
		this.partySrNo = partySrNo;
	}

	public String getTypeOfContainer() {
		return typeOfContainer;
	}

	public void setTypeOfContainer(String typeOfContainer) {
		this.typeOfContainer = typeOfContainer;
	}

	public String getTypeOfCargo() {
		return typeOfCargo;
	}

	public void setTypeOfCargo(String typeOfCargo) {
		this.typeOfCargo = typeOfCargo;
	}

	public String getScanerType() {
		return scanerType;
	}

	public void setScanerType(String scanerType) {
		this.scanerType = scanerType;
	}

	public BigDecimal getExaminedPercentage() {
		return examinedPercentage;
	}

	public void setExaminedPercentage(BigDecimal examinedPercentage) {
		this.examinedPercentage = examinedPercentage;
	}

	public String getSealCuttingType() {
		return sealCuttingType;
	}

	public void setSealCuttingType(String sealCuttingType) {
		this.sealCuttingType = sealCuttingType;
	}

	public String getWeighmentFlag() {
		return weighmentFlag;
	}

	public void setWeighmentFlag(String weighmentFlag) {
		this.weighmentFlag = weighmentFlag;
	}

	public String getLabour() {
		return labour;
	}

	public void setLabour(String labour) {
		this.labour = labour;
	}

	public String getFk3MT() {
		return fk3MT;
	}

	public void setFk3MT(String fk3mt) {
		fk3MT = fk3mt;
	}

	public String getFk5MT() {
		return fk5MT;
	}

	public void setFk5MT(String fk5mt) {
		fk5MT = fk5mt;
	}

	public String getFk10MT() {
		return fk10MT;
	}

	public void setFk10MT(String fk10mt) {
		fk10MT = fk10mt;
	}

	public String getHydra12MT() {
		return hydra12MT;
	}

	public void setHydra12MT(String hydra12mt) {
		hydra12MT = hydra12mt;
	}

	public String getCrane() {
		return crane;
	}

	public void setCrane(String crane) {
		this.crane = crane;
	}

	public String getKalmar() {
		return kalmar;
	}

	public void setKalmar(String kalmar) {
		this.kalmar = kalmar;
	}

	public char getPusher() {
		return pusher;
	}

	public void setPusher(char pusher) {
		this.pusher = pusher;
	}

	public char getEmptyUse() {
		return emptyUse;
	}

	public void setEmptyUse(char emptyUse) {
		this.emptyUse = emptyUse;
	}

	public char getClamp() {
		return clamp;
	}

	public void setClamp(char clamp) {
		this.clamp = clamp;
	}

	public char getCarpenter() {
		return carpenter;
	}

	public void setCarpenter(char carpenter) {
		this.carpenter = carpenter;
	}

	public char getOnlineStampDuty() {
		return onlineStampDuty;
	}

	public void setOnlineStampDuty(char onlineStampDuty) {
		this.onlineStampDuty = onlineStampDuty;
	}

	public BigDecimal getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(BigDecimal cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public Date getDestuffDate() {
		return destuffDate;
	}

	public void setDestuffDate(Date destuffDate) {
		this.destuffDate = destuffDate;
	}

	public Character getCalculateInvoice() {
		return calculateInvoice;
	}

	public void setCalculateInvoice(Character calculateInvoice) {
		this.calculateInvoice = calculateInvoice;
	}

	public char getGatepassAllowed() {
		return gatepassAllowed;
	}

	public void setGatepassAllowed(char gatepassAllowed) {
		this.gatepassAllowed = gatepassAllowed;
	}

	public BigDecimal getInsuranceValue() {
		return insuranceValue;
	}

	public void setInsuranceValue(BigDecimal insuranceValue) {
		this.insuranceValue = insuranceValue;
	}

	public BigDecimal getDutyValue() {
		return dutyValue;
	}

	public void setDutyValue(BigDecimal dutyValue) {
		this.dutyValue = dutyValue;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getInvoiceCalDate() {
		return invoiceCalDate;
	}

	public void setInvoiceCalDate(Date invoiceCalDate) {
		this.invoiceCalDate = invoiceCalDate;
	}

	public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}

	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
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

	public BigDecimal getNocWeeks() {
		return nocWeeks;
	}

	public void setNocWeeks(BigDecimal nocWeeks) {
		this.nocWeeks = nocWeeks;
	}

	public char getMovementCharges() {
		return movementCharges;
	}

	public void setMovementCharges(char movementCharges) {
		this.movementCharges = movementCharges;
	}

	public char getDpdTariff() {
		return dpdTariff;
	}

	public void setDpdTariff(char dpdTariff) {
		this.dpdTariff = dpdTariff;
	}

	public char getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(char taxApplicable) {
		this.taxApplicable = taxApplicable;
	}

	public char getSez() {
		return sez;
	}

	public void setSez(char sez) {
		this.sez = sez;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getAddMovFlag() {
		return addMovFlag;
	}

	public void setAddMovFlag(char addMovFlag) {
		this.addMovFlag = addMovFlag;
	}

	public int getAddMovementAmt() {
		return addMovementAmt;
	}

	public void setAddMovementAmt(int addMovementAmt) {
		this.addMovementAmt = addMovementAmt;
	}

	public char getDiscountStatus() {
		return discountStatus;
	}

	public void setDiscountStatus(char discountStatus) {
		this.discountStatus = discountStatus;
	}

	public int getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(int discountAmt) {
		this.discountAmt = discountAmt;
	}

	public BigDecimal getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
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

	public char getMultyInvoiceFlag() {
		return multyInvoiceFlag;
	}

	public void setMultyInvoiceFlag(char multyInvoiceFlag) {
		this.multyInvoiceFlag = multyInvoiceFlag;
	}

	public int getInvoiceSrNo() {
		return invoiceSrNo;
	}

	public void setInvoiceSrNo(int invoiceSrNo) {
		this.invoiceSrNo = invoiceSrNo;
	}

	public char getBlockMoveAllow() {
		return blockMoveAllow;
	}

	public void setBlockMoveAllow(char blockMoveAllow) {
		this.blockMoveAllow = blockMoveAllow;
	}

	public char getLockDown() {
		return lockDown;
	}

	public void setLockDown(char lockDown) {
		this.lockDown = lockDown;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getFinalInvoiceDate() {
		return finalInvoiceDate;
	}

	public void setFinalInvoiceDate(Date finalInvoiceDate) {
		this.finalInvoiceDate = finalInvoiceDate;
	}

	public BigDecimal getContainerHandlingAmt() {
		return containerHandlingAmt;
	}

	public void setContainerHandlingAmt(BigDecimal containerHandlingAmt) {
		this.containerHandlingAmt = containerHandlingAmt;
	}

	public BigDecimal getContainerStorageAmt() {
		return containerStorageAmt;
	}

	public void setContainerStorageAmt(BigDecimal containerStorageAmt) {
		this.containerStorageAmt = containerStorageAmt;
	}

	public char getCreditType() {
		return creditType;
	}

	public void setCreditType(char creditType) {
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

	public String getIrn() {
		return irn;
	}

	public void setIrn(String irn) {
		this.irn = irn;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getRefInvoiceNo() {
		return refInvoiceNo;
	}

	public void setRefInvoiceNo(String refInvoiceNo) {
		this.refInvoiceNo = refInvoiceNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getTdsDeductee() {
		return tdsDeductee;
	}

	public void setTdsDeductee(String tdsDeductee) {
		this.tdsDeductee = tdsDeductee;
	}

	public String getTdsDeducteeId() {
		return tdsDeducteeId;
	}

	public void setTdsDeducteeId(String tdsDeducteeId) {
		this.tdsDeducteeId = tdsDeducteeId;
	}

	public String getTds() {
		return tds;
	}

	public void setTds(String tds) {
		this.tds = tds;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIntComments() {
		return intComments;
	}

	public void setIntComments(String intComments) {
		this.intComments = intComments;
	}

	public char getDrt() {
		return drt;
	}

	public void setDrt(char drt) {
		this.drt = drt;
	}

	public BigDecimal getChaRebate20() {
		return chaRebate20;
	}

	public void setChaRebate20(BigDecimal chaRebate20) {
		this.chaRebate20 = chaRebate20;
	}

	public BigDecimal getChaRebate40() {
		return chaRebate40;
	}

	public void setChaRebate40(BigDecimal chaRebate40) {
		this.chaRebate40 = chaRebate40;
	}

	public char getCrgAllowFlag() {
		return crgAllowFlag;
	}

	public void setCrgAllowFlag(char crgAllowFlag) {
		this.crgAllowFlag = crgAllowFlag;
	}

	public String getOthPartyId() {
		return othPartyId;
	}

	public void setOthPartyId(String othPartyId) {
		this.othPartyId = othPartyId;
	}

	public String getOthSrNo() {
		return othSrNo;
	}

	public void setOthSrNo(String othSrNo) {
		this.othSrNo = othSrNo;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getSsrTransId() {
		return ssrTransId;
	}

	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}

	public String getSsrServiceId() {
		return ssrServiceId;
	}

	public void setSsrServiceId(String ssrServiceId) {
		this.ssrServiceId = ssrServiceId;
	}

	public String getInvType() {
		return invType;
	}

	public void setInvType(String invType) {
		this.invType = invType;
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

	public Date getLastInvoiceUptoDate() {
		return lastInvoiceUptoDate;
	}

	public void setLastInvoiceUptoDate(Date lastInvoiceUptoDate) {
		this.lastInvoiceUptoDate = lastInvoiceUptoDate;
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

	public String getPayingParty() {
		return payingParty;
	}

	public void setPayingParty(String payingParty) {
		this.payingParty = payingParty;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStuffTallyWoTransId() {
		return stuffTallyWoTransId;
	}

	public void setStuffTallyWoTransId(String stuffTallyWoTransId) {
		this.stuffTallyWoTransId = stuffTallyWoTransId;
	}

	public char getIsBos() {
		return isBos;
	}

	public void setIsBos(char isBos) {
		this.isBos = isBos;
	}

	public String getNewCommodity() {
		return newCommodity;
	}

	public void setNewCommodity(String newCommodity) {
		this.newCommodity = newCommodity;
	}

	public BigDecimal getInvoiceDaysOld() {
		return invoiceDaysOld;
	}

	public void setInvoiceDaysOld(BigDecimal invoiceDaysOld) {
		this.invoiceDaysOld = invoiceDaysOld;
	}

	public BigDecimal getInvoiceAmtOld() {
		return invoiceAmtOld;
	}

	public void setInvoiceAmtOld(BigDecimal invoiceAmtOld) {
		this.invoiceAmtOld = invoiceAmtOld;
	}

	public Date getOldInvoiceUpto() {
		return oldInvoiceUpto;
	}

	public void setOldInvoiceUpto(Date oldInvoiceUpto) {
		this.oldInvoiceUpto = oldInvoiceUpto;
	}

	public char getForceEntryFlag() {
		return forceEntryFlag;
	}

	public void setForceEntryFlag(char forceEntryFlag) {
		this.forceEntryFlag = forceEntryFlag;
	}

	public Date getMinGateInTransDate() {
		return minGateInTransDate;
	}

	public void setMinGateInTransDate(Date minGateInTransDate) {
		this.minGateInTransDate = minGateInTransDate;
	}

	public int getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
	}

	public BigDecimal getOtherDeduction() {
		return otherDeduction;
	}

	public void setOtherDeduction(BigDecimal otherDeduction) {
		this.otherDeduction = otherDeduction;
	}

	public String getStuffMode() {
		return stuffMode;
	}

	public void setStuffMode(String stuffMode) {
		this.stuffMode = stuffMode;
	}

	public Date getFormThirteenEntryDate() {
		return formThirteenEntryDate;
	}

	public void setFormThirteenEntryDate(Date formThirteenEntryDate) {
		this.formThirteenEntryDate = formThirteenEntryDate;
	}

	public char getInvCatH() {
		return invCatH;
	}

	public void setInvCatH(char invCatH) {
		this.invCatH = invCatH;
	}

	public char getInvCatG() {
		return invCatG;
	}

	public void setInvCatG(char invCatG) {
		this.invCatG = invCatG;
	}

	public char getIsAncillary() {
		return isAncillary;
	}

	public void setIsAncillary(char isAncillary) {
		this.isAncillary = isAncillary;
	}

	public char getRebateCalFlag() {
		return rebateCalFlag;
	}

	public void setRebateCalFlag(char rebateCalFlag) {
		this.rebateCalFlag = rebateCalFlag;
	}

	public char getCustomerLedgerFlag() {
		return customerLedgerFlag;
	}

	public void setCustomerLedgerFlag(char customerLedgerFlag) {
		this.customerLedgerFlag = customerLedgerFlag;
	}

	public char getSrcCollectedFlag() {
		return srcCollectedFlag;
	}

	public void setSrcCollectedFlag(char srcCollectedFlag) {
		this.srcCollectedFlag = srcCollectedFlag;
	}

	public String getImpAddress() {
		return impAddress;
	}

	public void setImpAddress(String impAddress) {
		this.impAddress = impAddress;
	}

	public String getImpGst() {
		return impGst;
	}

	public void setImpGst(String impGst) {
		this.impGst = impGst;
	}

	public String getChaAddress() {
		return chaAddress;
	}

	public void setChaAddress(String chaAddress) {
		this.chaAddress = chaAddress;
	}

	public String getChaGst() {
		return chaGst;
	}

	public void setChaGst(String chaGst) {
		this.chaGst = chaGst;
	}

	public String getFwdGst() {
		return fwdGst;
	}

	public void setFwdGst(String fwdGst) {
		this.fwdGst = fwdGst;
	}

	public String getFwdState() {
		return fwdState;
	}

	public void setFwdState(String fwdState) {
		this.fwdState = fwdState;
	}

	public String getAccHolderGst() {
		return accHolderGst;
	}

	public void setAccHolderGst(String accHolderGst) {
		this.accHolderGst = accHolderGst;
	}

	public String getAccHolderState() {
		return accHolderState;
	}

	public void setAccHolderState(String accHolderState) {
		this.accHolderState = accHolderState;
	}




	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
    
//	Export Container Invoice
	
	    transient private String profitcentreName;
	    transient private String cartingTransId;
	    transient private String cargoSbNo;
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    transient private Date cartingDate;	    
	    transient private String movementType;    
	    @Transient
	    private String expGst;
	    
	    @Transient
	    private String expTanNo;
	    
	    @Transient
	    private String expStateCode;    
	    
	  	    
	    @Transient
	    private String pendingCredit;
	    
	    
	    

	public String getAccHolderGst2() {
			return accHolderGst2;
		}






		public void setAccHolderGst2(String accHolderGst2) {
			this.accHolderGst2 = accHolderGst2;
		}






		public String getAccHolderState2() {
			return accHolderState2;
		}






		public void setAccHolderState2(String accHolderState2) {
			this.accHolderState2 = accHolderState2;
		}






		public String getAccStateCode2() {
			return accStateCode2;
		}






		public void setAccStateCode2(String accStateCode2) {
			this.accStateCode2 = accStateCode2;
		}






		public String getAccAddress2() {
			return accAddress2;
		}






		public void setAccAddress2(String accAddress2) {
			this.accAddress2 = accAddress2;
		}






	public String getProfitcentreName() {
			return profitcentreName;
		}






		public void setProfitcentreName(String profitcentreName) {
			this.profitcentreName = profitcentreName;
		}






		public String getCartingTransId() {
			return cartingTransId;
		}






		public void setCartingTransId(String cartingTransId) {
			this.cartingTransId = cartingTransId;
		}






		public String getCargoSbNo() {
			return cargoSbNo;
		}






		public void setCargoSbNo(String cargoSbNo) {
			this.cargoSbNo = cargoSbNo;
		}






		public Date getCartingDate() {
			return cartingDate;
		}






		public void setCartingDate(Date cartingDate) {
			this.cartingDate = cartingDate;
		}






		public String getMovementType() {
			return movementType;
		}






		public void setMovementType(String movementType) {
			this.movementType = movementType;
		}


		@Transient
	    private String expAddress;
		 @Transient
		    private String chaStateCode;
		 @Transient
		    private String accAddress;  
		 @Transient
		    private String accStateCode;  
		 @Transient
		    private String fwdAddress;  
		 
		 
		 @Transient
		    private String accTanId1; 
		    
		    @Transient
		    private String accTanId2; 
		    
		    
		    
		    @Transient
		    private String accHolderGst2;
		        
		    @Transient
		    private String accHolderState2;   
		    
		    @Transient
		    private String accStateCode2;  
		    
		    @Transient
		    private String accAddress2;   
		    

		public String getAccTanId1() {
				return accTanId1;
			}






			public void setAccTanId1(String accTanId1) {
				this.accTanId1 = accTanId1;
			}






			public String getAccTanId2() {
				return accTanId2;
			}






			public void setAccTanId2(String accTanId2) {
				this.accTanId2 = accTanId2;
			}






		public String getExpGst() {
			return expGst;
		}






		public void setExpGst(String expGst) {
			this.expGst = expGst;
		}






		public String getExpTanNo() {
			return expTanNo;
		}






		public void setExpTanNo(String expTanNo) {
			this.expTanNo = expTanNo;
		}






		public String getExpStateCode() {
			return expStateCode;
		}






		public void setExpStateCode(String expStateCode) {
			this.expStateCode = expStateCode;
		}






		public String getExpAddress() {
			return expAddress;
		}






		public void setExpAddress(String expAddress) {
			this.expAddress = expAddress;
		}






		public String getChaStateCode() {
			return chaStateCode;
		}






		public void setChaStateCode(String chaStateCode) {
			this.chaStateCode = chaStateCode;
		}






		public String getAccAddress() {
			return accAddress;
		}






		public void setAccAddress(String accAddress) {
			this.accAddress = accAddress;
		}






		public String getAccStateCode() {
			return accStateCode;
		}






		public void setAccStateCode(String accStateCode) {
			this.accStateCode = accStateCode;
		}






		public String getFwdAddress() {
			return fwdAddress;
		}






		public void setFwdAddress(String fwdAddress) {
			this.fwdAddress = fwdAddress;
		}






		//getSelectedExportAssesmentSheet
		public AssessmentSheet(String companyId, String branchId, String assesmentId, String assesmentLineNo,
	            String transType, Date assesmentDate, String sbNo, String sbTransId, Date sbDate, char status,
	            String createdBy, String profitcentreId, String profitcentreName, String billingParty, String sl,
	            String sa, String commodityDescription, Date minCartingTransDate, String importerId,
	            String exporterName, String expAddress, String expGst, String expStateCode, int impSrNo,
	            String cha, String chaName, String chaAddress, String chaGst, String chaStateCode, int chaSrNo,
	            String othPartyId, String accHolderName, String accAddress, String accHolderGst,
	            String accStateCode, String othSrNo, String onAccountOf, String fwdName, String fwdAddress,
	            String fwdGst, String fwdState, int accSrNo, String invoiceNo, Date invoiceDate, char taxApplicable,
	            char sez, String commodityCode, char creditType, String invoiceCategory, String irn,
	            String receiptNo, String creditAllowed, String pendingCredit, String comments,
	            String intComments, String partyId) {
	this.companyId = companyId;
	this.branchId = branchId;
	this.assesmentId = assesmentId;
	this.assesmentLineNo = assesmentLineNo;
	this.transType = transType;
	this.assesmentDate = assesmentDate;
	this.sbNo = sbNo;
	this.sbTransId = sbTransId;
	this.sbDate = sbDate;
	this.status = status;
	this.createdBy = createdBy;
	this.profitcentreId = profitcentreId;
	this.profitcentreName = profitcentreName;
	this.billingParty = billingParty;
	this.sa = sa;
	this.sl = sl;
	this.commodityDescription = commodityDescription;
	this.minCartingTransDate = minCartingTransDate;
	this.importerId = importerId;
	this.exporterName = exporterName;
	this.expAddress = expAddress;
	this.expGst = expGst;
	this.expStateCode = expStateCode;
	this.impSrNo = impSrNo;
	this.cha = cha;
	this.chaName = chaName;
	this.chaAddress = chaAddress;
	this.chaGst = chaGst;
	this.chaStateCode = chaStateCode;
	this.chaSrNo = chaSrNo;
	this.othPartyId = othPartyId;
	this.accHolderName = accHolderName;
	this.accAddress = accAddress;
	this.accHolderGst = accHolderGst;
	this.accStateCode = accStateCode;
	this.othSrNo = othSrNo;
	this.onAccountOf = onAccountOf;
	this.fwdName = fwdName;
	this.fwdAddress = fwdAddress;
	this.fwdGst = fwdGst;
	this.fwdState = fwdState;
	this.accSrNo = accSrNo;
	this.invoiceNo = invoiceNo;
	this.invoiceDate = invoiceDate;
	this.taxApplicable = taxApplicable;
	this.sez = sez;
	this.commodityCode = commodityCode;
	this.creditType = creditType;
	this.invoiceCategory = invoiceCategory;
	this.irn = irn;
	this.receiptNo = receiptNo;
	this.creditAllowed = creditAllowed;
	this.pendingCredit = pendingCredit;
	this.comments = comments;
	this.intComments = intComments;
	this.partyId = partyId;	
	}

		

	    transient private String gateOutId;
	    transient private String gatePassNo;
	    
	    
	    
		public String getPendingCredit() {
			return pendingCredit;
		}

		public void setPendingCredit(String pendingCredit) {
			this.pendingCredit = pendingCredit;
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

		public AssessmentSheet(String companyId, String branchId, String assesmentId, String assesmentLineNo,Date assesmentDate, String containerNo, String containerSize, String containerType, Date gateInDate, Date invoiceDate,
				String gateOutId, String gatePassNo,  String partyId)
		{
			this.companyId = companyId;
			this.branchId = branchId;
			this.assesmentId = assesmentId;
			this.assesmentLineNo = assesmentLineNo;
			this.assesmentDate = assesmentDate;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.gateInDate = gateInDate;
			this.invoiceDate = invoiceDate;
			this.gateOutId = gateOutId;
			this.gatePassNo = gatePassNo;
			this.partyId = partyId;		
		}
		
		
		
		//getSelectedExportAssesmentSheetNew
				public AssessmentSheet(String companyId, String branchId, String assesmentId, String assesmentLineNo,
			            String transType, Date assesmentDate, String sbNo, String sbTransId, Date sbDate, char status,
			            String createdBy, String profitcentreId, String profitcentreName, String billingParty, String sl,
			            String sa, String commodityDescription, Date minCartingTransDate, String importerId,
			            String exporterName, String expAddress, String expGst, String expStateCode, int impSrNo,
			            String cha, String chaName, String chaAddress, String chaGst, String chaStateCode, int chaSrNo,
			            String othPartyId, String accHolderName, String accAddress, String accHolderGst,
			            String accStateCode, String othSrNo, String onAccountOf, String fwdName, String fwdAddress,
			            String fwdGst, String fwdState, int accSrNo, String invoiceNo, Date invoiceDate, char taxApplicable,
			            char sez, String commodityCode, char creditType, String invoiceCategory, String irn,
			            String receiptNo, String creditAllowed, String pendingCredit, String comments,
			            String intComments, String partyId, String cgst, String sgst, String igst) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.assesmentId = assesmentId;
			this.assesmentLineNo = assesmentLineNo;
			this.transType = transType;
			this.assesmentDate = assesmentDate;
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.sbDate = sbDate;
			this.status = status;
			this.createdBy = createdBy;
			this.profitcentreId = profitcentreId;
			this.profitcentreName = profitcentreName;
			this.billingParty = billingParty;
			this.sa = sa;
			this.sl = sl;
			this.commodityDescription = commodityDescription;
			this.minCartingTransDate = minCartingTransDate;
			this.importerId = importerId;
			this.exporterName = exporterName;
			this.expAddress = expAddress;
			this.expGst = expGst;
			this.expStateCode = expStateCode;
			this.impSrNo = impSrNo;
			this.cha = cha;
			this.chaName = chaName;
			this.chaAddress = chaAddress;
			this.chaGst = chaGst;
			this.chaStateCode = chaStateCode;
			this.chaSrNo = chaSrNo;
			this.othPartyId = othPartyId;
			this.accHolderName = accHolderName;
			this.accAddress = accAddress;
			this.accHolderGst = accHolderGst;
			this.accStateCode = accStateCode;
			this.othSrNo = othSrNo;
			this.onAccountOf = onAccountOf;
			this.fwdName = fwdName;
			this.fwdAddress = fwdAddress;
			this.fwdGst = fwdGst;
			this.fwdState = fwdState;
			this.accSrNo = accSrNo;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.taxApplicable = taxApplicable;
			this.sez = sez;
			this.commodityCode = commodityCode;
			this.creditType = creditType;
			this.invoiceCategory = invoiceCategory;
			this.irn = irn;
			this.receiptNo = receiptNo;
			this.creditAllowed = creditAllowed;
			this.pendingCredit = pendingCredit;
			this.comments = comments;
			this.intComments = intComments;
			this.partyId = partyId;	
			this.cgst = cgst;
			this.sgst = sgst;
			this.igst = igst;
			}


}
