//package com.cwms.entities;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.IdClass;
//import jakarta.persistence.Table;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
//import jakarta.persistence.Transient;
//
//@Entity
//@Table(name="cfstufftally")
//@IdClass(ExportStuffTallyId.class)
//public class ExportStuffTally implements Cloneable {
//	    @Id
//	    @Column(name = "Company_Id", length = 6)
//	    private String companyId;
//
//	    @Id
//	    @Column(name = "Branch_Id", length = 6)
//	    private String branchId;
//
//	    @Id
//	    @Column(name = "Stuff_Tally_Id", length = 10)
//	    private String stuffTallyId;
//
//	    @Id
//	    @Column(name = "SB_Trans_Id", length = 10)
//	    private String sbTransId;
//
//	    @Id
//	    @Column(name = "Stuff_Tally_Line_Id")
//	    private int stuffTallyLineId;
//
//	    @Id
//	    @Column(name = "Profitcentre_Id", length = 6)
//	    private String profitcentreId;
//
//
//	    @Id
//	    @Column(name = "Carting_Trans_Id", length = 10)
//	    private String cartingTransId;
//
//	    @Id
//	    @Column(name = "SB_Line_Id", length = 5)
//	    private String sbLineId;
//
//	    @Id
//	    @Column(name = "Carting_Line_Id", length = 5)
//	    private String cartingLineId;
//
//	    @Id
//	    @Column(name = "SB_No", length = 15)
//	    private String sbNo;
//	    
//	    @Column(name = "Movement_Req_Id", length = 10)
//	    private String movementReqId;
//
//	    @Column(name = "MOVEMENT_TYPE", length = 10)
//	    private String movementType;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Stuff_Tally_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date stuffTallyDate;
//
//	    @Column(name = "Stuff_Id", length = 10)
//	    private String stuffId;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Stuff_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date stuffDate;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "SB_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date sbDate;
//
//	    @Column(name = "Shift", length = 6)
//	    private String shift;
//
//	    @Column(name = "Agent_Seal_No", length = 15)
//	    private String agentSealNo;
//
//	    @Column(name = "Vessel_Id", length = 7)
//	    private String vesselId;
//
//	    @Column(name = "Voyage_No", length = 10)
//	    private String voyageNo;
//
//	    @Column(name = "Rotation_No", length = 10)
//	    private String rotationNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name="Rotation_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date rotationDate;
//	    
//	    @Column(name = "POL", length = 100)
//	    private String pol;
//
//	    @Column(name = "Terminal", length = 50)
//	    private String terminal;
//
//	    @Column(name = "pod", length = 140)
//	    private String pod;
//
//	    @Column(name = "Final_POD", length = 50)
//	    private String finalPod;
//
//	    @Column(name = "Container_No", length = 11)
//	    private String containerNo;
//
//	    @Column(name = "Container_Status", length = 3)
//	    private String containerStatus;
//
//	    @Column(name = "ASR_Container_Status", length = 3)
//	    private String asrContainerStatus;
//
//	    @Column(name = "Current_Location", length = 26)
//	    private String currentLocation;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Period_From")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date periodFrom;
//	    
//	    @Column(name = "GATE_IN_ID", length = 10)
//	    private String gateInId;
//
//	    @Column(name = "Container_Size", length = 6)
//	    private String containerSize;
//
//	    @Column(name = "Container_Type", length = 6)
//	    private String containerType;
//
//	    @Column(name = "Container_Condition", length = 6)
//	    private String containerCondition;
//
//	    @Column(name = "Crg_Yard_Location", length = 10)
//	    private String crgYardLocation;
//
//	    @Column(name = "Crg_Yard_Block", length = 10)
//	    private String crgYardBlock;
//
//	    @Column(name = "Crg_Block_Cell_No", length = 10)
//	    private String crgBlockCellNo;
//
//	    @Column(name = "Yard_Location", length = 10)
//	    private String yardLocation;
//
//	    @Column(name = "Yard_Block", length = 10)
//	    private String yardBlock;
//
//	    @Column(name = "Block_Cell_No", length = 10)
//	    private String blockCellNo;
//
//	    @Column(name = "Yard_Location1", length = 10)
//	    private String yardLocation1;
//
//	    @Column(name = "Yard_Block1", length = 10)
//	    private String yardBlock1;
//
//	    @Column(name = "Block_Cell_No1", length = 10)
//	    private String blockCellNo1;
//
//	    @Column(name = "Yard_Packages", precision = 18, scale = 3)
//	    private BigDecimal yardPackages;
//
//	    @Column(name = "Cell_Area_Allocated", precision = 18, scale = 3)
//	    private BigDecimal cellAreaAllocated;
//
//	    @Column(name = "On_Account_Of", length = 6)
//	    private String onAccountOf;
//
//	    @Column(name = "CHA", length = 10)
//	    private String cha;
//
//	    @Column(name = "Stuff_Request_Qty", precision = 8, scale = 0)
//	    private BigDecimal stuffRequestQty;
//
//	    @Column(name = "Stuffed_Qty", precision = 8, scale = 0)
//	    private BigDecimal stuffedQty;
//
//	    @Column(name = "Prv_Stuffed_Qty", precision = 8, scale = 0)
//	    private BigDecimal prvStuffedQty;
//
//	    @Column(name = "Balance_Qty", precision = 8, scale = 0)
//	    private BigDecimal balanceQty;
//
//	    @Column(name = "Cargo_weight", precision = 16, scale = 4)
//	    private BigDecimal cargoWeight;
//
//	    @Column(name = "Total_Cargo_Wt", precision = 16, scale = 4)
//	    private BigDecimal totalCargoWeight;
//
//	    @Column(name = "Total_GW", precision = 16, scale = 4)
//	    private BigDecimal totalGrossWeight;
//
//	    @Column(name = "GROSS_WEIGHT", precision = 16, scale = 4)
//	    private BigDecimal grossWeight;
//
//	    @Column(name = "Weighment_Flag", length = 1)
//	    private String weighmentFlag;
//
//	    @Column(name = "Weighment_Done", length = 1)
//	    private String weighmentDone;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Weighment_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date weighmentDate;
//
//	    @Column(name = "Weighment_weight", precision = 16, scale = 3)
//	    private BigDecimal weighmentWeight;
//
//	    @Column(name = "Weighment_Pass_No", length = 30)
//	    private String weighmentPassNo;
//	    
//	    @Column(name = "Tare_Weight", precision = 15, scale = 3)
//	    private BigDecimal tareWeight;
//
//	    @Column(name = "Area_Released", precision = 8, scale = 3, nullable = true)
//	    private BigDecimal areaReleased;
//
//	    @Column(name = "Gen_Set_Required", length = 1)
//	    private String genSetRequired;
//
//	    @Column(name = "Haz", length = 1)
//	    private String haz;
//
//	    @Column(name = "IMO_Code", length = 10)
//	    private String imoCode;
//
//	    @Column(name = "Container_Invoice_Type", length = 10)
//	    private String containerInvoiceType;
//
//	    @Column(name = "Item", length = 6)
//	    private String item;
//
//	    @Column(name = "Shipping_Agent", length = 6)
//	    private String shippingAgent;
//
//	    @Column(name = "Shipping_Line", length = 6)
//	    private String shippingLine;
//
//	    @Column(name = "Commodity", length = 250)
//	    private String commodity;
//
//	    @Column(name = "Customs_Seal_No", length = 15)
//	    private String customsSealNo;
//
//	    @Column(name = "VIA_No", length = 10)
//	    private String viaNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Carting_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date cartingDate;
//
//	    @Column(name = "ICD_Hub", length = 35)
//	    private String icdHub;
//
//	    @Column(name = "Exporter_Name", length = 100)
//	    private String exporterName;
//
//	    @Column(name = "Consignee", length = 60)
//	    private String consignee;
//
//	    @Column(name = "FOB", precision = 16, scale = 4)
//	    private BigDecimal fob;
//
//	    @Column(name = "Cover_Details", length = 250)
//	    private String coverDetails;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Cover_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date coverDate;
//
//	    @Column(name = "Holding_Agent", length = 1)
//	    private String holdingAgent;
//
//	    @Column(name = "Holding_Agent_Name", length = 35)
//	    private String holdingAgentName;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Hold_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date holdDate;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Release_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date releaseDate;
//
//	    @Column(name = "Hold_Remarks", length = 150)
//	    private String holdRemarks;
//
//	    @Column(name = "CLP_Status", length = 1)
//	    private String clpStatus;
//
//	    @Column(name = "CLP_Created_By", length = 10)
//	    private String clpCreatedBy;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "CLP_Created_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date clpCreatedDate;
//
//	    @Column(name = "CLP_Approved_By", length = 10)
//	    private String clpApprovedBy;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "CLP_Approved_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date clpApprovedDate;
//
//	    @Column(name = "Gate_Pass_No", length = 10)
//	    private String gatePassNo;
//
//	    @Column(name = "Gate_Out_Id", length = 10)
//	    private String gateOutId;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Gate_Out_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date gateOutDate;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Berthing_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date berthingDate;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Gate_Open_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date gateOpenDate;
//
//	    @Column(name = "Seal_Type", length = 5)
//	    private String sealType;
//
//	    @Column(name = "Seal_Dev", length = 5)
//	    private String sealDev;
//
//	    @Column(name = "Doc_Type", length = 5)
//	    private String docType;
//
//	    @Column(name = "Doc_No", length = 5)
//	    private String docNo;
//
//	    @Column(name = "Status", length = 1)
//	    private String status;
//
//	    @Column(name = "Created_By", length = 10)
//	    private String createdBy;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Created_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date createdDate;
//
//	    @Column(name = "Edited_By", length = 10)
//	    private String editedBy;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Edited_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date editedDate;
//
//	    @Column(name = "Approved_By", length = 10)
//	    private String approvedBy;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Approved_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date approvedDate;
//
//	    @Column(name = "CLP_Confirm_Status", length = 1)
//	    private String clpConfirmStatus;
//
//	    @Column(name = "CLP_Confirm_By", length = 30)
//	    private String clpConfirmBy;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "CLP_Confirm_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date clpConfirmDate;
//
//	    @Column(name = "CLP_PCS_Status", length = 1)
//	    private String clpPcsStatus;
//
//	    @Column(name = "CLP_PCS_MSG_CRE_Status", length = 1)
//	    private String clpPcsMsgCreStatus;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "CLP_PCS_MSG_CRE_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date clpPcsMsgCreDate;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "CLP_PCS_MSG_AMD_CRE_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date clpPcsMsgAmdCreDate;
//
//	    @Column(name = "Document_Number", precision = 16, scale = 0)
//	    private BigDecimal documentNumber;
//
//	    @Column(name = "Common_Reference_Number", precision = 16, scale = 0)
//	    private BigDecimal commonReferenceNumber;
//
//	    @Column(name = "AMD_Document_Number", precision = 16, scale = 0)
//	    private BigDecimal amdDocumentNumber;
//
//	    @Column(name = "AMD_Common_Reference_Number", precision = 16, scale = 0)
//	    private BigDecimal amdCommonReferenceNumber;
//
//	    @Column(name = "SF_Job_No", length = 10)
//	    private String sfJobNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "SF_Job_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date sfJobDate;
//
//	    @Column(name = "ASR_Job_No", length = 10)
//	    private String asrJobNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "ASR_Job_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date asrJobDate;
//	    
//	    
//	    @Column(name = "CIM_No", length = 10)
//	    private String cimNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "CIM_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date cimDate;
//
//	    @Column(name = "Bond_No", length = 10)
//	    private String bondNo;
//
//	    @Column(name = "DP_Job_No", length = 10)
//	    private String dpJobNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "DP_Job_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date dpJobDate;
//
//	    @Column(name = "DT_Job_No", length = 10)
//	    private String dtJobNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "DT_Job_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date dtJobDate;
//
//	    @Column(name = "From_Pkg")
//	    private int fromPkg;
//
//	    @Column(name = "To_Pkg")
//	    private int toPkg;
//
//	    @Column(name = "Is_SF_Ack", length = 1)
//	    private String isSfAck;
//
//	    @Column(name = "Ack_SF_Status", length = 20)
//	    private String ackSfStatus;
//
//	    @Column(name = "Is_ASR_Ack", length = 1)
//	    private String isAsrAck;
//
//	    @Column(name = "Ack_ASR_Status", length = 20)
//	    private String ackAsrStatus;
//
//	    @Column(name = "Is_DP_Ack", length = 1)
//	    private String isDpAck;
//
//	    @Column(name = "Ack_DP_Status", length = 20)
//	    private String ackDpStatus;
//
//	    @Column(name = "Is_DT_Ack", length = 1)
//	    private String isDtAck;
//
//	    @Column(name = "Ack_DT_Status", length = 20)
//	    private String ackDtStatus;
//
//	    @Column(name = "Is_SF_Cancel_Status", length = 1)
//	    private String isSfCancelStatus;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "SF_Cancel_Created_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date sfCancelCreatedDate;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "SF_Cancel_Ack_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date sfCancelAckDate;
//
//	    @Column(name = "Is_SF_Cancel_Desc", length = 20)
//	    private String isSfCancelDesc;
//
//	    @Column(name = "oth_party_Id", length = 10)
//	    private String othPartyId;
//
//	    @Column(name = "Invoice_Assesed", length = 1)
//	    private String invoiceAssesed;
//
//	    @Column(name = "Assesment_Id", length = 20)
//	    private String assesmentId;
//
//	    @Column(name = "Invoice_No", length = 16)
//	    private String invoiceNo;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Invoice_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date invoiceDate;
//
//	    @Column(name = "Credit_Type", length = 1)
//	    private String creditType;
//
//	    @Column(name = "Invoice_Category", length = 10)
//	    private String invoiceCategory;
//
//	    @Column(name = "Bill_Amt", precision = 12, scale = 2)
//	    private BigDecimal billAmt;
//
//	    @Column(name = "Invoice_Amt", precision = 12, scale = 2)
//	    private BigDecimal invoiceAmt;
//
//	    @Column(name = "Back_to_town_Remark", length = 250)
//	    private String backToTownRemark;
//
//	    @Column(name = "Stuff_Tally_Flag", length = 1)
//	    private String stuffTallyFlag;
//
//	    @Column(name = "STUFFTALLY_WO_TRANS_ID", length = 25)
//	    private String stuffTallyWoTransId;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "STUFFTALLY_CUT_WO_TRANS_DATE")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date stuffTallyCutWoTransDate;
//
//	    @Column(name = "SSR_TRANS_ID", length = 10)
//	    private String ssrTransId;
//
//	    @Column(name = "nopGross_weight", precision = 16, scale = 3)
//	    private BigDecimal nopGrossWeight;
//
//	    @Column(name = "delivery_order_No", length = 35)
//	    private String deliveryOrderNo;
//
//	    @Column(name = "Rework_Flag", length = 1)
//	    private String reworkFlag;
//
//	    @Column(name = "Rework_Id", length = 10)
//	    private String reworkId;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Rework_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date reworkDate;
//
//	    @Column(name = "PAY_LOAD", precision = 16, scale = 3)
//	    private BigDecimal payLoad;
//
//	    @Column(name = "stuff_mode", length = 15)
//	    private String stuffMode;
//
//	    @Column(name = "Form_Thirteen_Entry_Flag", length = 1)
//	    private String formThirteenEntryFlag;
//
//	    @Column(name = "Form_Thirteen_Entry_Remarks", length = 250)
//	    private String formThirteenEntryRemarks;
//
//	    @Temporal(TemporalType.TIMESTAMP)
//	    @Column(name = "Form_Thirteen_Entry_Date")
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date formThirteenEntryDate;
//
//	    @Column(name = "Form_Thirteen_Entry_user", length = 10)
//	    private String formThirteenEntryUser;
//
//	    @Column(name = "Cal_Gross_Wt", precision = 16, scale = 3)
//	    private BigDecimal calGrossWt;
//
//	    @Column(name = "Stuff_Line_Id")
//	    private int stuffLineId;
//
//	    @Column(name = "Eqactivity_flg", length = 1)
//	    private String eqActivityFlag;
//	    
//	    @Column(name="Type_Of_Package",length = 6)
//	    private String typeOfPackage;
//	    
//	    @Transient
//	    private String vesselName;
//	    
//	    @Transient
//	    private String chaName;
//	    
//	    @Transient
//	    private BigDecimal sbPackages;
//	    
//	    @Transient
//	    private BigDecimal stuffedQuantity;
//	    
//	    @Transient
//	    private BigDecimal sbWt;
//	    
//	    @Transient
//	    private Date gateInDate;
//	    
//	    @Transient
//	    private String cargoType;
//	    
//	    @Transient
//	    private BigDecimal contStuffPackages;
//	    
//	    @Transient
//	    private BigDecimal balQty;
//	    
//	    @Transient
//	    private String shippingLineName;
//
//	    @Transient
//	    private String shippingAgentName;
//	    
//	    
//	    @Transient
//	    private String terminalName;
//
//	    @Transient
//	    private String finalPodName;
//
//	    
//	    
//	    
//	    
//
//
//
//
//
//
//
//		public String getTerminalName() {
//			return terminalName;
//		}
//
//
//
//
//
//
//
//		public void setTerminalName(String terminalName) {
//			this.terminalName = terminalName;
//		}
//
//
//
//
//
//
//
//		public String getFinalPodName() {
//			return finalPodName;
//		}
//
//
//
//
//
//
//
//		public void setFinalPodName(String finalPodName) {
//			this.finalPodName = finalPodName;
//		}
//
//
//
//
//
//
//
//		public String getShippingLineName() {
//			return shippingLineName;
//		}
//
//
//
//
//
//
//
//		public void setShippingLineName(String shippingLineName) {
//			this.shippingLineName = shippingLineName;
//		}
//
//
//
//
//
//
//
//		public String getShippingAgentName() {
//			return shippingAgentName;
//		}
//
//
//
//
//
//
//
//		public void setShippingAgentName(String shippingAgentName) {
//			this.shippingAgentName = shippingAgentName;
//		}
//
//
//
//
//
//
//
//		public ExportStuffTally() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//
//		
//		
//		
//
//
//
//		public BigDecimal getBalQty() {
//			return balQty;
//		}
//
//
//
//
//
//
//
//		public void setBalQty(BigDecimal balQty) {
//			this.balQty = balQty;
//		}
//
//
//
//
//
//
//
//		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
//				int stuffTallyLineId, String profitcentreId, String cartingTransId, String sbLineId,
//				String cartingLineId, String sbNo, String movementReqId, String movementType, Date stuffTallyDate,
//				String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
//				String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
//				String finalPod, String containerNo, String containerStatus, String asrContainerStatus,
//				String currentLocation, Date periodFrom, String gateInId, String containerSize, String containerType,
//				String containerCondition, String crgYardLocation, String crgYardBlock, String crgBlockCellNo,
//				String yardLocation, String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1,
//				String blockCellNo1, BigDecimal yardPackages, BigDecimal cellAreaAllocated, String onAccountOf,
//				String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal prvStuffedQty,
//				BigDecimal balanceQty, BigDecimal cargoWeight, BigDecimal totalCargoWeight, BigDecimal totalGrossWeight,
//				BigDecimal grossWeight, String weighmentFlag, String weighmentDone, Date weighmentDate,
//				BigDecimal weighmentWeight, String weighmentPassNo, BigDecimal tareWeight, BigDecimal areaReleased,
//				String genSetRequired, String haz, String imoCode, String containerInvoiceType, String item,
//				String shippingAgent, String shippingLine, String commodity, String customsSealNo, String viaNo,
//				Date cartingDate, String icdHub, String exporterName, String consignee, BigDecimal fob,
//				String coverDetails, Date coverDate, String holdingAgent, String holdingAgentName, Date holdDate,
//				Date releaseDate, String holdRemarks, String clpStatus, String clpCreatedBy, Date clpCreatedDate,
//				String clpApprovedBy, Date clpApprovedDate, String gatePassNo, String gateOutId, Date gateOutDate,
//				Date berthingDate, Date gateOpenDate, String sealType, String sealDev, String docType, String docNo,
//				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
//				Date approvedDate, String clpConfirmStatus, String clpConfirmBy, Date clpConfirmDate,
//				String clpPcsStatus, String clpPcsMsgCreStatus, Date clpPcsMsgCreDate, Date clpPcsMsgAmdCreDate,
//				BigDecimal documentNumber, BigDecimal commonReferenceNumber, BigDecimal amdDocumentNumber,
//				BigDecimal amdCommonReferenceNumber, String sfJobNo, Date sfJobDate, String asrJobNo, Date asrJobDate,
//				String cimNo, Date cimDate, String bondNo, String dpJobNo, Date dpJobDate, String dtJobNo,
//				Date dtJobDate, int fromPkg, int toPkg, String isSfAck, String ackSfStatus, String isAsrAck,
//				String ackAsrStatus, String isDpAck, String ackDpStatus, String isDtAck, String ackDtStatus,
//				String isSfCancelStatus, Date sfCancelCreatedDate, Date sfCancelAckDate, String isSfCancelDesc,
//				String othPartyId, String invoiceAssesed, String assesmentId, String invoiceNo, Date invoiceDate,
//				String creditType, String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt,
//				String backToTownRemark, String stuffTallyFlag, String stuffTallyWoTransId,
//				Date stuffTallyCutWoTransDate, String ssrTransId, BigDecimal nopGrossWeight, String deliveryOrderNo,
//				String reworkFlag, String reworkId, Date reworkDate, BigDecimal payLoad, String stuffMode,
//				String formThirteenEntryFlag, String formThirteenEntryRemarks, Date formThirteenEntryDate,
//				String formThirteenEntryUser, BigDecimal calGrossWt, int stuffLineId, String eqActivityFlag,
//				String typeOfPackage, String vesselName, String chaName, BigDecimal sbPackages,
//				BigDecimal stuffedQuantity, BigDecimal sbWt, Date gateInDate, String cargoType,
//				BigDecimal contStuffPackages, BigDecimal balQty) {
//			this.companyId = companyId;
//			this.branchId = branchId;
//			this.stuffTallyId = stuffTallyId;
//			this.sbTransId = sbTransId;
//			this.stuffTallyLineId = stuffTallyLineId;
//			this.profitcentreId = profitcentreId;
//			this.cartingTransId = cartingTransId;
//			this.sbLineId = sbLineId;
//			this.cartingLineId = cartingLineId;
//			this.sbNo = sbNo;
//			this.movementReqId = movementReqId;
//			this.movementType = movementType;
//			this.stuffTallyDate = stuffTallyDate;
//			this.stuffId = stuffId;
//			this.stuffDate = stuffDate;
//			this.sbDate = sbDate;
//			this.shift = shift;
//			this.agentSealNo = agentSealNo;
//			this.vesselId = vesselId;
//			this.voyageNo = voyageNo;
//			this.rotationNo = rotationNo;
//			this.rotationDate = rotationDate;
//			this.pol = pol;
//			this.terminal = terminal;
//			this.pod = pod;
//			this.finalPod = finalPod;
//			this.containerNo = containerNo;
//			this.containerStatus = containerStatus;
//			this.asrContainerStatus = asrContainerStatus;
//			this.currentLocation = currentLocation;
//			this.periodFrom = periodFrom;
//			this.gateInId = gateInId;
//			this.containerSize = containerSize;
//			this.containerType = containerType;
//			this.containerCondition = containerCondition;
//			this.crgYardLocation = crgYardLocation;
//			this.crgYardBlock = crgYardBlock;
//			this.crgBlockCellNo = crgBlockCellNo;
//			this.yardLocation = yardLocation;
//			this.yardBlock = yardBlock;
//			this.blockCellNo = blockCellNo;
//			this.yardLocation1 = yardLocation1;
//			this.yardBlock1 = yardBlock1;
//			this.blockCellNo1 = blockCellNo1;
//			this.yardPackages = yardPackages;
//			this.cellAreaAllocated = cellAreaAllocated;
//			this.onAccountOf = onAccountOf;
//			this.cha = cha;
//			this.stuffRequestQty = stuffRequestQty;
//			this.stuffedQty = stuffedQty;
//			this.prvStuffedQty = prvStuffedQty;
//			this.balanceQty = balanceQty;
//			this.cargoWeight = cargoWeight;
//			this.totalCargoWeight = totalCargoWeight;
//			this.totalGrossWeight = totalGrossWeight;
//			this.grossWeight = grossWeight;
//			this.weighmentFlag = weighmentFlag;
//			this.weighmentDone = weighmentDone;
//			this.weighmentDate = weighmentDate;
//			this.weighmentWeight = weighmentWeight;
//			this.weighmentPassNo = weighmentPassNo;
//			this.tareWeight = tareWeight;
//			this.areaReleased = areaReleased;
//			this.genSetRequired = genSetRequired;
//			this.haz = haz;
//			this.imoCode = imoCode;
//			this.containerInvoiceType = containerInvoiceType;
//			this.item = item;
//			this.shippingAgent = shippingAgent;
//			this.shippingLine = shippingLine;
//			this.commodity = commodity;
//			this.customsSealNo = customsSealNo;
//			this.viaNo = viaNo;
//			this.cartingDate = cartingDate;
//			this.icdHub = icdHub;
//			this.exporterName = exporterName;
//			this.consignee = consignee;
//			this.fob = fob;
//			this.coverDetails = coverDetails;
//			this.coverDate = coverDate;
//			this.holdingAgent = holdingAgent;
//			this.holdingAgentName = holdingAgentName;
//			this.holdDate = holdDate;
//			this.releaseDate = releaseDate;
//			this.holdRemarks = holdRemarks;
//			this.clpStatus = clpStatus;
//			this.clpCreatedBy = clpCreatedBy;
//			this.clpCreatedDate = clpCreatedDate;
//			this.clpApprovedBy = clpApprovedBy;
//			this.clpApprovedDate = clpApprovedDate;
//			this.gatePassNo = gatePassNo;
//			this.gateOutId = gateOutId;
//			this.gateOutDate = gateOutDate;
//			this.berthingDate = berthingDate;
//			this.gateOpenDate = gateOpenDate;
//			this.sealType = sealType;
//			this.sealDev = sealDev;
//			this.docType = docType;
//			this.docNo = docNo;
//			this.status = status;
//			this.createdBy = createdBy;
//			this.createdDate = createdDate;
//			this.editedBy = editedBy;
//			this.editedDate = editedDate;
//			this.approvedBy = approvedBy;
//			this.approvedDate = approvedDate;
//			this.clpConfirmStatus = clpConfirmStatus;
//			this.clpConfirmBy = clpConfirmBy;
//			this.clpConfirmDate = clpConfirmDate;
//			this.clpPcsStatus = clpPcsStatus;
//			this.clpPcsMsgCreStatus = clpPcsMsgCreStatus;
//			this.clpPcsMsgCreDate = clpPcsMsgCreDate;
//			this.clpPcsMsgAmdCreDate = clpPcsMsgAmdCreDate;
//			this.documentNumber = documentNumber;
//			this.commonReferenceNumber = commonReferenceNumber;
//			this.amdDocumentNumber = amdDocumentNumber;
//			this.amdCommonReferenceNumber = amdCommonReferenceNumber;
//			this.sfJobNo = sfJobNo;
//			this.sfJobDate = sfJobDate;
//			this.asrJobNo = asrJobNo;
//			this.asrJobDate = asrJobDate;
//			this.cimNo = cimNo;
//			this.cimDate = cimDate;
//			this.bondNo = bondNo;
//			this.dpJobNo = dpJobNo;
//			this.dpJobDate = dpJobDate;
//			this.dtJobNo = dtJobNo;
//			this.dtJobDate = dtJobDate;
//			this.fromPkg = fromPkg;
//			this.toPkg = toPkg;
//			this.isSfAck = isSfAck;
//			this.ackSfStatus = ackSfStatus;
//			this.isAsrAck = isAsrAck;
//			this.ackAsrStatus = ackAsrStatus;
//			this.isDpAck = isDpAck;
//			this.ackDpStatus = ackDpStatus;
//			this.isDtAck = isDtAck;
//			this.ackDtStatus = ackDtStatus;
//			this.isSfCancelStatus = isSfCancelStatus;
//			this.sfCancelCreatedDate = sfCancelCreatedDate;
//			this.sfCancelAckDate = sfCancelAckDate;
//			this.isSfCancelDesc = isSfCancelDesc;
//			this.othPartyId = othPartyId;
//			this.invoiceAssesed = invoiceAssesed;
//			this.assesmentId = assesmentId;
//			this.invoiceNo = invoiceNo;
//			this.invoiceDate = invoiceDate;
//			this.creditType = creditType;
//			this.invoiceCategory = invoiceCategory;
//			this.billAmt = billAmt;
//			this.invoiceAmt = invoiceAmt;
//			this.backToTownRemark = backToTownRemark;
//			this.stuffTallyFlag = stuffTallyFlag;
//			this.stuffTallyWoTransId = stuffTallyWoTransId;
//			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
//			this.ssrTransId = ssrTransId;
//			this.nopGrossWeight = nopGrossWeight;
//			this.deliveryOrderNo = deliveryOrderNo;
//			this.reworkFlag = reworkFlag;
//			this.reworkId = reworkId;
//			this.reworkDate = reworkDate;
//			this.payLoad = payLoad;
//			this.stuffMode = stuffMode;
//			this.formThirteenEntryFlag = formThirteenEntryFlag;
//			this.formThirteenEntryRemarks = formThirteenEntryRemarks;
//			this.formThirteenEntryDate = formThirteenEntryDate;
//			this.formThirteenEntryUser = formThirteenEntryUser;
//			this.calGrossWt = calGrossWt;
//			this.stuffLineId = stuffLineId;
//			this.eqActivityFlag = eqActivityFlag;
//			this.typeOfPackage = typeOfPackage;
//			this.vesselName = vesselName;
//			this.chaName = chaName;
//			this.sbPackages = sbPackages;
//			this.stuffedQuantity = stuffedQuantity;
//			this.sbWt = sbWt;
//			this.gateInDate = gateInDate;
//			this.cargoType = cargoType;
//			this.contStuffPackages = contStuffPackages;
//			this.balQty = balQty;
//		}
//
//
//
//
//
//
//
//		public BigDecimal getContStuffPackages() {
//			return contStuffPackages;
//		}
//
//
//
//
//
//
//
//		public void setContStuffPackages(BigDecimal contStuffPackages) {
//			this.contStuffPackages = contStuffPackages;
//		}
//
//
//
//
//
//
//
//
//
//
//		public BigDecimal getStuffedQuantity() {
//			return stuffedQuantity;
//		}
//
//
//
//
//
//
//
//		public void setStuffedQuantity(BigDecimal stuffedQuantity) {
//			this.stuffedQuantity = stuffedQuantity;
//		}
//
//
//
//
//
//
//
//		public String getCargoType() {
//			return cargoType;
//		}
//
//
//
//
//
//
//
//		public void setCargoType(String cargoType) {
//			this.cargoType = cargoType;
//		}
//
//
//
//
//
//
//
//		public Date getGateInDate() {
//			return gateInDate;
//		}
//
//
//
//
//
//
//
//		public void setGateInDate(Date gateInDate) {
//			this.gateInDate = gateInDate;
//		}
//
//
//
//
//
//
//
//		public BigDecimal getSbWt() {
//			return sbWt;
//		}
//
//
//
//
//
//
//
//		public void setSbWt(BigDecimal sbWt) {
//			this.sbWt = sbWt;
//		}
//
//
//
//
//
//
//
//		public BigDecimal getSbPackages() {
//			return sbPackages;
//		}
//
//
//
//
//
//
//
//		public void setSbPackages(BigDecimal sbPackages) {
//			this.sbPackages = sbPackages;
//		}
//
//
//
//
//
//
//
//		public String getChaName() {
//			return chaName;
//		}
//
//
//
//
//
//
//
//		public void setChaName(String chaName) {
//			this.chaName = chaName;
//		}
//
//
//
//
//	
//
//
//
//
//
//
//
//
//		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
//				int stuffTallyLineId, String profitcentreId, String cartingTransId, String sbLineId,
//				String cartingLineId, String sbNo, String movementReqId, String movementType, Date stuffTallyDate,
//				String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
//				String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
//				String finalPod, String containerNo, String containerStatus, String asrContainerStatus,
//				String currentLocation, Date periodFrom, String gateInId, String containerSize, String containerType,
//				String containerCondition, String crgYardLocation, String crgYardBlock, String crgBlockCellNo,
//				String yardLocation, String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1,
//				String blockCellNo1, BigDecimal yardPackages, BigDecimal cellAreaAllocated, String onAccountOf,
//				String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal prvStuffedQty,
//				BigDecimal balanceQty, BigDecimal cargoWeight, BigDecimal totalCargoWeight, BigDecimal totalGrossWeight,
//				BigDecimal grossWeight, String weighmentFlag, String weighmentDone, Date weighmentDate,
//				BigDecimal weighmentWeight, String weighmentPassNo, BigDecimal tareWeight, BigDecimal areaReleased,
//				String genSetRequired, String haz, String imoCode, String containerInvoiceType, String item,
//				String shippingAgent, String shippingLine, String commodity, String customsSealNo, String viaNo,
//				Date cartingDate, String icdHub, String exporterName, String consignee, BigDecimal fob,
//				String coverDetails, Date coverDate, String holdingAgent, String holdingAgentName, Date holdDate,
//				Date releaseDate, String holdRemarks, String clpStatus, String clpCreatedBy, Date clpCreatedDate,
//				String clpApprovedBy, Date clpApprovedDate, String gatePassNo, String gateOutId, Date gateOutDate,
//				Date berthingDate, Date gateOpenDate, String sealType, String sealDev, String docType, String docNo,
//				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
//				Date approvedDate, String clpConfirmStatus, String clpConfirmBy, Date clpConfirmDate,
//				String clpPcsStatus, String clpPcsMsgCreStatus, Date clpPcsMsgCreDate, Date clpPcsMsgAmdCreDate,
//				BigDecimal documentNumber, BigDecimal commonReferenceNumber, BigDecimal amdDocumentNumber,
//				BigDecimal amdCommonReferenceNumber, String sfJobNo, Date sfJobDate, String asrJobNo, Date asrJobDate,
//				String cimNo, Date cimDate, String bondNo, String dpJobNo, Date dpJobDate, String dtJobNo,
//				Date dtJobDate, int fromPkg, int toPkg, String isSfAck, String ackSfStatus, String isAsrAck,
//				String ackAsrStatus, String isDpAck, String ackDpStatus, String isDtAck, String ackDtStatus,
//				String isSfCancelStatus, Date sfCancelCreatedDate, Date sfCancelAckDate, String isSfCancelDesc,
//				String othPartyId, String invoiceAssesed, String assesmentId, String invoiceNo, Date invoiceDate,
//				String creditType, String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt,
//				String backToTownRemark, String stuffTallyFlag, String stuffTallyWoTransId,
//				Date stuffTallyCutWoTransDate, String ssrTransId, BigDecimal nopGrossWeight, String deliveryOrderNo,
//				String reworkFlag, String reworkId, Date reworkDate, BigDecimal payLoad, String stuffMode,
//				String formThirteenEntryFlag, String formThirteenEntryRemarks, Date formThirteenEntryDate,
//				String formThirteenEntryUser, BigDecimal calGrossWt, int stuffLineId, String eqActivityFlag,
//				String typeOfPackage, String vesselName, String chaName, BigDecimal sbPackages,
//				BigDecimal stuffedQuantity, BigDecimal sbWt, Date gateInDate, String cargoType) {
//			this.companyId = companyId;
//			this.branchId = branchId;
//			this.stuffTallyId = stuffTallyId;
//			this.sbTransId = sbTransId;
//			this.stuffTallyLineId = stuffTallyLineId;
//			this.profitcentreId = profitcentreId;
//			this.cartingTransId = cartingTransId;
//			this.sbLineId = sbLineId;
//			this.cartingLineId = cartingLineId;
//			this.sbNo = sbNo;
//			this.movementReqId = movementReqId;
//			this.movementType = movementType;
//			this.stuffTallyDate = stuffTallyDate;
//			this.stuffId = stuffId;
//			this.stuffDate = stuffDate;
//			this.sbDate = sbDate;
//			this.shift = shift;
//			this.agentSealNo = agentSealNo;
//			this.vesselId = vesselId;
//			this.voyageNo = voyageNo;
//			this.rotationNo = rotationNo;
//			this.rotationDate = rotationDate;
//			this.pol = pol;
//			this.terminal = terminal;
//			this.pod = pod;
//			this.finalPod = finalPod;
//			this.containerNo = containerNo;
//			this.containerStatus = containerStatus;
//			this.asrContainerStatus = asrContainerStatus;
//			this.currentLocation = currentLocation;
//			this.periodFrom = periodFrom;
//			this.gateInId = gateInId;
//			this.containerSize = containerSize;
//			this.containerType = containerType;
//			this.containerCondition = containerCondition;
//			this.crgYardLocation = crgYardLocation;
//			this.crgYardBlock = crgYardBlock;
//			this.crgBlockCellNo = crgBlockCellNo;
//			this.yardLocation = yardLocation;
//			this.yardBlock = yardBlock;
//			this.blockCellNo = blockCellNo;
//			this.yardLocation1 = yardLocation1;
//			this.yardBlock1 = yardBlock1;
//			this.blockCellNo1 = blockCellNo1;
//			this.yardPackages = yardPackages;
//			this.cellAreaAllocated = cellAreaAllocated;
//			this.onAccountOf = onAccountOf;
//			this.cha = cha;
//			this.stuffRequestQty = stuffRequestQty;
//			this.stuffedQty = stuffedQty;
//			this.prvStuffedQty = prvStuffedQty;
//			this.balanceQty = balanceQty;
//			this.cargoWeight = cargoWeight;
//			this.totalCargoWeight = totalCargoWeight;
//			this.totalGrossWeight = totalGrossWeight;
//			this.grossWeight = grossWeight;
//			this.weighmentFlag = weighmentFlag;
//			this.weighmentDone = weighmentDone;
//			this.weighmentDate = weighmentDate;
//			this.weighmentWeight = weighmentWeight;
//			this.weighmentPassNo = weighmentPassNo;
//			this.tareWeight = tareWeight;
//			this.areaReleased = areaReleased;
//			this.genSetRequired = genSetRequired;
//			this.haz = haz;
//			this.imoCode = imoCode;
//			this.containerInvoiceType = containerInvoiceType;
//			this.item = item;
//			this.shippingAgent = shippingAgent;
//			this.shippingLine = shippingLine;
//			this.commodity = commodity;
//			this.customsSealNo = customsSealNo;
//			this.viaNo = viaNo;
//			this.cartingDate = cartingDate;
//			this.icdHub = icdHub;
//			this.exporterName = exporterName;
//			this.consignee = consignee;
//			this.fob = fob;
//			this.coverDetails = coverDetails;
//			this.coverDate = coverDate;
//			this.holdingAgent = holdingAgent;
//			this.holdingAgentName = holdingAgentName;
//			this.holdDate = holdDate;
//			this.releaseDate = releaseDate;
//			this.holdRemarks = holdRemarks;
//			this.clpStatus = clpStatus;
//			this.clpCreatedBy = clpCreatedBy;
//			this.clpCreatedDate = clpCreatedDate;
//			this.clpApprovedBy = clpApprovedBy;
//			this.clpApprovedDate = clpApprovedDate;
//			this.gatePassNo = gatePassNo;
//			this.gateOutId = gateOutId;
//			this.gateOutDate = gateOutDate;
//			this.berthingDate = berthingDate;
//			this.gateOpenDate = gateOpenDate;
//			this.sealType = sealType;
//			this.sealDev = sealDev;
//			this.docType = docType;
//			this.docNo = docNo;
//			this.status = status;
//			this.createdBy = createdBy;
//			this.createdDate = createdDate;
//			this.editedBy = editedBy;
//			this.editedDate = editedDate;
//			this.approvedBy = approvedBy;
//			this.approvedDate = approvedDate;
//			this.clpConfirmStatus = clpConfirmStatus;
//			this.clpConfirmBy = clpConfirmBy;
//			this.clpConfirmDate = clpConfirmDate;
//			this.clpPcsStatus = clpPcsStatus;
//			this.clpPcsMsgCreStatus = clpPcsMsgCreStatus;
//			this.clpPcsMsgCreDate = clpPcsMsgCreDate;
//			this.clpPcsMsgAmdCreDate = clpPcsMsgAmdCreDate;
//			this.documentNumber = documentNumber;
//			this.commonReferenceNumber = commonReferenceNumber;
//			this.amdDocumentNumber = amdDocumentNumber;
//			this.amdCommonReferenceNumber = amdCommonReferenceNumber;
//			this.sfJobNo = sfJobNo;
//			this.sfJobDate = sfJobDate;
//			this.asrJobNo = asrJobNo;
//			this.asrJobDate = asrJobDate;
//			this.cimNo = cimNo;
//			this.cimDate = cimDate;
//			this.bondNo = bondNo;
//			this.dpJobNo = dpJobNo;
//			this.dpJobDate = dpJobDate;
//			this.dtJobNo = dtJobNo;
//			this.dtJobDate = dtJobDate;
//			this.fromPkg = fromPkg;
//			this.toPkg = toPkg;
//			this.isSfAck = isSfAck;
//			this.ackSfStatus = ackSfStatus;
//			this.isAsrAck = isAsrAck;
//			this.ackAsrStatus = ackAsrStatus;
//			this.isDpAck = isDpAck;
//			this.ackDpStatus = ackDpStatus;
//			this.isDtAck = isDtAck;
//			this.ackDtStatus = ackDtStatus;
//			this.isSfCancelStatus = isSfCancelStatus;
//			this.sfCancelCreatedDate = sfCancelCreatedDate;
//			this.sfCancelAckDate = sfCancelAckDate;
//			this.isSfCancelDesc = isSfCancelDesc;
//			this.othPartyId = othPartyId;
//			this.invoiceAssesed = invoiceAssesed;
//			this.assesmentId = assesmentId;
//			this.invoiceNo = invoiceNo;
//			this.invoiceDate = invoiceDate;
//			this.creditType = creditType;
//			this.invoiceCategory = invoiceCategory;
//			this.billAmt = billAmt;
//			this.invoiceAmt = invoiceAmt;
//			this.backToTownRemark = backToTownRemark;
//			this.stuffTallyFlag = stuffTallyFlag;
//			this.stuffTallyWoTransId = stuffTallyWoTransId;
//			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
//			this.ssrTransId = ssrTransId;
//			this.nopGrossWeight = nopGrossWeight;
//			this.deliveryOrderNo = deliveryOrderNo;
//			this.reworkFlag = reworkFlag;
//			this.reworkId = reworkId;
//			this.reworkDate = reworkDate;
//			this.payLoad = payLoad;
//			this.stuffMode = stuffMode;
//			this.formThirteenEntryFlag = formThirteenEntryFlag;
//			this.formThirteenEntryRemarks = formThirteenEntryRemarks;
//			this.formThirteenEntryDate = formThirteenEntryDate;
//			this.formThirteenEntryUser = formThirteenEntryUser;
//			this.calGrossWt = calGrossWt;
//			this.stuffLineId = stuffLineId;
//			this.eqActivityFlag = eqActivityFlag;
//			this.typeOfPackage = typeOfPackage;
//			this.vesselName = vesselName;
//			this.chaName = chaName;
//			this.sbPackages = sbPackages;
//			this.stuffedQuantity = stuffedQuantity;
//			this.sbWt = sbWt;
//			this.gateInDate = gateInDate;
//			this.cargoType = cargoType;
//		}
//
//
//
//
//
//
//
//		public String getVesselName() {
//			return vesselName;
//		}
//
//
//
//
//
//
//
//		public void setVesselName(String vesselName) {
//			this.vesselName = vesselName;
//		}
//
//
//
//
//
//
//
//		public String getTypeOfPackage() {
//			return typeOfPackage;
//		}
//
//
//
//
//
//		public void setTypeOfPackage(String typeOfPackage) {
//			this.typeOfPackage = typeOfPackage;
//		}
//
//
//
//
//
//		public Date getRotationDate() {
//			return rotationDate;
//		}
//
//
//		public void setRotationDate(Date rotationDate) {
//			this.rotationDate = rotationDate;
//		}
//
//
//		public String getCompanyId() {
//			return companyId;
//		}
//
//		public void setCompanyId(String companyId) {
//			this.companyId = companyId;
//		}
//
//		public String getBranchId() {
//			return branchId;
//		}
//
//		public void setBranchId(String branchId) {
//			this.branchId = branchId;
//		}
//
//		public String getStuffTallyId() {
//			return stuffTallyId;
//		}
//
//		public void setStuffTallyId(String stuffTallyId) {
//			this.stuffTallyId = stuffTallyId;
//		}
//
//		public String getSbTransId() {
//			return sbTransId;
//		}
//
//		public void setSbTransId(String sbTransId) {
//			this.sbTransId = sbTransId;
//		}
//
//		public int getStuffTallyLineId() {
//			return stuffTallyLineId;
//		}
//
//		public void setStuffTallyLineId(int stuffTallyLineId) {
//			this.stuffTallyLineId = stuffTallyLineId;
//		}
//
//		public String getProfitcentreId() {
//			return profitcentreId;
//		}
//
//		public void setProfitcentreId(String profitcentreId) {
//			this.profitcentreId = profitcentreId;
//		}
//
//		public String getCartingTransId() {
//			return cartingTransId;
//		}
//
//		public void setCartingTransId(String cartingTransId) {
//			this.cartingTransId = cartingTransId;
//		}
//
//		public String getSbLineId() {
//			return sbLineId;
//		}
//
//		public void setSbLineId(String sbLineId) {
//			this.sbLineId = sbLineId;
//		}
//
//		public String getCartingLineId() {
//			return cartingLineId;
//		}
//
//		public void setCartingLineId(String cartingLineId) {
//			this.cartingLineId = cartingLineId;
//		}
//
//		public String getSbNo() {
//			return sbNo;
//		}
//
//		public void setSbNo(String sbNo) {
//			this.sbNo = sbNo;
//		}
//
//		public String getMovementReqId() {
//			return movementReqId;
//		}
//
//		public void setMovementReqId(String movementReqId) {
//			this.movementReqId = movementReqId;
//		}
//
//		public String getMovementType() {
//			return movementType;
//		}
//
//		public void setMovementType(String movementType) {
//			this.movementType = movementType;
//		}
//
//		public Date getStuffTallyDate() {
//			return stuffTallyDate;
//		}
//
//		public void setStuffTallyDate(Date stuffTallyDate) {
//			this.stuffTallyDate = stuffTallyDate;
//		}
//
//		public String getStuffId() {
//			return stuffId;
//		}
//
//		public void setStuffId(String stuffId) {
//			this.stuffId = stuffId;
//		}
//
//		public Date getStuffDate() {
//			return stuffDate;
//		}
//
//		public void setStuffDate(Date stuffDate) {
//			this.stuffDate = stuffDate;
//		}
//
//		public Date getSbDate() {
//			return sbDate;
//		}
//
//		public void setSbDate(Date sbDate) {
//			this.sbDate = sbDate;
//		}
//
//		public String getShift() {
//			return shift;
//		}
//
//		public void setShift(String shift) {
//			this.shift = shift;
//		}
//
//		public String getAgentSealNo() {
//			return agentSealNo;
//		}
//
//		public void setAgentSealNo(String agentSealNo) {
//			this.agentSealNo = agentSealNo;
//		}
//
//		public String getVesselId() {
//			return vesselId;
//		}
//
//		public void setVesselId(String vesselId) {
//			this.vesselId = vesselId;
//		}
//
//		public String getVoyageNo() {
//			return voyageNo;
//		}
//
//		public void setVoyageNo(String voyageNo) {
//			this.voyageNo = voyageNo;
//		}
//
//		public String getRotationNo() {
//			return rotationNo;
//		}
//
//		public void setRotationNo(String rotationNo) {
//			this.rotationNo = rotationNo;
//		}
//
//		public String getPol() {
//			return pol;
//		}
//
//		public void setPol(String pol) {
//			this.pol = pol;
//		}
//
//		public String getTerminal() {
//			return terminal;
//		}
//
//		public void setTerminal(String terminal) {
//			this.terminal = terminal;
//		}
//
//		public String getPod() {
//			return pod;
//		}
//
//		public void setPod(String pod) {
//			this.pod = pod;
//		}
//
//		public String getFinalPod() {
//			return finalPod;
//		}
//
//		public void setFinalPod(String finalPod) {
//			this.finalPod = finalPod;
//		}
//
//		public String getContainerNo() {
//			return containerNo;
//		}
//
//		public void setContainerNo(String containerNo) {
//			this.containerNo = containerNo;
//		}
//
//		public String getContainerStatus() {
//			return containerStatus;
//		}
//
//		public void setContainerStatus(String containerStatus) {
//			this.containerStatus = containerStatus;
//		}
//
//		public String getAsrContainerStatus() {
//			return asrContainerStatus;
//		}
//
//		public void setAsrContainerStatus(String asrContainerStatus) {
//			this.asrContainerStatus = asrContainerStatus;
//		}
//
//		public String getCurrentLocation() {
//			return currentLocation;
//		}
//
//		public void setCurrentLocation(String currentLocation) {
//			this.currentLocation = currentLocation;
//		}
//
//		public Date getPeriodFrom() {
//			return periodFrom;
//		}
//
//		public void setPeriodFrom(Date periodFrom) {
//			this.periodFrom = periodFrom;
//		}
//
//		public String getGateInId() {
//			return gateInId;
//		}
//
//		public void setGateInId(String gateInId) {
//			this.gateInId = gateInId;
//		}
//
//		public String getContainerSize() {
//			return containerSize;
//		}
//
//		public void setContainerSize(String containerSize) {
//			this.containerSize = containerSize;
//		}
//
//		public String getContainerType() {
//			return containerType;
//		}
//
//		public void setContainerType(String containerType) {
//			this.containerType = containerType;
//		}
//
//		public String getContainerCondition() {
//			return containerCondition;
//		}
//
//		public void setContainerCondition(String containerCondition) {
//			this.containerCondition = containerCondition;
//		}
//
//		public String getCrgYardLocation() {
//			return crgYardLocation;
//		}
//
//		public void setCrgYardLocation(String crgYardLocation) {
//			this.crgYardLocation = crgYardLocation;
//		}
//
//		public String getCrgYardBlock() {
//			return crgYardBlock;
//		}
//
//		public void setCrgYardBlock(String crgYardBlock) {
//			this.crgYardBlock = crgYardBlock;
//		}
//
//		public String getCrgBlockCellNo() {
//			return crgBlockCellNo;
//		}
//
//		public void setCrgBlockCellNo(String crgBlockCellNo) {
//			this.crgBlockCellNo = crgBlockCellNo;
//		}
//
//		public String getYardLocation() {
//			return yardLocation;
//		}
//
//		public void setYardLocation(String yardLocation) {
//			this.yardLocation = yardLocation;
//		}
//
//		public String getYardBlock() {
//			return yardBlock;
//		}
//
//		public void setYardBlock(String yardBlock) {
//			this.yardBlock = yardBlock;
//		}
//
//		public String getBlockCellNo() {
//			return blockCellNo;
//		}
//
//		public void setBlockCellNo(String blockCellNo) {
//			this.blockCellNo = blockCellNo;
//		}
//
//		public String getYardLocation1() {
//			return yardLocation1;
//		}
//
//		public void setYardLocation1(String yardLocation1) {
//			this.yardLocation1 = yardLocation1;
//		}
//
//		public String getYardBlock1() {
//			return yardBlock1;
//		}
//
//		public void setYardBlock1(String yardBlock1) {
//			this.yardBlock1 = yardBlock1;
//		}
//
//		public String getBlockCellNo1() {
//			return blockCellNo1;
//		}
//
//		public void setBlockCellNo1(String blockCellNo1) {
//			this.blockCellNo1 = blockCellNo1;
//		}
//
//		public BigDecimal getYardPackages() {
//			return yardPackages;
//		}
//
//		public void setYardPackages(BigDecimal yardPackages) {
//			this.yardPackages = yardPackages;
//		}
//
//		public BigDecimal getCellAreaAllocated() {
//			return cellAreaAllocated;
//		}
//
//		public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
//			this.cellAreaAllocated = cellAreaAllocated;
//		}
//
//		public String getOnAccountOf() {
//			return onAccountOf;
//		}
//
//		public void setOnAccountOf(String onAccountOf) {
//			this.onAccountOf = onAccountOf;
//		}
//
//		public String getCha() {
//			return cha;
//		}
//
//		public void setCha(String cha) {
//			this.cha = cha;
//		}
//
//		public BigDecimal getStuffRequestQty() {
//			return stuffRequestQty;
//		}
//
//		public void setStuffRequestQty(BigDecimal stuffRequestQty) {
//			this.stuffRequestQty = stuffRequestQty;
//		}
//
//		public BigDecimal getStuffedQty() {
//			return stuffedQty;
//		}
//
//		public void setStuffedQty(BigDecimal stuffedQty) {
//			this.stuffedQty = stuffedQty;
//		}
//
//		public BigDecimal getPrvStuffedQty() {
//			return prvStuffedQty;
//		}
//
//		public void setPrvStuffedQty(BigDecimal prvStuffedQty) {
//			this.prvStuffedQty = prvStuffedQty;
//		}
//
//		public BigDecimal getBalanceQty() {
//			return balanceQty;
//		}
//
//		public void setBalanceQty(BigDecimal balanceQty) {
//			this.balanceQty = balanceQty;
//		}
//
//		public BigDecimal getCargoWeight() {
//			return cargoWeight;
//		}
//
//		public void setCargoWeight(BigDecimal cargoWeight) {
//			this.cargoWeight = cargoWeight;
//		}
//
//		public BigDecimal getTotalCargoWeight() {
//			return totalCargoWeight;
//		}
//
//		public void setTotalCargoWeight(BigDecimal totalCargoWeight) {
//			this.totalCargoWeight = totalCargoWeight;
//		}
//
//		public BigDecimal getTotalGrossWeight() {
//			return totalGrossWeight;
//		}
//
//		public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
//			this.totalGrossWeight = totalGrossWeight;
//		}
//
//		public BigDecimal getGrossWeight() {
//			return grossWeight;
//		}
//
//		public void setGrossWeight(BigDecimal grossWeight) {
//			this.grossWeight = grossWeight;
//		}
//
//		public String getWeighmentFlag() {
//			return weighmentFlag;
//		}
//
//		public void setWeighmentFlag(String weighmentFlag) {
//			this.weighmentFlag = weighmentFlag;
//		}
//
//		public String getWeighmentDone() {
//			return weighmentDone;
//		}
//
//		public void setWeighmentDone(String weighmentDone) {
//			this.weighmentDone = weighmentDone;
//		}
//
//		public Date getWeighmentDate() {
//			return weighmentDate;
//		}
//
//		public void setWeighmentDate(Date weighmentDate) {
//			this.weighmentDate = weighmentDate;
//		}
//
//		public BigDecimal getWeighmentWeight() {
//			return weighmentWeight;
//		}
//
//		public void setWeighmentWeight(BigDecimal weighmentWeight) {
//			this.weighmentWeight = weighmentWeight;
//		}
//
//		public String getWeighmentPassNo() {
//			return weighmentPassNo;
//		}
//
//		public void setWeighmentPassNo(String weighmentPassNo) {
//			this.weighmentPassNo = weighmentPassNo;
//		}
//
//		public BigDecimal getTareWeight() {
//			return tareWeight;
//		}
//
//		public void setTareWeight(BigDecimal tareWeight) {
//			this.tareWeight = tareWeight;
//		}
//
//		public BigDecimal getAreaReleased() {
//			return areaReleased;
//		}
//
//		public void setAreaReleased(BigDecimal areaReleased) {
//			this.areaReleased = areaReleased;
//		}
//
//		public String getGenSetRequired() {
//			return genSetRequired;
//		}
//
//		public void setGenSetRequired(String genSetRequired) {
//			this.genSetRequired = genSetRequired;
//		}
//
//		public String getHaz() {
//			return haz;
//		}
//
//		public void setHaz(String haz) {
//			this.haz = haz;
//		}
//
//		public String getImoCode() {
//			return imoCode;
//		}
//
//		public void setImoCode(String imoCode) {
//			this.imoCode = imoCode;
//		}
//
//		public String getContainerInvoiceType() {
//			return containerInvoiceType;
//		}
//
//		public void setContainerInvoiceType(String containerInvoiceType) {
//			this.containerInvoiceType = containerInvoiceType;
//		}
//
//		public String getItem() {
//			return item;
//		}
//
//		public void setItem(String item) {
//			this.item = item;
//		}
//
//		public String getShippingAgent() {
//			return shippingAgent;
//		}
//
//		public void setShippingAgent(String shippingAgent) {
//			this.shippingAgent = shippingAgent;
//		}
//
//		public String getShippingLine() {
//			return shippingLine;
//		}
//
//		public void setShippingLine(String shippingLine) {
//			this.shippingLine = shippingLine;
//		}
//
//		public String getCommodity() {
//			return commodity;
//		}
//
//		public void setCommodity(String commodity) {
//			this.commodity = commodity;
//		}
//
//		public String getCustomsSealNo() {
//			return customsSealNo;
//		}
//
//		public void setCustomsSealNo(String customsSealNo) {
//			this.customsSealNo = customsSealNo;
//		}
//
//		public String getViaNo() {
//			return viaNo;
//		}
//
//		public void setViaNo(String viaNo) {
//			this.viaNo = viaNo;
//		}
//
//		public Date getCartingDate() {
//			return cartingDate;
//		}
//
//		public void setCartingDate(Date cartingDate) {
//			this.cartingDate = cartingDate;
//		}
//
//		public String getIcdHub() {
//			return icdHub;
//		}
//
//		public void setIcdHub(String icdHub) {
//			this.icdHub = icdHub;
//		}
//
//		public String getExporterName() {
//			return exporterName;
//		}
//
//		public void setExporterName(String exporterName) {
//			this.exporterName = exporterName;
//		}
//
//		public String getConsignee() {
//			return consignee;
//		}
//
//		public void setConsignee(String consignee) {
//			this.consignee = consignee;
//		}
//
//		public BigDecimal getFob() {
//			return fob;
//		}
//
//		public void setFob(BigDecimal fob) {
//			this.fob = fob;
//		}
//
//		public String getCoverDetails() {
//			return coverDetails;
//		}
//
//		public void setCoverDetails(String coverDetails) {
//			this.coverDetails = coverDetails;
//		}
//
//		public Date getCoverDate() {
//			return coverDate;
//		}
//
//		public void setCoverDate(Date coverDate) {
//			this.coverDate = coverDate;
//		}
//
//		public String getHoldingAgent() {
//			return holdingAgent;
//		}
//
//		public void setHoldingAgent(String holdingAgent) {
//			this.holdingAgent = holdingAgent;
//		}
//
//		public String getHoldingAgentName() {
//			return holdingAgentName;
//		}
//
//		public void setHoldingAgentName(String holdingAgentName) {
//			this.holdingAgentName = holdingAgentName;
//		}
//
//		public Date getHoldDate() {
//			return holdDate;
//		}
//
//		public void setHoldDate(Date holdDate) {
//			this.holdDate = holdDate;
//		}
//
//		public Date getReleaseDate() {
//			return releaseDate;
//		}
//
//		public void setReleaseDate(Date releaseDate) {
//			this.releaseDate = releaseDate;
//		}
//
//		public String getHoldRemarks() {
//			return holdRemarks;
//		}
//
//		public void setHoldRemarks(String holdRemarks) {
//			this.holdRemarks = holdRemarks;
//		}
//
//		public String getClpStatus() {
//			return clpStatus;
//		}
//
//		public void setClpStatus(String clpStatus) {
//			this.clpStatus = clpStatus;
//		}
//
//		public String getClpCreatedBy() {
//			return clpCreatedBy;
//		}
//
//		public void setClpCreatedBy(String clpCreatedBy) {
//			this.clpCreatedBy = clpCreatedBy;
//		}
//
//		public Date getClpCreatedDate() {
//			return clpCreatedDate;
//		}
//
//		public void setClpCreatedDate(Date clpCreatedDate) {
//			this.clpCreatedDate = clpCreatedDate;
//		}
//
//		public String getClpApprovedBy() {
//			return clpApprovedBy;
//		}
//
//		public void setClpApprovedBy(String clpApprovedBy) {
//			this.clpApprovedBy = clpApprovedBy;
//		}
//
//		public Date getClpApprovedDate() {
//			return clpApprovedDate;
//		}
//
//		public void setClpApprovedDate(Date clpApprovedDate) {
//			this.clpApprovedDate = clpApprovedDate;
//		}
//
//		public String getGatePassNo() {
//			return gatePassNo;
//		}
//
//		public void setGatePassNo(String gatePassNo) {
//			this.gatePassNo = gatePassNo;
//		}
//
//		public String getGateOutId() {
//			return gateOutId;
//		}
//
//		public void setGateOutId(String gateOutId) {
//			this.gateOutId = gateOutId;
//		}
//
//		public Date getGateOutDate() {
//			return gateOutDate;
//		}
//
//		public void setGateOutDate(Date gateOutDate) {
//			this.gateOutDate = gateOutDate;
//		}
//
//		public Date getBerthingDate() {
//			return berthingDate;
//		}
//
//		public void setBerthingDate(Date berthingDate) {
//			this.berthingDate = berthingDate;
//		}
//
//		public Date getGateOpenDate() {
//			return gateOpenDate;
//		}
//
//		public void setGateOpenDate(Date gateOpenDate) {
//			this.gateOpenDate = gateOpenDate;
//		}
//
//		public String getSealType() {
//			return sealType;
//		}
//
//		public void setSealType(String sealType) {
//			this.sealType = sealType;
//		}
//
//		public String getSealDev() {
//			return sealDev;
//		}
//
//		public void setSealDev(String sealDev) {
//			this.sealDev = sealDev;
//		}
//
//		public String getDocType() {
//			return docType;
//		}
//
//		public void setDocType(String docType) {
//			this.docType = docType;
//		}
//
//		public String getDocNo() {
//			return docNo;
//		}
//
//		public void setDocNo(String docNo) {
//			this.docNo = docNo;
//		}
//
//		public String getStatus() {
//			return status;
//		}
//
//		public void setStatus(String status) {
//			this.status = status;
//		}
//
//		public String getCreatedBy() {
//			return createdBy;
//		}
//
//		public void setCreatedBy(String createdBy) {
//			this.createdBy = createdBy;
//		}
//
//		public Date getCreatedDate() {
//			return createdDate;
//		}
//
//		public void setCreatedDate(Date createdDate) {
//			this.createdDate = createdDate;
//		}
//
//		public String getEditedBy() {
//			return editedBy;
//		}
//
//		public void setEditedBy(String editedBy) {
//			this.editedBy = editedBy;
//		}
//
//		public Date getEditedDate() {
//			return editedDate;
//		}
//
//		public void setEditedDate(Date editedDate) {
//			this.editedDate = editedDate;
//		}
//
//		public String getApprovedBy() {
//			return approvedBy;
//		}
//
//		public void setApprovedBy(String approvedBy) {
//			this.approvedBy = approvedBy;
//		}
//
//		public Date getApprovedDate() {
//			return approvedDate;
//		}
//
//		public void setApprovedDate(Date approvedDate) {
//			this.approvedDate = approvedDate;
//		}
//
//		public String getClpConfirmStatus() {
//			return clpConfirmStatus;
//		}
//
//		public void setClpConfirmStatus(String clpConfirmStatus) {
//			this.clpConfirmStatus = clpConfirmStatus;
//		}
//
//		public String getClpConfirmBy() {
//			return clpConfirmBy;
//		}
//
//		public void setClpConfirmBy(String clpConfirmBy) {
//			this.clpConfirmBy = clpConfirmBy;
//		}
//
//		public Date getClpConfirmDate() {
//			return clpConfirmDate;
//		}
//
//		public void setClpConfirmDate(Date clpConfirmDate) {
//			this.clpConfirmDate = clpConfirmDate;
//		}
//
//		public String getClpPcsStatus() {
//			return clpPcsStatus;
//		}
//
//		public void setClpPcsStatus(String clpPcsStatus) {
//			this.clpPcsStatus = clpPcsStatus;
//		}
//
//		public String getClpPcsMsgCreStatus() {
//			return clpPcsMsgCreStatus;
//		}
//
//		public void setClpPcsMsgCreStatus(String clpPcsMsgCreStatus) {
//			this.clpPcsMsgCreStatus = clpPcsMsgCreStatus;
//		}
//
//		public Date getClpPcsMsgCreDate() {
//			return clpPcsMsgCreDate;
//		}
//
//		public void setClpPcsMsgCreDate(Date clpPcsMsgCreDate) {
//			this.clpPcsMsgCreDate = clpPcsMsgCreDate;
//		}
//
//		public Date getClpPcsMsgAmdCreDate() {
//			return clpPcsMsgAmdCreDate;
//		}
//
//		public void setClpPcsMsgAmdCreDate(Date clpPcsMsgAmdCreDate) {
//			this.clpPcsMsgAmdCreDate = clpPcsMsgAmdCreDate;
//		}
//
//		public BigDecimal getDocumentNumber() {
//			return documentNumber;
//		}
//
//		public void setDocumentNumber(BigDecimal documentNumber) {
//			this.documentNumber = documentNumber;
//		}
//
//		public BigDecimal getCommonReferenceNumber() {
//			return commonReferenceNumber;
//		}
//
//		public void setCommonReferenceNumber(BigDecimal commonReferenceNumber) {
//			this.commonReferenceNumber = commonReferenceNumber;
//		}
//
//		public BigDecimal getAmdDocumentNumber() {
//			return amdDocumentNumber;
//		}
//
//		public void setAmdDocumentNumber(BigDecimal amdDocumentNumber) {
//			this.amdDocumentNumber = amdDocumentNumber;
//		}
//
//		public BigDecimal getAmdCommonReferenceNumber() {
//			return amdCommonReferenceNumber;
//		}
//
//		public void setAmdCommonReferenceNumber(BigDecimal amdCommonReferenceNumber) {
//			this.amdCommonReferenceNumber = amdCommonReferenceNumber;
//		}
//
//		public String getSfJobNo() {
//			return sfJobNo;
//		}
//
//		public void setSfJobNo(String sfJobNo) {
//			this.sfJobNo = sfJobNo;
//		}
//
//		public Date getSfJobDate() {
//			return sfJobDate;
//		}
//
//		public void setSfJobDate(Date sfJobDate) {
//			this.sfJobDate = sfJobDate;
//		}
//
//		public String getAsrJobNo() {
//			return asrJobNo;
//		}
//
//		public void setAsrJobNo(String asrJobNo) {
//			this.asrJobNo = asrJobNo;
//		}
//
//		public Date getAsrJobDate() {
//			return asrJobDate;
//		}
//
//		public void setAsrJobDate(Date asrJobDate) {
//			this.asrJobDate = asrJobDate;
//		}
//
//		public String getCimNo() {
//			return cimNo;
//		}
//
//		public void setCimNo(String cimNo) {
//			this.cimNo = cimNo;
//		}
//
//		public Date getCimDate() {
//			return cimDate;
//		}
//
//		public void setCimDate(Date cimDate) {
//			this.cimDate = cimDate;
//		}
//
//		public String getBondNo() {
//			return bondNo;
//		}
//
//		public void setBondNo(String bondNo) {
//			this.bondNo = bondNo;
//		}
//
//		public String getDpJobNo() {
//			return dpJobNo;
//		}
//
//		public void setDpJobNo(String dpJobNo) {
//			this.dpJobNo = dpJobNo;
//		}
//
//		public Date getDpJobDate() {
//			return dpJobDate;
//		}
//
//		public void setDpJobDate(Date dpJobDate) {
//			this.dpJobDate = dpJobDate;
//		}
//
//		public String getDtJobNo() {
//			return dtJobNo;
//		}
//
//		public void setDtJobNo(String dtJobNo) {
//			this.dtJobNo = dtJobNo;
//		}
//
//		public Date getDtJobDate() {
//			return dtJobDate;
//		}
//
//		public void setDtJobDate(Date dtJobDate) {
//			this.dtJobDate = dtJobDate;
//		}
//
//		public int getFromPkg() {
//			return fromPkg;
//		}
//
//		public void setFromPkg(int fromPkg) {
//			this.fromPkg = fromPkg;
//		}
//
//		public int getToPkg() {
//			return toPkg;
//		}
//
//		public void setToPkg(int toPkg) {
//			this.toPkg = toPkg;
//		}
//
//		public String getIsSfAck() {
//			return isSfAck;
//		}
//
//		public void setIsSfAck(String isSfAck) {
//			this.isSfAck = isSfAck;
//		}
//
//		public String getAckSfStatus() {
//			return ackSfStatus;
//		}
//
//		public void setAckSfStatus(String ackSfStatus) {
//			this.ackSfStatus = ackSfStatus;
//		}
//
//		public String getIsAsrAck() {
//			return isAsrAck;
//		}
//
//		public void setIsAsrAck(String isAsrAck) {
//			this.isAsrAck = isAsrAck;
//		}
//
//		public String getAckAsrStatus() {
//			return ackAsrStatus;
//		}
//
//		public void setAckAsrStatus(String ackAsrStatus) {
//			this.ackAsrStatus = ackAsrStatus;
//		}
//
//		public String getIsDpAck() {
//			return isDpAck;
//		}
//
//		public void setIsDpAck(String isDpAck) {
//			this.isDpAck = isDpAck;
//		}
//
//		public String getAckDpStatus() {
//			return ackDpStatus;
//		}
//
//		public void setAckDpStatus(String ackDpStatus) {
//			this.ackDpStatus = ackDpStatus;
//		}
//
//		public String getIsDtAck() {
//			return isDtAck;
//		}
//
//		public void setIsDtAck(String isDtAck) {
//			this.isDtAck = isDtAck;
//		}
//
//		public String getAckDtStatus() {
//			return ackDtStatus;
//		}
//
//		public void setAckDtStatus(String ackDtStatus) {
//			this.ackDtStatus = ackDtStatus;
//		}
//
//		public String getIsSfCancelStatus() {
//			return isSfCancelStatus;
//		}
//
//		public void setIsSfCancelStatus(String isSfCancelStatus) {
//			this.isSfCancelStatus = isSfCancelStatus;
//		}
//
//		public Date getSfCancelCreatedDate() {
//			return sfCancelCreatedDate;
//		}
//
//		public void setSfCancelCreatedDate(Date sfCancelCreatedDate) {
//			this.sfCancelCreatedDate = sfCancelCreatedDate;
//		}
//
//		public Date getSfCancelAckDate() {
//			return sfCancelAckDate;
//		}
//
//		public void setSfCancelAckDate(Date sfCancelAckDate) {
//			this.sfCancelAckDate = sfCancelAckDate;
//		}
//
//		public String getIsSfCancelDesc() {
//			return isSfCancelDesc;
//		}
//
//		public void setIsSfCancelDesc(String isSfCancelDesc) {
//			this.isSfCancelDesc = isSfCancelDesc;
//		}
//
//		public String getOthPartyId() {
//			return othPartyId;
//		}
//
//		public void setOthPartyId(String othPartyId) {
//			this.othPartyId = othPartyId;
//		}
//
//		public String getInvoiceAssesed() {
//			return invoiceAssesed;
//		}
//
//		public void setInvoiceAssesed(String invoiceAssesed) {
//			this.invoiceAssesed = invoiceAssesed;
//		}
//
//		public String getAssesmentId() {
//			return assesmentId;
//		}
//
//		public void setAssesmentId(String assesmentId) {
//			this.assesmentId = assesmentId;
//		}
//
//		public String getInvoiceNo() {
//			return invoiceNo;
//		}
//
//		public void setInvoiceNo(String invoiceNo) {
//			this.invoiceNo = invoiceNo;
//		}
//
//		public Date getInvoiceDate() {
//			return invoiceDate;
//		}
//
//		public void setInvoiceDate(Date invoiceDate) {
//			this.invoiceDate = invoiceDate;
//		}
//
//		public String getCreditType() {
//			return creditType;
//		}
//
//		public void setCreditType(String creditType) {
//			this.creditType = creditType;
//		}
//
//		public String getInvoiceCategory() {
//			return invoiceCategory;
//		}
//
//		public void setInvoiceCategory(String invoiceCategory) {
//			this.invoiceCategory = invoiceCategory;
//		}
//
//		public BigDecimal getBillAmt() {
//			return billAmt;
//		}
//
//		public void setBillAmt(BigDecimal billAmt) {
//			this.billAmt = billAmt;
//		}
//
//		public BigDecimal getInvoiceAmt() {
//			return invoiceAmt;
//		}
//
//		public void setInvoiceAmt(BigDecimal invoiceAmt) {
//			this.invoiceAmt = invoiceAmt;
//		}
//
//		public String getBackToTownRemark() {
//			return backToTownRemark;
//		}
//
//		public void setBackToTownRemark(String backToTownRemark) {
//			this.backToTownRemark = backToTownRemark;
//		}
//
//		public String getStuffTallyFlag() {
//			return stuffTallyFlag;
//		}
//
//		public void setStuffTallyFlag(String stuffTallyFlag) {
//			this.stuffTallyFlag = stuffTallyFlag;
//		}
//
//		public String getStuffTallyWoTransId() {
//			return stuffTallyWoTransId;
//		}
//
//		public void setStuffTallyWoTransId(String stuffTallyWoTransId) {
//			this.stuffTallyWoTransId = stuffTallyWoTransId;
//		}
//
//		public Date getStuffTallyCutWoTransDate() {
//			return stuffTallyCutWoTransDate;
//		}
//
//		public void setStuffTallyCutWoTransDate(Date stuffTallyCutWoTransDate) {
//			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
//		}
//
//		public String getSsrTransId() {
//			return ssrTransId;
//		}
//
//		public void setSsrTransId(String ssrTransId) {
//			this.ssrTransId = ssrTransId;
//		}
//
//		public BigDecimal getNopGrossWeight() {
//			return nopGrossWeight;
//		}
//
//		public void setNopGrossWeight(BigDecimal nopGrossWeight) {
//			this.nopGrossWeight = nopGrossWeight;
//		}
//
//		public String getDeliveryOrderNo() {
//			return deliveryOrderNo;
//		}
//
//		public void setDeliveryOrderNo(String deliveryOrderNo) {
//			this.deliveryOrderNo = deliveryOrderNo;
//		}
//
//		public String getReworkFlag() {
//			return reworkFlag;
//		}
//
//		public void setReworkFlag(String reworkFlag) {
//			this.reworkFlag = reworkFlag;
//		}
//
//		public String getReworkId() {
//			return reworkId;
//		}
//
//		public void setReworkId(String reworkId) {
//			this.reworkId = reworkId;
//		}
//
//		public Date getReworkDate() {
//			return reworkDate;
//		}
//
//		public void setReworkDate(Date reworkDate) {
//			this.reworkDate = reworkDate;
//		}
//
//		public BigDecimal getPayLoad() {
//			return payLoad;
//		}
//
//		public void setPayLoad(BigDecimal payLoad) {
//			this.payLoad = payLoad;
//		}
//
//		public String getStuffMode() {
//			return stuffMode;
//		}
//
//		public void setStuffMode(String stuffMode) {
//			this.stuffMode = stuffMode;
//		}
//
//		public String getFormThirteenEntryFlag() {
//			return formThirteenEntryFlag;
//		}
//
//		public void setFormThirteenEntryFlag(String formThirteenEntryFlag) {
//			this.formThirteenEntryFlag = formThirteenEntryFlag;
//		}
//
//		public String getFormThirteenEntryRemarks() {
//			return formThirteenEntryRemarks;
//		}
//
//		public void setFormThirteenEntryRemarks(String formThirteenEntryRemarks) {
//			this.formThirteenEntryRemarks = formThirteenEntryRemarks;
//		}
//
//		public Date getFormThirteenEntryDate() {
//			return formThirteenEntryDate;
//		}
//
//		public void setFormThirteenEntryDate(Date formThirteenEntryDate) {
//			this.formThirteenEntryDate = formThirteenEntryDate;
//		}
//
//		public String getFormThirteenEntryUser() {
//			return formThirteenEntryUser;
//		}
//
//		public void setFormThirteenEntryUser(String formThirteenEntryUser) {
//			this.formThirteenEntryUser = formThirteenEntryUser;
//		}
//
//		public BigDecimal getCalGrossWt() {
//			return calGrossWt;
//		}
//
//		public void setCalGrossWt(BigDecimal calGrossWt) {
//			this.calGrossWt = calGrossWt;
//		}
//
//		public int getStuffLineId() {
//			return stuffLineId;
//		}
//
//		public void setStuffLineId(int stuffLineId) {
//			this.stuffLineId = stuffLineId;
//		}
//
//		public String getEqActivityFlag() {
//			return eqActivityFlag;
//		}
//
//		public void setEqActivityFlag(String eqActivityFlag) {
//			this.eqActivityFlag = eqActivityFlag;
//		}
//
//
//
//
//
//
//
//		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
//				int stuffTallyLineId, String profitcentreId, String sbLineId, String sbNo, Date stuffTallyDate,
//				String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
//				String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
//				String finalPod, String containerNo, String containerStatus, Date periodFrom, String containerSize,
//				String containerType, String containerCondition, BigDecimal yardPackages, BigDecimal cellAreaAllocated,
//				String onAccountOf, String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty,
//				BigDecimal balanceQty,BigDecimal balQty, BigDecimal cargoWeight, BigDecimal totalGrossWeight, BigDecimal tareWeight,
//				BigDecimal areaReleased, String genSetRequired, String haz, String shippingAgent, String shippingLine,
//				String commodity, String customsSealNo, String viaNo, String exporterName, String consignee,
//				BigDecimal fob, Date berthingDate, Date gateOpenDate, String sealType, String docType, String docNo,
//				String status, String createdBy, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate,
//				String deliveryOrderNo, String stuffMode, String typeOfPackage, String vesselName, String chaName, BigDecimal totalCargoWeight) {
//			this.companyId = companyId;
//			this.branchId = branchId;
//			this.stuffTallyId = stuffTallyId;
//			this.sbTransId = sbTransId;
//			this.stuffTallyLineId = stuffTallyLineId;
//			this.profitcentreId = profitcentreId;
//			this.sbLineId = sbLineId;
//			this.sbNo = sbNo;
//			this.stuffTallyDate = stuffTallyDate;
//			this.stuffId = stuffId;
//			this.stuffDate = stuffDate;
//			this.sbDate = sbDate;
//			this.shift = shift;
//			this.agentSealNo = agentSealNo;
//			this.vesselId = vesselId;
//			this.voyageNo = voyageNo;
//			this.rotationNo = rotationNo;
//			this.rotationDate = rotationDate;
//			this.pol = pol;
//			this.terminal = terminal;
//			this.pod = pod;
//			this.finalPod = finalPod;
//			this.containerNo = containerNo;
//			this.containerStatus = containerStatus;
//			this.periodFrom = periodFrom;
//			this.containerSize = containerSize;
//			this.containerType = containerType;
//			this.containerCondition = containerCondition;
//			this.yardPackages = yardPackages;
//			this.cellAreaAllocated = cellAreaAllocated;
//			this.onAccountOf = onAccountOf;
//			this.cha = cha;
//			this.stuffRequestQty = stuffRequestQty;
//			this.stuffedQty = stuffedQty;
//			this.balanceQty = balanceQty;
//			this.balQty = balQty;
//			this.cargoWeight = cargoWeight;
//			this.totalGrossWeight = totalGrossWeight;
//			this.tareWeight = tareWeight;
//			this.areaReleased = areaReleased;
//			this.genSetRequired = genSetRequired;
//			this.haz = haz;
//			this.shippingAgent = shippingAgent;
//			this.shippingLine = shippingLine;
//			this.commodity = commodity;
//			this.customsSealNo = customsSealNo;
//			this.viaNo = viaNo;
//			this.exporterName = exporterName;
//			this.consignee = consignee;
//			this.fob = fob;
//			this.berthingDate = berthingDate;
//			this.gateOpenDate = gateOpenDate;
//			this.sealType = sealType;
//			this.docType = docType;
//			this.docNo = docNo;
//			this.status = status;
//			this.createdBy = createdBy;
//			this.stuffTallyWoTransId = stuffTallyWoTransId;
//			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
//			this.deliveryOrderNo = deliveryOrderNo;
//			this.stuffMode = stuffMode;
//			this.typeOfPackage = typeOfPackage;
//			this.vesselName = vesselName;
//			this.chaName = chaName;
//			this.totalCargoWeight = totalCargoWeight;
//		}
//
//
//
//
//
//
//
//		public ExportStuffTally(String stuffTallyId, String sbTransId, String sbNo, String movementType,
//				Date stuffTallyDate, Date sbDate, String agentSealNo, String vesselId, String voyageNo,
//				String rotationNo, Date rotationDate, String terminal, String pod, String finalPod, String containerNo,String stuffId,
//				String gateInId, String containerSize, String containerType, String onAccountOf, String cha,
//				BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal balanceQty,BigDecimal cargoWeight, BigDecimal totalCargoWeight,
//				BigDecimal tareWeight, String shippingAgent, String shippingLine, String commodity,
//				String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
//				String status, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate, String deliveryOrderNo,
//				String stuffMode, String vesselName, BigDecimal sbPackages, BigDecimal stuffedQuantity, BigDecimal sbWt,
//				Date gateInDate, String cargoType, Date berthingDate, String reworkFlag) {
//			this.stuffTallyId = stuffTallyId;
//			this.sbTransId = sbTransId;
//			this.sbNo = sbNo;
//			this.movementType = movementType;
//			this.stuffTallyDate = stuffTallyDate;
//			this.sbDate = sbDate;
//			this.agentSealNo = agentSealNo;
//			this.vesselId = vesselId;
//			this.voyageNo = voyageNo;
//			this.rotationNo = rotationNo;
//			this.rotationDate = rotationDate;
//			this.terminal = terminal;
//			this.pod = pod;
//			this.finalPod = finalPod;
//			this.containerNo = containerNo;
//			this.stuffId = stuffId;
//			this.gateInId = gateInId;
//			this.containerSize = containerSize;
//			this.containerType = containerType;
//			this.onAccountOf = onAccountOf;
//			this.cha = cha;
//			this.stuffRequestQty = stuffRequestQty;
//			this.stuffedQty = stuffedQty;
//			this.balanceQty = balanceQty;
//			this.cargoWeight = cargoWeight;
//			this.totalCargoWeight = totalCargoWeight;
//			this.tareWeight = tareWeight;
//			this.shippingAgent = shippingAgent;
//			this.shippingLine = shippingLine;
//			this.commodity = commodity;
//			this.customsSealNo = customsSealNo;
//			this.viaNo = viaNo;
//			this.exporterName = exporterName;
//			this.consignee = consignee;
//			this.fob = fob;
//			this.status = status;
//			this.stuffTallyWoTransId = stuffTallyWoTransId;
//			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
//			this.deliveryOrderNo = deliveryOrderNo;
//			this.stuffMode = stuffMode;
//			this.vesselName = vesselName;
//			this.sbPackages = sbPackages;
//			this.stuffedQuantity = stuffedQuantity;
//			this.sbWt = sbWt;
//			this.gateInDate = gateInDate;
//			this.cargoType = cargoType;
//			this.berthingDate = berthingDate;
//			this.reworkFlag = reworkFlag;
//		}
//
//
//
//
//
//
//
//		@Override
//		public String toString() {
//			return "ExportStuffTally [companyId=" + companyId + ", branchId=" + branchId + ", stuffTallyId="
//					+ stuffTallyId + ", sbTransId=" + sbTransId + ", stuffTallyLineId=" + stuffTallyLineId
//					+ ", profitcentreId=" + profitcentreId + ", cartingTransId=" + cartingTransId + ", sbLineId="
//					+ sbLineId + ", cartingLineId=" + cartingLineId + ", sbNo=" + sbNo + ", movementReqId="
//					+ movementReqId + ", movementType=" + movementType + ", stuffTallyDate=" + stuffTallyDate
//					+ ", stuffId=" + stuffId + ", stuffDate=" + stuffDate + ", sbDate=" + sbDate + ", shift=" + shift
//					+ ", agentSealNo=" + agentSealNo + ", vesselId=" + vesselId + ", voyageNo=" + voyageNo
//					+ ", rotationNo=" + rotationNo + ", rotationDate=" + rotationDate + ", pol=" + pol + ", terminal="
//					+ terminal + ", pod=" + pod + ", finalPod=" + finalPod + ", containerNo=" + containerNo
//					+ ", containerStatus=" + containerStatus + ", asrContainerStatus=" + asrContainerStatus
//					+ ", currentLocation=" + currentLocation + ", periodFrom=" + periodFrom + ", gateInId=" + gateInId
//					+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", containerCondition="
//					+ containerCondition + ", crgYardLocation=" + crgYardLocation + ", crgYardBlock=" + crgYardBlock
//					+ ", crgBlockCellNo=" + crgBlockCellNo + ", yardLocation=" + yardLocation + ", yardBlock="
//					+ yardBlock + ", blockCellNo=" + blockCellNo + ", yardLocation1=" + yardLocation1 + ", yardBlock1="
//					+ yardBlock1 + ", blockCellNo1=" + blockCellNo1 + ", yardPackages=" + yardPackages
//					+ ", cellAreaAllocated=" + cellAreaAllocated + ", onAccountOf=" + onAccountOf + ", cha=" + cha
//					+ ", stuffRequestQty=" + stuffRequestQty + ", stuffedQty=" + stuffedQty + ", prvStuffedQty="
//					+ prvStuffedQty + ", balanceQty=" + balanceQty + ", cargoWeight=" + cargoWeight
//					+ ", totalCargoWeight=" + totalCargoWeight + ", totalGrossWeight=" + totalGrossWeight
//					+ ", grossWeight=" + grossWeight + ", weighmentFlag=" + weighmentFlag + ", weighmentDone="
//					+ weighmentDone + ", weighmentDate=" + weighmentDate + ", weighmentWeight=" + weighmentWeight
//					+ ", weighmentPassNo=" + weighmentPassNo + ", tareWeight=" + tareWeight + ", areaReleased="
//					+ areaReleased + ", genSetRequired=" + genSetRequired + ", haz=" + haz + ", imoCode=" + imoCode
//					+ ", containerInvoiceType=" + containerInvoiceType + ", item=" + item + ", shippingAgent="
//					+ shippingAgent + ", shippingLine=" + shippingLine + ", commodity=" + commodity + ", customsSealNo="
//					+ customsSealNo + ", viaNo=" + viaNo + ", cartingDate=" + cartingDate + ", icdHub=" + icdHub
//					+ ", exporterName=" + exporterName + ", consignee=" + consignee + ", fob=" + fob + ", coverDetails="
//					+ coverDetails + ", coverDate=" + coverDate + ", holdingAgent=" + holdingAgent
//					+ ", holdingAgentName=" + holdingAgentName + ", holdDate=" + holdDate + ", releaseDate="
//					+ releaseDate + ", holdRemarks=" + holdRemarks + ", clpStatus=" + clpStatus + ", clpCreatedBy="
//					+ clpCreatedBy + ", clpCreatedDate=" + clpCreatedDate + ", clpApprovedBy=" + clpApprovedBy
//					+ ", clpApprovedDate=" + clpApprovedDate + ", gatePassNo=" + gatePassNo + ", gateOutId=" + gateOutId
//					+ ", gateOutDate=" + gateOutDate + ", berthingDate=" + berthingDate + ", gateOpenDate="
//					+ gateOpenDate + ", sealType=" + sealType + ", sealDev=" + sealDev + ", docType=" + docType
//					+ ", docNo=" + docNo + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
//					+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy="
//					+ approvedBy + ", approvedDate=" + approvedDate + ", clpConfirmStatus=" + clpConfirmStatus
//					+ ", clpConfirmBy=" + clpConfirmBy + ", clpConfirmDate=" + clpConfirmDate + ", clpPcsStatus="
//					+ clpPcsStatus + ", clpPcsMsgCreStatus=" + clpPcsMsgCreStatus + ", clpPcsMsgCreDate="
//					+ clpPcsMsgCreDate + ", clpPcsMsgAmdCreDate=" + clpPcsMsgAmdCreDate + ", documentNumber="
//					+ documentNumber + ", commonReferenceNumber=" + commonReferenceNumber + ", amdDocumentNumber="
//					+ amdDocumentNumber + ", amdCommonReferenceNumber=" + amdCommonReferenceNumber + ", sfJobNo="
//					+ sfJobNo + ", sfJobDate=" + sfJobDate + ", asrJobNo=" + asrJobNo + ", asrJobDate=" + asrJobDate
//					+ ", cimNo=" + cimNo + ", cimDate=" + cimDate + ", bondNo=" + bondNo + ", dpJobNo=" + dpJobNo
//					+ ", dpJobDate=" + dpJobDate + ", dtJobNo=" + dtJobNo + ", dtJobDate=" + dtJobDate + ", fromPkg="
//					+ fromPkg + ", toPkg=" + toPkg + ", isSfAck=" + isSfAck + ", ackSfStatus=" + ackSfStatus
//					+ ", isAsrAck=" + isAsrAck + ", ackAsrStatus=" + ackAsrStatus + ", isDpAck=" + isDpAck
//					+ ", ackDpStatus=" + ackDpStatus + ", isDtAck=" + isDtAck + ", ackDtStatus=" + ackDtStatus
//					+ ", isSfCancelStatus=" + isSfCancelStatus + ", sfCancelCreatedDate=" + sfCancelCreatedDate
//					+ ", sfCancelAckDate=" + sfCancelAckDate + ", isSfCancelDesc=" + isSfCancelDesc + ", othPartyId="
//					+ othPartyId + ", invoiceAssesed=" + invoiceAssesed + ", assesmentId=" + assesmentId
//					+ ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", creditType=" + creditType
//					+ ", invoiceCategory=" + invoiceCategory + ", billAmt=" + billAmt + ", invoiceAmt=" + invoiceAmt
//					+ ", backToTownRemark=" + backToTownRemark + ", stuffTallyFlag=" + stuffTallyFlag
//					+ ", stuffTallyWoTransId=" + stuffTallyWoTransId + ", stuffTallyCutWoTransDate="
//					+ stuffTallyCutWoTransDate + ", ssrTransId=" + ssrTransId + ", nopGrossWeight=" + nopGrossWeight
//					+ ", deliveryOrderNo=" + deliveryOrderNo + ", reworkFlag=" + reworkFlag + ", reworkId=" + reworkId
//					+ ", reworkDate=" + reworkDate + ", payLoad=" + payLoad + ", stuffMode=" + stuffMode
//					+ ", formThirteenEntryFlag=" + formThirteenEntryFlag + ", formThirteenEntryRemarks="
//					+ formThirteenEntryRemarks + ", formThirteenEntryDate=" + formThirteenEntryDate
//					+ ", formThirteenEntryUser=" + formThirteenEntryUser + ", calGrossWt=" + calGrossWt
//					+ ", stuffLineId=" + stuffLineId + ", eqActivityFlag=" + eqActivityFlag + ", typeOfPackage="
//					+ typeOfPackage + ", vesselName=" + vesselName + ", chaName=" + chaName + ", sbPackages="
//					+ sbPackages + ", stuffedQuantity=" + stuffedQuantity + ", sbWt=" + sbWt + ", gateInDate="
//					+ gateInDate + ", cargoType=" + cargoType + "]";
//		}
//	    
//	    
//		
//		@Override
//		public Object clone() throws CloneNotSupportedException {
//		    return super.clone();
//		}
//
//
//
//
//	
//		
//		
////		BufferTally
//		
//		
//		
//
//
//		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
//				int stuffTallyLineId, String profitcentreId, String sbLineId, String sbNo, String movementType,
//				Date stuffTallyDate, Date sbDate, String shift, String agentSealNo, String vesselId, String voyageNo,
//				String rotationNo, Date rotationDate, String pol, String terminal, String pod, String finalPod,
//				String containerNo, String containerStatus, Date periodFrom, String gateInId, String containerSize,
//				String containerType, String containerCondition, String onAccountOf, String cha, BigDecimal stuffedQty,
//				BigDecimal prvStuffedQty, BigDecimal cargoWeight, BigDecimal totalCargoWeight,
//				BigDecimal totalGrossWeight, BigDecimal grossWeight, BigDecimal tareWeight, BigDecimal areaReleased,
//				String haz, String imoCode, String shippingAgent, String shippingLine, String commodity,
//				String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
//				Date berthingDate, Date gateOpenDate, String status, String createdBy, Date createdDate,
//				String editedBy, Date editedDate, String approvedBy, Date approvedDate, String stuffTallyFlag,
//				BigDecimal nopGrossWeight, String deliveryOrderNo, String stuffMode, String typeOfPackage,
//				String vesselName, BigDecimal sbPackages, BigDecimal sbWt,
//				String shippingLineName, String shippingAgentName,String terminalName, String finalPodName) {
//			super();
//			this.companyId = companyId;
//			this.branchId = branchId;
//			this.stuffTallyId = stuffTallyId;
//			this.sbTransId = sbTransId;
//			this.stuffTallyLineId = stuffTallyLineId;
//			this.profitcentreId = profitcentreId;
//			this.sbLineId = sbLineId;
//			this.sbNo = sbNo;
//			this.movementType = movementType;
//			this.stuffTallyDate = stuffTallyDate;
//			this.sbDate = sbDate;
//			this.shift = shift;
//			this.agentSealNo = agentSealNo;
//			this.vesselId = vesselId;
//			this.voyageNo = voyageNo;
//			this.rotationNo = rotationNo;
//			this.rotationDate = rotationDate;
//			this.pol = pol;
//			this.terminal = terminal;
//			this.pod = pod;
//			this.finalPod = finalPod;
//			this.containerNo = containerNo;
//			this.containerStatus = containerStatus;
//			this.periodFrom = periodFrom;
//			this.gateInId = gateInId;
//			this.containerSize = containerSize;
//			this.containerType = containerType;
//			this.containerCondition = containerCondition;
//			this.onAccountOf = onAccountOf;
//			this.cha = cha;
//			this.stuffedQty = stuffedQty;
//			this.prvStuffedQty = prvStuffedQty;
//			this.cargoWeight = cargoWeight;
//			this.totalCargoWeight = totalCargoWeight;
//			this.totalGrossWeight = totalGrossWeight;
//			this.grossWeight = grossWeight;
//			this.tareWeight = tareWeight;
//			this.areaReleased = areaReleased;
//			this.haz = haz;
//			this.imoCode = imoCode;
//			this.shippingAgent = shippingAgent;
//			this.shippingLine = shippingLine;
//			this.commodity = commodity;
//			this.customsSealNo = customsSealNo;
//			this.viaNo = viaNo;
//			this.exporterName = exporterName;
//			this.consignee = consignee;
//			this.fob = fob;
//			this.berthingDate = berthingDate;
//			this.gateOpenDate = gateOpenDate;
//			this.status = status;
//			this.createdBy = createdBy;
//			this.createdDate = createdDate;
//			this.editedBy = editedBy;
//			this.editedDate = editedDate;
//			this.approvedBy = approvedBy;
//			this.approvedDate = approvedDate;
//			this.stuffTallyFlag = stuffTallyFlag;
//			this.nopGrossWeight = nopGrossWeight;
//			this.deliveryOrderNo = deliveryOrderNo;
//			this.stuffMode = stuffMode;
//			this.typeOfPackage = typeOfPackage;
//			this.vesselName = vesselName;
//			this.sbPackages = sbPackages;
//			this.sbWt = sbWt;
//			this.shippingLineName = shippingLineName;
//			this.shippingAgentName = shippingAgentName;			
//			this.terminalName = terminalName;
//			this.finalPodName = finalPodName;
//		}
//
//		
//		
//
//
//		// Export Main Search
//
//				public ExportStuffTally(String stuffTallyId, String containerNo, String gateInId, String reworkId) {
//					super();
//					this.stuffTallyId = stuffTallyId;
//					this.containerNo = containerNo;
//					this.gateInId = gateInId;
//					this.reworkId = reworkId;
//					}
//
//
//
//
//
//
//
//				public ExportStuffTally(String stuffTallyId, String sbTransId, int stuffTallyLineId, String sbLineId,
//						String sbNo, String containerNo, String gateInId, String reworkId) {
//					super();
//					this.stuffTallyId = stuffTallyId;
//					this.sbTransId = sbTransId;
//					this.stuffTallyLineId = stuffTallyLineId;
//					this.sbLineId = sbLineId;
//					this.sbNo = sbNo;
//					this.containerNo = containerNo;
//					this.gateInId = gateInId;
//					this.reworkId = reworkId;
//				}
//
//				
//		
//				
////				Stuff created By
//					
//
//					public ExportStuffTally(String stuffTallyId, String sbTransId, String sbNo, String movementType,
//							Date stuffTallyDate, Date sbDate, String agentSealNo, String vesselId, String voyageNo,
//							String rotationNo, Date rotationDate, String terminal, String pod, String finalPod, String containerNo,String stuffId,
//							String gateInId, String containerSize, String containerType, String onAccountOf, String cha,
//							BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal balanceQty,BigDecimal cargoWeight, BigDecimal totalCargoWeight,
//							BigDecimal tareWeight, String shippingAgent, String shippingLine, String commodity,
//							String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
//							String status, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate, String deliveryOrderNo,
//							String stuffMode, String vesselName, BigDecimal sbPackages, BigDecimal stuffedQuantity, BigDecimal sbWt,
//							Date gateInDate, String cargoType, Date berthingDate, String reworkFlag, String createdBy) {
//						this.stuffTallyId = stuffTallyId;
//						this.sbTransId = sbTransId;
//						this.sbNo = sbNo;
//						this.movementType = movementType;
//						this.stuffTallyDate = stuffTallyDate;
//						this.sbDate = sbDate;
//						this.agentSealNo = agentSealNo;
//						this.vesselId = vesselId;
//						this.voyageNo = voyageNo;
//						this.rotationNo = rotationNo;
//						this.rotationDate = rotationDate;
//						this.terminal = terminal;
//						this.pod = pod;
//						this.finalPod = finalPod;
//						this.containerNo = containerNo;
//						this.stuffId = stuffId;
//						this.gateInId = gateInId;
//						this.containerSize = containerSize;
//						this.containerType = containerType;
//						this.onAccountOf = onAccountOf;
//						this.cha = cha;
//						this.stuffRequestQty = stuffRequestQty;
//						this.stuffedQty = stuffedQty;
//						this.balanceQty = balanceQty;
//						this.cargoWeight = cargoWeight;
//						this.totalCargoWeight = totalCargoWeight;
//						this.tareWeight = tareWeight;
//						this.shippingAgent = shippingAgent;
//						this.shippingLine = shippingLine;
//						this.commodity = commodity;
//						this.customsSealNo = customsSealNo;
//						this.viaNo = viaNo;
//						this.exporterName = exporterName;
//						this.consignee = consignee;
//						this.fob = fob;
//						this.status = status;
//						this.stuffTallyWoTransId = stuffTallyWoTransId;
//						this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
//						this.deliveryOrderNo = deliveryOrderNo;
//						this.stuffMode = stuffMode;
//						this.vesselName = vesselName;
//						this.sbPackages = sbPackages;
//						this.stuffedQuantity = stuffedQuantity;
//						this.sbWt = sbWt;
//						this.gateInDate = gateInDate;
//						this.cargoType = cargoType;
//						this.berthingDate = berthingDate;
//						this.reworkFlag = reworkFlag;
//						this.createdBy = createdBy;
//					}
//
//
//
//					
//			
//		    
//		
//		
//	    
//}
//




















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
@Table(name="cfstufftally")
@IdClass(ExportStuffTallyId.class)
public class ExportStuffTally implements Cloneable {
	    @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Stuff_Tally_Id", length = 10)
	    private String stuffTallyId;

