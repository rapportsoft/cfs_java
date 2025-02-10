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
@Table(name = "cfexpmovementreq")
@IdClass(ExportMovementId.class)
public class ExportMovement {

	 	@Id
	    @Column(name = "Company_Id", length = 6, columnDefinition = "varchar(6) default ''")
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6, columnDefinition = "varchar(6) default ''")
	    private String branchId;

	    @Id
	    @Column(name = "Fin_Year", length = 4, columnDefinition = "varchar(4) default ''")
	    private String finYear;

	    @Id
	    @Column(name = "Movement_Req_Id", length = 10, columnDefinition = "varchar(10) default ''")
	    private String movementReqId;

	    @Id
	    @Column(name = "Movement_Req_Line_Id", length = 4, columnDefinition = "varchar(4) default ''")
	    private String movementReqLineId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Movement_Req_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date movementReqDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    @Column(name = "Movement_Order_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    private Date movementOrderDate;

	    @Column(name = "Stuff_Tally_Id", length = 10, columnDefinition = "varchar(10) default ''")
	    private String stuffTallyId;

	    @Column(name = "Mov_Req_Type", length = 10, columnDefinition = "varchar(10) default ''")
	    private String movReqType;

	    @Column(name = "Stuff_Tally_Line_Id", length = 4, columnDefinition = "varchar(4) default ''")
	    private String stuffTallyLineId;

	    @Column(name = "Profitcentre_Id", length = 6, columnDefinition = "varchar(6) default ''")
	    private String profitcentreId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    @Column(name = "Stuff_Tally_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    private Date stuffTallyDate;

	    @Column(name = "Stuff_Id", length = 10, columnDefinition = "varchar(10) default ''")
	    private String stuffId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    @Column(name = "Stuff_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    private Date stuffDate;

	  
	    @Column(name = "SB_No", length = 15, columnDefinition = "varchar(15) default ''")
	    private String sbNo;

	    @Column(name = "sb_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
	    private String sbTransId;

	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    @Column(name = "SB_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    private Date sbDate;

	    @Column(name = "Shift", length = 6, columnDefinition = "varchar(6) default ''")
	    private String shift;

	    @Column(name = "Agent_Seal_No", length = 15, columnDefinition = "varchar(15) default ''")
	    private String agentSealNo;

	    @Column(name = "Vessel_Id", length = 7, columnDefinition = "varchar(7) default ''")
	    private String vesselId;

	    @Column(name = "Voyage_No", length = 10, columnDefinition = "varchar(10) default ''")
	    private String voyageNo;

	    
	    @Column(name = "POL", length = 100, columnDefinition = "varchar(100) default ''")
	    private String pol;

	    @Column(name = "pod", length = 140, columnDefinition = "varchar(140) default ''")
	    private String pod;

	    @Column(name = "Container_No", length = 11, columnDefinition = "varchar(11) default ''")
	    private String containerNo;

	    @Column(name = "Container_Size", length = 6, columnDefinition = "varchar(6) default ''")
	    private String containerSize;

	    @Column(name = "Container_Type", length = 6, columnDefinition = "varchar(6) default ''")
	    private String containerType;

	    @Column(name = "Container_Status", length = 26, columnDefinition = "varchar(26) default ''")
	    private String containerStatus;

	    @Temporal(TemporalType.TIMESTAMP)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    @Column(name = "Period_From", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    private Date periodFrom;

	    @Column(name = "Acc_Sr_no", columnDefinition = "int default 1")
	    private int accSrNo;

	    @Column(name = "On_Account_Of", length = 6, columnDefinition = "varchar(6) default ''")
	    private String onAccountOf;
	  
		@Column(name = "Total_Cargo_Wt", precision = 10, scale = 3, columnDefinition = "decimal(10,3) default 0.000")
	    private BigDecimal totalCargoWt;

	    @Column(name = "Gross_Weight", precision = 16, scale = 4, columnDefinition = "decimal(16,4) default 0.0000")
	    private BigDecimal grossWeight;

	    @Column(name = "Tare_Weight", precision = 12, scale = 3, columnDefinition = "decimal(12,3) default 0.000")
	    private BigDecimal tareWeight;

	    @Column(name = "Shipping_Agent", length = 6, columnDefinition = "varchar(6) default ''")
	    private String shippingAgent;

	    @Column(name = "Shipping_Line", length = 6, columnDefinition = "varchar(6) default NULL")
	    private String shippingLine;


	    @Column(name = "Customs_Seal_No", length = 15, columnDefinition = "varchar(15) default ''")
	    private String customsSealNo;

	    @Column(name = "VIA_No", length = 10, columnDefinition = "varchar(10) default ''")
	    private String viaNo;
	    

	    @Column(name = "Holding_Agent", length = 1, columnDefinition = "char(1) default ''")
	    private String holdingAgent;

	    @Column(name = "Holding_Agent_Name", length = 35, columnDefinition = "varchar(35) default ''")
	    private String holdingAgentName;

	    @Column(name = "Hold_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date holdDate;

	    @Column(name = "Release_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date releaseDate;

	    @Column(name = "Hold_Remarks", length = 150, columnDefinition = "varchar(150) default ''")
	    private String holdRemarks;
	   

	    @Column(name = "Gate_Pass_No", length = 10, columnDefinition = "varchar(10) default ''")
	    private String gatePassNo;

	    @Column(name = "Add_Services", length = 1, columnDefinition = "char(1) default 'N'")
	    private String addServices;

	    @Column(name = "Type_of_Container", length = 30, columnDefinition = "varchar(30) default ''")
	    private String typeOfContainer;

	    @Column(name = "Gate_Out_Id", length = 10, columnDefinition = "varchar(10) default ''")
	    private String gateOutId;

	    @Column(name = "Gate_Out_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date gateOutDate;

	    @Column(name = "imp_Sr_no", columnDefinition = "int default 1")
	    private int impSrNo;

	   
	    @Column(name = "Billing_Party", length = 3, columnDefinition = "char(3) default 'CHA'")
	    private String billingParty;

	    @Column(name = "IGST", length = 1, columnDefinition = "char(1) default 'N'")
	    private String igst;

	    @Column(name = "CGST", length = 1, columnDefinition = "char(1) default 'N'")
	    private String cgst;

	    @Column(name = "SGST", length = 1, columnDefinition = "char(1) default 'N'")
	    private String sgst;

	    @Column(name = "Status", length = 1, columnDefinition = "char(1) default ''")
	    private String status;

	    @Column(name = "comments", length = 150, columnDefinition = "varchar(150) DEFAULT NULL")
	    private String comments = null; // varchar(150) DEFAULT NULL

	    @Column(name = "Created_By", length = 10, columnDefinition = "varchar(10) NOT NULL DEFAULT ''")
	    private String createdBy = ""; // varchar(10) NOT NULL DEFAULT ''

	    @Column(name = "Created_Date", columnDefinition = "datetime NOT NULL DEFAULT '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date createdDate = new Date(0); // datetime NOT NULL DEFAULT '0000-00-00 00:00:00'

	    @Column(name = "Edited_By", length = 10, nullable = true, columnDefinition = "varchar(10) DEFAULT NULL")
	    private String editedBy = null; // varchar(10) DEFAULT NULL

	    @Column(name = "Edited_Date", nullable = true, columnDefinition = "datetime DEFAULT '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date editedDate = new Date(0); // datetime DEFAULT '0000-00-00 00:00:00'

	    @Column(name = "Approved_By", length = 10, nullable = true, columnDefinition = "varchar(10) DEFAULT NULL")
	    private String approvedBy = null; // varchar(10) DEFAULT NULL

	    @Column(name = "Approved_Date", nullable = true, columnDefinition = "datetime DEFAULT '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date approvedDate = new Date(0); // datetime DEFAULT '0000-00-00 00:00:00'

	    @Column(name = "CURRENT_LOCATION", length = 30, nullable = true, columnDefinition = "varchar(30) DEFAULT NULL")
	    private String currentLocation = null; // varchar(30) DEFAULT NULL

	    @Column(name = "oth_party_Id", length = 10, columnDefinition = "varchar(10) NOT NULL DEFAULT ''")
	    private String othPartyId = ""; // varchar(10) NOT NULL DEFAULT ''

	    @Column(name = "Invoice_Assesed", columnDefinition = "char(1) NOT NULL DEFAULT 'N'")
	    private String invoiceAssessed = "N"; // char(1) NOT NULL DEFAULT 'N'

	    @Column(name = "Assesment_Id", length = 20, columnDefinition = "varchar(20) NOT NULL DEFAULT ''")
	    private String assessmentId = ""; // varchar(20) NOT NULL DEFAULT ''

	    @Column(name = "Invoice_No", length = 16, columnDefinition = "varchar(16) NOT NULL DEFAULT ''")
	    private String invoiceNo = ""; // varchar(16) NOT NULL DEFAULT ''

	    @Column(name = "Invoice_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date invoiceDate; // datetime NOT NULL DEFAULT '0000-00-00 00:00:00'

	    @Column(name = "Credit_Type", columnDefinition = "char(1) NOT NULL DEFAULT 'N'")
	    private String creditType = "N"; // char(1) NOT NULL DEFAULT 'N'

	    @Column(name = "Invoice_Category", length = 10, columnDefinition = "varchar(10) NOT NULL DEFAULT 'SINGLE'")
	    private String invoiceCategory = "SINGLE"; // varchar(10) NOT NULL DEFAULT 'SINGLE'

	    @Column(name = "Bill_Amt", columnDefinition = "decimal(12,2) NOT NULL DEFAULT '0.00'")
	    private BigDecimal billAmt = BigDecimal.ZERO; // decimal(12,2) NOT NULL DEFAULT '0.00'

	    @Column(name = "Invoice_Amt", columnDefinition = "decimal(12,2) NOT NULL DEFAULT '0.00'")
	    private BigDecimal invoiceAmt = BigDecimal.ZERO; // decimal(12,2) NOT NULL DEFAULT '0.00'

	    @Column(name = "Force_Entry_Flag", nullable = true, columnDefinition = "char(1) DEFAULT 'N'")
	    private String forceEntryFlag = "N"; // char(1) DEFAULT 'N'

	    @Column(name = "Force_Entry_Date", columnDefinition = "datetime DEFAULT '0000-00-00 00:00:00'")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date forceEntryDate = new Date(0); // datetime DEFAULT '0000-00-00 00:00:00'

	    @Column(name = "Force_Entry_Approval", length = 50, nullable = true, columnDefinition = "varchar(50) DEFAULT NULL")
	    private String forceEntryApproval = null; // varchar(50) DEFAULT NULL

	    @Column(name = "Force_Entry_Remarks", length = 250, nullable = true, columnDefinition = "varchar(250) DEFAULT NULL")
	    private String forceEntryRemarks = null; // varchar(250) DEFAULT NULL

	    @Column(name = "SSR_Trans_Id", length = 12, columnDefinition = "varchar(12) NOT NULL DEFAULT ''")
	    private String sSRTransId = ""; // varchar(12) NOT NULL DEFAULT ''

	    @Column(name = "Force_Entry_Flag_INV", columnDefinition = "char(1) NOT NULL DEFAULT 'N'")
	    private String forceEntryFlagInv = "N"; // char(1) NOT NULL DEFAULT 'N'

	    @Column(name = "Trailer_Type", length = 30, columnDefinition = "varchar(30) NOT NULL DEFAULT ''")
	    private String trailerType = ""; // varchar(30) NOT NULL DEFAULT ''

	    
	    @Transient
	    private String shippingLineName;

	    @Transient
	    private String shippingAgentName;
	    
	    @Transient
	    private String vesselName;
	    
	    
	    @Transient
	    private String gateInId;
	    
	    @Transient
	    private String cha;
	    
	    @Transient
	    private String chaName;
	    
	    
	    
	    
	    
	    
		public String getCha() {
			return cha;
		}

		public void setCha(String cha) {
			this.cha = cha;
		}

		public String getChaName() {
			return chaName;
		}

		public void setChaName(String chaName) {
			this.chaName = chaName;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
		}

		public ExportMovement() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		

		public ExportMovement(String companyId, String branchId, String finYear, String movementReqId,
				String movementReqLineId, Date movementReqDate, Date movementOrderDate, String stuffTallyId,
				String movReqType, String stuffTallyLineId, String profitcentreId, Date stuffTallyDate, String stuffId,
				Date stuffDate, String sbNo, String sbTransId, Date sbDate, String shift, String agentSealNo,
				String vesselId, String voyageNo, String pol, String pod, String containerNo, String containerSize,
				String containerType, String containerStatus, Date periodFrom, int accSrNo, String onAccountOf,
				BigDecimal totalCargoWt, BigDecimal grossWeight, BigDecimal tareWeight, String shippingAgent,
				String shippingLine, String customsSealNo, String viaNo, String holdingAgent, String holdingAgentName,
				Date holdDate, Date releaseDate, String holdRemarks, String gatePassNo, String addServices,
				String typeOfContainer, String gateOutId, Date gateOutDate, int impSrNo, String billingParty,
				String igst, String cgst, String sgst, String status, String comments, String createdBy,
				Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
				String currentLocation, String othPartyId, String invoiceAssessed, String assessmentId,
				String invoiceNo, Date invoiceDate, String creditType, String invoiceCategory, BigDecimal billAmt,
				BigDecimal invoiceAmt, String forceEntryFlag, Date forceEntryDate, String forceEntryApproval,
				String forceEntryRemarks, String sSRTransId, String forceEntryFlagInv, String trailerType,
				String shippingLineName, String shippingAgentName, String vesselName, String gateInId, String cha,
				String chaName) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.movementReqId = movementReqId;
			this.movementReqLineId = movementReqLineId;
			this.movementReqDate = movementReqDate;
			this.movementOrderDate = movementOrderDate;
			this.stuffTallyId = stuffTallyId;
			this.movReqType = movReqType;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.stuffTallyDate = stuffTallyDate;
			this.stuffId = stuffId;
			this.stuffDate = stuffDate;
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.pol = pol;
			this.pod = pod;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerStatus = containerStatus;
			this.periodFrom = periodFrom;
			this.accSrNo = accSrNo;
			this.onAccountOf = onAccountOf;
			this.totalCargoWt = totalCargoWt;
			this.grossWeight = grossWeight;
			this.tareWeight = tareWeight;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.gatePassNo = gatePassNo;
			this.addServices = addServices;
			this.typeOfContainer = typeOfContainer;
			this.gateOutId = gateOutId;
			this.gateOutDate = gateOutDate;
			this.impSrNo = impSrNo;
			this.billingParty = billingParty;
			this.igst = igst;
			this.cgst = cgst;
			this.sgst = sgst;
			this.status = status;
			this.comments = comments;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.currentLocation = currentLocation;
			this.othPartyId = othPartyId;
			this.invoiceAssessed = invoiceAssessed;
			this.assessmentId = assessmentId;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.creditType = creditType;
			this.invoiceCategory = invoiceCategory;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.forceEntryFlag = forceEntryFlag;
			this.forceEntryDate = forceEntryDate;
			this.forceEntryApproval = forceEntryApproval;
			this.forceEntryRemarks = forceEntryRemarks;
			this.sSRTransId = sSRTransId;
			this.forceEntryFlagInv = forceEntryFlagInv;
			this.trailerType = trailerType;
			this.shippingLineName = shippingLineName;
			this.shippingAgentName = shippingAgentName;
			this.vesselName = vesselName;
			this.gateInId = gateInId;
			this.cha = cha;
			this.chaName = chaName;
		}

		public ExportMovement(String companyId, String branchId, String finYear, String movementReqId,
				String movementReqLineId, Date movementReqDate, Date movementOrderDate, String stuffTallyId,
				String movReqType, String stuffTallyLineId, String profitcentreId, Date stuffTallyDate, String stuffId,
				Date stuffDate, String sbNo, String sbTransId, Date sbDate, String shift, String agentSealNo,
				String vesselId, String voyageNo, String pol, String pod, String containerNo, String containerSize,
				String containerType, String containerStatus, Date periodFrom, int accSrNo, String onAccountOf,
				BigDecimal totalCargoWt, BigDecimal grossWeight, BigDecimal tareWeight, String shippingAgent,
				String shippingLine, String customsSealNo, String viaNo, String holdingAgent, String holdingAgentName,
				Date holdDate, Date releaseDate, String holdRemarks, String gatePassNo, String addServices,
				String typeOfContainer, String gateOutId, Date gateOutDate, int impSrNo, String billingParty,
				String igst, String cgst, String sgst, String status, String comments, String createdBy,
				Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
				String currentLocation, String othPartyId, String invoiceAssessed, String assessmentId,
				String invoiceNo, Date invoiceDate, String creditType, String invoiceCategory, BigDecimal billAmt,
				BigDecimal invoiceAmt, String forceEntryFlag, Date forceEntryDate, String forceEntryApproval,
				String forceEntryRemarks, String sSRTransId, String forceEntryFlagInv, String trailerType,
				String shippingLineName, String shippingAgentName, String vesselName) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.movementReqId = movementReqId;
			this.movementReqLineId = movementReqLineId;
			this.movementReqDate = movementReqDate;
			this.movementOrderDate = movementOrderDate;
			this.stuffTallyId = stuffTallyId;
			this.movReqType = movReqType;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.stuffTallyDate = stuffTallyDate;
			this.stuffId = stuffId;
			this.stuffDate = stuffDate;
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.pol = pol;
			this.pod = pod;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerStatus = containerStatus;
			this.periodFrom = periodFrom;
			this.accSrNo = accSrNo;
			this.onAccountOf = onAccountOf;
			this.totalCargoWt = totalCargoWt;
			this.grossWeight = grossWeight;
			this.tareWeight = tareWeight;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.gatePassNo = gatePassNo;
			this.addServices = addServices;
			this.typeOfContainer = typeOfContainer;
			this.gateOutId = gateOutId;
			this.gateOutDate = gateOutDate;
			this.impSrNo = impSrNo;
			this.billingParty = billingParty;
			this.igst = igst;
			this.cgst = cgst;
			this.sgst = sgst;
			this.status = status;
			this.comments = comments;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.currentLocation = currentLocation;
			this.othPartyId = othPartyId;
			this.invoiceAssessed = invoiceAssessed;
			this.assessmentId = assessmentId;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.creditType = creditType;
			this.invoiceCategory = invoiceCategory;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.forceEntryFlag = forceEntryFlag;
			this.forceEntryDate = forceEntryDate;
			this.forceEntryApproval = forceEntryApproval;
			this.forceEntryRemarks = forceEntryRemarks;
			this.sSRTransId = sSRTransId;
			this.forceEntryFlagInv = forceEntryFlagInv;
			this.trailerType = trailerType;
			this.shippingLineName = shippingLineName;
			this.shippingAgentName = shippingAgentName;
			this.vesselName = vesselName;
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

		public String getMovementReqId() {
			return movementReqId;
		}

		public void setMovementReqId(String movementReqId) {
			this.movementReqId = movementReqId;
		}

		public String getMovementReqLineId() {
			return movementReqLineId;
		}

		public void setMovementReqLineId(String movementReqLineId) {
			this.movementReqLineId = movementReqLineId;
		}

		public Date getMovementReqDate() {
			return movementReqDate;
		}

		public void setMovementReqDate(Date movementReqDate) {
			this.movementReqDate = movementReqDate;
		}

		public Date getMovementOrderDate() {
			return movementOrderDate;
		}

		public void setMovementOrderDate(Date movementOrderDate) {
			this.movementOrderDate = movementOrderDate;
		}

		public String getStuffTallyId() {
			return stuffTallyId;
		}

		public void setStuffTallyId(String stuffTallyId) {
			this.stuffTallyId = stuffTallyId;
		}

		public String getMovReqType() {
			return movReqType;
		}

		public void setMovReqType(String movReqType) {
			this.movReqType = movReqType;
		}

		public String getStuffTallyLineId() {
			return stuffTallyLineId;
		}

		public void setStuffTallyLineId(String stuffTallyLineId) {
			this.stuffTallyLineId = stuffTallyLineId;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
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

		public String getSbNo() {
			return sbNo;
		}

		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}

		public String getSbTransId() {
			return sbTransId;
		}

		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
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

		public String getPol() {
			return pol;
		}

		public void setPol(String pol) {
			this.pol = pol;
		}

		public String getPod() {
			return pod;
		}

		public void setPod(String pod) {
			this.pod = pod;
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

		public String getContainerStatus() {
			return containerStatus;
		}

		public void setContainerStatus(String containerStatus) {
			this.containerStatus = containerStatus;
		}

		public Date getPeriodFrom() {
			return periodFrom;
		}

		public void setPeriodFrom(Date periodFrom) {
			this.periodFrom = periodFrom;
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

		public BigDecimal getTotalCargoWt() {
			return totalCargoWt;
		}

		public void setTotalCargoWt(BigDecimal totalCargoWt) {
			this.totalCargoWt = totalCargoWt;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public BigDecimal getTareWeight() {
			return tareWeight;
		}

		public void setTareWeight(BigDecimal tareWeight) {
			this.tareWeight = tareWeight;
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

		public String getTypeOfContainer() {
			return typeOfContainer;
		}

		public void setTypeOfContainer(String typeOfContainer) {
			this.typeOfContainer = typeOfContainer;
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

		public int getImpSrNo() {
			return impSrNo;
		}

		public void setImpSrNo(int impSrNo) {
			this.impSrNo = impSrNo;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
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

		public String getCurrentLocation() {
			return currentLocation;
		}

		public void setCurrentLocation(String currentLocation) {
			this.currentLocation = currentLocation;
		}

		public String getOthPartyId() {
			return othPartyId;
		}

		public void setOthPartyId(String othPartyId) {
			this.othPartyId = othPartyId;
		}

		public String getInvoiceAssessed() {
			return invoiceAssessed;
		}

		public void setInvoiceAssessed(String invoiceAssessed) {
			this.invoiceAssessed = invoiceAssessed;
		}

		public String getAssessmentId() {
			return assessmentId;
		}

		public void setAssessmentId(String assessmentId) {
			this.assessmentId = assessmentId;
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

		public String getsSRTransId() {
			return sSRTransId;
		}

		public void setsSRTransId(String sSRTransId) {
			this.sSRTransId = sSRTransId;
		}

		public String getForceEntryFlagInv() {
			return forceEntryFlagInv;
		}

		public void setForceEntryFlagInv(String forceEntryFlagInv) {
			this.forceEntryFlagInv = forceEntryFlagInv;
		}

		public String getTrailerType() {
			return trailerType;
		}

		public void setTrailerType(String trailerType) {
			this.trailerType = trailerType;
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

		public String getVesselName() {
			return vesselName;
		}

		public void setVesselName(String vesselName) {
			this.vesselName = vesselName;
		}

		@Override
		public String toString() {
			return "ExportMovement [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
					+ ", movementReqId=" + movementReqId + ", movementReqLineId=" + movementReqLineId
					+ ", movementReqDate=" + movementReqDate + ", movementOrderDate=" + movementOrderDate
					+ ", stuffTallyId=" + stuffTallyId + ", movReqType=" + movReqType + ", stuffTallyLineId="
					+ stuffTallyLineId + ", profitcentreId=" + profitcentreId + ", stuffTallyDate=" + stuffTallyDate
					+ ", stuffId=" + stuffId + ", stuffDate=" + stuffDate + ", sbNo=" + sbNo + ", sbTransId="
					+ sbTransId + ", sbDate=" + sbDate + ", shift=" + shift + ", agentSealNo=" + agentSealNo
					+ ", vesselId=" + vesselId + ", voyageNo=" + voyageNo + ", pol=" + pol + ", pod=" + pod
					+ ", containerNo=" + containerNo + ", containerSize=" + containerSize + ", containerType="
					+ containerType + ", containerStatus=" + containerStatus + ", periodFrom=" + periodFrom
					+ ", accSrNo=" + accSrNo + ", onAccountOf=" + onAccountOf + ", totalCargoWt=" + totalCargoWt
					+ ", grossWeight=" + grossWeight + ", tareWeight=" + tareWeight + ", shippingAgent=" + shippingAgent
					+ ", shippingLine=" + shippingLine + ", customsSealNo=" + customsSealNo + ", viaNo=" + viaNo
					+ ", holdingAgent=" + holdingAgent + ", holdingAgentName=" + holdingAgentName + ", holdDate="
					+ holdDate + ", releaseDate=" + releaseDate + ", holdRemarks=" + holdRemarks + ", gatePassNo="
					+ gatePassNo + ", addServices=" + addServices + ", typeOfContainer=" + typeOfContainer
					+ ", gateOutId=" + gateOutId + ", gateOutDate=" + gateOutDate + ", impSrNo=" + impSrNo
					+ ", billingParty=" + billingParty + ", igst=" + igst + ", cgst=" + cgst + ", sgst=" + sgst
					+ ", status=" + status + ", comments=" + comments + ", createdBy=" + createdBy + ", createdDate="
					+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy="
					+ approvedBy + ", approvedDate=" + approvedDate + ", currentLocation=" + currentLocation
					+ ", othPartyId=" + othPartyId + ", invoiceAssessed=" + invoiceAssessed + ", assessmentId="
					+ assessmentId + ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", creditType="
					+ creditType + ", invoiceCategory=" + invoiceCategory + ", billAmt=" + billAmt + ", invoiceAmt="
					+ invoiceAmt + ", forceEntryFlag=" + forceEntryFlag + ", forceEntryDate=" + forceEntryDate
					+ ", forceEntryApproval=" + forceEntryApproval + ", forceEntryRemarks=" + forceEntryRemarks
					+ ", sSRTransId=" + sSRTransId + ", forceEntryFlagInv=" + forceEntryFlagInv + ", trailerType="
					+ trailerType + ", shippingLineName=" + shippingLineName + ", shippingAgentName="
					+ shippingAgentName + ", vesselName=" + vesselName + "]";
		}

		public ExportMovement(String companyId, String branchId, String finYear, String movementReqId,
				String movementReqLineId, Date movementReqDate, Date movementOrderDate, String stuffTallyId,
				String movReqType, String stuffTallyLineId, String profitcentreId, Date stuffTallyDate, String stuffId,
				Date stuffDate, String sbNo, String sbTransId, Date sbDate, String shift, String agentSealNo,
				String vesselId, String voyageNo, String pol, String pod, String containerNo, String containerSize,
				String containerType, String containerStatus, Date periodFrom, int accSrNo, String onAccountOf,
				BigDecimal totalCargoWt, BigDecimal grossWeight, BigDecimal tareWeight, String shippingAgent,
				String shippingLine, String customsSealNo, String viaNo, String holdingAgent, String holdingAgentName,
				Date holdDate, Date releaseDate, String holdRemarks, String gatePassNo, String addServices,
				String typeOfContainer, String gateOutId, Date gateOutDate, int impSrNo, String billingParty,
				String igst, String cgst, String sgst, String status, String comments, String createdBy,
				Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
				String currentLocation, String othPartyId, String invoiceAssessed, String assessmentId,
				String invoiceNo, Date invoiceDate, String creditType, String invoiceCategory, BigDecimal billAmt,
				BigDecimal invoiceAmt, String forceEntryFlag, Date forceEntryDate, String forceEntryApproval,
				String forceEntryRemarks, String sSRTransId, String forceEntryFlagInv, String trailerType,
				String shippingLineName, String shippingAgentName, String vesselName, String gateInId) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.movementReqId = movementReqId;
			this.movementReqLineId = movementReqLineId;
			this.movementReqDate = movementReqDate;
			this.movementOrderDate = movementOrderDate;
			this.stuffTallyId = stuffTallyId;
			this.movReqType = movReqType;
			this.stuffTallyLineId = stuffTallyLineId;
			this.profitcentreId = profitcentreId;
			this.stuffTallyDate = stuffTallyDate;
			this.stuffId = stuffId;
			this.stuffDate = stuffDate;
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.sbDate = sbDate;
			this.shift = shift;
			this.agentSealNo = agentSealNo;
			this.vesselId = vesselId;
			this.voyageNo = voyageNo;
			this.pol = pol;
			this.pod = pod;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerStatus = containerStatus;
			this.periodFrom = periodFrom;
			this.accSrNo = accSrNo;
			this.onAccountOf = onAccountOf;
			this.totalCargoWt = totalCargoWt;
			this.grossWeight = grossWeight;
			this.tareWeight = tareWeight;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.holdingAgent = holdingAgent;
			this.holdingAgentName = holdingAgentName;
			this.holdDate = holdDate;
			this.releaseDate = releaseDate;
			this.holdRemarks = holdRemarks;
			this.gatePassNo = gatePassNo;
			this.addServices = addServices;
			this.typeOfContainer = typeOfContainer;
			this.gateOutId = gateOutId;
			this.gateOutDate = gateOutDate;
			this.impSrNo = impSrNo;
			this.billingParty = billingParty;
			this.igst = igst;
			this.cgst = cgst;
			this.sgst = sgst;
			this.status = status;
			this.comments = comments;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.currentLocation = currentLocation;
			this.othPartyId = othPartyId;
			this.invoiceAssessed = invoiceAssessed;
			this.assessmentId = assessmentId;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.creditType = creditType;
			this.invoiceCategory = invoiceCategory;
			this.billAmt = billAmt;
			this.invoiceAmt = invoiceAmt;
			this.forceEntryFlag = forceEntryFlag;
			this.forceEntryDate = forceEntryDate;
			this.forceEntryApproval = forceEntryApproval;
			this.forceEntryRemarks = forceEntryRemarks;
			this.sSRTransId = sSRTransId;
			this.forceEntryFlagInv = forceEntryFlagInv;
			this.trailerType = trailerType;
			this.shippingLineName = shippingLineName;
			this.shippingAgentName = shippingAgentName;
			this.vesselName = vesselName;
			this.gateInId = gateInId;
		}

		public ExportMovement(String movementReqId, String movementReqLineId, String sbNo, String sbTransId,
				Date sbDate, String agentSealNo, String pol, String pod, String containerNo, String containerSize,
				String containerType, String containerStatus, BigDecimal grossWeight, String customsSealNo,
				String viaNo, String cha,String chaName) {
			this.movementReqId = movementReqId;
			this.movementReqLineId = movementReqLineId;
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.sbDate = sbDate;
			this.agentSealNo = agentSealNo;
			this.pol = pol;
			this.pod = pod;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerStatus = containerStatus;
			this.grossWeight = grossWeight;
			this.customsSealNo = customsSealNo;
			this.viaNo = viaNo;
			this.cha = cha;
			this.chaName = chaName;
		}

		    
}
