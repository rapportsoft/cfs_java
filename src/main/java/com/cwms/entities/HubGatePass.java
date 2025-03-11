package com.cwms.entities;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cfgatepass_hub")
@IdClass(HubGatePassId.class)
public class HubGatePass {


    @Id
    @Column(name = "Company_Id", length = 6, columnDefinition = "varchar(6) default ''")
    private String companyId = "";

    @Id
    @Column(name = "Branch_Id", length = 6, columnDefinition = "varchar(6) default ''")
    private String branchId = "";

    @Id
    @Column(name = "Fin_Year", length = 4, columnDefinition = "varchar(4) default ''")
    private String finYear = "";

    @Id
    @Column(name = "Gatepass_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String gatepassId = "";

    @Id
    @Column(name = "SB_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String sbTransId = "";

    @Id
    @Column(name = "Stuff_Req_Line_Id", columnDefinition = "int default 0")
    private int stuffReqLineId = 0;

    @Column(name = "SB_Line_No", length = 12, columnDefinition = "varchar(12) default ''")
    private String sbLineNo = "";

    @Column(name = "Profitcentre_Id", length = 6, columnDefinition = "varchar(6) default ''")
    private String profitcentreId = "";

    @Column(name = "Gatepass_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date gatepassDate;

    @Column(name = "SB_No", length = 15, columnDefinition = "varchar(15) default ''")
    private String sbNo = "";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "SB_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date sbDate;

    @Column(name = "Shift", length = 6, columnDefinition = "varchar(6) default ''")
    private String shift = "";

    @Column(name = "stuff_tally", columnDefinition = "char(1) default 'N'")
    private char stuffTally = 'N';

    @Column(name = "Carting_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String cartingTransId = "";

    @Column(name = "Carting_Line_Id", length = 4, columnDefinition = "varchar(4) default ''")
    private String cartingLineId = "";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Carting_Trans_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date cartingTransDate;

    @Column(name = "Total_Cargo_Weight", columnDefinition = "decimal(12,2) default '0.00'")
    private BigDecimal totalCargoWeight = BigDecimal.ZERO;

    @Column(name = "Shipping_Agent", length = 6, columnDefinition = "varchar(6) default ''")
    private String shippingAgent = "";

    @Column(name = "Shipping_Line", length = 6, columnDefinition = "varchar(6) default ''")
    private String shippingLine = "";

    @Column(name = "Exporter_Name", length = 60)
    private String exporterName;

    @Column(name = "Cargo_Description", length = 250, columnDefinition = "varchar(250) default ''")
    private String cargoDescription = "";

    @Column(name = "On_Account_Of", length = 6, columnDefinition = "varchar(6) default ''")
    private String onAccountOf = "";

    @Column(name = "Vessel_Id", length = 7, columnDefinition = "varchar(7) default ''")
    private String vesselId = "";

    @Column(name = "VIA_No", length = 7, columnDefinition = "varchar(7) default ''")
    private String viaNo = "";

    @Column(name = "Voyage_No", length = 10, columnDefinition = "varchar(10) default ''")
    private String voyageNo = "";

    @Column(name = "Terminal", length = 10, columnDefinition = "varchar(10) default ''")
    private String terminal = "";

    @Column(name = "Cover_Details", length = 10, columnDefinition = "varchar(10) default ''")
    private String coverDetails = "";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Cover_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date coverDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Berthing_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date berthingDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Gate_Open_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date gateOpenDate;

    @Column(name = "Gate_In_Id", length = 10, columnDefinition = "varchar(10) default ''")
    private String gateInId = "";

    @Column(name = "Agent_Seal_No", length = 15, columnDefinition = "varchar(15) default ''")
    private String agentSealNo = "";

    @Column(name = "Tare_Weight", columnDefinition = "decimal(15,3) default '0.000'")
    private BigDecimal tareWeight = BigDecimal.ZERO;

    @Column(name = "Container_Size", length = 6, columnDefinition = "varchar(6) default ''")
    private String containerSize = "";

    @Column(name = "Container_Type", length = 6, columnDefinition = "varchar(6) default ''")
    private String containerType = "";

    @Column(name = "Yard_Location", length = 10, columnDefinition = "varchar(10) default ''")
    private String yardLocation = "";

    @Column(name = "Yard_Block", length = 10, columnDefinition = "varchar(10) default ''")
    private String yardBlock = "";

    @Column(name = "Yard_Cell_No", length = 10)
    private String yardCellNo;

    @Column(name = "Yard_Location1", length = 10, columnDefinition = "varchar(10) default ''")
    private String yardLocation1 = "";

    @Column(name = "Yard_Block1", length = 10, columnDefinition = "varchar(10) default ''")
    private String yardBlock1 = "";

    @Column(name = "Block_Cell_No1", length = 10)
    private String blockCellNo1;

    @Column(name = "Yard_Packages", columnDefinition = "decimal(18,3) default '0.000'")
    private BigDecimal yardPackages = BigDecimal.ZERO;

    @Column(name = "Cell_Area_Allocated", columnDefinition = "decimal(18,3) default '0.000'")
    private BigDecimal cellAreaAllocated = BigDecimal.ZERO;

    @Column(name = "Area_Released", columnDefinition = "decimal(8,3) default '0.000'")
    private BigDecimal areaReleased = BigDecimal.ZERO;

    @Column(name = "POD", length = 10, columnDefinition = "varchar(10) default ''")
    private String pod = "";
    
    @Column(name = "POD_DESC", length = 60, columnDefinition = "varchar(60) default ''")
    private String podDesc = "";

    @Column(name = "Comments", length = 150, columnDefinition = "varchar(150) default ''")
    private String comments = "";

    @Column(name = "Type_Of_Package", length = 6, columnDefinition = "varchar(6) default ''")
    private String typeOfPackage = "";

    @Column(name = "No_Of_Packages", columnDefinition = "decimal(8,0) default '0'")
    private int noOfPackages = 0;

    @Column(name = "No_Of_Packages_Stuffed", columnDefinition = "decimal(8,0) default '0'")
    private int noOfPackagesStuffed = 0;

    @Column(name = "Cont_Stuff_Packages", columnDefinition = "decimal(8,0) default '0'")
    private int contStuffPackages = 0;

    @Column(name = "CBM", length = 50, columnDefinition = "varchar(50) default ''")
    private String cbm = "";

    @Column(name = "Container_No", length = 11)
    private String containerNo;

    @Column(name = "Current_Location", length = 26)
    private String currentLocation;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Period_From", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date periodFrom;

    @Column(name = "container_health", length = 6)
    private String containerHealth;

    @Column(name = "Cargo_Weight", columnDefinition = "decimal(12,2) default '0.00'")
    private BigDecimal cargoWeight = BigDecimal.ZERO;

    @Column(name = "Status", columnDefinition = "char(1) default ''")
    private char status = ' ';

    @Column(name = "Created_By", length = 10, columnDefinition = "varchar(10) default ''")
    private String createdBy = "";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Created_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Edited_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Approved_Date", columnDefinition = "datetime default '0000-00-00 00:00:00'")
    private Date approvedDate;

    @Column(name = "Labour", columnDefinition = "char(1) default 'N'")
    private char labour = 'N';

    @Column(name = "Fk_3MT", columnDefinition = "char(1) default 'N'")
    private char fk3MT = 'N';

    @Column(name = "Fk_5MT", columnDefinition = "char(1) default 'N'")
    private char fk5MT = 'N';

    @Column(name = "Fk_10MT", columnDefinition = "char(1) default 'N'")
    private char fk10MT = 'N';

    @Column(name = "Hydra_12MT", columnDefinition = "char(1) default 'N'")
    private char hydra12MT = 'N';

    @Column(name = "crane", columnDefinition = "char(1) default 'N'")
    private char crane = 'N';

    @Column(name = "SSR_Trans_Id", length = 20, columnDefinition = "varchar(20) default ''")
    private String ssrTransId = "";

    @Column(name = "Transporter_Name", length = 60)
    private String transporterName;

    @Column(name = "Vehicle_No", length = 13, columnDefinition = "varchar(13) default ''")
    private String vehicleNo = "";

    @Column(name = "ICD_Destination", length = 60, columnDefinition = "varchar(60) default ''")
    private String icdDestination = "";

    @Column(name = "Custom_Seal_No", length = 15, columnDefinition = "varchar(15) default ''")
    private String customSealNo = "";

    @Column(name = "Weight_Taken_In", columnDefinition = "decimal(15,3) default '0.000'")
    private BigDecimal weightTakenIn = BigDecimal.ZERO;

    @Column(name = "Gross_Weight", columnDefinition = "decimal(15,3) default '0.000'")
    private BigDecimal grossWeight = BigDecimal.ZERO;
    

	transient private String shippingLineName;
	
	transient private Integer stuffReqQty;
	

	public Integer getStuffReqQty() {
		return stuffReqQty;
	}


	public void setStuffReqQty(Integer stuffReqQty) {
		this.stuffReqQty = stuffReqQty;
	}


	public HubGatePass() {
		super();
		// TODO Auto-generated constructor stub
	}


	public HubGatePass(String companyId, String branchId, String finYear, String gatepassId, String sbTransId,
			int stuffReqLineId, String sbLineNo, String profitcentreId, Date gatepassDate, String sbNo, Date sbDate,
			String shift, char stuffTally, String cartingTransId, String cartingLineId, Date cartingTransDate,
			BigDecimal totalCargoWeight, String shippingAgent, String shippingLine, String exporterName,
			String cargoDescription, String onAccountOf, String vesselId, String viaNo, String voyageNo,
			String terminal, String coverDetails, Date coverDate, Date berthingDate, Date gateOpenDate, String gateInId,
			String agentSealNo, BigDecimal tareWeight, String containerSize, String containerType, String yardLocation,
			String yardBlock, String yardCellNo, String yardLocation1, String yardBlock1, String blockCellNo1,
			BigDecimal yardPackages, BigDecimal cellAreaAllocated, BigDecimal areaReleased, String pod, String comments,
			String typeOfPackage, int noOfPackages, int noOfPackagesStuffed, int contStuffPackages, String cbm,
			String containerNo, String currentLocation, Date periodFrom, String containerHealth, BigDecimal cargoWeight,
			char status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, char labour, char fk3mt, char fk5mt, char fk10mt, char hydra12mt, char crane,
			String ssrTransId, String transporterName, String vehicleNo, String icdDestination, String customSealNo,
			BigDecimal weightTakenIn, BigDecimal grossWeight, String shippingLineName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gatepassId = gatepassId;
		this.sbTransId = sbTransId;
		this.stuffReqLineId = stuffReqLineId;
		this.sbLineNo = sbLineNo;
		this.profitcentreId = profitcentreId;
		this.gatepassDate = gatepassDate;
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
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.yardCellNo = yardCellNo;
		this.yardLocation1 = yardLocation1;
		this.yardBlock1 = yardBlock1;
		this.blockCellNo1 = blockCellNo1;
		this.yardPackages = yardPackages;
		this.cellAreaAllocated = cellAreaAllocated;
		this.areaReleased = areaReleased;
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
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.icdDestination = icdDestination;
		this.customSealNo = customSealNo;
		this.weightTakenIn = weightTakenIn;
		this.grossWeight = grossWeight;
		this.shippingLineName = shippingLineName;
	}


	
	
	
	public String getPodDesc() {
		return podDesc;
	}


	public void setPodDesc(String podDesc) {
		this.podDesc = podDesc;
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


	public String getGatepassId() {
		return gatepassId;
	}


	public void setGatepassId(String gatepassId) {
		this.gatepassId = gatepassId;
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


	public String getProfitcentreId() {
		return profitcentreId;
	}


	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}


	public Date getGatepassDate() {
		return gatepassDate;
	}


	public void setGatepassDate(Date gatepassDate) {
		this.gatepassDate = gatepassDate;
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


	public String getYardCellNo() {
		return yardCellNo;
	}


	public void setYardCellNo(String yardCellNo) {
		this.yardCellNo = yardCellNo;
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


	public BigDecimal getAreaReleased() {
		return areaReleased;
	}


	public void setAreaReleased(BigDecimal areaReleased) {
		this.areaReleased = areaReleased;
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


	public int getNoOfPackages() {
		return noOfPackages;
	}


	public void setNoOfPackages(int noOfPackages) {
		this.noOfPackages = noOfPackages;
	}


	public int getNoOfPackagesStuffed() {
		return noOfPackagesStuffed;
	}


	public void setNoOfPackagesStuffed(int noOfPackagesStuffed) {
		this.noOfPackagesStuffed = noOfPackagesStuffed;
	}


	public int getContStuffPackages() {
		return contStuffPackages;
	}


	public void setContStuffPackages(int contStuffPackages) {
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


	public String getTransporterName() {
		return transporterName;
	}


	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}


	public String getVehicleNo() {
		return vehicleNo;
	}


	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}


	public String getIcdDestination() {
		return icdDestination;
	}


	public void setIcdDestination(String icdDestination) {
		this.icdDestination = icdDestination;
	}


	public String getCustomSealNo() {
		return customSealNo;
	}


	public void setCustomSealNo(String customSealNo) {
		this.customSealNo = customSealNo;
	}


	public BigDecimal getWeightTakenIn() {
		return weightTakenIn;
	}


	public void setWeightTakenIn(BigDecimal weightTakenIn) {
		this.weightTakenIn = weightTakenIn;
	}


	public BigDecimal getGrossWeight() {
		return grossWeight;
	}


	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}


	public String getShippingLineName() {
		return shippingLineName;
	}


	public void setShippingLineName(String shippingLineName) {
		this.shippingLineName = shippingLineName;
	}


	@Override
	public String toString() {
		return "HubGatePass [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", gatepassId=" + gatepassId + ", sbTransId=" + sbTransId + ", stuffReqLineId=" + stuffReqLineId
				+ ", sbLineNo=" + sbLineNo + ", profitcentreId=" + profitcentreId + ", gatepassDate=" + gatepassDate
				+ ", sbNo=" + sbNo + ", sbDate=" + sbDate + ", shift=" + shift + ", stuffTally=" + stuffTally
				+ ", cartingTransId=" + cartingTransId + ", cartingLineId=" + cartingLineId + ", cartingTransDate="
				+ cartingTransDate + ", totalCargoWeight=" + totalCargoWeight + ", shippingAgent=" + shippingAgent
				+ ", shippingLine=" + shippingLine + ", exporterName=" + exporterName + ", cargoDescription="
				+ cargoDescription + ", onAccountOf=" + onAccountOf + ", vesselId=" + vesselId + ", viaNo=" + viaNo
				+ ", voyageNo=" + voyageNo + ", terminal=" + terminal + ", coverDetails=" + coverDetails
				+ ", coverDate=" + coverDate + ", berthingDate=" + berthingDate + ", gateOpenDate=" + gateOpenDate
				+ ", gateInId=" + gateInId + ", agentSealNo=" + agentSealNo + ", tareWeight=" + tareWeight
				+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", yardLocation="
				+ yardLocation + ", yardBlock=" + yardBlock + ", yardCellNo=" + yardCellNo + ", yardLocation1="
				+ yardLocation1 + ", yardBlock1=" + yardBlock1 + ", blockCellNo1=" + blockCellNo1 + ", yardPackages="
				+ yardPackages + ", cellAreaAllocated=" + cellAreaAllocated + ", areaReleased=" + areaReleased
				+ ", pod=" + pod + ", comments=" + comments + ", typeOfPackage=" + typeOfPackage + ", noOfPackages="
				+ noOfPackages + ", noOfPackagesStuffed=" + noOfPackagesStuffed + ", contStuffPackages="
				+ contStuffPackages + ", cbm=" + cbm + ", containerNo=" + containerNo + ", currentLocation="
				+ currentLocation + ", periodFrom=" + periodFrom + ", containerHealth=" + containerHealth
				+ ", cargoWeight=" + cargoWeight + ", status=" + status + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", labour=" + labour + ", fk3MT=" + fk3MT + ", fk5MT=" + fk5MT
				+ ", fk10MT=" + fk10MT + ", hydra12MT=" + hydra12MT + ", crane=" + crane + ", ssrTransId=" + ssrTransId
				+ ", transporterName=" + transporterName + ", vehicleNo=" + vehicleNo + ", icdDestination="
				+ icdDestination + ", customSealNo=" + customSealNo + ", weightTakenIn=" + weightTakenIn
				+ ", grossWeight=" + grossWeight + "]";
	}
	
	
	
	
    
    
    
	public HubGatePass(
		    String companyId, String branchId, String profitcentreId, String gatepassId,
		    Date gatepassDate, String shift, char status, String createdBy, String containerNo,
		    String containerSize, String containerType, String containerHealth, String gateInId,
		    String partyName, String vehicleNo, Date periodFrom, String transporterName, 
		    String icdDestination, String comments, String sbNo, String sbLineNo, String sbTransId,
		    Date sbDate, String cargoDescription, String exporterName, int noOfPackages,
		    String podDesc, Integer stuffReqQty, BigDecimal cargoWeight, int noOfPackagesStuffed
		) {
		    this.companyId = companyId;
		    this.branchId = branchId;
		    this.profitcentreId = profitcentreId;
		    this.gatepassId = gatepassId;
		    this.gatepassDate = gatepassDate;
		    this.shift = shift;
		    this.status = status;
		    this.createdBy = createdBy;
		    this.containerNo = containerNo;
		    this.containerSize = containerSize;
		    this.containerType = containerType;
		    this.containerHealth = containerHealth;
		    this.gateInId = gateInId;
		    this.shippingLineName = partyName;
		    this.vehicleNo = vehicleNo;
		    this.periodFrom = periodFrom;
		    this.transporterName = transporterName;
		    this.icdDestination = icdDestination;
		    this.comments = comments;
		    this.sbNo = sbNo;
		    this.sbLineNo = sbLineNo;
		    this.sbTransId = sbTransId;
		    this.sbDate = sbDate;
		    this.cargoDescription = cargoDescription;
		    this.exporterName = exporterName;
		    this.noOfPackages = noOfPackages;
		    this.podDesc = podDesc;
		    this.stuffReqQty = stuffReqQty;
		    this.cargoWeight = cargoWeight;
		    this.noOfPackagesStuffed = noOfPackagesStuffed;
		}

    
    
	
	
	
}