	    @Id
	    @Column(name = "SB_Trans_Id", length = 10)
	    private String sbTransId;

	    @Id
	    @Column(name = "Stuff_Tally_Line_Id")
	    private int stuffTallyLineId;

	    @Id
	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;


	    @Id
	    @Column(name = "Carting_Trans_Id", length = 10)
	    private String cartingTransId;

	    @Id
	    @Column(name = "SB_Line_Id", length = 5)
	    private String sbLineId;

	    @Id
	    @Column(name = "Carting_Line_Id", length = 5)
	    private String cartingLineId;

	    @Id
	    @Column(name = "SB_No", length = 15)
	    private String sbNo;
	    
	    @Column(name = "Movement_Req_Id", length = 10)
	    private String movementReqId;

	    @Column(name = "MOVEMENT_TYPE", length = 10)
	    private String movementType;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Stuff_Tally_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date stuffTallyDate;

	    @Column(name = "Stuff_Id", length = 10)
	    private String stuffId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Stuff_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date stuffDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SB_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sbDate;

	    @Column(name = "Shift", length = 6)
	    private String shift;

	    @Column(name = "Agent_Seal_No", length = 15)
	    private String agentSealNo;

	    @Column(name = "Vessel_Id", length = 7)
	    private String vesselId;

	    @Column(name = "Voyage_No", length = 10)
	    private String voyageNo;

