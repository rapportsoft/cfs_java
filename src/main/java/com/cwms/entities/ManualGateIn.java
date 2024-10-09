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
@Table(name = "cfmanualgatein")
@IdClass(ManualGateInId.class)
public class ManualGateIn {
	@Id
	@Column(name = "Company_Id",  length = 6)
	private String companyId; // Company_Id

	@Id
	@Column(name = "Branch_Id",  length = 6)
	private String branchId; // Branch_Id

	@Id
	@Column(name = "Gate_No",  length = 6)
	private String gateNo; // Gate_No

	@Id
	@Column(name = "Gate_In_Id",  length = 10)
	private String gateInId; // Gate_In_Id

	@Id
	@Column(name = "Sr_No")
	private int srNo; // Sr_No

	@Id
	@Column(name = "ERP_Doc_Ref_No",  length = 10)
	private String erpDocRefNo; // ERP_Doc_Ref_No

	@Id
	@Column(name = "Doc_Ref_No",  length = 10)
	private String docRefNo; // Doc_Ref_No

	@Id
	@Column(name = "Line_No",  length = 10)
	private String lineNo; // Line_No

	@Column(name = "In_Bonding_Id",  length = 10)
	private String inBondingId; // In_Bonding_Id

	@Column(name = "Doc_Ref_Date")
	@Temporal(TemporalType.TIMESTAMP) // Specify that this is a timestamp
	private Date docRefDate; // Doc_Ref_Date

	@Column(name = "BOE_No",  length = 15)
	private String boeNo; // BOE_No

	@Column(name = "Gate_In_Date")
	@Temporal(TemporalType.TIMESTAMP) // Specify that this is a timestamp
	private Date gateInDate; // Gate_In_Date

    @Column(name = "BOE_Date")
    @Temporal(TemporalType.DATE) // For date only
    private Date boeDate; // BOE_Date

    @Column(name = "Invoice_No",  length = 10)
    private String invoiceNo; // Invoice_No

    @Column(name = "Invoice_Date")
    @Temporal(TemporalType.TIMESTAMP) // For datetime
    private Date invoiceDate; // Invoice_Date

    @Column(name = "NOC_No",  length = 10)
    private String nocNo; // NOC_No

    @Column(name = "NOC_Date")
    @Temporal(TemporalType.TIMESTAMP) // For datetime
    private Date nocDate; // NOC_Date

    @Column(name = "Gate_In_Type",  length = 6)
    private String gateInType; // Gate_In_Type

    @Column(name = "Profitcentre_Id",  length = 6)
    private String profitcentreId; // Profitcentre_Id
    
    
    @Column(name = "Process_Id",  length = 10)
    private String processId; // Process_Id

    @Column(name = "Carting_Trans_Id",  length = 10)
    private String cartingTransId; // Carting_Trans_Id

    @Column(name = "Carted_Packages")
    private BigDecimal cartedPackages; // Carted_Packages

    @Column(name = "VIA_No",  length = 7)
    private String viaNo; // VIA_No

    @Column(name = "Container_No",  length = 11)
    private String containerNo; // Container_No

    @Column(name = "Container_Size",  length = 6)
    private String containerSize; // Container_Size

    @Column(name = "Container_Type",  length = 6)
    private String containerType; // Container_Type

    @Column(name = "Container_Status",  length = 3)
    private String containerStatus; // Container_Status

    @Column(name = "Container_Seal_No",  length = 15)
    private String containerSealNo; // Container_Seal_No
    
    
    @Column(name = "Customs_Seal_No",  length = 15)
    private String customsSealNo; // Customs_Seal_No

    @Column(name = "ISO_Code",  length = 4)
    private String isoCode; // ISO_Code

    @Column(name = "Gross_Weight")
    private BigDecimal grossWeight; // Gross_Weight

    @Column(name = "EIR_Gross_Weight")
    private BigDecimal eirGrossWeight; // EIR_Gross_Weight

    @Column(name = "Tare_Weight")
    private BigDecimal tareWeight; // Tare_Weight

