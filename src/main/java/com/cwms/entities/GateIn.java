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
@Table(name = "cfgatein")
@IdClass(GateInId.class)
public class GateIn {

	@Id
	@Column(name = "Company_Id", length = 6)
	public String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	public String branchId;

	@Id
	@Column(name = "GateIn_Id", length = 10)
	public String gateInId;

	@Id
	@Column(name = "fin_year", length = 6)
	public String finYear;

	@Id
	@Column(name = "Erp_Doc_ref_no", length = 10)
	public String erpDocRefNo;

	@Id
	@Column(name = "Doc_Ref_No", length = 25)
	public String docRefNo;

	@Id
	@Column(name = "Line_No", length = 10)
	public String lineNo;

	@Id
	@Column(name = "Sr_No")
	public int srNo;

	@Column(name = "In_Bonding_Id", length = 10, columnDefinition = "varchar(10) default ''")
	private String inBondingId = "";

	@Column(name = "Doc_Ref_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date docRefDate = new Date(0);

	@Column(name = "BOE_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String boeNo = "";

	@Column(name = "BOE_Date", columnDefinition = "date default null")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date boeDate;

	@Column(name = "Invoice_No", length = 100, columnDefinition = "varchar(100) default ''")
	private String invoiceNo = "";

	@Column(name = "Invoice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceDate = new Date(0);

	@Column(name = "NOC_No", length = 25, columnDefinition = "varchar(25) default ''")
	private String nocNo = "";

	@Column(name = "NOC_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocDate = new Date(0);

	@Column(name = "Gate_In_Type", length = 6, columnDefinition = "varchar(6) default ''")
	private String gateInType = "";

	@Column(name = "Profitcentre_Id", length = 6, columnDefinition = "varchar(6) default ''")
	private String profitcentreId = "";

	@Column(name = "Process_Id", length = 10, columnDefinition = "varchar(10) default ''")
	private String processId = "";

	@Column(name = "Carting_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
	private String cartingTransId = "";

	@Column(name = "Carted_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal cartedPackages = BigDecimal.ZERO;

	@Column(name = "VIA_NO", length = 10, columnDefinition = "varchar(10) default ''")
	private String viaNo = "";

	@Column(name = "Container_No", length = 11, columnDefinition = "varchar(11) default ''")
	private String containerNo = "";

	@Column(name = "Container_Size", length = 6, columnDefinition = "varchar(6) default ''")
	private String containerSize = "";

	@Column(name = "Container_Type", length = 6, columnDefinition = "varchar(6) default ''")
	private String containerType = "";

	@Column(name = "Container_Status", length = 3, columnDefinition = "char(3) default ''")
	private String containerStatus = "";

	@Column(name = "Container_Seal_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String containerSealNo = "";

	@Column(name = "Customs_Seal_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String customsSealNo = "";

	@Column(name = "Actual_seal_no", length = 15, columnDefinition = "varchar(15) default ''")
	private String actualSealNo = "";

	@Column(name = "Seal_mismatch", length = 1, columnDefinition = "char(1) default 'N'")
	private String sealMismatch = "N";

	@Column(name = "vehicle_type", length = 10, columnDefinition = "varchar(10) default ''")
	private String vehicleType = "";

	@Column(name = "ISO_Code", length = 4, columnDefinition = "varchar(4) default ''")
	private String isoCode = "";

	@Column(name = "Gross_Weight", precision = 16, scale = 4, columnDefinition = "decimal(16,4) default '0.0000'")
	private BigDecimal grossWeight = BigDecimal.ZERO;

	@Column(name = "EIR_Gross_Weight", precision = 15, scale = 3, columnDefinition = "decimal(15,3) default '0.000'")
	private BigDecimal eirGrossWeight = BigDecimal.ZERO;

	@Column(name = "Tare_Weight", precision = 15, scale = 3, columnDefinition = "decimal(15,3) default null")
	private BigDecimal tareWeight;

	@Column(name = "Cargo_weight", precision = 15, scale = 3, columnDefinition = "decimal(15,3) default null")
	private BigDecimal cargoWeight;

	@Column(name = "Weighment_weight", precision = 15, scale = 3, columnDefinition = "decimal(15,3) default '0.000'")
	private BigDecimal weighmentWeight = BigDecimal.ZERO;

	@Column(name = "Weighment_Pass_No", length = 30, columnDefinition = "varchar(30) default ''")
	private String weighmentPassNo = "";

	@Column(name = "Weighment_Wt_User", length = 20, columnDefinition = "varchar(20) default ''")
	private String weighmentWtUser = "";

	@Column(name = "Weighment_Wt_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date weighmentWtDate = new Date(0);

	@Column(name = "Weighment_Done", length = 1, columnDefinition = "char(1) default 'N'")
	private String weighmentDone = "N";

	@Column(name = "Over_Dimension", length = 1, columnDefinition = "char(1) default ''")
	private String overDimension = "";

	@Column(name = "Hazardous", length = 1, columnDefinition = "char(1) default ''")
	private String hazardous = "";

	@Column(name = "haz_class", length = 10, columnDefinition = "varchar(10) default ''")
	private String hazClass = "";

	@Column(name = "SA", length = 6, columnDefinition = "varchar(6) default ''")
	private String sa = "";

	@Column(name = "SL", length = 6, columnDefinition = "varchar(6) default ''")
	private String sl = "";

	@Column(name = "On_Account_Of", length = 6, columnDefinition = "varchar(6) default ''")
	private String onAccountOf = "";

	@Column(name = "CHA", length = 6, columnDefinition = "varchar(6) default ''")
	private String cha = "";

	@Column(name = "CHA_Code", length = 10, columnDefinition = "varchar(10) default ''")
	private String chaCode = "";

	@Column(name = "Importer_Name", length = 100, columnDefinition = "varchar(100) default ''")
	private String importerName = "";

	@Column(name = "Commodity_Description", length = 250, columnDefinition = "varchar(250) default ''")
	private String commodityDescription = "";

	@Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal actualNoOfPackages = BigDecimal.ZERO;

	@Column(name = "FOB", precision = 16, scale = 4, columnDefinition = "decimal(16,4) default null")
	private BigDecimal fob;

	@Column(name = "Qty_Taken_In", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal qtyTakenIn = BigDecimal.ZERO;

	@Column(name = "Transfer_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal transferPackages = BigDecimal.ZERO;

	@Column(name = "Nil_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal nilPackages = BigDecimal.ZERO;

	@Column(name = "Delivery_Order_No", length = 10, columnDefinition = "varchar(10) default ''")
	private String deliveryOrderNo = "";

	@Column(name = "Delivery_Order_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date deliveryOrderDate = new Date(0);

	@Column(name = "DO_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date doValidityDate = new Date(0);

	@Column(name = "Shift", length = 15, columnDefinition = "varchar(15) default ''")
	private String shift = "";

	@Column(name = "Port_Exit_No", length = 25, columnDefinition = "varchar(25) default ''")
	private String portExitNo = "";

	@Column(name = "Port_Exit_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date portExitDate = new Date(0);

	@Column(name = "Terminal", length = 50, columnDefinition = "varchar(50) default ''")
	private String terminal = "";

	@Column(name = "Origin", length = 50, columnDefinition = "varchar(50) default ''")
	private String origin = "";

	@Column(name = "Refer", length = 1, columnDefinition = "char(1) default ''")
	private String refer = "";

	@Column(name = "temperature", length = 5, columnDefinition = "varchar(5) default ''")
	private String temperature = "";

	@Column(name = "Container_Health", length = 6, columnDefinition = "varchar(6) default ''")
	private String containerHealth = "";

	@Column(name = "Yard_Location", length = 20, columnDefinition = "varchar(20) default ''")
	private String yardLocation = "";

	@Column(name = "Yard_Block", length = 10, columnDefinition = "varchar(10) default ''")
	private String yardBlock = "";

	@Column(name = "Yard_Cell", length = 10, columnDefinition = "varchar(10) default null")
	private String yardCell;

	@Column(name = "Yard_Location1", length = 20, columnDefinition = "varchar(20) default ''")
	private String yardLocation1 = "";

	@Column(name = "Yard_Block1", length = 10, columnDefinition = "varchar(10) default ''")
	private String yardBlock1 = "";

	@Column(name = "Yard_Cell1", length = 10, columnDefinition = "varchar(10) default null")
	private String yardCell1;

	@Column(name = "Transporter_Status", length = 1, columnDefinition = "char(1) default ''")
	private String transporterStatus = "";

	@Column(name = "Transporter_Name", length = 50, columnDefinition = "varchar(50) default ''")
	private String transporterName = "";

	@Column(name = "Transporter", length = 60, columnDefinition = "varchar(60) default ''")
	private String transporter = "";

	@Column(name = "Vehicle_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String vehicleNo = "";

	@Column(name = "Driver_Name", length = 50, columnDefinition = "varchar(50) default ''")
	private String driverName = "";

	@Column(name = "Damage_Details", length = 250, columnDefinition = "varchar(250) default ''")
	private String damageDetails = "";

	@Column(name = "Comments", length = 150, columnDefinition = "varchar(150) default ''")
	private String comments = "";

	@Column(name = "Special_Remarks", length = 100, columnDefinition = "varchar(100) default ''")
	private String specialRemarks = "";

	@Column(name = "Booking_No", length = 50, columnDefinition = "varchar(50) default ''")
	private String bookingNo = "";

	@Column(name = "Scanning_Done_Status", length = 40, columnDefinition = "varchar(40) default ''")
	private String scanningDoneStatus = "";

	@Column(name = "Scanning_Edited_By", length = 10, columnDefinition = "varchar(10) default ''")
	private String scanningEditedBy = "";

	@Column(name = "Scanning_Done_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date scanningDoneDate = new Date(0);

	@Column(name = "Weighment_Flag", length = 1, columnDefinition = "char(1) default 'N'")
	private String weighmentFlag = "N";

	@Column(name = "Damage_Report_Flag", length = 1, columnDefinition = "char(1) default 'N'")
	private String damageReportFlag = "N";

	@Column(name = "EQ_ID", length = 15, columnDefinition = "varchar(15) default ''")
	private String eqId = "";

	@Column(name = "EQ_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date eqDate = new Date(0);

	@Column(name = "EQ_ID_IN", length = 30, columnDefinition = "varchar(30) default ''")
	private String eqIdIn = "";

	@Column(name = "EQ_DATE_IN")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date eqDateIn = new Date(0);

	@Column(name = "EQ_ID_OUT", length = 30, columnDefinition = "varchar(30) default ''")
	private String eqIdOut = "";

	@Column(name = "EQ_DATE_OUT")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date eqDateOut = new Date(0);

	@Column(name = "Status", length = 1, columnDefinition = "char(1) default ''")
	private String status = "";

	@Column(name = "Created_By", length = 10, columnDefinition = "varchar(10) default ''")
	private String createdBy = "";

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate = new Date(0);

	@Column(name = "Edited_By", length = 10, columnDefinition = "varchar(10) default ''")
	private String editedBy = "";

	@Column(name = "Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate = new Date(0);

	@Column(name = "Approved_By", length = 10, columnDefinition = "varchar(10) default ''")
	private String approvedBy = "";

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate = new Date(0);

	@Column(name = "Trip_Type", length = 10, columnDefinition = "varchar(10) default ''")
	private String tripType = "";

	@Column(name = "DRT", length = 1, columnDefinition = "char(1) default 'N'")
	private String drt = "N";

	@Column(name = "Scanner_Type", length = 60, columnDefinition = "varchar(60) default ''")
	private String scannerType = "";

	@Column(name = "carting_status", length = 1, columnDefinition = "char(1) default 'N'")
	private String cartingStatus = "N";

	@Column(name = "Labour", length = 1, columnDefinition = "char(1) default 'N'")
	private String labour = "N";

	@Column(name = "Fk_3MT", length = 1, columnDefinition = "char(1) default 'N'")
	private String fk3mt = "N";

	@Column(name = "Fk_5MT", length = 1, columnDefinition = "char(1) default 'N'")
	private String fk5mt = "N";

	@Column(name = "Fk_10MT", length = 1, columnDefinition = "char(1) default 'N'")
	private String fk10mt = "N";

	@Column(name = "Hydra_12MT", length = 1, columnDefinition = "char(1) default 'N'")
	private String hydra12mt = "N";

	@Column(name = "Crane", length = 1, columnDefinition = "char(1) default 'N'")
	private String crane = "N";

	@Column(name = "From_SB_Trans_Id", length = 10, columnDefinition = "varchar(10) default ''")
	private String fromSbTransId = "";

	@Column(name = "From_SB_Line_No", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal fromSbLineNo = BigDecimal.ZERO;

	@Column(name = "From_SB_No", precision = 8, scale = 0, columnDefinition = "decimal(8,0) default '0'")
	private BigDecimal fromSbNo = BigDecimal.ZERO;

	@Column(name = "upload_user", length = 30, columnDefinition = "varchar(30) default ''")
	private String uploadUser = "";

	@Column(name = "upload_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date uploadDate = new Date(0);

	@Column(name = "remove_user", length = 30, columnDefinition = "varchar(30) default ''")
	private String removeUser = "";

	@Column(name = "remove_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date removeDate = new Date(0);

	@Column(name = "image_path", length = 200, columnDefinition = "varchar(200) default ''")
	private String imagePath = "";

	@Column(name = "back_image", length = 200, columnDefinition = "varchar(200) default ''")
	private String backImage = "";

	@Column(name = "BL_No", length = 20, columnDefinition = "varchar(20) default ''")
	private String blNo = "";

	@Column(name = "BL_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date blDate = new Date(0);

	@Column(name = "Hold_Status", length = 1, columnDefinition = "char(1) default 'N'")
	private String holdStatus = "N";

	@Column(name = "Hold_Type", length = 12, columnDefinition = "char(12) default 'N'")
	private String holdType = "N";

	@Column(name = "Hold_User", length = 10, columnDefinition = "varchar(10) default ''")
	private String holdUser = "";

	@Column(name = "Hold_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date holdDate = new Date(0);

	@Column(name = "Hold_Remarks", length = 250, columnDefinition = "varchar(250) default ''")
	private String holdRemarks = "";

	@Column(name = "Stuff_Tally_Id", length = 20, columnDefinition = "varchar(20) default ''")
	private String stuffTallyId = "";

	@Column(name = "PR_Gate_Pass_No", length = 10, columnDefinition = "varchar(10) default ''")
	private String prGatePassNo = "";

	@Column(name = "PR_Gate_Out_Id", length = 50, columnDefinition = "varchar(50) default ''")
	private String prGateOutId = "";

	@Column(name = "PR_Gate_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date prGateOutDate = new Date(0);

	@Column(name = "Stuff_Tally_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date stuffTallyDate = new Date(0);

	@Column(name = "Stuff_Tally_Status", length = 1, columnDefinition = "char(1) default 'N'")
	private String stuffTallyStatus = "N";

	@Column(name = "Back_to_town", length = 20, columnDefinition = "varchar(20) default ''")
	private String backToTown = "";

	@Column(name = "Back_to_town_Remark", length = 250, columnDefinition = "varchar(250) default ''")
	private String backToTownRemark = "";

	@Column(name = "Back_to_town_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date backToTownDate = new Date(0);

	@Column(name = "UN_No", length = 7, columnDefinition = "varchar(7) default ''")
	private String unNo = "";

	@Column(name = "Commodity", length = 250, columnDefinition = "varchar(250) default ''")
	private String commodity = "";

	@Column(name = "Assesment_Id", length = 20, columnDefinition = "varchar(20) default ''")
	private String assesmentId = "";

	@Column(name = "PN_Status", length = 1, columnDefinition = "char(1) default ''")
	private String pnStatus = "";

	@Column(name = "JOB_ORDER_ID", length = 16, columnDefinition = "varchar(16) default ''")
	private String jobOrderId = "";

	@Column(name = "JOB_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date jobDate = new Date(0);

	@Column(name = "Area", length = 20, columnDefinition = "varchar(20) default ''")
	private String area = "";

	@Column(name = "Cargo_Type", length = 20, columnDefinition = "varchar(20) default ''")
	private String cargoType = "";

	@Column(name = "Weight_Taken_In", precision = 12, scale = 2, columnDefinition = "decimal(12,2) default '0.00'")
	private BigDecimal weightTakenIn = BigDecimal.ZERO;

	@Column(name = "RScan_Out", length = 1, columnDefinition = "char(1) default 'N'")
	private String rScanOut = "N";

	@Column(name = "Out_Vehicle_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String outVehicleNo = "";

	@Column(name = "Out_Transporter_Status", length = 1, columnDefinition = "char(1) default ''")
	private String outTransporterStatus = "";

	@Column(name = "Out_Transporter", length = 6, columnDefinition = "varchar(6) default ''")
	private String outTransporter = "";

	@Column(name = "Out_Transporter_Name", length = 50, columnDefinition = "varchar(50) default ''")
	private String outTransporterName = "";

	@Column(name = "Out_Gate_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date outGateOutDate = new Date(0);

	@Column(name = "In_Vehicle_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String inVehicleNo = "";

	@Column(name = "In_Transporter_Status", length = 1, columnDefinition = "char(1) default ''")
	private String inTransporterStatus = "";

	@Column(name = "In_Transporter", length = 6, columnDefinition = "varchar(6) default ''")
	private String inTransporter = "";

	@Column(name = "In_Transporter_Name", length = 50, columnDefinition = "varchar(50) default ''")
	private String inTransporterName = "";

	@Column(name = "In_Gate_In_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date inGateInDate = new Date(0);

	@Column(name = "RScan_In", length = 1, columnDefinition = "char(1) default 'N'")
	private String rScanIn = "N";

	@Column(name = "HUB_Stuff_ID", length = 15, columnDefinition = "varchar(15) default ''")
	private String hubStuffId = "";

	@Column(name = "Scanning_Status", length = 50, columnDefinition = "varchar(50) default NULL")
	private String scanningStatus = null;

	@Column(name = "ODC_Status", length = 15, columnDefinition = "varchar(15) default NULL")
	private String odcStatus = null;

	@Column(name = "Invoice_Assesed", length = 1, columnDefinition = "char(1) default 'N'")
	private String invoiceAssesed = "N";

	@Column(name = "Commodity_Code", length = 25, columnDefinition = "varchar(25) default ''")
	private String commodityCode = "";

	@Column(name = "CODECCO_MT_IN_STATUS", length = 1, columnDefinition = "char(1) default 'N'")
	private String codeccoMtInStatus = "N";

	@Column(name = "CODECCO_MT_IN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date codeccoMtInDate = new Date(0);

	@Column(name = "Factory_Code", columnDefinition = "int default '0'")
	private int factoryCode = 0;

	@Column(name = "Buffer_Code", columnDefinition = "int default '0'")
	private int bufferCode = 0;

	@Column(name = "Last_Location_Received_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lastLocationReceivedDate = new Date(0);

	@Column(name = "Location_Received_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date locationReceivedDate = new Date(0);

	@Column(name = "Tag_Remove_Status", length = 1, columnDefinition = "char(1) default 'N'")
	private String tagRemoveStatus = "N";

	@Column(name = "Tag_Remove_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date tagRemoveDate = new Date(0);

	@Column(name = "RFTag", length = 30, columnDefinition = "varchar(30) default ''")
	private String rfTag = "";

	@Column(name = "Tag_Receive_Status", length = 1, columnDefinition = "char(1) default 'N'")
	private String tagReceiveStatus = "N";

	@Column(name = "Tag_Receive_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date tagReceiveDate = new Date(0);

	@Column(name = "Eqactivity_flg", length = 1, columnDefinition = "varchar(1) default 'N'")
	private String eqactivityFlg = "N";

	@Column(name = "EDI_Cus_Menifest_Status", length = 1, columnDefinition = "char(1) default 'N'")
	private String ediCusMenifestStatus = "N";

	@Column(name = "EDI_Cus_Menifest_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date ediCusMenifestDate = new Date(0);

	@Column(name = "EDI_Cus_In_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date ediCusInDate = new Date(0);

	@Column(name = "EDI_Cus_In_Status", length = 1, columnDefinition = "char(1) default 'N'")
	private String ediCusInStatus = "N";
	
	@Column(name="Low_Bed",length = 1)
	private String lowBed;
	
	
	@Column(name="Vessel",length = 10)
	private String vessel;
	
	@Column(name = "TYPE_OF_PACKAGE", length = 15)
	private String typeOfPackage;

	@Column(name = "Gate_No", length = 15, columnDefinition = "varchar(15) default ''")
	private String gateNo = "";
	
	
	@Column(name = "Remarks", length = 100, columnDefinition = "varchar(100) default ''")
	private String remarks = "";
	
	
	
	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getGateNo() {
		return gateNo;
	}

	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}
	

	public String getLowBed() {
		return lowBed;
	}

	public void setLowBed(String lowBed) {
		this.lowBed = lowBed;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public GateIn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

	public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
			String docRefNo, String lineNo, int srNo, String inBondingId, Date docRefDate, String boeNo, Date boeDate,
			String invoiceNo, Date invoiceDate, String nocNo, Date nocDate, String gateInType, String profitcentreId,
			String processId, String cartingTransId, BigDecimal cartedPackages, String viaNo, String containerNo,
			String containerSize, String containerType, String containerStatus, String containerSealNo,
			String customsSealNo, String actualSealNo, String sealMismatch, String vehicleType, String isoCode,
			BigDecimal grossWeight, BigDecimal eirGrossWeight, BigDecimal tareWeight, BigDecimal cargoWeight,
			BigDecimal weighmentWeight, String weighmentPassNo, String weighmentWtUser, Date weighmentWtDate,
			String weighmentDone, String overDimension, String hazardous, String hazClass, String sa, String sl,
			String onAccountOf, String cha, String chaCode, String importerName, String commodityDescription,
			BigDecimal actualNoOfPackages, BigDecimal fob, BigDecimal qtyTakenIn, BigDecimal transferPackages,
			BigDecimal nilPackages, String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate, String shift,
			String portExitNo, Date portExitDate, String terminal, String origin, String refer, String temperature,
			String containerHealth, String yardLocation, String yardBlock, String yardCell, String yardLocation1,
			String yardBlock1, String yardCell1, String transporterStatus, String transporterName, String transporter,
			String vehicleNo, String driverName, String damageDetails, String comments, String specialRemarks,
			String bookingNo, String scanningDoneStatus, String scanningEditedBy, Date scanningDoneDate,
			String weighmentFlag, String damageReportFlag, String eqId, Date eqDate, String eqIdIn, Date eqDateIn,
			String eqIdOut, Date eqDateOut, String status, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String tripType, String drt, String scannerType,
			String cartingStatus, String labour, String fk3mt, String fk5mt, String fk10mt, String hydra12mt,
			String crane, String fromSbTransId, BigDecimal fromSbLineNo, BigDecimal fromSbNo, String uploadUser,
			Date uploadDate, String removeUser, Date removeDate, String imagePath, String backImage, String blNo,
			Date blDate, String holdStatus, String holdType, String holdUser, Date holdDate, String holdRemarks,
			String stuffTallyId, String prGatePassNo, String prGateOutId, Date prGateOutDate, Date stuffTallyDate,
			String stuffTallyStatus, String backToTown, String backToTownRemark, Date backToTownDate, String unNo,
			String commodity, String assesmentId, String pnStatus, String jobOrderId, Date jobDate, String area,
			String cargoType, BigDecimal weightTakenIn, String rScanOut, String outVehicleNo,
			String outTransporterStatus, String outTransporter, String outTransporterName, Date outGateOutDate,
			String inVehicleNo, String inTransporterStatus, String inTransporter, String inTransporterName,
			Date inGateInDate, String rScanIn, String hubStuffId, String scanningStatus, String odcStatus,
			String invoiceAssesed, String commodityCode, String codeccoMtInStatus, Date codeccoMtInDate,
			int factoryCode, int bufferCode, Date lastLocationReceivedDate, Date locationReceivedDate,
			String tagRemoveStatus, Date tagRemoveDate, String rfTag, String tagReceiveStatus, Date tagReceiveDate,
			String eqactivityFlg, String ediCusMenifestStatus, Date ediCusMenifestDate, Date ediCusInDate,
			String ediCusInStatus, String lowBed, String vessel, String typeOfPackage, String gateNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.lineNo = lineNo;
		this.srNo = srNo;
		this.inBondingId = inBondingId;
		this.docRefDate = docRefDate;
		this.boeNo = boeNo;
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
		this.actualSealNo = actualSealNo;
		this.sealMismatch = sealMismatch;
		this.vehicleType = vehicleType;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.eirGrossWeight = eirGrossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.weighmentWeight = weighmentWeight;
		this.weighmentPassNo = weighmentPassNo;
		this.weighmentWtUser = weighmentWtUser;
		this.weighmentWtDate = weighmentWtDate;
		this.weighmentDone = weighmentDone;
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
		this.transferPackages = transferPackages;
		this.nilPackages = nilPackages;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
		this.shift = shift;
		this.portExitNo = portExitNo;
		this.portExitDate = portExitDate;
		this.terminal = terminal;
		this.origin = origin;
		this.refer = refer;
		this.temperature = temperature;
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
		this.cartingStatus = cartingStatus;
		this.labour = labour;
		this.fk3mt = fk3mt;
		this.fk5mt = fk5mt;
		this.fk10mt = fk10mt;
		this.hydra12mt = hydra12mt;
		this.crane = crane;
		this.fromSbTransId = fromSbTransId;
		this.fromSbLineNo = fromSbLineNo;
		this.fromSbNo = fromSbNo;
		this.uploadUser = uploadUser;
		this.uploadDate = uploadDate;
		this.removeUser = removeUser;
		this.removeDate = removeDate;
		this.imagePath = imagePath;
		this.backImage = backImage;
		this.blNo = blNo;
		this.blDate = blDate;
		this.holdStatus = holdStatus;
		this.holdType = holdType;
		this.holdUser = holdUser;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.stuffTallyId = stuffTallyId;
		this.prGatePassNo = prGatePassNo;
		this.prGateOutId = prGateOutId;
		this.prGateOutDate = prGateOutDate;
		this.stuffTallyDate = stuffTallyDate;
		this.stuffTallyStatus = stuffTallyStatus;
		this.backToTown = backToTown;
		this.backToTownRemark = backToTownRemark;
		this.backToTownDate = backToTownDate;
		this.unNo = unNo;
		this.commodity = commodity;
		this.assesmentId = assesmentId;
		this.pnStatus = pnStatus;
		this.jobOrderId = jobOrderId;
		this.jobDate = jobDate;
		this.area = area;
		this.cargoType = cargoType;
		this.weightTakenIn = weightTakenIn;
		this.rScanOut = rScanOut;
		this.outVehicleNo = outVehicleNo;
		this.outTransporterStatus = outTransporterStatus;
		this.outTransporter = outTransporter;
		this.outTransporterName = outTransporterName;
		this.outGateOutDate = outGateOutDate;
		this.inVehicleNo = inVehicleNo;
		this.inTransporterStatus = inTransporterStatus;
		this.inTransporter = inTransporter;
		this.inTransporterName = inTransporterName;
		this.inGateInDate = inGateInDate;
		this.rScanIn = rScanIn;
		this.hubStuffId = hubStuffId;
		this.scanningStatus = scanningStatus;
		this.odcStatus = odcStatus;
		this.invoiceAssesed = invoiceAssesed;
		this.commodityCode = commodityCode;
		this.codeccoMtInStatus = codeccoMtInStatus;
		this.codeccoMtInDate = codeccoMtInDate;
		this.factoryCode = factoryCode;
		this.bufferCode = bufferCode;
		this.lastLocationReceivedDate = lastLocationReceivedDate;
		this.locationReceivedDate = locationReceivedDate;
		this.tagRemoveStatus = tagRemoveStatus;
		this.tagRemoveDate = tagRemoveDate;
		this.rfTag = rfTag;
		this.tagReceiveStatus = tagReceiveStatus;
		this.tagReceiveDate = tagReceiveDate;
		this.eqactivityFlg = eqactivityFlg;
		this.ediCusMenifestStatus = ediCusMenifestStatus;
		this.ediCusMenifestDate = ediCusMenifestDate;
		this.ediCusInDate = ediCusInDate;
		this.ediCusInStatus = ediCusInStatus;
		this.lowBed = lowBed;
		this.vessel = vessel;
		this.typeOfPackage = typeOfPackage;
		this.gateNo = gateNo;
	}

	public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
			String docRefNo, String lineNo, int srNo, String inBondingId, Date docRefDate, String boeNo, Date boeDate,
			String invoiceNo, Date invoiceDate, String nocNo, Date nocDate, String gateInType, String profitcentreId,
			String processId, String cartingTransId, BigDecimal cartedPackages, String viaNo, String containerNo,
			String containerSize, String containerType, String containerStatus, String containerSealNo,
			String customsSealNo, String actualSealNo, String sealMismatch, String vehicleType, String isoCode,
			BigDecimal grossWeight, BigDecimal eirGrossWeight, BigDecimal tareWeight, BigDecimal cargoWeight,
			BigDecimal weighmentWeight, String weighmentPassNo, String weighmentWtUser, Date weighmentWtDate,
			String weighmentDone, String overDimension, String hazardous, String hazClass, String sa, String sl,
			String onAccountOf, String cha, String chaCode, String importerName, String commodityDescription,
			BigDecimal actualNoOfPackages, BigDecimal fob, BigDecimal qtyTakenIn, BigDecimal transferPackages,
			BigDecimal nilPackages, String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate, String shift,
			String portExitNo, Date portExitDate, String terminal, String origin, String refer, String temperature,
			String containerHealth, String yardLocation, String yardBlock, String yardCell, String yardLocation1,
			String yardBlock1, String yardCell1, String transporterStatus, String transporterName, String transporter,
			String vehicleNo, String driverName, String damageDetails, String comments, String specialRemarks,
			String bookingNo, String scanningDoneStatus, String scanningEditedBy, Date scanningDoneDate,
			String weighmentFlag, String damageReportFlag, String eqId, Date eqDate, String eqIdIn, Date eqDateIn,
			String eqIdOut, Date eqDateOut, String status, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String tripType, String drt, String scannerType,
			String cartingStatus, String labour, String fk3mt, String fk5mt, String fk10mt, String hydra12mt,
			String crane, String fromSbTransId, BigDecimal fromSbLineNo, BigDecimal fromSbNo, String uploadUser,
			Date uploadDate, String removeUser, Date removeDate, String imagePath, String backImage, String blNo,
			Date blDate, String holdStatus, String holdType, String holdUser, Date holdDate, String holdRemarks,
			String stuffTallyId, String prGatePassNo, String prGateOutId, Date prGateOutDate, Date stuffTallyDate,
			String stuffTallyStatus, String backToTown, String backToTownRemark, Date backToTownDate, String unNo,
			String commodity, String assesmentId, String pnStatus, String jobOrderId, Date jobDate, String area,
			String cargoType, BigDecimal weightTakenIn, String rScanOut, String outVehicleNo,
			String outTransporterStatus, String outTransporter, String outTransporterName, Date outGateOutDate,
			String inVehicleNo, String inTransporterStatus, String inTransporter, String inTransporterName,
			Date inGateInDate, String rScanIn, String hubStuffId, String scanningStatus, String odcStatus,
			String invoiceAssesed, String commodityCode, String codeccoMtInStatus, Date codeccoMtInDate,
			int factoryCode, int bufferCode, Date lastLocationReceivedDate, Date locationReceivedDate,
			String tagRemoveStatus, Date tagRemoveDate, String rfTag, String tagReceiveStatus, Date tagReceiveDate,
			String eqactivityFlg, String ediCusMenifestStatus, Date ediCusMenifestDate, Date ediCusInDate,
			String ediCusInStatus, String lowBed, String vessel, String typeOfPackage) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.lineNo = lineNo;
		this.srNo = srNo;
		this.inBondingId = inBondingId;
		this.docRefDate = docRefDate;
		this.boeNo = boeNo;
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
		this.actualSealNo = actualSealNo;
		this.sealMismatch = sealMismatch;
		this.vehicleType = vehicleType;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.eirGrossWeight = eirGrossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.weighmentWeight = weighmentWeight;
		this.weighmentPassNo = weighmentPassNo;
		this.weighmentWtUser = weighmentWtUser;
		this.weighmentWtDate = weighmentWtDate;
		this.weighmentDone = weighmentDone;
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
		this.transferPackages = transferPackages;
		this.nilPackages = nilPackages;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
		this.shift = shift;
		this.portExitNo = portExitNo;
		this.portExitDate = portExitDate;
		this.terminal = terminal;
		this.origin = origin;
		this.refer = refer;
		this.temperature = temperature;
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
		this.cartingStatus = cartingStatus;
		this.labour = labour;
		this.fk3mt = fk3mt;
		this.fk5mt = fk5mt;
		this.fk10mt = fk10mt;
		this.hydra12mt = hydra12mt;
		this.crane = crane;
		this.fromSbTransId = fromSbTransId;
		this.fromSbLineNo = fromSbLineNo;
		this.fromSbNo = fromSbNo;
		this.uploadUser = uploadUser;
		this.uploadDate = uploadDate;
		this.removeUser = removeUser;
		this.removeDate = removeDate;
		this.imagePath = imagePath;
		this.backImage = backImage;
		this.blNo = blNo;
		this.blDate = blDate;
		this.holdStatus = holdStatus;
		this.holdType = holdType;
		this.holdUser = holdUser;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.stuffTallyId = stuffTallyId;
		this.prGatePassNo = prGatePassNo;
		this.prGateOutId = prGateOutId;
		this.prGateOutDate = prGateOutDate;
		this.stuffTallyDate = stuffTallyDate;
		this.stuffTallyStatus = stuffTallyStatus;
		this.backToTown = backToTown;
		this.backToTownRemark = backToTownRemark;
		this.backToTownDate = backToTownDate;
		this.unNo = unNo;
		this.commodity = commodity;
		this.assesmentId = assesmentId;
		this.pnStatus = pnStatus;
		this.jobOrderId = jobOrderId;
		this.jobDate = jobDate;
		this.area = area;
		this.cargoType = cargoType;
		this.weightTakenIn = weightTakenIn;
		this.rScanOut = rScanOut;
		this.outVehicleNo = outVehicleNo;
		this.outTransporterStatus = outTransporterStatus;
		this.outTransporter = outTransporter;
		this.outTransporterName = outTransporterName;
		this.outGateOutDate = outGateOutDate;
		this.inVehicleNo = inVehicleNo;
		this.inTransporterStatus = inTransporterStatus;
		this.inTransporter = inTransporter;
		this.inTransporterName = inTransporterName;
		this.inGateInDate = inGateInDate;
		this.rScanIn = rScanIn;
		this.hubStuffId = hubStuffId;
		this.scanningStatus = scanningStatus;
		this.odcStatus = odcStatus;
		this.invoiceAssesed = invoiceAssesed;
		this.commodityCode = commodityCode;
		this.codeccoMtInStatus = codeccoMtInStatus;
		this.codeccoMtInDate = codeccoMtInDate;
		this.factoryCode = factoryCode;
		this.bufferCode = bufferCode;
		this.lastLocationReceivedDate = lastLocationReceivedDate;
		this.locationReceivedDate = locationReceivedDate;
		this.tagRemoveStatus = tagRemoveStatus;
		this.tagRemoveDate = tagRemoveDate;
		this.rfTag = rfTag;
		this.tagReceiveStatus = tagReceiveStatus;
		this.tagReceiveDate = tagReceiveDate;
		this.eqactivityFlg = eqactivityFlg;
		this.ediCusMenifestStatus = ediCusMenifestStatus;
		this.ediCusMenifestDate = ediCusMenifestDate;
		this.ediCusInDate = ediCusInDate;
		this.ediCusInStatus = ediCusInStatus;
		this.lowBed = lowBed;
		this.vessel = vessel;
		this.typeOfPackage = typeOfPackage;
	}

	public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
			String docRefNo, String lineNo, int srNo, String inBondingId, Date docRefDate, String boeNo, Date boeDate,
			String invoiceNo, Date invoiceDate, String nocNo, Date nocDate, String gateInType, String profitcentreId,
			String processId, String cartingTransId, BigDecimal cartedPackages, String viaNo, String containerNo,
			String containerSize, String containerType, String containerStatus, String containerSealNo,
			String customsSealNo, String actualSealNo, String sealMismatch, String vehicleType, String isoCode,
			BigDecimal grossWeight, BigDecimal eirGrossWeight, BigDecimal tareWeight, BigDecimal cargoWeight,
			BigDecimal weighmentWeight, String weighmentPassNo, String weighmentWtUser, Date weighmentWtDate,
			String weighmentDone, String overDimension, String hazardous, String hazClass, String sa, String sl,
			String onAccountOf, String cha, String chaCode, String importerName, String commodityDescription,
			BigDecimal actualNoOfPackages, BigDecimal fob, BigDecimal qtyTakenIn, BigDecimal transferPackages,
			BigDecimal nilPackages, String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate, String shift,
			String portExitNo, Date portExitDate, String terminal, String origin, String refer, String temperature,
			String containerHealth, String yardLocation, String yardBlock, String yardCell, String yardLocation1,
			String yardBlock1, String yardCell1, String transporterStatus, String transporterName, String transporter,
			String vehicleNo, String driverName, String damageDetails, String comments, String specialRemarks,
			String bookingNo, String scanningDoneStatus, String scanningEditedBy, Date scanningDoneDate,
			String weighmentFlag, String damageReportFlag, String eqId, Date eqDate, String eqIdIn, Date eqDateIn,
			String eqIdOut, Date eqDateOut, String status, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String tripType, String drt, String scannerType,
			String cartingStatus, String labour, String fk3mt, String fk5mt, String fk10mt, String hydra12mt,
			String crane, String fromSbTransId, BigDecimal fromSbLineNo, BigDecimal fromSbNo, String uploadUser,
			Date uploadDate, String removeUser, Date removeDate, String imagePath, String backImage, String blNo,
			Date blDate, String holdStatus, String holdType, String holdUser, Date holdDate, String holdRemarks,
			String stuffTallyId, String prGatePassNo, String prGateOutId, Date prGateOutDate, Date stuffTallyDate,
			String stuffTallyStatus, String backToTown, String backToTownRemark, Date backToTownDate, String unNo,
			String commodity, String assesmentId, String pnStatus, String jobOrderId, Date jobDate, String area,
			String cargoType, BigDecimal weightTakenIn, String rScanOut, String outVehicleNo,
			String outTransporterStatus, String outTransporter, String outTransporterName, Date outGateOutDate,
			String inVehicleNo, String inTransporterStatus, String inTransporter, String inTransporterName,
			Date inGateInDate, String rScanIn, String hubStuffId, String scanningStatus, String odcStatus,
			String invoiceAssesed, String commodityCode, String codeccoMtInStatus, Date codeccoMtInDate,
			int factoryCode, int bufferCode, Date lastLocationReceivedDate, Date locationReceivedDate,
			String tagRemoveStatus, Date tagRemoveDate, String rfTag, String tagReceiveStatus, Date tagReceiveDate,
			String eqactivityFlg, String ediCusMenifestStatus, Date ediCusMenifestDate, Date ediCusInDate,
			String ediCusInStatus) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.lineNo = lineNo;
		this.srNo = srNo;
		this.inBondingId = inBondingId;
		this.docRefDate = docRefDate;
		this.boeNo = boeNo;
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
		this.actualSealNo = actualSealNo;
		this.sealMismatch = sealMismatch;
		this.vehicleType = vehicleType;
		this.isoCode = isoCode;
		this.grossWeight = grossWeight;
		this.eirGrossWeight = eirGrossWeight;
		this.tareWeight = tareWeight;
		this.cargoWeight = cargoWeight;
		this.weighmentWeight = weighmentWeight;
		this.weighmentPassNo = weighmentPassNo;
		this.weighmentWtUser = weighmentWtUser;
		this.weighmentWtDate = weighmentWtDate;
		this.weighmentDone = weighmentDone;
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
		this.transferPackages = transferPackages;
		this.nilPackages = nilPackages;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
		this.shift = shift;
		this.portExitNo = portExitNo;
		this.portExitDate = portExitDate;
		this.terminal = terminal;
		this.origin = origin;
		this.refer = refer;
		this.temperature = temperature;
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
		this.cartingStatus = cartingStatus;
		this.labour = labour;
		this.fk3mt = fk3mt;
		this.fk5mt = fk5mt;
		this.fk10mt = fk10mt;
		this.hydra12mt = hydra12mt;
		this.crane = crane;
		this.fromSbTransId = fromSbTransId;
		this.fromSbLineNo = fromSbLineNo;
		this.fromSbNo = fromSbNo;
		this.uploadUser = uploadUser;
		this.uploadDate = uploadDate;
		this.removeUser = removeUser;
		this.removeDate = removeDate;
		this.imagePath = imagePath;
		this.backImage = backImage;
		this.blNo = blNo;
		this.blDate = blDate;
		this.holdStatus = holdStatus;
		this.holdType = holdType;
		this.holdUser = holdUser;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.stuffTallyId = stuffTallyId;
		this.prGatePassNo = prGatePassNo;
		this.prGateOutId = prGateOutId;
		this.prGateOutDate = prGateOutDate;
		this.stuffTallyDate = stuffTallyDate;
		this.stuffTallyStatus = stuffTallyStatus;
		this.backToTown = backToTown;
		this.backToTownRemark = backToTownRemark;
		this.backToTownDate = backToTownDate;
		this.unNo = unNo;
		this.commodity = commodity;
		this.assesmentId = assesmentId;
		this.pnStatus = pnStatus;
		this.jobOrderId = jobOrderId;
		this.jobDate = jobDate;
		this.area = area;
		this.cargoType = cargoType;
		this.weightTakenIn = weightTakenIn;
		this.rScanOut = rScanOut;
		this.outVehicleNo = outVehicleNo;
		this.outTransporterStatus = outTransporterStatus;
		this.outTransporter = outTransporter;
		this.outTransporterName = outTransporterName;
		this.outGateOutDate = outGateOutDate;
		this.inVehicleNo = inVehicleNo;
		this.inTransporterStatus = inTransporterStatus;
		this.inTransporter = inTransporter;
		this.inTransporterName = inTransporterName;
		this.inGateInDate = inGateInDate;
		this.rScanIn = rScanIn;
		this.hubStuffId = hubStuffId;
		this.scanningStatus = scanningStatus;
		this.odcStatus = odcStatus;
		this.invoiceAssesed = invoiceAssesed;
		this.commodityCode = commodityCode;
		this.codeccoMtInStatus = codeccoMtInStatus;
		this.codeccoMtInDate = codeccoMtInDate;
		this.factoryCode = factoryCode;
		this.bufferCode = bufferCode;
		this.lastLocationReceivedDate = lastLocationReceivedDate;
		this.locationReceivedDate = locationReceivedDate;
		this.tagRemoveStatus = tagRemoveStatus;
		this.tagRemoveDate = tagRemoveDate;
		this.rfTag = rfTag;
		this.tagReceiveStatus = tagReceiveStatus;
		this.tagReceiveDate = tagReceiveDate;
		this.eqactivityFlg = eqactivityFlg;
		this.ediCusMenifestStatus = ediCusMenifestStatus;
		this.ediCusMenifestDate = ediCusMenifestDate;
		this.ediCusInDate = ediCusInDate;
		this.ediCusInStatus = ediCusInStatus;
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

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
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

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
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

	public String getActualSealNo() {
		return actualSealNo;
	}

	public void setActualSealNo(String actualSealNo) {
		this.actualSealNo = actualSealNo;
	}

	public String getSealMismatch() {
		return sealMismatch;
	}

	public void setSealMismatch(String sealMismatch) {
		this.sealMismatch = sealMismatch;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
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

	public String getWeighmentWtUser() {
		return weighmentWtUser;
	}

	public void setWeighmentWtUser(String weighmentWtUser) {
		this.weighmentWtUser = weighmentWtUser;
	}

	public Date getWeighmentWtDate() {
		return weighmentWtDate;
	}

	public void setWeighmentWtDate(Date weighmentWtDate) {
		this.weighmentWtDate = weighmentWtDate;
	}

	public String getWeighmentDone() {
		return weighmentDone;
	}

	public void setWeighmentDone(String weighmentDone) {
		this.weighmentDone = weighmentDone;
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

	public BigDecimal getTransferPackages() {
		return transferPackages;
	}

	public void setTransferPackages(BigDecimal transferPackages) {
		this.transferPackages = transferPackages;
	}

	public BigDecimal getNilPackages() {
		return nilPackages;
	}

	public void setNilPackages(BigDecimal nilPackages) {
		this.nilPackages = nilPackages;
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

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getRefer() {
		return refer;
	}

	public void setRefer(String refer) {
		this.refer = refer;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
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

	public String getCartingStatus() {
		return cartingStatus;
	}

	public void setCartingStatus(String cartingStatus) {
		this.cartingStatus = cartingStatus;
	}

	public String getLabour() {
		return labour;
	}

	public void setLabour(String labour) {
		this.labour = labour;
	}

	public String getFk3mt() {
		return fk3mt;
	}

	public void setFk3mt(String fk3mt) {
		this.fk3mt = fk3mt;
	}

	public String getFk5mt() {
		return fk5mt;
	}

	public void setFk5mt(String fk5mt) {
		this.fk5mt = fk5mt;
	}

	public String getFk10mt() {
		return fk10mt;
	}

	public void setFk10mt(String fk10mt) {
		this.fk10mt = fk10mt;
	}

	public String getHydra12mt() {
		return hydra12mt;
	}

	public void setHydra12mt(String hydra12mt) {
		this.hydra12mt = hydra12mt;
	}

	public String getCrane() {
		return crane;
	}

	public void setCrane(String crane) {
		this.crane = crane;
	}

	public String getFromSbTransId() {
		return fromSbTransId;
	}

	public void setFromSbTransId(String fromSbTransId) {
		this.fromSbTransId = fromSbTransId;
	}

	public BigDecimal getFromSbLineNo() {
		return fromSbLineNo;
	}

	public void setFromSbLineNo(BigDecimal fromSbLineNo) {
		this.fromSbLineNo = fromSbLineNo;
	}

	public BigDecimal getFromSbNo() {
		return fromSbNo;
	}

	public void setFromSbNo(BigDecimal fromSbNo) {
		this.fromSbNo = fromSbNo;
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

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getHoldType() {
		return holdType;
	}

	public void setHoldType(String holdType) {
		this.holdType = holdType;
	}

	public String getHoldUser() {
		return holdUser;
	}

	public void setHoldUser(String holdUser) {
		this.holdUser = holdUser;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public String getHoldRemarks() {
		return holdRemarks;
	}

	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	public String getPrGatePassNo() {
		return prGatePassNo;
	}

	public void setPrGatePassNo(String prGatePassNo) {
		this.prGatePassNo = prGatePassNo;
	}

	public String getPrGateOutId() {
		return prGateOutId;
	}

	public void setPrGateOutId(String prGateOutId) {
		this.prGateOutId = prGateOutId;
	}

	public Date getPrGateOutDate() {
		return prGateOutDate;
	}

	public void setPrGateOutDate(Date prGateOutDate) {
		this.prGateOutDate = prGateOutDate;
	}

	public Date getStuffTallyDate() {
		return stuffTallyDate;
	}

	public void setStuffTallyDate(Date stuffTallyDate) {
		this.stuffTallyDate = stuffTallyDate;
	}

	public String getStuffTallyStatus() {
		return stuffTallyStatus;
	}

	public void setStuffTallyStatus(String stuffTallyStatus) {
		this.stuffTallyStatus = stuffTallyStatus;
	}

	public String getBackToTown() {
		return backToTown;
	}

	public void setBackToTown(String backToTown) {
		this.backToTown = backToTown;
	}

	public String getBackToTownRemark() {
		return backToTownRemark;
	}

	public void setBackToTownRemark(String backToTownRemark) {
		this.backToTownRemark = backToTownRemark;
	}

	public Date getBackToTownDate() {
		return backToTownDate;
	}

	public void setBackToTownDate(Date backToTownDate) {
		this.backToTownDate = backToTownDate;
	}

	public String getUnNo() {
		return unNo;
	}

	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getAssesmentId() {
		return assesmentId;
	}

	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}

	public String getPnStatus() {
		return pnStatus;
	}

	public void setPnStatus(String pnStatus) {
		this.pnStatus = pnStatus;
	}

	public String getJobOrderId() {
		return jobOrderId;
	}

	public void setJobOrderId(String jobOrderId) {
		this.jobOrderId = jobOrderId;
	}

	public Date getJobDate() {
		return jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public BigDecimal getWeightTakenIn() {
		return weightTakenIn;
	}

	public void setWeightTakenIn(BigDecimal weightTakenIn) {
		this.weightTakenIn = weightTakenIn;
	}

	public String getrScanOut() {
		return rScanOut;
	}

	public void setrScanOut(String rScanOut) {
		this.rScanOut = rScanOut;
	}

	public String getOutVehicleNo() {
		return outVehicleNo;
	}

	public void setOutVehicleNo(String outVehicleNo) {
		this.outVehicleNo = outVehicleNo;
	}

	public String getOutTransporterStatus() {
		return outTransporterStatus;
	}

	public void setOutTransporterStatus(String outTransporterStatus) {
		this.outTransporterStatus = outTransporterStatus;
	}

	public String getOutTransporter() {
		return outTransporter;
	}

	public void setOutTransporter(String outTransporter) {
		this.outTransporter = outTransporter;
	}

	public String getOutTransporterName() {
		return outTransporterName;
	}

	public void setOutTransporterName(String outTransporterName) {
		this.outTransporterName = outTransporterName;
	}

	public Date getOutGateOutDate() {
		return outGateOutDate;
	}

	public void setOutGateOutDate(Date outGateOutDate) {
		this.outGateOutDate = outGateOutDate;
	}

	public String getInVehicleNo() {
		return inVehicleNo;
	}

	public void setInVehicleNo(String inVehicleNo) {
		this.inVehicleNo = inVehicleNo;
	}

	public String getInTransporterStatus() {
		return inTransporterStatus;
	}

	public void setInTransporterStatus(String inTransporterStatus) {
		this.inTransporterStatus = inTransporterStatus;
	}

	public String getInTransporter() {
		return inTransporter;
	}

	public void setInTransporter(String inTransporter) {
		this.inTransporter = inTransporter;
	}

	public String getInTransporterName() {
		return inTransporterName;
	}

	public void setInTransporterName(String inTransporterName) {
		this.inTransporterName = inTransporterName;
	}

	public Date getInGateInDate() {
		return inGateInDate;
	}

	public void setInGateInDate(Date inGateInDate) {
		this.inGateInDate = inGateInDate;
	}

	public String getrScanIn() {
		return rScanIn;
	}

	public void setrScanIn(String rScanIn) {
		this.rScanIn = rScanIn;
	}

	public String getHubStuffId() {
		return hubStuffId;
	}

	public void setHubStuffId(String hubStuffId) {
		this.hubStuffId = hubStuffId;
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

	public String getInvoiceAssesed() {
		return invoiceAssesed;
	}

	public void setInvoiceAssesed(String invoiceAssesed) {
		this.invoiceAssesed = invoiceAssesed;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCodeccoMtInStatus() {
		return codeccoMtInStatus;
	}

	public void setCodeccoMtInStatus(String codeccoMtInStatus) {
		this.codeccoMtInStatus = codeccoMtInStatus;
	}

	public Date getCodeccoMtInDate() {
		return codeccoMtInDate;
	}

	public void setCodeccoMtInDate(Date codeccoMtInDate) {
		this.codeccoMtInDate = codeccoMtInDate;
	}

	public int getFactoryCode() {
		return factoryCode;
	}

	public void setFactoryCode(int factoryCode) {
		this.factoryCode = factoryCode;
	}

	public int getBufferCode() {
		return bufferCode;
	}

	public void setBufferCode(int bufferCode) {
		this.bufferCode = bufferCode;
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

	public String getTagRemoveStatus() {
		return tagRemoveStatus;
	}

	public void setTagRemoveStatus(String tagRemoveStatus) {
		this.tagRemoveStatus = tagRemoveStatus;
	}

	public Date getTagRemoveDate() {
		return tagRemoveDate;
	}

	public void setTagRemoveDate(Date tagRemoveDate) {
		this.tagRemoveDate = tagRemoveDate;
	}

	public String getRfTag() {
		return rfTag;
	}

	public void setRfTag(String rfTag) {
		this.rfTag = rfTag;
	}

	public String getTagReceiveStatus() {
		return tagReceiveStatus;
	}

	public void setTagReceiveStatus(String tagReceiveStatus) {
		this.tagReceiveStatus = tagReceiveStatus;
	}

	public Date getTagReceiveDate() {
		return tagReceiveDate;
	}

	public void setTagReceiveDate(Date tagReceiveDate) {
		this.tagReceiveDate = tagReceiveDate;
	}

	public String getEqactivityFlg() {
		return eqactivityFlg;
	}

	public void setEqactivityFlg(String eqactivityFlg) {
		this.eqactivityFlg = eqactivityFlg;
	}

	public String getEdiCusMenifestStatus() {
		return ediCusMenifestStatus;
	}

	public void setEdiCusMenifestStatus(String ediCusMenifestStatus) {
		this.ediCusMenifestStatus = ediCusMenifestStatus;
	}

	public Date getEdiCusMenifestDate() {
		return ediCusMenifestDate;
	}

	public void setEdiCusMenifestDate(Date ediCusMenifestDate) {
		this.ediCusMenifestDate = ediCusMenifestDate;
	}

	public Date getEdiCusInDate() {
		return ediCusInDate;
	}

	public void setEdiCusInDate(Date ediCusInDate) {
		this.ediCusInDate = ediCusInDate;
	}

	public String getEdiCusInStatus() {
		return ediCusInStatus;
	}

	public void setEdiCusInStatus(String ediCusInStatus) {
		this.ediCusInStatus = ediCusInStatus;
	}

	@Override
	public String toString() {
		return "GateIn [companyId=" + companyId + ", branchId=" + branchId + ", gateInId=" + gateInId + ", finYear="
				+ finYear + ", erpDocRefNo=" + erpDocRefNo + ", docRefNo=" + docRefNo + ", lineNo=" + lineNo + ", srNo="
				+ srNo + ", inBondingId=" + inBondingId + ", docRefDate=" + docRefDate + ", boeNo=" + boeNo
				+ ", boeDate=" + boeDate + ", invoiceNo=" + invoiceNo + ", invoiceDate=" + invoiceDate + ", nocNo="
				+ nocNo + ", nocDate=" + nocDate + ", gateInType=" + gateInType + ", profitcentreId=" + profitcentreId
				+ ", processId=" + processId + ", cartingTransId=" + cartingTransId + ", cartedPackages="
				+ cartedPackages + ", viaNo=" + viaNo + ", containerNo=" + containerNo + ", containerSize="
				+ containerSize + ", containerType=" + containerType + ", containerStatus=" + containerStatus
				+ ", containerSealNo=" + containerSealNo + ", customsSealNo=" + customsSealNo + ", actualSealNo="
				+ actualSealNo + ", sealMismatch=" + sealMismatch + ", vehicleType=" + vehicleType + ", isoCode="
				+ isoCode + ", grossWeight=" + grossWeight + ", eirGrossWeight=" + eirGrossWeight + ", tareWeight="
				+ tareWeight + ", cargoWeight=" + cargoWeight + ", weighmentWeight=" + weighmentWeight
				+ ", weighmentPassNo=" + weighmentPassNo + ", weighmentWtUser=" + weighmentWtUser + ", weighmentWtDate="
				+ weighmentWtDate + ", weighmentDone=" + weighmentDone + ", overDimension=" + overDimension
				+ ", hazardous=" + hazardous + ", hazClass=" + hazClass + ", sa=" + sa + ", sl=" + sl + ", onAccountOf="
				+ onAccountOf + ", cha=" + cha + ", chaCode=" + chaCode + ", importerName=" + importerName
				+ ", commodityDescription=" + commodityDescription + ", actualNoOfPackages=" + actualNoOfPackages
				+ ", fob=" + fob + ", qtyTakenIn=" + qtyTakenIn + ", transferPackages=" + transferPackages
				+ ", nilPackages=" + nilPackages + ", deliveryOrderNo=" + deliveryOrderNo + ", deliveryOrderDate="
				+ deliveryOrderDate + ", doValidityDate=" + doValidityDate + ", shift=" + shift + ", portExitNo="
				+ portExitNo + ", portExitDate=" + portExitDate + ", terminal=" + terminal + ", origin=" + origin
				+ ", refer=" + refer + ", temperature=" + temperature + ", containerHealth=" + containerHealth
				+ ", yardLocation=" + yardLocation + ", yardBlock=" + yardBlock + ", yardCell=" + yardCell
				+ ", yardLocation1=" + yardLocation1 + ", yardBlock1=" + yardBlock1 + ", yardCell1=" + yardCell1
				+ ", transporterStatus=" + transporterStatus + ", transporterName=" + transporterName + ", transporter="
				+ transporter + ", vehicleNo=" + vehicleNo + ", driverName=" + driverName + ", damageDetails="
				+ damageDetails + ", comments=" + comments + ", specialRemarks=" + specialRemarks + ", bookingNo="
				+ bookingNo + ", scanningDoneStatus=" + scanningDoneStatus + ", scanningEditedBy=" + scanningEditedBy
				+ ", scanningDoneDate=" + scanningDoneDate + ", weighmentFlag=" + weighmentFlag + ", damageReportFlag="
				+ damageReportFlag + ", eqId=" + eqId + ", eqDate=" + eqDate + ", eqIdIn=" + eqIdIn + ", eqDateIn="
				+ eqDateIn + ", eqIdOut=" + eqIdOut + ", eqDateOut=" + eqDateOut + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", tripType=" + tripType + ", drt="
				+ drt + ", scannerType=" + scannerType + ", cartingStatus=" + cartingStatus + ", labour=" + labour
				+ ", fk3mt=" + fk3mt + ", fk5mt=" + fk5mt + ", fk10mt=" + fk10mt + ", hydra12mt=" + hydra12mt
				+ ", crane=" + crane + ", fromSbTransId=" + fromSbTransId + ", fromSbLineNo=" + fromSbLineNo
				+ ", fromSbNo=" + fromSbNo + ", uploadUser=" + uploadUser + ", uploadDate=" + uploadDate
				+ ", removeUser=" + removeUser + ", removeDate=" + removeDate + ", imagePath=" + imagePath
				+ ", backImage=" + backImage + ", blNo=" + blNo + ", blDate=" + blDate + ", holdStatus=" + holdStatus
				+ ", holdType=" + holdType + ", holdUser=" + holdUser + ", holdDate=" + holdDate + ", holdRemarks="
				+ holdRemarks + ", stuffTallyId=" + stuffTallyId + ", prGatePassNo=" + prGatePassNo + ", prGateOutId="
				+ prGateOutId + ", prGateOutDate=" + prGateOutDate + ", stuffTallyDate=" + stuffTallyDate
				+ ", stuffTallyStatus=" + stuffTallyStatus + ", backToTown=" + backToTown + ", backToTownRemark="
				+ backToTownRemark + ", backToTownDate=" + backToTownDate + ", unNo=" + unNo + ", commodity="
				+ commodity + ", assesmentId=" + assesmentId + ", pnStatus=" + pnStatus + ", jobOrderId=" + jobOrderId
				+ ", jobDate=" + jobDate + ", area=" + area + ", cargoType=" + cargoType + ", weightTakenIn="
				+ weightTakenIn + ", rScanOut=" + rScanOut + ", outVehicleNo=" + outVehicleNo
				+ ", outTransporterStatus=" + outTransporterStatus + ", outTransporter=" + outTransporter
				+ ", outTransporterName=" + outTransporterName + ", outGateOutDate=" + outGateOutDate + ", inVehicleNo="
				+ inVehicleNo + ", inTransporterStatus=" + inTransporterStatus + ", inTransporter=" + inTransporter
				+ ", inTransporterName=" + inTransporterName + ", inGateInDate=" + inGateInDate + ", rScanIn=" + rScanIn
				+ ", hubStuffId=" + hubStuffId + ", scanningStatus=" + scanningStatus + ", odcStatus=" + odcStatus
				+ ", invoiceAssesed=" + invoiceAssesed + ", commodityCode=" + commodityCode + ", codeccoMtInStatus="
				+ codeccoMtInStatus + ", codeccoMtInDate=" + codeccoMtInDate + ", factoryCode=" + factoryCode
				+ ", bufferCode=" + bufferCode + ", lastLocationReceivedDate=" + lastLocationReceivedDate
				+ ", locationReceivedDate=" + locationReceivedDate + ", tagRemoveStatus=" + tagRemoveStatus
				+ ", tagRemoveDate=" + tagRemoveDate + ", rfTag=" + rfTag + ", tagReceiveStatus=" + tagReceiveStatus
				+ ", tagReceiveDate=" + tagReceiveDate + ", eqactivityFlg=" + eqactivityFlg + ", ediCusMenifestStatus="
				+ ediCusMenifestStatus + ", ediCusMenifestDate=" + ediCusMenifestDate + ", ediCusInDate=" + ediCusInDate
				+ ", ediCusInStatus=" + ediCusInStatus + ", lowBed=" + lowBed + ", vessel=" + vessel
				+ ", typeOfPackage=" + typeOfPackage + "]";
	}

	public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
			String docRefNo, String lineNo, int srNo, Date docRefDate, String profitcentreId, String viaNo,
			String containerNo, String containerSize, String containerType, String containerStatus,
			String containerSealNo, String customsSealNo, String actualSealNo, String vehicleType, String isoCode,
			BigDecimal eirGrossWeight, BigDecimal tareWeight, String hazardous, String hazClass, String sa, String sl,
			String portExitNo, Date portExitDate, String origin, String refer, String temperature,
			String containerHealth, String yardLocation, String yardBlock, String yardCell, String yardLocation1,
			String yardBlock1, String yardCell1, String transporterStatus, String transporterName, String vehicleNo,
			String driverName, String comments, String specialRemarks, String status, String createdBy,
			String approvedBy, String tripType, String drt, String scannerType, String holdStatus, String holdType,
			String holdUser, Date holdDate, String holdRemarks, String pnStatus, String jobOrderId, Date jobDate,
			Date inGateInDate, String scanningStatus, String odcStatus, String terminal, String vessel, String lowBed) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gateInId = gateInId;
		this.finYear = finYear;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.lineNo = lineNo;
		this.srNo = srNo;
		this.docRefDate = docRefDate;
		this.profitcentreId = profitcentreId;
		this.viaNo = viaNo;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.customsSealNo = customsSealNo;
		this.actualSealNo = actualSealNo;
		this.vehicleType = vehicleType;
		this.isoCode = isoCode;
		this.eirGrossWeight = eirGrossWeight;
		this.tareWeight = tareWeight;
		this.hazardous = hazardous;
		this.hazClass = hazClass;
		this.sa = sa;
		this.sl = sl;
		this.portExitNo = portExitNo;
		this.portExitDate = portExitDate;
		this.origin = origin;
		this.refer = refer;
		this.temperature = temperature;
		this.containerHealth = containerHealth;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.yardCell = yardCell;
		this.yardLocation1 = yardLocation1;
		this.yardBlock1 = yardBlock1;
		this.yardCell1 = yardCell1;
		this.transporterStatus = transporterStatus;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.specialRemarks = specialRemarks;
		this.status = status;
		this.createdBy = createdBy;
		this.approvedBy = approvedBy;
		this.tripType = tripType;
		this.drt = drt;
		this.scannerType = scannerType;
		this.holdStatus = holdStatus;
		this.holdType = holdType;
		this.holdUser = holdUser;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.pnStatus = pnStatus;
		this.jobOrderId = jobOrderId;
		this.jobDate = jobDate;
		this.inGateInDate = inGateInDate;
		this.scanningStatus = scanningStatus;
		this.odcStatus = odcStatus;
		this.terminal = terminal;
		this.vessel = vessel;
		this.lowBed = lowBed;
	}

	public GateIn(String gateInId, String erpDocRefNo, String docRefNo, String containerNo, String vehicleNo,
			String status, String createdBy) {
		super();
		this.gateInId = gateInId;
		this.erpDocRefNo = erpDocRefNo;
		this.docRefNo = docRefNo;
		this.containerNo = containerNo;
		this.vehicleNo = vehicleNo;
		this.status = status;
		this.createdBy = createdBy;
	}
	
	
	
	//search data 

		public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
				String docRefNo, String lineNo, int srNo, Date docRefDate, String boeNo, Date boeDate, String nocNo,
				Date nocDate, String gateInType, String profitcentreId, String containerNo, String containerSize,
				String containerType, String isoCode, BigDecimal grossWeight, BigDecimal eirGrossWeight, String cha,
				String importerName, String commodityDescription, BigDecimal actualNoOfPackages, BigDecimal qtyTakenIn,
				String shift, String transporterStatus, String transporterName, String transporter, String vehicleNo,
				String driverName, String status, String createdBy, String editedBy, String approvedBy, Date inGateInDate,BigDecimal weightTakenIn,String typeOfPackage) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.gateInId = gateInId;
			this.finYear = finYear;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.lineNo = lineNo;
			this.srNo = srNo;
			this.docRefDate = docRefDate;
			this.boeNo = boeNo;
			this.boeDate = boeDate;
			this.nocNo = nocNo;
			this.nocDate = nocDate;
			this.gateInType = gateInType;
			this.profitcentreId = profitcentreId;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.isoCode = isoCode;
			this.grossWeight = grossWeight;
			this.eirGrossWeight = eirGrossWeight;
			this.cha = cha;
			this.importerName = importerName;
			this.commodityDescription = commodityDescription;
			this.actualNoOfPackages = actualNoOfPackages;
			this.qtyTakenIn = qtyTakenIn;
			this.shift = shift;
			this.transporterStatus = transporterStatus;
			this.transporterName = transporterName;
			this.transporter = transporter;
			this.vehicleNo = vehicleNo;
			this.driverName = driverName;
			this.status = status;
			this.createdBy = createdBy;
			this.editedBy = editedBy;
			this.approvedBy = approvedBy;
			this.inGateInDate = inGateInDate;
			this.weightTakenIn = weightTakenIn;
			this.typeOfPackage=typeOfPackage;
		}
		
		
		public GateIn(String companyId, String branchId, String gateInId, BigDecimal qtyTakenIn,
				BigDecimal weightTakenIn) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.gateInId = gateInId;
			
			this.qtyTakenIn = qtyTakenIn;
			
			this.weightTakenIn = weightTakenIn;
		}
		
		
		//search data 

				public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
						String docRefNo, String lineNo, int srNo, Date docRefDate, String boeNo, Date boeDate, String nocNo,
						Date nocDate, String gateInType, String profitcentreId, String containerNo, String containerSize,
						String containerType, String isoCode, BigDecimal grossWeight, BigDecimal eirGrossWeight, String cha,
						String importerName, String commodityDescription, BigDecimal actualNoOfPackages, BigDecimal qtyTakenIn,
						String shift, String transporterStatus, String transporterName, String transporter, String vehicleNo,
						String driverName, String status, String createdBy, String editedBy, String approvedBy, Date inGateInDate,BigDecimal weightTakenIn,String typeOfPackage,String commodity) {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.gateInId = gateInId;
					this.finYear = finYear;
					this.erpDocRefNo = erpDocRefNo;
					this.docRefNo = docRefNo;
					this.lineNo = lineNo;
					this.srNo = srNo;
					this.docRefDate = docRefDate;
					this.boeNo = boeNo;
					this.boeDate = boeDate;
					this.nocNo = nocNo;
					this.nocDate = nocDate;
					this.gateInType = gateInType;
					this.profitcentreId = profitcentreId;
					this.containerNo = containerNo;
					this.containerSize = containerSize;
					this.containerType = containerType;
					this.isoCode = isoCode;
					this.grossWeight = grossWeight;
					this.eirGrossWeight = eirGrossWeight;
					this.cha = cha;
					this.importerName = importerName;
					this.commodityDescription = commodityDescription;
					this.actualNoOfPackages = actualNoOfPackages;
					this.qtyTakenIn = qtyTakenIn;
					this.shift = shift;
					this.transporterStatus = transporterStatus;
					this.transporterName = transporterName;
					this.transporter = transporter;
					this.vehicleNo = vehicleNo;
					this.driverName = driverName;
					this.status = status;
					this.createdBy = createdBy;
					this.editedBy = editedBy;
					this.approvedBy = approvedBy;
					this.inGateInDate = inGateInDate;
					this.weightTakenIn = weightTakenIn;
					this.typeOfPackage=typeOfPackage;
					this.commodity=commodity;
				}
				
				
//				Export Gate In
				public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
						String docRefNo, String lineNo, int srNo, Date docRefDate, String gateNo,
						BigDecimal gateInPackages, String gateInType, String profitcentreId, String processId,
						BigDecimal grossWeight, String onAccountOf, String cha, BigDecimal qtyTakenIn, String shift,
						String status, String createdBy, String approvedBy, String transporterStatus, String transporterName,
						String vehicleNo, String driverName, BigDecimal cargoWeight, String commodityDescription, BigDecimal actualNoOfPackages, Date inGateInDate) {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.gateInId = gateInId;
					this.finYear = finYear;
					this.erpDocRefNo = erpDocRefNo;
					this.docRefNo = docRefNo;
					this.lineNo = lineNo;
					this.srNo = srNo;
					this.docRefDate = docRefDate;
					this.gateNo = gateNo;
					this.fob = gateInPackages;
					this.gateInType = gateInType;
					this.profitcentreId = profitcentreId;
					this.processId = processId;
					this.grossWeight = grossWeight;
					this.onAccountOf = onAccountOf;
					this.cha = cha;
					this.qtyTakenIn = qtyTakenIn;
					this.shift = shift;
					this.status = status;
					this.createdBy = createdBy;
					this.approvedBy = approvedBy;
					this.transporterStatus = transporterStatus;
					this.transporterName = transporterName;
					this.vehicleNo = vehicleNo;
					this.driverName = driverName;
					this.cargoWeight = cargoWeight;
					this.commodityDescription = commodityDescription;
					this.actualNoOfPackages = actualNoOfPackages;
					this.inGateInDate = inGateInDate;
				}
				
				//Export Gate In
				public GateIn(String companyId, String branchId, String gateInId, String finYear, String erpDocRefNo,
						String docRefNo, String lineNo, int srNo, Date docRefDate, String gateNo,
						BigDecimal gateInPackages, String gateInType, String profitcentreId, String processId,
						BigDecimal grossWeight, String onAccountOf, String cha, BigDecimal qtyTakenIn, String shift,
						String status, String createdBy, String approvedBy, String transporterStatus, String transporterName,
						String vehicleNo, String driverName, BigDecimal cargoWeight, String commodityDescription, BigDecimal actualNoOfPackages, Date inGateInDate, String remarks) {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.gateInId = gateInId;
					this.finYear = finYear;
					this.erpDocRefNo = erpDocRefNo;
					this.docRefNo = docRefNo;
					this.lineNo = lineNo;
					this.srNo = srNo;
					this.docRefDate = docRefDate;
					this.gateNo = gateNo;
					this.fob = gateInPackages;
					this.gateInType = gateInType;
					this.profitcentreId = profitcentreId;
					this.processId = processId;
					this.grossWeight = grossWeight;
					this.onAccountOf = onAccountOf;
					this.cha = cha;
					this.qtyTakenIn = qtyTakenIn;
					this.shift = shift;
					this.status = status;
					this.createdBy = createdBy;
					this.approvedBy = approvedBy;
					this.transporterStatus = transporterStatus;
					this.transporterName = transporterName;
					this.vehicleNo = vehicleNo;
					this.driverName = driverName;
					this.cargoWeight = cargoWeight;
					this.commodityDescription = commodityDescription;
					this.actualNoOfPackages = actualNoOfPackages;
					this.inGateInDate = inGateInDate;
					this.remarks = remarks;
				}
				
				public GateIn(String gateInId, String docRefNo,
						BigDecimal qtyTakenIn, String vehicleNo,
						String cartingStatus, Date inGateInDate, String transporterName) {
					super();
					this.gateInId = gateInId;
					this.docRefNo = docRefNo;
					this.qtyTakenIn = qtyTakenIn;
					this.vehicleNo = vehicleNo;
					this.cartingStatus = cartingStatus;
					this.inGateInDate = inGateInDate;
					this.transporterName = transporterName;
				}
				
//				Common Search

public GateIn(String gateInId, String erpDocRefNo, String docRefNo, String cartingTransId, BigDecimal cartedPackages,
		String containerNo, BigDecimal actualNoOfPackages, BigDecimal qtyTakenIn, String portExitNo, String vehicleNo,
		String cartingStatus, String stuffTallyId, String prGatePassNo, String prGateOutId, String stuffTallyStatus, BigDecimal fob, BigDecimal cargoWeight, String profitcentreId) {
	super();
	this.gateInId = gateInId;
	this.erpDocRefNo = erpDocRefNo;
	this.docRefNo = docRefNo;
	this.cartingTransId = cartingTransId;
	this.cartedPackages = cartedPackages;
	this.containerNo = containerNo;
	this.actualNoOfPackages = actualNoOfPackages;
	this.qtyTakenIn = qtyTakenIn;
	this.portExitNo = portExitNo;
	this.vehicleNo = vehicleNo;
	this.cartingStatus = cartingStatus;
	this.stuffTallyId = stuffTallyId;
	this.prGatePassNo = prGatePassNo;
	this.prGateOutId = prGateOutId;
	this.stuffTallyStatus = stuffTallyStatus;
	this.fob = fob;
	this.cargoWeight = cargoWeight;
	this.profitcentreId = 	profitcentreId;
}




public GateIn(String gateInId, String erpDocRefNo, String docRefNo, String boeNo) {
	super();
	this.gateInId = gateInId;
	this.erpDocRefNo = erpDocRefNo;
	this.docRefNo = docRefNo;
	this.boeNo = boeNo;
}







public GateIn(String gateInId, String erpDocRefNo, String docRefNo, int srNo, String onAccountOf, String cha,
		String commodityDescription, BigDecimal actualNoOfPackages, BigDecimal qtyTakenIn, String vehicleNo,
		BigDecimal cargoWeight,BigDecimal fob, Date inGateInDate, Date docRefDate, BigDecimal grossWeight) {
	super();
	this.gateInId = gateInId;
	this.erpDocRefNo = erpDocRefNo;
	this.docRefNo = docRefNo;
	this.srNo = srNo;
	this.onAccountOf = onAccountOf;
	this.cha = cha;
	this.commodityDescription = commodityDescription;
	this.actualNoOfPackages = actualNoOfPackages;
	this.qtyTakenIn = qtyTakenIn;
	this.vehicleNo = vehicleNo;
	this.cargoWeight = cargoWeight;
	this.fob = fob;
	this.inGateInDate = inGateInDate;
	this.docRefDate = docRefDate;
	this.grossWeight = grossWeight;
	}


public GateIn(String gateInId, String docRefNo,
		String erpDocRefNo, String vehicleNo, BigDecimal grossWeight) {
	super();
	this.gateInId = gateInId;
	this.docRefNo = docRefNo;
	this.erpDocRefNo = erpDocRefNo;
	this.vehicleNo = vehicleNo;	
	this.grossWeight = grossWeight;
}




}