	    @Column(name = "Rotation_No", length = 10)
	    private String rotationNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="Rotation_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date rotationDate;
	    
	    @Column(name = "POL", length = 100)
	    private String pol;

	    @Column(name = "Terminal", length = 50)
	    private String terminal;

	    @Column(name = "pod", length = 140)
	    private String pod;

	    @Column(name = "Final_POD", length = 50)
	    private String finalPod;

	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Container_Status", length = 3)
	    private String containerStatus;

	    @Column(name = "ASR_Container_Status", length = 3)
	    private String asrContainerStatus;

	    @Column(name = "Current_Location", length = 26)
	    private String currentLocation;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Period_From")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date periodFrom;
	    
	    @Column(name = "GATE_IN_ID", length = 10)
	    private String gateInId;

	    @Column(name = "Container_Size", length = 6)
	    private String containerSize;

	    @Column(name = "Container_Type", length = 6)
	    private String containerType;

	    @Column(name = "Container_Condition", length = 6)
	    private String containerCondition;

	    @Column(name = "Crg_Yard_Location", length = 10)
	    private String crgYardLocation;

	    @Column(name = "Crg_Yard_Block", length = 10)
	    private String crgYardBlock;

	    @Column(name = "Crg_Block_Cell_No", length = 10)
	    private String crgBlockCellNo;