    @Column(name = "Cargo_weight")
    private BigDecimal cargoWeight; // Cargo_weight

    @Column(name = "Over_Dimension",  length = 1)
    private String overDimension; // Over_Dimension

    @Column(name = "Hazardous",  length = 1)
    private String hazardous; // Hazardous

    @Column(name = "haz_class",  length = 10)
    private String hazClass; // haz_class
    
    @Column(name = "SA",  length = 6)
    private String sa; // SA

    @Column(name = "SL",  length = 6)
    private String sl; // SL

    @Column(name = "On_Account_Of",  length = 6)
    private String onAccountOf; // On_Account_Of

    @Column(name = "CHA",  length = 6)
    private String cha; // CHA

    @Column(name = "CHA_Code",  length = 10)
    private String chaCode; // CHA_Code

    @Column(name = "Importer_Name",  length = 35)
    private String importerName; // Importer_Name

    @Column(name = "Commodity_Description",  length = 250)
    private String commodityDescription; // Commodity_Description

    @Column(name = "Actual_No_Of_Packages")
    private BigDecimal actualNoOfPackages; // Actual_No_Of_Packages

    @Column(name = "FOB")
    private BigDecimal fob; // FOB

    @Column(name = "Qty_Taken_In")
    private BigDecimal qtyTakenIn; // Qty_Taken_In

    @Column(name = "Delivery_Order_No",  length = 10)
    private String deliveryOrderNo; // Delivery_Order_No
    
