package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name="cfexportgatepass")
@IdClass(ExportGatePassId.class)
public class ExportGatePass {
	
	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="Gate_Pass_Id",length = 10)
	private String gatePassId;
	
	@Id
	@Column(name="Profitcentre_Id",length = 10)
	private String profitcentreId;
	
	@Id
	@Column(name="Sr_No")
	private int srNo;
	
	@Column(name="Sb_Trans_Id",length = 10)
	private String sbTransId;
	
	@Column(name="Sb_Line_No",length = 5)
	private String sbLineNo;
	
	@Column(name="Carting_Trans_Id",length = 10)
	private String cartingTransId;
	
	@Column(name="Carting_Line_Id",length = 4)
	private String cartingLineId;
	
	  @Column(name = "Cargo_Exam_Id", length = 10)
	    private String cargoExamId;

	    @Column(name = "Cargo_Line_Id", length = 4)
	    private String cargoLineId;

	    @Column(name = "Stuff_Tally_Id", length = 100)
	    private String stuffTallyId;

	    @Column(name = "Stuff_Tally_Line_Id", precision = 8, scale = 0)
	    private BigDecimal stuffTallyLineId;

	    @Column(name = "MOV_REQ_TYPE", length = 15)
	    private String movReqType;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Gate_Pass_Date")
	    private Date gatePassDate;

	    @Column(name = "Movement_Req_Id", length = 10)
	    private String movementReqId;

	    @Column(name = "Trans_Type", length = 10)
	    private String transType;

	    @Column(name = "SB_No", length = 15)
	    private String sbNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SB_Date")
	    private Date sbDate;

	    @Column(name = "Shift", length = 6)
	    private String shift;

	    @Column(name = "Trip_Type", length = 10)
	    private String tripType;

	    @Column(name = "Vehicle_No", length = 15)
	    private String vehicleNo;
	    
	    @Column(name = "Vehicle_Id", length = 10)
	    private String vehicleId;

	    @Column(name = "Veh_Status", length = 3)
	    private String vehStatus;

	    @Column(name = "Invoice_No", length = 16)
	    private String invoiceNo;

	    @Column(name = "POL", length = 100)
	    private String pol;

	    @Column(name = "Importer_Name", length = 100)
	    private String importerName;

	    @Column(name = "Importer_Address1", length = 250)
	    private String importerAddress1;

	    @Column(name = "Importer_Address2", length = 100)
	    private String importerAddress2;

	    @Column(name = "Importer_Address3", length = 60)
	    private String importerAddress3;

	    @Column(name = "CHA", length = 6)
	    private String cha;

	    @Column(name = "Commodity", length = 250)
	    private String commodity;

	    @Column(name = "Examiner", length = 250)
	    private String examiner;

	    @Lob
	    @Column(name = "Number_Of_Marks")
	    private String numberOfMarks;

	    @Column(name = "Driver_Name", length = 35)
	    private String driverName;

	    @Column(name = "Transporter_Name", length = 100)
	    private String transporterName;

	    @Column(name = "Transporter", length = 6)
	    private String transporter;

	    @Column(name = "Transporter_Status", length = 1)
	    private String transporterStatus;

	    @Column(name = "Customs_Seal_No", length = 15)
	    private String customsSealNo;

	    @Column(name = "Agent_Seal_No", length = 15)
	    private String agentSealNo;

	    @Column(name = "Hauller", length = 50)
	    private String hauller;

	    @Column(name = "SL", length = 6)
	    private String sl;

	    @Column(name = "Vessel_Id", length = 7)
	    private String vesselId;

	    @Column(name = "Back_To_Town_Packages", precision = 8, scale = 0)
	    private BigDecimal backToTownPackages;

	    @Column(name = "Gate_Out_Packages", precision = 8, scale = 0)
	    private BigDecimal gateOutPackages;

	    @Column(name = "Sample_Qty", precision = 8, scale = 0)
	    private BigDecimal sampleQty;

	    @Column(name = "ISO_Code", length = 6)
	    private String isoCode;

	    @Column(name = "POD", length = 40)
	    private String pod;


	    @Column(name = "Location", length = 30)
	    private String location;

	    @Column(name = "Cell_Area_Allocated", precision = 18, scale = 3)
	    private BigDecimal cellAreaAllocated;

	    @Column(name = "QTY_TAKEN_OUT", precision = 8, scale = 0)
	    private BigDecimal qtyTakenOut;

	    @Column(name = "Area_Released", precision = 8, scale = 3)
	    private BigDecimal areaReleased;

	    @Column(name = "GW_Taken_Out", precision = 8, scale = 3)
	    private BigDecimal gwTakenOut;

	    @Column(name = "Yard_Packages", precision = 18, scale = 3)
	    private BigDecimal yardPackages;

	    @Column(name = "VIA_No", length = 10)
	    private String viaNo;

	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Container_Size", length = 6)
	    private String containerSize;

	    @Column(name = "Container_Type", length = 6)
	    private String containerType;

	    @Column(name = "Gross_Wt", precision = 15, scale = 3)
	    private BigDecimal grossWt;

	    @Column(name = "Vehicle_Wt", precision = 16, scale = 3)
	    private BigDecimal vehicleWt;

	    @Column(name = "Seal_No", length = 20)
	    private String sealNo;

	    @Column(name = "Container_Status", length = 3)
	    private String containerStatus;

	    @Column(name = "Delivery_Order_No", length = 15)
	    private String deliveryOrderNo;

	    @Column(name = "Haz", length = 1)
	    private String haz;

	    @Column(name = "Gate_Out_Id", length = 10)
	    private String gateOutId;

	    @Column(name = "Temperature", length = 10)
	    private String temperature;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Departure_Time")
	    private Date departureTime;

	    @Column(name = "Comments", length = 150)
	    private String comments;

	    @Column(name = "Damage_Code", length = 10)
	    private String damageCode;

	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Created_Date")
	    private Date createdDate;

	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Edited_Date")
	    private Date editedDate;
	    
	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Approved_Date")
	    private Date approvedDate;
	    
	    @Column(name="SA",length = 7)
	    private String sa;
	    
	    
	    @Column(name="Voyage_No",length = 7)
	    private String voyageNo;
	    
	    @Transient
	    private String chaName;


		public ExportGatePass() {
			super();
			// TODO Auto-generated constructor stub
		}


	


		public String getChaName() {
			return chaName;
		}





		public void setChaName(String chaName) {
			this.chaName = chaName;
		}





		public String getVehicleId() {
			return vehicleId;
		}





		public void setVehicleId(String vehicleId) {
			this.vehicleId = vehicleId;
		}





	



		public ExportGatePass(String companyId, String branchId, String gatePassId, String profitcentreId, int srNo,
				String sbTransId, String sbLineNo, String cartingTransId, String cartingLineId, String cargoExamId,
				String cargoLineId, String stuffTallyId, BigDecimal stuffTallyLineId, String movReqType,
				Date gatePassDate, String movementReqId, String transType, String sbNo, Date sbDate, String shift,
				String tripType, String vehicleNo, String vehicleId, String vehStatus, String invoiceNo, String pol,
				String importerName, String importerAddress1, String importerAddress2, String importerAddress3,
				String cha, String commodity, String examiner, String numberOfMarks, String driverName,
				String transporterName, String transporter, String transporterStatus, String customsSealNo,
				String agentSealNo, String hauller, String sl, String vesselId, BigDecimal backToTownPackages,
				BigDecimal gateOutPackages, BigDecimal sampleQty, String isoCode, String pod, String location,
				BigDecimal cellAreaAllocated, BigDecimal qtyTakenOut, BigDecimal areaReleased, BigDecimal gwTakenOut,
				BigDecimal yardPackages, String viaNo, String containerNo, String containerSize, String containerType,
				BigDecimal grossWt, BigDecimal vehicleWt, String sealNo, String containerStatus, String deliveryOrderNo,
				String haz, String gateOutId, String temperature, Date departureTime, String comments,
				String damageCode, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
				String approvedBy, Date approvedDate, String sa, String voyageNo, String chaName) {
			this.companyId = companyId;
			this.branchId = branchId;
			this.gatePassId = gatePassId;
			this.profitcentreId = profitcentreId;
			this.srNo = srNo;
			this.sbTransId = sbTransId;
			this.sbLineNo = sbLineNo;
			this.cartingTransId = cartingTransId;
			this.cartingLineId = cartingLineId;
			this.cargoExamId = cargoExamId;
			this.cargoLineId = cargoLineId;
			this.stuffTallyId = stuffTallyId;
			this.stuffTallyLineId = stuffTallyLineId;
			this.movReqType = movReqType;
			this.gatePassDate = gatePassDate;
			this.movementReqId = movementReqId;
			this.transType = transType;
			this.sbNo = sbNo;
			this.sbDate = sbDate;
			this.shift = shift;
			this.tripType = tripType;
			this.vehicleNo = vehicleNo;
			this.vehicleId = vehicleId;
			this.vehStatus = vehStatus;
			this.invoiceNo = invoiceNo;
			this.pol = pol;
			this.importerName = importerName;
			this.importerAddress1 = importerAddress1;
			this.importerAddress2 = importerAddress2;
			this.importerAddress3 = importerAddress3;
			this.cha = cha;
			this.commodity = commodity;
			this.examiner = examiner;
			this.numberOfMarks = numberOfMarks;
			this.driverName = driverName;
			this.transporterName = transporterName;
			this.transporter = transporter;
			this.transporterStatus = transporterStatus;
			this.customsSealNo = customsSealNo;
			this.agentSealNo = agentSealNo;
			this.hauller = hauller;
			this.sl = sl;
			this.vesselId = vesselId;
			this.backToTownPackages = backToTownPackages;
			this.gateOutPackages = gateOutPackages;
			this.sampleQty = sampleQty;
			this.isoCode = isoCode;
			this.pod = pod;
			this.location = location;
			this.cellAreaAllocated = cellAreaAllocated;
			this.qtyTakenOut = qtyTakenOut;
			this.areaReleased = areaReleased;
			this.gwTakenOut = gwTakenOut;
			this.yardPackages = yardPackages;
			this.viaNo = viaNo;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.grossWt = grossWt;
			this.vehicleWt = vehicleWt;
			this.sealNo = sealNo;
			this.containerStatus = containerStatus;
			this.deliveryOrderNo = deliveryOrderNo;
			this.haz = haz;
			this.gateOutId = gateOutId;
			this.temperature = temperature;
			this.departureTime = departureTime;
			this.comments = comments;
			this.damageCode = damageCode;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.sa = sa;
			this.voyageNo = voyageNo;
			this.chaName = chaName;
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


		public String getGatePassId() {
			return gatePassId;
		}


		public void setGatePassId(String gatePassId) {
			this.gatePassId = gatePassId;
		}


		public String getProfitcentreId() {
			return profitcentreId;
		}


		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}


		public int getSrNo() {
			return srNo;
		}


		public void setSrNo(int srNo) {
			this.srNo = srNo;
		}


		public String getSbTransId() {
			return sbTransId;
		}


		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}


		public String getSbLineNo() {
			return sbLineNo;
		}


		public void setSbLineNo(String sbLineNo) {
			this.sbLineNo = sbLineNo;
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


		public String getCargoExamId() {
			return cargoExamId;
		}


		public void setCargoExamId(String cargoExamId) {
			this.cargoExamId = cargoExamId;
		}


		public String getCargoLineId() {
			return cargoLineId;
		}


		public void setCargoLineId(String cargoLineId) {
			this.cargoLineId = cargoLineId;
		}


		public String getStuffTallyId() {
			return stuffTallyId;
		}


		public void setStuffTallyId(String stuffTallyId) {
			this.stuffTallyId = stuffTallyId;
		}


		public BigDecimal getStuffTallyLineId() {
			return stuffTallyLineId;
		}


		public void setStuffTallyLineId(BigDecimal stuffTallyLineId) {
			this.stuffTallyLineId = stuffTallyLineId;
		}


		public String getMovReqType() {
			return movReqType;
		}


		public void setMovReqType(String movReqType) {
			this.movReqType = movReqType;
		}


		public Date getGatePassDate() {
			return gatePassDate;
		}


		public void setGatePassDate(Date gatePassDate) {
			this.gatePassDate = gatePassDate;
		}


		public String getMovementReqId() {
			return movementReqId;
		}


		public void setMovementReqId(String movementReqId) {
			this.movementReqId = movementReqId;
		}


		public String getTransType() {
			return transType;
		}


		public void setTransType(String transType) {
			this.transType = transType;
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


		public String getTripType() {
			return tripType;
		}


		public void setTripType(String tripType) {
			this.tripType = tripType;
		}


		public String getVehicleNo() {
			return vehicleNo;
		}


		public void setVehicleNo(String vehicleNo) {
			this.vehicleNo = vehicleNo;
		}


		public String getVehStatus() {
			return vehStatus;
		}


		public void setVehStatus(String vehStatus) {
			this.vehStatus = vehStatus;
		}


		public String getInvoiceNo() {
			return invoiceNo;
		}


		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}


		public String getPol() {
			return pol;
		}


		public void setPol(String pol) {
			this.pol = pol;
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


		public String getCha() {
			return cha;
		}


		public void setCha(String cha) {
			this.cha = cha;
		}


		public String getCommodity() {
			return commodity;
		}


		public void setCommodity(String commodity) {
			this.commodity = commodity;
		}


		public String getExaminer() {
			return examiner;
		}


		public void setExaminer(String examiner) {
			this.examiner = examiner;
		}


		public String getNumberOfMarks() {
			return numberOfMarks;
		}


		public void setNumberOfMarks(String numberOfMarks) {
			this.numberOfMarks = numberOfMarks;
		}


		public String getDriverName() {
			return driverName;
		}


		public void setDriverName(String driverName) {
			this.driverName = driverName;
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


		public String getTransporterStatus() {
			return transporterStatus;
		}


		public void setTransporterStatus(String transporterStatus) {
			this.transporterStatus = transporterStatus;
		}


		public String getCustomsSealNo() {
			return customsSealNo;
		}


		public void setCustomsSealNo(String customsSealNo) {
			this.customsSealNo = customsSealNo;
		}


		public String getAgentSealNo() {
			return agentSealNo;
		}


		public void setAgentSealNo(String agentSealNo) {
			this.agentSealNo = agentSealNo;
		}


		public String getHauller() {
			return hauller;
		}


		public void setHauller(String hauller) {
			this.hauller = hauller;
		}


		public String getSl() {
			return sl;
		}


		public void setSl(String sl) {
			this.sl = sl;
		}


		public String getVesselId() {
			return vesselId;
		}


		public void setVesselId(String vesselId) {
			this.vesselId = vesselId;
		}


		public BigDecimal getBackToTownPackages() {
			return backToTownPackages;
		}


		public void setBackToTownPackages(BigDecimal backToTownPackages) {
			this.backToTownPackages = backToTownPackages;
		}


		public BigDecimal getGateOutPackages() {
			return gateOutPackages;
		}


		public void setGateOutPackages(BigDecimal gateOutPackages) {
			this.gateOutPackages = gateOutPackages;
		}


		public BigDecimal getSampleQty() {
			return sampleQty;
		}


		public void setSampleQty(BigDecimal sampleQty) {
			this.sampleQty = sampleQty;
		}


		public String getIsoCode() {
			return isoCode;
		}


		public void setIsoCode(String isoCode) {
			this.isoCode = isoCode;
		}


		public String getPod() {
			return pod;
		}


		public void setPod(String pod) {
			this.pod = pod;
		}


		public String getLocation() {
			return location;
		}


		public void setLocation(String location) {
			this.location = location;
		}


		public BigDecimal getCellAreaAllocated() {
			return cellAreaAllocated;
		}


		public void setCellAreaAllocated(BigDecimal cellAreaAllocated) {
			this.cellAreaAllocated = cellAreaAllocated;
		}


		public BigDecimal getQtyTakenOut() {
			return qtyTakenOut;
		}


		public void setQtyTakenOut(BigDecimal qtyTakenOut) {
			this.qtyTakenOut = qtyTakenOut;
		}


		public BigDecimal getAreaReleased() {
			return areaReleased;
		}


		public void setAreaReleased(BigDecimal areaReleased) {
			this.areaReleased = areaReleased;
		}


		public BigDecimal getGwTakenOut() {
			return gwTakenOut;
		}


		public void setGwTakenOut(BigDecimal gwTakenOut) {
			this.gwTakenOut = gwTakenOut;
		}


		public BigDecimal getYardPackages() {
			return yardPackages;
		}


		public void setYardPackages(BigDecimal yardPackages) {
			this.yardPackages = yardPackages;
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


		public BigDecimal getGrossWt() {
			return grossWt;
		}


		public void setGrossWt(BigDecimal grossWt) {
			this.grossWt = grossWt;
		}


		public BigDecimal getVehicleWt() {
			return vehicleWt;
		}


		public void setVehicleWt(BigDecimal vehicleWt) {
			this.vehicleWt = vehicleWt;
		}


		public String getSealNo() {
			return sealNo;
		}


		public void setSealNo(String sealNo) {
			this.sealNo = sealNo;
		}


		public String getContainerStatus() {
			return containerStatus;
		}


		public void setContainerStatus(String containerStatus) {
			this.containerStatus = containerStatus;
		}


		public String getDeliveryOrderNo() {
			return deliveryOrderNo;
		}


		public void setDeliveryOrderNo(String deliveryOrderNo) {
			this.deliveryOrderNo = deliveryOrderNo;
		}


		public String getHaz() {
			return haz;
		}


		public void setHaz(String haz) {
			this.haz = haz;
		}


		public String getGateOutId() {
			return gateOutId;
		}


		public void setGateOutId(String gateOutId) {
			this.gateOutId = gateOutId;
		}


		public String getTemperature() {
			return temperature;
		}


		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}


		public Date getDepartureTime() {
			return departureTime;
		}


		public void setDepartureTime(Date departureTime) {
			this.departureTime = departureTime;
		}


		public String getComments() {
			return comments;
		}


		public void setComments(String comments) {
			this.comments = comments;
		}


		public String getDamageCode() {
			return damageCode;
		}


		public void setDamageCode(String damageCode) {
			this.damageCode = damageCode;
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


		public String getSa() {
			return sa;
		}


		public void setSa(String sa) {
			this.sa = sa;
		}


		public String getVoyageNo() {
			return voyageNo;
		}


		public void setVoyageNo(String voyageNo) {
			this.voyageNo = voyageNo;
		}





		public ExportGatePass(String movementReqId,String gatePassId, String sbTransId, Date gatePassDate, String transType, String sbNo,
				Date sbDate, String tripType, String vehicleNo, String vehicleId, String pol, String transporterName,
				String transporter, String customsSealNo, String agentSealNo, String pod, String containerNo,
				String containerSize, String containerType, BigDecimal grossWt, String containerStatus, String comments,
				String status, String createdBy, String chaName, String viaNo) {
			this.movementReqId = movementReqId;
			this.gatePassId = gatePassId;
			this.sbTransId = sbTransId;
			this.gatePassDate = gatePassDate;
			this.transType = transType;
			this.sbNo = sbNo;
			this.sbDate = sbDate;
			this.tripType = tripType;
			this.vehicleNo = vehicleNo;
			this.vehicleId = vehicleId;
			this.pol = pol;
			this.transporterName = transporterName;
			this.transporter = transporter;
			this.customsSealNo = customsSealNo;
			this.agentSealNo = agentSealNo;
			this.pod = pod;
			this.containerNo = containerNo;
			this.containerSize = containerSize;
			this.containerType = containerType;
			this.grossWt = grossWt;
			this.containerStatus = containerStatus;
			this.comments = comments;
			this.status = status;
			this.createdBy = createdBy;
			this.chaName = chaName;
			this.viaNo = viaNo;
		}
	    
	    
	  
		
		
	    

}