	    @Column(name = "Yard_Location", length = 10)
	    private String yardLocation;

	    @Column(name = "Yard_Block", length = 10)
	    private String yardBlock;

	    @Column(name = "Block_Cell_No", length = 10)
	    private String blockCellNo;

	    @Column(name = "Yard_Location1", length = 10)
	    private String yardLocation1;

	    @Column(name = "Yard_Block1", length = 10)
	    private String yardBlock1;

	    @Column(name = "Block_Cell_No1", length = 10)
	    private String blockCellNo1;

	    @Column(name = "Yard_Packages", precision = 18, scale = 3)
	    private BigDecimal yardPackages;

	    @Column(name = "Cell_Area_Allocated", precision = 18, scale = 3)
	    private BigDecimal cellAreaAllocated;

	    @Column(name = "On_Account_Of", length = 6)
	    private String onAccountOf;

	    @Column(name = "CHA", length = 10)
	    private String cha;

	    @Column(name = "Stuff_Request_Qty", precision = 8, scale = 0)
	    private BigDecimal stuffRequestQty;

	    @Column(name = "Stuffed_Qty", precision = 8, scale = 0)
	    private BigDecimal stuffedQty;

	    @Column(name = "Prv_Stuffed_Qty", precision = 8, scale = 0)
	    private BigDecimal prvStuffedQty;

