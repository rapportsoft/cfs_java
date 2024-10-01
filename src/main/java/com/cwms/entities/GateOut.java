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
@Table(name="cfgateout")
@IdClass(GateOutId.class)
public class GateOut {
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
	    @Column(name = "Gate_Out_Id", length = 10)
	    private String gateOutId;

	    @Id
	    @Column(name = "ERP_Doc_Ref_No", length = 10)
	    private String erpDocRefNo;

	    @Id
	    @Column(name = "doc_ref_No", length = 25)
	    private String docRefNo;

	    @Id
	    @Column(name = "Sr_No", length = 10)
	    private String srNo;

	    @Column(name = "Doc_Ref_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date docRefDate;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;

	    @Column(name = "Trans_Type", length = 5)
	    private String transType;

	    @Column(name = "DRT", length = 1)
	    private char drt = 'N';

	    @Column(name = "Gate_Out_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date gateOutDate;

	    @Column(name = "Process_Id", length = 10)
	    private String processId;

	    @Column(name = "Shift", length = 6)
	    private String shift;

	    @Column(name = "Gate_No_In", length = 6)
	    private String gateNoIn;
	    
	    @Column(name = "Gate_No_Out", length = 6)
	    private String gateNoOut;

	    @Column(name = "IGM_Line_No", length = 10)
	    private String igmLineNo;

	    @Column(name = "Invoice_No", length = 16)
	    private String invoiceNo;

	    @Column(name = "Invoice_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date invoiceDate;

	    @Column(name = "On_Account_Of", length = 6)
	    private String onAccountOf;

	    @Column(name = "VIA_NO", length = 10)
	    private String viaNo;

	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Container_Size", length = 6)
	    private String containerSize;

	    @Column(name = "Container_Type", length = 6)
	    private String containerType;

	    @Column(name = "Container_Status", length = 3)
	    private String containerStatus;

	    @Column(name = "Container_Health", length = 6)
	    private String containerHealth;

	    @Column(name = "IMO_Code", length = 10)
	    private String imoCode;

	    @Column(name = "Haz", length = 1)
	    private char haz;

	    @Column(name = "ISO_Code", length = 4)
	    private String isoCode;

	    @Column(name = "Refer", length = 1)
	    private char refer;

	    @Column(name = "SA", length = 6)
	    private String sa;

	    @Column(name = "SL", length = 6)
	    private String sl;

	    @Column(name = "CHA", length = 25)
	    private String cha;

	    @Column(name = "CHA_Name", length = 100)
	    private String chaName;

	    @Column(name = "Exporter_Name", length = 100)
	    private String exporterName;

	    @Column(name = "Importer_Name", length = 100)
	    private String importerName;

	    @Column(name = "Commodity_Description", length = 250)
	    private String commodityDescription;

	    @Column(name = "Gross_Wt", precision = 12, scale = 4)
	    private BigDecimal grossWt;

	    @Column(name = "NATURE_OF_CARGO", length = 10)
	    private String natureOfCargo;

	    @Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	    private BigDecimal actualNoOfPackages;

	    @Column(name = "Qty_Taken_Out", precision = 8, scale = 0)
	    private BigDecimal qtyTakenOut;

	    @Column(name = "VESSEL_ID", length = 10)
	    private String vesselId;

	    @Column(name = "Transporter_Status", length = 1)
	    private char transporterStatus;

	    @Column(name = "Transporter", length = 6)
	    private String transporter;

	    @Column(name = "Transporter_Name", length = 50)
	    private String transporterName;

	    @Column(name = "Vehicle_No", length = 15)
	    private String vehicleNo;

	    @Column(name = "Trip_type", length = 10)
	    private String tripType;

	    @Column(name = "Driver_Name", length = 50)
	    private String driverName;

	    @Column(name = "Delivery_Order_No", length = 25)
	    private String deliveryOrderNo;

	    @Column(name = "Delivery_Order_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date deliveryOrderDate;

	    @Column(name = "DO_Validity_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date doValidityDate;

	    @Column(name = "Gate_Pass_No", length = 10)
	    private String gatePassNo;

	    @Column(name = "Gate_Pass_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date gatePassDate;

	    @Column(name = "Location", length = 30)
	    private String location;

	    @Column(name = "Comments", length = 150)
	    private String comments;

	    @Column(name = "Gate_In_Type", length = 3)
	    private String gateInType;

	    @Column(name = "EIR_No", length = 10)
	    private String eirNo;

	    @Column(name = "EIR_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date eirDate;

	    @Column(name = "EIR_Status", length = 1)
	    private char eirStatus;

	    @Column(name = "EIR_Created_By", length = 10)
	    private String eirCreatedBy;

	    @Column(name = "EIR_Created_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date eirCreatedDate;

	    @Column(name = "EIR_Approved_By", length = 10)
	    private String eirApprovedBy;

	    @Column(name = "EIR_Approved_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date eirApprovedDate;

	    @Column(name = "EGM_No", length = 20)
	    private String egmNo;

	    @Column(name = "EGM_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date egmDate;

	    @Column(name = "Status", length = 1)
	    private char status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

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

	    @Column(name = "oth_party_Id", length = 10)
	    private String othPartyId;

	    @Column(name = "Weight_Taken_OUT", precision = 15, scale = 3)
	    private BigDecimal weightTakenOut;

	    @Column(name = "PR_Status", length = 1)
	    private char prStatus;

	    @Column(name = "CODECCO_MT_OUT_STATUS", length = 1)
	    private char codeccoMtOutStatus;

	    @Column(name = "CODECCO_MT_OUT_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date codeccoMtOutDate;

	    @Column(name = "Licence_No", length = 30)
	    private String licenceNo;

	    @Column(name = "Driver_Contact_No", length = 12)
	    private String driverContactNo;

	    @Column(name = "Out_Driver_Name", length = 50)
	    private String outDriverName;

	    @Column(name = "Customs_EXP_OUT_STATUS", length = 1)
	    private char customsExpOutStatus;

	    @Column(name = "Customs_EXP_OUT_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date customsExpOutDate;

	    @Column(name = "EMPTY_OUT_ID", length = 10)
	    private String emptyOutId;

	    @Column(name = "EMPTY_OUT_DATE")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date emptyOutDate;

	    @Column(name = "Vehical_Gate_In", length = 10)
	    private String vehicalGateIn;

	    @Column(name = "empty_pass_date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date emptyPassDate;

	    @Column(name = "Tag_Remove_Status", length = 1)
	    private char tagRemoveStatus;
	    
	    
	    @Column(name = "Tag_Remove_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date tagRemoveDate;

	    @Column(name = "Tally_Type", length = 20)
	    private String tallyType;

	    @Column(name = "Out_Seal_No", length = 20)
	    private String outSealNo;

	    @Column(name = "DRAFT_Bill_No", length = 10)
	    private String draftBillNo;

	    @Column(name = "DRAFT_Bill_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date draftBillDate;

	    @Column(name = "GSTIN", length = 15)
	    private String gstin;

	    @Column(name = "INTERNAL_MOVE", length = 1)
	    private char internalMove;

	    @Column(name = "DO_Assess_Status", length = 1)
	    private char doAssessStatus;

	    @Column(name = "DO_Assess_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date doAssessDate;

	    @Column(name = "No_Of_Trips", length = 4)
	    private String noOfTrips;

	    @Column(name = "Alternate_Contact_No", length = 12)
	    private String alternateContactNo;

	    @Column(name = "Trans_Type_Subtype", length = 10)
	    private String transTypeSubtype;

	    @Column(name = "Invoice_Type", length = 10)
	    private String invoiceType;

	    @Column(name = "Tally_Id", length = 15)
	    private String tallyId;

	    @Column(name = "Seal_Type", length = 10)
	    private String sealType;

	    @Column(name = "Export_Document", length = 10)
	    private String exportDocument;

	    @Column(name = "INVT_STATUS", length = 1)
	    private char invtStatus;

	    @Column(name = "Mark_For_Review", length = 1)
	    private char markForReview;

	    @Column(name = "Review_Comments", length = 250)
	    private String reviewComments;
	    
	    @Column(name = "Commodity", length = 10)
	    private String commodity;
	    
	    @Column(name = "ExBond_BE_No", length = 20)
	    private String exBondBeNo;
	    
	    @Column(name = "Vehicle_Id", length = 10)
		private String vehicleId;
	    
	    @Column(name="Tare_Wt",precision = 18,scale = 3)
		public BigDecimal tareWt;
		
	    @Column(name="Movement_Type",length = 10)
		public String movementType;
		
	    @Column(name = "From_Location", length = 50)
	    public String fromLocation = "";
	    
	    @Column(name="shipper",length = 6)
		public String shipper;
	    
	    @Column(name = "Gate_In_Date")
		@Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(shape = JsonFormat.Shape.NUMBER)
		public Date gateInDate; // Default value

		public GateOut() {
			super();
			// TODO Auto-generated constructor stub
		}

		
		
		

		public String getVehicleId() {
			return vehicleId;
		}





		public void setVehicleId(String vehicleId) {
			this.vehicleId = vehicleId;
		}





		public BigDecimal getTareWt() {
			return tareWt;
		}





		public void setTareWt(BigDecimal tareWt) {
			this.tareWt = tareWt;
		}





		public String getMovementType() {
			return movementType;
		}





		public void setMovementType(String movementType) {
			this.movementType = movementType;
		}





		public String getFromLocation() {
			return fromLocation;
		}





		public void setFromLocation(String fromLocation) {
			this.fromLocation = fromLocation;
		}





		public String getShipper() {
			return shipper;
		}





		public void setShipper(String shipper) {
			this.shipper = shipper;
		}





		public Date getGateInDate() {
			return gateInDate;
		}





		public void setGateInDate(Date gateInDate) {
			this.gateInDate = gateInDate;
		}





		public GateOut(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
				String docRefNo, String srNo, Date docRefDate, String profitcentreId, String transType, char drt,
				Date gateOutDate, String processId, String shift, String gateNoIn, String gateNoOut, String igmLineNo,
				String invoiceNo, Date invoiceDate, String onAccountOf, String viaNo, String containerNo,
				String containerSize, String containerType, String containerStatus, String containerHealth,
				String imoCode, char haz, String isoCode, char refer, String sa, String sl, String cha, String chaName,
				String exporterName, String importerName, String commodityDescription, BigDecimal grossWt,
				String natureOfCargo, BigDecimal actualNoOfPackages, BigDecimal qtyTakenOut, String vesselId,
				char transporterStatus, String transporter, String transporterName, String vehicleNo, String tripType,
				String driverName, String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate,
				String gatePassNo, Date gatePassDate, String location, String comments, String gateInType, String eirNo,
				Date eirDate, char eirStatus, String eirCreatedBy, Date eirCreatedDate, String eirApprovedBy,
				Date eirApprovedDate, String egmNo, Date egmDate, char status, String createdBy, Date createdDate,
				String editedBy, Date editedDate, String approvedBy, Date approvedDate, String othPartyId,
				BigDecimal weightTakenOut, char prStatus, char codeccoMtOutStatus, Date codeccoMtOutDate,
				String licenceNo, String driverContactNo, String outDriverName, char customsExpOutStatus,
				Date customsExpOutDate, String emptyOutId, Date emptyOutDate, String vehicalGateIn, Date emptyPassDate,
				char tagRemoveStatus, Date tagRemoveDate, String tallyType, String outSealNo, String draftBillNo,
				Date draftBillDate, String gstin, char internalMove, char doAssessStatus, Date doAssessDate,
				String noOfTrips, String alternateContactNo, String transTypeSubtype, String invoiceType,
				String tallyId, String sealType, String exportDocument, char invtStatus, char markForReview,
				String reviewComments, String commodity, String exBondBeNo, String vehicleId, BigDecimal tareWt,
				String movementType, String fromLocation, String shipper, Date gateInDate) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.gateOutId = gateOutId;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.srNo = srNo;
			this.docRefDate = docRefDate;
			this.profitcentreId = profitcentreId;
			this.transType = transType;
			this.drt = drt;
			this.gateOutDate = gateOutDate;
			this.processId = processId;
			this.shift = shift;
			this.gateNoIn = gateNoIn;
			this.gateNoOut = gateNoOut;
			this.igmLineNo = igmLineNo;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.onAccountOf = onAccountOf;
			this.viaNo = viaNo;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerStatus = containerStatus;
			this.containerHealth = containerHealth;
			this.imoCode = imoCode;
			this.haz = haz;
			this.isoCode = isoCode;
			this.refer = refer;
			this.sa = sa;
			this.sl = sl;
			this.cha = cha;
			this.chaName = chaName;
			this.exporterName = exporterName;
			this.importerName = importerName;
			this.commodityDescription = commodityDescription;
			this.grossWt = grossWt;
			this.natureOfCargo = natureOfCargo;
			this.actualNoOfPackages = actualNoOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.vesselId = vesselId;
			this.transporterStatus = transporterStatus;
			this.transporter = transporter;
			this.transporterName = transporterName;
			this.vehicleNo = vehicleNo;
			this.tripType = tripType;
			this.driverName = driverName;
			this.deliveryOrderNo = deliveryOrderNo;
			this.deliveryOrderDate = deliveryOrderDate;
			this.doValidityDate = doValidityDate;
			this.gatePassNo = gatePassNo;
			this.gatePassDate = gatePassDate;
			this.location = location;
			this.comments = comments;
			this.gateInType = gateInType;
			this.eirNo = eirNo;
			this.eirDate = eirDate;
			this.eirStatus = eirStatus;
			this.eirCreatedBy = eirCreatedBy;
			this.eirCreatedDate = eirCreatedDate;
			this.eirApprovedBy = eirApprovedBy;
			this.eirApprovedDate = eirApprovedDate;
			this.egmNo = egmNo;
			this.egmDate = egmDate;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.othPartyId = othPartyId;
			this.weightTakenOut = weightTakenOut;
			this.prStatus = prStatus;
			this.codeccoMtOutStatus = codeccoMtOutStatus;
			this.codeccoMtOutDate = codeccoMtOutDate;
			this.licenceNo = licenceNo;
			this.driverContactNo = driverContactNo;
			this.outDriverName = outDriverName;
			this.customsExpOutStatus = customsExpOutStatus;
			this.customsExpOutDate = customsExpOutDate;
			this.emptyOutId = emptyOutId;
			this.emptyOutDate = emptyOutDate;
			this.vehicalGateIn = vehicalGateIn;
			this.emptyPassDate = emptyPassDate;
			this.tagRemoveStatus = tagRemoveStatus;
			this.tagRemoveDate = tagRemoveDate;
			this.tallyType = tallyType;
			this.outSealNo = outSealNo;
			this.draftBillNo = draftBillNo;
			this.draftBillDate = draftBillDate;
			this.gstin = gstin;
			this.internalMove = internalMove;
			this.doAssessStatus = doAssessStatus;
			this.doAssessDate = doAssessDate;
			this.noOfTrips = noOfTrips;
			this.alternateContactNo = alternateContactNo;
			this.transTypeSubtype = transTypeSubtype;
			this.invoiceType = invoiceType;
			this.tallyId = tallyId;
			this.sealType = sealType;
			this.exportDocument = exportDocument;
			this.invtStatus = invtStatus;
			this.markForReview = markForReview;
			this.reviewComments = reviewComments;
			this.commodity = commodity;
			this.exBondBeNo = exBondBeNo;
			this.vehicleId = vehicleId;
			this.tareWt = tareWt;
			this.movementType = movementType;
			this.fromLocation = fromLocation;
			this.shipper = shipper;
			this.gateInDate = gateInDate;
		}





		public GateOut(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
				String docRefNo, String srNo, Date docRefDate, String profitcentreId, String transType, char drt,
				Date gateOutDate, String processId, String shift, String gateNoIn, String gateNoOut, String igmLineNo,
				String invoiceNo, Date invoiceDate, String onAccountOf, String viaNo, String containerNo,
				String containerSize, String containerType, String containerStatus, String containerHealth,
				String imoCode, char haz, String isoCode, char refer, String sa, String sl, String cha, String chaName,
				String exporterName, String importerName, String commodityDescription, BigDecimal grossWt,
				String natureOfCargo, BigDecimal actualNoOfPackages, BigDecimal qtyTakenOut, String vesselId,
				char transporterStatus, String transporter, String transporterName, String vehicleNo, String tripType,
				String driverName, String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate,
				String gatePassNo, Date gatePassDate, String location, String comments, String gateInType, String eirNo,
				Date eirDate, char eirStatus, String eirCreatedBy, Date eirCreatedDate, String eirApprovedBy,
				Date eirApprovedDate, String egmNo, Date egmDate, char status, String createdBy, Date createdDate,
				String editedBy, Date editedDate, String approvedBy, Date approvedDate, String othPartyId,
				BigDecimal weightTakenOut, char prStatus, char codeccoMtOutStatus, Date codeccoMtOutDate,
				String licenceNo, String driverContactNo, String outDriverName, char customsExpOutStatus,
				Date customsExpOutDate, String emptyOutId, Date emptyOutDate, String vehicalGateIn, Date emptyPassDate,
				char tagRemoveStatus, Date tagRemoveDate, String tallyType, String outSealNo, String draftBillNo,
				Date draftBillDate, String gstin, char internalMove, char doAssessStatus, Date doAssessDate,
				String noOfTrips, String alternateContactNo, String transTypeSubtype, String invoiceType,
				String tallyId, String sealType, String commodity, String exBondBeNo, String exportDocument,
				char invtStatus, char markForReview, String reviewComments) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.gateOutId = gateOutId;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.srNo = srNo;
			this.docRefDate = docRefDate;
			this.profitcentreId = profitcentreId;
			this.transType = transType;
			this.drt = drt;
			this.gateOutDate = gateOutDate;
			this.processId = processId;
			this.shift = shift;
			this.gateNoIn = gateNoIn;
			this.gateNoOut = gateNoOut;
			this.igmLineNo = igmLineNo;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.onAccountOf = onAccountOf;
			this.viaNo = viaNo;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.containerStatus = containerStatus;
			this.containerHealth = containerHealth;
			this.imoCode = imoCode;
			this.haz = haz;
			this.isoCode = isoCode;
			this.refer = refer;
			this.sa = sa;
			this.sl = sl;
			this.cha = cha;
			this.chaName = chaName;
			this.exporterName = exporterName;
			this.importerName = importerName;
			this.commodityDescription = commodityDescription;
			this.grossWt = grossWt;
			this.natureOfCargo = natureOfCargo;
			this.actualNoOfPackages = actualNoOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.vesselId = vesselId;
			this.transporterStatus = transporterStatus;
			this.transporter = transporter;
			this.transporterName = transporterName;
			this.vehicleNo = vehicleNo;
			this.tripType = tripType;
			this.driverName = driverName;
			this.deliveryOrderNo = deliveryOrderNo;
			this.deliveryOrderDate = deliveryOrderDate;
			this.doValidityDate = doValidityDate;
			this.gatePassNo = gatePassNo;
			this.gatePassDate = gatePassDate;
			this.location = location;
			this.comments = comments;
			this.gateInType = gateInType;
			this.eirNo = eirNo;
			this.eirDate = eirDate;
			this.eirStatus = eirStatus;
			this.eirCreatedBy = eirCreatedBy;
			this.eirCreatedDate = eirCreatedDate;
			this.eirApprovedBy = eirApprovedBy;
			this.eirApprovedDate = eirApprovedDate;
			this.egmNo = egmNo;
			this.egmDate = egmDate;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.othPartyId = othPartyId;
			this.weightTakenOut = weightTakenOut;
			this.prStatus = prStatus;
			this.codeccoMtOutStatus = codeccoMtOutStatus;
			this.codeccoMtOutDate = codeccoMtOutDate;
			this.licenceNo = licenceNo;
			this.driverContactNo = driverContactNo;
			this.outDriverName = outDriverName;
			this.customsExpOutStatus = customsExpOutStatus;
			this.customsExpOutDate = customsExpOutDate;
			this.emptyOutId = emptyOutId;
			this.emptyOutDate = emptyOutDate;
			this.vehicalGateIn = vehicalGateIn;
			this.emptyPassDate = emptyPassDate;
			this.tagRemoveStatus = tagRemoveStatus;
			this.tagRemoveDate = tagRemoveDate;
			this.tallyType = tallyType;
			this.outSealNo = outSealNo;
			this.draftBillNo = draftBillNo;
			this.draftBillDate = draftBillDate;
			this.gstin = gstin;
			this.internalMove = internalMove;
			this.doAssessStatus = doAssessStatus;
			this.doAssessDate = doAssessDate;
			this.noOfTrips = noOfTrips;
			this.alternateContactNo = alternateContactNo;
			this.transTypeSubtype = transTypeSubtype;
			this.invoiceType = invoiceType;
			this.tallyId = tallyId;
			this.sealType = sealType;
			this.commodity = commodity;
			this.exBondBeNo = exBondBeNo;
			this.exportDocument = exportDocument;
			this.invtStatus = invtStatus;
			this.markForReview = markForReview;
			this.reviewComments = reviewComments;
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

		public String getGateOutId() {
			return gateOutId;
		}

		public void setGateOutId(String gateOutId) {
			this.gateOutId = gateOutId;
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

		public String getSrNo() {
			return srNo;
		}

		public void setSrNo(String srNo) {
			this.srNo = srNo;
		}

		public Date getDocRefDate() {
			return docRefDate;
		}

		public void setDocRefDate(Date docRefDate) {
			this.docRefDate = docRefDate;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
		}

		public char getDrt() {
			return drt;
		}

		public void setDrt(char drt) {
			this.drt = drt;
		}

		public Date getGateOutDate() {
			return gateOutDate;
		}

		public void setGateOutDate(Date gateOutDate) {
			this.gateOutDate = gateOutDate;
		}

		public String getProcessId() {
			return processId;
		}

		public void setProcessId(String processId) {
			this.processId = processId;
		}

		public String getShift() {
			return shift;
		}

		public void setShift(String shift) {
			this.shift = shift;
		}

		

		public String getGateNoIn() {
			return gateNoIn;
		}



		public void setGateNoIn(String gateNoIn) {
			this.gateNoIn = gateNoIn;
		}



		public String getGateNoOut() {
			return gateNoOut;
		}



		public void setGateNoOut(String gateNoOut) {
			this.gateNoOut = gateNoOut;
		}



		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
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

		public String getOnAccountOf() {
			return onAccountOf;
		}

		public void setOnAccountOf(String onAccountOf) {
			this.onAccountOf = onAccountOf;
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

		public String getContainerHealth() {
			return containerHealth;
		}

		public void setContainerHealth(String containerHealth) {
			this.containerHealth = containerHealth;
		}

		public String getImoCode() {
			return imoCode;
		}

		public void setImoCode(String imoCode) {
			this.imoCode = imoCode;
		}

		public char getHaz() {
			return haz;
		}

		public void setHaz(char haz) {
			this.haz = haz;
		}

		public String getIsoCode() {
			return isoCode;
		}

		public void setIsoCode(String isoCode) {
			this.isoCode = isoCode;
		}

		public char getRefer() {
			return refer;
		}

		public void setRefer(char refer) {
			this.refer = refer;
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

		public String getExporterName() {
			return exporterName;
		}

		public void setExporterName(String exporterName) {
			this.exporterName = exporterName;
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

		public BigDecimal getGrossWt() {
			return grossWt;
		}

		public void setGrossWt(BigDecimal grossWt) {
			this.grossWt = grossWt;
		}

		public String getNatureOfCargo() {
			return natureOfCargo;
		}

		public void setNatureOfCargo(String natureOfCargo) {
			this.natureOfCargo = natureOfCargo;
		}

		public BigDecimal getActualNoOfPackages() {
			return actualNoOfPackages;
		}

		public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
			this.actualNoOfPackages = actualNoOfPackages;
		}

		public BigDecimal getQtyTakenOut() {
			return qtyTakenOut;
		}

		public void setQtyTakenOut(BigDecimal qtyTakenOut) {
			this.qtyTakenOut = qtyTakenOut;
		}

		public String getVesselId() {
			return vesselId;
		}

		public void setVesselId(String vesselId) {
			this.vesselId = vesselId;
		}

		public char getTransporterStatus() {
			return transporterStatus;
		}

		public void setTransporterStatus(char transporterStatus) {
			this.transporterStatus = transporterStatus;
		}

		public String getTransporter() {
			return transporter;
		}

		public void setTransporter(String transporter) {
			this.transporter = transporter;
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

		public String getTripType() {
			return tripType;
		}

		public void setTripType(String tripType) {
			this.tripType = tripType;
		}

		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
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

		public String getGatePassNo() {
			return gatePassNo;
		}

		public void setGatePassNo(String gatePassNo) {
			this.gatePassNo = gatePassNo;
		}

		public Date getGatePassDate() {
			return gatePassDate;
		}

		public void setGatePassDate(Date gatePassDate) {
			this.gatePassDate = gatePassDate;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getGateInType() {
			return gateInType;
		}

		public void setGateInType(String gateInType) {
			this.gateInType = gateInType;
		}

		public String getEirNo() {
			return eirNo;
		}

		public void setEirNo(String eirNo) {
			this.eirNo = eirNo;
		}

		public Date getEirDate() {
			return eirDate;
		}

		public void setEirDate(Date eirDate) {
			this.eirDate = eirDate;
		}

		public char getEirStatus() {
			return eirStatus;
		}

		public void setEirStatus(char eirStatus) {
			this.eirStatus = eirStatus;
		}

		public String getEirCreatedBy() {
			return eirCreatedBy;
		}

		public void setEirCreatedBy(String eirCreatedBy) {
			this.eirCreatedBy = eirCreatedBy;
		}

		public Date getEirCreatedDate() {
			return eirCreatedDate;
		}

		public void setEirCreatedDate(Date eirCreatedDate) {
			this.eirCreatedDate = eirCreatedDate;
		}

		public String getEirApprovedBy() {
			return eirApprovedBy;
		}

		public void setEirApprovedBy(String eirApprovedBy) {
			this.eirApprovedBy = eirApprovedBy;
		}

		public Date getEirApprovedDate() {
			return eirApprovedDate;
		}

		public void setEirApprovedDate(Date eirApprovedDate) {
			this.eirApprovedDate = eirApprovedDate;
		}

		public String getEgmNo() {
			return egmNo;
		}

		public void setEgmNo(String egmNo) {
			this.egmNo = egmNo;
		}

		public Date getEgmDate() {
			return egmDate;
		}

		public void setEgmDate(Date egmDate) {
			this.egmDate = egmDate;
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

		public String getOthPartyId() {
			return othPartyId;
		}

		public void setOthPartyId(String othPartyId) {
			this.othPartyId = othPartyId;
		}

		public BigDecimal getWeightTakenOut() {
			return weightTakenOut;
		}

		public void setWeightTakenOut(BigDecimal weightTakenOut) {
			this.weightTakenOut = weightTakenOut;
		}

		public char getPrStatus() {
			return prStatus;
		}

		public void setPrStatus(char prStatus) {
			this.prStatus = prStatus;
		}

		public char getCodeccoMtOutStatus() {
			return codeccoMtOutStatus;
		}

		public void setCodeccoMtOutStatus(char codeccoMtOutStatus) {
			this.codeccoMtOutStatus = codeccoMtOutStatus;
		}

		public Date getCodeccoMtOutDate() {
			return codeccoMtOutDate;
		}

		public void setCodeccoMtOutDate(Date codeccoMtOutDate) {
			this.codeccoMtOutDate = codeccoMtOutDate;
		}

		public String getLicenceNo() {
			return licenceNo;
		}

		public void setLicenceNo(String licenceNo) {
			this.licenceNo = licenceNo;
		}

		public String getDriverContactNo() {
			return driverContactNo;
		}

		public void setDriverContactNo(String driverContactNo) {
			this.driverContactNo = driverContactNo;
		}

		public String getOutDriverName() {
			return outDriverName;
		}

		public void setOutDriverName(String outDriverName) {
			this.outDriverName = outDriverName;
		}

		public char getCustomsExpOutStatus() {
			return customsExpOutStatus;
		}

		public void setCustomsExpOutStatus(char customsExpOutStatus) {
			this.customsExpOutStatus = customsExpOutStatus;
		}

		public Date getCustomsExpOutDate() {
			return customsExpOutDate;
		}

		public void setCustomsExpOutDate(Date customsExpOutDate) {
			this.customsExpOutDate = customsExpOutDate;
		}

		public String getEmptyOutId() {
			return emptyOutId;
		}

		public void setEmptyOutId(String emptyOutId) {
			this.emptyOutId = emptyOutId;
		}

		public Date getEmptyOutDate() {
			return emptyOutDate;
		}

		public void setEmptyOutDate(Date emptyOutDate) {
			this.emptyOutDate = emptyOutDate;
		}

		public String getVehicalGateIn() {
			return vehicalGateIn;
		}

		public void setVehicalGateIn(String vehicalGateIn) {
			this.vehicalGateIn = vehicalGateIn;
		}

		public Date getEmptyPassDate() {
			return emptyPassDate;
		}

		public void setEmptyPassDate(Date emptyPassDate) {
			this.emptyPassDate = emptyPassDate;
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

		public String getTallyType() {
			return tallyType;
		}

		public void setTallyType(String tallyType) {
			this.tallyType = tallyType;
		}

		public String getOutSealNo() {
			return outSealNo;
		}

		public void setOutSealNo(String outSealNo) {
			this.outSealNo = outSealNo;
		}

		public String getDraftBillNo() {
			return draftBillNo;
		}

		public void setDraftBillNo(String draftBillNo) {
			this.draftBillNo = draftBillNo;
		}

		public Date getDraftBillDate() {
			return draftBillDate;
		}

		public void setDraftBillDate(Date draftBillDate) {
			this.draftBillDate = draftBillDate;
		}

		public String getGstin() {
			return gstin;
		}

		public void setGstin(String gstin) {
			this.gstin = gstin;
		}

		public char getInternalMove() {
			return internalMove;
		}

		public void setInternalMove(char internalMove) {
			this.internalMove = internalMove;
		}

		public char getDoAssessStatus() {
			return doAssessStatus;
		}

		public void setDoAssessStatus(char doAssessStatus) {
			this.doAssessStatus = doAssessStatus;
		}

		public Date getDoAssessDate() {
			return doAssessDate;
		}

		public void setDoAssessDate(Date doAssessDate) {
			this.doAssessDate = doAssessDate;
		}

		public String getNoOfTrips() {
			return noOfTrips;
		}

		public void setNoOfTrips(String noOfTrips) {
			this.noOfTrips = noOfTrips;
		}

		public String getAlternateContactNo() {
			return alternateContactNo;
		}

		public void setAlternateContactNo(String alternateContactNo) {
			this.alternateContactNo = alternateContactNo;
		}

		public String getTransTypeSubtype() {
			return transTypeSubtype;
		}

		public void setTransTypeSubtype(String transTypeSubtype) {
			this.transTypeSubtype = transTypeSubtype;
		}

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getTallyId() {
			return tallyId;
		}

		public void setTallyId(String tallyId) {
			this.tallyId = tallyId;
		}

		public String getSealType() {
			return sealType;
		}

		public void setSealType(String sealType) {
			this.sealType = sealType;
		}

		public String getExportDocument() {
			return exportDocument;
		}

		public void setExportDocument(String exportDocument) {
			this.exportDocument = exportDocument;
		}

		public char getInvtStatus() {
			return invtStatus;
		}

		public void setInvtStatus(char invtStatus) {
			this.invtStatus = invtStatus;
		}

		public char getMarkForReview() {
			return markForReview;
		}

		public void setMarkForReview(char markForReview) {
			this.markForReview = markForReview;
		}

		public String getReviewComments() {
			return reviewComments;
		}

		public void setReviewComments(String reviewComments) {
			this.reviewComments = reviewComments;
		}

		public String getCommodity() {
			return commodity;
		}





		public void setCommodity(String commodity) {
			this.commodity = commodity;
		}

		public String getExBondBeNo() {
			return exBondBeNo;
		}





		public void setExBondBeNo(String exBondBeNo) {
			this.exBondBeNo = exBondBeNo;
		}


		public GateOut(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
				String srNo, String profitcentreId, Date gateOutDate, String processId, String shift, String gateNoIn,
				String gateNoOut, char transporterStatus, String transporter, String transporterName, String vehicleNo,
				String tripType, String driverName,char status, String createdBy, Date createdDate, String editedBy,
				Date editedDate, String approvedBy, Date approvedDate,String comments ) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.gateOutId = gateOutId;
			this.erpDocRefNo = erpDocRefNo;
			this.srNo = srNo;
			this.profitcentreId = profitcentreId;
			this.gateOutDate = gateOutDate;
			this.processId = processId;
			this.shift = shift;
			this.gateNoIn = gateNoIn;
			this.gateNoOut = gateNoOut;
			this.transporterStatus = transporterStatus;
			this.transporter = transporter;
			this.transporterName = transporterName;
			this.vehicleNo = vehicleNo;
			this.tripType = tripType;
			this.driverName = driverName;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.comments = comments;
		}
	    
		
		public GateOut(String gateOutId, Date createdDate) {
			super();
			this.gateOutId = gateOutId;
			this.createdDate = createdDate;
		}
		

		// query constructor for getting gate out data 
				
				public GateOut(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo,
						String docRefNo, String srNo, Date gateOutDate, String shift, String gatePassNo,
						String commodityDescription, BigDecimal grossWt, String vehicleNo, String driverName,
						String deliveryOrderNo, Date deliveryOrderDate, Date doValidityDate, String exBondBeNo,String comments,char transporterStatus,String transporter,
						String transporterName,String tripType,Date gatePassDate,String gateNoIn,String createdBy,String approvedBy,char status,String profitcentreId,String driverContactNo,String licenceNo)  {
					super();
					this.companyId = companyId;
					this.branchId = branchId;
					this.finYear = finYear;
					this.gateOutId = gateOutId;
					this.erpDocRefNo = erpDocRefNo;
					this.docRefNo = docRefNo;
					this.srNo = srNo;
					this.gateOutDate = gateOutDate;
					this.shift = shift;
					this.gatePassNo = gatePassNo;
					this.commodityDescription = commodityDescription;
					this.grossWt = grossWt;
					this.vehicleNo = vehicleNo;
					this.driverName = driverName;
					this.deliveryOrderNo = deliveryOrderNo;
					this.deliveryOrderDate = deliveryOrderDate;
					this.doValidityDate = doValidityDate;
					this.exBondBeNo = exBondBeNo;
					this.comments = comments;
					this.transporterStatus = transporterStatus;
					this.transporter = transporter;
					this.transporterName = transporterName;
					this.tripType = tripType;
					this.gatePassDate = gatePassDate;
					this.gateNoIn = gateNoIn;
					this.createdBy = createdBy;
					this.approvedBy = approvedBy;
					this.status=status;
					this.profitcentreId=profitcentreId;
					this.driverContactNo=driverContactNo;
					this.licenceNo=licenceNo;
				}





		public GateOut(String companyId, String branchId, String finYear, String gateOutId, String erpDocRefNo, String docRefNo,
				String srNo, String igmLineNo, BigDecimal grossWt, String natureOfCargo, BigDecimal actualNoOfPackages,
				BigDecimal qtyTakenOut, String gatePassNo,String commodityDescription) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.gateOutId = gateOutId;
			this.erpDocRefNo = erpDocRefNo;
			this.docRefNo = docRefNo;
			this.srNo = srNo;
			this.igmLineNo = igmLineNo;
			this.grossWt = grossWt;
			this.natureOfCargo = natureOfCargo;
			this.actualNoOfPackages = actualNoOfPackages;
			this.qtyTakenOut = qtyTakenOut;
			this.gatePassNo = gatePassNo;
			this.commodityDescription = commodityDescription;
		}
	    
}
