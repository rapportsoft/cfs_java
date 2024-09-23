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
@Table(name = "cfigmcrg")
@IdClass(CfigmcrgId.class)
public class Cfigmcrg {

	    @Id
	    @Column(name = "Company_Id", length = 6, nullable = false)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6, nullable = false)
	    private String branchId;

	    @Id
	    @Column(name = "Fin_Year", length = 4, nullable = false)
	    private String finYear;

	    @Id
	    @Column(name = "IGM_Trans_Id", length = 10, nullable = false)
	    private String igmTransId;
	    
	    @Id
	    @Column(name = "IGM_Crg_Trans_Id", length = 10, nullable = false)
	    private String igmCrgTransId;

	    @Id
	    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
	    private String profitcentreId;

	    @Id
	    @Column(name = "IGM_Line_No", length = 7, nullable = false)
	    private String igmLineNo;

	    @Id
	    @Column(name = "igm_no", length = 10, nullable = false)
	    private String igmNo;

	    @Column(name = "CYCLE", length = 10)
	    private String cycle = "IMP";

	    @Column(name = "VIA_NO", length = 10)
	    private String viaNo = "";

	    @Column(name = "BL_No", length = 20)
	    private String blNo = "";

	    @Column(name = "BL_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date blDate;

	    @Column(name = "Cargo_Movement", length = 2)
	    private String cargoMovement = "";

	    @Column(name = "Sample_Qty")
	    private int sampleQty = 0;

	    @Column(name = "Importer_Id", length = 7)
	    private String importerId = "";

	    @Column(name = "Importer_Name", length = 100)
	    private String importerName = "";
	    
	    @Column(name = "importer_sr", length = 3)
	    private String importerSr;

	    @Column(name = "importer_address1", length = 250)
	    private String importerAddress1;

	    @Column(name = "importer_address2", length = 100)
	    private String importerAddress2 = "";

	    @Column(name = "importer_address3", length = 100)
	    private String importerAddress3 = "";
	    
	    @Column(name = "Notify_Party_Id", length = 7)
	    private String notifyPartyId = "";
	    
	    @Column(name = "notify_sr", length = 3)
	    private String notifySr;


	    @Column(name = "Notify_Party_Name", length = 100)
	    private String notifyPartyName = "";

	    @Column(name = "Notified_Address1", length = 250)
	    private String notifiedAddress1;

	    @Column(name = "Notified_Address2", length = 100)
	    private String notifiedAddress2 = "";

	    @Column(name = "Notified_Address3", length = 100)
	    private String notifiedAddress3 = "";

	    @Column(name = "Old_Importer_Name", length = 100)
	    private String oldImporterName;

	    @Column(name = "Old_importer_address1", length = 250)
	    private String oldImporterAddress1;

	    @Column(name = "Old_importer_address2", length = 100)
	    private String oldImporterAddress2;

	    @Column(name = "Old_importer_address3", length = 100)
	    private String oldImporterAddress3;

	    @Column(name = "Origin", length = 50)
	    private String origin = "";

	    @Column(name = "Destination", length = 50)
	    private String destination = "";

	    @Column(name = "Commodity_Description", length = 250)
	    private String commodityDescription = "";

	    @Column(name = "commodity_Code", length = 20)
	    private String commodityCode = "";

	    @Column(name = "Area_Used", precision = 16, scale = 3)
	    private BigDecimal areaUsed = BigDecimal.ZERO;

	    @Column(name = "No_Of_Packages", precision = 8, scale = 0)
	    private BigDecimal noOfPackages = BigDecimal.ZERO;

	    @Column(name = "Qty_Taken_Out",  precision = 8, scale = 0)
	    private BigDecimal qtyTakenOut = BigDecimal.ZERO;

	    @Column(name = "Qty_Taken_Out_weight",  precision = 16, scale = 3)
	    private BigDecimal qtyTakenOutWeight = BigDecimal.ZERO;

	    @Column(name = "Gross_Weight", precision = 15, scale = 3)
	    private BigDecimal grossWeight = BigDecimal.ZERO;
	    
	    @Column(name = "Actual_Gross_Weight", precision = 15, scale = 3)
	    private BigDecimal actualGrossWeight = BigDecimal.ZERO;
	    
	    @Column(name = "Actual_Cargo_Weight", precision = 15, scale = 3)
	    private BigDecimal actualCargoWeight = BigDecimal.ZERO;

	    @Column(name = "Weighment_weight",  precision = 15, scale = 3)
	    private BigDecimal weighmentWeight = BigDecimal.ZERO;

	    @Column(name = "Unit_Of_Weight", length = 3)
	    private String unitOfWeight = "";

	    @Column(name = "Type_Of_Package", length = 3)
	    private String typeOfPackage = "";

	    @Column(name = "Cargo_Type", length = 6)
	    private String cargoType = "";

	    @Column(name = "IMO_Code", length = 3)
	    private String imoCode = "";

	    @Column(name = "UN_No", length = 10)
	    private String unNo = "";

	    @Column(name = "Data_Input_Status", length = 1)
	    private String dataInputStatus = "";

	    @Column(name = "Entry_Status", length = 3)
	    private String entryStatus = "";

	    @Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	    private BigDecimal actualNoOfPackages = BigDecimal.ZERO;

	    @Column(name = "Damaged_No_Of_Packages", precision = 8, scale = 0)
	    private BigDecimal damagedNoOfPackages = BigDecimal.ZERO;

	    @Column(name = "Gain_Loss_Package", length = 100)
	    private String gainLossPackage = "0";

	    @Column(name = "Yard_Location", length = 6)
	    private String yardLocation;

	    @Column(name = "Yard_Block", length = 6)
	    private String yardBlock;

	    @Column(name = "Block_Cell_No", length = 10)
	    private String blockCellNo;

	    @Column(name = "No_Of_Destuff_Containers")
	    private int noOfDestuffContainers = 0;

	    @Column(name = "No_Of_Containers")
	    private int noOfContainers;

	    @Column(name = "Exam_Tally_Id", length = 10)
	    private String examTallyId;

	    @Column(name = "Exam_Tally_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date examTallyDate;

	    @Column(name = "BL_Tariff_No", length = 10)
	    private String blTariffNo = "";

	    @Column(name = "Destuff_Id", length = 10)
	    private String destuffId = "";

	    @Column(name = "Destuff_Charges",  precision = 16, scale = 3)
	    private BigDecimal destuffCharges = BigDecimal.ZERO;

	    @Column(name = "destuff_date")
	    @Temporal(TemporalType.DATE)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date destuffDate;

	    @Column(name = "Cargo_Value", precision = 15, scale = 3)
	    private BigDecimal cargoValue = BigDecimal.ZERO;

	    @Column(name = "Cargo_Duty", precision = 15, scale = 3)
	    private BigDecimal cargoDuty = BigDecimal.ZERO;

	    @Column(name = "Gate_Out_No", length = 10)
	    private String gateOutNo;

	    @Column(name = "Gate_Out_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date gateOutDate;

	    @Column(name = "Marks_Of_Numbers", columnDefinition = "TEXT")
	    private String marksOfNumbers;

	    @Column(name = "Holding_Agent", length = 25)
	    private String holdingAgent;

	    @Column(name = "Holding_Agent_Name", length = 35)
	    private String holdingAgentName;

	    @Column(name = "Hold_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date holdDate;

	    @Column(name = "Release_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date releaseDate;

	    @Column(name = "Hold_Remarks", length = 150)
	    private String holdRemarks;

	    @Column(name = "Hold_Status", length = 1)
	    private String holdStatus;

	    @Column(name = "Release_Agent", length = 35)
	    private String releaseAgent;

	    @Column(name = "Release_Remarks", length = 150)
	    private String releaseRemarks;

	    @Column(name = "Notice_Id", length = 10)
	    private String noticeId = "";

	    @Column(name = "Notice_Type", length = 1)
	    private String noticeType = "";

	    @Column(name = "Notice_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date noticeDate;

	    @Column(name = "Auction_Status", length = 1)
	    private String auctionStatus = "N";

	    @Column(name = "Status", length = 1)
	    private String status = "";

	    @Column(name = "Customer_Id", length = 6)
	    private String customerId = "";

	    @Column(name = "BL_Updater_User", length = 250)
	    private String blUpdaterUser = "";

	    @Column(name = "BL_Updater_Flag", length = 1)
	    private String blUpdaterFlag = "N";

	    @Column(name = "BL_Updater_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date blUpdaterDate;

	    @Column(name = "BL_Report_User", length = 50)
	    private String blReportUser = "";

	    @Column(name = "BL_Report_Flag", length = 1)
	    private String blReportFlag = "N";

	    @Column(name = "BL_Report_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date blReportDate;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Column(name = "Created_Date")
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

	    @Column(name = "Haz_Reefer_Remarks", length = 10)
	    private String hazReeferRemarks;

	    @Column(name = "crg_allow_flag", length = 1)
	    private String crgAllowFlag = "N";

	    @Column(name = "oth_party_Id", length = 10)
	    private String othPartyId = "";

	    @Column(name = "merge_status", length = 10)
	    private String mergeStatus = "";

	    @Column(name = "merge_Created_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date mergeCreatedDate;

	    @Column(name = "merge_Created_By", length = 10)
	    private String mergeCreatedBy = "";

	    @Column(name = "merge_Approved_By", length = 45)
	    private String mergeApprovedBy = "";

	    @Column(name = "merge_Approved_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date mergeApprovedDate;

	    @Column(name = "Old_Line_No", length = 7)
	    private String oldLineNo = "";

	    @Column(name = "Risk_Status", length = 1)
	    private String riskStatus = "N";

	    @Column(name = "Risk_Status_By", length = 10)
	    private String riskStatusBy;

	    @Column(name = "Risk_Status_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date riskStatusDate;

	    @Column(name = "SMTP_Flag", length = 10)
	    private String smtpFlag = "N";

	    @Column(name = "SMTP_Status_By", length = 10)
	    private String smtpStatusBy;

	    @Column(name = "SMTP_Status_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date smtpStatusDate;

	    @Column(name = "New_FWD_Id", length = 6)
	    private String newFwdId = "";

	    @Column(name = "Primary_Item", length = 1)
	    private String primaryItem = "Y";

	    @Column(name = "Igm_Send_Status", length = 1)
	    private String igmSendStatus = "N";

	    @Column(name = "Igm_Send_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date igmSendDate;

	    @Column(name = "Part_DeStuff_Id", length = 10)
	    private String partDeStuffId = "";

	    @Column(name = "Part_DeStuff_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date partDeStuffDate;

	    @Column(name = "IGM_Importer_Name", length = 100)
	    private String igmImporterName = "";

	    @Column(name = "IGM_Importer_Address1", length = 250)
	    private String igmImporterAddress1 = "";

	    @Column(name = "IGM_Importer_Address2", length = 250)
	    private String igmImporterAddress2 = "";

	    @Column(name = "IGM_Importer_Address3", length = 250)
	    private String igmImporterAddress3 = "";
	    
	    @Column(name = "Account_holder_id", length = 7)
	    private String accountHolderId = "";

	    @Column(name = "Account_holder_name", length = 100)
	    private String accountHolderName = "";
	    
	    @Column(name="Be_No",length = 30)
	    private String beNo;
	    
	    @Column(name="Be_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date beDate;
	    
	    @Column(name="Cha_Code",length = 30)
	    private String chaCode;
	    
	    @Column(name="Cha_Name",length = 100)
	    private String chaName;
	    
	    @Column(name="Mobile_no",length = 10)
	    private String mobileNo;
	    
	    @Column(name="Seal_cutting_type",length = 10)
	    private String sealCuttingType;
	    
	    @Column(name="Be_Wt",precision = 12,scale = 3)
	    private BigDecimal beWt;
	    
	    @Column(name="Seal_cutting_Remarks",length = 150)
	    private String sealCuttingRemarks;
	    
	    @Column(name="Examination_Remarks",length = 150)
	    private String examinationRemarks;

	    
	    @Column(name="Bl_Type",length = 15)
	    private String blType;

		public Cfigmcrg() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		
	
		
		
		



		public BigDecimal getActualCargoWeight() {
			return actualCargoWeight;
		}











		public void setActualCargoWeight(BigDecimal actualCargoWeight) {
			this.actualCargoWeight = actualCargoWeight;
		}











		public String getImporterSr() {
			return importerSr;
		}











		public void setImporterSr(String importerSr) {
			this.importerSr = importerSr;
		}











		public String getNotifySr() {
			return notifySr;
		}











		public void setNotifySr(String notifySr) {
			this.notifySr = notifySr;
		}











		










		public Cfigmcrg(String companyId, String branchId, String finYear, String igmTransId, String igmCrgTransId,
				String profitcentreId, String igmLineNo, String igmNo, String cycle, String viaNo, String blNo,
				Date blDate, String cargoMovement, int sampleQty, String importerId, String importerName,
				String importerSr, String importerAddress1, String importerAddress2, String importerAddress3,
				String notifyPartyId, String notifySr, String notifyPartyName, String notifiedAddress1,
				String notifiedAddress2, String notifiedAddress3, String oldImporterName, String oldImporterAddress1,
				String oldImporterAddress2, String oldImporterAddress3, String origin, String destination,
				String commodityDescription, String commodityCode, BigDecimal areaUsed, BigDecimal noOfPackages,
				BigDecimal qtyTakenOut, BigDecimal qtyTakenOutWeight, BigDecimal grossWeight,
				BigDecimal actualGrossWeight, BigDecimal actualCargoWeight, BigDecimal weighmentWeight,
				String unitOfWeight, String typeOfPackage, String cargoType, String imoCode, String unNo,
				String dataInputStatus, String entryStatus, BigDecimal actualNoOfPackages,
				BigDecimal damagedNoOfPackages, String gainLossPackage, String yardLocation, String yardBlock,
				String blockCellNo, int noOfDestuffContainers, int noOfContainers, String examTallyId,
				Date examTallyDate, String blTariffNo, String destuffId, BigDecimal destuffCharges, Date destuffDate,
				BigDecimal cargoValue, BigDecimal cargoDuty, String gateOutNo, Date gateOutDate, String marksOfNumbers,
				String holdingAgent, String holdingAgentName, Date holdDate, Date releaseDate, String holdRemarks,
				String holdStatus, String releaseAgent, String releaseRemarks, String noticeId, String noticeType,
				Date noticeDate, String auctionStatus, String status, String customerId, String blUpdaterUser,
				String blUpdaterFlag, Date blUpdaterDate, String blReportUser, String blReportFlag, Date blReportDate,
				String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String hazReeferRemarks, String crgAllowFlag, String othPartyId, String mergeStatus,
				Date mergeCreatedDate, String mergeCreatedBy, String mergeApprovedBy, Date mergeApprovedDate,
				String oldLineNo, String riskStatus, String riskStatusBy, Date riskStatusDate, String smtpFlag,
				String smtpStatusBy, Date smtpStatusDate, String newFwdId, String primaryItem, String igmSendStatus,
				Date igmSendDate, String partDeStuffId, Date partDeStuffDate, String igmImporterName,
				String igmImporterAddress1, String igmImporterAddress2, String igmImporterAddress3,
				String accountHolderId, String accountHolderName, String beNo, Date beDate, String chaCode,
				String chaName, String mobileNo, String sealCuttingType, BigDecimal beWt, String sealCuttingRemarks,
				String examinationRemarks, String blType) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.cargoMovement = cargoMovement;
			this.sampleQty = sampleQty;
			this.importerId = importerId;
			this.importerName = importerName;
			this.importerSr = importerSr;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.notifyPartyId = notifyPartyId;
			this.notifySr = notifySr;
			this.notifyPartyName = notifyPartyName;
			this.notifiedAddress1 = notifiedAddress1;
			this.notifiedAddress2 = notifiedAddress2;
			this.notifiedAddress3 = notifiedAddress3;
			this.oldImporterName = oldImporterName;
			this.oldImporterAddress1 = oldImporterAddress1;
			this.oldImporterAddress2 = oldImporterAddress2;
			this.oldImporterAddress3 = oldImporterAddress3;
			this.origin = origin;
			this.destination = destination;
			this.commodityDescription = commodityDescription;
			this.commodityCode = commodityCode;
			this.areaUsed = areaUsed;
			this.noOfPackages = noOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.qtyTakenOutWeight = qtyTakenOutWeight;
			this.grossWeight = grossWeight;
			this.actualGrossWeight = actualGrossWeight;
			this.actualCargoWeight = actualCargoWeight;
			this.weighmentWeight = weighmentWeight;
			this.unitOfWeight = unitOfWeight;
			this.typeOfPackage = typeOfPackage;
			this.cargoType = cargoType;
			this.imoCode = imoCode;
			this.unNo = unNo;
			this.dataInputStatus = dataInputStatus;
			this.entryStatus = entryStatus;
			this.actualNoOfPackages = actualNoOfPackages;
			this.damagedNoOfPackages = damagedNoOfPackages;
			this.gainLossPackage = gainLossPackage;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.noOfDestuffContainers = noOfDestuffContainers;
			this.noOfContainers = noOfContainers;
			this.examTallyId = examTallyId;
			this.examTallyDate = examTallyDate;
			this.blTariffNo = blTariffNo;
			this.destuffId = destuffId;
			this.destuffCharges = destuffCharges;
			this.destuffDate = destuffDate;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.gateOutNo = gateOutNo;
			this.gateOutDate = gateOutDate;
			this.marksOfNumbers = marksOfNumbers;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.holdStatus = holdStatus;
			this.releaseAgent = releaseAgent;
			this.releaseRemarks = releaseRemarks;
			this.noticeId = noticeId;
			this.noticeType = noticeType;
			this.noticeDate = noticeDate;
			this.auctionStatus = auctionStatus;
			this.status = status;
			this.customerId = customerId;
			this.blUpdaterUser = blUpdaterUser;
			this.blUpdaterFlag = blUpdaterFlag;
			this.blUpdaterDate = blUpdaterDate;
			this.blReportUser = blReportUser;
			this.blReportFlag = blReportFlag;
			this.blReportDate = blReportDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.hazReeferRemarks = hazReeferRemarks;
			this.crgAllowFlag = crgAllowFlag;
			this.othPartyId = othPartyId;
			this.mergeStatus = mergeStatus;
			this.mergeCreatedDate = mergeCreatedDate;
			this.mergeCreatedBy = mergeCreatedBy;
			this.mergeApprovedBy = mergeApprovedBy;
			this.mergeApprovedDate = mergeApprovedDate;
			this.oldLineNo = oldLineNo;
			this.riskStatus = riskStatus;
			this.riskStatusBy = riskStatusBy;
			this.riskStatusDate = riskStatusDate;
			this.smtpFlag = smtpFlag;
			this.smtpStatusBy = smtpStatusBy;
			this.smtpStatusDate = smtpStatusDate;
			this.newFwdId = newFwdId;
			this.primaryItem = primaryItem;
			this.igmSendStatus = igmSendStatus;
			this.igmSendDate = igmSendDate;
			this.partDeStuffId = partDeStuffId;
			this.partDeStuffDate = partDeStuffDate;
			this.igmImporterName = igmImporterName;
			this.igmImporterAddress1 = igmImporterAddress1;
			this.igmImporterAddress2 = igmImporterAddress2;
			this.igmImporterAddress3 = igmImporterAddress3;
			this.accountHolderId = accountHolderId;
			this.accountHolderName = accountHolderName;
			this.beNo = beNo;
			this.beDate = beDate;
			this.chaCode = chaCode;
			this.chaName = chaName;
			this.mobileNo = mobileNo;
			this.sealCuttingType = sealCuttingType;
			this.beWt = beWt;
			this.sealCuttingRemarks = sealCuttingRemarks;
			this.examinationRemarks = examinationRemarks;
			this.blType = blType;
		}











		public BigDecimal getActualGrossWeight() {
			return actualGrossWeight;
		}











		public void setActualGrossWeight(BigDecimal actualGrossWeight) {
			this.actualGrossWeight = actualGrossWeight;
		}











		public Cfigmcrg(String companyId, String branchId, String finYear, String igmTransId, String igmCrgTransId,
				String profitcentreId, String igmLineNo, String igmNo, String cycle, String viaNo, String blNo,
				Date blDate, String cargoMovement, int sampleQty, String importerId, String importerName,
				String importerSr, String importerAddress1, String importerAddress2, String importerAddress3,
				String notifyPartyId, String notifySr, String notifyPartyName, String notifiedAddress1,
				String notifiedAddress2, String notifiedAddress3, String oldImporterName, String oldImporterAddress1,
				String oldImporterAddress2, String oldImporterAddress3, String origin, String destination,
				String commodityDescription, String commodityCode, BigDecimal areaUsed, BigDecimal noOfPackages,
				BigDecimal qtyTakenOut, BigDecimal qtyTakenOutWeight, BigDecimal grossWeight,
				BigDecimal weighmentWeight, String unitOfWeight, String typeOfPackage, String cargoType, String imoCode,
				String unNo, String dataInputStatus, String entryStatus, BigDecimal actualNoOfPackages,
				BigDecimal damagedNoOfPackages, String gainLossPackage, String yardLocation, String yardBlock,
				String blockCellNo, int noOfDestuffContainers, int noOfContainers, String examTallyId,
				Date examTallyDate, String blTariffNo, String destuffId, BigDecimal destuffCharges, Date destuffDate,
				BigDecimal cargoValue, BigDecimal cargoDuty, String gateOutNo, Date gateOutDate, String marksOfNumbers,
				String holdingAgent, String holdingAgentName, Date holdDate, Date releaseDate, String holdRemarks,
				String holdStatus, String releaseAgent, String releaseRemarks, String noticeId, String noticeType,
				Date noticeDate, String auctionStatus, String status, String customerId, String blUpdaterUser,
				String blUpdaterFlag, Date blUpdaterDate, String blReportUser, String blReportFlag, Date blReportDate,
				String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String hazReeferRemarks, String crgAllowFlag, String othPartyId, String mergeStatus,
				Date mergeCreatedDate, String mergeCreatedBy, String mergeApprovedBy, Date mergeApprovedDate,
				String oldLineNo, String riskStatus, String riskStatusBy, Date riskStatusDate, String smtpFlag,
				String smtpStatusBy, Date smtpStatusDate, String newFwdId, String primaryItem, String igmSendStatus,
				Date igmSendDate, String partDeStuffId, Date partDeStuffDate, String igmImporterName,
				String igmImporterAddress1, String igmImporterAddress2, String igmImporterAddress3,
				String accountHolderId, String accountHolderName, String beNo, Date beDate, String chaCode,
				String chaName, String mobileNo, String sealCuttingType, BigDecimal beWt, String sealCuttingRemarks,
				String examinationRemarks, String blType) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.cargoMovement = cargoMovement;
			this.sampleQty = sampleQty;
			this.importerId = importerId;
			this.importerName = importerName;
			this.importerSr = importerSr;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.notifyPartyId = notifyPartyId;
			this.notifySr = notifySr;
			this.notifyPartyName = notifyPartyName;
			this.notifiedAddress1 = notifiedAddress1;
			this.notifiedAddress2 = notifiedAddress2;
			this.notifiedAddress3 = notifiedAddress3;
			this.oldImporterName = oldImporterName;
			this.oldImporterAddress1 = oldImporterAddress1;
			this.oldImporterAddress2 = oldImporterAddress2;
			this.oldImporterAddress3 = oldImporterAddress3;
			this.origin = origin;
			this.destination = destination;
			this.commodityDescription = commodityDescription;
			this.commodityCode = commodityCode;
			this.areaUsed = areaUsed;
			this.noOfPackages = noOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.qtyTakenOutWeight = qtyTakenOutWeight;
			this.grossWeight = grossWeight;
			this.weighmentWeight = weighmentWeight;
			this.unitOfWeight = unitOfWeight;
			this.typeOfPackage = typeOfPackage;
			this.cargoType = cargoType;
			this.imoCode = imoCode;
			this.unNo = unNo;
			this.dataInputStatus = dataInputStatus;
			this.entryStatus = entryStatus;
			this.actualNoOfPackages = actualNoOfPackages;
			this.damagedNoOfPackages = damagedNoOfPackages;
			this.gainLossPackage = gainLossPackage;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.noOfDestuffContainers = noOfDestuffContainers;
			this.noOfContainers = noOfContainers;
			this.examTallyId = examTallyId;
			this.examTallyDate = examTallyDate;
			this.blTariffNo = blTariffNo;
			this.destuffId = destuffId;
			this.destuffCharges = destuffCharges;
			this.destuffDate = destuffDate;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.gateOutNo = gateOutNo;
			this.gateOutDate = gateOutDate;
			this.marksOfNumbers = marksOfNumbers;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.holdStatus = holdStatus;
			this.releaseAgent = releaseAgent;
			this.releaseRemarks = releaseRemarks;
			this.noticeId = noticeId;
			this.noticeType = noticeType;
			this.noticeDate = noticeDate;
			this.auctionStatus = auctionStatus;
			this.status = status;
			this.customerId = customerId;
			this.blUpdaterUser = blUpdaterUser;
			this.blUpdaterFlag = blUpdaterFlag;
			this.blUpdaterDate = blUpdaterDate;
			this.blReportUser = blReportUser;
			this.blReportFlag = blReportFlag;
			this.blReportDate = blReportDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.hazReeferRemarks = hazReeferRemarks;
			this.crgAllowFlag = crgAllowFlag;
			this.othPartyId = othPartyId;
			this.mergeStatus = mergeStatus;
			this.mergeCreatedDate = mergeCreatedDate;
			this.mergeCreatedBy = mergeCreatedBy;
			this.mergeApprovedBy = mergeApprovedBy;
			this.mergeApprovedDate = mergeApprovedDate;
			this.oldLineNo = oldLineNo;
			this.riskStatus = riskStatus;
			this.riskStatusBy = riskStatusBy;
			this.riskStatusDate = riskStatusDate;
			this.smtpFlag = smtpFlag;
			this.smtpStatusBy = smtpStatusBy;
			this.smtpStatusDate = smtpStatusDate;
			this.newFwdId = newFwdId;
			this.primaryItem = primaryItem;
			this.igmSendStatus = igmSendStatus;
			this.igmSendDate = igmSendDate;
			this.partDeStuffId = partDeStuffId;
			this.partDeStuffDate = partDeStuffDate;
			this.igmImporterName = igmImporterName;
			this.igmImporterAddress1 = igmImporterAddress1;
			this.igmImporterAddress2 = igmImporterAddress2;
			this.igmImporterAddress3 = igmImporterAddress3;
			this.accountHolderId = accountHolderId;
			this.accountHolderName = accountHolderName;
			this.beNo = beNo;
			this.beDate = beDate;
			this.chaCode = chaCode;
			this.chaName = chaName;
			this.mobileNo = mobileNo;
			this.sealCuttingType = sealCuttingType;
			this.beWt = beWt;
			this.sealCuttingRemarks = sealCuttingRemarks;
			this.examinationRemarks = examinationRemarks;
			this.blType = blType;
		}











		public String getExaminationRemarks() {
			return examinationRemarks;
		}











		public void setExaminationRemarks(String examinationRemarks) {
			this.examinationRemarks = examinationRemarks;
		}











		public BigDecimal getBeWt() {
			return beWt;
		}











		public void setBeWt(BigDecimal beWt) {
			this.beWt = beWt;
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











		public String getChaCode() {
			return chaCode;
		}











		public void setChaCode(String chaCode) {
			this.chaCode = chaCode;
		}











		public String getChaName() {
			return chaName;
		}











		public void setChaName(String chaName) {
			this.chaName = chaName;
		}











		public String getMobileNo() {
			return mobileNo;
		}











		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}











		public String getSealCuttingType() {
			return sealCuttingType;
		}











		public void setSealCuttingType(String sealCuttingType) {
			this.sealCuttingType = sealCuttingType;
		}











		public String getSealCuttingRemarks() {
			return sealCuttingRemarks;
		}











		public void setSealCuttingRemarks(String sealCuttingRemarks) {
			this.sealCuttingRemarks = sealCuttingRemarks;
		}











		public String getBlType() {
			return blType;
		}











		public void setBlType(String blType) {
			this.blType = blType;
		}











		










		public String getAccountHolderId() {
			return accountHolderId;
		}











	








		public Cfigmcrg(String companyId, String branchId, String finYear, String igmTransId, String igmCrgTransId,
				String profitcentreId, String igmLineNo, String igmNo, String cycle, String viaNo, String blNo,
				Date blDate, String cargoMovement, int sampleQty, String importerId, String importerName,
				String importerAddress1, String importerAddress2, String importerAddress3, String notifyPartyId,
				String notifyPartyName, String notifiedAddress1, String notifiedAddress2, String notifiedAddress3,
				String oldImporterName, String oldImporterAddress1, String oldImporterAddress2,
				String oldImporterAddress3, String origin, String destination, String commodityDescription,
				String commodityCode, BigDecimal areaUsed, BigDecimal noOfPackages, BigDecimal qtyTakenOut,
				BigDecimal qtyTakenOutWeight, BigDecimal grossWeight, BigDecimal weighmentWeight, String unitOfWeight,
				String typeOfPackage, String cargoType, String imoCode, String unNo, String dataInputStatus,
				String entryStatus, BigDecimal actualNoOfPackages, BigDecimal damagedNoOfPackages,
				String gainLossPackage, String yardLocation, String yardBlock, String blockCellNo,
				int noOfDestuffContainers, int noOfContainers, String examTallyId, Date examTallyDate,
				String blTariffNo, String destuffId, BigDecimal destuffCharges, Date destuffDate, BigDecimal cargoValue,
				BigDecimal cargoDuty, String gateOutNo, Date gateOutDate, String marksOfNumbers, String holdingAgent,
				String holdingAgentName, Date holdDate, Date releaseDate, String holdRemarks, String holdStatus,
				String releaseAgent, String releaseRemarks, String noticeId, String noticeType, Date noticeDate,
				String auctionStatus, String status, String customerId, String blUpdaterUser, String blUpdaterFlag,
				Date blUpdaterDate, String blReportUser, String blReportFlag, Date blReportDate, String createdBy,
				Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
				String hazReeferRemarks, String crgAllowFlag, String othPartyId, String mergeStatus,
				Date mergeCreatedDate, String mergeCreatedBy, String mergeApprovedBy, Date mergeApprovedDate,
				String oldLineNo, String riskStatus, String riskStatusBy, Date riskStatusDate, String smtpFlag,
				String smtpStatusBy, Date smtpStatusDate, String newFwdId, String primaryItem, String igmSendStatus,
				Date igmSendDate, String partDeStuffId, Date partDeStuffDate, String igmImporterName,
				String igmImporterAddress1, String igmImporterAddress2, String igmImporterAddress3,
				String accountHolderId, String accountHolderName, String beNo, Date beDate, String chaCode,
				String chaName, String mobileNo, String sealCuttingType, BigDecimal beWt, String sealCuttingRemarks,
				String examinationRemarks, String blType) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.cargoMovement = cargoMovement;
			this.sampleQty = sampleQty;
			this.importerId = importerId;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.notifyPartyId = notifyPartyId;
			this.notifyPartyName = notifyPartyName;
			this.notifiedAddress1 = notifiedAddress1;
			this.notifiedAddress2 = notifiedAddress2;
			this.notifiedAddress3 = notifiedAddress3;
			this.oldImporterName = oldImporterName;
			this.oldImporterAddress1 = oldImporterAddress1;
			this.oldImporterAddress2 = oldImporterAddress2;
			this.oldImporterAddress3 = oldImporterAddress3;
			this.origin = origin;
			this.destination = destination;
			this.commodityDescription = commodityDescription;
			this.commodityCode = commodityCode;
			this.areaUsed = areaUsed;
			this.noOfPackages = noOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.qtyTakenOutWeight = qtyTakenOutWeight;
			this.grossWeight = grossWeight;
			this.weighmentWeight = weighmentWeight;
			this.unitOfWeight = unitOfWeight;
			this.typeOfPackage = typeOfPackage;
			this.cargoType = cargoType;
			this.imoCode = imoCode;
			this.unNo = unNo;
			this.dataInputStatus = dataInputStatus;
			this.entryStatus = entryStatus;
			this.actualNoOfPackages = actualNoOfPackages;
			this.damagedNoOfPackages = damagedNoOfPackages;
			this.gainLossPackage = gainLossPackage;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.noOfDestuffContainers = noOfDestuffContainers;
			this.noOfContainers = noOfContainers;
			this.examTallyId = examTallyId;
			this.examTallyDate = examTallyDate;
			this.blTariffNo = blTariffNo;
			this.destuffId = destuffId;
			this.destuffCharges = destuffCharges;
			this.destuffDate = destuffDate;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.gateOutNo = gateOutNo;
			this.gateOutDate = gateOutDate;
			this.marksOfNumbers = marksOfNumbers;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.holdStatus = holdStatus;
			this.releaseAgent = releaseAgent;
			this.releaseRemarks = releaseRemarks;
			this.noticeId = noticeId;
			this.noticeType = noticeType;
			this.noticeDate = noticeDate;
			this.auctionStatus = auctionStatus;
			this.status = status;
			this.customerId = customerId;
			this.blUpdaterUser = blUpdaterUser;
			this.blUpdaterFlag = blUpdaterFlag;
			this.blUpdaterDate = blUpdaterDate;
			this.blReportUser = blReportUser;
			this.blReportFlag = blReportFlag;
			this.blReportDate = blReportDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.hazReeferRemarks = hazReeferRemarks;
			this.crgAllowFlag = crgAllowFlag;
			this.othPartyId = othPartyId;
			this.mergeStatus = mergeStatus;
			this.mergeCreatedDate = mergeCreatedDate;
			this.mergeCreatedBy = mergeCreatedBy;
			this.mergeApprovedBy = mergeApprovedBy;
			this.mergeApprovedDate = mergeApprovedDate;
			this.oldLineNo = oldLineNo;
			this.riskStatus = riskStatus;
			this.riskStatusBy = riskStatusBy;
			this.riskStatusDate = riskStatusDate;
			this.smtpFlag = smtpFlag;
			this.smtpStatusBy = smtpStatusBy;
			this.smtpStatusDate = smtpStatusDate;
			this.newFwdId = newFwdId;
			this.primaryItem = primaryItem;
			this.igmSendStatus = igmSendStatus;
			this.igmSendDate = igmSendDate;
			this.partDeStuffId = partDeStuffId;
			this.partDeStuffDate = partDeStuffDate;
			this.igmImporterName = igmImporterName;
			this.igmImporterAddress1 = igmImporterAddress1;
			this.igmImporterAddress2 = igmImporterAddress2;
			this.igmImporterAddress3 = igmImporterAddress3;
			this.accountHolderId = accountHolderId;
			this.accountHolderName = accountHolderName;
			this.beNo = beNo;
			this.beDate = beDate;
			this.chaCode = chaCode;
			this.chaName = chaName;
			this.mobileNo = mobileNo;
			this.sealCuttingType = sealCuttingType;
			this.beWt = beWt;
			this.sealCuttingRemarks = sealCuttingRemarks;
			this.examinationRemarks = examinationRemarks;
			this.blType = blType;
		}











		public void setAccountHolderId(String accountHolderId) {
			this.accountHolderId = accountHolderId;
		}











		public String getAccountHolderName() {
			return accountHolderName;
		}











		public void setAccountHolderName(String accountHolderName) {
			this.accountHolderName = accountHolderName;
		}











		public Cfigmcrg(String companyId, String branchId, String finYear, String igmTransId, String igmCrgTransId,
				String profitcentreId, String igmLineNo, String igmNo, String cycle, String viaNo, String blNo,
				Date blDate, String cargoMovement, int sampleQty, String importerId, String importerName,
				String importerAddress1, String importerAddress2, String importerAddress3, String notifyPartyId,
				String notifyPartyName, String notifiedAddress1, String notifiedAddress2, String notifiedAddress3,
				String oldImporterName, String oldImporterAddress1, String oldImporterAddress2,
				String oldImporterAddress3, String origin, String destination, String commodityDescription,
				String commodityCode, BigDecimal areaUsed, BigDecimal noOfPackages, BigDecimal qtyTakenOut,
				BigDecimal qtyTakenOutWeight, BigDecimal grossWeight, BigDecimal weighmentWeight, String unitOfWeight,
				String typeOfPackage, String cargoType, String imoCode, String unNo, String dataInputStatus,
				String entryStatus, BigDecimal actualNoOfPackages, BigDecimal damagedNoOfPackages,
				String gainLossPackage, String yardLocation, String yardBlock, String blockCellNo,
				int noOfDestuffContainers, int noOfContainers, String examTallyId, Date examTallyDate,
				String blTariffNo, String destuffId, BigDecimal destuffCharges, Date destuffDate, BigDecimal cargoValue,
				BigDecimal cargoDuty, String gateOutNo, Date gateOutDate, String marksOfNumbers, String holdingAgent,
				String holdingAgentName, Date holdDate, Date releaseDate, String holdRemarks, String holdStatus,
				String releaseAgent, String releaseRemarks, String noticeId, String noticeType, Date noticeDate,
				String auctionStatus, String status, String customerId, String blUpdaterUser, String blUpdaterFlag,
				Date blUpdaterDate, String blReportUser, String blReportFlag, Date blReportDate, String createdBy,
				Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
				String hazReeferRemarks, String crgAllowFlag, String othPartyId, String mergeStatus,
				Date mergeCreatedDate, String mergeCreatedBy, String mergeApprovedBy, Date mergeApprovedDate,
				String oldLineNo, String riskStatus, String riskStatusBy, Date riskStatusDate, String smtpFlag,
				String smtpStatusBy, Date smtpStatusDate, String newFwdId, String primaryItem, String igmSendStatus,
				Date igmSendDate, String partDeStuffId, Date partDeStuffDate, String igmImporterName,
				String igmImporterAddress1, String igmImporterAddress2, String igmImporterAddress3,
				String accountHolderId, String accountHolderName) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.cargoMovement = cargoMovement;
			this.sampleQty = sampleQty;
			this.importerId = importerId;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.notifyPartyId = notifyPartyId;
			this.notifyPartyName = notifyPartyName;
			this.notifiedAddress1 = notifiedAddress1;
			this.notifiedAddress2 = notifiedAddress2;
			this.notifiedAddress3 = notifiedAddress3;
			this.oldImporterName = oldImporterName;
			this.oldImporterAddress1 = oldImporterAddress1;
			this.oldImporterAddress2 = oldImporterAddress2;
			this.oldImporterAddress3 = oldImporterAddress3;
			this.origin = origin;
			this.destination = destination;
			this.commodityDescription = commodityDescription;
			this.commodityCode = commodityCode;
			this.areaUsed = areaUsed;
			this.noOfPackages = noOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.qtyTakenOutWeight = qtyTakenOutWeight;
			this.grossWeight = grossWeight;
			this.weighmentWeight = weighmentWeight;
			this.unitOfWeight = unitOfWeight;
			this.typeOfPackage = typeOfPackage;
			this.cargoType = cargoType;
			this.imoCode = imoCode;
			this.unNo = unNo;
			this.dataInputStatus = dataInputStatus;
			this.entryStatus = entryStatus;
			this.actualNoOfPackages = actualNoOfPackages;
			this.damagedNoOfPackages = damagedNoOfPackages;
			this.gainLossPackage = gainLossPackage;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.noOfDestuffContainers = noOfDestuffContainers;
			this.noOfContainers = noOfContainers;
			this.examTallyId = examTallyId;
			this.examTallyDate = examTallyDate;
			this.blTariffNo = blTariffNo;
			this.destuffId = destuffId;
			this.destuffCharges = destuffCharges;
			this.destuffDate = destuffDate;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.gateOutNo = gateOutNo;
			this.gateOutDate = gateOutDate;
			this.marksOfNumbers = marksOfNumbers;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.holdStatus = holdStatus;
			this.releaseAgent = releaseAgent;
			this.releaseRemarks = releaseRemarks;
			this.noticeId = noticeId;
			this.noticeType = noticeType;
			this.noticeDate = noticeDate;
			this.auctionStatus = auctionStatus;
			this.status = status;
			this.customerId = customerId;
			this.blUpdaterUser = blUpdaterUser;
			this.blUpdaterFlag = blUpdaterFlag;
			this.blUpdaterDate = blUpdaterDate;
			this.blReportUser = blReportUser;
			this.blReportFlag = blReportFlag;
			this.blReportDate = blReportDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.hazReeferRemarks = hazReeferRemarks;
			this.crgAllowFlag = crgAllowFlag;
			this.othPartyId = othPartyId;
			this.mergeStatus = mergeStatus;
			this.mergeCreatedDate = mergeCreatedDate;
			this.mergeCreatedBy = mergeCreatedBy;
			this.mergeApprovedBy = mergeApprovedBy;
			this.mergeApprovedDate = mergeApprovedDate;
			this.oldLineNo = oldLineNo;
			this.riskStatus = riskStatus;
			this.riskStatusBy = riskStatusBy;
			this.riskStatusDate = riskStatusDate;
			this.smtpFlag = smtpFlag;
			this.smtpStatusBy = smtpStatusBy;
			this.smtpStatusDate = smtpStatusDate;
			this.newFwdId = newFwdId;
			this.primaryItem = primaryItem;
			this.igmSendStatus = igmSendStatus;
			this.igmSendDate = igmSendDate;
			this.partDeStuffId = partDeStuffId;
			this.partDeStuffDate = partDeStuffDate;
			this.igmImporterName = igmImporterName;
			this.igmImporterAddress1 = igmImporterAddress1;
			this.igmImporterAddress2 = igmImporterAddress2;
			this.igmImporterAddress3 = igmImporterAddress3;
			this.accountHolderId = accountHolderId;
			this.accountHolderName = accountHolderName;
		}











		public String getIgmCrgTransId() {
			return igmCrgTransId;
		}










		public void setIgmCrgTransId(String igmCrgTransId) {
			this.igmCrgTransId = igmCrgTransId;
		}










		public Cfigmcrg(String companyId, String branchId, String finYear, String igmTransId, String igmCrgTransId,
				String profitcentreId, String igmLineNo, String igmNo, String cycle, String viaNo, String blNo,
				Date blDate, String cargoMovement, int sampleQty, String importerId, String importerName,
				String importerAddress1, String importerAddress2, String importerAddress3, String notifyPartyId,
				String notifyPartyName, String notifiedAddress1, String notifiedAddress2, String notifiedAddress3,
				String oldImporterName, String oldImporterAddress1, String oldImporterAddress2,
				String oldImporterAddress3, String origin, String destination, String commodityDescription,
				String commodityCode, BigDecimal areaUsed, BigDecimal noOfPackages, BigDecimal qtyTakenOut,
				BigDecimal qtyTakenOutWeight, BigDecimal grossWeight, BigDecimal weighmentWeight, String unitOfWeight,
				String typeOfPackage, String cargoType, String imoCode, String unNo, String dataInputStatus,
				String entryStatus, BigDecimal actualNoOfPackages, BigDecimal damagedNoOfPackages,
				String gainLossPackage, String yardLocation, String yardBlock, String blockCellNo,
				int noOfDestuffContainers, int noOfContainers, String examTallyId, Date examTallyDate,
				String blTariffNo, String destuffId, BigDecimal destuffCharges, Date destuffDate, BigDecimal cargoValue,
				BigDecimal cargoDuty, String gateOutNo, Date gateOutDate, String marksOfNumbers, String holdingAgent,
				String holdingAgentName, Date holdDate, Date releaseDate, String holdRemarks, String holdStatus,
				String releaseAgent, String releaseRemarks, String noticeId, String noticeType, Date noticeDate,
				String auctionStatus, String status, String customerId, String blUpdaterUser, String blUpdaterFlag,
				Date blUpdaterDate, String blReportUser, String blReportFlag, Date blReportDate, String createdBy,
				Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
				String hazReeferRemarks, String crgAllowFlag, String othPartyId, String mergeStatus,
				Date mergeCreatedDate, String mergeCreatedBy, String mergeApprovedBy, Date mergeApprovedDate,
				String oldLineNo, String riskStatus, String riskStatusBy, Date riskStatusDate, String smtpFlag,
				String smtpStatusBy, Date smtpStatusDate, String newFwdId, String primaryItem, String igmSendStatus,
				Date igmSendDate, String partDeStuffId, Date partDeStuffDate, String igmImporterName,
				String igmImporterAddress1, String igmImporterAddress2, String igmImporterAddress3) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.cargoMovement = cargoMovement;
			this.sampleQty = sampleQty;
			this.importerId = importerId;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.notifyPartyId = notifyPartyId;
			this.notifyPartyName = notifyPartyName;
			this.notifiedAddress1 = notifiedAddress1;
			this.notifiedAddress2 = notifiedAddress2;
			this.notifiedAddress3 = notifiedAddress3;
			this.oldImporterName = oldImporterName;
			this.oldImporterAddress1 = oldImporterAddress1;
			this.oldImporterAddress2 = oldImporterAddress2;
			this.oldImporterAddress3 = oldImporterAddress3;
			this.origin = origin;
			this.destination = destination;
			this.commodityDescription = commodityDescription;
			this.commodityCode = commodityCode;
			this.areaUsed = areaUsed;
			this.noOfPackages = noOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.qtyTakenOutWeight = qtyTakenOutWeight;
			this.grossWeight = grossWeight;
			this.weighmentWeight = weighmentWeight;
			this.unitOfWeight = unitOfWeight;
			this.typeOfPackage = typeOfPackage;
			this.cargoType = cargoType;
			this.imoCode = imoCode;
			this.unNo = unNo;
			this.dataInputStatus = dataInputStatus;
			this.entryStatus = entryStatus;
			this.actualNoOfPackages = actualNoOfPackages;
			this.damagedNoOfPackages = damagedNoOfPackages;
			this.gainLossPackage = gainLossPackage;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.noOfDestuffContainers = noOfDestuffContainers;
			this.noOfContainers = noOfContainers;
			this.examTallyId = examTallyId;
			this.examTallyDate = examTallyDate;
			this.blTariffNo = blTariffNo;
			this.destuffId = destuffId;
			this.destuffCharges = destuffCharges;
			this.destuffDate = destuffDate;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.gateOutNo = gateOutNo;
			this.gateOutDate = gateOutDate;
			this.marksOfNumbers = marksOfNumbers;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.holdStatus = holdStatus;
			this.releaseAgent = releaseAgent;
			this.releaseRemarks = releaseRemarks;
			this.noticeId = noticeId;
			this.noticeType = noticeType;
			this.noticeDate = noticeDate;
			this.auctionStatus = auctionStatus;
			this.status = status;
			this.customerId = customerId;
			this.blUpdaterUser = blUpdaterUser;
			this.blUpdaterFlag = blUpdaterFlag;
			this.blUpdaterDate = blUpdaterDate;
			this.blReportUser = blReportUser;
			this.blReportFlag = blReportFlag;
			this.blReportDate = blReportDate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.hazReeferRemarks = hazReeferRemarks;
			this.crgAllowFlag = crgAllowFlag;
			this.othPartyId = othPartyId;
			this.mergeStatus = mergeStatus;
			this.mergeCreatedDate = mergeCreatedDate;
			this.mergeCreatedBy = mergeCreatedBy;
			this.mergeApprovedBy = mergeApprovedBy;
			this.mergeApprovedDate = mergeApprovedDate;
			this.oldLineNo = oldLineNo;
			this.riskStatus = riskStatus;
			this.riskStatusBy = riskStatusBy;
			this.riskStatusDate = riskStatusDate;
			this.smtpFlag = smtpFlag;
			this.smtpStatusBy = smtpStatusBy;
			this.smtpStatusDate = smtpStatusDate;
			this.newFwdId = newFwdId;
			this.primaryItem = primaryItem;
			this.igmSendStatus = igmSendStatus;
			this.igmSendDate = igmSendDate;
			this.partDeStuffId = partDeStuffId;
			this.partDeStuffDate = partDeStuffDate;
			this.igmImporterName = igmImporterName;
			this.igmImporterAddress1 = igmImporterAddress1;
			this.igmImporterAddress2 = igmImporterAddress2;
			this.igmImporterAddress3 = igmImporterAddress3;
		}










		public String getNotifyPartyId() {
			return notifyPartyId;
		}



		public void setNotifyPartyId(String notifyPartyId) {
			this.notifyPartyId = notifyPartyId;
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

		public String getCycle() {
			return cycle;
		}

		public void setCycle(String cycle) {
			this.cycle = cycle;
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

		public String getCargoMovement() {
			return cargoMovement;
		}

		public void setCargoMovement(String cargoMovement) {
			this.cargoMovement = cargoMovement;
		}

		public int getSampleQty() {
			return sampleQty;
		}

		public void setSampleQty(int sampleQty) {
			this.sampleQty = sampleQty;
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

		public String getNotifyPartyName() {
			return notifyPartyName;
		}

		public void setNotifyPartyName(String notifyPartyName) {
			this.notifyPartyName = notifyPartyName;
		}

		public String getNotifiedAddress1() {
			return notifiedAddress1;
		}

		public void setNotifiedAddress1(String notifiedAddress1) {
			this.notifiedAddress1 = notifiedAddress1;
		}

		public String getNotifiedAddress2() {
			return notifiedAddress2;
		}

		public void setNotifiedAddress2(String notifiedAddress2) {
			this.notifiedAddress2 = notifiedAddress2;
		}

		public String getNotifiedAddress3() {
			return notifiedAddress3;
		}

		public void setNotifiedAddress3(String notifiedAddress3) {
			this.notifiedAddress3 = notifiedAddress3;
		}

		public String getOldImporterName() {
			return oldImporterName;
		}

		public void setOldImporterName(String oldImporterName) {
			this.oldImporterName = oldImporterName;
		}

		public String getOldImporterAddress1() {
			return oldImporterAddress1;
		}

		public void setOldImporterAddress1(String oldImporterAddress1) {
			this.oldImporterAddress1 = oldImporterAddress1;
		}

		public String getOldImporterAddress2() {
			return oldImporterAddress2;
		}

		public void setOldImporterAddress2(String oldImporterAddress2) {
			this.oldImporterAddress2 = oldImporterAddress2;
		}

		public String getOldImporterAddress3() {
			return oldImporterAddress3;
		}

		public void setOldImporterAddress3(String oldImporterAddress3) {
			this.oldImporterAddress3 = oldImporterAddress3;
		}

		public String getOrigin() {
			return origin;
		}

		public void setOrigin(String origin) {
			this.origin = origin;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
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

		public BigDecimal getQtyTakenOut() {
			return qtyTakenOut;
		}

		public void setQtyTakenOut(BigDecimal qtyTakenOut) {
			this.qtyTakenOut = qtyTakenOut;
		}

		public BigDecimal getQtyTakenOutWeight() {
			return qtyTakenOutWeight;
		}

		public void setQtyTakenOutWeight(BigDecimal qtyTakenOutWeight) {
			this.qtyTakenOutWeight = qtyTakenOutWeight;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public BigDecimal getWeighmentWeight() {
			return weighmentWeight;
		}

		public void setWeighmentWeight(BigDecimal weighmentWeight) {
			this.weighmentWeight = weighmentWeight;
		}

		public String getUnitOfWeight() {
			return unitOfWeight;
		}

		public void setUnitOfWeight(String unitOfWeight) {
			this.unitOfWeight = unitOfWeight;
		}

		public String getTypeOfPackage() {
			return typeOfPackage;
		}

		public void setTypeOfPackage(String typeOfPackage) {
			this.typeOfPackage = typeOfPackage;
		}

		public String getCargoType() {
			return cargoType;
		}

		public void setCargoType(String cargoType) {
			this.cargoType = cargoType;
		}

		public String getImoCode() {
			return imoCode;
		}

		public void setImoCode(String imoCode) {
			this.imoCode = imoCode;
		}

		public String getUnNo() {
			return unNo;
		}

		public void setUnNo(String unNo) {
			this.unNo = unNo;
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

		public String getGainLossPackage() {
			return gainLossPackage;
		}

		public void setGainLossPackage(String gainLossPackage) {
			this.gainLossPackage = gainLossPackage;
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

		public int getNoOfDestuffContainers() {
			return noOfDestuffContainers;
		}

		public void setNoOfDestuffContainers(int noOfDestuffContainers) {
			this.noOfDestuffContainers = noOfDestuffContainers;
		}

		public int getNoOfContainers() {
			return noOfContainers;
		}

		public void setNoOfContainers(int noOfContainers) {
			this.noOfContainers = noOfContainers;
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

		public String getBlTariffNo() {
			return blTariffNo;
		}

		public void setBlTariffNo(String blTariffNo) {
			this.blTariffNo = blTariffNo;
		}

		public String getDestuffId() {
			return destuffId;
		}

		public void setDestuffId(String destuffId) {
			this.destuffId = destuffId;
		}

		public BigDecimal getDestuffCharges() {
			return destuffCharges;
		}

		public void setDestuffCharges(BigDecimal destuffCharges) {
			this.destuffCharges = destuffCharges;
		}

		public Date getDestuffDate() {
			return destuffDate;
		}

		public void setDestuffDate(Date destuffDate) {
			this.destuffDate = destuffDate;
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

		public String getGateOutNo() {
			return gateOutNo;
		}

		public void setGateOutNo(String gateOutNo) {
			this.gateOutNo = gateOutNo;
		}

		public Date getGateOutDate() {
			return gateOutDate;
		}

		public void setGateOutDate(Date gateOutDate) {
			this.gateOutDate = gateOutDate;
		}

		public String getMarksOfNumbers() {
			return marksOfNumbers;
		}

		public void setMarksOfNumbers(String marksOfNumbers) {
			this.marksOfNumbers = marksOfNumbers;
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

		public Date getNoticeDate() {
			return noticeDate;
		}

		public void setNoticeDate(Date noticeDate) {
			this.noticeDate = noticeDate;
		}

		public String getAuctionStatus() {
			return auctionStatus;
		}

		public void setAuctionStatus(String auctionStatus) {
			this.auctionStatus = auctionStatus;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public String getBlUpdaterUser() {
			return blUpdaterUser;
		}

		public void setBlUpdaterUser(String blUpdaterUser) {
			this.blUpdaterUser = blUpdaterUser;
		}

		public String getBlUpdaterFlag() {
			return blUpdaterFlag;
		}

		public void setBlUpdaterFlag(String blUpdaterFlag) {
			this.blUpdaterFlag = blUpdaterFlag;
		}

		public Date getBlUpdaterDate() {
			return blUpdaterDate;
		}

		public void setBlUpdaterDate(Date blUpdaterDate) {
			this.blUpdaterDate = blUpdaterDate;
		}

		public String getBlReportUser() {
			return blReportUser;
		}

		public void setBlReportUser(String blReportUser) {
			this.blReportUser = blReportUser;
		}

		public String getBlReportFlag() {
			return blReportFlag;
		}

		public void setBlReportFlag(String blReportFlag) {
			this.blReportFlag = blReportFlag;
		}

		public Date getBlReportDate() {
			return blReportDate;
		}

		public void setBlReportDate(Date blReportDate) {
			this.blReportDate = blReportDate;
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

		public String getHazReeferRemarks() {
			return hazReeferRemarks;
		}

		public void setHazReeferRemarks(String hazReeferRemarks) {
			this.hazReeferRemarks = hazReeferRemarks;
		}

		public String getCrgAllowFlag() {
			return crgAllowFlag;
		}

		public void setCrgAllowFlag(String crgAllowFlag) {
			this.crgAllowFlag = crgAllowFlag;
		}

		public String getOthPartyId() {
			return othPartyId;
		}

		public void setOthPartyId(String othPartyId) {
			this.othPartyId = othPartyId;
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

		public String getOldLineNo() {
			return oldLineNo;
		}

		public void setOldLineNo(String oldLineNo) {
			this.oldLineNo = oldLineNo;
		}

		public String getRiskStatus() {
			return riskStatus;
		}

		public void setRiskStatus(String riskStatus) {
			this.riskStatus = riskStatus;
		}

		public String getRiskStatusBy() {
			return riskStatusBy;
		}

		public void setRiskStatusBy(String riskStatusBy) {
			this.riskStatusBy = riskStatusBy;
		}

		public Date getRiskStatusDate() {
			return riskStatusDate;
		}

		public void setRiskStatusDate(Date riskStatusDate) {
			this.riskStatusDate = riskStatusDate;
		}

		public String getSmtpFlag() {
			return smtpFlag;
		}

		public void setSmtpFlag(String smtpFlag) {
			this.smtpFlag = smtpFlag;
		}

		public String getSmtpStatusBy() {
			return smtpStatusBy;
		}

		public void setSmtpStatusBy(String smtpStatusBy) {
			this.smtpStatusBy = smtpStatusBy;
		}

		public Date getSmtpStatusDate() {
			return smtpStatusDate;
		}

		public void setSmtpStatusDate(Date smtpStatusDate) {
			this.smtpStatusDate = smtpStatusDate;
		}

		public String getNewFwdId() {
			return newFwdId;
		}

		public void setNewFwdId(String newFwdId) {
			this.newFwdId = newFwdId;
		}

		public String getPrimaryItem() {
			return primaryItem;
		}

		public void setPrimaryItem(String primaryItem) {
			this.primaryItem = primaryItem;
		}

		public String getIgmSendStatus() {
			return igmSendStatus;
		}

		public void setIgmSendStatus(String igmSendStatus) {
			this.igmSendStatus = igmSendStatus;
		}

		public Date getIgmSendDate() {
			return igmSendDate;
		}

		public void setIgmSendDate(Date igmSendDate) {
			this.igmSendDate = igmSendDate;
		}

		public String getPartDeStuffId() {
			return partDeStuffId;
		}

		public void setPartDeStuffId(String partDeStuffId) {
			this.partDeStuffId = partDeStuffId;
		}

		public Date getPartDeStuffDate() {
			return partDeStuffDate;
		}

		public void setPartDeStuffDate(Date partDeStuffDate) {
			this.partDeStuffDate = partDeStuffDate;
		}

		public String getIgmImporterName() {
			return igmImporterName;
		}

		public void setIgmImporterName(String igmImporterName) {
			this.igmImporterName = igmImporterName;
		}

		public String getIgmImporterAddress1() {
			return igmImporterAddress1;
		}

		public void setIgmImporterAddress1(String igmImporterAddress1) {
			this.igmImporterAddress1 = igmImporterAddress1;
		}

		public String getIgmImporterAddress2() {
			return igmImporterAddress2;
		}

		public void setIgmImporterAddress2(String igmImporterAddress2) {
			this.igmImporterAddress2 = igmImporterAddress2;
		}

		public String getIgmImporterAddress3() {
			return igmImporterAddress3;
		}

		public void setIgmImporterAddress3(String igmImporterAddress3) {
			this.igmImporterAddress3 = igmImporterAddress3;
		}











		public Cfigmcrg(String igmTransId, String igmCrgTransId, String profitcentreId, String igmLineNo, String igmNo,
				String cycle, String viaNo, String blNo, Date blDate, String importerName, String commodityDescription,
				BigDecimal cargoValue, BigDecimal cargoDuty, String beNo, Date beDate, String chaCode, String chaName,
				String mobileNo, String sealCuttingType, String sealCuttingRemarks, String blType, BigDecimal beWt,String notifyPartyName, String createdBy) {
			super();
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.importerName = importerName;
			this.commodityDescription = commodityDescription;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.beNo = beNo;
			this.beDate = beDate;
			this.chaCode = chaCode;
			this.chaName = chaName;
			this.mobileNo = mobileNo;
			this.sealCuttingType = sealCuttingType;
			this.sealCuttingRemarks = sealCuttingRemarks;
			this.blType = blType;
			this.beWt = beWt;
			this.notifyPartyName = notifyPartyName;
			this.createdBy = createdBy;
		}
		
		
		public Cfigmcrg(String igmTransId, String igmCrgTransId, String profitcentreId, String igmLineNo, String igmNo,
				String cycle, String viaNo, String blNo, Date blDate, String importerName, String commodityDescription,
				BigDecimal cargoValue, BigDecimal cargoDuty, String beNo, Date beDate, String chaCode, String chaName,
				String mobileNo, String sealCuttingType, String sealCuttingRemarks, String blType, BigDecimal beWt,String notifyPartyName,
				String createdBy,String examinationRemarks) {
			super();
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.cycle = cycle;
			this.viaNo = viaNo;
			this.blNo = blNo;
			this.blDate = blDate;
			this.importerName = importerName;
			this.commodityDescription = commodityDescription;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.beNo = beNo;
			this.beDate = beDate;
			this.chaCode = chaCode;
			this.chaName = chaName;
			this.mobileNo = mobileNo;
			this.sealCuttingType = sealCuttingType;
			this.sealCuttingRemarks = sealCuttingRemarks;
			this.blType = blType;
			this.beWt = beWt;
			this.notifyPartyName = notifyPartyName;
			this.createdBy = createdBy;
			this.examinationRemarks = examinationRemarks;
		}











		public Cfigmcrg(String igmTransId, String igmCrgTransId, String profitcentreId, String igmLineNo, String igmNo,
				String viaNo, String importerName, String importerAddress1, String importerAddress2,
				String importerAddress3, String commodityDescription, String marksOfNumbers) {
			super();
			this.igmTransId = igmTransId;
			this.igmCrgTransId = igmCrgTransId;
			this.profitcentreId = profitcentreId;
			this.igmLineNo = igmLineNo;
			this.igmNo = igmNo;
			this.viaNo = viaNo;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.commodityDescription = commodityDescription;
			this.marksOfNumbers = marksOfNumbers;
		}











		@Override
		public String toString() {
			return "Cfigmcrg [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
					+ ", igmTransId=" + igmTransId + ", igmCrgTransId=" + igmCrgTransId + ", profitcentreId="
					+ profitcentreId + ", igmLineNo=" + igmLineNo + ", igmNo=" + igmNo + ", cycle=" + cycle + ", viaNo="
					+ viaNo + ", blNo=" + blNo + ", blDate=" + blDate + ", cargoMovement=" + cargoMovement
					+ ", sampleQty=" + sampleQty + ", importerId=" + importerId + ", importerName=" + importerName
					+ ", importerSr=" + importerSr + ", importerAddress1=" + importerAddress1 + ", importerAddress2="
					+ importerAddress2 + ", importerAddress3=" + importerAddress3 + ", notifyPartyId=" + notifyPartyId
					+ ", notifySr=" + notifySr + ", notifyPartyName=" + notifyPartyName + ", notifiedAddress1="
					+ notifiedAddress1 + ", notifiedAddress2=" + notifiedAddress2 + ", notifiedAddress3="
					+ notifiedAddress3 + ", oldImporterName=" + oldImporterName + ", oldImporterAddress1="
					+ oldImporterAddress1 + ", oldImporterAddress2=" + oldImporterAddress2 + ", oldImporterAddress3="
					+ oldImporterAddress3 + ", origin=" + origin + ", destination=" + destination
					+ ", commodityDescription=" + commodityDescription + ", commodityCode=" + commodityCode
					+ ", areaUsed=" + areaUsed + ", noOfPackages=" + noOfPackages + ", qtyTakenOut=" + qtyTakenOut
					+ ", qtyTakenOutWeight=" + qtyTakenOutWeight + ", grossWeight=" + grossWeight
					+ ", actualGrossWeight=" + actualGrossWeight + ", actualCargoWeight=" + actualCargoWeight
					+ ", weighmentWeight=" + weighmentWeight + ", unitOfWeight=" + unitOfWeight + ", typeOfPackage="
					+ typeOfPackage + ", cargoType=" + cargoType + ", imoCode=" + imoCode + ", unNo=" + unNo
					+ ", dataInputStatus=" + dataInputStatus + ", entryStatus=" + entryStatus + ", actualNoOfPackages="
					+ actualNoOfPackages + ", damagedNoOfPackages=" + damagedNoOfPackages + ", gainLossPackage="
					+ gainLossPackage + ", yardLocation=" + yardLocation + ", yardBlock=" + yardBlock + ", blockCellNo="
					+ blockCellNo + ", noOfDestuffContainers=" + noOfDestuffContainers + ", noOfContainers="
					+ noOfContainers + ", examTallyId=" + examTallyId + ", examTallyDate=" + examTallyDate
					+ ", blTariffNo=" + blTariffNo + ", destuffId=" + destuffId + ", destuffCharges=" + destuffCharges
					+ ", destuffDate=" + destuffDate + ", cargoValue=" + cargoValue + ", cargoDuty=" + cargoDuty
					+ ", gateOutNo=" + gateOutNo + ", gateOutDate=" + gateOutDate + ", marksOfNumbers=" + marksOfNumbers
					+ ", holdingAgent=" + holdingAgent + ", holdingAgentName=" + holdingAgentName + ", holdDate="
					+ holdDate + ", releaseDate=" + releaseDate + ", holdRemarks=" + holdRemarks + ", holdStatus="
					+ holdStatus + ", releaseAgent=" + releaseAgent + ", releaseRemarks=" + releaseRemarks
					+ ", noticeId=" + noticeId + ", noticeType=" + noticeType + ", noticeDate=" + noticeDate
					+ ", auctionStatus=" + auctionStatus + ", status=" + status + ", customerId=" + customerId
					+ ", blUpdaterUser=" + blUpdaterUser + ", blUpdaterFlag=" + blUpdaterFlag + ", blUpdaterDate="
					+ blUpdaterDate + ", blReportUser=" + blReportUser + ", blReportFlag=" + blReportFlag
					+ ", blReportDate=" + blReportDate + ", createdBy=" + createdBy + ", createdDate=" + createdDate
					+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
					+ ", approvedDate=" + approvedDate + ", hazReeferRemarks=" + hazReeferRemarks + ", crgAllowFlag="
					+ crgAllowFlag + ", othPartyId=" + othPartyId + ", mergeStatus=" + mergeStatus
					+ ", mergeCreatedDate=" + mergeCreatedDate + ", mergeCreatedBy=" + mergeCreatedBy
					+ ", mergeApprovedBy=" + mergeApprovedBy + ", mergeApprovedDate=" + mergeApprovedDate
					+ ", oldLineNo=" + oldLineNo + ", riskStatus=" + riskStatus + ", riskStatusBy=" + riskStatusBy
					+ ", riskStatusDate=" + riskStatusDate + ", smtpFlag=" + smtpFlag + ", smtpStatusBy=" + smtpStatusBy
					+ ", smtpStatusDate=" + smtpStatusDate + ", newFwdId=" + newFwdId + ", primaryItem=" + primaryItem
					+ ", igmSendStatus=" + igmSendStatus + ", igmSendDate=" + igmSendDate + ", partDeStuffId="
					+ partDeStuffId + ", partDeStuffDate=" + partDeStuffDate + ", igmImporterName=" + igmImporterName
					+ ", igmImporterAddress1=" + igmImporterAddress1 + ", igmImporterAddress2=" + igmImporterAddress2
					+ ", igmImporterAddress3=" + igmImporterAddress3 + ", accountHolderId=" + accountHolderId
					+ ", accountHolderName=" + accountHolderName + ", beNo=" + beNo + ", beDate=" + beDate
					+ ", chaCode=" + chaCode + ", chaName=" + chaName + ", mobileNo=" + mobileNo + ", sealCuttingType="
					+ sealCuttingType + ", beWt=" + beWt + ", sealCuttingRemarks=" + sealCuttingRemarks
					+ ", examinationRemarks=" + examinationRemarks + ", blType=" + blType + "]";
		}











		

		
		
		
		
	    
	    
}