	    @Column(name = "Balance_Qty", precision = 8, scale = 0)
	    private BigDecimal balanceQty;

	    @Column(name = "Cargo_weight", precision = 16, scale = 4)
	    private BigDecimal cargoWeight;

	    @Column(name = "Total_Cargo_Wt", precision = 16, scale = 4)
	    private BigDecimal totalCargoWeight;

	    @Column(name = "Total_GW", precision = 16, scale = 4)
	    private BigDecimal totalGrossWeight;

	    @Column(name = "GROSS_WEIGHT", precision = 16, scale = 4)
	    private BigDecimal grossWeight;

	    @Column(name = "Weighment_Flag", length = 1)
	    private String weighmentFlag;

	    @Column(name = "Weighment_Done", length = 1)
	    private String weighmentDone;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Weighment_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date weighmentDate;

	    @Column(name = "Weighment_weight", precision = 16, scale = 3)
	    private BigDecimal weighmentWeight;

	    @Column(name = "Weighment_Pass_No", length = 30)
	    private String weighmentPassNo;
	    
	    @Column(name = "Tare_Weight", precision = 15, scale = 3)
	    private BigDecimal tareWeight;

	    @Column(name = "Area_Released", precision = 8, scale = 3, nullable = true)
	    private BigDecimal areaReleased;

	    @Column(name = "Gen_Set_Required", length = 1)
	    private String genSetRequired;

	    @Column(name = "Haz", length = 1)
	    private String haz;

	    @Column(name = "IMO_Code", length = 10)
	    private String imoCode;

	    @Column(name = "Container_Invoice_Type", length = 10)
	    private String containerInvoiceType;

	    @Column(name = "Item", length = 6)
	    private String item;

	    @Column(name = "Shipping_Agent", length = 6)
	    private String shippingAgent;

	    @Column(name = "Shipping_Line", length = 6)
	    private String shippingLine;

	    @Column(name = "Commodity", length = 250)
	    private String commodity;

	    @Column(name = "Customs_Seal_No", length = 15)
	    private String customsSealNo;

	    @Column(name = "VIA_No", length = 10)
	    private String viaNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Carting_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date cartingDate;

	    @Column(name = "ICD_Hub", length = 35)
	    private String icdHub;

	    @Column(name = "Exporter_Name", length = 100)
	    private String exporterName;

	    @Column(name = "Consignee", length = 60)
	    private String consignee;

	    @Column(name = "FOB", precision = 16, scale = 4)
	    private BigDecimal fob;

	    @Column(name = "Cover_Details", length = 250)
	    private String coverDetails;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Cover_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date coverDate;

	    @Column(name = "Holding_Agent", length = 1)
	    private String holdingAgent;

	    @Column(name = "Holding_Agent_Name", length = 35)
	    private String holdingAgentName;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Hold_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date holdDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Release_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date releaseDate;

	    @Column(name = "Hold_Remarks", length = 150)
	    private String holdRemarks;

	    @Column(name = "CLP_Status", length = 1)
	    private String clpStatus;

	    @Column(name = "CLP_Created_By", length = 10)
	    private String clpCreatedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CLP_Created_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date clpCreatedDate;

	    @Column(name = "CLP_Approved_By", length = 10)
	    private String clpApprovedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CLP_Approved_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date clpApprovedDate;

	    @Column(name = "Gate_Pass_No", length = 10)
	    private String gatePassNo;

	    @Column(name = "Gate_Out_Id", length = 10)
	    private String gateOutId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Gate_Out_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date gateOutDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Berthing_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date berthingDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Gate_Open_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date gateOpenDate;

	    @Column(name = "Seal_Type", length = 5)
	    private String sealType;

	    @Column(name = "Seal_Dev", length = 5)
	    private String sealDev;

	    @Column(name = "Doc_Type", length = 5)
	    private String docType;

	    @Column(name = "Doc_No", length = 5)
	    private String docNo;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Created_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date createdDate;

	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Edited_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date editedDate;

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Approved_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date approvedDate;

	    @Column(name = "CLP_Confirm_Status", length = 1)
	    private String clpConfirmStatus;

	    @Column(name = "CLP_Confirm_By", length = 30)
	    private String clpConfirmBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CLP_Confirm_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date clpConfirmDate;

	    @Column(name = "CLP_PCS_Status", length = 1)
	    private String clpPcsStatus;

	    @Column(name = "CLP_PCS_MSG_CRE_Status", length = 1)
	    private String clpPcsMsgCreStatus;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CLP_PCS_MSG_CRE_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date clpPcsMsgCreDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CLP_PCS_MSG_AMD_CRE_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date clpPcsMsgAmdCreDate;

	    @Column(name = "Document_Number", precision = 16, scale = 0)
	    private BigDecimal documentNumber;

	    @Column(name = "Common_Reference_Number", precision = 16, scale = 0)
	    private BigDecimal commonReferenceNumber;

	    @Column(name = "AMD_Document_Number", precision = 16, scale = 0)
	    private BigDecimal amdDocumentNumber;

	    @Column(name = "AMD_Common_Reference_Number", precision = 16, scale = 0)
	    private BigDecimal amdCommonReferenceNumber;

	    @Column(name = "SF_Job_No", length = 10)
	    private String sfJobNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SF_Job_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sfJobDate;

	    @Column(name = "ASR_Job_No", length = 10)
	    private String asrJobNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "ASR_Job_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date asrJobDate;
	    
	    
	    @Column(name = "CIM_No", length = 10)
	    private String cimNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "CIM_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date cimDate;

	    @Column(name = "Bond_No", length = 10)
	    private String bondNo;

	    @Column(name = "DP_Job_No", length = 10)
	    private String dpJobNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "DP_Job_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date dpJobDate;

	    @Column(name = "DT_Job_No", length = 10)
	    private String dtJobNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "DT_Job_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date dtJobDate;

	    @Column(name = "From_Pkg")
	    private int fromPkg;

	    @Column(name = "To_Pkg")
	    private int toPkg;

	    @Column(name = "Is_SF_Ack", length = 1)
	    private String isSfAck;

	    @Column(name = "Ack_SF_Status", length = 20)
	    private String ackSfStatus;

	    @Column(name = "Is_ASR_Ack", length = 1)
	    private String isAsrAck;

	    @Column(name = "Ack_ASR_Status", length = 20)
	    private String ackAsrStatus;

	    @Column(name = "Is_DP_Ack", length = 1)
	    private String isDpAck;

	    @Column(name = "Ack_DP_Status", length = 20)
	    private String ackDpStatus;

	    @Column(name = "Is_DT_Ack", length = 1)
	    private String isDtAck;

	    @Column(name = "Ack_DT_Status", length = 20)
	    private String ackDtStatus;

	    @Column(name = "Is_SF_Cancel_Status", length = 1)
	    private String isSfCancelStatus;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SF_Cancel_Created_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sfCancelCreatedDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SF_Cancel_Ack_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sfCancelAckDate;

	    @Column(name = "Is_SF_Cancel_Desc", length = 20)
	    private String isSfCancelDesc;

	    @Column(name = "oth_party_Id", length = 10)
	    private String othPartyId;

	    @Column(name = "Invoice_Assesed", length = 1)
	    private String invoiceAssesed;

	    @Column(name = "Assesment_Id", length = 20)
	    private String assesmentId;

	    @Column(name = "Invoice_No", length = 16)
	    private String invoiceNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Invoice_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date invoiceDate;

	    @Column(name = "Credit_Type", length = 1)
	    private String creditType;

	    @Column(name = "Invoice_Category", length = 10)
	    private String invoiceCategory;

	    @Column(name = "Bill_Amt", precision = 12, scale = 2)
	    private BigDecimal billAmt;

	    @Column(name = "Invoice_Amt", precision = 12, scale = 2)
	    private BigDecimal invoiceAmt;

	    @Column(name = "Back_to_town_Remark", length = 250)
	    private String backToTownRemark;

	    @Column(name = "Stuff_Tally_Flag", length = 1)
	    private String stuffTallyFlag;

