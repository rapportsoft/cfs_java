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
@Table(name = "cfigmcn")
@IdClass(CfigmcnId.class)
public class Cfigmcn {
	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Fin_Year", length = 4)
	private String finYear;

	@Id
	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;

	@Id
	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;

	@Id
	@Column(name = "Igm_no", length = 10)
	private String igmNo;

	@Id
	@Column(name = "IGM_Line_No", length = 7)
	private String igmLineNo;

	@Id
	@Column(name = "Container_No", length = 11)
	private String containerNo;
	
	@Id
	@Column(name = "Container_Trans_id", length = 10)
	private String containerTransId;

	@Column(name = "Cycle", length = 10)
	private String cycle = "IMP"; // Default value

	@Column(name = "Cycle_Updated_By", length = 10)
	private String cycleUpdatedBy = ""; // Default value

	@Column(name = "Cycle_Updated_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date cycleUpdatedDate; // Nullable field

	@Column(name = "Container_Size", length = 6)
	private String containerSize = ""; // Default value

	@Column(name = "Container_Type", length = 6)
	private String containerType = ""; // Default value

	@Column(name = "Haz", length = 1)
	private String haz = "N"; // Default value

	@Column(name = "Haz_Class", length = 10)
	private String hazClass = ""; // Default value

	@Column(name = "Type_of_Container", length = 10)
	private String typeOfContainer = ""; // Default value

	@Column(name = "Old_Type_of_Container", length = 10)
	private String oldTypeOfContainer = ""; // Default value

	@Column(name = "Over_Dimension", length = 1)
	private String overDimension = "N"; // Default value

	@Column(name = "Shift", length = 6)
	private String shift = ""; // Default value

	@Column(name = "ISO", length = 4)
	private String iso = ""; // Default value

	@Column(name = "Internal_shifting", length = 1)
	private String internalShifting = "N"; // Default value

	@Column(name = "BE_No", length = 20)
	private String beNo; // Nullable by default

	@Column(name = "BE_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date beDate; // Nullable field

	@Column(name = "Container_Weight", precision = 14, scale = 2)
	private BigDecimal containerWeight = BigDecimal.ZERO; // Default value

	@Column(name = "Container_Status", length = 3)
	private String containerStatus = ""; // Default value

	@Column(name = "Container_Seal_No", length = 15)
	private String containerSealNo = ""; // Default value

	@Column(name = "Customs_Seal_No", length = 15)
	private String customsSealNo = ""; // Default value

	@Column(name = "Data_Input_Status", length = 1)
	private String dataInputStatus = ""; // Default value

	@Column(name = "Entry_Status", length = 3)
	private String entryStatus = ""; // Default value

	@Column(name = "Customs_Sample", length = 1)
	private String customsSample = ""; // Default value

	@Column(name = "Customs_Sample_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date customsSampleDate; // Nullable field

	@Column(name = "Scanner_Type", length = 60)
	private String scannerType = ""; // Default value

	@Column(name = "ReExport", length = 1)
	private String reExport = "N"; // Default value

	@Column(name = "Extra_Transport", length = 1)
	private String extraTransport = "N"; // Default value

	@Column(name = "Extra_Transport_Date")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	@Temporal(TemporalType.TIMESTAMP)
	private Date extraTransportDate; // Default value

	@Column(name = "Fumigation", length = 1)
	private String fumigation = "N"; // Default value

	@Column(name = "Fumigation_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date fumigationDate; // Default value

	@Column(name = "Movement_Req_Id")
	private String movementReqId = ""; // Default value

	@Column(name = "Seal_Cutting_Type", length = 10)
	private String sealCuttingType = ""; // Default value

	@Column(name = "Cargo_Value", precision = 15, scale = 3)
	private BigDecimal cargoValue = BigDecimal.ZERO; // Default value

	@Column(name = "Cargo_Duty", precision = 15, scale = 3)
	private BigDecimal cargoDuty = BigDecimal.ZERO; // Default value

	@Column(name = "BL_Status", length = 6)
	private String blStatus; // Nullable by default

	@Column(name = "marketing_person", length = 20)
	private String marketingPerson; // Nullable by default

	@Column(name = "Movement_Type", length = 6)
	private String movementType; // Nullable by default

	@Column(name = "Seal_Cut_Trans_Id", length = 10)
	private String sealCutTransId = ""; // Default value

	@Column(name = "Container_Exam_Status", length = 1)
	private String containerExamStatus = ""; // Default value

	@Column(name = "Seal_Cut_Req_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sealCutReqDate = null; // Default value

	@Column(name = "Container_Exam_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date containerExamDate = null; // Default value

	@Column(name = "Mobile_No", length = 10)
	private String mobileNo = ""; // Default value

	@Column(name = "Seal_Cut_Remarks", length = 250)
	private String sealCutRemarks = ""; // Default value

	@Column(name = "Container_Exam_Remarks", length = 250)
	private String containerExamRemarks = ""; // Default value

	@Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal actualNoOfPackages = BigDecimal.ZERO; // Default value
	
	@Column(name = "Gain_Or_Loss_Pkgs", precision = 8, scale = 0)
	private BigDecimal gainOrLossPkgs = BigDecimal.ZERO; // Default value
	
	@Column(name = "Old_Actual_No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal oldActualNoOfPackages = BigDecimal.ZERO; // Default value

	@Column(name = "Damaged_No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal damagedNoOfPackages = BigDecimal.ZERO; // Default value

	@Column(name = "Yard_Location", length = 20)
	private String yardLocation = ""; // Default value

	@Column(name = "Yard_Block", length = 6)
	private String yardBlock = ""; // Default value

	@Column(name = "Block_Cell_No", length = 10)
	private String blockCellNo; // Nullable by default

	@Column(name = "Yard_Location1", length = 20)
	private String yardLocation1 = ""; // Default value

	@Column(name = "Yard_Block1", length = 10)
	private String yardBlock1 = ""; // Default value

	@Column(name = "Block_Cell_No1", length = 10)
	private String blockCellNo1 = ""; // Default value

	@Column(name = "BL_Tariff_No", length = 10)
	private String blTariffNo = ""; // Default value

	@Column(name = "DeStuff_Id", length = 10)
	private String deStuffId = ""; // Default value

	@Column(name = "Type_Of_Cargo", length = 15)
	private String typeOfCargo = ""; // Default value

	@Column(name = "Destuff_Status", length = 1)
	private String destuffStatus = "N"; // Default value

	@Column(name = "DeStuff_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date deStuffDate = null; // Default value

	@Column(name = "DO_Entry_Flag", length = 1)
	private String doEntryFlag = "N"; // Default value

	@Column(name = "DO_Entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date doEntryDate = null; // Default value

	@Column(name = "Force_Entry_Flag", length = 1)
	private String forceEntryFlag = "N"; // Default value

	@Column(name = "Force_Entry_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date forceEntryDate; // Default value

	@Column(name = "Force_Entry_Approval", length = 50)
	private String forceEntryApproval = ""; // Default value

	@Column(name = "Force_Entry_Remarks", length = 250)
	private String forceEntryRemarks = ""; // Default value

	@Column(name = "Exam_Tally_Id", length = 10)
	private String examTallyId = ""; // Default value

	@Column(name = "Exam_Tally_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date examTallyDate; // Default value

	@Column(name = "Exam_Crg_Tally_Id", length = 10)
	private String examCrgTallyId = ""; // Default value

	@Column(name = "Exam_Crg_Tally_Date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date examCrgTallyDate; // Default value

	@Column(name = "vehicle_type", length = 10)
	private String vehicleType = ""; // Default value

	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId = ""; // Default value

	@Column(name = "Gate_In_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gateInDate; // Default value

	@Column(name = "Gate_Out_Id", length = 10)
	private String gateOutId = ""; // Default value

	@Column(name = "Gate_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gateOutDate; // Default value

	@Column(name = "Gate_Out_Type", length = 10)
	private String gateOutType = ""; // Default value

	@Column(name = "Special_Delivery", length = 6)
	private String specialDelivery = "NA"; // Default value

	@Column(name = "Holding_Agent", length = 1)
	private String holdingAgent = ""; // Default value

	@Column(name = "Holding_Agent_Name", length = 35)
	private String holdingAgentName = ""; // Default value

	@Column(name = "Hold_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date holdDate; // Default value

	@Column(name = "Release_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date releaseDate; // Default value

	@Column(name = "Hold_Remarks", length = 150)
	private String holdRemarks = ""; // Default value

	@Column(name = "Hold_Status", length = 1)
	private String holdStatus = ""; // Default value

	@Column(name = "Hold_Doc_Ref_No", length = 10)
	private String holdDocRefNo = ""; // Default value

	@Column(name = "Release_Agent", length = 35)
	private String releaseAgent; // Nullable by default

	@Column(name = "Release_Remarks", length = 150)
	private String releaseRemarks; // Nullable by default

	@Column(name = "Weighment_Flag", length = 1)
	private String weighmentFlag = "N"; // Default value

	@Column(name = "Weighment_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date weighmentDate; // Default value

	@Column(name = "Labour", length = 1)
	private char labour = 'N'; // Default value

	@Column(name = "Fk_3MT", length = 1)
	private char fk3mt = 'N'; // Default value

	@Column(name = "Fk_5MT", length = 1)
	private char fk5mt = 'N'; // Default value

	@Column(name = "Fk_10MT", length = 1)
	private char fk10mt = 'N'; // Default value

	@Column(name = "Hydra_12MT", length = 1)
	private char hydra12mt = 'N'; // Default value

	@Column(name = "Crane", length = 1)
	private char crane = 'N'; // Default value

	@Column(name = "Kalmar", length = 1)
	private char kalmar = 'N'; // Default value

	@Column(name = "Pusher", length = 1)
	private char pusher = 'N'; // Default value

	@Column(name = "Empty_Use", length = 1)
	private char emptyUse = 'N'; // Default value

	@Column(name = "Clamp", length = 1)
	private char clamp = 'N'; // Default value

	@Column(name = "carpenter", length = 1)
	private char carpenter = 'N'; // Default value

	@Column(name = "RTO_charges", length = 1)
	private char rtoCharges = 'N'; // Default value

	@Column(name = "Equipment_Entry_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date equipmentEntryDate; // Default value

	@Column(name = "Notice_Id", length = 10)
	private String noticeId = ""; // Default value

	@Column(name = "Notice_Type", length = 1)
	private char noticeType = ' '; // Default value

	@Column(name = "Notice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date noticeDate; // Default value

	@Column(name = "Second_Notice_Id", length = 10)
	private String secondNoticeId = ""; // Default value

	@Column(name = "Second_Notice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date secondNoticeDate; // Default value

	@Column(name = "Final_Notice_Id", length = 10)
	private String finalNoticeId = ""; // Default value

	@Column(name = "Final_Notice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date finalNoticeDate; // Default value

	@Column(name = "Auction_Status", length = 1)
	private char auctionStatus = 'N'; // Default value

	@Column(name = "Gate_Pass_No", length = 10)
	private String gatePassNo = ""; // Default value

	@Column(name = "Remarks", length = 250)
	private String remarks; // Nullable by default

	@Column(name = "Special_Remarks", length = 100)
	private String specialRemarks = ""; // Default value

	@Column(name = "Cargo_Wt", precision = 18, scale = 3)
	private BigDecimal cargoWt = BigDecimal.ZERO; // Default value

	@Column(name = "Gross_Wt", precision = 18, scale = 3)
	private BigDecimal grossWt = BigDecimal.ZERO; // Default value

	@Column(name = "EIR_Gross_Weight", precision = 15, scale = 3)
	private BigDecimal eirGrossWeight = BigDecimal.ZERO; // Default value

	@Column(name = "No_Of_Packages")
	private int noOfPackages; // Nullable by default

	@Column(name = "Examined_Packages")
	private int examinedPackages = 0; // Default value

	@Column(name = "Weighment_weight", precision = 15, scale = 3)
	private BigDecimal weighmentWeight = BigDecimal.ZERO; // Default value

	@Column(name = "Packages_DeStuffed")
	private int packagesDeStuffed = 0; // Default value

	@Column(name = "Packages_stuffed")
	private int packagesStuffed = 0; // Default value

	@Column(name = "CHA", length = 6)
	private String cha = ""; // Default value

	@Column(name = "GRN_No", length = 25)
	private String grnNo = ""; // Default value

	@Column(name = "GRN_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date grnDate; // Default value

	@Column(name = "CIN_No", length = 25)
	private String cinNo = ""; // Default value

	@Column(name = "CIN_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date cinDate; // Default value

	@Column(name = "Stamp_Duty", precision = 16, scale = 3)
	private BigDecimal stampDuty = BigDecimal.ZERO; // Default value

	@Column(name = "DO_No", length = 30)
	private String doNo = ""; // Default value

	@Column(name = "DO_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date doDate = null; // Default value

	@Column(name = "DO_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date doValidityDate = null; // Default value

	@Column(name = "ICE_CODE", length = 30)
	private String iceCode = ""; // Default value

	@Column(name = "CHA_CODE", length = 30)
	private String chaCode = ""; // Default value

	@Column(name = "Copan_Code", length = 10)
	private String copanCode = ""; // Default value

	@Column(name = "Scanning_Done_Status", length = 40)
	private String scanningDoneStatus; // Nullable by default

	@Column(name = "Scanning_Edited_By", length = 10)
	private String scanningEditedBy = ""; // Default value

	@Column(name = "Scanning_Done_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date scanningDoneDate; // Default value

	@Column(name = "OOC_No", length = 20)
	private String oocNo = ""; // Default value

	@Column(name = "OOC_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date oocDate; // Default value

	@Column(name = "Loading_Start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date loadingStartDate; // Default value

	@Column(name = "Loading_End_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date loadingEndDate; // Default value

	@Column(name = "Reefer_Escalation", length = 1)
	private char reeferEscalation = 'N'; // Default value

	@Column(name = "Gen_set", length = 1)
	private char genSet = 'N'; // Default value

	@Column(name = "Igm_Container_Hold", length = 1)
	private String igmContainerHold = ""; // Default value

	@Column(name = "Igm_Container_Agency", length = 10)
	private String igmContainerAgency = ""; // Default value

	@Column(name = "Temperature", length = 5)
	private String temperature = ""; // Default value

	@Column(name = "Plug_In_Flag", length = 1)
	private char plugInFlag = 'N'; // Default value

	@Column(name = "Plug_In_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date plugInDate; // Default value

	@Column(name = "Plug_In_User", length = 10)
	private String plugInUser = ""; // Default value

	@Column(name = "Plug_Out_Flag", length = 1)
	private char plugOutFlag = 'N'; // Default value

	@Column(name = "Plug_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date plugOutDate; // Default value

	@Column(name = "Plug_Out_User", length = 10)
	private String plugOutUser = ""; // Default value

	@Column(name = "Plug_Entry_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date plugEntryDate; // Default value

	@Column(name = "Status", length = 1)
	private char status = ' '; // Default value

	@Column(name = "Created_By", length = 10)
	private String createdBy = ""; // Default value

	@Column(name = "Seal_Cut_Created_By", length = 10)
	private String sealCutCreatedBy; // Nullable by default

	@Column(name = "Seal_Cut_Approved_By", length = 10)
	private String sealCutApprovedBy; // Nullable by default

	@Column(name = "Container_Exam_Created_By", length = 10)
	private String containerExamCreatedBy = ""; // Default value

	@Column(name = "Container_Exam_Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date containerExamCreatedDate = null; // Default value

	@Column(name = "Container_Exam_Approved_By", length = 10)
	private String containerExamApprovedBy = ""; // Default value

	@Column(name = "Container_Exam_Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date containerExamApprovedDate = null; // Default value

	@Column(name = "Seal_Cut_Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sealCutCreatedDate = null; // Default value

	@Column(name = "Seal_Cut_Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sealCutApprovedDate = null; // Default value

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate; // Default value

	@Column(name = "Edited_By", length = 10)
	private String editedBy = ""; // Default value

	@Column(name = "Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate; // Default value

	@Column(name = "Approved_By", length = 10)
	private String approvedBy = ""; // Default value

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate; // Default value

	@Column(name = "Transfer", length = 1)
	private char transfer = 'N'; // Default value

	@Column(name = "Drt_Transfer_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date drtTransferDate; // Default value

	@Column(name = "DRT", length = 1)
	private char drt = 'N'; // Default value

	@Column(name = "No_of_Item")
	private int noOfItem = 0; // Default value

	@Column(name = "upload_user", length = 30)
	private String uploadUser = ""; // Default value

	@Column(name = "upload_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date uploadDate; // Default value

	@Column(name = "remove_user", length = 30)
	private String removeUser = ""; // Default value

	@Column(name = "remove_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date removeDate; // Default value

	@Column(name = "image_path", length = 200)
	private String imagePath = ""; // Default value

	@Column(name = "back_image", length = 200)
	private String backImage = ""; // Default value

	@Column(name = "labour_company_name", length = 20)
	private String labourCompanyName = ""; // Default value

	@Column(name = "lmport_examination_status", length = 1)
	private char lmportExaminationStatus = 'N'; // Default value

	@Column(name = "up_Tariff_Fwd", length = 10)
	private String upTariffFwd = ""; // Default value

	@Column(name = "up_Tariff_No", length = 10)
	private String upTariffNo = ""; // Default value

	@Column(name = "up_Tariff_Amnd_No", length = 3)
	private String upTariffAmndNo = ""; // Default value

	@Column(name = "up_Tariff_Del_Mode", length = 3)
	private String upTariffDelMode = ""; // Default value

	@Column(name = "oth_party_Id", length = 10)
	private String othPartyId = ""; // Default value

	@Column(name = "Invoice_Assesed", length = 1)
	private char invoiceAssesed = 'N'; // Default value

	@Column(name = "Assesment_Id", length = 20)
	private String assesmentId = ""; // Default value

	@Column(name = "Invoice_No", length = 16)
	private String invoiceNo = ""; // Default value

	@Column(name = "Invoice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceDate; // Default value

	@Column(name = "Credit_Type", length = 1)
	private char creditType = 'N'; // Default value

	@Column(name = "Invoice_Category", length = 10)
	private String invoiceCategory = "SINGLE"; // Default value

	@Column(name = "Bill_Amt", precision = 12, scale = 2)
	private BigDecimal billAmt = BigDecimal.ZERO; // Default value

	@Column(name = "Invoice_Amt", precision = 12, scale = 2)
	private BigDecimal invoiceAmt = BigDecimal.ZERO; // Default value

	@Column(name = "Sub_Item_no", length = 10)
	private String subItemNo = ""; // Default value

	@Column(name = "Tariff_valid")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date tariffValid; // Default value

	@Column(name = "Tariff_Code", length = 20)
	private String tariffCode = ""; // Default value

	@Column(name = "Destuff_WO_Trans_Id", length = 21)
	private String destuffWoTransId = ""; // Default value

	@Column(name = "SSR_Trans_Id", length = 20)
	private String ssrTransId = ""; // Default value

	@Column(name = "Seal_Cut_WO_Trans_Id", length = 25)
	private String sealCutWoTransId = ""; // Default value

	@Column(name = "Seal_Cut_WO_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sealCutWoTransDate = null; // Default value

	@Column(name = "Container_Exam_WO_Trans_Id", length = 25)
	private String containerExamWoTransId = ""; // Default value

	@Column(name = "Container_Exam_WO_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date containerExamWoTransDate = null; // Default value

	@Column(name = "Exam_Remarks", length = 250)
	private String examRemarks = ""; // Default value

	@Column(name = "File_No", length = 50)
	private String fileNo = ""; // Default value

	@Column(name = "Lot_No", length = 50)
	private String lotNo = ""; // Default value

	@Column(name = "HSN_No", length = 50)
	private String hsnNo = ""; // Default value

	@Column(name = "File_Staus", length = 50)
	private String fileStatus = ""; // Default value

	@Column(name = "merge_status", length = 10)
	private String mergeStatus = ""; // Default value

	@Column(name = "merge_Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date mergeCreatedDate; // Default value

	@Column(name = "MERGE_CREATED_BY", length = 10)
	private String mergeCreatedBy = ""; // Default value

	@Column(name = "REFER", length = 1)
	private char refer = 'N'; // Default value

	@Column(name = "WEIGHMENT_WT", precision = 16, scale = 3)
	private BigDecimal weighmentWt = BigDecimal.ZERO; // Default value

	@Column(name = "merge_Approved_By", length = 10)
	private String mergeApprovedBy = ""; // Default value

	@Column(name = "merge_Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date mergeApprovedDate; // Default value

	@Column(name = "Last_Assesment_Id", length = 20)
	private String lastAssesmentId = ""; // Default value

	@Column(name = "Last_Assesment_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lastAssesmentDate; // Default value

	@Column(name = "Last_Invoice_No", length = 16)
	private String lastInvoiceNo = ""; // Default value

	@Column(name = "Last_Invoice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lastInvoiceDate; // Default value

	@Column(name = "PN_Status", length = 1)
	private char pnStatus = 'N'; // Default value

	@Column(name = "Last_Invoice_Assesed", length = 1)
	private char lastInvoiceAssesed = 'N'; // Default value

	@Column(name = "Last_Bill_Amt", precision = 16, scale = 3)
	private BigDecimal lastBillAmt = BigDecimal.ZERO; // Default value

	@Column(name = "Last_Invoice_Amt", precision = 16, scale = 3)
	private BigDecimal lastInvoiceAmt = BigDecimal.ZERO; // Default value

	@Column(name = "Last_Credit_Type", length = 16)
	private String lastCreditType = ""; // Default value

	@Column(name = "LCL_Zero_Entry_Flag", length = 1)
	private char lclZeroEntryFlag = 'N'; // Default value

	@Column(name = "LCL_Zero_Entry_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lclZeroEntryDate; // Default value

	@Column(name = "LCL_ZERO_ENTRY_Validity_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lclZeroEntryValidityDate; // Default value

	@Column(name = "LCL_ZERO_ENTRY_Created_By", length = 50)
	private String lclZeroEntryCreatedBy = ""; // Default value

	@Column(name = "LCL_Zero_Entry_Approval", length = 50)
	private String lclZeroEntryApproval = ""; // Default value

	@Column(name = "LCL_Zero_Entry_Remarks", length = 250)
	private String lclZeroEntryRemarks = ""; // Default value

	@Column(name = "RScan_Out", length = 1)
	private char rscanOut = 'N'; // Default value

	@Column(name = "RScan_In", length = 1)
	private char rscanIn = 'N'; // Default value

	@Column(name = "Destuff_WO_CreatedBy", length = 10)
	private String destuffWoCreatedBy = ""; // Default value

	@Column(name = "Destuff_WO_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date destuffWoDate = null; // Default value

	@Column(name = "New_FWD_Id", length = 6)
	private String newFwdId = ""; // Default value

	@Column(name = "Document_Upload_Flag", length = 1)
	private char documentUploadFlag = 'N'; // Default value

	@Column(name = "AUCBID", length = 1)
	private char aucbid = 'N'; // Default value

	@Column(name = "ODC_Status", length = 1)
	private char odcStatus = 'N'; // Default value

	@Column(name = "LCLCN_Invoice_Assesed", length = 1)
	private char lclcnInvoiceAssesed = 'N'; // Default value

	@Column(name = "LCLCN_Assesment_Id", length = 20)
	private String lclcnAssesmentId = ""; // Default value

	@Column(name = "LCLCN_Invoice_No", length = 16)
	private String lclcnInvoiceNo = ""; // Default value

	@Column(name = "LCLCN_Invoice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lclcnInvoiceDate; // Default value

	@Column(name = "LCLCN_Credit_Type", length = 1)
	private char lclcnCreditType = 'N'; // Default value

	@Column(name = "LCLCN_Invoice_Category", length = 10)
	private String lclcnInvoiceCategory = "SINGLE"; // Default value

	@Column(name = "LCLCN_Bill_Amt", precision = 12, scale = 2)
	private BigDecimal lclcnBillAmt = BigDecimal.ZERO; // Default value

	@Column(name = "LCLCN_Invoice_Amt", precision = 12, scale = 2)
	private BigDecimal lclcnInvoiceAmt = BigDecimal.ZERO; // Default value

	@Column(name = "CODECCO_IN_STATUS", length = 1)
	private char codeccoInStatus = 'N'; // Default value

	@Column(name = "CODECCO_IN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date codeccoInDate; // Default value

	@Column(name = "CODECCO_OUT_STATUS", length = 1)
	private char codeccoOutStatus = 'N'; // Default value

	@Column(name = "CODECCO_OUT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date codeccoOutDate; // Default value

	@Column(name = "CODECCO_DST_STATUS", length = 1)
	private char codeccoDstStatus = 'N'; // Default value

	@Column(name = "CODECCO_DST_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date codeccoDstDate; // Default value

	@Column(name = "CODECCO_MT_IN_STATUS", length = 1)
	private char codeccoMtInStatus = 'N'; // Default value

	@Column(name = "CODECCO_MT_IN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date codeccoMtInDate; // Default value

	@Column(name = "CODECCO_TERMINAL_OUT_STATUS", length = 1)
	private char codeccoTerminalOutStatus = 'N'; // Default value

	@Column(name = "CODECCO_TERMINAL_OUT_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date codeccoTerminalOutDate; // Default value

	@Column(name = "THCharges", precision = 16, scale = 0)
	private BigDecimal thCharges = BigDecimal.ZERO; // Default value

	@Column(name = "Part_DeStuff_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date partDeStuffDate; // Default value

	@Column(name = "Part_DeStuff_Id", length = 10)
	private String partDeStuffId = ""; // Default value

	@Column(name = "Invoice_Days_Old", precision = 8, scale = 0)
	private BigDecimal invoiceDaysOld = BigDecimal.ZERO; // Default value

	@Column(name = "Invoice_Amt_Old", precision = 16, scale = 3)
	private BigDecimal invoiceAmtOld = BigDecimal.ZERO; // Default value

	@Column(name = "CRG_STORAGE_AMT", precision = 16, scale = 3)
	private BigDecimal crgStorageAmt = BigDecimal.ZERO; // Default value

	@Column(name = "CRG_STORAGE_DAY", precision = 8, scale = 0)
	private BigDecimal crgStorageDay = BigDecimal.ZERO; // Default value

	@Column(name = "AUCTION_AMT", precision = 16, scale = 3)
	private BigDecimal auctionAmt = BigDecimal.ZERO; // Default value

	@Column(name = "INVOICE_UPTO_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceUptoDate; // Default value

	@Column(name = "AUCTION_FLAG", length = 1)
	private char auctionFlag = 'N'; // Default value

	@Column(name = "Force_Entry_Flag_INV", length = 1)
	private char forceEntryFlagInv = 'N'; // Default value

	@Column(name = "Scan_Actual_Status", length = 1)
	private char scanActualStatus = ' '; // Default value (empty character)

	@Column(name = "LCL_Cont_up_Tariff_Fwd", length = 10)
	private String lclContUpTariffFwd = ""; // Default value

	@Column(name = "LCL_Cont_up_Tariff_No", length = 10)
	private String lclContUpTariffNo = ""; // Default value

	@Column(name = "LCL_Cont_up_Tariff_Amnd_No", length = 3)
	private String lclContUpTariffAmndNo = ""; // Default value

	@Column(name = "LCL_Cont_up_Tariff_Del_Mode", length = 3)
	private String lclContUpTariffDelMode = ""; // Default value

	@Column(name = "Last_Location_Received_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lastLocationReceivedDate; // Default value

	@Column(name = "Location_Received_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date locationReceivedDate; // Default value

	@Column(name = "Tag_Receive_Status", length = 1)
	private char tagReceiveStatus = 'N'; // Default value

	@Column(name = "Tag_Receive_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date tagReceiveDate; // Default value

	@Column(name = "RFTag", length = 30)
	private String rfTag = ""; // Default value

	@Column(name = "Tag_Remove_Status", length = 1)
	private char tagRemoveStatus = ' '; // Default value (empty character)

	@Column(name = "Tag_Remove_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date tagRemoveDate; // Default value

	@Column(name = "GP_Send_Status", length = 1)
	private char gpSendStatus = 'N'; // Default value

	@Column(name = "GP_Send_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gpSendDate; // Default value

	@Column(name = "Inv_Cat_H", length = 1)
	private char invCatH = ' '; // Default value (empty character)

	@Column(name = "Inv_Cat_G", length = 1)
	private char invCatG = ' '; // Default value (empty character)

	@Column(name = "Is_Ancillary", length = 1)
	private char isAncillary = 'N'; // Default value

	@Column(name = "Inv_Count", length = 10)
	private String invCount = "0"; // Default value

	@Column(name = "Low_Bed", length = 1)
	private char lowBed = 'N'; // Default value
	
	@Column(name = "Seal_Cutting_Status", length = 1)
	private String sealCuttingStatus = "N"; // Default value

	public Cfigmcn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public BigDecimal getGainOrLossPkgs() {
		return gainOrLossPkgs;
	}



	public void setGainOrLossPkgs(BigDecimal gainOrLossPkgs) {
		this.gainOrLossPkgs = gainOrLossPkgs;
	}



	public String getSealCuttingStatus() {
		return sealCuttingStatus;
	}



	public void setSealCuttingStatus(String sealCuttingStatus) {
		this.sealCuttingStatus = sealCuttingStatus;
	}



	public char getLowBed() {
		return lowBed;
	}

	public void setLowBed(char lowBed) {
		this.lowBed = lowBed;
	}
	
	

	public String getContainerTransId() {
		return containerTransId;
	}

	public void setContainerTransId(String containerTransId) {
		this.containerTransId = containerTransId;
	}

	public Cfigmcn(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
			String igmNo, String igmLineNo, String containerNo, String cycle, String cycleUpdatedBy,
			Date cycleUpdatedDate, String containerSize, String containerType, String haz, String hazClass,
			String typeOfContainer, String oldTypeOfContainer, String overDimension, String shift, String iso,
			String internalShifting, String beNo, Date beDate, BigDecimal containerWeight, String containerStatus,
			String containerSealNo, String customsSealNo, String dataInputStatus, String entryStatus,
			String customsSample, Date customsSampleDate, String scannerType, String reExport, String extraTransport,
			Date extraTransportDate, String fumigation, Date fumigationDate, String movementReqId,
			String sealCuttingType, BigDecimal cargoValue, BigDecimal cargoDuty, String blStatus,
			String marketingPerson, String movementType, String sealCutTransId, String containerExamStatus,
			Date sealCutReqDate, Date containerExamDate, String mobileNo, String sealCutRemarks,
			String containerExamRemarks, BigDecimal actualNoOfPackages, BigDecimal damagedNoOfPackages,
			String yardLocation, String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1,
			String blockCellNo1, String blTariffNo, String deStuffId, String typeOfCargo, String destuffStatus,
			Date deStuffDate, String doEntryFlag, Date doEntryDate, String forceEntryFlag, Date forceEntryDate,
			String forceEntryApproval, String forceEntryRemarks, String examTallyId, Date examTallyDate,
			String examCrgTallyId, Date examCrgTallyDate, String vehicleType, String gateInId, Date gateInDate,
			String gateOutId, Date gateOutDate, String gateOutType, String specialDelivery, String holdingAgent,
			String holdingAgentName, Date holdDate, Date releaseDate, String holdRemarks, String holdStatus,
			String holdDocRefNo, String releaseAgent, String releaseRemarks, String weighmentFlag, Date weighmentDate,
			char labour, char fk3mt, char fk5mt, char fk10mt, char hydra12mt, char crane, char kalmar, char pusher,
			char emptyUse, char clamp, char carpenter, char rtoCharges, Date equipmentEntryDate, String noticeId,
			char noticeType, Date noticeDate, String secondNoticeId, Date secondNoticeDate, String finalNoticeId,
			Date finalNoticeDate, char auctionStatus, String gatePassNo, String remarks, String specialRemarks,
			BigDecimal cargoWt, BigDecimal grossWt, BigDecimal eirGrossWeight, int noOfPackages, int examinedPackages,
			BigDecimal weighmentWeight, int packagesDeStuffed, int packagesStuffed, String cha, String grnNo,
			Date grnDate, String cinNo, Date cinDate, BigDecimal stampDuty, String doNo, Date doDate,
			Date doValidityDate, String iceCode, String chaCode, String copanCode, String scanningDoneStatus,
			String scanningEditedBy, Date scanningDoneDate, String oocNo, Date oocDate, Date loadingStartDate,
			Date loadingEndDate, char reeferEscalation, char genSet, String igmContainerHold, String igmContainerAgency,
			String temperature, char plugInFlag, Date plugInDate, String plugInUser, char plugOutFlag, Date plugOutDate,
			String plugOutUser, Date plugEntryDate, char status, String createdBy, String sealCutCreatedBy,
			String sealCutApprovedBy, String containerExamCreatedBy, Date containerExamCreatedDate,
			String containerExamApprovedBy, Date containerExamApprovedDate, Date sealCutCreatedDate,
			Date sealCutApprovedDate, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, char transfer, Date drtTransferDate, char drt, int noOfItem, String uploadUser,
			Date uploadDate, String removeUser, Date removeDate, String imagePath, String backImage,
			String labourCompanyName, char lmportExaminationStatus, String upTariffFwd, String upTariffNo,
			String upTariffAmndNo, String upTariffDelMode, String othPartyId, char invoiceAssesed, String assesmentId,
			String invoiceNo, Date invoiceDate, char creditType, String invoiceCategory, BigDecimal billAmt,
			BigDecimal invoiceAmt, String subItemNo, Date tariffValid, String tariffCode, String destuffWoTransId,
			String ssrTransId, String sealCutWoTransId, Date sealCutWoTransDate, String containerExamWoTransId,
			Date containerExamWoTransDate, String examRemarks, String fileNo, String lotNo, String hsnNo,
			String fileStatus, String mergeStatus, Date mergeCreatedDate, String mergeCreatedBy, char refer,
			BigDecimal weighmentWt, String mergeApprovedBy, Date mergeApprovedDate, String lastAssesmentId,
			Date lastAssesmentDate, String lastInvoiceNo, Date lastInvoiceDate, char pnStatus, char lastInvoiceAssesed,
			BigDecimal lastBillAmt, BigDecimal lastInvoiceAmt, String lastCreditType, char lclZeroEntryFlag,
			Date lclZeroEntryDate, Date lclZeroEntryValidityDate, String lclZeroEntryCreatedBy,
			String lclZeroEntryApproval, String lclZeroEntryRemarks, char rscanOut, char rscanIn,
			String destuffWoCreatedBy, Date destuffWoDate, String newFwdId, char documentUploadFlag, char aucbid,
			char odcStatus, char lclcnInvoiceAssesed, String lclcnAssesmentId, String lclcnInvoiceNo,
			Date lclcnInvoiceDate, char lclcnCreditType, String lclcnInvoiceCategory, BigDecimal lclcnBillAmt,
			BigDecimal lclcnInvoiceAmt, char codeccoInStatus, Date codeccoInDate, char codeccoOutStatus,
			Date codeccoOutDate, char codeccoDstStatus, Date codeccoDstDate, char codeccoMtInStatus) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.cycle = cycle;
		this.cycleUpdatedBy = cycleUpdatedBy;
		this.cycleUpdatedDate = cycleUpdatedDate;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.haz = haz;
		this.hazClass = hazClass;
		this.typeOfContainer = typeOfContainer;
		this.oldTypeOfContainer = oldTypeOfContainer;
		this.overDimension = overDimension;
		this.shift = shift;
		this.iso = iso;
		this.internalShifting = internalShifting;
		this.beNo = beNo;
		this.beDate = beDate;
		this.containerWeight = containerWeight;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.customsSealNo = customsSealNo;
		this.dataInputStatus = dataInputStatus;
		this.entryStatus = entryStatus;
		this.customsSample = customsSample;
		this.customsSampleDate = customsSampleDate;
		this.scannerType = scannerType;
		this.reExport = reExport;
		this.extraTransport = extraTransport;
		this.extraTransportDate = extraTransportDate;
		this.fumigation = fumigation;
		this.fumigationDate = fumigationDate;
		this.movementReqId = movementReqId;
		this.sealCuttingType = sealCuttingType;
		this.cargoValue = cargoValue;
		this.cargoDuty = cargoDuty;
		this.blStatus = blStatus;
		this.marketingPerson = marketingPerson;
		this.movementType = movementType;
		this.sealCutTransId = sealCutTransId;
		this.containerExamStatus = containerExamStatus;
		this.sealCutReqDate = sealCutReqDate;
		this.containerExamDate = containerExamDate;
		this.mobileNo = mobileNo;
		this.sealCutRemarks = sealCutRemarks;
		this.containerExamRemarks = containerExamRemarks;
		this.actualNoOfPackages = actualNoOfPackages;
		this.damagedNoOfPackages = damagedNoOfPackages;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.yardLocation1 = yardLocation1;
		this.yardBlock1 = yardBlock1;
		this.blockCellNo1 = blockCellNo1;
		this.blTariffNo = blTariffNo;
		this.deStuffId = deStuffId;
		this.typeOfCargo = typeOfCargo;
		this.destuffStatus = destuffStatus;
		this.deStuffDate = deStuffDate;
		this.doEntryFlag = doEntryFlag;
		this.doEntryDate = doEntryDate;
		this.forceEntryFlag = forceEntryFlag;
		this.forceEntryDate = forceEntryDate;
		this.forceEntryApproval = forceEntryApproval;
		this.forceEntryRemarks = forceEntryRemarks;
		this.examTallyId = examTallyId;
		this.examTallyDate = examTallyDate;
		this.examCrgTallyId = examCrgTallyId;
		this.examCrgTallyDate = examCrgTallyDate;
		this.vehicleType = vehicleType;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.gateOutType = gateOutType;
		this.specialDelivery = specialDelivery;
		this.holdingAgent = holdingAgent;
		this.holdingAgentName = holdingAgentName;
		this.holdDate = holdDate;
		this.releaseDate = releaseDate;
		this.holdRemarks = holdRemarks;
		this.holdStatus = holdStatus;
		this.holdDocRefNo = holdDocRefNo;
		this.releaseAgent = releaseAgent;
		this.releaseRemarks = releaseRemarks;
		this.weighmentFlag = weighmentFlag;
		this.weighmentDate = weighmentDate;
		this.labour = labour;
		this.fk3mt = fk3mt;
		this.fk5mt = fk5mt;
		this.fk10mt = fk10mt;
		this.hydra12mt = hydra12mt;
		this.crane = crane;
		this.kalmar = kalmar;
		this.pusher = pusher;
		this.emptyUse = emptyUse;
		this.clamp = clamp;
		this.carpenter = carpenter;
		this.rtoCharges = rtoCharges;
		this.equipmentEntryDate = equipmentEntryDate;
		this.noticeId = noticeId;
		this.noticeType = noticeType;
		this.noticeDate = noticeDate;
		this.secondNoticeId = secondNoticeId;
		this.secondNoticeDate = secondNoticeDate;
		this.finalNoticeId = finalNoticeId;
		this.finalNoticeDate = finalNoticeDate;
		this.auctionStatus = auctionStatus;
		this.gatePassNo = gatePassNo;
		this.remarks = remarks;
		this.specialRemarks = specialRemarks;
		this.cargoWt = cargoWt;
		this.grossWt = grossWt;
		this.eirGrossWeight = eirGrossWeight;
		this.noOfPackages = noOfPackages;
		this.examinedPackages = examinedPackages;
		this.weighmentWeight = weighmentWeight;
		this.packagesDeStuffed = packagesDeStuffed;
		this.packagesStuffed = packagesStuffed;
		this.cha = cha;
		this.grnNo = grnNo;
		this.grnDate = grnDate;
		this.cinNo = cinNo;
		this.cinDate = cinDate;
		this.stampDuty = stampDuty;
		this.doNo = doNo;
		this.doDate = doDate;
		this.doValidityDate = doValidityDate;
		this.iceCode = iceCode;
		this.chaCode = chaCode;
		this.copanCode = copanCode;
		this.scanningDoneStatus = scanningDoneStatus;
		this.scanningEditedBy = scanningEditedBy;
		this.scanningDoneDate = scanningDoneDate;
		this.oocNo = oocNo;
		this.oocDate = oocDate;
		this.loadingStartDate = loadingStartDate;
		this.loadingEndDate = loadingEndDate;
		this.reeferEscalation = reeferEscalation;
		this.genSet = genSet;
		this.igmContainerHold = igmContainerHold;
		this.igmContainerAgency = igmContainerAgency;
		this.temperature = temperature;
		this.plugInFlag = plugInFlag;
		this.plugInDate = plugInDate;
		this.plugInUser = plugInUser;
		this.plugOutFlag = plugOutFlag;
		this.plugOutDate = plugOutDate;
		this.plugOutUser = plugOutUser;
		this.plugEntryDate = plugEntryDate;
		this.status = status;
		this.createdBy = createdBy;
		this.sealCutCreatedBy = sealCutCreatedBy;
		this.sealCutApprovedBy = sealCutApprovedBy;
		this.containerExamCreatedBy = containerExamCreatedBy;
		this.containerExamCreatedDate = containerExamCreatedDate;
		this.containerExamApprovedBy = containerExamApprovedBy;
		this.containerExamApprovedDate = containerExamApprovedDate;
		this.sealCutCreatedDate = sealCutCreatedDate;
		this.sealCutApprovedDate = sealCutApprovedDate;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.transfer = transfer;
		this.drtTransferDate = drtTransferDate;
		this.drt = drt;
		this.noOfItem = noOfItem;
		this.uploadUser = uploadUser;
		this.uploadDate = uploadDate;
		this.removeUser = removeUser;
		this.removeDate = removeDate;
		this.imagePath = imagePath;
		this.backImage = backImage;
		this.labourCompanyName = labourCompanyName;
		this.lmportExaminationStatus = lmportExaminationStatus;
		this.upTariffFwd = upTariffFwd;
		this.upTariffNo = upTariffNo;
		this.upTariffAmndNo = upTariffAmndNo;
		this.upTariffDelMode = upTariffDelMode;
		this.othPartyId = othPartyId;
		this.invoiceAssesed = invoiceAssesed;
		this.assesmentId = assesmentId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.creditType = creditType;
		this.invoiceCategory = invoiceCategory;
		this.billAmt = billAmt;
		this.invoiceAmt = invoiceAmt;
		this.subItemNo = subItemNo;
		this.tariffValid = tariffValid;
		this.tariffCode = tariffCode;
		this.destuffWoTransId = destuffWoTransId;
		this.ssrTransId = ssrTransId;
		this.sealCutWoTransId = sealCutWoTransId;
		this.sealCutWoTransDate = sealCutWoTransDate;
		this.containerExamWoTransId = containerExamWoTransId;
		this.containerExamWoTransDate = containerExamWoTransDate;
		this.examRemarks = examRemarks;
		this.fileNo = fileNo;
		this.lotNo = lotNo;
		this.hsnNo = hsnNo;
		this.fileStatus = fileStatus;
		this.mergeStatus = mergeStatus;
		this.mergeCreatedDate = mergeCreatedDate;
		this.mergeCreatedBy = mergeCreatedBy;
		this.refer = refer;
		this.weighmentWt = weighmentWt;
		this.mergeApprovedBy = mergeApprovedBy;
		this.mergeApprovedDate = mergeApprovedDate;
		this.lastAssesmentId = lastAssesmentId;
		this.lastAssesmentDate = lastAssesmentDate;
		this.lastInvoiceNo = lastInvoiceNo;
		this.lastInvoiceDate = lastInvoiceDate;
		this.pnStatus = pnStatus;
		this.lastInvoiceAssesed = lastInvoiceAssesed;
		this.lastBillAmt = lastBillAmt;
		this.lastInvoiceAmt = lastInvoiceAmt;
		this.lastCreditType = lastCreditType;
		this.lclZeroEntryFlag = lclZeroEntryFlag;
		this.lclZeroEntryDate = lclZeroEntryDate;
		this.lclZeroEntryValidityDate = lclZeroEntryValidityDate;
		this.lclZeroEntryCreatedBy = lclZeroEntryCreatedBy;
		this.lclZeroEntryApproval = lclZeroEntryApproval;
		this.lclZeroEntryRemarks = lclZeroEntryRemarks;
		this.rscanOut = rscanOut;
		this.rscanIn = rscanIn;
		this.destuffWoCreatedBy = destuffWoCreatedBy;
		this.destuffWoDate = destuffWoDate;
		this.newFwdId = newFwdId;
		this.documentUploadFlag = documentUploadFlag;
		this.aucbid = aucbid;
		this.odcStatus = odcStatus;
		this.lclcnInvoiceAssesed = lclcnInvoiceAssesed;
		this.lclcnAssesmentId = lclcnAssesmentId;
		this.lclcnInvoiceNo = lclcnInvoiceNo;
		this.lclcnInvoiceDate = lclcnInvoiceDate;
		this.lclcnCreditType = lclcnCreditType;
		this.lclcnInvoiceCategory = lclcnInvoiceCategory;
		this.lclcnBillAmt = lclcnBillAmt;
		this.lclcnInvoiceAmt = lclcnInvoiceAmt;
		this.codeccoInStatus = codeccoInStatus;
		this.codeccoInDate = codeccoInDate;
		this.codeccoOutStatus = codeccoOutStatus;
		this.codeccoOutDate = codeccoOutDate;
		this.codeccoDstStatus = codeccoDstStatus;
		this.codeccoDstDate = codeccoDstDate;
		this.codeccoMtInStatus = codeccoMtInStatus;

	}

	
	
	
	public BigDecimal getOldActualNoOfPackages() {
		return oldActualNoOfPackages;
	}



	public void setOldActualNoOfPackages(BigDecimal oldActualNoOfPackages) {
		this.oldActualNoOfPackages = oldActualNoOfPackages;
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

	public String getIgmTransId() {
		return igmTransId;
	}

	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
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

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getCycleUpdatedBy() {
		return cycleUpdatedBy;
	}

	public void setCycleUpdatedBy(String cycleUpdatedBy) {
		this.cycleUpdatedBy = cycleUpdatedBy;
	}

	public Date getCycleUpdatedDate() {
		return cycleUpdatedDate;
	}

	public void setCycleUpdatedDate(Date cycleUpdatedDate) {
		this.cycleUpdatedDate = cycleUpdatedDate;
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

	public String getHaz() {
		return haz;
	}

	public void setHaz(String haz) {
		this.haz = haz;
	}

	public String getHazClass() {
		return hazClass;
	}

	public void setHazClass(String hazClass) {
		this.hazClass = hazClass;
	}

	public String getTypeOfContainer() {
		return typeOfContainer;
	}

	public void setTypeOfContainer(String typeOfContainer) {
		this.typeOfContainer = typeOfContainer;
	}

	public String getOldTypeOfContainer() {
		return oldTypeOfContainer;
	}

	public void setOldTypeOfContainer(String oldTypeOfContainer) {
		this.oldTypeOfContainer = oldTypeOfContainer;
	}

	public String getOverDimension() {
		return overDimension;
	}

	public void setOverDimension(String overDimension) {
		this.overDimension = overDimension;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getInternalShifting() {
		return internalShifting;
	}

	public void setInternalShifting(String internalShifting) {
		this.internalShifting = internalShifting;
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

	public BigDecimal getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(BigDecimal containerWeight) {
		this.containerWeight = containerWeight;
	}

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public String getContainerSealNo() {
		return containerSealNo;
	}

	public void setContainerSealNo(String containerSealNo) {
		this.containerSealNo = containerSealNo;
	}

	public String getCustomsSealNo() {
		return customsSealNo;
	}

	public void setCustomsSealNo(String customsSealNo) {
		this.customsSealNo = customsSealNo;
	}

	public String getDataInputStatus() {
		return dataInputStatus;
	}

	public void setDataInputStatus(String dataInputStatus) {
		this.dataInputStatus = dataInputStatus;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public String getCustomsSample() {
		return customsSample;
	}

	public void setCustomsSample(String customsSample) {
		this.customsSample = customsSample;
	}

	public Date getCustomsSampleDate() {
		return customsSampleDate;
	}

	public void setCustomsSampleDate(Date customsSampleDate) {
		this.customsSampleDate = customsSampleDate;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}

	public String getReExport() {
		return reExport;
	}

	public void setReExport(String reExport) {
		this.reExport = reExport;
	}

	public String getExtraTransport() {
		return extraTransport;
	}

	public void setExtraTransport(String extraTransport) {
		this.extraTransport = extraTransport;
	}

	public Date getExtraTransportDate() {
		return extraTransportDate;
	}

	public void setExtraTransportDate(Date extraTransportDate) {
		this.extraTransportDate = extraTransportDate;
	}

	public String getFumigation() {
		return fumigation;
	}

	public void setFumigation(String fumigation) {
		this.fumigation = fumigation;
	}

	public Date getFumigationDate() {
		return fumigationDate;
	}

	public void setFumigationDate(Date fumigationDate) {
		this.fumigationDate = fumigationDate;
	}

	public String getMovementReqId() {
		return movementReqId;
	}

	public void setMovementReqId(String movementReqId) {
		this.movementReqId = movementReqId;
	}

	public String getSealCuttingType() {
		return sealCuttingType;
	}

	public void setSealCuttingType(String sealCuttingType) {
		this.sealCuttingType = sealCuttingType;
	}

	public BigDecimal getCargoValue() {
		return cargoValue;
	}

	public void setCargoValue(BigDecimal cargoValue) {
		this.cargoValue = cargoValue;
	}

	public BigDecimal getCargoDuty() {
		return cargoDuty;
	}

	public void setCargoDuty(BigDecimal cargoDuty) {
		this.cargoDuty = cargoDuty;
	}

	public String getBlStatus() {
		return blStatus;
	}

	public void setBlStatus(String blStatus) {
		this.blStatus = blStatus;
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

	public String getSealCutTransId() {
		return sealCutTransId;
	}

	public void setSealCutTransId(String sealCutTransId) {
		this.sealCutTransId = sealCutTransId;
	}

	public String getContainerExamStatus() {
		return containerExamStatus;
	}

	public void setContainerExamStatus(String containerExamStatus) {
		this.containerExamStatus = containerExamStatus;
	}

	public Date getSealCutReqDate() {
		return sealCutReqDate;
	}

	public void setSealCutReqDate(Date sealCutReqDate) {
		this.sealCutReqDate = sealCutReqDate;
	}

	public Date getContainerExamDate() {
		return containerExamDate;
	}

	public void setContainerExamDate(Date containerExamDate) {
		this.containerExamDate = containerExamDate;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSealCutRemarks() {
		return sealCutRemarks;
	}

	public void setSealCutRemarks(String sealCutRemarks) {
		this.sealCutRemarks = sealCutRemarks;
	}

	public String getContainerExamRemarks() {
		return containerExamRemarks;
	}

	public void setContainerExamRemarks(String containerExamRemarks) {
		this.containerExamRemarks = containerExamRemarks;
	}

	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}

	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}

	public BigDecimal getDamagedNoOfPackages() {
		return damagedNoOfPackages;
	}

	public void setDamagedNoOfPackages(BigDecimal damagedNoOfPackages) {
		this.damagedNoOfPackages = damagedNoOfPackages;
	}

	public String getYardLocation() {
		return yardLocation;
	}

	public void setYardLocation(String yardLocation) {
		this.yardLocation = yardLocation;
	}

	public String getYardBlock() {
		return yardBlock;
	}

	public void setYardBlock(String yardBlock) {
		this.yardBlock = yardBlock;
	}

	public String getBlockCellNo() {
		return blockCellNo;
	}

	public void setBlockCellNo(String blockCellNo) {
		this.blockCellNo = blockCellNo;
	}

	public String getYardLocation1() {
		return yardLocation1;
	}

	public void setYardLocation1(String yardLocation1) {
		this.yardLocation1 = yardLocation1;
	}

	public String getYardBlock1() {
		return yardBlock1;
	}

	public void setYardBlock1(String yardBlock1) {
		this.yardBlock1 = yardBlock1;
	}

	public String getBlockCellNo1() {
		return blockCellNo1;
	}

	public void setBlockCellNo1(String blockCellNo1) {
		this.blockCellNo1 = blockCellNo1;
	}

	public String getBlTariffNo() {
		return blTariffNo;
	}

	public void setBlTariffNo(String blTariffNo) {
		this.blTariffNo = blTariffNo;
	}

	public String getDeStuffId() {
		return deStuffId;
	}

	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}

	public String getTypeOfCargo() {
		return typeOfCargo;
	}

	public void setTypeOfCargo(String typeOfCargo) {
		this.typeOfCargo = typeOfCargo;
	}

	public String getDestuffStatus() {
		return destuffStatus;
	}

	public void setDestuffStatus(String destuffStatus) {
		this.destuffStatus = destuffStatus;
	}

	public Date getDeStuffDate() {
		return deStuffDate;
	}

	public void setDeStuffDate(Date deStuffDate) {
		this.deStuffDate = deStuffDate;
	}

	public String getDoEntryFlag() {
		return doEntryFlag;
	}

	public void setDoEntryFlag(String doEntryFlag) {
		this.doEntryFlag = doEntryFlag;
	}

	public Date getDoEntryDate() {
		return doEntryDate;
	}

	public void setDoEntryDate(Date doEntryDate) {
		this.doEntryDate = doEntryDate;
	}

	public String getForceEntryFlag() {
		return forceEntryFlag;
	}

	public void setForceEntryFlag(String forceEntryFlag) {
		this.forceEntryFlag = forceEntryFlag;
	}

	public Date getForceEntryDate() {
		return forceEntryDate;
	}

	public void setForceEntryDate(Date forceEntryDate) {
		this.forceEntryDate = forceEntryDate;
	}

	public String getForceEntryApproval() {
		return forceEntryApproval;
	}

	public void setForceEntryApproval(String forceEntryApproval) {
		this.forceEntryApproval = forceEntryApproval;
	}

	public String getForceEntryRemarks() {
		return forceEntryRemarks;
	}

	public void setForceEntryRemarks(String forceEntryRemarks) {
		this.forceEntryRemarks = forceEntryRemarks;
	}

	public String getExamTallyId() {
		return examTallyId;
	}

	public void setExamTallyId(String examTallyId) {
		this.examTallyId = examTallyId;
	}

	public Date getExamTallyDate() {
		return examTallyDate;
	}

	public void setExamTallyDate(Date examTallyDate) {
		this.examTallyDate = examTallyDate;
	}

	public String getExamCrgTallyId() {
		return examCrgTallyId;
	}

	public void setExamCrgTallyId(String examCrgTallyId) {
		this.examCrgTallyId = examCrgTallyId;
	}

	public Date getExamCrgTallyDate() {
		return examCrgTallyDate;
	}

	public void setExamCrgTallyDate(Date examCrgTallyDate) {
		this.examCrgTallyDate = examCrgTallyDate;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
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

	public String getGateOutType() {
		return gateOutType;
	}

	public void setGateOutType(String gateOutType) {
		this.gateOutType = gateOutType;
	}

	public String getSpecialDelivery() {
		return specialDelivery;
	}

	public void setSpecialDelivery(String specialDelivery) {
		this.specialDelivery = specialDelivery;
	}

	public String getHoldingAgent() {
		return holdingAgent;
	}

	public void setHoldingAgent(String holdingAgent) {
		this.holdingAgent = holdingAgent;
	}

	public String getHoldingAgentName() {
		return holdingAgentName;
	}

	public void setHoldingAgentName(String holdingAgentName) {
		this.holdingAgentName = holdingAgentName;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getHoldRemarks() {
		return holdRemarks;
	}

	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getHoldDocRefNo() {
		return holdDocRefNo;
	}

	public void setHoldDocRefNo(String holdDocRefNo) {
		this.holdDocRefNo = holdDocRefNo;
	}

	public String getReleaseAgent() {
		return releaseAgent;
	}

	public void setReleaseAgent(String releaseAgent) {
		this.releaseAgent = releaseAgent;
	}

	public String getReleaseRemarks() {
		return releaseRemarks;
	}

	public void setReleaseRemarks(String releaseRemarks) {
		this.releaseRemarks = releaseRemarks;
	}

	public String getWeighmentFlag() {
		return weighmentFlag;
	}

	public void setWeighmentFlag(String weighmentFlag) {
		this.weighmentFlag = weighmentFlag;
	}

	public Date getWeighmentDate() {
		return weighmentDate;
	}

	public void setWeighmentDate(Date weighmentDate) {
		this.weighmentDate = weighmentDate;
	}

	public char getLabour() {
		return labour;
	}

	public void setLabour(char labour) {
		this.labour = labour;
	}

	public char getFk3mt() {
		return fk3mt;
	}

	public void setFk3mt(char fk3mt) {
		this.fk3mt = fk3mt;
	}

	public char getFk5mt() {
		return fk5mt;
	}

	public void setFk5mt(char fk5mt) {
		this.fk5mt = fk5mt;
	}

	public char getFk10mt() {
		return fk10mt;
	}

	public void setFk10mt(char fk10mt) {
		this.fk10mt = fk10mt;
	}

	public char getHydra12mt() {
		return hydra12mt;
	}

	public void setHydra12mt(char hydra12mt) {
		this.hydra12mt = hydra12mt;
	}

	public char getCrane() {
		return crane;
	}

	public void setCrane(char crane) {
		this.crane = crane;
	}

	public char getKalmar() {
		return kalmar;
	}

	public void setKalmar(char kalmar) {
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

	public char getRtoCharges() {
		return rtoCharges;
	}

	public void setRtoCharges(char rtoCharges) {
		this.rtoCharges = rtoCharges;
	}

	public Date getEquipmentEntryDate() {
		return equipmentEntryDate;
	}

	public void setEquipmentEntryDate(Date equipmentEntryDate) {
		this.equipmentEntryDate = equipmentEntryDate;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public char getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(char noticeType) {
		this.noticeType = noticeType;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public String getSecondNoticeId() {
		return secondNoticeId;
	}

	public void setSecondNoticeId(String secondNoticeId) {
		this.secondNoticeId = secondNoticeId;
	}

	public Date getSecondNoticeDate() {
		return secondNoticeDate;
	}

	public void setSecondNoticeDate(Date secondNoticeDate) {
		this.secondNoticeDate = secondNoticeDate;
	}

	public String getFinalNoticeId() {
		return finalNoticeId;
	}

	public void setFinalNoticeId(String finalNoticeId) {
		this.finalNoticeId = finalNoticeId;
	}

	public Date getFinalNoticeDate() {
		return finalNoticeDate;
	}

	public void setFinalNoticeDate(Date finalNoticeDate) {
		this.finalNoticeDate = finalNoticeDate;
	}

	public char getAuctionStatus() {
		return auctionStatus;
	}

	public void setAuctionStatus(char auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSpecialRemarks() {
		return specialRemarks;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	public BigDecimal getCargoWt() {
		return cargoWt;
	}

	public void setCargoWt(BigDecimal cargoWt) {
		this.cargoWt = cargoWt;
	}

	public BigDecimal getGrossWt() {
		return grossWt;
	}

	public void setGrossWt(BigDecimal grossWt) {
		this.grossWt = grossWt;
	}

	public BigDecimal getEirGrossWeight() {
		return eirGrossWeight;
	}

	public void setEirGrossWeight(BigDecimal eirGrossWeight) {
		this.eirGrossWeight = eirGrossWeight;
	}

	public int getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(int noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public int getExaminedPackages() {
		return examinedPackages;
	}

	public void setExaminedPackages(int examinedPackages) {
		this.examinedPackages = examinedPackages;
	}

	public BigDecimal getWeighmentWeight() {
		return weighmentWeight;
	}

	public void setWeighmentWeight(BigDecimal weighmentWeight) {
		this.weighmentWeight = weighmentWeight;
	}

	public int getPackagesDeStuffed() {
		return packagesDeStuffed;
	}

	public void setPackagesDeStuffed(int packagesDeStuffed) {
		this.packagesDeStuffed = packagesDeStuffed;
	}

	public int getPackagesStuffed() {
		return packagesStuffed;
	}

	public void setPackagesStuffed(int packagesStuffed) {
		this.packagesStuffed = packagesStuffed;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getGrnNo() {
		return grnNo;
	}

	public void setGrnNo(String grnNo) {
		this.grnNo = grnNo;
	}

	public Date getGrnDate() {
		return grnDate;
	}

	public void setGrnDate(Date grnDate) {
		this.grnDate = grnDate;
	}

	public String getCinNo() {
		return cinNo;
	}

	public void setCinNo(String cinNo) {
		this.cinNo = cinNo;
	}

	public Date getCinDate() {
		return cinDate;
	}

	public void setCinDate(Date cinDate) {
		this.cinDate = cinDate;
	}

	public BigDecimal getStampDuty() {
		return stampDuty;
	}

	public void setStampDuty(BigDecimal stampDuty) {
		this.stampDuty = stampDuty;
	}

	public String getDoNo() {
		return doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public Date getDoDate() {
		return doDate;
	}

	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}

	public Date getDoValidityDate() {
		return doValidityDate;
	}

	public void setDoValidityDate(Date doValidityDate) {
		this.doValidityDate = doValidityDate;
	}

	public String getIceCode() {
		return iceCode;
	}

	public void setIceCode(String iceCode) {
		this.iceCode = iceCode;
	}

	public String getChaCode() {
		return chaCode;
	}

	public void setChaCode(String chaCode) {
		this.chaCode = chaCode;
	}

	public String getCopanCode() {
		return copanCode;
	}

	public void setCopanCode(String copanCode) {
		this.copanCode = copanCode;
	}

	public String getScanningDoneStatus() {
		return scanningDoneStatus;
	}

	public void setScanningDoneStatus(String scanningDoneStatus) {
		this.scanningDoneStatus = scanningDoneStatus;
	}

	public String getScanningEditedBy() {
		return scanningEditedBy;
	}

	public void setScanningEditedBy(String scanningEditedBy) {
		this.scanningEditedBy = scanningEditedBy;
	}

	public Date getScanningDoneDate() {
		return scanningDoneDate;
	}

	public void setScanningDoneDate(Date scanningDoneDate) {
		this.scanningDoneDate = scanningDoneDate;
	}

	public String getOocNo() {
		return oocNo;
	}

	public void setOocNo(String oocNo) {
		this.oocNo = oocNo;
	}

	public Date getOocDate() {
		return oocDate;
	}

	public void setOocDate(Date oocDate) {
		this.oocDate = oocDate;
	}

	public Date getLoadingStartDate() {
		return loadingStartDate;
	}

	public void setLoadingStartDate(Date loadingStartDate) {
		this.loadingStartDate = loadingStartDate;
	}

	public Date getLoadingEndDate() {
		return loadingEndDate;
	}

	public void setLoadingEndDate(Date loadingEndDate) {
		this.loadingEndDate = loadingEndDate;
	}

	public char getReeferEscalation() {
		return reeferEscalation;
	}

	public void setReeferEscalation(char reeferEscalation) {
		this.reeferEscalation = reeferEscalation;
	}

	public char getGenSet() {
		return genSet;
	}

	public void setGenSet(char genSet) {
		this.genSet = genSet;
	}

	public String getIgmContainerHold() {
		return igmContainerHold;
	}

	public void setIgmContainerHold(String igmContainerHold) {
		this.igmContainerHold = igmContainerHold;
	}

	public String getIgmContainerAgency() {
		return igmContainerAgency;
	}

	public void setIgmContainerAgency(String igmContainerAgency) {
		this.igmContainerAgency = igmContainerAgency;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public char getPlugInFlag() {
		return plugInFlag;
	}

	public void setPlugInFlag(char plugInFlag) {
		this.plugInFlag = plugInFlag;
	}

	public Date getPlugInDate() {
		return plugInDate;
	}

	public void setPlugInDate(Date plugInDate) {
		this.plugInDate = plugInDate;
	}

	public String getPlugInUser() {
		return plugInUser;
	}

	public void setPlugInUser(String plugInUser) {
		this.plugInUser = plugInUser;
	}

	public char getPlugOutFlag() {
		return plugOutFlag;
	}

	public void setPlugOutFlag(char plugOutFlag) {
		this.plugOutFlag = plugOutFlag;
	}

	public Date getPlugOutDate() {
		return plugOutDate;
	}

	public void setPlugOutDate(Date plugOutDate) {
		this.plugOutDate = plugOutDate;
	}

	public String getPlugOutUser() {
		return plugOutUser;
	}

	public void setPlugOutUser(String plugOutUser) {
		this.plugOutUser = plugOutUser;
	}

	public Date getPlugEntryDate() {
		return plugEntryDate;
	}

	public void setPlugEntryDate(Date plugEntryDate) {
		this.plugEntryDate = plugEntryDate;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getSealCutCreatedBy() {
		return sealCutCreatedBy;
	}

	public void setSealCutCreatedBy(String sealCutCreatedBy) {
		this.sealCutCreatedBy = sealCutCreatedBy;
	}

	public String getSealCutApprovedBy() {
		return sealCutApprovedBy;
	}

	public void setSealCutApprovedBy(String sealCutApprovedBy) {
		this.sealCutApprovedBy = sealCutApprovedBy;
	}

	public String getContainerExamCreatedBy() {
		return containerExamCreatedBy;
	}

	public void setContainerExamCreatedBy(String containerExamCreatedBy) {
		this.containerExamCreatedBy = containerExamCreatedBy;
	}

	public Date getContainerExamCreatedDate() {
		return containerExamCreatedDate;
	}

	public void setContainerExamCreatedDate(Date containerExamCreatedDate) {
		this.containerExamCreatedDate = containerExamCreatedDate;
	}

	public String getContainerExamApprovedBy() {
		return containerExamApprovedBy;
	}

	public void setContainerExamApprovedBy(String containerExamApprovedBy) {
		this.containerExamApprovedBy = containerExamApprovedBy;
	}

	public Date getContainerExamApprovedDate() {
		return containerExamApprovedDate;
	}

	public void setContainerExamApprovedDate(Date containerExamApprovedDate) {
		this.containerExamApprovedDate = containerExamApprovedDate;
	}

	public Date getSealCutCreatedDate() {
		return sealCutCreatedDate;
	}

	public void setSealCutCreatedDate(Date sealCutCreatedDate) {
		this.sealCutCreatedDate = sealCutCreatedDate;
	}

	public Date getSealCutApprovedDate() {
		return sealCutApprovedDate;
	}

	public void setSealCutApprovedDate(Date sealCutApprovedDate) {
		this.sealCutApprovedDate = sealCutApprovedDate;
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

	public char getTransfer() {
		return transfer;
	}

	public void setTransfer(char transfer) {
		this.transfer = transfer;
	}

	public Date getDrtTransferDate() {
		return drtTransferDate;
	}

	public void setDrtTransferDate(Date drtTransferDate) {
		this.drtTransferDate = drtTransferDate;
	}

	public char getDrt() {
		return drt;
	}

	public void setDrt(char drt) {
		this.drt = drt;
	}

	public int getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getRemoveUser() {
		return removeUser;
	}

	public void setRemoveUser(String removeUser) {
		this.removeUser = removeUser;
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public String getLabourCompanyName() {
		return labourCompanyName;
	}

	public void setLabourCompanyName(String labourCompanyName) {
		this.labourCompanyName = labourCompanyName;
	}

	public char getLmportExaminationStatus() {
		return lmportExaminationStatus;
	}

	public void setLmportExaminationStatus(char lmportExaminationStatus) {
		this.lmportExaminationStatus = lmportExaminationStatus;
	}

	public String getUpTariffFwd() {
		return upTariffFwd;
	}

	public void setUpTariffFwd(String upTariffFwd) {
		this.upTariffFwd = upTariffFwd;
	}

	public String getUpTariffNo() {
		return upTariffNo;
	}

	public void setUpTariffNo(String upTariffNo) {
		this.upTariffNo = upTariffNo;
	}

	public String getUpTariffAmndNo() {
		return upTariffAmndNo;
	}

	public void setUpTariffAmndNo(String upTariffAmndNo) {
		this.upTariffAmndNo = upTariffAmndNo;
	}

	public String getUpTariffDelMode() {
		return upTariffDelMode;
	}

	public void setUpTariffDelMode(String upTariffDelMode) {
		this.upTariffDelMode = upTariffDelMode;
	}

	public String getOthPartyId() {
		return othPartyId;
	}

	public void setOthPartyId(String othPartyId) {
		this.othPartyId = othPartyId;
	}

	public char getInvoiceAssesed() {
		return invoiceAssesed;
	}

	public void setInvoiceAssesed(char invoiceAssesed) {
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

	public String getSubItemNo() {
		return subItemNo;
	}

	public void setSubItemNo(String subItemNo) {
		this.subItemNo = subItemNo;
	}

	public Date getTariffValid() {
		return tariffValid;
	}

	public void setTariffValid(Date tariffValid) {
		this.tariffValid = tariffValid;
	}

	public String getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(String tariffCode) {
		this.tariffCode = tariffCode;
	}

	public String getDestuffWoTransId() {
		return destuffWoTransId;
	}

	public void setDestuffWoTransId(String destuffWoTransId) {
		this.destuffWoTransId = destuffWoTransId;
	}

	

	public String getSsrTransId() {
		return ssrTransId;
	}



	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}



	public String getSealCutWoTransId() {
		return sealCutWoTransId;
	}

	public void setSealCutWoTransId(String sealCutWoTransId) {
		this.sealCutWoTransId = sealCutWoTransId;
	}

	public Date getSealCutWoTransDate() {
		return sealCutWoTransDate;
	}

	public void setSealCutWoTransDate(Date sealCutWoTransDate) {
		this.sealCutWoTransDate = sealCutWoTransDate;
	}

	public String getContainerExamWoTransId() {
		return containerExamWoTransId;
	}

	public void setContainerExamWoTransId(String containerExamWoTransId) {
		this.containerExamWoTransId = containerExamWoTransId;
	}

	public Date getContainerExamWoTransDate() {
		return containerExamWoTransDate;
	}

	public void setContainerExamWoTransDate(Date containerExamWoTransDate) {
		this.containerExamWoTransDate = containerExamWoTransDate;
	}

	public String getExamRemarks() {
		return examRemarks;
	}

	public void setExamRemarks(String examRemarks) {
		this.examRemarks = examRemarks;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public String getHsnNo() {
		return hsnNo;
	}

	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getMergeStatus() {
		return mergeStatus;
	}

	public void setMergeStatus(String mergeStatus) {
		this.mergeStatus = mergeStatus;
	}

	public Date getMergeCreatedDate() {
		return mergeCreatedDate;
	}

	public void setMergeCreatedDate(Date mergeCreatedDate) {
		this.mergeCreatedDate = mergeCreatedDate;
	}

	public String getMergeCreatedBy() {
		return mergeCreatedBy;
	}

	public void setMergeCreatedBy(String mergeCreatedBy) {
		this.mergeCreatedBy = mergeCreatedBy;
	}

	public char getRefer() {
		return refer;
	}

	public void setRefer(char refer) {
		this.refer = refer;
	}

	public BigDecimal getWeighmentWt() {
		return weighmentWt;
	}

	public void setWeighmentWt(BigDecimal weighmentWt) {
		this.weighmentWt = weighmentWt;
	}

	public String getMergeApprovedBy() {
		return mergeApprovedBy;
	}

	public void setMergeApprovedBy(String mergeApprovedBy) {
		this.mergeApprovedBy = mergeApprovedBy;
	}

	public Date getMergeApprovedDate() {
		return mergeApprovedDate;
	}

	public void setMergeApprovedDate(Date mergeApprovedDate) {
		this.mergeApprovedDate = mergeApprovedDate;
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

	public char getPnStatus() {
		return pnStatus;
	}

	public void setPnStatus(char pnStatus) {
		this.pnStatus = pnStatus;
	}

	public char getLastInvoiceAssesed() {
		return lastInvoiceAssesed;
	}

	public void setLastInvoiceAssesed(char lastInvoiceAssesed) {
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

	public char getLclZeroEntryFlag() {
		return lclZeroEntryFlag;
	}

	public void setLclZeroEntryFlag(char lclZeroEntryFlag) {
		this.lclZeroEntryFlag = lclZeroEntryFlag;
	}

	public Date getLclZeroEntryDate() {
		return lclZeroEntryDate;
	}

	public void setLclZeroEntryDate(Date lclZeroEntryDate) {
		this.lclZeroEntryDate = lclZeroEntryDate;
	}

	public Date getLclZeroEntryValidityDate() {
		return lclZeroEntryValidityDate;
	}

	public void setLclZeroEntryValidityDate(Date lclZeroEntryValidityDate) {
		this.lclZeroEntryValidityDate = lclZeroEntryValidityDate;
	}

	public String getLclZeroEntryCreatedBy() {
		return lclZeroEntryCreatedBy;
	}

	public void setLclZeroEntryCreatedBy(String lclZeroEntryCreatedBy) {
		this.lclZeroEntryCreatedBy = lclZeroEntryCreatedBy;
	}

	public String getLclZeroEntryApproval() {
		return lclZeroEntryApproval;
	}

	public void setLclZeroEntryApproval(String lclZeroEntryApproval) {
		this.lclZeroEntryApproval = lclZeroEntryApproval;
	}

	public String getLclZeroEntryRemarks() {
		return lclZeroEntryRemarks;
	}

	public void setLclZeroEntryRemarks(String lclZeroEntryRemarks) {
		this.lclZeroEntryRemarks = lclZeroEntryRemarks;
	}

	public char getRscanOut() {
		return rscanOut;
	}

	public void setRscanOut(char rscanOut) {
		this.rscanOut = rscanOut;
	}

	public char getRscanIn() {
		return rscanIn;
	}

	public void setRscanIn(char rscanIn) {
		this.rscanIn = rscanIn;
	}

	public String getDestuffWoCreatedBy() {
		return destuffWoCreatedBy;
	}

	public void setDestuffWoCreatedBy(String destuffWoCreatedBy) {
		this.destuffWoCreatedBy = destuffWoCreatedBy;
	}

	public Date getDestuffWoDate() {
		return destuffWoDate;
	}

	public void setDestuffWoDate(Date destuffWoDate) {
		this.destuffWoDate = destuffWoDate;
	}

	public String getNewFwdId() {
		return newFwdId;
	}

	public void setNewFwdId(String newFwdId) {
		this.newFwdId = newFwdId;
	}

	public char getDocumentUploadFlag() {
		return documentUploadFlag;
	}

	public void setDocumentUploadFlag(char documentUploadFlag) {
		this.documentUploadFlag = documentUploadFlag;
	}

	public char getAucbid() {
		return aucbid;
	}

	public void setAucbid(char aucbid) {
		this.aucbid = aucbid;
	}

	public char getOdcStatus() {
		return odcStatus;
	}

	public void setOdcStatus(char odcStatus) {
		this.odcStatus = odcStatus;
	}

	public char getLclcnInvoiceAssesed() {
		return lclcnInvoiceAssesed;
	}

	public void setLclcnInvoiceAssesed(char lclcnInvoiceAssesed) {
		this.lclcnInvoiceAssesed = lclcnInvoiceAssesed;
	}

	public String getLclcnAssesmentId() {
		return lclcnAssesmentId;
	}

	public void setLclcnAssesmentId(String lclcnAssesmentId) {
		this.lclcnAssesmentId = lclcnAssesmentId;
	}

	public String getLclcnInvoiceNo() {
		return lclcnInvoiceNo;
	}

	public void setLclcnInvoiceNo(String lclcnInvoiceNo) {
		this.lclcnInvoiceNo = lclcnInvoiceNo;
	}

	public Date getLclcnInvoiceDate() {
		return lclcnInvoiceDate;
	}

	public void setLclcnInvoiceDate(Date lclcnInvoiceDate) {
		this.lclcnInvoiceDate = lclcnInvoiceDate;
	}

	public char getLclcnCreditType() {
		return lclcnCreditType;
	}

	public void setLclcnCreditType(char lclcnCreditType) {
		this.lclcnCreditType = lclcnCreditType;
	}

	public String getLclcnInvoiceCategory() {
		return lclcnInvoiceCategory;
	}

	public void setLclcnInvoiceCategory(String lclcnInvoiceCategory) {
		this.lclcnInvoiceCategory = lclcnInvoiceCategory;
	}

	public BigDecimal getLclcnBillAmt() {
		return lclcnBillAmt;
	}

	public void setLclcnBillAmt(BigDecimal lclcnBillAmt) {
		this.lclcnBillAmt = lclcnBillAmt;
	}

	public BigDecimal getLclcnInvoiceAmt() {
		return lclcnInvoiceAmt;
	}

	public void setLclcnInvoiceAmt(BigDecimal lclcnInvoiceAmt) {
		this.lclcnInvoiceAmt = lclcnInvoiceAmt;
	}

	public char getCodeccoInStatus() {
		return codeccoInStatus;
	}

	public void setCodeccoInStatus(char codeccoInStatus) {
		this.codeccoInStatus = codeccoInStatus;
	}

	public Date getCodeccoInDate() {
		return codeccoInDate;
	}

	public void setCodeccoInDate(Date codeccoInDate) {
		this.codeccoInDate = codeccoInDate;
	}

	public char getCodeccoOutStatus() {
		return codeccoOutStatus;
	}

	public void setCodeccoOutStatus(char codeccoOutStatus) {
		this.codeccoOutStatus = codeccoOutStatus;
	}

	public Date getCodeccoOutDate() {
		return codeccoOutDate;
	}

	public void setCodeccoOutDate(Date codeccoOutDate) {
		this.codeccoOutDate = codeccoOutDate;
	}

	public char getCodeccoDstStatus() {
		return codeccoDstStatus;
	}

	public void setCodeccoDstStatus(char codeccoDstStatus) {
		this.codeccoDstStatus = codeccoDstStatus;
	}

	public Date getCodeccoDstDate() {
		return codeccoDstDate;
	}

	public void setCodeccoDstDate(Date codeccoDstDate) {
		this.codeccoDstDate = codeccoDstDate;
	}

	public char getCodeccoMtInStatus() {
		return codeccoMtInStatus;
	}

	public void setCodeccoMtInStatus(char codeccoMtInStatus) {
		this.codeccoMtInStatus = codeccoMtInStatus;
	}

	public Date getCodeccoMtInDate() {
		return codeccoMtInDate;
	}

	public void setCodeccoMtInDate(Date codeccoMtInDate) {
		this.codeccoMtInDate = codeccoMtInDate;
	}

	public char getCodeccoTerminalOutStatus() {
		return codeccoTerminalOutStatus;
	}

	public void setCodeccoTerminalOutStatus(char codeccoTerminalOutStatus) {
		this.codeccoTerminalOutStatus = codeccoTerminalOutStatus;
	}

	public Date getCodeccoTerminalOutDate() {
		return codeccoTerminalOutDate;
	}

	public void setCodeccoTerminalOutDate(Date codeccoTerminalOutDate) {
		this.codeccoTerminalOutDate = codeccoTerminalOutDate;
	}

	public BigDecimal getThCharges() {
		return thCharges;
	}

	public void setThCharges(BigDecimal thCharges) {
		this.thCharges = thCharges;
	}

	public Date getPartDeStuffDate() {
		return partDeStuffDate;
	}

	public void setPartDeStuffDate(Date partDeStuffDate) {
		this.partDeStuffDate = partDeStuffDate;
	}

	public String getPartDeStuffId() {
		return partDeStuffId;
	}

	public void setPartDeStuffId(String partDeStuffId) {
		this.partDeStuffId = partDeStuffId;
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

	public BigDecimal getCrgStorageAmt() {
		return crgStorageAmt;
	}

	public void setCrgStorageAmt(BigDecimal crgStorageAmt) {
		this.crgStorageAmt = crgStorageAmt;
	}

	public BigDecimal getCrgStorageDay() {
		return crgStorageDay;
	}

	public void setCrgStorageDay(BigDecimal crgStorageDay) {
		this.crgStorageDay = crgStorageDay;
	}

	public BigDecimal getAuctionAmt() {
		return auctionAmt;
	}

	public void setAuctionAmt(BigDecimal auctionAmt) {
		this.auctionAmt = auctionAmt;
	}

	public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}

	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
	}

	public char getAuctionFlag() {
		return auctionFlag;
	}

	public void setAuctionFlag(char auctionFlag) {
		this.auctionFlag = auctionFlag;
	}

	public char getForceEntryFlagInv() {
		return forceEntryFlagInv;
	}

	public void setForceEntryFlagInv(char forceEntryFlagInv) {
		this.forceEntryFlagInv = forceEntryFlagInv;
	}

	public char getScanActualStatus() {
		return scanActualStatus;
	}

	public void setScanActualStatus(char scanActualStatus) {
		this.scanActualStatus = scanActualStatus;
	}

	public String getLclContUpTariffFwd() {
		return lclContUpTariffFwd;
	}

	public void setLclContUpTariffFwd(String lclContUpTariffFwd) {
		this.lclContUpTariffFwd = lclContUpTariffFwd;
	}

	public String getLclContUpTariffNo() {
		return lclContUpTariffNo;
	}

	public void setLclContUpTariffNo(String lclContUpTariffNo) {
		this.lclContUpTariffNo = lclContUpTariffNo;
	}

	public String getLclContUpTariffAmndNo() {
		return lclContUpTariffAmndNo;
	}

	public void setLclContUpTariffAmndNo(String lclContUpTariffAmndNo) {
		this.lclContUpTariffAmndNo = lclContUpTariffAmndNo;
	}

	public String getLclContUpTariffDelMode() {
		return lclContUpTariffDelMode;
	}

	public void setLclContUpTariffDelMode(String lclContUpTariffDelMode) {
		this.lclContUpTariffDelMode = lclContUpTariffDelMode;
	}

	public Date getLastLocationReceivedDate() {
		return lastLocationReceivedDate;
	}

	public void setLastLocationReceivedDate(Date lastLocationReceivedDate) {
		this.lastLocationReceivedDate = lastLocationReceivedDate;
	}

	public Date getLocationReceivedDate() {
		return locationReceivedDate;
	}

	public void setLocationReceivedDate(Date locationReceivedDate) {
		this.locationReceivedDate = locationReceivedDate;
	}

	public char getTagReceiveStatus() {
		return tagReceiveStatus;
	}

	public void setTagReceiveStatus(char tagReceiveStatus) {
		this.tagReceiveStatus = tagReceiveStatus;
	}

	public Date getTagReceiveDate() {
		return tagReceiveDate;
	}

	public void setTagReceiveDate(Date tagReceiveDate) {
		this.tagReceiveDate = tagReceiveDate;
	}

	public String getRfTag() {
		return rfTag;
	}

	public void setRfTag(String rfTag) {
		this.rfTag = rfTag;
	}

	public char getTagRemoveStatus() {
		return tagRemoveStatus;
	}

	public void setTagRemoveStatus(char tagRemoveStatus) {
		this.tagRemoveStatus = tagRemoveStatus;
	}

	public Date getTagRemoveDate() {
		return tagRemoveDate;
	}

	public void setTagRemoveDate(Date tagRemoveDate) {
		this.tagRemoveDate = tagRemoveDate;
	}

	public char getGpSendStatus() {
		return gpSendStatus;
	}

	public void setGpSendStatus(char gpSendStatus) {
		this.gpSendStatus = gpSendStatus;
	}

	public Date getGpSendDate() {
		return gpSendDate;
	}

	public void setGpSendDate(Date gpSendDate) {
		this.gpSendDate = gpSendDate;
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

	public String getInvCount() {
		return invCount;
	}

	public void setInvCount(String invCount) {
		this.invCount = invCount;
	}

	@Override
	public String toString() {
		return "Cfigmcn [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear + ", igmTransId="
				+ igmTransId + ", profitcentreId=" + profitcentreId + ", igmNo=" + igmNo + ", igmLineNo=" + igmLineNo
				+ ", containerNo=" + containerNo + ", cycle=" + cycle + ", cycleUpdatedBy=" + cycleUpdatedBy
				+ ", cycleUpdatedDate=" + cycleUpdatedDate + ", containerSize=" + containerSize + ", containerType="
				+ containerType + ", haz=" + haz + ", hazClass=" + hazClass + ", typeOfContainer=" + typeOfContainer
				+ ", oldTypeOfContainer=" + oldTypeOfContainer + ", overDimension=" + overDimension + ", shift=" + shift
				+ ", iso=" + iso + ", internalShifting=" + internalShifting + ", beNo=" + beNo + ", beDate=" + beDate
				+ ", containerWeight=" + containerWeight + ", containerStatus=" + containerStatus + ", containerSealNo="
				+ containerSealNo + ", customsSealNo=" + customsSealNo + ", dataInputStatus=" + dataInputStatus
				+ ", entryStatus=" + entryStatus + ", customsSample=" + customsSample + ", customsSampleDate="
				+ customsSampleDate + ", scannerType=" + scannerType + ", reExport=" + reExport + ", extraTransport="
				+ extraTransport + ", extraTransportDate=" + extraTransportDate + ", fumigation=" + fumigation
				+ ", fumigationDate=" + fumigationDate + ", movementReqId=" + movementReqId + ", sealCuttingType="
				+ sealCuttingType + ", cargoValue=" + cargoValue + ", cargoDuty=" + cargoDuty + ", blStatus=" + blStatus
				+ ", marketingPerson=" + marketingPerson + ", movementType=" + movementType + ", sealCutTransId="
				+ sealCutTransId + ", containerExamStatus=" + containerExamStatus + ", sealCutReqDate=" + sealCutReqDate
				+ ", containerExamDate=" + containerExamDate + ", mobileNo=" + mobileNo + ", sealCutRemarks="
				+ sealCutRemarks + ", containerExamRemarks=" + containerExamRemarks + ", actualNoOfPackages="
				+ actualNoOfPackages + ", damagedNoOfPackages=" + damagedNoOfPackages + ", yardLocation=" + yardLocation
				+ ", yardBlock=" + yardBlock + ", blockCellNo=" + blockCellNo + ", yardLocation1=" + yardLocation1
				+ ", yardBlock1=" + yardBlock1 + ", blockCellNo1=" + blockCellNo1 + ", blTariffNo=" + blTariffNo
				+ ", deStuffId=" + deStuffId + ", typeOfCargo=" + typeOfCargo + ", destuffStatus=" + destuffStatus
				+ ", deStuffDate=" + deStuffDate + ", doEntryFlag=" + doEntryFlag + ", doEntryDate=" + doEntryDate
				+ ", forceEntryFlag=" + forceEntryFlag + ", forceEntryDate=" + forceEntryDate + ", forceEntryApproval="
				+ forceEntryApproval + ", forceEntryRemarks=" + forceEntryRemarks + ", examTallyId=" + examTallyId
				+ ", examTallyDate=" + examTallyDate + ", examCrgTallyId=" + examCrgTallyId + ", examCrgTallyDate="
				+ examCrgTallyDate + ", vehicleType=" + vehicleType + ", gateInId=" + gateInId + ", gateInDate="
				+ gateInDate + ", gateOutId=" + gateOutId + ", gateOutDate=" + gateOutDate + ", gateOutType="
				+ gateOutType + ", specialDelivery=" + specialDelivery + ", holdingAgent=" + holdingAgent
				+ ", holdingAgentName=" + holdingAgentName + ", holdDate=" + holdDate + ", releaseDate=" + releaseDate
				+ ", holdRemarks=" + holdRemarks + ", holdStatus=" + holdStatus + ", holdDocRefNo=" + holdDocRefNo
				+ ", releaseAgent=" + releaseAgent + ", releaseRemarks=" + releaseRemarks + ", weighmentFlag="
				+ weighmentFlag + ", weighmentDate=" + weighmentDate + ", labour=" + labour + ", fk3mt=" + fk3mt
				+ ", fk5mt=" + fk5mt + ", fk10mt=" + fk10mt + ", hydra12mt=" + hydra12mt + ", crane=" + crane
				+ ", kalmar=" + kalmar + ", pusher=" + pusher + ", emptyUse=" + emptyUse + ", clamp=" + clamp
				+ ", carpenter=" + carpenter + ", rtoCharges=" + rtoCharges + ", equipmentEntryDate="
				+ equipmentEntryDate + ", noticeId=" + noticeId + ", noticeType=" + noticeType + ", noticeDate="
				+ noticeDate + ", secondNoticeId=" + secondNoticeId + ", secondNoticeDate=" + secondNoticeDate
				+ ", finalNoticeId=" + finalNoticeId + ", finalNoticeDate=" + finalNoticeDate + ", auctionStatus="
				+ auctionStatus + ", gatePassNo=" + gatePassNo + ", remarks=" + remarks + ", specialRemarks="
				+ specialRemarks + ", cargoWt=" + cargoWt + ", grossWt=" + grossWt + ", eirGrossWeight="
				+ eirGrossWeight + ", noOfPackages=" + noOfPackages + ", examinedPackages=" + examinedPackages
				+ ", weighmentWeight=" + weighmentWeight + ", packagesDeStuffed=" + packagesDeStuffed
				+ ", packagesStuffed=" + packagesStuffed + ", cha=" + cha + ", grnNo=" + grnNo + ", grnDate=" + grnDate
				+ ", cinNo=" + cinNo + ", cinDate=" + cinDate + ", stampDuty=" + stampDuty + ", doNo=" + doNo
				+ ", doDate=" + doDate + ", doValidityDate=" + doValidityDate + ", iceCode=" + iceCode + ", chaCode="
				+ chaCode + ", copanCode=" + copanCode + ", scanningDoneStatus=" + scanningDoneStatus
				+ ", scanningEditedBy=" + scanningEditedBy + ", scanningDoneDate=" + scanningDoneDate + ", oocNo="
				+ oocNo + ", oocDate=" + oocDate + ", loadingStartDate=" + loadingStartDate + ", loadingEndDate="
				+ loadingEndDate + ", reeferEscalation=" + reeferEscalation + ", genSet=" + genSet
				+ ", igmContainerHold=" + igmContainerHold + ", igmContainerAgency=" + igmContainerAgency
				+ ", temperature=" + temperature + ", plugInFlag=" + plugInFlag + ", plugInDate=" + plugInDate
				+ ", plugInUser=" + plugInUser + ", plugOutFlag=" + plugOutFlag + ", plugOutDate=" + plugOutDate
				+ ", plugOutUser=" + plugOutUser + ", plugEntryDate=" + plugEntryDate + ", status=" + status
				+ ", createdBy=" + createdBy + ", sealCutCreatedBy=" + sealCutCreatedBy + ", sealCutApprovedBy="
				+ sealCutApprovedBy + ", containerExamCreatedBy=" + containerExamCreatedBy
				+ ", containerExamCreatedDate=" + containerExamCreatedDate + ", containerExamApprovedBy="
				+ containerExamApprovedBy + ", containerExamApprovedDate=" + containerExamApprovedDate
				+ ", sealCutCreatedDate=" + sealCutCreatedDate + ", sealCutApprovedDate=" + sealCutApprovedDate
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", transfer=" + transfer
				+ ", drtTransferDate=" + drtTransferDate + ", drt=" + drt + ", noOfItem=" + noOfItem + ", uploadUser="
				+ uploadUser + ", uploadDate=" + uploadDate + ", removeUser=" + removeUser + ", removeDate="
				+ removeDate + ", imagePath=" + imagePath + ", backImage=" + backImage + ", labourCompanyName="
				+ labourCompanyName + ", lmportExaminationStatus=" + lmportExaminationStatus + ", upTariffFwd="
				+ upTariffFwd + ", upTariffNo=" + upTariffNo + ", upTariffAmndNo=" + upTariffAmndNo
				+ ", upTariffDelMode=" + upTariffDelMode + ", othPartyId=" + othPartyId + ", invoiceAssesed="
				+ invoiceAssesed + ", assesmentId=" + assesmentId + ", invoiceNo=" + invoiceNo + ", invoiceDate="
				+ invoiceDate + ", creditType=" + creditType + ", invoiceCategory=" + invoiceCategory + ", billAmt="
				+ billAmt + ", invoiceAmt=" + invoiceAmt + ", subItemNo=" + subItemNo + ", tariffValid=" + tariffValid
				+ ", tariffCode=" + tariffCode + ", destuffWoTransId=" + destuffWoTransId + ", sssTransId=" + ssrTransId
				+ ", sealCutWoTransId=" + sealCutWoTransId + ", sealCutWoTransDate=" + sealCutWoTransDate
				+ ", containerExamWoTransId=" + containerExamWoTransId + ", containerExamWoTransDate="
				+ containerExamWoTransDate + ", examRemarks=" + examRemarks + ", fileNo=" + fileNo + ", lotNo=" + lotNo
				+ ", hsnNo=" + hsnNo + ", fileStatus=" + fileStatus + ", mergeStatus=" + mergeStatus
				+ ", mergeCreatedDate=" + mergeCreatedDate + ", mergeCreatedBy=" + mergeCreatedBy + ", refer=" + refer
				+ ", weighmentWt=" + weighmentWt + ", mergeApprovedBy=" + mergeApprovedBy + ", mergeApprovedDate="
				+ mergeApprovedDate + ", lastAssesmentId=" + lastAssesmentId + ", lastAssesmentDate="
				+ lastAssesmentDate + ", lastInvoiceNo=" + lastInvoiceNo + ", lastInvoiceDate=" + lastInvoiceDate
				+ ", pnStatus=" + pnStatus + ", lastInvoiceAssesed=" + lastInvoiceAssesed + ", lastBillAmt="
				+ lastBillAmt + ", lastInvoiceAmt=" + lastInvoiceAmt + ", lastCreditType=" + lastCreditType
				+ ", lclZeroEntryFlag=" + lclZeroEntryFlag + ", lclZeroEntryDate=" + lclZeroEntryDate
				+ ", lclZeroEntryValidityDate=" + lclZeroEntryValidityDate + ", lclZeroEntryCreatedBy="
				+ lclZeroEntryCreatedBy + ", lclZeroEntryApproval=" + lclZeroEntryApproval + ", lclZeroEntryRemarks="
				+ lclZeroEntryRemarks + ", rscanOut=" + rscanOut + ", rscanIn=" + rscanIn + ", destuffWoCreatedBy="
				+ destuffWoCreatedBy + ", destuffWoDate=" + destuffWoDate + ", newFwdId=" + newFwdId
				+ ", documentUploadFlag=" + documentUploadFlag + ", aucbid=" + aucbid + ", odcStatus=" + odcStatus
				+ ", lclcnInvoiceAssesed=" + lclcnInvoiceAssesed + ", lclcnAssesmentId=" + lclcnAssesmentId
				+ ", lclcnInvoiceNo=" + lclcnInvoiceNo + ", lclcnInvoiceDate=" + lclcnInvoiceDate + ", lclcnCreditType="
				+ lclcnCreditType + ", lclcnInvoiceCategory=" + lclcnInvoiceCategory + ", lclcnBillAmt=" + lclcnBillAmt
				+ ", lclcnInvoiceAmt=" + lclcnInvoiceAmt + ", codeccoInStatus=" + codeccoInStatus + ", codeccoInDate="
				+ codeccoInDate + ", codeccoOutStatus=" + codeccoOutStatus + ", codeccoOutDate=" + codeccoOutDate
				+ ", codeccoDstStatus=" + codeccoDstStatus + ", codeccoDstDate=" + codeccoDstDate
				+ ", codeccoMtInStatus=" + codeccoMtInStatus + ", codeccoMtInDate=" + codeccoMtInDate
				+ ", codeccoTerminalOutStatus=" + codeccoTerminalOutStatus + ", codeccoTerminalOutDate="
				+ codeccoTerminalOutDate + ", thCharges=" + thCharges + ", partDeStuffDate=" + partDeStuffDate
				+ ", partDeStuffId=" + partDeStuffId + ", invoiceDaysOld=" + invoiceDaysOld + ", invoiceAmtOld="
				+ invoiceAmtOld + ", crgStorageAmt=" + crgStorageAmt + ", crgStorageDay=" + crgStorageDay
				+ ", auctionAmt=" + auctionAmt + ", invoiceUptoDate=" + invoiceUptoDate + ", auctionFlag=" + auctionFlag
				+ ", forceEntryFlagInv=" + forceEntryFlagInv + ", scanActualStatus=" + scanActualStatus
				+ ", lclContUpTariffFwd=" + lclContUpTariffFwd + ", lclContUpTariffNo=" + lclContUpTariffNo
				+ ", lclContUpTariffAmndNo=" + lclContUpTariffAmndNo + ", lclContUpTariffDelMode="
				+ lclContUpTariffDelMode + ", lastLocationReceivedDate=" + lastLocationReceivedDate
				+ ", locationReceivedDate=" + locationReceivedDate + ", tagReceiveStatus=" + tagReceiveStatus
				+ ", tagReceiveDate=" + tagReceiveDate + ", rfTag=" + rfTag + ", tagRemoveStatus=" + tagRemoveStatus
				+ ", tagRemoveDate=" + tagRemoveDate + ", gpSendStatus=" + gpSendStatus + ", gpSendDate=" + gpSendDate
				+ ", invCatH=" + invCatH + ", invCatG=" + invCatG + ", isAncillary=" + isAncillary + ", invCount="
				+ invCount + "]";
	}

	public Cfigmcn(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
			String igmNo, String igmLineNo, String containerNo,String containerTransId, String containerSize, String containerType,
			String typeOfContainer, String iso, BigDecimal containerWeight, String containerStatus,
			String customsSealNo, String scannerType, Date gateInDate, Date gateOutDate, String holdRemarks,
			String holdStatus, BigDecimal cargoWt, BigDecimal grossWt, int noOfPackages, String scanningDoneStatus,
			String temperature, char status, String upTariffDelMode, char odcStatus, char lowBed) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerTransId = containerTransId;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.typeOfContainer = typeOfContainer;
		this.iso = iso;
		this.containerWeight = containerWeight;
		this.containerStatus = containerStatus;
		this.customsSealNo = customsSealNo;
		this.scannerType = scannerType;
		this.gateInDate = gateInDate;
		this.gateOutDate = gateOutDate;
		this.holdRemarks = holdRemarks;
		this.holdStatus = holdStatus;
		this.cargoWt = cargoWt;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.scanningDoneStatus = scanningDoneStatus;
		this.temperature = temperature;
		this.status = status;
		this.upTariffDelMode = upTariffDelMode;
		this.odcStatus = odcStatus;
		this.lowBed = lowBed;
	}
	
	

	public Cfigmcn(String finYear, String igmTransId, String containerTransId, String profitcentreId, String igmNo, String igmLineNo,
			String containerNo, String containerSize, String containerType, String typeOfContainer, String iso,
			BigDecimal containerWeight, String containerStatus, BigDecimal grossWt, int noOfPackages,
			String temperature,String gateInId) {
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.containerTransId = containerTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.typeOfContainer = typeOfContainer;
		this.iso = iso;
		this.containerWeight = containerWeight;
		this.containerStatus = containerStatus;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.temperature = temperature;
		this.gateInId = gateInId;
	}

	public Cfigmcn(String igmTransId, String profitcentreId, String igmNo, String igmLineNo, String containerNo,
			String containerTransId, String containerSize, String containerType, BigDecimal containerWeight,
			String containerSealNo, String sealCutTransId, Date sealCutReqDate, Date gateInDate, String gateOutType,
			BigDecimal cargoWt, BigDecimal grossWt, int noOfPackages, String scanningDoneStatus,
			String sealCutWoTransId, Date sealCutWoTransDate, BigDecimal weighmentWt, char odcStatus, char lowBed, String sealCuttingStatus) {
		super();
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerTransId = containerTransId;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerWeight = containerWeight;
		this.containerSealNo = containerSealNo;
		this.sealCutTransId = sealCutTransId;
		this.sealCutReqDate = sealCutReqDate;
		this.gateInDate = gateInDate;
		this.gateOutType = gateOutType;
		this.cargoWt = cargoWt;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.scanningDoneStatus = scanningDoneStatus;
		this.sealCutWoTransId = sealCutWoTransId;
		this.sealCutWoTransDate = sealCutWoTransDate;
		this.weighmentWt = weighmentWt;
		this.odcStatus = odcStatus;
		this.lowBed = lowBed;
		this.sealCuttingStatus = sealCuttingStatus;
	}



	public Cfigmcn(String igmTransId, String profitcentreId, String igmNo, String igmLineNo, String containerNo,
			String containerSize, String containerType, String haz, String typeOfContainer, BigDecimal containerWeight,
			String containerStatus, String containerSealNo, String yardLocation, String yardBlock, String blockCellNo,
			String yardLocation1, String yardBlock1, String blockCellNo1, String deStuffId, String typeOfCargo,
			String gateInId, Date gateInDate, String gateOutType, BigDecimal cargoWt, BigDecimal grossWt,
			int noOfPackages, String cha, String chaCode, char drt, Date destuffWoDate, String createdBy) {
		super();
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.haz = haz;
		this.typeOfContainer = typeOfContainer;
		this.containerWeight = containerWeight;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.yardLocation1 = yardLocation1;
		this.yardBlock1 = yardBlock1;
		this.blockCellNo1 = blockCellNo1;
		this.deStuffId = deStuffId;
		this.typeOfCargo = typeOfCargo;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.gateOutType = gateOutType;
		this.cargoWt = cargoWt;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.cha = cha;
		this.chaCode = chaCode;
		this.drt = drt;
		this.destuffWoDate = destuffWoDate;
		this.createdBy = createdBy;
	}



	public Cfigmcn(String igmTransId, String profitcentreId, String igmNo, String igmLineNo, String containerNo,
			String containerTransId,String shift, String containerSize, String containerType, String haz, BigDecimal containerWeight,
			String containerSealNo, BigDecimal actualNoOfPackages, BigDecimal damagedNoOfPackages, String yardLocation,
			String yardBlock, String blockCellNo, String deStuffId, Date deStuffDate, String gateInId, Date gateInDate,
			BigDecimal cargoWt, BigDecimal grossWt, int noOfPackages, char status) {
		super();
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerTransId = containerTransId;
		this.shift = shift;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.haz = haz;
		this.containerWeight = containerWeight;
		this.containerSealNo = containerSealNo;
		this.actualNoOfPackages = actualNoOfPackages;
		this.damagedNoOfPackages = damagedNoOfPackages;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.deStuffId = deStuffId;
		this.deStuffDate = deStuffDate;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.cargoWt = cargoWt;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.status = status;
	}
	
	
	
	public Cfigmcn(String finYear, String igmTransId, String containerTransId, String profitcentreId, String igmNo, String igmLineNo,
			String containerNo, String containerSize, String containerType, String typeOfContainer, String iso,
			BigDecimal containerWeight, String containerStatus, BigDecimal grossWt, int noOfPackages,
			String temperature,String gateInId, String scannerType,String holdStatus) {
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.containerTransId = containerTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.typeOfContainer = typeOfContainer;
		this.iso = iso;
		this.containerWeight = containerWeight;
		this.containerStatus = containerStatus;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.temperature = temperature;
		this.gateInId = gateInId;
		this.scannerType = scannerType;
		this.holdStatus = holdStatus;
	}
	
	public Cfigmcn(String finYear, String igmTransId, String containerTransId, String profitcentreId, String igmNo, String igmLineNo,
			String containerNo, String containerSize, String containerType, String typeOfContainer, String iso,
			BigDecimal containerWeight, String containerStatus, BigDecimal grossWt, int noOfPackages,
			String temperature,String gateInId, String scannerType,String holdStatus, String gateOutType, BigDecimal cargoWt) {
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.containerTransId = containerTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.typeOfContainer = typeOfContainer;
		this.iso = iso;
		this.containerWeight = containerWeight;
		this.containerStatus = containerStatus;
		this.grossWt = grossWt;
		this.noOfPackages = noOfPackages;
		this.temperature = temperature;
		this.gateInId = gateInId;
		this.scannerType = scannerType;
		this.holdStatus = holdStatus;
		this.gateOutType = gateOutType;
		this.cargoWt = cargoWt;
 	}

	

}
