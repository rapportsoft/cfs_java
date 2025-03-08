package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

@Entity
@Table(name = "cfstuffrq_hub")
@IdClass(ExportStuffRequestId.class)
public class StuffRequestHub implements Cloneable {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId = "";

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId = "";

	@Id
	@Column(name = "Fin_Year", length = 4, nullable = false)
	private String finYear = "";

	@Id
	@Column(name = "Stuff_Req_Id", length = 10, nullable = false)
	private String stuffReqId = "";

	@Id
	@Column(name = "SB_Trans_Id", length = 10, nullable = false)
	private String sbTransId = "";

	@Id
	@Column(name = "Stuff_Req_Line_Id", nullable = false)
	private int stuffReqLineId = 0;

	@Column(name = "SB_Line_No", length = 20, nullable = false)
	private String sbLineNo = "";

	@Column(name = "Profitcentre_Id", length = 6, nullable = false)
	private String profitCentreId = "";

	@Column(name = "Stuff_Req_Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date stuffReqDate = new Date(0); // Default: '0000-00-00 00:00:00'

	@Column(name = "SB_No", length = 15, nullable = false)
	private String sbNo = "";

	@Column(name = "SB_Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sbDate = new Date(0); // Default: '0000-00-00 00:00:00'

	@Column(name = "Shift", length = 6, nullable = false)
	private String shift = "";

	@Column(name = "stuff_tally", length = 1)
	private char stuffTally = 'N';

	@Column(name = "Carting_Trans_Id", length = 10)
	private String cartingTransId = "";

	@Column(name = "Carting_Line_Id", length = 4)
	private String cartingLineId = "";

	@Column(name = "Carting_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date cartingTransDate = new Date(0); // Default: '0000-00-00 00:00:00'

	@Column(name = "Total_Cargo_Weight", precision = 12, scale = 2, nullable = false)
	private BigDecimal totalCargoWeight = BigDecimal.ZERO;

	@Column(name = "Shipping_Agent", length = 6)
	private String shippingAgent = "";

	@Column(name = "Shipping_Line", length = 6)
	private String shippingLine = "";

	@Column(name = "Exporter_Name", length = 60)
	private String exporterName;

	@Column(name = "Cargo_Description", length = 250)
	private String cargoDescription = "";

	@Column(name = "On_Account_Of", length = 6)
	private String onAccountOf = "";

	@Column(name = "Vessel_Id", length = 7)
	private String vesselId = "";

	@Column(name = "VIA_No", length = 7)
	private String viaNo = "";

	@Column(name = "Voyage_No", length = 10)
	private String voyageNo = "";

	@Column(name = "Terminal", length = 10)
	private String terminal = "";

	@Column(name = "Cover_Details", length = 10)
	private String coverDetails = "";

	@Column(name = "Cover_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date coverDate = new Date(0); // Default: '0000-00-00 00:00:00'

	@Column(name = "Berthing_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date berthingDate = new Date(0); // Default: '0000-00-00 00:00:00'

	@Column(name = "Gate_Open_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gateOpenDate = new Date(0); // Default: '0000-00-00 00:00:00'

	@Column(name = "Gate_In_Id", length = 10)
	private String gateInId = "";

	@Column(name = "Agent_Seal_No", length = 15)
	private String agentSealNo = "";

	@Column(name = "Tare_Weight", precision = 15, scale = 3)
	private BigDecimal tareWeight = BigDecimal.ZERO;

	@Column(name = "Container_Size", length = 6)
	private String containerSize = "";

	@Column(name = "Container_Type", length = 6)
	private String containerType = "";

	@Column(name = "POD", length = 10)
	private String pod = "";

	@Column(name = "POD_Desc", length = 50)
	private String podDesc = "";

	@Column(name = "Comments", length = 150)
	private String comments = "";

	@Column(name = "Type_Of_Package", length = 6)
	private String typeOfPackage = "";

	@Column(name = "No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal noOfPackages = BigDecimal.ZERO;

	@Column(name = "No_Of_Packages_Stuffed", precision = 8, scale = 0)
	private BigDecimal noOfPackagesStuffed = BigDecimal.ZERO;

	@Column(name = "Cont_Stuff_Packages", precision = 8, scale = 0)
	private BigDecimal contStuffPackages = BigDecimal.ZERO;

	@Column(name = "CBM", length = 50)
	private String cbm = "";

	@Column(name = "Container_No", length = 11)
	private String containerNo;

	@Column(name = "Current_Location", length = 26)
	private String currentLocation;

	@Column(name = "Period_From")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date periodFrom; // Default: '0000-00-00 00:00:00'

	@Column(name = "container_health", length = 6)
	private String containerHealth;

	@Column(name = "Cargo_Weight", precision = 12, scale = 2)
	private BigDecimal cargoWeight = BigDecimal.ZERO;

	@Column(name = "Status", length = 1, nullable = false)
	private String status = "";

	@Column(name = "Created_By", length = 10, nullable = false)
	private String createdBy = "";

	@Column(name = "Created_Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate; // Default: '0000-00-00 00:00:00'

	@Column(name = "Edited_By", length = 10)
	private String editedBy;

	@Column(name = "Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate; // Default: '0000-00-00 00:00:00'

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate; // Default: '0000-00-00 00:00:00'

	@Column(name = "Labour", length = 1)
	private char labour = 'N';

	@Column(name = "Fk_3MT", length = 1)
	private char fk3MT = 'N';

	@Column(name = "Fk_5MT", length = 1)
	private char fk5MT = 'N';

	@Column(name = "Fk_10MT", length = 1)
	private char fk10MT = 'N';

	@Column(name = "Hydra_12MT", length = 1)
	private char hydra12MT = 'N';

	@Column(name = "crane", length = 1)
	private char crane = 'N';

	@Column(name = "SSR_Trans_Id", length = 20)
	private String ssrTransId = "";

	@Column(name = "GatePass_Id", length = 10)
	private String gatePassId = "";

	@Column(name = "Custom_Seal_No", length = 15)
	private String customSealNo = "";

	@Column(name = "Gross_Weight", precision = 15, scale = 3)
	private BigDecimal grossWeight = BigDecimal.ZERO;

	@Column(name = "Weight_Taken_In", precision = 15, scale = 3)
	private BigDecimal weightTakenIn = BigDecimal.ZERO;

	@Column(name = "CHA_Name", length = 50)
	private String chaName = "";

	@Column(name = "Commodity_Code", length = 30)
	private String commodityCode = "";

	@Column(name = "Commodity_DESC", length = 100)
	private String commodityDesc = "";

	@Column(name = "Pkgs", precision = 16, scale = 3)
	private BigDecimal pkgs = BigDecimal.ZERO;

	@Column(name = "Weight", precision = 16, scale = 3)
	private BigDecimal weight = BigDecimal.ZERO;

	@Column(name = "CHA", length = 30)
	private String cha = "";

	@Column(name = "Movement_Order_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date movementOrderDate;

	@Column(name = "Placement_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date placementDate;

	@Column(name = "Stuff_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date stuffDate;

	@Column(name = "Begin_Date_Time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date beginDateTime;

	@Column(name = "End_Date_Time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date endDateTime;

	@Column(name = "Mtycountwt", precision = 10, scale = 3)
	private BigDecimal mtyCountWt = BigDecimal.ZERO;

	@Column(name = "StuffingLocation", length = 30)
	private String stuffingLocation = "";

	@Column(name = "Movement_Type", length = 20)
	private String movementType = "";

	@Column(name = "Transporter", length = 30)
	private String transporter = "";

	@Column(name = "Destination", length = 20)
	private String destination = "";

	@Column(name = "Hold_Status", length = 1)
	private char holdStatus = 'N';

	@Column(name = "SMTP_flag", length = 1)
	private char smtpFlag = 'N';

	@Column(name = "Rotation_No")
	private String rotationNo;

	@Column(name = "Rotation_Date")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date rotationDate;

	@Transient
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date containerGateInDate;

	@Transient
	private String shippingLineName;

	@Transient
	private String shippingAgentName;

	@Transient
	private String onAccountName;

	@Transient
	private String vesselName;

	@Transient
	private String consignee;

	@Transient
	private BigDecimal fob;

	@Transient
	private BigDecimal cellAreaAllocated;

	@Transient
	private BigDecimal yardPackages;

	@Transient
	private BigDecimal sbPackages;

	@Transient
	private BigDecimal stuffedQuantity;

	@Transient
	private BigDecimal sbWt;

	@Transient
	private String cargoType;

	@Column(name = "Stuffed_Qty", precision = 8, scale = 0)
	private BigDecimal stuffedQty;
	
	transient private Integer stuffReqQty =  0 ;

	transient private BigDecimal stuffReqWeight ;
	
	 transient private Integer balanceQuantity;
	 transient private BigDecimal balanceWeight;
	
	    

	public Integer getStuffReqQty() {
			return stuffReqQty;
		}

		public void setStuffReqQty(Integer stuffReqQty) {
			this.stuffReqQty = stuffReqQty;
		}

		public BigDecimal getStuffReqWeight() {
			return stuffReqWeight;
		}

		public void setStuffReqWeight(BigDecimal stuffReqWeight) {
			this.stuffReqWeight = stuffReqWeight;
		}

		public Integer getBalanceQuantity() {
			return balanceQuantity;
		}

		public void setBalanceQuantity(Integer balanceQuantity) {
			this.balanceQuantity = balanceQuantity;
		}

		public BigDecimal getBalanceWeight() {
			return balanceWeight;
		}

		public void setBalanceWeight(BigDecimal balanceWeight) {
			this.balanceWeight = balanceWeight;
		}

	public StuffRequestHub() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
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

	public BigDecimal getCellAreaAllocated() {
		return cellAreaAllocated;
	}

	public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
		this.cellAreaAllocated = cellAreaAllocated;
	}

	public BigDecimal getYardPackages() {
		return yardPackages;
	}

	public void setYardPackages(BigDecimal yardPackages) {
		this.yardPackages = yardPackages;
	}

	public BigDecimal getSbPackages() {
		return sbPackages;
	}

	public void setSbPackages(BigDecimal sbPackages) {
		this.sbPackages = sbPackages;
	}

	public BigDecimal getStuffedQuantity() {
		return stuffedQuantity;
	}

	public void setStuffedQuantity(BigDecimal stuffedQuantity) {
		this.stuffedQuantity = stuffedQuantity;
	}

	public BigDecimal getSbWt() {
		return sbWt;
	}

	public void setSbWt(BigDecimal sbWt) {
		this.sbWt = sbWt;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public BigDecimal getStuffedQty() {
		return stuffedQty;
	}

	public void setStuffedQty(BigDecimal stuffedQty) {
		this.stuffedQty = stuffedQty;
	}

	public StuffRequestHub(String companyId, String branchId, String finYear, String stuffReqId, String sbTransId,
			int stuffReqLineId, String sbLineNo, String profitCentreId, Date stuffReqDate, String sbNo, Date sbDate,
			String shift, char stuffTally, String cartingTransId, String cartingLineId, Date cartingTransDate,
			BigDecimal totalCargoWeight, String shippingAgent, String shippingLine, String exporterName,
			String cargoDescription, String onAccountOf, String vesselId, String viaNo, String voyageNo,
			String terminal, String coverDetails, Date coverDate, Date berthingDate, Date gateOpenDate, String gateInId,
			String agentSealNo, BigDecimal tareWeight, String containerSize, String containerType, String pod,
			String podDesc, String comments, String typeOfPackage, BigDecimal noOfPackages,
			BigDecimal noOfPackagesStuffed, BigDecimal contStuffPackages, String cbm, String containerNo,
			String currentLocation, Date periodFrom, String containerHealth, BigDecimal cargoWeight, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			char labour, char fk3mt, char fk5mt, char fk10mt, char hydra12mt, char crane, String ssrTransId,
			String gatePassId, String customSealNo, BigDecimal grossWeight, BigDecimal weightTakenIn, String chaName,
			String jarDtlId, String jarDtlDesc, BigDecimal pkgs, BigDecimal weight, String cha, Date movementOrderDate,
			Date placementDate, Date stuffDate, Date beginDateTime, Date endDateTime, BigDecimal mtyCountWt,
			String stuffingLocation, String movementType, String transporter, String destination, char holdStatus,
			char smtpFlag, Date containerGateInDate, String shippingLineName, String shippingAgentName,
			String onAccountName, String vesselName, String consignee, BigDecimal fob, BigDecimal cellAreaAllocated,
			BigDecimal yardPackages, BigDecimal sbPackages, BigDecimal stuffedQuantity, BigDecimal sbWt,
			String cargoType, BigDecimal stuffedQty) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.stuffReqId = stuffReqId;
		this.sbTransId = sbTransId;
		this.stuffReqLineId = stuffReqLineId;
		this.sbLineNo = sbLineNo;
		this.profitCentreId = profitCentreId;
		this.stuffReqDate = stuffReqDate;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.shift = shift;
		this.stuffTally = stuffTally;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.cartingTransDate = cartingTransDate;
		this.totalCargoWeight = totalCargoWeight;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.exporterName = exporterName;
		this.cargoDescription = cargoDescription;
		this.onAccountOf = onAccountOf;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.terminal = terminal;
		this.coverDetails = coverDetails;
		this.coverDate = coverDate;
		this.berthingDate = berthingDate;
		this.gateOpenDate = gateOpenDate;
		this.gateInId = gateInId;
		this.agentSealNo = agentSealNo;
		this.tareWeight = tareWeight;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.pod = pod;
		this.podDesc = podDesc;
		this.comments = comments;
		this.typeOfPackage = typeOfPackage;
		this.noOfPackages = noOfPackages;
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.contStuffPackages = contStuffPackages;
		this.cbm = cbm;
		this.containerNo = containerNo;
		this.currentLocation = currentLocation;
		this.periodFrom = periodFrom;
		this.containerHealth = containerHealth;
		this.cargoWeight = cargoWeight;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.labour = labour;
		fk3MT = fk3mt;
		fk5MT = fk5mt;
		fk10MT = fk10mt;
		hydra12MT = hydra12mt;
		this.crane = crane;
		this.ssrTransId = ssrTransId;
		this.gatePassId = gatePassId;
		this.customSealNo = customSealNo;
		this.grossWeight = grossWeight;
		this.weightTakenIn = weightTakenIn;
		this.chaName = chaName;
		this.commodityCode = jarDtlId;
		this.commodityDesc = jarDtlDesc;
		this.pkgs = pkgs;
		this.weight = weight;
		this.cha = cha;
		this.movementOrderDate = movementOrderDate;
		this.placementDate = placementDate;
		this.stuffDate = stuffDate;
		this.beginDateTime = beginDateTime;
		this.endDateTime = endDateTime;
		this.mtyCountWt = mtyCountWt;
		this.stuffingLocation = stuffingLocation;
		this.movementType = movementType;
		this.transporter = transporter;
		this.destination = destination;
		this.holdStatus = holdStatus;
		this.smtpFlag = smtpFlag;
		this.containerGateInDate = containerGateInDate;
		this.shippingLineName = shippingLineName;
		this.shippingAgentName = shippingAgentName;
		this.onAccountName = onAccountName;
		this.vesselName = vesselName;
		this.consignee = consignee;
		this.fob = fob;
		this.cellAreaAllocated = cellAreaAllocated;
		this.yardPackages = yardPackages;
		this.sbPackages = sbPackages;
		this.stuffedQuantity = stuffedQuantity;
		this.sbWt = sbWt;
		this.cargoType = cargoType;
		this.stuffedQty = stuffedQty;
	}

	public StuffRequestHub(String companyId, String branchId, String finYear, String stuffReqId, String sbTransId,
			int stuffReqLineId, String sbLineNo, String profitcentreId, Date stuffReqDate, String sbNo, Date sbDate,
			String shift, char stuffTally, String stuffTallyId, BigDecimal totalCargoWeight, String shippingAgent,
			String shippingLine, String exporterName, String cargoDescription, String onAccountOf, String vesselId,
			String viaNo, String voyageNo, String terminal, String coverDetails, Date coverDate, Date berthingDate,
			Date gateOpenDate, String gateInId, String agentSealNo, BigDecimal tareWeight, String containerSize,
			String containerType, String pod, String comments, String typeOfPackage, BigDecimal noOfPackages,
			BigDecimal noOfPackagesStuffed, BigDecimal contStuffPackages, String cbm, String containerNo,
			String currentLocation, Date periodFrom, String containerHealth, BigDecimal cargoWeight, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			char labour, char fk3mt, char fk5mt, char fk10mt, char hydra12mt, char crane, String ssrTransId,
			String deliveryOrderNo, String rotationNo, Date rotationDate, Date containerGateInDate,
			String shippingLineName, String shippingAgentName, String onAccountName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.stuffReqId = stuffReqId;
		this.sbTransId = sbTransId;
		this.stuffReqLineId = stuffReqLineId;
		this.sbLineNo = sbLineNo;
		this.profitCentreId = profitcentreId;
		this.stuffReqDate = stuffReqDate;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.shift = shift;
		this.stuffTally = stuffTally;
		this.totalCargoWeight = totalCargoWeight;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.exporterName = exporterName;
		this.cargoDescription = cargoDescription;
		this.onAccountOf = onAccountOf;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.terminal = terminal;
		this.coverDetails = coverDetails;
		this.coverDate = coverDate;
		this.berthingDate = berthingDate;
		this.gateOpenDate = gateOpenDate;
		this.gateInId = gateInId;
		this.agentSealNo = agentSealNo;
		this.tareWeight = tareWeight;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.pod = pod;
		this.comments = comments;
		this.typeOfPackage = typeOfPackage;
		this.noOfPackages = noOfPackages;
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.contStuffPackages = contStuffPackages;
		this.cbm = cbm;
		this.containerNo = containerNo;
		this.currentLocation = currentLocation;
		this.periodFrom = periodFrom;
		this.containerHealth = containerHealth;
		this.cargoWeight = cargoWeight;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.labour = labour;
		fk3MT = fk3mt;
		fk5MT = fk5mt;
		fk10MT = fk10mt;
		hydra12MT = hydra12mt;
		this.crane = crane;
		this.ssrTransId = ssrTransId;
		this.rotationNo = rotationNo;
		this.rotationDate = rotationDate;
		this.containerGateInDate = containerGateInDate;
		this.shippingLineName = shippingLineName;
		this.shippingAgentName = shippingAgentName;
		this.onAccountName = onAccountName;
	}

	public String getRotationNo() {
		return rotationNo;
	}

	public void setRotationNo(String rotationNo) {
		this.rotationNo = rotationNo;
	}

	public Date getRotationDate() {
		return rotationDate;
	}

	public void setRotationDate(Date rotationDate) {
		this.rotationDate = rotationDate;
	}

	public Date getContainerGateInDate() {
		return containerGateInDate;
	}

	public void setContainerGateInDate(Date containerGateInDate) {
		this.containerGateInDate = containerGateInDate;
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

	public String getOnAccountName() {
		return onAccountName;
	}

	public void setOnAccountName(String onAccountName) {
		this.onAccountName = onAccountName;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
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

	public String getStuffReqId() {
		return stuffReqId;
	}

	public void setStuffReqId(String stuffReqId) {
		this.stuffReqId = stuffReqId;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public int getStuffReqLineId() {
		return stuffReqLineId;
	}

	public void setStuffReqLineId(int stuffReqLineId) {
		this.stuffReqLineId = stuffReqLineId;
	}

	public String getSbLineNo() {
		return sbLineNo;
	}

	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}

	public Date getStuffReqDate() {
		return stuffReqDate;
	}

	public void setStuffReqDate(Date stuffReqDate) {
		this.stuffReqDate = stuffReqDate;
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

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public char getStuffTally() {
		return stuffTally;
	}

	public void setStuffTally(char stuffTally) {
		this.stuffTally = stuffTally;
	}

	public BigDecimal getTotalCargoWeight() {
		return totalCargoWeight;
	}

	public void setTotalCargoWeight(BigDecimal totalCargoWeight) {
		this.totalCargoWeight = totalCargoWeight;
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

	public String getExporterName() {
		return exporterName;
	}

	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
	}

	public String getCargoDescription() {
		return cargoDescription;
	}

	public void setCargoDescription(String cargoDescription) {
		this.cargoDescription = cargoDescription;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public String getVesselId() {
		return vesselId;
	}

	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}

	public String getVoyageNo() {
		return voyageNo;
	}

	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
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

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getAgentSealNo() {
		return agentSealNo;
	}

	public void setAgentSealNo(String agentSealNo) {
		this.agentSealNo = agentSealNo;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
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

	public String getPod() {
		return pod;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public BigDecimal getNoOfPackagesStuffed() {
		return noOfPackagesStuffed;
	}

	public void setNoOfPackagesStuffed(BigDecimal noOfPackagesStuffed) {
		this.noOfPackagesStuffed = noOfPackagesStuffed;
	}

	public BigDecimal getContStuffPackages() {
		return contStuffPackages;
	}

	public void setContStuffPackages(BigDecimal contStuffPackages) {
		this.contStuffPackages = contStuffPackages;
	}

	public String getCbm() {
		return cbm;
	}

	public void setCbm(String cbm) {
		this.cbm = cbm;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public String getContainerHealth() {
		return containerHealth;
	}

	public void setContainerHealth(String containerHealth) {
		this.containerHealth = containerHealth;
	}

	public BigDecimal getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(BigDecimal cargoWeight) {
		this.cargoWeight = cargoWeight;
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

	public char getLabour() {
		return labour;
	}

	public void setLabour(char labour) {
		this.labour = labour;
	}

	public char getFk3MT() {
		return fk3MT;
	}

	public void setFk3MT(char fk3mt) {
		fk3MT = fk3mt;
	}

	public char getFk5MT() {
		return fk5MT;
	}

	public void setFk5MT(char fk5mt) {
		fk5MT = fk5mt;
	}

	public char getFk10MT() {
		return fk10MT;
	}

	public void setFk10MT(char fk10mt) {
		fk10MT = fk10mt;
	}

	public char getHydra12MT() {
		return hydra12MT;
	}

	public void setHydra12MT(char hydra12mt) {
		hydra12MT = hydra12mt;
	}

	public char getCrane() {
		return crane;
	}

	public void setCrane(char crane) {
		this.crane = crane;
	}

	public String getSsrTransId() {
		return ssrTransId;
	}

	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}

	@Override
	public String toString() {
		return "ExportStuffRequest [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", stuffReqId=" + stuffReqId + ", sbTransId=" + sbTransId + ", stuffReqLineId=" + stuffReqLineId
				+ ", sbLineNo=" + sbLineNo + ", profitcentreId=" + ", stuffReqDate=" + stuffReqDate + ", sbNo=" + sbNo
				+ ", sbDate=" + sbDate + ", shift=" + shift + ", stuffTally=" + stuffTally + ", shippingLine="
				+ shippingLine + ", exporterName=" + exporterName + ", cargoDescription=" + cargoDescription
				+ ", onAccountOf=" + onAccountOf + ", vesselId=" + vesselId + ", viaNo=" + viaNo + ", voyageNo="
				+ voyageNo + ", terminal=" + terminal + ", coverDetails=" + coverDetails + ", coverDate=" + coverDate
				+ ", berthingDate=" + berthingDate + ", gateOpenDate=" + gateOpenDate + ", gateInId=" + gateInId
				+ ", agentSealNo=" + agentSealNo + ", tareWeight=" + tareWeight + ", containerSize=" + containerSize
				+ ", containerType=" + containerType + ", pod=" + pod + ", comments=" + comments + ", typeOfPackage="
				+ typeOfPackage + ", noOfPackages=" + noOfPackages + ", noOfPackagesStuffed=" + noOfPackagesStuffed
				+ ", contStuffPackages=" + contStuffPackages + ", cbm=" + cbm + ", containerNo=" + containerNo
				+ ", currentLocation=" + currentLocation + ", periodFrom=" + periodFrom + ", containerHealth="
				+ containerHealth + ", cargoWeight=" + cargoWeight + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", labour=" + labour + ", fk3MT="
				+ fk3MT + ", fk5MT=" + fk5MT + ", fk10MT=" + fk10MT + ", hydra12MT=" + hydra12MT + ", crane=" + crane
				+ ", ssrTransId=" + ssrTransId + ", deliveryOrderNo=" + ", containerGateInDate=" + containerGateInDate
				+ ", shippingLineName=" + shippingLineName + ", shippingAgentName=" + shippingAgentName
				+ ", onAccountName=" + onAccountName + "]";
	}

	public StuffRequestHub(BigDecimal noOfPackagesStuffed, BigDecimal cargoWeight, BigDecimal contStuffPackages) {
		super();
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.cargoWeight = cargoWeight;
		this.contStuffPackages = contStuffPackages;
	}

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public String getCartingLineId() {
		return cartingLineId;
	}

	public void setCartingLineId(String cartingLineId) {
		this.cartingLineId = cartingLineId;
	}

	public Date getCartingTransDate() {
		return cartingTransDate;
	}

	public void setCartingTransDate(Date cartingTransDate) {
		this.cartingTransDate = cartingTransDate;
	}

	public String getPodDesc() {
		return podDesc;
	}

	public void setPodDesc(String podDesc) {
		this.podDesc = podDesc;
	}

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public String getCustomSealNo() {
		return customSealNo;
	}

	public void setCustomSealNo(String customSealNo) {
		this.customSealNo = customSealNo;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getWeightTakenIn() {
		return weightTakenIn;
	}

	public void setWeightTakenIn(BigDecimal weightTakenIn) {
		this.weightTakenIn = weightTakenIn;
	}

	public String getChaName() {
		return chaName;
	}

	public void setChaName(String chaName) {
		this.chaName = chaName;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCommodityDesc() {
		return commodityDesc;
	}

	public void setCommodityDesc(String commodityDesc) {
		this.commodityDesc = commodityDesc;
	}

	public BigDecimal getPkgs() {
		return pkgs;
	}

	public void setPkgs(BigDecimal pkgs) {
		this.pkgs = pkgs;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public Date getMovementOrderDate() {
		return movementOrderDate;
	}

	public void setMovementOrderDate(Date movementOrderDate) {
		this.movementOrderDate = movementOrderDate;
	}

	public Date getPlacementDate() {
		return placementDate;
	}

	public void setPlacementDate(Date placementDate) {
		this.placementDate = placementDate;
	}

	public Date getStuffDate() {
		return stuffDate;
	}

	public void setStuffDate(Date stuffDate) {
		this.stuffDate = stuffDate;
	}

	public Date getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(Date beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public BigDecimal getMtyCountWt() {
		return mtyCountWt;
	}

	public void setMtyCountWt(BigDecimal mtyCountWt) {
		this.mtyCountWt = mtyCountWt;
	}

	public String getStuffingLocation() {
		return stuffingLocation;
	}

	public void setStuffingLocation(String stuffingLocation) {
		this.stuffingLocation = stuffingLocation;
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public char getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(char holdStatus) {
		this.holdStatus = holdStatus;
	}

	public char getSmtpFlag() {
		return smtpFlag;
	}

	public void setSmtpFlag(char smtpFlag) {
		this.smtpFlag = smtpFlag;
	}

	public StuffRequestHub(String companyId, String branchId, String finYear, String stuffReqId, String sbTransId,
			int stuffReqLineId, String sbLineNo, String profitcentreId, Date stuffReqDate, String sbNo, Date sbDate,
			String shift, char stuffTally, String stuffTallyId, BigDecimal totalCargoWeight, String shippingAgent,
			String shippingLine, String exporterName, String cargoDescription, String onAccountOf, String vesselId,
			String viaNo, String voyageNo, String terminal, String coverDetails, Date coverDate, Date berthingDate,
			Date gateOpenDate, String gateInId, String agentSealNo, BigDecimal tareWeight, String containerSize,
			String containerType, String pod, String comments, String typeOfPackage, BigDecimal noOfPackages,
			BigDecimal noOfPackagesStuffed, Integer contStuffPackages, String cbm, String containerNo,
			String currentLocation, Date periodFrom, String containerHealth, BigDecimal cargoWeight, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			char labour, char fk3mt, char fk5mt, char fk10mt, char hydra12mt, char crane, String ssrTransId,
			String deliveryOrderNo, String rotationNo, Date rotationDate, Date containerGateInDate,
			String shippingLineName, String shippingAgentName, String onAccountName, String vesselName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.stuffReqId = stuffReqId;
		this.sbTransId = sbTransId;
		this.stuffReqLineId = stuffReqLineId;
		this.sbLineNo = sbLineNo;
		this.profitCentreId = profitcentreId;
		this.stuffReqDate = stuffReqDate;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.shift = shift;
		this.stuffTally = stuffTally;
		this.totalCargoWeight = totalCargoWeight;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.exporterName = exporterName;
		this.cargoDescription = cargoDescription;
		this.onAccountOf = onAccountOf;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.terminal = terminal;
		this.coverDetails = coverDetails;
		this.coverDate = coverDate;
		this.berthingDate = berthingDate;
		this.gateOpenDate = gateOpenDate;
		this.gateInId = gateInId;
		this.agentSealNo = agentSealNo;
		this.tareWeight = tareWeight;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.pod = pod;
		this.comments = comments;
		this.typeOfPackage = typeOfPackage;
		this.noOfPackages = noOfPackages;
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.contStuffPackages = new BigDecimal(contStuffPackages);
		this.cbm = cbm;
		this.containerNo = containerNo;
		this.currentLocation = currentLocation;
		this.periodFrom = periodFrom;
		this.containerHealth = containerHealth;
		this.cargoWeight = cargoWeight;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.labour = labour;
		fk3MT = fk3mt;
		fk5MT = fk5mt;
		fk10MT = fk10mt;
		hydra12MT = hydra12mt;
		this.crane = crane;
		this.ssrTransId = ssrTransId;
		this.rotationNo = rotationNo;
		this.rotationDate = rotationDate;
		this.containerGateInDate = containerGateInDate;
		this.shippingLineName = shippingLineName;
		this.shippingAgentName = shippingAgentName;
		this.onAccountName = onAccountName;
		this.vesselName = vesselName;
	}

	public StuffRequestHub(String stuffReqId, String sbTransId, int stuffReqLineId, String sbLineNo, String sbNo,
			char stuffTally, String stuffTallyId) {
		super();
		this.stuffReqId = stuffReqId;
		this.sbTransId = sbTransId;
		this.stuffReqLineId = stuffReqLineId;
		this.sbLineNo = sbLineNo;
		this.sbNo = sbNo;
		this.stuffTally = stuffTally;
	}

	public StuffRequestHub(String sbTransId, String profitcentreId, String sbNo, Date sbDate, String shippingAgent,
			String shippingLine, String cargoDescription, String onAccountOf, String vesselId, String viaNo,
			String voyageNo, String terminal, Date berthingDate, Date gateOpenDate, String gateInId, String agentSealNo,
			BigDecimal tareWeight, String containerSize, String containerType, String pod,
			BigDecimal noOfPackagesStuffed, String containerNo, BigDecimal cargoWeight, String status,
			String deliveryOrderNo, String rotationNo, Date rotationDate, Date containerGateInDate, String vesselName,
			String consignee, BigDecimal fob, String cha, String exporterName, BigDecimal sbPackages, BigDecimal sbWt,
			BigDecimal stuffedQuantity, String stuffReqId, String cargoType) {
		this.sbTransId = sbTransId;
		this.profitCentreId = profitcentreId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.cargoDescription = cargoDescription;
		this.onAccountOf = onAccountOf;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.terminal = terminal;
		this.berthingDate = berthingDate;
		this.gateOpenDate = gateOpenDate;
		this.gateInId = gateInId;
		this.agentSealNo = agentSealNo;
		this.tareWeight = tareWeight;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.pod = pod;
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.containerNo = containerNo;
		this.cargoWeight = cargoWeight;
		this.status = status;
		this.rotationNo = rotationNo;
		this.rotationDate = rotationDate;
		this.containerGateInDate = containerGateInDate;
		this.vesselName = vesselName;
		this.consignee = consignee;
		this.fob = fob;
		this.cha = cha;
		this.exporterName = exporterName;
		this.sbPackages = sbPackages;
		this.sbWt = sbWt;
		this.stuffedQuantity = stuffedQuantity;
		this.stuffReqId = stuffReqId;
		this.cargoType = cargoType;
	}

	public StuffRequestHub(String stuffReqId, String sbTransId, int stuffReqLineId, String sbLineNo,
			String profitcentreId, Date stuffReqDate, String sbNo, Date sbDate, char stuffTally,
			BigDecimal totalCargoWeight, String shippingAgent, String shippingLine, String exporterName,
			String cargoDescription, String onAccountOf, String vesselId, String viaNo, String voyageNo,
			String terminal, Date berthingDate, Date gateOpenDate, String gateInId, String agentSealNo,
			BigDecimal tareWeight, String containerSize, String containerType, BigDecimal yardPackages,
			BigDecimal cellAreaAllocated, String pod, String typeOfPackage, BigDecimal noOfPackages,
			BigDecimal noOfPackagesStuffed, String containerNo, String currentLocation, Date periodFrom,
			String containerHealth, BigDecimal cargoWeight, String deliveryOrderNo, String rotationNo,
			Date rotationDate, String vesselName, String consignee, BigDecimal fob, BigDecimal contStuffPackages) {
		this.stuffReqId = stuffReqId;
		this.sbTransId = sbTransId;
		this.stuffReqLineId = stuffReqLineId;
		this.sbLineNo = sbLineNo;
		this.profitCentreId = profitcentreId;
		this.stuffReqDate = stuffReqDate;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.stuffTally = stuffTally;
		this.totalCargoWeight = totalCargoWeight;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.exporterName = exporterName;
		this.cargoDescription = cargoDescription;
		this.onAccountOf = onAccountOf;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.terminal = terminal;
		this.berthingDate = berthingDate;
		this.gateOpenDate = gateOpenDate;
		this.gateInId = gateInId;
		this.agentSealNo = agentSealNo;
		this.tareWeight = tareWeight;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.yardPackages = yardPackages;
		this.cellAreaAllocated = cellAreaAllocated;
		this.pod = pod;
		this.typeOfPackage = typeOfPackage;
		this.noOfPackages = noOfPackages;
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.containerNo = containerNo;
		this.currentLocation = currentLocation;
		this.periodFrom = periodFrom;
		this.containerHealth = containerHealth;
		this.cargoWeight = cargoWeight;
		this.rotationNo = rotationNo;
		this.rotationDate = rotationDate;
		this.vesselName = vesselName;
		this.consignee = consignee;
		this.fob = fob;
		this.contStuffPackages = contStuffPackages;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

//		Export Main Search
	public StuffRequestHub(String stuffReqId, int stuffReqLineId, char stuffTally, String stuffTallyId, String gateInId,
			String containerNo) {
		super();
		this.stuffReqId = stuffReqId;
		this.stuffReqLineId = stuffReqLineId;
		this.stuffTally = stuffTally;
		this.gateInId = gateInId;
		this.containerNo = containerNo;
	}

	public StuffRequestHub(String stuffReqId, int stuffReqLineId, char stuffTally, String stuffTallyId, String gateInId,
			String containerNo, String sbNo, String sbTransId, String sbLineNo) {
		super();
		this.stuffReqId = stuffReqId;
		this.stuffReqLineId = stuffReqLineId;
		this.stuffTally = stuffTally;
		this.gateInId = gateInId;
		this.containerNo = containerNo;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbLineNo = sbLineNo;
	}

//		After Search Select
	
	

	public StuffRequestHub(String companyId, String branchId, String finYear, String profitCentreId, String stuffReqId,
			Date stuffReqDate, String shift, String status, String createdBy, String containerNo, String containerSize,
			String containerType, String containerHealth, String gateInId, String vesselId, String viaNo,
			String voyageNo, String vesselName, String terminal, String shippingAgentName, String shippingLineName,
			String onAccountOf, String onAccountOfName, String destination, String pod, String podDesc,
			String customSealNo, String agentSealNo, Date movementOrderDate, Date placementDate, Date stuffDate,
			Date beginDateTime, Date endDateTime, String stuffingLocation, String movementType, String rotationNo,
			Date rotationDate, char holdStatus, BigDecimal mtyCountWt, String sbNo, String sbLineNo, String exporterName,
			String cha, String chaName, String cargoDescription, String comments, String commodityCode,
			String commodityDesc, BigDecimal noOfPackages, BigDecimal noOfPackagesStuffed, BigDecimal cargoWeight,
			BigDecimal totalCargoWeight, char smtpFlag, Integer stuffReqQty, BigDecimal stuffReqWeight, String sbTransId, int stuffReqLineId,
			String shippingAgent, String shippingLine, BigDecimal tareWeight) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitCentreId = profitCentreId;
		this.stuffReqId = stuffReqId;
		this.stuffReqDate = stuffReqDate;
		this.shift = shift;
		this.status = status;
		this.createdBy = createdBy;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerHealth = containerHealth;
		this.gateInId = gateInId;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.vesselName = vesselName;
		this.terminal = terminal;
		this.shippingAgentName = shippingAgentName;
		this.shippingLineName = shippingLineName;
		this.onAccountOf = onAccountOf;
		this.onAccountName = onAccountOfName;
		this.destination = destination;
		this.pod = pod;
		this.podDesc = podDesc;
		this.customSealNo = customSealNo;
		this.agentSealNo = agentSealNo;
		this.movementOrderDate = movementOrderDate;
		this.placementDate = placementDate;
		this.stuffDate = stuffDate;
		this.beginDateTime = beginDateTime;
		this.endDateTime = endDateTime;
		this.stuffingLocation = stuffingLocation;
		this.movementType = movementType;
		this.rotationNo = rotationNo;
		this.rotationDate = rotationDate;
		this.holdStatus = holdStatus;
		this.mtyCountWt = mtyCountWt;
		this.sbNo = sbNo;
		this.sbLineNo = sbLineNo;
		this.exporterName = exporterName;
		this.cha = cha;
		this.chaName = chaName;
		this.cargoDescription = cargoDescription;
		this.comments = comments;
		this.commodityCode = commodityCode;
		this.commodityDesc = commodityDesc;
		this.noOfPackages = noOfPackages;
		this.noOfPackagesStuffed = noOfPackagesStuffed;
		this.cargoWeight = cargoWeight;
		this.totalCargoWeight = totalCargoWeight;
		this.smtpFlag = smtpFlag;
		this.stuffReqQty = stuffReqQty;
		this.stuffReqWeight = stuffReqWeight;
		this.balanceQuantity =  noOfPackages.intValue() - stuffReqQty; 
		this.balanceWeight = cargoWeight.subtract(stuffReqWeight);		
		this.stuffReqLineId = stuffReqLineId;
		this.sbTransId = sbTransId;
		this.tareWeight = tareWeight;
		this.shippingLine = shippingLine;
		this.shippingAgent = shippingAgent;
		
	}

}