	    @Column(name = "STUFFTALLY_WO_TRANS_ID", length = 25)
	    private String stuffTallyWoTransId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "STUFFTALLY_CUT_WO_TRANS_DATE")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date stuffTallyCutWoTransDate;

	    @Column(name = "SSR_TRANS_ID", length = 10)
	    private String ssrTransId;

	    @Column(name = "nopGross_weight", precision = 16, scale = 3)
	    private BigDecimal nopGrossWeight;

	    @Column(name = "delivery_order_No", length = 35)
	    private String deliveryOrderNo;

	    @Column(name = "Rework_Flag", length = 1)
	    private String reworkFlag;

	    @Column(name = "Rework_Id", length = 10)
	    private String reworkId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Rework_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date reworkDate;

	    @Column(name = "PAY_LOAD", precision = 16, scale = 3)
	    private BigDecimal payLoad;

	    @Column(name = "stuff_mode", length = 15)
	    private String stuffMode;

	    @Column(name = "Form_Thirteen_Entry_Flag", length = 1)
	    private String formThirteenEntryFlag;

	    @Column(name = "Form_Thirteen_Entry_Remarks", length = 250)
	    private String formThirteenEntryRemarks;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Form_Thirteen_Entry_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date formThirteenEntryDate;

	    @Column(name = "Form_Thirteen_Entry_user", length = 10)
	    private String formThirteenEntryUser;

	    @Column(name = "Cal_Gross_Wt", precision = 16, scale = 3)
	    private BigDecimal calGrossWt;

	    @Column(name = "Stuff_Line_Id")
	    private int stuffLineId;

	    @Column(name = "Eqactivity_flg", length = 1)
	    private String eqActivityFlag;
	    
	    @Column(name="Type_Of_Package",length = 6)
	    private String typeOfPackage;
	    
	    
	    @Column(name = "LENGTH_ODC", precision = 16, scale = 3)
	    private BigDecimal length;
	    
	    @Column(name = "HEIGHT_ODC", precision = 16, scale = 3)
	    private BigDecimal height;
	    
	    @Column(name = "WEIGHT_ODC", precision = 16, scale = 3)
	    private BigDecimal weight;
	    
	    @Column(name="ODC_TYPE",length = 50)
	    private String odcType;
	    
	    
	    public BigDecimal getLength() {
			return length;
		}







		public void setLength(BigDecimal length) {
			this.length = length;
		}







		public BigDecimal getHeight() {
			return height;
		}







		public void setHeight(BigDecimal height) {
			this.height = height;
		}







		public BigDecimal getWeight() {
			return weight;
		}







		public void setWeight(BigDecimal weight) {
			this.weight = weight;
		}







		public String getOdcType() {
			return odcType;
		}







		public void setOdcType(String odcType) {
			this.odcType = odcType;
		}




		@Transient
	    private String vesselName;
	    
	    @Transient
	    private String chaName;
	    
	    @Transient
	    private BigDecimal sbPackages;
	    
	    @Transient
	    private BigDecimal stuffedQuantity;
	    
	    @Transient
	    private BigDecimal sbWt;
	    
	    @Transient
	    private Date gateInDate;
	    
	    @Transient
	    private String cargoType;
	    
	    @Transient
	    private BigDecimal contStuffPackages;
	    
	    @Transient
	    private BigDecimal balQty;
	    
	    @Transient
	    private String shippingLineName;

	    @Transient
	    private String shippingAgentName;
	    
	    
	    @Transient
	    private String terminalName;

	    @Transient
	    private String finalPodName;

	    
	    
	    
	    







		public String getTerminalName() {
			return terminalName;
		}







		public void setTerminalName(String terminalName) {
			this.terminalName = terminalName;
		}







		public String getFinalPodName() {
			return finalPodName;
		}







		public void setFinalPodName(String finalPodName) {
			this.finalPodName = finalPodName;
		}







		public String getShippingLineName() {
			return shippingLineName;
		}







		public void setShippingLineName(String shippingLineName) {
			this.shippingLineName = shippingLineName;
		}







		public String getShippingAgentName() {
			return shippingAgentName;
		}







		public void setShippingAgentName(String shippingAgentName) {
			this.shippingAgentName = shippingAgentName;
		}







		public ExportStuffTally() {
			super();
			// TODO Auto-generated constructor stub
		}

		
		
		



		public BigDecimal getBalQty() {
			return balQty;
		}







		public void setBalQty(BigDecimal balQty) {
			this.balQty = balQty;
		}







		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
				int stuffTallyLineId, String profitcentreId, String cartingTransId, String sbLineId,
				String cartingLineId, String sbNo, String movementReqId, String movementType, Date stuffTallyDate,
				String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
				String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
				String finalPod, String containerNo, String containerStatus, String asrContainerStatus,
				String currentLocation, Date periodFrom, String gateInId, String containerSize, String containerType,
				String containerCondition, String crgYardLocation, String crgYardBlock, String crgBlockCellNo,
				String yardLocation, String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1,
				String blockCellNo1, BigDecimal yardPackages, BigDecimal cellAreaAllocated, String onAccountOf,
				String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal prvStuffedQty,
				BigDecimal balanceQty, BigDecimal cargoWeight, BigDecimal totalCargoWeight, BigDecimal totalGrossWeight,
				BigDecimal grossWeight, String weighmentFlag, String weighmentDone, Date weighmentDate,
				BigDecimal weighmentWeight, String weighmentPassNo, BigDecimal tareWeight, BigDecimal areaReleased,
				String genSetRequired, String haz, String imoCode, String containerInvoiceType, String item,
				String shippingAgent, String shippingLine, String commodity, String customsSealNo, String viaNo,
				Date cartingDate, String icdHub, String exporterName, String consignee, BigDecimal fob,
				String coverDetails, Date coverDate, String holdingAgent, String holdingAgentName, Date holdDate,
				Date releaseDate, String holdRemarks, String clpStatus, String clpCreatedBy, Date clpCreatedDate,
				String clpApprovedBy, Date clpApprovedDate, String gatePassNo, String gateOutId, Date gateOutDate,
				Date berthingDate, Date gateOpenDate, String sealType, String sealDev, String docType, String docNo,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String clpConfirmStatus, String clpConfirmBy, Date clpConfirmDate,
				String clpPcsStatus, String clpPcsMsgCreStatus, Date clpPcsMsgCreDate, Date clpPcsMsgAmdCreDate,
				BigDecimal documentNumber, BigDecimal commonReferenceNumber, BigDecimal amdDocumentNumber,
				BigDecimal amdCommonReferenceNumber, String sfJobNo, Date sfJobDate, String asrJobNo, Date asrJobDate,
				String cimNo, Date cimDate, String bondNo, String dpJobNo, Date dpJobDate, String dtJobNo,
				Date dtJobDate, int fromPkg, int toPkg, String isSfAck, String ackSfStatus, String isAsrAck,
				String ackAsrStatus, String isDpAck, String ackDpStatus, String isDtAck, String ackDtStatus,
				String isSfCancelStatus, Date sfCancelCreatedDate, Date sfCancelAckDate, String isSfCancelDesc,
				String othPartyId, String invoiceAssesed, String assesmentId, String invoiceNo, Date invoiceDate,
				String creditType, String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt,
				String backToTownRemark, String stuffTallyFlag, String stuffTallyWoTransId,
				Date stuffTallyCutWoTransDate, String ssrTransId, BigDecimal nopGrossWeight, String deliveryOrderNo,
				String reworkFlag, String reworkId, Date reworkDate, BigDecimal payLoad, String stuffMode,
				String formThirteenEntryFlag, String formThirteenEntryRemarks, Date formThirteenEntryDate,
				String formThirteenEntryUser, BigDecimal calGrossWt, int stuffLineId, String eqActivityFlag,
				String typeOfPackage, String vesselName, String chaName, BigDecimal sbPackages,
				BigDecimal stuffedQuantity, BigDecimal sbWt, Date gateInDate, String cargoType,
				BigDecimal contStuffPackages, BigDecimal balQty) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.stuffTallyId = stuffTallyId;
			this.sbTransId = sbTransId;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.cartingTransId = cartingTransId;
			this.sbLineId = sbLineId;
			this.cartingLineId = cartingLineId;
			this.sbNo = sbNo;
			this.movementReqId = movementReqId;
			this.movementType = movementType;
			this.stuffTallyDate = stuffTallyDate;
			this.stuffId = stuffId;
			this.stuffDate = stuffDate;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.rotationNo = rotationNo;
			this.rotationDate = rotationDate;
			this.pol = pol;
			this.terminal = terminal;
			this.pod = pod;
			this.finalPod = finalPod;
			this.containerNo = containerNo;
			this.containerStatus = containerStatus;
			this.asrContainerStatus = asrContainerStatus;
			this.currentLocation = currentLocation;
			this.periodFrom = periodFrom;
			this.gateInId = gateInId;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerCondition = containerCondition;
			this.crgYardLocation = crgYardLocation;
			this.crgYardBlock = crgYardBlock;
			this.crgBlockCellNo = crgBlockCellNo;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.yardLocation1 = yardLocation1;
			this.yardBlock1 = yardBlock1;
			this.blockCellNo1 = blockCellNo1;
			this.yardPackages = yardPackages;
			this.cellAreaAllocated = cellAreaAllocated;
			this.onAccountOf = onAccountOf;
			this.cha = cha;
			this.stuffRequestQty = stuffRequestQty;
			this.stuffedQty = stuffedQty;
			this.prvStuffedQty = prvStuffedQty;
			this.balanceQty = balanceQty;
			this.cargoWeight = cargoWeight;
			this.totalCargoWeight = totalCargoWeight;
			this.totalGrossWeight = totalGrossWeight;
			this.grossWeight = grossWeight;
			this.weighmentFlag = weighmentFlag;
			this.weighmentDone = weighmentDone;
			this.weighmentDate = weighmentDate;
			this.weighmentWeight = weighmentWeight;
			this.weighmentPassNo = weighmentPassNo;
			this.tareWeight = tareWeight;
			this.areaReleased = areaReleased;
			this.genSetRequired = genSetRequired;
			this.haz = haz;
			this.imoCode = imoCode;
			this.containerInvoiceType = containerInvoiceType;
			this.item = item;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.commodity = commodity;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.cartingDate = cartingDate;
			this.icdHub = icdHub;
			this.exporterName = exporterName;
			this.consignee = consignee;
			this.fob = fob;
			this.coverDetails = coverDetails;
			this.coverDate = coverDate;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.clpStatus = clpStatus;
			this.clpCreatedBy = clpCreatedBy;
			this.clpCreatedDate = clpCreatedDate;
			this.clpApprovedBy = clpApprovedBy;
			this.clpApprovedDate = clpApprovedDate;
			this.gatePassNo = gatePassNo;
			this.gateOutId = gateOutId;
			this.gateOutDate = gateOutDate;
			this.berthingDate = berthingDate;
			this.gateOpenDate = gateOpenDate;
			this.sealType = sealType;
			this.sealDev = sealDev;
			this.docType = docType;
			this.docNo = docNo;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.clpConfirmStatus = clpConfirmStatus;
			this.clpConfirmBy = clpConfirmBy;
			this.clpConfirmDate = clpConfirmDate;
			this.clpPcsStatus = clpPcsStatus;
			this.clpPcsMsgCreStatus = clpPcsMsgCreStatus;
			this.clpPcsMsgCreDate = clpPcsMsgCreDate;
			this.clpPcsMsgAmdCreDate = clpPcsMsgAmdCreDate;
			this.documentNumber = documentNumber;
			this.commonReferenceNumber = commonReferenceNumber;
			this.amdDocumentNumber = amdDocumentNumber;
			this.amdCommonReferenceNumber = amdCommonReferenceNumber;
			this.sfJobNo = sfJobNo;
			this.sfJobDate = sfJobDate;
			this.asrJobNo = asrJobNo;
			this.asrJobDate = asrJobDate;
			this.cimNo = cimNo;
			this.cimDate = cimDate;
			this.bondNo = bondNo;
			this.dpJobNo = dpJobNo;
			this.dpJobDate = dpJobDate;
			this.dtJobNo = dtJobNo;
			this.dtJobDate = dtJobDate;
			this.fromPkg = fromPkg;
			this.toPkg = toPkg;
			this.isSfAck = isSfAck;
			this.ackSfStatus = ackSfStatus;
			this.isAsrAck = isAsrAck;
			this.ackAsrStatus = ackAsrStatus;
			this.isDpAck = isDpAck;
			this.ackDpStatus = ackDpStatus;
			this.isDtAck = isDtAck;
			this.ackDtStatus = ackDtStatus;
			this.isSfCancelStatus = isSfCancelStatus;
			this.sfCancelCreatedDate = sfCancelCreatedDate;
			this.sfCancelAckDate = sfCancelAckDate;
			this.isSfCancelDesc = isSfCancelDesc;
			this.othPartyId = othPartyId;
			this.invoiceAssesed = invoiceAssesed;
			this.assesmentId = assesmentId;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.creditType = creditType;
			this.invoiceCategory = invoiceCategory;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.backToTownRemark = backToTownRemark;
			this.stuffTallyFlag = stuffTallyFlag;
			this.stuffTallyWoTransId = stuffTallyWoTransId;
			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
			this.ssrTransId = ssrTransId;
			this.nopGrossWeight = nopGrossWeight;
			this.deliveryOrderNo = deliveryOrderNo;
			this.reworkFlag = reworkFlag;
			this.reworkId = reworkId;
			this.reworkDate = reworkDate;
			this.payLoad = payLoad;
			this.stuffMode = stuffMode;
			this.formThirteenEntryFlag = formThirteenEntryFlag;
			this.formThirteenEntryRemarks = formThirteenEntryRemarks;
			this.formThirteenEntryDate = formThirteenEntryDate;
			this.formThirteenEntryUser = formThirteenEntryUser;
			this.calGrossWt = calGrossWt;
			this.stuffLineId = stuffLineId;
			this.eqActivityFlag = eqActivityFlag;
			this.typeOfPackage = typeOfPackage;
			this.vesselName = vesselName;
			this.chaName = chaName;
			this.sbPackages = sbPackages;
			this.stuffedQuantity = stuffedQuantity;
			this.sbWt = sbWt;
			this.gateInDate = gateInDate;
			this.cargoType = cargoType;
			this.contStuffPackages = contStuffPackages;
			this.balQty = balQty;
		}







		public BigDecimal getContStuffPackages() {
			return contStuffPackages;
		}







		public void setContStuffPackages(BigDecimal contStuffPackages) {
			this.contStuffPackages = contStuffPackages;
		}










		public BigDecimal getStuffedQuantity() {
			return stuffedQuantity;
		}







		public void setStuffedQuantity(BigDecimal stuffedQuantity) {
			this.stuffedQuantity = stuffedQuantity;
		}







		public String getCargoType() {
			return cargoType;
		}







		public void setCargoType(String cargoType) {
			this.cargoType = cargoType;
		}







		public Date getGateInDate() {
			return gateInDate;
		}







		public void setGateInDate(Date gateInDate) {
			this.gateInDate = gateInDate;
		}







		public BigDecimal getSbWt() {
			return sbWt;
		}







		public void setSbWt(BigDecimal sbWt) {
			this.sbWt = sbWt;
		}







		public BigDecimal getSbPackages() {
			return sbPackages;
		}







		public void setSbPackages(BigDecimal sbPackages) {
			this.sbPackages = sbPackages;
		}







		public String getChaName() {
			return chaName;
		}







		public void setChaName(String chaName) {
			this.chaName = chaName;
		}




	








		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
				int stuffTallyLineId, String profitcentreId, String cartingTransId, String sbLineId,
				String cartingLineId, String sbNo, String movementReqId, String movementType, Date stuffTallyDate,
				String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
				String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
				String finalPod, String containerNo, String containerStatus, String asrContainerStatus,
				String currentLocation, Date periodFrom, String gateInId, String containerSize, String containerType,
				String containerCondition, String crgYardLocation, String crgYardBlock, String crgBlockCellNo,
				String yardLocation, String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1,
				String blockCellNo1, BigDecimal yardPackages, BigDecimal cellAreaAllocated, String onAccountOf,
				String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal prvStuffedQty,
				BigDecimal balanceQty, BigDecimal cargoWeight, BigDecimal totalCargoWeight, BigDecimal totalGrossWeight,
				BigDecimal grossWeight, String weighmentFlag, String weighmentDone, Date weighmentDate,
				BigDecimal weighmentWeight, String weighmentPassNo, BigDecimal tareWeight, BigDecimal areaReleased,
				String genSetRequired, String haz, String imoCode, String containerInvoiceType, String item,
				String shippingAgent, String shippingLine, String commodity, String customsSealNo, String viaNo,
				Date cartingDate, String icdHub, String exporterName, String consignee, BigDecimal fob,
				String coverDetails, Date coverDate, String holdingAgent, String holdingAgentName, Date holdDate,
				Date releaseDate, String holdRemarks, String clpStatus, String clpCreatedBy, Date clpCreatedDate,
				String clpApprovedBy, Date clpApprovedDate, String gatePassNo, String gateOutId, Date gateOutDate,
				Date berthingDate, Date gateOpenDate, String sealType, String sealDev, String docType, String docNo,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String clpConfirmStatus, String clpConfirmBy, Date clpConfirmDate,
				String clpPcsStatus, String clpPcsMsgCreStatus, Date clpPcsMsgCreDate, Date clpPcsMsgAmdCreDate,
				BigDecimal documentNumber, BigDecimal commonReferenceNumber, BigDecimal amdDocumentNumber,
				BigDecimal amdCommonReferenceNumber, String sfJobNo, Date sfJobDate, String asrJobNo, Date asrJobDate,
				String cimNo, Date cimDate, String bondNo, String dpJobNo, Date dpJobDate, String dtJobNo,
				Date dtJobDate, int fromPkg, int toPkg, String isSfAck, String ackSfStatus, String isAsrAck,
				String ackAsrStatus, String isDpAck, String ackDpStatus, String isDtAck, String ackDtStatus,
				String isSfCancelStatus, Date sfCancelCreatedDate, Date sfCancelAckDate, String isSfCancelDesc,
				String othPartyId, String invoiceAssesed, String assesmentId, String invoiceNo, Date invoiceDate,
				String creditType, String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt,
				String backToTownRemark, String stuffTallyFlag, String stuffTallyWoTransId,
				Date stuffTallyCutWoTransDate, String ssrTransId, BigDecimal nopGrossWeight, String deliveryOrderNo,
				String reworkFlag, String reworkId, Date reworkDate, BigDecimal payLoad, String stuffMode,
				String formThirteenEntryFlag, String formThirteenEntryRemarks, Date formThirteenEntryDate,
				String formThirteenEntryUser, BigDecimal calGrossWt, int stuffLineId, String eqActivityFlag,
				String typeOfPackage, String vesselName, String chaName, BigDecimal sbPackages,
				BigDecimal stuffedQuantity, BigDecimal sbWt, Date gateInDate, String cargoType) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.stuffTallyId = stuffTallyId;
			this.sbTransId = sbTransId;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.cartingTransId = cartingTransId;
			this.sbLineId = sbLineId;
			this.cartingLineId = cartingLineId;
			this.sbNo = sbNo;
			this.movementReqId = movementReqId;
			this.movementType = movementType;
			this.stuffTallyDate = stuffTallyDate;
			this.stuffId = stuffId;
			this.stuffDate = stuffDate;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.rotationNo = rotationNo;
			this.rotationDate = rotationDate;
			this.pol = pol;
			this.terminal = terminal;
			this.pod = pod;
			this.finalPod = finalPod;
			this.containerNo = containerNo;
			this.containerStatus = containerStatus;
			this.asrContainerStatus = asrContainerStatus;
			this.currentLocation = currentLocation;
			this.periodFrom = periodFrom;
			this.gateInId = gateInId;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerCondition = containerCondition;
			this.crgYardLocation = crgYardLocation;
			this.crgYardBlock = crgYardBlock;
			this.crgBlockCellNo = crgBlockCellNo;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.yardLocation1 = yardLocation1;
			this.yardBlock1 = yardBlock1;
			this.blockCellNo1 = blockCellNo1;
			this.yardPackages = yardPackages;
			this.cellAreaAllocated = cellAreaAllocated;
			this.onAccountOf = onAccountOf;
			this.cha = cha;
			this.stuffRequestQty = stuffRequestQty;
			this.stuffedQty = stuffedQty;
			this.prvStuffedQty = prvStuffedQty;
			this.balanceQty = balanceQty;
			this.cargoWeight = cargoWeight;
			this.totalCargoWeight = totalCargoWeight;
			this.totalGrossWeight = totalGrossWeight;
			this.grossWeight = grossWeight;
			this.weighmentFlag = weighmentFlag;
			this.weighmentDone = weighmentDone;
			this.weighmentDate = weighmentDate;
			this.weighmentWeight = weighmentWeight;
			this.weighmentPassNo = weighmentPassNo;
			this.tareWeight = tareWeight;
			this.areaReleased = areaReleased;
			this.genSetRequired = genSetRequired;
			this.haz = haz;
			this.imoCode = imoCode;
			this.containerInvoiceType = containerInvoiceType;
			this.item = item;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.commodity = commodity;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.cartingDate = cartingDate;
			this.icdHub = icdHub;
			this.exporterName = exporterName;
			this.consignee = consignee;
			this.fob = fob;
			this.coverDetails = coverDetails;
			this.coverDate = coverDate;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.clpStatus = clpStatus;
			this.clpCreatedBy = clpCreatedBy;
			this.clpCreatedDate = clpCreatedDate;
			this.clpApprovedBy = clpApprovedBy;
			this.clpApprovedDate = clpApprovedDate;
			this.gatePassNo = gatePassNo;
			this.gateOutId = gateOutId;
			this.gateOutDate = gateOutDate;
			this.berthingDate = berthingDate;
			this.gateOpenDate = gateOpenDate;
			this.sealType = sealType;
			this.sealDev = sealDev;
			this.docType = docType;
			this.docNo = docNo;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.clpConfirmStatus = clpConfirmStatus;
			this.clpConfirmBy = clpConfirmBy;
			this.clpConfirmDate = clpConfirmDate;
			this.clpPcsStatus = clpPcsStatus;
			this.clpPcsMsgCreStatus = clpPcsMsgCreStatus;
			this.clpPcsMsgCreDate = clpPcsMsgCreDate;
			this.clpPcsMsgAmdCreDate = clpPcsMsgAmdCreDate;
			this.documentNumber = documentNumber;
			this.commonReferenceNumber = commonReferenceNumber;
			this.amdDocumentNumber = amdDocumentNumber;
			this.amdCommonReferenceNumber = amdCommonReferenceNumber;
			this.sfJobNo = sfJobNo;
			this.sfJobDate = sfJobDate;
			this.asrJobNo = asrJobNo;
			this.asrJobDate = asrJobDate;
			this.cimNo = cimNo;
			this.cimDate = cimDate;
			this.bondNo = bondNo;
			this.dpJobNo = dpJobNo;
			this.dpJobDate = dpJobDate;
			this.dtJobNo = dtJobNo;
			this.dtJobDate = dtJobDate;
			this.fromPkg = fromPkg;
			this.toPkg = toPkg;
			this.isSfAck = isSfAck;
			this.ackSfStatus = ackSfStatus;
			this.isAsrAck = isAsrAck;
			this.ackAsrStatus = ackAsrStatus;
			this.isDpAck = isDpAck;
			this.ackDpStatus = ackDpStatus;
			this.isDtAck = isDtAck;
			this.ackDtStatus = ackDtStatus;
			this.isSfCancelStatus = isSfCancelStatus;
			this.sfCancelCreatedDate = sfCancelCreatedDate;
			this.sfCancelAckDate = sfCancelAckDate;
			this.isSfCancelDesc = isSfCancelDesc;
			this.othPartyId = othPartyId;
			this.invoiceAssesed = invoiceAssesed;
			this.assesmentId = assesmentId;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.creditType = creditType;
			this.invoiceCategory = invoiceCategory;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.backToTownRemark = backToTownRemark;
			this.stuffTallyFlag = stuffTallyFlag;
			this.stuffTallyWoTransId = stuffTallyWoTransId;
			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
			this.ssrTransId = ssrTransId;
			this.nopGrossWeight = nopGrossWeight;
			this.deliveryOrderNo = deliveryOrderNo;
			this.reworkFlag = reworkFlag;
			this.reworkId = reworkId;
			this.reworkDate = reworkDate;
			this.payLoad = payLoad;
			this.stuffMode = stuffMode;
			this.formThirteenEntryFlag = formThirteenEntryFlag;
			this.formThirteenEntryRemarks = formThirteenEntryRemarks;
			this.formThirteenEntryDate = formThirteenEntryDate;
			this.formThirteenEntryUser = formThirteenEntryUser;
			this.calGrossWt = calGrossWt;
			this.stuffLineId = stuffLineId;
			this.eqActivityFlag = eqActivityFlag;
			this.typeOfPackage = typeOfPackage;
			this.vesselName = vesselName;
			this.chaName = chaName;
			this.sbPackages = sbPackages;
			this.stuffedQuantity = stuffedQuantity;
			this.sbWt = sbWt;
			this.gateInDate = gateInDate;
			this.cargoType = cargoType;
		}







		public String getVesselName() {
			return vesselName;
		}







		public void setVesselName(String vesselName) {
			this.vesselName = vesselName;
		}







		public String getTypeOfPackage() {
			return typeOfPackage;
		}





		public void setTypeOfPackage(String typeOfPackage) {
			this.typeOfPackage = typeOfPackage;
		}





		public Date getRotationDate() {
			return rotationDate;
		}


		public void setRotationDate(Date rotationDate) {
			this.rotationDate = rotationDate;
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

		public String getStuffTallyId() {
			return stuffTallyId;
		}

		public void setStuffTallyId(String stuffTallyId) {
			this.stuffTallyId = stuffTallyId;
		}

		public String getSbTransId() {
			return sbTransId;
		}

		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}

		public int getStuffTallyLineId() {
			return stuffTallyLineId;
		}

		public void setStuffTallyLineId(int stuffTallyLineId) {
			this.stuffTallyLineId = stuffTallyLineId;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getCartingTransId() {
			return cartingTransId;
		}

		public void setCartingTransId(String cartingTransId) {
			this.cartingTransId = cartingTransId;
		}

		public String getSbLineId() {
			return sbLineId;
		}

		public void setSbLineId(String sbLineId) {
			this.sbLineId = sbLineId;
		}

		public String getCartingLineId() {
			return cartingLineId;
		}

		public void setCartingLineId(String cartingLineId) {
			this.cartingLineId = cartingLineId;
		}

		public String getSbNo() {
			return sbNo;
		}

		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}

		public String getMovementReqId() {
			return movementReqId;
		}

		public void setMovementReqId(String movementReqId) {
			this.movementReqId = movementReqId;
		}

		public String getMovementType() {
			return movementType;
		}

		public void setMovementType(String movementType) {
			this.movementType = movementType;
		}

		public Date getStuffTallyDate() {
			return stuffTallyDate;
		}

		public void setStuffTallyDate(Date stuffTallyDate) {
			this.stuffTallyDate = stuffTallyDate;
		}

		public String getStuffId() {
			return stuffId;
		}

		public void setStuffId(String stuffId) {
			this.stuffId = stuffId;
		}

		public Date getStuffDate() {
			return stuffDate;
		}

		public void setStuffDate(Date stuffDate) {
			this.stuffDate = stuffDate;
		}

		public Date getSbDate() {
			return sbDate;
		}

		public void setSbDate(Date sbDate) {
			this.sbDate = sbDate;
		}

		public String getShift() {
			return shift;
		}

		public void setShift(String shift) {
			this.shift = shift;
		}

		public String getAgentSealNo() {
			return agentSealNo;
		}

		public void setAgentSealNo(String agentSealNo) {
			this.agentSealNo = agentSealNo;
		}

		public String getVesselId() {
			return vesselId;
		}

		public void setVesselId(String vesselId) {
			this.vesselId = vesselId;
		}

		public String getVoyageNo() {
			return voyageNo;
		}

		public void setVoyageNo(String voyageNo) {
			this.voyageNo = voyageNo;
		}

		public String getRotationNo() {
			return rotationNo;
		}

		public void setRotationNo(String rotationNo) {
			this.rotationNo = rotationNo;
		}

		public String getPol() {
			return pol;
		}

		public void setPol(String pol) {
			this.pol = pol;
		}

		public String getTerminal() {
			return terminal;
		}

		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}

		public String getPod() {
			return pod;
		}

		public void setPod(String pod) {
			this.pod = pod;
		}

		public String getFinalPod() {
			return finalPod;
		}

		public void setFinalPod(String finalPod) {
			this.finalPod = finalPod;
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

		public String getAsrContainerStatus() {
			return asrContainerStatus;
		}

		public void setAsrContainerStatus(String asrContainerStatus) {
			this.asrContainerStatus = asrContainerStatus;
		}

		public String getCurrentLocation() {
			return currentLocation;
		}

		public void setCurrentLocation(String currentLocation) {
			this.currentLocation = currentLocation;
		}

		public Date getPeriodFrom() {
			return periodFrom;
		}

		public void setPeriodFrom(Date periodFrom) {
			this.periodFrom = periodFrom;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
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

		public String getContainerCondition() {
			return containerCondition;
		}

		public void setContainerCondition(String containerCondition) {
			this.containerCondition = containerCondition;
		}

		public String getCrgYardLocation() {
			return crgYardLocation;
		}

		public void setCrgYardLocation(String crgYardLocation) {
			this.crgYardLocation = crgYardLocation;
		}

		public String getCrgYardBlock() {
			return crgYardBlock;
		}

		public void setCrgYardBlock(String crgYardBlock) {
			this.crgYardBlock = crgYardBlock;
		}

		public String getCrgBlockCellNo() {
			return crgBlockCellNo;
		}

		public void setCrgBlockCellNo(String crgBlockCellNo) {
			this.crgBlockCellNo = crgBlockCellNo;
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

		public BigDecimal getYardPackages() {
			return yardPackages;
		}

		public void setYardPackages(BigDecimal yardPackages) {
			this.yardPackages = yardPackages;
		}

		public BigDecimal getCellAreaAllocated() {
			return cellAreaAllocated;
		}

		public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
			this.cellAreaAllocated = cellAreaAllocated;
		}

		public String getOnAccountOf() {
			return onAccountOf;
		}

		public void setOnAccountOf(String onAccountOf) {
			this.onAccountOf = onAccountOf;
		}

		public String getCha() {
			return cha;
		}

		public void setCha(String cha) {
			this.cha = cha;
		}

		public BigDecimal getStuffRequestQty() {
			return stuffRequestQty;
		}

		public void setStuffRequestQty(BigDecimal stuffRequestQty) {
			this.stuffRequestQty = stuffRequestQty;
		}

		public BigDecimal getStuffedQty() {
			return stuffedQty;
		}

		public void setStuffedQty(BigDecimal stuffedQty) {
			this.stuffedQty = stuffedQty;
		}

		public BigDecimal getPrvStuffedQty() {
			return prvStuffedQty;
		}

		public void setPrvStuffedQty(BigDecimal prvStuffedQty) {
			this.prvStuffedQty = prvStuffedQty;
		}

		public BigDecimal getBalanceQty() {
			return balanceQty;
		}

		public void setBalanceQty(BigDecimal balanceQty) {
			this.balanceQty = balanceQty;
		}

		public BigDecimal getCargoWeight() {
			return cargoWeight;
		}

		public void setCargoWeight(BigDecimal cargoWeight) {
			this.cargoWeight = cargoWeight;
		}

		public BigDecimal getTotalCargoWeight() {
			return totalCargoWeight;
		}

		public void setTotalCargoWeight(BigDecimal totalCargoWeight) {
			this.totalCargoWeight = totalCargoWeight;
		}

		public BigDecimal getTotalGrossWeight() {
			return totalGrossWeight;
		}

		public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
			this.totalGrossWeight = totalGrossWeight;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public String getWeighmentFlag() {
			return weighmentFlag;
		}

		public void setWeighmentFlag(String weighmentFlag) {
			this.weighmentFlag = weighmentFlag;
		}

		public String getWeighmentDone() {
			return weighmentDone;
		}

		public void setWeighmentDone(String weighmentDone) {
			this.weighmentDone = weighmentDone;
		}

		public Date getWeighmentDate() {
			return weighmentDate;
		}

		public void setWeighmentDate(Date weighmentDate) {
			this.weighmentDate = weighmentDate;
		}

		public BigDecimal getWeighmentWeight() {
			return weighmentWeight;
		}

		public void setWeighmentWeight(BigDecimal weighmentWeight) {
			this.weighmentWeight = weighmentWeight;
		}

		public String getWeighmentPassNo() {
			return weighmentPassNo;
		}

		public void setWeighmentPassNo(String weighmentPassNo) {
			this.weighmentPassNo = weighmentPassNo;
		}

		public BigDecimal getTareWeight() {
			return tareWeight;
		}

		public void setTareWeight(BigDecimal tareWeight) {
			this.tareWeight = tareWeight;
		}

		public BigDecimal getAreaReleased() {
			return areaReleased;
		}

		public void setAreaReleased(BigDecimal areaReleased) {
			this.areaReleased = areaReleased;
		}

		public String getGenSetRequired() {
			return genSetRequired;
		}

		public void setGenSetRequired(String genSetRequired) {
			this.genSetRequired = genSetRequired;
		}

		public String getHaz() {
			return haz;
		}

		public void setHaz(String haz) {
			this.haz = haz;
		}

		public String getImoCode() {
			return imoCode;
		}

		public void setImoCode(String imoCode) {
			this.imoCode = imoCode;
		}

		public String getContainerInvoiceType() {
			return containerInvoiceType;
		}

		public void setContainerInvoiceType(String containerInvoiceType) {
			this.containerInvoiceType = containerInvoiceType;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
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

		public String getCommodity() {
			return commodity;
		}

		public void setCommodity(String commodity) {
			this.commodity = commodity;
		}

		public String getCustomsSealNo() {
			return customsSealNo;
		}

		public void setCustomsSealNo(String customsSealNo) {
			this.customsSealNo = customsSealNo;
		}

		public String getViaNo() {
			return viaNo;
		}

		public void setViaNo(String viaNo) {
			this.viaNo = viaNo;
		}

		public Date getCartingDate() {
			return cartingDate;
		}

		public void setCartingDate(Date cartingDate) {
			this.cartingDate = cartingDate;
		}

		public String getIcdHub() {
			return icdHub;
		}

		public void setIcdHub(String icdHub) {
			this.icdHub = icdHub;
		}

		public String getExporterName() {
			return exporterName;
		}

		public void setExporterName(String exporterName) {
			this.exporterName = exporterName;
		}

		public String getConsignee() {
			return consignee;
		}

		public void setConsignee(String consignee) {
			this.consignee = consignee;
		}

		public BigDecimal getFob() {
			return fob;
		}

		public void setFob(BigDecimal fob) {
			this.fob = fob;
		}

		public String getCoverDetails() {
			return coverDetails;
		}

		public void setCoverDetails(String coverDetails) {
			this.coverDetails = coverDetails;
		}

		public Date getCoverDate() {
			return coverDate;
		}

		public void setCoverDate(Date coverDate) {
			this.coverDate = coverDate;
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

		public String getClpStatus() {
			return clpStatus;
		}

		public void setClpStatus(String clpStatus) {
			this.clpStatus = clpStatus;
		}

		public String getClpCreatedBy() {
			return clpCreatedBy;
		}

		public void setClpCreatedBy(String clpCreatedBy) {
			this.clpCreatedBy = clpCreatedBy;
		}

		public Date getClpCreatedDate() {
			return clpCreatedDate;
		}

		public void setClpCreatedDate(Date clpCreatedDate) {
			this.clpCreatedDate = clpCreatedDate;
		}

		public String getClpApprovedBy() {
			return clpApprovedBy;
		}

		public void setClpApprovedBy(String clpApprovedBy) {
			this.clpApprovedBy = clpApprovedBy;
		}

		public Date getClpApprovedDate() {
			return clpApprovedDate;
		}

		public void setClpApprovedDate(Date clpApprovedDate) {
			this.clpApprovedDate = clpApprovedDate;
		}

		public String getGatePassNo() {
			return gatePassNo;
		}

		public void setGatePassNo(String gatePassNo) {
			this.gatePassNo = gatePassNo;
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

		public Date getBerthingDate() {
			return berthingDate;
		}

		public void setBerthingDate(Date berthingDate) {
			this.berthingDate = berthingDate;
		}

		public Date getGateOpenDate() {
			return gateOpenDate;
		}

		public void setGateOpenDate(Date gateOpenDate) {
			this.gateOpenDate = gateOpenDate;
		}

		public String getSealType() {
			return sealType;
		}

		public void setSealType(String sealType) {
			this.sealType = sealType;
		}

		public String getSealDev() {
			return sealDev;
		}

		public void setSealDev(String sealDev) {
			this.sealDev = sealDev;
		}

		public String getDocType() {
			return docType;
		}

		public void setDocType(String docType) {
			this.docType = docType;
		}

		public String getDocNo() {
			return docNo;
		}

		public void setDocNo(String docNo) {
			this.docNo = docNo;
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

		public String getClpConfirmStatus() {
			return clpConfirmStatus;
		}

		public void setClpConfirmStatus(String clpConfirmStatus) {
			this.clpConfirmStatus = clpConfirmStatus;
		}

		public String getClpConfirmBy() {
			return clpConfirmBy;
		}

		public void setClpConfirmBy(String clpConfirmBy) {
			this.clpConfirmBy = clpConfirmBy;
		}

		public Date getClpConfirmDate() {
			return clpConfirmDate;
		}

		public void setClpConfirmDate(Date clpConfirmDate) {
			this.clpConfirmDate = clpConfirmDate;
		}

		public String getClpPcsStatus() {
			return clpPcsStatus;
		}

		public void setClpPcsStatus(String clpPcsStatus) {
			this.clpPcsStatus = clpPcsStatus;
		}

		public String getClpPcsMsgCreStatus() {
			return clpPcsMsgCreStatus;
		}

		public void setClpPcsMsgCreStatus(String clpPcsMsgCreStatus) {
			this.clpPcsMsgCreStatus = clpPcsMsgCreStatus;
		}

		public Date getClpPcsMsgCreDate() {
			return clpPcsMsgCreDate;
		}

		public void setClpPcsMsgCreDate(Date clpPcsMsgCreDate) {
			this.clpPcsMsgCreDate = clpPcsMsgCreDate;
		}

		public Date getClpPcsMsgAmdCreDate() {
			return clpPcsMsgAmdCreDate;
		}

		public void setClpPcsMsgAmdCreDate(Date clpPcsMsgAmdCreDate) {
			this.clpPcsMsgAmdCreDate = clpPcsMsgAmdCreDate;
		}

		public BigDecimal getDocumentNumber() {
			return documentNumber;
		}

		public void setDocumentNumber(BigDecimal documentNumber) {
			this.documentNumber = documentNumber;
		}

		public BigDecimal getCommonReferenceNumber() {
			return commonReferenceNumber;
		}

		public void setCommonReferenceNumber(BigDecimal commonReferenceNumber) {
			this.commonReferenceNumber = commonReferenceNumber;
		}

		public BigDecimal getAmdDocumentNumber() {
			return amdDocumentNumber;
		}

		public void setAmdDocumentNumber(BigDecimal amdDocumentNumber) {
			this.amdDocumentNumber = amdDocumentNumber;
		}

		public BigDecimal getAmdCommonReferenceNumber() {
			return amdCommonReferenceNumber;
		}

		public void setAmdCommonReferenceNumber(BigDecimal amdCommonReferenceNumber) {
			this.amdCommonReferenceNumber = amdCommonReferenceNumber;
		}

		public String getSfJobNo() {
			return sfJobNo;
		}

		public void setSfJobNo(String sfJobNo) {
			this.sfJobNo = sfJobNo;
		}

		public Date getSfJobDate() {
			return sfJobDate;
		}

		public void setSfJobDate(Date sfJobDate) {
			this.sfJobDate = sfJobDate;
		}

		public String getAsrJobNo() {
			return asrJobNo;
		}

		public void setAsrJobNo(String asrJobNo) {
			this.asrJobNo = asrJobNo;
		}

		public Date getAsrJobDate() {
			return asrJobDate;
		}

		public void setAsrJobDate(Date asrJobDate) {
			this.asrJobDate = asrJobDate;
		}

		public String getCimNo() {
			return cimNo;
		}

		public void setCimNo(String cimNo) {
			this.cimNo = cimNo;
		}

		public Date getCimDate() {
			return cimDate;
		}

		public void setCimDate(Date cimDate) {
			this.cimDate = cimDate;
		}

		public String getBondNo() {
			return bondNo;
		}

		public void setBondNo(String bondNo) {
			this.bondNo = bondNo;
		}

		public String getDpJobNo() {
			return dpJobNo;
		}

		public void setDpJobNo(String dpJobNo) {
			this.dpJobNo = dpJobNo;
		}

		public Date getDpJobDate() {
			return dpJobDate;
		}

		public void setDpJobDate(Date dpJobDate) {
			this.dpJobDate = dpJobDate;
		}

		public String getDtJobNo() {
			return dtJobNo;
		}

		public void setDtJobNo(String dtJobNo) {
			this.dtJobNo = dtJobNo;
		}

		public Date getDtJobDate() {
			return dtJobDate;
		}

		public void setDtJobDate(Date dtJobDate) {
			this.dtJobDate = dtJobDate;
		}

		public int getFromPkg() {
			return fromPkg;
		}

		public void setFromPkg(int fromPkg) {
			this.fromPkg = fromPkg;
		}

		public int getToPkg() {
			return toPkg;
		}

		public void setToPkg(int toPkg) {
			this.toPkg = toPkg;
		}

		public String getIsSfAck() {
			return isSfAck;
		}

		public void setIsSfAck(String isSfAck) {
			this.isSfAck = isSfAck;
		}

		public String getAckSfStatus() {
			return ackSfStatus;
		}

		public void setAckSfStatus(String ackSfStatus) {
			this.ackSfStatus = ackSfStatus;
		}

		public String getIsAsrAck() {
			return isAsrAck;
		}

		public void setIsAsrAck(String isAsrAck) {
			this.isAsrAck = isAsrAck;
		}

		public String getAckAsrStatus() {
			return ackAsrStatus;
		}

		public void setAckAsrStatus(String ackAsrStatus) {
			this.ackAsrStatus = ackAsrStatus;
		}

		public String getIsDpAck() {
			return isDpAck;
		}

		public void setIsDpAck(String isDpAck) {
			this.isDpAck = isDpAck;
		}

		public String getAckDpStatus() {
			return ackDpStatus;
		}

		public void setAckDpStatus(String ackDpStatus) {
			this.ackDpStatus = ackDpStatus;
		}

		public String getIsDtAck() {
			return isDtAck;
		}

		public void setIsDtAck(String isDtAck) {
			this.isDtAck = isDtAck;
		}

		public String getAckDtStatus() {
			return ackDtStatus;
		}

		public void setAckDtStatus(String ackDtStatus) {
			this.ackDtStatus = ackDtStatus;
		}

		public String getIsSfCancelStatus() {
			return isSfCancelStatus;
		}

		public void setIsSfCancelStatus(String isSfCancelStatus) {
			this.isSfCancelStatus = isSfCancelStatus;
		}

		public Date getSfCancelCreatedDate() {
			return sfCancelCreatedDate;
		}

		public void setSfCancelCreatedDate(Date sfCancelCreatedDate) {
			this.sfCancelCreatedDate = sfCancelCreatedDate;
		}

		public Date getSfCancelAckDate() {
			return sfCancelAckDate;
		}

		public void setSfCancelAckDate(Date sfCancelAckDate) {
			this.sfCancelAckDate = sfCancelAckDate;
		}

		public String getIsSfCancelDesc() {
			return isSfCancelDesc;
		}

		public void setIsSfCancelDesc(String isSfCancelDesc) {
			this.isSfCancelDesc = isSfCancelDesc;
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

		public String getBackToTownRemark() {
			return backToTownRemark;
		}

		public void setBackToTownRemark(String backToTownRemark) {
			this.backToTownRemark = backToTownRemark;
		}

		public String getStuffTallyFlag() {
			return stuffTallyFlag;
		}

		public void setStuffTallyFlag(String stuffTallyFlag) {
			this.stuffTallyFlag = stuffTallyFlag;
		}

		public String getStuffTallyWoTransId() {
			return stuffTallyWoTransId;
		}

		public void setStuffTallyWoTransId(String stuffTallyWoTransId) {
			this.stuffTallyWoTransId = stuffTallyWoTransId;
		}

		public Date getStuffTallyCutWoTransDate() {
			return stuffTallyCutWoTransDate;
		}

		public void setStuffTallyCutWoTransDate(Date stuffTallyCutWoTransDate) {
			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
		}

		public String getSsrTransId() {
			return ssrTransId;
		}

		public void setSsrTransId(String ssrTransId) {
			this.ssrTransId = ssrTransId;
		}

		public BigDecimal getNopGrossWeight() {
			return nopGrossWeight;
		}

		public void setNopGrossWeight(BigDecimal nopGrossWeight) {
			this.nopGrossWeight = nopGrossWeight;
		}

		public String getDeliveryOrderNo() {
			return deliveryOrderNo;
		}

		public void setDeliveryOrderNo(String deliveryOrderNo) {
			this.deliveryOrderNo = deliveryOrderNo;
		}

		public String getReworkFlag() {
			return reworkFlag;
		}

		public void setReworkFlag(String reworkFlag) {
			this.reworkFlag = reworkFlag;
		}

		public String getReworkId() {
			return reworkId;
		}

		public void setReworkId(String reworkId) {
			this.reworkId = reworkId;
		}

		public Date getReworkDate() {
			return reworkDate;
		}

		public void setReworkDate(Date reworkDate) {
			this.reworkDate = reworkDate;
		}

		public BigDecimal getPayLoad() {
			return payLoad;
		}

		public void setPayLoad(BigDecimal payLoad) {
			this.payLoad = payLoad;
		}

		public String getStuffMode() {
			return stuffMode;
		}

		public void setStuffMode(String stuffMode) {
			this.stuffMode = stuffMode;
		}

		public String getFormThirteenEntryFlag() {
			return formThirteenEntryFlag;
		}

		public void setFormThirteenEntryFlag(String formThirteenEntryFlag) {
			this.formThirteenEntryFlag = formThirteenEntryFlag;
		}

		public String getFormThirteenEntryRemarks() {
			return formThirteenEntryRemarks;
		}

		public void setFormThirteenEntryRemarks(String formThirteenEntryRemarks) {
			this.formThirteenEntryRemarks = formThirteenEntryRemarks;
		}

		public Date getFormThirteenEntryDate() {
			return formThirteenEntryDate;
		}

		public void setFormThirteenEntryDate(Date formThirteenEntryDate) {
			this.formThirteenEntryDate = formThirteenEntryDate;
		}

		public String getFormThirteenEntryUser() {
			return formThirteenEntryUser;
		}

		public void setFormThirteenEntryUser(String formThirteenEntryUser) {
			this.formThirteenEntryUser = formThirteenEntryUser;
		}

		public BigDecimal getCalGrossWt() {
			return calGrossWt;
		}

		public void setCalGrossWt(BigDecimal calGrossWt) {
			this.calGrossWt = calGrossWt;
		}

		public int getStuffLineId() {
			return stuffLineId;
		}

		public void setStuffLineId(int stuffLineId) {
			this.stuffLineId = stuffLineId;
		}

		public String getEqActivityFlag() {
			return eqActivityFlag;
		}

		public void setEqActivityFlag(String eqActivityFlag) {
			this.eqActivityFlag = eqActivityFlag;
		}







		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
				int stuffTallyLineId, String profitcentreId, String sbLineId, String sbNo, Date stuffTallyDate,
				String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
				String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
				String finalPod, String containerNo, String containerStatus, Date periodFrom, String containerSize,
				String containerType, String containerCondition, BigDecimal yardPackages, BigDecimal cellAreaAllocated,
				String onAccountOf, String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty,
				BigDecimal balanceQty,BigDecimal balQty, BigDecimal cargoWeight, BigDecimal totalGrossWeight, BigDecimal tareWeight,
				BigDecimal areaReleased, String genSetRequired, String haz, String shippingAgent, String shippingLine,
				String commodity, String customsSealNo, String viaNo, String exporterName, String consignee,
				BigDecimal fob, Date berthingDate, Date gateOpenDate, String sealType, String docType, String docNo,
				String status, String createdBy, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate,
				String deliveryOrderNo, String stuffMode, String typeOfPackage, String vesselName, String chaName, BigDecimal totalCargoWeight) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.stuffTallyId = stuffTallyId;
			this.sbTransId = sbTransId;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.sbLineId = sbLineId;
			this.sbNo = sbNo;
			this.stuffTallyDate = stuffTallyDate;
			this.stuffId = stuffId;
			this.stuffDate = stuffDate;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.rotationNo = rotationNo;
			this.rotationDate = rotationDate;
			this.pol = pol;
			this.terminal = terminal;
			this.pod = pod;
			this.finalPod = finalPod;
			this.containerNo = containerNo;
			this.containerStatus = containerStatus;
			this.periodFrom = periodFrom;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerCondition = containerCondition;
			this.yardPackages = yardPackages;
			this.cellAreaAllocated = cellAreaAllocated;
			this.onAccountOf = onAccountOf;
			this.cha = cha;
			this.stuffRequestQty = stuffRequestQty;
			this.stuffedQty = stuffedQty;
			this.balanceQty = balanceQty;
			this.balQty = balQty;
			this.cargoWeight = cargoWeight;
			this.totalGrossWeight = totalGrossWeight;
			this.tareWeight = tareWeight;
			this.areaReleased = areaReleased;
			this.genSetRequired = genSetRequired;
			this.haz = haz;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.commodity = commodity;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.exporterName = exporterName;
			this.consignee = consignee;
			this.fob = fob;
			this.berthingDate = berthingDate;
			this.gateOpenDate = gateOpenDate;
			this.sealType = sealType;
			this.docType = docType;
			this.docNo = docNo;
			this.status = status;
			this.createdBy = createdBy;
			this.stuffTallyWoTransId = stuffTallyWoTransId;
			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
			this.deliveryOrderNo = deliveryOrderNo;
			this.stuffMode = stuffMode;
			this.typeOfPackage = typeOfPackage;
			this.vesselName = vesselName;
			this.chaName = chaName;
			this.totalCargoWeight = totalCargoWeight;
		}







		public ExportStuffTally(String stuffTallyId, String sbTransId, String sbNo, String movementType,
				Date stuffTallyDate, Date sbDate, String agentSealNo, String vesselId, String voyageNo,
				String rotationNo, Date rotationDate, String terminal, String pod, String finalPod, String containerNo,String stuffId,
				String gateInId, String containerSize, String containerType, String onAccountOf, String cha,
				BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal balanceQty,BigDecimal cargoWeight, BigDecimal totalCargoWeight,
				BigDecimal tareWeight, String shippingAgent, String shippingLine, String commodity,
				String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
				String status, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate, String deliveryOrderNo,
				String stuffMode, String vesselName, BigDecimal sbPackages, BigDecimal stuffedQuantity, BigDecimal sbWt,
				Date gateInDate, String cargoType, Date berthingDate, String reworkFlag) {
			this.stuffTallyId = stuffTallyId;
			this.sbTransId = sbTransId;
			this.sbNo = sbNo;
			this.movementType = movementType;
			this.stuffTallyDate = stuffTallyDate;
			this.sbDate = sbDate;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.rotationNo = rotationNo;
			this.rotationDate = rotationDate;
			this.terminal = terminal;
			this.pod = pod;
			this.finalPod = finalPod;
			this.containerNo = containerNo;
			this.stuffId = stuffId;
			this.gateInId = gateInId;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.onAccountOf = onAccountOf;
			this.cha = cha;
			this.stuffRequestQty = stuffRequestQty;
			this.stuffedQty = stuffedQty;
			this.balanceQty = balanceQty;
			this.cargoWeight = cargoWeight;
			this.totalCargoWeight = totalCargoWeight;
			this.tareWeight = tareWeight;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.commodity = commodity;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.exporterName = exporterName;
			this.consignee = consignee;
			this.fob = fob;
			this.status = status;
			this.stuffTallyWoTransId = stuffTallyWoTransId;
			this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
			this.deliveryOrderNo = deliveryOrderNo;
			this.stuffMode = stuffMode;
			this.vesselName = vesselName;
			this.sbPackages = sbPackages;
			this.stuffedQuantity = stuffedQuantity;
			this.sbWt = sbWt;
			this.gateInDate = gateInDate;
			this.cargoType = cargoType;
			this.berthingDate = berthingDate;
			this.reworkFlag = reworkFlag;
		}







		@Override
		public String toString() {
			return "ExportStuffTally [companyId=" + companyId + ", branchId=" + branchId + ", stuffTallyId="
					+ stuffTallyId + ", sbTransId=" + sbTransId + ", stuffTallyLineId=" + stuffTallyLineId
					+ ", profitcentreId=" + profitcentreId + ", cartingTransId=" + cartingTransId + ", sbLineId="
					+ sbLineId + ", cartingLineId=" + cartingLineId + ", sbNo=" + sbNo + ", movementReqId="
					+ movementReqId + ", movementType=" + movementType + ", stuffTallyDate=" + stuffTallyDate
					+ ", stuffId=" + stuffId + ", stuffDate=" + stuffDate + ", sbDate=" + sbDate + ", shift=" + shift
					+ ", agentSealNo=" + agentSealNo + ", vesselId=" + vesselId + ", voyageNo=" + voyageNo
					+ ", rotationNo=" + rotationNo + ", rotationDate=" + rotationDate + ", pol=" + pol + ", terminal="
					+ terminal + ", pod=" + pod + ", finalPod=" + finalPod + ", containerNo=" + containerNo
					+ ", containerStatus=" + containerStatus + ", asrContainerStatus=" + asrContainerStatus
					+ ", currentLocation=" + currentLocation + ", periodFrom=" + periodFrom + ", gateInId=" + gateInId
					+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", containerCondition="
					+ containerCondition + ", crgYardLocation=" + crgYardLocation + ", crgYardBlock=" + crgYardBlock
					+ ", crgBlockCellNo=" + crgBlockCellNo + ", yardLocation=" + yardLocation + ", yardBlock="
					+ yardBlock + ", blockCellNo=" + blockCellNo + ", yardLocation1=" + yardLocation1 + ", yardBlock1="
					+ yardBlock1 + ", blockCellNo1=" + blockCellNo1 + ", yardPackages=" + yardPackages
					+ ", cellAreaAllocated=" + cellAreaAllocated + ", onAccountOf=" + onAccountOf + ", cha=" + cha
					+ ", stuffRequestQty=" + stuffRequestQty + ", stuffedQty=" + stuffedQty + ", prvStuffedQty="
					+ prvStuffedQty + ", balanceQty=" + balanceQty + ", cargoWeight=" + cargoWeight
					+ ", totalCargoWeight=" + totalCargoWeight + ", totalGrossWeight=" + totalGrossWeight
					+ ", grossWeight=" + grossWeight + ", weighmentFlag=" + weighmentFlag + ", weighmentDone="
					+ weighmentDone + ", weighmentDate=" + weighmentDate + ", weighmentWeight=" + weighmentWeight
					+ ", weighmentPassNo=" + weighmentPassNo + ", tareWeight=" + tareWeight + ", areaReleased="
					+ areaReleased + ", genSetRequired=" + genSetRequired + ", haz=" + haz + ", imoCode=" + imoCode
					+ ", containerInvoiceType=" + containerInvoiceType + ", item=" + item + ", shippingAgent="
					+ shippingAgent + ", shippingLine=" + shippingLine + ", commodity=" + commodity + ", customsSealNo="
					+ customsSealNo + ", viaNo=" + viaNo + ", cartingDate=" + cartingDate + ", icdHub=" + icdHub
					+ ", exporterName=" + exporterName + ", consignee=" + consignee + ", fob=" + fob + ", coverDetails="
					+ coverDetails + ", coverDate=" + coverDate + ", holdingAgent=" + holdingAgent
					+ ", holdingAgentName=" + holdingAgentName + ", holdDate=" + holdDate + ", releaseDate="
					+ releaseDate + ", holdRemarks=" + holdRemarks + ", clpStatus=" + clpStatus + ", clpCreatedBy="
					+ clpCreatedBy + ", clpCreatedDate=" + clpCreatedDate + ", clpApprovedBy=" + clpApprovedBy
					+ ", clpApprovedDate=" + clpApprovedDate + ", gatePassNo=" + gatePassNo + ", gateOutId=" + gateOutId
					+ ", gateOutDate=" + gateOutDate + ", berthingDate=" + berthingDate + ", gateOpenDate="
					+ gateOpenDate + ", sealType=" + sealType + ", sealDev=" + sealDev + ", docType=" + docType
					+ ", docNo=" + docNo + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
					+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy="
					+ approvedBy + ", approvedDate=" + approvedDate + ", clpConfirmStatus=" + clpConfirmStatus
					+ ", clpConfirmBy=" + clpConfirmBy + ", clpConfirmDate=" + clpConfirmDate + ", clpPcsStatus="
					+ clpPcsStatus + ", clpPcsMsgCreStatus=" + clpPcsMsgCreStatus + ", clpPcsMsgCreDate="
					+ clpPcsMsgCreDate + ", clpPcsMsgAmdCreDate=" + clpPcsMsgAmdCreDate + ", documentNumber="
					+ documentNumber + ", commonReferenceNumber=" + commonReferenceNumber + ", amdDocumentNumber="
					+ amdDocumentNumber + ", amdCommonReferenceNumber=" + amdCommonReferenceNumber + ", sfJobNo="
					+ sfJobNo + ", sfJobDate=" + sfJobDate + ", asrJobNo=" + asrJobNo + ", asrJobDate=" + asrJobDate
					+ ", cimNo=" + cimNo + ", cimDate=" + cimDate + ", bondNo=" + bondNo + ", dpJobNo=" + dpJobNo
					+ ", dpJobDate=" + dpJobDate + ", dtJobNo=" + dtJobNo + ", dtJobDate=" + dtJobDate + ", fromPkg="
					+ fromPkg + ", toPkg=" + toPkg + ", isSfAck=" + isSfAck + ", ackSfStatus=" + ackSfStatus
					+ ", isAsrAck=" + isAsrAck + ", ackAsrStatus=" + ackAsrStatus + ", isDpAck=" + isDpAck
					+ ", ackDpStatus=" + ackDpStatus + ", isDtAck=" + isDtAck + ", ackDtStatus=" + ackDtStatus
					+ ", isSfCancelStatus=" + isSfCancelStatus + ", sfCancelCreatedDate=" + sfCancelCreatedDate
					+ ", sfCancelAckDate=" + sfCancelAckDate + ", isSfCancelDesc=" + isSfCancelDesc + ", othPartyId="
					+ othPartyId + ", invoiceAssesed=" + invoiceAssesed + ", assesmentId=" + assesmentId
					+ ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", creditType=" + creditType
					+ ", invoiceCategory=" + invoiceCategory + ", billAmt=" + billAmt + ", invoiceAmt=" + invoiceAmt
					+ ", backToTownRemark=" + backToTownRemark + ", stuffTallyFlag=" + stuffTallyFlag
					+ ", stuffTallyWoTransId=" + stuffTallyWoTransId + ", stuffTallyCutWoTransDate="
					+ stuffTallyCutWoTransDate + ", ssrTransId=" + ssrTransId + ", nopGrossWeight=" + nopGrossWeight
					+ ", deliveryOrderNo=" + deliveryOrderNo + ", reworkFlag=" + reworkFlag + ", reworkId=" + reworkId
					+ ", reworkDate=" + reworkDate + ", payLoad=" + payLoad + ", stuffMode=" + stuffMode
					+ ", formThirteenEntryFlag=" + formThirteenEntryFlag + ", formThirteenEntryRemarks="
					+ formThirteenEntryRemarks + ", formThirteenEntryDate=" + formThirteenEntryDate
					+ ", formThirteenEntryUser=" + formThirteenEntryUser + ", calGrossWt=" + calGrossWt
					+ ", stuffLineId=" + stuffLineId + ", eqActivityFlag=" + eqActivityFlag + ", typeOfPackage="
					+ typeOfPackage + ", vesselName=" + vesselName + ", chaName=" + chaName + ", sbPackages="
					+ sbPackages + ", stuffedQuantity=" + stuffedQuantity + ", sbWt=" + sbWt + ", gateInDate="
					+ gateInDate + ", cargoType=" + cargoType + "]";
		}
	    
	    
		
		@Override
		public Object clone() throws CloneNotSupportedException {
		    return super.clone();
		}




	
		
		
//		BufferTally
		
		
		


		public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
				int stuffTallyLineId, String profitcentreId, String sbLineId, String sbNo, String movementType,
				Date stuffTallyDate, Date sbDate, String shift, String agentSealNo, String vesselId, String voyageNo,
				String rotationNo, Date rotationDate, String pol, String terminal, String pod, String finalPod,
				String containerNo, String containerStatus, Date periodFrom, String gateInId, String containerSize,
				String containerType, String containerCondition, String onAccountOf, String cha, BigDecimal stuffedQty,
				BigDecimal prvStuffedQty, BigDecimal cargoWeight, BigDecimal totalCargoWeight,
				BigDecimal totalGrossWeight, BigDecimal grossWeight, BigDecimal tareWeight, BigDecimal areaReleased,
				String haz, String imoCode, String shippingAgent, String shippingLine, String commodity,
				String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
				Date berthingDate, Date gateOpenDate, String status, String createdBy, Date createdDate,
				String editedBy, Date editedDate, String approvedBy, Date approvedDate, String stuffTallyFlag,
				BigDecimal nopGrossWeight, String deliveryOrderNo, String stuffMode, String typeOfPackage,
				String vesselName, BigDecimal sbPackages, BigDecimal sbWt,
				String shippingLineName, String shippingAgentName,String terminalName, String finalPodName) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.stuffTallyId = stuffTallyId;
			this.sbTransId = sbTransId;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.sbLineId = sbLineId;
			this.sbNo = sbNo;
			this.movementType = movementType;
			this.stuffTallyDate = stuffTallyDate;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.rotationNo = rotationNo;
			this.rotationDate = rotationDate;
			this.pol = pol;
			this.terminal = terminal;
			this.pod = pod;
			this.finalPod = finalPod;
			this.containerNo = containerNo;
			this.containerStatus = containerStatus;
			this.periodFrom = periodFrom;
			this.gateInId = gateInId;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerCondition = containerCondition;
			this.onAccountOf = onAccountOf;
			this.cha = cha;
			this.stuffedQty = stuffedQty;
			this.prvStuffedQty = prvStuffedQty;
			this.cargoWeight = cargoWeight;
			this.totalCargoWeight = totalCargoWeight;
			this.totalGrossWeight = totalGrossWeight;
			this.grossWeight = grossWeight;
			this.tareWeight = tareWeight;
			this.areaReleased = areaReleased;
			this.haz = haz;
			this.imoCode = imoCode;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.commodity = commodity;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.exporterName = exporterName;
			this.consignee = consignee;
			this.fob = fob;
			this.berthingDate = berthingDate;
			this.gateOpenDate = gateOpenDate;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.stuffTallyFlag = stuffTallyFlag;
			this.nopGrossWeight = nopGrossWeight;
			this.deliveryOrderNo = deliveryOrderNo;
			this.stuffMode = stuffMode;
			this.typeOfPackage = typeOfPackage;
			this.vesselName = vesselName;
			this.sbPackages = sbPackages;
			this.sbWt = sbWt;
			this.shippingLineName = shippingLineName;
			this.shippingAgentName = shippingAgentName;			
			this.terminalName = terminalName;
			this.finalPodName = finalPodName;
		}

		
		


		// Export Main Search

				public ExportStuffTally(String stuffTallyId, String containerNo, String gateInId, String reworkId) {
					super();
					this.stuffTallyId = stuffTallyId;
					this.containerNo = containerNo;
					this.gateInId = gateInId;
					this.reworkId = reworkId;
					}







				public ExportStuffTally(String stuffTallyId, String sbTransId, int stuffTallyLineId, String sbLineId,
						String sbNo, String containerNo, String gateInId, String reworkId) {
					super();
					this.stuffTallyId = stuffTallyId;
					this.sbTransId = sbTransId;
					this.stuffTallyLineId = stuffTallyLineId;
					this.sbLineId = sbLineId;
					this.sbNo = sbNo;
					this.containerNo = containerNo;
					this.gateInId = gateInId;
					this.reworkId = reworkId;
				}

				
		
				
//				Stuff created By
					

					public ExportStuffTally(String stuffTallyId, String sbTransId, String sbNo, String movementType,
							Date stuffTallyDate, Date sbDate, String agentSealNo, String vesselId, String voyageNo,
							String rotationNo, Date rotationDate, String terminal, String pod, String finalPod, String containerNo,String stuffId,
							String gateInId, String containerSize, String containerType, String onAccountOf, String cha,
							BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal balanceQty,BigDecimal cargoWeight, BigDecimal totalCargoWeight,
							BigDecimal tareWeight, String shippingAgent, String shippingLine, String commodity,
							String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
							String status, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate, String deliveryOrderNo,
							String stuffMode, String vesselName, BigDecimal sbPackages, BigDecimal stuffedQuantity, BigDecimal sbWt,
							Date gateInDate, String cargoType, Date berthingDate, String reworkFlag, String createdBy) {
						this.stuffTallyId = stuffTallyId;
						this.sbTransId = sbTransId;
						this.sbNo = sbNo;
						this.movementType = movementType;
						this.stuffTallyDate = stuffTallyDate;
						this.sbDate = sbDate;
						this.agentSealNo = agentSealNo;
						this.vesselId = vesselId;
						this.voyageNo = voyageNo;
						this.rotationNo = rotationNo;
						this.rotationDate = rotationDate;
						this.terminal = terminal;
						this.pod = pod;
						this.finalPod = finalPod;
						this.containerNo = containerNo;
						this.stuffId = stuffId;
						this.gateInId = gateInId;
						this.containerSize = containerSize;
						this.containerType = containerType;
						this.onAccountOf = onAccountOf;
						this.cha = cha;
						this.stuffRequestQty = stuffRequestQty;
						this.stuffedQty = stuffedQty;
						this.balanceQty = balanceQty;
						this.cargoWeight = cargoWeight;
						this.totalCargoWeight = totalCargoWeight;
						this.tareWeight = tareWeight;
						this.shippingAgent = shippingAgent;
						this.shippingLine = shippingLine;
						this.commodity = commodity;
						this.customsSealNo = customsSealNo;
						this.viaNo = viaNo;
						this.exporterName = exporterName;
						this.consignee = consignee;
						this.fob = fob;
						this.status = status;
						this.stuffTallyWoTransId = stuffTallyWoTransId;
						this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
						this.deliveryOrderNo = deliveryOrderNo;
						this.stuffMode = stuffMode;
						this.vesselName = vesselName;
						this.sbPackages = sbPackages;
						this.stuffedQuantity = stuffedQuantity;
						this.sbWt = sbWt;
						this.gateInDate = gateInDate;
						this.cargoType = cargoType;
						this.berthingDate = berthingDate;
						this.reworkFlag = reworkFlag;
						this.createdBy = createdBy;
					}
					
					
					
					
//					New select tag
					
					public ExportStuffTally(String stuffTallyId, String sbTransId, String sbNo, String movementType,
							Date stuffTallyDate, Date sbDate, String agentSealNo, String vesselId, String voyageNo,
							String rotationNo, Date rotationDate, String terminal, String pod, String finalPod, String containerNo,String stuffId,
							String gateInId, String containerSize, String containerType, String onAccountOf, String cha,
							BigDecimal stuffRequestQty, BigDecimal stuffedQty, BigDecimal balanceQty,BigDecimal cargoWeight, BigDecimal totalCargoWeight,
							BigDecimal tareWeight, String shippingAgent, String shippingLine, String commodity,
							String customsSealNo, String viaNo, String exporterName, String consignee, BigDecimal fob,
							String status, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate, String deliveryOrderNo,
							String stuffMode, String vesselName, BigDecimal sbPackages, BigDecimal stuffedQuantity, BigDecimal sbWt,
							Date gateInDate, String cargoType, Date berthingDate, String reworkFlag, String createdBy, String approvedBy, BigDecimal length, BigDecimal height, BigDecimal weight, String odcType) {
						this.stuffTallyId = stuffTallyId;
						this.sbTransId = sbTransId;
						this.sbNo = sbNo;
						this.movementType = movementType;
						this.stuffTallyDate = stuffTallyDate;
						this.sbDate = sbDate;
						this.agentSealNo = agentSealNo;
						this.vesselId = vesselId;
						this.voyageNo = voyageNo;
						this.rotationNo = rotationNo;
						this.rotationDate = rotationDate;
						this.terminal = terminal;
						this.pod = pod;
						this.finalPod = finalPod;
						this.containerNo = containerNo;
						this.stuffId = stuffId;
						this.gateInId = gateInId;
						this.containerSize = containerSize;
						this.containerType = containerType;
						this.onAccountOf = onAccountOf;
						this.cha = cha;
						this.stuffRequestQty = stuffRequestQty;
						this.stuffedQty = stuffedQty;
						this.balanceQty = balanceQty;
						this.cargoWeight = cargoWeight;
						this.totalCargoWeight = totalCargoWeight;
						this.tareWeight = tareWeight;
						this.shippingAgent = shippingAgent;
						this.shippingLine = shippingLine;
						this.commodity = commodity;
						this.customsSealNo = customsSealNo;
						this.viaNo = viaNo;
						this.exporterName = exporterName;
						this.consignee = consignee;
						this.fob = fob;
						this.status = status;
						this.stuffTallyWoTransId = stuffTallyWoTransId;
						this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
						this.deliveryOrderNo = deliveryOrderNo;
						this.stuffMode = stuffMode;
						this.vesselName = vesselName;
						this.sbPackages = sbPackages;
						this.stuffedQuantity = stuffedQuantity;
						this.sbWt = sbWt;
						this.gateInDate = gateInDate;
						this.cargoType = cargoType;
						this.berthingDate = berthingDate;
						this.reworkFlag = reworkFlag;
						this.createdBy = createdBy;
						this.approvedBy = approvedBy;
						this.length = length;
						this.height = height;
						this.weight = weight;
						this.odcType = odcType;
 					}



//					ContainerWise tally
					public ExportStuffTally(String companyId, String branchId, String stuffTallyId, String sbTransId,
							int stuffTallyLineId, String profitcentreId, String sbLineId, String sbNo, Date stuffTallyDate,
							String stuffId, Date stuffDate, Date sbDate, String shift, String agentSealNo, String vesselId,
							String voyageNo, String rotationNo, Date rotationDate, String pol, String terminal, String pod,
							String finalPod, String containerNo, String containerStatus, Date periodFrom, String containerSize,
							String containerType, String containerCondition, BigDecimal yardPackages, BigDecimal cellAreaAllocated,
							String onAccountOf, String cha, BigDecimal stuffRequestQty, BigDecimal stuffedQty,
							BigDecimal balanceQty,BigDecimal balQty, BigDecimal cargoWeight, BigDecimal totalGrossWeight, BigDecimal tareWeight,
							BigDecimal areaReleased, String genSetRequired, String haz, String shippingAgent, String shippingLine,
							String commodity, String customsSealNo, String viaNo, String exporterName, String consignee,
							BigDecimal fob, Date berthingDate, Date gateOpenDate, String sealType, String docType, String docNo,
							String status, String createdBy, String stuffTallyWoTransId, Date stuffTallyCutWoTransDate,
							String deliveryOrderNo, String stuffMode, String typeOfPackage, String vesselName, String chaName, BigDecimal totalCargoWeight
							,String approvedBy, BigDecimal length, BigDecimal height, BigDecimal weight, String odcType, String cargoType) {
						this.companyId = companyId;
						this.branchId = branchId;
						this.stuffTallyId = stuffTallyId;
						this.sbTransId = sbTransId;
						this.stuffTallyLineId = stuffTallyLineId;
						this.profitcentreId = profitcentreId;
						this.sbLineId = sbLineId;
						this.sbNo = sbNo;
						this.stuffTallyDate = stuffTallyDate;
						this.stuffId = stuffId;
						this.stuffDate = stuffDate;
						this.sbDate = sbDate;
						this.shift = shift;
						this.agentSealNo = agentSealNo;
						this.vesselId = vesselId;
						this.voyageNo = voyageNo;
						this.rotationNo = rotationNo;
						this.rotationDate = rotationDate;
						this.pol = pol;
						this.terminal = terminal;
						this.pod = pod;
						this.finalPod = finalPod;
						this.containerNo = containerNo;
						this.containerStatus = containerStatus;
						this.periodFrom = periodFrom;
						this.containerSize = containerSize;
						this.containerType = containerType;
						this.containerCondition = containerCondition;
						this.yardPackages = yardPackages;
						this.cellAreaAllocated = cellAreaAllocated;
						this.onAccountOf = onAccountOf;
						this.cha = cha;
						this.stuffRequestQty = stuffRequestQty;
						this.stuffedQty = stuffedQty;
						this.balanceQty = balanceQty;
						this.balQty = balQty;
						this.cargoWeight = cargoWeight;
						this.totalGrossWeight = totalGrossWeight;
						this.tareWeight = tareWeight;
						this.areaReleased = areaReleased;
						this.genSetRequired = genSetRequired;
						this.haz = haz;
						this.shippingAgent = shippingAgent;
						this.shippingLine = shippingLine;
						this.commodity = commodity;
						this.customsSealNo = customsSealNo;
						this.viaNo = viaNo;
						this.exporterName = exporterName;
						this.consignee = consignee;
						this.fob = fob;
						this.berthingDate = berthingDate;
						this.gateOpenDate = gateOpenDate;
						this.sealType = sealType;
						this.docType = docType;
						this.docNo = docNo;
						this.status = status;
						this.createdBy = createdBy;
						this.stuffTallyWoTransId = stuffTallyWoTransId;
						this.stuffTallyCutWoTransDate = stuffTallyCutWoTransDate;
						this.deliveryOrderNo = deliveryOrderNo;
						this.stuffMode = stuffMode;
						this.typeOfPackage = typeOfPackage;
						this.vesselName = vesselName;
						this.chaName = chaName;
						this.totalCargoWeight = totalCargoWeight;
						this.approvedBy = approvedBy;
						this.length = length;
						this.height = height;
						this.weight = weight;
						this.odcType = odcType;
						this.cargoType = cargoType;
					}
			
		    
		
		
	    
}