    @Column(name = "Delivery_Order_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryOrderDate; // Delivery_Order_Date

    @Column(name = "DO_Validity_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doValidityDate; // DO_Validity_Date

    @Column(name = "Shift",  length = 15)
    private String shift; // Shift

    @Column(name = "Port_Exit_No",  length = 25)
    private String portExitNo; // Port_Exit_No

    @Column(name = "Port_Exit_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date portExitDate; // Port_Exit_Date

    @Column(name = "Terminal",  length = 50)
    private String terminal; // Terminal

    @Column(name = "Refer",  length = 1)
    private String refer; // Refer

    @Column(name = "Container_Health",  length = 6)
    private String containerHealth; // Container_Health

    @Column(name = "Yard_Location",  length = 10)
    private String yardLocation; // Yard_Location

    @Column(name = "Yard_Block",  length = 10)
    private String yardBlock; // Yard_Block

    @Column(name = "Yard_Cell",length = 10)
    private String yardCell; // Yard_Cell
    
    @Column(name = "Yard_Location1", length = 10)
    private String yardLocation1; // Yard_Location1

    @Column(name = "Yard_Block1", length = 10)
    private String yardBlock1; // Yard_Block1

    @Column(name = "Yard_Cell1",length = 10)
    private String yardCell1; // Yard_Cell1

    @Column(name = "Transporter_Status",  length = 1)
    private String transporterStatus; // Transporter_Status

    @Column(name = "Transporter_Name", length = 50)
    private String transporterName; // Transporter_Name

    @Column(name = "Transporter", length = 6)
    private String transporter; // Transporter

    @Column(name = "Vehicle_No",  length = 15)
    private String vehicleNo; // Vehicle_No

    @Column(name = "Driver_Name",  length = 50)
    private String driverName; // Driver_Name

    @Column(name = "Damage_Details",  length = 250)
    private String damageDetails; // Damage_Details

    @Column(name = "Comments",  length = 150)
    private String comments; // Comments

    @Column(name = "Special_Remarks",  length = 100)
    private String specialRemarks; // Special_Remarks
    
    
    @Column(name = "Booking_No",  length = 25)
    private String bookingNo; // Booking_No

    @Column(name = "Scanning_Done_Status",  length = 40)
    private String scanningDoneStatus; // Scanning_Done_Status

    @Column(name = "Scanning_Edited_By",  length = 10)
    private String scanningEditedBy; // Scanning_Edited_By

    @Column(name = "Scanning_Done_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scanningDoneDate; // Scanning_Done_Date

    @Column(name = "Weighment_Flag",  length = 1)
    private String weighmentFlag; // Weighment_Flag

    @Column(name = "Weighment_weight",  precision = 16, scale = 3)
    private BigDecimal weighmentWeight; // Weighment_weight

    @Column(name = "Weighment_Pass_No",  length = 30)
    private String weighmentPassNo; // Weighment_Pass_No

    @Column(name = "Weighment_Done",  length = 1)
    private String weighmentDone; // Weighment_Done

    @Column(name = "Damage_Report_Flag",  length = 1)
    private String damageReportFlag; // Damage_Report_Flag

    @Column(name = "EQ_ID",  length = 15)
    private String eqId; // EQ_ID

    @Column(name = "EQ_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eqDate; // EQ_Date

    @Column(name = "EQ_ID_IN",  length = 30)
    private String eqIdIn; // EQ_ID_IN

    @Column(name = "EQ_DATE_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eqDateIn; // EQ_DATE_IN

    @Column(name = "EQ_ID_OUT", length = 30)
    private String eqIdOut; // EQ_ID_OUT
    

    @Column(name = "EQ_DATE_OUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eqDateOut; // EQ_DATE_OUT

    @Column(name = "Status",  length = 1)
    private String status; // Status

    @Column(name = "Created_By",  length = 10)
    private String createdBy; // Created_By

    @Column(name = "Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; // Created_Date

    @Column(name = "Edited_By", length = 10)
    private String editedBy; // Edited_By

    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate; // Edited_Date

    @Column(name = "Approved_By", length = 10)
    private String approvedBy; // Approved_By

    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate; // Approved_Date

    @Column(name = "Trip_Type",  length = 10)
    private String tripType; // Trip_Type

    @Column(name = "DRT",  length = 1)
    private String drt; // DRT

    @Column(name = "Scanner_Type",  length = 60)
    private String scannerType; // Scanner_Type
    
    @Column(name = "upload_user", length = 30)
    private String uploadUser; // upload_user

    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate; // upload_date

    @Column(name = "remove_user", length = 30)
    private String removeUser; // remove_user

    @Column(name = "remove_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date removeDate; // remove_date

    @Column(name = "image_path", length = 200)
    private String imagePath; // image_path

    @Column(name = "back_image", length = 200)
    private String backImage; // back_image

    @Column(name = "vehicle_type",  length = 10)
    private String vehicleType; // vehicle_type

    @Column(name = "ACTUAL_SEAL_NO", length = 15)
    private String actualSealNo; // ACTUAL_SEAL_NO

    @Column(name = "PN_Status", length = 15)
    private String pnStatus; // PN_Status

    @Column(name = "Scanning_Status", length = 50)
    private String scanningStatus; // Scanning_Status

    @Column(name = "ODC_Status", length = 15)
    private String odcStatus; // ODC_Status

    @Column(name = "Eqactivity_flg",  length = 1)
    private String eqActivityFlag; // Eqactivity_flg

    @Column(name = "WEIGHMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date weighmentDate; // WEIGHMENT_DATE
    
    @Column(name="No_Check-Digit",length = 1)
    private String noCheckDigit;
    
    @Column(name="Vessel",length = 50)
    private String vessel;
    
    
    @Column(name="Voyage_No",length = 50)
    private String voyageNo;
    
    @Column(name="Low-Bed",length = 1)
    private String lowBed;
    
    @Column(name="Hold_Type",length = 10)   
    private String holdType;
    
    @Column(name = "Hold_Remarks",  length = 250)
    private String holdRemarks; // Special_Remarks
    
    

	public ManualGateIn() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	


	public String getHoldRemarks() {
		return holdRemarks;
	}







	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}







	public String getHoldType() {
		return holdType;
	}







	public void setHoldType(String holdType) {
		this.holdType = holdType;
	}







	public String getLowBed() {
		return lowBed;
	}







	public void setLowBed(String lowBed) {
		this.lowBed = lowBed;
	}







	public String getVoyageNo() {
		return voyageNo;
	}







	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}




	public ManualGateIn(String companyId, String branchId, String gateNo, String gateInId, int srNo, String erpDocRefNo,
			String docRefNo, String lineNo, String inBondingId, Date docRefDate, String boeNo, Date gateInDate,
			Date boeDate, String invoiceNo, Date invoiceDate, String nocNo, Date nocDate, String gateInType,
			String profitcentreId, String processId, String cartingTransId, BigDecimal cartedPackages, String viaNo,
			String containerNo, String containerSize, String containerType, String containerStatus,
			String containerSealNo, String customsSealNo, String isoCode, BigDecimal grossWeight,
			BigDecimal eirGrossWeight, BigDecimal tareWeight, BigDecimal cargoWeight, String overDimension,
			String hazardous, String hazClass, String sa, String sl, String onAccountOf, String cha, String chaCode,
			String importerName, String commodityDescription, BigDecimal actualNoOfPackages, BigDecimal fob,
			BigDecimal qtyTakenIn, String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate, String shift,
			String portExitNo, Date portExitDate, String terminal, String refer, String containerHealth,
			String yardLocation, String yardBlock, String yardCell, String yardLocation1, String yardBlock1,
			String yardCell1, String transporterStatus, String transporterName, String transporter, String vehicleNo,
			String driverName, String damageDetails, String comments, String specialRemarks, String bookingNo,
			String scanningDoneStatus, String scanningEditedBy, Date scanningDoneDate, String weighmentFlag,
			BigDecimal weighmentWeight, String weighmentPassNo, String weighmentDone, String damageReportFlag,
			String eqId, Date eqDate, String eqIdIn, Date eqDateIn, String eqIdOut, Date eqDateOut, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String tripType, String drt, String scannerType, String uploadUser, Date uploadDate, String removeUser,
			Date removeDate, String imagePath, String backImage, String vehicleType, String actualSealNo,
			String pnStatus, String scanningStatus, String odcStatus, String eqActivityFlag, Date weighmentDate,
			String noCheckDigit, String vessel, String voyageNo, String lowBed, String holdType, String holdRemarks) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateNo = gateNo;
		this.gateInId = gateInId;
		this.srNo = srNo;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.lineNo = lineNo;
		this.inBondingId = inBondingId;
		this.docRefDate = docRefDate;
		this.boeNo = boeNo;
		this.gateInDate = gateInDate;
		this.boeDate = boeDate;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.nocNo = nocNo;
		this.nocDate = nocDate;
		this.gateInType = gateInType;
		this.profitcentreId = profitcentreId;
		this.processId = processId;
		this.cartingTransId = cartingTransId;
		this.cartedPackages = cartedPackages;
		this.viaNo = viaNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.customsSealNo = customsSealNo;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.eirGrossWeight = eirGrossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.overDimension = overDimension;
		this.hazardous = hazardous;
		this.hazClass = hazClass;
		this.sa = sa;
		this.sl = sl;
		this.onAccountOf = onAccountOf;
		this.cha = cha;
		this.chaCode = chaCode;
		this.importerName = importerName;
		this.commodityDescription = commodityDescription;
		this.actualNoOfPackages = actualNoOfPackages;
		this.fob = fob;
		this.qtyTakenIn = qtyTakenIn;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
		this.shift = shift;
		this.portExitNo = portExitNo;
		this.portExitDate = portExitDate;
		this.terminal = terminal;
		this.refer = refer;
		this.containerHealth = containerHealth;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.yardCell = yardCell;
		this.yardLocation1 = yardLocation1;
		this.yardBlock1 = yardBlock1;
		this.yardCell1 = yardCell1;
		this.transporterStatus = transporterStatus;
		this.transporterName = transporterName;
		this.transporter = transporter;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.damageDetails = damageDetails;
		this.comments = comments;
		this.specialRemarks = specialRemarks;
		this.bookingNo = bookingNo;
		this.scanningDoneStatus = scanningDoneStatus;
		this.scanningEditedBy = scanningEditedBy;
		this.scanningDoneDate = scanningDoneDate;
		this.weighmentFlag = weighmentFlag;
		this.weighmentWeight = weighmentWeight;
		this.weighmentPassNo = weighmentPassNo;
		this.weighmentDone = weighmentDone;
		this.damageReportFlag = damageReportFlag;
		this.eqId = eqId;
		this.eqDate = eqDate;
		this.eqIdIn = eqIdIn;
		this.eqDateIn = eqDateIn;
		this.eqIdOut = eqIdOut;
		this.eqDateOut = eqDateOut;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.tripType = tripType;
		this.drt = drt;
		this.scannerType = scannerType;
		this.uploadUser = uploadUser;
		this.uploadDate = uploadDate;
		this.removeUser = removeUser;
		this.removeDate = removeDate;
		this.imagePath = imagePath;
		this.backImage = backImage;
		this.vehicleType = vehicleType;
		this.actualSealNo = actualSealNo;
		this.pnStatus = pnStatus;
		this.scanningStatus = scanningStatus;
		this.odcStatus = odcStatus;
		this.eqActivityFlag = eqActivityFlag;
		this.weighmentDate = weighmentDate;
		this.noCheckDigit = noCheckDigit;
		this.vessel = vessel;
		this.voyageNo = voyageNo;
		this.lowBed = lowBed;
		this.holdType = holdType;
		this.holdRemarks = holdRemarks;
	}







	public String getVessel() {
		return vessel;
	}







	public void setVessel(String vessel) {
		this.vessel = vessel;
	}







	public String getNoCheckDigit() {
		return noCheckDigit;
	}



	public void setNoCheckDigit(String noCheckDigit) {
		this.noCheckDigit = noCheckDigit;
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

	public String getGateNo() {
		return gateNo;
	}

	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getErpDocRefNo() {
		return erpDocRefNo;
	}

	public void setErpDocRefNo(String erpDocRefNo) {
		this.erpDocRefNo = erpDocRefNo;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
	}

	public Date getDocRefDate() {
		return docRefDate;
	}

	public void setDocRefDate(Date docRefDate) {
		this.docRefDate = docRefDate;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public Date getBoeDate() {
		return boeDate;
	}

	public void setBoeDate(Date boeDate) {
		this.boeDate = boeDate;
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

	public String getGateInType() {
		return gateInType;
	}

	public void setGateInType(String gateInType) {
		this.gateInType = gateInType;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public BigDecimal getCartedPackages() {
		return cartedPackages;
	}

	public void setCartedPackages(BigDecimal cartedPackages) {
		this.cartedPackages = cartedPackages;
	}

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
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

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getEirGrossWeight() {
		return eirGrossWeight;
	}

	public void setEirGrossWeight(BigDecimal eirGrossWeight) {
		this.eirGrossWeight = eirGrossWeight;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
	}

	public BigDecimal getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(BigDecimal cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public String getOverDimension() {
		return overDimension;
	}

	public void setOverDimension(String overDimension) {
		this.overDimension = overDimension;
	}

	public String getHazardous() {
		return hazardous;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public String getHazClass() {
		return hazClass;
	}

	public void setHazClass(String hazClass) {
		this.hazClass = hazClass;
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

	public String getChaCode() {
		return chaCode;
	}

	public void setChaCode(String chaCode) {
		this.chaCode = chaCode;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
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

	public BigDecimal getFob() {
		return fob;
	}

	public void setFob(BigDecimal fob) {
		this.fob = fob;
	}

	public BigDecimal getQtyTakenIn() {
		return qtyTakenIn;
	}

	public void setQtyTakenIn(BigDecimal qtyTakenIn) {
		this.qtyTakenIn = qtyTakenIn;
	}

	public String getDeliveryOrderNo() {
		return deliveryOrderNo;
	}

	public void setDeliveryOrderNo(String deliveryOrderNo) {
		this.deliveryOrderNo = deliveryOrderNo;
	}

	public Date getDeliveryOrderDate() {
		return deliveryOrderDate;
	}

	public void setDeliveryOrderDate(Date deliveryOrderDate) {
		this.deliveryOrderDate = deliveryOrderDate;
	}

	public Date getDoValidityDate() {
		return doValidityDate;
	}

	public void setDoValidityDate(Date doValidityDate) {
		this.doValidityDate = doValidityDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getPortExitNo() {
		return portExitNo;
	}

	public void setPortExitNo(String portExitNo) {
		this.portExitNo = portExitNo;
	}

	public Date getPortExitDate() {
		return portExitDate;
	}

	public void setPortExitDate(Date portExitDate) {
		this.portExitDate = portExitDate;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public String getContainerHealth() {
		return containerHealth;
	}

	public void setContainerHealth(String containerHealth) {
		this.containerHealth = containerHealth;
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

	public String getYardCell() {
		return yardCell;
	}

	public void setYardCell(String yardCell) {
		this.yardCell = yardCell;
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

	public String getYardCell1() {
		return yardCell1;
	}

	public void setYardCell1(String yardCell1) {
		this.yardCell1 = yardCell1;
	}

	public String getTransporterStatus() {
		return transporterStatus;
	}

	public void setTransporterStatus(String transporterStatus) {
		this.transporterStatus = transporterStatus;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getTransporter() {
		return transporter;
	}

	public void setTransporter(String transporter) {
		this.transporter = transporter;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDamageDetails() {
		return damageDetails;
	}

	public void setDamageDetails(String damageDetails) {
		this.damageDetails = damageDetails;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSpecialRemarks() {
		return specialRemarks;
	}

	public void setSpecialRemarks(String specialRemarks) {
		this.specialRemarks = specialRemarks;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
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

	public String getWeighmentFlag() {
		return weighmentFlag;
	}

	public void setWeighmentFlag(String weighmentFlag) {
		this.weighmentFlag = weighmentFlag;
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

	public String getWeighmentDone() {
		return weighmentDone;
	}

	public void setWeighmentDone(String weighmentDone) {
		this.weighmentDone = weighmentDone;
	}

	public String getDamageReportFlag() {
		return damageReportFlag;
	}

	public void setDamageReportFlag(String damageReportFlag) {
		this.damageReportFlag = damageReportFlag;
	}

	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public Date getEqDate() {
		return eqDate;
	}

	public void setEqDate(Date eqDate) {
		this.eqDate = eqDate;
	}

	public String getEqIdIn() {
		return eqIdIn;
	}

	public void setEqIdIn(String eqIdIn) {
		this.eqIdIn = eqIdIn;
	}

	public Date getEqDateIn() {
		return eqDateIn;
	}

	public void setEqDateIn(Date eqDateIn) {
		this.eqDateIn = eqDateIn;
	}

	public String getEqIdOut() {
		return eqIdOut;
	}

	public void setEqIdOut(String eqIdOut) {
		this.eqIdOut = eqIdOut;
	}

	public Date getEqDateOut() {
		return eqDateOut;
	}

	public void setEqDateOut(Date eqDateOut) {
		this.eqDateOut = eqDateOut;
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

	public String getTripType() {
		return tripType;
	}

	public void setTripType(String tripType) {
		this.tripType = tripType;
	}

	public String getDrt() {
		return drt;
	}

	public void setDrt(String drt) {
		this.drt = drt;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
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

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getActualSealNo() {
		return actualSealNo;
	}

	public void setActualSealNo(String actualSealNo) {
		this.actualSealNo = actualSealNo;
	}

	public String getPnStatus() {
		return pnStatus;
	}

	public void setPnStatus(String pnStatus) {
		this.pnStatus = pnStatus;
	}

	public String getScanningStatus() {
		return scanningStatus;
	}

	public void setScanningStatus(String scanningStatus) {
		this.scanningStatus = scanningStatus;
	}

	public String getOdcStatus() {
		return odcStatus;
	}

	public void setOdcStatus(String odcStatus) {
		this.odcStatus = odcStatus;
	}

	public String getEqActivityFlag() {
		return eqActivityFlag;
	}

	public void setEqActivityFlag(String eqActivityFlag) {
		this.eqActivityFlag = eqActivityFlag;
	}

	public Date getWeighmentDate() {
		return weighmentDate;
	}

	public void setWeighmentDate(Date weighmentDate) {
		this.weighmentDate = weighmentDate;
	}
    
    
    
    
}
